package com.joveo.server.entity.job;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shivanshu.dixit on 24/09/17.
 */

class JobStore {

    static Map<String, Job> jobStoreMap;

    /**
     * Stores all the job entries with name and id
     */
    static {
        jobStoreMap = new ConcurrentHashMap<>();
    }
}


