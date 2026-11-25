package org.jetbrains.kotlin.com.intellij.core;

import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.com.intellij.codeInsight.ContainerProvider;
import org.jetbrains.kotlin.com.intellij.codeInsight.JavaContainerProvider;
import org.jetbrains.kotlin.com.intellij.codeInsight.folding.JavaCodeFoldingSettings;
import org.jetbrains.kotlin.com.intellij.codeInsight.folding.impl.JavaCodeFoldingSettingsBase;
import org.jetbrains.kotlin.com.intellij.codeInsight.folding.impl.JavaFoldingBuilderBase;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.ArchiveFileType;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaClassFileType;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaFileType;
import org.jetbrains.kotlin.com.intellij.lang.LanguageASTFactory;
import org.jetbrains.kotlin.com.intellij.lang.folding.LanguageFolding;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaParserDefinition;
import org.jetbrains.kotlin.com.intellij.navigation.ItemPresentationProviders;
import org.jetbrains.kotlin.com.intellij.openapi.Disposable;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.BinaryFileTypeDecompilers;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.PlainTextFileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.PlainTextLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.PlainTextParserDefinition;
import org.jetbrains.kotlin.com.intellij.openapi.projectRoots.JavaVersionService;
import org.jetbrains.kotlin.com.intellij.psi.ClassFileViewProviderFactory;
import org.jetbrains.kotlin.com.intellij.psi.FileTypeFileViewProviders;
import org.jetbrains.kotlin.com.intellij.psi.JavaDirectoryService;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiLocalVariable;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutorFactory;
import org.jetbrains.kotlin.com.intellij.psi.compiled.ClassFileDecompilers;
import org.jetbrains.kotlin.com.intellij.psi.impl.LanguageConstantExpressionEvaluator;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiExpressionEvaluator;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSubstitutorFactoryImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClassFileStubBuilder;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsDecompilerImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImplementationHelper;
import org.jetbrains.kotlin.com.intellij.psi.impl.search.MethodSuperSearcher;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.JavaASTFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.PlainTextASTFactory;
import org.jetbrains.kotlin.com.intellij.psi.presentation.java.ClassPresentationProvider;
import org.jetbrains.kotlin.com.intellij.psi.presentation.java.FieldPresentationProvider;
import org.jetbrains.kotlin.com.intellij.psi.presentation.java.MethodPresentationProvider;
import org.jetbrains.kotlin.com.intellij.psi.presentation.java.PackagePresentationProvider;
import org.jetbrains.kotlin.com.intellij.psi.presentation.java.VariablePresentationProvider;
import org.jetbrains.kotlin.com.intellij.psi.search.searches.SuperMethodsSearch;
import org.jetbrains.kotlin.com.intellij.psi.stubs.BinaryFileStubBuilders;
import org.jetbrains.kotlin.com.intellij.util.QueryExecutor;

/* JADX INFO: loaded from: classes3.dex */
public class JavaCoreApplicationEnvironment extends CoreApplicationEnvironment {
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "parentDisposable", "org/jetbrains/kotlin/com/intellij/core/JavaCoreApplicationEnvironment", "<init>"));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JavaCoreApplicationEnvironment(Disposable disposable) {
        this(disposable, true);
        if (disposable == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaCoreApplicationEnvironment(Disposable disposable, boolean z) {
        super(disposable, z);
        if (disposable == null) {
            $$$reportNull$$$0(1);
        }
        registerFileType(JavaClassFileType.INSTANCE, "class");
        registerFileType(JavaFileType.INSTANCE, SuffixConstants.EXTENSION_java);
        registerFileType(ArchiveFileType.INSTANCE, "jar;zip");
        registerFileType(PlainTextFileType.INSTANCE, "txt;sh;bat;cmd;policy;log;cgi;MF;jad;jam;htaccess");
        addExplicitExtension(LanguageASTFactory.INSTANCE, PlainTextLanguage.INSTANCE, new PlainTextASTFactory());
        registerParserDefinition(new PlainTextParserDefinition());
        addExplicitExtension(FileTypeFileViewProviders.INSTANCE, JavaClassFileType.INSTANCE, new ClassFileViewProviderFactory());
        addExplicitExtension(BinaryFileStubBuilders.INSTANCE, JavaClassFileType.INSTANCE, new ClassFileStubBuilder());
        addExplicitExtension(LanguageASTFactory.INSTANCE, JavaLanguage.INSTANCE, new JavaASTFactory());
        registerParserDefinition(new JavaParserDefinition());
        addExplicitExtension(LanguageConstantExpressionEvaluator.INSTANCE, JavaLanguage.INSTANCE, new PsiExpressionEvaluator());
        registerApplicationExtensionPoint(ContainerProvider.EP_NAME, ContainerProvider.class);
        addExtension(ContainerProvider.EP_NAME, new JavaContainerProvider());
        this.myApplication.registerService((Class<CorePsiPackageImplementationHelper>) PsiPackageImplementationHelper.class, new CorePsiPackageImplementationHelper());
        this.myApplication.registerService((Class<PsiSubstitutorFactoryImpl>) PsiSubstitutorFactory.class, new PsiSubstitutorFactoryImpl());
        this.myApplication.registerService((Class<CoreJavaDirectoryService>) JavaDirectoryService.class, createJavaDirectoryService());
        this.myApplication.registerService((Class<JavaVersionService>) JavaVersionService.class, new JavaVersionService());
        addExplicitExtension(ItemPresentationProviders.INSTANCE, PsiPackage.class, new PackagePresentationProvider());
        addExplicitExtension(ItemPresentationProviders.INSTANCE, PsiClass.class, new ClassPresentationProvider());
        addExplicitExtension(ItemPresentationProviders.INSTANCE, PsiMethod.class, new MethodPresentationProvider());
        addExplicitExtension(ItemPresentationProviders.INSTANCE, PsiField.class, new FieldPresentationProvider());
        addExplicitExtension(ItemPresentationProviders.INSTANCE, PsiLocalVariable.class, new VariablePresentationProvider());
        addExplicitExtension(ItemPresentationProviders.INSTANCE, PsiParameter.class, new VariablePresentationProvider());
        registerApplicationService(JavaCodeFoldingSettings.class, new JavaCodeFoldingSettingsBase());
        addExplicitExtension(LanguageFolding.INSTANCE, JavaLanguage.INSTANCE, new JavaFoldingBuilderBase() { // from class: org.jetbrains.kotlin.com.intellij.core.JavaCoreApplicationEnvironment.1
        });
        registerApplicationExtensionPoint(SuperMethodsSearch.EP_NAME, QueryExecutor.class);
        addExtension(SuperMethodsSearch.EP_NAME, new MethodSuperSearcher());
        registerApplicationDynamicExtensionPoint("org.jetbrains.kotlin.com.intellij.filetype.decompiler", BinaryFileTypeDecompilers.class);
        registerApplicationDynamicExtensionPoint("org.jetbrains.kotlin.com.intellij.psi.classFileDecompiler", ClassFileDecompilers.Decompiler.class);
        addExtension(ClassFileDecompilers.getInstance().EP_NAME, new ClsDecompilerImpl());
    }

    protected CoreJavaDirectoryService createJavaDirectoryService() {
        return new CoreJavaDirectoryService();
    }
}
