/**
 * 
 */
package com.sbdownloadmanager.paramutils;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;

import com.sbdownloadmanager.vo.ParamsVO;

/**
 * @author rajesh
 *
 */
public class ParamsUtils {

	/**
	 * 
	 */
	public ParamsUtils(SingleTonEnforcer singletonEnforcer) {
		// TODO Auto-generated constructor stub
	}
	
	private static class SingleTonEnforcer
	{
		
	}
	
	/**
	 * @param paramsVO of type ParamsVO
	 * @return combinedParams of type String 
	 * function which will create the params String 
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public static String getParamsString(ParamsVO paramsVO) throws Exception
	{
		ArrayList<NameValuePair> _params = paramsVO.get_params();
		String combinedParams = "";
		if(_params != null)
		{
	        if(!_params.isEmpty()){
	            combinedParams += "?";
	            for(NameValuePair p : _params)
	            {
	                String paramString = p.getName() + "=" + URLEncoder.encode(p.getValue(),"UTF-8");
	                if(combinedParams.length() > 1)
	                {
	                    combinedParams  +=  "&" + paramString;
	                }
	                else
	                {
	                    combinedParams += paramString;
	                }
	            }
	        }
		}
        return combinedParams;
	}
	
	/**
	 * @param paramsVO of type ParamsVO
	 * @param resquest of type HTTPUriRequest
	 * @return of type null
	 * @author rajesh
	 * @date 8 feb 2012
	 */
	public static void addHeaders(HttpUriRequest request, ParamsVO params)
	{
		ArrayList<NameValuePair> headers = params.get_headers();
		if(headers != null && !headers.isEmpty())
		{
			for(NameValuePair h : headers)
            {
                request.addHeader(h.getName(), h.getValue());
            }
		}
	}
	
	

}
