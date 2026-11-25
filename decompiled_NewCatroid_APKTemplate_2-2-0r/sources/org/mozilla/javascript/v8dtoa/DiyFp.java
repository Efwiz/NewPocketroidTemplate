package org.mozilla.javascript.v8dtoa;

/* JADX INFO: loaded from: classes6.dex */
class DiyFp {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int kSignificandSize = 64;
    static final long kUint64MSB = Long.MIN_VALUE;
    private int e;
    private long f;

    private static boolean uint64_gte(long j, long j2) {
        if (j == j2) {
            return true;
        }
        return (((j > 0L ? 1 : (j == 0L ? 0 : -1)) < 0) ^ ((j > j2 ? 1 : (j == j2 ? 0 : -1)) > 0)) ^ ((j2 > 0L ? 1 : (j2 == 0L ? 0 : -1)) < 0);
    }

    DiyFp() {
        this.f = 0L;
        this.e = 0;
    }

    DiyFp(long j, int i) {
        this.f = j;
        this.e = i;
    }

    void subtract(DiyFp diyFp) {
        this.f -= diyFp.f;
    }

    static DiyFp minus(DiyFp diyFp, DiyFp diyFp2) {
        DiyFp diyFp3 = new DiyFp(diyFp.f, diyFp.e);
        diyFp3.subtract(diyFp2);
        return diyFp3;
    }

    void multiply(DiyFp diyFp) {
        long j = this.f;
        long j2 = j >>> 32;
        long j3 = j & 4294967295L;
        long j4 = diyFp.f;
        long j5 = j4 >>> 32;
        long j6 = j4 & 4294967295L;
        long j7 = j2 * j5;
        long j8 = j5 * j3;
        long j9 = j2 * j6;
        this.e += diyFp.e + 64;
        this.f = j7 + (j9 >>> 32) + (j8 >>> 32) + ((((((j3 * j6) >>> 32) + (j9 & 4294967295L)) + (4294967295L & j8)) + 2147483648L) >>> 32);
    }

    static DiyFp times(DiyFp diyFp, DiyFp diyFp2) {
        DiyFp diyFp3 = new DiyFp(diyFp.f, diyFp.e);
        diyFp3.multiply(diyFp2);
        return diyFp3;
    }

    void normalize() {
        long j = this.f;
        int i = this.e;
        while (((-18014398509481984L) & j) == 0) {
            j <<= 10;
            i -= 10;
        }
        while ((Long.MIN_VALUE & j) == 0) {
            j <<= 1;
            i--;
        }
        this.f = j;
        this.e = i;
    }

    static DiyFp normalize(DiyFp diyFp) {
        DiyFp diyFp2 = new DiyFp(diyFp.f, diyFp.e);
        diyFp2.normalize();
        return diyFp2;
    }

    long f() {
        return this.f;
    }

    int e() {
        return this.e;
    }

    void setF(long j) {
        this.f = j;
    }

    void setE(int i) {
        this.e = i;
    }

    public String toString() {
        return "[DiyFp f:" + this.f + ", e:" + this.e + "]";
    }
}
