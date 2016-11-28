package com.employeeinfoportal.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.employeeinfoportal.dto.Employee;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Repository
public class EmployeeInfoDAOImpl implements EmployeeInfoDAO {
	private static final String _ID = "_id";

	@Override
	public void updateEmployeeInfo(final Employee emp) {
		final MongoClient mongoClient = getConnection();
		final MongoCollection<Document> collection = getEmployeeCollection(mongoClient);
		
		try {
			if (null != emp && emp.get_id() != null) {
				final ObjectId _id = new ObjectId(emp.get_id());
				Document createEmployeeDocument = createEmployeeDocument(emp);
				final UpdateResult updateMany = collection.replaceOne(com.mongodb.client.model.Filters.eq(_ID, _id),createEmployeeDocument);
				if (null != updateMany && updateMany.getModifiedCount() > 0) {
					for (Document doc : collection.find()) {

						System.out.println("After update:" + doc.toJson());

					}
				}
			}
		} catch (final Exception exception) {
			System.out.println("Exception thrown inside updateEmployeeInfo:MongoDBDAOImpl" + exception);
		} finally {
			mongoClient.close();
		}
	}

	private MongoCollection<Document> getEmployeeCollection(final MongoClient mongoClient) {
		final MongoDatabase db = mongoClient.getDatabase("employeeportal");
		final MongoCollection<Document> collection = db.getCollection("employee");
		return collection;
	}

	private MongoClient getConnection() {
		final Builder clientBuilder = MongoClientOptions.builder().connectTimeout(9000);
		final MongoClientOptions options = clientBuilder.build();
		final MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017), options);
		return mongoClient;
	}

	private Document createEmployeeDocument(Employee emp) {
		Document documentObj = new Document();
		documentObj.put("name", emp.getName());
		documentObj.put("city", emp.getCity());
		documentObj.put("phoneNumber", emp.getPhoneNumber());
		return documentObj;
	}

	@Override
	public List<Employee> retrieveAllEmployeeInfo() {
		final MongoClient mongoClient = getConnection();
		final MongoCollection<Document> collection = getEmployeeCollection(mongoClient);
		final List<Employee> allEmployees = new ArrayList<Employee>();

		try {
		    for (final Document doc : collection.find()) {

				final String id = doc.getObjectId(_ID).toString();
				final String name = doc.getString("name");
				final String city = doc.getString("city");
				final String phoneNum = doc.getString("phoneNumber");
				final Employee emp = new Employee(id,name,city,phoneNum);
		    	allEmployees.add(emp);
			}
		} catch(final Exception exception){
			System.out.println("Exception occured inside EmployeeInfoDAOImpl : retrieveAllEmployeeInfo"+exception);
		} finally {
		    mongoClient.close();
		}
		return allEmployees;
	}

	@Override
	public void deleteSingleUser(final String id) {
		final MongoClient mongoClient = getConnection();
		final MongoCollection<Document> collection = getEmployeeCollection(mongoClient);
		DeleteResult deleteOne = null;
		
		try{
			ObjectId _id = new ObjectId(id);
			Document myDoc = collection.find(com.mongodb.client.model.Filters.eq(_ID, _id)).first();
			
			if(null != myDoc){
				deleteOne = collection.deleteOne(com.mongodb.client.model.Filters.eq(_ID, _id));
			} else {
				//throw new Exception();
			}
			
			if(null == deleteOne ||  !(deleteOne.wasAcknowledged())){
				//throw new exception
			}
		} catch(final Exception exception){
			System.out.println("Exception occurred inside deleteSingleUser:EmployeeInfoDAOImpl"+exception);
		} finally{
			mongoClient.close();
		}
	}

	@Override
	public void createNewEmployee(Employee emp) {
		final MongoClient mongoClient = getConnection();
		final MongoCollection<Document> collection = getEmployeeCollection(mongoClient);

		try {
			collection.insertOne(createEmployeeDocument(emp));
		} catch (final Exception exception) {
			System.out.println("Exception occurred inside createNewEmployee:EmployeeInfoDAOImpl" + exception);
		} finally {
			mongoClient.close();
		}
	}
}
