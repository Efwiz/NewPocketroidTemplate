package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import java.util.List;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.parser.Parser;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilation;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilationUnit;
import org.eclipse.jdt.internal.compiler.problem.AbortMethod;
import org.eclipse.jdt.internal.compiler.problem.AbortType;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.problem.ProblemSeverities;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public abstract class AbstractMethodDeclaration extends ASTNode implements ProblemSeverities, ReferenceContext {
    public Annotation[] annotations;
    public Argument[] arguments;
    public MethodBinding binding;
    public int bodyStart;
    public CompilationResult compilationResult;
    public int declarationSourceEnd;
    public int declarationSourceStart;
    public int explicitDeclarations;
    public Javadoc javadoc;
    public int modifiers;
    public int modifiersSourceStart;
    public Receiver receiver;
    public MethodScope scope;
    public char[] selector;
    public Statement[] statements;
    public TypeReference[] thrownExceptions;
    public boolean ignoreFurtherInvestigation = false;
    public int bodyEnd = -1;

    public void getAllAnnotationContexts(int i, List list) {
    }

    public boolean isAnnotationMethod() {
        return false;
    }

    public boolean isClinit() {
        return false;
    }

    public boolean isConstructor() {
        return false;
    }

    public boolean isDefaultConstructor() {
        return false;
    }

    public boolean isDefaultMethod() {
        return false;
    }

    public boolean isInitializationMethod() {
        return false;
    }

    public boolean isMethod() {
        return false;
    }

    public abstract void parseStatements(Parser parser, CompilationUnitDeclaration compilationUnitDeclaration);

    public StringBuffer printReturnType(int i, StringBuffer stringBuffer) {
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public void tagAsHavingIgnoredMandatoryErrors(int i) {
    }

    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
    }

    public TypeParameter[] typeParameters() {
        return null;
    }

    AbstractMethodDeclaration(CompilationResult compilationResult) {
        this.compilationResult = compilationResult;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public void abort(int i, CategorizedProblem categorizedProblem) {
        if (i == 2) {
            throw new AbortCompilation(this.compilationResult, categorizedProblem);
        }
        if (i == 4) {
            throw new AbortCompilationUnit(this.compilationResult, categorizedProblem);
        }
        if (i == 8) {
            throw new AbortType(this.compilationResult, categorizedProblem);
        }
        throw new AbortMethod(this.compilationResult, categorizedProblem);
    }

    public void createArgumentBindings() {
        createArgumentBindings(this.arguments, this.binding, this.scope);
    }

    static void createArgumentBindings(Argument[] argumentArr, MethodBinding methodBinding, MethodScope methodScope) {
        boolean zUsesNullTypeAnnotations = methodScope.environment().usesNullTypeAnnotations();
        if (argumentArr == null || methodBinding == null) {
            return;
        }
        int length = argumentArr.length;
        for (int i = 0; i < length; i++) {
            Argument argument = argumentArr[i];
            methodBinding.parameters[i] = argument.createBinding(methodScope, methodBinding.parameters[i]);
            if (!zUsesNullTypeAnnotations) {
                long j = argument.binding.tagBits & TagBits.AnnotationNullMASK;
                if (j != 0) {
                    if (methodBinding.parameterNonNullness == null) {
                        methodBinding.parameterNonNullness = new Boolean[argumentArr.length];
                        methodBinding.tagBits |= 4096;
                    }
                    methodBinding.parameterNonNullness[i] = Boolean.valueOf(j == 72057594037927936L);
                }
            }
        }
    }

    public void bindArguments() {
        Argument[] argumentArr = this.arguments;
        if (argumentArr != null) {
            MethodBinding methodBinding = this.binding;
            AnnotationBinding[][] annotationBindingArr = null;
            boolean z = true;
            if (methodBinding == null) {
                int length = argumentArr.length;
                for (int i = 0; i < length; i++) {
                    this.arguments[i].bind(this.scope, null, true);
                }
                return;
            }
            if (!methodBinding.isAbstract() && !this.binding.isNative()) {
                z = false;
            }
            int length2 = this.arguments.length;
            for (int i2 = 0; i2 < length2; i2++) {
                Argument argument = this.arguments[i2];
                this.binding.parameters[i2] = argument.bind(this.scope, this.binding.parameters[i2], z);
                if (argument.annotations != null) {
                    if (annotationBindingArr == null) {
                        AnnotationBinding[][] annotationBindingArr2 = new AnnotationBinding[length2][];
                        for (int i3 = 0; i3 < i2; i3++) {
                            annotationBindingArr2[i3] = Binding.NO_ANNOTATIONS;
                        }
                        annotationBindingArr = annotationBindingArr2;
                    }
                    annotationBindingArr[i2] = argument.binding.getAnnotations();
                } else if (annotationBindingArr != null) {
                    annotationBindingArr[i2] = Binding.NO_ANNOTATIONS;
                }
            }
            if (annotationBindingArr != null) {
                this.binding.setParameterAnnotations(annotationBindingArr);
            }
        }
    }

    public void bindThrownExceptions() {
        MethodBinding methodBinding;
        if (this.thrownExceptions == null || (methodBinding = this.binding) == null || methodBinding.thrownExceptions == null) {
            return;
        }
        int length = this.thrownExceptions.length;
        int length2 = this.binding.thrownExceptions.length;
        if (length2 == length) {
            for (int i = 0; i < length2; i++) {
                this.thrownExceptions[i].resolvedType = this.binding.thrownExceptions[i];
            }
            return;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < length && i2 < length2; i3++) {
            TypeReference typeReference = this.thrownExceptions[i3];
            ReferenceBinding referenceBinding = this.binding.thrownExceptions[i2];
            char[][] cArr = referenceBinding.compoundName;
            if (cArr != null) {
                if (typeReference instanceof SingleTypeReference) {
                    if (CharOperation.equals(typeReference.getTypeName()[0], cArr[cArr.length - 1])) {
                        typeReference.resolvedType = referenceBinding;
                        i2++;
                    }
                } else if (CharOperation.equals(typeReference.getTypeName(), cArr)) {
                    typeReference.resolvedType = referenceBinding;
                    i2++;
                }
            }
        }
    }

    static void analyseArguments(LookupEnvironment lookupEnvironment, FlowInfo flowInfo, Argument[] argumentArr, MethodBinding methodBinding) {
        Boolean bool;
        if (argumentArr != null) {
            boolean zUsesNullTypeAnnotations = lookupEnvironment.usesNullTypeAnnotations();
            int iMin = Math.min(methodBinding.parameters.length, argumentArr.length);
            for (int i = 0; i < iMin; i++) {
                if (zUsesNullTypeAnnotations) {
                    long j = methodBinding.parameters[i].tagBits & TagBits.AnnotationNullMASK;
                    if (j == 72057594037927936L) {
                        flowInfo.markAsDefinitelyNonNull(argumentArr[i].binding);
                    } else if (j == 36028797018963968L) {
                        flowInfo.markPotentiallyNullBit(argumentArr[i].binding);
                    } else if (methodBinding.parameters[i].isFreeTypeVariable()) {
                        flowInfo.markNullStatus(argumentArr[i].binding, 48);
                    }
                } else if (methodBinding.parameterNonNullness != null && (bool = methodBinding.parameterNonNullness[i]) != null) {
                    if (bool.booleanValue()) {
                        flowInfo.markAsDefinitelyNonNull(argumentArr[i].binding);
                    } else {
                        flowInfo.markPotentiallyNullBit(argumentArr[i].binding);
                    }
                }
                flowInfo.markAsDefinitelyAssigned(argumentArr[i].binding);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public CompilationResult compilationResult() {
        return this.compilationResult;
    }

    public void generateCode(ClassScope classScope, ClassFile classFile) {
        CompilationResult compilationResult;
        int i;
        boolean z;
        TypeDeclaration typeDeclaration;
        classFile.codeStream.wideMode = false;
        if (this.ignoreFurtherInvestigation) {
            if (this.binding == null) {
                return;
            }
            CategorizedProblem[] problems = this.scope.referenceCompilationUnit().compilationResult.getProblems();
            int length = problems.length;
            CategorizedProblem[] categorizedProblemArr = new CategorizedProblem[length];
            System.arraycopy(problems, 0, categorizedProblemArr, 0, length);
            classFile.addProblemMethod(this, this.binding, categorizedProblemArr);
            return;
        }
        if (classScope == null || (typeDeclaration = classScope.referenceContext) == null) {
            compilationResult = null;
            i = 0;
        } else {
            compilationResult = typeDeclaration.compilationResult();
            i = compilationResult.problemCount;
        }
        int i2 = 0;
        boolean z2 = false;
        do {
            try {
                i2 = classFile.contentsOffset;
                generateCode(classFile);
            } catch (AbortMethod e) {
                z = true;
                if (e.compilationResult == CodeStream.RESTART_IN_WIDE_MODE) {
                    classFile.contentsOffset = i2;
                    classFile.methodCount--;
                    classFile.codeStream.resetInWideMode();
                    if (compilationResult != null) {
                        compilationResult.problemCount = i;
                    }
                } else if (e.compilationResult == CodeStream.RESTART_CODE_GEN_FOR_UNUSED_LOCALS_MODE) {
                    classFile.contentsOffset = i2;
                    classFile.methodCount--;
                    classFile.codeStream.resetForCodeGenUnusedLocals();
                    if (compilationResult != null) {
                        compilationResult.problemCount = i;
                    }
                } else {
                    z2 = true;
                }
            }
            z = false;
        } while (z);
        if (z2) {
            CategorizedProblem[] allProblems = this.scope.referenceCompilationUnit().compilationResult.getAllProblems();
            int length2 = allProblems.length;
            CategorizedProblem[] categorizedProblemArr2 = new CategorizedProblem[length2];
            System.arraycopy(allProblems, 0, categorizedProblemArr2, 0, length2);
            classFile.addProblemMethod(this, this.binding, categorizedProblemArr2, i2);
        }
    }

    public void generateCode(ClassFile classFile) {
        classFile.generateMethodInfoHeader(this.binding);
        int i = classFile.contentsOffset;
        int iGenerateMethodInfoAttributes = classFile.generateMethodInfoAttributes(this.binding);
        if (!this.binding.isNative() && !this.binding.isAbstract()) {
            int i2 = classFile.contentsOffset;
            classFile.generateCodeAttributeHeader();
            CodeStream codeStream = classFile.codeStream;
            codeStream.reset(this, classFile);
            this.scope.computeLocalVariablePositions(!this.binding.isStatic() ? 1 : 0, codeStream);
            Argument[] argumentArr = this.arguments;
            if (argumentArr != null) {
                int length = argumentArr.length;
                for (int i3 = 0; i3 < length; i3++) {
                    LocalVariableBinding localVariableBinding = this.arguments[i3].binding;
                    codeStream.addVisibleLocalVariable(localVariableBinding);
                    localVariableBinding.recordInitializationStartPC(0);
                }
            }
            Statement[] statementArr = this.statements;
            if (statementArr != null) {
                int length2 = statementArr.length;
                for (int i4 = 0; i4 < length2; i4++) {
                    this.statements[i4].generateCode(this.scope, codeStream);
                }
            }
            if (this.ignoreFurtherInvestigation) {
                throw new AbortMethod(this.scope.referenceCompilationUnit().compilationResult, null);
            }
            if ((this.bits & 64) != 0) {
                codeStream.return_();
            }
            codeStream.exitUserScope(this.scope);
            codeStream.recordPositionsFrom(0, this.declarationSourceEnd);
            try {
                classFile.completeCodeAttribute(i2);
                iGenerateMethodInfoAttributes++;
            } catch (NegativeArraySizeException unused) {
                throw new AbortMethod(this.scope.referenceCompilationUnit().compilationResult, null);
            }
        } else {
            checkArgumentsSize();
        }
        classFile.completeMethodInfo(this.binding, i, iGenerateMethodInfoAttributes);
    }

    private void checkArgumentsSize() {
        TypeBinding[] typeBindingArr = this.binding.parameters;
        int length = typeBindingArr.length;
        int i = 1;
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = typeBindingArr[i2].f626id;
            i = (i3 == 7 || i3 == 8) ? i + 2 : i + 1;
            if (i > 255) {
                this.scope.problemReporter().noMoreAvailableSpaceForArgument(this.scope.locals[i2], this.scope.locals[i2].declaration);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public CompilationUnitDeclaration getCompilationUnitDeclaration() {
        MethodScope methodScope = this.scope;
        if (methodScope != null) {
            return methodScope.compilationUnitScope().referenceContext;
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public boolean hasErrors() {
        return this.ignoreFurtherInvestigation;
    }

    public boolean isAbstract() {
        MethodBinding methodBinding = this.binding;
        if (methodBinding != null) {
            return methodBinding.isAbstract();
        }
        return (this.modifiers & 1024) != 0;
    }

    public boolean isNative() {
        MethodBinding methodBinding = this.binding;
        if (methodBinding != null) {
            return methodBinding.isNative();
        }
        return (this.modifiers & 256) != 0;
    }

    public boolean isStatic() {
        MethodBinding methodBinding = this.binding;
        if (methodBinding != null) {
            return methodBinding.isStatic();
        }
        return (this.modifiers & 8) != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public StringBuffer print(int i, StringBuffer stringBuffer) {
        Javadoc javadoc = this.javadoc;
        if (javadoc != null) {
            javadoc.print(i, stringBuffer);
        }
        printIndent(i, stringBuffer);
        printModifiers(this.modifiers, stringBuffer);
        Annotation[] annotationArr = this.annotations;
        if (annotationArr != null) {
            printAnnotations(annotationArr, stringBuffer);
            stringBuffer.append(StringUtil.SPACE);
        }
        TypeParameter[] typeParameterArrTypeParameters = typeParameters();
        if (typeParameterArrTypeParameters != null) {
            stringBuffer.append('<');
            int length = typeParameterArrTypeParameters.length - 1;
            for (int i2 = 0; i2 < length; i2++) {
                typeParameterArrTypeParameters[i2].print(0, stringBuffer);
                stringBuffer.append(", ");
            }
            typeParameterArrTypeParameters[length].print(0, stringBuffer);
            stringBuffer.append('>');
        }
        printReturnType(0, stringBuffer).append(this.selector).append('(');
        Receiver receiver = this.receiver;
        if (receiver != null) {
            receiver.print(0, stringBuffer);
        }
        if (this.arguments != null) {
            for (int i3 = 0; i3 < this.arguments.length; i3++) {
                if (i3 > 0 || this.receiver != null) {
                    stringBuffer.append(", ");
                }
                this.arguments[i3].print(0, stringBuffer);
            }
        }
        stringBuffer.append(')');
        if (this.thrownExceptions != null) {
            stringBuffer.append(" throws ");
            for (int i4 = 0; i4 < this.thrownExceptions.length; i4++) {
                if (i4 > 0) {
                    stringBuffer.append(", ");
                }
                this.thrownExceptions[i4].print(0, stringBuffer);
            }
        }
        printBody(i + 1, stringBuffer);
        return stringBuffer;
    }

    public StringBuffer printBody(int i, StringBuffer stringBuffer) {
        if (isAbstract() || (this.modifiers & 16777216) != 0) {
            return stringBuffer.append(';');
        }
        stringBuffer.append(" {");
        if (this.statements != null) {
            for (int i2 = 0; i2 < this.statements.length; i2++) {
                stringBuffer.append('\n');
                this.statements[i2].printStatement(i, stringBuffer);
            }
        }
        stringBuffer.append('\n');
        printIndent(i != 0 ? i - 1 : 0, stringBuffer).append('}');
        return stringBuffer;
    }

    public void resolve(ClassScope classScope) {
        if (this.binding == null) {
            this.ignoreFurtherInvestigation = true;
        }
        try {
            bindArguments();
            resolveReceiver();
            bindThrownExceptions();
            resolveJavadoc();
            resolveAnnotations(this.scope, this.annotations, this.binding, isConstructor());
            long j = this.scope.compilerOptions().sourceLevel;
            if (j < ClassFileConstants.JDK1_8) {
                validateNullAnnotations(this.scope.environment().usesNullTypeAnnotations());
            }
            resolveStatements();
            MethodBinding methodBinding = this.binding;
            if (methodBinding == null || (methodBinding.getAnnotationTagBits() & 70368744177664L) != 0 || (this.binding.modifiers & 1048576) == 0 || j < ClassFileConstants.JDK1_5) {
                return;
            }
            this.scope.problemReporter().missingDeprecatedAnnotationForMethod(this);
        } catch (AbortMethod unused) {
            this.ignoreFurtherInvestigation = true;
        }
    }

    public void resolveReceiver() {
        Receiver receiver = this.receiver;
        if (receiver == null) {
            return;
        }
        if (receiver.modifiers != 0) {
            this.scope.problemReporter().illegalModifiers(this.receiver.declarationSourceStart, this.receiver.declarationSourceEnd);
        }
        TypeBinding typeBinding = this.receiver.type.resolvedType;
        if (this.binding == null || typeBinding == null || !typeBinding.isValidBinding()) {
            return;
        }
        ReferenceBinding referenceBinding = this.binding.declaringClass;
        if (isStatic() || referenceBinding.isAnonymousType()) {
            this.scope.problemReporter().disallowedThisParameter(this.receiver);
            return;
        }
        ReferenceBinding referenceBindingEnclosingReceiverType = this.scope.enclosingReceiverType();
        if (isConstructor()) {
            if (referenceBinding.isStatic() || (referenceBinding.tagBits & 24) == 0) {
                this.scope.problemReporter().disallowedThisParameter(this.receiver);
                return;
            }
            referenceBindingEnclosingReceiverType = referenceBindingEnclosingReceiverType.enclosingType();
        }
        char[][] name2 = this.receiver.qualifyingName == null ? null : this.receiver.qualifyingName.getName();
        if (isConstructor()) {
            if (name2 == null || name2.length > 1 || !CharOperation.equals(referenceBindingEnclosingReceiverType.sourceName(), name2[0])) {
                this.scope.problemReporter().illegalQualifierForExplicitThis(this.receiver, referenceBindingEnclosingReceiverType);
                this.receiver.qualifyingName = null;
            }
        } else if (name2 != null && name2.length > 0) {
            this.scope.problemReporter().illegalQualifierForExplicitThis2(this.receiver);
            this.receiver.qualifyingName = null;
        }
        if (TypeBinding.notEquals(referenceBindingEnclosingReceiverType, typeBinding)) {
            this.scope.problemReporter().illegalTypeForExplicitThis(this.receiver, referenceBindingEnclosingReceiverType);
        }
        if (this.receiver.type.hasNullTypeAnnotation(TypeReference.AnnotationPosition.ANY)) {
            this.scope.problemReporter().nullAnnotationUnsupportedLocation(this.receiver.type);
        }
    }

    public void resolveJavadoc() {
        MethodBinding methodBinding = this.binding;
        if (methodBinding == null) {
            return;
        }
        Javadoc javadoc = this.javadoc;
        if (javadoc != null) {
            javadoc.resolve(this.scope);
            return;
        }
        if (methodBinding.declaringClass == null || this.binding.declaringClass.isLocalType()) {
            return;
        }
        int iComputeOuterMostVisibility = this.binding.modifiers & 7;
        ClassScope classScope = this.scope.classScope();
        ProblemReporter problemReporter = this.scope.problemReporter();
        int iComputeSeverity = problemReporter.computeSeverity(IProblem.JavadocMissing);
        if (iComputeSeverity != 256) {
            if (classScope != null) {
                iComputeOuterMostVisibility = Util.computeOuterMostVisibility(classScope.referenceType(), iComputeOuterMostVisibility);
            }
            problemReporter.javadocMissing(this.sourceStart, this.sourceEnd, iComputeSeverity, iComputeOuterMostVisibility | (this.binding.modifiers & (-8)));
        }
    }

    public void resolveStatements() {
        Statement[] statementArr = this.statements;
        if (statementArr != null) {
            int length = statementArr.length;
            for (int i = 0; i < length; i++) {
                this.statements[i].resolve(this.scope);
            }
            return;
        }
        if ((this.bits & 8) != 0) {
            if (isConstructor() && this.arguments == null) {
                return;
            }
            this.scope.problemReporter().undocumentedEmptyBlock(this.bodyStart - 1, this.bodyEnd + 1);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public void tagAsHavingErrors() {
        this.ignoreFurtherInvestigation = true;
    }

    void validateNullAnnotations(boolean z) {
        MethodBinding methodBinding = this.binding;
        if (methodBinding == null) {
            return;
        }
        int i = 0;
        if (!z) {
            if (methodBinding.parameterNonNullness != null) {
                int length = this.binding.parameters.length;
                while (i < length) {
                    if (this.binding.parameterNonNullness[i] != null) {
                        if (!this.scope.validateNullAnnotation(this.binding.parameterNonNullness[i].booleanValue() ? 72057594037927936L : 36028797018963968L, this.arguments[i].type, this.arguments[i].annotations)) {
                            this.binding.parameterNonNullness[i] = null;
                        }
                    }
                    i++;
                }
                return;
            }
            return;
        }
        int length2 = methodBinding.parameters.length;
        while (i < length2) {
            this.scope.validateNullAnnotation(this.binding.parameters[i].tagBits, this.arguments[i].type, this.arguments[i].annotations);
            i++;
        }
    }
}
