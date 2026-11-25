package org.jetbrains.kotlin.com.intellij.core;

import java.io.File;
import org.jetbrains.kotlin.com.intellij.lang.jvm.facade.JvmFacade;
import org.jetbrains.kotlin.com.intellij.lang.jvm.facade.JvmFacadeImpl;
import org.jetbrains.kotlin.com.intellij.mock.MockFileIndexFacade;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.roots.LanguageLevelProjectExtension;
import org.jetbrains.kotlin.com.intellij.openapi.roots.PackageIndex;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.JavaPsiFacade;
import org.jetbrains.kotlin.com.intellij.psi.JvmPsiConversionHelper;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFactory;
import org.jetbrains.kotlin.com.intellij.psi.PsiResolveHelper;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleManager;
import org.jetbrains.kotlin.com.intellij.psi.codeStyle.JavaCodeStyleSettingsFacade;
import org.jetbrains.kotlin.com.intellij.psi.controlFlow.ControlFlowFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiFacadeImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.JavaPsiImplementationHelper;
import org.jetbrains.kotlin.com.intellij.psi.impl.JvmPsiConversionHelperImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementFactoryImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.impl.JavaFileManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.JavaResolveCache;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.resolve.PsiResolveHelperImpl;

/* JADX INFO: loaded from: classes3.dex */
public class JavaCoreProjectEnvironment extends CoreProjectEnvironment {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final JavaFileManager myFileManager;
    private final PackageIndex myPackageIndex;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "applicationEnvironment";
        } else if (i != 2) {
            objArr[0] = "parentDisposable";
        } else {
            objArr[0] = "root";
        }
        objArr[1] = "org/jetbrains/kotlin/com/intellij/core/JavaCoreProjectEnvironment";
        if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "addSourcesToClasspath";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaCoreProjectEnvironment(Disposable disposable, CoreApplicationEnvironment coreApplicationEnvironment) {
        super(disposable, coreApplicationEnvironment);
        if (disposable == null) {
            $$$reportNull$$$0(0);
        }
        if (coreApplicationEnvironment == null) {
            $$$reportNull$$$0(1);
        }
        this.myProject.registerService((Class<PsiElementFactoryImpl>) PsiElementFactory.class, new PsiElementFactoryImpl(this.myProject));
        this.myProject.registerService((Class<CoreJavaPsiImplementationHelper>) JavaPsiImplementationHelper.class, createJavaPsiImplementationHelper());
        this.myProject.registerService((Class<PsiResolveHelperImpl>) PsiResolveHelper.class, new PsiResolveHelperImpl(this.myProject));
        this.myProject.registerService((Class<CoreLanguageLevelProjectExtension>) LanguageLevelProjectExtension.class, new CoreLanguageLevelProjectExtension());
        this.myProject.registerService((Class<JavaResolveCache>) JavaResolveCache.class, new JavaResolveCache(this.myProject));
        this.myProject.registerService((Class<CoreJavaCodeStyleSettingsFacade>) JavaCodeStyleSettingsFacade.class, new CoreJavaCodeStyleSettingsFacade());
        this.myProject.registerService((Class<CoreJavaCodeStyleManager>) JavaCodeStyleManager.class, new CoreJavaCodeStyleManager());
        this.myProject.registerService((Class<ControlFlowFactory>) ControlFlowFactory.class, new ControlFlowFactory(this.myProject));
        PackageIndex packageIndexCreateCorePackageIndex = createCorePackageIndex();
        this.myPackageIndex = packageIndexCreateCorePackageIndex;
        this.myProject.registerService((Class<PackageIndex>) PackageIndex.class, packageIndexCreateCorePackageIndex);
        JavaFileManager javaFileManagerCreateCoreFileManager = createCoreFileManager();
        this.myFileManager = javaFileManagerCreateCoreFileManager;
        this.myProject.registerService((Class<JavaFileManager>) JavaFileManager.class, javaFileManagerCreateCoreFileManager);
        this.myProject.registerService((Class<JvmPsiConversionHelperImpl>) JvmPsiConversionHelper.class, new JvmPsiConversionHelperImpl());
        registerJavaPsiFacade();
        this.myProject.registerService((Class<JvmFacadeImpl>) JvmFacade.class, new JvmFacadeImpl(this.myProject, this.myMessageBus));
    }

    protected void registerJavaPsiFacade() {
        this.myProject.registerService((Class<JavaPsiFacadeImpl>) JavaPsiFacade.class, new JavaPsiFacadeImpl(this.myProject));
    }

    protected CoreJavaPsiImplementationHelper createJavaPsiImplementationHelper() {
        return new CoreJavaPsiImplementationHelper(this.myProject);
    }

    protected JavaFileManager createCoreFileManager() {
        return new CoreJavaFileManager(this.myPsiManager);
    }

    protected PackageIndex createCorePackageIndex() {
        return new CorePackageIndex();
    }

    public void addJarToClassPath(File file) {
        VirtualFile virtualFileFindFileByPath = getEnvironment().getJarFileSystem().findFileByPath(file + "!/");
        if (virtualFileFindFileByPath == null) {
            throw new IllegalArgumentException("trying to add non-existing file to classpath: " + file);
        }
        addSourcesToClasspath(virtualFileFindFileByPath);
    }

    public void addSourcesToClasspath(VirtualFile virtualFile) {
        if (virtualFile == null) {
            $$$reportNull$$$0(2);
        }
        ((CoreJavaFileManager) this.myFileManager).addToClasspath(virtualFile);
        ((CorePackageIndex) this.myPackageIndex).addToClasspath(virtualFile);
        ((MockFileIndexFacade) this.myFileIndexFacade).addLibraryRoot(virtualFile);
    }
}
