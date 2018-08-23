package com.ugo.entity;

/**
 * @author sunshangfeng
 *货道表
 */
public class Channels {
	private int id;
	private int seq;
	private int vendor;
	
	public int getVendor() {
		return vendor;
	}
	public void setVendor(int vendor) {
		this.vendor = vendor;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	@Override
	public String toString() {
		return "Channels [id=" + id + ", seq=" + seq + ", vendor=" + vendor + "]";
	}
	
}
