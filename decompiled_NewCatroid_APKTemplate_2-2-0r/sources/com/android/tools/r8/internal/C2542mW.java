package com.android.tools.r8.internal;

import com.android.tools.r8.retrace.StackTraceLineParser;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mW, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2542mW implements StackTraceLineParser {
    static final /* synthetic */ boolean l = true;
    private final Pattern a;
    private final ArrayList c;
    private final C2389kW b = new C2389kW();
    private final C2468lW d = new C2468lW();
    private final ZV e = new ZV();
    private final C2310jW f = new C2310jW();
    private final C1929eW g = new C1929eW();
    private final C1847dW h = new C1847dW();
    private final C2006fW i = new C2006fW();
    private final C2086gW j = new C2086gW();
    private final C1770cW k = new C1770cW();

    public C2542mW(String str) {
        ArrayList arrayList = new ArrayList();
        this.c = arrayList;
        StringBuilder sb = new StringBuilder();
        a(str, sb, arrayList);
        this.a = Pattern.compile(sb.toString());
    }

    @Override // com.android.tools.r8.retrace.StackTraceLineParser
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final YV parse(String str) {
        WV wvA = YV.a(str);
        Matcher matcher = this.a.matcher(str);
        if (matcher.matches()) {
            boolean zA = false;
            for (InterfaceC2240iW interfaceC2240iW : this.c) {
                if (!zA || !interfaceC2240iW.a()) {
                    if (interfaceC2240iW.a(wvA, matcher)) {
                        zA |= interfaceC2240iW.a();
                    }
                }
            }
        }
        return wvA.a();
    }

    private void a(String str, StringBuilder sb, ArrayList arrayList) {
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        int i2 = 0;
        for (int i3 = 0; i3 < str.length(); i3++) {
            if (z) {
                if (!l && z2) {
                    throw new AssertionError();
                }
                AbstractC2160hW abstractC2160hWA = a(str.charAt(i3));
                sb.append((CharSequence) str, i, i3 - 1);
                abstractC2160hWA.getClass();
                int i4 = i2 + 1;
                String str2 = "captureGroup" + i2;
                sb.append("(?<").append(str2).append(">").append(abstractC2160hWA.a()).append(Constant.AFTER_QUTO);
                arrayList.add(abstractC2160hWA.a(str2));
                i2 = i4;
                i = i3 + 1;
                z = false;
            } else {
                boolean z3 = !z2 && str.charAt(i3) == '%';
                z2 = !z2 && str.charAt(i3) == '\\';
                z = z3;
            }
        }
        sb.append((CharSequence) str, i, str.length());
    }

    private AbstractC2160hW a(char c) {
        if (c == 'C') {
            return this.e;
        }
        if (c == 'S') {
            return this.b;
        }
        if (c == 'a') {
            return this.i;
        }
        if (c == 'c') {
            return this.d;
        }
        if (c == 'f') {
            return this.k;
        }
        if (c == 'l') {
            return this.g;
        }
        if (c == 'm') {
            return this.j;
        }
        if (c == 's') {
            return this.f;
        }
        if (c != 't') {
            throw new C3405y10("Unexpected variable: " + c);
        }
        return this.h;
    }
}
