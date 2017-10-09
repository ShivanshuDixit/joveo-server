package entity.job;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by shivanshu.dixit on 24/09/17.
 */

class JobStore {

    static Map<String, Job> jobStoreMap;

    static {
        jobStoreMap = new ConcurrentHashMap<>();
    }
}


