package org.jetbrains.kotlin.fir.backend.generators;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.signaturer.FirBasedSignatureComposer;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: FakeOverrideGenerator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003\"\u0010\b\u0001\u0010\u0004\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u0005\"\n\b\u0002\u0010\u0006\u0018\u0001*\u00020\u0007H\n¢\u0006\u0002\b\b"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "D", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "I", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class FakeOverrideGenerator$createFakeOverriddenIfNeeded$irDeclaration$1 extends Lambda implements Function0<IdSignature> {

    /* JADX INFO: Incorrect field signature: TD; */
    final /* synthetic */ FirCallableDeclaration $fakeOverrideFirDeclaration;
    final /* synthetic */ FakeOverrideGenerator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Incorrect types in method signature: (Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;TD;)V */
    public FakeOverrideGenerator$createFakeOverriddenIfNeeded$irDeclaration$1(FakeOverrideGenerator fakeOverrideGenerator, FirCallableDeclaration firCallableDeclaration) {
        super(0);
        this.this$0 = fakeOverrideGenerator;
        this.$fakeOverrideFirDeclaration = firCallableDeclaration;
    }

    @Override // kotlin.jvm.functions.Function0
    public final IdSignature invoke() {
        return FirBasedSignatureComposer.composeSignature$default(this.this$0.getSignatureComposer(), this.$fakeOverrideFirDeclaration, null, false, 6, null);
    }
}
