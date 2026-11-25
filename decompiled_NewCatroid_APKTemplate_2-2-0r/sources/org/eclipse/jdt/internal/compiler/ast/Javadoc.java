package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.CompilationUnitScope;
import org.eclipse.jdt.internal.compiler.lookup.ImportBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;

/* JADX INFO: loaded from: classes14.dex */
public class Javadoc extends ASTNode {
    public TypeReference[] exceptionReferences;
    public JavadocSingleNameReference[] invalidParameters;
    public JavadocSingleNameReference[] paramReferences;
    public JavadocSingleTypeReference[] paramTypeParameters;
    public JavadocReturnStatement returnStatement;
    public Expression[] seeReferences;
    public long[] inheritedPositions = null;
    public long valuePositions = -1;

    boolean canBeSeen(int i, int i2) {
        if (i2 < 0) {
            return true;
        }
        int i3 = i2 & 7;
        if (i3 == 0) {
            return i == 0 || i == 2;
        }
        if (i3 != 2) {
            return (i3 == 4 && i == 1) ? false : true;
        }
        return i == 2;
    }

    public Javadoc(int i, int i2) {
        this.sourceStart = i;
        this.sourceEnd = i2;
        this.bits |= 65536;
    }

    public ASTNode getNodeStartingAt(int i) {
        JavadocSingleNameReference[] javadocSingleNameReferenceArr = this.paramReferences;
        if (javadocSingleNameReferenceArr != null) {
            int length = javadocSingleNameReferenceArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                JavadocSingleNameReference javadocSingleNameReference = this.paramReferences[i2];
                if (javadocSingleNameReference.sourceStart == i) {
                    return javadocSingleNameReference;
                }
            }
        }
        JavadocSingleNameReference[] javadocSingleNameReferenceArr2 = this.invalidParameters;
        if (javadocSingleNameReferenceArr2 != null) {
            int length2 = javadocSingleNameReferenceArr2.length;
            for (int i3 = 0; i3 < length2; i3++) {
                JavadocSingleNameReference javadocSingleNameReference2 = this.invalidParameters[i3];
                if (javadocSingleNameReference2.sourceStart == i) {
                    return javadocSingleNameReference2;
                }
            }
        }
        JavadocSingleTypeReference[] javadocSingleTypeReferenceArr = this.paramTypeParameters;
        if (javadocSingleTypeReferenceArr != null) {
            int length3 = javadocSingleTypeReferenceArr.length;
            for (int i4 = 0; i4 < length3; i4++) {
                JavadocSingleTypeReference javadocSingleTypeReference = this.paramTypeParameters[i4];
                if (javadocSingleTypeReference.sourceStart == i) {
                    return javadocSingleTypeReference;
                }
            }
        }
        TypeReference[] typeReferenceArr = this.exceptionReferences;
        if (typeReferenceArr != null) {
            int length4 = typeReferenceArr.length;
            for (int i5 = 0; i5 < length4; i5++) {
                TypeReference typeReference = this.exceptionReferences[i5];
                if (typeReference.sourceStart == i) {
                    return typeReference;
                }
            }
        }
        Expression[] expressionArr = this.seeReferences;
        if (expressionArr == null) {
            return null;
        }
        int length5 = expressionArr.length;
        for (int i6 = 0; i6 < length5; i6++) {
            Expression expression = this.seeReferences[i6];
            if (expression.sourceStart == i) {
                return expression;
            }
            if (expression instanceof JavadocAllocationExpression) {
                JavadocAllocationExpression javadocAllocationExpression = (JavadocAllocationExpression) this.seeReferences[i6];
                if (javadocAllocationExpression.binding != null && javadocAllocationExpression.binding.isValidBinding() && javadocAllocationExpression.arguments != null) {
                    int length6 = javadocAllocationExpression.arguments.length;
                    for (int i7 = 0; i7 < length6; i7++) {
                        if (javadocAllocationExpression.arguments[i7].sourceStart == i) {
                            return javadocAllocationExpression.arguments[i7];
                        }
                    }
                }
            } else if (expression instanceof JavadocMessageSend) {
                JavadocMessageSend javadocMessageSend = (JavadocMessageSend) this.seeReferences[i6];
                if (javadocMessageSend.binding != null && javadocMessageSend.binding.isValidBinding() && javadocMessageSend.arguments != null) {
                    int length7 = javadocMessageSend.arguments.length;
                    for (int i8 = 0; i8 < length7; i8++) {
                        if (javadocMessageSend.arguments[i8].sourceStart == i) {
                            return javadocMessageSend.arguments[i8];
                        }
                    }
                }
            } else {
                continue;
            }
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public StringBuffer print(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer).append("/**\n");
        JavadocSingleNameReference[] javadocSingleNameReferenceArr = this.paramReferences;
        if (javadocSingleNameReferenceArr != null) {
            int length = javadocSingleNameReferenceArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                printIndent(i + 1, stringBuffer).append(" * @param ");
                this.paramReferences[i2].print(i, stringBuffer).append('\n');
            }
        }
        JavadocSingleTypeReference[] javadocSingleTypeReferenceArr = this.paramTypeParameters;
        if (javadocSingleTypeReferenceArr != null) {
            int length2 = javadocSingleTypeReferenceArr.length;
            for (int i3 = 0; i3 < length2; i3++) {
                printIndent(i + 1, stringBuffer).append(" * @param <");
                this.paramTypeParameters[i3].print(i, stringBuffer).append(">\n");
            }
        }
        if (this.returnStatement != null) {
            printIndent(i + 1, stringBuffer).append(" * @");
            this.returnStatement.print(i, stringBuffer).append('\n');
        }
        TypeReference[] typeReferenceArr = this.exceptionReferences;
        if (typeReferenceArr != null) {
            int length3 = typeReferenceArr.length;
            for (int i4 = 0; i4 < length3; i4++) {
                printIndent(i + 1, stringBuffer).append(" * @throws ");
                this.exceptionReferences[i4].print(i, stringBuffer).append('\n');
            }
        }
        Expression[] expressionArr = this.seeReferences;
        if (expressionArr != null) {
            int length4 = expressionArr.length;
            for (int i5 = 0; i5 < length4; i5++) {
                printIndent(i + 1, stringBuffer).append(" * @see ");
                this.seeReferences[i5].print(i, stringBuffer).append('\n');
            }
        }
        printIndent(i, stringBuffer).append(" */\n");
        return stringBuffer;
    }

    public void resolve(ClassScope classScope) {
        int i;
        int i2;
        if ((this.bits & 65536) == 0) {
            return;
        }
        long[] jArr = this.inheritedPositions;
        if (jArr != null) {
            int length = jArr.length;
            for (int i3 = 0; i3 < length; i3++) {
                long j = this.inheritedPositions[i3];
                classScope.problemReporter().javadocUnexpectedTag((int) (j >>> 32), (int) j);
            }
        }
        JavadocSingleNameReference[] javadocSingleNameReferenceArr = this.paramReferences;
        int length2 = javadocSingleNameReferenceArr == null ? 0 : javadocSingleNameReferenceArr.length;
        for (int i4 = 0; i4 < length2; i4++) {
            JavadocSingleNameReference javadocSingleNameReference = this.paramReferences[i4];
            classScope.problemReporter().javadocUnexpectedTag(javadocSingleNameReference.tagSourceStart, javadocSingleNameReference.tagSourceEnd);
        }
        resolveTypeParameterTags(classScope, true);
        if (this.returnStatement != null) {
            classScope.problemReporter().javadocUnexpectedTag(this.returnStatement.sourceStart, this.returnStatement.sourceEnd);
        }
        TypeReference[] typeReferenceArr = this.exceptionReferences;
        int length3 = typeReferenceArr == null ? 0 : typeReferenceArr.length;
        for (int i5 = 0; i5 < length3; i5++) {
            TypeReference typeReference = this.exceptionReferences[i5];
            if (typeReference instanceof JavadocSingleTypeReference) {
                JavadocSingleTypeReference javadocSingleTypeReference = (JavadocSingleTypeReference) typeReference;
                i = javadocSingleTypeReference.tagSourceStart;
                i2 = javadocSingleTypeReference.tagSourceEnd;
            } else if (typeReference instanceof JavadocQualifiedTypeReference) {
                JavadocQualifiedTypeReference javadocQualifiedTypeReference = (JavadocQualifiedTypeReference) typeReference;
                i = javadocQualifiedTypeReference.tagSourceStart;
                i2 = javadocQualifiedTypeReference.tagSourceEnd;
            } else {
                i = typeReference.sourceStart;
                i2 = typeReference.sourceEnd;
            }
            classScope.problemReporter().javadocUnexpectedTag(i, i2);
        }
        Expression[] expressionArr = this.seeReferences;
        int length4 = expressionArr == null ? 0 : expressionArr.length;
        for (int i6 = 0; i6 < length4; i6++) {
            resolveReference(this.seeReferences[i6], classScope);
        }
        if (classScope.compilerOptions().sourceLevel < ClassFileConstants.JDK1_5 && this.valuePositions != -1) {
            ProblemReporter problemReporter = classScope.problemReporter();
            long j2 = this.valuePositions;
            problemReporter.javadocUnexpectedTag((int) (j2 >>> 32), (int) j2);
        }
    }

    public void resolve(CompilationUnitScope compilationUnitScope) {
        int i = this.bits;
    }

    /* JADX WARN: Code duplicated, block: B:129:0x01f4  */
    public void resolve(MethodScope methodScope) {
        long[] jArr;
        if ((this.bits & 65536) == 0) {
            return;
        }
        AbstractMethodDeclaration abstractMethodDeclarationReferenceMethod = methodScope.referenceMethod();
        boolean z = (abstractMethodDeclarationReferenceMethod == null || abstractMethodDeclarationReferenceMethod.binding == null || abstractMethodDeclarationReferenceMethod.binding.isStatic() || (abstractMethodDeclarationReferenceMethod.binding.modifiers & 805306368) == 0) ? false : true;
        Expression[] expressionArr = this.seeReferences;
        int length = expressionArr == null ? 0 : expressionArr.length;
        boolean z2 = false;
        for (int i = 0; i < length; i++) {
            resolveReference(this.seeReferences[i], methodScope);
            if (abstractMethodDeclarationReferenceMethod != null && !z2) {
                if (abstractMethodDeclarationReferenceMethod.isConstructor()) {
                    Expression expression = this.seeReferences[i];
                    if (expression instanceof JavadocAllocationExpression) {
                        JavadocAllocationExpression javadocAllocationExpression = (JavadocAllocationExpression) expression;
                        if (javadocAllocationExpression.binding != null && javadocAllocationExpression.binding.isValidBinding()) {
                            ReferenceBinding referenceBinding = (ReferenceBinding) abstractMethodDeclarationReferenceMethod.binding.declaringClass.findSuperTypeOriginatingFrom((ReferenceBinding) javadocAllocationExpression.resolvedType.original());
                            if (referenceBinding != null && TypeBinding.notEquals(referenceBinding.original(), abstractMethodDeclarationReferenceMethod.binding.declaringClass)) {
                                MethodBinding constructor = methodScope.getConstructor(referenceBinding, abstractMethodDeclarationReferenceMethod.binding.parameters, javadocAllocationExpression);
                                if (constructor.isValidBinding() && constructor.original() == javadocAllocationExpression.binding.original()) {
                                    MethodBinding methodBindingAsRawMethod = abstractMethodDeclarationReferenceMethod.binding;
                                    if (methodScope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_8 && methodBindingAsRawMethod.typeVariables != Binding.NO_TYPE_VARIABLES) {
                                        methodBindingAsRawMethod = methodBindingAsRawMethod.asRawMethod(methodScope.environment());
                                    }
                                    if (constructor.areParametersEqual(methodBindingAsRawMethod)) {
                                        z2 = true;
                                    }
                                }
                            }
                        }
                    }
                } else if (z) {
                    Expression expression2 = this.seeReferences[i];
                    if (expression2 instanceof JavadocMessageSend) {
                        JavadocMessageSend javadocMessageSend = (JavadocMessageSend) expression2;
                        if (javadocMessageSend.binding != null && javadocMessageSend.binding.isValidBinding() && (javadocMessageSend.actualReceiverType instanceof ReferenceBinding)) {
                            TypeBinding typeBindingFindSuperTypeOriginatingFrom = abstractMethodDeclarationReferenceMethod.binding.declaringClass.findSuperTypeOriginatingFrom((ReferenceBinding) javadocMessageSend.actualReceiverType);
                            if (typeBindingFindSuperTypeOriginatingFrom != null && TypeBinding.notEquals(typeBindingFindSuperTypeOriginatingFrom.original(), abstractMethodDeclarationReferenceMethod.binding.declaringClass) && CharOperation.equals(javadocMessageSend.selector, abstractMethodDeclarationReferenceMethod.selector) && methodScope.environment().methodVerifier().doesMethodOverride(abstractMethodDeclarationReferenceMethod.binding, javadocMessageSend.binding.original())) {
                                z2 = true;
                            }
                        }
                    }
                }
            }
        }
        if (!z2 && abstractMethodDeclarationReferenceMethod != null && abstractMethodDeclarationReferenceMethod.annotations != null) {
            int length2 = abstractMethodDeclarationReferenceMethod.annotations.length;
            for (int i2 = 0; i2 < length2 && !z2; i2++) {
                z2 = (abstractMethodDeclarationReferenceMethod.binding.tagBits & 562949953421312L) != 0;
            }
        }
        boolean z3 = abstractMethodDeclarationReferenceMethod == null || ((!z || this.inheritedPositions == null) && !z2 && (abstractMethodDeclarationReferenceMethod.binding.declaringClass == null || !abstractMethodDeclarationReferenceMethod.binding.declaringClass.isLocalType()));
        if (!z && (jArr = this.inheritedPositions) != null) {
            int length3 = jArr.length;
            for (int i3 = 0; i3 < length3; i3++) {
                long j = this.inheritedPositions[i3];
                methodScope.problemReporter().javadocUnexpectedTag((int) (j >>> 32), (int) j);
            }
        }
        CompilerOptions compilerOptions = methodScope.compilerOptions();
        resolveParamTags(methodScope, z3, compilerOptions.reportUnusedParameterIncludeDocCommentReference);
        resolveTypeParameterTags(methodScope, z3 && compilerOptions.reportMissingJavadocTagsMethodTypeParameters);
        JavadocReturnStatement javadocReturnStatement = this.returnStatement;
        if (javadocReturnStatement == null) {
            if (z3 && abstractMethodDeclarationReferenceMethod != null && abstractMethodDeclarationReferenceMethod.isMethod()) {
                MethodDeclaration methodDeclaration = (MethodDeclaration) abstractMethodDeclarationReferenceMethod;
                if (methodDeclaration.binding.returnType != TypeBinding.VOID) {
                    methodScope.problemReporter().javadocMissingReturnTag(methodDeclaration.returnType.sourceStart, methodDeclaration.returnType.sourceEnd, abstractMethodDeclarationReferenceMethod.binding.modifiers);
                }
            }
        } else {
            javadocReturnStatement.resolve(methodScope);
        }
        resolveThrowsTags(methodScope, z3);
        if (compilerOptions.sourceLevel < ClassFileConstants.JDK1_5 && abstractMethodDeclarationReferenceMethod != null && this.valuePositions != -1) {
            ProblemReporter problemReporter = methodScope.problemReporter();
            long j2 = this.valuePositions;
            problemReporter.javadocUnexpectedTag((int) (j2 >>> 32), (int) j2);
        }
        JavadocSingleNameReference[] javadocSingleNameReferenceArr = this.invalidParameters;
        int length4 = javadocSingleNameReferenceArr == null ? 0 : javadocSingleNameReferenceArr.length;
        for (int i4 = 0; i4 < length4; i4++) {
            this.invalidParameters[i4].resolve(methodScope, false, false);
        }
    }

    private void resolveReference(Expression expression, Scope scope) {
        int i = scope.referenceContext().compilationResult().problemCount;
        int i2 = scope.kind;
        if (i2 == 2) {
            expression.resolveType((MethodScope) scope);
        } else if (i2 == 3) {
            expression.resolveType((ClassScope) scope);
        }
        boolean z = scope.referenceContext().compilationResult().problemCount > i;
        boolean z2 = scope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_5;
        if (expression instanceof JavadocFieldReference) {
            JavadocFieldReference javadocFieldReference = (JavadocFieldReference) expression;
            if (javadocFieldReference.methodBinding != null) {
                if (javadocFieldReference.tagValue == 10) {
                    scope.problemReporter().javadocInvalidValueReference(javadocFieldReference.sourceStart, javadocFieldReference.sourceEnd, scope.getDeclarationModifiers());
                } else if (javadocFieldReference.actualReceiverType != null) {
                    if (scope.enclosingSourceType().isCompatibleWith(javadocFieldReference.actualReceiverType)) {
                        javadocFieldReference.bits |= 16384;
                    }
                    ReferenceBinding referenceBinding = (ReferenceBinding) javadocFieldReference.actualReceiverType;
                    if (CharOperation.equals(referenceBinding.sourceName(), javadocFieldReference.token)) {
                        javadocFieldReference.methodBinding = scope.getConstructor(referenceBinding, Binding.NO_TYPES, javadocFieldReference);
                    } else {
                        javadocFieldReference.methodBinding = scope.findMethod(referenceBinding, javadocFieldReference.token, Binding.NO_TYPES, javadocFieldReference, false);
                    }
                }
            } else if (z2 && javadocFieldReference.binding != null && javadocFieldReference.binding.isValidBinding() && javadocFieldReference.tagValue == 10 && !javadocFieldReference.binding.isStatic()) {
                scope.problemReporter().javadocInvalidValueReference(javadocFieldReference.sourceStart, javadocFieldReference.sourceEnd, scope.getDeclarationModifiers());
            }
            if (z || javadocFieldReference.binding == null || !javadocFieldReference.binding.isValidBinding() || !(javadocFieldReference.actualReceiverType instanceof ReferenceBinding)) {
                return;
            }
            verifyTypeReference(javadocFieldReference, javadocFieldReference.receiver, scope, z2, (ReferenceBinding) javadocFieldReference.actualReceiverType, javadocFieldReference.binding.modifiers);
            return;
        }
        if (!z && (((expression instanceof JavadocSingleTypeReference) || (expression instanceof JavadocQualifiedTypeReference)) && (expression.resolvedType instanceof ReferenceBinding))) {
            ReferenceBinding referenceBinding2 = (ReferenceBinding) expression.resolvedType;
            verifyTypeReference(expression, expression, scope, z2, referenceBinding2, referenceBinding2.modifiers);
        }
        if (expression instanceof JavadocMessageSend) {
            JavadocMessageSend javadocMessageSend = (JavadocMessageSend) expression;
            if (z2 && javadocMessageSend.tagValue == 10) {
                scope.problemReporter().javadocInvalidValueReference(javadocMessageSend.sourceStart, javadocMessageSend.sourceEnd, scope.getDeclarationModifiers());
            }
            if (z || javadocMessageSend.binding == null || !javadocMessageSend.binding.isValidBinding() || !(javadocMessageSend.actualReceiverType instanceof ReferenceBinding)) {
                return;
            }
            verifyTypeReference(javadocMessageSend, javadocMessageSend.receiver, scope, z2, (ReferenceBinding) javadocMessageSend.actualReceiverType, javadocMessageSend.binding.modifiers);
            return;
        }
        if (expression instanceof JavadocAllocationExpression) {
            JavadocAllocationExpression javadocAllocationExpression = (JavadocAllocationExpression) expression;
            if (z2 && javadocAllocationExpression.tagValue == 10) {
                scope.problemReporter().javadocInvalidValueReference(javadocAllocationExpression.sourceStart, javadocAllocationExpression.sourceEnd, scope.getDeclarationModifiers());
            }
            if (z || javadocAllocationExpression.binding == null || !javadocAllocationExpression.binding.isValidBinding() || !(javadocAllocationExpression.resolvedType instanceof ReferenceBinding)) {
                return;
            }
            verifyTypeReference(javadocAllocationExpression, javadocAllocationExpression.type, scope, z2, (ReferenceBinding) javadocAllocationExpression.resolvedType, javadocAllocationExpression.binding.modifiers);
            return;
        }
        if ((expression instanceof JavadocSingleTypeReference) && expression.resolvedType != null && expression.resolvedType.isTypeVariable()) {
            scope.problemReporter().javadocInvalidReference(expression.sourceStart, expression.sourceEnd);
        }
    }

    private void resolveParamTags(MethodScope methodScope, boolean z, boolean z2) {
        AbstractMethodDeclaration abstractMethodDeclarationReferenceMethod = methodScope.referenceMethod();
        JavadocSingleNameReference[] javadocSingleNameReferenceArr = this.paramReferences;
        int length = javadocSingleNameReferenceArr == null ? 0 : javadocSingleNameReferenceArr.length;
        if (abstractMethodDeclarationReferenceMethod == null) {
            for (int i = 0; i < length; i++) {
                JavadocSingleNameReference javadocSingleNameReference = this.paramReferences[i];
                methodScope.problemReporter().javadocUnexpectedTag(javadocSingleNameReference.tagSourceStart, javadocSingleNameReference.tagSourceEnd);
            }
            return;
        }
        int length2 = abstractMethodDeclarationReferenceMethod.arguments == null ? 0 : abstractMethodDeclarationReferenceMethod.arguments.length;
        if (length == 0) {
            if (z) {
                for (int i2 = 0; i2 < length2; i2++) {
                    Argument argument = abstractMethodDeclarationReferenceMethod.arguments[i2];
                    methodScope.problemReporter().javadocMissingParamTag(argument.f617name, argument.sourceStart, argument.sourceEnd, abstractMethodDeclarationReferenceMethod.binding.modifiers);
                }
                return;
            }
            return;
        }
        LocalVariableBinding[] localVariableBindingArr = new LocalVariableBinding[length];
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            JavadocSingleNameReference javadocSingleNameReference2 = this.paramReferences[i4];
            javadocSingleNameReference2.resolve(methodScope, true, z2);
            if (javadocSingleNameReference2.binding != null && javadocSingleNameReference2.binding.isValidBinding()) {
                boolean z3 = false;
                for (int i5 = 0; i5 < i3 && !z3; i5++) {
                    if (localVariableBindingArr[i5] == javadocSingleNameReference2.binding) {
                        methodScope.problemReporter().javadocDuplicatedParamTag(javadocSingleNameReference2.token, javadocSingleNameReference2.sourceStart, javadocSingleNameReference2.sourceEnd, abstractMethodDeclarationReferenceMethod.binding.modifiers);
                        z3 = true;
                    }
                }
                if (!z3) {
                    localVariableBindingArr[i3] = (LocalVariableBinding) javadocSingleNameReference2.binding;
                    i3++;
                }
            }
        }
        if (z) {
            for (int i6 = 0; i6 < length2; i6++) {
                Argument argument2 = abstractMethodDeclarationReferenceMethod.arguments[i6];
                boolean z4 = false;
                for (int i7 = 0; i7 < i3 && !z4; i7++) {
                    if (argument2.binding == localVariableBindingArr[i7]) {
                        z4 = true;
                    }
                }
                if (!z4) {
                    methodScope.problemReporter().javadocMissingParamTag(argument2.f617name, argument2.sourceStart, argument2.sourceEnd, abstractMethodDeclarationReferenceMethod.binding.modifiers);
                }
            }
        }
    }

    private void resolveTypeParameterTags(Scope scope, boolean z) {
        TypeParameter[] typeParameterArrTypeParameters;
        TypeVariableBinding[] typeVariableBindingArr;
        int i;
        JavadocSingleTypeReference[] javadocSingleTypeReferenceArr = this.paramTypeParameters;
        int i2 = 0;
        int length = javadocSingleTypeReferenceArr == null ? 0 : javadocSingleTypeReferenceArr.length;
        int i3 = scope.kind;
        if (i3 == 2) {
            AbstractMethodDeclaration abstractMethodDeclarationReferenceMethod = ((MethodScope) scope).referenceMethod();
            if (abstractMethodDeclarationReferenceMethod == null) {
                while (i2 < length) {
                    JavadocSingleTypeReference javadocSingleTypeReference = this.paramTypeParameters[i2];
                    scope.problemReporter().javadocUnexpectedTag(javadocSingleTypeReference.tagSourceStart, javadocSingleTypeReference.tagSourceEnd);
                    i2++;
                }
                return;
            }
            typeParameterArrTypeParameters = abstractMethodDeclarationReferenceMethod.typeParameters();
            typeVariableBindingArr = abstractMethodDeclarationReferenceMethod.binding.typeVariables;
            i = abstractMethodDeclarationReferenceMethod.binding.modifiers;
        } else if (i3 != 3) {
            i = -1;
            typeParameterArrTypeParameters = null;
            typeVariableBindingArr = null;
        } else {
            TypeDeclaration typeDeclaration = ((ClassScope) scope).referenceContext;
            typeParameterArrTypeParameters = typeDeclaration.typeParameters;
            typeVariableBindingArr = typeDeclaration.binding.typeVariables;
            i = typeDeclaration.binding.modifiers;
        }
        if (typeVariableBindingArr == null || typeVariableBindingArr.length == 0) {
            for (int i4 = 0; i4 < length; i4++) {
                JavadocSingleTypeReference javadocSingleTypeReference2 = this.paramTypeParameters[i4];
                scope.problemReporter().javadocUnexpectedTag(javadocSingleTypeReference2.tagSourceStart, javadocSingleTypeReference2.tagSourceEnd);
            }
            return;
        }
        if (typeParameterArrTypeParameters != null) {
            boolean z2 = z && scope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_5;
            int length2 = typeParameterArrTypeParameters.length;
            if (length == 0) {
                if (z2) {
                    while (i2 < length2) {
                        scope.problemReporter().javadocMissingParamTag(typeParameterArrTypeParameters[i2].f617name, typeParameterArrTypeParameters[i2].sourceStart, typeParameterArrTypeParameters[i2].sourceEnd, i);
                        i2++;
                    }
                    return;
                }
                return;
            }
            if (typeVariableBindingArr.length == length2) {
                TypeVariableBinding[] typeVariableBindingArr2 = new TypeVariableBinding[length];
                int i5 = 0;
                while (i5 < length) {
                    JavadocSingleTypeReference javadocSingleTypeReference3 = this.paramTypeParameters[i5];
                    TypeBinding typeBindingInternalResolveType = javadocSingleTypeReference3.internalResolveType(scope, i2);
                    if (typeBindingInternalResolveType != null && typeBindingInternalResolveType.isValidBinding()) {
                        if (typeBindingInternalResolveType.isTypeVariable()) {
                            if (scope.compilerOptions().reportUnusedParameterIncludeDocCommentReference) {
                                ((TypeVariableBinding) typeBindingInternalResolveType).modifiers |= 134217728;
                            }
                            int i6 = i2;
                            int i7 = i6;
                            while (i6 < i5 && i7 == 0) {
                                if (TypeBinding.equalsEquals(typeVariableBindingArr2[i6], javadocSingleTypeReference3.resolvedType)) {
                                    scope.problemReporter().javadocDuplicatedParamTag(javadocSingleTypeReference3.token, javadocSingleTypeReference3.sourceStart, javadocSingleTypeReference3.sourceEnd, i);
                                    i7 = 1;
                                }
                                i6++;
                            }
                            if (i7 == 0) {
                                typeVariableBindingArr2[i5] = (TypeVariableBinding) javadocSingleTypeReference3.resolvedType;
                            }
                        } else {
                            scope.problemReporter().javadocUndeclaredParamTagName(javadocSingleTypeReference3.token, javadocSingleTypeReference3.sourceStart, javadocSingleTypeReference3.sourceEnd, i);
                        }
                    }
                    i5++;
                    i2 = 0;
                }
                for (int i8 = i2; i8 < length2; i8++) {
                    TypeParameter typeParameter = typeParameterArrTypeParameters[i8];
                    int i9 = i2;
                    int i10 = i9;
                    while (i9 < length && i10 == 0) {
                        if (TypeBinding.equalsEquals(typeParameter.binding, typeVariableBindingArr2[i9])) {
                            typeVariableBindingArr2[i9] = null;
                            i10 = 1;
                        }
                        i9++;
                    }
                    if (i10 == 0 && z2) {
                        scope.problemReporter().javadocMissingParamTag(typeParameter.f617name, typeParameter.sourceStart, typeParameter.sourceEnd, i);
                    }
                }
                while (i2 < length) {
                    if (typeVariableBindingArr2[i2] != null) {
                        JavadocSingleTypeReference javadocSingleTypeReference4 = this.paramTypeParameters[i2];
                        scope.problemReporter().javadocUndeclaredParamTagName(javadocSingleTypeReference4.token, javadocSingleTypeReference4.sourceStart, javadocSingleTypeReference4.sourceEnd, i);
                    }
                    i2++;
                }
            }
        }
    }

    private void resolveThrowsTags(MethodScope methodScope, boolean z) {
        AbstractMethodDeclaration abstractMethodDeclarationReferenceMethod = methodScope.referenceMethod();
        TypeReference[] typeReferenceArr = this.exceptionReferences;
        int i = 0;
        int length = typeReferenceArr == null ? 0 : typeReferenceArr.length;
        if (abstractMethodDeclarationReferenceMethod == null) {
            while (i < length) {
                TypeReference typeReference = this.exceptionReferences[i];
                int i2 = typeReference.sourceStart;
                int i3 = typeReference.sourceEnd;
                if (typeReference instanceof JavadocQualifiedTypeReference) {
                    JavadocQualifiedTypeReference javadocQualifiedTypeReference = (JavadocQualifiedTypeReference) typeReference;
                    i2 = javadocQualifiedTypeReference.tagSourceStart;
                    i3 = javadocQualifiedTypeReference.tagSourceEnd;
                } else if (typeReference instanceof JavadocSingleTypeReference) {
                    JavadocSingleTypeReference javadocSingleTypeReference = (JavadocSingleTypeReference) typeReference;
                    i2 = javadocSingleTypeReference.tagSourceStart;
                    i3 = javadocSingleTypeReference.tagSourceEnd;
                }
                methodScope.problemReporter().javadocUnexpectedTag(i2, i3);
                i++;
            }
            return;
        }
        int length2 = abstractMethodDeclarationReferenceMethod.binding == null ? 0 : abstractMethodDeclarationReferenceMethod.binding.thrownExceptions.length;
        int length3 = abstractMethodDeclarationReferenceMethod.thrownExceptions == null ? 0 : abstractMethodDeclarationReferenceMethod.thrownExceptions.length;
        if (length == 0) {
            if (z) {
                while (i < length2) {
                    ReferenceBinding referenceBinding = abstractMethodDeclarationReferenceMethod.binding.thrownExceptions[i];
                    if (referenceBinding != null && referenceBinding.isValidBinding()) {
                        int i4 = i;
                        while (i4 < length3 && TypeBinding.notEquals(referenceBinding, abstractMethodDeclarationReferenceMethod.thrownExceptions[i4].resolvedType)) {
                            i4++;
                        }
                        if (i4 < length3) {
                            methodScope.problemReporter().javadocMissingThrowsTag(abstractMethodDeclarationReferenceMethod.thrownExceptions[i4], abstractMethodDeclarationReferenceMethod.binding.modifiers);
                        }
                    }
                    i++;
                }
                return;
            }
            return;
        }
        TypeReference[] typeReferenceArr2 = new TypeReference[length];
        int i5 = 0;
        for (int i6 = 0; i6 < length; i6++) {
            TypeReference typeReference2 = this.exceptionReferences[i6];
            typeReference2.resolve(methodScope);
            TypeBinding typeBinding = typeReference2.resolvedType;
            if (typeBinding != null && typeBinding.isValidBinding() && typeBinding.isClass()) {
                typeReferenceArr2[i5] = typeReference2;
                i5++;
            }
        }
        for (int i7 = 0; i7 < length2; i7++) {
            ReferenceBinding referenceBinding2 = abstractMethodDeclarationReferenceMethod.binding.thrownExceptions[i7];
            if (referenceBinding2 != null) {
                referenceBinding2 = (ReferenceBinding) referenceBinding2.erasure();
            }
            boolean z2 = false;
            for (int i8 = 0; i8 < i5 && !z2; i8++) {
                TypeReference typeReference3 = typeReferenceArr2[i8];
                if (typeReference3 != null && TypeBinding.equalsEquals(referenceBinding2, typeReference3.resolvedType)) {
                    typeReferenceArr2[i8] = null;
                    z2 = true;
                }
            }
            if (!z2 && z && referenceBinding2 != null && referenceBinding2.isValidBinding()) {
                int i9 = i7;
                while (i9 < length3 && TypeBinding.notEquals(referenceBinding2, abstractMethodDeclarationReferenceMethod.thrownExceptions[i9].resolvedType)) {
                    i9++;
                }
                if (i9 < length3) {
                    methodScope.problemReporter().javadocMissingThrowsTag(abstractMethodDeclarationReferenceMethod.thrownExceptions[i9], abstractMethodDeclarationReferenceMethod.binding.modifiers);
                }
            }
        }
        for (int i10 = 0; i10 < i5; i10++) {
            TypeReference typeReference4 = typeReferenceArr2[i10];
            if (typeReference4 != null) {
                boolean zIsCompatibleWith = false;
                for (int i11 = 0; i11 < length3 && !zIsCompatibleWith; i11++) {
                    TypeBinding typeBinding2 = abstractMethodDeclarationReferenceMethod.thrownExceptions[i11].resolvedType;
                    if (typeBinding2 != null) {
                        zIsCompatibleWith = typeReference4.resolvedType.isCompatibleWith(typeBinding2);
                    }
                }
                if (!zIsCompatibleWith && !typeReference4.resolvedType.isUncheckedException(false)) {
                    methodScope.problemReporter().javadocInvalidThrowsClassName(typeReference4, abstractMethodDeclarationReferenceMethod.binding.modifiers);
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:55:0x00f7 A[EDGE_INSN: B:55:0x00f7->B:56:0x00f8 BREAK  A[LOOP:3: B:37:0x00a2->B:52:0x00e4, LOOP_LABEL: LOOP:3: B:37:0x00a2->B:52:0x00e4], PHI: r10
      0x00f7: PHI (r10v26 org.eclipse.jdt.internal.compiler.lookup.ClassScope) = 
      (r10v15 org.eclipse.jdt.internal.compiler.lookup.ClassScope)
      (r10v15 org.eclipse.jdt.internal.compiler.lookup.ClassScope)
      (r10v15 org.eclipse.jdt.internal.compiler.lookup.ClassScope)
      (r10v15 org.eclipse.jdt.internal.compiler.lookup.ClassScope)
      (r10v14 org.eclipse.jdt.internal.compiler.lookup.ClassScope)
     binds: [B:25:0x0083, B:30:0x0092, B:50:0x00db, B:51:0x00dd, B:23:0x007b] A[DONT_GENERATE, DONT_INLINE]] */
    private void verifyTypeReference(Expression expression, Expression expression2, Scope scope, boolean z, ReferenceBinding referenceBinding, int i) {
        int declarationModifiers;
        if (referenceBinding.isValidBinding()) {
            if (!canBeSeen(scope.problemReporter().options.reportInvalidJavadocTagsVisibility, i)) {
                scope.problemReporter().javadocHiddenReference(expression2.sourceStart, expression.sourceEnd, scope, i);
                return;
            }
            if (expression != expression2 && !canBeSeen(scope.problemReporter().options.reportInvalidJavadocTagsVisibility, referenceBinding.modifiers)) {
                scope.problemReporter().javadocHiddenReference(expression2.sourceStart, expression2.sourceEnd, scope, referenceBinding.modifiers);
                return;
            }
            if (referenceBinding.isMemberType()) {
                int length = referenceBinding.fPackage.compoundName.length;
                int iDepth = referenceBinding.depth();
                int i2 = iDepth + length;
                int i3 = i2 + 1;
                char[][] cArr = new char[i3][];
                cArr[i2] = referenceBinding.sourceName;
                ReferenceBinding referenceBindingEnclosingType = referenceBinding;
                int i4 = i2;
                while (referenceBindingEnclosingType.enclosingType() != null) {
                    referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType();
                    i4--;
                    cArr[i4] = referenceBindingEnclosingType.sourceName;
                }
                while (true) {
                    length--;
                    if (length < 0) {
                        break;
                    }
                    i4--;
                    cArr[i4] = referenceBindingEnclosingType.fPackage.compoundName[length];
                }
                ClassScope classScope = scope.classScope();
                int i5 = 0;
                if (classScope.parent.kind == 4 && CharOperation.equals(referenceBindingEnclosingType.sourceName, classScope.referenceContext.f619name)) {
                    declarationModifiers = -1;
                    break loop3;
                }
                classScope = classScope.outerMostClassScope();
                if (!(expression2 instanceof JavadocSingleTypeReference) || ((z || iDepth != 1) && !TypeBinding.notEquals(referenceBindingEnclosingType, classScope.referenceContext.binding))) {
                    declarationModifiers = -1;
                    break loop3;
                }
                if (z) {
                    ImportBinding[] importBindingArr = classScope.compilationUnitScope().imports;
                    int length2 = importBindingArr == null ? 0 : importBindingArr.length;
                    int i6 = 0;
                    loop3: while (true) {
                        if (i6 < length2) {
                            int length3 = importBindingArr[i6].compoundName.length;
                            if ((importBindingArr[i6].onDemand && length3 == i2) || length3 == i3) {
                                while (true) {
                                    length3--;
                                    if (length3 >= 0 && CharOperation.equals(importBindingArr[i6].compoundName[length3], cArr[length3])) {
                                        if (length3 == 0) {
                                            ImportReference importReference = importBindingArr[i6].reference;
                                            if (importReference != null) {
                                                importReference.bits |= 2;
                                            }
                                            declarationModifiers = -1;
                                            break loop3;
                                        }
                                    }
                                }
                            }
                            i6++;
                        } else {
                            declarationModifiers = scope.getDeclarationModifiers();
                            scope.problemReporter().javadocInvalidMemberTypeQualification(expression2.sourceStart, expression2.sourceEnd, declarationModifiers);
                            break;
                        }
                    }
                } else {
                    scope.problemReporter().javadocInvalidMemberTypeQualification(expression2.sourceStart, expression2.sourceEnd, scope.getDeclarationModifiers());
                    return;
                }
                if ((expression2 instanceof JavadocQualifiedTypeReference) && !scope.isDefinedInSameUnit(referenceBinding)) {
                    char[][] typeName = ((JavadocQualifiedTypeReference) expression2).getTypeName();
                    int length4 = (classScope.getCurrentPackage() != referenceBinding.getPackage() || typeName.length >= i3) ? 0 : referenceBinding.fPackage.compoundName.length;
                    if (typeName.length == i3 - length4) {
                        while (true) {
                            if (i5 < typeName.length) {
                                if (CharOperation.equals(typeName[i5], cArr[i5 + length4])) {
                                    i5++;
                                }
                            }
                        }
                    }
                    if (declarationModifiers == -1) {
                        declarationModifiers = scope.getDeclarationModifiers();
                    }
                    scope.problemReporter().javadocInvalidMemberTypeQualification(expression2.sourceStart, expression2.sourceEnd, declarationModifiers);
                    return;
                }
            }
            if (scope.referenceCompilationUnit().isPackageInfo() && (expression2 instanceof JavadocSingleTypeReference) && referenceBinding.fPackage.compoundName.length > 0) {
                scope.problemReporter().javadocInvalidReference(expression2.sourceStart, expression2.sourceEnd);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            JavadocSingleNameReference[] javadocSingleNameReferenceArr = this.paramReferences;
            if (javadocSingleNameReferenceArr != null) {
                int length = javadocSingleNameReferenceArr.length;
                for (int i = 0; i < length; i++) {
                    this.paramReferences[i].traverse(aSTVisitor, blockScope);
                }
            }
            JavadocSingleTypeReference[] javadocSingleTypeReferenceArr = this.paramTypeParameters;
            if (javadocSingleTypeReferenceArr != null) {
                int length2 = javadocSingleTypeReferenceArr.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    this.paramTypeParameters[i2].traverse(aSTVisitor, blockScope);
                }
            }
            JavadocReturnStatement javadocReturnStatement = this.returnStatement;
            if (javadocReturnStatement != null) {
                javadocReturnStatement.traverse(aSTVisitor, blockScope);
            }
            TypeReference[] typeReferenceArr = this.exceptionReferences;
            if (typeReferenceArr != null) {
                int length3 = typeReferenceArr.length;
                for (int i3 = 0; i3 < length3; i3++) {
                    this.exceptionReferences[i3].traverse(aSTVisitor, blockScope);
                }
            }
            Expression[] expressionArr = this.seeReferences;
            if (expressionArr != null) {
                int length4 = expressionArr.length;
                for (int i4 = 0; i4 < length4; i4++) {
                    this.seeReferences[i4].traverse(aSTVisitor, blockScope);
                }
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope)) {
            JavadocSingleNameReference[] javadocSingleNameReferenceArr = this.paramReferences;
            if (javadocSingleNameReferenceArr != null) {
                int length = javadocSingleNameReferenceArr.length;
                for (int i = 0; i < length; i++) {
                    this.paramReferences[i].traverse(aSTVisitor, classScope);
                }
            }
            JavadocSingleTypeReference[] javadocSingleTypeReferenceArr = this.paramTypeParameters;
            if (javadocSingleTypeReferenceArr != null) {
                int length2 = javadocSingleTypeReferenceArr.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    this.paramTypeParameters[i2].traverse(aSTVisitor, classScope);
                }
            }
            JavadocReturnStatement javadocReturnStatement = this.returnStatement;
            if (javadocReturnStatement != null) {
                javadocReturnStatement.traverse(aSTVisitor, classScope);
            }
            TypeReference[] typeReferenceArr = this.exceptionReferences;
            if (typeReferenceArr != null) {
                int length3 = typeReferenceArr.length;
                for (int i3 = 0; i3 < length3; i3++) {
                    this.exceptionReferences[i3].traverse(aSTVisitor, classScope);
                }
            }
            Expression[] expressionArr = this.seeReferences;
            if (expressionArr != null) {
                int length4 = expressionArr.length;
                for (int i4 = 0; i4 < length4; i4++) {
                    this.seeReferences[i4].traverse(aSTVisitor, classScope);
                }
            }
        }
        aSTVisitor.endVisit(this, classScope);
    }
}
