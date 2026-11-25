package org.jetbrains.kotlin.fir.renderer;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;

/* JADX INFO: compiled from: FirClassMemberRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0080.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0018\u0010\r\u001a\u00060\u000eR\u00020\u000f8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirClassMemberRenderer;", "", "()V", "components", "Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "getComponents$tree", "()Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "setComponents$tree", "(Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;)V", "printer", "Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "getPrinter", "()Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "visitor", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Visitor;", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer;", "getVisitor", "()Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Visitor;", "render", "", "declarations", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirClassMemberRenderer {
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

    public void render(FirRegularClass regularClass) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        render(regularClass.getDeclarations());
    }

    public void render(final List<? extends FirDeclaration> declarations) {
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        FirPrinter.renderInBraces$default(getPrinter(), null, null, new Function0<Unit>() { // from class: org.jetbrains.kotlin.fir.renderer.FirClassMemberRenderer.render.1
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
                Iterator<FirDeclaration> it = declarations.iterator();
                while (it.hasNext()) {
                    it.next().accept(this.getVisitor());
                    this.getPrinter().println(new Object[0]);
                }
            }
        }, 3, null);
    }
}
