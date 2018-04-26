package org.wlyyy.common.service;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 采用一次从数据库中取出多个流水号，缓存在类中，同步递增取，不够再取数据库的方式实现的流水号生成器。
 * (core)
 *
 * @author yangrl14628 2016-08-25
 */
public class JumpedSequenceManagerImpl implements CachedSequenceManagementService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final Long count;
    private final Map<String, SerialNumber> numberMap;

    SqlSessionFactory fac;

    public JumpedSequenceManagerImpl(Long jumpStep) {
        this.count = jumpStep;

        this.numberMap = new HashMap<String, SerialNumber>();
    }

    @Override
    public synchronized Long getBySequenceType(String type) {
        SerialNumber number = this.numberMap.get(type);
        if (number == null) {
            Long r = querySerialByKey(type, this.count);
            if (r == null) {
                return r;
            } else {
                number = new SerialNumber();
                number.setNewValue(r, this.count);
                this.numberMap.put(type, number);
            }
        } else if (number.isEnded()) {
            number.setNewValue(querySerialByKey(type, this.count), this.count);
        }
        return number.getValuePlus();
    }

    static class SerialNumber {
        Long max;
        Long current;

        SerialNumber() {
            this.max = null;
            this.current = null;
        }

        Long getValuePlus() {
            return this.current++;
        }

        void setNewValue(Long value, Long count) {
            this.current = value;
            this.max = value + count;
        }

        boolean isEnded() {
            return this.max == null || this.current == null || this.current >= this.max;
        }
    }

    private Long querySerialByKey(String serialNumberKeyName, Long count) {
        Map<String, Object> querymap = new HashMap<String, Object>();
        querymap.put("name", serialNumberKeyName);
        querymap.put("count", count);
        // TODO query sql select nextval_bunch(#name:varchar#, #count:int#) as val from DUAL
        Long result = 1L;
        return result;
    }
}
