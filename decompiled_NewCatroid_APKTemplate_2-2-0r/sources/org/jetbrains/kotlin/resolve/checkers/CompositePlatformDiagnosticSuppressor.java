package org.jetbrains.kotlin.resolve.checkers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: compiled from: PlatformDiagnosticSuppressor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0017J\u0018\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/CompositePlatformDiagnosticSuppressor;", "Lorg/jetbrains/kotlin/resolve/checkers/PlatformDiagnosticSuppressor;", "suppressors", "", "(Ljava/util/List;)V", "shouldReportNoBody", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "shouldReportUnusedParameter", "parameter", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompositePlatformDiagnosticSuppressor implements PlatformDiagnosticSuppressor {
    private final List<PlatformDiagnosticSuppressor> suppressors;

    /* JADX WARN: Multi-variable type inference failed */
    public CompositePlatformDiagnosticSuppressor(List<? extends PlatformDiagnosticSuppressor> suppressors) {
        Intrinsics.checkNotNullParameter(suppressors, "suppressors");
        this.suppressors = suppressors;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.PlatformDiagnosticSuppressor
    public boolean shouldReportUnusedParameter(VariableDescriptor parameter, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        List<PlatformDiagnosticSuppressor> list = this.suppressors;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (!((PlatformDiagnosticSuppressor) it.next()).shouldReportUnusedParameter(parameter, bindingContext)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.PlatformDiagnosticSuppressor
    @Deprecated(message = "Use shouldReportUnusedParameter with bindingContext parameter")
    public boolean shouldReportUnusedParameter(VariableDescriptor parameter) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        List<PlatformDiagnosticSuppressor> list = this.suppressors;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (!((PlatformDiagnosticSuppressor) it.next()).shouldReportUnusedParameter(parameter)) {
                return false;
            }
        }
        return true;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.PlatformDiagnosticSuppressor
    public boolean shouldReportNoBody(CallableMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        List<PlatformDiagnosticSuppressor> list = this.suppressors;
        if ((list instanceof Collection) && list.isEmpty()) {
            return true;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (!((PlatformDiagnosticSuppressor) it.next()).shouldReportNoBody(descriptor)) {
                return false;
            }
        }
        return true;
    }
}
