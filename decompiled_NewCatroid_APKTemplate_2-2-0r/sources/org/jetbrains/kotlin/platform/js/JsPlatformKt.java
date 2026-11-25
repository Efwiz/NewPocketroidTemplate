package org.jetbrains.kotlin.platform.js;

import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import org.jetbrains.kotlin.platform.TargetPlatform;

/* JADX INFO: compiled from: JsPlatform.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002H\u0007¨\u0006\u0003"}, d2 = {"isJs", "", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "js.config"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsPlatformKt {
    @Deprecated(message = "For binary compatibility, please use org.jetbrains.kotlin.platform.isJs", replaceWith = @ReplaceWith(expression = "this.isJs()", imports = {"org.jetbrains.kotlin.platform.isJs"}))
    public static final boolean isJs(TargetPlatform targetPlatform) {
        return org.jetbrains.kotlin.platform.JsPlatformKt.isJs(targetPlatform);
    }
}
