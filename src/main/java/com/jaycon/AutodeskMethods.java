package com.jaycon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import com.autodesk.client.ApiException;
import com.autodesk.client.ApiResponse;
import com.autodesk.client.api.BucketsApi;
import com.autodesk.client.api.DerivativesApi;
import com.autodesk.client.api.ObjectsApi;
import com.autodesk.client.auth.Credentials;
import com.autodesk.client.auth.OAuth2TwoLegged;
import com.autodesk.client.model.Bucket;
import com.autodesk.client.model.Job;
import com.autodesk.client.model.JobPayload;
import com.autodesk.client.model.JobPayloadInput;
import com.autodesk.client.model.JobPayloadItem;
import com.autodesk.client.model.JobPayloadOutput;
import com.autodesk.client.model.Manifest;
import com.autodesk.client.model.ObjectDetails;
import com.autodesk.client.model.PostBucketsPayload;
import com.jaycon.model.AutodeskCredentials;

@Configuration
public class AutodeskMethods {
	private static final String CLIENT_ID = "qbunDHRpegsspdTLJNRAQGudK8epKHfM";
	private static final String CLIENT_SECRET = "frtlOKas8A74amR9";
	private static String guid = "";
	private static final String BUCKET_KEY = "forge-java-app-" + CLIENT_ID.toLowerCase();
	//private static  String BUCKET_KEY = "forge-java-sample-app-atdaaadfdwghg";
	
	// Initialize the relevant clients; in this example, the Objects, Buckets and Derivatives clients, which are part of the Data Management API and Model Derivatives API
	private static final BucketsApi bucketsApi = new BucketsApi();
	private static final ObjectsApi objectsApi = new ObjectsApi();
	private static final DerivativesApi derivativesApi = new DerivativesApi();

	private static OAuth2TwoLegged oauth2TwoLegged;
	private static Credentials twoLeggedCredentials;
	
	
	/**
	 * Initialize the 2-legged OAuth 2.0 client, and optionally set specific scopes.
	 * @throws Exception
	 */

	private static void initializeOAuth() throws Exception {

	    // You must provide at least one valid scope
	    List<String> scopes = new ArrayList<String>();
	    scopes.add("data:read");
	    scopes.add("data:write");
	    scopes.add("bucket:create");
	    scopes.add("bucket:read");

	    //Set autoRefresh to `true` to automatically refresh the access token when it expires.
	    oauth2TwoLegged = new OAuth2TwoLegged(CLIENT_ID, CLIENT_SECRET, scopes, true);
	    twoLeggedCredentials = oauth2TwoLegged.authenticate();
	}

	/**
	 * Example of how to create a new bucket using Forge SDK.
	 * Uses the oauth2TwoLegged and twoLeggedCredentials objects that you retrieved previously.
	 * @throws com.autodesk.client.ApiException
	 * @throws Exception
	 */
	private static void createBucket(MultipartFile f) throws ApiException, Exception {
	    System.out.println("***** Sending createBucket request" );
	    PostBucketsPayload payload = new PostBucketsPayload();
	    payload.setBucketKey(BUCKET_KEY);
	    payload.setPolicyKey(PostBucketsPayload.PolicyKeyEnum.PERSISTENT);
	    ApiResponse<Bucket> response = bucketsApi.createBucket(payload, "US", oauth2TwoLegged, twoLeggedCredentials);
	    System.out.println("***** Response for createBucket: " + response.getData());
	}

	/**
	 * Example of how to upload a file to the bucket.
	 * Uses the oauth2TwoLegged and twoLeggedCredentials objects that you retrieved previously.
	 * @throws java.io.FileNotFoundException
	 * @throws com.autodesk.client.ApiException
	 * @throws Exception
	 */
	private static ObjectDetails uploadFile(MultipartFile f) throws FileNotFoundException, ApiException, Exception {
	    System.out.println("***** Sending uploadFile request" );
	    System.out.println(f.getOriginalFilename());
	    
	   // File fileToUpload = new File( f.getOriginalFilename());
	  //  f.transferTo(fileToUpload);
	    File fileToUpload = new File(f.getOriginalFilename());
	    fileToUpload.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(fileToUpload); 
	    fos.write(f.getBytes());
	    fos.close(); 
	    //File fileToUpload = new File(FILE_PATH);
	    System.out.println("Apurv "+(int)fileToUpload.length());
	    System.out.println(oauth2TwoLegged);
	    System.out.println(twoLeggedCredentials);
	    
	    ApiResponse<ObjectDetails> response = objectsApi.uploadObject(BUCKET_KEY,f.getOriginalFilename(), (int)fileToUpload.length(), fileToUpload, null, null, oauth2TwoLegged, twoLeggedCredentials);

	    System.out.println("***** Response for uploadFile: ");
	    ObjectDetails objectDetails = response.getData();
	    System.out.println("Uploaded object Details - Location: " + objectDetails.getLocation() + ", Size:"+objectDetails.getSize());
	    return objectDetails;
	}

