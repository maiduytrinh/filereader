package com.tp.filereader.rest.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

public final class ResponseBase<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonInclude(Include.NON_NULL)
	private String message = "OK";
	@JsonInclude(Include.NON_NULL)
	private int status = 200;
	@JsonInclude(Include.NON_NULL)
	private T data;
	@JsonInclude(Include.NON_NULL)
	private Pagination pagination = null;
	@JsonInclude(Include.NON_NULL)
	private Link links = null;

	private ResponseBase() {

	}

	private ResponseBase(int code, String message, T data) {
		this.status = code;
		this.message = message;
		this.data = data;
	}

	private ResponseBase(int code, String message) {
		this.status = code;
		this.message = message;
	}

	public static <T> ResponseBase<T> ok(T data) {
		return responseBase(ResponseCode.SUCCESS, ResponseMsg.OK, data);
	}

	public static <T> ResponseBase<T> ok() {
		return responseBase(ResponseCode.SUCCESS, ResponseMsg.OK);
	}

	public static <T> ResponseBase<T> Done() {
		return responseBase(ResponseCode.SUCCESS, ResponseMsg.Done);
	}

	public static <T> ResponseBase<T> ok(String message, T data) {
		return responseBase(ResponseCode.SUCCESS, message, data);
	}

	public static <T> ResponseBase<T> created(T data) {
		return responseBase(ResponseCode.CREATED, ResponseMsg.SUCCESS, data);
	}

	public static <T> ResponseBase<T> accepted(String msg) {
		return responseBase(ResponseCode.ACCEPTED, msg, null);
	}

	public static <T> ResponseBase<T> created(String message, T data) {
		return responseBase(ResponseCode.CREATED, message, data);
	}

	public static <T> ResponseBase<T> Found(T data) {
		return responseBase(ResponseCode.ACCEPTED, ResponseMsg.FOUND, data);
	}

	public static <T> ResponseBase<T> responseBase(int code, String messsage, T data) {
		ResponseBase<T> response = new ResponseBase<>(code, messsage, data);
		return response;
	}

	public static <T> ResponseBase<T> responseBase(int code, String messsage) {
		ResponseBase<T> response = new ResponseBase<>(code, messsage);
		return response;
	}

	public static <T> ResponseBase<T> fail(T data) {
		return responseBase(ResponseCode.FAIL, null, data);
	}

	public static <T> ResponseBase<T> unauthorized() {
		return responseBase(ResponseCode.UNAUTHORIZED, ResponseMsg.UNAUTHORIZED, null);
	}

	public static <T> ResponseBase<T> fail(String msg) {
		return responseBase(ResponseCode.FAIL, msg, null);
	}

	public static <T> ResponseBase<T> notFound(T data) {
		return responseBase(ResponseCode.NOT_FOUND, null, data);
	}

	public static <T> ResponseBase<T> notFound(String msg, T data) {
		return responseBase(ResponseCode.NOT_FOUND, msg, data);
	}

	public static <T> ResponseBase<T> notFound(String msg) {
		return responseBase(ResponseCode.NOT_FOUND, msg, null);
	}

	static class ResponseMsg {
		public static String SUCCESS = "Success";
		public static String FOUND = "Found";
		public static String OK = "OK";
		public static String Done = "Done";
		public static String CREATED_SUCCESS = "Created Success";
		public static String UNAUTHORIZED = "Non-Authoritative Information";
	}

	static class ResponseCode {
		public static int SUCCESS = 200;
		public static int CREATED = 201;
		public static int ACCEPTED = 202;
		public static int UNAUTHORIZED = 203;
		public static int NOT_FOUND = 404;
		public static int FAIL = 400;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public Link getLinks() {
		return links;
	}

	public void setLinks(Link links) {
		this.links = links;
	}
	
}
