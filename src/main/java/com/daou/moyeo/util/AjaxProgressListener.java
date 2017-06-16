package com.daou.moyeo.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.json.simple.JSONObject;

public class AjaxProgressListener implements ProgressListener{
	
	HttpSession session;											// 정보가 담길 session
	String uploadId;													// jsp단에서 넘어온 file id
	/*
	public AjaxProgressListener(HttpServletRequest request){
		//request.getSession().setAttribute("progress", this);
		session = request.getSession();
	}
	*/
	public void setUploadId(String uploadId){
		this.uploadId = uploadId;
	}
    public void setSession(HttpSession session){
    	this.session = session;
    }
	@Override
	public void update(long pBytesRead, long pContentLength, int pItems) {
		// TODO Auto-generated method stub
		//System.out.println("현재 " +pItems+" 읽고 있음");
		//System.out.println("uploadId " + uploadId);
		
		if(pContentLength == -1){
			System.out.println(+ pBytesRead + "bytes 읽혔음");
		}else{
			System.out.println(+ pBytesRead + " / " + pContentLength + " 읽혔음");	
		}
		
		//String attrName = "UPLOAD_INFO_PREFIX" + uploadId;
		String attrName = "UPLOAD_INFO_PREFIX";
		
		JSONObject currentUploadInfo = new JSONObject();
		currentUploadInfo.put("pByteRead", pBytesRead);
		currentUploadInfo.put("pContentLength", pContentLength);
		
		session.setAttribute(attrName, currentUploadInfo);
		
	}

	
}
