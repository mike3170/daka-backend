package com.stit.utils;

import com.stit.common.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Helper method to create ResponseEntity
 * 1. ok(data)
 * 2. ok(data, message)
 * 3. ok(data, message, success)
 * 4. ok()
 * 5. created(data)
 * 6. noContent() 
 */
public class RespUtils {
	private static final Logger log = LoggerFactory.getLogger(RespUtils.class);

	/** 
	 * 200 - HttpStatus.OK
	 * @param 	data
	 * @return	ResponseEntity
	 */
	public static ResponseEntity<ApiResponse> ok(Object data) {
		ApiResponse resp = ApiResponse.ok(data);
		return ResponseEntity.ok(resp);
	}

	/** 
	 * 200 - HttpStatus.OK
	 * @param 	data
	 * @param 	message
	 * @return	ResponseEntity
	 */
	public static ResponseEntity<ApiResponse> ok(Object data, String message) {
		ApiResponse resp  = ApiResponse.ok(data, message);
		return ResponseEntity.ok(resp);
	}


	/** 
	 * 200 - HttpStatus.OK
	 * just succcess	true/false
	 * @return	ResponseEntity
	 */
	public static ResponseEntity<ApiResponse> ok( ) {
		ApiResponse resp = ApiResponse.ok();
		return ResponseEntity.ok(resp);
	}

	/** 
	 * 200 - HttpStatus.OK
	 * Succcess case, no data, no message
	 * @return	ResponseEntity
	 */
	public static ResponseEntity<ApiResponse> succcess( ) {
		ApiResponse resp = ApiResponse.ok();
		return ResponseEntity.ok(resp);
	}

	/** 
	 * 200 - HttpStatus.OK
	 * Fail case with message.
	 * @param message
	 * @return	ResponseEntity
	 */
	public static ResponseEntity<ApiResponse> fail(String message) {
		ApiResponse resp = ApiResponse.fail(message);
		return ResponseEntity.ok(resp);
	}


	/** 
	 * 201 - HttpStatus.CREATED
	 * @param 	data
	 * @return	ResponseEntity
	 */
	public static ResponseEntity<ApiResponse> created(Object data) {
		ApiResponse resp = ApiResponse.ok(data);
		return ResponseEntity.status(HttpStatus.CREATED).body(resp);
	}

}
