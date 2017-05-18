package com.rg.service.business;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.rg.service.util.common.CommonIOUtil;

@Named
@SessionScoped
public class CommonIOService implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(CommonIOService.class.getName()); 
	
	
	public CommonIOService() {
		log.info("Common IO Service constructor called");
	}
	
	public void writeToFile(final byte[] content, String fileName){
		log.info("Invoking file writer");
		CommonIOUtil.writeFile(content, fileName);
		log.info("Exit File writer");
	}
	
	public void deleteFile(String fileName){
		log.info("Invoking file delete");
		CommonIOUtil.deleteFile(fileName);
		log.info("Exit file delete");
	}
}
