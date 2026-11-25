package org.eclipse.jdt.internal.compiler.ast;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FinallyFlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.problem.ProblemReporter;
import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: loaded from: classes14.dex */
public class FakedTrackingVariable extends LocalDeclaration {
    private static final int CLOSED_IN_NESTED_METHOD = 8;
    private static final int CLOSE_SEEN = 1;
    private static final int OWNED_BY_OUTSIDE = 4;
    private static final int REPORTED_DEFINITIVE_LEAK = 64;
    private static final int REPORTED_EXPLICIT_CLOSE = 16;
    private static final int REPORTED_POTENTIAL_LEAK = 32;
    private static final int SHARED_WITH_OUTSIDE = 2;
    private ASTNode currentAssignment;
    private int globalClosingState;
    public FakedTrackingVariable innerTracker;
    MethodScope methodScope;
    public LocalVariableBinding originalBinding;
    public FakedTrackingVariable outerTracker;
    private HashMap recordedLocations;
    private FlowContext tryContext;
    private static final char[] UNASSIGNED_CLOSEABLE_NAME = "<unassigned Closeable value>".toCharArray();
    private static final char[] UNASSIGNED_CLOSEABLE_NAME_TEMPLATE = "<unassigned Closeable value from line {0}>".toCharArray();
    private static final char[] TEMPLATE_ARGUMENT = "{0}".toCharArray();
    public static boolean TEST_372319 = false;

