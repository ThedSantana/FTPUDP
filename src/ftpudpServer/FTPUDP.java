package ftpudpServer;

import java.io.BufferedReader;
import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.FileReader;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
public class FTPUDP  {
    
    public static void main(String[] args) throws Exception{
        DatagramSocket socket=null;
        DatagramPacket inPacket=null;
        DatagramPacket outPack=null;
        byte[] inBuf,outBuf;
        String msg;
        final int PORT=5789;
        try{
        socket=new DatagramSocket(PORT);
        while(true)
        {
            System.out.println("\nRunning...\n");
            
            inBuf=new byte[100];
            inPacket=new DatagramPacket(inBuf,inBuf.length);
            socket.receive(inPacket);
            System.out.println("Packet received 1");
            int SOurcePort=inPacket.getPort();
            InetAddress sourceAddress=inPacket.getAddress();
            msg=new String(inPacket.getData(),0,inPacket.getLength());
            System.out.println("CLient: "+sourceAddress+":"+SOurcePort);
            
            String dirnam="C:\\Users\\Abrar Abir\\Documents\\NetBeansProjects\\FTPUDP\\src\\ftpudpServer\\Files";
            
            File f1=new File(dirnam);
            File filelist[]=f1.listFiles();
            System.out.println(filelist[0].getName());
            StringBuilder sb=new StringBuilder("\n");
            int count=0;
            
            for(int i=0;i<filelist.length;i++)
               {
                   if(filelist[i].canRead())
                       count++;
               } 
            sb.append(count+" files have beend found \n \n");
            for(int i=0;i<filelist.length;i++)
                sb.append(filelist[i].getName()+":"+filelist[i].length()+"\n");
                    
            sb.append("\nDownload :");
            outBuf=(sb.toString()).getBytes();
            outPack=new DatagramPacket(outBuf,0,outBuf.length,sourceAddress,SOurcePort);
            socket.send(outPack);
            
            inBuf=new byte[100];
            inPacket=new DatagramPacket(inBuf, inBuf.length);
            socket.receive(inPacket);
            String filename=new String(inPacket.getData(),0,inPacket.getLength());
            
            System.out.println("Requested file: "+filename);
            filename=filename+".mp3";
            System.out.println("Filenameeee: "+filename);
            boolean fileIs=false;
            int index=-1;
            sb=new StringBuilder("");
            for(int i=0;i<filelist.length;i++)
            {
             if(((filelist[i].getName()).toString()).equalsIgnoreCase(filename));
             {
                 index=i;
                 fileIs=true;
                // break;
             }
            }
            System.out.println("Index: "+index);
            if(!fileIs)
            {
                  String s="Error";
                  outBuf=s.getBytes();
                  outPack=new DatagramPacket(outBuf,0,outBuf.length,sourceAddress,SOurcePort);
                  socket.send(outPack);
            }  
            else
            {
                File Sendfile=new File(filelist[index].getAbsolutePath()) ;
                System.out.println("Send File size : "+Sendfile.length());
                if(Sendfile.length()<65000){
                  try
                  {
                   double nosofpackets = 0.0;
                         
                        outBuf=new byte[10000];
                        String packets=""+nosofpackets;
                         outBuf=(packets.toString()).getBytes();
                   outPack=new DatagramPacket(outBuf,0,outBuf.length,sourceAddress,SOurcePort);
                   socket.send(outPack);
                   
                   FileReader fr=new FileReader(Sendfile);
                   BufferedReader bf=new BufferedReader(fr);
                   String s=null;
                   sb=new StringBuilder();
                   
                   while((s=bf.readLine())!=null)
                       {
                           sb.append(s);
                       }
                   if(bf.readLine()==null)
                          System.out.println("File read successfully.Closing Socket.");
                   
                   outBuf=new byte[100000];
                   outBuf=(sb.toString()).getBytes();
                   outPack=new DatagramPacket(outBuf,0,outBuf.length,sourceAddress,SOurcePort);
                   socket.send(outPack);
                   
                  }
                  
                  catch(Exception e)
                  {
                      System.err.println("IOE Exception");
                  }
                }
                
                else
                 {
                     int packetsize = 65000;
                       BufferedInputStream bis = null;
                    double nosofpackets;
                    
                       try{
                       
                    nosofpackets = Math.ceil(((int) Sendfile.length()) / packetsize);
                     System.out.println(nosofpackets);
                     outBuf=new byte[10000];
                     String packets=""+nosofpackets;
                     System.out.println("In server "+packets);
                   outBuf=(packets.toString()).getBytes();
                   outPack=new DatagramPacket(outBuf,0,outBuf.length,sourceAddress,SOurcePort);
                   socket.send(outPack);
                           
            bis = new BufferedInputStream(new FileInputStream(Sendfile));
            for (double i = 0; i < nosofpackets + 1; i++) {
                byte[] mybytearray = new byte[packetsize];
                bis.read(mybytearray, 0, mybytearray.length);
                outPack= new DatagramPacket(mybytearray, mybytearray.length,sourceAddress,SOurcePort);
                socket.send(outPack);
                Thread.sleep(10);
            }
             }
            finally {
            if(bis!=null)
                bis.close();
            if(socket !=null)
                socket.close();
            }       
        
        
        
    
                 }
            }
                    
            
        }
        }catch(Exception e){
            
        }
    }
    
}
