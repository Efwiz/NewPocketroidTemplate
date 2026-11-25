package org.xmlunit.diff;

import java.util.Collections;
import java.util.Map;
import javax.xml.namespace.QName;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xmlunit.util.IterableNodeList;
import org.xmlunit.util.Linqy;
import org.xmlunit.util.Mapper;
import org.xmlunit.util.Nodes;

/* JADX INFO: loaded from: classes3.dex */
public class RecursiveXPathBuilder implements Mapper<Node, XPathContext> {
    private Map<String, String> prefix2uri;

    public void setNamespaceContext(Map<String, String> map) {
        Map<String, String> mapUnmodifiableMap;
        if (map == null) {
            mapUnmodifiableMap = Collections.emptyMap();
        } else {
            mapUnmodifiableMap = Collections.unmodifiableMap(map);
        }
        this.prefix2uri = mapUnmodifiableMap;
    }

    @Override // org.xmlunit.util.Mapper
    public XPathContext apply(Node node) {
        if (node instanceof Attr) {
            return getXPathForAttribute((Attr) node);
        }
        return getXPathForNonAttribute(node);
    }

    private XPathContext getXPathForNonAttribute(Node node) {
        Node parentNode = node.getParentNode();
        if (parentNode == null || (parentNode instanceof Document)) {
            return new XPathContext(this.prefix2uri, node);
        }
        XPathContext xPathForNonAttribute = getXPathForNonAttribute(parentNode);
        IterableNodeList iterableNodeList = new IterableNodeList(parentNode.getChildNodes());
        xPathForNonAttribute.setChildren(Linqy.map(iterableNodeList, ElementSelectors.TO_NODE_INFO));
        return new ChildNodeXPathContextProvider(xPathForNonAttribute, iterableNodeList).apply(node);
    }

    private XPathContext getXPathForAttribute(Attr attr) {
        XPathContext xPathForNonAttribute = getXPathForNonAttribute(attr.getOwnerElement());
        QName qName = Nodes.getQName(attr);
        xPathForNonAttribute.addAttribute(qName);
        xPathForNonAttribute.navigateToAttribute(qName);
        return xPathForNonAttribute;
    }
}
