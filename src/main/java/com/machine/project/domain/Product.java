package com.machine.project.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Class describing product entity
 *
 * @author Oleg Dibrov
 */

@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name="products")
public class Product {

    /**
     * product id field
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "quantity")
    private Integer quantity;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(cost, product.cost) &&
                Objects.equals(quantity, product.quantity);
    }

    @Override
    public int hashCode() {
        return 31 * id.hashCode() + 31;
    }
}
