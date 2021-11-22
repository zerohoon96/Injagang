package com.SpringIsComing.injagang.service;

import com.SpringIsComing.injagang.DTO.EssayDTO;
import com.SpringIsComing.injagang.DTO.PageRequestDTO;
import com.SpringIsComing.injagang.DTO.PageResultDTO;
import com.SpringIsComing.injagang.Entity.Essay;
import com.SpringIsComing.injagang.Service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {
    @Autowired
    private BoardService service;

    @Test
    public void testEssayList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<EssayDTO, Essay> pageResultDTO = service.getEssayList(pageRequestDTO);

        System.out.println("PREV: " + pageResultDTO.isPrev());
        System.out.println("NEXT: " + pageResultDTO.isNext());
        System.out.println("TOTAL: " + pageResultDTO.getTotalPage());
        System.out.println("------------------------------------------");

        for (EssayDTO dto : pageResultDTO.getDtoList()) {
            System.out.println(dto);
        }

        System.out.println("==========================================");
        pageResultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}
