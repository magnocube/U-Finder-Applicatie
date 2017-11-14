import java.awt.image.BufferedImage;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;



public class App {
	public static void main(String[] args) {
		
		
		 UWBServer u = new UWBServer("77.172.10.240",8379);
		 
		  
		 BufferedImage b = u.getImage("room1");
		 System.out.println(b.getWidth() + "  " + b.getHeight());
		 
		 Coordinates x = u.getCoordinates("room1");
		 for(int i = 0; i<x.getSize() ; i++) {		 
			 System.out.println(x.getName(i) + "  " +  x.getX(i) + "  " +  x.getY(i));
		 }
			 
		 
		 
		 System.out.println(u.login("test1", "test2"));
		
		
		

		
	}
}	
