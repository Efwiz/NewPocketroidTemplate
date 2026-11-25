package org.jetbrains.kotlin.fir.backend;

import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: Fir2IrDeclarationStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\u000e\b\u0000\u0010\u0002\u0018\u0001*\u0006\u0012\u0002\b\u00030\u0003\"\n\b\u0001\u0010\u0004\u0018\u0001*\u00020\u0005\"\b\b\u0002\u0010\u0006*\u00020\u0007H\n¢\u0006\u0002\b\b"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "FS", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "F", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "I", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class Fir2IrDeclarationStorage$getIrCallableSymbol$1$2 extends Lambda implements Function0<IdSignature> {
    final /* synthetic */ Lazy<IrDeclarationParent> $irParent$delegate;
    final /* synthetic */ Lazy<IdSignature> $signature$delegate;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public Fir2IrDeclarationStorage$getIrCallableSymbol$1$2(Lazy<? extends IrDeclarationParent> lazy, Lazy<? extends IdSignature> lazy2) {
        super(0);
        this.$irParent$delegate = lazy;
        this.$signature$delegate = lazy2;
    }

    @Override // kotlin.jvm.functions.Function0
    public final IdSignature invoke() {
        Fir2IrDeclarationStorage.m6782getIrCallableSymbol$lambda86(this.$irParent$delegate);
        return Fir2IrDeclarationStorage.m6783getIrCallableSymbol$lambda87(this.$signature$delegate);
    }
}
