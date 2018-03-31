package ftpudpClient;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class FTPClient {
    public static void main(String[] args) throws Exception{
        DatagramSocket socket=null;
        DatagramPacket inPack=null;
        DatagramPacket outPack=null;
        byte[] inBuf,outBuf;
        String msg;
        final int port=5789;
        Scanner src=new Scanner(System.in);
        try{
        InetAddress address=InetAddress.getByName("localhost");
            System.out.println("InetAdress: "+address);
        socket=new DatagramSocket();
        
        msg="";
        outBuf=msg.getBytes();
        outPack=new DatagramPacket(outBuf,0, outBuf.length,address,port);
        socket.send(outPack);
        
        inBuf=new byte[65535];
        inPack=new DatagramPacket(inBuf,inBuf.length);
        socket.receive(inPack);
        
        String data=new String(inPack.getData(),0,inPack.getLength());
        System.out.println(data);
        
        outBuf=new byte[100];
        double noOfpacket=0;
        String filename=src.nextLine();
        //if(filename.equals("3")) noOfpacket=82; 
        outBuf=filename.getBytes();
        outPack=new DatagramPacket(outBuf,0,outBuf.length,address,port);
        socket.send(outPack);
        //recieve file
        
        long packetsize=65000;
        
        inBuf=new byte[10000];
            inPack=new DatagramPacket(inBuf,inBuf.length);
            socket.receive(inPack);
        
            data=new String(inPack.getData(),0,inPack.getLength());
            noOfpacket=Double.parseDouble(data);
                System.out.println(data);
        
            if (noOfpacket==0.0) {
                try{
                    inBuf=new byte[10000];
                    inPack=new DatagramPacket(inBuf,inBuf.length);
                    socket.receive(inPack);        
                    data=new String(inPack.getData(),0,inPack.getLength());
                    System.out.println("Data of text file : "+data);
                    BufferedWriter pw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename)));
                    //FileWriter writer = new FileWriter(filename); 
                    //BufferedWriter pw = new BufferedWriter(writer);


                    pw.write(data);
                    pw.close();
                    //force write buffer to File
                    System.out.println("File Write Successful.closing Socket");
                    socket.close();
                }
                catch(IOException e)
                {
                    System.out.println("File Error");
                    socket.close();
                }
            } else {
                try {
                    System.out.println("Mp3 entering");
                    FileOutputStream   fos = new FileOutputStream("C:\\Users\\Abrar Abir\\Documents\\NetBeansProjects\\song.mp3");
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    byte[] mybytearray = new byte[65000];
                    DatagramPacket receivePacket = new DatagramPacket(mybytearray,mybytearray.length);
                    System.out.println("Mp3 entering 1234");
                    //inPack=new DatagramPacket(inBuf,inBuf.length);
                    for(double i=0;i<noOfpacket+1;i++)
                    {
                        socket.receive(receivePacket);
                        byte audioData[] = receivePacket.getData();
                        
                        bos.write(audioData, 0,audioData.length);
                    }
                }catch(Exception e) {   
                }
            } //}
        }
        catch(Exception e)
        {
            System.out.println("\nNetworkError.");
        }
        socket.close();
    }
}
