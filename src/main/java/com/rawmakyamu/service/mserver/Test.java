/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.service.mserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;
import org.jpos.iso.ISOException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author dilanka_w
 */
public class Test {

    public static void main(String[] args) throws ISOException {

//        System.out.println("---------------------: " + "1111196190");
        try {

//            URL urlToWebService = new URL("file:/C:/Users/dilanka_w/Desktop/WSDL_NTB/SBank_Service.wsdl");
//
//            SBankService sBankService = new SBankService(urlToWebService);
//            sBankService.getSBankServiceSoap().updateWalletIdLevel("", "");
//
//            String switchResponse = Test.getResponce("1111999999");
//            System.out.println("2222222222:" + switchResponse);
            String key = "";
            String mname = "";
            String mDescription = "A h bvj";

            try {

                Random randomgenerator = new Random();
                Date nowDate = new Date();
                long date = nowDate.getTime();

                try {

                    mname = mDescription.trim().replaceAll(" ", "").substring(0, 3);

                } catch (Exception ex) {

                    String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
                    StringBuilder salt = new StringBuilder();
                    Random rnd = new Random();
                    while (salt.length() < 3) { // length of the random string.
                        int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                        salt.append(SALTCHARS.charAt(index));
                    }

                    mname = salt.toString();
                }

                String randomNo = randomgenerator.nextInt(999) + "";
                System.out.println("randno : "+randomNo);

                String RANDINT = "0123456789";

                if (randomNo.length() == 1) {

                    StringBuilder sb = new StringBuilder();
                    Random rnd = new Random();

                    while (sb.length() < 2) { // length of the random string.
                        int index = (int) (rnd.nextFloat() * RANDINT.length());
                        sb.append(RANDINT.charAt(index));
                    }
                    
                    System.out.println("randno 1 : "+sb.toString());

                    randomNo = randomNo + sb.toString();

                } else if (randomNo.length() == 2) {

                    StringBuilder sb = new StringBuilder();
                    Random rnd = new Random();

                    while (sb.length() < 1) { // length of the random string.
                        int index = (int) (rnd.nextFloat() * RANDINT.length());
                        sb.append(RANDINT.charAt(index));
                    }

                     System.out.println("randno 2 : "+sb.toString());
                    randomNo = randomNo + sb.toString();
                }

                key = date + mname + randomNo;
                System.out.println("key : " + key);

            } catch (Exception ex) {

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getResponce(String walletID) throws Exception {

        String message = "";

        final String SERVER_IP;
        final int SERVER_PORT;

        try {

            try {
                String ip = "192.168.1.130";
                int port = 4848;

//                ip = ServletActionContext.getServletContext().getInitParameter("mserverclientip");
//                port = Integer.parseInt(ServletActionContext.getServletContext().getInitParameter("mserverclientport"));
                SERVER_IP = ip;
                SERVER_PORT = port;

                System.out.println("MServer IP : " + SERVER_IP);
                System.out.println("MServer PORT : " + SERVER_PORT);

                URL url = new URL("http://" + SERVER_IP + ":" + SERVER_PORT + "/NTBWALLET_SERVER/webresources/generic/Upgrade_User");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                String mServerRequest = "{\"wallet_id\":\"" + walletID + "\"}";

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
                    System.out.println("MServer Respose : " + output);

                    final JSONObject obj = new JSONObject(output);

                    System.out.println("response_code =" + obj.getString("response_code"));
                    System.out.println("response_description =" + obj.getString("response_description"));

                    message = obj.getString("response_description");

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
