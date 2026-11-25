package org.apache.xerces.impl.xs;

import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.xs.XSAnnotation;
import org.apache.xerces.xs.XSModelGroup;
import org.apache.xerces.xs.XSModelGroupDefinition;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSObjectList;

/* JADX INFO: loaded from: classes13.dex */
public class XSGroupDecl implements XSModelGroupDefinition {
    public String fName = null;
    public String fTargetNamespace = null;
    public XSModelGroupImpl fModelGroup = null;
    public XSObjectList fAnnotations = null;
    private XSNamespaceItem fNamespaceItem = null;

    @Override // org.apache.xerces.xs.XSModelGroupDefinition
    public XSAnnotation getAnnotation() {
        XSObjectList xSObjectList = this.fAnnotations;
        if (xSObjectList != null) {
            return (XSAnnotation) xSObjectList.item(0);
        }
        return null;
    }

    @Override // org.apache.xerces.xs.XSModelGroupDefinition
    public XSObjectList getAnnotations() {
        XSObjectList xSObjectList = this.fAnnotations;
        return xSObjectList != null ? xSObjectList : XSObjectListImpl.EMPTY_LIST;
    }

    @Override // org.apache.xerces.xs.XSModelGroupDefinition
    public XSModelGroup getModelGroup() {
        return this.fModelGroup;
    }

    @Override // org.apache.xerces.xs.XSObject
    public String getName() {
        return this.fName;
    }

    @Override // org.apache.xerces.xs.XSObject
    public String getNamespace() {
        return this.fTargetNamespace;
    }

    @Override // org.apache.xerces.xs.XSObject
    public XSNamespaceItem getNamespaceItem() {
        return this.fNamespaceItem;
    }

    @Override // org.apache.xerces.xs.XSObject
    public short getType() {
        return (short) 6;
    }

    void setNamespaceItem(XSNamespaceItem xSNamespaceItem) {
        this.fNamespaceItem = xSNamespaceItem;
    }
}
