package com.beust.jcommander;

import com.beust.jcommander.internal.Maps;
import java.util.Map;

/* JADX INFO: loaded from: classes8.dex */
public class FuzzyMap {

    interface IKey {
        String getName();
    }

    public static <V> V findInMap(Map<? extends IKey, V> map, IKey iKey, boolean z, boolean z2) {
        if (z2) {
            return (V) findAbbreviatedValue(map, iKey, z);
        }
        if (z) {
            return map.get(iKey);
        }
        for (IKey iKey2 : map.keySet()) {
            if (iKey2.getName().equalsIgnoreCase(iKey.getName())) {
                return map.get(iKey2);
            }
        }
        return null;
    }

    private static <V> V findAbbreviatedValue(Map<? extends IKey, V> map, IKey iKey, boolean z) {
        String name2 = iKey.getName();
        Map mapNewHashMap = Maps.newHashMap();
        for (IKey iKey2 : map.keySet()) {
            String name3 = iKey2.getName();
            if ((z && name3.startsWith(name2)) || (!z && name3.toLowerCase().startsWith(name2.toLowerCase()))) {
                mapNewHashMap.put(name3, map.get(iKey2));
            }
        }
        if (mapNewHashMap.size() > 1) {
            throw new ParameterException("Ambiguous option: " + iKey + " matches " + mapNewHashMap.keySet());
        }
        if (mapNewHashMap.size() == 1) {
            return mapNewHashMap.values().iterator().next();
        }
        return null;
    }
}
