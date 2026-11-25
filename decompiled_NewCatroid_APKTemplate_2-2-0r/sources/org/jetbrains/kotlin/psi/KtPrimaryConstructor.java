package org.jetbrains.kotlin.psi;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.addRemoveModifier.AddRemoveModifierKt;
import org.jetbrains.kotlin.psi.stubs.KotlinConstructorStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtPrimaryConstructorElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;

/* JADX INFO: compiled from: KtPrimaryConstructor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0015\b\u0016\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00000\u0006¢\u0006\u0002\u0010\u0007J=\u0010\b\u001a\n \n*\u0004\u0018\u0001H\tH\t\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u0002H\u000b0\r2\u0006\u0010\u000e\u001a\u0002H\u000bH\u0016¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0002J\u0010\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0006\u0010\u001c\u001a\u00020\u0014¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtPrimaryConstructor;", "Lorg/jetbrains/kotlin/psi/KtConstructor;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "stub", "Lorg/jetbrains/kotlin/psi/stubs/KotlinConstructorStub;", "(Lorg/jetbrains/kotlin/psi/stubs/KotlinConstructorStub;)V", "accept", "R", JvmProtoBufUtil.PLATFORM_TYPE_ID, "D", "visitor", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "addAnnotationEntry", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "annotationEntry", "addModifier", "", "modifier", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", "getContainingClassOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getOrCreateConstructorKeyword", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "removeModifier", "removeRedundantConstructorKeywordAndSpace", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtPrimaryConstructor extends KtConstructor<KtPrimaryConstructor> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtPrimaryConstructor(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KtPrimaryConstructor(KotlinConstructorStub<KtPrimaryConstructor> stub) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        KtPrimaryConstructorElementType PRIMARY_CONSTRUCTOR = KtStubElementTypes.PRIMARY_CONSTRUCTOR;
        Intrinsics.checkNotNullExpressionValue(PRIMARY_CONSTRUCTOR, "PRIMARY_CONSTRUCTOR");
        super(stub, PRIMARY_CONSTRUCTOR);
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitPrimaryConstructor(this, data);
    }

    @Override // org.jetbrains.kotlin.psi.KtConstructor
    public KtClassOrObject getContainingClassOrObject() {
        PsiElement parent = getLightParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtClassOrObject");
        return (KtClassOrObject) parent;
    }

    private final PsiElement getOrCreateConstructorKeyword() {
        PsiElement constructorKeyword = getConstructorKeyword();
        if (constructorKeyword != null) {
            return constructorKeyword;
        }
        Project project = getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        PsiElement psiElementCreateConstructorKeyword = new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null).createConstructorKeyword();
        KtParameterList valueParameterList = getValueParameterList();
        Intrinsics.checkNotNull(valueParameterList);
        PsiElement psiElementAddBefore = addBefore(psiElementCreateConstructorKeyword, valueParameterList);
        Intrinsics.checkNotNullExpressionValue(psiElementAddBefore, "addBefore(...)");
        return psiElementAddBefore;
    }

    public final void removeRedundantConstructorKeywordAndSpace() {
        PsiElement constructorKeyword = getConstructorKeyword();
        if (constructorKeyword != null) {
            constructorKeyword.delete();
        }
        if (getPrevSibling() instanceof PsiWhiteSpace) {
            getPrevSibling().delete();
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtModifierListOwnerStub, org.jetbrains.kotlin.psi.KtModifierListOwner
    public void addModifier(KtModifierKeywordToken modifier) {
        PsiElement constructorKeyword;
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        KtModifierList modifierList = getModifierList();
        if (modifierList != null) {
            AddRemoveModifierKt.addModifier(modifierList, modifier);
            if (getModifierList() != null || (constructorKeyword = getConstructorKeyword()) == null) {
                return;
            }
            constructorKeyword.delete();
            return;
        }
        if (Intrinsics.areEqual(modifier, KtTokens.PUBLIC_KEYWORD)) {
            return;
        }
        Project project = getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        addBefore(new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null).createModifierList(modifier), getOrCreateConstructorKeyword());
    }

    @Override // org.jetbrains.kotlin.psi.KtModifierListOwnerStub, org.jetbrains.kotlin.psi.KtModifierListOwner
    public void removeModifier(KtModifierKeywordToken modifier) {
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        super.removeModifier(modifier);
        if (getModifierList() == null) {
            removeRedundantConstructorKeywordAndSpace();
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtModifierListOwnerStub, org.jetbrains.kotlin.psi.KtModifierListOwner
    public KtAnnotationEntry addAnnotationEntry(KtAnnotationEntry annotationEntry) {
        Intrinsics.checkNotNullParameter(annotationEntry, "annotationEntry");
        KtModifierList modifierList = getModifierList();
        if (modifierList != null) {
            PsiElement psiElementAddBefore = modifierList.addBefore(annotationEntry, modifierList.getFirstChild());
            Intrinsics.checkNotNull(psiElementAddBefore, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtAnnotationEntry");
            return (KtAnnotationEntry) psiElementAddBefore;
        }
        Project project = getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        KtPsiFactory ktPsiFactory = new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null);
        String text = annotationEntry.getText();
        Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
        PsiElement psiElementAddBefore2 = addBefore(ktPsiFactory.createModifierList(text), getOrCreateConstructorKeyword());
        Intrinsics.checkNotNull(psiElementAddBefore2, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtModifierList");
        List<KtAnnotationEntry> annotationEntries = ((KtModifierList) psiElementAddBefore2).getAnnotationEntries();
        Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
        Object objFirst = CollectionsKt.first((List<? extends Object>) annotationEntries);
        Intrinsics.checkNotNull(objFirst);
        return (KtAnnotationEntry) objFirst;
    }
}
