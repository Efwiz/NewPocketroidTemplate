package org.apache.xerces.impl.xs;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidatedInfo;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.impl.dv.XSSimpleType;
import org.apache.xerces.impl.xs.models.CMBuilder;
import org.apache.xerces.impl.xs.models.XSCMValidator;
import org.apache.xerces.impl.xs.util.SimpleLocator;
import org.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.apache.xerces.util.SymbolHash;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xs.XSObjectList;
import org.apache.xerces.xs.XSTerm;
import org.apache.xerces.xs.XSTypeDefinition;

/* JADX INFO: loaded from: classes13.dex */
public class XSConstraints {
    static final int OCCURRENCE_UNKNOWN = -2;
    static final XSSimpleType STRING_TYPE = (XSSimpleType) SchemaGrammar.SG_SchemaNS.getGlobalTypeDecl("string");
    private static XSParticleDecl fEmptyParticle = null;
    private static final Comparator ELEMENT_PARTICLE_COMPARATOR = new Comparator() { // from class: org.apache.xerces.impl.xs.XSConstraints.1
        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            XSElementDecl xSElementDecl = (XSElementDecl) ((XSParticleDecl) obj).fValue;
            XSElementDecl xSElementDecl2 = (XSElementDecl) ((XSParticleDecl) obj2).fValue;
            String namespace = xSElementDecl.getNamespace();
            String namespace2 = xSElementDecl2.getNamespace();
            String name2 = xSElementDecl.getName();
            String name3 = xSElementDecl2.getName();
            int iCompareTo = 1;
            if (namespace == namespace2) {
                iCompareTo = 0;
            } else if (namespace == null) {
                iCompareTo = -1;
            } else if (namespace2 != null) {
                iCompareTo = namespace.compareTo(namespace2);
            }
            return iCompareTo != 0 ? iCompareTo : name2.compareTo(name3);
        }
    };

    public static Object ElementDefaultValidImmediate(XSTypeDefinition xSTypeDefinition, String str, ValidationContext validationContext, ValidatedInfo validatedInfo) {
        XSSimpleType xSSimpleType;
        if (xSTypeDefinition.getTypeCategory() == 16) {
            xSSimpleType = (XSSimpleType) xSTypeDefinition;
        } else {
            XSComplexTypeDecl xSComplexTypeDecl = (XSComplexTypeDecl) xSTypeDefinition;
            if (xSComplexTypeDecl.fContentType == 1) {
                xSSimpleType = xSComplexTypeDecl.fXSSimpleType;
            } else {
                if (xSComplexTypeDecl.fContentType != 3 || !((XSParticleDecl) xSComplexTypeDecl.getParticle()).emptiable()) {
                    return null;
                }
                xSSimpleType = null;
            }
        }
        if (xSSimpleType == null) {
            xSSimpleType = STRING_TYPE;
        }
        try {
            return validatedInfo != null ? xSSimpleType.validate(validatedInfo.stringValue(), validationContext, validatedInfo) : xSSimpleType.validate(str, validationContext, validatedInfo);
        } catch (InvalidDatatypeValueException unused) {
            return null;
        }
    }

    private static void addElementToParticleVector(Vector vector, XSElementDecl xSElementDecl) {
        XSParticleDecl xSParticleDecl = new XSParticleDecl();
        xSParticleDecl.fValue = xSElementDecl;
        xSParticleDecl.fType = (short) 1;
        vector.addElement(xSParticleDecl);
    }

    private static boolean checkComplexDerivation(XSComplexTypeDecl xSComplexTypeDecl, XSTypeDefinition xSTypeDefinition, short s) {
        if (xSComplexTypeDecl == xSTypeDefinition) {
            return true;
        }
        if ((xSComplexTypeDecl.fDerivedBy & s) != 0) {
            return false;
        }
        XSTypeDefinition xSTypeDefinition2 = xSComplexTypeDecl.fBaseType;
        if (xSTypeDefinition2 == xSTypeDefinition) {
            return true;
        }
        if (xSTypeDefinition2 != SchemaGrammar.fAnyType && xSTypeDefinition2 != SchemaGrammar.fAnySimpleType) {
            if (xSTypeDefinition2.getTypeCategory() == 15) {
                return checkComplexDerivation((XSComplexTypeDecl) xSTypeDefinition2, xSTypeDefinition, s);
            }
            if (xSTypeDefinition2.getTypeCategory() == 16) {
                if (xSTypeDefinition.getTypeCategory() == 15) {
                    if (xSTypeDefinition != SchemaGrammar.fAnyType) {
                        return false;
                    }
                    xSTypeDefinition = SchemaGrammar.fAnySimpleType;
                }
                return checkSimpleDerivation((XSSimpleType) xSTypeDefinition2, (XSSimpleType) xSTypeDefinition, s);
            }
        }
        return false;
    }

    public static boolean checkComplexDerivationOk(XSComplexTypeDecl xSComplexTypeDecl, XSTypeDefinition xSTypeDefinition, short s) {
        if (xSComplexTypeDecl == SchemaGrammar.fAnyType) {
            return xSComplexTypeDecl == xSTypeDefinition;
        }
        return checkComplexDerivation(xSComplexTypeDecl, xSTypeDefinition, s);
    }

    public static void checkElementDeclsConsistent(XSComplexTypeDecl xSComplexTypeDecl, XSParticleDecl xSParticleDecl, SymbolHash symbolHash, SubstitutionGroupHandler substitutionGroupHandler) throws XMLSchemaException {
        short s = xSParticleDecl.fType;
        if (s == 2) {
            return;
        }
        int i = 0;
        XSTerm xSTerm = xSParticleDecl.fValue;
        if (s != 1) {
            XSModelGroupImpl xSModelGroupImpl = (XSModelGroupImpl) xSTerm;
            while (i < xSModelGroupImpl.fParticleCount) {
                checkElementDeclsConsistent(xSComplexTypeDecl, xSModelGroupImpl.fParticles[i], symbolHash, substitutionGroupHandler);
                i++;
            }
            return;
        }
        XSElementDecl xSElementDecl = (XSElementDecl) xSTerm;
        findElemInTable(xSComplexTypeDecl, xSElementDecl, symbolHash);
        if (xSElementDecl.fScope == 1) {
            XSElementDecl[] substitutionGroup = substitutionGroupHandler.getSubstitutionGroup(xSElementDecl);
            while (i < substitutionGroup.length) {
                findElemInTable(xSComplexTypeDecl, substitutionGroup[i], symbolHash);
                i++;
            }
        }
    }

    private static void checkIDConstraintRestriction(XSElementDecl xSElementDecl, XSElementDecl xSElementDecl2) throws XMLSchemaException {
    }

    private static void checkMapAndSum(Vector vector, int i, int i2, SubstitutionGroupHandler substitutionGroupHandler, Vector vector2, int i3, int i4, SubstitutionGroupHandler substitutionGroupHandler2) throws XMLSchemaException {
        if (!checkOccurrenceRange(i, i2, i3, i4)) {
            String string = Integer.toString(i);
            String string2 = SchemaSymbols.ATTVAL_UNBOUNDED;
            String string3 = i2 == -1 ? SchemaSymbols.ATTVAL_UNBOUNDED : Integer.toString(i2);
            String string4 = Integer.toString(i3);
            if (i4 != -1) {
                string2 = Integer.toString(i4);
            }
            throw new XMLSchemaException("rcase-MapAndSum.2", new Object[]{string, string3, string4, string2});
        }
        int size = vector.size();
        int size2 = vector2.size();
        for (int i5 = 0; i5 < size; i5++) {
            XSParticleDecl xSParticleDecl = (XSParticleDecl) vector.elementAt(i5);
            int i6 = 0;
            while (true) {
                if (i6 >= size2) {
                    throw new XMLSchemaException("rcase-MapAndSum.1", null);
                }
                try {
                    particleValidRestriction(xSParticleDecl, substitutionGroupHandler, (XSParticleDecl) vector2.elementAt(i6), substitutionGroupHandler2);
                    break;
                } catch (XMLSchemaException unused) {
                    i6++;
                }
            }
        }
    }

    private static void checkNSCompat(XSElementDecl xSElementDecl, int i, int i2, XSWildcardDecl xSWildcardDecl, int i3, int i4, boolean z) throws XMLSchemaException {
        if (!z || checkOccurrenceRange(i, i2, i3, i4)) {
            if (!xSWildcardDecl.allowNamespace(xSElementDecl.fTargetNamespace)) {
                throw new XMLSchemaException("rcase-NSCompat.1", new Object[]{xSElementDecl.fName, xSElementDecl.fTargetNamespace});
            }
            return;
        }
        String str = xSElementDecl.fName;
        String string = Integer.toString(i);
        String string2 = SchemaSymbols.ATTVAL_UNBOUNDED;
        String string3 = i2 == -1 ? SchemaSymbols.ATTVAL_UNBOUNDED : Integer.toString(i2);
        String string4 = Integer.toString(i3);
        if (i4 != -1) {
            string2 = Integer.toString(i4);
        }
        throw new XMLSchemaException("rcase-NSCompat.2", new Object[]{str, string, string3, string4, string2});
    }

    private static void checkNSRecurseCheckCardinality(Vector vector, int i, int i2, SubstitutionGroupHandler substitutionGroupHandler, XSParticleDecl xSParticleDecl, int i3, int i4, boolean z) throws XMLSchemaException {
        if (!z || checkOccurrenceRange(i, i2, i3, i4)) {
            int size = vector.size();
            for (int i5 = 0; i5 < size; i5++) {
                try {
                    particleValidRestriction((XSParticleDecl) vector.elementAt(i5), substitutionGroupHandler, xSParticleDecl, null, false);
                } catch (XMLSchemaException unused) {
                    throw new XMLSchemaException("rcase-NSRecurseCheckCardinality.1", null);
                }
            }
            return;
        }
        String string = Integer.toString(i);
        String string2 = SchemaSymbols.ATTVAL_UNBOUNDED;
        String string3 = i2 == -1 ? SchemaSymbols.ATTVAL_UNBOUNDED : Integer.toString(i2);
        String string4 = Integer.toString(i3);
        if (i4 != -1) {
            string2 = Integer.toString(i4);
        }
        throw new XMLSchemaException("rcase-NSRecurseCheckCardinality.2", new Object[]{string, string3, string4, string2});
    }

    private static void checkNSSubset(XSWildcardDecl xSWildcardDecl, int i, int i2, XSWildcardDecl xSWildcardDecl2, int i3, int i4) throws XMLSchemaException {
        if (checkOccurrenceRange(i, i2, i3, i4)) {
            if (!xSWildcardDecl.isSubsetOf(xSWildcardDecl2)) {
                throw new XMLSchemaException("rcase-NSSubset.1", null);
            }
            if (xSWildcardDecl.weakerProcessContents(xSWildcardDecl2)) {
                throw new XMLSchemaException("rcase-NSSubset.3", new Object[]{xSWildcardDecl.getProcessContentsAsString(), xSWildcardDecl2.getProcessContentsAsString()});
            }
            return;
        }
        String string = Integer.toString(i);
        String string2 = SchemaSymbols.ATTVAL_UNBOUNDED;
        String string3 = i2 == -1 ? SchemaSymbols.ATTVAL_UNBOUNDED : Integer.toString(i2);
        String string4 = Integer.toString(i3);
        if (i4 != -1) {
            string2 = Integer.toString(i4);
        }
        throw new XMLSchemaException("rcase-NSSubset.2", new Object[]{string, string3, string4, string2});
    }

    private static void checkNameAndTypeOK(XSElementDecl xSElementDecl, int i, int i2, XSElementDecl xSElementDecl2, int i3, int i4) throws XMLSchemaException {
        if (xSElementDecl.fName != xSElementDecl2.fName || xSElementDecl.fTargetNamespace != xSElementDecl2.fTargetNamespace) {
            throw new XMLSchemaException("rcase-NameAndTypeOK.1", new Object[]{xSElementDecl.fName, xSElementDecl.fTargetNamespace, xSElementDecl2.fName, xSElementDecl2.fTargetNamespace});
        }
        if (!xSElementDecl2.getNillable() && xSElementDecl.getNillable()) {
            throw new XMLSchemaException("rcase-NameAndTypeOK.2", new Object[]{xSElementDecl.fName});
        }
        if (!checkOccurrenceRange(i, i2, i3, i4)) {
            String str = xSElementDecl.fName;
            String string = Integer.toString(i);
            String string2 = SchemaSymbols.ATTVAL_UNBOUNDED;
            String string3 = i2 == -1 ? SchemaSymbols.ATTVAL_UNBOUNDED : Integer.toString(i2);
            String string4 = Integer.toString(i3);
            if (i4 != -1) {
                string2 = Integer.toString(i4);
            }
            throw new XMLSchemaException("rcase-NameAndTypeOK.3", new Object[]{str, string, string3, string4, string2});
        }
        if (xSElementDecl2.getConstraintType() == 2) {
            if (xSElementDecl.getConstraintType() != 2) {
                throw new XMLSchemaException("rcase-NameAndTypeOK.4.a", new Object[]{xSElementDecl.fName, xSElementDecl2.fDefault.stringValue()});
            }
            boolean z = true;
            if (xSElementDecl.fType.getTypeCategory() != 16 && ((XSComplexTypeDecl) xSElementDecl.fType).fContentType != 1) {
                z = false;
            }
            if ((!z && !xSElementDecl2.fDefault.normalizedValue.equals(xSElementDecl.fDefault.normalizedValue)) || (z && !xSElementDecl2.fDefault.actualValue.equals(xSElementDecl.fDefault.actualValue))) {
                throw new XMLSchemaException("rcase-NameAndTypeOK.4.b", new Object[]{xSElementDecl.fName, xSElementDecl.fDefault.stringValue(), xSElementDecl2.fDefault.stringValue()});
            }
        }
        checkIDConstraintRestriction(xSElementDecl, xSElementDecl2);
        short s = xSElementDecl.fBlock;
        short s2 = xSElementDecl2.fBlock;
        if ((s & s2) != s2 || (s == 0 && s2 != 0)) {
            throw new XMLSchemaException("rcase-NameAndTypeOK.6", new Object[]{xSElementDecl.fName});
        }
        if (!checkTypeDerivationOk(xSElementDecl.fType, xSElementDecl2.fType, (short) 25)) {
            throw new XMLSchemaException("rcase-NameAndTypeOK.7", new Object[]{xSElementDecl.fName, xSElementDecl.fType.getName(), xSElementDecl2.fType.getName()});
        }
    }

    private static boolean checkOccurrenceRange(int i, int i2, int i3, int i4) {
        if (i < i3) {
            return false;
        }
        if (i4 != -1) {
            return i2 != -1 && i2 <= i4;
        }
        return true;
    }

    private static void checkRecurse(Vector vector, int i, int i2, SubstitutionGroupHandler substitutionGroupHandler, Vector vector2, int i3, int i4, SubstitutionGroupHandler substitutionGroupHandler2) throws XMLSchemaException {
        if (!checkOccurrenceRange(i, i2, i3, i4)) {
            String string = Integer.toString(i);
            String string2 = SchemaSymbols.ATTVAL_UNBOUNDED;
            String string3 = i2 == -1 ? SchemaSymbols.ATTVAL_UNBOUNDED : Integer.toString(i2);
            String string4 = Integer.toString(i3);
            if (i4 != -1) {
                string2 = Integer.toString(i4);
            }
            throw new XMLSchemaException("rcase-Recurse.1", new Object[]{string, string3, string4, string2});
        }
        int size = vector.size();
        int size2 = vector2.size();
        int i5 = 0;
        int i6 = 0;
        while (i5 < size) {
            XSParticleDecl xSParticleDecl = (XSParticleDecl) vector.elementAt(i5);
            int i7 = i6;
            while (true) {
                if (i6 >= size2) {
                    throw new XMLSchemaException("rcase-Recurse.2", null);
                }
                XSParticleDecl xSParticleDecl2 = (XSParticleDecl) vector2.elementAt(i6);
                i7++;
                try {
                    particleValidRestriction(xSParticleDecl, substitutionGroupHandler, xSParticleDecl2, substitutionGroupHandler2);
                    break;
                } catch (XMLSchemaException unused) {
                    if (!xSParticleDecl2.emptiable()) {
                        throw new XMLSchemaException("rcase-Recurse.2", null);
                    }
                    i6++;
                }
            }
            i5++;
            i6 = i7;
        }
        while (i6 < size2) {
            if (!((XSParticleDecl) vector2.elementAt(i6)).emptiable()) {
                throw new XMLSchemaException("rcase-Recurse.2", null);
            }
            i6++;
        }
    }

    private static void checkRecurseLax(Vector vector, int i, int i2, SubstitutionGroupHandler substitutionGroupHandler, Vector vector2, int i3, int i4, SubstitutionGroupHandler substitutionGroupHandler2) throws XMLSchemaException {
        int i5;
        if (!checkOccurrenceRange(i, i2, i3, i4)) {
            String string = Integer.toString(i);
            String string2 = SchemaSymbols.ATTVAL_UNBOUNDED;
            String string3 = i2 == -1 ? SchemaSymbols.ATTVAL_UNBOUNDED : Integer.toString(i2);
            String string4 = Integer.toString(i3);
            if (i4 != -1) {
                string2 = Integer.toString(i4);
            }
            throw new XMLSchemaException("rcase-RecurseLax.1", new Object[]{string, string3, string4, string2});
        }
        int size = vector.size();
        int size2 = vector2.size();
        int i6 = 0;
        for (int i7 = 0; i7 < size; i7++) {
            XSParticleDecl xSParticleDecl = (XSParticleDecl) vector.elementAt(i7);
            int i8 = i6;
            while (true) {
                if (i6 >= size2) {
                    throw new XMLSchemaException("rcase-RecurseLax.2", null);
                }
                i5 = i8 + 1;
                try {
                } catch (XMLSchemaException unused) {
                    i6++;
                    i8 = i5;
                }
            }
            i6 = particleValidRestriction(xSParticleDecl, substitutionGroupHandler, (XSParticleDecl) vector2.elementAt(i6), substitutionGroupHandler2) ? i8 : i5;
        }
    }

    /* JADX WARN: Code duplicated, block: B:17:0x003d  */
    /* JADX WARN: Code duplicated, block: B:19:0x0046  */
    /* JADX WARN: Code duplicated, block: B:23:0x0059 A[Catch: XMLSchemaException -> 0x005f, TryCatch #0 {XMLSchemaException -> 0x005f, blocks: (B:20:0x004c, B:22:0x0053, B:23:0x0059, B:24:0x005e), top: B:38:0x004c }] */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    private static void checkRecurseUnordered(java.util.Vector r6, int r7, int r8, org.apache.xerces.impl.xs.SubstitutionGroupHandler r9, java.util.Vector r10, int r11, int r12, org.apache.xerces.impl.xs.SubstitutionGroupHandler r13) throws org.apache.xerces.impl.xs.XMLSchemaException {
        /*
            boolean r0 = checkOccurrenceRange(r7, r8, r11, r12)
            if (r0 != 0) goto L2c
            org.apache.xerces.impl.xs.XMLSchemaException r6 = new org.apache.xerces.impl.xs.XMLSchemaException
            java.lang.String r7 = java.lang.Integer.toString(r7)
            java.lang.String r9 = "unbounded"
            r10 = -1
            if (r8 != r10) goto L13
            r8 = r9
            goto L17
        L13:
            java.lang.String r8 = java.lang.Integer.toString(r8)
        L17:
            java.lang.String r11 = java.lang.Integer.toString(r11)
            if (r12 != r10) goto L1e
            goto L22
        L1e:
            java.lang.String r9 = java.lang.Integer.toString(r12)
        L22:
            java.lang.Object[] r7 = new java.lang.Object[]{r7, r8, r11, r9}
            java.lang.String r8 = "rcase-RecurseUnordered.1"
            r6.<init>(r8, r7)
            throw r6
        L2c:
            int r7 = r6.size()
            int r8 = r10.size()
            boolean[] r11 = new boolean[r8]
            r12 = 0
            r0 = r12
        L38:
            r1 = 0
            java.lang.String r2 = "rcase-RecurseUnordered.2"
            if (r0 >= r7) goto L68
            java.lang.Object r3 = r6.elementAt(r0)
            org.apache.xerces.impl.xs.XSParticleDecl r3 = (org.apache.xerces.impl.xs.XSParticleDecl) r3
            r4 = r12
        L44:
            if (r4 >= r8) goto L62
            java.lang.Object r5 = r10.elementAt(r4)
            org.apache.xerces.impl.xs.XSParticleDecl r5 = (org.apache.xerces.impl.xs.XSParticleDecl) r5
            particleValidRestriction(r3, r9, r5, r13)     // Catch: org.apache.xerces.impl.xs.XMLSchemaException -> L5f
            boolean r5 = r11[r4]     // Catch: org.apache.xerces.impl.xs.XMLSchemaException -> L5f
            if (r5 != 0) goto L59
            r5 = 1
            r11[r4] = r5     // Catch: org.apache.xerces.impl.xs.XMLSchemaException -> L5f
            int r0 = r0 + 1
            goto L38
        L59:
            org.apache.xerces.impl.xs.XMLSchemaException r5 = new org.apache.xerces.impl.xs.XMLSchemaException     // Catch: org.apache.xerces.impl.xs.XMLSchemaException -> L5f
            r5.<init>(r2, r1)     // Catch: org.apache.xerces.impl.xs.XMLSchemaException -> L5f
            throw r5     // Catch: org.apache.xerces.impl.xs.XMLSchemaException -> L5f
        L5f:
            int r4 = r4 + 1
            goto L44
        L62:
            org.apache.xerces.impl.xs.XMLSchemaException r6 = new org.apache.xerces.impl.xs.XMLSchemaException
            r6.<init>(r2, r1)
            throw r6
        L68:
            if (r12 >= r8) goto L84
            java.lang.Object r6 = r10.elementAt(r12)
            org.apache.xerces.impl.xs.XSParticleDecl r6 = (org.apache.xerces.impl.xs.XSParticleDecl) r6
            boolean r7 = r11[r12]
            if (r7 != 0) goto L81
            boolean r6 = r6.emptiable()
            if (r6 == 0) goto L7b
            goto L81
        L7b:
            org.apache.xerces.impl.xs.XMLSchemaException r6 = new org.apache.xerces.impl.xs.XMLSchemaException
            r6.<init>(r2, r1)
            throw r6
        L81:
            int r12 = r12 + 1
            goto L68
        L84:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.xerces.impl.xs.XSConstraints.checkRecurseUnordered(java.util.Vector, int, int, org.apache.xerces.impl.xs.SubstitutionGroupHandler, java.util.Vector, int, int, org.apache.xerces.impl.xs.SubstitutionGroupHandler):void");
    }

    private static boolean checkSimpleDerivation(XSSimpleType xSSimpleType, XSSimpleType xSSimpleType2, short s) {
        if (xSSimpleType == xSSimpleType2) {
            return true;
        }
        if ((s & 2) == 0 && (xSSimpleType.getBaseType().getFinal() & 2) == 0) {
            XSSimpleType xSSimpleType3 = (XSSimpleType) xSSimpleType.getBaseType();
            if (xSSimpleType3 == xSSimpleType2) {
                return true;
            }
            if (xSSimpleType3 != SchemaGrammar.fAnySimpleType && checkSimpleDerivation(xSSimpleType3, xSSimpleType2, s)) {
                return true;
            }
            if ((xSSimpleType.getVariety() == 2 || xSSimpleType.getVariety() == 3) && xSSimpleType2 == SchemaGrammar.fAnySimpleType) {
                return true;
            }
            if (xSSimpleType2.getVariety() == 3) {
                XSObjectList memberTypes = xSSimpleType2.getMemberTypes();
                int length = memberTypes.getLength();
                for (int i = 0; i < length; i++) {
                    if (checkSimpleDerivation(xSSimpleType, (XSSimpleType) memberTypes.item(i), s)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkSimpleDerivationOk(XSSimpleType xSSimpleType, XSTypeDefinition xSTypeDefinition, short s) {
        if (xSSimpleType == SchemaGrammar.fAnySimpleType) {
            return xSTypeDefinition == SchemaGrammar.fAnyType || xSTypeDefinition == SchemaGrammar.fAnySimpleType;
        }
        if (xSTypeDefinition.getTypeCategory() == 15) {
            if (xSTypeDefinition != SchemaGrammar.fAnyType) {
                return false;
            }
            xSTypeDefinition = SchemaGrammar.fAnySimpleType;
        }
        return checkSimpleDerivation(xSSimpleType, (XSSimpleType) xSTypeDefinition, s);
    }

    public static boolean checkTypeDerivationOk(XSTypeDefinition xSTypeDefinition, XSTypeDefinition xSTypeDefinition2, short s) {
        if (xSTypeDefinition == SchemaGrammar.fAnyType) {
            return xSTypeDefinition == xSTypeDefinition2;
        }
        if (xSTypeDefinition == SchemaGrammar.fAnySimpleType) {
            return xSTypeDefinition2 == SchemaGrammar.fAnyType || xSTypeDefinition2 == SchemaGrammar.fAnySimpleType;
        }
        if (xSTypeDefinition.getTypeCategory() != 16) {
            return checkComplexDerivation((XSComplexTypeDecl) xSTypeDefinition, xSTypeDefinition2, s);
        }
        if (xSTypeDefinition2.getTypeCategory() == 15) {
            if (xSTypeDefinition2 != SchemaGrammar.fAnyType) {
                return false;
            }
            xSTypeDefinition2 = SchemaGrammar.fAnySimpleType;
        }
        return checkSimpleDerivation((XSSimpleType) xSTypeDefinition, (XSSimpleType) xSTypeDefinition2, s);
    }

    public static void findElemInTable(XSComplexTypeDecl xSComplexTypeDecl, XSElementDecl xSElementDecl, SymbolHash symbolHash) throws XMLSchemaException {
        String str = xSElementDecl.fName + "," + xSElementDecl.fTargetNamespace;
        XSElementDecl xSElementDecl2 = (XSElementDecl) symbolHash.get(str);
        if (xSElementDecl2 == null) {
            symbolHash.put(str, xSElementDecl);
        } else if (xSElementDecl != xSElementDecl2 && xSElementDecl.fType != xSElementDecl2.fType) {
            throw new XMLSchemaException("cos-element-consistent", new Object[]{xSComplexTypeDecl.fName, xSElementDecl.fName});
        }
    }

    public static void fullSchemaChecking(XSGrammarBucket xSGrammarBucket, SubstitutionGroupHandler substitutionGroupHandler, CMBuilder cMBuilder, XMLErrorReporter xMLErrorReporter) {
        boolean zCheckUniqueParticleAttribution;
        SchemaGrammar[] grammars = xSGrammarBucket.getGrammars();
        for (int length = grammars.length - 1; length >= 0; length--) {
            substitutionGroupHandler.addSubstitutionGroup(grammars[length].getSubstitutionGroups());
        }
        XSParticleDecl xSParticleDecl = new XSParticleDecl();
        XSParticleDecl xSParticleDecl2 = new XSParticleDecl();
        xSParticleDecl.fType = (short) 3;
        xSParticleDecl2.fType = (short) 3;
        int length2 = grammars.length - 1;
        while (true) {
            int i = 0;
            if (length2 < 0) {
                break;
            }
            XSGroupDecl[] redefinedGroupDecls = grammars[length2].getRedefinedGroupDecls();
            SimpleLocator[] rGLocators = grammars[length2].getRGLocators();
            while (i < redefinedGroupDecls.length) {
                int i2 = i + 1;
                XSGroupDecl xSGroupDecl = redefinedGroupDecls[i];
                XSModelGroupImpl xSModelGroupImpl = xSGroupDecl.fModelGroup;
                i += 2;
                XSModelGroupImpl xSModelGroupImpl2 = redefinedGroupDecls[i2].fModelGroup;
                xSParticleDecl.fValue = xSModelGroupImpl;
                xSParticleDecl2.fValue = xSModelGroupImpl2;
                if (xSModelGroupImpl2 == null) {
                    if (xSModelGroupImpl != null) {
                        reportSchemaError(xMLErrorReporter, rGLocators[(i / 2) - 1], "src-redefine.6.2.2", new Object[]{xSGroupDecl.fName, "rcase-Recurse.2"});
                    }
                } else if (xSModelGroupImpl != null) {
                    try {
                        particleValidRestriction(xSParticleDecl, substitutionGroupHandler, xSParticleDecl2, substitutionGroupHandler);
                    } catch (XMLSchemaException e) {
                        String key = e.getKey();
                        int i3 = (i / 2) - 1;
                        reportSchemaError(xMLErrorReporter, rGLocators[i3], key, e.getArgs());
                        reportSchemaError(xMLErrorReporter, rGLocators[i3], "src-redefine.6.2.2", new Object[]{xSGroupDecl.fName, key});
                    }
                } else if (!xSParticleDecl2.emptiable()) {
                    reportSchemaError(xMLErrorReporter, rGLocators[(i / 2) - 1], "src-redefine.6.2.2", new Object[]{xSGroupDecl.fName, "rcase-Recurse.2"});
                }
            }
            length2--;
        }
        SymbolHash symbolHash = new SymbolHash();
        for (int length3 = grammars.length - 1; length3 >= 0; length3--) {
            boolean z = grammars[length3].fFullChecked;
            XSComplexTypeDecl[] uncheckedComplexTypeDecls = grammars[length3].getUncheckedComplexTypeDecls();
            SimpleLocator[] uncheckedCTLocators = grammars[length3].getUncheckedCTLocators();
            int i4 = 0;
            for (int i5 = 0; i5 < uncheckedComplexTypeDecls.length; i5++) {
                if (!z && uncheckedComplexTypeDecls[i5].fParticle != null) {
                    symbolHash.clear();
                    try {
                        XSComplexTypeDecl xSComplexTypeDecl = uncheckedComplexTypeDecls[i5];
                        checkElementDeclsConsistent(xSComplexTypeDecl, xSComplexTypeDecl.fParticle, symbolHash, substitutionGroupHandler);
                    } catch (XMLSchemaException e2) {
                        reportSchemaError(xMLErrorReporter, uncheckedCTLocators[i5], e2.getKey(), e2.getArgs());
                    }
                }
                if (uncheckedComplexTypeDecls[i5].fBaseType != null && uncheckedComplexTypeDecls[i5].fBaseType != SchemaGrammar.fAnyType && uncheckedComplexTypeDecls[i5].fDerivedBy == 2 && (uncheckedComplexTypeDecls[i5].fBaseType instanceof XSComplexTypeDecl)) {
                    XSParticleDecl xSParticleDecl3 = uncheckedComplexTypeDecls[i5].fParticle;
                    XSParticleDecl xSParticleDecl4 = ((XSComplexTypeDecl) uncheckedComplexTypeDecls[i5].fBaseType).fParticle;
                    if (xSParticleDecl3 == null) {
                        if (xSParticleDecl4 != null && !xSParticleDecl4.emptiable()) {
                            reportSchemaError(xMLErrorReporter, uncheckedCTLocators[i5], "derivation-ok-restriction.5.3.2", new Object[]{uncheckedComplexTypeDecls[i5].fName, uncheckedComplexTypeDecls[i5].fBaseType.getName()});
                        }
                    } else if (xSParticleDecl4 != null) {
                        try {
                            XSParticleDecl xSParticleDecl5 = uncheckedComplexTypeDecls[i5].fParticle;
                            XSComplexTypeDecl xSComplexTypeDecl2 = (XSComplexTypeDecl) uncheckedComplexTypeDecls[i5].fBaseType;
                            particleValidRestriction(xSParticleDecl5, substitutionGroupHandler, xSComplexTypeDecl2.fParticle, substitutionGroupHandler);
                        } catch (XMLSchemaException e3) {
                            reportSchemaError(xMLErrorReporter, uncheckedCTLocators[i5], e3.getKey(), e3.getArgs());
                            reportSchemaError(xMLErrorReporter, uncheckedCTLocators[i5], "derivation-ok-restriction.5.4.2", new Object[]{uncheckedComplexTypeDecls[i5].fName});
                        }
                    } else {
                        reportSchemaError(xMLErrorReporter, uncheckedCTLocators[i5], "derivation-ok-restriction.5.4.2", new Object[]{uncheckedComplexTypeDecls[i5].fName});
                    }
                }
                XSCMValidator contentModel = uncheckedComplexTypeDecls[i5].getContentModel(cMBuilder, true);
                if (contentModel != null) {
                    try {
                        zCheckUniqueParticleAttribution = contentModel.checkUniqueParticleAttribution(substitutionGroupHandler);
                    } catch (XMLSchemaException e4) {
                        reportSchemaError(xMLErrorReporter, uncheckedCTLocators[i5], e4.getKey(), e4.getArgs());
                        zCheckUniqueParticleAttribution = false;
                    }
                } else {
                    zCheckUniqueParticleAttribution = false;
                }
                if (!z && zCheckUniqueParticleAttribution) {
                    uncheckedComplexTypeDecls[i4] = uncheckedComplexTypeDecls[i5];
                    i4++;
                }
            }
            if (!z) {
                grammars[length3].setUncheckedTypeNum(i4);
                grammars[length3].fFullChecked = true;
            }
        }
    }

    private static void gatherChildren(int i, XSParticleDecl xSParticleDecl, Vector vector) {
        int i2 = xSParticleDecl.fMinOccurs;
        int i3 = xSParticleDecl.fMaxOccurs;
        short s = xSParticleDecl.fType;
        if (s == 3) {
            s = ((XSModelGroupImpl) xSParticleDecl.fValue).fCompositor;
        }
        if (s == 1 || s == 2) {
            vector.addElement(xSParticleDecl);
            return;
        }
        if (i2 == 1 && i3 == 1) {
            if (i == s) {
                XSModelGroupImpl xSModelGroupImpl = (XSModelGroupImpl) xSParticleDecl.fValue;
                for (int i4 = 0; i4 < xSModelGroupImpl.fParticleCount; i4++) {
                    gatherChildren(s, xSModelGroupImpl.fParticles[i4], vector);
                }
                return;
            }
            if (xSParticleDecl.isEmpty()) {
                return;
            }
        }
        vector.addElement(xSParticleDecl);
    }

    public static XSParticleDecl getEmptySequence() {
        if (fEmptyParticle == null) {
            XSModelGroupImpl xSModelGroupImpl = new XSModelGroupImpl();
            xSModelGroupImpl.fCompositor = (short) 102;
            xSModelGroupImpl.fParticleCount = 0;
            xSModelGroupImpl.fParticles = null;
            xSModelGroupImpl.fAnnotations = XSObjectListImpl.EMPTY_LIST;
            XSParticleDecl xSParticleDecl = new XSParticleDecl();
            xSParticleDecl.fType = (short) 3;
            xSParticleDecl.fValue = xSModelGroupImpl;
            xSParticleDecl.fAnnotations = XSObjectListImpl.EMPTY_LIST;
            fEmptyParticle = xSParticleDecl;
        }
        return fEmptyParticle;
    }

    private static XSParticleDecl getNonUnaryGroup(XSParticleDecl xSParticleDecl) {
        return (xSParticleDecl.fType == 1 || xSParticleDecl.fType == 2 || xSParticleDecl.fMinOccurs != 1 || xSParticleDecl.fMaxOccurs != 1 || xSParticleDecl.fValue == null || ((XSModelGroupImpl) xSParticleDecl.fValue).fParticleCount != 1) ? xSParticleDecl : getNonUnaryGroup(((XSModelGroupImpl) xSParticleDecl.fValue).fParticles[0]);
    }

    public static boolean overlapUPA(Object obj, Object obj2, SubstitutionGroupHandler substitutionGroupHandler) {
        if (!(obj instanceof XSElementDecl)) {
            return obj2 instanceof XSElementDecl ? overlapUPA((XSElementDecl) obj2, (XSWildcardDecl) obj, substitutionGroupHandler) : overlapUPA((XSWildcardDecl) obj, (XSWildcardDecl) obj2);
        }
        XSElementDecl xSElementDecl = (XSElementDecl) obj;
        return obj2 instanceof XSElementDecl ? overlapUPA(xSElementDecl, (XSElementDecl) obj2, substitutionGroupHandler) : overlapUPA(xSElementDecl, (XSWildcardDecl) obj2, substitutionGroupHandler);
    }

    public static boolean overlapUPA(XSElementDecl xSElementDecl, XSElementDecl xSElementDecl2, SubstitutionGroupHandler substitutionGroupHandler) {
        if (xSElementDecl.fName == xSElementDecl2.fName && xSElementDecl.fTargetNamespace == xSElementDecl2.fTargetNamespace) {
            return true;
        }
        XSElementDecl[] substitutionGroup = substitutionGroupHandler.getSubstitutionGroup(xSElementDecl);
        for (int length = substitutionGroup.length - 1; length >= 0; length--) {
            if (substitutionGroup[length].fName == xSElementDecl2.fName && substitutionGroup[length].fTargetNamespace == xSElementDecl2.fTargetNamespace) {
                return true;
            }
        }
        XSElementDecl[] substitutionGroup2 = substitutionGroupHandler.getSubstitutionGroup(xSElementDecl2);
        for (int length2 = substitutionGroup2.length - 1; length2 >= 0; length2--) {
            if (substitutionGroup2[length2].fName == xSElementDecl.fName && substitutionGroup2[length2].fTargetNamespace == xSElementDecl.fTargetNamespace) {
                return true;
            }
        }
        return false;
    }

    public static boolean overlapUPA(XSElementDecl xSElementDecl, XSWildcardDecl xSWildcardDecl, SubstitutionGroupHandler substitutionGroupHandler) {
        if (xSWildcardDecl.allowNamespace(xSElementDecl.fTargetNamespace)) {
            return true;
        }
        XSElementDecl[] substitutionGroup = substitutionGroupHandler.getSubstitutionGroup(xSElementDecl);
        for (int length = substitutionGroup.length - 1; length >= 0; length--) {
            if (xSWildcardDecl.allowNamespace(substitutionGroup[length].fTargetNamespace)) {
                return true;
            }
        }
        return false;
    }

    public static boolean overlapUPA(XSWildcardDecl xSWildcardDecl, XSWildcardDecl xSWildcardDecl2) {
        XSWildcardDecl xSWildcardDeclPerformIntersectionWith = xSWildcardDecl.performIntersectionWith(xSWildcardDecl2, xSWildcardDecl.fProcessContents);
        return (xSWildcardDeclPerformIntersectionWith != null && xSWildcardDeclPerformIntersectionWith.fType == 3 && xSWildcardDeclPerformIntersectionWith.fNamespaceList.length == 0) ? false : true;
    }

    private static boolean particleValidRestriction(XSParticleDecl xSParticleDecl, SubstitutionGroupHandler substitutionGroupHandler, XSParticleDecl xSParticleDecl2, SubstitutionGroupHandler substitutionGroupHandler2) throws XMLSchemaException {
        return particleValidRestriction(xSParticleDecl, substitutionGroupHandler, xSParticleDecl2, substitutionGroupHandler2, true);
    }

    /* JADX WARN: Code duplicated, block: B:39:0x009a  */
    /* JADX WARN: Code duplicated, block: B:63:0x00fe  */
    private static boolean particleValidRestriction(XSParticleDecl xSParticleDecl, SubstitutionGroupHandler substitutionGroupHandler, XSParticleDecl xSParticleDecl2, SubstitutionGroupHandler substitutionGroupHandler2, boolean z) throws XMLSchemaException {
        short s;
        Vector vectorRemovePointlessChildren;
        SubstitutionGroupHandler substitutionGroupHandler3;
        Vector vector;
        int iMinEffectiveTotalRange;
        int i;
        XSParticleDecl nonUnaryGroup;
        Vector vectorRemovePointlessChildren2;
        Vector vector2;
        SubstitutionGroupHandler substitutionGroupHandler4;
        short s2;
        boolean z2;
        XSParticleDecl xSParticleDecl3 = xSParticleDecl;
        if (xSParticleDecl.isEmpty() && !xSParticleDecl2.emptiable()) {
            throw new XMLSchemaException("cos-particle-restrict.a", null);
        }
        if (!xSParticleDecl.isEmpty() && xSParticleDecl2.isEmpty()) {
            throw new XMLSchemaException("cos-particle-restrict.b", null);
        }
        short s3 = xSParticleDecl3.fType;
        if (s3 == 3) {
            short s4 = ((XSModelGroupImpl) xSParticleDecl3.fValue).fCompositor;
            XSParticleDecl nonUnaryGroup2 = getNonUnaryGroup(xSParticleDecl);
            if (nonUnaryGroup2 != xSParticleDecl3) {
                short s5 = nonUnaryGroup2.fType;
                if (s5 == 3) {
                    s5 = ((XSModelGroupImpl) nonUnaryGroup2.fValue).fCompositor;
                }
                s4 = s5;
            } else {
                nonUnaryGroup2 = xSParticleDecl3;
            }
            short s6 = s4;
            vectorRemovePointlessChildren = removePointlessChildren(nonUnaryGroup2);
            xSParticleDecl3 = nonUnaryGroup2;
            s = s6;
        } else {
            s = s3;
            vectorRemovePointlessChildren = null;
        }
        int i2 = xSParticleDecl3.fMinOccurs;
        int size = xSParticleDecl3.fMaxOccurs;
        if (substitutionGroupHandler == null || s != 1) {
            substitutionGroupHandler3 = substitutionGroupHandler;
            vector = vectorRemovePointlessChildren;
            iMinEffectiveTotalRange = -2;
            i = -2;
        } else {
            XSElementDecl xSElementDecl = (XSElementDecl) xSParticleDecl3.fValue;
            if (xSElementDecl.fScope == 1) {
                XSElementDecl[] substitutionGroup = substitutionGroupHandler.getSubstitutionGroup(xSElementDecl);
                if (substitutionGroup.length > 0) {
                    Vector vector3 = new Vector(substitutionGroup.length + 1);
                    for (XSElementDecl xSElementDecl2 : substitutionGroup) {
                        addElementToParticleVector(vector3, xSElementDecl2);
                    }
                    addElementToParticleVector(vector3, xSElementDecl);
                    Collections.sort(vector3, ELEMENT_PARTICLE_COMPARATOR);
                    vector = vector3;
                    iMinEffectiveTotalRange = i2;
                    i = size;
                    s = 101;
                    substitutionGroupHandler3 = null;
                } else {
                    substitutionGroupHandler3 = substitutionGroupHandler;
                    vector = vectorRemovePointlessChildren;
                    iMinEffectiveTotalRange = -2;
                    i = -2;
                }
            } else {
                substitutionGroupHandler3 = substitutionGroupHandler;
                vector = vectorRemovePointlessChildren;
                iMinEffectiveTotalRange = -2;
                i = -2;
            }
        }
        short s7 = xSParticleDecl2.fType;
        if (s7 == 3) {
            s7 = ((XSModelGroupImpl) xSParticleDecl2.fValue).fCompositor;
            nonUnaryGroup = getNonUnaryGroup(xSParticleDecl2);
            if (nonUnaryGroup != xSParticleDecl2) {
                short s8 = nonUnaryGroup.fType;
                if (s8 == 3) {
                    s8 = ((XSModelGroupImpl) nonUnaryGroup.fValue).fCompositor;
                }
                s7 = s8;
            } else {
                nonUnaryGroup = xSParticleDecl2;
            }
            vectorRemovePointlessChildren2 = removePointlessChildren(nonUnaryGroup);
        } else {
            nonUnaryGroup = xSParticleDecl2;
            vectorRemovePointlessChildren2 = null;
        }
        int i3 = nonUnaryGroup.fMinOccurs;
        int i4 = nonUnaryGroup.fMaxOccurs;
        if (substitutionGroupHandler2 == null || s7 != 1) {
            vector2 = vectorRemovePointlessChildren2;
            substitutionGroupHandler4 = substitutionGroupHandler2;
            s2 = s7;
            z2 = false;
        } else {
            XSElementDecl xSElementDecl3 = (XSElementDecl) nonUnaryGroup.fValue;
            if (xSElementDecl3.fScope == 1) {
                XSElementDecl[] substitutionGroup2 = substitutionGroupHandler2.getSubstitutionGroup(xSElementDecl3);
                if (substitutionGroup2.length > 0) {
                    Vector vector4 = new Vector(substitutionGroup2.length + 1);
                    for (XSElementDecl xSElementDecl4 : substitutionGroup2) {
                        addElementToParticleVector(vector4, xSElementDecl4);
                    }
                    addElementToParticleVector(vector4, xSElementDecl3);
                    Collections.sort(vector4, ELEMENT_PARTICLE_COMPARATOR);
                    vector2 = vector4;
                    z2 = true;
                    s2 = 101;
                    substitutionGroupHandler4 = null;
                } else {
                    vector2 = vectorRemovePointlessChildren2;
                    substitutionGroupHandler4 = substitutionGroupHandler2;
                    s2 = s7;
                    z2 = false;
                }
            } else {
                vector2 = vectorRemovePointlessChildren2;
                substitutionGroupHandler4 = substitutionGroupHandler2;
                s2 = s7;
                z2 = false;
            }
        }
        if (s == 1) {
            if (s2 == 1) {
                checkNameAndTypeOK((XSElementDecl) xSParticleDecl3.fValue, i2, size, (XSElementDecl) nonUnaryGroup.fValue, i3, i4);
                return z2;
            }
            if (s2 == 2) {
                checkNSCompat((XSElementDecl) xSParticleDecl3.fValue, i2, size, (XSWildcardDecl) nonUnaryGroup.fValue, i3, i4, z);
                return z2;
            }
            switch (s2) {
                case 101:
                    Vector vector5 = new Vector();
                    vector5.addElement(xSParticleDecl3);
                    checkRecurseLax(vector5, 1, 1, substitutionGroupHandler3, vector2, i3, i4, substitutionGroupHandler4);
                    return z2;
                case 102:
                case 103:
                    Vector vector6 = new Vector();
                    vector6.addElement(xSParticleDecl3);
                    checkRecurse(vector6, 1, 1, substitutionGroupHandler3, vector2, i3, i4, substitutionGroupHandler4);
                    return z2;
                default:
                    throw new XMLSchemaException("Internal-Error", new Object[]{"in particleValidRestriction"});
            }
        }
        if (s == 2) {
            if (s2 != 1) {
                if (s2 == 2) {
                    checkNSSubset((XSWildcardDecl) xSParticleDecl3.fValue, i2, size, (XSWildcardDecl) nonUnaryGroup.fValue, i3, i4);
                    return z2;
                }
                switch (s2) {
                    case 101:
                    case 102:
                    case 103:
                        break;
                    default:
                        throw new XMLSchemaException("Internal-Error", new Object[]{"in particleValidRestriction"});
                }
            }
            throw new XMLSchemaException("cos-particle-restrict.2", new Object[]{"any:choice,sequence,all,elt"});
        }
        switch (s) {
            case 101:
                if (s2 != 1) {
                    if (s2 == 2) {
                        if (iMinEffectiveTotalRange == -2) {
                            iMinEffectiveTotalRange = xSParticleDecl3.minEffectiveTotalRange();
                        }
                        checkNSRecurseCheckCardinality(vector, iMinEffectiveTotalRange, i == -2 ? xSParticleDecl3.maxEffectiveTotalRange() : i, substitutionGroupHandler3, nonUnaryGroup, i3, i4, z);
                        return z2;
                    }
                    switch (s2) {
                        case 101:
                            checkRecurseLax(vector, i2, size, substitutionGroupHandler3, vector2, i3, i4, substitutionGroupHandler4);
                            return z2;
                        case 102:
                        case 103:
                            break;
                        default:
                            throw new XMLSchemaException("Internal-Error", new Object[]{"in particleValidRestriction"});
                    }
                }
                throw new XMLSchemaException("cos-particle-restrict.2", new Object[]{"choice:all,sequence,elt"});
            case 102:
                if (s2 == 1) {
                    throw new XMLSchemaException("cos-particle-restrict.2", new Object[]{"seq:elt"});
                }
                if (s2 == 2) {
                    if (iMinEffectiveTotalRange == -2) {
                        iMinEffectiveTotalRange = xSParticleDecl3.minEffectiveTotalRange();
                    }
                    checkNSRecurseCheckCardinality(vector, iMinEffectiveTotalRange, i == -2 ? xSParticleDecl3.maxEffectiveTotalRange() : i, substitutionGroupHandler3, nonUnaryGroup, i3, i4, z);
                    return z2;
                }
                switch (s2) {
                    case 101:
                        int size2 = i2 * vector.size();
                        if (size != -1) {
                            size *= vector.size();
                        }
                        checkMapAndSum(vector, size2, size, substitutionGroupHandler3, vector2, i3, i4, substitutionGroupHandler4);
                        return z2;
                    case 102:
                        checkRecurse(vector, i2, size, substitutionGroupHandler3, vector2, i3, i4, substitutionGroupHandler4);
                        return z2;
                    case 103:
                        checkRecurseUnordered(vector, i2, size, substitutionGroupHandler3, vector2, i3, i4, substitutionGroupHandler4);
                        return z2;
                    default:
                        throw new XMLSchemaException("Internal-Error", new Object[]{"in particleValidRestriction"});
                }
            case 103:
                if (s2 != 1) {
                    if (s2 == 2) {
                        if (iMinEffectiveTotalRange == -2) {
                            iMinEffectiveTotalRange = xSParticleDecl3.minEffectiveTotalRange();
                        }
                        checkNSRecurseCheckCardinality(vector, iMinEffectiveTotalRange, i == -2 ? xSParticleDecl3.maxEffectiveTotalRange() : i, substitutionGroupHandler3, nonUnaryGroup, i3, i4, z);
                        return z2;
                    }
                    switch (s2) {
                        case 101:
                        case 102:
                            break;
                        case 103:
                            checkRecurse(vector, i2, size, substitutionGroupHandler3, vector2, i3, i4, substitutionGroupHandler4);
                            return z2;
                        default:
                            throw new XMLSchemaException("Internal-Error", new Object[]{"in particleValidRestriction"});
                    }
                }
                throw new XMLSchemaException("cos-particle-restrict.2", new Object[]{"all:choice,sequence,elt"});
            default:
                return z2;
        }
    }

    private static Vector removePointlessChildren(XSParticleDecl xSParticleDecl) {
        if (xSParticleDecl.fType == 1 || xSParticleDecl.fType == 2) {
            return null;
        }
        Vector vector = new Vector();
        XSModelGroupImpl xSModelGroupImpl = (XSModelGroupImpl) xSParticleDecl.fValue;
        for (int i = 0; i < xSModelGroupImpl.fParticleCount; i++) {
            gatherChildren(xSModelGroupImpl.fCompositor, xSModelGroupImpl.fParticles[i], vector);
        }
        return vector;
    }

    static void reportSchemaError(XMLErrorReporter xMLErrorReporter, SimpleLocator simpleLocator, String str, Object[] objArr) {
        if (simpleLocator != null) {
            xMLErrorReporter.reportError((XMLLocator) simpleLocator, XSMessageFormatter.SCHEMA_DOMAIN, str, objArr, (short) 1);
        } else {
            xMLErrorReporter.reportError(XSMessageFormatter.SCHEMA_DOMAIN, str, objArr, (short) 1);
        }
    }
}
