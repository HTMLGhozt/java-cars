package com.thomasdillard.cars;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CarLog implements Serializable
{
	private final String msg;
	private final String formattedDate;

	public CarLog(String msg)
	{
		this.msg = msg;
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd hh:mm:ss a");
		formattedDate = dateFormat.format(date);
	}

	public ObjectNode toJSON()
	{
		ObjectNode logMsgJSON = new ObjectMapper().createObjectNode();
		logMsgJSON.put("msg", msg);
		logMsgJSON.put("date", formattedDate);
		return logMsgJSON;
	}
}
