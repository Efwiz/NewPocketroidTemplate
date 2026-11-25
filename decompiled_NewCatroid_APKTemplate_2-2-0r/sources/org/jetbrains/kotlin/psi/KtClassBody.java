package org.jetbrains.kotlin.psi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KtClassBody.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00000\u00020\u00012\u00020\u0003B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\u0015\b\u0016\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00000\u0002¢\u0006\u0002\u0010\bJ=\u0010-\u001a\n /*\u0004\u0018\u0001H.H.\"\u0004\b\u0000\u0010.\"\u0004\b\u0001\u001002\u0012\u00101\u001a\u000e\u0012\u0004\u0012\u0002H.\u0012\u0004\u0012\u0002H0022\u0006\u00103\u001a\u0002H0H\u0016¢\u0006\u0002\u00104J\u0016\u00105\u001a\u0010\u0012\f\u0012\n /*\u0004\u0018\u000106060\nH\u0016J\u0010\u00107\u001a\n /*\u0004\u0018\u00010\u001e0\u001eH\u0016R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8F¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\n8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\rR\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\n8F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\rR\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\n8F¢\u0006\u0006\u001a\u0004\b\u0016\u0010\rR\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\n8F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\rR\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\n8F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\rR\u0013\u0010\u001d\u001a\u0004\u0018\u00010\u001e8F¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0013\u0010!\u001a\u00070\"¢\u0006\u0002\b#X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\n8F¢\u0006\u0006\u001a\u0004\b&\u0010\rR\u0013\u0010'\u001a\u0004\u0018\u00010\u001e8F¢\u0006\u0006\u001a\u0004\b(\u0010 R\u0013\u0010)\u001a\u00070\"¢\u0006\u0002\b#X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\n8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b,\u0010\r¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtClassBody;", "Lorg/jetbrains/kotlin/psi/KtElementImplStub;", "Lorg/jetbrains/kotlin/psi/stubs/KotlinPlaceHolderStub;", "Lorg/jetbrains/kotlin/psi/KtDeclarationContainer;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinPlaceHolderStub;)V", "allCompanionObjects", "", "Lorg/jetbrains/kotlin/psi/KtObjectDeclaration;", "getAllCompanionObjects", "()Ljava/util/List;", "anonymousInitializers", "Lorg/jetbrains/kotlin/psi/KtAnonymousInitializer;", "getAnonymousInitializers", "danglingAnnotations", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "getDanglingAnnotations", "danglingModifierLists", "Lorg/jetbrains/kotlin/psi/KtModifierList;", "getDanglingModifierLists", "enumEntries", "Lorg/jetbrains/kotlin/psi/KtEnumEntry;", "getEnumEntries", "functions", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "getFunctions", "lBrace", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getLBrace", "()Lcom/intellij/psi/PsiElement;", "lBraceTokenSet", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "Lorg/jetbrains/annotations/NotNull;", "properties", "Lorg/jetbrains/kotlin/psi/KtProperty;", "getProperties", "rBrace", "getRBrace", "rBraceTokenSet", "secondaryConstructors", "Lorg/jetbrains/kotlin/psi/KtSecondaryConstructor;", "getSecondaryConstructors$psi", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getDeclarations", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getParent", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtClassBody extends KtElementImplStub<KotlinPlaceHolderStub<KtClassBody>> implements KtDeclarationContainer {
    private final TokenSet lBraceTokenSet;
    private final TokenSet rBraceTokenSet;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtClassBody(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
        TokenSet tokenSetCreate = TokenSet.create(KtTokens.LBRACE);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate, "create(...)");
        this.lBraceTokenSet = tokenSetCreate;
        TokenSet tokenSetCreate2 = TokenSet.create(KtTokens.RBRACE);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate2, "create(...)");
        this.rBraceTokenSet = tokenSetCreate2;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtClassBody(KotlinPlaceHolderStub<KtClassBody> stub) {
        super(stub, KtStubElementTypes.CLASS_BODY);
        Intrinsics.checkNotNullParameter(stub, "stub");
        TokenSet tokenSetCreate = TokenSet.create(KtTokens.LBRACE);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate, "create(...)");
        this.lBraceTokenSet = tokenSetCreate;
        TokenSet tokenSetCreate2 = TokenSet.create(KtTokens.RBRACE);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate2, "create(...)");
        this.rBraceTokenSet = tokenSetCreate2;
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.com.intellij.extapi.psi.StubBasedPsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: getParent */
    public PsiElement getMyParent() {
        return getParentByStub();
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationContainer
    public List<KtDeclaration> getDeclarations() {
        KtDeclaration[] ktDeclarationArr;
        List<KtDeclaration> list;
        KotlinPlaceHolderStub kotlinPlaceHolderStub = (KotlinPlaceHolderStub) getStub();
        if (kotlinPlaceHolderStub != null && (ktDeclarationArr = (KtDeclaration[]) kotlinPlaceHolderStub.getChildrenByType(KtFile.INSTANCE.getFILE_DECLARATION_TYPES(), KtDeclaration.ARRAY_FACTORY)) != null && (list = ArraysKt.toList(ktDeclarationArr)) != null) {
            return list;
        }
        List<KtDeclaration> childrenOfTypeAsList = PsiTreeUtil.getChildrenOfTypeAsList(this, KtDeclaration.class);
        Intrinsics.checkNotNullExpressionValue(childrenOfTypeAsList, "getChildrenOfTypeAsList(...)");
        return childrenOfTypeAsList;
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitClassBody(this, data);
    }

    public final List<KtAnonymousInitializer> getAnonymousInitializers() {
        List<KtAnonymousInitializer> listFindChildrenByType = findChildrenByType(KtNodeTypes.CLASS_INITIALIZER);
        Intrinsics.checkNotNullExpressionValue(listFindChildrenByType, "findChildrenByType(...)");
        return listFindChildrenByType;
    }

    public final List<KtSecondaryConstructor> getSecondaryConstructors$psi() {
        List stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.SECONDARY_CONSTRUCTOR);
        Intrinsics.checkNotNullExpressionValue(stubOrPsiChildrenAsList, "getStubOrPsiChildrenAsList(...)");
        return stubOrPsiChildrenAsList;
    }

    public final List<KtProperty> getProperties() {
        List stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.PROPERTY);
        Intrinsics.checkNotNullExpressionValue(stubOrPsiChildrenAsList, "getStubOrPsiChildrenAsList(...)");
        return stubOrPsiChildrenAsList;
    }

    public final List<KtNamedFunction> getFunctions() {
        List stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.FUNCTION);
        Intrinsics.checkNotNullExpressionValue(stubOrPsiChildrenAsList, "getStubOrPsiChildrenAsList(...)");
        return stubOrPsiChildrenAsList;
    }

    public final List<KtEnumEntry> getEnumEntries() {
        Iterable stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.ENUM_ENTRY);
        Intrinsics.checkNotNullExpressionValue(stubOrPsiChildrenAsList, "getStubOrPsiChildrenAsList(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : stubOrPsiChildrenAsList) {
            if (obj instanceof KtEnumEntry) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public final List<KtObjectDeclaration> getAllCompanionObjects() {
        Iterable stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.OBJECT_DECLARATION);
        Intrinsics.checkNotNullExpressionValue(stubOrPsiChildrenAsList, "getStubOrPsiChildrenAsList(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj : stubOrPsiChildrenAsList) {
            if (((KtObjectDeclaration) obj).isCompanion()) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public final PsiElement getRBrace() {
        ASTNode[] children = getNode().getChildren(this.rBraceTokenSet);
        Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
        ASTNode aSTNode = (ASTNode) ArraysKt.singleOrNull(children);
        if (aSTNode != null) {
            return aSTNode.getPsi();
        }
        return null;
    }

    public final PsiElement getLBrace() {
        ASTNode[] children = getNode().getChildren(this.lBraceTokenSet);
        Intrinsics.checkNotNullExpressionValue(children, "getChildren(...)");
        ASTNode aSTNode = (ASTNode) ArraysKt.singleOrNull(children);
        if (aSTNode != null) {
            return aSTNode.getPsi();
        }
        return null;
    }

    public final List<KtAnnotationEntry> getDanglingAnnotations() {
        List<KtModifierList> danglingModifierLists = getDanglingModifierLists();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = danglingModifierLists.iterator();
        while (it.hasNext()) {
            List<KtAnnotationEntry> annotationEntries = ((KtModifierList) it.next()).getAnnotationEntries();
            Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
            CollectionsKt.addAll(arrayList, annotationEntries);
        }
        return arrayList;
    }

    public final List<KtModifierList> getDanglingModifierLists() {
        List stubOrPsiChildrenAsList = getStubOrPsiChildrenAsList(KtStubElementTypes.MODIFIER_LIST);
        Intrinsics.checkNotNullExpressionValue(stubOrPsiChildrenAsList, "getStubOrPsiChildrenAsList(...)");
        return stubOrPsiChildrenAsList;
    }
}
