package dtos;

import entities.Boat;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

public class BoatDTO {

    private Integer id;
    private String brand;
    private String make;
    private String name;
    private String image;

    public BoatDTO(String brand, String make, String name, String image) {
        this.brand = brand;
        this.make = make;
        this.name = name;
        this.image = image;
    }

    public BoatDTO(Boat boat) {
        this.id = boat.getId();
        this.brand = boat.getBrand();
        this.make = boat.getMake();
        this.name = boat.getName();
        this.image = boat.getImage();
    }
    public static List<BoatDTO> getDtos(List<Boat> boats){
        List<BoatDTO> boatDTOS = new ArrayList();
        boats.forEach(boat->boatDTOS.add(new BoatDTO(boat)));
        return boatDTOS;
    }


    public BoatDTO() {
    }

    public Integer getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getMake() {
        return make;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
