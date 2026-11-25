package org.jetbrains.kotlin.cli.jvm.compiler;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.core.CoreApplicationEnvironment;
import org.jetbrains.kotlin.com.intellij.lang.jvm.facade.JvmElementProvider;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionsArea;
import org.jetbrains.kotlin.com.intellij.psi.JavaModuleSystem;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsCustomNavigationPolicy;

/* JADX INFO: compiled from: IdeaExtensionPoints.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/IdeaExtensionPoints;", "", "()V", "registerVersionSpecificAppExtensionPoints", "", "area", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionsArea;", "registerVersionSpecificProjectExtensionPoints", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IdeaExtensionPoints {
    public static final IdeaExtensionPoints INSTANCE = new IdeaExtensionPoints();

    private IdeaExtensionPoints() {
    }

    public final void registerVersionSpecificAppExtensionPoints(ExtensionsArea area) {
        Intrinsics.checkNotNullParameter(area, "area");
        CoreApplicationEnvironment.registerExtensionPoint(area, ClsCustomNavigationPolicy.EP_NAME, ClsCustomNavigationPolicy.class);
        CoreApplicationEnvironment.registerExtensionPoint(area, JavaModuleSystem.EP_NAME, JavaModuleSystem.class);
    }

    public final void registerVersionSpecificProjectExtensionPoints(ExtensionsArea area) {
        Intrinsics.checkNotNullParameter(area, "area");
        CoreApplicationEnvironment.registerExtensionPoint(area, JvmElementProvider.EP_NAME, JvmElementProvider.class);
    }
}
