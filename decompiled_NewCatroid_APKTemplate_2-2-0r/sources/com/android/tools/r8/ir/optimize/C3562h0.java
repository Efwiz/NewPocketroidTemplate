package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.internal.AbstractC2251ih;
import com.android.tools.r8.internal.C2173hh;
import com.android.tools.r8.utils.O3;
import java.util.HashSet;
import java.util.function.BiFunction;

/* JADX INFO: renamed from: com.android.tools.r8.ir.optimize.h0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3562h0 {
    static final /* synthetic */ boolean g = true;
    private final AbstractC0727q0 a;
    private final AbstractC2251ih b;
    private C3562h0 c;
    private final HashSet d = new HashSet();
    private final HashSet e = new HashSet();
    private final HashSet f = new HashSet();

    C3562h0(AbstractC2251ih abstractC2251ih, AbstractC0727q0 abstractC0727q0) {
        this.b = abstractC2251ih;
        this.a = abstractC0727q0;
    }

    final synchronized void a(C3562h0 c3562h0) {
        boolean zAdd = this.d.add(c3562h0);
        if (!g && !zAdd) {
            throw new AssertionError();
        }
    }

    final synchronized void b(C3562h0 c3562h0) {
        boolean zAdd = this.e.add(c3562h0);
        if (!g && !zAdd) {
            throw new AssertionError();
        }
    }

    final synchronized void c(C3562h0 c3562h0) {
        if (!g && this.c != null) {
            throw new AssertionError();
        }
        this.c = c3562h0;
    }

    public final void a(C0684j2 c0684j2) {
        b(this.b.c(c0684j2));
    }

    public final synchronized void b(C2173hh c2173hh) {
        boolean zAdd = this.f.add(c2173hh);
        if (!g && !zAdd) {
            throw new AssertionError();
        }
    }

    public final boolean a(C2173hh c2173hh) {
        Boolean bool = Boolean.FALSE;
        Boolean bool2 = Boolean.TRUE;
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.ir.optimize.h0$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return C3562h0.a((AbstractC0727q0) obj, (Boolean) obj2);
            }
        };
        O3 o3H = O3.h(this);
        Object objA = bool;
        while (o3H.c()) {
            C3562h0 c3562h0 = (C3562h0) o3H.f();
            if (c3562h0.f.contains(c2173hh)) {
                objA = biFunction.apply(c3562h0.a, objA);
            }
            if (objA == bool2) {
                break;
            }
            C3562h0 c3562h1 = c3562h0.c;
            if (c3562h1 != null) {
                o3H.c(c3562h1);
            }
            o3H.a((Iterable) c3562h0.d);
        }
        if (objA != bool2) {
            objA = a(c2173hh, bool, bool2, biFunction);
        }
        return ((Boolean) objA).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean a(AbstractC0727q0 abstractC0727q0, Boolean bool) {
        return Boolean.TRUE;
    }

    public final Object a(C2173hh c2173hh, Boolean bool, Boolean bool2, BiFunction biFunction) {
        Object objApply;
        O3 o3D = O3.d((Iterable) this.e);
        Object obj = bool;
        while (o3D.c()) {
            C3562h0 c3562h0 = (C3562h0) o3D.f();
            if (c3562h0.f.contains(c2173hh)) {
                objApply = obj;
                objApply = biFunction.apply(c3562h0.a, obj);
            }
            if (objApply == bool2) {
                return objApply;
            }
            o3D.a((Iterable) c3562h0.d);
            o3D.a((Iterable) c3562h0.e);
            obj = objApply;
        }
        return obj;
    }
}
