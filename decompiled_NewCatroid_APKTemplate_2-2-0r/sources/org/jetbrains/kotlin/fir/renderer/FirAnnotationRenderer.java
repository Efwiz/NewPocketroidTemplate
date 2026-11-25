package org.jetbrains.kotlin.fir.renderer;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirAnnotationCall;
import org.jetbrains.kotlin.fir.expressions.FirCall;
import org.jetbrains.kotlin.fir.references.FirErrorNamedReference;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;

/* JADX INFO: compiled from: FirAnnotationRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u001bJ!\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u001e2\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0000¢\u0006\u0002\b\u001fJ'\u0010 \u001a\u00020\u00172\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001e0\"2\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0000¢\u0006\u0002\b#R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u00048DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\bX\u0080.¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0011\u001a\u00060\u0012R\u00020\u00138DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirAnnotationRenderer;", "", "()V", "callArgumentsRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirCallArgumentsRenderer;", "getCallArgumentsRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirCallArgumentsRenderer;", "components", "Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "getComponents$tree", "()Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "setComponents$tree", "(Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;)V", "printer", "Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "getPrinter", "()Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "visitor", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Visitor;", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer;", "getVisitor", "()Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Visitor;", "render", "", "annotationContainer", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "explicitAnnotationUseSiteTarget", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationUseSiteTarget;", "renderAnnotation", "annotation", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "renderAnnotation$tree", "renderAnnotations", "annotations", "", "renderAnnotations$tree", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirAnnotationRenderer {
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

    protected final FirRenderer.Visitor getVisitor() {
        return getComponents$tree().getVisitor();
    }

    protected final FirPrinter getPrinter() {
        return getComponents$tree().getPrinter();
    }

    protected final FirCallArgumentsRenderer getCallArgumentsRenderer() {
        return getComponents$tree().getCallArgumentsRenderer();
    }

    public static /* synthetic */ void render$default(FirAnnotationRenderer firAnnotationRenderer, FirAnnotationContainer firAnnotationContainer, AnnotationUseSiteTarget annotationUseSiteTarget, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: render");
        }
        if ((i & 2) != 0) {
            annotationUseSiteTarget = null;
        }
        firAnnotationRenderer.render(firAnnotationContainer, annotationUseSiteTarget);
    }

    public final void render(FirAnnotationContainer annotationContainer, AnnotationUseSiteTarget explicitAnnotationUseSiteTarget) {
        Intrinsics.checkNotNullParameter(annotationContainer, "annotationContainer");
        renderAnnotations$tree(annotationContainer.getAnnotations(), explicitAnnotationUseSiteTarget);
    }

    public static /* synthetic */ void renderAnnotations$tree$default(FirAnnotationRenderer firAnnotationRenderer, List list, AnnotationUseSiteTarget annotationUseSiteTarget, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: renderAnnotations");
        }
        if ((i & 2) != 0) {
            annotationUseSiteTarget = null;
        }
        firAnnotationRenderer.renderAnnotations$tree(list, annotationUseSiteTarget);
    }

    public final void renderAnnotations$tree(List<? extends FirAnnotation> annotations, AnnotationUseSiteTarget explicitAnnotationUseSiteTarget) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Iterator<? extends FirAnnotation> it = annotations.iterator();
        while (it.hasNext()) {
            renderAnnotation$tree(it.next(), explicitAnnotationUseSiteTarget);
        }
    }

    public static /* synthetic */ void renderAnnotation$tree$default(FirAnnotationRenderer firAnnotationRenderer, FirAnnotation firAnnotation, AnnotationUseSiteTarget annotationUseSiteTarget, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: renderAnnotation");
        }
        if ((i & 2) != 0) {
            annotationUseSiteTarget = null;
        }
        firAnnotationRenderer.renderAnnotation$tree(firAnnotation, annotationUseSiteTarget);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void renderAnnotation$tree(FirAnnotation annotation, AnnotationUseSiteTarget explicitAnnotationUseSiteTarget) {
        Intrinsics.checkNotNullParameter(annotation, "annotation");
        getPrinter().print(PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT);
        if (explicitAnnotationUseSiteTarget == null) {
            explicitAnnotationUseSiteTarget = annotation.getUseSiteTarget();
        }
        if (explicitAnnotationUseSiteTarget != null) {
            getPrinter().print(explicitAnnotationUseSiteTarget.name());
            getPrinter().print(":");
        }
        annotation.getAnnotationTypeRef().accept(getVisitor());
        if (annotation instanceof FirAnnotationCall) {
            FirResolvePhaseRenderer resolvePhaseRenderer = getComponents$tree().getResolvePhaseRenderer();
            if (resolvePhaseRenderer != null) {
                resolvePhaseRenderer.render((FirAnnotationCall) annotation);
            }
            FirReference calleeReference = ((FirAnnotationCall) annotation).getCalleeReference();
            if ((calleeReference instanceof FirResolvedNamedReference) || (calleeReference instanceof FirErrorNamedReference)) {
                FirCallArgumentsRenderer callArgumentsRenderer = getCallArgumentsRenderer();
                if (callArgumentsRenderer != null) {
                    callArgumentsRenderer.renderArgumentMapping(annotation.getArgumentMapping());
                }
            } else {
                getVisitor().visitCall((FirCall) annotation);
            }
        } else {
            FirCallArgumentsRenderer callArgumentsRenderer2 = getCallArgumentsRenderer();
            if (callArgumentsRenderer2 != null) {
                callArgumentsRenderer2.renderArgumentMapping(annotation.getArgumentMapping());
            }
        }
        if (annotation.getUseSiteTarget() == AnnotationUseSiteTarget.FILE) {
            getPrinter().println(new Object[0]);
        } else {
            getPrinter().print(" ");
        }
    }
}
