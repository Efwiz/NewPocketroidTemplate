package org.jetbrains.kotlin.fir.declarations;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.config.LanguageVersionSettings;

/* JADX INFO: compiled from: DeprecationsProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/EmptyDeprecationsProvider;", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsProvider;", "()V", "getDeprecationsInfo", "Lorg/jetbrains/kotlin/fir/declarations/DeprecationsPerUseSite;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EmptyDeprecationsProvider extends DeprecationsProvider {
    public static final EmptyDeprecationsProvider INSTANCE = new EmptyDeprecationsProvider();

    private EmptyDeprecationsProvider() {
    }

    @Override // org.jetbrains.kotlin.fir.declarations.DeprecationsProvider
    public DeprecationsPerUseSite getDeprecationsInfo(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        return DeprecationsKt.getEmptyDeprecationsPerUseSite();
    }
}
