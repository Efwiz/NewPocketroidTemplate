package com.android.tools.r8.internal;

import java.io.IOException;
import java.io.StringWriter;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ot, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1249Ot {
    public boolean a() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public int b() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public final C1222Nt c() {
        if (this instanceof C1222Nt) {
            return (C1222Nt) this;
        }
        throw new IllegalStateException("This is not a JSON Array.");
    }

    public final C1333Rt d() {
        if (this instanceof C1333Rt) {
            return (C1333Rt) this;
        }
        throw new IllegalStateException("Not a JSON Object: " + this);
    }

    public long e() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public String f() {
        throw new UnsupportedOperationException(getClass().getSimpleName());
    }

    public final String toString() {
        try {
            StringWriter stringWriter = new StringWriter();
            C1799cu c1799cu = new C1799cu(stringWriter);
            c1799cu.b(true);
            AbstractC2961s00.A.a(c1799cu, this);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
