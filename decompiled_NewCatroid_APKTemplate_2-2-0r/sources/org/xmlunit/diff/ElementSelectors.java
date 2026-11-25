package org.xmlunit.diff;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xmlunit.util.IsNullPredicate;
import org.xmlunit.util.Linqy;
import org.xmlunit.util.Mapper;
import org.xmlunit.util.Nodes;
import org.xmlunit.util.Predicate;
import org.xmlunit.xpath.JAXPXPathEngine;
import org.xmlunit.xpath.XPathEngine;

/* JADX INFO: loaded from: classes3.dex */
public final class ElementSelectors {
    private static final String ATTRIBUTES_MUST_NOT_BE_NULL = "attributes must not be null";
    private static final String ATTRIBUTES_MUST_NOT_CONTAIN_NULL_VALUES = "attributes must not contain null values";
    private static final String SELECTORS_MUST_NOT_BE_NULL = "selectors must not be null";
    public static final ElementSelector Default = new ElementSelector() { // from class: org.xmlunit.diff.ElementSelectors.1
        @Override // org.xmlunit.diff.ElementSelector
        public boolean canBeCompared(Element element, Element element2) {
            return true;
        }
    };
    public static final ElementSelector byName = new ElementSelector() { // from class: org.xmlunit.diff.ElementSelectors.2
        @Override // org.xmlunit.diff.ElementSelector
        public boolean canBeCompared(Element element, Element element2) {
            return (element == null || element2 == null || !ElementSelectors.bothNullOrEqual(Nodes.getQName(element), Nodes.getQName(element2))) ? false : true;
        }
    };
    public static final ElementSelector byNameAndText = new ElementSelector() { // from class: org.xmlunit.diff.ElementSelectors.3
        @Override // org.xmlunit.diff.ElementSelector
        public boolean canBeCompared(Element element, Element element2) {
            return ElementSelectors.byName.canBeCompared(element, element2) && ElementSelectors.bothNullOrEqual(Nodes.getMergedNestedText(element), Nodes.getMergedNestedText(element2));
        }
    };
    public static final ElementSelector byNameAndAllAttributes = new ElementSelector() { // from class: org.xmlunit.diff.ElementSelectors.4
        @Override // org.xmlunit.diff.ElementSelector
        public boolean canBeCompared(Element element, Element element2) {
            if (!ElementSelectors.byName.canBeCompared(element, element2)) {
                return false;
            }
            Map<QName, String> attributes = Nodes.getAttributes(element);
            Map<QName, String> attributes2 = Nodes.getAttributes(element2);
            if (attributes.size() != attributes2.size()) {
                return false;
            }
            return ElementSelectors.mapsEqualForKeys(attributes, attributes2, attributes.keySet());
        }
    };
    static final Mapper<Node, XPathContext.NodeInfo> TO_NODE_INFO = new Mapper<Node, XPathContext.NodeInfo>() { // from class: org.xmlunit.diff.ElementSelectors.5
        @Override // org.xmlunit.util.Mapper
        public XPathContext.NodeInfo apply(Node node) {
            return new XPathContext.DOMNodeInfo(node);
        }
    };

    public interface ConditionalSelectorBuilder {
        ElementSelector build();

        ConditionalSelectorBuilder elseUse(ElementSelector elementSelector);

        ConditionalSelectorBuilderThen when(Predicate<? super Element> predicate);

        ConditionalSelectorBuilderThen whenElementIsNamed(String str);

        ConditionalSelectorBuilderThen whenElementIsNamed(QName qName);
    }

    public interface ConditionalSelectorBuilderThen {
        ConditionalSelectorBuilder thenUse(ElementSelector elementSelector);
    }

    private ElementSelectors() {
    }

    public static ElementSelector byNameAndAttributes(String... strArr) {
        if (strArr == null) {
            throw new IllegalArgumentException(ATTRIBUTES_MUST_NOT_BE_NULL);
        }
        if (Linqy.any(Arrays.asList(strArr), new IsNullPredicate())) {
            throw new IllegalArgumentException(ATTRIBUTES_MUST_NOT_CONTAIN_NULL_VALUES);
        }
        QName[] qNameArr = new QName[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            qNameArr[i] = new QName(strArr[i]);
        }
        return byNameAndAttributes(qNameArr);
    }

