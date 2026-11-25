package org.jetbrains.kotlin.load.java.components;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.OverrideResolver;
import org.jetbrains.kotlin.serialization.deserialization.ErrorReporter;
import org.jetbrains.kotlin.util.slicedMap.BasicWritableSlice;
import org.jetbrains.kotlin.util.slicedMap.Slices;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: TraceBasedErrorReporter.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u001e\u0010\t\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/load/java/components/TraceBasedErrorReporter;", "Lorg/jetbrains/kotlin/serialization/deserialization/ErrorReporter;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;)V", "reportCannotInferVisibility", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "reportIncompleteHierarchy", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "unresolvedSuperClasses", "", "", "Companion", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TraceBasedErrorReporter implements ErrorReporter {
    public static final WritableSlice<ClassDescriptor, List<String>> INCOMPLETE_HIERARCHY;
    private final BindingTrace trace;

    public TraceBasedErrorReporter(BindingTrace trace) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        this.trace = trace;
    }

    static {
        WritableSlice<ClassDescriptor, List<String>> writableSliceCreateCollectiveSlice = Slices.createCollectiveSlice();
        Intrinsics.checkNotNullExpressionValue(writableSliceCreateCollectiveSlice, "createCollectiveSlice(...)");
        INCOMPLETE_HIERARCHY = writableSliceCreateCollectiveSlice;
        BasicWritableSlice.initSliceDebugNames(TraceBasedErrorReporter.class);
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ErrorReporter
    public void reportIncompleteHierarchy(ClassDescriptor descriptor, List<String> unresolvedSuperClasses) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(unresolvedSuperClasses, "unresolvedSuperClasses");
        this.trace.record(INCOMPLETE_HIERARCHY, descriptor, unresolvedSuperClasses);
    }

    @Override // org.jetbrains.kotlin.serialization.deserialization.ErrorReporter
    public void reportCannotInferVisibility(CallableMemberDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        OverrideResolver.INSTANCE.createCannotInferVisibilityReporter(this.trace).invoke(descriptor);
    }
}
