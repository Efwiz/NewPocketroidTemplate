package com.android.tools.r8.utils;

import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class J2 {
    static final /* synthetic */ boolean b = true;
    ArrayList a = new ArrayList();

    public final I2 a(String str) {
        I2 i2 = new I2(str, 1, this);
        this.a.add(i2);
        return i2;
    }

    public final I2 b() {
        I2 i2 = new I2("getValue", 2, this);
        this.a.add(i2);
        return i2;
    }

    public final H2 a() {
        if (!b && this.a.isEmpty()) {
            throw new AssertionError();
        }
        H2 h2A = null;
        for (int size = this.a.size() - 1; size >= 0; size--) {
            h2A = ((I2) this.a.get(size)).a(h2A);
        }
        return h2A;
    }
}
