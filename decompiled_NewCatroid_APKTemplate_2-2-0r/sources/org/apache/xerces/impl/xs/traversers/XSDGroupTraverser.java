package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAnnotationImpl;
import org.apache.xerces.impl.xs.XSConstraints;
import org.apache.xerces.impl.xs.XSGroupDecl;
import org.apache.xerces.impl.xs.XSModelGroupImpl;
import org.apache.xerces.impl.xs.XSParticleDecl;
import org.apache.xerces.impl.xs.util.XInt;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xs.XSObjectList;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes13.dex */
class XSDGroupTraverser extends XSDAbstractParticleTraverser {
    XSDGroupTraverser(XSDHandler xSDHandler, XSAttributeChecker xSAttributeChecker) {
        super(xSDHandler, xSAttributeChecker);
    }

    XSGroupDecl traverseGlobal(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        Element element2;
        XSAnnotationImpl xSAnnotationImplTraverseSyntheticAnnotation;
        String str;
        Element element3;
        XSParticleDecl xSParticleDeclTraverseSequence;
        XSAnnotationImpl xSAnnotationImpl;
        Object grpOrAttrGrpRedefinedByRestriction;
        XSObjectListImpl xSObjectListImpl;
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, true, xSDocumentInfo);
        String str2 = (String) objArrCheckAttributes[XSAttributeChecker.ATTIDX_NAME];
        if (str2 == null) {
            reportSchemaError("s4s-att-must-appear", new Object[]{"group (global)", "name"}, element);
        }
        XSGroupDecl xSGroupDecl = new XSGroupDecl();
        Element firstChildElement = DOMUtil.getFirstChildElement(element);
        if (firstChildElement == null) {
            reportSchemaError("s4s-elt-must-match.2", new Object[]{"group (global)", "(annotation?, (all | choice | sequence))"}, element);
            xSParticleDeclTraverseSequence = null;
            xSAnnotationImpl = null;
        } else {
            String localName = firstChildElement.getLocalName();
            if (localName.equals(SchemaSymbols.ELT_ANNOTATION)) {
                XSAnnotationImpl xSAnnotationImplTraverseAnnotationDecl = traverseAnnotationDecl(firstChildElement, objArrCheckAttributes, true, xSDocumentInfo);
                Element nextSiblingElement = DOMUtil.getNextSiblingElement(firstChildElement);
                if (nextSiblingElement != null) {
                    localName = nextSiblingElement.getLocalName();
                }
                xSAnnotationImplTraverseSyntheticAnnotation = xSAnnotationImplTraverseAnnotationDecl;
                element2 = nextSiblingElement;
            } else {
                String syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element);
                if (syntheticAnnotation != null) {
                    element2 = firstChildElement;
                    localName = localName;
                    xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo);
                } else {
                    element2 = firstChildElement;
                    xSAnnotationImplTraverseSyntheticAnnotation = null;
                }
            }
            if (element2 == null) {
                reportSchemaError("s4s-elt-must-match.2", new Object[]{"group (global)", "(annotation?, (all | choice | sequence))"}, element);
                str = "s4s-elt-must-match.1";
                element3 = element2;
            } else {
                if (localName.equals(SchemaSymbols.ELT_ALL)) {
                    str = "s4s-elt-must-match.1";
                    xSParticleDeclTraverseSequence = traverseAll(element2, xSDocumentInfo, schemaGrammar, 4, xSGroupDecl);
                    element3 = element2;
                } else {
                    str = "s4s-elt-must-match.1";
                    Element element4 = element2;
                    if (localName.equals(SchemaSymbols.ELT_CHOICE)) {
                        element3 = element4;
                        xSParticleDeclTraverseSequence = traverseChoice(element3, xSDocumentInfo, schemaGrammar, 4, xSGroupDecl);
                    } else if (localName.equals(SchemaSymbols.ELT_SEQUENCE)) {
                        element3 = element4;
                        xSParticleDeclTraverseSequence = traverseSequence(element3, xSDocumentInfo, schemaGrammar, 4, xSGroupDecl);
                    } else {
                        element3 = element4;
                        reportSchemaError(str, new Object[]{"group (global)", r5, DOMUtil.getLocalName(element4)}, element3);
                    }
                }
                if (element3 != null && DOMUtil.getNextSiblingElement(element3) != null) {
                    reportSchemaError(str, new Object[]{"group (global)", r5, DOMUtil.getLocalName(DOMUtil.getNextSiblingElement(element3))}, DOMUtil.getNextSiblingElement(element3));
                }
                xSAnnotationImpl = xSAnnotationImplTraverseSyntheticAnnotation;
            }
            xSParticleDeclTraverseSequence = null;
            if (element3 != null) {
                reportSchemaError(str, new Object[]{"group (global)", r5, DOMUtil.getLocalName(DOMUtil.getNextSiblingElement(element3))}, DOMUtil.getNextSiblingElement(element3));
            }
            xSAnnotationImpl = xSAnnotationImplTraverseSyntheticAnnotation;
        }
        if (str2 != null) {
            xSGroupDecl.fName = str2;
            xSGroupDecl.fTargetNamespace = xSDocumentInfo.fTargetNamespace;
            if (xSParticleDeclTraverseSequence == null) {
                xSParticleDeclTraverseSequence = XSConstraints.getEmptySequence();
            }
            xSGroupDecl.fModelGroup = (XSModelGroupImpl) xSParticleDeclTraverseSequence.fValue;
            if (xSAnnotationImpl != null) {
                xSObjectListImpl = new XSObjectListImpl();
                xSObjectListImpl.addXSObject(xSAnnotationImpl);
            } else {
                xSObjectListImpl = XSObjectListImpl.EMPTY_LIST;
            }
            xSGroupDecl.fAnnotations = xSObjectListImpl;
            if (schemaGrammar.getGlobalGroupDecl(xSGroupDecl.fName) == null) {
                schemaGrammar.addGlobalGroupDecl(xSGroupDecl);
            }
            String strSchemaDocument2SystemId = this.fSchemaHandler.schemaDocument2SystemId(xSDocumentInfo);
            XSGroupDecl globalGroupDecl = schemaGrammar.getGlobalGroupDecl(xSGroupDecl.fName, strSchemaDocument2SystemId);
            if (globalGroupDecl == null) {
                schemaGrammar.addGlobalGroupDecl(xSGroupDecl, strSchemaDocument2SystemId);
            }
            if (this.fSchemaHandler.fTolerateDuplicates) {
                if (globalGroupDecl != null) {
                    xSGroupDecl = globalGroupDecl;
                }
                this.fSchemaHandler.addGlobalGroupDecl(xSGroupDecl);
            }
        } else {
            xSGroupDecl = null;
        }
        if (xSGroupDecl != null && (grpOrAttrGrpRedefinedByRestriction = this.fSchemaHandler.getGrpOrAttrGrpRedefinedByRestriction(4, new QName(XMLSymbols.EMPTY_STRING, str2, str2, xSDocumentInfo.fTargetNamespace), xSDocumentInfo, element)) != null) {
            schemaGrammar.addRedefinedGroupDecl(xSGroupDecl, (XSGroupDecl) grpOrAttrGrpRedefinedByRestriction, this.fSchemaHandler.element2Locator(element));
        }
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
        return xSGroupDecl;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0076  */
    /* JADX WARN: Code duplicated, block: B:26:0x009d  */
    /* JADX WARN: Code duplicated, block: B:27:0x00a6  */
    /* JADX WARN: Code duplicated, block: B:30:0x00be  */
    /* JADX WARN: Code duplicated, block: B:31:0x00df  */
    /* JADX WARN: Code duplicated, block: B:33:0x00e3 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:34:0x00e5  */
    /* JADX WARN: Code duplicated, block: B:35:0x00f1  */
    /* JADX WARN: Code duplicated, block: B:36:0x00f4  */
    XSParticleDecl traverseLocal(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        XSGroupDecl xSGroupDecl;
        Element nextSiblingElement;
        XSAnnotationImpl xSAnnotationImpl;
        XSAnnotationImpl xSAnnotationImplTraverseSyntheticAnnotation;
        int iIntValue;
        int iIntValue2;
        XSParticleDecl xSParticleDecl;
        XSParticleDecl xSParticleDeclCheckOccurrences;
        XSObjectList xSObjectList;
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, false, xSDocumentInfo);
        QName qName = (QName) objArrCheckAttributes[XSAttributeChecker.ATTIDX_REF];
        XInt xInt = (XInt) objArrCheckAttributes[XSAttributeChecker.ATTIDX_MINOCCURS];
        XInt xInt2 = (XInt) objArrCheckAttributes[XSAttributeChecker.ATTIDX_MAXOCCURS];
        XSParticleDecl xSParticleDecl2 = null;
        if (qName == null) {
            reportSchemaError("s4s-att-must-appear", new Object[]{"group (local)", "ref"}, element);
            xSGroupDecl = null;
        } else {
            xSGroupDecl = (XSGroupDecl) this.fSchemaHandler.getGlobalDecl(xSDocumentInfo, 4, qName, element);
        }
        Element firstChildElement = DOMUtil.getFirstChildElement(element);
        if (firstChildElement == null || !DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
            String syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element);
            if (syntheticAnnotation != null) {
                nextSiblingElement = firstChildElement;
                xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo);
            } else {
                nextSiblingElement = firstChildElement;
                xSAnnotationImpl = null;
            }
            if (nextSiblingElement != null) {
                reportSchemaError("s4s-elt-must-match.1", new Object[]{"group (local)", "(annotation?)", DOMUtil.getLocalName(element)}, element);
            }
            iIntValue = xInt.intValue();
            iIntValue2 = xInt2.intValue();
            if (xSGroupDecl != null && xSGroupDecl.fModelGroup != null && (iIntValue != 0 || iIntValue2 != 0)) {
                if (this.fSchemaHandler.fDeclPool != null) {
                    xSParticleDecl = this.fSchemaHandler.fDeclPool.getParticleDecl();
                } else {
                    xSParticleDecl = new XSParticleDecl();
                }
                xSParticleDecl.fType = (short) 3;
                xSParticleDecl.fValue = xSGroupDecl.fModelGroup;
                xSParticleDecl.fMinOccurs = iIntValue;
                xSParticleDecl.fMaxOccurs = iIntValue2;
                if (xSGroupDecl.fModelGroup.fCompositor == 103) {
                    xSParticleDeclCheckOccurrences = checkOccurrences(xSParticleDecl, SchemaSymbols.ELT_GROUP, (Element) element.getParentNode(), 2, ((Long) objArrCheckAttributes[XSAttributeChecker.ATTIDX_FROMDEFAULT]).longValue());
                } else {
                    xSParticleDeclCheckOccurrences = xSParticleDecl;
                }
                if (qName != null) {
                    xSObjectList = xSGroupDecl.fAnnotations;
                } else if (xSAnnotationImpl != 0) {
                    XSObjectListImpl xSObjectListImpl = new XSObjectListImpl();
                    xSObjectListImpl.addXSObject(xSAnnotationImpl);
                    xSObjectList = xSObjectListImpl;
                } else {
                    xSObjectList = XSObjectListImpl.EMPTY_LIST;
                }
                xSParticleDeclCheckOccurrences.fAnnotations = xSObjectList;
                xSParticleDecl2 = xSParticleDeclCheckOccurrences;
            }
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            return xSParticleDecl2;
        }
        xSAnnotationImplTraverseSyntheticAnnotation = traverseAnnotationDecl(firstChildElement, objArrCheckAttributes, false, xSDocumentInfo);
        nextSiblingElement = DOMUtil.getNextSiblingElement(firstChildElement);
        xSAnnotationImpl = xSAnnotationImplTraverseSyntheticAnnotation;
        if (nextSiblingElement != null) {
            reportSchemaError("s4s-elt-must-match.1", new Object[]{"group (local)", "(annotation?)", DOMUtil.getLocalName(element)}, element);
        }
        iIntValue = xInt.intValue();
        iIntValue2 = xInt2.intValue();
        if (xSGroupDecl != null) {
            if (this.fSchemaHandler.fDeclPool != null) {
                xSParticleDecl = this.fSchemaHandler.fDeclPool.getParticleDecl();
            } else {
                xSParticleDecl = new XSParticleDecl();
            }
            xSParticleDecl.fType = (short) 3;
            xSParticleDecl.fValue = xSGroupDecl.fModelGroup;
            xSParticleDecl.fMinOccurs = iIntValue;
            xSParticleDecl.fMaxOccurs = iIntValue2;
            if (xSGroupDecl.fModelGroup.fCompositor == 103) {
                xSParticleDeclCheckOccurrences = checkOccurrences(xSParticleDecl, SchemaSymbols.ELT_GROUP, (Element) element.getParentNode(), 2, ((Long) objArrCheckAttributes[XSAttributeChecker.ATTIDX_FROMDEFAULT]).longValue());
            } else {
                xSParticleDeclCheckOccurrences = xSParticleDecl;
            }
            if (qName != null) {
                xSObjectList = xSGroupDecl.fAnnotations;
            } else if (xSAnnotationImpl != 0) {
                XSObjectListImpl xSObjectListImpl2 = new XSObjectListImpl();
                xSObjectListImpl2.addXSObject(xSAnnotationImpl);
                xSObjectList = xSObjectListImpl2;
            } else {
                xSObjectList = XSObjectListImpl.EMPTY_LIST;
            }
            xSParticleDeclCheckOccurrences.fAnnotations = xSObjectList;
            xSParticleDecl2 = xSParticleDeclCheckOccurrences;
        }
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
        return xSParticleDecl2;
    }
}
