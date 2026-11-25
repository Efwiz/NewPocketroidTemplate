package org.jetbrains.kotlin.fir.resolve.dfa;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: model.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/MutableTypeStatement;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/TypeStatement;", "variable", "Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;", "exactType", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "(Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;Ljava/util/Set;)V", "getExactType", "()Ljava/util/Set;", "getVariable", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MutableTypeStatement extends TypeStatement {
    private final Set<ConeKotlinType> exactType;
    private final RealVariable variable;

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.Statement
    public RealVariable getVariable() {
        return this.variable;
    }

    public /* synthetic */ MutableTypeStatement(RealVariable realVariable, LinkedHashSet linkedHashSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(realVariable, (i & 2) != 0 ? new LinkedHashSet() : linkedHashSet);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.TypeStatement
    public Set<ConeKotlinType> getExactType() {
        return this.exactType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MutableTypeStatement(RealVariable variable, Set<ConeKotlinType> exactType) {
        super(null);
        Intrinsics.checkNotNullParameter(variable, "variable");
        Intrinsics.checkNotNullParameter(exactType, "exactType");
        this.variable = variable;
        this.exactType = exactType;
    }
}
