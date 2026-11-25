package org.jetbrains.kotlin;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory;
import org.jetbrains.kotlin.com.intellij.lexer.Lexer;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.psi.tree.IErrorCounterReparseableElementType;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.lexer.KotlinLexer;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.parsing.KotlinParser;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtParameterList;

/* JADX INFO: loaded from: classes14.dex */
class LambdaExpressionElementType extends IErrorCounterReparseableElementType {
    public LambdaExpressionElementType() {
        super("LAMBDA_EXPRESSION", KotlinLanguage.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementTypeBase
    public ASTNode parseContents(ASTNode aSTNode) {
        return KotlinParser.parseLambdaExpression(PsiBuilderFactory.getInstance().createBuilder(aSTNode.getPsi().getProject(), aSTNode, (Lexer) null, KotlinLanguage.INSTANCE, aSTNode.getChars())).getFirstChildNode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.ILazyParseableElementType
    public ASTNode createNode(CharSequence charSequence) {
        return new KtLambdaExpression(charSequence);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IReparseableElementType, org.jetbrains.kotlin.com.intellij.psi.tree.IReparseableElementTypeBase
    public boolean isParsable(ASTNode aSTNode, CharSequence charSequence, Language language, Project project) {
        return (!super.isParsable(aSTNode, charSequence, language, project) || wasArrowMovedOrDeleted(aSTNode, charSequence) || wasParameterCommaMovedOrDeleted(aSTNode, charSequence)) ? false : true;
    }

    private static boolean wasArrowMovedOrDeleted(ASTNode aSTNode, CharSequence charSequence) {
        KtFunctionLiteral functionLiteral;
        PsiElement arrow;
        KtLambdaExpression ktLambdaExpressionFindLambdaExpression = findLambdaExpression(aSTNode);
        if (ktLambdaExpressionFindLambdaExpression == null || (arrow = (functionLiteral = ktLambdaExpressionFindLambdaExpression.getFunctionLiteral()).getArrow()) == null) {
            return false;
        }
        return hasTokenMoved(ktLambdaExpressionFindLambdaExpression.getText(), charSequence, arrow.getStartOffsetInParent() + functionLiteral.getStartOffsetInParent(), KtTokens.ARROW);
    }

    private static boolean wasParameterCommaMovedOrDeleted(ASTNode aSTNode, CharSequence charSequence) {
        KtParameterList valueParameterList;
        PsiElement firstComma;
        KtLambdaExpression ktLambdaExpressionFindLambdaExpression = findLambdaExpression(aSTNode);
        if (ktLambdaExpressionFindLambdaExpression == null || (valueParameterList = ktLambdaExpressionFindLambdaExpression.getFunctionLiteral().getValueParameterList()) == null || valueParameterList.getParameters().size() <= 1 || (firstComma = valueParameterList.getFirstComma()) == null) {
            return false;
        }
        return hasTokenMoved(ktLambdaExpressionFindLambdaExpression.getText(), charSequence, firstComma.getTextOffset() - ktLambdaExpressionFindLambdaExpression.getTextOffset(), KtTokens.COMMA);
    }

    private static KtLambdaExpression findLambdaExpression(ASTNode aSTNode) {
        KtLambdaExpression[] ktLambdaExpressionArr;
        if (aSTNode == null || (ktLambdaExpressionArr = (KtLambdaExpression[]) PsiTreeUtil.getChildrenOfType(aSTNode.getPsi(), KtLambdaExpression.class)) == null || ktLambdaExpressionArr.length != 1) {
            return null;
        }
        return ktLambdaExpressionArr[0];
    }

    private static boolean hasTokenMoved(String str, CharSequence charSequence, int i, IElementType iElementType) {
        KotlinLexer kotlinLexer = new KotlinLexer();
        kotlinLexer.start(str);
        KotlinLexer kotlinLexer2 = new KotlinLexer();
        kotlinLexer2.start(charSequence);
        while (true) {
            IElementType tokenType = kotlinLexer.getTokenType();
            if (tokenType == null) {
                return false;
            }
            IElementType tokenType2 = kotlinLexer2.getTokenType();
            if (tokenType2 == null) {
                return true;
            }
            if (tokenType2 != tokenType) {
                if (tokenType2 == KtTokens.WHITE_SPACE) {
                    kotlinLexer2.advance();
                } else {
                    if (tokenType != KtTokens.WHITE_SPACE) {
                        return true;
                    }
                    kotlinLexer.advance();
                }
            } else {
                if (tokenType == iElementType && kotlinLexer.getCurrentPosition().getOffset() == i) {
                    return false;
                }
                kotlinLexer.advance();
                kotlinLexer2.advance();
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.tree.IErrorCounterReparseableElementType
    public int getErrorsCount(CharSequence charSequence, Language language, Project project) {
        return ElementTypeUtils.getKotlinBlockImbalanceCount(charSequence);
    }
}
