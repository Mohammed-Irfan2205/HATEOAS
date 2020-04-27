package com.hateoas.rest.db;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

import org.springframework.stereotype.Service;

@Service
public class MySqlConnection {
  
  public static final String driver = "com.mysql.jdbc.Driver";

  public Connection connectingDB()
  {
  Connection con = null;;
    try{
  
    //Load the driver class dynamically.
    Driver d = (Driver)Class.forName(driver).newInstance();
    DriverManager.registerDriver(d);
  
  
    //Path of the db file.
    String url = "jdbc:mysql://localhost:3306/HATEOAS?useSSL=false";
    con = DriverManager.getConnection(url, "root", "root");
    }
    catch(Exception e)
    {
      System.out.println("Error loading database driver: " + e.toString());
    }
    return con;
  }

}
