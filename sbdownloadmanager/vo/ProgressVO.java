/**
 * 
 */
package com.sbdownloadmanager.vo;

/**
 * @author rajesh
 *
 */
public class ProgressVO {
	
	private float _progress;
	
	private String _url;
	
	
	private float _totalBytes;
	
	private long _bytesLoaded;
	
	
	/**
	 * @param of type null
	 * @return _progress of type float
	 * getter function for _progress
	 * @author rajesh
	 * @date 9 feb 2012
	 */
	public float get_progress() {
		return _progress;
	}

	/**
	 * @param _progress of type float
	 * @return of type null
	 * setter function for _progress
	 * @author rajesh
	 * @date 9 feb 2012
	 */
	public void set_progress(float _progress) {
		this._progress = _progress;
	}

	/**
	 * @param of type null
	 * @return _url of type String
	 * getter function for _url
	 * @author rajesh
	 * @date 9 feb 2012
	 */
	public String get_url() {
		return _url;
	}
	
	/**
	 * @param _url of type String
	 * @return of type null
	 * setter function for _url
	 * @author rajesh
	 * @date 9 feb 2012
	 */
	public void set_url(String _url) {
		this._url = _url;
	}
	
	
	/**
	 * @param of type null
	 * @return of type 
	 * getter function for 
	 * @author rajesh
	 * @date 9 feb 2012
	 */
	public float get_totalBytes() {
		return _totalBytes;
	}
	
	/**
	 * @param _totalBytes of type float
	 * @return of type null
	 * setter function for _totalBytes
	 * @author rajesh
	 * @date 9 feb 2012
	 */
	public void set_totalBytes(float _totalBytes) {
		this._totalBytes = _totalBytes;
	}
	
	
	/**
	 * @param of type null
	 * @return _bytesLoaded of type long
	 * getter function for _bytesLoaded
	 * @author rajesh
	 * @date 9 feb 2012
	 */
	public long get_bytesLoaded() {
		return _bytesLoaded;
	}
	
	/**
	 * @param _bytesLoaded of type long
	 * @return of type null
	 * setter function for _bytesLoaded
	 * @author rajesh
	 * @date 9 feb 2012
	 */
	public void set_bytesLoaded(long _bytesLoaded) {
		this._bytesLoaded = _bytesLoaded;
	}

	/**
	 * 
	 */
	public ProgressVO() {
		// TODO Auto-generated constructor stub
	}

}
