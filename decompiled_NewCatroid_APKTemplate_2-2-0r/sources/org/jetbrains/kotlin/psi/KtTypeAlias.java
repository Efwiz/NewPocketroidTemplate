package org.jetbrains.kotlin.psi;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentation;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.psi.psiUtil.ClassIdCalculator;
import org.jetbrains.kotlin.psi.stubs.KotlinTypeAliasStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KtTypeAlias.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\u00020\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B\u000f\b\u0016\u0012\u0006\u0010\b\u001a\u00020\u0002¢\u0006\u0002\u0010\tJ5\u0010\n\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\f0\u000e2\u0006\u0010\u000f\u001a\u0002H\fH\u0016¢\u0006\u0002\u0010\u0010J\n\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\u000f\u0010\u0013\u001a\t\u0018\u00010\u0014¢\u0006\u0002\b\u0015H\u0016J\n\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0007J\n\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0007J\u0006\u0010\u001a\u001a\u00020\u001b¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "Lorg/jetbrains/kotlin/psi/KtTypeParameterListOwnerStub;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinTypeAliasStub;", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "Lorg/jetbrains/kotlin/psi/KtClassLikeDeclaration;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinTypeAliasStub;)V", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "getPresentation", "Lorg/jetbrains/kotlin/com/intellij/navigation/ItemPresentation;", "Lorg/jetbrains/annotations/Nullable;", "getTypeAliasKeyword", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getTypeReference", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "isTopLevel", "", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtTypeAlias extends KtTypeParameterListOwnerStub<KotlinTypeAliasStub> implements KtClassLikeDeclaration, KtNamedDeclaration {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeAlias(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtTypeAlias(KotlinTypeAliasStub stub) {
        super(stub, KtStubElementTypes.TYPEALIAS);
        Intrinsics.checkNotNullParameter(stub, "stub");
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitTypeAlias(this, data);
    }

    public final boolean isTopLevel() {
        KotlinTypeAliasStub kotlinTypeAliasStub = (KotlinTypeAliasStub) getStub();
        return kotlinTypeAliasStub != null ? kotlinTypeAliasStub.isTopLevel() : getParent() instanceof KtFile;
    }

    @IfNotParsed
    public final PsiElement getTypeAliasKeyword() {
        return findChildByType(KtTokens.TYPE_ALIAS_KEYWORD);
    }

    @IfNotParsed
    public final KtTypeReference getTypeReference() {
        if (getStub() != 0) {
            List<PsiT> stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.TYPE_REFERENCE);
            Intrinsics.checkNotNullExpressionValue(stubOrPsiChildrenAsList, "getStubOrPsiChildrenAsList(...)");
            return (KtTypeReference) stubOrPsiChildrenAsList.get(0);
        }
        return (KtTypeReference) findChildByType(KtNodeTypes.TYPE_REFERENCE);
    }

    @Override // org.jetbrains.kotlin.psi.KtClassLikeDeclaration
    public ClassId getClassId() {
        KotlinTypeAliasStub kotlinTypeAliasStub = (KotlinTypeAliasStub) getStub();
        if (kotlinTypeAliasStub == null) {
            return ClassIdCalculator.INSTANCE.calculateClassId(this);
        }
        return kotlinTypeAliasStub.getClassId();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem
    public ItemPresentation getPresentation() {
        return ItemPresentationProviders.getItemPresentation(this);
    }
}
