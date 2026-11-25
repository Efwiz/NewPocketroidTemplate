package org.jetbrains.kotlin.kdoc.psi.impl;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.kdoc.psi.api.KDoc;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtElementImpl;

/* JADX INFO: compiled from: KDocName.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\fJ\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\u000eJ\u0006\u0010\u000f\u001a\u00020\u0010J\b\u0010\u0011\u001a\u0004\u0018\u00010\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/kdoc/psi/impl/KDocName;", "Lorg/jetbrains/kotlin/psi/KtElementImpl;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "getContainingDoc", "Lorg/jetbrains/kotlin/kdoc/psi/api/KDoc;", "getContainingSection", "Lorg/jetbrains/kotlin/kdoc/psi/impl/KDocSection;", "getNameText", "", "getNameTextRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getQualifiedName", "", "getQualifiedNameAsFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getQualifier", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KDocName extends KtElementImpl {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KDocName(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    public final KDoc getContainingDoc() {
        KDoc kDoc = (KDoc) PsiTreeUtil.getParentOfType(this, KDoc.class, true);
        if (kDoc != null) {
            return kDoc;
        }
        throw new IllegalStateException("KDocName must be inside a KDoc");
    }

    public final KDocSection getContainingSection() {
        KDocSection kDocSection = (KDocSection) PsiTreeUtil.getParentOfType(this, KDocSection.class, true);
        if (kDocSection != null) {
            return kDocSection;
        }
        throw new IllegalStateException("KDocName must be inside a KDocSection");
    }

    public final KDocName getQualifier() {
        return (KDocName) PsiTreeUtil.getChildOfType(this, KDocName.class);
    }

    public final TextRange getNameTextRange() {
        ASTNode aSTNodeFindChildByType = getNode().findChildByType(KtTokens.DOT);
        TextRange textRange = getTextRange();
        return new TextRange(aSTNodeFindChildByType != null ? aSTNodeFindChildByType.getTextRange().getEndOffset() - textRange.getStartOffset() : 0, textRange.getLength());
    }

    public final String getNameText() {
        String strSubstring = getNameTextRange().substring(getText());
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return strSubstring;
    }

    public final List<String> getQualifiedName() {
        KDocName qualifier = getQualifier();
        List<String> listListOf = CollectionsKt.listOf(getNameText());
        return qualifier != null ? CollectionsKt.plus((Collection) qualifier.getQualifiedName(), (Iterable) listListOf) : listListOf;
    }

    public final FqName getQualifiedNameAsFqName() {
        FqName fqNameFromSegments = FqName.fromSegments(getQualifiedName());
        Intrinsics.checkNotNullExpressionValue(fqNameFromSegments, "fromSegments(...)");
        return fqNameFromSegments;
    }
}
