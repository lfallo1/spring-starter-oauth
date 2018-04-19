package com.sinclair.vpreports.spreadsheetrefresh.util;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DataUtil {

    /**
     * given a value in a map, return its key (this assumes no duplicate values as it will return the first key)
     *
     * @param map
     * @param value
     * @param <S>
     * @param <T>
     * @return
     */
    public <S, T> S getKey(Map<S, T> map, T value) {
        for (S key : map.keySet()) {
            if (map.get(key).equals(value)) {
                return key;
            }
        }
        return null;
    }

    /**
     * return if
     * @param map
     * @param key
     * @param <S>
     * @param <T>
     * @return
     */
    public <S,T> Boolean hasValue(Map<S,T> map, S key){
        return null != map && null != map.get(key);
    }

}
