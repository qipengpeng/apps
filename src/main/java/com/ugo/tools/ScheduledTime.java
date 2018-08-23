/**
 * 
 */
package com.ugo.tools;

/**
 * @author qipeng 2018/8/17
 *	预定时间参数
 */
public class ScheduledTime {
	
	public final static String node_OFF = "0 59 23 ? * FRI";//点位关闭时间
	
	public final static String node_NO = "0 59 23 ? * SUN";//点位开启时间
	
	public final static String offline_timer = "0 0/10 * * * ?";//定时检测-离线
	
	public final static String pay_state = "0 0/3 * * * ?";//定时检测-未付款
	
	public final static int time = 10;//超过时间离线预警

	public final static String task_token ="0 0 0/1 * * ?";//企业微信定时-token
	
}
