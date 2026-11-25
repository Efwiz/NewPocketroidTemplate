package org.jetbrains.kotlin.asJava.classes;

import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.LightClassUtilsKt;
import org.jetbrains.kotlin.asJava.elements.PsiElementWithOrigin;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightReferenceListBuilder;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightTypeParameterBuilder;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeParameterListOwner;

/* JADX INFO: compiled from: KtUltraLightTypeParameter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B9\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000e0\r¢\u0006\u0002\u0010\u000fJ\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0014H\u0016J\b\u0010\u001c\u001a\u00020\tH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001b\u0010\u0013\u001a\u00020\u00148BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightTypeParameter;", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightTypeParameterBuilder;", "Lorg/jetbrains/kotlin/asJava/elements/PsiElementWithOrigin;", "Lorg/jetbrains/kotlin/psi/KtTypeParameter;", "name", "", "myOwner", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterListOwner;", "myParent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "index", "", "referenceListBuilder", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/asJava/classes/KotlinLightReferenceListBuilder;", "(Ljava/lang/String;Lcom/intellij/psi/PsiTypeParameterListOwner;Lcom/intellij/psi/PsiElement;ILkotlin/jvm/functions/Function1;)V", "origin", "getOrigin", "()Lorg/jetbrains/kotlin/psi/KtTypeParameter;", "superList", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightReferenceListBuilder;", "getSuperList", "()Lcom/intellij/psi/impl/light/LightReferenceListBuilder;", "superList$delegate", "Lkotlin/Lazy;", "getContainingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getExtendsList", "getParent", "getUseScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightTypeParameter extends LightTypeParameterBuilder implements PsiElementWithOrigin<KtTypeParameter> {
    private final PsiTypeParameterListOwner myOwner;
    private final PsiElement myParent;

    /* JADX INFO: renamed from: superList$delegate, reason: from kotlin metadata */
    private final Lazy superList;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtUltraLightTypeParameter(String name2, PsiTypeParameterListOwner myOwner, PsiElement myParent, int i, final Function1<? super PsiElement, ? extends KotlinLightReferenceListBuilder> referenceListBuilder) {
        super(name2, myOwner, i);
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(myOwner, "myOwner");
        Intrinsics.checkNotNullParameter(myParent, "myParent");
        Intrinsics.checkNotNullParameter(referenceListBuilder, "referenceListBuilder");
        this.myOwner = myOwner;
        this.myParent = myParent;
        this.superList = ImplUtilsKt.lazyPub(new Function0<KotlinLightReferenceListBuilder>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightTypeParameter$superList$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KotlinLightReferenceListBuilder invoke() {
                return referenceListBuilder.invoke(this);
            }
        });
    }

    private final LightReferenceListBuilder getSuperList() {
        return (LightReferenceListBuilder) this.superList.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.PsiElementWithOrigin
    public KtTypeParameter getOrigin() {
        PsiElement unwrapped = LightClassUtilsKt.getUnwrapped(this.myOwner);
        Intrinsics.checkNotNull(unwrapped, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtTypeParameterListOwner");
        KtTypeParameter ktTypeParameter = ((KtTypeParameterListOwner) unwrapped).getTypeParameters().get(getIndex());
        Intrinsics.checkNotNullExpressionValue(ktTypeParameter, "get(...)");
        return ktTypeParameter;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightPsiClassBuilder, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public LightReferenceListBuilder getExtendsList() {
        return getSuperList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent, reason: from getter */
    public PsiElement getMyParent() {
        return this.myParent;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        PsiFile containingFile = this.myOwner.getContainingFile();
        Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
        return containingFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public SearchScope getUseScope() {
        SearchScope useScope = getOrigin().getUseScope();
        Intrinsics.checkNotNullExpressionValue(useScope, "getUseScope(...)");
        return useScope;
    }
}
