package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.Essay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EssayRepository extends JpaRepository<Essay, Long> {
    //extends로 안써도 여러가지 기능을쓸수있음
}
