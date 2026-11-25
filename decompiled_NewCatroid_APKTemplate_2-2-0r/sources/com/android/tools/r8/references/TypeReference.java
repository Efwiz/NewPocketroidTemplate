package com.android.tools.r8.references;

import com.android.tools.r8.utils.C3970u0;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface TypeReference {
    default ArrayReference asArray() {
        return null;
    }

    default ClassReference asClass() {
        return null;
    }

    default PrimitiveReference asPrimitive() {
        return null;
    }

    String getDescriptor();

    default String getTypeName() {
        return C3970u0.b(getDescriptor());
    }

    default boolean isArray() {
        return false;
    }

    default boolean isClass() {
        return false;
    }

    default boolean isPrimitive() {
        return false;
    }
}
