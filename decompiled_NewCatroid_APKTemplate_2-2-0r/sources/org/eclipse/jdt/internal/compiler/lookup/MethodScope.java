package org.eclipse.jdt.internal.compiler.lookup;

import androidx.core.view.InputDeviceCompat;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.ast.ConstructorDeclaration;
import org.eclipse.jdt.internal.compiler.ast.LambdaExpression;
import org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference;
import org.eclipse.jdt.internal.compiler.ast.SingleNameReference;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeParameter;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.codegen.ConstantPool;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;

/* JADX INFO: loaded from: classes14.dex */
public class MethodScope extends BlockScope {
    public int analysisIndex;
    public long[] definiteInits;
    public long[][] extraDefiniteInits;
    public SyntheticArgumentBinding[] extraSyntheticArguments;
    public boolean hasMissingSwitchDefault;
    public FieldBinding initializedField;
    public boolean isConstructorCall;
    public boolean isPropagatingInnerClassEmulation;
    public boolean isStatic;
    public int lastIndex;
    public int lastVisibleFieldID;
    public ReferenceContext referenceContext;

    public MethodScope(Scope scope, ReferenceContext referenceContext, boolean z) {
        super(2, scope);
        this.isConstructorCall = false;
        this.lastVisibleFieldID = -1;
        this.lastIndex = 0;
        this.definiteInits = new long[4];
        this.extraDefiniteInits = new long[4][];
        this.locals = new LocalVariableBinding[5];
        this.referenceContext = referenceContext;
        this.isStatic = z;
        this.startIndex = 0;
    }

    public MethodScope(Scope scope, ReferenceContext referenceContext, boolean z, int i) {
        this(scope, referenceContext, z);
        this.lastVisibleFieldID = i;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.BlockScope
    String basicToString(int i) {
        String str = "\n";
        while (true) {
            i--;
            if (i < 0) {
                break;
            }
            str = String.valueOf(str) + "\t";
        }
        String str2 = String.valueOf(str) + "--- Method Scope ---";
        String str3 = String.valueOf(str) + "\t";
        String str4 = String.valueOf(str2) + str3 + "locals:";
        for (int i2 = 0; i2 < this.localIndex; i2++) {
            str4 = String.valueOf(str4) + str3 + "\t" + this.locals[i2].toString();
        }
        return String.valueOf(String.valueOf(String.valueOf(String.valueOf(String.valueOf(str4) + str3 + "startIndex = " + this.startIndex) + str3 + "isConstructorCall = " + this.isConstructorCall) + str3 + "initializedField = " + this.initializedField) + str3 + "lastVisibleFieldID = " + this.lastVisibleFieldID) + str3 + "referenceContext = " + this.referenceContext;
    }

    /* JADX WARN: Code duplicated, block: B:46:0x00c4 A[PHI: r2
      0x00c4: PHI (r2v20 int) = (r2v18 int), (r2v22 int) binds: [B:45:0x00c2, B:40:0x00b9] A[DONT_GENERATE, DONT_INLINE]] */
    private void checkAndSetModifiersForConstructor(MethodBinding methodBinding) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5 = methodBinding.modifiers;
        ReferenceBinding referenceBinding = methodBinding.declaringClass;
        if ((4194304 & i5) != 0) {
            problemReporter().duplicateModifierForMethod(referenceBinding, (AbstractMethodDeclaration) this.referenceContext);
        }
        if ((((ConstructorDeclaration) this.referenceContext).bits & 128) != 0 && (i4 = (i3 = referenceBinding.modifiers) & 16389) != 0) {
            i5 = (i3 & 16384) != 0 ? (i5 & (-8)) | 2 : (i5 & (-8)) | i4;
        }
        if (referenceBinding.isEnum() && (((ConstructorDeclaration) this.referenceContext).bits & 128) == 0) {
            if ((63485 & i5) != 0) {
                problemReporter().illegalModifierForEnumConstructor((AbstractMethodDeclaration) this.referenceContext);
                i2 = (-63486) & i5;
            } else {
                if ((((AbstractMethodDeclaration) this.referenceContext).modifiers & 2048) != 0) {
                    problemReporter().illegalModifierForMethod((AbstractMethodDeclaration) this.referenceContext);
                }
                i2 = i5;
            }
            i = i2 | 2;
        } else if ((63480 & i5) != 0) {
            problemReporter().illegalModifierForMethod((AbstractMethodDeclaration) this.referenceContext);
            i = (-63481) & i5;
        } else {
            if ((((AbstractMethodDeclaration) this.referenceContext).modifiers & 2048) != 0) {
                problemReporter().illegalModifierForMethod((AbstractMethodDeclaration) this.referenceContext);
            }
            i = i5;
        }
        int i6 = i5 & 7;
        if ((i6 & (i6 - 1)) != 0) {
            problemReporter().illegalVisibilityModifierCombinationForMethod(referenceBinding, (AbstractMethodDeclaration) this.referenceContext);
            if ((i5 & 1) != 0) {
                if ((i5 & 4) != 0) {
                    i &= -5;
                }
                if ((i5 & 2) != 0) {
                    i &= -3;
                }
            } else if ((i5 & 4) != 0 && (i5 & 2) != 0) {
                i &= -3;
            }
        }
        methodBinding.modifiers = i;
    }

