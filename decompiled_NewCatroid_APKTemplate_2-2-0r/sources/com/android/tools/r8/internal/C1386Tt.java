package com.android.tools.r8.internal;

import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Tt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1386Tt {
    public static AbstractC1249Ot a(String str) {
        try {
            C1486Wt c1486Wt = new C1486Wt(new StringReader(str));
            AbstractC1249Ot abstractC1249OtA = a(c1486Wt);
            abstractC1249OtA.getClass();
            if (!(abstractC1249OtA instanceof C1306Qt) && c1486Wt.t() != 10) {
                throw new C1542Yt("Did not consume the entire document.");
            }
            return abstractC1249OtA;
        } catch (C2905rD e) {
            throw new C1542Yt(e);
        } catch (IOException e2) {
            throw new C1279Pt(e2);
        } catch (NumberFormatException e3) {
            throw new C1542Yt(e3);
        }
    }

    public static AbstractC1249Ot a(C1486Wt c1486Wt) {
        AbstractC1249Ot abstractC1249Ot;
        boolean zJ = c1486Wt.j();
        boolean z = true;
        c1486Wt.b(true);
        try {
            try {
                try {
                    c1486Wt.t();
                    z = false;
                    abstractC1249Ot = (AbstractC1249Ot) AbstractC2961s00.A.a(c1486Wt);
                } catch (Throwable th) {
                    c1486Wt.b(zJ);
                    throw th;
                }
            } catch (C2905rD e) {
                throw new C1542Yt(e);
            } catch (EOFException e2) {
                if (z) {
                    abstractC1249Ot = C1306Qt.a;
                } else {
                    throw new C1542Yt(e2);
                }
            } catch (IOException e3) {
                throw new C1279Pt(e3);
            } catch (NumberFormatException e4) {
                throw new C1542Yt(e4);
            }
            c1486Wt.b(zJ);
            return abstractC1249Ot;
        } catch (OutOfMemoryError e5) {
            throw new C1359St("Failed parsing JSON source: " + c1486Wt + " to Json", e5);
        } catch (StackOverflowError e6) {
            throw new C1359St("Failed parsing JSON source: " + c1486Wt + " to Json", e6);
        }
    }
}
