package com.example.architectureofconnectapp.APIforServer;



import com.example.architectureofconnectapp.Model.AuthAndReg.DataUserReg;

import java.io.IOException;
import java.util.Map;


/**
 * Created by xschen on 6/4/2017.
 */
public interface WebClient {
    Map<String, String> get() throws Exception;

    String put(String url, String body, Map<String, String> headers);

    String delete(String url, Map<String, String> headers);

    Map<String,String> get( Map<String, String> headers) throws IOException;

    SpringIdentity post( DataUserReg body, Map<String, String> headers);
    SpringIdentity posttoken( String tkn, Map<String, String> headers);

    String getToken();
    String getSessionId();

    SpringIdentity jsonPost( Map<String, String> data);
}
