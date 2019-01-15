package com.zhy.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.zhy.common.BaseException;
import com.zhy.common.CommonUtils;



/**
 * @ClassName   : HttpUtils   
 * @Description : http工具类  
 * @author : zhy
 * @date   : 2018年8月7日 下午6:41:15 
 */
public class HttpUtils {
	
	private static Logger logger = LoggerFactory.getLogger(HttpClients.class);
	
    private static CloseableHttpClient httpClient;

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(20);
        cm.setDefaultMaxPerRoute(50);
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
    }

    /**
     * @Title  : doGet   
     * @Description : TODO(这里用一句话描述这个方法的作用)   
     * @param url
     * @param params
     * @param contentType ,FORM
     * @author : zhy
     * @date   : 2018年8月7日 下午6:48:38 
     */
    public static String doGet(String url,Map<String, String> params) {
    	 CloseableHttpResponse response = null;
    	 BufferedReader in = null;
         try {
        	 if(null != params && !params.isEmpty()){
        		 if(url.endsWith("?")){ 
        			 url += CommonUtils.concatParams(params, false);
        		 }else {
        			 url += "?"+CommonUtils.concatParams(params, false);
				 }
         	 }
        	 logger.info("=-> HttpUtils.get(..) request url -> {}",url);
             HttpGet httpGet = new HttpGet(url);
             RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000)
             		   .setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
             httpGet.setConfig(requestConfig);
             httpGet.setHeader("Accept", "application/json");
			 httpGet.addHeader("Content-type", ContentTypeEnum.FORM.getContentType());
             response = httpClient.execute(httpGet);
             if(response.getStatusLine().getStatusCode() == 200){
            	 in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            	 StringBuffer buffer = new StringBuffer("");
            	 String line = "";
            	 String NL = System.getProperty("line.separator"); // \n
            	 while ((line = in.readLine()) != null) {
            		 buffer.append(line + NL);
            	 }
            	 in.close();
            	 String result = buffer.toString().trim();
            	 logger.info("=-> HttpUtils.get(..) response data -> {}",result);
            	 return result;
             } else {
            	 throw new BaseException(response.getStatusLine().getStatusCode(), "network error!");
             }
         } catch (BaseException be) {
        	 logger.error("=-> HttpUtils.get(..) remote method invocation exception!!,url->{} ; cause->{}",url,be);
        	 throw new BaseException(be.getErrorCode(), be.getErrorMessage());
         } catch (Exception e) {
             logger.error("=-> HttpUtils.get(..) remote method invocation exception!!,url->{} ; cause->{}",url,e);
             throw new BaseException(500, "http request exception!");
         } finally {
             try {
                 if (null != response)  response.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
    }
    
    public static String doPost(String url, Map<String, String> params) {
    	return doPost(url, params, ContentTypeEnum.FORM);
    }

    /**
     * @Title  : doPost   
     * @Description : TODO(这里用一句话描述这个方法的作用)   
     * @param url
     * @param params
     * @param contentType  @{link ecar.oil.ejiayou.util.ContentTypeEnum}, 默认FORM
     * @author : zhy
     * @date   : 2018年8月8日 上午10:23:12 
     */
    public static String doPost(String url, Map<String, String> params,ContentTypeEnum contentType) {
        CloseableHttpResponse response = null;
        BufferedReader in = null;
        try {
        	logger.info("=-> HttpUtils.post(..) request url -> {}, params -> {}",url,JSON.toJSONString(params));
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000)
            		  .setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpPost.setConfig(requestConfig);
            httpPost.setHeader("Accept", "application/json");
            HttpEntity entity = null;
            switch (contentType) {
			case JSON:
				httpPost.addHeader("Content-type", ContentTypeEnum.JSON.getContentType());
				entity = new StringEntity(JSON.toJSONString(params),Consts.UTF_8);
				break;
			default:
				httpPost.addHeader("Content-type", ContentTypeEnum.FORM.getContentType());
				List<NameValuePair> formPairs = new ArrayList<NameValuePair>();
				for (Entry<String, String> param : params.entrySet()) {
					if(!StringUtils.isEmpty(param.getKey()) && !StringUtils.isEmpty(param.getValue())){
						formPairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
					}
				}
				entity = new UrlEncodedFormEntity(formPairs,Consts.UTF_8);
				break;
			}
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode() == 200){
	            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	            StringBuffer buffer = new StringBuffer("");
	            String line = "";
	            String NL = System.getProperty("line.separator"); // \n
	            while ((line = in.readLine()) != null) {
	                buffer.append(line + NL);
	            }
	            in.close();
	            String result = buffer.toString().trim();
	            logger.info("=-> HttpUtils.post(..) response data -> {}",result);
	            return result;
            } else {
           	 	throw new BaseException(response.getStatusLine().getStatusCode(), "network error!");
            }
        } catch (BaseException be) {
        	logger.error("=-> HttpUtils.post(..) remote method invocation exception!!,url->{} ; cause->{}",url,be);
        	throw new BaseException(be.getErrorCode(), be.getErrorMessage());
        } catch (IOException e) {
        	logger.error("=-> HttpUtils.post(..) remote method invocation exception!!,url->{} ; cause->{}",url,e);
        	throw new BaseException(500, "http request exception!");
        } finally {
            try {
                if (null != response) response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}