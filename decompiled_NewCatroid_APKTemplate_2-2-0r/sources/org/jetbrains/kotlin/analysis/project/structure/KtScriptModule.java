package org.jetbrains.kotlin.analysis.project.structure;

import kotlin.Metadata;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: KtModule.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analysis/project/structure/KtScriptModule;", "Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "getFile", "()Lorg/jetbrains/kotlin/psi/KtFile;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "moduleDescription", "", "getModuleDescription", "()Ljava/lang/String;", "project-structure"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtScriptModule extends KtModule {
    KtFile getFile();

    LanguageVersionSettings getLanguageVersionSettings();

    @Override // org.jetbrains.kotlin.analysis.project.structure.KtModule
    default String getModuleDescription() {
        return "Script " + getFile().getName();
    }
}
