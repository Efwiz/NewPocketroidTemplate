package org.apache.xerces.impl.xs.traversers;

import java.util.ArrayList;
import java.util.Vector;
import org.apache.xerces.impl.dv.InvalidDatatypeFacetException;
import org.apache.xerces.impl.dv.SchemaDVFactory;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl;
import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAnnotationImpl;
import org.apache.xerces.impl.xs.util.XInt;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes13.dex */
class XSDSimpleTypeTraverser extends XSDAbstractTraverser {
    private boolean fIsBuiltIn;

    XSDSimpleTypeTraverser(XSDHandler xSDHandler, XSAttributeChecker xSAttributeChecker) {
        super(xSDHandler, xSAttributeChecker);
        this.fIsBuiltIn = false;
    }

    private final boolean checkBuiltIn(String str, String str2) {
        if (str2 != SchemaSymbols.URI_SCHEMAFORSCHEMA) {
            return false;
        }
        if (SchemaGrammar.SG_SchemaNS.getGlobalTypeDecl(str) != null) {
            this.fIsBuiltIn = true;
        }
        return this.fIsBuiltIn;
    }

    private XSSimpleType errorType(String str, String str2, short s) {
        XSSimpleType xSSimpleType = (XSSimpleType) SchemaGrammar.SG_SchemaNS.getTypeDefinition("string");
        if (s == 2) {
            return this.fSchemaHandler.fDVFactory.createTypeRestriction(str, str2, (short) 0, xSSimpleType, null);
        }
        if (s == 8) {
            return this.fSchemaHandler.fDVFactory.createTypeUnion(str, str2, (short) 0, new XSSimpleType[]{xSSimpleType}, null);
        }
        if (s != 16) {
            return null;
        }
        return this.fSchemaHandler.fDVFactory.createTypeList(str, str2, (short) 0, xSSimpleType, null);
    }

