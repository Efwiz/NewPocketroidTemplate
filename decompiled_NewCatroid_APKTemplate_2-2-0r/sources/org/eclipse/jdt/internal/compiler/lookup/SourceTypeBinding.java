package org.eclipse.jdt.internal.compiler.lookup;

import java.util.HashMap;
import java.util.Iterator;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.LambdaExpression;
import org.eclipse.jdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.ReferenceExpression;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeParameter;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.util.SimpleLookupTable;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class SourceTypeBinding extends ReferenceBinding {
    private static final int CLASS_LITERAL_EMUL = 2;
    private static final int FIELD_EMUL = 1;
    private static final int MAX_SYNTHETICS = 3;
    private static final int METHOD_EMUL = 0;
    private ReferenceBinding containerAnnotationType;
    public int defaultNullness;
    LookupEnvironment environment;
    public ExternalAnnotationProvider externalAnnotationProvider;
    private FieldBinding[] fields;
    char[] genericReferenceTypeSignature;
    private int lambdaOrdinal;
    public ReferenceBinding[] memberTypes;
    private MethodBinding[] methods;
    private int nullnessDefaultInitialized;
    protected SourceTypeBinding prototype;
    public ClassScope scope;
    private SimpleLookupTable storedAnnotations;
    public ReferenceBinding[] superInterfaces;
    public ReferenceBinding superclass;
    HashMap[] synthetics;
    public TypeVariableBinding[] typeVariables;

    public SourceTypeBinding(char[][] cArr, PackageBinding packageBinding, ClassScope classScope) {
        this.storedAnnotations = null;
        this.nullnessDefaultInitialized = 0;
        this.lambdaOrdinal = 0;
        this.containerAnnotationType = null;
        this.compoundName = cArr;
        this.fPackage = packageBinding;
        this.fileName = classScope.referenceCompilationUnit().getFileName();
        this.modifiers = classScope.referenceContext.modifiers;
        this.sourceName = classScope.referenceContext.f619name;
        this.scope = classScope;
        this.environment = classScope.environment();
        this.fields = Binding.UNINITIALIZED_FIELDS;
        this.methods = Binding.UNINITIALIZED_METHODS;
        this.prototype = this;
        computeId();
    }

    public SourceTypeBinding(SourceTypeBinding sourceTypeBinding) {
        super(sourceTypeBinding);
        this.storedAnnotations = null;
        this.nullnessDefaultInitialized = 0;
        this.lambdaOrdinal = 0;
        this.containerAnnotationType = null;
        SourceTypeBinding sourceTypeBinding2 = sourceTypeBinding.prototype;
        this.prototype = sourceTypeBinding2;
        sourceTypeBinding2.tagBits |= 8388608;
        this.tagBits &= -8388609;
        this.superclass = sourceTypeBinding.superclass;
        this.superInterfaces = sourceTypeBinding.superInterfaces;
        this.fields = sourceTypeBinding.fields;
        this.methods = sourceTypeBinding.methods;
        this.memberTypes = sourceTypeBinding.memberTypes;
        this.typeVariables = sourceTypeBinding.typeVariables;
        this.environment = sourceTypeBinding.environment;
        this.synthetics = sourceTypeBinding.synthetics;
        this.genericReferenceTypeSignature = sourceTypeBinding.genericReferenceTypeSignature;
        this.storedAnnotations = sourceTypeBinding.storedAnnotations;
        this.defaultNullness = sourceTypeBinding.defaultNullness;
        this.nullnessDefaultInitialized = sourceTypeBinding.nullnessDefaultInitialized;
        this.lambdaOrdinal = sourceTypeBinding.lambdaOrdinal;
        this.containerAnnotationType = sourceTypeBinding.containerAnnotationType;
        this.tagBits |= TagBits.HasUnresolvedMemberTypes;
    }

    private void addDefaultAbstractMethods() {
        ReferenceBinding[] referenceBindingArrSuperInterfaces;
        int i;
        ReferenceBinding[] referenceBindingArr;
        int i2;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if ((this.tagBits & 1024) != 0) {
            return;
        }
        this.tagBits |= 1024;
        if (!isClass() || !isAbstract() || this.scope.compilerOptions().targetJDK >= ClassFileConstants.JDK1_2 || (referenceBindingArrSuperInterfaces = superInterfaces()) == Binding.NO_SUPERINTERFACES) {
            return;
        }
        int i3 = 0;
        MethodBinding[] methodBindingArr = null;
        ReferenceBinding[] referenceBindingArr2 = referenceBindingArrSuperInterfaces;
        int length = referenceBindingArrSuperInterfaces.length;
        int i4 = 0;
        int i5 = 0;
        while (i5 < length) {
            ReferenceBinding referenceBinding = referenceBindingArr2[i5];
            if (referenceBinding.isValidBinding()) {
                MethodBinding[] methodBindingArrMethods = referenceBinding.methods();
                int length2 = methodBindingArrMethods.length;
                int i6 = i4;
                methodBindingArr = methodBindingArr;
                while (true) {
                    int i7 = length2 - 1;
                    if (i7 < 0) {
                        break;
                    }
                    MethodBinding methodBinding = methodBindingArrMethods[i7];
                    if (implementsMethod(methodBinding)) {
                        i2 = i3;
                    } else {
                        if (i6 == 0) {
                            methodBindingArr = new MethodBinding[5];
                        } else {
                            int i8 = i3;
                            while (true) {
                                if (i8 < i6) {
                                    i2 = i3;
                                    MethodBinding methodBinding2 = methodBindingArr[i8];
                                    if (!CharOperation.equals(methodBinding2.selector, methodBinding.selector) || !methodBinding2.areParametersEqual(methodBinding)) {
                                        i8++;
                                        i3 = i2;
                                    }
                                }
                            }
                        }
                        MethodBinding[] methodBindingArr2 = methodBindingArr;
                        MethodBinding methodBinding3 = new MethodBinding(528384 | methodBinding.modifiers, methodBinding.selector, methodBinding.returnType, methodBinding.parameters, methodBinding.thrownExceptions, this);
                        if (i6 == methodBindingArr2.length) {
                            MethodBinding[] methodBindingArr3 = new MethodBinding[i6 * 2];
                            i2 = 0;
                            System.arraycopy(methodBindingArr2, 0, methodBindingArr3, 0, i6);
                            methodBindingArr = methodBindingArr3;
                        } else {
                            i2 = 0;
                            methodBindingArr = methodBindingArr2;
                        }
                        methodBindingArr[i6] = methodBinding3;
                        i6++;
                    }
                    i3 = i2;
                    length2 = i7;
                }
                ReferenceBinding[] referenceBindingArrSuperInterfaces2 = referenceBinding.superInterfaces();
                if (referenceBindingArrSuperInterfaces2 != Binding.NO_SUPERINTERFACES) {
                    int length3 = referenceBindingArrSuperInterfaces2.length;
                    int i9 = length + length3;
                    if (i9 >= referenceBindingArr2.length) {
                        referenceBindingArr = new ReferenceBinding[i9 + 5];
                        System.arraycopy(referenceBindingArr2, i3, referenceBindingArr, i3, length);
                    } else {
                        referenceBindingArr = referenceBindingArr2;
                    }
                    int i10 = length;
                    for (int i11 = i3; i11 < length3; i11++) {
                        ReferenceBinding referenceBinding2 = referenceBindingArrSuperInterfaces2[i11];
                        int i12 = i3;
                        while (true) {
                            if (i12 < i10) {
                                if (TypeBinding.equalsEquals(referenceBinding2, referenceBindingArr[i12])) {
                                    break;
                                } else {
                                    i12++;
                                }
                            } else {
                                referenceBindingArr[i10] = referenceBinding2;
                                i10++;
                                break;
                            }
                        }
                    }
                    referenceBindingArr2 = referenceBindingArr;
                    length = i10;
                }
                i = i3;
                i4 = i6;
            } else {
                i = i3;
            }
            i5++;
            i3 = i;
        }
        if (i4 > 0) {
            MethodBinding[] methodBindingArr4 = this.methods;
            int length4 = methodBindingArr4.length;
            int i13 = length4 + i4;
            System.arraycopy(methodBindingArr4, i3, setMethods(new MethodBinding[i13]), i3, length4);
            System.arraycopy(methodBindingArr, i3, this.methods, length4, i4);
            if (i13 > 1) {
                ReferenceBinding.sortMethods(this.methods, i3, i13);
            }
        }
    }

    public FieldBinding addSyntheticFieldForInnerclass(LocalVariableBinding localVariableBinding) {
        boolean z;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr[1] == null) {
            mapArr[1] = new HashMap(5);
        }
        FieldBinding syntheticFieldBinding = (FieldBinding) this.synthetics[1].get(localVariableBinding);
        if (syntheticFieldBinding == null) {
            syntheticFieldBinding = new SyntheticFieldBinding(CharOperation.concat(TypeConstants.SYNTHETIC_OUTER_LOCAL_PREFIX, localVariableBinding.f628name), localVariableBinding.type, 4114, this, Constant.NotAConstant, this.synthetics[1].size());
            this.synthetics[1].put(localVariableBinding, syntheticFieldBinding);
        }
        int i = 1;
        do {
            FieldBinding field = getField(syntheticFieldBinding.f628name, true);
            z = false;
            if (field != null) {
                FieldDeclaration[] fieldDeclarationArr = this.scope.referenceContext.fields;
                int length = fieldDeclarationArr == null ? 0 : fieldDeclarationArr.length;
                for (int i2 = 0; i2 < length; i2++) {
                    if (fieldDeclarationArr[i2].binding == field) {
                        syntheticFieldBinding.f628name = CharOperation.concat(TypeConstants.SYNTHETIC_OUTER_LOCAL_PREFIX, localVariableBinding.f628name, ("$" + String.valueOf(i)).toCharArray());
                        z = true;
                        i++;
                        break;
                    }
                }
            }
        } while (z);
        return syntheticFieldBinding;
    }

    public FieldBinding addSyntheticFieldForInnerclass(ReferenceBinding referenceBinding) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr[1] == null) {
            mapArr[1] = new HashMap(5);
        }
        FieldBinding syntheticFieldBinding = (FieldBinding) this.synthetics[1].get(referenceBinding);
        if (syntheticFieldBinding == null) {
            syntheticFieldBinding = new SyntheticFieldBinding(CharOperation.concat(TypeConstants.SYNTHETIC_ENCLOSING_INSTANCE_PREFIX, String.valueOf(referenceBinding.depth()).toCharArray()), referenceBinding, 4112, this, Constant.NotAConstant, this.synthetics[1].size());
            this.synthetics[1].put(referenceBinding, syntheticFieldBinding);
        }
        loop0: while (true) {
            FieldBinding field = getField(syntheticFieldBinding.f628name, true);
            if (field == null) {
                break;
            }
            FieldDeclaration[] fieldDeclarationArr = this.scope.referenceContext.fields;
            int length = fieldDeclarationArr == null ? 0 : fieldDeclarationArr.length;
            for (int i = 0; i < length; i++) {
                FieldDeclaration fieldDeclaration = fieldDeclarationArr[i];
                if (fieldDeclaration.binding == field) {
                    if (this.scope.compilerOptions().complianceLevel >= ClassFileConstants.JDK1_5) {
                        syntheticFieldBinding.f628name = CharOperation.concat(syntheticFieldBinding.f628name, "$".toCharArray());
                    } else {
                        this.scope.problemReporter().duplicateFieldInType(this, fieldDeclaration);
                        break;
                    }
                }
            }
            break loop0;
        }
        return syntheticFieldBinding;
    }

    public FieldBinding addSyntheticFieldForClassLiteral(TypeBinding typeBinding, BlockScope blockScope) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr[2] == null) {
            mapArr[2] = new HashMap(5);
        }
        FieldBinding syntheticFieldBinding = (FieldBinding) this.synthetics[2].get(typeBinding);
        if (syntheticFieldBinding == null) {
            syntheticFieldBinding = new SyntheticFieldBinding(CharOperation.concat(TypeConstants.SYNTHETIC_CLASS, String.valueOf(this.synthetics[2].size()).toCharArray()), blockScope.getJavaLangClass(), 4104, this, Constant.NotAConstant, this.synthetics[2].size());
            this.synthetics[2].put(typeBinding, syntheticFieldBinding);
        }
        FieldBinding field = getField(syntheticFieldBinding.f628name, true);
        if (field != null) {
            FieldDeclaration[] fieldDeclarationArr = blockScope.referenceType().fields;
            int length = fieldDeclarationArr == null ? 0 : fieldDeclarationArr.length;
            for (int i = 0; i < length; i++) {
                FieldDeclaration fieldDeclaration = fieldDeclarationArr[i];
                if (fieldDeclaration.binding == field) {
                    blockScope.problemReporter().duplicateFieldInType(this, fieldDeclaration);
                    break;
                }
            }
        }
        return syntheticFieldBinding;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FieldBinding addSyntheticFieldForAssert(BlockScope blockScope) {
        Object[] objArr;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr[1] == null) {
            mapArr[1] = new HashMap(5);
        }
        FieldBinding syntheticFieldBinding = (FieldBinding) this.synthetics[1].get("assertionEmulation");
        if (syntheticFieldBinding == null) {
            syntheticFieldBinding = new SyntheticFieldBinding(TypeConstants.SYNTHETIC_ASSERT_DISABLED, TypeBinding.BOOLEAN, (isInterface() ? 1 : 0) | 4120, this, Constant.NotAConstant, this.synthetics[1].size());
            this.synthetics[1].put("assertionEmulation", syntheticFieldBinding);
        }
        int i = 0;
        do {
            FieldBinding field = getField(syntheticFieldBinding.f628name, true);
            if (field == null) {
                objArr = false;
                break;
                break;
            }
            TypeDeclaration typeDeclaration = this.scope.referenceContext;
            int length = typeDeclaration.fields == null ? 0 : typeDeclaration.fields.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    objArr = false;
                    break;
                }
                if (typeDeclaration.fields[i2].binding == field) {
                    syntheticFieldBinding.f628name = CharOperation.concat(TypeConstants.SYNTHETIC_ASSERT_DISABLED, ("_" + String.valueOf(i)).toCharArray());
                    objArr = true;
                    i++;
                    break;
                }
                i2++;
            }
        } while (objArr != false);
        return syntheticFieldBinding;
    }

    public FieldBinding addSyntheticFieldForEnumValues() {
        boolean z;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr[1] == null) {
            mapArr[1] = new HashMap(5);
        }
        FieldBinding syntheticFieldBinding = (FieldBinding) this.synthetics[1].get("enumConstantValues");
        if (syntheticFieldBinding == null) {
            syntheticFieldBinding = new SyntheticFieldBinding(TypeConstants.SYNTHETIC_ENUM_VALUES, this.scope.createArrayType(this, 1), 4122, this, Constant.NotAConstant, this.synthetics[1].size());
            this.synthetics[1].put("enumConstantValues", syntheticFieldBinding);
        }
        int i = 0;
        do {
            FieldBinding field = getField(syntheticFieldBinding.f628name, true);
            if (field == null) {
                z = false;
                break;
            }
            FieldDeclaration[] fieldDeclarationArr = this.scope.referenceContext.fields;
            int length = fieldDeclarationArr == null ? 0 : fieldDeclarationArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    z = false;
                    break;
                    break;
                }
                if (fieldDeclarationArr[i2].binding == field) {
                    syntheticFieldBinding.f628name = CharOperation.concat(TypeConstants.SYNTHETIC_ENUM_VALUES, ("_" + String.valueOf(i)).toCharArray());
                    z = true;
                    i++;
                    break;
                }
                i2++;
            }
        } while (z);
        return syntheticFieldBinding;
    }

    public SyntheticMethodBinding addSyntheticMethod(FieldBinding fieldBinding, boolean z, boolean z2) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr[0] == null) {
            mapArr[0] = new HashMap(5);
        }
        SyntheticMethodBinding[] syntheticMethodBindingArr = (SyntheticMethodBinding[]) this.synthetics[0].get(fieldBinding);
        if (syntheticMethodBindingArr == null) {
            SyntheticMethodBinding syntheticMethodBinding = new SyntheticMethodBinding(fieldBinding, z, z2, this);
            SyntheticMethodBinding[] syntheticMethodBindingArr2 = new SyntheticMethodBinding[2];
            this.synthetics[0].put(fieldBinding, syntheticMethodBindingArr2);
            syntheticMethodBindingArr2[!z ? 1 : 0] = syntheticMethodBinding;
            return syntheticMethodBinding;
        }
        SyntheticMethodBinding syntheticMethodBinding2 = syntheticMethodBindingArr[!z ? 1 : 0];
        if (syntheticMethodBinding2 == null) {
            syntheticMethodBinding2 = new SyntheticMethodBinding(fieldBinding, z, z2, this);
            syntheticMethodBindingArr[!z ? 1 : 0] = syntheticMethodBinding2;
        }
        return syntheticMethodBinding2;
    }

    public SyntheticMethodBinding addSyntheticEnumMethod(char[] cArr) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr[0] == null) {
            mapArr[0] = new HashMap(5);
        }
        SyntheticMethodBinding[] syntheticMethodBindingArr = (SyntheticMethodBinding[]) this.synthetics[0].get(cArr);
        if (syntheticMethodBindingArr == null) {
            SyntheticMethodBinding syntheticMethodBinding = new SyntheticMethodBinding(this, cArr);
            SyntheticMethodBinding[] syntheticMethodBindingArr2 = new SyntheticMethodBinding[2];
            this.synthetics[0].put(cArr, syntheticMethodBindingArr2);
            syntheticMethodBindingArr2[0] = syntheticMethodBinding;
            return syntheticMethodBinding;
        }
        SyntheticMethodBinding syntheticMethodBinding2 = syntheticMethodBindingArr[0];
        if (syntheticMethodBinding2 == null) {
            syntheticMethodBinding2 = new SyntheticMethodBinding(this, cArr);
            syntheticMethodBindingArr[0] = syntheticMethodBinding2;
        }
        return syntheticMethodBinding2;
    }

    public SyntheticFieldBinding addSyntheticFieldForSwitchEnum(char[] cArr, String str) {
        boolean z;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr[1] == null) {
            mapArr[1] = new HashMap(5);
        }
        SyntheticFieldBinding syntheticFieldBinding = (SyntheticFieldBinding) this.synthetics[1].get(str);
        if (syntheticFieldBinding == null) {
            syntheticFieldBinding = new SyntheticFieldBinding(cArr, this.scope.createArrayType(TypeBinding.INT, 1), (isInterface() ? 17 : 2) | 4104, this, Constant.NotAConstant, this.synthetics[1].size());
            this.synthetics[1].put(str, syntheticFieldBinding);
        }
        int i = 0;
        do {
            FieldBinding field = getField(syntheticFieldBinding.f628name, true);
            if (field == null) {
                z = false;
                break;
            }
            FieldDeclaration[] fieldDeclarationArr = this.scope.referenceContext.fields;
            int length = fieldDeclarationArr == null ? 0 : fieldDeclarationArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    z = false;
                    break;
                    break;
                }
                if (fieldDeclarationArr[i2].binding == field) {
                    syntheticFieldBinding.f628name = CharOperation.concat(cArr, ("_" + String.valueOf(i)).toCharArray());
                    z = true;
                    i++;
                    break;
                }
                i2++;
            }
        } while (z);
        return syntheticFieldBinding;
    }

    public SyntheticMethodBinding addSyntheticMethodForSwitchEnum(TypeBinding typeBinding) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr[0] == null) {
            mapArr[0] = new HashMap(5);
        }
        char[] cArrConcat = CharOperation.concat(TypeConstants.SYNTHETIC_SWITCH_ENUM_TABLE, typeBinding.constantPoolName());
        CharOperation.replace(cArrConcat, '/', '$');
        String str = new String(cArrConcat);
        SyntheticMethodBinding[] syntheticMethodBindingArr = (SyntheticMethodBinding[]) this.synthetics[0].get(str);
        if (syntheticMethodBindingArr == null) {
            SyntheticMethodBinding syntheticMethodBinding = new SyntheticMethodBinding(addSyntheticFieldForSwitchEnum(cArrConcat, str), this, typeBinding, cArrConcat);
            SyntheticMethodBinding[] syntheticMethodBindingArr2 = new SyntheticMethodBinding[2];
            this.synthetics[0].put(str, syntheticMethodBindingArr2);
            syntheticMethodBindingArr2[0] = syntheticMethodBinding;
            return syntheticMethodBinding;
        }
        SyntheticMethodBinding syntheticMethodBinding2 = syntheticMethodBindingArr[0];
        if (syntheticMethodBinding2 != null) {
            return syntheticMethodBinding2;
        }
        SyntheticMethodBinding syntheticMethodBinding3 = new SyntheticMethodBinding(addSyntheticFieldForSwitchEnum(cArrConcat, str), this, typeBinding, cArrConcat);
        syntheticMethodBindingArr[0] = syntheticMethodBinding3;
        return syntheticMethodBinding3;
    }

    public SyntheticMethodBinding addSyntheticMethodForEnumInitialization(int i, int i2) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr[0] == null) {
            mapArr[0] = new HashMap(5);
        }
        SyntheticMethodBinding syntheticMethodBinding = new SyntheticMethodBinding(this, i, i2);
        SyntheticMethodBinding[] syntheticMethodBindingArr = new SyntheticMethodBinding[2];
        this.synthetics[0].put(syntheticMethodBinding.selector, syntheticMethodBindingArr);
        syntheticMethodBindingArr[0] = syntheticMethodBinding;
        return syntheticMethodBinding;
    }

    public SyntheticMethodBinding addSyntheticMethod(LambdaExpression lambdaExpression) {
        SyntheticMethodBinding syntheticMethodBinding;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr[0] == null) {
            mapArr[0] = new HashMap(5);
        }
        SyntheticMethodBinding[] syntheticMethodBindingArr = (SyntheticMethodBinding[]) this.synthetics[0].get(lambdaExpression);
        if (syntheticMethodBindingArr == null) {
            char[] cArr = TypeConstants.ANONYMOUS_METHOD;
            int i = this.lambdaOrdinal;
            this.lambdaOrdinal = i + 1;
            syntheticMethodBinding = new SyntheticMethodBinding(lambdaExpression, CharOperation.concat(cArr, Integer.toString(i).toCharArray()), this);
            this.synthetics[0].put(lambdaExpression, new SyntheticMethodBinding[]{syntheticMethodBinding});
        } else {
            syntheticMethodBinding = syntheticMethodBindingArr[0];
        }
        if (lambdaExpression.isSerializable) {
            addDeserializeLambdaMethod();
        }
        return syntheticMethodBinding;
    }

    public SyntheticMethodBinding addSyntheticMethod(ReferenceExpression referenceExpression) {
        SyntheticMethodBinding syntheticMethodBinding;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (!referenceExpression.isSerializable) {
            return null;
        }
        if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr[0] == null) {
            mapArr[0] = new HashMap(5);
        }
        SyntheticMethodBinding[] syntheticMethodBindingArr = (SyntheticMethodBinding[]) this.synthetics[0].get(referenceExpression);
        if (syntheticMethodBindingArr == null) {
            syntheticMethodBinding = new SyntheticMethodBinding(referenceExpression, this);
            this.synthetics[0].put(referenceExpression, new SyntheticMethodBinding[]{syntheticMethodBinding});
        } else {
            syntheticMethodBinding = syntheticMethodBindingArr[0];
        }
        addDeserializeLambdaMethod();
        return syntheticMethodBinding;
    }

    private void addDeserializeLambdaMethod() {
        if (((SyntheticMethodBinding[]) this.synthetics[0].get(TypeConstants.DESERIALIZE_LAMBDA)) == null) {
            this.synthetics[0].put(TypeConstants.DESERIALIZE_LAMBDA, new SyntheticMethodBinding[]{new SyntheticMethodBinding(this)});
        }
    }

    public SyntheticMethodBinding addSyntheticMethod(MethodBinding methodBinding, boolean z) {
        SyntheticMethodBinding syntheticMethodBinding;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr[0] == null) {
            mapArr[0] = new HashMap(5);
        }
        SyntheticMethodBinding[] syntheticMethodBindingArr = (SyntheticMethodBinding[]) this.synthetics[0].get(methodBinding);
        if (syntheticMethodBindingArr == null) {
            syntheticMethodBinding = new SyntheticMethodBinding(methodBinding, z, this);
            SyntheticMethodBinding[] syntheticMethodBindingArr2 = new SyntheticMethodBinding[2];
            this.synthetics[0].put(methodBinding, syntheticMethodBindingArr2);
            syntheticMethodBindingArr2[!z ? 1 : 0] = syntheticMethodBinding;
        } else {
            SyntheticMethodBinding syntheticMethodBinding2 = syntheticMethodBindingArr[!z ? 1 : 0];
            if (syntheticMethodBinding2 == null) {
                syntheticMethodBinding2 = new SyntheticMethodBinding(methodBinding, z, this);
                syntheticMethodBindingArr[!z ? 1 : 0] = syntheticMethodBinding2;
            }
            syntheticMethodBinding = syntheticMethodBinding2;
        }
        if (methodBinding.declaringClass.isStatic()) {
            if ((methodBinding.isConstructor() && methodBinding.parameters.length >= 254) || methodBinding.parameters.length >= 255) {
                this.scope.problemReporter().tooManyParametersForSyntheticMethod(methodBinding.sourceMethod());
            }
        } else if ((methodBinding.isConstructor() && methodBinding.parameters.length >= 253) || methodBinding.parameters.length >= 254) {
            this.scope.problemReporter().tooManyParametersForSyntheticMethod(methodBinding.sourceMethod());
        }
        return syntheticMethodBinding;
    }

    public SyntheticMethodBinding addSyntheticArrayMethod(ArrayBinding arrayBinding, int i) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr[0] == null) {
            mapArr[0] = new HashMap(5);
        }
        SyntheticMethodBinding[] syntheticMethodBindingArr = (SyntheticMethodBinding[]) this.synthetics[0].get(arrayBinding);
        if (syntheticMethodBindingArr == null) {
            char[] cArr = TypeConstants.ANONYMOUS_METHOD;
            int i2 = this.lambdaOrdinal;
            this.lambdaOrdinal = i2 + 1;
            SyntheticMethodBinding syntheticMethodBinding = new SyntheticMethodBinding(i, arrayBinding, CharOperation.concat(cArr, Integer.toString(i2).toCharArray()), this);
            SyntheticMethodBinding[] syntheticMethodBindingArr2 = new SyntheticMethodBinding[2];
            this.synthetics[0].put(arrayBinding, syntheticMethodBindingArr2);
            syntheticMethodBindingArr2[i != 14 ? (char) 1 : (char) 0] = syntheticMethodBinding;
            return syntheticMethodBinding;
        }
        SyntheticMethodBinding syntheticMethodBinding2 = syntheticMethodBindingArr[i == 14 ? (char) 0 : (char) 1];
        if (syntheticMethodBinding2 != null) {
            return syntheticMethodBinding2;
        }
        char[] cArr2 = TypeConstants.ANONYMOUS_METHOD;
        int i3 = this.lambdaOrdinal;
        this.lambdaOrdinal = i3 + 1;
        SyntheticMethodBinding syntheticMethodBinding3 = new SyntheticMethodBinding(i, arrayBinding, CharOperation.concat(cArr2, Integer.toString(i3).toCharArray()), this);
        syntheticMethodBindingArr[i != 14 ? (char) 1 : (char) 0] = syntheticMethodBinding3;
        return syntheticMethodBinding3;
    }

    public SyntheticMethodBinding addSyntheticFactoryMethod(MethodBinding methodBinding, MethodBinding methodBinding2, TypeBinding[] typeBindingArr) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr[0] == null) {
            mapArr[0] = new HashMap(5);
        }
        char[] cArr = TypeConstants.ANONYMOUS_METHOD;
        int i = this.lambdaOrdinal;
        this.lambdaOrdinal = i + 1;
        char[] cArrConcat = CharOperation.concat(cArr, Integer.toString(i).toCharArray());
        SyntheticMethodBinding syntheticMethodBinding = new SyntheticMethodBinding(methodBinding, methodBinding2, cArrConcat, typeBindingArr, this);
        this.synthetics[0].put(cArrConcat, new SyntheticMethodBinding[]{syntheticMethodBinding});
        return syntheticMethodBinding;
    }

    public SyntheticMethodBinding addSyntheticBridgeMethod(MethodBinding methodBinding, MethodBinding methodBinding2) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (isInterface() && this.scope.compilerOptions().sourceLevel <= ClassFileConstants.JDK1_7) {
            return null;
        }
        if (TypeBinding.equalsEquals(methodBinding.returnType.erasure(), methodBinding2.returnType.erasure()) && methodBinding.areParameterErasuresEqual(methodBinding2)) {
            return null;
        }
        if (this.synthetics == null) {
            this.synthetics = new HashMap[3];
        }
        HashMap[] mapArr = this.synthetics;
        HashMap map = mapArr[0];
        if (map == null) {
            mapArr[0] = new HashMap(5);
        } else {
            for (Object obj : map.keySet()) {
                if (obj instanceof MethodBinding) {
                    MethodBinding methodBinding3 = (MethodBinding) obj;
                    if (CharOperation.equals(methodBinding.selector, methodBinding3.selector) && TypeBinding.equalsEquals(methodBinding.returnType.erasure(), methodBinding3.returnType.erasure()) && methodBinding.areParameterErasuresEqual(methodBinding3)) {
                        return null;
                    }
                }
            }
        }
        SyntheticMethodBinding[] syntheticMethodBindingArr = (SyntheticMethodBinding[]) this.synthetics[0].get(methodBinding);
        if (syntheticMethodBindingArr == null) {
            SyntheticMethodBinding syntheticMethodBinding = new SyntheticMethodBinding(methodBinding, methodBinding2, this);
            SyntheticMethodBinding[] syntheticMethodBindingArr2 = new SyntheticMethodBinding[2];
            this.synthetics[0].put(methodBinding, syntheticMethodBindingArr2);
            syntheticMethodBindingArr2[1] = syntheticMethodBinding;
            return syntheticMethodBinding;
        }
        SyntheticMethodBinding syntheticMethodBinding2 = syntheticMethodBindingArr[1];
        if (syntheticMethodBinding2 == null) {
            syntheticMethodBinding2 = new SyntheticMethodBinding(methodBinding, methodBinding2, this);
            syntheticMethodBindingArr[1] = syntheticMethodBinding2;
        }
        return syntheticMethodBinding2;
    }

    public SyntheticMethodBinding addSyntheticBridgeMethod(MethodBinding methodBinding) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.scope.compilerOptions().complianceLevel <= ClassFileConstants.JDK1_5) {
            return null;
        }
        if ((!isInterface() || methodBinding.isDefaultMethod()) && !methodBinding.isAbstract() && !methodBinding.isFinal() && !methodBinding.isStatic()) {
            if (this.synthetics == null) {
                this.synthetics = new HashMap[3];
            }
            HashMap[] mapArr = this.synthetics;
            HashMap map = mapArr[0];
            if (map == null) {
                mapArr[0] = new HashMap(5);
            } else {
                for (Object obj : map.keySet()) {
                    if (obj instanceof MethodBinding) {
                        MethodBinding methodBinding2 = (MethodBinding) obj;
                        if (!CharOperation.equals(methodBinding.selector, methodBinding2.selector) || !TypeBinding.equalsEquals(methodBinding.returnType.erasure(), methodBinding2.returnType.erasure()) || !methodBinding.areParameterErasuresEqual(methodBinding2)) {
                        }
                    }
                }
            }
            SyntheticMethodBinding[] syntheticMethodBindingArr = (SyntheticMethodBinding[]) this.synthetics[0].get(methodBinding);
            if (syntheticMethodBindingArr == null) {
                SyntheticMethodBinding syntheticMethodBinding = new SyntheticMethodBinding(methodBinding, this);
                SyntheticMethodBinding[] syntheticMethodBindingArr2 = new SyntheticMethodBinding[2];
                this.synthetics[0].put(methodBinding, syntheticMethodBindingArr2);
                syntheticMethodBindingArr2[1] = syntheticMethodBinding;
                return syntheticMethodBinding;
            }
            SyntheticMethodBinding syntheticMethodBinding2 = syntheticMethodBindingArr[1];
            if (syntheticMethodBinding2 == null) {
                syntheticMethodBinding2 = new SyntheticMethodBinding(methodBinding, this);
                syntheticMethodBindingArr[1] = syntheticMethodBinding2;
            }
            return syntheticMethodBinding2;
        }
        return null;
    }

    boolean areFieldsInitialized() {
        if (isPrototype()) {
            return this.fields != Binding.UNINITIALIZED_FIELDS;
        }
        return this.prototype.areFieldsInitialized();
    }

    boolean areMethodsInitialized() {
        if (isPrototype()) {
            return this.methods != Binding.UNINITIALIZED_METHODS;
        }
        return this.prototype.areMethodsInitialized();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public int kind() {
        if (!isPrototype()) {
            return this.prototype.kind();
        }
        if (this.typeVariables != Binding.NO_TYPE_VARIABLES) {
            return Binding.GENERIC_TYPE;
        }
        return 4;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding clone(TypeBinding typeBinding) {
        return new SourceTypeBinding(this);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] computeUniqueKey(boolean z) {
        int iLastIndexOf;
        if (!isPrototype()) {
            return this.prototype.computeUniqueKey();
        }
        char[] cArrComputeUniqueKey = super.computeUniqueKey(z);
        if (cArrComputeUniqueKey.length != 2 && !Util.isClassFileName(this.fileName) && (iLastIndexOf = CharOperation.lastIndexOf('.', this.fileName)) != -1) {
            char[] cArrSubarray = CharOperation.subarray(this.fileName, CharOperation.lastIndexOf('/', this.fileName) + 1, iLastIndexOf);
            int iLastIndexOf2 = CharOperation.lastIndexOf('/', cArrComputeUniqueKey) + 1;
            int i = iLastIndexOf2 != 0 ? iLastIndexOf2 : 1;
            int iIndexOf = isMemberType() ? CharOperation.indexOf('$', cArrComputeUniqueKey, i) : -1;
            if (iIndexOf == -1) {
                iIndexOf = CharOperation.indexOf('<', cArrComputeUniqueKey, i);
            }
            if (iIndexOf == -1) {
                iIndexOf = CharOperation.indexOf(';', cArrComputeUniqueKey, i);
            }
            char[] cArrSubarray2 = CharOperation.subarray(cArrComputeUniqueKey, i, iIndexOf);
            if (!CharOperation.equals(cArrSubarray2, cArrSubarray)) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(cArrComputeUniqueKey, 0, i);
                stringBuffer.append(cArrSubarray);
                stringBuffer.append('~');
                stringBuffer.append(cArrSubarray2);
                stringBuffer.append(cArrComputeUniqueKey, iIndexOf, cArrComputeUniqueKey.length - iIndexOf);
                int length = stringBuffer.length();
                char[] cArr = new char[length];
                stringBuffer.getChars(0, length, cArr, 0);
                return cArr;
            }
        }
        return cArrComputeUniqueKey;
    }

    private void checkAnnotationsInType() {
        getAnnotationTagBits();
        ReferenceBinding referenceBindingEnclosingType = enclosingType();
        if (referenceBindingEnclosingType != null && referenceBindingEnclosingType.isViewedAsDeprecated() && !isDeprecated()) {
            this.modifiers |= 2097152;
        }
        int length = this.memberTypes.length;
        for (int i = 0; i < length; i++) {
            ((SourceTypeBinding) this.memberTypes[i]).checkAnnotationsInType();
        }
    }

    void faultInTypesForFieldsAndMethods() throws Throwable {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        checkAnnotationsInType();
        internalFaultInTypeForFieldsAndMethods();
    }

    private void internalFaultInTypeForFieldsAndMethods() throws Throwable {
        fields();
        methods();
        int length = this.memberTypes.length;
        for (int i = 0; i < length; i++) {
            ((SourceTypeBinding) this.memberTypes[i]).internalFaultInTypeForFieldsAndMethods();
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public FieldBinding[] fields() {
        int i;
        if (!isPrototype()) {
            if ((this.tagBits & 8192) != 0) {
                return this.fields;
            }
            this.tagBits |= 8192;
            FieldBinding[] fieldBindingArrFields = this.prototype.fields();
            this.fields = fieldBindingArrFields;
            return fieldBindingArrFields;
        }
        if ((this.tagBits & 8192) != 0) {
            return this.fields;
        }
        FieldBinding[] fieldBindingArr = this.fields;
        int i2 = 0;
        try {
            if ((this.tagBits & 4096) == 0) {
                FieldBinding[] fieldBindingArr2 = this.fields;
                int length = fieldBindingArr2.length;
                if (length > 1) {
                    ReferenceBinding.sortFields(fieldBindingArr2, 0, length);
                }
                this.tagBits |= 4096;
            }
            FieldBinding[] fieldBindingArr3 = this.fields;
            int length2 = fieldBindingArr3.length;
            i = 0;
            for (int i3 = 0; i3 < length2; i3++) {
                try {
                    if (resolveTypeFor(fieldBindingArr3[i3]) == null) {
                        if (fieldBindingArr == fieldBindingArr3) {
                            fieldBindingArr = new FieldBinding[length2];
                            System.arraycopy(fieldBindingArr3, 0, fieldBindingArr, 0, length2);
                        }
                        fieldBindingArr[i3] = null;
                        i++;
                    }
                } catch (Throwable th) {
                    th = th;
                    if (i > 0) {
                        int length3 = fieldBindingArr.length - i;
                        if (length3 == 0) {
                            return setFields(Binding.NO_FIELDS);
                        }
                        FieldBinding[] fieldBindingArr4 = new FieldBinding[length3];
                        int length4 = fieldBindingArr.length;
                        int i4 = 0;
                        while (i2 < length4) {
                            FieldBinding fieldBinding = fieldBindingArr[i2];
                            if (fieldBinding != null) {
                                fieldBindingArr4[i4] = fieldBinding;
                                i4++;
                            }
                            i2++;
                        }
                        setFields(fieldBindingArr4);
                    }
                    throw th;
                }
            }
            if (i > 0) {
                int length5 = fieldBindingArr.length - i;
                if (length5 == 0) {
                    return setFields(Binding.NO_FIELDS);
                }
                FieldBinding[] fieldBindingArr5 = new FieldBinding[length5];
                int length6 = fieldBindingArr.length;
                int i5 = 0;
                while (i2 < length6) {
                    FieldBinding fieldBinding2 = fieldBindingArr[i2];
                    if (fieldBinding2 != null) {
                        fieldBindingArr5[i5] = fieldBinding2;
                        i5++;
                    }
                    i2++;
                }
                setFields(fieldBindingArr5);
            }
            this.tagBits |= 8192;
            return this.fields;
        } catch (Throwable th2) {
            th = th2;
            i = 0;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public char[] genericTypeSignature() {
        if (!isPrototype()) {
            return this.prototype.genericTypeSignature();
        }
        if (this.genericReferenceTypeSignature == null) {
            this.genericReferenceTypeSignature = computeGenericTypeSignature(this.typeVariables);
        }
        return this.genericReferenceTypeSignature;
    }

    public char[] genericSignature() {
        StringBuffer stringBuffer;
        if (!isPrototype()) {
            return this.prototype.genericSignature();
        }
        if (this.typeVariables != Binding.NO_TYPE_VARIABLES) {
            stringBuffer = new StringBuffer(10);
            stringBuffer.append('<');
            int length = this.typeVariables.length;
            for (int i = 0; i < length; i++) {
                stringBuffer.append(this.typeVariables[i].genericSignature());
            }
            stringBuffer.append('>');
        } else {
            ReferenceBinding referenceBinding = this.superclass;
            if (referenceBinding == null || !referenceBinding.isParameterizedType()) {
                int length2 = this.superInterfaces.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    if (!this.superInterfaces[i2].isParameterizedType()) {
                    }
                }
                return null;
            }
            stringBuffer = new StringBuffer(10);
        }
        StringBuffer stringBuffer2 = stringBuffer;
        ReferenceBinding referenceBinding2 = this.superclass;
        if (referenceBinding2 != null) {
            stringBuffer2.append(referenceBinding2.genericTypeSignature());
        } else {
            stringBuffer2.append(this.scope.getJavaLangObject().genericTypeSignature());
        }
        int length3 = this.superInterfaces.length;
        for (int i3 = 0; i3 < length3; i3++) {
            stringBuffer2.append(this.superInterfaces[i3].genericTypeSignature());
        }
        return stringBuffer2.toString().toCharArray();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public long getAnnotationTagBits() {
        ClassScope classScope;
        if (!isPrototype()) {
            return this.prototype.getAnnotationTagBits();
        }
        if ((this.tagBits & 8589934592L) == 0 && (classScope = this.scope) != null) {
            TypeDeclaration typeDeclaration = classScope.referenceContext;
            boolean z = typeDeclaration.staticInitializerScope.insideTypeAnnotation;
            try {
                typeDeclaration.staticInitializerScope.insideTypeAnnotation = true;
                ASTNode.resolveAnnotations(typeDeclaration.staticInitializerScope, typeDeclaration.annotations, this);
                typeDeclaration.staticInitializerScope.insideTypeAnnotation = z;
                if ((this.tagBits & 70368744177664L) != 0) {
                    this.modifiers |= 1048576;
                }
            } catch (Throwable th) {
                typeDeclaration.staticInitializerScope.insideTypeAnnotation = z;
                throw th;
            }
        }
        return this.tagBits;
    }

    public MethodBinding[] getDefaultAbstractMethods() {
        if (!isPrototype()) {
            return this.prototype.getDefaultAbstractMethods();
        }
        int length = this.methods.length;
        int i = 0;
        int i2 = 0;
        while (true) {
            length--;
            if (length < 0) {
                break;
            }
            if (this.methods[length].isDefaultAbstract()) {
                i2++;
            }
        }
        if (i2 == 0) {
            return Binding.NO_METHODS;
        }
        MethodBinding[] methodBindingArr = new MethodBinding[i2];
        int length2 = this.methods.length;
        while (true) {
            length2--;
            if (length2 < 0) {
                return methodBindingArr;
            }
            if (this.methods[length2].isDefaultAbstract()) {
                methodBindingArr[i] = this.methods[length2];
                i++;
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public MethodBinding getExactConstructor(TypeBinding[] typeBindingArr) throws Throwable {
        if (!isPrototype()) {
            return this.prototype.getExactConstructor(typeBindingArr);
        }
        int length = typeBindingArr.length;
        if ((this.tagBits & 32768) != 0) {
            long jBinarySearch = ReferenceBinding.binarySearch(TypeConstants.INIT, this.methods);
            if (jBinarySearch < 0) {
                return null;
            }
            int i = (int) (jBinarySearch >> 32);
            for (int i2 = (int) jBinarySearch; i2 <= i; i2++) {
                MethodBinding methodBinding = this.methods[i2];
                if (methodBinding.parameters.length == length) {
                    TypeBinding[] typeBindingArr2 = methodBinding.parameters;
                    for (int i3 = 0; i3 < length; i3++) {
                        if (!TypeBinding.notEquals(typeBindingArr2[i3], typeBindingArr[i3])) {
                        }
                    }
                    return methodBinding;
                }
            }
            return null;
        }
        if ((this.tagBits & 16384) == 0) {
            MethodBinding[] methodBindingArr = this.methods;
            int length2 = methodBindingArr.length;
            if (length2 > 1) {
                ReferenceBinding.sortMethods(methodBindingArr, 0, length2);
            }
            this.tagBits |= 16384;
        }
        long jBinarySearch2 = ReferenceBinding.binarySearch(TypeConstants.INIT, this.methods);
        if (jBinarySearch2 < 0) {
            return null;
        }
        int i4 = (int) (jBinarySearch2 >> 32);
        for (int i5 = (int) jBinarySearch2; i5 <= i4; i5++) {
            MethodBinding methodBinding2 = this.methods[i5];
            if (resolveTypesFor(methodBinding2) == null || methodBinding2.returnType == null) {
                methods();
                return getExactConstructor(typeBindingArr);
            }
            if (methodBinding2.parameters.length == length) {
                TypeBinding[] typeBindingArr3 = methodBinding2.parameters;
                for (int i6 = 0; i6 < length; i6++) {
                    if (!TypeBinding.notEquals(typeBindingArr3[i6], typeBindingArr[i6])) {
                    }
                }
                return methodBinding2;
            }
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:102:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:103:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:72:0x00f2  */
    /* JADX WARN: Code duplicated, block: B:74:0x00f8  */
    /* JADX WARN: Code duplicated, block: B:76:0x00fd A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:77:0x00ff  */
    /* JADX WARN: Code duplicated, block: B:80:0x010d  */
    /* JADX WARN: Code duplicated, block: B:82:0x0111 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:83:0x0113  */
    /* JADX WARN: Code duplicated, block: B:86:0x011d A[ORIG_RETURN, RETURN] */
    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public MethodBinding getExactMethod(char[] cArr, TypeBinding[] typeBindingArr, CompilationUnitScope compilationUnitScope) throws Throwable {
        boolean zAreParametersEqual;
        boolean z;
        ReferenceBinding referenceBinding;
        ReferenceBinding[] referenceBindingArr;
        if (!isPrototype()) {
            return this.prototype.getExactMethod(cArr, typeBindingArr, compilationUnitScope);
        }
        int length = typeBindingArr.length;
        if ((this.tagBits & 32768) != 0) {
            long jBinarySearch = ReferenceBinding.binarySearch(cArr, this.methods);
            if (jBinarySearch >= 0) {
                int i = (int) jBinarySearch;
                int i2 = (int) (jBinarySearch >> 32);
                z = true;
                while (i <= i2) {
                    MethodBinding methodBinding = this.methods[i];
                    if (methodBinding.parameters.length == length) {
                        TypeBinding[] typeBindingArr2 = methodBinding.parameters;
                        for (int i3 = 0; i3 < length; i3++) {
                            if (!TypeBinding.notEquals(typeBindingArr2[i3], typeBindingArr[i3])) {
                            }
                        }
                        return methodBinding;
                    }
                    i++;
                    z = false;
                }
            }
            if (z) {
                return null;
            }
            if (isInterface()) {
                referenceBindingArr = this.superInterfaces;
                if (referenceBindingArr.length == 1) {
                    return null;
                }
                if (compilationUnitScope != null) {
                    compilationUnitScope.recordTypeReference(referenceBindingArr[0]);
                }
                return this.superInterfaces[0].getExactMethod(cArr, typeBindingArr, compilationUnitScope);
            }
            referenceBinding = this.superclass;
            if (referenceBinding != null) {
                return null;
            }
            if (compilationUnitScope != null) {
                compilationUnitScope.recordTypeReference(referenceBinding);
            }
            return this.superclass.getExactMethod(cArr, typeBindingArr, compilationUnitScope);
        }
        if ((this.tagBits & 16384) == 0) {
            MethodBinding[] methodBindingArr = this.methods;
            int length2 = methodBindingArr.length;
            if (length2 > 1) {
                ReferenceBinding.sortMethods(methodBindingArr, 0, length2);
            }
            this.tagBits |= 16384;
        }
        long jBinarySearch2 = ReferenceBinding.binarySearch(cArr, this.methods);
        if (jBinarySearch2 >= 0) {
            int i4 = (int) jBinarySearch2;
            int i5 = (int) (jBinarySearch2 >> 32);
            for (int i6 = i4; i6 <= i5; i6++) {
                MethodBinding methodBinding2 = this.methods[i6];
                if (resolveTypesFor(methodBinding2) == null || methodBinding2.returnType == null) {
                    methods();
                    return getExactMethod(cArr, typeBindingArr, compilationUnitScope);
                }
            }
            boolean z2 = this.scope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_5;
            for (int i7 = i4; i7 <= i5; i7++) {
                MethodBinding methodBinding3 = this.methods[i7];
                for (int i8 = i5; i8 > i7; i8--) {
                    MethodBinding methodBinding4 = this.methods[i8];
                    if (z2) {
                        zAreParametersEqual = methodBinding3.areParameterErasuresEqual(methodBinding4);
                    } else {
                        zAreParametersEqual = methodBinding3.areParametersEqual(methodBinding4);
                    }
                    if (zAreParametersEqual) {
                        methods();
                        return getExactMethod(cArr, typeBindingArr, compilationUnitScope);
                    }
                }
            }
            while (i4 <= i5) {
                MethodBinding methodBinding5 = this.methods[i4];
                TypeBinding[] typeBindingArr3 = methodBinding5.parameters;
                if (typeBindingArr3.length == length) {
                    for (int i9 = 0; i9 < length; i9++) {
                        if (!TypeBinding.notEquals(typeBindingArr3[i9], typeBindingArr[i9])) {
                        }
                    }
                    return methodBinding5;
                }
                i4++;
            }
        }
        z = true;
        if (z) {
            return null;
        }
        if (isInterface()) {
            referenceBindingArr = this.superInterfaces;
            if (referenceBindingArr.length == 1) {
                return null;
            }
            if (compilationUnitScope != null) {
                compilationUnitScope.recordTypeReference(referenceBindingArr[0]);
            }
            return this.superInterfaces[0].getExactMethod(cArr, typeBindingArr, compilationUnitScope);
        }
        referenceBinding = this.superclass;
        if (referenceBinding != null) {
            return null;
        }
        if (compilationUnitScope != null) {
            compilationUnitScope.recordTypeReference(referenceBinding);
        }
        return this.superclass.getExactMethod(cArr, typeBindingArr, compilationUnitScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public FieldBinding getField(char[] cArr, boolean z) {
        if (!isPrototype()) {
            return this.prototype.getField(cArr, z);
        }
        if ((this.tagBits & 8192) != 0) {
            return ReferenceBinding.binarySearch(cArr, this.fields);
        }
        int i = 0;
        if ((this.tagBits & 4096) == 0) {
            FieldBinding[] fieldBindingArr = this.fields;
            int length = fieldBindingArr.length;
            if (length > 1) {
                ReferenceBinding.sortFields(fieldBindingArr, 0, length);
            }
            this.tagBits |= 4096;
        }
        FieldBinding fieldBindingBinarySearch = ReferenceBinding.binarySearch(cArr, this.fields);
        if (fieldBindingBinarySearch == null) {
            return null;
        }
        try {
            FieldBinding fieldBindingResolveTypeFor = resolveTypeFor(fieldBindingBinarySearch);
            if (fieldBindingResolveTypeFor == null) {
                FieldBinding[] fieldBindingArr2 = this.fields;
                int length2 = fieldBindingArr2.length - 1;
                if (length2 == 0) {
                    setFields(Binding.NO_FIELDS);
                } else {
                    FieldBinding[] fieldBindingArr3 = new FieldBinding[length2];
                    int length3 = fieldBindingArr2.length;
                    int i2 = 0;
                    while (i < length3) {
                        FieldBinding fieldBinding = this.fields[i];
                        if (fieldBinding != fieldBindingBinarySearch) {
                            fieldBindingArr3[i2] = fieldBinding;
                            i2++;
                        }
                        i++;
                    }
                    setFields(fieldBindingArr3);
                }
            }
            return fieldBindingResolveTypeFor;
        } catch (Throwable th) {
            int length4 = this.fields.length - 1;
            if (length4 == 0) {
                setFields(Binding.NO_FIELDS);
            } else {
                FieldBinding[] fieldBindingArr4 = new FieldBinding[length4];
                int length5 = this.fields.length;
                int i3 = 0;
                while (i < length5) {
                    FieldBinding fieldBinding2 = this.fields[i];
                    if (fieldBinding2 != fieldBindingBinarySearch) {
                        fieldBindingArr4[i3] = fieldBinding2;
                        i3++;
                    }
                    i++;
                }
                setFields(fieldBindingArr4);
            }
            throw th;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public MethodBinding[] getMethods(char[] cArr) throws Throwable {
        boolean zAreParametersEqual;
        if (!isPrototype()) {
            return this.prototype.getMethods(cArr);
        }
        if ((this.tagBits & 32768) != 0) {
            long jBinarySearch = ReferenceBinding.binarySearch(cArr, this.methods);
            if (jBinarySearch >= 0) {
                int i = (int) jBinarySearch;
                int i2 = (((int) (jBinarySearch >> 32)) - i) + 1;
                MethodBinding[] methodBindingArr = new MethodBinding[i2];
                System.arraycopy(this.methods, i, methodBindingArr, 0, i2);
                return methodBindingArr;
            }
            return Binding.NO_METHODS;
        }
        if ((this.tagBits & 16384) == 0) {
            MethodBinding[] methodBindingArr2 = this.methods;
            int length = methodBindingArr2.length;
            if (length > 1) {
                ReferenceBinding.sortMethods(methodBindingArr2, 0, length);
            }
            this.tagBits |= 16384;
        }
        long jBinarySearch2 = ReferenceBinding.binarySearch(cArr, this.methods);
        if (jBinarySearch2 >= 0) {
            int i3 = (int) jBinarySearch2;
            int i4 = (int) (jBinarySearch2 >> 32);
            for (int i5 = i3; i5 <= i4; i5++) {
                MethodBinding methodBinding = this.methods[i5];
                if (resolveTypesFor(methodBinding) == null || methodBinding.returnType == null) {
                    methods();
                    return getMethods(cArr);
                }
            }
            int i6 = i4 - i3;
            int i7 = i6 + 1;
            MethodBinding[] methodBindingArr3 = new MethodBinding[i7];
            System.arraycopy(this.methods, i3, methodBindingArr3, 0, i7);
            boolean z = this.scope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_5;
            for (int i8 = 0; i8 < i6; i8++) {
                MethodBinding methodBinding2 = methodBindingArr3[i8];
                for (int i9 = i6; i9 > i8; i9--) {
                    if (z) {
                        zAreParametersEqual = methodBinding2.areParameterErasuresEqual(methodBindingArr3[i9]);
                    } else {
                        zAreParametersEqual = methodBinding2.areParametersEqual(methodBindingArr3[i9]);
                    }
                    if (zAreParametersEqual) {
                        methods();
                        return getMethods(cArr);
                    }
                }
            }
            return methodBindingArr3;
        }
        return Binding.NO_METHODS;
    }

    public FieldBinding getSyntheticField(LocalVariableBinding localVariableBinding) {
        HashMap map;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr == null || (map = mapArr[1]) == null) {
            return null;
        }
        return (FieldBinding) map.get(localVariableBinding);
    }

    public FieldBinding getSyntheticField(ReferenceBinding referenceBinding, boolean z) {
        HashMap map;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr != null && (map = mapArr[1]) != null) {
            FieldBinding fieldBinding = (FieldBinding) map.get(referenceBinding);
            if (fieldBinding != null) {
                return fieldBinding;
            }
            if (!z) {
                for (FieldBinding fieldBinding2 : this.synthetics[1].values()) {
                    if (CharOperation.prefixEquals(TypeConstants.SYNTHETIC_ENCLOSING_INSTANCE_PREFIX, fieldBinding2.f628name) && fieldBinding2.type.findSuperTypeOriginatingFrom(referenceBinding) != null) {
                        return fieldBinding2;
                    }
                }
            }
        }
        return null;
    }

    public SyntheticMethodBinding getSyntheticBridgeMethod(MethodBinding methodBinding) {
        HashMap map;
        SyntheticMethodBinding[] syntheticMethodBindingArr;
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr == null || (map = mapArr[0]) == null || (syntheticMethodBindingArr = (SyntheticMethodBinding[]) map.get(methodBinding)) == null) {
            return null;
        }
        return syntheticMethodBindingArr[1];
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean hasTypeBit(int i) {
        if (isPrototype()) {
            return (i & this.typeBits) != 0;
        }
        return this.prototype.hasTypeBit(i);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public void initializeDeprecatedAnnotationTagBits() {
        if (!isPrototype()) {
            this.prototype.initializeDeprecatedAnnotationTagBits();
            return;
        }
        if ((this.tagBits & 17179869184L) == 0) {
            TypeDeclaration typeDeclaration = this.scope.referenceContext;
            boolean z = typeDeclaration.staticInitializerScope.insideTypeAnnotation;
            try {
                typeDeclaration.staticInitializerScope.insideTypeAnnotation = true;
                ASTNode.resolveDeprecatedAnnotations(typeDeclaration.staticInitializerScope, typeDeclaration.annotations, this);
                this.tagBits = 17179869184L | this.tagBits;
                typeDeclaration.staticInitializerScope.insideTypeAnnotation = z;
                if ((this.tagBits & 70368744177664L) != 0) {
                    this.modifiers |= 1048576;
                }
            } catch (Throwable th) {
                typeDeclaration.staticInitializerScope.insideTypeAnnotation = z;
                throw th;
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    void initializeForStaticImports() {
        if (!isPrototype()) {
            this.prototype.initializeForStaticImports();
            return;
        }
        ClassScope classScope = this.scope;
        if (classScope == null) {
            return;
        }
        if (this.superInterfaces == null) {
            classScope.connectTypeHierarchy();
        }
        this.scope.buildFields();
        this.scope.buildMethods();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    int getNullDefault() {
        if (!isPrototype()) {
            return this.prototype.getNullDefault();
        }
        int i = this.nullnessDefaultInitialized;
        if (i == 0) {
            getAnnotationTagBits();
        } else {
            if (i == 1) {
            }
            return this.defaultNullness;
        }
        getPackage().isViewedAsDeprecated();
        this.nullnessDefaultInitialized = 2;
        return this.defaultNullness;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isEquivalentTo(TypeBinding typeBinding) {
        ReferenceBinding referenceBindingEnclosingType;
        if (!isPrototype()) {
            return this.prototype.isEquivalentTo(typeBinding);
        }
        if (TypeBinding.equalsEquals(this, typeBinding)) {
            return true;
        }
        if (typeBinding == null) {
            return false;
        }
        int iKind = typeBinding.kind();
        if (iKind != 260) {
            if (iKind != 516) {
                if (iKind == 1028) {
                    return TypeBinding.equalsEquals(typeBinding.erasure(), this);
                }
                if (iKind != 8196) {
                    return false;
                }
            }
            return ((WildcardBinding) typeBinding).boundCheck(this);
        }
        if ((typeBinding.tagBits & 1073741824) == 0 && (!isMemberType() || !typeBinding.isMemberType())) {
            return false;
        }
        ParameterizedTypeBinding parameterizedTypeBinding = (ParameterizedTypeBinding) typeBinding;
        if (TypeBinding.notEquals(this, parameterizedTypeBinding.genericType())) {
            return false;
        }
        if (!isStatic() && (referenceBindingEnclosingType = enclosingType()) != null) {
            ReferenceBinding referenceBindingEnclosingType2 = parameterizedTypeBinding.enclosingType();
            if (referenceBindingEnclosingType2 == null) {
                return false;
            }
            if ((1073741824 & referenceBindingEnclosingType2.tagBits) == 0) {
                if (TypeBinding.notEquals(referenceBindingEnclosingType, referenceBindingEnclosingType2)) {
                    return false;
                }
            } else if (!referenceBindingEnclosingType.isEquivalentTo(parameterizedTypeBinding.enclosingType())) {
                return false;
            }
        }
        TypeVariableBinding[] typeVariableBindingArr = this.typeVariables;
        int length = typeVariableBindingArr == null ? 0 : typeVariableBindingArr.length;
        TypeBinding[] typeBindingArr = parameterizedTypeBinding.arguments;
        if ((typeBindingArr == null ? 0 : typeBindingArr.length) != length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (!this.typeVariables[i].isTypeArgumentContainedBy(typeBindingArr[i])) {
                return false;
            }
        }
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isGenericType() {
        if (isPrototype()) {
            return this.typeVariables != Binding.NO_TYPE_VARIABLES;
        }
        return this.prototype.isGenericType();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public boolean isHierarchyConnected() {
        if (isPrototype()) {
            return (this.tagBits & 512) != 0;
        }
        return this.prototype.isHierarchyConnected();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public boolean isRepeatableAnnotationType() {
        if (isPrototype()) {
            return this.containerAnnotationType != null;
        }
        throw new IllegalStateException();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public boolean isTaggedRepeatable() {
        return (this.tagBits & 1152921504606846976L) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public ReferenceBinding[] memberTypes() {
        if (!isPrototype()) {
            if ((this.tagBits & TagBits.HasUnresolvedMemberTypes) == 0) {
                return this.memberTypes;
            }
            ReferenceBinding[] referenceBindingArrMemberTypes = this.prototype.memberTypes();
            this.memberTypes = referenceBindingArrMemberTypes;
            int length = referenceBindingArrMemberTypes == null ? 0 : referenceBindingArrMemberTypes.length;
            this.memberTypes = new ReferenceBinding[length];
            for (int i = 0; i < length; i++) {
                this.memberTypes[i] = this.environment.createMemberType(referenceBindingArrMemberTypes[i], this);
            }
            this.tagBits &= -268435457;
        }
        return this.memberTypes;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public boolean hasMemberTypes() {
        if (isPrototype()) {
            return this.memberTypes.length > 0;
        }
        return this.prototype.hasMemberTypes();
    }

    /* JADX WARN: Code duplicated, block: B:105:0x015c A[Catch: all -> 0x0326, TryCatch #1 {all -> 0x0326, blocks: (B:28:0x005a, B:32:0x006c, B:36:0x007e, B:56:0x00bb, B:59:0x00c0, B:62:0x00cc, B:193:0x02a7, B:65:0x00d6, B:83:0x010a, B:86:0x0112, B:88:0x0116, B:90:0x011a, B:92:0x012c, B:97:0x0140, B:102:0x0154, B:150:0x0202, B:152:0x0208, B:154:0x0210, B:162:0x0226, B:164:0x022c, B:166:0x0230, B:168:0x0234, B:173:0x023d, B:176:0x0257, B:178:0x025e, B:179:0x0264, B:174:0x0248, B:181:0x026f, B:183:0x0275, B:186:0x027b, B:189:0x0295, B:191:0x029c, B:192:0x02a2, B:187:0x0286, B:105:0x015c, B:107:0x0160, B:112:0x0169, B:131:0x01b8, B:134:0x01bd, B:136:0x01cb, B:138:0x01d1, B:115:0x016e, B:117:0x017c, B:119:0x0182, B:122:0x0197, B:124:0x01a1, B:126:0x01ab, B:146:0x01f2, B:194:0x02b3, B:196:0x02bf, B:214:0x02fc, B:216:0x0306, B:218:0x030a), top: B:251:0x02b3 }] */
    /* JADX WARN: Code duplicated, block: B:107:0x0160 A[Catch: all -> 0x0326, TryCatch #1 {all -> 0x0326, blocks: (B:28:0x005a, B:32:0x006c, B:36:0x007e, B:56:0x00bb, B:59:0x00c0, B:62:0x00cc, B:193:0x02a7, B:65:0x00d6, B:83:0x010a, B:86:0x0112, B:88:0x0116, B:90:0x011a, B:92:0x012c, B:97:0x0140, B:102:0x0154, B:150:0x0202, B:152:0x0208, B:154:0x0210, B:162:0x0226, B:164:0x022c, B:166:0x0230, B:168:0x0234, B:173:0x023d, B:176:0x0257, B:178:0x025e, B:179:0x0264, B:174:0x0248, B:181:0x026f, B:183:0x0275, B:186:0x027b, B:189:0x0295, B:191:0x029c, B:192:0x02a2, B:187:0x0286, B:105:0x015c, B:107:0x0160, B:112:0x0169, B:131:0x01b8, B:134:0x01bd, B:136:0x01cb, B:138:0x01d1, B:115:0x016e, B:117:0x017c, B:119:0x0182, B:122:0x0197, B:124:0x01a1, B:126:0x01ab, B:146:0x01f2, B:194:0x02b3, B:196:0x02bf, B:214:0x02fc, B:216:0x0306, B:218:0x030a), top: B:251:0x02b3 }] */
    /* JADX WARN: Code duplicated, block: B:110:0x0166 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:111:0x0168  */
    /* JADX WARN: Code duplicated, block: B:115:0x016e A[Catch: all -> 0x0326, TryCatch #1 {all -> 0x0326, blocks: (B:28:0x005a, B:32:0x006c, B:36:0x007e, B:56:0x00bb, B:59:0x00c0, B:62:0x00cc, B:193:0x02a7, B:65:0x00d6, B:83:0x010a, B:86:0x0112, B:88:0x0116, B:90:0x011a, B:92:0x012c, B:97:0x0140, B:102:0x0154, B:150:0x0202, B:152:0x0208, B:154:0x0210, B:162:0x0226, B:164:0x022c, B:166:0x0230, B:168:0x0234, B:173:0x023d, B:176:0x0257, B:178:0x025e, B:179:0x0264, B:174:0x0248, B:181:0x026f, B:183:0x0275, B:186:0x027b, B:189:0x0295, B:191:0x029c, B:192:0x02a2, B:187:0x0286, B:105:0x015c, B:107:0x0160, B:112:0x0169, B:131:0x01b8, B:134:0x01bd, B:136:0x01cb, B:138:0x01d1, B:115:0x016e, B:117:0x017c, B:119:0x0182, B:122:0x0197, B:124:0x01a1, B:126:0x01ab, B:146:0x01f2, B:194:0x02b3, B:196:0x02bf, B:214:0x02fc, B:216:0x0306, B:218:0x030a), top: B:251:0x02b3 }] */
    /* JADX WARN: Code duplicated, block: B:130:0x01b6 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:134:0x01bd A[Catch: all -> 0x0326, TryCatch #1 {all -> 0x0326, blocks: (B:28:0x005a, B:32:0x006c, B:36:0x007e, B:56:0x00bb, B:59:0x00c0, B:62:0x00cc, B:193:0x02a7, B:65:0x00d6, B:83:0x010a, B:86:0x0112, B:88:0x0116, B:90:0x011a, B:92:0x012c, B:97:0x0140, B:102:0x0154, B:150:0x0202, B:152:0x0208, B:154:0x0210, B:162:0x0226, B:164:0x022c, B:166:0x0230, B:168:0x0234, B:173:0x023d, B:176:0x0257, B:178:0x025e, B:179:0x0264, B:174:0x0248, B:181:0x026f, B:183:0x0275, B:186:0x027b, B:189:0x0295, B:191:0x029c, B:192:0x02a2, B:187:0x0286, B:105:0x015c, B:107:0x0160, B:112:0x0169, B:131:0x01b8, B:134:0x01bd, B:136:0x01cb, B:138:0x01d1, B:115:0x016e, B:117:0x017c, B:119:0x0182, B:122:0x0197, B:124:0x01a1, B:126:0x01ab, B:146:0x01f2, B:194:0x02b3, B:196:0x02bf, B:214:0x02fc, B:216:0x0306, B:218:0x030a), top: B:251:0x02b3 }] */
    /* JADX WARN: Code duplicated, block: B:142:0x01e8  */
    /* JADX WARN: Code duplicated, block: B:143:0x01e9 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:144:0x01eb  */
    /* JADX WARN: Code duplicated, block: B:152:0x0208 A[Catch: all -> 0x0326, TryCatch #1 {all -> 0x0326, blocks: (B:28:0x005a, B:32:0x006c, B:36:0x007e, B:56:0x00bb, B:59:0x00c0, B:62:0x00cc, B:193:0x02a7, B:65:0x00d6, B:83:0x010a, B:86:0x0112, B:88:0x0116, B:90:0x011a, B:92:0x012c, B:97:0x0140, B:102:0x0154, B:150:0x0202, B:152:0x0208, B:154:0x0210, B:162:0x0226, B:164:0x022c, B:166:0x0230, B:168:0x0234, B:173:0x023d, B:176:0x0257, B:178:0x025e, B:179:0x0264, B:174:0x0248, B:181:0x026f, B:183:0x0275, B:186:0x027b, B:189:0x0295, B:191:0x029c, B:192:0x02a2, B:187:0x0286, B:105:0x015c, B:107:0x0160, B:112:0x0169, B:131:0x01b8, B:134:0x01bd, B:136:0x01cb, B:138:0x01d1, B:115:0x016e, B:117:0x017c, B:119:0x0182, B:122:0x0197, B:124:0x01a1, B:126:0x01ab, B:146:0x01f2, B:194:0x02b3, B:196:0x02bf, B:214:0x02fc, B:216:0x0306, B:218:0x030a), top: B:251:0x02b3 }] */
    /* JADX WARN: Code duplicated, block: B:157:0x021c  */
    /* JADX WARN: Code duplicated, block: B:159:0x0221  */
    /* JADX WARN: Code duplicated, block: B:160:0x0223  */
    /* JADX WARN: Code duplicated, block: B:162:0x0226 A[Catch: all -> 0x0326, TryCatch #1 {all -> 0x0326, blocks: (B:28:0x005a, B:32:0x006c, B:36:0x007e, B:56:0x00bb, B:59:0x00c0, B:62:0x00cc, B:193:0x02a7, B:65:0x00d6, B:83:0x010a, B:86:0x0112, B:88:0x0116, B:90:0x011a, B:92:0x012c, B:97:0x0140, B:102:0x0154, B:150:0x0202, B:152:0x0208, B:154:0x0210, B:162:0x0226, B:164:0x022c, B:166:0x0230, B:168:0x0234, B:173:0x023d, B:176:0x0257, B:178:0x025e, B:179:0x0264, B:174:0x0248, B:181:0x026f, B:183:0x0275, B:186:0x027b, B:189:0x0295, B:191:0x029c, B:192:0x02a2, B:187:0x0286, B:105:0x015c, B:107:0x0160, B:112:0x0169, B:131:0x01b8, B:134:0x01bd, B:136:0x01cb, B:138:0x01d1, B:115:0x016e, B:117:0x017c, B:119:0x0182, B:122:0x0197, B:124:0x01a1, B:126:0x01ab, B:146:0x01f2, B:194:0x02b3, B:196:0x02bf, B:214:0x02fc, B:216:0x0306, B:218:0x030a), top: B:251:0x02b3 }] */
    /* JADX WARN: Code duplicated, block: B:164:0x022c A[Catch: all -> 0x0326, TryCatch #1 {all -> 0x0326, blocks: (B:28:0x005a, B:32:0x006c, B:36:0x007e, B:56:0x00bb, B:59:0x00c0, B:62:0x00cc, B:193:0x02a7, B:65:0x00d6, B:83:0x010a, B:86:0x0112, B:88:0x0116, B:90:0x011a, B:92:0x012c, B:97:0x0140, B:102:0x0154, B:150:0x0202, B:152:0x0208, B:154:0x0210, B:162:0x0226, B:164:0x022c, B:166:0x0230, B:168:0x0234, B:173:0x023d, B:176:0x0257, B:178:0x025e, B:179:0x0264, B:174:0x0248, B:181:0x026f, B:183:0x0275, B:186:0x027b, B:189:0x0295, B:191:0x029c, B:192:0x02a2, B:187:0x0286, B:105:0x015c, B:107:0x0160, B:112:0x0169, B:131:0x01b8, B:134:0x01bd, B:136:0x01cb, B:138:0x01d1, B:115:0x016e, B:117:0x017c, B:119:0x0182, B:122:0x0197, B:124:0x01a1, B:126:0x01ab, B:146:0x01f2, B:194:0x02b3, B:196:0x02bf, B:214:0x02fc, B:216:0x0306, B:218:0x030a), top: B:251:0x02b3 }] */
    /* JADX WARN: Code duplicated, block: B:180:0x026d  */
    /* JADX WARN: Code duplicated, block: B:183:0x0275 A[Catch: all -> 0x0326, TryCatch #1 {all -> 0x0326, blocks: (B:28:0x005a, B:32:0x006c, B:36:0x007e, B:56:0x00bb, B:59:0x00c0, B:62:0x00cc, B:193:0x02a7, B:65:0x00d6, B:83:0x010a, B:86:0x0112, B:88:0x0116, B:90:0x011a, B:92:0x012c, B:97:0x0140, B:102:0x0154, B:150:0x0202, B:152:0x0208, B:154:0x0210, B:162:0x0226, B:164:0x022c, B:166:0x0230, B:168:0x0234, B:173:0x023d, B:176:0x0257, B:178:0x025e, B:179:0x0264, B:174:0x0248, B:181:0x026f, B:183:0x0275, B:186:0x027b, B:189:0x0295, B:191:0x029c, B:192:0x02a2, B:187:0x0286, B:105:0x015c, B:107:0x0160, B:112:0x0169, B:131:0x01b8, B:134:0x01bd, B:136:0x01cb, B:138:0x01d1, B:115:0x016e, B:117:0x017c, B:119:0x0182, B:122:0x0197, B:124:0x01a1, B:126:0x01ab, B:146:0x01f2, B:194:0x02b3, B:196:0x02bf, B:214:0x02fc, B:216:0x0306, B:218:0x030a), top: B:251:0x02b3 }] */
    /* JADX WARN: Code duplicated, block: B:274:0x02a7 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:277:0x016d A[ADDED_TO_REGION, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:280:0x01a1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:281:0x01ab A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:284:0x0169 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:285:0x0169 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:287:0x01bc A[ADDED_TO_REGION, SYNTHETIC] */
    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public MethodBinding[] methods() throws Throwable {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        boolean z;
        int i6;
        AbstractMethodDeclaration abstractMethodDeclarationSourceMethod;
        AbstractMethodDeclaration abstractMethodDeclarationSourceMethod2;
        MethodBinding methodBindingComputeSubstitutedMethod;
        int i7;
        TypeBinding typeBindingLeafComponentType;
        long j = 0;
        long j2 = 32768;
        if (!isPrototype()) {
            if ((this.tagBits & 32768) != 0) {
                return this.methods;
            }
            this.tagBits |= 32768;
            MethodBinding[] methodBindingArrMethods = this.prototype.methods();
            this.methods = methodBindingArrMethods;
            return methodBindingArrMethods;
        }
        if ((this.tagBits & 32768) != 0) {
            return this.methods;
        }
        if (!areMethodsInitialized()) {
            this.scope.buildMethods();
        }
        int i8 = 1;
        int i9 = 0;
        if ((this.tagBits & 16384) == 0) {
            MethodBinding[] methodBindingArr = this.methods;
            int length = methodBindingArr.length;
            if (length > 1) {
                ReferenceBinding.sortMethods(methodBindingArr, 0, length);
            }
            this.tagBits = 16384 | this.tagBits;
        }
        MethodBinding[] methodBindingArr2 = this.methods;
        try {
            int length2 = methodBindingArr2.length;
            int i10 = 0;
            i2 = 0;
            while (true) {
                MethodBinding methodBinding = null;
                if (i10 >= length2) {
                    int i11 = this.scope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_5 ? i8 : i9;
                    int i12 = this.scope.compilerOptions().complianceLevel == ClassFileConstants.JDK1_6 ? i8 : i9;
                    int length3 = this.methods.length;
                    int i13 = i9;
                    while (i13 < length3) {
                        MethodBinding methodBinding2 = methodBindingArr2[i13];
                        if (methodBinding2 != null) {
                            char[] cArr = methodBinding2.selector;
                            int i14 = i8;
                            AbstractMethodDeclaration abstractMethodDeclaration = methodBinding;
                            int i15 = i13 + 1;
                            while (i15 < length3) {
                                MethodBinding methodBinding3 = methodBindingArr2[i15];
                                if (methodBinding3 == null) {
                                    i3 = i11;
                                    i4 = i14;
                                    i14 = i4;
                                } else {
                                    if (!CharOperation.equals(cArr, methodBinding3.selector)) {
                                        break;
                                    }
                                    if (i11 != 0) {
                                        if (methodBinding2.areParameterErasuresEqual(methodBinding3)) {
                                            if (i12 == 0 || methodBinding2.returnType == null || methodBinding3.returnType == null || !TypeBinding.notEquals(methodBinding2.returnType.erasure(), methodBinding3.returnType.erasure())) {
                                                i3 = i11;
                                                i4 = i14;
                                            } else {
                                                TypeBinding[] typeBindingArr = methodBinding2.parameters;
                                                TypeBinding[] typeBindingArr2 = methodBinding3.parameters;
                                                int length4 = typeBindingArr.length;
                                                TypeVariableBinding[] typeVariableBindingArr = methodBinding2.typeVariables;
                                                i3 = i11;
                                                TypeVariableBinding[] typeVariableBindingArr2 = methodBinding3.typeVariables;
                                                boolean z2 = typeVariableBindingArr == typeVariableBindingArr2;
                                                if (z2) {
                                                    i4 = i14;
                                                } else {
                                                    i4 = i14;
                                                    methodBindingComputeSubstitutedMethod = methodBinding2.computeSubstitutedMethod(methodBinding3, this.scope.environment());
                                                    if (methodBindingComputeSubstitutedMethod != null) {
                                                        z2 = true;
                                                    }
                                                    if (methodBinding2.areParametersEqual(methodBindingComputeSubstitutedMethod) || !z2) {
                                                        if (typeVariableBindingArr != Binding.NO_TYPE_VARIABLES || typeVariableBindingArr2 == Binding.NO_TYPE_VARIABLES) {
                                                            if (length4 > 0) {
                                                                i7 = length4;
                                                                while (true) {
                                                                    i7--;
                                                                    if (i7 >= 0 || (TypeBinding.notEquals(typeBindingArr[i7], typeBindingArr2[i7].erasure()) && (!(typeBindingArr[i7] instanceof RawTypeBinding) || TypeBinding.notEquals(typeBindingArr2[i7].erasure(), ((RawTypeBinding) typeBindingArr[i7]).actualType())))) {
                                                                        break;
                                                                        break;
                                                                        break;
                                                                    }
                                                                    if (TypeBinding.equalsEquals(typeBindingArr[i7], typeBindingArr2[i7])) {
                                                                        typeBindingLeafComponentType = typeBindingArr[i7].leafComponentType();
                                                                        if (!(typeBindingLeafComponentType instanceof SourceTypeBinding) && typeBindingLeafComponentType.typeVariables() != Binding.NO_TYPE_VARIABLES) {
                                                                            i7 = length4;
                                                                            break;
                                                                        }
                                                                    }
                                                                }
                                                                if (i7 >= 0 && i7 < length4) {
                                                                    while (true) {
                                                                        length4--;
                                                                        if (length4 >= 0 || (TypeBinding.notEquals(typeBindingArr[length4].erasure(), typeBindingArr2[length4]) && (!(typeBindingArr2[length4] instanceof RawTypeBinding) || TypeBinding.notEquals(typeBindingArr[length4].erasure(), ((RawTypeBinding) typeBindingArr2[length4]).actualType())))) {
                                                                            break;
                                                                            break;
                                                                            break;
                                                                        }
                                                                    }
                                                                    i7 = length4;
                                                                }
                                                                if (i7 >= 0) {
                                                                }
                                                            } else if (length4 != 0) {
                                                            }
                                                        }
                                                        i14 = 0;
                                                    }
                                                    if (isEnum() || !(CharOperation.equals(cArr, TypeConstants.VALUEOF) || CharOperation.equals(cArr, TypeConstants.VALUES))) {
                                                        i5 = 1;
                                                        z = false;
                                                    } else {
                                                        i5 = 1;
                                                        z = true;
                                                    }
                                                    if (i14 == i5) {
                                                        i6 = i5;
                                                    } else {
                                                        i6 = 0;
                                                    }
                                                    if (abstractMethodDeclaration == null) {
                                                        abstractMethodDeclarationSourceMethod2 = methodBinding2.sourceMethod();
                                                        if (abstractMethodDeclarationSourceMethod2 != null || abstractMethodDeclarationSourceMethod2.binding == null) {
                                                            abstractMethodDeclaration = abstractMethodDeclarationSourceMethod2;
                                                        } else {
                                                            int i16 = (methodBinding2.returnType != null || methodBinding3.returnType == null) ? 0 : i5;
                                                            if (z) {
                                                                this.scope.problemReporter().duplicateEnumSpecialMethod(this, abstractMethodDeclarationSourceMethod2);
                                                                i16 = i5;
                                                            } else {
                                                                this.scope.problemReporter().duplicateMethodInType(abstractMethodDeclarationSourceMethod2, methodBinding2.areParametersEqual(methodBinding3), i14);
                                                            }
                                                            if (i16 != 0) {
                                                                abstractMethodDeclarationSourceMethod2.binding = null;
                                                                MethodBinding[] methodBindingArr3 = this.methods;
                                                                if (methodBindingArr2 == methodBindingArr3) {
                                                                    methodBindingArr2 = new MethodBinding[length3];
                                                                    System.arraycopy(methodBindingArr3, 0, methodBindingArr2, 0, length3);
                                                                }
                                                                methodBindingArr2[i13] = null;
                                                                i2++;
                                                                abstractMethodDeclaration = abstractMethodDeclarationSourceMethod2;
                                                                i6 = 0;
                                                            } else {
                                                                abstractMethodDeclaration = abstractMethodDeclarationSourceMethod2;
                                                            }
                                                        }
                                                    }
                                                    abstractMethodDeclarationSourceMethod = methodBinding3.sourceMethod();
                                                    if (abstractMethodDeclarationSourceMethod == null && abstractMethodDeclarationSourceMethod.binding != null) {
                                                        if (z) {
                                                            this.scope.problemReporter().duplicateEnumSpecialMethod(this, abstractMethodDeclarationSourceMethod);
                                                            i6 = 1;
                                                        } else {
                                                            this.scope.problemReporter().duplicateMethodInType(abstractMethodDeclarationSourceMethod, methodBinding2.areParametersEqual(methodBinding3), i14);
                                                        }
                                                        if (i6 != 0) {
                                                            abstractMethodDeclarationSourceMethod.binding = null;
                                                            MethodBinding[] methodBindingArr4 = this.methods;
                                                            if (methodBindingArr2 == methodBindingArr4) {
                                                                methodBindingArr2 = new MethodBinding[length3];
                                                                System.arraycopy(methodBindingArr4, 0, methodBindingArr2, 0, length3);
                                                            }
                                                            methodBindingArr2[i15] = null;
                                                            i2++;
                                                        }
                                                    }
                                                }
                                                methodBindingComputeSubstitutedMethod = methodBinding3;
                                                if (methodBinding2.areParametersEqual(methodBindingComputeSubstitutedMethod)) {
                                                    if (typeVariableBindingArr != Binding.NO_TYPE_VARIABLES) {
                                                        if (length4 > 0) {
                                                            i7 = length4;
                                                            while (true) {
                                                                i7--;
                                                                if (i7 >= 0) {
                                                                    break;
                                                                }
                                                                if (TypeBinding.equalsEquals(typeBindingArr[i7], typeBindingArr2[i7])) {
                                                                    typeBindingLeafComponentType = typeBindingArr[i7].leafComponentType();
                                                                    if (!(typeBindingLeafComponentType instanceof SourceTypeBinding)) {
                                                                        continue;
                                                                    }
                                                                }
                                                            }
                                                            if (i7 >= 0) {
                                                                while (true) {
                                                                    length4--;
                                                                    if (length4 >= 0) {
                                                                        break;
                                                                    }
                                                                }
                                                                i7 = length4;
                                                            }
                                                            if (i7 >= 0) {
                                                                i14 = 0;
                                                            }
                                                        } else if (length4 != 0) {
                                                            i14 = 0;
                                                        }
                                                    } else if (length4 > 0) {
                                                        i7 = length4;
                                                        while (true) {
                                                            i7--;
                                                            if (i7 >= 0) {
                                                                break;
                                                                break;
                                                            }
                                                            if (TypeBinding.equalsEquals(typeBindingArr[i7], typeBindingArr2[i7])) {
                                                                typeBindingLeafComponentType = typeBindingArr[i7].leafComponentType();
                                                                if (!(typeBindingLeafComponentType instanceof SourceTypeBinding)) {
                                                                    continue;
                                                                }
                                                            }
                                                        }
                                                        if (i7 >= 0) {
                                                            while (true) {
                                                                length4--;
                                                                if (length4 >= 0) {
                                                                    break;
                                                                    break;
                                                                }
                                                            }
                                                            i7 = length4;
                                                        }
                                                        if (i7 >= 0) {
                                                            i14 = 0;
                                                        }
                                                    } else if (length4 != 0) {
                                                        i14 = 0;
                                                    }
                                                } else if (typeVariableBindingArr != Binding.NO_TYPE_VARIABLES) {
                                                    if (length4 > 0) {
                                                        i7 = length4;
                                                        while (true) {
                                                            i7--;
                                                            if (i7 >= 0) {
                                                                break;
                                                                break;
                                                            }
                                                            if (TypeBinding.equalsEquals(typeBindingArr[i7], typeBindingArr2[i7])) {
                                                                typeBindingLeafComponentType = typeBindingArr[i7].leafComponentType();
                                                                if (!(typeBindingLeafComponentType instanceof SourceTypeBinding)) {
                                                                    continue;
                                                                }
                                                            }
                                                        }
                                                        if (i7 >= 0) {
                                                            while (true) {
                                                                length4--;
                                                                if (length4 >= 0) {
                                                                    break;
                                                                    break;
                                                                }
                                                            }
                                                            i7 = length4;
                                                        }
                                                        if (i7 >= 0) {
                                                            i14 = 0;
                                                        }
                                                    } else if (length4 != 0) {
                                                        i14 = 0;
                                                    }
                                                } else if (length4 > 0) {
                                                    i7 = length4;
                                                    while (true) {
                                                        i7--;
                                                        if (i7 >= 0) {
                                                            break;
                                                            break;
                                                        }
                                                        if (TypeBinding.equalsEquals(typeBindingArr[i7], typeBindingArr2[i7])) {
                                                            typeBindingLeafComponentType = typeBindingArr[i7].leafComponentType();
                                                            if (!(typeBindingLeafComponentType instanceof SourceTypeBinding)) {
                                                                continue;
                                                            }
                                                        }
                                                    }
                                                    if (i7 >= 0) {
                                                        while (true) {
                                                            length4--;
                                                            if (length4 >= 0) {
                                                                break;
                                                                break;
                                                            }
                                                        }
                                                        i7 = length4;
                                                    }
                                                    if (i7 >= 0) {
                                                        i14 = 0;
                                                    }
                                                } else if (length4 != 0) {
                                                    i14 = 0;
                                                }
                                                if (isEnum()) {
                                                    i5 = 1;
                                                    z = false;
                                                } else {
                                                    i5 = 1;
                                                    z = false;
                                                }
                                                if (i14 == i5) {
                                                    i6 = i5;
                                                } else {
                                                    i6 = 0;
                                                }
                                                if (abstractMethodDeclaration == null) {
                                                    abstractMethodDeclarationSourceMethod2 = methodBinding2.sourceMethod();
                                                    if (abstractMethodDeclarationSourceMethod2 != null) {
                                                        abstractMethodDeclaration = abstractMethodDeclarationSourceMethod2;
                                                    } else {
                                                        abstractMethodDeclaration = abstractMethodDeclarationSourceMethod2;
                                                    }
                                                }
                                                abstractMethodDeclarationSourceMethod = methodBinding3.sourceMethod();
                                                if (abstractMethodDeclarationSourceMethod == null) {
                                                }
                                            }
                                        }
                                        i3 = i11;
                                        i4 = i14;
                                        i14 = i4;
                                    } else {
                                        i3 = i11;
                                        i4 = i14;
                                        if (!methodBinding2.areParametersEqual(methodBinding3)) {
                                            i14 = i4;
                                        }
                                    }
                                    i14 = i4;
                                    if (isEnum()) {
                                        i5 = 1;
                                        z = false;
                                    } else {
                                        i5 = 1;
                                        z = false;
                                    }
                                    if (i14 == i5) {
                                        i6 = i5;
                                    } else {
                                        i6 = 0;
                                    }
                                    if (abstractMethodDeclaration == null) {
                                        abstractMethodDeclarationSourceMethod2 = methodBinding2.sourceMethod();
                                        if (abstractMethodDeclarationSourceMethod2 != null) {
                                            abstractMethodDeclaration = abstractMethodDeclarationSourceMethod2;
                                        } else {
                                            abstractMethodDeclaration = abstractMethodDeclarationSourceMethod2;
                                        }
                                    }
                                    abstractMethodDeclarationSourceMethod = methodBinding3.sourceMethod();
                                    if (abstractMethodDeclarationSourceMethod == null) {
                                    }
                                }
                                i15++;
                                i11 = i3;
                                i8 = 1;
                                i9 = 0;
                                methodBinding = null;
                            }
                            try {
                                if (methodBinding2.returnType == null && methodBindingArr2[i13] != null) {
                                    AbstractMethodDeclaration abstractMethodDeclarationSourceMethod3 = methodBinding2.sourceMethod();
                                    if (abstractMethodDeclarationSourceMethod3 != null) {
                                        abstractMethodDeclarationSourceMethod3.binding = methodBinding;
                                    }
                                    MethodBinding[] methodBindingArr5 = this.methods;
                                    if (methodBindingArr2 == methodBindingArr5) {
                                        methodBindingArr2 = new MethodBinding[length3];
                                        System.arraycopy(methodBindingArr5, i9, methodBindingArr2, i9, length3);
                                    }
                                    methodBindingArr2[i13] = methodBinding;
                                    i2++;
                                }
                            } catch (Throwable th) {
                                th = th;
                                i = i9;
                            }
                        }
                        i13++;
                        j = 0;
                        j2 = 32768;
                    }
                    if ((this.tagBits & j2) != j) {
                        return this.methods;
                    }
                    if (i2 > 0) {
                        int length5 = methodBindingArr2.length - i2;
                        if (length5 == 0) {
                            setMethods(Binding.NO_METHODS);
                        } else {
                            MethodBinding[] methodBindingArr6 = new MethodBinding[length5];
                            int length6 = methodBindingArr2.length;
                            int i17 = i9;
                            while (i9 < length6) {
                                MethodBinding methodBinding4 = methodBindingArr2[i9];
                                if (methodBinding4 != null) {
                                    methodBindingArr6[i17] = methodBinding4;
                                    i17++;
                                }
                                i9++;
                            }
                            setMethods(methodBindingArr6);
                        }
                    }
                    addDefaultAbstractMethods();
                    this.tagBits |= j2;
                    return this.methods;
                }
                try {
                    if ((this.tagBits & 32768) != 0) {
                        MethodBinding[] methodBindingArr7 = this.methods;
                        if ((this.tagBits & 32768) != 0) {
                            return this.methods;
                        }
                        if (i2 > 0) {
                            int length7 = methodBindingArr2.length - i2;
                            if (length7 == 0) {
                                setMethods(Binding.NO_METHODS);
                            } else {
                                MethodBinding[] methodBindingArr8 = new MethodBinding[length7];
                                int i18 = 0;
                                for (MethodBinding methodBinding5 : methodBindingArr2) {
                                    if (methodBinding5 != null) {
                                        methodBindingArr8[i18] = methodBinding5;
                                        i18++;
                                    }
                                }
                                setMethods(methodBindingArr8);
                            }
                        }
                        addDefaultAbstractMethods();
                        this.tagBits |= 32768;
                        return methodBindingArr7;
                    }
                    if (resolveTypesFor(this.methods[i10]) == null) {
                        MethodBinding[] methodBindingArr9 = this.methods;
                        if (methodBindingArr2 == methodBindingArr9) {
                            methodBindingArr2 = new MethodBinding[length2];
                            i = 0;
                            try {
                                System.arraycopy(methodBindingArr9, 0, methodBindingArr2, 0, length2);
                            } catch (Throwable th2) {
                                th = th2;
                            }
                        } else {
                            i = 0;
                        }
                        methodBindingArr2[i10] = null;
                        i2++;
                    } else {
                        i = 0;
                    }
                    i10++;
                    i9 = i;
                    j = 0;
                    j2 = 32768;
                    i8 = 1;
                } catch (Throwable th3) {
                    th = th3;
                    i = 0;
                }
                if ((this.tagBits & 32768) != 0) {
                    return this.methods;
                }
                if (i2 > 0) {
                    int length8 = methodBindingArr2.length - i2;
                    if (length8 == 0) {
                        setMethods(Binding.NO_METHODS);
                    } else {
                        MethodBinding[] methodBindingArr10 = new MethodBinding[length8];
                        int length9 = methodBindingArr2.length;
                        for (int i19 = i; i19 < length9; i19++) {
                            MethodBinding methodBinding6 = methodBindingArr2[i19];
                            if (methodBinding6 != null) {
                                methodBindingArr10[i] = methodBinding6;
                                i++;
                            }
                        }
                        setMethods(methodBindingArr10);
                    }
                }
                addDefaultAbstractMethods();
                this.tagBits |= 32768;
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            i = 0;
            i2 = 0;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding prototype() {
        return this.prototype;
    }

    public boolean isPrototype() {
        return this == this.prototype;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding containerAnnotationType() {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        ReferenceBinding referenceBinding = this.containerAnnotationType;
        if (referenceBinding instanceof UnresolvedReferenceBinding) {
            this.containerAnnotationType = (ReferenceBinding) BinaryTypeBinding.resolveType(referenceBinding, this.scope.environment(), false);
        }
        return this.containerAnnotationType;
    }

    public FieldBinding resolveTypeFor(FieldBinding fieldBinding) {
        if (!isPrototype()) {
            return this.prototype.resolveTypeFor(fieldBinding);
        }
        if ((fieldBinding.modifiers & 33554432) == 0) {
            return fieldBinding;
        }
        long j = this.scope.compilerOptions().sourceLevel;
        if (j >= ClassFileConstants.JDK1_5 && (fieldBinding.getAnnotationTagBits() & 70368744177664L) != 0) {
            fieldBinding.modifiers |= 1048576;
        }
        if (isViewedAsDeprecated() && !fieldBinding.isDeprecated()) {
            fieldBinding.modifiers |= 2097152;
        }
        if (hasRestrictedAccess()) {
            fieldBinding.modifiers |= 262144;
        }
        FieldDeclaration[] fieldDeclarationArr = this.scope.referenceContext.fields;
        int length = fieldDeclarationArr == null ? 0 : fieldDeclarationArr.length;
        for (int i = 0; i < length; i++) {
            if (fieldDeclarationArr[i].binding == fieldBinding) {
                MethodScope methodScope = fieldBinding.isStatic() ? this.scope.referenceContext.staticInitializerScope : this.scope.referenceContext.initializerScope;
                FieldBinding fieldBinding2 = methodScope.initializedField;
                try {
                    methodScope.initializedField = fieldBinding;
                    FieldDeclaration fieldDeclaration = fieldDeclarationArr[i];
                    TypeBinding typeBindingConvertToRawType = fieldDeclaration.getKind() == 3 ? methodScope.environment().convertToRawType(this, false) : fieldDeclaration.type.resolveType((BlockScope) methodScope, true);
                    fieldBinding.type = typeBindingConvertToRawType;
                    fieldBinding.modifiers &= -33554433;
                    if (typeBindingConvertToRawType == null) {
                        fieldDeclaration.binding = null;
                        methodScope.initializedField = fieldBinding2;
                        return null;
                    }
                    if (typeBindingConvertToRawType == TypeBinding.VOID) {
                        this.scope.problemReporter().variableTypeCannotBeVoid(fieldDeclaration);
                        fieldDeclaration.binding = null;
                        methodScope.initializedField = fieldBinding2;
                        return null;
                    }
                    if (typeBindingConvertToRawType.isArrayType() && ((ArrayBinding) typeBindingConvertToRawType).leafComponentType == TypeBinding.VOID) {
                        this.scope.problemReporter().variableTypeCannotBeVoidArray(fieldDeclaration);
                        fieldDeclaration.binding = null;
                        methodScope.initializedField = fieldBinding2;
                        return null;
                    }
                    if ((typeBindingConvertToRawType.tagBits & 128) != 0) {
                        fieldBinding.tagBits |= 128;
                    }
                    TypeBinding typeBindingLeafComponentType = typeBindingConvertToRawType.leafComponentType();
                    if ((typeBindingLeafComponentType instanceof ReferenceBinding) && (((ReferenceBinding) typeBindingLeafComponentType).modifiers & 1073741824) != 0) {
                        fieldBinding.modifiers |= 1073741824;
                    }
                    if (j >= ClassFileConstants.JDK1_8) {
                        Annotation[] annotationArr = fieldDeclaration.annotations;
                        if (annotationArr != null && annotationArr.length != 0) {
                            ASTNode.copySE8AnnotationsToType(methodScope, fieldBinding, annotationArr, fieldDeclaration.getKind() == 3);
                        }
                        Annotation.isTypeUseCompatible(fieldDeclaration.type, this.scope, annotationArr);
                    }
                    if (this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
                        if (fieldDeclaration.getKind() == 3) {
                            fieldBinding.tagBits |= 72057594037927936L;
                        } else {
                            if (hasNonNullDefaultFor(32, this.environment.usesNullTypeAnnotations())) {
                                fieldBinding.fillInDefaultNonNullness(fieldDeclaration, methodScope);
                            }
                            if (!this.scope.validateNullAnnotation(fieldBinding.tagBits, fieldDeclaration.type, fieldDeclaration.annotations)) {
                                fieldBinding.tagBits &= -108086391056891905L;
                            }
                        }
                    }
                    methodScope.initializedField = fieldBinding2;
                    ExternalAnnotationProvider externalAnnotationProvider = this.externalAnnotationProvider;
                    if (externalAnnotationProvider != null) {
                        ExternalAnnotationSuperimposer.annotateFieldBinding(fieldBinding, externalAnnotationProvider, this.environment);
                    }
                    return fieldBinding;
                } catch (Throwable th) {
                    methodScope.initializedField = fieldBinding2;
                    throw th;
                }
            }
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:143:0x029d  */
    /* JADX WARN: Code duplicated, block: B:146:0x02ab  */
    /* JADX WARN: Code duplicated, block: B:148:0x02b5  */
    /* JADX WARN: Code duplicated, block: B:149:0x02b9  */
    /* JADX WARN: Code duplicated, block: B:151:0x02c4  */
    /* JADX WARN: Code duplicated, block: B:167:0x0303  */
    /* JADX WARN: Code duplicated, block: B:169:0x0307  */
    /* JADX WARN: Code duplicated, block: B:186:0x0333  */
    /* JADX WARN: Code duplicated, block: B:188:0x033c  */
    /* JADX WARN: Code duplicated, block: B:191:0x0341 A[LOOP:4: B:189:0x033e->B:191:0x0341, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:193:0x0349  */
    /* JADX WARN: Code duplicated, block: B:195:0x0353  */
    /* JADX WARN: Code duplicated, block: B:201:0x0368  */
    /* JADX WARN: Code duplicated, block: B:203:0x037e  */
    /* JADX WARN: Code duplicated, block: B:205:0x0388  */
    /* JADX WARN: Code duplicated, block: B:207:0x0395  */
    /* JADX WARN: Code duplicated, block: B:209:0x039f  */
    /* JADX WARN: Code duplicated, block: B:212:0x03a8  */
    /* JADX WARN: Code duplicated, block: B:214:0x03ad A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:215:0x03ae  */
    /* JADX WARN: Code duplicated, block: B:217:0x03ba  */
    /* JADX WARN: Code duplicated, block: B:81:0x017d  */
    /* JADX WARN: Code duplicated, block: B:84:0x018f  */
    /* JADX WARN: Code duplicated, block: B:86:0x019b  */
    /* JADX WARN: Code duplicated, block: B:87:0x019e  */
    /* JADX WARN: Code duplicated, block: B:89:0x01a2  */
    /* JADX WARN: Code duplicated, block: B:90:0x01ac  */
    /* JADX WARN: Code duplicated, block: B:92:0x01b9  */
    public MethodBinding resolveTypesFor(MethodBinding methodBinding) {
        boolean z;
        Annotation[] annotationArr;
        int i;
        int i2;
        CompilerOptions compilerOptions;
        ExternalAnnotationProvider externalAnnotationProvider;
        long j;
        TypeReference typeReference;
        int length;
        int i3;
        int i4;
        boolean z2;
        TypeBinding typeBindingResolveType;
        TypeBinding typeBindingLeafComponentType;
        Annotation[] annotationArr2;
        int i5;
        boolean z3;
        TypeBinding typeBindingResolveType2;
        int i6;
        TypeBinding typeBindingLeafComponentType2;
        if (!isPrototype()) {
            return this.prototype.resolveTypesFor(methodBinding);
        }
        if ((methodBinding.modifiers & 33554432) == 0) {
            return methodBinding;
        }
        long j2 = this.scope.compilerOptions().sourceLevel;
        int i7 = 0;
        if (j2 >= ClassFileConstants.JDK1_5) {
            ReferenceBinding javaLangObject = this.scope.getJavaLangObject();
            for (TypeVariableBinding typeVariableBinding : methodBinding.typeVariables) {
                typeVariableBinding.superclass = javaLangObject;
            }
            if ((methodBinding.getAnnotationTagBits() & 70368744177664L) != 0) {
                methodBinding.modifiers |= 1048576;
            }
        }
        if (isViewedAsDeprecated() && !methodBinding.isDeprecated()) {
            methodBinding.modifiers |= 2097152;
        }
        if (hasRestrictedAccess()) {
            methodBinding.modifiers |= 262144;
        }
        AbstractMethodDeclaration abstractMethodDeclarationSourceMethod = methodBinding.sourceMethod();
        if (abstractMethodDeclarationSourceMethod == null) {
            return null;
        }
        TypeParameter[] typeParameterArrTypeParameters = abstractMethodDeclarationSourceMethod.typeParameters();
        boolean z4 = true;
        if (typeParameterArrTypeParameters != null) {
            abstractMethodDeclarationSourceMethod.scope.connectTypeVariables(typeParameterArrTypeParameters, true);
            for (TypeParameter typeParameter : typeParameterArrTypeParameters) {
                typeParameter.checkBounds(abstractMethodDeclarationSourceMethod.scope);
            }
        }
        TypeReference[] typeReferenceArr = abstractMethodDeclarationSourceMethod.thrownExceptions;
        if (typeReferenceArr != null) {
            int length2 = typeReferenceArr.length;
            methodBinding.thrownExceptions = new ReferenceBinding[length2];
            int i8 = 0;
            int i9 = 0;
            while (i8 < length2) {
                ReferenceBinding referenceBinding = (ReferenceBinding) typeReferenceArr[i8].resolveType((BlockScope) abstractMethodDeclarationSourceMethod.scope, true);
                if (referenceBinding != null) {
                    if (referenceBinding.isBoundParameterizedType()) {
                        abstractMethodDeclarationSourceMethod.scope.problemReporter().invalidParameterizedExceptionType(referenceBinding, typeReferenceArr[i8]);
                    } else if (referenceBinding.findSuperTypeOriginatingFrom(21, true) == null && referenceBinding.isValidBinding()) {
                        abstractMethodDeclarationSourceMethod.scope.problemReporter().cannotThrowType(typeReferenceArr[i8], referenceBinding);
                    } else {
                        if ((referenceBinding.tagBits & 128) != 0) {
                            methodBinding.tagBits |= 128;
                        }
                        if (typeReferenceArr[i8].hasNullTypeAnnotation(TypeReference.AnnotationPosition.ANY)) {
                            abstractMethodDeclarationSourceMethod.scope.problemReporter().nullAnnotationUnsupportedLocation(typeReferenceArr[i8]);
                        }
                        methodBinding.modifiers |= referenceBinding.modifiers & 1073741824;
                        methodBinding.thrownExceptions[i9] = referenceBinding;
                        i9++;
                    }
                }
                i8++;
                i7 = 0;
            }
            if (i9 < length2) {
                ReferenceBinding[] referenceBindingArr = methodBinding.thrownExceptions;
                ReferenceBinding[] referenceBindingArr2 = new ReferenceBinding[i9];
                methodBinding.thrownExceptions = referenceBindingArr2;
                System.arraycopy(referenceBindingArr, i7, referenceBindingArr2, i7, i9);
            }
        }
        if (abstractMethodDeclarationSourceMethod.receiver != null) {
            methodBinding.receiver = abstractMethodDeclarationSourceMethod.receiver.type.resolveType((BlockScope) abstractMethodDeclarationSourceMethod.scope, true);
        }
        boolean z5 = this.scope.compilerOptions().reportUnavoidableGenericTypeProblems;
        Argument[] argumentArr = abstractMethodDeclarationSourceMethod.arguments;
        if (argumentArr != null) {
            int length3 = argumentArr.length;
            methodBinding.parameters = Binding.NO_PARAMETERS;
            TypeBinding[] typeBindingArr = new TypeBinding[length3];
            int i10 = 0;
            z = false;
            while (i10 < length3) {
                Argument argument = argumentArr[i10];
                int i11 = length3;
                if (argument.annotations != null) {
                    methodBinding.tagBits |= 1024;
                }
                try {
                    if (z5 || methodBinding.isConstructor()) {
                        i5 = 1073741824;
                    } else {
                        i5 = 1073741824;
                        z3 = (argument.type.bits & 1073741824) == 0 ? z4 : false;
                        if (z3) {
                            argument.type.bits |= i5;
                        }
                        typeBindingResolveType2 = argument.type.resolveType((BlockScope) abstractMethodDeclarationSourceMethod.scope, true);
                        if (z3) {
                            argument.type.bits &= -1073741825;
                        }
                        if (typeBindingResolveType2 != null) {
                            i6 = i10;
                            z = true;
                        } else if (typeBindingResolveType2 == TypeBinding.VOID) {
                            abstractMethodDeclarationSourceMethod.scope.problemReporter().argumentTypeCannotBeVoid(abstractMethodDeclarationSourceMethod, argument);
                            i6 = i10;
                            z = true;
                        } else {
                            i6 = i10;
                            if ((typeBindingResolveType2.tagBits & 128) != 0) {
                                methodBinding.tagBits |= 128;
                            }
                            typeBindingLeafComponentType2 = typeBindingResolveType2.leafComponentType();
                            if ((typeBindingLeafComponentType2 instanceof ReferenceBinding) && (((ReferenceBinding) typeBindingLeafComponentType2).modifiers & 1073741824) != 0) {
                                methodBinding.modifiers |= 1073741824;
                            }
                            typeBindingArr[i6] = typeBindingResolveType2;
                            argument.binding = new LocalVariableBinding(argument, typeBindingResolveType2, argument.modifiers, abstractMethodDeclarationSourceMethod.scope);
                        }
                        i10 = i6 + 1;
                        length3 = i11;
                        z4 = true;
                    }
                    typeBindingResolveType2 = argument.type.resolveType((BlockScope) abstractMethodDeclarationSourceMethod.scope, true);
                    if (z3) {
                        argument.type.bits &= -1073741825;
                    }
                    if (typeBindingResolveType2 != null) {
                        i6 = i10;
                        z = true;
                    } else if (typeBindingResolveType2 == TypeBinding.VOID) {
                        abstractMethodDeclarationSourceMethod.scope.problemReporter().argumentTypeCannotBeVoid(abstractMethodDeclarationSourceMethod, argument);
                        i6 = i10;
                        z = true;
                    } else {
                        i6 = i10;
                        if ((typeBindingResolveType2.tagBits & 128) != 0) {
                            methodBinding.tagBits |= 128;
                        }
                        typeBindingLeafComponentType2 = typeBindingResolveType2.leafComponentType();
                        if (typeBindingLeafComponentType2 instanceof ReferenceBinding) {
                            methodBinding.modifiers |= 1073741824;
                        }
                        typeBindingArr[i6] = typeBindingResolveType2;
                        argument.binding = new LocalVariableBinding(argument, typeBindingResolveType2, argument.modifiers, abstractMethodDeclarationSourceMethod.scope);
                    }
                    i10 = i6 + 1;
                    length3 = i11;
                    z4 = true;
                } catch (Throwable th) {
                    if (z3) {
                        argument.type.bits &= -1073741825;
                    }
                    throw th;
                }
                if (z3) {
                    argument.type.bits |= i5;
                }
            }
            if (!z) {
                methodBinding.parameters = typeBindingArr;
            }
        } else {
            z = false;
        }
        if (j2 >= ClassFileConstants.JDK1_7) {
            if ((methodBinding.tagBits & 2251799813685248L) != 0) {
                if (!methodBinding.isVarargs()) {
                    abstractMethodDeclarationSourceMethod.scope.problemReporter().safeVarargsOnFixedArityMethod(methodBinding);
                } else if (!methodBinding.isStatic() && !methodBinding.isFinal() && !methodBinding.isConstructor()) {
                    abstractMethodDeclarationSourceMethod.scope.problemReporter().safeVarargsOnNonFinalInstanceMethod(methodBinding);
                }
            } else if (methodBinding.parameters != null && methodBinding.parameters.length > 0 && methodBinding.isVarargs() && !methodBinding.parameters[methodBinding.parameters.length - 1].isReifiable()) {
                abstractMethodDeclarationSourceMethod.scope.problemReporter().possibleHeapPollutionFromVararg(abstractMethodDeclarationSourceMethod.arguments[abstractMethodDeclarationSourceMethod.arguments.length - 1]);
            }
        }
        if (!methodBinding.isConstructor()) {
            TypeReference typeReference2 = abstractMethodDeclarationSourceMethod instanceof MethodDeclaration ? ((MethodDeclaration) abstractMethodDeclarationSourceMethod).returnType : null;
            if (typeReference2 == null) {
                abstractMethodDeclarationSourceMethod.scope.problemReporter().missingReturnType(abstractMethodDeclarationSourceMethod);
                methodBinding.returnType = null;
                i = 0;
                i2 = 1;
            } else {
                try {
                    if (z5) {
                        i4 = 1073741824;
                    } else {
                        i4 = 1073741824;
                        z2 = (typeReference2.bits & 1073741824) == 0;
                        if (z2) {
                            typeReference2.bits |= i4;
                        }
                        typeBindingResolveType = typeReference2.resolveType((BlockScope) abstractMethodDeclarationSourceMethod.scope, true);
                        if (z2) {
                            typeReference2.bits &= -1073741825;
                        }
                        if (typeBindingResolveType == null) {
                            i2 = 1;
                            i = 0;
                        } else {
                            if ((typeBindingResolveType.tagBits & 128) != 0) {
                                methodBinding.tagBits |= 128;
                            }
                            methodBinding.returnType = typeBindingResolveType;
                            if (j2 >= ClassFileConstants.JDK1_8 && !methodBinding.isVoidMethod()) {
                                annotationArr2 = abstractMethodDeclarationSourceMethod.annotations;
                                if (annotationArr2 != null && annotationArr2.length != 0) {
                                    ASTNode.copySE8AnnotationsToType(abstractMethodDeclarationSourceMethod.scope, methodBinding, abstractMethodDeclarationSourceMethod.annotations, false);
                                }
                                Annotation.isTypeUseCompatible(typeReference2, this.scope, abstractMethodDeclarationSourceMethod.annotations);
                            }
                            typeBindingLeafComponentType = typeBindingResolveType.leafComponentType();
                            if (!(typeBindingLeafComponentType instanceof ReferenceBinding) && (((ReferenceBinding) typeBindingLeafComponentType).modifiers & 1073741824) != 0) {
                                methodBinding.modifiers |= 1073741824;
                            } else if (typeBindingLeafComponentType == TypeBinding.VOID && abstractMethodDeclarationSourceMethod.annotations != null) {
                                rejectTypeAnnotatedVoidMethod(abstractMethodDeclarationSourceMethod);
                            }
                        }
                    }
                    typeBindingResolveType = typeReference2.resolveType((BlockScope) abstractMethodDeclarationSourceMethod.scope, true);
                    if (z2) {
                        typeReference2.bits &= -1073741825;
                    }
                    if (typeBindingResolveType == null) {
                        i2 = 1;
                        i = 0;
                    } else {
                        if ((typeBindingResolveType.tagBits & 128) != 0) {
                            methodBinding.tagBits |= 128;
                        }
                        methodBinding.returnType = typeBindingResolveType;
                        if (j2 >= ClassFileConstants.JDK1_8) {
                            annotationArr2 = abstractMethodDeclarationSourceMethod.annotations;
                            if (annotationArr2 != null) {
                                ASTNode.copySE8AnnotationsToType(abstractMethodDeclarationSourceMethod.scope, methodBinding, abstractMethodDeclarationSourceMethod.annotations, false);
                            }
                            Annotation.isTypeUseCompatible(typeReference2, this.scope, abstractMethodDeclarationSourceMethod.annotations);
                        }
                        typeBindingLeafComponentType = typeBindingResolveType.leafComponentType();
                        if (!(typeBindingLeafComponentType instanceof ReferenceBinding)) {
                            if (typeBindingLeafComponentType == TypeBinding.VOID) {
                                rejectTypeAnnotatedVoidMethod(abstractMethodDeclarationSourceMethod);
                            }
                        } else if (typeBindingLeafComponentType == TypeBinding.VOID) {
                            rejectTypeAnnotatedVoidMethod(abstractMethodDeclarationSourceMethod);
                        }
                    }
                } catch (Throwable th2) {
                    if (z2) {
                        typeReference2.bits &= -1073741825;
                    }
                    throw th2;
                }
                if (z2) {
                    typeReference2.bits |= i4;
                }
            }
            if (z) {
                abstractMethodDeclarationSourceMethod.binding = null;
                methodBinding.parameters = Binding.NO_PARAMETERS;
                if (typeParameterArrTypeParameters != null) {
                    length = typeParameterArrTypeParameters.length;
                    for (i3 = i; i3 < length; i3++) {
                        typeParameterArrTypeParameters[i3].binding = null;
                    }
                }
                return null;
            }
            compilerOptions = this.scope.compilerOptions();
            if (compilerOptions.isAnnotationBasedNullAnalysisEnabled && !methodBinding.isConstructor() && methodBinding.returnType != null) {
                j = methodBinding.tagBits & TagBits.AnnotationNullMASK;
                if (j != 0) {
                    typeReference = ((MethodDeclaration) abstractMethodDeclarationSourceMethod).returnType;
                    if (this.scope.environment().usesNullTypeAnnotations()) {
                        if (!this.scope.validateNullAnnotation(j, typeReference, abstractMethodDeclarationSourceMethod.annotations)) {
                            methodBinding.returnType.tagBits &= -108086391056891905L;
                        }
                        methodBinding.tagBits &= -108086391056891905L;
                    } else if (!this.scope.validateNullAnnotation(j, typeReference, abstractMethodDeclarationSourceMethod.annotations)) {
                        methodBinding.tagBits &= -108086391056891905L;
                    }
                }
            }
            if (compilerOptions.storeAnnotations) {
                createArgumentBindings(methodBinding, compilerOptions);
            }
            if (i2 != 0) {
                return methodBinding;
            }
            methodBinding.modifiers &= -33554433;
            externalAnnotationProvider = this.externalAnnotationProvider;
            if (externalAnnotationProvider != null) {
                ExternalAnnotationSuperimposer.annotateMethodBinding(methodBinding, externalAnnotationProvider, this.environment);
            }
            return methodBinding;
        }
        if (j2 >= ClassFileConstants.JDK1_8 && (annotationArr = abstractMethodDeclarationSourceMethod.annotations) != null && annotationArr.length != 0) {
            i = 0;
            ASTNode.copySE8AnnotationsToType(abstractMethodDeclarationSourceMethod.scope, methodBinding, abstractMethodDeclarationSourceMethod.annotations, false);
        }
        i2 = i;
        if (z) {
            abstractMethodDeclarationSourceMethod.binding = null;
            methodBinding.parameters = Binding.NO_PARAMETERS;
            if (typeParameterArrTypeParameters != null) {
                length = typeParameterArrTypeParameters.length;
                while (i3 < length) {
                    typeParameterArrTypeParameters[i3].binding = null;
                }
            }
            return null;
        }
        compilerOptions = this.scope.compilerOptions();
        if (compilerOptions.isAnnotationBasedNullAnalysisEnabled) {
            j = methodBinding.tagBits & TagBits.AnnotationNullMASK;
            if (j != 0) {
                typeReference = ((MethodDeclaration) abstractMethodDeclarationSourceMethod).returnType;
                if (this.scope.environment().usesNullTypeAnnotations()) {
                    if (!this.scope.validateNullAnnotation(j, typeReference, abstractMethodDeclarationSourceMethod.annotations)) {
                        methodBinding.returnType.tagBits &= -108086391056891905L;
                    }
                    methodBinding.tagBits &= -108086391056891905L;
                } else if (!this.scope.validateNullAnnotation(j, typeReference, abstractMethodDeclarationSourceMethod.annotations)) {
                    methodBinding.tagBits &= -108086391056891905L;
                }
            }
        }
        if (compilerOptions.storeAnnotations) {
            createArgumentBindings(methodBinding, compilerOptions);
        }
        if (i2 != 0) {
            return methodBinding;
        }
        methodBinding.modifiers &= -33554433;
        externalAnnotationProvider = this.externalAnnotationProvider;
        if (externalAnnotationProvider != null) {
            ExternalAnnotationSuperimposer.annotateMethodBinding(methodBinding, externalAnnotationProvider, this.environment);
        }
        return methodBinding;
        i = 0;
        i2 = i;
        if (z) {
            abstractMethodDeclarationSourceMethod.binding = null;
            methodBinding.parameters = Binding.NO_PARAMETERS;
            if (typeParameterArrTypeParameters != null) {
                length = typeParameterArrTypeParameters.length;
                while (i3 < length) {
                    typeParameterArrTypeParameters[i3].binding = null;
                }
            }
            return null;
        }
        compilerOptions = this.scope.compilerOptions();
        if (compilerOptions.isAnnotationBasedNullAnalysisEnabled) {
            j = methodBinding.tagBits & TagBits.AnnotationNullMASK;
            if (j != 0) {
                typeReference = ((MethodDeclaration) abstractMethodDeclarationSourceMethod).returnType;
                if (this.scope.environment().usesNullTypeAnnotations()) {
                    if (!this.scope.validateNullAnnotation(j, typeReference, abstractMethodDeclarationSourceMethod.annotations)) {
                        methodBinding.returnType.tagBits &= -108086391056891905L;
                    }
                    methodBinding.tagBits &= -108086391056891905L;
                } else if (!this.scope.validateNullAnnotation(j, typeReference, abstractMethodDeclarationSourceMethod.annotations)) {
                    methodBinding.tagBits &= -108086391056891905L;
                }
            }
        }
        if (compilerOptions.storeAnnotations) {
            createArgumentBindings(methodBinding, compilerOptions);
        }
        if (i2 != 0) {
            return methodBinding;
        }
        methodBinding.modifiers &= -33554433;
        externalAnnotationProvider = this.externalAnnotationProvider;
        if (externalAnnotationProvider != null) {
            ExternalAnnotationSuperimposer.annotateMethodBinding(methodBinding, externalAnnotationProvider, this.environment);
        }
        return methodBinding;
    }

    private static void rejectTypeAnnotatedVoidMethod(AbstractMethodDeclaration abstractMethodDeclaration) {
        Annotation[] annotationArr = abstractMethodDeclaration.annotations;
        int length = annotationArr == null ? 0 : annotationArr.length;
        for (int i = 0; i < length; i++) {
            ReferenceBinding referenceBinding = (ReferenceBinding) annotationArr[i].resolvedType;
            if (referenceBinding != null && (referenceBinding.tagBits & 9007199254740992L) != 0 && (referenceBinding.tagBits & 274877906944L) == 0) {
                abstractMethodDeclaration.scope.problemReporter().illegalUsageOfTypeAnnotations(annotationArr[i]);
            }
        }
    }

    private void createArgumentBindings(MethodBinding methodBinding, CompilerOptions compilerOptions) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (compilerOptions.isAnnotationBasedNullAnalysisEnabled) {
            getNullDefault();
        }
        AbstractMethodDeclaration abstractMethodDeclarationSourceMethod = methodBinding.sourceMethod();
        if (abstractMethodDeclarationSourceMethod != null) {
            if (methodBinding.parameters != Binding.NO_PARAMETERS) {
                abstractMethodDeclarationSourceMethod.createArgumentBindings();
            }
            if (compilerOptions.isAnnotationBasedNullAnalysisEnabled) {
                new ImplicitNullAnnotationVerifier(this.scope.environment()).checkImplicitNullAnnotations(methodBinding, abstractMethodDeclarationSourceMethod, true, this.scope);
            }
        }
    }

    public void evaluateNullAnnotations() {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.nullnessDefaultInitialized > 0 || !this.scope.compilerOptions().isAnnotationBasedNullAnalysisEnabled) {
            return;
        }
        int i = 0;
        if ((this.tagBits & TagBits.AnnotationNullMASK) != 0) {
            Annotation[] annotationArr = this.scope.referenceContext.annotations;
            for (int i2 = 0; i2 < annotationArr.length; i2++) {
                ReferenceBinding annotationType = annotationArr[i2].getCompilerAnnotation().getAnnotationType();
                if (annotationType != null && annotationType.hasNullBit(96)) {
                    this.scope.problemReporter().nullAnnotationUnsupportedLocation(annotationArr[i2]);
                    this.tagBits &= -108086391056891905L;
                }
            }
        }
        boolean zEquals = CharOperation.equals(this.sourceName, TypeConstants.PACKAGE_INFO_NAME);
        PackageBinding packageBinding = getPackage();
        boolean z = packageBinding.compoundName == CharOperation.NO_CHAR_CHAR;
        if (!zEquals) {
            boolean zIsNullnessAnnotationPackage = this.scope.environment().isNullnessAnnotationPackage(packageBinding);
            if (packageBinding.defaultNullness == 0 && !z && !zIsNullnessAnnotationPackage && !(this instanceof NestedTypeBinding)) {
                ReferenceBinding type = packageBinding.getType(TypeConstants.PACKAGE_INFO_NAME);
                if (type == null) {
                    this.scope.problemReporter().missingNonNullByDefaultAnnotation(this.scope.referenceContext);
                    packageBinding.defaultNullness = 2;
                } else if ((type instanceof SourceTypeBinding) && (type.tagBits & 512) == 0) {
                    CompilationUnitScope compilationUnitScope = ((SourceTypeBinding) type).scope.compilationUnitScope();
                    boolean z2 = compilationUnitScope.connectingHierarchy;
                    compilationUnitScope.connectingHierarchy = true;
                    try {
                        type.getAnnotationTagBits();
                        compilationUnitScope.connectingHierarchy = z2;
                    } catch (Throwable th) {
                        compilationUnitScope.connectingHierarchy = z2;
                        throw th;
                    }
                } else {
                    type.getAnnotationTagBits();
                }
            }
        }
        this.nullnessDefaultInitialized = 1;
        if (this.scope.environment().usesNullTypeAnnotations()) {
            int i3 = this.defaultNullness;
            if (i3 != 0) {
                if (zEquals) {
                    packageBinding.defaultNullness = i3;
                } else {
                    TypeDeclaration typeDeclaration = this.scope.referenceContext;
                    checkRedundantNullnessDefaultRecurse(typeDeclaration, typeDeclaration.annotations, this.defaultNullness, true);
                }
            } else if (zEquals || (z && !(this instanceof NestedTypeBinding))) {
                this.scope.problemReporter().missingNonNullByDefaultAnnotation(this.scope.referenceContext);
                if (!z) {
                    packageBinding.defaultNullness = 2;
                }
            }
        } else {
            long j = this.tagBits;
            if ((j & 288230376151711744L) != 0) {
                i = 2;
            } else if ((j & 144115188075855872L) != 0) {
                i = 1;
            } else {
                int i4 = this.defaultNullness;
                if (i4 != 0) {
                    if (i4 == 2) {
                        i = 2;
                        j = 288230376151711744L;
                    } else {
                        i = 1;
                        j = 144115188075855872L;
                    }
                }
            }
            if (i != 0) {
                if (zEquals) {
                    packageBinding.defaultNullness = i;
                } else {
                    this.defaultNullness = i;
                    TypeDeclaration typeDeclaration2 = this.scope.referenceContext;
                    checkRedundantNullnessDefaultRecurse(typeDeclaration2, typeDeclaration2.annotations, j & 432345564227567616L, false);
                }
            } else if (zEquals || (z && !(this instanceof NestedTypeBinding))) {
                this.scope.problemReporter().missingNonNullByDefaultAnnotation(this.scope.referenceContext);
                if (!z) {
                    packageBinding.defaultNullness = 2;
                }
            }
        }
        maybeMarkTypeParametersNonNull();
    }

    private void maybeMarkTypeParametersNonNull() {
        TypeVariableBinding[] typeVariableBindingArr;
        ClassScope classScope = this.scope;
        if (classScope == null || !classScope.hasDefaultNullnessFor(128) || (typeVariableBindingArr = this.typeVariables) == null || typeVariableBindingArr.length <= 0) {
            return;
        }
        int i = 0;
        AnnotationBinding[] annotationBindingArr = {this.environment.getNonNullAnnotation()};
        while (true) {
            TypeVariableBinding[] typeVariableBindingArr2 = this.typeVariables;
            if (i >= typeVariableBindingArr2.length) {
                return;
            }
            TypeVariableBinding typeVariableBinding = typeVariableBindingArr2[i];
            if ((typeVariableBinding.tagBits & TagBits.AnnotationNullMASK) == 0) {
                this.typeVariables[i] = (TypeVariableBinding) this.environment.createAnnotatedType(typeVariableBinding, annotationBindingArr);
            }
            i++;
        }
    }

    protected void checkRedundantNullnessDefaultRecurse(ASTNode aSTNode, Annotation[] annotationArr, long j, boolean z) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (this.fPackage.defaultNullness != 0) {
            if (z) {
                if (this.fPackage.defaultNullness != j) {
                    return;
                }
            } else if (this.fPackage.defaultNullness != 1 || (j & 144115188075855872L) == 0) {
                return;
            }
            this.scope.problemReporter().nullDefaultAnnotationIsRedundant(aSTNode, annotationArr, this.fPackage);
        }
    }

    protected boolean checkRedundantNullnessDefaultOne(ASTNode aSTNode, Annotation[] annotationArr, long j, boolean z) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        int nullDefault = getNullDefault();
        if (nullDefault == 0) {
            return true;
        }
        if (z) {
            if (nullDefault != j) {
                return false;
            }
        } else if ((j & 144115188075855872L) == 0) {
            return false;
        }
        this.scope.problemReporter().nullDefaultAnnotationIsRedundant(aSTNode, annotationArr, this);
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    boolean hasNonNullDefaultFor(int i, boolean z) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        if (z) {
            ClassScope classScope = this.scope;
            if (classScope == null) {
                return (i & this.defaultNullness) != 0;
            }
            return classScope.hasDefaultNullnessFor(i);
        }
        SourceTypeBinding sourceTypeBinding = null;
        for (Scope scope = this.scope; scope != null; scope = scope.parent) {
            int i2 = scope.kind;
            if (i2 == 2) {
                AbstractMethodDeclaration abstractMethodDeclarationReferenceMethod = ((MethodScope) scope).referenceMethod();
                if (abstractMethodDeclarationReferenceMethod != null && abstractMethodDeclarationReferenceMethod.binding != null) {
                    long j = abstractMethodDeclarationReferenceMethod.binding.tagBits;
                    if ((144115188075855872L & j) != 0) {
                        return true;
                    }
                    if ((j & 288230376151711744L) != 0) {
                        return false;
                    }
                }
            } else if (i2 == 3 && (sourceTypeBinding = ((ClassScope) scope).referenceContext.binding) != null) {
                int nullDefault = sourceTypeBinding.getNullDefault();
                if ((nullDefault & 1018) > 2) {
                    return true;
                }
                if (nullDefault != 0) {
                    return nullDefault == 1;
                }
            }
        }
        return sourceTypeBinding != null && sourceTypeBinding.getPackage().defaultNullness == 1;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public AnnotationHolder retrieveAnnotationHolder(Binding binding, boolean z) {
        if (!isPrototype()) {
            return this.prototype.retrieveAnnotationHolder(binding, z);
        }
        if (z) {
            binding.getAnnotationTagBits();
        }
        return super.retrieveAnnotationHolder(binding, false);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public void setContainerAnnotationType(ReferenceBinding referenceBinding) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        this.containerAnnotationType = referenceBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public void tagAsHavingDefectiveContainerType() {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        ReferenceBinding referenceBinding = this.containerAnnotationType;
        if (referenceBinding == null || !referenceBinding.isValidBinding()) {
            return;
        }
        this.containerAnnotationType = new ProblemReferenceBinding(this.containerAnnotationType.compoundName, this.containerAnnotationType, 22);
    }

    public FieldBinding[] setFields(FieldBinding[] fieldBindingArr) {
        if (!isPrototype()) {
            return this.prototype.setFields(fieldBindingArr);
        }
        if ((this.tagBits & 8388608) != 0) {
            TypeBinding[] annotatedTypes = this.scope.environment().getAnnotatedTypes(this);
            int length = annotatedTypes == null ? 0 : annotatedTypes.length;
            for (int i = 0; i < length; i++) {
                ((SourceTypeBinding) annotatedTypes[i]).fields = fieldBindingArr;
            }
        }
        this.fields = fieldBindingArr;
        return fieldBindingArr;
    }

    public ReferenceBinding[] setMemberTypes(ReferenceBinding[] referenceBindingArr) {
        if (!isPrototype()) {
            return this.prototype.setMemberTypes(referenceBindingArr);
        }
        this.memberTypes = referenceBindingArr;
        if ((this.tagBits & 8388608) != 0) {
            TypeBinding[] annotatedTypes = this.scope.environment().getAnnotatedTypes(this);
            int length = annotatedTypes == null ? 0 : annotatedTypes.length;
            for (int i = 0; i < length; i++) {
                SourceTypeBinding sourceTypeBinding = (SourceTypeBinding) annotatedTypes[i];
                sourceTypeBinding.tagBits |= TagBits.HasUnresolvedMemberTypes;
                sourceTypeBinding.memberTypes();
            }
        }
        return this.memberTypes;
    }

    public MethodBinding[] setMethods(MethodBinding[] methodBindingArr) {
        if (!isPrototype()) {
            return this.prototype.setMethods(methodBindingArr);
        }
        if ((this.tagBits & 8388608) != 0) {
            TypeBinding[] annotatedTypes = this.scope.environment().getAnnotatedTypes(this);
            int length = annotatedTypes == null ? 0 : annotatedTypes.length;
            for (int i = 0; i < length; i++) {
                ((SourceTypeBinding) annotatedTypes[i]).methods = methodBindingArr;
            }
        }
        this.methods = methodBindingArr;
        return methodBindingArr;
    }

    public ReferenceBinding setSuperClass(ReferenceBinding referenceBinding) {
        if (!isPrototype()) {
            return this.prototype.setSuperClass(referenceBinding);
        }
        if ((this.tagBits & 8388608) != 0) {
            TypeBinding[] annotatedTypes = this.scope.environment().getAnnotatedTypes(this);
            int length = annotatedTypes == null ? 0 : annotatedTypes.length;
            for (int i = 0; i < length; i++) {
                ((SourceTypeBinding) annotatedTypes[i]).superclass = referenceBinding;
            }
        }
        this.superclass = referenceBinding;
        return referenceBinding;
    }

    public ReferenceBinding[] setSuperInterfaces(ReferenceBinding[] referenceBindingArr) {
        if (!isPrototype()) {
            return this.prototype.setSuperInterfaces(referenceBindingArr);
        }
        if ((this.tagBits & 8388608) != 0) {
            TypeBinding[] annotatedTypes = this.scope.environment().getAnnotatedTypes(this);
            int length = annotatedTypes == null ? 0 : annotatedTypes.length;
            for (int i = 0; i < length; i++) {
                ((SourceTypeBinding) annotatedTypes[i]).superInterfaces = referenceBindingArr;
            }
        }
        this.superInterfaces = referenceBindingArr;
        return referenceBindingArr;
    }

    public TypeVariableBinding[] setTypeVariables(TypeVariableBinding[] typeVariableBindingArr) {
        if (!isPrototype()) {
            return this.prototype.setTypeVariables(typeVariableBindingArr);
        }
        if ((this.tagBits & 8388608) != 0) {
            TypeBinding[] annotatedTypes = this.scope.environment().getAnnotatedTypes(this);
            int length = annotatedTypes == null ? 0 : annotatedTypes.length;
            for (int i = 0; i < length; i++) {
                ((SourceTypeBinding) annotatedTypes[i]).typeVariables = typeVariableBindingArr;
            }
        }
        this.typeVariables = typeVariableBindingArr;
        return typeVariableBindingArr;
    }

    public final int sourceEnd() {
        if (!isPrototype()) {
            return this.prototype.sourceEnd();
        }
        return this.scope.referenceContext.sourceEnd;
    }

    public final int sourceStart() {
        if (!isPrototype()) {
            return this.prototype.sourceStart();
        }
        return this.scope.referenceContext.sourceStart;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    SimpleLookupTable storedAnnotations(boolean z) {
        ClassScope classScope;
        if (!isPrototype()) {
            return this.prototype.storedAnnotations(z);
        }
        if (z && this.storedAnnotations == null && (classScope = this.scope) != null) {
            classScope.referenceCompilationUnit().compilationResult.hasAnnotations = true;
            if (!this.scope.environment().globalOptions.storeAnnotations) {
                return null;
            }
            this.storedAnnotations = new SimpleLookupTable(3);
        }
        return this.storedAnnotations;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding superclass() {
        if (!isPrototype()) {
            ReferenceBinding referenceBindingSuperclass = this.prototype.superclass();
            this.superclass = referenceBindingSuperclass;
            return referenceBindingSuperclass;
        }
        return this.superclass;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding, org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public ReferenceBinding[] superInterfaces() {
        if (!isPrototype()) {
            ReferenceBinding[] referenceBindingArrSuperInterfaces = this.prototype.superInterfaces();
            this.superInterfaces = referenceBindingArrSuperInterfaces;
            return referenceBindingArrSuperInterfaces;
        }
        ReferenceBinding[] referenceBindingArr = this.superInterfaces;
        if (referenceBindingArr != null) {
            return referenceBindingArr;
        }
        if (!isAnnotationType()) {
            return null;
        }
        ReferenceBinding[] referenceBindingArr2 = {this.scope.getJavaLangAnnotationAnnotation()};
        this.superInterfaces = referenceBindingArr2;
        return referenceBindingArr2;
    }

    public SyntheticMethodBinding[] syntheticMethods() {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr == null) {
            return null;
        }
        HashMap map = mapArr[0];
        if (map == null || map.size() == 0) {
            return null;
        }
        SyntheticMethodBinding[] syntheticMethodBindingArr = new SyntheticMethodBinding[1];
        int i = 0;
        for (SyntheticMethodBinding[] syntheticMethodBindingArr2 : this.synthetics[0].values()) {
            int length = syntheticMethodBindingArr2.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (syntheticMethodBindingArr2[i2] != null) {
                    int i3 = i + 1;
                    if (i3 > syntheticMethodBindingArr.length) {
                        SyntheticMethodBinding[] syntheticMethodBindingArr3 = new SyntheticMethodBinding[i3];
                        System.arraycopy(syntheticMethodBindingArr, 0, syntheticMethodBindingArr3, 0, i);
                        syntheticMethodBindingArr = syntheticMethodBindingArr3;
                    }
                    syntheticMethodBindingArr[i] = syntheticMethodBindingArr2[i2];
                    i = i3;
                }
            }
        }
        SyntheticMethodBinding[] syntheticMethodBindingArr4 = new SyntheticMethodBinding[syntheticMethodBindingArr.length];
        for (SyntheticMethodBinding syntheticMethodBinding : syntheticMethodBindingArr) {
            syntheticMethodBindingArr4[syntheticMethodBinding.index] = syntheticMethodBinding;
        }
        return syntheticMethodBindingArr4;
    }

    public FieldBinding[] syntheticFields() {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        HashMap[] mapArr = this.synthetics;
        if (mapArr == null) {
            return null;
        }
        HashMap map = mapArr[1];
        int size = map == null ? 0 : map.size();
        HashMap map2 = this.synthetics[2];
        int size2 = map2 == null ? 0 : map2.size();
        int i = size + size2;
        if (i == 0) {
            return null;
        }
        FieldBinding[] fieldBindingArr = new FieldBinding[i];
        HashMap map3 = this.synthetics[1];
        if (map3 != null) {
            Iterator it = map3.values().iterator();
            for (int i2 = 0; i2 < size; i2++) {
                SyntheticFieldBinding syntheticFieldBinding = (SyntheticFieldBinding) it.next();
                fieldBindingArr[syntheticFieldBinding.index] = syntheticFieldBinding;
            }
        }
        HashMap map4 = this.synthetics[2];
        if (map4 != null) {
            Iterator it2 = map4.values().iterator();
            for (int i3 = 0; i3 < size2; i3++) {
                SyntheticFieldBinding syntheticFieldBinding2 = (SyntheticFieldBinding) it2.next();
                fieldBindingArr[syntheticFieldBinding2.index + size] = syntheticFieldBinding2;
            }
        }
        return fieldBindingArr;
    }

    public String toString() {
        if (hasTypeAnnotations()) {
            return annotatedDebugName();
        }
        StringBuffer stringBuffer = new StringBuffer(30);
        stringBuffer.append("(id=");
        if (this.f626id == Integer.MAX_VALUE) {
            stringBuffer.append("NoId");
        } else {
            stringBuffer.append(this.f626id);
        }
        stringBuffer.append(")\n");
        if (isDeprecated()) {
            stringBuffer.append("deprecated ");
        }
        if (isPublic()) {
            stringBuffer.append("public ");
        }
        if (isProtected()) {
            stringBuffer.append("protected ");
        }
        if (isPrivate()) {
            stringBuffer.append("private ");
        }
        if (isAbstract() && isClass()) {
            stringBuffer.append("abstract ");
        }
        if (isStatic() && isNestedType()) {
            stringBuffer.append("static ");
        }
        if (isFinal()) {
            stringBuffer.append("final ");
        }
        if (isEnum()) {
            stringBuffer.append("enum ");
        } else if (isAnnotationType()) {
            stringBuffer.append("@interface ");
        } else if (isClass()) {
            stringBuffer.append(ExternalAnnotationProvider.CLASS_PREFIX);
        } else {
            stringBuffer.append("interface ");
        }
        stringBuffer.append(this.compoundName != null ? CharOperation.toString(this.compoundName) : "UNNAMED TYPE");
        TypeVariableBinding[] typeVariableBindingArr = this.typeVariables;
        if (typeVariableBindingArr == null) {
            stringBuffer.append("<NULL TYPE VARIABLES>");
        } else if (typeVariableBindingArr != Binding.NO_TYPE_VARIABLES) {
            stringBuffer.append("<");
            int length = this.typeVariables.length;
            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    stringBuffer.append(", ");
                }
                TypeVariableBinding typeVariableBinding = this.typeVariables[i];
                if (typeVariableBinding == null) {
                    stringBuffer.append("NULL TYPE VARIABLE");
                } else {
                    char[] charArray = typeVariableBinding.toString().toCharArray();
                    stringBuffer.append(charArray, 1, charArray.length - 2);
                }
            }
            stringBuffer.append(">");
        }
        stringBuffer.append("\n\textends ");
        ReferenceBinding referenceBinding = this.superclass;
        stringBuffer.append(referenceBinding != null ? referenceBinding.debugName() : "NULL TYPE");
        ReferenceBinding[] referenceBindingArr = this.superInterfaces;
        if (referenceBindingArr != null) {
            if (referenceBindingArr != Binding.NO_SUPERINTERFACES) {
                stringBuffer.append("\n\timplements : ");
                int length2 = this.superInterfaces.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    if (i2 > 0) {
                        stringBuffer.append(", ");
                    }
                    ReferenceBinding referenceBinding2 = this.superInterfaces[i2];
                    stringBuffer.append(referenceBinding2 != null ? referenceBinding2.debugName() : "NULL TYPE");
                }
            }
        } else {
            stringBuffer.append("NULL SUPERINTERFACES");
        }
        if (enclosingType() != null) {
            stringBuffer.append("\n\tenclosing type : ");
            stringBuffer.append(enclosingType().debugName());
        }
        FieldBinding[] fieldBindingArr = this.fields;
        if (fieldBindingArr != null) {
            if (fieldBindingArr != Binding.NO_FIELDS) {
                stringBuffer.append("\n/*   fields   */");
                int length3 = this.fields.length;
                for (int i3 = 0; i3 < length3; i3++) {
                    StringBuffer stringBufferAppend = stringBuffer.append('\n');
                    FieldBinding fieldBinding = this.fields[i3];
                    stringBufferAppend.append(fieldBinding != null ? fieldBinding.toString() : "NULL FIELD");
                }
            }
        } else {
            stringBuffer.append("NULL FIELDS");
        }
        MethodBinding[] methodBindingArr = this.methods;
        if (methodBindingArr != null) {
            if (methodBindingArr != Binding.NO_METHODS) {
                stringBuffer.append("\n/*   methods   */");
                int length4 = this.methods.length;
                for (int i4 = 0; i4 < length4; i4++) {
                    StringBuffer stringBufferAppend2 = stringBuffer.append('\n');
                    MethodBinding methodBinding = this.methods[i4];
                    stringBufferAppend2.append(methodBinding != null ? methodBinding.toString() : "NULL METHOD");
                }
            }
        } else {
            stringBuffer.append("NULL METHODS");
        }
        ReferenceBinding[] referenceBindingArr2 = this.memberTypes;
        if (referenceBindingArr2 != null) {
            if (referenceBindingArr2 != Binding.NO_MEMBER_TYPES) {
                stringBuffer.append("\n/*   members   */");
                int length5 = this.memberTypes.length;
                for (int i5 = 0; i5 < length5; i5++) {
                    StringBuffer stringBufferAppend3 = stringBuffer.append('\n');
                    ReferenceBinding referenceBinding3 = this.memberTypes[i5];
                    stringBufferAppend3.append(referenceBinding3 != null ? referenceBinding3.toString() : "NULL TYPE");
                }
            }
        } else {
            stringBuffer.append("NULL MEMBER TYPES");
        }
        stringBuffer.append("\n\n");
        return stringBuffer.toString();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeVariableBinding[] typeVariables() {
        if (!isPrototype()) {
            TypeVariableBinding[] typeVariableBindingArrTypeVariables = this.prototype.typeVariables();
            this.typeVariables = typeVariableBindingArrTypeVariables;
            return typeVariableBindingArrTypeVariables;
        }
        TypeVariableBinding[] typeVariableBindingArr = this.typeVariables;
        return typeVariableBindingArr != null ? typeVariableBindingArr : Binding.NO_TYPE_VARIABLES;
    }

    void verifyMethods(MethodVerifier methodVerifier) {
        if (!isPrototype()) {
            throw new IllegalStateException();
        }
        methodVerifier.verify(this);
        int length = this.memberTypes.length;
        while (true) {
            length--;
            if (length < 0) {
                return;
            } else {
                ((SourceTypeBinding) this.memberTypes[length]).verifyMethods(methodVerifier);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding unannotated() {
        return this.prototype;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBinding
    public TypeBinding withoutToplevelNullAnnotation() {
        if (!hasNullTypeAnnotations()) {
            return this;
        }
        AnnotationBinding[] annotationBindingArrFilterNullTypeAnnotations = this.environment.filterNullTypeAnnotations(this.typeAnnotations);
        if (annotationBindingArrFilterNullTypeAnnotations.length > 0) {
            return this.environment.createAnnotatedType(this.prototype, annotationBindingArrFilterNullTypeAnnotations);
        }
        return this.prototype;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding
    public FieldBinding[] unResolvedFields() {
        if (!isPrototype()) {
            return this.prototype.unResolvedFields();
        }
        return this.fields;
    }

    public void tagIndirectlyAccessibleMembers() {
        if (!isPrototype()) {
            this.prototype.tagIndirectlyAccessibleMembers();
            return;
        }
        int i = 0;
        int i2 = 0;
        while (true) {
            FieldBinding[] fieldBindingArr = this.fields;
            if (i2 >= fieldBindingArr.length) {
                break;
            }
            if (!fieldBindingArr[i2].isPrivate()) {
                this.fields[i2].modifiers |= 134217728;
            }
            i2++;
        }
        while (true) {
            ReferenceBinding[] referenceBindingArr = this.memberTypes;
            if (i >= referenceBindingArr.length) {
                break;
            }
            if (!referenceBindingArr[i].isPrivate()) {
                this.memberTypes[i].modifiers |= 134217728;
            }
            i++;
        }
        if (this.superclass.isPrivate()) {
            ReferenceBinding referenceBinding = this.superclass;
            if (referenceBinding instanceof SourceTypeBinding) {
                ((SourceTypeBinding) referenceBinding).tagIndirectlyAccessibleMembers();
            }
        }
    }
}
