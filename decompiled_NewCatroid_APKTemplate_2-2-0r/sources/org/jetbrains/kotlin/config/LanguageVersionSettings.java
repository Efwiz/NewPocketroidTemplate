package org.jetbrains.kotlin.config;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.chunk.SpecBlock;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: compiled from: LanguageVersionSettings.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH&J!\u0010\u000e\u001a\u0002H\u000f\"\u0004\b\u0000\u0010\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u0011H&¢\u0006\u0002\u0010\u0012J\b\u0010\u0013\u001a\u00020\u0014H&J\u0010\u0010\u0015\u001a\u00020\u00142\u0006\u0010\f\u001a\u00020\rH\u0016R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0017À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "", "apiVersion", "Lorg/jetbrains/kotlin/config/ApiVersion;", "getApiVersion", "()Lorg/jetbrains/kotlin/config/ApiVersion;", "languageVersion", "Lorg/jetbrains/kotlin/config/LanguageVersion;", "getLanguageVersion", "()Lorg/jetbrains/kotlin/config/LanguageVersion;", "getFeatureSupport", "Lorg/jetbrains/kotlin/config/LanguageFeature$State;", ConfigConstants.CONFIG_FEATURE_SECTION, "Lorg/jetbrains/kotlin/config/LanguageFeature;", "getFlag", ExifInterface.GPS_DIRECTION_TRUE, SpecBlock.NAME_flag, "Lorg/jetbrains/kotlin/config/AnalysisFlag;", "(Lorg/jetbrains/kotlin/config/AnalysisFlag;)Ljava/lang/Object;", "isPreRelease", "", "supportsFeature", "Companion", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface LanguageVersionSettings {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;
    public static final String RESOURCE_NAME_TO_ALLOW_READING_FROM_ENVIRONMENT = "META-INF/allow-configuring-from-environment";

    ApiVersion getApiVersion();

    LanguageFeature.State getFeatureSupport(LanguageFeature feature);

    <T> T getFlag(AnalysisFlag<? extends T> flag);

    LanguageVersion getLanguageVersion();

    boolean isPreRelease();

    default boolean supportsFeature(LanguageFeature feature) {
        Intrinsics.checkNotNullParameter(feature, "feature");
        LanguageFeature.State featureSupport = getFeatureSupport(feature);
        return featureSupport == LanguageFeature.State.ENABLED || featureSupport == LanguageFeature.State.ENABLED_WITH_WARNING;
    }

    /* JADX INFO: compiled from: LanguageVersionSettings.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/config/LanguageVersionSettings$Companion;", "", "()V", "RESOURCE_NAME_TO_ALLOW_READING_FROM_ENVIRONMENT", "", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final String RESOURCE_NAME_TO_ALLOW_READING_FROM_ENVIRONMENT = "META-INF/allow-configuring-from-environment";

        private Companion() {
        }
    }
}
