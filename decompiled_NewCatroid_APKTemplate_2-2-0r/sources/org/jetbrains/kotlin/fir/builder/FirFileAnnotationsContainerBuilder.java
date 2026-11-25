package org.jetbrains.kotlin.fir.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.fir.FirFileAnnotationsContainer;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.impl.FirFileAnnotationsContainerImpl;
import org.jetbrains.kotlin.fir.symbols.impl.FirFileSymbol;

/* JADX INFO: compiled from: FirFileAnnotationsContainerBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010 \u001a\u00020!H\u0016R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u00020\tX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001f¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/builder/FirFileAnnotationsContainerBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "containingFileSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFileSymbol;", "getContainingFileSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirFileSymbol;", "setContainingFileSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirFileSymbol;)V", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "setModuleData", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "setResolvePhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/FirFileAnnotationsContainer;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirFileAnnotationsContainerBuilder implements FirAnnotationContainerBuilder {
    public FirFileSymbol containingFileSymbol;
    public FirModuleData moduleData;
    private KtSourceElement source;
    private FirResolvePhase resolvePhase = FirResolvePhase.RAW_FIR;
    private final List<FirAnnotation> annotations = new ArrayList();

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

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public List<FirAnnotation> getAnnotations() {
        return this.annotations;
    }

    public final FirFileSymbol getContainingFileSymbol() {
        FirFileSymbol firFileSymbol = this.containingFileSymbol;
        if (firFileSymbol != null) {
            return firFileSymbol;
        }
        Intrinsics.throwUninitializedPropertyAccessException("containingFileSymbol");
        return null;
    }

    public final void setContainingFileSymbol(FirFileSymbol firFileSymbol) {
        Intrinsics.checkNotNullParameter(firFileSymbol, "<set-?>");
        this.containingFileSymbol = firFileSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirFileAnnotationsContainer build() {
        return new FirFileAnnotationsContainerImpl(getSource(), this.resolvePhase, getModuleData(), FirBuilderDslKt.toMutableOrEmpty(getAnnotations()), getContainingFileSymbol(), null);
    }
}
