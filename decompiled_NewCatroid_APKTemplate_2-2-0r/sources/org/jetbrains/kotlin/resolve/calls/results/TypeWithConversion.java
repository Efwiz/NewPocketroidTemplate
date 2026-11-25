package org.jetbrains.kotlin.resolve.calls.results;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: FlatSignature.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001b\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/results/TypeWithConversion;", "", "resultType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "originalTypeIfWasConverted", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)V", "getOriginalTypeIfWasConverted", "()Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "getResultType", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeWithConversion {
    private final KotlinTypeMarker originalTypeIfWasConverted;
    private final KotlinTypeMarker resultType;

    public TypeWithConversion(KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2) {
        this.resultType = kotlinTypeMarker;
        this.originalTypeIfWasConverted = kotlinTypeMarker2;
    }

    public /* synthetic */ TypeWithConversion(KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kotlinTypeMarker, (i & 2) != 0 ? null : kotlinTypeMarker2);
    }

    public final KotlinTypeMarker getOriginalTypeIfWasConverted() {
        return this.originalTypeIfWasConverted;
    }

    public final KotlinTypeMarker getResultType() {
        return this.resultType;
    }
}
