# FTPUDP
Sending a large file using UDP
This a simple code for sending a file greater than 64KB. We Know that at once we can send at least 64 kb .To send a larger file we need to split the file then send 
it to the Client.
The code is simple..To run the project First run the "ftpudpserver".Then run "FTPClient" in ftpudpClient package. It will show you some file names that u can douwnload.
Then you select the file name and press enter,, file will be downloaded.
Caution: Please change the path accoriding tp your pc.You need to change the path name at "FTPClient" at 85 Line.This is where your file will be downloade.
You will also need to change the file path in "FTPUDP.java" in "ftpudpServer" package.at 36 number line you will see a path name.This is the patha where your downloadable files are located.
Please change these paths according to computer and then u are ready to go :)
