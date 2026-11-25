package org.jetbrains.kotlin.load.java.typeEnhancement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TypeComponentPosition.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"shouldEnhance", "", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/TypeComponentPosition;", "compiler.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeComponentPositionKt {
    public static final boolean shouldEnhance(TypeComponentPosition typeComponentPosition) {
        Intrinsics.checkNotNullParameter(typeComponentPosition, "<this>");
        return typeComponentPosition != TypeComponentPosition.INFLEXIBLE;
    }
}
