package org.jetbrains.kotlin.fir.renderer;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.FirFileAnnotationsContainer;

/* JADX INFO: compiled from: FirFileAnnotationsContainerRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010R\u001a\u0010\u0003\u001a\u00020\u0004X\u0080.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirFileAnnotationsContainerRenderer;", "", "()V", "components", "Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "getComponents$tree", "()Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "setComponents$tree", "(Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;)V", "printer", "Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "getPrinter", "()Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "render", "", "fileAnnotationsContainer", "Lorg/jetbrains/kotlin/fir/FirFileAnnotationsContainer;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirFileAnnotationsContainerRenderer {
    public FirRendererComponents components;

    public final FirRendererComponents getComponents$tree() {
        FirRendererComponents firRendererComponents = this.components;
        if (firRendererComponents != null) {
            return firRendererComponents;
        }
        Intrinsics.throwUninitializedPropertyAccessException("components");
        return null;
    }

    public final void setComponents$tree(FirRendererComponents firRendererComponents) {
        Intrinsics.checkNotNullParameter(firRendererComponents, "<set-?>");
        this.components = firRendererComponents;
    }

    private final FirPrinter getPrinter() {
        return getComponents$tree().getPrinter();
    }

    public final void render(FirFileAnnotationsContainer fileAnnotationsContainer) {
        Intrinsics.checkNotNullParameter(fileAnnotationsContainer, "fileAnnotationsContainer");
        FirAnnotationRenderer annotationRenderer = getComponents$tree().getAnnotationRenderer();
        if (annotationRenderer != null) {
            FirAnnotationRenderer.render$default(annotationRenderer, fileAnnotationsContainer, null, 2, null);
        }
        FirResolvePhaseRenderer resolvePhaseRenderer = getComponents$tree().getResolvePhaseRenderer();
        if (resolvePhaseRenderer != null) {
            resolvePhaseRenderer.render(fileAnnotationsContainer);
        }
        getPrinter().println("annotations container");
    }
}
