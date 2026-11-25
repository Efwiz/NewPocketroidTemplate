package org.xmlunit.diff;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import org.w3c.dom.Node;
import org.xmlunit.util.Mapper;

/* JADX INFO: loaded from: classes3.dex */
class ChildNodeXPathContextProvider implements Mapper<Node, XPathContext> {
    private final Map<Node, Integer> childIndex;
    private final XPathContext xpathContext;

    ChildNodeXPathContextProvider(XPathContext xPathContext, Iterable<Node> iterable) {
        this.xpathContext = xPathContext.clone();
        HashMap map = new HashMap();
        Iterator<Node> it = iterable.iterator();
        int i = 0;
        while (it.hasNext()) {
            map.put(it.next(), Integer.valueOf(i));
            i++;
        }
        this.childIndex = Collections.unmodifiableMap(map);
    }

    @Override // org.xmlunit.util.Mapper
    public XPathContext apply(Node node) {
        XPathContext xPathContextClone = this.xpathContext.clone();
        Integer num = this.childIndex.get(node);
        if (num == null) {
            throw new NoSuchElementException(node + " is not a known child node");
        }
        xPathContextClone.navigateToChild(num.intValue());
        return xPathContextClone;
    }
}
