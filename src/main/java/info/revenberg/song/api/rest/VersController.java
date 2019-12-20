package info.revenberg.song.api.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import info.revenberg.song.domain.Vers;
import info.revenberg.song.exception.DataFormatException;
import info.revenberg.song.service.VersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/rest/v1/vers")
@Api(tags = { "vers" })
public class VersController extends AbstractRestHandler {

        @Autowired
        private VersService versService;

        @RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
                        "application/json" })
        @ResponseStatus(HttpStatus.CREATED)
        @ApiOperation(value = "Create a vers resource.", notes = "Returns the URL of the new resource in the Location header.")
        public @ResponseBody Vers createVers(@RequestBody Vers vers, HttpServletRequest request,
                        HttpServletResponse response) {
                Vers createdVers = this.versService.createVers(vers);
                response.setHeader("Location",
                                request.getRequestURL().append("/").append(createdVers.getId()).toString());
                return createdVers;
        }

        @RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json" })
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Get a paginated list of all verses.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
        public @ResponseBody Page<Vers> getAllVers(
                        @ApiParam(value = "The page number (zero-based)", required = true) @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                        @ApiParam(value = "The page size", required = true) @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                        HttpServletRequest request, HttpServletResponse response) {
                System.out.println("Page<Vers> getAllVers");
                System.out.println(page);
                System.out.println(size);
                Page<Vers> x = this.versService.getAllVerses(page, size);
                System.out.println(x.getTotalElements());
                System.out.println(x.getContent().get(0));

                return this.versService.getAllVerses(page, size);
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Get a single vers.", notes = "You have to provide a valid vers ID.")
        public @ResponseBody Vers getVers(
                        @ApiParam(value = "The ID of the vers.", required = true) @PathVariable("id") Long id,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
                Optional<Vers> vers = this.versService.getVers(id);
                checkResourceFound(vers);
                return vers.get();
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/json" }, produces = {
                        "application/json" })
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @ApiOperation(value = "Update a vers resource.", notes = "You have to provide a valid vers ID in the URL and in the payload. The ID attribute can not be updated.")
        public void updateVers(
                        @ApiParam(value = "The ID of the existing vers resource.", required = true) @PathVariable("id") Long id,
                        @RequestBody Vers vers, HttpServletRequest request, HttpServletResponse response) {
                checkResourceFound(this.versService.getVers(id));
                if (id != vers.getId())
                        throw new DataFormatException("ID doesn't match!");
                this.versService.updateVers(vers);
        }

        // todo: @ApiImplicitParams, @ApiResponses
        @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @ApiOperation(value = "Delete a vers resource.", notes = "You have to provide a valid vers ID in the URL. Once deleted the resource can not be recovered.")
        public void deleteVers(
                        @ApiParam(value = "The ID of the existing vers resource.", required = true) @PathVariable("id") Long id,
                        HttpServletRequest request, HttpServletResponse response) {
                checkResourceFound(this.versService.getVers(id));
                this.versService.deleteVers(id);
        }
}
