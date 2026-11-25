package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.stubs.KotlinEnumEntrySuperclassReferenceExpressionStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KtEnumEntrySuperclassReferenceExpression.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u000f\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020\u0002¢\u0006\u0002\u0010\bJ5\u0010\r\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u0010\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H\u000f0\u00112\u0006\u0010\u0012\u001a\u0002H\u000fH\u0016¢\u0006\u0002\u0010\u0013J\n\u0010\u0014\u001a\u0004\u0018\u00010\nH\u0002J\n\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0016H\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0016R\u0016\u0010\t\u001a\u0004\u0018\u00010\n8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtEnumEntrySuperclassReferenceExpression;", "Lorg/jetbrains/kotlin/psi/KtExpressionImplStub;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinEnumEntrySuperclassReferenceExpressionStub;", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinEnumEntrySuperclassReferenceExpressionStub;)V", "referencedElement", "Lorg/jetbrains/kotlin/psi/KtClass;", "getReferencedElement", "()Lorg/jetbrains/kotlin/psi/KtClass;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "calcReferencedElement", "getIdentifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getReferencedName", "", "getReferencedNameAsName", "Lorg/jetbrains/kotlin/name/Name;", "getReferencedNameElement", "getReferencedNameElementType", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtEnumEntrySuperclassReferenceExpression extends KtExpressionImplStub<KotlinEnumEntrySuperclassReferenceExpressionStub> implements KtSimpleNameExpression {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtEnumEntrySuperclassReferenceExpression(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtEnumEntrySuperclassReferenceExpression(KotlinEnumEntrySuperclassReferenceExpressionStub stub) {
        super(stub, KtStubElementTypes.ENUM_ENTRY_SUPERCLASS_REFERENCE_EXPRESSION);
        Intrinsics.checkNotNullParameter(stub, "stub");
    }

    private final KtClass getReferencedElement() {
        return calcReferencedElement();
    }

    private final KtClass calcReferencedElement() {
        PsiElement parent;
        KtEnumEntry ktEnumEntry = (KtEnumEntry) PsiTreeUtil.getParentOfType(this, KtEnumEntry.class, true);
        PsiElement parent2 = (ktEnumEntry == null || (parent = ktEnumEntry.getParent()) == null) ? null : parent.getParent();
        if (parent2 instanceof KtClass) {
            return (KtClass) parent2;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtSimpleNameExpression
    public String getReferencedName() {
        KotlinEnumEntrySuperclassReferenceExpressionStub kotlinEnumEntrySuperclassReferenceExpressionStub = (KotlinEnumEntrySuperclassReferenceExpressionStub) getStub();
        if (kotlinEnumEntrySuperclassReferenceExpressionStub != null) {
            return kotlinEnumEntrySuperclassReferenceExpressionStub.getReferencedName();
        }
        PsiElement referencedNameElement = getReferencedNameElement();
        Intrinsics.checkNotNull(referencedNameElement, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtNamedDeclaration");
        String strAsString = ((KtNamedDeclaration) referencedNameElement).getNameAsSafeName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String strUnquoteIdentifierOrFieldReference = KtPsiUtil.unquoteIdentifierOrFieldReference(strAsString);
        Intrinsics.checkNotNullExpressionValue(strUnquoteIdentifierOrFieldReference, "unquoteIdentifierOrFieldReference(...)");
        return strUnquoteIdentifierOrFieldReference;
    }

    @Override // org.jetbrains.kotlin.psi.KtSimpleNameExpression
    public Name getReferencedNameAsName() {
        String name2;
        KtClass referencedElement = getReferencedElement();
        Name nameIdentifier = (referencedElement == null || (name2 = referencedElement.getName()) == null) ? null : Name.identifier(name2);
        return nameIdentifier == null ? SpecialNames.NO_NAME_PROVIDED : nameIdentifier;
    }

    @Override // org.jetbrains.kotlin.psi.KtSimpleNameExpression
    public PsiElement getReferencedNameElement() {
        KtClass referencedElement = getReferencedElement();
        Intrinsics.checkNotNull(referencedElement);
        return referencedElement;
    }

    @Override // org.jetbrains.kotlin.psi.KtSimpleNameExpression
    public PsiElement getIdentifier() {
        KtClass referencedElement = getReferencedElement();
        if (referencedElement != null) {
            return referencedElement.mo7392getNameIdentifier();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtSimpleNameExpression
    public IElementType getReferencedNameElementType() {
        ASTNode node = getReferencedNameElement().getNode();
        Intrinsics.checkNotNull(node);
        IElementType elementType = node.getElementType();
        Intrinsics.checkNotNullExpressionValue(elementType, "getElementType(...)");
        return elementType;
    }

    @Override // org.jetbrains.kotlin.psi.KtExpressionImplStub, org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitSimpleNameExpression(this, data);
    }
}
