package org.jetbrains.kotlin.scripting.extensions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Computable;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtImportInfo;
import org.jetbrains.kotlin.resolve.ImportPath;
import org.jetbrains.kotlin.resolve.extensions.ExtraImportsProviderExtension;
import org.jetbrains.kotlin.scripting.definitions.ScriptDependenciesProvider;
import org.jetbrains.kotlin.scripting.resolve.ScriptCompilationConfigurationWrapper;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ScriptExtraImportsProviderExtension.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\bB\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/scripting/extensions/ScriptExtraImportsProviderExtension;", "Lorg/jetbrains/kotlin/resolve/extensions/ExtraImportsProviderExtension;", "()V", "getExtraImports", "", "Lorg/jetbrains/kotlin/psi/KtImportInfo;", "ktFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "ScriptExtraImportImpl", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptExtraImportsProviderExtension implements ExtraImportsProviderExtension {

    /* JADX INFO: compiled from: ScriptExtraImportsProviderExtension.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\u0004\u0018\u00010\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/scripting/extensions/ScriptExtraImportsProviderExtension$ScriptExtraImportImpl;", "Lorg/jetbrains/kotlin/psi/KtImportInfo;", "importPath", "Lorg/jetbrains/kotlin/resolve/ImportPath;", "(Lorg/jetbrains/kotlin/resolve/ImportPath;)V", "aliasName", "", "getAliasName", "()Ljava/lang/String;", "importContent", "Lorg/jetbrains/kotlin/psi/KtImportInfo$ImportContent$FqNameBased;", "getImportContent", "()Lorg/jetbrains/kotlin/psi/KtImportInfo$ImportContent$FqNameBased;", "importedFqName", "Lorg/jetbrains/kotlin/name/FqName;", "getImportedFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "isAllUnder", "", "()Z", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ScriptExtraImportImpl implements KtImportInfo {
        private final KtImportInfo.ImportContent.FqNameBased importContent;
        private final ImportPath importPath;

        public ScriptExtraImportImpl(ImportPath importPath) {
            Intrinsics.checkNotNullParameter(importPath, "importPath");
            this.importPath = importPath;
            this.importContent = new KtImportInfo.ImportContent.FqNameBased(importPath.getFqName());
        }

        @Override // org.jetbrains.kotlin.psi.KtImportInfo
        public boolean isAllUnder() {
            return this.importPath.isAllUnder();
        }

        @Override // org.jetbrains.kotlin.psi.KtImportInfo
        public KtImportInfo.ImportContent.FqNameBased getImportContent() {
            return this.importContent;
        }

        @Override // org.jetbrains.kotlin.psi.KtImportInfo
        public String getAliasName() {
            Name alias = this.importPath.getAlias();
            if (alias != null) {
                return alias.asString();
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.psi.KtImportInfo
        public FqName getImportedFqName() {
            return this.importPath.getFqName();
        }
    }

    @Override // org.jetbrains.kotlin.resolve.extensions.ExtraImportsProviderExtension
    public Collection<KtImportInfo> getExtraImports(final KtFile ktFile) {
        ScriptCompilationConfigurationWrapper scriptConfiguration;
        List<String> defaultImports;
        Intrinsics.checkNotNullParameter(ktFile, "ktFile");
        List listEmptyList = null;
        if (!((Boolean) ApplicationManager.getApplication().runReadAction(new Computable() { // from class: org.jetbrains.kotlin.scripting.extensions.ScriptExtraImportsProviderExtension$getExtraImports$lambda$1$$inlined$runReadAction$1
            @Override // org.jetbrains.kotlin.com.intellij.openapi.util.Computable
            public final T compute() {
                return (T) Boolean.valueOf(ktFile.isScript());
            }
        })).booleanValue()) {
            ktFile = null;
        }
        if (ktFile != null) {
            ScriptDependenciesProvider.Companion companion = ScriptDependenciesProvider.INSTANCE;
            Project project = ktFile.getProject();
            Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
            ScriptDependenciesProvider companion2 = companion.getInstance(project);
            if (companion2 != null) {
                PsiFile originalFile = ktFile.getOriginalFile();
                Intrinsics.checkNotNull(originalFile, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFile");
                scriptConfiguration = companion2.getScriptConfiguration((KtFile) originalFile);
            } else {
                scriptConfiguration = null;
            }
            if (scriptConfiguration != null && (defaultImports = scriptConfiguration.getDefaultImports()) != null) {
                List<String> list = defaultImports;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(new ScriptExtraImportImpl(ImportPath.INSTANCE.fromString((String) it.next())));
                }
                listEmptyList = arrayList;
            }
        }
        if (listEmptyList == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        return listEmptyList;
    }
}