	/**
	 * Example of how to send a translate to SVF job request.
	 * Uses the oauth2TwoLegged and twoLeggedCredentials objects that you retrieved previously.
	 * @param urn - the urn of the file to translate
	 * @throws com.autodesk.client.ApiException
	 * @throws Exception
	 */
	private static Job translateToSVF(String urn) throws ApiException, Exception{
	    System.out.println("***** Sending Derivative API translate request" );

	    JobPayload job = new JobPayload();

	    byte[] urnBase64 = Base64.encodeBase64(urn.getBytes());

	    JobPayloadInput input = new JobPayloadInput();
	    input.setUrn(new String(urnBase64));

	    JobPayloadOutput output = new JobPayloadOutput();
	    JobPayloadItem formats = new JobPayloadItem();
	    formats.setType(JobPayloadItem.TypeEnum.SVF);
	    formats.setViews(Arrays.asList(JobPayloadItem.ViewsEnum._3D));
	    output.setFormats(Arrays.asList(formats));

	    job.setInput(input);
	    job.setOutput(output);

	    ApiResponse<Job> response = derivativesApi.translate(job,true,oauth2TwoLegged,twoLeggedCredentials);
	    System.out.println("***** Response for Translating File to SVF: " + response.getData());

	    return response.getData();
	}

	/**
	 * Example of how to query the status of a translate job.
	 * Uses the oauth2TwoLegged and twoLeggedCredentials objects that you retrieved previously.
	 * @param base64Urn - the urn of the file to translate in base 64 format
	 * @throws com.autodesk.client.ApiException
	 * @throws Exception
	 */
	private static Manifest verifyJobComplete(String base64Urn) throws ApiException, Exception{
	    System.out.println("***** Sending getManifest request" );
	    boolean isComplete = false;
	    ApiResponse<Manifest> response = null;

	    while(!isComplete){
	        response = derivativesApi.getManifest(base64Urn,null,oauth2TwoLegged,twoLeggedCredentials);
	        Manifest manifest = response.getData();
	        if(response.getData().getProgress().equals("complete")){
	            isComplete = true;
	            System.out.println("***** Finished translating your file to SVF - status: " + manifest.getStatus() + ", progress:" + manifest.getProgress());
	        }
	        else{
	            System.out.println("***** Haven't finished translating your file to SVF - status: " + manifest.getStatus() + ", progress:" + manifest.getProgress());
	            Thread.sleep(1000);
	        }
	    }

	    return response.getData();

	}
	public AutodeskCredentials getAutodeskCredentials(MultipartFile f) throws Exception
	{
	AutodeskCredentials credentials=new AutodeskCredentials();
	//try (InputStream in = new FileInputStream(f)) {
		  
    try{
        initializeOAuth();

        try{
            createBucket(f);
        }
        catch (ApiException e){
            System.err.println("Error creating bucket : " + e.getResponseBody());
        }

        try{
            ObjectDetails uploadedObject = uploadFile(f);

            try{
                Job job = translateToSVF(uploadedObject.getObjectId());

                if(job.getResult().equals("success")){
                    String base64Urn = job.getUrn();

                    Manifest manifest = verifyJobComplete(base64Urn);
                    if (manifest.getStatus().equals("success")){
                    	credentials.setToken(twoLeggedCredentials.getAccessToken());
                    	credentials.setUrn(manifest.getUrn());
                    	return credentials;
                    }
                }
            }
            catch (ApiException e){
                System.err.println("Error translating file : " + e.getResponseBody());
                return null;
            }

        }
        catch (ApiException e){
            System.err.println("Error uploading file : " + e.getResponseBody());
            return null;
        }
    }
    catch (ApiException e){
        System.err.println("Error Initializing OAuth client : " + e.getResponseBody());
        return null;
    }
    
	//}
	return credentials;
	}
}
