package org.jetbrains.kotlin.types.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: typeSystemContextHelpers.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0005\u001a\u00020\u0002*\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0007"}, d2 = {"isIntegerLiteralTypeConstructor", "", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "context", "Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "typeConstructor", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "compiler.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeSystemContextHelpersKt {
    public static final TypeConstructorMarker typeConstructor(KotlinTypeMarker kotlinTypeMarker, TypeSystemContext context) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return context.typeConstructor(kotlinTypeMarker);
    }

    public static final boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker, TypeSystemContext context) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return context.isIntegerLiteralTypeConstructor(typeConstructorMarker);
    }
}
