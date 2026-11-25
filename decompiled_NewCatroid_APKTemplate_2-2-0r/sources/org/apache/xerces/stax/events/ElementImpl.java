package org.apache.xerces.stax.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.events.Namespace;

/* JADX INFO: loaded from: classes13.dex */
abstract class ElementImpl extends XMLEventImpl {
    private final QName fName;
    private final List fNamespaces;

    private static final class NoRemoveIterator implements Iterator {
        private final Iterator fWrapped;

        public NoRemoveIterator(Iterator it) {
            this.fWrapped = it;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.fWrapped.hasNext();
        }

        @Override // java.util.Iterator
        public Object next() {
            return this.fWrapped.next();
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Attributes iterator is read-only.");
        }
    }

    ElementImpl(QName qName, boolean z, Iterator it, Location location) {
        super(z ? 1 : 2, location);
        this.fName = qName;
        if (it == null || !it.hasNext()) {
            this.fNamespaces = Collections.EMPTY_LIST;
            return;
        }
        this.fNamespaces = new ArrayList();
        do {
            this.fNamespaces.add((Namespace) it.next());
        } while (it.hasNext());
    }

    static Iterator createImmutableIterator(Iterator it) {
        return new NoRemoveIterator(it);
    }

    public final QName getName() {
        return this.fName;
    }

    public final Iterator getNamespaces() {
        return createImmutableIterator(this.fNamespaces.iterator());
    }
}
