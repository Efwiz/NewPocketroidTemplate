package org.xmlunit.diff;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/* JADX INFO: loaded from: classes3.dex */
public class MultiLevelByNameAndTextSelector implements ElementSelector {
    private final boolean ignoreEmptyTexts;
    private final int levels;

    public MultiLevelByNameAndTextSelector(int i) {
        this(i, false);
    }

    public MultiLevelByNameAndTextSelector(int i, boolean z) {
        if (i < 1) {
            throw new IllegalArgumentException("levels must be equal or greater than one");
        }
        this.levels = i;
        this.ignoreEmptyTexts = z;
    }

    @Override // org.xmlunit.diff.ElementSelector
    public boolean canBeCompared(Element element, Element element2) {
        for (int i = 0; i <= this.levels - 2; i++) {
            if (ElementSelectors.byName.canBeCompared(element, element2) && element.hasChildNodes() && element2.hasChildNodes()) {
                Node firstEligibleChild = getFirstEligibleChild(element);
                Node firstEligibleChild2 = getFirstEligibleChild(element2);
                if (firstEligibleChild.getNodeType() == 1 && firstEligibleChild2.getNodeType() == 1) {
                    element = (Element) firstEligibleChild;
                    element2 = (Element) firstEligibleChild2;
                }
            }
            return false;
        }
        return ElementSelectors.byNameAndText.canBeCompared(element, element2);
    }

    private Node getFirstEligibleChild(Node node) {
        Node nextSibling;
        Node firstChild = node.getFirstChild();
        if (this.ignoreEmptyTexts) {
            while (isText(firstChild) && firstChild.getNodeValue().trim().length() == 0 && (nextSibling = firstChild.getNextSibling()) != null) {
                firstChild = nextSibling;
            }
        }
        return firstChild;
    }

    private static boolean isText(Node node) {
        return node instanceof Text;
    }
}
