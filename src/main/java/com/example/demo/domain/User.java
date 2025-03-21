package com.example.demo.domain;

import jakarta.persistence.*; //jpa 엔터티, pk, column등
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class User {

    //Getter & Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 자동 증가
    private Long id;


    @Column(nullable = false, unique = true) // username 필수, 중복불가
    private String username;

    @Column(nullable = false)
    private String password;
    
    //기본 생성자 (JPA 필수)
    public User() {}

    //생성자
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

}

