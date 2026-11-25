package org.jetbrains.kotlin.analyzer;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: AnalyzerFacade.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003B#\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u000e\u0010\u0012\u001a\u00028\u0000HÆ\u0003¢\u0006\u0002\u0010\u000eJ\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006HÆ\u0003J\t\u0010\u0014\u001a\u00020\tHÆ\u0003J8\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0004\u001a\u00028\u00002\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001¢\u0006\u0002\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0003HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u001dHÖ\u0001R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/ModuleContent;", "M", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "", "moduleInfo", "syntheticFiles", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "moduleContentScope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;Ljava/util/Collection;Lcom/intellij/psi/search/GlobalSearchScope;)V", "getModuleContentScope", "()Lcom/intellij/psi/search/GlobalSearchScope;", "getModuleInfo", "()Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "getSyntheticFiles", "()Ljava/util/Collection;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(Lorg/jetbrains/kotlin/analyzer/ModuleInfo;Ljava/util/Collection;Lcom/intellij/psi/search/GlobalSearchScope;)Lorg/jetbrains/kotlin/analyzer/ModuleContent;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ModuleContent<M extends ModuleInfo> {
    private final GlobalSearchScope moduleContentScope;
    private final M moduleInfo;
    private final Collection<KtFile> syntheticFiles;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ModuleContent copy$default(ModuleContent moduleContent, ModuleInfo moduleInfo, Collection collection, GlobalSearchScope globalSearchScope, int i, Object obj) {
        if ((i & 1) != 0) {
            moduleInfo = moduleContent.moduleInfo;
        }
        if ((i & 2) != 0) {
            collection = moduleContent.syntheticFiles;
        }
        if ((i & 4) != 0) {
            globalSearchScope = moduleContent.moduleContentScope;
        }
        return moduleContent.copy(moduleInfo, collection, globalSearchScope);
    }

    public final M component1() {
        return this.moduleInfo;
    }

    public final Collection<KtFile> component2() {
        return this.syntheticFiles;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final GlobalSearchScope getModuleContentScope() {
        return this.moduleContentScope;
    }

    public final ModuleContent<M> copy(M moduleInfo, Collection<? extends KtFile> syntheticFiles, GlobalSearchScope moduleContentScope) {
        Intrinsics.checkNotNullParameter(moduleInfo, "moduleInfo");
        Intrinsics.checkNotNullParameter(syntheticFiles, "syntheticFiles");
        Intrinsics.checkNotNullParameter(moduleContentScope, "moduleContentScope");
        return new ModuleContent<>(moduleInfo, syntheticFiles, moduleContentScope);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ModuleContent)) {
            return false;
        }
        ModuleContent moduleContent = (ModuleContent) other;
        return Intrinsics.areEqual(this.moduleInfo, moduleContent.moduleInfo) && Intrinsics.areEqual(this.syntheticFiles, moduleContent.syntheticFiles) && Intrinsics.areEqual(this.moduleContentScope, moduleContent.moduleContentScope);
    }

    public int hashCode() {
        return (((this.moduleInfo.hashCode() * 31) + this.syntheticFiles.hashCode()) * 31) + this.moduleContentScope.hashCode();
    }

    public String toString() {
        return "ModuleContent(moduleInfo=" + this.moduleInfo + ", syntheticFiles=" + this.syntheticFiles + ", moduleContentScope=" + this.moduleContentScope + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ModuleContent(M moduleInfo, Collection<? extends KtFile> syntheticFiles, GlobalSearchScope moduleContentScope) {
        Intrinsics.checkNotNullParameter(moduleInfo, "moduleInfo");
        Intrinsics.checkNotNullParameter(syntheticFiles, "syntheticFiles");
        Intrinsics.checkNotNullParameter(moduleContentScope, "moduleContentScope");
        this.moduleInfo = moduleInfo;
        this.syntheticFiles = syntheticFiles;
        this.moduleContentScope = moduleContentScope;
    }

    public final M getModuleInfo() {
        return this.moduleInfo;
    }

    public final Collection<KtFile> getSyntheticFiles() {
        return this.syntheticFiles;
    }

    public final GlobalSearchScope getModuleContentScope() {
        return this.moduleContentScope;
    }
}
