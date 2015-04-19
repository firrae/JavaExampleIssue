/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.mongodb.*;
import com.mongodb.client.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.bson.Document;

/**
 *
 * @author Steven lambe
 */
@Path("/bridge")
public class bridge {
    
    MongoClientURI connectionString = new MongoClientURI("mongodb://myUser:doubleDown@ds061741.mongolab.com:61741/bridges");
    MongoClient mongoClient = new MongoClient(connectionString);
    MongoDatabase database = mongoClient.getDatabase("bridges");
    MongoCollection<Document> collection = database.getCollection("bridges");
    
    @GET
    @Produces("application/json")
    public String getAll() {
        return "";
    }
    
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public String getBridge(@PathParam("id") String id) {
        return id;
    }
    
    @POST
    @Consumes("application/json")
    public Response test() {
        Document doc = new Document("name", "MongoDB")
               .append("type", "database")
               .append("count", 1)
               .append("info", new Document("x", 203).append("y", 102));
        
        collection.insertOne(doc);
        
        Document myDoc = collection.find().first();
        
        return Response.status(201).entity(myDoc.toJson()).build();
    } 
    
}
