package com.SpringIsComing.injagang.Controller;

import com.SpringIsComing.injagang.DTO.UploadResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FileController {

    @Value("${com.SpringIsComing.injagang.upload.path}")
    private String uploadPath;

    //파일 업로드용 컨트롤러
    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDTO>> UploadFile(MultipartFile[] uploadFiles) {

        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        for (MultipartFile uploadFile : uploadFiles) {
            //동영상만 업로드 가능
            if (uploadFile.getContentType().startsWith("video") == false) {
                log.warn("this file is not video type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN); //403 Forbidden
            }

            //IE나 Edge의 경우 filename 처리
            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            //log.info("fileName: " + fileName);

            //날짜 폴더 생성
            String folderPath = makeFolder();
            String uuid = UUID.randomUUID().toString();
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(saveName);

            try{
                //원본 파일 저장
                uploadFile.transferTo(savePath);
                resultDTOList.add(new UploadResultDTO(fileName, uuid, folderPath));
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
    }

    //업로드 비디오 출력하기
    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName) {
        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");
            String srcFilePath = uploadPath + File.separator + srcFileName;

            File file = new File(srcFilePath);
            log.info("file: " + file);

            HttpHeaders header = new HttpHeaders();

            //MIME타입 처리
            header.add("Content-Type", Files.probeContentType(file.toPath()));
            //파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    private String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        //make folder
        File uploadPathFolder = new File(uploadPath, folderPath);

        if (uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }
}
