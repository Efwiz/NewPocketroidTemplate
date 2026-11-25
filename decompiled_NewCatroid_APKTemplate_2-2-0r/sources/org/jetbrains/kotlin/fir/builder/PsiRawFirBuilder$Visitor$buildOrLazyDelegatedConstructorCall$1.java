package org.jetbrains.kotlin.fir.builder;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.builder.FirLazyDelegatedConstructorCallBuilder;
import org.jetbrains.kotlin.fir.references.FirSuperReference;
import org.jetbrains.kotlin.fir.references.builder.FirExplicitSuperReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirExplicitThisReferenceBuilder;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: PsiRawFirBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class PsiRawFirBuilder$Visitor$buildOrLazyDelegatedConstructorCall$1 extends Lambda implements Function0<FirDelegatedConstructorCall> {
    final /* synthetic */ FirTypeRef $constructedTypeRef;
    final /* synthetic */ boolean $isThis;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PsiRawFirBuilder$Visitor$buildOrLazyDelegatedConstructorCall$1(boolean z, FirTypeRef firTypeRef) {
        super(0);
        this.$isThis = z;
        this.$constructedTypeRef = firTypeRef;
    }

    @Override // kotlin.jvm.functions.Function0
    public final FirDelegatedConstructorCall invoke() {
        FirSuperReference firSuperReferenceBuild;
        boolean z = this.$isThis;
        FirTypeRef firTypeRef = this.$constructedTypeRef;
        FirLazyDelegatedConstructorCallBuilder firLazyDelegatedConstructorCallBuilder = new FirLazyDelegatedConstructorCallBuilder();
        firLazyDelegatedConstructorCallBuilder.setThis(z);
        firLazyDelegatedConstructorCallBuilder.setConstructedTypeRef(firTypeRef);
        if (z) {
            FirExplicitThisReferenceBuilder firExplicitThisReferenceBuilder = new FirExplicitThisReferenceBuilder();
            firExplicitThisReferenceBuilder.setSource(null);
            firSuperReferenceBuild = firExplicitThisReferenceBuilder.build();
        } else {
            FirExplicitSuperReferenceBuilder firExplicitSuperReferenceBuilder = new FirExplicitSuperReferenceBuilder();
            firExplicitSuperReferenceBuilder.setSource(null);
            firExplicitSuperReferenceBuilder.setSuperTypeRef(firTypeRef);
            firSuperReferenceBuild = firExplicitSuperReferenceBuilder.build();
        }
        firLazyDelegatedConstructorCallBuilder.setCalleeReference(firSuperReferenceBuild);
        return firLazyDelegatedConstructorCallBuilder.mo6810build();
    }
}
