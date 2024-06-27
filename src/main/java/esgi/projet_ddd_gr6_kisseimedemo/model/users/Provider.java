package esgi.projet_ddd_gr6_kisseimedemo.model.users;

import esgi.projet_ddd_gr6_kisseimedemo.model.delivery.Trip;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Provider {
    private final String name;
    private List<Trip> listOfTrips = new ArrayList<>();


    public Provider(String name) {
        this.name = name;
    }
}
