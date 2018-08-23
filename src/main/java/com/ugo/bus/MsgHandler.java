package com.ugo.bus;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import ch.qos.logback.classic.Logger;

/**
 * Mqtt 消息接收监听
 * @author zsp
 *
 */
public class MsgHandler implements MqttCallback {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(MsgHandler.class);
	
	MsgBus msgBus;

	final static String TOPIC_LAST_WILL= "LastWill";
	final static String TOPIC_GET_INFO= "GetInfo";
	final static String TOPIC_REPLY_DELIVER= "ReplyDeliver";
	final static String TOPIC_DELIVER_LOG = "DeliverLog";
	final static String TOPIC_VMC_CMD_LOG = "CmdLog";
	final static String TOPIC_VMC_CMD_WRAN = "CmdErr";
	
	public MsgHandler(MsgBus bus) {
		msgBus = bus;
	}
	
	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		logger.error("Mqtt connectionLost");
		cause.printStackTrace();
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		try {
			logger.info("Mqtt 接收 topic:"+topic+"\r\n"+message);
			JSONObject jsonObject = JSON.parseObject(message.toString());
			if(topic.startsWith("$SYS/")) {
				String deviceId = jsonObject.getString("clientid");
				if(topic.endsWith("/connected")) {
					msgBus.getBusListener().online(deviceId);
					BusSocket.sendMessage(deviceId,"上线");
				}else if(topic.endsWith("/disconnected")) {
					msgBus.getBusListener().offline(deviceId);
					BusSocket.sendMessage(deviceId,"下线");
				}
			}else if(topic.startsWith("clients/")) {
				String deviceId = topic.split("/")[1];
				System.out.println("deviceId=="+deviceId);
				String cmd = jsonObject.getString("cmd");
				String msg = "";
				if(TOPIC_LAST_WILL.equals(cmd)) {
					
				}else if(TOPIC_GET_INFO.equals(cmd)) {
					msgBus.setInfo(deviceId);
					msg = cmd;
				}else if(TOPIC_REPLY_DELIVER.equals(cmd)) {
					JSONObject content = jsonObject.getJSONObject("content");
					String msgId = content.getString("msgId");
					int columnNo = content.getIntValue("columnNo");
					int status = content.getIntValue("status");
					String statusMsg = content.getString("statusMsg");
					msgBus.getBusListener().receivedReplyDeliverMsg(msgId,deviceId, columnNo, status, statusMsg);
					msg = "出货执行回复 msgId="+msgId+" 设备="+" 货道="+columnNo+" 状态="+status+" "+statusMsg;
				}else if(TOPIC_DELIVER_LOG.equals(cmd)) {
					JSONObject content = jsonObject.getJSONObject("content");
					String msgId = content.getString("msgId");
					int columnNo = content.getIntValue("columnNo");
					int status = content.getIntValue("status");
					String statusMsg = content.getString("statusMsg");
					msgBus.getBusListener().receivedDeliverLogMsg(msgId,deviceId, columnNo, status, statusMsg);
					msg = "出货结果上报 msgId="+msgId+" 设备="+" 货道="+columnNo+" 状态="+status+" "+statusMsg;
				}else if(TOPIC_VMC_CMD_LOG.equals(cmd)) {
					JSONObject content = jsonObject.getJSONObject("content");
					msg = content.getString("log");
				}
				BusSocket.sendMessage(deviceId,msg);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		logger.info("Mqtt deliveryComplete:"+token.toString());
	}
}
