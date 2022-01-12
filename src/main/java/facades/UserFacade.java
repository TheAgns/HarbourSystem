package facades;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.MalformedJsonException;
import dtos.BoatDTO;
import dtos.OwnerDTO;
import dtos.RenameMeDTO;
import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.WebApplicationException;

import security.errorhandling.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    //US 1 As a user I would like to see all owners
    public List<OwnerDTO> getAllOwners() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Owner> query = em.createQuery("SELECT o FROM Owner o", Owner.class);
            List<Owner> owners = query.getResultList();
            ArrayList<OwnerDTO> ownerDTOs = new ArrayList<>();
            for (Owner owner : owners) {
                ownerDTOs.add(new OwnerDTO(owner));
            }
            return ownerDTOs;
        } catch (WebApplicationException e) {
            throw new WebApplicationException("Doesn't work", 500);
        }
    }

   /*
    public List<BoatDTO> getBoatsBySpecificHarbour(String id) {
        {
            EntityManager em = emf.createEntityManager();
            int harbourId = Integer.parseInt(id);
            try {
                TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b JOIN b.harbour h WHERE h.id = :id", Boat.class);
                query.setParameter("id", harbourId);
                List<Boat> boats = query.getResultList();
                System.out.println(boats.size());
                ArrayList<BoatDTO> boatDTOs = new ArrayList<>();
                for (Boat boat : boats) {
                    boatDTOs.add(new BoatDTO(boat));
                }
                return boatDTOs;
            } catch (RuntimeException ex) {
                throw new WebApplicationException("Internal Server Problem. We are sorry for the inconvenience", 500);
            } finally {
                em.close();
            }
        }
    }*/

    //US 2 As a user I would like to see all boats belonging in a specific harbour
    public List<BoatDTO> getHarbour(String id){
        int testId = Integer.parseInt(id);
        EntityManager em = emf.createEntityManager();
     Harbour harbour;
        try {
            harbour = em.find(Harbour.class, testId);
        } finally {
            em.close();
        }
        List<Boat> boats = harbour.getBoats();
        List<BoatDTO> boatDTOS = new ArrayList<>();
        for (Boat boat: boats) {
            BoatDTO boatDTO = new BoatDTO(boat);
            boatDTOS.add(boatDTO);
        }
        return boatDTOS;
    }
    //US 3 As a user I would like to see all boats belonging in a specific harbour
    public List<OwnerDTO> getOwnersByBoatId(String id){
        int intId = Integer.parseInt(id);
        EntityManager em = emf.createEntityManager();
        Boat boat;
        try {
            boat = em.find(Boat.class, intId);
        } finally {
            em.close();
        }
        List<Owner> owners = boat.getOwners();
        List<OwnerDTO> ownerDTOS = new ArrayList<>();
        for (Owner owner: owners) {
            OwnerDTO ownerDTO = new OwnerDTO(owner);
            ownerDTOS.add(ownerDTO);
        }
        return ownerDTOS;
    }

    //US 4 As an admin I would like to create a new boat
    public BoatDTO createBoat(String jsonBoat){
        EntityManager em = emf.createEntityManager();
        //Boat boat = new Boat(boatDTO.getBrand(), boatDTO.getMake(), boatDTO.getName(), boatDTO.getImage());
        String brand;
        String make;
        String name;
        String image;
        try {
            JsonObject json = JsonParser.parseString(jsonBoat).getAsJsonObject();
            brand = json.get("brand").getAsString();
            make = json.get("make").getAsString();
            name = json.get("name").getAsString();
            image = json.get("image").getAsString();
        }catch(WebApplicationException e){
            throw new WebApplicationException("Not correct json");
        }
        try {
            Boat boat = new Boat(brand,make,name,image);
            em.getTransaction().begin();
            em.persist(boat);
            em.getTransaction().commit();
            return new BoatDTO(boat);
        } catch (Exception e) {
            throw new WebApplicationException("Couldn't create a new boat");
        } finally {
            em.close();
        }
    }

}
