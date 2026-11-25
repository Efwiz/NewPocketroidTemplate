package org.jetbrains.kotlin.cfg.pseudocode;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;

/* JADX INFO: compiled from: TypePredicate.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\u0016\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u0003H\u0096\u0002¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/cfg/pseudocode/AllSubtypes;", "Lorg/jetbrains/kotlin/cfg/pseudocode/TypePredicate;", "upperBound", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/KotlinType;)V", "getUpperBound", "()Lorg/jetbrains/kotlin/types/KotlinType;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "invoke", "typeToCheck", "(Lorg/jetbrains/kotlin/types/KotlinType;)Ljava/lang/Boolean;", "toString", "", "cfg"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class AllSubtypes implements TypePredicate {
    private final KotlinType upperBound;

    public static /* synthetic */ AllSubtypes copy$default(AllSubtypes allSubtypes, KotlinType kotlinType, int i, Object obj) {
        if ((i & 1) != 0) {
            kotlinType = allSubtypes.upperBound;
        }
        return allSubtypes.copy(kotlinType);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final KotlinType getUpperBound() {
        return this.upperBound;
    }

    public final AllSubtypes copy(KotlinType upperBound) {
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        return new AllSubtypes(upperBound);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof AllSubtypes) && Intrinsics.areEqual(this.upperBound, ((AllSubtypes) other).upperBound);
    }

    public int hashCode() {
        return this.upperBound.hashCode();
    }

    public AllSubtypes(KotlinType upperBound) {
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        this.upperBound = upperBound;
    }

    public final KotlinType getUpperBound() {
        return this.upperBound;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // kotlin.jvm.functions.Function1
    public Boolean invoke(KotlinType typeToCheck) {
        Intrinsics.checkNotNullParameter(typeToCheck, "typeToCheck");
        return Boolean.valueOf(KotlinTypeChecker.DEFAULT.isSubtypeOf(typeToCheck, this.upperBound));
    }

    public String toString() {
        return "{<: " + TypePredicateKt.render(this.upperBound) + '}';
    }
}
