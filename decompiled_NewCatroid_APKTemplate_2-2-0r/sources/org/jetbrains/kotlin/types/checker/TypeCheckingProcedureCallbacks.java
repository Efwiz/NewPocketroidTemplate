package org.jetbrains.kotlin.types.checker;

import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjection;

/* JADX INFO: loaded from: classes6.dex */
public interface TypeCheckingProcedureCallbacks {
    boolean assertEqualTypeConstructors(TypeConstructor typeConstructor, TypeConstructor typeConstructor2);

    boolean assertEqualTypes(KotlinType kotlinType, KotlinType kotlinType2, TypeCheckingProcedure typeCheckingProcedure);

    boolean assertSubtype(KotlinType kotlinType, KotlinType kotlinType2, TypeCheckingProcedure typeCheckingProcedure);

    boolean capture(KotlinType kotlinType, TypeProjection typeProjection);

    boolean noCorrespondingSupertype(KotlinType kotlinType, KotlinType kotlinType2);
}
