package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "owner")
@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "phone", length = 255)
    private int phone;

   @ManyToMany(mappedBy = "owners", cascade = CascadeType.PERSIST)
    private List<Boat> boats;


    public Owner(String name, String address, int phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.boats = new ArrayList<>();
    }

    public Owner() {
    }
    public void addBoatToOwner(Boat boat){
        if (boat != null){
            this.boats.add(boat);
            boat.getOwners().add(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}