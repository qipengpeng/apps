package com.ugo.bus;

/**
 * @author zsp
 */
public interface BusListener {
	
	/**
	 * 设备在线
	 * @param deviceId
	 */
	void online(String deviceId);
	
	/**
	 * 设备离线
	 * @param deviceId
	 */
	void offline(String deviceId);
	
	/**
	 * 
	 * @param deviceId
	 */
	void lastWill(String deviceId);
	
	/**
	 * 机器收到出货指令后 ，立即回复是否可执行
	 * @param deviceId
	 * @param columnNo
	  * @param status 0 正常 1 设备占用 2 设备故障 3 指令超时 
	 * @param statusMsg 说明描述
	 */
	void receivedReplyDeliverMsg(String msgId, String deviceId,int columnNo, int status,String statusMsg);
	
	
	/**
	 * 机器出货后上报结果
	 * @param msgId 
	 * @param deviceId
	 * @param columnNo
	 * @param status 0 正常 1 设备占用 2 设备故障 3 指令超时
	 * @param statusMsg
	 */
	void receivedDeliverLogMsg(String msgId,String deviceId,int columnNo,int status,String statusMsg);
}
