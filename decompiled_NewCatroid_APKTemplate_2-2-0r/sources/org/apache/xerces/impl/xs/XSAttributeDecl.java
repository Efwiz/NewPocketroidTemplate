package org.apache.xerces.impl.xs;

import org.apache.xerces.impl.dv.ValidatedInfo;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xs.ShortList;
import org.apache.xerces.xs.XSAnnotation;
import org.apache.xerces.xs.XSAttributeDeclaration;
import org.apache.xerces.xs.XSComplexTypeDefinition;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSValue;

/* JADX INFO: loaded from: classes13.dex */
public class XSAttributeDecl implements XSAttributeDeclaration {
    public static final short SCOPE_ABSENT = 0;
    public static final short SCOPE_GLOBAL = 1;
    public static final short SCOPE_LOCAL = 2;
    String fName = null;
    String fTargetNamespace = null;
    XSSimpleType fType = null;
    public QName fUnresolvedTypeName = null;
    short fConstraintType = 0;
    short fScope = 0;
    XSComplexTypeDecl fEnclosingCT = null;
    XSObjectList fAnnotations = null;
    ValidatedInfo fDefault = null;
    private XSNamespaceItem fNamespaceItem = null;

    @Override // org.apache.xerces.xs.XSAttributeDeclaration
    public Object getActualVC() {
        if (getConstraintType() == 0) {
            return null;
        }
        return this.fDefault.actualValue;
    }

    @Override // org.apache.xerces.xs.XSAttributeDeclaration
    public short getActualVCType() {
        if (getConstraintType() == 0) {
            return (short) 45;
        }
        return this.fDefault.actualValueType;
    }

    @Override // org.apache.xerces.xs.XSAttributeDeclaration
    public XSAnnotation getAnnotation() {
        XSObjectList xSObjectList = this.fAnnotations;
        if (xSObjectList != null) {
            return (XSAnnotation) xSObjectList.item(0);
        }
        return null;
    }

    @Override // org.apache.xerces.xs.XSAttributeDeclaration
    public XSObjectList getAnnotations() {
        XSObjectList xSObjectList = this.fAnnotations;
        return xSObjectList != null ? xSObjectList : XSObjectListImpl.EMPTY_LIST;
    }

    @Override // org.apache.xerces.xs.XSAttributeDeclaration
    public short getConstraintType() {
        return this.fConstraintType;
    }

    @Override // org.apache.xerces.xs.XSAttributeDeclaration
    public String getConstraintValue() {
        if (getConstraintType() == 0) {
            return null;
        }
        return this.fDefault.stringValue();
    }

    @Override // org.apache.xerces.xs.XSAttributeDeclaration
    public XSComplexTypeDefinition getEnclosingCTDefinition() {
        return this.fEnclosingCT;
    }

    @Override // org.apache.xerces.xs.XSAttributeDeclaration
    public ShortList getItemValueTypes() {
        if (getConstraintType() == 0) {
            return null;
        }
        return this.fDefault.itemValueTypes;
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

    @Override // org.apache.xerces.xs.XSAttributeDeclaration
    public short getScope() {
        return this.fScope;
    }

    @Override // org.apache.xerces.xs.XSObject
    public short getType() {
        return (short) 1;
    }

    @Override // org.apache.xerces.xs.XSAttributeDeclaration
    public XSSimpleTypeDefinition getTypeDefinition() {
        return this.fType;
    }

    public ValidatedInfo getValInfo() {
        return this.fDefault;
    }

    @Override // org.apache.xerces.xs.XSAttributeDeclaration
    public XSValue getValueConstraintValue() {
        return this.fDefault;
    }

    public void reset() {
        this.fName = null;
        this.fTargetNamespace = null;
        this.fType = null;
        this.fUnresolvedTypeName = null;
        this.fConstraintType = (short) 0;
        this.fScope = (short) 0;
        this.fDefault = null;
        this.fAnnotations = null;
    }

    void setNamespaceItem(XSNamespaceItem xSNamespaceItem) {
        this.fNamespaceItem = xSNamespaceItem;
    }

    public void setValues(String str, String str2, XSSimpleType xSSimpleType, short s, short s2, ValidatedInfo validatedInfo, XSComplexTypeDecl xSComplexTypeDecl, XSObjectList xSObjectList) {
        this.fName = str;
        this.fTargetNamespace = str2;
        this.fType = xSSimpleType;
        this.fConstraintType = s;
        this.fScope = s2;
        this.fDefault = validatedInfo;
        this.fEnclosingCT = xSComplexTypeDecl;
        this.fAnnotations = xSObjectList;
    }
}
