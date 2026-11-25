package com.android.tools.r8.dex;

import com.android.tools.r8.ByteBufferProvider;
import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.M2;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.C1367Tb;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

/* JADX INFO: renamed from: com.android.tools.r8.dex.x, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0586x {
    static final /* synthetic */ boolean c = true;
    private final ByteBufferProvider a;
    private C0581s b;

    C0586x() {
        this(new C0585w());
    }

    private C0581s b(int i) {
        C0581s c0581s = new C0581s(this.a.acquireByteBuffer(i));
        if (!c0581s.h()) {
            throw new C1367Tb("Provided byte-buffer is required to have an array backing, but does not.");
        }
        if (c0581s.d() < i) {
            throw new C1367Tb("Insufficient capacity of provided byte-buffer. Requested capacity " + i + ", actual capacity: " + c0581s.d());
        }
        if (c0581s.j() == 0) {
            c0581s.a(ByteOrder.LITTLE_ENDIAN);
            return c0581s;
        }
        throw new C1367Tb("Provided byte-buffer is required to start at position zero, but starts at " + c0581s.j() + ".");
    }

    private void c(int i) {
        if (this.b.c().remaining() < i) {
            C0581s c0581sB = b(Math.max(this.b.d(), i * 2) + this.b.d());
            System.arraycopy(this.b.c().array(), 0, c0581sB.c().array(), 0, this.b.d());
            c0581sB.c(this.b.c().position());
            C0581s c0581s = this.b;
            if (!c && c0581s == null) {
                throw new AssertionError();
            }
            this.a.releaseByteBuffer(c0581s.c());
            this.b = c0581sB;
        }
    }

    public final void a(C0702m c0702m, M2 m2, o5 o5Var, C0645e5 c0645e5, r rVar) {
        c(m2.V() * 2);
        if (!c && this.b.c().position() % 2 != 0) {
            throw new AssertionError();
        }
        ShortBuffer shortBufferAsShortBuffer = this.b.c().asShortBuffer();
        m2.a(c0645e5, o5Var, c0645e5.h(), m2.a(c0702m), c0645e5.i(), shortBufferAsShortBuffer);
        m2.a(rVar);
        C0581s c0581s = this.b;
        c0581s.c((shortBufferAsShortBuffer.position() * 2) + c0581s.c().position());
    }

    public final void d(int i) {
        c(i);
        C0581s c0581s = this.b;
        c0581s.c(c0581s.c().position() + i);
    }

    public final void e(int i) {
        c(i - this.b.c().position());
        this.b.c(i);
    }

    public final void f(int i) {
        c(4);
        this.b.c().putInt(i);
    }

    public C0586x(ByteBufferProvider byteBufferProvider) {
        this.a = byteBufferProvider;
        this.b = b(262144);
    }

    public final C0581s d() {
        C0581s c0581s = this.b;
        this.b = null;
        return c0581s;
    }

    public int c() {
        return this.b.c().position();
    }

    public void a(byte b) {
        c(1);
        this.b.c().put(b);
    }

    public void a(byte[] bArr) {
        c(bArr.length);
        this.b.c().put(bArr);
    }

    public final int b() {
        return this.b.f();
    }

    public final void a(short s) {
        c(2);
        this.b.c().putShort(s);
    }

    public final int a(int i) {
        if (!c && i <= 0) {
            throw new AssertionError();
        }
        int i2 = i - 1;
        int iC = (~i2) & (c() + i2);
        c(iC - c());
        this.b.c(iC);
        return iC;
    }

    public byte[] a() {
        return this.b.c().array();
    }
}
