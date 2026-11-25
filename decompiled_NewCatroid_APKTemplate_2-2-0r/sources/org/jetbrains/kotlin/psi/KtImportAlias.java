package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.kotlin.com.intellij.psi.search.LocalSearchScope;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi.stubs.KotlinImportAliasStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KtImportAlias.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u000f\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020\u0002¢\u0006\u0002\u0010\bJ5\u0010\r\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u0010\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H\u000f0\u00112\u0006\u0010\u0012\u001a\u0002H\u000fH\u0016¢\u0006\u0002\u0010\u0013J\n\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\n\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00172\u0006\u0010\u001d\u001a\u00020\u0015H\u0016R\u0013\u0010\t\u001a\u0004\u0018\u00010\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtImportAlias;", "Lorg/jetbrains/kotlin/psi/KtElementImplStub;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinImportAliasStub;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiNameIdentifierOwner;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinImportAliasStub;)V", "importDirective", "Lorg/jetbrains/kotlin/psi/KtImportDirective;", "getImportDirective", "()Lorg/jetbrains/kotlin/psi/KtImportDirective;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getName", "", "getNameIdentifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getTextOffset", "", "getUseScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/LocalSearchScope;", "setName", "name", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtImportAlias extends KtElementImplStub<KotlinImportAliasStub> implements PsiNameIdentifierOwner {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtImportAlias(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtImportAlias(KotlinImportAliasStub stub) {
        super(stub, KtStubElementTypes.IMPORT_ALIAS);
        Intrinsics.checkNotNullParameter(stub, "stub");
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitImportAlias(this, data);
    }

    public final KtImportDirective getImportDirective() {
        PsiElement parent = getParent();
        if (parent instanceof KtImportDirective) {
            return (KtImportDirective) parent;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String name2;
        KotlinImportAliasStub kotlinImportAliasStub = (KotlinImportAliasStub) getStub();
        if (kotlinImportAliasStub != null && (name2 = kotlinImportAliasStub.getName()) != null) {
            return name2;
        }
        PsiElement psiElementMo7392getNameIdentifier = mo7392getNameIdentifier();
        if (psiElementMo7392getNameIdentifier != null) {
            return psiElementMo7392getNameIdentifier.getText();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        PsiElement psiElementMo7392getNameIdentifier = mo7392getNameIdentifier();
        if (psiElementMo7392getNameIdentifier != null) {
            Project project = getProject();
            Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
            psiElementMo7392getNameIdentifier.replace(new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null).createNameIdentifier(name2));
        }
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiElement mo7392getNameIdentifier() {
        return findChildByType(KtTokens.IDENTIFIER);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        PsiElement psiElementMo7392getNameIdentifier = mo7392getNameIdentifier();
        return psiElementMo7392getNameIdentifier != null ? psiElementMo7392getNameIdentifier.getTextOffset() : PsiUtilsKt.getStartOffset(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public LocalSearchScope getUseScope() {
        return new LocalSearchScope(getContainingFile());
    }
}
