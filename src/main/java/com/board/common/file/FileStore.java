package com.board.common.file;

import com.board.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//이미지 파일 저장 객체
@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();

        String storeFilename = createStoreFileName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeFilename)));
        return new UploadFile(originalFilename, storeFilename);
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                UploadFile uploadFile = storeFile(multipartFile);
                storeFileResult.add(uploadFile);
            }
        }
        return storeFileResult;
    }

    public List<UploadFile> updateFile(List<UploadFile> existFiles, List<MultipartFile> multipartFiles) throws IOException {
        for (UploadFile existFile : existFiles) {
            deleteFile(existFile);
        }
        return storeFiles(multipartFiles);
    }
    public void deleteFile(UploadFile file) {
        String storeFileName = file.getStoreFileName();
        File deleteFile = new File(getFullPath(storeFileName));
        deleteFile.delete();
    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();

        //확장자
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.indexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
