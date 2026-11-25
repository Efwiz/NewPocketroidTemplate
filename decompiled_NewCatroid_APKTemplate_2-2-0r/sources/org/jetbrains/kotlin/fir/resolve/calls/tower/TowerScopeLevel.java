package org.jetbrains.kotlin.fir.resolve.calls.tower;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.resolve.calls.CallInfo;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: TowerLevels.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001:\u0001\u000eB\u0005¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0010\u0010\u0007\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\bH&J\"\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0010\u0010\u0007\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000b0\bH&J\"\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0010\u0010\u0007\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\r0\bH&¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerScopeLevel;", "", "()V", "processFunctionsByName", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/ProcessResult;", CompilerOptions.INFO, "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "processor", "Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerScopeLevel$TowerScopeLevelProcessor;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "processObjectsByName", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "TowerScopeLevelProcessor", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TowerScopeLevel {
    public abstract ProcessResult processFunctionsByName(CallInfo info, TowerScopeLevelProcessor<? super FirFunctionSymbol<?>> processor);

    public abstract ProcessResult processObjectsByName(CallInfo info, TowerScopeLevelProcessor<? super FirBasedSymbol<?>> processor);

    public abstract ProcessResult processPropertiesByName(CallInfo info, TowerScopeLevelProcessor<? super FirVariableSymbol<?>> processor);

    /* JADX INFO: compiled from: TowerLevels.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u0000*\u000e\b\u0000\u0010\u0001 \u0000*\u0006\u0012\u0002\b\u00030\u00022\u00020\u0003JI\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\u00002\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000eH&¢\u0006\u0002\u0010\u0010ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0011À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/tower/TowerScopeLevel$TowerScopeLevelProcessor;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "", "consumeCandidate", "", NativeSymbol.TYPE_NAME, "dispatchReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "givenExtensionReceiverOptions", "", "scope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "objectsByName", "", "isFromOriginalTypeInPresenceOfSmartCast", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;Lorg/jetbrains/kotlin/fir/expressions/FirExpression;Ljava/util/List;Lorg/jetbrains/kotlin/fir/scopes/FirScope;ZZ)V", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface TowerScopeLevelProcessor<T extends FirBasedSymbol<?>> {
        void consumeCandidate(T symbol, FirExpression dispatchReceiver, List<? extends FirExpression> givenExtensionReceiverOptions, FirScope scope, boolean objectsByName, boolean isFromOriginalTypeInPresenceOfSmartCast);

        static /* synthetic */ void consumeCandidate$default(TowerScopeLevelProcessor towerScopeLevelProcessor, FirBasedSymbol firBasedSymbol, FirExpression firExpression, List list, FirScope firScope, boolean z, boolean z2, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: consumeCandidate");
            }
            towerScopeLevelProcessor.consumeCandidate(firBasedSymbol, firExpression, list, firScope, (i & 16) != 0 ? false : z, (i & 32) != 0 ? false : z2);
        }
    }
}
