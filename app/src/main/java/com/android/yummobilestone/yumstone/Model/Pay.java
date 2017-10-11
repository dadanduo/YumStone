package com.android.yummobilestone.yumstone.Model;

import java.io.Serializable;

public class Pay implements Serializable {
	private String proc_tp;
	private String pay_tp;
	private String amt;
	private String msg_tp;
	private String proc_cd;
	private String order_no;
	private String appid;
	private String time_stamp;
	private String order_info;
	public String getProc_tp() {
		return proc_tp;
	}
	public void setProc_tp(String proc_tp) {
		this.proc_tp = proc_tp;
	}
	public String getPay_tp() {
		return pay_tp;
	}
	public void setPay_tp(String pay_tp) {
		this.pay_tp = pay_tp;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getMsg_tp() {
		return msg_tp;
	}
	public void setMsg_tp(String msg_tp) {
		this.msg_tp = msg_tp;
	}
	public String getProc_cd() {
		return proc_cd;
	}
	public void setProc_cd(String proc_cd) {
		this.proc_cd = proc_cd;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(String time_stamp) {
		this.time_stamp = time_stamp;
	}
	public String getOrder_info() {
		return order_info;
	}
	public void setOrder_info(String order_info) {
		this.order_info = order_info;
	}
	
	
	

}
