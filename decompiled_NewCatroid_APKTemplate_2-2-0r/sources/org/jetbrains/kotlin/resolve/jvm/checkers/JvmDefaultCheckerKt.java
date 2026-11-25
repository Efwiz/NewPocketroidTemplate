package org.jetbrains.kotlin.resolve.jvm.checkers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.DeserializedDescriptor;
import org.jetbrains.kotlin.resolve.LanguageVersionSettingsProvider;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;

/* JADX INFO: compiled from: JvmDefaultChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0000¨\u0006\u0007"}, d2 = {"isCompiledToJvmDefaultWithProperMode", "", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "ideService", "Lorg/jetbrains/kotlin/resolve/LanguageVersionSettingsProvider;", "compilationDefaultMode", "Lorg/jetbrains/kotlin/config/JvmDefaultMode;", "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmDefaultCheckerKt {
    public static final boolean isCompiledToJvmDefaultWithProperMode(CallableMemberDescriptor callableMemberDescriptor, LanguageVersionSettingsProvider languageVersionSettingsProvider, JvmDefaultMode compilationDefaultMode) {
        LanguageVersionSettings moduleLanguageVersionSettings;
        JvmDefaultMode jvmDefaultMode;
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(compilationDefaultMode, "compilationDefaultMode");
        if (!(callableMemberDescriptor instanceof DeserializedDescriptor) && languageVersionSettingsProvider != null && (moduleLanguageVersionSettings = languageVersionSettingsProvider.getModuleLanguageVersionSettings(DescriptorUtilsKt.getModule(callableMemberDescriptor))) != null && (jvmDefaultMode = (JvmDefaultMode) moduleLanguageVersionSettings.getFlag(JvmAnalysisFlags.getJvmDefaultMode())) != null) {
            compilationDefaultMode = jvmDefaultMode;
        }
        return JvmAnnotationUtilKt.isCompiledToJvmDefault(callableMemberDescriptor, compilationDefaultMode);
    }
}
