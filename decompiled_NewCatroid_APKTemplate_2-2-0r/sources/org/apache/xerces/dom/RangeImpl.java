package org.apache.xerces.dom;

import java.util.ArrayList;
import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.ranges.Range;
import org.w3c.dom.ranges.RangeException;

/* JADX INFO: loaded from: classes13.dex */
public class RangeImpl implements Range {
    static final int CLONE_CONTENTS = 2;
    static final int DELETE_CONTENTS = 3;
    static final int EXTRACT_CONTENTS = 1;
    private DocumentImpl fDocument;
    private Node fEndContainer;
    private Node fStartContainer;
    private Node fInsertNode = null;
    private Node fDeleteNode = null;
    private Node fSplitNode = null;
    private boolean fInsertedFromRange = false;
    private Node fRemoveChild = null;
    private int fStartOffset = 0;
    private int fEndOffset = 0;
    private boolean fDetach = false;

    public RangeImpl(DocumentImpl documentImpl) {
        this.fDocument = documentImpl;
        this.fStartContainer = documentImpl;
        this.fEndContainer = documentImpl;
    }

    private Node getRootContainer(Node node) {
        if (node == null) {
            return null;
        }
        while (node.getParentNode() != null) {
            node = node.getParentNode();
        }
        return node;
    }

    private Node getSelectedNode(Node node, int i) {
        if (node.getNodeType() == 3 || i < 0) {
            return node;
        }
        Node firstChild = node.getFirstChild();
        while (firstChild != null && i > 0) {
            i--;
            firstChild = firstChild.getNextSibling();
        }
        return firstChild != null ? firstChild : node;
    }

    private boolean hasLegalRootContainer(Node node) {
        if (node == null) {
            return false;
        }
        short nodeType = getRootContainer(node).getNodeType();
        return nodeType == 2 || nodeType == 9 || nodeType == 11;
    }

    private boolean isLegalContainedNode(Node node) {
        short nodeType;
        return (node == null || (nodeType = node.getNodeType()) == 2 || nodeType == 6 || nodeType == 9 || nodeType == 11 || nodeType == 12) ? false : true;
    }

    private boolean isLegalContainer(Node node) {
        if (node == null) {
            return false;
        }
        while (node != null) {
            short nodeType = node.getNodeType();
            if (nodeType == 6 || nodeType == 10 || nodeType == 12) {
                return false;
            }
            node = node.getParentNode();
        }
        return true;
    }

    private Node traverseCharacterDataNode(Node node, boolean z, int i) {
        String strSubstring;
        String strSubstring2;
        String nodeValue = node.getNodeValue();
        if (z) {
            int startOffset = getStartOffset();
            strSubstring = nodeValue.substring(startOffset);
            strSubstring2 = nodeValue.substring(0, startOffset);
        } else {
            int endOffset = getEndOffset();
            strSubstring = nodeValue.substring(0, endOffset);
            strSubstring2 = nodeValue.substring(endOffset);
        }
        if (i != 2) {
            node.setNodeValue(strSubstring2);
        }
        if (i == 3) {
            return null;
        }
        Node nodeCloneNode = node.cloneNode(false);
        nodeCloneNode.setNodeValue(strSubstring);
        return nodeCloneNode;
    }

    private DocumentFragment traverseCommonAncestors(Node node, Node node2, int i) {
        DocumentFragment documentFragmentCreateDocumentFragment = i != 3 ? this.fDocument.createDocumentFragment() : null;
        Node nodeTraverseLeftBoundary = traverseLeftBoundary(node, i);
        if (documentFragmentCreateDocumentFragment != null) {
            documentFragmentCreateDocumentFragment.appendChild(nodeTraverseLeftBoundary);
        }
        Node parentNode = node.getParentNode();
        int iIndexOf = indexOf(node2, parentNode) - (indexOf(node, parentNode) + 1);
        Node nextSibling = node.getNextSibling();
        while (iIndexOf > 0) {
            Node nextSibling2 = nextSibling.getNextSibling();
            Node nodeTraverseFullySelected = traverseFullySelected(nextSibling, i);
            if (documentFragmentCreateDocumentFragment != null) {
                documentFragmentCreateDocumentFragment.appendChild(nodeTraverseFullySelected);
            }
            iIndexOf--;
            nextSibling = nextSibling2;
        }
        Node nodeTraverseRightBoundary = traverseRightBoundary(node2, i);
        if (documentFragmentCreateDocumentFragment != null) {
            documentFragmentCreateDocumentFragment.appendChild(nodeTraverseRightBoundary);
        }
        if (i != 2) {
            setStartAfter(node);
            collapse(true);
        }
        return documentFragmentCreateDocumentFragment;
    }

    private DocumentFragment traverseCommonEndContainer(Node node, int i) {
        DocumentFragment documentFragmentCreateDocumentFragment = i != 3 ? this.fDocument.createDocumentFragment() : null;
        Node nodeTraverseLeftBoundary = traverseLeftBoundary(node, i);
        if (documentFragmentCreateDocumentFragment != null) {
            documentFragmentCreateDocumentFragment.appendChild(nodeTraverseLeftBoundary);
        }
        int iIndexOf = this.fEndOffset - (indexOf(node, this.fEndContainer) + 1);
        Node nextSibling = node.getNextSibling();
        while (iIndexOf > 0) {
            Node nextSibling2 = nextSibling.getNextSibling();
            Node nodeTraverseFullySelected = traverseFullySelected(nextSibling, i);
            if (documentFragmentCreateDocumentFragment != null) {
                documentFragmentCreateDocumentFragment.appendChild(nodeTraverseFullySelected);
            }
            iIndexOf--;
            nextSibling = nextSibling2;
        }
        if (i != 2) {
            setStartAfter(node);
            collapse(true);
        }
        return documentFragmentCreateDocumentFragment;
    }

