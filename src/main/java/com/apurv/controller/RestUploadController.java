package com.apurv.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.apurv.model.Metadata;
import com.apurv.service.FileMetadatServices;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RestUploadController {

    private final Logger logger = LoggerFactory.getLogger(RestUploadController.class);

     //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "D://demo//";

    @Autowired
	FileMetadatServices fileSer;


    // Multiple file upload
    @PostMapping("/api/upload/multi")
    public ResponseEntity<?> uploadFileMulti(
            @RequestParam("extraField") String extraField,
            @RequestParam("files") MultipartFile[] uploadfiles) {

        logger.debug("Multiple file upload!");

        String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename())
                .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));

        if (StringUtils.isEmpty(uploadedFileName)) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
            saveUploadedFiles(Arrays.asList(uploadfiles));
        } 
        catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - "
                + uploadedFileName, HttpStatus.OK);
    }
    @RequestMapping(value = "/Metadata", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	 @ResponseBody
	 public ResponseEntity<List<Metadata>> getMatadata(@RequestBody Metadata metadata1 ) {
	       
	        List<Metadata> metadata= fileSer.getAllMetadata();
	        System.out.println(metadata);
		    return new ResponseEntity<List<Metadata>>(metadata, HttpStatus.OK);
	    }
    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {
    for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; 
            }
            Date dNow = new Date( );
            SimpleDateFormat ft = 
            new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
            Metadata m=new Metadata();
            m.setFiles(file.getOriginalFilename());
            m.setCurrentTime(ft.format(dNow));
            fileSer.addMetadata(m);
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

        }

    }
}
