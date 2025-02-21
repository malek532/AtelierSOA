package webservices;

import entities.Logement;
import metiers.LogementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/logement")
public class LogementRessources {

    static LogementBusiness help = new LogementBusiness();
    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response  getAll(){
        return Response.
                status(200).header("Access-Control-Allow-Origin", "*").
                entity(help.getLogements()).
                build();
    }

    @GET
    @Path("/getByReference/{reference}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogementByReference(@PathParam("reference") int reference) {
        Logement logement = help.getLogementsByReference(reference);

        if (logement != null) {
            return Response.status(200)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity(logement)
                    .build();
        } else {
            return Response.status(404) // Not found
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Logement not found")
                    .build();
        }
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON) // Expecting a JSON object in the request body
    @Produces(MediaType.APPLICATION_JSON)
    public Response addLogement(Logement logement) {
        boolean added = help.addLogement(logement);

        if (added) {
            return Response.status(201) // Created
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Logement added successfully")
                    .build();
        } else {
            return Response.status(400) // Bad request if the addition fails
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Failed to add logement")
                    .build();
        }
    }



    @DELETE
    @Path("/delete/{reference}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteLogement(@PathParam("reference") int reference) {
        boolean deleted = help.deleteLogement(reference);

        if (deleted) {
            return Response.status(200) // OK
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Logement deleted successfully")
                    .build();
        } else {
            return Response.status(404) // Not found
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("Logement not found")
                    .build();
        }
    }

    @GET
    @Path("/getByDeleguation/{deleguation}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogementsByDeleguation(@PathParam("deleguation") String deleguation) {
        List<Logement> logements = help.getLogementsByDeleguation(deleguation);

        if (logements.isEmpty()) {
            return Response.status(404) // Not found
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("No logements found for delegation: " + deleguation)
                    .build();
        } else {
            return Response.status(200)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity(logements)
                    .build();
        }
    }

    // Endpoint to get logements by reference (list of matching references)
    @GET
    @Path("/getListByRef/{reference}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogementsListeByRef(@PathParam("reference") int reference) {
        List<Logement> logements = help.getLogementsListeByref(reference);

        if (logements.isEmpty()) {
            return Response.status(404) // Not found
                    .header("Access-Control-Allow-Origin", "*")
                    .entity("No logements found with reference: " + reference)
                    .build();
        } else {
            return Response.status(200)
                    .header("Access-Control-Allow-Origin", "*")
                    .entity(logements)
                    .build();
        }
    }

}
