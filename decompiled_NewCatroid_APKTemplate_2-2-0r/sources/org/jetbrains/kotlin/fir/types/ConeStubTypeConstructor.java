package org.jetbrains.kotlin.fir.types;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;

/* JADX INFO: compiled from: ConeTypeVariableAndStubTypes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\t\u0010\r\u001a\u00020\u0005HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00052\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeStubTypeConstructor;", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "variable", "Lorg/jetbrains/kotlin/fir/types/ConeTypeVariable;", "isTypeVariableInSubtyping", "", "isForFixation", "(Lorg/jetbrains/kotlin/fir/types/ConeTypeVariable;ZZ)V", "()Z", "getVariable", "()Lorg/jetbrains/kotlin/fir/types/ConeTypeVariable;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ConeStubTypeConstructor implements TypeConstructorMarker {
    private final boolean isForFixation;
    private final boolean isTypeVariableInSubtyping;
    private final ConeTypeVariable variable;

    public static /* synthetic */ ConeStubTypeConstructor copy$default(ConeStubTypeConstructor coneStubTypeConstructor, ConeTypeVariable coneTypeVariable, boolean z, boolean z2, int i, Object obj) {
        if ((i & 1) != 0) {
            coneTypeVariable = coneStubTypeConstructor.variable;
        }
        if ((i & 2) != 0) {
            z = coneStubTypeConstructor.isTypeVariableInSubtyping;
        }
        if ((i & 4) != 0) {
            z2 = coneStubTypeConstructor.isForFixation;
        }
        return coneStubTypeConstructor.copy(coneTypeVariable, z, z2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ConeTypeVariable getVariable() {
        return this.variable;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getIsTypeVariableInSubtyping() {
        return this.isTypeVariableInSubtyping;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getIsForFixation() {
        return this.isForFixation;
    }

    public final ConeStubTypeConstructor copy(ConeTypeVariable variable, boolean isTypeVariableInSubtyping, boolean isForFixation) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        return new ConeStubTypeConstructor(variable, isTypeVariableInSubtyping, isForFixation);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ConeStubTypeConstructor)) {
            return false;
        }
        ConeStubTypeConstructor coneStubTypeConstructor = (ConeStubTypeConstructor) other;
        return Intrinsics.areEqual(this.variable, coneStubTypeConstructor.variable) && this.isTypeVariableInSubtyping == coneStubTypeConstructor.isTypeVariableInSubtyping && this.isForFixation == coneStubTypeConstructor.isForFixation;
    }

    public int hashCode() {
        return (((this.variable.hashCode() * 31) + Boolean.hashCode(this.isTypeVariableInSubtyping)) * 31) + Boolean.hashCode(this.isForFixation);
    }

    public ConeStubTypeConstructor(ConeTypeVariable variable, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        this.variable = variable;
        this.isTypeVariableInSubtyping = z;
        this.isForFixation = z2;
    }

    public /* synthetic */ ConeStubTypeConstructor(ConeTypeVariable coneTypeVariable, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(coneTypeVariable, z, (i & 4) != 0 ? false : z2);
    }

    public final ConeTypeVariable getVariable() {
        return this.variable;
    }

    public final boolean isTypeVariableInSubtyping() {
        return this.isTypeVariableInSubtyping;
    }

    public final boolean isForFixation() {
        return this.isForFixation;
    }

    public String toString() {
        return "Stub(" + this.variable.getTypeConstructor().getDebugName() + ')';
    }
}
