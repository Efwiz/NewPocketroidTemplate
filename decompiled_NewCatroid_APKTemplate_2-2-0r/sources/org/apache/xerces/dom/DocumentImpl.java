package org.apache.xerces.dom;

import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import org.apache.xerces.dom.events.EventImpl;
import org.apache.xerces.dom.events.MouseEventImpl;
import org.apache.xerces.dom.events.MutationEventImpl;
import org.apache.xerces.dom.events.UIEventImpl;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.events.DocumentEvent;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventException;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.ranges.DocumentRange;
import org.w3c.dom.ranges.Range;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.w3c.dom.traversal.TreeWalker;

/* JADX INFO: loaded from: classes13.dex */
public class DocumentImpl extends CoreDocumentImpl implements DocumentTraversal, DocumentEvent, DocumentRange {
    static final long serialVersionUID = 515687835542616694L;
    protected Hashtable eventListeners;
    protected transient ReferenceQueue iteratorReferenceQueue;
    protected transient List iterators;
    protected boolean mutationEvents;
    protected transient ReferenceQueue rangeReferenceQueue;
    protected transient List ranges;
    EnclosingAttr savedEnclosingAttr;

    class EnclosingAttr implements Serializable {
        private static final long serialVersionUID = 5208387723391647216L;
        AttrImpl node;
        String oldvalue;

        EnclosingAttr() {
        }
    }

    class LEntry implements Serializable {
        private static final long serialVersionUID = -8426757059492421631L;
        EventListener listener;
        String type;
        boolean useCapture;

        LEntry(String str, EventListener eventListener, boolean z) {
            this.type = str;
            this.listener = eventListener;
            this.useCapture = z;
        }
    }

    public DocumentImpl() {
        this.mutationEvents = false;
    }

    public DocumentImpl(DocumentType documentType) {
        super(documentType);
        this.mutationEvents = false;
    }

    public DocumentImpl(DocumentType documentType, boolean z) {
        super(documentType, z);
        this.mutationEvents = false;
    }

    public DocumentImpl(boolean z) {
        super(z);
        this.mutationEvents = false;
    }

    private void mutationEventsInsertedNode(NodeImpl nodeImpl, NodeImpl nodeImpl2, boolean z) {
        if (LCount.lookup(MutationEventImpl.DOM_NODE_INSERTED).total > 0) {
            MutationEventImpl mutationEventImpl = new MutationEventImpl();
            mutationEventImpl.initMutationEvent(MutationEventImpl.DOM_NODE_INSERTED, true, false, nodeImpl, null, null, null, (short) 0);
            dispatchEvent(nodeImpl2, mutationEventImpl);
        }
        if (LCount.lookup(MutationEventImpl.DOM_NODE_INSERTED_INTO_DOCUMENT).total > 0) {
            EnclosingAttr enclosingAttr = this.savedEnclosingAttr;
            NodeImpl nodeImplParentNode = enclosingAttr != null ? (NodeImpl) enclosingAttr.node.getOwnerElement() : nodeImpl;
            if (nodeImplParentNode != null) {
                NodeImpl nodeImpl3 = nodeImplParentNode;
                while (nodeImplParentNode != null) {
                    nodeImpl3 = nodeImplParentNode;
                    nodeImplParentNode = nodeImplParentNode.getNodeType() == 2 ? (NodeImpl) ((AttrImpl) nodeImplParentNode).getOwnerElement() : nodeImplParentNode.parentNode();
                }
                if (nodeImpl3.getNodeType() == 9) {
                    MutationEventImpl mutationEventImpl2 = new MutationEventImpl();
                    mutationEventImpl2.initMutationEvent(MutationEventImpl.DOM_NODE_INSERTED_INTO_DOCUMENT, false, false, null, null, null, null, (short) 0);
                    dispatchEventToSubtree(nodeImpl2, mutationEventImpl2);
                }
            }
        }
        if (z) {
            return;
        }
        dispatchAggregateEvents(nodeImpl, this.savedEnclosingAttr);
    }

