package org.eclipse.jdt.internal.compiler.lookup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.QualifiedAllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeParameter;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.env.AccessRestriction;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilation;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.util.HashtableOfObject;

/* JADX INFO: loaded from: classes14.dex */
public class ClassScope extends Scope {
    ArrayList<Object> deferredBoundChecks;
    public TypeDeclaration referenceContext;
    public TypeReference superTypeReference;

    public ClassScope(Scope scope, TypeDeclaration typeDeclaration) {
        super(3, scope);
        this.referenceContext = typeDeclaration;
        this.deferredBoundChecks = null;
    }

    void buildAnonymousTypeBinding(SourceTypeBinding sourceTypeBinding, ReferenceBinding referenceBinding) {
        AbstractMethodDeclaration[] abstractMethodDeclarationArr;
        LocalTypeBinding localTypeBindingBuildLocalType = buildLocalType(sourceTypeBinding, sourceTypeBinding.fPackage);
        localTypeBindingBuildLocalType.modifiers |= 134217728;
        int i = referenceBinding.typeBits;
        if ((i & 4) != 0 && (abstractMethodDeclarationArr = this.referenceContext.methods) != null) {
            for (int i2 = 0; i2 < abstractMethodDeclarationArr.length; i2++) {
                if (CharOperation.equals(TypeConstants.CLOSE, abstractMethodDeclarationArr[i2].selector) && abstractMethodDeclarationArr[i2].arguments == null) {
                    i &= 19;
                    break;
                }
            }
        }
        localTypeBindingBuildLocalType.typeBits = i | localTypeBindingBuildLocalType.typeBits;
        if (referenceBinding.isInterface()) {
            localTypeBindingBuildLocalType.setSuperClass(getJavaLangObject());
            localTypeBindingBuildLocalType.setSuperInterfaces(new ReferenceBinding[]{referenceBinding});
            TypeReference typeReference = this.referenceContext.allocation.type;
            if (typeReference != null) {
                this.referenceContext.superInterfaces = new TypeReference[]{typeReference};
                if ((1073741824 & referenceBinding.tagBits) != 0) {
                    problemReporter().superTypeCannotUseWildcard(localTypeBindingBuildLocalType, typeReference, referenceBinding);
                    localTypeBindingBuildLocalType.tagBits |= 131072;
                    localTypeBindingBuildLocalType.setSuperInterfaces(Binding.NO_SUPERINTERFACES);
                }
            }
        } else {
            localTypeBindingBuildLocalType.setSuperClass(referenceBinding);
            localTypeBindingBuildLocalType.setSuperInterfaces(Binding.NO_SUPERINTERFACES);
            TypeReference typeReference2 = this.referenceContext.allocation.type;
            if (typeReference2 != null) {
                this.referenceContext.superclass = typeReference2;
                if (referenceBinding.erasure().f626id == 41) {
                    problemReporter().cannotExtendEnum(localTypeBindingBuildLocalType, typeReference2, referenceBinding);
                    localTypeBindingBuildLocalType.tagBits |= 131072;
                    localTypeBindingBuildLocalType.setSuperClass(getJavaLangObject());
                } else if (referenceBinding.isFinal()) {
                    problemReporter().anonymousClassCannotExtendFinalClass(typeReference2, referenceBinding);
                    localTypeBindingBuildLocalType.tagBits |= 131072;
                    localTypeBindingBuildLocalType.setSuperClass(getJavaLangObject());
                } else if ((referenceBinding.tagBits & 1073741824) != 0) {
                    problemReporter().superTypeCannotUseWildcard(localTypeBindingBuildLocalType, typeReference2, referenceBinding);
                    localTypeBindingBuildLocalType.tagBits |= 131072;
                    localTypeBindingBuildLocalType.setSuperClass(getJavaLangObject());
                }
            }
        }
        connectMemberTypes();
        buildFieldsAndMethods();
        localTypeBindingBuildLocalType.faultInTypesForFieldsAndMethods();
        localTypeBindingBuildLocalType.verifyMethods(environment().methodVerifier());
    }

