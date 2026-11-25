package org.jetbrains.kotlin.fir.backend;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: Fir2IrDeclarationStorage.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\u000e\b\u0000\u0010\u0002\u0018\u0001*\u0006\u0012\u0002\b\u00030\u0003\"\n\b\u0001\u0010\u0004\u0018\u0001*\u00020\u0005\"\b\b\u0002\u0010\u0006*\u00020\u0007H\n¢\u0006\u0002\b\b"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "FS", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "F", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "I", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrDeclarationStorage$getIrCallableSymbol$signature$2 extends Lambda implements Function0<IdSignature> {
    final /* synthetic */ ConeClassLikeLookupTag $fakeOverrideOwnerLookupTag;

    /* JADX INFO: Incorrect field signature: TF; */
    final /* synthetic */ FirCallableDeclaration $fir;
    final /* synthetic */ Fir2IrDeclarationStorage this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Incorrect types in method signature: (Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;TF;Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;)V */
    public Fir2IrDeclarationStorage$getIrCallableSymbol$signature$2(Fir2IrDeclarationStorage fir2IrDeclarationStorage, FirCallableDeclaration firCallableDeclaration, ConeClassLikeLookupTag coneClassLikeLookupTag) {
        super(0);
        this.this$0 = fir2IrDeclarationStorage;
        this.$fir = firCallableDeclaration;
        this.$fakeOverrideOwnerLookupTag = coneClassLikeLookupTag;
    }

    @Override // kotlin.jvm.functions.Function0
    public final IdSignature invoke() {
        FirClassLikeSymbol<?> symbol;
        FirBasedSignatureComposer signatureComposer = this.this$0.getSignatureComposer();
        FirCallableDeclaration firCallableDeclaration = this.$fir;
        ConeClassLikeLookupTag coneClassLikeLookupTag = this.$fakeOverrideOwnerLookupTag;
        boolean z = false;
        if (coneClassLikeLookupTag != null && (symbol = LookupTagUtilsKt.toSymbol(coneClassLikeLookupTag, this.this$0.getSession())) != null && symbol.getRawStatus().isExpect()) {
            z = true;
        }
        return signatureComposer.composeSignature(firCallableDeclaration, coneClassLikeLookupTag, z);
    }
}
