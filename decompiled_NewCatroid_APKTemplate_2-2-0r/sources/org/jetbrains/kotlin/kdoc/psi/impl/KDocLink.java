package org.jetbrains.kotlin.kdoc.psi.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.psi.KtElementImpl;

/* JADX INFO: compiled from: KDocLink.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\bJ\b\u0010\t\u001a\u0004\u0018\u00010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/kdoc/psi/impl/KDocLink;", "Lorg/jetbrains/kotlin/psi/KtElementImpl;", "node", "Lorg/jetbrains/kotlin/com/intellij/lang/ASTNode;", "(Lcom/intellij/lang/ASTNode;)V", "getLinkText", "", "getLinkTextRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", "getTagIfSubject", "Lorg/jetbrains/kotlin/kdoc/psi/impl/KDocTag;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KDocLink extends KtElementImpl {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KDocLink(ASTNode node) {
        super(node);
        Intrinsics.checkNotNullParameter(node, "node");
    }

    public final String getLinkText() {
        String strSubstring = getLinkTextRange().substring(getText());
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return strSubstring;
    }

    public final TextRange getLinkTextRange() {
        String text = getText();
        Intrinsics.checkNotNull(text);
        String str = text;
        if (StringsKt.startsWith$default((CharSequence) str, '[', false, 2, (Object) null) && StringsKt.endsWith$default((CharSequence) str, ']', false, 2, (Object) null)) {
            return new TextRange(1, text.length() - 1);
        }
        return new TextRange(0, text.length());
    }

    public final KDocTag getTagIfSubject() {
        KDocTag kDocTag = (KDocTag) PsiTreeUtil.getParentOfType(this, KDocTag.class, true);
        if (kDocTag == null || !Intrinsics.areEqual(kDocTag.getSubjectLink(), this)) {
            return null;
        }
        return kDocTag;
    }
}
