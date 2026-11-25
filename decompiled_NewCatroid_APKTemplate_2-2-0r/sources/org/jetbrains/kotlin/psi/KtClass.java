package org.jetbrains.kotlin.psi;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.stubs.KotlinClassStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtClassElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KtClass.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J5\u0010\u000e\u001a\u0002H\u000f\"\u0004\b\u0000\u0010\u000f\"\u0004\b\u0001\u0010\u00102\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u0002H\u000f\u0012\u0004\u0012\u0002H\u00100\u00122\u0006\u0010\u0013\u001a\u0002H\u0010H\u0016¢\u0006\u0002\u0010\u0014J\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016J\b\u0010\u0017\u001a\u0004\u0018\u00010\u0016J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019H\u0016J\b\u0010\u001b\u001a\u0004\u0018\u00010\u0016J\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0019J\u0006\u0010\u001e\u001a\u00020\u001fJ\u0006\u0010 \u001a\u00020\u001fJ\u0006\u0010!\u001a\u00020\u001fJ\u0006\u0010\"\u001a\u00020\u001fJ\u0006\u0010#\u001a\u00020\u001fJ\u0006\u0010$\u001a\u00020\u001fR\u0016\u0010\b\u001a\u0004\u0018\u00010\u00068BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0013\u0010\u000b\u001a\u00070\f¢\u0006\u0002\b\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtClass;", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "Lorg/jetbrains/kotlin/psi/stubs/KotlinClassStub;", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinClassStub;)V", "_stub", "get_stub", "()Lorg/jetbrains/kotlin/psi/stubs/KotlinClassStub;", "classInterfaceTokenSet", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "Lorg/jetbrains/annotations/NotNull;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "getClassKeyword", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getClassOrInterfaceKeyword", "getCompanionObjects", "", "Lorg/jetbrains/kotlin/psi/KtObjectDeclaration;", "getFunKeyword", "getProperties", "Lorg/jetbrains/kotlin/psi/KtProperty;", "isEnum", "", "isInline", "isInner", "isInterface", "isSealed", "isValue", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtClass extends KtClassOrObject {
    private final TokenSet classInterfaceTokenSet;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtClass(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
        TokenSet tokenSetCreate = TokenSet.create(KtTokens.CLASS_KEYWORD, KtTokens.INTERFACE_KEYWORD);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate, "create(...)");
        this.classInterfaceTokenSet = tokenSetCreate;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KtClass(KotlinClassStub stub) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        KtClassElementType CLASS = KtStubElementTypes.CLASS;
        Intrinsics.checkNotNullExpressionValue(CLASS, "CLASS");
        super(stub, CLASS);
        TokenSet tokenSetCreate = TokenSet.create(KtTokens.CLASS_KEYWORD, KtTokens.INTERFACE_KEYWORD);
        Intrinsics.checkNotNullExpressionValue(tokenSetCreate, "create(...)");
        this.classInterfaceTokenSet = tokenSetCreate;
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitClass(this, data);
    }

    private final KotlinClassStub get_stub() {
        T stub = getStub();
        if (stub instanceof KotlinClassStub) {
            return (KotlinClassStub) stub;
        }
        return null;
    }

    public final List<KtProperty> getProperties() {
        KtClassBody body = getBody();
        List<KtProperty> properties = body != null ? body.getProperties() : null;
        return properties == null ? CollectionsKt.emptyList() : properties;
    }

    public final boolean isInterface() {
        KotlinClassStub kotlinClassStub = get_stub();
        if (kotlinClassStub != null) {
            return kotlinClassStub.isInterface();
        }
        return findChildByType(KtTokens.INTERFACE_KEYWORD) != null;
    }

    public final boolean isEnum() {
        return hasModifier(KtTokens.ENUM_KEYWORD);
    }

    public final boolean isSealed() {
        return hasModifier(KtTokens.SEALED_KEYWORD);
    }

    public final boolean isInner() {
        return hasModifier(KtTokens.INNER_KEYWORD);
    }

    public final boolean isInline() {
        return hasModifier(KtTokens.INLINE_KEYWORD);
    }

    public final boolean isValue() {
        return hasModifier(KtTokens.VALUE_KEYWORD);
    }

    @Override // org.jetbrains.kotlin.psi.KtPureClassOrObject
    public List<KtObjectDeclaration> getCompanionObjects() {
        KtClassBody body = getBody();
        List<KtObjectDeclaration> allCompanionObjects = body != null ? body.getAllCompanionObjects() : null;
        return allCompanionObjects == null ? CollectionsKt.emptyList() : allCompanionObjects;
    }

    public final PsiElement getClassOrInterfaceKeyword() {
        return findChildByType(this.classInterfaceTokenSet);
    }

    public final PsiElement getClassKeyword() {
        return findChildByType(KtTokens.CLASS_KEYWORD);
    }

    public final PsiElement getFunKeyword() {
        KtModifierList modifierList = getModifierList();
        if (modifierList != null) {
            return modifierList.getModifier(KtTokens.FUN_KEYWORD);
        }
        return null;
    }
}
