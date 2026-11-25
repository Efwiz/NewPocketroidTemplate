package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0649f0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.KotlinClassMetadata;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.IdentityHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2888qz {
    static final /* synthetic */ boolean a = true;

    private static String[] b(com.android.tools.r8.graph.B2 b2, String str) {
        if (!b2.P0()) {
            throw new C2816pz("invalid '" + str + "' value: " + b2.r0());
        }
        com.android.tools.r8.graph.B2[] b2ArrJ1 = b2.v0().j1();
        String[] strArr = new String[b2ArrJ1.length];
        for (int i = 0; i < b2ArrJ1.length; i++) {
            strArr[i] = a(b2ArrJ1[i], str + "[" + i + "]");
        }
        return strArr;
    }

    private static int[] a(com.android.tools.r8.graph.B2 b2) {
        if (b2.P0()) {
            com.android.tools.r8.graph.B2[] b2ArrJ1 = b2.v0().j1();
            int[] iArr = new int[b2ArrJ1.length];
            for (int i = 0; i < b2ArrJ1.length; i++) {
                iArr[i] = ((Integer) b2ArrJ1[i].M0()).intValue();
            }
            return iArr;
        }
        throw new C2816pz("invalid 'mv' value: " + b2.r0());
    }

    public static InterfaceC2743oz a(C0702m c0702m, AbstractC0727q0 abstractC0727q0, C0633d0 c0633d0, Consumer consumer, Supplier supplier) {
        try {
            return a(c0702m, abstractC0727q0, consumer, c0633d0);
        } catch (C1440Uz e) {
            if (((Boolean) supplier.get()).booleanValue()) {
                c0702m.a0().warning(C1339Rz.a());
            }
            c0702m.a0().info(new StringDiagnostic("Class " + abstractC0727q0.d.r0() + " has malformed kotlin.Metadata: " + e.getMessage()));
            return AbstractC1745cA.b();
        } catch (Throwable th) {
            if (((Boolean) supplier.get()).booleanValue()) {
                c0702m.a0().warning(C1339Rz.a());
            }
            c0702m.a0().info(new StringDiagnostic("Unexpected error while reading " + abstractC0727q0.d.r0() + "'s kotlin.Metadata: " + th.getMessage()));
            return AbstractC1745cA.c();
        }
    }

    private static InterfaceC2743oz a(C0702m c0702m, AbstractC0727q0 abstractC0727q0, Consumer consumer, C0633d0 c0633d0) throws C1440Uz {
        C2037fz c2037fz = c0702m.a().z4;
        KotlinClassMetadata kotlinClassMetadataA = a(c2037fz, c0633d0.b);
        if (kotlinClassMetadataA != null) {
            return a(c2037fz, abstractC0727q0, kotlinClassMetadataA, c0702m, consumer);
        }
        throw new C1440Uz(new Exception("Could not parse metadata for " + abstractC0727q0.r0()));
    }

    public static boolean a(C0702m c0702m, AbstractC0727q0 abstractC0727q0, Supplier supplier) {
        C0633d0 c0633d0A;
        C0722p1 c0722p1A = c0702m.a();
        C2037fz c2037fz = c0722p1A.z4;
        if (C2593nA.a(abstractC0727q0, c2037fz) == 3 || (c0633d0A = abstractC0727q0.s0().a(c0722p1A.t3)) == null) {
            return false;
        }
        com.android.tools.r8.graph.R0 r0 = c0633d0A.b;
        IdentityHashMap identityHashMap = new IdentityHashMap();
        for (C0649f0 c0649f0 : r0.b) {
            identityHashMap.put(c0649f0.a, c0649f0);
        }
        try {
            C0649f0 c0649f1 = (C0649f0) identityHashMap.get(c2037fz.d.a);
            if (c0649f1 != null) {
                if (((Integer) c0649f1.b.M0()).intValue() == 3) {
                    KotlinClassMetadata kotlinClassMetadataA = a(c2037fz, identityHashMap);
                    if (kotlinClassMetadataA instanceof KotlinClassMetadata.SyntheticClass) {
                        return ((KotlinClassMetadata.SyntheticClass) kotlinClassMetadataA).b();
                    }
                }
                if (!a) {
                    boolean z = a(c2037fz, identityHashMap) instanceof KotlinClassMetadata.SyntheticClass;
                    C0649f0 c0649f2 = (C0649f0) identityHashMap.get(c2037fz.d.a);
                    if (c0649f2 != null) {
                        if (z != (((Integer) c0649f2.b.M0()).intValue() == 3)) {
                            throw new AssertionError("Synthetic class kinds should agree");
                        }
                    } else {
                        throw new C2816pz("element 'k' is missing.");
                    }
                }
                return false;
            }
            throw new C2816pz("element 'k' is missing.");
        } catch (C1440Uz e) {
            if (((Boolean) supplier.get()).booleanValue()) {
                c0702m.a0().warning(C1339Rz.a());
            }
            c0702m.a0().info(new StringDiagnostic("Class " + abstractC0727q0.d.r0() + " has malformed kotlin.Metadata: " + e.getMessage()));
            return false;
        }
    }

    public static KotlinClassMetadata a(C2037fz c2037fz, com.android.tools.r8.graph.R0 r0) throws C1440Uz {
        IdentityHashMap identityHashMap = new IdentityHashMap();
        for (C0649f0 c0649f0 : r0.b) {
            identityHashMap.put(c0649f0.a, c0649f0);
        }
        return a(c2037fz, identityHashMap);
    }

    private static KotlinClassMetadata a(C2037fz c2037fz, IdentityHashMap identityHashMap) throws C1440Uz {
        C0649f0 c0649f0 = (C0649f0) identityHashMap.get(c2037fz.d.a);
        if (c0649f0 != null) {
            int iIntValue = ((Integer) c0649f0.b.M0()).intValue();
            C0649f0 c0649f1 = (C0649f0) identityHashMap.get(c2037fz.d.b);
            int[] iArrA = c0649f1 == null ? null : a(c0649f1.b);
            C0649f0 c0649f2 = (C0649f0) identityHashMap.get(c2037fz.d.d);
            String[] strArrB = c0649f2 == null ? null : b(c0649f2.b, JvmAnnotationNames.METADATA_DATA_FIELD_NAME);
            C0649f0 c0649f3 = (C0649f0) identityHashMap.get(c2037fz.d.e);
            String[] strArrB2 = c0649f3 == null ? null : b(c0649f3.b, JvmAnnotationNames.METADATA_STRINGS_FIELD_NAME);
            C0649f0 c0649f4 = (C0649f0) identityHashMap.get(c2037fz.d.f);
            String strA = c0649f4 == null ? null : a(c0649f4.b, "xs");
            C0649f0 c0649f5 = (C0649f0) identityHashMap.get(c2037fz.d.g);
            String strA2 = c0649f5 == null ? null : a(c0649f5.b, JvmAnnotationNames.METADATA_PACKAGE_NAME_FIELD_NAME);
            C0649f0 c0649f6 = (C0649f0) identityHashMap.get(c2037fz.d.h);
            try {
                return KotlinClassMetadata.a(new C1312Qz(Integer.valueOf(iIntValue), iArrA, strArrB, strArrB2, strA, strA2, c0649f6 != null ? (Integer) c0649f6.b.M0() : null));
            } catch (C1640an | C2816pz | ClassCastException e) {
                throw new C1440Uz(e);
            }
        }
        throw new C2816pz("element 'k' is missing.");
    }

    public static InterfaceC2743oz a(C2037fz c2037fz, AbstractC0727q0 abstractC0727q0, KotlinClassMetadata kotlinClassMetadata, C0702m c0702m, Consumer consumer) {
        InterfaceC3115uE interfaceC3115uEA = kotlinClassMetadata.a();
        String strPn = interfaceC3115uEA.pn();
        int[] iArrMv = interfaceC3115uEA.mv();
        if (kotlinClassMetadata instanceof KotlinClassMetadata.Class) {
            return C2654nz.a((KotlinClassMetadata.Class) kotlinClassMetadata, strPn, iArrMv, abstractC0727q0, c0702m, consumer);
        }
        if (kotlinClassMetadata instanceof KotlinClassMetadata.FileFacade) {
            return C1030Gz.a((KotlinClassMetadata.FileFacade) kotlinClassMetadata, strPn, iArrMv, abstractC0727q0, c0702m, consumer);
        }
        if (kotlinClassMetadata instanceof KotlinClassMetadata.a) {
            return C1981fA.a((KotlinClassMetadata.a) kotlinClassMetadata, strPn, iArrMv, c0702m.a());
        }
        if (kotlinClassMetadata instanceof KotlinClassMetadata.MultiFileClassPart) {
            return C2053gA.a((KotlinClassMetadata.MultiFileClassPart) kotlinClassMetadata, strPn, iArrMv, abstractC0727q0, c0702m, consumer);
        }
        if (kotlinClassMetadata instanceof KotlinClassMetadata.SyntheticClass) {
            return C2593nA.a((KotlinClassMetadata.SyntheticClass) kotlinClassMetadata, strPn, iArrMv, abstractC0727q0, c2037fz, c0702m);
        }
        throw new C2816pz("unsupported 'k' value: " + interfaceC3115uEA.k());
    }

    private static String a(com.android.tools.r8.graph.B2 b2, String str) {
        if (b2.e1()) {
            return ((C0759u2) b2.K0().j1()).toString();
        }
        throw new C2816pz("invalid '" + str + "' value: " + b2.r0());
    }
}
