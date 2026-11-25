package org.eclipse.jdt.internal.compiler.lookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import name.antonsmirnov.firmata.FormatHelper;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.CaseStatement;
import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.ExpressionContext;
import org.eclipse.jdt.internal.compiler.ast.ImportReference;
import org.eclipse.jdt.internal.compiler.ast.Invocation;
import org.eclipse.jdt.internal.compiler.ast.LambdaExpression;
import org.eclipse.jdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.NameReference;
import org.eclipse.jdt.internal.compiler.ast.ReferenceExpression;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeParameter;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilation;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.util.HashtableOfObject;
import org.eclipse.jdt.internal.compiler.util.ObjectVector;
import org.eclipse.jdt.internal.compiler.util.SimpleLookupTable;
import org.eclipse.jdt.internal.compiler.util.SimpleSet;

/* JADX INFO: loaded from: classes14.dex */
public abstract class Scope {
    public static final int AUTOBOX_COMPATIBLE = 1;
    public static final int BLOCK_SCOPE = 1;
    public static final int CLASS_SCOPE = 3;
    public static final int COMPATIBLE = 0;
    public static final int COMPILATION_UNIT_SCOPE = 4;
    public static final int EQUAL_OR_MORE_SPECIFIC = -1;
    public static final int METHOD_SCOPE = 2;
    public static final int MORE_GENERIC = 1;
    public static final int NOT_COMPATIBLE = -1;
    public static final int NOT_RELATED = 0;
    public static final int VARARGS_COMPATIBLE = 2;
    private static Substitutor defaultSubstitutor = new Substitutor();
    public int kind;
    public Scope parent;

    public LocalVariableBinding findVariable(char[] cArr) {
        return null;
    }

    public abstract boolean hasDefaultNullnessFor(int i);

    public boolean isLambdaScope() {
        return false;
    }

    public abstract ProblemReporter problemReporter();

    void resolveTypeParameter(TypeParameter typeParameter) {
    }

    int startIndex() {
        return 0;
    }

    protected Scope(int i, Scope scope) {
        this.kind = i;
        this.parent = scope;
    }

    public static int compareTypes(TypeBinding typeBinding, TypeBinding typeBinding2) {
        if (typeBinding.isCompatibleWith(typeBinding2)) {
            return -1;
        }
        return typeBinding2.isCompatibleWith(typeBinding) ? 1 : 0;
    }

