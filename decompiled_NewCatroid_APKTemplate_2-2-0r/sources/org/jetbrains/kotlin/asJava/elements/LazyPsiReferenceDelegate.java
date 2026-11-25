package org.jetbrains.kotlin.asJava.elements;

import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.asJava.classes.ImplUtilsKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiReference;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: compiled from: KtLightPsiJavaCodeReferenceElement.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0003H\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0003H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0013\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0016¢\u0006\u0002\u0010\u0016J\u0010\u0010\u0017\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u000fH\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\r\u001a\u00020\u0003H\u0016J\b\u0010\u001b\u001a\u00020\u001aH\u0016J\n\u0010\u001c\u001a\u0004\u0018\u00010\u0003H\u0016R\u001d\u0010\u0007\u001a\u0004\u0018\u00010\u00018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/LazyPsiReferenceDelegate;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReference;", "psiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "referenceProvider", "Lkotlin/Function0;", "(Lcom/intellij/psi/PsiElement;Lkotlin/jvm/functions/Function0;)V", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "getDelegate", "()Lcom/intellij/psi/PsiReference;", "delegate$delegate", "Lkotlin/Lazy;", "bindToElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "getCanonicalText", "", "getElement", "getRangeInElement", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getVariants", "", "", "()[Ljava/lang/Object;", "handleElementRename", "newElementName", "isReferenceTo", "", "isSoft", "resolve", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class LazyPsiReferenceDelegate implements PsiReference {

    /* JADX INFO: renamed from: delegate$delegate, reason: from kotlin metadata */
    private final Lazy delegate;
    private final PsiElement psiElement;

    public LazyPsiReferenceDelegate(PsiElement psiElement, Function0<? extends PsiReference> referenceProvider) {
        Intrinsics.checkNotNullParameter(psiElement, "psiElement");
        Intrinsics.checkNotNullParameter(referenceProvider, "referenceProvider");
        this.psiElement = psiElement;
        this.delegate = ImplUtilsKt.lazyPub(referenceProvider);
    }

    private final PsiReference getDelegate() {
        return (PsiReference) this.delegate.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    /* JADX INFO: renamed from: getElement, reason: from getter */
    public PsiElement getPsiElement() {
        return this.psiElement;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement resolve() {
        PsiReference delegate = getDelegate();
        if (delegate != null) {
            return delegate.resolve();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public TextRange getRangeInElement() {
        PsiReference delegate = getDelegate();
        TextRange rangeInElement = delegate != null ? delegate.getRangeInElement() : null;
        if (rangeInElement != null) {
            return rangeInElement;
        }
        TextRange textRange = this.psiElement.getTextRange();
        Intrinsics.checkNotNullExpressionValue(textRange, "getTextRange(...)");
        return textRange;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public String getCanonicalText() {
        PsiReference delegate = getDelegate();
        String canonicalText = delegate != null ? delegate.getCanonicalText() : null;
        return canonicalText == null ? "<no-text>" : canonicalText;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        Intrinsics.checkNotNullParameter(newElementName, "newElementName");
        PsiReference delegate = getDelegate();
        PsiElement psiElementHandleElementRename = delegate != null ? delegate.handleElementRename(newElementName) : null;
        return psiElementHandleElementRename == null ? getPsiElement() : psiElementHandleElementRename;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public PsiElement bindToElement(PsiElement element) throws IncorrectOperationException {
        Intrinsics.checkNotNullParameter(element, "element");
        PsiReference delegate = getDelegate();
        PsiElement psiElementBindToElement = delegate != null ? delegate.bindToElement(element) : null;
        if (psiElementBindToElement != null) {
            return psiElementBindToElement;
        }
        throw new IncorrectOperationException("can't rename LazyPsiReferenceDelegate");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isSoft() {
        PsiReference delegate = getDelegate();
        if (delegate != null) {
            return delegate.isSoft();
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public boolean isReferenceTo(PsiElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        PsiReference delegate = getDelegate();
        if (delegate != null) {
            return delegate.isReferenceTo(element);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiReference
    public Object[] getVariants() {
        PsiReference delegate = getDelegate();
        Object[] variants = delegate != null ? delegate.getVariants() : null;
        return variants == null ? new Object[0] : variants;
    }
}
