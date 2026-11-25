package org.apache.xerces.impl.xs.traversers;

import com.reandroid.apk.ApkUtil;
import org.apache.xerces.impl.dv.InvalidDatatypeFacetException;
import org.apache.xerces.impl.dv.XSFacets;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl;
import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAnnotationImpl;
import org.apache.xerces.impl.xs.XSAttributeGroupDecl;
import org.apache.xerces.impl.xs.XSAttributeUseImpl;
import org.apache.xerces.impl.xs.XSComplexTypeDecl;
import org.apache.xerces.impl.xs.XSConstraints;
import org.apache.xerces.impl.xs.XSModelGroupImpl;
import org.apache.xerces.impl.xs.XSParticleDecl;
import org.apache.xerces.impl.xs.XSWildcardDecl;
import org.apache.xerces.impl.xs.util.XInt;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xs.XSAttributeUse;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes13.dex */
class XSDComplexTypeTraverser extends XSDAbstractParticleTraverser {
    private static final boolean DEBUG = false;
    private static final int GLOBAL_NUM = 11;
    private static XSParticleDecl fErrorContent;
    private static XSWildcardDecl fErrorWildcard;
    private XSAnnotationImpl[] fAnnotations;
    private XSAttributeGroupDecl fAttrGrp;
    private XSTypeDefinition fBaseType;
    private short fBlock;
    private XSComplexTypeDecl fComplexTypeDecl;
    private short fContentType;
    private short fDerivedBy;
    private short fFinal;
    private Object[] fGlobalStore;
    private int fGlobalStorePos;
    private boolean fIsAbstract;
    private String fName;
    private XSParticleDecl fParticle;
    private String fTargetNamespace;
    private XSSimpleType fXSSimpleType;

    private static final class ComplexTypeRecoverableError extends Exception {
        private static final long serialVersionUID = 6802729912091130335L;
        Element errorElem;
        Object[] errorSubstText;

        ComplexTypeRecoverableError() {
            this.errorSubstText = null;
            this.errorElem = null;
        }

        ComplexTypeRecoverableError(String str, Object[] objArr, Element element) {
            super(str);
            this.errorSubstText = objArr;
            this.errorElem = element;
        }
    }

    XSDComplexTypeTraverser(XSDHandler xSDHandler, XSAttributeChecker xSAttributeChecker) {
        super(xSDHandler, xSAttributeChecker);
        this.fName = null;
        this.fTargetNamespace = null;
        this.fDerivedBy = (short) 2;
        this.fFinal = (short) 0;
        this.fBlock = (short) 0;
        this.fContentType = (short) 0;
        this.fBaseType = null;
        this.fAttrGrp = null;
        this.fXSSimpleType = null;
        this.fParticle = null;
        this.fIsAbstract = false;
        this.fComplexTypeDecl = null;
        this.fAnnotations = null;
        this.fGlobalStore = null;
        this.fGlobalStorePos = 0;
    }

    private void addAnnotation(XSAnnotationImpl xSAnnotationImpl) {
        if (xSAnnotationImpl == null) {
            return;
        }
        XSAnnotationImpl[] xSAnnotationImplArr = this.fAnnotations;
        if (xSAnnotationImplArr == null) {
            this.fAnnotations = new XSAnnotationImpl[1];
        } else {
            XSAnnotationImpl[] xSAnnotationImplArr2 = new XSAnnotationImpl[xSAnnotationImplArr.length + 1];
            System.arraycopy(xSAnnotationImplArr, 0, xSAnnotationImplArr2, 0, xSAnnotationImplArr.length);
            this.fAnnotations = xSAnnotationImplArr2;
        }
        XSAnnotationImpl[] xSAnnotationImplArr3 = this.fAnnotations;
        xSAnnotationImplArr3[xSAnnotationImplArr3.length - 1] = xSAnnotationImpl;
    }

    private void contentBackup() {
        if (this.fGlobalStore == null) {
            this.fGlobalStore = new Object[11];
            this.fGlobalStorePos = 0;
        }
        int i = this.fGlobalStorePos;
        Object[] objArr = this.fGlobalStore;
        if (i == objArr.length) {
            Object[] objArr2 = new Object[i + 11];
            System.arraycopy(objArr, 0, objArr2, 0, i);
            this.fGlobalStore = objArr2;
        }
        Object[] objArr3 = this.fGlobalStore;
        int i2 = this.fGlobalStorePos;
        int i3 = i2 + 1;
        this.fGlobalStorePos = i3;
        objArr3[i2] = this.fComplexTypeDecl;
        this.fGlobalStorePos = i2 + 2;
        objArr3[i3] = this.fIsAbstract ? Boolean.TRUE : Boolean.FALSE;
        Object[] objArr4 = this.fGlobalStore;
        int i4 = this.fGlobalStorePos;
        int i5 = i4 + 1;
        this.fGlobalStorePos = i5;
        objArr4[i4] = this.fName;
        int i6 = i4 + 2;
        this.fGlobalStorePos = i6;
        objArr4[i5] = this.fTargetNamespace;
        this.fGlobalStorePos = i4 + 3;
        objArr4[i6] = new Integer((this.fDerivedBy << 16) + this.fFinal);
        Object[] objArr5 = this.fGlobalStore;
        int i7 = this.fGlobalStorePos;
        this.fGlobalStorePos = i7 + 1;
        objArr5[i7] = new Integer((this.fBlock << 16) + this.fContentType);
        Object[] objArr6 = this.fGlobalStore;
        int i8 = this.fGlobalStorePos;
        int i9 = i8 + 1;
        this.fGlobalStorePos = i9;
        objArr6[i8] = this.fBaseType;
        int i10 = i8 + 2;
        this.fGlobalStorePos = i10;
        objArr6[i9] = this.fAttrGrp;
        int i11 = i8 + 3;
        this.fGlobalStorePos = i11;
        objArr6[i10] = this.fParticle;
        int i12 = i8 + 4;
        this.fGlobalStorePos = i12;
        objArr6[i11] = this.fXSSimpleType;
        this.fGlobalStorePos = i8 + 5;
        objArr6[i12] = this.fAnnotations;
    }

    private void contentRestore() {
        Object[] objArr = this.fGlobalStore;
        int i = this.fGlobalStorePos;
        int i2 = i - 1;
        this.fGlobalStorePos = i2;
        this.fAnnotations = (XSAnnotationImpl[]) objArr[i2];
        int i3 = i - 2;
        this.fGlobalStorePos = i3;
        this.fXSSimpleType = (XSSimpleType) objArr[i3];
        int i4 = i - 3;
        this.fGlobalStorePos = i4;
        this.fParticle = (XSParticleDecl) objArr[i4];
        int i5 = i - 4;
        this.fGlobalStorePos = i5;
        this.fAttrGrp = (XSAttributeGroupDecl) objArr[i5];
        int i6 = i - 5;
        this.fGlobalStorePos = i6;
        this.fBaseType = (XSTypeDefinition) objArr[i6];
        int i7 = i - 6;
        this.fGlobalStorePos = i7;
        int iIntValue = ((Integer) objArr[i7]).intValue();
        this.fBlock = (short) (iIntValue >> 16);
        this.fContentType = (short) iIntValue;
        Object[] objArr2 = this.fGlobalStore;
        int i8 = this.fGlobalStorePos - 1;
        this.fGlobalStorePos = i8;
        int iIntValue2 = ((Integer) objArr2[i8]).intValue();
        this.fDerivedBy = (short) (iIntValue2 >> 16);
        this.fFinal = (short) iIntValue2;
        Object[] objArr3 = this.fGlobalStore;
        int i9 = this.fGlobalStorePos;
        int i10 = i9 - 1;
        this.fGlobalStorePos = i10;
        this.fTargetNamespace = (String) objArr3[i10];
        int i11 = i9 - 2;
        this.fGlobalStorePos = i11;
        this.fName = (String) objArr3[i11];
        int i12 = i9 - 3;
        this.fGlobalStorePos = i12;
        this.fIsAbstract = ((Boolean) objArr3[i12]).booleanValue();
        Object[] objArr4 = this.fGlobalStore;
        int i13 = this.fGlobalStorePos - 1;
        this.fGlobalStorePos = i13;
        this.fComplexTypeDecl = (XSComplexTypeDecl) objArr4[i13];
    }

