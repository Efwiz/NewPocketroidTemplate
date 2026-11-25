package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: compiled from: CodegenFactory.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u0000 \u00142\u00020\u0001:\u0004\u0012\u0013\u0014\u0015J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0003H\u0016J\u001e\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u00032\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH&J\u0010\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0010H&J\u0018\u0010\u0011\u001a\u00020\u00102\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0016À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CodegenFactory;", "", "convertToIr", "Lorg/jetbrains/kotlin/codegen/CodegenFactory$BackendInput;", "input", "Lorg/jetbrains/kotlin/codegen/CodegenFactory$IrConversionInput;", "generateModule", "", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "getModuleChunkBackendInput", "wholeBackendInput", "sourceFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "invokeCodegen", "Lorg/jetbrains/kotlin/codegen/CodegenFactory$CodegenInput;", "invokeLowerings", "BackendInput", "CodegenInput", "Companion", "IrConversionInput", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CodegenFactory {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: CodegenFactory.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\bf\u0018\u00002\u00020\u0001ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0002À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CodegenFactory$BackendInput;", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface BackendInput {
    }

    /* JADX INFO: compiled from: CodegenFactory.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CodegenFactory$CodegenInput;", "", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "getState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface CodegenInput {
        GenerationState getState();
    }

    BackendInput convertToIr(IrConversionInput input);

    BackendInput getModuleChunkBackendInput(BackendInput wholeBackendInput, Collection<? extends KtFile> sourceFiles);

    void invokeCodegen(CodegenInput input);

    CodegenInput invokeLowerings(GenerationState state, BackendInput input);

    default void generateModule(GenerationState state, BackendInput input) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(input, "input");
        invokeCodegen(invokeLowerings(state, input));
    }

    /* JADX INFO: compiled from: CodegenFactory.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0013\u0018\u0000 \"2\u00020\u0001:\u0001\"BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0010¢\u0006\u0002\u0010\u0012R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0011\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001a¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CodegenFactory$IrConversionInput;", "", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "ignoreErrors", "", "skipBodies", "(Lcom/intellij/openapi/project/Project;Ljava/util/Collection;Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/BindingContext;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;ZZ)V", "getBindingContext", "()Lorg/jetbrains/kotlin/resolve/BindingContext;", "getConfiguration", "()Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getFiles", "()Ljava/util/Collection;", "getIgnoreErrors", "()Z", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getModule", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getProject", "()Lcom/intellij/openapi/project/Project;", "getSkipBodies", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IrConversionInput {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final BindingContext bindingContext;
        private final CompilerConfiguration configuration;
        private final Collection<KtFile> files;
        private final boolean ignoreErrors;
        private final LanguageVersionSettings languageVersionSettings;
        private final ModuleDescriptor module;
        private final Project project;
        private final boolean skipBodies;

        /* JADX WARN: Multi-variable type inference failed */
        public IrConversionInput(Project project, Collection<? extends KtFile> files, CompilerConfiguration configuration, ModuleDescriptor module, BindingContext bindingContext, LanguageVersionSettings languageVersionSettings, boolean z, boolean z2) {
            Intrinsics.checkNotNullParameter(project, "project");
            Intrinsics.checkNotNullParameter(files, "files");
            Intrinsics.checkNotNullParameter(configuration, "configuration");
            Intrinsics.checkNotNullParameter(module, "module");
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            this.project = project;
            this.files = files;
            this.configuration = configuration;
            this.module = module;
            this.bindingContext = bindingContext;
            this.languageVersionSettings = languageVersionSettings;
            this.ignoreErrors = z;
            this.skipBodies = z2;
        }

        public final Project getProject() {
            return this.project;
        }

        public final Collection<KtFile> getFiles() {
            return this.files;
        }

        public final CompilerConfiguration getConfiguration() {
            return this.configuration;
        }

        public final ModuleDescriptor getModule() {
            return this.module;
        }

        public final BindingContext getBindingContext() {
            return this.bindingContext;
        }

        public final LanguageVersionSettings getLanguageVersionSettings() {
            return this.languageVersionSettings;
        }

        public final boolean getIgnoreErrors() {
            return this.ignoreErrors;
        }

        public final boolean getSkipBodies() {
            return this.skipBodies;
        }

        /* JADX INFO: compiled from: CodegenFactory.kt */
        @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CodegenFactory$IrConversionInput$Companion;", "", "()V", "fromGenerationStateAndFiles", "Lorg/jetbrains/kotlin/codegen/CodegenFactory$IrConversionInput;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final IrConversionInput fromGenerationStateAndFiles(GenerationState state, Collection<? extends KtFile> files) {
                Intrinsics.checkNotNullParameter(state, "state");
                Intrinsics.checkNotNullParameter(files, "files");
                return new IrConversionInput(state.getProject(), files, state.getConfiguration(), state.getModule(), state.getOriginalFrontendBindingContext(), state.getLanguageVersionSettings(), state.getIgnoreErrors(), !state.getClassBuilderMode().generateBodies);
            }
        }
    }

    /* JADX INFO: compiled from: CodegenFactory.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/CodegenFactory$Companion;", "", "()V", "doCheckCancelled", "", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final void doCheckCancelled(GenerationState state) {
            Intrinsics.checkNotNullParameter(state, "state");
            if (state.getClassBuilderMode().generateBodies) {
                ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
            }
        }
    }
}
