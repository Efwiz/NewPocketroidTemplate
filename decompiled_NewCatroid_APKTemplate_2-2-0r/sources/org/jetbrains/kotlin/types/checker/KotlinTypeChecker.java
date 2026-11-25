package org.jetbrains.kotlin.types.checker;

import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;

/* JADX INFO: loaded from: classes6.dex */
public interface KotlinTypeChecker {
    public static final KotlinTypeChecker DEFAULT = NewKotlinTypeChecker.INSTANCE.getDefault();

    public interface TypeConstructorEquality {
        boolean equals(TypeConstructor typeConstructor, TypeConstructor typeConstructor2);
    }

    boolean equalTypes(KotlinType kotlinType, KotlinType kotlinType2);

    boolean isSubtypeOf(KotlinType kotlinType, KotlinType kotlinType2);
}
