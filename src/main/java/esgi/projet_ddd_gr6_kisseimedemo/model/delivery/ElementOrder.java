package esgi.projet_ddd_gr6_kisseimedemo.model.delivery;

import lombok.Getter;

@Getter
public class ElementOrder {
    private final String description;
    private final double weight;

    public ElementOrder(String description, double weight) {
        this.description = description;
        this.weight = weight;
    }
}
