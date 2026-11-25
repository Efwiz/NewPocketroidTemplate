package org.eclipse.jdt.internal.compiler.ast;

import java.util.Arrays;
import java.util.Comparator;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.impl.IrritantSet;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.lookup.CompilationUnitScope;
import org.eclipse.jdt.internal.compiler.lookup.LocalTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.parser.NLSTag;
import org.eclipse.jdt.internal.compiler.problem.AbortCompilationUnit;
import org.eclipse.jdt.internal.compiler.problem.AbortMethod;
import org.eclipse.jdt.internal.compiler.problem.AbortType;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.problem.ProblemSeverities;
import org.eclipse.jdt.internal.compiler.util.HashSetOfInt;

/* JADX INFO: loaded from: classes14.dex */
public class CompilationUnitDeclaration extends ASTNode implements ProblemSeverities, ReferenceContext {
    private static final int STRING_LITERALS_INCREMENT = 10;
    private static final Comparator STRING_LITERAL_COMPARATOR = new Comparator() { // from class: org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration.1
        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return ((StringLiteral) obj).sourceStart - ((StringLiteral) obj2).sourceStart;
        }
    };
    public int[][] comments;
    public CompilationResult compilationResult;
    public ImportReference currentPackage;
    public FunctionalExpression[] functionalExpressions;
    public int functionalExpressionsCount;
    public ImportReference[] imports;
    public boolean isPropagatingInnerClassEmulation;
    public Javadoc javadoc;
    public LocalTypeBinding[] localTypes;
    public NLSTag[] nlsTags;
    public ProblemReporter problemReporter;
    public CompilationUnitScope scope;
    private StringLiteral[] stringLiterals;
    private int stringLiteralsPtr;
    private HashSetOfInt stringLiteralsStart;
    Annotation[] suppressWarningAnnotations;
    IrritantSet[] suppressWarningIrritants;
    long[] suppressWarningScopePositions;
    int suppressWarningsCount;
    public TypeDeclaration[] types;
    public boolean[] validIdentityComparisonLines;
    public boolean ignoreFurtherInvestigation = false;
    public boolean ignoreMethodBodies = false;
    public int localTypeCount = 0;

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public CompilationUnitDeclaration getCompilationUnitDeclaration() {
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public void tagAsHavingIgnoredMandatoryErrors(int i) {
    }

    public CompilationUnitDeclaration(ProblemReporter problemReporter, CompilationResult compilationResult, int i) {
        this.problemReporter = problemReporter;
        this.compilationResult = compilationResult;
        this.sourceStart = 0;
        this.sourceEnd = i - 1;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public void abort(int i, CategorizedProblem categorizedProblem) {
        if (i == 8) {
            throw new AbortType(this.compilationResult, categorizedProblem);
        }
        if (i == 16) {
            throw new AbortMethod(this.compilationResult, categorizedProblem);
        }
        throw new AbortCompilationUnit(this.compilationResult, categorizedProblem);
    }

    public void analyseCode() {
        if (this.ignoreFurtherInvestigation) {
            return;
        }
        try {
            TypeDeclaration[] typeDeclarationArr = this.types;
            if (typeDeclarationArr != null) {
                int length = typeDeclarationArr.length;
                for (int i = 0; i < length; i++) {
                    this.types[i].analyseCode(this.scope);
                }
            }
            propagateInnerEmulationForAllLocalTypes();
        } catch (AbortCompilationUnit unused) {
            this.ignoreFurtherInvestigation = true;
        }
    }

    public void cleanUp() {
        TypeDeclaration[] typeDeclarationArr = this.types;
        if (typeDeclarationArr != null) {
            int length = typeDeclarationArr.length;
            for (int i = 0; i < length; i++) {
                cleanUp(this.types[i]);
            }
            int i2 = this.localTypeCount;
            for (int i3 = 0; i3 < i2; i3++) {
                LocalTypeBinding localTypeBinding = this.localTypes[i3];
                localTypeBinding.scope = null;
                localTypeBinding.enclosingCase = null;
            }
        }
        this.compilationResult.recoveryScannerData = null;
        for (ClassFile classFile : this.compilationResult.getClassFiles()) {
            classFile.referenceBinding = null;
            classFile.innerClassesBindings = null;
            classFile.bootstrapMethods = null;
            classFile.missingTypes = null;
            classFile.visitedTypes = null;
        }
        this.suppressWarningAnnotations = null;
        CompilationUnitScope compilationUnitScope = this.scope;
        if (compilationUnitScope != null) {
            compilationUnitScope.cleanUpInferenceContexts();
        }
    }

    private void cleanUp(TypeDeclaration typeDeclaration) {
        if (typeDeclaration.memberTypes != null) {
            int length = typeDeclaration.memberTypes.length;
            for (int i = 0; i < length; i++) {
                cleanUp(typeDeclaration.memberTypes[i]);
            }
        }
        if (typeDeclaration.binding != null && typeDeclaration.binding.isAnnotationType()) {
            this.compilationResult.hasAnnotations = true;
        }
        if (typeDeclaration.binding != null) {
            typeDeclaration.binding.scope = null;
        }
    }

    public void checkUnusedImports() {
        if (this.scope.imports != null) {
            int length = this.scope.imports.length;
            for (int i = 0; i < length; i++) {
                ImportReference importReference = this.scope.imports[i].reference;
                if (importReference != null && (importReference.bits & 2) == 0) {
                    this.scope.problemReporter().unusedImport(importReference);
                }
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public CompilationResult compilationResult() {
        return this.compilationResult;
    }

    public void createPackageInfoType() {
        TypeDeclaration typeDeclaration = new TypeDeclaration(this.compilationResult);
        typeDeclaration.f619name = TypeConstants.PACKAGE_INFO_NAME;
        typeDeclaration.modifiers = 512;
        typeDeclaration.javadoc = this.javadoc;
        this.types[0] = typeDeclaration;
    }

    public TypeDeclaration declarationOfType(char[][] cArr) {
        int i = 0;
        while (true) {
            TypeDeclaration[] typeDeclarationArr = this.types;
            if (i >= typeDeclarationArr.length) {
                return null;
            }
            TypeDeclaration typeDeclarationDeclarationOfType = typeDeclarationArr[i].declarationOfType(cArr);
            if (typeDeclarationDeclarationOfType != null) {
                return typeDeclarationDeclarationOfType;
            }
            i++;
        }
    }

    /* JADX WARN: Code duplicated, block: B:118:0x01b3  */
    /* JADX WARN: Code duplicated, block: B:121:0x01c6  */
    /* JADX WARN: Code duplicated, block: B:131:0x01ff  */
    /* JADX WARN: Code duplicated, block: B:132:0x0207  */
    /* JADX WARN: Code duplicated, block: B:139:0x01c0 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:30:0x0057 A[PHI: r1 r2 r10
      0x0057: PHI (r1v28 int) = (r1v7 int), (r1v7 int), (r1v7 int), (r1v7 int), (r1v15 int), (r1v7 int) binds: [B:29:0x0055, B:37:0x006d, B:159:0x0057, B:46:0x0091, B:161:0x0057, B:34:0x0068] A[DONT_GENERATE, DONT_INLINE]
      0x0057: PHI (r2v20 int) = (r2v5 int), (r2v5 int), (r2v5 int), (r2v5 int), (r2v12 int), (r2v5 int) binds: [B:29:0x0055, B:37:0x006d, B:159:0x0057, B:46:0x0091, B:161:0x0057, B:34:0x0068] A[DONT_GENERATE, DONT_INLINE]
      0x0057: PHI (r10v12 boolean) = (r10v2 boolean), (r10v2 boolean), (r10v2 boolean), (r10v2 boolean), (r10v5 boolean), (r10v2 boolean) binds: [B:29:0x0055, B:37:0x006d, B:159:0x0057, B:46:0x0091, B:161:0x0057, B:34:0x0068] A[DONT_GENERATE, DONT_INLINE]] */
    public void finalizeProblems() {
        int severity;
        int i;
        int i2;
        boolean z;
        IrritantSet irritantSetWarningTokenToIrritants;
        IrritantSet irritantSet;
        int i3;
        int i4;
        boolean z2;
        int i5;
        IrritantSet irritantSetWarningTokenToIrritants2;
        IrritantSet irritantSet2;
        int sourceStart;
        int i6;
        int i7;
        int i8;
        IrritantSet[] irritantSetArr;
        CompilerOptions compilerOptions;
        CategorizedProblem categorizedProblem;
        int i9;
        IrritantSet irritantSet3;
        if (this.suppressWarningsCount == 0) {
            return;
        }
        CategorizedProblem[] categorizedProblemArr = this.compilationResult.problems;
        int i10 = this.compilationResult.problemCount;
        IrritantSet[] irritantSetArr2 = new IrritantSet[this.suppressWarningsCount];
        CompilerOptions compilerOptions2 = this.scope.compilerOptions();
        int i11 = 0;
        int i12 = 0;
        boolean z3 = false;
        while (true) {
            if (i11 >= i10) {
                break;
            }
            CategorizedProblem categorizedProblem2 = categorizedProblemArr[i11];
            int irritant = ProblemReporter.getIrritant(categorizedProblem2.getID());
            if (!categorizedProblem2.isError()) {
                sourceStart = categorizedProblem2.getSourceStart();
                int sourceEnd = categorizedProblem2.getSourceEnd();
                i6 = this.suppressWarningsCount;
                i7 = 0;
                while (true) {
                    if (i7 >= i6) {
                        i8 = i10;
                        irritantSetArr = irritantSetArr2;
                        compilerOptions = compilerOptions2;
                        break;
                    }
                    i8 = i10;
                    irritantSetArr = irritantSetArr2;
                    long j = this.suppressWarningScopePositions[i7];
                    compilerOptions = compilerOptions2;
                    categorizedProblem = categorizedProblem2;
                    i9 = (int) (j >>> 32);
                    int i13 = (int) j;
                    if (sourceStart >= i9 && sourceEnd <= i13 && this.suppressWarningIrritants[i7].isSet(irritant)) {
                        i12++;
                        categorizedProblemArr[i11] = null;
                        this.compilationResult.removeProblem(categorizedProblem);
                        irritantSet3 = irritantSetArr[i7];
                        if (irritantSet3 == null) {
                            irritantSetArr[i7] = new IrritantSet(irritant);
                            break;
                        } else {
                            irritantSet3.set(irritant);
                            break;
                        }
                    }
                    i7++;
                    compilerOptions2 = compilerOptions;
                    irritantSetArr2 = irritantSetArr;
                    i10 = i8;
                    categorizedProblem2 = categorizedProblem;
                }
            } else if (irritant == 0) {
                i8 = i10;
                irritantSetArr = irritantSetArr2;
                compilerOptions = compilerOptions2;
                z3 = true;
            } else {
                if (!compilerOptions2.suppressOptionalErrors) {
                    i8 = i10;
                    irritantSetArr = irritantSetArr2;
                    compilerOptions = compilerOptions2;
                    break;
                    break;
                }
                sourceStart = categorizedProblem2.getSourceStart();
                int sourceEnd2 = categorizedProblem2.getSourceEnd();
                i6 = this.suppressWarningsCount;
                i7 = 0;
                while (true) {
                    if (i7 >= i6) {
                        i8 = i10;
                        irritantSetArr = irritantSetArr2;
                        compilerOptions = compilerOptions2;
                        break;
                        break;
                    }
                    i8 = i10;
                    irritantSetArr = irritantSetArr2;
                    long j2 = this.suppressWarningScopePositions[i7];
                    compilerOptions = compilerOptions2;
                    categorizedProblem = categorizedProblem2;
                    i9 = (int) (j2 >>> 32);
                    int i14 = (int) j2;
                    if (sourceStart >= i9) {
                        i12++;
                        categorizedProblemArr[i11] = null;
                        this.compilationResult.removeProblem(categorizedProblem);
                        irritantSet3 = irritantSetArr[i7];
                        if (irritantSet3 == null) {
                            irritantSetArr[i7] = new IrritantSet(irritant);
                            break;
                            break;
                        } else {
                            irritantSet3.set(irritant);
                            break;
                            break;
                        }
                    }
                    i7++;
                    compilerOptions2 = compilerOptions;
                    irritantSetArr2 = irritantSetArr;
                    i10 = i8;
                    categorizedProblem2 = categorizedProblem;
                }
            }
            i11++;
            compilerOptions2 = compilerOptions;
            irritantSetArr2 = irritantSetArr;
            i10 = i8;
        }
        if (i12 > 0) {
            int i15 = 0;
            for (int i16 = 0; i16 < i10; i16++) {
                CategorizedProblem categorizedProblem3 = categorizedProblemArr[i16];
                if (categorizedProblem3 != null) {
                    if (i16 > i15) {
                        categorizedProblemArr[i15] = categorizedProblem3;
                        i15++;
                    } else {
                        i15++;
                    }
                }
            }
        }
        if (z3 || (severity = compilerOptions2.getSeverity(CompilerOptions.UnusedWarningToken)) == 256) {
            return;
        }
        boolean z4 = (severity & 1) == 0;
        int i17 = this.suppressWarningsCount;
        int i18 = 0;
        while (i18 < i17) {
            Annotation annotation = this.suppressWarningAnnotations[i18];
            if (annotation == null) {
                i = i17;
                i2 = i18;
                z = z4;
                break;
            }
            IrritantSet irritantSet4 = this.suppressWarningIrritants[i18];
            if ((!z4 || !irritantSet4.areAllSet()) && irritantSet4 != irritantSetArr2[i18]) {
                MemberValuePair[] memberValuePairArrMemberValuePairs = annotation.memberValuePairs();
                int length = memberValuePairArrMemberValuePairs.length;
                int i19 = 0;
                while (true) {
                    if (i19 < length) {
                        MemberValuePair memberValuePair = memberValuePairArrMemberValuePairs[i19];
                        if (CharOperation.equals(memberValuePair.f618name, TypeConstants.VALUE)) {
                            Expression expression = memberValuePair.value;
                            if (expression instanceof ArrayInitializer) {
                                Expression[] expressionArr = ((ArrayInitializer) expression).expressions;
                                if (expressionArr != null) {
                                    int length2 = expressionArr.length;
                                    int i20 = 0;
                                    while (true) {
                                        if (i20 < length2) {
                                            Constant constant = expressionArr[i20].constant;
                                            if (constant == Constant.NotAConstant || constant.typeID() != 11 || (irritantSetWarningTokenToIrritants2 = CompilerOptions.warningTokenToIrritants(constant.stringValue())) == null || irritantSetWarningTokenToIrritants2.areAllSet() || !compilerOptions2.isAnyEnabled(irritantSetWarningTokenToIrritants2) || ((irritantSet2 = irritantSetArr2[i18]) != null && irritantSet2.isAnySet(irritantSetWarningTokenToIrritants2))) {
                                                i3 = i17;
                                                i4 = i18;
                                                z2 = z4;
                                                i5 = length2;
                                            } else {
                                                if (z4) {
                                                    int i21 = expression.sourceStart;
                                                    int i22 = expression.sourceEnd;
                                                    int i23 = i18 - 1;
                                                    while (true) {
                                                        if (i23 >= 0) {
                                                            z = z4;
                                                            int i24 = length2;
                                                            long j3 = this.suppressWarningScopePositions[i23];
                                                            i = i17;
                                                            i2 = i18;
                                                            int i25 = (int) j3;
                                                            if (i21 >= ((int) (j3 >>> 32)) && i22 <= i25 && this.suppressWarningIrritants[i23].areAllSet()) {
                                                                break;
                                                            }
                                                            i23--;
                                                            i17 = i;
                                                            z4 = z;
                                                            length2 = i24;
                                                            i18 = i2;
                                                        }
                                                    }
                                                }
                                                i3 = i17;
                                                i4 = i18;
                                                z2 = z4;
                                                i5 = length2;
                                                this.scope.problemReporter().unusedWarningToken(expressionArr[i20]);
                                            }
                                            i20++;
                                            i17 = i3;
                                            z4 = z2;
                                            length2 = i5;
                                            i18 = i4;
                                        }
                                    }
                                }
                            } else {
                                i = i17;
                                i2 = i18;
                                z = z4;
                                Constant constant2 = expression.constant;
                                if (constant2 == Constant.NotAConstant || constant2.typeID() != 11 || (irritantSetWarningTokenToIrritants = CompilerOptions.warningTokenToIrritants(constant2.stringValue())) == null || irritantSetWarningTokenToIrritants.areAllSet() || !compilerOptions2.isAnyEnabled(irritantSetWarningTokenToIrritants) || ((irritantSet = irritantSetArr2[i2]) != null && irritantSet.isAnySet(irritantSetWarningTokenToIrritants))) {
                                    break;
                                }
                                if (z) {
                                    int i26 = expression.sourceStart;
                                    int i27 = expression.sourceEnd;
                                    int i28 = i2 - 1;
                                    while (true) {
                                        if (i28 >= 0) {
                                            long j4 = this.suppressWarningScopePositions[i28];
                                            int i29 = (int) (j4 >>> 32);
                                            int i30 = (int) j4;
                                            if (i26 >= i29 && i27 <= i30 && this.suppressWarningIrritants[i28].areAllSet()) {
                                                break;
                                            } else {
                                                i28--;
                                            }
                                        } else {
                                            this.scope.problemReporter().unusedWarningToken(expression);
                                            break;
                                        }
                                    }
                                } else {
                                    this.scope.problemReporter().unusedWarningToken(expression);
                                    break;
                                    break;
                                }
                            }
                        } else {
                            i19++;
                        }
                    }
                    i = i17;
                    i2 = i18;
                    z = z4;
                    break;
                    break;
                }
            } else {
                i = i17;
                i2 = i18;
                z = z4;
                break;
                break;
            }
            i18 = i2 + 1;
            i17 = i;
            z4 = z;
        }
    }

    public void generateCode() {
        int i = 0;
        if (this.ignoreFurtherInvestigation) {
            TypeDeclaration[] typeDeclarationArr = this.types;
            if (typeDeclarationArr != null) {
                int length = typeDeclarationArr.length;
                while (i < length) {
                    this.types[i].ignoreFurtherInvestigation = true;
                    this.types[i].generateCode(this.scope);
                    i++;
                }
                return;
            }
            return;
        }
        try {
            TypeDeclaration[] typeDeclarationArr2 = this.types;
            if (typeDeclarationArr2 != null) {
                int length2 = typeDeclarationArr2.length;
                while (i < length2) {
                    this.types[i].generateCode(this.scope);
                    i++;
                }
            }
        } catch (AbortCompilationUnit unused) {
        }
    }

    public char[] getFileName() {
        return this.compilationResult.getFileName();
    }

    public char[] getMainTypeName() {
        if (this.compilationResult.compilationUnit == null) {
            char[] fileName = this.compilationResult.getFileName();
            int iLastIndexOf = CharOperation.lastIndexOf('/', fileName) + 1;
            if (iLastIndexOf == 0 || iLastIndexOf < CharOperation.lastIndexOf('\\', fileName)) {
                iLastIndexOf = CharOperation.lastIndexOf('\\', fileName) + 1;
            }
            int iLastIndexOf2 = CharOperation.lastIndexOf('.', fileName);
            if (iLastIndexOf2 == -1) {
                iLastIndexOf2 = fileName.length;
            }
            return CharOperation.subarray(fileName, iLastIndexOf, iLastIndexOf2);
        }
        return this.compilationResult.compilationUnit.getMainTypeName();
    }

    public boolean isEmpty() {
        return this.currentPackage == null && this.imports == null && this.types == null;
    }

    public boolean isPackageInfo() {
        return CharOperation.equals(getMainTypeName(), TypeConstants.PACKAGE_INFO_NAME);
    }

    public boolean isSuppressed(CategorizedProblem categorizedProblem) {
        int irritant;
        if (this.suppressWarningsCount == 0 || (irritant = ProblemReporter.getIrritant(categorizedProblem.getID())) == 0) {
            return false;
        }
        int sourceStart = categorizedProblem.getSourceStart();
        int sourceEnd = categorizedProblem.getSourceEnd();
        int i = this.suppressWarningsCount;
        for (int i2 = 0; i2 < i; i2++) {
            long j = this.suppressWarningScopePositions[i2];
            int i3 = (int) (j >>> 32);
            int i4 = (int) j;
            if (sourceStart >= i3 && sourceEnd <= i4 && this.suppressWarningIrritants[i2].isSet(irritant)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasFunctionalTypes() {
        return this.compilationResult.hasFunctionalTypes;
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public boolean hasErrors() {
        return this.ignoreFurtherInvestigation;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public StringBuffer print(int i, StringBuffer stringBuffer) {
        int i2 = 0;
        if (this.currentPackage != null) {
            printIndent(i, stringBuffer).append("package ");
            this.currentPackage.print(0, stringBuffer, false).append(";\n");
        }
        if (this.imports != null) {
            for (int i3 = 0; i3 < this.imports.length; i3++) {
                printIndent(i, stringBuffer).append("import ");
                ImportReference importReference = this.imports[i3];
                if (importReference.isStatic()) {
                    stringBuffer.append("static ");
                }
                importReference.print(0, stringBuffer).append(";\n");
            }
        }
        if (this.types != null) {
            while (true) {
                TypeDeclaration[] typeDeclarationArr = this.types;
                if (i2 >= typeDeclarationArr.length) {
                    break;
                }
                typeDeclarationArr[i2].print(i, stringBuffer).append("\n");
                i2++;
            }
        }
        return stringBuffer;
    }

    public void propagateInnerEmulationForAllLocalTypes() {
        this.isPropagatingInnerClassEmulation = true;
        int i = this.localTypeCount;
        for (int i2 = 0; i2 < i; i2++) {
            LocalTypeBinding localTypeBinding = this.localTypes[i2];
            if ((localTypeBinding.scope.referenceType().bits & Integer.MIN_VALUE) != 0) {
                localTypeBinding.updateInnerEmulationDependents();
            }
        }
    }

    public void recordStringLiteral(StringLiteral stringLiteral, boolean z) {
        HashSetOfInt hashSetOfInt = this.stringLiteralsStart;
        if (hashSetOfInt != null) {
            if (hashSetOfInt.contains(stringLiteral.sourceStart)) {
                return;
            } else {
                this.stringLiteralsStart.add(stringLiteral.sourceStart);
            }
        } else if (z) {
            this.stringLiteralsStart = new HashSetOfInt(this.stringLiteralsPtr + 10);
            for (int i = 0; i < this.stringLiteralsPtr; i++) {
                this.stringLiteralsStart.add(this.stringLiterals[i].sourceStart);
            }
            if (this.stringLiteralsStart.contains(stringLiteral.sourceStart)) {
                return;
            } else {
                this.stringLiteralsStart.add(stringLiteral.sourceStart);
            }
        }
        StringLiteral[] stringLiteralArr = this.stringLiterals;
        if (stringLiteralArr == null) {
            this.stringLiterals = new StringLiteral[10];
            this.stringLiteralsPtr = 0;
        } else {
            int length = stringLiteralArr.length;
            if (this.stringLiteralsPtr == length) {
                StringLiteral[] stringLiteralArr2 = new StringLiteral[length + 10];
                this.stringLiterals = stringLiteralArr2;
                System.arraycopy(stringLiteralArr, 0, stringLiteralArr2, 0, length);
            }
        }
        StringLiteral[] stringLiteralArr3 = this.stringLiterals;
        int i2 = this.stringLiteralsPtr;
        this.stringLiteralsPtr = i2 + 1;
        stringLiteralArr3[i2] = stringLiteral;
    }

    public void recordSuppressWarnings(IrritantSet irritantSet, Annotation annotation, int i, int i2, ReferenceContext referenceContext) {
        if (!(referenceContext instanceof LambdaExpression) || referenceContext == ((LambdaExpression) referenceContext).original()) {
            IrritantSet[] irritantSetArr = this.suppressWarningIrritants;
            if (irritantSetArr == null) {
                this.suppressWarningIrritants = new IrritantSet[3];
                this.suppressWarningAnnotations = new Annotation[3];
                this.suppressWarningScopePositions = new long[3];
            } else {
                int length = irritantSetArr.length;
                int i3 = this.suppressWarningsCount;
                if (length == i3) {
                    IrritantSet[] irritantSetArr2 = new IrritantSet[i3 * 2];
                    this.suppressWarningIrritants = irritantSetArr2;
                    System.arraycopy(irritantSetArr, 0, irritantSetArr2, 0, i3);
                    Annotation[] annotationArr = this.suppressWarningAnnotations;
                    int i4 = this.suppressWarningsCount;
                    Annotation[] annotationArr2 = new Annotation[i4 * 2];
                    this.suppressWarningAnnotations = annotationArr2;
                    System.arraycopy(annotationArr, 0, annotationArr2, 0, i4);
                    long[] jArr = this.suppressWarningScopePositions;
                    int i5 = this.suppressWarningsCount;
                    long[] jArr2 = new long[i5 * 2];
                    this.suppressWarningScopePositions = jArr2;
                    System.arraycopy(jArr, 0, jArr2, 0, i5);
                }
            }
            long j = (((long) i) << 32) + ((long) i2);
            int i6 = this.suppressWarningsCount;
            for (int i7 = 0; i7 < i6; i7++) {
                if (this.suppressWarningAnnotations[i7] == annotation && this.suppressWarningScopePositions[i7] == j && this.suppressWarningIrritants[i7].hasSameIrritants(irritantSet)) {
                    return;
                }
            }
            IrritantSet[] irritantSetArr3 = this.suppressWarningIrritants;
            int i8 = this.suppressWarningsCount;
            irritantSetArr3[i8] = irritantSet;
            this.suppressWarningAnnotations[i8] = annotation;
            long[] jArr3 = this.suppressWarningScopePositions;
            this.suppressWarningsCount = i8 + 1;
            jArr3[i8] = j;
        }
    }

    public void record(LocalTypeBinding localTypeBinding) {
        int i = this.localTypeCount;
        if (i == 0) {
            this.localTypes = new LocalTypeBinding[5];
        } else {
            LocalTypeBinding[] localTypeBindingArr = this.localTypes;
            if (i == localTypeBindingArr.length) {
                LocalTypeBinding[] localTypeBindingArr2 = new LocalTypeBinding[i * 2];
                this.localTypes = localTypeBindingArr2;
                System.arraycopy(localTypeBindingArr, 0, localTypeBindingArr2, 0, i);
            }
        }
        LocalTypeBinding[] localTypeBindingArr3 = this.localTypes;
        int i2 = this.localTypeCount;
        this.localTypeCount = i2 + 1;
        localTypeBindingArr3[i2] = localTypeBinding;
    }

    public int record(FunctionalExpression functionalExpression) {
        int i = this.functionalExpressionsCount;
        if (i == 0) {
            this.functionalExpressions = new FunctionalExpression[5];
        } else {
            FunctionalExpression[] functionalExpressionArr = this.functionalExpressions;
            if (i == functionalExpressionArr.length) {
                FunctionalExpression[] functionalExpressionArr2 = new FunctionalExpression[i * 2];
                this.functionalExpressions = functionalExpressionArr2;
                System.arraycopy(functionalExpressionArr, 0, functionalExpressionArr2, 0, i);
            }
        }
        FunctionalExpression[] functionalExpressionArr3 = this.functionalExpressions;
        int i2 = this.functionalExpressionsCount;
        this.functionalExpressionsCount = i2 + 1;
        functionalExpressionArr3[i2] = functionalExpression;
        return functionalExpression.enclosingScope.classScope().referenceContext.record(functionalExpression);
    }

    public void resolve() {
        int i;
        boolean zIsPackageInfo = isPackageInfo();
        TypeDeclaration[] typeDeclarationArr = this.types;
        if (typeDeclarationArr != null && zIsPackageInfo) {
            TypeDeclaration typeDeclaration = typeDeclarationArr[0];
            if (typeDeclaration.javadoc == null) {
                typeDeclaration.javadoc = new Javadoc(typeDeclaration.declarationSourceStart, typeDeclaration.declarationSourceStart);
            }
            typeDeclaration.resolve(this.scope);
            if (this.javadoc != null && typeDeclaration.staticInitializerScope != null) {
                this.javadoc.resolve(typeDeclaration.staticInitializerScope);
            }
            i = 1;
        } else {
            Javadoc javadoc = this.javadoc;
            if (javadoc != null) {
                javadoc.resolve(this.scope);
            }
            i = 0;
        }
        ImportReference importReference = this.currentPackage;
        if (importReference != null && importReference.annotations != null && !zIsPackageInfo) {
            this.scope.problemReporter().invalidFileNameForPackageAnnotations(this.currentPackage.annotations[0]);
        }
        try {
            TypeDeclaration[] typeDeclarationArr2 = this.types;
            if (typeDeclarationArr2 != null) {
                int length = typeDeclarationArr2.length;
                while (i < length) {
                    this.types[i].resolve(this.scope);
                    i++;
                }
            }
            if (!this.compilationResult.hasMandatoryErrors()) {
                checkUnusedImports();
            }
            reportNLSProblems();
        } catch (AbortCompilationUnit unused) {
            this.ignoreFurtherInvestigation = true;
        }
    }

    private void reportNLSProblems() {
        NLSTag[] nLSTagArr = this.nlsTags;
        if (nLSTagArr == null && this.stringLiterals == null) {
            return;
        }
        int i = this.stringLiteralsPtr;
        int i2 = 0;
        int length = nLSTagArr == null ? 0 : nLSTagArr.length;
        if (i == 0) {
            if (length != 0) {
                while (i2 < length) {
                    NLSTag nLSTag = this.nlsTags[i2];
                    if (nLSTag != null) {
                        this.scope.problemReporter().unnecessaryNLSTags(nLSTag.start, nLSTag.end);
                    }
                    i2++;
                }
                return;
            }
            return;
        }
        if (length == 0) {
            StringLiteral[] stringLiteralArr = this.stringLiterals;
            if (stringLiteralArr.length != i) {
                StringLiteral[] stringLiteralArr2 = new StringLiteral[i];
                this.stringLiterals = stringLiteralArr2;
                System.arraycopy(stringLiteralArr, 0, stringLiteralArr2, 0, i);
            }
            Arrays.sort(this.stringLiterals, STRING_LITERAL_COMPARATOR);
            while (i2 < i) {
                this.scope.problemReporter().nonExternalizedStringLiteral(this.stringLiterals[i2]);
                i2++;
            }
            return;
        }
        StringLiteral[] stringLiteralArr3 = this.stringLiterals;
        if (stringLiteralArr3.length != i) {
            StringLiteral[] stringLiteralArr4 = new StringLiteral[i];
            this.stringLiterals = stringLiteralArr4;
            System.arraycopy(stringLiteralArr3, 0, stringLiteralArr4, 0, i);
        }
        Arrays.sort(this.stringLiterals, STRING_LITERAL_COMPARATOR);
        int i3 = 1;
        int i4 = -1;
        int i5 = 0;
        loop2: while (i2 < i) {
            StringLiteral stringLiteral = this.stringLiterals[i2];
            int i6 = stringLiteral.lineNumber;
            if (i4 != i6) {
                i3 = 1;
                i4 = i6;
            } else {
                i3++;
            }
            if (i5 >= length) {
                break;
            }
            while (true) {
                if (i5 >= length) {
                    break loop2;
                }
                NLSTag nLSTag2 = this.nlsTags[i5];
                if (nLSTag2 != null) {
                    int i7 = nLSTag2.lineNumber;
                    if (i6 < i7) {
                        this.scope.problemReporter().nonExternalizedStringLiteral(stringLiteral);
                        break;
                    }
                    if (i6 == i7) {
                        if (nLSTag2.index != i3) {
                            int i8 = i5 + 1;
                            while (true) {
                                if (i8 < length) {
                                    NLSTag nLSTag3 = this.nlsTags[i8];
                                    if (nLSTag3 != null) {
                                        if (i6 == nLSTag3.lineNumber) {
                                            if (nLSTag3.index == i3) {
                                                this.nlsTags[i8] = null;
                                                break;
                                            }
                                        } else {
                                            this.scope.problemReporter().nonExternalizedStringLiteral(stringLiteral);
                                            break;
                                        }
                                    }
                                    i8++;
                                } else {
                                    this.scope.problemReporter().nonExternalizedStringLiteral(stringLiteral);
                                    break;
                                }
                            }
                        } else {
                            this.nlsTags[i5] = null;
                            i5++;
                            break;
                        }
                    } else {
                        this.scope.problemReporter().unnecessaryNLSTags(nLSTag2.start, nLSTag2.end);
                    }
                }
                i5++;
            }
            i2++;
        }
        while (i2 < i) {
            this.scope.problemReporter().nonExternalizedStringLiteral(this.stringLiterals[i2]);
            i2++;
        }
        if (i5 < length) {
            while (i5 < length) {
                NLSTag nLSTag4 = this.nlsTags[i5];
                if (nLSTag4 != null) {
                    this.scope.problemReporter().unnecessaryNLSTags(nLSTag4.start, nLSTag4.end);
                }
                i5++;
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.impl.ReferenceContext
    public void tagAsHavingErrors() {
        this.ignoreFurtherInvestigation = true;
    }

    public void traverse(ASTVisitor aSTVisitor, CompilationUnitScope compilationUnitScope) {
        traverse(aSTVisitor, compilationUnitScope, true);
    }

    public void traverse(ASTVisitor aSTVisitor, CompilationUnitScope compilationUnitScope, boolean z) {
        Annotation[] annotationArr;
        if (z && this.ignoreFurtherInvestigation) {
            return;
        }
        try {
            if (aSTVisitor.visit(this, this.scope)) {
                if (this.types != null && isPackageInfo()) {
                    MethodScope methodScope = this.types[0].staticInitializerScope;
                    Javadoc javadoc = this.javadoc;
                    if (javadoc != null && methodScope != null) {
                        javadoc.traverse(aSTVisitor, methodScope);
                    }
                    ImportReference importReference = this.currentPackage;
                    if (importReference != null && methodScope != null && (annotationArr = importReference.annotations) != null) {
                        for (Annotation annotation : annotationArr) {
                            annotation.traverse(aSTVisitor, methodScope);
                        }
                    }
                }
                ImportReference importReference2 = this.currentPackage;
                if (importReference2 != null) {
                    importReference2.traverse(aSTVisitor, this.scope);
                }
                ImportReference[] importReferenceArr = this.imports;
                if (importReferenceArr != null) {
                    int length = importReferenceArr.length;
                    for (int i = 0; i < length; i++) {
                        this.imports[i].traverse(aSTVisitor, this.scope);
                    }
                }
                TypeDeclaration[] typeDeclarationArr = this.types;
                if (typeDeclarationArr != null) {
                    int length2 = typeDeclarationArr.length;
                    for (int i2 = 0; i2 < length2; i2++) {
                        this.types[i2].traverse(aSTVisitor, this.scope);
                    }
                }
            }
            aSTVisitor.endVisit(this, this.scope);
        } catch (AbortCompilationUnit unused) {
        }
    }
}
