package ml.u_finder.u_finder;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.*;

import java.io.*;

import javax.json.Json;

public class UWBServer {
    private String serverName;
    private int port;

    public UWBServer(String IP, int poort) {
        serverName = IP;
        port = poort;
    }

    public Boolean login(String uName, String PW) {
        try {
            String data = new JSONObject().put("command", "login").put("uName", uName).put("PW", PW).toString();
            Log.v("Raber", "" + data);

            String responce = send(data);

            Log.v("Raber", "" + responce);

            JSONObject json = new JSONObject(responce);


            if (json.get("loginReply").toString().replace("\"", "").equalsIgnoreCase("true")) {
                return true;
            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.v("Raber", "login error");
        }


        return false;

    }


    public Coordinates getCoordinates(String roomName) {
        try {
            String data = new JSONObject().put("command", "getCoordinates").put("name", roomName).toString();
            return new Coordinates(send(data));
        } catch (JSONException e) {
            Log.v("Raber", "couldnt get coordinates");
            return null;
        }


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
            Log.v("Raber", "" + responce);
            System.out.println(responce);
            client.close();
        } catch (IOException e) {
            Log.v("Raber", "Io exceptoin");
            return null;
        }
        return responce;
    }


    public Bitmap getImage(String ImageName) {


        try {
            String data = new JSONObject().put("command", "getImageFast").put("name", ImageName).toString();

            System.out.println("sending: " + data);
            Socket client = new Socket(serverName, port);
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            InputStream inFromServer = client.getInputStream();

            out.writeUTF(data);

            Bitmap responce = BitmapFactory.decodeStream(inFromServer);
            client.close();
            return responce;
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("Raber", "io error");
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.v("Raber", "json error");
            return null;
        }
    }

}
