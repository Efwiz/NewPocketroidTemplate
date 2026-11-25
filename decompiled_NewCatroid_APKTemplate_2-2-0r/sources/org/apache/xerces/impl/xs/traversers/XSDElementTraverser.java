package org.apache.xerces.impl.xs.traversers;

import java.util.Locale;
import org.apache.xerces.impl.dv.ValidatedInfo;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAnnotationImpl;
import org.apache.xerces.impl.xs.XSComplexTypeDecl;
import org.apache.xerces.impl.xs.XSConstraints;
import org.apache.xerces.impl.xs.XSElementDecl;
import org.apache.xerces.impl.xs.XSParticleDecl;
import org.apache.xerces.impl.xs.util.XInt;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes13.dex */
class XSDElementTraverser extends XSDAbstractTraverser {
    boolean fDeferTraversingLocalElements;
    protected final XSElementDecl fTempElementDecl;

    XSDElementTraverser(XSDHandler xSDHandler, XSAttributeChecker xSAttributeChecker) {
        super(xSDHandler, xSAttributeChecker);
        this.fTempElementDecl = new XSElementDecl();
    }

    @Override // org.apache.xerces.impl.xs.traversers.XSDAbstractTraverser
    void reset(SymbolTable symbolTable, boolean z, Locale locale) {
        super.reset(symbolTable, z, locale);
        this.fDeferTraversingLocalElements = true;
    }

