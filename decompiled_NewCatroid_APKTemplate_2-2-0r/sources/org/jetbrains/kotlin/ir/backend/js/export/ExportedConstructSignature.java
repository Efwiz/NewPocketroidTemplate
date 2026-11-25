package org.jetbrains.kotlin.ir.backend.js.export;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ExportModel.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0006HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedConstructSignature;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedDeclaration;", "parameters", "", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedParameter;", "returnType", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "(Ljava/util/List;Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;)V", "getParameters", "()Ljava/util/List;", "getReturnType", "()Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ExportedConstructSignature extends ExportedDeclaration {
    private final List<ExportedParameter> parameters;
    private final ExportedType returnType;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ExportedConstructSignature copy$default(ExportedConstructSignature exportedConstructSignature, List list, ExportedType exportedType, int i, Object obj) {
        if ((i & 1) != 0) {
            list = exportedConstructSignature.parameters;
        }
        if ((i & 2) != 0) {
            exportedType = exportedConstructSignature.returnType;
        }
        return exportedConstructSignature.copy(list, exportedType);
    }

    public final List<ExportedParameter> component1() {
        return this.parameters;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ExportedType getReturnType() {
        return this.returnType;
    }

    public final ExportedConstructSignature copy(List<ExportedParameter> parameters, ExportedType returnType) {
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        return new ExportedConstructSignature(parameters, returnType);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExportedConstructSignature)) {
            return false;
        }
        ExportedConstructSignature exportedConstructSignature = (ExportedConstructSignature) other;
        return Intrinsics.areEqual(this.parameters, exportedConstructSignature.parameters) && Intrinsics.areEqual(this.returnType, exportedConstructSignature.returnType);
    }

    public int hashCode() {
        return (this.parameters.hashCode() * 31) + this.returnType.hashCode();
    }

    public String toString() {
        return "ExportedConstructSignature(parameters=" + this.parameters + ", returnType=" + this.returnType + ')';
    }

    public final List<ExportedParameter> getParameters() {
        return this.parameters;
    }

    public final ExportedType getReturnType() {
        return this.returnType;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExportedConstructSignature(List<ExportedParameter> parameters, ExportedType returnType) {
        super(null);
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        this.parameters = parameters;
        this.returnType = returnType;
    }
}
