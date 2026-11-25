package org.jetbrains.kotlin.parsing;

import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilder;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;

/* JADX INFO: loaded from: classes4.dex */
public class KotlinLightParser {
    public static FlyweightCapableTreeStructure<LighterASTNode> parse(PsiBuilder psiBuilder) {
        KotlinParsing.createForTopLevelNonLazy(new SemanticWhitespaceAwarePsiBuilderImpl(psiBuilder)).parseFile();
        return psiBuilder.getLightTree();
    }
}
