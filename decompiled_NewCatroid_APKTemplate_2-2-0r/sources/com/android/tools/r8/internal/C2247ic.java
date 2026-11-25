package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0702m;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ic, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2247ic extends AbstractC1777cc implements InterfaceC2319jc, InterfaceC2397kc {
    static final /* synthetic */ boolean c = true;
    private final Map b;

    private C2247ic(HashMap map) {
        this.b = map;
        boolean z = c;
        if (!z && k()) {
            throw new AssertionError();
        }
        if (!z && l()) {
            throw new AssertionError();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static InterfaceC2397kc a(C1207Nf c1207Nf, InterfaceC2013fc interfaceC2013fc) {
        return (c1207Nf.m() && (((ZE) interfaceC2013fc) instanceof C2508m10)) ? C2508m10.k() : new C2247ic(c1207Nf, interfaceC2013fc);
    }

    /* JADX WARN: Code duplicated, block: B:37:0x008f  */
    public final ZE b(C0702m c0702m, AbstractC0717o2 abstractC0717o2, FW fw, Function function) {
        InterfaceC2013fc interfaceC2013fcK;
        boolean z = c;
        if (!z && this.b.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && l()) {
            throw new AssertionError();
        }
        HashMap map = new HashMap();
        for (Map.Entry entry : this.b.entrySet()) {
            C1207Nf c1207Nf = (C1207Nf) function.apply((C1207Nf) entry.getKey());
            if (c1207Nf != null) {
                YE ye = (InterfaceC2013fc) map.get(c1207Nf);
                YE ye2 = (InterfaceC2013fc) entry.getValue();
                if (ye == null) {
                    interfaceC2013fcK = (InterfaceC2013fc) fw.a(ye2);
                } else {
                    ZE ze = (ZE) ye;
                    if (ze instanceof C2508m10) {
                        interfaceC2013fcK = C2508m10.k();
                    } else {
                        ZE ze2 = (ZE) ye2;
                        ze2.getClass();
                        if (ze2 instanceof C2508m10) {
                            interfaceC2013fcK = C2508m10.k();
                        } else {
                            if (!c && !(ze instanceof C1853dc)) {
                                throw new AssertionError();
                            }
                            interfaceC2013fcK = ye.b().a(c0702m, abstractC0717o2, ye2.b(), fw);
                        }
                    }
                }
                if (c1207Nf.m()) {
                    ZE ze3 = (ZE) interfaceC2013fcK;
                    ze3.getClass();
                    if (ze3 instanceof C2508m10) {
                        return C2508m10.k();
                    }
                }
                map.put(c1207Nf, interfaceC2013fcK);
            }
        }
        return map.isEmpty() ? C3480z5.k() : new C2247ic(map);
    }

    @Override // com.android.tools.r8.internal.ZE, com.android.tools.r8.internal.YE
    public final InterfaceC2319jc i() {
        return this;
    }

    @Override // com.android.tools.r8.internal.ZE, com.android.tools.r8.internal.YE
    public final C2247ic j() {
        return this;
    }

    public final boolean k() {
        return this.b.isEmpty();
    }

    public final boolean l() {
        return a(AbstractC1154Lf.n()).isUnknown();
    }

    public final Collection m() {
        return this.b.values();
    }

    private C2247ic(C1207Nf c1207Nf, InterfaceC2013fc interfaceC2013fc) {
        HashMap map = new HashMap(1);
        this.b = map;
        map.put(c1207Nf, interfaceC2013fc);
        if (!c && l()) {
            throw new AssertionError();
        }
    }

    public final void a(BiConsumer biConsumer) {
        this.b.forEach(biConsumer);
    }

    public final YE a(C1207Nf c1207Nf) {
        InterfaceC2013fc interfaceC2013fc = (InterfaceC2013fc) this.b.get(c1207Nf);
        return interfaceC2013fc != null ? interfaceC2013fc : C3480z5.k();
    }

    @Override // com.android.tools.r8.internal.YE
    public final YE a() {
        boolean z = c;
        if (!z && this.b.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && l()) {
            throw new AssertionError();
        }
        final HashMap map = new HashMap();
        this.b.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.ic$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2247ic.a(map, (C1207Nf) obj, (InterfaceC2013fc) obj2);
            }
        });
        return new C2247ic(map);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, C1207Nf c1207Nf, InterfaceC2013fc interfaceC2013fc) {
        map.put(c1207Nf, interfaceC2013fc.a());
    }

    /* JADX WARN: Code duplicated, block: B:70:0x00e7  */
    public final ZE a(C0702m c0702m, AbstractC0717o2 abstractC0717o2, C2247ic c2247ic, FW fw) {
        InterfaceC2013fc interfaceC2013fcK;
        ZE zeK;
        boolean z = c;
        if (!z && this.b.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && l()) {
            throw new AssertionError();
        }
        if (!z && c2247ic.b.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && c2247ic.l()) {
            throw new AssertionError();
        }
        for (Map.Entry entry : c2247ic.b.entrySet()) {
            C1207Nf c1207Nf = (C1207Nf) entry.getKey();
            YE ye = (InterfaceC2013fc) entry.getValue();
            boolean z2 = c;
            if (!z2 && this.b.isEmpty()) {
                throw new AssertionError();
            }
            if (!z2 && l()) {
                throw new AssertionError();
            }
            ZE ze = (ZE) ye;
            ze.getClass();
            boolean z3 = ze instanceof C2508m10;
            if (z3) {
                if (c1207Nf.m()) {
                    zeK = C2508m10.k();
                } else {
                    this.b.put(c1207Nf, ye);
                    zeK = this;
                }
            } else {
                if (!z2 && !(ze instanceof C1853dc)) {
                    throw new AssertionError();
                }
                YE ye2 = (InterfaceC2013fc) this.b.get(c1207Nf);
                if (ye2 == null) {
                    interfaceC2013fcK = (InterfaceC2013fc) fw.a(ye);
                } else {
                    ZE ze2 = (ZE) ye2;
                    if (!(ze2 instanceof C2508m10)) {
                        ze.getClass();
                        if (!z3) {
                            if (!z2 && !(ze2 instanceof C1853dc)) {
                                throw new AssertionError();
                            }
                            interfaceC2013fcK = ye2.b().a(c0702m, abstractC0717o2, ye.b(), fw);
                        } else {
                            interfaceC2013fcK = C2508m10.k();
                        }
                    } else {
                        interfaceC2013fcK = C2508m10.k();
                    }
                }
                if (c1207Nf.m()) {
                    ZE ze3 = (ZE) interfaceC2013fcK;
                    ze3.getClass();
                    if (ze3 instanceof C2508m10) {
                        zeK = C2508m10.k();
                    }
                }
                this.b.put(c1207Nf, interfaceC2013fcK);
                zeK = this;
            }
            if (zeK instanceof C2508m10) {
                return zeK;
            }
            if (!z2 && zeK != this) {
                throw new AssertionError();
            }
        }
        if (c || !l()) {
            return this;
        }
        throw new AssertionError();
    }
}
