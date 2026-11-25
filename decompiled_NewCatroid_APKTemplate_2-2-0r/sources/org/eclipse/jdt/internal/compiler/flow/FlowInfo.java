package org.eclipse.jdt.internal.compiler.flow;

import org.eclipse.jdt.internal.compiler.ast.IfStatement;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public abstract class FlowInfo {
    public static final UnconditionalFlowInfo DEAD_END;
    public static final int FREE_TYPEVARIABLE = 48;
    public static final int NON_NULL = 4;
    public static final int NULL = 2;
    public static final int NULL_FLAG_MASK = 4;
    public static final int POTENTIALLY_NON_NULL = 32;
    public static final int POTENTIALLY_NULL = 16;
    public static final int POTENTIALLY_UNKNOWN = 8;
    public static final int REACHABLE = 0;
    public static final int UNKNOWN = 1;
    public static final int UNREACHABLE = 3;
    public static final int UNREACHABLE_BY_NULLANALYSIS = 2;
    public static final int UNREACHABLE_OR_DEAD = 1;
    public static final int UNROOTED = 64;
    public int tagBits;

    public static int mergeNullStatus(int i, int i2) {
        boolean z;
        boolean z2 = false;
        if (i == 2) {
            z = z2;
            z2 = true;
        } else if (i == 4) {
            z = true;
        } else if (i == 16) {
            z2 = true;
            z = z2;
            z2 = true;
        } else if (i != 32) {
            z = false;
        } else {
            z2 = true;
            z = true;
        }
        if (i2 == 2) {
            z2 = true;
        } else if (i2 == 4) {
            z = true;
        } else if (i2 == 16) {
            z = true;
            z2 = true;
        } else if (i2 == 32) {
            z2 = true;
            z = true;
        }
        if (z2) {
            return z ? 16 : 2;
        }
        return z ? 4 : 1;
    }

    public static int tagBitsToNullStatus(long j) {
        if ((72057594037927936L & j) != 0) {
            return 4;
        }
        return (j & 36028797018963968L) != 0 ? 48 : 1;
    }

    public abstract FlowInfo addInitializationsFrom(FlowInfo flowInfo);

    public abstract FlowInfo addNullInfoFrom(FlowInfo flowInfo);

    public abstract FlowInfo addPotentialInitializationsFrom(FlowInfo flowInfo);

    public FlowInfo asNegatedCondition() {
        return this;
    }

    public abstract FlowInfo copy();

    public abstract boolean hasNullInfoFor(LocalVariableBinding localVariableBinding);

    public abstract FlowInfo initsWhenFalse();

    public abstract FlowInfo initsWhenTrue();

    public abstract boolean isDefinitelyAssigned(FieldBinding fieldBinding);

    public abstract boolean isDefinitelyAssigned(LocalVariableBinding localVariableBinding);

    public abstract boolean isDefinitelyNonNull(LocalVariableBinding localVariableBinding);

    public abstract boolean isDefinitelyNull(LocalVariableBinding localVariableBinding);

    public abstract boolean isDefinitelyUnknown(LocalVariableBinding localVariableBinding);

    public abstract boolean isPotentiallyAssigned(FieldBinding fieldBinding);

    public abstract boolean isPotentiallyAssigned(LocalVariableBinding localVariableBinding);

    public abstract boolean isPotentiallyNonNull(LocalVariableBinding localVariableBinding);

    public abstract boolean isPotentiallyNull(LocalVariableBinding localVariableBinding);

    public abstract boolean isPotentiallyUnknown(LocalVariableBinding localVariableBinding);

    public abstract boolean isProtectedNonNull(LocalVariableBinding localVariableBinding);

    public abstract boolean isProtectedNull(LocalVariableBinding localVariableBinding);

    public abstract void markAsComparedEqualToNonNull(LocalVariableBinding localVariableBinding);

    public abstract void markAsComparedEqualToNull(LocalVariableBinding localVariableBinding);

    public abstract void markAsDefinitelyAssigned(FieldBinding fieldBinding);

    public abstract void markAsDefinitelyAssigned(LocalVariableBinding localVariableBinding);

    public abstract void markAsDefinitelyNonNull(LocalVariableBinding localVariableBinding);

    public abstract void markAsDefinitelyNull(LocalVariableBinding localVariableBinding);

    public abstract void markAsDefinitelyUnknown(LocalVariableBinding localVariableBinding);

    public abstract void markPotentiallyNonNullBit(LocalVariableBinding localVariableBinding);

    public abstract void markPotentiallyNullBit(LocalVariableBinding localVariableBinding);

    public abstract void markPotentiallyUnknownBit(LocalVariableBinding localVariableBinding);

    public abstract UnconditionalFlowInfo mergedWith(UnconditionalFlowInfo unconditionalFlowInfo);

    public abstract UnconditionalFlowInfo nullInfoLessUnconditionalCopy();

    public abstract void resetAssignmentInfo(LocalVariableBinding localVariableBinding);

    public abstract void resetNullInfo(LocalVariableBinding localVariableBinding);

    public abstract FlowInfo safeInitsWhenTrue();

    public abstract FlowInfo setReachMode(int i);

    public abstract UnconditionalFlowInfo unconditionalCopy();

    public abstract UnconditionalFlowInfo unconditionalFieldLessCopy();

    public abstract UnconditionalFlowInfo unconditionalInits();

    public abstract UnconditionalFlowInfo unconditionalInitsWithoutSideEffect();

    static {
        UnconditionalFlowInfo unconditionalFlowInfo = new UnconditionalFlowInfo();
        DEAD_END = unconditionalFlowInfo;
        unconditionalFlowInfo.tagBits = 3;
    }

    public static FlowInfo conditional(FlowInfo flowInfo, FlowInfo flowInfo2) {
        return flowInfo == flowInfo2 ? flowInfo : new ConditionalFlowInfo(flowInfo, flowInfo2);
    }

    public boolean cannotBeDefinitelyNullOrNonNull(LocalVariableBinding localVariableBinding) {
        if (isPotentiallyUnknown(localVariableBinding)) {
            return true;
        }
        return isPotentiallyNonNull(localVariableBinding) && isPotentiallyNull(localVariableBinding);
    }

    public boolean cannotBeNull(LocalVariableBinding localVariableBinding) {
        return isDefinitelyNonNull(localVariableBinding) || isProtectedNonNull(localVariableBinding);
    }

    public boolean canOnlyBeNull(LocalVariableBinding localVariableBinding) {
        return isDefinitelyNull(localVariableBinding) || isProtectedNull(localVariableBinding);
    }

    public static UnconditionalFlowInfo initial(int i) {
        UnconditionalFlowInfo unconditionalFlowInfo = new UnconditionalFlowInfo();
        unconditionalFlowInfo.maxFieldCount = i;
        return unconditionalFlowInfo;
    }

    public void markNullStatus(LocalVariableBinding localVariableBinding, int i) {
        if (i == 1) {
            markAsDefinitelyUnknown(localVariableBinding);
            return;
        }
        if (i == 2) {
            markAsDefinitelyNull(localVariableBinding);
            return;
        }
        if (i == 4) {
            markAsDefinitelyNonNull(localVariableBinding);
            return;
        }
        resetNullInfo(localVariableBinding);
        if ((i & 8) != 0) {
            markPotentiallyUnknownBit(localVariableBinding);
        }
        if ((i & 16) != 0) {
            markPotentiallyNullBit(localVariableBinding);
        }
        if ((i & 32) != 0) {
            markPotentiallyNonNullBit(localVariableBinding);
        }
        if ((i & 56) == 0) {
            markAsDefinitelyUnknown(localVariableBinding);
        }
    }

    public int nullStatus(LocalVariableBinding localVariableBinding) {
        if (isDefinitelyUnknown(localVariableBinding)) {
            return 1;
        }
        if (isDefinitelyNull(localVariableBinding)) {
            return 2;
        }
        if (isDefinitelyNonNull(localVariableBinding)) {
            return 4;
        }
        int i = isPotentiallyUnknown(localVariableBinding) ? 8 : 0;
        if (isPotentiallyNull(localVariableBinding)) {
            i |= 16;
        }
        if (isPotentiallyNonNull(localVariableBinding)) {
            i |= 32;
        }
        if (i > 0) {
            return i;
        }
        return 1;
    }

    public static UnconditionalFlowInfo mergedOptimizedBranches(FlowInfo flowInfo, boolean z, FlowInfo flowInfo2, boolean z2, boolean z3) {
        if (z) {
            if (flowInfo == DEAD_END && z3) {
                return flowInfo2.setReachMode(1).unconditionalInits();
            }
            return flowInfo.addPotentialInitializationsFrom(flowInfo2.nullInfoLessUnconditionalCopy()).unconditionalInits();
        }
        if (z2) {
            if (flowInfo2 == DEAD_END && z3) {
                return flowInfo.setReachMode(1).unconditionalInits();
            }
            return flowInfo2.addPotentialInitializationsFrom(flowInfo.nullInfoLessUnconditionalCopy()).unconditionalInits();
        }
        return flowInfo.mergedWith(flowInfo2.unconditionalInits());
    }

    public static UnconditionalFlowInfo mergedOptimizedBranchesIfElse(FlowInfo flowInfo, boolean z, FlowInfo flowInfo2, boolean z2, boolean z3, FlowInfo flowInfo3, IfStatement ifStatement, boolean z4) {
        UnconditionalFlowInfo unconditionalFlowInfo;
        UnconditionalFlowInfo unconditionalFlowInfo2;
        if (z) {
            UnconditionalFlowInfo unconditionalFlowInfo3 = DEAD_END;
            if (flowInfo != unconditionalFlowInfo3 || !z3) {
                return flowInfo.addPotentialInitializationsFrom(flowInfo2.nullInfoLessUnconditionalCopy()).unconditionalInits();
            }
            if (!z4) {
                if (ifStatement.elseStatement == null) {
                    return flowInfo3.unconditionalInits();
                }
                UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits = flowInfo2.unconditionalInits();
                if (flowInfo2 == unconditionalFlowInfo3) {
                    return unconditionalFlowInfoUnconditionalInits;
                }
                unconditionalFlowInfoUnconditionalInits.setReachMode(flowInfo3.reachMode());
                return unconditionalFlowInfoUnconditionalInits;
            }
            return flowInfo2.setReachMode(1).unconditionalInits();
        }
        if (z2) {
            UnconditionalFlowInfo unconditionalFlowInfo4 = DEAD_END;
            if (flowInfo2 != unconditionalFlowInfo4 || !z3) {
                return flowInfo2.addPotentialInitializationsFrom(flowInfo.nullInfoLessUnconditionalCopy()).unconditionalInits();
            }
            if (!z4) {
                if (ifStatement.thenStatement == null) {
                    return flowInfo3.unconditionalInits();
                }
                UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits2 = flowInfo.unconditionalInits();
                if (flowInfo != unconditionalFlowInfo4) {
                    unconditionalFlowInfoUnconditionalInits2.setReachMode(flowInfo3.reachMode());
                }
                return unconditionalFlowInfoUnconditionalInits2;
            }
            return flowInfo.setReachMode(1).unconditionalInits();
        }
        if ((flowInfo3.tagBits & 3) == 0 && (ifStatement.bits & 128) != 0 && flowInfo != (unconditionalFlowInfo2 = DEAD_END) && flowInfo2 != unconditionalFlowInfo2) {
            UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits3 = flowInfo.addPotentialInitializationsFrom(flowInfo2.nullInfoLessUnconditionalCopy()).unconditionalInits();
            unconditionalFlowInfoUnconditionalInits3.definiteInits &= flowInfo2.unconditionalCopy().definiteInits;
            if ((unconditionalFlowInfoUnconditionalInits3.tagBits & 1) == 0 || (flowInfo2.tagBits & 3) != 2) {
                return unconditionalFlowInfoUnconditionalInits3;
            }
            unconditionalFlowInfoUnconditionalInits3.tagBits &= -2;
            unconditionalFlowInfoUnconditionalInits3.tagBits |= 2;
            return unconditionalFlowInfoUnconditionalInits3;
        }
        if ((flowInfo3.tagBits & 3) == 0 && (ifStatement.bits & 256) != 0 && flowInfo != (unconditionalFlowInfo = DEAD_END) && flowInfo2 != unconditionalFlowInfo) {
            UnconditionalFlowInfo unconditionalFlowInfoUnconditionalInits4 = flowInfo2.addPotentialInitializationsFrom(flowInfo.nullInfoLessUnconditionalCopy()).unconditionalInits();
            unconditionalFlowInfoUnconditionalInits4.definiteInits &= flowInfo.unconditionalCopy().definiteInits;
            if ((unconditionalFlowInfoUnconditionalInits4.tagBits & 1) != 0 && (flowInfo.tagBits & 3) == 2) {
                unconditionalFlowInfoUnconditionalInits4.tagBits &= -2;
                unconditionalFlowInfoUnconditionalInits4.tagBits |= 2;
            }
            return unconditionalFlowInfoUnconditionalInits4;
        }
        return flowInfo.mergedWith(flowInfo2.unconditionalInits());
    }

    public int reachMode() {
        return this.tagBits & 3;
    }

    public String toString() {
        if (this == DEAD_END) {
            return "FlowInfo.DEAD_END";
        }
        return super.toString();
    }
}
