/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author stlam_000
 */
@Path("/correction")
public class corrections {
    MongoClientURI connectionString = new MongoClientURI("mongodb://myUser:doubleDown@ds061741.mongolab.com:61741/bridges");
    MongoClient mongoClient = new MongoClient(connectionString);
    MongoDatabase database = mongoClient.getDatabase("bridges");
    MongoCollection<Document> collection = database.getCollection("bridges");
    
    @POST
    @Path("/{id}")
    @Consumes("application/json")
    public Response addCorrection(String str, @PathParam("id") String id) {
        JsonReader reader = Json.createReader(new StringReader(str));
        JsonObject json = reader.readObject();
        
        Document myDoc = collection.find(eq("_id", new ObjectId(id))).first();
        BasicDBObject updateCommand = null;
        
        System.out.println(json.getString("field"));
        
        BasicDBObject docToInsert = new BasicDBObject("minutes", json.getInt("minutes"));
        
        System.out.println(docToInsert.toString());
        
        if("correctionsCanCommercialFlow".equals(json.getString("field"))) {
            updateCommand = new BasicDBObject("$push", new BasicDBObject("correctionsCanCommercialFlow", docToInsert));
        } else if("correctionsUsCommercialFlow".equals(json.getString("field"))) {
            updateCommand = new BasicDBObject("$push", new BasicDBObject("correctionsUsCommercialFlow", docToInsert));
        } else if("correctionsCanTravellersFlow".equals(json.getString("field"))) {
            updateCommand = new BasicDBObject("$push", new BasicDBObject("correctionsCanTravellersFlow", docToInsert));
        } else if("correctionsUsTravellersFlow".equals(json.getString("field"))) {
            updateCommand = new BasicDBObject("$push", new BasicDBObject("correctionsUsTravellersFlow", docToInsert));
        }
        
        BasicDBObject updateQuery = new BasicDBObject("_id", new ObjectId(id));
        
        if(updateCommand != null) {
            collection.updateOne(myDoc, updateCommand);
        }
        
        System.out.println(myDoc.toJson());
        return Response
                .status(201)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }
}
