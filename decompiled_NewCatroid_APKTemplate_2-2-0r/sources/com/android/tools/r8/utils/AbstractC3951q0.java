package com.android.tools.r8.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.utils.q0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3951q0 extends AbstractC3955r0 {
    static final /* synthetic */ boolean e = true;
    private final IdentityHashMap d = new IdentityHashMap();

    /* JADX INFO: Access modifiers changed from: private */
    public Object c(Object obj) {
        return ((C3941o0) ((C3931m0) ((AbstractC3955r0) this).b.get(obj))).d;
    }

    protected abstract C3 a(InterfaceC3936n0 interfaceC3936n0, List list);

    protected abstract D3 a(InterfaceC3936n0 interfaceC3936n0, Function function);

    @Override // com.android.tools.r8.utils.AbstractC3955r0
    final C3931m0 a(Object obj) {
        return new C3941o0(obj);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.utils.AbstractC3955r0
    public final D3 b(C3941o0 c3941o0) {
        final ArrayList arrayList = new ArrayList();
        List list = (List) this.d.put(c3941o0, arrayList);
        if (e || list == null) {
            return a(c3941o0, new Function() { // from class: com.android.tools.r8.utils.q0$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a(arrayList, obj);
                }
            });
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ InterfaceC3936n0 a(List list, Object obj) {
        C3941o0 c3941o0 = (C3941o0) b(obj);
        list.add(c3941o0);
        return c3941o0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List c(C3941o0 c3941o0) {
        if (e) {
            return new ArrayList();
        }
        throw new AssertionError("Unexpected joining of not visited node");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.tools.r8.utils.AbstractC3955r0
    public final D3 a(C3941o0 c3941o0) {
        return a(c3941o0, (List) this.d.computeIfAbsent(c3941o0, new Function() { // from class: com.android.tools.r8.utils.q0$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC3951q0.c((C3941o0) obj);
            }
        }));
    }

    @Override // com.android.tools.r8.utils.AbstractC3955r0
    public final List a(Collection collection) {
        return V1.a(collection, new Function() { // from class: com.android.tools.r8.utils.q0$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.c(obj);
            }
        });
    }
}
