package ibf2022.batch2.csf.backend.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.Archive;
import ibf2022.batch2.csf.backend.services.ArchiveService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path = "/api")
public class UploadController {

	@Autowired
	private ArchiveService arcSvc;

	// TODO: Task 2, Task 3, Task 4

	@PostMapping(path = "/upload")
	public ResponseEntity<String> upload(@RequestPart MultipartFile[] files,
			@RequestPart String name,
			@RequestPart String title, @RequestPart String comments) throws IOException {

		try {

			String id = arcSvc.uploadAllFiles(files, name, title, comments);

			JsonObject msg = Json.createObjectBuilder()
					.add("bundleId", id)
					.build();

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(msg.toString());

		} catch (Error e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}

	}

	@GetMapping(path = "/search/{id}")
	public ResponseEntity<String> getOneArchive(@PathVariable String id) {

		JsonObject j = arcSvc.getArchive(id);

		System.out.println("i am here");

		if (null == j) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("not found");
		} else {
			return ResponseEntity.status(HttpStatus.OK)
					.body(j.toString());
		}

	}

	@GetMapping(path = "/all")
	public ResponseEntity<String> getAll() {

		List<Archive> archivesInfo = arcSvc.getAll();

		JsonArrayBuilder arr = Json.createArrayBuilder();

		for (Archive a : archivesInfo) {
			JsonObject json = Json.createObjectBuilder()
					.add("title", a.getTitle())
					.add("name", a.getName())
					.build();
			arr.add(json);
		}

		return ResponseEntity.status(HttpStatus.OK)
				.body(arr.toString());

	}

	// @PostMapping(path = "/upload")
	// public ResponseEntity<String> upload(@RequestPart MultipartFile file,
	// @RequestPart String name,
	// @RequestPart String title, @RequestPart String comments) throws IOException {

	// System.out.println("i am zip");
	// ZipInputStream zis = new ZipInputStream(file.getInputStream());
	// ZipEntry entry;

	// while ((entry = zis.getNextEntry()) != null) {

	// String url = arcSvc.uploadFile((MultipartFile) entry);
	// System.out.printf(">>>>%s\n", url);
	// }

	// return null;

	// }

	// TODO: Task 5

	// TODO: Task 6

}