    private void mutationEventsModifiedCharacterData(NodeImpl nodeImpl, String str, String str2, boolean z) {
        if (z) {
            return;
        }
        if (LCount.lookup(MutationEventImpl.DOM_CHARACTER_DATA_MODIFIED).total > 0) {
            MutationEventImpl mutationEventImpl = new MutationEventImpl();
            mutationEventImpl.initMutationEvent(MutationEventImpl.DOM_CHARACTER_DATA_MODIFIED, true, false, null, str, str2, null, (short) 0);
            dispatchEvent(nodeImpl, mutationEventImpl);
        }
        dispatchAggregateEvents(nodeImpl, this.savedEnclosingAttr);
    }

    private void mutationEventsRemovedAttrNode(AttrImpl attrImpl, NodeImpl nodeImpl, String str) {
        if (LCount.lookup(MutationEventImpl.DOM_ATTR_MODIFIED).total > 0) {
            MutationEventImpl mutationEventImpl = new MutationEventImpl();
            mutationEventImpl.initMutationEvent(MutationEventImpl.DOM_ATTR_MODIFIED, true, false, attrImpl, attrImpl.getNodeValue(), null, str, (short) 3);
            dispatchEvent(nodeImpl, mutationEventImpl);
        }
        dispatchAggregateEvents(nodeImpl, null, null, (short) 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [org.apache.xerces.dom.DocumentImpl] */
    /* JADX WARN: Type inference failed for: r11v14 */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference failed for: r11v6 */
    /* JADX WARN: Type inference failed for: r11v7, types: [org.apache.xerces.dom.NodeImpl] */
    /* JADX WARN: Type inference failed for: r11v8 */
    /* JADX WARN: Type inference failed for: r13v5, types: [org.apache.xerces.dom.NodeImpl] */
    private void mutationEventsRemovingNode(NodeImpl nodeImpl, NodeImpl nodeImpl2, boolean z) {
        ?? ParentNode;
        ?? r13;
        NodeImpl nodeImpl3;
        if (!z) {
            saveEnclosingAttr(nodeImpl);
        }
        if (LCount.lookup(MutationEventImpl.DOM_NODE_REMOVED).total > 0) {
            MutationEventImpl mutationEventImpl = new MutationEventImpl();
            mutationEventImpl.initMutationEvent(MutationEventImpl.DOM_NODE_REMOVED, true, false, nodeImpl, null, null, null, (short) 0);
            dispatchEvent(nodeImpl2, mutationEventImpl);
        }
        if (LCount.lookup(MutationEventImpl.DOM_NODE_REMOVED_FROM_DOCUMENT).total > 0) {
            EnclosingAttr enclosingAttr = this.savedEnclosingAttr;
            if (enclosingAttr != null) {
                nodeImpl3 = (NodeImpl) enclosingAttr.node.getOwnerElement();
            } else {
                ParentNode = this;
            }
            if (ParentNode == 0) {
                ParentNode = nodeImpl3;
                return;
            }
            do {
                ParentNode = nodeImpl3;
                r13 = ParentNode;
                ParentNode = ParentNode.parentNode();
            } while (ParentNode != 0);
            if (r13.getNodeType() == 9) {
                MutationEventImpl mutationEventImpl2 = new MutationEventImpl();
                mutationEventImpl2.initMutationEvent(MutationEventImpl.DOM_NODE_REMOVED_FROM_DOCUMENT, false, false, null, null, null, null, (short) 0);
                dispatchEventToSubtree(nodeImpl2, mutationEventImpl2);
            }
        }
    }

    private void notifyIteratorsRemovingNode(NodeImpl nodeImpl) {
        removeStaleIteratorReferences();
        Iterator it = this.iterators.iterator();
        while (it.hasNext()) {
            NodeIteratorImpl nodeIteratorImpl = (NodeIteratorImpl) ((Reference) it.next()).get();
            if (nodeIteratorImpl != null) {
                nodeIteratorImpl.removeNode(nodeImpl);
            } else {
                it.remove();
            }
        }
    }

    private void notifyRangesDeletedText(CharacterDataImpl characterDataImpl, int i, int i2) {
        removeStaleRangeReferences();
        Iterator it = this.ranges.iterator();
        while (it.hasNext()) {
            RangeImpl rangeImpl = (RangeImpl) ((Reference) it.next()).get();
            if (rangeImpl != null) {
                rangeImpl.receiveDeletedText(characterDataImpl, i, i2);
            } else {
                it.remove();
            }
        }
    }

    private void notifyRangesInsertedNode(NodeImpl nodeImpl) {
        removeStaleRangeReferences();
        Iterator it = this.ranges.iterator();
        while (it.hasNext()) {
            RangeImpl rangeImpl = (RangeImpl) ((Reference) it.next()).get();
            if (rangeImpl != null) {
                rangeImpl.insertedNodeFromDOM(nodeImpl);
            } else {
                it.remove();
            }
        }
    }

    private void notifyRangesInsertedText(CharacterDataImpl characterDataImpl, int i, int i2) {
        removeStaleRangeReferences();
        Iterator it = this.ranges.iterator();
        while (it.hasNext()) {
            RangeImpl rangeImpl = (RangeImpl) ((Reference) it.next()).get();
            if (rangeImpl != null) {
                rangeImpl.receiveInsertedText(characterDataImpl, i, i2);
            } else {
                it.remove();
            }
        }
    }

    private void notifyRangesRemovingNode(NodeImpl nodeImpl) {
        removeStaleRangeReferences();
        Iterator it = this.ranges.iterator();
        while (it.hasNext()) {
            RangeImpl rangeImpl = (RangeImpl) ((Reference) it.next()).get();
            if (rangeImpl != null) {
                rangeImpl.removeNode(nodeImpl);
            } else {
                it.remove();
            }
        }
    }

    private void notifyRangesReplacedText(CharacterDataImpl characterDataImpl) {
        removeStaleRangeReferences();
        Iterator it = this.ranges.iterator();
        while (it.hasNext()) {
            RangeImpl rangeImpl = (RangeImpl) ((Reference) it.next()).get();
            if (rangeImpl != null) {
                rangeImpl.receiveReplacedText(characterDataImpl);
            } else {
                it.remove();
            }
        }
    }

    private void notifyRangesSplitData(Node node, Node node2, int i) {
        removeStaleRangeReferences();
        Iterator it = this.ranges.iterator();
        while (it.hasNext()) {
            RangeImpl rangeImpl = (RangeImpl) ((Reference) it.next()).get();
            if (rangeImpl != null) {
                rangeImpl.receiveSplitData(node, node2, i);
            } else {
                it.remove();
            }
        }
    }

    private void removeStaleIteratorReferences() {
        removeStaleReferences(this.iteratorReferenceQueue, this.iterators);
    }

    private void removeStaleRangeReferences() {
        removeStaleReferences(this.rangeReferenceQueue, this.ranges);
    }

    private void removeStaleReferences(ReferenceQueue referenceQueue, List list) {
        Reference referencePoll = referenceQueue.poll();
        int i = 0;
        while (referencePoll != null) {
            i++;
            referencePoll = referenceQueue.poll();
        }
        if (i > 0) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (((Reference) it.next()).get() == null) {
                    it.remove();
                    i--;
                    if (i <= 0) {
                        return;
                    }
                }
            }
        }
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    protected void addEventListener(NodeImpl nodeImpl, String str, EventListener eventListener, boolean z) {
        if (str == null || str.length() == 0 || eventListener == null) {
            return;
        }
        removeEventListener(nodeImpl, str, eventListener, z);
        Vector eventListeners = getEventListeners(nodeImpl);
        if (eventListeners == null) {
            eventListeners = new Vector();
            setEventListeners(nodeImpl, eventListeners);
        }
        eventListeners.addElement(new LEntry(str, eventListener, z));
        LCount lCountLookup = LCount.lookup(str);
        if (z) {
            lCountLookup.captures++;
        } else {
            lCountLookup.bubbles++;
        }
        lCountLookup.total++;
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl, org.apache.xerces.dom.ParentNode, org.apache.xerces.dom.ChildNode, org.apache.xerces.dom.NodeImpl, org.w3c.dom.Node
    public Node cloneNode(boolean z) {
        DocumentImpl documentImpl = new DocumentImpl();
        callUserDataHandlers(this, documentImpl, (short) 1);
        cloneNode(documentImpl, z);
        documentImpl.mutationEvents = this.mutationEvents;
        return documentImpl;
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    protected void copyEventListeners(NodeImpl nodeImpl, NodeImpl nodeImpl2) {
        Vector eventListeners = getEventListeners(nodeImpl);
        if (eventListeners == null) {
            return;
        }
        setEventListeners(nodeImpl2, (Vector) eventListeners.clone());
    }

    @Override // org.w3c.dom.events.DocumentEvent
    public Event createEvent(String str) throws DOMException {
        if (str.equalsIgnoreCase("Events") || "Event".equals(str)) {
            return new EventImpl();
        }
        if (str.equalsIgnoreCase("MutationEvents") || "MutationEvent".equals(str)) {
            return new MutationEventImpl();
        }
        if (str.equalsIgnoreCase("UIEvents") || "UIEvent".equals(str)) {
            return new UIEventImpl();
        }
        if (str.equalsIgnoreCase("MouseEvents") || "MouseEvent".equals(str)) {
            return new MouseEventImpl();
        }
        throw new DOMException((short) 9, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_SUPPORTED_ERR", null));
    }

    @Override // org.w3c.dom.traversal.DocumentTraversal
    public NodeIterator createNodeIterator(Node node, int i, NodeFilter nodeFilter, boolean z) {
        if (node == null) {
            throw new DOMException((short) 9, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_SUPPORTED_ERR", null));
        }
        NodeIteratorImpl nodeIteratorImpl = new NodeIteratorImpl(this, node, i, nodeFilter, z);
        if (this.iterators == null) {
            this.iterators = new LinkedList();
            this.iteratorReferenceQueue = new ReferenceQueue();
        }
        removeStaleIteratorReferences();
        this.iterators.add(new WeakReference(nodeIteratorImpl, this.iteratorReferenceQueue));
        return nodeIteratorImpl;
    }

    public NodeIterator createNodeIterator(Node node, short s, NodeFilter nodeFilter) {
        return createNodeIterator(node, s, nodeFilter, true);
    }

    @Override // org.w3c.dom.ranges.DocumentRange
    public Range createRange() {
        if (this.ranges == null) {
            this.ranges = new LinkedList();
            this.rangeReferenceQueue = new ReferenceQueue();
        }
        RangeImpl rangeImpl = new RangeImpl(this);
        removeStaleRangeReferences();
        this.ranges.add(new WeakReference(rangeImpl, this.rangeReferenceQueue));
        return rangeImpl;
    }

    @Override // org.w3c.dom.traversal.DocumentTraversal
    public TreeWalker createTreeWalker(Node node, int i, NodeFilter nodeFilter, boolean z) {
        if (node != null) {
            return new TreeWalkerImpl(node, i, nodeFilter, z);
        }
        throw new DOMException((short) 9, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "NOT_SUPPORTED_ERR", null));
    }

    public TreeWalker createTreeWalker(Node node, short s, NodeFilter nodeFilter) {
        return createTreeWalker(node, s, nodeFilter, true);
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void deletedText(CharacterDataImpl characterDataImpl, int i, int i2) {
        if (this.ranges != null) {
            notifyRangesDeletedText(characterDataImpl, i, i2);
        }
    }

    protected void dispatchAggregateEvents(NodeImpl nodeImpl, AttrImpl attrImpl, String str, short s) {
        NodeImpl nodeImpl2;
        if (attrImpl != null) {
            LCount lCountLookup = LCount.lookup(MutationEventImpl.DOM_ATTR_MODIFIED);
            nodeImpl2 = (NodeImpl) attrImpl.getOwnerElement();
            if (lCountLookup.total > 0 && nodeImpl2 != null) {
                MutationEventImpl mutationEventImpl = new MutationEventImpl();
                mutationEventImpl.initMutationEvent(MutationEventImpl.DOM_ATTR_MODIFIED, true, false, attrImpl, str, attrImpl.getNodeValue(), attrImpl.getNodeName(), s);
                nodeImpl2.dispatchEvent(mutationEventImpl);
            }
        } else {
            nodeImpl2 = null;
        }
        if (LCount.lookup(MutationEventImpl.DOM_SUBTREE_MODIFIED).total > 0) {
            MutationEventImpl mutationEventImpl2 = new MutationEventImpl();
            mutationEventImpl2.initMutationEvent(MutationEventImpl.DOM_SUBTREE_MODIFIED, true, false, null, null, null, null, (short) 0);
            if (attrImpl == null) {
                dispatchEvent(nodeImpl, mutationEventImpl2);
                return;
            }
            dispatchEvent(attrImpl, mutationEventImpl2);
            if (nodeImpl2 != null) {
                dispatchEvent(nodeImpl2, mutationEventImpl2);
            }
        }
    }

    protected void dispatchAggregateEvents(NodeImpl nodeImpl, EnclosingAttr enclosingAttr) {
        if (enclosingAttr != null) {
            dispatchAggregateEvents(nodeImpl, enclosingAttr.node, enclosingAttr.oldvalue, (short) 1);
        } else {
            dispatchAggregateEvents(nodeImpl, null, null, (short) 0);
        }
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    protected boolean dispatchEvent(NodeImpl nodeImpl, Event event) {
        if (event == null) {
            return false;
        }
        EventImpl eventImpl = (EventImpl) event;
        if (!eventImpl.initialized || eventImpl.type == null || eventImpl.type.length() == 0) {
            throw new EventException((short) 0, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "UNSPECIFIED_EVENT_TYPE_ERR", null));
        }
        LCount lCountLookup = LCount.lookup(eventImpl.getType());
        if (lCountLookup.total == 0) {
            return eventImpl.preventDefault;
        }
        eventImpl.target = nodeImpl;
        eventImpl.stopPropagation = false;
        eventImpl.preventDefault = false;
        ArrayList arrayList = new ArrayList(10);
        for (Node parentNode = nodeImpl.getParentNode(); parentNode != null; parentNode = parentNode.getParentNode()) {
            arrayList.add(parentNode);
        }
        if (lCountLookup.captures > 0) {
            eventImpl.eventPhase = (short) 1;
            for (int size = arrayList.size() - 1; size >= 0 && !eventImpl.stopPropagation; size--) {
                NodeImpl nodeImpl2 = (NodeImpl) arrayList.get(size);
                eventImpl.currentTarget = nodeImpl2;
                Vector eventListeners = getEventListeners(nodeImpl2);
                if (eventListeners != null) {
                    Vector vector = (Vector) eventListeners.clone();
                    int size2 = vector.size();
                    for (int i = 0; i < size2; i++) {
                        LEntry lEntry = (LEntry) vector.elementAt(i);
                        if (lEntry.useCapture && lEntry.type.equals(eventImpl.type) && eventListeners.contains(lEntry)) {
                            try {
                                lEntry.listener.handleEvent(eventImpl);
                            } catch (Exception unused) {
                            }
                        }
                    }
                }
            }
        }
        if (lCountLookup.bubbles > 0) {
            eventImpl.eventPhase = (short) 2;
            eventImpl.currentTarget = nodeImpl;
            Vector eventListeners2 = getEventListeners(nodeImpl);
            if (!eventImpl.stopPropagation && eventListeners2 != null) {
                Vector vector2 = (Vector) eventListeners2.clone();
                int size3 = vector2.size();
                for (int i2 = 0; i2 < size3; i2++) {
                    LEntry lEntry2 = (LEntry) vector2.elementAt(i2);
                    if (!lEntry2.useCapture && lEntry2.type.equals(eventImpl.type) && eventListeners2.contains(lEntry2)) {
                        try {
                            lEntry2.listener.handleEvent(eventImpl);
                        } catch (Exception unused2) {
                        }
                    }
                }
            }
            if (eventImpl.bubbles) {
                eventImpl.eventPhase = (short) 3;
                int size4 = arrayList.size();
                for (int i3 = 0; i3 < size4 && !eventImpl.stopPropagation; i3++) {
                    NodeImpl nodeImpl3 = (NodeImpl) arrayList.get(i3);
                    eventImpl.currentTarget = nodeImpl3;
                    Vector eventListeners3 = getEventListeners(nodeImpl3);
                    if (eventListeners3 != null) {
                        Vector vector3 = (Vector) eventListeners3.clone();
                        int size5 = vector3.size();
                        for (int i4 = 0; i4 < size5; i4++) {
                            LEntry lEntry3 = (LEntry) vector3.elementAt(i4);
                            if (!lEntry3.useCapture && lEntry3.type.equals(eventImpl.type) && eventListeners3.contains(lEntry3)) {
                                try {
                                    lEntry3.listener.handleEvent(eventImpl);
                                } catch (Exception unused3) {
                                }
                            }
                        }
                    }
                }
            }
        }
        if (lCountLookup.defaults > 0 && eventImpl.cancelable) {
            boolean z = eventImpl.preventDefault;
        }
        return eventImpl.preventDefault;
    }

    protected void dispatchEventToSubtree(Node node, Event event) {
        ((NodeImpl) node).dispatchEvent(event);
        if (node.getNodeType() == 1) {
            NamedNodeMap attributes = node.getAttributes();
            for (int length = attributes.getLength() - 1; length >= 0; length--) {
                dispatchingEventToSubtree(attributes.item(length), event);
            }
        }
        dispatchingEventToSubtree(node.getFirstChild(), event);
    }

    protected void dispatchingEventToSubtree(Node node, Event event) {
        if (node == null) {
            return;
        }
        ((NodeImpl) node).dispatchEvent(event);
        if (node.getNodeType() == 1) {
            NamedNodeMap attributes = node.getAttributes();
            for (int length = attributes.getLength() - 1; length >= 0; length--) {
                dispatchingEventToSubtree(attributes.item(length), event);
            }
        }
        dispatchingEventToSubtree(node.getFirstChild(), event);
        dispatchingEventToSubtree(node.getNextSibling(), event);
    }

    protected Vector getEventListeners(NodeImpl nodeImpl) {
        Hashtable hashtable = this.eventListeners;
        if (hashtable == null) {
            return null;
        }
        return (Vector) hashtable.get(nodeImpl);
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl, org.w3c.dom.Document
    public DOMImplementation getImplementation() {
        return DOMImplementationImpl.getDOMImplementation();
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    boolean getMutationEvents() {
        return this.mutationEvents;
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void insertedNode(NodeImpl nodeImpl, NodeImpl nodeImpl2, boolean z) {
        if (this.mutationEvents) {
            mutationEventsInsertedNode(nodeImpl, nodeImpl2, z);
        }
        if (this.ranges != null) {
            notifyRangesInsertedNode(nodeImpl2);
        }
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void insertedText(CharacterDataImpl characterDataImpl, int i, int i2) {
        if (this.ranges != null) {
            notifyRangesInsertedText(characterDataImpl, i, i2);
        }
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void insertingNode(NodeImpl nodeImpl, boolean z) {
        if (!this.mutationEvents || z) {
            return;
        }
        saveEnclosingAttr(nodeImpl);
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void modifiedAttrValue(AttrImpl attrImpl, String str) {
        if (this.mutationEvents) {
            dispatchAggregateEvents(attrImpl, attrImpl, str, (short) 1);
        }
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void modifiedCharacterData(NodeImpl nodeImpl, String str, String str2, boolean z) {
        if (this.mutationEvents) {
            mutationEventsModifiedCharacterData(nodeImpl, str, str2, z);
        }
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void modifyingCharacterData(NodeImpl nodeImpl, boolean z) {
        if (!this.mutationEvents || z) {
            return;
        }
        saveEnclosingAttr(nodeImpl);
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    protected void removeEventListener(NodeImpl nodeImpl, String str, EventListener eventListener, boolean z) {
        Vector eventListeners;
        if (str == null || str.length() == 0 || eventListener == null || (eventListeners = getEventListeners(nodeImpl)) == null) {
            return;
        }
        for (int size = eventListeners.size() - 1; size >= 0; size--) {
            LEntry lEntry = (LEntry) eventListeners.elementAt(size);
            if (lEntry.useCapture == z && lEntry.listener == eventListener && lEntry.type.equals(str)) {
                eventListeners.removeElementAt(size);
                if (eventListeners.size() == 0) {
                    setEventListeners(nodeImpl, null);
                }
                LCount lCountLookup = LCount.lookup(str);
                if (z) {
                    lCountLookup.captures--;
                } else {
                    lCountLookup.bubbles--;
                }
                lCountLookup.total--;
                return;
            }
        }
    }

    void removeNodeIterator(NodeIterator nodeIterator) {
        if (nodeIterator == null || this.iterators == null) {
            return;
        }
        removeStaleIteratorReferences();
        Iterator it = this.iterators.iterator();
        while (it.hasNext()) {
            Object obj = ((Reference) it.next()).get();
            if (obj == nodeIterator) {
                it.remove();
                return;
            } else if (obj == null) {
                it.remove();
            }
        }
    }

    void removeRange(Range range) {
        if (range == null || this.ranges == null) {
            return;
        }
        removeStaleRangeReferences();
        Iterator it = this.ranges.iterator();
        while (it.hasNext()) {
            Object obj = ((Reference) it.next()).get();
            if (obj == range) {
                it.remove();
                return;
            } else if (obj == null) {
                it.remove();
            }
        }
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void removedAttrNode(AttrImpl attrImpl, NodeImpl nodeImpl, String str) {
        if (this.mutationEvents) {
            mutationEventsRemovedAttrNode(attrImpl, nodeImpl, str);
        }
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void removedNode(NodeImpl nodeImpl, boolean z) {
        if (!this.mutationEvents || z) {
            return;
        }
        dispatchAggregateEvents(nodeImpl, this.savedEnclosingAttr);
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void removingNode(NodeImpl nodeImpl, NodeImpl nodeImpl2, boolean z) {
        if (this.iterators != null) {
            notifyIteratorsRemovingNode(nodeImpl2);
        }
        if (this.ranges != null) {
            notifyRangesRemovingNode(nodeImpl2);
        }
        if (this.mutationEvents) {
            mutationEventsRemovingNode(nodeImpl, nodeImpl2, z);
        }
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void renamedAttrNode(Attr attr, Attr attr2) {
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void renamedElement(Element element, Element element2) {
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void replacedCharacterData(NodeImpl nodeImpl, String str, String str2) {
        modifiedCharacterData(nodeImpl, str, str2, false);
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void replacedNode(NodeImpl nodeImpl) {
        if (this.mutationEvents) {
            dispatchAggregateEvents(nodeImpl, this.savedEnclosingAttr);
        }
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void replacedText(CharacterDataImpl characterDataImpl) {
        if (this.ranges != null) {
            notifyRangesReplacedText(characterDataImpl);
        }
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void replacingData(NodeImpl nodeImpl) {
        if (this.mutationEvents) {
            saveEnclosingAttr(nodeImpl);
        }
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void replacingNode(NodeImpl nodeImpl) {
        if (this.mutationEvents) {
            saveEnclosingAttr(nodeImpl);
        }
    }

    protected void saveEnclosingAttr(NodeImpl nodeImpl) {
        this.savedEnclosingAttr = null;
        if (LCount.lookup(MutationEventImpl.DOM_ATTR_MODIFIED).total > 0) {
            while (nodeImpl != null) {
                short nodeType = nodeImpl.getNodeType();
                if (nodeType == 2) {
                    EnclosingAttr enclosingAttr = new EnclosingAttr();
                    enclosingAttr.node = (AttrImpl) nodeImpl;
                    enclosingAttr.oldvalue = enclosingAttr.node.getNodeValue();
                    this.savedEnclosingAttr = enclosingAttr;
                    return;
                }
                if (nodeType != 5 && nodeType != 3) {
                    return;
                } else {
                    nodeImpl = nodeImpl.parentNode();
                }
            }
        }
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void setAttrNode(AttrImpl attrImpl, AttrImpl attrImpl2) {
        if (this.mutationEvents) {
            if (attrImpl2 == null) {
                dispatchAggregateEvents(attrImpl.ownerNode, attrImpl, null, (short) 2);
            } else {
                dispatchAggregateEvents(attrImpl.ownerNode, attrImpl, attrImpl2.getNodeValue(), (short) 1);
            }
        }
    }

    protected void setEventListeners(NodeImpl nodeImpl, Vector vector) {
        boolean z;
        if (this.eventListeners == null) {
            this.eventListeners = new Hashtable();
        }
        if (vector == null) {
            this.eventListeners.remove(nodeImpl);
            if (!this.eventListeners.isEmpty()) {
                return;
            } else {
                z = false;
            }
        } else {
            this.eventListeners.put(nodeImpl, vector);
            z = true;
        }
        this.mutationEvents = z;
    }

    @Override // org.apache.xerces.dom.CoreDocumentImpl
    void setMutationEvents(boolean z) {
        this.mutationEvents = z;
    }

    void splitData(Node node, Node node2, int i) {
        if (this.ranges != null) {
            notifyRangesSplitData(node, node2, i);
        }
    }
}
