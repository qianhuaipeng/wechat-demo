package com.eastrobot.robotdev.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.apache.commons.io.IOUtils;

import com.eastrobot.robotdev.utils.HttpUtils;

public class TestServlet extends HttpServlet {
	

	private static final long serialVersionUID = 7777085988578468476L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String question = request.getParameter("question");
		System.out.println("question===="+question);
		String userId = request.getParameter("userId");
		System.out.println("userId===="+userId);
		String app_key = request.getParameter("app_key");
		System.out.println("app_key==="+app_key);
		String app_secret = request.getParameter("app_secret");
		System.out.println("app_secret===="+app_secret);
		String sign = "error" ; 
		String app_nonce = "";
		String url = "http://vcloud.xiaoi.com/synth.do";
		System.out.println("url===="+url);
		String Content = "text/plain";
		String XAUE = "speex-wb;7";
		String XTXE = "gbk";
		String XAUF = "audio/L16;rate=8000";
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("question", "你好");
		params.put("app_key", "IiXxvQi2pAHu");
		params.put("app_secret", "4GxhDY0Igr5UGmf3rVtF");
		HashMap<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", Content);
		header.put("X-AUE", XAUE);
		header.put("X-TXE", XTXE);
		header.put("X-AUF", XAUF);
		
		//url += "?Content-Type="+Content+"&X-AUE="+XAUE+"&X-TXE="+XTXE+"&X-AUF="+XAUF+"&question="+question+"&app_key="+app_key+"&app_secret="+app_secret;
		//System.out.println("请求url==="+url);
		try {
			AudioFormat af = null;  
			af = getAudioFormat();  
			 //定义字节数组输入输出流  
		    ByteArrayInputStream bais = null;  
		    ByteArrayOutputStream baos = null;  
		    //定义音频输入流  
		    AudioInputStream ais = null;  
			byte[] audioData = HttpUtils.doPost(url, params, header, XTXE);
			bais = new ByteArrayInputStream(audioData); 
			ais = new AudioInputStream(bais,af, audioData.length / af.getFrameSize());  
			//定义最终保存的文件名  
	        File file = null;  
	        File filePath = new File("F:/语音文件"); 
	        if(!filePath.exists())  
            {//如果文件不存在，则创建该目录  
                filePath.mkdir();  
            }  
	        long time = System.currentTimeMillis();  
            file = new File(filePath+"/"+time+".wav");
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file);  
            
            String tarFileName = time+".mp3";  
            Runtime run = null;  
              
            try {  
                run = Runtime.getRuntime();  
                long start=System.currentTimeMillis();  
                //调用解码器来将wav文件转换为mp3文件  
                Process p=run.exec(filePath+"/"+"lame -b 16 "+filePath+"/"+file.getName()+" "+filePath+"/"+tarFileName); //16为码率，可自行修改  
                //释放进程  
                p.getOutputStream().close();  
                p.getInputStream().close();  
                p.getErrorStream().close();  
                p.waitFor();  
                long end=System.currentTimeMillis();  
                System.out.println("convert need costs:"+(end-start)+"ms");  
                //删除无用的wav文件  
                if(file.exists())  
                {  
                    file.delete();  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            }finally{  
                //最后都要执行的语句  
                //run调用lame解码器最后释放内存  
                run.freeMemory();  
            }
			byte[] bts = new byte[1024];
			int length = 0;
			try {
				ByteArrayInputStream stream = new ByteArrayInputStream(audioData);
			  //  InputStream stream = new FileInputStream(new String(aa));
				String path = this.getClass().getResource("/").getPath()+"dest.amr";
			    OutputStream os = new FileOutputStream(new File(path));
			    while ((length = stream.read(bts)) > 0) {
			        os.write(bts, 0, length);
			    }
			    os.flush();
			    os.close();
			    stream.close();
			} catch (Exception e) {
			    e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//设置AudioFormat的参数  
    public AudioFormat getAudioFormat()   
    {  
        //下面注释部分是另外一种音频格式，两者都可以  
        AudioFormat.Encoding encoding = AudioFormat.Encoding.  
        PCM_SIGNED ;  
        float rate = 8000f;  
        int sampleSize = 16;  
        String signedString = "signed";  
        boolean bigEndian = true;  
        int channels = 1;  
        return new AudioFormat(encoding, rate, sampleSize, channels,  
                (sampleSize / 8) * channels, rate, bigEndian);  
        
//      //采样率是每秒播放和录制的样本数  
//      float sampleRate = 16000.0F;  
//      // 采样率8000,11025,16000,22050,44100  
//      //sampleSizeInBits表示每个具有此格式的声音样本中的位数  
//      int sampleSizeInBits = 16;  
//      // 8,16  
//      int channels = 1;  
//      // 单声道为1，立体声为2  
//      boolean signed = true;  
//      // true,false  
//      boolean bigEndian = true;  
//      // true,false  
//      return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,bigEndian);  
    }  

    
	public static void main(String[] args) throws Exception {
		String XAUE = "speex-wb;7";
		String XTXE = "GBK";
		String XAUF = "audio/L16;rate=8000";
		URLConnection conn = new URL("http://vcloud.xiaoi.com/synth.do")
				.openConnection();
		//conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("X-TXE", "UTF-8");
		conn.setRequestProperty("Content-Type", "text/plain");
		conn.setRequestProperty("X-AUE", XAUE);
		conn.setRequestProperty("X-TXE", XTXE);
		conn.setRequestProperty("X-AUF", XAUF);
		conn.addRequestProperty("question", "你好");
		conn.addRequestProperty("app_key", "IiXxvQi2pAHu");
		conn.addRequestProperty("app_secret", "4GxhDY0Igr5UGmf3rVtF");
		ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
		InputStream in = null;
		in = conn.getInputStream();
		IOUtils.copy(in, bytesOut);
		in.close();
		in = conn.getInputStream();
		IOUtils.copy(in, bytesOut);
		String ret = new String(bytesOut.toByteArray(), "UTF-8");
	}
}
