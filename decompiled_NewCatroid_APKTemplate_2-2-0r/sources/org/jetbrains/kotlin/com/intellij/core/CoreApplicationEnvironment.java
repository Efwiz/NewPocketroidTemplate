package org.jetbrains.kotlin.com.intellij.core;

import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.DynamicBundle;
import org.jetbrains.kotlin.com.intellij.codeInsight.folding.CodeFoldingSettings;
import org.jetbrains.kotlin.com.intellij.concurrency.JobLauncher;
import org.jetbrains.kotlin.com.intellij.ide.plugins.DisabledPluginsState;
import org.jetbrains.kotlin.com.intellij.ide.plugins.IdeaPluginDescriptorImpl;
import org.jetbrains.kotlin.com.intellij.ide.plugins.PluginDescriptorLoader;
import org.jetbrains.kotlin.com.intellij.ide.plugins.PluginManagerCore;
import org.jetbrains.kotlin.com.intellij.lang.DefaultASTFactory;
import org.jetbrains.kotlin.com.intellij.lang.DefaultASTFactoryImpl;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.LanguageExtension;
import org.jetbrains.kotlin.com.intellij.lang.LanguageParserDefinitions;
import org.jetbrains.kotlin.com.intellij.lang.ParserDefinition;
import org.jetbrains.kotlin.com.intellij.lang.PsiBuilderFactory;
import org.jetbrains.kotlin.com.intellij.lang.impl.PsiBuilderFactoryImpl;
import org.jetbrains.kotlin.com.intellij.mock.MockApplication;
import org.jetbrains.kotlin.com.intellij.mock.MockFileDocumentManagerImpl;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationInfo;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.application.impl.ApplicationInfoImpl;
import org.jetbrains.kotlin.com.intellij.openapi.command.CommandProcessor;
import org.jetbrains.kotlin.com.intellij.openapi.command.impl.CoreCommandProcessor;
import org.jetbrains.kotlin.com.intellij.openapi.editor.impl.DocumentImpl;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPoint;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointDescriptor;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.Extensions;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionsArea;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.impl.ExtensionsAreaImpl;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeExtension;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.progress.impl.CoreProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.util.ClassExtension;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileManagerListener;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.encoding.EncodingManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.CoreVirtualFilePointerManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.VirtualFileManagerImpl;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.impl.jar.CoreJarFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.local.CoreLocalFileSystem;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.pointers.VirtualFilePointerManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceService;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceServiceImpl;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistryImpl;
import org.jetbrains.kotlin.com.intellij.psi.stubs.CoreStubTreeLoader;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubTreeLoader;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.com.intellij.util.KeyedLazyInstanceEP;
import org.jetbrains.kotlin.com.intellij.util.graph.GraphAlgorithms;
import org.jetbrains.kotlin.com.intellij.util.graph.impl.GraphAlgorithmsImpl;
import org.jetbrains.kotlin.org.picocontainer.MutablePicoContainer;

