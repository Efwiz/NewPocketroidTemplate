package org.eclipse.jdt.internal.compiler.lookup;

import org.eclipse.jdt.internal.compiler.ast.NullAnnotationMatching;

/* JADX INFO: loaded from: classes14.dex */
public class ParameterizedMethodBinding extends MethodBinding {
    protected MethodBinding originalMethod;

    /* JADX WARN: Multi-variable type inference failed */
    public ParameterizedMethodBinding(final ParameterizedTypeBinding parameterizedTypeBinding, MethodBinding methodBinding) {
        super(methodBinding.modifiers, methodBinding.selector, methodBinding.returnType, methodBinding.parameters, methodBinding.thrownExceptions, parameterizedTypeBinding);
        this.originalMethod = methodBinding;
        this.tagBits = methodBinding.tagBits & (-129);
        this.parameterNonNullness = methodBinding.parameterNonNullness;
        this.defaultNullness = methodBinding.defaultNullness;
        TypeVariableBinding[] typeVariableBindingArr = methodBinding.typeVariables;
        final int length = typeVariableBindingArr.length;
        final boolean zIsStatic = methodBinding.isStatic();
        int i = 1;
        Substitution substitution = null;
        if (length == 0) {
            this.typeVariables = Binding.NO_TYPE_VARIABLES;
            if (!zIsStatic) {
                substitution = parameterizedTypeBinding;
            }
        } else {
            final TypeVariableBinding[] typeVariableBindingArr2 = new TypeVariableBinding[length];
            int i2 = 0;
            while (i2 < length) {
                TypeVariableBinding typeVariableBinding = typeVariableBindingArr[i2];
                TypeVariableBinding typeVariableBinding2 = new TypeVariableBinding(typeVariableBinding.sourceName, this, typeVariableBinding.rank, parameterizedTypeBinding.environment);
                typeVariableBindingArr2[i2] = typeVariableBinding2;
                typeVariableBinding2.tagBits |= typeVariableBinding.tagBits & 108086391057940480L;
                i2++;
                typeVariableBindingArr = typeVariableBindingArr;
                i = 1;
            }
            this.typeVariables = typeVariableBindingArr2;
            final TypeVariableBinding[] typeVariableBindingArr3 = typeVariableBindingArr;
            Substitution substitution2 = new Substitution() { // from class: org.eclipse.jdt.internal.compiler.lookup.ParameterizedMethodBinding.1
                @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
                public LookupEnvironment environment() {
                    return parameterizedTypeBinding.environment;
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
                public boolean isRawSubstitution() {
                    return !zIsStatic && parameterizedTypeBinding.isRawSubstitution();
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
                public TypeBinding substitute(TypeVariableBinding typeVariableBinding3) {
                    if (typeVariableBinding3.rank >= length || !TypeBinding.equalsEquals(typeVariableBindingArr3[typeVariableBinding3.rank], typeVariableBinding3)) {
                        return !zIsStatic ? parameterizedTypeBinding.substitute(typeVariableBinding3) : typeVariableBinding3;
                    }
                    TypeVariableBinding typeVariableBinding4 = typeVariableBindingArr2[typeVariableBinding3.rank];
                    return typeVariableBinding3.hasTypeAnnotations() ? environment().createAnnotatedType(typeVariableBinding4, typeVariableBinding3.getTypeAnnotations()) : typeVariableBinding4;
                }
            };
            for (int i3 = 0; i3 < length; i3++) {
                TypeVariableBinding typeVariableBinding3 = typeVariableBindingArr[i3];
                TypeVariableBinding typeVariableBinding4 = typeVariableBindingArr2[i3];
                TypeBinding typeBindingSubstitute = Scope.substitute(substitution2, typeVariableBinding3.superclass);
                ReferenceBinding[] referenceBindingArrSubstitute = Scope.substitute(substitution2, typeVariableBinding3.superInterfaces);
                if (typeVariableBinding3.firstBound != null) {
                    typeVariableBinding4.setFirstBound(TypeBinding.equalsEquals(typeVariableBinding3.firstBound, typeVariableBinding3.superclass) ? typeBindingSubstitute : referenceBindingArrSubstitute[0]);
                }
                if (typeBindingSubstitute.kind() == 68) {
                    typeVariableBinding4.setSuperClass(parameterizedTypeBinding.environment.getResolvedType(TypeConstants.JAVA_LANG_OBJECT, null));
                    typeVariableBinding4.setSuperInterfaces(referenceBindingArrSubstitute);
                } else if (typeBindingSubstitute.isInterface()) {
                    typeVariableBinding4.setSuperClass(parameterizedTypeBinding.environment.getResolvedType(TypeConstants.JAVA_LANG_OBJECT, null));
                    int length2 = referenceBindingArrSubstitute.length;
                    ReferenceBinding[] referenceBindingArr = new ReferenceBinding[length2 + 1];
                    System.arraycopy(referenceBindingArrSubstitute, 0, referenceBindingArr, i, length2);
                    referenceBindingArr[0] = (ReferenceBinding) typeBindingSubstitute;
                    typeVariableBinding4.setSuperInterfaces(referenceBindingArr);
                } else {
                    typeVariableBinding4.setSuperClass((ReferenceBinding) typeBindingSubstitute);
                    typeVariableBinding4.setSuperInterfaces(referenceBindingArrSubstitute);
                }
            }
            substitution = substitution2;
        }
        if (substitution != null) {
            this.returnType = Scope.substitute(substitution, this.returnType);
            this.parameters = Scope.substitute(substitution, this.parameters);
            this.thrownExceptions = Scope.substitute(substitution, this.thrownExceptions);
            if (this.thrownExceptions == null) {
                this.thrownExceptions = Binding.NO_EXCEPTIONS;
            }
            if (parameterizedTypeBinding.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
                long jValidNullTagBits = NullAnnotationMatching.validNullTagBits(this.returnType.tagBits);
                if (jValidNullTagBits != 0) {
                    this.tagBits &= -108086391056891905L;
                    this.tagBits = jValidNullTagBits | this.tagBits;
                }
                int length3 = this.parameters.length;
                for (int i4 = 0; i4 < length3; i4++) {
                    long jValidNullTagBits2 = NullAnnotationMatching.validNullTagBits(this.parameters[i4].tagBits);
                    if (jValidNullTagBits2 != 0) {
                        if (this.parameterNonNullness == null) {
                            this.parameterNonNullness = new Boolean[length3];
                        }
                        this.parameterNonNullness[i4] = Boolean.valueOf(jValidNullTagBits2 == 72057594037927936L ? i : 0);
                    }
                }
            }
        }
        if ((this.tagBits & 128) != 0) {
            return;
        }
        if ((this.returnType.tagBits & 128) != 0) {
            this.tagBits |= 128;
            return;
        }
        int length4 = this.parameters.length;
        for (int i5 = 0; i5 < length4; i5++) {
            if ((this.parameters[i5].tagBits & 128) != 0) {
                this.tagBits |= 128;
                return;
            }
        }
        int length5 = this.thrownExceptions.length;
        for (int i6 = 0; i6 < length5; i6++) {
            if ((this.thrownExceptions[i6].tagBits & 128) != 0) {
                this.tagBits |= 128;
                return;
            }
        }
    }

    public ParameterizedMethodBinding(ReferenceBinding referenceBinding, MethodBinding methodBinding, char[][] cArr, final LookupEnvironment lookupEnvironment) {
        char[] cArr2;
        super(methodBinding.modifiers, methodBinding.selector, methodBinding.returnType, methodBinding.parameters, methodBinding.thrownExceptions, referenceBinding);
        this.originalMethod = methodBinding;
        this.tagBits = methodBinding.tagBits & (-129);
        this.parameterNonNullness = methodBinding.parameterNonNullness;
        this.defaultNullness = methodBinding.defaultNullness;
        final TypeVariableBinding[] typeVariableBindingArr = methodBinding.typeVariables;
        final int length = typeVariableBindingArr.length;
        Substitution substitution = null;
        if (length == 0) {
            this.typeVariables = Binding.NO_TYPE_VARIABLES;
        } else {
            final TypeVariableBinding[] typeVariableBindingArr2 = new TypeVariableBinding[length];
            for (int i = 0; i < length; i++) {
                TypeVariableBinding typeVariableBinding = typeVariableBindingArr[i];
                if (cArr == null) {
                    cArr2 = typeVariableBinding.sourceName;
                } else {
                    cArr2 = cArr[i];
                }
                TypeVariableBinding typeVariableBinding2 = new TypeVariableBinding(cArr2, this, typeVariableBinding.rank, lookupEnvironment);
                typeVariableBindingArr2[i] = typeVariableBinding2;
                typeVariableBinding2.tagBits |= typeVariableBinding.tagBits & 108086391057940480L;
            }
            this.typeVariables = typeVariableBindingArr2;
            Substitution substitution2 = new Substitution() { // from class: org.eclipse.jdt.internal.compiler.lookup.ParameterizedMethodBinding.2
                @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
                public boolean isRawSubstitution() {
                    return false;
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
                public LookupEnvironment environment() {
                    return lookupEnvironment;
                }

                @Override // org.eclipse.jdt.internal.compiler.lookup.Substitution
                public TypeBinding substitute(TypeVariableBinding typeVariableBinding3) {
                    if (typeVariableBinding3.rank >= length || !TypeBinding.equalsEquals(typeVariableBindingArr[typeVariableBinding3.rank], typeVariableBinding3)) {
                        return typeVariableBinding3;
                    }
                    TypeVariableBinding typeVariableBinding4 = typeVariableBindingArr2[typeVariableBinding3.rank];
                    return typeVariableBinding3.hasTypeAnnotations() ? environment().createAnnotatedType(typeVariableBinding4, typeVariableBinding3.getTypeAnnotations()) : typeVariableBinding4;
                }
            };
            for (int i2 = 0; i2 < length; i2++) {
                TypeVariableBinding typeVariableBinding3 = typeVariableBindingArr[i2];
                TypeVariableBinding typeVariableBinding4 = typeVariableBindingArr2[i2];
                TypeBinding typeBindingSubstitute = Scope.substitute(substitution2, typeVariableBinding3.superclass);
                ReferenceBinding[] referenceBindingArrSubstitute = Scope.substitute(substitution2, typeVariableBinding3.superInterfaces);
                if (typeVariableBinding3.firstBound != null) {
                    typeVariableBinding4.setFirstBound(TypeBinding.equalsEquals(typeVariableBinding3.firstBound, typeVariableBinding3.superclass) ? typeBindingSubstitute : referenceBindingArrSubstitute[0]);
                }
                if (typeBindingSubstitute.kind() == 68) {
                    typeVariableBinding4.setSuperClass(lookupEnvironment.getResolvedType(TypeConstants.JAVA_LANG_OBJECT, null));
                    typeVariableBinding4.setSuperInterfaces(referenceBindingArrSubstitute);
                } else if (typeBindingSubstitute.isInterface()) {
                    typeVariableBinding4.setSuperClass(lookupEnvironment.getResolvedType(TypeConstants.JAVA_LANG_OBJECT, null));
                    int length2 = referenceBindingArrSubstitute.length;
                    ReferenceBinding[] referenceBindingArr = new ReferenceBinding[length2 + 1];
                    System.arraycopy(referenceBindingArrSubstitute, 0, referenceBindingArr, 1, length2);
                    referenceBindingArr[0] = (ReferenceBinding) typeBindingSubstitute;
                    typeVariableBinding4.setSuperInterfaces(referenceBindingArr);
                } else {
                    typeVariableBinding4.setSuperClass((ReferenceBinding) typeBindingSubstitute);
                    typeVariableBinding4.setSuperInterfaces(referenceBindingArrSubstitute);
                }
            }
            substitution = substitution2;
        }
        if (substitution != null) {
            this.returnType = Scope.substitute(substitution, this.returnType);
            this.parameters = Scope.substitute(substitution, this.parameters);
            this.thrownExceptions = Scope.substitute(substitution, this.thrownExceptions);
            if (this.thrownExceptions == null) {
                this.thrownExceptions = Binding.NO_EXCEPTIONS;
            }
        }
        if ((this.tagBits & 128) != 0) {
            return;
        }
        if ((this.returnType.tagBits & 128) != 0) {
            this.tagBits |= 128;
            return;
        }
        int length3 = this.parameters.length;
        for (int i3 = 0; i3 < length3; i3++) {
            if ((this.parameters[i3].tagBits & 128) != 0) {
                this.tagBits |= 128;
                return;
            }
        }
        int length4 = this.thrownExceptions.length;
        for (int i4 = 0; i4 < length4; i4++) {
            if ((this.thrownExceptions[i4].tagBits & 128) != 0) {
                this.tagBits |= 128;
                return;
            }
        }
    }

    public ParameterizedMethodBinding() {
    }

    public static ParameterizedMethodBinding instantiateGetClass(TypeBinding typeBinding, MethodBinding methodBinding, Scope scope) {
        ParameterizedMethodBinding parameterizedMethodBinding = new ParameterizedMethodBinding();
        parameterizedMethodBinding.modifiers = methodBinding.modifiers;
        parameterizedMethodBinding.selector = methodBinding.selector;
        parameterizedMethodBinding.declaringClass = methodBinding.declaringClass;
        parameterizedMethodBinding.typeVariables = Binding.NO_TYPE_VARIABLES;
        parameterizedMethodBinding.originalMethod = methodBinding;
        parameterizedMethodBinding.parameters = methodBinding.parameters;
        parameterizedMethodBinding.thrownExceptions = methodBinding.thrownExceptions;
        parameterizedMethodBinding.tagBits = methodBinding.tagBits;
        ReferenceBinding javaLangClass = scope.getJavaLangClass();
        LookupEnvironment lookupEnvironmentEnvironment = scope.environment();
        TypeBinding typeBindingConvertToRawType = lookupEnvironmentEnvironment.convertToRawType(typeBinding.erasure(), false);
        if (lookupEnvironmentEnvironment.usesNullTypeAnnotations()) {
            typeBindingConvertToRawType = lookupEnvironmentEnvironment.createAnnotatedType(typeBindingConvertToRawType, new AnnotationBinding[]{lookupEnvironmentEnvironment.getNonNullAnnotation()});
        }
        parameterizedMethodBinding.returnType = lookupEnvironmentEnvironment.createParameterizedType(javaLangClass, new TypeBinding[]{lookupEnvironmentEnvironment.createWildcard(javaLangClass, 0, typeBindingConvertToRawType, null, 1)}, null);
        if (lookupEnvironmentEnvironment.globalOptions.isAnnotationBasedNullAnalysisEnabled) {
            if (lookupEnvironmentEnvironment.usesNullTypeAnnotations()) {
                parameterizedMethodBinding.returnType = lookupEnvironmentEnvironment.createAnnotatedType(parameterizedMethodBinding.returnType, new AnnotationBinding[]{lookupEnvironmentEnvironment.getNonNullAnnotation()});
            } else {
                parameterizedMethodBinding.tagBits |= 72057594037927936L;
            }
        }
        if ((parameterizedMethodBinding.returnType.tagBits & 128) != 0) {
            parameterizedMethodBinding.tagBits |= 128;
        }
        return parameterizedMethodBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public boolean hasSubstitutedParameters() {
        return this.parameters != this.originalMethod.parameters;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public boolean hasSubstitutedReturnType() {
        return this.returnType != this.originalMethod.returnType;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public MethodBinding original() {
        return this.originalMethod.original();
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.MethodBinding
    public MethodBinding shallowOriginal() {
        return this.originalMethod;
    }
}
