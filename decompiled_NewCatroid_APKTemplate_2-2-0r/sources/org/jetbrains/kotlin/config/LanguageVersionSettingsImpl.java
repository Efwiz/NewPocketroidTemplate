package org.jetbrains.kotlin.config;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.chunk.SpecBlock;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: compiled from: LanguageVersionSettings.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cBI\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u001a\b\u0002\u0010\u0006\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0007\u0012\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\u0007¢\u0006\u0002\u0010\rJ\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u000bH\u0016J!\u0010\u0014\u001a\u0002H\u0015\"\u0004\b\u0000\u0010\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00150\bH\u0016¢\u0006\u0002\u0010\u0017J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u001c\u0010\u0006\u001a\u0010\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b\u0012\u0002\b\u00030\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/config/LanguageVersionSettingsImpl;", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "languageVersion", "Lorg/jetbrains/kotlin/config/LanguageVersion;", "apiVersion", "Lorg/jetbrains/kotlin/config/ApiVersion;", "analysisFlags", "", "Lorg/jetbrains/kotlin/config/AnalysisFlag;", "", "specificFeatures", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "Lorg/jetbrains/kotlin/config/LanguageFeature$State;", "(Lorg/jetbrains/kotlin/config/LanguageVersion;Lorg/jetbrains/kotlin/config/ApiVersion;Ljava/util/Map;Ljava/util/Map;)V", "getApiVersion", "()Lorg/jetbrains/kotlin/config/ApiVersion;", "getLanguageVersion", "()Lorg/jetbrains/kotlin/config/LanguageVersion;", "getFeatureSupport", ConfigConstants.CONFIG_FEATURE_SECTION, "getFlag", ExifInterface.GPS_DIRECTION_TRUE, SpecBlock.NAME_flag, "(Lorg/jetbrains/kotlin/config/AnalysisFlag;)Ljava/lang/Object;", "isPreRelease", "", "toString", "", "Companion", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LanguageVersionSettingsImpl implements LanguageVersionSettings {
    public static final LanguageVersionSettingsImpl DEFAULT = new LanguageVersionSettingsImpl(LanguageVersion.LATEST_STABLE, ApiVersion.LATEST_STABLE, null, null, 12, null);
    private final Map<AnalysisFlag<?>, ?> analysisFlags;
    private final ApiVersion apiVersion;
    private final LanguageVersion languageVersion;
    private final Map<LanguageFeature, LanguageFeature.State> specificFeatures;

    /* JADX INFO: compiled from: LanguageVersionSettings.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LanguageFeature.State.values().length];
            try {
                iArr[LanguageFeature.State.ENABLED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LanguageFeature.State.ENABLED_WITH_WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[LanguageFeature.State.DISABLED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LanguageVersionSettingsImpl(LanguageVersion languageVersion, ApiVersion apiVersion) {
        this(languageVersion, apiVersion, null, null, 12, null);
        Intrinsics.checkNotNullParameter(languageVersion, "languageVersion");
        Intrinsics.checkNotNullParameter(apiVersion, "apiVersion");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public LanguageVersionSettingsImpl(LanguageVersion languageVersion, ApiVersion apiVersion, Map<AnalysisFlag<?>, ? extends Object> analysisFlags) {
        this(languageVersion, apiVersion, analysisFlags, null, 8, null);
        Intrinsics.checkNotNullParameter(languageVersion, "languageVersion");
        Intrinsics.checkNotNullParameter(apiVersion, "apiVersion");
        Intrinsics.checkNotNullParameter(analysisFlags, "analysisFlags");
    }

    public LanguageVersionSettingsImpl(LanguageVersion languageVersion, ApiVersion apiVersion, Map<AnalysisFlag<?>, ? extends Object> analysisFlags, Map<LanguageFeature, ? extends LanguageFeature.State> specificFeatures) {
        Intrinsics.checkNotNullParameter(languageVersion, "languageVersion");
        Intrinsics.checkNotNullParameter(apiVersion, "apiVersion");
        Intrinsics.checkNotNullParameter(analysisFlags, "analysisFlags");
        Intrinsics.checkNotNullParameter(specificFeatures, "specificFeatures");
        this.languageVersion = languageVersion;
        this.apiVersion = apiVersion;
        Map<AnalysisFlag<?>, ?> mapUnmodifiableMap = Collections.unmodifiableMap(analysisFlags);
        Intrinsics.checkNotNullExpressionValue(mapUnmodifiableMap, "unmodifiableMap(...)");
        this.analysisFlags = mapUnmodifiableMap;
        Map<LanguageFeature, LanguageFeature.State> mapUnmodifiableMap2 = Collections.unmodifiableMap(specificFeatures);
        Intrinsics.checkNotNullExpressionValue(mapUnmodifiableMap2, "unmodifiableMap(...)");
        this.specificFeatures = mapUnmodifiableMap2;
    }

    @Override // org.jetbrains.kotlin.config.LanguageVersionSettings
    public LanguageVersion getLanguageVersion() {
        return this.languageVersion;
    }

    @Override // org.jetbrains.kotlin.config.LanguageVersionSettings
    public ApiVersion getApiVersion() {
        return this.apiVersion;
    }

    public /* synthetic */ LanguageVersionSettingsImpl(LanguageVersion languageVersion, ApiVersion apiVersion, Map map, Map map2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(languageVersion, apiVersion, (i & 4) != 0 ? MapsKt.emptyMap() : map, (i & 8) != 0 ? MapsKt.emptyMap() : map2);
    }

    @Override // org.jetbrains.kotlin.config.LanguageVersionSettings
    public <T> T getFlag(AnalysisFlag<? extends T> flag) {
        Intrinsics.checkNotNullParameter(flag, "flag");
        T t = (T) this.analysisFlags.get(flag);
        return t == null ? flag.getDefaultValue() : t;
    }

    @Override // org.jetbrains.kotlin.config.LanguageVersionSettings
    public LanguageFeature.State getFeatureSupport(LanguageFeature feature) {
        Intrinsics.checkNotNullParameter(feature, "feature");
        LanguageFeature.State state = this.specificFeatures.get(feature);
        if (state != null) {
            return state;
        }
        LanguageVersion sinceVersion = feature.getSinceVersion();
        if (sinceVersion == null || getLanguageVersion().compareTo(sinceVersion) < 0 || getApiVersion().compareTo(feature.getSinceApiVersion()) < 0) {
            return LanguageFeature.State.DISABLED;
        }
        return feature.getIsEnabledWithWarning() ? LanguageFeature.State.ENABLED_WITH_WARNING : LanguageFeature.State.ENABLED;
    }

    public String toString() {
        char c;
        StringBuilder sb = new StringBuilder();
        sb.append("Language = " + getLanguageVersion() + ", API = " + getApiVersion());
        for (Map.Entry entry : CollectionsKt.sortedWith(this.specificFeatures.entrySet(), new Comparator() { // from class: org.jetbrains.kotlin.config.LanguageVersionSettingsImpl$toString$lambda$5$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(Integer.valueOf(((LanguageFeature) ((Map.Entry) t).getKey()).ordinal()), Integer.valueOf(((LanguageFeature) ((Map.Entry) t2).getKey()).ordinal()));
            }
        })) {
            LanguageFeature languageFeature = (LanguageFeature) entry.getKey();
            int i = WhenMappings.$EnumSwitchMapping$0[((LanguageFeature.State) entry.getValue()).ordinal()];
            if (i == 1) {
                c = '+';
            } else if (i == 2) {
                c = '~';
            } else {
                if (i != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                c = '-';
            }
            sb.append(" " + c + languageFeature);
        }
        for (Map.Entry entry2 : CollectionsKt.sortedWith(this.analysisFlags.entrySet(), new Comparator() { // from class: org.jetbrains.kotlin.config.LanguageVersionSettingsImpl$toString$lambda$5$$inlined$sortedBy$2
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(((AnalysisFlag) ((Map.Entry) t).getKey()).getName(), ((AnalysisFlag) ((Map.Entry) t2).getKey()).getName());
            }
        })) {
            sb.append(" " + ((AnalysisFlag) entry2.getKey()) + ':' + entry2.getValue());
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    @Override // org.jetbrains.kotlin.config.LanguageVersionSettings
    public boolean isPreRelease() {
        if (getLanguageVersion().isStable()) {
            Map<LanguageFeature, LanguageFeature.State> map = this.specificFeatures;
            if (!map.isEmpty()) {
                for (Map.Entry<LanguageFeature, LanguageFeature.State> entry : map.entrySet()) {
                    LanguageFeature key = entry.getKey();
                    if (entry.getValue() != LanguageFeature.State.ENABLED || !LanguageVersionSettingsKt.forcesPreReleaseBinariesIfEnabled(key)) {
                    }
                }
            }
            return false;
        }
        return true;
    }
}
