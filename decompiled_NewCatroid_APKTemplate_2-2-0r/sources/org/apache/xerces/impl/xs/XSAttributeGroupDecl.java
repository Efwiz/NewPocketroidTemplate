package org.apache.xerces.impl.xs;

import org.apache.xerces.impl.dv.ValidatedInfo;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.xs.XSAnnotation;
import org.apache.xerces.xs.XSAttributeGroupDefinition;
import org.apache.xerces.xs.XSAttributeUse;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSWildcard;

/* JADX INFO: loaded from: classes13.dex */
public class XSAttributeGroupDecl implements XSAttributeGroupDefinition {
    private static final int INITIAL_SIZE = 5;
    public XSObjectList fAnnotations;
    public String fName = null;
    public String fTargetNamespace = null;
    int fAttrUseNum = 0;
    XSAttributeUseImpl[] fAttributeUses = new XSAttributeUseImpl[5];
    public XSWildcardDecl fAttributeWC = null;
    public String fIDAttrName = null;
    protected XSObjectListImpl fAttrUses = null;
    private XSNamespaceItem fNamespaceItem = null;

    static final XSAttributeUseImpl[] resize(XSAttributeUseImpl[] xSAttributeUseImplArr, int i) {
        XSAttributeUseImpl[] xSAttributeUseImplArr2 = new XSAttributeUseImpl[i];
        System.arraycopy(xSAttributeUseImplArr, 0, xSAttributeUseImplArr2, 0, Math.min(xSAttributeUseImplArr.length, i));
        return xSAttributeUseImplArr2;
    }

    public String addAttributeUse(XSAttributeUseImpl xSAttributeUseImpl) {
        if (xSAttributeUseImpl.fUse != 2 && xSAttributeUseImpl.fAttrDecl.fType.isIDType()) {
            String str = this.fIDAttrName;
            if (str != null) {
                return str;
            }
            this.fIDAttrName = xSAttributeUseImpl.fAttrDecl.fName;
        }
        int i = this.fAttrUseNum;
        XSAttributeUseImpl[] xSAttributeUseImplArr = this.fAttributeUses;
        if (i == xSAttributeUseImplArr.length) {
            this.fAttributeUses = resize(xSAttributeUseImplArr, i * 2);
        }
        XSAttributeUseImpl[] xSAttributeUseImplArr2 = this.fAttributeUses;
        int i2 = this.fAttrUseNum;
        this.fAttrUseNum = i2 + 1;
        xSAttributeUseImplArr2[i2] = xSAttributeUseImpl;
        return null;
    }

    @Override // org.apache.xerces.xs.XSAttributeGroupDefinition
    public XSAnnotation getAnnotation() {
        XSObjectList xSObjectList = this.fAnnotations;
        if (xSObjectList != null) {
            return (XSAnnotation) xSObjectList.item(0);
        }
        return null;
    }

    @Override // org.apache.xerces.xs.XSAttributeGroupDefinition
    public XSObjectList getAnnotations() {
        XSObjectList xSObjectList = this.fAnnotations;
        return xSObjectList != null ? xSObjectList : XSObjectListImpl.EMPTY_LIST;
    }

    public XSAttributeUse getAttributeUse(String str, String str2) {
        for (int i = 0; i < this.fAttrUseNum; i++) {
            if (this.fAttributeUses[i].fAttrDecl.fTargetNamespace == str && this.fAttributeUses[i].fAttrDecl.fName == str2) {
                return this.fAttributeUses[i];
            }
        }
        return null;
    }

    public XSAttributeUse getAttributeUseNoProhibited(String str, String str2) {
        for (int i = 0; i < this.fAttrUseNum; i++) {
            if (this.fAttributeUses[i].fAttrDecl.fTargetNamespace == str && this.fAttributeUses[i].fAttrDecl.fName == str2 && this.fAttributeUses[i].fUse != 2) {
                return this.fAttributeUses[i];
            }
        }
        return null;
    }

    @Override // org.apache.xerces.xs.XSAttributeGroupDefinition
    public XSObjectList getAttributeUses() {
        if (this.fAttrUses == null) {
            this.fAttrUses = this.fAttrUseNum > 0 ? new XSObjectListImpl(this.fAttributeUses, this.fAttrUseNum) : XSObjectListImpl.EMPTY_LIST;
        }
        return this.fAttrUses;
    }

    @Override // org.apache.xerces.xs.XSAttributeGroupDefinition
    public XSWildcard getAttributeWildcard() {
        return this.fAttributeWC;
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
        return (short) 5;
    }

    public void removeProhibitedAttrs() {
        int i = this.fAttrUseNum;
        if (i == 0) {
            return;
        }
        XSAttributeUseImpl[] xSAttributeUseImplArr = new XSAttributeUseImpl[i];
        int i2 = 0;
        for (int i3 = 0; i3 < this.fAttrUseNum; i3++) {
            if (this.fAttributeUses[i3].fUse != 2) {
                xSAttributeUseImplArr[i2] = this.fAttributeUses[i3];
                i2++;
            }
        }
        this.fAttributeUses = xSAttributeUseImplArr;
        this.fAttrUseNum = i2;
    }

    public void replaceAttributeUse(XSAttributeUse xSAttributeUse, XSAttributeUseImpl xSAttributeUseImpl) {
        for (int i = 0; i < this.fAttrUseNum; i++) {
            XSAttributeUseImpl[] xSAttributeUseImplArr = this.fAttributeUses;
            if (xSAttributeUseImplArr[i] == xSAttributeUse) {
                xSAttributeUseImplArr[i] = xSAttributeUseImpl;
            }
        }
    }

