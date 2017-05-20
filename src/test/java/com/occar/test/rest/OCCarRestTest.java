package com.occar.test.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

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
import com.occar.bean.rest.RouteBean;
import com.occar.bean.rest.TripBean;
import com.occar.bean.rest.UploadDocumentBean;
import com.occar.bean.rest.VehicleBean;
import com.rg.service.bean.rest.UserBean;
import com.rg.service.constant.CommonConstants;

public class OCCarRestTest {
//	public static final String HTTP_SERVICE_URL = "http://service-occarapp.rhcloud.com/rest";
	private static final String HTTP_SERVICE_URL = "http://localhost:8080/service/rest";
	private static final String TEST_DIRECTORY_URL = "D:/";
	
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
	public void getDriverServiceTest() { 
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		DriverServiceClient driverService = target.proxy(DriverServiceClient.class);
		DriverBean bean = driverService.getDriverDetails("17");
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
		//Latitude and Logitude from GeoApiContext
		Response response = simple.findLocation("12.9372378", "80.2070472");
		System.out.println(response.readEntity(String.class));
		Assert.assertNotNull(response);
	}
	
	/**
	 * Ramnath
	 * Test document upload service
	 * */
	@Test
	public void documentUploadTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL+"/file");
		MultipartFormDataOutput mfd = new MultipartFormDataOutput();
		mfd.addFormData("uploadedDoc", getTestDocContent(), MediaType.APPLICATION_OCTET_STREAM_TYPE);
//		mfd.addFormData("person", getPersonBean(), MediaType.APPLICATION_OCTET_STREAM_TYPE);
		mfd.addFormData("personId", "1", MediaType.TEXT_PLAIN_TYPE);
//		mfd.addFormData("personName", "Ramnath", MediaType.TEXT_PLAIN_TYPE);
		mfd.addFormData("docExtension", "png", MediaType.TEXT_PLAIN_TYPE);
		mfd.addFormData("docType", "DummyType", MediaType.TEXT_PLAIN_TYPE);
		GenericEntity<MultipartFormDataOutput> entityObj = new GenericEntity<MultipartFormDataOutput>(mfd){};
		System.out.println("Sending MultiPart request");
		Response response = target.request().post(Entity.entity(entityObj, MediaType.MULTIPART_FORM_DATA));
		System.out.println(response.readEntity(String.class));
		Assert.assertNotNull(response);
	}
	
	/**
	 * Test document update
	 */
	@Test
	public void documentUpdateTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL+"/file");
		MultipartFormDataOutput mfd = new MultipartFormDataOutput();
		mfd.addFormData("uploadedDoc", getTestDocContent(), MediaType.APPLICATION_OCTET_STREAM_TYPE);
		mfd.addFormData("docId", "2", MediaType.TEXT_PLAIN_TYPE);
		mfd.addFormData("docType", "DummyType", MediaType.TEXT_PLAIN_TYPE);
		GenericEntity<MultipartFormDataOutput> entityObj = new GenericEntity<MultipartFormDataOutput>(mfd){};
		System.out.println("Sending MultiPart request");
		Response response = target.request().put(Entity.entity(entityObj, MediaType.MULTIPART_FORM_DATA));
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
		Response response = docClient.deleteFile("9","1");
		System.out.println(response.readEntity(String.class));
		Assert.assertNotNull(response);
	}
	
	@Test
	public void documentReadTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		DocumentRestClient docClient = target.proxy(DocumentRestClient.class);
		UploadDocumentBean bean = docClient.getDocumentDetails("25");
		System.out.println(bean);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void passengerGetRouteTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		TripRestServiceClient passClient = target.proxy(TripRestServiceClient.class);
		List<RouteBean> routes = passClient.getRoutesForATrip("S1", "S2");
		System.out.println(routes);
		Assert.assertNotNull(routes);
	}
	
	@Test
	public void driverStartTripTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		TripRestServiceClient tripClient = target.proxy(TripRestServiceClient.class);
		TripBean bean = new TripBean();
		bean.setDriverPersonId(17);
		bean.setRouteId(46);
		bean.setOriginLocId(35);
		bean.setDestLocId(45);
		bean.setDescription("Jolly Trip to Siruseri");
		bean.setOperation(CommonConstants.TRIP_OPERATION_DRIVER_START);
		System.out.println(tripClient.startOrJoinTrip(bean));
		Assert.assertNotNull("Success");
	}
	
	@Test
	public void linkPassengerToTripTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		TripRestServiceClient tripClient = target.proxy(TripRestServiceClient.class);
		TripBean bean = new TripBean();
		bean.setPassengerId(24);
		bean.setTripId(70);
		String response = tripClient.startOrJoinTrip(bean);
		System.out.println(response);
		Assert.assertNotNull(response);
	}
	
	@Test
	public void fullTripTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		
		DriverServiceClient driverService = target.proxy(DriverServiceClient.class);
		DriverBean driver = new DriverBean();
		driver.setName("Richy Driver");
		driver.setCurrentVehicleTypeId(15);
		driver.setNoOfSeats(4);
		driver.setCurrentVehicle("TN11 W 0117");
		
		String driverId = driverService.saveDriverDetails(driver);
		System.out.println("Driver Created :: " + driverId);
		
		PersonRestServiceClient userService = target.proxy(PersonRestServiceClient.class);
		UserBean passenger = new UserBean();
		passenger.setName("Richy Passenger");
		String passengerId = userService.saveUser(passenger);
		System.out.println("Passenger Created :: " + passengerId);
		
		TripRestServiceClient tripClient = target.proxy(TripRestServiceClient.class);
		TripBean bean = new TripBean();
		bean.setDriverPersonId(Integer.valueOf(driverId));
		bean.setDescription("Jolly Trip to Siruseri");
		bean.setOperation(CommonConstants.TRIP_OPERATION_DRIVER_START);
		String tripId = tripClient.startOrJoinTrip(bean);
		System.out.println("Trip Created :: " + tripId);
		
		bean = new TripBean();
		bean.setPassengerId(Integer.valueOf(passengerId));
		bean.setTripId(Integer.valueOf(tripId));
		String response = tripClient.startOrJoinTrip(bean);
		System.out.println(response);
		Assert.assertNotNull(response);
		
		passenger = new UserBean();
		passenger.setName("Richy Passenger New");
		passengerId = userService.saveUser(passenger);
		System.out.println("Passenger Created :: " + passengerId);
		
		bean = new TripBean();
		bean.setPassengerId(Integer.valueOf(passengerId));
		bean.setTripId(Integer.valueOf(tripId));
		response = tripClient.startOrJoinTrip(bean);
		System.out.println(response);
		Assert.assertNotNull(response);
	}
	
	/**
	 * Gives the byte array of file in local. 
	 * Used by the test method
	 * @return
	 */
	private byte[] getTestDocContent(){
		FileInputStream fis;
		byte [] fileContent = null;
		try {
			fis = new FileInputStream(new File(TEST_DIRECTORY_URL+"Dummy.png"));
			fileContent = IOUtils.toByteArray(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}
	
}