    private DocumentFragment traverseCommonStartContainer(Node node, int i) {
        DocumentFragment documentFragmentCreateDocumentFragment = i != 3 ? this.fDocument.createDocumentFragment() : null;
        Node nodeTraverseRightBoundary = traverseRightBoundary(node, i);
        if (documentFragmentCreateDocumentFragment != null) {
            documentFragmentCreateDocumentFragment.appendChild(nodeTraverseRightBoundary);
        }
        int iIndexOf = indexOf(node, this.fStartContainer) - this.fStartOffset;
        if (iIndexOf <= 0) {
            if (i != 2) {
                setEndBefore(node);
                collapse(false);
            }
            return documentFragmentCreateDocumentFragment;
        }
        Node previousSibling = node.getPreviousSibling();
        while (iIndexOf > 0) {
            Node previousSibling2 = previousSibling.getPreviousSibling();
            Node nodeTraverseFullySelected = traverseFullySelected(previousSibling, i);
            if (documentFragmentCreateDocumentFragment != null) {
                documentFragmentCreateDocumentFragment.insertBefore(nodeTraverseFullySelected, documentFragmentCreateDocumentFragment.getFirstChild());
            }
            iIndexOf--;
            previousSibling = previousSibling2;
        }
        if (i != 2) {
            setEndBefore(node);
            collapse(false);
        }
        return documentFragmentCreateDocumentFragment;
    }

    private DocumentFragment traverseContents(int i) throws DOMException {
        Node node;
        Node node2 = this.fStartContainer;
        if (node2 == null || (node = this.fEndContainer) == null) {
            return null;
        }
        if (this.fDetach) {
            throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
        }
        if (node2 == node) {
            return traverseSameContainer(i);
        }
        Node parentNode = node.getParentNode();
        int i2 = 0;
        while (true) {
            Node node3 = node;
            node = parentNode;
            if (node == null) {
                Node node4 = this.fStartContainer;
                Node node5 = node4;
                int i3 = 0;
                for (Node parentNode2 = node4.getParentNode(); parentNode2 != null; parentNode2 = parentNode2.getParentNode()) {
                    if (parentNode2 == this.fEndContainer) {
                        return traverseCommonEndContainer(node5, i);
                    }
                    i3++;
                    node5 = parentNode2;
                }
                int i4 = i3 - i2;
                Node parentNode3 = this.fStartContainer;
                while (i4 > 0) {
                    parentNode3 = parentNode3.getParentNode();
                    i4--;
                }
                Node parentNode4 = this.fEndContainer;
                while (i4 < 0) {
                    parentNode4 = parentNode4.getParentNode();
                    i4++;
                }
                Node parentNode5 = parentNode3.getParentNode();
                Node parentNode6 = parentNode4.getParentNode();
                Node node6 = parentNode4;
                Node node7 = parentNode3;
                Node parentNode7 = parentNode5;
                Node node8 = node6;
                while (parentNode7 != parentNode6) {
                    node7 = parentNode7;
                    parentNode7 = parentNode7.getParentNode();
                    Node node9 = parentNode6;
                    parentNode6 = parentNode6.getParentNode();
                    node8 = node9;
                }
                return traverseCommonAncestors(node7, node8, i);
            }
            if (node == this.fStartContainer) {
                return traverseCommonStartContainer(node3, i);
            }
            i2++;
            parentNode = node.getParentNode();
        }
    }

