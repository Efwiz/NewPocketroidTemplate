package org.jetbrains.kotlin.js.analyzer;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.AnalysisResult;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;

/* JADX INFO: compiled from: JsAnalysisResult.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0016\u0018\u0000 \u000b2\u00020\u0001:\u0002\u000b\fB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/js/analyzer/JsAnalysisResult;", "Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "bindingTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "shouldGenerateCode", "", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Z)V", "getBindingTrace", "()Lorg/jetbrains/kotlin/resolve/BindingTrace;", "Companion", "RetryWithAdditionalRoots", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class JsAnalysisResult extends AnalysisResult {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final BindingTrace bindingTrace;

    @JvmStatic
    public static final JsAnalysisResult success(BindingTrace bindingTrace, ModuleDescriptor moduleDescriptor) {
        return INSTANCE.success(bindingTrace, moduleDescriptor);
    }

    @JvmStatic
    public static final JsAnalysisResult success(BindingTrace bindingTrace, ModuleDescriptor moduleDescriptor, boolean z) {
        return INSTANCE.success(bindingTrace, moduleDescriptor, z);
    }

    public final BindingTrace getBindingTrace() {
        return this.bindingTrace;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public JsAnalysisResult(BindingTrace bindingTrace, ModuleDescriptor moduleDescriptor, boolean z) {
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        BindingContext context = bindingTrace.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        super(context, moduleDescriptor, z);
        this.bindingTrace = bindingTrace;
    }

    /* JADX INFO: compiled from: JsAnalysisResult.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/analyzer/JsAnalysisResult$Companion;", "", "()V", FirebaseAnalytics.Param.SUCCESS, "Lorg/jetbrains/kotlin/js/analyzer/JsAnalysisResult;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "shouldGenerateCode", "", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final JsAnalysisResult success(BindingTrace trace, ModuleDescriptor module) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(module, "module");
            return new JsAnalysisResult(trace, module, true);
        }

        @JvmStatic
        public final JsAnalysisResult success(BindingTrace trace, ModuleDescriptor module, boolean shouldGenerateCode) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(module, "module");
            return new JsAnalysisResult(trace, module, shouldGenerateCode);
        }
    }

    /* JADX INFO: compiled from: JsAnalysisResult.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/js/analyzer/JsAnalysisResult$RetryWithAdditionalRoots;", "Lorg/jetbrains/kotlin/js/analyzer/JsAnalysisResult;", "bindingTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "additionalKotlinRoots", "", "Ljava/io/File;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Ljava/util/List;)V", "getAdditionalKotlinRoots", "()Ljava/util/List;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class RetryWithAdditionalRoots extends JsAnalysisResult {
        private final List<File> additionalKotlinRoots;

        public final List<File> getAdditionalKotlinRoots() {
            return this.additionalKotlinRoots;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public RetryWithAdditionalRoots(BindingTrace bindingTrace, ModuleDescriptor moduleDescriptor, List<? extends File> additionalKotlinRoots) {
            super(bindingTrace, moduleDescriptor, false);
            Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
            Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
            Intrinsics.checkNotNullParameter(additionalKotlinRoots, "additionalKotlinRoots");
            this.additionalKotlinRoots = additionalKotlinRoots;
        }
    }
}
