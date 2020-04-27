package com.hateoas.rest.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hateoas.rest.db.MySqlConnection;
import com.hateoas.rest.model.Car;

@Service
@Transactional
public class CarService {
  
  @Autowired
  private MySqlConnection MySqlConnection;
  
  public List<Car> listAll() throws Exception {
    List<Car> cars = new ArrayList<Car>();
    Connection conn = MySqlConnection.connectingDB();
    String sql = "SELECT * FROM cars";
    try {
      
      Statement stmt = conn.createStatement();
      ResultSet res = stmt.executeQuery(sql);
      
      while(res.next()) {
        Car car = new Car(
          res.getInt("id"), res.getString("name"), res.getString("manufacture_name"),
          res.getString("model"), res.getInt("manufacturing_year"), res.getString("color")
        );
        cars.add(car);
      }
      conn.close();
      if(cars.isEmpty()) {
        throw new Exception("No cars found");
      }
    }
    catch (SQLException soe) {
      System.out.println("SQLE: "+soe);
    }
    
    return cars;
  }
   
  public Car save(Car car) {
    Connection conn = MySqlConnection.connectingDB();
    try {
      String query = " INSERT INTO cars\n" + 
      "(name, manufacture_name, model, manufacturing_year, color)"
        + " values (?, ?, ?, ?, ?)";
      PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      preparedStmt.setString (1, car.getName());
      preparedStmt.setString (2, car.getManufactureName());
      preparedStmt.setString(3, car.getModel());
      preparedStmt.setInt (4, car.getManufacturingYear());
      preparedStmt.setString (5, car.getColor());
      
      preparedStmt.execute();
      
      ResultSet rs = preparedStmt.getGeneratedKeys();
      if(rs.next())
        car.setId(rs.getInt(1));
      
      conn.close();
    }
    catch (SQLException soe) {
      System.out.println("SQLE: "+soe);
    }
    return car;
  }
  
  public Car update(Map<String, Object> parameterMap, Integer id) throws Exception {
    Connection conn = MySqlConnection.connectingDB();
    String updateList = prepareUpdateList(parameterMap);
    Car car = null;
    try {
      String query = " UPDATE cars SET\n"+updateList+" WHERE id="+id;
      PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      
      preparedStmt.execute();
      conn.close();
      car = getById(id);
    }
    catch (SQLException soe) {
      System.out.println("SQLE: "+soe);
    }
    
    return car;
  }
   
  public Car getById(int id) throws Exception {
    Car car = null;
    Connection conn = MySqlConnection.connectingDB();
    String sql = "SELECT * FROM cars WHERE id="+id;
    try {
      
      Statement stmt = conn.createStatement();
      ResultSet res = stmt.executeQuery(sql);
      
      while(res.next()) {
        car = new Car(
          res.getInt("id"), res.getString("name"), res.getString("manufacture_name"),
          res.getString("model"), res.getInt("manufacturing_year"), res.getString("color")
        );
      }
      conn.close();
      if(null == car) {
        throw new Exception("No car found");
      }
    }
    catch (SQLException soe) {
      System.out.println("SQLE: "+soe);
    } 
    
    return car;
  }
  
  public List<Car> getByParams(HashMap<String, Object> parameterMap) throws Exception {
    List<Car> cars = new ArrayList<Car>();
    Connection conn = MySqlConnection.connectingDB();
    String filterList = prepareFilterList(parameterMap);
    String sql = "SELECT * FROM cars WHERE "+ filterList;
    try {
      
      Statement stmt = conn.createStatement();
      ResultSet res = stmt.executeQuery(sql);
      
      while(res.next()) {
        Car car = new Car(
          res.getInt("id"), res.getString("name"), res.getString("manufacture_name"),
          res.getString("model"), res.getInt("manufacturing_year"), res.getString("color")
        );
        cars.add(car);
      }
      if(cars.isEmpty()) {
        throw new Exception("No cars found");
      }
    }
    catch (SQLException soe) {
      System.out.println("SQLE: "+soe);
    }
    
    return cars;
  }
  
  public void deleteById(int id) {
    Connection conn = MySqlConnection.connectingDB();
    String query = "DELETE FROM cars WHERE id = ?";
    try {
      PreparedStatement preparedStmt = conn.prepareStatement(query);
      preparedStmt.setInt(1, id);
      
      preparedStmt.execute();
      conn.close();
    }
    catch (SQLException soe) {
      System.out.println("SQLE: "+soe);
    }
    
  }
  
  private String prepareFilterList(HashMap<String, Object> parameterMap) {
    String filterList = "";
    int i=0;
    for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      if (value != null ) {
        if(i > 0) {
          filterList+=" AND ";
        }
        filterList+=key+"= '"+value+"'";
      }
      i++;
    }
    return filterList;
  }
  
  private String prepareUpdateList(Map<String, Object> parameterMap) {
    String updateList = "";
    int i=0;
    for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      if (value != null ) {
        if(i > 0) {
          updateList+=" ,";
        }
        updateList+=key+"= '"+value+"'";
      }
      i++;
    }
    return updateList;
  }
}
