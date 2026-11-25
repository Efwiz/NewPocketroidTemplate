package com.android.tools.r8.utils;

import com.android.tools.r8.references.FieldReference;
import com.android.tools.r8.references.Reference;
import java.util.Comparator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class V0 {
    private static final Comparator a = new Comparator() { // from class: com.android.tools.r8.utils.V0$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return V0.b((FieldReference) obj, (FieldReference) obj2);
        }
    };
    public static final /* synthetic */ int b = 0;

    public static FieldReference a(Class<?> cls, String str) {
        try {
            return Reference.fieldFromField(cls.getDeclaredField(str));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(FieldReference fieldReference, FieldReference fieldReference2) {
        int iA = AbstractC3902h0.a(C3882d0.a().compare(fieldReference.getHolderClass(), fieldReference2.getHolderClass()));
        if (!AbstractC3902h0.b(iA)) {
            return AbstractC3902h0.c(iA);
        }
        int iA2 = AbstractC3902h0.a(fieldReference.getFieldName().compareTo(fieldReference2.getFieldName()));
        return !AbstractC3902h0.b(iA2) ? AbstractC3902h0.c(iA2) : I3.a().compare(fieldReference.getFieldType(), fieldReference2.getFieldType());
    }

    public static int a(FieldReference fieldReference, FieldReference fieldReference2) {
        return a.compare(fieldReference, fieldReference2);
    }

    public static Comparator a() {
        return a;
    }

    public static String a(FieldReference fieldReference) {
        return fieldReference.getFieldType().getTypeName() + " " + fieldReference.getHolderClass().getTypeName() + "." + fieldReference.getFieldName();
    }
}
