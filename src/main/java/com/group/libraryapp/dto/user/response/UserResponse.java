package com.group.libraryapp.dto.user.response;

import com.group.libraryapp.domain.user.User;

public class UserResponse {
    private long id;
    private String name;
    private Integer age;

    public UserResponse(long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public UserResponse(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.age = user.getAge();
    }


    //
    // public UserResponse(long id, String name, Integer age) {
    public UserResponse(long id, User User) {

        this.id = id;
        this.name = User.getName();
        // this.name = name;
        this.age = User.getAge();
        //this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
