package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC2509m2;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.CZ;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class N3 extends AbstractC2509m2 {
    static final /* synthetic */ boolean f = true;
    private final M3 b;
    private final BiConsumer c;
    private ArrayList d;
    private final ArrayList e;

    public N3(M3 m3, BiConsumer biConsumer) {
        super(589824);
        this.d = null;
        this.e = new ArrayList();
        this.b = m3;
        this.c = biConsumer;
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, String str2, String str3) {
        a(new C2(this.b.a(this.b.e(str2), str3, str2)), str);
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(final String str, final String str2) {
        return new N3(this.b, new BiConsumer() { // from class: com.android.tools.r8.graph.N3$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(str, str2, (List) obj, (List) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, String str2, List list, List list2) {
        a(new A2(T3.a(str2, list, list2, this.b)), str);
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final AbstractC2509m2 a(final String str) {
        return new N3(this.b, new BiConsumer() { // from class: com.android.tools.r8.graph.N3$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(str, (List) obj, (List) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, List list, List list2) {
        if (!f && list != null) {
            throw new AssertionError();
        }
        a(new B2.a((B2[]) list2.toArray(B2.a)), str);
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a() {
        this.c.accept(this.d, this.e);
    }

    private void a(B2 b2, String str) {
        if (str != null) {
            if (this.d == null) {
                this.d = new ArrayList();
            }
            this.d.add(this.b.d(str));
        }
        this.e.add(b2);
    }

    private static B2.a a(Object obj) {
        int i = 0;
        if (obj instanceof byte[]) {
            byte[] bArr = (byte[]) obj;
            B2[] b2Arr = new B2[bArr.length];
            while (i < bArr.length) {
                b2Arr[i] = B2.c.a(bArr[i]);
                i++;
            }
            return new B2.a(b2Arr);
        }
        if (obj instanceof boolean[]) {
            boolean[] zArr = (boolean[]) obj;
            B2[] b2Arr2 = new B2[zArr.length];
            while (i < zArr.length) {
                b2Arr2[i] = B2.b.a(zArr[i]);
                i++;
            }
            return new B2.a(b2Arr2);
        }
        if (obj instanceof char[]) {
            char[] cArr = (char[]) obj;
            B2[] b2Arr3 = new B2[cArr.length];
            while (i < cArr.length) {
                b2Arr3[i] = B2.d.a(cArr[i]);
                i++;
            }
            return new B2.a(b2Arr3);
        }
        if (obj instanceof short[]) {
            short[] sArr = (short[]) obj;
            B2[] b2Arr4 = new B2[sArr.length];
            while (i < sArr.length) {
                b2Arr4[i] = B2.i.a(sArr[i]);
                i++;
            }
            return new B2.a(b2Arr4);
        }
        if (obj instanceof int[]) {
            int[] iArr = (int[]) obj;
            B2[] b2Arr5 = new B2[iArr.length];
            while (i < iArr.length) {
                b2Arr5[i] = B2.g.j(iArr[i]);
                i++;
            }
            return new B2.a(b2Arr5);
        }
        if (obj instanceof long[]) {
            long[] jArr = (long[]) obj;
            B2[] b2Arr6 = new B2[jArr.length];
            while (i < jArr.length) {
                b2Arr6[i] = B2.h.a(jArr[i]);
                i++;
            }
            return new B2.a(b2Arr6);
        }
        if (obj instanceof float[]) {
            float[] fArr = (float[]) obj;
            B2[] b2Arr7 = new B2[fArr.length];
            while (i < fArr.length) {
                b2Arr7[i] = B2.f.a(fArr[i]);
                i++;
            }
            return new B2.a(b2Arr7);
        }
        if (obj instanceof double[]) {
            double[] dArr = (double[]) obj;
            B2[] b2Arr8 = new B2[dArr.length];
            while (i < dArr.length) {
                b2Arr8[i] = B2.e.a(dArr[i]);
                i++;
            }
            return new B2.a(b2Arr8);
        }
        throw new C3405y10("Unexpected type of annotation value: " + obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC2509m2
    public final void a(String str, Object obj) {
        B2 b2A;
        B2 kVar;
        if (obj == null) {
            b2A = I2.b;
        } else if (obj instanceof Byte) {
            b2A = B2.c.a(((Byte) obj).byteValue());
        } else if (obj instanceof Boolean) {
            b2A = B2.b.a(((Boolean) obj).booleanValue());
        } else if (obj instanceof Character) {
            b2A = B2.d.a(((Character) obj).charValue());
        } else if (obj instanceof Short) {
            b2A = B2.i.a(((Short) obj).shortValue());
        } else if (obj instanceof Integer) {
            b2A = B2.g.j(((Integer) obj).intValue());
        } else if (obj instanceof Long) {
            b2A = B2.h.a(((Long) obj).longValue());
        } else if (obj instanceof Float) {
            b2A = B2.f.a(((Float) obj).floatValue());
        } else if (obj instanceof Double) {
            b2A = B2.e.a(((Double) obj).doubleValue());
        } else {
            if (obj instanceof String) {
                kVar = new B2.j(this.b.d((String) obj));
            } else if (obj instanceof CZ) {
                kVar = new B2.k(this.b.e(((CZ) obj).b()));
            } else {
                b2A = a(obj);
            }
            b2A = kVar;
        }
        a(b2A, str);
    }
}
