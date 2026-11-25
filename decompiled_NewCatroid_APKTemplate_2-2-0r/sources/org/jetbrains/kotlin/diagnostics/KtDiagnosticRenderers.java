package org.jetbrains.kotlin.diagnostics;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.diagnostics.rendering.ContextIndependentParameterRenderer;
import org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRendererKt;

/* JADX INFO: compiled from: KtDiagnosticRenderers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u00170\u0004\"\u0004\b\u0000\u0010\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u0002H\u00180\u0004R\u0019\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0006R\u0019\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0006R\u0019\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0019\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0019\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0006¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/KtDiagnosticRenderers;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/diagnostics/rendering/ContextIndependentParameterRenderer;", "getEMPTY", "()Lorg/jetbrains/kotlin/diagnostics/rendering/ContextIndependentParameterRenderer;", "FUNCTION_PARAMETERS", "", "getFUNCTION_PARAMETERS", "NOT_RENDERED", "getNOT_RENDERED", "NULLABLE_STRING", "", "getNULLABLE_STRING", "OPTIONAL_COLON_TO_STRING", "getOPTIONAL_COLON_TO_STRING", "TO_STRING", "getTO_STRING", "VISIBILITY", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVISIBILITY", "COLLECTION", "", ExifInterface.GPS_DIRECTION_TRUE, "renderer", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtDiagnosticRenderers {
    public static final KtDiagnosticRenderers INSTANCE = new KtDiagnosticRenderers();
    private static final ContextIndependentParameterRenderer<String> NULLABLE_STRING = DiagnosticParameterRendererKt.Renderer(new Function1<String, String>() { // from class: org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderers$NULLABLE_STRING$1
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(String str) {
            return str == null ? "null" : str;
        }
    });
    private static final ContextIndependentParameterRenderer<Object> TO_STRING = DiagnosticParameterRendererKt.Renderer(new Function1<Object, String>() { // from class: org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderers$TO_STRING$1
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(Object obj) {
            return String.valueOf(obj);
        }
    });
    private static final ContextIndependentParameterRenderer<Object> OPTIONAL_COLON_TO_STRING = DiagnosticParameterRendererKt.Renderer(new Function1<Object, String>() { // from class: org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderers$OPTIONAL_COLON_TO_STRING$1
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(Object obj) {
            String strValueOf = String.valueOf(obj);
            return strValueOf.length() > 0 ? ": " + strValueOf : "";
        }
    });
    private static final ContextIndependentParameterRenderer<Object> EMPTY = DiagnosticParameterRendererKt.Renderer(new Function1<Object, String>() { // from class: org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderers$EMPTY$1
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(Object obj) {
            return "";
        }
    });
    private static final ContextIndependentParameterRenderer<Visibility> VISIBILITY = DiagnosticParameterRendererKt.Renderer(new Function1<Visibility, String>() { // from class: org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderers$VISIBILITY$1
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(Visibility visibility) {
            Intrinsics.checkNotNullParameter(visibility, "visibility");
            return visibility.getExternalDisplayName();
        }
    });
    private static final ContextIndependentParameterRenderer<Object> NOT_RENDERED = DiagnosticParameterRendererKt.Renderer(new Function1<Object, String>() { // from class: org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderers$NOT_RENDERED$1
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(Object obj) {
            return "";
        }
    });
    private static final ContextIndependentParameterRenderer<Boolean> FUNCTION_PARAMETERS = DiagnosticParameterRendererKt.Renderer(new Function1<Boolean, String>() { // from class: org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderers$FUNCTION_PARAMETERS$1
        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ String invoke(Boolean bool) {
            return invoke(bool.booleanValue());
        }

        public final String invoke(boolean z) {
            return z ? "..." : "";
        }
    });

    private KtDiagnosticRenderers() {
    }

    public final ContextIndependentParameterRenderer<String> getNULLABLE_STRING() {
        return NULLABLE_STRING;
    }

    public final ContextIndependentParameterRenderer<Object> getTO_STRING() {
        return TO_STRING;
    }

    public final ContextIndependentParameterRenderer<Object> getOPTIONAL_COLON_TO_STRING() {
        return OPTIONAL_COLON_TO_STRING;
    }

    public final ContextIndependentParameterRenderer<Object> getEMPTY() {
        return EMPTY;
    }

    public final ContextIndependentParameterRenderer<Visibility> getVISIBILITY() {
        return VISIBILITY;
    }

    public final ContextIndependentParameterRenderer<Object> getNOT_RENDERED() {
        return NOT_RENDERED;
    }

    public final ContextIndependentParameterRenderer<Boolean> getFUNCTION_PARAMETERS() {
        return FUNCTION_PARAMETERS;
    }

    public final <T> ContextIndependentParameterRenderer<Collection<? extends T>> COLLECTION(final ContextIndependentParameterRenderer<? super T> renderer) {
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        return DiagnosticParameterRendererKt.Renderer(new Function1<Collection<? extends T>, String>() { // from class: org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderers.COLLECTION.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final String invoke(Collection<? extends T> list) {
                Intrinsics.checkNotNullParameter(list, "list");
                final ContextIndependentParameterRenderer<T> contextIndependentParameterRenderer = renderer;
                return CollectionsKt.joinToString(list, ", ", "[", "]", 3, "...", new Function1<T, CharSequence>() { // from class: org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderers.COLLECTION.1.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(1);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function1
                    public final CharSequence invoke(T t) {
                        return contextIndependentParameterRenderer.render(t);
                    }
                });
            }
        });
    }
}
