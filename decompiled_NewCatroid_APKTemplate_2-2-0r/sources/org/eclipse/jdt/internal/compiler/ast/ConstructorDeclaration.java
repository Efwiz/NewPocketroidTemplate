package org.eclipse.jdt.internal.compiler.ast;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.eclipse.jdt.internal.compiler.codegen.StackMapFrameCodeStream;
import org.eclipse.jdt.internal.compiler.flow.ExceptionHandlingFlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.InitializationFlowContext;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.NestedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.SyntheticArgumentBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.parser.Parser;
import org.eclipse.jdt.internal.compiler.problem.AbortMethod;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class ConstructorDeclaration extends AbstractMethodDeclaration {
    public ExplicitConstructorCall constructorCall;
    public TypeParameter[] typeParameters;

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public boolean isConstructor() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public boolean isInitializationMethod() {
        return true;
    }

    public ConstructorDeclaration(CompilationResult compilationResult) {
        super(compilationResult);
    }

    /* JADX WARN: Code duplicated, block: B:45:0x008b  */
    public void analyseCode(ClassScope classScope, InitializationFlowContext initializationFlowContext, FlowInfo flowInfo, int i) {
        UnconditionalFlowInfo unconditionalFlowInfoAnalyseCode;
        ASTNode aSTNodeDeclarationOf;
        ArrayList arrayList;
        int size;
        ExplicitConstructorCall explicitConstructorCall;
        MethodBinding exactConstructor;
        if (this.ignoreFurtherInvestigation) {
            return;
        }
        int iReachMode = flowInfo.reachMode();
        flowInfo.setReachMode(i);
        MethodBinding methodBinding = this.binding;
        if (methodBinding != null && (this.bits & 128) == 0 && !methodBinding.isUsed() && (!methodBinding.isPrivate() ? methodBinding.isOrEnclosedByPrivateType() : (this.binding.declaringClass.tagBits & 1152921504606846976L) != 0) && (explicitConstructorCall = this.constructorCall) != null) {
            if (explicitConstructorCall.accessMode != 3) {
                ReferenceBinding referenceBindingSuperclass = methodBinding.declaringClass.superclass();
                if (referenceBindingSuperclass != null && (exactConstructor = referenceBindingSuperclass.getExactConstructor(Binding.NO_PARAMETERS)) != null && exactConstructor.canBeSeenBy(SuperReference.implicitSuperConstructorCall(), this.scope)) {
                    ReferenceBinding referenceBinding = methodBinding.declaringClass;
                    if (!methodBinding.isPublic() || methodBinding.parameters.length != 0 || !referenceBinding.isStatic() || referenceBinding.findSuperTypeOriginatingFrom(56, false) == null) {
                        this.scope.problemReporter().unusedPrivateConstructor(this);
                    }
                }
            } else {
                this.scope.problemReporter().unusedPrivateConstructor(this);
            }
        }
        if (isRecursive(null)) {
            this.scope.problemReporter().recursiveConstructorInvocation(this.constructorCall);
        }
        if (this.typeParameters != null && !this.scope.referenceCompilationUnit().compilationResult.hasSyntaxError) {
            int length = this.typeParameters.length;
            for (int i2 = 0; i2 < length; i2++) {
                TypeParameter typeParameter = this.typeParameters[i2];
                if ((typeParameter.binding.modifiers & 134217728) == 0) {
                    this.scope.problemReporter().unusedTypeParameter(typeParameter);
                }
            }
        }
        try {
            ExceptionHandlingFlowContext exceptionHandlingFlowContext = new ExceptionHandlingFlowContext(initializationFlowContext.parent, this, this.binding.thrownExceptions, initializationFlowContext, this.scope, FlowInfo.DEAD_END);
            initializationFlowContext.checkInitializerExceptions(this.scope, exceptionHandlingFlowContext, flowInfo);
            if (this.binding.declaringClass.isAnonymousType() && (arrayList = exceptionHandlingFlowContext.extendedExceptions) != null && (size = arrayList.size()) > 0) {
                ReferenceBinding[] referenceBindingArr = new ReferenceBinding[size];
                arrayList.toArray(referenceBindingArr);
                this.binding.thrownExceptions = referenceBindingArr;
            }
            analyseArguments(classScope.environment(), flowInfo, this.arguments, this.binding);
            ExplicitConstructorCall explicitConstructorCall2 = this.constructorCall;
            if (explicitConstructorCall2 != null) {
                if (explicitConstructorCall2.accessMode == 3) {
                    for (FieldBinding fieldBinding : this.binding.declaringClass.fields()) {
                        if (!fieldBinding.isStatic()) {
                            flowInfo.markAsDefinitelyAssigned(fieldBinding);
                        }
                    }
                }
                unconditionalFlowInfoAnalyseCode = this.constructorCall.analyseCode(this.scope, exceptionHandlingFlowContext, flowInfo);
            } else {
                unconditionalFlowInfoAnalyseCode = flowInfo;
            }
            unconditionalFlowInfoAnalyseCode.setReachMode(iReachMode);
            if (this.statements != null) {
                boolean z = this.scope.compilerOptions().enableSyntacticNullAnalysisForFields;
                int iComplainIfUnreachable = (iReachMode & 3) == 0 ? 0 : 1;
                int length2 = this.statements.length;
                for (int i3 = 0; i3 < length2; i3++) {
                    Statement statement = this.statements[i3];
                    iComplainIfUnreachable = statement.complainIfUnreachable(unconditionalFlowInfoAnalyseCode, this.scope, iComplainIfUnreachable, true);
                    if (iComplainIfUnreachable < 2) {
                        unconditionalFlowInfoAnalyseCode = statement.analyseCode(this.scope, exceptionHandlingFlowContext, unconditionalFlowInfoAnalyseCode);
                    }
                    if (z) {
                        exceptionHandlingFlowContext.expireNullCheckedFieldInfo();
                    }
                }
            }
            if ((unconditionalFlowInfoAnalyseCode.tagBits & 1) == 0) {
                this.bits |= 64;
            }
            ExplicitConstructorCall explicitConstructorCall3 = this.constructorCall;
            UnconditionalFlowInfo unconditionalFlowInfo = unconditionalFlowInfoAnalyseCode;
            if (explicitConstructorCall3 != null && explicitConstructorCall3.accessMode != 3) {
                UnconditionalFlowInfo unconditionalFlowInfoMergedWith = unconditionalFlowInfoAnalyseCode.mergedWith(exceptionHandlingFlowContext.initsOnReturn);
                for (FieldBinding fieldBinding2 : this.binding.declaringClass.fields()) {
                    if (fieldBinding2.isStatic() || unconditionalFlowInfoMergedWith.isDefinitelyAssigned(fieldBinding2)) {
                        unconditionalFlowInfo = unconditionalFlowInfoAnalyseCode;
                    } else if (fieldBinding2.isFinal()) {
                        ProblemReporter problemReporter = this.scope.problemReporter();
                        if ((this.bits & 128) != 0) {
                            unconditionalFlowInfo = unconditionalFlowInfoAnalyseCode;
                            aSTNodeDeclarationOf = this.scope.referenceType().declarationOf(fieldBinding2.original());
                        } else {
                            unconditionalFlowInfo = unconditionalFlowInfoAnalyseCode;
                            aSTNodeDeclarationOf = this;
                        }
                        problemReporter.uninitializedBlankFinalField(fieldBinding2, aSTNodeDeclarationOf);
                    } else if (fieldBinding2.isNonNull() || fieldBinding2.type.isFreeTypeVariable()) {
                        unconditionalFlowInfo = unconditionalFlowInfoAnalyseCode;
                        unconditionalFlowInfo = unconditionalFlowInfoAnalyseCode;
                        FieldDeclaration fieldDeclarationDeclarationOf = this.scope.referenceType().declarationOf(fieldBinding2.original());
                        if (!isValueProvidedUsingAnnotation(fieldDeclarationDeclarationOf)) {
                            this.scope.problemReporter().uninitializedNonNullField(fieldBinding2, (this.bits & 128) != 0 ? fieldDeclarationDeclarationOf : this);
                        }
                    } else {
                        unconditionalFlowInfo = unconditionalFlowInfoAnalyseCode;
                    }
                }
                unconditionalFlowInfo = unconditionalFlowInfoAnalyseCode;
                unconditionalFlowInfo = unconditionalFlowInfoMergedWith;
            }
            unconditionalFlowInfo = unconditionalFlowInfoAnalyseCode;
            exceptionHandlingFlowContext.complainIfUnusedExceptionHandlers(this);
            this.scope.checkUnusedParameters(this.binding);
            this.scope.checkUnclosedCloseables(unconditionalFlowInfo, null, null, null);
        } catch (AbortMethod unused) {
            this.ignoreFurtherInvestigation = true;
        }
    }

    boolean isValueProvidedUsingAnnotation(FieldDeclaration fieldDeclaration) {
        if (fieldDeclaration.annotations != null) {
            int length = fieldDeclaration.annotations.length;
            for (int i = 0; i < length; i++) {
                Annotation annotation = fieldDeclaration.annotations[i];
                if (annotation.resolvedType.f626id == 80) {
                    return true;
                }
                if (annotation.resolvedType.f626id == 81) {
                    MemberValuePair[] memberValuePairArrMemberValuePairs = annotation.memberValuePairs();
                    if (memberValuePairArrMemberValuePairs == Annotation.NoValuePairs) {
                        return true;
                    }
                    for (int i2 = 0; i2 < memberValuePairArrMemberValuePairs.length; i2++) {
                        if (CharOperation.equals(memberValuePairArrMemberValuePairs[i2].f618name, TypeConstants.OPTIONAL)) {
                            return memberValuePairArrMemberValuePairs[i2].value instanceof FalseLiteral;
                        }
                    }
                } else if (annotation.resolvedType.f626id != 82) {
                    continue;
                } else {
                    MemberValuePair[] memberValuePairArrMemberValuePairs2 = annotation.memberValuePairs();
                    if (memberValuePairArrMemberValuePairs2 == Annotation.NoValuePairs) {
                        return true;
                    }
                    for (int i3 = 0; i3 < memberValuePairArrMemberValuePairs2.length; i3++) {
                        if (CharOperation.equals(memberValuePairArrMemberValuePairs2[i3].f618name, TypeConstants.REQUIRED)) {
                            return memberValuePairArrMemberValuePairs2[i3].value instanceof TrueLiteral;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public void generateCode(ClassScope classScope, ClassFile classFile) {
        CompilationResult compilationResult;
        int i;
        int i2;
        boolean z;
        TypeDeclaration typeDeclaration;
        if (this.ignoreFurtherInvestigation) {
            if (this.binding == null) {
                return;
            }
            CategorizedProblem[] problems = this.scope.referenceCompilationUnit().compilationResult.getProblems();
            int length = problems.length;
            CategorizedProblem[] categorizedProblemArr = new CategorizedProblem[length];
            System.arraycopy(problems, 0, categorizedProblemArr, 0, length);
            classFile.addProblemConstructor(this, this.binding, categorizedProblemArr);
            return;
        }
        if (classScope == null || (typeDeclaration = classScope.referenceContext) == null) {
            compilationResult = null;
            i = 0;
            i2 = 0;
        } else {
            compilationResult = typeDeclaration.compilationResult();
            i = compilationResult.problemCount;
            i2 = 0;
        }
        int i3 = i2;
        do {
            try {
                i2 = classFile.contentsOffset;
                internalGenerateCode(classScope, classFile);
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
                    i3 = 1;
                }
            }
            z = false;
        } while (z);
        if (i3 != 0) {
            CategorizedProblem[] allProblems = this.scope.referenceCompilationUnit().compilationResult.getAllProblems();
            int length2 = allProblems.length;
            CategorizedProblem[] categorizedProblemArr2 = new CategorizedProblem[length2];
            System.arraycopy(allProblems, 0, categorizedProblemArr2, 0, length2);
            classFile.addProblemConstructor(this, this.binding, categorizedProblemArr2, i2);
        }
    }

    public void generateSyntheticFieldInitializationsIfNecessary(MethodScope methodScope, CodeStream codeStream, ReferenceBinding referenceBinding) {
        if (referenceBinding.isNestedType()) {
            NestedTypeBinding nestedTypeBinding = (NestedTypeBinding) referenceBinding;
            SyntheticArgumentBinding[] syntheticArgumentBindingArrSyntheticEnclosingInstances = nestedTypeBinding.syntheticEnclosingInstances();
            if (syntheticArgumentBindingArrSyntheticEnclosingInstances != null) {
                for (SyntheticArgumentBinding syntheticArgumentBinding : syntheticArgumentBindingArrSyntheticEnclosingInstances) {
                    if (syntheticArgumentBinding.matchingField != null) {
                        codeStream.aload_0();
                        codeStream.load(syntheticArgumentBinding);
                        codeStream.fieldAccess(Opcodes.OPC_putfield, syntheticArgumentBinding.matchingField, null);
                    }
                }
            }
            SyntheticArgumentBinding[] syntheticArgumentBindingArrSyntheticOuterLocalVariables = nestedTypeBinding.syntheticOuterLocalVariables();
            if (syntheticArgumentBindingArrSyntheticOuterLocalVariables != null) {
                for (SyntheticArgumentBinding syntheticArgumentBinding2 : syntheticArgumentBindingArrSyntheticOuterLocalVariables) {
                    if (syntheticArgumentBinding2.matchingField != null) {
                        codeStream.aload_0();
                        codeStream.load(syntheticArgumentBinding2);
                        codeStream.fieldAccess(Opcodes.OPC_putfield, syntheticArgumentBinding2.matchingField, null);
                    }
                }
            }
        }
    }

    private void internalGenerateCode(ClassScope classScope, ClassFile classFile) {
        classFile.generateMethodInfoHeader(this.binding);
        int i = classFile.contentsOffset;
        int iGenerateMethodInfoAttributes = classFile.generateMethodInfoAttributes(this.binding);
        if (!this.binding.isNative() && !this.binding.isAbstract()) {
            TypeDeclaration typeDeclaration = classScope.referenceContext;
            int i2 = classFile.contentsOffset;
            classFile.generateCodeAttributeHeader();
            CodeStream codeStream = classFile.codeStream;
            codeStream.reset(this, classFile);
            ReferenceBinding referenceBinding = this.binding.declaringClass;
            int i3 = referenceBinding.isEnum() ? 2 : 0;
            int enclosingInstancesSlotSize = i3 + 1;
            if (referenceBinding.isNestedType()) {
                this.scope.extraSyntheticArguments = referenceBinding.syntheticOuterLocalVariables();
                this.scope.computeLocalVariablePositions(referenceBinding.getEnclosingInstancesSlotSize() + 1 + i3, codeStream);
                enclosingInstancesSlotSize = enclosingInstancesSlotSize + referenceBinding.getEnclosingInstancesSlotSize() + referenceBinding.getOuterLocalVariablesSlotSize();
            } else {
                this.scope.computeLocalVariablePositions(enclosingInstancesSlotSize, codeStream);
            }
            if (this.arguments != null) {
                int length = this.arguments.length;
                for (int i4 = 0; i4 < length; i4++) {
                    LocalVariableBinding localVariableBinding = this.arguments[i4].binding;
                    codeStream.addVisibleLocalVariable(localVariableBinding);
                    localVariableBinding.recordInitializationStartPC(0);
                    int i5 = localVariableBinding.type.f626id;
                    enclosingInstancesSlotSize = (i5 == 7 || i5 == 8) ? enclosingInstancesSlotSize + 2 : enclosingInstancesSlotSize + 1;
                }
            }
            MethodScope methodScope = typeDeclaration.initializerScope;
            methodScope.computeLocalVariablePositions(enclosingInstancesSlotSize, codeStream);
            ExplicitConstructorCall explicitConstructorCall = this.constructorCall;
            boolean z = explicitConstructorCall == null || explicitConstructorCall.accessMode != 3;
            boolean z2 = this.scope.compilerOptions().targetJDK >= ClassFileConstants.JDK1_4;
            if (z && z2) {
                generateSyntheticFieldInitializationsIfNecessary(this.scope, codeStream, referenceBinding);
                codeStream.recordPositionsFrom(0, this.bodyStart);
            }
            ExplicitConstructorCall explicitConstructorCall2 = this.constructorCall;
            if (explicitConstructorCall2 != null) {
                explicitConstructorCall2.generateCode(this.scope, codeStream);
            }
            if (z) {
                if (!z2) {
                    generateSyntheticFieldInitializationsIfNecessary(this.scope, codeStream, referenceBinding);
                }
                if (typeDeclaration.fields != null) {
                    int length2 = typeDeclaration.fields.length;
                    for (int i6 = 0; i6 < length2; i6++) {
                        FieldDeclaration fieldDeclaration = typeDeclaration.fields[i6];
                        if (!fieldDeclaration.isStatic()) {
                            fieldDeclaration.generateCode(methodScope, codeStream);
                        }
                    }
                }
            }
            if (this.statements != null) {
                int length3 = this.statements.length;
                for (int i7 = 0; i7 < length3; i7++) {
                    this.statements[i7].generateCode(this.scope, codeStream);
                }
            }
            if (this.ignoreFurtherInvestigation) {
                throw new AbortMethod(this.scope.referenceCompilationUnit().compilationResult, null);
            }
            if ((this.bits & 64) != 0) {
                codeStream.return_();
            }
            codeStream.exitUserScope(this.scope);
            codeStream.recordPositionsFrom(0, this.bodyEnd);
            try {
                classFile.completeCodeAttribute(i2);
                iGenerateMethodInfoAttributes++;
                if ((codeStream instanceof StackMapFrameCodeStream) && z && typeDeclaration.fields != null) {
                    ((StackMapFrameCodeStream) codeStream).resetSecretLocals();
                }
            } catch (NegativeArraySizeException unused) {
                throw new AbortMethod(this.scope.referenceCompilationUnit().compilationResult, null);
            }
        }
        classFile.completeMethodInfo(this.binding, i, iGenerateMethodInfoAttributes);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public void getAllAnnotationContexts(int i, List list) {
        SingleTypeReference singleTypeReference = new SingleTypeReference(this.selector, 0L);
        singleTypeReference.resolvedType = this.binding.declaringClass;
        TypeReference.AnnotationCollector annotationCollector = new TypeReference.AnnotationCollector(singleTypeReference, i, list);
        int length = this.annotations.length;
        for (int i2 = 0; i2 < length; i2++) {
            this.annotations[i2].traverse(annotationCollector, (BlockScope) null);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public boolean isDefaultConstructor() {
        return (this.bits & 128) != 0;
    }

    public boolean isRecursive(ArrayList arrayList) {
        ExplicitConstructorCall explicitConstructorCall;
        ConstructorDeclaration constructorDeclaration;
        if (this.binding == null || (explicitConstructorCall = this.constructorCall) == null || explicitConstructorCall.binding == null || this.constructorCall.isSuperAccess() || !this.constructorCall.binding.isValidBinding() || (constructorDeclaration = (ConstructorDeclaration) this.scope.referenceType().declarationOf(this.constructorCall.binding.original())) == null) {
            return false;
        }
        if (this == constructorDeclaration) {
            return true;
        }
        if (arrayList == null) {
            arrayList = new ArrayList(1);
        } else {
            int iIndexOf = arrayList.indexOf(this);
            if (iIndexOf >= 0) {
                return iIndexOf == 0;
            }
        }
        arrayList.add(this);
        return constructorDeclaration.isRecursive(arrayList);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public void parseStatements(Parser parser, CompilationUnitDeclaration compilationUnitDeclaration) {
        if ((this.bits & 128) != 0 && this.constructorCall == null) {
            ExplicitConstructorCall explicitConstructorCallImplicitSuperConstructorCall = SuperReference.implicitSuperConstructorCall();
            this.constructorCall = explicitConstructorCallImplicitSuperConstructorCall;
            explicitConstructorCallImplicitSuperConstructorCall.sourceStart = this.sourceStart;
            this.constructorCall.sourceEnd = this.sourceEnd;
            return;
        }
        parser.parse(this, compilationUnitDeclaration, false);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public StringBuffer printBody(int i, StringBuffer stringBuffer) {
        stringBuffer.append(" {");
        if (this.constructorCall != null) {
            stringBuffer.append('\n');
            this.constructorCall.printStatement(i, stringBuffer);
        }
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

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public void resolveJavadoc() {
        if (this.binding == null || this.javadoc != null) {
            super.resolveJavadoc();
            return;
        }
        if ((this.bits & 128) != 0 || this.binding.declaringClass == null || this.binding.declaringClass.isLocalType()) {
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

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public void resolveStatements() {
        SourceTypeBinding sourceTypeBindingEnclosingSourceType = this.scope.enclosingSourceType();
        if (!CharOperation.equals(sourceTypeBindingEnclosingSourceType.sourceName, this.selector)) {
            this.scope.problemReporter().missingReturnType(this);
        }
        if (this.binding != null && !this.binding.isPrivate()) {
            sourceTypeBindingEnclosingSourceType.tagBits |= 1152921504606846976L;
        }
        if (this.constructorCall != null) {
            if (sourceTypeBindingEnclosingSourceType.f626id == 1 && this.constructorCall.accessMode != 3) {
                if (this.constructorCall.accessMode == 2) {
                    this.scope.problemReporter().cannotUseSuperInJavaLangObject(this.constructorCall);
                }
                this.constructorCall = null;
            } else {
                this.constructorCall.resolve(this.scope);
            }
        }
        if ((this.modifiers & 16777216) != 0) {
            this.scope.problemReporter().methodNeedBody(this);
        }
        super.resolveStatements();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        if (aSTVisitor.visit(this, classScope)) {
            if (this.javadoc != null) {
                this.javadoc.traverse(aSTVisitor, this.scope);
            }
            if (this.annotations != null) {
                int length = this.annotations.length;
                for (int i = 0; i < length; i++) {
                    this.annotations[i].traverse(aSTVisitor, this.scope);
                }
            }
            TypeParameter[] typeParameterArr = this.typeParameters;
            if (typeParameterArr != null) {
                int length2 = typeParameterArr.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    this.typeParameters[i2].traverse(aSTVisitor, this.scope);
                }
            }
            if (this.arguments != null) {
                int length3 = this.arguments.length;
                for (int i3 = 0; i3 < length3; i3++) {
                    this.arguments[i3].traverse(aSTVisitor, this.scope);
                }
            }
            if (this.thrownExceptions != null) {
                int length4 = this.thrownExceptions.length;
                for (int i4 = 0; i4 < length4; i4++) {
                    this.thrownExceptions[i4].traverse(aSTVisitor, this.scope);
                }
            }
            ExplicitConstructorCall explicitConstructorCall = this.constructorCall;
            if (explicitConstructorCall != null) {
                explicitConstructorCall.traverse(aSTVisitor, this.scope);
            }
            if (this.statements != null) {
                int length5 = this.statements.length;
                for (int i5 = 0; i5 < length5; i5++) {
                    this.statements[i5].traverse(aSTVisitor, this.scope);
                }
            }
        }
        aSTVisitor.endVisit(this, classScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public TypeParameter[] typeParameters() {
        return this.typeParameters;
    }
}
