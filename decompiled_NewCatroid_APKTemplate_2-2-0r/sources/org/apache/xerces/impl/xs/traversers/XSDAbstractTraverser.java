package org.apache.xerces.impl.xs.traversers;

import java.util.Locale;
import java.util.Vector;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidatedInfo;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.impl.dv.XSFacets;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.dv.util.Base64;
import org.apache.xerces.impl.dv.xs.XSSimpleTypeDecl;
import org.apache.xerces.impl.validation.ValidationState;
import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAnnotationImpl;
import org.apache.xerces.impl.xs.XSAttributeGroupDecl;
import org.apache.xerces.impl.xs.XSAttributeUseImpl;
import org.apache.xerces.impl.xs.XSComplexTypeDecl;
import org.apache.xerces.impl.xs.XSElementDecl;
import org.apache.xerces.impl.xs.XSParticleDecl;
import org.apache.xerces.impl.xs.XSWildcardDecl;
import org.apache.xerces.impl.xs.util.XInt;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.util.NamespaceSupport;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xs.XSAttributeUse;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSSimpleTypeDefinition;
import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes13.dex */
abstract class XSDAbstractTraverser {
    protected static final int CHILD_OF_GROUP = 4;
    protected static final int GROUP_REF_WITH_ALL = 2;
    protected static final int NOT_ALL_CONTEXT = 0;
    protected static final String NO_NAME = "(no name)";
    protected static final int PROCESSING_ALL_EL = 1;
    protected static final int PROCESSING_ALL_GP = 8;
    private static final XSSimpleType fQNameDV = (XSSimpleType) SchemaGrammar.SG_SchemaNS.getGlobalTypeDecl(SchemaSymbols.ATTVAL_QNAME);
    protected XSAttributeChecker fAttrChecker;
    protected XSDHandler fSchemaHandler;
    protected SymbolTable fSymbolTable = null;
    protected boolean fValidateAnnotations = false;
    ValidationState fValidationState = new ValidationState();
    private StringBuffer fPattern = new StringBuffer();
    private final XSFacets xsFacets = new XSFacets();

    static final class FacetInfo {
        final short fFixedFacets;
        final short fPresentFacets;
        final XSFacets facetdata;
        final Element nodeAfterFacets;

        FacetInfo(XSFacets xSFacets, Element element, short s, short s2) {
            this.facetdata = xSFacets;
            this.nodeAfterFacets = element;
            this.fPresentFacets = s;
            this.fFixedFacets = s2;
        }
    }

    XSDAbstractTraverser(XSDHandler xSDHandler, XSAttributeChecker xSAttributeChecker) {
        this.fSchemaHandler = null;
        this.fAttrChecker = null;
        this.fSchemaHandler = xSDHandler;
        this.fAttrChecker = xSAttributeChecker;
    }

