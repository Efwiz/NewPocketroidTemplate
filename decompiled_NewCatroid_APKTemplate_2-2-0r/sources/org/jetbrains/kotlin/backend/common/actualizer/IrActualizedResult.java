package org.jetbrains.kotlin.backend.common.actualizer;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IrActualizer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u0019\u0010\t\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/actualizer/IrActualizedResult;", "", "actualizedExpectDeclarations", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "(Ljava/util/List;)V", "getActualizedExpectDeclarations", "()Ljava/util/List;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class IrActualizedResult {
    private final List<IrDeclaration> actualizedExpectDeclarations;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IrActualizedResult copy$default(IrActualizedResult irActualizedResult, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            list = irActualizedResult.actualizedExpectDeclarations;
        }
        return irActualizedResult.copy(list);
    }

    public final List<IrDeclaration> component1() {
        return this.actualizedExpectDeclarations;
    }

    public final IrActualizedResult copy(List<? extends IrDeclaration> actualizedExpectDeclarations) {
        Intrinsics.checkNotNullParameter(actualizedExpectDeclarations, "actualizedExpectDeclarations");
        return new IrActualizedResult(actualizedExpectDeclarations);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof IrActualizedResult) && Intrinsics.areEqual(this.actualizedExpectDeclarations, ((IrActualizedResult) other).actualizedExpectDeclarations);
    }

    public int hashCode() {
        return this.actualizedExpectDeclarations.hashCode();
    }

    public String toString() {
        return "IrActualizedResult(actualizedExpectDeclarations=" + this.actualizedExpectDeclarations + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public IrActualizedResult(List<? extends IrDeclaration> actualizedExpectDeclarations) {
        Intrinsics.checkNotNullParameter(actualizedExpectDeclarations, "actualizedExpectDeclarations");
        this.actualizedExpectDeclarations = actualizedExpectDeclarations;
    }

    public final List<IrDeclaration> getActualizedExpectDeclarations() {
        return this.actualizedExpectDeclarations;
    }
}
