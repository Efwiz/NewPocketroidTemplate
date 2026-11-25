package org.jetbrains.kotlin.com.intellij.lang;

import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.TokenType;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.Factory;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtilCore;

/* JADX INFO: loaded from: classes3.dex */
public final class LanguageTokenSeparatorGenerators extends LanguageExtension<TokenSeparatorGenerator> {
    public static final LanguageTokenSeparatorGenerators INSTANCE = new LanguageTokenSeparatorGenerators();

    private LanguageTokenSeparatorGenerators() {
        super("org.jetbrains.kotlin.com.intellij.lang.tokenSeparatorGenerator", new TokenSeparatorGenerator() { // from class: org.jetbrains.kotlin.com.intellij.lang.LanguageTokenSeparatorGenerators.1
            @Override // org.jetbrains.kotlin.com.intellij.lang.TokenSeparatorGenerator
            public ASTNode generateWhitespaceBetweenTokens(ASTNode aSTNode, ASTNode aSTNode2) {
                Language notAnyLanguage = PsiUtilCore.getNotAnyLanguage(aSTNode);
                Language notAnyLanguage2 = PsiUtilCore.getNotAnyLanguage(aSTNode2);
                if (notAnyLanguage2.isKindOf(notAnyLanguage)) {
                    notAnyLanguage = notAnyLanguage2;
                }
                ParserDefinition parserDefinitionForLanguage = LanguageParserDefinitions.INSTANCE.forLanguage(notAnyLanguage);
                if (parserDefinitionForLanguage == null) {
                    return null;
                }
                PsiManager manager = aSTNode2.getTreeParent().getPsi().getManager();
                int i = AnonymousClass2.$SwitchMap$com$intellij$lang$ParserDefinition$SpaceRequirements[parserDefinitionForLanguage.spaceExistenceTypeBetweenTokens(aSTNode, aSTNode2).ordinal()];
                if (i == 1) {
                    return Factory.createSingleLeafElement(TokenType.WHITE_SPACE, " ", 0, 1, null, manager);
                }
                if (i != 2) {
                    return null;
                }
                return Factory.createSingleLeafElement(TokenType.WHITE_SPACE, "\n", 0, 1, null, manager);
            }
        });
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.lang.LanguageTokenSeparatorGenerators$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$intellij$lang$ParserDefinition$SpaceRequirements;

        static {
            int[] iArr = new int[ParserDefinition.SpaceRequirements.values().length];
            $SwitchMap$com$intellij$lang$ParserDefinition$SpaceRequirements = iArr;
            try {
                iArr[ParserDefinition.SpaceRequirements.MUST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$intellij$lang$ParserDefinition$SpaceRequirements[ParserDefinition.SpaceRequirements.MUST_LINE_BREAK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }
}
