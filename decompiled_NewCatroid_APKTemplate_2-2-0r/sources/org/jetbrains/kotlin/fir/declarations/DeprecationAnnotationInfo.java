package org.jetbrains.kotlin.fir.declarations;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationInfo;

/* JADX INFO: compiled from: DeprecationsProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&\u0082\u0001\u0004\u0007\b\t\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/DeprecationAnnotationInfo;", "", "()V", "computeDeprecationInfo", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationInfo;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "Lorg/jetbrains/kotlin/fir/declarations/DeprecatedInfo;", "Lorg/jetbrains/kotlin/fir/declarations/DeprecatedSinceKotlinInfo;", "Lorg/jetbrains/kotlin/fir/declarations/RequireKotlinInfo;", "Lorg/jetbrains/kotlin/fir/declarations/SinceKotlinInfo;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DeprecationAnnotationInfo {
    public /* synthetic */ DeprecationAnnotationInfo(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract DeprecationInfo computeDeprecationInfo(LanguageVersionSettings languageVersionSettings);

    private DeprecationAnnotationInfo() {
    }
}
