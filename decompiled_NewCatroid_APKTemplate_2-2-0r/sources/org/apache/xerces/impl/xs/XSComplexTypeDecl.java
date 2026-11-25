package org.apache.xerces.impl.xs;

import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl;
import org.apache.xerces.impl.xs.models.CMBuilder;
import org.apache.xerces.impl.xs.models.XSCMValidator;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.xs.XSAttributeUse;
import org.apache.xerces.xs.XSComplexTypeDefinition;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSParticle;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;
import org.apache.xerces.xs.XSWildcard;
import org.w3c.dom.TypeInfo;

/* JADX INFO: loaded from: classes13.dex */
public class XSComplexTypeDecl implements XSComplexTypeDefinition, TypeInfo {
    private static final short CT_HAS_TYPE_ID = 2;
    private static final short CT_IS_ABSTRACT = 1;
    private static final short CT_IS_ANONYMOUS = 4;
    static final int DERIVATION_ANY = 0;
    static final int DERIVATION_EXTENSION = 2;
    static final int DERIVATION_LIST = 8;
    static final int DERIVATION_RESTRICTION = 1;
    static final int DERIVATION_UNION = 4;
    String fName = null;
    String fTargetNamespace = null;
    XSTypeDefinition fBaseType = null;
    short fDerivedBy = 2;
    short fFinal = 0;
    short fBlock = 0;
    short fMiscFlags = 0;
    XSAttributeGroupDecl fAttrGrp = null;
    short fContentType = 0;
    XSSimpleType fXSSimpleType = null;
    XSParticleDecl fParticle = null;
    XSCMValidator fCMValidator = null;
    XSCMValidator fUPACMValidator = null;
    XSObjectListImpl fAnnotations = null;
    private XSNamespaceItem fNamespaceItem = null;

    private boolean isDerivedByAny(String str, String str2, int i, XSTypeDefinition xSTypeDefinition) {
        XSTypeDefinition xSTypeDefinition2 = null;
        while (xSTypeDefinition != null && xSTypeDefinition != xSTypeDefinition2) {
            if (str2.equals(xSTypeDefinition.getName())) {
                if (str == null && xSTypeDefinition.getNamespace() == null) {
                    return true;
                }
                if (str != null && str.equals(xSTypeDefinition.getNamespace())) {
                    return true;
                }
            }
            if (isDerivedByRestriction(str, str2, i, xSTypeDefinition) || !isDerivedByExtension(str, str2, i, xSTypeDefinition)) {
                return true;
            }
            xSTypeDefinition2 = xSTypeDefinition;
            xSTypeDefinition = xSTypeDefinition.getBaseType();
        }
        return false;
    }

    private boolean isDerivedByExtension(String str, String str2, int i, XSTypeDefinition xSTypeDefinition) {
        XSTypeDefinition xSTypeDefinition2 = null;
        boolean z = false;
        while (xSTypeDefinition != null && xSTypeDefinition != xSTypeDefinition2 && (str == null || !str.equals(SchemaSymbols.URI_SCHEMAFORSCHEMA) || !str2.equals(SchemaSymbols.ATTVAL_ANYSIMPLETYPE) || !SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(xSTypeDefinition.getNamespace()) || !SchemaSymbols.ATTVAL_ANYTYPE.equals(xSTypeDefinition.getName()))) {
            if (str2.equals(xSTypeDefinition.getName()) && ((str == null && xSTypeDefinition.getNamespace() == null) || (str != null && str.equals(xSTypeDefinition.getNamespace())))) {
                return z;
            }
            if (xSTypeDefinition instanceof XSSimpleTypeDecl) {
                if (str.equals(SchemaSymbols.URI_SCHEMAFORSCHEMA) && str2.equals(SchemaSymbols.ATTVAL_ANYTYPE)) {
                    str2 = SchemaSymbols.ATTVAL_ANYSIMPLETYPE;
                }
                XSSimpleTypeDecl xSSimpleTypeDecl = (XSSimpleTypeDecl) xSTypeDefinition;
                if ((i & 2) != 0) {
                    i &= 1;
                }
                return xSSimpleTypeDecl.isDOMDerivedFrom(str, str2, i) & z;
            }
            if (((XSComplexTypeDecl) xSTypeDefinition).getDerivationMethod() == 1) {
                z = true;
            }
            xSTypeDefinition2 = xSTypeDefinition;
            xSTypeDefinition = xSTypeDefinition.getBaseType();
        }
        return false;
    }

