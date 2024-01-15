package com.group.libraryapp.controller.user;

import com.group.libraryapp.domain.user.Fruit;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import com.group.libraryapp.service.fruit.FruitService;
import com.group.libraryapp.service.user.UserService;
import com.group.libraryapp.service.user.UserServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final FruitService fruitService;

    //2. 실제 작성한 User 클래스에 인스턴스가 생겨 저장되게 구현
    //private final List<User> users = new ArrayList<>();

    //private final UserService userService = new UserService();
    //@Autowired
    private final UserServiceV2 userService;
    //private UserService userService;
    //private final JdbcTemplate jdbcTemplate;

/*    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }*/


/*    public UserController(JdbcTemplate jdbcTemplate){
        //this.jdbcTemplate = jdbcTemplate;
        this.userService = new UserService(jdbcTemplate);
    }*/
    public UserController(UserServiceV2 userService, @Qualifier("main") FruitService fruitService){
        //필요한 UserService를 스프링 빈으로 등록해줬으니 바로 가져올 수 있음
        this.userService = userService;
        this.fruitService = fruitService;
    }


    //생성 API
    @PostMapping("/user")
    public void saveUser(@RequestBody UserCreateRequest request) throws IllegalAccessException {
        //user 저장 구현
        //users.add(new User(request.getName(), request.getAge()));
        //users에 새로운 유저를 추가
        //유저를 만들 때는 API를 통해 들어온 이름과 나이를 집어넣어주자

       /* String sql = "INSERT INTO user(name, age) VALUES(?, ?)";
        jdbcTemplate.update(sql, request.getName(), request.getAge());*/
        userService.saveUser(request);
    }

    @GetMapping("/fruit")
    public Fruit fruit(){
        return new Fruit("딸기", 7000);
    }
    //조회 API
    @GetMapping("/user")
    public List<UserResponse> getUsers(){

        //List<UserResponse> responses = new ArrayList<>();
        //for(int i = 0; i < users.size(); i++){
        //    responses.add(new UserResponse(i+1, users.get(i)));
        //    // responses.add(new UserResponse(i+1, users.get(i).getName(), users.get(i).getAge()));
        //}

        //return responses;

       /* String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new RowMapper<UserResponse>() {
            @Override
            public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                return new UserResponse(id, name, age);
            }
        });*/
        return userService.getUsers();
    }

    @PutMapping("/user")
    public void updateUser(@RequestBody UserUpdateRequest request){
   /*     //데이터가 존재하는 지 확인해야하므로 조회용 SQL작성
        String readSQL = "SELECT * FROM user WHERE id = ?";
        //jdbcTemplate.query(readSQL, (rs, rowNum)-> 0, request.getId());
        //조회 후 결과가 있으면 무조건 0을 반환하도록 함
        //? 에 값을 넣어주기 위해 api를 타고 들어온 request.getId()(아이디)를 가져옴
        //jdbcTemplate.query(readSQL, (rs, rowNum)-> 0, request.getId()); 이 전체가 list
        //만약 우리가 찾는 유저가 있으면 0이 담긴  list가 나올 것이고, 아니면 비어있는 list가 나올 것임
        boolean isUserNotExist = jdbcTemplate.query(readSQL, (rs, rowNum)-> 0, request.getId()).isEmpty();
        //isUserNotExist 리스트가 존재하지 않는 경우(.isEmpty())는
        //조회결과가 비어있는 경우(0이 없는 경우) : true가 나올 것

        if(isUserNotExist){
           throw new IllegalArgumentException();
        }

        //특정 아이디를 가진 유저가 특정 이름으로 변경되어야 함
        String sql = "UPDATE user SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, request.getName(), request.getId());*/

        //userService.updateUser(jdbcTemplate, request);
        userService.updateUser(request);
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestParam String name){
        //쿼리사용 : RequestParam
        //String name이라는 키를 가진 query가 name으로 들어오고

        /*String readSql =  "SELECT * FROM user WHERE name = ?";
        boolean isUserNotExist = jdbcTemplate.query(readSql, (rs, rowNum) -> 0, name).isEmpty();;
        if(isUserNotExist){
            throw new IllegalArgumentException();
        }

        String sql =  "DELETE FROM user WHERE name = ?";
        jdbcTemplate.update(sql, name);*/
        userService.deleteUser(name);
    }

    //api안에서 에외를 던져보자(실제로 데이터가 존재하는지 확인 후)
    @GetMapping("/user/error-test")
    public void errorTest(){
        throw new IllegalArgumentException();
        //postman 예외를 던지는 API 호출) 500 Internal Server Error응답
        //서버 내부에 문제가 있어서 요청한 것이 실패함
        //함수가 정상 종료되지않고 그 안에서 예외가 발생
        //spring이 이 예외를 잡아서 500 Internal Server Error를 내준 것
    }
}