    private XSSimpleType findDTValidator(Element element, String str, QName qName, short s, XSDocumentInfo xSDocumentInfo) {
        XSTypeDefinition xSTypeDefinition;
        if (qName == null || (xSTypeDefinition = (XSTypeDefinition) this.fSchemaHandler.getGlobalDecl(xSDocumentInfo, 7, qName, element)) == null) {
            return null;
        }
        if (xSTypeDefinition.getTypeCategory() != 16) {
            reportSchemaError("cos-st-restricts.1.1", new Object[]{qName.rawname, str}, element);
            return null;
        }
        if (xSTypeDefinition == SchemaGrammar.fAnySimpleType && s == 2) {
            if (checkBuiltIn(str, xSDocumentInfo.fTargetNamespace)) {
                return null;
            }
            reportSchemaError("cos-st-restricts.1.1", new Object[]{qName.rawname, str}, element);
            return null;
        }
        if ((xSTypeDefinition.getFinal() & s) == 0) {
            return (XSSimpleType) xSTypeDefinition;
        }
        if (s == 2) {
            reportSchemaError("st-props-correct.3", new Object[]{str, qName.rawname}, element);
        } else if (s == 16) {
            reportSchemaError("cos-st-restricts.2.3.1.1", new Object[]{qName.rawname, str}, element);
        } else if (s == 8) {
            reportSchemaError("cos-st-restricts.3.3.1.1", new Object[]{qName.rawname, str}, element);
        }
        return null;
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

    /* JADX WARN: Code duplicated, block: B:128:0x0267  */
    /* JADX WARN: Code duplicated, block: B:130:0x026a A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:131:0x026c A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:133:0x0270  */
    /* JADX WARN: Code duplicated, block: B:134:0x0273  */
    /* JADX WARN: Code duplicated, block: B:145:0x0290 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:146:0x0292 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:149:0x029d  */
    /* JADX WARN: Code duplicated, block: B:150:0x029f  */
    /* JADX WARN: Code duplicated, block: B:154:0x02a8 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:160:0x02c0  */
    /* JADX WARN: Code duplicated, block: B:165:0x02e3  */
    /* JADX WARN: Code duplicated, block: B:167:0x02ee  */
    /* JADX WARN: Code duplicated, block: B:168:0x02f1  */
    /* JADX WARN: Code duplicated, block: B:170:0x0304  */
    /* JADX WARN: Code duplicated, block: B:172:0x0308  */
    /* JADX WARN: Code duplicated, block: B:174:0x0311  */
    /* JADX WARN: Code duplicated, block: B:175:0x0313  */
    /* JADX WARN: Code duplicated, block: B:177:0x0322 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:178:0x0324  */
    /* JADX WARN: Code duplicated, block: B:180:0x033c  */
    /* JADX WARN: Code duplicated, block: B:181:0x033e  */
    /* JADX WARN: Code duplicated, block: B:183:0x034c  */
    /* JADX WARN: Code duplicated, block: B:185:0x034f A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:196:0x0394 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:197:0x0396  */
    /* JADX WARN: Code duplicated, block: B:198:0x03a6 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:199:0x03a8  */
    /* JADX WARN: Code duplicated, block: B:200:0x03b8 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:201:0x03ba  */
    /* JADX WARN: Code duplicated, block: B:204:0x03cf  */
    /* JADX WARN: Code duplicated, block: B:22:0x0062  */
    /* JADX WARN: Code duplicated, block: B:24:0x0074  */
    /* JADX WARN: Code duplicated, block: B:26:0x0082  */
    /* JADX WARN: Code duplicated, block: B:27:0x008a  */
    /* JADX WARN: Code duplicated, block: B:29:0x0092  */
    /* JADX WARN: Code duplicated, block: B:30:0x009b  */
    /* JADX WARN: Code duplicated, block: B:32:0x00a3  */
    /* JADX WARN: Code duplicated, block: B:35:0x00b1  */
    /* JADX WARN: Code duplicated, block: B:38:0x00c6  */
    /* JADX WARN: Code duplicated, block: B:39:0x00c9  */
    /* JADX WARN: Code duplicated, block: B:42:0x00de  */
    /* JADX WARN: Code duplicated, block: B:44:0x00ea  */
    /* JADX WARN: Code duplicated, block: B:46:0x00f0 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:47:0x00f2  */
    /* JADX WARN: Code duplicated, block: B:48:0x00f7  */
    /* JADX WARN: Code duplicated, block: B:49:0x0103 A[PHI: r12
      0x0103: PHI (r12v14 org.apache.xerces.impl.xs.XSAnnotationImpl[]) = (r12v2 org.apache.xerces.impl.xs.XSAnnotationImpl[]), (r12v20 org.apache.xerces.impl.xs.XSAnnotationImpl[]) binds: [B:45:0x00ee, B:47:0x00f2] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:51:0x0111  */
    /* JADX WARN: Code duplicated, block: B:52:0x0112 A[PHI: r5
      0x0112: PHI (r5v2 char) = (r5v1 char), (r5v27 char) binds: [B:41:0x00dc, B:51:0x0111] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:54:0x0118  */
    /* JADX WARN: Code duplicated, block: B:56:0x0130  */
    /* JADX WARN: Code duplicated, block: B:57:0x0139  */
    /* JADX WARN: Code duplicated, block: B:58:0x0144  */
    /* JADX WARN: Code duplicated, block: B:65:0x0169  */
    /* JADX WARN: Code duplicated, block: B:73:0x017c  */
    /* JADX WARN: Code duplicated, block: B:91:0x01e9  */
    /* JADX WARN: Code duplicated, block: B:94:0x01ef  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v38, types: [org.apache.xerces.impl.dv.SchemaDVFactory] */
    /* JADX WARN: Type inference failed for: r28v0, types: [org.apache.xerces.impl.xs.traversers.XSDSimpleTypeTraverser] */
    /* JADX WARN: Type inference failed for: r6v21, types: [org.apache.xerces.xs.XSObjectList] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private XSSimpleType getSimpleType(String str, Element element, Object[] objArr, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        Element element2;
        XSAnnotationImpl[] xSAnnotationImplArr;
        XSAnnotationImpl[] xSAnnotationImplArr2;
        char c;
        String localName;
        boolean z;
        boolean z2;
        boolean z3;
        short s;
        Element nextSiblingElement;
        Object[] objArrCheckAttributes;
        int i;
        QName qName;
        Vector vector;
        Element firstChildElement;
        String syntheticAnnotation;
        Element element3;
        Vector vector2;
        Object[] objArr2;
        Element element4;
        char c2;
        XSAnnotationImpl[] xSAnnotationImplArr3;
        Element nextSiblingElement2;
        XSAnnotationImpl xSAnnotationImplTraverseSyntheticAnnotation;
        XSSimpleType xSSimpleTypeFindDTValidator;
        XSSimpleType xSSimpleTypeTraverseLocal;
        Vector vector3;
        ArrayList arrayList;
        short s2;
        String str2;
        Element element5;
        Object[] objArr3;
        Element element6;
        XSSimpleType xSSimpleType;
        short s3;
        XSAnnotationImpl[] xSAnnotationImplArr4;
        XSSimpleType xSSimpleTypeCreateTypeUnion;
        XSObjectListImpl xSObjectListImpl;
        XSObjectListImpl xSObjectListImpl2;
        Element element7;
        XSAnnotationImpl[] xSAnnotationImplArr5;
        Object xSObjectListImpl3;
        Vector vector4;
        XSAnnotationImpl xSAnnotationImplTraverseAnnotationDecl;
        char c3;
        XInt xInt = (XInt) objArr[XSAttributeChecker.ATTIDX_FINAL];
        int iIntValue = xInt == null ? xSDocumentInfo.fFinalDefault : xInt.intValue();
        Element firstChildElement2 = DOMUtil.getFirstChildElement(element);
        if (firstChildElement2 == null || !DOMUtil.getLocalName(firstChildElement2).equals(SchemaSymbols.ELT_ANNOTATION)) {
            String syntheticAnnotation2 = DOMUtil.getSyntheticAnnotation(element);
            if (syntheticAnnotation2 != null) {
                xSAnnotationImplArr2 = new XSAnnotationImpl[]{traverseSyntheticAnnotation(element, syntheticAnnotation2, objArr, false, xSDocumentInfo)};
            } else {
                element2 = firstChildElement2;
                xSAnnotationImplArr = null;
            }
            c = 2;
            if (element2 == null) {
                reportSchemaError("s4s-elt-must-match.2", new Object[]{SchemaSymbols.ELT_SIMPLETYPE, "(annotation?, (restriction | list | union))"}, element);
                return errorType(str, xSDocumentInfo.fTargetNamespace, (short) 2);
            }
            localName = DOMUtil.getLocalName(element2);
            if (localName.equals(SchemaSymbols.ELT_RESTRICTION)) {
                s = 2;
                z2 = true;
                z3 = false;
                z = false;
            } else if (localName.equals(SchemaSymbols.ELT_LIST)) {
                z3 = true;
                z2 = false;
                z = false;
                s = 16;
            } else {
                if (localName.equals(SchemaSymbols.ELT_UNION)) {
                    reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_SIMPLETYPE, "(annotation?, (restriction | list | union))", localName}, element);
                    return errorType(str, xSDocumentInfo.fTargetNamespace, (short) 2);
                }
                z = true;
                z2 = false;
                z3 = false;
                s = 8;
            }
            nextSiblingElement = DOMUtil.getNextSiblingElement(element2);
            if (nextSiblingElement != null) {
                reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_SIMPLETYPE, "(annotation?, (restriction | list | union))", DOMUtil.getLocalName(nextSiblingElement)}, nextSiblingElement);
            }
            objArrCheckAttributes = this.fAttrChecker.checkAttributes(element2, false, xSDocumentInfo);
            if (z2) {
                i = XSAttributeChecker.ATTIDX_BASE;
            } else {
                i = XSAttributeChecker.ATTIDX_ITEMTYPE;
            }
            qName = (QName) objArrCheckAttributes[i];
            vector = (Vector) objArrCheckAttributes[XSAttributeChecker.ATTIDX_MEMBERTYPES];
            firstChildElement = DOMUtil.getFirstChildElement(element2);
            if (firstChildElement == null) {
                syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element2);
                if (syntheticAnnotation != null) {
                    element3 = firstChildElement;
                    vector2 = vector;
                    objArr2 = objArrCheckAttributes;
                    element4 = element2;
                    xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element2, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo);
                    if (xSAnnotationImplArr == null) {
                        xSAnnotationImplArr3 = new XSAnnotationImpl[]{xSAnnotationImplTraverseSyntheticAnnotation};
                        nextSiblingElement2 = element3;
                        c2 = 2;
                    } else {
                        c2 = 2;
                        xSAnnotationImplArr3 = new XSAnnotationImpl[]{xSAnnotationImplArr[0], xSAnnotationImplTraverseSyntheticAnnotation};
                    }
                } else {
                    element3 = firstChildElement;
                    vector2 = vector;
                    objArr2 = objArrCheckAttributes;
                    element4 = element2;
                    c2 = c;
                    xSAnnotationImplArr3 = xSAnnotationImplArr;
                }
                nextSiblingElement2 = element3;
            } else if (DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
                xSAnnotationImplTraverseAnnotationDecl = traverseAnnotationDecl(firstChildElement, objArrCheckAttributes, false, xSDocumentInfo);
                if (xSAnnotationImplTraverseAnnotationDecl == null) {
                    c3 = 2;
                } else if (xSAnnotationImplArr == null) {
                    xSAnnotationImplArr = new XSAnnotationImpl[]{xSAnnotationImplTraverseAnnotationDecl};
                    c3 = 2;
                } else {
                    c3 = 2;
                    xSAnnotationImplArr = new XSAnnotationImpl[]{xSAnnotationImplArr[0], xSAnnotationImplTraverseAnnotationDecl};
                }
                vector2 = vector;
                objArr2 = objArrCheckAttributes;
                element4 = element2;
                c2 = c3;
                xSAnnotationImplArr3 = xSAnnotationImplArr;
                nextSiblingElement2 = DOMUtil.getNextSiblingElement(firstChildElement);
            } else {
                c = 2;
                syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element2);
                if (syntheticAnnotation != null) {
                    element3 = firstChildElement;
                    vector2 = vector;
                    objArr2 = objArrCheckAttributes;
                    element4 = element2;
                    xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element2, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo);
                    if (xSAnnotationImplArr == null) {
                        xSAnnotationImplArr3 = new XSAnnotationImpl[]{xSAnnotationImplTraverseSyntheticAnnotation};
                        nextSiblingElement2 = element3;
                        c2 = 2;
                    } else {
                        c2 = 2;
                        xSAnnotationImplArr3 = new XSAnnotationImpl[]{xSAnnotationImplArr[0], xSAnnotationImplTraverseSyntheticAnnotation};
                    }
                } else {
                    element3 = firstChildElement;
                    vector2 = vector;
                    objArr2 = objArrCheckAttributes;
                    element4 = element2;
                    c2 = c;
                    xSAnnotationImplArr3 = xSAnnotationImplArr;
                }
                nextSiblingElement2 = element3;
            }
            if ((!z2 || z3) && qName != null) {
                xSSimpleTypeFindDTValidator = findDTValidator(element4, str, qName, s, xSDocumentInfo);
                if (xSSimpleTypeFindDTValidator != null && this.fIsBuiltIn) {
                    this.fIsBuiltIn = false;
                    return null;
                }
                xSSimpleTypeTraverseLocal = xSSimpleTypeFindDTValidator;
            } else {
                xSSimpleTypeTraverseLocal = null;
            }
            short s4 = 3;
            if (z) {
                vector4 = vector2;
                if (vector4 != null || vector4.size() <= 0) {
                    vector3 = vector4;
                } else {
                    int size = vector4.size();
                    ArrayList arrayList2 = new ArrayList(size);
                    int i2 = 0;
                    while (i2 < size) {
                        int i3 = i2;
                        ArrayList arrayList3 = arrayList2;
                        int i4 = size;
                        Vector vector5 = vector4;
                        short s5 = s4;
                        XSSimpleType xSSimpleTypeFindDTValidator2 = findDTValidator(element4, str, (QName) vector4.elementAt(i2), (short) 8, xSDocumentInfo);
                        if (xSSimpleTypeFindDTValidator2 != null) {
                            if (xSSimpleTypeFindDTValidator2.getVariety() == s5) {
                                XSObjectList memberTypes = xSSimpleTypeFindDTValidator2.getMemberTypes();
                                for (int i5 = 0; i5 < memberTypes.getLength(); i5++) {
                                    arrayList3.add(memberTypes.item(i5));
                                }
                            } else {
                                arrayList3.add(xSSimpleTypeFindDTValidator2);
                            }
                        }
                        i2 = i3 + 1;
                        s4 = s5;
                        arrayList2 = arrayList3;
                        vector4 = vector5;
                        size = i4;
                    }
                    vector3 = vector4;
                    s4 = s4;
                    arrayList = arrayList2;
                }
                if (nextSiblingElement2 == null && DOMUtil.getLocalName(nextSiblingElement2).equals(SchemaSymbols.ELT_SIMPLETYPE)) {
                    if (z2 || z3) {
                        s2 = 2;
                        if (qName != null) {
                            reportSchemaError(z3 ? "src-simple-type.3.a" : "src-simple-type.2.a", null, nextSiblingElement2);
                        }
                        if (xSSimpleTypeTraverseLocal == null) {
                            xSSimpleTypeTraverseLocal = traverseLocal(nextSiblingElement2, xSDocumentInfo, schemaGrammar);
                        }
                        nextSiblingElement2 = DOMUtil.getNextSiblingElement(nextSiblingElement2);
                    } else if (z) {
                        if (arrayList == null) {
                            s2 = 2;
                            arrayList = new ArrayList(2);
                        } else {
                            s2 = 2;
                        }
                        do {
                            XSSimpleType xSSimpleTypeTraverseLocal2 = traverseLocal(nextSiblingElement2, xSDocumentInfo, schemaGrammar);
                            if (xSSimpleTypeTraverseLocal2 != null) {
                                if (xSSimpleTypeTraverseLocal2.getVariety() == s4) {
                                    XSObjectList memberTypes2 = xSSimpleTypeTraverseLocal2.getMemberTypes();
                                    for (int i6 = 0; i6 < memberTypes2.getLength(); i6++) {
                                        arrayList.add(memberTypes2.item(i6));
                                    }
                                } else {
                                    arrayList.add(xSSimpleTypeTraverseLocal2);
                                }
                            }
                            nextSiblingElement2 = DOMUtil.getNextSiblingElement(nextSiblingElement2);
                            if (nextSiblingElement2 == null) {
                                break;
                            }
                        } while (DOMUtil.getLocalName(nextSiblingElement2).equals(SchemaSymbols.ELT_SIMPLETYPE));
                    } else {
                        s2 = 2;
                        element5 = element4;
                        objArr3 = null;
                    }
                    element5 = element4;
                    element6 = nextSiblingElement2;
                    xSSimpleType = xSSimpleTypeTraverseLocal;
                    objArr3 = null;
                    if ((!z2 || z3) && xSSimpleType == null) {
                        this.fAttrChecker.returnAttrArray(objArr2, xSDocumentInfo);
                        String str3 = xSDocumentInfo.fTargetNamespace;
                        if (z2) {
                            s3 = s2;
                        } else {
                            s3 = 16;
                        }
                        return errorType(str, str3, s3);
                    }
                    if (!z && (arrayList == null || arrayList.size() == 0)) {
                        this.fAttrChecker.returnAttrArray(objArr2, xSDocumentInfo);
                        return errorType(str, xSDocumentInfo.fTargetNamespace, (short) 8);
                    }
                    if (!z3 && isListDatatype(xSSimpleType)) {
                        reportSchemaError("cos-st-restricts.2.1", new Object[]{str, xSSimpleType.getName()}, element5);
                        this.fAttrChecker.returnAttrArray(objArr2, xSDocumentInfo);
                        return errorType(str, xSDocumentInfo.fTargetNamespace, (short) 16);
                    }
                    if (z2) {
                        ?? r1 = this.fSchemaHandler.fDVFactory;
                        String str4 = xSDocumentInfo.fTargetNamespace;
                        short s6 = (short) iIntValue;
                        xSAnnotationImplArr5 = xSAnnotationImplArr3;
                        if (xSAnnotationImplArr5 == null) {
                            xSObjectListImpl3 = objArr3;
                        } else {
                            xSObjectListImpl3 = new XSObjectListImpl(xSAnnotationImplArr5, xSAnnotationImplArr5.length);
                        }
                        xSAnnotationImplArr4 = xSAnnotationImplArr5;
                        xSSimpleTypeCreateTypeUnion = r1.createTypeRestriction(str, str4, s6, xSSimpleType, xSObjectListImpl3);
                    } else {
                        xSAnnotationImplArr4 = xSAnnotationImplArr3;
                        if (z3) {
                            SchemaDVFactory schemaDVFactory = this.fSchemaHandler.fDVFactory;
                            String str5 = xSDocumentInfo.fTargetNamespace;
                            short s7 = (short) iIntValue;
                            if (xSAnnotationImplArr4 == null) {
                                xSObjectListImpl2 = null;
                            } else {
                                xSObjectListImpl2 = new XSObjectListImpl(xSAnnotationImplArr4, xSAnnotationImplArr4.length);
                            }
                            xSSimpleTypeCreateTypeUnion = schemaDVFactory.createTypeList(str, str5, s7, xSSimpleType, xSObjectListImpl2);
                        } else if (z) {
                            XSSimpleType[] xSSimpleTypeArr = (XSSimpleType[]) arrayList.toArray(new XSSimpleType[arrayList.size()]);
                            SchemaDVFactory schemaDVFactory2 = this.fSchemaHandler.fDVFactory;
                            String str6 = xSDocumentInfo.fTargetNamespace;
                            short s8 = (short) iIntValue;
                            if (xSAnnotationImplArr4 == null) {
                                xSObjectListImpl = null;
                            } else {
                                xSObjectListImpl = new XSObjectListImpl(xSAnnotationImplArr4, xSAnnotationImplArr4.length);
                            }
                            xSSimpleTypeCreateTypeUnion = schemaDVFactory2.createTypeUnion(str, str6, s8, xSSimpleTypeArr, xSObjectListImpl);
                        } else {
                            xSSimpleTypeCreateTypeUnion = null;
                        }
                    }
                    if (z2 && element6 != null) {
                        XSDAbstractTraverser.FacetInfo facetInfoTraverseFacets = traverseFacets(element6, xSSimpleTypeCreateTypeUnion, xSSimpleType, xSDocumentInfo);
                        element7 = facetInfoTraverseFacets.nodeAfterFacets;
                        try {
                            this.fValidationState.setNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
                            xSSimpleTypeCreateTypeUnion.applyFacets(facetInfoTraverseFacets.facetdata, facetInfoTraverseFacets.fPresentFacets, facetInfoTraverseFacets.fFixedFacets, this.fValidationState);
                            element6 = element7;
                        } catch (InvalidDatatypeFacetException e) {
                            reportSchemaError(e.getKey(), e.getArgs(), element5);
                            xSSimpleTypeCreateTypeUnion = this.fSchemaHandler.fDVFactory.createTypeRestriction(str, xSDocumentInfo.fTargetNamespace, (short) iIntValue, xSSimpleType, xSAnnotationImplArr4 == null ? null : new XSObjectListImpl(xSAnnotationImplArr4, xSAnnotationImplArr4.length));
                            element6 = element7;
                        }
                    }
                    if (element6 != null) {
                        if (z2) {
                            reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_RESTRICTION, "(annotation?, (simpleType?, (minExclusive | minInclusive | maxExclusive | maxInclusive | totalDigits | fractionDigits | length | minLength | maxLength | enumeration | whiteSpace | pattern)*))", DOMUtil.getLocalName(element6)}, element6);
                        } else if (z3) {
                            reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_LIST, "(annotation?, (simpleType?))", DOMUtil.getLocalName(element6)}, element6);
                        } else if (z) {
                            reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_UNION, "(annotation?, (simpleType*))", DOMUtil.getLocalName(element6)}, element6);
                        }
                    }
                    this.fAttrChecker.returnAttrArray(objArr2, xSDocumentInfo);
                    return xSSimpleTypeCreateTypeUnion;
                }
                s2 = 2;
                if ((!z2 || z3) && qName == null) {
                    if (z3) {
                        str2 = "src-simple-type.3.b";
                    } else {
                        str2 = "src-simple-type.2.b";
                    }
                    element5 = element4;
                    objArr3 = null;
                } else {
                    element5 = element4;
                    objArr3 = null;
                    if (z && (vector3 == null || vector3.size() == 0)) {
                        str2 = "src-union-memberTypes-or-simpleTypes";
                    }
                }
                reportSchemaError(str2, objArr3, element5);
                element6 = nextSiblingElement2;
                xSSimpleType = xSSimpleTypeTraverseLocal;
                if (z2) {
                    this.fAttrChecker.returnAttrArray(objArr2, xSDocumentInfo);
                    String str7 = xSDocumentInfo.fTargetNamespace;
                    if (z2) {
                        s3 = s2;
                    } else {
                        s3 = 16;
                    }
                    return errorType(str, str7, s3);
                }
                this.fAttrChecker.returnAttrArray(objArr2, xSDocumentInfo);
                String str8 = xSDocumentInfo.fTargetNamespace;
                if (z2) {
                    s3 = s2;
                } else {
                    s3 = 16;
                }
                return errorType(str, str8, s3);
                if (!z) {
                }
                if (!z3) {
                }
                if (z2) {
                    ?? r2 = this.fSchemaHandler.fDVFactory;
                    String str9 = xSDocumentInfo.fTargetNamespace;
                    short s9 = (short) iIntValue;
                    xSAnnotationImplArr5 = xSAnnotationImplArr3;
                    if (xSAnnotationImplArr5 == null) {
                        xSObjectListImpl3 = objArr3;
                    } else {
                        xSObjectListImpl3 = new XSObjectListImpl(xSAnnotationImplArr5, xSAnnotationImplArr5.length);
                    }
                    xSAnnotationImplArr4 = xSAnnotationImplArr5;
                    xSSimpleTypeCreateTypeUnion = r2.createTypeRestriction(str, str9, s9, xSSimpleType, xSObjectListImpl3);
                } else {
                    xSAnnotationImplArr4 = xSAnnotationImplArr3;
                    if (z3) {
                        SchemaDVFactory schemaDVFactory3 = this.fSchemaHandler.fDVFactory;
                        String str10 = xSDocumentInfo.fTargetNamespace;
                        short s10 = (short) iIntValue;
                        if (xSAnnotationImplArr4 == null) {
                            xSObjectListImpl2 = null;
                        } else {
                            xSObjectListImpl2 = new XSObjectListImpl(xSAnnotationImplArr4, xSAnnotationImplArr4.length);
                        }
                        xSSimpleTypeCreateTypeUnion = schemaDVFactory3.createTypeList(str, str10, s10, xSSimpleType, xSObjectListImpl2);
                    } else if (z) {
                        XSSimpleType[] xSSimpleTypeArr2 = (XSSimpleType[]) arrayList.toArray(new XSSimpleType[arrayList.size()]);
                        SchemaDVFactory schemaDVFactory4 = this.fSchemaHandler.fDVFactory;
                        String str11 = xSDocumentInfo.fTargetNamespace;
                        short s11 = (short) iIntValue;
                        if (xSAnnotationImplArr4 == null) {
                            xSObjectListImpl = null;
                        } else {
                            xSObjectListImpl = new XSObjectListImpl(xSAnnotationImplArr4, xSAnnotationImplArr4.length);
                        }
                        xSSimpleTypeCreateTypeUnion = schemaDVFactory4.createTypeUnion(str, str11, s11, xSSimpleTypeArr2, xSObjectListImpl);
                    } else {
                        xSSimpleTypeCreateTypeUnion = null;
                    }
                }
                if (z2) {
                    XSDAbstractTraverser.FacetInfo facetInfoTraverseFacets2 = traverseFacets(element6, xSSimpleTypeCreateTypeUnion, xSSimpleType, xSDocumentInfo);
                    element7 = facetInfoTraverseFacets2.nodeAfterFacets;
                    this.fValidationState.setNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
                    xSSimpleTypeCreateTypeUnion.applyFacets(facetInfoTraverseFacets2.facetdata, facetInfoTraverseFacets2.fPresentFacets, facetInfoTraverseFacets2.fFixedFacets, this.fValidationState);
                    element6 = element7;
                }
                if (element6 != null) {
                    if (z2) {
                        reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_RESTRICTION, "(annotation?, (simpleType?, (minExclusive | minInclusive | maxExclusive | maxInclusive | totalDigits | fractionDigits | length | minLength | maxLength | enumeration | whiteSpace | pattern)*))", DOMUtil.getLocalName(element6)}, element6);
                    } else if (z3) {
                        reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_LIST, "(annotation?, (simpleType?))", DOMUtil.getLocalName(element6)}, element6);
                    } else if (z) {
                        reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_UNION, "(annotation?, (simpleType*))", DOMUtil.getLocalName(element6)}, element6);
                    }
                }
                this.fAttrChecker.returnAttrArray(objArr2, xSDocumentInfo);
                return xSSimpleTypeCreateTypeUnion;
            }
            vector3 = vector2;
            arrayList = null;
            if (nextSiblingElement2 == null) {
                s2 = 2;
                if (z2) {
                    if (z3) {
                        str2 = "src-simple-type.3.b";
                    } else {
                        str2 = "src-simple-type.2.b";
                    }
                    element5 = element4;
                    objArr3 = null;
                } else {
                    if (z3) {
                        str2 = "src-simple-type.3.b";
                    } else {
                        str2 = "src-simple-type.2.b";
                    }
                    element5 = element4;
                    objArr3 = null;
                }
                reportSchemaError(str2, objArr3, element5);
                element6 = nextSiblingElement2;
                xSSimpleType = xSSimpleTypeTraverseLocal;
            } else {
                s2 = 2;
                if (z2) {
                    if (z3) {
                        str2 = "src-simple-type.3.b";
                    } else {
                        str2 = "src-simple-type.2.b";
                    }
                    element5 = element4;
                    objArr3 = null;
                } else {
                    if (z3) {
                        str2 = "src-simple-type.3.b";
                    } else {
                        str2 = "src-simple-type.2.b";
                    }
                    element5 = element4;
                    objArr3 = null;
                }
                reportSchemaError(str2, objArr3, element5);
                element6 = nextSiblingElement2;
                xSSimpleType = xSSimpleTypeTraverseLocal;
            }
            if (z2) {
                this.fAttrChecker.returnAttrArray(objArr2, xSDocumentInfo);
                String str12 = xSDocumentInfo.fTargetNamespace;
                if (z2) {
                    s3 = s2;
                } else {
                    s3 = 16;
                }
                return errorType(str, str12, s3);
            }
            this.fAttrChecker.returnAttrArray(objArr2, xSDocumentInfo);
            String str13 = xSDocumentInfo.fTargetNamespace;
            if (z2) {
                s3 = s2;
            } else {
                s3 = 16;
            }
            return errorType(str, str13, s3);
            if (!z) {
            }
            if (!z3) {
            }
            if (z2) {
                ?? r3 = this.fSchemaHandler.fDVFactory;
                String str14 = xSDocumentInfo.fTargetNamespace;
                short s12 = (short) iIntValue;
                xSAnnotationImplArr5 = xSAnnotationImplArr3;
                if (xSAnnotationImplArr5 == null) {
                    xSObjectListImpl3 = objArr3;
                } else {
                    xSObjectListImpl3 = new XSObjectListImpl(xSAnnotationImplArr5, xSAnnotationImplArr5.length);
                }
                xSAnnotationImplArr4 = xSAnnotationImplArr5;
                xSSimpleTypeCreateTypeUnion = r3.createTypeRestriction(str, str14, s12, xSSimpleType, xSObjectListImpl3);
            } else {
                xSAnnotationImplArr4 = xSAnnotationImplArr3;
                if (z3) {
                    SchemaDVFactory schemaDVFactory5 = this.fSchemaHandler.fDVFactory;
                    String str15 = xSDocumentInfo.fTargetNamespace;
                    short s13 = (short) iIntValue;
                    if (xSAnnotationImplArr4 == null) {
                        xSObjectListImpl2 = null;
                    } else {
                        xSObjectListImpl2 = new XSObjectListImpl(xSAnnotationImplArr4, xSAnnotationImplArr4.length);
                    }
                    xSSimpleTypeCreateTypeUnion = schemaDVFactory5.createTypeList(str, str15, s13, xSSimpleType, xSObjectListImpl2);
                } else if (z) {
                    XSSimpleType[] xSSimpleTypeArr3 = (XSSimpleType[]) arrayList.toArray(new XSSimpleType[arrayList.size()]);
                    SchemaDVFactory schemaDVFactory6 = this.fSchemaHandler.fDVFactory;
                    String str16 = xSDocumentInfo.fTargetNamespace;
                    short s14 = (short) iIntValue;
                    if (xSAnnotationImplArr4 == null) {
                        xSObjectListImpl = null;
                    } else {
                        xSObjectListImpl = new XSObjectListImpl(xSAnnotationImplArr4, xSAnnotationImplArr4.length);
                    }
                    xSSimpleTypeCreateTypeUnion = schemaDVFactory6.createTypeUnion(str, str16, s14, xSSimpleTypeArr3, xSObjectListImpl);
                } else {
                    xSSimpleTypeCreateTypeUnion = null;
                }
            }
            if (z2) {
                XSDAbstractTraverser.FacetInfo facetInfoTraverseFacets3 = traverseFacets(element6, xSSimpleTypeCreateTypeUnion, xSSimpleType, xSDocumentInfo);
                element7 = facetInfoTraverseFacets3.nodeAfterFacets;
                this.fValidationState.setNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
                xSSimpleTypeCreateTypeUnion.applyFacets(facetInfoTraverseFacets3.facetdata, facetInfoTraverseFacets3.fPresentFacets, facetInfoTraverseFacets3.fFixedFacets, this.fValidationState);
                element6 = element7;
            }
            if (element6 != null) {
                if (z2) {
                    reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_RESTRICTION, "(annotation?, (simpleType?, (minExclusive | minInclusive | maxExclusive | maxInclusive | totalDigits | fractionDigits | length | minLength | maxLength | enumeration | whiteSpace | pattern)*))", DOMUtil.getLocalName(element6)}, element6);
                } else if (z3) {
                    reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_LIST, "(annotation?, (simpleType?))", DOMUtil.getLocalName(element6)}, element6);
                } else if (z) {
                    reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_UNION, "(annotation?, (simpleType*))", DOMUtil.getLocalName(element6)}, element6);
                }
            }
            this.fAttrChecker.returnAttrArray(objArr2, xSDocumentInfo);
            return xSSimpleTypeCreateTypeUnion;
        }
        XSAnnotationImpl xSAnnotationImplTraverseAnnotationDecl2 = traverseAnnotationDecl(firstChildElement2, objArr, false, xSDocumentInfo);
        xSAnnotationImplArr2 = xSAnnotationImplTraverseAnnotationDecl2 != null ? new XSAnnotationImpl[]{xSAnnotationImplTraverseAnnotationDecl2} : null;
        firstChildElement2 = DOMUtil.getNextSiblingElement(firstChildElement2);
        element2 = firstChildElement2;
        xSAnnotationImplArr = xSAnnotationImplArr2;
        c = 2;
        if (element2 == null) {
            reportSchemaError("s4s-elt-must-match.2", new Object[]{SchemaSymbols.ELT_SIMPLETYPE, "(annotation?, (restriction | list | union))"}, element);
            return errorType(str, xSDocumentInfo.fTargetNamespace, (short) 2);
        }
        localName = DOMUtil.getLocalName(element2);
        if (localName.equals(SchemaSymbols.ELT_RESTRICTION)) {
            s = 2;
            z2 = true;
            z3 = false;
            z = false;
        } else if (localName.equals(SchemaSymbols.ELT_LIST)) {
            z3 = true;
            z2 = false;
            z = false;
            s = 16;
        } else {
            if (localName.equals(SchemaSymbols.ELT_UNION)) {
                reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_SIMPLETYPE, "(annotation?, (restriction | list | union))", localName}, element);
                return errorType(str, xSDocumentInfo.fTargetNamespace, (short) 2);
            }
            z = true;
            z2 = false;
            z3 = false;
            s = 8;
        }
        nextSiblingElement = DOMUtil.getNextSiblingElement(element2);
        if (nextSiblingElement != null) {
            reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_SIMPLETYPE, "(annotation?, (restriction | list | union))", DOMUtil.getLocalName(nextSiblingElement)}, nextSiblingElement);
        }
        objArrCheckAttributes = this.fAttrChecker.checkAttributes(element2, false, xSDocumentInfo);
        if (z2) {
            i = XSAttributeChecker.ATTIDX_BASE;
        } else {
            i = XSAttributeChecker.ATTIDX_ITEMTYPE;
        }
        qName = (QName) objArrCheckAttributes[i];
        vector = (Vector) objArrCheckAttributes[XSAttributeChecker.ATTIDX_MEMBERTYPES];
        firstChildElement = DOMUtil.getFirstChildElement(element2);
        if (firstChildElement == null) {
            syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element2);
            if (syntheticAnnotation != null) {
                element3 = firstChildElement;
                vector2 = vector;
                objArr2 = objArrCheckAttributes;
                element4 = element2;
                xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element2, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo);
                if (xSAnnotationImplArr == null) {
                    xSAnnotationImplArr3 = new XSAnnotationImpl[]{xSAnnotationImplTraverseSyntheticAnnotation};
                    nextSiblingElement2 = element3;
                    c2 = 2;
                } else {
                    c2 = 2;
                    xSAnnotationImplArr3 = new XSAnnotationImpl[]{xSAnnotationImplArr[0], xSAnnotationImplTraverseSyntheticAnnotation};
                }
            } else {
                element3 = firstChildElement;
                vector2 = vector;
                objArr2 = objArrCheckAttributes;
                element4 = element2;
                c2 = c;
                xSAnnotationImplArr3 = xSAnnotationImplArr;
            }
            nextSiblingElement2 = element3;
        } else if (DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
            xSAnnotationImplTraverseAnnotationDecl = traverseAnnotationDecl(firstChildElement, objArrCheckAttributes, false, xSDocumentInfo);
            if (xSAnnotationImplTraverseAnnotationDecl == null) {
                c3 = 2;
            } else if (xSAnnotationImplArr == null) {
                xSAnnotationImplArr = new XSAnnotationImpl[]{xSAnnotationImplTraverseAnnotationDecl};
                c3 = 2;
            } else {
                c3 = 2;
                xSAnnotationImplArr = new XSAnnotationImpl[]{xSAnnotationImplArr[0], xSAnnotationImplTraverseAnnotationDecl};
            }
            vector2 = vector;
            objArr2 = objArrCheckAttributes;
            element4 = element2;
            c2 = c3;
            xSAnnotationImplArr3 = xSAnnotationImplArr;
            nextSiblingElement2 = DOMUtil.getNextSiblingElement(firstChildElement);
        } else {
            c = 2;
            syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element2);
            if (syntheticAnnotation != null) {
                element3 = firstChildElement;
                vector2 = vector;
                objArr2 = objArrCheckAttributes;
                element4 = element2;
                xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element2, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo);
                if (xSAnnotationImplArr == null) {
                    xSAnnotationImplArr3 = new XSAnnotationImpl[]{xSAnnotationImplTraverseSyntheticAnnotation};
                    nextSiblingElement2 = element3;
                    c2 = 2;
                } else {
                    c2 = 2;
                    xSAnnotationImplArr3 = new XSAnnotationImpl[]{xSAnnotationImplArr[0], xSAnnotationImplTraverseSyntheticAnnotation};
                }
            } else {
                element3 = firstChildElement;
                vector2 = vector;
                objArr2 = objArrCheckAttributes;
                element4 = element2;
                c2 = c;
                xSAnnotationImplArr3 = xSAnnotationImplArr;
            }
            nextSiblingElement2 = element3;
        }
        if (z2) {
            xSSimpleTypeFindDTValidator = findDTValidator(element4, str, qName, s, xSDocumentInfo);
            if (xSSimpleTypeFindDTValidator != null) {
            }
            xSSimpleTypeTraverseLocal = xSSimpleTypeFindDTValidator;
        } else {
            xSSimpleTypeFindDTValidator = findDTValidator(element4, str, qName, s, xSDocumentInfo);
            if (xSSimpleTypeFindDTValidator != null) {
            }
            xSSimpleTypeTraverseLocal = xSSimpleTypeFindDTValidator;
        }
        short s15 = 3;
        if (z) {
            vector4 = vector2;
            if (vector4 != null) {
            }
            vector3 = vector4;
        } else {
            vector3 = vector2;
        }
        arrayList = null;
        if (nextSiblingElement2 == null) {
            s2 = 2;
            if (z2) {
                if (z3) {
                    str2 = "src-simple-type.3.b";
                } else {
                    str2 = "src-simple-type.2.b";
                }
                element5 = element4;
                objArr3 = null;
            } else {
                if (z3) {
                    str2 = "src-simple-type.3.b";
                } else {
                    str2 = "src-simple-type.2.b";
                }
                element5 = element4;
                objArr3 = null;
            }
            reportSchemaError(str2, objArr3, element5);
            element6 = nextSiblingElement2;
            xSSimpleType = xSSimpleTypeTraverseLocal;
        } else {
            s2 = 2;
            if (z2) {
                if (z3) {
                    str2 = "src-simple-type.3.b";
                } else {
                    str2 = "src-simple-type.2.b";
                }
                element5 = element4;
                objArr3 = null;
            } else {
                if (z3) {
                    str2 = "src-simple-type.3.b";
                } else {
                    str2 = "src-simple-type.2.b";
                }
                element5 = element4;
                objArr3 = null;
            }
            reportSchemaError(str2, objArr3, element5);
            element6 = nextSiblingElement2;
            xSSimpleType = xSSimpleTypeTraverseLocal;
        }
        if (z2) {
            this.fAttrChecker.returnAttrArray(objArr2, xSDocumentInfo);
            String str17 = xSDocumentInfo.fTargetNamespace;
            if (z2) {
                s3 = s2;
            } else {
                s3 = 16;
            }
            return errorType(str, str17, s3);
        }
        this.fAttrChecker.returnAttrArray(objArr2, xSDocumentInfo);
        String str18 = xSDocumentInfo.fTargetNamespace;
        if (z2) {
            s3 = s2;
        } else {
            s3 = 16;
        }
        return errorType(str, str18, s3);
        if (!z) {
        }
        if (!z3) {
        }
        if (z2) {
            ?? r4 = this.fSchemaHandler.fDVFactory;
            String str19 = xSDocumentInfo.fTargetNamespace;
            short s16 = (short) iIntValue;
            xSAnnotationImplArr5 = xSAnnotationImplArr3;
            if (xSAnnotationImplArr5 == null) {
                xSObjectListImpl3 = objArr3;
            } else {
                xSObjectListImpl3 = new XSObjectListImpl(xSAnnotationImplArr5, xSAnnotationImplArr5.length);
            }
            xSAnnotationImplArr4 = xSAnnotationImplArr5;
            xSSimpleTypeCreateTypeUnion = r4.createTypeRestriction(str, str19, s16, xSSimpleType, xSObjectListImpl3);
        } else {
            xSAnnotationImplArr4 = xSAnnotationImplArr3;
            if (z3) {
                SchemaDVFactory schemaDVFactory7 = this.fSchemaHandler.fDVFactory;
                String str110 = xSDocumentInfo.fTargetNamespace;
                short s17 = (short) iIntValue;
                if (xSAnnotationImplArr4 == null) {
                    xSObjectListImpl2 = null;
                } else {
                    xSObjectListImpl2 = new XSObjectListImpl(xSAnnotationImplArr4, xSAnnotationImplArr4.length);
                }
                xSSimpleTypeCreateTypeUnion = schemaDVFactory7.createTypeList(str, str110, s17, xSSimpleType, xSObjectListImpl2);
            } else if (z) {
                XSSimpleType[] xSSimpleTypeArr4 = (XSSimpleType[]) arrayList.toArray(new XSSimpleType[arrayList.size()]);
                SchemaDVFactory schemaDVFactory8 = this.fSchemaHandler.fDVFactory;
                String str111 = xSDocumentInfo.fTargetNamespace;
                short s18 = (short) iIntValue;
                if (xSAnnotationImplArr4 == null) {
                    xSObjectListImpl = null;
                } else {
                    xSObjectListImpl = new XSObjectListImpl(xSAnnotationImplArr4, xSAnnotationImplArr4.length);
                }
                xSSimpleTypeCreateTypeUnion = schemaDVFactory8.createTypeUnion(str, str111, s18, xSSimpleTypeArr4, xSObjectListImpl);
            } else {
                xSSimpleTypeCreateTypeUnion = null;
            }
        }
        if (z2) {
            XSDAbstractTraverser.FacetInfo facetInfoTraverseFacets4 = traverseFacets(element6, xSSimpleTypeCreateTypeUnion, xSSimpleType, xSDocumentInfo);
            element7 = facetInfoTraverseFacets4.nodeAfterFacets;
            this.fValidationState.setNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
            xSSimpleTypeCreateTypeUnion.applyFacets(facetInfoTraverseFacets4.facetdata, facetInfoTraverseFacets4.fPresentFacets, facetInfoTraverseFacets4.fFixedFacets, this.fValidationState);
            element6 = element7;
        }
        if (element6 != null) {
            if (z2) {
                reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_RESTRICTION, "(annotation?, (simpleType?, (minExclusive | minInclusive | maxExclusive | maxInclusive | totalDigits | fractionDigits | length | minLength | maxLength | enumeration | whiteSpace | pattern)*))", DOMUtil.getLocalName(element6)}, element6);
            } else if (z3) {
                reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_LIST, "(annotation?, (simpleType?))", DOMUtil.getLocalName(element6)}, element6);
            } else if (z) {
                reportSchemaError("s4s-elt-must-match.1", new Object[]{SchemaSymbols.ELT_UNION, "(annotation?, (simpleType*))", DOMUtil.getLocalName(element6)}, element6);
            }
        }
        this.fAttrChecker.returnAttrArray(objArr2, xSDocumentInfo);
        return xSSimpleTypeCreateTypeUnion;
    }

    private boolean isListDatatype(XSSimpleType xSSimpleType) {
        if (xSSimpleType.getVariety() == 2) {
            return true;
        }
        if (xSSimpleType.getVariety() == 3) {
            XSObjectList memberTypes = xSSimpleType.getMemberTypes();
            for (int i = 0; i < memberTypes.getLength(); i++) {
                if (((XSSimpleType) memberTypes.item(i)).getVariety() == 2) {
                    return true;
                }
            }
        }
        return false;
    }

    private XSSimpleType traverseSimpleTypeDecl(Element element, Object[] objArr, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        return getSimpleType((String) objArr[XSAttributeChecker.ATTIDX_NAME], element, objArr, xSDocumentInfo, schemaGrammar);
    }

    XSSimpleType traverseGlobal(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, true, xSDocumentInfo);
        String str = (String) objArrCheckAttributes[XSAttributeChecker.ATTIDX_NAME];
        if (str == null) {
            objArrCheckAttributes[XSAttributeChecker.ATTIDX_NAME] = "(no name)";
        }
        XSSimpleType xSSimpleTypeTraverseSimpleTypeDecl = traverseSimpleTypeDecl(element, objArrCheckAttributes, xSDocumentInfo, schemaGrammar);
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
        if (str == null) {
            reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_SIMPLETYPE, SchemaSymbols.ATT_NAME}, element);
            xSSimpleTypeTraverseSimpleTypeDecl = null;
        }
        if (xSSimpleTypeTraverseSimpleTypeDecl != null) {
            if (schemaGrammar.getGlobalTypeDecl(xSSimpleTypeTraverseSimpleTypeDecl.getName()) == null) {
                schemaGrammar.addGlobalSimpleTypeDecl(xSSimpleTypeTraverseSimpleTypeDecl);
            }
            String strSchemaDocument2SystemId = this.fSchemaHandler.schemaDocument2SystemId(xSDocumentInfo);
            XSTypeDefinition globalTypeDecl = schemaGrammar.getGlobalTypeDecl(xSSimpleTypeTraverseSimpleTypeDecl.getName(), strSchemaDocument2SystemId);
            if (globalTypeDecl == null) {
                schemaGrammar.addGlobalSimpleTypeDecl(xSSimpleTypeTraverseSimpleTypeDecl, strSchemaDocument2SystemId);
            }
            if (this.fSchemaHandler.fTolerateDuplicates) {
                if (globalTypeDecl != null && (globalTypeDecl instanceof XSSimpleType)) {
                    xSSimpleTypeTraverseSimpleTypeDecl = (XSSimpleType) globalTypeDecl;
                }
                this.fSchemaHandler.addGlobalTypeDecl(xSSimpleTypeTraverseSimpleTypeDecl);
            }
        }
        return xSSimpleTypeTraverseSimpleTypeDecl;
    }

    XSSimpleType traverseLocal(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, false, xSDocumentInfo);
        XSSimpleType simpleType = getSimpleType(genAnonTypeName(element), element, objArrCheckAttributes, xSDocumentInfo, schemaGrammar);
        if (simpleType instanceof XSSimpleTypeDecl) {
            ((XSSimpleTypeDecl) simpleType).setAnonymous(true);
        }
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
        return simpleType;
    }
}
