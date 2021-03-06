/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.mongodb.*;
import com.mongodb.client.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.bson.Document;
import org.bson.types.ObjectId;

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
    public Response getAll() {
        List<Document> foundDocuments = collection.find().sort(descending("update")).limit(25).into(new ArrayList<Document>());
        
        String json = "[";
        
        for(int i = 0; i < foundDocuments.size(); i++)
        {
            json += foundDocuments.get(i).toJson();
            if(i != foundDocuments.size() - 1)
            {
                json += ",";
            }
        }
        
        json += "]";
        
        return Response
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .entity(json)
                .build();
    }
    
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getBridge(@PathParam("id") String id) {
        Document myDoc = collection.find(eq("_id", new ObjectId(id))).first();
        return Response
                .ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .entity(myDoc.toJson())
                .build();
    }
}
