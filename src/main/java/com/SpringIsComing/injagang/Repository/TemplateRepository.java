package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.EssayTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<EssayTemplate, String> {

}
