package com.daou.moyeo.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.ProgressListener;

public class AjaxProgressListener implements ProgressListener{

	public AjaxProgressListener(HttpServletRequest request){
		request.getSession().setAttribute("progress", this);
	}
	@Override
	public void update(long pBytesRead, long pContentLength, int pItems) {
		// TODO Auto-generated method stub
		System.out.println("현재 " +pItems+" 읽고 있음");
		
		if(pContentLength == -1){
			System.out.println(+ pBytesRead + "bytes 읽혔음");
		}else{
			System.out.println(+pBytesRead +"중"+ pContentLength + " 읽혔음");	
		}
	}

	
}
