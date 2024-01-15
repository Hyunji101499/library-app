package com.group.libraryapp.repository.user;

import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserJdbcRepository {
    //실제 jdbcTemplate을 사용하는 것은 Repository뿐인데, 자꾸 매개변수로 넘겨줘야함
    //변수로 선언해주자.Repository자체가 jdbcTemplate을 가지도록
    // 초기 ) jdbcTemplate을 계속 함수를 통해 컨트롤러에서 서비스, 서비스에서 레포지토리로 전달
    // UserRepositor에 변수로 선언한 뒤 생성자를 통해 인스턴스화 하는 시점에 jdbcTemplate을 넣어줌
    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveUser(String name, Integer age){
        String sql = "INSERT INTO user(name, age) VALUES(?, ?)";
        jdbcTemplate.update(sql, name, age);
    }
    public List<UserResponse> getUsers(){
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new RowMapper<UserResponse>() {
            @Override
            public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                return new UserResponse(id, name, age);
            }
        });
    }

    //public boolean isUserNotExist(JdbcTemplate jdbcTemplate, long id){

    public boolean isUserNotExist(long id){
        //repository까지 올 경우, request쓸 경우 모든 필드를 사용하는 것이 아니므로
        // 특정필드만 되도록이면 사용해주자
        String readSQL = "SELECT * FROM user WHERE id = ?";
        return jdbcTemplate.query(readSQL, (rs, rowNum) -> 0, id).isEmpty();
    }

    public void updateUserName(String name, Long id){
        String sql = "UPDATE user SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, id);
    }

    public boolean isUserNotExist(String name){
        String readSql =  "SELECT * FROM user WHERE name = ?";
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, name).isEmpty();
    }

    public void deleteUser(String name){
        String sql =  "DELETE FROM user WHERE name = ?";
        jdbcTemplate.update(sql, name);
    }
}
