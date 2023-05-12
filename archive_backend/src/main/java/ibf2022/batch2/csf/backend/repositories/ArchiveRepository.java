package ibf2022.batch2.csf.backend.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import ibf2022.batch2.csf.backend.models.Archive;
import jakarta.json.JsonObject;

@Repository
public class ArchiveRepository {

	// TODO: Task 4

	@Autowired
	private MongoTemplate mongoTemplate;

	private static final String DB_COLLECTIONS = "archives";

	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	/*
	 * // Native query: db.archives.insert({
	 * bundle_id: 12345678,
	 * date: Date(),
	 * title: "harry potter",
	 * name: "Zi Hao",
	 * comments: "memories",
	 * urls:
	 * ["https://ozh2923.sgp1.digitaloceanspaces.com/myObject3a887d2c.blob.png",
	 * "https://ozh2923.sgp1.digitaloceanspaces.com/myObjectcd08a4a7.png"]
	 * });
	 */

	public Object recordBundle(Archive archive) {

		Document doc = Archive.toDocument(archive);
		mongoTemplate.insert(doc, DB_COLLECTIONS);

		return archive.getBundleId();
	}

	// TODO: Task 5
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//

	/*
	 * db.archives.findOne({
	 * bundle_id: bundleId
	 * })
	 */

	public Archive getBundleByBundleId(String bundleId) {

		Criteria criterial = Criteria.where("bundle_id").is(bundleId);
		Query query = Query.query(criterial);

		Document d = mongoTemplate.findOne(query, Document.class, DB_COLLECTIONS);

		Archive a = Archive.toArchive(d);

		return a;
	}

	// TODO: Task 6
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	/*
	 * db.archives.find({}).sort({"date": -1, "title": 1})
	 */

	public List<Archive> getBundles() {

		Criteria criterial = Criteria.where("bundle_id").exists(true);

		Query query = Query.query(criterial)
				.with(
						Sort.by(Direction.DESC, "date")
								.and(Sort.by(Direction.ASC, "title")));

		query.fields().include("title", "date").exclude("_id");
		List<Archive> archives = mongoTemplate.find(query, Document.class, DB_COLLECTIONS).stream()
				.map(v -> Archive.toArchive(v))
				.toList();

		return archives;
	}

}
