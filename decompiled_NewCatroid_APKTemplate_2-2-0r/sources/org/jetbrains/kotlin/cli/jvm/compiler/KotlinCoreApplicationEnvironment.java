package org.jetbrains.kotlin.cli.jvm.compiler;

import com.reandroid.apkeditor.Options;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.jvm.compiler.jarfs.FastJarFileSystem;
import org.jetbrains.kotlin.cli.jvm.modules.CoreJrtFileSystem;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.DynamicBundle;
import org.jetbrains.kotlin.com.intellij.codeInsight.ContainerProvider;
import org.jetbrains.kotlin.com.intellij.codeInsight.runner.JavaMainMethodProvider;
import org.jetbrains.kotlin.com.intellij.core.JavaCoreApplicationEnvironment;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaClassFileType;
import org.jetbrains.kotlin.com.intellij.lang.MetaLanguage;
import org.jetbrains.kotlin.com.intellij.mock.MockApplication;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionsArea;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.psi.FileContextProvider;
import org.jetbrains.kotlin.com.intellij.psi.augment.PsiAugmentProvider;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaFileCodeStyleFacadeFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerAnchorProvider;
import org.jetbrains.kotlin.com.intellij.psi.meta.MetaDataContributor;

/* JADX INFO: compiled from: KotlinCoreApplicationEnvironment.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0002\u001a\u00020\u0003H\u0014J\b\u0010\u000f\u001a\u00020\u0010H\u0014J\u0006\u0010\u0011\u001a\u00020\u0012R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b8F¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreApplicationEnvironment;", "Lorg/jetbrains/kotlin/com/intellij/core/JavaCoreApplicationEnvironment;", "parentDisposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "unitTestMode", "", "(Lcom/intellij/openapi/Disposable;Z)V", "fastJarFileSystem", "Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/FastJarFileSystem;", "getFastJarFileSystem", "()Lorg/jetbrains/kotlin/cli/jvm/compiler/jarfs/FastJarFileSystem;", "fastJarFileSystemField", "fastJarFileSystemFieldInitialized", "createApplication", "Lorg/jetbrains/kotlin/com/intellij/mock/MockApplication;", "createJrtFileSystem", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFileSystem;", "idleCleanup", "", "Companion", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KotlinCoreApplicationEnvironment extends JavaCoreApplicationEnvironment {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private FastJarFileSystem fastJarFileSystemField;
    private boolean fastJarFileSystemFieldInitialized;

    public /* synthetic */ KotlinCoreApplicationEnvironment(Disposable disposable, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(disposable, z);
    }

    private KotlinCoreApplicationEnvironment(Disposable disposable, boolean z) {
        super(disposable, z);
        registerApplicationService(JavaFileCodeStyleFacadeFactory.class, new DummyJavaFileCodeStyleFacadeFactory());
        registerFileType(JavaClassFileType.INSTANCE, Options.TYPE_SIG);
    }

    @Override // org.jetbrains.kotlin.com.intellij.core.CoreApplicationEnvironment
    protected VirtualFileSystem createJrtFileSystem() {
        return new CoreJrtFileSystem();
    }

    @Override // org.jetbrains.kotlin.com.intellij.core.CoreApplicationEnvironment
    protected MockApplication createApplication(Disposable parentDisposable) {
        Intrinsics.checkNotNullParameter(parentDisposable, "parentDisposable");
        MockApplication mockApplicationCreateApplication = super.createApplication(parentDisposable);
        Intrinsics.checkNotNullExpressionValue(mockApplicationCreateApplication, "createApplication(...)");
        return mockApplicationCreateApplication.isUnitTestMode() ? new MockApplication(parentDisposable) { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreApplicationEnvironment.createApplication.1
            @Override // org.jetbrains.kotlin.com.intellij.mock.MockApplication, org.jetbrains.kotlin.com.intellij.openapi.application.Application
            public boolean isUnitTestMode() {
                return true;
            }

            @Override // org.jetbrains.kotlin.com.intellij.mock.MockApplication, org.jetbrains.kotlin.com.intellij.openapi.application.Application
            public boolean isWriteAccessAllowed() {
                return false;
            }
        } : mockApplicationCreateApplication;
    }

    public final FastJarFileSystem getFastJarFileSystem() {
        FastJarFileSystem fastJarFileSystem;
        synchronized (KotlinCoreEnvironment.INSTANCE.getAPPLICATION_LOCK()) {
            if (!this.fastJarFileSystemFieldInitialized) {
                final FastJarFileSystem fastJarFileSystemCreateIfUnmappingPossible = FastJarFileSystem.INSTANCE.createIfUnmappingPossible();
                if (fastJarFileSystemCreateIfUnmappingPossible != null) {
                    Disposer.register(getParentDisposable(), new Disposable() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreApplicationEnvironment$$ExternalSyntheticLambda0
                        @Override // org.jetbrains.kotlin.com.intellij.openapi.Disposable
                        public final void dispose() {
                            KotlinCoreApplicationEnvironment._get_fastJarFileSystem_$lambda$2$lambda$1$lambda$0(fastJarFileSystemCreateIfUnmappingPossible);
                        }
                    });
                } else {
                    fastJarFileSystemCreateIfUnmappingPossible = null;
                }
                this.fastJarFileSystemField = fastJarFileSystemCreateIfUnmappingPossible;
                this.fastJarFileSystemFieldInitialized = true;
            }
            fastJarFileSystem = this.fastJarFileSystemField;
        }
        return fastJarFileSystem;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _get_fastJarFileSystem_$lambda$2$lambda$1$lambda$0(FastJarFileSystem it) {
        Intrinsics.checkNotNullParameter(it, "$it");
        it.clearHandlersCache();
    }

    public final void idleCleanup() {
        FastJarFileSystem fastJarFileSystem = this.fastJarFileSystemField;
        if (fastJarFileSystem != null) {
            fastJarFileSystem.clearHandlersCache();
        }
    }

    /* JADX INFO: compiled from: KotlinCoreApplicationEnvironment.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bJ\b\u0010\t\u001a\u00020\nH\u0002¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreApplicationEnvironment$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/cli/jvm/compiler/KotlinCoreApplicationEnvironment;", "parentDisposable", "Lorg/jetbrains/kotlin/com/intellij/openapi/Disposable;", "unitTestMode", "", "registerExtensionPoints", "", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final KotlinCoreApplicationEnvironment create(Disposable parentDisposable, boolean unitTestMode) {
            Intrinsics.checkNotNullParameter(parentDisposable, "parentDisposable");
            KotlinCoreApplicationEnvironment kotlinCoreApplicationEnvironment = new KotlinCoreApplicationEnvironment(parentDisposable, unitTestMode, null);
            registerExtensionPoints();
            return kotlinCoreApplicationEnvironment;
        }

        private final void registerExtensionPoints() {
            JavaCoreApplicationEnvironment.registerApplicationExtensionPoint(DynamicBundle.LanguageBundleEP.EP_NAME, DynamicBundle.LanguageBundleEP.class);
            JavaCoreApplicationEnvironment.registerApplicationExtensionPoint(FileContextProvider.EP_NAME, FileContextProvider.class);
            JavaCoreApplicationEnvironment.registerApplicationExtensionPoint(MetaDataContributor.EP_NAME, MetaDataContributor.class);
            JavaCoreApplicationEnvironment.registerApplicationExtensionPoint(PsiAugmentProvider.EP_NAME, PsiAugmentProvider.class);
            JavaCoreApplicationEnvironment.registerApplicationExtensionPoint(JavaMainMethodProvider.EP_NAME, JavaMainMethodProvider.class);
            JavaCoreApplicationEnvironment.registerApplicationExtensionPoint(ContainerProvider.EP_NAME, ContainerProvider.class);
            JavaCoreApplicationEnvironment.registerApplicationExtensionPoint(MetaLanguage.EP_NAME, MetaLanguage.class);
            JavaCoreApplicationEnvironment.registerApplicationExtensionPoint(SmartPointerAnchorProvider.EP_NAME, SmartPointerAnchorProvider.class);
            IdeaExtensionPoints ideaExtensionPoints = IdeaExtensionPoints.INSTANCE;
            ExtensionsArea extensionArea = ApplicationManager.getApplication().getExtensionArea();
            Intrinsics.checkNotNullExpressionValue(extensionArea, "getExtensionArea(...)");
            ideaExtensionPoints.registerVersionSpecificAppExtensionPoints(extensionArea);
        }
    }
}
