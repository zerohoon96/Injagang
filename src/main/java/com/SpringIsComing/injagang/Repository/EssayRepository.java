package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.Essay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EssayRepository extends JpaRepository<Essay, Long> {
}
