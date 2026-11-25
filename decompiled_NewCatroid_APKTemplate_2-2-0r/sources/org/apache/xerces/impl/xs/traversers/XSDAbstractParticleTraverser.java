package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAnnotationImpl;
import org.apache.xerces.impl.xs.XSModelGroupImpl;
import org.apache.xerces.impl.xs.XSParticleDecl;
import org.apache.xerces.impl.xs.util.XInt;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.xs.XSObject;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes13.dex */
abstract class XSDAbstractParticleTraverser extends XSDAbstractTraverser {
    ParticleArray fPArray;

    static class ParticleArray {
        XSParticleDecl[] fParticles = new XSParticleDecl[10];
        int[] fPos = new int[5];
        int fContextCount = 0;

        ParticleArray() {
        }

        void addParticle(XSParticleDecl xSParticleDecl) {
            int i = this.fPos[this.fContextCount];
            XSParticleDecl[] xSParticleDeclArr = this.fParticles;
            if (i == xSParticleDeclArr.length) {
                XSParticleDecl[] xSParticleDeclArr2 = new XSParticleDecl[i * 2];
                System.arraycopy(xSParticleDeclArr, 0, xSParticleDeclArr2, 0, i);
                this.fParticles = xSParticleDeclArr2;
            }
            XSParticleDecl[] xSParticleDeclArr3 = this.fParticles;
            int[] iArr = this.fPos;
            int i2 = this.fContextCount;
            int i3 = iArr[i2];
            iArr[i2] = i3 + 1;
            xSParticleDeclArr3[i3] = xSParticleDecl;
        }

        int getParticleCount() {
            int[] iArr = this.fPos;
            int i = this.fContextCount;
            return iArr[i] - iArr[i - 1];
        }

        XSParticleDecl[] popContext() {
            int[] iArr = this.fPos;
            int i = this.fContextCount;
            int i2 = iArr[i] - iArr[i - 1];
            XSParticleDecl[] xSParticleDeclArr = null;
            if (i2 != 0) {
                XSParticleDecl[] xSParticleDeclArr2 = new XSParticleDecl[i2];
                System.arraycopy(this.fParticles, iArr[i - 1], xSParticleDeclArr2, 0, i2);
                for (int i3 = this.fPos[this.fContextCount - 1]; i3 < this.fPos[this.fContextCount]; i3++) {
                    this.fParticles[i3] = null;
                }
                xSParticleDeclArr = xSParticleDeclArr2;
            }
            this.fContextCount--;
            return xSParticleDeclArr;
        }

        void pushContext() {
            int i = this.fContextCount + 1;
            this.fContextCount = i;
            int[] iArr = this.fPos;
            if (i == iArr.length) {
                int[] iArr2 = new int[i * 2];
                System.arraycopy(iArr, 0, iArr2, 0, i);
                this.fPos = iArr2;
            }
            int[] iArr3 = this.fPos;
            int i2 = this.fContextCount;
            iArr3[i2] = iArr3[i2 - 1];
        }
    }

    XSDAbstractParticleTraverser(XSDHandler xSDHandler, XSAttributeChecker xSAttributeChecker) {
        super(xSDHandler, xSAttributeChecker);
        this.fPArray = new ParticleArray();
    }

