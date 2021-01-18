package com.scholar.projektseminar_programmierung;

import java.io.IOException;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class ProjektseminarProgrammierungApplication {
	
	public static void startBrowser(String url, String os) {
		try {
			Runtime runtime = Runtime.getRuntime(); 
			
			//Check operating systems
			if(os.contains("windows")) {
				runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
			}
			else if(os.contains("mac")) {
				runtime.exec("open "+url);
			}
			else if(os.contains("nix") || os.contains("nux")) {
				runtime.exec("xdg-open "+url);
			} else {
				System.out.println("Unable to launch browser");
			}
		} catch(IOException eek) {
			System.out.println("Error: "+ eek.getMessage());
	}
	}

	public static void main(String[] args) {
		SpringApplication.run(ProjektseminarProgrammierungApplication.class, args);
		
		//Open Browser
		String url = "http://localhost:8080";
		String myOS = System.getProperty("os.name").toLowerCase();
		startBrowser(url,myOS); //Start default browser 
		
		

	}

}

