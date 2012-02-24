/**
 * 
 */
package com.sbdownloadmanager;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HTTP;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.sbdownloadmanager.paramutils.ParamsUtils;
import com.sbdownloadmanager.paramutils.RequestMethords;
import com.sbdownloadmanager.paramutils.ResultFormats;
import com.sbdownloadmanager.threadUtils.DownloadRunnable;
import com.sbdownloadmanager.vo.ParamsVO;
import com.sbdownloadmanager.vo.ResultVO;

/**
 * @author rajesh
 *
 */
public class DownLoadManager{
	
	
	public static final int PROGRESS = 0;
	public static final int COMPLETE = 1;
	
	private static DownLoadManager _instance;
	
	private HashMap<String, Bitmap> bitmapCache = new HashMap<String, Bitmap>();
	
	private HashMap<String, String> stringCache = new HashMap<String, String>();
	
	private HashMap<String, Thread> runningThreads = new HashMap<String, Thread>();
	
	private HashMap<String, ArrayList<ParamsVO>> pendingRequest = new HashMap<String, ArrayList<ParamsVO>>();
	
	
	/**
	 * handle connecting child thread to main thread
	 */
	private Handler messageHandler = new Handler() {
		/**
		 * @param msg of type Message
		 * function which will be called on load progress or load complete
		 * @author rajesh
		 * @date 23 feb 2012
		 */
		public void handleMessage(Message msg) {
			
			int type = msg.what;
			DownloadRunnable runnable = (DownloadRunnable)msg.obj;
			ParamsVO params = runnable.get_params();
			if(type == COMPLETE)
			{
				ResultVO result = runnable.get_result();
				runnable.reset();
				cancelThread(result.get_url());
				if(pendingRequest.get(params.get_url()) == null)
				{
					params.get_listener().onResult(result);
				}else
				{
					ArrayList<ParamsVO> requestParamsList = pendingRequest.get(params.get_url());
					for(ParamsVO param : requestParamsList)
					{
						param.get_listener().onResult(result);
					}
					pendingRequest.remove(params.get_url());
				}
				runnable = null;
			}else
			{
				if(pendingRequest.get(params.get_url()) == null)
				{
					params.get_listener().onProgress(runnable.get_progress());
				}else
				{
					ArrayList<ParamsVO> requestParamsList = pendingRequest.get(params.get_url());
					for(ParamsVO param : requestParamsList)
					{
						param.get_listener().onProgress(runnable.get_progress());
					}
				}
			}
			
			//super.handleMessage(msg);
		}
	};
	
	
	
	
	/**
	 * @param of type null
	 * @return _instance of type DownLoadManager
	 * getter function for _instance
	 * @author rajesh
	 * @date 7 feb 2012
	 */
	public static DownLoadManager get_instance() {
		_instance = (_instance == null) ? new DownLoadManager(new SingleTonEnforcer()) : _instance;
		return _instance;
	}

	/**
	 * Constructor function
	 * @param singleTonEnforcer of type SingleTonEnforcer
	 */
	public DownLoadManager(SingleTonEnforcer singleTonEnforcer) {
		// TODO Auto-generated constructor stub
	}
	
	
	
	/**
	 * @param params of type ParamsVO
	 * @return of type null
	 * function which will set the initial steps for the loading 
	 * @author rajesh
	 * @date 7 feb 2012
	 */
	public void getAsset(ParamsVO params, boolean saveResult) {
		
		if(!saveResult)
		{
			loadAsset(params);
		}else if(bitmapCache.get(params.get_url()) == null && stringCache.get(params.get_url()) == null)
		{
			if(!checkPendingRequest(params))
			{
				loadAsset(params);
			}
		}else
		{
			ResultVO result = new ResultVO("Sucsess", 200);
			if(params.get_resultFormat() == ResultFormats.IMAGE)
			{
				result.set_bitmap(bitmapCache.get(params.get_url()));
			}else
			{
				result.set_resultString(stringCache.get(params.get_url()));
			}
			result.set_url(params.get_url());
			params.get_listener().onResult(result);
		}
	}
	
	
	/**
	 * @param params of type ParamsVO
	 * @return of type null
	 * function which will initiate loading of assets 
	 * @author rajesh
	 * @date 9 feb 2012
	 */
	private void loadAsset(ParamsVO params)
	{
		try
		{
			Thread thread = new Thread(new DownloadRunnable(params, messageHandler, getRequest(params)));
			runningThreads.put(params.get_url(), thread);
			thread.start();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param runnable of type DownloadRunnable
	 * @return of type null
	 * function which will handle the complete of the runnable 
	 * @author rajesh
	 * @date 7 feb 2012
	 */
	public void observe(DownloadRunnable runnable, int type) {
		Message msg = new Message();
		msg.what = type;
		msg.obj = runnable;
		messageHandler.sendMessage(msg);
	}
	
	/**
	 * @param key of type String 
	 * @return of type null
	 * @author rajesh
	 * function which will remove the running thread 
	 * @author rajesh
	 * @date 7 feb 2012
	 */
	public void cancelThread(String key)
	{
		try
		{
			Thread thread = runningThreads.get(key);
			if(thread.isAlive())
			{
				thread.interrupt();
			}
			runningThreads.remove(key);
			thread = null;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * @param params of type ParamsVO
	 * @retur request of type HTTPUriRequest
	 * function which will create the request 
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	private HttpUriRequest getRequest(ParamsVO params) throws Exception
	{
		HttpUriRequest request = null;
		switch (params.get_requestType()) {
			case RequestMethords.GET:
			{
				request = new HttpGet(params.get_url() + ParamsUtils.getParamsString(params));
				ParamsUtils.addHeaders(request, params);
				break;
			}
			case RequestMethords.POST:
			{
				HttpPost postRequest = new HttpPost(params.get_url());
                ParamsUtils.addHeaders(request, params);
                if(params.get_params() != null && !params.get_params().isEmpty()){
                	postRequest.setEntity(new UrlEncodedFormEntity(params.get_params(), HTTP.UTF_8));
                }
                request = postRequest;
				break;
			}
			default:
				break;
		}
		return request;
	}
	
	/**
	 * @param params of type ParamsVO
	 * @return of type Boolean
	 * function which will check for the pending request of the same asset 
	 * @author rajesh
	 * @date 9 feb 2012
	 */
	private boolean checkPendingRequest(ParamsVO params)
	{
		boolean pending;
		ArrayList<ParamsVO> requestParamsList;
		if(pendingRequest.get(params.get_url()) != null)
		{
			pending = true;
			requestParamsList = pendingRequest.get(params.get_url());
			requestParamsList.add(params);
			pendingRequest.put(params.get_url(), requestParamsList);
		}else
		{
			pending = false;
			requestParamsList = new ArrayList<ParamsVO>();
			requestParamsList.add(params);
			pendingRequest.put(params.get_url(), requestParamsList);
		}
		return pending;
	}
	
	
	/**
	 * Singleton Class
	 * @author rajesh
	 */
	private static class SingleTonEnforcer
	{
		public SingleTonEnforcer()
		{
			
		}
	}
	
}
