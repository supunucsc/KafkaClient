package com.blockchain.kafkabroadcaster.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable {

    private static Socket socket;

    private  static ArrayList<Socket> socketList=new ArrayList<Socket>();


    public void run() {
        try
        {



            int port = 9000;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Started and listening to the port 9000");

            //Server is running always. This is done using this while(true) loop
            while(true)
            {
                //Reading the message from the client

                socket = serverSocket.accept();
                socketList.add(socket);
//                InputStream is = socket.getInputStream();
//                InputStreamReader isr = new InputStreamReader(is);
//                BufferedReader br = new BufferedReader(isr);
//                String number = br.readLine();
//                System.out.println("Message received from client is "+number);
//
//                //Multiplying the number by 2 and forming the return message
//                String returnMessage;
//                try
//                {
//                    int numberInIntFormat = Integer.parseInt(number);
//                    int returnValue = numberInIntFormat*2;
//                    returnMessage = String.valueOf(returnValue) + "\n";
//                }
//                catch(NumberFormatException e)
//                {
//                    //Input was not a number. Sending proper message bac
//
//
//                    // k to client.
//                    returnMessage = "Please send a proper number\n";
//                }
//
//                //Sending the response back to the client.
//                OutputStream os = socket.getOutputStream();
//                OutputStreamWriter osw = new OutputStreamWriter(os);
//                BufferedWriter bw = new BufferedWriter(osw);
//                bw.write(returnMessage);
//                System.out.println("Message sent to the client is "+returnMessage);
//                bw.flush();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch(Exception e){}
        }
    }

    public static  void sendMessage(String mesg)throws Exception{


        Socket currentSock;
        for(int i=0;i<socketList.size();i++){
            currentSock=socketList.get(i);

            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(mesg);
            System.out.println("Message sent to the client "+currentSock.getInetAddress()+" "+mesg);
            bw.flush();

        }



    }
}
