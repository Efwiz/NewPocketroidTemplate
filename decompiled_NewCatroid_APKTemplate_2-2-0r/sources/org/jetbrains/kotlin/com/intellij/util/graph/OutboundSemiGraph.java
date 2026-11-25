package org.jetbrains.kotlin.com.intellij.util.graph;

import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: classes3.dex */
public interface OutboundSemiGraph<Node> {
    Collection<Node> getNodes();

    Iterator<Node> getOut(Node node);
}
