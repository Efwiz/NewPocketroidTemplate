package org.jetbrains.kotlin.cli.jvm.compiler;

import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.asJava.LightClassGenerationSupport;
import org.jetbrains.kotlin.asJava.classes.ImplUtilsKt;
import org.jetbrains.kotlin.asJava.classes.KtUltraLightSupport;
import org.jetbrains.kotlin.asJava.classes.UltraLightUtilsKt;
import org.jetbrains.kotlin.cli.jvm.compiler.builder.LightClassConstructionContext;
import org.jetbrains.kotlin.codegen.ClassBuilderMode;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.load.java.components.JavaDeprecationSettings;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: CliLightClassGenerationSupport.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001!B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0015\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u0016H\u0014J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0016R\u0011\u0010\u0007\u001a\u00020\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/CliLightClassGenerationSupport;", "Lorg/jetbrains/kotlin/asJava/LightClassGenerationSupport;", "traceHolder", "Lorg/jetbrains/kotlin/cli/jvm/compiler/CliTraceHolder;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lorg/jetbrains/kotlin/cli/jvm/compiler/CliTraceHolder;Lcom/intellij/openapi/project/Project;)V", "context", "Lorg/jetbrains/kotlin/cli/jvm/compiler/builder/LightClassConstructionContext;", "getContext", "()Lorg/jetbrains/kotlin/cli/jvm/compiler/builder/LightClassConstructionContext;", "getTraceHolder", "()Lorg/jetbrains/kotlin/cli/jvm/compiler/CliTraceHolder;", "ultraLightSupport", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "getUltraLightSupport", "()Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "ultraLightSupport$delegate", "Lkotlin/Lazy;", "analyze", "Lorg/jetbrains/kotlin/resolve/BindingContext;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "analyzeAnnotation", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "analyzeWithContent", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getUltraLightClassSupport", "resolveToDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "CliLightClassSupport", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CliLightClassGenerationSupport extends LightClassGenerationSupport {
    private final Project project;
    private final CliTraceHolder traceHolder;

    /* JADX INFO: renamed from: ultraLightSupport$delegate, reason: from kotlin metadata */
    private final Lazy ultraLightSupport;

    public final CliTraceHolder getTraceHolder() {
        return this.traceHolder;
    }

    public CliLightClassGenerationSupport(CliTraceHolder traceHolder, Project project) {
        Intrinsics.checkNotNullParameter(traceHolder, "traceHolder");
        Intrinsics.checkNotNullParameter(project, "project");
        this.traceHolder = traceHolder;
        this.project = project;
        this.ultraLightSupport = ImplUtilsKt.lazyPub(new Function0<CliLightClassSupport>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.CliLightClassGenerationSupport$ultraLightSupport$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final CliLightClassGenerationSupport.CliLightClassSupport invoke() {
                return new CliLightClassGenerationSupport.CliLightClassSupport(this.this$0.project, this.this$0.getTraceHolder().getLanguageVersionSettings(), this.this$0.getTraceHolder().getJvmTarget());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: CliLightClassGenerationSupport.kt */
    @Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0016R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\u00020\u001a8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u001b\u0010\u001d\u001a\u00020\u001e8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b\u001f\u0010 ¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/CliLightClassGenerationSupport$CliLightClassSupport;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "jvmTarget", "Lorg/jetbrains/kotlin/config/JvmTarget;", "(Lcom/intellij/openapi/project/Project;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/config/JvmTarget;)V", "deprecationResolver", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "getDeprecationResolver", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "getJvmTarget", "()Lorg/jetbrains/kotlin/config/JvmTarget;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "getModuleDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "moduleName", "", "getModuleName", "()Ljava/lang/String;", "traceHolder", "Lorg/jetbrains/kotlin/cli/jvm/compiler/CliTraceHolder;", "getTraceHolder", "()Lorg/jetbrains/kotlin/cli/jvm/compiler/CliTraceHolder;", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "getTypeMapper", "()Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "typeMapper$delegate", "Lkotlin/Lazy;", "possiblyHasAlias", "", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "shortName", "Lorg/jetbrains/kotlin/name/Name;", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class CliLightClassSupport implements KtUltraLightSupport {
        private final JvmTarget jvmTarget;
        private final LanguageVersionSettings languageVersionSettings;
        private final Project project;

        /* JADX INFO: renamed from: typeMapper$delegate, reason: from kotlin metadata */
        private final Lazy typeMapper;

        @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightSupport
        public boolean possiblyHasAlias(KtFile file, Name shortName) {
            Intrinsics.checkNotNullParameter(file, "file");
            Intrinsics.checkNotNullParameter(shortName, "shortName");
            return true;
        }

        public CliLightClassSupport(Project project, LanguageVersionSettings languageVersionSettings, JvmTarget jvmTarget) {
            Intrinsics.checkNotNullParameter(project, "project");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            Intrinsics.checkNotNullParameter(jvmTarget, "jvmTarget");
            this.project = project;
            this.languageVersionSettings = languageVersionSettings;
            this.jvmTarget = jvmTarget;
            this.typeMapper = ImplUtilsKt.lazyPub(new Function0<KotlinTypeMapper>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.CliLightClassGenerationSupport$CliLightClassSupport$typeMapper$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final KotlinTypeMapper invoke() {
                    BindingContext EMPTY = BindingContext.EMPTY;
                    Intrinsics.checkNotNullExpressionValue(EMPTY, "EMPTY");
                    ClassBuilderMode LIGHT_CLASSES = ClassBuilderMode.LIGHT_CLASSES;
                    Intrinsics.checkNotNullExpressionValue(LIGHT_CLASSES, "LIGHT_CLASSES");
                    return new KotlinTypeMapper(EMPTY, LIGHT_CLASSES, this.this$0.getModuleName(), this.this$0.getLanguageVersionSettings(), false, this.this$0.getJvmTarget(), false, AnonymousClass1.INSTANCE, AnonymousClass2.INSTANCE, 64, null);
                }

                /* JADX INFO: renamed from: org.jetbrains.kotlin.cli.jvm.compiler.CliLightClassGenerationSupport$CliLightClassSupport$typeMapper$2$1, reason: invalid class name */
                /* JADX INFO: compiled from: CliLightClassGenerationSupport.kt */
                @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<KotlinType, KotlinType> {
                    public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

                    AnonymousClass1() {
                        super(1);
                    }

                    @Override // kotlin.jvm.internal.CallableReference
                    public final String getSignature() {
                        return "cleanFromAnonymousTypes(Lorg/jetbrains/kotlin/types/KotlinType;)Lorg/jetbrains/kotlin/types/KotlinType;";
                    }

                    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                    public final String getName() {
                        return "cleanFromAnonymousTypes";
                    }

                    @Override // kotlin.jvm.internal.CallableReference
                    public final KDeclarationContainer getOwner() {
                        return Reflection.getOrCreateKotlinPackage(UltraLightUtilsKt.class, "cli-base");
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final KotlinType invoke(KotlinType p0) {
                        Intrinsics.checkNotNullParameter(p0, "p0");
                        return UltraLightUtilsKt.cleanFromAnonymousTypes(p0);
                    }
                }

                /* JADX INFO: renamed from: org.jetbrains.kotlin.cli.jvm.compiler.CliLightClassGenerationSupport$CliLightClassSupport$typeMapper$2$2, reason: invalid class name */
                /* JADX INFO: compiled from: CliLightClassGenerationSupport.kt */
                @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
                /* synthetic */ class AnonymousClass2 extends FunctionReference implements Function1<ClassDescriptor, String> {
                    public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

                    AnonymousClass2() {
                        super(1);
                    }

                    @Override // kotlin.jvm.internal.CallableReference
                    public final String getSignature() {
                        return "tryGetPredefinedName(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)Ljava/lang/String;";
                    }

                    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                    public final String getName() {
                        return "tryGetPredefinedName";
                    }

                    @Override // kotlin.jvm.internal.CallableReference
                    public final KDeclarationContainer getOwner() {
                        return Reflection.getOrCreateKotlinPackage(UltraLightUtilsKt.class, "cli-base");
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final String invoke(ClassDescriptor p0) {
                        Intrinsics.checkNotNullParameter(p0, "p0");
                        return UltraLightUtilsKt.tryGetPredefinedName(p0);
                    }
                }
            });
        }

        @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightSupport
        public LanguageVersionSettings getLanguageVersionSettings() {
            return this.languageVersionSettings;
        }

        @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightSupport
        public JvmTarget getJvmTarget() {
            return this.jvmTarget;
        }

        private final CliTraceHolder getTraceHolder() {
            LightClassGenerationSupport companion = LightClassGenerationSupport.INSTANCE.getInstance(this.project);
            Intrinsics.checkNotNull(companion, "null cannot be cast to non-null type org.jetbrains.kotlin.cli.jvm.compiler.CliLightClassGenerationSupport");
            return ((CliLightClassGenerationSupport) companion).getTraceHolder();
        }

        @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightSupport
        public ModuleDescriptor getModuleDescriptor() {
            return getTraceHolder().getModule();
        }

        @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightSupport
        public String getModuleName() {
            String moduleName = JvmCodegenUtil.getModuleName(getModuleDescriptor());
            Intrinsics.checkNotNullExpressionValue(moduleName, "getModuleName(...)");
            return moduleName;
        }

        @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightSupport
        public DeprecationResolver getDeprecationResolver() {
            StorageManager NO_LOCKS = LockBasedStorageManager.NO_LOCKS;
            Intrinsics.checkNotNullExpressionValue(NO_LOCKS, "NO_LOCKS");
            return new DeprecationResolver(NO_LOCKS, getLanguageVersionSettings(), JavaDeprecationSettings.INSTANCE);
        }

        @Override // org.jetbrains.kotlin.asJava.classes.KtUltraLightSupport
        public KotlinTypeMapper getTypeMapper() {
            return (KotlinTypeMapper) this.typeMapper.getValue();
        }
    }

    private final KtUltraLightSupport getUltraLightSupport() {
        return (KtUltraLightSupport) this.ultraLightSupport.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.LightClassGenerationSupport
    protected KtUltraLightSupport getUltraLightClassSupport(KtElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (!Intrinsics.areEqual(element.getProject(), this.project)) {
            throw new IllegalArgumentException("ULC support created from another project from requested".toString());
        }
        return getUltraLightSupport();
    }

    public final LightClassConstructionContext getContext() {
        return new LightClassConstructionContext(this.traceHolder.getBindingContext(), this.traceHolder.getModule(), this.traceHolder.getLanguageVersionSettings(), this.traceHolder.getJvmTarget());
    }

    @Override // org.jetbrains.kotlin.asJava.LightClassGenerationSupport
    public DeclarationDescriptor resolveToDescriptor(KtDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return (DeclarationDescriptor) this.traceHolder.getBindingContext().get(BindingContext.DECLARATION_TO_DESCRIPTOR, declaration);
    }

    @Override // org.jetbrains.kotlin.asJava.LightClassGenerationSupport
    public BindingContext analyze(KtElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return this.traceHolder.getBindingContext();
    }

    @Override // org.jetbrains.kotlin.asJava.LightClassGenerationSupport
    public AnnotationDescriptor analyzeAnnotation(KtAnnotationEntry element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return (AnnotationDescriptor) this.traceHolder.getBindingContext().get(BindingContext.ANNOTATION, element);
    }

    @Override // org.jetbrains.kotlin.asJava.LightClassGenerationSupport
    public BindingContext analyzeWithContent(KtClassOrObject element) {
        Intrinsics.checkNotNullParameter(element, "element");
        return this.traceHolder.getBindingContext();
    }
}
