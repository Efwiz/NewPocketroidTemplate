package com.android.tools.r8.jetbrains.kotlinx.metadata.jvm;

import com.android.tools.r8.internal.AbstractC0936Ds;
import com.android.tools.r8.internal.InterfaceC3115uE;
import java.util.Arrays;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class i {
    /* JADX WARN: Type inference failed for: r15v2, types: [com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.h] */
    public static h a(Integer num, int[] iArr, String[] strArr, String[] strArr2, String str, Integer num2, int i) {
        if ((i & 1) != 0) {
            num = null;
        }
        if ((i & 2) != 0) {
            iArr = null;
        }
        if ((i & 4) != 0) {
            strArr = null;
        }
        if ((i & 8) != 0) {
            strArr2 = null;
        }
        if ((i & 16) != 0) {
            str = null;
        }
        if ((i & 64) != 0) {
            num2 = null;
        }
        final int[] iArr2 = {1, 0, 3};
        if (strArr == null) {
            strArr = new String[0];
        }
        if (strArr2 == null) {
            strArr2 = new String[0];
        }
        final int iIntValue = num2 != null ? num2.intValue() : 0;
        if (str == null) {
            str = "";
        }
        final String str2 = str;
        final int iIntValue2 = num != null ? num.intValue() : 1;
        if (iArr == null) {
            iArr = new int[0];
        }
        final int[] iArr3 = iArr;
        final String str3 = "";
        final String[] strArr3 = strArr;
        final String[] strArr4 = strArr2;
        return new InterfaceC3115uE() { // from class: com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.h
            @Override // java.lang.annotation.Annotation
            public final /* synthetic */ Class annotationType() {
                return InterfaceC3115uE.class;
            }

            @Override // com.android.tools.r8.internal.InterfaceC3115uE
            public final /* synthetic */ int[] bv() {
                return iArr2;
            }

            @Override // com.android.tools.r8.internal.InterfaceC3115uE
            public final /* synthetic */ String[] d1() {
                return strArr3;
            }

            @Override // com.android.tools.r8.internal.InterfaceC3115uE
            public final /* synthetic */ String[] d2() {
                return strArr4;
            }

            @Override // java.lang.annotation.Annotation
            public final boolean equals(Object obj) {
                if (!(obj instanceof InterfaceC3115uE)) {
                    return false;
                }
                InterfaceC3115uE interfaceC3115uE = (InterfaceC3115uE) obj;
                return Arrays.equals(iArr2, interfaceC3115uE.bv()) && Arrays.equals(strArr3, interfaceC3115uE.d1()) && Arrays.equals(strArr4, interfaceC3115uE.d2()) && iIntValue == interfaceC3115uE.xi() && AbstractC0936Ds.a((Object) str2, (Object) interfaceC3115uE.xs()) && iIntValue2 == interfaceC3115uE.k() && Arrays.equals(iArr3, interfaceC3115uE.mv()) && AbstractC0936Ds.a((Object) str3, (Object) interfaceC3115uE.pn());
            }

            @Override // java.lang.annotation.Annotation
            public final int hashCode() {
                return (Arrays.hashCode(iArr2) ^ (-2059602595)) + (Arrays.hashCode(strArr3) ^ (-774644295)) + (Arrays.hashCode(strArr4) ^ (-774644168)) + (Integer.hashCode(iIntValue) ^ (-2147447359)) + (str2.hashCode() ^ 1642302527) + (Integer.hashCode(iIntValue2) ^ 418090604) + (Arrays.hashCode(iArr3) ^ (-1975239209)) + (str3.hashCode() ^ (-551720817));
            }

            @Override // com.android.tools.r8.internal.InterfaceC3115uE
            public final /* synthetic */ int k() {
                return iIntValue2;
            }

            @Override // com.android.tools.r8.internal.InterfaceC3115uE
            public final /* synthetic */ int[] mv() {
                return iArr3;
            }

            @Override // com.android.tools.r8.internal.InterfaceC3115uE
            public final /* synthetic */ String pn() {
                return str3;
            }

            @Override // java.lang.annotation.Annotation
            public final String toString() {
                return "@kotlin.Metadata(bytecodeVersion=" + Arrays.toString(iArr2) + ", data1=" + Arrays.toString(strArr3) + ", data2=" + Arrays.toString(strArr4) + ", extraInt=" + iIntValue + ", extraString=" + str2 + ", kind=" + iIntValue2 + ", metadataVersion=" + Arrays.toString(iArr3) + ", packageName=" + str3 + ')';
            }

            @Override // com.android.tools.r8.internal.InterfaceC3115uE
            public final /* synthetic */ int xi() {
                return iIntValue;
            }

            @Override // com.android.tools.r8.internal.InterfaceC3115uE
            public final /* synthetic */ String xs() {
                return str2;
            }
        };
    }
}
