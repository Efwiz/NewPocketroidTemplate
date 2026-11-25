package org.jetbrains.kotlin.diagnostics;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.mlplugin.asr.MLAsrCaptureConstants;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer;

/* JADX INFO: compiled from: KtDiagnosticFactoryToRendererMap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\tJ\u0013\u0010\u000e\u001a\u0004\u0018\u00010\n2\u0006\u0010\r\u001a\u00020\tH\u0086\u0002J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\nH\u0002J\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0003J2\u0010\u000f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u00142\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00140\u00152\u0006\u0010\u0013\u001a\u00020\u00032\u000e\u0010\u0016\u001a\n\u0012\u0004\u0012\u0002H\u0014\u0018\u00010\u0017JN\u0010\u000f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u0014\"\u0004\b\u0001\u0010\u00182\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u0002H\u00180\u00192\u0006\u0010\u0013\u001a\u00020\u00032\u000e\u0010\u0016\u001a\n\u0012\u0004\u0012\u0002H\u0014\u0018\u00010\u00172\u000e\u0010\u001a\u001a\n\u0012\u0004\u0012\u0002H\u0018\u0018\u00010\u0017Jj\u0010\u000f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u0014\"\u0004\b\u0001\u0010\u0018\"\u0004\b\u0002\u0010\u001b2\u0018\u0010\r\u001a\u0014\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u001b0\u001c2\u0006\u0010\u0013\u001a\u00020\u00032\u000e\u0010\u0016\u001a\n\u0012\u0004\u0012\u0002H\u0014\u0018\u00010\u00172\u000e\u0010\u001a\u001a\n\u0012\u0004\u0012\u0002H\u0018\u0018\u00010\u00172\u000e\u0010\u001d\u001a\n\u0012\u0004\u0012\u0002H\u001b\u0018\u00010\u0017J\u0086\u0001\u0010\u000f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u0014\"\u0004\b\u0001\u0010\u0018\"\u0004\b\u0002\u0010\u001b\"\u0004\b\u0003\u0010\u001e2\u001e\u0010\r\u001a\u001a\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002H\u001e0\u001f2\u0006\u0010\u0013\u001a\u00020\u00032\u000e\u0010\u0016\u001a\n\u0012\u0004\u0012\u0002H\u0014\u0018\u00010\u00172\u000e\u0010\u001a\u001a\n\u0012\u0004\u0012\u0002H\u0018\u0018\u00010\u00172\u000e\u0010\u001d\u001a\n\u0012\u0004\u0012\u0002H\u001b\u0018\u00010\u00172\u000e\u0010 \u001a\n\u0012\u0004\u0012\u0002H\u001e\u0018\u00010\u0017J\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020!2\u0006\u0010\u0013\u001a\u00020\u0003J2\u0010\u000f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u00142\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00140\"2\u0006\u0010\u0013\u001a\u00020\u00032\u000e\u0010\u0016\u001a\n\u0012\u0004\u0012\u0002H\u0014\u0018\u00010\u0017JN\u0010\u000f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u0014\"\u0004\b\u0001\u0010\u00182\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u0002H\u00180#2\u0006\u0010\u0013\u001a\u00020\u00032\u000e\u0010\u0016\u001a\n\u0012\u0004\u0012\u0002H\u0014\u0018\u00010\u00172\u000e\u0010\u001a\u001a\n\u0012\u0004\u0012\u0002H\u0018\u0018\u00010\u0017Jj\u0010\u000f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u0014\"\u0004\b\u0001\u0010\u0018\"\u0004\b\u0002\u0010\u001b2\u0018\u0010\r\u001a\u0014\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u001b0$2\u0006\u0010\u0013\u001a\u00020\u00032\u000e\u0010\u0016\u001a\n\u0012\u0004\u0012\u0002H\u0014\u0018\u00010\u00172\u000e\u0010\u001a\u001a\n\u0012\u0004\u0012\u0002H\u0018\u0018\u00010\u00172\u000e\u0010\u001d\u001a\n\u0012\u0004\u0012\u0002H\u001b\u0018\u00010\u0017J\u0086\u0001\u0010\u000f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u0014\"\u0004\b\u0001\u0010\u0018\"\u0004\b\u0002\u0010\u001b\"\u0004\b\u0003\u0010\u001e2\u001e\u0010\r\u001a\u001a\u0012\u0004\u0012\u0002H\u0014\u0012\u0004\u0012\u0002H\u0018\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002H\u001e0%2\u0006\u0010\u0013\u001a\u00020\u00032\u000e\u0010\u0016\u001a\n\u0012\u0004\u0012\u0002H\u0014\u0018\u00010\u00172\u000e\u0010\u001a\u001a\n\u0012\u0004\u0012\u0002H\u0018\u0018\u00010\u00172\u000e\u0010\u001d\u001a\n\u0012\u0004\u0012\u0002H\u001b\u0018\u00010\u00172\u000e\u0010 \u001a\n\u0012\u0004\u0012\u0002H\u001e\u0018\u00010\u0017J\u0018\u0010&\u001a\u00020\u0003*\u0006\u0012\u0002\b\u00030'2\u0006\u0010(\u001a\u00020\u0003H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryToRendererMap;", "", "name", "", "(Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "renderersMap", "", "Lorg/jetbrains/kotlin/diagnostics/AbstractKtDiagnosticFactory;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticRenderer;", "containsKey", "", "factory", "get", "put", "", "renderer", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory0;", CrashHianalyticsData.MESSAGE, "A", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory1;", "rendererA", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "B", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory2;", "rendererB", "C", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory3;", "rendererC", "D", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactory4;", "rendererD", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation0;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation1;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation2;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation3;", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation4;", "warningMessage", "Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticFactoryForDeprecation;", MLAsrCaptureConstants.ASR_ERROR_MESSAGE, "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtDiagnosticFactoryToRendererMap {
    private final String name;
    private final Map<AbstractKtDiagnosticFactory, KtDiagnosticRenderer> renderersMap;

    public KtDiagnosticFactoryToRendererMap(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.name = name2;
        this.renderersMap = new LinkedHashMap();
    }

    public final String getName() {
        return this.name;
    }

    public final KtDiagnosticRenderer get(AbstractKtDiagnosticFactory factory) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        return this.renderersMap.get(factory);
    }

    public final boolean containsKey(AbstractKtDiagnosticFactory factory) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        return this.renderersMap.containsKey(factory);
    }

    public final void put(KtDiagnosticFactory0 factory, String message) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(message, "message");
        put(factory, new SimpleKtDiagnosticRenderer(message));
    }

    public final <A> void put(KtDiagnosticFactory1<A> factory, String message, DiagnosticParameterRenderer<? super A> rendererA) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(message, "message");
        put(factory, new KtDiagnosticWithParameters1Renderer(message, rendererA));
    }

    public final <A, B> void put(KtDiagnosticFactory2<A, B> factory, String message, DiagnosticParameterRenderer<? super A> rendererA, DiagnosticParameterRenderer<? super B> rendererB) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(message, "message");
        put(factory, new KtDiagnosticWithParameters2Renderer(message, rendererA, rendererB));
    }

    public final <A, B, C> void put(KtDiagnosticFactory3<A, B, C> factory, String message, DiagnosticParameterRenderer<? super A> rendererA, DiagnosticParameterRenderer<? super B> rendererB, DiagnosticParameterRenderer<? super C> rendererC) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(message, "message");
        put(factory, new KtDiagnosticWithParameters3Renderer(message, rendererA, rendererB, rendererC));
    }

    public final <A, B, C, D> void put(KtDiagnosticFactory4<A, B, C, D> factory, String message, DiagnosticParameterRenderer<? super A> rendererA, DiagnosticParameterRenderer<? super B> rendererB, DiagnosticParameterRenderer<? super C> rendererC, DiagnosticParameterRenderer<? super D> rendererD) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(message, "message");
        put(factory, new KtDiagnosticWithParameters4Renderer(message, rendererA, rendererB, rendererC, rendererD));
    }

    public final void put(KtDiagnosticFactoryForDeprecation0 factory, String message) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(message, "message");
        put(factory.getErrorFactory(), new SimpleKtDiagnosticRenderer(message));
        put(factory.getWarningFactory(), new SimpleKtDiagnosticRenderer(warningMessage(factory, message)));
    }

    public final <A> void put(KtDiagnosticFactoryForDeprecation1<A> factory, String message, DiagnosticParameterRenderer<? super A> rendererA) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(message, "message");
        put(factory.getErrorFactory(), new KtDiagnosticWithParameters1Renderer(message, rendererA));
        put(factory.getWarningFactory(), new KtDiagnosticWithParameters1Renderer(warningMessage(factory, message), rendererA));
    }

    public final <A, B> void put(KtDiagnosticFactoryForDeprecation2<A, B> factory, String message, DiagnosticParameterRenderer<? super A> rendererA, DiagnosticParameterRenderer<? super B> rendererB) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(message, "message");
        put(factory.getErrorFactory(), new KtDiagnosticWithParameters2Renderer(message, rendererA, rendererB));
        put(factory.getWarningFactory(), new KtDiagnosticWithParameters2Renderer(warningMessage(factory, message), rendererA, rendererB));
    }

    public final <A, B, C> void put(KtDiagnosticFactoryForDeprecation3<A, B, C> factory, String message, DiagnosticParameterRenderer<? super A> rendererA, DiagnosticParameterRenderer<? super B> rendererB, DiagnosticParameterRenderer<? super C> rendererC) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(message, "message");
        put(factory.getErrorFactory(), new KtDiagnosticWithParameters3Renderer(message, rendererA, rendererB, rendererC));
        put(factory.getWarningFactory(), new KtDiagnosticWithParameters3Renderer(warningMessage(factory, message), rendererA, rendererB, rendererC));
    }

    public final <A, B, C, D> void put(KtDiagnosticFactoryForDeprecation4<A, B, C, D> factory, String message, DiagnosticParameterRenderer<? super A> rendererA, DiagnosticParameterRenderer<? super B> rendererB, DiagnosticParameterRenderer<? super C> rendererC, DiagnosticParameterRenderer<? super D> rendererD) {
        Intrinsics.checkNotNullParameter(factory, "factory");
        Intrinsics.checkNotNullParameter(message, "message");
        put(factory.getErrorFactory(), new KtDiagnosticWithParameters4Renderer(message, rendererA, rendererB, rendererC, rendererD));
        put(factory.getWarningFactory(), new KtDiagnosticWithParameters4Renderer(warningMessage(factory, message), rendererA, rendererB, rendererC, rendererD));
    }

    private final void put(AbstractKtDiagnosticFactory factory, KtDiagnosticRenderer renderer) {
        if (this.renderersMap.containsKey(factory)) {
            throw new IllegalStateException("Diagnostic renderer is already initialized for " + factory);
        }
        this.renderersMap.put(factory, renderer);
    }

    private final String warningMessage(KtDiagnosticFactoryForDeprecation<?> ktDiagnosticFactoryForDeprecation, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(". This will become an error");
        LanguageVersion sinceVersion = ktDiagnosticFactoryForDeprecation.getDeprecatingFeature().getSinceVersion();
        if (sinceVersion != null) {
            sb.append(" in Kotlin ");
            sb.append(sinceVersion.getVersionString());
        } else {
            sb.append(" in a future release");
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
