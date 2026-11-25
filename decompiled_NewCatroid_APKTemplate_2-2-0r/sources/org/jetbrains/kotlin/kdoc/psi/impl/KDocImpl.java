package org.jetbrains.kotlin.kdoc.psi.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.kdoc.lexer.KDocTokens;
import org.jetbrains.kotlin.kdoc.parser.KDocKnownTag;
import org.jetbrains.kotlin.kdoc.psi.api.KDoc;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.util.capitalizeDecapitalize.CapitalizeDecapitalizeKt;

/* JADX INFO: compiled from: KDocImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005J\u0012\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0012\u0010\n\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u001a\u0010\n\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\tH\u0016J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0007H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\n\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\tH\u0016¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/kdoc/psi/impl/KDocImpl;", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/source/tree/LazyParseablePsiElement;", "Lorg/jetbrains/kotlin/kdoc/psi/api/KDoc;", "buffer", "", "(Ljava/lang/CharSequence;)V", "findSectionByName", "Lorg/jetbrains/kotlin/kdoc/psi/impl/KDocSection;", "name", "", "findSectionByTag", "tag", "Lorg/jetbrains/kotlin/kdoc/parser/KDocKnownTag;", "subjectName", "getAllSections", "", "getDefaultSection", "getLanguage", "Lorg/jetbrains/kotlin/com/intellij/lang/Language;", "getOwner", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getTokenType", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/IElementType;", "toString", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KDocImpl extends LazyParseablePsiElement implements KDoc {
    public KDocImpl(CharSequence charSequence) {
        super(KDocTokens.KDOC, charSequence);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public Language getLanguage() {
        KotlinLanguage INSTANCE = KotlinLanguage.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(INSTANCE, "INSTANCE");
        return INSTANCE;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LazyParseablePsiElement, org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        String string = getNode().getElementType().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiComment
    public IElementType getTokenType() {
        IElementType DOC_COMMENT = KtTokens.DOC_COMMENT;
        Intrinsics.checkNotNullExpressionValue(DOC_COMMENT, "DOC_COMMENT");
        return DOC_COMMENT;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentBase
    public KtDeclaration getOwner() {
        return (KtDeclaration) PsiTreeUtil.getParentOfType(this, KtDeclaration.class, true);
    }

    @Override // org.jetbrains.kotlin.kdoc.psi.api.KDoc
    public KDocSection getDefaultSection() {
        PsiElement childOfType = PsiTreeUtil.getChildOfType(this, KDocSection.class);
        Intrinsics.checkNotNull(childOfType);
        return (KDocSection) childOfType;
    }

    @Override // org.jetbrains.kotlin.kdoc.psi.api.KDoc
    public List<KDocSection> getAllSections() {
        KDocSection[] childrenOfType = PsiTreeUtil.getChildrenOfType(this, KDocSection.class);
        if (childrenOfType == null) {
            childrenOfType = new KDocSection[0];
        } else {
            Intrinsics.checkNotNull(childrenOfType);
        }
        return ArraysKt.toList(childrenOfType);
    }

    @Override // org.jetbrains.kotlin.kdoc.psi.api.KDoc
    public KDocSection findSectionByName(String name2) {
        PsiElement psiElement;
        Intrinsics.checkNotNullParameter(name2, "name");
        KDocSection[] childrenOfType = PsiTreeUtil.getChildrenOfType(this, KDocSection.class);
        if (childrenOfType == null) {
            childrenOfType = new KDocSection[0];
        } else {
            Intrinsics.checkNotNull(childrenOfType);
        }
        int length = childrenOfType.length;
        for (int i = 0; i < length; i++) {
            psiElement = childrenOfType[i];
            if (Intrinsics.areEqual(((KDocSection) psiElement).getMyName(), name2)) {
                return (KDocSection) psiElement;
            }
        }
        psiElement = null;
        return (KDocSection) psiElement;
    }

    @Override // org.jetbrains.kotlin.kdoc.psi.api.KDoc
    public KDocSection findSectionByTag(KDocKnownTag tag) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        return findSectionByName(CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(tag.name()));
    }

    @Override // org.jetbrains.kotlin.kdoc.psi.api.KDoc
    public KDocSection findSectionByTag(KDocKnownTag tag, String subjectName) {
        PsiElement psiElement;
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(subjectName, "subjectName");
        KDocSection[] childrenOfType = PsiTreeUtil.getChildrenOfType(this, KDocSection.class);
        if (childrenOfType == null) {
            childrenOfType = new KDocSection[0];
        } else {
            Intrinsics.checkNotNull(childrenOfType);
        }
        int length = childrenOfType.length;
        for (int i = 0; i < length; i++) {
            psiElement = childrenOfType[i];
            KDocSection kDocSection = (KDocSection) psiElement;
            if (Intrinsics.areEqual(kDocSection.getMyName(), CapitalizeDecapitalizeKt.toLowerCaseAsciiOnly(tag.name())) && Intrinsics.areEqual(kDocSection.getSubjectName(), subjectName)) {
                return (KDocSection) psiElement;
            }
        }
        psiElement = null;
        return (KDocSection) psiElement;
    }
}
