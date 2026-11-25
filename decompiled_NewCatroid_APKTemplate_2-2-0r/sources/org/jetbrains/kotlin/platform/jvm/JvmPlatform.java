package org.jetbrains.kotlin.platform.jvm;

import kotlin.Metadata;
import org.jetbrains.kotlin.platform.SimplePlatform;

/* JADX INFO: compiled from: JvmPlatform.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/platform/jvm/JvmPlatform;", "Lorg/jetbrains/kotlin/platform/SimplePlatform;", "()V", "oldFashionedDescription", "", "getOldFashionedDescription", "()Ljava/lang/String;", "config.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class JvmPlatform extends SimplePlatform {
    public JvmPlatform() {
        super("JVM");
    }

    @Override // org.jetbrains.kotlin.platform.SimplePlatform
    public String getOldFashionedDescription() {
        return "JVM ";
    }
}
