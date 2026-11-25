package org.xmlunit.diff;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.w3c.dom.Attr;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.xmlunit.XMLUnitException;
import org.xmlunit.util.Convert;
import org.xmlunit.util.DocumentBuilderFactoryConfigurer;
import org.xmlunit.util.IterableNodeList;
import org.xmlunit.util.Linqy;
import org.xmlunit.util.Mapper;
import org.xmlunit.util.Nodes;

/* JADX INFO: loaded from: classes3.dex */
public final class DOMDifferenceEngine extends AbstractDifferenceEngine {
    private static final Mapper<Node, QName> QNAME_MAPPER = new Mapper<Node, QName>() { // from class: org.xmlunit.diff.DOMDifferenceEngine.1
        @Override // org.xmlunit.util.Mapper
        public QName apply(Node node) {
            return Nodes.getQName(node);
        }
    };
    private DocumentBuilderFactory documentBuilderFactory;

    public DOMDifferenceEngine() {
        this(DocumentBuilderFactoryConfigurer.Default.configure(DocumentBuilderFactory.newInstance()));
    }

    public DOMDifferenceEngine(DocumentBuilderFactory documentBuilderFactory) {
        if (documentBuilderFactory == null) {
            throw new IllegalArgumentException("factory must not be null");
        }
        this.documentBuilderFactory = documentBuilderFactory;
    }

    public void setDocumentBuilderFactory(DocumentBuilderFactory documentBuilderFactory) {
        if (documentBuilderFactory == null) {
            throw new IllegalArgumentException("factory must not be null");
        }
        this.documentBuilderFactory = documentBuilderFactory;
    }

    @Override // org.xmlunit.diff.DifferenceEngine
    public void compare(Source source, Source source2) {
        if (source == null) {
            throw new IllegalArgumentException("control must not be null");
        }
        if (source2 == null) {
            throw new IllegalArgumentException("test must not be null");
        }
        try {
            Node node = Convert.toNode(source, this.documentBuilderFactory);
            Node node2 = Convert.toNode(source2, this.documentBuilderFactory);
            compareNodes(node, xpathContextFor(node), node2, xpathContextFor(node2));
        } catch (Exception e) {
            throw new XMLUnitException("Caught exception during comparison", e);
        }
    }

    private XPathContext xpathContextFor(Node node) {
        return new XPathContext(getNamespaceContext(), node);
    }

