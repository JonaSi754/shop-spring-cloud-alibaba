package org.sijinghua.shop.utils.id;

public class SnowFlake {
    /**
     * 起始的时间戳:2022-04-12 11:56:45，使用时此值不可修改
     */
    private final static long START_STAMP = 1649735805910L;

    /**
     * 每一部分占用的位数
     */
    private final static long SEQUENCE_BIT = 12;
    private final static long MACHINE_BIT = 5;
    private final static long DATACENTER_BIT = 5;

    /**
     * 每部分的最大值
     */
    private final static long MAX_DATACENTER_NUM = ~(-1L << DATACENTER_BIT);
    private final static long MAX_MACHINE_NUM    = ~(-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE       = ~(-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT    = SEQUENCE_BIT;
    private final static long DATACENTER_LEFT = MACHINE_LEFT + MACHINE_BIT;
    private final static long TIMESTMP_LEFT   = DATACENTER_LEFT + DATACENTER_BIT;

    private long datacenterId;      // 数据中心
    private long machineId;         // 机器标识
    private long sequence = 0L;     // 序列号
    private long lastStmp = -1L;   // 上一次时间戳

    public SnowFlake(long datacenterId, long machineId) {
        if (datacenterId > MAX_DATACENTER_NUM || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId cannot be greater than MAX_DATACENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId cannot be greater than MAX_MACHINE_NUM or less than 0");
        }
        this.datacenterId = datacenterId;
        this.machineId = machineId;
    }

    public synchronized long nextId() {
        long curStmp = getNewStmp();
        if (curStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards, refuse to generate id");
        }

        if (curStmp == lastStmp) {
            // 相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            // 同一毫秒内的序列数已达到最大
            if (sequence == 0L) {
                curStmp = getNextMill();
            }
        } else {
            // 不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastStmp = curStmp;

        return (curStmp - START_STAMP) << TIMESTMP_LEFT  // 时间戳部分
                | datacenterId << DATACENTER_LEFT        // 数据中心部分
                | machineId << MACHINE_LEFT              // 机器标识部分
                | sequence;                              // 序列号部分
    }

    private long getNextMill() {
        long mill = getNewStmp();
        while (mill <= lastStmp) {
            mill = getNewStmp();
        }
        return mill;
    }

    private long getNewStmp() {
        return System.currentTimeMillis();
    }

    public static long getMaxDatacenterNum() {
        return MAX_DATACENTER_NUM;
    }

    public static long getMaxMachineNum() {
        return MAX_MACHINE_NUM;
    }
}
