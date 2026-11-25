package com.android.tools.r8.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class JN extends LN {
    private final InputStream i;
    private final byte[] j;
    private int k;
    private int l;
    private int m;
    private int n;

    public JN(InputStream inputStream, Predicate predicate, boolean z) {
        super(predicate, z);
        this.j = new byte[8192];
        this.k = 8192;
        this.l = 0;
        this.m = 0;
        this.n = 0;
        this.i = inputStream;
    }

    @Override // com.android.tools.r8.internal.LN
    public final boolean b() {
        return this.n == this.m;
    }

    @Override // com.android.tools.r8.internal.LN
    public final int c() {
        int i = this.m;
        if (i > 0) {
            int i2 = i - 1;
            if (this.j[i2] == 13) {
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
        return this.l;
    }

    @Override // com.android.tools.r8.internal.LN
    public final byte[] f() throws IOException {
        int i;
        boolean z = false;
        if (this.k >= this.n) {
            int i2 = this.i.read(this.j);
            this.n = i2;
            if (i2 == -1) {
                return null;
            }
            this.k = 0;
        }
        int i3 = this.k;
        this.l = i3;
        this.m = i3;
        while (true) {
            i = this.m;
            if (i >= this.n) {
                break;
            }
            if (this.j[i] == 10) {
                z = true;
                break;
            }
            this.m = i + 1;
        }
        this.k = i;
        if (z) {
            this.k = i + 1;
        }
        return this.j;
    }
}
