package com.ugo.bus;


import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import ch.qos.logback.classic.Logger;

/**
 * 设备消息调用
 * @author zsp
 */
public class MsgBus {
	
	private static final Logger logger = (Logger) LoggerFactory.getLogger(MsgBus.class);
	
	public static final int QOS2 = 2;
	
	private String broker = "tcp://127.0.0.1:1883";
	private String clientId = "bus";
	private String username = "bus";
	private String password = "qawsedrftg!";

	private MqttClient client;
	private MqttConnectOptions connOpts;
	
	final public String PUB_TOPIC = "server/";
	final public String SUB_TOPIC = "clients/#";

	final static String PUBLISH_PC_REBOOT = "Reboot"; // 设备重启
	final static String PUBLISH_PC_GETLOG = "Getlog"; // 拉取设备log
	final static String PUBLISH_PC_SET_INFO = "SetInfo";  // 服务端信息
	final static String PUBLISH_PC_UPDATE_LIST= "UpdateList";  // 更新商品列表
	final static String PUBLISH_DELIVER = "Deliver"; // 出货
	final static String PUBLISH_PC_SCREEN= "Screen";  //屏幕截屏

	boolean init = false;
	
//	private static class SingletonHolder {
//		private static MsgBus INSTANCE = new MsgBus();
//	}
	
	static MsgBus instance;
	public Box box;
	public Oven oven;
	private BusListener busListener;
	
	public BusListener getBusListener() {
		return busListener;
	}
	
	public MsgBus setBusListener(BusListener listener) {
		busListener = listener;
		return this;
	}
	
	public static MsgBus getInstance() {
		if(instance == null) {
			instance = new MsgBus();
			logger.info("实例化Mqtt");
		}
		return instance; //SingletonHolder.INSTANCE;
	}

