/**
 * 
 */
package com.sbdownloadmanager.threadUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.BitmapFactory;

import com.sbdownloadmanager.DownLoadManager;
import com.sbdownloadmanager.paramutils.ResultFormats;
import com.sbdownloadmanager.vo.ParamsVO;
import com.sbdownloadmanager.vo.ProgressVO;
import com.sbdownloadmanager.vo.ResultVO;

/**
 * @author rajesh
 *
 */
public class DownloadRunnable implements Runnable{
	private ParamsVO _params;
	private RequestObserver _listener;
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
	public DownloadRunnable(ParamsVO params, RequestObserver listener, HttpUriRequest request) {
		_params = params;
		_listener = listener;
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
	                int count = 0;
	                byte data[] = new byte[1024];
	                
	                long total = 0;
	                while ((count = instream.read(data)) != -1) {
	                    total += count;
	                    _progress.set_bytesLoaded(count);
	                    _progress.set_progress(total * (100 / _progress.get_totalBytes()));
	                    _listener.observe(this, DownLoadManager.PROGRESS);
	                }
                }
                
                if(_params.get_resultFormat() == ResultFormats.IMAGE)
                {
                	_result.set_bitmap(BitmapFactory.decodeStream(instream));
                }else
                {
                	_result.set_resultString(convertStreamToString(instream));
                }
                
            }
        }catch (Exception e)
        {
        	client.getConnectionManager().shutdown();
            e.printStackTrace();
        }
		
		_listener.observe(this, DownLoadManager.COMPLETE);
	}
	
	/**
	 * @param is of type InputStream
	 * @return of type String 
	 * function which convert the stream to String 
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	private static String convertStreamToString(InputStream is) {
		 
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
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
