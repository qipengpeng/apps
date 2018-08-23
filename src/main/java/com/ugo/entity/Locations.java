/**
 * 
 */
package com.ugo.entity;

import java.util.Date;

/**
 * @author sunshangfeng
 *货位表
 */
public class Locations {
	private int id;
	private int channelId;
	private int seq;
	private Date createdAt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getChannelId() {
		return channelId;
	}
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "Locations [id=" + id + ", channelId=" + channelId + ", seq=" + seq + ", createdAt=" + createdAt + "]";
	}
	
	
}
