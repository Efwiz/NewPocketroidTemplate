package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.internal.AbstractC1249Ot;
import com.android.tools.r8.internal.C1333Rt;
import com.android.tools.r8.internal.C1386Tt;
import com.android.tools.r8.internal.C1542Yt;
import com.android.tools.r8.internal.C1684bJ;
import com.android.tools.r8.internal.RI;
import java.io.UTFDataFormatException;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.dex.n, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0576n {
    static final /* synthetic */ boolean b = true;
    private final C1684bJ a = new C1684bJ();

    public C0576n() {
        boolean z = b;
        if (!z && "~~~".charAt(0) != '~') {
            throw new AssertionError();
        }
        if (!z && "~~~".charAt(1) != '~') {
            throw new AssertionError();
        }
        if (!z && "~~~".charAt(2) != '~') {
            throw new AssertionError();
        }
    }

    private void a(C1333Rt c1333Rt) {
        c1333Rt.entrySet().forEach(new Consumer() { // from class: com.android.tools.r8.dex.n$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((Map.Entry) obj);
            }
        });
    }

    public final String b() {
        final C1333Rt c1333Rt = new C1333Rt();
        this.a.e().stream().sorted(Comparator.comparing(new Function() { // from class: com.android.tools.r8.dex.n$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (String) ((RI) obj).getKey();
            }
        })).forEach(new Consumer() { // from class: com.android.tools.r8.dex.n$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0576n.a(c1333Rt, (RI) obj);
            }
        });
        return "~~~" + c1333Rt;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Map.Entry entry) {
        this.a.a((String) entry.getKey(), Long.parseLong(((AbstractC1249Ot) entry.getValue()).f(), 16));
    }

    public final void a(String str, long j) {
        this.a.a(str, j);
    }

    public final C1684bJ a() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1333Rt c1333Rt, RI ri) {
        c1333Rt.a((String) ri.getKey(), Long.toString(ri.getLongValue(), 16));
    }

    public static boolean a(C0759u2 c0759u2) {
        try {
            char[] cArr = new char[3];
            int iA = c0759u2.a(cArr);
            if (iA == 0) {
                return true;
            }
            char c = cArr[0];
            if (c != '~') {
                return c < '~';
            }
            if (iA == 1) {
                return true;
            }
            char c2 = cArr[1];
            if (c2 != '~') {
                return c2 < '~';
            }
            return iA == 2 || cArr[2] < '~';
        } catch (UTFDataFormatException e) {
            throw new RuntimeException("Bad format", e);
        }
    }

    public final void b(C0759u2 c0759u2) {
        if (c0759u2.d > 2) {
            byte[] bArr = c0759u2.e;
            if (bArr[0] == 126 && bArr[1] == 126 && bArr[2] == 126) {
                try {
                    AbstractC1249Ot abstractC1249OtA = C1386Tt.a(c0759u2.toString().substring(3));
                    if (abstractC1249OtA instanceof C1333Rt) {
                        a(abstractC1249OtA.d());
                    }
                } catch (C1542Yt unused) {
                }
            }
        }
    }
}
