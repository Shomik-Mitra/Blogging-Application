package com.blogging.app.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

         //FileName
        String name=file.getOriginalFilename();//abc.png

        //randomId
        String randomId= UUID.randomUUID().toString();
        // random Name generating
        String fileName1=randomId.concat(name.substring(name.lastIndexOf(".")));

        //fullPath
        String filePath=path+File.separator+fileName1;

        //create folder if not created
        File f=new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName1;
    }
}
