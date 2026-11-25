package org.jetbrains.kotlin.cli.jvm.compiler.builder;

import io.netty.util.internal.StringUtil;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.asJava.builder.ClsWrapperStubPsiFactory;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfo;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiJavaFileStubImpl;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: compiled from: LightClassBuilder.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000Z\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002\u001aj\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2<\u0010\u0010\u001a8\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00020\t0\b¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\u00160\u0011\u001a$\u0010\u0017\u001a\u00020\u00162\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u00062\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0002\"\u0013\u0010\u0000\u001a\u00070\u0001¢\u0006\u0002\b\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "createJavaFileStub", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/PsiJavaFileStub;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "extraJvmDiagnosticsFromBackend", "Lorg/jetbrains/kotlin/cli/jvm/compiler/builder/CodeGenerationResult;", "generateClassFilter", "Lorg/jetbrains/kotlin/codegen/state/GenerationState$GenerateClassFilter;", "context", "Lorg/jetbrains/kotlin/cli/jvm/compiler/builder/LightClassConstructionContext;", CompilerOptions.GENERATE, "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "Lkotlin/ParameterName;", "name", "state", "", "logErrorWithOSInfo", "cause", "", "fqName", "virtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "cli-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LightClassBuilderKt {
    private static final Logger LOG;

    public static final CodeGenerationResult extraJvmDiagnosticsFromBackend(FqName packageFqName, Collection<? extends KtFile> files, GenerationState.GenerateClassFilter generateClassFilter, LightClassConstructionContext context, Function2<? super GenerationState, ? super Collection<? extends KtFile>, Unit> generate) {
        CompilerConfiguration compilerConfiguration;
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(generateClassFilter, "generateClassFilter");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(generate, "generate");
        Collection<? extends KtFile> collection = files;
        Project project = ((KtFile) CollectionsKt.first(collection)).getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        try {
            KotlinLightClassBuilderFactory kotlinLightClassBuilderFactory = new KotlinLightClassBuilderFactory(createJavaFileStub(packageFqName, files));
            KotlinLightClassBuilderFactory kotlinLightClassBuilderFactory2 = kotlinLightClassBuilderFactory;
            ModuleDescriptor module = context.getModule();
            BindingContext bindingContext = context.getBindingContext();
            LanguageVersionSettings languageVersionSettings = context.getLanguageVersionSettings();
            if (languageVersionSettings != null) {
                compilerConfiguration = new CompilerConfiguration();
                CommonConfigurationKeysKt.setLanguageVersionSettings(compilerConfiguration, languageVersionSettings);
                compilerConfiguration.put(JVMConfigurationKeys.JVM_TARGET, context.getJvmTarget());
                compilerConfiguration.setReadOnly(true);
            } else {
                compilerConfiguration = CompilerConfiguration.EMPTY;
            }
            Intrinsics.checkNotNull(compilerConfiguration);
            GenerationState generationStateBuild = new GenerationState.Builder(project, kotlinLightClassBuilderFactory2, module, bindingContext, compilerConfiguration).generateDeclaredClassFilter(generateClassFilter).wantsDiagnostics(false).build();
            generationStateBuild.beforeCompile();
            generationStateBuild.oldBEInitTrace(files);
            generate.invoke(generationStateBuild, files);
            return new CodeGenerationResult(kotlinLightClassBuilderFactory.result(), context.getBindingContext(), generationStateBuild.getCollectedExtraJvmDiagnostics());
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (RuntimeException e2) {
            RuntimeException runtimeException = e2;
            KtFile ktFile = (KtFile) CollectionsKt.firstOrNull(collection);
            logErrorWithOSInfo(runtimeException, packageFqName, ktFile != null ? ktFile.getVirtualFile() : null);
            throw e2;
        }
    }

    private static final PsiJavaFileStub createJavaFileStub(final FqName fqName, final Collection<? extends KtFile> collection) {
        final PsiJavaFileStubImpl psiJavaFileStubImpl = new PsiJavaFileStubImpl(fqName.asString(), true);
        psiJavaFileStubImpl.setPsiFactory(ClsWrapperStubPsiFactory.INSTANCE);
        final FileViewProvider viewProvider = ((KtFile) CollectionsKt.first(collection)).getViewProvider();
        psiJavaFileStubImpl.setPsi(new ClsFileImpl(viewProvider) { // from class: org.jetbrains.kotlin.cli.jvm.compiler.builder.LightClassBuilderKt$createJavaFileStub$fakeFile$1
            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
            /* JADX INFO: renamed from: isPhysical */
            public boolean getIsPhysical() {
                return false;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl
            /* JADX INFO: renamed from: getStub, reason: from getter */
            public PsiJavaFileStubImpl get$javaFileStub() {
                return psiJavaFileStubImpl;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner, org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
            public String getPackageName() {
                String strAsString = fqName.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return strAsString;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl, org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiElement
            public String getText() {
                KtFile ktFile = (KtFile) CollectionsKt.singleOrNull(collection);
                String text = ktFile != null ? ktFile.getText() : null;
                if (text != null) {
                    return text;
                }
                String text2 = super.getText();
                Intrinsics.checkNotNullExpressionValue(text2, "getText(...)");
                return text2;
            }
        });
        return psiJavaFileStubImpl;
    }

    private static final void logErrorWithOSInfo(Throwable th, FqName fqName, VirtualFile virtualFile) {
        String path;
        if (virtualFile == null || (path = virtualFile.getPath()) == null) {
            path = "<null>";
        }
        LOG.error("Could not generate LightClass for " + fqName + " declared in " + path + "\nSystem: " + SystemInfo.OS_NAME + StringUtil.SPACE + SystemInfo.OS_VERSION + " Java Runtime: " + SystemInfo.JAVA_RUNTIME_VERSION, th);
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) CodeGenerationResult.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
    }
}
