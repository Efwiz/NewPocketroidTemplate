package org.jetbrains.kotlin.com.intellij.psi.impl.source.tree;

import java.util.Map;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;

/* JADX INFO: loaded from: classes3.dex */
public interface TreeCopyHandler {
    public static final ExtensionPointName<TreeCopyHandler> EP_NAME = ExtensionPointName.create("org.jetbrains.kotlin.com.intellij.treeCopyHandler");

    TreeElement decodeInformation(TreeElement treeElement, Map<Object, Object> map);

    void encodeInformation(TreeElement treeElement, ASTNode aSTNode, Map<Object, Object> map);
}
