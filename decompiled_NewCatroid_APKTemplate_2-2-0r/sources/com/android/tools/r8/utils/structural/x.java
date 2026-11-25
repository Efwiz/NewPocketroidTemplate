package com.android.tools.r8.utils.structural;

import com.android.tools.r8.utils.structural.x;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public interface x<T extends x<T>> extends s<T> {
    T U();

    @Override // com.android.tools.r8.utils.structural.s, java.lang.Comparable
    /* JADX INFO: renamed from: a */
    default int compareTo(T t) {
        return h.a(U(), t, new a$$ExternalSyntheticLambda0());
    }

    y<T> p();

    default int a(T t, t tVar) {
        x xVarU = U();
        a$$ExternalSyntheticLambda0 a__externalsyntheticlambda0 = new a$$ExternalSyntheticLambda0();
        if (xVarU == t) {
            return 0;
        }
        return a__externalsyntheticlambda0.a(xVarU, t, new f(tVar));
    }

    default int a(T t, a aVar) {
        return aVar.a((T) U(), t, U().p());
    }

    default void a(n nVar) {
        i.a(U(), nVar, new o$$ExternalSyntheticLambda0());
    }

    default void a(m mVar, t tVar) {
        q.a(U(), mVar, tVar, new o$$ExternalSyntheticLambda0());
    }

    default void a(o oVar) {
        oVar.a(U(), U().p());
    }
}
