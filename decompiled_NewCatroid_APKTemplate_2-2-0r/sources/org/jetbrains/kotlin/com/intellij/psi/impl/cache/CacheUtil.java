package org.jetbrains.kotlin.com.intellij.psi.impl.cache;

import java.util.Iterator;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageParserDefinitions;
import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;

/* JADX INFO: loaded from: classes3.dex */
public final class CacheUtil {
    public static boolean isInComments(IElementType iElementType) {
        Language language = iElementType.getLanguage();
        Iterator<CommentTokenSetProvider> it = CommentTokenSetProvider.EXTENSION.allForLanguage(language).iterator();
        while (it.hasNext()) {
            if (it.next().isInComments(iElementType)) {
                return true;
            }
        }
        ParserDefinition parserDefinitionForLanguage = LanguageParserDefinitions.INSTANCE.forLanguage(language);
        return parserDefinitionForLanguage != null && parserDefinitionForLanguage.getCommentTokens().contains(iElementType);
    }
}
