package org.jetbrains.kotlin.fir.declarations;

import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirement;
import org.jetbrains.kotlin.resolve.deprecation.CommonDeprecationUtilsKt;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationLevelValue;

/* JADX INFO: compiled from: DeprecationsProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/RequireKotlinInfo;", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationAnnotationInfo;", "versionRequirement", "Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement;", "(Lorg/jetbrains/kotlin/metadata/deserialization/VersionRequirement;)V", "computeDeprecationInfo", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RequireKotlinInfo extends DeprecationAnnotationInfo {
    private final VersionRequirement versionRequirement;

    /* JADX INFO: compiled from: DeprecationsProvider.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DeprecationLevel.values().length];
            try {
                iArr[DeprecationLevel.WARNING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DeprecationLevel.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DeprecationLevel.HIDDEN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RequireKotlinInfo(VersionRequirement versionRequirement) {
        super(null);
        Intrinsics.checkNotNullParameter(versionRequirement, "versionRequirement");
        this.versionRequirement = versionRequirement;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.DeprecationAnnotationInfo
    public DeprecationInfo computeDeprecationInfo(LanguageVersionSettings languageVersionSettings) {
        RequireKotlinDeprecationInfo requireKotlinDeprecationInfo;
        DeprecationLevelValue deprecationLevelValue;
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        if (CommonDeprecationUtilsKt.isFulfilled(this.versionRequirement, languageVersionSettings)) {
            requireKotlinDeprecationInfo = null;
        } else {
            int i = WhenMappings.$EnumSwitchMapping$0[this.versionRequirement.getLevel().ordinal()];
            if (i == 1) {
                deprecationLevelValue = DeprecationLevelValue.WARNING;
            } else if (i == 2) {
                deprecationLevelValue = DeprecationLevelValue.ERROR;
            } else {
                if (i != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                deprecationLevelValue = DeprecationLevelValue.HIDDEN;
            }
            requireKotlinDeprecationInfo = new RequireKotlinDeprecationInfo(deprecationLevelValue, this.versionRequirement);
        }
        return requireKotlinDeprecationInfo;
    }
}
