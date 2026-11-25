package org.jetbrains.kotlin.psi2ir.generators.fragments;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: EvaluatorFragmentInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000b\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\r\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\t¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/fragments/EvaluatorFragmentParameterInfo;", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "isLValue", "", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Z)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "()Z", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class EvaluatorFragmentParameterInfo {
    private final DeclarationDescriptor descriptor;
    private final boolean isLValue;

    public static /* synthetic */ EvaluatorFragmentParameterInfo copy$default(EvaluatorFragmentParameterInfo evaluatorFragmentParameterInfo, DeclarationDescriptor declarationDescriptor, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            declarationDescriptor = evaluatorFragmentParameterInfo.descriptor;
        }
        if ((i & 2) != 0) {
            z = evaluatorFragmentParameterInfo.isLValue;
        }
        return evaluatorFragmentParameterInfo.copy(declarationDescriptor, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final DeclarationDescriptor getDescriptor() {
        return this.descriptor;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getIsLValue() {
        return this.isLValue;
    }

    public final EvaluatorFragmentParameterInfo copy(DeclarationDescriptor descriptor, boolean isLValue) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return new EvaluatorFragmentParameterInfo(descriptor, isLValue);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EvaluatorFragmentParameterInfo)) {
            return false;
        }
        EvaluatorFragmentParameterInfo evaluatorFragmentParameterInfo = (EvaluatorFragmentParameterInfo) other;
        return Intrinsics.areEqual(this.descriptor, evaluatorFragmentParameterInfo.descriptor) && this.isLValue == evaluatorFragmentParameterInfo.isLValue;
    }

    public int hashCode() {
        return (this.descriptor.hashCode() * 31) + Boolean.hashCode(this.isLValue);
    }

    public String toString() {
        return "EvaluatorFragmentParameterInfo(descriptor=" + this.descriptor + ", isLValue=" + this.isLValue + ')';
    }

    public EvaluatorFragmentParameterInfo(DeclarationDescriptor descriptor, boolean z) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        this.descriptor = descriptor;
        this.isLValue = z;
    }

    public final DeclarationDescriptor getDescriptor() {
        return this.descriptor;
    }

    public final boolean isLValue() {
        return this.isLValue;
    }
}
