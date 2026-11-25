package org.eclipse.jdt.internal.compiler.ast;

import java.util.HashMap;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.env.AccessRestriction;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.InferenceContext18;
import org.eclipse.jdt.internal.compiler.lookup.InvocationSite;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.PackageBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedGenericMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.lookup.TypeIds;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.WildcardBinding;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;

/* JADX INFO: loaded from: classes14.dex */
public abstract class ASTNode implements TypeConstants, TypeIds {
    public static final int Bit1 = 1;
    public static final int Bit10 = 512;
    public static final int Bit11 = 1024;
    public static final int Bit12 = 2048;
    public static final int Bit13 = 4096;
    public static final int Bit14 = 8192;
    public static final int Bit15 = 16384;
    public static final int Bit16 = 32768;
    public static final int Bit17 = 65536;
    public static final int Bit18 = 131072;
    public static final int Bit19 = 262144;
    public static final int Bit2 = 2;
    public static final int Bit20 = 524288;
    public static final int Bit21 = 1048576;
    public static final int Bit22 = 2097152;
    public static final int Bit23 = 4194304;
    public static final int Bit24 = 8388608;
    public static final int Bit25 = 16777216;
    public static final int Bit26 = 33554432;
    public static final int Bit27 = 67108864;
    public static final int Bit28 = 134217728;
    public static final int Bit29 = 268435456;
    public static final int Bit3 = 4;
    public static final int Bit30 = 536870912;
    public static final int Bit31 = 1073741824;
    public static final int Bit32 = Integer.MIN_VALUE;
    public static final long Bit32L = 2147483648L;
    public static final long Bit33L = 4294967296L;
    public static final long Bit34L = 8589934592L;
    public static final long Bit35L = 17179869184L;
    public static final long Bit36L = 34359738368L;
    public static final long Bit37L = 68719476736L;
    public static final long Bit38L = 137438953472L;
    public static final long Bit39L = 274877906944L;
    public static final int Bit4 = 8;
    public static final long Bit40L = 549755813888L;
    public static final long Bit41L = 1099511627776L;
    public static final long Bit42L = 2199023255552L;
    public static final long Bit43L = 4398046511104L;
    public static final long Bit44L = 8796093022208L;
    public static final long Bit45L = 17592186044416L;
    public static final long Bit46L = 35184372088832L;
    public static final long Bit47L = 70368744177664L;
    public static final long Bit48L = 140737488355328L;
    public static final long Bit49L = 281474976710656L;
    public static final int Bit5 = 16;
    public static final long Bit50L = 562949953421312L;
    public static final long Bit51L = 1125899906842624L;
    public static final long Bit52L = 2251799813685248L;
    public static final long Bit53L = 4503599627370496L;
    public static final long Bit54L = 9007199254740992L;
    public static final long Bit55L = 18014398509481984L;
    public static final long Bit56L = 36028797018963968L;
    public static final long Bit57L = 72057594037927936L;
    public static final long Bit58L = 144115188075855872L;
    public static final long Bit59L = 288230376151711744L;
    public static final int Bit6 = 32;
    public static final long Bit60L = 576460752303423488L;
    public static final long Bit61L = 1152921504606846976L;
    public static final long Bit62L = 2305843009213693952L;
    public static final long Bit63L = 4611686018427387904L;
    public static final long Bit64L = Long.MIN_VALUE;
    public static final int Bit7 = 64;
    public static final int Bit8 = 128;
    public static final int Bit9 = 256;
    public static final int BlockExit = 536870912;
    public static final int CanBeStatic = 256;
    public static final int ContainsAssertion = 1;
    public static final int DepthMASK = 8160;
    public static final int DepthSHIFT = 5;
    public static final int DidResolve = 262144;
    public static final int DisableUnnecessaryCastCheck = 32;
    public static final int DiscardEnclosingInstance = 8192;
    public static final int DocumentedCasesOmitted = 1073741824;
    public static final int DocumentedFallthrough = 536870912;
    public static final int Empty = 262144;
    public static final int ErrorInSignature = 32;
    public static final int FirstAssignmentToLocal = 8;
    public static final int GenerateCheckcast = 64;
    public static final int HasAbstractMethods = 2048;
    public static final int HasAllMethodBodies = 16;
    public static final int HasBeenGenerated = 8192;
    public static final int HasBeenResolved = 16;
    public static final int HasFunctionalInterfaceTypes = 2097152;
    public static final int HasLocalType = 2;
    public static final int HasSyntaxErrors = 524288;
    public static final int HasTypeAnnotations = 1048576;
    public static final int INVOCATION_ARGUMENT_OK = 0;
    public static final int INVOCATION_ARGUMENT_UNCHECKED = 1;
    public static final int INVOCATION_ARGUMENT_WILDCARD = 2;
    public static final int IgnoreNoEffectAssignCheck = 536870912;
    public static final int IgnoreRawTypeCheck = 1073741824;
    public static final int InsideExpressionStatement = 16;
    public static final int InsideJavadoc = 32768;
    public static final int IsAnnotationDefaultValue = 1;
    public static final int IsAnonymousType = 512;
    public static final int IsAnySubRoutineEscaping = 536870912;
    public static final int IsArgument = 4;
    public static final int IsCapturedOuterLocal = 524288;
    public static final int IsCompoundAssigned = 65536;
    public static final int IsDefaultConstructor = 128;
    public static final int IsDiamond = 524288;
    public static final int IsElseIfStatement = 536870912;
    public static final int IsElseStatementUnreachable = 128;
    public static final int IsForeachElementVariable = 16;
    public static final int IsImplicitThis = 4;
    public static final int IsImplicitUnit = 1;
    public static final int IsLocalDeclarationReachable = 1073741824;
    public static final int IsLocalType = 256;
    public static final int IsMemberType = 1024;
    public static final int IsNonNull = 131072;
    public static final int IsReachable = Integer.MIN_VALUE;
    public static final int IsRecovered = 32;
    public static final int IsReturnedValue = 16;
    public static final int IsSecondaryType = 4096;
    public static final int IsStrictlyAssigned = 8192;
    public static final int IsSubRoutineEscaping = 16384;
    public static final int IsSuperType = 16;
    public static final int IsSynchronized = 1073741824;
    public static final int IsSynthetic = 64;
    public static final int IsThenStatementUnreachable = 256;
    public static final int IsTryBlockExiting = 536870912;
    public static final int IsTypeElided = 2;
    public static final int IsUnionType = 536870912;
    public static final int IsUsefulEmptyStatement = 1;
    public static final int IsVarArgs = 16384;
    public static final int LabelUsed = 64;
    public static final Argument[] NO_ARGUMENTS = new Argument[0];
    public static final int NeedFreeReturn = 64;
    public static final int NeedReceiverGenericCast = 262144;
    public static final int NeededScope = 536870912;
    public static final int OnDemand = 131072;
    public static final int OperatorMASK = 4032;
    public static final int OperatorSHIFT = 6;
    public static final int OverridingMethodWithSupercall = 16;
    public static final int ParenthesizedMASK = 534773760;
    public static final int ParenthesizedSHIFT = 21;
    public static final int ResolveJavadoc = 65536;
    public static final int RestrictiveFlagMASK = 7;
    public static final int ReturnTypeIDMASK = 15;
    public static final int ShadowsOuterLocal = 2097152;
    public static final int SuperAccess = 16384;
    public static final int ThenExit = 1073741824;
    public static final int Unchecked = 65536;
    public static final int UndocumentedEmptyBlock = 8;
    public static final int UnnecessaryCast = 16384;
    public static final int UnsafeCast = 128;
    public static final int Used = 2;
    public int bits = Integer.MIN_VALUE;
    public int sourceEnd;
    public int sourceStart;