    public static TypeBinding convertEliminatingTypeVariables(TypeBinding typeBinding, ReferenceBinding referenceBinding, int i, Set set) {
        if ((typeBinding.tagBits & TagBits.HasTypeVariable) == 0) {
            return typeBinding;
        }
        int iKind = typeBinding.kind();
        if (iKind == 68) {
            ArrayBinding arrayBinding = (ArrayBinding) typeBinding;
            TypeBinding typeBinding2 = arrayBinding.leafComponentType;
            TypeBinding typeBindingConvertEliminatingTypeVariables = convertEliminatingTypeVariables(typeBinding2, referenceBinding, i, set);
            return TypeBinding.notEquals(typeBindingConvertEliminatingTypeVariables, typeBinding2) ? arrayBinding.environment.createArrayType(typeBindingConvertEliminatingTypeVariables.leafComponentType(), typeBindingConvertEliminatingTypeVariables.dimensions() + arrayBinding.dimensions()) : typeBinding;
        }
        if (iKind == 260) {
            ParameterizedTypeBinding parameterizedTypeBinding = (ParameterizedTypeBinding) typeBinding;
            ReferenceBinding referenceBindingEnclosingType = parameterizedTypeBinding.enclosingType();
            ReferenceBinding referenceBinding2 = referenceBindingEnclosingType != null ? (ReferenceBinding) convertEliminatingTypeVariables(referenceBindingEnclosingType, referenceBinding, i, set) : referenceBindingEnclosingType;
            TypeBinding[] typeBindingArr = parameterizedTypeBinding.arguments;
            int length = typeBindingArr == null ? 0 : typeBindingArr.length;
            TypeBinding[] typeBindingArr2 = typeBindingArr;
            for (int i2 = 0; i2 < length; i2++) {
                TypeBinding typeBinding3 = typeBindingArr[i2];
                TypeBinding typeBindingConvertEliminatingTypeVariables2 = convertEliminatingTypeVariables(typeBinding3, parameterizedTypeBinding.genericType(), i2, set);
                if (TypeBinding.notEquals(typeBindingConvertEliminatingTypeVariables2, typeBinding3)) {
                    if (typeBindingArr2 == typeBindingArr) {
                        typeBindingArr2 = new TypeBinding[length];
                        System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, i2);
                    }
                    typeBindingArr2[i2] = typeBindingConvertEliminatingTypeVariables2;
                } else if (typeBindingArr2 != typeBindingArr) {
                    typeBindingArr2[i2] = typeBinding3;
                }
            }
            return (TypeBinding.notEquals(referenceBindingEnclosingType, referenceBinding2) || typeBindingArr != typeBindingArr2) ? parameterizedTypeBinding.environment.createParameterizedType(parameterizedTypeBinding.genericType(), typeBindingArr2, referenceBinding2) : typeBinding;
        }
        if (iKind == 516) {
            WildcardBinding wildcardBinding = (WildcardBinding) typeBinding;
            TypeBinding typeBinding4 = wildcardBinding.bound;
            if (typeBinding4 == null) {
                return typeBinding;
            }
            TypeBinding typeBindingConvertEliminatingTypeVariables3 = convertEliminatingTypeVariables(typeBinding4, referenceBinding, i, set);
            return TypeBinding.notEquals(typeBindingConvertEliminatingTypeVariables3, typeBinding4) ? wildcardBinding.environment.createWildcard(wildcardBinding.genericType, wildcardBinding.rank, typeBindingConvertEliminatingTypeVariables3, null, wildcardBinding.boundKind) : typeBinding;
        }
        if (iKind == 2052) {
            ReferenceBinding referenceBinding3 = (ReferenceBinding) typeBinding;
            ReferenceBinding referenceBindingEnclosingType2 = referenceBinding3.enclosingType();
            ReferenceBinding referenceBinding4 = referenceBindingEnclosingType2 != null ? (ReferenceBinding) convertEliminatingTypeVariables(referenceBindingEnclosingType2, referenceBinding, i, set) : referenceBindingEnclosingType2;
            TypeVariableBinding[] typeVariableBindingArrTypeVariables = referenceBinding3.typeVariables();
            int length2 = typeVariableBindingArrTypeVariables == null ? 0 : typeVariableBindingArrTypeVariables.length;
            TypeBinding[] typeBindingArr3 = typeVariableBindingArrTypeVariables;
            for (int i3 = 0; i3 < length2; i3++) {
                TypeVariableBinding typeVariableBinding = typeVariableBindingArrTypeVariables[i3];
                TypeBinding typeBindingConvertEliminatingTypeVariables4 = convertEliminatingTypeVariables(typeVariableBinding, referenceBinding3, i3, set);
                if (TypeBinding.notEquals(typeBindingConvertEliminatingTypeVariables4, typeVariableBinding)) {
                    if (typeBindingArr3 == typeVariableBindingArrTypeVariables) {
                        typeBindingArr3 = new TypeBinding[length2];
                        System.arraycopy(typeVariableBindingArrTypeVariables, 0, typeBindingArr3, 0, i3);
                    }
                    typeBindingArr3[i3] = typeBindingConvertEliminatingTypeVariables4;
                } else if (typeBindingArr3 != typeVariableBindingArrTypeVariables) {
                    typeBindingArr3[i3] = typeVariableBinding;
                }
            }
            return (TypeBinding.notEquals(referenceBindingEnclosingType2, referenceBinding4) || typeVariableBindingArrTypeVariables != typeBindingArr3) ? typeVariableBindingArrTypeVariables[0].environment.createParameterizedType(referenceBinding, typeBindingArr3, referenceBinding4) : typeBinding;
        }
        if (iKind == 4100) {
            if (referenceBinding == null) {
                return typeBinding;
            }
            TypeVariableBinding typeVariableBinding2 = (TypeVariableBinding) typeBinding;
            if (set != null && set.contains(typeBinding)) {
                return typeVariableBinding2.environment.createWildcard(referenceBinding, i, null, null, 0);
            }
            TypeBinding typeBindingUpperBound = typeVariableBinding2.upperBound();
            if (set == null) {
                set = new HashSet(2);
            }
            set.add(typeVariableBinding2);
            TypeBinding typeBindingConvertEliminatingTypeVariables5 = convertEliminatingTypeVariables(typeBindingUpperBound, referenceBinding, i, set);
            set.remove(typeVariableBinding2);
            return typeVariableBinding2.environment.createWildcard(referenceBinding, i, typeBindingConvertEliminatingTypeVariables5, null, 1);
        }
        if (iKind != 8196) {
            return typeBinding;
        }
        WildcardBinding wildcardBinding2 = (WildcardBinding) typeBinding;
        TypeBinding typeBinding5 = wildcardBinding2.bound;
        TypeBinding typeBindingConvertEliminatingTypeVariables6 = typeBinding5 != null ? convertEliminatingTypeVariables(typeBinding5, referenceBinding, i, set) : typeBinding5;
        TypeBinding[] typeBindingArr4 = wildcardBinding2.otherBounds;
        int length3 = typeBindingArr4 == null ? 0 : typeBindingArr4.length;
        TypeBinding[] typeBindingArr5 = typeBindingArr4;
        for (int i4 = 0; i4 < length3; i4++) {
            TypeBinding typeBinding6 = typeBindingArr4[i4];
            TypeBinding typeBindingConvertEliminatingTypeVariables7 = convertEliminatingTypeVariables(typeBinding6, referenceBinding, i, set);
            if (TypeBinding.notEquals(typeBindingConvertEliminatingTypeVariables7, typeBinding6)) {
                if (typeBindingArr5 == typeBindingArr4) {
                    TypeBinding[] typeBindingArr6 = new TypeBinding[length3];
                    System.arraycopy(typeBindingArr4, 0, typeBindingArr6, 0, i4);
                    typeBindingArr5 = typeBindingArr6;
                }
                typeBindingArr5[i4] = typeBindingConvertEliminatingTypeVariables7;
            } else if (typeBindingArr5 != typeBindingArr4) {
                typeBindingArr5[i4] = typeBinding6;
            }
        }
        return (TypeBinding.notEquals(typeBindingConvertEliminatingTypeVariables6, typeBinding5) || typeBindingArr5 != typeBindingArr4) ? wildcardBinding2.environment.createWildcard(wildcardBinding2.genericType, wildcardBinding2.rank, typeBindingConvertEliminatingTypeVariables6, typeBindingArr5, wildcardBinding2.boundKind) : typeBinding;
    }

    public static TypeBinding getBaseType(char[] cArr) {
        int length = cArr.length;
        if (length <= 2 || length >= 8) {
            return null;
        }
        char c = cArr[0];
        if (c == 'f') {
            if (length == 5 && cArr[1] == 'l' && cArr[2] == 'o' && cArr[3] == 'a' && cArr[4] == 't') {
                return TypeBinding.FLOAT;
            }
            return null;
        }
        if (c == 'i') {
            if (length == 3 && cArr[1] == 'n' && cArr[2] == 't') {
                return TypeBinding.INT;
            }
            return null;
        }
        if (c == 'l') {
            if (length == 4 && cArr[1] == 'o' && cArr[2] == 'n' && cArr[3] == 'g') {
                return TypeBinding.LONG;
            }
            return null;
        }
        if (c == 's') {
            if (length == 5 && cArr[1] == 'h' && cArr[2] == 'o' && cArr[3] == 'r' && cArr[4] == 't') {
                return TypeBinding.SHORT;
            }
            return null;
        }
        if (c == 'v') {
            if (length == 4 && cArr[1] == 'o' && cArr[2] == 'i' && cArr[3] == 'd') {
                return TypeBinding.VOID;
            }
            return null;
        }
        switch (c) {
            case 'b':
                if (length == 7 && cArr[1] == 'o' && cArr[2] == 'o' && cArr[3] == 'l' && cArr[4] == 'e' && cArr[5] == 'a' && cArr[6] == 'n') {
                    return TypeBinding.BOOLEAN;
                }
                if (length == 4 && cArr[1] == 'y' && cArr[2] == 't' && cArr[3] == 'e') {
                    return TypeBinding.BYTE;
                }
                return null;
            case 'c':
                if (length == 4 && cArr[1] == 'h' && cArr[2] == 'a' && cArr[3] == 'r') {
                    return TypeBinding.CHAR;
                }
                return null;
            case 'd':
                if (length == 6 && cArr[1] == 'o' && cArr[2] == 'u' && cArr[3] == 'b' && cArr[4] == 'l' && cArr[5] == 'e') {
                    return TypeBinding.DOUBLE;
                }
                return null;
            default:
                return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v4 */
    public static ReferenceBinding[] greaterLowerBound(ReferenceBinding[] referenceBindingArr) {
        int length;
        LocalTypeBinding localTypeBinding;
        if (referenceBindingArr == null || (length = referenceBindingArr.length) == 0) {
            return null;
        }
        ReferenceBinding[] referenceBindingArr2 = referenceBindingArr;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            LocalTypeBinding localTypeBinding2 = referenceBindingArr2[i];
            if (localTypeBinding2 != 0) {
                int i3 = 0;
                while (i3 < length) {
                    if (i != i3 && (localTypeBinding = referenceBindingArr2[i3]) != 0) {
                        if (localTypeBinding2.isCompatibleWith(localTypeBinding)) {
                            if (referenceBindingArr2 == referenceBindingArr) {
                                ReferenceBinding[] referenceBindingArr3 = new ReferenceBinding[length];
                                System.arraycopy(referenceBindingArr2, 0, referenceBindingArr3, 0, length);
                                referenceBindingArr2 = referenceBindingArr3;
                            }
                            referenceBindingArr2[i3] = 0;
                            i2++;
                        } else {
                            referenceBindingArr2 = referenceBindingArr2;
                        }
                    }
                    i3++;
                    referenceBindingArr2 = referenceBindingArr2;
                }
                referenceBindingArr2 = referenceBindingArr2;
            }
            i++;
            referenceBindingArr2 = referenceBindingArr2;
        }
        if (i2 == 0) {
            return referenceBindingArr2;
        }
        if (length == i2) {
            return null;
        }
        ReferenceBinding[] referenceBindingArr4 = new ReferenceBinding[length - i2];
        int i4 = 0;
        for (int i5 = 0; i5 < length; i5++) {
            Object[] objArr = referenceBindingArr2[i5];
            if (objArr != 0) {
                referenceBindingArr4[i4] = objArr;
                i4++;
            }
        }
        return referenceBindingArr4;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v5 */
    public static TypeBinding[] greaterLowerBound(TypeBinding[] typeBindingArr, Scope scope, LookupEnvironment lookupEnvironment) {
        int length;
        LocalTypeBinding localTypeBinding;
        ParameterizedTypeBinding parameterizedTypeBinding;
        ParameterizedTypeBinding parameterizedTypeBinding2;
        TypeBinding[] typeBindingArr2 = null;
        if (typeBindingArr == null || (length = typeBindingArr.length) == 0) {
            return null;
        }
        TypeBinding[] typeBindingArr3 = typeBindingArr;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            LocalTypeBinding localTypeBinding2 = typeBindingArr3[i];
            if (localTypeBinding2 != 0) {
                int i3 = 0;
                while (i3 < length) {
                    if (i == i3 || (localTypeBinding = typeBindingArr3[i3]) == 0) {
                        typeBindingArr3 = typeBindingArr3;
                        typeBindingArr3 = typeBindingArr3;
                        typeBindingArr3 = typeBindingArr3;
                        typeBindingArr3 = typeBindingArr3;
                    } else {
                        if (localTypeBinding2.isCompatibleWith(localTypeBinding, scope)) {
                            if (typeBindingArr3 == typeBindingArr) {
                                TypeBinding[] typeBindingArr4 = new TypeBinding[length];
                                System.arraycopy(typeBindingArr3, 0, typeBindingArr4, 0, length);
                                typeBindingArr3 = typeBindingArr4;
                            }
                            typeBindingArr3[i3] = typeBindingArr2;
                            i2++;
                        } else if (!localTypeBinding.isCompatibleWith(localTypeBinding2, scope) && localTypeBinding2.isParameterizedType() && localTypeBinding.isParameterizedType()) {
                            if (localTypeBinding2.original().isCompatibleWith(localTypeBinding.original(), scope)) {
                                typeBindingArr3 = typeBindingArr3;
                                parameterizedTypeBinding2 = (ParameterizedTypeBinding) localTypeBinding;
                                parameterizedTypeBinding = (ParameterizedTypeBinding) localTypeBinding2;
                            } else if (localTypeBinding.original().isCompatibleWith(localTypeBinding2.original(), scope)) {
                                typeBindingArr3 = typeBindingArr3;
                                parameterizedTypeBinding = (ParameterizedTypeBinding) localTypeBinding;
                                parameterizedTypeBinding2 = (ParameterizedTypeBinding) localTypeBinding2;
                            }
                            if (parameterizedTypeBinding2.arguments != null && parameterizedTypeBinding.isProperType(false) && parameterizedTypeBinding2.isProperType(false)) {
                                int length2 = parameterizedTypeBinding2.arguments.length;
                                TypeBinding[] typeBindingArr5 = new TypeBinding[length2];
                                int i4 = 0;
                                while (i4 < length2) {
                                    TypeBinding typeBindingUpperBound = parameterizedTypeBinding2.arguments[i4];
                                    if (typeBindingUpperBound.isTypeVariable()) {
                                        typeBindingUpperBound = ((TypeVariableBinding) typeBindingUpperBound).upperBound();
                                    }
                                    typeBindingArr5[i4] = typeBindingUpperBound;
                                    i4++;
                                    typeBindingArr2 = null;
                                }
                                ReferenceBinding referenceBinding = (ReferenceBinding) parameterizedTypeBinding2.original();
                                if (!parameterizedTypeBinding.isCompatibleWith(lookupEnvironment.createParameterizedType(referenceBinding, typeBindingArr5, referenceBinding.enclosingType()), scope)) {
                                    return typeBindingArr2;
                                }
                            }
                        }
                        typeBindingArr3 = typeBindingArr3;
                        typeBindingArr3 = typeBindingArr3;
                        typeBindingArr3 = typeBindingArr3;
                        typeBindingArr3 = typeBindingArr3;
                    }
                    i3++;
                    typeBindingArr2 = null;
                    typeBindingArr3 = typeBindingArr3;
                }
                typeBindingArr3 = typeBindingArr3;
            }
            i++;
            typeBindingArr3 = typeBindingArr3;
        }
        if (i2 == 0) {
            return typeBindingArr3;
        }
        if (length == i2) {
            return typeBindingArr2;
        }
        TypeBinding[] typeBindingArr6 = new TypeBinding[length - i2];
        int i5 = 0;
        for (int i6 = 0; i6 < length; i6++) {
            Object[] objArr = typeBindingArr3[i6];
            if (objArr != 0) {
                typeBindingArr6[i5] = objArr;
                i5++;
            }
        }
        return typeBindingArr6;
    }

    public static ReferenceBinding[] substitute(Substitution substitution, ReferenceBinding[] referenceBindingArr) {
        return defaultSubstitutor.substitute(substitution, referenceBindingArr);
    }

    public static TypeBinding substitute(Substitution substitution, TypeBinding typeBinding) {
        return defaultSubstitutor.substitute(substitution, typeBinding);
    }

    public static TypeBinding[] substitute(Substitution substitution, TypeBinding[] typeBindingArr) {
        return defaultSubstitutor.substitute(substitution, typeBindingArr);
    }

    public static class Substitutor {
        public ReferenceBinding[] substitute(Substitution substitution, ReferenceBinding[] referenceBindingArr) {
            if (referenceBindingArr == null) {
                return null;
            }
            int length = referenceBindingArr.length;
            ReferenceBinding[] referenceBindingArr2 = referenceBindingArr;
            for (int i = 0; i < length; i++) {
                ReferenceBinding referenceBinding = referenceBindingArr[i];
                TypeBinding typeBindingSubstitute = substitute(substitution, referenceBinding);
                if (!(typeBindingSubstitute instanceof ReferenceBinding)) {
                    return null;
                }
                if (typeBindingSubstitute != referenceBinding) {
                    if (referenceBindingArr2 == referenceBindingArr) {
                        referenceBindingArr2 = new ReferenceBinding[length];
                        System.arraycopy(referenceBindingArr, 0, referenceBindingArr2, 0, i);
                    }
                    referenceBindingArr2[i] = (ReferenceBinding) typeBindingSubstitute;
                } else if (referenceBindingArr2 != referenceBindingArr) {
                    referenceBindingArr2[i] = referenceBinding;
                }
            }
            return referenceBindingArr2;
        }

        /* JADX WARN: Code duplicated, block: B:42:0x00ab  */
        /* JADX WARN: Code duplicated, block: B:44:0x00b2  */
        /* JADX WARN: Code duplicated, block: B:48:0x00c4  */
        /* JADX WARN: Code duplicated, block: B:56:0x00ec  */
        public TypeBinding substitute(Substitution substitution, TypeBinding typeBinding) {
            ReferenceBinding referenceBinding;
            ReferenceBinding referenceBinding2;
            TypeBinding[] typeBindingArrSubstitute;
            WildcardBinding wildcardBinding;
            TypeBinding typeBinding2;
            TypeBinding typeBindingSubstitute;
            TypeBinding[] typeBindingArr;
            TypeBinding[] typeBindingArrSubstitute2;
            TypeBinding typeBinding3;
            TypeBinding[] typeBindingArr2;
            TypeBinding[] typeBindingArrGreaterLowerBound;
            TypeBinding[] typeBindingArr3 = null;
            if (typeBinding == null) {
                return null;
            }
            int iKind = typeBinding.kind();
            if (iKind != 4) {
                if (iKind == 68) {
                    ArrayBinding arrayBinding = (ArrayBinding) typeBinding;
                    TypeBinding typeBinding4 = arrayBinding.leafComponentType;
                    TypeBinding typeBindingSubstitute2 = substitute(substitution, typeBinding4);
                    if (typeBindingSubstitute2 != typeBinding4) {
                        return arrayBinding.environment.createArrayType(typeBindingSubstitute2.leafComponentType(), typeBindingSubstitute2.dimensions() + typeBinding.dimensions(), typeBinding.getTypeAnnotations());
                    }
                } else if (iKind == 260) {
                    ParameterizedTypeBinding parameterizedTypeBinding = (ParameterizedTypeBinding) typeBinding;
                    ReferenceBinding referenceBindingEnclosingType = typeBinding.enclosingType();
                    if (referenceBindingEnclosingType != null) {
                        referenceBinding2 = (ReferenceBinding) substitute(substitution, referenceBindingEnclosingType);
                        if (isMemberTypeOfRaw(typeBinding, referenceBinding2)) {
                            return parameterizedTypeBinding.environment.createRawType(parameterizedTypeBinding.genericType(), referenceBinding2, typeBinding.getTypeAnnotations());
                        }
                    } else {
                        referenceBinding2 = referenceBindingEnclosingType;
                    }
                    TypeBinding[] typeBindingArr4 = parameterizedTypeBinding.arguments;
                    if (typeBindingArr4 == null) {
                        typeBindingArrSubstitute = typeBindingArr4;
                    } else {
                        if (substitution.isRawSubstitution()) {
                            return parameterizedTypeBinding.environment.createRawType(parameterizedTypeBinding.genericType(), referenceBinding2, typeBinding.getTypeAnnotations());
                        }
                        typeBindingArrSubstitute = substitute(substitution, typeBindingArr4);
                    }
                    if (typeBindingArrSubstitute != typeBindingArr4 || referenceBinding2 != referenceBindingEnclosingType) {
                        return parameterizedTypeBinding.environment.createParameterizedType(parameterizedTypeBinding.genericType(), typeBindingArrSubstitute, referenceBinding2, typeBinding.getTypeAnnotations());
                    }
                } else if (iKind == 516) {
                    wildcardBinding = (WildcardBinding) typeBinding;
                    if (wildcardBinding.boundKind != 0) {
                        typeBinding2 = wildcardBinding.bound;
                        typeBindingSubstitute = substitute(substitution, typeBinding2);
                        typeBindingArr = wildcardBinding.otherBounds;
                        typeBindingArrSubstitute2 = substitute(substitution, typeBindingArr);
                        if (typeBindingSubstitute == typeBinding2 || typeBindingArr != typeBindingArrSubstitute2) {
                            if (typeBindingArr != null) {
                                TypeBinding[] typeBindingArr5 = new TypeBinding[typeBindingArrSubstitute2.length + 1];
                                typeBindingArr5[0] = typeBindingSubstitute;
                                System.arraycopy(typeBindingArrSubstitute2, 0, typeBindingArr5, 1, typeBindingArrSubstitute2.length);
                                typeBindingArrGreaterLowerBound = Scope.greaterLowerBound(typeBindingArr5, null, substitution.environment());
                                if (typeBindingArrGreaterLowerBound != null || typeBindingArrGreaterLowerBound == typeBindingArr5) {
                                    typeBinding3 = typeBindingSubstitute;
                                    typeBindingArr2 = typeBindingArrSubstitute2;
                                } else {
                                    TypeBinding typeBinding5 = typeBindingArrGreaterLowerBound[0];
                                    if (typeBindingArrGreaterLowerBound.length != 1) {
                                        typeBindingArr3 = new TypeBinding[typeBindingArrGreaterLowerBound.length - 1];
                                        System.arraycopy(typeBindingArrGreaterLowerBound, 1, typeBindingArr3, 0, typeBindingArrGreaterLowerBound.length - 1);
                                    }
                                    typeBindingArr2 = typeBindingArr3;
                                    typeBinding3 = typeBinding5;
                                }
                            } else {
                                typeBinding3 = typeBindingSubstitute;
                                typeBindingArr2 = typeBindingArrSubstitute2;
                            }
                            return wildcardBinding.environment.createWildcard(wildcardBinding.genericType, wildcardBinding.rank, typeBinding3, typeBindingArr2, wildcardBinding.boundKind, wildcardBinding.getTypeAnnotations());
                        }
                    }
                } else {
                    if (iKind == 2052) {
                        ReferenceBinding referenceBinding3 = (ReferenceBinding) typeBinding.unannotated();
                        ReferenceBinding referenceBindingEnclosingType2 = typeBinding.enclosingType();
                        if (referenceBindingEnclosingType2 != null) {
                            referenceBindingEnclosingType2 = (ReferenceBinding) (typeBinding.isStatic() ? substitution.environment().convertToRawType(referenceBindingEnclosingType2, true) : (ReferenceBinding) substitute(substitution, referenceBindingEnclosingType2));
                            if (isMemberTypeOfRaw(typeBinding, referenceBindingEnclosingType2)) {
                                return substitution.environment().createRawType(referenceBinding3, referenceBindingEnclosingType2, typeBinding.getTypeAnnotations());
                            }
                        }
                        if (substitution.isRawSubstitution()) {
                            return substitution.environment().createRawType(referenceBinding3, referenceBindingEnclosingType2, typeBinding.getTypeAnnotations());
                        }
                        return substitution.environment().createParameterizedType(referenceBinding3, substitute(substitution, (TypeBinding[]) referenceBinding3.typeVariables()), referenceBindingEnclosingType2, typeBinding.getTypeAnnotations());
                    }
                    if (iKind == 4100) {
                        return substitution.substitute((TypeVariableBinding) typeBinding);
                    }
                    if (iKind == 8196) {
                        wildcardBinding = (WildcardBinding) typeBinding;
                        if (wildcardBinding.boundKind != 0) {
                            typeBinding2 = wildcardBinding.bound;
                            typeBindingSubstitute = substitute(substitution, typeBinding2);
                            typeBindingArr = wildcardBinding.otherBounds;
                            typeBindingArrSubstitute2 = substitute(substitution, typeBindingArr);
                            if (typeBindingSubstitute == typeBinding2) {
                            }
                            if (typeBindingArr != null) {
                                TypeBinding[] typeBindingArr6 = new TypeBinding[typeBindingArrSubstitute2.length + 1];
                                typeBindingArr6[0] = typeBindingSubstitute;
                                System.arraycopy(typeBindingArrSubstitute2, 0, typeBindingArr6, 1, typeBindingArrSubstitute2.length);
                                typeBindingArrGreaterLowerBound = Scope.greaterLowerBound(typeBindingArr6, null, substitution.environment());
                                if (typeBindingArrGreaterLowerBound != null) {
                                    typeBinding3 = typeBindingSubstitute;
                                    typeBindingArr2 = typeBindingArrSubstitute2;
                                } else {
                                    typeBinding3 = typeBindingSubstitute;
                                    typeBindingArr2 = typeBindingArrSubstitute2;
                                }
                            } else {
                                typeBinding3 = typeBindingSubstitute;
                                typeBindingArr2 = typeBindingArrSubstitute2;
                            }
                            return wildcardBinding.environment.createWildcard(wildcardBinding.genericType, wildcardBinding.rank, typeBinding3, typeBindingArr2, wildcardBinding.boundKind, wildcardBinding.getTypeAnnotations());
                        }
                    } else if (iKind == 32772) {
                        ReferenceBinding[] referenceBindingArrSubstitute = substitute(substitution, ((IntersectionTypeBinding18) typeBinding).getIntersectingTypes());
                        ReferenceBinding[] referenceBindingArr = new ReferenceBinding[referenceBindingArrSubstitute.length];
                        System.arraycopy(referenceBindingArrSubstitute, 0, referenceBindingArr, 0, referenceBindingArrSubstitute.length);
                        return substitution.environment().createIntersectionType18(referenceBindingArr);
                    }
                }
            } else if (typeBinding.isMemberType()) {
                ReferenceBinding referenceBinding4 = (ReferenceBinding) typeBinding.unannotated();
                ReferenceBinding referenceBindingEnclosingType3 = typeBinding.enclosingType();
                if (referenceBindingEnclosingType3 != null) {
                    referenceBinding = (ReferenceBinding) substitute(substitution, referenceBindingEnclosingType3);
                    if (isMemberTypeOfRaw(typeBinding, referenceBinding)) {
                        return substitution.environment().createRawType(referenceBinding4, referenceBinding, typeBinding.getTypeAnnotations());
                    }
                } else {
                    referenceBinding = referenceBindingEnclosingType3;
                }
                if (referenceBinding != referenceBindingEnclosingType3) {
                    if (substitution.isRawSubstitution()) {
                        return substitution.environment().createRawType(referenceBinding4, referenceBinding, typeBinding.getTypeAnnotations());
                    }
                    return substitution.environment().createParameterizedType(referenceBinding4, null, referenceBinding, typeBinding.getTypeAnnotations());
                }
            }
            return typeBinding;
        }

        private static boolean isMemberTypeOfRaw(TypeBinding typeBinding, ReferenceBinding referenceBinding) {
            return referenceBinding != null && referenceBinding.isRawType() && (typeBinding instanceof ReferenceBinding) && !((ReferenceBinding) typeBinding).isStatic();
        }

        public TypeBinding[] substitute(Substitution substitution, TypeBinding[] typeBindingArr) {
            if (typeBindingArr == null) {
                return null;
            }
            int length = typeBindingArr.length;
            TypeBinding[] typeBindingArr2 = typeBindingArr;
            for (int i = 0; i < length; i++) {
                TypeBinding typeBinding = typeBindingArr[i];
                TypeBinding typeBindingSubstitute = substitute(substitution, typeBinding);
                if (typeBindingSubstitute != typeBinding) {
                    if (typeBindingArr2 == typeBindingArr) {
                        typeBindingArr2 = new TypeBinding[length];
                        System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, i);
                    }
                    typeBindingArr2[i] = typeBindingSubstitute;
                } else if (typeBindingArr2 != typeBindingArr) {
                    typeBindingArr2[i] = typeBinding;
                }
            }
            return typeBindingArr2;
        }
    }

    public TypeBinding boxing(TypeBinding typeBinding) {
        return (typeBinding.isBaseType() || typeBinding.kind() == 65540) ? environment().computeBoxingType(typeBinding) : typeBinding;
    }

    public final ClassScope classScope() {
        Scope scope = this;
        while (!(scope instanceof ClassScope)) {
            scope = scope.parent;
            if (scope == null) {
                return null;
            }
        }
        return (ClassScope) scope;
    }

    public final CompilationUnitScope compilationUnitScope() {
        Scope scope = this;
        while (true) {
            Scope scope2 = scope.parent;
            if (scope2 == null) {
                return (CompilationUnitScope) scope;
            }
            scope = scope2;
        }
    }

    public boolean isLambdaSubscope() {
        for (Scope scope = this; scope != null; scope = scope.parent) {
            int i = scope.kind;
            if (i != 1) {
                if (i != 2) {
                    return false;
                }
                return scope.isLambdaScope();
            }
        }
        return false;
    }

    public final CompilerOptions compilerOptions() {
        return compilationUnitScope().environment.globalOptions;
    }

    protected final MethodBinding computeCompatibleMethod(MethodBinding methodBinding, TypeBinding[] typeBindingArr, InvocationSite invocationSite) {
        return computeCompatibleMethod(methodBinding, typeBindingArr, invocationSite, false);
    }

    protected final MethodBinding computeCompatibleMethod(MethodBinding methodBinding, TypeBinding[] typeBindingArr, InvocationSite invocationSite, boolean z) {
        TypeBinding[] typeBindingArr2;
        MethodBinding methodBindingComputeCompatibleMethod = methodBinding;
        TypeBinding[] typeBindingArrGenericTypeArguments = invocationSite.genericTypeArguments();
        TypeBinding[] typeBindingArr3 = methodBindingComputeCompatibleMethod.parameters;
        TypeVariableBinding[] typeVariableBindingArr = methodBindingComputeCompatibleMethod.typeVariables;
        if (typeBindingArr3 == typeBindingArr && (methodBindingComputeCompatibleMethod.returnType.tagBits & TagBits.HasTypeVariable) == 0 && typeBindingArrGenericTypeArguments == null && typeVariableBindingArr == Binding.NO_TYPE_VARIABLES) {
            return methodBindingComputeCompatibleMethod;
        }
        int length = typeBindingArr.length;
        int length2 = typeBindingArr3.length;
        boolean zIsVarargs = methodBinding.isVarargs();
        if (length != length2 && (!zIsVarargs || length < length2 - 1)) {
            return null;
        }
        CompilerOptions compilerOptions = compilerOptions();
        if (typeVariableBindingArr == Binding.NO_TYPE_VARIABLES || compilerOptions.sourceLevel < ClassFileConstants.JDK1_5) {
            if (typeBindingArrGenericTypeArguments != null && compilerOptions.complianceLevel < ClassFileConstants.JDK1_7) {
                if (methodBindingComputeCompatibleMethod instanceof ParameterizedGenericMethodBinding) {
                    if (!((ParameterizedGenericMethodBinding) methodBindingComputeCompatibleMethod).wasInferred) {
                        return new ProblemMethodBinding(methodBindingComputeCompatibleMethod, methodBindingComputeCompatibleMethod.selector, typeBindingArrGenericTypeArguments, 13);
                    }
                } else if (!methodBinding.isOverriding() || !isOverriddenMethodGeneric(methodBinding)) {
                    return new ProblemMethodBinding(methodBindingComputeCompatibleMethod, methodBindingComputeCompatibleMethod.selector, typeBindingArrGenericTypeArguments, 11);
                }
            } else if (typeVariableBindingArr == Binding.NO_TYPE_VARIABLES && (methodBindingComputeCompatibleMethod instanceof ParameterizedGenericMethodBinding) && compilerOptions.sourceLevel >= ClassFileConstants.JDK1_8 && (invocationSite instanceof Invocation) && ((Invocation) invocationSite).getInferenceContext((ParameterizedGenericMethodBinding) methodBindingComputeCompatibleMethod) != null) {
                return methodBindingComputeCompatibleMethod;
            }
            typeBindingArr2 = typeBindingArr;
        } else {
            if (compilerOptions.sourceLevel < ClassFileConstants.JDK1_8 || typeBindingArrGenericTypeArguments != null) {
                int i = 0;
                typeBindingArr2 = null;
                while (i < length) {
                    if (typeBindingArr[i].isBaseType() != (i < length2 ? typeBindingArr3[i] : typeBindingArr3[length2 - 1]).isBaseType()) {
                        if (typeBindingArr2 == null) {
                            typeBindingArr2 = new TypeBinding[length];
                            System.arraycopy(typeBindingArr, 0, typeBindingArr2, 0, length);
                        }
                        typeBindingArr2[i] = environment().computeBoxingType(typeBindingArr[i]);
                    }
                    i++;
                }
            } else {
                typeBindingArr2 = null;
            }
            if (typeBindingArr2 == null) {
                typeBindingArr2 = typeBindingArr;
            }
            methodBindingComputeCompatibleMethod = ParameterizedGenericMethodBinding.computeCompatibleMethod(methodBindingComputeCompatibleMethod, typeBindingArr2, this, invocationSite);
            if (methodBindingComputeCompatibleMethod == null) {
                return null;
            }
            if (!methodBindingComputeCompatibleMethod.isValidBinding()) {
                return methodBindingComputeCompatibleMethod;
            }
            if (compilerOptions.sourceLevel >= ClassFileConstants.JDK1_8 && (methodBindingComputeCompatibleMethod instanceof ParameterizedGenericMethodBinding) && (invocationSite instanceof Invocation) && ((Invocation) invocationSite).getInferenceContext((ParameterizedGenericMethodBinding) methodBindingComputeCompatibleMethod) != null) {
                return methodBindingComputeCompatibleMethod;
            }
        }
        if (parameterCompatibilityLevel(methodBindingComputeCompatibleMethod, typeBindingArr2, (z && CompilerOptions.tolerateIllegalAmbiguousVarargsInvocation && compilerOptions.complianceLevel < ClassFileConstants.JDK1_7) ? false : z) > -1) {
            return (methodBindingComputeCompatibleMethod.tagBits & 4503599627370496L) != 0 ? environment().createPolymorphicMethod(methodBindingComputeCompatibleMethod, typeBindingArr2) : methodBindingComputeCompatibleMethod;
        }
        if (typeBindingArrGenericTypeArguments != null && typeVariableBindingArr != Binding.NO_TYPE_VARIABLES) {
            return new ProblemMethodBinding(methodBindingComputeCompatibleMethod, methodBindingComputeCompatibleMethod.selector, typeBindingArr2, 12);
        }
        if (methodBindingComputeCompatibleMethod instanceof PolyParameterizedGenericMethodBinding) {
            return new ProblemMethodBinding(methodBindingComputeCompatibleMethod, methodBindingComputeCompatibleMethod.selector, methodBindingComputeCompatibleMethod.parameters, 27);
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:103:0x020b  */
    /* JADX WARN: Code duplicated, block: B:106:0x0223  */
    /* JADX WARN: Code duplicated, block: B:107:0x0225  */
    /* JADX WARN: Code duplicated, block: B:110:0x023f A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:114:0x0255  */
    /* JADX WARN: Code duplicated, block: B:115:0x0257  */
    /* JADX WARN: Code duplicated, block: B:137:0x01f5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:138:0x0215 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:69:0x013e  */
    /* JADX WARN: Code duplicated, block: B:72:0x0144  */
    /* JADX WARN: Code duplicated, block: B:74:0x014b  */
    /* JADX WARN: Code duplicated, block: B:75:0x0153  */
    /* JADX WARN: Code duplicated, block: B:78:0x015d  */
    /* JADX WARN: Code duplicated, block: B:79:0x016d  */
    /* JADX WARN: Code duplicated, block: B:83:0x0192  */
    /* JADX WARN: Code duplicated, block: B:85:0x0198 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:86:0x019a  */
    /* JADX WARN: Code duplicated, block: B:88:0x01ad  */
    /* JADX WARN: Code duplicated, block: B:90:0x01b3 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:91:0x01b5  */
    /* JADX WARN: Code duplicated, block: B:92:0x01c3 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:93:0x01c5  */
    /* JADX WARN: Code duplicated, block: B:98:0x01e5  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v20, types: [org.eclipse.jdt.internal.compiler.problem.ProblemReporter] */
    /* JADX WARN: Type inference failed for: r0v23, types: [org.eclipse.jdt.internal.compiler.problem.ProblemReporter] */
    /* JADX WARN: Type inference failed for: r0v33, types: [org.eclipse.jdt.internal.compiler.problem.ProblemReporter] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v43, types: [org.eclipse.jdt.internal.compiler.problem.ProblemReporter] */
    /* JADX WARN: Type inference failed for: r0v5, types: [int] */
    /* JADX WARN: Type inference failed for: r0v50 */
    /* JADX WARN: Type inference failed for: r0v51 */
    /* JADX WARN: Type inference failed for: r10v10, types: [org.eclipse.jdt.internal.compiler.ast.ASTNode] */
    /* JADX WARN: Type inference failed for: r10v13, types: [org.eclipse.jdt.internal.compiler.ast.ASTNode] */
    /* JADX WARN: Type inference failed for: r10v18 */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Type inference failed for: r12v12 */
    /* JADX WARN: Type inference failed for: r12v15 */
    /* JADX WARN: Type inference failed for: r12v2, types: [boolean] */
    /* JADX WARN: Type inference failed for: r12v20 */
    /* JADX WARN: Type inference failed for: r12v21 */
    /* JADX WARN: Type inference failed for: r12v3, types: [int] */
    /* JADX WARN: Type inference failed for: r12v4, types: [int] */
    /* JADX WARN: Type inference failed for: r12v6, types: [boolean] */
    /* JADX WARN: Type inference failed for: r13v10 */
    /* JADX WARN: Type inference failed for: r13v16 */
    /* JADX WARN: Type inference failed for: r13v4 */
    /* JADX WARN: Type inference failed for: r13v5 */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r13v7 */
    /* JADX WARN: Type inference failed for: r13v8, types: [int] */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r14v2 */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r14v5 */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v22 */
    /* JADX WARN: Type inference failed for: r1v3, types: [org.eclipse.jdt.internal.compiler.ast.ASTNode, org.eclipse.jdt.internal.compiler.ast.TypeReference] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r26v0, types: [org.eclipse.jdt.internal.compiler.lookup.Scope] */
    /* JADX WARN: Type inference failed for: r2v9, types: [org.eclipse.jdt.internal.compiler.problem.ProblemReporter] */
    /* JADX WARN: Type inference failed for: r3v10, types: [org.eclipse.jdt.internal.compiler.problem.ProblemReporter] */
    /* JADX WARN: Type inference failed for: r3v14, types: [org.eclipse.jdt.internal.compiler.ast.ASTNode, org.eclipse.jdt.internal.compiler.ast.TypeReference] */
    /* JADX WARN: Type inference failed for: r3v2, types: [org.eclipse.jdt.internal.compiler.problem.ProblemReporter] */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v22 */
    /* JADX WARN: Type inference failed for: r5v15, types: [org.eclipse.jdt.internal.compiler.ast.ASTNode] */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r9v3, types: [org.eclipse.jdt.internal.compiler.ast.TypeReference[]] */
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
    protected boolean connectTypeVariables(TypeParameter[] typeParameterArr, boolean z) {
        TypeBinding typeBindingResolveType;
        ?? r1;
        ?? r8;
        ?? r9;
        int i;
        int i2;
        int i3;
        char c;
        int length;
        ?? r4;
        ?? r12;
        ?? r3;
        TypeBinding typeBindingResolveType2;
        TypeBinding typeBinding;
        int i4;
        int i5;
        boolean zIsValidBinding;
        ?? r13;
        int i6;
        ?? r10;
        ReferenceBinding referenceBinding;
        int length2;
        int i7;
        ReferenceBinding referenceBinding2;
        ?? r11;
        int i8 = 1;
        if (typeParameterArr == null || typeParameterArr.length == 0) {
            return true;
        }
        int i9 = 2;
        HashMap map = new HashMap(2);
        int length3 = typeParameterArr.length;
        boolean z2 = false;
        int i10 = 0;
        while (i10 < length3) {
            HashMap map2 = map;
            int i11 = i9;
            int i12 = length3;
            TypeVariableBinding typeVariableBinding = typeParameterArr[i10].binding;
            if (typeVariableBinding == null) {
                return false;
            }
            typeVariableBinding.setSuperClass(getJavaLangObject());
            typeVariableBinding.setSuperInterfaces(Binding.NO_SUPERINTERFACES);
            typeVariableBinding.setFirstBound(null);
            i10++;
            i9 = i11;
            length3 = i12;
            map = map2;
            i8 = 1;
            z2 = false;
        }
        ?? r14 = i8;
        int i13 = z2 ? 1 : 0;
        int i14 = i8;
        ?? r15 = z2;
        while (i13 < length3) {
            TypeParameter typeParameter = typeParameterArr[i13 == true ? 1 : 0];
            TypeVariableBinding typeVariableBinding2 = typeParameter.binding;
            ?? r2 = typeParameter.type;
            if (r2 == 0) {
                map = map;
                i3 = i9;
                i = length3;
                i2 = i13 == true ? 1 : 0;
            } else {
                if (this.kind == i9) {
                    typeBindingResolveType = r2.resolveType((BlockScope) this, r15, 256);
                } else {
                    typeBindingResolveType = r2.resolveType((ClassScope) this, 256);
                }
                if (typeBindingResolveType == null) {
                    typeVariableBinding2.tagBits |= 131072;
                } else {
                    r2.resolvedType = typeBindingResolveType;
                    int iKind = typeBindingResolveType.kind();
                    if (iKind == 68) {
                        problemReporter().boundCannotBeArray(r2, typeBindingResolveType);
                        typeVariableBinding2.tagBits |= 131072;
                    } else {
                        if (iKind == 4100) {
                            TypeVariableBinding typeVariableBinding3 = (TypeVariableBinding) typeBindingResolveType;
                            if (typeVariableBinding3.rank >= typeVariableBinding2.rank && typeVariableBinding3.declaringElement == typeVariableBinding2.declaringElement && compilerOptions().complianceLevel <= ClassFileConstants.JDK1_6) {
                                problemReporter().forwardTypeVariableReference(typeParameter, typeVariableBinding3);
                                typeVariableBinding2.tagBits |= 131072;
                            } else {
                                if (compilerOptions().complianceLevel > ClassFileConstants.JDK1_6 && typeVariableBinding2.rank >= typeVariableBinding3.rank && typeVariableBinding3.declaringElement == typeVariableBinding2.declaringElement) {
                                    SimpleSet simpleSet = new SimpleSet(typeParameterArr.length);
                                    simpleSet.add(typeVariableBinding2);
                                    ReferenceBinding referenceBinding3 = typeVariableBinding3;
                                    while (true) {
                                        if (referenceBinding3 instanceof TypeVariableBinding) {
                                            if (simpleSet.includes(referenceBinding3)) {
                                                problemReporter().hierarchyCircularity(typeVariableBinding2, typeVariableBinding3, r2);
                                                typeVariableBinding2.tagBits |= 131072;
                                            } else {
                                                simpleSet.add(referenceBinding3);
                                                referenceBinding3 = ((TypeVariableBinding) referenceBinding3).superclass;
                                            }
                                        }
                                    }
                                }
                                r1 = i14;
                            }
                            map = map;
                            r8 = i14;
                        } else {
                            if (((ReferenceBinding) typeBindingResolveType).isFinal()) {
                                problemReporter().finalVariableBound(typeVariableBinding2, r2);
                            }
                            r1 = r15;
                        }
                        ReferenceBinding referenceBinding4 = (ReferenceBinding) typeBindingResolveType;
                        if (!typeBindingResolveType.isInterface()) {
                            typeVariableBinding2.setSuperClass(referenceBinding4);
                        } else {
                            ReferenceBinding[] referenceBindingArr = new ReferenceBinding[i14];
                            referenceBindingArr[r15] = referenceBinding4;
                            typeVariableBinding2.setSuperInterfaces(referenceBindingArr);
                        }
                        map = map;
                        typeVariableBinding2.tagBits |= typeBindingResolveType.tagBits & 2048;
                        typeVariableBinding2.setFirstBound(referenceBinding4);
                        r8 = r1;
                    }
                    r9 = typeParameter.bounds;
                    if (r9 != 0) {
                        length = r9.length;
                        r4 = r15;
                        while (r4 < length) {
                            r3 = r9[r4];
                            if (this.kind == 2) {
                                r12 = r15;
                                typeBindingResolveType2 = r3.resolveType((BlockScope) this, r12);
                            } else {
                                r12 = r15;
                                typeBindingResolveType2 = r3.resolveType((ClassScope) this);
                            }
                            typeBinding = typeBindingResolveType2;
                            if (typeBinding == null) {
                                typeVariableBinding2.tagBits |= 131072;
                                i6 = length;
                                i4 = length3;
                                i5 = i13 == true ? 1 : 0;
                                r13 = r4;
                            } else {
                                i4 = length3;
                                long j = typeVariableBinding2.tagBits;
                                i5 = i13 == true ? 1 : 0;
                                typeVariableBinding2.tagBits = j | (typeBinding.tagBits & 2048);
                                zIsValidBinding = r3.resolvedType.isValidBinding();
                                if (r8 == 0 && r4 == 0) {
                                    problemReporter().noAdditionalBoundAfterTypeVariable(r3);
                                    typeVariableBinding2.tagBits |= 131072;
                                } else {
                                    if (typeBinding.isArrayType()) {
                                        if (zIsValidBinding) {
                                            problemReporter().boundCannotBeArray(r3, typeBinding);
                                            typeVariableBinding2.tagBits |= 131072;
                                        }
                                    } else if (typeBinding.isInterface()) {
                                        if (zIsValidBinding) {
                                            problemReporter().boundMustBeAnInterface(r3, typeBinding);
                                            typeVariableBinding2.tagBits |= 131072;
                                        }
                                    }
                                    r13 = r4;
                                    i6 = length;
                                }
                                if (z || !TypeBinding.equalsEquals(typeVariableBinding2.firstBound, typeVariableBinding2.superclass)) {
                                    r10 = r3;
                                    r13 = r4;
                                    i6 = length;
                                } else {
                                    r11 = r3;
                                    r13 = r4;
                                    i6 = length;
                                    if (hasErasedCandidatesCollisions(typeBinding, typeVariableBinding2.superclass, map, typeVariableBinding2, r11)) {
                                        r10 = r11;
                                        r13 = r13;
                                    }
                                }
                                r10 = r11;
                                r13 = r13;
                                referenceBinding = (ReferenceBinding) typeBinding;
                                length2 = typeVariableBinding2.superInterfaces.length;
                                while (true) {
                                    i7 = length2 - 1;
                                    if (i7 >= 0) {
                                        referenceBinding2 = typeVariableBinding2.superInterfaces[i7];
                                        if (TypeBinding.equalsEquals(referenceBinding2, referenceBinding)) {
                                            problemReporter().duplicateBounds(r10, typeBinding);
                                            typeVariableBinding2.tagBits |= 131072;
                                            break;
                                        }
                                        if (!z && hasErasedCandidatesCollisions(typeBinding, referenceBinding2, map, typeVariableBinding2, r10)) {
                                            break;
                                        }
                                        length2 = i7;
                                        referenceBinding = referenceBinding;
                                    } else {
                                        int length4 = typeVariableBinding2.superInterfaces.length;
                                        System.arraycopy(typeVariableBinding2.superInterfaces, 0, typeVariableBinding2.setSuperInterfaces(new ReferenceBinding[length4 + 1]), 0, length4);
                                        typeVariableBinding2.superInterfaces[length4] = referenceBinding;
                                        break;
                                    }
                                }
                            }
                            int i15 = r13 + 1;
                            length3 = i4;
                            i13 = i5;
                            length = i6;
                            r12 = 0;
                            r4 = i15;
                        }
                        r12 = r15;
                    }
                    i = length3;
                    i2 = i13;
                    i3 = 2;
                    if ((typeVariableBinding2.tagBits & 131072) == 0) {
                        c = 1;
                    } else {
                        c = 0;
                    }
                    r14 = (r14 == true ? 1 : 0) & c;
                }
                r8 = r15;
                r9 = typeParameter.bounds;
                if (r9 != 0) {
                    length = r9.length;
                    r4 = r15;
                    while (r4 < length) {
                        r3 = r9[r4];
                        if (this.kind == 2) {
                            r12 = r15;
                            typeBindingResolveType2 = r3.resolveType((BlockScope) this, r12);
                        } else {
                            r12 = r15;
                            typeBindingResolveType2 = r3.resolveType((ClassScope) this);
                        }
                        typeBinding = typeBindingResolveType2;
                        if (typeBinding == null) {
                            typeVariableBinding2.tagBits |= 131072;
                            i6 = length;
                            i4 = length3;
                            i5 = i13 == true ? 1 : 0;
                            r13 = r4;
                        } else {
                            i4 = length3;
                            long j2 = typeVariableBinding2.tagBits;
                            i5 = i13 == true ? 1 : 0;
                            typeVariableBinding2.tagBits = j2 | (typeBinding.tagBits & 2048);
                            zIsValidBinding = r3.resolvedType.isValidBinding();
                            if (r8 == 0) {
                                if (typeBinding.isArrayType()) {
                                    if (zIsValidBinding) {
                                        problemReporter().boundCannotBeArray(r3, typeBinding);
                                        typeVariableBinding2.tagBits |= 131072;
                                    }
                                } else if (typeBinding.isInterface()) {
                                    if (z) {
                                        r10 = r3;
                                        r13 = r4;
                                        i6 = length;
                                        r10 = r11;
                                        r13 = r13;
                                        referenceBinding = (ReferenceBinding) typeBinding;
                                        length2 = typeVariableBinding2.superInterfaces.length;
                                        while (true) {
                                            i7 = length2 - 1;
                                            if (i7 >= 0) {
                                                referenceBinding2 = typeVariableBinding2.superInterfaces[i7];
                                                if (TypeBinding.equalsEquals(referenceBinding2, referenceBinding)) {
                                                    problemReporter().duplicateBounds(r10, typeBinding);
                                                    typeVariableBinding2.tagBits |= 131072;
                                                    break;
                                                    break;
                                                }
                                                if (!z) {
                                                    break;
                                                    break;
                                                }
                                                length2 = i7;
                                                referenceBinding = referenceBinding;
                                            } else {
                                                int length5 = typeVariableBinding2.superInterfaces.length;
                                                System.arraycopy(typeVariableBinding2.superInterfaces, 0, typeVariableBinding2.setSuperInterfaces(new ReferenceBinding[length5 + 1]), 0, length5);
                                                typeVariableBinding2.superInterfaces[length5] = referenceBinding;
                                                break;
                                                break;
                                            }
                                        }
                                    } else {
                                        r10 = r3;
                                        r13 = r4;
                                        i6 = length;
                                        r10 = r11;
                                        r13 = r13;
                                        referenceBinding = (ReferenceBinding) typeBinding;
                                        length2 = typeVariableBinding2.superInterfaces.length;
                                        while (true) {
                                            i7 = length2 - 1;
                                            if (i7 >= 0) {
                                                referenceBinding2 = typeVariableBinding2.superInterfaces[i7];
                                                if (TypeBinding.equalsEquals(referenceBinding2, referenceBinding)) {
                                                    problemReporter().duplicateBounds(r10, typeBinding);
                                                    typeVariableBinding2.tagBits |= 131072;
                                                    break;
                                                    break;
                                                }
                                                if (!z) {
                                                    break;
                                                    break;
                                                }
                                                length2 = i7;
                                                referenceBinding = referenceBinding;
                                            } else {
                                                int length6 = typeVariableBinding2.superInterfaces.length;
                                                System.arraycopy(typeVariableBinding2.superInterfaces, 0, typeVariableBinding2.setSuperInterfaces(new ReferenceBinding[length6 + 1]), 0, length6);
                                                typeVariableBinding2.superInterfaces[length6] = referenceBinding;
                                                break;
                                                break;
                                            }
                                        }
                                    }
                                } else if (zIsValidBinding) {
                                    problemReporter().boundMustBeAnInterface(r3, typeBinding);
                                    typeVariableBinding2.tagBits |= 131072;
                                }
                                r13 = r4;
                                i6 = length;
                            } else {
                                if (typeBinding.isArrayType()) {
                                    if (zIsValidBinding) {
                                        problemReporter().boundCannotBeArray(r3, typeBinding);
                                        typeVariableBinding2.tagBits |= 131072;
                                    }
                                } else if (typeBinding.isInterface()) {
                                    if (zIsValidBinding) {
                                        problemReporter().boundMustBeAnInterface(r3, typeBinding);
                                        typeVariableBinding2.tagBits |= 131072;
                                    }
                                } else if (z) {
                                    r10 = r3;
                                    r13 = r4;
                                    i6 = length;
                                    r10 = r11;
                                    r13 = r13;
                                    referenceBinding = (ReferenceBinding) typeBinding;
                                    length2 = typeVariableBinding2.superInterfaces.length;
                                    while (true) {
                                        i7 = length2 - 1;
                                        if (i7 >= 0) {
                                            referenceBinding2 = typeVariableBinding2.superInterfaces[i7];
                                            if (TypeBinding.equalsEquals(referenceBinding2, referenceBinding)) {
                                                problemReporter().duplicateBounds(r10, typeBinding);
                                                typeVariableBinding2.tagBits |= 131072;
                                                break;
                                                break;
                                            }
                                            if (!z) {
                                                break;
                                                break;
                                            }
                                            length2 = i7;
                                            referenceBinding = referenceBinding;
                                        } else {
                                            int length7 = typeVariableBinding2.superInterfaces.length;
                                            System.arraycopy(typeVariableBinding2.superInterfaces, 0, typeVariableBinding2.setSuperInterfaces(new ReferenceBinding[length7 + 1]), 0, length7);
                                            typeVariableBinding2.superInterfaces[length7] = referenceBinding;
                                            break;
                                            break;
                                        }
                                    }
                                } else {
                                    r10 = r3;
                                    r13 = r4;
                                    i6 = length;
                                    r10 = r11;
                                    r13 = r13;
                                    referenceBinding = (ReferenceBinding) typeBinding;
                                    length2 = typeVariableBinding2.superInterfaces.length;
                                    while (true) {
                                        i7 = length2 - 1;
                                        if (i7 >= 0) {
                                            referenceBinding2 = typeVariableBinding2.superInterfaces[i7];
                                            if (TypeBinding.equalsEquals(referenceBinding2, referenceBinding)) {
                                                problemReporter().duplicateBounds(r10, typeBinding);
                                                typeVariableBinding2.tagBits |= 131072;
                                                break;
                                                break;
                                            }
                                            if (!z) {
                                                break;
                                                break;
                                            }
                                            length2 = i7;
                                            referenceBinding = referenceBinding;
                                        } else {
                                            int length8 = typeVariableBinding2.superInterfaces.length;
                                            System.arraycopy(typeVariableBinding2.superInterfaces, 0, typeVariableBinding2.setSuperInterfaces(new ReferenceBinding[length8 + 1]), 0, length8);
                                            typeVariableBinding2.superInterfaces[length8] = referenceBinding;
                                            break;
                                            break;
                                        }
                                    }
                                }
                                r13 = r4;
                                i6 = length;
                            }
                        }
                        int i16 = r13 + 1;
                        length3 = i4;
                        i13 = i5;
                        length = i6;
                        r12 = 0;
                        r4 = i16;
                    }
                    r12 = r15;
                }
                i = length3;
                i2 = i13;
                i3 = 2;
                if ((typeVariableBinding2.tagBits & 131072) == 0) {
                    c = 1;
                } else {
                    c = 0;
                }
                r14 = (r14 == true ? 1 : 0) & c;
            }
            i13 = i2 + 1;
            i9 = i3;
            length3 = i;
            map = map;
            i14 = 1;
            r15 = 0;
            r14 = r14;
        }
        ?? r0 = r15;
        ?? r5 = r0;
        ?? r6 = r0;
        while (r6 < length3) {
            resolveTypeParameter(typeParameterArr[r6]);
            int i17 = r5 | (typeParameterArr[r6].binding.hasNullTypeAnnotations() ? 1 : 0);
            r6++;
            r5 = i17;
        }
        if (r5 != 0) {
            while (r15 < length3) {
                typeParameterArr[r15].binding.updateTagBits();
                r15++;
            }
        }
        return r14;
    }

    public ArrayBinding createArrayType(TypeBinding typeBinding, int i) {
        return createArrayType(typeBinding, i, Binding.NO_ANNOTATIONS);
    }

    public ArrayBinding createArrayType(TypeBinding typeBinding, int i, AnnotationBinding[] annotationBindingArr) {
        if (typeBinding.isValidBinding()) {
            return environment().createArrayType(typeBinding, i, annotationBindingArr);
        }
        return new ArrayBinding(typeBinding, i, environment());
    }

    public TypeVariableBinding[] createTypeVariables(TypeParameter[] typeParameterArr, Binding binding) {
        TypeDeclaration typeDeclaration;
        AbstractMethodDeclaration abstractMethodDeclarationSourceMethod;
        if (typeParameterArr == null || typeParameterArr.length == 0) {
            return Binding.NO_TYPE_VARIABLES;
        }
        PackageBinding packageBinding = compilationUnitScope().fPackage;
        int length = typeParameterArr.length;
        TypeVariableBinding[] typeVariableBindingArr = new TypeVariableBinding[length];
        int i = 0;
        int i2 = 0;
        while (i < length) {
            TypeParameter typeParameter = typeParameterArr[i];
            TypeVariableBinding typeVariableBinding = new TypeVariableBinding(typeParameter.f617name, binding, i, environment());
            typeVariableBinding.fPackage = packageBinding;
            typeParameter.binding = typeVariableBinding;
            if ((typeParameter.bits & 1048576) != 0) {
                int iKind = binding.kind();
                if (iKind != 4) {
                    if (iKind == 8 && (abstractMethodDeclarationSourceMethod = ((MethodBinding) binding).sourceMethod()) != null) {
                        abstractMethodDeclarationSourceMethod.bits = 1048576 | abstractMethodDeclarationSourceMethod.bits;
                    }
                } else if ((binding instanceof SourceTypeBinding) && (typeDeclaration = ((SourceTypeBinding) binding).scope.referenceContext) != null) {
                    typeDeclaration.bits = 1048576 | typeDeclaration.bits;
                }
            }
            for (int i3 = 0; i3 < i2; i3++) {
                if (CharOperation.equals(typeVariableBindingArr[i3].sourceName, typeParameter.f617name)) {
                    problemReporter().duplicateTypeParameterInType(typeParameter);
                }
            }
            typeVariableBindingArr[i2] = typeVariableBinding;
            i++;
            i2++;
        }
        if (i2 == length) {
            return typeVariableBindingArr;
        }
        TypeVariableBinding[] typeVariableBindingArr2 = new TypeVariableBinding[i2];
        System.arraycopy(typeVariableBindingArr, 0, typeVariableBindingArr2, 0, i2);
        return typeVariableBindingArr2;
    }

    public final ClassScope enclosingClassScope() {
        Scope scope = this;
        do {
            scope = scope.parent;
            if (scope == null) {
                return null;
            }
        } while (!(scope instanceof ClassScope));
        return (ClassScope) scope;
    }

    public final MethodScope enclosingMethodScope() {
        Scope scope = this;
        do {
            scope = scope.parent;
            if (scope == null) {
                return null;
            }
        } while (!(scope instanceof MethodScope));
        return (MethodScope) scope;
    }

    public final MethodScope enclosingLambdaScope() {
        Scope scope = this;
        while (true) {
            scope = scope.parent;
            if (scope == null) {
                return null;
            }
            if (scope instanceof MethodScope) {
                MethodScope methodScope = (MethodScope) scope;
                if (methodScope.referenceContext instanceof LambdaExpression) {
                    return methodScope;
                }
            }
        }
    }

    public final ReferenceBinding enclosingReceiverType() {
        Scope scope = this;
        while (!(scope instanceof ClassScope)) {
            scope = scope.parent;
            if (scope == null) {
                return null;
            }
        }
        return environment().convertToParameterizedType(((ClassScope) scope).referenceContext.binding);
    }

    public ReferenceContext enclosingReferenceContext() {
        int i;
        Scope scope = this;
        do {
            scope = scope.parent;
            if (scope == null) {
                return null;
            }
            i = scope.kind;
            if (i == 2) {
                return ((MethodScope) scope).referenceContext;
            }
            if (i == 3) {
                return ((ClassScope) scope).referenceContext;
            }
        } while (i != 4);
        return ((CompilationUnitScope) scope).referenceContext;
    }

    public final SourceTypeBinding enclosingSourceType() {
        Scope scope = this;
        while (!(scope instanceof ClassScope)) {
            scope = scope.parent;
            if (scope == null) {
                return null;
            }
        }
        return ((ClassScope) scope).referenceContext.binding;
    }

    public final LookupEnvironment environment() {
        Scope scope = this;
        while (true) {
            Scope scope2 = scope.parent;
            if (scope2 == null) {
                return ((CompilationUnitScope) scope).environment;
            }
            scope = scope2;
        }
    }

    protected MethodBinding findDefaultAbstractMethod(ReferenceBinding referenceBinding, char[] cArr, TypeBinding[] typeBindingArr, InvocationSite invocationSite, ReferenceBinding referenceBinding2, ObjectVector objectVector, MethodBinding[] methodBindingArr) {
        int i;
        int i2 = objectVector.size;
        int i3 = 0;
        boolean z = compilerOptions().sourceLevel >= ClassFileConstants.JDK1_8;
        ArrayList arrayList = new ArrayList();
        for (ReferenceBinding referenceBindingSuperclass = referenceBinding2; referenceBindingSuperclass != null; referenceBindingSuperclass = referenceBindingSuperclass.superclass()) {
            findMethodInSuperInterfaces(referenceBindingSuperclass, cArr, objectVector, arrayList, invocationSite);
        }
        int length = methodBindingArr == null ? 0 : methodBindingArr.length;
        int i4 = objectVector.size;
        MethodBinding[] methodBindingArr2 = new MethodBinding[(i4 - i2) + length];
        if (methodBindingArr != null) {
            System.arraycopy(methodBindingArr, 0, methodBindingArr2, 0, length);
        }
        MethodBinding methodBinding = null;
        if (i4 > i2) {
            MethodVerifier methodVerifier = environment().methodVerifier();
            int i5 = i2;
            while (i5 < i4) {
                MethodBinding methodBindingComputeCompatibleMethod = computeCompatibleMethod((MethodBinding) objectVector.elementAt(i5), typeBindingArr, invocationSite);
                if (methodBindingComputeCompatibleMethod == null) {
                    i = i4;
                } else if (methodBindingComputeCompatibleMethod.isValidBinding()) {
                    if (methodBindingArr != null) {
                        int length2 = methodBindingArr.length;
                        while (i3 < length2) {
                            methodVerifier.areMethodsCompatible(methodBindingArr[i3], methodBindingComputeCompatibleMethod);
                            i3++;
                            i4 = i4;
                        }
                    }
                    i = i4;
                    if (z || !methodBindingComputeCompatibleMethod.isVarargs() || !(methodBindingComputeCompatibleMethod instanceof ParameterizedGenericMethodBinding)) {
                        int i6 = 0;
                        while (true) {
                            if (i6 < i2) {
                                MethodBinding methodBinding2 = (MethodBinding) objectVector.elementAt(i6);
                                if (methodBinding2 != null && methodVerifier.areMethodsCompatible(methodBinding2, methodBindingComputeCompatibleMethod)) {
                                    break;
                                }
                                i6++;
                            } else {
                                methodBindingArr2[length] = methodBindingComputeCompatibleMethod;
                                length++;
                                break;
                            }
                        }
                    } else {
                        methodBindingArr2[length] = methodBindingComputeCompatibleMethod;
                        length++;
                        break;
                        break;
                    }
                } else {
                    i = i4;
                    if (methodBinding == null) {
                        methodBinding = methodBindingComputeCompatibleMethod;
                    }
                }
                i5++;
                i4 = i;
                i3 = 0;
            }
        }
        MethodBinding methodBinding3 = methodBinding;
        int i7 = length;
        if (i7 >= 2) {
            if (compilerOptions().complianceLevel >= ClassFileConstants.JDK1_4) {
                return mostSpecificMethodBinding(methodBindingArr2, i7, typeBindingArr, invocationSite, referenceBinding);
            }
            return mostSpecificInterfaceMethodBinding(methodBindingArr2, i7, invocationSite);
        }
        if (methodBindingArr == null && i7 == 0) {
            return methodBinding3;
        }
        MethodBinding methodBinding4 = methodBindingArr2[0];
        if (methodBinding4 != null) {
            compilationUnitScope().recordTypeReferences(methodBinding4.thrownExceptions);
        }
        return methodBinding4;
    }

    public ReferenceBinding findDirectMemberType(char[] cArr, ReferenceBinding referenceBinding) {
        TypeDeclaration[] typeDeclarationArr;
        if ((referenceBinding.tagBits & 65536) != 0) {
            return null;
        }
        ReferenceBinding referenceBindingEnclosingReceiverType = enclosingReceiverType();
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordReference(referenceBinding, cArr);
        ReferenceBinding memberType = referenceBinding.getMemberType(cArr);
        if (memberType == null) {
            return null;
        }
        compilationUnitScope.recordTypeReference(memberType);
        if (referenceBindingEnclosingReceiverType == null) {
            if (memberType.canBeSeenBy(getCurrentPackage())) {
                return memberType;
            }
            if ((this instanceof CompilationUnitScope) && (typeDeclarationArr = ((CompilationUnitScope) this).referenceContext.types) != null) {
                for (TypeDeclaration typeDeclaration : typeDeclarationArr) {
                    if (memberType.canBeSeenBy(referenceBinding, typeDeclaration.binding)) {
                        return memberType;
                    }
                }
            }
        } else if (memberType.canBeSeenBy(referenceBinding, referenceBindingEnclosingReceiverType)) {
            return memberType;
        }
        return new ProblemReferenceBinding(new char[][]{cArr}, memberType, 2);
    }

    public MethodBinding findExactMethod(ReferenceBinding referenceBinding, char[] cArr, TypeBinding[] typeBindingArr, InvocationSite invocationSite) {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordTypeReferences(typeBindingArr);
        MethodBinding exactMethod = referenceBinding.getExactMethod(cArr, typeBindingArr, compilationUnitScope);
        if (exactMethod != null && exactMethod.typeVariables == Binding.NO_TYPE_VARIABLES && !exactMethod.isBridge()) {
            if (compilerOptions().sourceLevel >= ClassFileConstants.JDK1_5) {
                int length = typeBindingArr.length;
                while (true) {
                    length--;
                    if (length < 0) {
                        break;
                    }
                    TypeBinding typeBindingLeafComponentType = typeBindingArr[length].leafComponentType();
                    if (typeBindingLeafComponentType instanceof ReferenceBinding) {
                        ReferenceBinding referenceBinding2 = (ReferenceBinding) typeBindingLeafComponentType;
                        if (referenceBinding2.isHierarchyConnected()) {
                            if (isSubtypeOfRawType(referenceBinding2)) {
                                return null;
                            }
                        } else if (referenceBinding2.isRawType()) {
                            return null;
                        }
                    }
                }
            }
            compilationUnitScope.recordTypeReferences(exactMethod.thrownExceptions);
            if ((!exactMethod.isAbstract() || exactMethod.thrownExceptions == Binding.NO_EXCEPTIONS) && exactMethod.canBeSeenBy(referenceBinding, invocationSite, this)) {
                if (typeBindingArr == Binding.NO_PARAMETERS && CharOperation.equals(cArr, TypeConstants.GETCLASS) && exactMethod.returnType.isParameterizedType()) {
                    return environment().createGetClassMethod(referenceBinding, exactMethod, this);
                }
                if (invocationSite.genericTypeArguments() != null) {
                    return computeCompatibleMethod(exactMethod, typeBindingArr, invocationSite);
                }
                return (exactMethod.tagBits & 4503599627370496L) != 0 ? environment().createPolymorphicMethod(exactMethod, typeBindingArr) : exactMethod;
            }
        }
        return null;
    }

    public FieldBinding findField(TypeBinding typeBinding, char[] cArr, InvocationSite invocationSite, boolean z) {
        return findField(typeBinding, cArr, invocationSite, z, false);
    }

    public FieldBinding findField(TypeBinding typeBinding, char[] cArr, InvocationSite invocationSite, boolean z, boolean z2) {
        TypeBinding typeBindingLeafComponentType;
        ProblemFieldBinding problemFieldBinding;
        int i;
        ReferenceBinding[] referenceBindingArr;
        boolean z3;
        ReferenceBinding[] referenceBindingArr2;
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordTypeReference(typeBinding);
        int iKind = typeBinding.kind();
        if (iKind != 68) {
            if (iKind == 132) {
                return null;
            }
            if (iKind == 516 || iKind == 4100 || iKind == 8196) {
                TypeBinding typeBindingErasure = typeBinding.erasure();
                if (typeBindingErasure.isArrayType()) {
                    typeBindingLeafComponentType = typeBindingErasure.leafComponentType();
                }
            }
            ReferenceBinding referenceBindingSuperclass = (ReferenceBinding) typeBinding;
            if (!referenceBindingSuperclass.canBeSeenBy(this)) {
                return new ProblemFieldBinding(referenceBindingSuperclass, cArr, 8);
            }
            referenceBindingSuperclass.initializeForStaticImports();
            FieldBinding field = referenceBindingSuperclass.getField(cArr, z);
            boolean z4 = (this instanceof MethodScope) && ((MethodScope) this).insideTypeAnnotation;
            if (field != null) {
                if (z2) {
                    return field;
                }
                return (invocationSite == null || z4 ? !field.canBeSeenBy(getCurrentPackage()) : !field.canBeSeenBy(referenceBindingSuperclass, invocationSite, this)) ? new ProblemFieldBinding(field, field.declaringClass, cArr, 2) : field;
            }
            boolean z5 = true;
            ReferenceBinding[] referenceBindingArr3 = null;
            int length = 0;
            FieldBinding fieldBinding = null;
            FieldBinding fieldBinding2 = null;
            while (z5) {
                ReferenceBinding[] referenceBindingArrSuperInterfaces = referenceBindingSuperclass.superInterfaces();
                if (referenceBindingArrSuperInterfaces == null || referenceBindingArrSuperInterfaces == Binding.NO_SUPERINTERFACES) {
                    z3 = z5;
                } else if (referenceBindingArr3 == null) {
                    z3 = z5;
                    length = referenceBindingArrSuperInterfaces.length;
                    referenceBindingArr3 = referenceBindingArrSuperInterfaces;
                } else {
                    int length2 = referenceBindingArrSuperInterfaces.length;
                    int i2 = length + length2;
                    if (i2 >= referenceBindingArr3.length) {
                        referenceBindingArr2 = new ReferenceBinding[i2 + 5];
                        System.arraycopy(referenceBindingArr3, 0, referenceBindingArr2, 0, length);
                    } else {
                        referenceBindingArr2 = referenceBindingArr3;
                    }
                    int i3 = length;
                    int i4 = 0;
                    while (i4 < length2) {
                        ReferenceBinding referenceBinding = referenceBindingArrSuperInterfaces[i4];
                        boolean z6 = z5;
                        int i5 = 0;
                        while (true) {
                            if (i5 < i3) {
                                int i6 = i3;
                                if (TypeBinding.equalsEquals(referenceBinding, referenceBindingArr2[i5])) {
                                    i3 = i6;
                                    break;
                                }
                                i5++;
                                i3 = i6;
                            } else {
                                referenceBindingArr2[i3] = referenceBinding;
                                i3++;
                                break;
                            }
                        }
                        i4++;
                        z5 = z6;
                    }
                    z3 = z5;
                    length = i3;
                    referenceBindingArr3 = referenceBindingArr2;
                }
                referenceBindingSuperclass = referenceBindingSuperclass.superclass();
                if (referenceBindingSuperclass == null) {
                    break;
                }
                z5 = false;
                compilationUnitScope.recordTypeReference(referenceBindingSuperclass);
                referenceBindingSuperclass.initializeForStaticImports();
                referenceBindingSuperclass = (ReferenceBinding) referenceBindingSuperclass.capture(this, invocationSite == null ? 0 : invocationSite.sourceStart(), invocationSite == null ? 0 : invocationSite.sourceEnd());
                FieldBinding field2 = referenceBindingSuperclass.getField(cArr, z);
                if (field2 == null) {
                    z5 = z3;
                } else {
                    if (z2) {
                        return field2;
                    }
                    if (field2.canBeSeenBy(typeBinding, invocationSite, this)) {
                        if (fieldBinding != null) {
                            return new ProblemFieldBinding(fieldBinding, fieldBinding.declaringClass, cArr, 3);
                        }
                        fieldBinding = field2;
                    } else if (fieldBinding2 == null) {
                        fieldBinding2 = field2;
                    }
                }
            }
            if (referenceBindingArr3 != null) {
                int i7 = 0;
                while (true) {
                    if (i7 >= length) {
                        problemFieldBinding = null;
                        break;
                    }
                    ReferenceBinding referenceBinding2 = referenceBindingArr3[i7];
                    compilationUnitScope.recordTypeReference(referenceBinding2);
                    FieldBinding field3 = referenceBinding2.getField(cArr, true);
                    if (field3 == null) {
                        ReferenceBinding[] referenceBindingArrSuperInterfaces2 = referenceBinding2.superInterfaces();
                        if (referenceBindingArrSuperInterfaces2 != null && referenceBindingArrSuperInterfaces2 != Binding.NO_SUPERINTERFACES) {
                            int length3 = referenceBindingArrSuperInterfaces2.length;
                            int i8 = length + length3;
                            if (i8 >= referenceBindingArr3.length) {
                                referenceBindingArr = new ReferenceBinding[i8 + 5];
                                i = 0;
                                System.arraycopy(referenceBindingArr3, 0, referenceBindingArr, 0, length);
                            } else {
                                i = 0;
                                referenceBindingArr = referenceBindingArr3;
                            }
                            int i9 = length;
                            for (int i10 = i; i10 < length3; i10++) {
                                ReferenceBinding referenceBinding3 = referenceBindingArrSuperInterfaces2[i10];
                                int i11 = i;
                                while (true) {
                                    if (i11 < i9) {
                                        if (TypeBinding.equalsEquals(referenceBinding3, referenceBindingArr[i11])) {
                                            break;
                                        }
                                        i11++;
                                    } else {
                                        referenceBindingArr[i9] = referenceBinding3;
                                        i9++;
                                        break;
                                    }
                                }
                            }
                            referenceBindingArr3 = referenceBindingArr;
                            length = i9;
                        }
                        i7++;
                    } else {
                        if (z2) {
                            return field3;
                        }
                        if (fieldBinding != null) {
                            problemFieldBinding = new ProblemFieldBinding(fieldBinding, fieldBinding.declaringClass, cArr, 3);
                            break;
                        }
                        fieldBinding = field3;
                    }
                    i7++;
                }
                if (problemFieldBinding != null) {
                    return problemFieldBinding;
                }
            }
            if (fieldBinding != null) {
                return fieldBinding;
            }
            if (fieldBinding2 != null) {
                return new ProblemFieldBinding(fieldBinding2, referenceBindingSuperclass, cArr, 2);
            }
            return null;
        }
        typeBindingLeafComponentType = typeBinding.leafComponentType();
        if (typeBindingLeafComponentType instanceof ReferenceBinding) {
            ReferenceBinding referenceBinding4 = (ReferenceBinding) typeBindingLeafComponentType;
            if (!referenceBinding4.canBeSeenBy(this)) {
                return new ProblemFieldBinding(referenceBinding4, cArr, 8);
            }
        }
        if (!CharOperation.equals(cArr, TypeConstants.LENGTH)) {
            return null;
        }
        if ((typeBindingLeafComponentType.tagBits & 128) != 0) {
            return new ProblemFieldBinding(ArrayBinding.ArrayLength, null, cArr, 1);
        }
        return ArrayBinding.ArrayLength;
    }

    public ReferenceBinding findMemberType(char[] cArr, ReferenceBinding referenceBinding) {
        ProblemReferenceBinding problemReferenceBinding;
        int i;
        ReferenceBinding[] referenceBindingArr;
        boolean z;
        ReferenceBinding[] referenceBindingArr2;
        boolean z2;
        ReferenceBinding referenceBinding2 = null;
        if ((referenceBinding.tagBits & 65536) != 0) {
            return null;
        }
        SourceTypeBinding sourceTypeBindingEnclosingSourceType = enclosingSourceType();
        PackageBinding currentPackage = getCurrentPackage();
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordReference(referenceBinding, cArr);
        ReferenceBinding memberType = referenceBinding.getMemberType(cArr);
        if (memberType != null) {
            compilationUnitScope.recordTypeReference(memberType);
            return (sourceTypeBindingEnclosingSourceType == null || (this.parent == compilationUnitScope && (sourceTypeBindingEnclosingSourceType.tagBits & 262144) == 0) ? !memberType.canBeSeenBy(currentPackage) : !memberType.canBeSeenBy(referenceBinding, sourceTypeBindingEnclosingSourceType)) ? new ProblemReferenceBinding(new char[][]{cArr}, memberType, 2) : memberType;
        }
        boolean z3 = true;
        ReferenceBinding referenceBindingSuperclass = referenceBinding;
        ReferenceBinding[] referenceBindingArr3 = null;
        ReferenceBinding referenceBinding3 = null;
        ReferenceBinding referenceBinding4 = null;
        int length = 0;
        while (z3) {
            ReferenceBinding[] referenceBindingArrSuperInterfaces = referenceBindingSuperclass.superInterfaces();
            if (referenceBindingArrSuperInterfaces == null) {
                ReferenceBinding referenceBindingGenericType = referenceBindingSuperclass.isParameterizedType() ? ((ParameterizedTypeBinding) referenceBindingSuperclass).genericType() : referenceBindingSuperclass;
                if (referenceBindingGenericType.isHierarchyBeingConnected()) {
                    return referenceBinding2;
                }
                ((SourceTypeBinding) referenceBindingGenericType).scope.connectTypeHierarchy();
                referenceBindingArrSuperInterfaces = referenceBindingSuperclass.superInterfaces();
            }
            ReferenceBinding[] referenceBindingArr4 = referenceBindingArrSuperInterfaces;
            if (referenceBindingArr4 == null || referenceBindingArr4 == Binding.NO_SUPERINTERFACES) {
                z = z3;
            } else if (referenceBindingArr3 == null) {
                referenceBindingArr3 = referenceBindingArr4;
                z = z3;
                length = referenceBindingArr4.length;
            } else {
                int length2 = referenceBindingArr4.length;
                int i2 = length + length2;
                if (i2 >= referenceBindingArr3.length) {
                    referenceBindingArr2 = new ReferenceBinding[i2 + 5];
                    System.arraycopy(referenceBindingArr3, 0, referenceBindingArr2, 0, length);
                } else {
                    referenceBindingArr2 = referenceBindingArr3;
                }
                int i3 = length;
                int i4 = 0;
                while (i4 < length2) {
                    ReferenceBinding referenceBinding5 = referenceBindingArr4[i4];
                    ReferenceBinding[] referenceBindingArr5 = referenceBindingArr4;
                    int i5 = 0;
                    while (true) {
                        if (i5 < i3) {
                            z2 = z3;
                            if (TypeBinding.equalsEquals(referenceBinding5, referenceBindingArr2[i5])) {
                                break;
                            }
                            i5++;
                            z3 = z2;
                        } else {
                            referenceBindingArr2[i3] = referenceBinding5;
                            i3++;
                            z2 = z3;
                            break;
                        }
                    }
                    i4++;
                    referenceBindingArr4 = referenceBindingArr5;
                    z3 = z2;
                }
                z = z3;
                length = i3;
                referenceBindingArr3 = referenceBindingArr2;
            }
            referenceBindingSuperclass = referenceBindingSuperclass.superclass();
            if (referenceBindingSuperclass == null) {
                break;
            }
            z3 = false;
            referenceBinding2 = null;
            compilationUnitScope.recordReference(referenceBindingSuperclass, cArr);
            ReferenceBinding memberType2 = referenceBindingSuperclass.getMemberType(cArr);
            if (memberType2 != null) {
                compilationUnitScope.recordTypeReference(memberType2);
                if (sourceTypeBindingEnclosingSourceType != null ? !memberType2.canBeSeenBy(referenceBinding, sourceTypeBindingEnclosingSourceType) : !memberType2.canBeSeenBy(currentPackage)) {
                    referenceBinding4 = memberType2;
                } else {
                    if (referenceBinding3 != null) {
                        return new ProblemReferenceBinding(new char[][]{cArr}, referenceBinding3, 3);
                    }
                    referenceBinding3 = memberType2;
                }
            } else {
                z3 = z;
            }
        }
        if (referenceBindingArr3 != null) {
            int i6 = 0;
            while (true) {
                if (i6 >= length) {
                    problemReferenceBinding = null;
                    break;
                }
                ReferenceBinding referenceBinding6 = referenceBindingArr3[i6];
                compilationUnitScope.recordReference(referenceBinding6, cArr);
                ReferenceBinding memberType3 = referenceBinding6.getMemberType(cArr);
                if (memberType3 != null) {
                    compilationUnitScope.recordTypeReference(memberType3);
                    if (referenceBinding3 != null) {
                        problemReferenceBinding = new ProblemReferenceBinding(new char[][]{cArr}, referenceBinding3, 3);
                        break;
                    }
                    referenceBinding3 = memberType3;
                } else {
                    ReferenceBinding[] referenceBindingArrSuperInterfaces2 = referenceBinding6.superInterfaces();
                    if (referenceBindingArrSuperInterfaces2 != null && referenceBindingArrSuperInterfaces2 != Binding.NO_SUPERINTERFACES) {
                        int length3 = referenceBindingArrSuperInterfaces2.length;
                        int i7 = length + length3;
                        if (i7 >= referenceBindingArr3.length) {
                            referenceBindingArr = new ReferenceBinding[i7 + 5];
                            i = 0;
                            System.arraycopy(referenceBindingArr3, 0, referenceBindingArr, 0, length);
                        } else {
                            i = 0;
                            referenceBindingArr = referenceBindingArr3;
                        }
                        int i8 = length;
                        for (int i9 = i; i9 < length3; i9++) {
                            ReferenceBinding referenceBinding7 = referenceBindingArrSuperInterfaces2[i9];
                            int i10 = i;
                            while (true) {
                                if (i10 < i8) {
                                    if (TypeBinding.equalsEquals(referenceBinding7, referenceBindingArr[i10])) {
                                        break;
                                    }
                                    i10++;
                                } else {
                                    referenceBindingArr[i8] = referenceBinding7;
                                    i8++;
                                    break;
                                }
                            }
                        }
                        referenceBindingArr3 = referenceBindingArr;
                        length = i8;
                    }
                    i6++;
                }
                i6++;
            }
            if (problemReferenceBinding != null) {
                return problemReferenceBinding;
            }
        }
        if (referenceBinding3 != null) {
            return referenceBinding3;
        }
        if (referenceBinding4 != null) {
            return new ProblemReferenceBinding(new char[][]{cArr}, referenceBinding4, 2);
        }
        return null;
    }

    public MethodBinding findMethod(ReferenceBinding referenceBinding, char[] cArr, TypeBinding[] typeBindingArr, InvocationSite invocationSite, boolean z) {
        MethodBinding methodBindingFindMethod0 = findMethod0(referenceBinding, cArr, typeBindingArr, invocationSite, z);
        if (methodBindingFindMethod0 != null && methodBindingFindMethod0.isValidBinding() && methodBindingFindMethod0.isVarargs()) {
            TypeBinding typeBindingLeafComponentType = methodBindingFindMethod0.parameters[methodBindingFindMethod0.parameters.length - 1].leafComponentType();
            if ((typeBindingLeafComponentType instanceof ReferenceBinding) && !((ReferenceBinding) typeBindingLeafComponentType).canBeSeenBy(this)) {
                return new ProblemMethodBinding(methodBindingFindMethod0, methodBindingFindMethod0.selector, invocationSite.genericTypeArguments(), 16);
            }
        }
        return methodBindingFindMethod0;
    }

    /* JADX WARN: Code duplicated, block: B:118:0x01d1  */
    /* JADX WARN: Code duplicated, block: B:122:0x01de  */
    public MethodBinding findMethod0(ReferenceBinding referenceBinding, char[] cArr, TypeBinding[] typeBindingArr, InvocationSite invocationSite, boolean z) {
        ReferenceBinding javaLangObject;
        long j;
        MethodVerifier methodVerifier;
        int i;
        int i2;
        MethodBinding methodBinding;
        MethodBinding[] methodBindingArr;
        MethodBinding methodBindingComputeSubstituteMethod;
        int iProblemId;
        long j2;
        MethodVerifier methodVerifier2;
        boolean z2;
        boolean z3;
        ReferenceBinding referenceBinding2 = referenceBinding;
        boolean zIsInterface = referenceBinding.isInterface();
        ObjectVector objectVector = new ObjectVector(3);
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordTypeReferences(typeBindingArr);
        ArrayList arrayList = new ArrayList();
        if (zIsInterface) {
            compilationUnitScope.recordTypeReference(referenceBinding2);
            MethodBinding[] methods = referenceBinding2.getMethods(cArr, typeBindingArr.length);
            if (methods.length > 0) {
                objectVector.addAll(methods);
            }
            findMethodInSuperInterfaces(referenceBinding, cArr, objectVector, arrayList, invocationSite);
            javaLangObject = getJavaLangObject();
        } else {
            javaLangObject = referenceBinding2;
        }
        long j3 = compilerOptions().complianceLevel;
        boolean z4 = j3 >= ClassFileConstants.JDK1_4;
        boolean z5 = j3 >= ClassFileConstants.JDK1_5;
        ReferenceBinding referenceBinding3 = javaLangObject;
        boolean z6 = compilerOptions().sourceLevel >= ClassFileConstants.JDK1_8;
        MethodVerifier methodVerifier3 = environment().methodVerifier();
        ReferenceBinding referenceBindingSuperclass = referenceBinding3;
        while (referenceBindingSuperclass != null) {
            long j4 = j3;
            compilationUnitScope.recordTypeReference(referenceBindingSuperclass);
            ReferenceBinding referenceBinding4 = (ReferenceBinding) referenceBindingSuperclass.capture(this, invocationSite == 0 ? 0 : invocationSite.sourceStart(), invocationSite == null ? 0 : invocationSite.sourceEnd());
            MethodBinding[] methods2 = referenceBinding4.getMethods(cArr, typeBindingArr.length);
            int length = methods2.length;
            if (length > 0) {
                if (!z4 || (!zIsInterface && objectVector.size <= 0)) {
                    z2 = z4;
                } else {
                    int i3 = length;
                    int i4 = 0;
                    while (i4 < length) {
                        MethodBinding methodBinding2 = methods2[i4];
                        if (methodBinding2 == null) {
                            z3 = z4;
                        } else if (zIsInterface && !methodBinding2.isPublic()) {
                            i3--;
                            methods2[i4] = null;
                            z3 = z4;
                        } else {
                            z3 = z4;
                            int i5 = objectVector.size;
                            int i6 = 0;
                            while (i6 < i5) {
                                MethodBinding methodBinding3 = (MethodBinding) objectVector.elementAt(i6);
                                int i7 = i5;
                                MethodBinding methodBindingOriginal = methodBinding3.original();
                                MethodBinding methodBindingFindOriginalInheritedMethod = methodBindingOriginal.findOriginalInheritedMethod(methodBinding2);
                                if (methodBindingFindOriginalInheritedMethod != null && methodVerifier3.isParameterSubsignature(methodBindingOriginal, methodBindingFindOriginalInheritedMethod)) {
                                    if (!z5 || !methodBinding3.isBridge() || methodBinding2.isBridge()) {
                                        i3--;
                                        methods2[i4] = null;
                                        break;
                                    }
                                    break;
                                }
                                i6++;
                                i5 = i7;
                            }
                        }
                        i4++;
                        z4 = z3;
                    }
                    z2 = z4;
                    length = i3;
                }
                if (length > 0) {
                    if (methods2.length == length) {
                        objectVector.addAll(methods2);
                    } else {
                        for (MethodBinding methodBinding4 : methods2) {
                            if (methodBinding4 != null) {
                                objectVector.add(methodBinding4);
                            }
                        }
                    }
                }
            } else {
                z2 = z4;
            }
            referenceBindingSuperclass = referenceBinding4.superclass();
            referenceBinding2 = referenceBinding;
            z4 = z2;
            j3 = j4;
        }
        int i8 = objectVector.size;
        boolean z7 = z6 || (z4 && !zIsInterface && (referenceBinding.isAbstract() || referenceBinding.isTypeVariable()));
        if (i8 > 0) {
            methodBinding = null;
            MethodBinding[] methodBindingArr2 = null;
            int i9 = 0;
            int i10 = 0;
            while (i9 < i8) {
                MethodBinding methodBindingComputeCompatibleMethod = computeCompatibleMethod((MethodBinding) objectVector.elementAt(i9), typeBindingArr, invocationSite);
                if (methodBindingComputeCompatibleMethod != null) {
                    if (methodBindingComputeCompatibleMethod.isValidBinding()) {
                        j2 = j3;
                        methodVerifier2 = methodVerifier3;
                    } else {
                        methodVerifier2 = methodVerifier3;
                        j2 = j3;
                        if (methodBindingComputeCompatibleMethod.problemId() != 23) {
                            if (methodBinding == null) {
                                methodBinding = methodBindingComputeCompatibleMethod;
                            }
                        }
                        i9++;
                        methodVerifier3 = methodVerifier2;
                        j3 = j2;
                    }
                    if (i8 == 1 && methodBindingComputeCompatibleMethod.canBeSeenBy(referenceBinding2, invocationSite, this)) {
                        if (z7) {
                            return findDefaultAbstractMethod(referenceBinding, cArr, typeBindingArr, invocationSite, referenceBinding3, objectVector, new MethodBinding[]{methodBindingComputeCompatibleMethod});
                        }
                        compilationUnitScope.recordTypeReferences(methodBindingComputeCompatibleMethod.thrownExceptions);
                        return methodBindingComputeCompatibleMethod;
                    }
                    if (i10 == 0) {
                        methodBindingArr2 = new MethodBinding[i8];
                    }
                    methodBindingArr2[i10] = methodBindingComputeCompatibleMethod;
                    i10++;
                    i9++;
                    methodVerifier3 = methodVerifier2;
                    j3 = j2;
                } else {
                    j2 = j3;
                    methodVerifier2 = methodVerifier3;
                }
                i9++;
                methodVerifier3 = methodVerifier2;
                j3 = j2;
            }
            j = j3;
            i2 = i10;
            methodVerifier = methodVerifier3;
            methodBindingArr = methodBindingArr2;
            i = 0;
        } else {
            j = j3;
            methodVerifier = methodVerifier3;
            i = 0;
            i2 = 0;
            methodBinding = null;
            methodBindingArr = null;
        }
        int i11 = 2;
        if (i2 == 0) {
            if (methodBinding != null && ((iProblemId = methodBinding.problemId()) == 11 || iProblemId == 13)) {
                return methodBinding;
            }
            MethodVerifier methodVerifier4 = methodVerifier;
            MethodBinding methodBindingFindDefaultAbstractMethod = findDefaultAbstractMethod(referenceBinding, cArr, typeBindingArr, invocationSite, referenceBinding3, objectVector, null);
            if (methodBindingFindDefaultAbstractMethod != null) {
                if (z6 && i8 > 0 && methodBindingFindDefaultAbstractMethod.isVarargs() && (methodBindingFindDefaultAbstractMethod instanceof ParameterizedGenericMethodBinding)) {
                    MethodBinding methodBindingOriginal2 = methodBindingFindDefaultAbstractMethod.original();
                    for (int i12 = 0; i12 < i8; i12++) {
                        MethodBinding methodBinding5 = (MethodBinding) objectVector.elementAt(i12);
                        if (!methodBinding5.isAbstract() && (methodBindingComputeSubstituteMethod = methodVerifier4.computeSubstituteMethod(methodBindingOriginal2, methodBinding5)) != null && methodVerifier4.isSubstituteParameterSubsignature(methodBinding5, methodBindingComputeSubstituteMethod)) {
                            return new ProblemMethodBinding(methodBindingFindDefaultAbstractMethod, cArr, typeBindingArr, 24);
                        }
                    }
                }
                return methodBindingFindDefaultAbstractMethod;
            }
            if (objectVector.size == 0) {
                return null;
            }
            if (methodBinding != null) {
                return methodBinding;
            }
            MethodBinding methodBinding6 = (MethodBinding) objectVector.elementAt(0);
            int length2 = typeBindingArr.length;
            int i13 = objectVector.size;
            int i14 = -1;
            for (int i15 = 0; i15 < i13; i15++) {
                MethodBinding methodBinding7 = (MethodBinding) objectVector.elementAt(i15);
                TypeBinding[] typeBindingArr2 = methodBinding7.parameters;
                int length3 = typeBindingArr2.length;
                int i16 = 0;
                int i17 = 0;
                while (i16 < length2) {
                    TypeBinding typeBinding = typeBindingArr[i16];
                    for (int i18 = i16 == 0 ? 0 : i16 - 1; i18 < length3 && i18 < i16 + 1; i18++) {
                        if (TypeBinding.equalsEquals(typeBindingArr2[i18], typeBinding)) {
                            i17++;
                            break;
                        }
                    }
                    i16++;
                }
                if (i17 >= i14) {
                    if (i17 == i14) {
                        int i19 = length3 < length2 ? (length2 - length3) * 2 : length3 - length2;
                        int length4 = methodBinding6.parameters.length;
                        if (i19 < (length4 < length2 ? (length2 - length4) * 2 : length4 - length2)) {
                            if (methodBinding6 != methodBinding7) {
                                methodBinding6 = methodBinding7;
                                i14 = i17;
                            } else {
                                methodBinding6 = methodBinding7;
                                i14 = i17;
                            }
                        }
                    } else if (methodBinding6 != methodBinding7 || !MethodVerifier.doesMethodOverride(methodBinding6, methodBinding7, environment())) {
                        methodBinding6 = methodBinding7;
                        i14 = i17;
                    }
                }
            }
            return new ProblemMethodBinding(methodBinding6, methodBinding6.selector, typeBindingArr, 1);
        }
        int i20 = i;
        int i21 = i20;
        for (int i22 = i21; i22 < i2; i22++) {
            MethodBinding methodBinding8 = methodBindingArr[i22];
            if (methodBinding8.canBeSeenBy(referenceBinding2, invocationSite, this)) {
                if (i21 != i22) {
                    methodBindingArr[i22] = null;
                    methodBindingArr[i21] = methodBinding8;
                }
                i21++;
            }
        }
        if (i21 == 0) {
            MethodBinding methodBindingFindDefaultAbstractMethod2 = findDefaultAbstractMethod(referenceBinding, cArr, typeBindingArr, invocationSite, referenceBinding3, objectVector, null);
            if (methodBindingFindDefaultAbstractMethod2 != null) {
                return methodBindingFindDefaultAbstractMethod2;
            }
            MethodBinding methodBinding9 = methodBindingArr[i20];
            if (methodBinding9.isStatic() && methodBinding9.declaringClass.isInterface()) {
                i11 = z6 ? 20 : 29;
            }
            return new ProblemMethodBinding(methodBinding9, methodBinding9.selector, methodBinding9.parameters, i11);
        }
        if (i21 == 1) {
            if (z7) {
                MethodBinding[] methodBindingArr3 = new MethodBinding[1];
                methodBindingArr3[i20] = methodBindingArr[i20];
                return findDefaultAbstractMethod(referenceBinding, cArr, typeBindingArr, invocationSite, referenceBinding3, objectVector, methodBindingArr3);
            }
            MethodBinding methodBinding10 = methodBindingArr[i20];
            if (methodBinding10 != null) {
                compilationUnitScope.recordTypeReferences(methodBinding10.thrownExceptions);
            }
            return methodBinding10;
        }
        if (j <= ClassFileConstants.JDK1_3) {
            if (!methodBindingArr[i20].declaringClass.isInterface()) {
                return mostSpecificClassMethodBinding(methodBindingArr, i21, invocationSite);
            }
            return mostSpecificInterfaceMethodBinding(methodBindingArr, i21, invocationSite);
        }
        if (compilerOptions().sourceLevel >= ClassFileConstants.JDK1_5) {
            for (int i23 = i20; i23 < i21; i23++) {
                MethodBinding methodBindingShallowOriginal = methodBindingArr[i23];
                if (methodBindingShallowOriginal.isParameterizedGeneric()) {
                    methodBindingShallowOriginal = methodBindingShallowOriginal.shallowOriginal();
                }
                if (methodBindingShallowOriginal.hasSubstitutedParameters()) {
                    for (int i24 = i23 + 1; i24 < i21; i24++) {
                        MethodBinding methodBinding11 = methodBindingArr[i24];
                        if (methodBinding11.hasSubstitutedParameters() && (methodBinding11 == methodBindingShallowOriginal || (TypeBinding.equalsEquals(methodBindingShallowOriginal.declaringClass, methodBinding11.declaringClass) && methodBindingShallowOriginal.areParametersEqual(methodBinding11)))) {
                            MethodBinding methodBinding12 = methodBindingArr[i23];
                            return new ProblemMethodBinding(methodBinding12, methodBinding12.selector, methodBindingArr[i23].parameters, 3);
                        }
                    }
                }
            }
        }
        if (z) {
            MethodBinding[] methodBindingArr4 = new MethodBinding[i21];
            int i25 = i20;
            for (int i26 = i25; i26 < i21; i26++) {
                if (methodBindingArr[i26].isStatic()) {
                    methodBindingArr4[i25] = methodBindingArr[i26];
                    i25++;
                }
            }
            if (i25 == 1) {
                return methodBindingArr4[i20];
            }
            if (i25 > 1) {
                return mostSpecificMethodBinding(methodBindingArr4, i25, typeBindingArr, invocationSite, referenceBinding);
            }
        }
        if (i21 != methodBindingArr.length) {
            MethodBinding[] methodBindingArr5 = new MethodBinding[i21];
            System.arraycopy(methodBindingArr, i20, methodBindingArr5, i20, i21);
            methodBindingArr = methodBindingArr5;
        }
        if (z7) {
            return findDefaultAbstractMethod(referenceBinding, cArr, typeBindingArr, invocationSite, referenceBinding3, objectVector, methodBindingArr);
        }
        return mostSpecificMethodBinding(methodBindingArr, i21, typeBindingArr, invocationSite, referenceBinding);
    }

    public MethodBinding findMethodForArray(ArrayBinding arrayBinding, char[] cArr, TypeBinding[] typeBindingArr, InvocationSite invocationSite) {
        TypeBinding typeBindingLeafComponentType = arrayBinding.leafComponentType();
        if (typeBindingLeafComponentType instanceof ReferenceBinding) {
            ReferenceBinding referenceBinding = (ReferenceBinding) typeBindingLeafComponentType;
            if (!referenceBinding.canBeSeenBy(this)) {
                return new ProblemMethodBinding(cArr, Binding.NO_PARAMETERS, referenceBinding, 8);
            }
        }
        ReferenceBinding javaLangObject = getJavaLangObject();
        MethodBinding exactMethod = javaLangObject.getExactMethod(cArr, typeBindingArr, null);
        if (exactMethod != null) {
            if (typeBindingArr == Binding.NO_PARAMETERS) {
                char c = cArr[0];
                if (c == 'c') {
                    if (CharOperation.equals(cArr, TypeConstants.CLONE)) {
                        return environment().computeArrayClone(exactMethod);
                    }
                } else if (c == 'g' && CharOperation.equals(cArr, TypeConstants.GETCLASS) && exactMethod.returnType.isParameterizedType()) {
                    return environment().createGetClassMethod(arrayBinding, exactMethod, this);
                }
            }
            if (exactMethod.canBeSeenBy(arrayBinding, invocationSite, this)) {
                return exactMethod;
            }
        }
        MethodBinding methodBindingFindMethod = findMethod(javaLangObject, cArr, typeBindingArr, invocationSite, false);
        return methodBindingFindMethod == null ? new ProblemMethodBinding(cArr, typeBindingArr, 26) : methodBindingFindMethod;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x004a  */
    /* JADX WARN: Code duplicated, block: B:22:0x004c  */
    /* JADX WARN: Code duplicated, block: B:24:0x0052  */
    /* JADX WARN: Code duplicated, block: B:25:0x0054  */
    /* JADX WARN: Code duplicated, block: B:28:0x0067  */
    /* JADX WARN: Code duplicated, block: B:31:0x006e  */
    /* JADX WARN: Code duplicated, block: B:34:0x0079 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:35:0x007b  */
    /* JADX WARN: Code duplicated, block: B:38:0x007f  */
    /* JADX WARN: Code duplicated, block: B:41:0x0086 A[LOOP:3: B:36:0x007c->B:41:0x0086, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:42:0x008b  */
    /* JADX WARN: Code duplicated, block: B:72:0x008e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:73:0x008e A[EDGE_INSN: B:73:0x008e->B:43:0x008e BREAK  A[LOOP:3: B:36:0x007c->B:41:0x0086], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:74:0x008b A[SYNTHETIC] */
    protected void findMethodInSuperInterfaces(ReferenceBinding referenceBinding, char[] cArr, ObjectVector objectVector, List<TypeBinding> list, InvocationSite invocationSite) {
        int iSourceStart;
        int iSourceEnd;
        MethodBinding[] methods;
        ReferenceBinding[] referenceBindingArrSuperInterfaces;
        int i;
        ReferenceBinding[] referenceBindingArr;
        int i2;
        int length;
        int i3;
        MethodBinding methodBinding;
        int i4;
        ReferenceBinding[] referenceBindingArrSuperInterfaces2 = referenceBinding.superInterfaces();
        if (referenceBindingArrSuperInterfaces2 == null || referenceBindingArrSuperInterfaces2 == Binding.NO_SUPERINTERFACES) {
            return;
        }
        int length2 = referenceBindingArrSuperInterfaces2.length;
        for (int i5 = 0; i5 < length2; i5++) {
            ReferenceBinding referenceBinding2 = referenceBindingArrSuperInterfaces2[i5];
            if (list != null) {
                TypeBinding typeBindingUncapture = referenceBinding2.uncapture(this);
                Iterator<TypeBinding> it = list.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (typeBindingUncapture.isEquivalentTo(it.next())) {
                        }
                    } else {
                        list.add(typeBindingUncapture);
                        compilationUnitScope().recordTypeReference(referenceBinding2);
                        if (invocationSite == null) {
                            iSourceStart = 0;
                        } else {
                            iSourceStart = invocationSite.sourceStart();
                        }
                        if (invocationSite == null) {
                            iSourceEnd = 0;
                        } else {
                            iSourceEnd = invocationSite.sourceEnd();
                        }
                        ReferenceBinding referenceBinding3 = (ReferenceBinding) referenceBinding2.capture(this, iSourceStart, iSourceEnd);
                        methods = referenceBinding3.getMethods(cArr);
                        if (methods.length > 0) {
                            i2 = objectVector.size;
                            length = methods.length;
                            i3 = 0;
                            while (i3 < length) {
                                methodBinding = methods[i3];
                                if (methodBinding.canBeSeenBy(referenceBinding, invocationSite, this)) {
                                    if (i2 > 0) {
                                        objectVector.add(methodBinding);
                                        break;
                                        break;
                                    }
                                    i4 = 0;
                                    while (true) {
                                        if (i4 >= i2) {
                                            if (methodBinding == objectVector.elementAt(i4)) {
                                                break;
                                            } else {
                                                i4++;
                                            }
                                        } else {
                                            objectVector.add(methodBinding);
                                            break;
                                        }
                                    }
                                }
                                i3++;
                                this = this;
                            }
                        }
                        referenceBindingArrSuperInterfaces = referenceBinding3.superInterfaces();
                        if (referenceBindingArrSuperInterfaces == null && referenceBindingArrSuperInterfaces != Binding.NO_SUPERINTERFACES) {
                            int length3 = referenceBindingArrSuperInterfaces.length;
                            int i6 = length2 + length3;
                            if (i6 >= referenceBindingArrSuperInterfaces2.length) {
                                referenceBindingArr = new ReferenceBinding[i6 + 5];
                                i = 0;
                                System.arraycopy(referenceBindingArrSuperInterfaces2, 0, referenceBindingArr, 0, length2);
                            } else {
                                i = 0;
                                referenceBindingArr = referenceBindingArrSuperInterfaces2;
                            }
                            int i7 = length2;
                            for (int i8 = i; i8 < length3; i8++) {
                                ReferenceBinding referenceBinding4 = referenceBindingArrSuperInterfaces[i8];
                                int i9 = i;
                                while (true) {
                                    if (i9 < i7) {
                                        if (TypeBinding.equalsEquals(referenceBinding4, referenceBindingArr[i9])) {
                                            break;
                                        } else {
                                            i9++;
                                        }
                                    } else {
                                        referenceBindingArr[i7] = referenceBinding4;
                                        i7++;
                                        break;
                                    }
                                }
                            }
                            referenceBindingArrSuperInterfaces2 = referenceBindingArr;
                            length2 = i7;
                        }
                    }
                }
            } else {
                compilationUnitScope().recordTypeReference(referenceBinding2);
                if (invocationSite == null) {
                    iSourceStart = 0;
                } else {
                    iSourceStart = invocationSite.sourceStart();
                }
                if (invocationSite == null) {
                    iSourceEnd = 0;
                } else {
                    iSourceEnd = invocationSite.sourceEnd();
                }
                ReferenceBinding referenceBinding5 = (ReferenceBinding) referenceBinding2.capture(this, iSourceStart, iSourceEnd);
                methods = referenceBinding5.getMethods(cArr);
                if (methods.length > 0) {
                    i2 = objectVector.size;
                    length = methods.length;
                    i3 = 0;
                    while (i3 < length) {
                        methodBinding = methods[i3];
                        if (methodBinding.canBeSeenBy(referenceBinding, invocationSite, this)) {
                            if (i2 > 0) {
                                objectVector.add(methodBinding);
                                break;
                                break;
                            }
                            i4 = 0;
                            while (true) {
                                if (i4 >= i2) {
                                    if (methodBinding == objectVector.elementAt(i4)) {
                                        break;
                                        break;
                                    }
                                    i4++;
                                } else {
                                    objectVector.add(methodBinding);
                                    break;
                                    break;
                                }
                            }
                        }
                        i3++;
                        this = this;
                    }
                }
                referenceBindingArrSuperInterfaces = referenceBinding5.superInterfaces();
                if (referenceBindingArrSuperInterfaces == null) {
                }
            }
        }
    }

    public ReferenceBinding findType(char[] cArr, PackageBinding packageBinding, PackageBinding packageBinding2) {
        compilationUnitScope().recordReference(packageBinding.compoundName, cArr);
        ReferenceBinding type = packageBinding.getType(cArr);
        if (type == null) {
            return null;
        }
        return (!type.isValidBinding() || packageBinding == packageBinding2 || type.canBeSeenBy(packageBinding2)) ? type : new ProblemReferenceBinding(new char[][]{cArr}, type, 2);
    }

    /* JADX WARN: Code duplicated, block: B:112:0x0179  */
    /* JADX WARN: Code duplicated, block: B:150:0x0204  */
    /* JADX WARN: Code duplicated, block: B:151:0x0206 A[Catch: all -> 0x02e8, AbortCompilation -> 0x02eb, TryCatch #0 {AbortCompilation -> 0x02eb, blocks: (B:3:0x0010, B:6:0x0025, B:19:0x003c, B:23:0x0046, B:28:0x0056, B:30:0x0063, B:32:0x006a, B:34:0x006e, B:37:0x0077, B:180:0x028b, B:182:0x028f, B:190:0x02a1, B:210:0x02d0, B:215:0x02da, B:200:0x02b9, B:40:0x0080, B:42:0x0088, B:44:0x008c, B:46:0x0092, B:48:0x009a, B:52:0x00a4, B:56:0x00ad, B:59:0x00b3, B:61:0x00ba, B:66:0x00cc, B:68:0x00d4, B:70:0x00d8, B:72:0x00e9, B:74:0x00f1, B:76:0x00f7, B:79:0x0101, B:80:0x0106, B:175:0x027d, B:178:0x0285, B:89:0x0123, B:91:0x012e, B:93:0x0134, B:96:0x013f, B:99:0x0147, B:104:0x0157, B:106:0x015d, B:109:0x0165, B:113:0x017a, B:115:0x0182, B:141:0x01d8, B:143:0x01df, B:148:0x01f7, B:151:0x0206, B:122:0x0199, B:129:0x01a9, B:131:0x01af, B:133:0x01bb, B:135:0x01c3, B:111:0x0170, B:153:0x0212, B:155:0x0233, B:158:0x023b, B:160:0x0241, B:164:0x024f, B:166:0x0254, B:168:0x0258, B:169:0x0263, B:171:0x0267), top: B:225:0x0010, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:177:0x0283  */
    /* JADX WARN: Code duplicated, block: B:233:0x0285 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:87:0x011c A[EDGE_INSN: B:87:0x011c->B:180:0x028b BREAK  A[LOOP:0: B:6:0x0025->B:178:0x0285]] */
    public Binding getBinding(char[] cArr, int i, InvocationSite invocationSite, boolean z) {
        FieldBinding fieldBinding;
        Binding topLevelPackage;
        int i2;
        ProblemFieldBinding problemFieldBinding;
        int i3;
        int i4;
        MethodScope methodScope;
        FieldBinding fieldBindingFindField;
        ProblemFieldBinding problemFieldBinding2;
        ProblemFieldBinding problemFieldBinding3;
        FieldBinding fieldBinding2;
        FieldBinding fieldBindingFindField2;
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        LookupEnvironment lookupEnvironment = compilationUnitScope.environment;
        try {
            try {
                lookupEnvironment.missingClassFileLocation = invocationSite;
                int i5 = 1;
                if ((i & 3) != 0) {
                    Scope scope = this;
                    FieldBinding fieldBinding3 = null;
                    boolean zIsStatic = false;
                    ProblemFieldBinding problemFieldBinding4 = null;
                    boolean z2 = false;
                    boolean z3 = false;
                    int i6 = 0;
                    boolean z4 = false;
                    int i7 = 0;
                    ReferenceBinding referenceBinding = null;
                    while (true) {
                        int i8 = scope.kind;
                        if (i8 != i5) {
                            if (i8 == 2) {
                                MethodScope methodScope2 = (MethodScope) scope;
                                zIsStatic |= methodScope2.isStatic;
                                z2 |= methodScope2.isConstructorCall;
                                z3 = methodScope2.insideTypeAnnotation;
                                i2 = i6;
                            } else if (i8 == 3) {
                                int i9 = i7;
                                ReferenceBinding referenceBinding2 = referenceBinding;
                                ClassScope classScope = (ClassScope) scope;
                                ReferenceBinding referenceBindingEnclosingReceiverType = classScope.enclosingReceiverType();
                                if (z3 || (fieldBindingFindField = classScope.findField(referenceBindingEnclosingReceiverType, cArr, invocationSite, z)) == null) {
                                    problemFieldBinding = problemFieldBinding4;
                                    i3 = i6;
                                } else {
                                    problemFieldBinding = problemFieldBinding4;
                                    if (fieldBindingFindField.problemId() == 3) {
                                        if (fieldBinding3 != null && fieldBinding3.problemId() != 2) {
                                            ProblemFieldBinding problemFieldBinding5 = new ProblemFieldBinding(fieldBinding3, fieldBinding3.declaringClass, cArr, 5);
                                            lookupEnvironment.missingClassFileLocation = null;
                                            return problemFieldBinding5;
                                        }
                                        lookupEnvironment.missingClassFileLocation = null;
                                        return fieldBindingFindField;
                                    }
                                    if (fieldBindingFindField.isValidBinding()) {
                                        if (fieldBindingFindField.isStatic()) {
                                            problemFieldBinding3 = null;
                                        } else if (z2) {
                                            problemFieldBinding3 = new ProblemFieldBinding(fieldBindingFindField, fieldBindingFindField.declaringClass, cArr, 6);
                                        } else if (zIsStatic) {
                                            problemFieldBinding3 = new ProblemFieldBinding(fieldBindingFindField, fieldBindingFindField.declaringClass, cArr, 7);
                                        } else {
                                            problemFieldBinding3 = null;
                                        }
                                        if (!TypeBinding.equalsEquals(referenceBindingEnclosingReceiverType, fieldBindingFindField.declaringClass) && compilerOptions().complianceLevel < ClassFileConstants.JDK1_4) {
                                            i3 = i6;
                                        } else {
                                            if (fieldBinding3 == null) {
                                                int i10 = i6;
                                                if (i10 > 0) {
                                                    invocationSite.setDepth(i10);
                                                    invocationSite.setActualReceiverType(referenceBindingEnclosingReceiverType);
                                                }
                                                if (problemFieldBinding3 != null) {
                                                    fieldBindingFindField = problemFieldBinding3;
                                                }
                                                lookupEnvironment.missingClassFileLocation = null;
                                                return fieldBindingFindField;
                                            }
                                            i3 = i6;
                                            if (fieldBinding3.isValidBinding()) {
                                                problemFieldBinding2 = problemFieldBinding3;
                                                if (TypeBinding.notEquals(fieldBinding3.declaringClass, fieldBindingFindField.declaringClass) && TypeBinding.notEquals(fieldBinding3.declaringClass, referenceBinding2)) {
                                                    ProblemFieldBinding problemFieldBinding6 = new ProblemFieldBinding(fieldBinding3, fieldBinding3.declaringClass, cArr, 5);
                                                    lookupEnvironment.missingClassFileLocation = null;
                                                    return problemFieldBinding6;
                                                }
                                            }
                                        }
                                        problemFieldBinding2 = problemFieldBinding3;
                                    } else {
                                        i3 = i6;
                                        problemFieldBinding2 = null;
                                    }
                                    if (fieldBinding3 == null || (fieldBinding3.problemId() == 2 && fieldBindingFindField.problemId() != 2)) {
                                        referenceBinding = referenceBindingEnclosingReceiverType;
                                        fieldBinding3 = fieldBindingFindField;
                                        problemFieldBinding4 = problemFieldBinding2;
                                        i4 = i3;
                                    }
                                    int i11 = i3 + 1;
                                    zIsStatic |= referenceBindingEnclosingReceiverType.isStatic();
                                    methodScope = scope.methodScope();
                                    if (methodScope == null) {
                                        z2 = false;
                                    } else {
                                        z2 = methodScope.isConstructorCall;
                                    }
                                    i7 = i4;
                                    z3 = false;
                                    z4 = true;
                                    i6 = i11;
                                }
                                i4 = i9;
                                problemFieldBinding4 = problemFieldBinding;
                                referenceBinding = referenceBinding2;
                                int i12 = i3 + 1;
                                zIsStatic |= referenceBindingEnclosingReceiverType.isStatic();
                                methodScope = scope.methodScope();
                                if (methodScope == null) {
                                    z2 = false;
                                } else {
                                    z2 = methodScope.isConstructorCall;
                                }
                                i7 = i4;
                                z3 = false;
                                z4 = true;
                                i6 = i12;
                            } else if (i8 == 4) {
                                if (problemFieldBinding4 == null) {
                                    if (fieldBinding3 == null) {
                                        fieldBinding2 = fieldBinding3;
                                        fieldBinding3 = null;
                                    } else {
                                        if (fieldBinding3.isValidBinding()) {
                                            int i13 = i7;
                                            if (i13 > 0) {
                                                invocationSite.setDepth(i13);
                                                invocationSite.setActualReceiverType(referenceBinding);
                                            }
                                            lookupEnvironment.missingClassFileLocation = null;
                                            return fieldBinding3;
                                        }
                                        fieldBinding2 = null;
                                    }
                                    if (compilerOptions().sourceLevel < ClassFileConstants.JDK1_5) {
                                        fieldBinding = fieldBinding3;
                                        break;
                                    }
                                    compilationUnitScope.faultInImports();
                                    ImportBinding[] importBindingArr = compilationUnitScope.imports;
                                    if (importBindingArr == null) {
                                        fieldBinding = fieldBinding3;
                                        break;
                                    }
                                    for (ImportBinding importBinding : importBindingArr) {
                                        if (importBinding.isStatic() && !importBinding.onDemand && CharOperation.equals(importBinding.compoundName[importBinding.compoundName.length - 1], cArr) && compilationUnitScope.resolveSingleImport(importBinding, 13) != null && (importBinding.resolvedImport instanceof FieldBinding)) {
                                            fieldBinding2 = (FieldBinding) importBinding.resolvedImport;
                                            ImportReference importReference = importBinding.reference;
                                            if (importReference != null && z) {
                                                importReference.bits |= 2;
                                            }
                                            invocationSite.setActualReceiverType(fieldBinding2.declaringClass);
                                            if (fieldBinding2.isValidBinding()) {
                                                lookupEnvironment.missingClassFileLocation = null;
                                                return fieldBinding2;
                                            }
                                            if (fieldBinding3 == null) {
                                                fieldBinding3 = fieldBinding2;
                                            }
                                        }
                                    }
                                    fieldBinding = fieldBinding3;
                                    boolean z5 = false;
                                    for (ImportBinding importBinding2 : importBindingArr) {
                                        if (importBinding2.isStatic() && importBinding2.onDemand) {
                                            Binding binding = importBinding2.resolvedImport;
                                            if ((binding instanceof ReferenceBinding) && (fieldBindingFindField2 = findField((ReferenceBinding) binding, cArr, invocationSite, z)) != null) {
                                                if (fieldBindingFindField2.isValidBinding()) {
                                                    if (fieldBindingFindField2.isStatic() && fieldBinding2 != fieldBindingFindField2) {
                                                        ImportReference importReference2 = importBinding2.reference;
                                                        if (importReference2 != null && z) {
                                                            importReference2.bits |= 2;
                                                        }
                                                        if (z5) {
                                                            ProblemFieldBinding problemFieldBinding7 = new ProblemFieldBinding(fieldBinding2, fieldBinding2.declaringClass, cArr, 3);
                                                            lookupEnvironment.missingClassFileLocation = null;
                                                            return problemFieldBinding7;
                                                        }
                                                        fieldBinding2 = fieldBindingFindField2;
                                                        z5 = true;
                                                    }
                                                } else if (fieldBinding == null) {
                                                    fieldBinding = fieldBindingFindField2;
                                                }
                                            }
                                        }
                                    }
                                    if (fieldBinding2 == null) {
                                        break;
                                    }
                                    invocationSite.setActualReceiverType(fieldBinding2.declaringClass);
                                    lookupEnvironment.missingClassFileLocation = null;
                                    return fieldBinding2;
                                }
                                lookupEnvironment.missingClassFileLocation = null;
                                return problemFieldBinding4;
                            }
                            if (scope.isLambdaScope()) {
                                z4 = true;
                            }
                            scope = scope.parent;
                            i5 = 1;
                        } else {
                            i2 = i6;
                        }
                        LocalVariableBinding localVariableBindingFindVariable = scope.findVariable(cArr);
                        if (localVariableBindingFindVariable != null) {
                            if (fieldBinding3 != null && fieldBinding3.isValidBinding()) {
                                ProblemFieldBinding problemFieldBinding8 = new ProblemFieldBinding(fieldBinding3, fieldBinding3.declaringClass, cArr, 5);
                                lookupEnvironment.missingClassFileLocation = null;
                                return problemFieldBinding8;
                            }
                            if (i2 > 0) {
                                invocationSite.setDepth(i2);
                            }
                            if (z4) {
                                if (invocationSite instanceof NameReference) {
                                    ((NameReference) invocationSite).bits |= 524288;
                                } else if (invocationSite instanceof AbstractVariableDeclaration) {
                                    ((AbstractVariableDeclaration) invocationSite).bits |= 2097152;
                                }
                            }
                            lookupEnvironment.missingClassFileLocation = null;
                            return localVariableBindingFindVariable;
                        }
                        i6 = i2;
                        i7 = i7;
                        problemFieldBinding4 = problemFieldBinding4;
                        referenceBinding = referenceBinding;
                        if (scope.isLambdaScope()) {
                            z4 = true;
                        }
                        scope = scope.parent;
                        i5 = 1;
                    }
                } else {
                    fieldBinding = null;
                }
                if ((i & 4) != 0) {
                    TypeBinding baseType = getBaseType(cArr);
                    if (baseType != null) {
                        lookupEnvironment.missingClassFileLocation = null;
                        return baseType;
                    }
                    Binding typeOrPackage = getTypeOrPackage(cArr, (i & 16) == 0 ? 4 : 20, z);
                    if (typeOrPackage.isValidBinding() || i == 4) {
                        lookupEnvironment.missingClassFileLocation = null;
                        return typeOrPackage;
                    }
                    topLevelPackage = typeOrPackage;
                } else if ((i & 16) != 0) {
                    compilationUnitScope.recordSimpleReference(cArr);
                    topLevelPackage = lookupEnvironment.getTopLevelPackage(cArr);
                    if (topLevelPackage != null) {
                        lookupEnvironment.missingClassFileLocation = null;
                        return topLevelPackage;
                    }
                } else {
                    topLevelPackage = null;
                }
                if (fieldBinding != null) {
                    lookupEnvironment.missingClassFileLocation = null;
                    return fieldBinding;
                }
                if (topLevelPackage != null && topLevelPackage.problemId() != 1) {
                    lookupEnvironment.missingClassFileLocation = null;
                    return topLevelPackage;
                }
                ProblemBinding problemBinding = new ProblemBinding(cArr, enclosingSourceType(), 1);
                lookupEnvironment.missingClassFileLocation = null;
                return problemBinding;
            } catch (AbortCompilation e) {
                e.updateContext(invocationSite, referenceCompilationUnit().compilationResult);
                throw e;
            }
        } catch (Throwable th) {
            lookupEnvironment.missingClassFileLocation = null;
            throw th;
        }
    }

    class MethodClashException extends RuntimeException {
        private static final long serialVersionUID = -7996779527641476028L;

        MethodClashException() {
        }
    }

    private MethodBinding getExactMethod(TypeBinding typeBinding, TypeBinding typeBinding2, char[] cArr, InvocationSite invocationSite, MethodBinding methodBinding) {
        if (typeBinding2 == null) {
            return null;
        }
        ReferenceBinding[] referenceBindingArrSuperInterfaces = typeBinding2.superInterfaces();
        int length = referenceBindingArrSuperInterfaces.length + 2;
        TypeBinding[] typeBindingArr = new TypeBinding[length];
        typeBindingArr[0] = typeBinding2;
        typeBindingArr[1] = typeBinding2.superclass();
        if (referenceBindingArrSuperInterfaces.length != 0) {
            System.arraycopy(referenceBindingArrSuperInterfaces, 0, typeBindingArr, 2, referenceBindingArrSuperInterfaces.length);
        }
        compilationUnitScope().recordTypeReference(typeBinding2);
        TypeBinding typeBindingCapture = typeBinding2.capture(this, invocationSite.sourceStart(), invocationSite.sourceEnd());
        MethodBinding methodBinding2 = methodBinding;
        int i = 0;
        while (i < length) {
            for (MethodBinding methodBinding3 : i == 0 ? typeBindingCapture.getMethods(cArr) : new MethodBinding[]{getExactMethod(typeBinding, typeBindingArr[i], cArr, invocationSite, methodBinding2)}) {
                if (methodBinding3 != null && methodBinding2 != methodBinding3) {
                    if (i != 0 || (methodBinding3.canBeSeenBy(typeBinding, invocationSite, this) && !methodBinding3.isSynthetic() && !methodBinding3.isBridge())) {
                        if (methodBinding2 == null) {
                            methodBinding2 = methodBinding3;
                        } else if (!methodBinding2.areParameterErasuresEqual(methodBinding3)) {
                            throw new MethodClashException();
                        }
                    }
                }
            }
            i++;
        }
        return methodBinding2;
    }

    public MethodBinding getExactMethod(TypeBinding typeBinding, char[] cArr, InvocationSite invocationSite) {
        TypeBinding javaLangObject;
        if (typeBinding != null && typeBinding.isValidBinding() && !typeBinding.isBaseType()) {
            if (!typeBinding.isArrayType()) {
                javaLangObject = typeBinding;
            } else {
                if (!typeBinding.leafComponentType().canBeSeenBy(this)) {
                    return null;
                }
                javaLangObject = getJavaLangObject();
            }
            try {
                MethodBinding exactMethod = getExactMethod(typeBinding, javaLangObject, cArr, invocationSite, null);
                if (exactMethod != null && exactMethod.canBeSeenBy(invocationSite, this)) {
                    TypeBinding[] typeBindingArrGenericTypeArguments = invocationSite.genericTypeArguments();
                    TypeVariableBinding[] typeVariableBindingArrTypeVariables = exactMethod.typeVariables();
                    if (!exactMethod.isVarargs() && (typeVariableBindingArrTypeVariables == Binding.NO_TYPE_VARIABLES || (typeBindingArrGenericTypeArguments != null && typeBindingArrGenericTypeArguments.length == typeVariableBindingArrTypeVariables.length))) {
                        if (typeBinding.isArrayType()) {
                            if (CharOperation.equals(cArr, TypeConstants.CLONE)) {
                                return environment().computeArrayClone(exactMethod);
                            }
                            if (CharOperation.equals(cArr, TypeConstants.GETCLASS)) {
                                return environment().createGetClassMethod(typeBinding, exactMethod, this);
                            }
                        }
                        if (exactMethod.declaringClass.f626id == 1 && CharOperation.equals(cArr, TypeConstants.GETCLASS) && exactMethod.returnType.isParameterizedType()) {
                            return environment().createGetClassMethod(typeBinding, exactMethod, this);
                        }
                        return typeVariableBindingArrTypeVariables != Binding.NO_TYPE_VARIABLES ? environment().createParameterizedGenericMethod(exactMethod, typeBindingArrGenericTypeArguments) : exactMethod;
                    }
                }
            } catch (MethodClashException unused) {
            }
        }
        return null;
    }

    public MethodBinding getExactConstructor(TypeBinding typeBinding, InvocationSite invocationSite) {
        TypeVariableBinding[] typeVariableBindingArrTypeVariables;
        if (typeBinding != null && typeBinding.isValidBinding() && typeBinding.canBeInstantiated() && !typeBinding.isBaseType()) {
            if (typeBinding.isArrayType()) {
                TypeBinding typeBindingLeafComponentType = typeBinding.leafComponentType();
                if (typeBindingLeafComponentType.canBeSeenBy(this) && typeBindingLeafComponentType.isReifiable()) {
                    return new MethodBinding(4097, TypeConstants.INIT, typeBinding, new TypeBinding[]{TypeBinding.INT}, Binding.NO_EXCEPTIONS, getJavaLangObject());
                }
                return null;
            }
            compilationUnitScope().recordTypeReference(typeBinding);
            MethodBinding[] methods = typeBinding.getMethods(TypeConstants.INIT);
            TypeBinding[] typeBindingArrGenericTypeArguments = invocationSite.genericTypeArguments();
            MethodBinding methodBinding = null;
            for (MethodBinding methodBinding2 : methods) {
                if (methodBinding2.canBeSeenBy(invocationSite, this)) {
                    if (methodBinding2.isVarargs()) {
                        return null;
                    }
                    if ((methodBinding2.typeVariables() == Binding.NO_TYPE_VARIABLES || typeBindingArrGenericTypeArguments != null) && methodBinding == null) {
                        methodBinding = methodBinding2;
                    }
                }
            }
            if (methodBinding == null || (typeVariableBindingArrTypeVariables = methodBinding.typeVariables()) == Binding.NO_TYPE_VARIABLES) {
                return methodBinding;
            }
            if (typeVariableBindingArrTypeVariables.length != typeBindingArrGenericTypeArguments.length) {
                return null;
            }
            return environment().createParameterizedGenericMethod(methodBinding, typeBindingArrGenericTypeArguments);
        }
        return null;
    }

    public MethodBinding getConstructor(ReferenceBinding referenceBinding, TypeBinding[] typeBindingArr, InvocationSite invocationSite) {
        MethodBinding constructor0 = getConstructor0(referenceBinding, typeBindingArr, invocationSite);
        if (constructor0 != null && constructor0.isValidBinding() && constructor0.isVarargs()) {
            TypeBinding typeBindingLeafComponentType = constructor0.parameters[constructor0.parameters.length - 1].leafComponentType();
            if ((typeBindingLeafComponentType instanceof ReferenceBinding) && !((ReferenceBinding) typeBindingLeafComponentType).canBeSeenBy(this)) {
                return new ProblemMethodBinding(constructor0, constructor0.selector, invocationSite.genericTypeArguments(), 16);
            }
        }
        return constructor0;
    }

    public MethodBinding getConstructor0(ReferenceBinding referenceBinding, TypeBinding[] typeBindingArr, InvocationSite invocationSite) {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        LookupEnvironment lookupEnvironment = compilationUnitScope.environment;
        try {
            try {
                lookupEnvironment.missingClassFileLocation = invocationSite;
                compilationUnitScope.recordTypeReference(referenceBinding);
                compilationUnitScope.recordTypeReferences(typeBindingArr);
                MethodBinding exactConstructor = referenceBinding.getExactConstructor(typeBindingArr);
                if (exactConstructor != null && exactConstructor.canBeSeenBy(invocationSite, this)) {
                    if (invocationSite.genericTypeArguments() != null) {
                        exactConstructor = computeCompatibleMethod(exactConstructor, typeBindingArr, invocationSite);
                    }
                    lookupEnvironment.missingClassFileLocation = null;
                    return exactConstructor;
                }
                MethodBinding[] methods = referenceBinding.getMethods(TypeConstants.INIT, typeBindingArr.length);
                if (methods == Binding.NO_METHODS) {
                    ProblemMethodBinding problemMethodBinding = new ProblemMethodBinding(TypeConstants.INIT, typeBindingArr, 1);
                    lookupEnvironment.missingClassFileLocation = null;
                    return problemMethodBinding;
                }
                MethodBinding[] methodBindingArr = new MethodBinding[methods.length];
                MethodBinding methodBinding = null;
                int i = 0;
                for (MethodBinding methodBinding2 : methods) {
                    MethodBinding methodBindingComputeCompatibleMethod = computeCompatibleMethod(methodBinding2, typeBindingArr, invocationSite);
                    if (methodBindingComputeCompatibleMethod != null) {
                        if (methodBindingComputeCompatibleMethod.isValidBinding()) {
                            int i2 = i + 1;
                            methodBindingArr[i] = methodBindingComputeCompatibleMethod;
                            i = i2;
                        } else if (methodBinding == null) {
                            methodBinding = methodBindingComputeCompatibleMethod;
                        }
                    }
                }
                if (i == 0) {
                    if (methodBinding != null) {
                        lookupEnvironment.missingClassFileLocation = null;
                        return methodBinding;
                    }
                    ProblemMethodBinding problemMethodBinding2 = new ProblemMethodBinding(methods[0], TypeConstants.INIT, typeBindingArr, 1);
                    lookupEnvironment.missingClassFileLocation = null;
                    return problemMethodBinding2;
                }
                MethodBinding[] methodBindingArr2 = new MethodBinding[i];
                int i3 = 0;
                for (int i4 = 0; i4 < i; i4++) {
                    MethodBinding methodBinding3 = methodBindingArr[i4];
                    if (methodBinding3.canBeSeenBy(invocationSite, this)) {
                        methodBindingArr2[i3] = methodBinding3;
                        i3++;
                    }
                }
                if (i3 == 1) {
                    MethodBinding methodBinding4 = methodBindingArr2[0];
                    lookupEnvironment.missingClassFileLocation = null;
                    return methodBinding4;
                }
                if (i3 == 0) {
                    ProblemMethodBinding problemMethodBinding3 = new ProblemMethodBinding(methodBindingArr[0], TypeConstants.INIT, methodBindingArr[0].parameters, 2);
                    lookupEnvironment.missingClassFileLocation = null;
                    return problemMethodBinding3;
                }
                MethodBinding methodBindingMostSpecificMethodBinding = mostSpecificMethodBinding(methodBindingArr2, i3, typeBindingArr, invocationSite, referenceBinding);
                lookupEnvironment.missingClassFileLocation = null;
                return methodBindingMostSpecificMethodBinding;
            } catch (AbortCompilation e) {
                e.updateContext(invocationSite, referenceCompilationUnit().compilationResult);
                throw e;
            }
        } catch (Throwable th) {
            lookupEnvironment.missingClassFileLocation = null;
            throw th;
        }
    }

    public final PackageBinding getCurrentPackage() {
        Scope scope = this;
        while (true) {
            Scope scope2 = scope.parent;
            if (scope2 == null) {
                return ((CompilationUnitScope) scope).fPackage;
            }
            scope = scope2;
        }
    }

    public int getDeclarationModifiers() {
        SourceTypeBinding sourceTypeBinding;
        int i = this.kind;
        if (i != 1 && i != 2) {
            if (i == 3 && (sourceTypeBinding = ((ClassScope) this).referenceType().binding) != null) {
                return sourceTypeBinding.modifiers;
            }
            return -1;
        }
        MethodScope methodScope = methodScope();
        if (!methodScope.isInsideInitializer()) {
            MethodBinding methodBinding = ((AbstractMethodDeclaration) methodScope.referenceContext).binding;
            if (methodBinding != null) {
                return methodBinding.modifiers;
            }
            return -1;
        }
        SourceTypeBinding sourceTypeBinding2 = ((BlockScope) this).referenceType().binding;
        if (methodScope.initializedField != null) {
            return methodScope.initializedField.modifiers;
        }
        if (sourceTypeBinding2 != null) {
            return sourceTypeBinding2.modifiers;
        }
        return -1;
    }

    public FieldBinding getField(TypeBinding typeBinding, char[] cArr, InvocationSite invocationSite) {
        LookupEnvironment lookupEnvironmentEnvironment = environment();
        try {
            try {
                lookupEnvironmentEnvironment.missingClassFileLocation = invocationSite;
                FieldBinding fieldBindingFindField = findField(typeBinding, cArr, invocationSite, true);
                if (fieldBindingFindField == null) {
                    ProblemFieldBinding problemFieldBinding = new ProblemFieldBinding(typeBinding instanceof ReferenceBinding ? (ReferenceBinding) typeBinding : null, cArr, 1);
                    lookupEnvironmentEnvironment.missingClassFileLocation = null;
                    return problemFieldBinding;
                }
                lookupEnvironmentEnvironment.missingClassFileLocation = null;
                return fieldBindingFindField;
            } catch (AbortCompilation e) {
                e.updateContext(invocationSite, referenceCompilationUnit().compilationResult);
                throw e;
            }
        } catch (Throwable th) {
            lookupEnvironmentEnvironment.missingClassFileLocation = null;
            throw th;
        }
    }

    /* JADX WARN: Code duplicated, block: B:104:0x01a2  */
    /* JADX WARN: Code duplicated, block: B:201:0x02e5  */
    /* JADX WARN: Code duplicated, block: B:202:0x02e8  */
    /* JADX WARN: Code duplicated, block: B:58:0x011f A[PHI: r12 r14 r18 r21 r22
      0x011f: PHI (r12v11 org.eclipse.jdt.internal.compiler.util.ObjectVector) = 
      (r12v10 org.eclipse.jdt.internal.compiler.util.ObjectVector)
      (r12v10 org.eclipse.jdt.internal.compiler.util.ObjectVector)
      (r12v10 org.eclipse.jdt.internal.compiler.util.ObjectVector)
      (r12v10 org.eclipse.jdt.internal.compiler.util.ObjectVector)
      (r12v13 org.eclipse.jdt.internal.compiler.util.ObjectVector)
     binds: [B:52:0x00f6, B:54:0x0100, B:56:0x010c, B:49:0x00e2, B:45:0x00c5] A[DONT_GENERATE, DONT_INLINE]
      0x011f: PHI (r14v5 org.eclipse.jdt.internal.compiler.lookup.ImportBinding) = 
      (r14v4 org.eclipse.jdt.internal.compiler.lookup.ImportBinding)
      (r14v4 org.eclipse.jdt.internal.compiler.lookup.ImportBinding)
      (r14v4 org.eclipse.jdt.internal.compiler.lookup.ImportBinding)
      (r14v4 org.eclipse.jdt.internal.compiler.lookup.ImportBinding)
      (r14v13 org.eclipse.jdt.internal.compiler.lookup.ImportBinding)
     binds: [B:52:0x00f6, B:54:0x0100, B:56:0x010c, B:49:0x00e2, B:45:0x00c5] A[DONT_GENERATE, DONT_INLINE]
      0x011f: PHI (r18v10 int) = (r18v9 int), (r18v9 int), (r18v9 int), (r18v9 int), (r18v12 int) binds: [B:52:0x00f6, B:54:0x0100, B:56:0x010c, B:49:0x00e2, B:45:0x00c5] A[DONT_GENERATE, DONT_INLINE]
      0x011f: PHI (r21v1 org.eclipse.jdt.internal.compiler.lookup.MethodBinding) = 
      (r21v0 org.eclipse.jdt.internal.compiler.lookup.MethodBinding)
      (r21v0 org.eclipse.jdt.internal.compiler.lookup.MethodBinding)
      (r21v0 org.eclipse.jdt.internal.compiler.lookup.MethodBinding)
      (r21v0 org.eclipse.jdt.internal.compiler.lookup.MethodBinding)
      (r21v3 org.eclipse.jdt.internal.compiler.lookup.MethodBinding)
     binds: [B:52:0x00f6, B:54:0x0100, B:56:0x010c, B:49:0x00e2, B:45:0x00c5] A[DONT_GENERATE, DONT_INLINE]
      0x011f: PHI (r22v7 org.eclipse.jdt.internal.compiler.lookup.MethodBinding) = 
      (r22v6 org.eclipse.jdt.internal.compiler.lookup.MethodBinding)
      (r22v6 org.eclipse.jdt.internal.compiler.lookup.MethodBinding)
      (r22v6 org.eclipse.jdt.internal.compiler.lookup.MethodBinding)
      (r22v6 org.eclipse.jdt.internal.compiler.lookup.MethodBinding)
      (r22v9 org.eclipse.jdt.internal.compiler.lookup.MethodBinding)
     binds: [B:52:0x00f6, B:54:0x0100, B:56:0x010c, B:49:0x00e2, B:45:0x00c5] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v62 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r17v0 */
    /* JADX WARN: Type inference failed for: r17v1 */
    /* JADX WARN: Type inference failed for: r17v2 */
    /* JADX WARN: Type inference failed for: r17v3 */
    /* JADX WARN: Type inference failed for: r17v4 */
    /* JADX WARN: Type inference failed for: r17v5 */
    /* JADX WARN: Type inference failed for: r17v6 */
    /* JADX WARN: Type inference failed for: r17v8 */
    public MethodBinding getImplicitMethod(char[] cArr, TypeBinding[] typeBindingArr, InvocationSite invocationSite) {
        int i;
        int i2;
        int i3;
        MethodBinding methodBinding;
        MethodScope methodScope;
        MethodBinding methodBinding2;
        ObjectVector objectVector;
        int i4;
        MethodBinding methodBinding3;
        ImportBinding importBinding;
        MethodBinding methodBinding4;
        MethodBinding methodBindingFindMethod;
        CompilerOptions compilerOptions = compilerOptions();
        int i5 = 1;
        int i6 = 0;
        boolean z = compilerOptions.complianceLevel >= ClassFileConstants.JDK1_4;
        Scope scope = this;
        ?? r0 = 0;
        int i7 = 0;
        boolean zIsStatic = false;
        ?? r17 = 0;
        int i8 = 0;
        MethodBinding problemMethodBinding = null;
        MethodBinding methodBindingMostSpecificMethodBinding = null;
        MethodScope methodScope2 = null;
        while (true) {
            int i9 = scope.kind;
            if (i9 == 2) {
                i = i6;
                int i10 = i5;
                i2 = i7;
                i3 = i10;
                MethodScope methodScope3 = (MethodScope) scope;
                zIsStatic |= methodScope3.isStatic;
                r17 = (r17 == true ? 1 : 0) | (methodScope3.isConstructorCall ? 1 : 0);
                methodScope2 = methodScope3;
                r0 = methodScope3.insideTypeAnnotation;
                problemMethodBinding = problemMethodBinding;
            } else if (i9 == 3) {
                MethodBinding methodBinding5 = methodBindingMostSpecificMethodBinding;
                ClassScope classScope = (ClassScope) scope;
                ReferenceBinding referenceBindingEnclosingReceiverType = classScope.enclosingReceiverType();
                if (r0 == 0) {
                    MethodBinding methodBindingFindExactMethod = classScope.findExactMethod(referenceBindingEnclosingReceiverType, cArr, typeBindingArr, invocationSite);
                    if (methodBindingFindExactMethod == null) {
                        methodBindingFindExactMethod = classScope.findMethod(referenceBindingEnclosingReceiverType, cArr, typeBindingArr, invocationSite, false);
                    }
                    problemMethodBinding = methodBindingFindExactMethod;
                    methodBinding = methodBinding5;
                    if (problemMethodBinding == null) {
                        i = 0;
                    } else if (methodBinding == null) {
                        if (problemMethodBinding.isValidBinding()) {
                            if (!problemMethodBinding.isStatic() && (r17 != 0 || zIsStatic)) {
                                if (problemMethodBinding == 0 || problemMethodBinding.problemId() == 2) {
                                    return new ProblemMethodBinding(problemMethodBinding, problemMethodBinding.selector, problemMethodBinding.parameters, r17 != 0 ? 6 : 7);
                                }
                                return problemMethodBinding;
                            }
                            if (problemMethodBinding.isStatic() || methodScope2 == null) {
                                i = 0;
                            } else {
                                i = 0;
                                tagAsAccessingEnclosingInstanceStateOf(referenceBindingEnclosingReceiverType, false);
                            }
                            if (z || TypeBinding.equalsEquals(referenceBindingEnclosingReceiverType, problemMethodBinding.declaringClass) || referenceBindingEnclosingReceiverType.getMethods(cArr) != Binding.NO_METHODS) {
                                if (i8 != 0) {
                                    return problemMethodBinding;
                                }
                                if (i7 > 0) {
                                    invocationSite.setDepth(i7);
                                    invocationSite.setActualReceiverType(referenceBindingEnclosingReceiverType);
                                }
                                return (typeBindingArr == Binding.NO_PARAMETERS && CharOperation.equals(cArr, TypeConstants.GETCLASS) && problemMethodBinding.returnType.isParameterizedType()) ? environment().createGetClassMethod(referenceBindingEnclosingReceiverType, problemMethodBinding, this) : problemMethodBinding;
                            }
                            if (problemMethodBinding == 0 || problemMethodBinding.problemId() == 2) {
                                if (problemMethodBinding != 0) {
                                    problemMethodBinding = null;
                                }
                                if (i7 > 0) {
                                    invocationSite.setDepth(i7);
                                    invocationSite.setActualReceiverType(referenceBindingEnclosingReceiverType);
                                }
                                methodBindingMostSpecificMethodBinding = problemMethodBinding;
                                problemMethodBinding = problemMethodBinding;
                                i3 = 1;
                            }
                        } else {
                            i = 0;
                            if (problemMethodBinding.problemId() != 2) {
                                i3 = 1;
                                if (problemMethodBinding.problemId() != 1) {
                                    return problemMethodBinding;
                                }
                            } else {
                                i3 = 1;
                            }
                            if (problemMethodBinding == 0) {
                                problemMethodBinding = problemMethodBinding;
                            }
                            if (i8 == 0 && problemMethodBinding.problemId() == i3 && (methodBinding2 = ((ProblemMethodBinding) problemMethodBinding).closestMatch) != null && methodBinding2.canBeSeenBy(referenceBindingEnclosingReceiverType, invocationSite, this)) {
                                methodBindingMostSpecificMethodBinding = methodBinding;
                                i8 = i3;
                            }
                        }
                        int i11 = i7 + 1;
                        zIsStatic |= referenceBindingEnclosingReceiverType.isStatic();
                        methodScope = scope.methodScope();
                        if (methodScope == null) {
                            r17 = i;
                        } else {
                            r17 = methodScope.isConstructorCall;
                        }
                        i2 = i11;
                        r0 = i;
                    } else {
                        i = 0;
                        i3 = 1;
                        if (problemMethodBinding.problemId() == 3 || (TypeBinding.notEquals(methodBinding.declaringClass, problemMethodBinding.declaringClass) && (TypeBinding.equalsEquals(referenceBindingEnclosingReceiverType, problemMethodBinding.declaringClass) || referenceBindingEnclosingReceiverType.getMethods(cArr) != Binding.NO_METHODS))) {
                            return new ProblemMethodBinding(problemMethodBinding, cArr, typeBindingArr, 5);
                        }
                    }
                    i3 = 1;
                } else {
                    problemMethodBinding = problemMethodBinding;
                    methodBinding = methodBinding5;
                    i = 0;
                    int i12 = i5;
                    i7 = i7;
                    i3 = i12;
                }
                methodBindingMostSpecificMethodBinding = methodBinding;
                problemMethodBinding = problemMethodBinding;
                int i13 = i7 + 1;
                zIsStatic |= referenceBindingEnclosingReceiverType.isStatic();
                methodScope = scope.methodScope();
                if (methodScope == null) {
                    r17 = i;
                } else {
                    r17 = methodScope.isConstructorCall;
                }
                i2 = i13;
                r0 = i;
            } else {
                if (i9 == 4) {
                    if (!zIsStatic || compilerOptions.sourceLevel < ClassFileConstants.JDK1_5) {
                        methodBindingMostSpecificMethodBinding = methodBindingMostSpecificMethodBinding;
                    } else {
                        if (problemMethodBinding != null) {
                            if (problemMethodBinding.declaringClass != null && problemMethodBinding.declaringClass.f626id == i5) {
                                return problemMethodBinding;
                            }
                            if (problemMethodBinding.problemId() == i5 && i8 != 0) {
                                return problemMethodBinding;
                            }
                        }
                        CompilationUnitScope compilationUnitScope = (CompilationUnitScope) scope;
                        compilationUnitScope.faultInImports();
                        ImportBinding[] importBindingArr = compilationUnitScope.imports;
                        if (importBindingArr != null) {
                            int length = importBindingArr.length;
                            int i14 = i6;
                            int i15 = i14;
                            ObjectVector objectVector2 = null;
                            while (i14 < length) {
                                ImportBinding importBinding2 = importBindingArr[i14];
                                if (importBinding2.isStatic()) {
                                    Binding binding = importBinding2.resolvedImport;
                                    if (importBinding2.onDemand) {
                                        if (i15 == 0 && (binding instanceof ReferenceBinding)) {
                                            importBinding = importBinding2;
                                            objectVector = objectVector2;
                                            i4 = i14;
                                            methodBinding4 = problemMethodBinding;
                                            methodBinding3 = methodBindingMostSpecificMethodBinding;
                                            methodBindingFindMethod = findMethod((ReferenceBinding) binding, cArr, typeBindingArr, invocationSite, true);
                                        } else {
                                            importBinding = importBinding2;
                                            objectVector = objectVector2;
                                            i4 = i14;
                                            methodBinding4 = problemMethodBinding;
                                            methodBinding3 = methodBindingMostSpecificMethodBinding;
                                            methodBindingFindMethod = null;
                                        }
                                    } else {
                                        importBinding = importBinding2;
                                        objectVector = objectVector2;
                                        i4 = i14;
                                        methodBinding4 = problemMethodBinding;
                                        methodBinding3 = methodBindingMostSpecificMethodBinding;
                                        if (binding instanceof MethodBinding) {
                                            MethodBinding methodBinding6 = (MethodBinding) binding;
                                            if (CharOperation.equals(methodBinding6.selector, cArr)) {
                                                methodBindingFindMethod = findMethod(methodBinding6.declaringClass, cArr, typeBindingArr, invocationSite, true);
                                            } else {
                                                methodBindingFindMethod = null;
                                            }
                                        } else if ((binding instanceof FieldBinding) && CharOperation.equals(((FieldBinding) binding).f628name, cArr)) {
                                            char[][] cArr2 = importBinding.reference.tokens;
                                            TypeBinding type = getType(cArr2, cArr2.length - i5);
                                            if (type != null) {
                                                methodBindingFindMethod = findMethod((ReferenceBinding) type, cArr, typeBindingArr, invocationSite, true);
                                            } else {
                                                methodBindingFindMethod = null;
                                            }
                                        } else {
                                            methodBindingFindMethod = null;
                                        }
                                    }
                                    problemMethodBinding = methodBinding4;
                                    if (methodBindingFindMethod != null && methodBindingFindMethod != problemMethodBinding) {
                                        if (methodBindingFindMethod.isValidBinding()) {
                                            if (methodBindingFindMethod.isStatic()) {
                                                MethodBinding methodBindingComputeCompatibleMethod = computeCompatibleMethod(methodBindingFindMethod, typeBindingArr, invocationSite);
                                                if (methodBindingComputeCompatibleMethod != null) {
                                                    if (methodBindingComputeCompatibleMethod.isValidBinding()) {
                                                        if (methodBindingComputeCompatibleMethod.canBeSeenBy(compilationUnitScope.fPackage)) {
                                                            if (i15 != 0 || importBinding.onDemand) {
                                                                objectVector2 = objectVector;
                                                            } else {
                                                                i15 = i5;
                                                                objectVector2 = null;
                                                            }
                                                            if (objectVector2 == null || !objectVector2.contains(methodBindingComputeCompatibleMethod)) {
                                                                ImportReference importReference = importBinding.reference;
                                                                if (importReference != null) {
                                                                    importReference.bits |= 2;
                                                                }
                                                                if (objectVector2 == null) {
                                                                    objectVector2 = new ObjectVector(3);
                                                                }
                                                                objectVector2.add(methodBindingComputeCompatibleMethod);
                                                            }
                                                        } else if (problemMethodBinding == null) {
                                                            problemMethodBinding = new ProblemMethodBinding(methodBindingComputeCompatibleMethod, cArr, methodBindingComputeCompatibleMethod.parameters, 2);
                                                        }
                                                    } else if (problemMethodBinding == null) {
                                                        problemMethodBinding = methodBindingComputeCompatibleMethod;
                                                    }
                                                } else if (problemMethodBinding == null) {
                                                    methodBindingComputeCompatibleMethod = new ProblemMethodBinding(methodBindingFindMethod, cArr, typeBindingArr, i5);
                                                    problemMethodBinding = methodBindingComputeCompatibleMethod;
                                                }
                                            }
                                            objectVector2 = objectVector;
                                        } else if (problemMethodBinding == null) {
                                            problemMethodBinding = methodBindingFindMethod;
                                            objectVector2 = objectVector;
                                        }
                                    }
                                    i14 = i4 + 1;
                                    methodBindingMostSpecificMethodBinding = methodBinding3;
                                    i6 = 0;
                                } else {
                                    objectVector = objectVector2;
                                    i4 = i14;
                                    methodBinding3 = methodBindingMostSpecificMethodBinding;
                                }
                                objectVector2 = objectVector;
                                i14 = i4 + 1;
                                methodBindingMostSpecificMethodBinding = methodBinding3;
                                i6 = 0;
                            }
                            if (objectVector2 != null) {
                                if (objectVector2.size == i5) {
                                    methodBindingMostSpecificMethodBinding = (MethodBinding) objectVector2.elementAt(i6);
                                } else {
                                    int i16 = objectVector2.size;
                                    MethodBinding[] methodBindingArr = new MethodBinding[i16];
                                    objectVector2.copyInto(methodBindingArr);
                                    methodBindingMostSpecificMethodBinding = mostSpecificMethodBinding(methodBindingArr, i16, typeBindingArr, invocationSite, null);
                                    problemMethodBinding = problemMethodBinding;
                                }
                            }
                        } else {
                            methodBindingMostSpecificMethodBinding = methodBindingMostSpecificMethodBinding;
                        }
                    }
                    if (methodBindingMostSpecificMethodBinding == null) {
                        return problemMethodBinding != null ? problemMethodBinding : new ProblemMethodBinding(cArr, typeBindingArr, i5);
                    }
                    invocationSite.setActualReceiverType(methodBindingMostSpecificMethodBinding.declaringClass);
                    return methodBindingMostSpecificMethodBinding;
                }
                i = i6;
                int i17 = i5;
                i2 = i7;
                i3 = i17;
            }
            scope = scope.parent;
            i6 = i;
            int i18 = i2;
            i5 = i3;
            i7 = i18;
            r0 = r0;
            r17 = r17;
        }
    }

    public final ReferenceBinding getJavaIoSerializable() {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordQualifiedReference(TypeConstants.JAVA_IO_SERIALIZABLE);
        return compilationUnitScope.environment.getResolvedType(TypeConstants.JAVA_IO_SERIALIZABLE, this);
    }

    public final ReferenceBinding getJavaLangAnnotationAnnotation() {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordQualifiedReference(TypeConstants.JAVA_LANG_ANNOTATION_ANNOTATION);
        return compilationUnitScope.environment.getResolvedType(TypeConstants.JAVA_LANG_ANNOTATION_ANNOTATION, this);
    }

    public final ReferenceBinding getJavaLangAssertionError() {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordQualifiedReference(TypeConstants.JAVA_LANG_ASSERTIONERROR);
        return compilationUnitScope.environment.getResolvedType(TypeConstants.JAVA_LANG_ASSERTIONERROR, this);
    }

    public final ReferenceBinding getJavaLangClass() {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordQualifiedReference(TypeConstants.JAVA_LANG_CLASS);
        return compilationUnitScope.environment.getResolvedType(TypeConstants.JAVA_LANG_CLASS, this);
    }

    public final ReferenceBinding getJavaLangCloneable() {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordQualifiedReference(TypeConstants.JAVA_LANG_CLONEABLE);
        return compilationUnitScope.environment.getResolvedType(TypeConstants.JAVA_LANG_CLONEABLE, this);
    }

    public final ReferenceBinding getJavaLangEnum() {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordQualifiedReference(TypeConstants.JAVA_LANG_ENUM);
        return compilationUnitScope.environment.getResolvedType(TypeConstants.JAVA_LANG_ENUM, this);
    }

    public final ReferenceBinding getJavaLangInvokeLambdaMetafactory() {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordQualifiedReference(TypeConstants.JAVA_LANG_INVOKE_LAMBDAMETAFACTORY);
        return compilationUnitScope.environment.getResolvedType(TypeConstants.JAVA_LANG_INVOKE_LAMBDAMETAFACTORY, this);
    }

    public final ReferenceBinding getJavaLangInvokeSerializedLambda() {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordQualifiedReference(TypeConstants.JAVA_LANG_INVOKE_SERIALIZEDLAMBDA);
        return compilationUnitScope.environment.getResolvedType(TypeConstants.JAVA_LANG_INVOKE_SERIALIZEDLAMBDA, this);
    }

    public final ReferenceBinding getJavaLangInvokeMethodHandlesLookup() {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordQualifiedReference(TypeConstants.JAVA_LANG_INVOKE_METHODHANDLES);
        return findDirectMemberType("Lookup".toCharArray(), compilationUnitScope.environment.getResolvedType(TypeConstants.JAVA_LANG_INVOKE_METHODHANDLES, this));
    }

    public final ReferenceBinding getJavaLangIterable() {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordQualifiedReference(TypeConstants.JAVA_LANG_ITERABLE);
        return compilationUnitScope.environment.getResolvedType(TypeConstants.JAVA_LANG_ITERABLE, this);
    }

    public final ReferenceBinding getJavaLangObject() {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordQualifiedReference(TypeConstants.JAVA_LANG_OBJECT);
        return compilationUnitScope.environment.getResolvedType(TypeConstants.JAVA_LANG_OBJECT, this);
    }

    public final ReferenceBinding getJavaLangString() {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordQualifiedReference(TypeConstants.JAVA_LANG_STRING);
        return compilationUnitScope.environment.getResolvedType(TypeConstants.JAVA_LANG_STRING, this);
    }

    public final ReferenceBinding getJavaLangThrowable() {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordQualifiedReference(TypeConstants.JAVA_LANG_THROWABLE);
        return compilationUnitScope.environment.getResolvedType(TypeConstants.JAVA_LANG_THROWABLE, this);
    }

    public final ReferenceBinding getJavaLangIllegalArgumentException() {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordQualifiedReference(TypeConstants.JAVA_LANG_ILLEGALARGUMENTEXCEPTION);
        return compilationUnitScope.environment.getResolvedType(TypeConstants.JAVA_LANG_ILLEGALARGUMENTEXCEPTION, this);
    }

    public final ReferenceBinding getJavaUtilIterator() {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordQualifiedReference(TypeConstants.JAVA_UTIL_ITERATOR);
        return compilationUnitScope.environment.getResolvedType(TypeConstants.JAVA_UTIL_ITERATOR, this);
    }

    public final ReferenceBinding getMemberType(char[] cArr, ReferenceBinding referenceBinding) {
        ReferenceBinding referenceBindingFindMemberType = findMemberType(cArr, referenceBinding);
        return referenceBindingFindMemberType != null ? referenceBindingFindMemberType : new ProblemReferenceBinding(new char[][]{cArr}, null, 1);
    }

    public MethodBinding getMethod(TypeBinding typeBinding, char[] cArr, TypeBinding[] typeBindingArr, InvocationSite invocationSite) {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        LookupEnvironment lookupEnvironment = compilationUnitScope.environment;
        try {
            try {
                lookupEnvironment.missingClassFileLocation = invocationSite;
                int iKind = typeBinding.kind();
                if (iKind == 68) {
                    compilationUnitScope.recordTypeReference(typeBinding);
                    MethodBinding methodBindingFindMethodForArray = findMethodForArray((ArrayBinding) typeBinding, cArr, typeBindingArr, invocationSite);
                    lookupEnvironment.missingClassFileLocation = null;
                    return methodBindingFindMethodForArray;
                }
                if (iKind == 132) {
                    ProblemMethodBinding problemMethodBinding = new ProblemMethodBinding(cArr, typeBindingArr, 1);
                    lookupEnvironment.missingClassFileLocation = null;
                    return problemMethodBinding;
                }
                compilationUnitScope.recordTypeReference(typeBinding);
                ReferenceBinding referenceBinding = (ReferenceBinding) typeBinding;
                if (!referenceBinding.canBeSeenBy(this)) {
                    ProblemMethodBinding problemMethodBinding2 = new ProblemMethodBinding(cArr, typeBindingArr, 8);
                    lookupEnvironment.missingClassFileLocation = null;
                    return problemMethodBinding2;
                }
                MethodBinding methodBindingFindExactMethod = findExactMethod(referenceBinding, cArr, typeBindingArr, invocationSite);
                if (methodBindingFindExactMethod != null && methodBindingFindExactMethod.isValidBinding()) {
                    lookupEnvironment.missingClassFileLocation = null;
                    return methodBindingFindExactMethod;
                }
                MethodBinding methodBindingFindMethod = findMethod(referenceBinding, cArr, typeBindingArr, invocationSite, false);
                if (methodBindingFindMethod == null) {
                    ProblemMethodBinding problemMethodBinding3 = new ProblemMethodBinding(cArr, typeBindingArr, 1);
                    lookupEnvironment.missingClassFileLocation = null;
                    return problemMethodBinding3;
                }
                if (!methodBindingFindMethod.isValidBinding()) {
                    lookupEnvironment.missingClassFileLocation = null;
                    return methodBindingFindMethod;
                }
                if (typeBindingArr != Binding.NO_PARAMETERS || !CharOperation.equals(cArr, TypeConstants.GETCLASS) || !methodBindingFindMethod.returnType.isParameterizedType()) {
                    lookupEnvironment.missingClassFileLocation = null;
                    return methodBindingFindMethod;
                }
                ParameterizedMethodBinding parameterizedMethodBindingCreateGetClassMethod = environment().createGetClassMethod(typeBinding, methodBindingFindMethod, this);
                lookupEnvironment.missingClassFileLocation = null;
                return parameterizedMethodBindingCreateGetClassMethod;
            } catch (AbortCompilation e) {
                e.updateContext(invocationSite, referenceCompilationUnit().compilationResult);
                throw e;
            }
        } catch (Throwable th) {
            lookupEnvironment.missingClassFileLocation = null;
            throw th;
        }
    }

    public final Binding getPackage(char[][] cArr) {
        compilationUnitScope().recordQualifiedReference(cArr);
        Binding typeOrPackage = getTypeOrPackage(cArr[0], 20, true);
        if (typeOrPackage == null) {
            return new ProblemReferenceBinding(new char[][]{cArr[0]}, environment().createMissingType(null, cArr), 1);
        }
        if (!typeOrPackage.isValidBinding()) {
            return typeOrPackage instanceof PackageBinding ? new ProblemReferenceBinding(new char[][]{cArr[0]}, null, 1) : typeOrPackage;
        }
        if (!(typeOrPackage instanceof PackageBinding)) {
            return null;
        }
        int length = cArr.length;
        PackageBinding packageBinding = (PackageBinding) typeOrPackage;
        int i = 1;
        while (i < length) {
            int i2 = i + 1;
            Binding typeOrPackage2 = packageBinding.getTypeOrPackage(cArr[i]);
            if (typeOrPackage2 == null) {
                return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i2), null, 1);
            }
            if (!typeOrPackage2.isValidBinding()) {
                return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i2), typeOrPackage2 instanceof ReferenceBinding ? (ReferenceBinding) ((ReferenceBinding) typeOrPackage2).closestMatch() : null, typeOrPackage2.problemId());
            }
            if (!(typeOrPackage2 instanceof PackageBinding)) {
                return packageBinding;
            }
            packageBinding = (PackageBinding) typeOrPackage2;
            i = i2;
        }
        return new ProblemReferenceBinding(cArr, null, 1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [org.eclipse.jdt.internal.compiler.lookup.Binding, org.eclipse.jdt.internal.compiler.lookup.PackageBinding] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v7 */
    public final Binding getOnlyPackage(char[][] cArr) {
        ?? r1;
        compilationUnitScope().recordQualifiedReference(cArr);
        Binding typeOrPackage = getTypeOrPackage(cArr[0], 16, true);
        if (typeOrPackage == null || !typeOrPackage.isValidBinding()) {
            return new ProblemReferenceBinding(new char[][]{cArr[0]}, null, 1);
        }
        if (!(typeOrPackage instanceof PackageBinding)) {
            return null;
        }
        int length = cArr.length;
        PackageBinding packageBinding = (PackageBinding) typeOrPackage;
        int i = 1;
        while (i < length) {
            int i2 = i + 1;
            Binding binding = r1.getPackage(cArr[i]);
            if (binding == null) {
                r1 = packageBinding;
                return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i2), null, 1);
            }
            if (!binding.isValidBinding()) {
                r1 = packageBinding;
                return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i2), binding instanceof ReferenceBinding ? (ReferenceBinding) ((ReferenceBinding) binding).closestMatch() : null, binding.problemId());
            }
            r1 = packageBinding;
            i = i2;
            r1 = binding;
        }
        r1 = packageBinding;
        return r1;
    }

    public final TypeBinding getType(char[] cArr) {
        TypeBinding baseType = getBaseType(cArr);
        return baseType != null ? baseType : (ReferenceBinding) getTypeOrPackage(cArr, 4, true);
    }

    public final TypeBinding getType(char[] cArr, PackageBinding packageBinding) {
        if (packageBinding == null) {
            return getType(cArr);
        }
        Binding typeOrPackage = packageBinding.getTypeOrPackage(cArr);
        if (typeOrPackage == null) {
            return new ProblemReferenceBinding(CharOperation.arrayConcat(packageBinding.compoundName, cArr), null, 1);
        }
        if (!typeOrPackage.isValidBinding()) {
            boolean z = typeOrPackage instanceof ReferenceBinding;
            return new ProblemReferenceBinding(z ? ((ReferenceBinding) typeOrPackage).compoundName : CharOperation.arrayConcat(packageBinding.compoundName, cArr), z ? (ReferenceBinding) ((ReferenceBinding) typeOrPackage).closestMatch() : null, typeOrPackage.problemId());
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) typeOrPackage;
        return !referenceBinding.canBeSeenBy(this) ? new ProblemReferenceBinding(referenceBinding.compoundName, referenceBinding, 2) : referenceBinding;
    }

    public final TypeBinding getType(char[][] cArr, int i) {
        int i2;
        TypeBinding baseType;
        boolean z = true;
        if (i == 1 && (baseType = getBaseType(cArr[0])) != null) {
            return baseType;
        }
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordQualifiedReference(cArr);
        Binding typeOrPackage = getTypeOrPackage(cArr[0], i == 1 ? 4 : 20, true);
        if (typeOrPackage == null) {
            char[][] cArr2 = {cArr[0]};
            return new ProblemReferenceBinding(cArr2, environment().createMissingType(compilationUnitScope().getCurrentPackage(), cArr2), 1);
        }
        if (!typeOrPackage.isValidBinding()) {
            if (typeOrPackage instanceof PackageBinding) {
                char[][] cArr3 = {cArr[0]};
                return new ProblemReferenceBinding(cArr3, environment().createMissingType(null, cArr3), 1);
            }
            return (ReferenceBinding) typeOrPackage;
        }
        if (typeOrPackage instanceof PackageBinding) {
            PackageBinding packageBinding = (PackageBinding) typeOrPackage;
            i2 = 1;
            while (i2 < i) {
                int i3 = i2 + 1;
                Binding typeOrPackage2 = packageBinding.getTypeOrPackage(cArr[i2]);
                if (typeOrPackage2 == null) {
                    char[][] cArrSubarray = CharOperation.subarray(cArr, 0, i3);
                    return new ProblemReferenceBinding(cArrSubarray, environment().createMissingType(packageBinding, cArrSubarray), 1);
                }
                if (!typeOrPackage2.isValidBinding()) {
                    return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i3), typeOrPackage2 instanceof ReferenceBinding ? (ReferenceBinding) ((ReferenceBinding) typeOrPackage2).closestMatch() : null, typeOrPackage2.problemId());
                }
                if (!(typeOrPackage2 instanceof PackageBinding)) {
                    i2 = i3;
                    typeOrPackage = typeOrPackage2;
                    break;
                }
                packageBinding = (PackageBinding) typeOrPackage2;
                i2 = i3;
                typeOrPackage = typeOrPackage2;
            }
            if (typeOrPackage instanceof PackageBinding) {
                char[][] cArrSubarray2 = CharOperation.subarray(cArr, 0, i2);
                return new ProblemReferenceBinding(cArrSubarray2, environment().createMissingType(null, cArrSubarray2), 1);
            }
        } else {
            i2 = 1;
            z = false;
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) typeOrPackage;
        compilationUnitScope.recordTypeReference(referenceBinding);
        if (z && !referenceBinding.canBeSeenBy(this)) {
            return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i2), referenceBinding, 2);
        }
        ReferenceBinding memberType = referenceBinding;
        while (i2 < i) {
            int i4 = i2 + 1;
            memberType = getMemberType(cArr[i2], memberType);
            if (!memberType.isValidBinding()) {
                if (memberType instanceof ProblemReferenceBinding) {
                    return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i4), ((ProblemReferenceBinding) memberType).closestReferenceMatch(), memberType.problemId());
                }
                return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i4), (ReferenceBinding) referenceBinding.closestMatch(), memberType.problemId());
            }
            i2 = i4;
        }
        return memberType;
    }

    /* JADX WARN: Code duplicated, block: B:139:0x01af  */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$ArrayArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    final Binding getTypeOrPackage(char[] cArr, int i, boolean z) {
        ReferenceBinding referenceBinding;
        ReferenceBinding referenceBindingFindMemberType;
        Scope scope;
        MissingTypeBinding missingTypeBindingCreateMissingType;
        PackageBinding topLevelPackage;
        ReferenceBinding referenceBindingFindDirectMemberType;
        Binding bindingResolveSingleImport;
        Binding binding;
        int i2 = i & 4;
        int i3 = 3;
        int i4 = 1;
        if (i2 == 0) {
            scope = this;
            while (true) {
                Scope scope2 = scope.parent;
                if (scope2 == null) {
                    break;
                }
                scope = scope2;
            }
            referenceBinding = null;
        } else {
            boolean z2 = compilerOptions().complianceLevel >= ClassFileConstants.JDK1_4;
            Scope scope3 = this;
            referenceBinding = null;
            boolean z3 = false;
            boolean zIsStatic = false;
            while (true) {
                int i5 = scope3.kind;
                if (i5 != i4) {
                    if (i5 == 2) {
                        MethodScope methodScope = (MethodScope) scope3;
                        AbstractMethodDeclaration abstractMethodDeclarationReferenceMethod = methodScope.referenceMethod();
                        if (abstractMethodDeclarationReferenceMethod != null) {
                            if (abstractMethodDeclarationReferenceMethod.binding != null) {
                                TypeVariableBinding typeVariable = abstractMethodDeclarationReferenceMethod.binding.getTypeVariable(cArr);
                                if (typeVariable != null) {
                                    return typeVariable;
                                }
                            } else {
                                TypeParameter[] typeParameterArrTypeParameters = abstractMethodDeclarationReferenceMethod.typeParameters();
                                int length = typeParameterArrTypeParameters == null ? 0 : typeParameterArrTypeParameters.length;
                                while (true) {
                                    length--;
                                    if (length < 0) {
                                        break;
                                    }
                                    if (CharOperation.equals(typeParameterArrTypeParameters[length].f617name, cArr) && typeParameterArrTypeParameters[length].binding != null && typeParameterArrTypeParameters[length].binding.isValidBinding()) {
                                        return typeParameterArrTypeParameters[length].binding;
                                    }
                                }
                            }
                        }
                        zIsStatic |= methodScope.isStatic;
                        z3 = methodScope.insideTypeAnnotation;
                    } else if (i5 == i3) {
                        SourceTypeBinding sourceTypeBinding = ((ClassScope) scope3).referenceContext.binding;
                        if (scope3 == this && (sourceTypeBinding.tagBits & 262144) == 0) {
                            TypeVariableBinding typeVariable2 = sourceTypeBinding.getTypeVariable(cArr);
                            if (typeVariable2 != null) {
                                return typeVariable2;
                            }
                            if (CharOperation.equals(cArr, sourceTypeBinding.sourceName)) {
                                return sourceTypeBinding;
                            }
                            zIsStatic |= sourceTypeBinding.isStatic();
                            i3 = 3;
                        } else {
                            if (z3 || (referenceBindingFindMemberType = findMemberType(cArr, sourceTypeBinding)) == null) {
                                i3 = 3;
                            } else {
                                i3 = 3;
                                if (referenceBindingFindMemberType.problemId() == 3) {
                                    return (referenceBinding == null || referenceBinding.problemId() == 2) ? referenceBindingFindMemberType : new ProblemReferenceBinding(new char[][]{cArr}, referenceBinding, 5);
                                }
                                if (referenceBindingFindMemberType.isValidBinding() && (TypeBinding.equalsEquals(sourceTypeBinding, referenceBindingFindMemberType.enclosingType()) || z2)) {
                                    if (zIsStatic && !referenceBindingFindMemberType.isStatic() && sourceTypeBinding.isGenericType()) {
                                        return new ProblemReferenceBinding(new char[][]{cArr}, referenceBindingFindMemberType, 7);
                                    }
                                    if (referenceBinding == null || (z2 && referenceBinding.problemId() == 2)) {
                                        return referenceBindingFindMemberType;
                                    }
                                    if (referenceBinding.isValidBinding() && TypeBinding.notEquals(referenceBinding, referenceBindingFindMemberType)) {
                                        return new ProblemReferenceBinding(new char[][]{cArr}, referenceBinding, 5);
                                    }
                                }
                                if (referenceBinding == null || (referenceBinding.problemId() == 2 && referenceBindingFindMemberType.problemId() != 2)) {
                                    referenceBinding = referenceBindingFindMemberType;
                                }
                            }
                            TypeVariableBinding typeVariable3 = sourceTypeBinding.getTypeVariable(cArr);
                            if (typeVariable3 != null) {
                                return zIsStatic ? new ProblemReferenceBinding(new char[][]{cArr}, typeVariable3, 7) : typeVariable3;
                            }
                            zIsStatic |= sourceTypeBinding.isStatic();
                            if (CharOperation.equals(sourceTypeBinding.sourceName, cArr)) {
                                return (referenceBinding == null || !TypeBinding.notEquals(referenceBinding, sourceTypeBinding) || referenceBinding.problemId() == 2) ? sourceTypeBinding : new ProblemReferenceBinding(new char[][]{cArr}, referenceBinding, 5);
                            }
                            z3 = false;
                        }
                    } else {
                        if (i5 == 4) {
                            if (referenceBinding != null && referenceBinding.problemId() != 2) {
                                return referenceBinding;
                            }
                            scope = scope3;
                            break;
                        }
                        i3 = i3;
                    }
                    scope3 = scope3.parent;
                    i3 = i3;
                    i4 = 1;
                }
                ReferenceBinding referenceBindingFindLocalType = ((BlockScope) scope3).findLocalType(cArr);
                if (referenceBindingFindLocalType != null) {
                    return (referenceBinding == null || !TypeBinding.notEquals(referenceBinding, referenceBindingFindLocalType)) ? referenceBindingFindLocalType : new ProblemReferenceBinding(new char[][]{cArr}, referenceBinding, 5);
                }
                scope3 = scope3.parent;
                i3 = i3;
                i4 = 1;
            }
        }
        CompilationUnitScope compilationUnitScope = (CompilationUnitScope) scope;
        HashtableOfObject hashtableOfObject = compilationUnitScope.typeOrPackageCache;
        if (hashtableOfObject != null && (binding = (Binding) hashtableOfObject.get(cArr)) != null) {
            if (binding instanceof ImportBinding) {
                ImportBinding importBinding = (ImportBinding) binding;
                ImportReference importReference = importBinding.reference;
                if (importReference != null) {
                    importReference.bits |= 2;
                }
                if (binding instanceof ImportConflictBinding) {
                    binding = ((ImportConflictBinding) binding).conflictingTypeBinding;
                    hashtableOfObject.put(cArr, binding);
                } else {
                    binding = importBinding.resolvedImport;
                    hashtableOfObject.put(cArr, binding);
                }
            }
            if (i2 != 0) {
                if (referenceBinding != null && referenceBinding.problemId() != 2 && binding.problemId() != i3) {
                    return referenceBinding;
                }
                if (binding instanceof ReferenceBinding) {
                    return binding;
                }
            }
            if ((i & 16) != 0 && (binding instanceof PackageBinding)) {
                return binding;
            }
        }
        if (i2 != 0) {
            ImportBinding[] importBindingArr = compilationUnitScope.imports;
            if (importBindingArr != null && hashtableOfObject == null) {
                for (ImportBinding importBinding2 : importBindingArr) {
                    if (!importBinding2.onDemand && CharOperation.equals(importBinding2.compoundName[importBinding2.compoundName.length - i4], cArr) && (bindingResolveSingleImport = compilationUnitScope.resolveSingleImport(importBinding2, 4)) != null && (bindingResolveSingleImport instanceof TypeBinding)) {
                        ImportReference importReference2 = importBinding2.reference;
                        if (importReference2 != null) {
                            importReference2.bits |= 2;
                        }
                        return bindingResolveSingleImport;
                    }
                }
            }
            PackageBinding packageBinding = compilationUnitScope.fPackage;
            compilationUnitScope.recordReference(packageBinding.compoundName, cArr);
            Binding typeOrPackage = packageBinding.getTypeOrPackage(cArr);
            if (typeOrPackage instanceof ReferenceBinding) {
                ReferenceBinding referenceBinding2 = (ReferenceBinding) typeOrPackage;
                if ((referenceBinding2.tagBits & 128) == 0) {
                    if (hashtableOfObject != null) {
                        hashtableOfObject.put(cArr, referenceBinding2);
                    }
                    return referenceBinding2;
                }
            }
            if (importBindingArr != null) {
                ReferenceBinding referenceBinding3 = null;
                boolean z4 = false;
                for (ImportBinding importBinding3 : importBindingArr) {
                    if (importBinding3.onDemand) {
                        Binding binding2 = importBinding3.resolvedImport;
                        if (binding2 instanceof PackageBinding) {
                            referenceBindingFindDirectMemberType = findType(cArr, (PackageBinding) binding2, packageBinding);
                        } else if (importBinding3.isStatic()) {
                            referenceBindingFindDirectMemberType = findMemberType(cArr, (ReferenceBinding) binding2);
                            if (referenceBindingFindDirectMemberType != null && !referenceBindingFindDirectMemberType.isStatic()) {
                                referenceBindingFindDirectMemberType = null;
                            }
                        } else {
                            referenceBindingFindDirectMemberType = findDirectMemberType(cArr, (ReferenceBinding) binding2);
                        }
                        if (TypeBinding.notEquals(referenceBindingFindDirectMemberType, referenceBinding3) && referenceBindingFindDirectMemberType != null) {
                            if (referenceBindingFindDirectMemberType.isValidBinding()) {
                                ImportReference importReference3 = importBinding3.reference;
                                if (importReference3 != null) {
                                    importReference3.bits |= 2;
                                }
                                if (z4) {
                                    ProblemReferenceBinding problemReferenceBinding = new ProblemReferenceBinding(new char[][]{cArr}, referenceBinding3, 3);
                                    if (hashtableOfObject != null) {
                                        hashtableOfObject.put(cArr, problemReferenceBinding);
                                    }
                                    return problemReferenceBinding;
                                }
                                referenceBinding3 = referenceBindingFindDirectMemberType;
                                z4 = true;
                            } else if (referenceBinding == null) {
                                referenceBinding = referenceBindingFindDirectMemberType;
                            }
                        }
                    }
                }
                if (referenceBinding3 != null) {
                    if (hashtableOfObject != null) {
                        hashtableOfObject.put(cArr, referenceBinding3);
                    }
                    return referenceBinding3;
                }
            }
        }
        compilationUnitScope.recordSimpleReference(cArr);
        int i6 = i & 16;
        if (i6 != 0 && (topLevelPackage = compilationUnitScope.environment.getTopLevelPackage(cArr)) != null && (topLevelPackage.tagBits & 128) == 0) {
            if (hashtableOfObject != null) {
                hashtableOfObject.put(cArr, topLevelPackage);
            }
            return topLevelPackage;
        }
        if (referenceBinding != null) {
            if ((referenceBinding.tagBits & 128) == 0) {
                return referenceBinding;
            }
            ProblemReferenceBinding problemReferenceBinding2 = new ProblemReferenceBinding(new char[][]{cArr}, referenceBinding, 1);
            if (hashtableOfObject != null && i6 != 0) {
                hashtableOfObject.put(cArr, problemReferenceBinding2);
            }
            return problemReferenceBinding2;
        }
        char[][] cArr2 = {cArr};
        if (i6 == 0) {
            PackageBinding topLevelPackage2 = compilationUnitScope.environment.getTopLevelPackage(cArr);
            if ((topLevelPackage2 == null || !topLevelPackage2.isValidBinding()) && z) {
                missingTypeBindingCreateMissingType = environment().createMissingType(compilationUnitScope.fPackage, cArr2);
            } else {
                missingTypeBindingCreateMissingType = null;
            }
        } else if (z) {
            missingTypeBindingCreateMissingType = environment().createMissingType(compilationUnitScope.fPackage, cArr2);
        } else {
            missingTypeBindingCreateMissingType = null;
        }
        ProblemReferenceBinding problemReferenceBinding3 = new ProblemReferenceBinding(cArr2, missingTypeBindingCreateMissingType, 1);
        if (hashtableOfObject == null || i6 == 0) {
            return problemReferenceBinding3;
        }
        hashtableOfObject.put(cArr, problemReferenceBinding3);
        return problemReferenceBinding3;
    }

    public final Binding getTypeOrPackage(char[][] cArr) {
        int i;
        TypeBinding baseType;
        int length = cArr.length;
        boolean z = true;
        if (length == 1 && (baseType = getBaseType(cArr[0])) != null) {
            return baseType;
        }
        Binding typeOrPackage = getTypeOrPackage(cArr[0], 20, true);
        if (!typeOrPackage.isValidBinding()) {
            return typeOrPackage;
        }
        if (typeOrPackage instanceof PackageBinding) {
            PackageBinding packageBinding = (PackageBinding) typeOrPackage;
            i = 1;
            while (i < length) {
                int i2 = i + 1;
                Binding typeOrPackage2 = packageBinding.getTypeOrPackage(cArr[i]);
                if (typeOrPackage2 == null) {
                    return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i2), null, 1);
                }
                if (!typeOrPackage2.isValidBinding()) {
                    return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i2), typeOrPackage2 instanceof ReferenceBinding ? (ReferenceBinding) ((ReferenceBinding) typeOrPackage2).closestMatch() : null, typeOrPackage2.problemId());
                }
                if (!(typeOrPackage2 instanceof PackageBinding)) {
                    i = i2;
                    typeOrPackage = typeOrPackage2;
                    break;
                }
                i = i2;
                typeOrPackage = typeOrPackage2;
                packageBinding = (PackageBinding) typeOrPackage2;
            }
            if (typeOrPackage instanceof PackageBinding) {
                return typeOrPackage;
            }
        } else {
            i = 1;
            z = false;
        }
        ReferenceBinding memberType = (ReferenceBinding) typeOrPackage;
        ReferenceBinding referenceBindingCreateParameterizedType = (ReferenceBinding) environment().convertToRawType(memberType, false);
        if (z && !memberType.canBeSeenBy(this)) {
            return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i), memberType, 2);
        }
        while (i < length) {
            int i3 = i + 1;
            memberType = getMemberType(cArr[i], memberType);
            if (!memberType.isValidBinding()) {
                return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i3), (ReferenceBinding) memberType.closestMatch(), memberType.problemId());
            }
            if (memberType.isGenericType()) {
                referenceBindingCreateParameterizedType = environment().createRawType(memberType, referenceBindingCreateParameterizedType);
            } else {
                referenceBindingCreateParameterizedType = (referenceBindingCreateParameterizedType == null || !(referenceBindingCreateParameterizedType.isRawType() || referenceBindingCreateParameterizedType.isParameterizedType())) ? memberType : environment().createParameterizedType(memberType, null, referenceBindingCreateParameterizedType);
            }
            i = i3;
        }
        return referenceBindingCreateParameterizedType;
    }

    public boolean hasErasedCandidatesCollisions(TypeBinding typeBinding, TypeBinding typeBinding2, Map map, ReferenceBinding referenceBinding, ASTNode aSTNode) {
        map.clear();
        TypeBinding[] typeBindingArrMinimalErasedCandidates = minimalErasedCandidates(new TypeBinding[]{typeBinding, typeBinding2}, map);
        if (typeBindingArrMinimalErasedCandidates != null) {
            for (TypeBinding typeBinding3 : typeBindingArrMinimalErasedCandidates) {
                if (typeBinding3 != null) {
                    Object obj = map.get(typeBinding3);
                    if (obj instanceof TypeBinding[]) {
                        TypeBinding[] typeBindingArr = (TypeBinding[]) obj;
                        problemReporter().superinterfacesCollide(typeBindingArr[0].erasure(), aSTNode, typeBindingArr[0], typeBindingArr[1]);
                        referenceBinding.tagBits |= 131072;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public CaseStatement innermostSwitchCase() {
        Scope scope = this;
        while (!(scope instanceof BlockScope)) {
            scope = scope.parent;
            if (scope == null) {
                return null;
            }
        }
        return ((BlockScope) scope).enclosingCase;
    }

    /* JADX WARN: Code duplicated, block: B:111:0x00f6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:112:0x0109 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:116:0x0109 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:60:0x00cb  */
    /* JADX WARN: Code duplicated, block: B:62:0x00de  */
    /* JADX WARN: Code duplicated, block: B:64:0x00e2  */
    /* JADX WARN: Code duplicated, block: B:66:0x00e6  */
    /* JADX WARN: Code duplicated, block: B:68:0x00e9  */
    /* JADX WARN: Code duplicated, block: B:72:0x00f7  */
    /* JADX WARN: Code duplicated, block: B:74:0x00fd  */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v4, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r10v5 */
    protected boolean isAcceptableMethod(MethodBinding methodBinding, MethodBinding methodBinding2) {
        int iKind;
        TypeBinding typeBindingFindSuperTypeOriginatingFrom;
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        TypeBinding[] typeBindingArr2 = methodBinding2.parameters;
        int length = typeBindingArr.length;
        int length2 = typeBindingArr2.length;
        long j = ClassFileConstants.JDK1_7;
        ?? r10 = 1;
        if (length != length2) {
            if (!methodBinding.isVarargs() || !methodBinding2.isVarargs() || (CompilerOptions.tolerateIllegalAmbiguousVarargsInvocation && compilerOptions().complianceLevel < ClassFileConstants.JDK1_7 && length > length2 && ((ArrayBinding) typeBindingArr2[length2 - 1]).elementsType().f626id != 1)) {
                return false;
            }
            if (length > length2) {
                length = length2;
            }
            for (int i = length - 2; i >= 0; i--) {
                if (TypeBinding.notEquals(typeBindingArr[i], typeBindingArr2[i]) && !typeBindingArr[i].isCompatibleWith(typeBindingArr2[i])) {
                    return false;
                }
            }
            return parameterCompatibilityLevel(methodBinding, typeBindingArr2, true) == -1 && parameterCompatibilityLevel(methodBinding2, typeBindingArr, true) == 2;
        }
        boolean z = environment().globalOptions.sourceLevel < ClassFileConstants.JDK1_5;
        int i2 = 0;
        while (i2 < length) {
            TypeBinding typeBindingErasure = z ? typeBindingArr[i2].erasure() : typeBindingArr[i2];
            TypeBinding typeBindingErasure2 = z ? typeBindingArr2[i2].erasure() : typeBindingArr2[i2];
            if (TypeBinding.equalsEquals(typeBindingErasure, typeBindingErasure2) || typeBindingErasure.isCompatibleWith(typeBindingErasure2)) {
                if (!methodBinding2.declaringClass.isRawType()) {
                    TypeBinding typeBindingLeafComponentType = methodBinding2.original().parameters[i2].leafComponentType();
                    if (z) {
                        typeBindingLeafComponentType = typeBindingLeafComponentType.erasure();
                    }
                    int iKind2 = typeBindingLeafComponentType.kind();
                    if (iKind2 == 260 || iKind2 == 516) {
                        TypeBinding typeBindingLeafComponentType2 = methodBinding.original().parameters[i2].leafComponentType();
                        iKind = typeBindingLeafComponentType2.kind();
                        if (iKind != 4) {
                            if (iKind != 1028) {
                                if (iKind == 2052) {
                                    typeBindingFindSuperTypeOriginatingFrom = typeBindingErasure.findSuperTypeOriginatingFrom(typeBindingErasure2);
                                    if (typeBindingFindSuperTypeOriginatingFrom != null) {
                                        continue;
                                    }
                                } else if (iKind != 4100 && ((TypeVariableBinding) typeBindingLeafComponentType2).upperBound().isRawType()) {
                                }
                            }
                            return false;
                        }
                        typeBindingFindSuperTypeOriginatingFrom = typeBindingErasure.findSuperTypeOriginatingFrom(typeBindingErasure2);
                        if (typeBindingFindSuperTypeOriginatingFrom != null && typeBindingFindSuperTypeOriginatingFrom.leafComponentType().isRawType()) {
                            return false;
                        }
                    } else if (iKind2 != 4100) {
                        if (iKind2 != 8196) {
                            continue;
                        } else {
                            TypeBinding typeBindingLeafComponentType3 = methodBinding.original().parameters[i2].leafComponentType();
                            iKind = typeBindingLeafComponentType3.kind();
                            if (iKind != 4) {
                                if (iKind != 1028) {
                                    if (iKind == 2052) {
                                        typeBindingFindSuperTypeOriginatingFrom = typeBindingErasure.findSuperTypeOriginatingFrom(typeBindingErasure2);
                                        if (typeBindingFindSuperTypeOriginatingFrom != null) {
                                            continue;
                                        }
                                    } else if (iKind != 4100) {
                                        continue;
                                    }
                                }
                                return false;
                            }
                            typeBindingFindSuperTypeOriginatingFrom = typeBindingErasure.findSuperTypeOriginatingFrom(typeBindingErasure2);
                            if (typeBindingFindSuperTypeOriginatingFrom != null) {
                                continue;
                            }
                        }
                    } else if (((TypeVariableBinding) typeBindingLeafComponentType).hasOnlyRawBounds()) {
                        continue;
                    } else {
                        TypeBinding typeBindingLeafComponentType4 = methodBinding.original().parameters[i2].leafComponentType();
                        iKind = typeBindingLeafComponentType4.kind();
                        if (iKind != 4) {
                            if (iKind != 1028) {
                                if (iKind == 2052) {
                                    typeBindingFindSuperTypeOriginatingFrom = typeBindingErasure.findSuperTypeOriginatingFrom(typeBindingErasure2);
                                    if (typeBindingFindSuperTypeOriginatingFrom != null) {
                                        continue;
                                    }
                                } else if (iKind != 4100) {
                                    continue;
                                }
                            }
                            return false;
                        }
                        typeBindingFindSuperTypeOriginatingFrom = typeBindingErasure.findSuperTypeOriginatingFrom(typeBindingErasure2);
                        if (typeBindingFindSuperTypeOriginatingFrom != null) {
                            continue;
                        }
                    }
                }
                i2++;
                j = ClassFileConstants.JDK1_7;
                r10 = 1;
            } else {
                if (i2 == length - r10 && methodBinding.isVarargs() && methodBinding2.isVarargs()) {
                    TypeBinding typeBindingElementsType = ((ArrayBinding) typeBindingErasure).elementsType();
                    TypeBinding typeBindingElementsType2 = ((ArrayBinding) typeBindingErasure2).elementsType();
                    if (CompilerOptions.tolerateIllegalAmbiguousVarargsInvocation && compilerOptions().complianceLevel < j) {
                        if (TypeBinding.equalsEquals(typeBindingErasure, typeBindingElementsType2) || typeBindingErasure.isCompatibleWith(typeBindingElementsType2)) {
                            return r10;
                        }
                    } else if (TypeBinding.equalsEquals(typeBindingElementsType, typeBindingElementsType2) || typeBindingElementsType.isCompatibleWith(typeBindingElementsType2)) {
                        return r10;
                    }
                }
                return false;
            }
        }
        return r10;
    }

    public boolean isBoxingCompatibleWith(TypeBinding typeBinding, TypeBinding typeBinding2) {
        LookupEnvironment lookupEnvironmentEnvironment = environment();
        if (lookupEnvironmentEnvironment.globalOptions.sourceLevel < ClassFileConstants.JDK1_5 || typeBinding.isBaseType() == typeBinding2.isBaseType()) {
            return false;
        }
        TypeBinding typeBindingComputeBoxingType = lookupEnvironmentEnvironment.computeBoxingType(typeBinding);
        return TypeBinding.equalsEquals(typeBindingComputeBoxingType, typeBinding2) || typeBindingComputeBoxingType.isCompatibleWith(typeBinding2, this);
    }

    public final boolean isDefinedInField(FieldBinding fieldBinding) {
        Scope scope = this;
        do {
            if ((scope instanceof MethodScope) && ((MethodScope) scope).initializedField == fieldBinding) {
                return true;
            }
            scope = scope.parent;
        } while (scope != null);
        return false;
    }

    public final boolean isDefinedInMethod(MethodBinding methodBinding) {
        MethodBinding methodBindingOriginal = methodBinding.original();
        Scope scope = this;
        do {
            if (scope instanceof MethodScope) {
                ReferenceContext referenceContext = ((MethodScope) scope).referenceContext;
                if ((referenceContext instanceof AbstractMethodDeclaration) && ((AbstractMethodDeclaration) referenceContext).binding == methodBindingOriginal) {
                    return true;
                }
            }
            scope = scope.parent;
        } while (scope != null);
        return false;
    }

    public final boolean isDefinedInSameUnit(ReferenceBinding referenceBinding) {
        while (true) {
            ReferenceBinding referenceBindingEnclosingType = referenceBinding.enclosingType();
            if (referenceBindingEnclosingType == null) {
                break;
            }
            referenceBinding = referenceBindingEnclosingType;
        }
        Scope scope = this;
        while (true) {
            Scope scope2 = scope.parent;
            if (scope2 == null) {
                break;
            }
            scope = scope2;
        }
        SourceTypeBinding[] sourceTypeBindingArr = ((CompilationUnitScope) scope).topLevelTypes;
        int length = sourceTypeBindingArr.length;
        do {
            length--;
            if (length < 0) {
                return false;
            }
        } while (!TypeBinding.equalsEquals(sourceTypeBindingArr[length], referenceBinding.original()));
        return true;
    }

    public final boolean isDefinedInType(ReferenceBinding referenceBinding) {
        Scope scope = this;
        do {
            if ((scope instanceof ClassScope) && TypeBinding.equalsEquals(((ClassScope) scope).referenceContext.binding, referenceBinding)) {
                return true;
            }
            scope = scope.parent;
        } while (scope != null);
        return false;
    }

    public boolean isInsideCase(CaseStatement caseStatement) {
        Scope scope = this;
        do {
            if (scope.kind == 1 && ((BlockScope) scope).enclosingCase == caseStatement) {
                return true;
            }
            scope = scope.parent;
        } while (scope != null);
        return false;
    }

    public boolean isInsideDeprecatedCode() {
        SourceTypeBinding sourceTypeBinding;
        int i = this.kind;
        if (i == 1 || i == 2) {
            MethodScope methodScope = methodScope();
            if (!methodScope.isInsideInitializer()) {
                ReferenceContext referenceContext = methodScope.referenceContext;
                MethodBinding methodBinding = referenceContext instanceof AbstractMethodDeclaration ? ((AbstractMethodDeclaration) referenceContext).binding : ((LambdaExpression) referenceContext).binding;
                if (methodBinding != null && methodBinding.isViewedAsDeprecated()) {
                    return true;
                }
            } else if (methodScope.initializedField != null && methodScope.initializedField.isViewedAsDeprecated()) {
                return true;
            }
            SourceTypeBinding sourceTypeBinding2 = ((BlockScope) this).referenceType().binding;
            if (sourceTypeBinding2 != null) {
                sourceTypeBinding2.initializeDeprecatedAnnotationTagBits();
                if (sourceTypeBinding2.isViewedAsDeprecated()) {
                    return true;
                }
            }
        } else if (i == 3) {
            SourceTypeBinding sourceTypeBinding3 = ((ClassScope) this).referenceType().binding;
            if (sourceTypeBinding3 != null) {
                sourceTypeBinding3.initializeDeprecatedAnnotationTagBits();
                if (sourceTypeBinding3.isViewedAsDeprecated()) {
                    return true;
                }
            }
        } else if (i == 4) {
            CompilationUnitDeclaration compilationUnitDeclarationReferenceCompilationUnit = referenceCompilationUnit();
            if (compilationUnitDeclarationReferenceCompilationUnit.types != null && compilationUnitDeclarationReferenceCompilationUnit.types.length > 0 && (sourceTypeBinding = compilationUnitDeclarationReferenceCompilationUnit.types[0].binding) != null) {
                sourceTypeBinding.initializeDeprecatedAnnotationTagBits();
                if (sourceTypeBinding.isViewedAsDeprecated()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isOverriddenMethodGeneric(MethodBinding methodBinding) {
        MethodVerifier methodVerifier = environment().methodVerifier();
        ReferenceBinding referenceBindingSuperclass = methodBinding.declaringClass.superclass();
        while (true) {
            if (referenceBindingSuperclass == null) {
                return false;
            }
            for (MethodBinding methodBinding2 : referenceBindingSuperclass.getMethods(methodBinding.selector)) {
                if (methodBinding2 != null && methodBinding2.original().typeVariables != Binding.NO_TYPE_VARIABLES && methodVerifier.doesMethodOverride(methodBinding, methodBinding2)) {
                    return true;
                }
            }
            referenceBindingSuperclass = referenceBindingSuperclass.superclass();
        }
    }

    public boolean isSubtypeOfRawType(TypeBinding typeBinding) {
        ReferenceBinding[] referenceBindingArr;
        ReferenceBinding[] referenceBindingArr2;
        TypeBinding typeBindingLeafComponentType = typeBinding.leafComponentType();
        if (typeBindingLeafComponentType.isBaseType()) {
            return false;
        }
        ReferenceBinding referenceBindingSuperclass = (ReferenceBinding) typeBindingLeafComponentType;
        ReferenceBinding[] referenceBindingArr3 = null;
        int length = 0;
        while (!referenceBindingSuperclass.isRawType()) {
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
                    for (ReferenceBinding referenceBinding : referenceBindingArrSuperInterfaces) {
                        int i2 = 0;
                        while (true) {
                            if (i2 < i) {
                                if (TypeBinding.equalsEquals(referenceBinding, referenceBindingArr2[i2])) {
                                    break;
                                }
                                i2++;
                            } else {
                                referenceBindingArr2[i] = referenceBinding;
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
            if (referenceBindingSuperclass == null) {
                for (int i3 = 0; i3 < length; i3++) {
                    ReferenceBinding referenceBinding2 = referenceBindingArr3[i3];
                    if (referenceBinding2.isRawType()) {
                        return true;
                    }
                    ReferenceBinding[] referenceBindingArrSuperInterfaces2 = referenceBinding2.superInterfaces();
                    if (referenceBindingArrSuperInterfaces2 != null && referenceBindingArrSuperInterfaces2 != Binding.NO_SUPERINTERFACES) {
                        int length3 = length + referenceBindingArrSuperInterfaces2.length;
                        if (length3 >= referenceBindingArr3.length) {
                            referenceBindingArr = new ReferenceBinding[length3 + 5];
                            System.arraycopy(referenceBindingArr3, 0, referenceBindingArr, 0, length);
                        } else {
                            referenceBindingArr = referenceBindingArr3;
                        }
                        int i4 = length;
                        for (ReferenceBinding referenceBinding3 : referenceBindingArrSuperInterfaces2) {
                            int i5 = 0;
                            while (true) {
                                if (i5 < i4) {
                                    if (TypeBinding.equalsEquals(referenceBinding3, referenceBindingArr[i5])) {
                                        break;
                                    }
                                    i5++;
                                } else {
                                    referenceBindingArr[i4] = referenceBinding3;
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
        }
        return true;
    }

    private TypeBinding leastContainingInvocation(TypeBinding typeBinding, Object obj, ArrayList arrayList) {
        if (obj == null) {
            return typeBinding;
        }
        if (obj instanceof TypeBinding) {
            return (TypeBinding) obj;
        }
        TypeBinding[] typeBindingArr = (TypeBinding[]) obj;
        int iDimensions = typeBinding.dimensions();
        TypeBinding typeBindingLeafComponentType = typeBinding.leafComponentType();
        int length = typeBindingLeafComponentType.typeVariables().length;
        if (length == 0) {
            return typeBindingLeafComponentType;
        }
        TypeBinding[] typeBindingArr2 = new TypeBinding[length];
        for (TypeBinding typeBinding2 : typeBindingArr) {
            TypeBinding typeBindingLeafComponentType2 = typeBinding2.leafComponentType();
            int iKind = typeBindingLeafComponentType2.kind();
            if (iKind == 260) {
                ParameterizedTypeBinding parameterizedTypeBinding = (ParameterizedTypeBinding) typeBindingLeafComponentType2;
                for (int i = 0; i < length; i++) {
                    TypeBinding typeBindingLeastContainingTypeArgument = leastContainingTypeArgument(typeBindingArr2[i], parameterizedTypeBinding.arguments[i], (ReferenceBinding) typeBindingLeafComponentType, i, (ArrayList) arrayList.clone());
                    if (typeBindingLeastContainingTypeArgument == null) {
                        return null;
                    }
                    typeBindingArr2[i] = typeBindingLeastContainingTypeArgument;
                }
            } else {
                if (iKind == 1028) {
                    return iDimensions == 0 ? typeBindingLeafComponentType2 : environment().createArrayType(typeBindingLeafComponentType2, iDimensions);
                }
                if (iKind != 2052) {
                    continue;
                } else {
                    TypeVariableBinding[] typeVariableBindingArrTypeVariables = typeBindingLeafComponentType2.typeVariables();
                    for (int i2 = 0; i2 < length; i2++) {
                        TypeBinding typeBindingLeastContainingTypeArgument2 = leastContainingTypeArgument(typeBindingArr2[i2], typeVariableBindingArrTypeVariables[i2], (ReferenceBinding) typeBindingLeafComponentType, i2, (ArrayList) arrayList.clone());
                        if (typeBindingLeastContainingTypeArgument2 == null) {
                            return null;
                        }
                        typeBindingArr2[i2] = typeBindingLeastContainingTypeArgument2;
                    }
                }
            }
        }
        ParameterizedTypeBinding parameterizedTypeBindingCreateParameterizedType = environment().createParameterizedType((ReferenceBinding) typeBindingLeafComponentType.erasure(), typeBindingArr2, typeBindingLeafComponentType.enclosingType());
        return iDimensions == 0 ? parameterizedTypeBindingCreateParameterizedType : environment().createArrayType(parameterizedTypeBindingCreateParameterizedType, iDimensions);
    }

    private TypeBinding leastContainingTypeArgument(TypeBinding typeBinding, TypeBinding typeBinding2, ReferenceBinding referenceBinding, int i, ArrayList arrayList) {
        if (typeBinding == null) {
            return typeBinding2;
        }
        if (TypeBinding.equalsEquals(typeBinding, typeBinding2)) {
            return typeBinding;
        }
        if (typeBinding2.isWildcard()) {
            WildcardBinding wildcardBinding = (WildcardBinding) typeBinding2;
            if (typeBinding.isWildcard()) {
                WildcardBinding wildcardBinding2 = (WildcardBinding) typeBinding;
                int i2 = wildcardBinding2.boundKind;
                if (i2 == 1) {
                    int i3 = wildcardBinding.boundKind;
                    if (i3 == 1) {
                        TypeBinding typeBindingLowerUpperBound = lowerUpperBound(new TypeBinding[]{wildcardBinding2.bound, wildcardBinding.bound}, arrayList);
                        if (typeBindingLowerUpperBound == null) {
                            return null;
                        }
                        return TypeBinding.equalsEquals(typeBindingLowerUpperBound, TypeBinding.INT) ? environment().createWildcard(referenceBinding, i, null, null, 0) : environment().createWildcard(referenceBinding, i, typeBindingLowerUpperBound, null, 1);
                    }
                    if (i3 == 2) {
                        return TypeBinding.equalsEquals(wildcardBinding2.bound, wildcardBinding.bound) ? wildcardBinding2.bound : environment().createWildcard(referenceBinding, i, null, null, 0);
                    }
                } else if (i2 == 2 && wildcardBinding2.boundKind == 2) {
                    TypeBinding[] typeBindingArrGreaterLowerBound = greaterLowerBound(new TypeBinding[]{wildcardBinding2.bound, wildcardBinding.bound}, this, environment());
                    if (typeBindingArrGreaterLowerBound == null) {
                        return null;
                    }
                    return environment().createWildcard(referenceBinding, i, typeBindingArrGreaterLowerBound[0], null, 2);
                }
            } else {
                int i4 = wildcardBinding.boundKind;
                if (i4 == 1) {
                    TypeBinding typeBindingLowerUpperBound2 = lowerUpperBound(new TypeBinding[]{typeBinding, wildcardBinding.bound}, arrayList);
                    if (typeBindingLowerUpperBound2 == null) {
                        return null;
                    }
                    return TypeBinding.equalsEquals(typeBindingLowerUpperBound2, TypeBinding.INT) ? environment().createWildcard(referenceBinding, i, null, null, 0) : environment().createWildcard(referenceBinding, i, typeBindingLowerUpperBound2, null, 1);
                }
                if (i4 == 2) {
                    TypeBinding[] typeBindingArrGreaterLowerBound2 = greaterLowerBound(new TypeBinding[]{typeBinding, wildcardBinding.bound}, this, environment());
                    if (typeBindingArrGreaterLowerBound2 == null) {
                        return null;
                    }
                    return environment().createWildcard(referenceBinding, i, typeBindingArrGreaterLowerBound2[0], null, 2);
                }
            }
        } else if (typeBinding.isWildcard()) {
            WildcardBinding wildcardBinding3 = (WildcardBinding) typeBinding;
            int i5 = wildcardBinding3.boundKind;
            if (i5 == 1) {
                TypeBinding typeBindingLowerUpperBound3 = lowerUpperBound(new TypeBinding[]{wildcardBinding3.bound, typeBinding2}, arrayList);
                if (typeBindingLowerUpperBound3 == null) {
                    return null;
                }
                return TypeBinding.equalsEquals(typeBindingLowerUpperBound3, TypeBinding.INT) ? environment().createWildcard(referenceBinding, i, null, null, 0) : environment().createWildcard(referenceBinding, i, typeBindingLowerUpperBound3, null, 1);
            }
            if (i5 == 2) {
                TypeBinding[] typeBindingArrGreaterLowerBound3 = greaterLowerBound(new TypeBinding[]{wildcardBinding3.bound, typeBinding2}, this, environment());
                if (typeBindingArrGreaterLowerBound3 == null) {
                    return null;
                }
                return environment().createWildcard(referenceBinding, i, typeBindingArrGreaterLowerBound3[0], null, 2);
            }
        }
        TypeBinding typeBindingLowerUpperBound4 = lowerUpperBound(new TypeBinding[]{typeBinding, typeBinding2}, arrayList);
        if (typeBindingLowerUpperBound4 == null) {
            return null;
        }
        return TypeBinding.equalsEquals(typeBindingLowerUpperBound4, TypeBinding.INT) ? environment().createWildcard(referenceBinding, i, null, null, 0) : environment().createWildcard(referenceBinding, i, typeBindingLowerUpperBound4, null, 1);
    }

    public TypeBinding lowerUpperBound(TypeBinding[] typeBindingArr) {
        if (typeBindingArr.length == 1) {
            TypeBinding typeBinding = typeBindingArr[0];
            return typeBinding == null ? TypeBinding.VOID : typeBinding;
        }
        return lowerUpperBound(typeBindingArr, new ArrayList(1));
    }

    private TypeBinding lowerUpperBound(TypeBinding[] typeBindingArr, ArrayList arrayList) {
        int length = typeBindingArr.length;
        if (length == 1) {
            TypeBinding typeBinding = typeBindingArr[0];
            return typeBinding == null ? TypeBinding.VOID : typeBinding;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            TypeBinding[] typeBindingArr2 = (TypeBinding[]) arrayList.get(i);
            int length2 = typeBindingArr2.length;
            if (length2 >= length) {
                for (TypeBinding typeBinding2 : typeBindingArr) {
                    if (typeBinding2 != null) {
                        int i2 = 0;
                        while (true) {
                            if (i2 < length2) {
                                TypeBinding typeBinding3 = typeBindingArr2[i2];
                                if (typeBinding3 == null || (!TypeBinding.equalsEquals(typeBinding3, typeBinding2) && !typeBinding3.isEquivalentTo(typeBinding2))) {
                                    i2++;
                                }
                            }
                        }
                    }
                }
                return TypeBinding.INT;
            }
        }
        arrayList.add(typeBindingArr);
        HashMap map = new HashMap(1);
        TypeBinding[] typeBindingArrMinimalErasedCandidates = minimalErasedCandidates(typeBindingArr, map);
        if (typeBindingArrMinimalErasedCandidates == null) {
            return null;
        }
        if (typeBindingArrMinimalErasedCandidates.length == 0) {
            return TypeBinding.VOID;
        }
        int i3 = 0;
        TypeBinding typeBindingLeafComponentType = null;
        int i4 = -1;
        for (TypeBinding typeBinding4 : typeBindingArrMinimalErasedCandidates) {
            if (typeBinding4 != null) {
                TypeBinding typeBindingLeastContainingInvocation = leastContainingInvocation(typeBinding4, map.get(typeBinding4), arrayList);
                if (typeBindingLeastContainingInvocation == null) {
                    return null;
                }
                int iDimensions = typeBindingLeastContainingInvocation.dimensions();
                if (i4 == -1) {
                    i4 = iDimensions;
                } else if (iDimensions != i4) {
                    return null;
                }
                if (typeBindingLeafComponentType == null && !typeBindingLeastContainingInvocation.leafComponentType().isInterface()) {
                    typeBindingLeafComponentType = typeBindingLeastContainingInvocation.leafComponentType();
                }
                typeBindingArrMinimalErasedCandidates[i3] = typeBindingLeastContainingInvocation;
                i3++;
            }
        }
        if (i3 == 0) {
            return TypeBinding.VOID;
        }
        if (i3 == 1) {
            return typeBindingArrMinimalErasedCandidates[0];
        }
        if (i3 == 2) {
            if ((i4 == 0 ? typeBindingArrMinimalErasedCandidates[1] : typeBindingArrMinimalErasedCandidates[1].leafComponentType()).f626id == 1) {
                return typeBindingArrMinimalErasedCandidates[0];
            }
            if ((i4 == 0 ? typeBindingArrMinimalErasedCandidates[0] : typeBindingArrMinimalErasedCandidates[0].leafComponentType()).f626id == 1) {
                return typeBindingArrMinimalErasedCandidates[1];
            }
        }
        TypeBinding[] typeBindingArr3 = new TypeBinding[i3 - 1];
        int i5 = 0;
        for (int i6 = 0; i6 < i3; i6++) {
            TypeBinding typeBindingLeafComponentType2 = i4 == 0 ? typeBindingArrMinimalErasedCandidates[i6] : typeBindingArrMinimalErasedCandidates[i6].leafComponentType();
            if (typeBindingLeafComponentType2.isInterface()) {
                typeBindingArr3[i5] = typeBindingLeafComponentType2;
                i5++;
            }
        }
        WildcardBinding wildcardBindingCreateWildcard = environment().createWildcard(null, 0, typeBindingLeafComponentType, typeBindingArr3, 1);
        return i4 == 0 ? wildcardBindingCreateWildcard : environment().createArrayType(wildcardBindingCreateWildcard, i4);
    }

    public final MethodScope methodScope() {
        Scope scope = this;
        while (!(scope instanceof MethodScope)) {
            scope = scope.parent;
            if (scope == null) {
                return null;
            }
        }
        return (MethodScope) scope;
    }

    public final MethodScope namedMethodScope() {
        Scope scope = this;
        do {
            if ((scope instanceof MethodScope) && !scope.isLambdaScope()) {
                return (MethodScope) scope;
            }
            scope = scope.parent;
        } while (scope != null);
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:190:0x0281  */
    /* JADX WARN: Code duplicated, block: B:192:0x0287  */
    /* JADX WARN: Code duplicated, block: B:195:0x028c  */
    /* JADX WARN: Code duplicated, block: B:197:0x0290  */
    /* JADX WARN: Code duplicated, block: B:198:0x0292  */
    /* JADX WARN: Code duplicated, block: B:201:0x02a0  */
    /* JADX WARN: Code duplicated, block: B:203:0x02ab  */
    /* JADX WARN: Code duplicated, block: B:207:0x02b7  */
    /* JADX WARN: Code duplicated, block: B:210:0x02be  */
    /* JADX WARN: Code duplicated, block: B:214:0x02ca A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:215:0x02cc  */
    /* JADX WARN: Code duplicated, block: B:216:0x02ce  */
    /* JADX WARN: Code duplicated, block: B:219:0x02dc  */
    /* JADX WARN: Code duplicated, block: B:221:0x02e7  */
    /* JADX WARN: Code duplicated, block: B:225:0x02f3  */
    /* JADX WARN: Code duplicated, block: B:228:0x02fa  */
    /* JADX WARN: Code duplicated, block: B:258:0x02fd A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:259:0x02fd A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:260:0x02fd A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:263:0x02c1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:264:0x02c1 A[SYNTHETIC] */
    /* JADX WARN: Instruction removed from duplicated block: B:192:0x0287, please report this as an issue */
    /* JADX WARN: Multi-variable type inference failed */
    protected TypeBinding[] minimalErasedCandidates(TypeBinding[] typeBindingArr, Map map) {
        LocalTypeBinding localTypeBinding;
        TypeBinding typeBinding;
        ReferenceBinding referenceBinding;
        ReferenceBinding[] referenceBindingArrSuperInterfaces;
        ReferenceBinding referenceBindingSuperclass;
        TypeBinding typeBindingCreateArrayType;
        TypeBinding typeBindingErasure;
        int i;
        TypeBinding typeBindingCreateArrayType2;
        TypeBinding typeBindingErasure2;
        TypeBinding typeBinding2;
        TypeBinding[] typeBindingArr2;
        int length = typeBindingArr.length;
        int i2 = -1;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            TypeBinding[] typeBindingArr3 = null;
            if (i4 >= length) {
                if (i5 == 0) {
                    return Binding.NO_TYPES;
                }
                if (i5 == 1) {
                    return typeBindingArr;
                }
                TypeBinding typeBinding3 = typeBindingArr[i2];
                if (typeBinding3.isBaseType()) {
                    return null;
                }
                ArrayList<TypeBinding> arrayList = new ArrayList(5);
                typeBinding3.dimensions();
                int iKind = typeBinding3.leafComponentType().kind();
                TypeBinding typeBindingErasure3 = (iKind == 68 || iKind == 260 || iKind == 1028) ? typeBinding3.erasure() : typeBinding3;
                if (TypeBinding.notEquals(typeBindingErasure3, typeBinding3)) {
                    map.put(typeBindingErasure3, typeBinding3);
                }
                arrayList.add(typeBinding3);
                int i6 = i3;
                int i7 = 1;
                while (i6 < i7) {
                    TypeBinding typeBinding4 = (TypeBinding) arrayList.get(i6);
                    int iDimensions = typeBinding4.dimensions();
                    if (iDimensions > 0) {
                        TypeBinding typeBindingLeafComponentType = typeBinding4.leafComponentType();
                        switch (typeBindingLeafComponentType.f626id) {
                            case 1:
                                if (iDimensions > 1) {
                                    TypeBinding typeBindingElementsType = ((ArrayBinding) typeBinding4).elementsType();
                                    if (!arrayList.contains(typeBindingElementsType)) {
                                        arrayList.add(typeBindingElementsType);
                                        i7++;
                                    }
                                    break;
                                }
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                                ReferenceBinding javaIoSerializable = getJavaIoSerializable();
                                if (!arrayList.contains(javaIoSerializable)) {
                                    arrayList.add(javaIoSerializable);
                                    i7++;
                                }
                                ReferenceBinding javaLangCloneable = getJavaLangCloneable();
                                if (!arrayList.contains(javaLangCloneable)) {
                                    arrayList.add(javaLangCloneable);
                                    i7++;
                                }
                                ReferenceBinding javaLangObject = getJavaLangObject();
                                if (!arrayList.contains(javaLangObject)) {
                                    arrayList.add(javaLangObject);
                                    i7++;
                                }
                                break;
                            case 6:
                            default:
                                typeBinding4 = typeBindingLeafComponentType;
                                referenceBinding = (ReferenceBinding) typeBinding4;
                                if (!referenceBinding.isCapture() && (typeBinding2 = ((CaptureBinding) referenceBinding).firstBound) != null && typeBinding2.isArrayType()) {
                                    TypeBinding typeBindingCreateArrayType3 = iDimensions == 0 ? typeBinding2 : environment().createArrayType(typeBinding2, iDimensions);
                                    if (!arrayList.contains(typeBindingCreateArrayType3)) {
                                        arrayList.add(typeBindingCreateArrayType3);
                                        i7++;
                                        TypeBinding typeBindingErasure4 = (typeBinding2.isTypeVariable() || typeBinding2.isWildcard()) ? typeBindingCreateArrayType3 : typeBindingCreateArrayType3.erasure();
                                        if (TypeBinding.notEquals(typeBindingErasure4, typeBindingCreateArrayType3)) {
                                            map.put(typeBindingErasure4, typeBindingCreateArrayType3);
                                        }
                                    }
                                } else {
                                    referenceBindingArrSuperInterfaces = referenceBinding.superInterfaces();
                                    if (referenceBindingArrSuperInterfaces != null) {
                                        for (ReferenceBinding referenceBinding2 : referenceBindingArrSuperInterfaces) {
                                            if (iDimensions == 0) {
                                                typeBindingCreateArrayType2 = referenceBinding2;
                                            } else {
                                                typeBindingCreateArrayType2 = environment().createArrayType(referenceBinding2, iDimensions);
                                            }
                                            if (!arrayList.contains(typeBindingCreateArrayType2)) {
                                                arrayList.add(typeBindingCreateArrayType2);
                                                i7++;
                                                if (!referenceBinding2.isTypeVariable() || referenceBinding2.isWildcard()) {
                                                    typeBindingErasure2 = typeBindingCreateArrayType2;
                                                } else {
                                                    typeBindingErasure2 = typeBindingCreateArrayType2.erasure();
                                                }
                                                if (TypeBinding.notEquals(typeBindingErasure2, typeBindingCreateArrayType2)) {
                                                    map.put(typeBindingErasure2, typeBindingCreateArrayType2);
                                                }
                                            }
                                        }
                                    }
                                    referenceBindingSuperclass = referenceBinding.superclass();
                                    if (referenceBindingSuperclass != null) {
                                        if (iDimensions == 0) {
                                            typeBindingCreateArrayType = referenceBindingSuperclass;
                                        } else {
                                            typeBindingCreateArrayType = environment().createArrayType(referenceBindingSuperclass, iDimensions);
                                        }
                                        if (!arrayList.contains(typeBindingCreateArrayType)) {
                                            arrayList.add(typeBindingCreateArrayType);
                                            i7++;
                                            if (!referenceBindingSuperclass.isTypeVariable() || referenceBindingSuperclass.isWildcard()) {
                                                typeBindingErasure = typeBindingCreateArrayType;
                                            } else {
                                                typeBindingErasure = typeBindingCreateArrayType.erasure();
                                            }
                                            if (TypeBinding.notEquals(typeBindingErasure, typeBindingCreateArrayType)) {
                                                map.put(typeBindingErasure, typeBindingCreateArrayType);
                                            }
                                        }
                                    }
                                }
                                break;
                        }
                    } else {
                        referenceBinding = (ReferenceBinding) typeBinding4;
                        if (!referenceBinding.isCapture()) {
                            referenceBindingArrSuperInterfaces = referenceBinding.superInterfaces();
                            if (referenceBindingArrSuperInterfaces != null) {
                                while (i < r11) {
                                    if (iDimensions == 0) {
                                        typeBindingCreateArrayType2 = referenceBinding2;
                                    } else {
                                        typeBindingCreateArrayType2 = environment().createArrayType(referenceBinding2, iDimensions);
                                    }
                                    if (!arrayList.contains(typeBindingCreateArrayType2)) {
                                        arrayList.add(typeBindingCreateArrayType2);
                                        i7++;
                                        if (referenceBinding2.isTypeVariable()) {
                                            typeBindingErasure2 = typeBindingCreateArrayType2;
                                        } else {
                                            typeBindingErasure2 = typeBindingCreateArrayType2;
                                        }
                                        if (TypeBinding.notEquals(typeBindingErasure2, typeBindingCreateArrayType2)) {
                                            map.put(typeBindingErasure2, typeBindingCreateArrayType2);
                                        }
                                    }
                                }
                            }
                            referenceBindingSuperclass = referenceBinding.superclass();
                            if (referenceBindingSuperclass != null) {
                                if (iDimensions == 0) {
                                    typeBindingCreateArrayType = referenceBindingSuperclass;
                                } else {
                                    typeBindingCreateArrayType = environment().createArrayType(referenceBindingSuperclass, iDimensions);
                                }
                                if (!arrayList.contains(typeBindingCreateArrayType)) {
                                    arrayList.add(typeBindingCreateArrayType);
                                    i7++;
                                    if (referenceBindingSuperclass.isTypeVariable()) {
                                        typeBindingErasure = typeBindingCreateArrayType;
                                    } else {
                                        typeBindingErasure = typeBindingCreateArrayType;
                                    }
                                    if (TypeBinding.notEquals(typeBindingErasure, typeBindingCreateArrayType)) {
                                        map.put(typeBindingErasure, typeBindingCreateArrayType);
                                    }
                                }
                            }
                        } else {
                            referenceBindingArrSuperInterfaces = referenceBinding.superInterfaces();
                            if (referenceBindingArrSuperInterfaces != null) {
                                while (i < r11) {
                                    if (iDimensions == 0) {
                                        typeBindingCreateArrayType2 = referenceBinding2;
                                    } else {
                                        typeBindingCreateArrayType2 = environment().createArrayType(referenceBinding2, iDimensions);
                                    }
                                    if (!arrayList.contains(typeBindingCreateArrayType2)) {
                                        arrayList.add(typeBindingCreateArrayType2);
                                        i7++;
                                        if (referenceBinding2.isTypeVariable()) {
                                            typeBindingErasure2 = typeBindingCreateArrayType2;
                                        } else {
                                            typeBindingErasure2 = typeBindingCreateArrayType2;
                                        }
                                        if (TypeBinding.notEquals(typeBindingErasure2, typeBindingCreateArrayType2)) {
                                            map.put(typeBindingErasure2, typeBindingCreateArrayType2);
                                        }
                                    }
                                }
                            }
                            referenceBindingSuperclass = referenceBinding.superclass();
                            if (referenceBindingSuperclass != null) {
                                if (iDimensions == 0) {
                                    typeBindingCreateArrayType = referenceBindingSuperclass;
                                } else {
                                    typeBindingCreateArrayType = environment().createArrayType(referenceBindingSuperclass, iDimensions);
                                }
                                if (!arrayList.contains(typeBindingCreateArrayType)) {
                                    arrayList.add(typeBindingCreateArrayType);
                                    i7++;
                                    if (referenceBindingSuperclass.isTypeVariable()) {
                                        typeBindingErasure = typeBindingCreateArrayType;
                                    } else {
                                        typeBindingErasure = typeBindingCreateArrayType;
                                    }
                                    if (TypeBinding.notEquals(typeBindingErasure, typeBindingCreateArrayType)) {
                                        map.put(typeBindingErasure, typeBindingCreateArrayType);
                                    }
                                }
                            }
                        }
                    }
                    i6++;
                    i3 = 0;
                    typeBindingArr3 = null;
                }
                int size = arrayList.size();
                TypeBinding[] typeBindingArr4 = new TypeBinding[size];
                int i8 = i3;
                for (TypeBinding typeBindingErasure5 : arrayList) {
                    TypeBinding typeBindingLeafComponentType2 = typeBindingErasure5.leafComponentType();
                    int i9 = i8 + 1;
                    if (!typeBindingLeafComponentType2.isTypeVariable() && !typeBindingLeafComponentType2.isWildcard()) {
                        typeBindingErasure5 = typeBindingErasure5.erasure();
                    }
                    typeBindingArr4[i8] = typeBindingErasure5;
                    i8 = i9;
                    i3 = 0;
                    typeBindingArr3 = null;
                }
                int i10 = i2 + 1;
                int i11 = size;
                while (i10 < length) {
                    LocalTypeBinding localTypeBinding2 = typeBindingArr[i10];
                    if (localTypeBinding2 != 0) {
                        int i12 = 2;
                        if (localTypeBinding2.isArrayType()) {
                            int i13 = i3;
                            while (i13 < size) {
                                LocalTypeBinding localTypeBinding3 = typeBindingArr4[i13];
                                if (localTypeBinding3 != 0 && !TypeBinding.equalsEquals(localTypeBinding3, localTypeBinding2)) {
                                    TypeBinding typeBindingFindSuperTypeOriginatingFrom = localTypeBinding2.findSuperTypeOriginatingFrom(localTypeBinding3);
                                    if (typeBindingFindSuperTypeOriginatingFrom == null) {
                                        typeBindingArr4[i13] = typeBindingArr3;
                                        i11--;
                                        if (i11 == 0) {
                                            return typeBindingArr3;
                                        }
                                    } else {
                                        Object obj = map.get(localTypeBinding3);
                                        if (obj == null) {
                                            map.put(localTypeBinding3, typeBindingFindSuperTypeOriginatingFrom);
                                        } else if (obj instanceof TypeBinding) {
                                            TypeBinding typeBinding5 = (TypeBinding) obj;
                                            if (TypeBinding.notEquals(typeBindingFindSuperTypeOriginatingFrom, typeBinding5)) {
                                                TypeBinding[] typeBindingArr5 = new TypeBinding[i12];
                                                typeBindingArr5[i3] = typeBinding5;
                                                typeBindingArr5[1] = typeBindingFindSuperTypeOriginatingFrom;
                                                map.put(localTypeBinding3, typeBindingArr5);
                                            }
                                        } else {
                                            TypeBinding[] typeBindingArr6 = (TypeBinding[]) obj;
                                            int length2 = typeBindingArr6.length;
                                            int i14 = i3;
                                            while (true) {
                                                if (i14 < length2) {
                                                    if (TypeBinding.equalsEquals(typeBindingArr6[i14], typeBindingFindSuperTypeOriginatingFrom)) {
                                                        break;
                                                    }
                                                    i14++;
                                                    i3 = 0;
                                                } else {
                                                    TypeBinding[] typeBindingArr7 = new TypeBinding[length2 + 1];
                                                    System.arraycopy(typeBindingArr6, i3, typeBindingArr7, i3, length2);
                                                    map.put(localTypeBinding3, typeBindingArr7);
                                                    typeBindingArr7[length2] = typeBindingFindSuperTypeOriginatingFrom;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                i13++;
                                i3 = 0;
                                typeBindingArr3 = null;
                                i12 = 2;
                            }
                        } else {
                            int i15 = i11;
                            for (int i16 = 0; i16 < size; i16++) {
                                LocalTypeBinding localTypeBinding4 = typeBindingArr4[i16];
                                if (localTypeBinding4 != 0) {
                                    if (TypeBinding.equalsEquals(localTypeBinding4, localTypeBinding2) || (localTypeBinding4.f626id == 1 && localTypeBinding2.isInterface())) {
                                        typeBinding = localTypeBinding4;
                                    } else {
                                        TypeBinding typeBindingFindSuperTypeOriginatingFrom2 = localTypeBinding4.isArrayType() ? null : localTypeBinding2.findSuperTypeOriginatingFrom(localTypeBinding4);
                                        if (typeBindingFindSuperTypeOriginatingFrom2 == null) {
                                            typeBindingArr4[i16] = 0;
                                            i15--;
                                            if (i15 == 0) {
                                                return null;
                                            }
                                        } else {
                                            typeBinding = typeBindingFindSuperTypeOriginatingFrom2;
                                        }
                                    }
                                    Object obj2 = map.get(localTypeBinding4);
                                    if (obj2 == null) {
                                        map.put(localTypeBinding4, typeBinding);
                                    } else if (obj2 instanceof TypeBinding) {
                                        TypeBinding typeBinding6 = (TypeBinding) obj2;
                                        if (TypeBinding.notEquals(typeBinding, typeBinding6)) {
                                            map.put(localTypeBinding4, new TypeBinding[]{typeBinding6, typeBinding});
                                        }
                                    } else {
                                        TypeBinding[] typeBindingArr8 = (TypeBinding[]) obj2;
                                        int length3 = typeBindingArr8.length;
                                        int i17 = 0;
                                        while (true) {
                                            if (i17 < length3) {
                                                if (TypeBinding.equalsEquals(typeBindingArr8[i17], typeBinding)) {
                                                    break;
                                                }
                                                i17++;
                                            } else {
                                                TypeBinding[] typeBindingArr9 = new TypeBinding[length3 + 1];
                                                System.arraycopy(typeBindingArr8, 0, typeBindingArr9, 0, length3);
                                                map.put(localTypeBinding4, typeBindingArr9);
                                                typeBindingArr9[length3] = typeBinding;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            i11 = i15;
                        }
                    }
                    i10++;
                    i3 = 0;
                    typeBindingArr3 = null;
                }
                if (i11 > 1) {
                    for (int i18 = i3; i18 < size; i18++) {
                        LocalTypeBinding localTypeBinding5 = typeBindingArr4[i18];
                        if (localTypeBinding5 != 0) {
                            for (int i19 = i3; i19 < size; i19++) {
                                if (i18 != i19 && (localTypeBinding = typeBindingArr4[i19]) != 0) {
                                    if (localTypeBinding5 instanceof ReferenceBinding) {
                                        if ((localTypeBinding.f626id != 1 || !localTypeBinding5.isInterface()) && localTypeBinding5.findSuperTypeOriginatingFrom(localTypeBinding) != null) {
                                            typeBindingArr4[i19] = typeBindingArr3;
                                        }
                                    } else if (localTypeBinding5.isArrayType() && ((!localTypeBinding.isArrayType() || localTypeBinding.leafComponentType().f626id != 1 || localTypeBinding.dimensions() != localTypeBinding5.dimensions() || !localTypeBinding5.leafComponentType().isInterface()) && localTypeBinding5.findSuperTypeOriginatingFrom(localTypeBinding) != null)) {
                                        typeBindingArr4[i19] = typeBindingArr3;
                                    }
                                }
                            }
                        }
                    }
                }
                return typeBindingArr4;
            }
            TypeBinding typeBinding7 = typeBindingArr[i4];
            if (typeBinding7 == TypeBinding.NULL) {
                typeBindingArr2 = null;
                typeBindingArr[i4] = 0;
                typeBinding7 = null;
            } else {
                typeBindingArr2 = null;
            }
            if (typeBinding7 != null) {
                if (typeBinding7.isBaseType()) {
                    return typeBindingArr2;
                }
                if (i2 < 0) {
                    i2 = i4;
                }
                i5++;
            }
            i4++;
            i3 = 0;
        }
    }

    protected final MethodBinding mostSpecificClassMethodBinding(MethodBinding[] methodBindingArr, int i, InvocationSite invocationSite) {
        MethodBinding methodBinding = null;
        for (int i2 = 0; i2 < i; i2++) {
            MethodBinding methodBinding2 = methodBindingArr[i2];
            if (methodBinding != null && TypeBinding.notEquals(methodBinding2.declaringClass, methodBinding.declaringClass)) {
                break;
            }
            if (!methodBinding2.isStatic()) {
                methodBinding = methodBinding2;
            }
            int i3 = 0;
            while (true) {
                if (i3 < i) {
                    if (i2 != i3 && !methodBindingArr[i3].areParametersCompatibleWith(methodBinding2.parameters)) {
                        break;
                    }
                    i3++;
                } else {
                    compilationUnitScope().recordTypeReferences(methodBinding2.thrownExceptions);
                    return methodBinding2;
                }
            }
        }
        MethodBinding methodBinding3 = methodBindingArr[0];
        return new ProblemMethodBinding(methodBinding3, methodBinding3.selector, methodBindingArr[0].parameters, 3);
    }

    protected final MethodBinding mostSpecificInterfaceMethodBinding(MethodBinding[] methodBindingArr, int i, InvocationSite invocationSite) {
        for (int i2 = 0; i2 < i; i2++) {
            MethodBinding methodBinding = methodBindingArr[i2];
            int i3 = 0;
            while (true) {
                if (i3 < i) {
                    if (i2 != i3 && !methodBindingArr[i3].areParametersCompatibleWith(methodBinding.parameters)) {
                        break;
                    }
                    i3++;
                } else {
                    compilationUnitScope().recordTypeReferences(methodBinding.thrownExceptions);
                    return methodBinding;
                }
            }
        }
        MethodBinding methodBinding2 = methodBindingArr[0];
        return new ProblemMethodBinding(methodBinding2, methodBinding2.selector, methodBindingArr[0].parameters, 3);
    }

    /* JADX WARN: Code duplicated, block: B:268:0x0407  */
    protected final MethodBinding mostSpecificMethodBinding(MethodBinding[] methodBindingArr, int i, TypeBinding[] typeBindingArr, final InvocationSite invocationSite, ReferenceBinding referenceBinding) {
        int i2;
        MethodBinding[] methodBindingArr2;
        int i3;
        int[] iArr;
        MethodBinding[] methodBindingArr3;
        int i4;
        ReferenceBinding referenceBinding2;
        int i5;
        MethodBinding[] methodBindingArr4;
        MethodBinding methodBinding;
        ReferenceBinding referenceBinding3;
        int i6;
        MethodBinding methodBinding2;
        ReferenceBinding referenceBinding4;
        ReferenceBinding referenceBinding5;
        MethodBinding methodBindingFindOriginalInheritedMethod;
        int[] iArr2;
        MethodBinding[] methodBindingArr5;
        int i7;
        Expression[] expressionArrCreatePseudoExpressions;
        MethodBinding[] methodBindingArr6 = methodBindingArr;
        ReferenceBinding referenceBinding6 = referenceBinding;
        int i8 = 1;
        int i9 = 0;
        boolean z = compilerOptions().sourceLevel >= ClassFileConstants.JDK1_8;
        if (z && invocationSite.checkingPotentialCompatibility()) {
            if (i != methodBindingArr6.length) {
                MethodBinding[] methodBindingArr7 = new MethodBinding[i];
                System.arraycopy(methodBindingArr6, 0, methodBindingArr7, 0, i);
                methodBindingArr6 = methodBindingArr7;
            }
            invocationSite.acceptPotentiallyCompatibleMethods(methodBindingArr6);
        }
        int[] iArr3 = new int[i];
        int i10 = 0;
        int i11 = 0;
        while (true) {
            i2 = -1;
            if (i10 >= i) {
                break;
            }
            ReferenceBinding referenceBinding7 = referenceBinding6;
            int iParameterCompatibilityLevel = parameterCompatibilityLevel(methodBindingArr6[i10], typeBindingArr, invocationSite);
            iArr3[i10] = iParameterCompatibilityLevel;
            if (iParameterCompatibilityLevel != -1) {
                if (i10 != i11) {
                    methodBindingArr6[i11] = methodBindingArr6[i10];
                    iArr3[i11] = iParameterCompatibilityLevel;
                }
                i11++;
            }
            i10++;
            referenceBinding6 = referenceBinding7;
            i8 = 1;
            i9 = 0;
        }
        if (i11 == 0) {
            return new ProblemMethodBinding(methodBindingArr6[i9].selector, typeBindingArr, i8);
        }
        if (i11 == i8) {
            MethodBinding methodBinding3 = methodBindingArr6[i9];
            if (methodBinding3 != null) {
                compilationUnitScope().recordTypeReferences(methodBinding3.thrownExceptions);
            }
            return methodBinding3;
        }
        if (i11 != i) {
            MethodBinding[] methodBindingArr8 = new MethodBinding[i11];
            System.arraycopy(methodBindingArr6, i9, methodBindingArr8, i9, i11);
            int[] iArr4 = new int[i11];
            System.arraycopy(iArr3, i9, iArr4, i9, i11);
            iArr = iArr4;
            methodBindingArr2 = methodBindingArr8;
            i3 = i11;
        } else {
            methodBindingArr2 = methodBindingArr6;
            i3 = i;
            iArr = iArr3;
        }
        MethodBinding[] methodBindingArr9 = new MethodBinding[i3];
        int i12 = 3;
        if (z) {
            int i13 = i9;
            i5 = i13;
            while (i13 < i3) {
                MethodBinding methodBindingGenericMethod = methodBindingArr2[i13].genericMethod();
                TypeBinding[] typeBindingArr2 = methodBindingGenericMethod.parameters;
                int i14 = iArr[i13];
                int i15 = 0;
                while (true) {
                    if (i15 >= i3) {
                        methodBindingArr9[i5] = methodBindingArr2[i13];
                        i5++;
                        iArr2 = iArr;
                        methodBindingArr5 = methodBindingArr2;
                        break;
                    }
                    if (i13 != i15) {
                        int i16 = iArr[i15];
                        if (i14 <= i2 || i16 <= i2 || i14 == i16) {
                            MethodBinding methodBindingGenericMethod2 = methodBindingArr2[i15].genericMethod();
                            i7 = i5;
                            TypeBinding[] typeBindingArr3 = methodBindingGenericMethod2.parameters;
                            iArr2 = iArr;
                            boolean z2 = invocationSite instanceof Invocation;
                            if (z2 || (invocationSite instanceof ReferenceExpression)) {
                                methodBindingArr5 = methodBindingArr2;
                                if (methodBindingGenericMethod2.typeVariables() != Binding.NO_TYPE_VARIABLES) {
                                    if (z2) {
                                        expressionArrCreatePseudoExpressions = ((Invocation) invocationSite).arguments();
                                    } else {
                                        expressionArrCreatePseudoExpressions = ((ReferenceExpression) invocationSite).createPseudoExpressions(typeBindingArr);
                                    }
                                    if (!new InferenceContext18(this, expressionArrCreatePseudoExpressions, null, null).isMoreSpecificThan(methodBindingGenericMethod, methodBindingGenericMethod2, i14 == 2, i16 == 2)) {
                                    }
                                }
                            } else {
                                methodBindingArr5 = methodBindingArr2;
                            }
                            int i17 = 2;
                            int length = typeBindingArr.length;
                            int i18 = 0;
                            while (true) {
                                if (i18 < length) {
                                    TypeBinding typeBinding = typeBindingArr[i18];
                                    int i19 = i14;
                                    TypeBinding parameter = InferenceContext18.getParameter(typeBindingArr2, i18, i14 == 2);
                                    TypeBinding parameter2 = InferenceContext18.getParameter(typeBindingArr3, i18, i16 == 2);
                                    if (TypeBinding.equalsEquals(parameter, parameter2) || typeBinding.sIsMoreSpecific(parameter, parameter2, this)) {
                                        i18++;
                                        i14 = i19;
                                        i17 = 2;
                                    }
                                } else if (i14 == i17 && i16 == i17) {
                                    TypeBinding parameter3 = InferenceContext18.getParameter(typeBindingArr2, typeBindingArr.length, true);
                                    TypeBinding parameter4 = InferenceContext18.getParameter(typeBindingArr3, typeBindingArr.length, true);
                                    if (!TypeBinding.notEquals(parameter3, parameter4) || !parameter4.isSubtypeOf(parameter3)) {
                                    }
                                }
                            }
                        } else {
                            if (i14 >= i16) {
                                i7 = i5;
                                iArr2 = iArr;
                                methodBindingArr5 = methodBindingArr2;
                            }
                            i7 = i5;
                            iArr2 = iArr;
                            methodBindingArr5 = methodBindingArr2;
                        }
                        i5 = i7;
                        break;
                    }
                    i7 = i5;
                    iArr2 = iArr;
                    methodBindingArr5 = methodBindingArr2;
                    i15++;
                    i5 = i7;
                    iArr = iArr2;
                    methodBindingArr2 = methodBindingArr5;
                    i2 = -1;
                }
                i13++;
                referenceBinding6 = referenceBinding;
                iArr = iArr2;
                methodBindingArr2 = methodBindingArr5;
                i8 = 1;
                i9 = 0;
                i12 = 3;
                i2 = -1;
            }
            if (i5 == 0) {
                MethodBinding methodBinding4 = methodBindingArr2[i9];
                return new ProblemMethodBinding(methodBinding4, methodBinding4.selector, methodBindingArr2[i9].parameters, i12);
            }
            if (i5 == i8) {
                MethodBinding methodBinding5 = methodBindingArr9[i9];
                if (methodBinding5 != null) {
                    compilationUnitScope().recordTypeReferences(methodBinding5.thrownExceptions);
                }
                return methodBinding5;
            }
            referenceBinding2 = referenceBinding6;
            i4 = i8;
            methodBindingArr3 = methodBindingArr2;
        } else {
            int[] iArr5 = iArr;
            methodBindingArr3 = methodBindingArr2;
            InvocationSite invocationSite2 = new InvocationSite() { // from class: org.eclipse.jdt.internal.compiler.lookup.Scope.1
                @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
                public void acceptPotentiallyCompatibleMethods(MethodBinding[] methodBindingArr10) {
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
                public boolean checkingPotentialCompatibility() {
                    return false;
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
                public InferenceContext18 freshInferenceContext(Scope scope) {
                    return null;
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
                public TypeBinding[] genericTypeArguments() {
                    return null;
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
                public void setActualReceiverType(ReferenceBinding referenceBinding8) {
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
                public void setDepth(int i20) {
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
                public void setFieldIndex(int i20) {
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
                public boolean isSuperAccess() {
                    return invocationSite.isSuperAccess();
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
                public boolean isTypeAccess() {
                    return invocationSite.isTypeAccess();
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
                public int sourceStart() {
                    return invocationSite.sourceStart();
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
                public int sourceEnd() {
                    return invocationSite.sourceStart();
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
                public TypeBinding invocationTargetType() {
                    return invocationSite.invocationTargetType();
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
                public boolean receiverIsImplicitThis() {
                    return invocationSite.receiverIsImplicitThis();
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
                public ExpressionContext getExpressionContext() {
                    return ExpressionContext.VANILLA_CONTEXT;
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.InvocationSite
                public boolean isQualifiedSuper() {
                    return invocationSite.isQualifiedSuper();
                }
            };
            int i20 = 2;
            int i21 = 0;
            int i22 = 0;
            while (i22 <= i20) {
                int i23 = i21;
                int i24 = 0;
                while (i24 < i3) {
                    if (iArr5[i24] != i22) {
                        methodBindingArr4 = methodBindingArr9;
                    } else {
                        MethodBinding methodBinding6 = methodBindingArr3[i24];
                        MethodBinding methodBindingOriginal = methodBinding6.original();
                        MethodBinding methodBindingTiebreakMethod = methodBinding6.tiebreakMethod();
                        int i25 = 0;
                        while (true) {
                            if (i25 < i3) {
                                if (i24 != i25 && iArr5[i25] == i22) {
                                    MethodBinding methodBinding7 = methodBindingArr3[i25];
                                    if (methodBindingOriginal == methodBinding7.original()) {
                                        iArr5[i25] = -1;
                                        methodBindingArr4 = methodBindingArr9;
                                    } else {
                                        if (methodBinding7 instanceof ParameterizedGenericMethodBinding) {
                                            ParameterizedGenericMethodBinding parameterizedGenericMethodBinding = (ParameterizedGenericMethodBinding) methodBinding7;
                                            if (!parameterizedGenericMethodBinding.isRaw || parameterizedGenericMethodBinding.isStatic()) {
                                                methodBinding = parameterizedGenericMethodBinding.originalMethod;
                                            } else {
                                                methodBinding = methodBinding7;
                                            }
                                        } else {
                                            methodBinding = methodBinding7;
                                        }
                                        methodBindingArr4 = methodBindingArr9;
                                        MethodBinding methodBindingComputeCompatibleMethod = computeCompatibleMethod(methodBinding, methodBindingTiebreakMethod.parameters, invocationSite2, i22 == 2);
                                        if (methodBindingComputeCompatibleMethod == null || !methodBindingComputeCompatibleMethod.isValidBinding() || !isAcceptableMethod(methodBindingTiebreakMethod, methodBindingComputeCompatibleMethod) || (methodBinding6.isBridge() && !methodBinding7.isBridge() && methodBindingTiebreakMethod.areParametersEqual(methodBindingComputeCompatibleMethod))) {
                                            break;
                                        }
                                    }
                                } else {
                                    methodBindingArr4 = methodBindingArr9;
                                }
                                i25++;
                                methodBindingArr9 = methodBindingArr4;
                            } else {
                                methodBindingArr9[i24] = methodBinding6;
                                i23++;
                                methodBindingArr4 = methodBindingArr9;
                                break;
                            }
                        }
                        i20 = i22;
                    }
                    i24++;
                    methodBindingArr9 = methodBindingArr4;
                }
                i22++;
                i21 = i23;
            }
            i4 = 1;
            if (i21 == 1) {
                for (int i26 = 0; i26 < i3; i26++) {
                    if (methodBindingArr9[i26] != null) {
                        MethodBinding methodBinding8 = methodBindingArr3[i26];
                        if (methodBinding8 != null) {
                            compilationUnitScope().recordTypeReferences(methodBinding8.thrownExceptions);
                        }
                        return methodBinding8;
                    }
                }
            } else if (i21 == 0) {
                MethodBinding methodBinding9 = methodBindingArr3[0];
                return new ProblemMethodBinding(methodBinding9, methodBinding9.selector, methodBindingArr3[0].parameters, 3);
            }
            referenceBinding2 = referenceBinding;
            i5 = i3;
        }
        if (referenceBinding2 != null && !(referenceBinding2 instanceof CaptureBinding)) {
            referenceBinding2 = (ReferenceBinding) referenceBinding.erasure();
        }
        ReferenceBinding referenceBinding8 = referenceBinding2;
        int i27 = 0;
        loop4: while (i27 < i5) {
            MethodBinding methodBinding10 = methodBindingArr9[i27];
            if (methodBinding10 != null) {
                MethodBinding methodBindingOriginal2 = methodBinding10.original();
                int i28 = (!methodBindingOriginal2.declaringClass.isAbstract() || methodBindingOriginal2.thrownExceptions == Binding.NO_EXCEPTIONS) ? 0 : i4;
                MethodBinding methodBinding11 = methodBindingOriginal2;
                int i29 = 0;
                ReferenceBinding[] referenceBindingArr = null;
                while (true) {
                    if (i29 >= i5) {
                        return (referenceBindingArr == null || referenceBindingArr == methodBinding10.thrownExceptions) ? methodBinding10 : new MostSpecificExceptionMethodBinding(methodBinding10, referenceBindingArr);
                    }
                    MethodBinding methodBinding12 = methodBindingArr9[i29];
                    if (methodBinding12 == null || i27 == i29) {
                        methodBinding2 = methodBinding10;
                        referenceBinding4 = referenceBinding8;
                        i6 = i5;
                    } else {
                        MethodBinding methodBindingOriginal3 = methodBinding12.original();
                        if (TypeBinding.equalsEquals(methodBinding11.declaringClass, methodBindingOriginal3.declaringClass)) {
                            break loop4;
                        }
                        if (methodBinding11.isAbstract()) {
                            if (referenceBinding8 != null) {
                                TypeBinding typeBindingFindSuperTypeOriginatingFrom = referenceBinding8.findSuperTypeOriginatingFrom(methodBinding11.declaringClass.erasure());
                                if (TypeBinding.equalsEquals(methodBinding11.declaringClass, typeBindingFindSuperTypeOriginatingFrom) || !(typeBindingFindSuperTypeOriginatingFrom instanceof ReferenceBinding)) {
                                    i6 = i5;
                                    break;
                                }
                                MethodBinding[] methods = ((ReferenceBinding) typeBindingFindSuperTypeOriginatingFrom).getMethods(methodBinding11.selector, typeBindingArr.length);
                                int length2 = methods.length;
                                int i30 = 0;
                                while (true) {
                                    if (i30 >= length2) {
                                        i6 = i5;
                                        break;
                                    }
                                    i6 = i5;
                                    if (methods[i30].original() == methodBinding11) {
                                        methodBinding11 = methods[i30];
                                        break;
                                    }
                                    i30++;
                                    i5 = i6;
                                }
                                TypeBinding typeBindingFindSuperTypeOriginatingFrom2 = referenceBinding8.findSuperTypeOriginatingFrom(methodBindingOriginal3.declaringClass.erasure());
                                if (!TypeBinding.equalsEquals(methodBindingOriginal3.declaringClass, typeBindingFindSuperTypeOriginatingFrom2) && (typeBindingFindSuperTypeOriginatingFrom2 instanceof ReferenceBinding)) {
                                    MethodBinding[] methods2 = ((ReferenceBinding) typeBindingFindSuperTypeOriginatingFrom2).getMethods(methodBindingOriginal3.selector, typeBindingArr.length);
                                    int length3 = methods2.length;
                                    for (int i31 = 0; i31 < length3; i31++) {
                                        if (methods2[i31].original() == methodBindingOriginal3) {
                                            methodBindingOriginal3 = methods2[i31];
                                            break;
                                        }
                                    }
                                }
                                if (methodBinding11.typeVariables != Binding.NO_TYPE_VARIABLES) {
                                    methodBindingOriginal3 = methodBinding11.computeSubstitutedMethod(methodBindingOriginal3, environment());
                                }
                                if (methodBindingOriginal3 != null && methodBinding11.areParameterErasuresEqual(methodBindingOriginal3)) {
                                    if (TypeBinding.notEquals(methodBinding11.returnType, methodBindingOriginal3.returnType)) {
                                        if (methodBinding12.original().typeVariables != Binding.NO_TYPE_VARIABLES) {
                                            if (methodBinding11.returnType.erasure().findSuperTypeOriginatingFrom(methodBindingOriginal3.returnType.erasure()) == null) {
                                            }
                                        } else if (!methodBinding10.returnType.isCompatibleWith(methodBinding12.returnType)) {
                                        }
                                    }
                                    if (i28 == 0 || !methodBindingOriginal3.declaringClass.isInterface() || methodBinding10.thrownExceptions == methodBinding12.thrownExceptions) {
                                        methodBinding2 = methodBinding10;
                                        referenceBinding4 = referenceBinding8;
                                    } else {
                                        if (methodBinding12.thrownExceptions == Binding.NO_EXCEPTIONS) {
                                            referenceBindingArr = Binding.NO_EXCEPTIONS;
                                        } else {
                                            if (referenceBindingArr == null) {
                                                referenceBindingArr = methodBinding10.thrownExceptions;
                                            }
                                            ReferenceBinding[] referenceBindingArr2 = referenceBindingArr;
                                            int length4 = referenceBindingArr2.length;
                                            ReferenceBinding[] filteredExceptions = getFilteredExceptions(methodBinding12);
                                            int length5 = filteredExceptions.length;
                                            SimpleSet simpleSet = new SimpleSet(length4);
                                            boolean z3 = false;
                                            int i32 = 0;
                                            while (i32 < length4) {
                                                MethodBinding methodBinding13 = methodBinding10;
                                                TypeBinding typeBinding2 = referenceBindingArr2[i32];
                                                boolean z4 = z3;
                                                int i33 = 0;
                                                while (true) {
                                                    if (i33 >= length5) {
                                                        referenceBinding5 = referenceBinding8;
                                                    } else {
                                                        referenceBinding5 = referenceBinding8;
                                                        ReferenceBinding referenceBinding9 = filteredExceptions[i33];
                                                        if (typeBinding2.isCompatibleWith(referenceBinding9)) {
                                                            simpleSet.add(typeBinding2);
                                                        } else {
                                                            if (referenceBinding9.isCompatibleWith(typeBinding2)) {
                                                                simpleSet.add(referenceBinding9);
                                                                z3 = true;
                                                                break;
                                                            }
                                                            i33++;
                                                            referenceBinding8 = referenceBinding5;
                                                            z4 = true;
                                                        }
                                                    }
                                                    z3 = z4;
                                                    break;
                                                }
                                                i32++;
                                                methodBinding10 = methodBinding13;
                                                referenceBinding8 = referenceBinding5;
                                            }
                                            if (z3) {
                                                referenceBindingArr = simpleSet.elementSize == 0 ? Binding.NO_EXCEPTIONS : new ReferenceBinding[simpleSet.elementSize];
                                                simpleSet.asArray(referenceBindingArr);
                                            } else {
                                                methodBinding2 = methodBinding10;
                                                referenceBinding4 = referenceBinding8;
                                                referenceBindingArr = referenceBindingArr2;
                                            }
                                        }
                                        methodBinding2 = methodBinding10;
                                        referenceBinding4 = referenceBinding8;
                                    }
                                }
                                referenceBinding3 = referenceBinding8;
                            }
                        } else if (!methodBindingOriginal3.isAbstract() && !methodBindingOriginal3.isDefaultMethod() && ((methodBindingFindOriginalInheritedMethod = methodBinding11.findOriginalInheritedMethod(methodBindingOriginal3)) == null || ((methodBinding10.hasSubstitutedParameters() || methodBinding11.typeVariables != Binding.NO_TYPE_VARIABLES) && !environment().methodVerifier().isParameterSubsignature(methodBinding11, methodBindingFindOriginalInheritedMethod)))) {
                            referenceBinding3 = referenceBinding8;
                            i6 = i5;
                        }
                        methodBinding2 = methodBinding10;
                        referenceBinding4 = referenceBinding8;
                        i6 = i5;
                    }
                    i29++;
                    methodBinding10 = methodBinding2;
                    referenceBinding8 = referenceBinding4;
                    i5 = i6;
                }
            } else {
                referenceBinding3 = referenceBinding8;
                i6 = i5;
            }
            i27++;
            referenceBinding8 = referenceBinding3;
            i5 = i6;
            i4 = 1;
        }
        MethodBinding methodBinding14 = methodBindingArr3[0];
        return new ProblemMethodBinding(methodBinding14, methodBinding14.selector, methodBindingArr3[0].parameters, 3);
    }

    private ReferenceBinding[] getFilteredExceptions(MethodBinding methodBinding) {
        ReferenceBinding[] referenceBindingArr = methodBinding.thrownExceptions;
        int length = referenceBindingArr.length;
        if (length < 2) {
            return referenceBindingArr;
        }
        ReferenceBinding[] referenceBindingArr2 = new ReferenceBinding[length];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            ReferenceBinding referenceBinding = referenceBindingArr[i2];
            int i3 = 0;
            while (true) {
                if (i3 < length) {
                    if (i2 != i3) {
                        if (!TypeBinding.equalsEquals(referenceBinding, referenceBindingArr[i3])) {
                            if (referenceBinding.isCompatibleWith(referenceBindingArr[i3])) {
                                break;
                            }
                        } else {
                            if (i2 >= i3) {
                                break;
                            }
                        }
                    }
                    i3++;
                }
                referenceBindingArr2[i] = referenceBinding;
                i++;
                break;
            }
        }
        if (i == length) {
            return referenceBindingArr;
        }
        ReferenceBinding[] referenceBindingArr3 = new ReferenceBinding[i];
        System.arraycopy(referenceBindingArr2, 0, referenceBindingArr3, 0, i);
        return referenceBindingArr3;
    }

    public final ClassScope outerMostClassScope() {
        ClassScope classScope = null;
        Scope scope = this;
        do {
            if (scope instanceof ClassScope) {
                classScope = (ClassScope) scope;
            }
            scope = scope.parent;
        } while (scope != null);
        return classScope;
    }

    public final MethodScope outerMostMethodScope() {
        MethodScope methodScope = null;
        Scope scope = this;
        do {
            if (scope instanceof MethodScope) {
                methodScope = (MethodScope) scope;
            }
            scope = scope.parent;
        } while (scope != null);
        return methodScope;
    }

    public int parameterCompatibilityLevel(MethodBinding methodBinding, TypeBinding[] typeBindingArr, InvocationSite invocationSite) {
        InferenceContext18 inferenceContext;
        int i;
        if (methodBinding.problemId() == 23 && (methodBinding = ((ProblemMethodBinding) methodBinding).closestMatch) == null) {
            return -1;
        }
        if (compilerOptions().sourceLevel >= ClassFileConstants.JDK1_8 && (methodBinding instanceof ParameterizedGenericMethodBinding)) {
            boolean z = invocationSite instanceof Invocation;
            if (z) {
                inferenceContext = ((Invocation) invocationSite).getInferenceContext((ParameterizedGenericMethodBinding) methodBinding);
                i = inferenceContext != null ? inferenceContext.inferenceKind : 0;
            } else if (invocationSite instanceof ReferenceExpression) {
                inferenceContext = ((ReferenceExpression) invocationSite).getInferenceContext((ParameterizedGenericMethodBinding) methodBinding);
                if (inferenceContext != null) {
                    i = inferenceContext.inferenceKind;
                }
            } else {
                inferenceContext = null;
            }
            if (z && inferenceContext != null && inferenceContext.stepCompleted >= 2) {
                int length = typeBindingArr.length;
                for (int i2 = 0; i2 < length; i2++) {
                    TypeBinding typeBinding = typeBindingArr[i2];
                    if (typeBinding.isFunctionalType() && !typeBinding.isCompatibleWith(InferenceContext18.getParameter(methodBinding.parameters, i2, inferenceContext.isVarArgs()), this)) {
                        if (typeBinding.isPolyType()) {
                            if (!((PolyTypeBinding) typeBinding).expression.isPertinentToApplicability(InferenceContext18.getParameter(methodBinding.original().parameters, i2, inferenceContext.isVarArgs()), methodBinding)) {
                            }
                        }
                        return -1;
                    }
                }
            }
            if (i == 1) {
                return 0;
            }
            if (i == 2) {
                return 1;
            }
            if (i == 3) {
                return 2;
            }
        }
        return parameterCompatibilityLevel(methodBinding, typeBindingArr, false);
    }

    public int parameterCompatibilityLevel(MethodBinding methodBinding, TypeBinding[] typeBindingArr) {
        return parameterCompatibilityLevel(methodBinding, typeBindingArr, false);
    }

    /* JADX WARN: Code duplicated, block: B:59:0x00b2  */
    /* JADX WARN: Code duplicated, block: B:64:0x00c3  */
    /* JADX WARN: Code duplicated, block: B:67:0x00cb  */
    /* JADX WARN: Code duplicated, block: B:70:0x00d2 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:71:0x00d4  */
    /* JADX WARN: Code duplicated, block: B:77:0x00d1 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:80:0x00d5 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:81:0x00d5 A[SYNTHETIC] */
    public int parameterCompatibilityLevel(MethodBinding methodBinding, TypeBinding[] typeBindingArr, boolean z) {
        int i;
        int i2;
        TypeBinding typeBinding;
        TypeBinding typeBindingElementsType;
        int iParameterCompatibilityLevel;
        TypeBinding[] typeBindingArr2 = methodBinding.parameters;
        int length = typeBindingArr2.length;
        int length2 = typeBindingArr.length;
        CompilerOptions compilerOptions = compilerOptions();
        if (compilerOptions.sourceLevel < ClassFileConstants.JDK1_5) {
            if (length != length2) {
                return -1;
            }
            for (int i3 = 0; i3 < length2; i3++) {
                TypeBinding typeBinding2 = typeBindingArr2[i3];
                TypeBinding typeBinding3 = typeBindingArr[i3];
                if (TypeBinding.notEquals(typeBinding3, typeBinding2) && !typeBinding3.isCompatibleWith(typeBinding2.erasure(), this)) {
                    return -1;
                }
            }
            return 0;
        }
        if (z && CompilerOptions.tolerateIllegalAmbiguousVarargsInvocation && compilerOptions.complianceLevel < ClassFileConstants.JDK1_7) {
            z = false;
        }
        LookupEnvironment lookupEnvironmentEnvironment = environment();
        if (methodBinding.isVarargs()) {
            i = length - 1;
            i2 = 2;
            if (length == length2) {
                TypeBinding typeBinding4 = typeBindingArr2[i];
                TypeBinding typeBindingElementsType2 = typeBindingArr[i];
                if (TypeBinding.notEquals(typeBinding4, typeBindingElementsType2)) {
                    int iParameterCompatibilityLevel2 = parameterCompatibilityLevel(typeBindingElementsType2, typeBinding4, lookupEnvironmentEnvironment, z);
                    if (iParameterCompatibilityLevel2 == -1) {
                        TypeBinding typeBindingElementsType3 = ((ArrayBinding) typeBinding4).elementsType();
                        if (z) {
                            typeBindingElementsType2 = ((ArrayBinding) typeBindingElementsType2).elementsType();
                        }
                        if (parameterCompatibilityLevel(typeBindingElementsType2, typeBindingElementsType3, lookupEnvironmentEnvironment, z) == -1) {
                            return -1;
                        }
                    } else {
                        i2 = iParameterCompatibilityLevel2;
                    }
                }
            } else if (length < length2) {
                TypeBinding typeBindingElementsType4 = ((ArrayBinding) typeBindingArr2[i]).elementsType();
                int i4 = i;
                while (i4 < length2) {
                    TypeBinding typeBindingElementsType5 = (z && i4 == length2 + (-1)) ? ((ArrayBinding) typeBindingArr[i4]).elementsType() : typeBindingArr[i4];
                    if (TypeBinding.notEquals(typeBindingElementsType4, typeBindingElementsType5) && parameterCompatibilityLevel(typeBindingElementsType5, typeBindingElementsType4, lookupEnvironmentEnvironment, z) == -1) {
                        return -1;
                    }
                    i4++;
                }
            } else if (i != length2) {
                return -1;
            }
            for (int i5 = 0; i5 < i; i5++) {
                typeBinding = typeBindingArr2[i5];
                if (z || i5 != length2 - 1) {
                    typeBindingElementsType = typeBindingArr[i5];
                } else {
                    typeBindingElementsType = ((ArrayBinding) typeBindingArr[i5]).elementsType();
                }
                if (!TypeBinding.notEquals(typeBindingElementsType, typeBinding)) {
                    iParameterCompatibilityLevel = parameterCompatibilityLevel(typeBindingElementsType, typeBinding, lookupEnvironmentEnvironment, z);
                    if (iParameterCompatibilityLevel == -1) {
                        return -1;
                    }
                    if (iParameterCompatibilityLevel > i2) {
                        i2 = iParameterCompatibilityLevel;
                    }
                }
            }
            return i2;
        }
        if (length != length2) {
            return -1;
        }
        i = length2;
        i2 = 0;
        while (i5 < i) {
            typeBinding = typeBindingArr2[i5];
            if (z) {
                typeBindingElementsType = typeBindingArr[i5];
            } else {
                typeBindingElementsType = typeBindingArr[i5];
            }
            if (!TypeBinding.notEquals(typeBindingElementsType, typeBinding)) {
                iParameterCompatibilityLevel = parameterCompatibilityLevel(typeBindingElementsType, typeBinding, lookupEnvironmentEnvironment, z);
                if (iParameterCompatibilityLevel == -1) {
                    return -1;
                }
                if (iParameterCompatibilityLevel > i2) {
                    i2 = iParameterCompatibilityLevel;
                }
            }
        }
        return i2;
    }

    public int parameterCompatibilityLevel(TypeBinding typeBinding, TypeBinding typeBinding2) {
        if (TypeBinding.equalsEquals(typeBinding, typeBinding2)) {
            return 0;
        }
        if (typeBinding == null || typeBinding2 == null) {
            return -1;
        }
        if (typeBinding.isCompatibleWith(typeBinding2, this)) {
            return 0;
        }
        if (typeBinding.kind() == 65540 || typeBinding.isBaseType() != typeBinding2.isBaseType()) {
            TypeBinding typeBindingComputeBoxingType = environment().computeBoxingType(typeBinding);
            if (TypeBinding.equalsEquals(typeBindingComputeBoxingType, typeBinding2) || typeBindingComputeBoxingType.isCompatibleWith(typeBinding2, this)) {
                return 1;
            }
        }
        return -1;
    }

    private int parameterCompatibilityLevel(TypeBinding typeBinding, TypeBinding typeBinding2, LookupEnvironment lookupEnvironment, boolean z) {
        if (typeBinding == null || typeBinding2 == null) {
            return -1;
        }
        if (typeBinding.isCompatibleWith(typeBinding2, this)) {
            return 0;
        }
        if (z && (compilerOptions().complianceLevel >= ClassFileConstants.JDK1_7 || !CompilerOptions.tolerateIllegalAmbiguousVarargsInvocation)) {
            return -1;
        }
        if (typeBinding.kind() == 65540 || typeBinding.isBaseType() != typeBinding2.isBaseType()) {
            TypeBinding typeBindingComputeBoxingType = lookupEnvironment.computeBoxingType(typeBinding);
            if (TypeBinding.equalsEquals(typeBindingComputeBoxingType, typeBinding2) || typeBindingComputeBoxingType.isCompatibleWith(typeBinding2, this)) {
                return 1;
            }
        }
        return -1;
    }

    public final CompilationUnitDeclaration referenceCompilationUnit() {
        Scope scope = this;
        while (true) {
            Scope scope2 = scope.parent;
            if (scope2 == null) {
                return ((CompilationUnitScope) scope).referenceContext;
            }
            scope = scope2;
        }
    }

    public ReferenceContext referenceContext() {
        Scope scope = this;
        do {
            int i = scope.kind;
            if (i == 2) {
                return ((MethodScope) scope).referenceContext;
            }
            if (i == 3) {
                return ((ClassScope) scope).referenceContext;
            }
            if (i == 4) {
                return ((CompilationUnitScope) scope).referenceContext;
            }
            scope = scope.parent;
        } while (scope != null);
        return null;
    }

    public ReferenceContext originalReferenceContext() {
        Scope scope = this;
        do {
            int i = scope.kind;
            if (i == 2) {
                ReferenceContext referenceContext = ((MethodScope) scope).referenceContext;
                if (!(referenceContext instanceof LambdaExpression)) {
                    return referenceContext;
                }
                LambdaExpression lambdaExpression = (LambdaExpression) referenceContext;
                while (lambdaExpression != lambdaExpression.original) {
                    lambdaExpression = lambdaExpression.original;
                }
                return lambdaExpression;
            }
            if (i == 3) {
                return ((ClassScope) scope).referenceContext;
            }
            if (i == 4) {
                return ((CompilationUnitScope) scope).referenceContext;
            }
            scope = scope.parent;
        } while (scope != null);
        return null;
    }

    public boolean deferCheck(Runnable runnable) {
        Scope scope = this.parent;
        if (scope != null) {
            return scope.deferCheck(runnable);
        }
        return false;
    }

    public void deferBoundCheck(TypeReference typeReference) {
        if (this.kind == 3) {
            ClassScope classScope = (ClassScope) this;
            if (classScope.deferredBoundChecks == null) {
                classScope.deferredBoundChecks = new ArrayList<>(3);
                classScope.deferredBoundChecks.add(typeReference);
            } else {
                if (classScope.deferredBoundChecks.contains(typeReference)) {
                    return;
                }
                classScope.deferredBoundChecks.add(typeReference);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:45:0x00a4  */
    public MethodBinding getStaticFactory(ParameterizedTypeBinding parameterizedTypeBinding, ReferenceBinding referenceBinding, TypeBinding[] typeBindingArr, InvocationSite invocationSite) {
        MethodBinding[] methodBindingArr;
        TypeVariableBinding[] typeVariableBindingArr = Binding.NO_TYPE_VARIABLES;
        ReferenceBinding referenceBindingGenericType = parameterizedTypeBinding.genericType();
        int i = 0;
        ReferenceBinding referenceBindingEnclosingType = referenceBindingGenericType;
        int i2 = 0;
        while (referenceBindingEnclosingType != null) {
            TypeVariableBinding[] typeVariableBindingArrTypeVariables = referenceBindingEnclosingType.typeVariables();
            int length = typeVariableBindingArrTypeVariables == null ? i : typeVariableBindingArrTypeVariables.length;
            if (length > 0) {
                int i3 = i2 + length;
                TypeVariableBinding[] typeVariableBindingArr2 = new TypeVariableBinding[i3];
                System.arraycopy(typeVariableBindingArr, i, typeVariableBindingArr2, i, i2);
                System.arraycopy(typeVariableBindingArrTypeVariables, i, typeVariableBindingArr2, i2, length);
                i2 = i3;
                typeVariableBindingArr = typeVariableBindingArr2;
            }
            if (referenceBindingEnclosingType.isStatic()) {
                break;
            }
            referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType();
            i = 0;
        }
        TypeVariableBinding[] typeVariableBindingArr3 = typeVariableBindingArr;
        int i4 = i2;
        MethodBinding[] methods = parameterizedTypeBinding.getMethods(TypeConstants.INIT, typeBindingArr.length);
        MethodBinding[] methodBindingArr2 = new MethodBinding[methods.length];
        int length2 = methods.length;
        int i5 = i;
        int i6 = i5;
        while (i6 < length2) {
            MethodBinding methodBinding = methods[i6];
            if (methodBinding.canBeSeenBy(invocationSite, this)) {
                int length3 = methodBinding.parameters.length;
                boolean zIsVarargs = methodBinding.isVarargs();
                if (typeBindingArr.length == length3 || (zIsVarargs && typeBindingArr.length >= length3 - 1)) {
                    TypeVariableBinding[] typeVariableBindingArrTypeVariables2 = methodBinding.typeVariables();
                    int length4 = typeVariableBindingArrTypeVariables2.length + i4;
                    LookupEnvironment lookupEnvironmentEnvironment = environment();
                    SyntheticFactoryMethodBinding syntheticFactoryMethodBinding = new SyntheticFactoryMethodBinding(methodBinding.original(), lookupEnvironmentEnvironment, referenceBinding);
                    syntheticFactoryMethodBinding.typeVariables = new TypeVariableBinding[length4];
                    final SimpleLookupTable simpleLookupTable = new SimpleLookupTable(length4);
                    String str = "";
                    int i7 = 0;
                    Binding binding = null;
                    while (i7 < i4) {
                        int i8 = i5;
                        ReferenceBinding referenceBinding2 = referenceBindingGenericType;
                        TypeVariableBinding[] typeVariableBindingArr4 = typeVariableBindingArrTypeVariables2;
                        MethodBinding methodBinding2 = methodBinding;
                        TypeVariableBinding typeVariableBinding = typeVariableBindingArr3[i7];
                        Binding binding2 = binding;
                        if (typeVariableBinding.declaringElement != binding2) {
                            binding = typeVariableBinding.declaringElement;
                            str = String.valueOf(str) + FormatHelper.QUOTE;
                        } else {
                            binding = binding2;
                        }
                        TypeBinding typeBindingUnannotated = typeVariableBinding.unannotated();
                        TypeVariableBinding[] typeVariableBindingArr5 = syntheticFactoryMethodBinding.typeVariables;
                        TypeVariableBinding typeVariableBinding2 = new TypeVariableBinding(CharOperation.concat(typeVariableBinding.sourceName, str.toCharArray()), syntheticFactoryMethodBinding, i7, lookupEnvironmentEnvironment);
                        typeVariableBindingArr5[i7] = typeVariableBinding2;
                        simpleLookupTable.put(typeBindingUnannotated, typeVariableBinding2);
                        i7++;
                        this = this;
                        referenceBinding = referenceBinding;
                        methodBinding = methodBinding2;
                        typeVariableBindingArrTypeVariables2 = typeVariableBindingArr4;
                        i5 = i8;
                        referenceBindingGenericType = referenceBinding2;
                    }
                    TypeVariableBinding[] typeVariableBindingArr6 = typeVariableBindingArrTypeVariables2;
                    String str2 = String.valueOf(str) + FormatHelper.QUOTE;
                    int i9 = i4;
                    int i10 = 0;
                    while (i9 < length4) {
                        int i11 = i5;
                        ReferenceBinding referenceBinding3 = referenceBindingGenericType;
                        TypeBinding typeBindingUnannotated2 = typeVariableBindingArr6[i10].unannotated();
                        TypeVariableBinding[] typeVariableBindingArr7 = syntheticFactoryMethodBinding.typeVariables;
                        TypeVariableBinding typeVariableBinding3 = new TypeVariableBinding(CharOperation.concat(typeVariableBindingArr6[i10].sourceName, str2.toCharArray()), syntheticFactoryMethodBinding, i9, lookupEnvironmentEnvironment);
                        typeVariableBindingArr7[i9] = typeVariableBinding3;
                        simpleLookupTable.put(typeBindingUnannotated2, typeVariableBinding3);
                        i9++;
                        i10++;
                        this = this;
                        referenceBinding = referenceBinding;
                        methodBinding = methodBinding;
                        i5 = i11;
                        referenceBindingGenericType = referenceBinding3;
                    }
                    Substitution substitution = new Substitution() { // from class: org.eclipse.jdt.internal.compiler.lookup.Scope.2
                        @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
                        public boolean isRawSubstitution() {
                            return false;
                        }

                        @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
                        public LookupEnvironment environment() {
                            return this.environment();
                        }

                        @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
                        public TypeBinding substitute(TypeVariableBinding typeVariableBinding4) {
                            TypeBinding typeBinding = (TypeBinding) simpleLookupTable.get(typeVariableBinding4.unannotated());
                            if (typeBinding == null) {
                                return typeVariableBinding4;
                            }
                            return typeVariableBinding4.hasTypeAnnotations() ? environment().createAnnotatedType(typeBinding, typeVariableBinding4.getTypeAnnotations()) : typeBinding;
                        }
                    };
                    int i12 = 0;
                    while (i12 < length4) {
                        TypeVariableBinding typeVariableBinding4 = i12 < i4 ? typeVariableBindingArr3[i12] : typeVariableBindingArr6[i12 - i4];
                        TypeVariableBinding typeVariableBinding5 = (TypeVariableBinding) simpleLookupTable.get(typeVariableBinding4.unannotated());
                        int i13 = i5;
                        TypeBinding typeBindingSubstitute = substitute(substitution, typeVariableBinding4.superclass);
                        ReferenceBinding referenceBinding4 = referenceBindingGenericType;
                        ReferenceBinding[] referenceBindingArrSubstitute = substitute(substitution, typeVariableBinding4.superInterfaces);
                        Substitution substitution2 = substitution;
                        if (typeVariableBinding4.firstBound != null) {
                            typeVariableBinding5.setFirstBound(TypeBinding.equalsEquals(typeVariableBinding4.firstBound, typeVariableBinding4.superclass) ? typeBindingSubstitute : referenceBindingArrSubstitute[0]);
                        }
                        if (typeBindingSubstitute.kind() == 68) {
                            methodBinding = methodBinding;
                            typeVariableBinding5.setSuperClass(lookupEnvironmentEnvironment.getResolvedType(TypeConstants.JAVA_LANG_OBJECT, null));
                            typeVariableBinding5.setSuperInterfaces(referenceBindingArrSubstitute);
                        } else if (typeBindingSubstitute.isInterface()) {
                            typeVariableBinding5.setSuperClass(lookupEnvironmentEnvironment.getResolvedType(TypeConstants.JAVA_LANG_OBJECT, null));
                            int length5 = referenceBindingArrSubstitute.length;
                            ReferenceBinding[] referenceBindingArr = new ReferenceBinding[length5 + 1];
                            System.arraycopy(referenceBindingArrSubstitute, 0, referenceBindingArr, 1, length5);
                            referenceBindingArr[0] = (ReferenceBinding) typeBindingSubstitute;
                            typeVariableBinding5.setSuperInterfaces(referenceBindingArr);
                        } else {
                            typeVariableBinding5.setSuperClass((ReferenceBinding) typeBindingSubstitute);
                            typeVariableBinding5.setSuperInterfaces(referenceBindingArrSubstitute);
                        }
                        i12++;
                        this = this;
                        referenceBinding = referenceBinding;
                        substitution = substitution2;
                        methodBinding = methodBinding;
                        i5 = i13;
                        referenceBindingGenericType = referenceBinding4;
                    }
                    syntheticFactoryMethodBinding.returnType = lookupEnvironmentEnvironment.createParameterizedType(referenceBindingGenericType, substitute(substitution, (ReferenceBinding[]) referenceBindingGenericType.typeVariables()), referenceBinding);
                    syntheticFactoryMethodBinding.parameters = substitute(substitution, methodBinding.parameters);
                    syntheticFactoryMethodBinding.thrownExceptions = substitute(substitution, methodBinding.thrownExceptions);
                    if (syntheticFactoryMethodBinding.thrownExceptions == null) {
                        syntheticFactoryMethodBinding.thrownExceptions = Binding.NO_EXCEPTIONS;
                    }
                    methodBindingArr2[i5] = new ParameterizedMethodBinding((ParameterizedTypeBinding) lookupEnvironmentEnvironment.convertToParameterizedType(syntheticFactoryMethodBinding.declaringClass), syntheticFactoryMethodBinding);
                    i5++;
                }
            }
            i6++;
            typeBindingArr = typeBindingArr;
            invocationSite = invocationSite;
            methods = methods;
            length2 = length2;
            i = 0;
        }
        if (i5 == 0) {
            return null;
        }
        if (i5 != methods.length) {
            MethodBinding[] methodBindingArr3 = new MethodBinding[i5];
            System.arraycopy(methodBindingArr2, i, methodBindingArr3, i, i5);
            methodBindingArr = methodBindingArr3;
        } else {
            methodBindingArr = methodBindingArr2;
        }
        MethodBinding[] methodBindingArr4 = new MethodBinding[i5];
        int i14 = i;
        int i15 = i14;
        while (i14 < i5) {
            MethodBinding methodBindingComputeCompatibleMethod = this.computeCompatibleMethod(methodBindingArr[i14], typeBindingArr, invocationSite);
            if (methodBindingComputeCompatibleMethod != null && methodBindingComputeCompatibleMethod.isValidBinding()) {
                methodBindingArr4[i15] = methodBindingComputeCompatibleMethod;
                i15++;
            }
            i14++;
        }
        if (i15 == 0) {
            return null;
        }
        return i15 == 1 ? methodBindingArr4[i] : mostSpecificMethodBinding(methodBindingArr4, i15, typeBindingArr, invocationSite, parameterizedTypeBinding);
    }

    public boolean validateNullAnnotation(long j, TypeReference typeReference, Annotation[] annotationArr) {
        if (typeReference == null) {
            return true;
        }
        TypeBinding typeBindingLeafComponentType = typeReference.resolvedType;
        boolean zUsesNullTypeAnnotations = environment().usesNullTypeAnnotations();
        if (zUsesNullTypeAnnotations) {
            typeBindingLeafComponentType = typeBindingLeafComponentType.leafComponentType();
            j = typeBindingLeafComponentType.tagBits;
        }
        long j2 = j & TagBits.AnnotationNullMASK;
        if (j2 == 0 || typeBindingLeafComponentType == null || !typeBindingLeafComponentType.isBaseType()) {
            return true;
        }
        if (typeReference.resolvedType.f626id == 6 && zUsesNullTypeAnnotations) {
            return false;
        }
        problemReporter().illegalAnnotationForBaseType(typeReference, annotationArr, j2);
        return false;
    }

    public static BlockScope typeAnnotationsResolutionScope(Scope scope) {
        int i = scope.kind;
        if (i == 1 || i == 2) {
            return (BlockScope) scope;
        }
        if (i != 3) {
            return null;
        }
        return ((ClassScope) scope).referenceContext.staticInitializerScope;
    }

    public void tagAsAccessingEnclosingInstanceStateOf(ReferenceBinding referenceBinding, boolean z) {
        TypeDeclaration typeDeclaration;
        MethodScope methodScope = methodScope();
        if (methodScope != null && (methodScope.referenceContext instanceof TypeDeclaration) && !methodScope.enclosingReceiverType().isCompatibleWith(referenceBinding)) {
            methodScope = methodScope.enclosingMethodScope();
        }
        MethodBinding methodBindingEnclosingMethod = referenceBinding != null ? referenceBinding.enclosingMethod() : null;
        while (methodScope != null) {
            while (methodScope != null && (methodScope.referenceContext instanceof LambdaExpression)) {
                LambdaExpression lambdaExpression = (LambdaExpression) methodScope.referenceContext;
                if (!z) {
                    lambdaExpression.shouldCaptureInstance = true;
                }
                methodScope = methodScope.enclosingMethodScope();
            }
            if (methodScope != null) {
                if (methodScope.referenceContext instanceof MethodDeclaration) {
                    MethodDeclaration methodDeclaration = (MethodDeclaration) methodScope.referenceContext;
                    if (methodDeclaration.binding == methodBindingEnclosingMethod) {
                        return;
                    } else {
                        methodDeclaration.bits &= -257;
                    }
                }
                ClassScope classScopeEnclosingClassScope = methodScope.enclosingClassScope();
                if (classScopeEnclosingClassScope == null || (typeDeclaration = classScopeEnclosingClassScope.referenceContext) == null || typeDeclaration.binding == null || referenceBinding == null || typeDeclaration.binding.isCompatibleWith(referenceBinding.original())) {
                    return;
                } else {
                    methodScope = classScopeEnclosingClassScope.enclosingMethodScope();
                }
            }
        }
    }
}
