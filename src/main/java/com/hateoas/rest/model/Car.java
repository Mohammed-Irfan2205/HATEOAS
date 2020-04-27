package com.hateoas.rest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cars")
public class Car {

    public Car() {

    }

    public Car(Integer id, String name, String manufactureName, String model, Integer manufacturingYear, String color) {
        super();
        this.id = id;
        this.name = name;
        this.manufactureName = manufactureName;
        this.model = model;
        this.manufacturingYear = manufacturingYear;
        this.color = color;
    }
 
    private Integer id;
    private String name;
    private String manufactureName;
    private String model;
    private Integer manufacturingYear;
    private String color;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getManufactureName() {
      return manufactureName;
    }

    public void setManufactureName(String manufactureName) {
      this.manufactureName = manufactureName;
    }

    public String getModel() {
      return model;
    }

    public void setModel(String model) {
      this.model = model;
    }

    public Integer getManufacturingYear() {
      return manufacturingYear;
    }

    public void setManufacturingYear(Integer manufacturingYear) {
      this.manufacturingYear = manufacturingYear;
    }

    public String getColor() {
      return color;
    }

    public void setColor(String color) {
      this.color = color;
    }

    @Override
    public String toString() {
        return "Cars [id=" + id + ", name=" + name + ", manufactureName=" + manufactureName + ", model=" + model + ", manufacturingYear=" + manufacturingYear
        + ", color=" + color + "]";
    }
}
