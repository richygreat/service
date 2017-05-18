package com.occar.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.occar.bean.admin.AppConfig;
import com.occar.entity.Document;
import com.rg.service.business.CommonService;

import net.coobird.thumbnailator.Thumbnails;

/**
 * Servlet implementation class StaticContentRenderServlet
 */
@WebServlet(value = "/file", loadOnStartup = 1)
public class StaticContentRenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(StaticContentRenderServlet.class.getName());

	/* Only because of this inject the postConstruct will be called */
	@Inject
	private AppConfig appConfig;

	@Inject
	private CommonService service;

	@Override
	public void init() throws ServletException {
		super.init();
		log.info("Entering init");
		appConfig.dummy();

		try {
			Class<?> clazz = null;
			try {
				clazz = Class.forName("io.swagger.jaxrs.config.BeanConfig");
			} catch (ClassNotFoundException e) {
				log.info("Swagger Lib not included so skipping doc creation");
			}
			if (clazz != null) {
				Object obj = clazz.newInstance();
				clazz.getMethod("setVersion", String.class).invoke(obj, "1.0");
				clazz.getMethod("setHost", String.class).invoke(obj, "localhost:8080");
				clazz.getMethod("setBasePath", String.class).invoke(obj, "/service/rest");
				clazz.getMethod("setResourcePackage", String.class).invoke(obj, "com.occar.rest");
				clazz.getMethod("setTitle", String.class).invoke(obj, "OC Car App Service Rest API");
				clazz.getMethod("setScan").invoke(obj);
			}
		} catch (InstantiationException e) {
			log.log(Level.SEVERE, "Swagger Lib can not be instantiated", e);
		} catch (IllegalAccessException e) {
			log.log(Level.SEVERE, "Swagger Lib Private Constructor", e);
		} catch (NoSuchMethodException e) {
			log.log(Level.SEVERE, "Swagger Lib Method not found", e);
		} catch (SecurityException e) {
			log.log(Level.SEVERE, "Swagger Lib Private Method", e);
		} catch (IllegalArgumentException e) {
			log.log(Level.SEVERE, "Swagger Lib Method execution", e);
		} catch (InvocationTargetException e) {
			log.log(Level.SEVERE, "Swagger Lib Method execution", e);
		}

		log.info("Exiting init");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext cntx = request.getServletContext();
		// Get the absolute path of the image
		Document entityDoc = service.getDocumentById(Integer.valueOf(request.getParameter("docid")));
		// retrieve mimeType dynamically
		String mime = cntx.getMimeType(entityDoc.getFileName());
		if (mime == null) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}

		response.setContentType(mime);
		File file = new File(entityDoc.getFileName());

		File thumbsFile = new File(
				"/var/lib/openshift/576c156489f5cfda2f000103/app-root/data/files/thumbs-" + UUID.randomUUID() + ".jpg");
		if ("true".equalsIgnoreCase(request.getParameter("thumbs"))) {
			Thumbnails.of(file).size(150, 150).outputFormat("jpg").toFile(thumbsFile);
			file = thumbsFile;
		}

		response.setContentLength((int) file.length());

		FileInputStream in = new FileInputStream(file);
		OutputStream out = response.getOutputStream();

		// Copy the contents of the file to the output stream
		byte[] buf = new byte[1024];
		int count = 0;
		while ((count = in.read(buf)) >= 0) {
			out.write(buf, 0, count);
		}
		out.close();
		in.close();
		try {
			if (thumbsFile != null) {
				thumbsFile.delete();
			}
		} catch (Exception e) {
			log.info("Exception while closing the thumbsFile");
		}
	}

	public CommonService getService() {
		return service;
	}

	public void setService(CommonService service) {
		this.service = service;
	}

	public AppConfig getAppConfig() {
		return appConfig;
	}

	public void setAppConfig(AppConfig appConfig) {
		this.appConfig = appConfig;
	}
}
