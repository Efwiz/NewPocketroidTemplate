package org.jetbrains.kotlin.com.intellij.psi.tree;

import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.lang.LighterLazyParseableNode;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;

/* JADX INFO: loaded from: classes3.dex */
public interface ILightLazyParseableElementType {
    FlyweightCapableTreeStructure<LighterASTNode> parseContents(LighterLazyParseableNode lighterLazyParseableNode);
}
