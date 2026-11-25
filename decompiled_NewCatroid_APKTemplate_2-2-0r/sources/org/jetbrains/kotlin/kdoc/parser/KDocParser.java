package org.jetbrains.kotlin.kdoc.parser;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.PsiParser;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.kdoc.lexer.KDocTokens;

/* JADX INFO: loaded from: classes5.dex */
public class KDocParser implements PsiParser {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/kdoc/parser/KDocParser", "parse"));
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.PsiParser
    public ASTNode parse(IElementType iElementType, PsiBuilder psiBuilder) {
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        if (psiBuilder.getTokenType() == KDocTokens.START) {
            psiBuilder.advanceLexer();
        }
        PsiBuilder.Marker markerM6463mark2 = psiBuilder.m6463mark();
        while (!psiBuilder.eof()) {
            if (psiBuilder.getTokenType() == KDocTokens.TAG_NAME) {
                markerM6463mark2 = parseTag(psiBuilder, markerM6463mark2);
            } else if (psiBuilder.getTokenType() == KDocTokens.END) {
                if (markerM6463mark2 != null) {
                    markerM6463mark2.done(KDocElementTypes.KDOC_SECTION);
                    markerM6463mark2 = null;
                }
                psiBuilder.advanceLexer();
            } else {
                psiBuilder.advanceLexer();
            }
        }
        if (markerM6463mark2 != null) {
            markerM6463mark2.done(KDocElementTypes.KDOC_SECTION);
        }
        markerM6463mark.done(iElementType);
        ASTNode treeBuilt = psiBuilder.getTreeBuilt();
        if (treeBuilt == null) {
            $$$reportNull$$$0(0);
        }
        return treeBuilt;
    }

    private static PsiBuilder.Marker parseTag(PsiBuilder psiBuilder, PsiBuilder.Marker marker) {
        KDocKnownTag kDocKnownTagFindByTagName = KDocKnownTag.INSTANCE.findByTagName(psiBuilder.getTokenText());
        if (kDocKnownTagFindByTagName != null && kDocKnownTagFindByTagName.getIsSectionStart()) {
            marker.done(KDocElementTypes.KDOC_SECTION);
            marker = psiBuilder.m6463mark();
        }
        PsiBuilder.Marker markerM6463mark = psiBuilder.m6463mark();
        psiBuilder.advanceLexer();
        while (!psiBuilder.eof() && !isAtEndOfTag(psiBuilder)) {
            psiBuilder.advanceLexer();
        }
        markerM6463mark.done(KDocElementTypes.KDOC_TAG);
        return marker;
    }

    private static boolean isAtEndOfTag(PsiBuilder psiBuilder) {
        if (psiBuilder.getTokenType() == KDocTokens.END) {
            return true;
        }
        if (psiBuilder.getTokenType() == KDocTokens.LEADING_ASTERISK) {
            return psiBuilder.lookAhead(psiBuilder.lookAhead(1) == KDocTokens.TEXT ? 2 : 1) == KDocTokens.TAG_NAME;
        }
        return false;
    }
}
