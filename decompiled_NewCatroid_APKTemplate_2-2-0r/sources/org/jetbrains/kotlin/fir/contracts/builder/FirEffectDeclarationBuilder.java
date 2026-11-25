package org.jetbrains.kotlin.fir.contracts.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.contracts.description.KtEffectDeclaration;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.contracts.FirEffectDeclaration;
import org.jetbrains.kotlin.fir.contracts.impl.FirEffectDeclarationImpl;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;

/* JADX INFO: compiled from: FirEffectDeclarationBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0012\u001a\u00020\u0013R*\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u0002`\u0007X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/contracts/builder/FirEffectDeclarationBuilder;", "", "()V", "effect", "Lorg/jetbrains/kotlin/contracts/description/KtEffectDeclaration;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeEffectDeclaration;", "getEffect", "()Lorg/jetbrains/kotlin/contracts/description/KtEffectDeclaration;", "setEffect", "(Lorg/jetbrains/kotlin/contracts/description/KtEffectDeclaration;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/contracts/FirEffectDeclaration;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirEffectDeclarationBuilder {
    public KtEffectDeclaration<ConeKotlinType, ConeDiagnostic> effect;
    private KtSourceElement source;

    public final KtSourceElement getSource() {
        return this.source;
    }

    public final void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    public final KtEffectDeclaration<ConeKotlinType, ConeDiagnostic> getEffect() {
        KtEffectDeclaration<ConeKotlinType, ConeDiagnostic> ktEffectDeclaration = this.effect;
        if (ktEffectDeclaration != null) {
            return ktEffectDeclaration;
        }
        Intrinsics.throwUninitializedPropertyAccessException("effect");
        return null;
    }

    public final void setEffect(KtEffectDeclaration<ConeKotlinType, ConeDiagnostic> ktEffectDeclaration) {
        Intrinsics.checkNotNullParameter(ktEffectDeclaration, "<set-?>");
        this.effect = ktEffectDeclaration;
    }

    public final FirEffectDeclaration build() {
        return new FirEffectDeclarationImpl(this.source, getEffect());
    }
}
