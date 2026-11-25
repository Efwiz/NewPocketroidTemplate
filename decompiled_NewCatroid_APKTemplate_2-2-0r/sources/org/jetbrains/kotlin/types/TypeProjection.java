package org.jetbrains.kotlin.types;

import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;

/* JADX INFO: loaded from: classes6.dex */
public interface TypeProjection extends TypeArgumentMarker {
    Variance getProjectionKind();

    KotlinType getType();

    boolean isStarProjection();

    TypeProjection refine(KotlinTypeRefiner kotlinTypeRefiner);

    TypeProjection replaceType(KotlinType kotlinType);
}
