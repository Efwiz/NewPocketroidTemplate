package com.android.tools.r8.internal;

import com.android.tools.r8.naming.MapVersion;
import com.android.tools.r8.naming.MappingComposeException;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.jU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2308jU extends com.android.tools.r8.naming.mappinginformation.c {
    public static final MapVersion c = MapVersion.MAP_VERSION_2_0;
    private final List a;
    private final List b;

    private C2308jU(List list, List list2) {
        this.a = list;
        this.b = list2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1222Nt c1222Nt, AbstractC2004fU abstractC2004fU) {
        c1222Nt.a(abstractC2004fU.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void b(C1507Xl c1507Xl, AbstractC1249Ot abstractC1249Ot) {
        c1507Xl.a(AbstractC2158hU.a(abstractC1249Ot));
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final C2308jU i() {
        return this;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final String p() {
        C1333Rt c1333Rt = new C1333Rt();
        c1333Rt.a("id", new C1434Ut("com.android.tools.r8.rewriteFrame"));
        final C1222Nt c1222Nt = new C1222Nt();
        this.a.forEach(new Consumer() { // from class: com.android.tools.r8.internal.jU$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2308jU.a(c1222Nt, (AbstractC2004fU) obj);
            }
        });
        c1333Rt.a("conditions", c1222Nt);
        final C1222Nt c1222Nt2 = new C1222Nt();
        this.b.forEach(new Consumer() { // from class: com.android.tools.r8.internal.jU$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2308jU.a(c1222Nt2, (AbstractC2158hU) obj);
            }
        });
        c1333Rt.a("actions", c1222Nt2);
        return c1333Rt.toString();
    }

    public final List q() {
        return this.b;
    }

    public final List r() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1222Nt c1222Nt, AbstractC2158hU abstractC2158hU) {
        c1222Nt.a(abstractC2158hU.a());
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final com.android.tools.r8.naming.mappinginformation.e b(com.android.tools.r8.naming.mappinginformation.e eVar) throws MappingComposeException {
        throw new MappingComposeException("Unable to compose com.android.tools.r8.rewriteFrame");
    }

    public static void a(MapVersion mapVersion, C1333Rt c1333Rt, Consumer consumer) {
        if (mapVersion.a(c)) {
            final C1507Xl c1507XlG = AbstractC1639am.g();
            c1333Rt.a("conditions").c().forEach(new Consumer() { // from class: com.android.tools.r8.internal.jU$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2308jU.a(c1507XlG, (AbstractC1249Ot) obj);
                }
            });
            final C1507Xl c1507XlG2 = AbstractC1639am.g();
            c1333Rt.a("actions").c().forEach(new Consumer() { // from class: com.android.tools.r8.internal.jU$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2308jU.b(c1507XlG2, (AbstractC1249Ot) obj);
                }
            });
            consumer.accept(new C2308jU(c1507XlG.a(), c1507XlG2.a()));
        }
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final boolean a(com.android.tools.r8.naming.mappinginformation.e eVar) {
        eVar.getClass();
        return !(eVar instanceof C2308jU);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1507Xl c1507Xl, AbstractC1249Ot abstractC1249Ot) {
        c1507Xl.a(AbstractC2004fU.a(abstractC1249Ot));
    }
}
