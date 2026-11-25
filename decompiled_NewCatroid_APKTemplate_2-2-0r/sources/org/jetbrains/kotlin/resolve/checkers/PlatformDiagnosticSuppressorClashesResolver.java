package org.jetbrains.kotlin.resolve.checkers;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.PlatformExtensionsClashResolver;
import org.jetbrains.kotlin.container.PlatformSpecificExtension;

/* JADX INFO: compiled from: PlatformDiagnosticSuppressor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\u00020\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/PlatformDiagnosticSuppressorClashesResolver;", "Lorg/jetbrains/kotlin/container/PlatformExtensionsClashResolver;", "Lorg/jetbrains/kotlin/resolve/checkers/PlatformDiagnosticSuppressor;", "()V", "resolveExtensionsClash", "extensions", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PlatformDiagnosticSuppressorClashesResolver extends PlatformExtensionsClashResolver<PlatformDiagnosticSuppressor> {
    public PlatformDiagnosticSuppressorClashesResolver() {
        super(PlatformDiagnosticSuppressor.class);
    }

    @Override // org.jetbrains.kotlin.container.PlatformExtensionsClashResolver
    public /* bridge */ /* synthetic */ PlatformSpecificExtension resolveExtensionsClash(List list) {
        return resolveExtensionsClash((List<? extends PlatformDiagnosticSuppressor>) list);
    }

    @Override // org.jetbrains.kotlin.container.PlatformExtensionsClashResolver
    public PlatformDiagnosticSuppressor resolveExtensionsClash(List<? extends PlatformDiagnosticSuppressor> extensions) {
        Intrinsics.checkNotNullParameter(extensions, "extensions");
        return new CompositePlatformDiagnosticSuppressor(extensions);
    }
}
