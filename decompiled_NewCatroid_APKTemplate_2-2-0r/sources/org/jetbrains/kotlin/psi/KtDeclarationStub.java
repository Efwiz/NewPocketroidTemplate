package org.jetbrains.kotlin.psi;

import java.util.concurrent.atomic.AtomicLong;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.IStubElementType;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.kdoc.psi.api.KDoc;
import org.jetbrains.kotlin.psi.findDocComment.FindDocCommentKt;
import org.jetbrains.kotlin.psi.stubs.KotlinClassOrObjectStub;

/* JADX INFO: loaded from: classes6.dex */
public abstract class KtDeclarationStub<T extends StubElement<?>> extends KtModifierListOwnerStub<T> implements KtDeclaration {
    private final AtomicLong modificationStamp;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 3 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 3 ? 3 : 2];
        if (i == 1) {
            objArr[0] = "nodeType";
        } else if (i == 2) {
            objArr[0] = "node";
        } else if (i != 3) {
            objArr[0] = "stub";
        } else {
            objArr[0] = "org/jetbrains/kotlin/psi/KtDeclarationStub";
        }
        if (i != 3) {
            objArr[1] = "org/jetbrains/kotlin/psi/KtDeclarationStub";
        } else {
            objArr[1] = "getNavigationElement";
        }
        if (i != 3) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 3) {
            throw new IllegalStateException(str2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDeclarationStub(T t, IStubElementType iStubElementType) {
        super(t, iStubElementType);
        if (t == null) {
            $$$reportNull$$$0(0);
        }
        if (iStubElementType == null) {
            $$$reportNull$$$0(1);
        }
        this.modificationStamp = new AtomicLong();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtDeclarationStub(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(2);
        }
        this.modificationStamp = new AtomicLong();
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement
    public void subtreeChanged() {
        super.subtreeChanged();
        this.modificationStamp.getAndIncrement();
    }

    public long getModificationStamp() {
        return this.modificationStamp.get();
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclaration
    public KDoc getDocComment() {
        return FindDocCommentKt.findDocComment(this);
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getLightParent() {
        T stub = getStub();
        if (stub != null && (!(stub instanceof KotlinClassOrObjectStub) || !((KotlinClassOrObjectStub) stub).getIsLocal())) {
            return stub.getParentStub().getPsi();
        }
        return super.getLightParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getOriginalElement() {
        KotlinDeclarationNavigationPolicy kotlinDeclarationNavigationPolicy = (KotlinDeclarationNavigationPolicy) ApplicationManager.getApplication().getService(KotlinDeclarationNavigationPolicy.class);
        return kotlinDeclarationNavigationPolicy != null ? kotlinDeclarationNavigationPolicy.getOriginalElement(this) : this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        KotlinDeclarationNavigationPolicy kotlinDeclarationNavigationPolicy = (KotlinDeclarationNavigationPolicy) ApplicationManager.getApplication().getService(KotlinDeclarationNavigationPolicy.class);
        KtElement navigationElement = kotlinDeclarationNavigationPolicy != null ? kotlinDeclarationNavigationPolicy.getNavigationElement(this) : this;
        if (navigationElement == null) {
            $$$reportNull$$$0(3);
        }
        return navigationElement;
    }
}
