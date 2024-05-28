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
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Conn{
    MassengerWindow f;
    Socket sk;
    BufferedReader r;
    Thread rect = new Thread(new Runnable(){
        public void run(){
            String s="";
            while(true){
                try {
                    s=r.readLine();
                } catch (Exception ex) {
                    break;
                }
                //print
                try{
                    String s1 =s;
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
                }catch(Exception e){
                    break;
                }        
               }
        }
    });
    
    Conn(MassengerWindow mw,Socket sk){
        this.f=mw;
        this.sk=sk;
        try {
            r=new BufferedReader(new InputStreamReader(sk.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        rect.start();
    }
}