    private Node traverseFullySelected(Node node, int i) {
        if (i == 1) {
            if (node.getNodeType() != 10) {
                return node;
            }
            throw new DOMException((short) 3, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "HIERARCHY_REQUEST_ERR", null));
        }
        if (i == 2) {
            return node.cloneNode(true);
        }
        if (i != 3) {
            return null;
        }
        node.getParentNode().removeChild(node);
        return null;
    }

    private Node traverseLeftBoundary(Node node, int i) {
        Node selectedNode = getSelectedNode(getStartContainer(), getStartOffset());
        boolean z = selectedNode != getStartContainer();
        if (selectedNode == node) {
            return traverseNode(selectedNode, z, true, i);
        }
        Node parentNode = selectedNode.getParentNode();
        Node nodeTraverseNode = traverseNode(parentNode, false, true, i);
        while (parentNode != null) {
            while (selectedNode != null) {
                Node nextSibling = selectedNode.getNextSibling();
                Node nodeTraverseNode2 = traverseNode(selectedNode, z, true, i);
                if (i != 3) {
                    nodeTraverseNode.appendChild(nodeTraverseNode2);
                }
                z = true;
                selectedNode = nextSibling;
            }
            if (parentNode == node) {
                return nodeTraverseNode;
            }
            selectedNode = parentNode.getNextSibling();
            parentNode = parentNode.getParentNode();
            Node nodeTraverseNode3 = traverseNode(parentNode, false, true, i);
            if (i != 3) {
                nodeTraverseNode3.appendChild(nodeTraverseNode);
            }
            nodeTraverseNode = nodeTraverseNode3;
        }
        return null;
    }

    private Node traverseNode(Node node, boolean z, boolean z2, int i) {
        if (z) {
            return traverseFullySelected(node, i);
        }
        short nodeType = node.getNodeType();
        return (nodeType == 3 || nodeType == 4 || nodeType == 8 || nodeType == 7) ? traverseCharacterDataNode(node, z2, i) : traversePartiallySelected(node, i);
    }

    private Node traversePartiallySelected(Node node, int i) {
        if (i == 1 || i == 2) {
            return node.cloneNode(false);
        }
        return null;
    }

    private Node traverseRightBoundary(Node node, int i) {
        Node selectedNode = getSelectedNode(this.fEndContainer, this.fEndOffset - 1);
        boolean z = selectedNode != this.fEndContainer;
        if (selectedNode == node) {
            return traverseNode(selectedNode, z, false, i);
        }
        Node parentNode = selectedNode.getParentNode();
        Node nodeTraverseNode = traverseNode(parentNode, false, false, i);
        while (parentNode != null) {
            while (selectedNode != null) {
                Node previousSibling = selectedNode.getPreviousSibling();
                Node nodeTraverseNode2 = traverseNode(selectedNode, z, false, i);
                if (i != 3) {
                    nodeTraverseNode.insertBefore(nodeTraverseNode2, nodeTraverseNode.getFirstChild());
                }
                z = true;
                selectedNode = previousSibling;
            }
            if (parentNode == node) {
                return nodeTraverseNode;
            }
            selectedNode = parentNode.getPreviousSibling();
            parentNode = parentNode.getParentNode();
            Node nodeTraverseNode3 = traverseNode(parentNode, false, false, i);
            if (i != 3) {
                nodeTraverseNode3.appendChild(nodeTraverseNode);
            }
            nodeTraverseNode = nodeTraverseNode3;
        }
        return null;
    }

    private DocumentFragment traverseSameContainer(int i) {
        Node nodeCreateComment;
        DocumentFragment documentFragmentCreateDocumentFragment = i != 3 ? this.fDocument.createDocumentFragment() : null;
        if (this.fStartOffset == this.fEndOffset) {
            return documentFragmentCreateDocumentFragment;
        }
        short nodeType = this.fStartContainer.getNodeType();
        if (nodeType != 3 && nodeType != 4 && nodeType != 8 && nodeType != 7) {
            Node selectedNode = getSelectedNode(this.fStartContainer, this.fStartOffset);
            int i2 = this.fEndOffset - this.fStartOffset;
            while (i2 > 0) {
                Node nextSibling = selectedNode.getNextSibling();
                Node nodeTraverseFullySelected = traverseFullySelected(selectedNode, i);
                if (documentFragmentCreateDocumentFragment != null) {
                    documentFragmentCreateDocumentFragment.appendChild(nodeTraverseFullySelected);
                }
                i2--;
                selectedNode = nextSibling;
            }
            if (i != 2) {
                collapse(true);
            }
            return documentFragmentCreateDocumentFragment;
        }
        String strSubstring = this.fStartContainer.getNodeValue().substring(this.fStartOffset, this.fEndOffset);
        if (i != 2) {
            CharacterDataImpl characterDataImpl = (CharacterDataImpl) this.fStartContainer;
            int i3 = this.fStartOffset;
            characterDataImpl.deleteData(i3, this.fEndOffset - i3);
            collapse(true);
        }
        if (i == 3) {
            return null;
        }
        if (nodeType == 3) {
            nodeCreateComment = this.fDocument.createTextNode(strSubstring);
        } else {
            DocumentImpl documentImpl = this.fDocument;
            if (nodeType == 4) {
                nodeCreateComment = documentImpl.createCDATASection(strSubstring);
            } else {
                nodeCreateComment = nodeType == 8 ? documentImpl.createComment(strSubstring) : documentImpl.createProcessingInstruction(this.fStartContainer.getNodeName(), strSubstring);
            }
        }
        documentFragmentCreateDocumentFragment.appendChild(nodeCreateComment);
        return documentFragmentCreateDocumentFragment;
    }

    void checkIndex(Node node, int i) throws DOMException {
        if (i < 0) {
            throw new DOMException((short) 1, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INDEX_SIZE_ERR", null));
        }
        short nodeType = node.getNodeType();
        if (nodeType == 3 || nodeType == 4 || nodeType == 8 || nodeType == 7) {
            if (i > node.getNodeValue().length()) {
                throw new DOMException((short) 1, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INDEX_SIZE_ERR", null));
            }
        } else if (i > node.getChildNodes().getLength()) {
            throw new DOMException((short) 1, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INDEX_SIZE_ERR", null));
        }
    }

    @Override // org.w3c.dom.ranges.Range
    public DocumentFragment cloneContents() throws DOMException {
        return traverseContents(2);
    }

    @Override // org.w3c.dom.ranges.Range
    public Range cloneRange() {
        if (this.fDetach) {
            throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
        }
        Range rangeCreateRange = this.fDocument.createRange();
        rangeCreateRange.setStart(this.fStartContainer, this.fStartOffset);
        rangeCreateRange.setEnd(this.fEndContainer, this.fEndOffset);
        return rangeCreateRange;
    }

    @Override // org.w3c.dom.ranges.Range
    public void collapse(boolean z) {
        if (this.fDetach) {
            throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
        }
        if (z) {
            this.fEndContainer = this.fStartContainer;
            this.fEndOffset = this.fStartOffset;
        } else {
            this.fStartContainer = this.fEndContainer;
            this.fStartOffset = this.fEndOffset;
        }
    }

    /* JADX WARN: Code duplicated, block: B:36:0x0099 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:37:0x009b A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:38:0x009c A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:39:0x009e A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:40:0x009f A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:41:0x00a0  */
    /* JADX WARN: Code duplicated, block: B:43:0x00a7 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:46:0x00af A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:47:0x00b0 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:48:0x00b1 A[LOOP:0: B:42:0x00a5->B:48:0x00b1, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:51:0x00c0 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:54:0x00c8 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:55:0x00c9 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:56:0x00ca A[LOOP:1: B:50:0x00be->B:56:0x00ca, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:59:0x00d5 A[LOOP:2: B:58:0x00d3->B:59:0x00d5, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:62:0x00df A[LOOP:3: B:61:0x00dd->B:62:0x00df, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:64:0x00e8 A[LOOP:4: B:63:0x00e6->B:64:0x00e8, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:66:0x00f1 A[LOOP:5: B:65:0x00ef->B:66:0x00f1, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:72:0x010f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:74:0x0112 A[LOOP:7: B:71:0x010d->B:74:0x0112, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:77:0x00a9 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:79:0x00c2 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:87:0x0111 A[SYNTHETIC] */
    @Override // org.w3c.dom.ranges.Range
    public short compareBoundaryPoints(short s, Range range) throws DOMException {
        Node endContainer;
        Node parentNode;
        int endOffset;
        int i;
        int i2;
        Node parentNode2;
        Node node;
        Node parentNode3;
        Node node2;
        Node parentNode4;
        Node parentNode5;
        Node node3;
        Node node4;
        Node nextSibling;
        if (this.fDocument.errorChecking) {
            if (this.fDetach) {
                throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
            }
            if ((this.fDocument != range.getStartContainer().getOwnerDocument() && this.fDocument != range.getStartContainer() && range.getStartContainer() != null) || (this.fDocument != range.getEndContainer().getOwnerDocument() && this.fDocument != range.getEndContainer() && range.getStartContainer() != null)) {
                throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
            }
        }
        if (s != 0) {
            if (s == 1) {
                endContainer = range.getStartContainer();
                parentNode = this.fEndContainer;
                endOffset = range.getStartOffset();
            } else if (s == 3) {
                endContainer = range.getEndContainer();
                parentNode = this.fStartContainer;
                endOffset = range.getEndOffset();
            } else {
                endContainer = range.getEndContainer();
                parentNode = this.fEndContainer;
                endOffset = range.getEndOffset();
            }
            i = this.fEndOffset;
            i2 = 0;
            if (endContainer == parentNode) {
                if (endOffset < i) {
                    return (short) 1;
                }
                if (endOffset == i) {
                    return (short) 0;
                }
                return (short) -1;
            }
            node = parentNode;
            for (parentNode2 = parentNode.getParentNode(); parentNode2 != null; parentNode2 = parentNode2.getParentNode()) {
                if (parentNode2 == endContainer) {
                    if (endOffset <= indexOf(node, endContainer)) {
                        return (short) 1;
                    }
                    return (short) -1;
                }
                node = parentNode2;
            }
            node2 = endContainer;
            for (parentNode3 = endContainer.getParentNode(); parentNode3 != null; parentNode3 = parentNode3.getParentNode()) {
                if (parentNode3 == parentNode) {
                    if (indexOf(node2, parentNode) < i) {
                        return (short) 1;
                    }
                    return (short) -1;
                }
                node2 = parentNode3;
            }
            for (parentNode4 = endContainer; parentNode4 != null; parentNode4 = parentNode4.getParentNode()) {
                i2++;
            }
            for (parentNode5 = parentNode; parentNode5 != null; parentNode5 = parentNode5.getParentNode()) {
                i2--;
            }
            while (i2 > 0) {
                endContainer = endContainer.getParentNode();
                i2--;
            }
            while (i2 < 0) {
                parentNode = parentNode.getParentNode();
                i2++;
            }
            do {
                node3 = endContainer;
                endContainer = endContainer.getParentNode();
                node4 = parentNode;
                parentNode = parentNode.getParentNode();
            } while (endContainer != parentNode);
            for (nextSibling = node3.getNextSibling(); nextSibling != null; nextSibling = nextSibling.getNextSibling()) {
                if (nextSibling == node4) {
                    return (short) 1;
                }
            }
            return (short) -1;
        }
        endContainer = range.getStartContainer();
        parentNode = this.fStartContainer;
        endOffset = range.getStartOffset();
        i = this.fStartOffset;
        i2 = 0;
        if (endContainer == parentNode) {
            if (endOffset < i) {
                return (short) 1;
            }
            if (endOffset == i) {
                return (short) 0;
            }
            return (short) -1;
        }
        node = parentNode;
        while (parentNode2 != null) {
            if (parentNode2 == endContainer) {
                if (endOffset <= indexOf(node, endContainer)) {
                    return (short) 1;
                }
                return (short) -1;
            }
            node = parentNode2;
        }
        node2 = endContainer;
        while (parentNode3 != null) {
            if (parentNode3 == parentNode) {
                if (indexOf(node2, parentNode) < i) {
                    return (short) 1;
                }
                return (short) -1;
            }
            node2 = parentNode3;
        }
        while (parentNode4 != null) {
            i2++;
        }
        while (parentNode5 != null) {
            i2--;
        }
        while (i2 > 0) {
            endContainer = endContainer.getParentNode();
            i2--;
        }
        while (i2 < 0) {
            parentNode = parentNode.getParentNode();
            i2++;
        }
        do {
            node3 = endContainer;
            endContainer = endContainer.getParentNode();
            node4 = parentNode;
            parentNode = parentNode.getParentNode();
        } while (endContainer != parentNode);
        while (nextSibling != null) {
            if (nextSibling == node4) {
                return (short) 1;
            }
        }
        return (short) -1;
    }

    @Override // org.w3c.dom.ranges.Range
    public void deleteContents() throws DOMException {
        traverseContents(3);
    }

    void deleteData(CharacterData characterData, int i, int i2) {
        this.fDeleteNode = characterData;
        characterData.deleteData(i, i2);
        this.fDeleteNode = null;
    }

    @Override // org.w3c.dom.ranges.Range
    public void detach() {
        if (this.fDetach) {
            throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
        }
        this.fDetach = true;
        this.fDocument.removeRange(this);
    }

    @Override // org.w3c.dom.ranges.Range
    public DocumentFragment extractContents() throws DOMException {
        return traverseContents(1);
    }

    @Override // org.w3c.dom.ranges.Range
    public boolean getCollapsed() {
        if (this.fDetach) {
            throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
        }
        return this.fStartContainer == this.fEndContainer && this.fStartOffset == this.fEndOffset;
    }

    @Override // org.w3c.dom.ranges.Range
    public Node getCommonAncestorContainer() {
        Object obj = null;
        if (this.fDetach) {
            throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
        }
        ArrayList arrayList = new ArrayList();
        for (Node parentNode = this.fStartContainer; parentNode != null; parentNode = parentNode.getParentNode()) {
            arrayList.add(parentNode);
        }
        ArrayList arrayList2 = new ArrayList();
        for (Node parentNode2 = this.fEndContainer; parentNode2 != null; parentNode2 = parentNode2.getParentNode()) {
            arrayList2.add(parentNode2);
        }
        int size = arrayList.size() - 1;
        for (int size2 = arrayList2.size() - 1; size >= 0 && size2 >= 0 && arrayList.get(size) == arrayList2.get(size2); size2--) {
            obj = arrayList.get(size);
            size--;
        }
        return (Node) obj;
    }

    @Override // org.w3c.dom.ranges.Range
    public Node getEndContainer() {
        if (this.fDetach) {
            throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
        }
        return this.fEndContainer;
    }

    @Override // org.w3c.dom.ranges.Range
    public int getEndOffset() {
        if (this.fDetach) {
            throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
        }
        return this.fEndOffset;
    }

    @Override // org.w3c.dom.ranges.Range
    public Node getStartContainer() {
        if (this.fDetach) {
            throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
        }
        return this.fStartContainer;
    }

    @Override // org.w3c.dom.ranges.Range
    public int getStartOffset() {
        if (this.fDetach) {
            throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
        }
        return this.fStartOffset;
    }

    int indexOf(Node node, Node node2) {
        if (node.getParentNode() != node2) {
            return -1;
        }
        int i = 0;
        for (Node firstChild = node2.getFirstChild(); firstChild != node; firstChild = firstChild.getNextSibling()) {
            i++;
        }
        return i;
    }

    void insertData(CharacterData characterData, int i, String str) {
        this.fInsertNode = characterData;
        characterData.insertData(i, str);
        this.fInsertNode = null;
    }

    @Override // org.w3c.dom.ranges.Range
    public void insertNode(Node node) throws RangeException, DOMException {
        int i;
        int length;
        if (node == null) {
            return;
        }
        short nodeType = node.getNodeType();
        if (this.fDocument.errorChecking) {
            if (this.fDetach) {
                throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
            }
            if (this.fDocument != node.getOwnerDocument()) {
                throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
            }
            if (nodeType == 2 || nodeType == 6 || nodeType == 12 || nodeType == 9) {
                throw new RangeExceptionImpl((short) 2, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_NODE_TYPE_ERR", null));
            }
        }
        this.fInsertedFromRange = true;
        if (this.fStartContainer.getNodeType() == 3) {
            Node parentNode = this.fStartContainer.getParentNode();
            int length2 = parentNode.getChildNodes().getLength();
            Node nodeCloneNode = this.fStartContainer.cloneNode(false);
            ((TextImpl) nodeCloneNode).setNodeValueInternal(nodeCloneNode.getNodeValue().substring(this.fStartOffset));
            Node node2 = this.fStartContainer;
            ((TextImpl) node2).setNodeValueInternal(node2.getNodeValue().substring(0, this.fStartOffset));
            Node nextSibling = this.fStartContainer.getNextSibling();
            if (nextSibling != null) {
                if (parentNode != null) {
                    parentNode.insertBefore(node, nextSibling);
                    parentNode.insertBefore(nodeCloneNode, nextSibling);
                }
            } else if (parentNode != null) {
                parentNode.appendChild(node);
                parentNode.appendChild(nodeCloneNode);
            }
            Node node3 = this.fEndContainer;
            if (node3 == this.fStartContainer) {
                this.fEndContainer = nodeCloneNode;
                length = this.fEndOffset - this.fStartOffset;
            } else {
                if (node3 == parentNode) {
                    length = this.fEndOffset + (parentNode.getChildNodes().getLength() - length2);
                }
                signalSplitData(this.fStartContainer, nodeCloneNode, this.fStartOffset);
            }
            this.fEndOffset = length;
            signalSplitData(this.fStartContainer, nodeCloneNode, this.fStartOffset);
        } else {
            Node node4 = this.fEndContainer;
            int length3 = node4 == this.fStartContainer ? node4.getChildNodes().getLength() : 0;
            Node firstChild = this.fStartContainer.getFirstChild();
            for (int i2 = 0; i2 < this.fStartOffset && firstChild != null; i2++) {
                firstChild = firstChild.getNextSibling();
            }
            if (firstChild != null) {
                this.fStartContainer.insertBefore(node, firstChild);
            } else {
                this.fStartContainer.appendChild(node);
            }
            Node node5 = this.fEndContainer;
            if (node5 == this.fStartContainer && (i = this.fEndOffset) != 0) {
                this.fEndOffset = i + (node5.getChildNodes().getLength() - length3);
            }
        }
        this.fInsertedFromRange = false;
    }

    public void insertedNodeFromDOM(Node node) {
        if (node == null || this.fInsertNode == node || this.fInsertedFromRange) {
            return;
        }
        Node parentNode = node.getParentNode();
        Node node2 = this.fStartContainer;
        if (parentNode == node2) {
            int iIndexOf = indexOf(node, node2);
            int i = this.fStartOffset;
            if (iIndexOf < i) {
                this.fStartOffset = i + 1;
            }
        }
        Node node3 = this.fEndContainer;
        if (parentNode == node3) {
            int iIndexOf2 = indexOf(node, node3);
            int i2 = this.fEndOffset;
            if (iIndexOf2 < i2) {
                this.fEndOffset = i2 + 1;
            }
        }
    }

    boolean isAncestorOf(Node node, Node node2) {
        while (node2 != null) {
            if (node2 == node) {
                return true;
            }
            node2 = node2.getParentNode();
        }
        return false;
    }

    Node nextNode(Node node, boolean z) {
        Node nextSibling;
        Node firstChild;
        if (node == null) {
            return null;
        }
        if (z && (firstChild = node.getFirstChild()) != null) {
            return firstChild;
        }
        Node nextSibling2 = node.getNextSibling();
        if (nextSibling2 != null) {
            return nextSibling2;
        }
        do {
            node = node.getParentNode();
            if (node == null || node == this.fDocument) {
                return null;
            }
            nextSibling = node.getNextSibling();
        } while (nextSibling == null);
        return nextSibling;
    }

    void receiveDeletedText(CharacterDataImpl characterDataImpl, int i, int i2) {
        if (characterDataImpl == null || this.fDeleteNode == characterDataImpl) {
            return;
        }
        if (characterDataImpl == this.fStartContainer) {
            int i3 = this.fStartOffset;
            int i4 = i + i2;
            if (i3 > i4) {
                this.fStartOffset = (i3 - i4) + i;
            } else if (i3 > i) {
                this.fStartOffset = i;
            }
        }
        if (characterDataImpl == this.fEndContainer) {
            int i5 = this.fEndOffset;
            int i6 = i2 + i;
            if (i5 > i6) {
                i += i5 - i6;
            } else if (i5 <= i) {
                return;
            }
            this.fEndOffset = i;
        }
    }

    void receiveInsertedText(CharacterDataImpl characterDataImpl, int i, int i2) {
        int i3;
        int i4;
        if (characterDataImpl == null || this.fInsertNode == characterDataImpl) {
            return;
        }
        if (characterDataImpl == this.fStartContainer && i < (i4 = this.fStartOffset)) {
            this.fStartOffset = i4 + i2;
        }
        if (characterDataImpl != this.fEndContainer || i >= (i3 = this.fEndOffset)) {
            return;
        }
        this.fEndOffset = i3 + i2;
    }

    void receiveReplacedText(CharacterDataImpl characterDataImpl) {
        if (characterDataImpl == null) {
            return;
        }
        if (characterDataImpl == this.fStartContainer) {
            this.fStartOffset = 0;
        }
        if (characterDataImpl == this.fEndContainer) {
            this.fEndOffset = 0;
        }
    }

    void receiveSplitData(Node node, Node node2, int i) {
        int i2;
        int i3;
        if (node == null || node2 == null || this.fSplitNode == node) {
            return;
        }
        Node node3 = this.fStartContainer;
        if (node == node3 && node3.getNodeType() == 3 && (i3 = this.fStartOffset) > i) {
            this.fStartOffset = i3 - i;
            this.fStartContainer = node2;
        }
        Node node4 = this.fEndContainer;
        if (node == node4 && node4.getNodeType() == 3 && (i2 = this.fEndOffset) > i) {
            this.fEndOffset = i2 - i;
            this.fEndContainer = node2;
        }
    }

    Node removeChild(Node node, Node node2) {
        this.fRemoveChild = node2;
        Node nodeRemoveChild = node.removeChild(node2);
        this.fRemoveChild = null;
        return nodeRemoveChild;
    }

    void removeNode(Node node) {
        if (node == null || this.fRemoveChild == node) {
            return;
        }
        Node parentNode = node.getParentNode();
        Node node2 = this.fStartContainer;
        if (parentNode == node2) {
            int iIndexOf = indexOf(node, node2);
            int i = this.fStartOffset;
            if (iIndexOf < i) {
                this.fStartOffset = i - 1;
            }
        }
        Node node3 = this.fEndContainer;
        if (parentNode == node3) {
            int iIndexOf2 = indexOf(node, node3);
            int i2 = this.fEndOffset;
            if (iIndexOf2 < i2) {
                this.fEndOffset = i2 - 1;
            }
        }
        Node node4 = this.fStartContainer;
        if (parentNode == node4 && parentNode == this.fEndContainer) {
            return;
        }
        if (isAncestorOf(node, node4)) {
            this.fStartContainer = parentNode;
            this.fStartOffset = indexOf(node, parentNode);
        }
        if (isAncestorOf(node, this.fEndContainer)) {
            this.fEndContainer = parentNode;
            this.fEndOffset = indexOf(node, parentNode);
        }
    }

    @Override // org.w3c.dom.ranges.Range
    public void selectNode(Node node) throws RangeException {
        if (this.fDocument.errorChecking) {
            if (this.fDetach) {
                throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
            }
            if (!isLegalContainer(node.getParentNode()) || !isLegalContainedNode(node)) {
                throw new RangeExceptionImpl((short) 2, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_NODE_TYPE_ERR", null));
            }
            if (this.fDocument != node.getOwnerDocument() && this.fDocument != node) {
                throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
            }
        }
        Node parentNode = node.getParentNode();
        if (parentNode != null) {
            this.fStartContainer = parentNode;
            this.fEndContainer = parentNode;
            int i = 0;
            while (node != null) {
                i++;
                node = node.getPreviousSibling();
            }
            this.fStartOffset = i - 1;
            this.fEndOffset = i;
        }
    }

    @Override // org.w3c.dom.ranges.Range
    public void selectNodeContents(Node node) throws RangeException {
        if (this.fDocument.errorChecking) {
            if (this.fDetach) {
                throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
            }
            if (!isLegalContainer(node)) {
                throw new RangeExceptionImpl((short) 2, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_NODE_TYPE_ERR", null));
            }
            if (this.fDocument != node.getOwnerDocument() && this.fDocument != node) {
                throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
            }
        }
        this.fStartContainer = node;
        this.fEndContainer = node;
        Node firstChild = node.getFirstChild();
        int i = 0;
        this.fStartOffset = 0;
        if (firstChild != null) {
            while (firstChild != null) {
                i++;
                firstChild = firstChild.getNextSibling();
            }
        }
        this.fEndOffset = i;
    }

    @Override // org.w3c.dom.ranges.Range
    public void setEnd(Node node, int i) throws RangeException, DOMException {
        if (this.fDocument.errorChecking) {
            if (this.fDetach) {
                throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
            }
            if (!isLegalContainer(node)) {
                throw new RangeExceptionImpl((short) 2, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_NODE_TYPE_ERR", null));
            }
            if (this.fDocument != node.getOwnerDocument() && this.fDocument != node) {
                throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
            }
        }
        checkIndex(node, i);
        this.fEndContainer = node;
        this.fEndOffset = i;
        if (getCommonAncestorContainer() == null || (this.fStartContainer == this.fEndContainer && this.fEndOffset < this.fStartOffset)) {
            collapse(false);
        }
    }

    @Override // org.w3c.dom.ranges.Range
    public void setEndAfter(Node node) throws RangeException {
        if (this.fDocument.errorChecking) {
            if (this.fDetach) {
                throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
            }
            if (!hasLegalRootContainer(node) || !isLegalContainedNode(node)) {
                throw new RangeExceptionImpl((short) 2, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_NODE_TYPE_ERR", null));
            }
            if (this.fDocument != node.getOwnerDocument() && this.fDocument != node) {
                throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
            }
        }
        this.fEndContainer = node.getParentNode();
        int i = 0;
        while (node != null) {
            i++;
            node = node.getPreviousSibling();
        }
        this.fEndOffset = i;
        if (getCommonAncestorContainer() == null || (this.fStartContainer == this.fEndContainer && this.fEndOffset < this.fStartOffset)) {
            collapse(false);
        }
    }

    @Override // org.w3c.dom.ranges.Range
    public void setEndBefore(Node node) throws RangeException {
        if (this.fDocument.errorChecking) {
            if (this.fDetach) {
                throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
            }
            if (!hasLegalRootContainer(node) || !isLegalContainedNode(node)) {
                throw new RangeExceptionImpl((short) 2, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_NODE_TYPE_ERR", null));
            }
            if (this.fDocument != node.getOwnerDocument() && this.fDocument != node) {
                throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
            }
        }
        this.fEndContainer = node.getParentNode();
        int i = 0;
        while (node != null) {
            i++;
            node = node.getPreviousSibling();
        }
        this.fEndOffset = i - 1;
        if (getCommonAncestorContainer() == null || (this.fStartContainer == this.fEndContainer && this.fEndOffset < this.fStartOffset)) {
            collapse(false);
        }
    }

    @Override // org.w3c.dom.ranges.Range
    public void setStart(Node node, int i) throws RangeException, DOMException {
        if (this.fDocument.errorChecking) {
            if (this.fDetach) {
                throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
            }
            if (!isLegalContainer(node)) {
                throw new RangeExceptionImpl((short) 2, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_NODE_TYPE_ERR", null));
            }
            if (this.fDocument != node.getOwnerDocument() && this.fDocument != node) {
                throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
            }
        }
        checkIndex(node, i);
        this.fStartContainer = node;
        this.fStartOffset = i;
        if (getCommonAncestorContainer() == null || (this.fStartContainer == this.fEndContainer && this.fEndOffset < this.fStartOffset)) {
            collapse(true);
        }
    }

    @Override // org.w3c.dom.ranges.Range
    public void setStartAfter(Node node) throws RangeException {
        if (this.fDocument.errorChecking) {
            if (this.fDetach) {
                throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
            }
            if (!hasLegalRootContainer(node) || !isLegalContainedNode(node)) {
                throw new RangeExceptionImpl((short) 2, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_NODE_TYPE_ERR", null));
            }
            if (this.fDocument != node.getOwnerDocument() && this.fDocument != node) {
                throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
            }
        }
        this.fStartContainer = node.getParentNode();
        int i = 0;
        while (node != null) {
            i++;
            node = node.getPreviousSibling();
        }
        this.fStartOffset = i;
        if (getCommonAncestorContainer() == null || (this.fStartContainer == this.fEndContainer && this.fEndOffset < this.fStartOffset)) {
            collapse(true);
        }
    }

    @Override // org.w3c.dom.ranges.Range
    public void setStartBefore(Node node) throws RangeException {
        if (this.fDocument.errorChecking) {
            if (this.fDetach) {
                throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
            }
            if (!hasLegalRootContainer(node) || !isLegalContainedNode(node)) {
                throw new RangeExceptionImpl((short) 2, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_NODE_TYPE_ERR", null));
            }
            if (this.fDocument != node.getOwnerDocument() && this.fDocument != node) {
                throw new DOMException((short) 4, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "WRONG_DOCUMENT_ERR", null));
            }
        }
        this.fStartContainer = node.getParentNode();
        int i = 0;
        while (node != null) {
            i++;
            node = node.getPreviousSibling();
        }
        this.fStartOffset = i - 1;
        if (getCommonAncestorContainer() == null || (this.fStartContainer == this.fEndContainer && this.fEndOffset < this.fStartOffset)) {
            collapse(true);
        }
    }

    void signalSplitData(Node node, Node node2, int i) {
        this.fSplitNode = node;
        this.fDocument.splitData(node, node2, i);
        this.fSplitNode = null;
    }

    @Override // org.w3c.dom.ranges.Range
    public void surroundContents(Node node) throws RangeException, DOMException {
        if (node == null) {
            return;
        }
        short nodeType = node.getNodeType();
        if (this.fDocument.errorChecking) {
            if (this.fDetach) {
                throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
            }
            if (nodeType == 2 || nodeType == 6 || nodeType == 12 || nodeType == 10 || nodeType == 9 || nodeType == 11) {
                throw new RangeExceptionImpl((short) 2, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_NODE_TYPE_ERR", null));
            }
        }
        Node parentNode = this.fStartContainer;
        Node parentNode2 = this.fEndContainer;
        if (parentNode.getNodeType() == 3) {
            parentNode = this.fStartContainer.getParentNode();
        }
        if (this.fEndContainer.getNodeType() == 3) {
            parentNode2 = this.fEndContainer.getParentNode();
        }
        if (parentNode != parentNode2) {
            throw new RangeExceptionImpl((short) 1, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "BAD_BOUNDARYPOINTS_ERR", null));
        }
        DocumentFragment documentFragmentExtractContents = extractContents();
        insertNode(node);
        node.appendChild(documentFragmentExtractContents);
        selectNode(node);
    }

    @Override // org.w3c.dom.ranges.Range
    public String toString() {
        Node nodeNextNode;
        if (this.fDetach) {
            throw new DOMException((short) 11, DOMMessageFormatter.formatMessage(DOMMessageFormatter.DOM_DOMAIN, "INVALID_STATE_ERR", null));
        }
        Node node = this.fStartContainer;
        Node nodeNextNode2 = this.fEndContainer;
        StringBuffer stringBuffer = new StringBuffer();
        if (this.fStartContainer.getNodeType() == 3 || this.fStartContainer.getNodeType() == 4) {
            Node node2 = this.fStartContainer;
            if (node2 == this.fEndContainer) {
                stringBuffer.append(node2.getNodeValue().substring(this.fStartOffset, this.fEndOffset));
                return stringBuffer.toString();
            }
            stringBuffer.append(node2.getNodeValue().substring(this.fStartOffset));
            nodeNextNode = nextNode(node, true);
        } else {
            nodeNextNode = node.getFirstChild();
            if (this.fStartOffset > 0) {
                for (int i = 0; i < this.fStartOffset && nodeNextNode != null; i++) {
                    nodeNextNode = nodeNextNode.getNextSibling();
                }
            }
            if (nodeNextNode == null) {
                nodeNextNode = nextNode(this.fStartContainer, false);
            }
        }
        if (this.fEndContainer.getNodeType() != 3 && this.fEndContainer.getNodeType() != 4) {
            int i2 = this.fEndOffset;
            Node firstChild = this.fEndContainer.getFirstChild();
            while (i2 > 0 && firstChild != null) {
                i2--;
                firstChild = firstChild.getNextSibling();
            }
            nodeNextNode2 = firstChild == null ? nextNode(this.fEndContainer, false) : firstChild;
        }
        while (nodeNextNode != nodeNextNode2 && nodeNextNode != null) {
            if (nodeNextNode.getNodeType() == 3 || nodeNextNode.getNodeType() == 4) {
                stringBuffer.append(nodeNextNode.getNodeValue());
            }
            nodeNextNode = nextNode(nodeNextNode, true);
        }
        if (this.fEndContainer.getNodeType() == 3 || this.fEndContainer.getNodeType() == 4) {
            stringBuffer.append(this.fEndContainer.getNodeValue().substring(0, this.fEndOffset));
        }
        return stringBuffer.toString();
    }
}
