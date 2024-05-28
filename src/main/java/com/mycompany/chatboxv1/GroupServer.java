package com.mycompany.chatboxv1;


import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hii
 */
import java.net.*;
import java.io.*;

public class GroupServer {
    MassengerWindow mw;
    Socket[] sarr=new Socket[100];
    int count=0;
    ServerSocket ser;
    Thread serviceth = new Thread(new Runnable()
    {
    public void run(){
        while(true){
            try {
                Socket s = ser.accept();
                sarr[count]=s;
                count++;
                Conn c =new Conn(mw,s);
                } catch (Exception ex) {
                    mw.showPop();
                    int uniqueId = (int) System.currentTimeMillis();
                    String commandName = ""; 
                    ActionEvent event = new ActionEvent(this, uniqueId, commandName);
                    mw.closebtnActionmethod(event);
                    break;
                }
            }
    }
    });
    GroupServer(MassengerWindow mw,int port)
    {
        try {
            try {
                this.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            this.mw=mw;
            ser = new ServerSocket(port);
            serviceth.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void send(String s){
        for(int i=0;i<count;i++)
        {
            try {
                DataOutputStream dos = new DataOutputStream(sarr[i].getOutputStream());
                dos.writeBytes(s+"\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        int mleninchat=(int)(mw.getjPanel4wd()*2.48*6/(7*2*25))-8;
        mw.setmsgtxtarea("");
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
          mw.rrtxtpappend(ss+"\n");

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
        mw.lltxtpappend(ss+"\n");
        
    }

    void close() throws Exception {
        if(ser!=null)
            ser.close();
        for(int i=0;i<count;i++)
        {
            sarr[i].close();
        }
    }
}

