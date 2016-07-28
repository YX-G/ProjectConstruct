/*
 * Copyright 2010-2012 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 * 
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.gyx.projectconstruct.aws;

import java.util.Random;

public class Constants {

    public static final String IDENTITY_POOL_ID = "us-east-1:60f63ec2-a72c-4d23-b748-21481a4694ae";
    // Note that spaces are not allowed in the table name
    public static final String TEST_TABLE_NAME = "image";
    public static final String IMAGE_TABLE_NAME = "hosma_Image";
    public static final String STORE_TABLE_NAME = "hosma_Store";
    public static final String FAVORITE_TABLE_NAME = "hosma_VideoUserFavorite";
    public static final String EVENT_TABLE_NAME = "hosma_Event";
    public static final String VIDEOLIST_TABLE_NAME = "hosma_VideoList";
    public static final String USERDATA_TABLE_NAME = "hosma_UserData";
    public static final String AD_TABLE_NAME = "hosma_Ad";
    public static final String VideoUserDetail_TABLE_NAME = "hosma_VideoUserDetail";
    public static final String VideoUser_TABLE_NAME = "hosma_VideoUser";

    public static final String S3IMAGE_URL = "http://s3.amazonaws.com/";
    public static final String S3IMAGE_FILENAME = "ioshosmabucket/";
    public static final String S3IMAGE_VIDEONAME = "iosvideobucket/";


    public static final String BD_Report = "BD_Report";
    public static final String Web  = "Web";
    public static final String Gravure  = "Gravure";
    public static final String Ad  = "Ad";
    public static final String Call_Movie  = "Call_Movie";
    public static final String AllEvent   = "AllEvent";
    public static final String EVENT_NOTICE_NAME="hosma_Notice";
    public static final String General= "General";


    public static final String VIDEODETAIL_SEX="UserGender";
    public static final String VIDEODETAIL_POST="UserPostCode";
    public static final String VIDEODETAIL_NAME="UserName";
    public static final String VIDEODETAIL_HEADIMG="UserImageName";
    public static final String VIDEODETAIL_ADDRESS="UserAddress";
    public static final String VIDEODETAIL_BIRTH="UserBirthDay";
    /*
     * Note, you must first create a bucket using the S3 console before running
     * the sample (https://console.aws.amazon.com/s3/). After creating a bucket,
     * put it's name in the field below.
     */
    public static final String BUCKET_NAME = "iosvideobucket";

}