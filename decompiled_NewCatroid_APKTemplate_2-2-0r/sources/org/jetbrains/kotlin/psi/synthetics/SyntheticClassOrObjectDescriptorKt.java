package org.jetbrains.kotlin.psi.synthetics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;

/* JADX INFO: compiled from: SyntheticClassOrObjectDescriptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005"}, d2 = {"findClassDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/psi/KtPureElement;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SyntheticClassOrObjectDescriptorKt {
    public static final ClassDescriptor findClassDescriptor(KtPureElement ktPureElement, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(ktPureElement, "<this>");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        if (ktPureElement instanceof PsiElement) {
            Object notNull = BindingContextUtils.getNotNull(bindingContext, BindingContext.CLASS, ktPureElement);
            Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
            return (ClassDescriptor) notNull;
        }
        if (ktPureElement instanceof SyntheticClassOrObjectDescriptor.SyntheticDeclaration) {
            return ((SyntheticClassOrObjectDescriptor.SyntheticDeclaration) ktPureElement).descriptor();
        }
        throw new IllegalArgumentException(ktPureElement + " shall be PsiElement or SyntheticClassOrObjectDescriptor.SyntheticDeclaration");
    }
}
