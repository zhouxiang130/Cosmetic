package com.yj.cosmetics.model;

import java.util.List;

/**
 * Created by Suo on 2018/3/20.
 */

public class HomeEntity {

	private String code;
	private String msg;
	private HomeData data;

	public final String HTTP_OK = "200";


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public HomeData getData() {
		return data;
	}

	public void setData(HomeData data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static class HomeData {
		private BannerExtensionBean bannerExtension;
		private String classifyHomeImg1;
		private String classifyHomeImg2;
		private String classifyHomeImg3;
		private String homeImg1;
		private String homeImg2;
		private String homeImg3;

		private String homeImg7;
		private String homeImg8;
		private String juli;
		private String shopName;
		private String shopId;
		private String city;
		private String location;

		private String classifyId1;
		private String classifyId2;

		private String classifyId3;
		private String punchTheClockImg;

		private List<HomeBanners> banners;
		private List<HomeBanner> banner;
		private List<HomeSeckill> timeLimitList;
		private List<HomeClassify> productClassifyList;
		private List<HomeHot> productHotList;
		private String newDate;

		public String getShopId() {
			return shopId;
		}

		public void setShopId(String shopId) {
			this.shopId = shopId;
		}

		public String getJuli() {
			return juli;
		}

		public void setJuli(String juli) {
			this.juli = juli;
		}

		public String getShopName() {
			return shopName;
		}

		public void setShopName(String shopName) {
			this.shopName = shopName;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public String getHomeImg7() {
			return homeImg7;
		}

		public void setHomeImg7(String homeImg7) {
			this.homeImg7 = homeImg7;
		}

		public String getHomeImg8() {
			return homeImg8;
		}

		public void setHomeImg8(String homeImg8) {
			this.homeImg8 = homeImg8;
		}

		public List<HomeBanners> getBanners() {
			return banners;
		}

		public void setBanners(List<HomeBanners> banners) {
			this.banners = banners;
		}

		public String getClassifyId1() {
			return classifyId1;
		}

		public void setClassifyId1(String classifyId1) {
			this.classifyId1 = classifyId1;
		}

		public String getClassifyId2() {
			return classifyId2;
		}

		public void setClassifyId2(String classifyId2) {
			this.classifyId2 = classifyId2;
		}

		public String getClassifyId3() {
			return classifyId3;
		}

		public void setClassifyId3(String classifyId3) {
			this.classifyId3 = classifyId3;
		}




		public BannerExtensionBean getBannerExtension() {
			return bannerExtension;
		}

		public void setBannerExtension(BannerExtensionBean bannerExtension) {
			this.bannerExtension = bannerExtension;
		}

		public String getClassifyHomeImg1() {
			return classifyHomeImg1;
		}

		public void setClassifyHomeImg1(String classifyHomeImg1) {
			this.classifyHomeImg1 = classifyHomeImg1;
		}

		public String getClassifyHomeImg2() {
			return classifyHomeImg2;
		}

		public void setClassifyHomeImg2(String classifyHomeImg2) {
			this.classifyHomeImg2 = classifyHomeImg2;
		}

		public String getClassifyHomeImg3() {
			return classifyHomeImg3;
		}

		public void setClassifyHomeImg3(String classifyHomeImg3) {
			this.classifyHomeImg3 = classifyHomeImg3;
		}

		public String getHomeImg1() {
			return homeImg1;
		}

		public void setHomeImg1(String homeImg1) {
			this.homeImg1 = homeImg1;
		}

		public String getHomeImg2() {
			return homeImg2;
		}

		public void setHomeImg2(String homeImg2) {
			this.homeImg2 = homeImg2;
		}

		public String getHomeImg3() {
			return homeImg3;
		}

		public void setHomeImg3(String homeImg3) {
			this.homeImg3 = homeImg3;
		}

		public String getPunchTheClockImg() {
			return punchTheClockImg;
		}

		public void setPunchTheClockImg(String punchTheClockImg) {
			this.punchTheClockImg = punchTheClockImg;
		}

		public List<HomeBanner> getBanner() {
			return banner;
		}

		public void setBanner(List<HomeBanner> banner) {
			this.banner = banner;
		}


		public static class BannerExtensionBean {
			/**
			 * bannerId : 6
			 * bannerImg : /upload/banner/2018/06/20/b8f9297d-792a-4eab-adf4-d1849b254c6e.png
			 * bannerSort : 6
			 * bannerType : 4
			 * bannerUrl :
			 * endTime :
			 * groupBy :
			 * insertTime : 2018-06-20 11:45:15
			 * orderBy :
			 * pageNum : 0
			 * pageSize : 10
			 * productId : 3
			 * startTime :
			 */

			private int bannerId;
			private String bannerImg;
			private int bannerSort;
			private int bannerType;
			private String bannerUrl;
			private String endTime;
			private String groupBy;
			private String insertTime;
			private String orderBy;
			private int pageNum;
			private int pageSize;
			private String productId;
			private String startTime;

			public int getBannerId() {
				return bannerId;
			}

			public void setBannerId(int bannerId) {
				this.bannerId = bannerId;
			}

			public String getBannerImg() {
				return bannerImg;
			}

			public void setBannerImg(String bannerImg) {
				this.bannerImg = bannerImg;
			}

			public int getBannerSort() {
				return bannerSort;
			}

			public void setBannerSort(int bannerSort) {
				this.bannerSort = bannerSort;
			}

			public int getBannerType() {
				return bannerType;
			}

			public void setBannerType(int bannerType) {
				this.bannerType = bannerType;
			}

			public String getBannerUrl() {
				return bannerUrl;
			}

			public void setBannerUrl(String bannerUrl) {
				this.bannerUrl = bannerUrl;
			}

			public String getEndTime() {
				return endTime;
			}

			public void setEndTime(String endTime) {
				this.endTime = endTime;
			}

			public String getGroupBy() {
				return groupBy;
			}

			public void setGroupBy(String groupBy) {
				this.groupBy = groupBy;
			}

			public String getInsertTime() {
				return insertTime;
			}

			public void setInsertTime(String insertTime) {
				this.insertTime = insertTime;
			}

			public String getOrderBy() {
				return orderBy;
			}

			public void setOrderBy(String orderBy) {
				this.orderBy = orderBy;
			}

			public int getPageNum() {
				return pageNum;
			}

			public void setPageNum(int pageNum) {
				this.pageNum = pageNum;
			}

			public int getPageSize() {
				return pageSize;
			}

			public void setPageSize(int pageSize) {
				this.pageSize = pageSize;
			}

			public String getProductId() {
				return productId;
			}

			public void setProductId(String productId) {
				this.productId = productId;
			}

			public String getStartTime() {
				return startTime;
			}

			public void setStartTime(String startTime) {
				this.startTime = startTime;
			}
		}

		public String getNewDate() {
			return newDate;
		}

		public void setNewDate(String newDate) {
			this.newDate = newDate;
		}


		public List<HomeClassify> getProductClassifyList() {
			return productClassifyList;
		}

		public void setProductClassifyList(List<HomeClassify> productClassifyList) {
			this.productClassifyList = productClassifyList;
		}

		public List<HomeHot> getProductHotList() {
			return productHotList;
		}

		public void setProductHotList(List<HomeHot> productHotList) {
			this.productHotList = productHotList;
		}

		public List<HomeSeckill> getTimeLimitList() {
			return timeLimitList;
		}

		public void setTimeLimitList(List<HomeSeckill> timeLimitList) {
			this.timeLimitList = timeLimitList;
		}

		public class HomeBanners{

//			 "bannerId": 29,
//					 "bannerImg": "/upload/banner/2018/07/04/fd927e30-7625-4adf-9db2-c99715c80f7a.png",
//					 "bannerSort": 2,
//					 "bannerType": 4,
//					 "bannerUrl": "",
//					 "endTime": "",
//					 "groupBy": "",
//					 "insertTime": "2018-07-02 18:15:09",
//					 "orderBy": "",
//					 "pageNum": 0,
//					 "pageSize": 10,
//					 "productId": 391,
//					 "startTime": ""

			private String bannerId;
			private String bannerImg;
			private String bannerSort;
			private String bannerType;
			private String bannerUrl;
			private String endTime;
			private String groupBy;
			private String insertTime;
			private String orderBy;
			private String pageNum;
			private String pageSize;
			private String productId;
			private String startTime;


			public String getBannerId() {
				return bannerId;
			}

			public void setBannerId(String bannerId) {
				this.bannerId = bannerId;
			}

			public String getBannerImg() {
				return bannerImg;
			}

			public void setBannerImg(String bannerImg) {
				this.bannerImg = bannerImg;
			}

			public String getBannerSort() {
				return bannerSort;
			}

			public void setBannerSort(String bannerSort) {
				this.bannerSort = bannerSort;
			}

			public String getBannerType() {
				return bannerType;
			}

			public void setBannerType(String bannerType) {
				this.bannerType = bannerType;
			}

			public String getBannerUrl() {
				return bannerUrl;
			}

			public void setBannerUrl(String bannerUrl) {
				this.bannerUrl = bannerUrl;
			}

			public String getEndTime() {
				return endTime;
			}

			public void setEndTime(String endTime) {
				this.endTime = endTime;
			}

			public String getGroupBy() {
				return groupBy;
			}

			public void setGroupBy(String groupBy) {
				this.groupBy = groupBy;
			}

			public String getInsertTime() {
				return insertTime;
			}

			public void setInsertTime(String insertTime) {
				this.insertTime = insertTime;
			}

			public String getOrderBy() {
				return orderBy;
			}

			public void setOrderBy(String orderBy) {
				this.orderBy = orderBy;
			}

			public String getPageNum() {
				return pageNum;
			}

			public void setPageNum(String pageNum) {
				this.pageNum = pageNum;
			}

			public String getPageSize() {
				return pageSize;
			}

			public void setPageSize(String pageSize) {
				this.pageSize = pageSize;
			}

			public String getProductId() {
				return productId;
			}

			public void setProductId(String productId) {
				this.productId = productId;
			}

			public String getStartTime() {
				return startTime;
			}

			public void setStartTime(String startTime) {
				this.startTime = startTime;
			}
		}


		public class HomeBanner {
			private String bannerId;
			private String bannerUrl;
			private String productId;
			private String bannerImg;


			public String getBannerId() {
				return bannerId;
			}

			public void setBannerId(String bannerId) {
				this.bannerId = bannerId;
			}

			public String getBannerUrl() {
				return bannerUrl;
			}

			public void setBannerUrl(String bannerUrl) {
				this.bannerUrl = bannerUrl;
			}

			public String getProductId() {
				return productId;
			}

			public void setProductId(String productId) {
				this.productId = productId;
			}

			public String getBannerImg() {
				return bannerImg;
			}

			public void setBannerImg(String bannerImg) {
				this.bannerImg = bannerImg;
			}
		}

		public class HomeSeckill {
			private String product_current;
			private String product_id;
			private String product_orginal;
			private String product_name;
			private String product_timeStart;
			private String product_abstract;
			private String product_timeEnd;
			private String product_stock;
			private String product_listImg;

			//创建线程进行计算添加的参数
			private long time;
			private String finalTime;
			private int hours;
			private int min;
			private int sec;


			public String getProduct_abstract() {
				return product_abstract;
			}

			public void setProduct_abstract(String product_abstract) {
				this.product_abstract = product_abstract;
			}

			public String getProduct_current() {
				return product_current;
			}

			public void setProduct_current(String product_current) {
				this.product_current = product_current;
			}

			public String getProduct_id() {
				return product_id;
			}

			public void setProduct_id(String product_id) {
				this.product_id = product_id;
			}

			public String getProduct_listImg() {
				return product_listImg;
			}

			public void setProduct_listImg(String product_listImg) {
				this.product_listImg = product_listImg;
			}

			public String getProduct_name() {
				return product_name;
			}

			public void setProduct_name(String product_name) {
				this.product_name = product_name;
			}

			public String getProduct_orginal() {
				return product_orginal;
			}

			public void setProduct_orginal(String product_orginal) {
				this.product_orginal = product_orginal;
			}

			public String getProduct_stock() {
				return product_stock;
			}

			public void setProduct_stock(String product_stock) {
				this.product_stock = product_stock;
			}

			public String getProduct_timeEnd() {
				return product_timeEnd;
			}

			public void setProduct_timeEnd(String product_timeEnd) {
				this.product_timeEnd = product_timeEnd;
			}

			public String getProduct_timeStart() {
				return product_timeStart;
			}

			public void setProduct_timeStart(String product_timeStart) {
				this.product_timeStart = product_timeStart;
			}


			public String getFinalTime() {
				return finalTime;
			}

			public void setFinalTime(String finalTime) {
				this.finalTime = finalTime;
			}

			public int getHours() {
				return hours;
			}

			public void setHours(int hours) {
				this.hours = hours;
			}

			public int getMin() {
				return min;
			}

			public void setMin(int min) {
				this.min = min;
			}

			public int getSec() {
				return sec;
			}

			public void setSec(int sec) {
				this.sec = sec;
			}

			public long getTime() {
				return time;
			}

			public void setTime(long time) {
				this.time = time;
			}
		}

		public static class HomeClassify {
			private String classify_id;
			private String classify_name;
			private String classify_img;

			public String getClassify_id() {
				return classify_id;
			}

			public void setClassify_id(String classify_id) {
				this.classify_id = classify_id;
			}

			public String getClassify_img() {
				return classify_img;
			}

			public void setClassify_img(String classify_img) {
				this.classify_img = classify_img;
			}

			public String getClassify_name() {
				return classify_name;
			}

			public void setClassify_name(String classify_name) {
				this.classify_name = classify_name;
			}
		}

		public class HomeHot {
			private String product_current;
			private String product_id;
			private String product_orginal;
			private String product_name;
			private String product_abstract;
			private String product_listImg;

			public String getProduct_abstract() {
				return product_abstract;
			}

			public void setProduct_abstract(String product_abstract) {
				this.product_abstract = product_abstract;
			}

			public String getProduct_current() {
				return product_current;
			}

			public void setProduct_current(String product_current) {
				this.product_current = product_current;
			}

			public String getProduct_id() {
				return product_id;
			}

			public void setProduct_id(String product_id) {
				this.product_id = product_id;
			}

			public String getProduct_listImg() {
				return product_listImg;
			}

			public void setProduct_listImg(String product_listImg) {
				this.product_listImg = product_listImg;
			}

			public String getProduct_name() {
				return product_name;
			}

			public void setProduct_name(String product_name) {
				this.product_name = product_name;
			}

			public String getProduct_orginal() {
				return product_orginal;
			}

			public void setProduct_orginal(String product_orginal) {
				this.product_orginal = product_orginal;
			}
		}
	}
}
