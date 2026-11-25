package org.jetbrains.kotlin.com.intellij.pom.tree.events.impl;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.util.Function;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class TreeChangeEventImpl$$ExternalSyntheticLambda0 implements Function {
    @Override // org.jetbrains.kotlin.com.intellij.util.Function
    public final Object fun(Object obj) {
        return ((ASTNode) obj).getTreeParent();
    }
}
