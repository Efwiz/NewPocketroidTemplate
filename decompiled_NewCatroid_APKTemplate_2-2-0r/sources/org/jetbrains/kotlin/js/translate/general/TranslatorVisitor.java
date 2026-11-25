package org.jetbrains.kotlin.js.translate.general;

import java.util.Iterator;
import org.jetbrains.kotlin.js.resolve.diagnostics.ErrorsJs;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationContainer;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtVisitor;

/* JADX INFO: loaded from: classes5.dex */
public abstract class TranslatorVisitor<T> extends KtVisitor<T, TranslationContext> {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "ktClass";
        } else if (i != 2) {
            objArr[0] = "expression";
        } else {
            objArr[0] = "context";
        }
        objArr[1] = "org/jetbrains/kotlin/js/translate/general/TranslatorVisitor";
        if (i == 1 || i == 2) {
            objArr[2] = "traverseContainer";
        } else {
            objArr[2] = "visitKtElement";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    protected abstract T emptyResult(TranslationContext translationContext);

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public T visitKtElement(KtElement ktElement, TranslationContext translationContext) {
        if (ktElement == null) {
            $$$reportNull$$$0(0);
        }
        translationContext.bindingTrace().report(ErrorsJs.NOT_SUPPORTED.on(ktElement, ktElement));
        return emptyResult(translationContext);
    }

    public final void traverseContainer(KtDeclarationContainer ktDeclarationContainer, TranslationContext translationContext) {
        if (ktDeclarationContainer == null) {
            $$$reportNull$$$0(1);
        }
        if (translationContext == null) {
            $$$reportNull$$$0(2);
        }
        Iterator<KtDeclaration> it = ktDeclarationContainer.getDeclarations().iterator();
        while (it.hasNext()) {
            it.next().accept(this, translationContext);
        }
    }
}
