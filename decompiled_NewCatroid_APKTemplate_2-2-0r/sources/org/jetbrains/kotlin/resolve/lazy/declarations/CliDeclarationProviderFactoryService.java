package org.jetbrains.kotlin.resolve.lazy.declarations;

import java.util.ArrayList;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: CliDeclarationProviderFactoryService.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J6\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/declarations/CliDeclarationProviderFactoryService;", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProviderFactoryService;", "sourceFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "(Ljava/util/Collection;)V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProviderFactory;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "syntheticFiles", "filesScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "moduleInfo", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CliDeclarationProviderFactoryService extends DeclarationProviderFactoryService {
    private final Collection<KtFile> sourceFiles;

    /* JADX WARN: Multi-variable type inference failed */
    public CliDeclarationProviderFactoryService(Collection<? extends KtFile> sourceFiles) {
        Intrinsics.checkNotNullParameter(sourceFiles, "sourceFiles");
        this.sourceFiles = sourceFiles;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.declarations.DeclarationProviderFactoryService
    public DeclarationProviderFactory create(Project project, StorageManager storageManager, Collection<? extends KtFile> syntheticFiles, GlobalSearchScope filesScope, ModuleInfo moduleInfo) {
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(syntheticFiles, "syntheticFiles");
        Intrinsics.checkNotNullParameter(filesScope, "filesScope");
        Intrinsics.checkNotNullParameter(moduleInfo, "moduleInfo");
        ArrayList arrayList = new ArrayList();
        for (Object obj : this.sourceFiles) {
            VirtualFile virtualFile = ((KtFile) obj).getVirtualFile();
            if (virtualFile != null) {
                if (filesScope.contains(virtualFile)) {
                    arrayList.add(obj);
                }
            } else {
                throw new AssertionError("Source files should be physical files");
            }
        }
        arrayList.addAll(syntheticFiles);
        return new FileBasedDeclarationProviderFactory(storageManager, arrayList);
    }
}
