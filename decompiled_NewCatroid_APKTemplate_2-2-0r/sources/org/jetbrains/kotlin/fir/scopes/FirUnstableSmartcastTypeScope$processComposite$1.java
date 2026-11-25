package org.jetbrains.kotlin.fir.scopes;

import androidx.exifinterface.media.ExifInterface;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX INFO: compiled from: FirUnstableSmartcastTypeScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\f\b\u0000\u0010\u0002*\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "it", "invoke", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)V"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class FirUnstableSmartcastTypeScope$processComposite$1<T> extends Lambda implements Function1<T, Unit> {
    final /* synthetic */ Function1<T, Unit> $processor;
    final /* synthetic */ Set<T> $unique;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FirUnstableSmartcastTypeScope$processComposite$1(Set<T> set, Function1<? super T, Unit> function1) {
        super(1);
        this.$unique = set;
        this.$processor = function1;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
        invoke((FirCallableSymbol) obj);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Incorrect types in method signature: (TT;)V */
    public final void invoke(FirCallableSymbol it) {
        Intrinsics.checkNotNullParameter(it, "it");
        this.$unique.add(it);
        this.$processor.invoke(it);
    }
}
