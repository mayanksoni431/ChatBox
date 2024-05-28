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
public class MultiCreate {
        static DatagramSocket soc;
        static MassengerWindow f;
        static MulticastSocket ms=null;
	static InetAddress g=null;
	static int port;
	Thread t = new Thread (new Runnable(){
		public void run(){
;
			try{
				ms = new MulticastSocket(port);
				ms.joinGroup(g);
				}catch(Exception e){e.printStackTrace();}
			while(true)
			{	
                                byte[] barr=new byte[1024]; 
				DatagramPacket p;
				try{
				p = new DatagramPacket(barr,barr.length);
				ms.receive(p);
				String s1 = new String(p.getData());
                                s1=s1.trim();
                                //print
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
                                
                            }catch(Exception e){break;}
			}
		}
	});
        MultiCreate(MassengerWindow f,String group,int port)
        {
            
            this.f=f;
            this.port=port;
            try {
                g=InetAddress.getByName(group);
                t.start();
		soc = new DatagramSocket();	
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
        }
	public static void send(String s)
	{	
            s=s.trim();
            DatagramPacket p = new DatagramPacket(s.getBytes(),s.getBytes().length,g,port);
            try {
                soc.send(p);
                int mleninchat=(int)(f.getjPanel4wd()*2.48*6/(7*2*25))-8;
                f.setmsgtxtarea("");
                //formatting msgtxt
                String ss="";
                if(s.length()>mleninchat)
                {	
                        int l=(int)(s.length()/mleninchat);
                        int nind=0;
                        for(int i=1;i<=l;i++) {
                                ss=ss+s.substring(nind,nind+mleninchat)+"\n";
                                nind=nind+mleninchat;
                        }
                        ss=ss+s.substring(nind);
                }
                else
                        ss=s;
                f.rrtxtpappend(ss+"\n");

                //move left position
                String rs="";
                for(int i=0;i<ss.length();i++)
                        rs=rs+" ";

                ss="";
                if(rs.length()>mleninchat)
                {	
                        int l=(int)(rs.length()/mleninchat);
                        int nind=0;
                        for(int i=1;i<=l;i++) {
                                ss=ss+rs.substring(nind,nind+mleninchat)+"\n";
                                nind=nind+mleninchat;
                        }
                        ss=ss+rs.substring(nind);
                }
                else
                        ss=rs;

                f.lltxtpappend(ss+"\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
	}

    void close() {
        if(ms!=null)
        {ms.close();}
    }
}
