package ibf2022.batch2.csf.backend.models;

import java.io.Serializable;
import java.io.StringReader;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Archive implements Serializable {

    private String bundleId;
    private String title;
    private Date date;
    private String name;
    private String comments;
    private List<String> urls;

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public static Document toDocument(Archive a) {
        Document d = new Document();
        d.put("bundle_id", a.getBundleId());
        d.put("name", a.getName());
        d.put("title", a.getTitle());
        d.put("urls", a.getUrls());
        d.put("comments", a.getComments());
        // d.put("date", a.getDate());
        return d;
    }

    public static JsonObject toJson(Document d) {
        JsonReader reader = Json.createReader(new StringReader(d.toJson()));
        return reader.readObject();
    }

    public static Archive toArchive(JsonObject j) {
        Archive archive = new Archive();
        archive.setBundleId(j.getString("bundle_id"));
        archive.setComments(j.getString("comments"));
        // archive.setDate(Date.valueOf(j.getString("date")));
        archive.setName(j.getString("name"));
        archive.setTitle(j.getString("title"));
        JsonArray jArr = j.getJsonArray("urls");
        List<String> urls = new ArrayList<>();
        for (int i = 0; i < jArr.size(); i++) {
            urls.add(jArr.getString(i));
        }
        archive.setUrls(urls);
        return archive;
    }

    public static Archive toArchive(Document d) {
        Archive archive = new Archive();
        archive.setBundleId(d.getString("bundle_id"));
        archive.setComments(d.getString("comments"));
        // archive.setDate(Date.valueOf(d.getDate("date").toString()));
        archive.setName(d.getString("name"));
        archive.setTitle(d.getString("title"));

        List<String> urls = d.getList("urls", String.class);
        archive.setUrls(urls);

        return archive;
    }

    @Override
    public String toString() {
        return "Archive [bundleId=" + bundleId + ", comments=" + comments + ", date=" + date + ", name=" + name
                + ", title=" + title + ", urls=" + urls + "]";
    }

}
