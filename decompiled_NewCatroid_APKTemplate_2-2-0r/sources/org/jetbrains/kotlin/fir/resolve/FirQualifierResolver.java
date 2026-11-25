package org.jetbrains.kotlin.fir.resolve;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirQualifierResolver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H&J$\u0010\b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\t\u001a\u00020\nH&¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/FirQualifierResolver;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "resolveSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "parts", "", "Lorg/jetbrains/kotlin/fir/types/FirQualifierPart;", "resolveSymbolWithPrefix", "prefix", "Lorg/jetbrains/kotlin/name/ClassId;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirQualifierResolver implements FirSessionComponent {
    public abstract FirClassifierSymbol<?> resolveSymbol(List<? extends FirQualifierPart> parts);

    public abstract FirClassifierSymbol<?> resolveSymbolWithPrefix(List<? extends FirQualifierPart> parts, ClassId prefix);
}
