package org.jetbrains.kotlin.platform.konan;

import io.netty.util.internal.StringUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.platform.SimplePlatform;

/* JADX INFO: compiled from: NativePlatform.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0002\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/platform/konan/NativePlatform;", "Lorg/jetbrains/kotlin/platform/SimplePlatform;", "()V", "oldFashionedDescription", "", "getOldFashionedDescription", "()Ljava/lang/String;", "Lorg/jetbrains/kotlin/platform/konan/NativePlatformUnspecifiedTarget;", "Lorg/jetbrains/kotlin/platform/konan/NativePlatformWithTarget;", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class NativePlatform extends SimplePlatform {
    public /* synthetic */ NativePlatform(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private NativePlatform() {
        super("Native");
    }

    @Override // org.jetbrains.kotlin.platform.SimplePlatform
    public String getOldFashionedDescription() {
        return new StringBuilder().append(this).append(StringUtil.SPACE).toString();
    }
}
