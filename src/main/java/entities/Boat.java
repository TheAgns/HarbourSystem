package entities;

import dtos.BoatDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "boat")
@Entity
public class Boat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "brand")
    private String brand;

    @Column(name = "make")
    private String make;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    private Harbour harbour;

    @ManyToMany
    //private List<Harbour> harbours;
    private List<Owner> owners;

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }

    public Boat(String brand, String make, String name, String image) {
        this.brand = brand;
        this.make = make;
        this.name = name;
        this.image = image;
        this.owners = new ArrayList<Owner>();
    }

    public Boat(BoatDTO boatDTO) {
        this.brand = brand;
        this.make = make;
        this.name = name;
        this.image = image;
        this.owners = new ArrayList<Owner>();
    }


    public Boat() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Harbour getHarbour() {
        return harbour;
    }

    public void setHarbour(Harbour harbour) {
        this.harbour = harbour;
    }
}