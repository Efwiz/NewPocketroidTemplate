package org.eclipse.jdt.internal.compiler.ast;

import java.util.Arrays;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CaseLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.SwitchFlowContext;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.SyntheticMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class SwitchStatement extends Statement {
    public static final int CASE = 0;
    public static final int ESCAPING = 2;
    public static final int FALLTHROUGH = 1;
    private static final char[] SecretStringVariableName = " switchDispatchString".toCharArray();
    public int blockStart;
    public BranchLabel breakLabel;
    public int caseCount;
    public CaseStatement[] cases;
    int[] constants;
    public CaseStatement defaultCase;
    public int explicitDeclarations;
    public Expression expression;
    public BlockScope scope;
    public Statement[] statements;
    String[] stringConstants;
    public SyntheticMethodBinding synthetic;
    int preSwitchInitStateIndex = -1;
    int mergedInitStateIndex = -1;
    CaseStatement[] duplicateCaseStatements = null;
    int duplicateCaseStatementsCounter = 0;
    private LocalVariableBinding dispatchStringCopy = null;

    /* JADX WARN: Code duplicated, block: B:43:0x00d0 A[Catch: all -> 0x012f, TryCatch #0 {all -> 0x012f, blocks: (B:3:0x0007, B:5:0x0018, B:7:0x001e, B:9:0x0028, B:12:0x0037, B:14:0x0053, B:18:0x005f, B:21:0x006a, B:23:0x0074, B:25:0x007a, B:27:0x0082, B:29:0x0088, B:30:0x0095, B:41:0x00c7, B:43:0x00d0, B:46:0x00db, B:47:0x00de, B:31:0x009e, B:33:0x00a2, B:35:0x00a8, B:37:0x00ae, B:38:0x00bb, B:48:0x00e2, B:50:0x00ec, B:51:0x00fa, B:53:0x00fe, B:58:0x0118, B:11:0x0032), top: B:68:0x0007 }] */
    /* JADX WARN: Code duplicated, block: B:45:0x00da  */
    /* JADX WARN: Code duplicated, block: B:71:0x00de A[SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v1 */
    /* JADX WARN: Type inference failed for: r12v10 */
    /* JADX WARN: Type inference failed for: r12v11 */
    /* JADX WARN: Type inference failed for: r12v12 */
    /* JADX WARN: Type inference failed for: r12v18 */
    /* JADX WARN: Type inference failed for: r12v19 */
    /* JADX WARN: Type inference failed for: r12v2 */
    /* JADX WARN: Type inference failed for: r12v8 */
    /* JADX WARN: Type inference failed for: r12v9 */
    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public FlowInfo analyseCode(BlockScope blockScope, FlowContext flowContext, FlowInfo flowInfo) {
        ?? r12;
        CaseStatement caseStatement;
        try {
            FlowInfo flowInfoAnalyseCode = this.expression.analyseCode(blockScope, flowContext, flowInfo);
            boolean z = true;
            if ((this.expression.implicitConversion & 1024) != 0 || (this.expression.resolvedType != null && (this.expression.resolvedType.f626id == 11 || this.expression.resolvedType.isEnum()))) {
                this.expression.checkNPE(blockScope, flowContext, flowInfoAnalyseCode, 1);
            }
            BranchLabel branchLabel = new BranchLabel();
            this.breakLabel = branchLabel;
            SwitchFlowContext switchFlowContext = new SwitchFlowContext(flowContext, this, branchLabel, true);
            FlowInfo flowInfoMergedWith = FlowInfo.DEAD_END;
            this.preSwitchInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfoAnalyseCode);
            if (this.statements != null) {
                int i = (flowInfoAnalyseCode.reachMode() & 3) != 0 ? 1 : 0;
                int length = this.statements.length;
                int iComplainIfUnreachable = i;
                int i2 = 0;
                int i3 = 0;
                ?? r13 = 0;
                while (i2 < length) {
                    Statement statement = this.statements[i2];
                    if (i3 < this.caseCount && statement == (caseStatement = this.cases[i3])) {
                        this.scope.enclosingCase = caseStatement;
                        i3++;
                        if (r13 == z && (statement.bits & 536870912) == 0) {
                            this.scope.problemReporter().possibleFallThroughCase(this.scope.enclosingCase);
                        }
                        flowInfoMergedWith = flowInfoMergedWith.mergedWith(flowInfoAnalyseCode.unconditionalInits());
                    } else {
                        CaseStatement caseStatement2 = this.defaultCase;
                        if (statement == caseStatement2) {
                            this.scope.enclosingCase = caseStatement2;
                            if (r13 == z && (statement.bits & 536870912) == 0) {
                                this.scope.problemReporter().possibleFallThroughCase(this.scope.enclosingCase);
                            }
                            flowInfoMergedWith = flowInfoMergedWith.mergedWith(flowInfoAnalyseCode.unconditionalInits());
                        } else {
                            r12 = z;
                        }
                        iComplainIfUnreachable = statement.complainIfUnreachable(flowInfoMergedWith, this.scope, iComplainIfUnreachable, z);
                        if (iComplainIfUnreachable >= 2) {
                            flowInfoMergedWith = statement.analyseCode(this.scope, switchFlowContext, flowInfoMergedWith);
                            if (flowInfoMergedWith == FlowInfo.DEAD_END) {
                                r12 = r12;
                                r12 = 2;
                            }
                            r12 = r12;
                            switchFlowContext.expireNullCheckedFieldInfo();
                        }
                        i2++;
                        z = true;
                        r13 = r12;
                    }
                    iComplainIfUnreachable = i;
                    r12 = 0;
                    iComplainIfUnreachable = statement.complainIfUnreachable(flowInfoMergedWith, this.scope, iComplainIfUnreachable, z);
                    if (iComplainIfUnreachable >= 2) {
                        flowInfoMergedWith = statement.analyseCode(this.scope, switchFlowContext, flowInfoMergedWith);
                        if (flowInfoMergedWith == FlowInfo.DEAD_END) {
                            r12 = r12;
                            r12 = 2;
                        }
                        r12 = r12;
                        switchFlowContext.expireNullCheckedFieldInfo();
                    }
                    i2++;
                    z = true;
                    r13 = r12;
                }
            }
            TypeBinding typeBinding = this.expression.resolvedType;
            if (typeBinding.isEnum()) {
                this.synthetic = blockScope.classScope().referenceContext.binding.addSyntheticMethodForSwitchEnum(typeBinding);
            }
            if (this.defaultCase == null) {
                flowInfoAnalyseCode.addPotentialInitializationsFrom(flowInfoMergedWith.mergedWith(switchFlowContext.initsOnBreak));
                this.mergedInitStateIndex = blockScope.methodScope().recordInitializationStates(flowInfoAnalyseCode);
                return flowInfoAnalyseCode;
            }
            UnconditionalFlowInfo unconditionalFlowInfoMergedWith = flowInfoMergedWith.mergedWith(switchFlowContext.initsOnBreak);
            this.mergedInitStateIndex = blockScope.methodScope().recordInitializationStates(unconditionalFlowInfoMergedWith);
            return unconditionalFlowInfoMergedWith;
        } finally {
            BlockScope blockScope2 = this.scope;
            if (blockScope2 != null) {
                blockScope2.enclosingCase = null;
            }
        }
    }

    public void generateCodeForStringSwitch(BlockScope blockScope, CodeStream codeStream) {
        BlockScope blockScope2;
        CaseStatement caseStatement;
        try {
            if ((this.bits & Integer.MIN_VALUE) == 0) {
                if (blockScope2 != null) {
                    return;
                } else {
                    return;
                }
            }
            int i = codeStream.position;
            int i2 = this.caseCount;
            boolean z = true;
            boolean z2 = i2 != 0;
            C1StringSwitchCase[] c1StringSwitchCaseArr = new C1StringSwitchCase[i2];
            BranchLabel[] branchLabelArr = new BranchLabel[i2];
            CaseLabel[] caseLabelArr = new CaseLabel[i2];
            this.constants = new int[i2];
            int i3 = 0;
            while (i3 < i2) {
                CaseStatement caseStatement2 = this.cases[i3];
                BranchLabel branchLabel = new BranchLabel(codeStream);
                branchLabelArr[i3] = branchLabel;
                caseStatement2.targetLabel = branchLabel;
                branchLabelArr[i3].tagBits |= 2;
                c1StringSwitchCaseArr[i3] = new C1StringSwitchCase(this.stringConstants[i3].hashCode(), this.stringConstants[i3], branchLabelArr[i3]);
                CaseLabel caseLabel = new CaseLabel(codeStream);
                caseLabelArr[i3] = caseLabel;
                caseLabel.tagBits |= 2;
                i3++;
                z = true;
            }
            Arrays.sort(c1StringSwitchCaseArr);
            int i4 = this.caseCount;
            int i5 = 0;
            int i6 = 0;
            for (int i7 = 0; i7 < i4; i7++) {
                int i8 = c1StringSwitchCaseArr[i7].hashCode;
                if (i7 == 0 || i8 != i6) {
                    this.constants[i5] = i8;
                    i6 = i8;
                    i5++;
                }
            }
            if (i5 != this.caseCount) {
                int[] iArr = this.constants;
                int[] iArr2 = new int[i5];
                this.constants = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, i5);
                CaseLabel[] caseLabelArr2 = new CaseLabel[i5];
                System.arraycopy(caseLabelArr, 0, caseLabelArr2, 0, i5);
                caseLabelArr = caseLabelArr2;
            }
            int[] iArr3 = new int[i5];
            for (int i9 = 0; i9 < i5; i9++) {
                iArr3[i9] = i9;
            }
            CaseLabel caseLabel2 = new CaseLabel(codeStream);
            caseLabel2.tagBits |= 2;
            this.breakLabel.initialize(codeStream);
            BranchLabel branchLabel2 = new BranchLabel(codeStream);
            if (z2) {
                branchLabel2.tagBits |= 2;
            }
            CaseStatement caseStatement3 = this.defaultCase;
            if (caseStatement3 != null) {
                caseStatement3.targetLabel = branchLabel2;
            }
            this.expression.generateCode(blockScope, codeStream, z);
            codeStream.store(this.dispatchStringCopy, z);
            codeStream.addVariable(this.dispatchStringCopy);
            codeStream.invokeStringHashCode();
            if (z2) {
                codeStream.lookupswitch(caseLabel2, this.constants, iArr3, caseLabelArr);
                int i10 = this.caseCount;
                int i11 = 0;
                for (int i12 = 0; i12 < i10; i12++) {
                    int i13 = c1StringSwitchCaseArr[i12].hashCode;
                    if (i12 == 0 || i13 != i6) {
                        if (i12 != 0) {
                            codeStream.goto_(branchLabel2);
                        }
                        caseLabelArr[i11].place();
                        i11++;
                        i6 = i13;
                    }
                    codeStream.load(this.dispatchStringCopy);
                    codeStream.ldc(c1StringSwitchCaseArr[i12].string);
                    codeStream.invokeStringEquals();
                    codeStream.ifne(c1StringSwitchCaseArr[i12].label);
                }
                codeStream.goto_(branchLabel2);
            } else {
                codeStream.pop();
            }
            Statement[] statementArr = this.statements;
            if (statementArr != null) {
                int length = statementArr.length;
                int i14 = 0;
                for (int i15 = 0; i15 < length; i15++) {
                    Statement statement = this.statements[i15];
                    if (i14 < this.caseCount && statement == (caseStatement = this.cases[i14])) {
                        this.scope.enclosingCase = caseStatement;
                        int i16 = this.preSwitchInitStateIndex;
                        if (i16 != -1) {
                            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i16);
                        }
                        i14++;
                    } else if (statement == this.defaultCase) {
                        caseLabel2.place();
                        this.scope.enclosingCase = this.defaultCase;
                        int i17 = this.preSwitchInitStateIndex;
                        if (i17 != -1) {
                            codeStream.removeNotDefinitelyAssignedVariables(blockScope, i17);
                        }
                    }
                    statement.generateCode(this.scope, codeStream);
                }
            }
            int i18 = this.mergedInitStateIndex;
            if (i18 != -1) {
                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i18);
                codeStream.addDefinitelyAssignedVariables(blockScope, this.mergedInitStateIndex);
            }
            codeStream.removeVariable(this.dispatchStringCopy);
            BlockScope blockScope3 = this.scope;
            if (blockScope3 != blockScope) {
                codeStream.exitUserScope(blockScope3);
            }
            this.breakLabel.place();
            if (this.defaultCase == null) {
                codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd, z);
                caseLabel2.place();
                branchLabel2.place();
            }
            codeStream.recordPositionsFrom(i, this.sourceStart);
        } finally {
            blockScope2 = this.scope;
            if (blockScope2 != null) {
                blockScope2.enclosingCase = null;
            }
        }
    }

    /* JADX INFO: renamed from: org.eclipse.jdt.internal.compiler.ast.SwitchStatement$1StringSwitchCase, reason: invalid class name */
    class C1StringSwitchCase implements Comparable {
        int hashCode;
        BranchLabel label;
        String string;

        public C1StringSwitchCase(int i, String str, BranchLabel branchLabel) {
            this.hashCode = i;
            this.string = str;
            this.label = branchLabel;
        }

        @Override // java.lang.Comparable
        public int compareTo(Object obj) {
            int i = this.hashCode;
            int i2 = ((C1StringSwitchCase) obj).hashCode;
            if (i == i2) {
                return 0;
            }
            return i > i2 ? 1 : -1;
        }

        public String toString() {
            return "StringSwitchCase :\ncase " + this.hashCode + ":(" + this.string + ")\n";
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
        boolean z;
        int i;
        CaseStatement caseStatement;
        int i2;
        if (this.expression.resolvedType.f626id == 11) {
            generateCodeForStringSwitch(blockScope, codeStream);
            return;
        }
        TypeBinding typeBinding = null;
        try {
            if ((this.bits & Integer.MIN_VALUE) != 0) {
                int i3 = codeStream.position;
                this.breakLabel.initialize(codeStream);
                int i4 = this.caseCount;
                CaseLabel[] caseLabelArr = new CaseLabel[i4];
                int i5 = 0;
                while (i5 < i4) {
                    int i6 = i3;
                    CaseStatement caseStatement2 = this.cases[i5];
                    CaseLabel caseLabel = new CaseLabel(codeStream);
                    caseLabelArr[i5] = caseLabel;
                    caseStatement2.targetLabel = caseLabel;
                    caseLabelArr[i5].tagBits |= 2;
                    i5++;
                    i3 = i6;
                    typeBinding = null;
                }
                CaseLabel caseLabel2 = new CaseLabel(codeStream);
                int i7 = 1;
                boolean z2 = this.caseCount != 0;
                if (z2) {
                    caseLabel2.tagBits |= 2;
                }
                CaseStatement caseStatement3 = this.defaultCase;
                if (caseStatement3 != null) {
                    caseStatement3.targetLabel = caseLabel2;
                }
                TypeBinding typeBinding2 = this.expression.resolvedType;
                if (typeBinding2.isEnum()) {
                    codeStream.invoke(Opcodes.OPC_invokestatic, this.synthetic, typeBinding);
                    this.expression.generateCode(blockScope, codeStream, true);
                    codeStream.invokeEnumOrdinal(typeBinding2.constantPoolName());
                    codeStream.iaload();
                    if (!z2) {
                        codeStream.pop();
                    }
                    z = false;
                } else {
                    z = this.expression.constant == Constant.NotAConstant || z2;
                    this.expression.generateCode(blockScope, codeStream, z);
                }
                if (z2) {
                    int[] iArr = new int[this.caseCount];
                    int i8 = 0;
                    while (true) {
                        i2 = this.caseCount;
                        if (i8 >= i2) {
                            break;
                        }
                        iArr[i8] = i8;
                        i8++;
                        i3 = i3;
                        i7 = 1;
                    }
                    int[] iArr2 = new int[i2];
                    System.arraycopy(this.constants, 0, iArr2, 0, i2);
                    CodeStream.sort(iArr2, 0, this.caseCount - i7, iArr);
                    int i9 = this.caseCount;
                    int i10 = iArr2[i9 - 1];
                    int i11 = iArr2[0];
                    i = i3;
                    if (((long) (((double) i9) * 2.5d)) <= ((long) i10) - ((long) i11)) {
                        codeStream.lookupswitch(caseLabel2, this.constants, iArr, caseLabelArr);
                    } else if (i10 > 2147418112 && blockScope.compilerOptions().complianceLevel < ClassFileConstants.JDK1_4) {
                        codeStream.lookupswitch(caseLabel2, this.constants, iArr, caseLabelArr);
                    } else {
                        codeStream.tableswitch(caseLabel2, i11, i10, this.constants, iArr, caseLabelArr);
                    }
                    codeStream.recordPositionsFrom(codeStream.position, this.expression.sourceEnd);
                } else {
                    i = i3;
                    if (z) {
                        codeStream.pop();
                    }
                }
                Statement[] statementArr = this.statements;
                if (statementArr != null) {
                    int length = statementArr.length;
                    int i12 = 0;
                    for (int i13 = 0; i13 < length; i13++) {
                        Statement statement = this.statements[i13];
                        if (i12 < this.caseCount && statement == (caseStatement = this.cases[i12])) {
                            this.scope.enclosingCase = caseStatement;
                            int i14 = this.preSwitchInitStateIndex;
                            if (i14 != -1) {
                                codeStream.removeNotDefinitelyAssignedVariables(blockScope, i14);
                            }
                            i12++;
                        } else {
                            CaseStatement caseStatement4 = this.defaultCase;
                            if (statement == caseStatement4) {
                                this.scope.enclosingCase = caseStatement4;
                                int i15 = this.preSwitchInitStateIndex;
                                if (i15 != -1) {
                                    codeStream.removeNotDefinitelyAssignedVariables(blockScope, i15);
                                }
                            }
                        }
                        statement.generateCode(this.scope, codeStream);
                    }
                }
                int i16 = this.mergedInitStateIndex;
                if (i16 != -1) {
                    codeStream.removeNotDefinitelyAssignedVariables(blockScope, i16);
                    codeStream.addDefinitelyAssignedVariables(blockScope, this.mergedInitStateIndex);
                }
                BlockScope blockScope2 = this.scope;
                if (blockScope2 != blockScope) {
                    codeStream.exitUserScope(blockScope2);
                }
                this.breakLabel.place();
                if (this.defaultCase == null) {
                    codeStream.recordPositionsFrom(codeStream.position, this.sourceEnd, true);
                    caseLabel2.place();
                }
                codeStream.recordPositionsFrom(i, this.sourceStart);
                BlockScope blockScope3 = this.scope;
                if (blockScope3 != null) {
                    blockScope3.enclosingCase = null;
                    return;
                }
                return;
            }
            BlockScope blockScope4 = this.scope;
            if (blockScope4 != null) {
                blockScope4.enclosingCase = null;
            }
        } catch (Throwable th) {
            BlockScope blockScope5 = this.scope;
            if (blockScope5 != null) {
                blockScope5.enclosingCase = null;
            }
            throw th;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer).append("switch (");
        this.expression.printExpression(0, stringBuffer).append(") {");
        if (this.statements != null) {
            for (int i2 = 0; i2 < this.statements.length; i2++) {
                stringBuffer.append('\n');
                Statement statement = this.statements[i2];
                if (statement instanceof CaseStatement) {
                    statement.printStatement(i, stringBuffer);
                } else {
                    statement.printStatement(i + 2, stringBuffer);
                }
            }
        }
        stringBuffer.append("\n");
        return printIndent(i, stringBuffer).append('}');
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        boolean z;
        boolean z2;
        try {
            TypeBinding typeBindingResolveType = this.expression.resolveType(blockScope);
            CompilerOptions compilerOptions = blockScope.compilerOptions();
            int i = 0;
            if (typeBindingResolveType != null) {
                this.expression.computeConversion(blockScope, typeBindingResolveType, typeBindingResolveType);
                if (!typeBindingResolveType.isValidBinding()) {
                    z = false;
                    z2 = false;
                    typeBindingResolveType = null;
                } else if (typeBindingResolveType.isBaseType()) {
                    if (this.expression.isConstantValueOfTypeAssignableToType(typeBindingResolveType, TypeBinding.INT) || typeBindingResolveType.isCompatibleWith(TypeBinding.INT)) {
                        z = false;
                        z2 = false;
                    }
                    blockScope.problemReporter().incorrectSwitchType(this.expression, typeBindingResolveType);
                    z = false;
                    z2 = false;
                    typeBindingResolveType = null;
                } else if (typeBindingResolveType.isEnum()) {
                    if (compilerOptions.complianceLevel < ClassFileConstants.JDK1_5) {
                        blockScope.problemReporter().incorrectSwitchType(this.expression, typeBindingResolveType);
                    }
                    z2 = true;
                    z = false;
                } else if (blockScope.isBoxingCompatibleWith(typeBindingResolveType, TypeBinding.INT)) {
                    this.expression.computeConversion(blockScope, TypeBinding.INT, typeBindingResolveType);
                    z = false;
                    z2 = false;
                } else if (compilerOptions.complianceLevel < ClassFileConstants.JDK1_7 || typeBindingResolveType.f626id != 11) {
                    blockScope.problemReporter().incorrectSwitchType(this.expression, typeBindingResolveType);
                    z = false;
                    z2 = false;
                    typeBindingResolveType = null;
                } else {
                    z = true;
                    z2 = false;
                }
            } else {
                z = false;
                z2 = false;
            }
            if (z) {
                LocalVariableBinding localVariableBinding = new LocalVariableBinding(SecretStringVariableName, (TypeBinding) blockScope.getJavaLangString(), 0, false);
                this.dispatchStringCopy = localVariableBinding;
                blockScope.addLocalVariable(localVariableBinding);
                this.dispatchStringCopy.setConstant(Constant.NotAConstant);
                this.dispatchStringCopy.useFlag = 1;
            }
            if (this.statements != null) {
                this.scope = new BlockScope(blockScope);
                int length = this.statements.length;
                this.cases = new CaseStatement[length];
                if (!z) {
                    this.constants = new int[length];
                } else {
                    this.stringConstants = new String[length];
                }
                int i2 = 0;
                int i3 = 0;
                while (i2 < length) {
                    Statement statement = this.statements[i2];
                    Constant constantResolveCase = statement.resolveCase(this.scope, typeBindingResolveType, this);
                    if (constantResolveCase != Constant.NotAConstant) {
                        if (!z) {
                            int iIntValue = constantResolveCase.intValue();
                            for (int i4 = 0; i4 < i3; i4++) {
                                if (this.constants[i4] == iIntValue) {
                                    reportDuplicateCase((CaseStatement) statement, this.cases[i4], length);
                                }
                            }
                            this.constants[i3] = iIntValue;
                            i3++;
                        } else {
                            String strStringValue = constantResolveCase.stringValue();
                            for (int i5 = 0; i5 < i3; i5++) {
                                if (this.stringConstants[i5].equals(strStringValue)) {
                                    reportDuplicateCase((CaseStatement) statement, this.cases[i5], length);
                                }
                            }
                            this.stringConstants[i3] = strStringValue;
                            i3++;
                        }
                    }
                    i2++;
                    i = 0;
                }
                if (length != i3) {
                    if (!z) {
                        int[] iArr = this.constants;
                        int[] iArr2 = new int[i3];
                        this.constants = iArr2;
                        System.arraycopy(iArr, i, iArr2, i, i3);
                    } else {
                        String[] strArr = this.stringConstants;
                        String[] strArr2 = new String[i3];
                        this.stringConstants = strArr2;
                        System.arraycopy(strArr, i, strArr2, i, i3);
                    }
                }
            } else if ((this.bits & 8) != 0) {
                blockScope.problemReporter().undocumentedEmptyBlock(this.blockStart, this.sourceEnd);
            }
            if (this.defaultCase == null) {
                if (compilerOptions.getSeverity(CompilerOptions.MissingDefaultCase) != 256) {
                    blockScope.problemReporter().missingDefaultCase(this, z2, typeBindingResolveType);
                } else if (z2) {
                    blockScope.methodScope().hasMissingSwitchDefault = true;
                }
            }
            if (z2 && compilerOptions.complianceLevel >= ClassFileConstants.JDK1_5 && (this.defaultCase == null || compilerOptions.reportMissingEnumCaseDespiteDefault)) {
                int[] iArr3 = this.constants;
                int length2 = iArr3 == null ? 0 : iArr3.length;
                int i6 = this.caseCount;
                if (length2 == i6 && i6 != ((ReferenceBinding) typeBindingResolveType).enumConstantCount()) {
                    for (FieldBinding fieldBinding : ((ReferenceBinding) typeBindingResolveType.erasure()).fields()) {
                        if ((fieldBinding.modifiers & 16384) != 0) {
                            int i7 = 0;
                            while (true) {
                                if (i7 < this.caseCount) {
                                    if (fieldBinding.f627id + 1 == this.constants[i7]) {
                                        break;
                                    } else {
                                        i7++;
                                    }
                                } else {
                                    CaseStatement caseStatement = this.defaultCase;
                                    if (caseStatement != null && (caseStatement.bits & 1073741824) != 0) {
                                        break;
                                    }
                                    blockScope.problemReporter().missingEnumConstantCase(this, fieldBinding);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } finally {
            BlockScope blockScope2 = this.scope;
            if (blockScope2 != null) {
                blockScope2.enclosingCase = null;
            }
        }
    }

    private void reportDuplicateCase(CaseStatement caseStatement, CaseStatement caseStatement2, int i) {
        if (this.duplicateCaseStatements == null) {
            this.scope.problemReporter().duplicateCase(caseStatement2);
            this.scope.problemReporter().duplicateCase(caseStatement);
            CaseStatement[] caseStatementArr = new CaseStatement[i];
            this.duplicateCaseStatements = caseStatementArr;
            int i2 = this.duplicateCaseStatementsCounter;
            int i3 = i2 + 1;
            this.duplicateCaseStatementsCounter = i3;
            caseStatementArr[i2] = caseStatement2;
            this.duplicateCaseStatementsCounter = i2 + 2;
            caseStatementArr[i3] = caseStatement;
            return;
        }
        for (int i4 = 2; i4 < this.duplicateCaseStatementsCounter; i4++) {
            if (this.duplicateCaseStatements[i4] == caseStatement) {
                return;
            }
        }
        this.scope.problemReporter().duplicateCase(caseStatement);
        CaseStatement[] caseStatementArr2 = this.duplicateCaseStatements;
        int i5 = this.duplicateCaseStatementsCounter;
        this.duplicateCaseStatementsCounter = i5 + 1;
        caseStatementArr2[i5] = caseStatement;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.ASTNode
    public void traverse(ASTVisitor aSTVisitor, BlockScope blockScope) {
        if (aSTVisitor.visit(this, blockScope)) {
            this.expression.traverse(aSTVisitor, blockScope);
            Statement[] statementArr = this.statements;
            if (statementArr != null) {
                int length = statementArr.length;
                for (int i = 0; i < length; i++) {
                    this.statements[i].traverse(aSTVisitor, this.scope);
                }
            }
        }
        aSTVisitor.endVisit(this, blockScope);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public void branchChainTo(BranchLabel branchLabel) {
        if (this.breakLabel.forwardReferenceCount() > 0) {
            branchLabel.becomeDelegateFor(this.breakLabel);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean doesNotCompleteNormally() {
        Statement[] statementArr = this.statements;
        if (statementArr == null || statementArr.length == 0) {
            return false;
        }
        int length = statementArr.length;
        for (int i = 0; i < length; i++) {
            if (this.statements[i].breaksOut(null)) {
                return false;
            }
        }
        Statement[] statementArr2 = this.statements;
        return statementArr2[statementArr2.length - 1].doesNotCompleteNormally();
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.Statement
    public boolean completesByContinue() {
        Statement[] statementArr = this.statements;
        if (statementArr == null || statementArr.length == 0) {
            return false;
        }
        int length = statementArr.length;
        for (int i = 0; i < length; i++) {
            if (this.statements[i].completesByContinue()) {
                return true;
            }
        }
        return false;
    }
}
