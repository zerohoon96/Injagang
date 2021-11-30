package com.SpringIsComing.injagang.Repository;

import com.SpringIsComing.injagang.Entity.EssayTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateRepository extends JpaRepository<EssayTemplate, Long> {

    List<EssayTemplate> findEssayTemplatesByChecked(boolean checked);
}
