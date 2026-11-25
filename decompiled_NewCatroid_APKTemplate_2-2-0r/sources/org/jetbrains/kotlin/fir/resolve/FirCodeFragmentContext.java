package org.jetbrains.kotlin.fir.resolve;

import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataContext;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: DeclarationUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R(\u0010\u0006\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/FirCodeFragmentContext;", "", "towerDataContext", "Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;", "getTowerDataContext", "()Lorg/jetbrains/kotlin/fir/declarations/FirTowerDataContext;", "variables", "", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getVariables", "()Ljava/util/Map;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface FirCodeFragmentContext {
    FirTowerDataContext getTowerDataContext();

    Map<FirBasedSymbol<?>, Set<ConeKotlinType>> getVariables();
}