    AbstractDifferenceEngine.ComparisonState compareNodes(final Node node, final XPathContext xPathContext, final Node node2, final XPathContext xPathContext2) {
        IterableNodeList iterableNodeList = new IterableNodeList(node.getChildNodes());
        Iterable<Node> iterableFilter = Linqy.filter(iterableNodeList, getNodeFilter());
        IterableNodeList iterableNodeList2 = new IterableNodeList(node2.getChildNodes());
        Iterable<Node> iterableFilter2 = Linqy.filter(iterableNodeList2, getNodeFilter());
        return compare(new Comparison(ComparisonType.NODE_TYPE, node, getXPath(xPathContext), Short.valueOf(node.getNodeType()), getParentXPath(xPathContext), node2, getXPath(xPathContext2), Short.valueOf(node2.getNodeType()), getParentXPath(xPathContext2))).andThen(new Comparison(ComparisonType.NAMESPACE_URI, node, getXPath(xPathContext), node.getNamespaceURI(), getParentXPath(xPathContext), node2, getXPath(xPathContext2), node2.getNamespaceURI(), getParentXPath(xPathContext2))).andThen(new Comparison(ComparisonType.NAMESPACE_PREFIX, node, getXPath(xPathContext), node.getPrefix(), getParentXPath(xPathContext), node2, getXPath(xPathContext2), node2.getPrefix(), getParentXPath(xPathContext2))).andIfTrueThen(node.getNodeType() != 2, new Comparison(ComparisonType.CHILD_NODELIST_LENGTH, node, getXPath(xPathContext), Integer.valueOf(Linqy.count(iterableFilter)), getParentXPath(xPathContext), node2, getXPath(xPathContext2), Integer.valueOf(Linqy.count(iterableFilter2)), getParentXPath(xPathContext2))).andThen(new AbstractDifferenceEngine.DeferredComparison() { // from class: org.xmlunit.diff.DOMDifferenceEngine.2
            @Override // org.xmlunit.diff.AbstractDifferenceEngine.DeferredComparison
            public AbstractDifferenceEngine.ComparisonState apply() {
                return DOMDifferenceEngine.this.nodeTypeSpecificComparison(node, xPathContext, node2, xPathContext2);
            }
        }).andIfTrueThen(node.getNodeType() != 2, compareChildren(xPathContext, iterableNodeList, iterableFilter, xPathContext2, iterableNodeList2, iterableFilter2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractDifferenceEngine.ComparisonState nodeTypeSpecificComparison(Node node, XPathContext xPathContext, Node node2, XPathContext xPathContext2) {
        switch (node.getNodeType()) {
            case 1:
                if (node2 instanceof Element) {
                    return compareElements((Element) node, xPathContext, (Element) node2, xPathContext2);
                }
                break;
            case 2:
                if (node2 instanceof Attr) {
                    return compareAttributes((Attr) node, xPathContext, (Attr) node2, xPathContext2);
                }
                break;
            case 3:
            case 4:
            case 8:
                if (node2 instanceof CharacterData) {
                    return compareCharacterData((CharacterData) node, xPathContext, (CharacterData) node2, xPathContext2);
                }
                break;
            case 7:
                if (node2 instanceof ProcessingInstruction) {
                    return compareProcessingInstructions((ProcessingInstruction) node, xPathContext, (ProcessingInstruction) node2, xPathContext2);
                }
                break;
            case 9:
                if (node2 instanceof Document) {
                    return compareDocuments((Document) node, xPathContext, (Document) node2, xPathContext2);
                }
                break;
            case 10:
                if (node2 instanceof DocumentType) {
                    return compareDocTypes((DocumentType) node, xPathContext, (DocumentType) node2, xPathContext2);
                }
                break;
        }
        return new AbstractDifferenceEngine.OngoingComparisonState(this);
    }

    private AbstractDifferenceEngine.DeferredComparison compareChildren(final XPathContext xPathContext, final Iterable<Node> iterable, final Iterable<Node> iterable2, final XPathContext xPathContext2, final Iterable<Node> iterable3, final Iterable<Node> iterable4) {
        return new AbstractDifferenceEngine.DeferredComparison() { // from class: org.xmlunit.diff.DOMDifferenceEngine.3
            @Override // org.xmlunit.diff.AbstractDifferenceEngine.DeferredComparison
            public AbstractDifferenceEngine.ComparisonState apply() {
                xPathContext.setChildren(Linqy.map(iterable, ElementSelectors.TO_NODE_INFO));
                xPathContext2.setChildren(Linqy.map(iterable3, ElementSelectors.TO_NODE_INFO));
                return DOMDifferenceEngine.this.compareNodeLists(iterable, iterable2, xPathContext, iterable3, iterable4, xPathContext2);
            }
        };
    }

    private AbstractDifferenceEngine.ComparisonState compareCharacterData(CharacterData characterData, XPathContext xPathContext, CharacterData characterData2, XPathContext xPathContext2) {
        return compare(new Comparison(ComparisonType.TEXT_VALUE, characterData, getXPath(xPathContext), characterData.getData(), getParentXPath(xPathContext), characterData2, getXPath(xPathContext2), characterData2.getData(), getParentXPath(xPathContext2)));
    }

    private AbstractDifferenceEngine.ComparisonState compareDocuments(Document document, final XPathContext xPathContext, Document document2, final XPathContext xPathContext2) {
        final DocumentType documentType = (DocumentType) filterNode(document.getDoctype());
        final DocumentType documentType2 = (DocumentType) filterNode(document2.getDoctype());
        return compare(new Comparison(ComparisonType.HAS_DOCTYPE_DECLARATION, document, getXPath(xPathContext), Boolean.valueOf(documentType != null), getParentXPath(xPathContext), document2, getXPath(xPathContext2), Boolean.valueOf(documentType2 != null), getParentXPath(xPathContext2))).andIfTrueThen((documentType == null || documentType2 == null) ? false : true, new AbstractDifferenceEngine.DeferredComparison() { // from class: org.xmlunit.diff.DOMDifferenceEngine.4
            @Override // org.xmlunit.diff.AbstractDifferenceEngine.DeferredComparison
            public AbstractDifferenceEngine.ComparisonState apply() {
                return DOMDifferenceEngine.this.compareNodes(documentType, xPathContext, documentType2, xPathContext2);
            }
        }).andThen(compareDeclarations(document, xPathContext, document2, xPathContext2));
    }

    private <T extends Node> T filterNode(T t) {
        if (t == null || !getNodeFilter().test(t)) {
            return null;
        }
        return t;
    }

    private AbstractDifferenceEngine.ComparisonState compareDocTypes(DocumentType documentType, XPathContext xPathContext, DocumentType documentType2, XPathContext xPathContext2) {
        return compare(new Comparison(ComparisonType.DOCTYPE_NAME, documentType, getXPath(xPathContext), documentType.getName(), getParentXPath(xPathContext), documentType2, getXPath(xPathContext2), documentType2.getName(), getParentXPath(xPathContext2))).andThen(new Comparison(ComparisonType.DOCTYPE_PUBLIC_ID, documentType, getXPath(xPathContext), documentType.getPublicId(), getParentXPath(xPathContext), documentType2, getXPath(xPathContext2), documentType2.getPublicId(), getParentXPath(xPathContext2))).andThen(new Comparison(ComparisonType.DOCTYPE_SYSTEM_ID, documentType, null, documentType.getSystemId(), null, documentType2, null, documentType2.getSystemId(), null));
    }

    private AbstractDifferenceEngine.DeferredComparison compareDeclarations(final Document document, final XPathContext xPathContext, final Document document2, final XPathContext xPathContext2) {
        return new AbstractDifferenceEngine.DeferredComparison() { // from class: org.xmlunit.diff.DOMDifferenceEngine.5
            @Override // org.xmlunit.diff.AbstractDifferenceEngine.DeferredComparison
            public AbstractDifferenceEngine.ComparisonState apply() {
                return DOMDifferenceEngine.this.compare(new Comparison(ComparisonType.XML_VERSION, document, AbstractDifferenceEngine.getXPath(xPathContext), document.getXmlVersion(), AbstractDifferenceEngine.getParentXPath(xPathContext), document2, AbstractDifferenceEngine.getXPath(xPathContext2), document2.getXmlVersion(), AbstractDifferenceEngine.getParentXPath(xPathContext2))).andThen(new Comparison(ComparisonType.XML_STANDALONE, document, AbstractDifferenceEngine.getXPath(xPathContext), Boolean.valueOf(document.getXmlStandalone()), AbstractDifferenceEngine.getParentXPath(xPathContext), document2, AbstractDifferenceEngine.getXPath(xPathContext2), Boolean.valueOf(document2.getXmlStandalone()), AbstractDifferenceEngine.getParentXPath(xPathContext2))).andThen(new Comparison(ComparisonType.XML_ENCODING, document, AbstractDifferenceEngine.getXPath(xPathContext), document.getXmlEncoding(), AbstractDifferenceEngine.getParentXPath(xPathContext), document2, AbstractDifferenceEngine.getXPath(xPathContext2), document2.getXmlEncoding(), AbstractDifferenceEngine.getParentXPath(xPathContext2)));
            }
        };
    }

    private AbstractDifferenceEngine.ComparisonState compareElements(final Element element, final XPathContext xPathContext, final Element element2, final XPathContext xPathContext2) {
        return compare(new Comparison(ComparisonType.ELEMENT_TAG_NAME, element, getXPath(xPathContext), Nodes.getQName(element).getLocalPart(), getParentXPath(xPathContext), element2, getXPath(xPathContext2), Nodes.getQName(element2).getLocalPart(), getParentXPath(xPathContext2))).andThen(new AbstractDifferenceEngine.DeferredComparison() { // from class: org.xmlunit.diff.DOMDifferenceEngine.6
            @Override // org.xmlunit.diff.AbstractDifferenceEngine.DeferredComparison
            public AbstractDifferenceEngine.ComparisonState apply() {
                return DOMDifferenceEngine.this.compareElementAttributes(element, xPathContext, element2, xPathContext2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractDifferenceEngine.ComparisonState compareElementAttributes(Element element, final XPathContext xPathContext, Element element2, final XPathContext xPathContext2) {
        final Attributes attributesSplitAttributes = splitAttributes(element.getAttributes());
        List list = attributesSplitAttributes.remainingAttributes;
        Mapper<Node, QName> mapper = QNAME_MAPPER;
        xPathContext.addAttributes(Linqy.map(list, mapper));
        final Attributes attributesSplitAttributes2 = splitAttributes(element2.getAttributes());
        xPathContext2.addAttributes(Linqy.map(attributesSplitAttributes2.remainingAttributes, mapper));
        return compare(new Comparison(ComparisonType.ELEMENT_NUM_ATTRIBUTES, element, getXPath(xPathContext), Integer.valueOf(attributesSplitAttributes.remainingAttributes.size()), getParentXPath(xPathContext), element2, getXPath(xPathContext2), Integer.valueOf(attributesSplitAttributes2.remainingAttributes.size()), getParentXPath(xPathContext2))).andThen(new AbstractDifferenceEngine.DeferredComparison() { // from class: org.xmlunit.diff.DOMDifferenceEngine.7
            @Override // org.xmlunit.diff.AbstractDifferenceEngine.DeferredComparison
            public AbstractDifferenceEngine.ComparisonState apply() {
                return DOMDifferenceEngine.this.compareXsiType(attributesSplitAttributes.type, xPathContext, attributesSplitAttributes2.type, xPathContext2);
            }
        }).andThen(new Comparison(ComparisonType.SCHEMA_LOCATION, element, getXPath(xPathContext), attributesSplitAttributes.schemaLocation != null ? attributesSplitAttributes.schemaLocation.getValue() : null, getParentXPath(xPathContext), element2, getXPath(xPathContext2), attributesSplitAttributes2.schemaLocation != null ? attributesSplitAttributes2.schemaLocation.getValue() : null, getParentXPath(xPathContext2))).andThen(new Comparison(ComparisonType.NO_NAMESPACE_SCHEMA_LOCATION, element, getXPath(xPathContext), attributesSplitAttributes.noNamespaceSchemaLocation != null ? attributesSplitAttributes.noNamespaceSchemaLocation.getValue() : null, getParentXPath(xPathContext), element2, getXPath(xPathContext2), attributesSplitAttributes2.noNamespaceSchemaLocation != null ? attributesSplitAttributes2.noNamespaceSchemaLocation.getValue() : null, getParentXPath(xPathContext2))).andThen(new NormalAttributeComparer(element, xPathContext, attributesSplitAttributes, element2, xPathContext2, attributesSplitAttributes2));
    }

    private class NormalAttributeComparer implements AbstractDifferenceEngine.DeferredComparison {
        private final Element control;
        private final Attributes controlAttributes;
        private final XPathContext controlContext;
        private final Set<Attr> foundTestAttributes;
        private final Element test;
        private final Attributes testAttributes;
        private final XPathContext testContext;

        private NormalAttributeComparer(Element element, XPathContext xPathContext, Attributes attributes, Element element2, XPathContext xPathContext2, Attributes attributes2) {
            this.foundTestAttributes = new HashSet();
            this.control = element;
            this.controlContext = xPathContext;
            this.controlAttributes = attributes;
            this.test = element2;
            this.testContext = xPathContext2;
            this.testAttributes = attributes2;
        }

        @Override // org.xmlunit.diff.AbstractDifferenceEngine.DeferredComparison
        public AbstractDifferenceEngine.ComparisonState apply() {
            AbstractDifferenceEngine.ComparisonState ongoingComparisonState = new AbstractDifferenceEngine.OngoingComparisonState(DOMDifferenceEngine.this);
            Iterator it = this.controlAttributes.remainingAttributes.iterator();
            while (it.hasNext()) {
                final Attr attr = (Attr) it.next();
                QName qName = Nodes.getQName(attr);
                final Attr attrFindMatchingAttr = DOMDifferenceEngine.findMatchingAttr(this.testAttributes.remainingAttributes, attr);
                QName qName2 = attrFindMatchingAttr != null ? Nodes.getQName(attrFindMatchingAttr) : null;
                this.controlContext.navigateToAttribute(qName);
                try {
                    Iterator it2 = it;
                    ongoingComparisonState = ongoingComparisonState.andThen(new Comparison(ComparisonType.ATTR_NAME_LOOKUP, this.control, AbstractDifferenceEngine.getXPath(this.controlContext), qName, AbstractDifferenceEngine.getParentXPath(this.controlContext), this.test, AbstractDifferenceEngine.getXPath(this.testContext), qName2, AbstractDifferenceEngine.getParentXPath(this.testContext)));
                    if (attrFindMatchingAttr != null) {
                        this.testContext.navigateToAttribute(qName2);
                        try {
                            ongoingComparisonState = ongoingComparisonState.andThen(new AbstractDifferenceEngine.DeferredComparison() { // from class: org.xmlunit.diff.DOMDifferenceEngine.NormalAttributeComparer.1
                                @Override // org.xmlunit.diff.AbstractDifferenceEngine.DeferredComparison
                                public AbstractDifferenceEngine.ComparisonState apply() {
                                    return DOMDifferenceEngine.this.compareNodes(attr, NormalAttributeComparer.this.controlContext, attrFindMatchingAttr, NormalAttributeComparer.this.testContext);
                                }
                            });
                            this.foundTestAttributes.add(attrFindMatchingAttr);
                            this.testContext.navigateToParent();
                        } catch (Throwable th) {
                            this.testContext.navigateToParent();
                            throw th;
                        }
                    }
                    this.controlContext.navigateToParent();
                    it = it2;
                } catch (Throwable th2) {
                    this.controlContext.navigateToParent();
                    throw th2;
                }
            }
            return ongoingComparisonState.andThen(new ControlAttributePresentComparer(this.control, this.controlContext, this.test, this.testContext, this.testAttributes, this.foundTestAttributes));
        }
    }

    private class ControlAttributePresentComparer implements AbstractDifferenceEngine.DeferredComparison {
        private final Element control;
        private final XPathContext controlContext;
        private final Set<Attr> foundTestAttributes;
        private final Element test;
        private final Attributes testAttributes;
        private final XPathContext testContext;

        private ControlAttributePresentComparer(Element element, XPathContext xPathContext, Element element2, XPathContext xPathContext2, Attributes attributes, Set<Attr> set) {
            this.control = element;
            this.controlContext = xPathContext;
            this.test = element2;
            this.testContext = xPathContext2;
            this.testAttributes = attributes;
            this.foundTestAttributes = set;
        }

        @Override // org.xmlunit.diff.AbstractDifferenceEngine.DeferredComparison
        public AbstractDifferenceEngine.ComparisonState apply() {
            AbstractDifferenceEngine.ComparisonState ongoingComparisonState = new AbstractDifferenceEngine.OngoingComparisonState(DOMDifferenceEngine.this);
            for (Attr attr : this.testAttributes.remainingAttributes) {
                if (!this.foundTestAttributes.contains(attr)) {
                    QName qName = Nodes.getQName(attr);
                    this.testContext.navigateToAttribute(qName);
                    try {
                        ongoingComparisonState = ongoingComparisonState.andThen(new Comparison(ComparisonType.ATTR_NAME_LOOKUP, this.control, AbstractDifferenceEngine.getXPath(this.controlContext), null, AbstractDifferenceEngine.getParentXPath(this.controlContext), this.test, AbstractDifferenceEngine.getXPath(this.testContext), qName, AbstractDifferenceEngine.getParentXPath(this.testContext)));
                        this.testContext.navigateToParent();
                    } catch (Throwable th) {
                        this.testContext.navigateToParent();
                        throw th;
                    }
                }
            }
            return ongoingComparisonState;
        }
    }

    private AbstractDifferenceEngine.ComparisonState compareProcessingInstructions(ProcessingInstruction processingInstruction, XPathContext xPathContext, ProcessingInstruction processingInstruction2, XPathContext xPathContext2) {
        return compare(new Comparison(ComparisonType.PROCESSING_INSTRUCTION_TARGET, processingInstruction, getXPath(xPathContext), processingInstruction.getTarget(), getParentXPath(xPathContext), processingInstruction2, getXPath(xPathContext2), processingInstruction2.getTarget(), getParentXPath(xPathContext2))).andThen(new Comparison(ComparisonType.PROCESSING_INSTRUCTION_DATA, processingInstruction, getXPath(xPathContext), processingInstruction.getData(), getParentXPath(xPathContext), processingInstruction2, getXPath(xPathContext2), processingInstruction2.getData(), getParentXPath(xPathContext2)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractDifferenceEngine.ComparisonState compareNodeLists(Iterable<Node> iterable, Iterable<Node> iterable2, final XPathContext xPathContext, Iterable<Node> iterable3, Iterable<Node> iterable4, final XPathContext xPathContext2) {
        AbstractDifferenceEngine.ComparisonState ongoingComparisonState = new AbstractDifferenceEngine.OngoingComparisonState(this);
        Iterable<Map.Entry<Node, Node>> iterableMatch = getNodeMatcher().match(iterable2, iterable4);
        List listAsList = Linqy.asList(iterable2);
        List listAsList2 = Linqy.asList(iterable4);
        Map<Node, Integer> mapIndex = index(iterable);
        Map<Node, Integer> mapIndex2 = index(iterable3);
        Map<Node, Integer> mapIndex3 = index(listAsList);
        Map<Node, Integer> mapIndex4 = index(listAsList2);
        HashSet hashSet = new HashSet();
        for (Map.Entry<Node, Node> entry : iterableMatch) {
            final Node key = entry.getKey();
            hashSet.add(key);
            final Node value = entry.getValue();
            hashSet.add(value);
            Integer num = mapIndex.get(key);
            Integer num2 = mapIndex2.get(value);
            Integer num3 = mapIndex3.get(key);
            Integer num4 = mapIndex4.get(value);
            if (num == null || num2 == null || num3 == null || num4 == null) {
                throw new NullPointerException("failed to look up index for pair " + entry);
            }
            xPathContext.navigateToChild(num.intValue());
            xPathContext2.navigateToChild(num2.intValue());
            try {
                ongoingComparisonState = ongoingComparisonState.andThen(new Comparison(ComparisonType.CHILD_NODELIST_SEQUENCE, key, getXPath(xPathContext), Integer.valueOf(num3.intValue()), getParentXPath(xPathContext), value, getXPath(xPathContext2), Integer.valueOf(num4.intValue()), getParentXPath(xPathContext2))).andThen(new AbstractDifferenceEngine.DeferredComparison() { // from class: org.xmlunit.diff.DOMDifferenceEngine.8
                    @Override // org.xmlunit.diff.AbstractDifferenceEngine.DeferredComparison
                    public AbstractDifferenceEngine.ComparisonState apply() {
                        return DOMDifferenceEngine.this.compareNodes(key, xPathContext, value, xPathContext2);
                    }
                });
                xPathContext2.navigateToParent();
                xPathContext.navigateToParent();
            } catch (Throwable th) {
                xPathContext2.navigateToParent();
                xPathContext.navigateToParent();
                throw th;
            }
        }
        return ongoingComparisonState.andThen(new UnmatchedControlNodes(mapIndex, listAsList, xPathContext, hashSet, xPathContext2)).andThen(new UnmatchedTestNodes(mapIndex2, listAsList2, xPathContext2, hashSet, xPathContext));
    }

    private class UnmatchedControlNodes implements AbstractDifferenceEngine.DeferredComparison {
        private final XPathContext controlContext;
        private final List<Node> controlList;
        private final Map<Node, Integer> controlListForXpathIndex;
        private final Set<Node> seen;
        private final XPathContext testContext;

        private UnmatchedControlNodes(Map<Node, Integer> map, List<Node> list, XPathContext xPathContext, Set<Node> set, XPathContext xPathContext2) {
            this.controlListForXpathIndex = map;
            this.controlList = list;
            this.controlContext = xPathContext;
            this.seen = set;
            this.testContext = xPathContext2;
        }

        @Override // org.xmlunit.diff.AbstractDifferenceEngine.DeferredComparison
        public AbstractDifferenceEngine.ComparisonState apply() {
            AbstractDifferenceEngine.ComparisonState ongoingComparisonState = new AbstractDifferenceEngine.OngoingComparisonState(DOMDifferenceEngine.this);
            int size = this.controlList.size();
            for (int i = 0; i < size; i++) {
                if (!this.seen.contains(this.controlList.get(i))) {
                    this.controlContext.navigateToChild(this.controlListForXpathIndex.get(this.controlList.get(i)).intValue());
                    try {
                        ongoingComparisonState = ongoingComparisonState.andThen(new Comparison(ComparisonType.CHILD_LOOKUP, this.controlList.get(i), AbstractDifferenceEngine.getXPath(this.controlContext), Nodes.getQName(this.controlList.get(i)), AbstractDifferenceEngine.getParentXPath(this.controlContext), null, null, null, AbstractDifferenceEngine.getXPath(this.testContext)));
                        this.controlContext.navigateToParent();
                    } catch (Throwable th) {
                        this.controlContext.navigateToParent();
                        throw th;
                    }
                }
            }
            return ongoingComparisonState;
        }
    }

    private class UnmatchedTestNodes implements AbstractDifferenceEngine.DeferredComparison {
        private final XPathContext controlContext;
        private final Set<Node> seen;
        private final XPathContext testContext;
        private final List<Node> testList;
        private final Map<Node, Integer> testListForXpathIndex;

        private UnmatchedTestNodes(Map<Node, Integer> map, List<Node> list, XPathContext xPathContext, Set<Node> set, XPathContext xPathContext2) {
            this.testListForXpathIndex = map;
            this.testList = list;
            this.testContext = xPathContext;
            this.seen = set;
            this.controlContext = xPathContext2;
        }

        @Override // org.xmlunit.diff.AbstractDifferenceEngine.DeferredComparison
        public AbstractDifferenceEngine.ComparisonState apply() {
            AbstractDifferenceEngine.ComparisonState ongoingComparisonState = new AbstractDifferenceEngine.OngoingComparisonState(DOMDifferenceEngine.this);
            int size = this.testList.size();
            for (int i = 0; i < size; i++) {
                if (!this.seen.contains(this.testList.get(i))) {
                    this.testContext.navigateToChild(this.testListForXpathIndex.get(this.testList.get(i)).intValue());
                    try {
                        ongoingComparisonState = ongoingComparisonState.andThen(new Comparison(ComparisonType.CHILD_LOOKUP, null, null, null, AbstractDifferenceEngine.getXPath(this.controlContext), this.testList.get(i), AbstractDifferenceEngine.getXPath(this.testContext), Nodes.getQName(this.testList.get(i)), AbstractDifferenceEngine.getParentXPath(this.testContext)));
                        this.testContext.navigateToParent();
                    } catch (Throwable th) {
                        this.testContext.navigateToParent();
                        throw th;
                    }
                }
            }
            return ongoingComparisonState;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AbstractDifferenceEngine.ComparisonState compareXsiType(Attr attr, XPathContext xPathContext, Attr attr2, XPathContext xPathContext2) {
        QName qName;
        boolean z = attr != null;
        boolean z2 = attr2 != null;
        if (!z && !z2) {
            return new AbstractDifferenceEngine.OngoingComparisonState(this);
        }
        boolean z3 = z && z2;
        QName qName2 = null;
        if (z) {
            try {
                QName qName3 = Nodes.getQName(attr);
                xPathContext.addAttribute(qName3);
                xPathContext.navigateToAttribute(qName3);
                qName = qName3;
            } finally {
                if (z) {
                    xPathContext.navigateToParent();
                }
                if (z2) {
                    xPathContext2.navigateToParent();
                }
            }
        } else {
            qName = null;
        }
        if (z2) {
            qName2 = Nodes.getQName(attr2);
            xPathContext2.addAttribute(qName2);
            xPathContext2.navigateToAttribute(qName2);
        }
        return compare(new Comparison(ComparisonType.ATTR_NAME_LOOKUP, attr, getXPath(xPathContext), qName, getParentXPath(xPathContext), attr2, getXPath(xPathContext2), qName2, getParentXPath(xPathContext2))).andIfTrueThen(z3, compareAttributeExplicitness(attr, xPathContext, attr2, xPathContext2)).andIfTrueThen(z3, new Comparison(ComparisonType.ATTR_VALUE, attr, getXPath(xPathContext), valueAsQName(attr), getParentXPath(xPathContext), attr2, getXPath(xPathContext2), valueAsQName(attr2), getParentXPath(xPathContext2)));
    }

    private AbstractDifferenceEngine.ComparisonState compareAttributes(Attr attr, XPathContext xPathContext, Attr attr2, XPathContext xPathContext2) {
        return compareAttributeExplicitness(attr, xPathContext, attr2, xPathContext2).apply().andThen(new Comparison(ComparisonType.ATTR_VALUE, attr, getXPath(xPathContext), attr.getValue(), getParentXPath(xPathContext), attr2, getXPath(xPathContext2), attr2.getValue(), getParentXPath(xPathContext2)));
    }

    private AbstractDifferenceEngine.DeferredComparison compareAttributeExplicitness(final Attr attr, final XPathContext xPathContext, final Attr attr2, final XPathContext xPathContext2) {
        return new AbstractDifferenceEngine.DeferredComparison() { // from class: org.xmlunit.diff.DOMDifferenceEngine.9
            @Override // org.xmlunit.diff.AbstractDifferenceEngine.DeferredComparison
            public AbstractDifferenceEngine.ComparisonState apply() {
                return DOMDifferenceEngine.this.compare(new Comparison(ComparisonType.ATTR_VALUE_EXPLICITLY_SPECIFIED, attr, AbstractDifferenceEngine.getXPath(xPathContext), Boolean.valueOf(attr.getSpecified()), AbstractDifferenceEngine.getParentXPath(xPathContext), attr2, AbstractDifferenceEngine.getXPath(xPathContext2), Boolean.valueOf(attr2.getSpecified()), AbstractDifferenceEngine.getParentXPath(xPathContext2)));
            }
        };
    }

    private Attributes splitAttributes(NamedNodeMap namedNodeMap) {
        Attr attr = (Attr) namedNodeMap.getNamedItemNS(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "schemaLocation");
        Attr attr2 = (Attr) namedNodeMap.getNamedItemNS(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "noNamespaceSchemaLocation");
        Attr attr3 = (Attr) namedNodeMap.getNamedItemNS(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, ModuleXmlParser.TYPE);
        LinkedList linkedList = new LinkedList();
        int length = namedNodeMap.getLength();
        for (int i = 0; i < length; i++) {
            Attr attr4 = (Attr) namedNodeMap.item(i);
            if (!XMLConstants.XMLNS_ATTRIBUTE_NS_URI.equals(attr4.getNamespaceURI()) && attr4 != attr && attr4 != attr2 && attr4 != attr3 && getAttributeFilter().test(attr4)) {
                linkedList.add(attr4);
            }
        }
        return new Attributes(attr, attr2, attr3, linkedList);
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0043  */
    private static QName valueAsQName(Attr attr) {
        String[] strArr;
        if (attr == null) {
            return null;
        }
        String[] strArrSplit = attr.getValue().split(":");
        if (strArrSplit.length < 2) {
            strArr = new String[]{null, strArrSplit[0]};
        } else {
            if (strArrSplit.length > 2) {
                strArr = new String[]{strArrSplit[0], attr.getValue().substring(strArrSplit[0].length() + 1)};
            }
            if ("".equals(strArrSplit[0])) {
                strArrSplit[0] = null;
            }
            return new QName(attr.lookupNamespaceURI(strArrSplit[0]), strArrSplit[1]);
        }
        strArrSplit = strArr;
        if ("".equals(strArrSplit[0])) {
            strArrSplit[0] = null;
        }
        return new QName(attr.lookupNamespaceURI(strArrSplit[0]), strArrSplit[1]);
    }

    private static class Attributes {
        private final Attr noNamespaceSchemaLocation;
        private final List<Attr> remainingAttributes;
        private final Attr schemaLocation;
        private final Attr type;

        private Attributes(Attr attr, Attr attr2, Attr attr3, List<Attr> list) {
            this.schemaLocation = attr;
            this.noNamespaceSchemaLocation = attr2;
            this.type = attr3;
            this.remainingAttributes = list;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Attr findMatchingAttr(List<Attr> list, Attr attr) {
        String name2;
        boolean z = attr.getNamespaceURI() != null;
        String namespaceURI = attr.getNamespaceURI();
        if (z) {
            name2 = attr.getLocalName();
        } else {
            name2 = attr.getName();
        }
        for (Attr attr2 : list) {
            if ((!z && attr2.getNamespaceURI() == null) || (z && namespaceURI.equals(attr2.getNamespaceURI()))) {
                if ((z && name2.equals(attr2.getLocalName())) || (!z && name2.equals(attr2.getName()))) {
                    return attr2;
                }
            }
        }
        return null;
    }

    private static Map<Node, Integer> index(Iterable<Node> iterable) {
        HashMap map = new HashMap();
        Iterator<Node> it = iterable.iterator();
        int i = 0;
        while (it.hasNext()) {
            map.put(it.next(), Integer.valueOf(i));
            i++;
        }
        return Collections.unmodifiableMap(map);
    }
}
