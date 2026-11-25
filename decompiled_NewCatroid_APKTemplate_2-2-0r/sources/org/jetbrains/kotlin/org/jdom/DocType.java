package org.jetbrains.kotlin.org.jdom;

import org.jetbrains.kotlin.org.jdom.output.XMLOutputter2;

/* JADX INFO: loaded from: classes4.dex */
public class DocType extends Content {
    private static final long serialVersionUID = 200;
    protected String elementName;
    protected String internalSubset;
    protected String publicID;
    protected String systemID;

    protected DocType() {
        super(Content.CType.DocType);
    }

    public String getElementName() {
        return this.elementName;
    }

    public String getPublicID() {
        return this.publicID;
    }

    public String getSystemID() {
        return this.systemID;
    }

    public String getInternalSubset() {
        return this.internalSubset;
    }

    public String toString() {
        return "[DocType: " + new XMLOutputter2().outputString(this) + "]";
    }

    @Override // org.jetbrains.kotlin.org.jdom.Content, org.jetbrains.kotlin.org.jdom.CloneBase
    public DocType clone() {
        return (DocType) super.clone();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.org.jdom.Content
    public DocType setParent(Parent parent) {
        return (DocType) super.setParent(parent);
    }

    @Override // org.jetbrains.kotlin.org.jdom.Content
    public Document getParent() {
        return (Document) super.getParent();
    }
}
