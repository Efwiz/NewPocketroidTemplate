package org.w3c.dom.xpath;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes3.dex */
public interface XPathEvaluator {
    XPathExpression createExpression(String str, XPathNSResolver xPathNSResolver) throws DOMException, XPathException;

    XPathNSResolver createNSResolver(Node node);

    Object evaluate(String str, Node node, XPathNSResolver xPathNSResolver, short s, Object obj) throws DOMException, XPathException;
}
