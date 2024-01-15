package com.group.libraryapp.temp;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;

    public PersonService(PersonRepository personRepository, AddressRepository addressRepository) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
    }

    @Transactional
    public void savePerson(){
        Person person = personRepository.save(new Person());
        Address address = addressRepository.save(new Address());
        person.setAddress(address); //각각 저장한 사람데이터와 주소데이터를 이어줌
        //person에 있는 address를 업데이트 시켜주고, 
        //personRepository.save(person); // 다시 저장해줌
        //@Transactional 붙여주고 내의 자동 변경감지 이용해서 자동 저장
        
        //이제 데이터베이스 상에서 연결된 채로 존재할 것

        //연관관계의 주인이 아닌 객체에서 setter를 사용해주자(기존: 연관관계의 주인이 자신의 필드를 수정)
        //address.setPerson(person);
        //System.out.println(address.getPerson());//null이 될 것이다(객체끼리는 한쪽만 연결된 상황).
        //: 트랜잭션이 끝나지 않았을 떄, 한 쪽만 연결해두면 반대 쪽은 알 수 없다.

        //알맞은 객체를 사용해 테이블을 연결해주었으면, 객체끼리 연결하여주자.
        //트랜잭션이 끝나기 전에 객체를 가져왔을 경우 null이 나올 것
        address.getPerson();

    }
}
