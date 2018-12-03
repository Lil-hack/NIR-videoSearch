package com.mgtu.akashkin;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.mgtu.akashkin.entity.Hash;
import com.mgtu.akashkin.model.HashInfo;
import com.mgtu.akashkin.service.HashService;
import com.mgtu.akashkin.service.HashServiceImpl;
import com.pragone.jphash.image.radial.RadialHash;
import com.pragone.jphash.jpHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IPacket;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.Utils;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootApplication
public class Application {
    static final String URL_API_USERS = "http://localhost:8087/api-users/";

    static final String URL_API_USERS_GET = URL_API_USERS.concat("getAll");

    @Autowired
    private static HashService hashService;
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(Application.class, args);

            long startTime = System.currentTimeMillis();
            HashInfo hash = new HashInfo();
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL_API_USERS_GET);


            RestTemplate restTemplate = new RestTemplate();
            HashInfo[] result = restTemplate.getForObject(builder.toUriString(), HashInfo[].class);
            RadialHash    hashinputImage=   getHashFromImage("c:/test/picture/0500500.png");
for (int i=0;i<result.length;i++) {


    System.out.println("Similarity: " + jpHash.getSimilarity(hashinputImage,
         RadialHash.fromString(result[i].getHash())));
}



            long timeSpent = System.currentTimeMillis() - startTime;
            System.out.println("программа выполнялась " + timeSpent + " миллисекунд");

        } catch (Exception e) {
            logger.error("getAllError", e);

        }


    }


    private static RadialHash getHashFromImage(String path) {
        try {
            RadialHash hash = jpHash.getImageRadialHash(path);
            return hash;
        } catch (Exception e) {
            logger.error("getAllError", e);
            return null;
        }
    }


}

