package org.jetbrains.kotlin.fir.declarations.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.builder.FirBuilderDslKt;
import org.jetbrains.kotlin.fir.declarations.FirDanglingModifierList;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.impl.FirDanglingModifierListImpl;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.impl.FirDanglingModifierSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirDanglingModifierListBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u00102\u001a\u000203H\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u001bX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\u00020!X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001c\u0010&\u001a\u0004\u0018\u00010'X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001a\u0010,\u001a\u00020-X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010/\"\u0004\b0\u00101¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/builder/FirDanglingModifierListBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "setAttributes", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;)V", "diagnostic", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "getDiagnostic", "()Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "setDiagnostic", "(Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;)V", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "setModuleData", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "setResolvePhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirDanglingModifierSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirDanglingModifierSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirDanglingModifierSymbol;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/declarations/FirDanglingModifierList;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDanglingModifierListBuilder implements FirAnnotationContainerBuilder {
    public ConeDiagnostic diagnostic;
    public FirModuleData moduleData;
    public FirDeclarationOrigin origin;
    private KtSourceElement source;
    public FirDanglingModifierSymbol symbol;
    private FirResolvePhase resolvePhase = FirResolvePhase.RAW_FIR;
    private final List<FirAnnotation> annotations = new ArrayList();
    private FirDeclarationAttributes attributes = new FirDeclarationAttributes();

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    public final FirResolvePhase getResolvePhase() {
        return this.resolvePhase;
    }

    public final void setResolvePhase(FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firResolvePhase, "<set-?>");
        this.resolvePhase = firResolvePhase;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    public final FirModuleData getModuleData() {
        FirModuleData firModuleData = this.moduleData;
        if (firModuleData != null) {
            return firModuleData;
        }
        Intrinsics.throwUninitializedPropertyAccessException("moduleData");
        return null;
    }

    public final void setModuleData(FirModuleData firModuleData) {
        Intrinsics.checkNotNullParameter(firModuleData, "<set-?>");
        this.moduleData = firModuleData;
    }

    public final FirDeclarationOrigin getOrigin() {
        FirDeclarationOrigin firDeclarationOrigin = this.origin;
        if (firDeclarationOrigin != null) {
            return firDeclarationOrigin;
        }
        Intrinsics.throwUninitializedPropertyAccessException("origin");
        return null;
    }

    public final void setOrigin(FirDeclarationOrigin firDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(firDeclarationOrigin, "<set-?>");
        this.origin = firDeclarationOrigin;
    }

    public final FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    public final void setAttributes(FirDeclarationAttributes firDeclarationAttributes) {
        Intrinsics.checkNotNullParameter(firDeclarationAttributes, "<set-?>");
        this.attributes = firDeclarationAttributes;
    }

    public final ConeDiagnostic getDiagnostic() {
        ConeDiagnostic coneDiagnostic = this.diagnostic;
        if (coneDiagnostic != null) {
            return coneDiagnostic;
        }
        Intrinsics.throwUninitializedPropertyAccessException("diagnostic");
        return null;
    }

    public final void setDiagnostic(ConeDiagnostic coneDiagnostic) {
        Intrinsics.checkNotNullParameter(coneDiagnostic, "<set-?>");
        this.diagnostic = coneDiagnostic;
    }

    public final FirDanglingModifierSymbol getSymbol() {
        FirDanglingModifierSymbol firDanglingModifierSymbol = this.symbol;
        if (firDanglingModifierSymbol != null) {
            return firDanglingModifierSymbol;
        }
        Intrinsics.throwUninitializedPropertyAccessException(NativeSymbol.TYPE_NAME);
        return null;
    }

    public final void setSymbol(FirDanglingModifierSymbol firDanglingModifierSymbol) {
        Intrinsics.checkNotNullParameter(firDanglingModifierSymbol, "<set-?>");
        this.symbol = firDanglingModifierSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirDanglingModifierList build() {
        return new FirDanglingModifierListImpl(getSource(), this.resolvePhase, FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), getModuleData(), getOrigin(), this.attributes, getDiagnostic(), getSymbol(), null);
    }
}
