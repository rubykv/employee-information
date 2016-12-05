package com.employeeinfoportal.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.employeeinfoportal.controller.EmployeeInfoController;
import com.employeeinfoportal.dto.Employee;
import com.employeeinfoportal.util.DAOException;
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
	private static final Logger logger = Logger.getLogger(EmployeeInfoController.class);


	@Override
	public void updateEmployeeInfo(final Employee emp) {
		final MongoClient mongoClient = getConnection();
		final MongoCollection<Document> collection = getEmployeeCollection(mongoClient);
		
		try {
			if (null != emp && emp.get_id() != null) {
				final ObjectId _id = new ObjectId(emp.get_id());
				Document createEmployeeDocument = createEmployeeDocument(emp);
				final UpdateResult updateDoc = collection.replaceOne(com.mongodb.client.model.Filters.eq(_ID, _id),createEmployeeDocument);
				if (null == updateDoc || updateDoc.getModifiedCount() <= 0) {
					logger.error("Document not updated");
					throw new DAOException("Document not updated");
				}
			}
		} catch (final DAOException exception) {
			logger.error("Exception thrown inside updateEmployeeInfo:MongoDBDAOImpl" + exception);
		} catch (final Exception exception) {
			logger.error("Exception thrown inside updateEmployeeInfo:MongoDBDAOImpl" + exception);
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
			logger.error("Exception occured inside EmployeeInfoDAOImpl : retrieveAllEmployeeInfo"+exception);
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
				throw new DAOException("Document not found to delete");
			}
			
			if(null == deleteOne ||  !(deleteOne.wasAcknowledged())){
				throw new DAOException("Document not deleted");			
			}
		} catch(final DAOException exception){
			logger.error("Exception occured inside EmployeeInfoDAOImpl : deleteSingleUser"+exception);
		} catch(final Exception exception){
			logger.error("Exception occured inside EmployeeInfoDAOImpl : deleteSingleUser"+exception);
		}finally{
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
			logger.error("Exception occured inside EmployeeInfoDAOImpl : createNewEmployee"+exception);
		} finally {
			mongoClient.close();
		}
	}
}
