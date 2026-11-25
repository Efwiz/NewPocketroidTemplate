package com.android.tools.r8.graph;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class D3 implements G3 {
    static final /* synthetic */ boolean c = true;
    private final InterfaceC0694k5 a;
    private final BiConsumer b;

    D3(InterfaceC0694k5 interfaceC0694k5, BiConsumer biConsumer) {
        this.a = interfaceC0694k5;
        this.b = biConsumer;
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.e a(C0706m3.e eVar) {
        return eVar.a() ? eVar : b(eVar);
    }

    public final C0706m3.e b(C0706m3.e eVar) {
        if (eVar.a() || eVar.n() || eVar.o()) {
            return eVar;
        }
        if (eVar.l()) {
            eVar.g().a(this);
            return eVar;
        }
        if (c || eVar.m()) {
            return eVar.h().a(this);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.graph.G3
    public final List c(List list) {
        list.forEach(new Consumer() { // from class: com.android.tools.r8.graph.D3$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0706m3.f) obj);
            }
        });
        return list;
    }

    @Override // com.android.tools.r8.graph.G3
    public final List d(List list) {
        list.forEach(new D3$$ExternalSyntheticLambda0(this));
        return list;
    }

    @Override // com.android.tools.r8.graph.G3
    public final List e(List list) {
        if (list == null) {
            return null;
        }
        list.forEach(new Consumer() { // from class: com.android.tools.r8.graph.D3$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((C0706m3.c) obj);
            }
        });
        return list;
    }

    public final C0706m3.e c(C0706m3.e eVar) {
        return b(eVar);
    }

    @Override // com.android.tools.r8.graph.G3
    public final List a(List list) {
        list.forEach(new Consumer() { // from class: com.android.tools.r8.graph.D3$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c((C0706m3.e) obj);
            }
        });
        return list;
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.c a(C0706m3.c cVar) {
        return cVar.a(this);
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.i a(C0706m3.i iVar) {
        iVar.getClass();
        if (iVar instanceof C0712n3) {
            return iVar;
        }
        if (c || iVar.e()) {
            return b(iVar.d());
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.c a(C0706m3.c cVar, C0706m3.c cVar2) {
        return cVar.a(this);
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0706m3.h a(C0706m3.h hVar) {
        if (hVar.a()) {
            return hVar;
        }
        a(hVar.a);
        return hVar;
    }

    @Override // com.android.tools.r8.graph.G3
    public final List a(C0767v2 c0767v2, C0767v2 c0767v3, List list) {
        list.forEach(new Consumer() { // from class: com.android.tools.r8.graph.D3$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((C0706m3.e) obj);
            }
        });
        return list;
    }

    public final C0706m3.c b(C0706m3.c cVar) {
        return cVar.a(this);
    }

    public final C0706m3.f a(C0706m3.f fVar) {
        return fVar.a(this);
    }

    @Override // com.android.tools.r8.graph.G3
    public final List b(List list) {
        list.forEach(new D3$$ExternalSyntheticLambda0(this));
        return list;
    }

    @Override // com.android.tools.r8.graph.G3
    public final C0767v2 a(C0767v2 c0767v2) {
        this.b.accept(c0767v2, this.a);
        return c0767v2;
    }
}