    public static ElementSelector byNameAndAttributesControlNS(String... strArr) {
        if (strArr == null) {
            throw new IllegalArgumentException(ATTRIBUTES_MUST_NOT_BE_NULL);
        }
        List listAsList = Arrays.asList(strArr);
        if (Linqy.any(listAsList, new IsNullPredicate())) {
            throw new IllegalArgumentException(ATTRIBUTES_MUST_NOT_CONTAIN_NULL_VALUES);
        }
        final HashSet hashSet = new HashSet(listAsList);
        return new ElementSelector() { // from class: org.xmlunit.diff.ElementSelectors.6
            @Override // org.xmlunit.diff.ElementSelector
            public boolean canBeCompared(Element element, Element element2) {
                if (!ElementSelectors.byName.canBeCompared(element, element2)) {
                    return false;
                }
                Map<QName, String> attributes = Nodes.getAttributes(element);
                HashMap map = new HashMap();
                for (QName qName : attributes.keySet()) {
                    String localPart = qName.getLocalPart();
                    if (hashSet.contains(localPart)) {
                        map.put(localPart, qName);
                    }
                }
                for (String str : hashSet) {
                    if (((QName) map.get(str)) == null) {
                        map.put(str, new QName(str));
                    }
                }
                return ElementSelectors.mapsEqualForKeys(attributes, Nodes.getAttributes(element2), map.values());
            }
        };
    }

    public static ElementSelector byNameAndAttributes(QName... qNameArr) {
        if (qNameArr == null) {
            throw new IllegalArgumentException(ATTRIBUTES_MUST_NOT_BE_NULL);
        }
        final List listAsList = Arrays.asList(qNameArr);
        if (Linqy.any(listAsList, new IsNullPredicate())) {
            throw new IllegalArgumentException(ATTRIBUTES_MUST_NOT_CONTAIN_NULL_VALUES);
        }
        return new ElementSelector() { // from class: org.xmlunit.diff.ElementSelectors.7
            @Override // org.xmlunit.diff.ElementSelector
            public boolean canBeCompared(Element element, Element element2) {
                if (ElementSelectors.byName.canBeCompared(element, element2)) {
                    return ElementSelectors.mapsEqualForKeys(Nodes.getAttributes(element), Nodes.getAttributes(element2), listAsList);
                }
                return false;
            }
        };
    }

    public static ElementSelector not(final ElementSelector elementSelector) {
        if (elementSelector == null) {
            throw new IllegalArgumentException("es must not be null");
        }
        return new ElementSelector() { // from class: org.xmlunit.diff.ElementSelectors.8
            @Override // org.xmlunit.diff.ElementSelector
            public boolean canBeCompared(Element element, Element element2) {
                return !elementSelector.canBeCompared(element, element2);
            }
        };
    }

    public static ElementSelector or(ElementSelector... elementSelectorArr) {
        if (elementSelectorArr == null) {
            throw new IllegalArgumentException(SELECTORS_MUST_NOT_BE_NULL);
        }
        final List listAsList = Arrays.asList(elementSelectorArr);
        if (Linqy.any(listAsList, new IsNullPredicate())) {
            throw new IllegalArgumentException("selectors must not contain null values");
        }
        return new ElementSelector() { // from class: org.xmlunit.diff.ElementSelectors.9
            @Override // org.xmlunit.diff.ElementSelector
            public boolean canBeCompared(Element element, Element element2) {
                return Linqy.any(listAsList, new CanBeComparedPredicate(element, element2));
            }
        };
    }

    public static ElementSelector and(ElementSelector... elementSelectorArr) {
        if (elementSelectorArr == null) {
            throw new IllegalArgumentException(SELECTORS_MUST_NOT_BE_NULL);
        }
        final List listAsList = Arrays.asList(elementSelectorArr);
        if (Linqy.any(listAsList, new IsNullPredicate())) {
            throw new IllegalArgumentException("selectors must not contain null values");
        }
        return new ElementSelector() { // from class: org.xmlunit.diff.ElementSelectors.10
            @Override // org.xmlunit.diff.ElementSelector
            public boolean canBeCompared(Element element, Element element2) {
                return Linqy.all(listAsList, new CanBeComparedPredicate(element, element2));
            }
        };
    }

    public static ElementSelector xor(final ElementSelector elementSelector, final ElementSelector elementSelector2) {
        if (elementSelector == null || elementSelector2 == null) {
            throw new IllegalArgumentException(SELECTORS_MUST_NOT_BE_NULL);
        }
        return new ElementSelector() { // from class: org.xmlunit.diff.ElementSelectors.11
            @Override // org.xmlunit.diff.ElementSelector
            public boolean canBeCompared(Element element, Element element2) {
                return elementSelector2.canBeCompared(element, element2) ^ elementSelector.canBeCompared(element, element2);
            }
        };
    }

