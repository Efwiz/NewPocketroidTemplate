package org.jetbrains.kotlin.analyzer;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollector;
import org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments;

/* JADX INFO: compiled from: AbstractResolverForProject.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J*\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00062\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u0017¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/DiagnoseUnknownModuleInfoReporter;", "", "()V", "errorInLibrariesResolver", "Lorg/jetbrains/kotlin/utils/KotlinExceptionWithAttachments;", CrashHianalyticsData.MESSAGE, "", "errorInModulesResolver", "errorInModulesResolverWithEmptyInfos", "errorInModulesResolverWithLibraryInfo", "errorInModulesResolverWithScriptDependencies", "errorInScriptDependenciesInfoResolver", "errorInScriptModuleInfoResolver", "errorInSdkResolver", "errorInSpecialModuleInfoResolver", "otherError", "report", "", "name", AbstractDiagnosticCollector.SUPPRESS_ALL_INFOS, "", "Lorg/jetbrains/kotlin/analyzer/ModuleInfo;", "allModules", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class DiagnoseUnknownModuleInfoReporter {
    public static final DiagnoseUnknownModuleInfoReporter INSTANCE = new DiagnoseUnknownModuleInfoReporter();

    private DiagnoseUnknownModuleInfoReporter() {
    }

    public final Void report(String name2, List<? extends ModuleInfo> infos, Collection<? extends ModuleInfo> allModules) {
        KotlinExceptionWithAttachments kotlinExceptionWithAttachmentsErrorInSdkResolver;
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(infos, "infos");
        Intrinsics.checkNotNullParameter(allModules, "allModules");
        String str = name2 + " does not know how to resolve";
        String str2 = name2;
        if (StringsKt.contains$default((CharSequence) str2, (CharSequence) ResolverForProject.resolverForSdkName, false, 2, (Object) null)) {
            kotlinExceptionWithAttachmentsErrorInSdkResolver = errorInSdkResolver(str);
        } else if (StringsKt.contains$default((CharSequence) str2, (CharSequence) ResolverForProject.resolverForLibrariesName, false, 2, (Object) null)) {
            kotlinExceptionWithAttachmentsErrorInSdkResolver = errorInLibrariesResolver(str);
        } else if (StringsKt.contains$default((CharSequence) str2, (CharSequence) ResolverForProject.resolverForModulesName, false, 2, (Object) null)) {
            if (infos.isEmpty()) {
                kotlinExceptionWithAttachmentsErrorInSdkResolver = errorInModulesResolverWithEmptyInfos(str);
            } else if (infos.size() == 1) {
                String string = CollectionsKt.single((List) infos).toString();
                if (StringsKt.contains$default((CharSequence) string, (CharSequence) "ScriptDependencies", false, 2, (Object) null)) {
                    kotlinExceptionWithAttachmentsErrorInSdkResolver = errorInModulesResolverWithScriptDependencies(str);
                } else {
                    kotlinExceptionWithAttachmentsErrorInSdkResolver = StringsKt.contains$default((CharSequence) string, (CharSequence) "Library", false, 2, (Object) null) ? errorInModulesResolverWithLibraryInfo(str) : errorInModulesResolver(str);
                }
            } else {
                kotlinExceptionWithAttachmentsErrorInSdkResolver = errorInModulesResolver(str);
            }
        } else if (StringsKt.contains$default((CharSequence) str2, (CharSequence) ResolverForProject.resolverForScriptDependenciesName, false, 2, (Object) null)) {
            kotlinExceptionWithAttachmentsErrorInSdkResolver = errorInScriptDependenciesInfoResolver(str);
        } else if (StringsKt.contains$default((CharSequence) str2, (CharSequence) ResolverForProject.resolverForSpecialInfoName, false, 2, (Object) null)) {
            kotlinExceptionWithAttachmentsErrorInSdkResolver = StringsKt.contains$default((CharSequence) str2, (CharSequence) "ScriptModuleInfo", false, 2, (Object) null) ? errorInScriptModuleInfoResolver(str) : errorInSpecialModuleInfoResolver(str);
        } else {
            kotlinExceptionWithAttachmentsErrorInSdkResolver = otherError(str);
        }
        throw kotlinExceptionWithAttachmentsErrorInSdkResolver.withAttachment("infos.txt", (Object) infos).withAttachment("allModules.txt", (Object) allModules);
    }

    private final KotlinExceptionWithAttachments errorInSdkResolver(String message) {
        return new KotlinExceptionWithAttachments(message);
    }

    private final KotlinExceptionWithAttachments errorInLibrariesResolver(String message) {
        return new KotlinExceptionWithAttachments(message);
    }

    private final KotlinExceptionWithAttachments errorInModulesResolver(String message) {
        return new KotlinExceptionWithAttachments(message);
    }

    private final KotlinExceptionWithAttachments errorInModulesResolverWithEmptyInfos(String message) {
        return new KotlinExceptionWithAttachments(message);
    }

    private final KotlinExceptionWithAttachments errorInModulesResolverWithScriptDependencies(String message) {
        return new KotlinExceptionWithAttachments(message);
    }

    private final KotlinExceptionWithAttachments errorInModulesResolverWithLibraryInfo(String message) {
        return new KotlinExceptionWithAttachments(message);
    }

    private final KotlinExceptionWithAttachments errorInScriptDependenciesInfoResolver(String message) {
        return new KotlinExceptionWithAttachments(message);
    }

    private final KotlinExceptionWithAttachments errorInScriptModuleInfoResolver(String message) {
        return new KotlinExceptionWithAttachments(message);
    }

    private final KotlinExceptionWithAttachments errorInSpecialModuleInfoResolver(String message) {
        return new KotlinExceptionWithAttachments(message);
    }

    private final KotlinExceptionWithAttachments otherError(String message) {
        return new KotlinExceptionWithAttachments(message);
    }
}
