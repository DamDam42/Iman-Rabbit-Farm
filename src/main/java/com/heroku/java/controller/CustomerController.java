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
    
    @GetMapping("/customer")
    public String customerRegister() {
        return "account/customerRegister";
    }

    @PostMapping("/customer")
    public String customerRegister(@ModelAttribute("customer") customer customerRegister) {

        try{
            Connection connection = dataSource.getConnection();
                
                String custName = customerRegister.getCustName();
                String custPassword = customerRegister.getCustPassword();
                String custPhonenum = customerRegister.getCustPhoneNum();
                String custAddress = customerRegister.getCustAddress();
                String custEmail = customerRegister.getCustEmail();
                
                String sql = "INSERT INTO public.customer VALUES (?,?,?,?,?);";
                final var statement = connection.prepareStatement(sql);
                
                statement.setString(1, custName);
                statement.setString(2, custPassword); 
                statement.setString(3, custPhonenum);
                statement.setString(4, custAddress);
                statement.setString(5, custEmail);
                
                
                
               
                statement.executeUpdate();
                
                System.out.println("guest  name : " + custName);
                // System.out.println("type : "+protype);
                // System.out.println("product price : RM"+proprice);
                // System.out.println("proimg: "+proimgs.getBytes());

                connection.close();
            

        } catch (SQLException e) {
            System.out.println("gay e" + e);
            return "redirect:/index";
        }
        return "redirect:/index2";
    }
}
