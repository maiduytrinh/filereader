package com.tp.filereader.rest.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;

public final class Response<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	@JsonInclude(Include.NON_NULL)
	private int status = 200;
	@JsonInclude(Include.NON_NULL)
	private String message = "OK";
	@JsonInclude(Include.NON_NULL)
	private boolean hasnext = false;
	@JsonInclude(Include.NON_NULL)
	private int nextoffset = 0;
	@JsonInclude(Include.NON_NULL)
	private T data;
	@JsonInclude(Include.NON_NULL)
	private Pagination pagination = null;
	@JsonInclude(Include.NON_NULL)
	private Link links = null;

	private Response() {

	}

	private Response(int code, String message, T data) {
		this.status = code;
		this.message = message;
		this.data = data;
	}

	private Response(int code, String message) {
		this.status = code;
		this.message = message;
	}

	public static <T> Response<T> ok(T data) {
		return response(ResponseCode.SUCCESS, ResponseMsg.OK, data);
	}

	public static <T> Response<T> ok() {
		return response(ResponseCode.SUCCESS, ResponseMsg.OK);
	}

	public static <T> Response<T> Done() {
		return response(ResponseCode.SUCCESS, ResponseMsg.Done);
	}

	public static <T> Response<T> ok(String message, T data) {
		return response(ResponseCode.SUCCESS, message, data);
	}

	public static <T> Response<T> created(T data) {
		return response(ResponseCode.CREATED, ResponseMsg.SUCCESS, data);
	}

	public static <T> Response<T> accepted(String msg) {
		return response(ResponseCode.ACCEPTED, msg, null);
	}

	public static <T> Response<T> created(String message, T data) {
		return response(ResponseCode.CREATED, message, data);
	}

	public static <T> Response<T> Found(T data) {
		return response(ResponseCode.ACCEPTED, ResponseMsg.FOUND, data);
	}

	public static <T> Response<T> response(int code, String messsage, T data) {
		Response<T> response = new Response<>(code, messsage, data);
		return response;
	}

	public static <T> Response<T> response(int code, String messsage) {
		Response<T> response = new Response<>(code, messsage);
		return response;
	}

	public static <T> Response<T> fail(T data) {
		return response(ResponseCode.FAIL, null, data);
	}

	public static <T> Response<T> unauthorized() {
		return response(ResponseCode.UNAUTHORIZED, ResponseMsg.UNAUTHORIZED, null);
	}

	public static <T> Response<T> fail(String msg) {
		return response(ResponseCode.FAIL, msg, null);
	}

	public static <T> Response<T> notFound(T data) {
		return response(ResponseCode.NOT_FOUND, null, data);
	}

	public static <T> Response<T> notFound(String msg, T data) {
		return response(ResponseCode.NOT_FOUND, msg, data);
	}

	public static <T> Response<T> notFound(String msg) {
		return response(ResponseCode.NOT_FOUND, msg, null);
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

	public boolean isHasnext() {
		return hasnext;
	}

	public Response<T> setHasnext(boolean hasnext) {
		this.hasnext = hasnext;
		return this;
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

	public int getNextoffset() {
		return nextoffset;
	}

	public Response<T> setNextoffset(int nextoffset) {
		this.nextoffset = nextoffset;
		return this;
	}

	
}
