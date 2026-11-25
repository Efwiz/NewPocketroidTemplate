package org.jetbrains.kotlin.resolve.konan.diagnostics;

import kotlin.Metadata;
import org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticFactoryToRendererMap;

/* JADX INFO: compiled from: DefaultErrorMessagesNative.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/konan/diagnostics/DefaultErrorMessagesNative;", "Lorg/jetbrains/kotlin/diagnostics/rendering/DefaultErrorMessages$Extension;", "()V", "getMap", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticFactoryToRendererMap;", "frontend.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultErrorMessagesNative implements DefaultErrorMessages.Extension {
    @Override // org.jetbrains.kotlin.diagnostics.rendering.DefaultErrorMessages.Extension
    public DiagnosticFactoryToRendererMap getMap() {
        return DefaultErrorMessagesNativeKt.getDIAGNOSTIC_FACTORY_TO_RENDERER();
    }
}
