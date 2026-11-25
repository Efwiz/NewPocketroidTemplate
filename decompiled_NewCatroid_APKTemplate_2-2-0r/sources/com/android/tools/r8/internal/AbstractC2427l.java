package com.android.tools.r8.internal;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: renamed from: com.android.tools.r8.internal.l, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC2427l extends AbstractC2959s {
    private final ByteBuffer a = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN);

    AbstractC2427l() {
    }

    @Override // com.android.tools.r8.internal.InterfaceC3524zk
    public final InterfaceC3524zk a(byte b) {
        b(b);
        return this;
    }

    protected abstract void b(byte b);

    protected abstract void b(byte[] bArr, int i);

    @Override // com.android.tools.r8.internal.AbstractC2959s, com.android.tools.r8.internal.InterfaceC3524zk
    public final InterfaceC3524zk a(byte[] bArr) {
        int i = AbstractC3123uM.a;
        bArr.getClass();
        b(bArr, bArr.length);
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2959s
    public final InterfaceC3524zk a(byte[] bArr, int i) {
        AbstractC3123uM.a(0, i, bArr.length);
        b(bArr, i);
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3524zk
    public final InterfaceC3524zk a(int i) {
        this.a.putInt(i);
        try {
            b(this.a.array(), 4);
            return this;
        } finally {
            this.a.clear();
        }
    }

    @Override // com.android.tools.r8.internal.InterfaceC3524zk
    public final InterfaceC3524zk a(long j) {
        this.a.putLong(j);
        try {
            b(this.a.array(), 8);
            return this;
        } finally {
            this.a.clear();
        }
    }
}
