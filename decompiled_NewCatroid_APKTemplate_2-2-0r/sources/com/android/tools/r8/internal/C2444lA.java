package com.android.tools.r8.internal;

import com.android.tools.r8.utils.InterfaceC3905h3;
import com.huawei.hms.ml.language.common.utils.Constant;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import org.jetbrains.kotlin.codegen.inline.SMAP;
import org.slf4j.Marker;

/* JADX INFO: renamed from: com.android.tools.r8.internal.lA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2444lA {
    static final /* synthetic */ boolean a = true;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.lA$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private final c a;
        private final com.android.tools.r8.naming.N0 b;

        public a(c cVar, com.android.tools.r8.naming.N0 n0) {
            this.a = cVar;
            this.b = n0;
        }

        public com.android.tools.r8.naming.N0 a() {
            return this.b;
        }

        public c b() {
            return this.a;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.b.a);
            sb.append("#");
            sb.append(this.a);
            com.android.tools.r8.naming.N0 n0 = this.b;
            if (n0.b != n0.a) {
                sb.append(",");
                sb.append(this.b.b);
            }
            sb.append(":");
            return sb.toString();
        }
    }

    /* JADX INFO: renamed from: com.android.tools.r8.internal.lA$b */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class b {
        private final com.android.tools.r8.utils.Q2 a;
        private final com.android.tools.r8.utils.Q2 b;

        public b(com.android.tools.r8.utils.Q2 q2, com.android.tools.r8.utils.Q2 q3) {
            this.a = q2;
            this.b = q3;
        }

        public final Map.Entry a(int i) {
            return this.b.a(Integer.valueOf(i));
        }

        public Map.Entry<Integer, a> b(int i) {
            return this.a.a(Integer.valueOf(i));
        }

        public int a() {
            return this.a.a();
        }
    }

    /* JADX INFO: renamed from: com.android.tools.r8.internal.lA$c */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class c {
        private final String a;
        private final String b;

        private c(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public String a() {
            return this.a;
        }

        public String b() {
            return this.b;
        }

        public final String toString() {
            return this.b + "(" + this.a + Constant.AFTER_QUTO;
        }
    }

    private static String a(C2287jA c2287jA, final C2519mA c2519mA) throws Throwable {
        c2287jA.d();
        c2287jA.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.lA$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) throws C2362kA {
                C2444lA.a(c2519mA, (List) obj);
            }
        });
        if (c2287jA.c()) {
            throw new C2362kA("Unexpected EOF - no debug line positions");
        }
        return c2287jA.a(new Predicate() { // from class: com.android.tools.r8.internal.lA$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2444lA.b((String) obj);
            }
        }, 1, new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.lA$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) throws C2362kA {
                C2444lA.b(c2519mA, (List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(String str) {
        return str.equals(SMAP.END) || str.startsWith(SMAP.STRATA_SECTION);
    }

    public static b c(String str) {
        if (str != null && !str.isEmpty()) {
            try {
                C2287jA c2287jA = new C2287jA(str);
                try {
                    if (!c2287jA.e().equals("SMAP")) {
                        c2287jA.a.close();
                        return null;
                    }
                    while (!"*S Kotlin".equals(c2287jA.b) && !c2287jA.c()) {
                        c2287jA.e();
                    }
                    if (c2287jA.c()) {
                        c2287jA.a.close();
                        return null;
                    }
                    C2519mA c2519mA = new C2519mA();
                    C2519mA c2519mA2 = new C2519mA();
                    String strA = a(c2287jA, c2519mA);
                    if (strA.equals(SMAP.END)) {
                        String strE = c2287jA.e();
                        if (c2287jA.c()) {
                            if (!a && strE != null) {
                                throw new AssertionError();
                            }
                            b bVar = new b(c2519mA.a, c2519mA2.a);
                            c2287jA.a.close();
                            return bVar;
                        }
                        if (!strE.equals("*S KotlinDebug")) {
                            c2287jA.a.close();
                            return null;
                        }
                    } else if (!strA.equals("*S KotlinDebug")) {
                        c2287jA.a.close();
                        return null;
                    }
                    a(c2287jA, c2519mA2);
                    if (c2287jA.c() && !c2287jA.b.equals(SMAP.END)) {
                        throw new C2362kA("Unexpected EOF when parsing SMAP debug entries");
                    }
                    b bVar2 = new b(c2519mA.a, c2519mA2.a);
                    c2287jA.a.close();
                    return bVar2;
                } catch (Throwable th) {
                    try {
                        c2287jA.a.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (C2362kA | IOException unused) {
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(C2519mA c2519mA, List list) throws C2362kA {
        a((String) list.get(0), c2519mA);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C2519mA c2519mA, List list) throws C2362kA {
        a((String) list.get(0), (String) list.get(1), c2519mA);
    }

    private static void a(String str, String str2, C2519mA c2519mA) throws C2362kA {
        String[] strArrA = com.android.tools.r8.utils.Y2.a(str.trim(), StringUtil.SPACE, 3);
        if (strArrA != null && strArrA[0].equals(Marker.ANY_NON_NULL_MARKER)) {
            String str3 = strArrA[2];
            if (!str3.isEmpty()) {
                if (str2 != null && !str2.isEmpty()) {
                    int iA = a(strArrA[1]);
                    c cVar = new c(str3, str2);
                    if (((c) c2519mA.b.put(Integer.valueOf(iA), cVar)) != null) {
                        throw new C2362kA("File index " + iA + " was already mapped to an existing source: " + cVar);
                    }
                    return;
                }
                throw new C2362kA("Did not expect file path to be null or empty for " + str);
            }
            throw new C2362kA("Did not expect file name to be empty for line " + str);
        }
        throw new C2362kA("Wrong number of entries on line " + str);
    }

    private static int a(String str) throws C2362kA {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            throw new C2362kA("Could not parse number " + str);
        }
    }

    private static void a(String str, C2519mA c2519mA) throws C2362kA {
        int i;
        int i2;
        try {
            int iIndexOf = str.indexOf(58);
            int i3 = iIndexOf + 1;
            int iIndexOf2 = str.indexOf(44, iIndexOf);
            if (iIndexOf2 > -1) {
                i = Integer.parseInt(str.substring(i3, iIndexOf2));
                i2 = Integer.parseInt(str.substring(iIndexOf2 + 1));
            } else {
                i = Integer.parseInt(str.substring(i3));
                i2 = 1;
            }
            String strSubstring = str.substring(0, iIndexOf);
            int iIndexOf3 = strSubstring.indexOf(35);
            int i4 = Integer.parseInt(strSubstring.substring(0, iIndexOf3));
            String strSubstring2 = strSubstring.substring(iIndexOf3 + 1);
            int iIndexOf4 = strSubstring2.indexOf(44);
            if (iIndexOf4 > -1) {
                if (!a && iIndexOf4 <= 0) {
                    throw new AssertionError();
                }
                i2 = Integer.parseInt(strSubstring2.substring(iIndexOf4 + 1));
            } else {
                iIndexOf4 = strSubstring2.length();
            }
            int i5 = Integer.parseInt(strSubstring2.substring(0, iIndexOf4));
            c cVar = (c) c2519mA.b.get(Integer.valueOf(i5));
            if (cVar != null) {
                int i6 = i2 - 1;
                c2519mA.a.a(i, i6 + i, new a(cVar, new com.android.tools.r8.naming.N0(i4, i4 + i6)));
                return;
            }
            throw new C2362kA("Could not find file with index " + i5);
        } catch (NumberFormatException unused) {
            throw new C2362kA("Could not convert position to number");
        }
    }
}
