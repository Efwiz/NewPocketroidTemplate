package org.jetbrains.kotlin.org.jdom;

import java.io.Serializable;

/* JADX INFO: loaded from: classes4.dex */
public class Attribute extends CloneBase implements Serializable, Cloneable {
    private static final long serialVersionUID = 200;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    protected String f932name;
    protected Namespace namespace;
    protected transient Element parent;
    protected String value;
    public static final AttributeType UNDECLARED_TYPE = AttributeType.UNDECLARED;
    public static final AttributeType CDATA_TYPE = AttributeType.CDATA;
    public static final AttributeType ID_TYPE = AttributeType.ID;
    public static final AttributeType IDREF_TYPE = AttributeType.IDREF;
    public static final AttributeType IDREFS_TYPE = AttributeType.IDREFS;
    public static final AttributeType ENTITY_TYPE = AttributeType.ENTITY;
    public static final AttributeType ENTITIES_TYPE = AttributeType.ENTITIES;
    public static final AttributeType NMTOKEN_TYPE = AttributeType.NMTOKEN;
    public static final AttributeType NMTOKENS_TYPE = AttributeType.NMTOKENS;
    public static final AttributeType NOTATION_TYPE = AttributeType.NOTATION;
    public static final AttributeType ENUMERATED_TYPE = AttributeType.ENUMERATION;
    protected AttributeType type = AttributeType.UNDECLARED;
    protected boolean specified = true;

    protected Attribute() {
    }

    public Element getParent() {
        return this.parent;
    }

    public String getName() {
        return this.f932name;
    }

    public String getQualifiedName() {
        String prefix = this.namespace.getPrefix();
        if ("".equals(prefix)) {
            return getName();
        }
        return prefix + ':' + getName();
    }

    public String getNamespacePrefix() {
        return this.namespace.getPrefix();
    }

    public String getNamespaceURI() {
        return this.namespace.getURI();
    }

    public Namespace getNamespace() {
        return this.namespace;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return "[Attribute: " + getQualifiedName() + "=\"" + this.value + "\"]";
    }

    @Override // org.jetbrains.kotlin.org.jdom.CloneBase
    public Attribute clone() {
        Attribute attribute = (Attribute) super.clone();
        attribute.parent = null;
        return attribute;
    }

    protected Attribute setParent(Element element) {
        this.parent = element;
        return this;
    }
}
