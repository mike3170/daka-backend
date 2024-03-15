package com.stit.common;
/**
 * 
 * ApiResponse 
 * 1. data			app data
 * 2. message		message
 * 3. succcess	boolean
 * 
 * 4. errCode   ??? not yet
 */
public final class ApiResponse  {
	private Object data;
	private Boolean success;

  //@JsonInclude(Include.NON_NULL)
	private String message;

	public ApiResponse() {
		this(null, true, null);
	}

	public ApiResponse(Object data, boolean success ) {
		this(data, success, null);
	}

	public ApiResponse(Object data, boolean success, String message) {
		if (! success && data != null) {
			throw new IllegalArgumentException("api fail can not with data payload");
		}
		this.data = data;
		this.success = success;
		this.message = message;
	}

	public static ApiResponse ok() {
		return new ApiResponse(null, true, null);
	}

	public static ApiResponse ok(Object data) {
		return new ApiResponse(data, true, null);
	}

	public static ApiResponse ok(Object data, String message) {
		return new ApiResponse(data, true, message);
	}

	public static ApiResponse fail() {
		return new ApiResponse(null, false, null);
	}

	public static ApiResponse fail(String message) {
		return new ApiResponse(null, false, message);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
