package org.jetbrains.kotlin.asJava.elements;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightIdentifier;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtParameterList;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;

/* JADX INFO: compiled from: KtLightIdentifier.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B%\b\u0007\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\b\u0010\r\u001a\u00020\u0003H\u0016J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0003H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0016H\u0016J\b\u0010\u001a\u001a\u00020\u000fH\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\u0004\u0018\u00010\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/KtLightIdentifier;", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightIdentifier;", "Lorg/jetbrains/kotlin/asJava/elements/PsiElementWithOrigin;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "lightOwner", "ktDeclaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "name", "", "(Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/psi/KtDeclaration;Ljava/lang/String;)V", "origin", "getOrigin", "()Lcom/intellij/psi/PsiElement;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "getContainingFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "getParent", "getTextOffset", "", "getTextRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "hashCode", "isPhysical", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtLightIdentifier extends LightIdentifier implements PsiElementWithOrigin<PsiElement> {
    private final KtDeclaration ktDeclaration;
    private final PsiElement lightOwner;
    private final String name;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public KtLightIdentifier(PsiElement lightOwner, KtDeclaration ktDeclaration) {
        this(lightOwner, ktDeclaration, null, 4, null);
        Intrinsics.checkNotNullParameter(lightOwner, "lightOwner");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: isPhysical */
    public boolean getIsPhysical() {
        return true;
    }

    public /* synthetic */ KtLightIdentifier(PsiElement psiElement, KtDeclaration ktDeclaration, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(psiElement, ktDeclaration, (i & 4) != 0 ? ktDeclaration != null ? ktDeclaration.get_name() : null : str);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightIdentifier(PsiElement lightOwner, KtDeclaration ktDeclaration, String str) {
        super(lightOwner.getManager(), str);
        Intrinsics.checkNotNullParameter(lightOwner, "lightOwner");
        this.lightOwner = lightOwner;
        this.ktDeclaration = ktDeclaration;
        this.name = str;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.PsiElementWithOrigin
    public PsiElement getOrigin() {
        KtDeclaration ktDeclaration = this.ktDeclaration;
        if (ktDeclaration instanceof KtSecondaryConstructor) {
            return ((KtSecondaryConstructor) ktDeclaration).getConstructorKeyword();
        }
        if (ktDeclaration instanceof KtPrimaryConstructor) {
            PsiElement constructorKeyword = ((KtPrimaryConstructor) ktDeclaration).getConstructorKeyword();
            if (constructorKeyword != null) {
                return constructorKeyword;
            }
            KtParameterList valueParameterList = ((KtPrimaryConstructor) this.ktDeclaration).getValueParameterList();
            if (valueParameterList != null) {
                return valueParameterList;
            }
            KtClassOrObject containingClassOrObject = KtPsiUtilKt.getContainingClassOrObject(this.ktDeclaration);
            if (containingClassOrObject != null) {
                return containingClassOrObject.mo7392getNameIdentifier();
            }
        } else {
            if (ktDeclaration instanceof KtPropertyAccessor) {
                return ((KtPropertyAccessor) ktDeclaration).getNamePlaceholder();
            }
            if (ktDeclaration instanceof KtNamedDeclaration) {
                return ((KtNamedDeclaration) ktDeclaration).mo7392getNameIdentifier();
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightIdentifier, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return new KtLightIdentifier(getLightOwner(), this.ktDeclaration, this.name);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent, reason: from getter */
    public PsiElement getLightOwner() {
        return this.lightOwner;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiFile getContainingFile() {
        PsiFile containingFile = this.lightOwner.getContainingFile();
        Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
        return containingFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public TextRange getTextRange() {
        PsiElement origin = getOrigin();
        TextRange textRange = origin != null ? origin.getTextRange() : null;
        if (textRange != null) {
            return textRange;
        }
        TextRange EMPTY_RANGE = TextRange.EMPTY_RANGE;
        Intrinsics.checkNotNullExpressionValue(EMPTY_RANGE, "EMPTY_RANGE");
        return EMPTY_RANGE;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        PsiElement origin = getOrigin();
        if (origin != null) {
            return origin.getTextOffset();
        }
        return -1;
    }

    public boolean equals(Object other) {
        if (other != this) {
            if (other instanceof KtLightIdentifier) {
                KtLightIdentifier ktLightIdentifier = (KtLightIdentifier) other;
                if (!Intrinsics.areEqual(ktLightIdentifier.lightOwner, this.lightOwner) || !Intrinsics.areEqual(ktLightIdentifier.ktDeclaration, this.ktDeclaration) || !Intrinsics.areEqual(ktLightIdentifier.name, this.name)) {
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.lightOwner.hashCode();
    }
}
