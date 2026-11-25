package org.jetbrains.kotlin.resolve.jvm.diagnostics;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.psi.KtParameter;

/* JADX INFO: compiled from: JvmDeclarationOrigin.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0016\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0012\b\u0002\u0010\b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010\t¢\u0006\u0002\u0010\u000bJ\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u00018VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u001b\u0010\b\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "", "originKind", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "parametersForJvmOverload", "", "Lorg/jetbrains/kotlin/psi/KtParameter;", "(Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Ljava/util/List;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getElement", "()Lcom/intellij/psi/PsiElement;", "getOriginKind", "()Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", "originalSourceElement", "getOriginalSourceElement", "()Ljava/lang/Object;", "getParametersForJvmOverload", "()Ljava/util/List;", "toString", "", "Companion", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class JvmDeclarationOrigin {
    public static final JvmDeclarationOrigin NO_ORIGIN = new JvmDeclarationOrigin(JvmDeclarationOriginKind.OTHER, null, null, null, 8, null);
    private final DeclarationDescriptor descriptor;
    private final PsiElement element;
    private final JvmDeclarationOriginKind originKind;
    private final List<KtParameter> parametersForJvmOverload;

    /* JADX WARN: Multi-variable type inference failed */
    public JvmDeclarationOrigin(JvmDeclarationOriginKind originKind, PsiElement psiElement, DeclarationDescriptor declarationDescriptor, List<? extends KtParameter> list) {
        Intrinsics.checkNotNullParameter(originKind, "originKind");
        this.originKind = originKind;
        this.element = psiElement;
        this.descriptor = declarationDescriptor;
        this.parametersForJvmOverload = list;
    }

    public /* synthetic */ JvmDeclarationOrigin(JvmDeclarationOriginKind jvmDeclarationOriginKind, PsiElement psiElement, DeclarationDescriptor declarationDescriptor, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(jvmDeclarationOriginKind, psiElement, declarationDescriptor, (i & 8) != 0 ? null : list);
    }

    public final JvmDeclarationOriginKind getOriginKind() {
        return this.originKind;
    }

    public final PsiElement getElement() {
        return this.element;
    }

    public final DeclarationDescriptor getDescriptor() {
        return this.descriptor;
    }

    public final List<KtParameter> getParametersForJvmOverload() {
        return this.parametersForJvmOverload;
    }

    public Object getOriginalSourceElement() {
        return this.element;
    }

    public String toString() {
        Class<?> cls;
        if (Intrinsics.areEqual(this, NO_ORIGIN)) {
            return "NO_ORIGIN";
        }
        StringBuilder sbAppend = new StringBuilder("origin=").append(this.originKind).append(" element=");
        PsiElement psiElement = this.element;
        return sbAppend.append((psiElement == null || (cls = psiElement.getClass()) == null) ? null : cls.getSimpleName()).append(" descriptor=").append(this.descriptor).toString();
    }
}
