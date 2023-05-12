package ibf2022.batch2.csf.backend.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.Archive;
import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@Service
public class ArchiveService {

    @Autowired
    private ArchiveRepository arcRepo;

    @Autowired
    private ImageRepository imgRepo;

    @Autowired
    private ArchiveRepository arcSvc;

    public String uploadAllFiles(MultipartFile[] imageFiles, String name, String title, String comments)
            throws IOException {

        List<String> urls = new LinkedList<>();

        System.out.println(imageFiles.length);

        for (int i = 0; i < imageFiles.length; i++) {

            String url = imgRepo.upload(imageFiles[i]);
            urls.add(url);
        }

        Archive a = new Archive();

        String bundleId = UUID.randomUUID().toString().substring(0, 8);

        a.setBundleId(bundleId);
        a.setComments(comments);
        a.setTitle(title);
        a.setName(name);
        LocalDate date = LocalDate.now();
        a.setDate(date);
        a.setUrls(urls);

        arcRepo.recordBundle(a);

        System.out.printf(">>>>>>>>>%s\n", bundleId);

        return bundleId;

    }

    public JsonObject getArchive(String id) {

        Archive a = arcRepo.getBundleByBundleId(id);

        if (null != a) {
            JsonObject json = Json.createObjectBuilder()
                    .add("bundleId", a.getBundleId())
                    .add("name", a.getName())
                    .add("title", a.getTitle())
                    .add("comments", a.getComments())
                    .add("date", a.getDate().toString())
                    .add("urls", a.getUrls().toString())
                    .build();
            return json;
        } else {
            return null;
        }
    }

    public List<Archive> getAll() {

        List<Document> docs = this.arcRepo.getBundles();
        System.out.printf(">>>>docs:%s\n", docs.toString());
        List<Archive> archives = new LinkedList<>();

        for (Document d : docs) {

            Archive a = new Archive();
            a.setTitle(d.getString("title"));
            a.setName(d.getString("name"));
            Date date = d.getDate("date");

            LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
            LocalDate localDate = localDateTime.toLocalDate();
            a.setDate(localDate);
            archives.add(a);
        }

        return archives;

    }

    // public String uploadFile(MultipartFile imageFile)
    // throws IOException {
    // String url = imgRepo.upload(imageFile);
    // return url;
    // }

}
