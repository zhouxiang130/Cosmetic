package com.yj.cosmetics.util;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by Administrator on 2018/8/1 0001.
 */

public class GetLatAndLngByBaidu {

	private static String loadJSON(String url) {
		StringBuilder json = new StringBuilder();
		try {
			URL oracle = new URL(url);
			URLConnection yc = oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return json.toString();
	}

//	/**
//	 * 百度根据地址取经纬度
//	 *
//	 * @param @param  address
//	 * @param @return
//	 * @param @throws Exception
//	 * @return Map<String,Double>
//	 * @throws @author ltj
//	 * @date 2017年9月20日下午9:50:00
//	 */
//	public static Map<String, Double> getLngAndLat(String address) throws Exception {
//		Map<String, Double> map = new HashMap<String, Double>();
//		String url = "http://api.map.baidu.com/geocoder/v2/?address=" + address
//				+ "&output=json&ak=你的AK";
//		String json = loadJSON(url);
//		System.out.println(json);
//		JSONObject obj = JSONObject.parseObject(json);
//		if (obj != null) {
//			if (obj.get("status").toString().equals("0")) {
//				double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
//				double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
//				map.put("lng", lng);
//				map.put("lat", lat);
//				System.out.println("经度：" + lng + "---纬度：" + lat);
//			} else {
//				System.out.println("未找到相匹配的经纬度！");
//				throw new Exception();
//			}
//
//		}
//		return map;
//	}

//	/**
//	 * 高德地图地址获取经纬度
//	 *
//	 * @param @param  address
//	 * @param @return
//	 * @param @throws Exception
//	 * @return Map<String,Double>
//	 * @throws @author ltj
//	 * @date 2017年9月20日下午8:46:52
//	 */
//	public static Map<String, Double> getLngAndLatByAmap(String address) throws Exception {
//		address = address.trim();
//		Map<String, Double> map = new HashMap<String, Double>();
//		String url = "http://restapi.amap.com/v3/geocode/geo?address=" + URLEncoder.encode(address, "utf-8")
//				+ "&output=json&key=你的key";
//		GetMethod method = new GetMethod(url);
//		method.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
//		HttpClient client = new HttpClient();
//		client.getHttpConnectionManager().getParams().setConnectionTimeout(10000); // 设置连接超时
//		int status = client.executeMethod(method);
//		if (status == 200) {
//			String json = method.getResponseBodyAsString();
//			System.out.println(json);
//			JSONObject obj = JSONObject.parseObject(json);
//			if (obj.get("status").toString().equals("1")) {
//				JSONArray array = obj.getJSONArray("geocodes");
//				String str = array.getString(0);
//				JSONObject locationjson = JSONObject.parseObject(str);
//				str = locationjson.getString("location");
//				String[] location = str.split(",");
//				double lng = Double.parseDouble(location[0]);
//				double lat = Double.parseDouble(location[0]);
//				map.put("lng", lng);
//				map.put("lat", lat);
//				System.out.println("经度：" + lng + "---纬度：" + lat);
//			} else {
//				System.out.println("未找到相匹配的经纬度！");
//				throw new Exception();
//			}
//		}
//		return map;
//	}


	private static final String KEY = "0*******16d8db3a0e622fccca24f9";
	private static final String OUTPUT = "JSON";
	private static final String GET_LNG_LAT_URL = "http://restapi.amap.com/v3/geocode/geo";

//	private static final Logger LOGGER = Logger.getLogger(AddressLngLatExchange.class);

//	public static Pair getLngLatFromOneAddr(String address) {
//		if (StringUtils.isBlank(address)) {
//			LOGGER.error("地址（" + address + "）为null或者空");
//			return null;
//		}
//		Map params = new HashMap();
//		params.put("address", address);
//		params.put("output", OUTPUT);
//		params.put("key", KEY);
//		String result = HttpclientUtil.post(params, GET_LNG_LAT_URL);
//		Pair pair = null;
//		JSONObject jsonObject = JSONObject.fromObject(result);
//		//拿到返回报文的status值，高德的该接口返回值有两个：0-请求失败，1-请求成功；
//		int status = Integer.valueOf(jsonObject.getString("status"));
//		if (status == 1) {
//			JSONArray jsonArray = jsonObject.getJSONArray("geocodes");
//			for (int i = 0; i < jsonArray.size(); i++) {
//				JSONObject json = jsonArray.getJSONObject(i);
//				String lngLat = json.getString("location");
//				String[] lngLatArr = lngLat.split(",");
//				//经度
//				BigDecimal longitude = new BigDecimal(lngLatArr[0]);
////    			System.out.println("经度" + longitude);
//				//纬度
//				BigDecimal latitude = new BigDecimal(lngLatArr[1]);
////    			System.out.println("纬度" + latitude);
//				pair = new MutablePair(longitude, latitude);
//			}
//		} else {
//			String errorMsg = jsonObject.getString("info");
//			LOGGER.error("地址（" + address + "）" + errorMsg);
//		}
//		return pair;
//	}
}

