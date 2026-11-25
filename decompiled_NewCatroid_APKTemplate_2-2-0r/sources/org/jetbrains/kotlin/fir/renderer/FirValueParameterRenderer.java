package org.jetbrains.kotlin.fir.renderer;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: FirValueParameterRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!H\u0014J\u000e\u0010\"\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!J\u0014\u0010#\u001a\u00020\u001f2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020!0%R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\bX\u0080.¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0011\u001a\u0004\u0018\u00010\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0018\u0010\u0019\u001a\u00060\u001aR\u00020\u001b8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001d¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirValueParameterRenderer;", "", "()V", "annotationRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirAnnotationRenderer;", "getAnnotationRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirAnnotationRenderer;", "components", "Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "getComponents$tree", "()Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "setComponents$tree", "(Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;)V", "declarationRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirDeclarationRenderer;", "getDeclarationRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirDeclarationRenderer;", "modifierRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirModifierRenderer;", "getModifierRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirModifierRenderer;", "printer", "Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "getPrinter", "()Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "visitor", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Visitor;", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer;", "getVisitor", "()Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Visitor;", "renderDefaultValue", "", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "renderParameter", "renderParameters", "valueParameters", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirValueParameterRenderer {
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

    protected final FirPrinter getPrinter() {
        return getComponents$tree().getPrinter();
    }

    protected final FirRenderer.Visitor getVisitor() {
        return getComponents$tree().getVisitor();
    }

    private final FirAnnotationRenderer getAnnotationRenderer() {
        return getComponents$tree().getAnnotationRenderer();
    }

    protected final FirDeclarationRenderer getDeclarationRenderer() {
        return getComponents$tree().getDeclarationRenderer();
    }

    private final FirModifierRenderer getModifierRenderer() {
        return getComponents$tree().getModifierRenderer();
    }

    public final void renderParameters(List<? extends FirValueParameter> valueParameters) {
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        getPrinter().print("(");
        int i = 0;
        for (FirValueParameter firValueParameter : valueParameters) {
            int i2 = i + 1;
            if (i > 0) {
                getPrinter().print(", ");
            }
            renderParameter(firValueParameter);
            i = i2;
        }
        getPrinter().print(Constant.AFTER_QUTO);
    }

    public final void renderParameter(FirValueParameter valueParameter) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        FirDeclarationRenderer declarationRenderer = getDeclarationRenderer();
        if (declarationRenderer != null) {
            declarationRenderer.renderPhaseAndAttributes$tree(valueParameter);
        }
        FirAnnotationRenderer annotationRenderer = getAnnotationRenderer();
        if (annotationRenderer != null) {
            FirAnnotationRenderer.render$default(annotationRenderer, valueParameter, null, 2, null);
        }
        FirModifierRenderer modifierRenderer = getModifierRenderer();
        if (modifierRenderer != null) {
            modifierRenderer.renderModifiers(valueParameter);
        }
        if (!Intrinsics.areEqual(valueParameter.getName(), SpecialNames.NO_NAME_PROVIDED)) {
            getPrinter().print(valueParameter.getName().toString() + ": ");
        }
        valueParameter.getReturnTypeRef().accept(getVisitor());
        renderDefaultValue(valueParameter);
    }

    protected void renderDefaultValue(FirValueParameter valueParameter) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        FirExpression defaultValue = valueParameter.getDefaultValue();
        if (defaultValue != null) {
            getPrinter().print(" = ");
            defaultValue.accept(getVisitor());
        }
    }
}
