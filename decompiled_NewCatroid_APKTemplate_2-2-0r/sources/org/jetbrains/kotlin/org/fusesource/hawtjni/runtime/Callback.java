package org.jetbrains.kotlin.org.fusesource.hawtjni.runtime;

/* JADX INFO: loaded from: classes4.dex */
public class Callback {
    static final String PTR_SIGNATURE = "J";
    static final String SIGNATURE_0 = getSignature(0);
    static final String SIGNATURE_1 = getSignature(1);
    static final String SIGNATURE_2 = getSignature(2);
    static final String SIGNATURE_3 = getSignature(3);
    static final String SIGNATURE_4 = getSignature(4);
    static final String SIGNATURE_N = "([J)J";
    long address;
    int argCount;
    long errorResult;
    boolean isArrayBased;
    boolean isStatic;
    String method;
    Object object;
    String signature;

    static native synchronized long bind(Callback callback, Object obj, String str, String str2, int i, boolean z, boolean z2, long j);

    public static final native synchronized boolean getEnabled();

    public static native int getEntryCount();

    public static native String getPlatform();

    public static final native synchronized void reset();

    public static final native synchronized void setEnabled(boolean z);

    static final native synchronized void unbind(Callback callback);

    public Callback(Object obj, String str, int i) {
        this(obj, str, i, false);
    }

    public Callback(Object obj, String str, int i, boolean z) {
        this(obj, str, i, z, 0L);
    }

    public Callback(Object obj, String str, int i, boolean z, long j) {
        this.object = obj;
        this.method = str;
        this.argCount = i;
        this.isStatic = obj instanceof Class;
        this.isArrayBased = z;
        this.errorResult = j;
        if (z) {
            this.signature = SIGNATURE_N;
        } else if (i == 0) {
            this.signature = SIGNATURE_0;
        } else if (i == 1) {
            this.signature = SIGNATURE_1;
        } else if (i == 2) {
            this.signature = SIGNATURE_2;
        } else if (i == 3) {
            this.signature = SIGNATURE_3;
        } else if (i == 4) {
            this.signature = SIGNATURE_4;
        } else {
            this.signature = getSignature(i);
        }
        this.address = bind(this, obj, str, this.signature, i, this.isStatic, z, j);
    }

    public void dispose() {
        if (this.object == null) {
            return;
        }
        unbind(this);
        this.signature = null;
        this.method = null;
        this.object = null;
        this.address = 0L;
    }

    public long getAddress() {
        return this.address;
    }

    static String getSignature(int i) {
        String str = "(";
        for (int i2 = 0; i2 < i; i2++) {
            str = str + PTR_SIGNATURE;
        }
        return str + ")J";
    }
}
