/**
 * Copyright 2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
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

import android.content.Context;

import com.amazonaws.mobileconnectors.cognito.CognitoSyncManager;
import com.amazonaws.mobileconnectors.cognito.Dataset;
import com.amazonaws.regions.Regions;

public class CognitoSyncClientManager {

    private static CognitoSyncManager client;

    public static void init(Context context) {
        if (client == null) {
            client = new CognitoSyncManager(context.getApplicationContext(), Regions.US_EAST_1,
                    CognitoClientManager.getCredentials());
        }
    }

    public static void wipeData() {
        checkSyncAvailability();
        client.wipeData();
    }

    public static Dataset openOrCreateDataset(String datasetName) {
        checkSyncAvailability();
        return client.openOrCreateDataset(datasetName);
    }
    private static void checkSyncAvailability() {
        if (client == null) {
            throw new IllegalStateException("Sync client not initialized yet");
        }
    }
}
