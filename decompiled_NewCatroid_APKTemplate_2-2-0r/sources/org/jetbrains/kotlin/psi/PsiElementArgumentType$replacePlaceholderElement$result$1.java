package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;

/* JADX INFO: compiled from: createByPattern.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class PsiElementArgumentType$replacePlaceholderElement$result$1 extends FunctionReference implements Function1<PsiElement, PsiElement> {
    PsiElementArgumentType$replacePlaceholderElement$result$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "rawReplace(Lcom/intellij/psi/PsiElement;)Lcom/intellij/psi/PsiElement;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "rawReplace";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(KtExpressionImplStub.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final PsiElement invoke(PsiElement p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return ((KtExpressionImplStub) this.receiver).rawReplace(p0);
    }
}
