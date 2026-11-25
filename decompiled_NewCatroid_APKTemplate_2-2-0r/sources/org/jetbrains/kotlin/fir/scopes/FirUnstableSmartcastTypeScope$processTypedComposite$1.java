package org.jetbrains.kotlin.fir.scopes;

import androidx.exifinterface.media.ExifInterface;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.name.SpecialNames;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX INFO: compiled from: FirUnstableSmartcastTypeScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\f\b\u0001\u0010\u0003*\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0005\u001a\u0002H\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\n¢\u0006\u0004\b\b\u0010\t"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "N", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", NativeSymbol.TYPE_NAME, "firTypeScope", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "invoke", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class FirUnstableSmartcastTypeScope$processTypedComposite$1<T> extends Lambda implements Function2<T, FirTypeScope, ProcessorAction> {
    final /* synthetic */ Function2<T, FirTypeScope, ProcessorAction> $processor;
    final /* synthetic */ Set<T> $unique;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FirUnstableSmartcastTypeScope$processTypedComposite$1(Set<T> set, Function2<? super T, ? super FirTypeScope, ? extends ProcessorAction> function2) {
        super(2);
        this.$unique = set;
        this.$processor = function2;
    }

    /* JADX WARN: Incorrect types in method signature: (TT;Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction; */
    @Override // kotlin.jvm.functions.Function2
    public final ProcessorAction invoke(FirCallableSymbol symbol, FirTypeScope firTypeScope) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(firTypeScope, "firTypeScope");
        this.$unique.add(symbol);
        return this.$processor.invoke(symbol, firTypeScope);
    }
}
