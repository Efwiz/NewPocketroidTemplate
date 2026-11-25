package org.jetbrains.kotlin.com.intellij.psi.tree;

import java.util.List;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LanguageExtension;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.util.ThreeState;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;

/* JADX INFO: loaded from: classes3.dex */
public interface CustomLanguageASTComparator {
    public static final LanguageExtension<CustomLanguageASTComparator> EXTENSION_POINT_NAME = new LanguageExtension<>("org.jetbrains.kotlin.com.intellij.tree.CustomLanguageASTComparator");

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "file", "org/jetbrains/kotlin/com/intellij/psi/tree/CustomLanguageASTComparator", "getMatchingComparators"));
    }

    ThreeState compareAST(ASTNode aSTNode, LighterASTNode lighterASTNode, FlyweightCapableTreeStructure<LighterASTNode> flyweightCapableTreeStructure);

    static List<CustomLanguageASTComparator> getMatchingComparators(PsiFile psiFile) {
        if (psiFile == null) {
            $$$reportNull$$$0(0);
        }
        return EXTENSION_POINT_NAME.allForLanguage(psiFile.getLanguage());
    }
}
