package org.jetbrains.kotlin.analyzer;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.config.LanguageVersionSettingsImpl;
import org.jetbrains.kotlin.platform.TargetPlatformVersion;

/* JADX INFO: compiled from: AnalyzerFacade.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\nJ\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/LanguageSettingsProvider;", "", "getLanguageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "moduleInfo", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "getTargetPlatform", "Lorg/jetbrains/kotlin/platform/TargetPlatformVersion;", "Default", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LanguageSettingsProvider {
    LanguageVersionSettings getLanguageVersionSettings(ModuleInfo moduleInfo, Project project);

    TargetPlatformVersion getTargetPlatform(ModuleInfo moduleInfo, Project project);

    /* JADX INFO: compiled from: AnalyzerFacade.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/LanguageSettingsProvider$Default;", "Lorg/jetbrains/kotlin/analyzer/LanguageSettingsProvider;", "()V", "getLanguageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettingsImpl;", "moduleInfo", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "getTargetPlatform", "Lorg/jetbrains/kotlin/platform/TargetPlatformVersion;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements LanguageSettingsProvider {
        public static final Default INSTANCE = new Default();

        private Default() {
        }

        @Override // org.jetbrains.kotlin.analyzer.LanguageSettingsProvider
        public LanguageVersionSettingsImpl getLanguageVersionSettings(ModuleInfo moduleInfo, Project project) {
            Intrinsics.checkNotNullParameter(moduleInfo, "moduleInfo");
            Intrinsics.checkNotNullParameter(project, "project");
            return LanguageVersionSettingsImpl.DEFAULT;
        }

        @Override // org.jetbrains.kotlin.analyzer.LanguageSettingsProvider
        public TargetPlatformVersion getTargetPlatform(ModuleInfo moduleInfo, Project project) {
            Intrinsics.checkNotNullParameter(moduleInfo, "moduleInfo");
            Intrinsics.checkNotNullParameter(project, "project");
            return TargetPlatformVersion.NoVersion.INSTANCE;
        }
    }
}
