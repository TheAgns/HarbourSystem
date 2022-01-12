package utils;


import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

  public static void setupOwners(){
    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();

    Owner owner1 = new Owner("test1","Lars1",11111111);
    Owner owner2 = new Owner("test2","Toke2",22222222);
    Owner owner3 = new Owner("test3","Bjarke3",33333333);
    em.getTransaction().begin();
    em.persist(owner1);
    em.persist(owner2);
    em.persist(owner3);
    em.getTransaction().commit();


  }
  public static void addBoatOwner() {
    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();

    Boat boat11 = new Boat("BMW","i8","hurtighurtig","wwww.hurtigebaade.dk");
    Boat boat22 = new Boat("AUDI","a8","speed baad","wwww.hurtigebaade.dk");
    Owner ownerBoat1 = new Owner("jeg har en baad","testvej",11111111);
    Owner ownerBoat2 = new Owner("jeg har begge baade","lugevej",22222222);
    ownerBoat1.addBoatToOwner(boat11);
    ownerBoat2.addBoatToOwner(boat22);
    ownerBoat2.addBoatToOwner(boat11);
    em.getTransaction().begin();

    em.persist(ownerBoat1);
    em.persist(ownerBoat2);
    em.getTransaction().commit();

  }

  public static void addBoatToHarbour(){
    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();

    Harbour harbour1 = new Harbour("Mosevej","he",100);
    Harbour harbour2 = new Harbour("Nejvej","hejmeddaj",200);
    Harbour harbour3 = new Harbour("Javej","NejtilDigmedmig",400);

    Boat boat1 = new Boat("Audi","a","hurtighurtig","www.baad.dk");
    Boat boat2 = new Boat("Suzuki","b","sickspeed","www.bilbaad.dk");
    Boat boat3 = new Boat("TEST1","isa8","hurtigmegethurtig","wwww.hurrtigebaade.dk");
    Boat boat4 = new Boat("TEST2","ajk8","speed boatbaad","wwww.hurrtigebaade.dk");

    harbour1.addBoat(boat1);
    harbour1.addBoat(boat2);
    harbour3.addBoat(boat3);
    harbour2.addBoat(boat4);
    em.getTransaction().begin();
    em.persist(harbour1);
    em.persist(harbour2);
    em.persist(harbour3);
    em.getTransaction().commit();
  }

  public static void main(String[] args) {


    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    User user = new User("user", "test1");
    User admin = new User("admin", "test2");
    User both = new User("user_admin", "test3");


    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
      throw new UnsupportedOperationException("You have not changed the passwords");

    em.getTransaction().begin();
    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
    user.addRole(userRole);
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);
    em.persist(userRole);
    em.persist(adminRole);
    em.persist(user);
    em.persist(admin);
    em.persist(both);
    em.getTransaction().commit();
    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");


    //setupOwners



    //addBoatOwner


    //addBoatToHarbour




  }

}