    void buildFields() {
        SourceTypeBinding sourceTypeBinding = this.referenceContext.binding;
        if (sourceTypeBinding.areFieldsInitialized()) {
            return;
        }
        if (this.referenceContext.fields == null) {
            sourceTypeBinding.setFields(Binding.NO_FIELDS);
            return;
        }
        FieldDeclaration[] fieldDeclarationArr = this.referenceContext.fields;
        int length = fieldDeclarationArr.length;
        int i = 0;
        for (FieldDeclaration fieldDeclaration : fieldDeclarationArr) {
            int kind = fieldDeclaration.getKind();
            if (kind == 1 || kind == 3) {
                i++;
            }
        }
        FieldBinding[] fieldBindingArr = new FieldBinding[i];
        HashtableOfObject hashtableOfObject = new HashtableOfObject(i);
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            FieldDeclaration fieldDeclaration2 = fieldDeclarationArr[i3];
            if (fieldDeclaration2.getKind() != 2) {
                FieldBinding fieldBinding = new FieldBinding(fieldDeclaration2, null, fieldDeclaration2.modifiers | 33554432, sourceTypeBinding);
                fieldBinding.f627id = i2;
                checkAndSetModifiersForField(fieldBinding, fieldDeclaration2);
                if (hashtableOfObject.containsKey(fieldDeclaration2.f617name)) {
                    FieldBinding fieldBinding2 = (FieldBinding) hashtableOfObject.get(fieldDeclaration2.f617name);
                    if (fieldBinding2 != null) {
                        for (int i4 = 0; i4 < i3; i4++) {
                            FieldDeclaration fieldDeclaration3 = fieldDeclarationArr[i4];
                            if (fieldDeclaration3.binding == fieldBinding2) {
                                problemReporter().duplicateFieldInType(sourceTypeBinding, fieldDeclaration3);
                                break;
                            }
                        }
                    }
                    hashtableOfObject.put(fieldDeclaration2.f617name, null);
                    problemReporter().duplicateFieldInType(sourceTypeBinding, fieldDeclaration2);
                    fieldDeclaration2.binding = null;
                } else {
                    hashtableOfObject.put(fieldDeclaration2.f617name, fieldBinding);
                    fieldBindingArr[i2] = fieldBinding;
                    i2++;
                }
            }
        }
        if (i2 != i) {
            FieldBinding[] fieldBindingArr2 = new FieldBinding[i2];
            System.arraycopy(fieldBindingArr, 0, fieldBindingArr2, 0, i2);
            fieldBindingArr = fieldBindingArr2;
        }
        sourceTypeBinding.tagBits &= -12289;
        sourceTypeBinding.setFields(fieldBindingArr);
    }

    void buildFieldsAndMethods() {
        buildFields();
        buildMethods();
        SourceTypeBinding sourceTypeBinding = this.referenceContext.binding;
        if (!sourceTypeBinding.isPrivate() && (sourceTypeBinding.superclass instanceof SourceTypeBinding) && sourceTypeBinding.superclass.isPrivate()) {
            ((SourceTypeBinding) sourceTypeBinding.superclass).tagIndirectlyAccessibleMembers();
        }
        if (sourceTypeBinding.isMemberType() && !sourceTypeBinding.isLocalType()) {
            ((MemberTypeBinding) sourceTypeBinding).checkSyntheticArgsAndFields();
        }
        for (ReferenceBinding referenceBinding : sourceTypeBinding.memberTypes) {
            ((SourceTypeBinding) referenceBinding).scope.buildFieldsAndMethods();
        }
    }

    private LocalTypeBinding buildLocalType(SourceTypeBinding sourceTypeBinding, PackageBinding packageBinding) {
        this.referenceContext.scope = this;
        this.referenceContext.staticInitializerScope = new MethodScope(this, this.referenceContext, true);
        this.referenceContext.initializerScope = new MethodScope(this, this.referenceContext, false);
        LocalTypeBinding localTypeBinding = new LocalTypeBinding(this, sourceTypeBinding, innermostSwitchCase());
        this.referenceContext.binding = localTypeBinding;
        checkAndSetModifiers();
        buildTypeVariables();
        ReferenceBinding[] referenceBindingArr = Binding.NO_MEMBER_TYPES;
        if (this.referenceContext.memberTypes != null) {
            int length = this.referenceContext.memberTypes.length;
            ReferenceBinding[] referenceBindingArr2 = new ReferenceBinding[length];
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                TypeDeclaration typeDeclaration = this.referenceContext.memberTypes[i2];
                int iKind = TypeDeclaration.kind(typeDeclaration.modifiers);
                if (iKind == 2 || iKind == 4) {
                    problemReporter().illegalLocalTypeDeclaration(typeDeclaration);
                } else {
                    ReferenceBinding referenceBindingEnclosingType = localTypeBinding;
                    while (true) {
                        if (CharOperation.equals(referenceBindingEnclosingType.sourceName, typeDeclaration.f619name)) {
                            problemReporter().typeCollidesWithEnclosingType(typeDeclaration);
                            break;
                        }
                        referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType();
                        if (referenceBindingEnclosingType == null) {
                            int i3 = 0;
                            while (true) {
                                if (i3 < i2) {
                                    if (CharOperation.equals(this.referenceContext.memberTypes[i3].f619name, typeDeclaration.f619name)) {
                                        problemReporter().duplicateNestedType(typeDeclaration);
                                        break;
                                    }
                                    i3++;
                                } else {
                                    LocalTypeBinding localTypeBindingBuildLocalType = new ClassScope(this, this.referenceContext.memberTypes[i2]).buildLocalType(localTypeBinding, packageBinding);
                                    localTypeBindingBuildLocalType.setAsMemberType();
                                    referenceBindingArr2[i] = localTypeBindingBuildLocalType;
                                    i++;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (i != length) {
                referenceBindingArr = new ReferenceBinding[i];
                System.arraycopy(referenceBindingArr2, 0, referenceBindingArr, 0, i);
            } else {
                referenceBindingArr = referenceBindingArr2;
            }
        }
        localTypeBinding.setMemberTypes(referenceBindingArr);
        return localTypeBinding;
    }

    void buildLocalTypeBinding(SourceTypeBinding sourceTypeBinding) {
        LocalTypeBinding localTypeBindingBuildLocalType = buildLocalType(sourceTypeBinding, sourceTypeBinding.fPackage);
        connectTypeHierarchy();
        if (compilerOptions().sourceLevel >= ClassFileConstants.JDK1_5) {
            checkParameterizedTypeBounds();
            checkParameterizedSuperTypeCollisions();
        }
        buildFieldsAndMethods();
        localTypeBindingBuildLocalType.faultInTypesForFieldsAndMethods();
        this.referenceContext.binding.verifyMethods(environment().methodVerifier());
    }

    private void buildMemberTypes(AccessRestriction accessRestriction) {
        SourceTypeBinding sourceTypeBinding = this.referenceContext.binding;
        ReferenceBinding[] referenceBindingArr = Binding.NO_MEMBER_TYPES;
        if (this.referenceContext.memberTypes != null) {
            int length = this.referenceContext.memberTypes.length;
            ReferenceBinding[] referenceBindingArr2 = new ReferenceBinding[length];
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                TypeDeclaration typeDeclaration = this.referenceContext.memberTypes[i2];
                if (environment().isProcessingAnnotations && environment().isMissingType(typeDeclaration.f619name)) {
                    throw new SourceTypeCollisionException();
                }
                int iKind = TypeDeclaration.kind(typeDeclaration.modifiers);
                if ((iKind == 2 || iKind == 4) && sourceTypeBinding.isNestedType() && sourceTypeBinding.isClass() && !sourceTypeBinding.isStatic()) {
                    problemReporter().illegalLocalTypeDeclaration(typeDeclaration);
                } else {
                    ReferenceBinding referenceBindingEnclosingType = sourceTypeBinding;
                    while (true) {
                        if (CharOperation.equals(referenceBindingEnclosingType.sourceName, typeDeclaration.f619name)) {
                            problemReporter().typeCollidesWithEnclosingType(typeDeclaration);
                            break;
                        }
                        referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType();
                        if (referenceBindingEnclosingType == null) {
                            int i3 = 0;
                            while (true) {
                                if (i3 < i2) {
                                    if (CharOperation.equals(this.referenceContext.memberTypes[i3].f619name, typeDeclaration.f619name)) {
                                        problemReporter().duplicateNestedType(typeDeclaration);
                                        break;
                                    }
                                    i3++;
                                } else {
                                    referenceBindingArr2[i] = new ClassScope(this, typeDeclaration).buildType(sourceTypeBinding, sourceTypeBinding.fPackage, accessRestriction);
                                    i++;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (i != length) {
                referenceBindingArr = new ReferenceBinding[i];
                System.arraycopy(referenceBindingArr2, 0, referenceBindingArr, 0, i);
            } else {
                referenceBindingArr = referenceBindingArr2;
            }
        }
        sourceTypeBinding.setMemberTypes(referenceBindingArr);
    }

    void buildMethods() {
        int i;
        boolean z;
        MethodBinding methodBindingCreateMethod;
        MethodBinding methodBindingCreateMethod2;
        SourceTypeBinding sourceTypeBinding = this.referenceContext.binding;
        if (sourceTypeBinding.areMethodsInitialized()) {
            return;
        }
        boolean z2 = TypeDeclaration.kind(this.referenceContext.modifiers) == 3;
        if (this.referenceContext.methods == null && !z2) {
            this.referenceContext.binding.setMethods(Binding.NO_METHODS);
            return;
        }
        AbstractMethodDeclaration[] abstractMethodDeclarationArr = this.referenceContext.methods;
        int length = abstractMethodDeclarationArr == null ? 0 : abstractMethodDeclarationArr.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                i = -1;
                break;
            } else {
                if (abstractMethodDeclarationArr[i2].isClinit()) {
                    i = i2;
                    break;
                }
                i2++;
            }
        }
        int i3 = z2 ? 2 : 0;
        int i4 = (i == -1 ? length : length - 1) + i3;
        MethodBinding[] methodBindingArr = new MethodBinding[i4];
        if (z2) {
            methodBindingArr[0] = sourceTypeBinding.addSyntheticEnumMethod(TypeConstants.VALUES);
            methodBindingArr[1] = sourceTypeBinding.addSyntheticEnumMethod(TypeConstants.VALUEOF);
        }
        if (sourceTypeBinding.isAbstract()) {
            z = false;
            for (int i5 = 0; i5 < length; i5++) {
                if (i5 != i && (methodBindingCreateMethod2 = new MethodScope(this, abstractMethodDeclarationArr[i5], false).createMethod(abstractMethodDeclarationArr[i5])) != null) {
                    int i6 = i3 + 1;
                    methodBindingArr[i3] = methodBindingCreateMethod2;
                    z = z || methodBindingCreateMethod2.isNative();
                    i3 = i6;
                }
            }
        } else {
            z = false;
            boolean z3 = false;
            for (int i7 = 0; i7 < length; i7++) {
                if (i7 != i && (methodBindingCreateMethod = new MethodScope(this, abstractMethodDeclarationArr[i7], false).createMethod(abstractMethodDeclarationArr[i7])) != null) {
                    int i8 = i3 + 1;
                    methodBindingArr[i3] = methodBindingCreateMethod;
                    z3 = z3 || methodBindingCreateMethod.isAbstract();
                    z = z || methodBindingCreateMethod.isNative();
                    i3 = i8;
                }
            }
            if (z3) {
                problemReporter().abstractMethodInConcreteClass(sourceTypeBinding);
            }
        }
        if (i3 != i4) {
            MethodBinding[] methodBindingArr2 = new MethodBinding[i3];
            System.arraycopy(methodBindingArr, 0, methodBindingArr2, 0, i3);
            methodBindingArr = methodBindingArr2;
        }
        sourceTypeBinding.tagBits &= -49153;
        sourceTypeBinding.setMethods(methodBindingArr);
        if (z) {
            for (MethodBinding methodBinding : methodBindingArr) {
                methodBinding.modifiers = 134217728 | methodBinding.modifiers;
            }
            FieldBinding[] fieldBindingArrUnResolvedFields = sourceTypeBinding.unResolvedFields();
            for (FieldBinding fieldBinding : fieldBindingArrUnResolvedFields) {
                fieldBinding.modifiers |= 134217728;
            }
        }
        if (z2 && compilerOptions().isAnnotationBasedNullAnalysisEnabled) {
            LookupEnvironment lookupEnvironmentEnvironment = environment();
            ((SyntheticMethodBinding) methodBindingArr[0]).markNonNull(lookupEnvironmentEnvironment);
            ((SyntheticMethodBinding) methodBindingArr[1]).markNonNull(lookupEnvironmentEnvironment);
        }
    }

    SourceTypeBinding buildType(SourceTypeBinding sourceTypeBinding, PackageBinding packageBinding, AccessRestriction accessRestriction) {
        this.referenceContext.scope = this;
        this.referenceContext.staticInitializerScope = new MethodScope(this, this.referenceContext, true);
        this.referenceContext.initializerScope = new MethodScope(this, this.referenceContext, false);
        if (sourceTypeBinding == null) {
            this.referenceContext.binding = new SourceTypeBinding(CharOperation.arrayConcat(packageBinding.compoundName, this.referenceContext.f619name), packageBinding, this);
        } else {
            char[][] cArrDeepCopy = CharOperation.deepCopy(sourceTypeBinding.compoundName);
            cArrDeepCopy[cArrDeepCopy.length - 1] = CharOperation.concat(cArrDeepCopy[cArrDeepCopy.length - 1], this.referenceContext.f619name, '$');
            ReferenceBinding type0 = packageBinding.getType0(cArrDeepCopy[cArrDeepCopy.length - 1]);
            if (type0 != null && !(type0 instanceof UnresolvedReferenceBinding)) {
                this.parent.problemReporter().duplicateNestedType(this.referenceContext);
            }
            this.referenceContext.binding = new MemberTypeBinding(cArrDeepCopy, this, sourceTypeBinding);
        }
        SourceTypeBinding sourceTypeBinding2 = this.referenceContext.binding;
        environment().setAccessRestriction(sourceTypeBinding2, accessRestriction);
        TypeParameter[] typeParameterArr = this.referenceContext.typeParameters;
        sourceTypeBinding2.typeVariables = (typeParameterArr == null || typeParameterArr.length == 0) ? Binding.NO_TYPE_VARIABLES : null;
        sourceTypeBinding2.fPackage.addType(sourceTypeBinding2);
        checkAndSetModifiers();
        buildTypeVariables();
        buildMemberTypes(accessRestriction);
        return sourceTypeBinding2;
    }

    private void buildTypeVariables() {
        SourceTypeBinding sourceTypeBinding = this.referenceContext.binding;
        TypeParameter[] typeParameterArr = this.referenceContext.typeParameters;
        if (typeParameterArr == null || typeParameterArr.length == 0) {
            sourceTypeBinding.setTypeVariables(Binding.NO_TYPE_VARIABLES);
            return;
        }
        sourceTypeBinding.setTypeVariables(Binding.NO_TYPE_VARIABLES);
        if (sourceTypeBinding.f626id == 1) {
            problemReporter().objectCannotBeGeneric(this.referenceContext);
        } else {
            sourceTypeBinding.setTypeVariables(createTypeVariables(typeParameterArr, sourceTypeBinding));
            sourceTypeBinding.modifiers |= 1073741824;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Scope
    void resolveTypeParameter(TypeParameter typeParameter) {
        typeParameter.resolve(this);
    }

    /* JADX WARN: Code duplicated, block: B:111:0x0191 A[EDGE_INSN: B:111:0x0191->B:142:0x01d7 BREAK  A[LOOP:2: B:134:0x01c2->B:154:0x01f6]] */
    /* JADX WARN: Code duplicated, block: B:17:0x0048  */
    /* JADX WARN: Code duplicated, block: B:180:0x0254 A[PHI: r1
      0x0254: PHI (r1v28 int) = (r1v22 int), (r1v24 int), (r1v30 int) binds: [B:194:0x027a, B:189:0x0271, B:179:0x0252] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:42:0x0098 A[PHI: r1
      0x0098: PHI (r1v8 int) = (r1v3 int), (r1v5 int), (r1v2 int), (r1v10 int) binds: [B:71:0x00f5, B:61:0x00d8, B:53:0x00c3, B:41:0x0096] A[DONT_GENERATE, DONT_INLINE]] */
    private void checkAndSetModifiers() {
        SourceTypeBinding sourceTypeBinding = this.referenceContext.binding;
        int i = sourceTypeBinding.modifiers;
        if ((4194304 & i) != 0) {
            problemReporter().duplicateModifierForType(sourceTypeBinding);
        }
        ReferenceBinding referenceBindingEnclosingType = sourceTypeBinding.enclosingType();
        boolean zIsMemberType = sourceTypeBinding.isMemberType();
        int i2 = 0;
        if (zIsMemberType) {
            i |= referenceBindingEnclosingType.modifiers & CompilerOptions.NullAnnotationInferenceConflict;
            if (referenceBindingEnclosingType.isInterface()) {
                i |= 1;
            }
            if (sourceTypeBinding.isEnum()) {
                if (referenceBindingEnclosingType.isStatic()) {
                    i |= 8;
                } else {
                    problemReporter().nonStaticContextForEnumMemberType(sourceTypeBinding);
                }
            } else if (sourceTypeBinding.isInterface()) {
                i |= 8;
            }
        } else if (sourceTypeBinding.isLocalType()) {
            if (sourceTypeBinding.isEnum()) {
                problemReporter().illegalLocalTypeDeclaration(this.referenceContext);
                sourceTypeBinding.modifiers = 0;
                return;
            }
            if (sourceTypeBinding.isAnonymousType()) {
                i = this.referenceContext.allocation.type == null ? i | 16400 : i | 16;
            }
            Scope scope = this;
            do {
                int i3 = scope.kind;
                if (i3 == 2) {
                    MethodScope methodScopeNamedMethodScope = (MethodScope) scope;
                    if (methodScopeNamedMethodScope.isLambdaScope()) {
                        methodScopeNamedMethodScope = methodScopeNamedMethodScope.namedMethodScope();
                    }
                    if (methodScopeNamedMethodScope.isInsideInitializer()) {
                        SourceTypeBinding sourceTypeBinding2 = ((TypeDeclaration) methodScopeNamedMethodScope.referenceContext).binding;
                        if (methodScopeNamedMethodScope.initializedField != null) {
                            if (methodScopeNamedMethodScope.initializedField.isViewedAsDeprecated() && !sourceTypeBinding.isDeprecated()) {
                                i |= 2097152;
                            }
                        } else {
                            if (sourceTypeBinding2.isStrictfp()) {
                                i |= 2048;
                            }
                            if (sourceTypeBinding2.isViewedAsDeprecated() && !sourceTypeBinding.isDeprecated()) {
                                i |= 2097152;
                            }
                        }
                    } else {
                        MethodBinding methodBinding = ((AbstractMethodDeclaration) methodScopeNamedMethodScope.referenceContext).binding;
                        if (methodBinding != null) {
                            if (methodBinding.isStrictfp()) {
                                i |= 2048;
                            }
                            if (methodBinding.isViewedAsDeprecated() && !sourceTypeBinding.isDeprecated()) {
                                i |= 2097152;
                            }
                        }
                    }
                } else if (i3 == 3) {
                    if (referenceBindingEnclosingType.isStrictfp()) {
                        i |= 2048;
                    }
                    if (referenceBindingEnclosingType.isViewedAsDeprecated() && !sourceTypeBinding.isDeprecated()) {
                        i |= 2097152;
                    }
                }
                scope = scope.parent;
            } while (scope != null);
        }
        int i4 = 65535 & i;
        if ((i & 512) != 0) {
            if (zIsMemberType) {
                if ((53744 & i) != 0) {
                    if ((i & 8192) != 0) {
                        problemReporter().illegalModifierForAnnotationMemberType(sourceTypeBinding);
                    } else {
                        problemReporter().illegalModifierForMemberInterface(sourceTypeBinding);
                    }
                }
            } else if ((53758 & i) != 0) {
                if ((i & 8192) != 0) {
                    problemReporter().illegalModifierForAnnotationType(sourceTypeBinding);
                } else {
                    problemReporter().illegalModifierForInterface(sourceTypeBinding);
                }
            }
            if (sourceTypeBinding.sourceName == TypeConstants.PACKAGE_INFO_NAME && compilerOptions().targetJDK > ClassFileConstants.JDK1_5) {
                i |= 4096;
            }
            i |= 1024;
        } else if ((i & 16384) != 0) {
            if (zIsMemberType) {
                if ((47088 & i) != 0) {
                    problemReporter().illegalModifierForMemberEnum(sourceTypeBinding);
                    i4 = i & 64511;
                    i &= -1025;
                }
            } else if (!sourceTypeBinding.isLocalType() && (47102 & i) != 0) {
                problemReporter().illegalModifierForEnum(sourceTypeBinding);
            }
            if (!sourceTypeBinding.isAnonymousType()) {
                if ((this.referenceContext.bits & 2048) != 0) {
                    i |= 1024;
                    break;
                }
                TypeDeclaration typeDeclaration = this.referenceContext;
                FieldDeclaration[] fieldDeclarationArr = typeDeclaration.fields;
                int length = fieldDeclarationArr == null ? 0 : fieldDeclarationArr.length;
                if (length != 0) {
                    AbstractMethodDeclaration[] abstractMethodDeclarationArr = typeDeclaration.methods;
                    int length2 = abstractMethodDeclarationArr == null ? 0 : abstractMethodDeclarationArr.length;
                    boolean zIsAbstract = typeDeclaration.superInterfaces != null;
                    for (int i5 = 0; i5 < length2 && !zIsAbstract; i5++) {
                        zIsAbstract = abstractMethodDeclarationArr[i5].isAbstract();
                    }
                    if (zIsAbstract) {
                        int i6 = 0;
                        boolean z = false;
                        while (true) {
                            if (i6 >= length) {
                                if (!z) {
                                    break;
                                }
                                i |= 1024;
                                break;
                            } else {
                                FieldDeclaration fieldDeclaration = fieldDeclarationArr[i6];
                                if (fieldDeclaration.getKind() == 3) {
                                    if (!(fieldDeclaration.initialization instanceof QualifiedAllocationExpression)) {
                                        break;
                                    } else {
                                        z = true;
                                    }
                                }
                                i6++;
                            }
                        }
                    }
                }
                FieldDeclaration[] fieldDeclarationArr2 = this.referenceContext.fields;
                if (fieldDeclarationArr2 == null) {
                    i |= 16;
                    break;
                }
                int length3 = fieldDeclarationArr2.length;
                while (true) {
                    if (i2 >= length3) {
                        i |= 16;
                        break;
                    }
                    FieldDeclaration fieldDeclaration2 = fieldDeclarationArr2[i2];
                    if (fieldDeclaration2.getKind() == 3 && (fieldDeclaration2.initialization instanceof QualifiedAllocationExpression)) {
                        break;
                    } else {
                        i2++;
                    }
                }
            }
        } else {
            if (zIsMemberType) {
                if ((62432 & i) != 0) {
                    problemReporter().illegalModifierForMemberClass(sourceTypeBinding);
                }
            } else if (sourceTypeBinding.isLocalType()) {
                if ((62447 & i) != 0) {
                    problemReporter().illegalModifierForLocalClass(sourceTypeBinding);
                }
            } else if ((62446 & i) != 0) {
                problemReporter().illegalModifierForClass(sourceTypeBinding);
            }
            if ((i & 1040) == 1040) {
                problemReporter().illegalModifierCombinationFinalAbstractForClass(sourceTypeBinding);
            }
        }
        if (zIsMemberType) {
            if (!referenceBindingEnclosingType.isInterface()) {
                int i7 = i4 & 7;
                if ((i7 & (i7 - 1)) > 1) {
                    problemReporter().illegalVisibilityModifierCombinationForMemberType(sourceTypeBinding);
                    if ((i4 & 1) != 0) {
                        if ((i4 & 4) != 0) {
                            i &= -5;
                        }
                        if ((i4 & 2) != 0) {
                            i &= -3;
                        }
                    } else if ((i4 & 4) != 0 && (i4 & 2) != 0) {
                        i &= -3;
                    }
                }
            } else if ((i4 & 6) != 0) {
                problemReporter().illegalVisibilityModifierForInterfaceMemberType(sourceTypeBinding);
                if ((i4 & 4) != 0) {
                    i &= -5;
                }
                if ((i4 & 2) != 0) {
                    i &= -3;
                }
            }
            if ((i4 & 8) == 0) {
                if (referenceBindingEnclosingType.isInterface()) {
                    i |= 8;
                }
            } else if (!referenceBindingEnclosingType.isStatic()) {
                problemReporter().illegalStaticModifierForMemberType(sourceTypeBinding);
            }
        }
        sourceTypeBinding.modifiers = i;
    }

    /* JADX WARN: Code duplicated, block: B:40:0x008a A[PHI: r2
      0x008a: PHI (r2v11 int) = (r2v7 int), (r2v13 int) binds: [B:39:0x0088, B:34:0x007f] A[DONT_GENERATE, DONT_INLINE]] */
    private void checkAndSetModifiersForField(FieldBinding fieldBinding, FieldDeclaration fieldDeclaration) {
        int i;
        int i2 = fieldBinding.modifiers;
        ReferenceBinding referenceBinding = fieldBinding.declaringClass;
        if ((4194304 & i2) != 0) {
            problemReporter().duplicateModifierForField(referenceBinding, fieldDeclaration);
        }
        if (referenceBinding.isInterface()) {
            int i3 = i2 | 25;
            if ((65535 & i3) != 25) {
                if ((referenceBinding.modifiers & 8192) != 0) {
                    problemReporter().illegalModifierForAnnotationField(fieldDeclaration);
                } else {
                    problemReporter().illegalModifierForInterfaceField(fieldDeclaration);
                }
            }
            fieldBinding.modifiers = i3;
            return;
        }
        if (fieldDeclaration.getKind() == 3) {
            if ((i2 & 65535) != 0) {
                problemReporter().illegalModifierForEnumConstant(referenceBinding, fieldDeclaration);
            }
            fieldBinding.modifiers |= 134234137;
            return;
        }
        if ((65312 & i2) != 0) {
            problemReporter().illegalModifierForField(referenceBinding, fieldDeclaration);
            i = (-65313) & i2;
        } else {
            i = i2;
        }
        int i4 = i2 & 7;
        if ((i4 & (i4 - 1)) > 1) {
            problemReporter().illegalVisibilityModifierCombinationForField(referenceBinding, fieldDeclaration);
            if ((i2 & 1) != 0) {
                if ((i2 & 4) != 0) {
                    i &= -5;
                }
                if ((i2 & 2) != 0) {
                    i &= -3;
                }
            } else if ((i2 & 4) != 0 && (i2 & 2) != 0) {
                i &= -3;
            }
        }
        if ((i2 & 80) == 80) {
            problemReporter().illegalModifierCombinationFinalVolatileForField(referenceBinding, fieldDeclaration);
        }
        if (fieldDeclaration.initialization == null && (i & 16) != 0) {
            i |= 67108864;
        }
        fieldBinding.modifiers = i;
    }

    public void checkParameterizedSuperTypeCollisions() {
        TypeReference[] typeReferenceArr;
        ASTNode aSTNode;
        SourceTypeBinding sourceTypeBinding = this.referenceContext.binding;
        TypeBinding[] typeBindingArr = sourceTypeBinding.superInterfaces;
        Map map = new HashMap(2);
        TypeBinding typeBinding = sourceTypeBinding.isInterface() ? null : sourceTypeBinding.superclass;
        int length = typeBindingArr.length;
        for (int i = 0; i < length; i++) {
            TypeBinding typeBinding2 = typeBindingArr[i];
            if (typeBinding2 != null && (typeBinding == null || !hasErasedCandidatesCollisions(typeBinding, typeBinding2, map, sourceTypeBinding, this.referenceContext))) {
                for (int i2 = 0; i2 < i; i2++) {
                    TypeBinding typeBinding3 = typeBindingArr[i2];
                    if (typeBinding3 != null && hasErasedCandidatesCollisions(typeBinding2, typeBinding3, map, sourceTypeBinding, this.referenceContext)) {
                        break;
                    }
                }
            }
        }
        TypeParameter[] typeParameterArr = this.referenceContext.typeParameters;
        int length2 = typeParameterArr == null ? 0 : typeParameterArr.length;
        for (int i3 = 0; i3 < length2; i3++) {
            TypeParameter typeParameter = typeParameterArr[i3];
            TypeVariableBinding typeVariableBinding = typeParameter.binding;
            if (typeVariableBinding != null && typeVariableBinding.isValidBinding() && (typeReferenceArr = typeParameter.bounds) != null) {
                boolean zEqualsEquals = TypeBinding.equalsEquals(typeVariableBinding.firstBound, typeVariableBinding.superclass);
                int length3 = typeReferenceArr.length;
                int i4 = 0;
                while (i4 < length3) {
                    TypeReference typeReference = typeReferenceArr[i4];
                    TypeBinding typeBinding4 = typeReference.resolvedType;
                    if (typeBinding4 == null || !typeBinding4.isValidBinding()) {
                        i4 = i4;
                    } else {
                        if (zEqualsEquals) {
                            aSTNode = typeReference;
                            if (hasErasedCandidatesCollisions(typeBinding4, typeVariableBinding.superclass, map, typeVariableBinding, aSTNode)) {
                                break;
                            } else {
                                break;
                            }
                        }
                        aSTNode = typeReference;
                        int length4 = typeVariableBinding.superInterfaces.length;
                        while (true) {
                            int i5 = length4 - 1;
                            if (i5 < 0) {
                                break;
                            } else if (hasErasedCandidatesCollisions(typeBinding4, typeVariableBinding.superInterfaces[i5], map, typeVariableBinding, aSTNode)) {
                                break;
                            } else {
                                length4 = i5;
                            }
                        }
                    }
                    i4++;
                }
            }
        }
        ReferenceBinding[] referenceBindingArr = this.referenceContext.binding.memberTypes;
        if (referenceBindingArr == null || referenceBindingArr == Binding.NO_MEMBER_TYPES) {
            return;
        }
        for (ReferenceBinding referenceBinding : referenceBindingArr) {
            ((SourceTypeBinding) referenceBinding).scope.checkParameterizedSuperTypeCollisions();
        }
    }

    private void checkForInheritedMemberTypes(SourceTypeBinding sourceTypeBinding) {
        ReferenceBinding[] referenceBindingArr;
        ReferenceBinding[] referenceBindingArr2;
        ReferenceBinding[] referenceBindingArr3 = null;
        ReferenceBinding referenceBindingSuperclass = sourceTypeBinding;
        int length = 0;
        while (!referenceBindingSuperclass.hasMemberTypes()) {
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
                                } else {
                                    i2++;
                                }
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
            if (referenceBindingSuperclass == null || (referenceBindingSuperclass.tagBits & 65536) != 0) {
                if (referenceBindingArr3 != null) {
                    boolean z = false;
                    for (int i3 = 0; i3 < length; i3++) {
                        ReferenceBinding referenceBinding2 = referenceBindingArr3[i3];
                        if ((referenceBinding2.tagBits & 65536) == 0) {
                            if (referenceBinding2.hasMemberTypes()) {
                                return;
                            }
                            ReferenceBinding[] referenceBindingArrSuperInterfaces2 = referenceBinding2.superInterfaces();
                            if (referenceBindingArrSuperInterfaces2 == null || referenceBindingArrSuperInterfaces2 == Binding.NO_SUPERINTERFACES) {
                                z = true;
                            } else {
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
                                            } else {
                                                i5++;
                                            }
                                        } else {
                                            referenceBindingArr[i4] = referenceBinding3;
                                            i4++;
                                            break;
                                        }
                                    }
                                }
                                z = true;
                                referenceBindingArr3 = referenceBindingArr;
                                length = i4;
                            }
                        }
                    }
                    if (z) {
                        for (int i6 = 0; i6 < length; i6++) {
                            referenceBindingArr3[i6].tagBits |= 65536;
                        }
                    }
                }
                ReferenceBinding referenceBindingSuperclass2 = sourceTypeBinding;
                do {
                    referenceBindingSuperclass2.tagBits |= 65536;
                    referenceBindingSuperclass2 = referenceBindingSuperclass2.superclass();
                    if (referenceBindingSuperclass2 == null) {
                        return;
                    }
                } while ((referenceBindingSuperclass2.tagBits & 65536) == 0);
                return;
            }
        }
    }

    public void checkParameterizedTypeBounds() {
        ArrayList<Object> arrayList = this.deferredBoundChecks;
        int size = arrayList == null ? 0 : arrayList.size();
        for (int i = 0; i < size; i++) {
            Object obj = this.deferredBoundChecks.get(i);
            if (obj instanceof TypeReference) {
                ((TypeReference) obj).checkBounds(this);
            } else if (obj instanceof Runnable) {
                ((Runnable) obj).run();
            }
        }
        this.deferredBoundChecks = null;
        ReferenceBinding[] referenceBindingArr = this.referenceContext.binding.memberTypes;
        if (referenceBindingArr == null || referenceBindingArr == Binding.NO_MEMBER_TYPES) {
            return;
        }
        for (ReferenceBinding referenceBinding : referenceBindingArr) {
            ((SourceTypeBinding) referenceBinding).scope.checkParameterizedTypeBounds();
        }
    }

    private void connectMemberTypes() {
        ReferenceBinding[] referenceBindingArr = this.referenceContext.binding.memberTypes;
        if (referenceBindingArr == null || referenceBindingArr == Binding.NO_MEMBER_TYPES) {
            return;
        }
        for (ReferenceBinding referenceBinding : referenceBindingArr) {
            ((SourceTypeBinding) referenceBinding).scope.connectTypeHierarchy();
        }
    }

    private boolean connectSuperclass() {
        SourceTypeBinding sourceTypeBinding = this.referenceContext.binding;
        if (sourceTypeBinding.f626id == 1) {
            sourceTypeBinding.setSuperClass(null);
            sourceTypeBinding.setSuperInterfaces(Binding.NO_SUPERINTERFACES);
            if (!sourceTypeBinding.isClass()) {
                problemReporter().objectMustBeClass(sourceTypeBinding);
            }
            if (this.referenceContext.superclass != null || (this.referenceContext.superInterfaces != null && this.referenceContext.superInterfaces.length > 0)) {
                problemReporter().objectCannotHaveSuperTypes(sourceTypeBinding);
            }
            return true;
        }
        if (this.referenceContext.superclass == null) {
            if (sourceTypeBinding.isEnum() && compilerOptions().sourceLevel >= ClassFileConstants.JDK1_5) {
                return connectEnumSuperclass();
            }
            sourceTypeBinding.setSuperClass(getJavaLangObject());
            return !detectHierarchyCycle(sourceTypeBinding, sourceTypeBinding.superclass, null);
        }
        TypeReference typeReference = this.referenceContext.superclass;
        ReferenceBinding referenceBindingFindSupertype = findSupertype(typeReference);
        if (referenceBindingFindSupertype != null) {
            if (!referenceBindingFindSupertype.isClass() && (referenceBindingFindSupertype.tagBits & 128) == 0) {
                problemReporter().superclassMustBeAClass(sourceTypeBinding, typeReference, referenceBindingFindSupertype);
            } else if (referenceBindingFindSupertype.isFinal()) {
                problemReporter().classExtendFinalClass(sourceTypeBinding, typeReference, referenceBindingFindSupertype);
            } else if ((referenceBindingFindSupertype.tagBits & 1073741824) != 0) {
                problemReporter().superTypeCannotUseWildcard(sourceTypeBinding, typeReference, referenceBindingFindSupertype);
            } else if (referenceBindingFindSupertype.erasure().f626id == 41) {
                problemReporter().cannotExtendEnum(sourceTypeBinding, typeReference, referenceBindingFindSupertype);
            } else {
                if ((referenceBindingFindSupertype.tagBits & 131072) != 0 || !typeReference.resolvedType.isValidBinding()) {
                    sourceTypeBinding.setSuperClass(referenceBindingFindSupertype);
                    sourceTypeBinding.tagBits |= 131072;
                    return typeReference.resolvedType.isValidBinding();
                }
                sourceTypeBinding.setSuperClass(referenceBindingFindSupertype);
                sourceTypeBinding.typeBits |= referenceBindingFindSupertype.typeBits & 19;
                if ((sourceTypeBinding.typeBits & 3) != 0) {
                    sourceTypeBinding.typeBits |= sourceTypeBinding.applyCloseableClassWhitelists();
                }
                return true;
            }
        }
        sourceTypeBinding.tagBits |= 131072;
        sourceTypeBinding.setSuperClass(getJavaLangObject());
        if ((sourceTypeBinding.superclass.tagBits & 256) != 0) {
            return false;
        }
        detectHierarchyCycle(sourceTypeBinding, sourceTypeBinding.superclass, null);
        return false;
    }

    private boolean connectEnumSuperclass() {
        SourceTypeBinding sourceTypeBinding = this.referenceContext.binding;
        ReferenceBinding javaLangEnum = getJavaLangEnum();
        if ((javaLangEnum.tagBits & 128) != 0) {
            sourceTypeBinding.tagBits |= 131072;
            sourceTypeBinding.setSuperClass(javaLangEnum);
            return false;
        }
        boolean zDetectHierarchyCycle = detectHierarchyCycle(sourceTypeBinding, javaLangEnum, null);
        TypeVariableBinding[] typeVariableBindingArrTypeVariables = javaLangEnum.typeVariables();
        if (typeVariableBindingArrTypeVariables == Binding.NO_TYPE_VARIABLES) {
            problemReporter().nonGenericTypeCannotBeParameterized(0, null, javaLangEnum, new TypeBinding[]{sourceTypeBinding});
            return false;
        }
        if (1 != typeVariableBindingArrTypeVariables.length) {
            problemReporter().incorrectArityForParameterizedType(null, javaLangEnum, new TypeBinding[]{sourceTypeBinding});
            return false;
        }
        ParameterizedTypeBinding parameterizedTypeBindingCreateParameterizedType = environment().createParameterizedType(javaLangEnum, new TypeBinding[]{environment().convertToRawType(sourceTypeBinding, false)}, null);
        sourceTypeBinding.tagBits = (131072 & parameterizedTypeBindingCreateParameterizedType.tagBits) | sourceTypeBinding.tagBits;
        sourceTypeBinding.setSuperClass(parameterizedTypeBindingCreateParameterizedType);
        if (!typeVariableBindingArrTypeVariables[0].boundCheck(parameterizedTypeBindingCreateParameterizedType, sourceTypeBinding, this, null).isOKbyJLS()) {
            problemReporter().typeMismatchError(javaLangEnum, typeVariableBindingArrTypeVariables[0], sourceTypeBinding, (ASTNode) null);
        }
        return !zDetectHierarchyCycle;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v3 */
    private boolean connectSuperInterfaces() {
        ReferenceBinding[] referenceBindingArr;
        ?? r3;
        SourceTypeBinding sourceTypeBinding = this.referenceContext.binding;
        sourceTypeBinding.setSuperInterfaces(Binding.NO_SUPERINTERFACES);
        ?? r4 = 1;
        int i = 0;
        if (this.referenceContext.superInterfaces == null) {
            if (!sourceTypeBinding.isAnnotationType() || compilerOptions().sourceLevel < ClassFileConstants.JDK1_5) {
                return true;
            }
            ReferenceBinding javaLangAnnotationAnnotation = getJavaLangAnnotationAnnotation();
            boolean zDetectHierarchyCycle = detectHierarchyCycle(sourceTypeBinding, javaLangAnnotationAnnotation, null);
            sourceTypeBinding.setSuperInterfaces(new ReferenceBinding[]{javaLangAnnotationAnnotation});
            return !zDetectHierarchyCycle;
        }
        if (sourceTypeBinding.f626id == 1) {
            return true;
        }
        int length = this.referenceContext.superInterfaces.length;
        ReferenceBinding[] referenceBindingArr2 = new ReferenceBinding[length];
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            TypeReference typeReference = this.referenceContext.superInterfaces[i2];
            ReferenceBinding referenceBindingFindSupertype = findSupertype(typeReference);
            if (referenceBindingFindSupertype != null) {
                int i4 = i;
                while (true) {
                    if (i4 < i2) {
                        referenceBindingArr = referenceBindingArr2;
                        if (TypeBinding.equalsEquals(referenceBindingArr[i4], referenceBindingFindSupertype)) {
                            problemReporter().duplicateSuperinterface(sourceTypeBinding, typeReference, referenceBindingFindSupertype);
                            sourceTypeBinding.tagBits |= 131072;
                        } else {
                            i4++;
                            referenceBindingArr2 = referenceBindingArr;
                        }
                    } else {
                        referenceBindingArr = referenceBindingArr2;
                        if (!referenceBindingFindSupertype.isInterface() && (referenceBindingFindSupertype.tagBits & 128) == 0) {
                            problemReporter().superinterfaceMustBeAnInterface(sourceTypeBinding, typeReference, referenceBindingFindSupertype);
                            sourceTypeBinding.tagBits |= 131072;
                        } else {
                            if (referenceBindingFindSupertype.isAnnotationType()) {
                                problemReporter().annotationTypeUsedAsSuperinterface(sourceTypeBinding, typeReference, referenceBindingFindSupertype);
                            }
                            if ((referenceBindingFindSupertype.tagBits & 1073741824) != 0) {
                                problemReporter().superTypeCannotUseWildcard(sourceTypeBinding, typeReference, referenceBindingFindSupertype);
                                sourceTypeBinding.tagBits |= 131072;
                            } else {
                                if ((referenceBindingFindSupertype.tagBits & 131072) != 0 || !typeReference.resolvedType.isValidBinding()) {
                                    r3 = r4;
                                    sourceTypeBinding.tagBits |= 131072;
                                    r3 = (r4 == true ? 1 : 0) & (typeReference.resolvedType.isValidBinding() ? 1 : 0);
                                }
                                r3 = r4;
                                sourceTypeBinding.typeBits |= referenceBindingFindSupertype.typeBits & 19;
                                if ((sourceTypeBinding.typeBits & 3) != 0) {
                                    sourceTypeBinding.typeBits |= sourceTypeBinding.applyCloseableInterfaceWhitelists();
                                }
                                referenceBindingArr[i3] = referenceBindingFindSupertype;
                                i3++;
                                break;
                            }
                        }
                    }
                    r3 = 0;
                    break;
                }
            } else {
                sourceTypeBinding.tagBits |= 131072;
                r3 = i;
                referenceBindingArr = referenceBindingArr2;
            }
            i2++;
            referenceBindingArr2 = referenceBindingArr;
            i = 0;
            r4 = r3;
        }
        if (i3 > 0) {
            if (i3 != length) {
                ReferenceBinding[] referenceBindingArr3 = new ReferenceBinding[i3];
                System.arraycopy(referenceBindingArr2, i, referenceBindingArr3, i, i3);
                referenceBindingArr2 = referenceBindingArr3;
            }
            sourceTypeBinding.setSuperInterfaces(referenceBindingArr2);
        }
        return r4;
    }

    void connectTypeHierarchy() {
        SourceTypeBinding sourceTypeBinding = this.referenceContext.binding;
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        boolean z = compilationUnitScope.connectingHierarchy;
        compilationUnitScope.connectingHierarchy = true;
        try {
            if ((sourceTypeBinding.tagBits & 256) == 0) {
                sourceTypeBinding.tagBits |= 256;
                environment().typesBeingConnected.add(sourceTypeBinding);
                boolean zConnectSuperclass = connectSuperclass() & connectSuperInterfaces();
                environment().typesBeingConnected.remove(sourceTypeBinding);
                sourceTypeBinding.tagBits |= 512;
                boolean zConnectTypeVariables = zConnectSuperclass & connectTypeVariables(this.referenceContext.typeParameters, false);
                sourceTypeBinding.tagBits |= 262144;
                if (zConnectTypeVariables && sourceTypeBinding.isHierarchyInconsistent()) {
                    problemReporter().hierarchyHasProblems(sourceTypeBinding);
                }
            }
            connectMemberTypes();
            compilationUnitScope.connectingHierarchy = z;
            LookupEnvironment lookupEnvironmentEnvironment = environment();
            try {
                try {
                    lookupEnvironmentEnvironment.missingClassFileLocation = this.referenceContext;
                    checkForInheritedMemberTypes(sourceTypeBinding);
                    lookupEnvironmentEnvironment.missingClassFileLocation = null;
                } catch (Throwable th) {
                    lookupEnvironmentEnvironment.missingClassFileLocation = null;
                    throw th;
                }
            } catch (AbortCompilation e) {
                e.updateContext(this.referenceContext, referenceCompilationUnit().compilationResult);
                throw e;
            }
        } catch (Throwable th2) {
            compilationUnitScope.connectingHierarchy = z;
            throw th2;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Scope
    public boolean deferCheck(Runnable runnable) {
        if (!compilationUnitScope().connectingHierarchy) {
            return false;
        }
        if (this.deferredBoundChecks == null) {
            this.deferredBoundChecks = new ArrayList<>();
        }
        this.deferredBoundChecks.add(runnable);
        return true;
    }

    private void connectTypeHierarchyWithoutMembers() {
        if (this.parent instanceof CompilationUnitScope) {
            if (((CompilationUnitScope) this.parent).imports == null) {
                ((CompilationUnitScope) this.parent).checkAndSetImports();
            }
        } else if (this.parent instanceof ClassScope) {
            ((ClassScope) this.parent).connectTypeHierarchyWithoutMembers();
        }
        SourceTypeBinding sourceTypeBinding = this.referenceContext.binding;
        if ((sourceTypeBinding.tagBits & 256) != 0) {
            return;
        }
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        boolean z = compilationUnitScope.connectingHierarchy;
        compilationUnitScope.connectingHierarchy = true;
        try {
            sourceTypeBinding.tagBits = 256 | sourceTypeBinding.tagBits;
            environment().typesBeingConnected.add(sourceTypeBinding);
            boolean zConnectSuperclass = connectSuperclass() & connectSuperInterfaces();
            environment().typesBeingConnected.remove(sourceTypeBinding);
            sourceTypeBinding.tagBits |= 512;
            boolean zConnectTypeVariables = zConnectSuperclass & connectTypeVariables(this.referenceContext.typeParameters, false);
            sourceTypeBinding.tagBits |= 262144;
            if (zConnectTypeVariables && sourceTypeBinding.isHierarchyInconsistent()) {
                problemReporter().hierarchyHasProblems(sourceTypeBinding);
            }
        } finally {
            compilationUnitScope.connectingHierarchy = z;
        }
    }

    public boolean detectHierarchyCycle(TypeBinding typeBinding, TypeReference typeReference) {
        if (!(typeBinding instanceof ReferenceBinding)) {
            return false;
        }
        if (typeReference != this.superTypeReference) {
            if ((typeBinding.tagBits & 256) == 0 && (typeBinding instanceof SourceTypeBinding)) {
                ((SourceTypeBinding) typeBinding).scope.connectTypeHierarchyWithoutMembers();
            }
            return false;
        }
        if (typeBinding.isTypeVariable()) {
            return false;
        }
        if (typeBinding.isParameterizedType()) {
            typeBinding = ((ParameterizedTypeBinding) typeBinding).genericType();
        }
        compilationUnitScope().recordSuperTypeReference(typeBinding);
        return detectHierarchyCycle(this.referenceContext.binding, (ReferenceBinding) typeBinding, typeReference);
    }

    private boolean detectHierarchyCycle(SourceTypeBinding sourceTypeBinding, ReferenceBinding referenceBinding, TypeReference typeReference) {
        boolean zDetectHierarchyCycle;
        if (referenceBinding.isRawType()) {
            referenceBinding = ((RawTypeBinding) referenceBinding).genericType();
        }
        if (TypeBinding.equalsEquals(sourceTypeBinding, referenceBinding)) {
            problemReporter().hierarchyCircularity(sourceTypeBinding, referenceBinding, typeReference);
            sourceTypeBinding.tagBits |= 131072;
            return true;
        }
        if (referenceBinding.isMemberType()) {
            ReferenceBinding referenceBindingEnclosingType = referenceBinding.enclosingType();
            do {
                if (referenceBindingEnclosingType.isHierarchyBeingActivelyConnected() && TypeBinding.equalsEquals(referenceBindingEnclosingType, sourceTypeBinding)) {
                    problemReporter().hierarchyCircularity(sourceTypeBinding, referenceBindingEnclosingType, typeReference);
                    sourceTypeBinding.tagBits |= 131072;
                    referenceBindingEnclosingType.tagBits |= 131072;
                    return true;
                }
                referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType();
            } while (referenceBindingEnclosingType != null);
        }
        if (referenceBinding.isBinaryBinding()) {
            if (referenceBinding.problemId() != 1 && (referenceBinding.tagBits & 131072) != 0) {
                sourceTypeBinding.tagBits |= 131072;
                problemReporter().hierarchyHasProblems(sourceTypeBinding);
                return true;
            }
            ReferenceBinding referenceBindingSuperclass = referenceBinding.superclass();
            if (referenceBindingSuperclass == null) {
                zDetectHierarchyCycle = false;
            } else {
                if (TypeBinding.equalsEquals(sourceTypeBinding, referenceBindingSuperclass)) {
                    problemReporter().hierarchyCircularity(sourceTypeBinding, referenceBinding, typeReference);
                    sourceTypeBinding.tagBits |= 131072;
                    referenceBinding.tagBits = 131072 | referenceBinding.tagBits;
                    return true;
                }
                if (referenceBindingSuperclass.isParameterizedType()) {
                    referenceBindingSuperclass = ((ParameterizedTypeBinding) referenceBindingSuperclass).genericType();
                }
                zDetectHierarchyCycle = detectHierarchyCycle(sourceTypeBinding, referenceBindingSuperclass, typeReference);
                if ((referenceBindingSuperclass.tagBits & 131072) != 0) {
                    sourceTypeBinding.tagBits |= 131072;
                    referenceBindingSuperclass.tagBits |= 131072;
                }
            }
            ReferenceBinding[] referenceBindingArrSuperInterfaces = referenceBinding.superInterfaces();
            if (referenceBindingArrSuperInterfaces != null && referenceBindingArrSuperInterfaces != Binding.NO_SUPERINTERFACES) {
                for (ReferenceBinding referenceBindingGenericType : referenceBindingArrSuperInterfaces) {
                    if (TypeBinding.equalsEquals(sourceTypeBinding, referenceBindingGenericType)) {
                        problemReporter().hierarchyCircularity(sourceTypeBinding, referenceBinding, typeReference);
                        sourceTypeBinding.tagBits |= 131072;
                        referenceBinding.tagBits = 131072 | referenceBinding.tagBits;
                        return true;
                    }
                    if (referenceBindingGenericType.isParameterizedType()) {
                        referenceBindingGenericType = ((ParameterizedTypeBinding) referenceBindingGenericType).genericType();
                    }
                    zDetectHierarchyCycle |= detectHierarchyCycle(sourceTypeBinding, referenceBindingGenericType, typeReference);
                    if ((referenceBindingGenericType.tagBits & 131072) != 0) {
                        sourceTypeBinding.tagBits |= 131072;
                        referenceBinding.tagBits |= 131072;
                    }
                }
            }
            return zDetectHierarchyCycle;
        }
        if (referenceBinding.isHierarchyBeingActivelyConnected()) {
            TypeReference typeReference2 = ((SourceTypeBinding) referenceBinding).scope.superTypeReference;
            if (typeReference2 != null && typeReference2.resolvedType != null && ((ReferenceBinding) typeReference2.resolvedType).isHierarchyBeingActivelyConnected()) {
                problemReporter().hierarchyCircularity(sourceTypeBinding, referenceBinding, typeReference);
                sourceTypeBinding.tagBits |= 131072;
                referenceBinding.tagBits = 131072 | referenceBinding.tagBits;
                return true;
            }
            if (typeReference2 != null && typeReference2.resolvedType == null) {
                char[] lastToken = typeReference2.getLastToken();
                Iterator<SourceTypeBinding> it = environment().typesBeingConnected.iterator();
                while (it.hasNext()) {
                    if (CharOperation.equals(lastToken, it.next().sourceName())) {
                        problemReporter().hierarchyCircularity(sourceTypeBinding, referenceBinding, typeReference);
                        sourceTypeBinding.tagBits |= 131072;
                        referenceBinding.tagBits = 131072 | referenceBinding.tagBits;
                        return true;
                    }
                }
            }
        }
        if ((referenceBinding.tagBits & 256) == 0) {
            ((SourceTypeBinding) referenceBinding).scope.connectTypeHierarchyWithoutMembers();
        }
        if ((referenceBinding.tagBits & 131072) != 0) {
            sourceTypeBinding.tagBits |= 131072;
        }
        return false;
    }

    private ReferenceBinding findSupertype(TypeReference typeReference) {
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        LookupEnvironment lookupEnvironment = compilationUnitScope.environment;
        try {
            try {
                lookupEnvironment.missingClassFileLocation = typeReference;
                typeReference.aboutToResolve(this);
                compilationUnitScope.recordQualifiedReference(typeReference.getTypeName());
                this.superTypeReference = typeReference;
                ReferenceBinding referenceBinding = (ReferenceBinding) typeReference.resolveSuperType(this);
                lookupEnvironment.missingClassFileLocation = null;
                this.superTypeReference = null;
                return referenceBinding;
            } catch (AbortCompilation e) {
                SourceTypeBinding sourceTypeBinding = this.referenceContext.binding;
                if (sourceTypeBinding.superInterfaces == null) {
                    sourceTypeBinding.setSuperInterfaces(Binding.NO_SUPERINTERFACES);
                }
                e.updateContext(typeReference, referenceCompilationUnit().compilationResult);
                throw e;
            }
        } catch (Throwable th) {
            lookupEnvironment.missingClassFileLocation = null;
            this.superTypeReference = null;
            throw th;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Scope
    public ProblemReporter problemReporter() {
        MethodScope methodScopeOuterMostMethodScope = outerMostMethodScope();
        if (methodScopeOuterMostMethodScope == null) {
            ProblemReporter problemReporter = referenceCompilationUnit().problemReporter;
            problemReporter.referenceContext = this.referenceContext;
            return problemReporter;
        }
        return methodScopeOuterMostMethodScope.problemReporter();
    }

    public TypeDeclaration referenceType() {
        return this.referenceContext;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Scope
    public boolean hasDefaultNullnessFor(int i) {
        int nullDefault;
        SourceTypeBinding sourceTypeBinding = this.referenceContext.binding;
        if (sourceTypeBinding == null || (nullDefault = sourceTypeBinding.getNullDefault()) == 0) {
            return this.parent.hasDefaultNullnessFor(i);
        }
        return (i & nullDefault) != 0;
    }

    public String toString() {
        if (this.referenceContext != null) {
            return "--- Class Scope ---\n\n" + this.referenceContext.binding.toString();
        }
        return "--- Class Scope ---\n\n Binding not initialized";
    }
}