	private MsgBus() {
		connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(false);
		connOpts.setUserName(username);
		connOpts.setPassword(password.toCharArray());
		connOpts.setAutomaticReconnect(true);
		connOpts.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);	
		box = new Box();
		oven = new Oven();
	}

	public static void init(BusListener listener) {
		getInstance().connect().subcrite().setBusListener(listener);
		logger.info("初始化Mqtt");
	}
	
	public static void destory() {
		if(getInstance().client !=null) {
			try {
				getInstance().client.disconnect();
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public MsgBus connect() {
		try {
			if(client !=null) {
				return this;
			}
			client = new MqttClient(broker, clientId,new MemoryPersistence());
			client.setCallback(new MsgHandler(this));
			client.connect(connOpts);	
			logger.info("连接Mqtt");
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return this;
	}
	
	public MsgBus subcrite() {
		try {
			String connectedTopic = "$SYS/brokers/emq@127.0.0.1/clients/+/connected";
			String disconnectedTopic = "$SYS/brokers/emq@127.0.0.1/clients/+/disconnected";
			client.subscribe(SUB_TOPIC, QOS2);
			client.subscribe(connectedTopic, QOS2);
			client.subscribe(disconnectedTopic, QOS2);
			logger.info("订阅Mqtt");
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return this;
	}
	
	public void reboot(String deviceId) throws MqttException {
		publish(deviceId,createMsg(PUBLISH_PC_REBOOT));
	}

	public void screen(String deviceId) throws MqttException {
		publish(deviceId,createMsg(PUBLISH_PC_SCREEN));
	}
	
	public void updateList(String deviceId) throws MqttException {
		publish(deviceId,createMsg(PUBLISH_PC_UPDATE_LIST));
	}
	
	public void getLog(String deviceId,String date) throws MqttException {
		JSONObject json = new JSONObject();
		json.put("data", date);
		publish(deviceId,createMsg(PUBLISH_PC_GETLOG,json));
	}
	
	public void setInfo(String deviceId) throws MqttException {
		publish(deviceId,createMsg(PUBLISH_PC_SET_INFO));
	}
	
	private void publish(String deviceId,String content) throws MqttException {
		publish(deviceId,content,QOS2);
	}

	private void publish(String deviceId, String content,int qos) throws MqttException {
		if (content == null) {
			return;
		}

		MqttMessage message = new MqttMessage(content.getBytes());
		message.setQos(qos);
		client.publish(PUB_TOPIC+deviceId, message);
		logger.info("Mqtt 发布 topic:"+PUB_TOPIC+deviceId+"\r\n"+content);
	}
	
	private static String createMsg(String cmd) {
		return createMsg(cmd,null); 
	}
	
	private static String createMsg(String cmd,JSONObject content) {
		return createMsg(cmd,content,null); 
	}

	private static String createMsg(String cmd,JSONObject content, String device) {
		String msg = "";
        try {
        	JSONObject json = new JSONObject();
            json.put("timestamp", System.currentTimeMillis());
            json.put("cmd",cmd);
            if(device != null) {
            	json.put("device", device);
            }
            if (content != null){
            	 json.put("content", content);
            }
            msg = json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
	}
	
	// 微波加热
	public class Oven{
		
		final static String device = "oven";
		final static String PUBLISH_SELF_CHECK = "self_check"; //自检
		final static String PUBLISH_CLEAR_ERROR = "clear_error"; //清除故障
		final static String PUBLISH_GET_ERROR = "get_error"; // 获取故障状态
		final static String PUBLISH_GET_EMPTY = "get_empty"; // 获取售空状态
		
		public void deliver(String msgId,String deviceId,int columnNo,int hotTime) throws MqttException{
			//String msgId = UUID.randomUUID().toString().replace("-", "");
			JSONObject json = new JSONObject();
			json.put("msgId",msgId);
			json.put("columnNo", columnNo);
			json.put("hotTime", hotTime);
			publish(deviceId,createMsg(PUBLISH_DELIVER,json,device));
		}
		
		public void selfCheck(String deviceId) throws MqttException {
			publish(deviceId,createMsg(PUBLISH_SELF_CHECK,null,device));
		}
		
		public void clearError(String deviceId,int code) throws MqttException {
			JSONObject json = new JSONObject();
			json.put("code", code);
			publish(deviceId,createMsg(PUBLISH_SELF_CHECK,json,device));
		}
		
		public void getError(String deviceId) throws MqttException {
			publish(deviceId,createMsg(PUBLISH_GET_ERROR,null,device));
		}
		
		public void getEmpty(String deviceId) throws MqttException {
			publish(deviceId,createMsg(PUBLISH_GET_EMPTY,null,device));
		}
	}
	
	// 格子柜
	public class Box{
		
		final static String device = "box";
		
		final static String PUBLISH_GET_TEMP = "get_temp"; //读取温度
		final static String PUBLISH_SET_LIGHT = "set_light"; //设置照明开/关
		final static String PUBLISH_SET_HOT = "set_hot"; //设置加热开/关
		final static String PUBLISH_SET_COOL = "set_cool"; //设置制冷开/关
		final static String PUBLISH_SET_OZONE = "set_ozone"; //设置臭氧开/关
		final static String PUBLISH_HOT_PARAMS = "hot_params"; //加热参数设置/读取
		final static String PUBLISH_AUTO_OZONE = "auto_ozone"; //自动臭氧
		final static String PUBLISH_OPENALL = "open_all"; // 打开全部格子
		
		
		/**
		 * 格子柜出货
		 * @param deviceId
		 * @param columnNo
		 * @throws MqttException
		 */
		public void deliver(String msgId, String deviceId,int columnNo) throws MqttException {
			//String msgId = UUID.randomUUID().toString().replace("-", "");
			JSONObject json = new JSONObject();
			json.put("addr", 1);//暂时固定编号1柜子
			json.put("msgId",msgId);
			json.put("columnNo", columnNo);
			publish(deviceId,createMsg(PUBLISH_DELIVER,json,device));
		}
			
		/**
		 * 打开全部格子（补货）
		 * @param deviceId
		 * @throws MqttException
		 */
		public void openboxAll(String deviceId) throws MqttException {
			JSONObject json = new JSONObject();
			json.put("addr", 1);
			publish(deviceId,createMsg(PUBLISH_OPENALL,json,device));
		}
		
		/**
		 * 设置加热参数
		 * @param deviceId
		 * @param heat
		 * @param hold
		 * @param time
		 * @throws MqttException
		 */
		public void setHotParams(String deviceId,int heat,int hold,int time) throws MqttException {
			JSONObject json = new JSONObject();
			json.put("addr", 1);
			json.put("heat", heat);
			json.put("hold", hold);
			json.put("time", time);
			publish(deviceId,createMsg(PUBLISH_HOT_PARAMS,json,device));
		}
		
		/**
		 * 读取参数
		 * @param deviceId
		 * @throws MqttException
		 */
		public void getHotParams(String deviceId) throws MqttException{
			JSONObject json = new JSONObject();
			json.put("addr", 1);
			publish(deviceId,createMsg(PUBLISH_HOT_PARAMS,json,device));
		}
		
		/**
		 * 设置臭氧开关
		 * @param deviceId
		 * @param enable
		 * @throws MqttException
		 */
		public void setOzone(String deviceId,boolean enable) throws MqttException{
			JSONObject json = new JSONObject();
			json.put("addr", 1);
			json.put("sw", enable);
			publish(deviceId,createMsg(PUBLISH_SET_OZONE,json,device));
		}
		
		/**
		 * 设置制冷开关
		 * @param deviceId
		 * @param enable
		 * @throws MqttException
		 */
		public void setCool(String deviceId,boolean enable) throws MqttException{
			JSONObject json = new JSONObject();
			json.put("addr", 1);
			json.put("sw", enable);
			publish(deviceId,createMsg(PUBLISH_SET_COOL,json,device));
		}
		
		/**
		 * 设置加热开关
		 * @param deviceId
		 * @param enable
		 * @throws MqttException
		 */
		public void setHot(String deviceId,boolean enable) throws MqttException{
			JSONObject json = new JSONObject();
			json.put("addr", 1);
			json.put("sw", enable);
			publish(deviceId,createMsg(PUBLISH_SET_HOT,json,device));
		}
		
		/**
		 * 设置照明开关
		 * @param deviceId
		 * @param enable
		 * @throws MqttException
		 */
		public void setLight(String deviceId,boolean enable) throws MqttException{
			JSONObject json = new JSONObject();
			json.put("addr", 1);
			json.put("sw", enable);
			publish(deviceId,createMsg(PUBLISH_SET_LIGHT,json,device));
		}
		
		/**
		 * 设置自动臭氧
		 * @param deviceId
		 * @throws MqttException
		 */
		public void getAutoQzone(String deviceId) throws MqttException{
			JSONObject json = new JSONObject();
			json.put("addr", 1);
			publish(deviceId,createMsg(PUBLISH_AUTO_OZONE,json,device));
		}
		
		/**
		 * 读取自动臭氧参数
		 * @param deviceId
		 * @param st
		 * @param dt
		 * @throws MqttException
		 */
		public void setAutoQzone(String deviceId,String st,String dt) throws MqttException{
			JSONObject json = new JSONObject();
			json.put("addr", 1);
			json.put("st", st);
			json.put("dt", dt);
			publish(deviceId,createMsg(PUBLISH_AUTO_OZONE,json,device));
		}
	}
}
