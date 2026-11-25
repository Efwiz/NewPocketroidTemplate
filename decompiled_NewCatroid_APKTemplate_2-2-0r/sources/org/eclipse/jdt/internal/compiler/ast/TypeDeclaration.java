package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import net.bytebuddy.description.method.ParameterDescription;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.InitializationFlowContext;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.CompilationUnitScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.MemberTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.NestedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.SyntheticArgumentBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;
import org.eclipse.jdt.internal.compiler.parser.Parser;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilation;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilationUnit;
import org.eclipse.jdt.internal.compiler.problem.AbortMethod;
import org.eclipse.jdt.internal.compiler.problem.AbortType;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.problem.ProblemSeverities;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class TypeDeclaration extends Statement implements ProblemSeverities, ReferenceContext {
    public static final int ANNOTATION_TYPE_DECL = 4;
    public static final int CLASS_DECL = 1;
    public static final int ENUM_DECL = 3;
    public static final int INTERFACE_DECL = 2;
    public QualifiedAllocationExpression allocation;
    public Annotation[] annotations;
    public SourceTypeBinding binding;
    public int bodyEnd;
    public int bodyStart;
    public CompilationResult compilationResult;
    public int declarationSourceEnd;
    public int declarationSourceStart;
    public TypeDeclaration enclosingType;
    public int enumConstantsCounter;
    public FieldBinding enumValuesSyntheticfield;
    public FieldDeclaration[] fields;
    public MethodScope initializerScope;
    public Javadoc javadoc;
    public int maxFieldCount;
    public TypeDeclaration[] memberTypes;
    public AbstractMethodDeclaration[] methods;
    public MethodDeclaration[] missingAbstractMethods;
    public int modifiersSourceStart;

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    public char[] f619name;
    public ClassScope scope;
    public MethodScope staticInitializerScope;
    public TypeReference[] superInterfaces;
    public TypeReference superclass;
    public TypeParameter[] typeParameters;
    public int modifiers = 0;
    public int functionalExpressionsCount = 0;
    public boolean ignoreFurtherInvestigation = false;

    public static final int kind(int i) {
        int i2 = i & 25088;
        if (i2 == 512) {
            return 2;
        }
        if (i2 != 8704) {
            return i2 != 16384 ? 1 : 3;
        }
        return 4;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public void tagAsHavingIgnoredMandatoryErrors(int i) {
    }

    public TypeDeclaration(CompilationResult compilationResult) {
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
        if (i == 16) {
            throw new AbortMethod(this.compilationResult, categorizedProblem);
        }
        throw new AbortType(this.compilationResult, categorizedProblem);
    }

    public final void addClinit() {
        AbstractMethodDeclaration[] abstractMethodDeclarationArr;
        if (needClassInitMethod()) {
            AbstractMethodDeclaration[] abstractMethodDeclarationArr2 = this.methods;
            if (abstractMethodDeclarationArr2 == null) {
                abstractMethodDeclarationArr = new AbstractMethodDeclaration[1];
            } else {
                int length = abstractMethodDeclarationArr2.length;
                AbstractMethodDeclaration[] abstractMethodDeclarationArr3 = new AbstractMethodDeclaration[length + 1];
                System.arraycopy(abstractMethodDeclarationArr2, 0, abstractMethodDeclarationArr3, 1, length);
                abstractMethodDeclarationArr = abstractMethodDeclarationArr3;
            }
            Clinit clinit = new Clinit(this.compilationResult);
            abstractMethodDeclarationArr[0] = clinit;
            int i = this.sourceStart;
            clinit.sourceStart = i;
            clinit.declarationSourceStart = i;
            int i2 = this.sourceEnd;
            clinit.sourceEnd = i2;
            clinit.declarationSourceEnd = i2;
            clinit.bodyEnd = this.sourceEnd;
            this.methods = abstractMethodDeclarationArr;
        }
    }

    public MethodDeclaration addMissingAbstractMethodFor(MethodBinding methodBinding) {
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        int length = typeBindingArr.length;
        MethodDeclaration methodDeclaration = new MethodDeclaration(this.compilationResult);
        methodDeclaration.selector = methodBinding.selector;
        methodDeclaration.sourceStart = this.sourceStart;
        methodDeclaration.sourceEnd = this.sourceEnd;
        methodDeclaration.modifiers = methodBinding.getAccessFlags() & (-1025);
        if (length > 0) {
            Argument[] argumentArr = new Argument[length];
            methodDeclaration.arguments = argumentArr;
            int i = length;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                argumentArr[i] = new Argument((String.valueOf(ParameterDescription.NAME_PREFIX) + i).toCharArray(), 0L, null, 0);
            }
        }
        MethodDeclaration[] methodDeclarationArr = this.missingAbstractMethods;
        if (methodDeclarationArr == null) {
            this.missingAbstractMethods = new MethodDeclaration[]{methodDeclaration};
        } else {
            MethodDeclaration[] methodDeclarationArr2 = new MethodDeclaration[methodDeclarationArr.length + 1];
            System.arraycopy(methodDeclarationArr, 0, methodDeclarationArr2, 1, methodDeclarationArr.length);
            methodDeclarationArr2[0] = methodDeclaration;
            this.missingAbstractMethods = methodDeclarationArr2;
        }
        int i2 = methodDeclaration.modifiers | 4096;
        char[] cArr = methodBinding.selector;
        TypeBinding typeBinding = methodBinding.returnType;
        if (length == 0) {
            typeBindingArr = Binding.NO_PARAMETERS;
        }
        methodDeclaration.binding = new MethodBinding(i2, cArr, typeBinding, typeBindingArr, methodBinding.thrownExceptions, this.binding);
        methodDeclaration.scope = new MethodScope(this.scope, methodDeclaration, true);
        methodDeclaration.bindArguments();
        return methodDeclaration;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        if (this.ignoreFurtherInvestigation) {
            return flowInfo;
        }
        try {
            if ((flowInfo.tagBits & 1) == 0) {
                this.bits |= Integer.MIN_VALUE;
                LocalTypeBinding localTypeBinding = (LocalTypeBinding) this.binding;
                localTypeBinding.setConstantPoolName(blockScope.compilationUnitScope().computeConstantPoolName(localTypeBinding));
            }
            manageEnclosingInstanceAccessIfNecessary(blockScope, flowInfo);
            updateMaxFieldCount();
            internalAnalyseCode(flowContext, flowInfo);
        } catch (AbortType unused) {
            this.ignoreFurtherInvestigation = true;
        }
        return flowInfo;
    }

    public void analyseCode(ClassScope classScope) {
        if (this.ignoreFurtherInvestigation) {
            return;
        }
        try {
            updateMaxFieldCount();
            internalAnalyseCode(null, FlowInfo.initial(this.maxFieldCount));
        } catch (AbortType unused) {
            this.ignoreFurtherInvestigation = true;
        }
    }

    public void analyseCode(ClassScope classScope, FlowContext flowContext, FlowInfo flowInfo) {
        if (this.ignoreFurtherInvestigation) {
            return;
        }
        try {
            if ((flowInfo.tagBits & 1) == 0) {
                this.bits |= Integer.MIN_VALUE;
                LocalTypeBinding localTypeBinding = (LocalTypeBinding) this.binding;
                localTypeBinding.setConstantPoolName(classScope.compilationUnitScope().computeConstantPoolName(localTypeBinding));
            }
            manageEnclosingInstanceAccessIfNecessary(classScope, flowInfo);
            updateMaxFieldCount();
            internalAnalyseCode(flowContext, flowInfo);
        } catch (AbortType unused) {
            this.ignoreFurtherInvestigation = true;
        }
    }

    public void analyseCode(CompilationUnitScope compilationUnitScope) {
        if (this.ignoreFurtherInvestigation) {
            return;
        }
        try {
            internalAnalyseCode(null, FlowInfo.initial(this.maxFieldCount));
        } catch (AbortType unused) {
            this.ignoreFurtherInvestigation = true;
        }
    }

    public boolean checkConstructors(Parser parser) {
        AbstractMethodDeclaration[] abstractMethodDeclarationArr = this.methods;
        boolean z = false;
        if (abstractMethodDeclarationArr != null) {
            int length = abstractMethodDeclarationArr.length;
            while (true) {
                length--;
                if (length < 0) {
                    break;
                }
                AbstractMethodDeclaration abstractMethodDeclaration = this.methods[length];
                if (abstractMethodDeclaration.isConstructor()) {
                    if (!CharOperation.equals(abstractMethodDeclaration.selector, this.f619name)) {
                        ConstructorDeclaration constructorDeclaration = (ConstructorDeclaration) abstractMethodDeclaration;
                        if (constructorDeclaration.constructorCall == null || constructorDeclaration.constructorCall.isImplicitSuper()) {
                            this.methods[length] = parser.convertToMethodDeclaration(constructorDeclaration, this.compilationResult);
                        }
                    } else {
                        int iKind = kind(this.modifiers);
                        if (iKind == 2) {
                            parser.problemReporter().interfaceCannotHaveConstructors((ConstructorDeclaration) abstractMethodDeclaration);
                        } else if (iKind == 4) {
                            parser.problemReporter().annotationTypeDeclarationCannotHaveConstructor((ConstructorDeclaration) abstractMethodDeclaration);
                        }
                        z = true;
                    }
                }
            }
        }
        return z;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public CompilationResult compilationResult() {
        return this.compilationResult;
    }

    public ConstructorDeclaration createDefaultConstructor(boolean z, boolean z2) {
        ConstructorDeclaration constructorDeclaration = new ConstructorDeclaration(this.compilationResult);
        constructorDeclaration.bits |= 128;
        constructorDeclaration.selector = this.f619name;
        constructorDeclaration.modifiers = this.modifiers & 7;
        int i = this.sourceStart;
        constructorDeclaration.sourceStart = i;
        constructorDeclaration.declarationSourceStart = i;
        int i2 = this.sourceEnd;
        constructorDeclaration.bodyEnd = i2;
        constructorDeclaration.sourceEnd = i2;
        constructorDeclaration.declarationSourceEnd = i2;
        if (z) {
            constructorDeclaration.constructorCall = SuperReference.implicitSuperConstructorCall();
            constructorDeclaration.constructorCall.sourceStart = this.sourceStart;
            constructorDeclaration.constructorCall.sourceEnd = this.sourceEnd;
        }
        if (z2) {
            AbstractMethodDeclaration[] abstractMethodDeclarationArr = this.methods;
            if (abstractMethodDeclarationArr == null) {
                this.methods = new AbstractMethodDeclaration[]{constructorDeclaration};
            } else {
                AbstractMethodDeclaration[] abstractMethodDeclarationArr2 = new AbstractMethodDeclaration[abstractMethodDeclarationArr.length + 1];
                System.arraycopy(abstractMethodDeclarationArr, 0, abstractMethodDeclarationArr2, 1, abstractMethodDeclarationArr.length);
                abstractMethodDeclarationArr2[0] = constructorDeclaration;
                this.methods = abstractMethodDeclarationArr2;
            }
        }
        return constructorDeclaration;
    }

    public MethodBinding createDefaultConstructorWithBinding(MethodBinding methodBinding, boolean z) throws Throwable {
        ReferenceBinding[] referenceBindingArrConvertToRawTypes;
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        int length = typeBindingArr.length;
        ConstructorDeclaration constructorDeclaration = new ConstructorDeclaration(this.compilationResult);
        constructorDeclaration.selector = new char[]{'x'};
        constructorDeclaration.sourceStart = this.sourceStart;
        constructorDeclaration.sourceEnd = this.sourceEnd;
        int i = this.modifiers & 7;
        if (methodBinding.isVarargs()) {
            i |= 128;
        }
        constructorDeclaration.modifiers = i;
        constructorDeclaration.bits |= 128;
        if (length > 0) {
            Argument[] argumentArr = new Argument[length];
            constructorDeclaration.arguments = argumentArr;
            int i2 = length;
            while (true) {
                i2--;
                if (i2 < 0) {
                    break;
                }
                argumentArr[i2] = new Argument((String.valueOf("$anonymous") + i2).toCharArray(), 0L, null, 0);
            }
        }
        constructorDeclaration.constructorCall = SuperReference.implicitSuperConstructorCall();
        constructorDeclaration.constructorCall.sourceStart = this.sourceStart;
        constructorDeclaration.constructorCall.sourceEnd = this.sourceEnd;
        if (length > 0) {
            Expression[] expressionArr = new Expression[length];
            constructorDeclaration.constructorCall.arguments = expressionArr;
            int i3 = length;
            while (true) {
                i3--;
                if (i3 < 0) {
                    break;
                }
                expressionArr[i3] = new SingleNameReference((String.valueOf("$anonymous") + i3).toCharArray(), 0L);
            }
        }
        AbstractMethodDeclaration[] abstractMethodDeclarationArr = this.methods;
        if (abstractMethodDeclarationArr == null) {
            this.methods = new AbstractMethodDeclaration[]{constructorDeclaration};
        } else {
            AbstractMethodDeclaration[] abstractMethodDeclarationArr2 = new AbstractMethodDeclaration[abstractMethodDeclarationArr.length + 1];
            System.arraycopy(abstractMethodDeclarationArr, 0, abstractMethodDeclarationArr2, 1, abstractMethodDeclarationArr.length);
            abstractMethodDeclarationArr2[0] = constructorDeclaration;
            this.methods = abstractMethodDeclarationArr2;
        }
        if (z) {
            referenceBindingArrConvertToRawTypes = this.scope.environment().convertToRawTypes(methodBinding.thrownExceptions, true, true);
        } else {
            referenceBindingArrConvertToRawTypes = methodBinding.thrownExceptions;
        }
        SourceTypeBinding sourceTypeBinding = this.binding;
        int i4 = constructorDeclaration.modifiers;
        if (length == 0) {
            typeBindingArr = Binding.NO_PARAMETERS;
        }
        constructorDeclaration.binding = new MethodBinding(i4, typeBindingArr, referenceBindingArrConvertToRawTypes, sourceTypeBinding);
        constructorDeclaration.binding.tagBits |= methodBinding.tagBits & 128;
        constructorDeclaration.binding.modifiers |= 67108864;
        if (methodBinding.parameterNonNullness != null && length > 0) {
            int length2 = methodBinding.parameterNonNullness.length;
            Boolean[] boolArr = methodBinding.parameterNonNullness;
            Boolean[] boolArr2 = new Boolean[length2];
            constructorDeclaration.binding.parameterNonNullness = boolArr2;
            System.arraycopy(boolArr, 0, boolArr2, 0, length2);
        }
        constructorDeclaration.scope = new MethodScope(this.scope, constructorDeclaration, true);
        constructorDeclaration.bindArguments();
        constructorDeclaration.constructorCall.resolve(constructorDeclaration.scope);
        MethodBinding[] methodBindingArrMethods = sourceTypeBinding.methods();
        int length3 = methodBindingArrMethods.length;
        int i5 = length3 + 1;
        MethodBinding[] methodBindingArr = new MethodBinding[i5];
        System.arraycopy(methodBindingArrMethods, 0, methodBindingArr, 1, length3);
        methodBindingArr[0] = constructorDeclaration.binding;
        if (i5 > 1) {
            ReferenceBinding.sortMethods(methodBindingArr, 0, i5);
        }
        sourceTypeBinding.setMethods(methodBindingArr);
        return constructorDeclaration.binding;
    }

    public FieldDeclaration declarationOf(FieldBinding fieldBinding) {
        FieldDeclaration[] fieldDeclarationArr;
        if (fieldBinding == null || (fieldDeclarationArr = this.fields) == null) {
            return null;
        }
        int length = fieldDeclarationArr.length;
        for (int i = 0; i < length; i++) {
            FieldDeclaration fieldDeclaration = this.fields[i];
            if (fieldDeclaration.binding == fieldBinding) {
                return fieldDeclaration;
            }
        }
        return null;
    }

    public TypeDeclaration declarationOf(MemberTypeBinding memberTypeBinding) {
        TypeDeclaration[] typeDeclarationArr;
        if (memberTypeBinding == null || (typeDeclarationArr = this.memberTypes) == null) {
            return null;
        }
        int length = typeDeclarationArr.length;
        for (int i = 0; i < length; i++) {
            TypeDeclaration typeDeclaration = this.memberTypes[i];
            if (TypeBinding.equalsEquals(typeDeclaration.binding, memberTypeBinding)) {
                return typeDeclaration;
            }
        }
        return null;
    }

    public AbstractMethodDeclaration declarationOf(MethodBinding methodBinding) {
        AbstractMethodDeclaration[] abstractMethodDeclarationArr;
        if (methodBinding == null || (abstractMethodDeclarationArr = this.methods) == null) {
            return null;
        }
        int length = abstractMethodDeclarationArr.length;
        for (int i = 0; i < length; i++) {
            AbstractMethodDeclaration abstractMethodDeclaration = this.methods[i];
            if (abstractMethodDeclaration.binding == methodBinding) {
                return abstractMethodDeclaration;
            }
        }
        return null;
    }

    public TypeDeclaration declarationOfType(char[][] cArr) {
        int length = cArr.length;
        if (length >= 1) {
            int i = 0;
            if (CharOperation.equals(cArr[0], this.f619name)) {
                if (length == 1) {
                    return this;
                }
                int i2 = length - 1;
                char[][] cArr2 = new char[i2][];
                System.arraycopy(cArr, 1, cArr2, 0, i2);
                while (true) {
                    TypeDeclaration[] typeDeclarationArr = this.memberTypes;
                    if (i >= typeDeclarationArr.length) {
                        return null;
                    }
                    TypeDeclaration typeDeclarationDeclarationOfType = typeDeclarationArr[i].declarationOfType(cArr2);
                    if (typeDeclarationDeclarationOfType != null) {
                        return typeDeclarationDeclarationOfType;
                    }
                    i++;
                }
            }
        }
        return null;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public CompilationUnitDeclaration getCompilationUnitDeclaration() {
        ClassScope classScope = this.scope;
        if (classScope != null) {
            return classScope.compilationUnitScope().referenceContext;
        }
        return null;
    }

    public void generateCode(ClassFile classFile) {
        if ((this.bits & 8192) != 0) {
            return;
        }
        this.bits |= 8192;
        if (this.ignoreFurtherInvestigation) {
            if (this.binding == null) {
                return;
            }
            ClassFile.createProblemType(this, this.scope.referenceCompilationUnit().compilationResult);
            return;
        }
        try {
            ClassFile newInstance = ClassFile.getNewInstance(this.binding);
            newInstance.initialize(this.binding, classFile, false);
            if (this.binding.isMemberType()) {
                newInstance.recordInnerClasses(this.binding);
            } else if (this.binding.isLocalType()) {
                classFile.recordInnerClasses(this.binding);
                newInstance.recordInnerClasses(this.binding);
            }
            for (TypeVariableBinding typeVariableBinding : this.binding.typeVariables()) {
                if ((typeVariableBinding.tagBits & 2048) != 0) {
                    Util.recordNestedType(newInstance, typeVariableBinding);
                }
            }
            newInstance.addFieldInfos();
            TypeDeclaration[] typeDeclarationArr = this.memberTypes;
            if (typeDeclarationArr != null) {
                int length = typeDeclarationArr.length;
                for (int i = 0; i < length; i++) {
                    TypeDeclaration typeDeclaration = this.memberTypes[i];
                    newInstance.recordInnerClasses(typeDeclaration.binding);
                    typeDeclaration.generateCode(this.scope, newInstance);
                }
            }
            newInstance.setForMethodInfos();
            AbstractMethodDeclaration[] abstractMethodDeclarationArr = this.methods;
            if (abstractMethodDeclarationArr != null) {
                int length2 = abstractMethodDeclarationArr.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    this.methods[i2].generateCode(this.scope, newInstance);
                }
            }
            newInstance.addSpecialMethods();
            if (this.ignoreFurtherInvestigation) {
                throw new AbortType(this.scope.referenceCompilationUnit().compilationResult, null);
            }
            newInstance.addAttributes();
            this.scope.referenceCompilationUnit().compilationResult.record(this.binding.constantPoolName(), newInstance);
        } catch (AbortType unused) {
            if (this.binding == null) {
                return;
            }
            ClassFile.createProblemType(this, this.scope.referenceCompilationUnit().compilationResult);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
        if ((this.bits & Integer.MIN_VALUE) != 0 && (this.bits & 8192) == 0) {
            int i = codeStream.position;
            SourceTypeBinding sourceTypeBinding = this.binding;
            if (sourceTypeBinding != null) {
                SyntheticArgumentBinding[] syntheticArgumentBindingArrSyntheticEnclosingInstances = ((NestedTypeBinding) sourceTypeBinding).syntheticEnclosingInstances();
                int length = syntheticArgumentBindingArrSyntheticEnclosingInstances == null ? 0 : syntheticArgumentBindingArrSyntheticEnclosingInstances.length;
                int i2 = 0;
                for (int i3 = 0; i3 < length; i3++) {
                    SyntheticArgumentBinding syntheticArgumentBinding = syntheticArgumentBindingArrSyntheticEnclosingInstances[i3];
                    i2++;
                    syntheticArgumentBinding.resolvedPosition = i2;
                    if (i2 > 255) {
                        blockScope.problemReporter().noMoreAvailableSpaceForArgument(syntheticArgumentBinding, blockScope.referenceType());
                    }
                }
            }
            generateCode(codeStream.classFile);
            codeStream.recordPositionsFrom(i, this.sourceStart);
        }
    }

    public void generateCode(ClassScope classScope, ClassFile classFile) {
        if ((this.bits & 8192) != 0) {
            return;
        }
        SourceTypeBinding sourceTypeBinding = this.binding;
        if (sourceTypeBinding != null) {
            SyntheticArgumentBinding[] syntheticArgumentBindingArrSyntheticEnclosingInstances = ((NestedTypeBinding) sourceTypeBinding).syntheticEnclosingInstances();
            int length = syntheticArgumentBindingArrSyntheticEnclosingInstances == null ? 0 : syntheticArgumentBindingArrSyntheticEnclosingInstances.length;
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                SyntheticArgumentBinding syntheticArgumentBinding = syntheticArgumentBindingArrSyntheticEnclosingInstances[i2];
                i++;
                syntheticArgumentBinding.resolvedPosition = i;
                if (i > 255) {
                    classScope.problemReporter().noMoreAvailableSpaceForArgument(syntheticArgumentBinding, classScope.referenceType());
                }
            }
        }
        generateCode(classFile);
    }

    public void generateCode(CompilationUnitScope compilationUnitScope) {
        generateCode((ClassFile) null);
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public boolean hasErrors() {
        return this.ignoreFurtherInvestigation;
    }

    private void internalAnalyseCode(FlowContext flowContext, FlowInfo flowInfo) {
        if (!this.binding.isUsed() && this.binding.isOrEnclosedByPrivateType() && !this.scope.referenceCompilationUnit().compilationResult.hasSyntaxError) {
            this.scope.problemReporter().unusedPrivateType(this);
        }
        if (this.typeParameters != null && !this.scope.referenceCompilationUnit().compilationResult.hasSyntaxError) {
            int length = this.typeParameters.length;
            for (int i = 0; i < length; i++) {
                TypeParameter typeParameter = this.typeParameters[i];
                if ((typeParameter.binding.modifiers & 134217728) == 0) {
                    this.scope.problemReporter().unusedTypeParameter(typeParameter);
                }
            }
        }
        FlowContext flowContext2 = flowContext instanceof InitializationFlowContext ? null : flowContext;
        InitializationFlowContext initializationFlowContext = new InitializationFlowContext(flowContext2, this, flowInfo, flowContext, this.initializerScope);
        InitializationFlowContext initializationFlowContext2 = new InitializationFlowContext(null, this, flowInfo, flowContext, this.staticInitializerScope);
        FlowInfo flowInfoUnconditionalFieldLessCopy = flowInfo.unconditionalFieldLessCopy();
        FlowInfo flowInfoUnconditionalFieldLessCopy2 = flowInfo.unconditionalFieldLessCopy();
        FieldDeclaration[] fieldDeclarationArr = this.fields;
        if (fieldDeclarationArr != null) {
            int length2 = fieldDeclarationArr.length;
            for (int i2 = 0; i2 < length2; i2++) {
                FieldDeclaration fieldDeclaration = this.fields[i2];
                if (fieldDeclaration.isStatic()) {
                    if ((flowInfoUnconditionalFieldLessCopy2.tagBits & 1) != 0) {
                        fieldDeclaration.bits &= Integer.MAX_VALUE;
                    }
                    initializationFlowContext2.handledExceptions = Binding.ANY_EXCEPTION;
                    flowInfoUnconditionalFieldLessCopy2 = fieldDeclaration.analyseCode(this.staticInitializerScope, (FlowContext) initializationFlowContext2, flowInfoUnconditionalFieldLessCopy2);
                    if (flowInfoUnconditionalFieldLessCopy2 == FlowInfo.DEAD_END) {
                        this.staticInitializerScope.problemReporter().initializerMustCompleteNormally(fieldDeclaration);
                        flowInfoUnconditionalFieldLessCopy2 = FlowInfo.initial(this.maxFieldCount).setReachMode(1);
                    }
                } else {
                    if ((flowInfoUnconditionalFieldLessCopy.tagBits & 1) != 0) {
                        fieldDeclaration.bits &= Integer.MAX_VALUE;
                    }
                    initializationFlowContext.handledExceptions = Binding.ANY_EXCEPTION;
                    flowInfoUnconditionalFieldLessCopy = fieldDeclaration.analyseCode(this.initializerScope, (FlowContext) initializationFlowContext, flowInfoUnconditionalFieldLessCopy);
                    if (flowInfoUnconditionalFieldLessCopy == FlowInfo.DEAD_END) {
                        this.initializerScope.problemReporter().initializerMustCompleteNormally(fieldDeclaration);
                        flowInfoUnconditionalFieldLessCopy = FlowInfo.initial(this.maxFieldCount).setReachMode(1);
                    }
                }
            }
        }
        TypeDeclaration[] typeDeclarationArr = this.memberTypes;
        if (typeDeclarationArr != null) {
            int length3 = typeDeclarationArr.length;
            for (int i3 = 0; i3 < length3; i3++) {
                if (flowContext != null) {
                    this.memberTypes[i3].analyseCode(this.scope, flowContext, flowInfoUnconditionalFieldLessCopy.copy().setReachMode(flowInfo.reachMode()));
                } else {
                    this.memberTypes[i3].analyseCode(this.scope);
                }
            }
        }
        if (this.methods != null) {
            UnconditionalFlowInfo unconditionalFlowInfoUnconditionalFieldLessCopy = flowInfo.unconditionalFieldLessCopy();
            FlowInfo flowInfoAddInitializationsFrom = flowInfoUnconditionalFieldLessCopy.unconditionalInits().discardNonFieldInitializations().addInitializationsFrom(unconditionalFlowInfoUnconditionalFieldLessCopy);
            int length4 = this.methods.length;
            for (int i4 = 0; i4 < length4; i4++) {
                AbstractMethodDeclaration abstractMethodDeclaration = this.methods[i4];
                if (!abstractMethodDeclaration.ignoreFurtherInvestigation) {
                    if (abstractMethodDeclaration.isInitializationMethod()) {
                        if (abstractMethodDeclaration.isStatic()) {
                            ((Clinit) abstractMethodDeclaration).analyseCode(this.scope, initializationFlowContext2, flowInfoUnconditionalFieldLessCopy2.unconditionalInits().discardNonFieldInitializations().addInitializationsFrom(unconditionalFlowInfoUnconditionalFieldLessCopy));
                        } else {
                            ((ConstructorDeclaration) abstractMethodDeclaration).analyseCode(this.scope, initializationFlowContext, flowInfoAddInitializationsFrom.copy(), flowInfo.reachMode());
                        }
                    } else {
                        ((MethodDeclaration) abstractMethodDeclaration).analyseCode(this.scope, flowContext2, flowInfo.copy());
                    }
                }
            }
        }
        if (!this.binding.isEnum() || this.binding.isAnonymousType()) {
            return;
        }
        this.enumValuesSyntheticfield = this.binding.addSyntheticFieldForEnumValues();
    }

    public void manageEnclosingInstanceAccessIfNecessary(BlockScope blockScope, FlowInfo flowInfo) {
        if ((flowInfo.tagBits & 1) != 0) {
            return;
        }
        NestedTypeBinding nestedTypeBinding = (NestedTypeBinding) this.binding;
        MethodScope methodScope = blockScope.methodScope();
        if (!methodScope.isStatic && !methodScope.isConstructorCall) {
            nestedTypeBinding.addSyntheticArgumentAndField(nestedTypeBinding.enclosingType());
        }
        if (nestedTypeBinding.isAnonymousType()) {
            ReferenceBinding referenceBinding = (ReferenceBinding) nestedTypeBinding.superclass.erasure();
            if (referenceBinding.enclosingType() != null && !referenceBinding.isStatic() && (!referenceBinding.isLocalType() || ((NestedTypeBinding) referenceBinding).getSyntheticField(referenceBinding.enclosingType(), true) != null || referenceBinding.isMemberType())) {
                nestedTypeBinding.addSyntheticArgument(referenceBinding.enclosingType());
            }
            if (methodScope.isStatic || !methodScope.isConstructorCall || blockScope.compilerOptions().complianceLevel < ClassFileConstants.JDK1_5) {
                return;
            }
            ReferenceBinding referenceBindingEnclosingType = nestedTypeBinding.enclosingType();
            if (referenceBindingEnclosingType.isNestedType()) {
                NestedTypeBinding nestedTypeBinding2 = (NestedTypeBinding) referenceBindingEnclosingType;
                SyntheticArgumentBinding syntheticArgument = nestedTypeBinding2.getSyntheticArgument(nestedTypeBinding2.enclosingType(), true, false);
                if (syntheticArgument != null) {
                    nestedTypeBinding.addSyntheticArgumentAndField(syntheticArgument);
                }
            }
        }
    }

    public void manageEnclosingInstanceAccessIfNecessary(ClassScope classScope, FlowInfo flowInfo) {
        if ((flowInfo.tagBits & 1) == 0) {
            SourceTypeBinding sourceTypeBinding = this.binding;
            ((NestedTypeBinding) sourceTypeBinding).addSyntheticArgumentAndField(sourceTypeBinding.enclosingType());
        }
    }

    public final boolean needClassInitMethod() {
        if ((this.bits & 1) != 0) {
            return true;
        }
        int iKind = kind(this.modifiers);
        if (iKind != 2) {
            if (iKind == 3) {
                return true;
            }
            if (iKind != 4) {
                FieldDeclaration[] fieldDeclarationArr = this.fields;
                if (fieldDeclarationArr != null) {
                    int length = fieldDeclarationArr.length;
                    do {
                        length--;
                        if (length < 0) {
                        }
                    } while ((this.fields[length].modifiers & 8) == 0);
                    return true;
                }
                return false;
            }
        }
        return this.fields != null;
    }

    public void parseMethods(Parser parser, CompilationUnitDeclaration compilationUnitDeclaration) {
        if (compilationUnitDeclaration.ignoreMethodBodies) {
            return;
        }
        TypeDeclaration[] typeDeclarationArr = this.memberTypes;
        if (typeDeclarationArr != null) {
            int length = typeDeclarationArr.length;
            for (int i = 0; i < length; i++) {
                TypeDeclaration typeDeclaration = this.memberTypes[i];
                typeDeclaration.parseMethods(parser, compilationUnitDeclaration);
                this.bits = (typeDeclaration.bits & 524288) | this.bits;
            }
        }
        AbstractMethodDeclaration[] abstractMethodDeclarationArr = this.methods;
        if (abstractMethodDeclarationArr != null) {
            int length2 = abstractMethodDeclarationArr.length;
            for (int i2 = 0; i2 < length2; i2++) {
                AbstractMethodDeclaration abstractMethodDeclaration = this.methods[i2];
                abstractMethodDeclaration.parseStatements(parser, compilationUnitDeclaration);
                this.bits = (abstractMethodDeclaration.bits & 524288) | this.bits;
            }
        }
        FieldDeclaration[] fieldDeclarationArr = this.fields;
        if (fieldDeclarationArr != null) {
            int length3 = fieldDeclarationArr.length;
            for (int i3 = 0; i3 < length3; i3++) {
                FieldDeclaration fieldDeclaration = this.fields[i3];
                if (fieldDeclaration.getKind() == 2) {
                    ((Initializer) fieldDeclaration).parseStatements(parser, this, compilationUnitDeclaration);
                    this.bits = (fieldDeclaration.bits & 524288) | this.bits;
                }
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public StringBuffer print(int i, StringBuffer stringBuffer) {
        Javadoc javadoc = this.javadoc;
        if (javadoc != null) {
            javadoc.print(i, stringBuffer);
        }
        if ((this.bits & 512) == 0) {
            printIndent(i, stringBuffer);
            printHeader(0, stringBuffer);
        }
        return printBody(i, stringBuffer);
    }

    public StringBuffer printBody(int i, StringBuffer stringBuffer) {
        stringBuffer.append(" {");
        int i2 = 0;
        if (this.memberTypes != null) {
            int i3 = 0;
            while (true) {
                TypeDeclaration[] typeDeclarationArr = this.memberTypes;
                if (i3 >= typeDeclarationArr.length) {
                    break;
                }
                if (typeDeclarationArr[i3] != null) {
                    stringBuffer.append('\n');
                    this.memberTypes[i3].print(i + 1, stringBuffer);
                }
                i3++;
            }
        }
        if (this.fields != null) {
            int i4 = 0;
            while (true) {
                FieldDeclaration[] fieldDeclarationArr = this.fields;
                if (i4 >= fieldDeclarationArr.length) {
                    break;
                }
                if (fieldDeclarationArr[i4] != null) {
                    stringBuffer.append('\n');
                    this.fields[i4].print(i + 1, stringBuffer);
                }
                i4++;
            }
        }
        if (this.methods != null) {
            while (true) {
                AbstractMethodDeclaration[] abstractMethodDeclarationArr = this.methods;
                if (i2 >= abstractMethodDeclarationArr.length) {
                    break;
                }
                if (abstractMethodDeclarationArr[i2] != null) {
                    stringBuffer.append('\n');
                    this.methods[i2].print(i + 1, stringBuffer);
                }
                i2++;
            }
        }
        stringBuffer.append('\n');
        return printIndent(i, stringBuffer).append('}');
    }

    /* JADX WARN: Code duplicated, block: B:37:0x008b  */
    /* JADX WARN: Code duplicated, block: B:38:0x008f  */
    public StringBuffer printHeader(int i, StringBuffer stringBuffer) {
        printModifiers(this.modifiers, stringBuffer);
        Annotation[] annotationArr = this.annotations;
        if (annotationArr != null) {
            printAnnotations(annotationArr, stringBuffer);
            stringBuffer.append(StringUtil.SPACE);
        }
        int iKind = kind(this.modifiers);
        if (iKind == 1) {
            stringBuffer.append(ExternalAnnotationProvider.CLASS_PREFIX);
        } else if (iKind == 2) {
            stringBuffer.append("interface ");
        } else if (iKind == 3) {
            stringBuffer.append("enum ");
        } else if (iKind == 4) {
            stringBuffer.append("@interface ");
        }
        stringBuffer.append(this.f619name);
        if (this.typeParameters != null) {
            stringBuffer.append("<");
            for (int i2 = 0; i2 < this.typeParameters.length; i2++) {
                if (i2 > 0) {
                    stringBuffer.append(", ");
                }
                this.typeParameters[i2].print(0, stringBuffer);
            }
            stringBuffer.append(">");
        }
        if (this.superclass != null) {
            stringBuffer.append(" extends ");
            this.superclass.print(0, stringBuffer);
        }
        TypeReference[] typeReferenceArr = this.superInterfaces;
        if (typeReferenceArr != null && typeReferenceArr.length > 0) {
            int iKind2 = kind(this.modifiers);
            if (iKind2 == 1) {
                stringBuffer.append(" implements ");
            } else if (iKind2 == 2) {
                stringBuffer.append(" extends ");
            } else if (iKind2 == 3) {
                stringBuffer.append(" implements ");
            } else if (iKind2 == 4) {
                stringBuffer.append(" extends ");
            }
            for (int i3 = 0; i3 < this.superInterfaces.length; i3++) {
                if (i3 > 0) {
                    stringBuffer.append(", ");
                }
                this.superInterfaces[i3].print(0, stringBuffer);
            }
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        return print(i, stringBuffer);
    }

    public int record(FunctionalExpression functionalExpression) {
        int i = this.functionalExpressionsCount;
        this.functionalExpressionsCount = i + 1;
        return i;
    }

    /* JADX WARN: Code duplicated, block: B:124:0x01c8 A[Catch: AbortType -> 0x0348, TryCatch #0 {AbortType -> 0x0348, blocks: (B:6:0x000a, B:8:0x001a, B:10:0x0021, B:12:0x0030, B:13:0x0039, B:15:0x0040, B:17:0x004a, B:18:0x0053, B:20:0x0059, B:21:0x0067, B:23:0x0079, B:25:0x007f, B:27:0x0087, B:33:0x0096, B:35:0x00a6, B:37:0x00ac, B:39:0x00b2, B:41:0x00b7, B:46:0x00c4, B:48:0x00d4, B:50:0x00e0, B:52:0x00e6, B:54:0x00ea, B:56:0x00f0, B:58:0x00f5, B:62:0x00fe, B:64:0x010c, B:66:0x0118, B:68:0x011e, B:70:0x0122, B:72:0x0128, B:74:0x012d, B:83:0x013f, B:86:0x0148, B:88:0x014e, B:89:0x0158, B:92:0x015f, B:94:0x0165, B:97:0x0176, B:99:0x017c, B:101:0x0180, B:104:0x0185, B:105:0x0191, B:107:0x0196, B:148:0x0217, B:150:0x021b, B:153:0x0220, B:155:0x022e, B:158:0x0235, B:161:0x023d, B:163:0x0244, B:164:0x024d, B:202:0x02d0, B:206:0x02d7, B:210:0x02de, B:212:0x02e4, B:213:0x02ed, B:215:0x02f1, B:218:0x02f6, B:219:0x0302, B:221:0x0306, B:223:0x030a, B:225:0x0310, B:226:0x0318, B:228:0x031e, B:230:0x0331, B:232:0x0335, B:233:0x0339, B:209:0x02dd, B:205:0x02d6, B:168:0x0259, B:170:0x025d, B:171:0x0266, B:173:0x026a, B:174:0x0274, B:177:0x027e, B:180:0x0285, B:182:0x028f, B:184:0x0293, B:185:0x029c, B:187:0x02a1, B:190:0x02a8, B:192:0x02b2, B:194:0x02b6, B:197:0x02bb, B:199:0x02bf, B:200:0x02ca, B:201:0x02cd, B:110:0x019f, B:141:0x0203, B:143:0x0209, B:145:0x020e, B:146:0x0211, B:144:0x020c, B:115:0x01ae, B:118:0x01b6, B:119:0x01b8, B:121:0x01bc, B:122:0x01c4, B:124:0x01c8, B:126:0x01cc, B:128:0x01d4, B:130:0x01d9, B:129:0x01d7, B:131:0x01dc, B:133:0x01e1, B:135:0x01e8, B:137:0x01f2, B:140:0x01fd), top: B:237:0x000a }] */
    /* JADX WARN: Code duplicated, block: B:126:0x01cc A[Catch: AbortType -> 0x0348, TryCatch #0 {AbortType -> 0x0348, blocks: (B:6:0x000a, B:8:0x001a, B:10:0x0021, B:12:0x0030, B:13:0x0039, B:15:0x0040, B:17:0x004a, B:18:0x0053, B:20:0x0059, B:21:0x0067, B:23:0x0079, B:25:0x007f, B:27:0x0087, B:33:0x0096, B:35:0x00a6, B:37:0x00ac, B:39:0x00b2, B:41:0x00b7, B:46:0x00c4, B:48:0x00d4, B:50:0x00e0, B:52:0x00e6, B:54:0x00ea, B:56:0x00f0, B:58:0x00f5, B:62:0x00fe, B:64:0x010c, B:66:0x0118, B:68:0x011e, B:70:0x0122, B:72:0x0128, B:74:0x012d, B:83:0x013f, B:86:0x0148, B:88:0x014e, B:89:0x0158, B:92:0x015f, B:94:0x0165, B:97:0x0176, B:99:0x017c, B:101:0x0180, B:104:0x0185, B:105:0x0191, B:107:0x0196, B:148:0x0217, B:150:0x021b, B:153:0x0220, B:155:0x022e, B:158:0x0235, B:161:0x023d, B:163:0x0244, B:164:0x024d, B:202:0x02d0, B:206:0x02d7, B:210:0x02de, B:212:0x02e4, B:213:0x02ed, B:215:0x02f1, B:218:0x02f6, B:219:0x0302, B:221:0x0306, B:223:0x030a, B:225:0x0310, B:226:0x0318, B:228:0x031e, B:230:0x0331, B:232:0x0335, B:233:0x0339, B:209:0x02dd, B:205:0x02d6, B:168:0x0259, B:170:0x025d, B:171:0x0266, B:173:0x026a, B:174:0x0274, B:177:0x027e, B:180:0x0285, B:182:0x028f, B:184:0x0293, B:185:0x029c, B:187:0x02a1, B:190:0x02a8, B:192:0x02b2, B:194:0x02b6, B:197:0x02bb, B:199:0x02bf, B:200:0x02ca, B:201:0x02cd, B:110:0x019f, B:141:0x0203, B:143:0x0209, B:145:0x020e, B:146:0x0211, B:144:0x020c, B:115:0x01ae, B:118:0x01b6, B:119:0x01b8, B:121:0x01bc, B:122:0x01c4, B:124:0x01c8, B:126:0x01cc, B:128:0x01d4, B:130:0x01d9, B:129:0x01d7, B:131:0x01dc, B:133:0x01e1, B:135:0x01e8, B:137:0x01f2, B:140:0x01fd), top: B:237:0x000a }] */
    /* JADX WARN: Code duplicated, block: B:128:0x01d4 A[Catch: AbortType -> 0x0348, TryCatch #0 {AbortType -> 0x0348, blocks: (B:6:0x000a, B:8:0x001a, B:10:0x0021, B:12:0x0030, B:13:0x0039, B:15:0x0040, B:17:0x004a, B:18:0x0053, B:20:0x0059, B:21:0x0067, B:23:0x0079, B:25:0x007f, B:27:0x0087, B:33:0x0096, B:35:0x00a6, B:37:0x00ac, B:39:0x00b2, B:41:0x00b7, B:46:0x00c4, B:48:0x00d4, B:50:0x00e0, B:52:0x00e6, B:54:0x00ea, B:56:0x00f0, B:58:0x00f5, B:62:0x00fe, B:64:0x010c, B:66:0x0118, B:68:0x011e, B:70:0x0122, B:72:0x0128, B:74:0x012d, B:83:0x013f, B:86:0x0148, B:88:0x014e, B:89:0x0158, B:92:0x015f, B:94:0x0165, B:97:0x0176, B:99:0x017c, B:101:0x0180, B:104:0x0185, B:105:0x0191, B:107:0x0196, B:148:0x0217, B:150:0x021b, B:153:0x0220, B:155:0x022e, B:158:0x0235, B:161:0x023d, B:163:0x0244, B:164:0x024d, B:202:0x02d0, B:206:0x02d7, B:210:0x02de, B:212:0x02e4, B:213:0x02ed, B:215:0x02f1, B:218:0x02f6, B:219:0x0302, B:221:0x0306, B:223:0x030a, B:225:0x0310, B:226:0x0318, B:228:0x031e, B:230:0x0331, B:232:0x0335, B:233:0x0339, B:209:0x02dd, B:205:0x02d6, B:168:0x0259, B:170:0x025d, B:171:0x0266, B:173:0x026a, B:174:0x0274, B:177:0x027e, B:180:0x0285, B:182:0x028f, B:184:0x0293, B:185:0x029c, B:187:0x02a1, B:190:0x02a8, B:192:0x02b2, B:194:0x02b6, B:197:0x02bb, B:199:0x02bf, B:200:0x02ca, B:201:0x02cd, B:110:0x019f, B:141:0x0203, B:143:0x0209, B:145:0x020e, B:146:0x0211, B:144:0x020c, B:115:0x01ae, B:118:0x01b6, B:119:0x01b8, B:121:0x01bc, B:122:0x01c4, B:124:0x01c8, B:126:0x01cc, B:128:0x01d4, B:130:0x01d9, B:129:0x01d7, B:131:0x01dc, B:133:0x01e1, B:135:0x01e8, B:137:0x01f2, B:140:0x01fd), top: B:237:0x000a }] */
    /* JADX WARN: Code duplicated, block: B:129:0x01d7 A[Catch: AbortType -> 0x0348, TryCatch #0 {AbortType -> 0x0348, blocks: (B:6:0x000a, B:8:0x001a, B:10:0x0021, B:12:0x0030, B:13:0x0039, B:15:0x0040, B:17:0x004a, B:18:0x0053, B:20:0x0059, B:21:0x0067, B:23:0x0079, B:25:0x007f, B:27:0x0087, B:33:0x0096, B:35:0x00a6, B:37:0x00ac, B:39:0x00b2, B:41:0x00b7, B:46:0x00c4, B:48:0x00d4, B:50:0x00e0, B:52:0x00e6, B:54:0x00ea, B:56:0x00f0, B:58:0x00f5, B:62:0x00fe, B:64:0x010c, B:66:0x0118, B:68:0x011e, B:70:0x0122, B:72:0x0128, B:74:0x012d, B:83:0x013f, B:86:0x0148, B:88:0x014e, B:89:0x0158, B:92:0x015f, B:94:0x0165, B:97:0x0176, B:99:0x017c, B:101:0x0180, B:104:0x0185, B:105:0x0191, B:107:0x0196, B:148:0x0217, B:150:0x021b, B:153:0x0220, B:155:0x022e, B:158:0x0235, B:161:0x023d, B:163:0x0244, B:164:0x024d, B:202:0x02d0, B:206:0x02d7, B:210:0x02de, B:212:0x02e4, B:213:0x02ed, B:215:0x02f1, B:218:0x02f6, B:219:0x0302, B:221:0x0306, B:223:0x030a, B:225:0x0310, B:226:0x0318, B:228:0x031e, B:230:0x0331, B:232:0x0335, B:233:0x0339, B:209:0x02dd, B:205:0x02d6, B:168:0x0259, B:170:0x025d, B:171:0x0266, B:173:0x026a, B:174:0x0274, B:177:0x027e, B:180:0x0285, B:182:0x028f, B:184:0x0293, B:185:0x029c, B:187:0x02a1, B:190:0x02a8, B:192:0x02b2, B:194:0x02b6, B:197:0x02bb, B:199:0x02bf, B:200:0x02ca, B:201:0x02cd, B:110:0x019f, B:141:0x0203, B:143:0x0209, B:145:0x020e, B:146:0x0211, B:144:0x020c, B:115:0x01ae, B:118:0x01b6, B:119:0x01b8, B:121:0x01bc, B:122:0x01c4, B:124:0x01c8, B:126:0x01cc, B:128:0x01d4, B:130:0x01d9, B:129:0x01d7, B:131:0x01dc, B:133:0x01e1, B:135:0x01e8, B:137:0x01f2, B:140:0x01fd), top: B:237:0x000a }] */
    /* JADX WARN: Code duplicated, block: B:132:0x01df  */
    /* JADX WARN: Code duplicated, block: B:143:0x0209 A[Catch: AbortType -> 0x0348, TryCatch #0 {AbortType -> 0x0348, blocks: (B:6:0x000a, B:8:0x001a, B:10:0x0021, B:12:0x0030, B:13:0x0039, B:15:0x0040, B:17:0x004a, B:18:0x0053, B:20:0x0059, B:21:0x0067, B:23:0x0079, B:25:0x007f, B:27:0x0087, B:33:0x0096, B:35:0x00a6, B:37:0x00ac, B:39:0x00b2, B:41:0x00b7, B:46:0x00c4, B:48:0x00d4, B:50:0x00e0, B:52:0x00e6, B:54:0x00ea, B:56:0x00f0, B:58:0x00f5, B:62:0x00fe, B:64:0x010c, B:66:0x0118, B:68:0x011e, B:70:0x0122, B:72:0x0128, B:74:0x012d, B:83:0x013f, B:86:0x0148, B:88:0x014e, B:89:0x0158, B:92:0x015f, B:94:0x0165, B:97:0x0176, B:99:0x017c, B:101:0x0180, B:104:0x0185, B:105:0x0191, B:107:0x0196, B:148:0x0217, B:150:0x021b, B:153:0x0220, B:155:0x022e, B:158:0x0235, B:161:0x023d, B:163:0x0244, B:164:0x024d, B:202:0x02d0, B:206:0x02d7, B:210:0x02de, B:212:0x02e4, B:213:0x02ed, B:215:0x02f1, B:218:0x02f6, B:219:0x0302, B:221:0x0306, B:223:0x030a, B:225:0x0310, B:226:0x0318, B:228:0x031e, B:230:0x0331, B:232:0x0335, B:233:0x0339, B:209:0x02dd, B:205:0x02d6, B:168:0x0259, B:170:0x025d, B:171:0x0266, B:173:0x026a, B:174:0x0274, B:177:0x027e, B:180:0x0285, B:182:0x028f, B:184:0x0293, B:185:0x029c, B:187:0x02a1, B:190:0x02a8, B:192:0x02b2, B:194:0x02b6, B:197:0x02bb, B:199:0x02bf, B:200:0x02ca, B:201:0x02cd, B:110:0x019f, B:141:0x0203, B:143:0x0209, B:145:0x020e, B:146:0x0211, B:144:0x020c, B:115:0x01ae, B:118:0x01b6, B:119:0x01b8, B:121:0x01bc, B:122:0x01c4, B:124:0x01c8, B:126:0x01cc, B:128:0x01d4, B:130:0x01d9, B:129:0x01d7, B:131:0x01dc, B:133:0x01e1, B:135:0x01e8, B:137:0x01f2, B:140:0x01fd), top: B:237:0x000a }] */
    /* JADX WARN: Code duplicated, block: B:144:0x020c A[Catch: AbortType -> 0x0348, TryCatch #0 {AbortType -> 0x0348, blocks: (B:6:0x000a, B:8:0x001a, B:10:0x0021, B:12:0x0030, B:13:0x0039, B:15:0x0040, B:17:0x004a, B:18:0x0053, B:20:0x0059, B:21:0x0067, B:23:0x0079, B:25:0x007f, B:27:0x0087, B:33:0x0096, B:35:0x00a6, B:37:0x00ac, B:39:0x00b2, B:41:0x00b7, B:46:0x00c4, B:48:0x00d4, B:50:0x00e0, B:52:0x00e6, B:54:0x00ea, B:56:0x00f0, B:58:0x00f5, B:62:0x00fe, B:64:0x010c, B:66:0x0118, B:68:0x011e, B:70:0x0122, B:72:0x0128, B:74:0x012d, B:83:0x013f, B:86:0x0148, B:88:0x014e, B:89:0x0158, B:92:0x015f, B:94:0x0165, B:97:0x0176, B:99:0x017c, B:101:0x0180, B:104:0x0185, B:105:0x0191, B:107:0x0196, B:148:0x0217, B:150:0x021b, B:153:0x0220, B:155:0x022e, B:158:0x0235, B:161:0x023d, B:163:0x0244, B:164:0x024d, B:202:0x02d0, B:206:0x02d7, B:210:0x02de, B:212:0x02e4, B:213:0x02ed, B:215:0x02f1, B:218:0x02f6, B:219:0x0302, B:221:0x0306, B:223:0x030a, B:225:0x0310, B:226:0x0318, B:228:0x031e, B:230:0x0331, B:232:0x0335, B:233:0x0339, B:209:0x02dd, B:205:0x02d6, B:168:0x0259, B:170:0x025d, B:171:0x0266, B:173:0x026a, B:174:0x0274, B:177:0x027e, B:180:0x0285, B:182:0x028f, B:184:0x0293, B:185:0x029c, B:187:0x02a1, B:190:0x02a8, B:192:0x02b2, B:194:0x02b6, B:197:0x02bb, B:199:0x02bf, B:200:0x02ca, B:201:0x02cd, B:110:0x019f, B:141:0x0203, B:143:0x0209, B:145:0x020e, B:146:0x0211, B:144:0x020c, B:115:0x01ae, B:118:0x01b6, B:119:0x01b8, B:121:0x01bc, B:122:0x01c4, B:124:0x01c8, B:126:0x01cc, B:128:0x01d4, B:130:0x01d9, B:129:0x01d7, B:131:0x01dc, B:133:0x01e1, B:135:0x01e8, B:137:0x01f2, B:140:0x01fd), top: B:237:0x000a }] */
    /* JADX WARN: Code duplicated, block: B:44:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:61:0x00fd  */
    /* JADX WARN: Code duplicated, block: B:77:0x0135  */
    public void resolve() throws Throwable {
        int i;
        boolean z;
        FieldBinding fieldBinding;
        MethodScope methodScope;
        MethodScope methodScope2;
        boolean z2;
        boolean z3;
        boolean z4;
        MethodBinding exactMethod;
        MethodBinding exactMethod2;
        SourceTypeBinding sourceTypeBinding = this.binding;
        if (sourceTypeBinding == null) {
            this.ignoreFurtherInvestigation = true;
            return;
        }
        try {
            long annotationTagBits = sourceTypeBinding.getAnnotationTagBits();
            if ((70368744177664L & annotationTagBits) == 0 && (sourceTypeBinding.modifiers & 1048576) != 0 && this.scope.compilerOptions().sourceLevel >= ClassFileConstants.JDK1_5) {
                this.scope.problemReporter().missingDeprecatedAnnotationForType(this);
            }
            if ((annotationTagBits & 576460752303423488L) != 0 && !this.binding.isFunctionalInterface(this.scope)) {
                this.scope.problemReporter().notAFunctionalInterface(this);
            }
            if ((this.bits & 8) != 0) {
                this.scope.problemReporter().undocumentedEmptyBlock(this.bodyStart - 1, this.bodyEnd);
            }
            boolean z5 = this.scope.compilerOptions().getSeverity(CompilerOptions.MissingSerialVersion) != 256 && sourceTypeBinding.isClass() && sourceTypeBinding.findSuperTypeOriginatingFrom(56, false) == null && sourceTypeBinding.findSuperTypeOriginatingFrom(37, false) != null;
            if (z5) {
                CompilationUnitScope compilationUnitScope = this.scope.compilationUnitScope();
                MethodBinding exactMethod3 = sourceTypeBinding.getExactMethod(TypeConstants.WRITEREPLACE, Binding.NO_TYPES, compilationUnitScope);
                if (exactMethod3 != null && exactMethod3.isValidBinding() && exactMethod3.returnType.f626id == 1) {
                    ReferenceBinding[] referenceBindingArr = exactMethod3.thrownExceptions;
                    if (referenceBindingArr.length == 1 && referenceBindingArr[0].f626id == 57) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                } else {
                    z2 = true;
                }
                if (z2) {
                    TypeBinding type = this.scope.getType(TypeConstants.JAVA_IO_OBJECTOUTPUTSTREAM, 3);
                    if (type.isValidBinding() && (exactMethod2 = sourceTypeBinding.getExactMethod(TypeConstants.WRITEOBJECT, new TypeBinding[]{type}, compilationUnitScope)) != null && exactMethod2.isValidBinding() && exactMethod2.modifiers == 2 && exactMethod2.returnType == TypeBinding.VOID) {
                        ReferenceBinding[] referenceBindingArr2 = exactMethod2.thrownExceptions;
                        if (referenceBindingArr2.length == 1 && referenceBindingArr2[0].f626id == 58) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                    } else {
                        z3 = false;
                    }
                    TypeBinding type2 = this.scope.getType(TypeConstants.JAVA_IO_OBJECTINPUTSTREAM, 3);
                    if (type2.isValidBinding() && (exactMethod = sourceTypeBinding.getExactMethod(TypeConstants.READOBJECT, new TypeBinding[]{type2}, compilationUnitScope)) != null && exactMethod.isValidBinding() && exactMethod.modifiers == 2 && exactMethod.returnType == TypeBinding.VOID) {
                        ReferenceBinding[] referenceBindingArr3 = exactMethod.thrownExceptions;
                        if (referenceBindingArr3.length == 1 && referenceBindingArr3[0].f626id == 58) {
                            z4 = true;
                        } else {
                            z4 = false;
                        }
                    } else {
                        z4 = false;
                    }
                    z5 = (z3 && z4) ? false : true;
                } else {
                    z5 = z2;
                }
            }
            if (sourceTypeBinding.findSuperTypeOriginatingFrom(21, true) != null) {
                ReferenceBinding referenceBindingEnclosingType = sourceTypeBinding;
                do {
                    if (referenceBindingEnclosingType.isGenericType()) {
                        this.scope.problemReporter().genericTypeCannotExtendThrowable(this);
                        break;
                    } else {
                        if (referenceBindingEnclosingType.isStatic() || (referenceBindingEnclosingType.isLocalType() && ((NestedTypeBinding) referenceBindingEnclosingType.erasure()).scope.methodScope().isStatic)) {
                            break;
                            break;
                        }
                        referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType();
                    }
                } while (referenceBindingEnclosingType != null);
            }
            TypeDeclaration[] typeDeclarationArr = this.memberTypes;
            if (typeDeclarationArr != null) {
                int length = typeDeclarationArr.length;
                for (int i2 = 0; i2 < length; i2++) {
                    this.memberTypes[i2].resolve(this.scope);
                }
            }
            FieldDeclaration[] fieldDeclarationArr = this.fields;
            FieldDeclaration[] fieldDeclarationArr2 = null;
            if (fieldDeclarationArr != null) {
                int length2 = fieldDeclarationArr.length;
                int i3 = -1;
                i = 0;
                z = false;
                for (int i4 = 0; i4 < length2; i4++) {
                    FieldDeclaration fieldDeclaration = this.fields[i4];
                    int kind = fieldDeclaration.getKind();
                    if (kind == 1) {
                        fieldBinding = fieldDeclaration.binding;
                        if (fieldBinding == null) {
                            if (fieldDeclaration.initialization != null) {
                                Expression expression = fieldDeclaration.initialization;
                                if (fieldDeclaration.isStatic()) {
                                    methodScope = this.staticInitializerScope;
                                } else {
                                    methodScope = this.initializerScope;
                                }
                                expression.resolve(methodScope);
                            }
                            this.ignoreFurtherInvestigation = true;
                        } else {
                            if (z5 && (fieldBinding.modifiers & 24) == 24 && CharOperation.equals(TypeConstants.SERIALVERSIONUID, fieldBinding.f628name) && TypeBinding.equalsEquals(TypeBinding.LONG, fieldBinding.type)) {
                                z5 = false;
                            }
                            i++;
                            i3 = fieldDeclaration.binding.f627id;
                            if (fieldDeclaration.isStatic()) {
                                methodScope2 = this.staticInitializerScope;
                            } else {
                                methodScope2 = this.initializerScope;
                            }
                            fieldDeclaration.resolve(methodScope2);
                        }
                    } else {
                        if (kind == 2) {
                            ((Initializer) fieldDeclaration).lastVisibleFieldID = i3 + 1;
                        } else if (kind == 3) {
                            if (!(fieldDeclaration.initialization instanceof QualifiedAllocationExpression)) {
                                if (fieldDeclarationArr2 == null) {
                                    fieldDeclarationArr2 = new FieldDeclaration[length2];
                                }
                                fieldDeclarationArr2[i4] = fieldDeclaration;
                            }
                            z = true;
                            fieldBinding = fieldDeclaration.binding;
                            if (fieldBinding == null) {
                                if (fieldDeclaration.initialization != null) {
                                    Expression expression2 = fieldDeclaration.initialization;
                                    if (fieldDeclaration.isStatic()) {
                                        methodScope = this.staticInitializerScope;
                                    } else {
                                        methodScope = this.initializerScope;
                                    }
                                    expression2.resolve(methodScope);
                                }
                                this.ignoreFurtherInvestigation = true;
                            } else {
                                if (z5) {
                                    z5 = false;
                                }
                                i++;
                                i3 = fieldDeclaration.binding.f627id;
                            }
                        }
                        if (fieldDeclaration.isStatic()) {
                            methodScope2 = this.staticInitializerScope;
                        } else {
                            methodScope2 = this.initializerScope;
                        }
                        fieldDeclaration.resolve(methodScope2);
                    }
                }
            } else {
                i = 0;
                z = false;
            }
            if (this.maxFieldCount < i) {
                this.maxFieldCount = i;
            }
            if (z5) {
                TypeBinding type3 = this.scope.getType(TypeConstants.JAVAX_RMI_CORBA_STUB, 4);
                if (type3.isValidBinding()) {
                    for (ReferenceBinding referenceBindingSuperclass = this.binding.superclass; referenceBindingSuperclass != null; referenceBindingSuperclass = referenceBindingSuperclass.superclass()) {
                        if (TypeBinding.equalsEquals(referenceBindingSuperclass, type3)) {
                            z5 = false;
                            break;
                        }
                    }
                }
                if (z5) {
                    this.scope.problemReporter().missingSerialVersion(this);
                }
            }
            int iKind = kind(this.modifiers);
            if (iKind != 3) {
                if (iKind == 4) {
                    if (this.superclass != null) {
                        this.scope.problemReporter().annotationTypeDeclarationCannotHaveSuperclass(this);
                    }
                    if (this.superInterfaces != null) {
                        this.scope.problemReporter().annotationTypeDeclarationCannotHaveSuperinterfaces(this);
                    }
                }
            } else if (this.binding.isAbstract()) {
                if (!z) {
                    int length3 = this.methods.length;
                    for (int i5 = 0; i5 < length3; i5++) {
                        AbstractMethodDeclaration abstractMethodDeclaration = this.methods[i5];
                        if (abstractMethodDeclaration.isAbstract() && abstractMethodDeclaration.binding != null) {
                            this.scope.problemReporter().enumAbstractMethodMustBeImplemented(abstractMethodDeclaration);
                        }
                    }
                } else if (fieldDeclarationArr2 != null) {
                    int length4 = this.methods.length;
                    for (int i6 = 0; i6 < length4; i6++) {
                        AbstractMethodDeclaration abstractMethodDeclaration2 = this.methods[i6];
                        if (abstractMethodDeclaration2.isAbstract() && abstractMethodDeclaration2.binding != null) {
                            int length5 = fieldDeclarationArr2.length;
                            for (int i7 = 0; i7 < length5; i7++) {
                                if (fieldDeclarationArr2[i7] != null) {
                                    this.scope.problemReporter().enumConstantMustImplementAbstractMethod(abstractMethodDeclaration2, fieldDeclarationArr2[i7]);
                                }
                            }
                        }
                    }
                }
            }
            MethodDeclaration[] methodDeclarationArr = this.missingAbstractMethods;
            int length6 = methodDeclarationArr == null ? 0 : methodDeclarationArr.length;
            AbstractMethodDeclaration[] abstractMethodDeclarationArr = this.methods;
            if ((abstractMethodDeclarationArr == null ? 0 : abstractMethodDeclarationArr.length) + length6 > 65535) {
                this.scope.problemReporter().tooManyMethods(this);
            }
            AbstractMethodDeclaration[] abstractMethodDeclarationArr2 = this.methods;
            if (abstractMethodDeclarationArr2 != null) {
                int length7 = abstractMethodDeclarationArr2.length;
                for (int i8 = 0; i8 < length7; i8++) {
                    this.methods[i8].resolve(this.scope);
                }
            }
            if (this.javadoc != null) {
                if (this.scope == null || this.f619name == TypeConstants.PACKAGE_INFO_NAME) {
                    return;
                }
                this.javadoc.resolve(this.scope);
                return;
            }
            if (sourceTypeBinding.isLocalType()) {
                return;
            }
            int iComputeOuterMostVisibility = sourceTypeBinding.modifiers & 7;
            ProblemReporter problemReporter = this.scope.problemReporter();
            int iComputeSeverity = problemReporter.computeSeverity(IProblem.JavadocMissing);
            if (iComputeSeverity != 256) {
                TypeDeclaration typeDeclaration = this.enclosingType;
                if (typeDeclaration != null) {
                    iComputeOuterMostVisibility = Util.computeOuterMostVisibility(typeDeclaration, iComputeOuterMostVisibility);
                }
                problemReporter.javadocMissing(this.sourceStart, this.sourceEnd, iComputeSeverity, iComputeOuterMostVisibility | (this.binding.modifiers & (-8)));
            }
        } catch (AbortType unused) {
            this.ignoreFurtherInvestigation = true;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) throws Throwable {
        if ((this.bits & 512) == 0) {
            TypeBinding type = blockScope.getType(this.f619name);
            if ((type instanceof ReferenceBinding) && type != this.binding && type.isValidBinding()) {
                ReferenceBinding referenceBinding = (ReferenceBinding) type;
                if (referenceBinding instanceof TypeVariableBinding) {
                    TypeVariableBinding typeVariableBinding = (TypeVariableBinding) referenceBinding;
                    blockScope.problemReporter().typeHiding(this, typeVariableBinding);
                    for (Scope scope = blockScope.parent; scope != null; scope = scope.parent) {
                        TypeBinding type2 = scope.getType(this.f619name);
                        if ((type2 instanceof TypeVariableBinding) && type2.isValidBinding()) {
                            Binding binding = typeVariableBinding.declaringElement;
                            if ((binding instanceof ReferenceBinding) && CharOperation.equals(((ReferenceBinding) binding).sourceName(), this.f619name)) {
                                blockScope.problemReporter().typeCollidesWithEnclosingType(this);
                                break;
                            }
                        } else if ((type2 instanceof ReferenceBinding) && type2.isValidBinding() && scope.isDefinedInType((ReferenceBinding) type2)) {
                            blockScope.problemReporter().typeCollidesWithEnclosingType(this);
                            break;
                        } else {
                            if (type2 == null) {
                                break;
                            }
                        }
                    }
                } else {
                    boolean z = referenceBinding instanceof LocalTypeBinding;
                    if (z && ((LocalTypeBinding) referenceBinding).scope.methodScope() == blockScope.methodScope()) {
                        blockScope.problemReporter().duplicateNestedType(this);
                    } else if (z && blockScope.isLambdaSubscope() && blockScope.enclosingLambdaScope().enclosingMethodScope() == ((LocalTypeBinding) referenceBinding).scope.methodScope()) {
                        blockScope.problemReporter().duplicateNestedType(this);
                    } else if (blockScope.isDefinedInType(referenceBinding)) {
                        blockScope.problemReporter().typeCollidesWithEnclosingType(this);
                    } else if (blockScope.isDefinedInSameUnit(referenceBinding)) {
                        blockScope.problemReporter().typeHiding(this, referenceBinding);
                    }
                }
            }
            blockScope.addLocalType(this);
        }
        if (this.binding != null) {
            blockScope.referenceCompilationUnit().record((LocalTypeBinding) this.binding);
            resolve();
            updateMaxFieldCount();
        }
    }

    public void resolve(ClassScope classScope) throws Throwable {
        SourceTypeBinding sourceTypeBinding = this.binding;
        if (sourceTypeBinding != null && (sourceTypeBinding instanceof LocalTypeBinding)) {
            classScope.referenceCompilationUnit().record((LocalTypeBinding) this.binding);
        }
        resolve();
        updateMaxFieldCount();
    }

    public void resolve(CompilationUnitScope compilationUnitScope) {
        resolve();
        updateMaxFieldCount();
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public void tagAsHavingErrors() {
        this.ignoreFurtherInvestigation = true;
    }

    public void traverse(ASTVisitor aSTVisitor, CompilationUnitScope compilationUnitScope) {
        try {
            if (aSTVisitor.visit(this, compilationUnitScope)) {
                Javadoc javadoc = this.javadoc;
                if (javadoc != null) {
                    javadoc.traverse(aSTVisitor, this.scope);
                }
                Annotation[] annotationArr = this.annotations;
                if (annotationArr != null) {
                    int length = annotationArr.length;
                    for (int i = 0; i < length; i++) {
                        this.annotations[i].traverse(aSTVisitor, this.staticInitializerScope);
                    }
                }
                TypeReference typeReference = this.superclass;
                if (typeReference != null) {
                    typeReference.traverse(aSTVisitor, this.scope);
                }
                TypeReference[] typeReferenceArr = this.superInterfaces;
                if (typeReferenceArr != null) {
                    int length2 = typeReferenceArr.length;
                    for (int i2 = 0; i2 < length2; i2++) {
                        this.superInterfaces[i2].traverse(aSTVisitor, this.scope);
                    }
                }
                TypeParameter[] typeParameterArr = this.typeParameters;
                if (typeParameterArr != null) {
                    int length3 = typeParameterArr.length;
                    for (int i3 = 0; i3 < length3; i3++) {
                        this.typeParameters[i3].traverse(aSTVisitor, this.scope);
                    }
                }
                TypeDeclaration[] typeDeclarationArr = this.memberTypes;
                if (typeDeclarationArr != null) {
                    int length4 = typeDeclarationArr.length;
                    for (int i4 = 0; i4 < length4; i4++) {
                        this.memberTypes[i4].traverse(aSTVisitor, this.scope);
                    }
                }
                FieldDeclaration[] fieldDeclarationArr = this.fields;
                if (fieldDeclarationArr != null) {
                    int length5 = fieldDeclarationArr.length;
                    for (int i5 = 0; i5 < length5; i5++) {
                        FieldDeclaration fieldDeclaration = this.fields[i5];
                        if (fieldDeclaration.isStatic()) {
                            fieldDeclaration.traverse(aSTVisitor, this.staticInitializerScope);
                        } else {
                            fieldDeclaration.traverse(aSTVisitor, this.initializerScope);
                        }
                    }
                }
                AbstractMethodDeclaration[] abstractMethodDeclarationArr = this.methods;
                if (abstractMethodDeclarationArr != null) {
                    int length6 = abstractMethodDeclarationArr.length;
                    for (int i6 = 0; i6 < length6; i6++) {
                        this.methods[i6].traverse(aSTVisitor, this.scope);
                    }
                }
            }
            aSTVisitor.endVisit(this, compilationUnitScope);
        } catch (AbortType unused) {
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        try {
            if (aSTVisitor.visit(this, blockScope)) {
                Javadoc javadoc = this.javadoc;
                if (javadoc != null) {
                    javadoc.traverse(aSTVisitor, this.scope);
                }
                Annotation[] annotationArr = this.annotations;
                if (annotationArr != null) {
                    int length = annotationArr.length;
                    for (int i = 0; i < length; i++) {
                        this.annotations[i].traverse(aSTVisitor, this.staticInitializerScope);
                    }
                }
                TypeReference typeReference = this.superclass;
                if (typeReference != null) {
                    typeReference.traverse(aSTVisitor, this.scope);
                }
                TypeReference[] typeReferenceArr = this.superInterfaces;
                if (typeReferenceArr != null) {
                    int length2 = typeReferenceArr.length;
                    for (int i2 = 0; i2 < length2; i2++) {
                        this.superInterfaces[i2].traverse(aSTVisitor, this.scope);
                    }
                }
                TypeParameter[] typeParameterArr = this.typeParameters;
                if (typeParameterArr != null) {
                    int length3 = typeParameterArr.length;
                    for (int i3 = 0; i3 < length3; i3++) {
                        this.typeParameters[i3].traverse(aSTVisitor, this.scope);
                    }
                }
                TypeDeclaration[] typeDeclarationArr = this.memberTypes;
                if (typeDeclarationArr != null) {
                    int length4 = typeDeclarationArr.length;
                    for (int i4 = 0; i4 < length4; i4++) {
                        this.memberTypes[i4].traverse(aSTVisitor, this.scope);
                    }
                }
                FieldDeclaration[] fieldDeclarationArr = this.fields;
                if (fieldDeclarationArr != null) {
                    int length5 = fieldDeclarationArr.length;
                    for (int i5 = 0; i5 < length5; i5++) {
                        FieldDeclaration fieldDeclaration = this.fields[i5];
                        if (!fieldDeclaration.isStatic() || fieldDeclaration.isFinal()) {
                            fieldDeclaration.traverse(aSTVisitor, this.initializerScope);
                        }
                    }
                }
                AbstractMethodDeclaration[] abstractMethodDeclarationArr = this.methods;
                if (abstractMethodDeclarationArr != null) {
                    int length6 = abstractMethodDeclarationArr.length;
                    for (int i6 = 0; i6 < length6; i6++) {
                        this.methods[i6].traverse(aSTVisitor, this.scope);
                    }
                }
            }
            aSTVisitor.endVisit(this, blockScope);
        } catch (AbortType unused) {
        }
    }

    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        try {
            if (aSTVisitor.visit(this, classScope)) {
                Javadoc javadoc = this.javadoc;
                if (javadoc != null) {
                    javadoc.traverse(aSTVisitor, this.scope);
                }
                Annotation[] annotationArr = this.annotations;
                if (annotationArr != null) {
                    int length = annotationArr.length;
                    for (int i = 0; i < length; i++) {
                        this.annotations[i].traverse(aSTVisitor, this.staticInitializerScope);
                    }
                }
                TypeReference typeReference = this.superclass;
                if (typeReference != null) {
                    typeReference.traverse(aSTVisitor, this.scope);
                }
                TypeReference[] typeReferenceArr = this.superInterfaces;
                if (typeReferenceArr != null) {
                    int length2 = typeReferenceArr.length;
                    for (int i2 = 0; i2 < length2; i2++) {
                        this.superInterfaces[i2].traverse(aSTVisitor, this.scope);
                    }
                }
                TypeParameter[] typeParameterArr = this.typeParameters;
                if (typeParameterArr != null) {
                    int length3 = typeParameterArr.length;
                    for (int i3 = 0; i3 < length3; i3++) {
                        this.typeParameters[i3].traverse(aSTVisitor, this.scope);
                    }
                }
                TypeDeclaration[] typeDeclarationArr = this.memberTypes;
                if (typeDeclarationArr != null) {
                    int length4 = typeDeclarationArr.length;
                    for (int i4 = 0; i4 < length4; i4++) {
                        this.memberTypes[i4].traverse(aSTVisitor, this.scope);
                    }
                }
                FieldDeclaration[] fieldDeclarationArr = this.fields;
                if (fieldDeclarationArr != null) {
                    int length5 = fieldDeclarationArr.length;
                    for (int i5 = 0; i5 < length5; i5++) {
                        FieldDeclaration fieldDeclaration = this.fields[i5];
                        if (fieldDeclaration.isStatic()) {
                            fieldDeclaration.traverse(aSTVisitor, this.staticInitializerScope);
                        } else {
                            fieldDeclaration.traverse(aSTVisitor, this.initializerScope);
                        }
                    }
                }
                AbstractMethodDeclaration[] abstractMethodDeclarationArr = this.methods;
                if (abstractMethodDeclarationArr != null) {
                    int length6 = abstractMethodDeclarationArr.length;
                    for (int i6 = 0; i6 < length6; i6++) {
                        this.methods[i6].traverse(aSTVisitor, this.scope);
                    }
                }
            }
            aSTVisitor.endVisit(this, classScope);
        } catch (AbortType unused) {
        }
    }

    void updateMaxFieldCount() {
        if (this.binding == null) {
            return;
        }
        TypeDeclaration typeDeclarationReferenceType = this.scope.outerMostClassScope().referenceType();
        int i = this.maxFieldCount;
        int i2 = typeDeclarationReferenceType.maxFieldCount;
        if (i > i2) {
            typeDeclarationReferenceType.maxFieldCount = i;
        } else {
            this.maxFieldCount = i2;
        }
    }

    public boolean isPackageInfo() {
        return CharOperation.equals(this.f619name, TypeConstants.PACKAGE_INFO_NAME);
    }

    public boolean isSecondary() {
        return (this.bits & 4096) != 0;
    }
}
