
import java.net.*;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonReader;

import java.awt.image.BufferedImage;
import java.io.*;

public class UWBServer {
	private String serverName;
	private int port;

	public UWBServer(String IP, int poort) {
		serverName = IP;
		port = poort;
	}

	public Boolean login(String uName, String PW) {
		JsonBuilderFactory factory = Json.createBuilderFactory(null);
		String data = factory.createObjectBuilder().add("command", "login").add("uName", uName).add("PW", PW).build()
				.toString();
		String responce = send(data);

		JsonObject json;
		JsonReader jsonReader = Json.createReader(new StringReader(responce));
		json = jsonReader.readObject();
		jsonReader.close();

		if (json.get("loginReply").toString().replace("\"","").equalsIgnoreCase("true")) {
			return true;
		}
		return false;

	}

	public Coordinates getCoordinates(String roomName) {
		JsonBuilderFactory factory = Json.createBuilderFactory(null);
		String data = factory.createObjectBuilder().add("command", "getCoordinates").add("name", roomName).build()
				.toString();
		
		return new Coordinates(send(data)); //Coordinates is a class that will have all the data of the UWB module

	}

	private String send(String data) {
		String responce = "";
		try {
			Socket client = new Socket(serverName, port);
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);

			out.writeUTF(data);

			responce = in.readUTF();
			System.out.println(responce);
			client.close();
		} catch (IOException e) {
			return null;
		}
		return responce;
	}

	public BufferedImage getImage(String ImageName) {

		JsonBuilderFactory factory = Json.createBuilderFactory(null);
		String data = factory.createObjectBuilder().add("command", "getImage").add("name", ImageName).build()
				.toString();

		System.out.println("sending: " + data);
		try {
			Socket client = new Socket(serverName, port);
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			InputStream inFromServer = client.getInputStream();

			out.writeUTF(data);

			BufferedImage responce = ImageIO.read(ImageIO.createImageInputStream(inFromServer));
			client.close();
			return responce;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
