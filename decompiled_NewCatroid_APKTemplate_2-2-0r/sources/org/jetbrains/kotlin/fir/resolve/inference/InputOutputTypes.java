package org.jetbrains.kotlin.fir.resolve.inference;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: PostponedAtoms.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0080\b\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0004HÆ\u0003J#\u0010\r\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0004HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/InputOutputTypes;", "", "inputTypes", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "outputType", "(Ljava/util/List;Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;)V", "getInputTypes", "()Ljava/util/List;", "getOutputType", "()Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class InputOutputTypes {
    private final List<ConeKotlinType> inputTypes;
    private final ConeKotlinType outputType;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ InputOutputTypes copy$default(InputOutputTypes inputOutputTypes, List list, ConeKotlinType coneKotlinType, int i, Object obj) {
        if ((i & 1) != 0) {
            list = inputOutputTypes.inputTypes;
        }
        if ((i & 2) != 0) {
            coneKotlinType = inputOutputTypes.outputType;
        }
        return inputOutputTypes.copy(list, coneKotlinType);
    }

    public final List<ConeKotlinType> component1() {
        return this.inputTypes;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ConeKotlinType getOutputType() {
        return this.outputType;
    }

    public final InputOutputTypes copy(List<? extends ConeKotlinType> inputTypes, ConeKotlinType outputType) {
        Intrinsics.checkNotNullParameter(inputTypes, "inputTypes");
        Intrinsics.checkNotNullParameter(outputType, "outputType");
        return new InputOutputTypes(inputTypes, outputType);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof InputOutputTypes)) {
            return false;
        }
        InputOutputTypes inputOutputTypes = (InputOutputTypes) other;
        return Intrinsics.areEqual(this.inputTypes, inputOutputTypes.inputTypes) && Intrinsics.areEqual(this.outputType, inputOutputTypes.outputType);
    }

    public int hashCode() {
        return (this.inputTypes.hashCode() * 31) + this.outputType.hashCode();
    }

    public String toString() {
        return "InputOutputTypes(inputTypes=" + this.inputTypes + ", outputType=" + this.outputType + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public InputOutputTypes(List<? extends ConeKotlinType> inputTypes, ConeKotlinType outputType) {
        Intrinsics.checkNotNullParameter(inputTypes, "inputTypes");
        Intrinsics.checkNotNullParameter(outputType, "outputType");
        this.inputTypes = inputTypes;
        this.outputType = outputType;
    }

    public final List<ConeKotlinType> getInputTypes() {
        return this.inputTypes;
    }

    public final ConeKotlinType getOutputType() {
        return this.outputType;
    }
}
