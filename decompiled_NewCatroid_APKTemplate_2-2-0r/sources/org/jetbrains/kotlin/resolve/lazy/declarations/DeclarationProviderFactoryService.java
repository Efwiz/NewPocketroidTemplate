package org.jetbrains.kotlin.resolve.lazy.declarations;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: DeclarationProviderFactoryService.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 \u00102\u00020\u0001:\u0002\u0010\u0011B\u0005¢\u0006\u0002\u0010\u0002J6\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH&¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProviderFactoryService;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProviderFactory;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "syntheticFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "filesScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "moduleInfo", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "Companion", "SyntheticFilesFilteringScope", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DeclarationProviderFactoryService {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final DeclarationProviderFactory createDeclarationProviderFactory(Project project, StorageManager storageManager, Collection<? extends KtFile> collection, GlobalSearchScope globalSearchScope, ModuleInfo moduleInfo) {
        return INSTANCE.createDeclarationProviderFactory(project, storageManager, collection, globalSearchScope, moduleInfo);
    }

    public abstract DeclarationProviderFactory create(Project project, StorageManager storageManager, Collection<? extends KtFile> syntheticFiles, GlobalSearchScope filesScope, ModuleInfo moduleInfo);

    /* JADX INFO: compiled from: DeclarationProviderFactoryService.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J6\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u001e\u0010\u0010\u001a\u00020\r2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0011\u001a\u00020\rH\u0002¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProviderFactoryService$Companion;", "", "()V", "createDeclarationProviderFactory", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProviderFactory;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "syntheticFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "moduleContentScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "moduleInfo", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "filteringScope", "baseScope", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final DeclarationProviderFactory createDeclarationProviderFactory(Project project, StorageManager storageManager, Collection<? extends KtFile> syntheticFiles, GlobalSearchScope moduleContentScope, ModuleInfo moduleInfo) {
            Intrinsics.checkNotNullParameter(project, "project");
            Intrinsics.checkNotNullParameter(storageManager, "storageManager");
            Intrinsics.checkNotNullParameter(syntheticFiles, "syntheticFiles");
            Intrinsics.checkNotNullParameter(moduleContentScope, "moduleContentScope");
            Intrinsics.checkNotNullParameter(moduleInfo, "moduleInfo");
            Object service = project.getService(DeclarationProviderFactoryService.class);
            Intrinsics.checkNotNull(service);
            return ((DeclarationProviderFactoryService) service).create(project, storageManager, syntheticFiles, filteringScope(syntheticFiles, moduleContentScope), moduleInfo);
        }

        private final GlobalSearchScope filteringScope(Collection<? extends KtFile> syntheticFiles, GlobalSearchScope baseScope) {
            return (syntheticFiles.isEmpty() || Intrinsics.areEqual(baseScope, GlobalSearchScope.EMPTY_SCOPE)) ? baseScope : new SyntheticFilesFilteringScope(syntheticFiles, baseScope);
        }
    }

    /* JADX INFO: compiled from: DeclarationProviderFactoryService.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\nH\u0096\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/declarations/DeclarationProviderFactoryService$SyntheticFilesFilteringScope;", "Lorg/jetbrains/kotlin/com/intellij/psi/search/DelegatingGlobalSearchScope;", "syntheticFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "baseScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "(Ljava/util/Collection;Lcom/intellij/psi/search/GlobalSearchScope;)V", "originals", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "contains", "", "file", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class SyntheticFilesFilteringScope extends DelegatingGlobalSearchScope {
        private final HashSet<VirtualFile> originals;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SyntheticFilesFilteringScope(Collection<? extends KtFile> syntheticFiles, GlobalSearchScope baseScope) {
            super(baseScope);
            Intrinsics.checkNotNullParameter(syntheticFiles, "syntheticFiles");
            Intrinsics.checkNotNullParameter(baseScope, "baseScope");
            HashSet<VirtualFile> hashSet = new HashSet<>();
            Iterator<T> it = syntheticFiles.iterator();
            while (it.hasNext()) {
                VirtualFile virtualFile = ((KtFile) it.next()).getOriginalFile().getVirtualFile();
                if (virtualFile != null) {
                    hashSet.add(virtualFile);
                }
            }
            this.originals = hashSet;
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope, org.jetbrains.kotlin.com.intellij.psi.search.SearchScope
        public boolean contains(VirtualFile file) {
            Intrinsics.checkNotNullParameter(file, "file");
            return super.contains(file) && !this.originals.contains(file);
        }

        @Override // org.jetbrains.kotlin.com.intellij.psi.search.DelegatingGlobalSearchScope
        public String toString() {
            return "SyntheticFilesFilteringScope(" + this.myBaseScope + ')';
        }
    }
}