    public void acceptPotentiallyCompatibleMethods(MethodBinding[] methodBindingArr) {
    }

    public boolean checkingPotentialCompatibility() {
        return false;
    }

    public ASTNode concreteStatement() {
        return this;
    }

    public boolean isImplicitThis() {
        return false;
    }

    public boolean isQualifiedSuper() {
        return false;
    }

    public boolean isSuper() {
        return false;
    }

    public boolean isThis() {
        return false;
    }

    public boolean isUnqualifiedSuper() {
        return false;
    }

    public abstract StringBuffer print(int i, StringBuffer stringBuffer);

    public boolean receiverIsImplicitThis() {
        return false;
    }

    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
    }

    private static int checkInvocationArgument(BlockScope blockScope, Expression expression, TypeBinding typeBinding, TypeBinding typeBinding2, TypeBinding typeBinding3) {
        expression.computeConversion(blockScope, typeBinding, typeBinding2);
        if (typeBinding2 != TypeBinding.NULL && typeBinding.kind() == 516 && ((WildcardBinding) typeBinding).boundKind != 2) {
            return 2;
        }
        if (!TypeBinding.notEquals(typeBinding2, typeBinding) || !typeBinding2.needsUncheckedConversion(typeBinding)) {
            return 0;
        }
        blockScope.problemReporter().unsafeTypeConversion(expression, typeBinding2, typeBinding);
        return 1;
    }

    /* JADX WARN: Code duplicated, block: B:106:0x01bd  */
    /* JADX WARN: Code duplicated, block: B:108:0x01cb  */
    /* JADX WARN: Code duplicated, block: B:127:0x0210  */
    /* JADX WARN: Code duplicated, block: B:130:0x0215  */
    /* JADX WARN: Code duplicated, block: B:132:0x0220  */
    /* JADX WARN: Code duplicated, block: B:134:0x0227 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:151:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:66:0x00f6 A[LOOP:1: B:64:0x00f3->B:66:0x00f6, LOOP_END] */
    /* JADX WARN: Multi-variable type inference failed */
    public static boolean checkInvocationArguments(BlockScope blockScope, Expression expression, TypeBinding typeBinding, MethodBinding methodBinding, Expression[] expressionArr, TypeBinding[] typeBindingArr, boolean z, InvocationSite invocationSite) {
        long j;
        int iCheckInvocationArgument;
        MethodBinding methodBinding2;
        TypeBinding typeBindingElementsType;
        TypeBinding typeBindingElementsType2;
        int i;
        boolean z2;
        long j2 = blockScope.compilerOptions().sourceLevel;
        boolean z3 = j2 >= ClassFileConstants.JDK1_7;
        TypeBinding[] typeBindingArr2 = methodBinding.parameters;
        int length = typeBindingArr2.length;
        boolean z4 = !methodBinding.isStatic() && !typeBinding.isUnboundWildcard() && methodBinding.declaringClass.isRawType() && methodBinding.hasSubstitutedParameters();
        boolean z5 = (methodBinding.tagBits & 256) != 0;
        MethodBinding methodBindingOriginal = (!z4 && (methodBinding instanceof ParameterizedGenericMethodBinding) && ((ParameterizedGenericMethodBinding) methodBinding).isRaw && methodBinding.hasSubstitutedParameters()) ? methodBinding.original() : null;
        if (expressionArr == null) {
            if (methodBinding.isVarargs()) {
                TypeBinding typeBindingElementsType3 = ((ArrayBinding) typeBindingArr2[length - 1]).elementsType();
                if (!typeBindingElementsType3.isReifiable() && (!z3 || (methodBinding.tagBits & 2251799813685248L) == 0)) {
                    blockScope.problemReporter().unsafeGenericArrayForVarargs(typeBindingElementsType3, (ASTNode) invocationSite);
                }
            }
            j = j2;
            iCheckInvocationArgument = 0;
        } else {
            if (methodBinding.isVarargs()) {
                int i2 = length - 1;
                int i3 = 0;
                iCheckInvocationArgument = 0;
                while (i3 < i2) {
                    long j3 = j2;
                    iCheckInvocationArgument |= checkInvocationArgument(blockScope, expressionArr[i3], typeBindingArr2[i3], typeBindingArr[i3], methodBindingOriginal == null ? null : methodBindingOriginal.parameters[i3]);
                    i3++;
                    j2 = j3;
                }
                int length2 = expressionArr.length;
                if (i2 <= length2) {
                    TypeBinding typeBinding2 = typeBindingArr2[i2];
                    if (length == length2 && typeBinding2.dimensions() == typeBindingArr[i2].dimensions()) {
                        j = j2;
                        typeBindingElementsType = typeBinding2;
                    } else {
                        typeBindingElementsType = ((ArrayBinding) typeBinding2).elementsType();
                        j = j2;
                        if (!typeBindingElementsType.isReifiable() && (!z3 || (methodBinding.tagBits & 2251799813685248L) == 0)) {
                            blockScope.problemReporter().unsafeGenericArrayForVarargs(typeBindingElementsType, (ASTNode) invocationSite);
                        }
                        if (methodBindingOriginal != null) {
                            typeBindingElementsType2 = ((ArrayBinding) methodBindingOriginal.parameters[i2]).elementsType();
                        }
                        for (i = i2; i < length2; i++) {
                            iCheckInvocationArgument |= checkInvocationArgument(blockScope, expressionArr[i], typeBindingElementsType, typeBindingArr[i], typeBindingElementsType2);
                        }
                    }
                    typeBindingElementsType2 = null;
                    while (i < length2) {
                        iCheckInvocationArgument |= checkInvocationArgument(blockScope, expressionArr[i], typeBindingElementsType, typeBindingArr[i], typeBindingElementsType2);
                    }
                } else {
                    j = j2;
                }
                if (length == length2) {
                    ArrayBinding arrayBinding = (ArrayBinding) typeBindingArr2[i2];
                    TypeBinding typeBinding3 = typeBindingArr[i2];
                    if (typeBinding3 == TypeBinding.NULL) {
                        if (!arrayBinding.leafComponentType().isBaseType() || arrayBinding.dimensions() != 1) {
                            blockScope.problemReporter().varargsArgumentNeedCast(methodBinding, typeBinding3, invocationSite);
                        }
                    } else {
                        int i4 = arrayBinding.dimensions;
                        int iDimensions = typeBinding3.dimensions();
                        if (i4 <= iDimensions) {
                            if (typeBinding3.leafComponentType().isBaseType()) {
                                iDimensions--;
                            }
                            if (arrayBinding.dimensions < iDimensions) {
                                blockScope.problemReporter().varargsArgumentNeedCast(methodBinding, typeBinding3, invocationSite);
                            } else if (arrayBinding.dimensions == iDimensions && TypeBinding.notEquals(typeBinding3, arrayBinding) && TypeBinding.notEquals(typeBinding3.leafComponentType().erasure(), arrayBinding.leafComponentType.erasure()) && typeBinding3.isCompatibleWith(arrayBinding.elementsType()) && typeBinding3.isCompatibleWith(arrayBinding)) {
                                blockScope.problemReporter().varargsArgumentNeedCast(methodBinding, typeBinding3, invocationSite);
                            }
                        }
                    }
                }
            } else {
                j = j2;
                int i5 = 0;
                iCheckInvocationArgument = 0;
                while (i5 < length) {
                    MethodBinding methodBinding3 = methodBindingOriginal;
                    iCheckInvocationArgument |= checkInvocationArgument(blockScope, expressionArr[i5], typeBindingArr2[i5], typeBindingArr[i5], methodBinding3 == null ? null : methodBinding3.parameters[i5]);
                    i5++;
                    methodBindingOriginal = methodBinding3;
                }
            }
            if (z) {
                methodBinding2 = methodBindingOriginal;
                CastExpression.checkNeedForArgumentCasts(blockScope, expression, typeBinding, methodBinding, expressionArr, typeBindingArr, invocationSite);
            }
            if ((iCheckInvocationArgument & 2) != 0) {
                blockScope.problemReporter().wildcardInvocation((ASTNode) invocationSite, typeBinding, methodBinding, typeBindingArr);
                return false;
            }
            if (methodBinding.isStatic() && !typeBinding.isUnboundWildcard() && methodBinding.declaringClass.isRawType() && methodBinding.hasSubstitutedParameters()) {
                if (!blockScope.compilerOptions().reportUnavoidableGenericTypeProblems && expression != null && expression.forcedToBeRaw(blockScope.referenceContext())) {
                    return false;
                }
                blockScope.problemReporter().unsafeRawInvocation((ASTNode) invocationSite, methodBinding);
                return false;
            }
            if (methodBinding2 == null || z5) {
                z2 = true;
            } else {
                z2 = true;
                if ((iCheckInvocationArgument & 1) == 0) {
                    return false;
                }
            }
            if (methodBinding instanceof ParameterizedGenericMethodBinding) {
                blockScope.problemReporter().unsafeRawGenericMethodInvocation((ASTNode) invocationSite, methodBinding, typeBindingArr);
                return z2;
            }
            if (j >= ClassFileConstants.JDK1_8) {
                return z2;
            }
            return false;
        }
        methodBinding2 = methodBindingOriginal;
        if ((iCheckInvocationArgument & 2) != 0) {
            blockScope.problemReporter().wildcardInvocation((ASTNode) invocationSite, typeBinding, methodBinding, typeBindingArr);
            return false;
        }
        if (methodBinding.isStatic()) {
        }
        if (methodBinding2 == null) {
            z2 = true;
        } else {
            z2 = true;
        }
        if (methodBinding instanceof ParameterizedGenericMethodBinding) {
            blockScope.problemReporter().unsafeRawGenericMethodInvocation((ASTNode) invocationSite, methodBinding, typeBindingArr);
            return z2;
        }
        if (j >= ClassFileConstants.JDK1_8) {
            return z2;
        }
        return false;
    }

    public final boolean isFieldUseDeprecated(FieldBinding fieldBinding, Scope scope, int i) {
        AccessRestriction accessRestriction;
        if ((this.bits & 32768) == 0 && (i & 8192) == 0 && fieldBinding.isOrEnclosedByPrivateType() && !scope.isDefinedInField(fieldBinding)) {
            if ((i & 65536) != 0) {
                fieldBinding.original().compoundUseFlag++;
            } else {
                fieldBinding.original().modifiers |= 134217728;
            }
        }
        if ((fieldBinding.modifiers & 262144) != 0 && (accessRestriction = scope.environment().getAccessRestriction(fieldBinding.declaringClass.erasure())) != null) {
            scope.problemReporter().forbiddenReference(fieldBinding, this, accessRestriction.classpathEntryType, accessRestriction.classpathEntryName, accessRestriction.getProblemId());
        }
        if (fieldBinding.isViewedAsDeprecated() && !scope.isDefinedInSameUnit(fieldBinding.declaringClass)) {
            return scope.compilerOptions().reportDeprecationInsideDeprecatedCode || !scope.isInsideDeprecatedCode();
        }
        return false;
    }

    public final boolean isMethodUseDeprecated(MethodBinding methodBinding, Scope scope, boolean z) {
        AccessRestriction accessRestriction;
        if ((this.bits & 32768) == 0 && methodBinding.isOrEnclosedByPrivateType() && !scope.isDefinedInMethod(methodBinding)) {
            methodBinding.original().modifiers |= 134217728;
        }
        if (z && (methodBinding.modifiers & 262144) != 0 && (accessRestriction = scope.environment().getAccessRestriction(methodBinding.declaringClass.erasure())) != null) {
            scope.problemReporter().forbiddenReference(methodBinding, this, accessRestriction.classpathEntryType, accessRestriction.classpathEntryName, accessRestriction.getProblemId());
        }
        if (!methodBinding.isViewedAsDeprecated() || scope.isDefinedInSameUnit(methodBinding.declaringClass)) {
            return false;
        }
        if (z || (methodBinding.modifiers & 1048576) != 0) {
            return scope.compilerOptions().reportDeprecationInsideDeprecatedCode || !scope.isInsideDeprecatedCode();
        }
        return false;
    }

    public final boolean isTypeUseDeprecated(TypeBinding typeBinding, Scope scope) {
        AccessRestriction accessRestriction;
        if (typeBinding.isArrayType()) {
            typeBinding = ((ArrayBinding) typeBinding).leafComponentType;
        }
        TypeBinding typeBinding2 = typeBinding;
        if (typeBinding2.isBaseType()) {
            return false;
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) typeBinding2;
        if ((this.bits & 32768) == 0 && (referenceBinding instanceof TypeVariableBinding)) {
            referenceBinding.modifiers |= 134217728;
        }
        if ((this.bits & 32768) == 0 && referenceBinding.isOrEnclosedByPrivateType() && !scope.isDefinedInType(referenceBinding)) {
            ((ReferenceBinding) referenceBinding.erasure()).modifiers |= 134217728;
        }
        if (referenceBinding.hasRestrictedAccess() && (accessRestriction = scope.environment().getAccessRestriction(typeBinding2.erasure())) != null) {
            scope.problemReporter().forbiddenReference(typeBinding2, this, accessRestriction.classpathEntryType, accessRestriction.classpathEntryName, accessRestriction.getProblemId());
        }
        referenceBinding.initializeDeprecatedAnnotationTagBits();
        if (referenceBinding.isViewedAsDeprecated() && !scope.isDefinedInSameUnit(referenceBinding)) {
            return scope.compilerOptions().reportDeprecationInsideDeprecatedCode || !scope.isInsideDeprecatedCode();
        }
        return false;
    }

    public static StringBuffer printAnnotations(Annotation[] annotationArr, StringBuffer stringBuffer) {
        int length = annotationArr.length;
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                stringBuffer.append(" ");
            }
            Annotation annotation = annotationArr[i];
            if (annotation != null) {
                annotation.print(0, stringBuffer);
            } else {
                stringBuffer.append(MangleConstant.Q_MARK);
            }
        }
        return stringBuffer;
    }

    public static StringBuffer printIndent(int i, StringBuffer stringBuffer) {
        while (i > 0) {
            stringBuffer.append("  ");
            i--;
        }
        return stringBuffer;
    }

    public static StringBuffer printModifiers(int i, StringBuffer stringBuffer) {
        if ((i & 1) != 0) {
            stringBuffer.append("public ");
        }
        if ((i & 2) != 0) {
            stringBuffer.append("private ");
        }
        if ((i & 4) != 0) {
            stringBuffer.append("protected ");
        }
        if ((i & 8) != 0) {
            stringBuffer.append("static ");
        }
        if ((i & 16) != 0) {
            stringBuffer.append("final ");
        }
        if ((i & 32) != 0) {
            stringBuffer.append("synchronized ");
        }
        if ((i & 64) != 0) {
            stringBuffer.append("volatile ");
        }
        if ((i & 128) != 0) {
            stringBuffer.append("transient ");
        }
        if ((i & 256) != 0) {
            stringBuffer.append("native ");
        }
        if ((i & 1024) != 0) {
            stringBuffer.append("abstract ");
        }
        if ((i & 65536) != 0) {
            stringBuffer.append("default ");
        }
        return stringBuffer;
    }

    public static void resolvePolyExpressionArguments(Invocation invocation, MethodBinding methodBinding, TypeBinding[] typeBindingArr, BlockScope blockScope) {
        TypeBinding typeBinding;
        TypeBinding typeBindingResolveType;
        MethodBinding methodBinding2 = methodBinding.isValidBinding() ? methodBinding : methodBinding instanceof ProblemMethodBinding ? ((ProblemMethodBinding) methodBinding).closestMatch : null;
        if (methodBinding2 == null) {
            return;
        }
        boolean zIsVarargs = methodBinding2.isVarargs();
        TypeBinding[] typeBindingArr2 = methodBinding2.parameters;
        Expression[] expressionArrArguments = invocation.arguments();
        if (zIsVarargs && expressionArrArguments != null && typeBindingArr2.length == expressionArrArguments.length && expressionArrArguments[expressionArrArguments.length - 1].isCompatibleWith(typeBindingArr2[typeBindingArr2.length - 1], blockScope)) {
            zIsVarargs = false;
        }
        int length = expressionArrArguments == null ? 0 : expressionArrArguments.length;
        for (int i = 0; i < length; i++) {
            Expression expression = expressionArrArguments[i];
            TypeBinding parameter = InferenceContext18.getParameter(typeBindingArr2, i, zIsVarargs);
            if (parameter != null && (typeBinding = typeBindingArr[i]) != null && typeBinding.isPolyType()) {
                expression.setExpectedType(parameter);
                if (expression instanceof LambdaExpression) {
                    LambdaExpression lambdaExpression = (LambdaExpression) expression;
                    typeBindingResolveType = lambdaExpression.resolveType(blockScope, methodBinding.problemId() == 3);
                    if (!lambdaExpression.isCompatibleWith(parameter, blockScope) || lambdaExpression.hasErrors()) {
                    }
                } else {
                    typeBindingResolveType = expression.resolveType(blockScope);
                }
                if (typeBindingResolveType != null && typeBindingResolveType.kind() != 65540) {
                    typeBindingArr[i] = typeBindingResolveType;
                }
            }
        }
    }

    public static void resolveAnnotations(BlockScope blockScope, Annotation[] annotationArr, Binding binding) {
        resolveAnnotations(blockScope, annotationArr, binding, false);
        if (binding instanceof SourceTypeBinding) {
            ((SourceTypeBinding) binding).evaluateNullAnnotations();
        }
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0041  */
    /* JADX WARN: Code duplicated, block: B:38:0x007f A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:39:0x0080  */
    /* JADX WARN: Code duplicated, block: B:41:0x0087  */
    /* JADX WARN: Code duplicated, block: B:56:0x00ce A[PHI: r4
      0x00ce: PHI (r4v2 int) = (r4v1 int), (r4v1 int), (r4v1 int), (r4v10 int) binds: [B:7:0x0012, B:53:0x00c5, B:47:0x00a8, B:42:0x0090] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v5, types: [org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding] */
    /* JADX WARN: Type inference failed for: r11v8, types: [org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding] */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1, types: [org.eclipse.jdt.internal.compiler.ast.ContainerAnnotation] */
    /* JADX WARN: Type inference failed for: r14v12 */
    /* JADX WARN: Type inference failed for: r14v13 */
    /* JADX WARN: Type inference failed for: r14v14 */
    /* JADX WARN: Type inference failed for: r14v15 */
    /* JADX WARN: Type inference failed for: r14v16 */
    /* JADX WARN: Type inference failed for: r14v17 */
    /* JADX WARN: Type inference failed for: r14v2 */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r14v4 */
    /* JADX WARN: Type inference failed for: r14v5, types: [org.eclipse.jdt.internal.compiler.ast.ContainerAnnotation] */
    /* JADX WARN: Type inference failed for: r14v8 */
    /* JADX WARN: Type inference failed for: r6v3, types: [org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v4, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v1, types: [java.util.Map] */
    /* JADX WARN: Type inference failed for: r8v15 */
    /* JADX WARN: Type inference failed for: r8v16 */
    /* JADX WARN: Type inference failed for: r8v17 */
    /* JADX WARN: Type inference failed for: r8v18 */
    /* JADX WARN: Type inference failed for: r8v19 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v20 */
    /* JADX WARN: Type inference failed for: r8v21 */
    /* JADX WARN: Type inference failed for: r8v22 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v7, types: [java.util.Map] */
    public static AnnotationBinding[] resolveAnnotations(BlockScope blockScope, Annotation[] annotationArr, Binding binding, boolean z) {
        AnnotationBinding[] annotationBindingArr;
        ?? r14;
        ReferenceBinding annotationType;
        AnnotationBinding[] annotationBindingArr2;
        ReferenceBinding referenceBinding;
        int i;
        AnnotationBinding[] annotationBindingArr3;
        int length = annotationArr == null ? 0 : annotationArr.length;
        ReferenceBinding referenceBinding2 = null;
        if (binding == null) {
            annotationBindingArr = null;
        } else {
            int iKind = binding.kind();
            if (iKind != 1) {
                if (iKind == 2) {
                    LocalVariableBinding localVariableBinding = (LocalVariableBinding) binding;
                    if ((localVariableBinding.tagBits & 8589934592L) != 0) {
                        return null;
                    }
                    localVariableBinding.tagBits |= 25769803776L;
                    if (length > 0) {
                        annotationBindingArr2 = new AnnotationBinding[length];
                        localVariableBinding.setAnnotations(annotationBindingArr2, blockScope);
                        annotationBindingArr = annotationBindingArr2;
                    }
                } else if (iKind == 4) {
                    referenceBinding = (ReferenceBinding) binding;
                    i = length;
                    if ((referenceBinding.tagBits & 8589934592L) != 0) {
                        return null;
                    }
                    referenceBinding.tagBits |= 25769803776L;
                    if (i > 0) {
                        length = i;
                        AnnotationBinding[] annotationBindingArr4 = new AnnotationBinding[length];
                        referenceBinding.setAnnotations(annotationBindingArr4);
                        annotationBindingArr = annotationBindingArr4;
                    } else {
                        length = i;
                    }
                } else if (iKind != 8) {
                    if (iKind == 16) {
                        PackageBinding packageBinding = (PackageBinding) binding;
                        i = length;
                        if ((packageBinding.tagBits & 8589934592L) != 0) {
                            return null;
                        }
                        packageBinding.tagBits |= 25769803776L;
                    } else if (iKind == 2052) {
                        referenceBinding = (ReferenceBinding) binding;
                        i = length;
                        if ((referenceBinding.tagBits & 8589934592L) != 0) {
                            return null;
                        }
                        referenceBinding.tagBits |= 25769803776L;
                        if (i > 0) {
                            length = i;
                            AnnotationBinding[] annotationBindingArr5 = new AnnotationBinding[length];
                            referenceBinding.setAnnotations(annotationBindingArr5);
                            annotationBindingArr = annotationBindingArr5;
                        }
                    } else {
                        if (iKind != 4100 && iKind != 16388) {
                            return null;
                        }
                        annotationBindingArr3 = new AnnotationBinding[length];
                        annotationBindingArr = annotationBindingArr3;
                    }
                    length = i;
                } else {
                    i = length;
                    MethodBinding methodBinding = (MethodBinding) binding;
                    if ((methodBinding.tagBits & 8589934592L) != 0) {
                        return null;
                    }
                    methodBinding.tagBits |= 25769803776L;
                    if (i > 0) {
                        annotationBindingArr3 = new AnnotationBinding[i];
                        methodBinding.setAnnotations(annotationBindingArr3);
                        length = i;
                        annotationBindingArr = annotationBindingArr3;
                    }
                    length = i;
                }
                annotationBindingArr = null;
            } else {
                FieldBinding fieldBinding = (FieldBinding) binding;
                if ((fieldBinding.tagBits & 8589934592L) != 0) {
                    return null;
                }
                fieldBinding.tagBits |= 25769803776L;
                if (length > 0) {
                    annotationBindingArr2 = new AnnotationBinding[length];
                    fieldBinding.setAnnotations(annotationBindingArr2);
                    annotationBindingArr = annotationBindingArr2;
                } else {
                    annotationBindingArr = null;
                }
            }
        }
        if (annotationArr == null) {
            return annotationBindingArr;
        }
        int i2 = 0;
        while (i2 < length) {
            int i3 = 0;
            Annotation annotation = annotationArr[i2];
            Binding binding2 = annotation.recipient;
            if (binding2 != null && binding != null) {
                int iKind2 = binding.kind();
                if (iKind2 == 1) {
                    ((FieldBinding) binding).tagBits = ((FieldBinding) binding2).tagBits;
                    if (annotationBindingArr != null) {
                        while (i3 < length) {
                            annotationBindingArr[i3] = annotationArr[i3].getCompilerAnnotation();
                            i3++;
                        }
                    }
                } else if (iKind2 == 2) {
                    LocalVariableBinding localVariableBinding2 = (LocalVariableBinding) binding;
                    long j = ((LocalVariableBinding) binding2).tagBits;
                    localVariableBinding2.tagBits = j;
                    if ((j & 1125899906842624L) == 0) {
                        if (annotationBindingArr != null) {
                            while (i3 < length) {
                                annotationBindingArr[i3] = annotationArr[i3].getCompilerAnnotation();
                                i3++;
                            }
                        }
                    } else if (annotationBindingArr != null) {
                        LocalDeclaration localDeclaration = localVariableBinding2.declaration;
                        int i4 = localDeclaration.declarationSourceEnd;
                        int i5 = localDeclaration.declarationSourceStart;
                        while (i3 < length) {
                            Annotation annotation2 = annotationArr[i3];
                            AnnotationBinding compilerAnnotation = annotation2.getCompilerAnnotation();
                            annotationBindingArr[i3] = compilerAnnotation;
                            if (compilerAnnotation != null && (annotationType = compilerAnnotation.getAnnotationType()) != null && annotationType.f626id == 49) {
                                annotation2.recordSuppressWarnings(blockScope, i5, i4, blockScope.compilerOptions().suppressWarnings);
                            }
                            i3++;
                        }
                    }
                } else if (iKind2 == 16388 && annotationBindingArr != null) {
                    while (i3 < length) {
                        annotationBindingArr[i3] = annotationArr[i3].getCompilerAnnotation();
                        i3++;
                    }
                }
                return annotationBindingArr;
            }
            annotation.recipient = binding;
            annotation.resolveType(blockScope);
            if (annotationBindingArr != null) {
                annotationBindingArr[i2] = annotation.getCompilerAnnotation();
            }
            i2++;
            referenceBinding2 = null;
        }
        if (binding != null && binding.isTaggedRepeatable()) {
            for (int i6 = 0; i6 < length; i6++) {
                Annotation annotation3 = annotationArr[i6];
                AnnotationBinding annotationBinding = annotationBindingArr[i6];
                ReferenceBinding annotationType2 = annotationBinding != null ? annotationBinding.getAnnotationType() : referenceBinding2;
                if (annotationType2 != null && annotationType2.f626id == 90) {
                    annotation3.checkRepeatableMetaAnnotation(blockScope);
                }
            }
        }
        if (annotationBindingArr != null && length > 1) {
            ?? r7 = annotationBindingArr;
            ?? map = referenceBinding2;
            int i7 = 0;
            while (i7 < length) {
                ?? r11 = r7[i7];
                if (r11 != null) {
                    ReferenceBinding annotationType3 = r11.getAnnotationType();
                    ?? r15 = referenceBinding2;
                    int i8 = i7 + 1;
                    boolean z2 = false;
                    while (i8 < length) {
                        ?? r12 = r7[i8];
                        if (r12 != null && TypeBinding.equalsEquals(r12.getAnnotationType(), annotationType3)) {
                            if (r7 == annotationBindingArr) {
                                AnnotationBinding[] annotationBindingArr6 = new AnnotationBinding[length];
                                System.arraycopy(r7, 0, annotationBindingArr6, 0, length);
                                r7 = annotationBindingArr6;
                            }
                            r7[i8] = referenceBinding2;
                            if (annotationType3.isRepeatableAnnotationType()) {
                                Annotation persistibleAnnotation = annotationArr[i7].getPersistibleAnnotation();
                                if (persistibleAnnotation instanceof ContainerAnnotation) {
                                    r14 = r15;
                                    r14 = (ContainerAnnotation) persistibleAnnotation;
                                }
                                if (r14 == 0) {
                                    ReferenceBinding referenceBindingContainerAnnotationType = annotationType3.containerAnnotationType();
                                    ContainerAnnotation containerAnnotation = new ContainerAnnotation(annotationArr[i7], referenceBindingContainerAnnotationType, blockScope);
                                    map = map;
                                    if (map == 0) {
                                        map = new HashMap(3);
                                    }
                                    map.put(referenceBindingContainerAnnotationType, annotationArr[i7]);
                                    Annotation.checkForInstancesOfRepeatableWithRepeatingContainerAnnotation(blockScope, annotationType3, annotationArr);
                                    r14 = containerAnnotation;
                                }
                                r14.addContainee(annotationArr[i8]);
                                map = map;
                                r15 = r14;
                                map = map;
                                map = map;
                                r15 = r15;
                                map = map;
                                r15 = r15;
                                map = map;
                                map = map;
                                r15 = r15;
                            } else {
                                blockScope.problemReporter().duplicateAnnotation(annotationArr[i8], blockScope.compilerOptions().sourceLevel);
                                z2 = true;
                            }
                        } else {
                            map = map;
                            map = map;
                            r15 = r15;
                            map = map;
                            r15 = r15;
                            map = map;
                            map = map;
                            r15 = r15;
                        }
                        i8++;
                        annotationType3 = annotationType3;
                        referenceBinding2 = null;
                        map = map;
                        r15 = r15;
                    }
                    if (r15 != 0) {
                        map = map;
                        r15.resolveType(blockScope);
                    }
                    if (z2) {
                        blockScope.problemReporter().duplicateAnnotation(annotationArr[i7], blockScope.compilerOptions().sourceLevel);
                    }
                }
                i7++;
                map = map;
            }
            if (map != 0) {
                for (int i9 = 0; i9 < length; i9++) {
                    ?? r6 = r7[i9];
                    if (r6 != null) {
                        Annotation annotation4 = annotationArr[i9];
                        ReferenceBinding annotationType4 = r6.getAnnotationType();
                        if (map.containsKey(annotationType4)) {
                            blockScope.problemReporter().repeatedAnnotationWithContainer((Annotation) map.get(annotationType4), annotation4);
                        }
                    }
                }
            }
        }
        if (z) {
            copySE8AnnotationsToType(blockScope, binding, annotationArr, false);
        }
        return annotationBindingArr;
    }

    public static TypeBinding resolveAnnotations(BlockScope blockScope, Annotation[][] annotationArr, TypeBinding typeBinding) {
        int length = annotationArr == null ? 0 : annotationArr.length;
        if (typeBinding == null || length == 0) {
            return typeBinding;
        }
        AnnotationBinding[][] annotationBindingArr = new AnnotationBinding[length][];
        for (int i = 0; i < length; i++) {
            Annotation[] annotationArr2 = annotationArr[i];
            if (annotationArr2 != null && annotationArr2.length > 0) {
                annotationBindingArr[i] = resolveAnnotations(blockScope, annotationArr2, TypeBinding.TYPE_USE_BINDING, false);
            }
        }
        return blockScope.environment().createAnnotatedType(typeBinding, annotationBindingArr);
    }

    public static void copySE8AnnotationsToType(BlockScope blockScope, Binding binding, Annotation[] annotationArr, boolean z) {
        long j;
        long j2;
        if (annotationArr == null || annotationArr.length == 0 || binding == null) {
            return;
        }
        int iKind = binding.kind();
        int i = 8;
        int i2 = 2;
        int i3 = 1;
        if (iKind == 1) {
            j = 137438953472L;
        } else if (iKind == 2) {
            j = binding.isParameter() ? 549755813888L : 2199023255552L;
        } else if (iKind != 8) {
            return;
        } else {
            j = ((MethodBinding) binding).isConstructor() ? 1099511627776L : 274877906944L;
        }
        long j3 = j;
        int length = annotationArr.length;
        int i4 = -1;
        AnnotationBinding[] annotationBindingArr = null;
        int i5 = -1;
        Annotation annotation = null;
        int i6 = 0;
        int i7 = 0;
        long j4 = 0;
        while (i6 < length) {
            long j5 = j4;
            AnnotationBinding compilerAnnotation = annotationArr[i6].getCompilerAnnotation();
            if (compilerAnnotation != null) {
                ReferenceBinding annotationType = compilerAnnotation.getAnnotationType();
                long annotationTagBits = annotationType.getAnnotationTagBits();
                if ((annotationTagBits & 9007199254740992L) != 0) {
                    if (!z) {
                        int i8 = i5;
                        i5 = i8 == i4 ? i6 : i8;
                        if (annotationBindingArr == null) {
                            AnnotationBinding[] annotationBindingArr2 = new AnnotationBinding[i3];
                            annotationBindingArr2[0] = compilerAnnotation;
                            annotationBindingArr = annotationBindingArr2;
                        } else {
                            int i9 = i7;
                            i3 = i9 + 1;
                            AnnotationBinding[] annotationBindingArr3 = new AnnotationBinding[i3];
                            System.arraycopy(annotationBindingArr, 0, annotationBindingArr3, 0, i9);
                            annotationBindingArr3[i9] = compilerAnnotation;
                            annotationBindingArr = annotationBindingArr3;
                        }
                        if (annotationType.hasNullBit(32)) {
                            j2 = 72057594037927936L | j5;
                            annotation = annotationArr[i6];
                        } else if (annotationType.hasNullBit(64)) {
                            j2 = 36028797018963968L | j5;
                            annotation = annotationArr[i6];
                        } else {
                            j2 = j5;
                        }
                    } else if ((annotationTagBits & j3) == 0) {
                        ProblemReporter problemReporter = blockScope.problemReporter();
                        Annotation annotation2 = annotationArr[i6];
                        problemReporter.misplacedTypeAnnotations(annotation2, annotation2);
                    }
                    i6++;
                    j4 = j2;
                    i7 = i3;
                    i = 8;
                    i2 = 2;
                    i3 = 1;
                    i4 = -1;
                }
            }
            i3 = i7;
            i5 = i5;
            j2 = j5;
            i6++;
            j4 = j2;
            i7 = i3;
            i = 8;
            i2 = 2;
            i3 = 1;
            i4 = -1;
        }
        if (annotationBindingArr != null) {
            int iKind2 = binding.kind();
            if (iKind2 == i3) {
                long j6 = j4;
                FieldBinding fieldBinding = (FieldBinding) binding;
                FieldDeclaration fieldDeclarationDeclarationOf = ((SourceTypeBinding) fieldBinding.declaringClass).scope.referenceContext.declarationOf(fieldBinding);
                if (Annotation.isTypeUseCompatible(fieldDeclarationDeclarationOf.type, blockScope)) {
                    fieldDeclarationDeclarationOf.bits |= 1048576;
                    TypeReference typeReference = fieldDeclarationDeclarationOf.type;
                    typeReference.bits = 1048576 | typeReference.bits;
                    fieldBinding.type = mergeAnnotationsIntoType(blockScope, annotationBindingArr, j6, annotation, fieldDeclarationDeclarationOf.type, fieldBinding.type);
                    if (blockScope.environment().usesNullTypeAnnotations()) {
                        fieldBinding.tagBits &= ~j6;
                    }
                }
            } else if (iKind2 == i2) {
                long j7 = j4;
                LocalVariableBinding localVariableBinding = (LocalVariableBinding) binding;
                TypeReference typeReference2 = localVariableBinding.declaration.type;
                if (Annotation.isTypeUseCompatible(typeReference2, blockScope)) {
                    localVariableBinding.declaration.bits |= 1048576;
                    typeReference2.bits |= 1048576;
                    localVariableBinding.type = mergeAnnotationsIntoType(blockScope, annotationBindingArr, j7, annotation, typeReference2, localVariableBinding.type);
                    if (blockScope.environment().usesNullTypeAnnotations()) {
                        localVariableBinding.tagBits &= ~j7;
                    }
                }
            } else if (iKind2 == i) {
                MethodBinding methodBinding = (MethodBinding) binding;
                if (!methodBinding.isConstructor()) {
                    MethodDeclaration methodDeclaration = (MethodDeclaration) ((SourceTypeBinding) methodBinding.declaringClass).scope.referenceContext.declarationOf(methodBinding);
                    if (Annotation.isTypeUseCompatible(methodDeclaration.returnType, blockScope)) {
                        methodDeclaration.bits |= 1048576;
                        TypeReference typeReference3 = methodDeclaration.returnType;
                        typeReference3.bits = 1048576 | typeReference3.bits;
                        methodBinding.returnType = mergeAnnotationsIntoType(blockScope, annotationBindingArr, j4, annotation, methodDeclaration.returnType, methodBinding.returnType);
                        if (blockScope.environment().usesNullTypeAnnotations()) {
                            methodBinding.tagBits &= ~j4;
                        }
                    }
                } else {
                    methodBinding.setTypeAnnotations(annotationBindingArr);
                }
            }
            AnnotationBinding[] annotations = binding.getAnnotations();
            int length2 = annotations == null ? 0 : annotations.length;
            int i10 = 0;
            for (int i11 = 0; i11 < length2; i11++) {
                AnnotationBinding annotationBinding = annotations[i11];
                if (annotationBinding != null) {
                    long annotationTagBits2 = annotationBinding.getAnnotationType().getAnnotationTagBits() & TagBits.AnnotationTargetMASK;
                    if (annotationTagBits2 == 0 || (annotationTagBits2 & j3) != 0) {
                        annotations[i10] = annotationBinding;
                        i10++;
                    }
                }
            }
            if (i10 != length2) {
                AnnotationBinding[] annotationBindingArr4 = new AnnotationBinding[i10];
                System.arraycopy(annotations, 0, annotationBindingArr4, 0, i10);
                binding.setAnnotations(annotationBindingArr4, blockScope);
            }
        }
    }

    private static TypeBinding mergeAnnotationsIntoType(BlockScope blockScope, AnnotationBinding[] annotationBindingArr, long j, Annotation annotation, TypeReference typeReference, TypeBinding typeBinding) {
        if (typeBinding == null || !typeBinding.isValidBinding()) {
            return typeBinding;
        }
        TypeReference typeReference2 = typeReference.isUnionType() ? ((UnionTypeReference) typeReference).typeReferences[0] : null;
        TypeBinding typeBindingLeafComponentType = typeReference2 == null ? typeBinding.leafComponentType() : typeReference2.resolvedType;
        if (j != 0 && typeBindingLeafComponentType.isBaseType()) {
            blockScope.problemReporter().illegalAnnotationForBaseType(typeReference, new Annotation[]{annotation}, j);
            return typeBinding;
        }
        long j2 = typeBindingLeafComponentType.tagBits & TagBits.AnnotationNullMASK;
        if ((j2 | j) == TagBits.AnnotationNullMASK) {
            if (!(typeBindingLeafComponentType instanceof TypeVariableBinding)) {
                if (j2 != TagBits.AnnotationNullMASK && j != TagBits.AnnotationNullMASK) {
                    blockScope.problemReporter().contradictoryNullAnnotations(annotation);
                }
                annotationBindingArr = Binding.NO_ANNOTATIONS;
            }
            typeBindingLeafComponentType = typeBindingLeafComponentType.withoutToplevelNullAnnotation();
        }
        AnnotationBinding[][] annotationBindingArr2 = new AnnotationBinding[typeReference.getAnnotatableLevels()][];
        annotationBindingArr2[0] = annotationBindingArr;
        TypeBinding typeBindingCreateAnnotatedType = blockScope.environment().createAnnotatedType(typeBindingLeafComponentType, annotationBindingArr2);
        if (typeReference2 == null) {
            if (typeBinding.isArrayType()) {
                typeBindingCreateAnnotatedType = blockScope.environment().createArrayType(typeBindingCreateAnnotatedType, typeBinding.dimensions(), typeBinding.getTypeAnnotations());
            }
            typeReference.resolvedType = typeBindingCreateAnnotatedType;
        } else {
            typeReference2.resolvedType = typeBindingCreateAnnotatedType;
            typeReference2.bits |= 1048576;
        }
        return typeReference.resolvedType;
    }

    /* JADX WARN: Code duplicated, block: B:24:0x004f A[RETURN] */
    public static void resolveDeprecatedAnnotations(BlockScope blockScope, Annotation[] annotationArr, Binding binding) {
        int length;
        TypeBinding typeBindingResolveType;
        if (binding != null) {
            int iKind = binding.kind();
            if (annotationArr != null && (length = annotationArr.length) >= 0) {
                if (iKind != 1) {
                    if (iKind != 2) {
                        if (iKind == 4) {
                            if ((((ReferenceBinding) binding).tagBits & 17179869184L) != 0) {
                                return;
                            }
                        } else if (iKind != 8) {
                            if (iKind != 16) {
                                if (iKind != 2052) {
                                    return;
                                }
                                if ((((ReferenceBinding) binding).tagBits & 17179869184L) != 0) {
                                    return;
                                }
                            } else if ((((PackageBinding) binding).tagBits & 17179869184L) != 0) {
                                return;
                            }
                        } else if ((((MethodBinding) binding).tagBits & 17179869184L) != 0) {
                            return;
                        }
                    } else if ((((LocalVariableBinding) binding).tagBits & 17179869184L) != 0) {
                        return;
                    }
                } else if ((((FieldBinding) binding).tagBits & 17179869184L) != 0) {
                    return;
                }
                for (int i = 0; i < length; i++) {
                    if (CharOperation.equals(TypeConstants.JAVA_LANG_DEPRECATED[2], annotationArr[i].type.getLastToken()) && (typeBindingResolveType = annotationArr[i].type.resolveType(blockScope)) != null && typeBindingResolveType.isValidBinding() && typeBindingResolveType.f626id == 44) {
                        if (iKind == 1) {
                            ((FieldBinding) binding).tagBits |= 70385924046848L;
                            return;
                        }
                        if (iKind != 2) {
                            if (iKind != 4) {
                                if (iKind == 8) {
                                    ((MethodBinding) binding).tagBits |= 70385924046848L;
                                    return;
                                } else if (iKind == 16) {
                                    ((PackageBinding) binding).tagBits |= 70385924046848L;
                                    return;
                                } else if (iKind != 2052 && iKind != 4100) {
                                    return;
                                }
                            }
                            ((ReferenceBinding) binding).tagBits |= 70385924046848L;
                            return;
                        }
                        ((LocalVariableBinding) binding).tagBits |= 70385924046848L;
                        return;
                    }
                }
            }
            if (iKind == 1) {
                ((FieldBinding) binding).tagBits |= 17179869184L;
                return;
            }
            if (iKind != 2) {
                if (iKind != 4) {
                    if (iKind == 8) {
                        ((MethodBinding) binding).tagBits |= 17179869184L;
                        return;
                    } else if (iKind == 16) {
                        ((PackageBinding) binding).tagBits |= 17179869184L;
                        return;
                    } else if (iKind != 2052 && iKind != 4100) {
                        return;
                    }
                }
                ((ReferenceBinding) binding).tagBits |= 17179869184L;
                return;
            }
            ((LocalVariableBinding) binding).tagBits |= 17179869184L;
        }
    }

    public int sourceStart() {
        return this.sourceStart;
    }

    public int sourceEnd() {
        return this.sourceEnd;
    }

    public String toString() {
        return print(0, new StringBuffer(30)).toString();
    }
}
