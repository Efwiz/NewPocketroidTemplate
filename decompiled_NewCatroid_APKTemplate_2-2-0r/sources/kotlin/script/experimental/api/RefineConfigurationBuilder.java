package kotlin.script.experimental.api;

import androidx.exifinterface.media.ExifInterface;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptCompilation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0010 \n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\u001c\u0010\u0005\u001a\u0018\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006j\u0002`\nJ$\u0010\u000b\u001a\u00020\u00042\u001c\u0010\u0005\u001a\u0018\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006j\u0002`\nJM\u0010\f\u001a\u00020\u00042\"\u0010\r\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00100\u000f0\u000e\"\n\u0012\u0006\b\u0001\u0012\u00020\u00100\u000f2\u001c\u0010\u0005\u001a\u0018\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006j\u0002`\n¢\u0006\u0002\u0010\u0011J=\u0010\f\u001a\u00020\u00042\u0012\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00120\u000e\"\u00020\u00122\u001c\u0010\u0005\u001a\u0018\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006j\u0002`\n¢\u0006\u0002\u0010\u0013J:\u0010\f\u001a\u00020\u00042\u0014\u0010\r\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00100\u000f0\u00142\u001c\u0010\u0005\u001a\u0018\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006j\u0002`\nJ2\u0010\f\u001a\u00020\u00042\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00120\u00152\u001c\u0010\u0005\u001a\u0018\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006j\u0002`\nJ8\u0010\f\u001a\u00020\u0004\"\n\b\u0000\u0010\u0016\u0018\u0001*\u00020\u00102\u001e\b\b\u0010\u0005\u001a\u0018\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006j\u0002`\nH\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0017"}, d2 = {"Lkotlin/script/experimental/api/RefineConfigurationBuilder;", "Lkotlin/script/experimental/util/PropertiesCollection$Builder;", "()V", "beforeCompiling", "", "handler", "Lkotlin/Function1;", "Lkotlin/script/experimental/api/ScriptConfigurationRefinementContext;", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "Lkotlin/script/experimental/api/RefineScriptCompilationConfigurationHandler;", "beforeParsing", "onAnnotations", "annotations", "", "Lkotlin/reflect/KClass;", "", "([Lkotlin/reflect/KClass;Lkotlin/jvm/functions/Function1;)V", "Lkotlin/script/experimental/api/KotlinType;", "([Lkotlin/script/experimental/api/KotlinType;Lkotlin/jvm/functions/Function1;)V", "", "", ExifInterface.GPS_DIRECTION_TRUE, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RefineConfigurationBuilder extends PropertiesCollection.Builder {
    public RefineConfigurationBuilder() {
        super(null, 1, null);
    }

    public final void beforeParsing(Function1<? super ScriptConfigurationRefinementContext, ? extends ResultWithDiagnostics<? extends ScriptCompilationConfiguration>> handler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        append(ScriptCompilationKt.getRefineConfigurationBeforeParsing(ScriptCompilationConfiguration.INSTANCE), new RefineConfigurationUnconditionallyData(handler));
    }

    public final void onAnnotations(List<KotlinType> annotations, Function1<? super ScriptConfigurationRefinementContext, ? extends ResultWithDiagnostics<? extends ScriptCompilationConfiguration>> handler) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(handler, "handler");
        append(ScriptCompilationKt.getRefineConfigurationOnAnnotations(ScriptCompilationConfiguration.INSTANCE), new RefineConfigurationOnAnnotationsData(annotations, handler));
    }

    public final void onAnnotations(KotlinType[] annotations, Function1<? super ScriptConfigurationRefinementContext, ? extends ResultWithDiagnostics<? extends ScriptCompilationConfiguration>> handler) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(handler, "handler");
        onAnnotations(ArraysKt.asList(annotations), handler);
    }

    public final /* synthetic */ <T extends Annotation> void onAnnotations(Function1<? super ScriptConfigurationRefinementContext, ? extends ResultWithDiagnostics<? extends ScriptCompilationConfiguration>> handler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        onAnnotations(CollectionsKt.listOf(new KotlinType(Reflection.getOrCreateKotlinClass(Annotation.class), false, 2, (DefaultConstructorMarker) null)), handler);
    }

    public final void beforeCompiling(Function1<? super ScriptConfigurationRefinementContext, ? extends ResultWithDiagnostics<? extends ScriptCompilationConfiguration>> handler) {
        Intrinsics.checkNotNullParameter(handler, "handler");
        append(ScriptCompilationKt.getRefineConfigurationBeforeCompiling(ScriptCompilationConfiguration.INSTANCE), new RefineConfigurationUnconditionallyData(handler));
    }

    public final void onAnnotations(KClass<? extends Annotation>[] annotations, Function1<? super ScriptConfigurationRefinementContext, ? extends ResultWithDiagnostics<? extends ScriptCompilationConfiguration>> handler) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(handler, "handler");
        ArrayList arrayList = new ArrayList(annotations.length);
        for (KClass<? extends Annotation> kClass : annotations) {
            arrayList.add(new KotlinType((KClass) kClass, false, 2, (DefaultConstructorMarker) null));
        }
        onAnnotations((List<KotlinType>) arrayList, handler);
    }

    public final void onAnnotations(Iterable<? extends KClass<? extends Annotation>> annotations, Function1<? super ScriptConfigurationRefinementContext, ? extends ResultWithDiagnostics<? extends ScriptCompilationConfiguration>> handler) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(handler, "handler");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(annotations, 10));
        Iterator<? extends KClass<? extends Annotation>> it = annotations.iterator();
        while (it.hasNext()) {
            arrayList.add(new KotlinType((KClass) it.next(), false, 2, (DefaultConstructorMarker) null));
        }
        onAnnotations((List<KotlinType>) arrayList, handler);
    }
}
