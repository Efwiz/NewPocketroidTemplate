package org.jetbrains.kotlin.parsing;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.lang.PsiParser;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.io.FileUtilRt;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: loaded from: classes4.dex */
public class KotlinParser implements PsiParser {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "psiBuilder";
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[0] = "org/jetbrains/kotlin/parsing/KotlinParser";
                break;
            default:
                objArr[0] = "iElementType";
                break;
        }
        switch (i) {
            case 2:
                objArr[1] = "parse";
                break;
            case 3:
                objArr[1] = "parseTypeCodeFragment";
                break;
            case 4:
                objArr[1] = "parseExpressionCodeFragment";
                break;
            case 5:
                objArr[1] = "parseBlockCodeFragment";
                break;
            case 6:
                objArr[1] = "parseLambdaExpression";
                break;
            case 7:
                objArr[1] = "parseBlockExpression";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/parsing/KotlinParser";
                break;
        }
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                break;
            default:
                objArr[2] = "parse";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    public KotlinParser(Project project) {
    }

    @Override // org.jetbrains.kotlin.com.intellij.lang.PsiParser
    public ASTNode parse(IElementType iElementType, PsiBuilder psiBuilder) {
        if (iElementType == null) {
            $$$reportNull$$$0(0);
        }
        if (psiBuilder == null) {
            $$$reportNull$$$0(1);
        }
        throw new IllegalStateException("use another parse");
    }

    public static ASTNode parse(PsiBuilder psiBuilder, PsiFile psiFile) {
        KotlinParsing kotlinParsingCreateForTopLevel = KotlinParsing.createForTopLevel(new SemanticWhitespaceAwarePsiBuilderImpl(psiBuilder));
        String extension = FileUtilRt.getExtension(psiFile.getName());
        if (extension.isEmpty() || extension.equals(KotlinFileType.EXTENSION) || ((psiFile instanceof KtFile) && ((KtFile) psiFile).getIsCompiled())) {
            kotlinParsingCreateForTopLevel.parseFile();
        } else {
            kotlinParsingCreateForTopLevel.parseScript();
        }
        ASTNode treeBuilt = psiBuilder.getTreeBuilt();
        if (treeBuilt == null) {
            $$$reportNull$$$0(2);
        }
        return treeBuilt;
    }

    public static ASTNode parseTypeCodeFragment(PsiBuilder psiBuilder) {
        KotlinParsing.createForTopLevel(new SemanticWhitespaceAwarePsiBuilderImpl(psiBuilder)).parseTypeCodeFragment();
        ASTNode treeBuilt = psiBuilder.getTreeBuilt();
        if (treeBuilt == null) {
            $$$reportNull$$$0(3);
        }
        return treeBuilt;
    }

    public static ASTNode parseExpressionCodeFragment(PsiBuilder psiBuilder) {
        KotlinParsing.createForTopLevel(new SemanticWhitespaceAwarePsiBuilderImpl(psiBuilder)).parseExpressionCodeFragment();
        ASTNode treeBuilt = psiBuilder.getTreeBuilt();
        if (treeBuilt == null) {
            $$$reportNull$$$0(4);
        }
        return treeBuilt;
    }

    public static ASTNode parseBlockCodeFragment(PsiBuilder psiBuilder) {
        KotlinParsing.createForTopLevel(new SemanticWhitespaceAwarePsiBuilderImpl(psiBuilder)).parseBlockCodeFragment();
        ASTNode treeBuilt = psiBuilder.getTreeBuilt();
        if (treeBuilt == null) {
            $$$reportNull$$$0(5);
        }
        return treeBuilt;
    }

    public static ASTNode parseLambdaExpression(PsiBuilder psiBuilder) {
        KotlinParsing.createForTopLevel(new SemanticWhitespaceAwarePsiBuilderImpl(psiBuilder)).parseLambdaExpression();
        ASTNode treeBuilt = psiBuilder.getTreeBuilt();
        if (treeBuilt == null) {
            $$$reportNull$$$0(6);
        }
        return treeBuilt;
    }

    public static ASTNode parseBlockExpression(PsiBuilder psiBuilder) {
        KotlinParsing.createForTopLevel(new SemanticWhitespaceAwarePsiBuilderImpl(psiBuilder)).parseBlockExpression();
        ASTNode treeBuilt = psiBuilder.getTreeBuilt();
        if (treeBuilt == null) {
            $$$reportNull$$$0(7);
        }
        return treeBuilt;
    }
}
