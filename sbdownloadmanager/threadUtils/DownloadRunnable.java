/**
 * 
 */
package com.sbdownloadmanager.threadUtils;

import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.sbdownloadmanager.DownLoadManager;
import com.sbdownloadmanager.paramutils.ResultFormats;
import com.sbdownloadmanager.vo.ParamsVO;
import com.sbdownloadmanager.vo.ProgressVO;
import com.sbdownloadmanager.vo.ResultVO;
import com.sourcebits.hoppr.HopprActivity;

/**
 * @author rajesh
 *
 */
public class DownloadRunnable implements Runnable{
	private ParamsVO _params;
	private Handler _listener;
	private ResultVO _result;
	private ProgressVO _progress;
	private HttpUriRequest _request;
	
	/**
	 * @param of type null
	 * @return _params of type ParamsVO
	 * getter funcltion for _params
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public ParamsVO get_params() {
		return _params;
	}
	
	/**
	 * @param of type null
	 * @return _result of type ResultVO
	 * getter funcltion for _result
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public ResultVO get_result() {
		return _result;
	}
	
	/**
	 * @param of type null
	 * @return _progress of type ProgressVO
	 * getter function for _progress
	 * @author rajesh
	 * @date 9 feb 2012
	 */
	public ProgressVO get_progress() {
		return _progress;
	}

	/**
	 * 
	 */
	public DownloadRunnable(ParamsVO params, Handler handler, HttpUriRequest request) {
		_params = params;
		_listener = handler;
		_request = request;
	}

	/**
	 * @param of type null
	 * @return of type null
	 * function which will loald the file 
	 * @author rajesh
	 * @date 7 feb 2012
	 */
	public void run() {
		HttpClient client = new DefaultHttpClient();
		 
        HttpResponse httpResponse;
		try {
			
            httpResponse = client.execute(_request);
            _result = new ResultVO(httpResponse.getStatusLine().getReasonPhrase(), httpResponse.getStatusLine().getStatusCode());
            _result.set_url(_params.get_url());
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
            	
                InputStream instream = entity.getContent();
                
                if(httpResponse.getHeaders("Content-Length").length > 0)
                {
                	_progress = (_progress == null) ? new ProgressVO() : _progress;
	                Header header = httpResponse.getHeaders("Content-Length")[0];
	                _progress.set_totalBytes(Float.valueOf(header.getValue()));
	                _progress.set_url(_params.get_url());
	                /*int count = 0;
	                byte data[] = new byte[254];
	                long total = 0;
	                while ((count = instream.read(data)) != -1) {
	                	Message msg = new Message();
	                	msg.obj = this;
	                    total += count;
	                    _progress.set_bytesLoaded(count);
	                    _progress.set_progress(total * (100 / _progress.get_totalBytes()));
	                    msg.what = DownLoadManager.PROGRESS;
	                    _listener.sendMessage(msg);
	                }*/
                }
                
                if(_params.get_resultFormat() == ResultFormats.IMAGE)
                {
                	_result.set_bitmap(BitmapFactory.decodeStream(instream));
                }else
                {
                	_result.set_resultString(convertStreamToString(instream));
                }
                instream.close();
                client.getConnectionManager().shutdown();
                Message msg = new Message();
            	msg.obj = this;
                msg.what = DownLoadManager.COMPLETE;
                _listener.sendMessage(msg);
            }
        }catch (Exception e)
        {
        	client.getConnectionManager().shutdown();
        	System.out.print(e.getMessage());
        }
		
	}
	
	/**
	 * @param is of type InputStream
	 * @return of type String 
	 * function which convert the stream to String 
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	private static String convertStreamToString(InputStream inputStream) {
		String result = ""; 
		try
		{
			StringBuffer buffer = new StringBuffer();
			byte[] data = new byte[256];
	        int len = 0;
	        while (-1 != (len = inputStream.read(data)) )
	        {
	            buffer.append(new String(data, 0, len));
	        }
	        result = buffer.toString();
		}catch(Exception e)
		{
			Log.d(HopprActivity.TOSS, e.getMessage());
		}
		return result;
    }
	
	/**
	 * @param of type null
	 * function which will reset the runnable 
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public void reset()
	{
		_params = null;
		_listener = null;
		_result = null;
		_request = null;
		_progress = null;
	}

}
