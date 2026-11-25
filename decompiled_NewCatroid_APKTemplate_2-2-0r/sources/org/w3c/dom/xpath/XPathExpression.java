package org.w3c.dom.xpath;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes3.dex */
public interface XPathExpression {
    Object evaluate(Node node, short s, Object obj) throws DOMException, XPathException;
}
