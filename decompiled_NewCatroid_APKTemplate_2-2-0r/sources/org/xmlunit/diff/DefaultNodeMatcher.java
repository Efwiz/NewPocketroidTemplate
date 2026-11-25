package org.xmlunit.diff;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmlunit.util.Linqy;

/* JADX INFO: loaded from: classes3.dex */
public class DefaultNodeMatcher implements NodeMatcher {
    private static final short CDATA = 4;
    private static final short TEXT = 3;
    private final ElementSelector[] elementSelectors;
    private final NodeTypeMatcher nodeTypeMatcher;

    public static class DefaultNodeTypeMatcher implements NodeTypeMatcher {
        @Override // org.xmlunit.diff.DefaultNodeMatcher.NodeTypeMatcher
        public boolean canBeCompared(short s, short s2) {
            return s == s2 || (s == 4 && s2 == 3) || (s == 3 && s2 == 4);
        }
    }

    public interface NodeTypeMatcher {
        boolean canBeCompared(short s, short s2);
    }

    public DefaultNodeMatcher() {
        this(ElementSelectors.Default);
    }

    public DefaultNodeMatcher(ElementSelector... elementSelectorArr) {
        this(new DefaultNodeTypeMatcher(), elementSelectorArr);
    }

    public DefaultNodeMatcher(NodeTypeMatcher nodeTypeMatcher, ElementSelector... elementSelectorArr) {
        this.nodeTypeMatcher = nodeTypeMatcher;
        this.elementSelectors = elementSelectorArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.xmlunit.diff.NodeMatcher
    public Iterable<Map.Entry<Node, Node>> match(Iterable<Node> iterable, Iterable<Node> iterable2) {
        int i;
        Match match;
        int i2;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        List listAsList = Linqy.asList(iterable);
        List<Node> listAsList2 = Linqy.asList(iterable2);
        int size = listAsList2.size();
        HashSet hashSet = new HashSet();
        for (int i3 = 0; i3 < size; i3++) {
            hashSet.add(Integer.valueOf(i3));
        }
        int size2 = listAsList.size();
        HashSet hashSet2 = new HashSet();
        for (int i4 = 0; i4 < size2; i4++) {
            hashSet2.add(Integer.valueOf(i4));
        }
        ElementSelector[] elementSelectorArr = this.elementSelectors;
        int length = elementSelectorArr.length;
        int i5 = 0;
        while (i5 < length) {
            ElementSelector elementSelector = elementSelectorArr[i5];
            Match match2 = new Match(null, -1);
            int i6 = 0;
            while (i6 < size2) {
                if (hashSet2.contains(Integer.valueOf(i6))) {
                    Node node = (Node) listAsList.get(i6);
                    i = i6;
                    match = match2;
                    i2 = i5;
                    Match matchFindMatchingNode = findMatchingNode(node, listAsList2, match2.index, hashSet, elementSelector);
                    if (matchFindMatchingNode != null) {
                        hashSet2.remove(Integer.valueOf(i));
                        hashSet.remove(Integer.valueOf(matchFindMatchingNode.index));
                        linkedHashMap.put(node, matchFindMatchingNode.node);
                    }
                } else {
                    i = i6;
                    match = match2;
                    i2 = i5;
                }
                i6 = i + 1;
                match2 = match;
                i5 = i2;
            }
            i5++;
        }
        return linkedHashMap.entrySet();
    }

    private Match findMatchingNode(Node node, List<Node> list, int i, Set<Integer> set, ElementSelector elementSelector) {
        Match matchSearchIn = searchIn(node, list, set, i + 1, list.size(), elementSelector);
        return matchSearchIn != null ? matchSearchIn : searchIn(node, list, set, 0, i, elementSelector);
    }

    private Match searchIn(Node node, List<Node> list, Set<Integer> set, int i, int i2, ElementSelector elementSelector) {
        while (true) {
            if (i >= i2) {
                return null;
            }
            if (set.contains(Integer.valueOf(i)) && nodesMatch(node, list.get(i), elementSelector)) {
                return new Match(list.get(i), i);
            }
            i++;
        }
    }

    private boolean nodesMatch(Node node, Node node2, ElementSelector elementSelector) {
        if ((node instanceof Element) && (node2 instanceof Element)) {
            return elementSelector.canBeCompared((Element) node, (Element) node2);
        }
        return this.nodeTypeMatcher.canBeCompared(node.getNodeType(), node2.getNodeType());
    }

    private static class Match {
        private final int index;
        private final Node node;

        private Match(Node node, int i) {
            this.node = node;
            this.index = i;
        }
    }
}
