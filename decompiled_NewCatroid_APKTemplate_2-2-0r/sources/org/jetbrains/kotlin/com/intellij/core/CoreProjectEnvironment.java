package org.jetbrains.kotlin.com.intellij.core;

import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.lang.injection.InjectedLanguageManager;
import org.jetbrains.kotlin.com.intellij.mock.MockDumbService;
import org.jetbrains.kotlin.com.intellij.mock.MockDumbUtil;
import org.jetbrains.kotlin.com.intellij.mock.MockFileIndexFacade;
import org.jetbrains.kotlin.com.intellij.mock.MockProject;
import org.jetbrains.kotlin.com.intellij.mock.MockResolveScopeManager;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbService;
import org.jetbrains.kotlin.com.intellij.openapi.project.DumbUtil;
import org.jetbrains.kotlin.com.intellij.openapi.roots.FileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer;
import org.jetbrains.kotlin.com.intellij.psi.PsiDocumentManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.SmartPointerManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.DocumentCommitProcessor;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiCachedValuesFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiFileFactoryImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiModificationTrackerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.ResolveScopeManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiDirectoryFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiDirectoryFactoryImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.smartPointers.SmartPointerManagerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.ResolveCache;
import org.jetbrains.kotlin.com.intellij.psi.search.ProjectScopeBuilder;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiModificationTracker;
import org.jetbrains.kotlin.com.intellij.util.CachedValuesManagerImpl;
import org.jetbrains.kotlin.com.intellij.util.messages.MessageBus;
import org.jetbrains.kotlin.org.picocontainer.PicoContainer;

/* JADX INFO: loaded from: classes3.dex */
public class CoreProjectEnvironment {
    private final CoreApplicationEnvironment myEnvironment;
    protected final FileIndexFacade myFileIndexFacade;
    protected final MessageBus myMessageBus;
    private final Disposable myParentDisposable;
    protected final MockProject myProject;
    protected final PsiManagerImpl myPsiManager;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        switch (i) {
            case 9:
            case 10:
            case 11:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 9:
            case 10:
            case 11:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
                objArr[0] = "applicationEnvironment";
                break;
            case 2:
                objArr[0] = "parent";
                break;
            case 3:
            default:
                objArr[0] = "parentDisposable";
                break;
            case 4:
                objArr[0] = "psiManager";
                break;
            case 5:
                objArr[0] = "name";
                break;
            case 6:
                objArr[0] = SchemaSymbols.ATTVAL_EXTENSION;
                break;
            case 7:
                objArr[0] = "interfaceClass";
                break;
            case 8:
                objArr[0] = "implementation";
                break;
            case 9:
            case 10:
            case 11:
                objArr[0] = "org/jetbrains/kotlin/com/intellij/core/CoreProjectEnvironment";
                break;
        }
        switch (i) {
            case 9:
                objArr[1] = "getParentDisposable";
                break;
            case 10:
                objArr[1] = "getEnvironment";
                break;
            case 11:
                objArr[1] = "getProject";
                break;
            default:
                objArr[1] = "org/jetbrains/kotlin/com/intellij/core/CoreProjectEnvironment";
                break;
        }
        switch (i) {
            case 2:
            case 3:
                objArr[2] = "createProject";
                break;
            case 4:
                objArr[2] = "createResolveScopeManager";
                break;
            case 5:
            case 6:
                objArr[2] = "addProjectExtension";
                break;
            case 7:
            case 8:
                objArr[2] = "registerProjectComponent";
                break;
            case 9:
            case 10:
            case 11:
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        switch (i) {
            case 9:
            case 10:
            case 11:
                throw new IllegalStateException(str2);
            default:
                throw new IllegalArgumentException(str2);
        }
    }

    protected void preregisterServices() {
    }

