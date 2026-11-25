package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.resolve.ImportPath;
import org.jetbrains.kotlin.resolve.PlatformDependentAnalyzerServices;

/* JADX INFO: compiled from: DefaultImportPriority.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH&j\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/DefaultImportPriority;", "", "(Ljava/lang/String;I)V", "getAllDefaultImports", "", "Lorg/jetbrains/kotlin/resolve/ImportPath;", "platformDependentAnalyzerServices", "Lorg/jetbrains/kotlin/resolve/PlatformDependentAnalyzerServices;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "HIGH", "LOW", "KOTLIN_THROWS", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum DefaultImportPriority {
    HIGH { // from class: org.jetbrains.kotlin.fir.scopes.impl.DefaultImportPriority.HIGH
        @Override // org.jetbrains.kotlin.fir.scopes.impl.DefaultImportPriority
        public List<ImportPath> getAllDefaultImports(PlatformDependentAnalyzerServices platformDependentAnalyzerServices, LanguageVersionSettings languageVersionSettings) {
            List<ImportPath> defaultImports;
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            if (platformDependentAnalyzerServices == null || (defaultImports = platformDependentAnalyzerServices.getDefaultImports(languageVersionSettings, false)) == null) {
                return null;
            }
            return defaultImports;
        }
    },
    LOW { // from class: org.jetbrains.kotlin.fir.scopes.impl.DefaultImportPriority.LOW
        @Override // org.jetbrains.kotlin.fir.scopes.impl.DefaultImportPriority
        public List<ImportPath> getAllDefaultImports(PlatformDependentAnalyzerServices platformDependentAnalyzerServices, LanguageVersionSettings languageVersionSettings) {
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            if (platformDependentAnalyzerServices != null) {
                return platformDependentAnalyzerServices.getDefaultLowPriorityImports();
            }
            return null;
        }
    },
    KOTLIN_THROWS { // from class: org.jetbrains.kotlin.fir.scopes.impl.DefaultImportPriority.KOTLIN_THROWS
        @Override // org.jetbrains.kotlin.fir.scopes.impl.DefaultImportPriority
        public List<ImportPath> getAllDefaultImports(PlatformDependentAnalyzerServices platformDependentAnalyzerServices, LanguageVersionSettings languageVersionSettings) {
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            return CollectionsKt.listOf(ImportPath.INSTANCE.fromString("kotlin.Throws"));
        }
    };

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* synthetic */ DefaultImportPriority(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public static EnumEntries<DefaultImportPriority> getEntries() {
        return $ENTRIES;
    }

    public abstract List<ImportPath> getAllDefaultImports(PlatformDependentAnalyzerServices platformDependentAnalyzerServices, LanguageVersionSettings languageVersionSettings);
}
