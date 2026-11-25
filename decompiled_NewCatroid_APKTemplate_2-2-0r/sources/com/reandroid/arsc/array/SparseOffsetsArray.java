package com.reandroid.arsc.array;

/* JADX INFO: loaded from: classes10.dex */
public class SparseOffsetsArray extends IntegerOffsetArray {
    public int getHighestId() {
        int size = size();
        int i = -1;
        for (int i2 = 0; i2 < size; i2++) {
            int idx = getIdx(i2);
            if (idx > i) {
                i = idx;
            }
        }
        if (i == -1) {
            return 0;
        }
        return i;
    }

    public int indexOf(int i) {
        int size = super.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (i == getIdx(i2)) {
                return i2;
            }
        }
        return -1;
    }

    public int getIdx(int i) {
        int i2 = super.get(i);
        return i2 != -1 ? i2 & 65535 : i2;
    }

    public void setIdx(int i, int i2) {
        if (i2 != -1) {
            i2 = (i2 & 65535) | (get(i) & (-65536));
        }
        super.put(i, i2);
    }

    @Override // com.reandroid.arsc.array.IntegerOffsetArray, com.reandroid.arsc.array.OffsetArray
    public int getOffset(int i) {
        int i2 = super.get(i);
        return i2 == -1 ? i2 : ((i2 >>> 16) & 65535) * 4;
    }

    @Override // com.reandroid.arsc.array.IntegerOffsetArray, com.reandroid.arsc.array.OffsetArray
    public void setOffset(int i, int i2) {
        int i3;
        if (i2 == -1) {
            i3 = 0;
        } else {
            i3 = (((i2 & 65535) / 4) << 16) | (get(i) & 65535);
        }
        super.put(i, i3);
    }
}