/* JADX INFO: loaded from: classes3.dex */
public class CoreApplicationEnvironment {
    protected final MockApplication myApplication;
    private final CoreFileTypeRegistry myFileTypeRegistry;
    protected final VirtualFileSystem myJarFileSystem;
    private final VirtualFileSystem myJrtFileSystem;
    private final CoreLocalFileSystem myLocalFileSystem;
    private final Disposable myParentDisposable;
    private final boolean myUnitTestMode;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 5 || i == 6 || i == 44 || i == 45) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 5 || i == 6 || i == 44 || i == 45) ? 2 : 3];
        switch (i) {
            case 2:
                objArr[0] = "serviceInterface";
                break;
            case 3:
                objArr[0] = "serviceImplementation";
                break;
            case 4:
            default:
                objArr[0] = "parentDisposable";
                break;
            case 5:
            case 6:
            case 44:
            case 45:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/core/CoreApplicationEnvironment";
                break;
            case 7:
                objArr[0] = "interfaceClass";
                break;
            case 8:
            case 14:
                objArr[0] = "implementation";
                break;
            case 9:
            case 21:
                objArr[0] = "fileType";
                break;
            case 10:
            case 27:
                objArr[0] = SchemaSymbols.ATTVAL_EXTENSION;
                break;
            case 11:
                objArr[0] = "definition";
                break;
            case 12:
                objArr[0] = "container";
                break;
            case 13:
                objArr[0] = ASTExpr.DEFAULT_MAP_KEY_NAME;
                break;
            case 15:
            case 20:
            case 23:
                objArr[0] = "instance";
                break;
            case 16:
            case 18:
                objArr[0] = "language";
                break;
            case 17:
            case 22:
            case 25:
                objArr[0] = "object";
                break;
            case 19:
                objArr[0] = "parserDefinition";
                break;
            case 24:
            case 30:
            case 33:
            case 36:
            case 38:
            case 40:
                objArr[0] = "aClass";
                break;
            case 26:
            case 32:
            case 35:
                objArr[0] = "name";
                break;
            case 28:
            case 31:
            case 34:
            case 43:
                objArr[0] = "area";
                break;
            case 29:
            case 37:
            case 39:
                objArr[0] = "extensionPointName";
                break;
            case 41:
                objArr[0] = "pluginRoot";
                break;
            case 42:
                objArr[0] = PsiTreeChangeEvent.PROP_FILE_NAME;
                break;
        }
        if (i == 5) {
            objArr[1] = "getApplication";
        } else if (i == 6) {
            objArr[1] = "getParentDisposable";
        } else if (i == 44) {
            objArr[1] = "getLocalFileSystem";
        } else if (i != 45) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/core/CoreApplicationEnvironment";
        } else {
            objArr[1] = "getJarFileSystem";
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "registerApplicationService";
                break;
            case 4:
                objArr[2] = "createApplication";
                break;
            case 5:
            case 6:
            case 44:
            case 45:
                break;
            case 7:
            case 8:
                objArr[2] = "registerApplicationComponent";
                break;
            case 9:
            case 10:
                objArr[2] = "registerFileType";
                break;
            case 11:
            case 18:
            case 19:
                objArr[2] = "registerParserDefinition";
                break;
            case 12:
            case 13:
            case 14:
                objArr[2] = "registerComponentInstance";
                break;
            case 15:
            case 16:
            case 17:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                objArr[2] = "addExplicitExtension";
                break;
            case 26:
            case 27:
                objArr[2] = "addExtension";
                break;
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
                objArr[2] = "registerExtensionPoint";
                break;
            case 37:
            case 38:
                objArr[2] = "registerApplicationExtensionPoint";
                break;
            case 39:
            case 40:
                objArr[2] = "registerApplicationDynamicExtensionPoint";
                break;
            case 41:
            case 42:
            case 43:
                objArr[2] = "registerExtensionPointAndExtensions";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 5 && i != 6 && i != 44 && i != 45) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected VirtualFileSystem createJrtFileSystem() {
        return null;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CoreApplicationEnvironment(Disposable disposable) {
        this(disposable, true);
        if (disposable == null) {
            $$$reportNull$$$0(0);
        }
    }

    public CoreApplicationEnvironment(Disposable disposable, boolean z) {
        List listAsList;
        if (disposable == null) {
            $$$reportNull$$$0(1);
        }
        this.myParentDisposable = disposable;
        this.myUnitTestMode = z;
        DisabledPluginsState.setIgnoreDisabledPlugins(true);
        this.myFileTypeRegistry = new CoreFileTypeRegistry();
        MockApplication mockApplicationCreateApplication = createApplication(disposable);
        this.myApplication = mockApplicationCreateApplication;
        ApplicationManager.setApplication(mockApplicationCreateApplication, new Supplier() { // from class: org.jetbrains.kotlin.com.intellij.core.CoreApplicationEnvironment$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m6460x46d032bd();
            }
        }, disposable);
        CoreLocalFileSystem coreLocalFileSystemCreateLocalFileSystem = createLocalFileSystem();
        this.myLocalFileSystem = coreLocalFileSystemCreateLocalFileSystem;
        VirtualFileSystem virtualFileSystemCreateJarFileSystem = createJarFileSystem();
        this.myJarFileSystem = virtualFileSystemCreateJarFileSystem;
        VirtualFileSystem virtualFileSystemCreateJrtFileSystem = createJrtFileSystem();
        this.myJrtFileSystem = virtualFileSystemCreateJrtFileSystem;
        registerApplicationService(FileDocumentManager.class, new MockFileDocumentManagerImpl(null, new Function() { // from class: org.jetbrains.kotlin.com.intellij.core.CoreApplicationEnvironment$$ExternalSyntheticLambda1
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return new DocumentImpl((CharSequence) obj);
            }
        }));
        registerApplicationExtensionPoint(new ExtensionPointName("org.jetbrains.kotlin.com.intellij.virtualFileManagerListener"), VirtualFileManagerListener.class);
        if (virtualFileSystemCreateJrtFileSystem != null) {
            listAsList = Arrays.asList(coreLocalFileSystemCreateLocalFileSystem, virtualFileSystemCreateJarFileSystem, virtualFileSystemCreateJrtFileSystem);
        } else {
            listAsList = Arrays.asList(coreLocalFileSystemCreateLocalFileSystem, virtualFileSystemCreateJarFileSystem);
        }
        registerApplicationService(VirtualFileManager.class, new VirtualFileManagerImpl(listAsList));
        registerApplicationExtensionPoint(new ExtensionPointName("org.jetbrains.kotlin.com.intellij.virtualFileSystem"), KeyedLazyInstanceEP.class);
        registerApplicationService(EncodingManager.class, new CoreEncodingRegistry());
        registerApplicationService(VirtualFilePointerManager.class, createVirtualFilePointerManager());
        registerApplicationService(DefaultASTFactory.class, new DefaultASTFactoryImpl());
        registerApplicationService(PsiBuilderFactory.class, new PsiBuilderFactoryImpl());
        registerApplicationService(ReferenceProvidersRegistry.class, new ReferenceProvidersRegistryImpl());
        registerApplicationService(StubTreeLoader.class, new CoreStubTreeLoader());
        registerApplicationService(PsiReferenceService.class, new PsiReferenceServiceImpl());
        registerApplicationService(ProgressManager.class, createProgressIndicatorProvider());
        registerApplicationService(JobLauncher.class, createJobLauncher());
        registerApplicationService(CodeFoldingSettings.class, new CodeFoldingSettings());
        registerApplicationService(CommandProcessor.class, new CoreCommandProcessor());
        registerApplicationService(GraphAlgorithms.class, new GraphAlgorithmsImpl());
        mockApplicationCreateApplication.registerService(ApplicationInfo.class, ApplicationInfoImpl.class);
        registerApplicationExtensionPoint(DynamicBundle.LanguageBundleEP.EP_NAME, DynamicBundle.LanguageBundleEP.class);
    }

    /* JADX INFO: renamed from: lambda$new$0$org-jetbrains-kotlin-com-intellij-core-CoreApplicationEnvironment, reason: not valid java name */
    /* synthetic */ FileTypeRegistry m6460x46d032bd() {
        return this.myFileTypeRegistry;
    }

    public <T> void registerApplicationService(Class<T> cls, T t) {
        if (cls == null) {
            $$$reportNull$$$0(2);
        }
        if (t == null) {
            $$$reportNull$$$0(3);
        }
        this.myApplication.registerService(cls, t);
    }

    protected VirtualFilePointerManager createVirtualFilePointerManager() {
        return new CoreVirtualFilePointerManager();
    }

    protected MockApplication createApplication(Disposable disposable) {
        if (disposable == null) {
            $$$reportNull$$$0(4);
        }
        return new MockApplication(disposable) { // from class: org.jetbrains.kotlin.com.intellij.core.CoreApplicationEnvironment.1
            @Override // org.jetbrains.kotlin.com.intellij.mock.MockApplication, org.jetbrains.kotlin.com.intellij.openapi.application.Application
            public boolean isUnitTestMode() {
                return CoreApplicationEnvironment.this.myUnitTestMode;
            }
        };
    }

    protected JobLauncher createJobLauncher() {
        return new JobLauncher() { // from class: org.jetbrains.kotlin.com.intellij.core.CoreApplicationEnvironment.2
        };
    }

    protected ProgressManager createProgressIndicatorProvider() {
        return new CoreProgressManager();
    }

    protected VirtualFileSystem createJarFileSystem() {
        return new CoreJarFileSystem();
    }

    protected CoreLocalFileSystem createLocalFileSystem() {
        return new CoreLocalFileSystem();
    }

    public MockApplication getApplication() {
        MockApplication mockApplication = this.myApplication;
        if (mockApplication == null) {
            $$$reportNull$$$0(5);
        }
        return mockApplication;
    }

    public Disposable getParentDisposable() {
        Disposable disposable = this.myParentDisposable;
        if (disposable == null) {
            $$$reportNull$$$0(6);
        }
        return disposable;
    }

    public <T> void registerApplicationComponent(Class<T> cls, T t) {
        if (cls == null) {
            $$$reportNull$$$0(7);
        }
        if (t == null) {
            $$$reportNull$$$0(8);
        }
        registerComponentInstance(this.myApplication.m6468getPicoContainer(), cls, t);
        if (t instanceof Disposable) {
            Disposer.register(this.myApplication, (Disposable) t);
        }
    }

    public void registerFileType(FileType fileType, String str) {
        if (fileType == null) {
            $$$reportNull$$$0(9);
        }
        if (str == null) {
            $$$reportNull$$$0(10);
        }
        this.myFileTypeRegistry.registerFileType(fileType, str);
    }

    public void registerParserDefinition(ParserDefinition parserDefinition) {
        if (parserDefinition == null) {
            $$$reportNull$$$0(11);
        }
        addExplicitExtension(LanguageParserDefinitions.INSTANCE, parserDefinition.getFileNodeType().getLanguage(), parserDefinition);
    }

    public static <T> void registerComponentInstance(MutablePicoContainer mutablePicoContainer, Class<T> cls, T t) {
        if (mutablePicoContainer == null) {
            $$$reportNull$$$0(12);
        }
        if (cls == null) {
            $$$reportNull$$$0(13);
        }
        if (t == null) {
            $$$reportNull$$$0(14);
        }
        mutablePicoContainer.unregisterComponent(cls);
        mutablePicoContainer.registerComponentInstance(cls, t);
    }

    public <T> void addExplicitExtension(LanguageExtension<T> languageExtension, Language language, T t) {
        if (languageExtension == null) {
            $$$reportNull$$$0(15);
        }
        if (language == null) {
            $$$reportNull$$$0(16);
        }
        if (t == null) {
            $$$reportNull$$$0(17);
        }
        languageExtension.addExplicitExtension(language, t, this.myParentDisposable);
    }

    public void registerParserDefinition(Language language, ParserDefinition parserDefinition) {
        if (language == null) {
            $$$reportNull$$$0(18);
        }
        if (parserDefinition == null) {
            $$$reportNull$$$0(19);
        }
        addExplicitExtension(LanguageParserDefinitions.INSTANCE, language, parserDefinition);
    }

    public <T> void addExplicitExtension(FileTypeExtension<T> fileTypeExtension, FileType fileType, T t) {
        if (fileTypeExtension == null) {
            $$$reportNull$$$0(20);
        }
        if (fileType == null) {
            $$$reportNull$$$0(21);
        }
        if (t == null) {
            $$$reportNull$$$0(22);
        }
        fileTypeExtension.addExplicitExtension(fileType, t, this.myParentDisposable);
    }

    public <T> void addExplicitExtension(ClassExtension<T> classExtension, Class cls, T t) {
        if (classExtension == null) {
            $$$reportNull$$$0(23);
        }
        if (cls == null) {
            $$$reportNull$$$0(24);
        }
        if (t == null) {
            $$$reportNull$$$0(25);
        }
        classExtension.addExplicitExtension(cls, t, this.myParentDisposable);
    }

    public <T> void addExtension(ExtensionPointName<T> extensionPointName, T t) {
        if (extensionPointName == null) {
            $$$reportNull$$$0(26);
        }
        if (t == null) {
            $$$reportNull$$$0(27);
        }
        Extensions.getRootArea().getExtensionPoint(extensionPointName).registerExtension(t, this.myParentDisposable);
    }

    public static <T> void registerExtensionPoint(ExtensionsArea extensionsArea, ExtensionPointName<T> extensionPointName, Class<? extends T> cls) {
        if (extensionsArea == null) {
            $$$reportNull$$$0(28);
        }
        if (extensionPointName == null) {
            $$$reportNull$$$0(29);
        }
        if (cls == null) {
            $$$reportNull$$$0(30);
        }
        registerExtensionPoint(extensionsArea, extensionPointName.getName(), cls);
    }

    public static <T> void registerExtensionPoint(ExtensionsArea extensionsArea, String str, Class<? extends T> cls) {
        if (extensionsArea == null) {
            $$$reportNull$$$0(31);
        }
        if (str == null) {
            $$$reportNull$$$0(32);
        }
        if (cls == null) {
            $$$reportNull$$$0(33);
        }
        registerExtensionPoint(extensionsArea, str, cls, false);
    }

    private static <T> void registerExtensionPoint(ExtensionsArea extensionsArea, String str, Class<? extends T> cls, boolean z) {
        if (extensionsArea == null) {
            $$$reportNull$$$0(34);
        }
        if (str == null) {
            $$$reportNull$$$0(35);
        }
        if (cls == null) {
            $$$reportNull$$$0(36);
        }
        if (extensionsArea.hasExtensionPoint(str)) {
            return;
        }
        extensionsArea.registerExtensionPoint(str, cls.getName(), (cls.isInterface() || Modifier.isAbstract(cls.getModifiers())) ? ExtensionPoint.Kind.INTERFACE : ExtensionPoint.Kind.BEAN_CLASS, z);
    }

    public static <T> void registerApplicationExtensionPoint(ExtensionPointName<T> extensionPointName, Class<? extends T> cls) {
        if (extensionPointName == null) {
            $$$reportNull$$$0(37);
        }
        if (cls == null) {
            $$$reportNull$$$0(38);
        }
        registerExtensionPoint(Extensions.getRootArea(), extensionPointName.getName(), cls);
    }

    public static <T> void registerApplicationDynamicExtensionPoint(String str, Class<? extends T> cls) {
        if (str == null) {
            $$$reportNull$$$0(39);
        }
        if (cls == null) {
            $$$reportNull$$$0(40);
        }
        registerExtensionPoint(Extensions.getRootArea(), str, cls, true);
    }

    public static void registerExtensionPointAndExtensions(Path path, String str, ExtensionsArea extensionsArea) {
        if (path == null) {
            $$$reportNull$$$0(41);
        }
        if (str == null) {
            $$$reportNull$$$0(42);
        }
        if (extensionsArea == null) {
            $$$reportNull$$$0(43);
        }
        IdeaPluginDescriptorImpl ideaPluginDescriptorImplLoadForCoreEnv = PluginDescriptorLoader.loadForCoreEnv(path, str);
        if (ideaPluginDescriptorImplLoadForCoreEnv == null) {
            PluginManagerCore.getLogger().error("Cannot load " + str + " from " + path);
            return;
        }
        List<ExtensionPointDescriptor> list = ideaPluginDescriptorImplLoadForCoreEnv.appContainerDescriptor.extensionPoints;
        ExtensionsAreaImpl extensionsAreaImpl = (ExtensionsAreaImpl) extensionsArea;
        if (list != null) {
            extensionsAreaImpl.registerExtensionPoints(list, ideaPluginDescriptorImplLoadForCoreEnv);
        }
        ideaPluginDescriptorImplLoadForCoreEnv.registerExtensions(extensionsAreaImpl.extensionPoints, ideaPluginDescriptorImplLoadForCoreEnv.appContainerDescriptor, null);
    }

    public CoreLocalFileSystem getLocalFileSystem() {
        CoreLocalFileSystem coreLocalFileSystem = this.myLocalFileSystem;
        if (coreLocalFileSystem == null) {
            $$$reportNull$$$0(44);
        }
        return coreLocalFileSystem;
    }

    public VirtualFileSystem getJarFileSystem() {
        VirtualFileSystem virtualFileSystem = this.myJarFileSystem;
        if (virtualFileSystem == null) {
            $$$reportNull$$$0(45);
        }
        return virtualFileSystem;
    }

    public VirtualFileSystem getJrtFileSystem() {
        return this.myJrtFileSystem;
    }
}
