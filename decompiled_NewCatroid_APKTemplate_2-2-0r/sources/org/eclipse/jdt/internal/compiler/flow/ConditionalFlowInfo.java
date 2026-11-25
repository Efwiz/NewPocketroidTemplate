package org.eclipse.jdt.internal.compiler.flow;

import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;

/* JADX INFO: loaded from: classes14.dex */
public class ConditionalFlowInfo extends FlowInfo {
    public FlowInfo initsWhenFalse;
    public FlowInfo initsWhenTrue;

    ConditionalFlowInfo(FlowInfo flowInfo, FlowInfo flowInfo2) {
        this.initsWhenTrue = flowInfo;
        this.initsWhenFalse = flowInfo2;
        this.tagBits = flowInfo.tagBits & flowInfo2.tagBits & 3;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public FlowInfo addInitializationsFrom(FlowInfo flowInfo) {
        this.initsWhenTrue.addInitializationsFrom(flowInfo);
        this.initsWhenFalse.addInitializationsFrom(flowInfo);
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public FlowInfo addNullInfoFrom(FlowInfo flowInfo) {
        this.initsWhenTrue.addNullInfoFrom(flowInfo);
        this.initsWhenFalse.addNullInfoFrom(flowInfo);
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public FlowInfo addPotentialInitializationsFrom(FlowInfo flowInfo) {
        this.initsWhenTrue.addPotentialInitializationsFrom(flowInfo);
        this.initsWhenFalse.addPotentialInitializationsFrom(flowInfo);
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public FlowInfo asNegatedCondition() {
        FlowInfo flowInfo = this.initsWhenTrue;
        this.initsWhenTrue = this.initsWhenFalse;
        this.initsWhenFalse = flowInfo;
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public FlowInfo copy() {
        return new ConditionalFlowInfo(this.initsWhenTrue.copy(), this.initsWhenFalse.copy());
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public FlowInfo initsWhenFalse() {
        return this.initsWhenFalse;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public FlowInfo initsWhenTrue() {
        return this.initsWhenTrue;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public boolean isDefinitelyAssigned(FieldBinding fieldBinding) {
        return this.initsWhenTrue.isDefinitelyAssigned(fieldBinding) && this.initsWhenFalse.isDefinitelyAssigned(fieldBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public boolean isDefinitelyAssigned(LocalVariableBinding localVariableBinding) {
        return this.initsWhenTrue.isDefinitelyAssigned(localVariableBinding) && this.initsWhenFalse.isDefinitelyAssigned(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public boolean isDefinitelyNonNull(LocalVariableBinding localVariableBinding) {
        return this.initsWhenTrue.isDefinitelyNonNull(localVariableBinding) && this.initsWhenFalse.isDefinitelyNonNull(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public boolean isDefinitelyNull(LocalVariableBinding localVariableBinding) {
        return this.initsWhenTrue.isDefinitelyNull(localVariableBinding) && this.initsWhenFalse.isDefinitelyNull(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public boolean isDefinitelyUnknown(LocalVariableBinding localVariableBinding) {
        return this.initsWhenTrue.isDefinitelyUnknown(localVariableBinding) && this.initsWhenFalse.isDefinitelyUnknown(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public boolean hasNullInfoFor(LocalVariableBinding localVariableBinding) {
        return this.initsWhenTrue.hasNullInfoFor(localVariableBinding) || this.initsWhenFalse.hasNullInfoFor(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public boolean isPotentiallyAssigned(FieldBinding fieldBinding) {
        return this.initsWhenTrue.isPotentiallyAssigned(fieldBinding) || this.initsWhenFalse.isPotentiallyAssigned(fieldBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public boolean isPotentiallyAssigned(LocalVariableBinding localVariableBinding) {
        return this.initsWhenTrue.isPotentiallyAssigned(localVariableBinding) || this.initsWhenFalse.isPotentiallyAssigned(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public boolean isPotentiallyNonNull(LocalVariableBinding localVariableBinding) {
        return this.initsWhenTrue.isPotentiallyNonNull(localVariableBinding) || this.initsWhenFalse.isPotentiallyNonNull(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public boolean isPotentiallyNull(LocalVariableBinding localVariableBinding) {
        return this.initsWhenTrue.isPotentiallyNull(localVariableBinding) || this.initsWhenFalse.isPotentiallyNull(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public boolean isPotentiallyUnknown(LocalVariableBinding localVariableBinding) {
        return this.initsWhenTrue.isPotentiallyUnknown(localVariableBinding) || this.initsWhenFalse.isPotentiallyUnknown(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public boolean isProtectedNonNull(LocalVariableBinding localVariableBinding) {
        return this.initsWhenTrue.isProtectedNonNull(localVariableBinding) && this.initsWhenFalse.isProtectedNonNull(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public boolean isProtectedNull(LocalVariableBinding localVariableBinding) {
        return this.initsWhenTrue.isProtectedNull(localVariableBinding) && this.initsWhenFalse.isProtectedNull(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markAsComparedEqualToNonNull(LocalVariableBinding localVariableBinding) {
        this.initsWhenTrue.markAsComparedEqualToNonNull(localVariableBinding);
        this.initsWhenFalse.markAsComparedEqualToNonNull(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markAsComparedEqualToNull(LocalVariableBinding localVariableBinding) {
        this.initsWhenTrue.markAsComparedEqualToNull(localVariableBinding);
        this.initsWhenFalse.markAsComparedEqualToNull(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markAsDefinitelyAssigned(FieldBinding fieldBinding) {
        this.initsWhenTrue.markAsDefinitelyAssigned(fieldBinding);
        this.initsWhenFalse.markAsDefinitelyAssigned(fieldBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markAsDefinitelyAssigned(LocalVariableBinding localVariableBinding) {
        this.initsWhenTrue.markAsDefinitelyAssigned(localVariableBinding);
        this.initsWhenFalse.markAsDefinitelyAssigned(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markAsDefinitelyNonNull(LocalVariableBinding localVariableBinding) {
        this.initsWhenTrue.markAsDefinitelyNonNull(localVariableBinding);
        this.initsWhenFalse.markAsDefinitelyNonNull(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markAsDefinitelyNull(LocalVariableBinding localVariableBinding) {
        this.initsWhenTrue.markAsDefinitelyNull(localVariableBinding);
        this.initsWhenFalse.markAsDefinitelyNull(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void resetNullInfo(LocalVariableBinding localVariableBinding) {
        this.initsWhenTrue.resetNullInfo(localVariableBinding);
        this.initsWhenFalse.resetNullInfo(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markPotentiallyNullBit(LocalVariableBinding localVariableBinding) {
        this.initsWhenTrue.markPotentiallyNullBit(localVariableBinding);
        this.initsWhenFalse.markPotentiallyNullBit(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markPotentiallyNonNullBit(LocalVariableBinding localVariableBinding) {
        this.initsWhenTrue.markPotentiallyNonNullBit(localVariableBinding);
        this.initsWhenFalse.markPotentiallyNonNullBit(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markAsDefinitelyUnknown(LocalVariableBinding localVariableBinding) {
        this.initsWhenTrue.markAsDefinitelyUnknown(localVariableBinding);
        this.initsWhenFalse.markAsDefinitelyUnknown(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void markPotentiallyUnknownBit(LocalVariableBinding localVariableBinding) {
        this.initsWhenTrue.markPotentiallyUnknownBit(localVariableBinding);
        this.initsWhenFalse.markPotentiallyUnknownBit(localVariableBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public FlowInfo setReachMode(int i) {
        if (i == 0) {
            this.tagBits &= -4;
        } else {
            this.tagBits |= i;
        }
        this.initsWhenTrue.setReachMode(i);
        this.initsWhenFalse.setReachMode(i);
        return this;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public UnconditionalFlowInfo mergedWith(UnconditionalFlowInfo unconditionalFlowInfo) {
        return unconditionalInits().mergedWith(unconditionalFlowInfo);
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public UnconditionalFlowInfo nullInfoLessUnconditionalCopy() {
        return unconditionalInitsWithoutSideEffect().nullInfoLessUnconditionalCopy();
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public String toString() {
        return "FlowInfo<true: " + this.initsWhenTrue.toString() + ", false: " + this.initsWhenFalse.toString() + ">";
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public FlowInfo safeInitsWhenTrue() {
        return this.initsWhenTrue;
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public UnconditionalFlowInfo unconditionalCopy() {
        return this.initsWhenTrue.unconditionalCopy().mergedWith(this.initsWhenFalse.unconditionalInits());
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public UnconditionalFlowInfo unconditionalFieldLessCopy() {
        return this.initsWhenTrue.unconditionalFieldLessCopy().mergedWith(this.initsWhenFalse.unconditionalFieldLessCopy());
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public UnconditionalFlowInfo unconditionalInits() {
        return this.initsWhenTrue.unconditionalInits().mergedWith(this.initsWhenFalse.unconditionalInits());
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public UnconditionalFlowInfo unconditionalInitsWithoutSideEffect() {
        return this.initsWhenTrue.unconditionalCopy().mergedWith(this.initsWhenFalse.unconditionalInits());
    }

    @Override // org.eclipse.jdt.internal.compiler.flow.FlowInfo
    public void resetAssignmentInfo(LocalVariableBinding localVariableBinding) {
        this.initsWhenTrue.resetAssignmentInfo(localVariableBinding);
        this.initsWhenFalse.resetAssignmentInfo(localVariableBinding);
    }
}
