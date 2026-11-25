package org.jetbrains.kotlin.com.intellij.psi.controlFlow;

import com.gaurav.avnc.vnc.XKeySym;

/* JADX INFO: loaded from: classes3.dex */
public class ControlFlowOptions {
    private static final ControlFlowOptions[] DATA = {new ControlFlowOptions(false, false, false), new ControlFlowOptions(false, false, true), new ControlFlowOptions(false, true, false), new ControlFlowOptions(false, true, true), new ControlFlowOptions(true, false, false), new ControlFlowOptions(true, false, true), new ControlFlowOptions(true, true, false), new ControlFlowOptions(true, true, true)};
    public static final ControlFlowOptions NO_CONST_EVALUATE = create(false, false, true);
    private final boolean myEnableShortCircuit;
    private final boolean myEvaluateConstantIfCondition;
    private final boolean myExceptionAfterAssignment;

    public static ControlFlowOptions create(boolean z, boolean z2, boolean z3) {
        return DATA[(z ? 4 : 0) + (z2 ? 2 : 0) + (z3 ? 1 : 0)];
    }

    private ControlFlowOptions(boolean z, boolean z2, boolean z3) {
        this.myEnableShortCircuit = z;
        this.myEvaluateConstantIfCondition = z2;
        this.myExceptionAfterAssignment = z3;
    }

    public boolean enableShortCircuit() {
        return this.myEnableShortCircuit;
    }

    public boolean shouldEvaluateConstantIfCondition() {
        return this.myEvaluateConstantIfCondition;
    }

    public boolean isExceptionAfterAssignment() {
        return this.myExceptionAfterAssignment;
    }

    public ControlFlowOptions dontEvaluateConstantIfCondition() {
        return this.myEvaluateConstantIfCondition ? create(this.myEnableShortCircuit, false, this.myExceptionAfterAssignment) : this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ControlFlowOptions controlFlowOptions = (ControlFlowOptions) obj;
        return this.myEnableShortCircuit == controlFlowOptions.myEnableShortCircuit && this.myEvaluateConstantIfCondition == controlFlowOptions.myEvaluateConstantIfCondition && this.myExceptionAfterAssignment == controlFlowOptions.myExceptionAfterAssignment;
    }

    public int hashCode() {
        boolean z = this.myEnableShortCircuit;
        int i = XKeySym.XK_kana_MA;
        int i2 = ((((z ? 1231 : 1237) + 31) * 31) + (this.myEvaluateConstantIfCondition ? 1231 : 1237)) * 31;
        if (!this.myExceptionAfterAssignment) {
            i = 1237;
        }
        return i2 + i;
    }
}
