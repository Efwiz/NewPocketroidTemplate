package com.android.tools.r8.internal;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import okhttp3.HttpUrl;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2925rU {
    static final /* synthetic */ boolean a = true;

    public static void a(StringBuilder sb, C0846Aw c0846Aw, BiConsumer biConsumer) {
        sb.append(ExternalAnnotationProvider.CLASS_PREFIX);
        biConsumer.accept(sb, c0846Aw.d());
        AbstractC2884qw abstractC2884qwE = c0846Aw.e();
        if (abstractC2884qwE.d()) {
            return;
        }
        sb.append(" extends ");
        a(abstractC2884qwE.b(), C2781pU.a(sb));
    }

    private static String b(String str) {
        int length = str.length() - 1;
        if (str.charAt(0) == 'L' && str.charAt(length) == ';') {
            return str.substring(1, length).replace('/', '.');
        }
        throw new C2883qv("Invalid class descriptor: " + str);
    }

    private static String c(String str) {
        if (str.isEmpty()) {
            throw new C2883qv("Invalid empty type descriptor");
        }
        if (str.length() == 1) {
            return a(str.charAt(0));
        }
        return str.charAt(0) == '[' ? a(str) : b(str);
    }

    public static void a(AbstractC1171Lw abstractC1171Lw, C2781pU c2781pU) {
        if (abstractC1171Lw.e()) {
            c2781pU.b("*").a(";");
            return;
        }
        if (abstractC1171Lw.h()) {
            C1463Vw c1463VwC = abstractC1171Lw.c();
            C1225Nw c1225NwL = c1463VwC.l();
            a(c2781pU, c1225NwL);
            a(c2781pU, c1225NwL.n(), "synchronized");
            a(c2781pU, c1225NwL.k(), "bridge");
            a(c2781pU, c1225NwL.l(), "native");
            a(c2781pU, c1225NwL.j(), "abstract");
            a(c2781pU, c1225NwL.m(), "strictfp");
            AbstractC1545Yw abstractC1545YwO = c1463VwC.o();
            if (abstractC1545YwO.d()) {
                c2781pU.a(PsiKeyword.VOID);
            } else {
                AbstractC3024sx abstractC3024sxB = abstractC1545YwO.b();
                if (abstractC3024sxB.c()) {
                    c2781pU.d();
                } else {
                    c2781pU.a(c(abstractC3024sxB.b()));
                }
            }
            c2781pU.a(" ");
            AbstractC1309Qw abstractC1309QwM = c1463VwC.m();
            if (abstractC1309QwM.d()) {
                c2781pU.c();
            } else {
                c2781pU.a(abstractC1309QwM.b().e());
            }
            AbstractC1437Uw abstractC1437UwN = c1463VwC.n();
            if (abstractC1437UwN.d()) {
                c2781pU.a();
            } else {
                c2781pU.a("(");
                List listB = abstractC1437UwN.b();
                for (int i = 0; i < listB.size(); i++) {
                    if (i > 0) {
                        c2781pU.a(", ");
                    }
                    AbstractC3024sx abstractC3024sx = (AbstractC3024sx) listB.get(i);
                    if (abstractC3024sx.c()) {
                        c2781pU.d();
                    } else {
                        c2781pU.a(c(abstractC3024sx.b()));
                    }
                }
                c2781pU.a(Constant.AFTER_QUTO);
            }
            c2781pU.a(";");
            return;
        }
        if (abstractC1171Lw.f()) {
            C3319ww c3319wwB = abstractC1171Lw.b();
            C3023sw c3023swL = c3319wwB.l();
            a(c2781pU, c3023swL);
            a(c2781pU, c3023swL.k(), "volatile");
            a(c2781pU, c3023swL.j(), "transient");
            AbstractC3024sx abstractC3024sxB2 = c3319wwB.n().b();
            if (abstractC3024sxB2.c()) {
                c2781pU.d();
            } else {
                c2781pU.a(c(abstractC3024sxB2.b()));
            }
            c2781pU.a(" ");
            AbstractC3244vw abstractC3244vwM = c3319wwB.m();
            if (abstractC3244vwM.c()) {
                c2781pU.c();
            } else {
                c2781pU.a(abstractC3244vwM.b().d());
            }
            c2781pU.a(";");
            return;
        }
        if (!a && abstractC1171Lw.d().f()) {
            throw new AssertionError();
        }
        a(c2781pU, abstractC1171Lw.d());
        c2781pU.b("*").a(";");
    }

    public static void a(C2781pU c2781pU, C1027Gw c1027Gw) {
        if (c1027Gw.f()) {
            return;
        }
        if (!c1027Gw.g()) {
            Set setB = c1027Gw.b();
            boolean zContains = setB.contains(G1.PACKAGE_PRIVATE);
            for (G1 g1 : G1.values()) {
                if (!g1.equals(G1.PACKAGE_PRIVATE) && (!zContains) == setB.contains(g1)) {
                    if (zContains) {
                        c2781pU.a("!");
                    }
                    c2781pU.a(g1.c()).a(" ");
                }
            }
        }
        a(c2781pU, c1027Gw.d(), "static");
        a(c2781pU, c1027Gw.c(), "final");
        a(c2781pU, c1027Gw.e(), "synthetic");
    }

    public static void a(C2781pU c2781pU, AbstractC2450lF abstractC2450lF, String str) {
        if (abstractC2450lF.b()) {
            return;
        }
        if (abstractC2450lF.c()) {
            c2781pU.a("!");
        }
        c2781pU.a(str).a(" ");
    }

    public static void a(C2424kx c2424kx, C2781pU c2781pU) {
        if (c2424kx.e()) {
            c2781pU.c();
            return;
        }
        AbstractC2035fx abstractC2035fxD = c2424kx.d();
        if (abstractC2035fxD.c()) {
            c2781pU.b().a(".");
        } else if (!abstractC2035fxD.e()) {
            if (!a && !abstractC2035fxD.d()) {
                throw new AssertionError();
            }
            c2781pU.a(abstractC2035fxD.b()).a(".");
        }
        AbstractC3246vx abstractC3246vxC = c2424kx.c();
        if (abstractC3246vxC.c()) {
            c2781pU.c();
        } else {
            if (!a && !abstractC3246vxC.d()) {
                throw new AssertionError();
            }
            c2781pU.a(abstractC3246vxC.b().e());
        }
    }

    private static String a(char c) {
        if (c == 'B') {
            return "byte";
        }
        if (c == 'D') {
            return "double";
        }
        if (c == 'F') {
            return "float";
        }
        if (c == 'S') {
            return "short";
        }
        if (c == 'Z') {
            return "boolean";
        }
        if (c == 'I') {
            return "int";
        }
        if (c == 'J') {
            return "long";
        }
        throw new C2883qv("Invalid primitive descriptor: " + c);
    }

    private static String a(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '[') {
                StringBuilder sb = new StringBuilder();
                sb.append(c(str.substring(i)));
                for (int i2 = 0; i2 < i; i2++) {
                    sb.append(HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                }
                return sb.toString();
            }
        }
        throw new C2883qv("Invalid array descriptor: " + str);
    }
}
