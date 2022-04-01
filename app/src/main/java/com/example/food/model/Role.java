package com.example.food.model;

import com.example.food.util.ERole;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private String role;
    private ERole name;

//    public Role() {
//    }
//
//    public Role(String role, ERole name) {
//        this.role = role;
//        this.name = name;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
//    public ERole getName() {
//        return name;
//    }
//
//    public void setName(ERole name) {
//        this.name = name;
//    }
}
