package org.eclipse.jdt.internal.compiler.flow;

import java.util.ArrayList;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.ast.SubRoutineStatement;
import org.eclipse.jdt.internal.compiler.ast.TryStatement;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.ast.UnionTypeReference;
import org.eclipse.jdt.internal.compiler.codegen.ObjectCache;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.CatchParameterBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ExceptionHandlingFlowContext extends FlowContext {
    public static final int BitCacheSize = 32;
    private static final Argument[] NO_ARGUMENTS = new Argument[0];
    public Argument[] catchArguments;
    private int[] exceptionToCatchBlockMap;
    public ArrayList extendedExceptions;
    public ReferenceBinding[] handledExceptions;
    ObjectCache indexes;
    public FlowContext initializationParent;
    UnconditionalFlowInfo[] initsOnExceptions;
    public UnconditionalFlowInfo initsOnReturn;
    boolean isMethodContext;
    int[] isNeeded;
    int[] isReached;

    public ExceptionHandlingFlowContext(FlowContext flowContext, ASTNode aSTNode, ReferenceBinding[] referenceBindingArr, FlowContext flowContext2, BlockScope blockScope, UnconditionalFlowInfo unconditionalFlowInfo) {
        this(flowContext, aSTNode, referenceBindingArr, null, NO_ARGUMENTS, flowContext2, blockScope, unconditionalFlowInfo);
    }

    public ExceptionHandlingFlowContext(FlowContext flowContext, TryStatement tryStatement, ReferenceBinding[] referenceBindingArr, int[] iArr, FlowContext flowContext2, BlockScope blockScope, FlowInfo flowInfo) {
        this(flowContext, tryStatement, referenceBindingArr, iArr, tryStatement.catchArguments, flowContext2, blockScope, flowInfo.unconditionalInits());
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalCopy = flowInfo.unconditionalCopy();
        unconditionalFlowInfoUnconditionalCopy.iNBit = -1L;
        unconditionalFlowInfoUnconditionalCopy.iNNBit = -1L;
        unconditionalFlowInfoUnconditionalCopy.tagBits |= 64;
        this.initsOnFinally = unconditionalFlowInfoUnconditionalCopy;
    }

    ExceptionHandlingFlowContext(FlowContext flowContext, ASTNode aSTNode, ReferenceBinding[] referenceBindingArr, int[] iArr, Argument[] argumentArr, FlowContext flowContext2, BlockScope blockScope, UnconditionalFlowInfo unconditionalFlowInfo) {
        super(flowContext, aSTNode);
        this.indexes = new ObjectCache();
        boolean z = blockScope == blockScope.methodScope();
        this.isMethodContext = z;
        this.handledExceptions = referenceBindingArr;
        this.catchArguments = argumentArr;
        this.exceptionToCatchBlockMap = iArr;
        int length = referenceBindingArr.length;
        int i = (length / 32) + 1;
        this.isReached = new int[i];
        this.isNeeded = new int[i];
        this.initsOnExceptions = new UnconditionalFlowInfo[length];
        boolean z2 = !z || blockScope.compilerOptions().reportUnusedDeclaredThrownExceptionExemptExceptionAndThrowable;
        for (int i2 = 0; i2 < length; i2++) {
            ReferenceBinding referenceBinding = referenceBindingArr[i2];
            int[] iArr2 = this.exceptionToCatchBlockMap;
            int i3 = iArr2 != null ? iArr2[i2] : i2;
            this.indexes.put(referenceBinding, i2);
            if (referenceBinding.isUncheckedException(true)) {
                if (z2 || (referenceBinding.f626id != 21 && referenceBinding.f626id != 25)) {
                    int[] iArr3 = this.isReached;
                    int i4 = i2 / 32;
                    iArr3[i4] = iArr3[i4] | (1 << (i2 % 32));
                }
                this.initsOnExceptions[i3] = unconditionalFlowInfo.unconditionalCopy();
            } else {
                this.initsOnExceptions[i3] = FlowInfo.DEAD_END;
            }
        }
        if (!this.isMethodContext) {
            System.arraycopy(this.isReached, 0, this.isNeeded, 0, i);
        }
        this.initsOnReturn = FlowInfo.DEAD_END;
        this.initializationParent = flowContext2;
    }

    public void complainIfUnusedExceptionHandlers(AbstractMethodDeclaration abstractMethodDeclaration) {
        int length;
        MethodScope methodScope = abstractMethodDeclaration.scope;
        if ((abstractMethodDeclaration.binding.modifiers & 805306368) == 0 || methodScope.compilerOptions().reportUnusedDeclaredThrownExceptionWhenOverriding) {
            TypeBinding[] typeBindingArr = null;
            if (!methodScope.compilerOptions().reportUnusedDeclaredThrownExceptionIncludeDocCommentReference || abstractMethodDeclaration.javadoc == null || abstractMethodDeclaration.javadoc.exceptionReferences == null) {
                length = 0;
            } else {
                length = abstractMethodDeclaration.javadoc.exceptionReferences.length;
                if (length > 0) {
                    TypeBinding[] typeBindingArr2 = new TypeBinding[length];
                    for (int i = 0; i < length; i++) {
                        typeBindingArr2[i] = abstractMethodDeclaration.javadoc.exceptionReferences[i].resolvedType;
                    }
                    typeBindingArr = typeBindingArr2;
                }
            }
            int length2 = this.handledExceptions.length;
            for (int i2 = 0; i2 < length2; i2++) {
                int i3 = this.indexes.get(this.handledExceptions[i2]);
                if ((this.isReached[i3 / 32] & (1 << (i3 % 32))) == 0) {
                    int i4 = 0;
                    while (true) {
                        if (i4 < length) {
                            if (TypeBinding.equalsEquals(typeBindingArr[i4], this.handledExceptions[i2])) {
                                break;
                            } else {
                                i4++;
                            }
                        } else {
                            methodScope.problemReporter().unusedDeclaredThrownException(this.handledExceptions[i3], abstractMethodDeclaration, abstractMethodDeclaration.thrownExceptions[i3]);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void complainIfUnusedExceptionHandlers(BlockScope blockScope, TryStatement tryStatement) {
        int length = this.handledExceptions.length;
        for (int i = 0; i < length; i++) {
            int i2 = i / 32;
            int i3 = 1 << (i % 32);
            if ((this.isReached[i2] & i3) == 0) {
                blockScope.problemReporter().unreachableCatchBlock(this.handledExceptions[i], getExceptionType(i));
            } else if ((this.isNeeded[i2] & i3) == 0) {
                blockScope.problemReporter().hiddenCatchBlock(this.handledExceptions[i], getExceptionType(i));
            }
        }
    }

    private ASTNode getExceptionType(int i) {
        int[] iArr = this.exceptionToCatchBlockMap;
        if (iArr == null) {
            return this.catchArguments[i].type;
        }
        TypeReference typeReference = this.catchArguments[iArr[i]].type;
        if (typeReference instanceof UnionTypeReference) {
            for (TypeReference typeReference2 : ((UnionTypeReference) typeReference).typeReferences) {
                if (TypeBinding.equalsEquals(typeReference2.resolvedType, this.handledExceptions[i])) {
                    return typeReference2;
                }
            }
        }
        return typeReference;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public FlowContext getInitializationContext() {
        return this.initializationParent;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public String individualToString() {
        StringBuffer stringBuffer = new StringBuffer("Exception flow context");
        int length = this.handledExceptions.length;
        for (int i = 0; i < length; i++) {
            int i2 = i / 32;
            int i3 = 1 << (i % 32);
            stringBuffer.append('[').append(this.handledExceptions[i].readableName());
            if ((this.isReached[i2] & i3) != 0) {
                if ((this.isNeeded[i2] & i3) == 0) {
                    stringBuffer.append("-masked");
                } else {
                    stringBuffer.append("-reached");
                }
            } else {
                stringBuffer.append("-not reached");
            }
            int[] iArr = this.exceptionToCatchBlockMap;
            stringBuffer.append('-').append(this.initsOnExceptions[iArr != null ? iArr[i] : i].toString()).append(']');
        }
        stringBuffer.append("[initsOnReturn -").append(this.initsOnReturn.toString()).append(']');
        return stringBuffer.toString();
    }

    public UnconditionalFlowInfo initsOnException(int i) {
        return this.initsOnExceptions[i];
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public UnconditionalFlowInfo initsOnReturn() {
        return this.initsOnReturn;
    }

    public void mergeUnhandledException(TypeBinding typeBinding) {
        boolean z = false;
        if (this.extendedExceptions == null) {
            this.extendedExceptions = new ArrayList(5);
            int i = 0;
            while (true) {
                ReferenceBinding[] referenceBindingArr = this.handledExceptions;
                if (i >= referenceBindingArr.length) {
                    break;
                }
                this.extendedExceptions.add(referenceBindingArr[i]);
                i++;
            }
        }
        for (int size = this.extendedExceptions.size() - 1; size >= 0; size--) {
            int iCompareTypes = Scope.compareTypes(typeBinding, (TypeBinding) this.extendedExceptions.get(size));
            if (iCompareTypes == -1) {
                z = true;
            } else if (iCompareTypes == 1) {
                this.extendedExceptions.remove(size);
            }
        }
        if (z) {
            return;
        }
        this.extendedExceptions.add(typeBinding);
    }

    public void recordHandlingException(ReferenceBinding referenceBinding, UnconditionalFlowInfo unconditionalFlowInfo, TypeBinding typeBinding, TypeBinding typeBinding2, ASTNode aSTNode, boolean z) {
        UnconditionalFlowInfo unconditionalFlowInfoUnconditionalCopy;
        Argument[] argumentArr;
        int i = this.indexes.get(referenceBinding);
        int i2 = i / 32;
        int i3 = 1 << (i % 32);
        if (!z) {
            int[] iArr = this.isNeeded;
            iArr[i2] = iArr[i2] | i3;
        }
        int[] iArr2 = this.isReached;
        iArr2[i2] = i3 | iArr2[i2];
        int[] iArr3 = this.exceptionToCatchBlockMap;
        if (iArr3 != null) {
            i = iArr3[i];
        }
        if (typeBinding2 != null && (argumentArr = this.catchArguments) != null && argumentArr.length > 0 && !z) {
            ((CatchParameterBinding) argumentArr[i].binding).setPreciseType(typeBinding2);
        }
        UnconditionalFlowInfo[] unconditionalFlowInfoArr = this.initsOnExceptions;
        if ((unconditionalFlowInfoArr[i].tagBits & 3) == 0) {
            unconditionalFlowInfoUnconditionalCopy = this.initsOnExceptions[i].mergedWith(unconditionalFlowInfo);
        } else {
            unconditionalFlowInfoUnconditionalCopy = unconditionalFlowInfo.unconditionalCopy();
        }
        unconditionalFlowInfoArr[i] = unconditionalFlowInfoUnconditionalCopy;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public void recordReturnFrom(UnconditionalFlowInfo unconditionalFlowInfo) {
        if ((unconditionalFlowInfo.tagBits & 1) == 0) {
            if ((this.initsOnReturn.tagBits & 1) == 0) {
                this.initsOnReturn = this.initsOnReturn.mergedWith(unconditionalFlowInfo);
            } else {
                this.initsOnReturn = (UnconditionalFlowInfo) unconditionalFlowInfo.copy();
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowContext
    public SubRoutineStatement subroutine() {
        if (!(this.associatedNode instanceof SubRoutineStatement) || this.parent.subroutine() == this.associatedNode) {
            return null;
        }
        return (SubRoutineStatement) this.associatedNode;
    }
}
