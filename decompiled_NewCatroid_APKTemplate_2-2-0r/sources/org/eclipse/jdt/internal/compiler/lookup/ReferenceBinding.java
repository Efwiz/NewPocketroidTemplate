package org.eclipse.jdt.internal.compiler.lookup;

import io.netty.util.internal.StringUtil;
import java.util.Arrays;
import java.util.Comparator;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.eclipse.jdt.internal.compiler.ast.LambdaExpression;
import org.eclipse.jdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.NullAnnotationMatching;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.util.SimpleLookupTable;

/* JADX INFO: loaded from: classes14.dex */
public abstract class ReferenceBinding extends TypeBinding {
    private SimpleLookupTable compatibleCache;
    public char[][] compoundName;
    char[] constantPoolName;
    public PackageBinding fPackage;
    char[] fileName;
    public int modifiers;
    char[] signature;
    protected MethodBinding[] singleAbstractMethod;
    public char[] sourceName;
    int typeBits;
    public static final ReferenceBinding LUB_GENERIC = new ReferenceBinding() { // from class: org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding.1
        @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
        public boolean hasTypeBit(int i) {
            return false;
        }

        {
            this.f626id = 0;
        }
    };
    private static final Comparator<FieldBinding> FIELD_COMPARATOR = new Comparator<FieldBinding>() { // from class: org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding.2
        @Override // java.util.Comparator
        public int compare(FieldBinding fieldBinding, FieldBinding fieldBinding2) {
            char[] cArr = fieldBinding.f628name;
            char[] cArr2 = fieldBinding2.f628name;
            return ReferenceBinding.compare(cArr, cArr2, cArr.length, cArr2.length);
        }
    };
    private static final Comparator<MethodBinding> METHOD_COMPARATOR = new Comparator<MethodBinding>() { // from class: org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding.3
        @Override // java.util.Comparator
        public int compare(MethodBinding methodBinding, MethodBinding methodBinding2) {
            char[] cArr = methodBinding.selector;
            char[] cArr2 = methodBinding2.selector;
            int iCompare = ReferenceBinding.compare(cArr, cArr2, cArr.length, cArr2.length);
            return iCompare == 0 ? methodBinding.parameters.length - methodBinding2.parameters.length : iCompare;
        }
    };
    protected static ProblemMethodBinding samProblemBinding = new ProblemMethodBinding(TypeConstants.ANONYMOUS_METHOD, null, 17);

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean acceptsNonNullDefault() {
        return true;
    }

    public MethodBinding getExactConstructor(TypeBinding[] typeBindingArr) {
        return null;
    }

    public MethodBinding getExactMethod(char[] cArr, TypeBinding[] typeBindingArr, CompilationUnitScope compilationUnitScope) {
        return null;
    }

    public FieldBinding getField(char[] cArr, boolean z) {
        return null;
    }

    int getNullDefault() {
        return 0;
    }

    public int getOuterLocalVariablesSlotSize() {
        return 0;
    }

    public boolean hasMemberTypes() {
        return false;
    }

    public boolean isHierarchyConnected() {
        return true;
    }

    public void setContainerAnnotationType(ReferenceBinding referenceBinding) {
    }