    @Override // org.eclipse.jdt.internal.compiler.ast.LocalDeclaration, org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
    }

    public FakedTrackingVariable(LocalVariableBinding localVariableBinding, ASTNode aSTNode, FlowInfo flowInfo, FlowContext flowContext, int i) {
        super(localVariableBinding.f628name, aSTNode.sourceStart, aSTNode.sourceEnd);
        this.globalClosingState = 0;
        this.type = new SingleTypeReference(TypeConstants.OBJECT, (((long) this.sourceStart) << 32) + ((long) this.sourceEnd));
        this.methodScope = localVariableBinding.declaringScope.methodScope();
        this.originalBinding = localVariableBinding;
        while (flowContext != null) {
            if (flowContext instanceof FinallyFlowContext) {
                this.tryContext = ((FinallyFlowContext) flowContext).tryContext;
                break;
            }
            flowContext = flowContext.parent;
        }
        resolve(localVariableBinding.declaringScope);
        if (i != 0) {
            flowInfo.markNullStatus(this.binding, i);
        }
    }

    private FakedTrackingVariable(BlockScope blockScope, ASTNode aSTNode, FlowInfo flowInfo, int i) {
        super(UNASSIGNED_CLOSEABLE_NAME, aSTNode.sourceStart, aSTNode.sourceEnd);
        this.globalClosingState = 0;
        this.type = new SingleTypeReference(TypeConstants.OBJECT, (((long) this.sourceStart) << 32) + ((long) this.sourceEnd));
        this.methodScope = blockScope.methodScope();
        this.originalBinding = null;
        resolve(blockScope);
        if (i != 0) {
            flowInfo.markNullStatus(this.binding, i);
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.LocalDeclaration, org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration, org.eclipse.jdt.internal.compiler.ast.Statement
    public void resolve(BlockScope blockScope) {
        this.binding = new LocalVariableBinding(this.f617name, (TypeBinding) blockScope.getJavaLangObject(), 0, false);
        this.binding.closeTracker = this;
        this.binding.declaringScope = blockScope;
        this.binding.setConstant(Constant.NotAConstant);
        this.binding.useFlag = 1;
        this.binding.f627id = blockScope.registerTrackingVariable(this);
    }

    public static FakedTrackingVariable getCloseTrackingVariable(Expression expression, FlowInfo flowInfo, FlowContext flowContext) {
        while (true) {
            if (expression instanceof CastExpression) {
                expression = ((CastExpression) expression).expression;
            } else {
                if (!(expression instanceof Assignment)) {
                    break;
                }
                expression = ((Assignment) expression).expression;
            }
        }
        if (expression instanceof ConditionalExpression) {
            ConditionalExpression conditionalExpression = (ConditionalExpression) expression;
            FakedTrackingVariable closeTrackingVariable = getCloseTrackingVariable(conditionalExpression.valueIfFalse, flowInfo, flowContext);
            return closeTrackingVariable != null ? closeTrackingVariable : getCloseTrackingVariable(conditionalExpression.valueIfTrue, flowInfo, flowContext);
        }
        if (expression instanceof SingleNameReference) {
            SingleNameReference singleNameReference = (SingleNameReference) expression;
            if (singleNameReference.binding instanceof LocalVariableBinding) {
                LocalVariableBinding localVariableBinding = (LocalVariableBinding) singleNameReference.binding;
                if (localVariableBinding.closeTracker != null) {
                    return localVariableBinding.closeTracker;
                }
                if (!isAnyCloseable(expression.resolvedType) || (localVariableBinding.tagBits & 8192) != 0) {
                    return null;
                }
                localVariableBinding.closeTracker = new FakedTrackingVariable(localVariableBinding, localVariableBinding.declaration, flowInfo, flowContext, 1);
                if (localVariableBinding.isParameter()) {
                    localVariableBinding.closeTracker.globalClosingState |= 4;
                }
                return localVariableBinding.closeTracker;
            }
        } else if (expression instanceof AllocationExpression) {
            return ((AllocationExpression) expression).closeTracker;
        }
        return null;
    }

    public static void preConnectTrackerAcrossAssignment(ASTNode aSTNode, LocalVariableBinding localVariableBinding, Expression expression, FlowInfo flowInfo) {
        if (containsAllocation(expression)) {
            FakedTrackingVariable fakedTrackingVariable = localVariableBinding.closeTracker;
            if (fakedTrackingVariable == null && expression.resolvedType != TypeBinding.NULL) {
                fakedTrackingVariable = new FakedTrackingVariable(localVariableBinding, aSTNode, flowInfo, null, 1);
                if (localVariableBinding.isParameter()) {
                    fakedTrackingVariable.globalClosingState |= 4;
                }
            }
            if (fakedTrackingVariable != null) {
                fakedTrackingVariable.currentAssignment = aSTNode;
                preConnectTrackerAcrossAssignment(aSTNode, localVariableBinding, flowInfo, fakedTrackingVariable, expression);
            }
        }
    }

    private static boolean containsAllocation(ASTNode aSTNode) {
        if (aSTNode instanceof AllocationExpression) {
            return true;
        }
        if (aSTNode instanceof ConditionalExpression) {
            ConditionalExpression conditionalExpression = (ConditionalExpression) aSTNode;
            return containsAllocation(conditionalExpression.valueIfTrue) || containsAllocation(conditionalExpression.valueIfFalse);
        }
        if (aSTNode instanceof CastExpression) {
            return containsAllocation(((CastExpression) aSTNode).expression);
        }
        return false;
    }

    private static void preConnectTrackerAcrossAssignment(ASTNode aSTNode, LocalVariableBinding localVariableBinding, FlowInfo flowInfo, FakedTrackingVariable fakedTrackingVariable, Expression expression) {
        if (expression instanceof AllocationExpression) {
            preConnectTrackerAcrossAssignment(aSTNode, localVariableBinding, flowInfo, (AllocationExpression) expression, fakedTrackingVariable);
        } else if (expression instanceof ConditionalExpression) {
            preConnectTrackerAcrossAssignment(aSTNode, localVariableBinding, flowInfo, (ConditionalExpression) expression, fakedTrackingVariable);
        } else if (expression instanceof CastExpression) {
            preConnectTrackerAcrossAssignment(aSTNode, localVariableBinding, ((CastExpression) expression).expression, flowInfo);
        }
    }

    private static void preConnectTrackerAcrossAssignment(ASTNode aSTNode, LocalVariableBinding localVariableBinding, FlowInfo flowInfo, ConditionalExpression conditionalExpression, FakedTrackingVariable fakedTrackingVariable) {
        preConnectTrackerAcrossAssignment(aSTNode, localVariableBinding, flowInfo, fakedTrackingVariable, conditionalExpression.valueIfFalse);
        preConnectTrackerAcrossAssignment(aSTNode, localVariableBinding, flowInfo, fakedTrackingVariable, conditionalExpression.valueIfTrue);
    }

    private static void preConnectTrackerAcrossAssignment(ASTNode aSTNode, LocalVariableBinding localVariableBinding, FlowInfo flowInfo, AllocationExpression allocationExpression, FakedTrackingVariable fakedTrackingVariable) {
        allocationExpression.closeTracker = fakedTrackingVariable;
        if (allocationExpression.arguments == null || allocationExpression.arguments.length <= 0) {
            return;
        }
        preConnectTrackerAcrossAssignment(aSTNode, localVariableBinding, allocationExpression.arguments[0], flowInfo);
    }

    /* JADX WARN: Code duplicated, block: B:32:0x0069  */
    /* JADX WARN: Code duplicated, block: B:35:0x0082 A[ADDED_TO_REGION, LOOP:1: B:35:0x0082->B:37:0x0085, LOOP_START, PHI: r0
      0x0082: PHI (r0v17 org.eclipse.jdt.internal.compiler.ast.FakedTrackingVariable) = 
      (r0v16 org.eclipse.jdt.internal.compiler.ast.FakedTrackingVariable)
      (r0v18 org.eclipse.jdt.internal.compiler.ast.FakedTrackingVariable)
     binds: [B:34:0x0080, B:37:0x0085] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:37:0x0085 A[LOOP:1: B:35:0x0082->B:37:0x0085, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:38:0x0096 A[ORIG_RETURN, RETURN] */
    public static void analyseCloseableAllocation(BlockScope blockScope, FlowInfo flowInfo, AllocationExpression allocationExpression) {
        int iNullStatus;
        if (((ReferenceBinding) allocationExpression.resolvedType).hasTypeBit(8)) {
            if (allocationExpression.closeTracker != null) {
                allocationExpression.closeTracker.withdraw();
                allocationExpression.closeTracker = null;
                return;
            }
            return;
        }
        if (((ReferenceBinding) allocationExpression.resolvedType).hasTypeBit(4)) {
            if (allocationExpression.arguments != null && allocationExpression.arguments.length > 0) {
                FakedTrackingVariable fakedTrackingVariableFindCloseTracker = findCloseTracker(blockScope, flowInfo, allocationExpression.arguments[0]);
                if (fakedTrackingVariableFindCloseTracker != null) {
                    FakedTrackingVariable fakedTrackingVariable = fakedTrackingVariableFindCloseTracker;
                    while (fakedTrackingVariable != allocationExpression.closeTracker) {
                        fakedTrackingVariable = fakedTrackingVariable.innerTracker;
                        if (fakedTrackingVariable == null) {
                            if (allocationExpression.closeTracker == null) {
                                allocationExpression.closeTracker = new FakedTrackingVariable(blockScope, allocationExpression, flowInfo, 1);
                            } else {
                                if (blockScope.finallyInfo == null || (iNullStatus = blockScope.finallyInfo.nullStatus(allocationExpression.closeTracker.binding)) == 1) {
                                }
                                if (allocationExpression.closeTracker.innerTracker != null) {
                                    fakedTrackingVariableFindCloseTracker = pickMoreUnsafe(allocationExpression.closeTracker.innerTracker, fakedTrackingVariableFindCloseTracker, blockScope, flowInfo);
                                }
                                allocationExpression.closeTracker.innerTracker = fakedTrackingVariableFindCloseTracker;
                                fakedTrackingVariableFindCloseTracker.outerTracker = allocationExpression.closeTracker;
                                flowInfo.markNullStatus(allocationExpression.closeTracker.binding, iNullStatus);
                                if (iNullStatus != 2) {
                                    while (fakedTrackingVariableFindCloseTracker != null) {
                                        flowInfo.markNullStatus(fakedTrackingVariableFindCloseTracker.binding, iNullStatus);
                                        fakedTrackingVariableFindCloseTracker.globalClosingState |= allocationExpression.closeTracker.globalClosingState;
                                        fakedTrackingVariableFindCloseTracker = fakedTrackingVariableFindCloseTracker.innerTracker;
                                    }
                                    return;
                                }
                                return;
                            }
                            iNullStatus = 2;
                            if (allocationExpression.closeTracker.innerTracker != null) {
                                fakedTrackingVariableFindCloseTracker = pickMoreUnsafe(allocationExpression.closeTracker.innerTracker, fakedTrackingVariableFindCloseTracker, blockScope, flowInfo);
                            }
                            allocationExpression.closeTracker.innerTracker = fakedTrackingVariableFindCloseTracker;
                            fakedTrackingVariableFindCloseTracker.outerTracker = allocationExpression.closeTracker;
                            flowInfo.markNullStatus(allocationExpression.closeTracker.binding, iNullStatus);
                            if (iNullStatus != 2) {
                                while (fakedTrackingVariableFindCloseTracker != null) {
                                    flowInfo.markNullStatus(fakedTrackingVariableFindCloseTracker.binding, iNullStatus);
                                    fakedTrackingVariableFindCloseTracker.globalClosingState |= allocationExpression.closeTracker.globalClosingState;
                                    fakedTrackingVariableFindCloseTracker = fakedTrackingVariableFindCloseTracker.innerTracker;
                                }
                                return;
                            }
                            return;
                        }
                    }
                    return;
                }
                if (isAnyCloseable(allocationExpression.arguments[0].resolvedType)) {
                    if (allocationExpression.closeTracker != null) {
                        allocationExpression.closeTracker.withdraw();
                        allocationExpression.closeTracker = null;
                        return;
                    }
                    return;
                }
            }
            handleRegularResource(blockScope, flowInfo, allocationExpression);
            return;
        }
        handleRegularResource(blockScope, flowInfo, allocationExpression);
    }

    private static FakedTrackingVariable pickMoreUnsafe(FakedTrackingVariable fakedTrackingVariable, FakedTrackingVariable fakedTrackingVariable2, BlockScope blockScope, FlowInfo flowInfo) {
        int iNullStatus = flowInfo.nullStatus(fakedTrackingVariable.binding);
        int iNullStatus2 = flowInfo.nullStatus(fakedTrackingVariable2.binding);
        if (iNullStatus == 2 || iNullStatus2 == 4) {
            return pick(fakedTrackingVariable, fakedTrackingVariable2, blockScope);
        }
        if (iNullStatus == 4 || iNullStatus2 == 2) {
            return pick(fakedTrackingVariable2, fakedTrackingVariable, blockScope);
        }
        if ((iNullStatus & 16) != 0) {
            return pick(fakedTrackingVariable, fakedTrackingVariable2, blockScope);
        }
        if ((iNullStatus2 & 16) != 0) {
            return pick(fakedTrackingVariable2, fakedTrackingVariable, blockScope);
        }
        return pick(fakedTrackingVariable, fakedTrackingVariable2, blockScope);
    }

    private static FakedTrackingVariable pick(FakedTrackingVariable fakedTrackingVariable, FakedTrackingVariable fakedTrackingVariable2, BlockScope blockScope) {
        fakedTrackingVariable2.withdraw();
        return fakedTrackingVariable;
    }

    private static void handleRegularResource(BlockScope blockScope, FlowInfo flowInfo, AllocationExpression allocationExpression) {
        FakedTrackingVariable fakedTrackingVariable = allocationExpression.closeTracker;
        if (fakedTrackingVariable != null && fakedTrackingVariable.originalBinding != null) {
            int iNullStatus = flowInfo.nullStatus(fakedTrackingVariable.binding);
            if (iNullStatus != 4 && iNullStatus != 1 && !flowInfo.isDefinitelyNull(fakedTrackingVariable.originalBinding) && !(fakedTrackingVariable.currentAssignment instanceof LocalDeclaration)) {
                allocationExpression.closeTracker.recordErrorLocation(fakedTrackingVariable.currentAssignment, iNullStatus);
            }
        } else {
            allocationExpression.closeTracker = new FakedTrackingVariable(blockScope, allocationExpression, flowInfo, 1);
        }
        flowInfo.markAsDefinitelyNull(allocationExpression.closeTracker.binding);
    }

    private static FakedTrackingVariable findCloseTracker(BlockScope blockScope, FlowInfo flowInfo, Expression expression) {
        while (expression instanceof Assignment) {
            Assignment assignment = (Assignment) expression;
            LocalVariableBinding localVariableBinding = assignment.localVariableBinding();
            if (localVariableBinding != null) {
                return localVariableBinding.closeTracker;
            }
            expression = assignment.expression;
        }
        if (expression instanceof SingleNameReference) {
            LocalVariableBinding localVariableBinding2 = expression.localVariableBinding();
            if (localVariableBinding2 != null) {
                return localVariableBinding2.closeTracker;
            }
            return null;
        }
        if (expression instanceof AllocationExpression) {
            return ((AllocationExpression) expression).closeTracker;
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x002b  */
    /* JADX WARN: Code duplicated, block: B:16:0x0033  */
    /* JADX WARN: Code duplicated, block: B:18:0x0037  */
    /* JADX WARN: Code duplicated, block: B:20:0x003b  */
    /* JADX WARN: Code duplicated, block: B:23:0x0041  */
    /* JADX WARN: Code duplicated, block: B:24:0x0049  */
    /* JADX WARN: Code duplicated, block: B:28:0x0051 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:29:0x0053 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:30:0x0054  */
    /* JADX WARN: Code duplicated, block: B:32:0x0058  */
    /* JADX WARN: Code duplicated, block: B:35:0x006f  */
    /* JADX WARN: Code duplicated, block: B:36:0x0072 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:37:0x0074  */
    /* JADX WARN: Code duplicated, block: B:39:0x0078  */
    /* JADX WARN: Code duplicated, block: B:42:0x007c  */
    /* JADX WARN: Code duplicated, block: B:45:0x0081 A[LOOP:0: B:40:0x0079->B:45:0x0081, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:46:0x0084  */
    /* JADX WARN: Code duplicated, block: B:48:0x008a  */
    /* JADX WARN: Code duplicated, block: B:52:0x00a8  */
    /* JADX WARN: Code duplicated, block: B:54:0x00b9  */
    /* JADX WARN: Code duplicated, block: B:56:0x00c1  */
    /* JADX WARN: Code duplicated, block: B:58:0x00c8  */
    /* JADX WARN: Code duplicated, block: B:65:0x00e2  */
    /* JADX WARN: Code duplicated, block: B:67:0x0084 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:68:0x00c6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:69:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:71:? A[RETURN, SYNTHETIC] */
    public static void handleResourceAssignment(BlockScope blockScope, FlowInfo flowInfo, FlowInfo flowInfo2, FlowContext flowContext, ASTNode aSTNode, Expression expression, LocalVariableBinding localVariableBinding) {
        FakedTrackingVariable fakedTrackingVariable;
        FakedTrackingVariable fakedTrackingVariable2;
        FakedTrackingVariable fakedTrackingVariable3;
        int iNullStatus;
        FakedTrackingVariable closeTrackingVariable;
        FakedTrackingVariable fakedTrackingVariableAnalyseCloseableExpression;
        FlowContext flowContext2;
        if (localVariableBinding.closeTracker != null) {
            FakedTrackingVariable fakedTrackingVariable4 = localVariableBinding.closeTracker;
            int iNullStatus2 = flowInfo.nullStatus(localVariableBinding);
            if (iNullStatus2 == 2 || iNullStatus2 == 1) {
                fakedTrackingVariable = fakedTrackingVariable4;
                fakedTrackingVariable2 = null;
            } else {
                fakedTrackingVariable = fakedTrackingVariable4;
            }
            if (expression.resolvedType != TypeBinding.NULL) {
                closeTrackingVariable = getCloseTrackingVariable(expression, flowInfo2, flowContext);
                if (closeTrackingVariable != null) {
                    if (localVariableBinding.closeTracker == null) {
                        if (closeTrackingVariable.originalBinding != null) {
                            localVariableBinding.closeTracker = closeTrackingVariable;
                        }
                        if (closeTrackingVariable.currentAssignment == aSTNode) {
                            closeTrackingVariable.globalClosingState &= -7;
                        }
                    } else if ((expression instanceof AllocationExpression) && !(expression instanceof ConditionalExpression)) {
                        localVariableBinding.closeTracker = closeTrackingVariable;
                    } else {
                        if (closeTrackingVariable == fakedTrackingVariable2) {
                            return;
                        }
                        if (localVariableBinding.closeTracker != closeTrackingVariable && (closeTrackingVariable.globalClosingState & 4) != 0) {
                            localVariableBinding.closeTracker = new FakedTrackingVariable(localVariableBinding, aSTNode, flowInfo2, flowContext, 2);
                        } else {
                            localVariableBinding.closeTracker = closeTrackingVariable;
                        }
                    }
                } else if (fakedTrackingVariable != null) {
                    if (fakedTrackingVariable.tryContext != null) {
                        flowContext2 = flowContext;
                        while (true) {
                            if (flowContext2 == null) {
                                if (fakedTrackingVariable.tryContext == flowContext2) {
                                    flowContext2 = flowContext2.parent;
                                }
                            } else {
                                if ((fakedTrackingVariable.globalClosingState & 6) == 0 && flowInfo2.hasNullInfoFor(fakedTrackingVariable.binding)) {
                                    flowInfo2.markAsDefinitelyNull(fakedTrackingVariable.binding);
                                }
                                localVariableBinding.closeTracker = analyseCloseableExpression(flowInfo2, flowContext, localVariableBinding, aSTNode, expression, fakedTrackingVariable);
                            }
                        }
                    } else {
                        if ((fakedTrackingVariable.globalClosingState & 6) == 0) {
                            flowInfo2.markAsDefinitelyNull(fakedTrackingVariable.binding);
                        }
                        localVariableBinding.closeTracker = analyseCloseableExpression(flowInfo2, flowContext, localVariableBinding, aSTNode, expression, fakedTrackingVariable);
                    }
                } else {
                    fakedTrackingVariableAnalyseCloseableExpression = analyseCloseableExpression(flowInfo2, flowContext, localVariableBinding, aSTNode, expression, null);
                    if (fakedTrackingVariableAnalyseCloseableExpression != null) {
                        localVariableBinding.closeTracker = fakedTrackingVariableAnalyseCloseableExpression;
                        if ((fakedTrackingVariableAnalyseCloseableExpression.globalClosingState & 6) == 0) {
                            flowInfo2.markAsDefinitelyNull(fakedTrackingVariableAnalyseCloseableExpression.binding);
                        }
                    }
                }
            }
            if (fakedTrackingVariable2 != null) {
                fakedTrackingVariable3 = fakedTrackingVariable2.innerTracker;
                if (fakedTrackingVariable3 == null && fakedTrackingVariable3.binding.declaringScope == blockScope) {
                    fakedTrackingVariable2.innerTracker.outerTracker = null;
                    blockScope.pruneWrapperTrackingVar(fakedTrackingVariable2);
                    return;
                } else {
                    iNullStatus = flowInfo.nullStatus(fakedTrackingVariable2.binding);
                    if (iNullStatus != 4) {
                        fakedTrackingVariable2.recordErrorLocation(aSTNode, iNullStatus);
                    }
                }
            }
        }
        fakedTrackingVariable = null;
        fakedTrackingVariable2 = fakedTrackingVariable;
        if (expression.resolvedType != TypeBinding.NULL) {
            closeTrackingVariable = getCloseTrackingVariable(expression, flowInfo2, flowContext);
            if (closeTrackingVariable != null) {
                if (localVariableBinding.closeTracker == null) {
                    if (closeTrackingVariable.originalBinding != null) {
                        localVariableBinding.closeTracker = closeTrackingVariable;
                    }
                    if (closeTrackingVariable.currentAssignment == aSTNode) {
                        closeTrackingVariable.globalClosingState &= -7;
                    }
                } else if (expression instanceof AllocationExpression) {
                    if (closeTrackingVariable == fakedTrackingVariable2) {
                        return;
                    }
                    if (localVariableBinding.closeTracker != closeTrackingVariable) {
                        localVariableBinding.closeTracker = closeTrackingVariable;
                    } else {
                        localVariableBinding.closeTracker = closeTrackingVariable;
                    }
                } else {
                    if (closeTrackingVariable == fakedTrackingVariable2) {
                        return;
                    }
                    if (localVariableBinding.closeTracker != closeTrackingVariable) {
                        localVariableBinding.closeTracker = closeTrackingVariable;
                    } else {
                        localVariableBinding.closeTracker = closeTrackingVariable;
                    }
                }
            } else if (fakedTrackingVariable != null) {
                if (fakedTrackingVariable.tryContext != null) {
                    flowContext2 = flowContext;
                    while (true) {
                        if (flowContext2 == null) {
                            if (fakedTrackingVariable.tryContext == flowContext2) {
                                flowContext2 = flowContext2.parent;
                            }
                        } else {
                            if ((fakedTrackingVariable.globalClosingState & 6) == 0) {
                                flowInfo2.markAsDefinitelyNull(fakedTrackingVariable.binding);
                            }
                            localVariableBinding.closeTracker = analyseCloseableExpression(flowInfo2, flowContext, localVariableBinding, aSTNode, expression, fakedTrackingVariable);
                        }
                    }
                } else {
                    if ((fakedTrackingVariable.globalClosingState & 6) == 0) {
                        flowInfo2.markAsDefinitelyNull(fakedTrackingVariable.binding);
                    }
                    localVariableBinding.closeTracker = analyseCloseableExpression(flowInfo2, flowContext, localVariableBinding, aSTNode, expression, fakedTrackingVariable);
                }
            } else {
                fakedTrackingVariableAnalyseCloseableExpression = analyseCloseableExpression(flowInfo2, flowContext, localVariableBinding, aSTNode, expression, null);
                if (fakedTrackingVariableAnalyseCloseableExpression != null) {
                    localVariableBinding.closeTracker = fakedTrackingVariableAnalyseCloseableExpression;
                    if ((fakedTrackingVariableAnalyseCloseableExpression.globalClosingState & 6) == 0) {
                        flowInfo2.markAsDefinitelyNull(fakedTrackingVariableAnalyseCloseableExpression.binding);
                    }
                }
            }
        }
        if (fakedTrackingVariable2 != null) {
            fakedTrackingVariable3 = fakedTrackingVariable2.innerTracker;
            if (fakedTrackingVariable3 == null) {
            }
            iNullStatus = flowInfo.nullStatus(fakedTrackingVariable2.binding);
            if (iNullStatus != 4) {
                fakedTrackingVariable2.recordErrorLocation(aSTNode, iNullStatus);
            }
        }
    }

    private static FakedTrackingVariable analyseCloseableExpression(FlowInfo flowInfo, FlowContext flowContext, LocalVariableBinding localVariableBinding, ASTNode aSTNode, Expression expression, FakedTrackingVariable fakedTrackingVariable) {
        boolean z;
        while (true) {
            if (expression instanceof Assignment) {
                expression = ((Assignment) expression).expression;
            } else {
                if (!(expression instanceof CastExpression)) {
                    break;
                }
                expression = ((CastExpression) expression).expression;
            }
        }
        if (!(expression.resolvedType instanceof ReferenceBinding) || !((ReferenceBinding) expression.resolvedType).hasTypeBit(8)) {
            z = false;
        } else {
            if (!isBlacklistedMethod(expression)) {
                return null;
            }
            z = true;
        }
        if (expression instanceof AllocationExpression) {
            FakedTrackingVariable fakedTrackingVariable2 = ((AllocationExpression) expression).closeTracker;
            if (fakedTrackingVariable2 == null || fakedTrackingVariable2.originalBinding != null) {
                return fakedTrackingVariable2;
            }
            return null;
        }
        if ((expression instanceof MessageSend) || (expression instanceof ArrayReference)) {
            FakedTrackingVariable fakedTrackingVariable3 = new FakedTrackingVariable(localVariableBinding, aSTNode, flowInfo, flowContext, 16);
            if (!z) {
                fakedTrackingVariable3.globalClosingState |= 2;
            }
            return fakedTrackingVariable3;
        }
        if ((expression.bits & 7) == 1 || ((expression instanceof QualifiedNameReference) && ((QualifiedNameReference) expression).isFieldAccess())) {
            FakedTrackingVariable fakedTrackingVariable4 = new FakedTrackingVariable(localVariableBinding, aSTNode, flowInfo, flowContext, 1);
            fakedTrackingVariable4.globalClosingState |= 4;
            return fakedTrackingVariable4;
        }
        if (localVariableBinding.closeTracker != null) {
            return localVariableBinding.closeTracker;
        }
        FakedTrackingVariable fakedTrackingVariable5 = new FakedTrackingVariable(localVariableBinding, aSTNode, flowInfo, flowContext, 1);
        LocalVariableBinding localVariableBinding2 = expression.localVariableBinding();
        if (localVariableBinding2 != null && localVariableBinding2.isParameter()) {
            fakedTrackingVariable5.globalClosingState |= 4;
        }
        return fakedTrackingVariable5;
    }

    private static boolean isBlacklistedMethod(Expression expression) {
        MethodBinding methodBinding;
        if ((expression instanceof MessageSend) && (methodBinding = ((MessageSend) expression).binding) != null && methodBinding.isValidBinding()) {
            return CharOperation.equals(methodBinding.declaringClass.compoundName, TypeConstants.JAVA_NIO_FILE_FILES);
        }
        return false;
    }

    public static void cleanUpAfterAssignment(BlockScope blockScope, int i, Expression expression) {
        while (true) {
            if (expression instanceof Assignment) {
                expression = ((Assignment) expression).expression;
            } else if (!(expression instanceof CastExpression)) {
                break;
            } else {
                expression = ((CastExpression) expression).expression;
            }
        }
        if (expression instanceof AllocationExpression) {
            AllocationExpression allocationExpression = (AllocationExpression) expression;
            FakedTrackingVariable fakedTrackingVariable = allocationExpression.closeTracker;
            if (fakedTrackingVariable == null || fakedTrackingVariable.originalBinding != null) {
                return;
            }
            fakedTrackingVariable.withdraw();
            allocationExpression.closeTracker = null;
            return;
        }
        LocalVariableBinding localVariableBinding = expression.localVariableBinding();
        if (localVariableBinding == null || localVariableBinding.closeTracker == null || (i & 1) == 0) {
            return;
        }
        localVariableBinding.closeTracker.withdraw();
    }

    public static boolean isAnyCloseable(TypeBinding typeBinding) {
        return (typeBinding instanceof ReferenceBinding) && ((ReferenceBinding) typeBinding).hasTypeBit(3);
    }

    public int findMostSpecificStatus(FlowInfo flowInfo, BlockScope blockScope, BlockScope blockScope2) {
        int i = 1;
        for (FakedTrackingVariable fakedTrackingVariable = this; fakedTrackingVariable != null; fakedTrackingVariable = fakedTrackingVariable.innerTracker) {
            LocalVariableBinding localVariableBinding = fakedTrackingVariable.binding;
            int nullStatusAggressively = getNullStatusAggressively(localVariableBinding, flowInfo);
            if (blockScope2 != null) {
                nullStatusAggressively = mergeCloseStatus(blockScope2, nullStatusAggressively, localVariableBinding, blockScope);
            }
            if (nullStatusAggressively == 4) {
                return nullStatusAggressively;
            }
            if (i == 2 || i == 1) {
                i = nullStatusAggressively;
            }
        }
        return i;
    }

    private int getNullStatusAggressively(LocalVariableBinding localVariableBinding, FlowInfo flowInfo) {
        if (flowInfo == FlowInfo.DEAD_END) {
            return 1;
        }
        int iReachMode = flowInfo.reachMode();
        if (iReachMode != 0) {
            try {
                flowInfo.tagBits &= -4;
            } catch (Throwable th) {
                flowInfo.tagBits = iReachMode | flowInfo.tagBits;
                throw th;
            }
        }
        int iNullStatus = flowInfo.nullStatus(localVariableBinding);
        if (TEST_372319) {
            try {
                Thread.sleep(5L);
            } catch (InterruptedException unused) {
            }
        }
        flowInfo.tagBits = iReachMode | flowInfo.tagBits;
        if ((iNullStatus & 2) != 0) {
            return (iNullStatus & 36) != 0 ? 16 : 2;
        }
        if ((iNullStatus & 4) != 0) {
            return (iNullStatus & 16) != 0 ? 16 : 4;
        }
        if ((iNullStatus & 16) != 0) {
            return 16;
        }
        return iNullStatus;
    }

    public int mergeCloseStatus(BlockScope blockScope, int i, LocalVariableBinding localVariableBinding, BlockScope blockScope2) {
        if (i != 4) {
            if (blockScope.finallyInfo != null) {
                int iNullStatus = blockScope.finallyInfo.nullStatus(localVariableBinding);
                if (iNullStatus == 4) {
                    return iNullStatus;
                }
                if (iNullStatus != 2 && blockScope.finallyInfo.hasNullInfoFor(localVariableBinding)) {
                    i = 16;
                }
            }
            if (blockScope != blockScope2 && (blockScope.parent instanceof BlockScope)) {
                return mergeCloseStatus((BlockScope) blockScope.parent, i, localVariableBinding, blockScope2);
            }
        }
        return i;
    }

    public void markClose(FlowInfo flowInfo, FlowContext flowContext) {
        FakedTrackingVariable fakedTrackingVariable = this;
        do {
            flowInfo.markAsDefinitelyNonNull(fakedTrackingVariable.binding);
            fakedTrackingVariable.globalClosingState |= 1;
            flowContext.markFinallyNullStatus(fakedTrackingVariable.binding, 4);
            fakedTrackingVariable = fakedTrackingVariable.innerTracker;
        } while (fakedTrackingVariable != null);
    }

    public void markClosedInNestedMethod() {
        this.globalClosingState |= 8;
    }

    public static FlowInfo markPassedToOutside(BlockScope blockScope, Expression expression, FlowInfo flowInfo, FlowContext flowContext, boolean z) {
        FakedTrackingVariable closeTrackingVariable = getCloseTrackingVariable(expression, flowInfo, flowContext);
        if (closeTrackingVariable == null) {
            return flowInfo;
        }
        FlowInfo flowInfoCopy = z ? flowInfo : flowInfo.copy();
        int i = z ? 4 : 2;
        do {
            closeTrackingVariable.globalClosingState |= i;
            if (blockScope.methodScope() != closeTrackingVariable.methodScope) {
                closeTrackingVariable.globalClosingState |= 8;
            }
            flowInfoCopy.markAsDefinitelyNonNull(closeTrackingVariable.binding);
            closeTrackingVariable = closeTrackingVariable.innerTracker;
        } while (closeTrackingVariable != null);
        return z ? flowInfoCopy : FlowInfo.conditional(flowInfo, flowInfoCopy);
    }

    public static class IteratorForReporting implements Iterator<FakedTrackingVariable> {
        private static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$FakedTrackingVariable$IteratorForReporting$Stage;
        private final boolean atExit;
        private Iterator<FakedTrackingVariable> iterator;
        private FakedTrackingVariable next;
        private final Scope scope;
        private Stage stage;
        private final Set<FakedTrackingVariable> varSet;

        enum Stage {
            OuterLess,
            InnerOfProcessed,
            InnerOfNotEnclosing,
            AtExit;

            /* JADX INFO: renamed from: values, reason: to resolve conflict with enum method */
            public static Stage[] valuesCustom() {
                Stage[] stageArrValuesCustom = values();
                int length = stageArrValuesCustom.length;
                Stage[] stageArr = new Stage[length];
                System.arraycopy(stageArrValuesCustom, 0, stageArr, 0, length);
                return stageArr;
            }
        }

        static /* synthetic */ int[] $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$FakedTrackingVariable$IteratorForReporting$Stage() {
            int[] iArr = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$FakedTrackingVariable$IteratorForReporting$Stage;
            if (iArr != null) {
                return iArr;
            }
            int[] iArr2 = new int[Stage.valuesCustom().length];
            try {
                iArr2[Stage.AtExit.ordinal()] = 4;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr2[Stage.InnerOfNotEnclosing.ordinal()] = 3;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr2[Stage.InnerOfProcessed.ordinal()] = 2;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[Stage.OuterLess.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$FakedTrackingVariable$IteratorForReporting$Stage = iArr2;
            return iArr2;
        }

        public IteratorForReporting(List<FakedTrackingVariable> list, Scope scope, boolean z) {
            this.varSet = new HashSet(list);
            this.scope = scope;
            this.atExit = z;
            setUpForStage(Stage.OuterLess);
        }

        /* JADX WARN: Code duplicated, block: B:22:0x0053  */
        /* JADX WARN: Code duplicated, block: B:29:0x0076 A[ADDED_TO_REGION] */
        /* JADX WARN: Code duplicated, block: B:33:0x0081  */
        /* JADX WARN: Code duplicated, block: B:37:0x0096 A[ADDED_TO_REGION, ORIG_RETURN, RETURN] */
        /* JADX WARN: Code duplicated, block: B:50:0x0065 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:52:? A[LOOP:0: B:19:0x004a->B:52:?, LOOP_END, SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:53:0x0071 A[SYNTHETIC] */
        @Override // java.util.Iterator
        public boolean hasNext() {
            FakedTrackingVariable next;
            FakedTrackingVariable fakedTrackingVariable;
            BlockScope blockScope;
            Scope scope;
            int i = $SWITCH_TABLE$org$eclipse$jdt$internal$compiler$ast$FakedTrackingVariable$IteratorForReporting$Stage()[this.stage.ordinal()];
            if (i == 1) {
                while (this.iterator.hasNext()) {
                    FakedTrackingVariable next2 = this.iterator.next();
                    if (next2.outerTracker == null) {
                        return found(next2);
                    }
                }
                setUpForStage(Stage.InnerOfProcessed);
            } else {
                if (i != 2) {
                    if (i != 3) {
                        if (i != 4) {
                            throw new IllegalStateException("Unexpected Stage " + this.stage);
                        }
                    }
                    if (this.atExit || !this.iterator.hasNext()) {
                        return false;
                    }
                    return found(this.iterator.next());
                }
                while (this.iterator.hasNext()) {
                    next = this.iterator.next();
                    fakedTrackingVariable = next.outerTracker;
                    if (!this.varSet.contains(fakedTrackingVariable)) {
                        blockScope = fakedTrackingVariable.binding.declaringScope;
                        scope = this.scope;
                        do {
                            scope = scope.parent;
                            if (!(scope instanceof BlockScope)) {
                                return found(next);
                            }
                        } while (blockScope != scope);
                    }
                }
                setUpForStage(Stage.AtExit);
                if (this.atExit) {
                    return false;
                }
                return false;
            }
            while (this.iterator.hasNext()) {
                FakedTrackingVariable next3 = this.iterator.next();
                FakedTrackingVariable fakedTrackingVariable2 = next3.outerTracker;
                if (fakedTrackingVariable2.binding.declaringScope == this.scope && !this.varSet.contains(fakedTrackingVariable2)) {
                    return found(next3);
                }
            }
            setUpForStage(Stage.InnerOfNotEnclosing);
            while (this.iterator.hasNext()) {
                next = this.iterator.next();
                fakedTrackingVariable = next.outerTracker;
                if (!this.varSet.contains(fakedTrackingVariable)) {
                    blockScope = fakedTrackingVariable.binding.declaringScope;
                    scope = this.scope;
                    do {
                        scope = scope.parent;
                        if (!(scope instanceof BlockScope)) {
                            return found(next);
                        }
                    } while (blockScope != scope);
                }
            }
            setUpForStage(Stage.AtExit);
            if (this.atExit) {
                return false;
            }
            return false;
        }

        private boolean found(FakedTrackingVariable fakedTrackingVariable) {
            this.iterator.remove();
            this.next = fakedTrackingVariable;
            return true;
        }

        private void setUpForStage(Stage stage) {
            this.iterator = this.varSet.iterator();
            this.stage = stage;
        }

        @Override // java.util.Iterator
        public FakedTrackingVariable next() {
            return this.next;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public boolean hasDefinitelyNoResource(FlowInfo flowInfo) {
        LocalVariableBinding localVariableBinding = this.originalBinding;
        if (localVariableBinding == null) {
            return false;
        }
        if (flowInfo.isDefinitelyNull(localVariableBinding)) {
            return true;
        }
        return (flowInfo.isDefinitelyAssigned(this.originalBinding) || flowInfo.isPotentiallyAssigned(this.originalBinding)) ? false : true;
    }

    public boolean isClosedInFinallyOfEnclosing(BlockScope blockScope) {
        while (true) {
            if (blockScope.finallyInfo != null && blockScope.finallyInfo.isDefinitelyNonNull(this.binding)) {
                return true;
            }
            if (!(blockScope.parent instanceof BlockScope)) {
                return false;
            }
            blockScope = (BlockScope) blockScope.parent;
        }
    }

    public boolean isResourceBeingReturned(FakedTrackingVariable fakedTrackingVariable) {
        FakedTrackingVariable fakedTrackingVariable2 = this;
        while (fakedTrackingVariable2 != fakedTrackingVariable) {
            fakedTrackingVariable2 = fakedTrackingVariable2.innerTracker;
            if (fakedTrackingVariable2 == null) {
                return false;
            }
        }
        this.globalClosingState |= 64;
        return true;
    }

    public void withdraw() {
        this.binding.declaringScope.removeTrackingVar(this);
    }

    public void recordErrorLocation(ASTNode aSTNode, int i) {
        if ((this.globalClosingState & 4) != 0) {
            return;
        }
        if (this.recordedLocations == null) {
            this.recordedLocations = new HashMap();
        }
        this.recordedLocations.put(aSTNode, Integer.valueOf(i));
    }

    public boolean reportRecordedErrors(Scope scope, int i, boolean z) {
        FakedTrackingVariable fakedTrackingVariable = this;
        while (fakedTrackingVariable.globalClosingState == 0) {
            fakedTrackingVariable = fakedTrackingVariable.innerTracker;
            if (fakedTrackingVariable == null) {
                if (z && neverClosedAtLocations()) {
                    i = 2;
                }
                if ((i & 50) == 0) {
                    break;
                }
                reportError(scope.problemReporter(), null, i);
                return true;
            }
        }
        HashMap map = this.recordedLocations;
        int iReportError = 0;
        if (map == null) {
            return false;
        }
        boolean z2 = false;
        for (Map.Entry entry : map.entrySet()) {
            iReportError |= reportError(scope.problemReporter(), (ASTNode) entry.getKey(), ((Integer) entry.getValue()).intValue());
            z2 = true;
        }
        if (iReportError != 0) {
            FakedTrackingVariable fakedTrackingVariable2 = this;
            do {
                fakedTrackingVariable2.globalClosingState |= iReportError;
                fakedTrackingVariable2 = fakedTrackingVariable2.innerTracker;
            } while (fakedTrackingVariable2 != null);
        }
        return z2;
    }

    private boolean neverClosedAtLocations() {
        HashMap map = this.recordedLocations;
        if (map == null) {
            return true;
        }
        Iterator it = map.values().iterator();
        while (it.hasNext()) {
            if (!it.next().equals(2)) {
                return false;
            }
        }
        return true;
    }

    public int reportError(ProblemReporter problemReporter, ASTNode aSTNode, int i) {
        int i2 = this.globalClosingState;
        if ((i2 & 4) != 0) {
            return 0;
        }
        boolean z = true;
        if (i != 2 ? (i & 48) == 0 : (i2 & 8) == 0) {
            z = false;
        }
        if (z) {
            if ((i2 & 96) != 0) {
                return 0;
            }
            problemReporter.potentiallyUnclosedCloseable(this, aSTNode);
        } else {
            if ((i2 & 64) != 0) {
                return 0;
            }
            problemReporter.unclosedCloseable(this, aSTNode);
        }
        int i3 = z ? 32 : 64;
        if (aSTNode == null) {
            FakedTrackingVariable fakedTrackingVariable = this;
            do {
                fakedTrackingVariable.globalClosingState |= i3;
                fakedTrackingVariable = fakedTrackingVariable.innerTracker;
            } while (fakedTrackingVariable != null);
        }
        return i3;
    }

    public void reportExplicitClosing(ProblemReporter problemReporter) {
        int i = this.globalClosingState;
        if ((i & 20) == 0) {
            this.globalClosingState = i | 16;
            problemReporter.explicitlyClosedAutoCloseable(this);
        }
    }

    public String nameForReporting(ASTNode aSTNode, ReferenceContext referenceContext) {
        CompilationResult compilationResult;
        if (this.f617name == UNASSIGNED_CLOSEABLE_NAME && aSTNode != null && referenceContext != null && (compilationResult = referenceContext.compilationResult()) != null) {
            int[] lineSeparatorPositions = compilationResult.getLineSeparatorPositions();
            int lineNumber = Util.getLineNumber(this.sourceStart, lineSeparatorPositions, 0, lineSeparatorPositions.length - 1);
            if (lineNumber != Util.getLineNumber(aSTNode.sourceStart, lineSeparatorPositions, 0, lineSeparatorPositions.length - 1)) {
                return String.valueOf(CharOperation.replace(UNASSIGNED_CLOSEABLE_NAME_TEMPLATE, TEMPLATE_ARGUMENT, Integer.toString(lineNumber).toCharArray()));
            }
        }
        return String.valueOf(this.f617name);
    }
}
