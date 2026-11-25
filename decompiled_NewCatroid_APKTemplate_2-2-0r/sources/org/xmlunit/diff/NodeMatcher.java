package org.xmlunit.diff;

import java.util.Map;
import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes3.dex */
public interface NodeMatcher {
    Iterable<Map.Entry<Node, Node>> match(Iterable<Node> iterable, Iterable<Node> iterable2);
}
