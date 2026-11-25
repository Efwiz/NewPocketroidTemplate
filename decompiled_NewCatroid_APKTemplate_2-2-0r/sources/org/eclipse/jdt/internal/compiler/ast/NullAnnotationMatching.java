package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.BinaryTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.CaptureBinding;
import org.eclipse.jdt.internal.compiler.lookup.InvocationSite;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ProblemMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.RawTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.Substitution;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBindingVisitor;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.VariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.WildcardBinding;

/* JADX INFO: loaded from: classes14.dex */
public class NullAnnotationMatching {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$NullAnnotationMatching$CheckMode;
    public final int nullStatus;
    private final Severity severity;
    public final TypeBinding superTypeHint;
    public static final NullAnnotationMatching NULL_ANNOTATIONS_OK = new NullAnnotationMatching(Severity.OK, 1, null);
    public static final NullAnnotationMatching NULL_ANNOTATIONS_OK_NONNULL = new NullAnnotationMatching(Severity.OK, 4, null);
    public static final NullAnnotationMatching NULL_ANNOTATIONS_UNCHECKED = new NullAnnotationMatching(Severity.UNCHECKED, 1, null);
    public static final NullAnnotationMatching NULL_ANNOTATIONS_MISMATCH = new NullAnnotationMatching(Severity.MISMATCH, 1, null);

    public static long validNullTagBits(long j) {
        long j2 = j & TagBits.AnnotationNullMASK;
        if (j2 == TagBits.AnnotationNullMASK) {
            return 0L;
        }
        return j2;
    }