    public void reset() {
        this.fName = null;
        this.fTargetNamespace = null;
        for (int i = 0; i < this.fAttrUseNum; i++) {
            this.fAttributeUses[i] = null;
        }
        this.fAttrUseNum = 0;
        this.fAttributeWC = null;
        this.fAnnotations = null;
        this.fIDAttrName = null;
    }

    void setNamespaceItem(XSNamespaceItem xSNamespaceItem) {
        this.fNamespaceItem = xSNamespaceItem;
    }

    public Object[] validRestrictionOf(String str, XSAttributeGroupDecl xSAttributeGroupDecl) {
        for (int i = 0; i < this.fAttrUseNum; i++) {
            XSAttributeUseImpl xSAttributeUseImpl = this.fAttributeUses[i];
            XSAttributeDecl xSAttributeDecl = xSAttributeUseImpl.fAttrDecl;
            XSAttributeUseImpl xSAttributeUseImpl2 = (XSAttributeUseImpl) xSAttributeGroupDecl.getAttributeUse(xSAttributeDecl.fTargetNamespace, xSAttributeDecl.fName);
            if (xSAttributeUseImpl2 == null) {
                XSWildcardDecl xSWildcardDecl = xSAttributeGroupDecl.fAttributeWC;
                if (xSWildcardDecl == null) {
                    return new Object[]{str, xSAttributeDecl.fName, "derivation-ok-restriction.2.2.a"};
                }
                if (!xSWildcardDecl.allowNamespace(xSAttributeDecl.fTargetNamespace)) {
                    return new Object[]{str, xSAttributeDecl.fName, xSAttributeDecl.fTargetNamespace == null ? "" : xSAttributeDecl.fTargetNamespace, "derivation-ok-restriction.2.2.b"};
                }
            } else {
                if (xSAttributeUseImpl2.getRequired() && !xSAttributeUseImpl.getRequired()) {
                    return new Object[]{str, xSAttributeDecl.fName, xSAttributeUseImpl.fUse == 0 ? SchemaSymbols.ATTVAL_OPTIONAL : SchemaSymbols.ATTVAL_PROHIBITED, "derivation-ok-restriction.2.1.1"};
                }
                if (xSAttributeUseImpl.fUse == 2) {
                    continue;
                } else {
                    XSAttributeDecl xSAttributeDecl2 = xSAttributeUseImpl2.fAttrDecl;
                    if (!XSConstraints.checkSimpleDerivationOk(xSAttributeDecl.fType, xSAttributeDecl2.fType, xSAttributeDecl2.fType.getFinal())) {
                        return new Object[]{str, xSAttributeDecl.fName, xSAttributeDecl.fType.getName(), xSAttributeDecl2.fType.getName(), "derivation-ok-restriction.2.1.2"};
                    }
                    short constraintType = xSAttributeUseImpl2.fConstraintType != 0 ? xSAttributeUseImpl2.fConstraintType : xSAttributeDecl2.getConstraintType();
                    short constraintType2 = xSAttributeUseImpl.fConstraintType != 0 ? xSAttributeUseImpl.fConstraintType : xSAttributeDecl.getConstraintType();
                    if (constraintType != 2) {
                        continue;
                    } else {
                        if (constraintType2 != 2) {
                            return new Object[]{str, xSAttributeDecl.fName, "derivation-ok-restriction.2.1.3.a"};
                        }
                        ValidatedInfo validatedInfo = xSAttributeUseImpl2.fDefault != null ? xSAttributeUseImpl2.fDefault : xSAttributeDecl2.fDefault;
                        ValidatedInfo validatedInfo2 = xSAttributeUseImpl.fDefault != null ? xSAttributeUseImpl.fDefault : xSAttributeDecl.fDefault;
                        if (!validatedInfo.actualValue.equals(validatedInfo2.actualValue)) {
                            return new Object[]{str, xSAttributeDecl.fName, validatedInfo2.stringValue(), validatedInfo.stringValue(), "derivation-ok-restriction.2.1.3.b"};
                        }
                    }
                }
            }
        }
        for (int i2 = 0; i2 < xSAttributeGroupDecl.fAttrUseNum; i2++) {
            XSAttributeUseImpl xSAttributeUseImpl3 = xSAttributeGroupDecl.fAttributeUses[i2];
            if (xSAttributeUseImpl3.fUse == 1) {
                XSAttributeDecl xSAttributeDecl3 = xSAttributeUseImpl3.fAttrDecl;
                if (getAttributeUse(xSAttributeDecl3.fTargetNamespace, xSAttributeDecl3.fName) == null) {
                    return new Object[]{str, xSAttributeUseImpl3.fAttrDecl.fName, "derivation-ok-restriction.3"};
                }
            }
        }
        XSWildcardDecl xSWildcardDecl2 = this.fAttributeWC;
        if (xSWildcardDecl2 == null) {
            return null;
        }
        XSWildcardDecl xSWildcardDecl3 = xSAttributeGroupDecl.fAttributeWC;
        if (xSWildcardDecl3 == null) {
            return new Object[]{str, "derivation-ok-restriction.4.1"};
        }
        if (!xSWildcardDecl2.isSubsetOf(xSWildcardDecl3)) {
            return new Object[]{str, "derivation-ok-restriction.4.2"};
        }
        if (this.fAttributeWC.weakerProcessContents(xSAttributeGroupDecl.fAttributeWC)) {
            return new Object[]{str, this.fAttributeWC.getProcessContentsAsString(), xSAttributeGroupDecl.fAttributeWC.getProcessContentsAsString(), "derivation-ok-restriction.4.3"};
        }
        return null;
    }
}
