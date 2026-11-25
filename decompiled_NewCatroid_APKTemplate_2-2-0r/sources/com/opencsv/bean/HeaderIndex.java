package com.opencsv.bean;

import java.util.Arrays;
import java.util.Collection;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.ArrayUtils;

/* JADX INFO: loaded from: classes10.dex */
public class HeaderIndex {
    private String[] positionToHeader = new String[0];
    private MultiValuedMap<String, Integer> headerToPosition = new ArrayListValuedHashMap();

    public void clear() {
        this.positionToHeader = new String[0];
        this.headerToPosition.clear();
    }

    public int findMaxIndex() {
        return this.positionToHeader.length - 1;
    }

    public void initializeHeaderIndex(String[] strArr) {
        this.positionToHeader = strArr != null ? (String[]) ArrayUtils.clone(strArr) : new String[0];
        this.headerToPosition.clear();
        for (int i = 0; i < this.positionToHeader.length; i++) {
            this.headerToPosition.put(strArr[i], Integer.valueOf(i));
        }
    }

    public boolean isEmpty() {
        return this.positionToHeader.length == 0;
    }

    public int[] getByName(String str) {
        Collection<Integer> collection = this.headerToPosition.get(str);
        if (collection != null) {
            return ArrayUtils.toPrimitive((Integer[]) collection.toArray(new Integer[collection.size()]));
        }
        return new int[0];
    }

    public String getByPosition(int i) {
        String[] strArr = this.positionToHeader;
        if (i < strArr.length) {
            return strArr[i];
        }
        return null;
    }

    public String[] getHeaderIndex() {
        return (String[]) ArrayUtils.clone(this.positionToHeader);
    }

    public int getHeaderIndexLength() {
        return this.positionToHeader.length;
    }

    public void put(int i, String str) {
        String[] strArr = this.positionToHeader;
        if (i >= strArr.length) {
            String[] strArr2 = (String[]) Arrays.copyOf(strArr, i + 1);
            this.positionToHeader = strArr2;
            strArr2[i] = str;
        }
        this.headerToPosition.put(str, Integer.valueOf(i));
    }
}
