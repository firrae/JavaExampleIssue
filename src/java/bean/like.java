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
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author stlam_000
 */
@Path("/like")
public class like {
    MongoClientURI connectionString = new MongoClientURI("mongodb://myUser:doubleDown@ds061741.mongolab.com:61741/bridges");
    MongoClient mongoClient = new MongoClient(connectionString);
    MongoDatabase database = mongoClient.getDatabase("bridges");
    MongoCollection<Document> collection = database.getCollection("bridges");    
    
    @POST
    @Path("/{id}")
    @Consumes("application/json")
    public Response addLike(@PathParam("id") String id) {        
        Document myDoc = collection.find(eq("_id", new ObjectId(id))).first();
        
        int likes = myDoc.getInteger("likes");
        
        likes += 1;
        
        BasicDBObject docToDoc = new BasicDBObject("likes", 1);
        
        BasicDBObject updateCommand = new BasicDBObject("$inc", docToDoc);
         
        collection.updateOne(myDoc, updateCommand);
        
        return Response
                .status(201)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }
    
    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    public Response removeLike(@PathParam("id") String id) {        
        Document myDoc = collection.find(eq("_id", new ObjectId(id))).first();
        
        int likes = myDoc.getInteger("likes");
        
        likes += 1;
        
        BasicDBObject docToDoc = new BasicDBObject("likes", -1);
        
        BasicDBObject updateCommand = new BasicDBObject("$inc", docToDoc);
         
        collection.updateOne(myDoc, updateCommand);
        
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
