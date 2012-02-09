/**
 * 
 */
package com.sbdownloadmanager.vo;

import android.graphics.Bitmap;

/**
 * @author rajesh
 *
 */
public class ResultVO {
	
	
	private Bitmap _bitmap;
	
	private String _url;
	
	private String _response;
	
	private int _responseCode;
	
	private String _resultString;
	
	/**
	 * @param of type null
	 * @return _bitmap of type Bitmap
	 * getter function for _bitmap
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public Bitmap get_bitmap() {
		return _bitmap;
	}
	
	/**
	 * @param _bitmap of type Bitmap 
	 * @return of type null
	 * setter function for _bitmap
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public void set_bitmap(Bitmap _bitmap) {
		this._bitmap = _bitmap;
	}
	
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
	 * @return _response of type String
	 * getter function for _response
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public String get_response() {
		return _response;
	}
	
	/**
	 * @param of type null
	 * @return _responseCode of type int
	 * getter function for _responseCode
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public int get_responseCode() {
		return _responseCode;
	}
	
	/**
	 * @param of type null
	 * @return _resultString of type String
	 * getter function for _resultString
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public String get_resultString() {
		return _resultString;
	}
	
	
	/**
	 * @param _resultString of type String 
	 * @return of type null 
	 * setter function for _resultString
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public void set_resultString(String _resultString) {
		this._resultString = _resultString;
	}

	/**
	 * Constructor function
	 */
	public ResultVO(String response, int responseCode) {
		// TODO Auto-generated constructor stub
		_response = response;
		_responseCode = responseCode;
	}

}
