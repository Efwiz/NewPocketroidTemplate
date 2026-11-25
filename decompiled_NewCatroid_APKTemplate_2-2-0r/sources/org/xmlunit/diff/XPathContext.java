package org.xmlunit.diff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.w3c.dom.Node;
import org.xmlunit.util.Linqy;
import org.xmlunit.util.Nodes;

/* JADX INFO: loaded from: classes3.dex */
public class XPathContext implements Cloneable {
    private static final String ATTR = "@";
    private static final String CLOSE = "]";
    private static final String COMMENT = "comment()";
    private static final String EMPTY = "";
    private static final String OPEN = "[";
    private static final String PI = "processing-instruction()";
    private static final String SEP = "/";
    private static final String TEXT = "text()";
    private Deque<Level> path;
    private final Map<String, String> uri2Prefix;

    public interface NodeInfo {
        QName getName();

        short getType();
    }

    public XPathContext() {
        this(null, null);
    }

    public XPathContext(Node node) {
        this(null, node);
    }

    public XPathContext(Map<String, String> map) {
        this(map, null);
    }

    public XPathContext(Map<String, String> map, Node node) {
        this.path = new LinkedList();
        if (map == null) {
            this.uri2Prefix = Collections.emptyMap();
        } else {
            this.uri2Prefix = Collections.unmodifiableMap(invert(map));
        }
        this.path.addLast(new Level(""));
        if (node != null) {
            setChildren(Linqy.singleton(new DOMNodeInfo(node)));
            navigateToChild(0);
        }
    }

    public void navigateToChild(int i) {
        Deque<Level> deque = this.path;
        deque.addLast((Level) deque.getLast().children.get(i));
    }

    public void navigateToAttribute(QName qName) {
        Deque<Level> deque = this.path;
        deque.addLast((Level) deque.getLast().attributes.get(qName));
    }

    public void navigateToParent() {
        this.path.removeLast();
    }

    public void addAttributes(Iterable<? extends QName> iterable) {
        Level last = this.path.getLast();
        for (QName qName : iterable) {
            last.attributes.put(qName, new Level("@" + getName(qName)));
        }
    }

    public void addAttribute(QName qName) {
        this.path.getLast().attributes.put(qName, new Level("@" + getName(qName)));
    }

    public void setChildren(Iterable<? extends NodeInfo> iterable) {
        this.path.getLast().children.clear();
        appendChildren(iterable);
    }

    public void appendChildren(Iterable<? extends NodeInfo> iterable) {
        Level level;
        Level last = this.path.getLast();
        HashMap map = new HashMap();
        Iterator it = last.children.iterator();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            String str = ((Level) it.next()).expression;
            if (str.startsWith(COMMENT)) {
                i++;
            } else if (str.startsWith(PI)) {
                i2++;
            } else if (str.startsWith(TEXT)) {
                i3++;
            } else {
                add1OrIncrement(str.substring(0, str.indexOf(OPEN)), map);
            }
        }
        for (NodeInfo nodeInfo : iterable) {
            short type = nodeInfo.getType();
            if (type == 1) {
                String name2 = getName(nodeInfo.getName());
                level = new Level(name2 + OPEN + add1OrIncrement(name2, map) + CLOSE);
            } else if (type == 3 || type == 4) {
                i3++;
                level = new Level("text()[" + i3 + CLOSE);
            } else if (type == 7) {
                i2++;
                level = new Level("processing-instruction()[" + i2 + CLOSE);
            } else if (type == 8) {
                i++;
                level = new Level("comment()[" + i + CLOSE);
            } else {
                level = new Level("");
            }
            last.children.add(level);
        }
    }

    public String getXPath() {
        return getXPath(this.path.descendingIterator());
    }

    public String getParentXPath() {
        Iterator<Level> itDescendingIterator = this.path.descendingIterator();
        if (itDescendingIterator.hasNext()) {
            itDescendingIterator.next();
        }
        return getXPath(itDescendingIterator);
    }

    public XPathContext clone() {
        try {
            XPathContext xPathContext = (XPathContext) super.clone();
            xPathContext.path = new LinkedList();
            Iterator<Level> it = this.path.iterator();
            while (it.hasNext()) {
                xPathContext.path.addLast(it.next().clone());
            }
            return xPathContext;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("XPathContext cannot be cloned?", e);
        }
    }

    private String getXPath(Iterator<Level> it) {
        if (!it.hasNext()) {
            return "";
        }
        Level next = it.next();
        if (next.xpath == null) {
            String xPath = getXPath(it);
            if (!"/".equals(xPath)) {
                xPath = xPath + "/";
            }
            next.xpath = xPath + next.expression;
        }
        return next.xpath;
    }

    private String getName(QName qName) {
        String namespaceURI = qName.getNamespaceURI();
        String str = namespaceURI != null ? this.uri2Prefix.get(namespaceURI) : null;
        return (str == null ? "" : str + ":") + qName.getLocalPart();
    }

    private static int add1OrIncrement(String str, Map<String, Integer> map) {
        Integer num = map.get(str);
        int iIntValue = num != null ? 1 + num.intValue() : 1;
        map.put(str, Integer.valueOf(iIntValue));
        return iIntValue;
    }

    private static class Level implements Cloneable {
        private Map<QName, Level> attributes;
        private List<Level> children;
        private final String expression;
        private String xpath;

        private Level(String str) {
            this.children = new ArrayList();
            this.attributes = new HashMap();
            this.expression = str;
        }

        public Level clone() {
            try {
                Level level = (Level) super.clone();
                level.children = new ArrayList(this.children.size());
                Iterator<Level> it = this.children.iterator();
                while (it.hasNext()) {
                    level.children.add(it.next().clone());
                }
                level.attributes = new HashMap(this.attributes.size());
                for (Map.Entry<QName, Level> entry : this.attributes.entrySet()) {
                    level.attributes.put(entry.getKey(), entry.getValue().clone());
                }
                return level;
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException("Level cannot be cloned?", e);
            }
        }
    }

    public static final class DOMNodeInfo implements NodeInfo {

        /* JADX INFO: renamed from: name, reason: collision with root package name */
        private final QName f1079name;
        private final short type;

        public DOMNodeInfo(Node node) {
            this.f1079name = Nodes.getQName(node);
            this.type = node.getNodeType();
        }

        @Override // org.xmlunit.diff.XPathContext.NodeInfo
        public QName getName() {
            return this.f1079name;
        }

        @Override // org.xmlunit.diff.XPathContext.NodeInfo
        public short getType() {
            return this.type;
        }
    }

    private static Map<String, String> invert(Map<String, String> map) {
        HashMap map2 = new HashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            map2.put(entry.getValue(), entry.getKey());
        }
        return map2;
    }
}