    private String genAnonTypeName(Element element) {
        StringBuffer stringBuffer = new StringBuffer("#AnonType_");
        while (true) {
            element = DOMUtil.getParent(element);
            if (element == null || element == DOMUtil.getRoot(DOMUtil.getDocument(element))) {
                break;
            }
            stringBuffer.append(element.getAttribute(SchemaSymbols.ATT_NAME));
        }
        return stringBuffer.toString();
    }

    private static XSParticleDecl getErrorContent() {
        if (fErrorContent == null) {
            XSParticleDecl xSParticleDecl = new XSParticleDecl();
            xSParticleDecl.fType = (short) 2;
            xSParticleDecl.fValue = getErrorWildcard();
            xSParticleDecl.fMinOccurs = 0;
            xSParticleDecl.fMaxOccurs = -1;
            XSModelGroupImpl xSModelGroupImpl = new XSModelGroupImpl();
            xSModelGroupImpl.fCompositor = (short) 102;
            xSModelGroupImpl.fParticleCount = 1;
            xSModelGroupImpl.fParticles = new XSParticleDecl[1];
            xSModelGroupImpl.fParticles[0] = xSParticleDecl;
            XSParticleDecl xSParticleDecl2 = new XSParticleDecl();
            xSParticleDecl2.fType = (short) 3;
            xSParticleDecl2.fValue = xSModelGroupImpl;
            fErrorContent = xSParticleDecl2;
        }
        return fErrorContent;
    }

    private static XSWildcardDecl getErrorWildcard() {
        if (fErrorWildcard == null) {
            XSWildcardDecl xSWildcardDecl = new XSWildcardDecl();
            xSWildcardDecl.fProcessContents = (short) 2;
            fErrorWildcard = xSWildcardDecl;
        }
        return fErrorWildcard;
    }

    private void handleComplexTypeError(String str, Object[] objArr, Element element) {
        if (str != null) {
            reportSchemaError(str, objArr, element);
        }
        this.fBaseType = SchemaGrammar.fAnyType;
        this.fContentType = (short) 3;
        this.fXSSimpleType = null;
        this.fParticle = getErrorContent();
        this.fAttrGrp.fAttributeWC = getErrorWildcard();
    }

