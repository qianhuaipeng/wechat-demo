package com.eastrobot.robotdev.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eastrobot.robotdev.data.message.domain.Faqvote;
import com.eastrobot.robotdev.data.message.domain.ImageTextMessage;
import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: alan.peng
 * description:
 * date: create in 16:11 2018/7/18
 * modified By：
 */
public class MiniProgramUtils {

    public static JSONObject convert(JSONObject jsonObject) {
        //String content = jsonObject.getString("content");
        imgtxtMsgConvert(jsonObject);
        faqvoteConvert(jsonObject);
        //jsonObject.put("content",tagFilter(content));
        return jsonObject;
    }


    public static JSONObject faqvoteConvert(JSONObject jsonObject){
        String content = jsonObject.getString("content");
        if (StringUtils.isBlank(content)) {
            return jsonObject;
        }
        //content.replaceAll("<[^>]*>","");
        String regex = "\\[link submit=(.*?)](.*?)\\[/link]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        int type = jsonObject.getInteger("type");
        if (type == 1) {
            List<String> faqvote = new ArrayList<String>(2);
            while (m.find()) {
                Pattern p1 = Pattern.compile("\"(.*?)\"");
                Matcher m1 = p1.matcher(m.group(1));
                while(m1.find()){
                    //System.out.println(m1.group(1));
                    faqvote.add(m1.group(1));
                }
            }

            if (faqvote.size()==2) {
                Faqvote faq = new Faqvote();
                faq.setSolve(faqvote.get(0));
                faq.setUnsolve(faqvote.get(1));
                jsonObject.put("faqvote",faq);
            }
        }


        content = content.split("以上答案是否解决了您的问题")[0];
        content = content.replaceFirst("\r\n","");
        jsonObject.put("content", content);
        return jsonObject;
    }

    /**
     * 图文消息转换
     * @param jsonObject
     * @return
     */
    public static JSONObject imgtxtMsgConvert(JSONObject jsonObject){
        try {
            JSONArray commandArray = jsonObject.getJSONArray("commands");
            if (commandArray != null) {
                for (int i = 0; i < commandArray.size(); i++) {
                    String name = commandArray.getJSONObject(i).getString("name");
                    if ("imgtxtmsg".equalsIgnoreCase(name)) {
                        jsonObject.put("type", 13);
                        JSONArray argsArray = commandArray.getJSONObject(i).getJSONArray("args");
                        int size = Integer.parseInt(argsArray.getString(1));
                        String imgtxtmsg = argsArray.getString(3);
                        int end = imgtxtmsg.lastIndexOf("]]>");
                        imgtxtmsg = imgtxtmsg.substring(9, end);
                        imgtxtmsg = imgtxtmsg.replaceAll("\n", "");
                        try {
                            Document document = XmlStrUtil.string2Doc(imgtxtmsg);
                            Element root = document.getRootElement();
                            size = root.getChildren().size();
                            List<Element> itemList = root.getChildren("item");
                            List<ImageTextMessage> imageTextMessages = new ArrayList<ImageTextMessage>(size);
                            for(Element element: itemList){
                                String title = element.getChildText("Title");
                                String description = element.getChildText("Description");
                                String image = element.getChildText("PicUrl");
                                String url = element.getChildText("Url");
                                ImageTextMessage imageTextMessage = new ImageTextMessage();
                                imageTextMessage.setTitle(title);
                                imageTextMessage.setDescription(description);
                                imageTextMessage.setImage(image);
                                imageTextMessage.setUrl(url);
                                imageTextMessages.add(imageTextMessage);
                            }
                            jsonObject.put("imgtxtmsg",imageTextMessages);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public static String tagFilter(String answer)
    {
        if (answer == null) {
            return null;
        }
        answer = answer.replaceAll("\\[link url=(.*?)](.*?)\\[/link]",
                "<text >$2</text>");
        answer = answer.replaceAll("\\[link submit=(.*?)](.*?)\\[/link]",
                "<rich-text onclick='send($1)' href=\"javascript:void(0);\">$2</rich-text>");
        answer = answer.replaceAll("\\[link](.*?)\\[/link]",
                "<rich-text bindtap='send(\"$1\")' href=\"javascript:void(0);\">$1</rich-text>");
        return answer;
    }

    public static void main(String[] args) {
        String content = "香格里拉酒店免费延住活动无需报名注册哦，快来参加吧。\\r\\n\\r\\n<b>以上答案是否解决了您的问题？</b>[link submit=\\\"faqvote:00151814741112506220005056b61023 1 z+O48cDvwK2+xrXqw+K30dHT16G77rav16Ky4cf+tcA= z+O48cDvwK2+xrXqw+K30dHT16G77rav16Ky4cf+tcA= z+O48cDvwK2+xrXqw+K30dHT16G77ravzt7Q6LGow/vXorLhxbajrL/swLSyzrzTsMmhow==\\\"]解决[/link] [link submit=\\\"faqvote:00151814741112506220005056b61023 2 z+O48cDvwK2+xrXqw+K30dHT16G77rav16Ky4cf+tcA= z+O48cDvwK2+xrXqw+K30dHT16G77rav16Ky4cf+tcA= z+O48cDvwK2+xrXqw+K30dHT16G77ravzt7Q6LGow/vXorLhxbajrL/swLSyzrzTsMmhow==\\\"]未解决[/link]\\r\\n\\r\\n";
        String regex = "\\[link submit=(.*?)](.*?)\\[/link]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);

        while (m.find()) {
            System.out.println(m.group(1));
            Pattern p1 = Pattern.compile("\"(.*?)\"");
            Matcher m1 = p1.matcher(m.group(1));
            while(m1.find()){
                System.out.println(m1.group(1));
            }
        }


    }
}
