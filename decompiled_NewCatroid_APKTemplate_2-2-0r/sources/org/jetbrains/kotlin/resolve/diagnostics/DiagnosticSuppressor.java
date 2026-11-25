package org.jetbrains.kotlin.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.resolve.BindingContext;

/* JADX INFO: compiled from: KotlinSuppressCache.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u0000 \b2\u00020\u0001:\u0001\bJ\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u001a\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\tÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/diagnostics/DiagnosticSuppressor;", "", "isSuppressed", "", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DiagnosticSuppressor {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    boolean isSuppressed(Diagnostic diagnostic);

    default boolean isSuppressed(Diagnostic diagnostic, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        return isSuppressed(diagnostic);
    }

    /* JADX INFO: compiled from: KotlinSuppressCache.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/diagnostics/DiagnosticSuppressor$Companion;", "", "()V", "EP_NAME", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/ExtensionPointName;", "Lorg/jetbrains/kotlin/resolve/diagnostics/DiagnosticSuppressor;", "getEP_NAME", "()Lcom/intellij/openapi/extensions/ExtensionPointName;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final ExtensionPointName<DiagnosticSuppressor> EP_NAME;

        private Companion() {
        }

        public final ExtensionPointName<DiagnosticSuppressor> getEP_NAME() {
            return EP_NAME;
        }

        static {
            ExtensionPointName<DiagnosticSuppressor> extensionPointNameCreate = ExtensionPointName.create("org.jetbrains.kotlin.diagnosticSuppressor");
            Intrinsics.checkNotNullExpressionValue(extensionPointNameCreate, "create(...)");
            EP_NAME = extensionPointNameCreate;
        }
    }
}
