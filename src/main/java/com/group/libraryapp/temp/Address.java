package com.group.libraryapp.temp;

import javax.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    private String city;

    private String street;

    @OneToOne(mappedBy = "address")//1대1연관관계를 맺어줌 , 연관관계의 주인이 아님을 명시해줌
    private Person person;

    public void setPerson(Person person){
        this.person = person;
    }

    public Person getPerson() {
        return this.person;
    }
}