    /* JADX WARN: Code duplicated, block: B:61:0x00df A[PHI: r2
      0x00df: PHI (r2v9 int) = (r2v7 int), (r2v11 int) binds: [B:60:0x00dd, B:55:0x00d4] A[DONT_GENERATE, DONT_INLINE]] */
    private void checkAndSetModifiersForMethod(MethodBinding methodBinding) {
        int i;
        int i2;
        int i3 = methodBinding.modifiers;
        ReferenceBinding referenceBinding = methodBinding.declaringClass;
        if ((4194304 & i3) != 0) {
            problemReporter().duplicateModifierForMethod(referenceBinding, (AbstractMethodDeclaration) this.referenceContext);
        }
        int i4 = 65535 & i3;
        if (referenceBinding.isInterface()) {
            boolean z = false;
            boolean z2 = (i3 & 65536) != 0;
            if (compilerOptions().sourceLevel < ClassFileConstants.JDK1_8 || referenceBinding.isAnnotationType()) {
                i2 = 1025;
            } else {
                if (!methodBinding.isAbstract()) {
                    if (z2 && methodBinding.isStatic()) {
                        z = true;
                    }
                } else {
                    z = z2 || methodBinding.isStatic();
                    if (methodBinding.isStrictfp()) {
                        problemReporter().illegalAbstractModifierCombinationForMethod((AbstractMethodDeclaration) this.referenceContext);
                    }
                }
                if (z) {
                    problemReporter().illegalModifierCombinationForInterfaceMethod((AbstractMethodDeclaration) this.referenceContext);
                }
                i2 = 68617;
                if (z2) {
                    i4 |= 65536;
                }
                z = true;
            }
            if (((~i2) & i4) != 0) {
                if ((referenceBinding.modifiers & 8192) != 0) {
                    problemReporter().illegalModifierForAnnotationMember((AbstractMethodDeclaration) this.referenceContext);
                    return;
                } else {
                    problemReporter().illegalModifierForInterfaceMethod((AbstractMethodDeclaration) this.referenceContext, z);
                    return;
                }
            }
            return;
        }
        if ((62144 & i3) != 0) {
            problemReporter().illegalModifierForMethod((AbstractMethodDeclaration) this.referenceContext);
            i = (-62145) & i3;
        } else {
            i = i3;
        }
        int i5 = i3 & 7;
        if ((i5 & (i5 - 1)) != 0) {
            problemReporter().illegalVisibilityModifierCombinationForMethod(referenceBinding, (AbstractMethodDeclaration) this.referenceContext);
            if ((i3 & 1) != 0) {
                if ((i3 & 4) != 0) {
                    i &= -5;
                }
                if ((i3 & 2) != 0) {
                    i &= -3;
                }
            } else if ((i3 & 4) != 0 && (i3 & 2) != 0) {
                i &= -3;
            }
        }
        if ((i & 1024) != 0) {
            if ((i & 2362) != 0) {
                problemReporter().illegalAbstractModifierCombinationForMethod(referenceBinding, (AbstractMethodDeclaration) this.referenceContext);
            }
            if (!methodBinding.declaringClass.isAbstract()) {
                problemReporter().abstractMethodInAbstractClass((SourceTypeBinding) referenceBinding, (AbstractMethodDeclaration) this.referenceContext);
            }
        }
        if ((i & 256) != 0 && (i & 2048) != 0) {
            problemReporter().nativeMethodsCannotBeStrictfp(referenceBinding, (AbstractMethodDeclaration) this.referenceContext);
        }
        if ((i3 & 8) != 0 && referenceBinding.isNestedType() && !referenceBinding.isStatic()) {
            problemReporter().unexpectedStaticModifierForMethod(referenceBinding, (AbstractMethodDeclaration) this.referenceContext);
        }
        methodBinding.modifiers = i;
    }

