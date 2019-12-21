package info.revenberg.song.api.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import info.revenberg.song.domain.Bundle;
import info.revenberg.song.domain.Song;
import info.revenberg.song.exception.DataFormatException;
import info.revenberg.song.service.BundleService;
import info.revenberg.song.service.SongService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/rest/v1/song")
@Api(tags = { "song" })
public class SongController extends AbstractRestHandler {

        @Autowired
        private SongService songService;

        @Autowired
        private BundleService bundleService;

        @RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
                        "application/json" })
        @ResponseStatus(HttpStatus.CREATED)
        @ApiOperation(value = "Create a song resource.", notes = "Returns the URL of the new resource in the Location header.")
        public @ResponseBody Song createSong(@RequestBody Song song, HttpServletRequest request,
                        HttpServletResponse response) {
                Song createdSong = this.songService.createSong(song);
                response.setHeader("Location",
                                request.getRequestURL().append("/").append(createdSong.getId()).toString());
                return createdSong;
        }

        @RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json" })
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Get a paginated list of all songs.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
        public @ResponseBody Page<Song> getAllSong(
                        @ApiParam(value = "The page number (zero-based)", required = true) @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                        @ApiParam(value = "The page size", required = true) @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                        HttpServletRequest request, HttpServletResponse response) {
                return this.songService.getAllSongs(page, size);
        }

        @RequestMapping(value = "/{bundle}/findByName", method = RequestMethod.GET, produces = { "application/json" })
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Find song by name.")
        public @ResponseBody Page<Song> getAllSongesByName(
                        // @ApiParam(value = "The name of the bundle.", required = true)
                        // @PathVariable("bundle") String bundleName,
                        @ApiParam(value = "The name of the song.", required = true) @PathVariable("song") String songName,
                        @ApiParam(value = "The page number (zero-based)", required = true) @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                        @ApiParam(value = "The page size", required = true) @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                        @RequestParam(value = "bundle", required = true) String bundleName, HttpServletRequest request,
                        HttpServletResponse response) throws UnsupportedEncodingException {
                bundleName = URLDecoder.decode(bundleName, "UTF-8").trim();
                songName = URLDecoder.decode(songName, "UTF-8").trim();

                // Page<Song> songs = this.songService.getAllSongsBundlesByName(page, size,
                // bundleName, songName);
                // checkResourceFound(songs);
                return null;
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/json" }, produces = {
                        "application/json" })
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @ApiOperation(value = "Update a song resource.", notes = "You have to provide a valid song ID in the URL and in the payload. The ID attribute can not be updated.")
        public void updateSong(
                        @ApiParam(value = "The ID of the existing song resource.", required = true) @PathVariable("id") Long id,
                        @RequestBody Song song, HttpServletRequest request, HttpServletResponse response) {
                checkResourceFound(this.songService.getSong(id));
                if (id != song.getId())
                        throw new DataFormatException("ID doesn't match!");
                this.songService.updateSong(song);
        }

        // todo: @ApiImplicitParams, @ApiResponses
        @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @ApiOperation(value = "Delete a song resource.", notes = "You have to provide a valid song ID in the URL. Once deleted the resource can not be recovered.")
        public void deleteSong(
                        @ApiParam(value = "The ID of the existing song resource.", required = true) @PathVariable("id") Long id,
                        HttpServletRequest request, HttpServletResponse response) {
                checkResourceFound(this.songService.getSong(id));
                this.songService.deleteSong(id);
        }

        @RequestMapping(value = "/{bundleid}/findByName", method = RequestMethod.GET, produces = { "application/json" })
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Get a paginated list of all songs.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
        public @ResponseBody Song findSongsByName(
                @RequestParam(value = "name", required = true) String name,
                @ApiParam(value = "The ID of the existing bundle resource.", required = true) @PathVariable("bundleid") Long bundleid,
                        HttpServletRequest request, HttpServletResponse response) {
                Optional<Bundle> bundle = this.bundleService.getBundle(bundleid);                
                checkResourceFound(bundle);
                return this.songService.findSongByNameInBundle(name, bundle.get().getBundleid());                    
        }
        
}
