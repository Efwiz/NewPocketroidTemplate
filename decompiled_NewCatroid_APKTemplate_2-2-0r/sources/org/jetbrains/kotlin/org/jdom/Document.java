package org.jetbrains.kotlin.org.jdom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class Document extends CloneBase implements Parent {
    private static final long serialVersionUID = 200;
    transient ContentList content = new ContentList(this);
    protected String baseURI = null;
    private transient HashMap<String, Object> propertyMap = null;

    public final boolean equals(Object obj) {
        return obj == this;
    }

    @Override // org.jetbrains.kotlin.org.jdom.Parent
    public Parent getParent() {
        return null;
    }

    public boolean hasRootElement() {
        return this.content.indexOfFirstElement() >= 0;
    }

    public Element getRootElement() {
        int iIndexOfFirstElement = this.content.indexOfFirstElement();
        if (iIndexOfFirstElement < 0) {
            throw new IllegalStateException("Root element not set");
        }
        return (Element) this.content.get(iIndexOfFirstElement);
    }

    public DocType getDocType() {
        int iIndexOfDocType = this.content.indexOfDocType();
        if (iIndexOfDocType < 0) {
            return null;
        }
        return (DocType) this.content.get(iIndexOfDocType);
    }

    public Document addContent(Content content) {
        this.content.add(content);
        return this;
    }

    public Content getContent(int i) {
        return this.content.get(i);
    }

    public List<Content> getContent() {
        if (!hasRootElement()) {
            throw new IllegalStateException("Root element not set");
        }
        return this.content;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[Document: ");
        DocType docType = getDocType();
        if (docType != null) {
            sb.append(docType.toString()).append(", ");
        } else {
            sb.append(" No DOCTYPE declaration, ");
        }
        Element rootElement = hasRootElement() ? getRootElement() : null;
        if (rootElement != null) {
            sb.append("Root is ").append(rootElement.toString());
        } else {
            sb.append(" No root element");
        }
        sb.append("]");
        return sb.toString();
    }

    public final int hashCode() {
        return super.hashCode();
    }

    @Override // org.jetbrains.kotlin.org.jdom.CloneBase
    public Document clone() {
        Document document = (Document) super.clone();
        document.content = new ContentList(document);
        for (int i = 0; i < this.content.size(); i++) {
            Content content = this.content.get(i);
            if (content instanceof Element) {
                document.content.add(((Element) content).clone());
            } else if (content instanceof Comment) {
                document.content.add(((Comment) content).clone());
            } else if (content instanceof ProcessingInstruction) {
                document.content.add(((ProcessingInstruction) content).clone());
            } else if (content instanceof DocType) {
                document.content.add(((DocType) content).clone());
            }
        }
        return document;
    }

    @Override // org.jetbrains.kotlin.org.jdom.Parent
    public void canContainContent(Content content, int i, boolean z) {
        if (content instanceof Element) {
            int iIndexOfFirstElement = this.content.indexOfFirstElement();
            if (z && iIndexOfFirstElement == i) {
                return;
            }
            if (iIndexOfFirstElement >= 0) {
                throw new IllegalAddException("Cannot add a second root element, only one is allowed");
            }
            if (this.content.indexOfDocType() >= i) {
                throw new IllegalAddException("A root element cannot be added before the DocType");
            }
        }
        if (content instanceof DocType) {
            int iIndexOfDocType = this.content.indexOfDocType();
            if (z && iIndexOfDocType == i) {
                return;
            }
            if (iIndexOfDocType >= 0) {
                throw new IllegalAddException("Cannot add a second doctype, only one is allowed");
            }
            int iIndexOfFirstElement2 = this.content.indexOfFirstElement();
            if (iIndexOfFirstElement2 != -1 && iIndexOfFirstElement2 < i) {
                throw new IllegalAddException("A DocType cannot be added after the root element");
            }
        }
        if (content instanceof CDATA) {
            throw new IllegalAddException("A CDATA is not allowed at the document root");
        }
        if (content instanceof Text) {
            if (!Verifier.isAllXMLWhitespace(((Text) content).getText())) {
                throw new IllegalAddException("A Text is not allowed at the document root");
            }
        } else if (content instanceof EntityRef) {
            throw new IllegalAddException("An EntityRef is not allowed at the document root");
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        int size = this.content.size();
        objectOutputStream.writeInt(size);
        for (int i = 0; i < size; i++) {
            objectOutputStream.writeObject(getContent(i));
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.content = new ContentList(this);
        int i = objectInputStream.readInt();
        while (true) {
            i--;
            if (i < 0) {
                return;
            } else {
                addContent((Content) objectInputStream.readObject());
            }
        }
    }
}
