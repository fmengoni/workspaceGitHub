package com.telnetar.nodejs;

import org.json.JSONObject;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIOException;

public class TelnetarIOCallback implements IOCallback {

    @Override
    public void on(String event, IOAcknowledge ack, Object... args) {
		try{
			System.out.println("on - event: " + event  + " args: " + args);
	//	    if(event.equals("updateData")){
	//		onUpdateData(args);
	//	    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
    }
    
    @Override
    public void onConnect() {
        System.out.println("onConnect");
    }

    @Override
    public void onDisconnect() {
    	System.out.println("onDisconnect");
    }

    @Override
    public void onError(SocketIOException socketIOException) {
        socketIOException.printStackTrace();
    }

    @Override
    public void onMessage(String data, IOAcknowledge ack) {
    	System.out.println("onMessage - " + data);
    }

    @Override
    public void onMessage(JSONObject json, IOAcknowledge ack) {
    	System.out.println("onMessage - " + json);
    }
}