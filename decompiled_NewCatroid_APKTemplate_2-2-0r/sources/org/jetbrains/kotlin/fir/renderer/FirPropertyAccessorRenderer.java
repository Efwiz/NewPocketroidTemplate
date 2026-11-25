package org.jetbrains.kotlin.fir.renderer;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;

/* JADX INFO: compiled from: FirPropertyAccessorRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015R\u001a\u0010\u0003\u001a\u00020\u0004X\u0080.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0018\u0010\r\u001a\u00060\u000eR\u00020\u000f8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirPropertyAccessorRenderer;", "", "()V", "components", "Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "getComponents$tree", "()Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "setComponents$tree", "(Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;)V", "printer", "Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "getPrinter", "()Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "visitor", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Visitor;", "Lorg/jetbrains/kotlin/fir/renderer/FirRenderer;", "getVisitor", "()Lorg/jetbrains/kotlin/fir/renderer/FirRenderer$Visitor;", "render", "", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirPropertyAccessorRenderer {
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

    private final FirRenderer.Visitor getVisitor() {
        return getComponents$tree().getVisitor();
    }

    public final void render(FirProperty property) {
        Intrinsics.checkNotNullParameter(property, "property");
        getPrinter().println(new Object[0]);
        getPrinter().pushIndent$tree();
        if (DeclarationAttributesKt.getHasExplicitBackingField(property)) {
            FirBackingField backingField = property.getBackingField();
            if (backingField != null) {
                backingField.accept(getVisitor());
            }
            getPrinter().println(new Object[0]);
        }
        FirPropertyAccessor getter = property.getGetter();
        if (getter != null) {
            getter.accept(getVisitor());
        }
        FirPropertyAccessor getter2 = property.getGetter();
        if ((getter2 != null ? getter2.getBody() : null) == null) {
            getPrinter().println(new Object[0]);
        }
        if (property.getIsVar()) {
            FirPropertyAccessor setter = property.getSetter();
            if (setter != null) {
                setter.accept(getVisitor());
            }
            FirPropertyAccessor setter2 = property.getSetter();
            if ((setter2 != null ? setter2.getBody() : null) == null) {
                getPrinter().println(new Object[0]);
            }
        }
        getPrinter().popIndent$tree();
    }
}
