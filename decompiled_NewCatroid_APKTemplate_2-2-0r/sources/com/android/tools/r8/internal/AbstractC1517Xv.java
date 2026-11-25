package com.android.tools.r8.internal;

import java.util.function.Consumer;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Xv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC1517Xv extends AbstractC1057Hv {
    private String b = null;
    private EnumC3537zw c = null;
    private final C1087Iv d = new C1087Iv();
    private final C1144Kv e = new C1144Kv();
    private AbstractC0940Dw g = null;
    private final C1801cw f = new C1801cw(c());

    /* JADX WARN: Multi-variable type inference failed */
    AbstractC1517Xv() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AbstractC1171Lw abstractC1171Lw) {
    }

    public final AbstractC0940Dw d() {
        AbstractC0940Dw abstractC0940Dw = this.g;
        if (abstractC0940Dw != null) {
            return abstractC0940Dw;
        }
        throw new C2883qv("Item reference not finalized. Missing call to visitEnd()");
    }

    public final EnumC3537zw e() {
        return this.c;
    }

    public final boolean f() {
        C1801cw c1801cw = this.f;
        if (c1801cw.b == null) {
            C1956ew c1956ew = c1801cw.c;
            if (c1956ew.b == null && c1956ew.c == null) {
                C1197Mv c1197Mv = c1801cw.d;
                if (c1197Mv.b == null && c1197Mv.c == null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, String str2, String str3) {
        if (!str2.equals(AbstractC2277j2.a)) {
            super.a(str, str2, str3);
            throw null;
        }
        str3.getClass();
        str3.hashCode();
        switch (str3) {
            case "DEFAULT":
                return;
            case "ONLY_MEMBERS":
                this.c = EnumC3537zw.b;
                return;
            case "CLASS_AND_MEMBERS":
                this.c = EnumC3537zw.c;
                return;
            case "ONLY_CLASS":
                this.c = EnumC3537zw.a;
                return;
            default:
                super.a(str, str2, str3);
                throw null;
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public void a(String str, Object obj) {
        AbstractC3024sx abstractC3024sxA;
        AbstractC3024sx abstractC3024sxA2;
        AbstractC1545Yw abstractC1545YwA;
        if (str.equals("memberFromBinding") && (obj instanceof String)) {
            this.b = (String) obj;
            return;
        }
        C1087Iv c1087Iv = this.d;
        Object objA = c1087Iv.a(obj, str);
        if (objA != null) {
            if (((AbstractC2188hw) c1087Iv).b == null && ((AbstractC2188hw) c1087Iv).c == null) {
                ((AbstractC2188hw) c1087Iv).a = str;
                ((AbstractC2188hw) c1087Iv).b = objA;
                return;
            } else {
                c1087Iv.a(str);
                throw null;
            }
        }
        C1144Kv c1144Kv = this.e;
        Object objA2 = c1144Kv.a(obj, str);
        if (objA2 != null) {
            if (((AbstractC2188hw) c1144Kv).b == null && ((AbstractC2188hw) c1144Kv).c == null) {
                ((AbstractC2188hw) c1144Kv).a = str;
                ((AbstractC2188hw) c1144Kv).b = objA2;
                return;
            } else {
                c1144Kv.a(str);
                throw null;
            }
        }
        C1801cw c1801cw = this.f;
        C1956ew c1956ew = c1801cw.c;
        c1956ew.getClass();
        if (str.equals("methodName") && (obj instanceof String)) {
            String str2 = (String) obj;
            if ("".equals(str2)) {
                return;
            }
            if (c1956ew.c == null) {
                c1956ew.c = C1463Vw.k();
            }
            c1956ew.c.a(AbstractC1309Qw.a(str2));
            return;
        }
        if (str.equals("methodReturnType") && (obj instanceof String)) {
            String str3 = (String) obj;
            if ("".equals(str3)) {
                return;
            }
            if (c1956ew.c == null) {
                c1956ew.c = C1463Vw.k();
            }
            C1463Vw.a aVar = c1956ew.c;
            if (PsiKeyword.VOID.equals(str3)) {
                abstractC1545YwA = AbstractC1545Yw.e();
            } else {
                if (str3.equals("<any>")) {
                    abstractC3024sxA2 = AbstractC3024sx.a();
                } else {
                    abstractC3024sxA2 = AbstractC3024sx.a(AbstractC2497lw.a(str3));
                }
                abstractC1545YwA = AbstractC1545Yw.a(abstractC3024sxA2);
            }
            aVar.a(abstractC1545YwA);
            return;
        }
        C1197Mv c1197Mv = c1801cw.d;
        c1197Mv.getClass();
        if (str.equals("fieldName") && (obj instanceof String)) {
            String str4 = (String) obj;
            if ("".equals(str4)) {
                return;
            }
            if (c1197Mv.c == null) {
                c1197Mv.c = C3319ww.k();
            }
            c1197Mv.c.a(AbstractC3244vw.a(str4));
            return;
        }
        if (str.equals("fieldType") && (obj instanceof String)) {
            String str5 = (String) obj;
            if ("".equals(str5)) {
                return;
            }
            if (c1197Mv.c == null) {
                c1197Mv.c = C3319ww.k();
            }
            C3319ww.a aVar2 = c1197Mv.c;
            if (str5.equals("<any>")) {
                abstractC3024sxA = AbstractC3024sx.a();
            } else {
                abstractC3024sxA = AbstractC3024sx.a(AbstractC2497lw.a(str5));
            }
            aVar2.a(AbstractC3466yw.a(abstractC3024sxA));
            return;
        }
        super.a(str, obj);
        throw null;
    }

    @Override // com.android.tools.r8.internal.AbstractC1057Hv, com.android.tools.r8.internal.AbstractC2509m2
    public AbstractC2509m2 a(String str) {
        AbstractC2509m2 abstractC2509m2A = this.f.a(new Consumer() { // from class: com.android.tools.r8.internal.Xv$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractC1517Xv.a((AbstractC1171Lw) obj);
            }
        }, str);
        if (abstractC2509m2A != null) {
            return abstractC2509m2A;
        }
        super.a(str);
        throw null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public void a() {
        if (this.b != null) {
            C1087Iv c1087Iv = this.d;
            Object objB = ((AbstractC2188hw) c1087Iv).b;
            if (objB == null) {
                objB = c1087Iv.b();
            }
            this.d.getClass();
            if (((AbstractC2496lv) objB).equals(AbstractC2496lv.a(C2424kx.a())) && this.f.b().i()) {
                C1144Kv c1144Kv = this.e;
                Object objB2 = ((AbstractC2188hw) c1144Kv).b;
                if (objB2 == null) {
                    objB2 = c1144Kv.b();
                }
                if (((AbstractC2884qw) objB2).d() && this.c == null) {
                    this.g = AbstractC0940Dw.a(this.b);
                    return;
                }
            }
            throw new C2883qv("Cannot define an item explicitly and via a member-binding reference");
        }
        AbstractC1171Lw abstractC1171LwB = this.f.b();
        if (this.c == null) {
            this.c = abstractC1171LwB.i() ? EnumC3537zw.a : EnumC3537zw.b;
        }
        if (!this.c.equals(EnumC3537zw.a) && abstractC1171LwB.i()) {
            abstractC1171LwB = AbstractC1171Lw.a();
        }
        C0846Aw.a aVarA = C0846Aw.b().a(this.c);
        C1087Iv c1087Iv2 = this.d;
        Object objB3 = ((AbstractC2188hw) c1087Iv2).b;
        if (objB3 == null) {
            objB3 = c1087Iv2.b();
        }
        C0846Aw.a aVarA2 = aVarA.a((AbstractC2496lv) objB3);
        C1144Kv c1144Kv2 = this.e;
        Object objB4 = ((AbstractC2188hw) c1144Kv2).b;
        if (objB4 == null) {
            objB4 = c1144Kv2.b();
        }
        this.g = AbstractC0940Dw.a(aVarA2.a((AbstractC2884qw) objB4).a(abstractC1171LwB).a());
    }
}
