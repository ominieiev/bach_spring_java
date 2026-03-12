package edu.ntu.sau.javasb.controllers;


import edu.ntu.sau.javasb.service.Transforming;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class MainController {
    @Autowired
    Transforming transforming;

    @GetMapping("/")
    String welcome(Model model) {
        return "welcome";
    }

    @GetMapping("/transforming")
    String transforming(Model model) {
        return "transforming";
    }

    @PostMapping("/transforming/transform")
    public ResponseEntity<byte[]> transform(@RequestParam("file") MultipartFile file) throws IOException {


        String fileName = "result.xlsx";
        byte[] result = transforming.transformExcel(file.getInputStream());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(result);

    }


}
