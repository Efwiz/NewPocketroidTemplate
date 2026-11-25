package org.jetbrains.kotlin.kdoc.psi.api;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentBase;
import org.jetbrains.kotlin.kdoc.parser.KDocKnownTag;
import org.jetbrains.kotlin.kdoc.psi.impl.KDocSection;
import org.jetbrains.kotlin.psi.KtDeclaration;

/* JADX INFO: compiled from: KDoc.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u00012\u00020\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\tH&J\u001a\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0006H&J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\fH&J\b\u0010\r\u001a\u00020\u0004H&J\n\u0010\u000e\u001a\u0004\u0018\u00010\u000fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/kdoc/psi/api/KDoc;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiDocCommentBase;", "Lorg/jetbrains/kotlin/kdoc/psi/api/KDocElement;", "findSectionByName", "Lorg/jetbrains/kotlin/kdoc/psi/impl/KDocSection;", "name", "", "findSectionByTag", "tag", "Lorg/jetbrains/kotlin/kdoc/parser/KDocKnownTag;", "subjectName", "getAllSections", "", "getDefaultSection", "getOwner", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KDoc extends PsiDocCommentBase, KDocElement {
    KDocSection findSectionByName(String name2);

    KDocSection findSectionByTag(KDocKnownTag tag);

    KDocSection findSectionByTag(KDocKnownTag tag, String subjectName);

    List<KDocSection> getAllSections();

    KDocSection getDefaultSection();

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentBase
    KtDeclaration getOwner();
}
