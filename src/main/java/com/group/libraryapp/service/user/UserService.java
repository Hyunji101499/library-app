package com.group.libraryapp.service.user;

import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.repository.user.UserJdbcRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Service
public class UserService {

    private final UserJdbcRepository userRepository;
    //private final UserRepository userRepository = new UserRepository(jdbcTemplate);

    //생성자에서 jdbcTemplate을 받음
/*
    public UserService(JdbcTemplate jdbcTemplate) {
        userRepository = new UserRepository(jdbcTemplate);
        //UserRepository를 만들어주며 생성자에서 받은 jdbcTemplate을 그대로 넘겨줌
    }
*/
    public UserService(UserJdbcRepository userRepository) {
     this.userRepository = userRepository;
    }

    //public void updateUser(JdbcTemplate jdbcTemplate, UserUpdateRequest request) {
    public void saveUser(UserCreateRequest request){
        /*String sql = "INSERT INTO user(name, age) VALUES(?, ?)";
        jdbcTemplate.update(sql, request.getName(), request.getAge());*/
        userRepository.saveUser(request.getName(), request.getAge());
    }

    public List<UserResponse> getUsers(){
        return userRepository.getUsers();
    }

    public void updateUser(UserUpdateRequest request) {
        //Contoroller 같은 경우 @RequestBody 사용
        //Contoroller가 @RequestBody를 통해 User 객체로 변환한 것을 그냥 받아줌
        // UserUpdateRequest userUpdateRequest 어노테이션 없이 그냥 객체만 받아주면 됨

        /*String readSQL = "SELECT * FROM user WHERE id = ?";
        boolean isUserNotExist = jdbcTemplate.query(readSQL, (rs, rowNum) -> 0, request.getId()).isEmpty();*/
        /*boolean isUserNotExist = userRepository.isUserNotExist(jdbcTemplate, request.getId());*/

        //if (isUserNotExist) {
        //if (userRepository.isUserNotExist(jdbcTemplate, request.getId())){
        if (userRepository.isUserNotExist(request.getId())){
            throw new IllegalArgumentException();
        }
        /*String sql = "UPDATE user SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, request.getName(), request.getId());*/
        //userRepository.updateUserName(jdbcTemplate, request.getName(), request.getId());
        userRepository.updateUserName(request.getName(), request.getId());
    }

    public void deleteUser(String name){
        /*String readSql =  "SELECT * FROM user WHERE name = ?";
        boolean isUserNotExist = jdbcTemplate.query(readSql, (rs, rowNum) -> 0, name).isEmpty();;*/

        //if(isUserNotExist){
        if(userRepository.isUserNotExist(name)){
            throw new IllegalArgumentException();
        }

        /*String sql =  "DELETE FROM user WHERE name = ?";
        jdbcTemplate.update(sql, name);*/
        userRepository.deleteUser(name);
    }
}
