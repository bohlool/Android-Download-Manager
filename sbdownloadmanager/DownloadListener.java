package com.sbdownloadmanager;

import com.sbdownloadmanager.vo.ProgressVO;
import com.sbdownloadmanager.vo.ResultVO;

public interface DownloadListener {
	/**
	 * @param result
	 * function for handling result
	 */
	public void onResult(ResultVO result);
	
	/**
	 * @param error
	 * function for handling Error 
	 */
	public void onError(String error);
	
	/**
	 * @param progress of type ProgressVO
	 * function for handling progress
	 * @author rajesh
	 */
	public void onProgress(ProgressVO progress);
}
