package com.group.libraryapp.temp;

import javax.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    private Address address;

    public void setAddress(Address address){
        this.address = address;
        //setter하나로 객체 상에서 person도 address를 갖게 해주고,
        //address도 person을 갖게 해주자.
        this.address.setPerson(this);
        //연관관계주인을 호출할 때 한 번에 객체끼리도 양쪽으로 연결을 맺어줌
        //address.getPerson(); null값이 안나올 것
    }


}
