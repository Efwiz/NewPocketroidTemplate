package com.android.tools.r8;

import com.android.tools.r8.dex.code.A3;
import com.android.tools.r8.dex.code.AbstractC0411a1;
import com.android.tools.r8.dex.code.B1;
import com.android.tools.r8.dex.code.B3;
import com.android.tools.r8.dex.code.C0430e0;
import com.android.tools.r8.dex.code.C0435f0;
import com.android.tools.r8.dex.code.C0512u3;
import com.android.tools.r8.dex.code.C0517v3;
import com.android.tools.r8.dex.code.C0522w3;
import com.android.tools.r8.dex.code.C0524x0;
import com.android.tools.r8.dex.code.C0527x3;
import com.android.tools.r8.dex.code.C0529y0;
import com.android.tools.r8.dex.code.C0535y3;
import com.android.tools.r8.dex.code.O2;
import com.android.tools.r8.dex.code.Z0;
import com.android.tools.r8.graph.B2;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0649f0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.T0;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.internal.AbstractC1699bX;
import com.android.tools.r8.internal.B20;
import com.android.tools.r8.internal.InterfaceC2541mV;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class b0 {
    static final /* synthetic */ boolean c = true;
    private final C0729q2 a;
    private final ResourceShrinker.ReferenceChecker b;

    b0(C0729q2 c0729q2, ResourceShrinker.ReferenceChecker referenceChecker) {
        this.a = c0729q2;
        this.b = referenceChecker;
    }

    /* JADX WARN: Code duplicated, block: B:191:0x02f4  */
    /* JADX WARN: Multi-variable type inference failed */
    public final void a() {
        int iA;
        Y0 field;
        String string;
        this.b.startClassVisit(this.a.Z0());
        if (this.b.shouldProcess(this.a.d.E0())) {
            Iterator<T0> it = this.a.N1().iterator();
            while (it.hasNext()) {
                B2 b2Y0 = it.next().Y0();
                if (b2Y0 != null) {
                    int iOrdinal = b2Y0.N0().ordinal();
                    if (iOrdinal == 3) {
                        this.b.referencedInt(b2Y0.D0().k1());
                    } else if (iOrdinal == 9) {
                        this.b.referencedString(((C0759u2) b2Y0.K0().b).toString());
                    } else if (iOrdinal == 14) {
                        for (B2 b2 : b2Y0.v0().j1()) {
                            if (b2.X0()) {
                                this.b.referencedInt(b2.D0().k1());
                            }
                        }
                    }
                }
            }
            for (W0 w0 : this.a.I0()) {
                this.b.startMethodVisit(w0.getReference().E0());
                com.android.tools.r8.graph.W wA1 = w0.a1();
                if (wA1 != null) {
                    HashSet hashSet = new HashSet();
                    ArrayList<C0529y0> arrayList = new ArrayList();
                    B1[] b1Arr = wA1.P().i;
                    for (int i = 0; i < b1Arr.length; i++) {
                        com.android.tools.r8.dex.code.C c2 = b1Arr[i];
                        int iV = c2.v();
                        boolean z = true;
                        if (iV == 18 || iV == 19 || iV == 20 || iV == 23 || iV == 21 || iV == 22) {
                            if (!c) {
                                int iV2 = c2.v();
                                if (iV2 != 18 && iV2 != 19 && iV2 != 20 && iV2 != 23 && iV2 != 21 && iV2 != 22) {
                                    z = false;
                                }
                                if (!z) {
                                    throw new AssertionError();
                                }
                            }
                            if (c2 instanceof InterfaceC2541mV) {
                                iA = ((InterfaceC2541mV) c2).a();
                            } else {
                                if (!(c2 instanceof B20)) {
                                    throw new AssertionError("Not an int const instruction.");
                                }
                                B20 b20 = (B20) c2;
                                if (((int) b20.a()) == b20.a()) {
                                    iA = (int) b20.a();
                                }
                            }
                            this.b.referencedInt(iA);
                        } else {
                            int iV3 = c2.v();
                            if (iV3 == 26 || iV3 == 27) {
                                if (!c) {
                                    int iV4 = c2.v();
                                    if (iV4 != 26 && iV4 != 27) {
                                        z = false;
                                    }
                                    if (!z) {
                                        throw new AssertionError();
                                    }
                                }
                                if (c2 instanceof C0430e0) {
                                    string = ((C0430e0) c2).N().toString();
                                } else {
                                    if (!(c2 instanceof C0435f0)) {
                                        throw new AssertionError("Not a string constant instruction.");
                                    }
                                    string = ((C0435f0) c2).N().toString();
                                }
                                this.b.referencedString(string);
                            } else {
                                int iV5 = c2.v();
                                if (iV5 == 96 || iV5 == 99 || iV5 == 100 || iV5 == 101 || iV5 == 98 || iV5 == 102 || iV5 == 97) {
                                    if (!c) {
                                        int iV6 = c2.v();
                                        if (iV6 != 96 && iV6 != 99 && iV6 != 100 && iV6 != 101 && iV6 != 98 && iV6 != 102 && iV6 != 97) {
                                            z = false;
                                        }
                                        if (!z) {
                                            throw new AssertionError();
                                        }
                                    }
                                    if (c2 instanceof C0512u3) {
                                        field = ((C0512u3) c2).getField();
                                    } else if (c2 instanceof C0517v3) {
                                        field = ((C0517v3) c2).getField();
                                    } else if (c2 instanceof C0522w3) {
                                        field = ((C0522w3) c2).getField();
                                    } else if (c2 instanceof C0527x3) {
                                        field = ((C0527x3) c2).getField();
                                    } else if (c2 instanceof C0535y3) {
                                        field = ((C0535y3) c2).getField();
                                    } else if (c2 instanceof A3) {
                                        field = ((A3) c2).getField();
                                    } else {
                                        if (!(c2 instanceof B3)) {
                                            throw new AssertionError("Not a get static instruction");
                                        }
                                        field = ((B3) c2).getField();
                                    }
                                    this.b.referencedStaticField(field.e.E0(), field.f.toString());
                                } else {
                                    int iV7 = c2.v();
                                    if (iV7 == 110 || iV7 == 111 || iV7 == 112 || iV7 == 113 || iV7 == 114) {
                                        if (!c) {
                                            int iV8 = c2.v();
                                            if (iV8 != 110 && iV8 != 111 && iV8 != 112 && iV8 != 113 && iV8 != 114) {
                                                z = false;
                                            }
                                            if (!z) {
                                                throw new AssertionError();
                                            }
                                        }
                                        C0684j2 c0684j2 = (C0684j2) ((Z0) c2).k;
                                        ResourceShrinker.ReferenceChecker referenceChecker = this.b;
                                        String strE0 = c0684j2.e.E0();
                                        String string2 = c0684j2.f.toString();
                                        C0737r2 c0737r2 = c0684j2.h;
                                        c0737r2.getClass();
                                        referenceChecker.referencedMethod(strE0, string2, c0737r2.a(AbstractC3649o0.a()));
                                    } else {
                                        int iV9 = c2.v();
                                        if (iV9 == 116 || iV9 == 117 || iV9 == 118 || iV9 == 119 || iV9 == 120) {
                                            if (!c) {
                                                int iV10 = c2.v();
                                                if (iV10 != 116 && iV10 != 117 && iV10 != 118 && iV10 != 119 && iV10 != 120) {
                                                    z = false;
                                                }
                                                if (!z) {
                                                    throw new AssertionError();
                                                }
                                            }
                                            C0684j2 c0684j3 = (C0684j2) ((AbstractC0411a1) c2).g;
                                            ResourceShrinker.ReferenceChecker referenceChecker2 = this.b;
                                            String strE1 = c0684j3.e.E0();
                                            String string3 = c0684j3.f.toString();
                                            C0737r2 c0737r3 = c0684j3.h;
                                            c0737r3.getClass();
                                            referenceChecker2.referencedMethod(strE1, string3, c0737r3.a(AbstractC3649o0.a()));
                                        } else if (c2 instanceof C0524x0) {
                                            C0524x0 c0524x0 = (C0524x0) b1Arr[i];
                                            if (i > 0) {
                                                Object[] objArr = b1Arr[i - 1];
                                                if (!(objArr instanceof O2) || Objects.equals(((C0767v2) ((O2) objArr).g).e.toString(), "[I")) {
                                                    hashSet.add(Integer.valueOf(c0524x0.u() + c0524x0.x()));
                                                }
                                            } else {
                                                hashSet.add(Integer.valueOf(c0524x0.u() + c0524x0.x()));
                                            }
                                        } else if (c2 instanceof C0529y0) {
                                            arrayList.add((C0529y0) c2);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    for (C0529y0 c0529y0 : arrayList) {
                        if (!(c0529y0 instanceof C0529y0) ? false : hashSet.contains(Integer.valueOf(c0529y0.u()))) {
                            int i2 = 0;
                            while (true) {
                                short[] sArr = c0529y0.g;
                                if (i2 < sArr.length / 2) {
                                    int i3 = i2 * 2;
                                    this.b.referencedInt(sArr[i3] | (sArr[i3 + 1] << 16));
                                    i2++;
                                }
                            }
                        }
                    }
                }
                this.b.endMethodVisit(w0.getReference().E0());
            }
            if (this.a.a2()) {
                a(this.a);
            }
            this.b.endClassVisit(this.a.Z0());
        }
    }

    private void a(C0729q2 c0729q2) {
        AbstractC1699bX.a(Arrays.stream(c0729q2.s0().c), AbstractC1699bX.a(c0729q2.W0()).filter(new Predicate() { // from class: com.android.tools.r8.b0$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((T0) obj).y0();
            }
        }).flatMap(new Function() { // from class: com.android.tools.r8.b0$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return b0.a((T0) obj);
            }
        }), AbstractC1699bX.a(c0729q2.M1()).filter(new Predicate() { // from class: com.android.tools.r8.b0$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((W0) obj).z0();
            }
        }).flatMap(new Function() { // from class: com.android.tools.r8.b0$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return b0.a((W0) obj);
            }
        })).forEach(new Consumer() { // from class: com.android.tools.r8.b0$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0633d0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Stream a(T0 t0) {
        return t0.s0().stream();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Stream a(W0 w0) {
        return w0.s0().stream();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0633d0 c0633d0) {
        for (C0649f0 c0649f0 : c0633d0.b.b) {
            a(c0649f0.b);
        }
    }

    private void a(B2 b2) {
        int iOrdinal = b2.N0().ordinal();
        if (iOrdinal == 3) {
            this.b.referencedInt(b2.D0().b);
            return;
        }
        if (iOrdinal == 9) {
            this.b.referencedString(((C0759u2) b2.K0().b).toString());
            return;
        }
        int i = 0;
        if (iOrdinal == 14) {
            B2[] b2ArrJ1 = b2.v0().j1();
            int length = b2ArrJ1.length;
            while (i < length) {
                a(b2ArrJ1[i]);
                i++;
            }
            return;
        }
        if (iOrdinal != 15) {
            return;
        }
        C0649f0[] c0649f0Arr = b2.u0().b.b;
        int length2 = c0649f0Arr.length;
        while (i < length2) {
            a(c0649f0Arr[i].b);
            i++;
        }
    }
}
