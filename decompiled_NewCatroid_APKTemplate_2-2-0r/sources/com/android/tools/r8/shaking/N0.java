package com.android.tools.r8.shaking;

import com.android.tools.r8.internal.AbstractC0968Es;
import com.android.tools.r8.internal.AbstractC2251ih;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class N0 extends AbstractC2251ih {
    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final boolean a(Object obj, Object obj2) {
        C3712e3 c3712e3 = (C3712e3) obj;
        C3712e3 c3712e4 = (C3712e3) obj2;
        return c3712e3.b().equals(c3712e4.b()) && c3712e3.a().equals(c3712e4.a()) && c3712e3.k().equals(c3712e4.k()) && c3712e3.d() == c3712e4.d() && c3712e3.e() == c3712e4.e() && c3712e3.h() == c3712e4.h() && c3712e3.f().equals(c3712e4.f()) && Objects.equals(c3712e3.g(), c3712e4.g()) && c3712e3.c().equals(c3712e4.c());
    }

    @Override // com.android.tools.r8.internal.AbstractC2251ih
    protected final int a(Object obj) {
        int iA;
        C3712e3 c3712e3 = (C3712e3) obj;
        int iHashCode = ((c3712e3.i() ? 1 : 0) + ((((c3712e3.f().hashCode() + ((c3712e3.c().hashCode() + (((((c3712e3.e() ? 1 : 0) + ((c3712e3.k().hashCode() + ((c3712e3.a().hashCode() + (c3712e3.b().hashCode() * 3)) * 3)) * 3)) * 3) + (c3712e3.d() != null ? c3712e3.d().hashCode() : 0)) * 3)) * 3)) * 3) + (c3712e3.g() != null ? c3712e3.g().hashCode() : 0)) * 3)) * 3;
        Iterable<O3> iterableX = c3712e3.u.x();
        if (iterableX instanceof Collection) {
            iA = ((Collection) iterableX).size();
        } else {
            Iterator<O3> it = iterableX.iterator();
            long j = 0;
            while (it.hasNext()) {
                it.next();
                j++;
            }
            iA = AbstractC0968Es.a(j);
        }
        return iA + iHashCode;
    }
}
