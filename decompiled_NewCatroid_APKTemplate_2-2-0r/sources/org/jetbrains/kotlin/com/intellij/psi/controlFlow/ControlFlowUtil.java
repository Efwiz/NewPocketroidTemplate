package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.reandroid.archive.PathTree;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.codeInsight.ExceptionUtil;
import org.jetbrains.kotlin.com.intellij.codeInsight.ExpressionUtil;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.util.Condition;
import org.jetbrains.kotlin.com.intellij.psi.JavaResolveResult;
import org.jetbrains.kotlin.com.intellij.psi.PsiAssignmentExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiBlockStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiBreakStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiCatchSection;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiContinueStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiDeclarationStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiDirectory;
import org.jetbrains.kotlin.com.intellij.psi.PsiDisjunctionType;
import org.jetbrains.kotlin.com.intellij.psi.PsiDoWhileStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiExpressionStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiForStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiForeachStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiIfStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiLambdaExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiParenthesizedExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiPatternVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiReturnStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiSuperExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiSwitchBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiThisExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiTryStatement;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.PsiUnaryExpression;
import org.jetbrains.kotlin.com.intellij.psi.PsiVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhileStatement;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolder;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayUtilRt;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.com.intellij.util.ObjectUtils;
import org.jetbrains.kotlin.com.intellij.util.ReflectionUtil;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.IntStack;
import org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollector;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntArrayList;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntCollection;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntList;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.IntSet;

