package com.scholar.projektseminar_programmierung;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjektseminarProgrammierungApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjektseminarProgrammierungApplication.class, args);
		
		//Open Browser
		String url = "http://localhost:8080";
		
		String myOS = System.getProperty("os.name").toLowerCase();
		
		try {
			if(Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();
				desktop.browse(new URI(url));
			} else {
				Runtime runtime = Runtime.getRuntime();
				if(myOS.contains("mac")) {
					runtime.exec("open "+url);
				}
				else if(myOS.contains("nix") || myOS.contains("nux")) {
					runtime.exec("xdg-open "+url);
				} else {
					System.out.println("Unable to launch browser");
				}
			}
		} catch(IOException | URISyntaxException eek) {
			System.out.println("Error: "+eek.getMessage());
		}
	}

}
