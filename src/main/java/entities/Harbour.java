package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "harbour")
@Entity
public class Harbour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "capacity", length = 255)
    private int capacity;

    //@OneToMany(cascade = CascadeType.PERSIST)
    @OneToMany(mappedBy = "harbour", cascade = CascadeType.PERSIST)
    private List<Boat> boatList;

    public Harbour(String name, String address, int capacity) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.boatList = new ArrayList<>();
    }


    public List<Boat> getBoats() {
        return boatList;
    }

    public Harbour() {
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getCapacity() {
        return capacity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void addBoat(Boat boat) {
        this.boatList.add(boat);
        if (boat != null){
            boat.setHarbour(this);
        }
    }
}