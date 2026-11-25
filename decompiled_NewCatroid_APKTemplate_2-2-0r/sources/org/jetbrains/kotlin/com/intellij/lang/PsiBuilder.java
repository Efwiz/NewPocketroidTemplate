package org.jetbrains.kotlin.com.intellij.lang;

import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolder;
import org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderUnprotected;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.com.intellij.util.diff.FlyweightCapableTreeStructure;

/* JADX INFO: loaded from: classes3.dex */
public interface PsiBuilder extends SyntaxTreeBuilder, UserDataHolder, UserDataHolderUnprotected {

    public interface Marker extends SyntaxTreeBuilder.Marker {
        void doneBefore(IElementType iElementType, Marker marker);

        void errorBefore(String str, Marker marker);

        Marker precede();
    }

    FlyweightCapableTreeStructure<LighterASTNode> getLightTree();

    Project getProject();

    ASTNode getTreeBuilt();

    /* JADX INFO: renamed from: mark */
    Marker m6462mark();
}