    XSElementDecl traverseGlobal(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, true, xSDocumentInfo);
        XSElementDecl xSElementDeclTraverseNamedElement = traverseNamedElement(element, objArrCheckAttributes, xSDocumentInfo, schemaGrammar, true, null);
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
        return xSElementDeclTraverseNamedElement;
    }

    XSParticleDecl traverseLocal(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar, int i, XSObject xSObject) {
        XSParticleDecl particleDecl = this.fSchemaHandler.fDeclPool != null ? this.fSchemaHandler.fDeclPool.getParticleDecl() : new XSParticleDecl();
        if (!this.fDeferTraversingLocalElements) {
            traverseLocal(particleDecl, element, xSDocumentInfo, schemaGrammar, i, xSObject, null);
            if (particleDecl.fType == 0) {
                return null;
            }
            return particleDecl;
        }
        particleDecl.fType = (short) 1;
        Attr attributeNode = element.getAttributeNode(SchemaSymbols.ATT_MINOCCURS);
        if (attributeNode != null) {
            try {
                int i2 = Integer.parseInt(XMLChar.trim(attributeNode.getValue()));
                if (i2 >= 0) {
                    particleDecl.fMinOccurs = i2;
                }
            } catch (NumberFormatException unused) {
            }
        }
        this.fSchemaHandler.fillInLocalElemInfo(element, xSDocumentInfo, i, xSObject, particleDecl);
        return particleDecl;
    }

    /* JADX WARN: Code duplicated, block: B:25:0x00b2  */
    /* JADX WARN: Code duplicated, block: B:26:0x00b8  */
    /* JADX WARN: Code duplicated, block: B:28:0x00bc A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:29:0x00be  */
    /* JADX WARN: Code duplicated, block: B:30:0x00ca A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:31:0x00cc  */
    /* JADX WARN: Code duplicated, block: B:32:0x00cf  */
    protected void traverseLocal(XSParticleDecl xSParticleDecl, Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar, int i, XSObject xSObject, String[] strArr) {
        XSElementDecl xSElementDeclTraverseNamedElement;
        XSAnnotationImpl xSAnnotationImpl;
        XSObjectList xSObjectList;
        if (strArr != null) {
            xSDocumentInfo.fNamespaceSupport.setEffectiveContext(strArr);
        }
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, false, xSDocumentInfo);
        QName qName = (QName) objArrCheckAttributes[XSAttributeChecker.ATTIDX_REF];
        XInt xInt = (XInt) objArrCheckAttributes[XSAttributeChecker.ATTIDX_MINOCCURS];
        XInt xInt2 = (XInt) objArrCheckAttributes[XSAttributeChecker.ATTIDX_MAXOCCURS];
        XSAnnotationImpl xSAnnotationImplTraverseSyntheticAnnotation = null;
        if (element.getAttributeNode(SchemaSymbols.ATT_REF) != null) {
            if (qName != null) {
                XSElementDecl xSElementDecl = (XSElementDecl) this.fSchemaHandler.getGlobalDecl(xSDocumentInfo, 3, qName, element);
                Element firstChildElement = DOMUtil.getFirstChildElement(element);
                if (firstChildElement == null || !DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
                    String syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element);
                    if (syntheticAnnotation != null) {
                        xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo);
                    }
                } else {
                    XSAnnotationImpl xSAnnotationImplTraverseAnnotationDecl = traverseAnnotationDecl(firstChildElement, objArrCheckAttributes, false, xSDocumentInfo);
                    firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                    xSAnnotationImplTraverseSyntheticAnnotation = xSAnnotationImplTraverseAnnotationDecl;
                }
                if (firstChildElement != null) {
                    firstChildElement = firstChildElement;
                    reportSchemaError("src-element.2.2", new Object[]{qName.rawname, DOMUtil.getLocalName(firstChildElement)}, firstChildElement);
                }
                firstChildElement = firstChildElement;
                xSElementDeclTraverseNamedElement = xSElementDecl;
            } else {
                xSElementDeclTraverseNamedElement = null;
                xSAnnotationImpl = null;
            }
            xSParticleDecl.fMinOccurs = xInt.intValue();
            xSParticleDecl.fMaxOccurs = xInt2.intValue();
            if (xSElementDeclTraverseNamedElement != null) {
                xSParticleDecl.fType = (short) 1;
                xSParticleDecl.fValue = xSElementDeclTraverseNamedElement;
            } else {
                xSParticleDecl.fType = (short) 0;
            }
            if (qName != null) {
                if (xSAnnotationImpl != null) {
                    XSObjectListImpl xSObjectListImpl = new XSObjectListImpl();
                    xSObjectListImpl.addXSObject(xSAnnotationImpl);
                    xSObjectList = xSObjectListImpl;
                } else {
                    xSObjectList = XSObjectListImpl.EMPTY_LIST;
                }
            } else if (xSElementDeclTraverseNamedElement != null) {
                xSObjectList = xSElementDeclTraverseNamedElement.fAnnotations;
            } else {
                xSObjectList = XSObjectListImpl.EMPTY_LIST;
            }
            xSParticleDecl.fAnnotations = xSObjectList;
            checkOccurrences(xSParticleDecl, SchemaSymbols.ELT_ELEMENT, (Element) element.getParentNode(), i, ((Long) objArrCheckAttributes[XSAttributeChecker.ATTIDX_FROMDEFAULT]).longValue());
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
        }
        xSElementDeclTraverseNamedElement = traverseNamedElement(element, objArrCheckAttributes, xSDocumentInfo, schemaGrammar, false, xSObject);
        xSAnnotationImpl = xSAnnotationImplTraverseSyntheticAnnotation;
        xSParticleDecl.fMinOccurs = xInt.intValue();
        xSParticleDecl.fMaxOccurs = xInt2.intValue();
        if (xSElementDeclTraverseNamedElement != null) {
            xSParticleDecl.fType = (short) 1;
            xSParticleDecl.fValue = xSElementDeclTraverseNamedElement;
        } else {
            xSParticleDecl.fType = (short) 0;
        }
        if (qName != null) {
            if (xSAnnotationImpl != null) {
                XSObjectListImpl xSObjectListImpl2 = new XSObjectListImpl();
                xSObjectListImpl2.addXSObject(xSAnnotationImpl);
                xSObjectList = xSObjectListImpl2;
            } else {
                xSObjectList = XSObjectListImpl.EMPTY_LIST;
            }
        } else if (xSElementDeclTraverseNamedElement != null) {
            xSObjectList = xSElementDeclTraverseNamedElement.fAnnotations;
        } else {
            xSObjectList = XSObjectListImpl.EMPTY_LIST;
        }
        xSParticleDecl.fAnnotations = xSObjectList;
        checkOccurrences(xSParticleDecl, SchemaSymbols.ELT_ELEMENT, (Element) element.getParentNode(), i, ((Long) objArrCheckAttributes[XSAttributeChecker.ATTIDX_FROMDEFAULT]).longValue());
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0214  */
    /* JADX WARN: Code duplicated, block: B:102:0x021c  */
    /* JADX WARN: Code duplicated, block: B:103:0x0231  */
    /* JADX WARN: Code duplicated, block: B:105:0x0274  */
    /* JADX WARN: Code duplicated, block: B:108:0x0286  */
    /* JADX WARN: Code duplicated, block: B:109:0x0296  */
    /* JADX WARN: Code duplicated, block: B:113:0x02b2 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:114:0x02b4  */
    /* JADX WARN: Code duplicated, block: B:115:0x02c2  */
    /* JADX WARN: Code duplicated, block: B:117:0x02ca  */
    /* JADX WARN: Code duplicated, block: B:119:0x02ce  */
    /* JADX WARN: Code duplicated, block: B:121:0x02df A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:124:0x02ec A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:128:0x02fe  */
    /* JADX WARN: Code duplicated, block: B:130:0x0315  */
    /* JADX WARN: Code duplicated, block: B:131:0x0329  */
    /* JADX WARN: Code duplicated, block: B:134:0x032e  */
    /* JADX WARN: Code duplicated, block: B:139:0x0368  */
    /* JADX WARN: Code duplicated, block: B:150:0x039d A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:151:0x039e A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:152:0x03a0  */
    /* JADX WARN: Code duplicated, block: B:154:0x03ad  */
    /* JADX WARN: Code duplicated, block: B:157:0x03be  */
    /* JADX WARN: Code duplicated, block: B:160:0x03c7 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:161:0x03c9  */
    /* JADX WARN: Code duplicated, block: B:162:0x03cb  */
    /* JADX WARN: Code duplicated, block: B:24:0x009e  */
    /* JADX WARN: Code duplicated, block: B:25:0x00a1  */
    /* JADX WARN: Code duplicated, block: B:28:0x00a9  */
    /* JADX WARN: Code duplicated, block: B:29:0x00ac  */
    /* JADX WARN: Code duplicated, block: B:32:0x00c6  */
    /* JADX WARN: Code duplicated, block: B:34:0x00cb  */
    /* JADX WARN: Code duplicated, block: B:39:0x00d7  */
    /* JADX WARN: Code duplicated, block: B:40:0x00e8 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:41:0x00ea  */
    /* JADX WARN: Code duplicated, block: B:42:0x00fa  */
    /* JADX WARN: Code duplicated, block: B:44:0x0100  */
    /* JADX WARN: Code duplicated, block: B:47:0x0110  */
    /* JADX WARN: Code duplicated, block: B:50:0x0128  */
    /* JADX WARN: Code duplicated, block: B:52:0x012e  */
    /* JADX WARN: Code duplicated, block: B:53:0x0148  */
    /* JADX WARN: Code duplicated, block: B:55:0x0150  */
    /* JADX WARN: Code duplicated, block: B:56:0x015c  */
    /* JADX WARN: Code duplicated, block: B:59:0x0162  */
    /* JADX WARN: Code duplicated, block: B:61:0x016e  */
    /* JADX WARN: Code duplicated, block: B:63:0x017d  */
    /* JADX WARN: Code duplicated, block: B:65:0x0187  */
    /* JADX WARN: Code duplicated, block: B:66:0x0190  */
    /* JADX WARN: Code duplicated, block: B:69:0x0197 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:73:0x01a8  */
    /* JADX WARN: Code duplicated, block: B:75:0x01ac  */
    /* JADX WARN: Code duplicated, block: B:79:0x01b6  */
    /* JADX WARN: Code duplicated, block: B:82:0x01bc  */
    /* JADX WARN: Code duplicated, block: B:84:0x01c2  */
    /* JADX WARN: Code duplicated, block: B:92:0x01e2  */
    /* JADX WARN: Code duplicated, block: B:98:0x01fa  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v22, types: [org.apache.xerces.xs.XSObject] */
    /* JADX WARN: Type inference failed for: r0v25 */
    /* JADX WARN: Type inference failed for: r0v26 */
    /* JADX WARN: Type inference failed for: r0v27, types: [org.apache.xerces.xs.XSTypeDefinition] */
    /* JADX WARN: Type inference failed for: r0v37, types: [org.apache.xerces.impl.xs.XSComplexTypeDecl] */
    /* JADX WARN: Type inference failed for: r0v39, types: [org.apache.xerces.xs.XSTypeDefinition] */
    /* JADX WARN: Type inference failed for: r0v65 */
    /* JADX WARN: Type inference failed for: r0v66 */
    /* JADX WARN: Type inference failed for: r0v70 */
    /* JADX WARN: Type inference failed for: r0v71 */
    /* JADX WARN: Type inference failed for: r0v72 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v42, types: [org.apache.xerces.impl.xs.util.XSObjectListImpl] */
    /* JADX WARN: Type inference failed for: r1v44 */
    /* JADX WARN: Type inference failed for: r1v45 */
    /* JADX WARN: Type inference failed for: r1v5, types: [org.apache.xerces.xs.XSObjectList] */
    /* JADX WARN: Type inference failed for: r21v0, types: [org.apache.xerces.impl.xs.traversers.XSDElementTraverser] */
    /* JADX WARN: Type inference failed for: r4v9, types: [java.lang.Object[], org.apache.xerces.impl.dv.ValidatedInfo, org.apache.xerces.impl.xs.XSElementDecl] */
    XSElementDecl traverseNamedElement(Element element, Object[] objArr, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar, boolean z, XSObject xSObject) {
        String str;
        short sShortValue;
        short sShortValue2;
        boolean z2;
        Element firstChildElement;
        String syntheticAnnotation;
        ?? r0;
        XSAnnotationImpl xSAnnotationImplTraverseSyntheticAnnotation;
        ?? r1;
        SchemaGrammar schemaGrammar2;
        Object obj;
        QName qName;
        ?? r2;
        XSElementDecl xSElementDecl;
        QName qName2;
        String str2;
        String str3;
        ?? r4;
        String str4;
        short s;
        String strSchemaDocument2SystemId;
        XSElementDecl globalElementDecl;
        XSElementDecl xSElementDecl2;
        String localName;
        String str5;
        XSTypeDefinition xSTypeDefinition;
        String localName2;
        Object objTraverseLocal;
        XSAnnotationImpl xSAnnotationImplTraverseAnnotationDecl;
        Boolean bool = (Boolean) objArr[XSAttributeChecker.ATTIDX_ABSTRACT];
        XInt xInt = (XInt) objArr[XSAttributeChecker.ATTIDX_BLOCK];
        String str6 = (String) objArr[XSAttributeChecker.ATTIDX_DEFAULT];
        XInt xInt2 = (XInt) objArr[XSAttributeChecker.ATTIDX_FINAL];
        String str7 = (String) objArr[XSAttributeChecker.ATTIDX_FIXED];
        XInt xInt3 = (XInt) objArr[XSAttributeChecker.ATTIDX_FORM];
        String str8 = (String) objArr[XSAttributeChecker.ATTIDX_NAME];
        Boolean bool2 = (Boolean) objArr[XSAttributeChecker.ATTIDX_NILLABLE];
        QName qName3 = (QName) objArr[XSAttributeChecker.ATTIDX_SUBSGROUP];
        QName qName4 = (QName) objArr[XSAttributeChecker.ATTIDX_TYPE];
        XSElementDecl elementDecl = this.fSchemaHandler.fDeclPool != null ? this.fSchemaHandler.fDeclPool.getElementDecl() : new XSElementDecl();
        if (str8 != null) {
            elementDecl.fName = this.fSymbolTable.addSymbol(str8);
        }
        if (!z) {
            if (xSObject instanceof XSComplexTypeDecl) {
                elementDecl.setIsLocal((XSComplexTypeDecl) xSObject);
            }
            if (xInt3 == null) {
                str = null;
                if (xSDocumentInfo.fAreLocalElementsQualified) {
                    elementDecl.fTargetNamespace = xSDocumentInfo.fTargetNamespace;
                }
                if (xInt == null) {
                    sShortValue = xSDocumentInfo.fBlockDefault;
                } else {
                    sShortValue = xInt.shortValue();
                }
                elementDecl.fBlock = sShortValue;
                if (xInt2 == null) {
                    sShortValue2 = xSDocumentInfo.fFinalDefault;
                } else {
                    sShortValue2 = xInt2.shortValue();
                }
                elementDecl.fFinal = sShortValue2;
                elementDecl.fBlock = (short) (elementDecl.fBlock & 7);
                elementDecl.fFinal = (short) (elementDecl.fFinal & 3);
                if (bool2.booleanValue()) {
                    elementDecl.setIsNillable();
                }
                if (bool != null && bool.booleanValue()) {
                    elementDecl.setIsAbstract();
                }
                if (str7 != null) {
                    elementDecl.fDefault = new ValidatedInfo();
                    elementDecl.fDefault.normalizedValue = str7;
                    elementDecl.setConstraintType((short) 2);
                    z2 = true;
                } else if (str6 != null) {
                    elementDecl.fDefault = new ValidatedInfo();
                    elementDecl.fDefault.normalizedValue = str6;
                    z2 = true;
                    elementDecl.setConstraintType((short) 1);
                } else {
                    z2 = true;
                    elementDecl.setConstraintType((short) 0);
                }
                if (qName3 != null) {
                    elementDecl.fSubGroup = (XSElementDecl) this.fSchemaHandler.getGlobalDecl(xSDocumentInfo, 3, qName3, element);
                }
                firstChildElement = DOMUtil.getFirstChildElement(element);
                if (firstChildElement == null && DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
                    xSAnnotationImplTraverseAnnotationDecl = traverseAnnotationDecl(firstChildElement, objArr, false, xSDocumentInfo);
                    firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                } else {
                    syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element);
                    if (syntheticAnnotation != null) {
                        xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element, syntheticAnnotation, objArr, false, xSDocumentInfo);
                    } else {
                        r0 = str;
                    }
                }
                if (r0 != 0) {
                    firstChildElement = firstChildElement;
                    r0 = xSAnnotationImplTraverseSyntheticAnnotation;
                    r0 = xSAnnotationImplTraverseAnnotationDecl;
                    ?? xSObjectListImpl = new XSObjectListImpl();
                    xSObjectListImpl.addXSObject(r0);
                    r1 = xSObjectListImpl;
                } else {
                    firstChildElement = firstChildElement;
                    r0 = xSAnnotationImplTraverseSyntheticAnnotation;
                    r0 = xSAnnotationImplTraverseAnnotationDecl;
                    r1 = XSObjectListImpl.EMPTY_LIST;
                }
                elementDecl.fAnnotations = r1;
                if (firstChildElement != null) {
                    localName2 = DOMUtil.getLocalName(firstChildElement);
                    if (!localName2.equals(SchemaSymbols.ELT_COMPLEXTYPE)) {
                        schemaGrammar2 = schemaGrammar;
                        if (localName2.equals(SchemaSymbols.ELT_SIMPLETYPE)) {
                            objTraverseLocal = this.fSchemaHandler.fSimpleTypeTraverser.traverseLocal(firstChildElement, xSDocumentInfo, schemaGrammar2);
                        }
                        if (obj == null || qName4 == null) {
                            qName = qName4;
                            r2 = obj;
                        } else {
                            qName = qName4;
                            xSTypeDefinition = (XSTypeDefinition) this.fSchemaHandler.getGlobalDecl(xSDocumentInfo, 7, qName, element);
                            if (xSTypeDefinition == null) {
                                r2 = xSTypeDefinition;
                                elementDecl.fUnresolvedTypeName = qName;
                                r2 = xSTypeDefinition;
                            }
                        }
                        if (r2 == 0 && elementDecl.fSubGroup != null) {
                            r2 = elementDecl.fSubGroup.fType;
                        }
                        if (r2 == 0) {
                            r2 = SchemaGrammar.fAnyType;
                        }
                        elementDecl.fType = r2;
                        if (firstChildElement != null) {
                            localName = DOMUtil.getLocalName(firstChildElement);
                            while (firstChildElement != null && (localName.equals(SchemaSymbols.ELT_KEY) || localName.equals(SchemaSymbols.ELT_KEYREF) || localName.equals(SchemaSymbols.ELT_UNIQUE))) {
                                if (!localName.equals(SchemaSymbols.ELT_KEY) || localName.equals(SchemaSymbols.ELT_UNIQUE)) {
                                    DOMUtil.setHidden(firstChildElement, this.fSchemaHandler.fHiddenNodes);
                                    this.fSchemaHandler.fUniqueOrKeyTraverser.traverse(firstChildElement, elementDecl, xSDocumentInfo, schemaGrammar2);
                                    if (DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME).length() != 0) {
                                        XSDHandler xSDHandler = this.fSchemaHandler;
                                        if (xSDocumentInfo.fTargetNamespace == null) {
                                            str5 = "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                        } else {
                                            str5 = xSDocumentInfo.fTargetNamespace + "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                        }
                                        str = null;
                                        xSDHandler.checkForDuplicateNames(str5, 1, this.fSchemaHandler.getIDRegistry(), this.fSchemaHandler.getIDRegistry_sub(), firstChildElement, xSDocumentInfo);
                                    }
                                    firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                                    if (firstChildElement != null) {
                                        localName = DOMUtil.getLocalName(firstChildElement);
                                    } else {
                                        localName = localName;
                                    }
                                    schemaGrammar2 = schemaGrammar;
                                } else if (localName.equals(SchemaSymbols.ELT_KEYREF)) {
                                    this.fSchemaHandler.storeKeyRef(firstChildElement, xSDocumentInfo, elementDecl);
                                }
                                str = str;
                                firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                                if (firstChildElement != null) {
                                    localName = DOMUtil.getLocalName(firstChildElement);
                                } else {
                                    localName = localName;
                                }
                                schemaGrammar2 = schemaGrammar;
                            }
                        }
                        QName qName5 = qName;
                        xSElementDecl = elementDecl;
                        qName2 = qName3;
                        str2 = str8;
                        String str9 = str7;
                        str3 = str6;
                        r4 = str;
                        if (str2 == null) {
                            if (z) {
                                reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_ELEMENT, SchemaSymbols.ATT_NAME}, element);
                            } else {
                                reportSchemaError("src-element.2.1", r4, element);
                            }
                            str4 = "(no name)";
                        } else {
                            str4 = str2;
                        }
                        if (firstChildElement != null) {
                            reportSchemaError("s4s-elt-must-match.1", new Object[]{str4, "(annotation?, (simpleType | complexType)?, (unique | key | keyref)*))", DOMUtil.getLocalName(firstChildElement)}, firstChildElement);
                        }
                        if (str3 != null && str9 != null) {
                            reportSchemaError("src-element.1", new Object[]{str4}, element);
                        }
                        if (z2 && qName5 != null) {
                            reportSchemaError("src-element.3", new Object[]{str4}, element);
                        }
                        checkNotationType(str4, r2, element);
                        if (xSElementDecl.fDefault != null) {
                            this.fValidationState.setNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
                            if (XSConstraints.ElementDefaultValidImmediate(xSElementDecl.fType, xSElementDecl.fDefault.normalizedValue, this.fValidationState, xSElementDecl.fDefault) == null) {
                                reportSchemaError("e-props-correct.2", new Object[]{str4, xSElementDecl.fDefault.normalizedValue}, element);
                                xSElementDecl.fDefault = r4;
                                s = 0;
                                xSElementDecl.setConstraintType((short) 0);
                            } else {
                                s = 0;
                            }
                        } else {
                            s = 0;
                        }
                        if (xSElementDecl.fSubGroup != null && !XSConstraints.checkTypeDerivationOk(xSElementDecl.fType, xSElementDecl.fSubGroup.fType, xSElementDecl.fSubGroup.fFinal)) {
                            reportSchemaError("e-props-correct.4", new Object[]{str4, qName2.prefix + ":" + qName2.localpart}, element);
                            xSElementDecl.fSubGroup = r4;
                        }
                        if (xSElementDecl.fDefault != null && ((r2.getTypeCategory() == 16 && ((XSSimpleType) r2).isIDType()) || (r2.getTypeCategory() == 15 && ((XSComplexTypeDecl) r2).containsTypeID()))) {
                            reportSchemaError("e-props-correct.5", new Object[]{xSElementDecl.fName}, element);
                            xSElementDecl.fDefault = r4;
                            xSElementDecl.setConstraintType(s);
                        }
                        if (xSElementDecl.fName == null) {
                            return r4;
                        }
                        if (z) {
                            schemaGrammar.addGlobalElementDeclAll(xSElementDecl);
                            if (schemaGrammar.getGlobalElementDecl(xSElementDecl.fName) == null) {
                                schemaGrammar.addGlobalElementDecl(xSElementDecl);
                            }
                            strSchemaDocument2SystemId = this.fSchemaHandler.schemaDocument2SystemId(xSDocumentInfo);
                            globalElementDecl = schemaGrammar.getGlobalElementDecl(xSElementDecl.fName, strSchemaDocument2SystemId);
                            if (globalElementDecl == null) {
                                schemaGrammar.addGlobalElementDecl(xSElementDecl, strSchemaDocument2SystemId);
                            }
                            if (this.fSchemaHandler.fTolerateDuplicates) {
                                if (globalElementDecl != null) {
                                    xSElementDecl2 = globalElementDecl;
                                } else {
                                    xSElementDecl2 = xSElementDecl;
                                }
                                this.fSchemaHandler.addGlobalElementDecl(xSElementDecl2);
                                return xSElementDecl2;
                            }
                        }
                        return xSElementDecl;
                    }
                    schemaGrammar2 = schemaGrammar;
                    objTraverseLocal = this.fSchemaHandler.fComplexTypeTraverser.traverseLocal(firstChildElement, xSDocumentInfo, schemaGrammar2);
                    firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                    obj = objTraverseLocal;
                    if (obj == null) {
                        qName = qName4;
                        r2 = obj;
                    } else {
                        qName = qName4;
                        r2 = obj;
                    }
                    if (r2 == 0) {
                        r2 = elementDecl.fSubGroup.fType;
                    }
                    if (r2 == 0) {
                        r2 = SchemaGrammar.fAnyType;
                    }
                    elementDecl.fType = r2;
                    if (firstChildElement != null) {
                        localName = DOMUtil.getLocalName(firstChildElement);
                        while (firstChildElement != null) {
                            if (localName.equals(SchemaSymbols.ELT_KEY)) {
                                DOMUtil.setHidden(firstChildElement, this.fSchemaHandler.fHiddenNodes);
                                this.fSchemaHandler.fUniqueOrKeyTraverser.traverse(firstChildElement, elementDecl, xSDocumentInfo, schemaGrammar2);
                                if (DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME).length() != 0) {
                                    XSDHandler xSDHandler2 = this.fSchemaHandler;
                                    if (xSDocumentInfo.fTargetNamespace == null) {
                                        str5 = "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                    } else {
                                        str5 = xSDocumentInfo.fTargetNamespace + "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                    }
                                    str = null;
                                    xSDHandler2.checkForDuplicateNames(str5, 1, this.fSchemaHandler.getIDRegistry(), this.fSchemaHandler.getIDRegistry_sub(), firstChildElement, xSDocumentInfo);
                                } else {
                                    str = str;
                                }
                            } else {
                                DOMUtil.setHidden(firstChildElement, this.fSchemaHandler.fHiddenNodes);
                                this.fSchemaHandler.fUniqueOrKeyTraverser.traverse(firstChildElement, elementDecl, xSDocumentInfo, schemaGrammar2);
                                if (DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME).length() != 0) {
                                    XSDHandler xSDHandler3 = this.fSchemaHandler;
                                    if (xSDocumentInfo.fTargetNamespace == null) {
                                        str5 = "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                    } else {
                                        str5 = xSDocumentInfo.fTargetNamespace + "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                    }
                                    str = null;
                                    xSDHandler3.checkForDuplicateNames(str5, 1, this.fSchemaHandler.getIDRegistry(), this.fSchemaHandler.getIDRegistry_sub(), firstChildElement, xSDocumentInfo);
                                } else {
                                    str = str;
                                }
                            }
                            firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                            if (firstChildElement != null) {
                                localName = DOMUtil.getLocalName(firstChildElement);
                            } else {
                                localName = localName;
                            }
                            schemaGrammar2 = schemaGrammar;
                        }
                    }
                    QName qName6 = qName;
                    xSElementDecl = elementDecl;
                    qName2 = qName3;
                    str2 = str8;
                    String str10 = str7;
                    str3 = str6;
                    r4 = str;
                    if (str2 == null) {
                        if (z) {
                            reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_ELEMENT, SchemaSymbols.ATT_NAME}, element);
                        } else {
                            reportSchemaError("src-element.2.1", r4, element);
                        }
                        str4 = "(no name)";
                    } else {
                        str4 = str2;
                    }
                    if (firstChildElement != null) {
                        reportSchemaError("s4s-elt-must-match.1", new Object[]{str4, "(annotation?, (simpleType | complexType)?, (unique | key | keyref)*))", DOMUtil.getLocalName(firstChildElement)}, firstChildElement);
                    }
                    if (str3 != null) {
                        reportSchemaError("src-element.1", new Object[]{str4}, element);
                    }
                    if (z2) {
                        reportSchemaError("src-element.3", new Object[]{str4}, element);
                    }
                    checkNotationType(str4, r2, element);
                    if (xSElementDecl.fDefault != null) {
                        this.fValidationState.setNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
                        if (XSConstraints.ElementDefaultValidImmediate(xSElementDecl.fType, xSElementDecl.fDefault.normalizedValue, this.fValidationState, xSElementDecl.fDefault) == null) {
                            reportSchemaError("e-props-correct.2", new Object[]{str4, xSElementDecl.fDefault.normalizedValue}, element);
                            xSElementDecl.fDefault = r4;
                            s = 0;
                            xSElementDecl.setConstraintType((short) 0);
                        } else {
                            s = 0;
                        }
                    } else {
                        s = 0;
                    }
                    if (xSElementDecl.fSubGroup != null) {
                        reportSchemaError("e-props-correct.4", new Object[]{str4, qName2.prefix + ":" + qName2.localpart}, element);
                        xSElementDecl.fSubGroup = r4;
                    }
                    if (xSElementDecl.fDefault != null) {
                        reportSchemaError("e-props-correct.5", new Object[]{xSElementDecl.fName}, element);
                        xSElementDecl.fDefault = r4;
                        xSElementDecl.setConstraintType(s);
                    }
                    if (xSElementDecl.fName == null) {
                        return r4;
                    }
                    if (z) {
                        schemaGrammar.addGlobalElementDeclAll(xSElementDecl);
                        if (schemaGrammar.getGlobalElementDecl(xSElementDecl.fName) == null) {
                            schemaGrammar.addGlobalElementDecl(xSElementDecl);
                        }
                        strSchemaDocument2SystemId = this.fSchemaHandler.schemaDocument2SystemId(xSDocumentInfo);
                        globalElementDecl = schemaGrammar.getGlobalElementDecl(xSElementDecl.fName, strSchemaDocument2SystemId);
                        if (globalElementDecl == null) {
                            schemaGrammar.addGlobalElementDecl(xSElementDecl, strSchemaDocument2SystemId);
                        }
                        if (this.fSchemaHandler.fTolerateDuplicates) {
                            if (globalElementDecl != null) {
                                xSElementDecl2 = globalElementDecl;
                            } else {
                                xSElementDecl2 = xSElementDecl;
                            }
                            this.fSchemaHandler.addGlobalElementDecl(xSElementDecl2);
                            return xSElementDecl2;
                        }
                    }
                    return xSElementDecl;
                }
                schemaGrammar2 = schemaGrammar;
                obj = str;
                z2 = false;
                if (obj == null) {
                    qName = qName4;
                    r2 = obj;
                } else {
                    qName = qName4;
                    r2 = obj;
                }
                if (r2 == 0) {
                    r2 = elementDecl.fSubGroup.fType;
                }
                if (r2 == 0) {
                    r2 = SchemaGrammar.fAnyType;
                }
                elementDecl.fType = r2;
                if (firstChildElement != null) {
                    localName = DOMUtil.getLocalName(firstChildElement);
                    while (firstChildElement != null) {
                        if (localName.equals(SchemaSymbols.ELT_KEY)) {
                            DOMUtil.setHidden(firstChildElement, this.fSchemaHandler.fHiddenNodes);
                            this.fSchemaHandler.fUniqueOrKeyTraverser.traverse(firstChildElement, elementDecl, xSDocumentInfo, schemaGrammar2);
                            if (DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME).length() != 0) {
                                XSDHandler xSDHandler4 = this.fSchemaHandler;
                                if (xSDocumentInfo.fTargetNamespace == null) {
                                    str5 = "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                } else {
                                    str5 = xSDocumentInfo.fTargetNamespace + "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                }
                                str = null;
                                xSDHandler4.checkForDuplicateNames(str5, 1, this.fSchemaHandler.getIDRegistry(), this.fSchemaHandler.getIDRegistry_sub(), firstChildElement, xSDocumentInfo);
                            } else {
                                str = str;
                            }
                        } else {
                            DOMUtil.setHidden(firstChildElement, this.fSchemaHandler.fHiddenNodes);
                            this.fSchemaHandler.fUniqueOrKeyTraverser.traverse(firstChildElement, elementDecl, xSDocumentInfo, schemaGrammar2);
                            if (DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME).length() != 0) {
                                XSDHandler xSDHandler5 = this.fSchemaHandler;
                                if (xSDocumentInfo.fTargetNamespace == null) {
                                    str5 = "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                } else {
                                    str5 = xSDocumentInfo.fTargetNamespace + "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                }
                                str = null;
                                xSDHandler5.checkForDuplicateNames(str5, 1, this.fSchemaHandler.getIDRegistry(), this.fSchemaHandler.getIDRegistry_sub(), firstChildElement, xSDocumentInfo);
                            } else {
                                str = str;
                            }
                        }
                        firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                        if (firstChildElement != null) {
                            localName = DOMUtil.getLocalName(firstChildElement);
                        } else {
                            localName = localName;
                        }
                        schemaGrammar2 = schemaGrammar;
                    }
                }
                QName qName7 = qName;
                xSElementDecl = elementDecl;
                qName2 = qName3;
                str2 = str8;
                String str11 = str7;
                str3 = str6;
                r4 = str;
                if (str2 == null) {
                    if (z) {
                        reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_ELEMENT, SchemaSymbols.ATT_NAME}, element);
                    } else {
                        reportSchemaError("src-element.2.1", r4, element);
                    }
                    str4 = "(no name)";
                } else {
                    str4 = str2;
                }
                if (firstChildElement != null) {
                    reportSchemaError("s4s-elt-must-match.1", new Object[]{str4, "(annotation?, (simpleType | complexType)?, (unique | key | keyref)*))", DOMUtil.getLocalName(firstChildElement)}, firstChildElement);
                }
                if (str3 != null) {
                    reportSchemaError("src-element.1", new Object[]{str4}, element);
                }
                if (z2) {
                    reportSchemaError("src-element.3", new Object[]{str4}, element);
                }
                checkNotationType(str4, r2, element);
                if (xSElementDecl.fDefault != null) {
                    this.fValidationState.setNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
                    if (XSConstraints.ElementDefaultValidImmediate(xSElementDecl.fType, xSElementDecl.fDefault.normalizedValue, this.fValidationState, xSElementDecl.fDefault) == null) {
                        reportSchemaError("e-props-correct.2", new Object[]{str4, xSElementDecl.fDefault.normalizedValue}, element);
                        xSElementDecl.fDefault = r4;
                        s = 0;
                        xSElementDecl.setConstraintType((short) 0);
                    } else {
                        s = 0;
                    }
                } else {
                    s = 0;
                }
                if (xSElementDecl.fSubGroup != null) {
                    reportSchemaError("e-props-correct.4", new Object[]{str4, qName2.prefix + ":" + qName2.localpart}, element);
                    xSElementDecl.fSubGroup = r4;
                }
                if (xSElementDecl.fDefault != null) {
                    reportSchemaError("e-props-correct.5", new Object[]{xSElementDecl.fName}, element);
                    xSElementDecl.fDefault = r4;
                    xSElementDecl.setConstraintType(s);
                }
                if (xSElementDecl.fName == null) {
                    return r4;
                }
                if (z) {
                    schemaGrammar.addGlobalElementDeclAll(xSElementDecl);
                    if (schemaGrammar.getGlobalElementDecl(xSElementDecl.fName) == null) {
                        schemaGrammar.addGlobalElementDecl(xSElementDecl);
                    }
                    strSchemaDocument2SystemId = this.fSchemaHandler.schemaDocument2SystemId(xSDocumentInfo);
                    globalElementDecl = schemaGrammar.getGlobalElementDecl(xSElementDecl.fName, strSchemaDocument2SystemId);
                    if (globalElementDecl == null) {
                        schemaGrammar.addGlobalElementDecl(xSElementDecl, strSchemaDocument2SystemId);
                    }
                    if (this.fSchemaHandler.fTolerateDuplicates) {
                        if (globalElementDecl != null) {
                            xSElementDecl2 = globalElementDecl;
                        } else {
                            xSElementDecl2 = xSElementDecl;
                        }
                        this.fSchemaHandler.addGlobalElementDecl(xSElementDecl2);
                        return xSElementDecl2;
                    }
                }
                return xSElementDecl;
            }
            if (xInt3.intValue() == 1) {
                elementDecl.fTargetNamespace = xSDocumentInfo.fTargetNamespace;
            } else {
                str = null;
            }
            elementDecl.fTargetNamespace = str;
            if (xInt == null) {
                sShortValue = xSDocumentInfo.fBlockDefault;
            } else {
                sShortValue = xInt.shortValue();
            }
            elementDecl.fBlock = sShortValue;
            if (xInt2 == null) {
                sShortValue2 = xSDocumentInfo.fFinalDefault;
            } else {
                sShortValue2 = xInt2.shortValue();
            }
            elementDecl.fFinal = sShortValue2;
            elementDecl.fBlock = (short) (elementDecl.fBlock & 7);
            elementDecl.fFinal = (short) (elementDecl.fFinal & 3);
            if (bool2.booleanValue()) {
                elementDecl.setIsNillable();
            }
            if (bool != null) {
                elementDecl.setIsAbstract();
            }
            if (str7 != null) {
                elementDecl.fDefault = new ValidatedInfo();
                elementDecl.fDefault.normalizedValue = str7;
                elementDecl.setConstraintType((short) 2);
                z2 = true;
            } else if (str6 != null) {
                elementDecl.fDefault = new ValidatedInfo();
                elementDecl.fDefault.normalizedValue = str6;
                z2 = true;
                elementDecl.setConstraintType((short) 1);
            } else {
                z2 = true;
                elementDecl.setConstraintType((short) 0);
            }
            if (qName3 != null) {
                elementDecl.fSubGroup = (XSElementDecl) this.fSchemaHandler.getGlobalDecl(xSDocumentInfo, 3, qName3, element);
            }
            firstChildElement = DOMUtil.getFirstChildElement(element);
            if (firstChildElement == null) {
                syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element);
                if (syntheticAnnotation != null) {
                    xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element, syntheticAnnotation, objArr, false, xSDocumentInfo);
                } else {
                    r0 = str;
                }
            } else {
                syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element);
                if (syntheticAnnotation != null) {
                    xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element, syntheticAnnotation, objArr, false, xSDocumentInfo);
                } else {
                    r0 = str;
                }
            }
            if (r0 != 0) {
                firstChildElement = firstChildElement;
                r0 = xSAnnotationImplTraverseSyntheticAnnotation;
                r0 = xSAnnotationImplTraverseAnnotationDecl;
                ?? xSObjectListImpl2 = new XSObjectListImpl();
                xSObjectListImpl2.addXSObject(r0);
                r1 = xSObjectListImpl2;
            } else {
                firstChildElement = firstChildElement;
                r0 = xSAnnotationImplTraverseSyntheticAnnotation;
                r0 = xSAnnotationImplTraverseAnnotationDecl;
                r1 = XSObjectListImpl.EMPTY_LIST;
            }
            elementDecl.fAnnotations = r1;
            if (firstChildElement != null) {
                localName2 = DOMUtil.getLocalName(firstChildElement);
                if (!localName2.equals(SchemaSymbols.ELT_COMPLEXTYPE)) {
                    schemaGrammar2 = schemaGrammar;
                    if (localName2.equals(SchemaSymbols.ELT_SIMPLETYPE)) {
                        objTraverseLocal = this.fSchemaHandler.fSimpleTypeTraverser.traverseLocal(firstChildElement, xSDocumentInfo, schemaGrammar2);
                    }
                    if (obj == null) {
                        qName = qName4;
                        r2 = obj;
                    } else {
                        qName = qName4;
                        r2 = obj;
                    }
                    if (r2 == 0) {
                        r2 = elementDecl.fSubGroup.fType;
                    }
                    if (r2 == 0) {
                        r2 = SchemaGrammar.fAnyType;
                    }
                    elementDecl.fType = r2;
                    if (firstChildElement != null) {
                        localName = DOMUtil.getLocalName(firstChildElement);
                        while (firstChildElement != null) {
                            if (localName.equals(SchemaSymbols.ELT_KEY)) {
                                DOMUtil.setHidden(firstChildElement, this.fSchemaHandler.fHiddenNodes);
                                this.fSchemaHandler.fUniqueOrKeyTraverser.traverse(firstChildElement, elementDecl, xSDocumentInfo, schemaGrammar2);
                                if (DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME).length() != 0) {
                                    XSDHandler xSDHandler6 = this.fSchemaHandler;
                                    if (xSDocumentInfo.fTargetNamespace == null) {
                                        str5 = "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                    } else {
                                        str5 = xSDocumentInfo.fTargetNamespace + "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                    }
                                    str = null;
                                    xSDHandler6.checkForDuplicateNames(str5, 1, this.fSchemaHandler.getIDRegistry(), this.fSchemaHandler.getIDRegistry_sub(), firstChildElement, xSDocumentInfo);
                                } else {
                                    str = str;
                                }
                            } else {
                                DOMUtil.setHidden(firstChildElement, this.fSchemaHandler.fHiddenNodes);
                                this.fSchemaHandler.fUniqueOrKeyTraverser.traverse(firstChildElement, elementDecl, xSDocumentInfo, schemaGrammar2);
                                if (DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME).length() != 0) {
                                    XSDHandler xSDHandler7 = this.fSchemaHandler;
                                    if (xSDocumentInfo.fTargetNamespace == null) {
                                        str5 = "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                    } else {
                                        str5 = xSDocumentInfo.fTargetNamespace + "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                    }
                                    str = null;
                                    xSDHandler7.checkForDuplicateNames(str5, 1, this.fSchemaHandler.getIDRegistry(), this.fSchemaHandler.getIDRegistry_sub(), firstChildElement, xSDocumentInfo);
                                } else {
                                    str = str;
                                }
                            }
                            firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                            if (firstChildElement != null) {
                                localName = DOMUtil.getLocalName(firstChildElement);
                            } else {
                                localName = localName;
                            }
                            schemaGrammar2 = schemaGrammar;
                        }
                    }
                    QName qName8 = qName;
                    xSElementDecl = elementDecl;
                    qName2 = qName3;
                    str2 = str8;
                    String str12 = str7;
                    str3 = str6;
                    r4 = str;
                    if (str2 == null) {
                        if (z) {
                            reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_ELEMENT, SchemaSymbols.ATT_NAME}, element);
                        } else {
                            reportSchemaError("src-element.2.1", r4, element);
                        }
                        str4 = "(no name)";
                    } else {
                        str4 = str2;
                    }
                    if (firstChildElement != null) {
                        reportSchemaError("s4s-elt-must-match.1", new Object[]{str4, "(annotation?, (simpleType | complexType)?, (unique | key | keyref)*))", DOMUtil.getLocalName(firstChildElement)}, firstChildElement);
                    }
                    if (str3 != null) {
                        reportSchemaError("src-element.1", new Object[]{str4}, element);
                    }
                    if (z2) {
                        reportSchemaError("src-element.3", new Object[]{str4}, element);
                    }
                    checkNotationType(str4, r2, element);
                    if (xSElementDecl.fDefault != null) {
                        this.fValidationState.setNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
                        if (XSConstraints.ElementDefaultValidImmediate(xSElementDecl.fType, xSElementDecl.fDefault.normalizedValue, this.fValidationState, xSElementDecl.fDefault) == null) {
                            reportSchemaError("e-props-correct.2", new Object[]{str4, xSElementDecl.fDefault.normalizedValue}, element);
                            xSElementDecl.fDefault = r4;
                            s = 0;
                            xSElementDecl.setConstraintType((short) 0);
                        } else {
                            s = 0;
                        }
                    } else {
                        s = 0;
                    }
                    if (xSElementDecl.fSubGroup != null) {
                        reportSchemaError("e-props-correct.4", new Object[]{str4, qName2.prefix + ":" + qName2.localpart}, element);
                        xSElementDecl.fSubGroup = r4;
                    }
                    if (xSElementDecl.fDefault != null) {
                        reportSchemaError("e-props-correct.5", new Object[]{xSElementDecl.fName}, element);
                        xSElementDecl.fDefault = r4;
                        xSElementDecl.setConstraintType(s);
                    }
                    if (xSElementDecl.fName == null) {
                        return r4;
                    }
                    if (z) {
                        schemaGrammar.addGlobalElementDeclAll(xSElementDecl);
                        if (schemaGrammar.getGlobalElementDecl(xSElementDecl.fName) == null) {
                            schemaGrammar.addGlobalElementDecl(xSElementDecl);
                        }
                        strSchemaDocument2SystemId = this.fSchemaHandler.schemaDocument2SystemId(xSDocumentInfo);
                        globalElementDecl = schemaGrammar.getGlobalElementDecl(xSElementDecl.fName, strSchemaDocument2SystemId);
                        if (globalElementDecl == null) {
                            schemaGrammar.addGlobalElementDecl(xSElementDecl, strSchemaDocument2SystemId);
                        }
                        if (this.fSchemaHandler.fTolerateDuplicates) {
                            if (globalElementDecl != null) {
                                xSElementDecl2 = globalElementDecl;
                            } else {
                                xSElementDecl2 = xSElementDecl;
                            }
                            this.fSchemaHandler.addGlobalElementDecl(xSElementDecl2);
                            return xSElementDecl2;
                        }
                    }
                    return xSElementDecl;
                }
                schemaGrammar2 = schemaGrammar;
                objTraverseLocal = this.fSchemaHandler.fComplexTypeTraverser.traverseLocal(firstChildElement, xSDocumentInfo, schemaGrammar2);
                firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                obj = objTraverseLocal;
                if (obj == null) {
                    qName = qName4;
                    r2 = obj;
                } else {
                    qName = qName4;
                    r2 = obj;
                }
                if (r2 == 0) {
                    r2 = elementDecl.fSubGroup.fType;
                }
                if (r2 == 0) {
                    r2 = SchemaGrammar.fAnyType;
                }
                elementDecl.fType = r2;
                if (firstChildElement != null) {
                    localName = DOMUtil.getLocalName(firstChildElement);
                    while (firstChildElement != null) {
                        if (localName.equals(SchemaSymbols.ELT_KEY)) {
                            DOMUtil.setHidden(firstChildElement, this.fSchemaHandler.fHiddenNodes);
                            this.fSchemaHandler.fUniqueOrKeyTraverser.traverse(firstChildElement, elementDecl, xSDocumentInfo, schemaGrammar2);
                            if (DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME).length() != 0) {
                                XSDHandler xSDHandler8 = this.fSchemaHandler;
                                if (xSDocumentInfo.fTargetNamespace == null) {
                                    str5 = "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                } else {
                                    str5 = xSDocumentInfo.fTargetNamespace + "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                }
                                str = null;
                                xSDHandler8.checkForDuplicateNames(str5, 1, this.fSchemaHandler.getIDRegistry(), this.fSchemaHandler.getIDRegistry_sub(), firstChildElement, xSDocumentInfo);
                            } else {
                                str = str;
                            }
                        } else {
                            DOMUtil.setHidden(firstChildElement, this.fSchemaHandler.fHiddenNodes);
                            this.fSchemaHandler.fUniqueOrKeyTraverser.traverse(firstChildElement, elementDecl, xSDocumentInfo, schemaGrammar2);
                            if (DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME).length() != 0) {
                                XSDHandler xSDHandler9 = this.fSchemaHandler;
                                if (xSDocumentInfo.fTargetNamespace == null) {
                                    str5 = "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                } else {
                                    str5 = xSDocumentInfo.fTargetNamespace + "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                }
                                str = null;
                                xSDHandler9.checkForDuplicateNames(str5, 1, this.fSchemaHandler.getIDRegistry(), this.fSchemaHandler.getIDRegistry_sub(), firstChildElement, xSDocumentInfo);
                            } else {
                                str = str;
                            }
                        }
                        firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                        if (firstChildElement != null) {
                            localName = DOMUtil.getLocalName(firstChildElement);
                        } else {
                            localName = localName;
                        }
                        schemaGrammar2 = schemaGrammar;
                    }
                }
                QName qName9 = qName;
                xSElementDecl = elementDecl;
                qName2 = qName3;
                str2 = str8;
                String str13 = str7;
                str3 = str6;
                r4 = str;
                if (str2 == null) {
                    if (z) {
                        reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_ELEMENT, SchemaSymbols.ATT_NAME}, element);
                    } else {
                        reportSchemaError("src-element.2.1", r4, element);
                    }
                    str4 = "(no name)";
                } else {
                    str4 = str2;
                }
                if (firstChildElement != null) {
                    reportSchemaError("s4s-elt-must-match.1", new Object[]{str4, "(annotation?, (simpleType | complexType)?, (unique | key | keyref)*))", DOMUtil.getLocalName(firstChildElement)}, firstChildElement);
                }
                if (str3 != null) {
                    reportSchemaError("src-element.1", new Object[]{str4}, element);
                }
                if (z2) {
                    reportSchemaError("src-element.3", new Object[]{str4}, element);
                }
                checkNotationType(str4, r2, element);
                if (xSElementDecl.fDefault != null) {
                    this.fValidationState.setNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
                    if (XSConstraints.ElementDefaultValidImmediate(xSElementDecl.fType, xSElementDecl.fDefault.normalizedValue, this.fValidationState, xSElementDecl.fDefault) == null) {
                        reportSchemaError("e-props-correct.2", new Object[]{str4, xSElementDecl.fDefault.normalizedValue}, element);
                        xSElementDecl.fDefault = r4;
                        s = 0;
                        xSElementDecl.setConstraintType((short) 0);
                    } else {
                        s = 0;
                    }
                } else {
                    s = 0;
                }
                if (xSElementDecl.fSubGroup != null) {
                    reportSchemaError("e-props-correct.4", new Object[]{str4, qName2.prefix + ":" + qName2.localpart}, element);
                    xSElementDecl.fSubGroup = r4;
                }
                if (xSElementDecl.fDefault != null) {
                    reportSchemaError("e-props-correct.5", new Object[]{xSElementDecl.fName}, element);
                    xSElementDecl.fDefault = r4;
                    xSElementDecl.setConstraintType(s);
                }
                if (xSElementDecl.fName == null) {
                    return r4;
                }
                if (z) {
                    schemaGrammar.addGlobalElementDeclAll(xSElementDecl);
                    if (schemaGrammar.getGlobalElementDecl(xSElementDecl.fName) == null) {
                        schemaGrammar.addGlobalElementDecl(xSElementDecl);
                    }
                    strSchemaDocument2SystemId = this.fSchemaHandler.schemaDocument2SystemId(xSDocumentInfo);
                    globalElementDecl = schemaGrammar.getGlobalElementDecl(xSElementDecl.fName, strSchemaDocument2SystemId);
                    if (globalElementDecl == null) {
                        schemaGrammar.addGlobalElementDecl(xSElementDecl, strSchemaDocument2SystemId);
                    }
                    if (this.fSchemaHandler.fTolerateDuplicates) {
                        if (globalElementDecl != null) {
                            xSElementDecl2 = globalElementDecl;
                        } else {
                            xSElementDecl2 = xSElementDecl;
                        }
                        this.fSchemaHandler.addGlobalElementDecl(xSElementDecl2);
                        return xSElementDecl2;
                    }
                }
                return xSElementDecl;
            }
            schemaGrammar2 = schemaGrammar;
            obj = str;
            z2 = false;
            if (obj == null) {
                qName = qName4;
                r2 = obj;
            } else {
                qName = qName4;
                r2 = obj;
            }
            if (r2 == 0) {
                r2 = elementDecl.fSubGroup.fType;
            }
            if (r2 == 0) {
                r2 = SchemaGrammar.fAnyType;
            }
            elementDecl.fType = r2;
            if (firstChildElement != null) {
                localName = DOMUtil.getLocalName(firstChildElement);
                while (firstChildElement != null) {
                    if (localName.equals(SchemaSymbols.ELT_KEY)) {
                        DOMUtil.setHidden(firstChildElement, this.fSchemaHandler.fHiddenNodes);
                        this.fSchemaHandler.fUniqueOrKeyTraverser.traverse(firstChildElement, elementDecl, xSDocumentInfo, schemaGrammar2);
                        if (DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME).length() != 0) {
                            XSDHandler xSDHandler10 = this.fSchemaHandler;
                            if (xSDocumentInfo.fTargetNamespace == null) {
                                str5 = "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                            } else {
                                str5 = xSDocumentInfo.fTargetNamespace + "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                            }
                            str = null;
                            xSDHandler10.checkForDuplicateNames(str5, 1, this.fSchemaHandler.getIDRegistry(), this.fSchemaHandler.getIDRegistry_sub(), firstChildElement, xSDocumentInfo);
                        } else {
                            str = str;
                        }
                    } else {
                        DOMUtil.setHidden(firstChildElement, this.fSchemaHandler.fHiddenNodes);
                        this.fSchemaHandler.fUniqueOrKeyTraverser.traverse(firstChildElement, elementDecl, xSDocumentInfo, schemaGrammar2);
                        if (DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME).length() != 0) {
                            XSDHandler xSDHandler11 = this.fSchemaHandler;
                            if (xSDocumentInfo.fTargetNamespace == null) {
                                str5 = "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                            } else {
                                str5 = xSDocumentInfo.fTargetNamespace + "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                            }
                            str = null;
                            xSDHandler11.checkForDuplicateNames(str5, 1, this.fSchemaHandler.getIDRegistry(), this.fSchemaHandler.getIDRegistry_sub(), firstChildElement, xSDocumentInfo);
                        } else {
                            str = str;
                        }
                    }
                    firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                    if (firstChildElement != null) {
                        localName = DOMUtil.getLocalName(firstChildElement);
                    } else {
                        localName = localName;
                    }
                    schemaGrammar2 = schemaGrammar;
                }
            }
            QName qName10 = qName;
            xSElementDecl = elementDecl;
            qName2 = qName3;
            str2 = str8;
            String str14 = str7;
            str3 = str6;
            r4 = str;
            if (str2 == null) {
                if (z) {
                    reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_ELEMENT, SchemaSymbols.ATT_NAME}, element);
                } else {
                    reportSchemaError("src-element.2.1", r4, element);
                }
                str4 = "(no name)";
            } else {
                str4 = str2;
            }
            if (firstChildElement != null) {
                reportSchemaError("s4s-elt-must-match.1", new Object[]{str4, "(annotation?, (simpleType | complexType)?, (unique | key | keyref)*))", DOMUtil.getLocalName(firstChildElement)}, firstChildElement);
            }
            if (str3 != null) {
                reportSchemaError("src-element.1", new Object[]{str4}, element);
            }
            if (z2) {
                reportSchemaError("src-element.3", new Object[]{str4}, element);
            }
            checkNotationType(str4, r2, element);
            if (xSElementDecl.fDefault != null) {
                this.fValidationState.setNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
                if (XSConstraints.ElementDefaultValidImmediate(xSElementDecl.fType, xSElementDecl.fDefault.normalizedValue, this.fValidationState, xSElementDecl.fDefault) == null) {
                    reportSchemaError("e-props-correct.2", new Object[]{str4, xSElementDecl.fDefault.normalizedValue}, element);
                    xSElementDecl.fDefault = r4;
                    s = 0;
                    xSElementDecl.setConstraintType((short) 0);
                } else {
                    s = 0;
                }
            } else {
                s = 0;
            }
            if (xSElementDecl.fSubGroup != null) {
                reportSchemaError("e-props-correct.4", new Object[]{str4, qName2.prefix + ":" + qName2.localpart}, element);
                xSElementDecl.fSubGroup = r4;
            }
            if (xSElementDecl.fDefault != null) {
                reportSchemaError("e-props-correct.5", new Object[]{xSElementDecl.fName}, element);
                xSElementDecl.fDefault = r4;
                xSElementDecl.setConstraintType(s);
            }
            if (xSElementDecl.fName == null) {
                return r4;
            }
            if (z) {
                schemaGrammar.addGlobalElementDeclAll(xSElementDecl);
                if (schemaGrammar.getGlobalElementDecl(xSElementDecl.fName) == null) {
                    schemaGrammar.addGlobalElementDecl(xSElementDecl);
                }
                strSchemaDocument2SystemId = this.fSchemaHandler.schemaDocument2SystemId(xSDocumentInfo);
                globalElementDecl = schemaGrammar.getGlobalElementDecl(xSElementDecl.fName, strSchemaDocument2SystemId);
                if (globalElementDecl == null) {
                    schemaGrammar.addGlobalElementDecl(xSElementDecl, strSchemaDocument2SystemId);
                }
                if (this.fSchemaHandler.fTolerateDuplicates) {
                    if (globalElementDecl != null) {
                        xSElementDecl2 = globalElementDecl;
                    } else {
                        xSElementDecl2 = xSElementDecl;
                    }
                    this.fSchemaHandler.addGlobalElementDecl(xSElementDecl2);
                    return xSElementDecl2;
                }
            }
            return xSElementDecl;
        }
        elementDecl.fTargetNamespace = xSDocumentInfo.fTargetNamespace;
        elementDecl.setIsGlobal();
        str = null;
        if (xInt == null) {
            sShortValue = xSDocumentInfo.fBlockDefault;
        } else {
            sShortValue = xInt.shortValue();
        }
        elementDecl.fBlock = sShortValue;
        if (xInt2 == null) {
            sShortValue2 = xSDocumentInfo.fFinalDefault;
        } else {
            sShortValue2 = xInt2.shortValue();
        }
        elementDecl.fFinal = sShortValue2;
        elementDecl.fBlock = (short) (elementDecl.fBlock & 7);
        elementDecl.fFinal = (short) (elementDecl.fFinal & 3);
        if (bool2.booleanValue()) {
            elementDecl.setIsNillable();
        }
        if (bool != null) {
            elementDecl.setIsAbstract();
        }
        if (str7 != null) {
            elementDecl.fDefault = new ValidatedInfo();
            elementDecl.fDefault.normalizedValue = str7;
            elementDecl.setConstraintType((short) 2);
            z2 = true;
        } else if (str6 != null) {
            elementDecl.fDefault = new ValidatedInfo();
            elementDecl.fDefault.normalizedValue = str6;
            z2 = true;
            elementDecl.setConstraintType((short) 1);
        } else {
            z2 = true;
            elementDecl.setConstraintType((short) 0);
        }
        if (qName3 != null) {
            elementDecl.fSubGroup = (XSElementDecl) this.fSchemaHandler.getGlobalDecl(xSDocumentInfo, 3, qName3, element);
        }
        firstChildElement = DOMUtil.getFirstChildElement(element);
        if (firstChildElement == null) {
            syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element);
            if (syntheticAnnotation != null) {
                xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element, syntheticAnnotation, objArr, false, xSDocumentInfo);
            } else {
                r0 = str;
            }
        } else {
            syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element);
            if (syntheticAnnotation != null) {
                xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element, syntheticAnnotation, objArr, false, xSDocumentInfo);
            } else {
                r0 = str;
            }
        }
        if (r0 != 0) {
            firstChildElement = firstChildElement;
            r0 = xSAnnotationImplTraverseSyntheticAnnotation;
            r0 = xSAnnotationImplTraverseAnnotationDecl;
            ?? xSObjectListImpl3 = new XSObjectListImpl();
            xSObjectListImpl3.addXSObject(r0);
            r1 = xSObjectListImpl3;
        } else {
            firstChildElement = firstChildElement;
            r0 = xSAnnotationImplTraverseSyntheticAnnotation;
            r0 = xSAnnotationImplTraverseAnnotationDecl;
            r1 = XSObjectListImpl.EMPTY_LIST;
        }
        elementDecl.fAnnotations = r1;
        if (firstChildElement != null) {
            localName2 = DOMUtil.getLocalName(firstChildElement);
            if (!localName2.equals(SchemaSymbols.ELT_COMPLEXTYPE)) {
                schemaGrammar2 = schemaGrammar;
                if (localName2.equals(SchemaSymbols.ELT_SIMPLETYPE)) {
                    objTraverseLocal = this.fSchemaHandler.fSimpleTypeTraverser.traverseLocal(firstChildElement, xSDocumentInfo, schemaGrammar2);
                }
                if (obj == null) {
                    qName = qName4;
                    r2 = obj;
                } else {
                    qName = qName4;
                    r2 = obj;
                }
                if (r2 == 0) {
                    r2 = elementDecl.fSubGroup.fType;
                }
                if (r2 == 0) {
                    r2 = SchemaGrammar.fAnyType;
                }
                elementDecl.fType = r2;
                if (firstChildElement != null) {
                    localName = DOMUtil.getLocalName(firstChildElement);
                    while (firstChildElement != null) {
                        if (localName.equals(SchemaSymbols.ELT_KEY)) {
                            DOMUtil.setHidden(firstChildElement, this.fSchemaHandler.fHiddenNodes);
                            this.fSchemaHandler.fUniqueOrKeyTraverser.traverse(firstChildElement, elementDecl, xSDocumentInfo, schemaGrammar2);
                            if (DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME).length() != 0) {
                                XSDHandler xSDHandler12 = this.fSchemaHandler;
                                if (xSDocumentInfo.fTargetNamespace == null) {
                                    str5 = "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                } else {
                                    str5 = xSDocumentInfo.fTargetNamespace + "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                }
                                str = null;
                                xSDHandler12.checkForDuplicateNames(str5, 1, this.fSchemaHandler.getIDRegistry(), this.fSchemaHandler.getIDRegistry_sub(), firstChildElement, xSDocumentInfo);
                            } else {
                                str = str;
                            }
                        } else {
                            DOMUtil.setHidden(firstChildElement, this.fSchemaHandler.fHiddenNodes);
                            this.fSchemaHandler.fUniqueOrKeyTraverser.traverse(firstChildElement, elementDecl, xSDocumentInfo, schemaGrammar2);
                            if (DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME).length() != 0) {
                                XSDHandler xSDHandler13 = this.fSchemaHandler;
                                if (xSDocumentInfo.fTargetNamespace == null) {
                                    str5 = "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                } else {
                                    str5 = xSDocumentInfo.fTargetNamespace + "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                                }
                                str = null;
                                xSDHandler13.checkForDuplicateNames(str5, 1, this.fSchemaHandler.getIDRegistry(), this.fSchemaHandler.getIDRegistry_sub(), firstChildElement, xSDocumentInfo);
                            } else {
                                str = str;
                            }
                        }
                        firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                        if (firstChildElement != null) {
                            localName = DOMUtil.getLocalName(firstChildElement);
                        } else {
                            localName = localName;
                        }
                        schemaGrammar2 = schemaGrammar;
                    }
                }
                QName qName11 = qName;
                xSElementDecl = elementDecl;
                qName2 = qName3;
                str2 = str8;
                String str15 = str7;
                str3 = str6;
                r4 = str;
                if (str2 == null) {
                    if (z) {
                        reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_ELEMENT, SchemaSymbols.ATT_NAME}, element);
                    } else {
                        reportSchemaError("src-element.2.1", r4, element);
                    }
                    str4 = "(no name)";
                } else {
                    str4 = str2;
                }
                if (firstChildElement != null) {
                    reportSchemaError("s4s-elt-must-match.1", new Object[]{str4, "(annotation?, (simpleType | complexType)?, (unique | key | keyref)*))", DOMUtil.getLocalName(firstChildElement)}, firstChildElement);
                }
                if (str3 != null) {
                    reportSchemaError("src-element.1", new Object[]{str4}, element);
                }
                if (z2) {
                    reportSchemaError("src-element.3", new Object[]{str4}, element);
                }
                checkNotationType(str4, r2, element);
                if (xSElementDecl.fDefault != null) {
                    this.fValidationState.setNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
                    if (XSConstraints.ElementDefaultValidImmediate(xSElementDecl.fType, xSElementDecl.fDefault.normalizedValue, this.fValidationState, xSElementDecl.fDefault) == null) {
                        reportSchemaError("e-props-correct.2", new Object[]{str4, xSElementDecl.fDefault.normalizedValue}, element);
                        xSElementDecl.fDefault = r4;
                        s = 0;
                        xSElementDecl.setConstraintType((short) 0);
                    } else {
                        s = 0;
                    }
                } else {
                    s = 0;
                }
                if (xSElementDecl.fSubGroup != null) {
                    reportSchemaError("e-props-correct.4", new Object[]{str4, qName2.prefix + ":" + qName2.localpart}, element);
                    xSElementDecl.fSubGroup = r4;
                }
                if (xSElementDecl.fDefault != null) {
                    reportSchemaError("e-props-correct.5", new Object[]{xSElementDecl.fName}, element);
                    xSElementDecl.fDefault = r4;
                    xSElementDecl.setConstraintType(s);
                }
                if (xSElementDecl.fName == null) {
                    return r4;
                }
                if (z) {
                    schemaGrammar.addGlobalElementDeclAll(xSElementDecl);
                    if (schemaGrammar.getGlobalElementDecl(xSElementDecl.fName) == null) {
                        schemaGrammar.addGlobalElementDecl(xSElementDecl);
                    }
                    strSchemaDocument2SystemId = this.fSchemaHandler.schemaDocument2SystemId(xSDocumentInfo);
                    globalElementDecl = schemaGrammar.getGlobalElementDecl(xSElementDecl.fName, strSchemaDocument2SystemId);
                    if (globalElementDecl == null) {
                        schemaGrammar.addGlobalElementDecl(xSElementDecl, strSchemaDocument2SystemId);
                    }
                    if (this.fSchemaHandler.fTolerateDuplicates) {
                        if (globalElementDecl != null) {
                            xSElementDecl2 = globalElementDecl;
                        } else {
                            xSElementDecl2 = xSElementDecl;
                        }
                        this.fSchemaHandler.addGlobalElementDecl(xSElementDecl2);
                        return xSElementDecl2;
                    }
                }
                return xSElementDecl;
            }
            schemaGrammar2 = schemaGrammar;
            objTraverseLocal = this.fSchemaHandler.fComplexTypeTraverser.traverseLocal(firstChildElement, xSDocumentInfo, schemaGrammar2);
            firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
            obj = objTraverseLocal;
            if (obj == null) {
                qName = qName4;
                r2 = obj;
            } else {
                qName = qName4;
                r2 = obj;
            }
            if (r2 == 0) {
                r2 = elementDecl.fSubGroup.fType;
            }
            if (r2 == 0) {
                r2 = SchemaGrammar.fAnyType;
            }
            elementDecl.fType = r2;
            if (firstChildElement != null) {
                localName = DOMUtil.getLocalName(firstChildElement);
                while (firstChildElement != null) {
                    if (localName.equals(SchemaSymbols.ELT_KEY)) {
                        DOMUtil.setHidden(firstChildElement, this.fSchemaHandler.fHiddenNodes);
                        this.fSchemaHandler.fUniqueOrKeyTraverser.traverse(firstChildElement, elementDecl, xSDocumentInfo, schemaGrammar2);
                        if (DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME).length() != 0) {
                            XSDHandler xSDHandler14 = this.fSchemaHandler;
                            if (xSDocumentInfo.fTargetNamespace == null) {
                                str5 = "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                            } else {
                                str5 = xSDocumentInfo.fTargetNamespace + "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                            }
                            str = null;
                            xSDHandler14.checkForDuplicateNames(str5, 1, this.fSchemaHandler.getIDRegistry(), this.fSchemaHandler.getIDRegistry_sub(), firstChildElement, xSDocumentInfo);
                        } else {
                            str = str;
                        }
                    } else {
                        DOMUtil.setHidden(firstChildElement, this.fSchemaHandler.fHiddenNodes);
                        this.fSchemaHandler.fUniqueOrKeyTraverser.traverse(firstChildElement, elementDecl, xSDocumentInfo, schemaGrammar2);
                        if (DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME).length() != 0) {
                            XSDHandler xSDHandler15 = this.fSchemaHandler;
                            if (xSDocumentInfo.fTargetNamespace == null) {
                                str5 = "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                            } else {
                                str5 = xSDocumentInfo.fTargetNamespace + "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                            }
                            str = null;
                            xSDHandler15.checkForDuplicateNames(str5, 1, this.fSchemaHandler.getIDRegistry(), this.fSchemaHandler.getIDRegistry_sub(), firstChildElement, xSDocumentInfo);
                        } else {
                            str = str;
                        }
                    }
                    firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                    if (firstChildElement != null) {
                        localName = DOMUtil.getLocalName(firstChildElement);
                    } else {
                        localName = localName;
                    }
                    schemaGrammar2 = schemaGrammar;
                }
            }
            QName qName12 = qName;
            xSElementDecl = elementDecl;
            qName2 = qName3;
            str2 = str8;
            String str16 = str7;
            str3 = str6;
            r4 = str;
            if (str2 == null) {
                if (z) {
                    reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_ELEMENT, SchemaSymbols.ATT_NAME}, element);
                } else {
                    reportSchemaError("src-element.2.1", r4, element);
                }
                str4 = "(no name)";
            } else {
                str4 = str2;
            }
            if (firstChildElement != null) {
                reportSchemaError("s4s-elt-must-match.1", new Object[]{str4, "(annotation?, (simpleType | complexType)?, (unique | key | keyref)*))", DOMUtil.getLocalName(firstChildElement)}, firstChildElement);
            }
            if (str3 != null) {
                reportSchemaError("src-element.1", new Object[]{str4}, element);
            }
            if (z2) {
                reportSchemaError("src-element.3", new Object[]{str4}, element);
            }
            checkNotationType(str4, r2, element);
            if (xSElementDecl.fDefault != null) {
                this.fValidationState.setNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
                if (XSConstraints.ElementDefaultValidImmediate(xSElementDecl.fType, xSElementDecl.fDefault.normalizedValue, this.fValidationState, xSElementDecl.fDefault) == null) {
                    reportSchemaError("e-props-correct.2", new Object[]{str4, xSElementDecl.fDefault.normalizedValue}, element);
                    xSElementDecl.fDefault = r4;
                    s = 0;
                    xSElementDecl.setConstraintType((short) 0);
                } else {
                    s = 0;
                }
            } else {
                s = 0;
            }
            if (xSElementDecl.fSubGroup != null) {
                reportSchemaError("e-props-correct.4", new Object[]{str4, qName2.prefix + ":" + qName2.localpart}, element);
                xSElementDecl.fSubGroup = r4;
            }
            if (xSElementDecl.fDefault != null) {
                reportSchemaError("e-props-correct.5", new Object[]{xSElementDecl.fName}, element);
                xSElementDecl.fDefault = r4;
                xSElementDecl.setConstraintType(s);
            }
            if (xSElementDecl.fName == null) {
                return r4;
            }
            if (z) {
                schemaGrammar.addGlobalElementDeclAll(xSElementDecl);
                if (schemaGrammar.getGlobalElementDecl(xSElementDecl.fName) == null) {
                    schemaGrammar.addGlobalElementDecl(xSElementDecl);
                }
                strSchemaDocument2SystemId = this.fSchemaHandler.schemaDocument2SystemId(xSDocumentInfo);
                globalElementDecl = schemaGrammar.getGlobalElementDecl(xSElementDecl.fName, strSchemaDocument2SystemId);
                if (globalElementDecl == null) {
                    schemaGrammar.addGlobalElementDecl(xSElementDecl, strSchemaDocument2SystemId);
                }
                if (this.fSchemaHandler.fTolerateDuplicates) {
                    if (globalElementDecl != null) {
                        xSElementDecl2 = globalElementDecl;
                    } else {
                        xSElementDecl2 = xSElementDecl;
                    }
                    this.fSchemaHandler.addGlobalElementDecl(xSElementDecl2);
                    return xSElementDecl2;
                }
            }
            return xSElementDecl;
        }
        schemaGrammar2 = schemaGrammar;
        obj = str;
        z2 = false;
        if (obj == null) {
            qName = qName4;
            r2 = obj;
        } else {
            qName = qName4;
            r2 = obj;
        }
        if (r2 == 0) {
            r2 = elementDecl.fSubGroup.fType;
        }
        if (r2 == 0) {
            r2 = SchemaGrammar.fAnyType;
        }
        elementDecl.fType = r2;
        if (firstChildElement != null) {
            localName = DOMUtil.getLocalName(firstChildElement);
            while (firstChildElement != null) {
                if (localName.equals(SchemaSymbols.ELT_KEY)) {
                    DOMUtil.setHidden(firstChildElement, this.fSchemaHandler.fHiddenNodes);
                    this.fSchemaHandler.fUniqueOrKeyTraverser.traverse(firstChildElement, elementDecl, xSDocumentInfo, schemaGrammar2);
                    if (DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME).length() != 0) {
                        XSDHandler xSDHandler16 = this.fSchemaHandler;
                        if (xSDocumentInfo.fTargetNamespace == null) {
                            str5 = "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                        } else {
                            str5 = xSDocumentInfo.fTargetNamespace + "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                        }
                        str = null;
                        xSDHandler16.checkForDuplicateNames(str5, 1, this.fSchemaHandler.getIDRegistry(), this.fSchemaHandler.getIDRegistry_sub(), firstChildElement, xSDocumentInfo);
                    } else {
                        str = str;
                    }
                } else {
                    DOMUtil.setHidden(firstChildElement, this.fSchemaHandler.fHiddenNodes);
                    this.fSchemaHandler.fUniqueOrKeyTraverser.traverse(firstChildElement, elementDecl, xSDocumentInfo, schemaGrammar2);
                    if (DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME).length() != 0) {
                        XSDHandler xSDHandler17 = this.fSchemaHandler;
                        if (xSDocumentInfo.fTargetNamespace == null) {
                            str5 = "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                        } else {
                            str5 = xSDocumentInfo.fTargetNamespace + "," + DOMUtil.getAttrValue(firstChildElement, SchemaSymbols.ATT_NAME);
                        }
                        str = null;
                        xSDHandler17.checkForDuplicateNames(str5, 1, this.fSchemaHandler.getIDRegistry(), this.fSchemaHandler.getIDRegistry_sub(), firstChildElement, xSDocumentInfo);
                    } else {
                        str = str;
                    }
                }
                firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                if (firstChildElement != null) {
                    localName = DOMUtil.getLocalName(firstChildElement);
                } else {
                    localName = localName;
                }
                schemaGrammar2 = schemaGrammar;
            }
        }
        QName qName13 = qName;
        xSElementDecl = elementDecl;
        qName2 = qName3;
        str2 = str8;
        String str17 = str7;
        str3 = str6;
        r4 = str;
        if (str2 == null) {
            if (z) {
                reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_ELEMENT, SchemaSymbols.ATT_NAME}, element);
            } else {
                reportSchemaError("src-element.2.1", r4, element);
            }
            str4 = "(no name)";
        } else {
            str4 = str2;
        }
        if (firstChildElement != null) {
            reportSchemaError("s4s-elt-must-match.1", new Object[]{str4, "(annotation?, (simpleType | complexType)?, (unique | key | keyref)*))", DOMUtil.getLocalName(firstChildElement)}, firstChildElement);
        }
        if (str3 != null) {
            reportSchemaError("src-element.1", new Object[]{str4}, element);
        }
        if (z2) {
            reportSchemaError("src-element.3", new Object[]{str4}, element);
        }
        checkNotationType(str4, r2, element);
        if (xSElementDecl.fDefault != null) {
            this.fValidationState.setNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
            if (XSConstraints.ElementDefaultValidImmediate(xSElementDecl.fType, xSElementDecl.fDefault.normalizedValue, this.fValidationState, xSElementDecl.fDefault) == null) {
                reportSchemaError("e-props-correct.2", new Object[]{str4, xSElementDecl.fDefault.normalizedValue}, element);
                xSElementDecl.fDefault = r4;
                s = 0;
                xSElementDecl.setConstraintType((short) 0);
            } else {
                s = 0;
            }
        } else {
            s = 0;
        }
        if (xSElementDecl.fSubGroup != null) {
            reportSchemaError("e-props-correct.4", new Object[]{str4, qName2.prefix + ":" + qName2.localpart}, element);
            xSElementDecl.fSubGroup = r4;
        }
        if (xSElementDecl.fDefault != null) {
            reportSchemaError("e-props-correct.5", new Object[]{xSElementDecl.fName}, element);
            xSElementDecl.fDefault = r4;
            xSElementDecl.setConstraintType(s);
        }
        if (xSElementDecl.fName == null) {
            return r4;
        }
        if (z) {
            schemaGrammar.addGlobalElementDeclAll(xSElementDecl);
            if (schemaGrammar.getGlobalElementDecl(xSElementDecl.fName) == null) {
                schemaGrammar.addGlobalElementDecl(xSElementDecl);
            }
            strSchemaDocument2SystemId = this.fSchemaHandler.schemaDocument2SystemId(xSDocumentInfo);
            globalElementDecl = schemaGrammar.getGlobalElementDecl(xSElementDecl.fName, strSchemaDocument2SystemId);
            if (globalElementDecl == null) {
                schemaGrammar.addGlobalElementDecl(xSElementDecl, strSchemaDocument2SystemId);
            }
            if (this.fSchemaHandler.fTolerateDuplicates) {
                if (globalElementDecl != null) {
                    xSElementDecl2 = globalElementDecl;
                } else {
                    xSElementDecl2 = xSElementDecl;
                }
                this.fSchemaHandler.addGlobalElementDecl(xSElementDecl2);
                return xSElementDecl2;
            }
        }
        return xSElementDecl;
    }
}
