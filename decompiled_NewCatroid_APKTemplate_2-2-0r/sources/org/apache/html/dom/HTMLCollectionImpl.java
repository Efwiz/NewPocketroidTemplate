package org.apache.html.dom;

import java.io.Serializable;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.html.HTMLAnchorElement;
import org.w3c.dom.html.HTMLAppletElement;
import org.w3c.dom.html.HTMLAreaElement;
import org.w3c.dom.html.HTMLCollection;
import org.w3c.dom.html.HTMLElement;
import org.w3c.dom.html.HTMLFormElement;
import org.w3c.dom.html.HTMLImageElement;
import org.w3c.dom.html.HTMLObjectElement;
import org.w3c.dom.html.HTMLOptionElement;
import org.w3c.dom.html.HTMLTableCellElement;
import org.w3c.dom.html.HTMLTableRowElement;
import org.w3c.dom.html.HTMLTableSectionElement;

/* JADX INFO: loaded from: classes13.dex */
class HTMLCollectionImpl implements HTMLCollection, Serializable {
    static final short ANCHOR = 1;
    static final short APPLET = 4;
    static final short AREA = -1;
    static final short CELL = -3;
    static final short ELEMENT = 8;
    static final short FORM = 2;
    static final short IMAGE = 3;
    static final short LINK = 5;
    static final short OPTION = 6;
    static final short ROW = 7;
    static final short TBODY = -2;
    private static final long serialVersionUID = 9112122196669185082L;
    private short _lookingFor;
    private Element _topLevel;

    HTMLCollectionImpl(HTMLElement hTMLElement, short s) {
        if (hTMLElement == null) {
            throw new NullPointerException("HTM011 Argument 'topLevel' is null.");
        }
        this._topLevel = hTMLElement;
        this._lookingFor = s;
    }

    private int getLength(Element element) {
        int length;
        synchronized (element) {
            length = 0;
            for (Node firstChild = element.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                if (firstChild instanceof Element) {
                    if (collectionMatch((Element) firstChild, null)) {
                        length++;
                    } else if (recurse()) {
                        length += getLength((Element) firstChild);
                    }
                }
            }
        }
        return length;
    }

    private Node item(Element element, CollectionIndex collectionIndex) {
        Node nodeItem;
        synchronized (element) {
            for (Node firstChild = element.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                if (firstChild instanceof Element) {
                    if (collectionMatch((Element) firstChild, null)) {
                        if (collectionIndex.isZero()) {
                            return firstChild;
                        }
                        collectionIndex.decrement();
                    } else if (recurse() && (nodeItem = item((Element) firstChild, collectionIndex)) != null) {
                        return nodeItem;
                    }
                }
            }
            return null;
        }
    }

    private Node namedItem(Element element, String str) {
        Node nodeNamedItem;
        synchronized (element) {
            Node firstChild = element.getFirstChild();
            while (firstChild != null) {
                if (firstChild instanceof Element) {
                    if (collectionMatch((Element) firstChild, str)) {
                        return firstChild;
                    }
                    if (recurse() && (nodeNamedItem = namedItem((Element) firstChild, str)) != null) {
                        return nodeNamedItem;
                    }
                }
                firstChild = firstChild.getNextSibling();
            }
            return firstChild;
        }
    }

    /* JADX WARN: Code duplicated, block: B:15:0x002a  */
    protected boolean collectionMatch(Element element, String str) {
        synchronized (element) {
            boolean zEquals = false;
            switch (this._lookingFor) {
                case -3:
                    zEquals = element instanceof HTMLTableCellElement;
                    break;
                case -2:
                    if ((element instanceof HTMLTableSectionElement) && element.getTagName().equals("TBODY")) {
                        zEquals = true;
                    }
                    break;
                case -1:
                    zEquals = element instanceof HTMLAreaElement;
                    break;
                case 1:
                    if ((element instanceof HTMLAnchorElement) && element.getAttribute("name").length() > 0) {
                        zEquals = true;
                    }
                    break;
                case 2:
                    zEquals = element instanceof HTMLFormElement;
                    break;
                case 3:
                    zEquals = element instanceof HTMLImageElement;
                    break;
                case 4:
                    if ((element instanceof HTMLAppletElement) || ((element instanceof HTMLObjectElement) && ("application/java".equals(element.getAttribute("codetype")) || element.getAttribute("classid").startsWith("java:")))) {
                        zEquals = true;
                    }
                    break;
                case 5:
                    if (((element instanceof HTMLAnchorElement) || (element instanceof HTMLAreaElement)) && element.getAttribute("href").length() > 0) {
                        zEquals = true;
                    }
                    break;
                case 6:
                    zEquals = element instanceof HTMLOptionElement;
                    break;
                case 7:
                    zEquals = element instanceof HTMLTableRowElement;
                    break;
                case 8:
                    zEquals = element instanceof HTMLFormControl;
                    break;
            }
            if (zEquals && str != null) {
                if ((element instanceof HTMLAnchorElement) && str.equals(element.getAttribute("name"))) {
                    return true;
                }
                zEquals = str.equals(element.getAttribute("id"));
            }
            return zEquals;
        }
    }

    @Override // org.w3c.dom.html.HTMLCollection
    public final int getLength() {
        return getLength(this._topLevel);
    }

    @Override // org.w3c.dom.html.HTMLCollection
    public final Node item(int i) {
        if (i >= 0) {
            return item(this._topLevel, new CollectionIndex(i));
        }
        throw new IllegalArgumentException("HTM012 Argument 'index' is negative.");
    }

    @Override // org.w3c.dom.html.HTMLCollection
    public final Node namedItem(String str) {
        if (str != null) {
            return namedItem(this._topLevel, str);
        }
        throw new NullPointerException("HTM013 Argument 'name' is null.");
    }

    protected boolean recurse() {
        return this._lookingFor > 0;
    }
}
