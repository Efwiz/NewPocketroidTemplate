package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import kotlin.Metadata;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;

/* JADX INFO: compiled from: CompilerRequiredAnnotationEnhancementProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH&¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/CompilerRequiredAnnotationEnhancementProvider;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "enhance", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirEnumEntrySymbol;", "enumSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "enumEntrySymbol", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class CompilerRequiredAnnotationEnhancementProvider implements FirSessionComponent {
    public abstract FirEnumEntrySymbol enhance(FirClassSymbol<?> enumSymbol, FirEnumEntrySymbol enumEntrySymbol, FirSession session);
}
