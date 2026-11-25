package org.eclipse.jdt.internal.compiler.lookup;

/* JADX INFO: loaded from: classes14.dex */
public abstract class ReductionResult {
    protected static final int CAPTURE = 6;
    protected static final int COMPATIBLE = 1;
    static final int EXCEPTIONS_CONTAINED = 7;
    protected static final int POTENTIALLY_COMPATIBLE = 8;
    protected static final int SAME = 4;
    protected static final int SUBTYPE = 2;
    protected static final int SUPERTYPE = 3;
    protected static final int TYPE_ARGUMENT_CONTAINED = 5;
    protected int relation;
    protected TypeBinding right;
    protected static final ConstraintTypeFormula TRUE = new ConstraintTypeFormula() { // from class: org.eclipse.jdt.internal.compiler.lookup.ReductionResult.1
        @Override // org.eclipse.jdt.internal.compiler.lookup.ConstraintTypeFormula, org.eclipse.jdt.internal.compiler.lookup.ConstraintFormula
        public Object reduce(InferenceContext18 inferenceContext18) {
            return this;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.ConstraintTypeFormula
        public String toString() {
            return "TRUE";
        }
    };
    protected static final ConstraintTypeFormula FALSE = new ConstraintTypeFormula() { // from class: org.eclipse.jdt.internal.compiler.lookup.ReductionResult.2
        @Override // org.eclipse.jdt.internal.compiler.lookup.ConstraintTypeFormula, org.eclipse.jdt.internal.compiler.lookup.ConstraintFormula
        public Object reduce(InferenceContext18 inferenceContext18) {
            return this;
        }

        @Override // org.eclipse.jdt.internal.compiler.lookup.ConstraintTypeFormula
        public String toString() {
            return "FALSE";
        }
    };

    protected static String relationToString(int i) {
        switch (i) {
            case 1:
                return " → ";
            case 2:
                return " <: ";
            case 3:
                return " :> ";
            case 4:
                return " = ";
            case 5:
                return " <= ";
            case 6:
                return " captureOf ";
            case 7:
            default:
                throw new IllegalArgumentException("Unknown type relation " + i);
            case 8:
                return " →? ";
        }
    }
}
