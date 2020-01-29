package info.revenberg.song.api.rest;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import info.revenberg.song.domain.Bundle;
import info.revenberg.song.domain.Line;
import info.revenberg.song.domain.Song;
import info.revenberg.song.domain.TempFile;
import info.revenberg.song.domain.Vers;
import info.revenberg.song.domain.line.FindLinesInImage;
import info.revenberg.song.exception.DataContentTypeException;
import info.revenberg.song.exception.ResourceNotFoundException;
import info.revenberg.song.service.BundleService;
import info.revenberg.song.service.FileService;
import info.revenberg.song.service.LineService;
import info.revenberg.song.service.SongService;
import info.revenberg.song.service.VersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import info.revenberg.song.domain.line.ImageDefinition;

@RestController
@RequestMapping(value = "/rest/v1/ppt")
@Api(tags = { "ppt" }, description = "Process Power Point file to images")
public class PPTController {
	private static final Logger logger = Logger.getLogger(PPTController.class.getName());

	@Value("${upload.location}")
	private String uploadLocation;

	@Value("${unzip.location}")
	private String unzipLocation;

	@Value("${media.location}")
	private String mediaLocation;

	@Autowired
	private FileService fileService;

	@Autowired
	private BundleService bundleService;

	@Autowired
	private SongService songService;

	@Autowired
	private VersService versService;

	@Autowired
	private LineService lineService;

	@PostMapping("/")
	@RequestMapping(value = "/{bundle}/{song}", method = RequestMethod.POST, consumes = {
			"multipart/form-data" }, produces = { "application/json" })
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Process Power Point.", notes = "Returns details.")
	public @ResponseBody List<FindLinesInImage> uploadData(
			@ApiParam(value = "The name of the bundle.", required = true) @PathVariable("bundle") String bundleName,
			@ApiParam(value = "The name of the song.", required = true) @PathVariable("song") String songName,
			@RequestPart("file") MultipartFile file) throws Exception {

		bundleName = URLDecoder.decode(bundleName, "UTF-8").trim();
		songName = URLDecoder.decode(songName, "UTF-8").trim();

		if (file == null) {
			throw new RuntimeException("You must select the a file for uploading");
		}
		if (!file.getOriginalFilename().contains("pptx")) {
			throw new DataContentTypeException("Content must be Power Point presentation");
		}

		Bundle bundle = this.bundleService.getBundleByName(bundleName);
		if (bundle == null) {
			// throw new ResourceNotFoundException("Bundle "+ bundleName +" not found");
			bundle = new Bundle();
			bundle.setName(bundleName);
			bundle.setBundleid(0);
			bundle.setMnemonic(bundleName);
			bundle = this.bundleService.createBundle(bundle);
		}
		logger.info(bundle.toString());
		Song song = this.songService.findSongByNameInBundle(songName, bundle.getBundleid());
		if (song == null) {
			// throw new ResourceNotFoundException("Song "+ songName +" not found");
			song = new Song();
			song.setName(songName);
			song.setBundle(bundle);
			song.setsource("PPT processing");
			song = this.songService.createSong(song);
		}
		logger.info(song.toString());
		String filename = fileService.store(file, uploadLocation);

		String dir_ = file.getOriginalFilename().replace("/", "_").replace(".pptx", "").replace(" ", "_");
		String dest = unzipLocation + "/" + dir_;

		List<String> t1 = fileService.unzip(file, filename, uploadLocation, dest);

		List<FindLinesInImage> t2 = new ArrayList<FindLinesInImage>();
		// File tFile;
		int rank = 1;

		Vers vers;

		for (String temp : t1) {
			if (temp.contains(".png")) {
				vers = this.versService.findVersInSong(rank, song.getSongid());
				if (vers == null) {
					// tFile = fileService.moveToMedia(mediaLocation, temp, bundleName, songName);

					String[] s2 = temp.split("\\.");
					String ext = s2[s2.length - 1];
					String versName = Integer
							.toString(Integer.valueOf(temp.replace("." + ext, "").replace("image", "")) - 1);

					// TempFile fileInfo = new TempFile(tFile);

					vers = new Vers();
					// String[] tokens = fileInfo.getName().split(".");
					// String versName = tokens[tokens.length - 2];

					vers.setName(versName);
					vers.setSong(song);
					vers.setRank(rank);
					vers.setTitle(versName);
					this.versService.createVers(vers);

					FindLinesInImage result = new FindLinesInImage(temp, mediaLocation, bundleName, songName);
					// result1.createIMG(1, 3, "41_gezangen.Gz_001");

					for (int j = 0; j < result.getversLines(); j++) {
						ImageDefinition id = result.getImageDefinitions().get((Integer) j);

						Line line = new Line(j, "", id.getFilename(), id.getminY(), id.getMaxY(), id.getminX(),
								id.getMaxX(), vers);

						this.lineService.createLine(line);
					}

					t2.add(result);

					// vers.setLocation(tFile.getAbsolutePath());

					rank++;
				}
			}
		}

		FileUtils.deleteDirectory(new File(dest));
		if (t2.size() == 0) {
			throw new ResourceNotFoundException();
		}
		return t2;
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<TempFile> noChanges() {
		return null;
	}
}