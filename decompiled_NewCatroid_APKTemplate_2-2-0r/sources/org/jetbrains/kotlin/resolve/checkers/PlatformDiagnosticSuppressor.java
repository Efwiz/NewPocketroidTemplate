package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.container.PlatformSpecificExtension;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: compiled from: PlatformDiagnosticSuppressor.kt */
/* JADX INFO: loaded from: classes6.dex */
@DefaultImplementation(impl = Default.class)
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000bJ\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\u0017J\u0018\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/PlatformDiagnosticSuppressor;", "Lorg/jetbrains/kotlin/container/PlatformSpecificExtension;", "shouldReportNoBody", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "shouldReportUnusedParameter", "parameter", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "Default", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PlatformDiagnosticSuppressor extends PlatformSpecificExtension<PlatformDiagnosticSuppressor> {
    boolean shouldReportNoBody(CallableMemberDescriptor descriptor);

    @Deprecated(message = "Use shouldReportUnusedParameter with bindingContext parameter")
    default boolean shouldReportUnusedParameter(VariableDescriptor parameter) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        return true;
    }

    default boolean shouldReportUnusedParameter(VariableDescriptor parameter, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        return true;
    }

    /* JADX INFO: compiled from: PlatformDiagnosticSuppressor.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/PlatformDiagnosticSuppressor$Default;", "Lorg/jetbrains/kotlin/resolve/checkers/PlatformDiagnosticSuppressor;", "()V", "shouldReportNoBody", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "shouldReportUnusedParameter", "parameter", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements PlatformDiagnosticSuppressor {
        public static final Default INSTANCE = new Default();

        @Override // org.jetbrains.kotlin.resolve.checkers.PlatformDiagnosticSuppressor
        public boolean shouldReportNoBody(CallableMemberDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return true;
        }

        @Override // org.jetbrains.kotlin.resolve.checkers.PlatformDiagnosticSuppressor
        public boolean shouldReportUnusedParameter(VariableDescriptor parameter, BindingContext bindingContext) {
            Intrinsics.checkNotNullParameter(parameter, "parameter");
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            return true;
        }

        private Default() {
        }
    }
}
