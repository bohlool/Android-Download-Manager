/**
 * 
 */
package com.sbdownloadmanager.vo;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.sbdownloadmanager.DownloadListener;


/**
 * @author rajesh
 *
 */
public class ParamsVO {
	
	
	private ArrayList<NameValuePair> _params;
    private ArrayList<NameValuePair> _headers;
	
    private int _requestType;
    
    private String _resultFormat;
    
    
	private String _url;
	
	private DownloadListener _listener;
	
	/**
	 * @param of type null
	 * @return _url of type String
	 * getter function for _url
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public String get_url() {
		return _url;
	}

	/**
	 * @param _url of type String
	 * @return of type null
	 * setter function for _url
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public void set_url(String _url) {
		this._url = _url;
	}
	
	/**
	 * @param of type null
	 * @return _listener of type DownloadListener
	 * getter function for _listener
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public DownloadListener get_listener() {
		return _listener;
	}
	

	/**
	 * @param _listener of type DownloadListener
	 * @return of type null
	 * setter function for _listener
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public void set_listener(DownloadListener _listener) {
		this._listener = _listener;
	}
	
	/**
	 * @param of type null
	 * @return _params of type ArrayList<NameValuePair>
	 * getter function for _params
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public ArrayList<NameValuePair> get_params() {
		return _params;
	}
	
	/**
	 * @param of type null
	 * @return _headers of type ArrayList<NameValuePair>
	 * getter function for _headers
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public ArrayList<NameValuePair> get_headers() {
		return _headers;
	}
	
	/**
	 * @param of type null
	 * @return _requestType of type String
	 * getter function for _requestType
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public int get_requestType() {
		return _requestType;
	}
	

	/**
	 * @param _requestType of type String
	 * @return of type null
	 * setter function for _requestType
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public void set_requestType(int _requestType) {
		this._requestType = _requestType;
	}
	

	/**
	 * @param of type null
	 * @return _resultFormat of type String
	 * getter function for _resultFormat
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public String get_resultFormat() {
		return _resultFormat;
	}
	
	/**
	 * @param _resultFormat of type String
	 * @return of type null
	 * setter function for _resultFormat
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public void set_resultFormat(String _resultFormat) {
		this._resultFormat = _resultFormat;
	}

	/**
	 * 
	 */
	public ParamsVO() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param key of type String 
	 * @param value of type String 
	 * function which will add the param to _param 
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public void addParam(String key, String value)
	{
		_params = (_params == null) ? new ArrayList<NameValuePair>() : _params;
		_params.add(new BasicNameValuePair(key, value));
	}
	
	/**
	 * @param key of type String 
	 * @param value of type String 
	 * function which will add the header to _headers 
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public void addHeader(String key, String value)
	{
		_headers = (_headers == null) ? new ArrayList<NameValuePair>() : _headers;
		_headers.add(new BasicNameValuePair(key, value));
	}
}
