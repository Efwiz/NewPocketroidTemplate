package org.xmlunit.diff;

import java.util.AbstractMap;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/* JADX INFO: loaded from: classes3.dex */
public class ByNameAndTextRecSelector implements ElementSelector {
    @Override // org.xmlunit.diff.ElementSelector
    public boolean canBeCompared(Element element, Element element2) {
        if (!ElementSelectors.byNameAndText.canBeCompared(element, element2)) {
            return false;
        }
        NodeList childNodes = element.getChildNodes();
        NodeList childNodes2 = element2.getChildNodes();
        int length = childNodes.getLength();
        int length2 = childNodes2.getLength();
        int i = 0;
        int i2 = 0;
        while (i < length && i2 < length2) {
            Map.Entry<Integer, Node> entryFindNonText = findNonText(childNodes, i, length);
            int iIntValue = entryFindNonText.getKey().intValue();
            Node value = entryFindNonText.getValue();
            if (isText(value)) {
                i = iIntValue;
                break;
            }
            Map.Entry<Integer, Node> entryFindNonText2 = findNonText(childNodes2, i2, length2);
            int iIntValue2 = entryFindNonText2.getKey().intValue();
            Node value2 = entryFindNonText2.getValue();
            if (isText(value2)) {
                i = iIntValue;
                i2 = iIntValue2;
                break;
            }
            if (value.getNodeType() != value2.getNodeType()) {
                return false;
            }
            if ((value instanceof Element) && !canBeCompared((Element) value, (Element) value2)) {
                return false;
            }
            i = iIntValue + 1;
            i2 = iIntValue2 + 1;
        }
        if (i >= length || findNonText(childNodes, i, length).getKey().intValue() >= length) {
            return i2 >= length2 || findNonText(childNodes2, i2, length2).getKey().intValue() >= length2;
        }
        return false;
    }

    private static Map.Entry<Integer, Node> findNonText(NodeList nodeList, int i, int i2) {
        Node nodeItem = nodeList.item(i);
        while (isText(nodeItem) && (i = i + 1) < i2) {
            nodeItem = nodeList.item(i);
        }
        return new AbstractMap.SimpleImmutableEntry(Integer.valueOf(i), nodeItem);
    }

    private static boolean isText(Node node) {
        return node instanceof Text;
    }
}