    public void checkUnusedParameters(MethodBinding methodBinding) {
        if (methodBinding.isAbstract()) {
            return;
        }
        if (!methodBinding.isImplementing() || compilerOptions().reportUnusedParameterWhenImplementingAbstract) {
            if ((!methodBinding.isOverriding() || methodBinding.isImplementing() || compilerOptions().reportUnusedParameterWhenOverridingConcrete) && !methodBinding.isMain()) {
                int i = this.localIndex;
                for (int i2 = 0; i2 < i; i2++) {
                    LocalVariableBinding localVariableBinding = this.locals[i2];
                    if (localVariableBinding == null || (localVariableBinding.tagBits & 1024) == 0) {
                        return;
                    }
                    if (localVariableBinding.useFlag == 0 && (localVariableBinding.declaration.bits & 1073741824) != 0) {
                        problemReporter().unusedArgument(localVariableBinding.declaration);
                    }
                }
            }
        }
    }

    public void computeLocalVariablePositions(int i, CodeStream codeStream) {
        LocalVariableBinding localVariableBinding;
        this.offset = i;
        this.maxOffset = i;
        int i2 = this.localIndex;
        int i3 = 0;
        while (i3 < i2 && (localVariableBinding = this.locals[i3]) != null && (localVariableBinding.tagBits & 1024) != 0) {
            codeStream.record(localVariableBinding);
            localVariableBinding.resolvedPosition = this.offset;
            if (TypeBinding.equalsEquals(localVariableBinding.type, TypeBinding.LONG) || TypeBinding.equalsEquals(localVariableBinding.type, TypeBinding.DOUBLE)) {
                this.offset += 2;
            } else {
                this.offset++;
            }
            if (this.offset > 255) {
                problemReporter().noMoreAvailableSpaceForArgument(localVariableBinding, localVariableBinding.declaration);
            }
            i3++;
        }
        SyntheticArgumentBinding[] syntheticArgumentBindingArr = this.extraSyntheticArguments;
        if (syntheticArgumentBindingArr != null) {
            int length = syntheticArgumentBindingArr.length;
            for (int i4 = 0; i4 < length; i4++) {
                SyntheticArgumentBinding syntheticArgumentBinding = this.extraSyntheticArguments[i4];
                syntheticArgumentBinding.resolvedPosition = this.offset;
                if (TypeBinding.equalsEquals(syntheticArgumentBinding.type, TypeBinding.LONG) || TypeBinding.equalsEquals(syntheticArgumentBinding.type, TypeBinding.DOUBLE)) {
                    this.offset += 2;
                } else {
                    this.offset++;
                }
                if (this.offset > 255) {
                    problemReporter().noMoreAvailableSpaceForArgument(syntheticArgumentBinding, (ASTNode) this.referenceContext);
                }
            }
        }
        computeLocalVariablePositions(i3, this.offset, codeStream);
    }