/* JADX INFO: loaded from: classes3.dex */
public final class ControlFlowUtil {
    public static final int NORMAL_COMPLETION_REASON = 1;
    private static final Logger LOG = Logger.getInstance((Class<?>) ControlFlowUtil.class);
    public static final Class<? extends PsiStatement>[] DEFAULT_EXIT_STATEMENTS_CLASSES = {PsiReturnStatement.class, PsiBreakStatement.class, PsiContinueStatement.class};

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 6 || i == 23 || i == 26 || i == 50 || i == 52 || i == 80 || i == 92 || i == 105 || i == 10 || i == 11 || i == 30 || i == 31 || i == 95 || i == 96 || i == 100 || i == 101) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 6 || i == 23 || i == 26 || i == 50 || i == 52 || i == 80 || i == 92 || i == 105 || i == 10 || i == 11 || i == 30 || i == 31 || i == 95 || i == 96 || i == 100 || i == 101) ? 2 : 3];
        switch (i) {
            case 2:
            case 6:
            case 10:
            case 11:
            case 23:
            case 26:
            case 30:
            case 31:
            case 50:
            case 52:
            case 80:
            case 92:
            case 95:
            case 96:
            case 100:
            case 101:
            case 105:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil";
                break;
            case 3:
            case 13:
            case 15:
            case 18:
            case 21:
            case 66:
            case 68:
            case 69:
            case 71:
            case 73:
            case 76:
            case 78:
                objArr[0] = "variable";
                break;
            case 4:
            case 5:
            case 7:
            case 9:
            case 12:
            case 14:
            case 16:
            case 19:
            case 22:
            case 24:
            case 27:
            case 32:
            case 41:
            case 42:
            case 43:
            case 59:
            case 60:
            case 62:
            case 63:
            case 64:
            case 70:
            case 72:
            case 74:
            case 75:
            case 77:
            case 79:
            case 81:
            case 84:
            case 86:
            case 91:
            case 93:
            case 94:
            case 97:
            case 98:
            case 99:
            case 102:
            case 106:
            case 108:
            case 109:
            case 111:
            case 112:
            case 120:
            default:
                objArr[0] = "flow";
                break;
            case 8:
                objArr[0] = FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX;
                break;
            case 17:
            case 39:
            case 51:
            case 65:
            case 67:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            case 20:
            case 44:
            case 48:
            case 49:
                objArr[0] = "enclosingCodeFragment";
                break;
            case 25:
            case 28:
            case 33:
                objArr[0] = "exitPoints";
                break;
            case 29:
            case 36:
            case 38:
            case 40:
                objArr[0] = "classesFilter";
                break;
            case 34:
            case 37:
                objArr[0] = "exitStatements";
                break;
            case 35:
                objArr[0] = "instruction";
                break;
            case 45:
                objArr[0] = PathTree.NAME_elements;
                break;
            case 46:
                objArr[0] = "tryStatement";
                break;
            case 47:
                objArr[0] = "exceptionType";
                break;
            case 53:
                objArr[0] = "ref";
                break;
            case 54:
            case 58:
                objArr[0] = "targetClassMember";
                break;
            case 55:
                objArr[0] = "array";
                break;
            case 56:
                objArr[0] = "scope";
                break;
            case 57:
                objArr[0] = "member";
                break;
            case 61:
                objArr[0] = "afterVisitor";
                break;
            case 82:
                objArr[0] = OverlayablePolicy.NAME_references;
                break;
            case 83:
            case 85:
            case 87:
                objArr[0] = "visitor";
                break;
            case 88:
                objArr[0] = "instructions";
                break;
            case 89:
                objArr[0] = "clientVisitor";
                break;
            case 90:
                objArr[0] = "target";
                break;
            case 103:
                objArr[0] = "writeVars";
                break;
            case 104:
            case 107:
                objArr[0] = "readVars";
                break;
            case 110:
            case 113:
                objArr[0] = "instructionOffsets";
                break;
            case 114:
                objArr[0] = "expression";
                break;
            case 115:
                objArr[0] = "resolved";
                break;
            case 116:
            case 118:
                objArr[0] = "throwType";
                break;
            case 117:
            case 119:
                objArr[0] = "catchType";
                break;
            case 121:
                objArr[0] = "variables";
                break;
            case 122:
                objArr[0] = "locations";
                break;
        }
        if (i == 2) {
            objArr[1] = "getSSAVariables";
        } else if (i == 6) {
            objArr[1] = "getWrittenVariables";
        } else if (i == 23) {
            objArr[1] = "getInputVariables";
        } else if (i == 26) {
            objArr[1] = "getOutputVariables";
        } else if (i == 50) {
            objArr[1] = "collectTryStatementStack";
        } else if (i == 52) {
            objArr[1] = "findCodeFragment";
        } else if (i == 80) {
            objArr[1] = "getEdges";
        } else if (i == 92) {
            objArr[1] = "getReadBeforeWriteLocals";
        } else if (i == 105) {
            objArr[1] = "getWritesBeforeReads";
        } else if (i == 10 || i == 11) {
            objArr[1] = "getUsedVariables";
        } else if (i == 30 || i == 31) {
            objArr[1] = "findExitPointsAndStatements";
        } else if (i == 95 || i == 96) {
            objArr[1] = "getReadBeforeWrite";
        } else if (i == 100 || i == 101) {
            objArr[1] = "getInitializedTwice";
        } else {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil";
        }
        switch (i) {
            case 2:
            case 6:
            case 10:
            case 11:
            case 23:
            case 26:
            case 30:
            case 31:
            case 50:
            case 52:
            case 80:
            case 92:
            case 95:
            case 96:
            case 100:
            case 101:
            case 105:
                break;
            case 3:
            case 4:
                objArr[2] = "needVariableValueAt";
                break;
            case 5:
            case 7:
            case 8:
                objArr[2] = "getWrittenVariables";
                break;
            case 9:
                objArr[2] = "getUsedVariables";
                break;
            case 12:
            case 13:
                objArr[2] = "isVariableUsed";
                break;
            case 14:
            case 15:
                objArr[2] = "findSingleReadOffset";
                break;
            case 16:
            case 17:
            case 18:
                objArr[2] = "findSingleReadOccurrence";
                break;
            case 19:
            case 20:
            case 21:
                objArr[2] = "isVariableReadInFinally";
                break;
            case 22:
                objArr[2] = "getInputVariables";
                break;
            case 24:
            case 25:
                objArr[2] = "getOutputVariables";
                break;
            case 27:
            case 28:
            case 29:
                objArr[2] = "findExitPointsAndStatements";
                break;
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
                objArr[2] = "processGoto";
                break;
            case 37:
            case 38:
                objArr[2] = "processGotoStatement";
                break;
            case 39:
            case 40:
                objArr[2] = "isElementOfClass";
                break;
            case 41:
                objArr[2] = "promoteThroughGotoChain";
                break;
            case 42:
                objArr[2] = "findStatement";
                break;
            case 43:
            case 44:
            case 45:
                objArr[2] = "hasObservableThrowExitPoints";
                break;
            case 46:
            case 47:
                objArr[2] = "isExceptionCaught";
                break;
            case 48:
                objArr[2] = "getEnclosingTryStatementHavingCatchOrFinally";
                break;
            case 49:
                objArr[2] = "collectTryStatementStack";
                break;
            case 51:
                objArr[2] = "findCodeFragment";
                break;
            case 53:
            case 54:
                objArr[2] = "checkReferenceExpressionScope";
                break;
            case 55:
            case 56:
            case 57:
            case 58:
                objArr[2] = "collectOuterLocals";
                break;
            case 59:
                objArr[2] = "returnPresent";
                break;
            case 60:
            case 61:
                objArr[2] = "processReturns";
                break;
            case 62:
                objArr[2] = "returnPresentBetween";
                break;
            case 63:
                objArr[2] = "canCompleteNormally";
                break;
            case 64:
                objArr[2] = "getUnreachableStatement";
                break;
            case 65:
            case 66:
                objArr[2] = "getEnclosingReferenceExpression";
                break;
            case 67:
            case 68:
                objArr[2] = "findReferenceTo";
                break;
            case 69:
            case 70:
                objArr[2] = "isVariableDefinitelyAssigned";
                break;
            case 71:
            case 72:
                objArr[2] = "getVariablePossiblyUnassignedOffsets";
                break;
            case 73:
            case 74:
                objArr[2] = "isVariableDefinitelyNotAssigned";
                break;
            case 75:
            case 76:
                objArr[2] = "isValueUsedWithoutVisitingStop";
                break;
            case 77:
            case 78:
                objArr[2] = "isVariableAccess";
                break;
            case 79:
                objArr[2] = "getEdges";
                break;
            case 81:
            case 82:
                objArr[2] = "getMinDefinitelyReachedOffset";
                break;
            case 83:
                objArr[2] = "findUnprocessed";
                break;
            case 84:
            case 85:
            case 86:
            case 87:
                objArr[2] = "depthFirstSearch";
                break;
            case 88:
            case 89:
                objArr[2] = "internalDepthFirstSearch";
                break;
            case 90:
                objArr[2] = "merge";
                break;
            case 91:
                objArr[2] = "getReadBeforeWriteLocals";
                break;
            case 93:
            case 94:
                objArr[2] = "getReadBeforeWrite";
                break;
            case 97:
                objArr[2] = "getCompletionReasons";
                break;
            case 98:
            case 99:
                objArr[2] = "getInitializedTwice";
                break;
            case 102:
            case 103:
            case 104:
                objArr[2] = "getWritesBeforeReads";
                break;
            case 106:
            case 107:
                objArr[2] = "readBeforeStopPoint";
                break;
            case 108:
                objArr[2] = "isInstructionReachable";
                break;
            case 109:
            case 110:
                objArr[2] = "areInstructionsReachable";
                break;
            case 111:
                objArr[2] = "hasCalls";
                break;
            case 112:
            case 113:
                objArr[2] = "areInstructionsReachableWithCalls";
                break;
            case 114:
            case 115:
                objArr[2] = "isVariableAssignedInLoop";
                break;
            case 116:
            case 117:
                objArr[2] = "isCaughtExceptionType";
                break;
            case 118:
            case 119:
                objArr[2] = "mightBeAssignableFromSubclass";
                break;
            case 120:
            case 121:
            case 122:
                objArr[2] = "areVariablesUnmodifiedAtLocations";
                break;
            default:
                objArr[2] = "getSSAVariables";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 6 && i != 23 && i != 26 && i != 50 && i != 52 && i != 80 && i != 92 && i != 105 && i != 10 && i != 11 && i != 30 && i != 31 && i != 95 && i != 96 && i != 100 && i != 101) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    private static class SSAInstructionState implements Cloneable {
        private final int myInstructionIdx;
        private final int myWriteCount;

        SSAInstructionState(int i, int i2) {
            this.myWriteCount = i;
            this.myInstructionIdx = i2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SSAInstructionState)) {
                return false;
            }
            SSAInstructionState sSAInstructionState = (SSAInstructionState) obj;
            return this.myInstructionIdx == sSAInstructionState.myInstructionIdx && Math.min(2, this.myWriteCount) == Math.min(2, sSAInstructionState.myWriteCount);
        }

        public int hashCode() {
            return (Math.min(2, this.myWriteCount) * 29) + this.myInstructionIdx;
        }

        int getWriteCount() {
            return this.myWriteCount;
        }

        int getInstructionIdx() {
            return this.myInstructionIdx;
        }
    }

    public static List<PsiVariable> getSSAVariables(ControlFlow controlFlow) {
        if (controlFlow == null) {
            $$$reportNull$$$0(0);
        }
        return getSSAVariables(controlFlow, 0, controlFlow.getSize(), false);
    }

    public static List<PsiVariable> getSSAVariables(ControlFlow controlFlow, int i, int i2, boolean z) {
        int i3 = 1;
        if (controlFlow == null) {
            $$$reportNull$$$0(1);
        }
        List<Instruction> instructions = controlFlow.getInstructions();
        int i4 = 0;
        Collection<PsiVariable> writtenVariables = getWrittenVariables(controlFlow, i, i2, false);
        ArrayList arrayList = new ArrayList(1);
        for (PsiVariable psiVariable : writtenVariables) {
            PsiManager manager = psiVariable.getManager();
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(new SSAInstructionState(i4, i));
            HashSet hashSet = new HashSet();
            while (true) {
                if (!arrayList2.isEmpty()) {
                    SSAInstructionState sSAInstructionState = (SSAInstructionState) arrayList2.remove(i4);
                    if (sSAInstructionState.getWriteCount() > i3) {
                        break;
                    }
                    if (!hashSet.contains(sSAInstructionState)) {
                        hashSet.add(sSAInstructionState);
                        int instructionIdx = sSAInstructionState.getInstructionIdx();
                        if (instructionIdx < i2) {
                            Instruction instruction = instructions.get(instructionIdx);
                            if (instruction instanceof ReturnInstruction) {
                                int[] possibleReturnOffsets = ((ReturnInstruction) instruction).getPossibleReturnOffsets();
                                int length = possibleReturnOffsets.length;
                                for (int i5 = i4; i5 < length; i5++) {
                                    arrayList2.add(new SSAInstructionState(sSAInstructionState.getWriteCount(), Math.min(possibleReturnOffsets[i5], i2)));
                                }
                            } else if (instruction instanceof GoToInstruction) {
                                arrayList2.add(new SSAInstructionState(sSAInstructionState.getWriteCount(), Math.min(((GoToInstruction) instruction).offset, i2)));
                            } else if (instruction instanceof ThrowToInstruction) {
                                arrayList2.add(new SSAInstructionState(sSAInstructionState.getWriteCount(), Math.min(((ThrowToInstruction) instruction).offset, i2)));
                            } else if (instruction instanceof ConditionalGoToInstruction) {
                                arrayList2.add(new SSAInstructionState(sSAInstructionState.getWriteCount(), Math.min(((ConditionalGoToInstruction) instruction).offset, i2)));
                                arrayList2.add(new SSAInstructionState(sSAInstructionState.getWriteCount(), instructionIdx + 1));
                            } else if (instruction instanceof ConditionalThrowToInstruction) {
                                arrayList2.add(new SSAInstructionState(sSAInstructionState.getWriteCount(), Math.min(((ConditionalThrowToInstruction) instruction).offset, i2)));
                                arrayList2.add(new SSAInstructionState(sSAInstructionState.getWriteCount(), instructionIdx + 1));
                            } else if (instruction instanceof WriteVariableInstruction) {
                                arrayList2.add(new SSAInstructionState(sSAInstructionState.getWriteCount() + (manager.areElementsEquivalent(((WriteVariableInstruction) instruction).variable, psiVariable) ? 1 : 0), instructionIdx + 1));
                            } else if (instruction instanceof ReadVariableInstruction) {
                                if (!manager.areElementsEquivalent(((ReadVariableInstruction) instruction).variable, psiVariable) || sSAInstructionState.getWriteCount() != 0) {
                                    arrayList2.add(new SSAInstructionState(sSAInstructionState.getWriteCount(), instructionIdx + 1));
                                }
                            } else {
                                arrayList2.add(new SSAInstructionState(sSAInstructionState.getWriteCount(), instructionIdx + 1));
                            }
                        } else if (z || sSAInstructionState.getWriteCount() != 0) {
                        }
                    }
                    i3 = 1;
                    i4 = 0;
                } else {
                    arrayList.add(psiVariable);
                }
                i3 = 1;
                i4 = 0;
                break;
            }
        }
        return arrayList;
    }

    public static boolean needVariableValueAt(PsiVariable psiVariable, ControlFlow controlFlow, int i) {
        if (psiVariable == null) {
            $$$reportNull$$$0(3);
        }
        if (controlFlow == null) {
            $$$reportNull$$$0(4);
        }
        InstructionClientVisitor<Boolean> instructionClientVisitor = new InstructionClientVisitor<Boolean>(psiVariable, i) { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.1
            final boolean[] neededBelow;
            final /* synthetic */ int val$offset;
            final /* synthetic */ PsiVariable val$variable;

            {
                this.val$variable = psiVariable;
                this.val$offset = i;
                this.neededBelow = new boolean[this.val$flow.getSize() + 1];
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
            public void procedureEntered(int i2, int i3) {
                while (i2 < i3) {
                    this.neededBelow[i2] = false;
                    i2++;
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitReadVariableInstruction(ReadVariableInstruction readVariableInstruction, int i2, int i3) {
                if (i3 > this.val$flow.getSize()) {
                    i3 = this.val$flow.getSize();
                }
                boolean z = this.neededBelow[i3];
                if (readVariableInstruction.variable.equals(this.val$variable)) {
                    z = true;
                }
                boolean[] zArr = this.neededBelow;
                zArr[i2] = z | zArr[i2];
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitWriteVariableInstruction(WriteVariableInstruction writeVariableInstruction, int i2, int i3) {
                if (i3 > this.val$flow.getSize()) {
                    i3 = this.val$flow.getSize();
                }
                boolean z = this.neededBelow[i3];
                if (writeVariableInstruction.variable.equals(this.val$variable)) {
                    z = false;
                }
                this.neededBelow[i2] = z;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitInstruction(Instruction instruction, int i2, int i3) {
                if (i3 > this.val$flow.getSize()) {
                    i3 = this.val$flow.getSize();
                }
                boolean[] zArr = this.neededBelow;
                zArr[i2] = zArr[i3] | zArr[i2];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
            public Boolean getResult() {
                return Boolean.valueOf(this.neededBelow[this.val$offset]);
            }
        };
        depthFirstSearch(controlFlow, instructionClientVisitor, i, controlFlow.getSize());
        return instructionClientVisitor.getResult().booleanValue();
    }

    public static Collection<PsiVariable> getWrittenVariables(ControlFlow controlFlow, int i, int i2, boolean z) {
        if (controlFlow == null) {
            $$$reportNull$$$0(5);
        }
        HashSet hashSet = new HashSet();
        getWrittenVariables(controlFlow, i, i2, z, hashSet);
        return hashSet;
    }

    public static void getWrittenVariables(ControlFlow controlFlow, int i, int i2, boolean z, Collection<? super PsiVariable> collection) {
        if (controlFlow == null) {
            $$$reportNull$$$0(7);
        }
        if (collection == null) {
            $$$reportNull$$$0(8);
        }
        List<Instruction> instructions = controlFlow.getInstructions();
        while (i < i2) {
            Instruction instruction = instructions.get(i);
            if ((instruction instanceof WriteVariableInstruction) && (!z || isInstructionReachable(controlFlow, i2, i))) {
                collection.add(((WriteVariableInstruction) instruction).variable);
            }
            i++;
        }
    }

    public static List<PsiVariable> getUsedVariables(ControlFlow controlFlow, int i, int i2) {
        if (controlFlow == null) {
            $$$reportNull$$$0(9);
        }
        ArrayList arrayList = new ArrayList();
        if (i < 0) {
            return arrayList;
        }
        List<Instruction> instructions = controlFlow.getInstructions();
        while (i < i2) {
            Instruction instruction = instructions.get(i);
            if (instruction instanceof ReadVariableInstruction) {
                PsiVariable psiVariable = ((ReadVariableInstruction) instruction).variable;
                if (!arrayList.contains(psiVariable)) {
                    arrayList.add(psiVariable);
                }
            } else if (instruction instanceof WriteVariableInstruction) {
                PsiVariable psiVariable2 = ((WriteVariableInstruction) instruction).variable;
                if (!arrayList.contains(psiVariable2)) {
                    arrayList.add(psiVariable2);
                }
            }
            i++;
        }
        return arrayList;
    }

    public static boolean isVariableUsed(ControlFlow controlFlow, int i, int i2, PsiVariable psiVariable) {
        if (controlFlow == null) {
            $$$reportNull$$$0(12);
        }
        if (psiVariable == null) {
            $$$reportNull$$$0(13);
        }
        List<Instruction> instructions = controlFlow.getInstructions();
        Logger logger = LOG;
        logger.assertTrue(i >= 0, "flow start");
        logger.assertTrue(i2 <= instructions.size(), "flow end");
        PsiManager manager = psiVariable.getManager();
        while (i < i2) {
            Instruction instruction = instructions.get(i);
            if (instruction instanceof ReadVariableInstruction) {
                if (manager.areElementsEquivalent(((ReadVariableInstruction) instruction).variable, psiVariable)) {
                    return true;
                }
            } else if ((instruction instanceof WriteVariableInstruction) && manager.areElementsEquivalent(((WriteVariableInstruction) instruction).variable, psiVariable)) {
                return true;
            }
            i++;
        }
        return false;
    }

    private static int findSingleReadOffset(ControlFlow controlFlow, int i, int i2, PsiVariable psiVariable) {
        if (controlFlow == null) {
            $$$reportNull$$$0(14);
        }
        if (psiVariable == null) {
            $$$reportNull$$$0(15);
        }
        List<Instruction> instructions = controlFlow.getInstructions();
        if (i < 0 || i2 < 0 || i2 > instructions.size()) {
            return -1;
        }
        PsiManager manager = psiVariable.getManager();
        int i3 = -1;
        while (i < i2) {
            Instruction instruction = instructions.get(i);
            if (instruction instanceof ReadVariableInstruction) {
                if (!manager.areElementsEquivalent(((ReadVariableInstruction) instruction).variable, psiVariable)) {
                    continue;
                } else {
                    if (i3 >= 0) {
                        return -1;
                    }
                    i3 = i;
                }
            } else if ((instruction instanceof WriteVariableInstruction) && manager.areElementsEquivalent(((WriteVariableInstruction) instruction).variable, psiVariable)) {
                return -1;
            }
            i++;
        }
        return i3;
    }

    public static PsiReferenceExpression findSingleReadOccurrence(ControlFlow controlFlow, final PsiElement psiElement, PsiVariable psiVariable) {
        if (controlFlow == null) {
            $$$reportNull$$$0(16);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(17);
        }
        if (psiVariable == null) {
            $$$reportNull$$$0(18);
        }
        int iFindSingleReadOffset = findSingleReadOffset(controlFlow, controlFlow.getStartOffset(psiElement), controlFlow.getEndOffset(psiElement), psiVariable);
        if (iFindSingleReadOffset < 0) {
            return null;
        }
        PsiElement psiElementFindFirstParent = PsiTreeUtil.findFirstParent(controlFlow.getElement(iFindSingleReadOffset), false, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil$$ExternalSyntheticLambda2
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return ControlFlowUtil.lambda$findSingleReadOccurrence$0(psiElement, (PsiElement) obj);
            }
        });
        if (psiElementFindFirstParent instanceof PsiReferenceExpression) {
            return (PsiReferenceExpression) psiElementFindFirstParent;
        }
        return null;
    }

    static /* synthetic */ boolean lambda$findSingleReadOccurrence$0(PsiElement psiElement, PsiElement psiElement2) {
        return psiElement2 == psiElement || (psiElement2 instanceof PsiReferenceExpression);
    }

    public static boolean isVariableReadInFinally(ControlFlow controlFlow, PsiElement psiElement, PsiElement psiElement2, PsiVariable psiVariable) {
        PsiCodeBlock finallyBlock;
        if (controlFlow == null) {
            $$$reportNull$$$0(19);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(20);
        }
        if (psiVariable == null) {
            $$$reportNull$$$0(21);
        }
        PsiManager manager = psiVariable.getManager();
        while (true) {
            if (psiElement == null || psiElement == psiElement2) {
                break;
            }
            if (psiElement instanceof PsiCodeBlock) {
                PsiElement myParent = psiElement.getMyParent();
                if (myParent instanceof PsiTryStatement) {
                    PsiTryStatement psiTryStatement = (PsiTryStatement) myParent;
                    if (psiTryStatement.getTryBlock() == psiElement && (finallyBlock = psiTryStatement.getFinallyBlock()) != null) {
                        List<Instruction> instructions = controlFlow.getInstructions();
                        int startOffset = controlFlow.getStartOffset(finallyBlock);
                        int endOffset = controlFlow.getEndOffset(finallyBlock);
                        Logger logger = LOG;
                        logger.assertTrue(startOffset >= 0, "flow start");
                        logger.assertTrue(endOffset <= instructions.size(), "flow end");
                        while (startOffset < endOffset) {
                            Instruction instruction = instructions.get(startOffset);
                            if ((instruction instanceof ReadVariableInstruction) && manager.areElementsEquivalent(((ReadVariableInstruction) instruction).variable, psiVariable)) {
                                return true;
                            }
                            startOffset++;
                        }
                    }
                } else {
                    continue;
                }
            }
            psiElement = psiElement.getMyParent();
        }
        return false;
    }

    public static List<PsiVariable> getInputVariables(ControlFlow controlFlow, int i, int i2) {
        if (controlFlow == null) {
            $$$reportNull$$$0(22);
        }
        List<PsiVariable> usedVariables = getUsedVariables(controlFlow, i, i2);
        ArrayList arrayList = new ArrayList(usedVariables.size());
        for (PsiVariable psiVariable : usedVariables) {
            if (needVariableValueAt(psiVariable, controlFlow, i)) {
                arrayList.add(psiVariable);
            }
        }
        return arrayList;
    }

    public static PsiVariable[] getOutputVariables(ControlFlow controlFlow, int i, int i2, int[] iArr) {
        if (controlFlow == null) {
            $$$reportNull$$$0(24);
        }
        if (iArr == null) {
            $$$reportNull$$$0(25);
        }
        Collection<PsiVariable> writtenVariables = getWrittenVariables(controlFlow, i, i2, false);
        ArrayList arrayList = new ArrayList();
        for (PsiVariable psiVariable : writtenVariables) {
            for (int i3 : iArr) {
                if (needVariableValueAt(psiVariable, controlFlow, i3)) {
                    arrayList.add(psiVariable);
                }
            }
        }
        PsiVariable[] psiVariableArr = (PsiVariable[]) arrayList.toArray(new PsiVariable[0]);
        Logger logger = LOG;
        if (logger.isDebugEnabled()) {
            logger.debug("output variables:");
            for (PsiVariable psiVariable2 : psiVariableArr) {
                LOG.debug("  " + psiVariable2);
            }
        }
        if (psiVariableArr == null) {
            $$$reportNull$$$0(26);
        }
        return psiVariableArr;
    }

    @SafeVarargs
    public static Collection<PsiStatement> findExitPointsAndStatements(final ControlFlow controlFlow, final int i, final int i2, final IntList intList, final Class<? extends PsiStatement>... clsArr) {
        if (controlFlow == null) {
            $$$reportNull$$$0(27);
        }
        if (intList == null) {
            $$$reportNull$$$0(28);
        }
        if (clsArr == null) {
            $$$reportNull$$$0(29);
        }
        if (i2 == i) {
            intList.add(i2);
            List listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(30);
            }
            return listEmptyList;
        }
        final HashSet hashSet = new HashSet();
        depthFirstSearch(controlFlow, new InstructionClientVisitor<Void>() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.2
            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
            public Void getResult() {
                return null;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitCallInstruction(CallInstruction callInstruction, int i3, int i4) {
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitReturnInstruction(ReturnInstruction returnInstruction, int i3, int i4) {
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitThrowToInstruction(ThrowToInstruction throwToInstruction, int i3, int i4) {
                ControlFlowUtil.processGotoStatement(hashSet, ControlFlowUtil.findStatement(controlFlow, i3), clsArr);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitBranchingInstruction(BranchingInstruction branchingInstruction, int i3, int i4) {
                ControlFlow controlFlow2 = controlFlow;
                ControlFlowUtil.processGoto(controlFlow2, i, i2, intList, hashSet, branchingInstruction, ControlFlowUtil.findStatement(controlFlow2, i3), clsArr);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitConditionalThrowToInstruction(ConditionalThrowToInstruction conditionalThrowToInstruction, int i3, int i4) {
                visitInstruction(conditionalThrowToInstruction, i3, i4);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitInstruction(Instruction instruction, int i3, int i4) {
                int i5 = i2;
                if (i3 >= i5 - 1) {
                    int iPromoteThroughGotoChain = ControlFlowUtil.promoteThroughGotoChain(controlFlow, i5);
                    if (intList.contains(iPromoteThroughGotoChain)) {
                        return;
                    }
                    intList.add(iPromoteThroughGotoChain);
                }
            }
        }, i, i2);
        return hashSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SafeVarargs
    public static void processGoto(ControlFlow controlFlow, int i, int i2, IntList intList, Collection<? super PsiStatement> collection, BranchingInstruction branchingInstruction, PsiStatement psiStatement, Class<? extends PsiStatement>... clsArr) {
        if (controlFlow == null) {
            $$$reportNull$$$0(32);
        }
        if (intList == null) {
            $$$reportNull$$$0(33);
        }
        if (collection == null) {
            $$$reportNull$$$0(34);
        }
        if (branchingInstruction == null) {
            $$$reportNull$$$0(35);
        }
        if (clsArr == null) {
            $$$reportNull$$$0(36);
        }
        if (psiStatement == null) {
            return;
        }
        int i3 = branchingInstruction.offset;
        if (i > i3 || i3 >= i2 || isElementOfClass(psiStatement, clsArr)) {
            int iPromoteThroughGotoChain = promoteThroughGotoChain(controlFlow, i3);
            if (iPromoteThroughGotoChain > 0 && ((iPromoteThroughGotoChain >= i2 || iPromoteThroughGotoChain < i) && !intList.contains(iPromoteThroughGotoChain))) {
                intList.add(iPromoteThroughGotoChain);
            }
            if (iPromoteThroughGotoChain >= i2 || iPromoteThroughGotoChain < i) {
                processGotoStatement(collection, psiStatement, clsArr);
                return;
            }
            boolean z = (branchingInstruction instanceof GoToInstruction) && ((GoToInstruction) branchingInstruction).isReturn;
            Instruction instruction = controlFlow.getInstructions().get(iPromoteThroughGotoChain);
            if (z || ((instruction instanceof GoToInstruction) && ((GoToInstruction) instruction).isReturn)) {
                processGotoStatement(collection, psiStatement, clsArr);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SafeVarargs
    public static void processGotoStatement(Collection<? super PsiStatement> collection, PsiStatement psiStatement, Class<? extends PsiStatement>... clsArr) {
        if (collection == null) {
            $$$reportNull$$$0(37);
        }
        if (clsArr == null) {
            $$$reportNull$$$0(38);
        }
        if (psiStatement == null || !isElementOfClass(psiStatement, clsArr)) {
            return;
        }
        collection.add(psiStatement);
    }

    @SafeVarargs
    private static boolean isElementOfClass(PsiElement psiElement, Class<? extends PsiStatement>... clsArr) {
        if (psiElement == null) {
            $$$reportNull$$$0(39);
        }
        if (clsArr == null) {
            $$$reportNull$$$0(40);
        }
        for (Class<? extends PsiStatement> cls : clsArr) {
            if (ReflectionUtil.isAssignable(cls, psiElement.getClass())) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int promoteThroughGotoChain(ControlFlow controlFlow, int i) {
        if (controlFlow == null) {
            $$$reportNull$$$0(41);
        }
        List<Instruction> instructions = controlFlow.getInstructions();
        while (i < instructions.size()) {
            Instruction instruction = instructions.get(i);
            if (!(instruction instanceof GoToInstruction) || ((GoToInstruction) instruction).isReturn) {
                break;
            }
            i = ((BranchingInstruction) instruction).offset;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PsiStatement findStatement(ControlFlow controlFlow, int i) {
        if (controlFlow == null) {
            $$$reportNull$$$0(42);
        }
        return (PsiStatement) PsiTreeUtil.getParentOfType(controlFlow.getElement(i), PsiStatement.class, false);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil$1Worker, reason: invalid class name */
    class C1Worker {
        final /* synthetic */ ControlFlow val$flow;
        final /* synthetic */ int val$flowEnd;
        final /* synthetic */ int val$flowStart;
        final /* synthetic */ List val$instructions;

        /* JADX WARN: Code duplicated, block: B:35:0x005d  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2 || i == 5 || i == 6 || i == 8 || i == 9 || i == 11) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[(i == 1 || i == 2 || i == 5 || i == 6 || i == 8 || i == 9 || i == 11) ? 3 : 2];
            if (i == 1) {
                objArr[0] = "writeOffsets";
            } else if (i == 2) {
                objArr[0] = "tryBlock";
            } else if (i == 5) {
                objArr[0] = "writeOffsets";
            } else if (i == 6) {
                objArr[0] = "visibleReadOffsets";
            } else if (i == 8) {
                objArr[0] = "tryStatements";
            } else if (i == 9) {
                objArr[0] = "thrownExceptions";
            } else if (i != 11) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$1Worker";
            } else {
                objArr[0] = "fromOffsets";
            }
            switch (i) {
                case 1:
                case 2:
                case 5:
                case 6:
                case 8:
                case 9:
                case 11:
                    objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$1Worker";
                    break;
                case 3:
                case 4:
                    objArr[1] = "getVisibleReadsOffsets";
                    break;
                case 7:
                    objArr[1] = "getReachableAfterWrite";
                    break;
                case 10:
                    objArr[1] = "getCatchOrFinallyOffsets";
                    break;
                case 12:
                    objArr[1] = "getReachableInstructionsCalculator";
                    break;
                default:
                    objArr[1] = "getWritesOffsets";
                    break;
            }
            if (i == 1 || i == 2) {
                objArr[2] = "getVisibleReadsOffsets";
            } else if (i == 5 || i == 6) {
                objArr[2] = "getReachableAfterWrite";
            } else if (i == 8 || i == 9) {
                objArr[2] = "getCatchOrFinallyOffsets";
            } else if (i == 11) {
                objArr[2] = "isAnyReadOffsetReachableFrom";
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2 && i != 5 && i != 6 && i != 8 && i != 9 && i != 11) {
                throw new IllegalStateException(str2);
            }
            throw new IllegalArgumentException(str2);
        }

        C1Worker(int i, int i2, List list, ControlFlow controlFlow) {
            this.val$flowStart = i;
            this.val$flowEnd = i2;
            this.val$instructions = list;
            this.val$flow = controlFlow;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Map<PsiVariable, IntList> getWritesOffsets() {
            HashMap map = new HashMap();
            for (int i = this.val$flowStart; i < this.val$flowEnd; i++) {
                Instruction instruction = (Instruction) this.val$instructions.get(i);
                if (instruction instanceof WriteVariableInstruction) {
                    PsiVariable psiVariable = ((WriteVariableInstruction) instruction).variable;
                    if ((psiVariable instanceof PsiLocalVariable) || (psiVariable instanceof PsiParameter)) {
                        IntCollection intArrayList = (IntList) map.get(psiVariable);
                        if (intArrayList == null) {
                            intArrayList = new IntArrayList();
                            map.put(psiVariable, intArrayList);
                        }
                        intArrayList.add(i);
                    }
                }
            }
            ControlFlowUtil.LOG.debug("writeOffsets:", map);
            return map;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Map<PsiVariable, IntList> getVisibleReadsOffsets(Map<PsiVariable, IntList> map, PsiCodeBlock psiCodeBlock) {
            IntList intList;
            if (map == null) {
                $$$reportNull$$$0(1);
            }
            if (psiCodeBlock == null) {
                $$$reportNull$$$0(2);
            }
            HashMap map2 = new HashMap();
            for (PsiVariable psiVariable : map.keySet()) {
                if (!PsiTreeUtil.isAncestor(psiCodeBlock, psiVariable, true)) {
                    map2.put(psiVariable, new IntArrayList());
                }
            }
            if (map2.isEmpty()) {
                return map2;
            }
            for (int i = 0; i < this.val$instructions.size(); i++) {
                Instruction instruction = (Instruction) this.val$instructions.get(i);
                if ((instruction instanceof ReadVariableInstruction) && (intList = (IntList) map2.get(((ReadVariableInstruction) instruction).variable)) != null) {
                    intList.add(i);
                }
            }
            ControlFlowUtil.LOG.debug("visibleReadOffsets:", map2);
            return map2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Map<PsiVariable, Set<PsiElement>> getReachableAfterWrite(Map<PsiVariable, IntList> map, Map<PsiVariable, IntList> map2) {
            if (map == null) {
                $$$reportNull$$$0(5);
            }
            if (map2 == null) {
                $$$reportNull$$$0(6);
            }
            HashMap map3 = new HashMap();
            for (PsiVariable psiVariable : map2.keySet()) {
                Function<Integer, BitSet> reachableInstructionsCalculator = getReachableInstructionsCalculator();
                BitSet bitSet = new BitSet(this.val$flowEnd);
                int[] intArray = map.get(psiVariable).toIntArray();
                int length = intArray.length;
                for (int i = 0; i < length; i++) {
                    int i2 = intArray[i];
                    ControlFlowUtil.LOG.assertTrue(i2 >= this.val$flowStart, "writeOffset");
                    BitSet bitSetFun = reachableInstructionsCalculator.fun(Integer.valueOf(i2));
                    bitSetFun.set(i2, false);
                    int i3 = i2 + 1;
                    if (i3 < this.val$flow.getSize()) {
                        PsiElement element = this.val$flow.getElement(i3);
                        if ((element instanceof PsiExpressionStatement) && this.val$flow.getElement(i2) == ((PsiExpressionStatement) element).getExpression()) {
                            bitSetFun.set(i3, false);
                        }
                    }
                    bitSet.or(bitSetFun);
                }
                Set<PsiElement> set = (Set) map3.getOrDefault(psiVariable, new HashSet());
                for (int i4 = this.val$flowStart; i4 < this.val$flowEnd; i4++) {
                    if (bitSet.get(i4)) {
                        set.add(this.val$flow.getElement(i4));
                    }
                }
                ArrayList arrayList = new ArrayList();
                for (PsiElement psiElement : set) {
                    if (set.contains(psiElement.getMyParent())) {
                        arrayList.add(psiElement);
                    }
                }
                set.removeAll(arrayList);
                if (set.isEmpty()) {
                    map3.remove(psiVariable);
                } else {
                    map3.put(psiVariable, set);
                }
            }
            ControlFlowUtil.LOG.debug("afterWrite:", map3);
            return map3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public IntList getCatchOrFinallyOffsets(List<? extends PsiTryStatement> list, List<? extends PsiClassType> list2) {
            int startOffset;
            int startOffset2;
            if (list == null) {
                $$$reportNull$$$0(8);
            }
            if (list2 == null) {
                $$$reportNull$$$0(9);
            }
            IntArrayList intArrayList = new IntArrayList();
            for (PsiTryStatement psiTryStatement : list) {
                PsiCodeBlock finallyBlock = psiTryStatement.getFinallyBlock();
                if (finallyBlock != null && (startOffset2 = this.val$flow.getStartOffset(finallyBlock)) >= 0) {
                    intArrayList.add(startOffset2 - 2);
                }
                for (PsiCatchSection psiCatchSection : psiTryStatement.getCatchSections()) {
                    PsiCodeBlock catchBlock = psiCatchSection.getCatchBlock();
                    PsiParameter parameter = psiCatchSection.getParameter();
                    if (catchBlock != null && parameter != null) {
                        Iterator<? extends PsiClassType> it = list2.iterator();
                        while (it.hasNext()) {
                            if (ControlFlowUtil.isCaughtExceptionType(it.next(), parameter.getType()) && (startOffset = this.val$flow.getStartOffset(catchBlock)) >= 0) {
                                intArrayList.add(startOffset - 1);
                            }
                        }
                    }
                }
            }
            return intArrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isAnyReadOffsetReachableFrom(IntList intList, IntList intList2) {
            if (intList2 == null) {
                $$$reportNull$$$0(11);
            }
            if (intList != null && !intList.isEmpty()) {
                int[] intArray = intList.toIntArray();
                for (int i = 0; i < intList2.size(); i++) {
                    int i2 = intList2.getInt(i);
                    if (ControlFlowUtil.areInstructionsReachable(this.val$flow, intArray, i2)) {
                        ControlFlowUtil.LOG.debug("reachableFromOffset:", Integer.valueOf(i2));
                        return true;
                    }
                }
            }
            return false;
        }

        private Function<Integer, BitSet> getReachableInstructionsCalculator() {
            final ControlFlowGraph controlFlowGraph = new ControlFlowGraph(this.val$flow.getSize()) { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.1Worker.1
                @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.ControlFlowGraph
                void addArc(int i, int i2) {
                    int iPromoteThroughGotoChain = ControlFlowUtil.promoteThroughGotoChain(C1Worker.this.val$flow, i2);
                    if (iPromoteThroughGotoChain < C1Worker.this.val$flowStart || iPromoteThroughGotoChain >= C1Worker.this.val$flowEnd) {
                        return;
                    }
                    super.addArc(i, iPromoteThroughGotoChain);
                }
            };
            controlFlowGraph.buildFrom(this.val$flow);
            final int i = this.val$flowEnd;
            return new Function() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil$1Worker$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.util.Function
                public final Object fun(Object obj) {
                    return ControlFlowUtil.C1Worker.lambda$getReachableInstructionsCalculator$0(i, controlFlowGraph, (Integer) obj);
                }
            };
        }

        static /* synthetic */ BitSet lambda$getReachableInstructionsCalculator$0(int i, ControlFlowGraph controlFlowGraph, Integer num) {
            BitSet bitSet = new BitSet(i);
            controlFlowGraph.depthFirstSearch(num.intValue(), bitSet);
            return bitSet;
        }
    }

    public static boolean hasObservableThrowExitPoints(ControlFlow controlFlow, int i, int i2, PsiElement[] psiElementArr, PsiElement psiElement) {
        PsiCodeBlock tryBlock;
        if (controlFlow == null) {
            $$$reportNull$$$0(43);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(44);
        }
        if (psiElementArr == null) {
            $$$reportNull$$$0(45);
        }
        C1Worker c1Worker = new C1Worker(i, i2, controlFlow.getInstructions(), controlFlow);
        Map writesOffsets = c1Worker.getWritesOffsets();
        if (writesOffsets.isEmpty()) {
            return false;
        }
        List<PsiTryStatement> listCollectTryStatementStack = collectTryStatementStack(psiElementArr.length != 1 ? PsiTreeUtil.findCommonParent(psiElementArr) : psiElementArr[0].getMyParent(), psiElement);
        if (listCollectTryStatementStack.isEmpty() || (tryBlock = listCollectTryStatementStack.get(0).getTryBlock()) == null) {
            return false;
        }
        Map visibleReadsOffsets = c1Worker.getVisibleReadsOffsets(writesOffsets, tryBlock);
        if (visibleReadsOffsets.isEmpty()) {
            return false;
        }
        Map reachableAfterWrite = c1Worker.getReachableAfterWrite(writesOffsets, visibleReadsOffsets);
        if (reachableAfterWrite.isEmpty()) {
            return false;
        }
        final PsiClassType javaLangRuntimeException = PsiType.getJavaLangRuntimeException(tryBlock.getManager(), tryBlock.getResolveScope());
        boolean zExists = ContainerUtil.exists(listCollectTryStatementStack, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                return ControlFlowUtil.isExceptionCaught((PsiTryStatement) obj, javaLangRuntimeException);
            }
        });
        for (Map.Entry entry : reachableAfterWrite.entrySet()) {
            PsiVariable psiVariable = (PsiVariable) entry.getKey();
            List<PsiClassType> thrownExceptions = ExceptionUtil.getThrownExceptions((PsiElement[]) ((Set) entry.getValue()).toArray(PsiElement.EMPTY_ARRAY));
            if (zExists) {
                thrownExceptions.add(javaLangRuntimeException);
            }
            if (!thrownExceptions.isEmpty()) {
                if (c1Worker.isAnyReadOffsetReachableFrom((IntList) visibleReadsOffsets.get(psiVariable), c1Worker.getCatchOrFinallyOffsets(listCollectTryStatementStack, thrownExceptions))) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isExceptionCaught(PsiTryStatement psiTryStatement, final PsiClassType psiClassType) {
        if (psiTryStatement == null) {
            $$$reportNull$$$0(46);
        }
        if (psiClassType == null) {
            $$$reportNull$$$0(47);
        }
        return ContainerUtil.exists(psiTryStatement.getCatchBlockParameters(), new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
            public final boolean value(Object obj) {
                PsiClassType psiClassType2 = psiClassType;
                return psiClassType2.isAssignableFrom(psiClassType2);
            }
        });
    }

    private static PsiTryStatement getEnclosingTryStatementHavingCatchOrFinally(PsiElement psiElement, PsiElement psiElement2) {
        if (psiElement2 == null) {
            $$$reportNull$$$0(48);
        }
        while (psiElement != null && psiElement != psiElement2) {
            if (psiElement instanceof PsiCodeBlock) {
                PsiElement myParent = psiElement.getMyParent();
                if (myParent instanceof PsiTryStatement) {
                    PsiTryStatement psiTryStatement = (PsiTryStatement) myParent;
                    if (psiTryStatement.getTryBlock() == psiElement && (psiTryStatement.getFinallyBlock() != null || psiTryStatement.getCatchBlocks().length != 0)) {
                        return psiTryStatement;
                    }
                } else {
                    continue;
                }
            }
            psiElement = psiElement.getMyParent();
        }
        return null;
    }

    private static List<PsiTryStatement> collectTryStatementStack(PsiElement psiElement, PsiElement psiElement2) {
        if (psiElement2 == null) {
            $$$reportNull$$$0(49);
        }
        ArrayList arrayList = new ArrayList();
        PsiTryStatement enclosingTryStatementHavingCatchOrFinally = getEnclosingTryStatementHavingCatchOrFinally(psiElement, psiElement2);
        while (enclosingTryStatementHavingCatchOrFinally != null) {
            arrayList.add(enclosingTryStatementHavingCatchOrFinally);
            enclosingTryStatementHavingCatchOrFinally = getEnclosingTryStatementHavingCatchOrFinally(enclosingTryStatementHavingCatchOrFinally, psiElement2);
        }
        return arrayList;
    }

    public static PsiElement findCodeFragment(PsiElement psiElement) {
        PsiElement psiElement2;
        if (psiElement == null) {
            $$$reportNull$$$0(51);
        }
        PsiElement myParent = psiElement.getMyParent();
        while (true) {
            PsiElement psiElement3 = myParent;
            psiElement2 = psiElement;
            psiElement = psiElement3;
            if (psiElement == null || (psiElement instanceof PsiDirectory) || (psiElement instanceof PsiMethod) || (psiElement instanceof PsiField) || (psiElement instanceof PsiClassInitializer) || (psiElement instanceof DummyHolder) || (psiElement instanceof PsiLambdaExpression)) {
                break;
            }
            myParent = psiElement.getMyParent();
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(52);
        }
        return psiElement2;
    }

    private static boolean checkReferenceExpressionScope(PsiReferenceExpression psiReferenceExpression, PsiElement psiElement) {
        if (psiReferenceExpression == null) {
            $$$reportNull$$$0(53);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(54);
        }
        JavaResolveResult javaResolveResultAdvancedResolve = psiReferenceExpression.advancedResolve(false);
        PsiElement element = javaResolveResultAdvancedResolve.getElement();
        if (element != null) {
            PsiElement myParent = element.getMyParent();
            if ((myParent == null ? null : PsiTreeUtil.findCommonParent(myParent, psiElement)) == null) {
                myParent = javaResolveResultAdvancedResolve.getCurrentFileResolveScope();
            }
            if (myParent instanceof PsiClass) {
                PsiClass psiClass = (PsiClass) myParent;
                if (PsiTreeUtil.isAncestor(psiElement, psiClass, false)) {
                    return false;
                }
                for (PsiClass psiClassMo6092getContainingClass = (PsiClass) PsiTreeUtil.getParentOfType(psiReferenceExpression, PsiClass.class); psiClassMo6092getContainingClass != null; psiClassMo6092getContainingClass = psiClassMo6092getContainingClass.mo6092getContainingClass()) {
                    if (psiClassMo6092getContainingClass.isInheritor(psiClass, true) && PsiTreeUtil.isAncestor(psiElement, psiClassMo6092getContainingClass, false)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean collectOuterLocals(List<? super PsiVariable> list, PsiElement psiElement, PsiElement psiElement2, PsiElement psiElement3) {
        if (list == null) {
            $$$reportNull$$$0(55);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(56);
        }
        if (psiElement2 == null) {
            $$$reportNull$$$0(57);
        }
        if (psiElement3 == null) {
            $$$reportNull$$$0(58);
        }
        if (psiElement instanceof PsiMethodCallExpression) {
            if (!checkReferenceExpressionScope(((PsiMethodCallExpression) psiElement).getMethodExpression(), psiElement3)) {
                return false;
            }
        } else if ((psiElement instanceof PsiReferenceExpression) && !checkReferenceExpressionScope((PsiReferenceExpression) psiElement, psiElement3)) {
            return false;
        }
        if (psiElement instanceof PsiJavaCodeReferenceElement) {
            JavaResolveResult javaResolveResultAdvancedResolve = ((PsiJavaCodeReferenceElement) psiElement).advancedResolve(false);
            PsiElement element = javaResolveResultAdvancedResolve.getElement();
            if (element != null) {
                PsiElement myParent = element.getMyParent();
                PsiElement psiElementFindCommonParent = myParent != null ? PsiTreeUtil.findCommonParent(myParent, psiElement2) : null;
                if (psiElementFindCommonParent == null) {
                    psiElementFindCommonParent = javaResolveResultAdvancedResolve.getCurrentFileResolveScope();
                }
                if (psiElementFindCommonParent != null && !psiElement2.equals(psiElementFindCommonParent) && psiElement3.equals(PsiTreeUtil.findCommonParent(psiElementFindCommonParent, psiElement3))) {
                    if (!(element instanceof PsiVariable)) {
                        return false;
                    }
                    if ((psiElement instanceof PsiReferenceExpression) && PsiUtil.isAccessedForWriting((PsiReferenceExpression) psiElement)) {
                        return false;
                    }
                    PsiVariable psiVariable = (PsiVariable) element;
                    if (!list.contains(psiVariable)) {
                        list.add(psiVariable);
                    }
                }
            }
        } else if (psiElement instanceof PsiThisExpression) {
            if (((PsiThisExpression) psiElement).getQualifier() == null) {
                return false;
            }
        } else if ((psiElement instanceof PsiSuperExpression) && ((PsiSuperExpression) psiElement).getQualifier() == null) {
            return false;
        }
        for (PsiElement firstChild = psiElement.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (!collectOuterLocals(list, firstChild, psiElement2, psiElement3)) {
                return false;
            }
        }
        return true;
    }

    public static boolean returnPresent(ControlFlow controlFlow) {
        if (controlFlow == null) {
            $$$reportNull$$$0(59);
        }
        ReturnPresentClientVisitor returnPresentClientVisitor = new ReturnPresentClientVisitor(controlFlow);
        depthFirstSearch(controlFlow, returnPresentClientVisitor);
        return returnPresentClientVisitor.getResult().booleanValue();
    }

    public static boolean processReturns(ControlFlow controlFlow, ReturnStatementsVisitor returnStatementsVisitor) throws IncorrectOperationException {
        if (controlFlow == null) {
            $$$reportNull$$$0(60);
        }
        if (returnStatementsVisitor == null) {
            $$$reportNull$$$0(61);
        }
        ConvertReturnClientVisitor convertReturnClientVisitor = new ConvertReturnClientVisitor(controlFlow, returnStatementsVisitor);
        depthFirstSearch(controlFlow, convertReturnClientVisitor);
        convertReturnClientVisitor.afterProcessing();
        return convertReturnClientVisitor.getResult().booleanValue();
    }

    private static class ConvertReturnClientVisitor extends ReturnPresentClientVisitor {
        private final List<PsiReturnStatement> myAffectedReturns;
        private final ReturnStatementsVisitor myVisitor;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i != 1) {
                objArr[0] = "flow";
            } else {
                objArr[0] = "visitor";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$ConvertReturnClientVisitor";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        ConvertReturnClientVisitor(ControlFlow controlFlow, ReturnStatementsVisitor returnStatementsVisitor) {
            super(controlFlow);
            if (controlFlow == null) {
                $$$reportNull$$$0(0);
            }
            if (returnStatementsVisitor == null) {
                $$$reportNull$$$0(1);
            }
            this.myAffectedReturns = new ArrayList();
            this.myVisitor = returnStatementsVisitor;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.ReturnPresentClientVisitor, org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
        public void visitGoToInstruction(GoToInstruction goToInstruction, int i, int i2) {
            super.visitGoToInstruction(goToInstruction, i, i2);
            if (goToInstruction.isReturn) {
                PsiElement element = this.myFlow.getElement(i);
                if (element instanceof PsiReturnStatement) {
                    this.myAffectedReturns.add((PsiReturnStatement) element);
                }
            }
        }

        void afterProcessing() throws IncorrectOperationException {
            this.myVisitor.visit(this.myAffectedReturns);
        }
    }

    private static class ReturnPresentClientVisitor extends InstructionClientVisitor<Boolean> {
        private final boolean[] isNormalCompletion;
        protected final ControlFlow myFlow;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 1 ? 3 : 2];
            if (i != 1) {
                objArr[0] = "flow";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$ReturnPresentClientVisitor";
            }
            if (i != 1) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$ReturnPresentClientVisitor";
            } else {
                objArr[1] = "getResult";
            }
            if (i != 1) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 1) {
                throw new IllegalStateException(str2);
            }
        }

        ReturnPresentClientVisitor(ControlFlow controlFlow) {
            if (controlFlow == null) {
                $$$reportNull$$$0(0);
            }
            this.myFlow = controlFlow;
            boolean[] zArr = new boolean[controlFlow.getSize() + 1];
            this.isNormalCompletion = zArr;
            zArr[controlFlow.getSize()] = true;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
        public void visitConditionalThrowToInstruction(ConditionalThrowToInstruction conditionalThrowToInstruction, int i, int i2) {
            if (i2 > this.myFlow.getSize()) {
                i2 = this.myFlow.getSize();
            }
            boolean z = false;
            if (conditionalThrowToInstruction.offset != i2 || i2 == i + 1 ? isLeaf(i2) || this.isNormalCompletion[i2] : !isLeaf(i2) && this.isNormalCompletion[i2]) {
                z = true;
            }
            boolean[] zArr = this.isNormalCompletion;
            zArr[i] = zArr[i] | z;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
        public void visitThrowToInstruction(ThrowToInstruction throwToInstruction, int i, int i2) {
            if (i2 > this.myFlow.getSize()) {
                i2 = this.myFlow.getSize();
            }
            boolean[] zArr = this.isNormalCompletion;
            zArr[i] = (!isLeaf(i2) && this.isNormalCompletion[i2]) | zArr[i];
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
        public void visitGoToInstruction(GoToInstruction goToInstruction, int i, int i2) {
            if (i2 > this.myFlow.getSize()) {
                i2 = this.myFlow.getSize();
            }
            boolean[] zArr = this.isNormalCompletion;
            zArr[i] = (!goToInstruction.isReturn && this.isNormalCompletion[i2]) | zArr[i];
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
        public void visitInstruction(Instruction instruction, int i, int i2) {
            if (i2 > this.myFlow.getSize()) {
                i2 = this.myFlow.getSize();
            }
            boolean z = isLeaf(i2) || this.isNormalCompletion[i2];
            boolean[] zArr = this.isNormalCompletion;
            zArr[i] = z | zArr[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
        public Boolean getResult() {
            Boolean boolValueOf = Boolean.valueOf(!this.isNormalCompletion[0]);
            if (boolValueOf == null) {
                $$$reportNull$$$0(1);
            }
            return boolValueOf;
        }
    }

    public static boolean returnPresentBetween(ControlFlow controlFlow, int i, int i2) {
        if (controlFlow == null) {
            $$$reportNull$$$0(62);
        }
        InstructionClientVisitor<Boolean> instructionClientVisitor = new InstructionClientVisitor<Boolean>(i, i2) { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.1MyVisitor
            private final boolean[] isNormalCompletion;
            final /* synthetic */ int val$endOffset;
            final /* synthetic */ int val$startOffset;

            private static /* synthetic */ void $$$reportNull$$$0(int i3) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$1MyVisitor", "getResult"));
            }

            {
                this.val$startOffset = i;
                this.val$endOffset = i2;
                this.isNormalCompletion = new boolean[this.val$flow.getSize() + 1];
                int size = this.val$flow.getSize();
                for (int i3 = 0; i3 < this.val$startOffset; i3++) {
                    this.isNormalCompletion[i3] = true;
                }
                for (int i4 = this.val$endOffset; i4 <= size; i4++) {
                    this.isNormalCompletion[i4] = true;
                }
            }

            /* JADX WARN: Code duplicated, block: B:16:0x0029  */
            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitConditionalThrowToInstruction(ConditionalThrowToInstruction conditionalThrowToInstruction, int i3, int i4) {
                if (i4 > this.val$flow.getSize()) {
                    i4 = this.val$flow.getSize();
                }
                if (i3 > this.val$endOffset) {
                    return;
                }
                int i5 = conditionalThrowToInstruction.offset;
                boolean z = false;
                if (i5 == i4) {
                    if (i5 > this.val$endOffset) {
                        return;
                    }
                    if (!isLeaf(i4) && this.isNormalCompletion[i4]) {
                        z = true;
                    }
                } else if (isLeaf(i4) || this.isNormalCompletion[i4]) {
                    z = true;
                }
                boolean[] zArr = this.isNormalCompletion;
                zArr[i3] = zArr[i3] | z;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitThrowToInstruction(ThrowToInstruction throwToInstruction, int i3, int i4) {
                if (i4 > this.val$flow.getSize()) {
                    i4 = this.val$flow.getSize();
                }
                int i5 = this.val$endOffset;
                if (i3 <= i5 && i4 <= i5) {
                    boolean z = !isLeaf(i4) && this.isNormalCompletion[i4];
                    boolean[] zArr = this.isNormalCompletion;
                    zArr[i3] = z | zArr[i3];
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitCallInstruction(CallInstruction callInstruction, int i3, int i4) {
                if (i4 > this.val$flow.getSize()) {
                    i4 = this.val$flow.getSize();
                }
                int i5 = this.val$endOffset;
                if (i3 > i5) {
                    return;
                }
                if (i4 <= i5 || i4 == i3 + 1) {
                    boolean[] zArr = this.isNormalCompletion;
                    zArr[i3] = zArr[i4] | zArr[i3];
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitGoToInstruction(GoToInstruction goToInstruction, int i3, int i4) {
                if (i4 > this.val$flow.getSize()) {
                    i4 = this.val$flow.getSize();
                }
                if (i3 > this.val$endOffset) {
                    return;
                }
                boolean z = (goToInstruction.isReturn || !this.isNormalCompletion[i4] || ((goToInstruction instanceof ReturnInstruction) && ((ReturnInstruction) goToInstruction).isRethrowFromFinally())) ? false : true;
                boolean[] zArr = this.isNormalCompletion;
                zArr[i3] = zArr[i3] | z;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitInstruction(Instruction instruction, int i3, int i4) {
                if (i4 > this.val$flow.getSize()) {
                    i4 = this.val$flow.getSize();
                }
                if (i3 > this.val$endOffset) {
                    return;
                }
                boolean z = isLeaf(i4) || this.isNormalCompletion[i4];
                boolean[] zArr = this.isNormalCompletion;
                zArr[i3] = z | zArr[i3];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
            public Boolean getResult() {
                Boolean boolValueOf = Boolean.valueOf(!this.isNormalCompletion[this.val$startOffset]);
                if (boolValueOf == null) {
                    $$$reportNull$$$0(0);
                }
                return boolValueOf;
            }
        };
        depthFirstSearch(controlFlow, instructionClientVisitor, i, i2);
        return instructionClientVisitor.getResult().booleanValue();
    }

    public static boolean canCompleteNormally(ControlFlow controlFlow, int i, int i2) {
        if (controlFlow == null) {
            $$$reportNull$$$0(63);
        }
        InstructionClientVisitor<Boolean> instructionClientVisitor = new InstructionClientVisitor<Boolean>(i2, i) { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.2MyVisitor
            private final boolean[] canCompleteNormally;
            final /* synthetic */ int val$endOffset;
            final /* synthetic */ int val$startOffset;

            private static /* synthetic */ void $$$reportNull$$$0(int i3) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$2MyVisitor", "getResult"));
            }

            {
                this.val$endOffset = i2;
                this.val$startOffset = i;
                this.canCompleteNormally = new boolean[this.val$flow.getSize() + 1];
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitConditionalGoToInstruction(ConditionalGoToInstruction conditionalGoToInstruction, int i3, int i4) {
                checkInstruction(i3, i4, false);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitGoToInstruction(GoToInstruction goToInstruction, int i3, int i4) {
                checkInstruction(i3, i4, goToInstruction.isReturn);
            }

            /* JADX WARN: Code duplicated, block: B:32:0x005d  */
            private void checkInstruction(int i3, int i4, boolean z) {
                PsiStatement psiStatementFindContinuedStatement;
                PsiStatement psiStatementFindExitedStatement;
                if (i3 > this.val$endOffset) {
                    return;
                }
                if (i4 > this.val$flow.getSize()) {
                    i4 = this.val$flow.getSize();
                }
                int i5 = this.val$endOffset;
                boolean z2 = false;
                boolean z3 = i4 <= i5 && !z && (i4 == i5 || this.canCompleteNormally[i4]);
                if (z3 && i4 == i5) {
                    PsiElement element = this.val$flow.getElement(i3);
                    if (!(element instanceof PsiBreakStatement) ? !(element instanceof PsiContinueStatement) || ((psiStatementFindContinuedStatement = ((PsiContinueStatement) element).findContinuedStatement()) != null && this.val$flow.getStartOffset(psiStatementFindContinuedStatement) >= this.val$startOffset) : (psiStatementFindExitedStatement = ((PsiBreakStatement) element).findExitedStatement()) != null && this.val$flow.getStartOffset(psiStatementFindExitedStatement) >= this.val$startOffset) {
                        z2 = z3;
                    }
                } else {
                    z2 = z3;
                }
                boolean[] zArr = this.canCompleteNormally;
                zArr[i3] = zArr[i3] | z2;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitConditionalThrowToInstruction(ConditionalThrowToInstruction conditionalThrowToInstruction, int i3, int i4) {
                boolean z;
                if (i4 > this.val$flow.getSize()) {
                    i4 = this.val$flow.getSize();
                }
                if (i3 > this.val$endOffset) {
                    return;
                }
                int i5 = conditionalThrowToInstruction.offset;
                if (i5 == i4) {
                    z = i5 <= this.val$endOffset && !isLeaf(i4) && this.canCompleteNormally[i4];
                } else {
                    z = this.canCompleteNormally[i4];
                }
                boolean[] zArr = this.canCompleteNormally;
                zArr[i3] = z | zArr[i3];
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitThrowToInstruction(ThrowToInstruction throwToInstruction, int i3, int i4) {
                if (i4 > this.val$flow.getSize()) {
                    i4 = this.val$flow.getSize();
                }
                int i5 = this.val$endOffset;
                if (i3 <= i5 && i4 <= i5) {
                    boolean z = !isLeaf(i4) && this.canCompleteNormally[i4];
                    boolean[] zArr = this.canCompleteNormally;
                    zArr[i3] = z | zArr[i3];
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitCallInstruction(CallInstruction callInstruction, int i3, int i4) {
                if (i4 > this.val$flow.getSize()) {
                    i4 = this.val$flow.getSize();
                }
                int i5 = this.val$endOffset;
                if (i3 > i5) {
                    return;
                }
                if (i4 <= i5 || i4 == i3 + 1) {
                    boolean[] zArr = this.canCompleteNormally;
                    zArr[i3] = zArr[i4] | zArr[i3];
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitInstruction(Instruction instruction, int i3, int i4) {
                checkInstruction(i3, i4, false);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
            public Boolean getResult() {
                Boolean boolValueOf = Boolean.valueOf(this.canCompleteNormally[this.val$startOffset]);
                if (boolValueOf == null) {
                    $$$reportNull$$$0(0);
                }
                return boolValueOf;
            }
        };
        depthFirstSearch(controlFlow, instructionClientVisitor, i, i2);
        return instructionClientVisitor.getResult().booleanValue();
    }

    public static PsiElement getUnreachableStatement(ControlFlow controlFlow) {
        if (controlFlow == null) {
            $$$reportNull$$$0(64);
        }
        UnreachableStatementClientVisitor unreachableStatementClientVisitor = new UnreachableStatementClientVisitor(controlFlow);
        depthFirstSearch(controlFlow, unreachableStatementClientVisitor);
        return unreachableStatementClientVisitor.getResult();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class UnreachableStatementClientVisitor extends InstructionClientVisitor<PsiElement> {
        private final ControlFlow myFlow;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1 || i == 2) {
                objArr[0] = "statement";
            } else {
                objArr[0] = "flow";
            }
            objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$UnreachableStatementClientVisitor";
            if (i == 1) {
                objArr[2] = "getEnclosingUnreachableStatement";
            } else if (i != 2) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "getUnreachableStatementParent";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        UnreachableStatementClientVisitor(ControlFlow controlFlow) {
            if (controlFlow == null) {
                $$$reportNull$$$0(0);
            }
            this.myFlow = controlFlow;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
        public PsiElement getResult() {
            int startOffset;
            for (int i = 0; i < this.processedInstructions.length; i++) {
                if (!this.processedInstructions[i]) {
                    PsiElement element = this.myFlow.getElement(i);
                    PsiElement unreachableExpressionParent = getUnreachableExpressionParent(element);
                    if (unreachableExpressionParent != null) {
                        return unreachableExpressionParent;
                    }
                    if (element != null && PsiUtil.isStatement(element) && !(element.getMyParent() instanceof PsiExpression)) {
                        while (element instanceof PsiExpression) {
                            element = element.getMyParent();
                        }
                        if ((!(element instanceof PsiStatement) || !(element.getMyParent() instanceof PsiForStatement) || element != ((PsiForStatement) element.getMyParent()).getUpdate()) && this.myFlow.getEndOffset(element) == i + 1 && ((startOffset = this.myFlow.getStartOffset(element)) < 0 || startOffset >= this.processedInstructions.length || !this.processedInstructions[startOffset])) {
                            PsiElement enclosingUnreachableStatement = getEnclosingUnreachableStatement(element);
                            return enclosingUnreachableStatement != null ? enclosingUnreachableStatement : element;
                        }
                    }
                }
            }
            return null;
        }

        private static PsiElement getUnreachableExpressionParent(PsiElement psiElement) {
            PsiElement psiElementFindFirstParent;
            if (!(psiElement instanceof PsiExpression) || (psiElementFindFirstParent = PsiTreeUtil.findFirstParent(psiElement, new Condition() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil$UnreachableStatementClientVisitor$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Condition
                public final boolean value(Object obj) {
                    return ControlFlowUtil.UnreachableStatementClientVisitor.lambda$getUnreachableExpressionParent$0((PsiElement) obj);
                }
            })) == null) {
                return null;
            }
            PsiElement myParent = psiElementFindFirstParent.getMyParent();
            if (myParent instanceof PsiExpressionStatement) {
                return getUnreachableStatementParent(myParent);
            }
            if (((myParent instanceof PsiIfStatement) && ((PsiIfStatement) myParent).getCondition() == psiElementFindFirstParent) || (((myParent instanceof PsiSwitchBlock) && ((PsiSwitchBlock) myParent).getExpression() == psiElementFindFirstParent) || (((myParent instanceof PsiWhileStatement) && ((PsiWhileStatement) myParent).getCondition() == psiElementFindFirstParent) || ((myParent instanceof PsiForeachStatement) && ((PsiForeachStatement) myParent).getIteratedValue() == psiElementFindFirstParent)))) {
                return myParent;
            }
            return null;
        }

        static /* synthetic */ boolean lambda$getUnreachableExpressionParent$0(PsiElement psiElement) {
            return !(psiElement.getMyParent() instanceof PsiParenthesizedExpression);
        }

        private static PsiElement getEnclosingUnreachableStatement(PsiElement psiElement) {
            PsiBlockStatement psiBlockStatement;
            if (psiElement == null) {
                $$$reportNull$$$0(1);
            }
            PsiElement myParent = psiElement.getMyParent();
            if ((myParent instanceof PsiDoWhileStatement) && ((PsiDoWhileStatement) myParent).getBody() == psiElement) {
                return myParent;
            }
            if ((myParent instanceof PsiCodeBlock) && PsiTreeUtil.getNextSiblingOfType(myParent.getFirstChild(), PsiStatement.class) == psiElement && (psiBlockStatement = (PsiBlockStatement) ObjectUtils.tryCast(myParent.getMyParent(), PsiBlockStatement.class)) != null) {
                PsiElement parent = psiBlockStatement.getMyParent();
                if ((parent instanceof PsiDoWhileStatement) && ((PsiDoWhileStatement) parent).getBody() == psiBlockStatement) {
                    return parent;
                }
            }
            return getUnreachableStatementParent(psiElement);
        }

        private static PsiElement getUnreachableStatementParent(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(2);
            }
            PsiElement myParent = psiElement.getMyParent();
            if ((myParent instanceof PsiForStatement) && ((PsiForStatement) myParent).getInitialization() == psiElement) {
                return myParent;
            }
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static PsiReferenceExpression getEnclosingReferenceExpression(PsiElement psiElement, PsiVariable psiVariable) {
        if (psiElement == null) {
            $$$reportNull$$$0(65);
        }
        if (psiVariable == null) {
            $$$reportNull$$$0(66);
        }
        PsiReferenceExpression psiReferenceExpressionFindReferenceTo = findReferenceTo(psiElement, psiVariable);
        if (psiReferenceExpressionFindReferenceTo != null) {
            return psiReferenceExpressionFindReferenceTo;
        }
        while (psiElement != null) {
            if (psiElement instanceof PsiReferenceExpression) {
                return (PsiReferenceExpression) psiElement;
            }
            if ((psiElement instanceof PsiMethod) || (psiElement instanceof PsiClass)) {
                break;
            }
            psiElement = psiElement.getMyParent();
        }
        return null;
    }

    private static PsiReferenceExpression findReferenceTo(PsiElement psiElement, PsiVariable psiVariable) {
        if (psiElement == null) {
            $$$reportNull$$$0(67);
        }
        if (psiVariable == null) {
            $$$reportNull$$$0(68);
        }
        if (psiElement instanceof PsiReferenceExpression) {
            PsiReferenceExpression psiReferenceExpression = (PsiReferenceExpression) psiElement;
            if (ExpressionUtil.isEffectivelyUnqualified(psiReferenceExpression) && psiReferenceExpression.isReferenceTo(psiVariable)) {
                return psiReferenceExpression;
            }
        }
        for (PsiElement firstChild = psiElement.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            PsiReferenceExpression psiReferenceExpressionFindReferenceTo = findReferenceTo(firstChild, psiVariable);
            if (psiReferenceExpressionFindReferenceTo != null) {
                return psiReferenceExpressionFindReferenceTo;
            }
        }
        return null;
    }

    public static boolean isDominator(ControlFlow controlFlow, final int i, final int i2) {
        InstructionClientVisitor<Boolean> instructionClientVisitor = new InstructionClientVisitor<Boolean>() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.3MyVisitor
            private final BitSet myReachedWithoutDominator = new BitSet();

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitInstruction(Instruction instruction, int i3, int i4) {
                super.visitInstruction(instruction, i3, i4);
                if (i4 != i) {
                    if (i2 == i4 || this.myReachedWithoutDominator.get(i4)) {
                        this.myReachedWithoutDominator.set(i3);
                    }
                }
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
            public Boolean getResult() {
                return Boolean.valueOf(this.myReachedWithoutDominator.get(0));
            }
        };
        depthFirstSearch(controlFlow, instructionClientVisitor, 0, i2);
        return !instructionClientVisitor.getResult().booleanValue();
    }

    public static boolean isVariableDefinitelyAssigned(PsiVariable psiVariable, ControlFlow controlFlow) {
        if (psiVariable == null) {
            $$$reportNull$$$0(69);
        }
        if (controlFlow == null) {
            $$$reportNull$$$0(70);
        }
        PsiElement parent = psiVariable.getMyParent();
        int startOffset = parent == null ? -1 : controlFlow.getStartOffset(parent);
        if (startOffset <= -1) {
            startOffset = 0;
        }
        return !getVariablePossiblyUnassignedOffsets(psiVariable, controlFlow)[startOffset];
    }

    public static boolean[] getVariablePossiblyUnassignedOffsets(PsiVariable psiVariable, ControlFlow controlFlow) {
        if (psiVariable == null) {
            $$$reportNull$$$0(71);
        }
        if (controlFlow == null) {
            $$$reportNull$$$0(72);
        }
        if (controlFlow.getSize() == 0) {
            return new boolean[]{true};
        }
        InstructionClientVisitor<boolean[]> instructionClientVisitor = new InstructionClientVisitor<boolean[]>(controlFlow) { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.4MyVisitor
            private final boolean[] maybeUnassigned;
            final PsiManager psiManager;
            final /* synthetic */ ControlFlow val$flow;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$4MyVisitor", "getResult"));
            }

            {
                this.val$flow = controlFlow;
                this.psiManager = this.val$variable.getManager();
                boolean[] zArr = new boolean[controlFlow.getSize() + 1];
                this.maybeUnassigned = zArr;
                zArr[zArr.length - 1] = true;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitWriteVariableInstruction(WriteVariableInstruction writeVariableInstruction, int i, int i2) {
                if (this.psiManager.areElementsEquivalent(writeVariableInstruction.variable, this.val$variable)) {
                    this.maybeUnassigned[i] = false;
                } else {
                    visitInstruction(writeVariableInstruction, i, i2);
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitConditionalThrowToInstruction(ConditionalThrowToInstruction conditionalThrowToInstruction, int i, int i2) {
                if (i2 > this.val$flow.getSize()) {
                    i2 = this.val$flow.getSize();
                }
                boolean z = true;
                if (i != this.val$flow.getSize() - 1 && (isLeaf(i2) || !this.maybeUnassigned[i2])) {
                    z = false;
                }
                boolean[] zArr = this.maybeUnassigned;
                zArr[i] = zArr[i] | z;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitCallInstruction(CallInstruction callInstruction, int i, int i2) {
                visitInstruction(callInstruction, i, i2);
                for (int i3 = callInstruction.procBegin; i3 < callInstruction.procEnd + 3; i3++) {
                    this.maybeUnassigned[i3] = false;
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitGoToInstruction(GoToInstruction goToInstruction, int i, int i2) {
                if (goToInstruction.isReturn && (this.val$variable instanceof PsiLocalVariable)) {
                    if (i2 > this.val$flow.getSize()) {
                        i2 = this.val$flow.getSize();
                    }
                    boolean z = !isLeaf(i2) && this.maybeUnassigned[i2];
                    boolean[] zArr = this.maybeUnassigned;
                    zArr[i] = z | zArr[i];
                    return;
                }
                super.visitGoToInstruction(goToInstruction, i, i2);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitThrowToInstruction(ThrowToInstruction throwToInstruction, int i, int i2) {
                if (i2 > this.val$flow.getSize()) {
                    i2 = this.val$flow.getSize();
                }
                boolean z = !isLeaf(i2) && this.maybeUnassigned[i2];
                boolean[] zArr = this.maybeUnassigned;
                zArr[i] = z | zArr[i];
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitInstruction(Instruction instruction, int i, int i2) {
                if (i2 > this.val$flow.getSize()) {
                    i2 = this.val$flow.getSize();
                }
                boolean z = isLeaf(i2) || this.maybeUnassigned[i2];
                boolean[] zArr = this.maybeUnassigned;
                zArr[i] = z | zArr[i];
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
            public boolean[] getResult() {
                boolean[] zArr = this.maybeUnassigned;
                if (zArr == null) {
                    $$$reportNull$$$0(0);
                }
                return zArr;
            }
        };
        depthFirstSearch(controlFlow, instructionClientVisitor);
        return instructionClientVisitor.getResult();
    }

    public static boolean isVariableDefinitelyNotAssigned(PsiVariable psiVariable, ControlFlow controlFlow) {
        if (psiVariable == null) {
            $$$reportNull$$$0(73);
        }
        if (controlFlow == null) {
            $$$reportNull$$$0(74);
        }
        InstructionClientVisitor<Boolean> instructionClientVisitor = new InstructionClientVisitor<Boolean>(controlFlow) { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.5MyVisitor
            private final boolean[] maybeAssigned;
            final PsiManager psiManager;
            final /* synthetic */ ControlFlow val$flow;

            private static /* synthetic */ void $$$reportNull$$$0(int i) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$5MyVisitor", "getResult"));
            }

            {
                this.val$flow = controlFlow;
                this.psiManager = this.val$variable.getManager();
                this.maybeAssigned = new boolean[controlFlow.getSize() + 1];
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitWriteVariableInstruction(WriteVariableInstruction writeVariableInstruction, int i, int i2) {
                if (i2 > this.val$flow.getSize()) {
                    i2 = this.val$flow.getSize();
                }
                boolean z = this.psiManager.areElementsEquivalent(writeVariableInstruction.variable, this.val$variable) || this.maybeAssigned[i2];
                boolean[] zArr = this.maybeAssigned;
                zArr[i] = z | zArr[i];
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitThrowToInstruction(ThrowToInstruction throwToInstruction, int i, int i2) {
                if (i2 > this.val$flow.getSize()) {
                    i2 = this.val$flow.getSize();
                }
                boolean z = !isLeaf(i2) && this.maybeAssigned[i2];
                boolean[] zArr = this.maybeAssigned;
                zArr[i] = z | zArr[i];
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitConditionalThrowToInstruction(ConditionalThrowToInstruction conditionalThrowToInstruction, int i, int i2) {
                boolean z;
                if (i2 > this.val$flow.getSize()) {
                    i2 = this.val$flow.getSize();
                }
                if (conditionalThrowToInstruction.offset == i2) {
                    z = !isLeaf(i2) && this.maybeAssigned[i2];
                } else {
                    z = this.maybeAssigned[i2];
                }
                boolean[] zArr = this.maybeAssigned;
                zArr[i] = z | zArr[i];
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitInstruction(Instruction instruction, int i, int i2) {
                if (i2 > this.val$flow.getSize()) {
                    i2 = this.val$flow.getSize();
                }
                boolean[] zArr = this.maybeAssigned;
                zArr[i] = zArr[i2] | zArr[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
            public Boolean getResult() {
                Boolean boolValueOf = Boolean.valueOf(!this.maybeAssigned[0]);
                if (boolValueOf == null) {
                    $$$reportNull$$$0(0);
                }
                return boolValueOf;
            }
        };
        depthFirstSearch(controlFlow, instructionClientVisitor);
        return instructionClientVisitor.getResult().booleanValue();
    }

    public static boolean isValueUsedWithoutVisitingStop(ControlFlow controlFlow, int i, int i2, PsiVariable psiVariable) {
        if (controlFlow == null) {
            $$$reportNull$$$0(75);
        }
        if (psiVariable == null) {
            $$$reportNull$$$0(76);
        }
        if (i == i2) {
            return false;
        }
        InstructionClientVisitor<Boolean> instructionClientVisitor = new InstructionClientVisitor<Boolean>(psiVariable, i2, i) { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.6MyVisitor
            private final boolean[] maybeReferenced;
            final PsiManager psiManager;
            final /* synthetic */ int val$start;
            final /* synthetic */ int val$stop;
            final /* synthetic */ PsiVariable val$variable;

            private static /* synthetic */ void $$$reportNull$$$0(int i3) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$6MyVisitor", "getResult"));
            }

            {
                this.val$variable = psiVariable;
                this.val$stop = i2;
                this.val$start = i;
                this.maybeReferenced = new boolean[this.val$flow.getSize() + 1];
                this.psiManager = psiVariable.getManager();
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitInstruction(Instruction instruction, int i3, int i4) {
                boolean z = false;
                if (i3 == this.val$stop) {
                    this.maybeReferenced[i3] = false;
                    return;
                }
                if ((instruction instanceof WriteVariableInstruction) && this.psiManager.areElementsEquivalent(((WriteVariableInstruction) instruction).variable, this.val$variable)) {
                    this.maybeReferenced[i3] = false;
                    return;
                }
                if (this.maybeReferenced[i3]) {
                    return;
                }
                if (i4 > this.val$flow.getSize()) {
                    i4 = this.val$flow.getSize();
                }
                boolean[] zArr = this.maybeReferenced;
                if (zArr[i4] || ((instruction instanceof ReadVariableInstruction) && this.psiManager.areElementsEquivalent(((ReadVariableInstruction) instruction).variable, this.val$variable))) {
                    z = true;
                }
                zArr[i3] = z;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
            public Boolean getResult() {
                Boolean boolValueOf = Boolean.valueOf(this.maybeReferenced[this.val$start]);
                if (boolValueOf == null) {
                    $$$reportNull$$$0(0);
                }
                return boolValueOf;
            }
        };
        depthFirstSearch(controlFlow, instructionClientVisitor, i, controlFlow.getSize());
        return instructionClientVisitor.getResult().booleanValue();
    }

    public static boolean isVariableAccess(ControlFlow controlFlow, int i, PsiVariable psiVariable) {
        if (controlFlow == null) {
            $$$reportNull$$$0(77);
        }
        if (psiVariable == null) {
            $$$reportNull$$$0(78);
        }
        Instruction instruction = controlFlow.getInstructions().get(i);
        PsiManager manager = psiVariable.getManager();
        return ((instruction instanceof ReadVariableInstruction) && manager.areElementsEquivalent(((ReadVariableInstruction) instruction).variable, psiVariable)) || ((instruction instanceof WriteVariableInstruction) && manager.areElementsEquivalent(((WriteVariableInstruction) instruction).variable, psiVariable));
    }

    public static class ControlFlowEdge {
        public final int myFrom;
        public final int myTo;

        ControlFlowEdge(int i, int i2) {
            this.myFrom = i;
            this.myTo = i2;
        }

        public String toString() {
            return this.myFrom + "->" + this.myTo;
        }
    }

    public static List<ControlFlowEdge> getEdges(ControlFlow controlFlow, int i) {
        if (controlFlow == null) {
            $$$reportNull$$$0(79);
        }
        final ArrayList arrayList = new ArrayList();
        depthFirstSearch(controlFlow, new InstructionClientVisitor<Void>() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.3
            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
            public Void getResult() {
                return null;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitInstruction(Instruction instruction, int i2, int i3) {
                arrayList.add(new ControlFlowEdge(i2, i3));
            }
        }, i, controlFlow.getSize());
        return arrayList;
    }

    public static int getMinDefinitelyReachedOffset(ControlFlow controlFlow, int i, List<? extends PsiElement> list) {
        if (controlFlow == null) {
            $$$reportNull$$$0(81);
        }
        if (list == null) {
            $$$reportNull$$$0(82);
        }
        InstructionClientVisitor<Integer> instructionClientVisitor = new InstructionClientVisitor<Integer>(i, list) { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.7MyVisitor
            private final IntSet[] exitPoints;
            final /* synthetic */ List val$references;
            final /* synthetic */ int val$sourceOffset;

            private static /* synthetic */ void $$$reportNull$$$0(int i2) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$7MyVisitor", "getResult"));
            }

            {
                this.val$sourceOffset = i;
                this.val$references = list;
                this.exitPoints = new IntOpenHashSet[this.val$flow.getSize()];
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitInstruction(Instruction instruction, int i2, int i3) {
                if (i3 > this.val$flow.getSize()) {
                    i3 = this.val$flow.getSize();
                }
                IntSet[] intSetArr = this.exitPoints;
                if (intSetArr[i2] == null) {
                    intSetArr[i2] = new IntOpenHashSet();
                }
                if (isLeaf(i3)) {
                    this.exitPoints[i2].add(i2);
                    return;
                }
                IntSet[] intSetArr2 = this.exitPoints;
                IntSet intSet = intSetArr2[i3];
                if (intSet != null) {
                    intSetArr2[i2].addAll((IntCollection) intSet);
                }
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
            public Integer getResult() {
                int endOffset;
                int size = this.val$flow.getSize();
                int i2 = this.val$sourceOffset;
                int i3 = 0;
                while (true) {
                    IntSet[] intSetArr = this.exitPoints;
                    if (i2 >= intSetArr.length) {
                        break;
                    }
                    IntSet intSet = intSetArr[i2];
                    int size2 = intSet == null ? 0 : intSet.size();
                    if (size2 > i3) {
                        Iterator it = this.val$references.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                size = i2;
                                i3 = size2;
                                break;
                            }
                            PsiElement enclosingStatement = PsiUtil.getEnclosingStatement((PsiElement) it.next());
                            if (enclosingStatement != null && (endOffset = this.val$flow.getEndOffset(enclosingStatement)) != -1 && i2 != endOffset && !ControlFlowUtil.isInstructionReachable(this.val$flow, i2, endOffset)) {
                                break;
                            }
                        }
                    }
                    i2++;
                }
                Integer numValueOf = Integer.valueOf(size);
                if (numValueOf == null) {
                    $$$reportNull$$$0(0);
                }
                return numValueOf;
            }
        };
        depthFirstSearch(controlFlow, instructionClientVisitor);
        return instructionClientVisitor.getResult().intValue();
    }

    private static int findUnprocessed(int i, int i2, InstructionClientVisitor<?> instructionClientVisitor) {
        if (instructionClientVisitor == null) {
            $$$reportNull$$$0(83);
        }
        while (i < i2) {
            if (!instructionClientVisitor.processedInstructions[i]) {
                return i;
            }
            i++;
        }
        return i2;
    }

    private static void depthFirstSearch(ControlFlow controlFlow, InstructionClientVisitor<?> instructionClientVisitor) {
        if (controlFlow == null) {
            $$$reportNull$$$0(84);
        }
        if (instructionClientVisitor == null) {
            $$$reportNull$$$0(85);
        }
        depthFirstSearch(controlFlow, instructionClientVisitor, 0, controlFlow.getSize());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void depthFirstSearch(ControlFlow controlFlow, InstructionClientVisitor<?> instructionClientVisitor, int i, int i2) {
        if (controlFlow == null) {
            $$$reportNull$$$0(86);
        }
        if (instructionClientVisitor == null) {
            $$$reportNull$$$0(87);
        }
        instructionClientVisitor.processedInstructions = new boolean[i2];
        internalDepthFirstSearch(controlFlow.getInstructions(), instructionClientVisitor, i, i2);
    }

    private static void internalDepthFirstSearch(final List<? extends Instruction> list, final InstructionClientVisitor<?> instructionClientVisitor, int i, int i2) {
        if (list == null) {
            $$$reportNull$$$0(88);
        }
        if (instructionClientVisitor == null) {
            $$$reportNull$$$0(89);
        }
        final WalkThroughStack walkThroughStack = new WalkThroughStack(list.size() / 2);
        walkThroughStack.push(i);
        ControlFlowInstructionVisitor controlFlowInstructionVisitor = new ControlFlowInstructionVisitor() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.4
            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitCallInstruction(CallInstruction callInstruction, int i3, int i4) {
                int i5 = callInstruction.offset;
                int i6 = callInstruction.procBegin;
                while (i6 < instructionClientVisitor.processedInstructions.length && (i6 < callInstruction.procEnd || (i6 < list.size() && (list.get(i6) instanceof ReturnInstruction)))) {
                    instructionClientVisitor.processedInstructions[i6] = false;
                    i6++;
                }
                instructionClientVisitor.procedureEntered(callInstruction.procBegin, i6);
                walkThroughStack.currentStack = new CallStackItem(walkThroughStack.currentStack, i3 + 1);
                walkThroughStack.push(i3, i5);
                walkThroughStack.push(i5);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitReturnInstruction(ReturnInstruction returnInstruction, int i3, int i4) {
                int i5;
                if (walkThroughStack.currentStack != null) {
                    i5 = walkThroughStack.currentStack.target;
                    WalkThroughStack walkThroughStack2 = walkThroughStack;
                    walkThroughStack2.currentStack = walkThroughStack2.currentStack.next;
                } else {
                    i5 = -1;
                }
                if (returnInstruction.offset != 0) {
                    i5 = returnInstruction.offset;
                }
                if (i5 != -1) {
                    walkThroughStack.push(i3, i5);
                    walkThroughStack.push(i5);
                }
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitBranchingInstruction(BranchingInstruction branchingInstruction, int i3, int i4) {
                int i5 = branchingInstruction.offset;
                walkThroughStack.push(i3, i5);
                walkThroughStack.push(i5);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitConditionalBranchingInstruction(ConditionalBranchingInstruction conditionalBranchingInstruction, int i3, int i4) {
                int i5 = conditionalBranchingInstruction.offset;
                walkThroughStack.push(i3, i5);
                int i6 = i3 + 1;
                walkThroughStack.push(i3, i6);
                walkThroughStack.push(i5);
                walkThroughStack.push(i6);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitInstruction(Instruction instruction, int i3, int i4) {
                int i5 = i3 + 1;
                walkThroughStack.push(i3, i5);
                walkThroughStack.push(i5);
            }
        };
        while (!walkThroughStack.isEmpty()) {
            int iPeekOldOffset = walkThroughStack.peekOldOffset();
            int iPopNewOffset = walkThroughStack.popNewOffset();
            if (iPeekOldOffset < i2) {
                Instruction instruction = list.get(iPeekOldOffset);
                if (!instructionClientVisitor.processedInstructions[iPeekOldOffset]) {
                    instructionClientVisitor.processedInstructions[iPeekOldOffset] = true;
                    instruction.accept(controlFlowInstructionVisitor, iPeekOldOffset, iPopNewOffset);
                } else if (iPopNewOffset != -1) {
                    instruction.accept(instructionClientVisitor, iPeekOldOffset, iPopNewOffset);
                }
            }
        }
    }

    private static final class CallStackItem {
        final CallStackItem next;
        final int target;

        private CallStackItem(CallStackItem callStackItem, int i) {
            this.next = callStackItem;
            this.target = i;
        }
    }

    private static class WalkThroughStack {
        private CallStackItem[] callStacks;
        private CallStackItem currentStack;
        private int[] newOffsets;
        private int[] oldOffsets;
        private int size;

        WalkThroughStack(int i) {
            i = i < 2 ? 2 : i;
            this.oldOffsets = new int[i];
            this.newOffsets = new int[i];
            this.callStacks = new CallStackItem[i];
        }

        void push(int i, int i2) {
            ControlFlowUtil.LOG.assertTrue(i >= 0, "negative offset is pushed to walk-through stack");
            int i3 = this.size;
            if (i3 >= this.newOffsets.length) {
                int i4 = (i3 * 3) / 2;
                this.oldOffsets = Arrays.copyOf(this.oldOffsets, i4);
                this.newOffsets = Arrays.copyOf(this.newOffsets, i4);
                this.callStacks = (CallStackItem[]) Arrays.copyOf(this.callStacks, i4);
            }
            int[] iArr = this.oldOffsets;
            int i5 = this.size;
            iArr[i5] = i;
            this.newOffsets[i5] = i2;
            this.callStacks[i5] = this.currentStack;
            this.size = i5 + 1;
        }

        void push(int i) {
            push(i, -1);
        }

        int peekOldOffset() {
            return this.oldOffsets[this.size - 1];
        }

        int popNewOffset() {
            CallStackItem[] callStackItemArr = this.callStacks;
            int i = this.size - 1;
            this.size = i;
            this.currentStack = callStackItemArr[i];
            return this.newOffsets[i];
        }

        boolean isEmpty() {
            return this.size == 0;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.size; i++) {
                if (sb.length() != 0) {
                    sb.append(StringUtil.SPACE);
                }
                if (this.newOffsets[i] != -1) {
                    sb.append('(').append(this.oldOffsets[i]).append("->").append(this.newOffsets[i]).append(')');
                } else {
                    sb.append('[').append(this.oldOffsets[i]).append(']');
                }
            }
            return sb.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isInsideReturnStatement(PsiElement psiElement) {
        while (psiElement instanceof PsiExpression) {
            psiElement = psiElement.getMyParent();
        }
        return psiElement instanceof PsiReturnStatement;
    }

    private static class CopyOnWriteList {
        private final List<VariableInfo> list;

        /* JADX WARN: Code duplicated, block: B:34:0x005b  */
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 8 || i == 10 || i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 1 || i == 8 || i == 10 || i == 3 || i == 4) ? 2 : 3];
            switch (i) {
                case 1:
                case 3:
                case 4:
                case 8:
                case 10:
                    objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$CopyOnWriteList";
                    break;
                case 2:
                case 9:
                default:
                    objArr[0] = "value";
                    break;
                case 5:
                case 6:
                    objArr[0] = AbstractDiagnosticCollector.SUPPRESS_ALL_INFOS;
                    break;
                case 7:
                    objArr[0] = "addList";
                    break;
            }
            if (i == 1) {
                objArr[1] = ProductAction.ACTION_ADD;
            } else if (i == 8) {
                objArr[1] = "addAll";
            } else if (i == 10) {
                objArr[1] = ProductAction.ACTION_ADD;
            } else if (i == 3) {
                objArr[1] = ProductAction.ACTION_REMOVE;
            } else if (i != 4) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$CopyOnWriteList";
            } else {
                objArr[1] = "getList";
            }
            switch (i) {
                case 1:
                case 3:
                case 4:
                case 8:
                case 10:
                    break;
                case 2:
                    objArr[2] = ProductAction.ACTION_REMOVE;
                    break;
                case 5:
                case 6:
                    objArr[2] = "<init>";
                    break;
                case 7:
                    objArr[2] = "addAll";
                    break;
                case 9:
                default:
                    objArr[2] = ProductAction.ACTION_ADD;
                    break;
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 8 && i != 10 && i != 3 && i != 4) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        public CopyOnWriteList add(VariableInfo variableInfo) {
            if (variableInfo == null) {
                $$$reportNull$$$0(0);
            }
            CopyOnWriteList copyOnWriteList = new CopyOnWriteList();
            for (VariableInfo variableInfo2 : getList()) {
                if (!variableInfo.equals(variableInfo2)) {
                    copyOnWriteList.list.add(variableInfo2);
                }
            }
            copyOnWriteList.list.add(variableInfo);
            return copyOnWriteList;
        }

        public CopyOnWriteList remove(VariableInfo variableInfo) {
            if (variableInfo == null) {
                $$$reportNull$$$0(2);
            }
            CopyOnWriteList copyOnWriteList = new CopyOnWriteList();
            for (VariableInfo variableInfo2 : getList()) {
                if (!variableInfo.equals(variableInfo2)) {
                    copyOnWriteList.list.add(variableInfo2);
                }
            }
            return copyOnWriteList;
        }

        public List<VariableInfo> getList() {
            List<VariableInfo> list = this.list;
            if (list == null) {
                $$$reportNull$$$0(4);
            }
            return list;
        }

        CopyOnWriteList() {
            this(Collections.emptyList());
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        CopyOnWriteList(VariableInfo... variableInfoArr) {
            this(Arrays.asList(variableInfoArr));
            if (variableInfoArr == null) {
                $$$reportNull$$$0(5);
            }
        }

        CopyOnWriteList(Collection<? extends VariableInfo> collection) {
            if (collection == null) {
                $$$reportNull$$$0(6);
            }
            this.list = new SmartList((Collection) collection);
        }

        public CopyOnWriteList addAll(CopyOnWriteList copyOnWriteList) {
            if (copyOnWriteList == null) {
                $$$reportNull$$$0(7);
            }
            CopyOnWriteList copyOnWriteList2 = new CopyOnWriteList();
            copyOnWriteList2.list.addAll(getList());
            for (VariableInfo variableInfo : copyOnWriteList.getList()) {
                if (!copyOnWriteList2.list.contains(variableInfo)) {
                    copyOnWriteList2.list.add(variableInfo);
                }
            }
            return copyOnWriteList2;
        }

        public static CopyOnWriteList add(CopyOnWriteList copyOnWriteList, VariableInfo variableInfo) {
            if (variableInfo == null) {
                $$$reportNull$$$0(9);
            }
            CopyOnWriteList copyOnWriteList2 = copyOnWriteList == null ? new CopyOnWriteList(variableInfo) : copyOnWriteList.add(variableInfo);
            if (copyOnWriteList2 == null) {
                $$$reportNull$$$0(10);
            }
            return copyOnWriteList2;
        }
    }

    public static class VariableInfo {
        public final PsiElement expression;
        private final PsiVariable variable;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "variable", "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$VariableInfo", "<init>"));
        }

        public VariableInfo(PsiVariable psiVariable, PsiElement psiElement) {
            if (psiVariable == null) {
                $$$reportNull$$$0(0);
            }
            this.variable = psiVariable;
            this.expression = psiElement;
        }

        public boolean equals(Object obj) {
            return this == obj || ((obj instanceof VariableInfo) && this.variable.equals(((VariableInfo) obj).variable));
        }

        public int hashCode() {
            return this.variable.hashCode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void merge(int i, CopyOnWriteList copyOnWriteList, CopyOnWriteList[] copyOnWriteListArr) {
        if (copyOnWriteListArr == null) {
            $$$reportNull$$$0(90);
        }
        if (copyOnWriteList != null) {
            CopyOnWriteList copyOnWriteList2 = copyOnWriteListArr[i];
            if (copyOnWriteList2 != null) {
                copyOnWriteList = copyOnWriteList2.addAll(copyOnWriteList);
            }
            copyOnWriteListArr[i] = copyOnWriteList;
        }
    }

    public static List<PsiReferenceExpression> getReadBeforeWriteLocals(ControlFlow controlFlow) {
        if (controlFlow == null) {
            $$$reportNull$$$0(91);
        }
        ReadBeforeWriteClientVisitor readBeforeWriteClientVisitor = new ReadBeforeWriteClientVisitor(controlFlow, true);
        depthFirstSearch(controlFlow, readBeforeWriteClientVisitor);
        List<PsiReferenceExpression> result = readBeforeWriteClientVisitor.getResult();
        if (result == null) {
            $$$reportNull$$$0(92);
        }
        return result;
    }

    public static List<PsiReferenceExpression> getReadBeforeWrite(ControlFlow controlFlow) {
        if (controlFlow == null) {
            $$$reportNull$$$0(93);
        }
        return getReadBeforeWrite(controlFlow, 0);
    }

    private static List<PsiReferenceExpression> getReadBeforeWrite(ControlFlow controlFlow, int i) {
        if (controlFlow == null) {
            $$$reportNull$$$0(94);
        }
        if (i < 0 || i >= controlFlow.getSize()) {
            List<PsiReferenceExpression> listEmptyList = Collections.emptyList();
            if (listEmptyList == null) {
                $$$reportNull$$$0(95);
            }
            return listEmptyList;
        }
        ReadBeforeWriteClientVisitor readBeforeWriteClientVisitor = new ReadBeforeWriteClientVisitor(controlFlow, false);
        depthFirstSearch(controlFlow, readBeforeWriteClientVisitor);
        List<PsiReferenceExpression> result = readBeforeWriteClientVisitor.getResult(i);
        if (result == null) {
            $$$reportNull$$$0(96);
        }
        return result;
    }

    private static class ReadBeforeWriteClientVisitor extends InstructionClientVisitor<List<PsiReferenceExpression>> {
        private final boolean localVariablesOnly;
        private final ControlFlow myFlow;
        private final CopyOnWriteList[] readVariables;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 3 || i == 4 || i == 5) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 3 || i == 4 || i == 5) ? 2 : 3];
            if (i == 1 || i == 2) {
                objArr[0] = "variable";
            } else if (i == 3 || i == 4 || i == 5) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$ReadBeforeWriteClientVisitor";
            } else {
                objArr[0] = "flow";
            }
            if (i == 3 || i == 4 || i == 5) {
                objArr[1] = "getResult";
            } else {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$ReadBeforeWriteClientVisitor";
            }
            if (i == 1) {
                objArr[2] = "isImplicitlyInitialized";
            } else if (i == 2) {
                objArr[2] = "isMethodParameter";
            } else if (i != 3 && i != 4 && i != 5) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 3 && i != 4 && i != 5) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        ReadBeforeWriteClientVisitor(ControlFlow controlFlow, boolean z) {
            if (controlFlow == null) {
                $$$reportNull$$$0(0);
            }
            this.myFlow = controlFlow;
            this.localVariablesOnly = z;
            this.readVariables = new CopyOnWriteList[controlFlow.getSize() + 1];
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
        public void visitReadVariableInstruction(ReadVariableInstruction readVariableInstruction, int i, int i2) {
            PsiReferenceExpression enclosingReferenceExpression;
            CopyOnWriteList copyOnWriteListAdd = this.readVariables[Math.min(i2, this.myFlow.getSize())];
            PsiVariable psiVariable = readVariableInstruction.variable;
            if ((!this.localVariablesOnly || !isImplicitlyInitialized(psiVariable)) && (enclosingReferenceExpression = ControlFlowUtil.getEnclosingReferenceExpression(this.myFlow.getElement(i), psiVariable)) != null) {
                copyOnWriteListAdd = CopyOnWriteList.add(copyOnWriteListAdd, new VariableInfo(psiVariable, enclosingReferenceExpression));
            }
            ControlFlowUtil.merge(i, copyOnWriteListAdd, this.readVariables);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
        public void visitWriteVariableInstruction(WriteVariableInstruction writeVariableInstruction, int i, int i2) {
            CopyOnWriteList copyOnWriteListRemove = this.readVariables[Math.min(i2, this.myFlow.getSize())];
            if (copyOnWriteListRemove == null) {
                return;
            }
            PsiVariable psiVariable = writeVariableInstruction.variable;
            if (!this.localVariablesOnly || !isImplicitlyInitialized(psiVariable)) {
                copyOnWriteListRemove = copyOnWriteListRemove.remove(new VariableInfo(psiVariable, null));
            }
            ControlFlowUtil.merge(i, copyOnWriteListRemove, this.readVariables);
        }

        private static boolean isImplicitlyInitialized(PsiVariable psiVariable) {
            if (psiVariable == null) {
                $$$reportNull$$$0(1);
            }
            return isMethodParameter(psiVariable) || (psiVariable instanceof PsiPatternVariable);
        }

        private static boolean isMethodParameter(PsiVariable psiVariable) {
            if (psiVariable == null) {
                $$$reportNull$$$0(2);
            }
            if (psiVariable instanceof PsiParameter) {
                return !(((PsiParameter) psiVariable).getMethod() instanceof PsiForeachStatement);
            }
            return false;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
        public void visitInstruction(Instruction instruction, int i, int i2) {
            ControlFlowUtil.merge(i, this.readVariables[Math.min(i2, this.myFlow.getSize())], this.readVariables);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
        public void visitCallInstruction(CallInstruction callInstruction, int i, int i2) {
            visitInstruction(callInstruction, i, i2);
            for (int i3 = callInstruction.procBegin; i3 <= callInstruction.procEnd; i3++) {
                this.readVariables[i3] = null;
            }
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
        public List<PsiReferenceExpression> getResult() {
            List<PsiReferenceExpression> result = getResult(0);
            if (result == null) {
                $$$reportNull$$$0(3);
            }
            return result;
        }

        public List<PsiReferenceExpression> getResult(int i) {
            CopyOnWriteList copyOnWriteList = this.readVariables[i];
            if (copyOnWriteList == null) {
                List<PsiReferenceExpression> listEmptyList = Collections.emptyList();
                if (listEmptyList == null) {
                    $$$reportNull$$$0(4);
                }
                return listEmptyList;
            }
            ArrayList arrayList = new ArrayList();
            Iterator<VariableInfo> it = copyOnWriteList.getList().iterator();
            while (it.hasNext()) {
                arrayList.add((PsiReferenceExpression) it.next().expression);
            }
            return arrayList;
        }
    }

    public static int getCompletionReasons(ControlFlow controlFlow, int i, int i2) {
        if (controlFlow == null) {
            $$$reportNull$$$0(97);
        }
        InstructionClientVisitor<Integer> instructionClientVisitor = new InstructionClientVisitor<Integer>(i2, controlFlow, i) { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.8MyVisitor
            private final boolean[] normalCompletion;
            private final boolean[] returnCalled;
            final /* synthetic */ int val$endOffset;
            final /* synthetic */ ControlFlow val$flow;
            final /* synthetic */ int val$offset;

            private static /* synthetic */ void $$$reportNull$$$0(int i3) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$8MyVisitor", "getResult"));
            }

            {
                this.val$endOffset = i2;
                this.val$flow = controlFlow;
                this.val$offset = i;
                this.normalCompletion = new boolean[i2];
                this.returnCalled = new boolean[i2];
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitInstruction(Instruction instruction, int i3, int i4) {
                int i5 = this.val$endOffset;
                boolean z = true;
                boolean z2 = i4 < i5 && this.returnCalled[i4];
                boolean z3 = i4 < i5 && this.normalCompletion[i4];
                PsiElement element = this.val$flow.getElement(i3);
                if ((!(instruction instanceof GoToInstruction) || !((GoToInstruction) instruction).isReturn) && !ControlFlowUtil.isInsideReturnStatement(element)) {
                    if (instruction instanceof ConditionalThrowToInstruction) {
                        int i6 = ((ConditionalThrowToInstruction) instruction).offset;
                        int i7 = this.val$endOffset;
                        z3 = (i6 < i7 && this.normalCompletion[i6]) || (i3 == i7 - 1 || this.normalCompletion[i3 + 1]);
                    } else if (!(instruction instanceof ThrowToInstruction) && i4 >= this.val$endOffset) {
                        z3 = true;
                    }
                    z = z2;
                }
                boolean[] zArr = this.returnCalled;
                zArr[i3] = zArr[i3] | z;
                boolean[] zArr2 = this.normalCompletion;
                zArr2[i3] = zArr2[i3] | z3;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
            public Integer getResult() {
                boolean[] zArr = this.returnCalled;
                int i3 = this.val$offset;
                Integer numValueOf = Integer.valueOf((zArr[i3] ? 2 : 0) | (this.normalCompletion[i3] ? 1 : 0));
                if (numValueOf == null) {
                    $$$reportNull$$$0(0);
                }
                return numValueOf;
            }
        };
        depthFirstSearch(controlFlow, instructionClientVisitor, i, i2);
        return instructionClientVisitor.getResult().intValue();
    }

    public static Collection<VariableInfo> getInitializedTwice(ControlFlow controlFlow) {
        if (controlFlow == null) {
            $$$reportNull$$$0(98);
        }
        return getInitializedTwice(controlFlow, 0, controlFlow.getSize());
    }

    public static Collection<VariableInfo> getInitializedTwice(ControlFlow controlFlow, int i, int i2) {
        if (controlFlow == null) {
            $$$reportNull$$$0(99);
        }
        while (i < i2) {
            InitializedTwiceClientVisitor initializedTwiceClientVisitor = new InitializedTwiceClientVisitor(controlFlow, i);
            depthFirstSearch(controlFlow, initializedTwiceClientVisitor, i, i2);
            Collection<VariableInfo> result = initializedTwiceClientVisitor.getResult();
            if (!result.isEmpty()) {
                if (result == null) {
                    $$$reportNull$$$0(100);
                }
                return result;
            }
            i = findUnprocessed(i, i2, initializedTwiceClientVisitor);
        }
        List listEmptyList = Collections.emptyList();
        if (listEmptyList == null) {
            $$$reportNull$$$0(101);
        }
        return listEmptyList;
    }

    private static class InitializedTwiceClientVisitor extends InstructionClientVisitor<Collection<VariableInfo>> {
        private final ControlFlow myFlow;
        private final int myStartOffset;
        private final CopyOnWriteList[] writtenTwiceVariables;
        private final CopyOnWriteList[] writtenVariables;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 3 || i == 4) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] objArr = new Object[(i == 3 || i == 4) ? 2 : 3];
            if (i == 1) {
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
            } else if (i == 2) {
                objArr[0] = "variable";
            } else if (i == 3 || i == 4) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$InitializedTwiceClientVisitor";
            } else {
                objArr[0] = "flow";
            }
            if (i == 3 || i == 4) {
                objArr[1] = "getResult";
            } else {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$InitializedTwiceClientVisitor";
            }
            if (i == 1) {
                objArr[2] = "getExpression";
            } else if (i == 2) {
                objArr[2] = "getLatestWriteVarExpression";
            } else if (i != 3 && i != 4) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i != 3 && i != 4) {
                throw new IllegalArgumentException(str2);
            }
            throw new IllegalStateException(str2);
        }

        InitializedTwiceClientVisitor(ControlFlow controlFlow, int i) {
            if (controlFlow == null) {
                $$$reportNull$$$0(0);
            }
            this.myFlow = controlFlow;
            this.myStartOffset = i;
            this.writtenVariables = new CopyOnWriteList[controlFlow.getSize() + 1];
            this.writtenTwiceVariables = new CopyOnWriteList[controlFlow.getSize() + 1];
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
        public void visitInstruction(Instruction instruction, int i, int i2) {
            int iMin = Math.min(i2, this.myFlow.getSize());
            CopyOnWriteList copyOnWriteListAdd = this.writtenVariables[iMin];
            CopyOnWriteList copyOnWriteListAdd2 = this.writtenTwiceVariables[iMin];
            if (instruction instanceof WriteVariableInstruction) {
                PsiVariable psiVariable = ((WriteVariableInstruction) instruction).variable;
                PsiElement latestWriteVarExpression = getLatestWriteVarExpression(copyOnWriteListAdd, psiVariable);
                if (latestWriteVarExpression == null) {
                    copyOnWriteListAdd = CopyOnWriteList.add(copyOnWriteListAdd, new VariableInfo(psiVariable, getExpression(this.myFlow.getElement(i))));
                } else {
                    copyOnWriteListAdd2 = CopyOnWriteList.add(copyOnWriteListAdd2, new VariableInfo(psiVariable, latestWriteVarExpression));
                }
            }
            ControlFlowUtil.merge(i, copyOnWriteListAdd, this.writtenVariables);
            ControlFlowUtil.merge(i, copyOnWriteListAdd2, this.writtenTwiceVariables);
        }

        private static PsiElement getExpression(PsiElement psiElement) {
            if (psiElement == null) {
                $$$reportNull$$$0(1);
            }
            if (psiElement instanceof PsiAssignmentExpression) {
                return (PsiElement) ObjectUtils.tryCast(PsiUtil.skipParenthesizedExprDown(((PsiAssignmentExpression) psiElement).getLExpression()), PsiReferenceExpression.class);
            }
            if (psiElement instanceof PsiUnaryExpression) {
                return (PsiElement) ObjectUtils.tryCast(PsiUtil.skipParenthesizedExprDown(((PsiUnaryExpression) psiElement).getOperand()), PsiReferenceExpression.class);
            }
            if (psiElement instanceof PsiDeclarationStatement) {
                return psiElement;
            }
            return null;
        }

        private static PsiElement getLatestWriteVarExpression(CopyOnWriteList copyOnWriteList, PsiVariable psiVariable) {
            if (psiVariable == null) {
                $$$reportNull$$$0(2);
            }
            if (copyOnWriteList == null) {
                return null;
            }
            PsiManager manager = psiVariable.getManager();
            for (VariableInfo variableInfo : copyOnWriteList.getList()) {
                if (manager.areElementsEquivalent(variableInfo.variable, psiVariable)) {
                    return variableInfo.expression;
                }
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
        public Collection<VariableInfo> getResult() {
            CopyOnWriteList copyOnWriteList = this.writtenTwiceVariables[this.myStartOffset];
            if (copyOnWriteList == null) {
                List listEmptyList = Collections.emptyList();
                if (listEmptyList == null) {
                    $$$reportNull$$$0(3);
                }
                return listEmptyList;
            }
            List<VariableInfo> list = copyOnWriteList.getList();
            if (list == null) {
                $$$reportNull$$$0(4);
            }
            return list;
        }
    }

    public static Map<PsiElement, PsiVariable> getWritesBeforeReads(ControlFlow controlFlow, Set<? extends PsiVariable> set, Set<? extends PsiVariable> set2, int i) {
        if (controlFlow == null) {
            $$$reportNull$$$0(102);
        }
        if (set == null) {
            $$$reportNull$$$0(103);
        }
        if (set2 == null) {
            $$$reportNull$$$0(104);
        }
        HashMap map = new HashMap();
        List<Instruction> instructions = controlFlow.getInstructions();
        for (int i2 = 0; i2 < instructions.size(); i2++) {
            Instruction instruction = instructions.get(i2);
            if (instruction instanceof WriteVariableInstruction) {
                PsiVariable psiVariable = ((WriteVariableInstruction) instruction).variable;
                if (set.contains(psiVariable) && readBeforeStopPoint(controlFlow, set2, i2, i)) {
                    map.put(controlFlow.getElement(i2), psiVariable);
                }
            }
        }
        return map;
    }

    private static boolean readBeforeStopPoint(ControlFlow controlFlow, final Set<? extends PsiVariable> set, int i, final int i2) {
        if (controlFlow == null) {
            $$$reportNull$$$0(106);
        }
        if (set == null) {
            $$$reportNull$$$0(107);
        }
        InstructionClientVisitor<Boolean> instructionClientVisitor = new InstructionClientVisitor<Boolean>() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.9MyVisitor
            private boolean reachable = false;

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitInstruction(Instruction instruction, int i3, int i4) {
                boolean z = false;
                if (i3 == i2 || isWriteToReadVar(instruction)) {
                    this.reachable = false;
                    return;
                }
                if ((instruction instanceof ReadVariableInstruction) && set.contains(((ReadVariableInstruction) instruction).variable)) {
                    z = true;
                }
                this.reachable |= z;
            }

            private boolean isWriteToReadVar(Instruction instruction) {
                return (instruction instanceof WriteVariableInstruction) && set.contains(((WriteVariableInstruction) instruction).variable);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
            public Boolean getResult() {
                return Boolean.valueOf(this.reachable);
            }
        };
        depthFirstSearch(controlFlow, instructionClientVisitor, i, controlFlow.getSize());
        return instructionClientVisitor.getResult().booleanValue();
    }

    public static boolean isInstructionReachable(ControlFlow controlFlow, int i, int i2) {
        if (controlFlow == null) {
            $$$reportNull$$$0(108);
        }
        return areInstructionsReachable(controlFlow, new int[]{i}, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean areInstructionsReachable(ControlFlow controlFlow, final int[] iArr, int i) {
        if (controlFlow == null) {
            $$$reportNull$$$0(109);
        }
        if (iArr == null) {
            $$$reportNull$$$0(110);
        }
        if (i != 0 && hasCalls(controlFlow)) {
            return areInstructionsReachableWithCalls(controlFlow, iArr, i);
        }
        InstructionClientVisitor<Boolean> instructionClientVisitor = new InstructionClientVisitor<Boolean>() { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.10MyVisitor
            private boolean reachable;

            private static /* synthetic */ void $$$reportNull$$$0(int i2) {
                throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$10MyVisitor", "getResult"));
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
            public void visitInstruction(Instruction instruction, int i2, int i3) {
                this.reachable |= ArrayUtil.indexOf(iArr, i3) >= 0;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
            public Boolean getResult() {
                Boolean boolValueOf = Boolean.valueOf(this.reachable);
                if (boolValueOf == null) {
                    $$$reportNull$$$0(0);
                }
                return boolValueOf;
            }
        };
        depthFirstSearch(controlFlow, instructionClientVisitor, i, controlFlow.getSize());
        return instructionClientVisitor.getResult().booleanValue();
    }

    private static boolean hasCalls(ControlFlow controlFlow) {
        if (controlFlow == null) {
            $$$reportNull$$$0(111);
        }
        Iterator<Instruction> it = controlFlow.getInstructions().iterator();
        while (it.hasNext()) {
            if (it.next() instanceof CallInstruction) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static abstract class ControlFlowGraph extends InstructionClientVisitor<Void> {
        final int[][] nextOffsets;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = (i == 1 || i == 2) ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[(i == 1 || i == 2) ? 3 : 2];
            if (i == 1) {
                objArr[0] = "visitedOffsets";
            } else if (i != 2) {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$ControlFlowGraph";
            } else {
                objArr[0] = "flow";
            }
            if (i == 1 || i == 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/psi/controlFlow/ControlFlowUtil$ControlFlowGraph";
            } else {
                objArr[1] = "getNextOffsets";
            }
            if (i == 1) {
                objArr[2] = "depthFirstSearch";
            } else if (i == 2) {
                objArr[2] = "buildFrom";
            }
            String str2 = String.format(str, objArr);
            if (i != 1 && i != 2) {
                throw new IllegalStateException(str2);
            }
            throw new IllegalArgumentException(str2);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.InstructionClientVisitor
        public Void getResult() {
            return null;
        }

        boolean isComplete(int i, int i2) {
            return false;
        }

        ControlFlowGraph(int i) {
            this.nextOffsets = new int[i][];
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowInstructionVisitor
        public void visitInstruction(Instruction instruction, int i, int i2) {
            if (i2 > size()) {
                i2 = size();
            }
            addArc(i, i2);
        }

        void addArc(int i, int i2) {
            int[][] iArr = this.nextOffsets;
            int[] iArr2 = iArr[i];
            if (iArr2 == null) {
                iArr[i] = new int[]{i2, -1};
                return;
            }
            if (ArrayUtil.indexOf(iArr2, i2) < 0) {
                int iIndexOf = ArrayUtil.indexOf(iArr2, -1);
                if (iIndexOf >= 0) {
                    iArr2[iIndexOf] = i2;
                    return;
                }
                int length = iArr2.length;
                int[][] iArr3 = this.nextOffsets;
                int[] iArrRealloc = ArrayUtil.realloc(iArr2, (length * 3) / 2);
                iArr3[i] = iArrRealloc;
                Arrays.fill(iArrRealloc, length, iArrRealloc.length, -1);
                iArrRealloc[length] = i2;
            }
        }

        int[] getNextOffsets(int i) {
            int[] iArr = this.nextOffsets[i];
            if (iArr == null) {
                iArr = ArrayUtilRt.EMPTY_INT_ARRAY;
            }
            if (iArr == null) {
                $$$reportNull$$$0(0);
            }
            return iArr;
        }

        int size() {
            return this.nextOffsets.length;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (true) {
                int[][] iArr = this.nextOffsets;
                if (i < iArr.length) {
                    int[] iArr2 = iArr[i];
                    if (iArr2 != null && iArr2.length != 0 && iArr2[0] != -1) {
                        if (sb.length() != 0) {
                            sb.append(StringUtil.SPACE);
                        }
                        sb.append('(').append(i).append("->");
                        for (int i2 = 0; i2 < iArr2.length && iArr2[i2] != -1; i2++) {
                            if (i2 != 0) {
                                sb.append(",");
                            }
                            sb.append(iArr2[i2]);
                        }
                        sb.append(')');
                    }
                    i++;
                } else {
                    return sb.toString();
                }
            }
        }

        boolean depthFirstSearch(int i) {
            return depthFirstSearch(i, new BitSet(size()));
        }

        boolean depthFirstSearch(int i, BitSet bitSet) {
            if (bitSet == null) {
                $$$reportNull$$$0(1);
            }
            IntStack intStack = new IntStack(Math.max(size() / 2, 2));
            bitSet.clear();
            intStack.push(i);
            while (true) {
                if (intStack.empty()) {
                    return false;
                }
                int iPop = intStack.pop();
                if (iPop < size() && !bitSet.get(iPop)) {
                    bitSet.set(iPop);
                    for (int i2 : getNextOffsets(iPop)) {
                        if (i2 == -1) {
                            break;
                        }
                        if (isComplete(iPop, i2)) {
                            return true;
                        }
                        intStack.push(i2);
                    }
                }
            }
        }

        void buildFrom(ControlFlow controlFlow) {
            if (controlFlow == null) {
                $$$reportNull$$$0(2);
            }
            ControlFlowUtil.depthFirstSearch(controlFlow, this, 0, controlFlow.getSize());
        }
    }

    private static boolean areInstructionsReachableWithCalls(ControlFlow controlFlow, final int[] iArr, int i) {
        if (controlFlow == null) {
            $$$reportNull$$$0(112);
        }
        if (iArr == null) {
            $$$reportNull$$$0(113);
        }
        ControlFlowGraph controlFlowGraph = new ControlFlowGraph(controlFlow.getSize()) { // from class: org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.5
            @Override // org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowUtil.ControlFlowGraph
            boolean isComplete(int i2, int i3) {
                return ArrayUtil.indexOf(iArr, i3) >= 0;
            }
        };
        controlFlowGraph.buildFrom(controlFlow);
        return controlFlowGraph.depthFirstSearch(i);
    }

    public static boolean isVariableAssignedInLoop(PsiReferenceExpression psiReferenceExpression, PsiElement psiElement) {
        PsiElement variableCodeBlock;
        if (psiReferenceExpression == null) {
            $$$reportNull$$$0(114);
        }
        if (psiElement == null) {
            $$$reportNull$$$0(115);
        }
        if (!(psiReferenceExpression.getMyParent() instanceof PsiAssignmentExpression) || ((PsiAssignmentExpression) psiReferenceExpression.getMyParent()).getLExpression() != psiReferenceExpression) {
            return false;
        }
        PsiExpression qualifierExpression = psiReferenceExpression.getQualifierExpression();
        if ((qualifierExpression != null && !(qualifierExpression instanceof PsiThisExpression)) || !(psiElement instanceof PsiVariable) || (variableCodeBlock = PsiUtil.getVariableCodeBlock((PsiVariable) psiElement, psiReferenceExpression)) == null) {
            return false;
        }
        try {
            ControlFlow controlFlow = ControlFlowFactory.getInstance(variableCodeBlock.getProject()).getControlFlow(variableCodeBlock, (ControlFlowPolicy) LocalsOrMyInstanceFieldsControlFlowPolicy.getInstance(), true);
            int startOffset = controlFlow.getStartOffset((PsiAssignmentExpression) psiReferenceExpression.getMyParent());
            return startOffset != -1 && isInstructionReachable(controlFlow, startOffset, startOffset);
        } catch (AnalysisCanceledException unused) {
            return false;
        }
    }

    static boolean isCaughtExceptionType(PsiClassType psiClassType, PsiType psiType) {
        if (psiClassType == null) {
            $$$reportNull$$$0(116);
        }
        if (psiType == null) {
            $$$reportNull$$$0(117);
        }
        return psiType.isAssignableFrom(psiClassType) || mightBeAssignableFromSubclass(psiClassType, psiType);
    }

    private static boolean mightBeAssignableFromSubclass(PsiClassType psiClassType, PsiType psiType) {
        if (psiClassType == null) {
            $$$reportNull$$$0(118);
        }
        if (psiType == null) {
            $$$reportNull$$$0(119);
        }
        if (psiType instanceof PsiDisjunctionType) {
            Iterator<PsiType> it = ((PsiDisjunctionType) psiType).getDisjunctions().iterator();
            while (it.hasNext()) {
                if (psiClassType.isAssignableFrom(it.next())) {
                    return true;
                }
            }
            return false;
        }
        return psiClassType.isAssignableFrom(psiType);
    }

    public static boolean areVariablesUnmodifiedAtLocations(ControlFlow controlFlow, int i, int i2, Set<? extends PsiVariable> set, Iterable<? extends PsiElement> iterable) {
        if (controlFlow == null) {
            $$$reportNull$$$0(120);
        }
        if (set == null) {
            $$$reportNull$$$0(121);
        }
        if (iterable == null) {
            $$$reportNull$$$0(122);
        }
        List<Instruction> instructions = controlFlow.getInstructions();
        int iMax = Math.max(i, 0);
        int iMin = Math.min(i2, instructions.size());
        IntArrayList intArrayList = new IntArrayList();
        Iterator<? extends PsiElement> it = iterable.iterator();
        while (it.hasNext()) {
            int startOffset = controlFlow.getStartOffset(it.next());
            if (startOffset >= iMax && startOffset < iMin) {
                intArrayList.add(startOffset);
            }
        }
        int[] intArray = intArrayList.toIntArray();
        while (iMax < iMin) {
            Instruction instruction = instructions.get(iMax);
            if ((instruction instanceof WriteVariableInstruction) && set.contains(((WriteVariableInstruction) instruction).variable) && areInstructionsReachable(controlFlow, intArray, iMax)) {
                return false;
            }
            iMax++;
        }
        return true;
    }
}
