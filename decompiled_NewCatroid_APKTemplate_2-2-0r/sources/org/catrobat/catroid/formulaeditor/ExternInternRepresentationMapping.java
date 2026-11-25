package org.catrobat.catroid.formulaeditor;

import androidx.collection.SparseArrayCompat;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/formulaeditor/ExternInternRepresentationMapping.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ExternInternRepresentationMapping {
    public static final int MAPPING_NOT_FOUND = Integer.MIN_VALUE;
    private int externStringLength = 0;
    private SparseArrayCompat<Integer> externInternMapping = new SparseArrayCompat<>();
    private SparseArrayCompat<ExternToken> internExternMapping = new SparseArrayCompat<>();

    public void putMapping(int externStringStartIndex, int externStringEndIndex, int internListIndex) {
        this.externInternMapping.put(externStringStartIndex, Integer.valueOf(internListIndex));
        this.externInternMapping.put(externStringEndIndex - 1, Integer.valueOf(internListIndex));
        this.internExternMapping.put(internListIndex, new ExternToken(externStringStartIndex, externStringEndIndex));
        if (externStringEndIndex >= this.externStringLength) {
            this.externStringLength = externStringEndIndex;
        }
    }

    public int getExternTokenStartIndex(int internIndex) {
        ExternToken externToken = this.internExternMapping.get(internIndex);
        if (externToken == null) {
            return Integer.MIN_VALUE;
        }
        return externToken.getStartIndex();
    }

    public int getExternTokenEndIndex(int internIndex) {
        ExternToken externToken = this.internExternMapping.get(internIndex);
        if (externToken == null) {
            return Integer.MIN_VALUE;
        }
        return externToken.getEndIndex();
    }

    public int getInternTokenByExternIndex(int externIndex) {
        if (externIndex < 0) {
            return Integer.MIN_VALUE;
        }
        int iSearchDown = searchDown(this.externInternMapping, externIndex - 1);
        int iIntValue = this.externInternMapping.get(externIndex, Integer.MIN_VALUE).intValue();
        int iSearchUp = searchUp(this.externInternMapping, externIndex + 1);
        if (iIntValue != Integer.MIN_VALUE) {
            return iIntValue;
        }
        if (iSearchDown == Integer.MIN_VALUE || iSearchUp == Integer.MIN_VALUE || iSearchDown != iSearchUp) {
            return Integer.MIN_VALUE;
        }
        return iSearchDown;
    }

    public int getExternTokenStartOffset(int externIndex, int internTokenOffsetTo) {
        for (int i = externIndex; i >= 0; i--) {
            if (this.externInternMapping.get(i, Integer.MIN_VALUE).intValue() != Integer.MIN_VALUE && this.externInternMapping.get(i, Integer.MIN_VALUE).intValue() == internTokenOffsetTo) {
                int externTokenStartOffset = getExternTokenStartOffset(i - 1, internTokenOffsetTo);
                return externTokenStartOffset == -1 ? externIndex - i : (externIndex - i) + externTokenStartOffset + 1;
            }
        }
        return -1;
    }

    private int searchDown(SparseArrayCompat<Integer> mapping, int index) {
        while (index >= 0) {
            if (mapping.get(index, Integer.MIN_VALUE).intValue() != Integer.MIN_VALUE) {
                return mapping.get(index).intValue();
            }
            index--;
        }
        return Integer.MIN_VALUE;
    }

    private int searchUp(SparseArrayCompat<Integer> mapping, int index) {
        while (index < this.externStringLength) {
            if (mapping.get(index, Integer.MIN_VALUE).intValue() != Integer.MIN_VALUE) {
                return mapping.get(index).intValue();
            }
            index++;
        }
        return Integer.MIN_VALUE;
    }
}
