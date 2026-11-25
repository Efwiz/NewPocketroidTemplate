package org.jetbrains.kotlin.resolve.calls.components;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.resolve.calls.inference.model.InferredEmptyIntersectionError;
import org.jetbrains.kotlin.types.EmptyIntersectionTypeKind;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeVariableMarker;

/* JADX INFO: compiled from: ResolutionParts.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class CheckIncompatibleTypeVariableUpperBounds$process$1$errorFactory$1 extends FunctionReference implements Function4<List<? extends KotlinTypeMarker>, List<? extends KotlinTypeMarker>, TypeVariableMarker, EmptyIntersectionTypeKind, InferredEmptyIntersectionError> {
    public static final CheckIncompatibleTypeVariableUpperBounds$process$1$errorFactory$1 INSTANCE = new CheckIncompatibleTypeVariableUpperBounds$process$1$errorFactory$1();

    CheckIncompatibleTypeVariableUpperBounds$process$1$errorFactory$1() {
        super(4);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "<init>(Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/types/model/TypeVariableMarker;Lorg/jetbrains/kotlin/types/EmptyIntersectionTypeKind;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "<init>";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(InferredEmptyIntersectionError.class);
    }

    @Override // kotlin.jvm.functions.Function4
    public final InferredEmptyIntersectionError invoke(List<? extends KotlinTypeMarker> p0, List<? extends KotlinTypeMarker> p1, TypeVariableMarker p2, EmptyIntersectionTypeKind p3) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        Intrinsics.checkNotNullParameter(p2, "p2");
        Intrinsics.checkNotNullParameter(p3, "p3");
        return new InferredEmptyIntersectionError(p0, p1, p2, p3);
    }
}
