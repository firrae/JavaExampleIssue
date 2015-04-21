/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParser;
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
    public Response addCorrection(String str) {
        JsonReader reader = Json.createReader(new StringReader(str));
        JsonObject json = reader.readObject();
        Document myDoc = collection.find(eq("_id", new ObjectId("5535cb40e3035211001bf489"))).first();
        
        if(null != json.getString("field")) switch (json.getString("field")) {
            case "correctionsCanCommercialFlow":
                myDoc.append("correctionsCanCommercialFlow", "{\"minutes\":" + json.getInt("minutes") + "}");
                break;
            case "correctionsUsCommercialFlow":
                myDoc.append("correctionsUsCommercialFlow", "{\"minutes\":" + json.getInt("minutes") + "}");
                break;
            case "correctionsCanTravellersFlow":
                myDoc.append("correctionsCanTravellersFlow", "{\"minutes\":" + json.getInt("minutes") + "}");
                break;
            case "correctionsUsTravellersFlow":
                myDoc.append("correctionsUsTravellersFlow", "{\"minutes\":" + json.getInt("minutes") + "}");
                break;
        }
        
        collection.replaceOne(Filters.eq("_id", myDoc.get("_id")), myDoc);
        
        System.out.println(myDoc.toJson());
        return Response.status(201).build();
    }
}
