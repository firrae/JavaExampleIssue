/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Steven lambe
 */
@Path("/brigde")
public class bridge {
    
    @GET
    @Produces("application/json")
    public String getAll() {
        return "";
    }
    
}