    private boolean isDerivedByRestriction(String str, String str2, int i, XSTypeDefinition xSTypeDefinition) {
        XSTypeDefinition xSTypeDefinition2 = null;
        while (xSTypeDefinition != null && xSTypeDefinition != xSTypeDefinition2) {
            if (str != null && str.equals(SchemaSymbols.URI_SCHEMAFORSCHEMA) && str2.equals(SchemaSymbols.ATTVAL_ANYSIMPLETYPE)) {
                return false;
            }
            if (str2.equals(xSTypeDefinition.getName()) && str != null && str.equals(xSTypeDefinition.getNamespace())) {
                return true;
            }
            if (xSTypeDefinition.getNamespace() == null && str == null) {
                return true;
            }
            if (xSTypeDefinition instanceof XSSimpleTypeDecl) {
                if (str.equals(SchemaSymbols.URI_SCHEMAFORSCHEMA) && str2.equals(SchemaSymbols.ATTVAL_ANYTYPE)) {
                    str2 = SchemaSymbols.ATTVAL_ANYSIMPLETYPE;
                }
                return ((XSSimpleTypeDecl) xSTypeDefinition).isDOMDerivedFrom(str, str2, i);
            }
            if (((XSComplexTypeDecl) xSTypeDefinition).getDerivationMethod() != 2) {
                return false;
            }
            xSTypeDefinition2 = xSTypeDefinition;
            xSTypeDefinition = xSTypeDefinition.getBaseType();
        }
        return false;
    }

    void appendTypeInfo(StringBuffer stringBuffer) {
        String[] strArr = {"EMPTY", "SIMPLE", "ELEMENT", "MIXED"};
        String[] strArr2 = {"EMPTY", "EXTENSION", "RESTRICTION"};
        stringBuffer.append("Complex type name='").append(this.fTargetNamespace).append(',').append(getTypeName()).append("', ");
        if (this.fBaseType != null) {
            stringBuffer.append(" base type name='").append(this.fBaseType.getName()).append("', ");
        }
        stringBuffer.append(" content type='").append(strArr[this.fContentType]).append("', ");
        stringBuffer.append(" isAbstract='").append(getAbstract()).append("', ");
        stringBuffer.append(" hasTypeId='").append(containsTypeID()).append("', ");
        stringBuffer.append(" final='").append((int) this.fFinal).append("', ");
        stringBuffer.append(" block='").append((int) this.fBlock).append("', ");
        if (this.fParticle != null) {
            stringBuffer.append(" particle='").append(this.fParticle.toString()).append("', ");
        }
        stringBuffer.append(" derivedBy='").append(strArr2[this.fDerivedBy]).append("'. ");
    }

    public boolean containsTypeID() {
        return (this.fMiscFlags & 2) != 0;
    }

    @Override // org.apache.xerces.xs.XSTypeDefinition
    public boolean derivedFrom(String str, String str2, short s) {
        if (str2 == null) {
            return false;
        }
        if (str != null && str.equals(SchemaSymbols.URI_SCHEMAFORSCHEMA) && str2.equals(SchemaSymbols.ATTVAL_ANYTYPE)) {
            return true;
        }
        XSTypeDefinition baseType = this;
        while (true) {
            if ((str2.equals(baseType.getName()) && ((str == null && baseType.getNamespace() == null) || (str != null && str.equals(baseType.getNamespace())))) || baseType == SchemaGrammar.fAnySimpleType || baseType == SchemaGrammar.fAnyType) {
                break;
            }
            baseType = baseType.getBaseType();
        }
        return (baseType == SchemaGrammar.fAnySimpleType || baseType == SchemaGrammar.fAnyType) ? false : true;
    }

    @Override // org.apache.xerces.xs.XSTypeDefinition
    public boolean derivedFromType(XSTypeDefinition xSTypeDefinition, short s) {
        if (xSTypeDefinition == null) {
            return false;
        }
        if (xSTypeDefinition == SchemaGrammar.fAnyType) {
            return true;
        }
        XSTypeDefinition baseType = this;
        while (baseType != xSTypeDefinition && baseType != SchemaGrammar.fAnySimpleType && baseType != SchemaGrammar.fAnyType) {
            baseType = baseType.getBaseType();
        }
        return baseType == xSTypeDefinition;
    }

