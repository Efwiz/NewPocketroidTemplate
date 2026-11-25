package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0645e5;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.M0;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.utils.Q1;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* JADX INFO: renamed from: com.android.tools.r8.dex.u, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0583u {
    private final C0645e5 a;
    private final AbstractC1476Wj b;
    private final M0 c;
    private ByteBuffer d;

    public C0583u(M0 m0, C0645e5 c0645e5, AbstractC1476Wj abstractC1476Wj) {
        this.c = m0;
        this.a = c0645e5;
        this.b = abstractC1476Wj;
        this.d = ByteBuffer.allocate(m0.p0());
    }

    public byte[] a() {
        this.c.a(this, this.a, this.b);
        return Arrays.copyOf(this.d.array(), this.d.position());
    }

    public final void b(int i) {
        byte[] bArrA = Q1.a(i);
        if (this.d.remaining() < bArrA.length) {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(this.d.capacity() * 2);
            byteBufferAllocate.put(this.d.array(), 0, this.d.position());
            this.d = byteBufferAllocate;
        }
        this.d.put(bArrA, 0, bArrA.length);
    }

    public final void c(int i) {
        byte[] bArrB = Q1.b(i);
        if (this.d.remaining() < bArrB.length) {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(this.d.capacity() * 2);
            byteBufferAllocate.put(this.d.array(), 0, this.d.position());
            this.d = byteBufferAllocate;
        }
        this.d.put(bArrB, 0, bArrB.length);
    }

    public final void a(int i) {
        if (this.d.remaining() < 1) {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(this.d.capacity() * 2);
            byteBufferAllocate.put(this.d.array(), 0, this.d.position());
            this.d = byteBufferAllocate;
        }
        this.d.put((byte) i);
    }

    public final void a(C0759u2 c0759u2) {
        if (c0759u2 == null) {
            c(0);
        } else {
            c(this.a.a(c0759u2) + 1);
        }
    }

    public final void a(C0767v2 c0767v2) {
        if (c0767v2 == null) {
            c(0);
        } else {
            c(this.a.b(c0767v2) + 1);
        }
    }
}
