package org.jetbrains.kotlin.com.intellij.util.graph;

import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: loaded from: classes3.dex */
public interface InboundSemiGraph<Node> {
    Iterator<Node> getIn(Node node);

    Collection<Node> getNodes();
}