    @Override // org.apache.xerces.xs.XSComplexTypeDefinition
    public boolean getAbstract() {
        return (this.fMiscFlags & 1) != 0;
    }

    @Override // org.apache.xerces.xs.XSComplexTypeDefinition
    public XSObjectList getAnnotations() {
        XSObjectListImpl xSObjectListImpl = this.fAnnotations;
        return xSObjectListImpl != null ? xSObjectListImpl : XSObjectListImpl.EMPTY_LIST;
    }

    @Override // org.apache.xerces.xs.XSTypeDefinition
    public boolean getAnonymous() {
        return (this.fMiscFlags & 4) != 0;
    }

    public XSAttributeGroupDecl getAttrGrp() {
        return this.fAttrGrp;
    }

    public XSAttributeUse getAttributeUse(String str, String str2) {
        return this.fAttrGrp.getAttributeUse(str, str2);
    }

    @Override // org.apache.xerces.xs.XSComplexTypeDefinition
    public XSObjectList getAttributeUses() {
        return this.fAttrGrp.getAttributeUses();
    }

    @Override // org.apache.xerces.xs.XSComplexTypeDefinition
    public XSWildcard getAttributeWildcard() {
        return this.fAttrGrp.getAttributeWildcard();
    }

    @Override // org.apache.xerces.xs.XSTypeDefinition
    public XSTypeDefinition getBaseType() {
        return this.fBaseType;
    }

    public XSCMValidator getContentModel(CMBuilder cMBuilder) {
        return getContentModel(cMBuilder, false);
    }

    public synchronized XSCMValidator getContentModel(CMBuilder cMBuilder, boolean z) {
        if (this.fCMValidator == null) {
            if (z) {
                if (this.fUPACMValidator == null) {
                    XSCMValidator contentModel = cMBuilder.getContentModel(this, true);
                    this.fUPACMValidator = contentModel;
                    if (contentModel != null && !contentModel.isCompactedForUPA()) {
                        this.fCMValidator = this.fUPACMValidator;
                    }
                }
                return this.fUPACMValidator;
            }
            this.fCMValidator = cMBuilder.getContentModel(this, false);
        }
        return this.fCMValidator;
    }

    @Override // org.apache.xerces.xs.XSComplexTypeDefinition
    public short getContentType() {
        return this.fContentType;
    }

    @Override // org.apache.xerces.xs.XSComplexTypeDefinition
    public short getDerivationMethod() {
        return this.fDerivedBy;
    }

    @Override // org.apache.xerces.xs.XSTypeDefinition
    public short getFinal() {
        return this.fFinal;
    }

    public short getFinalSet() {
        return this.fFinal;
    }

