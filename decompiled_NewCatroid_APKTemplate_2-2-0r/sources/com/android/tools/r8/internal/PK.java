package com.android.tools.r8.internal;

import com.android.tools.r8.naming.MapVersion;
import com.android.tools.r8.naming.MappingComposeException;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.utils.C3889e2;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class PK extends com.android.tools.r8.naming.mappinginformation.c {
    public static final MapVersion c = MapVersion.MAP_VERSION_2_0;
    public static final MapVersion d = MapVersion.MAP_VERSION_2_1;
    private AbstractC3401y a;
    private MethodReference b;

    private PK(C0964Eo c0964Eo, MethodReference methodReference) {
        this.a = c0964Eo;
        this.b = methodReference;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1333Rt c1333Rt, Integer num, Integer num2) {
        c1333Rt.a(new StringBuilder().append(num).toString(), new C1434Ut((Number) num2));
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final com.android.tools.r8.naming.mappinginformation.e b(com.android.tools.r8.naming.mappinginformation.e eVar) throws MappingComposeException {
        throw new MappingComposeException("Unable to compose com.android.tools.r8.outlineCallsite");
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final PK c() {
        return this;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final String p() {
        C1333Rt c1333Rt = new C1333Rt();
        c1333Rt.a("id", new C1434Ut("com.android.tools.r8.outlineCallsite"));
        final C1333Rt c1333Rt2 = new C1333Rt();
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.PK$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                PK.a(c1333Rt2, (Integer) obj, (Integer) obj2);
            }
        });
        c1333Rt.a("positions", c1333Rt2);
        if (this.b != null) {
            c1333Rt.a("outline", new C1434Ut(this.b.toString()));
        }
        return c1333Rt.toString();
    }

    public final MethodReference q() {
        return this.b;
    }

    public final AbstractC3401y r() {
        return this.a;
    }

    @Override // com.android.tools.r8.naming.mappinginformation.e
    public final boolean a(com.android.tools.r8.naming.mappinginformation.e eVar) {
        eVar.getClass();
        return !(eVar instanceof PK);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int a(int i) {
        return ((Integer) this.a.getOrDefault(Integer.valueOf(i), Integer.valueOf(i))).intValue();
    }

    public static PK a(C0964Eo c0964Eo, MethodReference methodReference) {
        return new PK(c0964Eo, methodReference);
    }

    public static void a(MapVersion mapVersion, C1333Rt c1333Rt, Consumer consumer) {
        MethodReference methodReferenceA;
        if (mapVersion.a(c)) {
            C1333Rt c1333RtG = c1333Rt.g();
            if (c1333RtG != null) {
                final C0964Eo c0964Eo = new C0964Eo();
                c1333RtG.entrySet().forEach(new Consumer() { // from class: com.android.tools.r8.internal.PK$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        PK.a(c0964Eo, (Map.Entry) obj);
                    }
                });
                AbstractC1249Ot abstractC1249OtA = c1333Rt.a("outline");
                if (abstractC1249OtA != null) {
                    methodReferenceA = C3889e2.a(abstractC1249OtA.f());
                } else {
                    if (mapVersion.a(d)) {
                        throw new C1367Tb("Expected 'outline' to be present: " + c1333Rt);
                    }
                    methodReferenceA = null;
                }
                consumer.accept(new PK(c0964Eo, methodReferenceA));
                return;
            }
            throw new C1367Tb("Expected 'positions' to be present: " + c1333Rt);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AbstractC3401y abstractC3401y, Map.Entry entry) {
        try {
            ((C0964Eo) abstractC3401y).b(Integer.parseInt((String) entry.getKey()), ((AbstractC1249Ot) entry.getValue()).b());
        } catch (Throwable unused) {
            throw new C1367Tb("Invalid position entry: " + entry.toString());
        }
    }

    public final void a(MethodReference methodReference) {
        this.b = methodReference;
    }

    public final void a(C0964Eo c0964Eo) {
        this.a = c0964Eo;
    }
}
