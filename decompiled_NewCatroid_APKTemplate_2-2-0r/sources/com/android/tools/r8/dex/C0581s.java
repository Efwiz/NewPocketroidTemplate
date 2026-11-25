package com.android.tools.r8.dex;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: renamed from: com.android.tools.r8.dex.s, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0581s {
    private final ByteBuffer a;

    public C0581s(ByteBuffer byteBuffer) {
        this.a = byteBuffer;
    }

    public static C0581s b(byte[] bArr) {
        return new C0581s(ByteBuffer.wrap(bArr));
    }

    public final byte[] a() {
        return c().array();
    }

    public void c(int i) {
        this.a.position(i);
    }

    public int d() {
        return c().capacity();
    }

    public final byte e() {
        return c().get();
    }

    public int f() {
        return c().getInt();
    }

    public short g() {
        return c().getShort();
    }

    public final boolean h() {
        return c().hasArray();
    }

    public final boolean i() {
        return c().hasRemaining();
    }

    public final int j() {
        return c().position();
    }

    public final void k() {
        this.a.rewind();
    }

    public void a(ByteOrder byteOrder) {
        c().order(byteOrder);
    }

    public final int b() {
        return c().arrayOffset();
    }

    public byte a(int i) {
        return c().get(i);
    }

    public int b(int i) {
        return c().getInt(i);
    }

    public ByteBuffer c() {
        return this.a;
    }

    public void a(byte[] bArr) {
        c().get(bArr);
    }
}
