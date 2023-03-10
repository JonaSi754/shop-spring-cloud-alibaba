package org.sijinghua.shop.utils.test;

import org.junit.Test;
import org.sijinghua.shop.utils.id.SnowFlakeFactory;
import org.sijinghua.shop.utils.id.SnowFlakeLoader;

public class SnowFlakeFactoryTest {
    @Test
    public void testCreateDefaultId() {
        for (int i = 0; i < 100; ++i) {
            System.out.println(SnowFlakeFactory.getSnowFlakeFromCache().nextId());
        }
    }

    @Test
    public void testCreateId() {
        for (int i = 0; i < 100; ++i) {
            System.out.println(SnowFlakeFactory.getSnowFlakeByDatacenterIdAndMachineIdFromSnowFlakeCache(SnowFlakeLoader.getDatacenterId(), SnowFlakeLoader.getMachineId()).nextId());
        }
    }
}
