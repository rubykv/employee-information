import org.bson.Document;
import org.bson.types.ObjectId;

import com.employeeinfoportal.dto.Employee;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

public class Test {


	public static void main(String args[]) {
		// Create credentials
		// MongoCredential credential =
		// MongoCredential.createCredential("rubyshiv", "employeeportal",
		// "admin123".toCharArray());
		// List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		// credentials.add(credential);

		// Create client
		final Builder clientBuilder = MongoClientOptions.builder().connectTimeout(9000);
		final MongoClientOptions options = clientBuilder.build();
		final MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017), options);

		try {

			// get/create database
			MongoDatabase db = mongoClient.getDatabase("employeeportal");

			// get/create collections
			MongoCollection<Document> collection = db.getCollection("employee");
			final MongoCursor<Document> cursor = collection.find().iterator();


			// create data
			Document documentObj = new Document();
			documentObj.put("name", "ruby k v");
			documentObj.put("city", "xx k v");
			documentObj.put("phoneNumber", "ruby k v");

			


			// insert data
			// collection.insertOne(documentObj);

			// retrieve data
		/*	for (Document doc : collection.find()) {

				System.out.println("Before update:" + doc.toJson());
			}
*/
			// update data
			final ObjectId _id = new ObjectId("58382bb78e2b140b1f99efea");
			Document myDoc = collection.find(com.mongodb.client.model.Filters.eq("_id", _id)).first();
System.out.println(""+ myDoc.toJson());
			final UpdateResult updateMany = collection.replaceOne(com.mongodb.client.model.Filters.eq("_id", _id),documentObj);
			
			
			
			//collection.updateMany(com.mongodb.client.model.Filters.eq("id", "1"), new Document("$set", new Document("name", "xx")));
			
			// delete data
			//collection.deleteOne(com.mongodb.client.model.Filters.eq("name", "K P Menon"));

			for (Document doc : collection.find()) {

				String id = doc.getObjectId("_id").toString();
				System.out.println("After update:" + doc.toJson());
				System.out.println("_id is:" + id);

			}
			
			/*ObjectId id = new ObjectId("583824e88e2b140b03f81d7b");
			Document myDoc = collection.find(com.mongodb.client.model.Filters.eq("_id", id)).first();
			System.out.println("myDoc is:" + myDoc);*/


		} catch (Exception e) {
			System.out.println("Mongo is down"+e);
			return;
		} finally {
			mongoClient.close();
		}
	}
	

}
