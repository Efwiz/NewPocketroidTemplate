package org.eclipse.jdt.internal.compiler.lookup;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.CaseStatement;
import org.eclipse.jdt.internal.compiler.ast.FakedTrackingVariable;
import org.eclipse.jdt.internal.compiler.ast.LambdaExpression;
import org.eclipse.jdt.internal.compiler.ast.LocalDeclaration;
import org.eclipse.jdt.internal.compiler.ast.ReturnStatement;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;

/* JADX INFO: loaded from: classes14.dex */
public class BlockScope extends Scope {
    public static final VariableBinding[] EmulationPathToImplicitThis = new VariableBinding[0];
    public static final VariableBinding[] NoEnclosingInstanceInConstructorCall = new VariableBinding[0];
    public static final VariableBinding[] NoEnclosingInstanceInStaticContext = new VariableBinding[0];
    public CaseStatement enclosingCase;
    public FlowInfo finallyInfo;
    public boolean insideTypeAnnotation;
    public int localIndex;
    public LocalVariableBinding[] locals;
    public int maxOffset;
    public int offset;
    public BlockScope[] shiftScopes;
    public int startIndex;
    public int subscopeCount;
    public Scope[] subscopes;
    private List trackingVariables;

    public BlockScope(BlockScope blockScope) {
        this(blockScope, true);
    }

    public BlockScope(BlockScope blockScope, boolean z) {
        this(1, blockScope);
        this.locals = new LocalVariableBinding[5];
        if (z) {
            blockScope.addSubscope(this);
        }
        this.startIndex = blockScope.localIndex;
    }

    public BlockScope(BlockScope blockScope, int i) {
        this(1, blockScope);
        this.locals = new LocalVariableBinding[i];
        blockScope.addSubscope(this);
        this.startIndex = blockScope.localIndex;
    }

    protected BlockScope(int i, Scope scope) {
        super(i, scope);
        this.subscopes = new Scope[1];
        this.subscopeCount = 0;
        this.insideTypeAnnotation = false;
    }

    public final void addAnonymousType(TypeDeclaration typeDeclaration, ReferenceBinding referenceBinding) {
        new ClassScope(this, typeDeclaration).buildAnonymousTypeBinding(enclosingSourceType(), referenceBinding);
        for (MethodScope methodScope = methodScope(); methodScope != null && (methodScope.referenceContext instanceof LambdaExpression); methodScope = methodScope.enclosingMethodScope()) {
            LambdaExpression lambdaExpression = (LambdaExpression) methodScope.referenceContext;
            if (!lambdaExpression.scope.isStatic && !lambdaExpression.scope.isConstructorCall) {
                lambdaExpression.shouldCaptureInstance = true;
            }
        }
    }

    public final void addLocalType(TypeDeclaration typeDeclaration) {
        ClassScope classScope = new ClassScope(this, typeDeclaration);
        addSubscope(classScope);
        classScope.buildLocalTypeBinding(enclosingSourceType());
        for (MethodScope methodScope = methodScope(); methodScope != null && (methodScope.referenceContext instanceof LambdaExpression); methodScope = methodScope.enclosingMethodScope()) {
            LambdaExpression lambdaExpression = (LambdaExpression) methodScope.referenceContext;
            if (!lambdaExpression.scope.isStatic && !lambdaExpression.scope.isConstructorCall) {
                lambdaExpression.shouldCaptureInstance = true;
            }
        }
    }

    public final void addLocalVariable(LocalVariableBinding localVariableBinding) {
        checkAndSetModifiersForVariable(localVariableBinding);
        int i = this.localIndex;
        LocalVariableBinding[] localVariableBindingArr = this.locals;
        if (i == localVariableBindingArr.length) {
            LocalVariableBinding[] localVariableBindingArr2 = new LocalVariableBinding[i * 2];
            this.locals = localVariableBindingArr2;
            System.arraycopy(localVariableBindingArr, 0, localVariableBindingArr2, 0, i);
        }
        LocalVariableBinding[] localVariableBindingArr3 = this.locals;
        int i2 = this.localIndex;
        this.localIndex = i2 + 1;
        localVariableBindingArr3[i2] = localVariableBinding;
        localVariableBinding.declaringScope = this;
        MethodScope methodScopeOuterMostMethodScope = outerMostMethodScope();
        int i3 = methodScopeOuterMostMethodScope.analysisIndex;
        methodScopeOuterMostMethodScope.analysisIndex = i3 + 1;
        localVariableBinding.f627id = i3;
    }

    public void addSubscope(Scope scope) {
        int i = this.subscopeCount;
        Scope[] scopeArr = this.subscopes;
        if (i == scopeArr.length) {
            Scope[] scopeArr2 = new Scope[i * 2];
            this.subscopes = scopeArr2;
            System.arraycopy(scopeArr, 0, scopeArr2, 0, i);
        }
        Scope[] scopeArr3 = this.subscopes;
        int i2 = this.subscopeCount;
        this.subscopeCount = i2 + 1;
        scopeArr3[i2] = scope;
    }

    public final boolean allowBlankFinalFieldAssignment(FieldBinding fieldBinding) {
        if (TypeBinding.notEquals(enclosingReceiverType(), fieldBinding.declaringClass)) {
            return false;
        }
        MethodScope methodScope = methodScope();
        if (methodScope.isStatic == fieldBinding.isStatic() && !methodScope.isLambdaScope()) {
            return methodScope.isInsideInitializer() || ((AbstractMethodDeclaration) methodScope.referenceContext).isInitializationMethod();
        }
        return false;
    }

