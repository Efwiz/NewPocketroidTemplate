package org.jetbrains.kotlin.ir.backend.js.export;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ExportModel.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0006HÆ\u0003J#\u0010\u0011\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\t2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\b\u0010\nR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedConstructor;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedDeclaration;", "parameters", "", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedParameter;", "visibility", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedVisibility;", "(Ljava/util/List;Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedVisibility;)V", "isProtected", "", "()Z", "getParameters", "()Ljava/util/List;", "getVisibility", "()Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedVisibility;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ExportedConstructor extends ExportedDeclaration {
    private final List<ExportedParameter> parameters;
    private final ExportedVisibility visibility;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ExportedConstructor copy$default(ExportedConstructor exportedConstructor, List list, ExportedVisibility exportedVisibility, int i, Object obj) {
        if ((i & 1) != 0) {
            list = exportedConstructor.parameters;
        }
        if ((i & 2) != 0) {
            exportedVisibility = exportedConstructor.visibility;
        }
        return exportedConstructor.copy(list, exportedVisibility);
    }

    public final List<ExportedParameter> component1() {
        return this.parameters;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ExportedVisibility getVisibility() {
        return this.visibility;
    }

    public final ExportedConstructor copy(List<ExportedParameter> parameters, ExportedVisibility visibility) {
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        return new ExportedConstructor(parameters, visibility);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExportedConstructor)) {
            return false;
        }
        ExportedConstructor exportedConstructor = (ExportedConstructor) other;
        return Intrinsics.areEqual(this.parameters, exportedConstructor.parameters) && this.visibility == exportedConstructor.visibility;
    }

    public int hashCode() {
        return (this.parameters.hashCode() * 31) + this.visibility.hashCode();
    }

    public String toString() {
        return "ExportedConstructor(parameters=" + this.parameters + ", visibility=" + this.visibility + ')';
    }

    public final List<ExportedParameter> getParameters() {
        return this.parameters;
    }

    public final ExportedVisibility getVisibility() {
        return this.visibility;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExportedConstructor(List<ExportedParameter> parameters, ExportedVisibility visibility) {
        super(null);
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        this.parameters = parameters;
        this.visibility = visibility;
    }

    public final boolean isProtected() {
        return this.visibility == ExportedVisibility.PROTECTED;
    }
}
