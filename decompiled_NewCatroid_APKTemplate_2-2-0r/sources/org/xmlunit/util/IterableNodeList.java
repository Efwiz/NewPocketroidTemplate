package org.xmlunit.util;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* JADX INFO: loaded from: classes3.dex */
public final class IterableNodeList implements Iterable<Node> {
    private final int length;
    private final NodeList nl;

    public IterableNodeList(NodeList nodeList) {
        this.nl = nodeList;
        this.length = nodeList.getLength();
    }

    @Override // java.lang.Iterable
    public Iterator<Node> iterator() {
        return new NodeListIterator();
    }

    private class NodeListIterator implements Iterator<Node> {
        private int current;

        private NodeListIterator() {
            this.current = 0;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Iterator
        public Node next() {
            if (hasNext()) {
                NodeList nodeList = IterableNodeList.this.nl;
                int i = this.current;
                this.current = i + 1;
                return nodeList.item(i);
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.current < IterableNodeList.this.length;
        }
    }

    public static List<Node> asList(NodeList nodeList) {
        return Linqy.asList(new IterableNodeList(nodeList));
    }
}
