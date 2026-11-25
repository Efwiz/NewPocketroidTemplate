package org.jetbrains.kotlin.fir.renderer;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.type.TypeDescription;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirSymbolRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\r\u001a\u00020\u000e2\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0010J\u0014\u0010\u0011\u001a\u00020\u00122\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0010H\u0014R\u001a\u0010\u0003\u001a\u00020\u0004X\u0080.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8DX\u0084\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirSymbolRenderer;", "", "()V", "components", "Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "getComponents$tree", "()Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;", "setComponents$tree", "(Lorg/jetbrains/kotlin/fir/renderer/FirRendererComponents;)V", "printer", "Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "getPrinter", "()Lorg/jetbrains/kotlin/fir/renderer/FirPrinter;", "printReference", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "renderReference", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirSymbolRenderer {
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

    public final void printReference(FirBasedSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        getPrinter().print(renderReference(symbol));
    }

    protected String renderReference(FirBasedSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        if (symbol instanceof FirCallableSymbol) {
            return ((FirCallableSymbol) symbol).getCallableId().toString();
        }
        if (!(symbol instanceof FirClassLikeSymbol)) {
            return TypeDescription.Generic.OfWildcardType.SYMBOL;
        }
        String string = ((FirClassLikeSymbol) symbol).getClassId().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