    /* JADX WARN: Code duplicated, block: B:15:0x004c  */
    /* JADX WARN: Code duplicated, block: B:17:0x0058  */
    /* JADX WARN: Code duplicated, block: B:18:0x006a  */
    /* JADX WARN: Code duplicated, block: B:20:0x0072  */
    /* JADX WARN: Code duplicated, block: B:22:0x0080  */
    /* JADX WARN: Code duplicated, block: B:24:0x0088  */
    /* JADX WARN: Code duplicated, block: B:26:0x0090  */
    /* JADX WARN: Code duplicated, block: B:27:0x009f  */
    /* JADX WARN: Code duplicated, block: B:29:0x00a7  */
    /* JADX WARN: Code duplicated, block: B:30:0x00b6  */
    /* JADX WARN: Code duplicated, block: B:32:0x00be  */
    /* JADX WARN: Code duplicated, block: B:33:0x00c7  */
    /* JADX WARN: Code duplicated, block: B:35:0x00cf  */
    /* JADX WARN: Code duplicated, block: B:36:0x00dc  */
    /* JADX WARN: Code duplicated, block: B:39:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:43:0x0114  */
    /* JADX WARN: Code duplicated, block: B:44:0x0117  */
    /* JADX WARN: Code duplicated, block: B:47:0x012d  */
    /* JADX WARN: Code duplicated, block: B:48:0x0139  */
    /* JADX WARN: Code duplicated, block: B:51:0x0156  */
    /* JADX WARN: Code duplicated, block: B:52:0x0159  */
    /* JADX WARN: Code duplicated, block: B:57:0x00f5 A[SYNTHETIC] */
    private XSParticleDecl traverseSeqChoice(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar, int i, boolean z, XSObject xSObject) {
        Element element2;
        XSAnnotationImpl xSAnnotationImpl;
        XSAnnotationImpl xSAnnotationImplTraverseSyntheticAnnotation;
        Element nextSiblingElement;
        short s;
        XSObjectListImpl xSObjectListImpl;
        String str;
        String localName;
        Object[] objArr;
        XSParticleDecl xSParticleDeclTraverseAny;
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, false, xSDocumentInfo);
        Element firstChildElement = DOMUtil.getFirstChildElement(element);
        if (firstChildElement == null || !DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
            String syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element);
            if (syntheticAnnotation != null) {
                xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo);
            } else {
                element2 = firstChildElement;
                xSAnnotationImpl = null;
            }
            this.fPArray.pushContext();
            for (nextSiblingElement = element2; nextSiblingElement != null; nextSiblingElement = DOMUtil.getNextSiblingElement(nextSiblingElement)) {
                localName = DOMUtil.getLocalName(nextSiblingElement);
                if (localName.equals(SchemaSymbols.ELT_ELEMENT)) {
                    xSParticleDeclTraverseAny = this.fSchemaHandler.fElementTraverser.traverseLocal(nextSiblingElement, xSDocumentInfo, schemaGrammar, 0, xSObject);
                } else if (localName.equals(SchemaSymbols.ELT_GROUP)) {
                    xSParticleDeclTraverseAny = this.fSchemaHandler.fGroupTraverser.traverseLocal(nextSiblingElement, xSDocumentInfo, schemaGrammar);
                    if (hasAllContent(xSParticleDeclTraverseAny)) {
                        reportSchemaError("cos-all-limited.1.2", null, nextSiblingElement);
                        xSParticleDeclTraverseAny = null;
                    }
                } else if (localName.equals(SchemaSymbols.ELT_CHOICE)) {
                    xSParticleDeclTraverseAny = traverseChoice(nextSiblingElement, xSDocumentInfo, schemaGrammar, 0, xSObject);
                } else if (localName.equals(SchemaSymbols.ELT_SEQUENCE)) {
                    xSParticleDeclTraverseAny = traverseSequence(nextSiblingElement, xSDocumentInfo, schemaGrammar, 0, xSObject);
                } else if (localName.equals(SchemaSymbols.ELT_ANY)) {
                    xSParticleDeclTraverseAny = this.fSchemaHandler.fWildCardTraverser.traverseAny(nextSiblingElement, xSDocumentInfo, schemaGrammar);
                } else {
                    objArr = new Object[3];
                    if (z) {
                        objArr[0] = "choice";
                        objArr[1] = "(annotation?, (element | group | choice | sequence | any)*)";
                        objArr[2] = DOMUtil.getLocalName(nextSiblingElement);
                    } else {
                        objArr[0] = "sequence";
                        objArr[1] = "(annotation?, (element | group | choice | sequence | any)*)";
                        objArr[2] = DOMUtil.getLocalName(nextSiblingElement);
                    }
                    reportSchemaError("s4s-elt-must-match.1", objArr, nextSiblingElement);
                    xSParticleDeclTraverseAny = null;
                }
                if (xSParticleDeclTraverseAny != null) {
                    this.fPArray.addParticle(xSParticleDeclTraverseAny);
                }
            }
            XInt xInt = (XInt) objArrCheckAttributes[XSAttributeChecker.ATTIDX_MINOCCURS];
            XInt xInt2 = (XInt) objArrCheckAttributes[XSAttributeChecker.ATTIDX_MAXOCCURS];
            Long l = (Long) objArrCheckAttributes[XSAttributeChecker.ATTIDX_FROMDEFAULT];
            XSModelGroupImpl xSModelGroupImpl = new XSModelGroupImpl();
            if (z) {
                s = 101;
            } else {
                s = 102;
            }
            xSModelGroupImpl.fCompositor = s;
            xSModelGroupImpl.fParticleCount = this.fPArray.getParticleCount();
            xSModelGroupImpl.fParticles = this.fPArray.popContext();
            if (xSAnnotationImpl != null) {
                xSObjectListImpl = new XSObjectListImpl();
                xSObjectListImpl.addXSObject(xSAnnotationImpl);
            } else {
                xSObjectListImpl = XSObjectListImpl.EMPTY_LIST;
            }
            xSModelGroupImpl.fAnnotations = xSObjectListImpl;
            XSParticleDecl xSParticleDecl = new XSParticleDecl();
            xSParticleDecl.fType = (short) 3;
            xSParticleDecl.fMinOccurs = xInt.intValue();
            xSParticleDecl.fMaxOccurs = xInt2.intValue();
            xSParticleDecl.fValue = xSModelGroupImpl;
            xSParticleDecl.fAnnotations = xSObjectListImpl;
            if (z) {
                str = SchemaSymbols.ELT_CHOICE;
            } else {
                str = SchemaSymbols.ELT_SEQUENCE;
            }
            XSParticleDecl xSParticleDeclCheckOccurrences = checkOccurrences(xSParticleDecl, str, (Element) element.getParentNode(), i, l.longValue());
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            return xSParticleDeclCheckOccurrences;
        }
        xSAnnotationImplTraverseSyntheticAnnotation = traverseAnnotationDecl(firstChildElement, objArrCheckAttributes, false, xSDocumentInfo);
        firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
        Element element3 = firstChildElement;
        xSAnnotationImpl = xSAnnotationImplTraverseSyntheticAnnotation;
        element2 = element3;
        this.fPArray.pushContext();
        while (nextSiblingElement != null) {
            localName = DOMUtil.getLocalName(nextSiblingElement);
            if (localName.equals(SchemaSymbols.ELT_ELEMENT)) {
                xSParticleDeclTraverseAny = this.fSchemaHandler.fElementTraverser.traverseLocal(nextSiblingElement, xSDocumentInfo, schemaGrammar, 0, xSObject);
            } else if (localName.equals(SchemaSymbols.ELT_GROUP)) {
                xSParticleDeclTraverseAny = this.fSchemaHandler.fGroupTraverser.traverseLocal(nextSiblingElement, xSDocumentInfo, schemaGrammar);
                if (hasAllContent(xSParticleDeclTraverseAny)) {
                    reportSchemaError("cos-all-limited.1.2", null, nextSiblingElement);
                    xSParticleDeclTraverseAny = null;
                }
            } else if (localName.equals(SchemaSymbols.ELT_CHOICE)) {
                xSParticleDeclTraverseAny = traverseChoice(nextSiblingElement, xSDocumentInfo, schemaGrammar, 0, xSObject);
            } else if (localName.equals(SchemaSymbols.ELT_SEQUENCE)) {
                xSParticleDeclTraverseAny = traverseSequence(nextSiblingElement, xSDocumentInfo, schemaGrammar, 0, xSObject);
            } else if (localName.equals(SchemaSymbols.ELT_ANY)) {
                xSParticleDeclTraverseAny = this.fSchemaHandler.fWildCardTraverser.traverseAny(nextSiblingElement, xSDocumentInfo, schemaGrammar);
            } else {
                objArr = new Object[3];
                if (z) {
                    objArr[0] = "choice";
                    objArr[1] = "(annotation?, (element | group | choice | sequence | any)*)";
                    objArr[2] = DOMUtil.getLocalName(nextSiblingElement);
                } else {
                    objArr[0] = "sequence";
                    objArr[1] = "(annotation?, (element | group | choice | sequence | any)*)";
                    objArr[2] = DOMUtil.getLocalName(nextSiblingElement);
                }
                reportSchemaError("s4s-elt-must-match.1", objArr, nextSiblingElement);
                xSParticleDeclTraverseAny = null;
            }
            if (xSParticleDeclTraverseAny != null) {
                this.fPArray.addParticle(xSParticleDeclTraverseAny);
            }
        }
        XInt xInt3 = (XInt) objArrCheckAttributes[XSAttributeChecker.ATTIDX_MINOCCURS];
        XInt xInt4 = (XInt) objArrCheckAttributes[XSAttributeChecker.ATTIDX_MAXOCCURS];
        Long l2 = (Long) objArrCheckAttributes[XSAttributeChecker.ATTIDX_FROMDEFAULT];
        XSModelGroupImpl xSModelGroupImpl2 = new XSModelGroupImpl();
        if (z) {
            s = 101;
        } else {
            s = 102;
        }
        xSModelGroupImpl2.fCompositor = s;
        xSModelGroupImpl2.fParticleCount = this.fPArray.getParticleCount();
        xSModelGroupImpl2.fParticles = this.fPArray.popContext();
        if (xSAnnotationImpl != null) {
            xSObjectListImpl = new XSObjectListImpl();
            xSObjectListImpl.addXSObject(xSAnnotationImpl);
        } else {
            xSObjectListImpl = XSObjectListImpl.EMPTY_LIST;
        }
        xSModelGroupImpl2.fAnnotations = xSObjectListImpl;
        XSParticleDecl xSParticleDecl2 = new XSParticleDecl();
        xSParticleDecl2.fType = (short) 3;
        xSParticleDecl2.fMinOccurs = xInt3.intValue();
        xSParticleDecl2.fMaxOccurs = xInt4.intValue();
        xSParticleDecl2.fValue = xSModelGroupImpl2;
        xSParticleDecl2.fAnnotations = xSObjectListImpl;
        if (z) {
            str = SchemaSymbols.ELT_CHOICE;
        } else {
            str = SchemaSymbols.ELT_SEQUENCE;
        }
        XSParticleDecl xSParticleDeclCheckOccurrences2 = checkOccurrences(xSParticleDecl2, str, (Element) element.getParentNode(), i, l2.longValue());
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
        return xSParticleDeclCheckOccurrences2;
    }

    protected boolean hasAllContent(XSParticleDecl xSParticleDecl) {
        return xSParticleDecl != null && xSParticleDecl.fType == 3 && ((XSModelGroupImpl) xSParticleDecl.fValue).fCompositor == 103;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0045  */
    /* JADX WARN: Code duplicated, block: B:16:0x0051  */
    /* JADX WARN: Code duplicated, block: B:17:0x0062  */
    /* JADX WARN: Code duplicated, block: B:19:0x0076  */
    /* JADX WARN: Code duplicated, block: B:23:0x00ad  */
    /* JADX WARN: Code duplicated, block: B:24:0x00b9  */
    /* JADX WARN: Code duplicated, block: B:29:0x007b A[SYNTHETIC] */
    XSParticleDecl traverseAll(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar, int i, XSObject xSObject) {
        Element element2;
        XSAnnotationImpl xSAnnotationImpl;
        XSAnnotationImpl xSAnnotationImplTraverseSyntheticAnnotation;
        Element nextSiblingElement;
        XSObjectListImpl xSObjectListImpl;
        XSParticleDecl xSParticleDeclTraverseLocal;
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, false, xSDocumentInfo);
        Element firstChildElement = DOMUtil.getFirstChildElement(element);
        if (firstChildElement == null || !DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
            String syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element);
            if (syntheticAnnotation != null) {
                xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo);
            } else {
                element2 = firstChildElement;
                xSAnnotationImpl = null;
            }
            this.fPArray.pushContext();
            for (nextSiblingElement = element2; nextSiblingElement != null; nextSiblingElement = DOMUtil.getNextSiblingElement(nextSiblingElement)) {
                if (DOMUtil.getLocalName(nextSiblingElement).equals(SchemaSymbols.ELT_ELEMENT)) {
                    xSParticleDeclTraverseLocal = this.fSchemaHandler.fElementTraverser.traverseLocal(nextSiblingElement, xSDocumentInfo, schemaGrammar, 1, xSObject);
                } else {
                    reportSchemaError("s4s-elt-must-match.1", new Object[]{DevModeOverwritingStrategies.ALL, "(annotation?, element*)", DOMUtil.getLocalName(nextSiblingElement)}, nextSiblingElement);
                    xSParticleDeclTraverseLocal = null;
                }
                if (xSParticleDeclTraverseLocal != null) {
                    this.fPArray.addParticle(xSParticleDeclTraverseLocal);
                }
            }
            XInt xInt = (XInt) objArrCheckAttributes[XSAttributeChecker.ATTIDX_MINOCCURS];
            XInt xInt2 = (XInt) objArrCheckAttributes[XSAttributeChecker.ATTIDX_MAXOCCURS];
            Long l = (Long) objArrCheckAttributes[XSAttributeChecker.ATTIDX_FROMDEFAULT];
            XSModelGroupImpl xSModelGroupImpl = new XSModelGroupImpl();
            xSModelGroupImpl.fCompositor = (short) 103;
            xSModelGroupImpl.fParticleCount = this.fPArray.getParticleCount();
            xSModelGroupImpl.fParticles = this.fPArray.popContext();
            if (xSAnnotationImpl != null) {
                xSObjectListImpl = new XSObjectListImpl();
                xSObjectListImpl.addXSObject(xSAnnotationImpl);
            } else {
                xSObjectListImpl = XSObjectListImpl.EMPTY_LIST;
            }
            xSModelGroupImpl.fAnnotations = xSObjectListImpl;
            XSParticleDecl xSParticleDecl = new XSParticleDecl();
            xSParticleDecl.fType = (short) 3;
            xSParticleDecl.fMinOccurs = xInt.intValue();
            xSParticleDecl.fMaxOccurs = xInt2.intValue();
            xSParticleDecl.fValue = xSModelGroupImpl;
            xSParticleDecl.fAnnotations = xSObjectListImpl;
            XSParticleDecl xSParticleDeclCheckOccurrences = checkOccurrences(xSParticleDecl, SchemaSymbols.ELT_ALL, (Element) element.getParentNode(), i, l.longValue());
            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
            return xSParticleDeclCheckOccurrences;
        }
        xSAnnotationImplTraverseSyntheticAnnotation = traverseAnnotationDecl(firstChildElement, objArrCheckAttributes, false, xSDocumentInfo);
        firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
        Element element3 = firstChildElement;
        xSAnnotationImpl = xSAnnotationImplTraverseSyntheticAnnotation;
        element2 = element3;
        this.fPArray.pushContext();
        while (nextSiblingElement != null) {
            if (DOMUtil.getLocalName(nextSiblingElement).equals(SchemaSymbols.ELT_ELEMENT)) {
                xSParticleDeclTraverseLocal = this.fSchemaHandler.fElementTraverser.traverseLocal(nextSiblingElement, xSDocumentInfo, schemaGrammar, 1, xSObject);
            } else {
                reportSchemaError("s4s-elt-must-match.1", new Object[]{DevModeOverwritingStrategies.ALL, "(annotation?, element*)", DOMUtil.getLocalName(nextSiblingElement)}, nextSiblingElement);
                xSParticleDeclTraverseLocal = null;
            }
            if (xSParticleDeclTraverseLocal != null) {
                this.fPArray.addParticle(xSParticleDeclTraverseLocal);
            }
        }
        XInt xInt3 = (XInt) objArrCheckAttributes[XSAttributeChecker.ATTIDX_MINOCCURS];
        XInt xInt4 = (XInt) objArrCheckAttributes[XSAttributeChecker.ATTIDX_MAXOCCURS];
        Long l2 = (Long) objArrCheckAttributes[XSAttributeChecker.ATTIDX_FROMDEFAULT];
        XSModelGroupImpl xSModelGroupImpl2 = new XSModelGroupImpl();
        xSModelGroupImpl2.fCompositor = (short) 103;
        xSModelGroupImpl2.fParticleCount = this.fPArray.getParticleCount();
        xSModelGroupImpl2.fParticles = this.fPArray.popContext();
        if (xSAnnotationImpl != null) {
            xSObjectListImpl = new XSObjectListImpl();
            xSObjectListImpl.addXSObject(xSAnnotationImpl);
        } else {
            xSObjectListImpl = XSObjectListImpl.EMPTY_LIST;
        }
        xSModelGroupImpl2.fAnnotations = xSObjectListImpl;
        XSParticleDecl xSParticleDecl2 = new XSParticleDecl();
        xSParticleDecl2.fType = (short) 3;
        xSParticleDecl2.fMinOccurs = xInt3.intValue();
        xSParticleDecl2.fMaxOccurs = xInt4.intValue();
        xSParticleDecl2.fValue = xSModelGroupImpl2;
        xSParticleDecl2.fAnnotations = xSObjectListImpl;
        XSParticleDecl xSParticleDeclCheckOccurrences2 = checkOccurrences(xSParticleDecl2, SchemaSymbols.ELT_ALL, (Element) element.getParentNode(), i, l2.longValue());
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
        return xSParticleDeclCheckOccurrences2;
    }

    XSParticleDecl traverseChoice(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar, int i, XSObject xSObject) {
        return traverseSeqChoice(element, xSDocumentInfo, schemaGrammar, i, true, xSObject);
    }

    XSParticleDecl traverseSequence(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar, int i, XSObject xSObject) {
        return traverseSeqChoice(element, xSDocumentInfo, schemaGrammar, i, false, xSObject);
    }
}
