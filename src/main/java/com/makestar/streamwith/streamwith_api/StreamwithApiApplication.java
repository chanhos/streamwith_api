package com.makestar.streamwith.streamwith_api;

import com.makestar.streamwith.streamwith_api.model.spotify.TrackInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class StreamwithApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamwithApiApplication.class, args);;
	}

}
