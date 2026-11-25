package com.android.tools.r8.naming;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.StringConsumer;
import com.android.tools.r8.internal.AbstractC0860Bk;
import com.android.tools.r8.internal.InterfaceC3524zk;
import java.nio.charset.StandardCharsets;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.naming.v0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3667v0 implements StringConsumer {

    /* JADX INFO: renamed from: com.android.tools.r8.naming.v0$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        static final /* synthetic */ boolean c = true;
        private final boolean a;
        private final String b;

        private a(String str, boolean z) {
            this.a = z;
            this.b = str;
        }

        public static a a() {
            return new a(null, false);
        }

        public static a b(String str) {
            return new a(str, false);
        }

        public final boolean c() {
            return this.a;
        }

        public boolean d() {
            return !this.a && this.b == null;
        }

        public static a a(String str) {
            return new a(str, true);
        }

        public final String b() {
            if (c || this.b != null) {
                return this.b;
            }
            throw new AssertionError();
        }
    }

    public static a a(String str) {
        int i = -1;
        while (true) {
            int i2 = i + 1;
            int iIndexOf = str.indexOf(10, i2);
            if (iIndexOf < 0) {
                return a.b("Failure to find map hash");
            }
            String strTrim = str.substring(i2, iIndexOf).trim();
            if (!strTrim.isEmpty()) {
                if (strTrim.charAt(0) != '#') {
                    return a.b("Failure to find map hash in header");
                }
                String strTrim2 = strTrim.substring(1).trim();
                if (strTrim2.startsWith("pg_map_hash")) {
                    int iIndexOf2 = strTrim2.indexOf("SHA-256 ", 11);
                    if (iIndexOf2 < 0) {
                        return a.a("Unknown map hash function: '" + strTrim2 + FormatHelper.QUOTE);
                    }
                    String strTrim3 = strTrim2.substring(iIndexOf2 + 7).trim();
                    InterfaceC3524zk interfaceC3524zkA = AbstractC0860Bk.b().a();
                    interfaceC3524zkA.a(str.substring(iIndexOf + 1), StandardCharsets.UTF_8);
                    String string = interfaceC3524zkA.a().toString();
                    return strTrim3.equals(string) ? a.a() : a.a("Mismatching map hash: '" + strTrim3 + "' != '" + string + FormatHelper.QUOTE);
                }
            }
            i = iIndexOf;
        }
    }

    @Override // com.android.tools.r8.StringConsumer
    public final void accept(String str, DiagnosticsHandler diagnosticsHandler) {
        throw null;
    }

    @Override // com.android.tools.r8.J
    public final void finished(DiagnosticsHandler diagnosticsHandler) {
        throw null;
    }
}