    SimpleLookupTable storedAnnotations(boolean z) {
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding superclass() {
        return null;
    }

    public void tagAsHavingDefectiveContainerType() {
    }

    public ReferenceBinding(ReferenceBinding referenceBinding) {
        super(referenceBinding);
        this.compoundName = referenceBinding.compoundName;
        this.sourceName = referenceBinding.sourceName;
        this.modifiers = referenceBinding.modifiers;
        this.fPackage = referenceBinding.fPackage;
        this.fileName = referenceBinding.fileName;
        this.constantPoolName = referenceBinding.constantPoolName;
        this.signature = referenceBinding.signature;
        this.compatibleCache = referenceBinding.compatibleCache;
        this.typeBits = referenceBinding.typeBits;
        this.singleAbstractMethod = referenceBinding.singleAbstractMethod;
    }

    public ReferenceBinding() {
    }

    public static FieldBinding binarySearch(char[] cArr, FieldBinding[] fieldBindingArr) {
        int length;
        if (fieldBindingArr == null || (length = fieldBindingArr.length) == 0) {
            return null;
        }
        int i = length - 1;
        int length2 = cArr.length;
        int i2 = 0;
        while (i2 <= i) {
            int i3 = ((i - i2) / 2) + i2;
            char[] cArr2 = fieldBindingArr[i3].f628name;
            int iCompare = compare(cArr, cArr2, length2, cArr2.length);
            if (iCompare < 0) {
                i = i3 - 1;
            } else {
                if (iCompare <= 0) {
                    return fieldBindingArr[i3];
                }
                i2 = i3 + 1;
            }
        }
        return null;
    }

    public static long binarySearch(char[] cArr, MethodBinding[] methodBindingArr) {
        int length;
        if (methodBindingArr == null || (length = methodBindingArr.length) == 0) {
            return -1L;
        }
        int i = length - 1;
        int length2 = cArr.length;
        int i2 = 0;
        while (i2 <= i) {
            int i3 = ((i - i2) / 2) + i2;
            char[] cArr2 = methodBindingArr[i3].selector;
            int iCompare = compare(cArr, cArr2, length2, cArr2.length);
            if (iCompare < 0) {
                i = i3 - 1;
            } else {
                if (iCompare <= 0) {
                    int i4 = i3;
                    while (i4 > i2 && CharOperation.equals(methodBindingArr[i4 - 1].selector, cArr)) {
                        i4--;
                    }
                    while (i3 < i) {
                        int i5 = i3 + 1;
                        if (!CharOperation.equals(methodBindingArr[i5].selector, cArr)) {
                            break;
                        }
                        i3 = i5;
                    }
                    return ((long) i4) + (((long) i3) << 32);
                }
                i2 = i3 + 1;
            }
        }
        return -1L;
    }

    static int compare(char[] cArr, char[] cArr2, int i, int i2) {
        int iMin = Math.min(i, i2);
        int i3 = 0;
        while (true) {
            int i4 = iMin - 1;
            if (iMin == 0) {
                return i - i2;
            }
            char c = cArr[i3];
            int i5 = i3 + 1;
            char c2 = cArr2[i3];
            if (c != c2) {
                return c - c2;
            }
            iMin = i4;
            i3 = i5;
        }
    }

    public static void sortFields(FieldBinding[] fieldBindingArr, int i, int i2) {
        Arrays.sort(fieldBindingArr, i, i2, FIELD_COMPARATOR);
    }

    public static void sortMethods(MethodBinding[] methodBindingArr, int i, int i2) {
        Arrays.sort(methodBindingArr, i, i2, METHOD_COMPARATOR);
    }

    public FieldBinding[] availableFields() {
        return fields();
    }

    public MethodBinding[] availableMethods() {
        return methods();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean canBeInstantiated() {
        return (this.modifiers & 26112) == 0;
    }

    public boolean canBeSeenBy(PackageBinding packageBinding) {
        if (isPublic()) {
            return true;
        }
        return !isPrivate() && packageBinding == this.fPackage;
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x008f, code lost:
    
        if (r9.isErasureBoundTo(enclosingType().erasure()) != false) goto L45;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean canBeSeenBy(org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding r9, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding r10) {
        /*
            Method dump skipped, instruction units count: 266
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding.canBeSeenBy(org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding):boolean");
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean canBeSeenBy(Scope scope) {
        SourceTypeBinding sourceTypeBinding;
        if (isPublic()) {
            return true;
        }
        SourceTypeBinding sourceTypeBindingEnclosingSourceType = scope.enclosingSourceType();
        if (TypeBinding.equalsEquals(sourceTypeBindingEnclosingSourceType, this)) {
            return true;
        }
        if (sourceTypeBindingEnclosingSourceType == null) {
            return !isPrivate() && scope.getCurrentPackage() == this.fPackage;
        }
        if (isProtected()) {
            if (sourceTypeBindingEnclosingSourceType.fPackage == this.fPackage) {
                return true;
            }
            ReferenceBinding referenceBindingEnclosingType = enclosingType();
            if (referenceBindingEnclosingType == null) {
                return false;
            }
            TypeBinding typeBindingErasure = referenceBindingEnclosingType.erasure();
            TypeBinding typeBindingErasure2 = sourceTypeBindingEnclosingSourceType.erasure();
            while (!TypeBinding.equalsEquals(typeBindingErasure, sourceTypeBindingEnclosingSourceType) && typeBindingErasure2.findSuperTypeOriginatingFrom(typeBindingErasure) == null) {
                typeBindingErasure2 = typeBindingErasure2.enclosingType();
                if (typeBindingErasure2 == null) {
                    return false;
                }
            }
            return true;
        }
        if (!isPrivate()) {
            return sourceTypeBindingEnclosingSourceType.fPackage == this.fPackage;
        }
        ReferenceBinding referenceBindingEnclosingType2 = sourceTypeBindingEnclosingSourceType.enclosingType();
        while (true) {
            sourceTypeBinding = sourceTypeBindingEnclosingSourceType;
            sourceTypeBindingEnclosingSourceType = referenceBindingEnclosingType2;
            if (sourceTypeBindingEnclosingSourceType == null) {
                break;
            }
            referenceBindingEnclosingType2 = sourceTypeBindingEnclosingSourceType.enclosingType();
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) erasure();
        ReferenceBinding referenceBindingEnclosingType3 = referenceBinding.enclosingType();
        while (true) {
            ReferenceBinding referenceBinding2 = referenceBindingEnclosingType3;
            ReferenceBinding referenceBinding3 = referenceBinding;
            referenceBinding = referenceBinding2;
            if (referenceBinding != null) {
                referenceBindingEnclosingType3 = referenceBinding.enclosingType();
            } else {
                return TypeBinding.equalsEquals(sourceTypeBinding, referenceBinding3);
            }
        }
    }

    public char[] computeGenericTypeSignature(TypeVariableBinding[] typeVariableBindingArr) {
        boolean z = isMemberType() && (enclosingType().modifiers & 1073741824) != 0;
        if (typeVariableBindingArr == Binding.NO_TYPE_VARIABLES && !z) {
            return signature();
        }
        StringBuffer stringBuffer = new StringBuffer(10);
        if (z) {
            char[] cArrGenericTypeSignature = enclosingType().genericTypeSignature();
            stringBuffer.append(cArrGenericTypeSignature, 0, cArrGenericTypeSignature.length - 1);
            stringBuffer.append('.');
            stringBuffer.append(this.sourceName);
        } else {
            char[] cArrSignature = signature();
            stringBuffer.append(cArrSignature, 0, cArrSignature.length - 1);
        }
        if (typeVariableBindingArr == Binding.NO_TYPE_VARIABLES) {
            stringBuffer.append(';');
        } else {
            stringBuffer.append('<');
            for (TypeVariableBinding typeVariableBinding : typeVariableBindingArr) {
                stringBuffer.append(typeVariableBinding.genericTypeSignature());
            }
            stringBuffer.append(">;");
        }
        int length = stringBuffer.length();
        char[] cArr = new char[length];
        stringBuffer.getChars(0, length, cArr, 0);
        return cArr;
    }

    public void computeId() {
        char[][] cArr = this.compoundName;
        int length = cArr.length;
        if (length != 3) {
            if (length == 4) {
                if (CharOperation.equals(TypeConstants.COM_GOOGLE_INJECT_INJECT, this.compoundName)) {
                    this.f626id = 81;
                    return;
                }
                if (CharOperation.equals(TypeConstants.JAVA, this.compoundName[0])) {
                    char[][] cArr2 = this.compoundName;
                    if (cArr2[1].length == 0) {
                        return;
                    }
                    char[] cArr3 = cArr2[2];
                    if (cArr3.length == 0) {
                        return;
                    }
                    char[] cArr4 = cArr2[3];
                    if (cArr4.length == 0) {
                        return;
                    }
                    char c = cArr3[0];
                    if (c != 'a') {
                        if (c == 'i') {
                            if (CharOperation.equals(cArr3, TypeConstants.INVOKE) && cArr4.length != 0 && cArr4[0] == 'M' && CharOperation.equals(cArr4, TypeConstants.JAVA_LANG_INVOKE_METHODHANDLE_$_POLYMORPHICSIGNATURE[3])) {
                                this.f626id = 61;
                                return;
                            }
                            return;
                        }
                        if (c == 'r' && CharOperation.equals(cArr3, TypeConstants.REFLECT)) {
                            char c2 = cArr4[0];
                            if (c2 == 'C') {
                                if (CharOperation.equals(cArr4, TypeConstants.JAVA_LANG_REFLECT_CONSTRUCTOR[2])) {
                                    this.f626id = 20;
                                    return;
                                }
                                return;
                            } else if (c2 == 'F') {
                                if (CharOperation.equals(cArr4, TypeConstants.JAVA_LANG_REFLECT_FIELD[2])) {
                                    this.f626id = 54;
                                    return;
                                }
                                return;
                            } else {
                                if (c2 == 'M' && CharOperation.equals(cArr4, TypeConstants.JAVA_LANG_REFLECT_METHOD[2])) {
                                    this.f626id = 55;
                                    return;
                                }
                                return;
                            }
                        }
                        return;
                    }
                    if (CharOperation.equals(cArr3, TypeConstants.ANNOTATION)) {
                        char c3 = cArr4[0];
                        if (c3 == 'A') {
                            if (CharOperation.equals(cArr4, TypeConstants.JAVA_LANG_ANNOTATION_ANNOTATION[3])) {
                                this.f626id = 43;
                                return;
                            }
                            return;
                        }
                        if (c3 == 'I') {
                            if (CharOperation.equals(cArr4, TypeConstants.JAVA_LANG_ANNOTATION_INHERITED[3])) {
                                this.f626id = 46;
                                return;
                            }
                            return;
                        }
                        if (c3 != 'R') {
                            if (c3 == 'T') {
                                if (CharOperation.equals(cArr4, TypeConstants.JAVA_LANG_ANNOTATION_TARGET[3])) {
                                    this.f626id = 50;
                                    return;
                                }
                                return;
                            } else if (c3 == 'D') {
                                if (CharOperation.equals(cArr4, TypeConstants.JAVA_LANG_ANNOTATION_DOCUMENTED[3])) {
                                    this.f626id = 45;
                                    return;
                                }
                                return;
                            } else {
                                if (c3 == 'E' && CharOperation.equals(cArr4, TypeConstants.JAVA_LANG_ANNOTATION_ELEMENTTYPE[3])) {
                                    this.f626id = 52;
                                    return;
                                }
                                return;
                            }
                        }
                        int length2 = cArr4.length;
                        if (length2 == 9) {
                            if (CharOperation.equals(cArr4, TypeConstants.JAVA_LANG_ANNOTATION_RETENTION[3])) {
                                this.f626id = 48;
                                return;
                            }
                            return;
                        } else if (length2 == 10) {
                            if (CharOperation.equals(cArr4, TypeConstants.JAVA_LANG_ANNOTATION_REPEATABLE[3])) {
                                this.f626id = 90;
                                return;
                            }
                            return;
                        } else {
                            if (length2 == 15 && CharOperation.equals(cArr4, TypeConstants.JAVA_LANG_ANNOTATION_RETENTIONPOLICY[3])) {
                                this.f626id = 51;
                                return;
                            }
                            return;
                        }
                    }
                    return;
                }
                return;
            }
            if (length != 5) {
                if (length == 6) {
                    if (CharOperation.equals(TypeConstants.ORG, this.compoundName[0])) {
                        if (CharOperation.equals(TypeConstants.SPRING, this.compoundName[1])) {
                            if (CharOperation.equals(TypeConstants.AUTOWIRED, this.compoundName[5]) && CharOperation.equals(TypeConstants.ORG_SPRING_AUTOWIRED, this.compoundName)) {
                                this.f626id = 82;
                                return;
                            }
                            return;
                        }
                        if (CharOperation.equals(TypeConstants.JDT, this.compoundName[2]) && CharOperation.equals(TypeConstants.ITYPEBINDING, this.compoundName[5]) && CharOperation.equals(TypeConstants.ORG_ECLIPSE_JDT_CORE_DOM_ITYPEBINDING, this.compoundName)) {
                            this.typeBits |= 16;
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (length == 7 && CharOperation.equals(TypeConstants.JDT, this.compoundName[2]) && CharOperation.equals(TypeConstants.TYPEBINDING, this.compoundName[6]) && CharOperation.equals(TypeConstants.ORG_ECLIPSE_JDT_INTERNAL_COMPILER_LOOKUP_TYPEBINDING, this.compoundName)) {
                    this.typeBits |= 16;
                    return;
                }
                return;
            }
            char c4 = cArr[0][0];
            if (c4 == 'c') {
                if (CharOperation.equals(TypeConstants.COM, this.compoundName[0]) && CharOperation.equals(TypeConstants.COM_GOOGLE_COMMON_BASE_PRECONDITIONS, this.compoundName)) {
                    this.f626id = 73;
                    return;
                }
                return;
            }
            if (c4 == 'j') {
                if (CharOperation.equals(TypeConstants.JAVA, this.compoundName[0])) {
                    char[] cArr5 = this.compoundName[1];
                    if (cArr5.length != 0 && CharOperation.equals(TypeConstants.LANG, cArr5)) {
                        char[] cArr6 = this.compoundName[2];
                        if (cArr6.length != 0 && cArr6[0] == 'i' && CharOperation.equals(cArr6, TypeConstants.INVOKE)) {
                            char[][] cArr7 = this.compoundName;
                            char[] cArr8 = cArr7[3];
                            if (cArr8.length != 0 && cArr8[0] == 'M') {
                                char[] cArr9 = cArr7[4];
                                if (cArr9.length != 0 && CharOperation.equals(cArr8, TypeConstants.JAVA_LANG_INVOKE_METHODHANDLE_POLYMORPHICSIGNATURE[3]) && CharOperation.equals(cArr9, TypeConstants.JAVA_LANG_INVOKE_METHODHANDLE_POLYMORPHICSIGNATURE[4])) {
                                    this.f626id = 61;
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            if (c4 == 'o' && CharOperation.equals(TypeConstants.ORG, this.compoundName[0])) {
                char[] cArr10 = this.compoundName[1];
                if (cArr10.length == 0) {
                    return;
                }
                char c5 = cArr10[0];
                if (c5 != 'a') {
                    if (c5 == 'e' && CharOperation.equals(TypeConstants.ECLIPSE, cArr10)) {
                        char[] cArr11 = this.compoundName[2];
                        if (cArr11.length != 0 && cArr11[0] == 'c' && CharOperation.equals(cArr11, TypeConstants.CORE)) {
                            char[][] cArr12 = this.compoundName;
                            char[] cArr13 = cArr12[3];
                            if (cArr13.length != 0 && cArr13[0] == 'r') {
                                char[] cArr14 = cArr12[4];
                                if (cArr14.length != 0 && CharOperation.equals(cArr13, TypeConstants.ORG_ECLIPSE_CORE_RUNTIME_ASSERT[3]) && CharOperation.equals(cArr14, TypeConstants.ORG_ECLIPSE_CORE_RUNTIME_ASSERT[4])) {
                                    this.f626id = 68;
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (CharOperation.equals(TypeConstants.APACHE, cArr10) && CharOperation.equals(TypeConstants.COMMONS, this.compoundName[2])) {
                    if (CharOperation.equals(TypeConstants.ORG_APACHE_COMMONS_LANG_VALIDATE, this.compoundName)) {
                        this.f626id = 71;
                        return;
                    } else {
                        if (CharOperation.equals(TypeConstants.ORG_APACHE_COMMONS_LANG3_VALIDATE, this.compoundName)) {
                            this.f626id = 72;
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            return;
        }
        char[] cArr15 = cArr[0];
        int length3 = cArr15.length;
        if (length3 == 3) {
            if (CharOperation.equals(TypeConstants.ORG_JUNIT_ASSERT, this.compoundName)) {
                this.f626id = 70;
                return;
            }
            return;
        }
        if (length3 != 4) {
            if (length3 != 5) {
                return;
            }
            char c6 = cArr15[1];
            if (c6 == 'a') {
                if (CharOperation.equals(TypeConstants.JAVAX_ANNOTATION_INJECT_INJECT, this.compoundName)) {
                    this.f626id = 80;
                    return;
                }
                return;
            } else {
                if (c6 == 'u' && CharOperation.equals(TypeConstants.JUNIT_FRAMEWORK_ASSERT, this.compoundName)) {
                    this.f626id = 69;
                    return;
                }
                return;
            }
        }
        if (CharOperation.equals(TypeConstants.JAVA, cArr15)) {
            char[][] cArr16 = this.compoundName;
            char[] cArr17 = cArr16[1];
            if (cArr17.length == 0) {
                return;
            }
            char[] cArr18 = cArr16[2];
            if (cArr18.length == 0) {
                return;
            }
            if (!CharOperation.equals(TypeConstants.LANG, this.compoundName[1])) {
                char c7 = cArr17[0];
                if (c7 == 'i') {
                    if (CharOperation.equals(cArr17, TypeConstants.IO)) {
                        char c8 = cArr18[0];
                        if (c8 == 'C') {
                            if (CharOperation.equals(cArr18, TypeConstants.JAVA_IO_CLOSEABLE[2])) {
                                this.typeBits |= 2;
                                return;
                            }
                            return;
                        }
                        if (c8 == 'E') {
                            if (CharOperation.equals(cArr18, TypeConstants.JAVA_IO_EXTERNALIZABLE[2])) {
                                this.f626id = 56;
                                return;
                            }
                            return;
                        }
                        if (c8 == 'I') {
                            if (CharOperation.equals(cArr18, TypeConstants.JAVA_IO_IOEXCEPTION[2])) {
                                this.f626id = 58;
                                return;
                            }
                            return;
                        } else if (c8 == 'S') {
                            if (CharOperation.equals(cArr18, TypeConstants.JAVA_IO_SERIALIZABLE[2])) {
                                this.f626id = 37;
                                return;
                            }
                            return;
                        } else if (c8 == 'O') {
                            if (CharOperation.equals(cArr18, TypeConstants.JAVA_IO_OBJECTSTREAMEXCEPTION[2])) {
                                this.f626id = 57;
                                return;
                            }
                            return;
                        } else {
                            if (c8 == 'P' && CharOperation.equals(cArr18, TypeConstants.JAVA_IO_PRINTSTREAM[2])) {
                                this.f626id = 53;
                                return;
                            }
                            return;
                        }
                    }
                    return;
                }
                if (c7 == 'u' && CharOperation.equals(cArr17, TypeConstants.UTIL)) {
                    char c9 = cArr18[0];
                    if (c9 == 'C') {
                        if (CharOperation.equals(cArr18, TypeConstants.JAVA_UTIL_COLLECTION[2])) {
                            this.f626id = 59;
                            return;
                        }
                        return;
                    } else if (c9 == 'I') {
                        if (CharOperation.equals(cArr18, TypeConstants.JAVA_UTIL_ITERATOR[2])) {
                            this.f626id = 39;
                            return;
                        }
                        return;
                    } else {
                        if (c9 == 'O' && CharOperation.equals(cArr18, TypeConstants.JAVA_UTIL_OBJECTS[2])) {
                            this.f626id = 74;
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            switch (cArr18[0]) {
                case 'A':
                    int length4 = cArr18.length;
                    if (length4 == 13) {
                        if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_AUTOCLOSEABLE[2])) {
                            this.f626id = 62;
                            this.typeBits |= 1;
                        }
                    } else if (length4 == 14 && CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_ASSERTIONERROR[2])) {
                        this.f626id = 35;
                    }
                    break;
                case 'B':
                    int length5 = cArr18.length;
                    if (length5 == 4) {
                        if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_BYTE[2])) {
                            this.f626id = 26;
                        }
                    } else if (length5 == 7 && CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_BOOLEAN[2])) {
                        this.f626id = 33;
                    }
                    break;
                case 'C':
                    int length6 = cArr18.length;
                    if (length6 == 5) {
                        if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_CLASS[2])) {
                            this.f626id = 16;
                        }
                    } else if (length6 == 9) {
                        if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_CHARACTER[2])) {
                            this.f626id = 28;
                        } else if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_CLONEABLE[2])) {
                            this.f626id = 36;
                        }
                    } else if (length6 == 22 && CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_CLASSNOTFOUNDEXCEPTION[2])) {
                        this.f626id = 23;
                    }
                    break;
                case 'D':
                    int length7 = cArr18.length;
                    if (length7 == 6) {
                        if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_DOUBLE[2])) {
                            this.f626id = 32;
                        }
                    } else if (length7 == 10 && CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_DEPRECATED[2])) {
                        this.f626id = 44;
                    }
                    break;
                case 'E':
                    int length8 = cArr18.length;
                    if (length8 == 4) {
                        if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_ENUM[2])) {
                            this.f626id = 41;
                        }
                    } else if (length8 == 5) {
                        if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_ERROR[2])) {
                            this.f626id = 19;
                        }
                    } else if (length8 == 9 && CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_EXCEPTION[2])) {
                        this.f626id = 25;
                    }
                    break;
                case 'F':
                    if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_FLOAT[2])) {
                        this.f626id = 31;
                    } else if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_FUNCTIONAL_INTERFACE[2])) {
                        this.f626id = 77;
                    }
                    break;
                case 'I':
                    int length9 = cArr18.length;
                    if (length9 == 7) {
                        if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_INTEGER[2])) {
                            this.f626id = 29;
                        }
                    } else if (length9 == 8) {
                        if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_ITERABLE[2])) {
                            this.f626id = 38;
                        }
                    } else if (length9 == 24 && CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_ILLEGALARGUMENTEXCEPTION[2])) {
                        this.f626id = 42;
                    }
                    break;
                case 'L':
                    if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_LONG[2])) {
                        this.f626id = 30;
                    }
                    break;
                case 'N':
                    if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_NOCLASSDEFERROR[2])) {
                        this.f626id = 22;
                    }
                    break;
                case 'O':
                    int length10 = cArr18.length;
                    if (length10 == 6) {
                        if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_OBJECT[2])) {
                            this.f626id = 1;
                        }
                    } else if (length10 == 8 && CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_OVERRIDE[2])) {
                        this.f626id = 47;
                    }
                    break;
                case 'R':
                    if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_RUNTIMEEXCEPTION[2])) {
                        this.f626id = 24;
                    }
                    break;
                case 'S':
                    int length11 = cArr18.length;
                    if (length11 != 5) {
                        if (length11 != 6) {
                            if (length11 != 16) {
                                switch (length11) {
                                    case 11:
                                        if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_SAFEVARARGS[2])) {
                                            this.f626id = 60;
                                        }
                                        break;
                                    case 12:
                                        if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_STRINGBUFFER[2])) {
                                            this.f626id = 17;
                                        }
                                        break;
                                    case 13:
                                        if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_STRINGBUILDER[2])) {
                                            this.f626id = 40;
                                        }
                                        break;
                                }
                            } else {
                                if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_SUPPRESSWARNINGS[2])) {
                                    this.f626id = 49;
                                }
                                break;
                            }
                        } else {
                            if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_STRING[2])) {
                                this.f626id = 11;
                            } else if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_SYSTEM[2])) {
                                this.f626id = 18;
                            }
                            break;
                        }
                    } else {
                        if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_SHORT[2])) {
                            this.f626id = 27;
                        }
                        break;
                    }
                    break;
                case 'T':
                    if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_THROWABLE[2])) {
                        this.f626id = 21;
                    }
                    break;
                case 'V':
                    if (CharOperation.equals(cArr18, TypeConstants.JAVA_LANG_VOID[2])) {
                        this.f626id = 34;
                    }
                    break;
            }
        }
    }

    public void computeId(LookupEnvironment lookupEnvironment) {
        lookupEnvironment.getUnannotatedType(this);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] computeUniqueKey(boolean z) {
        if (!z) {
            return signature();
        }
        return genericTypeSignature();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] constantPoolName() {
        char[] cArr = this.constantPoolName;
        if (cArr != null) {
            return cArr;
        }
        char[] cArrConcatWith = CharOperation.concatWith(this.compoundName, '/');
        this.constantPoolName = cArrConcatWith;
        return cArrConcatWith;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public String debugName() {
        if (this.compoundName != null) {
            return hasTypeAnnotations() ? annotatedDebugName() : new String(readableName());
        }
        return "UNNAMED TYPE";
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public int depth() {
        int i = 0;
        ReferenceBinding referenceBindingEnclosingType = this;
        while (true) {
            referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType();
            if (referenceBindingEnclosingType == null) {
                return i;
            }
            i++;
        }
    }

    public boolean detectAnnotationCycle() {
        if ((this.tagBits & 4294967296L) != 0) {
            return false;
        }
        if ((this.tagBits & 2147483648L) != 0) {
            return true;
        }
        this.tagBits |= 2147483648L;
        MethodBinding[] methodBindingArrMethods = methods();
        int length = methodBindingArrMethods.length;
        boolean z = false;
        for (int i = 0; i < length; i++) {
            TypeBinding typeBindingErasure = methodBindingArrMethods[i].returnType.leafComponentType().erasure();
            if (TypeBinding.equalsEquals(this, typeBindingErasure)) {
                if (this instanceof SourceTypeBinding) {
                    MethodDeclaration methodDeclaration = (MethodDeclaration) methodBindingArrMethods[i].sourceMethod();
                    ((SourceTypeBinding) this).scope.problemReporter().annotationCircularity(this, this, methodDeclaration != null ? methodDeclaration.returnType : null);
                }
            } else if (typeBindingErasure.isAnnotationType() && ((ReferenceBinding) typeBindingErasure).detectAnnotationCycle()) {
                if (this instanceof SourceTypeBinding) {
                    MethodDeclaration methodDeclaration2 = (MethodDeclaration) methodBindingArrMethods[i].sourceMethod();
                    ((SourceTypeBinding) this).scope.problemReporter().annotationCircularity(this, typeBindingErasure, methodDeclaration2 != null ? methodDeclaration2.returnType : null);
                }
                z = true;
            }
        }
        if (z) {
            return true;
        }
        this.tagBits = 4294967296L | this.tagBits;
        return false;
    }

    public final ReferenceBinding enclosingTypeAt(int i) {
        ReferenceBinding referenceBindingEnclosingType = this;
        while (true) {
            int i2 = i - 1;
            if (i <= 0 || referenceBindingEnclosingType == null) {
                break;
            }
            referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType();
            i = i2;
        }
        return referenceBindingEnclosingType;
    }

    public int enumConstantCount() {
        int i = 0;
        for (FieldBinding fieldBinding : fields()) {
            if ((fieldBinding.modifiers & 16384) != 0) {
                i++;
            }
        }
        return i;
    }

    public int fieldCount() {
        return fields().length;
    }

    public FieldBinding[] fields() {
        return Binding.NO_FIELDS;
    }

    public final int getAccessFlags() {
        return this.modifiers & 65535;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public AnnotationBinding[] getAnnotations() {
        return retrieveAnnotations(this);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public long getAnnotationTagBits() {
        return this.tagBits;
    }

    public int getEnclosingInstancesSlotSize() {
        return (isStatic() || enclosingType() == null) ? 0 : 1;
    }

    public char[] getFileName() {
        return this.fileName;
    }

    public ReferenceBinding getMemberType(char[] cArr) {
        ReferenceBinding[] referenceBindingArrMemberTypes = memberTypes();
        int length = referenceBindingArrMemberTypes.length;
        do {
            length--;
            if (length < 0) {
                return null;
            }
        } while (!CharOperation.equals(referenceBindingArrMemberTypes[length].sourceName, cArr));
        return referenceBindingArrMemberTypes[length];
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public MethodBinding[] getMethods(char[] cArr) {
        return Binding.NO_METHODS;
    }

    public MethodBinding[] getMethods(char[] cArr, int i) {
        return getMethods(cArr);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public PackageBinding getPackage() {
        return this.fPackage;
    }

    public TypeVariableBinding getTypeVariable(char[] cArr) {
        TypeVariableBinding[] typeVariableBindingArrTypeVariables = typeVariables();
        int length = typeVariableBindingArrTypeVariables.length;
        do {
            length--;
            if (length < 0) {
                return null;
            }
        } while (!CharOperation.equals(typeVariableBindingArrTypeVariables[length].sourceName, cArr));
        return typeVariableBindingArrTypeVariables[length];
    }

    public int hashCode() {
        char[][] cArr = this.compoundName;
        if (cArr == null || cArr.length == 0) {
            return super.hashCode();
        }
        return CharOperation.hashCode(cArr[cArr.length - 1]);
    }

    public boolean hasIncompatibleSuperType(ReferenceBinding referenceBinding) {
        ReferenceBinding[] referenceBindingArr;
        ReferenceBinding[] referenceBindingArr2;
        if (TypeBinding.equalsEquals(this, referenceBinding)) {
            return false;
        }
        ReferenceBinding[] referenceBindingArr3 = null;
        ReferenceBinding referenceBindingSuperclass = this;
        int length = 0;
        do {
            TypeBinding typeBindingFindSuperTypeOriginatingFrom = referenceBinding.findSuperTypeOriginatingFrom(referenceBindingSuperclass);
            if (typeBindingFindSuperTypeOriginatingFrom != null && typeBindingFindSuperTypeOriginatingFrom.isProvablyDistinct(referenceBindingSuperclass)) {
                return true;
            }
            ReferenceBinding[] referenceBindingArrSuperInterfaces = referenceBindingSuperclass.superInterfaces();
            if (referenceBindingArrSuperInterfaces != null && referenceBindingArrSuperInterfaces != Binding.NO_SUPERINTERFACES) {
                if (referenceBindingArr3 == null) {
                    length = referenceBindingArrSuperInterfaces.length;
                    referenceBindingArr3 = referenceBindingArrSuperInterfaces;
                } else {
                    int length2 = length + referenceBindingArrSuperInterfaces.length;
                    if (length2 >= referenceBindingArr3.length) {
                        referenceBindingArr2 = new ReferenceBinding[length2 + 5];
                        System.arraycopy(referenceBindingArr3, 0, referenceBindingArr2, 0, length);
                    } else {
                        referenceBindingArr2 = referenceBindingArr3;
                    }
                    int i = length;
                    for (ReferenceBinding referenceBinding2 : referenceBindingArrSuperInterfaces) {
                        int i2 = 0;
                        while (true) {
                            if (i2 < i) {
                                if (TypeBinding.equalsEquals(referenceBinding2, referenceBindingArr2[i2])) {
                                    break;
                                }
                                i2++;
                            } else {
                                referenceBindingArr2[i] = referenceBinding2;
                                i++;
                                break;
                            }
                        }
                    }
                    referenceBindingArr3 = referenceBindingArr2;
                    length = i;
                }
            }
            referenceBindingSuperclass = referenceBindingSuperclass.superclass();
        } while (referenceBindingSuperclass != null);
        for (int i3 = 0; i3 < length; i3++) {
            ReferenceBinding referenceBinding3 = referenceBindingArr3[i3];
            if (TypeBinding.equalsEquals(referenceBinding3, referenceBinding)) {
                return false;
            }
            TypeBinding typeBindingFindSuperTypeOriginatingFrom2 = referenceBinding.findSuperTypeOriginatingFrom(referenceBinding3);
            if (typeBindingFindSuperTypeOriginatingFrom2 != null && typeBindingFindSuperTypeOriginatingFrom2.isProvablyDistinct(referenceBinding3)) {
                return true;
            }
            ReferenceBinding[] referenceBindingArrSuperInterfaces2 = referenceBinding3.superInterfaces();
            if (referenceBindingArrSuperInterfaces2 != null && referenceBindingArrSuperInterfaces2 != Binding.NO_SUPERINTERFACES) {
                int length3 = length + referenceBindingArrSuperInterfaces2.length;
                if (length3 >= referenceBindingArr3.length) {
                    referenceBindingArr = new ReferenceBinding[length3 + 5];
                    System.arraycopy(referenceBindingArr3, 0, referenceBindingArr, 0, length);
                } else {
                    referenceBindingArr = referenceBindingArr3;
                }
                int i4 = length;
                for (ReferenceBinding referenceBinding4 : referenceBindingArrSuperInterfaces2) {
                    int i5 = 0;
                    while (true) {
                        if (i5 < i4) {
                            if (TypeBinding.equalsEquals(referenceBinding4, referenceBindingArr[i5])) {
                                break;
                            }
                            i5++;
                        } else {
                            referenceBindingArr[i4] = referenceBinding4;
                            i4++;
                            break;
                        }
                    }
                }
                referenceBindingArr3 = referenceBindingArr;
                length = i4;
            }
        }
        return false;
    }

    boolean hasNonNullDefaultFor(int i, boolean z) {
        for (ReferenceBinding referenceBindingEnclosingType = this; referenceBindingEnclosingType != null; referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType()) {
            if (z) {
                int nullDefault = ((ReferenceBinding) referenceBindingEnclosingType.original()).getNullDefault();
                if (nullDefault != 0) {
                    return (i & nullDefault) != 0;
                }
            } else {
                if ((referenceBindingEnclosingType.tagBits & 144115188075855872L) != 0) {
                    return true;
                }
                if ((referenceBindingEnclosingType.tagBits & 288230376151711744L) != 0) {
                    return false;
                }
            }
        }
        if (z) {
            return (i & getPackage().defaultNullness) != 0;
        }
        return getPackage().defaultNullness == 1;
    }

    public final boolean hasRestrictedAccess() {
        return (this.modifiers & 262144) != 0;
    }

    public boolean hasNullBit(int i) {
        return (i & this.typeBits) != 0;
    }

    public boolean implementsInterface(ReferenceBinding referenceBinding, boolean z) {
        ReferenceBinding[] referenceBindingArr;
        ReferenceBinding[] referenceBindingArr2;
        if (TypeBinding.equalsEquals(this, referenceBinding)) {
            return true;
        }
        ReferenceBinding[] referenceBindingArr3 = null;
        ReferenceBinding referenceBindingSuperclass = this;
        int length = 0;
        do {
            ReferenceBinding[] referenceBindingArrSuperInterfaces = referenceBindingSuperclass.superInterfaces();
            if (referenceBindingArrSuperInterfaces != null && referenceBindingArrSuperInterfaces != Binding.NO_SUPERINTERFACES) {
                if (referenceBindingArr3 == null) {
                    length = referenceBindingArrSuperInterfaces.length;
                    referenceBindingArr3 = referenceBindingArrSuperInterfaces;
                } else {
                    int length2 = length + referenceBindingArrSuperInterfaces.length;
                    if (length2 >= referenceBindingArr3.length) {
                        referenceBindingArr2 = new ReferenceBinding[length2 + 5];
                        System.arraycopy(referenceBindingArr3, 0, referenceBindingArr2, 0, length);
                    } else {
                        referenceBindingArr2 = referenceBindingArr3;
                    }
                    int i = length;
                    for (ReferenceBinding referenceBinding2 : referenceBindingArrSuperInterfaces) {
                        int i2 = 0;
                        while (true) {
                            if (i2 < i) {
                                if (TypeBinding.equalsEquals(referenceBinding2, referenceBindingArr2[i2])) {
                                    break;
                                }
                                i2++;
                            } else {
                                referenceBindingArr2[i] = referenceBinding2;
                                i++;
                                break;
                            }
                        }
                    }
                    referenceBindingArr3 = referenceBindingArr2;
                    length = i;
                }
            }
            if (!z) {
                break;
            }
            referenceBindingSuperclass = referenceBindingSuperclass.superclass();
        } while (referenceBindingSuperclass != null);
        for (int i3 = 0; i3 < length; i3++) {
            ReferenceBinding referenceBinding3 = referenceBindingArr3[i3];
            if (referenceBinding3.isEquivalentTo(referenceBinding)) {
                return true;
            }
            ReferenceBinding[] referenceBindingArrSuperInterfaces2 = referenceBinding3.superInterfaces();
            if (referenceBindingArrSuperInterfaces2 != null && referenceBindingArrSuperInterfaces2 != Binding.NO_SUPERINTERFACES) {
                int length3 = length + referenceBindingArrSuperInterfaces2.length;
                if (length3 >= referenceBindingArr3.length) {
                    referenceBindingArr = new ReferenceBinding[length3 + 5];
                    System.arraycopy(referenceBindingArr3, 0, referenceBindingArr, 0, length);
                } else {
                    referenceBindingArr = referenceBindingArr3;
                }
                int i4 = length;
                for (ReferenceBinding referenceBinding4 : referenceBindingArrSuperInterfaces2) {
                    int i5 = 0;
                    while (true) {
                        if (i5 < i4) {
                            if (TypeBinding.equalsEquals(referenceBinding4, referenceBindingArr[i5])) {
                                break;
                            }
                            i5++;
                        } else {
                            referenceBindingArr[i4] = referenceBinding4;
                            i4++;
                            break;
                        }
                    }
                }
                referenceBindingArr3 = referenceBindingArr;
                length = i4;
            }
        }
        return false;
    }

    boolean implementsMethod(MethodBinding methodBinding) {
        char[] cArr = methodBinding.selector;
        for (ReferenceBinding referenceBindingSuperclass = this; referenceBindingSuperclass != null; referenceBindingSuperclass = referenceBindingSuperclass.superclass()) {
            MethodBinding[] methodBindingArrMethods = referenceBindingSuperclass.methods();
            long jBinarySearch = binarySearch(cArr, methodBindingArrMethods);
            if (jBinarySearch >= 0) {
                int i = (int) (jBinarySearch >> 32);
                for (int i2 = (int) jBinarySearch; i2 <= i; i2++) {
                    if (methodBindingArrMethods[i2].areParametersEqual(methodBinding)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final boolean isAbstract() {
        return (this.modifiers & 1024) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public boolean isAnnotationType() {
        return (this.modifiers & 8192) != 0;
    }

    public final boolean isBinaryBinding() {
        return (this.tagBits & 64) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isClass() {
        return (this.modifiers & 25088) == 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isProperType(boolean z) {
        ReferenceBinding referenceBindingEnclosingType = enclosingType();
        if (referenceBindingEnclosingType == null || referenceBindingEnclosingType.isProperType(z)) {
            return super.isProperType(z);
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isCompatibleWith(TypeBinding typeBinding, Scope scope) {
        if (equalsEquals(typeBinding, this) || typeBinding.f626id == 1) {
            return true;
        }
        SimpleLookupTable simpleLookupTable = this.compatibleCache;
        if (simpleLookupTable == null) {
            this.compatibleCache = new SimpleLookupTable(3);
        } else {
            Object obj = simpleLookupTable.get(typeBinding);
            if (obj != null) {
                return obj == Boolean.TRUE;
            }
        }
        this.compatibleCache.put(typeBinding, Boolean.FALSE);
        if (isCompatibleWith0(typeBinding, scope)) {
            this.compatibleCache.put(typeBinding, Boolean.TRUE);
            return true;
        }
        if (scope == null && (this instanceof TypeVariableBinding) && (((TypeVariableBinding) this).firstBound instanceof ParameterizedTypeBinding)) {
            this.compatibleCache.put(typeBinding, null);
        }
        return false;
    }

    private boolean isCompatibleWith0(TypeBinding typeBinding, Scope scope) {
        MethodScope methodScope;
        TypeBinding typeBinding2;
        if (TypeBinding.equalsEquals(typeBinding, this) || typeBinding.f626id == 1 || isEquivalentTo(typeBinding)) {
            return true;
        }
        int iKind = typeBinding.kind();
        if (iKind != 4 && iKind != 260 && iKind != 1028 && iKind != 2052) {
            if (iKind != 4100) {
                return false;
            }
            if (typeBinding.isCapture() && (typeBinding2 = ((CaptureBinding) typeBinding).lowerBound) != null) {
                if (typeBinding2.isArrayType()) {
                    return false;
                }
                return isCompatibleWith(typeBinding2);
            }
            if ((typeBinding instanceof InferenceVariable) && scope != null && (methodScope = scope.methodScope()) != null) {
                ReferenceContext referenceContext = methodScope.referenceContext;
                if ((referenceContext instanceof LambdaExpression) && ((LambdaExpression) referenceContext).inferenceContext != null) {
                    return true;
                }
            }
        }
        int iKind2 = kind();
        if ((iKind2 == 260 || iKind2 == 1028 || iKind2 == 2052) && TypeBinding.equalsEquals(erasure(), typeBinding.erasure())) {
            return false;
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) typeBinding;
        if (referenceBinding.isInterface()) {
            if (implementsInterface(referenceBinding, true)) {
                return true;
            }
            if ((this instanceof TypeVariableBinding) && scope != null) {
                TypeVariableBinding typeVariableBinding = (TypeVariableBinding) this;
                if (typeVariableBinding.firstBound instanceof ParameterizedTypeBinding) {
                    return typeVariableBinding.firstBound.capture(scope, -1, -1).isCompatibleWith(referenceBinding);
                }
            }
        }
        if (isInterface()) {
            return false;
        }
        return referenceBinding.isSuperclassOf(this);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isSubtypeOf(TypeBinding typeBinding) {
        if (isSubTypeOfRTL(typeBinding)) {
            return true;
        }
        TypeBinding typeBindingFindSuperTypeOriginatingFrom = findSuperTypeOriginatingFrom(typeBinding);
        if (typeBindingFindSuperTypeOriginatingFrom == null) {
            return false;
        }
        if (TypeBinding.equalsEquals(typeBindingFindSuperTypeOriginatingFrom, typeBinding)) {
            return true;
        }
        if (typeBinding.isRawType() && TypeBinding.equalsEquals(typeBindingFindSuperTypeOriginatingFrom.erasure(), typeBinding.erasure())) {
            return true;
        }
        TypeBinding[] typeBindingArrTypeArguments = typeBinding.typeArguments();
        TypeBinding[] typeBindingArrTypeArguments2 = typeBindingFindSuperTypeOriginatingFrom.typeArguments();
        if (typeBindingArrTypeArguments2 == null || typeBindingArrTypeArguments == null || typeBindingArrTypeArguments.length != typeBindingArrTypeArguments2.length) {
            return false;
        }
        for (int i = 0; i < typeBindingArrTypeArguments.length; i++) {
            if (!typeBindingArrTypeArguments2[i].isTypeArgumentContainedBy(typeBindingArrTypeArguments[i])) {
                return false;
            }
        }
        return true;
    }

    protected boolean isSubTypeOfRTL(TypeBinding typeBinding) {
        ReferenceBinding[] intersectingTypes;
        if (TypeBinding.equalsEquals(this, typeBinding)) {
            return true;
        }
        if (typeBinding instanceof CaptureBinding) {
            TypeBinding typeBinding2 = ((CaptureBinding) typeBinding).lowerBound;
            return typeBinding2 != null && isSubtypeOf(typeBinding2);
        }
        if (!(typeBinding instanceof ReferenceBinding) || (intersectingTypes = ((ReferenceBinding) typeBinding).getIntersectingTypes()) == null) {
            return false;
        }
        for (ReferenceBinding referenceBinding : intersectingTypes) {
            if (!isSubtypeOf(referenceBinding)) {
                return false;
            }
        }
        return true;
    }

    public final boolean isDefault() {
        return (this.modifiers & 7) == 0;
    }

    public final boolean isDeprecated() {
        return (this.modifiers & 1048576) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isEnum() {
        return (this.modifiers & 16384) != 0;
    }

    public final boolean isFinal() {
        return (this.modifiers & 16) != 0;
    }

    public boolean isHierarchyBeingConnected() {
        return (this.tagBits & 512) == 0 && (this.tagBits & 256) != 0;
    }

    public boolean isHierarchyBeingActivelyConnected() {
        return (this.tagBits & 512) == 0 && (this.tagBits & 256) != 0 && (this.tagBits & 524288) == 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isInterface() {
        return (this.modifiers & 512) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isFunctionalInterface(Scope scope) {
        MethodBinding singleAbstractMethod;
        return isInterface() && (singleAbstractMethod = getSingleAbstractMethod(scope, true)) != null && singleAbstractMethod.isValidBinding();
    }

    public final boolean isPrivate() {
        return (this.modifiers & 2) != 0;
    }

    public final boolean isOrEnclosedByPrivateType() {
        if (isLocalType()) {
            return true;
        }
        for (ReferenceBinding referenceBindingEnclosingType = this; referenceBindingEnclosingType != null; referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType()) {
            if ((referenceBindingEnclosingType.modifiers & 2) != 0) {
                return true;
            }
        }
        return false;
    }

    public final boolean isProtected() {
        return (this.modifiers & 4) != 0;
    }

    public final boolean isPublic() {
        return (this.modifiers & 1) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public final boolean isStatic() {
        return (this.modifiers & 520) != 0 || (this.tagBits & 4) == 0;
    }

    public final boolean isStrictfp() {
        return (this.modifiers & 2048) != 0;
    }

    public boolean isSuperclassOf(ReferenceBinding referenceBinding) {
        do {
            referenceBinding = referenceBinding.superclass();
            if (referenceBinding == null) {
                return false;
            }
        } while (!referenceBinding.isEquivalentTo(this));
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isThrowable() {
        ReferenceBinding referenceBindingSuperclass = this;
        do {
            int i = referenceBindingSuperclass.f626id;
            if (i == 19 || i == 21 || i == 24 || i == 25) {
                return true;
            }
            referenceBindingSuperclass = referenceBindingSuperclass.superclass();
        } while (referenceBindingSuperclass != null);
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isUncheckedException(boolean z) {
        int i;
        int i2 = this.f626id;
        if (i2 != 19) {
            if (i2 != 21) {
                if (i2 != 24) {
                    if (i2 != 25) {
                        ReferenceBinding referenceBindingSuperclass = this;
                        do {
                            referenceBindingSuperclass = referenceBindingSuperclass.superclass();
                            if (referenceBindingSuperclass == null) {
                                return false;
                            }
                            i = referenceBindingSuperclass.f626id;
                            if (i != 19) {
                                if (i == 21) {
                                    break;
                                }
                                if (i != 24) {
                                }
                            }
                            return true;
                        } while (i != 25);
                        return false;
                    }
                }
            }
            return z;
        }
        return true;
    }

    public final boolean isUsed() {
        return (this.modifiers & 134217728) != 0;
    }

    public final boolean isViewedAsDeprecated() {
        return (this.modifiers & 3145728) != 0 || getPackage().isViewedAsDeprecated();
    }

    public ReferenceBinding[] memberTypes() {
        return Binding.NO_MEMBER_TYPES;
    }

    public MethodBinding[] methods() {
        return Binding.NO_METHODS;
    }

    public final ReferenceBinding outermostEnclosingType() {
        ReferenceBinding referenceBinding = this;
        while (true) {
            ReferenceBinding referenceBindingEnclosingType = referenceBinding.enclosingType();
            if (referenceBindingEnclosingType == null) {
                return referenceBinding;
            }
            referenceBinding = referenceBindingEnclosingType;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] qualifiedSourceName() {
        if (isMemberType()) {
            return CharOperation.concat(enclosingType().qualifiedSourceName(), sourceName(), '.');
        }
        return sourceName();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] readableName() {
        char[] cArrConcatWith;
        if (isMemberType()) {
            cArrConcatWith = CharOperation.concat(enclosingType().readableName(), this.sourceName, '.');
        } else {
            cArrConcatWith = CharOperation.concatWith(this.compoundName, '.');
        }
        TypeVariableBinding[] typeVariableBindingArrTypeVariables = typeVariables();
        if (typeVariableBindingArrTypeVariables == Binding.NO_TYPE_VARIABLES) {
            return cArrConcatWith;
        }
        StringBuffer stringBuffer = new StringBuffer(10);
        stringBuffer.append(cArrConcatWith).append('<');
        int length = typeVariableBindingArrTypeVariables.length;
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                stringBuffer.append(',');
            }
            stringBuffer.append(typeVariableBindingArrTypeVariables[i].readableName());
        }
        stringBuffer.append('>');
        int length2 = stringBuffer.length();
        char[] cArr = new char[length2];
        stringBuffer.getChars(0, length2, cArr, 0);
        return cArr;
    }

    protected void appendNullAnnotation(StringBuffer stringBuffer, CompilerOptions compilerOptions) {
        if (compilerOptions.isAnnotationBasedNullAnalysisEnabled) {
            if (!compilerOptions.usesNullTypeAnnotations()) {
                if ((this.tagBits & 72057594037927936L) != 0) {
                    char[][] cArr = compilerOptions.nonNullAnnotationName;
                    stringBuffer.append('@').append(cArr[cArr.length - 1]).append(StringUtil.SPACE);
                }
                if ((this.tagBits & 36028797018963968L) != 0) {
                    char[][] cArr2 = compilerOptions.nullableAnnotationName;
                    stringBuffer.append('@').append(cArr2[cArr2.length - 1]).append(StringUtil.SPACE);
                    return;
                }
                return;
            }
            for (AnnotationBinding annotationBinding : this.typeAnnotations) {
                ReferenceBinding annotationType = annotationBinding.getAnnotationType();
                if (annotationType.hasNullBit(96)) {
                    stringBuffer.append('@').append(annotationType.shortReadableName()).append(StringUtil.SPACE);
                }
            }
        }
    }

    public AnnotationHolder retrieveAnnotationHolder(Binding binding, boolean z) {
        SimpleLookupTable simpleLookupTableStoredAnnotations = storedAnnotations(z);
        if (simpleLookupTableStoredAnnotations == null) {
            return null;
        }
        return (AnnotationHolder) simpleLookupTableStoredAnnotations.get(binding);
    }

    AnnotationBinding[] retrieveAnnotations(Binding binding) {
        AnnotationHolder annotationHolderRetrieveAnnotationHolder = retrieveAnnotationHolder(binding, true);
        return annotationHolderRetrieveAnnotationHolder == null ? Binding.NO_ANNOTATIONS : annotationHolderRetrieveAnnotationHolder.getAnnotations();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public void setAnnotations(AnnotationBinding[] annotationBindingArr) {
        storeAnnotations(this, annotationBindingArr);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] nullAnnotatedReadableName(CompilerOptions compilerOptions, boolean z) {
        if (z) {
            return nullAnnotatedShortReadableName(compilerOptions);
        }
        return nullAnnotatedReadableName(compilerOptions);
    }

    char[] nullAnnotatedReadableName(CompilerOptions compilerOptions) {
        StringBuffer stringBuffer = new StringBuffer(10);
        if (isMemberType()) {
            stringBuffer.append(enclosingType().nullAnnotatedReadableName(compilerOptions, false));
            stringBuffer.append('.');
            appendNullAnnotation(stringBuffer, compilerOptions);
            stringBuffer.append(this.sourceName);
        } else {
            char[][] cArr = this.compoundName;
            if (cArr != null) {
                int length = cArr.length;
                int i = 0;
                while (i < length - 1) {
                    stringBuffer.append(this.compoundName[i]);
                    stringBuffer.append('.');
                    i++;
                }
                appendNullAnnotation(stringBuffer, compilerOptions);
                stringBuffer.append(this.compoundName[i]);
            } else {
                appendNullAnnotation(stringBuffer, compilerOptions);
                char[] cArr2 = this.sourceName;
                if (cArr2 != null) {
                    stringBuffer.append(cArr2);
                } else {
                    stringBuffer.append(readableName());
                }
            }
        }
        TypeBinding[] typeBindingArrTypeArguments = typeArguments();
        if (typeBindingArrTypeArguments != null && typeBindingArrTypeArguments.length > 0) {
            stringBuffer.append('<');
            int length2 = typeBindingArrTypeArguments.length;
            for (int i2 = 0; i2 < length2; i2++) {
                if (i2 > 0) {
                    stringBuffer.append(',');
                }
                stringBuffer.append(typeBindingArrTypeArguments[i2].nullAnnotatedReadableName(compilerOptions, false));
            }
            stringBuffer.append('>');
        }
        int length3 = stringBuffer.length();
        char[] cArr3 = new char[length3];
        stringBuffer.getChars(0, length3, cArr3, 0);
        return cArr3;
    }

    char[] nullAnnotatedShortReadableName(CompilerOptions compilerOptions) {
        StringBuffer stringBuffer = new StringBuffer(10);
        if (isMemberType()) {
            stringBuffer.append(enclosingType().nullAnnotatedReadableName(compilerOptions, true));
            stringBuffer.append('.');
            appendNullAnnotation(stringBuffer, compilerOptions);
            stringBuffer.append(this.sourceName);
        } else {
            appendNullAnnotation(stringBuffer, compilerOptions);
            char[] cArr = this.sourceName;
            if (cArr != null) {
                stringBuffer.append(cArr);
            } else {
                stringBuffer.append(shortReadableName());
            }
        }
        TypeBinding[] typeBindingArrTypeArguments = typeArguments();
        if (typeBindingArrTypeArguments != null && typeBindingArrTypeArguments.length > 0) {
            stringBuffer.append('<');
            int length = typeBindingArrTypeArguments.length;
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    stringBuffer.append(',');
                }
                stringBuffer.append(typeBindingArrTypeArguments[i].nullAnnotatedReadableName(compilerOptions, true));
            }
            stringBuffer.append('>');
        }
        int length2 = stringBuffer.length();
        char[] cArr2 = new char[length2];
        stringBuffer.getChars(0, length2, cArr2, 0);
        return cArr2;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] shortReadableName() {
        char[] cArrConcat;
        if (isMemberType()) {
            cArrConcat = CharOperation.concat(enclosingType().shortReadableName(), this.sourceName, '.');
        } else {
            cArrConcat = this.sourceName;
        }
        TypeVariableBinding[] typeVariableBindingArrTypeVariables = typeVariables();
        if (typeVariableBindingArrTypeVariables == Binding.NO_TYPE_VARIABLES) {
            return cArrConcat;
        }
        StringBuffer stringBuffer = new StringBuffer(10);
        stringBuffer.append(cArrConcat).append('<');
        int length = typeVariableBindingArrTypeVariables.length;
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                stringBuffer.append(',');
            }
            stringBuffer.append(typeVariableBindingArrTypeVariables[i].shortReadableName());
        }
        stringBuffer.append('>');
        int length2 = stringBuffer.length();
        char[] cArr = new char[length2];
        stringBuffer.getChars(0, length2, cArr, 0);
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] signature() {
        char[] cArr = this.signature;
        if (cArr != null) {
            return cArr;
        }
        char[] cArrConcat = CharOperation.concat('L', constantPoolName(), ';');
        this.signature = cArrConcat;
        return cArrConcat;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] sourceName() {
        return this.sourceName;
    }

    void storeAnnotationHolder(Binding binding, AnnotationHolder annotationHolder) {
        if (annotationHolder == null) {
            SimpleLookupTable simpleLookupTableStoredAnnotations = storedAnnotations(false);
            if (simpleLookupTableStoredAnnotations != null) {
                simpleLookupTableStoredAnnotations.removeKey(binding);
                return;
            }
            return;
        }
        SimpleLookupTable simpleLookupTableStoredAnnotations2 = storedAnnotations(true);
        if (simpleLookupTableStoredAnnotations2 != null) {
            simpleLookupTableStoredAnnotations2.put(binding, annotationHolder);
        }
    }

    void storeAnnotations(Binding binding, AnnotationBinding[] annotationBindingArr) {
        AnnotationHolder annotationHolder;
        if (annotationBindingArr == null || annotationBindingArr.length == 0) {
            SimpleLookupTable simpleLookupTableStoredAnnotations = storedAnnotations(false);
            annotationHolder = simpleLookupTableStoredAnnotations != null ? (AnnotationHolder) simpleLookupTableStoredAnnotations.get(binding) : null;
            if (annotationHolder == null) {
                return;
            }
        } else {
            SimpleLookupTable simpleLookupTableStoredAnnotations2 = storedAnnotations(true);
            if (simpleLookupTableStoredAnnotations2 == null) {
                return;
            }
            annotationHolder = (AnnotationHolder) simpleLookupTableStoredAnnotations2.get(binding);
            if (annotationHolder == null) {
                annotationHolder = new AnnotationHolder();
            }
        }
        storeAnnotationHolder(binding, annotationHolder.setAnnotations(annotationBindingArr));
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding[] superInterfaces() {
        return Binding.NO_SUPERINTERFACES;
    }

    public ReferenceBinding[] syntheticEnclosingInstanceTypes() {
        ReferenceBinding referenceBindingEnclosingType;
        if (isStatic() || (referenceBindingEnclosingType = enclosingType()) == null) {
            return null;
        }
        return new ReferenceBinding[]{referenceBindingEnclosingType};
    }

    MethodBinding[] unResolvedMethods() {
        return methods();
    }

    public FieldBinding[] unResolvedFields() {
        return Binding.NO_FIELDS;
    }

    protected int applyCloseableClassWhitelists() {
        int length = this.compoundName.length;
        if (length == 3) {
            if (CharOperation.equals(TypeConstants.JAVA, this.compoundName[0]) && CharOperation.equals(TypeConstants.IO, this.compoundName[1])) {
                char[] cArr = this.compoundName[2];
                int length2 = TypeConstants.JAVA_IO_WRAPPER_CLOSEABLES.length;
                for (int i = 0; i < length2; i++) {
                    if (CharOperation.equals(cArr, TypeConstants.JAVA_IO_WRAPPER_CLOSEABLES[i])) {
                        return 4;
                    }
                }
                int length3 = TypeConstants.JAVA_IO_RESOURCE_FREE_CLOSEABLES.length;
                for (int i2 = 0; i2 < length3; i2++) {
                    if (CharOperation.equals(cArr, TypeConstants.JAVA_IO_RESOURCE_FREE_CLOSEABLES[i2])) {
                        return 8;
                    }
                }
            }
        } else if (length == 4 && CharOperation.equals(TypeConstants.JAVA, this.compoundName[0]) && CharOperation.equals(TypeConstants.UTIL, this.compoundName[1]) && CharOperation.equals(TypeConstants.ZIP, this.compoundName[2])) {
            char[] cArr2 = this.compoundName[3];
            int length4 = TypeConstants.JAVA_UTIL_ZIP_WRAPPER_CLOSEABLES.length;
            for (int i3 = 0; i3 < length4; i3++) {
                if (CharOperation.equals(cArr2, TypeConstants.JAVA_UTIL_ZIP_WRAPPER_CLOSEABLES[i3])) {
                    return 4;
                }
            }
        }
        int length5 = TypeConstants.OTHER_WRAPPER_CLOSEABLES.length;
        for (int i4 = 0; i4 < length5; i4++) {
            if (CharOperation.equals(this.compoundName, TypeConstants.OTHER_WRAPPER_CLOSEABLES[i4])) {
                return 4;
            }
        }
        return 0;
    }

    protected int applyCloseableInterfaceWhitelists() {
        char[][] cArr = this.compoundName;
        return (cArr.length == 4 && CharOperation.equals(cArr, TypeConstants.RESOURCE_FREE_CLOSEABLE_STREAM)) ? 8 : 0;
    }

    protected MethodBinding[] getInterfaceAbstractContracts(Scope scope, boolean z) throws InvalidInputException {
        if (!isInterface() || !isValidBinding()) {
            throw new InvalidInputException("Not a functional interface");
        }
        MethodBinding[] methodBindingArrMethods = methods();
        MethodBinding[] methodBindingArr = new MethodBinding[0];
        int i = 0;
        int i2 = 0;
        for (ReferenceBinding referenceBinding : superInterfaces()) {
            MethodBinding[] interfaceAbstractContracts = referenceBinding.getInterfaceAbstractContracts(scope, z);
            int length = interfaceAbstractContracts == null ? 0 : interfaceAbstractContracts.length;
            if (length != 0) {
                int i3 = i2 + length;
                if (i < i3) {
                    MethodBinding[] methodBindingArr2 = new MethodBinding[i3];
                    System.arraycopy(methodBindingArr, 0, methodBindingArr2, 0, i2);
                    methodBindingArr = methodBindingArr2;
                    i = i3;
                }
                System.arraycopy(interfaceAbstractContracts, 0, methodBindingArr, i2, length);
                i2 = i3;
            }
        }
        int length2 = methodBindingArrMethods == null ? 0 : methodBindingArrMethods.length;
        for (int i4 = 0; i4 < length2; i4++) {
            MethodBinding methodBinding = methodBindingArrMethods[i4];
            if (methodBinding != null && !methodBinding.isStatic() && !methodBinding.redeclaresPublicObjectMethod(scope)) {
                if (!methodBinding.isValidBinding()) {
                    throw new InvalidInputException("Not a functional interface");
                }
                int i5 = 0;
                while (i5 < i2) {
                    MethodBinding methodBinding2 = methodBindingArr[i5];
                    if (methodBinding2 == null || !MethodVerifier.doesMethodOverride(methodBinding, methodBinding2, scope.environment()) || i5 >= (i2 = i2 - 1)) {
                        i5++;
                    } else {
                        System.arraycopy(methodBindingArr, i5 + 1, methodBindingArr, i5, i2 - i5);
                    }
                }
                if (!methodBinding.isDefaultMethod()) {
                    if (i2 == i) {
                        i += 16;
                        MethodBinding[] methodBindingArr3 = new MethodBinding[i];
                        System.arraycopy(methodBindingArr, 0, methodBindingArr3, 0, i2);
                        methodBindingArr = methodBindingArr3;
                    }
                    methodBindingArr[i2] = methodBinding;
                    i2++;
                }
            }
        }
        if (i2 >= i) {
            return methodBindingArr;
        }
        MethodBinding[] methodBindingArr4 = new MethodBinding[i2];
        System.arraycopy(methodBindingArr, 0, methodBindingArr4, 0, i2);
        return methodBindingArr4;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public MethodBinding getSingleAbstractMethod(Scope scope, boolean z) {
        ReferenceBinding[] referenceBindingArr;
        ReferenceBinding[] referenceBindingArr2;
        ReferenceBinding[] referenceBindingArr3;
        int i;
        int i2;
        ReferenceBinding[] referenceBindingArr4;
        ReferenceBinding[] referenceBindingArr5;
        int i3;
        int i4;
        int i5;
        int i6 = !z ? 1 : 0;
        MethodBinding[] methodBindingArr = this.singleAbstractMethod;
        if (methodBindingArr != null) {
            MethodBinding methodBinding = methodBindingArr[i6];
            if (methodBinding != null) {
                return methodBinding;
            }
        } else {
            this.singleAbstractMethod = new MethodBinding[2];
        }
        if (this.compoundName != null) {
            scope.compilationUnitScope().recordQualifiedReference(this.compoundName);
        }
        try {
            MethodBinding[] interfaceAbstractContracts = getInterfaceAbstractContracts(scope, z);
            if (interfaceAbstractContracts != null && interfaceAbstractContracts.length != 0) {
                int length = interfaceAbstractContracts.length;
                int i7 = 0;
                char[] cArr = null;
                int i8 = 0;
                int length2 = 0;
                while (i8 < length) {
                    MethodBinding methodBinding2 = interfaceAbstractContracts[i8];
                    if (methodBinding2 != null) {
                        if (cArr == null) {
                            cArr = methodBinding2.selector;
                            length2 = methodBinding2.parameters == null ? 0 : methodBinding2.parameters.length;
                        } else {
                            if ((methodBinding2.parameters == null ? 0 : methodBinding2.parameters.length) == length2 && CharOperation.equals(methodBinding2.selector, cArr)) {
                            }
                            MethodBinding[] methodBindingArr2 = this.singleAbstractMethod;
                            ProblemMethodBinding problemMethodBinding = samProblemBinding;
                            methodBindingArr2[i6] = problemMethodBinding;
                            return problemMethodBinding;
                        }
                    }
                    i8++;
                    i7 = 0;
                }
                if (interfaceAbstractContracts.length == 1) {
                    MethodBinding[] methodBindingArr3 = this.singleAbstractMethod;
                    MethodBinding methodBinding3 = interfaceAbstractContracts[i7];
                    methodBindingArr3[i6] = methodBinding3;
                    return methodBinding3;
                }
                LookupEnvironment lookupEnvironmentEnvironment = scope.environment();
                int length3 = interfaceAbstractContracts.length;
                boolean z2 = lookupEnvironmentEnvironment.globalOptions.isAnnotationBasedNullAnalysisEnabled;
                int i9 = length3 - 1;
                int i10 = i7;
                while (i9 >= 0) {
                    MethodBinding methodBinding4 = interfaceAbstractContracts[i9];
                    if (methodBinding4.typeVariables != Binding.NO_TYPE_VARIABLES) {
                        i10 = 1;
                    }
                    TypeBinding typeBinding = methodBinding4.returnType;
                    TypeBinding[] typeBindingArrWeakerTypes = methodBinding4.parameters;
                    int i11 = i7;
                    while (true) {
                        if (i11 >= length3) {
                            ReferenceBinding[] referenceBindingArr6 = new ReferenceBinding[i7];
                            int i12 = (methodBinding4.typeVariables != Binding.NO_TYPE_VARIABLES || i10 == 0) ? i7 : 1;
                            int i13 = methodBinding4.typeVariables != Binding.NO_TYPE_VARIABLES ? 1 : i7;
                            int length4 = methodBinding4.typeVariables.length;
                            int i14 = i7;
                            int i15 = i14;
                            int i16 = i15;
                            loop3: while (true) {
                                if (i14 >= length3) {
                                    referenceBindingArr = referenceBindingArr6;
                                } else {
                                    MethodBinding methodBinding5 = interfaceAbstractContracts[i14];
                                    ReferenceBinding[] referenceBindingArr7 = methodBinding5.thrownExceptions;
                                    referenceBindingArr = referenceBindingArr6;
                                    int length5 = referenceBindingArr7 == null ? 0 : referenceBindingArr7.length;
                                    if (length5 != 0) {
                                        if (i13 != 0 && methodBinding5 != methodBinding4) {
                                            ReferenceBinding[] referenceBindingArr8 = new ReferenceBinding[length5];
                                            System.arraycopy(referenceBindingArr7, 0, referenceBindingArr8, 0, length5);
                                            for (int i17 = 0; i17 < length4; i17++) {
                                                if (referenceBindingArr8[i17] instanceof TypeVariableBinding) {
                                                    referenceBindingArr8[i17] = methodBinding4.typeVariables[i17];
                                                }
                                            }
                                            referenceBindingArr7 = referenceBindingArr8;
                                        }
                                        ReferenceBinding[] referenceBindingArr9 = referenceBindingArr;
                                        int i18 = i15;
                                        int i19 = length4;
                                        int i20 = i16;
                                        int i21 = 0;
                                        while (i21 < length5) {
                                            ReferenceBinding referenceBinding = referenceBindingArr7[i21];
                                            if (i12 != 0) {
                                                referenceBinding = (ReferenceBinding) referenceBinding.erasure();
                                            }
                                            ReferenceBinding[] referenceBindingArr10 = referenceBindingArr7;
                                            ReferenceBinding referenceBinding2 = referenceBinding;
                                            int i22 = length5;
                                            int i23 = 0;
                                            while (true) {
                                                if (i23 >= length3) {
                                                    if (i18 == i20) {
                                                        int i24 = i20 + 16;
                                                        ReferenceBinding[] referenceBindingArr11 = new ReferenceBinding[i24];
                                                        System.arraycopy(referenceBindingArr9, 0, referenceBindingArr11, 0, i18);
                                                        referenceBindingArr9 = referenceBindingArr11;
                                                        i20 = i24;
                                                    }
                                                    referenceBindingArr9[i18] = referenceBinding2;
                                                    i18++;
                                                    i = i14;
                                                    length3 = length3;
                                                    break;
                                                }
                                                if (i14 == i23) {
                                                    i2 = i20;
                                                    i = i14;
                                                    referenceBindingArr4 = referenceBindingArr9;
                                                    length3 = length3;
                                                } else {
                                                    i2 = i20;
                                                    MethodBinding methodBinding6 = interfaceAbstractContracts[i23];
                                                    i = i14;
                                                    ReferenceBinding[] referenceBindingArr12 = methodBinding6.thrownExceptions;
                                                    referenceBindingArr4 = referenceBindingArr9;
                                                    int length6 = referenceBindingArr12 == null ? 0 : referenceBindingArr12.length;
                                                    if (length6 == 0) {
                                                        i15 = i18;
                                                        i16 = i2;
                                                        referenceBindingArr2 = referenceBindingArr4;
                                                        break loop3;
                                                    }
                                                    if (i13 == 0 || methodBinding6 == methodBinding4) {
                                                        referenceBindingArr5 = referenceBindingArr12;
                                                    } else {
                                                        referenceBindingArr5 = new ReferenceBinding[length6];
                                                        System.arraycopy(referenceBindingArr12, 0, referenceBindingArr5, 0, length6);
                                                        int i25 = 0;
                                                        while (true) {
                                                            i3 = i19;
                                                            if (i25 >= i3) {
                                                                break;
                                                            }
                                                            i19 = i3;
                                                            if (referenceBindingArr5[i25] instanceof TypeVariableBinding) {
                                                                referenceBindingArr5[i25] = methodBinding4.typeVariables[i25];
                                                            }
                                                            i25++;
                                                        }
                                                        i19 = i3;
                                                    }
                                                    int i26 = 0;
                                                    while (true) {
                                                        if (i26 >= length6) {
                                                            i20 = i2;
                                                            referenceBindingArr9 = referenceBindingArr4;
                                                            break;
                                                        }
                                                        ReferenceBinding referenceBinding3 = referenceBindingArr5[i26];
                                                        if (i12 != 0) {
                                                            referenceBinding3 = (ReferenceBinding) referenceBinding3.erasure();
                                                        }
                                                        if (referenceBinding2.isCompatibleWith(referenceBinding3)) {
                                                            break;
                                                        }
                                                        i26++;
                                                    }
                                                }
                                                i23++;
                                                i20 = i2;
                                                i14 = i;
                                                referenceBindingArr9 = referenceBindingArr4;
                                                length3 = length3;
                                            }
                                            i21++;
                                            referenceBindingArr7 = referenceBindingArr10;
                                            length5 = i22;
                                            i14 = i;
                                            length3 = length3;
                                        }
                                        i14++;
                                        i16 = i20;
                                        referenceBindingArr6 = referenceBindingArr9;
                                        i15 = i18;
                                        length4 = i19;
                                    }
                                }
                                referenceBindingArr2 = referenceBindingArr;
                                break;
                            }
                            if (i15 != i16) {
                                ReferenceBinding[] referenceBindingArr13 = new ReferenceBinding[i15];
                                System.arraycopy(referenceBindingArr2, 0, referenceBindingArr13, 0, i15);
                                referenceBindingArr3 = referenceBindingArr13;
                            } else {
                                referenceBindingArr3 = referenceBindingArr2;
                            }
                            this.singleAbstractMethod[i6] = new MethodBinding(methodBinding4.modifiers | 4096, methodBinding4.selector, typeBinding, typeBindingArrWeakerTypes, referenceBindingArr3, methodBinding4.declaringClass);
                            this.singleAbstractMethod[i6].typeVariables = methodBinding4.typeVariables;
                            return this.singleAbstractMethod[i6];
                        }
                        int i27 = i10;
                        i4 = length3;
                        i5 = i9;
                        if (i5 != i11) {
                            MethodBinding methodBindingComputeSubstituteMethod = interfaceAbstractContracts[i11];
                            i10 = methodBindingComputeSubstituteMethod.typeVariables != Binding.NO_TYPE_VARIABLES ? 1 : i27;
                            if ((i10 != 0 && (methodBindingComputeSubstituteMethod = MethodVerifier.computeSubstituteMethod(methodBindingComputeSubstituteMethod, methodBinding4, lookupEnvironmentEnvironment)) == null) || !MethodVerifier.isSubstituteParameterSubsignature(methodBinding4, methodBindingComputeSubstituteMethod, lookupEnvironmentEnvironment) || !MethodVerifier.areReturnTypesCompatible(methodBinding4, methodBindingComputeSubstituteMethod, lookupEnvironmentEnvironment)) {
                                break;
                            }
                            if (z2) {
                                TypeBinding typeBindingStrongerType = NullAnnotationMatching.strongerType(typeBinding, methodBindingComputeSubstituteMethod.returnType, lookupEnvironmentEnvironment);
                                typeBindingArrWeakerTypes = NullAnnotationMatching.weakerTypes(typeBindingArrWeakerTypes, methodBindingComputeSubstituteMethod.parameters, lookupEnvironmentEnvironment);
                                typeBinding = typeBindingStrongerType;
                            }
                        } else {
                            i10 = i27;
                        }
                        i11++;
                        i9 = i5;
                        length3 = i4;
                        i7 = 0;
                    }
                    i9 = i5 - 1;
                    length3 = i4;
                    i7 = 0;
                }
                MethodBinding[] methodBindingArr4 = this.singleAbstractMethod;
                ProblemMethodBinding problemMethodBinding2 = samProblemBinding;
                methodBindingArr4[i6] = problemMethodBinding2;
                return problemMethodBinding2;
            }
            MethodBinding[] methodBindingArr5 = this.singleAbstractMethod;
            ProblemMethodBinding problemMethodBinding3 = samProblemBinding;
            methodBindingArr5[i6] = problemMethodBinding3;
            return problemMethodBinding3;
        } catch (InvalidInputException unused) {
            MethodBinding[] methodBindingArr6 = this.singleAbstractMethod;
            ProblemMethodBinding problemMethodBinding4 = samProblemBinding;
            methodBindingArr6[i6] = problemMethodBinding4;
            return problemMethodBinding4;
        }
    }

    public static boolean isConsistentIntersection(TypeBinding[] typeBindingArr) {
        int length = typeBindingArr.length;
        TypeBinding[] typeBindingArr2 = new TypeBinding[length];
        for (int i = 0; i < length; i++) {
            TypeBinding typeBindingSuperclass = typeBindingArr[i];
            if (!typeBindingSuperclass.isClass() && !typeBindingSuperclass.isArrayType()) {
                typeBindingSuperclass = typeBindingSuperclass.superclass();
            }
            typeBindingArr2[i] = typeBindingSuperclass;
        }
        TypeBinding typeBinding = typeBindingArr2[0];
        for (int i2 = 1; i2 < length; i2++) {
            TypeBinding typeBinding2 = typeBindingArr2[i2];
            if (!typeBinding2.isTypeVariable() && !typeBinding2.isWildcard() && typeBinding2.isProperType(true) && !typeBinding.isSubtypeOf(typeBinding2)) {
                if (!typeBinding2.isSubtypeOf(typeBinding)) {
                    return false;
                }
                typeBinding = typeBinding2;
            }
        }
        return true;
    }
}
