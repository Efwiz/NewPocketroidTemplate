package org.jetbrains.kotlin.com.intellij.lang;

import org.jetbrains.kotlin.com.intellij.util.CharTable;

/* JADX INFO: loaded from: classes3.dex */
public interface FileASTNode extends ASTNode {
    CharTable getCharTable();

    LighterAST getLighterAST();
}
