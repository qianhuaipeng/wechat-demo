package com.eastrobot.robotdev.utils;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * 用jdom来解析xml
 * @author alan
 *
 * 2016-8-15 下午1:59:02
 */
public class XmlStrUtil {
	
	/**   
     * 字符串转换为DOCUMENT   
     *    
     * @param xmlStr 字符串   
     * @return doc JDOM的Document   
     * @throws Exception   
     */    
    public static Document string2Doc(String xmlStr) throws Exception {     
        java.io.Reader in = new StringReader(xmlStr);     
        Document doc = (new SAXBuilder()).build(in);       
        return doc;     
    } 
    
    /**
	 * Document转换为字符串
	 * 
	 * @param xmlFilePath
	 *            XML文件路径
	 * @return xmlStr 字符串
	 * @throws Exception
	 */
	public static String doc2String(Document doc) throws Exception {
		Format format = Format.getPrettyFormat();
		format.setEncoding("UTF-8");// 设置xml文件的字符为UTF-8，解决中文问题
		XMLOutputter xmlout = new XMLOutputter(format);
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		xmlout.output(doc, bo);
		return bo.toString();
	}
}
