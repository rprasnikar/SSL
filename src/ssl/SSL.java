/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ssl;

import java.io.*;
import java.security.*;
import javax.net.ssl.*;


import java.util.regex.*;


public class SSL {
  public static void main(String[] args) {
    SSLServerSocket s;


    // Pick all AES algorithms of 256 bits key size
    String patternString = "AES*256*";
    Pattern pattern = Pattern.compile(patternString);
    Matcher matcher;
    boolean matchFound;

    try {
      SSLServerSocketFactory sslSrvFact = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
      s =(SSLServerSocket)sslSrvFact.createServerSocket(8181);

      SSLSocket in = (SSLSocket)s.accept();


      String str[]=in.getSupportedCipherSuites();

      int len = str.length;
      String set[] = new String[len];

      int j=0, k = len-1;
      for (int i=0; i < len; i++) {

        // Determine if pattern exists in input
        matcher = pattern.matcher(str[i]);
        matchFound = matcher.find();

        if (matchFound)
          set[j++] = str[i];
        else
          set[k--] = str[i];
      }

      in.setEnabledCipherSuites(set);

      str=in.getEnabledCipherSuites();

      System.out.println("Available Suites after Set:");
      for (int i=0; i < str.length; i++)
        System.out.println(str[i]);
      System.out.println("Using cipher suite: " +
         (in.getSession()).getCipherSuite());

      PrintWriter out = new PrintWriter (in.getOutputStream(), true);
      out.println("Hello on a SSL socket");
      in.close();
    } catch (Exception e) {
      System.out.println("Exception" + e);
    }
  }
}