    String basicToString(int i) {
        String str = "\n";
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            str = String.valueOf(str) + "\t";
        }
        String str2 = String.valueOf(str) + "--- Block Scope ---";
        String str3 = String.valueOf(str) + "\t";
        String str4 = String.valueOf(str2) + str3 + "locals:";
        for (int i2 = 0; i2 < this.localIndex; i2++) {
            str4 = String.valueOf(str4) + str3 + "\t" + this.locals[i2].toString();
        }
        return String.valueOf(str4) + str3 + "startIndex = " + this.startIndex;
    }

    private void checkAndSetModifiersForVariable(LocalVariableBinding localVariableBinding) {
        int i = localVariableBinding.modifiers;
        if ((4194304 & i) != 0 && localVariableBinding.declaration != null) {
            problemReporter().duplicateModifierForVariable(localVariableBinding.declaration, this instanceof MethodScope);
        }
        if ((65519 & i) != 0 && localVariableBinding.declaration != null) {
            problemReporter().illegalModifierForVariable(localVariableBinding.declaration, this instanceof MethodScope);
        }
        localVariableBinding.modifiers = i;
    }

    void computeLocalVariablePositions(int i, int i2, CodeStream codeStream) {
        this.offset = i2;
        this.maxOffset = i2;
        int i3 = this.localIndex;
        boolean z = i < i3;
        int i4 = this.subscopeCount;
        boolean z2 = i4 > 0;
        int i5 = 0;
        while (true) {
            if (!z && !z2) {
                break;
            }
            if (z2 && (!z || this.subscopes[i5].startIndex() <= i)) {
                Scope scope = this.subscopes[i5];
                if (scope instanceof BlockScope) {
                    BlockScope blockScope = (BlockScope) scope;
                    blockScope.computeLocalVariablePositions(0, blockScope.shiftScopes == null ? this.offset : blockScope.maxShiftedOffset(), codeStream);
                    int i6 = blockScope.maxOffset;
                    if (i6 > this.maxOffset) {
                        this.maxOffset = i6;
                    }
                }
                i5++;
                z2 = i5 < i4;
            } else {
                LocalVariableBinding localVariableBinding = this.locals[i];
                boolean z3 = localVariableBinding.useFlag > 0 && localVariableBinding.constant() == Constant.NotAConstant;
                if (localVariableBinding.useFlag == 0 && localVariableBinding.declaration != null && (localVariableBinding.declaration.bits & 1073741824) != 0) {
                    if (localVariableBinding.isCatchParameter()) {
                        problemReporter().unusedExceptionParameter(localVariableBinding.declaration);
                    } else {
                        problemReporter().unusedLocalVariable(localVariableBinding.declaration);
                    }
                }
                if (!z3 && localVariableBinding.declaration != null && compilerOptions().preserveAllLocalVariables) {
                    if (localVariableBinding.useFlag == 0) {
                        localVariableBinding.useFlag = 1;
                    }
                    z3 = true;
                }
                if (z3) {
                    if (localVariableBinding.declaration != null) {
                        codeStream.record(localVariableBinding);
                    }
                    localVariableBinding.resolvedPosition = this.offset;
                    if (TypeBinding.equalsEquals(localVariableBinding.type, TypeBinding.LONG) || TypeBinding.equalsEquals(localVariableBinding.type, TypeBinding.DOUBLE)) {
                        this.offset += 2;
                    } else {
                        this.offset++;
                    }
                    if (this.offset > 65535) {
                        problemReporter().noMoreAvailableSpaceForLocal(localVariableBinding, localVariableBinding.declaration == null ? (ASTNode) methodScope().referenceContext : localVariableBinding.declaration);
                    }
                } else {
                    localVariableBinding.resolvedPosition = -1;
                }
                i++;
                z = i < i3;
            }
        }
        int i7 = this.offset;
        if (i7 > this.maxOffset) {
            this.maxOffset = i7;
        }
    }

    public void emulateOuterAccess(LocalVariableBinding localVariableBinding) {
        MethodScope methodScope;
        BlockScope blockScope = localVariableBinding.declaringScope;
        if (blockScope == null) {
            return;
        }
        int i = 0;
        for (Scope scope = this; blockScope != scope; scope = scope.parent) {
            int i2 = scope.kind;
            if (i2 != 2) {
                if (i2 == 3) {
                    i++;
                }
            } else if (scope.isLambdaScope()) {
                ((LambdaExpression) scope.referenceContext()).addSyntheticArgument(localVariableBinding);
            }
        }
        if (i == 0 || blockScope.methodScope() == (methodScope = methodScope())) {
            return;
        }
        NestedTypeBinding nestedTypeBinding = (NestedTypeBinding) enclosingSourceType();
        if (nestedTypeBinding.isLocalType()) {
            if (!methodScope.isInsideInitializerOrConstructor()) {
                nestedTypeBinding.addSyntheticArgumentAndField(localVariableBinding);
            } else {
                nestedTypeBinding.addSyntheticArgument(localVariableBinding);
            }
        }
    }

    public final ReferenceBinding findLocalType(char[] cArr) {
        long j = compilerOptions().complianceLevel;
        for (int i = this.subscopeCount - 1; i >= 0; i--) {
            Scope scope = this.subscopes[i];
            if (scope instanceof ClassScope) {
                LocalTypeBinding localTypeBinding = (LocalTypeBinding) ((ClassScope) scope).referenceContext.binding;
                if ((j < ClassFileConstants.JDK1_4 || localTypeBinding.enclosingCase == null || isInsideCase(localTypeBinding.enclosingCase)) && CharOperation.equals(localTypeBinding.sourceName(), cArr)) {
                    return localTypeBinding;
                }
            }
        }
        return null;
    }

    public LocalDeclaration[] findLocalVariableDeclarations(int i) {
        LocalDeclaration localDeclaration;
        int i2 = this.localIndex;
        boolean z = i2 > 0;
        int i3 = this.subscopeCount;
        boolean z2 = i3 > 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        LocalDeclaration[] localDeclarationArrFindLocalVariableDeclarations = null;
        while (true) {
            if (!z && !z2) {
                return null;
            }
            if (z2 && (!z || this.subscopes[i4].startIndex() <= i5)) {
                Scope scope = this.subscopes[i4];
                if (scope.kind == 1 && (localDeclarationArrFindLocalVariableDeclarations = ((BlockScope) scope).findLocalVariableDeclarations(i)) != null) {
                    return localDeclarationArrFindLocalVariableDeclarations;
                }
                i4++;
                z2 = i4 < i3;
            } else {
                LocalVariableBinding localVariableBinding = this.locals[i5];
                if (localVariableBinding != null && (localDeclaration = localVariableBinding.declaration) != null) {
                    if (localDeclaration.declarationSourceStart > i) {
                        return localDeclarationArrFindLocalVariableDeclarations;
                    }
                    if (i <= localDeclaration.declarationSourceEnd) {
                        if (localDeclarationArrFindLocalVariableDeclarations == null) {
                            localDeclarationArrFindLocalVariableDeclarations = new LocalDeclaration[i2];
                        }
                        localDeclarationArrFindLocalVariableDeclarations[i6] = localDeclaration;
                        i6++;
                    }
                }
                i5++;
                z = i5 < i2;
                if (!z && localDeclarationArrFindLocalVariableDeclarations != null) {
                    return localDeclarationArrFindLocalVariableDeclarations;
                }
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Scope
    public LocalVariableBinding findVariable(char[] cArr) {
        int length = cArr.length;
        for (int i = this.localIndex - 1; i >= 0; i--) {
            LocalVariableBinding localVariableBinding = this.locals[i];
            char[] cArr2 = localVariableBinding.f628name;
            if (cArr2.length == length && CharOperation.equals(cArr2, cArr)) {
                return localVariableBinding;
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v5 */
    public Binding getBinding(char[][] cArr, int i, InvocationSite invocationSite, boolean z) {
        int i2;
        FieldBinding fieldBindingFindField;
        Binding binding = getBinding(cArr[0], i | 20, invocationSite, z);
        ?? r5 = 1;
        invocationSite.setFieldIndex(1);
        if (binding instanceof VariableBinding) {
            return binding;
        }
        CompilationUnitScope compilationUnitScope = compilationUnitScope();
        compilationUnitScope.recordQualifiedReference(cArr);
        if (!binding.isValidBinding()) {
            return binding;
        }
        int length = cArr.length;
        if (binding instanceof PackageBinding) {
            PackageBinding packageBinding = (PackageBinding) binding;
            int i3 = 1;
            while (i3 < length) {
                compilationUnitScope.recordReference(packageBinding.compoundName, cArr[i3]);
                i2 = i3 + 1;
                binding = packageBinding.getTypeOrPackage(cArr[i3]);
                invocationSite.setFieldIndex(i2);
                if (binding == null) {
                    if (i2 == length) {
                        return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i2), null, 1);
                    }
                    return new ProblemBinding(CharOperation.subarray(cArr, 0, i2), 1);
                }
                if (binding instanceof ReferenceBinding) {
                    if (!binding.isValidBinding()) {
                        return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i2), (ReferenceBinding) ((ReferenceBinding) binding).closestMatch(), binding.problemId());
                    }
                    ReferenceBinding referenceBinding = (ReferenceBinding) binding;
                    if (!referenceBinding.canBeSeenBy(this)) {
                        return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i2), referenceBinding, 2);
                    }
                } else {
                    packageBinding = (PackageBinding) binding;
                    i3 = i2;
                }
            }
            return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i3), null, 1);
        }
        i2 = 1;
        ReferenceBinding referenceBinding2 = (ReferenceBinding) binding;
        Binding bindingConvertToRawType = environment().convertToRawType(referenceBinding2, false);
        boolean z2 = invocationSite instanceof ASTNode;
        if (z2) {
            ASTNode aSTNode = (ASTNode) invocationSite;
            if (aSTNode.isTypeUseDeprecated(referenceBinding2, this)) {
                problemReporter().deprecatedType(referenceBinding2, aSTNode);
            }
        }
        ProblemFieldBinding problemFieldBinding = null;
        while (i2 < length) {
            ReferenceBinding referenceBinding3 = (ReferenceBinding) bindingConvertToRawType;
            int i4 = i2 + 1;
            char[] cArr2 = cArr[i2];
            invocationSite.setFieldIndex(i4);
            invocationSite.setActualReceiverType(referenceBinding3);
            int i5 = i & 1;
            if (i5 != 0 && (fieldBindingFindField = findField(referenceBinding3, cArr2, invocationSite, r5)) != null) {
                if (fieldBindingFindField.isValidBinding()) {
                    i2 = i4;
                    bindingConvertToRawType = fieldBindingFindField;
                    break;
                }
                ProblemFieldBinding problemFieldBinding2 = (ProblemFieldBinding) fieldBindingFindField;
                problemFieldBinding = new ProblemFieldBinding(problemFieldBinding2.closestMatch, problemFieldBinding2.declaringClass, CharOperation.concatWith(CharOperation.subarray(cArr, 0, i4), '.'), fieldBindingFindField.problemId());
                if (fieldBindingFindField.problemId() != 2) {
                    return problemFieldBinding;
                }
            }
            ReferenceBinding referenceBindingFindMemberType = findMemberType(cArr2, referenceBinding3);
            if (referenceBindingFindMemberType == null) {
                if (problemFieldBinding != null) {
                    return problemFieldBinding;
                }
                if (i5 != 0) {
                    return new ProblemFieldBinding(null, referenceBinding3, cArr2, 1);
                }
                if ((i & 3) != 0) {
                    return new ProblemBinding(CharOperation.subarray(cArr, 0, i4), referenceBinding3, 1);
                }
                return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i4), referenceBinding3, 1);
            }
            if (!referenceBindingFindMemberType.isValidBinding()) {
                return problemFieldBinding != null ? problemFieldBinding : new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i4), (ReferenceBinding) referenceBindingFindMemberType.closestMatch(), referenceBindingFindMemberType.problemId());
            }
            if (z2) {
                ASTNode aSTNode2 = (ASTNode) invocationSite;
                if (aSTNode2.isTypeUseDeprecated(referenceBindingFindMemberType, this)) {
                    problemReporter().deprecatedType(referenceBindingFindMemberType, aSTNode2);
                }
            }
            bindingConvertToRawType = referenceBindingFindMemberType;
            r5 = 1;
            i2 = i4;
        }
        if ((i & 1) == 0 || !(bindingConvertToRawType instanceof FieldBinding)) {
            return ((i & 4) == 0 || !(bindingConvertToRawType instanceof ReferenceBinding)) ? new ProblemBinding(CharOperation.subarray(cArr, 0, i2), (int) r5) : bindingConvertToRawType;
        }
        FieldBinding fieldBinding = (FieldBinding) bindingConvertToRawType;
        return !fieldBinding.isStatic() ? new ProblemFieldBinding(fieldBinding, fieldBinding.declaringClass, CharOperation.concatWith(CharOperation.subarray(cArr, 0, i2), '.'), 7) : bindingConvertToRawType;
    }

    public final Binding getBinding(char[][] cArr, InvocationSite invocationSite) {
        int i;
        int length = cArr.length;
        Binding binding = getBinding(cArr[0], 23, invocationSite, true);
        if (!binding.isValidBinding()) {
            return binding;
        }
        if (binding instanceof PackageBinding) {
            int i2 = 1;
            while (i2 < length) {
                i = i2 + 1;
                binding = ((PackageBinding) binding).getTypeOrPackage(cArr[i2]);
                if (binding == null) {
                    if (i == length) {
                        return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i), null, 1);
                    }
                    return new ProblemBinding(CharOperation.subarray(cArr, 0, i), 1);
                }
                if (!(binding instanceof ReferenceBinding)) {
                    i2 = i;
                } else {
                    if (!binding.isValidBinding()) {
                        return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i), (ReferenceBinding) ((ReferenceBinding) binding).closestMatch(), binding.problemId());
                    }
                    ReferenceBinding referenceBinding = (ReferenceBinding) binding;
                    if (!referenceBinding.canBeSeenBy(this)) {
                        return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i), referenceBinding, 2);
                    }
                }
            }
            return binding;
        }
        i = 1;
        if (binding instanceof ReferenceBinding) {
            while (i < length) {
                ReferenceBinding referenceBinding2 = (ReferenceBinding) binding;
                int i3 = i + 1;
                char[] cArr2 = cArr[i];
                FieldBinding fieldBindingFindField = findField(referenceBinding2.capture(this, invocationSite.sourceStart(), invocationSite.sourceEnd()), cArr2, invocationSite, true);
                if (fieldBindingFindField != null) {
                    if (!fieldBindingFindField.isValidBinding()) {
                        return new ProblemFieldBinding(fieldBindingFindField, fieldBindingFindField.declaringClass, CharOperation.concatWith(CharOperation.subarray(cArr, 0, i3), '.'), fieldBindingFindField.problemId());
                    }
                    if (!fieldBindingFindField.isStatic()) {
                        return new ProblemFieldBinding(fieldBindingFindField, fieldBindingFindField.declaringClass, CharOperation.concatWith(CharOperation.subarray(cArr, 0, i3), '.'), 7);
                    }
                    i = i3;
                    binding = fieldBindingFindField;
                } else {
                    ReferenceBinding referenceBindingFindMemberType = findMemberType(cArr2, referenceBinding2);
                    if (referenceBindingFindMemberType == null) {
                        return new ProblemBinding(CharOperation.subarray(cArr, 0, i3), referenceBinding2, 1);
                    }
                    if (!referenceBindingFindMemberType.isValidBinding()) {
                        return new ProblemReferenceBinding(CharOperation.subarray(cArr, 0, i3), (ReferenceBinding) referenceBindingFindMemberType.closestMatch(), referenceBindingFindMemberType.problemId());
                    }
                    binding = referenceBindingFindMemberType;
                    i = i3;
                }
            }
            return binding;
        }
        VariableBinding variableBinding = (VariableBinding) binding;
        while (i < length) {
            TypeBinding typeBinding = variableBinding.type;
            if (typeBinding == null) {
                return new ProblemFieldBinding(null, null, CharOperation.concatWith(CharOperation.subarray(cArr, 0, i), '.'), 1);
            }
            TypeBinding typeBindingCapture = typeBinding.capture(this, invocationSite.sourceStart(), invocationSite.sourceEnd());
            int i4 = i + 1;
            FieldBinding fieldBindingFindField2 = findField(typeBindingCapture, cArr[i], invocationSite, true);
            if (fieldBindingFindField2 == null) {
                return new ProblemFieldBinding(null, typeBindingCapture instanceof ReferenceBinding ? (ReferenceBinding) typeBindingCapture : null, CharOperation.concatWith(CharOperation.subarray(cArr, 0, i4), '.'), 1);
            }
            if (!fieldBindingFindField2.isValidBinding()) {
                return fieldBindingFindField2;
            }
            variableBinding = fieldBindingFindField2;
            i = i4;
        }
        return variableBinding;
    }

    public VariableBinding[] getEmulationPath(LocalVariableBinding localVariableBinding) {
        FieldBinding syntheticField;
        SyntheticArgumentBinding syntheticArgument;
        SyntheticArgumentBinding syntheticArgument2;
        MethodScope methodScope = methodScope();
        SourceTypeBinding sourceTypeBindingEnclosingSourceType = methodScope.enclosingSourceType();
        BlockScope blockScope = localVariableBinding.declaringScope;
        if (blockScope == null || methodScope == blockScope.methodScope()) {
            return new VariableBinding[]{localVariableBinding};
        }
        if (methodScope.isLambdaScope() && (syntheticArgument2 = ((LambdaExpression) methodScope.referenceContext).getSyntheticArgument(localVariableBinding)) != null) {
            return new VariableBinding[]{syntheticArgument2};
        }
        if (methodScope.isInsideInitializerOrConstructor() && sourceTypeBindingEnclosingSourceType.isNestedType() && (syntheticArgument = ((NestedTypeBinding) sourceTypeBindingEnclosingSourceType).getSyntheticArgument(localVariableBinding)) != null) {
            return new VariableBinding[]{syntheticArgument};
        }
        if (methodScope.isStatic || (syntheticField = sourceTypeBindingEnclosingSourceType.getSyntheticField(localVariableBinding)) == null) {
            return null;
        }
        return new VariableBinding[]{syntheticField};
    }

    public Object[] getEmulationPath(ReferenceBinding referenceBinding, boolean z, boolean z2) {
        FieldBinding syntheticField;
        SyntheticArgumentBinding syntheticArgument;
        MethodScope methodScope = methodScope();
        SourceTypeBinding sourceTypeBindingEnclosingSourceType = methodScope.enclosingSourceType();
        if (!methodScope.isStatic && !methodScope.isConstructorCall && (TypeBinding.equalsEquals(sourceTypeBindingEnclosingSourceType, referenceBinding) || (!z && sourceTypeBindingEnclosingSourceType.findSuperTypeOriginatingFrom(referenceBinding) != null))) {
            return EmulationPathToImplicitThis;
        }
        if (!sourceTypeBindingEnclosingSourceType.isNestedType() || sourceTypeBindingEnclosingSourceType.isStatic()) {
            if (methodScope.isConstructorCall) {
                return NoEnclosingInstanceInConstructorCall;
            }
            if (methodScope.isStatic) {
                return NoEnclosingInstanceInStaticContext;
            }
            return null;
        }
        boolean zIsInsideInitializerOrConstructor = methodScope.isInsideInitializerOrConstructor();
        if (zIsInsideInitializerOrConstructor && (syntheticArgument = ((NestedTypeBinding) sourceTypeBindingEnclosingSourceType).getSyntheticArgument(referenceBinding, z, methodScope.isConstructorCall)) != null) {
            boolean z3 = sourceTypeBindingEnclosingSourceType.isAnonymousType() && sourceTypeBindingEnclosingSourceType.scope.referenceContext.allocation.enclosingInstance != null;
            if (z2 && methodScope.isConstructorCall && !z3 && (TypeBinding.equalsEquals(sourceTypeBindingEnclosingSourceType, referenceBinding) || (!z && sourceTypeBindingEnclosingSourceType.findSuperTypeOriginatingFrom(referenceBinding) != null))) {
                return NoEnclosingInstanceInConstructorCall;
            }
            return new Object[]{syntheticArgument};
        }
        if (methodScope.isStatic) {
            return NoEnclosingInstanceInStaticContext;
        }
        if (sourceTypeBindingEnclosingSourceType.isAnonymousType()) {
            ReferenceBinding referenceBindingEnclosingType = sourceTypeBindingEnclosingSourceType.enclosingType();
            if (referenceBindingEnclosingType.isNestedType()) {
                NestedTypeBinding nestedTypeBinding = (NestedTypeBinding) referenceBindingEnclosingType;
                SyntheticArgumentBinding syntheticArgument2 = nestedTypeBinding.getSyntheticArgument(nestedTypeBinding.enclosingType(), z, methodScope.isConstructorCall);
                if (syntheticArgument2 != null && (syntheticField = sourceTypeBindingEnclosingSourceType.getSyntheticField(syntheticArgument2)) != null && (TypeBinding.equalsEquals(syntheticField.type, referenceBinding) || (!z && ((ReferenceBinding) syntheticField.type).findSuperTypeOriginatingFrom(referenceBinding) != null))) {
                    return new Object[]{syntheticField};
                }
            }
        }
        FieldBinding syntheticField2 = sourceTypeBindingEnclosingSourceType.getSyntheticField(referenceBinding, z);
        if (syntheticField2 != null) {
            if (methodScope.isConstructorCall) {
                return NoEnclosingInstanceInConstructorCall;
            }
            return new Object[]{syntheticField2};
        }
        Object[] objArr = new Object[2];
        ReferenceBinding referenceBindingEnclosingType2 = sourceTypeBindingEnclosingSourceType.enclosingType();
        if (zIsInsideInitializerOrConstructor) {
            objArr[0] = ((NestedTypeBinding) sourceTypeBindingEnclosingSourceType).getSyntheticArgument(referenceBindingEnclosingType2, z, methodScope.isConstructorCall);
        } else {
            if (methodScope.isConstructorCall) {
                return NoEnclosingInstanceInConstructorCall;
            }
            objArr[0] = sourceTypeBindingEnclosingSourceType.getSyntheticField(referenceBindingEnclosingType2, z);
        }
        if (objArr[0] != null) {
            int i = 1;
            while (true) {
                ReferenceBinding referenceBindingEnclosingType3 = referenceBindingEnclosingType2.enclosingType();
                if (referenceBindingEnclosingType3 == null || TypeBinding.equalsEquals(referenceBindingEnclosingType2, referenceBinding) || (!z && referenceBindingEnclosingType2.findSuperTypeOriginatingFrom(referenceBinding) != null)) {
                    break;
                }
                if (methodScope != null) {
                    methodScope = methodScope.enclosingMethodScope();
                    if (methodScope != null && methodScope.isConstructorCall) {
                        return NoEnclosingInstanceInConstructorCall;
                    }
                    if (methodScope != null && methodScope.isStatic) {
                        return NoEnclosingInstanceInStaticContext;
                    }
                }
                FieldBinding syntheticField3 = ((NestedTypeBinding) referenceBindingEnclosingType2).getSyntheticField(referenceBindingEnclosingType3, z);
                if (syntheticField3 == null) {
                    break;
                }
                if (i == objArr.length) {
                    Object[] objArr2 = new Object[i + 1];
                    System.arraycopy(objArr, 0, objArr2, 0, i);
                    objArr = objArr2;
                }
                objArr[i] = ((SourceTypeBinding) syntheticField3.declaringClass).addSyntheticMethod(syntheticField3, true, false);
                i++;
                referenceBindingEnclosingType2 = referenceBindingEnclosingType3;
            }
            if (TypeBinding.equalsEquals(referenceBindingEnclosingType2, referenceBinding) || (!z && referenceBindingEnclosingType2.findSuperTypeOriginatingFrom(referenceBinding) != null)) {
                return objArr;
            }
        }
        return null;
    }

    public final boolean isDuplicateLocalVariable(char[] cArr) {
        BlockScope blockScope = this;
        while (true) {
            for (int i = 0; i < this.localIndex; i++) {
                if (CharOperation.equals(cArr, blockScope.locals[i].f628name)) {
                    return true;
                }
            }
            if (blockScope.kind != 1) {
                return false;
            }
            blockScope = (BlockScope) blockScope.parent;
        }
    }

    public int maxShiftedOffset() {
        int i;
        BlockScope[] blockScopeArr = this.shiftScopes;
        int i2 = -1;
        if (blockScopeArr != null) {
            int length = blockScopeArr.length;
            for (int i3 = 0; i3 < length; i3++) {
                BlockScope blockScope = this.shiftScopes[i3];
                if (blockScope != null && (i = blockScope.maxOffset) > i2) {
                    i2 = i;
                }
            }
        }
        return i2;
    }

    public final boolean needBlankFinalFieldInitializationCheck(FieldBinding fieldBinding) {
        boolean zIsStatic = fieldBinding.isStatic();
        ReferenceBinding referenceBinding = fieldBinding.declaringClass;
        for (MethodScope methodScopeNamedMethodScope = namedMethodScope(); methodScopeNamedMethodScope != null && methodScopeNamedMethodScope.isStatic == zIsStatic; methodScopeNamedMethodScope = methodScopeNamedMethodScope.enclosingMethodScope().namedMethodScope()) {
            if (!methodScopeNamedMethodScope.isInsideInitializer() && !((AbstractMethodDeclaration) methodScopeNamedMethodScope.referenceContext).isInitializationMethod()) {
                return false;
            }
            ReferenceBinding referenceBindingEnclosingReceiverType = methodScopeNamedMethodScope.enclosingReceiverType();
            if (TypeBinding.equalsEquals(referenceBindingEnclosingReceiverType, referenceBinding)) {
                return true;
            }
            if (!referenceBindingEnclosingReceiverType.erasure().isAnonymousType()) {
                return false;
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Scope
    public ProblemReporter problemReporter() {
        return methodScope().problemReporter();
    }

    public void propagateInnerEmulation(ReferenceBinding referenceBinding, boolean z) {
        SyntheticArgumentBinding[] syntheticArgumentBindingArrSyntheticOuterLocalVariables = referenceBinding.syntheticOuterLocalVariables();
        if (syntheticArgumentBindingArrSyntheticOuterLocalVariables != null) {
            for (SyntheticArgumentBinding syntheticArgumentBinding : syntheticArgumentBindingArrSyntheticOuterLocalVariables) {
                if (!z || !TypeBinding.equalsEquals(syntheticArgumentBinding.type, referenceBinding.enclosingType())) {
                    emulateOuterAccess(syntheticArgumentBinding.actualOuterLocalVariable);
                }
            }
        }
    }

    public TypeDeclaration referenceType() {
        return methodScope().referenceType();
    }

    public int scopeIndex() {
        if (this instanceof MethodScope) {
            return -1;
        }
        BlockScope blockScope = (BlockScope) this.parent;
        Scope[] scopeArr = blockScope.subscopes;
        int i = blockScope.subscopeCount;
        for (int i2 = 0; i2 < i; i2++) {
            if (scopeArr[i2] == this) {
                return i2;
            }
        }
        return -1;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Scope
    int startIndex() {
        return this.startIndex;
    }

    public String toString() {
        return toString(0);
    }

    public String toString(int i) {
        String strBasicToString = basicToString(i);
        for (int i2 = 0; i2 < this.subscopeCount; i2++) {
            if (this.subscopes[i2] instanceof BlockScope) {
                strBasicToString = String.valueOf(strBasicToString) + ((BlockScope) this.subscopes[i2]).toString(i + 1) + "\n";
            }
        }
        return strBasicToString;
    }

    public int registerTrackingVariable(FakedTrackingVariable fakedTrackingVariable) {
        if (this.trackingVariables == null) {
            this.trackingVariables = new ArrayList(3);
        }
        this.trackingVariables.add(fakedTrackingVariable);
        MethodScope methodScopeOuterMostMethodScope = outerMostMethodScope();
        int i = methodScopeOuterMostMethodScope.analysisIndex;
        methodScopeOuterMostMethodScope.analysisIndex = i + 1;
        return i;
    }

    public void removeTrackingVar(FakedTrackingVariable fakedTrackingVariable) {
        if (fakedTrackingVariable.innerTracker != null) {
            fakedTrackingVariable.innerTracker.withdraw();
            fakedTrackingVariable.innerTracker = null;
        }
        List list = this.trackingVariables;
        if ((list == null || !list.remove(fakedTrackingVariable)) && (this.parent instanceof BlockScope)) {
            ((BlockScope) this.parent).removeTrackingVar(fakedTrackingVariable);
        }
    }

    public void pruneWrapperTrackingVar(FakedTrackingVariable fakedTrackingVariable) {
        this.trackingVariables.remove(fakedTrackingVariable);
    }

    public void checkUnclosedCloseables(FlowInfo flowInfo, FlowContext flowContext, ASTNode aSTNode, BlockScope blockScope) {
        if (compilerOptions().analyseResourceLeaks) {
            if (this.trackingVariables == null) {
                if (aSTNode == null || !(this.parent instanceof BlockScope)) {
                    return;
                }
                ((BlockScope) this.parent).checkUnclosedCloseables(flowInfo, flowContext, aSTNode, blockScope);
                return;
            }
            if (aSTNode == null || flowInfo.reachMode() == 0) {
                FakedTrackingVariable closeTrackingVariable = aSTNode instanceof ReturnStatement ? FakedTrackingVariable.getCloseTrackingVariable(((ReturnStatement) aSTNode).expression, flowInfo, flowContext) : null;
                FakedTrackingVariable.IteratorForReporting iteratorForReporting = new FakedTrackingVariable.IteratorForReporting(this.trackingVariables, this, aSTNode != null);
                while (iteratorForReporting.hasNext()) {
                    FakedTrackingVariable next = iteratorForReporting.next();
                    if (closeTrackingVariable == null || !next.isResourceBeingReturned(closeTrackingVariable)) {
                        if (aSTNode == null || !next.hasDefinitelyNoResource(flowInfo)) {
                            if (aSTNode == null || flowContext == null || !flowContext.recordExitAgainstResource(this, flowInfo, next, aSTNode)) {
                                int iFindMostSpecificStatus = next.findMostSpecificStatus(flowInfo, this, blockScope);
                                if (iFindMostSpecificStatus == 2) {
                                    reportResourceLeak(next, aSTNode, iFindMostSpecificStatus);
                                } else {
                                    if (aSTNode == null) {
                                        if (next.reportRecordedErrors(this, iFindMostSpecificStatus, flowInfo.reachMode() != 0)) {
                                        }
                                    }
                                    if (iFindMostSpecificStatus == 16) {
                                        reportResourceLeak(next, aSTNode, iFindMostSpecificStatus);
                                    } else if (iFindMostSpecificStatus == 4 && environment().globalOptions.complianceLevel >= ClassFileConstants.JDK1_7) {
                                        next.reportExplicitClosing(problemReporter());
                                    }
                                }
                            }
                        }
                    }
                }
                if (aSTNode == null) {
                    for (int i = 0; i < this.localIndex; i++) {
                        this.locals[i].closeTracker = null;
                    }
                    this.trackingVariables = null;
                }
            }
        }
    }

    private void reportResourceLeak(FakedTrackingVariable fakedTrackingVariable, ASTNode aSTNode, int i) {
        if (aSTNode != null) {
            fakedTrackingVariable.recordErrorLocation(aSTNode, i);
        } else {
            fakedTrackingVariable.reportError(problemReporter(), null, i);
        }
    }

    public void correlateTrackingVarsIfElse(FlowInfo flowInfo, FlowInfo flowInfo2) {
        int iMergeNullStatus;
        List list = this.trackingVariables;
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                FakedTrackingVariable fakedTrackingVariable = (FakedTrackingVariable) this.trackingVariables.get(i);
                if (fakedTrackingVariable.originalBinding != null) {
                    if (flowInfo.isDefinitelyNonNull(fakedTrackingVariable.binding) && flowInfo2.isDefinitelyNull(fakedTrackingVariable.originalBinding)) {
                        flowInfo2.markAsDefinitelyNonNull(fakedTrackingVariable.binding);
                    } else if (flowInfo2.isDefinitelyNonNull(fakedTrackingVariable.binding) && flowInfo.isDefinitelyNull(fakedTrackingVariable.originalBinding)) {
                        flowInfo.markAsDefinitelyNonNull(fakedTrackingVariable.binding);
                    } else if (flowInfo != FlowInfo.DEAD_END && flowInfo2 != FlowInfo.DEAD_END) {
                        for (int i2 = i + 1; i2 < size; i2++) {
                            FakedTrackingVariable fakedTrackingVariable2 = (FakedTrackingVariable) this.trackingVariables.get(i2);
                            if (fakedTrackingVariable.originalBinding == fakedTrackingVariable2.originalBinding) {
                                boolean zHasNullInfoFor = flowInfo.hasNullInfoFor(fakedTrackingVariable.binding);
                                boolean zHasNullInfoFor2 = flowInfo2.hasNullInfoFor(fakedTrackingVariable.binding);
                                boolean zHasNullInfoFor3 = flowInfo.hasNullInfoFor(fakedTrackingVariable2.binding);
                                boolean zHasNullInfoFor4 = flowInfo2.hasNullInfoFor(fakedTrackingVariable2.binding);
                                if (!zHasNullInfoFor && zHasNullInfoFor2 && zHasNullInfoFor3 && !zHasNullInfoFor4) {
                                    iMergeNullStatus = FlowInfo.mergeNullStatus(flowInfo.nullStatus(fakedTrackingVariable2.binding), flowInfo2.nullStatus(fakedTrackingVariable.binding));
                                } else if (zHasNullInfoFor && !zHasNullInfoFor2 && !zHasNullInfoFor3 && zHasNullInfoFor4) {
                                    iMergeNullStatus = FlowInfo.mergeNullStatus(flowInfo.nullStatus(fakedTrackingVariable.binding), flowInfo2.nullStatus(fakedTrackingVariable2.binding));
                                }
                                flowInfo.markNullStatus(fakedTrackingVariable.binding, iMergeNullStatus);
                                flowInfo2.markNullStatus(fakedTrackingVariable.binding, iMergeNullStatus);
                                fakedTrackingVariable.originalBinding.closeTracker = fakedTrackingVariable;
                                flowInfo.markNullStatus(fakedTrackingVariable2.binding, 4);
                                flowInfo2.markNullStatus(fakedTrackingVariable2.binding, 4);
                            }
                        }
                    }
                }
            }
        }
        if (this.parent instanceof BlockScope) {
            ((BlockScope) this.parent).correlateTrackingVarsIfElse(flowInfo, flowInfo2);
        }
    }

    public void checkAppropriateMethodAgainstSupers(char[] cArr, MethodBinding methodBinding, TypeBinding[] typeBindingArr, InvocationSite invocationSite) {
        ReferenceBinding[] referenceBindingArrSuperInterfaces;
        ReferenceBinding referenceBindingEnclosingReceiverType = enclosingReceiverType();
        if (!checkAppropriate(methodBinding, getMethod(referenceBindingEnclosingReceiverType.superclass(), cArr, typeBindingArr, invocationSite), invocationSite) || (referenceBindingArrSuperInterfaces = referenceBindingEnclosingReceiverType.superInterfaces()) == null) {
            return;
        }
        for (int i = 0; i < referenceBindingArrSuperInterfaces.length && checkAppropriate(methodBinding, getMethod(referenceBindingArrSuperInterfaces[i], cArr, typeBindingArr, invocationSite), invocationSite); i++) {
        }
    }

    private boolean checkAppropriate(MethodBinding methodBinding, MethodBinding methodBinding2, InvocationSite invocationSite) {
        if (methodBinding2 == null || !methodBinding2.isValidBinding() || methodBinding2 == methodBinding || !MethodVerifier.doesMethodOverride(methodBinding2, methodBinding, environment())) {
            return true;
        }
        problemReporter().illegalSuperCallBypassingOverride(invocationSite, methodBinding, methodBinding2.declaringClass);
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Scope
    public boolean hasDefaultNullnessFor(int i) {
        return this.parent.hasDefaultNullnessFor(i);
    }
}
