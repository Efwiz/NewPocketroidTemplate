package org.jetbrains.kotlin.resolve.calls.components;

import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.resolve.calls.inference.components.ConstraintInjector;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: ClassicTypeSystemContextForCS.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0000\u001a&\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t\u001a\u000f\u0010\n\u001a\u00020\u000b*\u0004\u0018\u00010\fH\u0082\b¨\u0006\r"}, d2 = {"NewConstraintSystemImpl", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "constraintInjector", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", MLAsrCaptureConstants.ASR_ERROR_MESSAGE, "", "", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClassicTypeSystemContextForCSKt {
    public static final NewConstraintSystemImpl NewConstraintSystemImpl(ConstraintInjector constraintInjector, KotlinBuiltIns builtIns, KotlinTypeRefiner kotlinTypeRefiner, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(constraintInjector, "constraintInjector");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return new NewConstraintSystemImpl(constraintInjector, new ClassicTypeSystemContextForCS(builtIns, kotlinTypeRefiner), languageVersionSettings);
    }
}
