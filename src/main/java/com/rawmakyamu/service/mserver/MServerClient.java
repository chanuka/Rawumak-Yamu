/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.service.mserver;

import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.varlist.CommonVarList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author dilanka_w
 */
public class MServerClient {

    public static String getResponce(String walletID, String code, String reqMessage) throws Exception {

        CommonDAO dao = new CommonDAO();

        String message = "";

        final String SERVER_IP;
        final int SERVER_PORT;

        try {

            try {

                String ip = "";
                int port = 0;

                ip = dao.getCommonConfigByCode(CommonVarList.MSERVER_IP);
                port = Integer.parseInt(dao.getCommonConfigByCode(CommonVarList.MSERVER_PORT));

                SERVER_IP = ip;
                SERVER_PORT = port;

                System.out.println("MServer IP : " + SERVER_IP);
                System.out.println("MServer PORT : " + SERVER_PORT);

                URL url = new URL("http://" + SERVER_IP + ":" + SERVER_PORT + "/NTBWALLET_SERVER/webresources/generic/Upgrade_User");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

//                String mServerRequest = "{\"wallet_id\":\"" + walletID + "\"}";
                String mServerRequest = "{\"wallet_id\":\"" + walletID + "\",\"code\":\"" + code + "\",\"message\":\"" + reqMessage + "\"}";

                Logger.getLogger("MServer Requset : " + mServerRequest);
                System.out.println("MServer Requset : " + mServerRequest);

                OutputStream os = conn.getOutputStream();
                os.write(mServerRequest.getBytes());
                os.flush();

                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + conn.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        (conn.getInputStream())));

                String output;

                if ((output = br.readLine()) != null) {
                    System.out.println("MServer Response : " + output);

                    final JSONObject obj = new JSONObject(output);

                    System.out.println("response_code =" + obj.getString("response_code"));
                    System.out.println("response_description =" + obj.getString("response_description"));

                    message = obj.getString("response_code");

                }

                conn.disconnect();

            } catch (IOException e) {
                System.out.println("Error while calling Crunchify REST Service : IOException");
                throw e;
            } catch (RuntimeException e) {
                System.out.println("Error while calling Crunchify REST Service : RuntimeException");
                throw e;
            } catch (JSONException e) {
                System.out.println("Error while calling Crunchify REST Service : JSONException");
                throw e;
            }

        } catch (Exception e) {
            System.out.println("Error while calling Crunchify REST Service : Exception");
            throw e;
        }
        return message;
    }

}
