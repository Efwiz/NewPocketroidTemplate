package org.jetbrains.kotlin.fir.scopes.impl;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: Add missing generic type declarations: [S] */
/* JADX INFO: compiled from: FirAbstractImportingScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\u0010\b\u0001\u0010\u0004*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00052\u0006\u0010\u0006\u001a\u0002H\u0004H\n¢\u0006\u0004\b\u0007\u0010\b"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "D", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "it", "invoke", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)V"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class FirAbstractImportingScope$processCallablesFromImportsByName$1<S> extends Lambda implements Function1<S, Unit> {
    final /* synthetic */ Function2<S, ClassId, S> $buildImportedCopy;
    final /* synthetic */ ClassId $parentClassId;
    final /* synthetic */ Function1<S, Unit> $processor;
    final /* synthetic */ FirClassSymbol<?> $staticsScopeOwnerSymbol;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public FirAbstractImportingScope$processCallablesFromImportsByName$1(FirClassSymbol<?> firClassSymbol, Function1<? super S, Unit> function1, Function2<? super S, ? super ClassId, ? extends S> function2, ClassId classId) {
        super(1);
        this.$staticsScopeOwnerSymbol = firClassSymbol;
        this.$processor = function1;
        this.$buildImportedCopy = function2;
        this.$parentClassId = classId;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
        invoke((FirCallableSymbol) obj);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Incorrect types in method signature: (TS;)V */
    public final void invoke(FirCallableSymbol it) {
        Intrinsics.checkNotNullParameter(it, "it");
        if (it.getRawStatus().isStatic() || this.$staticsScopeOwnerSymbol.getClassKind() == ClassKind.OBJECT) {
            this.$processor.invoke(this.$buildImportedCopy.invoke(it, this.$parentClassId));
        } else {
            this.$processor.invoke(it);
        }
    }
}