    MethodBinding createMethod(AbstractMethodDeclaration abstractMethodDeclaration) {
        int i;
        this.referenceContext = abstractMethodDeclaration;
        abstractMethodDeclaration.scope = this;
        SourceTypeBinding sourceTypeBinding = referenceType().binding;
        int i2 = abstractMethodDeclaration.modifiers;
        int i3 = 33554432 | i2;
        if (abstractMethodDeclaration.isConstructor()) {
            if (abstractMethodDeclaration.isDefaultConstructor()) {
                i3 = 100663296 | i2;
            }
            abstractMethodDeclaration.binding = new MethodBinding(i3, null, null, sourceTypeBinding);
            checkAndSetModifiersForConstructor(abstractMethodDeclaration.binding);
        } else {
            if (sourceTypeBinding.isInterface()) {
                i = i2 | ((abstractMethodDeclaration.isDefaultMethod() || abstractMethodDeclaration.isStatic()) ? InputDeviceCompat.SOURCE_HDMI : 33555457);
            } else {
                i = i3;
            }
            abstractMethodDeclaration.binding = new MethodBinding(i, abstractMethodDeclaration.selector, null, null, null, sourceTypeBinding);
            checkAndSetModifiersForMethod(abstractMethodDeclaration.binding);
        }
        this.isStatic = abstractMethodDeclaration.binding.isStatic();
        Argument[] argumentArr = abstractMethodDeclaration.arguments;
        int length = argumentArr == null ? 0 : argumentArr.length;
        long j = compilerOptions().sourceLevel;
        if (length > 0) {
            int i4 = length - 1;
            Argument argument = argumentArr[i4];
            if (argument.isVarArgs() && j >= ClassFileConstants.JDK1_5) {
                abstractMethodDeclaration.binding.modifiers |= 128;
            }
            if (CharOperation.equals(argument.f617name, ConstantPool.This)) {
                problemReporter().illegalThisDeclaration(argument);
            }
            while (true) {
                i4--;
                if (i4 < 0) {
                    break;
                }
                Argument argument2 = argumentArr[i4];
                if (argument2.isVarArgs() && j >= ClassFileConstants.JDK1_5) {
                    problemReporter().illegalVararg(argument2, abstractMethodDeclaration);
                }
                if (CharOperation.equals(argument2.f617name, ConstantPool.This)) {
                    problemReporter().illegalThisDeclaration(argument2);
                }
            }
        }
        if (abstractMethodDeclaration.receiver != null) {
            if (j <= ClassFileConstants.JDK1_7) {
                problemReporter().illegalSourceLevelForThis(abstractMethodDeclaration.receiver);
            }
            if (abstractMethodDeclaration.receiver.annotations != null) {
                abstractMethodDeclaration.bits |= 1048576;
            }
        }
        TypeParameter[] typeParameterArrTypeParameters = abstractMethodDeclaration.typeParameters();
        if (typeParameterArrTypeParameters == null || typeParameterArrTypeParameters.length == 0) {
            abstractMethodDeclaration.binding.typeVariables = Binding.NO_TYPE_VARIABLES;
        } else {
            abstractMethodDeclaration.binding.typeVariables = createTypeVariables(typeParameterArrTypeParameters, abstractMethodDeclaration.binding);
            abstractMethodDeclaration.binding.modifiers |= 1073741824;
        }
        return abstractMethodDeclaration.binding;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Scope
    public FieldBinding findField(TypeBinding typeBinding, char[] cArr, InvocationSite invocationSite, boolean z) {
        FieldBinding fieldBindingFindField = super.findField(typeBinding, cArr, invocationSite, z);
        if (fieldBindingFindField == null) {
            return null;
        }
        if (!fieldBindingFindField.isValidBinding()) {
            return fieldBindingFindField;
        }
        if (typeBinding.isInterface() && invocationSite.isQualifiedSuper()) {
            return new ProblemFieldBinding(fieldBindingFindField, fieldBindingFindField.declaringClass, cArr, 28);
        }
        if (!fieldBindingFindField.isStatic() && this.isConstructorCall && !TypeBinding.notEquals(typeBinding, enclosingSourceType())) {
            if (invocationSite instanceof SingleNameReference) {
                return new ProblemFieldBinding(fieldBindingFindField, fieldBindingFindField.declaringClass, cArr, 6);
            }
            if ((invocationSite instanceof QualifiedNameReference) && ((QualifiedNameReference) invocationSite).binding == null) {
                return new ProblemFieldBinding(fieldBindingFindField, fieldBindingFindField.declaringClass, cArr, 6);
            }
        }
        return fieldBindingFindField;
    }

    public boolean isInsideConstructor() {
        return this.referenceContext instanceof ConstructorDeclaration;
    }

    public boolean isInsideInitializer() {
        return this.referenceContext instanceof TypeDeclaration;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Scope
    public boolean isLambdaScope() {
        return this.referenceContext instanceof LambdaExpression;
    }

    public boolean isInsideInitializerOrConstructor() {
        ReferenceContext referenceContext = this.referenceContext;
        return (referenceContext instanceof TypeDeclaration) || (referenceContext instanceof ConstructorDeclaration);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.BlockScope, org.eclipse.jdt.internal.compiler.lookup.Scope
    public ProblemReporter problemReporter() {
        ProblemReporter problemReporter = referenceCompilationUnit().problemReporter;
        problemReporter.referenceContext = this.referenceContext;
        return problemReporter;
    }

    public final int recordInitializationStates(FlowInfo flowInfo) {
        if ((flowInfo.tagBits & 1) != 0) {
            return -1;
        }
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInitsWithoutSideEffect = flowInfo.unconditionalInitsWithoutSideEffect();
        long[] jArr = unconditionalFlowInfoUnconditionalInitsWithoutSideEffect.extra == null ? null : unconditionalFlowInfoUnconditionalInitsWithoutSideEffect.extra[0];
        long j = unconditionalFlowInfoUnconditionalInitsWithoutSideEffect.definiteInits;
        int i = this.lastIndex;
        while (true) {
            i--;
            if (i < 0) {
                long[] jArr2 = this.definiteInits;
                int length = jArr2.length;
                int i2 = this.lastIndex;
                if (length == i2) {
                    long[] jArr3 = new long[i2 + 20];
                    this.definiteInits = jArr3;
                    System.arraycopy(jArr2, 0, jArr3, 0, i2);
                    long[][] jArr4 = this.extraDefiniteInits;
                    int i3 = this.lastIndex;
                    long[][] jArr5 = new long[i3 + 20][];
                    this.extraDefiniteInits = jArr5;
                    System.arraycopy(jArr4, 0, jArr5, 0, i3);
                }
                long[] jArr6 = this.definiteInits;
                int i4 = this.lastIndex;
                jArr6[i4] = j;
                if (jArr != null) {
                    long[][] jArr7 = this.extraDefiniteInits;
                    long[] jArr8 = new long[jArr.length];
                    jArr7[i4] = jArr8;
                    System.arraycopy(jArr, 0, jArr8, 0, jArr.length);
                }
                int i5 = this.lastIndex;
                this.lastIndex = i5 + 1;
                return i5;
            }
            if (this.definiteInits[i] == j) {
                long[] jArr9 = this.extraDefiniteInits[i];
                if (jArr == null || jArr9 == null) {
                    if (jArr == null && jArr9 == null) {
                        return i;
                    }
                } else {
                    if (jArr.length == jArr9.length) {
                        int length2 = jArr.length;
                        for (int i6 = 0; i6 < length2; i6++) {
                            if (jArr[i6] == jArr9[i6]) {
                            }
                        }
                        return i;
                    }
                    continue;
                }
            }
        }
    }

    public AbstractMethodDeclaration referenceMethod() {
        ReferenceContext referenceContext = this.referenceContext;
        if (referenceContext instanceof AbstractMethodDeclaration) {
            return (AbstractMethodDeclaration) referenceContext;
        }
        return null;
    }

    public MethodBinding referenceMethodBinding() {
        ReferenceContext referenceContext = this.referenceContext;
        if (referenceContext instanceof LambdaExpression) {
            return ((LambdaExpression) referenceContext).binding;
        }
        if (referenceContext instanceof AbstractMethodDeclaration) {
            return ((AbstractMethodDeclaration) referenceContext).binding;
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.BlockScope
    public TypeDeclaration referenceType() {
        ClassScope classScopeEnclosingClassScope = enclosingClassScope();
        if (classScopeEnclosingClassScope == null) {
            return null;
        }
        return classScopeEnclosingClassScope.referenceContext;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Scope
    void resolveTypeParameter(TypeParameter typeParameter) {
        typeParameter.resolve(this);
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.BlockScope, org.eclipse.jdt.internal.compiler.lookup.Scope
    public boolean hasDefaultNullnessFor(int i) {
        MethodBinding methodBinding;
        ReferenceContext referenceContext = this.referenceContext;
        if (!(referenceContext instanceof AbstractMethodDeclaration) || (methodBinding = ((AbstractMethodDeclaration) referenceContext).binding) == null || methodBinding.defaultNullness == 0) {
            return this.parent.hasDefaultNullnessFor(i);
        }
        return (i & methodBinding.defaultNullness) != 0;
    }
}
