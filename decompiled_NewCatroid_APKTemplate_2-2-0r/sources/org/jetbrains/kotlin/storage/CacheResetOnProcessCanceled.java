package org.jetbrains.kotlin.storage;

import kotlin.Metadata;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.ide.util.PropertiesComponent;

/* JADX INFO: compiled from: ExceptionTracker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R$\u0010\b\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/storage/CacheResetOnProcessCanceled;", "", "()V", "DEFAULT_VALUE", "", "PROPERTY", "", "value", CompilerOptions.ENABLED, "getEnabled", "()Z", "setEnabled", "(Z)V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CacheResetOnProcessCanceled {
    public static final CacheResetOnProcessCanceled INSTANCE = new CacheResetOnProcessCanceled();

    private CacheResetOnProcessCanceled() {
    }

    public final boolean getEnabled() {
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        if (propertiesComponent != null) {
            return propertiesComponent.getBoolean("kotlin.internal.cacheResetOnProcessCanceled", false);
        }
        return false;
    }

    public final void setEnabled(boolean z) {
        PropertiesComponent propertiesComponent = PropertiesComponent.getInstance();
        if (propertiesComponent != null) {
            propertiesComponent.setValue("kotlin.internal.cacheResetOnProcessCanceled", z, false);
        }
    }
}
