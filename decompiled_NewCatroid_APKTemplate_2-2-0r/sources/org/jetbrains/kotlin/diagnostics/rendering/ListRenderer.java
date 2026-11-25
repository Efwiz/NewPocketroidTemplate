package org.jetbrains.kotlin.diagnostics.rendering;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PlatformIncompatibilityDiagnosticRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002B)\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0014\b\u0002\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u001e\u0010\t\u001a\u00020\u00072\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/ListRenderer;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "", "elementRenderer", "elemProcessor", "Lkotlin/Function1;", "", "(Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;Lkotlin/jvm/functions/Function1;)V", "render", "obj", "renderingContext", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ListRenderer<T> implements DiagnosticParameterRenderer<List<? extends T>> {
    private final Function1<String, String> elemProcessor;
    private final DiagnosticParameterRenderer<T> elementRenderer;

    /* JADX WARN: Multi-variable type inference failed */
    public ListRenderer(DiagnosticParameterRenderer<? super T> elementRenderer, Function1<? super String, String> elemProcessor) {
        Intrinsics.checkNotNullParameter(elementRenderer, "elementRenderer");
        Intrinsics.checkNotNullParameter(elemProcessor, "elemProcessor");
        this.elementRenderer = elementRenderer;
        this.elemProcessor = elemProcessor;
    }

    public /* synthetic */ ListRenderer(DiagnosticParameterRenderer diagnosticParameterRenderer, AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(diagnosticParameterRenderer, (i & 2) != 0 ? new Function1<String, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.ListRenderer.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it;
            }
        } : anonymousClass1);
    }

    @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
    public String render(List<? extends T> obj, final RenderingContext renderingContext) {
        Intrinsics.checkNotNullParameter(obj, "obj");
        Intrinsics.checkNotNullParameter(renderingContext, "renderingContext");
        return CollectionsKt.joinToString$default(obj, null, null, null, 0, null, new Function1<T, CharSequence>(this) { // from class: org.jetbrains.kotlin.diagnostics.rendering.ListRenderer.render.1
            final /* synthetic */ ListRenderer<T> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.this$0 = this;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(T t) {
                return (CharSequence) ((ListRenderer) this.this$0).elemProcessor.invoke(((ListRenderer) this.this$0).elementRenderer.render(t, renderingContext));
            }
        }, 31, null);
    }
}
