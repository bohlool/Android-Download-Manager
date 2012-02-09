/**
 * 
 */
package com.sbdownloadmanager.threadUtils;

/**
 * @author rajesh
 *
 */
public interface RequestObserver {
	
	public void observe(DownloadRunnable runnable, String type);
	
}
