package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeContext;

/* JADX INFO: compiled from: TypeVariableTypeRemovingSubstitutor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"removeTypeVariableTypes", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "typeContext", "Lorg/jetbrains/kotlin/fir/types/ConeTypeContext;", "semantics"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeVariableTypeRemovingSubstitutorKt {
    public static final ConeKotlinType removeTypeVariableTypes(ConeKotlinType coneKotlinType, ConeTypeContext typeContext) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(typeContext, "typeContext");
        return new TypeVariableTypeRemovingSubstitutor(typeContext).substituteOrSelf(coneKotlinType);
    }
}
