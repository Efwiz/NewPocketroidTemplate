package org.jetbrains.kotlin.cli.js;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.UtilsKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.psi.KtFile;

/* JADX INFO: compiled from: Helpers.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\u001a\u001c\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¨\u0006\u0007"}, d2 = {"checkKotlinPackageUsageForPsi", "", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "cli-js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class HelpersKt {
    public static final boolean checkKotlinPackageUsageForPsi(CompilerConfiguration configuration, Collection<? extends KtFile> files) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(files, "files");
        return UtilsKt.checkKotlinPackageUsageForPsi$default(configuration, files, null, 4, null);
    }
}
