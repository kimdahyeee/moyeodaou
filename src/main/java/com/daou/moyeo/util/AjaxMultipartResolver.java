
package com.daou.moyeo.util;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class AjaxMultipartResolver extends CommonsMultipartResolver {
    
    private static ThreadLocal<AjaxProgressListener> progressListener = new ThreadLocal<AjaxProgressListener>();
   
    public static int cnt = 0;
    
    private AjaxProgressListener getListener() {
        
        AjaxProgressListener listener = progressListener.get();
        //System.out.println("getListener()");
        
        if(listener == null)
        {
            listener = new AjaxProgressListener();
            progressListener.set(listener);
        }
        return listener;
    }
     
    @Override
    protected FileUpload prepareFileUpload(String encoding) {
        FileUpload fileUpload = getFileUpload();
        FileUpload actualFileUpload = fileUpload;
        System.out.println("AjaxMultipartResolver.prepareFileUpload()");
        actualFileUpload = newFileUpload(getFileItemFactory());
        actualFileUpload.setSizeMax(fileUpload.getSizeMax());
        actualFileUpload.setHeaderEncoding(encoding);
        
        actualFileUpload.setProgressListener(getListener());
        return actualFileUpload;
    }
 
    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
    	 System.out.println("AjaxMultipartResolver.resolveMultipart()");
         
    	try {           
            AjaxProgressListener listener = getListener();
            
            /*
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            
            List<FileItem> itemList = upload.parseRequest(request);
            Iterator<FileItem> iter = itemList.iterator();
            while(iter.hasNext()){
            	FileItem item = (FileItem)iter.next();
            	if(!(item.isFormField())){	//upload 파일 요청일 때
            		String fileName = item.getName();
            		System.out.println("파일이름:"+fileName+"/jsp id:"+item.getFieldName()+"/size:"+item.getSize());
            		
            	}
            }
            */
            
            System.out.println(request.getParameter("file_"+cnt));
            listener.setUploadId(request.getParameter("file_"+cnt));
            cnt++;
            listener.setSession(request.getSession());
             
            return super.resolveMultipart(request);
        } catch(MaxUploadSizeExceededException ex) {
             throw new MultipartException(ex.getMessage());
        } catch (Exception ex) {
            //exception is typically thrown when user hits stop or cancel
            //button halfway through upload
            throw new MultipartException(ex.getMessage());
        }
    }
}