    public CoreProjectEnvironment(Disposable disposable, CoreApplicationEnvironment coreApplicationEnvironment) {
        if (disposable == null) {
            $$$reportNull$$$0(0);
        }
        if (coreApplicationEnvironment == null) {
            $$$reportNull$$$0(1);
        }
        this.myParentDisposable = disposable;
        this.myEnvironment = coreApplicationEnvironment;
        MockProject mockProjectCreateProject = createProject(coreApplicationEnvironment.getApplication().m6468getPicoContainer(), disposable);
        this.myProject = mockProjectCreateProject;
        preregisterServices();
        FileIndexFacade fileIndexFacadeCreateFileIndexFacade = createFileIndexFacade();
        this.myFileIndexFacade = fileIndexFacadeCreateFileIndexFacade;
        this.myMessageBus = mockProjectCreateProject.getMessageBus();
        mockProjectCreateProject.registerService((Class<PsiModificationTrackerImpl>) PsiModificationTracker.class, new PsiModificationTrackerImpl(mockProjectCreateProject));
        mockProjectCreateProject.registerService((Class<FileIndexFacade>) FileIndexFacade.class, fileIndexFacadeCreateFileIndexFacade);
        mockProjectCreateProject.registerService((Class<ResolveCache>) ResolveCache.class, new ResolveCache(mockProjectCreateProject));
        PsiManagerImpl psiManagerImpl = new PsiManagerImpl(mockProjectCreateProject);
        this.myPsiManager = psiManagerImpl;
        mockProjectCreateProject.registerService((Class<PsiManagerImpl>) PsiManager.class, psiManagerImpl);
        mockProjectCreateProject.registerService(SmartPointerManager.class, SmartPointerManagerImpl.class);
        mockProjectCreateProject.registerService((Class<MockDocumentCommitProcessor>) DocumentCommitProcessor.class, new MockDocumentCommitProcessor());
        mockProjectCreateProject.registerService((Class<CorePsiDocumentManager>) PsiDocumentManager.class, new CorePsiDocumentManager(mockProjectCreateProject));
        mockProjectCreateProject.registerService((Class<ResolveScopeManager>) ResolveScopeManager.class, createResolveScopeManager(psiManagerImpl));
        mockProjectCreateProject.registerService((Class<PsiFileFactoryImpl>) PsiFileFactory.class, new PsiFileFactoryImpl(psiManagerImpl));
        mockProjectCreateProject.registerService((Class<CachedValuesManagerImpl>) CachedValuesManager.class, new CachedValuesManagerImpl(mockProjectCreateProject, new PsiCachedValuesFactory(mockProjectCreateProject)));
        mockProjectCreateProject.registerService((Class<PsiDirectoryFactoryImpl>) PsiDirectoryFactory.class, new PsiDirectoryFactoryImpl(mockProjectCreateProject));
        mockProjectCreateProject.registerService((Class<ProjectScopeBuilder>) ProjectScopeBuilder.class, createProjectScopeBuilder());
        mockProjectCreateProject.registerService((Class<MockDumbService>) DumbService.class, new MockDumbService(mockProjectCreateProject));
        mockProjectCreateProject.registerService((Class<MockDumbUtil>) DumbUtil.class, new MockDumbUtil());
        mockProjectCreateProject.registerService(CoreEncodingProjectManager.class, CoreEncodingProjectManager.class);
        mockProjectCreateProject.registerService((Class<CoreInjectedLanguageManager>) InjectedLanguageManager.class, new CoreInjectedLanguageManager());
    }

    protected MockProject createProject(PicoContainer picoContainer, Disposable disposable) {
        if (picoContainer == null) {
            $$$reportNull$$$0(2);
        }
        if (disposable == null) {
            $$$reportNull$$$0(3);
        }
        return new MockProject(picoContainer, disposable);
    }

    protected ProjectScopeBuilder createProjectScopeBuilder() {
        return new CoreProjectScopeBuilder(this.myProject, this.myFileIndexFacade);
    }

    protected FileIndexFacade createFileIndexFacade() {
        return new MockFileIndexFacade(this.myProject);
    }

    protected ResolveScopeManager createResolveScopeManager(PsiManager psiManager) {
        if (psiManager == null) {
            $$$reportNull$$$0(4);
        }
        return new MockResolveScopeManager(psiManager.getProject());
    }

    public <T> void addProjectExtension(ExtensionPointName<T> extensionPointName, T t) {
        if (extensionPointName == null) {
            $$$reportNull$$$0(5);
        }
        if (t == null) {
            $$$reportNull$$$0(6);
        }
        extensionPointName.getPoint(this.myProject).registerExtension(t, this.myParentDisposable);
    }

    public <T> void registerProjectComponent(Class<T> cls, T t) {
        if (cls == null) {
            $$$reportNull$$$0(7);
        }
        if (t == null) {
            $$$reportNull$$$0(8);
        }
        CoreApplicationEnvironment.registerComponentInstance(this.myProject.m6468getPicoContainer(), cls, t);
        if (t instanceof Disposable) {
            Disposer.register(this.myProject, (Disposable) t);
        }
    }

    public Disposable getParentDisposable() {
        Disposable disposable = this.myParentDisposable;
        if (disposable == null) {
            $$$reportNull$$$0(9);
        }
        return disposable;
    }

    public CoreApplicationEnvironment getEnvironment() {
        CoreApplicationEnvironment coreApplicationEnvironment = this.myEnvironment;
        if (coreApplicationEnvironment == null) {
            $$$reportNull$$$0(10);
        }
        return coreApplicationEnvironment;
    }

    public MockProject getProject() {
        MockProject mockProject = this.myProject;
        if (mockProject == null) {
            $$$reportNull$$$0(11);
        }
        return mockProject;
    }
}
