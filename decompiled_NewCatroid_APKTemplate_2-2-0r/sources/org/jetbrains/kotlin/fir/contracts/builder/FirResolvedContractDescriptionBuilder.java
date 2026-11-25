package org.jetbrains.kotlin.fir.contracts.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.contracts.FirContractElementDeclaration;
import org.jetbrains.kotlin.fir.contracts.FirEffectDeclaration;
import org.jetbrains.kotlin.fir.contracts.FirResolvedContractDescription;
import org.jetbrains.kotlin.fir.contracts.impl.FirResolvedContractDescriptionImpl;

/* JADX INFO: compiled from: FirResolvedContractDescriptionBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0011\u001a\u00020\u0012R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0007¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/contracts/builder/FirResolvedContractDescriptionBuilder;", "", "()V", "effects", "", "Lorg/jetbrains/kotlin/fir/contracts/FirEffectDeclaration;", "getEffects", "()Ljava/util/List;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "unresolvedEffects", "Lorg/jetbrains/kotlin/fir/contracts/FirContractElementDeclaration;", "getUnresolvedEffects", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/contracts/FirResolvedContractDescription;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirResolvedContractDescriptionBuilder {
    private KtSourceElement source;
    private final List<FirEffectDeclaration> effects = new ArrayList();
    private final List<FirContractElementDeclaration> unresolvedEffects = new ArrayList();

    public final KtSourceElement getSource() {
        return this.source;
    }

    public final void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    public final List<FirEffectDeclaration> getEffects() {
        return this.effects;
    }

    public final List<FirContractElementDeclaration> getUnresolvedEffects() {
        return this.unresolvedEffects;
    }

    public final FirResolvedContractDescription build() {
        return new FirResolvedContractDescriptionImpl(this.source, this.effects, this.unresolvedEffects);
    }
}
