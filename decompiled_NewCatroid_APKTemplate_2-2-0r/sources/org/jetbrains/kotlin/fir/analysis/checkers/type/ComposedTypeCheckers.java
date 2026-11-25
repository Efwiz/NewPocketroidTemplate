package org.jetbrains.kotlin.fir.analysis.checkers.type;

import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.analysis.CheckersComponentInternal;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: ComposedTypeCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0001H\u0007R\u001e\u0010\u0003\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\b\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/checkers/type/ComposedTypeCheckers;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/TypeCheckers;", "()V", "_typeRefCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeChecker;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeRefChecker;", "typeRefCheckers", "", "getTypeRefCheckers", "()Ljava/util/Set;", "register", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ComposedTypeCheckers extends TypeCheckers {
    private final Set<FirTypeChecker<FirTypeRef>> _typeRefCheckers = new LinkedHashSet();

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.type.TypeCheckers
    public Set<FirTypeChecker<FirTypeRef>> getTypeRefCheckers() {
        return this._typeRefCheckers;
    }

    @CheckersComponentInternal
    public final void register(TypeCheckers checkers) {
        Intrinsics.checkNotNullParameter(checkers, "checkers");
        CollectionsKt.addAll(this._typeRefCheckers, checkers.getTypeRefCheckers());
    }
}
