package org.apache.xerces.stax;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

/* JADX INFO: loaded from: classes13.dex */
public final class DefaultNamespaceContext implements NamespaceContext {
    private static final DefaultNamespaceContext DEFAULT_NAMESPACE_CONTEXT_INSTANCE = new DefaultNamespaceContext();

    private DefaultNamespaceContext() {
    }

    public static DefaultNamespaceContext getInstance() {
        return DEFAULT_NAMESPACE_CONTEXT_INSTANCE;
    }

    @Override // javax.xml.namespace.NamespaceContext
    public String getNamespaceURI(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Prefix cannot be null.");
        }
        if ("xml".equals(str)) {
            return "http://www.w3.org/XML/1998/namespace";
        }
        return XMLConstants.XMLNS_ATTRIBUTE.equals(str) ? XMLConstants.XMLNS_ATTRIBUTE_NS_URI : "";
    }

    @Override // javax.xml.namespace.NamespaceContext
    public String getPrefix(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Namespace URI cannot be null.");
        }
        if ("http://www.w3.org/XML/1998/namespace".equals(str)) {
            return "xml";
        }
        if (XMLConstants.XMLNS_ATTRIBUTE_NS_URI.equals(str)) {
            return XMLConstants.XMLNS_ATTRIBUTE;
        }
        return null;
    }

    @Override // javax.xml.namespace.NamespaceContext
    public Iterator getPrefixes(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Namespace URI cannot be null.");
        }
        if ("http://www.w3.org/XML/1998/namespace".equals(str)) {
            return new Iterator() { // from class: org.apache.xerces.stax.DefaultNamespaceContext.1
                boolean more = true;

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.more;
                }

                @Override // java.util.Iterator
                public Object next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.more = false;
                    return "xml";
                }

                @Override // java.util.Iterator
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
        return XMLConstants.XMLNS_ATTRIBUTE_NS_URI.equals(str) ? new Iterator() { // from class: org.apache.xerces.stax.DefaultNamespaceContext.2
            boolean more = true;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.more;
            }

            @Override // java.util.Iterator
            public Object next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                this.more = false;
                return XMLConstants.XMLNS_ATTRIBUTE;
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException();
            }
        } : Collections.EMPTY_LIST.iterator();
    }
}
