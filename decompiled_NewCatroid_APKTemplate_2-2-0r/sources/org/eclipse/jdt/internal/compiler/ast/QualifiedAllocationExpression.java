package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ImplicitNullAnnotationVerifier;
import org.eclipse.jdt.internal.compiler.lookup.LocalTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedGenericMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.PolyTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.RawTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class QualifiedAllocationExpression extends AllocationExpression {
    public TypeDeclaration anonymousType;
    public Expression enclosingInstance;

    public QualifiedAllocationExpression() {
    }

    public QualifiedAllocationExpression(TypeDeclaration typeDeclaration) {
        this.anonymousType = typeDeclaration;
        typeDeclaration.allocation = this;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AllocationExpression, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        ReferenceBinding referenceBindingSuperclass;
        TypeBinding typeBindingErasure;
        Expression expression = this.enclosingInstance;
        if (expression != null) {
            flowInfo = expression.analyseCode(blockScope, flowContext, flowInfo);
        } else if (this.binding != null && this.binding.declaringClass != null && (referenceBindingSuperclass = this.binding.declaringClass.superclass()) != null && referenceBindingSuperclass.isMemberType() && !referenceBindingSuperclass.isStatic()) {
            blockScope.tagAsAccessingEnclosingInstanceStateOf(referenceBindingSuperclass.enclosingType(), false);
        }
        if (this.anonymousType == null) {
            typeBindingErasure = this.binding.declaringClass.erasure();
        } else {
            typeBindingErasure = this.binding.declaringClass.superclass().erasure();
        }
        checkCapturedLocalInitializationIfNecessary((ReferenceBinding) typeBindingErasure, blockScope, flowInfo);
        if (this.arguments != null) {
            boolean z = blockScope.compilerOptions().analyseResourceLeaks;
            boolean z2 = z && (this.resolvedType instanceof ReferenceBinding) && ((ReferenceBinding) this.resolvedType).hasTypeBit(4);
            int length = this.arguments.length;
            for (int i = 0; i < length; i++) {
                flowInfo = this.arguments[i].analyseCode(blockScope, flowContext, flowInfo);
                if (z && !z2) {
                    flowInfo = FakedTrackingVariable.markPassedToOutside(blockScope, this.arguments[i], flowInfo, flowContext, false);
                }
                this.arguments[i].checkNPEbyUnboxing(blockScope, flowContext, flowInfo);
            }
            analyseArguments(blockScope, flowContext, flowInfo, this.binding, this.arguments);
        }
        TypeDeclaration typeDeclaration = this.anonymousType;
        if (typeDeclaration != null) {
            flowInfo = typeDeclaration.analyseCode(blockScope, flowContext, flowInfo);
        }
        ReferenceBinding[] referenceBindingArrConvertToRawTypes = this.binding.thrownExceptions;
        if (referenceBindingArrConvertToRawTypes.length != 0) {
            if ((this.bits & 65536) != 0 && this.genericTypeArguments == null) {
                referenceBindingArrConvertToRawTypes = blockScope.environment().convertToRawTypes(this.binding.thrownExceptions, true, true);
            }
            flowContext.checkExceptionHandlers(referenceBindingArrConvertToRawTypes, this, flowInfo.unconditionalCopy(), blockScope);
        }
        if (blockScope.compilerOptions().analyseResourceLeaks && FakedTrackingVariable.isAnyCloseable(this.resolvedType)) {
            FakedTrackingVariable.analyseCloseableAllocation(blockScope, flowInfo, this);
        }
        manageEnclosingInstanceAccessIfNecessary(blockScope, flowInfo);
        manageSyntheticAccessIfNecessary(blockScope, flowInfo);
        flowContext.recordAbruptExit();
        return flowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AllocationExpression
    public Expression enclosingInstance() {
        return this.enclosingInstance;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AllocationExpression, org.eclipse.jdt.internal.compiler.ast.Expression
    public void generateCode(BlockScope blockScope, CodeStream codeStream, boolean z) {
        cleanUpInferenceContexts();
        if (!z) {
            blockScope.problemReporter().unusedObjectAllocation(this);
        }
        int i = codeStream.position;
        MethodBinding methodBindingOriginal = this.binding.original();
        ReferenceBinding referenceBinding = methodBindingOriginal.declaringClass;
        codeStream.new_(this.type, referenceBinding);
        boolean z2 = (this.implicitConversion & 1024) != 0;
        if (z || z2) {
            codeStream.dup();
        }
        if (this.type != null) {
            codeStream.recordPositionsFrom(i, this.type.sourceStart);
        } else {
            codeStream.ldc(String.valueOf(this.enumConstant.f617name));
            codeStream.generateInlinedValue(this.enumConstant.binding.f627id);
        }
        if (referenceBinding.isNestedType()) {
            codeStream.generateSyntheticEnclosingInstanceValues(blockScope, referenceBinding, enclosingInstance(), this);
        }
        generateArguments(this.binding, this.arguments, blockScope, codeStream);
        if (referenceBinding.isNestedType()) {
            codeStream.generateSyntheticOuterArgumentValues(blockScope, referenceBinding, this);
        }
        if (this.syntheticAccessor == null) {
            codeStream.invoke(Opcodes.OPC_invokespecial, methodBindingOriginal, null, this.typeArguments);
        } else {
            int length = this.syntheticAccessor.parameters.length - methodBindingOriginal.parameters.length;
            for (int i2 = 0; i2 < length; i2++) {
                codeStream.aconst_null();
            }
            codeStream.invoke(Opcodes.OPC_invokespecial, this.syntheticAccessor, null, this.typeArguments);
        }
        if (z) {
            codeStream.generateImplicitConversion(this.implicitConversion);
        } else if (z2) {
            codeStream.generateImplicitConversion(this.implicitConversion);
            int i3 = postConversionType(blockScope).f626id;
            if (i3 == 7 || i3 == 8) {
                codeStream.pop2();
            } else {
                codeStream.pop();
            }
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
        TypeDeclaration typeDeclaration = this.anonymousType;
        if (typeDeclaration != null) {
            typeDeclaration.generateCode(blockScope, codeStream);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AllocationExpression, org.eclipse.jdt.internal.compiler.lookup.InvocationSite
    public boolean isSuperAccess() {
        return this.anonymousType != null;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AllocationExpression
    public void manageEnclosingInstanceAccessIfNecessary(BlockScope blockScope, FlowInfo flowInfo) {
        if ((flowInfo.tagBits & 1) == 0) {
            ReferenceBinding referenceBinding = (ReferenceBinding) this.binding.declaringClass.erasure();
            if (referenceBinding.isNestedType()) {
                if (blockScope.enclosingSourceType().isLocalType() || blockScope.isLambdaSubscope()) {
                    if (referenceBinding.isLocalType()) {
                        ((LocalTypeBinding) referenceBinding).addInnerEmulationDependent(blockScope, this.enclosingInstance != null);
                    } else {
                        blockScope.propagateInnerEmulation(referenceBinding, this.enclosingInstance != null);
                    }
                }
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AllocationExpression, org.eclipse.jdt.internal.compiler.ast.Expression
    public StringBuffer printExpression(int i, StringBuffer stringBuffer) {
        Expression expression = this.enclosingInstance;
        if (expression != null) {
            expression.printExpression(0, stringBuffer).append('.');
        }
        super.printExpression(0, stringBuffer);
        TypeDeclaration typeDeclaration = this.anonymousType;
        if (typeDeclaration != null) {
            typeDeclaration.print(i, stringBuffer);
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AllocationExpression, org.eclipse.jdt.internal.compiler.ast.Expression
    public TypeBinding resolveType(BlockScope blockScope) throws Throwable {
        if (this.anonymousType == null && this.enclosingInstance == null) {
            return super.resolveType(blockScope);
        }
        TypeBinding typeBindingResolveTypeForQualifiedAllocationExpression = resolveTypeForQualifiedAllocationExpression(blockScope);
        if (typeBindingResolveTypeForQualifiedAllocationExpression != null && !typeBindingResolveTypeForQualifiedAllocationExpression.isPolyType() && this.binding != null) {
            CompilerOptions compilerOptions = blockScope.compilerOptions();
            if (compilerOptions.isAnnotationBasedNullAnalysisEnabled) {
                if ((this.binding.tagBits & 4096) == 0) {
                    new ImplicitNullAnnotationVerifier(blockScope.environment(), compilerOptions.inheritNullAnnotations).checkImplicitNullAnnotations(this.binding, null, false, blockScope);
                }
                if (compilerOptions.sourceLevel >= ClassFileConstants.JDK1_8 && (this.binding instanceof ParameterizedGenericMethodBinding) && this.typeArguments != null) {
                    TypeVariableBinding[] typeVariableBindingArrTypeVariables = this.binding.original().typeVariables();
                    for (int i = 0; i < this.typeArguments.length; i++) {
                        this.typeArguments[i].checkNullConstraints(blockScope, (ParameterizedGenericMethodBinding) this.binding, typeVariableBindingArrTypeVariables, i);
                    }
                }
            }
            if (compilerOptions.sourceLevel >= ClassFileConstants.JDK1_8 && this.binding.getTypeAnnotations() != Binding.NO_ANNOTATIONS) {
                this.resolvedType = blockScope.environment().createAnnotatedType(this.resolvedType, this.binding.getTypeAnnotations());
            }
        }
        return typeBindingResolveTypeForQualifiedAllocationExpression;
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0172  */
    /* JADX WARN: Code duplicated, block: B:103:0x017d  */
    /* JADX WARN: Code duplicated, block: B:105:0x0185  */
    /* JADX WARN: Code duplicated, block: B:108:0x019c  */
    /* JADX WARN: Code duplicated, block: B:111:0x01a4 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:112:0x01a6 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:113:0x01a7  */
    /* JADX WARN: Code duplicated, block: B:115:0x01ab  */
    /* JADX WARN: Code duplicated, block: B:117:0x01b4  */
    /* JADX WARN: Code duplicated, block: B:119:0x01b8  */
    /* JADX WARN: Code duplicated, block: B:120:0x01ba  */
    /* JADX WARN: Code duplicated, block: B:132:0x01ef  */
    /* JADX WARN: Code duplicated, block: B:138:0x0215  */
    /* JADX WARN: Code duplicated, block: B:140:0x021b  */
    /* JADX WARN: Code duplicated, block: B:141:0x021e  */
    /* JADX WARN: Code duplicated, block: B:145:0x0229  */
    /* JADX WARN: Code duplicated, block: B:149:0x023b  */
    /* JADX WARN: Code duplicated, block: B:151:0x023f  */
    /* JADX WARN: Code duplicated, block: B:153:0x0245  */
    /* JADX WARN: Code duplicated, block: B:155:0x0251 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:156:0x0253  */
    /* JADX WARN: Code duplicated, block: B:158:0x025d  */
    /* JADX WARN: Code duplicated, block: B:160:0x0265  */
    /* JADX WARN: Code duplicated, block: B:162:0x027c  */
    /* JADX WARN: Code duplicated, block: B:170:0x0298  */
    /* JADX WARN: Code duplicated, block: B:171:0x029e  */
    /* JADX WARN: Code duplicated, block: B:174:0x02bb A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:175:0x02bc  */
    /* JADX WARN: Code duplicated, block: B:177:0x02c8  */
    /* JADX WARN: Code duplicated, block: B:179:0x02cc  */
    /* JADX WARN: Code duplicated, block: B:187:0x02e7  */
    /* JADX WARN: Code duplicated, block: B:189:0x02f0  */
    /* JADX WARN: Code duplicated, block: B:192:0x02fb  */
    /* JADX WARN: Code duplicated, block: B:194:0x0303  */
    /* JADX WARN: Code duplicated, block: B:196:0x030f  */
    /* JADX WARN: Code duplicated, block: B:218:0x036e  */
    /* JADX WARN: Code duplicated, block: B:227:0x038a  */
    /* JADX WARN: Code duplicated, block: B:243:0x03d2  */
    /* JADX WARN: Code duplicated, block: B:246:0x03e6  */
    /* JADX WARN: Code duplicated, block: B:248:0x03ee  */
    /* JADX WARN: Code duplicated, block: B:251:0x040c  */
    /* JADX WARN: Code duplicated, block: B:259:0x043a  */
    /* JADX WARN: Code duplicated, block: B:266:0x045f  */
    /* JADX WARN: Code duplicated, block: B:276:0x0487  */
    /* JADX WARN: Code duplicated, block: B:278:0x048e  */
    /* JADX WARN: Code duplicated, block: B:293:0x019f A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:294:0x01c3 A[EDGE_INSN: B:294:0x01c3->B:124:0x01c3 BREAK  A[LOOP:3: B:122:0x01bf->B:142:0x0222], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:68:0x0107  */
    /* JADX WARN: Code duplicated, block: B:71:0x010c  */
    /* JADX WARN: Code duplicated, block: B:73:0x0116  */
    /* JADX WARN: Code duplicated, block: B:74:0x0118  */
    /* JADX WARN: Code duplicated, block: B:78:0x0124  */
    /* JADX WARN: Code duplicated, block: B:80:0x012e  */
    /* JADX WARN: Code duplicated, block: B:82:0x0132  */
    /* JADX WARN: Code duplicated, block: B:84:0x0136  */
    /* JADX WARN: Code duplicated, block: B:87:0x013d A[LOOP:1: B:85:0x013a->B:87:0x013d, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:89:0x0148  */
    /* JADX WARN: Code duplicated, block: B:91:0x0156  */
    private TypeBinding resolveTypeForQualifiedAllocationExpression(BlockScope blockScope) throws Throwable {
        TypeBinding typeBindingEnclosingSourceType;
        TypeBinding typeBinding;
        TypeBinding typeBindingResolveType;
        TypeBinding typeBinding2;
        ReferenceBinding referenceBindingEnclosingType;
        boolean z;
        ReferenceBinding referenceBinding;
        ReferenceBinding javaLangObject;
        MethodBinding methodBindingFindConstructorBinding;
        ReferenceBinding referenceBindingEnclosingType2;
        ReferenceBinding referenceBinding2;
        TypeDeclaration typeDeclaration;
        int length;
        TypeBinding[] typeBindingArr;
        MethodBinding methodBindingCreateParameterizedGenericMethod;
        MethodBinding methodBindingOriginal;
        TypeBinding typeBinding3;
        int length2;
        int i;
        Expression expression;
        TypeBinding typeBindingResolveType2;
        int length3;
        boolean z2;
        int i2;
        int length4;
        int i3;
        TypeBinding typeBindingResolveType3;
        boolean z3;
        boolean z4 = (this.type == null || (this.type.bits & 524288) == 0) ? false : true;
        long j = blockScope.compilerOptions().sourceLevel;
        if (this.constant != Constant.NotAConstant) {
            this.constant = Constant.NotAConstant;
            Expression expression2 = this.enclosingInstance;
            if (expression2 != null) {
                if (expression2 instanceof CastExpression) {
                    expression2.bits |= 32;
                    z3 = true;
                } else {
                    z3 = false;
                }
                typeBindingResolveType = this.enclosingInstance.resolveType(blockScope);
                if (typeBindingResolveType != null) {
                    if (typeBindingResolveType.isBaseType() || typeBindingResolveType.isArrayType()) {
                        blockScope.problemReporter().illegalPrimitiveOrArrayTypeForEnclosingInstance(typeBindingResolveType, this.enclosingInstance);
                    } else if (this.type instanceof QualifiedTypeReference) {
                        blockScope.problemReporter().illegalUsageOfQualifiedTypeReference((QualifiedTypeReference) this.type);
                    } else {
                        ReferenceBinding referenceBinding3 = (ReferenceBinding) typeBindingResolveType;
                        if (!referenceBinding3.canBeSeenBy(blockScope)) {
                            typeBindingResolveType = new ProblemReferenceBinding(referenceBinding3.compoundName, referenceBinding3, 2);
                            blockScope.problemReporter().invalidType(this.enclosingInstance, typeBindingResolveType);
                        } else {
                            TypeBinding typeBindingResolveTypeEnclosing = ((SingleTypeReference) this.type).resolveTypeEnclosing(blockScope, referenceBinding3);
                            this.resolvedType = typeBindingResolveTypeEnclosing;
                            checkIllegalNullAnnotation(blockScope, typeBindingResolveTypeEnclosing);
                            if (typeBindingResolveTypeEnclosing != null && z3) {
                                CastExpression.checkNeedForEnclosingInstanceCast(blockScope, this.enclosingInstance, typeBindingResolveType, typeBindingResolveTypeEnclosing);
                            }
                            typeBindingEnclosingSourceType = typeBindingResolveTypeEnclosing;
                        }
                    }
                }
                typeBindingEnclosingSourceType = null;
                z = true;
                if (typeBindingEnclosingSourceType != null || !typeBindingEnclosingSourceType.isValidBinding()) {
                    z = true;
                }
                if (this.typeArguments != null) {
                    length3 = this.typeArguments.length;
                    if (j < ClassFileConstants.JDK1_5) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    this.argumentsHaveErrors = z2;
                    this.genericTypeArguments = new TypeBinding[length3];
                    for (i2 = 0; i2 < length3; i2++) {
                        TypeReference typeReference = this.typeArguments[i2];
                        TypeBinding[] typeBindingArr2 = this.genericTypeArguments;
                        typeBindingResolveType3 = typeReference.resolveType(blockScope, true);
                        typeBindingArr2[i2] = typeBindingResolveType3;
                        if (typeBindingResolveType3 == null) {
                            this.argumentsHaveErrors = true;
                        }
                        if (!this.argumentsHaveErrors && (typeReference instanceof Wildcard)) {
                            blockScope.problemReporter().illegalUsageOfWildcard(typeReference);
                        }
                    }
                    if (z4) {
                        blockScope.problemReporter().diamondNotWithExplicitTypeArguments(this.typeArguments);
                        return null;
                    }
                    if (this.argumentsHaveErrors) {
                        if (this.arguments != null) {
                            length4 = this.arguments.length;
                            for (i3 = 0; i3 < length4; i3++) {
                                this.arguments[i3].resolveType(blockScope);
                            }
                        }
                        return null;
                    }
                }
                this.argumentTypes = Binding.NO_PARAMETERS;
                if (this.arguments != null) {
                    length2 = this.arguments.length;
                    this.argumentTypes = new TypeBinding[length2];
                    for (i = 0; i < length2; i++) {
                        expression = this.arguments[i];
                        if (expression instanceof CastExpression) {
                            expression.bits |= 32;
                            this.argsContainCast = true;
                        }
                        expression.setExpressionContext(ExpressionContext.INVOCATION_CONTEXT);
                        TypeBinding[] typeBindingArr3 = this.argumentTypes;
                        typeBindingResolveType2 = expression.resolveType(blockScope);
                        typeBindingArr3[i] = typeBindingResolveType2;
                        if (typeBindingResolveType2 == null) {
                            this.argumentsHaveErrors = true;
                            z = true;
                        }
                    }
                }
                if (z) {
                    if (z4) {
                        return null;
                    }
                    if (typeBindingEnclosingSourceType instanceof ReferenceBinding) {
                        referenceBinding2 = (ReferenceBinding) typeBindingEnclosingSourceType;
                        if (typeBindingEnclosingSourceType.isValidBinding()) {
                            if (this.arguments == null) {
                                length = 0;
                            } else {
                                length = this.arguments.length;
                            }
                            typeBindingArr = new TypeBinding[length];
                            while (true) {
                                length--;
                                if (length < 0) {
                                    break;
                                }
                                if (this.argumentTypes[length] == null) {
                                    typeBinding3 = TypeBinding.NULL;
                                } else {
                                    typeBinding3 = this.argumentTypes[length];
                                }
                                typeBindingArr[length] = typeBinding3;
                            }
                            this.binding = blockScope.findMethod(referenceBinding2, TypeConstants.INIT, typeBindingArr, this, false);
                            if (this.binding != null && !this.binding.isValidBinding() && (methodBindingCreateParameterizedGenericMethod = ((ProblemMethodBinding) this.binding).closestMatch) != null) {
                                if (methodBindingCreateParameterizedGenericMethod.original().typeVariables != Binding.NO_TYPE_VARIABLES) {
                                    methodBindingCreateParameterizedGenericMethod = blockScope.environment().createParameterizedGenericMethod(methodBindingCreateParameterizedGenericMethod.original(), (RawTypeBinding) null);
                                }
                                this.binding = methodBindingCreateParameterizedGenericMethod;
                                methodBindingOriginal = methodBindingCreateParameterizedGenericMethod.original();
                                if (methodBindingOriginal.isOrEnclosedByPrivateType() && !blockScope.isDefinedInMethod(methodBindingOriginal)) {
                                    methodBindingOriginal.modifiers |= 134217728;
                                }
                            }
                        }
                        typeDeclaration = this.anonymousType;
                        if (typeDeclaration != null) {
                            blockScope.addAnonymousType(typeDeclaration, referenceBinding2);
                            this.anonymousType.resolve(blockScope);
                            SourceTypeBinding sourceTypeBinding = this.anonymousType.binding;
                            this.resolvedType = sourceTypeBinding;
                            return sourceTypeBinding;
                        }
                    }
                    this.resolvedType = typeBindingEnclosingSourceType;
                    return typeBindingEnclosingSourceType;
                }
                if (this.anonymousType == null) {
                    if (z4) {
                        blockScope.problemReporter().diamondNotWithAnoymousClasses(this.type);
                        return null;
                    }
                    referenceBinding = (ReferenceBinding) typeBindingEnclosingSourceType;
                    if (referenceBinding.isTypeVariable()) {
                        blockScope.problemReporter().invalidType(this, new ProblemReferenceBinding(new char[][]{referenceBinding.sourceName()}, referenceBinding, 9));
                        return null;
                    }
                    if (this.type == null && referenceBinding.isEnum()) {
                        blockScope.problemReporter().cannotInstantiate(this.type, referenceBinding);
                        this.resolvedType = referenceBinding;
                        return referenceBinding;
                    }
                    if (referenceBinding.isInterface()) {
                        javaLangObject = blockScope.getJavaLangObject();
                    } else {
                        javaLangObject = referenceBinding;
                    }
                    blockScope.addAnonymousType(this.anonymousType, referenceBinding);
                    this.anonymousType.resolve(blockScope);
                    this.resolvedType = this.anonymousType.binding;
                    if ((this.resolvedType.tagBits & 131072) != 0) {
                        return null;
                    }
                    methodBindingFindConstructorBinding = findConstructorBinding(blockScope, this, javaLangObject, this.argumentTypes);
                    if (!methodBindingFindConstructorBinding.isValidBinding()) {
                        if (methodBindingFindConstructorBinding.declaringClass == null) {
                            methodBindingFindConstructorBinding.declaringClass = javaLangObject;
                        }
                        if (this.type == null && !this.type.resolvedType.isValidBinding()) {
                            return null;
                        }
                        blockScope.problemReporter().invalidConstructor(this, methodBindingFindConstructorBinding);
                        return this.resolvedType;
                    }
                    if ((methodBindingFindConstructorBinding.tagBits & 128) != 0) {
                        blockScope.problemReporter().missingTypeInConstructor(this, methodBindingFindConstructorBinding);
                    }
                    if (this.enclosingInstance != null) {
                        referenceBindingEnclosingType2 = methodBindingFindConstructorBinding.declaringClass.enclosingType();
                        if (referenceBindingEnclosingType2 == null) {
                            blockScope.problemReporter().unnecessaryEnclosingInstanceSpecification(this.enclosingInstance, referenceBinding);
                            return this.resolvedType;
                        }
                        if (typeBindingResolveType.isCompatibleWith(referenceBindingEnclosingType2) && !blockScope.isBoxingCompatibleWith(typeBindingResolveType, referenceBindingEnclosingType2)) {
                            blockScope.problemReporter().typeMismatchError(typeBindingResolveType, referenceBindingEnclosingType2, this.enclosingInstance, (ASTNode) null);
                            return this.resolvedType;
                        }
                        this.enclosingInstance.computeConversion(blockScope, referenceBindingEnclosingType2, typeBindingResolveType);
                    }
                    if (this.arguments != null && checkInvocationArguments(blockScope, null, javaLangObject, methodBindingFindConstructorBinding, this.arguments, this.argumentTypes, this.argsContainCast, this)) {
                        this.bits |= 65536;
                    }
                    if (this.typeArguments != null && methodBindingFindConstructorBinding.original().typeVariables == Binding.NO_TYPE_VARIABLES) {
                        blockScope.problemReporter().unnecessaryTypeArgumentsForMethodInvocation(methodBindingFindConstructorBinding, this.genericTypeArguments, this.typeArguments);
                    }
                    this.binding = this.anonymousType.createDefaultConstructorWithBinding(methodBindingFindConstructorBinding, (this.bits & 65536) == 0 && this.genericTypeArguments == null);
                    return this.resolvedType;
                }
                if (!typeBindingEnclosingSourceType.canBeInstantiated()) {
                    blockScope.problemReporter().cannotInstantiate(this.type, typeBindingEnclosingSourceType);
                    this.resolvedType = typeBindingEnclosingSourceType;
                    return typeBindingEnclosingSourceType;
                }
            } else {
                if (this.type == null) {
                    typeBindingEnclosingSourceType = blockScope.enclosingSourceType();
                } else {
                    TypeBinding typeBindingResolveType4 = this.type.resolveType(blockScope, true);
                    checkIllegalNullAnnotation(blockScope, typeBindingResolveType4);
                    if (typeBindingResolveType4 != null && typeBindingResolveType4.isValidBinding() && (this.type instanceof ParameterizedQualifiedTypeReference)) {
                        ReferenceBinding referenceBindingEnclosingType3 = (ReferenceBinding) typeBindingResolveType4;
                        while ((referenceBindingEnclosingType3.modifiers & 8) == 0 && !referenceBindingEnclosingType3.isRawType()) {
                            referenceBindingEnclosingType3 = referenceBindingEnclosingType3.enclosingType();
                            if (referenceBindingEnclosingType3 == null) {
                                ParameterizedQualifiedTypeReference parameterizedQualifiedTypeReference = (ParameterizedQualifiedTypeReference) this.type;
                                for (int length5 = parameterizedQualifiedTypeReference.typeArguments.length - 2; length5 >= 0; length5--) {
                                    if (parameterizedQualifiedTypeReference.typeArguments[length5] != null) {
                                        blockScope.problemReporter().illegalQualifiedParameterizedTypeAllocation(this.type, typeBindingResolveType4);
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    typeBindingEnclosingSourceType = typeBindingResolveType4;
                }
                typeBindingResolveType = null;
            }
            z = false;
            if (typeBindingEnclosingSourceType != null) {
                z = true;
            } else {
                z = true;
            }
            if (this.typeArguments != null) {
                length3 = this.typeArguments.length;
                if (j < ClassFileConstants.JDK1_5) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                this.argumentsHaveErrors = z2;
                this.genericTypeArguments = new TypeBinding[length3];
                while (i2 < length3) {
                    TypeReference typeReference2 = this.typeArguments[i2];
                    TypeBinding[] typeBindingArr4 = this.genericTypeArguments;
                    typeBindingResolveType3 = typeReference2.resolveType(blockScope, true);
                    typeBindingArr4[i2] = typeBindingResolveType3;
                    if (typeBindingResolveType3 == null) {
                        this.argumentsHaveErrors = true;
                    }
                    if (!this.argumentsHaveErrors) {
                    }
                }
                if (z4) {
                    blockScope.problemReporter().diamondNotWithExplicitTypeArguments(this.typeArguments);
                    return null;
                }
                if (this.argumentsHaveErrors) {
                    if (this.arguments != null) {
                        length4 = this.arguments.length;
                        while (i3 < length4) {
                            this.arguments[i3].resolveType(blockScope);
                        }
                    }
                    return null;
                }
            }
            this.argumentTypes = Binding.NO_PARAMETERS;
            if (this.arguments != null) {
                length2 = this.arguments.length;
                this.argumentTypes = new TypeBinding[length2];
                while (i < length2) {
                    expression = this.arguments[i];
                    if (expression instanceof CastExpression) {
                        expression.bits |= 32;
                        this.argsContainCast = true;
                    }
                    expression.setExpressionContext(ExpressionContext.INVOCATION_CONTEXT);
                    TypeBinding[] typeBindingArr5 = this.argumentTypes;
                    typeBindingResolveType2 = expression.resolveType(blockScope);
                    typeBindingArr5[i] = typeBindingResolveType2;
                    if (typeBindingResolveType2 == null) {
                        this.argumentsHaveErrors = true;
                        z = true;
                    }
                }
            }
            if (z) {
                if (z4) {
                    return null;
                }
                if (typeBindingEnclosingSourceType instanceof ReferenceBinding) {
                    referenceBinding2 = (ReferenceBinding) typeBindingEnclosingSourceType;
                    if (typeBindingEnclosingSourceType.isValidBinding()) {
                        if (this.arguments == null) {
                            length = 0;
                        } else {
                            length = this.arguments.length;
                        }
                        typeBindingArr = new TypeBinding[length];
                        while (true) {
                            length--;
                            if (length < 0) {
                                break;
                                break;
                            }
                            if (this.argumentTypes[length] == null) {
                                typeBinding3 = TypeBinding.NULL;
                            } else {
                                typeBinding3 = this.argumentTypes[length];
                            }
                            typeBindingArr[length] = typeBinding3;
                        }
                        this.binding = blockScope.findMethod(referenceBinding2, TypeConstants.INIT, typeBindingArr, this, false);
                        if (this.binding != null) {
                            if (methodBindingCreateParameterizedGenericMethod.original().typeVariables != Binding.NO_TYPE_VARIABLES) {
                                methodBindingCreateParameterizedGenericMethod = blockScope.environment().createParameterizedGenericMethod(methodBindingCreateParameterizedGenericMethod.original(), (RawTypeBinding) null);
                            }
                            this.binding = methodBindingCreateParameterizedGenericMethod;
                            methodBindingOriginal = methodBindingCreateParameterizedGenericMethod.original();
                            if (methodBindingOriginal.isOrEnclosedByPrivateType()) {
                                methodBindingOriginal.modifiers |= 134217728;
                            }
                        }
                    }
                    typeDeclaration = this.anonymousType;
                    if (typeDeclaration != null) {
                        blockScope.addAnonymousType(typeDeclaration, referenceBinding2);
                        this.anonymousType.resolve(blockScope);
                        SourceTypeBinding sourceTypeBinding2 = this.anonymousType.binding;
                        this.resolvedType = sourceTypeBinding2;
                        return sourceTypeBinding2;
                    }
                }
                this.resolvedType = typeBindingEnclosingSourceType;
                return typeBindingEnclosingSourceType;
            }
            if (this.anonymousType == null) {
                if (z4) {
                    blockScope.problemReporter().diamondNotWithAnoymousClasses(this.type);
                    return null;
                }
                referenceBinding = (ReferenceBinding) typeBindingEnclosingSourceType;
                if (referenceBinding.isTypeVariable()) {
                    blockScope.problemReporter().invalidType(this, new ProblemReferenceBinding(new char[][]{referenceBinding.sourceName()}, referenceBinding, 9));
                    return null;
                }
                if (this.type == null) {
                }
                if (referenceBinding.isInterface()) {
                    javaLangObject = blockScope.getJavaLangObject();
                } else {
                    javaLangObject = referenceBinding;
                }
                blockScope.addAnonymousType(this.anonymousType, referenceBinding);
                this.anonymousType.resolve(blockScope);
                this.resolvedType = this.anonymousType.binding;
                if ((this.resolvedType.tagBits & 131072) != 0) {
                    return null;
                }
                methodBindingFindConstructorBinding = findConstructorBinding(blockScope, this, javaLangObject, this.argumentTypes);
                if (!methodBindingFindConstructorBinding.isValidBinding()) {
                    if (methodBindingFindConstructorBinding.declaringClass == null) {
                        methodBindingFindConstructorBinding.declaringClass = javaLangObject;
                    }
                    if (this.type == null) {
                    }
                    blockScope.problemReporter().invalidConstructor(this, methodBindingFindConstructorBinding);
                    return this.resolvedType;
                }
                if ((methodBindingFindConstructorBinding.tagBits & 128) != 0) {
                    blockScope.problemReporter().missingTypeInConstructor(this, methodBindingFindConstructorBinding);
                }
                if (this.enclosingInstance != null) {
                    referenceBindingEnclosingType2 = methodBindingFindConstructorBinding.declaringClass.enclosingType();
                    if (referenceBindingEnclosingType2 == null) {
                        blockScope.problemReporter().unnecessaryEnclosingInstanceSpecification(this.enclosingInstance, referenceBinding);
                        return this.resolvedType;
                    }
                    if (typeBindingResolveType.isCompatibleWith(referenceBindingEnclosingType2)) {
                    }
                    this.enclosingInstance.computeConversion(blockScope, referenceBindingEnclosingType2, typeBindingResolveType);
                }
                if (this.arguments != null) {
                    this.bits |= 65536;
                }
                if (this.typeArguments != null) {
                    blockScope.problemReporter().unnecessaryTypeArgumentsForMethodInvocation(methodBindingFindConstructorBinding, this.genericTypeArguments, this.typeArguments);
                }
                this.binding = this.anonymousType.createDefaultConstructorWithBinding(methodBindingFindConstructorBinding, (this.bits & 65536) == 0 && this.genericTypeArguments == null);
                return this.resolvedType;
            }
            if (!typeBindingEnclosingSourceType.canBeInstantiated()) {
                blockScope.problemReporter().cannotInstantiate(this.type, typeBindingEnclosingSourceType);
                this.resolvedType = typeBindingEnclosingSourceType;
                return typeBindingEnclosingSourceType;
            }
        } else {
            Expression expression3 = this.enclosingInstance;
            if (expression3 != null) {
                typeBindingResolveType = expression3.resolvedType;
                typeBindingEnclosingSourceType = this.type.resolvedType;
                this.resolvedType = typeBindingEnclosingSourceType;
            } else {
                typeBindingEnclosingSourceType = null;
                typeBinding = null;
            }
            if (z4) {
                this.binding = inferConstructorOfElidedParameterizedType(blockScope);
                if (this.binding != null || !this.binding.isValidBinding()) {
                    blockScope.problemReporter().cannotInferElidedTypes(this);
                    this.resolvedType = null;
                    return null;
                }
                if (this.typeExpected == null && j >= ClassFileConstants.JDK1_8 && this.expressionContext.definesTargetType()) {
                    return new PolyTypeBinding(this);
                }
                TypeReference typeReference3 = this.type;
                typeBindingEnclosingSourceType = this.binding.declaringClass;
                typeReference3.resolvedType = typeBindingEnclosingSourceType;
                this.resolvedType = typeBindingEnclosingSourceType;
                resolvePolyExpressionArguments(this, this.binding, this.argumentTypes, blockScope);
            } else {
                this.binding = findConstructorBinding(blockScope, this, (ReferenceBinding) typeBindingEnclosingSourceType, this.argumentTypes);
            }
            typeBinding2 = typeBindingEnclosingSourceType;
            if (this.binding.isValidBinding()) {
                if (isMethodUseDeprecated(this.binding, blockScope, true)) {
                    blockScope.problemReporter().deprecatedMethod(this.binding, this);
                }
                if (checkInvocationArguments(blockScope, null, typeBinding2, this.binding, this.arguments, this.argumentTypes, this.argsContainCast, this)) {
                    this.bits |= 65536;
                }
                if (this.typeArguments != null && this.binding.original().typeVariables == Binding.NO_TYPE_VARIABLES) {
                    blockScope.problemReporter().unnecessaryTypeArgumentsForMethodInvocation(this.binding, this.genericTypeArguments, this.typeArguments);
                }
                if ((this.binding.tagBits & 128) != 0) {
                    blockScope.problemReporter().missingTypeInConstructor(this, this.binding);
                }
                if (!z4 && typeBinding2.isParameterizedTypeWithActualArguments()) {
                    checkTypeArgumentRedundancy((ParameterizedTypeBinding) typeBinding2, blockScope);
                }
                referenceBindingEnclosingType = this.binding.declaringClass.enclosingType();
                if (TypeBinding.notEquals(referenceBindingEnclosingType, typeBinding)) {
                    blockScope.compilationUnitScope().recordTypeConversion(referenceBindingEnclosingType, typeBinding);
                }
                if (!typeBinding.isCompatibleWith(referenceBindingEnclosingType) || blockScope.isBoxingCompatibleWith(typeBinding, referenceBindingEnclosingType)) {
                    this.enclosingInstance.computeConversion(blockScope, referenceBindingEnclosingType, typeBinding);
                    this.resolvedType = typeBinding2;
                    return typeBinding2;
                }
                blockScope.problemReporter().typeMismatchError(typeBinding, referenceBindingEnclosingType, this.enclosingInstance, (ASTNode) null);
                this.resolvedType = typeBinding2;
                return typeBinding2;
            }
            if (this.binding.declaringClass == null) {
                this.binding.declaringClass = (ReferenceBinding) typeBinding2;
            }
            if (this.type == null && !this.type.resolvedType.isValidBinding()) {
                return null;
            }
            blockScope.problemReporter().invalidConstructor(this, this.binding);
            this.resolvedType = typeBinding2;
            return typeBinding2;
        }
        typeBinding = typeBindingResolveType;
        if (z4) {
            this.binding = inferConstructorOfElidedParameterizedType(blockScope);
            if (this.binding != null) {
            }
            blockScope.problemReporter().cannotInferElidedTypes(this);
            this.resolvedType = null;
            return null;
        }
        this.binding = findConstructorBinding(blockScope, this, (ReferenceBinding) typeBindingEnclosingSourceType, this.argumentTypes);
        typeBinding2 = typeBindingEnclosingSourceType;
        if (this.binding.isValidBinding()) {
            if (isMethodUseDeprecated(this.binding, blockScope, true)) {
                blockScope.problemReporter().deprecatedMethod(this.binding, this);
            }
            if (checkInvocationArguments(blockScope, null, typeBinding2, this.binding, this.arguments, this.argumentTypes, this.argsContainCast, this)) {
                this.bits |= 65536;
            }
            if (this.typeArguments != null) {
                blockScope.problemReporter().unnecessaryTypeArgumentsForMethodInvocation(this.binding, this.genericTypeArguments, this.typeArguments);
            }
            if ((this.binding.tagBits & 128) != 0) {
                blockScope.problemReporter().missingTypeInConstructor(this, this.binding);
            }
            if (!z4) {
                checkTypeArgumentRedundancy((ParameterizedTypeBinding) typeBinding2, blockScope);
            }
            referenceBindingEnclosingType = this.binding.declaringClass.enclosingType();
            if (TypeBinding.notEquals(referenceBindingEnclosingType, typeBinding)) {
                blockScope.compilationUnitScope().recordTypeConversion(referenceBindingEnclosingType, typeBinding);
            }
            if (!typeBinding.isCompatibleWith(referenceBindingEnclosingType)) {
            }
            this.enclosingInstance.computeConversion(blockScope, referenceBindingEnclosingType, typeBinding);
            this.resolvedType = typeBinding2;
            return typeBinding2;
        }
        if (this.binding.declaringClass == null) {
            this.binding.declaringClass = (ReferenceBinding) typeBinding2;
        }
        if (this.type == null) {
        }
        blockScope.problemReporter().invalidConstructor(this, this.binding);
        this.resolvedType = typeBinding2;
        return typeBinding2;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AllocationExpression, org.eclipse.jdt.internal.compiler.ast.Expression, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            Expression expression = this.enclosingInstance;
            if (expression != null) {
                expression.traverse(aSTVisitor, blockScope);
            }
            if (this.typeArguments != null) {
                int length = this.typeArguments.length;
                for (int i = 0; i < length; i++) {
                    this.typeArguments[i].traverse(aSTVisitor, blockScope);
                }
            }
            if (this.type != null) {
                this.type.traverse(aSTVisitor, blockScope);
            }
            if (this.arguments != null) {
                int length2 = this.arguments.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    this.arguments[i2].traverse(aSTVisitor, blockScope);
                }
            }
            TypeDeclaration typeDeclaration = this.anonymousType;
            if (typeDeclaration != null) {
                typeDeclaration.traverse(aSTVisitor, blockScope);
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }
}
