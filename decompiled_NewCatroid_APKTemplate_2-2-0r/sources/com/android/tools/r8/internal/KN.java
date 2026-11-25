package com.android.tools.r8.internal;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class KN extends LN {
    private final FileChannel i;
    private MappedByteBuffer j;
    private final long k;
    private final byte[] l;
    private int m;
    private int n;

    public KN(Path path, Predicate predicate, boolean z) throws IOException {
        super(predicate, z);
        this.l = new byte[8192];
        this.m = 0;
        this.n = 0;
        FileChannel fileChannelOpen = FileChannel.open(path, StandardOpenOption.READ);
        this.i = fileChannelOpen;
        this.k = fileChannelOpen.size();
        g();
    }

    private void g() {
        FileChannel fileChannel = this.i;
        FileChannel.MapMode mapMode = FileChannel.MapMode.READ_ONLY;
        long j = this.m;
        this.j = fileChannel.map(mapMode, j, Math.min(this.k - j, 2147483647L));
    }

    @Override // com.android.tools.r8.internal.LN
    public final boolean b() {
        return this.n == 8192;
    }

    @Override // com.android.tools.r8.internal.LN
    public final int c() {
        int i = this.n;
        if (i > 0) {
            int i2 = i - 1;
            if (this.l[i2] == 13) {
                return i2;
            }
        }
        return i;
    }

    @Override // com.android.tools.r8.naming.M
    public final void close() throws IOException {
        this.i.close();
    }

    @Override // com.android.tools.r8.internal.LN
    public final int d() {
        return 0;
    }

    @Override // com.android.tools.r8.internal.LN
    public final byte[] f() {
        if (this.m >= this.k) {
            return null;
        }
        this.n = 0;
        while (this.m < this.k) {
            if (!this.j.hasRemaining()) {
                g();
            }
            this.m++;
            byte b = this.j.get();
            if (b == 10) {
                break;
            }
            byte[] bArr = this.l;
            int i = this.n;
            int i2 = i + 1;
            this.n = i2;
            bArr[i] = b;
            if (i2 == 8192) {
                break;
            }
        }
        return this.l;
    }
}
