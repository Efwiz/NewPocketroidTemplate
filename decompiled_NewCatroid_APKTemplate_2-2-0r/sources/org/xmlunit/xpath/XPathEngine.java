package org.xmlunit.xpath;

import java.util.Map;
import javax.xml.transform.Source;
import org.w3c.dom.Node;

/* JADX INFO: loaded from: classes4.dex */
public interface XPathEngine {
    String evaluate(String str, Source source);

    String evaluate(String str, Node node);

    Iterable<Node> selectNodes(String str, Source source);

    Iterable<Node> selectNodes(String str, Node node);

    void setNamespaceContext(Map<String, String> map);
}
