package com.android.tools.r8.internal;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.slf4j.Marker;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class Y5 implements Iterable {
    public static final Y5 a = new C2904rC(new byte[0]);
    static final /* synthetic */ boolean b = true;

    Y5() {
    }

    public static Y5 a(String str) {
        try {
            return new C2904rC(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported?", e);
        }
    }

    public static X5 d() {
        return new X5();
    }

    protected abstract int a();

    protected abstract int a(int i, int i2, int i3);

    abstract void a(OutputStream outputStream, int i, int i2);

    protected abstract int b(int i, int i2, int i3);

    public final Y5 b(Y5 y5) {
        int size = size();
        int size2 = y5.size();
        if (((long) size) + ((long) size2) < 2147483647L) {
            return C2615nU.a(this, y5);
        }
        throw new IllegalArgumentException(new StringBuilder(53).append("ByteString would be too long: ").append(size).append(Marker.ANY_NON_NULL_MARKER).append(size2).toString());
    }

    protected abstract void b(int i, int i2, int i3, byte[] bArr);

    protected abstract boolean b();

    public abstract boolean c();

    protected abstract int e();

    public abstract String f();

    public abstract int size();

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(size()));
    }

    public static Y5 a(ArrayList arrayList) {
        if (!(arrayList instanceof Collection)) {
            ArrayList arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add((Y5) it.next());
            }
            arrayList = arrayList2;
        }
        if (arrayList.isEmpty()) {
            return a;
        }
        return a(arrayList.size(), arrayList.iterator());
    }

    private static Y5 a(int i, Iterator it) {
        if (!b && i < 1) {
            throw new AssertionError();
        }
        if (i == 1) {
            return (Y5) it.next();
        }
        int i2 = i >>> 1;
        return a(i2, it).b(a(i - i2, it));
    }

    public final void a(int i, int i2, int i3, byte[] bArr) {
        if (i < 0) {
            throw new IndexOutOfBoundsException(new StringBuilder(30).append("Source offset < 0: ").append(i).toString());
        }
        if (i2 < 0) {
            throw new IndexOutOfBoundsException(new StringBuilder(30).append("Target offset < 0: ").append(i2).toString());
        }
        if (i3 >= 0) {
            int i4 = i + i3;
            if (i4 <= size()) {
                int i5 = i2 + i3;
                if (i5 > bArr.length) {
                    throw new IndexOutOfBoundsException(new StringBuilder(34).append("Target end offset < 0: ").append(i5).toString());
                }
                if (i3 > 0) {
                    b(i, i2, i3, bArr);
                    return;
                }
                return;
            }
            throw new IndexOutOfBoundsException(new StringBuilder(34).append("Source end offset < 0: ").append(i4).toString());
        }
        throw new IndexOutOfBoundsException(new StringBuilder(23).append("Length < 0: ").append(i3).toString());
    }
}
