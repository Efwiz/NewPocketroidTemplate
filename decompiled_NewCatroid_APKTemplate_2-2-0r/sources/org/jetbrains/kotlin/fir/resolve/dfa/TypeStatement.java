package org.jetbrains.kotlin.fir.resolve.dfa;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: statements.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016R\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\b\u0010\nR\u0011\u0010\u000b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\nR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f\u0082\u0001\u0002\u0012\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/TypeStatement;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/Statement;", "()V", "exactType", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getExactType", "()Ljava/util/Set;", "isEmpty", "", "()Z", "isNotEmpty", "variable", "Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;", "getVariable", "()Lorg/jetbrains/kotlin/fir/resolve/dfa/RealVariable;", "toString", "", "Lorg/jetbrains/kotlin/fir/resolve/dfa/MutableTypeStatement;", "Lorg/jetbrains/kotlin/fir/resolve/dfa/PersistentTypeStatement;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TypeStatement extends Statement {
    public /* synthetic */ TypeStatement(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract Set<ConeKotlinType> getExactType();

    @Override // org.jetbrains.kotlin.fir.resolve.dfa.Statement
    public abstract RealVariable getVariable();

    private TypeStatement() {
        super(null);
    }

    public final boolean isEmpty() {
        return getExactType().isEmpty();
    }

    public final boolean isNotEmpty() {
        return !isEmpty();
    }

    public String toString() {
        return getVariable() + ": " + getExactType();
    }
}
