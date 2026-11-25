package org.jetbrains.kotlin.psi;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.stubs.KotlinObjectStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtObjectElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KtObjectDeclaration.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J5\u0010\u000b\u001a\u0002H\f\"\u0004\b\u0000\u0010\f\"\u0004\b\u0001\u0010\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u0002H\r0\u000f2\u0006\u0010\u0010\u001a\u0002H\rH\u0016¢\u0006\u0002\u0010\u0011J\u000e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00000\u0013H\u0016J\n\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\n\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\b\u0010\u0018\u001a\u0004\u0018\u00010\u0015J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\u0006\u0010\u001b\u001a\u00020\u001cJ\u0006\u0010\u001d\u001a\u00020\u001cJ\u0012\u0010\u001e\u001a\u00020\u00152\b\b\u0001\u0010\u001f\u001a\u00020\u0017H\u0016R\u0016\u0010\b\u001a\u0004\u0018\u00010\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/psi/KtObjectDeclaration;", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "Lorg/jetbrains/kotlin/psi/stubs/KotlinObjectStub;", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinObjectStub;)V", "_stub", "get_stub", "()Lorg/jetbrains/kotlin/psi/stubs/KotlinObjectStub;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getCompanionObjects", "", "getIdentifyingElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getName", "", "getObjectKeyword", "getTextOffset", "", "isCompanion", "", "isObjectLiteral", "setName", "name", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtObjectDeclaration extends KtClassOrObject {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtObjectDeclaration(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KtObjectDeclaration(KotlinObjectStub stub) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        KtObjectElementType OBJECT_DECLARATION = KtStubElementTypes.OBJECT_DECLARATION;
        Intrinsics.checkNotNullExpressionValue(OBJECT_DECLARATION, "OBJECT_DECLARATION");
        super(stub, OBJECT_DECLARATION);
    }

    private final KotlinObjectStub get_stub() {
        T stub = getStub();
        if (stub instanceof KotlinObjectStub) {
            return (KotlinObjectStub) stub;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwnerStub, org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        String name2 = super.getName();
        if (name2 != null) {
            return name2;
        }
        if (!isCompanion() || isTopLevel()) {
            return null;
        }
        return SpecialNames.DEFAULT_NAME_FOR_COMPANION_OBJECT.toString();
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwnerStub, org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        if (mo7392getNameIdentifier() == null) {
            Project project = getProject();
            Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
            KtPsiFactory ktPsiFactory = new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null);
            PsiElement psiElementCreateIdentifier = ktPsiFactory.createIdentifier(name2);
            PsiElement objectKeyword = getObjectKeyword();
            Intrinsics.checkNotNull(objectKeyword);
            PsiElement psiElementAddAfter = addAfter(psiElementCreateIdentifier, objectKeyword);
            PsiElement psiElementCreateWhiteSpace = ktPsiFactory.createWhiteSpace();
            PsiElement objectKeyword2 = getObjectKeyword();
            Intrinsics.checkNotNull(objectKeyword2);
            addAfter(psiElementCreateWhiteSpace, objectKeyword2);
            Intrinsics.checkNotNull(psiElementAddAfter);
            return psiElementAddAfter;
        }
        PsiElement name3 = super.setName(name2);
        Intrinsics.checkNotNull(name3);
        return name3;
    }

    public final boolean isCompanion() {
        KotlinObjectStub kotlinObjectStub = get_stub();
        return kotlinObjectStub != null ? kotlinObjectStub.isCompanion() : hasModifier(KtTokens.COMPANION_KEYWORD);
    }

    @Override // org.jetbrains.kotlin.psi.KtTypeParameterListOwnerStub, org.jetbrains.kotlin.psi.KtNamedDeclarationStub, org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public int getTextOffset() {
        TextRange textRange;
        PsiElement nameIdentifier = mo7392getNameIdentifier();
        if (nameIdentifier != null && (textRange = nameIdentifier.getTextRange()) != null) {
            return textRange.getStartOffset();
        }
        PsiElement objectKeyword = getObjectKeyword();
        Intrinsics.checkNotNull(objectKeyword);
        return objectKeyword.getTextRange().getStartOffset();
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitObjectDeclaration(this, data);
    }

    public final boolean isObjectLiteral() {
        KotlinObjectStub kotlinObjectStub = get_stub();
        return kotlinObjectStub != null ? kotlinObjectStub.isObjectLiteral() : getParent() instanceof KtObjectLiteralExpression;
    }

    public final PsiElement getObjectKeyword() {
        return findChildByType(KtTokens.OBJECT_KEYWORD);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    public PsiElement getIdentifyingElement() {
        return getObjectKeyword();
    }

    @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
    public List<KtObjectDeclaration> getCompanionObjects() {
        return CollectionsKt.emptyList();
    }
}
