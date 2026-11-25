package com.android.tools.r8.utils;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.references.ArrayReference;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.PrimitiveReference;
import com.android.tools.r8.references.TypeReference;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class I3 {
    private static final Comparator a = new Comparator() { // from class: com.android.tools.r8.utils.I3$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return I3.a((TypeReference) obj, (TypeReference) obj2);
        }
    };
    static final /* synthetic */ boolean b = true;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(TypeReference typeReference, TypeReference typeReference2) {
        if (typeReference == typeReference2) {
            return 0;
        }
        if (typeReference == null) {
            return -1;
        }
        if (typeReference2 == null) {
            return 1;
        }
        return typeReference.getDescriptor().compareTo(typeReference2.getDescriptor());
    }

    public static TypeReference b() {
        return null;
    }

    public static Comparator a() {
        return a;
    }

    public static C0767v2 b(C0722p1 c0722p1, Function function, TypeReference typeReference) {
        if (typeReference == null) {
            return c0722p1.D1;
        }
        if (!typeReference.isPrimitive()) {
            if (typeReference.isArray()) {
                ArrayReference arrayReferenceAsArray = typeReference.asArray();
                return c0722p1.a(arrayReferenceAsArray.getDimensions(), b(c0722p1, function, arrayReferenceAsArray.getBaseType()));
            }
            if (b || typeReference.isClass()) {
                return (C0767v2) function.apply(typeReference.asClass());
            }
            throw new AssertionError();
        }
        PrimitiveReference primitiveReferenceAsPrimitive = typeReference.asPrimitive();
        char cCharAt = primitiveReferenceAsPrimitive.getDescriptor().charAt(0);
        if (cCharAt == 'F') {
            return c0722p1.z1;
        }
        if (cCharAt == 'S') {
            return c0722p1.C1;
        }
        if (cCharAt == 'Z') {
            return c0722p1.v1;
        }
        if (cCharAt == 'I') {
            return c0722p1.A1;
        }
        if (cCharAt == 'J') {
            return c0722p1.B1;
        }
        switch (cCharAt) {
            case 'B':
                return c0722p1.w1;
            case 'C':
                return c0722p1.x1;
            case 'D':
                return c0722p1.y1;
            default:
                throw new C3405y10("Invalid primitive descriptor: " + primitiveReferenceAsPrimitive.getDescriptor());
        }
    }

    public static C0737r2 a(List list, TypeReference typeReference, final C0722p1 c0722p1) {
        return a(list, typeReference, c0722p1, new Function() { // from class: com.android.tools.r8.utils.I3$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return I3.a(c0722p1, (ClassReference) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C0767v2 a(C0722p1 c0722p1, ClassReference classReference) {
        int i = C3882d0.b;
        return c0722p1.e(classReference.getDescriptor());
    }

    public static C0737r2 a(List list, TypeReference typeReference, final C0722p1 c0722p1, final Function function) {
        return c0722p1.a(b(c0722p1, function, typeReference), V1.a((Collection) list, new Function() { // from class: com.android.tools.r8.utils.I3$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return I3.b(c0722p1, function, (TypeReference) obj);
            }
        }));
    }
}
