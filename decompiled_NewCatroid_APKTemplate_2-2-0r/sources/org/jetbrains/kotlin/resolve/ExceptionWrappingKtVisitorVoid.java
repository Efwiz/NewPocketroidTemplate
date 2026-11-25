package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.util.KotlinFrontEndException;

/* JADX INFO: compiled from: ExceptionWrappingKtVisitorVoid.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0010\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\nH\u0016R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/ExceptionWrappingKtVisitorVoid;", "Lorg/jetbrains/kotlin/psi/KtVisitorVoid;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "(Lorg/jetbrains/kotlin/psi/KtVisitorVoid;)V", "visitDeclaration", "", "dcl", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExceptionWrappingKtVisitorVoid extends KtVisitorVoid {
    private final KtVisitorVoid delegate;

    public ExceptionWrappingKtVisitorVoid(KtVisitorVoid delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor
    public void visitElement(PsiElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        element.accept(this.delegate);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitDeclaration(KtDeclaration dcl) {
        String name2;
        Intrinsics.checkNotNullParameter(dcl, "dcl");
        try {
            dcl.accept(this.delegate);
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (KotlinFrontEndException e2) {
            throw e2;
        } catch (Throwable th) {
            try {
                name2 = dcl.get_name();
            } catch (Throwable th2) {
                name2 = "- error: " + th2.getMessage();
            }
            throw new KotlinFrontEndException("Failed to analyze declaration " + name2, th, dcl);
        }
    }
}
