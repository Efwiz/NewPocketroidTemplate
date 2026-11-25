package org.jetbrains.kotlin.diagnostics.rendering;

import androidx.exifinterface.media.ExifInterface;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: CommonRenderers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00120\u00110\u0010\"\u0004\b\u0000\u0010\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0010H\u0007R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/CommonRenderers;", "", "()V", "CLASS_KIND", "Lorg/jetbrains/kotlin/diagnostics/rendering/ContextIndependentParameterRenderer;", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "EMPTY", "NAME", "Lorg/jetbrains/kotlin/name/Name;", "RENDER_POSITION_VARIANCE", "Lorg/jetbrains/kotlin/types/Variance;", "STRING", "", "THROWABLE", "", "commaSeparated", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "", ExifInterface.GPS_DIRECTION_TRUE, "itemRenderer", "frontend.common-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CommonRenderers {
    public static final CommonRenderers INSTANCE = new CommonRenderers();
    public static final ContextIndependentParameterRenderer<Object> EMPTY = DiagnosticParameterRendererKt.Renderer(new Function1<Object, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.CommonRenderers$EMPTY$1
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(Object it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return "";
        }
    });
    public static final ContextIndependentParameterRenderer<String> STRING = DiagnosticParameterRendererKt.Renderer(new Function1<String, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.CommonRenderers$STRING$1
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(String it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return it;
        }
    });
    public static final ContextIndependentParameterRenderer<Name> NAME = DiagnosticParameterRendererKt.Renderer(new Function1<Name, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.CommonRenderers$NAME$1
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(Name it) {
            Intrinsics.checkNotNullParameter(it, "it");
            String strAsString = it.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return strAsString;
        }
    });
    public static final ContextIndependentParameterRenderer<Throwable> THROWABLE = DiagnosticParameterRendererKt.Renderer(new Function1<Throwable, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.CommonRenderers$THROWABLE$1
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(Throwable it) {
            Intrinsics.checkNotNullParameter(it, "it");
            StringWriter stringWriter = new StringWriter();
            it.printStackTrace(new PrintWriter(stringWriter));
            String strFirst = StringUtil.first(stringWriter.toString(), 2048, true);
            Intrinsics.checkNotNullExpressionValue(strFirst, "first(...)");
            return strFirst;
        }
    });
    public static final ContextIndependentParameterRenderer<Variance> RENDER_POSITION_VARIANCE = DiagnosticParameterRendererKt.Renderer(new Function1<Variance, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.CommonRenderers$RENDER_POSITION_VARIANCE$1

        /* JADX INFO: compiled from: CommonRenderers.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[Variance.values().length];
                try {
                    iArr[Variance.INVARIANT.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[Variance.IN_VARIANCE.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[Variance.OUT_VARIANCE.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        @Override // kotlin.jvm.functions.Function1
        public final String invoke(Variance variance) {
            Intrinsics.checkNotNullParameter(variance, "variance");
            int i = WhenMappings.$EnumSwitchMapping$0[variance.ordinal()];
            if (i == 1) {
                return "invariant";
            }
            if (i == 2) {
                return "in";
            }
            if (i == 3) {
                return "out";
            }
            throw new NoWhenBranchMatchedException();
        }
    });
    public static final ContextIndependentParameterRenderer<ClassKind> CLASS_KIND = DiagnosticParameterRendererKt.Renderer(new Function1<ClassKind, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.CommonRenderers$CLASS_KIND$1

        /* JADX INFO: compiled from: CommonRenderers.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[ClassKind.values().length];
                try {
                    iArr[ClassKind.CLASS.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[ClassKind.INTERFACE.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[ClassKind.ENUM_CLASS.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[ClassKind.ENUM_ENTRY.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[ClassKind.ANNOTATION_CLASS.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr[ClassKind.OBJECT.ordinal()] = 6;
                } catch (NoSuchFieldError unused6) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        @Override // kotlin.jvm.functions.Function1
        public final String invoke(ClassKind classKind) {
            Intrinsics.checkNotNullParameter(classKind, "classKind");
            switch (WhenMappings.$EnumSwitchMapping$0[classKind.ordinal()]) {
                case 1:
                    return "class";
                case 2:
                    return PsiKeyword.INTERFACE;
                case 3:
                    return "enum class";
                case 4:
                    return "enum entry";
                case 5:
                    return "annotation class";
                case 6:
                    return "object";
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }
    });

    private CommonRenderers() {
    }

    @JvmStatic
    public static final <T> DiagnosticParameterRenderer<Collection<? extends T>> commaSeparated(final DiagnosticParameterRenderer<? super T> itemRenderer) {
        Intrinsics.checkNotNullParameter(itemRenderer, "itemRenderer");
        return DiagnosticParameterRendererKt.ContextDependentRenderer(new Function2<Collection<? extends T>, RenderingContext, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.CommonRenderers.commaSeparated.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final String invoke(Collection<? extends T> collection, RenderingContext context) {
                Intrinsics.checkNotNullParameter(collection, "collection");
                Intrinsics.checkNotNullParameter(context, "context");
                DiagnosticParameterRenderer<T> diagnosticParameterRenderer = itemRenderer;
                StringBuilder sb = new StringBuilder();
                Iterator<? extends T> it = collection.iterator();
                while (it.hasNext()) {
                    sb.append(diagnosticParameterRenderer.render(it.next(), context));
                    if (it.hasNext()) {
                        sb.append(", ");
                    }
                }
                String string = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        });
    }
}
