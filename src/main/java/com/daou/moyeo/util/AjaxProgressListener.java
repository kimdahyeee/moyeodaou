package com.daou.moyeo.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.json.simple.JSONObject;

public class AjaxProgressListener implements ProgressListener{
	
	HttpSession session;											// 정보가 담길 session
	
    public void setSession(HttpSession session){
    	this.session = session;
    }
	@Override
	public void update(long pBytesRead, long pContentLength, int pItems) {
		// TODO Auto-generated method stub
		
		String attrName = "UPLOAD_INFO_PREFIX";
		
		JSONObject currentUploadInfo = new JSONObject();
		currentUploadInfo.put("pByteRead", pBytesRead);
		currentUploadInfo.put("pContentLength", pContentLength);
		
		session.setAttribute(attrName, currentUploadInfo);
		
	}

	
}
