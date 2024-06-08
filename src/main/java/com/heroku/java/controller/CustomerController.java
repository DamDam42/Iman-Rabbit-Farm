package com.heroku.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.heroku.java.model.*;

import jakarta.servlet.http.HttpSession;

import java.sql.*;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Map;

import java.util.List;

@Controller
public class CustomerController {
    private final DataSource dataSource;

    @Autowired
    public CustomerController(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @GetMapping("/customerRegister")
    public String customerRegister() {
        return "account/index";
    }

    @PostMapping("/customerRegister")
    public String customerRegister(@ModelAttribute("customerRegister") customer customer) {

        try {
            Connection connection = dataSource.getConnection();
            String sql = "INSERT INTO public.customer(custname,custemail,custphonenum,custaddress,custpassword) VALUES (?, ?, ?, ?, ?);";
            final var statement = connection.prepareStatement(sql);

            String name = customer.getCustName();
            String email = customer.getCustEmail();
            String phonenum = customer.getCustPhoneNum();
            String address = customer.getCustAddress();
            String password = customer.getCustPassword();
            


            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phonenum);
            statement.setString(4, address);
            statement.setString(5, password);
           

            System.out.println("guest  name : " + name);
            // System.out.println("type : "+protype);
            // System.out.println("product price : RM"+proprice);
            // System.out.println("proimg: "+proimgs.getBytes());

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/index";
        }
        return "redirect:/index";
    }