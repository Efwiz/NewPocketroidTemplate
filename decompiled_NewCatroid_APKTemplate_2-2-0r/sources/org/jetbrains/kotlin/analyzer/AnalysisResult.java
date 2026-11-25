package org.jetbrains.kotlin.analyzer;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.compress.archivers.zip.UnixStat;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.types.error.ErrorUtils;

/* JADX INFO: compiled from: AnalysisResult.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0003\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0016\u0018\u0000 \u001d2\u00020\u0001:\u0004\u001d\u001e\u001f B!\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0013\u001a\u00020\u0003H\u0086\u0002J\t\u0010\u0014\u001a\u00020\u0005H\u0086\u0002J\t\u0010\u0015\u001a\u00020\u0007H\u0086\u0002J\u0013\u0010\u0016\u001a\u00020\u00072\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0006\u0010\u001a\u001a\u00020\u0007J\u0006\u0010\u001b\u001a\u00020\u001cR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "shouldGenerateCode", "", "(Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Z)V", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "error", "", "getError", "()Ljava/lang/Throwable;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getShouldGenerateCode", "()Z", "component1", "component2", "component3", Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "isError", "throwIfError", "", "Companion", "CompilationError", "InternalError", "RetryWithAdditionalRoots", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class AnalysisResult {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    private static final AnalysisResult EMPTY;
    private final BindingContext bindingContext;
    private final ModuleDescriptor moduleDescriptor;
    private final boolean shouldGenerateCode;

    @JvmStatic
    public static final AnalysisResult compilationError(BindingContext bindingContext) {
        return INSTANCE.compilationError(bindingContext);
    }

    @JvmStatic
    public static final AnalysisResult internalError(BindingContext bindingContext, Throwable th) {
        return INSTANCE.internalError(bindingContext, th);
    }

    @JvmStatic
    public static final AnalysisResult success(BindingContext bindingContext, ModuleDescriptor moduleDescriptor) {
        return INSTANCE.success(bindingContext, moduleDescriptor);
    }

    @JvmStatic
    public static final AnalysisResult success(BindingContext bindingContext, ModuleDescriptor moduleDescriptor, boolean z) {
        return INSTANCE.success(bindingContext, moduleDescriptor, z);
    }

    protected AnalysisResult(BindingContext bindingContext, ModuleDescriptor moduleDescriptor, boolean z) {
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        this.bindingContext = bindingContext;
        this.moduleDescriptor = moduleDescriptor;
        this.shouldGenerateCode = z;
    }

    public /* synthetic */ AnalysisResult(BindingContext bindingContext, ModuleDescriptor moduleDescriptor, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(bindingContext, moduleDescriptor, (i & 4) != 0 ? true : z);
    }

    public final BindingContext getBindingContext() {
        return this.bindingContext;
    }

    public final ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    public final boolean getShouldGenerateCode() {
        return this.shouldGenerateCode;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof AnalysisResult) {
            AnalysisResult analysisResult = (AnalysisResult) other;
            if (Intrinsics.areEqual(this.bindingContext, analysisResult.bindingContext) && Intrinsics.areEqual(this.moduleDescriptor, analysisResult.moduleDescriptor) && this.shouldGenerateCode == analysisResult.shouldGenerateCode) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((UnixStat.DEFAULT_DIR_PERM + this.bindingContext.hashCode()) * 29) + this.moduleDescriptor.hashCode()) * 29) + Boolean.hashCode(this.shouldGenerateCode);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final BindingContext getBindingContext() {
        return this.bindingContext;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ModuleDescriptor getModuleDescriptor() {
        return this.moduleDescriptor;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getShouldGenerateCode() {
        return this.shouldGenerateCode;
    }

    public final Throwable getError() {
        if (this instanceof InternalError) {
            return ((InternalError) this).getException();
        }
        throw new IllegalStateException("Should only be called for error analysis result");
    }

    public final boolean isError() {
        return (this instanceof InternalError) || (this instanceof CompilationError);
    }

    public final void throwIfError() {
        if (this instanceof InternalError) {
            throw new IllegalStateException("failed to analyze: " + getError(), getError());
        }
        if (this instanceof CompilationError) {
            throw new CompilationErrorException();
        }
    }

    /* JADX INFO: compiled from: AnalysisResult.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/AnalysisResult$CompilationError;", "Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "(Lorg/jetbrains/kotlin/resolve/BindingContext;)V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class CompilationError extends AnalysisResult {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CompilationError(BindingContext bindingContext) {
            super(bindingContext, ErrorUtils.INSTANCE.getErrorModule(), false, 4, null);
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        }
    }

    /* JADX INFO: compiled from: AnalysisResult.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/AnalysisResult$InternalError;", "Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_EXCEPTION, "", "(Lorg/jetbrains/kotlin/resolve/BindingContext;Ljava/lang/Throwable;)V", "getException", "()Ljava/lang/Throwable;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class InternalError extends AnalysisResult {
        private final Throwable exception;

        public final Throwable getException() {
            return this.exception;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public InternalError(BindingContext bindingContext, Throwable exception) {
            super(bindingContext, ErrorUtils.INSTANCE.getErrorModule(), false, 4, null);
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            Intrinsics.checkNotNullParameter(exception, "exception");
            this.exception = exception;
        }
    }

    /* JADX INFO: compiled from: AnalysisResult.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/AnalysisResult$RetryWithAdditionalRoots;", "Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "additionalJavaRoots", "", "Ljava/io/File;", "additionalKotlinRoots", "additionalClassPathRoots", "addToEnvironment", "", "(Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Ljava/util/List;Ljava/util/List;Ljava/util/List;Z)V", "getAddToEnvironment", "()Z", "getAdditionalClassPathRoots", "()Ljava/util/List;", "getAdditionalJavaRoots", "getAdditionalKotlinRoots", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class RetryWithAdditionalRoots extends AnalysisResult {
        private final boolean addToEnvironment;
        private final List<File> additionalClassPathRoots;
        private final List<File> additionalJavaRoots;
        private final List<File> additionalKotlinRoots;

        public final List<File> getAdditionalJavaRoots() {
            return this.additionalJavaRoots;
        }

        public final List<File> getAdditionalKotlinRoots() {
            return this.additionalKotlinRoots;
        }

        public /* synthetic */ RetryWithAdditionalRoots(BindingContext bindingContext, ModuleDescriptor moduleDescriptor, List list, List list2, List list3, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(bindingContext, moduleDescriptor, list, list2, (i & 16) != 0 ? CollectionsKt.emptyList() : list3, (i & 32) != 0 ? true : z);
        }

        public final List<File> getAdditionalClassPathRoots() {
            return this.additionalClassPathRoots;
        }

        public final boolean getAddToEnvironment() {
            return this.addToEnvironment;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public RetryWithAdditionalRoots(BindingContext bindingContext, ModuleDescriptor moduleDescriptor, List<? extends File> additionalJavaRoots, List<? extends File> additionalKotlinRoots, List<? extends File> additionalClassPathRoots, boolean z) {
            super(bindingContext, moduleDescriptor, false, 4, null);
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
            Intrinsics.checkNotNullParameter(additionalJavaRoots, "additionalJavaRoots");
            Intrinsics.checkNotNullParameter(additionalKotlinRoots, "additionalKotlinRoots");
            Intrinsics.checkNotNullParameter(additionalClassPathRoots, "additionalClassPathRoots");
            this.additionalJavaRoots = additionalJavaRoots;
            this.additionalKotlinRoots = additionalKotlinRoots;
            this.additionalClassPathRoots = additionalClassPathRoots;
            this.addToEnvironment = z;
        }
    }

    /* JADX INFO: compiled from: AnalysisResult.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0007J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0018\u0010\r\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J \u0010\r\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/AnalysisResult$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "getEMPTY", "()Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "compilationError", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "internalError", "error", "", FirebaseAnalytics.Param.SUCCESS, "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "shouldGenerateCode", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final AnalysisResult getEMPTY() {
            return AnalysisResult.EMPTY;
        }

        @JvmStatic
        public final AnalysisResult success(BindingContext bindingContext, ModuleDescriptor module) {
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            Intrinsics.checkNotNullParameter(module, "module");
            return new AnalysisResult(bindingContext, module, true);
        }

        @JvmStatic
        public final AnalysisResult success(BindingContext bindingContext, ModuleDescriptor module, boolean shouldGenerateCode) {
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            Intrinsics.checkNotNullParameter(module, "module");
            return new AnalysisResult(bindingContext, module, shouldGenerateCode);
        }

        @JvmStatic
        public final AnalysisResult internalError(BindingContext bindingContext, Throwable error) {
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            Intrinsics.checkNotNullParameter(error, "error");
            return new InternalError(bindingContext, error);
        }

        @JvmStatic
        public final AnalysisResult compilationError(BindingContext bindingContext) {
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            return new CompilationError(bindingContext);
        }
    }

    static {
        Companion companion = new Companion(null);
        INSTANCE = companion;
        BindingContext EMPTY2 = BindingContext.EMPTY;
        Intrinsics.checkNotNullExpressionValue(EMPTY2, "EMPTY");
        EMPTY = companion.success(EMPTY2, ErrorUtils.INSTANCE.getErrorModule());
    }
}
