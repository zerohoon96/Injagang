package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.EssayContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EssayContentRepository extends JpaRepository<EssayContent, Long> {
}
