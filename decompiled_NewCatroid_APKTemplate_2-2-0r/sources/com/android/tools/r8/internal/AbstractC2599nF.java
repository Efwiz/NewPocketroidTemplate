package com.android.tools.r8.internal;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nF, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2599nF {
    /* JADX WARN: Code duplicated, block: B:112:0x01ee A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:70:0x0195 A[PHI: r14
      0x0195: PHI (r14v3 java.lang.Integer) = (r14v2 java.lang.Integer), (r14v9 java.lang.Integer) binds: [B:62:0x0176, B:69:0x0193] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:73:0x01ab  */
    /* JADX WARN: Code duplicated, block: B:75:0x01ca  */
    /* JADX WARN: Code duplicated, block: B:76:0x01d4  */
    /* JADX WARN: Code duplicated, block: B:78:0x01d7  */
    /* JADX WARN: Code duplicated, block: B:79:0x01e2  */
    /* JADX WARN: Code duplicated, block: B:81:0x01e5  */
    /* JADX WARN: Code duplicated, block: B:82:0x01ea  */
    public static void a(byte[] bArr, String str, InterfaceC1475Wi interfaceC1475Wi) {
        int[] iArr;
        String str2;
        YA yaL;
        Integer num;
        Integer numValueOf;
        String str3;
        String strA;
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        try {
            int i = dataInputStream.readInt();
            if (i < 0 || i > 1024) {
                iArr = null;
            } else {
                iArr = new int[i];
                for (int i2 = 0; i2 < i; i2++) {
                    iArr[i2] = dataInputStream.readInt();
                }
            }
        } catch (IOException unused) {
        }
        if (iArr == null) {
            int i3 = C2690oF.b;
            return;
        }
        C3394xu c3394xu = new C3394xu(Arrays.copyOf(iArr, iArr.length));
        if (!c3394xu.d()) {
            interfaceC1475Wi.a(c3394xu);
            int i4 = C2690oF.b;
            return;
        }
        C3394xu c3394xu2 = new C3394xu(iArr, (((c3394xu.a() != 1 || c3394xu.b() < 4) ? 0 : dataInputStream.readInt()) & 1) != 0);
        if (!c3394xu2.d()) {
            interfaceC1475Wi.a(c3394xu2);
            int i5 = C2690oF.b;
            return;
        }
        C2867qh c2867qhA = C2867qh.a();
        c2867qhA.a(O5.a);
        c2867qhA.a(O5.b);
        c2867qhA.a(O5.c);
        c2867qhA.a(O5.d);
        c2867qhA.a(O5.e);
        c2867qhA.a(O5.f);
        c2867qhA.a(O5.g);
        c2867qhA.a(O5.h);
        c2867qhA.a(O5.i);
        c2867qhA.a(O5.j);
        c2867qhA.a(O5.k);
        c2867qhA.a(O5.l);
        C0844Au c0844Au = (C0844Au) ((AbstractC1732c1) C0844Au.m).a(dataInputStream, c2867qhA);
        if (c0844Au == null) {
            int i6 = C2690oF.b;
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (C0938Du c0938Du : c0844Au.l()) {
            String strM = c0938Du.m();
            AbstractC0936Ds.b(strM, "packageFqName");
            Object c2377kL = linkedHashMap.get(strM);
            if (c2377kL == null) {
                c2377kL = new C2377kL(strM);
                linkedHashMap.put(strM, c2377kL);
            }
            C2377kL c2377kL2 = (C2377kL) c2377kL;
            int i7 = 0;
            for (String str4 : c0938Du.n()) {
                int i8 = i7 + 1;
                AbstractC0936Ds.b(str4, "partShortName");
                String strA2 = AbstractC2766pF.a(strM, str4);
                List listK = c0938Du.k();
                AbstractC0936Ds.b(listK, "proto.multifileFacadeShortNameIdList");
                YA yaL2 = c0938Du.l();
                AbstractC0936Ds.b(yaL2, "proto.multifileFacadeShortNameList");
                Integer num2 = (Integer) AbstractC0823Ab.a(i7, listK);
                Integer numValueOf2 = num2 != null ? Integer.valueOf(num2.intValue() - 1) : null;
                String str5 = numValueOf2 != null ? (String) AbstractC0823Ab.a(numValueOf2.intValue(), yaL2) : null;
                c2377kL2.a(strA2, str5 != null ? AbstractC2766pF.a(strM, str5) : null);
                i7 = i8;
            }
            int i9 = 0;
            for (String str6 : c0938Du.i()) {
                int i10 = i9 + 1;
                List listH = c0938Du.h();
                AbstractC0936Ds.b(listH, "proto.classWithJvmPackageNamePackageIdList");
                Integer num3 = (Integer) AbstractC0823Ab.a(i9, listH);
                if (num3 != null) {
                    int iIntValue = num3.intValue();
                    YA yaI = c0844Au.i();
                    AbstractC0936Ds.b(yaI, "moduleProto.jvmPackageNameList");
                    str2 = (String) AbstractC0823Ab.a(iIntValue, yaI);
                    if (str2 == null) {
                        AbstractC0936Ds.b(str6, "partShortName");
                        String strA3 = AbstractC2766pF.a(str2, str6);
                        List listG = c0938Du.g();
                        AbstractC0936Ds.b(listG, "proto.classWithJvmPackag…fileFacadeShortNameIdList");
                        yaL = c0938Du.l();
                        AbstractC0936Ds.b(yaL, "proto.multifileFacadeShortNameList");
                        num = (Integer) AbstractC0823Ab.a(i9, listG);
                        if (num != null) {
                            numValueOf = Integer.valueOf(num.intValue() - 1);
                        } else {
                            numValueOf = null;
                        }
                        if (numValueOf != null) {
                            str3 = (String) AbstractC0823Ab.a(numValueOf.intValue(), yaL);
                        } else {
                            str3 = null;
                        }
                        if (str3 != null) {
                            strA = AbstractC2766pF.a(str2, str3);
                        } else {
                            strA = null;
                        }
                        c2377kL2.a(strA3, strA);
                    }
                } else {
                    List listH2 = c0938Du.h();
                    AbstractC0936Ds.b(listH2, "proto.classWithJvmPackageNamePackageIdList");
                    num3 = (Integer) (listH2.isEmpty() ? null : listH2.get(listH2.size() - 1));
                    if (num3 != null) {
                        int iIntValue2 = num3.intValue();
                        YA yaI2 = c0844Au.i();
                        AbstractC0936Ds.b(yaI2, "moduleProto.jvmPackageNameList");
                        str2 = (String) AbstractC0823Ab.a(iIntValue2, yaI2);
                        if (str2 == null) {
                            AbstractC0936Ds.b(str6, "partShortName");
                            String strA4 = AbstractC2766pF.a(str2, str6);
                            List listG2 = c0938Du.g();
                            AbstractC0936Ds.b(listG2, "proto.classWithJvmPackag…fileFacadeShortNameIdList");
                            yaL = c0938Du.l();
                            AbstractC0936Ds.b(yaL, "proto.multifileFacadeShortNameList");
                            num = (Integer) AbstractC0823Ab.a(i9, listG2);
                            if (num != null) {
                                numValueOf = Integer.valueOf(num.intValue() - 1);
                            } else {
                                numValueOf = null;
                            }
                            if (numValueOf != null) {
                                str3 = (String) AbstractC0823Ab.a(numValueOf.intValue(), yaL);
                            } else {
                                str3 = null;
                            }
                            if (str3 != null) {
                                strA = AbstractC2766pF.a(str2, str3);
                            } else {
                                strA = null;
                            }
                            c2377kL2.a(strA4, strA);
                        }
                    }
                }
                i9 = i10;
            }
        }
        for (C0938Du c0938Du2 : c0844Au.j()) {
            String strM2 = c0938Du2.m();
            AbstractC0936Ds.b(strM2, "proto.packageFqName");
            Object c2377kL3 = linkedHashMap.get(strM2);
            if (c2377kL3 == null) {
                String strM3 = c0938Du2.m();
                AbstractC0936Ds.b(strM3, "proto.packageFqName");
                c2377kL3 = new C2377kL(strM3);
                linkedHashMap.put(strM2, c2377kL3);
            }
            C2377kL c2377kL4 = (C2377kL) c2377kL3;
            YA yaN = c0938Du2.n();
            AbstractC0936Ds.b(yaN, "proto.shortClassNameList");
            Iterator<E> it = yaN.iterator();
            while (it.hasNext()) {
                c2377kL4.a((String) it.next());
            }
        }
        PO poN = c0844Au.n();
        AbstractC0936Ds.b(poN, "moduleProto.stringTable");
        MO moM = c0844Au.m();
        AbstractC0936Ds.b(moM, "moduleProto.qualifiedNameTable");
        C2691oG c2691oG = new C2691oG(poN, moM);
        List listG3 = c0844Au.g();
        AbstractC0936Ds.b(listG3, "moduleProto.annotationList");
        ArrayList arrayList = new ArrayList(AbstractC0823Ab.a((Iterable) listG3));
        Iterator it2 = listG3.iterator();
        while (it2.hasNext()) {
            arrayList.add(c2691oG.c(((XN) it2.next()).i()));
        }
        AbstractC0936Ds.b(c0844Au.k(), "moduleProto.optionalAnnotationClassList");
    }
}
