package com.gyx.projectconstruct.aws;

import android.content.Context;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;


import java.util.ArrayList;


/**
 * Created by Administrator on 2016/5/4.
 */
public class DynamoDBManager {
    private static final String TAG = "DynamoDBManager";
    public Context context;
    private static AmazonDynamoDBClient ddb;

    public static void init() {
        if (ddb == null) {
            ClientConfiguration clientConfiguration=new ClientConfiguration();
            clientConfiguration.setConnectionTimeout(3000 * 1000); // 60 sec
            clientConfiguration.setSocketTimeout(3000 * 1000); // 60 sec
                    ddb = new AmazonDynamoDBClient(
                    CognitoClientManager.getCredentials(),clientConfiguration);
        }
    }
    public DynamoDBManager(Context context) {
        this.context=context;
    }



    private static void checkDynamoDBClientAvailability() {
        if (ddb == null) {
            throw new IllegalStateException(
                    "DynamoDB client not initialized yet");
        }
    }

  /*  public static ArrayList<StoreBean> getStoreAllDataList() {
        checkDynamoDBClientAvailability();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();

        try {
            PaginatedScanList<StoreBean> result = mapper.scan(StoreBean.class, scanExpression);

            ArrayList<StoreBean> resultList = new ArrayList();
            for (StoreBean up : result) {
                resultList.add(up);
            }
            return resultList;
        } catch (AmazonServiceException ex) {
        }
        return  null;
    }

    public static ArrayList<NoticeBean> getNoticeList() {
        checkDynamoDBClientAvailability();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        try {
            PaginatedScanList<NoticeBean> result = mapper.scan(NoticeBean.class, scanExpression);
            ArrayList<NoticeBean> resultList = new ArrayList();
            for (NoticeBean up : result) {
                resultList.add(up);
            }
            return resultList;
        } catch (AmazonServiceException ex) {
        }
        return  null;
    }


    public static ArrayList<VideoUserDetailBean> getVideoListDetails() {
        checkDynamoDBClientAvailability();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        try {
            PaginatedScanList<VideoUserDetailBean> result = mapper.scan(VideoUserDetailBean.class, scanExpression);
            ArrayList<VideoUserDetailBean> resultList = new ArrayList();
            for (VideoUserDetailBean up : result) {
                resultList.add(up);
            }
            return resultList;
        } catch (AmazonServiceException ex) {
        }
        return  null;
    }




    public static ArrayList<ADBean> getAdList() {
        checkDynamoDBClientAvailability();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        try {
            PaginatedScanList<ADBean> result = mapper.scan(ADBean.class, scanExpression);
            ArrayList<ADBean> resultList = new ArrayList();
            for (ADBean up : result) {
                resultList.add(up);
            }
            return resultList;
        } catch (AmazonServiceException ex) {
        }
        return  null;
    }
    //关于视频扫描
    public static ArrayList<VideoListBean> getVideoList() {
        checkDynamoDBClientAvailability();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        scanExpression.withLimit(10);
        try {
            PaginatedScanList<VideoListBean> result = mapper.scan(VideoListBean.class, scanExpression);
            ArrayList<VideoListBean> resultList = new ArrayList();
            for (VideoListBean up : result) {
                resultList.add(up);
            }
            return resultList;
        } catch (AmazonServiceException ex) {
        }
        return  null;
    }

    //关于视频查找
    public static ArrayList<VideoListBean> queryVideoListInStore(String HashKey){
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);
        //查找
        VideoListBean bean=new VideoListBean();
        bean.setVideoListHashKey(HashKey);
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withHashKeyValues(bean)
                .withConsistentRead(false);
        queryExpression.setLimit(10);
        try{
            PaginatedQueryList<VideoListBean> result = mapper.query(VideoListBean.class, queryExpression);
            ArrayList<VideoListBean> resultList = new ArrayList();

            for (VideoListBean up : result) {
                resultList.add(up);
            }
            return  resultList;
        }catch (AmazonServiceException ex) {
            throw new IllegalStateException("Error querying", ex);
        }

    }
    //查询收藏
    public static ArrayList<FavoriteBean> queryFavoriteData(String HashKey){
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);
        //查找
        FavoriteBean bean=new FavoriteBean();
        bean.setUserFavoriteHashKey(HashKey);
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withHashKeyValues(bean)
                .withConsistentRead(false);

        try{
            PaginatedQueryList<FavoriteBean> result = mapper.query(FavoriteBean.class, queryExpression);
            ArrayList<FavoriteBean> resultList = new ArrayList();

            for (FavoriteBean up : result) {
                resultList.add(up);
            }
            return  resultList;
        }catch (AmazonServiceException ex) {
            throw new IllegalStateException("Error querying", ex);
        }
    }
    //查询商店
    public static ArrayList<StoreBean> queryStoreData(String HashKey){
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);
        //查找
        StoreBean bean=new StoreBean();
        bean.setStoreHashKey(HashKey);
        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                .withHashKeyValues(bean)
                .withConsistentRead(false);

        try{
            PaginatedQueryList<StoreBean> result = mapper.query(StoreBean.class, queryExpression);
            ArrayList<StoreBean> resultList = new ArrayList();

            for (StoreBean up : result) {
                resultList.add(up);
            }
            return  resultList;
        }catch (AmazonServiceException ex) {
            throw new IllegalStateException("Error querying", ex);
        }


    }
    //关于视频扫描
    public static ArrayList<VideoUserBean> getVideoUserAlls() {
        checkDynamoDBClientAvailability();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        try {
            PaginatedScanList<VideoUserBean> result = mapper.scan(VideoUserBean.class, scanExpression);
            ArrayList<VideoUserBean> resultList = new ArrayList();
            for (VideoUserBean up : result) {
                resultList.add(up);
            }
            return resultList;
        } catch (AmazonServiceException ex) {

        }
        return  null;
    }

    public static void insertVideoUserData(VideoUserDetailBean bean) {
        checkDynamoDBClientAvailability();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);
        try {
            VideoUserDetailBean user=new VideoUserDetailBean();
            user.setUserDetailHashKey(bean.getUserDetailHashKey());
            user.setUserDetailRangeKey(bean.getUserDetailRangeKey());
            user.setUserDetailContent(bean.getUserDetailContent());
            mapper.save(user);
        } catch (AmazonServiceException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }


    public static void insertUserData(VideoUserBean bean) {
        checkDynamoDBClientAvailability();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);
        try {
            VideoUserBean user=new VideoUserBean();
            user.setUserDataHashKey(bean.getUserDataHashKey());
            user.setUserDataRangeKey(bean.getUserDataRangeKey());
            user.setUserDataUniqueKey(bean.getUserDataUniqueKey());
            mapper.save(user);
        } catch (AmazonServiceException ex) {

            ex.printStackTrace();
            throw ex;
        }
    }

    public static void insertFavoriteData(FavoriteBean bean) {
        checkDynamoDBClientAvailability();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);
        try {
            FavoriteBean favoriteBean=new FavoriteBean();
            favoriteBean.setUserFavoriteHashKey(bean.getUserFavoriteHashKey());
            favoriteBean.setUserFavoriteCategory(bean.getUserFavoriteCategory());
            favoriteBean.setUserFavoriteContentHashKey(bean.getUserFavoriteContentHashKey());
            favoriteBean.setUserFavoriteDateTime(bean.getUserFavoriteDateTime());
            favoriteBean.setUserFavoriteRangeKey(bean.getUserFavoriteRangeKey());
            mapper.save(favoriteBean);
        } catch (AmazonServiceException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    //插入videolist
    public static void insertVideoListData(VideoListBean bean) {
        checkDynamoDBClientAvailability();
        DynamoDBMapper mapper = new DynamoDBMapper(ddb);
        try {

            VideoListBean videoListBean=new VideoListBean();
            videoListBean.setVideoListCategory(bean.getVideoListCategory());
            videoListBean.setVideoListChannel(bean.getVideoListChannel());
            videoListBean.setVideoListHashKey(bean.getVideoListHashKey());
            videoListBean.setVideoListRangeKey(bean.getVideoListRangeKey());
            videoListBean.setVideoListDateTime(bean.getVideoListDateTime());
            videoListBean.setVideoListDescription(bean.getVideoListDescription());
            videoListBean.setVideoListImage(bean.getVideoListImage());
            videoListBean.setVideoListStoreRangekey(bean.getVideoListStoreRangekey());
            videoListBean.setVideoListReleaseFlg(bean.getVideoListReleaseFlg());
            videoListBean.setVideoListQueryKey(bean.getVideoListQueryKey());
            videoListBean.setVideoListVideoName(bean.getVideoListVideoName());
            videoListBean.setVideoListTitle(bean.getVideoListTitle());
            videoListBean.setVideoListTag(bean.getVideoListTag());
            videoListBean.setVideoSubtitle(bean.getVideoSubtitle());
            videoListBean.setVideoSubtitleColor(bean.getVideoSubtitleColor());
            videoListBean.setVideoSubtitleStartTime(bean.getVideoSubtitleStartTime());
            videoListBean.setVideoSubtitleEndTime(bean.getVideoSubtitleEndTime());
            videoListBean.setVideoYouTubeURL(bean.getVideoYouTubeURL());
            videoListBean.setVideoListLength(bean.getVideoListLength());

            mapper.save(videoListBean);
        } catch (AmazonServiceException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }


*/
}