    public void report(Scope scope) {
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$NullAnnotationMatching$CheckMode() {
        int[] iArr = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$NullAnnotationMatching$CheckMode;
        if (iArr != null) {
            return iArr;
        }
        int[] iArr2 = new int[CheckMode.valuesCustom().length];
        try {
            iArr2[CheckMode.BOUND_CHECK.ordinal()] = 3;
        } catch (NoSuchFieldError unused) {
        }
        try {
            iArr2[CheckMode.BOUND_SUPER_CHECK.ordinal()] = 4;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            iArr2[CheckMode.COMPATIBLE.ordinal()] = 1;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            iArr2[CheckMode.EXACT.ordinal()] = 2;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            iArr2[CheckMode.OVERRIDE.ordinal()] = 6;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            iArr2[CheckMode.OVERRIDE_RETURN.ordinal()] = 5;
        } catch (NoSuchFieldError unused6) {
        }
        $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$NullAnnotationMatching$CheckMode = iArr2;
        return iArr2;
    }

    public enum CheckMode {
        COMPATIBLE { // from class: org.eclipse.jdt.internal.compiler.ast.NullAnnotationMatching.CheckMode.1
            @Override // org.eclipse.jdt.internal.compiler.ast.NullAnnotationMatching.CheckMode
            boolean requiredNullableMatchesAll() {
                return true;
            }
        },
        EXACT,
        BOUND_CHECK,
        BOUND_SUPER_CHECK,
        OVERRIDE_RETURN { // from class: org.eclipse.jdt.internal.compiler.ast.NullAnnotationMatching.CheckMode.2
            @Override // org.eclipse.jdt.internal.compiler.ast.NullAnnotationMatching.CheckMode
            CheckMode toDetail() {
                return OVERRIDE;
            }
        },
        OVERRIDE { // from class: org.eclipse.jdt.internal.compiler.ast.NullAnnotationMatching.CheckMode.3
            @Override // org.eclipse.jdt.internal.compiler.ast.NullAnnotationMatching.CheckMode
            boolean requiredNullableMatchesAll() {
                return true;
            }

            @Override // org.eclipse.jdt.internal.compiler.ast.NullAnnotationMatching.CheckMode
            CheckMode toDetail() {
                return OVERRIDE;
            }
        };

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static CheckMode[] valuesCustom() {
            CheckMode[] checkModeArrValuesCustom = values();
            int length = checkModeArrValuesCustom.length;
            CheckMode[] checkModeArr = new CheckMode[length];
            System.arraycopy(checkModeArrValuesCustom, 0, checkModeArr, 0, length);
            return checkModeArr;
        }

        boolean requiredNullableMatchesAll() {
            return false;
        }

        /* synthetic */ CheckMode(CheckMode checkMode) {
            this();
        }

        CheckMode toDetail() {
            return EXACT;
        }
    }

    private enum Severity {
        OK,
        LEGACY_WARNING,
        UNCHECKED,
        MISMATCH;

        /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
        public static Severity[] valuesCustom() {
            Severity[] severityArrValuesCustom = values();
            int length = severityArrValuesCustom.length;
            Severity[] severityArr = new Severity[length];
            System.arraycopy(severityArrValuesCustom, 0, severityArr, 0, length);
            return severityArr;
        }

        public Severity max(Severity severity) {
            return compareTo(severity) < 0 ? severity : this;
        }

        public boolean isAnyMismatch() {
            return compareTo(LEGACY_WARNING) > 0;
        }
    }

    NullAnnotationMatching(Severity severity, int i, TypeBinding typeBinding) {
        this.severity = severity;
        this.superTypeHint = typeBinding;
        this.nullStatus = i;
    }

    public boolean isAnyMismatch() {
        return this.severity.isAnyMismatch();
    }

    public boolean isUnchecked() {
        return this.severity == Severity.UNCHECKED;
    }

    public boolean isDefiniteMismatch() {
        return this.severity == Severity.MISMATCH;
    }

    public boolean wantToReport() {
        return this.severity == Severity.LEGACY_WARNING;
    }

    public boolean isPotentiallyNullMismatch() {
        int i;
        return (isDefiniteMismatch() || (i = this.nullStatus) == -1 || (i & 16) == 0) ? false : true;
    }

    public String superTypeHintName(CompilerOptions compilerOptions, boolean z) {
        return String.valueOf(this.superTypeHint.nullAnnotatedReadableName(compilerOptions, z));
    }

    public static int checkAssignment(BlockScope blockScope, FlowContext flowContext, VariableBinding variableBinding, FlowInfo flowInfo, int i, Expression expression, TypeBinding typeBinding) {
        long j;
        int i2;
        boolean z;
        int i3 = i;
        TypeBinding typeBinding2 = typeBinding;
        if (typeBinding2 == null) {
            return 1;
        }
        boolean zUsesNullTypeAnnotations = blockScope.environment().usesNullTypeAnnotations();
        if (!zUsesNullTypeAnnotations) {
            j = variableBinding.tagBits & TagBits.AnnotationNullMASK;
            i2 = 1;
        } else {
            if ((expression instanceof ConditionalExpression) && expression.isPolyExpression()) {
                ConditionalExpression conditionalExpression = (ConditionalExpression) expression;
                int iCheckAssignment = checkAssignment(blockScope, flowContext, variableBinding, flowInfo, conditionalExpression.ifTrueNullStatus, conditionalExpression.valueIfTrue, conditionalExpression.valueIfTrue.resolvedType);
                return iCheckAssignment == checkAssignment(blockScope, flowContext, variableBinding, flowInfo, conditionalExpression.ifFalseNullStatus, conditionalExpression.valueIfFalse, conditionalExpression.valueIfFalse.resolvedType) ? iCheckAssignment : i3;
            }
            j = variableBinding.type.tagBits & TagBits.AnnotationNullMASK;
            NullAnnotationMatching nullAnnotationMatchingAnalyse = analyse(variableBinding.type, typeBinding, null, null, i, expression, CheckMode.COMPATIBLE);
            if (nullAnnotationMatchingAnalyse.isAnyMismatch()) {
                i2 = 1;
                typeBinding2 = typeBinding2;
                i3 = i3;
                flowContext.recordNullityMismatch(blockScope, expression, typeBinding, variableBinding.type, flowInfo, i, nullAnnotationMatchingAnalyse);
                z = true;
            } else {
                i2 = 1;
                if (nullAnnotationMatchingAnalyse.wantToReport()) {
                    nullAnnotationMatchingAnalyse.report(blockScope);
                }
                int i4 = nullAnnotationMatchingAnalyse.nullStatus;
                if (i4 != 1) {
                    return i4;
                }
            }
            if (j != 72057594037927936L && i3 != 4) {
                if (!z) {
                    flowContext.recordNullityMismatch(blockScope, expression, typeBinding, variableBinding.type, flowInfo, i, null);
                }
                return 4;
            }
            if (j == 36028797018963968L || i3 != i2) {
                return i3;
            }
            return (zUsesNullTypeAnnotations && typeBinding.isTypeVariable() && (typeBinding2.tagBits & TagBits.AnnotationNullMASK) == 0) ? 48 : 24;
        }
        z = false;
        if (j != 72057594037927936L) {
        }
        if (j == 36028797018963968L) {
        }
        return i3;
    }

    public static NullAnnotationMatching analyse(TypeBinding typeBinding, TypeBinding typeBinding2, int i) {
        return analyse(typeBinding, typeBinding2, null, null, i, null, CheckMode.COMPATIBLE);
    }

    /* JADX WARN: Code duplicated, block: B:151:0x01fd  */
    /* JADX WARN: Code duplicated, block: B:83:0x011f  */
    public static NullAnnotationMatching analyse(TypeBinding typeBinding, TypeBinding typeBinding2, TypeBinding typeBinding3, Substitution substitution, int i, Expression expression, CheckMode checkMode) {
        TypeBinding typeBinding4;
        TypeBinding typeBinding5;
        TypeBinding typeBinding6;
        int i2;
        TypeBinding typeBinding7;
        TypeBinding typeBinding8;
        TypeBinding typeBindingSubstitute = typeBinding;
        int i3 = i;
        if (!typeBinding.enterRecursiveFunction()) {
            return NULL_ANNOTATIONS_OK;
        }
        try {
            Severity severityMax = Severity.OK;
            NullAnnotationMatching nullAnnotationMatchingOkNonNullStatus = NULL_ANNOTATIONS_OK;
            if (areSameTypes(typeBinding, typeBinding2, typeBinding3)) {
                if ((typeBindingSubstitute.tagBits & 72057594037927936L) == 0) {
                    typeBinding.exitRecursiveFunction();
                    return nullAnnotationMatchingOkNonNullStatus;
                }
                NullAnnotationMatching nullAnnotationMatchingOkNonNullStatus2 = okNonNullStatus(expression);
                typeBinding.exitRecursiveFunction();
                return nullAnnotationMatchingOkNonNullStatus2;
            }
            if ((typeBindingSubstitute instanceof TypeVariableBinding) && substitution != null && (checkMode == CheckMode.EXACT || checkMode == CheckMode.COMPATIBLE || checkMode == CheckMode.BOUND_SUPER_CHECK)) {
                typeBinding.exitRecursiveFunction();
                typeBindingSubstitute = Scope.substitute(substitution, typeBindingSubstitute);
                if (!typeBindingSubstitute.enterRecursiveFunction()) {
                    typeBindingSubstitute.exitRecursiveFunction();
                    return nullAnnotationMatchingOkNonNullStatus;
                }
                if (areSameTypes(typeBindingSubstitute, typeBinding2, typeBinding3)) {
                    if ((typeBindingSubstitute.tagBits & 72057594037927936L) == 0) {
                        typeBindingSubstitute.exitRecursiveFunction();
                        return nullAnnotationMatchingOkNonNullStatus;
                    }
                    NullAnnotationMatching nullAnnotationMatchingOkNonNullStatus3 = okNonNullStatus(expression);
                    typeBindingSubstitute.exitRecursiveFunction();
                    return nullAnnotationMatchingOkNonNullStatus3;
                }
            }
            TypeBinding typeBinding9 = typeBindingSubstitute;
            try {
                if (checkMode == CheckMode.BOUND_CHECK) {
                    try {
                        if (!(typeBinding9 instanceof TypeVariableBinding) || ((substitution instanceof ParameterizedTypeBinding) && (((ParameterizedTypeBinding) substitution).tagBits & 4194304) != 0)) {
                            typeBinding6 = null;
                            typeBinding5 = typeBinding9;
                        } else {
                            ReferenceBinding referenceBindingSuperclass = typeBinding9.superclass();
                            if (referenceBindingSuperclass == null || (!referenceBindingSuperclass.hasNullTypeAnnotations() && substitution == null)) {
                                typeBinding6 = null;
                                typeBinding5 = typeBinding9;
                            } else {
                                typeBinding6 = null;
                                typeBinding5 = typeBinding9;
                                try {
                                    Severity severityMax2 = severityMax.max(analyse(referenceBindingSuperclass, typeBinding2, null, substitution, i, expression, CheckMode.BOUND_SUPER_CHECK).severity);
                                    if (severityMax2 == Severity.MISMATCH) {
                                        NullAnnotationMatching nullAnnotationMatching = new NullAnnotationMatching(severityMax2, i3, null);
                                        typeBinding5.exitRecursiveFunction();
                                        return nullAnnotationMatching;
                                    }
                                    severityMax = severityMax2;
                                } catch (Throwable th) {
                                    th = th;
                                    typeBinding4 = typeBinding5;
                                    typeBinding4.exitRecursiveFunction();
                                    throw th;
                                }
                            }
                            ReferenceBinding[] referenceBindingArrSuperInterfaces = typeBinding5.superInterfaces();
                            if (referenceBindingArrSuperInterfaces != null) {
                                int i4 = 0;
                                while (i4 < referenceBindingArrSuperInterfaces.length) {
                                    if (referenceBindingArrSuperInterfaces[i4].hasNullTypeAnnotations() || substitution != null) {
                                        severityMax = severityMax.max(analyse(referenceBindingArrSuperInterfaces[i4], typeBinding2, null, substitution, i, expression, CheckMode.BOUND_SUPER_CHECK).severity);
                                        if (severityMax == Severity.MISMATCH) {
                                            NullAnnotationMatching nullAnnotationMatching2 = new NullAnnotationMatching(severityMax, i3, typeBinding6);
                                            typeBinding5.exitRecursiveFunction();
                                            return nullAnnotationMatching2;
                                        }
                                    }
                                    i4++;
                                    referenceBindingArrSuperInterfaces = referenceBindingArrSuperInterfaces;
                                }
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        typeBinding5 = typeBinding9;
                    }
                } else {
                    typeBinding6 = null;
                    typeBinding5 = typeBinding9;
                }
                TypeBinding typeBinding10 = typeBinding5;
                int i5 = 2;
                int i6 = -1;
                if (typeBinding10 instanceof ArrayBinding) {
                    long[] jArr = ((ArrayBinding) typeBinding10).nullTagBitsPerDimension;
                    if (jArr != null) {
                        int iDimensions = typeBinding10.dimensions();
                        if (typeBinding10.dimensions() == typeBinding2.dimensions()) {
                            long[] jArr2 = ((ArrayBinding) typeBinding2).nullTagBitsPerDimension;
                            if (jArr2 == null) {
                                jArr2 = new long[iDimensions + 1];
                            }
                            int i7 = i3;
                            int i8 = 0;
                            while (true) {
                                if (i8 <= iDimensions) {
                                    long jValidNullTagBits = validNullTagBits(jArr[i8]);
                                    long jValidNullTagBits2 = validNullTagBits(jArr2[i8]);
                                    if (i8 != 0 || jValidNullTagBits != 36028797018963968L || i3 == i6 || !checkMode.requiredNullableMatchesAll()) {
                                        if (i8 > 0) {
                                            i7 = i6;
                                        }
                                        Severity severityComputeNullProblemSeverity = computeNullProblemSeverity(jValidNullTagBits, jValidNullTagBits2, i7, i8 == 0 ? checkMode : checkMode.toDetail(), false);
                                        if (i8 > 0 && severityComputeNullProblemSeverity == Severity.UNCHECKED && (expression instanceof ArrayAllocationExpression) && jValidNullTagBits2 == 0 && jValidNullTagBits != 0) {
                                            Expression expression2 = ((ArrayAllocationExpression) expression).dimensions[i8 - 1];
                                            if ((expression2 instanceof IntLiteral) && expression2.constant.intValue() == 0) {
                                                Severity severity = Severity.OK;
                                                typeBinding4 = typeBinding10;
                                                typeBinding8 = null;
                                                i3 = -1;
                                                break;
                                            }
                                        }
                                        Severity severityMax3 = severityMax.max(severityComputeNullProblemSeverity);
                                        if (severityMax3 == Severity.MISMATCH) {
                                            NullAnnotationMatching nullAnnotationMatching3 = NULL_ANNOTATIONS_MISMATCH;
                                            typeBinding10.exitRecursiveFunction();
                                            return nullAnnotationMatching3;
                                        }
                                        severityMax = severityMax3;
                                    } else if (i3 == i5) {
                                    }
                                    if (severityMax == Severity.OK) {
                                        i3 = -1;
                                    }
                                    i8++;
                                    i6 = -1;
                                    i5 = 2;
                                    typeBinding6 = null;
                                }
                                typeBinding8 = typeBinding6;
                                typeBinding4 = typeBinding10;
                                break;
                            }
                        } else {
                            if (typeBinding2.f626id == 12 && iDimensions > 0 && jArr[0] == 72057594037927936L) {
                                NullAnnotationMatching nullAnnotationMatching4 = NULL_ANNOTATIONS_MISMATCH;
                                typeBinding10.exitRecursiveFunction();
                                return nullAnnotationMatching4;
                            }
                            typeBinding4 = typeBinding10;
                            typeBinding8 = null;
                        }
                    } else {
                        typeBinding4 = typeBinding10;
                        typeBinding8 = null;
                    }
                } else if (typeBinding10.hasNullTypeAnnotations() || typeBinding2.hasNullTypeAnnotations() || typeBinding10.isTypeVariable()) {
                    long jRequiredNullTagBits = requiredNullTagBits(typeBinding10, checkMode);
                    if (jRequiredNullTagBits == 36028797018963968L && i3 != -1 && checkMode.requiredNullableMatchesAll()) {
                        typeBinding4 = typeBinding10;
                        typeBinding7 = null;
                        i2 = 2;
                    } else {
                        long jProvidedNullTagBits = providedNullTagBits(typeBinding2);
                        i2 = 2;
                        Severity severityComputeNullProblemSeverity2 = computeNullProblemSeverity(jRequiredNullTagBits, jProvidedNullTagBits, i, checkMode, typeBinding10.isTypeVariable());
                        if (severityComputeNullProblemSeverity2.isAnyMismatch() && typeBinding10.isWildcard() && jRequiredNullTagBits != 0) {
                            typeBinding4 = typeBinding10;
                            try {
                                typeBinding7 = null;
                                if (((WildcardBinding) typeBinding4).determineNullBitsFromDeclaration(null, null) == 0) {
                                    severityComputeNullProblemSeverity2 = Severity.OK;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                typeBinding4.exitRecursiveFunction();
                                throw th;
                            }
                        } else {
                            typeBinding7 = null;
                            typeBinding4 = typeBinding10;
                        }
                        severityMax = severityMax.max(severityComputeNullProblemSeverity2);
                        if (!severityMax.isAnyMismatch() && (jProvidedNullTagBits & 72057594037927936L) != 0) {
                            nullAnnotationMatchingOkNonNullStatus = okNonNullStatus(expression);
                        }
                    }
                    if (severityMax == Severity.MISMATCH || i3 == i2) {
                        typeBinding8 = typeBinding7;
                    } else {
                        TypeBinding typeBindingFindSuperTypeOriginatingFrom = typeBinding2.findSuperTypeOriginatingFrom(typeBinding4);
                        TypeBinding typeBindingFindSuperTypeOriginatingFrom2 = typeBinding3 != null ? typeBinding3.findSuperTypeOriginatingFrom(typeBinding4) : typeBinding7;
                        if (severityMax == Severity.UNCHECKED && typeBinding4.isTypeVariable() && typeBinding2.isTypeVariable() && (typeBindingFindSuperTypeOriginatingFrom == typeBinding4 || typeBindingFindSuperTypeOriginatingFrom2 == typeBinding4)) {
                            severityMax = Severity.OK;
                        }
                        TypeBinding typeBinding11 = typeBindingFindSuperTypeOriginatingFrom != typeBinding2 ? typeBindingFindSuperTypeOriginatingFrom : typeBinding7;
                        if (typeBinding4.isParameterizedType() && (typeBindingFindSuperTypeOriginatingFrom instanceof ParameterizedTypeBinding)) {
                            TypeBinding[] typeBindingArr = ((ParameterizedTypeBinding) typeBinding4).arguments;
                            TypeBinding[] typeBindingArr2 = ((ParameterizedTypeBinding) typeBindingFindSuperTypeOriginatingFrom).arguments;
                            TypeBinding[] typeBindingArr3 = typeBindingFindSuperTypeOriginatingFrom2 instanceof ParameterizedTypeBinding ? ((ParameterizedTypeBinding) typeBindingFindSuperTypeOriginatingFrom2).arguments : typeBinding7;
                            if (typeBindingArr != null && typeBindingArr2 != null && typeBindingArr.length == typeBindingArr2.length) {
                                int i9 = 0;
                                while (i9 < typeBindingArr.length) {
                                    int i10 = i9;
                                    TypeBinding[] typeBindingArr4 = typeBindingArr2;
                                    TypeBinding typeBinding12 = typeBinding7;
                                    TypeBinding[] typeBindingArr5 = typeBindingArr;
                                    severityMax = severityMax.max(analyse(typeBindingArr[i9], typeBindingArr2[i9], typeBindingArr3 != null ? typeBindingArr3[i9] : typeBinding7, substitution, -1, expression, checkMode.toDetail()).severity);
                                    if (severityMax == Severity.MISMATCH) {
                                        NullAnnotationMatching nullAnnotationMatching5 = new NullAnnotationMatching(severityMax, i3, typeBinding11);
                                        typeBinding4.exitRecursiveFunction();
                                        return nullAnnotationMatching5;
                                    }
                                    i9 = i10 + 1;
                                    typeBinding7 = typeBinding12;
                                    typeBindingArr2 = typeBindingArr4;
                                    typeBindingArr = typeBindingArr5;
                                }
                            }
                        }
                        TypeBinding typeBindingEnclosingType = typeBinding7;
                        ReferenceBinding referenceBindingEnclosingType = typeBinding4.enclosingType();
                        ReferenceBinding referenceBindingEnclosingType2 = typeBinding2.enclosingType();
                        if (referenceBindingEnclosingType != null && referenceBindingEnclosingType2 != null) {
                            if (typeBinding3 != null) {
                                typeBindingEnclosingType = typeBinding3.enclosingType();
                            }
                            severityMax = severityMax.max(analyse(referenceBindingEnclosingType, referenceBindingEnclosingType2, typeBindingEnclosingType, substitution, -1, expression, checkMode).severity);
                        }
                        typeBinding8 = typeBinding11;
                    }
                } else {
                    typeBinding4 = typeBinding10;
                    typeBinding8 = null;
                }
                if (!severityMax.isAnyMismatch()) {
                    typeBinding4.exitRecursiveFunction();
                    return nullAnnotationMatchingOkNonNullStatus;
                }
                NullAnnotationMatching nullAnnotationMatching6 = new NullAnnotationMatching(severityMax, i3, typeBinding8);
                typeBinding4.exitRecursiveFunction();
                return nullAnnotationMatching6;
            } catch (Throwable th4) {
                th = th4;
                typeBinding4 = typeBinding9;
            }
        } catch (Throwable th5) {
            th = th5;
            typeBinding4 = typeBindingSubstitute;
        }
    }

    public static NullAnnotationMatching okNonNullStatus(final Expression expression) {
        final MethodBinding methodBinding;
        if ((expression instanceof MessageSend) && (methodBinding = ((MessageSend) expression).binding) != null && methodBinding.isValidBinding()) {
            MethodBinding methodBindingOriginal = methodBinding.original();
            ReferenceBinding referenceBinding = methodBindingOriginal.declaringClass;
            if (referenceBinding instanceof BinaryTypeBinding) {
                BinaryTypeBinding binaryTypeBinding = (BinaryTypeBinding) referenceBinding;
                if (binaryTypeBinding.externalAnnotationStatus.isPotentiallyUnannotatedLib() && methodBindingOriginal.returnType.isTypeVariable() && (methodBindingOriginal.returnType.tagBits & TagBits.AnnotationNullMASK) == 0) {
                    final int i = binaryTypeBinding.externalAnnotationStatus == BinaryTypeBinding.ExternalAnnotationStatus.NO_EEA_FILE ? 0 : 1024;
                    return new NullAnnotationMatching(Severity.LEGACY_WARNING, 1, null) { // from class: org.eclipse.jdt.internal.compiler.ast.NullAnnotationMatching.1
                        @Override // org.eclipse.jdt.internal.compiler.ast.NullAnnotationMatching
                        public void report(Scope scope) {
                            scope.problemReporter().nonNullTypeVariableInUnannotatedBinary(scope.environment(), methodBinding, expression, i);
                        }
                    };
                }
            }
        }
        return NULL_ANNOTATIONS_OK_NONNULL;
    }

    protected static boolean areSameTypes(TypeBinding typeBinding, TypeBinding typeBinding2, TypeBinding typeBinding3) {
        TypeBinding typeBindingUpperBound;
        if (typeBinding == typeBinding2) {
            return true;
        }
        if (!typeBinding.isParameterizedType() && !typeBinding.isArrayType()) {
            if (TypeBinding.notEquals(typeBinding, typeBinding2)) {
                if (typeBinding instanceof CaptureBinding) {
                    TypeBinding typeBinding4 = ((CaptureBinding) typeBinding).lowerBound;
                    return typeBinding4 != null && areSameTypes(typeBinding4, typeBinding2, typeBinding3) && (typeBinding.tagBits & TagBits.AnnotationNullMASK) == (typeBinding2.tagBits & TagBits.AnnotationNullMASK);
                }
                if (typeBinding.kind() == 4100 && typeBinding == typeBinding3) {
                    return true;
                }
                if ((typeBinding2 instanceof CaptureBinding) && (typeBindingUpperBound = ((CaptureBinding) typeBinding2).upperBound()) != null && areSameTypes(typeBinding, typeBindingUpperBound, typeBinding3) && (typeBinding.tagBits & TagBits.AnnotationNullMASK) == (typeBinding2.tagBits & TagBits.AnnotationNullMASK)) {
                    return true;
                }
                return false;
            }
            if ((typeBinding.tagBits & TagBits.AnnotationNullMASK) == (typeBinding2.tagBits & TagBits.AnnotationNullMASK)) {
                return true;
            }
        }
        return false;
    }

    static long requiredNullTagBits(TypeBinding typeBinding, CheckMode checkMode) {
        TypeBinding typeBinding2;
        long j = typeBinding.tagBits & TagBits.AnnotationNullMASK;
        if (j != 0) {
            return validNullTagBits(j);
        }
        if (typeBinding.isWildcard()) {
            return TagBits.AnnotationNullMASK;
        }
        if (typeBinding.isTypeVariable()) {
            if (typeBinding.isCapture() && (typeBinding2 = ((CaptureBinding) typeBinding).lowerBound) != null && (typeBinding2.tagBits & TagBits.AnnotationNullMASK) == 36028797018963968L) {
                return 36028797018963968L;
            }
            int i = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$NullAnnotationMatching$CheckMode()[checkMode.ordinal()];
            if (i != 3 && i != 4 && i != 5 && i != 6) {
                return 72057594037927936L;
            }
        }
        return 0L;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x003e  */
    static long providedNullTagBits(TypeBinding typeBinding) {
        boolean z;
        TypeBinding typeBinding2;
        long j = typeBinding.tagBits & TagBits.AnnotationNullMASK;
        if (j != 0) {
            return validNullTagBits(j);
        }
        if (typeBinding.isWildcard()) {
            return TagBits.AnnotationNullMASK;
        }
        if (typeBinding.isTypeVariable()) {
            TypeVariableBinding typeVariableBinding = (TypeVariableBinding) typeBinding;
            if (!typeVariableBinding.isCapture() || (typeBinding2 = ((CaptureBinding) typeVariableBinding).lowerBound) == null) {
                z = false;
            } else {
                long j2 = typeBinding2.tagBits & TagBits.AnnotationNullMASK;
                if (j2 == 36028797018963968L) {
                    return 36028797018963968L;
                }
                if (j2 != 0) {
                    z = true;
                } else {
                    z = false;
                }
            }
            if (typeVariableBinding.firstBound != null) {
                long j3 = typeVariableBinding.firstBound.tagBits & TagBits.AnnotationNullMASK;
                if (j3 == 72057594037927936L) {
                    return 72057594037927936L;
                }
                z |= j3 != 0;
            }
            if (z) {
                return TagBits.AnnotationNullMASK;
            }
        }
        return 0L;
    }

    public static TypeBinding moreDangerousType(TypeBinding typeBinding, TypeBinding typeBinding2) {
        if (typeBinding == null) {
            return null;
        }
        long jValidNullTagBits = validNullTagBits(typeBinding.tagBits);
        long jValidNullTagBits2 = validNullTagBits(typeBinding2.tagBits);
        if (jValidNullTagBits == jValidNullTagBits2) {
            return (typeBinding == typeBinding2 || !analyse(typeBinding, typeBinding2, -1).isAnyMismatch()) ? typeBinding : typeBinding2;
        }
        if (jValidNullTagBits == 36028797018963968L) {
            return typeBinding;
        }
        return (jValidNullTagBits2 != 36028797018963968L && jValidNullTagBits == 0) ? typeBinding : typeBinding2;
    }

    private static Severity computeNullProblemSeverity(long j, long j2, int i, CheckMode checkMode, boolean z) {
        if (j == j2) {
            return Severity.OK;
        }
        if (j == 0) {
            switch ($SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$NullAnnotationMatching$CheckMode()[checkMode.ordinal()]) {
                case 1:
                case 2:
                case 3:
                case 4:
                    return Severity.OK;
                case 5:
                    if (j2 == 72057594037927936L) {
                        return Severity.OK;
                    }
                    if (!z) {
                        return Severity.OK;
                    }
                    return Severity.UNCHECKED;
                case 6:
                    return Severity.UNCHECKED;
            }
        }
        if (j == TagBits.AnnotationNullMASK) {
            return Severity.OK;
        }
        if (j == 72057594037927936L) {
            switch ($SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$NullAnnotationMatching$CheckMode()[checkMode.ordinal()]) {
                case 1:
                case 4:
                    if (i == 4) {
                        return Severity.OK;
                    }
                    break;
            }
            if (j2 == 0) {
                return Severity.UNCHECKED;
            }
            return Severity.MISMATCH;
        }
        if (j == 36028797018963968L) {
            switch ($SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$NullAnnotationMatching$CheckMode()[checkMode.ordinal()]) {
                case 1:
                case 4:
                case 5:
                    return Severity.OK;
                case 2:
                case 3:
                    if (j2 == 0) {
                        return Severity.UNCHECKED;
                    }
                    return Severity.MISMATCH;
                case 6:
                    return Severity.MISMATCH;
            }
        }
        return Severity.OK;
    }

    static class SearchContradictions extends TypeBindingVisitor {
        ReferenceBinding typeWithContradiction;

        SearchContradictions() {
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBindingVisitor
        public boolean visit(ReferenceBinding referenceBinding) {
            if ((referenceBinding.tagBits & TagBits.AnnotationNullMASK) != TagBits.AnnotationNullMASK) {
                return true;
            }
            this.typeWithContradiction = referenceBinding;
            return false;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBindingVisitor
        public boolean visit(TypeVariableBinding typeVariableBinding) {
            if (!visit((ReferenceBinding) typeVariableBinding)) {
                return false;
            }
            long j = typeVariableBinding.tagBits & TagBits.AnnotationNullMASK;
            if (typeVariableBinding.firstBound != null) {
                j = typeVariableBinding.firstBound.tagBits & TagBits.AnnotationNullMASK;
            }
            for (TypeBinding typeBinding : typeVariableBinding.otherUpperBounds()) {
                j |= typeBinding.tagBits & TagBits.AnnotationNullMASK;
            }
            if (j != TagBits.AnnotationNullMASK) {
                return true;
            }
            this.typeWithContradiction = typeVariableBinding;
            return false;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBindingVisitor
        public boolean visit(RawTypeBinding rawTypeBinding) {
            return visit((ReferenceBinding) rawTypeBinding);
        }

        /* JADX WARN: Code duplicated, block: B:12:0x0021  */
        /* JADX WARN: Code duplicated, block: B:14:0x0025 A[RETURN] */
        @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBindingVisitor
        public boolean visit(WildcardBinding wildcardBinding) {
            long j;
            long j2;
            long j3 = wildcardBinding.tagBits & TagBits.AnnotationNullMASK;
            int i = wildcardBinding.boundKind;
            if (i != 1) {
                if (i == 2) {
                    j = wildcardBinding.bound.tagBits;
                    j2 = 36028797018963968L;
                }
                if (j3 == TagBits.AnnotationNullMASK) {
                    return true;
                }
                this.typeWithContradiction = wildcardBinding;
                return false;
            }
            j = wildcardBinding.bound.tagBits;
            j2 = 72057594037927936L;
            j3 |= j & j2;
            if (j3 == TagBits.AnnotationNullMASK) {
                return true;
            }
            this.typeWithContradiction = wildcardBinding;
            return false;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.TypeBindingVisitor
        public boolean visit(ParameterizedTypeBinding parameterizedTypeBinding) {
            if (visit((ReferenceBinding) parameterizedTypeBinding)) {
                return super.visit(parameterizedTypeBinding);
            }
            return false;
        }
    }

    public static MethodBinding checkForContradictions(MethodBinding methodBinding, Object obj, Scope scope) {
        int iSourceEnd;
        int iSourceStart;
        if (obj instanceof InvocationSite) {
            InvocationSite invocationSite = (InvocationSite) obj;
            iSourceStart = invocationSite.sourceStart();
            iSourceEnd = invocationSite.sourceEnd();
        } else if (obj instanceof ASTNode) {
            ASTNode aSTNode = (ASTNode) obj;
            iSourceStart = aSTNode.sourceStart;
            iSourceEnd = aSTNode.sourceEnd;
        } else {
            iSourceEnd = 0;
            iSourceStart = 0;
        }
        SearchContradictions searchContradictions = new SearchContradictions();
        TypeBindingVisitor.visit(searchContradictions, methodBinding.returnType);
        if (searchContradictions.typeWithContradiction != null) {
            if (scope == null) {
                return new ProblemMethodBinding(methodBinding, methodBinding.selector, methodBinding.parameters, 25);
            }
            scope.problemReporter().contradictoryNullAnnotationsInferred(methodBinding, iSourceStart, iSourceEnd, obj instanceof FunctionalExpression);
            return methodBinding;
        }
        Expression[] expressionArrArguments = obj instanceof Invocation ? ((Invocation) obj).arguments() : null;
        for (int i = 0; i < methodBinding.parameters.length; i++) {
            TypeBindingVisitor.visit(searchContradictions, methodBinding.parameters[i]);
            if (searchContradictions.typeWithContradiction != null) {
                if (scope == null) {
                    return new ProblemMethodBinding(methodBinding, methodBinding.selector, methodBinding.parameters, 25);
                }
                if (expressionArrArguments != null && i < expressionArrArguments.length) {
                    scope.problemReporter().contradictoryNullAnnotationsInferred(methodBinding, expressionArrArguments[i]);
                } else {
                    scope.problemReporter().contradictoryNullAnnotationsInferred(methodBinding, iSourceStart, iSourceEnd, obj instanceof FunctionalExpression);
                }
                return methodBinding;
            }
        }
        return methodBinding;
    }

    public static boolean hasContradictions(TypeBinding typeBinding) {
        SearchContradictions searchContradictions = new SearchContradictions();
        TypeBindingVisitor.visit(searchContradictions, typeBinding);
        return searchContradictions.typeWithContradiction != null;
    }

    public static TypeBinding strongerType(TypeBinding typeBinding, TypeBinding typeBinding2, LookupEnvironment lookupEnvironment) {
        if ((typeBinding.tagBits & 72057594037927936L) != 0) {
            return mergeTypeAnnotations(typeBinding, typeBinding2, true, lookupEnvironment);
        }
        return mergeTypeAnnotations(typeBinding2, typeBinding, true, lookupEnvironment);
    }

    public static TypeBinding[] weakerTypes(TypeBinding[] typeBindingArr, TypeBinding[] typeBindingArr2, LookupEnvironment lookupEnvironment) {
        int length = typeBindingArr.length;
        TypeBinding[] typeBindingArr3 = new TypeBinding[length];
        for (int i = 0; i < length; i++) {
            long j = typeBindingArr[i].tagBits;
            long j2 = typeBindingArr2[i].tagBits;
            if ((j & 36028797018963968L) != 0) {
                typeBindingArr3[i] = mergeTypeAnnotations(typeBindingArr[i], typeBindingArr2[i], true, lookupEnvironment);
            } else if ((j2 & 36028797018963968L) != 0) {
                typeBindingArr3[i] = mergeTypeAnnotations(typeBindingArr2[i], typeBindingArr[i], true, lookupEnvironment);
            } else if ((j & 72057594037927936L) == 0) {
                typeBindingArr3[i] = mergeTypeAnnotations(typeBindingArr[i], typeBindingArr2[i], true, lookupEnvironment);
            } else {
                typeBindingArr3[i] = mergeTypeAnnotations(typeBindingArr2[i], typeBindingArr[i], true, lookupEnvironment);
            }
        }
        return typeBindingArr3;
    }

    private static TypeBinding mergeTypeAnnotations(TypeBinding typeBinding, TypeBinding typeBinding2, boolean z, LookupEnvironment lookupEnvironment) {
        AnnotationBinding[] typeAnnotations;
        TypeBinding typeBindingCreateAnnotatedType = (z || (typeAnnotations = typeBinding2.getTypeAnnotations()) == Binding.NO_ANNOTATIONS) ? typeBinding : lookupEnvironment.createAnnotatedType(typeBinding, typeAnnotations);
        if (!typeBindingCreateAnnotatedType.isParameterizedType() || !typeBinding2.isParameterizedType()) {
            return typeBindingCreateAnnotatedType;
        }
        ParameterizedTypeBinding parameterizedTypeBinding = (ParameterizedTypeBinding) typeBinding;
        TypeBinding[] typeBindingArr = parameterizedTypeBinding.arguments;
        TypeBinding[] typeBindingArr2 = ((ParameterizedTypeBinding) typeBinding2).arguments;
        TypeBinding[] typeBindingArr3 = new TypeBinding[typeBindingArr.length];
        for (int i = 0; i < typeBindingArr.length; i++) {
            typeBindingArr3[i] = mergeTypeAnnotations(typeBindingArr[i], typeBindingArr2[i], false, lookupEnvironment);
        }
        return lookupEnvironment.createParameterizedType(parameterizedTypeBinding.genericType(), typeBindingArr3, parameterizedTypeBinding.enclosingType());
    }

    public String toString() {
        if (this == NULL_ANNOTATIONS_OK) {
            return "OK";
        }
        if (this == NULL_ANNOTATIONS_MISMATCH) {
            return "MISMATCH";
        }
        if (this == NULL_ANNOTATIONS_OK_NONNULL) {
            return "OK NonNull";
        }
        if (this == NULL_ANNOTATIONS_UNCHECKED) {
            return "UNCHECKED";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Analysis result: severity=" + this.severity);
        sb.append(" nullStatus=" + this.nullStatus);
        return sb.toString();
    }
}
