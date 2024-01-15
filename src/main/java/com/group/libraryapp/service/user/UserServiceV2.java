package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {

    private final UserRepository userRepository;

    public UserServiceV2(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //아래 있는 함수가 시작될 떄 start  transaction을 시작해줌
    //함수가 예외없이 잘 끝났다면 commit
    //혹시라도 문제가 있다면 rollback
    @Transactional
    public void saveUser(UserCreateRequest request) throws IllegalAccessException {
      userRepository.save(new User(request.getName(), request.getAge()));
      //User u = , u.getId(); //save되고 난 후 User에는 id가 들어 있다.
      //throw new IllegalArgumentException();
    }

    //저장은 성공하지만 예외가 발생하여 저장 자체가 롤백되어 반영이 안될 것임

    //조회기능(SELECT 쿼리만 사용시, readOnly옵션 사용 가능)
    // 저장, 업데이트,삭제 등 데이터 변경을 위한 불필요한 기능이 빠지기떄문에 성능적 이점 존재
    @Transactional(readOnly = true)
    public List<UserResponse> getUsers(){
       // return userRepository.findAll().stream()
       //         .map(user -> new UserResponse(user.getId(), user.getName(), user.getAge()))
       //         .collect(Collectors.toList());

        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());

        //findAll() : 모든 데이터를 가져옴 -> select * from user;

    }

    //유저 업데이트 기능
    @Transactional
    public void updateUser(UserUpdateRequest request){
        //select * from user where id = ?
        //결과 : Optional<User>
        User user = userRepository.findById(request.getId())
                .orElseThrow(IllegalArgumentException::new);

        //API를 통해 들어온 이름을 변경
        user.updateName(request.getName());
        //userRepository.save(user);
        //유저의 이름이 바뀐 걸 확인하고 바뀐 걸 기준으로 업데이트 쿼리가 수행됨

        //영속성 컨텍스트가 시작되고, 유저가 변경된 것을 감지하고 자동으로 업데이트 해 줌,
        //save 명시 X
    }

    //유저 삭제 기능
    @Transactional
    public void deleteUser(String name){
        //select * from user where name = ?
        User user = userRepository.findByName(name)
                .orElseThrow(IllegalArgumentException::new);
        //유저가 없으면 null이, 유저가 있으면 유저가 들어가 있는 채로 나옴
//        if(user == null){
//            throw new IllegalArgumentException();
//        }
        userRepository.delete(user);
    }

    public void deleteUserHistory(){
        User user =  userRepository.findByName("ABC")
                .orElseThrow(IllegalArgumentException::new);
        user.removeOneHistory();
    }
}
