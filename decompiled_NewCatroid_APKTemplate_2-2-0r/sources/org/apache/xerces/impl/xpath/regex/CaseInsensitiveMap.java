package org.apache.xerces.impl.xpath.regex;

import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes13.dex */
final class CaseInsensitiveMap {
    private static int CHUNK_MASK = 0;
    private static int CHUNK_SHIFT = 10;
    private static int CHUNK_SIZE = 0;
    private static int INITIAL_CHUNK_COUNT = 64;
    private static int LOWER_CASE_MATCH = 1;
    private static int UPPER_CASE_MATCH = 2;
    private static int[][][] caseInsensitiveMap;

    static {
        int i = 1 << 10;
        CHUNK_SIZE = i;
        CHUNK_MASK = i - 1;
        buildCaseInsensitiveMap();
    }

    CaseInsensitiveMap() {
    }

    private static void buildCaseInsensitiveMap() {
        int i;
        caseInsensitiveMap = (int[][][]) Array.newInstance((Class<?>) int[].class, INITIAL_CHUNK_COUNT, CHUNK_SIZE);
        for (int i2 = 0; i2 < 65536; i2++) {
            char c = (char) i2;
            char lowerCase = Character.toLowerCase(c);
            char upperCase = Character.toUpperCase(c);
            if (lowerCase != upperCase || lowerCase != i2) {
                int[] iArrUpdateMap = new int[2];
                if (lowerCase != i2) {
                    iArrUpdateMap[0] = lowerCase;
                    iArrUpdateMap[1] = LOWER_CASE_MATCH;
                    int[] mapping = getMapping(lowerCase);
                    if (mapping != null) {
                        iArrUpdateMap = updateMap(i2, iArrUpdateMap, lowerCase, mapping, LOWER_CASE_MATCH);
                    }
                    i = 2;
                } else {
                    i = 0;
                }
                if (upperCase != i2) {
                    if (i == iArrUpdateMap.length) {
                        iArrUpdateMap = expandMap(iArrUpdateMap, 2);
                    }
                    iArrUpdateMap[i] = upperCase;
                    iArrUpdateMap[i + 1] = UPPER_CASE_MATCH;
                    int[] mapping2 = getMapping(upperCase);
                    if (mapping2 != null) {
                        iArrUpdateMap = updateMap(i2, iArrUpdateMap, upperCase, mapping2, UPPER_CASE_MATCH);
                    }
                }
                set(i2, iArrUpdateMap);
            }
        }
    }

    private static boolean contains(int[] iArr, int i) {
        for (int i2 = 0; i2 < iArr.length; i2 += 2) {
            if (iArr[i2] == i) {
                return true;
            }
        }
        return false;
    }

    private static boolean contains(int[] iArr, int i, int i2) {
        for (int i3 = 0; i3 < iArr.length; i3 += 2) {
            if (iArr[i3] == i && iArr[i3 + 1] == i2) {
                return true;
            }
        }
        return false;
    }

    private static int[] expandAndAdd(int[] iArr, int i, int i2) {
        int length = iArr.length;
        int[] iArr2 = new int[length + 2];
        System.arraycopy(iArr, 0, iArr2, 0, length);
        iArr2[length] = i;
        iArr2[length + 1] = i2;
        return iArr2;
    }

    private static int[] expandMap(int[] iArr, int i) {
        int length = iArr.length;
        int[] iArr2 = new int[i + length];
        System.arraycopy(iArr, 0, iArr2, 0, length);
        return iArr2;
    }

    public static int[] get(int i) {
        if (i < 65536) {
            return getMapping(i);
        }
        return null;
    }

    private static int[] getMapping(int i) {
        int i2 = i >>> CHUNK_SHIFT;
        return caseInsensitiveMap[i2][i & CHUNK_MASK];
    }

    private static void set(int i, int[] iArr) {
        int i2 = i >>> CHUNK_SHIFT;
        caseInsensitiveMap[i2][i & CHUNK_MASK] = iArr;
    }

    private static int[] updateMap(int i, int[] iArr, int i2, int[] iArr2, int i3) {
        for (int i4 = 0; i4 < iArr2.length; i4 += 2) {
            int i5 = iArr2[i4];
            int[] mapping = getMapping(i5);
            if (mapping != null && contains(mapping, i2, i3)) {
                if (!contains(mapping, i)) {
                    set(i5, expandAndAdd(mapping, i, i3));
                }
                if (!contains(iArr, i5)) {
                    iArr = expandAndAdd(iArr, i5, i3);
                }
            }
        }
        if (!contains(iArr2, i)) {
            set(i2, expandAndAdd(iArr2, i, i3));
        }
        return iArr;
    }
}
