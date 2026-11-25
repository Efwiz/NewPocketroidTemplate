package org.jetbrains.kotlin.types;

/* JADX INFO: loaded from: classes6.dex */
public class TypeReconstructionResult {
    private final boolean allArgumentsInferred;
    private final KotlinType resultingType;

    public TypeReconstructionResult(KotlinType kotlinType, boolean z) {
        this.resultingType = kotlinType;
        this.allArgumentsInferred = z;
    }

    public KotlinType getResultingType() {
        return this.resultingType;
    }

    public boolean isAllArgumentsInferred() {
        return this.allArgumentsInferred;
    }
}
