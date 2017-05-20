package com.occar.test.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.junit.Assert;
import org.junit.Test;

import com.occar.bean.rest.DriverBean;
import com.occar.bean.rest.VehicleBean;
import com.rg.service.bean.rest.UserBean;

public class SprintOneTester {
	public static final String HTTP_SERVICE_URL = "http://service-occarapp.rhcloud.com/rest";

	private static final String TEST_DIRECTORY_URL = "C:/Users/Ramnath/Desktop/";

	@Test
	public void getVehicleServiceTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		VehicleRestServiceClient driverService = target.proxy(VehicleRestServiceClient.class);
		VehicleBean bean = driverService.getVehicleDetails("19");
		System.out.println(bean);
		Assert.assertNotNull(bean);

		bean.setVehicleId(19);
		bean.setChassisNumber("JAJJKJ912310");
		bean.setEngineNumber("50540JAJKAJSDKJ510510510");
		driverService.updateVehicleDetails(bean);

		bean.setPersonId(17);
		bean.setVehicleId(0);
		bean.setRegNumber("TN22 C 8066");
		bean.setTypeId(15);
		driverService.createVehicleDetails(bean);
	}

	@Test
	public void deleteVehicleServiceTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		VehicleRestServiceClient driverService = target.proxy(VehicleRestServiceClient.class);
		/*
		VehicleBean bean = new VehicleBean();
		bean.setRegNumber("TN22 C 8066");
		bean.setPersonId(17);
		bean.setTypeId(15);
		driverService.createVehicleDetails(bean);
		*/
		driverService.deleteVehicleDetails("17", "30");
	}

	@Test
	public void getDriverServiceTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		DriverServiceClient driverService = target.proxy(DriverServiceClient.class);
		DriverBean bean = driverService.getDriverDetails("3");
		System.out.println(bean);
		Assert.assertNotNull(bean);
	}

	@Test
	public void driverServiceTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		DriverServiceClient driverService = target.proxy(DriverServiceClient.class);
		DriverBean bean = new DriverBean();
		bean.setName("Richy Driver");
		bean.setCurrentVehicleTypeId(15);
		bean.setCurrentVehicle("TN11 W 0117");
		driverService.saveDriverDetails(bean);
		Assert.assertNotNull("success");
	}

	@Test
	public void userServiceTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		PersonRestServiceClient userService = target.proxy(PersonRestServiceClient.class);
		Response response = userService.getUser("1");
		System.out.println(response.readEntity(String.class));
		Assert.assertNotNull(response);
	}

	@Test
	public void userServicePostTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		PersonRestServiceClient userService = target.proxy(PersonRestServiceClient.class);
		UserBean bean = new UserBean();
		bean.setName("Richy");
		userService.saveUser(bean);
	}

	@Test
	public void pojoGetServiceTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		SimpleRESTPojoClient simple = target.proxy(SimpleRESTPojoClient.class);
		Response response = simple.pojo();
		System.out.println(response.readEntity(String.class));
		Assert.assertNotNull(response);
	}

	@Test
	public void dbGetServiceTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		DBRestClient simple = target.proxy(DBRestClient.class);
		Response response = simple.query("select m from Menu m", "richy");
		System.out.println(response.readEntity(String.class));
		Assert.assertNotNull(response);
	}

	/**
	 * Dhanan Jeyan
	 * 
	 * Test Location find service. In case of Net disconnection use local host.
	 * 
	 * If you want to see the results before commit to TEST Env, Use local host.
	 */
	@Test
	public void locationFindTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		LocationRestClient simple = target.proxy(LocationRestClient.class);
		// Latitude and Logitude from GeoApiContext
		Response response = simple.findLocation("22", "33");
		System.out.println(response.readEntity(String.class));
		Assert.assertNotNull(response);
	}

	/**
	 * Ramnath Test document upload service
	 */
	@Test
	public void documentUploadTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL + "/file/upload");
		MultipartFormDataOutput mfd = new MultipartFormDataOutput();
		mfd.addFormData("uploadedDoc", getTestDocContent(), MediaType.APPLICATION_OCTET_STREAM_TYPE);
		// mfd.addFormData("person", getPersonBean(),
		// MediaType.APPLICATION_OCTET_STREAM_TYPE);
		// mfd.addFormData("personId", "1234", MediaType.TEXT_PLAIN_TYPE);
		mfd.addFormData("personName", "Ramnath", MediaType.TEXT_PLAIN_TYPE);
		mfd.addFormData("docExtension", "png", MediaType.TEXT_PLAIN_TYPE);
		mfd.addFormData("docType", "DummyType", MediaType.TEXT_PLAIN_TYPE);
		GenericEntity<MultipartFormDataOutput> entityObj = new GenericEntity<MultipartFormDataOutput>(mfd) {
		};
		System.out.println("Sending MultiPart request");
		Response response = target.request().post(Entity.entity(entityObj, MediaType.MULTIPART_FORM_DATA));
		System.out.println(response.readEntity(String.class));
		Assert.assertNotNull(response);
	}

	/**
	 * Test document delete service
	 * 
	 */
	@Test
	public void documentDeleteTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		DocumentRestClient docClient = target.proxy(DocumentRestClient.class);
		Response response = docClient.deleteFile("2","1");
		System.out.println(response.readEntity(String.class));
		Assert.assertNotNull(response);
	}

	/**
	 * Gives the byte array of file in local. Used by the test method
	 * 
	 * @return
	 */
	private byte[] getTestDocContent() {
		FileInputStream fis;
		byte[] fileContent = null;
		try {
			fis = new FileInputStream(new File(TEST_DIRECTORY_URL + "Dummy.png"));
			fileContent = IOUtils.toByteArray(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}

}
