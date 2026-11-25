package org.apache.xerces.impl.xs.traversers;

import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidatedInfo;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.xs.SchemaGrammar;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.apache.xerces.impl.xs.XSAnnotationImpl;
import org.apache.xerces.impl.xs.XSAttributeDecl;
import org.apache.xerces.impl.xs.XSAttributeUseImpl;
import org.apache.xerces.impl.xs.XSComplexTypeDecl;
import org.apache.xerces.impl.xs.util.XInt;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xs.XSTypeDefinition;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes13.dex */
class XSDAttributeTraverser extends XSDAbstractTraverser {
    public XSDAttributeTraverser(XSDHandler xSDHandler, XSAttributeChecker xSAttributeChecker) {
        super(xSDHandler, xSAttributeChecker);
    }

    void checkDefaultValid(XSAttributeDecl xSAttributeDecl) throws InvalidDatatypeValueException {
        ((XSSimpleType) xSAttributeDecl.getTypeDefinition()).validate(xSAttributeDecl.getValInfo().normalizedValue, (ValidationContext) this.fValidationState, xSAttributeDecl.getValInfo());
        ((XSSimpleType) xSAttributeDecl.getTypeDefinition()).validate(xSAttributeDecl.getValInfo().stringValue(), (ValidationContext) this.fValidationState, xSAttributeDecl.getValInfo());
    }

    void checkDefaultValid(XSAttributeUseImpl xSAttributeUseImpl) throws InvalidDatatypeValueException {
        ((XSSimpleType) xSAttributeUseImpl.fAttrDecl.getTypeDefinition()).validate(xSAttributeUseImpl.fDefault.normalizedValue, (ValidationContext) this.fValidationState, xSAttributeUseImpl.fDefault);
        ((XSSimpleType) xSAttributeUseImpl.fAttrDecl.getTypeDefinition()).validate(xSAttributeUseImpl.fDefault.stringValue(), (ValidationContext) this.fValidationState, xSAttributeUseImpl.fDefault);
    }

