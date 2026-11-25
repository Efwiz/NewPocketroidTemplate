package org.jetbrains.kotlin.fir.lightTree.fir.modifier;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TypeParameterModifier.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/TypeParameterModifier;", "Lorg/jetbrains/kotlin/fir/lightTree/fir/modifier/Modifier;", "varianceOrReificationModifiers", "", "(J)V", "hasReified", "", "light-tree2fir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeParameterModifier extends Modifier {
    public TypeParameterModifier() {
        this(0L, 1, null);
    }

    public TypeParameterModifier(long j) {
        super(j);
    }

    public /* synthetic */ TypeParameterModifier(long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? ModifierFlag.NONE.getValue() : j);
    }

    public final boolean hasReified() {
        return hasFlag(ModifierFlag.REIFICATION_REIFIED);
    }
}
