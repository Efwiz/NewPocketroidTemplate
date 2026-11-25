package org.jetbrains.kotlin.fir.analysis.jvm.checkers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDynamicUnsupportedChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.type.FirTypeChecker;
import org.jetbrains.kotlin.fir.analysis.checkers.type.TypeCheckers;
import org.jetbrains.kotlin.fir.analysis.jvm.checkers.type.FirFunctionalTypeParameterNameChecker;
import org.jetbrains.kotlin.fir.types.FirTypeRef;

/* JADX INFO: compiled from: JvmTypeCheckers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R$\u0010\u0003\u001a\u0012\u0012\u000e\u0012\f\u0012\u0004\u0012\u00020\u00060\u0005j\u0002`\u00070\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/jvm/checkers/JvmTypeCheckers;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/TypeCheckers;", "()V", "typeRefCheckers", "", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeChecker;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "Lorg/jetbrains/kotlin/fir/analysis/checkers/type/FirTypeRefChecker;", "getTypeRefCheckers", "()Ljava/util/Set;", "checkers.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmTypeCheckers extends TypeCheckers {
    public static final JvmTypeCheckers INSTANCE = new JvmTypeCheckers();
    private static final Set<FirTypeChecker<FirTypeRef>> typeRefCheckers = SetsKt.setOf((Object[]) new FirTypeChecker[]{FirDynamicUnsupportedChecker.INSTANCE, FirFunctionalTypeParameterNameChecker.INSTANCE});

    private JvmTypeCheckers() {
    }

    @Override // org.jetbrains.kotlin.fir.analysis.checkers.type.TypeCheckers
    public Set<FirTypeChecker<FirTypeRef>> getTypeRefCheckers() {
        return typeRefCheckers;
    }
}
