package com.android.tools.r8.utils;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.references.TypeReference;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: renamed from: com.android.tools.r8.utils.e2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class C3889e2 {
    private static final Comparator a = new Comparator() { // from class: com.android.tools.r8.utils.e2$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return C3889e2.a((MethodReference) obj, (MethodReference) obj2);
        }
    };
    public static final /* synthetic */ int b = 0;

    /* JADX INFO: Access modifiers changed from: private */
    public static int a(MethodReference methodReference, MethodReference methodReference2) {
        int iA = AbstractC3902h0.a(C3882d0.a().compare(methodReference.getHolderClass(), methodReference2.getHolderClass()));
        if (!AbstractC3902h0.b(iA)) {
            return AbstractC3902h0.c(iA);
        }
        int iA2 = AbstractC3902h0.a(methodReference.getMethodName().compareTo(methodReference2.getMethodName()));
        if (!AbstractC3902h0.b(iA2)) {
            return AbstractC3902h0.c(iA2);
        }
        int iA3 = AbstractC3902h0.a(I3.a().compare(methodReference.getReturnType(), methodReference2.getReturnType()));
        if (!AbstractC3902h0.b(iA3)) {
            return AbstractC3902h0.c(iA3);
        }
        for (int i = 0; i < Math.min(methodReference.getFormalTypes().size(), methodReference2.getFormalTypes().size()); i++) {
            int iA4 = AbstractC3902h0.a(I3.a().compare(methodReference.getFormalTypes().get(i), methodReference2.getFormalTypes().get(i)));
            if (!AbstractC3902h0.b(iA4)) {
                return AbstractC3902h0.c(iA4);
            }
        }
        return methodReference.getFormalTypes().size() - methodReference2.getFormalTypes().size();
    }

    public static MethodReference b(Class<?> cls) {
        return b(Reference.classFromClass(cls));
    }

    public static MethodReference b(ClassReference classReference) {
        return Reference.method(classReference, "main", AbstractC1639am.c(Reference.array(Reference.classFromClass(String.class), 1)), null);
    }

    public static String b(MethodReference methodReference) {
        return a(methodReference, true, true);
    }

    public static MethodReference a(Class<?> cls) {
        return a(Reference.classFromClass(cls));
    }

    public static MethodReference a(ClassReference classReference) {
        return Reference.method(classReference, "<init>", Collections.emptyList(), null);
    }

    public static MethodReference a(ClassReference classReference, TypeReference... typeReferenceArr) {
        return Reference.method(classReference, "<init>", Arrays.asList(typeReferenceArr), null);
    }

    public static Comparator<MethodReference> a() {
        return a;
    }

    public static MethodReference a(Class<?> cls, String str, Class<?>... clsArr) {
        try {
            return Reference.methodFromMethod(cls.getDeclaredMethod(str, clsArr));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static MethodReference a(int i, String str) {
        String strSubstring;
        int iIndexOf;
        String strSubstring2 = str.substring(0, i);
        int i2 = C3882d0.b;
        ClassReference classReferenceClassFromDescriptor = C3970u0.z(strSubstring2) ? Reference.classFromDescriptor(strSubstring2) : null;
        if (classReferenceClassFromDescriptor == null || (iIndexOf = (strSubstring = str.substring(i + 2)).indexOf(40)) <= 0) {
            return null;
        }
        String strSubstring3 = strSubstring.substring(0, iIndexOf);
        String strSubstring4 = strSubstring.substring(iIndexOf);
        ArrayList arrayList = new ArrayList();
        for (String str2 : C3970u0.e(strSubstring4)) {
            arrayList.add(Reference.typeFromDescriptor(str2));
        }
        return Reference.method(classReferenceClassFromDescriptor, strSubstring3, arrayList, Reference.returnTypeFromDescriptor(C3970u0.u(strSubstring4)));
    }

    public static C0684j2 a(MethodReference methodReference, C0722p1 c0722p1) {
        ClassReference holderClass = methodReference.getHolderClass();
        int i = C3882d0.b;
        return c0722p1.a(c0722p1.e(holderClass.getDescriptor()), I3.a(methodReference.getFormalTypes(), methodReference.getReturnType(), c0722p1), methodReference.getMethodName());
    }

    public static String a(MethodReference methodReference) {
        return methodReference.getHolderClass().getDescriptor() + "->" + methodReference.getMethodName() + methodReference.getMethodDescriptor();
    }

    public static String a(MethodReference methodReference, boolean z, boolean z2) {
        String typeName;
        StringBuilder sb = new StringBuilder();
        if (z2) {
            if (methodReference.getReturnType() != null) {
                typeName = methodReference.getReturnType().getTypeName();
            } else {
                typeName = PsiKeyword.VOID;
            }
            sb.append(typeName).append(" ");
        }
        if (z) {
            sb.append(methodReference.getHolderClass().getTypeName()).append(".");
        }
        sb.append(methodReference.getMethodName()).append("(");
        Iterator<TypeReference> it = methodReference.getFormalTypes().iterator();
        if (it.hasNext()) {
            sb.append(it.next().getTypeName());
            while (it.hasNext()) {
                sb.append(", ").append(it.next().getTypeName());
            }
        }
        return sb.append(Constant.AFTER_QUTO).toString();
    }

    public static MethodReference a(String str) {
        int iIndexOf = str.indexOf(";") + 1;
        int iIndexOf2 = str.indexOf("(", iIndexOf);
        return Reference.methodFromDescriptor(str.substring(0, iIndexOf), str.substring(iIndexOf, iIndexOf2), str.substring(iIndexOf2));
    }
}
