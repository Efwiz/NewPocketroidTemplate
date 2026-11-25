package org.jetbrains.kotlin.fir.renderer;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirDelegatedConstructorCall;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirLazyBlock;
import org.jetbrains.kotlin.fir.expressions.FirStatement;

/* JADX INFO: compiled from: FirBodyRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u001bJ \u0010\u001c\u001a\u00020\u00172\b\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u000e\b\u0002\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 J\u0010\u0010\"\u001a\u00020\u00172\b\u0010#\u001a\u0004\u0018\u00010$R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\bX\u0080.¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0011\u001a\u00060\u0012R\u00020\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirBodyRenderer;", "", "()V", "annotationRenderer", "Lorg/jetbrains/kotlin/fir/renderer/FirAnnotationRenderer;", "getAnnotationRenderer", "()Lorg/jetbrains/kotlin/fir/renderer/FirAnnotationRenderer;", "components", "Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "getComponents$tree", "()Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "setComponents$tree", "(Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;)V", "printer", "Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "getPrinter", "()Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "visitor", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Visitor;", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer;", "getVisitor", "()Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Visitor;", "render", "", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "variable", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "renderBody", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "additionalStatements", "", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "renderDelegatedConstructor", "delegatedConstructor", "Lorg/jetbrains/kotlin/fir/expressions/FirDelegatedConstructorCall;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirBodyRenderer {
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

    private final FirAnnotationRenderer getAnnotationRenderer() {
        return getComponents$tree().getAnnotationRenderer();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirRenderer.Visitor getVisitor() {
        return getComponents$tree().getVisitor();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirPrinter getPrinter() {
        return getComponents$tree().getPrinter();
    }

    public final void render(FirFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        renderBody$default(this, function.getBody(), null, 2, null);
    }

    public final void render(FirVariable variable) {
        Intrinsics.checkNotNullParameter(variable, "variable");
        FirExpression initializer = variable.getInitializer();
        if (initializer != null) {
            getPrinter().print(" = ");
            initializer.accept(getVisitor());
        }
        FirExpression delegate = variable.getDelegate();
        if (delegate != null) {
            getPrinter().print("by ");
            delegate.accept(getVisitor());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void renderBody$default(FirBodyRenderer firBodyRenderer, FirBlock firBlock, List list, int i, Object obj) {
        if ((i & 2) != 0) {
            list = CollectionsKt.emptyList();
        }
        firBodyRenderer.renderBody(firBlock, list);
    }

    public final void renderBody(final FirBlock block, final List<? extends FirStatement> additionalStatements) {
        Intrinsics.checkNotNullParameter(additionalStatements, "additionalStatements");
        if (block == null) {
            return;
        }
        if (block instanceof FirLazyBlock) {
            getPrinter().println(" { LAZY_BLOCK }");
            return;
        }
        FirAnnotationRenderer annotationRenderer = getAnnotationRenderer();
        if (annotationRenderer != null) {
            FirAnnotationRenderer.render$default(annotationRenderer, block, null, 2, null);
        }
        FirPrinter.renderInBraces$default(getPrinter(), null, null, new Function0<Unit>() { // from class: org.jetbrains.kotlin.fir.renderer.FirBodyRenderer.renderBody.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                Iterator it = CollectionsKt.plus((Collection) additionalStatements, (Iterable) block.getStatements()).iterator();
                while (it.hasNext()) {
                    ((FirStatement) it.next()).accept(this.getVisitor());
                    this.getPrinter().println(new Object[0]);
                }
            }
        }, 3, null);
    }

    public final void renderDelegatedConstructor(final FirDelegatedConstructorCall delegatedConstructor) {
        if (delegatedConstructor != null) {
            FirPrinter.renderInBraces$default(getPrinter(), null, null, new Function0<Unit>() { // from class: org.jetbrains.kotlin.fir.renderer.FirBodyRenderer.renderDelegatedConstructor.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    delegatedConstructor.accept(this.getVisitor());
                    this.getPrinter().println(new Object[0]);
                }
            }, 3, null);
        } else {
            getPrinter().println(new Object[0]);
        }
    }
}
