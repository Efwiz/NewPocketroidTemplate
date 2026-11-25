package com.reandroid.utils;

/* JADX INFO: loaded from: classes11.dex */
public class Nibble extends Number {
    public static final int BYTES = 1;
    public static final byte MAX_VALUE = 7;
    public static final byte MIN_VALUE = -8;
    public static final int SIZE = 4;
    private static final Nibble[] VALUES;
    private final int value;

    public static int toSigned(int i) {
        return i < 8 ? i : i - 16;
    }

    public static int toUnsigned(int i) {
        return i >= 0 ? i : i + 16;
    }

    static {
        Nibble[] nibbleArr = new Nibble[16];
        VALUES = nibbleArr;
        for (int i = 0; i < 16; i++) {
            nibbleArr[i] = new Nibble(i - 8);
        }
    }

    public Nibble(int i) {
        if (i < -8 || i > 7) {
            throw new NumberFormatException("Nibble value out of range: " + i);
        }
        this.value = i;
    }

    @Override // java.lang.Number
    public int intValue() {
        return this.value;
    }

    @Override // java.lang.Number
    public long longValue() {
        return intValue();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return intValue();
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return intValue();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof Number) && this.value == ((Number) obj).intValue();
    }

    public int hashCode() {
        return this.value;
    }

    public String toString() {
        return Integer.toString(intValue());
    }

    public static Nibble valueOf(int i) {
        return VALUES[i + 8];
    }
}
