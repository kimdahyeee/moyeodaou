
package com.daou.moyeo.util;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Service;
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
        actualFileUpload = newFileUpload(getFileItemFactory());
        actualFileUpload.setSizeMax(fileUpload.getSizeMax());
        actualFileUpload.setHeaderEncoding(encoding);
        actualFileUpload.setProgressListener(getListener());
        return actualFileUpload;
    }
 
    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
    	 
    	try {           
            AjaxProgressListener listener = getListener();
            
            listener.setSession(request.getSession());
             
            return super.resolveMultipart(request);
        } catch(MaxUploadSizeExceededException ex) {
             throw new MultipartException(ex.getMessage());
        } catch (Exception ex) {
            throw new MultipartException(ex.getMessage());
        }
    }
}
