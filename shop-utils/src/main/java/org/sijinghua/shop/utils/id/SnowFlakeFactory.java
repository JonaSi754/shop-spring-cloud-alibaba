package org.sijinghua.shop.utils.id;

import java.util.concurrent.ConcurrentHashMap;

public class SnowFlakeFactory {
    /**
     * 默认数据中心id
     */
    private static final long DEFAULT_DATACENTER_ID = 1;

    /**
     * 默认机器id
     */
    private static final long DEFAULT_MACHINE_ID = 1;

    /**
     * 默认的雪花算法句柄
     */
    private static final String DEFAULT_SNOW_FLAKE = "snow_flake";

    /**
     * 缓存SnowFlake对象
     */
    private static ConcurrentHashMap<String, SnowFlake> snowFlakeCache = new ConcurrentHashMap<>(2);

    public static SnowFlake getSnowFlake(long datacenterId, long machineId) {
        return new SnowFlake(datacenterId, machineId);
    }

    public static SnowFlake getSnowFlake() {
        return new SnowFlake(DEFAULT_DATACENTER_ID, DEFAULT_MACHINE_ID);
    }

    public static SnowFlake getSnowFlakeFromCache() {
        SnowFlake snowFlake = snowFlakeCache.get(DEFAULT_SNOW_FLAKE);
        if (snowFlake == null) {
            snowFlake = new SnowFlake(DEFAULT_DATACENTER_ID, DEFAULT_MACHINE_ID);
            snowFlakeCache.put(DEFAULT_SNOW_FLAKE, snowFlake);
        }
        return snowFlake;
    }

    public static SnowFlake getSnowFlakeByDatacenterIdAndMachineIdFromSnowFlakeCache(Long datacenterId, Long machineId) {
        if (datacenterId > SnowFlake.getMaxDatacenterNum() || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId cannot be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > SnowFlake.getMaxMachineNum() || machineId < 0) {
            throw new IllegalArgumentException("machineId cannot be greater than MAX_MACHINE_NUM or less than 0");
        }
        String key = DEFAULT_SNOW_FLAKE.concat("_").concat(String.valueOf(datacenterId)).concat("_")
                .concat(String.valueOf(machineId));
        SnowFlake snowFlake = snowFlakeCache.get(key);
        if (snowFlake == null) {
            snowFlake = new SnowFlake(datacenterId, machineId);
            snowFlakeCache.put(key, snowFlake);
        }
        return snowFlake;
    }
}