    private void checkEnumerationAndLengthInconsistency(XSSimpleType xSSimpleType, Vector vector, Element element, String str) {
        int i = 0;
        if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(xSSimpleType.getNamespace()) && SchemaSymbols.ATTVAL_HEXBINARY.equals(xSSimpleType.getName())) {
            while (i < vector.size()) {
                String str2 = (String) vector.get(i);
                if (str2.length() / 2 != this.xsFacets.length) {
                    reportSchemaWarning("FacetsContradict", new Object[]{str2, SchemaSymbols.ELT_LENGTH, str}, element);
                }
                i++;
            }
            return;
        }
        if (!SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(xSSimpleType.getNamespace()) || !SchemaSymbols.ATTVAL_BASE64BINARY.equals(xSSimpleType.getName())) {
            while (i < vector.size()) {
                String str3 = (String) vector.get(i);
                if (str3.length() != this.xsFacets.length) {
                    reportSchemaWarning("FacetsContradict", new Object[]{str3, SchemaSymbols.ELT_LENGTH, str}, element);
                }
                i++;
            }
            return;
        }
        while (i < vector.size()) {
            String str4 = (String) vector.get(i);
            byte[] bArrDecode = Base64.decode(str4);
            if (bArrDecode != null && new String(bArrDecode).length() != this.xsFacets.length) {
                reportSchemaWarning("FacetsContradict", new Object[]{str4, SchemaSymbols.ELT_LENGTH, str}, element);
            }
            i++;
        }
    }

    private void checkEnumerationAndMaxLengthInconsistency(XSSimpleType xSSimpleType, Vector vector, Element element, String str) {
        int i = 0;
        if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(xSSimpleType.getNamespace()) && SchemaSymbols.ATTVAL_HEXBINARY.equals(xSSimpleType.getName())) {
            while (i < vector.size()) {
                String str2 = (String) vector.get(i);
                if (str2.length() / 2 > this.xsFacets.maxLength) {
                    reportSchemaWarning("FacetsContradict", new Object[]{str2, SchemaSymbols.ELT_MAXLENGTH, str}, element);
                }
                i++;
            }
            return;
        }
        if (!SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(xSSimpleType.getNamespace()) || !SchemaSymbols.ATTVAL_BASE64BINARY.equals(xSSimpleType.getName())) {
            while (i < vector.size()) {
                String str3 = (String) vector.get(i);
                if (str3.length() > this.xsFacets.maxLength) {
                    reportSchemaWarning("FacetsContradict", new Object[]{str3, SchemaSymbols.ELT_MAXLENGTH, str}, element);
                }
                i++;
            }
            return;
        }
        while (i < vector.size()) {
            String str4 = (String) vector.get(i);
            byte[] bArrDecode = Base64.decode(str4);
            if (bArrDecode != null && new String(bArrDecode).length() > this.xsFacets.maxLength) {
                reportSchemaWarning("FacetsContradict", new Object[]{str4, SchemaSymbols.ELT_MAXLENGTH, str}, element);
            }
            i++;
        }
    }

    private void checkEnumerationAndMinLengthInconsistency(XSSimpleType xSSimpleType, Vector vector, Element element, String str) {
        int i = 0;
        if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(xSSimpleType.getNamespace()) && SchemaSymbols.ATTVAL_HEXBINARY.equals(xSSimpleType.getName())) {
            while (i < vector.size()) {
                String str2 = (String) vector.get(i);
                if (str2.length() / 2 < this.xsFacets.minLength) {
                    reportSchemaWarning("FacetsContradict", new Object[]{str2, SchemaSymbols.ELT_MINLENGTH, str}, element);
                }
                i++;
            }
            return;
        }
        if (!SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(xSSimpleType.getNamespace()) || !SchemaSymbols.ATTVAL_BASE64BINARY.equals(xSSimpleType.getName())) {
            while (i < vector.size()) {
                String str3 = (String) vector.get(i);
                if (str3.length() < this.xsFacets.minLength) {
                    reportSchemaWarning("FacetsContradict", new Object[]{str3, SchemaSymbols.ELT_MINLENGTH, str}, element);
                }
                i++;
            }
            return;
        }
        while (i < vector.size()) {
            String str4 = (String) vector.get(i);
            byte[] bArrDecode = Base64.decode(str4);
            if (bArrDecode != null && new String(bArrDecode).length() < this.xsFacets.minLength) {
                reportSchemaWarning("FacetsContradict", new Object[]{str4, SchemaSymbols.ELT_MINLENGTH, str}, element);
            }
            i++;
        }
    }

    private boolean containsQName(XSSimpleType xSSimpleType) {
        if (xSSimpleType.getVariety() == 1) {
            short primitiveKind = xSSimpleType.getPrimitiveKind();
            return primitiveKind == 18 || primitiveKind == 20;
        }
        if (xSSimpleType.getVariety() == 2) {
            return containsQName((XSSimpleType) xSSimpleType.getItemType());
        }
        if (xSSimpleType.getVariety() == 3) {
            XSObjectList memberTypes = xSSimpleType.getMemberTypes();
            for (int i = 0; i < memberTypes.getLength(); i++) {
                if (containsQName((XSSimpleType) memberTypes.item(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String escapeAttValue(String str, int i) {
        String str2;
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length);
        stringBuffer.append(str.substring(0, i));
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\"') {
                str2 = "&quot;";
            } else if (cCharAt == '<') {
                str2 = "&lt;";
            } else if (cCharAt == '&') {
                str2 = "&amp;";
            } else if (cCharAt == '\t') {
                str2 = "&#x9;";
            } else if (cCharAt == '\n') {
                str2 = "&#xA;";
            } else {
                if (cCharAt == '\r') {
                    str2 = "&#xD;";
                } else {
                    stringBuffer.append(cCharAt);
                }
                i++;
            }
            stringBuffer.append(str2);
            i++;
        }
        return stringBuffer.toString();
    }

    public static String getSchemaTypeName(XSTypeDefinition xSTypeDefinition) {
        return xSTypeDefinition instanceof XSSimpleTypeDefinition ? ((XSSimpleTypeDecl) xSTypeDefinition).getTypeName() : ((XSComplexTypeDecl) xSTypeDefinition).getTypeName();
    }

    private static String processAttValue(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\"' || cCharAt == '<' || cCharAt == '&' || cCharAt == '\t' || cCharAt == '\n' || cCharAt == '\r') {
                return escapeAttValue(str, i);
            }
        }
        return str;
    }

    void checkNotationType(String str, XSTypeDefinition xSTypeDefinition, Element element) {
        if (xSTypeDefinition.getTypeCategory() == 16) {
            XSSimpleType xSSimpleType = (XSSimpleType) xSTypeDefinition;
            if (xSSimpleType.getVariety() == 1 && xSSimpleType.getPrimitiveKind() == 20 && (xSSimpleType.getDefinedFacets() & 2048) == 0) {
                reportSchemaError("enumeration-required-notation", new Object[]{xSTypeDefinition.getName(), str, DOMUtil.getLocalName(element)}, element);
            }
        }
    }

    protected XSParticleDecl checkOccurrences(XSParticleDecl xSParticleDecl, String str, Element element, int i, long j) {
        int i2 = xSParticleDecl.fMinOccurs;
        int i3 = xSParticleDecl.fMaxOccurs;
        int i4 = 1;
        boolean z = (j & ((long) (1 << XSAttributeChecker.ATTIDX_MINOCCURS))) != 0;
        boolean z2 = (j & ((long) (1 << XSAttributeChecker.ATTIDX_MAXOCCURS))) != 0;
        boolean z3 = (i & 1) != 0;
        boolean z4 = (i & 8) != 0;
        boolean z5 = (i & 2) != 0;
        if ((i & 4) != 0) {
            if (!z) {
                reportSchemaError("s4s-att-not-allowed", new Object[]{str, "minOccurs"}, element);
                i2 = 1;
            }
            if (!z2) {
                reportSchemaError("s4s-att-not-allowed", new Object[]{str, "maxOccurs"}, element);
                i3 = 1;
            }
        }
        if (i2 == 0 && i3 == 0) {
            xSParticleDecl.fType = (short) 0;
            return null;
        }
        if (!z3) {
            if ((z4 || z5) && i3 != 1) {
                reportSchemaError("cos-all-limited.1.2", null, element);
                if (i2 > 1) {
                    i2 = 1;
                }
                i3 = 1;
            }
            i4 = i2;
        } else if (i3 == 1) {
            i4 = i2;
        } else {
            reportSchemaError("cos-all-limited.2", new Object[]{i3 == -1 ? SchemaSymbols.ATTVAL_UNBOUNDED : Integer.toString(i3), ((XSElementDecl) xSParticleDecl.fValue).getName()}, element);
            if (i2 > 1) {
                i3 = 1;
            } else {
                i3 = 1;
                i4 = i2;
            }
        }
        xSParticleDecl.fMinOccurs = i4;
        xSParticleDecl.fMaxOccurs = i3;
        return xSParticleDecl;
    }

    void reportSchemaError(String str, Object[] objArr, Element element) {
        this.fSchemaHandler.reportSchemaError(str, objArr, element);
    }

    void reportSchemaWarning(String str, Object[] objArr, Element element) {
        this.fSchemaHandler.reportSchemaWarning(str, objArr, element);
    }

    void reset(SymbolTable symbolTable, boolean z, Locale locale) {
        this.fSymbolTable = symbolTable;
        this.fValidateAnnotations = z;
        this.fValidationState.setExtraChecking(false);
        this.fValidationState.setSymbolTable(symbolTable);
        this.fValidationState.setLocale(locale);
    }

    XSAnnotationImpl traverseAnnotationDecl(Element element, Object[] objArr, boolean z, XSDocumentInfo xSDocumentInfo) {
        String strSubstring;
        String strSubstring2;
        this.fAttrChecker.returnAttrArray(this.fAttrChecker.checkAttributes(element, z, xSDocumentInfo), xSDocumentInfo);
        String annotation = DOMUtil.getAnnotation(element);
        Element firstChildElement = DOMUtil.getFirstChildElement(element);
        if (firstChildElement != null) {
            do {
                String localName = DOMUtil.getLocalName(firstChildElement);
                if (localName.equals(SchemaSymbols.ELT_APPINFO) || localName.equals(SchemaSymbols.ELT_DOCUMENTATION)) {
                    this.fAttrChecker.returnAttrArray(this.fAttrChecker.checkAttributes(firstChildElement, true, xSDocumentInfo), xSDocumentInfo);
                } else {
                    reportSchemaError("src-annotation", new Object[]{localName}, firstChildElement);
                }
                firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
            } while (firstChildElement != null);
        }
        if (annotation == null) {
            return null;
        }
        SchemaGrammar grammar = this.fSchemaHandler.getGrammar(xSDocumentInfo.fTargetNamespace);
        Vector vector = (Vector) objArr[XSAttributeChecker.ATTIDX_NONSCHEMA];
        if (vector == null || vector.isEmpty()) {
            if (this.fValidateAnnotations) {
                xSDocumentInfo.addAnnotation(new XSAnnotationInfo(annotation, element));
            }
            return new XSAnnotationImpl(annotation, grammar);
        }
        StringBuffer stringBuffer = new StringBuffer(64);
        stringBuffer.append(" ");
        int i = 0;
        while (i < vector.size()) {
            int i2 = i + 1;
            String str = (String) vector.elementAt(i);
            int iIndexOf = str.indexOf(58);
            if (iIndexOf == -1) {
                strSubstring = "";
                strSubstring2 = str;
            } else {
                strSubstring = str.substring(0, iIndexOf);
                strSubstring2 = str.substring(iIndexOf + 1);
            }
            if (element.getAttributeNS(xSDocumentInfo.fNamespaceSupport.getURI(this.fSymbolTable.addSymbol(strSubstring)), strSubstring2).length() != 0) {
                i += 2;
            } else {
                stringBuffer.append(str).append("=\"");
                i += 2;
                stringBuffer.append(processAttValue((String) vector.elementAt(i2))).append("\" ");
            }
        }
        StringBuffer stringBuffer2 = new StringBuffer(annotation.length() + stringBuffer.length());
        int iIndexOf2 = annotation.indexOf(SchemaSymbols.ELT_ANNOTATION);
        if (iIndexOf2 == -1) {
            return null;
        }
        int length = iIndexOf2 + SchemaSymbols.ELT_ANNOTATION.length();
        stringBuffer2.append(annotation.substring(0, length));
        stringBuffer2.append(stringBuffer.toString());
        stringBuffer2.append(annotation.substring(length, annotation.length()));
        String string = stringBuffer2.toString();
        if (this.fValidateAnnotations) {
            xSDocumentInfo.addAnnotation(new XSAnnotationInfo(string, element));
        }
        return new XSAnnotationImpl(string, grammar);
    }

    Element traverseAttrsAndAttrGrps(Element element, XSAttributeGroupDecl xSAttributeGroupDecl, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar, XSComplexTypeDecl xSComplexTypeDecl) {
        String str;
        Element nextSiblingElement = element;
        while (true) {
            String str2 = "src-attribute_group.2";
            String str3 = "src-ct.4";
            if (nextSiblingElement == null) {
                break;
            }
            String localName = DOMUtil.getLocalName(nextSiblingElement);
            String str4 = "ag-props-correct.3";
            if (!localName.equals(SchemaSymbols.ELT_ATTRIBUTE)) {
                if (!localName.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) {
                    break;
                }
                XSAttributeGroupDecl xSAttributeGroupDeclTraverseLocal = this.fSchemaHandler.fAttributeGroupTraverser.traverseLocal(nextSiblingElement, xSDocumentInfo, schemaGrammar);
                if (xSAttributeGroupDeclTraverseLocal != null) {
                    XSObjectList attributeUses = xSAttributeGroupDeclTraverseLocal.getAttributeUses();
                    int length = attributeUses.getLength();
                    int i = 0;
                    while (i < length) {
                        String str5 = str2;
                        XSAttributeUseImpl xSAttributeUseImpl = (XSAttributeUseImpl) attributeUses.item(i);
                        String str6 = str3;
                        XSObjectList xSObjectList = attributeUses;
                        if (xSAttributeUseImpl.fUse == 2) {
                            xSAttributeGroupDecl.addAttributeUse(xSAttributeUseImpl);
                        } else {
                            XSAttributeUse attributeUseNoProhibited = xSAttributeGroupDecl.getAttributeUseNoProhibited(xSAttributeUseImpl.fAttrDecl.getNamespace(), xSAttributeUseImpl.fAttrDecl.getName());
                            if (attributeUseNoProhibited == null) {
                                String strAddAttributeUse = xSAttributeGroupDecl.addAttributeUse(xSAttributeUseImpl);
                                if (strAddAttributeUse != null) {
                                    str = str4;
                                    reportSchemaError(xSComplexTypeDecl == null ? str4 : "ct-props-correct.5", new Object[]{xSComplexTypeDecl == null ? xSAttributeGroupDecl.fName : xSComplexTypeDecl.getName(), xSAttributeUseImpl.fAttrDecl.getName(), strAddAttributeUse}, nextSiblingElement);
                                }
                            } else {
                                str = str4;
                                if (xSAttributeUseImpl != attributeUseNoProhibited) {
                                    reportSchemaError(xSComplexTypeDecl == null ? "ag-props-correct.2" : "ct-props-correct.4", new Object[]{xSComplexTypeDecl == null ? xSAttributeGroupDecl.fName : xSComplexTypeDecl.getName(), xSAttributeUseImpl.fAttrDecl.getName()}, nextSiblingElement);
                                }
                            }
                            i++;
                            str3 = str6;
                            str2 = str5;
                            attributeUses = xSObjectList;
                            str4 = str;
                        }
                        str = str4;
                        i++;
                        str3 = str6;
                        str2 = str5;
                        attributeUses = xSObjectList;
                        str4 = str;
                    }
                    String str7 = str2;
                    String str8 = str3;
                    if (xSAttributeGroupDeclTraverseLocal.fAttributeWC != null) {
                        if (xSAttributeGroupDecl.fAttributeWC == null) {
                            xSAttributeGroupDecl.fAttributeWC = xSAttributeGroupDeclTraverseLocal.fAttributeWC;
                        } else {
                            xSAttributeGroupDecl.fAttributeWC = xSAttributeGroupDecl.fAttributeWC.performIntersectionWith(xSAttributeGroupDeclTraverseLocal.fAttributeWC, xSAttributeGroupDecl.fAttributeWC.fProcessContents);
                            if (xSAttributeGroupDecl.fAttributeWC == null) {
                                reportSchemaError(xSComplexTypeDecl == null ? str7 : str8, new Object[]{xSComplexTypeDecl == null ? xSAttributeGroupDecl.fName : xSComplexTypeDecl.getName()}, nextSiblingElement);
                            }
                        }
                    }
                }
            } else {
                XSAttributeUseImpl xSAttributeUseImplTraverseLocal = this.fSchemaHandler.fAttributeTraverser.traverseLocal(nextSiblingElement, xSDocumentInfo, schemaGrammar, xSComplexTypeDecl);
                if (xSAttributeUseImplTraverseLocal != null) {
                    if (xSAttributeUseImplTraverseLocal.fUse == 2) {
                        xSAttributeGroupDecl.addAttributeUse(xSAttributeUseImplTraverseLocal);
                    } else {
                        XSAttributeUse attributeUseNoProhibited2 = xSAttributeGroupDecl.getAttributeUseNoProhibited(xSAttributeUseImplTraverseLocal.fAttrDecl.getNamespace(), xSAttributeUseImplTraverseLocal.fAttrDecl.getName());
                        if (attributeUseNoProhibited2 == null) {
                            String strAddAttributeUse2 = xSAttributeGroupDecl.addAttributeUse(xSAttributeUseImplTraverseLocal);
                            if (strAddAttributeUse2 != null) {
                                reportSchemaError(xSComplexTypeDecl != null ? "ct-props-correct.5" : "ag-props-correct.3", new Object[]{xSComplexTypeDecl == null ? xSAttributeGroupDecl.fName : xSComplexTypeDecl.getName(), xSAttributeUseImplTraverseLocal.fAttrDecl.getName(), strAddAttributeUse2}, nextSiblingElement);
                            }
                        } else if (attributeUseNoProhibited2 != xSAttributeUseImplTraverseLocal) {
                            reportSchemaError(xSComplexTypeDecl != null ? "ct-props-correct.4" : "ag-props-correct.2", new Object[]{xSComplexTypeDecl == null ? xSAttributeGroupDecl.fName : xSComplexTypeDecl.getName(), xSAttributeUseImplTraverseLocal.fAttrDecl.getName()}, nextSiblingElement);
                        }
                    }
                }
            }
            nextSiblingElement = DOMUtil.getNextSiblingElement(nextSiblingElement);
        }
        if (nextSiblingElement == null || !DOMUtil.getLocalName(nextSiblingElement).equals(SchemaSymbols.ELT_ANYATTRIBUTE)) {
            return nextSiblingElement;
        }
        XSWildcardDecl xSWildcardDeclTraverseAnyAttribute = this.fSchemaHandler.fWildCardTraverser.traverseAnyAttribute(nextSiblingElement, xSDocumentInfo, schemaGrammar);
        if (xSAttributeGroupDecl.fAttributeWC == null) {
            xSAttributeGroupDecl.fAttributeWC = xSWildcardDeclTraverseAnyAttribute;
        } else {
            xSAttributeGroupDecl.fAttributeWC = xSWildcardDeclTraverseAnyAttribute.performIntersectionWith(xSAttributeGroupDecl.fAttributeWC, xSWildcardDeclTraverseAnyAttribute.fProcessContents);
            if (xSAttributeGroupDecl.fAttributeWC == null) {
                reportSchemaError(xSComplexTypeDecl == null ? "src-attribute_group.2" : "src-ct.4", new Object[]{xSComplexTypeDecl == null ? xSAttributeGroupDecl.fName : xSComplexTypeDecl.getName()}, nextSiblingElement);
            }
        }
        return DOMUtil.getNextSiblingElement(nextSiblingElement);
    }

    /* JADX WARN: Code duplicated, block: B:33:0x00d2  */
    /* JADX WARN: Code duplicated, block: B:34:0x00de  */
    /* JADX WARN: Code duplicated, block: B:37:0x00e9  */
    /* JADX WARN: Code duplicated, block: B:43:0x011a  */
    /* JADX WARN: Code duplicated, block: B:45:0x0121  */
    /* JADX WARN: Code duplicated, block: B:46:0x0148  */
    /* JADX WARN: Code duplicated, block: B:49:0x0157  */
    /* JADX WARN: Code duplicated, block: B:76:0x0225  */
    FacetInfo traverseFacets(Element element, XSTypeDefinition xSTypeDefinition, XSSimpleType xSSimpleType, XSDocumentInfo xSDocumentInfo) {
        XSObjectListImpl xSObjectListImpl;
        XSObjectListImpl xSObjectListImpl2;
        Vector vector;
        XSSimpleType xSSimpleType2;
        Element element2;
        short s;
        short s2;
        short s3;
        XSAnnotationImpl xSAnnotationImplTraverseSyntheticAnnotation;
        Element nextSiblingElement;
        short s4;
        Object[] objArr;
        XSObjectListImpl xSObjectListImpl3;
        XSObjectListImpl xSObjectListImpl4;
        Element firstChildElement;
        String syntheticAnnotation;
        Element nextSiblingElement2;
        Object globalDecl;
        boolean zContainsQName = containsQName(xSSimpleType);
        Vector vector2 = zContainsQName ? new Vector() : null;
        this.xsFacets.reset();
        Element element3 = (Element) element.getParentNode();
        Element nextSiblingElement3 = element;
        short s5 = 0;
        short s6 = 0;
        XSObjectListImpl xSObjectListImpl5 = null;
        Vector vector3 = null;
        XSObjectListImpl xSObjectListImpl6 = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        while (true) {
            if (nextSiblingElement3 == null) {
                xSObjectListImpl = xSObjectListImpl5;
                xSObjectListImpl2 = xSObjectListImpl6;
                element3 = element3;
                vector = vector3;
                break;
            }
            String localName = DOMUtil.getLocalName(nextSiblingElement3);
            short s7 = s5;
            if (localName.equals(SchemaSymbols.ELT_ENUMERATION)) {
                Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(nextSiblingElement3, false, xSDocumentInfo, zContainsQName);
                String str = (String) objArrCheckAttributes[XSAttributeChecker.ATTIDX_VALUE];
                if (str == null) {
                    reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_ENUMERATION, SchemaSymbols.ATT_VALUE}, nextSiblingElement3);
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                    nextSiblingElement3 = DOMUtil.getNextSiblingElement(nextSiblingElement3);
                    s5 = s7;
                } else {
                    NamespaceSupport namespaceSupport = (NamespaceSupport) objArrCheckAttributes[XSAttributeChecker.ATTIDX_ENUMNSDECLS];
                    short s8 = s6;
                    if (xSSimpleType.getVariety() == 1 && xSSimpleType.getPrimitiveKind() == 20) {
                        xSDocumentInfo.fValidationContext.setNamespaceSupport(namespaceSupport);
                        try {
                            xSObjectListImpl3 = xSObjectListImpl5;
                            try {
                                globalDecl = this.fSchemaHandler.getGlobalDecl(xSDocumentInfo, 6, (QName) fQNameDV.validate(str, (ValidationContext) xSDocumentInfo.fValidationContext, (ValidatedInfo) null), nextSiblingElement3);
                            } catch (InvalidDatatypeValueException e) {
                                e = e;
                                reportSchemaError(e.getKey(), e.getArgs(), nextSiblingElement3);
                                globalDecl = null;
                            }
                        } catch (InvalidDatatypeValueException e2) {
                            e = e2;
                            xSObjectListImpl3 = xSObjectListImpl5;
                        }
                        if (globalDecl == null) {
                            this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
                            nextSiblingElement3 = DOMUtil.getNextSiblingElement(nextSiblingElement3);
                            s5 = s7;
                            s6 = s8;
                            xSObjectListImpl5 = xSObjectListImpl3;
                        } else {
                            xSDocumentInfo.fValidationContext.setNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
                            if (vector3 == null) {
                                vector3 = new Vector();
                                xSObjectListImpl4 = new XSObjectListImpl();
                            } else {
                                xSObjectListImpl4 = xSObjectListImpl3;
                            }
                            vector3.addElement(str);
                            xSObjectListImpl4.addXSObject(null);
                            if (zContainsQName) {
                                vector2.addElement(namespaceSupport);
                            }
                            firstChildElement = DOMUtil.getFirstChildElement(nextSiblingElement3);
                            if (firstChildElement == null) {
                                syntheticAnnotation = DOMUtil.getSyntheticAnnotation(nextSiblingElement3);
                                if (syntheticAnnotation != null) {
                                    xSObjectListImpl4.addXSObject(xSObjectListImpl4.getLength() - 1, traverseSyntheticAnnotation(nextSiblingElement3, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo));
                                }
                                nextSiblingElement2 = firstChildElement;
                            } else {
                                syntheticAnnotation = DOMUtil.getSyntheticAnnotation(nextSiblingElement3);
                                if (syntheticAnnotation != null) {
                                    xSObjectListImpl4.addXSObject(xSObjectListImpl4.getLength() - 1, traverseSyntheticAnnotation(nextSiblingElement3, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo));
                                }
                                nextSiblingElement2 = firstChildElement;
                            }
                            if (nextSiblingElement2 != null) {
                                reportSchemaError("s4s-elt-must-match.1", new Object[]{"enumeration", "(annotation?)", DOMUtil.getLocalName(nextSiblingElement2)}, nextSiblingElement2);
                            }
                            xSObjectListImpl5 = xSObjectListImpl4;
                            xSObjectListImpl6 = xSObjectListImpl6;
                            zContainsQName = zContainsQName;
                            vector3 = vector3;
                            s5 = s7;
                            s6 = s8;
                            objArr = objArrCheckAttributes;
                            element3 = element3;
                            this.fAttrChecker.returnAttrArray(objArr, xSDocumentInfo);
                            nextSiblingElement3 = DOMUtil.getNextSiblingElement(nextSiblingElement3);
                            zContainsQName = zContainsQName;
                            element3 = element3;
                        }
                    } else {
                        xSObjectListImpl3 = xSObjectListImpl5;
                        if (vector3 == null) {
                            vector3 = new Vector();
                            xSObjectListImpl4 = new XSObjectListImpl();
                        } else {
                            xSObjectListImpl4 = xSObjectListImpl3;
                        }
                        vector3.addElement(str);
                        xSObjectListImpl4.addXSObject(null);
                        if (zContainsQName) {
                            vector2.addElement(namespaceSupport);
                        }
                        firstChildElement = DOMUtil.getFirstChildElement(nextSiblingElement3);
                        if (firstChildElement == null && DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
                            xSObjectListImpl4.addXSObject(xSObjectListImpl4.getLength() - 1, traverseAnnotationDecl(firstChildElement, objArrCheckAttributes, false, xSDocumentInfo));
                            nextSiblingElement2 = DOMUtil.getNextSiblingElement(firstChildElement);
                            s7 = s7;
                            vector3 = vector3;
                            objArrCheckAttributes = objArrCheckAttributes;
                            s8 = s8;
                            xSObjectListImpl6 = xSObjectListImpl6;
                        } else {
                            syntheticAnnotation = DOMUtil.getSyntheticAnnotation(nextSiblingElement3);
                            if (syntheticAnnotation != null) {
                                xSObjectListImpl4.addXSObject(xSObjectListImpl4.getLength() - 1, traverseSyntheticAnnotation(nextSiblingElement3, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo));
                            }
                            nextSiblingElement2 = firstChildElement;
                        }
                        if (nextSiblingElement2 != null) {
                            reportSchemaError("s4s-elt-must-match.1", new Object[]{"enumeration", "(annotation?)", DOMUtil.getLocalName(nextSiblingElement2)}, nextSiblingElement2);
                        }
                        xSObjectListImpl5 = xSObjectListImpl4;
                        xSObjectListImpl6 = xSObjectListImpl6;
                        zContainsQName = zContainsQName;
                        vector3 = vector3;
                        s5 = s7;
                        s6 = s8;
                        objArr = objArrCheckAttributes;
                        element3 = element3;
                        this.fAttrChecker.returnAttrArray(objArr, xSDocumentInfo);
                        nextSiblingElement3 = DOMUtil.getNextSiblingElement(nextSiblingElement3);
                        zContainsQName = zContainsQName;
                        element3 = element3;
                    }
                }
            } else {
                short s9 = s7;
                short s10 = s6;
                XSObjectListImpl xSObjectListImpl7 = xSObjectListImpl5;
                XSObjectListImpl xSObjectListImpl8 = xSObjectListImpl6;
                if (localName.equals(SchemaSymbols.ELT_PATTERN)) {
                    Object[] objArrCheckAttributes2 = this.fAttrChecker.checkAttributes(nextSiblingElement3, false, xSDocumentInfo);
                    String str2 = (String) objArrCheckAttributes2[XSAttributeChecker.ATTIDX_VALUE];
                    if (str2 == null) {
                        reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_PATTERN, SchemaSymbols.ATT_VALUE}, nextSiblingElement3);
                        this.fAttrChecker.returnAttrArray(objArrCheckAttributes2, xSDocumentInfo);
                        nextSiblingElement3 = DOMUtil.getNextSiblingElement(nextSiblingElement3);
                        xSObjectListImpl6 = xSObjectListImpl8;
                        xSObjectListImpl5 = xSObjectListImpl7;
                        s5 = s9;
                        s6 = s10;
                    } else {
                        if (this.fPattern.length() != 0) {
                            this.fPattern.append("|");
                        }
                        this.fPattern.append(str2);
                        Element firstChildElement2 = DOMUtil.getFirstChildElement(nextSiblingElement3);
                        if (firstChildElement2 == null || !DOMUtil.getLocalName(firstChildElement2).equals(SchemaSymbols.ELT_ANNOTATION)) {
                            String syntheticAnnotation2 = DOMUtil.getSyntheticAnnotation(nextSiblingElement3);
                            if (syntheticAnnotation2 != null) {
                                if (xSObjectListImpl8 == null) {
                                    xSObjectListImpl8 = new XSObjectListImpl();
                                }
                                xSObjectListImpl8.addXSObject(traverseSyntheticAnnotation(nextSiblingElement3, syntheticAnnotation2, objArrCheckAttributes2, false, xSDocumentInfo));
                                firstChildElement2 = firstChildElement2;
                            }
                            if (firstChildElement2 != null) {
                                reportSchemaError("s4s-elt-must-match.1", new Object[]{"pattern", "(annotation?)", DOMUtil.getLocalName(firstChildElement2)}, firstChildElement2);
                            }
                            xSObjectListImpl6 = xSObjectListImpl8;
                            xSObjectListImpl5 = xSObjectListImpl7;
                            vector3 = vector3;
                            s5 = s9;
                            s6 = s10;
                            z = true;
                            objArr = objArrCheckAttributes2;
                            this.fAttrChecker.returnAttrArray(objArr, xSDocumentInfo);
                            nextSiblingElement3 = DOMUtil.getNextSiblingElement(nextSiblingElement3);
                            zContainsQName = zContainsQName;
                            element3 = element3;
                        } else {
                            XSObjectListImpl xSObjectListImpl9 = xSObjectListImpl8 == null ? new XSObjectListImpl() : xSObjectListImpl8;
                            xSObjectListImpl9.addXSObject(traverseAnnotationDecl(firstChildElement2, objArrCheckAttributes2, false, xSDocumentInfo));
                            firstChildElement2 = DOMUtil.getNextSiblingElement(firstChildElement2);
                            xSObjectListImpl8 = xSObjectListImpl9;
                        }
                        if (firstChildElement2 != null) {
                            reportSchemaError("s4s-elt-must-match.1", new Object[]{"pattern", "(annotation?)", DOMUtil.getLocalName(firstChildElement2)}, firstChildElement2);
                        }
                        xSObjectListImpl6 = xSObjectListImpl8;
                        xSObjectListImpl5 = xSObjectListImpl7;
                        vector3 = vector3;
                        s5 = s9;
                        s6 = s10;
                        z = true;
                        objArr = objArrCheckAttributes2;
                        this.fAttrChecker.returnAttrArray(objArr, xSDocumentInfo);
                        nextSiblingElement3 = DOMUtil.getNextSiblingElement(nextSiblingElement3);
                        zContainsQName = zContainsQName;
                        element3 = element3;
                    }
                } else {
                    zContainsQName = zContainsQName;
                    XSObjectListImpl xSObjectListImpl10 = xSObjectListImpl7;
                    element3 = element3;
                    vector = vector3;
                    if (!localName.equals(SchemaSymbols.ELT_MINLENGTH)) {
                        if (!localName.equals(SchemaSymbols.ELT_MAXLENGTH)) {
                            if (!localName.equals(SchemaSymbols.ELT_MAXEXCLUSIVE)) {
                                if (!localName.equals(SchemaSymbols.ELT_MAXINCLUSIVE)) {
                                    if (!localName.equals(SchemaSymbols.ELT_MINEXCLUSIVE)) {
                                        if (!localName.equals(SchemaSymbols.ELT_MININCLUSIVE)) {
                                            if (!localName.equals(SchemaSymbols.ELT_TOTALDIGITS)) {
                                                if (!localName.equals(SchemaSymbols.ELT_FRACTIONDIGITS)) {
                                                    if (!localName.equals(SchemaSymbols.ELT_WHITESPACE)) {
                                                        if (!localName.equals(SchemaSymbols.ELT_LENGTH)) {
                                                            xSObjectListImpl2 = xSObjectListImpl8;
                                                            xSObjectListImpl = xSObjectListImpl10;
                                                            s5 = s9;
                                                            s6 = s10;
                                                            break;
                                                        }
                                                        s = 1;
                                                    } else {
                                                        s = 16;
                                                    }
                                                } else {
                                                    s = 1024;
                                                }
                                            } else {
                                                s = XSSimpleTypeDefinition.FACET_TOTALDIGITS;
                                            }
                                        } else {
                                            s = 256;
                                        }
                                    } else {
                                        s = 128;
                                    }
                                } else {
                                    s = 32;
                                }
                            } else {
                                s = 64;
                            }
                        } else {
                            s = 4;
                        }
                    } else {
                        s = 2;
                    }
                    Object[] objArrCheckAttributes3 = this.fAttrChecker.checkAttributes(nextSiblingElement3, false, xSDocumentInfo);
                    s6 = s10;
                    if ((s6 & s) != 0) {
                        reportSchemaError("src-single-facet-value", new Object[]{localName}, nextSiblingElement3);
                    } else if (objArrCheckAttributes3[XSAttributeChecker.ATTIDX_VALUE] != null) {
                        short s11 = (short) (s6 | s);
                        if (((Boolean) objArrCheckAttributes3[XSAttributeChecker.ATTIDX_FIXED]).booleanValue()) {
                            s9 = (short) (s9 | s);
                        }
                        if (s == 1) {
                            this.xsFacets.length = ((XInt) objArrCheckAttributes3[XSAttributeChecker.ATTIDX_VALUE]).intValue();
                            z2 = true;
                        } else if (s == 2) {
                            this.xsFacets.minLength = ((XInt) objArrCheckAttributes3[XSAttributeChecker.ATTIDX_VALUE]).intValue();
                            z3 = true;
                        } else if (s == 4) {
                            this.xsFacets.maxLength = ((XInt) objArrCheckAttributes3[XSAttributeChecker.ATTIDX_VALUE]).intValue();
                            z4 = true;
                        } else if (s == 16) {
                            this.xsFacets.whiteSpace = ((XInt) objArrCheckAttributes3[XSAttributeChecker.ATTIDX_VALUE]).shortValue();
                        } else if (s == 32) {
                            this.xsFacets.maxInclusive = (String) objArrCheckAttributes3[XSAttributeChecker.ATTIDX_VALUE];
                        } else if (s == 64) {
                            this.xsFacets.maxExclusive = (String) objArrCheckAttributes3[XSAttributeChecker.ATTIDX_VALUE];
                        } else if (s == 128) {
                            this.xsFacets.minExclusive = (String) objArrCheckAttributes3[XSAttributeChecker.ATTIDX_VALUE];
                        } else if (s == 256) {
                            this.xsFacets.minInclusive = (String) objArrCheckAttributes3[XSAttributeChecker.ATTIDX_VALUE];
                        } else if (s == 512) {
                            this.xsFacets.totalDigits = ((XInt) objArrCheckAttributes3[XSAttributeChecker.ATTIDX_VALUE]).intValue();
                        } else if (s == 1024) {
                            this.xsFacets.fractionDigits = ((XInt) objArrCheckAttributes3[XSAttributeChecker.ATTIDX_VALUE]).intValue();
                        }
                        Element firstChildElement3 = DOMUtil.getFirstChildElement(nextSiblingElement3);
                        if (firstChildElement3 == null || !DOMUtil.getLocalName(firstChildElement3).equals(SchemaSymbols.ELT_ANNOTATION)) {
                            String syntheticAnnotation3 = DOMUtil.getSyntheticAnnotation(nextSiblingElement3);
                            if (syntheticAnnotation3 != null) {
                                s3 = s;
                                s2 = 2;
                                xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(nextSiblingElement3, syntheticAnnotation3, objArrCheckAttributes3, false, xSDocumentInfo);
                            } else {
                                s2 = 2;
                                s3 = s;
                                xSAnnotationImplTraverseSyntheticAnnotation = null;
                            }
                            nextSiblingElement = firstChildElement3;
                            s4 = 1;
                        } else {
                            XSAnnotationImpl xSAnnotationImplTraverseAnnotationDecl = traverseAnnotationDecl(firstChildElement3, objArrCheckAttributes3, false, xSDocumentInfo);
                            s11 = s11;
                            objArrCheckAttributes3 = objArrCheckAttributes3;
                            xSObjectListImpl8 = xSObjectListImpl8;
                            xSObjectListImpl10 = xSObjectListImpl10;
                            s4 = 1;
                            s2 = 2;
                            s3 = s;
                            nextSiblingElement = DOMUtil.getNextSiblingElement(firstChildElement3);
                            xSAnnotationImplTraverseSyntheticAnnotation = xSAnnotationImplTraverseAnnotationDecl;
                        }
                        if (s3 == s4) {
                            this.xsFacets.lengthAnnotation = xSAnnotationImplTraverseSyntheticAnnotation;
                        } else if (s3 == s2) {
                            this.xsFacets.minLengthAnnotation = xSAnnotationImplTraverseSyntheticAnnotation;
                        } else if (s3 == 4) {
                            this.xsFacets.maxLengthAnnotation = xSAnnotationImplTraverseSyntheticAnnotation;
                        } else if (s3 == 16) {
                            this.xsFacets.whiteSpaceAnnotation = xSAnnotationImplTraverseSyntheticAnnotation;
                        } else if (s3 == 32) {
                            this.xsFacets.maxInclusiveAnnotation = xSAnnotationImplTraverseSyntheticAnnotation;
                        } else if (s3 == 64) {
                            this.xsFacets.maxExclusiveAnnotation = xSAnnotationImplTraverseSyntheticAnnotation;
                        } else if (s3 == 128) {
                            this.xsFacets.minExclusiveAnnotation = xSAnnotationImplTraverseSyntheticAnnotation;
                        } else if (s3 == 256) {
                            this.xsFacets.minInclusiveAnnotation = xSAnnotationImplTraverseSyntheticAnnotation;
                        } else if (s3 == 512) {
                            this.xsFacets.totalDigitsAnnotation = xSAnnotationImplTraverseSyntheticAnnotation;
                        } else if (s3 == 1024) {
                            this.xsFacets.fractionDigitsAnnotation = xSAnnotationImplTraverseSyntheticAnnotation;
                        }
                        if (nextSiblingElement != null) {
                            reportSchemaError("s4s-elt-must-match.1", new Object[]{localName, "(annotation?)", DOMUtil.getLocalName(nextSiblingElement)}, nextSiblingElement);
                        }
                        vector3 = vector;
                        s5 = s9;
                        objArr = objArrCheckAttributes3;
                        s6 = s11;
                        xSObjectListImpl6 = xSObjectListImpl8;
                        xSObjectListImpl5 = xSObjectListImpl10;
                        this.fAttrChecker.returnAttrArray(objArr, xSDocumentInfo);
                        nextSiblingElement3 = DOMUtil.getNextSiblingElement(nextSiblingElement3);
                        zContainsQName = zContainsQName;
                        element3 = element3;
                    } else if (nextSiblingElement3.getAttributeNodeNS(null, "value") == null) {
                        reportSchemaError("s4s-att-must-appear", new Object[]{nextSiblingElement3.getLocalName(), SchemaSymbols.ATT_VALUE}, nextSiblingElement3);
                    }
                    this.fAttrChecker.returnAttrArray(objArrCheckAttributes3, xSDocumentInfo);
                    nextSiblingElement3 = DOMUtil.getNextSiblingElement(nextSiblingElement3);
                    xSObjectListImpl6 = xSObjectListImpl8;
                    xSObjectListImpl5 = xSObjectListImpl10;
                    vector3 = vector;
                    zContainsQName = zContainsQName;
                    element3 = element3;
                    s5 = s9;
                }
            }
        }
        if (vector != null) {
            s6 = (short) (s6 | 2048);
            this.xsFacets.enumeration = vector;
            this.xsFacets.enumNSDecls = vector2;
            this.xsFacets.enumAnnotations = xSObjectListImpl;
        }
        if (z) {
            s6 = (short) (s6 | 8);
            this.xsFacets.pattern = this.fPattern.toString();
            this.xsFacets.patternAnnotations = xSObjectListImpl2;
        }
        this.fPattern.setLength(0);
        if (vector != null) {
            if (z2) {
                xSSimpleType2 = xSSimpleType;
                element2 = element3;
                checkEnumerationAndLengthInconsistency(xSSimpleType2, vector, element2, getSchemaTypeName(xSTypeDefinition));
            } else {
                xSSimpleType2 = xSSimpleType;
                element2 = element3;
            }
            if (z3) {
                checkEnumerationAndMinLengthInconsistency(xSSimpleType2, vector, element2, getSchemaTypeName(xSTypeDefinition));
            }
            if (z4) {
                checkEnumerationAndMaxLengthInconsistency(xSSimpleType2, vector, element2, getSchemaTypeName(xSTypeDefinition));
            }
        }
        return new FacetInfo(this.xsFacets, nextSiblingElement3, s6, s5);
    }

    XSAnnotationImpl traverseSyntheticAnnotation(Element element, String str, Object[] objArr, boolean z, XSDocumentInfo xSDocumentInfo) {
        String strSubstring;
        SchemaGrammar grammar = this.fSchemaHandler.getGrammar(xSDocumentInfo.fTargetNamespace);
        Vector vector = (Vector) objArr[XSAttributeChecker.ATTIDX_NONSCHEMA];
        if (vector == null || vector.isEmpty()) {
            if (this.fValidateAnnotations) {
                xSDocumentInfo.addAnnotation(new XSAnnotationInfo(str, element));
            }
            return new XSAnnotationImpl(str, grammar);
        }
        StringBuffer stringBuffer = new StringBuffer(64);
        stringBuffer.append(" ");
        int i = 0;
        while (i < vector.size()) {
            int i2 = i + 1;
            String str2 = (String) vector.elementAt(i);
            int iIndexOf = str2.indexOf(58);
            if (iIndexOf == -1) {
                strSubstring = "";
            } else {
                strSubstring = str2.substring(0, iIndexOf);
                str2.substring(iIndexOf + 1);
            }
            xSDocumentInfo.fNamespaceSupport.getURI(this.fSymbolTable.addSymbol(strSubstring));
            stringBuffer.append(str2).append("=\"");
            i += 2;
            stringBuffer.append(processAttValue((String) vector.elementAt(i2))).append("\" ");
        }
        StringBuffer stringBuffer2 = new StringBuffer(str.length() + stringBuffer.length());
        int iIndexOf2 = str.indexOf(SchemaSymbols.ELT_ANNOTATION);
        if (iIndexOf2 == -1) {
            return null;
        }
        int length = iIndexOf2 + SchemaSymbols.ELT_ANNOTATION.length();
        stringBuffer2.append(str.substring(0, length));
        stringBuffer2.append(stringBuffer.toString());
        stringBuffer2.append(str.substring(length, str.length()));
        String string = stringBuffer2.toString();
        if (this.fValidateAnnotations) {
            xSDocumentInfo.addAnnotation(new XSAnnotationInfo(string, element));
        }
        return new XSAnnotationImpl(string, grammar);
    }
}