    protected XSAttributeDecl traverseGlobal(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar) {
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, true, xSDocumentInfo);
        XSAttributeDecl xSAttributeDeclTraverseNamedAttr = traverseNamedAttr(element, objArrCheckAttributes, xSDocumentInfo, schemaGrammar, true, null);
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
        return xSAttributeDeclTraverseNamedAttr;
    }

    protected XSAttributeUseImpl traverseLocal(Element element, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar, XSComplexTypeDecl xSComplexTypeDecl) {
        XSAttributeDecl xSAttributeDeclTraverseNamedAttr;
        XSAnnotationImpl xSAnnotationImpl;
        String str;
        short s;
        XSAttributeUseImpl attributeUse;
        ValidatedInfo validatedInfo;
        short s2;
        XSObjectListImpl xSObjectListImpl;
        XSAnnotationImpl xSAnnotationImplTraverseSyntheticAnnotation;
        Object[] objArrCheckAttributes = this.fAttrChecker.checkAttributes(element, false, xSDocumentInfo);
        String str2 = (String) objArrCheckAttributes[XSAttributeChecker.ATTIDX_DEFAULT];
        String str3 = (String) objArrCheckAttributes[XSAttributeChecker.ATTIDX_FIXED];
        String str4 = (String) objArrCheckAttributes[XSAttributeChecker.ATTIDX_NAME];
        QName qName = (QName) objArrCheckAttributes[XSAttributeChecker.ATTIDX_REF];
        XInt xInt = (XInt) objArrCheckAttributes[XSAttributeChecker.ATTIDX_USE];
        short s3 = 1;
        if (element.getAttributeNode(SchemaSymbols.ATT_REF) == null) {
            s3 = 1;
            xSAttributeDeclTraverseNamedAttr = traverseNamedAttr(element, objArrCheckAttributes, xSDocumentInfo, schemaGrammar, false, xSComplexTypeDecl);
            xSAnnotationImpl = null;
        } else if (qName != null) {
            XSAttributeDecl xSAttributeDecl = (XSAttributeDecl) this.fSchemaHandler.getGlobalDecl(xSDocumentInfo, 1, qName, element);
            Element firstChildElement = DOMUtil.getFirstChildElement(element);
            if (firstChildElement == null || !DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
                String syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element);
                xSAnnotationImplTraverseSyntheticAnnotation = syntheticAnnotation != null ? traverseSyntheticAnnotation(element, syntheticAnnotation, objArrCheckAttributes, false, xSDocumentInfo) : null;
            } else {
                XSAnnotationImpl xSAnnotationImplTraverseAnnotationDecl = traverseAnnotationDecl(firstChildElement, objArrCheckAttributes, false, xSDocumentInfo);
                firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                xSAnnotationImplTraverseSyntheticAnnotation = xSAnnotationImplTraverseAnnotationDecl;
            }
            if (firstChildElement != null) {
                firstChildElement = firstChildElement;
                reportSchemaError("src-attribute.3.2", new Object[]{qName.rawname}, firstChildElement);
            }
            firstChildElement = firstChildElement;
            str4 = qName.localpart;
            xSAnnotationImpl = xSAnnotationImplTraverseSyntheticAnnotation;
            xSAttributeDeclTraverseNamedAttr = xSAttributeDecl;
        } else {
            s3 = 1;
            xSAnnotationImpl = null;
            xSAttributeDeclTraverseNamedAttr = null;
        }
        if (str2 != null) {
            str = str3;
            str3 = str2;
            s = s3;
        } else if (str3 != null) {
            s = 2;
            str = null;
        } else {
            str = str3;
            str3 = str2;
            s = 0;
        }
        if (xSAttributeDeclTraverseNamedAttr != null) {
            attributeUse = this.fSchemaHandler.fDeclPool != null ? this.fSchemaHandler.fDeclPool.getAttributeUse() : new XSAttributeUseImpl();
            attributeUse.fAttrDecl = xSAttributeDeclTraverseNamedAttr;
            attributeUse.fUse = xInt.shortValue();
            attributeUse.fConstraintType = s;
            if (str3 != null) {
                attributeUse.fDefault = new ValidatedInfo();
                attributeUse.fDefault.normalizedValue = str3;
            }
            if (element.getAttributeNode(SchemaSymbols.ATT_REF) == null) {
                attributeUse.fAnnotations = xSAttributeDeclTraverseNamedAttr.getAnnotations();
            } else {
                if (xSAnnotationImpl != null) {
                    xSObjectListImpl = new XSObjectListImpl();
                    xSObjectListImpl.addXSObject(xSAnnotationImpl);
                } else {
                    xSObjectListImpl = XSObjectListImpl.EMPTY_LIST;
                }
                attributeUse.fAnnotations = xSObjectListImpl;
            }
        } else {
            attributeUse = null;
        }
        if (str3 != null && str != null) {
            reportSchemaError("src-attribute.1", new Object[]{str4}, element);
        }
        if (s == s3 && xInt != null && xInt.intValue() != 0) {
            reportSchemaError("src-attribute.2", new Object[]{str4}, element);
            attributeUse.fUse = (short) 0;
        }
        if (str3 != null && attributeUse != null) {
            this.fValidationState.setNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
            try {
                checkDefaultValid(attributeUse);
                validatedInfo = null;
                s2 = 0;
            } catch (InvalidDatatypeValueException e) {
                reportSchemaError(e.getKey(), e.getArgs(), element);
                reportSchemaError("a-props-correct.2", new Object[]{str4, str3}, element);
                validatedInfo = null;
                attributeUse.fDefault = null;
                s2 = 0;
                attributeUse.fConstraintType = (short) 0;
            }
            if (((XSSimpleType) xSAttributeDeclTraverseNamedAttr.getTypeDefinition()).isIDType()) {
                reportSchemaError("a-props-correct.3", new Object[]{str4}, element);
                attributeUse.fDefault = validatedInfo;
                attributeUse.fConstraintType = s2;
            }
            if (attributeUse.fAttrDecl.getConstraintType() == 2 && attributeUse.fConstraintType != 0 && (attributeUse.fConstraintType != 2 || !attributeUse.fAttrDecl.getValInfo().actualValue.equals(attributeUse.fDefault.actualValue))) {
                reportSchemaError("au-props-correct.2", new Object[]{str4, attributeUse.fAttrDecl.getValInfo().stringValue()}, element);
                attributeUse.fDefault = attributeUse.fAttrDecl.getValInfo();
                attributeUse.fConstraintType = (short) 2;
            }
        }
        this.fAttrChecker.returnAttrArray(objArrCheckAttributes, xSDocumentInfo);
        return attributeUse;
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0095  */
    /* JADX WARN: Code duplicated, block: B:42:0x0108  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10, types: [org.apache.xerces.impl.xs.traversers.XSDHandler] */
    /* JADX WARN: Type inference failed for: r15v0, types: [org.apache.xerces.impl.xs.XSAttributeDecl] */
    /* JADX WARN: Type inference failed for: r15v5 */
    /* JADX WARN: Type inference failed for: r15v6, types: [org.apache.xerces.impl.xs.XSAttributeDecl] */
    /* JADX WARN: Type inference failed for: r15v7 */
    /* JADX WARN: Type inference failed for: r17v1, types: [org.apache.xerces.impl.dv.XSSimpleType] */
    /* JADX WARN: Type inference failed for: r17v2, types: [org.apache.xerces.impl.dv.XSSimpleType] */
    /* JADX WARN: Type inference failed for: r17v3, types: [org.apache.xerces.impl.dv.XSSimpleType] */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14, types: [org.apache.xerces.impl.dv.XSSimpleType, org.apache.xerces.xs.XSTypeDefinition] */
    /* JADX WARN: Type inference failed for: r1v18, types: [org.apache.xerces.impl.dv.XSSimpleType] */
    /* JADX WARN: Type inference failed for: r1v20, types: [org.apache.xerces.impl.dv.XSSimpleType] */
    /* JADX WARN: Type inference failed for: r1v31 */
    /* JADX WARN: Type inference failed for: r26v0, types: [org.apache.xerces.impl.xs.traversers.XSDAttributeTraverser] */
    /* JADX WARN: Type inference failed for: r30v0, types: [org.apache.xerces.impl.xs.SchemaGrammar] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    XSAttributeDecl traverseNamedAttr(Element element, Object[] objArr, XSDocumentInfo xSDocumentInfo, SchemaGrammar schemaGrammar, boolean z, XSComplexTypeDecl xSComplexTypeDecl) {
        short s;
        XSComplexTypeDecl xSComplexTypeDecl2;
        XSComplexTypeDecl xSComplexTypeDecl3;
        short s2;
        String str;
        short s3;
        ValidatedInfo validatedInfo;
        String str2;
        Object[] objArr2;
        XSAnnotationImpl xSAnnotationImplTraverseSyntheticAnnotation;
        ?? r1;
        boolean z2;
        XSObjectListImpl xSObjectListImpl;
        String str3;
        ValidatedInfo validatedInfo2;
        String str4;
        XSSimpleType xSSimpleTypeTraverseLocal;
        String str5 = (String) objArr[XSAttributeChecker.ATTIDX_DEFAULT];
        String str6 = (String) objArr[XSAttributeChecker.ATTIDX_FIXED];
        XInt xInt = (XInt) objArr[XSAttributeChecker.ATTIDX_FORM];
        String strAddSymbol = (String) objArr[XSAttributeChecker.ATTIDX_NAME];
        QName qName = (QName) objArr[XSAttributeChecker.ATTIDX_TYPE];
        ?? attributeDecl = this.fSchemaHandler.fDeclPool != null ? this.fSchemaHandler.fDeclPool.getAttributeDecl() : new XSAttributeDecl();
        if (strAddSymbol != null) {
            strAddSymbol = this.fSymbolTable.addSymbol(strAddSymbol);
        }
        String str7 = strAddSymbol;
        if (z) {
            str = xSDocumentInfo.fTargetNamespace;
            s2 = 1;
            xSComplexTypeDecl3 = null;
        } else {
            if (xSComplexTypeDecl != null) {
                xSComplexTypeDecl2 = xSComplexTypeDecl;
                s = 2;
            } else {
                s = 0;
                xSComplexTypeDecl2 = null;
            }
            if (xInt == null ? !xSDocumentInfo.fAreLocalAttributesQualified : xInt.intValue() != 1) {
                xSComplexTypeDecl3 = xSComplexTypeDecl2;
                s2 = s;
                str = null;
            } else {
                xSComplexTypeDecl3 = xSComplexTypeDecl2;
                s2 = s;
                str = xSDocumentInfo.fTargetNamespace;
            }
        }
        if (!z) {
            s3 = 0;
            validatedInfo = null;
        } else if (str6 != null) {
            ValidatedInfo validatedInfo3 = new ValidatedInfo();
            validatedInfo3.normalizedValue = str6;
            validatedInfo = validatedInfo3;
            s3 = 2;
        } else if (str5 != null) {
            ValidatedInfo validatedInfo4 = new ValidatedInfo();
            validatedInfo4.normalizedValue = str5;
            validatedInfo = validatedInfo4;
            s3 = 1;
        } else {
            s3 = 0;
            validatedInfo = null;
        }
        Element firstChildElement = DOMUtil.getFirstChildElement(element);
        if (firstChildElement == null || !DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_ANNOTATION)) {
            String syntheticAnnotation = DOMUtil.getSyntheticAnnotation(element);
            if (syntheticAnnotation != null) {
                str2 = str;
                objArr2 = null;
                xSAnnotationImplTraverseSyntheticAnnotation = traverseSyntheticAnnotation(element, syntheticAnnotation, objArr, false, xSDocumentInfo);
            } else {
                str2 = str;
                objArr2 = null;
                xSAnnotationImplTraverseSyntheticAnnotation = null;
            }
        } else {
            XSAnnotationImpl xSAnnotationImplTraverseAnnotationDecl = traverseAnnotationDecl(firstChildElement, objArr, false, xSDocumentInfo);
            firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
            xSAnnotationImplTraverseSyntheticAnnotation = xSAnnotationImplTraverseAnnotationDecl;
            str2 = str;
            objArr2 = null;
        }
        if (firstChildElement != null) {
            firstChildElement = firstChildElement;
            if (DOMUtil.getLocalName(firstChildElement).equals(SchemaSymbols.ELT_SIMPLETYPE)) {
                xSSimpleTypeTraverseLocal = this.fSchemaHandler.fSimpleTypeTraverser.traverseLocal(firstChildElement, xSDocumentInfo, schemaGrammar);
                firstChildElement = DOMUtil.getNextSiblingElement(firstChildElement);
                z2 = true;
            } else {
                firstChildElement = firstChildElement;
                r1 = objArr2;
                z2 = false;
            }
        } else {
            firstChildElement = firstChildElement;
            r1 = objArr2;
            z2 = false;
        }
        if (r1 == 0 && qName != null) {
            XSTypeDefinition xSTypeDefinition = (XSTypeDefinition) this.fSchemaHandler.getGlobalDecl(xSDocumentInfo, 7, qName, element);
            if (xSTypeDefinition == null || xSTypeDefinition.getTypeCategory() != 16) {
                reportSchemaError("src-resolve", new Object[]{qName.rawname, "simpleType definition"}, element);
                if (xSTypeDefinition == null) {
                    attributeDecl.fUnresolvedTypeName = qName;
                }
            } else {
                r1 = (XSSimpleType) xSTypeDefinition;
            }
        }
        if (r1 == 0) {
            r1 = SchemaGrammar.fAnySimpleType;
        }
        if (xSAnnotationImplTraverseSyntheticAnnotation != null) {
            xSObjectListImpl = new XSObjectListImpl();
            xSObjectListImpl.addXSObject(xSAnnotationImplTraverseSyntheticAnnotation);
        } else {
            xSObjectListImpl = XSObjectListImpl.EMPTY_LIST;
        }
        attributeDecl.setValues(str7, str2, r1, s3, s2, validatedInfo, xSComplexTypeDecl3, xSObjectListImpl);
        if (str7 == null) {
            if (z) {
                reportSchemaError("s4s-att-must-appear", new Object[]{SchemaSymbols.ELT_ATTRIBUTE, SchemaSymbols.ATT_NAME}, element);
            } else {
                reportSchemaError("src-attribute.3.1", objArr2, element);
            }
            str3 = "(no name)";
        } else {
            str3 = str7;
        }
        if (firstChildElement != null) {
            reportSchemaError("s4s-elt-must-match.1", new Object[]{str3, "(annotation?, (simpleType?))", DOMUtil.getLocalName(firstChildElement)}, firstChildElement);
        }
        if (str5 != null && str6 != null) {
            reportSchemaError("src-attribute.1", new Object[]{str3}, element);
        }
        if (z2 && qName != null) {
            reportSchemaError("src-attribute.4", new Object[]{str3}, element);
        }
        checkNotationType(str3, r1, element);
        ValidatedInfo validatedInfo5 = validatedInfo;
        if (validatedInfo5 != null) {
            this.fValidationState.setNamespaceSupport(xSDocumentInfo.fNamespaceSupport);
            try {
                checkDefaultValid(attributeDecl);
                validatedInfo2 = validatedInfo5;
            } catch (InvalidDatatypeValueException e) {
                reportSchemaError(e.getKey(), e.getArgs(), element);
                reportSchemaError("a-props-correct.2", new Object[]{str3, validatedInfo5.normalizedValue}, element);
                validatedInfo2 = null;
                attributeDecl.setValues(str3, str2, r1, (short) 0, s2, null, xSComplexTypeDecl3, xSObjectListImpl);
            }
        } else {
            validatedInfo2 = validatedInfo5;
        }
        if (validatedInfo2 != null && r1.isIDType()) {
            reportSchemaError("a-props-correct.3", new Object[]{str3}, element);
            attributeDecl.setValues(str4, str2, r1, (short) 0, s2, null, xSComplexTypeDecl3, xSObjectListImpl);
        }
        if (str3 != 0 && str3.equals(XMLSymbols.PREFIX_XMLNS)) {
            str4 = str3;
            reportSchemaError("no-xmlns", null, element);
            return null;
        }
        str4 = str3;
        str4 = str3;
        String str8 = str2;
        if (str8 != null && str8.equals(SchemaSymbols.URI_XSI)) {
            reportSchemaError("no-xsi", new Object[]{SchemaSymbols.URI_XSI}, element);
            return null;
        }
        if (str3.equals("(no name)")) {
            return null;
        }
        if (z) {
            if (schemaGrammar.getGlobalAttributeDecl(str3) == null) {
                schemaGrammar.addGlobalAttributeDecl(attributeDecl);
            }
            String strSchemaDocument2SystemId = this.fSchemaHandler.schemaDocument2SystemId(xSDocumentInfo);
            XSAttributeDecl globalAttributeDecl = schemaGrammar.getGlobalAttributeDecl(str3, strSchemaDocument2SystemId);
            if (globalAttributeDecl == null) {
                schemaGrammar.addGlobalAttributeDecl(attributeDecl, strSchemaDocument2SystemId);
            }
            if (this.fSchemaHandler.fTolerateDuplicates) {
                ?? r15 = globalAttributeDecl != null ? globalAttributeDecl : attributeDecl;
                this.fSchemaHandler.addGlobalAttributeDecl(r15);
                return r15;
            }
        }
        return attributeDecl;
    }
}
