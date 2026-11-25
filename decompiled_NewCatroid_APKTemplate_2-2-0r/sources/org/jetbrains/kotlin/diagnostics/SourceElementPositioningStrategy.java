package org.jetbrains.kotlin.diagnostics;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.AbstractKtSourceElement;
import org.jetbrains.kotlin.KtLightSourceElement;
import org.jetbrains.kotlin.KtPsiSourceElement;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: compiled from: SourceElementPositioningStrategy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0018\u0010\u0012\u001a\u00020\n*\u0006\u0012\u0002\b\u00030\u00052\u0006\u0010\u0013\u001a\u00020\u0014H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/SourceElementPositioningStrategy;", "Lorg/jetbrains/kotlin/diagnostics/AbstractSourceElementPositioningStrategy;", "lightTreeStrategy", "Lorg/jetbrains/kotlin/diagnostics/LightTreePositioningStrategy;", "psiStrategy", "Lorg/jetbrains/kotlin/diagnostics/PositioningStrategy;", "offsetsOnlyPositioningStrategy", "Lorg/jetbrains/kotlin/diagnostics/OffsetsOnlyPositioningStrategy;", "(Lorg/jetbrains/kotlin/diagnostics/LightTreePositioningStrategy;Lorg/jetbrains/kotlin/diagnostics/PositioningStrategy;Lorg/jetbrains/kotlin/diagnostics/OffsetsOnlyPositioningStrategy;)V", "isValid", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "markDiagnostic", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnostic;", "hackyIsValid", "psi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourceElementPositioningStrategy extends AbstractSourceElementPositioningStrategy {
    private final LightTreePositioningStrategy lightTreeStrategy;
    private final OffsetsOnlyPositioningStrategy offsetsOnlyPositioningStrategy;
    private final PositioningStrategy<?> psiStrategy;

    public /* synthetic */ SourceElementPositioningStrategy(LightTreePositioningStrategy lightTreePositioningStrategy, PositioningStrategy positioningStrategy, OffsetsOnlyPositioningStrategy offsetsOnlyPositioningStrategy, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(lightTreePositioningStrategy, positioningStrategy, (i & 4) != 0 ? new OffsetsOnlyPositioningStrategy() : offsetsOnlyPositioningStrategy);
    }

    public SourceElementPositioningStrategy(LightTreePositioningStrategy lightTreeStrategy, PositioningStrategy<?> psiStrategy, OffsetsOnlyPositioningStrategy offsetsOnlyPositioningStrategy) {
        Intrinsics.checkNotNullParameter(lightTreeStrategy, "lightTreeStrategy");
        Intrinsics.checkNotNullParameter(psiStrategy, "psiStrategy");
        Intrinsics.checkNotNullParameter(offsetsOnlyPositioningStrategy, "offsetsOnlyPositioningStrategy");
        this.lightTreeStrategy = lightTreeStrategy;
        this.psiStrategy = psiStrategy;
        this.offsetsOnlyPositioningStrategy = offsetsOnlyPositioningStrategy;
    }

    @Override // org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy
    public List<TextRange> markDiagnostic(KtDiagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        AbstractKtSourceElement element = diagnostic.getElement();
        if (element instanceof KtPsiSourceElement) {
            return this.psiStrategy.markDiagnostic(diagnostic);
        }
        return element instanceof KtLightSourceElement ? this.lightTreeStrategy.markKtDiagnostic((KtSourceElement) element, diagnostic) : this.offsetsOnlyPositioningStrategy.markKtDiagnostic(element, diagnostic);
    }

    @Override // org.jetbrains.kotlin.diagnostics.AbstractSourceElementPositioningStrategy
    public boolean isValid(AbstractKtSourceElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (element instanceof KtPsiSourceElement) {
            return hackyIsValid(this.psiStrategy, ((KtPsiSourceElement) element).getPsi());
        }
        if (!(element instanceof KtLightSourceElement)) {
            return true;
        }
        KtLightSourceElement ktLightSourceElement = (KtLightSourceElement) element;
        return this.lightTreeStrategy.isValid(ktLightSourceElement.getLighterASTNode(), ktLightSourceElement.getTreeStructure());
    }

    private final boolean hackyIsValid(PositioningStrategy<?> positioningStrategy, PsiElement psiElement) {
        Intrinsics.checkNotNull(positioningStrategy, "null cannot be cast to non-null type org.jetbrains.kotlin.diagnostics.PositioningStrategy<com.intellij.psi.PsiElement>");
        return positioningStrategy.isValid(psiElement);
    }
}
