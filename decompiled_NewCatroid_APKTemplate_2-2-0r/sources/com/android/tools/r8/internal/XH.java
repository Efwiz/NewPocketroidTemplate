package com.android.tools.r8.internal;

import java.util.function.BiFunction;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class XH {
    static final /* synthetic */ boolean e = true;
    private BS a;
    private BS b;
    private BS c;
    private BS d;

    public static BS a(WH wh, Function function) {
        XH xh = new XH();
        BS bs = (BS) function.apply(xh);
        if (wh == WH.i()) {
            xh.a = bs;
        } else if (wh == WH.c()) {
            xh.b = bs;
        } else if (wh == WH.b()) {
            xh.c = bs;
        } else {
            if (!e && wh != WH.a()) {
                throw new AssertionError();
            }
            xh.d = bs;
        }
        return bs;
    }

    final BS a(WH wh, BiFunction biFunction) {
        BS bs;
        BS bs2;
        if (wh == WH.i()) {
            bs = this.a;
        } else if (wh == WH.c()) {
            bs = this.b;
        } else if (wh == WH.b()) {
            bs = this.c;
        } else {
            if (!e && wh != WH.a()) {
                throw new AssertionError();
            }
            bs = this.d;
        }
        if (bs != null) {
            return bs;
        }
        synchronized (this) {
            if (wh == WH.i()) {
                bs2 = this.a;
            } else if (wh == WH.c()) {
                bs2 = this.b;
            } else if (wh == WH.b()) {
                bs2 = this.c;
            } else {
                if (!e && wh != WH.a()) {
                    throw new AssertionError();
                }
                bs2 = this.d;
            }
            if (bs2 != null) {
                return bs2;
            }
            BS bs3 = (BS) biFunction.apply(wh, this);
            boolean z = e;
            if (!z && bs3 == null) {
                throw new AssertionError();
            }
            if (wh == WH.i()) {
                this.a = bs3;
            } else if (wh == WH.c()) {
                this.b = bs3;
            } else if (wh == WH.b()) {
                this.c = bs3;
            } else {
                if (!z && wh != WH.a()) {
                    throw new AssertionError();
                }
                this.d = bs3;
            }
            return bs3;
        }
    }
}
