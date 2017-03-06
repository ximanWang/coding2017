package com.coderising.download.impl;

import java.io.IOException;
import java.net.URL;

import com.coderising.download.api.Connection;
import com.coderising.download.api.ConnectionException;
import com.coderising.download.api.ConnectionManager;

import sun.net.www.protocol.http.HttpURLConnection;

public class ConnectionManagerImpl implements ConnectionManager {

	private int connections = 0;
	private String url;
	
	public ConnectionManagerImpl() {
		this.connections = 0;
	}

	@Override
	public Connection open(String url) throws ConnectionException {
		this.url = url;
		checkConnectionSize();
		URL address = null;
		HttpURLConnection conn = null;
		try {
			address = new URL(url);
			conn = (HttpURLConnection) address.openConnection();
			connections++;
			return (Connection) conn;
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}

	private void checkConnectionSize() {
		if (connections > MAX_CONNECTION_SIZE)
			try {
				throw new NoFreeSourceException("No free connections available.");
			} catch (NoFreeSourceException e) {
				e.printStackTrace();
			}
		
	}

}