    private boolean isAttrOrAttrGroup(Element element) {
        String localName = DOMUtil.getLocalName(element);
        return localName.equals(SchemaSymbols.ELT_ATTRIBUTE) || localName.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP) || localName.equals(SchemaSymbols.ELT_ANYATTRIBUTE);
    }

    private void mergeAttributes(XSAttributeGroupDecl xSAttributeGroupDecl, XSAttributeGroupDecl xSAttributeGroupDecl2, String str, boolean z, Element element) throws ComplexTypeRecoverableError {
        XSObjectList attributeUses = xSAttributeGroupDecl.getAttributeUses();
        int length = attributeUses.getLength();
        for (int i = 0; i < length; i++) {
            XSAttributeUseImpl xSAttributeUseImpl = (XSAttributeUseImpl) attributeUses.item(i);
            XSAttributeUse attributeUse = xSAttributeGroupDecl2.getAttributeUse(xSAttributeUseImpl.fAttrDecl.getNamespace(), xSAttributeUseImpl.fAttrDecl.getName());
            if (attributeUse == null) {
                String strAddAttributeUse = xSAttributeGroupDecl2.addAttributeUse(xSAttributeUseImpl);
                if (strAddAttributeUse != null) {
                    throw new ComplexTypeRecoverableError("ct-props-correct.5", new Object[]{str, strAddAttributeUse, xSAttributeUseImpl.fAttrDecl.getName()}, element);
                }
            } else if (attributeUse != xSAttributeUseImpl && z) {
                reportSchemaError("ct-props-correct.4", new Object[]{str, xSAttributeUseImpl.fAttrDecl.getName()}, element);
                xSAttributeGroupDecl2.replaceAttributeUse(attributeUse, xSAttributeUseImpl);
            }
        }
        if (z) {
            if (xSAttributeGroupDecl2.fAttributeWC == null) {
                xSAttributeGroupDecl2.fAttributeWC = xSAttributeGroupDecl.fAttributeWC;
            } else if (xSAttributeGroupDecl.fAttributeWC != null) {
                xSAttributeGroupDecl2.fAttributeWC = xSAttributeGroupDecl2.fAttributeWC.performUnionWith(xSAttributeGroupDecl.fAttributeWC, xSAttributeGroupDecl2.fAttributeWC.fProcessContents);
                if (xSAttributeGroupDecl2.fAttributeWC == null) {
                    throw new ComplexTypeRecoverableError("src-ct.5", new Object[]{str}, element);
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0049 A[PHI: r0
      0x0049: PHI (r0v18 org.apache.xerces.impl.xs.XSParticleDecl) = 
      (r0v15 org.apache.xerces.impl.xs.XSParticleDecl)
      (r0v15 org.apache.xerces.impl.xs.XSParticleDecl)
      (r0v17 org.apache.xerces.impl.xs.XSParticleDecl)
      (r0v17 org.apache.xerces.impl.xs.XSParticleDecl)
      (r0v17 org.apache.xerces.impl.xs.XSParticleDecl)
      (r0v21 org.apache.xerces.impl.xs.XSParticleDecl)
      (r0v21 org.apache.xerces.impl.xs.XSParticleDecl)
     binds: [B:28:0x0089, B:30:0x0091, B:19:0x0064, B:21:0x0068, B:23:0x0070, B:10:0x003e, B:12:0x0046] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:36:0x00a0  */
    /* JADX WARN: Code duplicated, block: B:43:0x00b9  */
    /* JADX WARN: Code duplicated, block: B:49:0x00c6  */
    /* JADX WARN: Code duplicated, block: B:50:0x00c9 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:51:0x00cb  */
    /* JADX WARN: Code duplicated, block: B:52:0x00cd  */
    /* JADX WARN: Code duplicated, block: B:55:0x00d2  */
    /* JADX WARN: Code duplicated, block: B:57:0x00da  */
    /* JADX WARN: Code duplicated, block: B:59:0x00e9 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:60:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:61:0x00f1  */
    /* JADX WARN: Code duplicated, block: B:63:0x0101  */
    /* JADX WARN: Code duplicated, block: B:65:0x0111 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Code duplicated, block: B:66:? A[RETURN, SYNTHETIC] */
    private void processComplexContent(Element element, boolean z, boolean z2, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) throws ComplexTypeRecoverableError {
        XSParticleDecl xSParticleDeclTraverseAll;
        Element nextSiblingElement;
        boolean z3;
        XSParticleDecl emptySequence;
        short s;
        Element elementTraverseAttrsAndAttrGrps;
        Element firstChildElement;
        if (element != null) {
            String localName = DOMUtil.getLocalName(element);
            if (!localName.equals(SchemaSymbols.ELT_GROUP)) {
                z3 = true;
                if (localName.equals(SchemaSymbols.ELT_SEQUENCE)) {
                    xSParticleDeclTraverseAll = traverseSequence(element, xSDocumentInfo, schemaGrammar, 0, this.fComplexTypeDecl);
                    if (xSParticleDeclTraverseAll == null || ((XSModelGroupImpl) xSParticleDeclTraverseAll.fValue).fParticleCount != 0) {
                        z3 = false;
                    }
                } else if (localName.equals(SchemaSymbols.ELT_CHOICE)) {
                    xSParticleDeclTraverseAll = traverseChoice(element, xSDocumentInfo, schemaGrammar, 0, this.fComplexTypeDecl);
                    if (xSParticleDeclTraverseAll == null || xSParticleDeclTraverseAll.fMinOccurs != 0 || ((XSModelGroupImpl) xSParticleDeclTraverseAll.fValue).fParticleCount != 0) {
                        z3 = false;
                    }
                } else if (localName.equals(SchemaSymbols.ELT_ALL)) {
                    xSParticleDeclTraverseAll = traverseAll(element, xSDocumentInfo, schemaGrammar, 8, this.fComplexTypeDecl);
                    if (xSParticleDeclTraverseAll == null || ((XSModelGroupImpl) xSParticleDeclTraverseAll.fValue).fParticleCount != 0) {
                        z3 = false;
                    }
                } else {
                    nextSiblingElement = element;
                    xSParticleDeclTraverseAll = null;
                }
                nextSiblingElement = DOMUtil.getNextSiblingElement(element);
                if (!z3) {
                    firstChildElement = DOMUtil.getFirstChildElement(element);
                    if (firstChildElement != null && DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
                        firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                    }
                    emptySequence = firstChildElement != null ? xSParticleDeclTraverseAll : null;
                }
                if (emptySequence == null && z) {
                    emptySequence = XSConstraints.getEmptySequence();
                }
                this.fParticle = emptySequence;
                if (emptySequence == null) {
                    this.fContentType = (short) 0;
                } else {
                    if (z) {
                        s = 3;
                    } else {
                        s = 2;
                    }
                    this.fContentType = s;
                }
                if (nextSiblingElement != null) {
                    if (isAttrOrAttrGroup(nextSiblingElement)) {
                        throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, DOMUtil.getLocalName(nextSiblingElement)}, nextSiblingElement);
                    }
                    elementTraverseAttrsAndAttrGrps = traverseAttrsAndAttrGrps(nextSiblingElement, this.fAttrGrp, xSDocumentInfo, schemaGrammar, this.fComplexTypeDecl);
                    if (elementTraverseAttrsAndAttrGrps == null) {
                        throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, DOMUtil.getLocalName(elementTraverseAttrsAndAttrGrps)}, elementTraverseAttrsAndAttrGrps);
                    }
                    if (z2) {
                    }
                    this.fAttrGrp.removeProhibitedAttrs();
                }
            }
            xSParticleDeclTraverseAll = this.fSchemaHandler.fGroupTraverser.traverseLocal(element, xSDocumentInfo, schemaGrammar);
            nextSiblingElement = DOMUtil.getNextSiblingElement(element);
        } else {
            xSParticleDeclTraverseAll = null;
            nextSiblingElement = null;
        }
        z3 = false;
        if (!z3) {
            firstChildElement = DOMUtil.getFirstChildElement(element);
            if (firstChildElement != null) {
                firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
            }
            if (firstChildElement != null) {
            }
        }
        if (emptySequence == null) {
            emptySequence = XSConstraints.getEmptySequence();
        }
        this.fParticle = emptySequence;
        if (emptySequence == null) {
            this.fContentType = (short) 0;
        } else {
            if (z) {
                s = 3;
            } else {
                s = 2;
            }
            this.fContentType = s;
        }
        if (nextSiblingElement != null) {
            if (isAttrOrAttrGroup(nextSiblingElement)) {
                throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, DOMUtil.getLocalName(nextSiblingElement)}, nextSiblingElement);
            }
            elementTraverseAttrsAndAttrGrps = traverseAttrsAndAttrGrps(nextSiblingElement, this.fAttrGrp, xSDocumentInfo, schemaGrammar, this.fComplexTypeDecl);
            if (elementTraverseAttrsAndAttrGrps == null) {
                throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, DOMUtil.getLocalName(elementTraverseAttrsAndAttrGrps)}, elementTraverseAttrsAndAttrGrps);
            }
            if (z2) {
                this.fAttrGrp.removeProhibitedAttrs();
            }
        }
    }

    private void traverseComplexContent(Element element, boolean z, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) throws ComplexTypeRecoverableError {
        Object[] objArrValidRestrictionOf;
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, false, xSDocumentInfo);
        Boolean bool = (Boolean) objArrCheckAttributes[XSAttributeChecker.ATTIDX_MIXED];
        boolean zBooleanValue = bool != null ? bool.booleanValue() : z;
        this.fXSSimpleType = null;
        Element firstChildElement = DOMUtil.getFirstChildElement(element);
        if (firstChildElement == null || !DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
            String syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element);
            if (syntheticAnnotation != null) {
                addAnnotation(traverseSyntheticAnnotation(element, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo));
            }
        } else {
            addAnnotation(traverseAnnotationDecl(firstChildElement, objArrCheckAttributes, false, xSDocumentInfo));
            firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
        }
        if (firstChildElement == null) {
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.2", new Object[]{this.fName, SchemaSymbols.ELT_COMPLEXCONTENT}, element);
        }
        String localName = DOMUtil.getLocalName(firstChildElement);
        if (localName.equals(SchemaSymbols.ELT_RESTRICTION)) {
            this.fDerivedBy = (short) 2;
        } else {
            if (!localName.equals(SchemaSymbols.ELT_EXTENSION)) {
                this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, localName}, firstChildElement);
            }
            this.fDerivedBy = (short) 1;
        }
        Element nextSiblingElement = DOMUtil.getNextSiblingElement(firstChildElement);
        if (nextSiblingElement != null) {
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, DOMUtil.getLocalName(nextSiblingElement)}, nextSiblingElement);
        }
        Object[] objArrCheckAttributes2 = this.fAttrChecker.checkAttributes(firstChildElement, false, xSDocumentInfo);
        QName qName = (QName) objArrCheckAttributes2[XSAttributeChecker.ATTIDX_BASE];
        if (qName == null) {
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
            throw new ComplexTypeRecoverableError("s4s-att-must-appear", new Object[]{localName, ApkUtil.DEF_MODULE_NAME}, firstChildElement);
        }
        XSTypeDefinition xSTypeDefinition = (XSTypeDefinition) this.fSchemaHandler.getGlobalDecl(xSDocumentInfo, 7, qName, firstChildElement);
        if (xSTypeDefinition == null) {
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
            throw new ComplexTypeRecoverableError();
        }
        if (!(xSTypeDefinition instanceof XSComplexTypeDecl)) {
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
            throw new ComplexTypeRecoverableError("src-ct.1", new Object[]{this.fName, xSTypeDefinition.getName()}, firstChildElement);
        }
        XSComplexTypeDecl xSComplexTypeDecl = (XSComplexTypeDecl) xSTypeDefinition;
        this.fBaseType = xSComplexTypeDecl;
        if ((xSComplexTypeDecl.getFinal() & this.fDerivedBy) != 0) {
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
            throw new ComplexTypeRecoverableError(this.fDerivedBy == 1 ? "cos-ct-extends.1.1" : "derivation-ok-restriction.1", new Object[]{this.fName, this.fBaseType.getName()}, firstChildElement);
        }
        Element firstChildElement2 = DOMUtil.getFirstChildElement(firstChildElement);
        if (firstChildElement2 != null) {
            if (DOMUtil.getLocalName(firstChildElement2).equals(SchemaSymbols.ELT_ANNOTATION)) {
                addAnnotation(traverseAnnotationDecl(firstChildElement2, objArrCheckAttributes2, false, xSDocumentInfo));
                firstChildElement2 = DOMUtil.getNextSiblingElement(firstChildElement2);
            } else {
                String syntheticAnnotation2 = DOMUtil.getSyntheticAnnotation(firstChildElement2);
                if (syntheticAnnotation2 != null) {
                    addAnnotation(traverseSyntheticAnnotation(firstChildElement2, syntheticAnnotation2, objArrCheckAttributes2, false, xSDocumentInfo));
                }
                if (firstChildElement2 != null && DOMUtil.getLocalName(firstChildElement2).equals(SchemaSymbols.ELT_ANNOTATION)) {
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                    throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, SchemaSymbols.ELT_ANNOTATION}, firstChildElement2);
                }
            }
            if (firstChildElement2 != null) {
                this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, SchemaSymbols.ELT_ANNOTATION}, firstChildElement2);
            }
        } else {
            objArrCheckAttributes2 = objArrCheckAttributes2;
            String syntheticAnnotation3 = DOMUtil.getSyntheticAnnotation(firstChildElement2);
            if (syntheticAnnotation3 != null) {
                addAnnotation(traverseSyntheticAnnotation(firstChildElement2, syntheticAnnotation3, objArrCheckAttributes2, false, xSDocumentInfo));
            }
        }
        try {
            processComplexContent(firstChildElement2, zBooleanValue, true, xSDocumentInfo, schemaGrammar);
            XSParticleDecl xSParticleDecl = (XSParticleDecl) xSComplexTypeDecl.getParticle();
            if (this.fDerivedBy != 2) {
                if (this.fParticle == null) {
                    this.fContentType = xSComplexTypeDecl.getContentType();
                    this.fXSSimpleType = (XSSimpleType) xSComplexTypeDecl.getSimpleType();
                    this.fParticle = xSParticleDecl;
                } else if (xSComplexTypeDecl.getContentType() != 0) {
                    if (this.fContentType == 2 && xSComplexTypeDecl.getContentType() != 2) {
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                        throw new ComplexTypeRecoverableError("cos-ct-extends.1.4.3.2.2.1.a", new Object[]{this.fName}, firstChildElement2);
                    }
                    if (this.fContentType == 3 && xSComplexTypeDecl.getContentType() != 3) {
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                        throw new ComplexTypeRecoverableError("cos-ct-extends.1.4.3.2.2.1.b", new Object[]{this.fName}, firstChildElement2);
                    }
                    if ((this.fParticle.fType == 3 && ((XSModelGroupImpl) this.fParticle.fValue).fCompositor == 103) || (((XSParticleDecl) xSComplexTypeDecl.getParticle()).fType == 3 && ((XSModelGroupImpl) ((XSParticleDecl) xSComplexTypeDecl.getParticle()).fValue).fCompositor == 103)) {
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                        throw new ComplexTypeRecoverableError("cos-all-limited.1.2", new Object[0], firstChildElement2);
                    }
                    XSModelGroupImpl xSModelGroupImpl = new XSModelGroupImpl();
                    xSModelGroupImpl.fCompositor = (short) 102;
                    xSModelGroupImpl.fParticleCount = 2;
                    xSModelGroupImpl.fParticles = new XSParticleDecl[2];
                    xSModelGroupImpl.fParticles[0] = (XSParticleDecl) xSComplexTypeDecl.getParticle();
                    xSModelGroupImpl.fParticles[1] = this.fParticle;
                    xSModelGroupImpl.fAnnotations = XSObjectListImpl.EMPTY_LIST;
                    XSParticleDecl xSParticleDecl2 = new XSParticleDecl();
                    xSParticleDecl2.fType = (short) 3;
                    xSParticleDecl2.fValue = xSModelGroupImpl;
                    xSParticleDecl2.fAnnotations = XSObjectListImpl.EMPTY_LIST;
                    this.fParticle = xSParticleDecl2;
                }
                this.fAttrGrp.removeProhibitedAttrs();
                try {
                    mergeAttributes(xSComplexTypeDecl.getAttrGrp(), this.fAttrGrp, this.fName, true, firstChildElement2);
                } catch (ComplexTypeRecoverableError e) {
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                    throw e;
                }
            } else {
                if (this.fContentType == 3 && xSComplexTypeDecl.getContentType() != 3) {
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                    throw new ComplexTypeRecoverableError("derivation-ok-restriction.5.4.1.2", new Object[]{this.fName, xSComplexTypeDecl.getName()}, firstChildElement2);
                }
                try {
                    mergeAttributes(xSComplexTypeDecl.getAttrGrp(), this.fAttrGrp, this.fName, false, firstChildElement2);
                    this.fAttrGrp.removeProhibitedAttrs();
                    if (xSComplexTypeDecl != SchemaGrammar.fAnyType && (objArrValidRestrictionOf = this.fAttrGrp.validRestrictionOf(this.fName, xSComplexTypeDecl.getAttrGrp())) != null) {
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                        throw new ComplexTypeRecoverableError((String) objArrValidRestrictionOf[objArrValidRestrictionOf.length - 1], objArrValidRestrictionOf, firstChildElement2);
                    }
                } catch (ComplexTypeRecoverableError e2) {
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                    throw e2;
                }
            }
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
        } catch (ComplexTypeRecoverableError e3) {
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
            throw e3;
        }
    }

    private void traverseComplexContentDecl(Element element, boolean z) {
    }

    /* JADX WARN: Code duplicated, block: B:60:0x0175  */
    private XSComplexTypeDecl traverseComplexTypeDecl(Element element, String str, Object[] objArr, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        boolean zBooleanValue;
        this.fComplexTypeDecl = new XSComplexTypeDecl();
        this.fAttrGrp = new XSAttributeGroupDecl();
        Boolean bool = (Boolean) objArr[XSAttributeChecker.ATTIDX_ABSTRACT];
        XInt xInt = (XInt) objArr[XSAttributeChecker.ATTIDX_BLOCK];
        Boolean bool2 = (Boolean) objArr[XSAttributeChecker.ATTIDX_MIXED];
        XInt xInt2 = (XInt) objArr[XSAttributeChecker.ATTIDX_FINAL];
        this.fName = str;
        this.fComplexTypeDecl.setName(str);
        this.fTargetNamespace = xSDocumentInfo.fTargetNamespace;
        this.fBlock = xInt == null ? xSDocumentInfo.fBlockDefault : xInt.shortValue();
        short sShortValue = xInt2 == null ? xSDocumentInfo.fFinalDefault : xInt2.shortValue();
        this.fBlock = (short) (this.fBlock & 3);
        this.fFinal = (short) (sShortValue & 3);
        this.fIsAbstract = bool != null && bool.booleanValue();
        this.fAnnotations = null;
        try {
            Element firstChildElement = DOMUtil.getFirstChildElement(element);
            if (firstChildElement != null) {
                if (DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
                    addAnnotation(traverseAnnotationDecl(firstChildElement, objArr, false, xSDocumentInfo));
                    firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                } else {
                    String syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element);
                    if (syntheticAnnotation != null) {
                        addAnnotation(traverseSyntheticAnnotation(element, syntheticAnnotation, objArr, false, xSDocumentInfo));
                    }
                }
                if (firstChildElement != null && DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
                    throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, SchemaSymbols.ELT_ANNOTATION}, firstChildElement);
                }
            } else {
                String syntheticAnnotation2 = DOMUtil.getSyntheticAnnotation(element);
                if (syntheticAnnotation2 != null) {
                    addAnnotation(traverseSyntheticAnnotation(element, syntheticAnnotation2, objArr, false, xSDocumentInfo));
                }
            }
            Element element2 = firstChildElement;
            if (element2 != null) {
                if (DOMUtil.getLocalName(element2).equals(SchemaSymbols.ELT_SIMPLECONTENT)) {
                    traverseSimpleContent(element2, xSDocumentInfo, schemaGrammar);
                    Element nextSiblingElement = DOMUtil.getNextSiblingElement(element2);
                    if (nextSiblingElement != null) {
                        throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, DOMUtil.getLocalName(nextSiblingElement)}, nextSiblingElement);
                    }
                } else if (DOMUtil.getLocalName(element2).equals(SchemaSymbols.ELT_COMPLEXCONTENT)) {
                    traverseComplexContent(element2, bool2.booleanValue(), xSDocumentInfo, schemaGrammar);
                    Element nextSiblingElement2 = DOMUtil.getNextSiblingElement(element2);
                    if (nextSiblingElement2 != null) {
                        throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, DOMUtil.getLocalName(nextSiblingElement2)}, nextSiblingElement2);
                    }
                } else {
                    this.fBaseType = SchemaGrammar.fAnyType;
                    this.fDerivedBy = (short) 2;
                    zBooleanValue = bool2.booleanValue();
                }
                XSComplexTypeDecl xSComplexTypeDecl = this.fComplexTypeDecl;
                String str2 = this.fName;
                String str3 = this.fTargetNamespace;
                XSTypeDefinition xSTypeDefinition = this.fBaseType;
                short s = this.fDerivedBy;
                short s2 = this.fFinal;
                short s3 = this.fBlock;
                short s4 = this.fContentType;
                boolean z = this.fIsAbstract;
                XSAttributeGroupDecl xSAttributeGroupDecl = this.fAttrGrp;
                XSSimpleType xSSimpleType = this.fXSSimpleType;
                XSParticleDecl xSParticleDecl = this.fParticle;
                XSAnnotationImpl[] xSAnnotationImplArr = this.fAnnotations;
                xSComplexTypeDecl.setValues(str2, str3, xSTypeDefinition, s, s2, s3, s4, z, xSAttributeGroupDecl, xSSimpleType, xSParticleDecl, new XSObjectListImpl(xSAnnotationImplArr, xSAnnotationImplArr != null ? xSAnnotationImplArr.length : 0));
                return this.fComplexTypeDecl;
            }
            this.fBaseType = SchemaGrammar.fAnyType;
            this.fDerivedBy = (short) 2;
            zBooleanValue = bool2.booleanValue();
            processComplexContent(element2, zBooleanValue, false, xSDocumentInfo, schemaGrammar);
        } catch (ComplexTypeRecoverableError e) {
            handleComplexTypeError(e.getMessage(), e.errorSubstText, e.errorElem);
        }
        XSComplexTypeDecl xSComplexTypeDecl2 = this.fComplexTypeDecl;
        String str4 = this.fName;
        String str5 = this.fTargetNamespace;
        XSTypeDefinition xSTypeDefinition2 = this.fBaseType;
        short s5 = this.fDerivedBy;
        short s6 = this.fFinal;
        short s7 = this.fBlock;
        short s8 = this.fContentType;
        boolean z2 = this.fIsAbstract;
        XSAttributeGroupDecl xSAttributeGroupDecl2 = this.fAttrGrp;
        XSSimpleType xSSimpleType2 = this.fXSSimpleType;
        XSParticleDecl xSParticleDecl2 = this.fParticle;
        XSAnnotationImpl[] xSAnnotationImplArr2 = this.fAnnotations;
        xSComplexTypeDecl2.setValues(str4, str5, xSTypeDefinition2, s5, s6, s7, s8, z2, xSAttributeGroupDecl2, xSSimpleType2, xSParticleDecl2, new XSObjectListImpl(xSAnnotationImplArr2, xSAnnotationImplArr2 != null ? xSAnnotationImplArr2.length : 0));
        return this.fComplexTypeDecl;
    }

    /* JADX WARN: Code duplicated, block: B:101:0x0273  */
    /* JADX WARN: Code duplicated, block: B:103:0x0279  */
    /* JADX WARN: Code duplicated, block: B:106:0x028b  */
    /* JADX WARN: Code duplicated, block: B:108:0x02a5  */
    /* JADX WARN: Code duplicated, block: B:114:0x02e4  */
    /* JADX WARN: Code duplicated, block: B:119:0x0307  */
    /* JADX WARN: Code duplicated, block: B:121:0x031f  */
    /* JADX WARN: Code duplicated, block: B:123:0x0325  */
    /* JADX WARN: Code duplicated, block: B:125:0x032b  */
    /* JADX WARN: Code duplicated, block: B:127:0x033b  */
    /* JADX WARN: Code duplicated, block: B:128:0x0341  */
    /* JADX WARN: Code duplicated, block: B:130:0x035b  */
    /* JADX WARN: Code duplicated, block: B:152:0x0377 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:44:0x00ee  */
    /* JADX WARN: Code duplicated, block: B:46:0x00fc  */
    /* JADX WARN: Code duplicated, block: B:47:0x00ff  */
    /* JADX WARN: Code duplicated, block: B:50:0x0113  */
    /* JADX WARN: Code duplicated, block: B:52:0x0119  */
    /* JADX WARN: Code duplicated, block: B:54:0x0125  */
    /* JADX WARN: Code duplicated, block: B:55:0x0132  */
    /* JADX WARN: Code duplicated, block: B:57:0x0138  */
    /* JADX WARN: Code duplicated, block: B:58:0x014c  */
    /* JADX WARN: Code duplicated, block: B:67:0x017a  */
    /* JADX WARN: Code duplicated, block: B:69:0x0183  */
    /* JADX WARN: Code duplicated, block: B:73:0x0196  */
    /* JADX WARN: Code duplicated, block: B:87:0x01f2  */
    /* JADX WARN: Code duplicated, block: B:89:0x01f7 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:90:0x01f9  */
    /* JADX WARN: Code duplicated, block: B:91:0x0212  */
    /* JADX WARN: Code duplicated, block: B:99:0x026b  */
    private void traverseSimpleContent(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) throws ComplexTypeRecoverableError {
        Element nextSiblingElement;
        XSSimpleType xSSimpleType;
        short s;
        XSComplexTypeDecl xSComplexTypeDecl;
        XSSimpleType xSSimpleType2;
        Element firstChildElement;
        String syntheticAnnotation;
        Element nextSiblingElement2;
        Element elementTraverseAttrsAndAttrGrps;
        XSSimpleType xSSimpleTypeTraverseLocal;
        short s2;
        short s3;
        XSFacets xSFacets;
        Element element2;
        XSSimpleType xSSimpleType3;
        Object[] objArrValidRestrictionOf;
        Element elementTraverseAttrsAndAttrGrps2;
        String syntheticAnnotation2;
        Element nextSiblingElement3;
        String str;
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, false, xSDocumentInfo);
        this.fContentType = (short) 1;
        this.fParticle = null;
        Element firstChildElement2 = DOMUtil.getFirstChildElement(element);
        if (firstChildElement2 == null || !DOMUtil.getLocalName(firstChildElement2).equals(SchemaSymbols.ELT_ANNOTATION)) {
            String syntheticAnnotation3 = DOMUtil.getSyntheticAnnotation(element);
            if (syntheticAnnotation3 != null) {
                addAnnotation(traverseSyntheticAnnotation(element, syntheticAnnotation3, objArrCheckAttributes, false, xSDocumentInfo));
            }
            nextSiblingElement = firstChildElement2;
        } else {
            addAnnotation(traverseAnnotationDecl(firstChildElement2, objArrCheckAttributes, false, xSDocumentInfo));
            nextSiblingElement = DOMUtil.getNextSiblingElement(firstChildElement2);
        }
        if (nextSiblingElement == null) {
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.2", new Object[]{this.fName, SchemaSymbols.ELT_SIMPLECONTENT}, element);
        }
        String localName = DOMUtil.getLocalName(nextSiblingElement);
        if (localName.equals(SchemaSymbols.ELT_RESTRICTION)) {
            this.fDerivedBy = (short) 2;
        } else {
            if (!localName.equals(SchemaSymbols.ELT_EXTENSION)) {
                this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, localName}, nextSiblingElement);
            }
            this.fDerivedBy = (short) 1;
        }
        Element nextSiblingElement4 = DOMUtil.getNextSiblingElement(nextSiblingElement);
        if (nextSiblingElement4 != null) {
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, DOMUtil.getLocalName(nextSiblingElement4)}, nextSiblingElement4);
        }
        Object[] objArrCheckAttributes2 = this.fAttrChecker.checkAttributes(nextSiblingElement, false, xSDocumentInfo);
        QName qName = (QName) objArrCheckAttributes2[XSAttributeChecker.ATTIDX_BASE];
        if (qName == null) {
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
            throw new ComplexTypeRecoverableError("s4s-att-must-appear", new Object[]{localName, ApkUtil.DEF_MODULE_NAME}, nextSiblingElement);
        }
        XSTypeDefinition xSTypeDefinition = (XSTypeDefinition) this.fSchemaHandler.getGlobalDecl(xSDocumentInfo, 7, qName, nextSiblingElement);
        if (xSTypeDefinition == null) {
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
            throw new ComplexTypeRecoverableError();
        }
        this.fBaseType = xSTypeDefinition;
        if (xSTypeDefinition.getTypeCategory() == 15) {
            XSComplexTypeDecl xSComplexTypeDecl2 = (XSComplexTypeDecl) xSTypeDefinition;
            s = xSComplexTypeDecl2.getFinal();
            if (xSComplexTypeDecl2.getContentType() == 1) {
                xSSimpleType = (XSSimpleType) xSComplexTypeDecl2.getSimpleType();
                xSComplexTypeDecl = xSComplexTypeDecl2;
            } else {
                if (this.fDerivedBy != 2 || xSComplexTypeDecl2.getContentType() != 3 || !((XSParticleDecl) xSComplexTypeDecl2.getParticle()).emptiable()) {
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                    throw new ComplexTypeRecoverableError("src-ct.2.1", new Object[]{this.fName, xSComplexTypeDecl2.getName()}, nextSiblingElement);
                }
                xSSimpleType2 = null;
                xSComplexTypeDecl = xSComplexTypeDecl2;
            }
            if ((this.fDerivedBy & s) != 0) {
                this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                if (this.fDerivedBy == 1) {
                    str = "cos-ct-extends.1.1";
                } else {
                    str = "derivation-ok-restriction.1";
                }
                throw new ComplexTypeRecoverableError(str, new Object[]{this.fName, this.fBaseType.getName()}, nextSiblingElement);
            }
            firstChildElement = DOMUtil.getFirstChildElement(nextSiblingElement);
            if (firstChildElement != null) {
                if (DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
                    addAnnotation(traverseAnnotationDecl(firstChildElement, objArrCheckAttributes2, false, xSDocumentInfo));
                    nextSiblingElement3 = DOMUtil.getNextSiblingElement(firstChildElement);
                    xSSimpleType2 = xSSimpleType2;
                } else {
                    syntheticAnnotation2 = DOMUtil.getSyntheticAnnotation(nextSiblingElement);
                    if (syntheticAnnotation2 != null) {
                        addAnnotation(traverseSyntheticAnnotation(nextSiblingElement, syntheticAnnotation2, objArrCheckAttributes2, false, xSDocumentInfo));
                    }
                    nextSiblingElement3 = firstChildElement;
                }
                if (nextSiblingElement3 == null && DOMUtil.getLocalName(nextSiblingElement3).equals(SchemaSymbols.ELT_ANNOTATION)) {
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                    throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, SchemaSymbols.ELT_ANNOTATION}, nextSiblingElement3);
                }
                nextSiblingElement2 = nextSiblingElement3;
            } else {
                xSSimpleType2 = xSSimpleType2;
                syntheticAnnotation = DOMUtil.getSyntheticAnnotation(nextSiblingElement);
                if (syntheticAnnotation != null) {
                    addAnnotation(traverseSyntheticAnnotation(nextSiblingElement, syntheticAnnotation, objArrCheckAttributes2, false, xSDocumentInfo));
                }
                nextSiblingElement2 = firstChildElement;
            }
            if (this.fDerivedBy == 2) {
                if (nextSiblingElement2 == null && DOMUtil.getLocalName(nextSiblingElement2).equals(SchemaSymbols.ELT_SIMPLETYPE)) {
                    xSSimpleTypeTraverseLocal = this.fSchemaHandler.fSimpleTypeTraverser.traverseLocal(nextSiblingElement2, xSDocumentInfo, schemaGrammar);
                    if (xSSimpleTypeTraverseLocal == null) {
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                        throw new ComplexTypeRecoverableError();
                    }
                    if (xSSimpleType2 != null && !XSConstraints.checkSimpleDerivationOk(xSSimpleTypeTraverseLocal, xSSimpleType2, xSSimpleType2.getFinal())) {
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                        throw new ComplexTypeRecoverableError("derivation-ok-restriction.5.2.2.1", new Object[]{this.fName, xSSimpleTypeTraverseLocal.getName(), xSSimpleType2.getName()}, nextSiblingElement2);
                    }
                    nextSiblingElement2 = DOMUtil.getNextSiblingElement(nextSiblingElement2);
                } else {
                    xSSimpleTypeTraverseLocal = xSSimpleType2;
                }
                if (xSSimpleTypeTraverseLocal != null) {
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                    throw new ComplexTypeRecoverableError("src-ct.2.2", new Object[]{this.fName}, nextSiblingElement2);
                }
                if (nextSiblingElement2 != null) {
                    XSDAbstractTraverser.FacetInfo facetInfoTraverseFacets = traverseFacets(nextSiblingElement2, this.fComplexTypeDecl, xSSimpleTypeTraverseLocal, xSDocumentInfo);
                    Element element3 = facetInfoTraverseFacets.nodeAfterFacets;
                    XSFacets xSFacets2 = facetInfoTraverseFacets.facetdata;
                    short s4 = facetInfoTraverseFacets.fPresentFacets;
                    s3 = facetInfoTraverseFacets.fFixedFacets;
                    xSFacets = xSFacets2;
                    element2 = element3;
                    s2 = s4;
                } else {
                    s2 = 0;
                    s3 = 0;
                    xSFacets = null;
                    element2 = null;
                }
                String strGenAnonTypeName = genAnonTypeName(element);
                this.fXSSimpleType = this.fSchemaHandler.fDVFactory.createTypeRestriction(strGenAnonTypeName, xSDocumentInfo.fTargetNamespace, (short) 0, xSSimpleTypeTraverseLocal, null);
                try {
                    this.fValidationState.setNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
                    this.fXSSimpleType.applyFacets(xSFacets, s2, s3, this.fValidationState);
                } catch (InvalidDatatypeFacetException e) {
                    reportSchemaError(e.getKey(), e.getArgs(), nextSiblingElement2);
                    this.fXSSimpleType = this.fSchemaHandler.fDVFactory.createTypeRestriction(strGenAnonTypeName, xSDocumentInfo.fTargetNamespace, (short) 0, xSSimpleTypeTraverseLocal, null);
                }
                xSSimpleType3 = this.fXSSimpleType;
                if (xSSimpleType3 instanceof XSSimpleTypeDecl) {
                    ((XSSimpleTypeDecl) xSSimpleType3).setAnonymous(true);
                }
                if (element2 != null) {
                    if (isAttrOrAttrGroup(element2)) {
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                        throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, DOMUtil.getLocalName(element2)}, element2);
                    }
                    elementTraverseAttrsAndAttrGrps2 = traverseAttrsAndAttrGrps(element2, this.fAttrGrp, xSDocumentInfo, schemaGrammar, this.fComplexTypeDecl);
                    if (elementTraverseAttrsAndAttrGrps2 != null) {
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                        throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, DOMUtil.getLocalName(elementTraverseAttrsAndAttrGrps2)}, elementTraverseAttrsAndAttrGrps2);
                    }
                }
                try {
                    mergeAttributes(xSComplexTypeDecl.getAttrGrp(), this.fAttrGrp, this.fName, false, element);
                    this.fAttrGrp.removeProhibitedAttrs();
                    objArrValidRestrictionOf = this.fAttrGrp.validRestrictionOf(this.fName, xSComplexTypeDecl.getAttrGrp());
                    if (objArrValidRestrictionOf != null) {
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                        throw new ComplexTypeRecoverableError((String) objArrValidRestrictionOf[objArrValidRestrictionOf.length - 1], objArrValidRestrictionOf, element2);
                    }
                } catch (ComplexTypeRecoverableError e2) {
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                    throw e2;
                }
            } else {
                this.fXSSimpleType = xSSimpleType2;
                if (nextSiblingElement2 != null) {
                    if (isAttrOrAttrGroup(nextSiblingElement2)) {
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                        throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, DOMUtil.getLocalName(nextSiblingElement2)}, nextSiblingElement2);
                    }
                    elementTraverseAttrsAndAttrGrps = traverseAttrsAndAttrGrps(nextSiblingElement2, this.fAttrGrp, xSDocumentInfo, schemaGrammar, this.fComplexTypeDecl);
                    if (elementTraverseAttrsAndAttrGrps == null) {
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                        throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, DOMUtil.getLocalName(elementTraverseAttrsAndAttrGrps)}, elementTraverseAttrsAndAttrGrps);
                    }
                    this.fAttrGrp.removeProhibitedAttrs();
                }
                if (xSComplexTypeDecl != null) {
                    try {
                        mergeAttributes(xSComplexTypeDecl.getAttrGrp(), this.fAttrGrp, this.fName, true, element);
                    } catch (ComplexTypeRecoverableError e3) {
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                        throw e3;
                    }
                }
            }
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
        }
        xSSimpleType = (XSSimpleType) xSTypeDefinition;
        if (this.fDerivedBy == 2) {
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
            throw new ComplexTypeRecoverableError("src-ct.2.1", new Object[]{this.fName, xSSimpleType.getName()}, nextSiblingElement);
        }
        s = xSSimpleType.getFinal();
        xSComplexTypeDecl = null;
        xSSimpleType2 = xSSimpleType;
        if ((this.fDerivedBy & s) != 0) {
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
            if (this.fDerivedBy == 1) {
                str = "cos-ct-extends.1.1";
            } else {
                str = "derivation-ok-restriction.1";
            }
            throw new ComplexTypeRecoverableError(str, new Object[]{this.fName, this.fBaseType.getName()}, nextSiblingElement);
        }
        firstChildElement = DOMUtil.getFirstChildElement(nextSiblingElement);
        if (firstChildElement != null) {
            if (DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
                addAnnotation(traverseAnnotationDecl(firstChildElement, objArrCheckAttributes2, false, xSDocumentInfo));
                nextSiblingElement3 = DOMUtil.getNextSiblingElement(firstChildElement);
                xSSimpleType2 = xSSimpleType2;
            } else {
                syntheticAnnotation2 = DOMUtil.getSyntheticAnnotation(nextSiblingElement);
                if (syntheticAnnotation2 != null) {
                    addAnnotation(traverseSyntheticAnnotation(nextSiblingElement, syntheticAnnotation2, objArrCheckAttributes2, false, xSDocumentInfo));
                }
                nextSiblingElement3 = firstChildElement;
            }
            if (nextSiblingElement3 == null) {
            }
            nextSiblingElement2 = nextSiblingElement3;
        } else {
            xSSimpleType2 = xSSimpleType2;
            syntheticAnnotation = DOMUtil.getSyntheticAnnotation(nextSiblingElement);
            if (syntheticAnnotation != null) {
                addAnnotation(traverseSyntheticAnnotation(nextSiblingElement, syntheticAnnotation, objArrCheckAttributes2, false, xSDocumentInfo));
            }
            nextSiblingElement2 = firstChildElement;
        }
        if (this.fDerivedBy == 2) {
            if (nextSiblingElement2 == null) {
                xSSimpleTypeTraverseLocal = xSSimpleType2;
            } else {
                xSSimpleTypeTraverseLocal = xSSimpleType2;
            }
            if (xSSimpleTypeTraverseLocal != null) {
                this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                throw new ComplexTypeRecoverableError("src-ct.2.2", new Object[]{this.fName}, nextSiblingElement2);
            }
            if (nextSiblingElement2 != null) {
                XSDAbstractTraverser.FacetInfo facetInfoTraverseFacets2 = traverseFacets(nextSiblingElement2, this.fComplexTypeDecl, xSSimpleTypeTraverseLocal, xSDocumentInfo);
                Element element4 = facetInfoTraverseFacets2.nodeAfterFacets;
                XSFacets xSFacets3 = facetInfoTraverseFacets2.facetdata;
                short s5 = facetInfoTraverseFacets2.fPresentFacets;
                s3 = facetInfoTraverseFacets2.fFixedFacets;
                xSFacets = xSFacets3;
                element2 = element4;
                s2 = s5;
            } else {
                s2 = 0;
                s3 = 0;
                xSFacets = null;
                element2 = null;
            }
            String strGenAnonTypeName2 = genAnonTypeName(element);
            this.fXSSimpleType = this.fSchemaHandler.fDVFactory.createTypeRestriction(strGenAnonTypeName2, xSDocumentInfo.fTargetNamespace, (short) 0, xSSimpleTypeTraverseLocal, null);
            this.fValidationState.setNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
            this.fXSSimpleType.applyFacets(xSFacets, s2, s3, this.fValidationState);
            xSSimpleType3 = this.fXSSimpleType;
            if (xSSimpleType3 instanceof XSSimpleTypeDecl) {
                ((XSSimpleTypeDecl) xSSimpleType3).setAnonymous(true);
            }
            if (element2 != null) {
                if (isAttrOrAttrGroup(element2)) {
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                    throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, DOMUtil.getLocalName(element2)}, element2);
                }
                elementTraverseAttrsAndAttrGrps2 = traverseAttrsAndAttrGrps(element2, this.fAttrGrp, xSDocumentInfo, schemaGrammar, this.fComplexTypeDecl);
                if (elementTraverseAttrsAndAttrGrps2 != null) {
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                    throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, DOMUtil.getLocalName(elementTraverseAttrsAndAttrGrps2)}, elementTraverseAttrsAndAttrGrps2);
                }
            }
            mergeAttributes(xSComplexTypeDecl.getAttrGrp(), this.fAttrGrp, this.fName, false, element);
            this.fAttrGrp.removeProhibitedAttrs();
            objArrValidRestrictionOf = this.fAttrGrp.validRestrictionOf(this.fName, xSComplexTypeDecl.getAttrGrp());
            if (objArrValidRestrictionOf != null) {
                this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                throw new ComplexTypeRecoverableError((String) objArrValidRestrictionOf[objArrValidRestrictionOf.length - 1], objArrValidRestrictionOf, element2);
            }
        } else {
            this.fXSSimpleType = xSSimpleType2;
            if (nextSiblingElement2 != null) {
                if (isAttrOrAttrGroup(nextSiblingElement2)) {
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                    throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, DOMUtil.getLocalName(nextSiblingElement2)}, nextSiblingElement2);
                }
                elementTraverseAttrsAndAttrGrps = traverseAttrsAndAttrGrps(nextSiblingElement2, this.fAttrGrp, xSDocumentInfo, schemaGrammar, this.fComplexTypeDecl);
                if (elementTraverseAttrsAndAttrGrps == null) {
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                    throw new ComplexTypeRecoverableError("s4s-elt-invalid-content.1", new Object[]{this.fName, DOMUtil.getLocalName(elementTraverseAttrsAndAttrGrps)}, elementTraverseAttrsAndAttrGrps);
                }
                this.fAttrGrp.removeProhibitedAttrs();
            }
            if (xSComplexTypeDecl != null) {
                mergeAttributes(xSComplexTypeDecl.getAttrGrp(), this.fAttrGrp, this.fName, true, element);
            }
        }
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
    }

    private void traverseSimpleContentDecl(Element element) {
    }

    XSComplexTypeDecl traverseGlobal(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, true, xSDocumentInfo);
        String str = (String) objArrCheckAttributes[XSAttributeChecker.ATTIDX_NAME];
        contentBackup();
        XSComplexTypeDecl xSComplexTypeDeclTraverseComplexTypeDecl = traverseComplexTypeDecl(element, str, objArrCheckAttributes, xSDocumentInfo, schemaGrammar);
        contentRestore();
        schemaGrammar.addComplexTypeDecl(xSComplexTypeDeclTraverseComplexTypeDecl, this.fSchemaHandler.element2Locator(element));
        if (str == null) {
            reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_COMPLEXTYPE, SchemaSymbols.ATT_NAME}, element);
            xSComplexTypeDeclTraverseComplexTypeDecl = null;
        } else {
            if (schemaGrammar.getGlobalTypeDecl(xSComplexTypeDeclTraverseComplexTypeDecl.getName()) == null) {
                schemaGrammar.addGlobalComplexTypeDecl(xSComplexTypeDeclTraverseComplexTypeDecl);
            }
            String strSchemaDocument2SystemId = this.fSchemaHandler.schemaDocument2SystemId(xSDocumentInfo);
            XSTypeDefinition globalTypeDecl = schemaGrammar.getGlobalTypeDecl(xSComplexTypeDeclTraverseComplexTypeDecl.getName(), strSchemaDocument2SystemId);
            if (globalTypeDecl == null) {
                schemaGrammar.addGlobalComplexTypeDecl(xSComplexTypeDeclTraverseComplexTypeDecl, strSchemaDocument2SystemId);
            }
            if (this.fSchemaHandler.fTolerateDuplicates) {
                if (globalTypeDecl != null && (globalTypeDecl instanceof XSComplexTypeDecl)) {
                    xSComplexTypeDeclTraverseComplexTypeDecl = (XSComplexTypeDecl) globalTypeDecl;
                }
                this.fSchemaHandler.addGlobalTypeDecl(xSComplexTypeDeclTraverseComplexTypeDecl);
            }
        }
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
        return xSComplexTypeDeclTraverseComplexTypeDecl;
    }

    XSComplexTypeDecl traverseLocal(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, false, xSDocumentInfo);
        String strGenAnonTypeName = genAnonTypeName(element);
        contentBackup();
        XSComplexTypeDecl xSComplexTypeDeclTraverseComplexTypeDecl = traverseComplexTypeDecl(element, strGenAnonTypeName, objArrCheckAttributes, xSDocumentInfo, schemaGrammar);
        contentRestore();
        schemaGrammar.addComplexTypeDecl(xSComplexTypeDeclTraverseComplexTypeDecl, this.fSchemaHandler.element2Locator(element));
        xSComplexTypeDeclTraverseComplexTypeDecl.setIsAnonymous();
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
        return xSComplexTypeDeclTraverseComplexTypeDecl;
    }
}
