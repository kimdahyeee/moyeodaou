package com.daou.moyeo.util;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


public class FileUtil {

	public static String getRandomName(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}
	
	/*
	 * Progress bar 구현을 위한 리스너 설정 
	 * */
	
	/**
	 *  file downLoad
	 * @param fileInfo
	 * @param response
	 * @throws IOException
	 */
	public void fileDownload(Map<String, Object> fileInfo, HttpServletResponse response) throws IOException{
		String filePath;						
		String originalFileName;		
		byte fileByte[] = null;
		
		filePath = (String)fileInfo.get("file_path");
		originalFileName = (String)fileInfo.get("file_name");
		
		fileByte = FileUtils.readFileToByteArray(new File("c:/home/mean17/fileStore/" +filePath));	
		//fileByte = FileUtils.readFileToByteArray(new File("/home/mean17/fileStore/" +filePath));	
		
		response.setContentType("application/octet-stream");
	    response.setContentLength(fileByte.length);
	    response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName,"UTF-8")+"\";");			// attachment : ÷������ , content-disposition : multipart-form / data    ,  UTF-8 ���ڵ� 
	    response.setHeader("Content-Transfer-Encoding", "binary");
	  
	    response.getOutputStream().write(fileByte);
	    
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
	    
	    
	}
	
	/**
	 * file upload
	 * @param mhsr
	 * @return
	 */
	public List<Map<String, Object>> fileUpload(MultipartHttpServletRequest mhsr){
		String path = "c:/home/mean17/fileStore/";
		//String path = "/home/mean17/fileStore/";

		MultipartFile mfile = null;							
		String originalFileName;							
		Iterator<String> iter;
		List<Map<String, Object>> fileInfoList = new ArrayList<Map<String, Object>>();		
		Map<String, Object> map = null;							
		String storedName = null;
		
		System.out.println("fileUpload() call");
		try{							
			iter = mhsr.getFileNames();
			File file = new File(path);
			
	        if(file.exists() == false){
	            file.mkdirs();
	        }
	        
			while (iter.hasNext()) { 
				
				mfile = mhsr.getFile(iter.next());	
				System.out.println("mfile 사이즈" + mfile.getSize());
				originalFileName = mfile.getOriginalFilename();
			
				String temp = originalFileName.substring(originalFileName.lastIndexOf('.'));
				storedName = getRandomName() + temp;
				file = new File(path + storedName);		
				
				if(mfile.isEmpty() == false){
					System.out.println("===================file=========================");
					System.out.println(file);
					mfile.transferTo(file);
				}
				
				map = new HashMap<String, Object>();
				map.put("FILE_ORIGINAL_NAME", originalFileName);
				map.put("FILE_STORED_NAME", storedName);
				map.put("FILE_SIZE", mfile.getSize());
				fileInfoList.add(map);
				
				System.out.println(storedName);
			}
			
		}catch (Exception e) {
			System.out.println("Hee - " + e.getMessage());
		}
		
		return fileInfoList;
	}
	
}

