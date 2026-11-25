package com.artemis.utils;

import java.util.BitSet;

/* JADX INFO: loaded from: classes8.dex */
public final class ConverterUtil {
    private ConverterUtil() {
    }

    public static IntBag toIntBag(BitVector bitVector, IntBag intBag) {
        return bitVector.toIntBag(intBag);
    }

    public static IntBag toIntBag(BitSet bitSet, IntBag intBag) {
        if (bitSet.isEmpty()) {
            intBag.setSize(0);
            return intBag;
        }
        int iCardinality = bitSet.cardinality();
        intBag.setSize(iCardinality);
        intBag.ensureCapacity(iCardinality);
        int[] data = intBag.getData();
        int iNextSetBit = -1;
        for (int i = 0; iCardinality > i; i++) {
            iNextSetBit = bitSet.nextSetBit(iNextSetBit + 1);
            data[i] = iNextSetBit;
        }
        return intBag;
    }

    public static BitVector toBitVector(IntBag intBag, BitVector bitVector) {
        int[] data = intBag.getData();
        int size = intBag.size();
        for (int i = 0; size > i; i++) {
            bitVector.set(data[i]);
        }
        return bitVector;
    }

    public static BitSet toBitSet(IntBag intBag, BitSet bitSet) {
        int[] data = intBag.getData();
        int size = intBag.size();
        for (int i = 0; size > i; i++) {
            bitSet.set(data[i]);
        }
        return bitSet;
    }
}
