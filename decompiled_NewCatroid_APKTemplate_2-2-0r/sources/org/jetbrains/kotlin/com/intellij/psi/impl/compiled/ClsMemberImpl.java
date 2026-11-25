package org.jetbrains.kotlin.com.intellij.psi.impl.compiled;

import java.util.function.Supplier;
import org.jetbrains.kotlin.com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiMemberStub;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ClsMemberImpl<T extends PsiMemberStub> extends ClsRepositoryPsiElement<T> implements PsiDocCommentOwner, PsiNameIdentifierOwner {
    private final NotNullLazyValue<PsiDocComment> myDocComment;
    private final NotNullLazyValue<PsiIdentifier> myNameIdentifier;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 2 ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[i != 2 ? 2 : 3];
        if (i != 2) {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsMemberImpl";
        } else {
            objArr[0] = "name";
        }
        if (i == 1) {
            objArr[1] = "getName";
        } else if (i != 2) {
            objArr[1] = "getNameIdentifier";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsMemberImpl";
        }
        if (i == 2) {
            objArr[2] = "setName";
        }
        String str2 = String.format(str, objArr);
        if (i == 2) {
            throw new IllegalArgumentException(str2);
        }
    }

    protected ClsMemberImpl(T t) {
        super(t);
        this.myDocComment = !t.isDeprecated() ? null : NotNullLazyValue.atomicLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsMemberImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6589x12a3069b();
            }
        });
        this.myNameIdentifier = NotNullLazyValue.atomicLazy(new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsMemberImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6590xee64825c();
            }
        });
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsMemberImpl, reason: not valid java name */
    /* synthetic */ PsiDocComment m6589x12a3069b() {
        return new ClsDocCommentImpl(this);
    }

    /* JADX INFO: renamed from: lambda$new$1$org-jetbrains-kotlin-com-intellij-psi-impl-compiled-ClsMemberImpl, reason: not valid java name */
    /* synthetic */ PsiIdentifier m6590xee64825c() {
        return new ClsIdentifierImpl(this, getName());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        NotNullLazyValue<PsiDocComment> notNullLazyValue = this.myDocComment;
        if (notNullLazyValue != null) {
            return notNullLazyValue.getValue();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        PsiIdentifier value = this.myNameIdentifier.getValue();
        if (value == null) {
            $$$reportNull$$$0(0);
        }
        return value;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String name2 = getStub().getName();
        if (name2 == null) {
            $$$reportNull$$$0(1);
        }
        return name2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String str) throws IncorrectOperationException {
        if (str == null) {
            $$$reportNull$$$0(2);
        }
        PsiImplUtil.setName(mo7392getNameIdentifier(), str);
        return this;
    }
}
