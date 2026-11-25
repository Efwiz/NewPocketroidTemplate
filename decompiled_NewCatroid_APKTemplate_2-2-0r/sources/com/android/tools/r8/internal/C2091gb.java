package com.android.tools.r8.internal;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayDeque;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2091gb implements Closeable {
    private static final InterfaceC2012fb c;
    final InterfaceC2012fb a;
    private final ArrayDeque b = new ArrayDeque(4);

    static {
        c = C1934eb.b != null ? C1934eb.a : C1852db.a;
    }

    C2091gb(InterfaceC2012fb interfaceC2012fb) {
        this.a = (InterfaceC2012fb) AbstractC3123uM.a(interfaceC2012fb);
    }

    public static C2091gb c() {
        return new C2091gb(c);
    }

    public final Closeable a(Closeable closeable) {
        if (closeable != null) {
            this.b.addFirst(closeable);
        }
        return closeable;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws Throwable {
        Throwable th = null;
        while (!this.b.isEmpty()) {
            Closeable closeable = (Closeable) this.b.removeFirst();
            try {
                closeable.close();
            } catch (Throwable th2) {
                if (th == null) {
                    th = th2;
                } else {
                    this.a.a(closeable, th, th2);
                }
            }
        }
        if (th != null) {
            int i = AbstractC1701bZ.b;
            int i2 = AbstractC3123uM.a;
            if (IOException.class.isInstance(th)) {
                throw ((Throwable) IOException.class.cast(th));
            }
            if (th instanceof RuntimeException) {
                throw ((RuntimeException) th);
            }
            if (!(th instanceof Error)) {
                throw new AssertionError(th);
            }
            throw ((Error) th);
        }
    }
}
