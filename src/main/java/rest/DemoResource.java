package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BoatDTO;
import dtos.OwnerDTO;
import entities.User;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import facades.UserFacade;
import utils.EMF_Creator;
import utils.SetupTestUsers;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class DemoResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private final UserFacade userFacade = UserFacade.getUserFacade(EMF);
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("alle")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    //USER 1
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String getAllOwners() {
        try {
            List<OwnerDTO> ownerDTOS = userFacade.getAllOwners();
            return gson.toJson(ownerDTOS);
        }catch(WebApplicationException e){
            String errorString = "{\"code\": " + e.getResponse().getStatus() + ", \"message\": \"" + e.getMessage() + "\"}";
            return errorString;
        }
    }

    @GET
    @Path("/populateOwners")
    @Produces(MediaType.APPLICATION_JSON)
    public String populateTestOwners(){
        SetupTestUsers.setupOwners();
        return "You have been populated";
    }

    @Path("/{id}/boats")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getBoatsBySpecificHarbour(@PathParam("id") String id) {
        try {
            List<BoatDTO> list = userFacade.getHarbour(id);
            return gson.toJson(list);
        } catch (WebApplicationException ex) {
            String errorString = "{\"code\": " + ex.getResponse().getStatus() + ", \"message\": \"" + ex.getMessage() + "\"}";
            return errorString;
        }
    }
    @GET
    @Path("/addBoatToOwner")
    @Produces(MediaType.APPLICATION_JSON)
    public String addBoatToOwner(){
        SetupTestUsers.addBoatOwner();
        return "You have been boated";
    }

    //Henter en båd med det id som bliver givet med som parameter i url'en
    @GET
    @Path("/getOwnersByBoatId/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOwnersByBoatId(@PathParam("id") String id){
        List<OwnerDTO> owners = userFacade.getOwnersByBoatId(id);
        return gson.toJson(owners);
    }

    @GET
    @Path("/addBoatToHarbour")
    @Produces(MediaType.APPLICATION_JSON)
    public String addBoatToHarbour(){
        SetupTestUsers.addBoatToHarbour();
        return "You have been boated to harbour";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createBoat")
    public String createBoat(String jsonBoat){
        try {
            BoatDTO boatDTO = userFacade.createBoat(jsonBoat);
            return gson.toJson(boatDTO);
        }catch(WebApplicationException e){
            throw new WebApplicationException(e.getMessage());
        }
    }
}
//step 1 populateOwners
//step 2 addBoatToOwner
//step 3 addBoatToHarbour
//step 4 /id/boats