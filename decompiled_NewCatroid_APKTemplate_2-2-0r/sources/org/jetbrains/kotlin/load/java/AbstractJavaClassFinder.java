package org.jetbrains.kotlin.load.java;

import com.reandroid.apk.ApkUtil;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.com.intellij.ide.highlighter.JavaClassFileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileTypeRegistry;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.UnknownFileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileFilter;
import org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.javax.inject.Inject;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.CodeAnalyzerInitializer;
import org.jetbrains.kotlin.resolve.jvm.JvmCodeAnalyzerInitializer;
import org.jetbrains.kotlin.resolve.jvm.TopPackageNamesProvider;
import org.jetbrains.kotlin.resolve.lazy.KotlinCodeAnalyzer;

/* JADX INFO: compiled from: AbstractJavaClassFinder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001:\u0001\u001cB\u0005¢\u0006\u0002\u0010\u0002J(\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\nH\u0017J\u0010\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u0004H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0084.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0084.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/load/java/AbstractJavaClassFinder;", "Lorg/jetbrains/kotlin/load/java/JavaClassFinder;", "()V", "javaSearchScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "getJavaSearchScope", "()Lcom/intellij/psi/search/GlobalSearchScope;", "setJavaSearchScope", "(Lcom/intellij/psi/search/GlobalSearchScope;)V", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "getProject", "()Lcom/intellij/openapi/project/Project;", "setProject", "(Lcom/intellij/openapi/project/Project;)V", "initialize", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "codeAnalyzer", "Lorg/jetbrains/kotlin/resolve/lazy/KotlinCodeAnalyzer;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "jvmTarget", "Lorg/jetbrains/kotlin/config/JvmTarget;", "setProjectInstance", "setScope", "scope", "FilterOutKotlinSourceFilesScope", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractJavaClassFinder implements JavaClassFinder {
    protected GlobalSearchScope javaSearchScope;
    protected Project project;

    protected final Project getProject() {
        Project project = this.project;
        if (project != null) {
            return project;
        }
        Intrinsics.throwUninitializedPropertyAccessException("project");
        return null;
    }

    protected final void setProject(Project project) {
        Intrinsics.checkNotNullParameter(project, "<set-?>");
        this.project = project;
    }

    protected final GlobalSearchScope getJavaSearchScope() {
        GlobalSearchScope globalSearchScope = this.javaSearchScope;
        if (globalSearchScope != null) {
            return globalSearchScope;
        }
        Intrinsics.throwUninitializedPropertyAccessException("javaSearchScope");
        return null;
    }

    protected final void setJavaSearchScope(GlobalSearchScope globalSearchScope) {
        Intrinsics.checkNotNullParameter(globalSearchScope, "<set-?>");
        this.javaSearchScope = globalSearchScope;
    }

    @Inject
    public final void setScope(GlobalSearchScope scope) {
        FilterOutKotlinSourceFilesScope filterOutKotlinSourceFilesScope;
        Intrinsics.checkNotNullParameter(scope, "scope");
        if (Intrinsics.areEqual(scope, GlobalSearchScope.EMPTY_SCOPE)) {
            filterOutKotlinSourceFilesScope = GlobalSearchScope.EMPTY_SCOPE;
            Intrinsics.checkNotNull(filterOutKotlinSourceFilesScope);
        } else {
            filterOutKotlinSourceFilesScope = new FilterOutKotlinSourceFilesScope(this, scope);
        }
        setJavaSearchScope(filterOutKotlinSourceFilesScope);
    }

    @Inject
    public void setProjectInstance(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        setProject(project);
    }

    public void initialize(BindingTrace trace, KotlinCodeAnalyzer codeAnalyzer, LanguageVersionSettings languageVersionSettings, JvmTarget jvmTarget) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(codeAnalyzer, "codeAnalyzer");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(jvmTarget, "jvmTarget");
        CodeAnalyzerInitializer companion = CodeAnalyzerInitializer.INSTANCE.getInstance(getProject());
        JvmCodeAnalyzerInitializer jvmCodeAnalyzerInitializer = companion instanceof JvmCodeAnalyzerInitializer ? (JvmCodeAnalyzerInitializer) companion : null;
        if (jvmCodeAnalyzerInitializer != null) {
            ModuleDescriptor moduleDescriptor = codeAnalyzer.getModuleDescriptor();
            Intrinsics.checkNotNullExpressionValue(moduleDescriptor, "getModuleDescriptor(...)");
            jvmCodeAnalyzerInitializer.initialize(trace, moduleDescriptor, codeAnalyzer, languageVersionSettings, jvmTarget);
        }
    }

    /* JADX INFO: compiled from: AbstractJavaClassFinder.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0011\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u000bH\u0016R\u0011\u0010\u0006\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u000b\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/load/java/AbstractJavaClassFinder$FilterOutKotlinSourceFilesScope;", "Lorg/jetbrains/kotlin/com/intellij/psi/search/DelegatingGlobalSearchScope;", "Lorg/jetbrains/kotlin/resolve/jvm/TopPackageNamesProvider;", "baseScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "(Lorg/jetbrains/kotlin/load/java/AbstractJavaClassFinder;Lcom/intellij/psi/search/GlobalSearchScope;)V", ApkUtil.DEF_MODULE_NAME, "getBase", "()Lcom/intellij/psi/search/GlobalSearchScope;", "topPackageNames", "", "", "getTopPackageNames", "()Ljava/util/Set;", "contains", "", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "getProject", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "toString", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class FilterOutKotlinSourceFilesScope extends DelegatingGlobalSearchScope implements TopPackageNamesProvider {
        private final GlobalSearchScope base;
        final /* synthetic */ AbstractJavaClassFinder this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FilterOutKotlinSourceFilesScope(AbstractJavaClassFinder abstractJavaClassFinder, GlobalSearchScope baseScope) {
            super(baseScope);
            Intrinsics.checkNotNullParameter(baseScope, "baseScope");
            this.this$0 = abstractJavaClassFinder;
            GlobalSearchScope myBaseScope = this.myBaseScope;
            Intrinsics.checkNotNullExpressionValue(myBaseScope, "myBaseScope");
            this.base = myBaseScope;
        }

        @Override // org.jetbrains.kotlin.resolve.jvm.TopPackageNamesProvider
        public Set<String> getTopPackageNames() {
            VirtualFileFilter virtualFileFilter = this.myBaseScope;
            TopPackageNamesProvider topPackageNamesProvider = virtualFileFilter instanceof TopPackageNamesProvider ? (TopPackageNamesProvider) virtualFileFilter : null;
            if (topPackageNamesProvider != null) {
                return topPackageNamesProvider.getTopPackageNames();
            }
            return null;
        }

        /* JADX WARN: Code duplicated, block: B:20:0x005e  */
        /* JADX WARN: Code duplicated, block: B:22:0x0066 A[RETURN, SYNTHETIC] */
        @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public boolean contains(VirtualFile file) {
            Intrinsics.checkNotNullParameter(file, "file");
            String extension = file.getExtension();
            if (!file.isDirectory()) {
                if (!Intrinsics.areEqual(extension, KotlinFileType.EXTENSION)) {
                    if (!Intrinsics.areEqual(extension, SuffixConstants.EXTENSION_java) && !Intrinsics.areEqual(extension, JavaClassFileType.INSTANCE.getDefaultExtension())) {
                        FileType fileTypeByFileName = FileTypeRegistry.getInstance().getFileTypeByFileName(file.getName());
                        Intrinsics.checkNotNullExpressionValue(fileTypeByFileName, "getFileTypeByFileName(...)");
                        if (!Intrinsics.areEqual(fileTypeByFileName, KotlinFileType.INSTANCE) && (!Intrinsics.areEqual(fileTypeByFileName, UnknownFileType.INSTANCE) || !FileTypeRegistry.getInstance().isFileOfType(file, KotlinFileType.INSTANCE))) {
                            if (this.myBaseScope.contains(file)) {
                                return true;
                            }
                        }
                    } else if (this.myBaseScope.contains(file)) {
                        return true;
                    }
                }
            } else if (this.myBaseScope.contains(file)) {
                return true;
            }
            return false;
        }

        public final GlobalSearchScope getBase() {
            return this.base;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.ProjectAwareFileFilter
        public Project getProject() {
            return this.this$0.getProject();
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope
        public String toString() {
            return "JCFI: " + this.myBaseScope;
        }
    }
}
