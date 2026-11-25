package org.jetbrains.kotlin.com.intellij.lang;

import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public interface LighterLazyParseableNode extends LighterASTNode {

    public interface Visitor {
        boolean visit(IElementType iElementType);
    }

    boolean accept(Visitor visitor);

    CharTable getCharTable();

    PsiFile getContainingFile();

    CharSequence getText();
}
