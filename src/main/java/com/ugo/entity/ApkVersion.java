/**
 * 
 */
package com.ugo.entity;

/**
 * @author sunshangfeng
 *
 */
public class ApkVersion {
	private int id;
	private int versionCode;//版本号
	private String packageName;//包名
	private String md5;
	private String descArea;//备注
	private String downUrl;//安装包路径
	private Long updateTime;//更新时间
	
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public String getDescArea() {
		return descArea;
	}
	public void setDescArea(String descArea) {
		this.descArea = descArea;
	}
	public String getDownUrl() {
		return downUrl;
	}
	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	@Override
	public String toString() {
		return "ApkVersion [id=" + id + ", versionCode=" + versionCode + ", packageName=" + packageName + ", md5=" + md5
				+ ", descArea=" + descArea + ", downUrl=" + downUrl + ", updateTime=" + updateTime + "]";
	}
	
}
