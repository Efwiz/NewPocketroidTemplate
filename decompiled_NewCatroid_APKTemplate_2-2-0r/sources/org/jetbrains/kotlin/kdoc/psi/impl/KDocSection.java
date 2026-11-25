package org.jetbrains.kotlin.kdoc.psi.impl;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;

/* JADX INFO: compiled from: KDocSection.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0006\u001a\u00020\u0007J\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\u0006\u0010\u0006\u001a\u00020\u0007J\b\u0010\n\u001a\u00020\u0007H\u0016J\n\u0010\u000b\u001a\u0004\u0018\u00010\u0007H\u0016J\n\u0010\f\u001a\u0004\u0018\u00010\u0007H\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/kdoc/psi/impl/KDocSection;", "Lorg/jetbrains/kotlin/kdoc/psi/impl/KDocTag;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "findTagByName", "name", "", "findTagsByName", "", "getContent", "getName", "getSubjectName", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KDocSection extends KDocTag {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KDocSection(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    @Override // org.jetbrains.kotlin.kdoc.psi.impl.KDocTag, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getName() {
        PsiElement firstChild = getFirstChild();
        KDocTag kDocTag = firstChild instanceof KDocTag ? (KDocTag) firstChild : null;
        if (kDocTag != null) {
            return kDocTag.getName();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.kdoc.psi.impl.KDocTag
    public String getSubjectName() {
        PsiElement firstChild = getFirstChild();
        KDocTag kDocTag = firstChild instanceof KDocTag ? (KDocTag) firstChild : null;
        if (kDocTag != null) {
            return kDocTag.getSubjectName();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.kdoc.psi.impl.KDocTag
    public String getContent() {
        String content;
        PsiElement firstChild = getFirstChild();
        KDocTag kDocTag = firstChild instanceof KDocTag ? (KDocTag) firstChild : null;
        return (kDocTag == null || (content = kDocTag.getContent()) == null) ? super.getContent() : content;
    }

    public final List<KDocTag> findTagsByName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        KDocTag[] childrenOfType = PsiTreeUtil.getChildrenOfType(this, KDocTag.class);
        if (childrenOfType == null) {
            childrenOfType = new KDocTag[0];
        } else {
            Intrinsics.checkNotNull(childrenOfType);
        }
        ArrayList arrayList = new ArrayList();
        for (PsiElement psiElement : childrenOfType) {
            if (Intrinsics.areEqual(((KDocTag) psiElement).getName(), name2)) {
                arrayList.add(psiElement);
            }
        }
        return arrayList;
    }

    public final KDocTag findTagByName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return (KDocTag) CollectionsKt.firstOrNull((List) findTagsByName(name2));
    }
}
