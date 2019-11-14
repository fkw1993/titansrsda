package com.titansoft.model;

public class Result {
	private String message;
	private Object data;
	private String code;
	
	public Result(){
	}
	public Result(String code, String message, Object data){
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public static Result FAIL(String message){
		return new Result("400", message,"");
	}
	
	public static Result SUCCESS(String message){
		return new Result("200", message, "");
	}
	
	public static Result SUCCESS(Object data){
		return new Result("200", "success", data);
	}
	
	public static Result SUCCESS(String message, Object data){
		return new Result("200", message, data);
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "Result [message=" + message + ", data=" + data + ", code=" + code + "]";
	}

}
