package org.apache.xerces.xpointer;

import org.apache.xerces.impl.Constants;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xs.AttributePSVI;
import org.apache.xerces.xs.XSTypeDefinition;

/* JADX INFO: loaded from: classes13.dex */
final class ShortHandPointer implements XPointerPart {
    private boolean fIsFragmentResolved = false;
    int fMatchingChildCount = 0;
    private String fShortHandPointer;
    private SymbolTable fSymbolTable;

    public ShortHandPointer() {
    }

    public ShortHandPointer(SymbolTable symbolTable) {
        this.fSymbolTable = symbolTable;
    }

    private boolean hasMatchingIdentifier(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations, int i) throws XNIException {
        String schemaDeterminedID = null;
        if (xMLAttributes != null) {
            for (int i2 = 0; i2 < xMLAttributes.getLength() && (schemaDeterminedID = getSchemaDeterminedID(xMLAttributes, i2)) == null && (schemaDeterminedID = getChildrenSchemaDeterminedID(xMLAttributes, i2)) == null && (schemaDeterminedID = getDTDDeterminedID(xMLAttributes, i2)) == null; i2++) {
            }
        }
        return schemaDeterminedID != null && schemaDeterminedID.equals(this.fShortHandPointer);
    }

    public String getChildrenSchemaDeterminedID(XMLAttributes xMLAttributes, int i) throws XNIException {
        return null;
    }

    public String getDTDDeterminedID(XMLAttributes xMLAttributes, int i) throws XNIException {
        if (xMLAttributes.getType(i).equals(SchemaSymbols.ATTVAL_ID)) {
            return xMLAttributes.getValue(i);
        }
        return null;
    }

    public String getSchemaDeterminedID(XMLAttributes xMLAttributes, int i) throws XNIException {
        AttributePSVI attributePSVI = (AttributePSVI) xMLAttributes.getAugmentations(i).getItem(Constants.ATTRIBUTE_PSVI);
        if (attributePSVI == null) {
            return null;
        }
        XSTypeDefinition memberTypeDefinition = attributePSVI.getMemberTypeDefinition();
        if (memberTypeDefinition != null) {
            memberTypeDefinition = attributePSVI.getTypeDefinition();
        }
        if (memberTypeDefinition == null || !((XSSimpleType) memberTypeDefinition).isIDType()) {
            return null;
        }
        return attributePSVI.getSchemaNormalizedValue();
    }

    @Override // org.apache.xerces.xpointer.XPointerPart
    public String getSchemeData() {
        return null;
    }

    @Override // org.apache.xerces.xpointer.XPointerPart
    public String getSchemeName() {
        return this.fShortHandPointer;
    }

    @Override // org.apache.xerces.xpointer.XPointerPart
    public boolean isChildFragmentResolved() {
        return this.fIsFragmentResolved && this.fMatchingChildCount > 0;
    }

    @Override // org.apache.xerces.xpointer.XPointerPart
    public boolean isFragmentResolved() {
        return this.fIsFragmentResolved;
    }

    @Override // org.apache.xerces.xpointer.XPointerPart
    public void parseXPointer(String str) throws XNIException {
        this.fShortHandPointer = str;
        this.fIsFragmentResolved = false;
    }

    @Override // org.apache.xerces.xpointer.XPointerPart
    public boolean resolveXPointer(QName qName, XMLAttributes xMLAttributes, Augmentations augmentations, int i) throws XNIException {
        int i2 = this.fMatchingChildCount;
        if (i2 == 0) {
            this.fIsFragmentResolved = false;
        }
        if (i == 0) {
            if (i2 == 0) {
                this.fIsFragmentResolved = hasMatchingIdentifier(qName, xMLAttributes, augmentations, i);
            }
            if (this.fIsFragmentResolved) {
                this.fMatchingChildCount++;
            }
        } else if (i == 2) {
            if (i2 == 0) {
                this.fIsFragmentResolved = hasMatchingIdentifier(qName, xMLAttributes, augmentations, i);
            }
        } else if (this.fIsFragmentResolved) {
            this.fMatchingChildCount = i2 - 1;
        }
        return this.fIsFragmentResolved;
    }

    @Override // org.apache.xerces.xpointer.XPointerPart
    public void setSchemeData(String str) {
    }

    @Override // org.apache.xerces.xpointer.XPointerPart
    public void setSchemeName(String str) {
        this.fShortHandPointer = str;
    }
}
