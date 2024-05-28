package com.mycompany.chatboxv1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hii
 */
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class MassegeSocket {
        static public DataOutputStream dos;
	static public BufferedReader brinc;
	static Socket s;
        
        //receiver thread
        public Thread recthread = new Thread(new Runnable() {
		public void run() {
			String s1="";
			while(true)
			{   
				try {
				s1 = brinc.readLine();
                                }
                                catch (Exception e) {
                                        f.showPop();
                                        f.setsendbtn(false);
                                        f.setconlabel("");
                                       try{
                                        ser.close();
                                        s.close();
                                       }catch(Exception ee){ee.printStackTrace();}
                                        break;
				}
				//by ms
                                int mleninchat=(int)(f.getjPanel4wd()*2.48*6/(7*2*25))-8;
                                
				String s2="";
				if(s1.length()>mleninchat)
				{	
					int l=(int)(s1.length()/mleninchat);
					int nind=0;
					for(int i=1;i<=l;i++) {
						s2=s2+s1.substring(nind,nind+mleninchat)+"\n";
						nind=nind+mleninchat;
					}
					s2=s2+s1.substring(nind);
				}
				else
					s2=s1;
				f.lltxtpappend(s2+"\n");
				
				//move right position
				String rs1="";
				for(int i=0;i<s2.length();i++)
					rs1=rs1+" ";
				
				s2="";
				if(rs1.length()>mleninchat)
				{	
					int l=(int)(rs1.length()/mleninchat);
					int nind=0;
					for(int i=1;i<=l;i++) {
						s2=s2+rs1.substring(nind,nind+mleninchat)+"\n";
						nind=nind+mleninchat;
					}
					s2=s2+rs1.substring(nind);
				}
				else
					s2=rs1;
				
                                f.rrtxtpappend(s2+"\n");
                                
				
			}
		}
	});
        
	MassegeSocket(String ip,int port) throws Exception{
		if(s!=null)
                s.close();
                s=null;
                if(ser!=null)
                ser.close();
                s=null;
                s = new Socket(ip,port);
		dos= new DataOutputStream(s.getOutputStream());
		brinc=new BufferedReader(new InputStreamReader(s.getInputStream()));
    }
        //server code server sockeet
        ServerSocket ser;
        int port;
        MassengerWindow f;
        Thread sert = new Thread(new Runnable(){
                    public void run(){
                        try {     
                            s = ser.accept();
                            dos= new DataOutputStream(s.getOutputStream());
                            brinc=new BufferedReader(new InputStreamReader(s.getInputStream()));
                            f.setconlabel("Connected To :"+s.getInetAddress());
                            recthread.start();
                            f.setsendbtn(true);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        
        MassegeSocket(MassengerWindow f,int port) throws Exception{
                this.close();
                ser = new ServerSocket(port);
                this.f=f;
                this.port=port;
                sert.start();
        }
    
    public void close(){
                if(ser!=null)
                    try {
                        ser.close();
                } catch (IOException ex) {
                    Logger.getLogger(MassegeSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(s!=null)
                    try {
                        s.close();
                } catch (IOException ex) {
                    Logger.getLogger(MassegeSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
            
        
    }
}
