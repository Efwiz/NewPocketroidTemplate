package org.jetbrains.kotlin.asJava.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightTypeParameterListBuilder;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.idea.KotlinLanguage;

/* JADX INFO: compiled from: KotlinLightTypeParameterListBuilder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016J\n\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J*\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0012\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KotlinLightTypeParameterListBuilder;", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightTypeParameterListBuilder;", "owner", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterListOwner;", "(Lcom/intellij/psi/PsiTypeParameterListOwner;)V", "getContainingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getParent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getText", "", "processDeclarations", "", "processor", "Lorg/jetbrains/kotlin/com/intellij/psi/scope/PsiScopeProcessor;", "state", "Lorg/jetbrains/kotlin/com/intellij/psi/ResolveState;", "lastParent", "place", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinLightTypeParameterListBuilder extends LightTypeParameterListBuilder {
    private final PsiTypeParameterListOwner owner;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KotlinLightTypeParameterListBuilder(PsiTypeParameterListOwner owner) {
        super(owner.getManager(), KotlinLanguage.INSTANCE);
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.owner = owner;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(place, "place");
        PsiTypeParameter[] typeParameters = getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        for (PsiTypeParameter psiTypeParameter : typeParameters) {
            if (!processor.execute(psiTypeParameter, state)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        return this.owner;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        PsiFile containingFile = this.owner.getContainingFile();
        Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
        return containingFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String getText() {
        return "";
    }
}
