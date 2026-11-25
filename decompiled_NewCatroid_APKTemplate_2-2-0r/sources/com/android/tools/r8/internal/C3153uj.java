package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uj, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3153uj extends AbstractC3229vj {
    private final DY g;
    private boolean h;
    private boolean i;
    private boolean j;
    private boolean k;

    public C3153uj(C0767v2 c0767v2, DY dy) {
        super(c0767v2);
        this.h = false;
        this.i = false;
        this.j = false;
        this.k = false;
        this.g = dy;
    }

    private String a(EY.c cVar) {
        if (cVar == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (cVar.e) {
            sb.append("<sup>1</sup>");
            this.h = true;
        }
        if (cVar.f) {
            sb.append("<sup>2</sup>");
            this.i = true;
        }
        if (cVar.a) {
            sb.append("<sup>3</sup>");
            this.j = true;
        }
        if (cVar.g) {
            sb.append("<sup>4</sup>");
            this.k = true;
        }
        return sb.toString();
    }

    public final String toString() {
        String string;
        C3080tj c3080tj = new C3080tj();
        c3080tj.l(LanguageCodeUtil.TR);
        if (this.e.length() > 0) {
            c3080tj.j(this.e);
        }
        c3080tj.h(a(this.d));
        c3080tj.l("td").l("ul style=\"list-style-position:inside; list-style-type: none !important; margin-left:0px;padding-left:0px !important;\"");
        if (!this.a.isEmpty()) {
            for (com.android.tools.r8.graph.T0 t0 : this.a.keySet()) {
                String strA = AbstractC3229vj.a(t0.f);
                String strA2 = a(t0.getReference().h);
                C0759u2 c0759u2 = t0.getReference().f;
                EY.a aVar = (EY.a) this.a.get(t0);
                if (aVar == null) {
                    string = "";
                } else {
                    StringBuilder sb = new StringBuilder();
                    if (aVar.a) {
                        sb.append("<sup>3</sup>");
                        this.j = true;
                    }
                    string = sb.toString();
                }
                c3080tj.f(strA + " " + strA2 + " " + c0759u2 + string);
            }
        }
        if (!this.b.isEmpty()) {
            for (com.android.tools.r8.graph.W0 w0 : this.b.keySet()) {
                c3080tj.g(AbstractC3229vj.a(w0.f) + " " + a(this.d) + super.a(w0) + a((EY.c) this.b.get(w0)));
            }
        }
        if (!this.c.isEmpty()) {
            for (com.android.tools.r8.graph.W0 w1 : this.c.keySet()) {
                c3080tj.g(AbstractC3229vj.a(w1.f) + " " + a(w1.getReference().h.e) + " " + w1.getReference().f + super.a(w1) + a((EY.c) this.c.get(w1)));
            }
        }
        c3080tj.k("ul").k("td");
        StringBuilder sb2 = new StringBuilder();
        if (this.g.e()) {
            sb2.append("Fully implemented class.<br>&nbsp;");
        }
        if (this.g.d()) {
            sb2.append("Additional methods on existing class.<br>&nbsp;");
        }
        if (this.h) {
            sb2.append("<sup>1</sup> Supported only on devices which API level is 21 or higher.<br>&nbsp;");
        }
        if (this.i) {
            sb2.append("<sup>2</sup> Not present in Android ").append(AbstractC2817q.i).append(" (May not resolve at compilation).<br>&nbsp;");
        }
        if (this.j) {
            sb2.append("<sup>3</sup> Not supported at all minSDK levels.<br>&nbsp;");
        }
        if (this.k) {
            sb2.append("<sup>4</sup> Also supported with covariant return type.<br>&nbsp;");
        }
        if (!this.g.b().isEmpty()) {
            sb2.append("Some fields (").append(this.g.b().size()).append(") present in Android ").append(AbstractC2817q.i).append(" are not supported.<br>&nbsp;");
        }
        if (!this.g.c().isEmpty()) {
            sb2.append("Some methods (").append(this.g.c().size()).append(") present in Android ").append(AbstractC2817q.i).append(" are not supported.");
        }
        c3080tj.i(sb2.toString());
        c3080tj.k(LanguageCodeUtil.TR);
        return c3080tj.toString();
    }
}