    @Override // org.apache.xerces.xs.XSObject
    public String getName() {
        if (getAnonymous()) {
            return null;
        }
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

    @Override // org.apache.xerces.xs.XSComplexTypeDefinition
    public XSParticle getParticle() {
        return this.fParticle;
    }

    @Override // org.apache.xerces.xs.XSComplexTypeDefinition
    public short getProhibitedSubstitutions() {
        return this.fBlock;
    }

    @Override // org.apache.xerces.xs.XSComplexTypeDefinition
    public XSSimpleTypeDefinition getSimpleType() {
        return this.fXSSimpleType;
    }

    public String getTargetNamespace() {
        return this.fTargetNamespace;
    }

    @Override // org.apache.xerces.xs.XSObject
    public short getType() {
        return (short) 3;
    }

    @Override // org.apache.xerces.xs.XSTypeDefinition
    public short getTypeCategory() {
        return (short) 15;
    }

    @Override // org.w3c.dom.TypeInfo
    public String getTypeName() {
        return this.fName;
    }

    @Override // org.w3c.dom.TypeInfo
    public String getTypeNamespace() {
        return getNamespace();
    }

    public boolean isDOMDerivedFrom(String str, String str2, int i) {
        if (str2 == null) {
            return false;
        }
        if (str != null && str.equals(SchemaSymbols.URI_SCHEMAFORSCHEMA) && str2.equals(SchemaSymbols.ATTVAL_ANYTYPE) && i == 1 && i == 2) {
            return true;
        }
        int i2 = i & 1;
        if (i2 != 0 && isDerivedByRestriction(str, str2, i, this)) {
            return true;
        }
        int i3 = i & 2;
        if (i3 != 0 && isDerivedByExtension(str, str2, i, this)) {
            return true;
        }
        int i4 = i & 8;
        if ((i4 != 0 || (i & 4) != 0) && i2 == 0 && i3 == 0) {
            if (str.equals(SchemaSymbols.URI_SCHEMAFORSCHEMA) && str2.equals(SchemaSymbols.ATTVAL_ANYTYPE)) {
                str2 = SchemaSymbols.ATTVAL_ANYSIMPLETYPE;
            }
            if (!this.fName.equals(SchemaSymbols.ATTVAL_ANYTYPE) || !this.fTargetNamespace.equals(SchemaSymbols.URI_SCHEMAFORSCHEMA)) {
                XSTypeDefinition xSTypeDefinition = this.fBaseType;
                if (xSTypeDefinition != null && (xSTypeDefinition instanceof XSSimpleTypeDecl)) {
                    return ((XSSimpleTypeDecl) xSTypeDefinition).isDOMDerivedFrom(str, str2, i);
                }
                if (xSTypeDefinition != null && (xSTypeDefinition instanceof XSComplexTypeDecl)) {
                    return ((XSComplexTypeDecl) xSTypeDefinition).isDOMDerivedFrom(str, str2, i);
                }
            }
        }
        if (i3 == 0 && i2 == 0 && i4 == 0 && (i & 4) == 0) {
            return isDerivedByAny(str, str2, i, this);
        }
        return false;
    }

    @Override // org.w3c.dom.TypeInfo
    public boolean isDerivedFrom(String str, String str2, int i) {
        return isDOMDerivedFrom(str, str2, i);
    }

    @Override // org.apache.xerces.xs.XSTypeDefinition
    public boolean isFinal(short s) {
        return (s & this.fFinal) != 0;
    }

    @Override // org.apache.xerces.xs.XSComplexTypeDefinition
    public boolean isProhibitedSubstitution(short s) {
        return (s & this.fBlock) != 0;
    }

    public void reset() {
        this.fName = null;
        this.fTargetNamespace = null;
        this.fBaseType = null;
        this.fDerivedBy = (short) 2;
        this.fFinal = (short) 0;
        this.fBlock = (short) 0;
        this.fMiscFlags = (short) 0;
        this.fAttrGrp.reset();
        this.fContentType = (short) 0;
        this.fXSSimpleType = null;
        this.fParticle = null;
        this.fCMValidator = null;
        this.fUPACMValidator = null;
        XSObjectListImpl xSObjectListImpl = this.fAnnotations;
        if (xSObjectListImpl != null) {
            xSObjectListImpl.clearXSObjectList();
        }
        this.fAnnotations = null;
    }

    public void setContainsTypeID() {
        this.fMiscFlags = (short) (this.fMiscFlags | 2);
    }

    public void setIsAbstractType() {
        this.fMiscFlags = (short) (this.fMiscFlags | 1);
    }

    public void setIsAnonymous() {
        this.fMiscFlags = (short) (this.fMiscFlags | 4);
    }

    public void setName(String str) {
        this.fName = str;
    }

    void setNamespaceItem(XSNamespaceItem xSNamespaceItem) {
        this.fNamespaceItem = xSNamespaceItem;
    }

    public void setValues(String str, String str2, XSTypeDefinition xSTypeDefinition, short s, short s2, short s3, short s4, boolean z, XSAttributeGroupDecl xSAttributeGroupDecl, XSSimpleType xSSimpleType, XSParticleDecl xSParticleDecl, XSObjectListImpl xSObjectListImpl) {
        this.fTargetNamespace = str2;
        this.fBaseType = xSTypeDefinition;
        this.fDerivedBy = s;
        this.fFinal = s2;
        this.fBlock = s3;
        this.fContentType = s4;
        if (z) {
            this.fMiscFlags = (short) (this.fMiscFlags | 1);
        }
        this.fAttrGrp = xSAttributeGroupDecl;
        this.fXSSimpleType = xSSimpleType;
        this.fParticle = xSParticleDecl;
        this.fAnnotations = xSObjectListImpl;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        appendTypeInfo(stringBuffer);
        return stringBuffer.toString();
    }
}
