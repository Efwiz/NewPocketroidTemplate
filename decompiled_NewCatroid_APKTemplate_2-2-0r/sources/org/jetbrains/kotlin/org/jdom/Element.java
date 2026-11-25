package org.jetbrains.kotlin.org.jdom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes4.dex */
public class Element extends Content implements Parent {
    private static final long serialVersionUID = 200;
    transient List<Namespace> additionalNamespaces;
    transient AttributeList attributes;
    transient ContentList content;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected String f933name;
    protected Namespace namespace;

    protected Element() {
        super(Content.CType.Element);
        this.additionalNamespaces = null;
        this.attributes = null;
        this.content = new ContentList(this);
    }

    public String getName() {
        return this.f933name;
    }

    public Namespace getNamespace() {
        return this.namespace;
    }

    public String getNamespaceURI() {
        return this.namespace.getURI();
    }

    public String getQualifiedName() {
        if ("".equals(this.namespace.getPrefix())) {
            return getName();
        }
        return this.namespace.getPrefix() + ':' + this.f933name;
    }

    public void addNamespaceDeclaration(Namespace namespace) {
        if (this.additionalNamespaces == null) {
            this.additionalNamespaces = new ArrayList(5);
        }
        Iterator<Namespace> it = this.additionalNamespaces.iterator();
        while (it.hasNext()) {
            if (it.next() == namespace) {
                return;
            }
        }
        String strCheckNamespaceCollision = Verifier.checkNamespaceCollision(namespace, this);
        if (strCheckNamespaceCollision != null) {
            throw new IllegalAddException(this, namespace, strCheckNamespaceCollision);
        }
        this.additionalNamespaces.add(namespace);
    }

    public List<Namespace> getAdditionalNamespaces() {
        List<Namespace> list = this.additionalNamespaces;
        if (list == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(list);
    }

    public List<Content> getContent() {
        return this.content;
    }

    public Element addContent(Content content) {
        this.content.add(content);
        return this;
    }

    public boolean isAncestor(Element element) {
        for (Parent parent = element.getParent(); parent instanceof Element; parent = parent.getParent()) {
            if (parent == this) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAttributes() {
        AttributeList attributeList = this.attributes;
        return (attributeList == null || attributeList.isEmpty()) ? false : true;
    }

    public boolean hasAdditionalNamespaces() {
        List<Namespace> list = this.additionalNamespaces;
        return (list == null || list.isEmpty()) ? false : true;
    }

    AttributeList getAttributeList() {
        if (this.attributes == null) {
            this.attributes = new AttributeList(this);
        }
        return this.attributes;
    }

    public List<Attribute> getAttributes() {
        return getAttributeList();
    }

    public String getAttributeValue(String str, Namespace namespace) {
        if (this.attributes == null) {
            return null;
        }
        return getAttributeValue(str, namespace, null);
    }

    public String getAttributeValue(String str, Namespace namespace, String str2) {
        Attribute attribute;
        return (this.attributes == null || (attribute = getAttributeList().get(str, namespace)) == null) ? str2 : attribute.getValue();
    }

    public Element setAttribute(Attribute attribute) {
        getAttributeList().add(attribute);
        return this;
    }

    public String toString() {
        StringBuilder sbAppend = new StringBuilder(64).append("[Element: <").append(getQualifiedName());
        String namespaceURI = getNamespaceURI();
        if (!"".equals(namespaceURI)) {
            sbAppend.append(" [Namespace: ").append(namespaceURI).append("]");
        }
        sbAppend.append("/>]");
        return sbAppend.toString();
    }

    @Override // org.jetbrains.kotlin.org.jdom.Content, org.jetbrains.kotlin.org.jdom.CloneBase
    public Element clone() {
        Element element = (Element) super.clone();
        element.content = new ContentList(element);
        element.attributes = this.attributes == null ? null : new AttributeList(element);
        if (this.attributes != null) {
            for (int i = 0; i < this.attributes.size(); i++) {
                element.attributes.add(this.attributes.get(i).clone());
            }
        }
        if (this.additionalNamespaces != null) {
            element.additionalNamespaces = new ArrayList(this.additionalNamespaces);
        }
        for (int i2 = 0; i2 < this.content.size(); i2++) {
            element.content.add(this.content.get(i2).clone());
        }
        return element;
    }

    @Override // org.jetbrains.kotlin.org.jdom.Parent
    public void canContainContent(Content content, int i, boolean z) throws IllegalAddException {
        if (content instanceof DocType) {
            throw new IllegalAddException("A DocType is not allowed except at the document level");
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        if (hasAdditionalNamespaces()) {
            int size = this.additionalNamespaces.size();
            objectOutputStream.writeInt(size);
            for (int i = 0; i < size; i++) {
                objectOutputStream.writeObject(this.additionalNamespaces.get(i));
            }
        } else {
            objectOutputStream.writeInt(0);
        }
        if (hasAttributes()) {
            int size2 = this.attributes.size();
            objectOutputStream.writeInt(size2);
            for (int i2 = 0; i2 < size2; i2++) {
                objectOutputStream.writeObject(this.attributes.get(i2));
            }
        } else {
            objectOutputStream.writeInt(0);
        }
        int size3 = this.content.size();
        objectOutputStream.writeInt(size3);
        for (int i3 = 0; i3 < size3; i3++) {
            objectOutputStream.writeObject(this.content.get(i3));
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.content = new ContentList(this);
        int i = objectInputStream.readInt();
        while (true) {
            i--;
            if (i < 0) {
                break;
            } else {
                addNamespaceDeclaration((Namespace) objectInputStream.readObject());
            }
        }
        int i2 = objectInputStream.readInt();
        while (true) {
            i2--;
            if (i2 < 0) {
                break;
            } else {
                setAttribute((Attribute) objectInputStream.readObject());
            }
        }
        int i3 = objectInputStream.readInt();
        while (true) {
            i3--;
            if (i3 < 0) {
                return;
            } else {
                addContent((Content) objectInputStream.readObject());
            }
        }
    }
}