    public static ElementSelector conditionalSelector(final Predicate<? super Element> predicate, final ElementSelector elementSelector) {
        if (predicate == null) {
            throw new IllegalArgumentException("predicate must not be null");
        }
        if (elementSelector == null) {
            throw new IllegalArgumentException("es must not be null");
        }
        return new ElementSelector() { // from class: org.xmlunit.diff.ElementSelectors.12
            @Override // org.xmlunit.diff.ElementSelector
            public boolean canBeCompared(Element element, Element element2) {
                return predicate.test(element) && elementSelector.canBeCompared(element, element2);
            }
        };
    }

    public static ElementSelector selectorForElementNamed(String str, ElementSelector elementSelector) {
        if (str == null) {
            throw new IllegalArgumentException("expectedName must not be null");
        }
        return conditionalSelector(elementNamePredicate(str), elementSelector);
    }

    public static ElementSelector selectorForElementNamed(QName qName, ElementSelector elementSelector) {
        if (qName == null) {
            throw new IllegalArgumentException("expectedName must not be null");
        }
        return conditionalSelector(elementNamePredicate(qName), elementSelector);
    }

    public static ElementSelector byXPath(String str, ElementSelector elementSelector) {
        return byXPath(str, null, null, elementSelector);
    }

    public static ElementSelector byXPath(String str, XPathEngine xPathEngine, ElementSelector elementSelector) {
        return byXPath(str, xPathEngine, null, elementSelector);
    }

    public static ElementSelector byXPath(String str, Map<String, String> map, ElementSelector elementSelector) {
        return byXPath(str, null, map, elementSelector);
    }

    public static ElementSelector byXPath(final String str, final XPathEngine xPathEngine, Map<String, String> map, ElementSelector elementSelector) {
        if (xPathEngine == null) {
            xPathEngine = new JAXPXPathEngine();
        }
        if (map != null) {
            xPathEngine.setNamespaceContext(map);
        }
        final DefaultNodeMatcher defaultNodeMatcher = new DefaultNodeMatcher(elementSelector);
        return new ElementSelector() { // from class: org.xmlunit.diff.ElementSelectors.13
            @Override // org.xmlunit.diff.ElementSelector
            public boolean canBeCompared(Element element, Element element2) {
                Iterable<Node> iterableSelectNodes = xPathEngine.selectNodes(str, element);
                return Linqy.count(iterableSelectNodes) == Linqy.count(defaultNodeMatcher.match(iterableSelectNodes, xPathEngine.selectNodes(str, element2)));
            }
        };
    }

    public static ConditionalSelectorBuilder conditionalBuilder() {
        return new DefaultConditionalSelectorBuilder();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean bothNullOrEqual(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean mapsEqualForKeys(Map<QName, String> map, Map<QName, String> map2, Iterable<QName> iterable) {
        for (QName qName : iterable) {
            if (!bothNullOrEqual(map.get(qName), map2.get(qName))) {
                return false;
            }
        }
        return true;
    }

    static Predicate<Element> elementNamePredicate(final String str) {
        return new Predicate<Element>() { // from class: org.xmlunit.diff.ElementSelectors.14
            @Override // org.xmlunit.util.Predicate
            public boolean test(Element element) {
                if (element == null) {
                    return false;
                }
                String localName = element.getLocalName();
                if (localName == null) {
                    localName = element.getNodeName();
                }
                return str.equals(localName);
            }
        };
    }

    static Predicate<Element> elementNamePredicate(final QName qName) {
        return new Predicate<Element>() { // from class: org.xmlunit.diff.ElementSelectors.15
            @Override // org.xmlunit.util.Predicate
            public boolean test(Element element) {
                return element != null && qName.equals(Nodes.getQName(element));
            }
        };
    }

    private static class CanBeComparedPredicate implements Predicate<ElementSelector> {
        private final Element e1;
        private final Element e2;

        private CanBeComparedPredicate(Element element, Element element2) {
            this.e1 = element;
            this.e2 = element2;
        }

        @Override // org.xmlunit.util.Predicate
        public boolean test(ElementSelector elementSelector) {
            return elementSelector.canBeCompared(this.e1, this.e2);
        }
    }
}
