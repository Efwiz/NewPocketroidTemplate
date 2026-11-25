package org.jetbrains.kotlin.fir.resolve.dfa;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet;

/* JADX INFO: compiled from: model.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/PersistentTypeStatement;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/TypeStatement;", "variable", "Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;", "exactType", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;Lkotlinx/collections/immutable/PersistentSet;)V", "getExactType", "()Lkotlinx/collections/immutable/PersistentSet;", "getVariable", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class PersistentTypeStatement extends TypeStatement {
    private final PersistentSet<ConeKotlinType> exactType;
    private final RealVariable variable;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ PersistentTypeStatement copy$default(PersistentTypeStatement persistentTypeStatement, RealVariable realVariable, PersistentSet persistentSet, int i, Object obj) {
        if ((i & 1) != 0) {
            realVariable = persistentTypeStatement.variable;
        }
        if ((i & 2) != 0) {
            persistentSet = persistentTypeStatement.exactType;
        }
        return persistentTypeStatement.copy(realVariable, persistentSet);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final RealVariable getVariable() {
        return this.variable;
    }

    public final PersistentSet<ConeKotlinType> component2() {
        return this.exactType;
    }

    public final PersistentTypeStatement copy(RealVariable variable, PersistentSet<? extends ConeKotlinType> exactType) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(exactType, "exactType");
        return new PersistentTypeStatement(variable, exactType);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PersistentTypeStatement)) {
            return false;
        }
        PersistentTypeStatement persistentTypeStatement = (PersistentTypeStatement) other;
        return Intrinsics.areEqual(this.variable, persistentTypeStatement.variable) && Intrinsics.areEqual(this.exactType, persistentTypeStatement.exactType);
    }

    public int hashCode() {
        return (this.variable.hashCode() * 31) + this.exactType.hashCode();
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.TypeStatement
    public String toString() {
        return "PersistentTypeStatement(variable=" + this.variable + ", exactType=" + this.exactType + ')';
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.Statement
    public RealVariable getVariable() {
        return this.variable;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.TypeStatement
    public PersistentSet<ConeKotlinType> getExactType() {
        return this.exactType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public PersistentTypeStatement(RealVariable variable, PersistentSet<? extends ConeKotlinType> exactType) {
        super(null);
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(exactType, "exactType");
        this.variable = variable;
        this.exactType = exactType;
    }
}
