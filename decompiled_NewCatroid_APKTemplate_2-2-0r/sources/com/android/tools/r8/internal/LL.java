package com.android.tools.r8.internal;

import io.netty.util.internal.StringUtil;
import java.util.List;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class LL {
    static final /* synthetic */ boolean c = true;
    private final C3395xv a;
    private final C1651ax b;

    private LL(C3395xv c3395xv, C1651ax c1651ax) {
        this.a = c3395xv;
        this.b = c1651ax;
    }

    public static BiConsumer a(final C2424kx c2424kx) {
        return new BiConsumer() { // from class: com.android.tools.r8.internal.LL$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                LL.a(c2424kx, (StringBuilder) obj, (AbstractC2496lv) obj2);
            }
        };
    }

    abstract String b();

    public final void b(StringBuilder sb) {
        String str;
        C3395xv c3395xv = this.a;
        if (c3395xv.d()) {
            sb.append("# context: ").append(c3395xv.b()).append('\n');
        }
        if (c3395xv.e()) {
            String strC = c3395xv.c();
            char[] charArray = strC.toCharArray();
            int i = 0;
            while (i < charArray.length) {
                char c2 = charArray[i];
                if ((c2 == '\n' ? "\\n" : c2 == '\r' ? "\\r" : null) != null) {
                    StringBuilder sb2 = new StringBuilder(strC.substring(0, i));
                    while (i < charArray.length) {
                        char c3 = charArray[i];
                        String str2 = c3 == '\n' ? "\\n" : c3 == '\r' ? "\\r" : null;
                        if (str2 != null) {
                            sb2.append(str2);
                        } else {
                            sb2.append(c3);
                        }
                        i++;
                    }
                    strC = sb2.toString();
                    break;
                }
                i++;
            }
            sb.append("# description: ").append(strC).append('\n');
        }
        if (d()) {
            sb.append("-if").append(StringUtil.SPACE);
            a(sb);
            List<String> listA = a();
            if (!listA.isEmpty()) {
                sb.append(" {");
                for (String str3 : listA) {
                    sb.append(StringUtil.SPACE);
                    a(sb, str3);
                }
                sb.append(" }");
            }
            sb.append(StringUtil.SPACE);
        }
        sb.append(b());
        C1651ax c1651ax = this.b;
        for (C1651ax.a aVar : C1651ax.a.values()) {
            if (c1651ax.a(aVar)) {
                StringBuilder sbAppend = sb.append(",allow");
                int i2 = AbstractC2852qU.a[aVar.ordinal()];
                if (i2 == 1) {
                    str = "shrinking";
                } else if (i2 == 2) {
                    str = "optimization";
                } else if (i2 == 3) {
                    str = "obfuscation";
                } else if (i2 == 4) {
                    str = "accessmodification";
                } else {
                    if (i2 != 5) {
                        throw new Y00();
                    }
                    str = "annotationremoval";
                }
                sbAppend.append(str);
            }
        }
        sb.append(StringUtil.SPACE);
        c(sb);
        List<String> listC = c();
        if (listC.isEmpty()) {
            return;
        }
        sb.append(" {");
        for (String str4 : listC) {
            sb.append(StringUtil.SPACE);
            b(sb, str4);
        }
        sb.append(" }");
    }

    abstract void b(StringBuilder sb, String str);

    abstract List c();

    abstract void c(StringBuilder sb);

    boolean d() {
        return this instanceof HL;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C2424kx c2424kx, StringBuilder sb, AbstractC2496lv abstractC2496lv) {
        if (!c && !abstractC2496lv.d() && !abstractC2496lv.b().equals(c2424kx)) {
            throw new AssertionError();
        }
        AbstractC2925rU.a(c2424kx, C2781pU.a(sb));
    }

    List a() {
        throw new C2883qv("Unreachable");
    }

    void a(StringBuilder sb) {
        throw new C2883qv("Unreachable");
    }

    void a(StringBuilder sb, String str) {
        throw new C2883qv("Unreachable");
    }
}
