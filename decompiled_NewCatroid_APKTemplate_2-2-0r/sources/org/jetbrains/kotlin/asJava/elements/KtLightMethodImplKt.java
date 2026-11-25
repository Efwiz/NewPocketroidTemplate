package org.jetbrains.kotlin.asJava.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.classes.KtLightClass;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPsiUtil;

/* JADX INFO: compiled from: KtLightMethodImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0001\u001a\n\u0010\u0007\u001a\u00020\u0001*\u00020\u0002\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0003¨\u0006\b"}, d2 = {"isGetter", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "(Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;)Z", "isSetter", "isAccessor", "getter", "isTraitFakeOverride", "light-classes-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtLightMethodImplKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final boolean isTraitFakeOverride(KtLightMethod ktLightMethod) {
        Intrinsics.checkNotNullParameter(ktLightMethod, "<this>");
        KtDeclaration ktDeclaration = (KtDeclaration) ktLightMethod.getKtDeclaration();
        if (!(ktDeclaration instanceof KtNamedFunction) && !(ktDeclaration instanceof KtPropertyAccessor) && !(ktDeclaration instanceof KtProperty)) {
            return false;
        }
        KtClassOrObject ktClassOrObject = (KtClassOrObject) PsiTreeUtil.getParentOfType(ktDeclaration, KtClassOrObject.class);
        return (ktClassOrObject == null || ((KtClassOrObject) ((KtLightClass) ktLightMethod.getContainingClass()).getKtDeclaration()) == ktClassOrObject || !KtPsiUtil.isTrait(ktClassOrObject)) ? false : true;
    }

    public static final boolean isAccessor(KtLightMethod ktLightMethod, boolean z) {
        Intrinsics.checkNotNullParameter(ktLightMethod, "<this>");
        T kotlinOrigin = ktLightMethod.getKtDeclaration();
        KtCallableDeclaration ktCallableDeclaration = kotlinOrigin instanceof KtCallableDeclaration ? (KtCallableDeclaration) kotlinOrigin : null;
        if (ktCallableDeclaration == null) {
            return false;
        }
        if ((ktCallableDeclaration instanceof KtProperty) || (ktCallableDeclaration instanceof KtParameter)) {
            return ktLightMethod.getParameterList().getParametersCount() == (!z ? 1 : 0) + (ktCallableDeclaration.mo7393getReceiverTypeReference() != null ? 1 : 0);
        }
        return false;
    }

    public static final boolean isGetter(KtLightMethod ktLightMethod) {
        Intrinsics.checkNotNullParameter(ktLightMethod, "<this>");
        return isAccessor(ktLightMethod, true);
    }

    public static final boolean isSetter(KtLightMethod ktLightMethod) {
        Intrinsics.checkNotNullParameter(ktLightMethod, "<this>");
        return isAccessor(ktLightMethod, false);
    }
}
