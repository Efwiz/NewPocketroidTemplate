package org.jetbrains.kotlin.analysis.project.structure;

import kotlin.Metadata;
import org.jetbrains.kotlin.config.LanguageVersionSettings;

/* JADX INFO: compiled from: KtModule.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u0016\u0010\f\u001a\u0004\u0018\u00010\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analysis/project/structure/KtSourceModule;", "Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "moduleDescription", "", "getModuleDescription", "()Ljava/lang/String;", "moduleName", "getModuleName", "stableModuleName", "getStableModuleName", "project-structure"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KtSourceModule extends KtModule {
    LanguageVersionSettings getLanguageVersionSettings();

    String getModuleName();

    default String getStableModuleName() {
        return null;
    }

    @Override // org.jetbrains.kotlin.analysis.project.structure.KtModule
    default String getModuleDescription() {
        return "Sources of " + getModuleName();
    }
}
