package com.hateoas.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hateoas.rest.model.Car;
import com.hateoas.rest.service.CarService;

@RestController
@Validated
@RequestMapping("/cars")
public class CarController 
{
    @Autowired
    private CarService carService;
    
    @RequestMapping(value = "/", produces = "application/json", method = RequestMethod.GET)
    public List<Car> getCars() throws Exception 
    {
        return carService.listAll();
    }
    
    @RequestMapping(value = "/{id}", produces = "application/json", method = RequestMethod.GET)
    public Car getCar(@PathVariable("id") Integer id) throws Exception 
    {
        return carService.getById(id);
    }
    
    @RequestMapping(value = "/", consumes = "application/json", produces = "application/json", method = RequestMethod.POST)
    public Car addCar(@RequestBody Map<String, Object> payload) 
                 throws Exception 
    {       
      Car car = getObjectFromPayLoad(payload, Car.class);
      car = carService.save(car);
       
      return car;
    }
    
    @RequestMapping(value= "/{id}", consumes = "application/json", produces = "application/json", method = RequestMethod.PUT)
    public Car updateCar(@RequestBody Map<String, Object> payload, @PathVariable("id") Integer id) 
                 throws Exception 
    {       
      Car car = carService.update(payload, id);
      return car;
    }
    
    @RequestMapping(value="/filter", produces = "application/json", method = RequestMethod.GET)
    public List<Car> getCarsByParams(@RequestParam(value = "id",required = false) Integer id,
      @RequestParam(value = "name",required = false)  String name,
      @RequestParam(value = "manufacture_name",required = false)  String manufactureName,
      @RequestParam(value = "model",required = false)  String model,
      @RequestParam(value = "manufacturing_year",required = false)  Integer manufacturingYear,
      @RequestParam(value = "color",required = false)  String color) throws Exception 
    {
      HashMap<String, Object> parameterMap = new HashMap<String, Object>();
      parameterMap.put("id", id);
      parameterMap.put("name", name);  
      parameterMap.put("manufacture_name", manufactureName);  
      parameterMap.put("model", model);  
      parameterMap.put("manufacturing_year", manufacturingYear);  
      parameterMap.put("color", color);  
      return carService.getByParams(parameterMap);
    }
    
    @RequestMapping(value="/{id}", produces = "application/json", method = RequestMethod.DELETE)
    public String deleteCar(@PathVariable("id") Integer id) 
    {
        carService.deleteById(id);
        return "Successfully deleted Car with id ="+id;
    }
    
    public <T> T getObjectFromPayLoad(Map<String, Object> payload, Class<T> clazz) {
    ObjectMapper objectMapper = new ObjectMapper();
    T clientCompany = objectMapper.convertValue(payload, clazz);
    return clientCompany;
  }
}
