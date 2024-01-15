package com.group.libraryapp.dto.user.request;

public class UserCreateRequest {
    private String name;
    private Integer age; // Integer : null 을 표현할 수 있는 자료형 int : null을 표현할 수 없는 기본자료형

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
