package com.heroku.java.controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.heroku.java.model.customer;


@Controller
public class CustomerController {
    private final DataSource dataSource;

    @Autowired
    public CustomerController(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @GetMapping("/customerRegister")
    public String customerRegister() {
        return "account/index2";
    }

    @PostMapping("/customerRegister")
    public String customerRegister(@ModelAttribute("customerRegister") customer customer) {

        try{
            try (Connection connection = dataSource.getConnection()) {
                String sql = "INSERT INTO public.customer(custname,custpassword,custphonenum,custadress,custemail) VALUES (?,?,?,?,?);";
                final var statement = connection.prepareStatement(sql);
                
                String custname = customer.getCustName();
                String custpassword = customer.getCustPassword();
                String custphonenum = customer.getCustPhoneNum();
                String custaddress = customer.getCustAddress();
                String custemail = customer.getCustEmail();
                
                
                
                statement.setString(1, custname);
                statement.setString(2, custpassword);
                statement.setString(3, custphonenum); 
                statement.setString(4, custaddress);
                statement.setString(5, custemail);
                
                
               
                statement.executeUpdate();
                
                System.out.println("guest  name : " + custname);
                // System.out.println("type : "+protype);
                // System.out.println("product price : RM"+proprice);
                // System.out.println("proimg: "+proimgs.getBytes());

                connection.close();
            }

        } catch (SQLException e) {
            System.out.println("gay e" + e);
            return "redirect:/index";
        }
        return "redirect:/index2";
    }
}
