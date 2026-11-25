package kotlin.script.experimental.api;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptData.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u001c\n\u0002\b\u0004\u0018\u0000 \u000e2\u00020\u0001:\u0002\r\u000eB\"\b\u0016\u0012\u0019\b\u0002\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006¢\u0006\u0002\u0010\u0007B6\b\u0016\u0012\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000\t\"\u00020\u0000\u0012\u0019\b\u0002\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006¢\u0006\u0002\u0010\nB.\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00000\u000b\u0012\u0019\b\u0002\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006¢\u0006\u0002\u0010\f¨\u0006\u000f"}, d2 = {"Lkotlin/script/experimental/api/ScriptEvaluationContextData;", "Lkotlin/script/experimental/util/PropertiesCollection;", "body", "Lkotlin/Function1;", "Lkotlin/script/experimental/api/ScriptEvaluationContextData$Builder;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function1;)V", "baseConfigurations", "", "([Lkotlin/script/experimental/api/ScriptEvaluationContextData;Lkotlin/jvm/functions/Function1;)V", "", "(Ljava/lang/Iterable;Lkotlin/jvm/functions/Function1;)V", "Builder", "Companion", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ScriptEvaluationContextData extends PropertiesCollection {
    public /* synthetic */ ScriptEvaluationContextData(Iterable iterable, AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((Iterable<ScriptEvaluationContextData>) iterable, (Function1<? super Builder, Unit>) ((i & 2) != 0 ? new Function1<Builder, Unit>() { // from class: kotlin.script.experimental.api.ScriptEvaluationContextData.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Builder builder) {
                Intrinsics.checkNotNullParameter(builder, "$this$null");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }
        } : anonymousClass1));
    }

    public ScriptEvaluationContextData(Iterable<ScriptEvaluationContextData> baseConfigurations, Function1<? super Builder, Unit> body) {
        Intrinsics.checkNotNullParameter(baseConfigurations, "baseConfigurations");
        Intrinsics.checkNotNullParameter(body, "body");
        Builder builder = new Builder(baseConfigurations);
        body.invoke(builder);
        super(builder.getData());
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ScriptEvaluationContextData(Function1<? super Builder, Unit> body) {
        this(CollectionsKt.emptyList(), body);
        Intrinsics.checkNotNullParameter(body, "body");
    }

    public /* synthetic */ ScriptEvaluationContextData(AnonymousClass2 anonymousClass2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new Function1<Builder, Unit>() { // from class: kotlin.script.experimental.api.ScriptEvaluationContextData.2
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Builder builder) {
                Intrinsics.checkNotNullParameter(builder, "$this$null");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }
        } : anonymousClass2);
    }

    public /* synthetic */ ScriptEvaluationContextData(ScriptEvaluationContextData[] scriptEvaluationContextDataArr, AnonymousClass3 anonymousClass3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(scriptEvaluationContextDataArr, (Function1<? super Builder, Unit>) ((i & 2) != 0 ? new Function1<Builder, Unit>() { // from class: kotlin.script.experimental.api.ScriptEvaluationContextData.3
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Builder builder) {
                Intrinsics.checkNotNullParameter(builder, "$this$null");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }
        } : anonymousClass3));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ScriptEvaluationContextData(ScriptEvaluationContextData[] baseConfigurations, Function1<? super Builder, Unit> body) {
        this((Iterable<ScriptEvaluationContextData>) ArraysKt.asIterable(baseConfigurations), body);
        Intrinsics.checkNotNullParameter(baseConfigurations, "baseConfigurations");
        Intrinsics.checkNotNullParameter(body, "body");
    }

    /* JADX INFO: compiled from: scriptData.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\b\u0000\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lkotlin/script/experimental/api/ScriptEvaluationContextData$Builder;", "Lkotlin/script/experimental/api/ScriptEvaluationContextDataKeys;", "Lkotlin/script/experimental/util/PropertiesCollection$Builder;", "baseConfigurations", "", "Lkotlin/script/experimental/api/ScriptEvaluationContextData;", "(Ljava/lang/Iterable;)V", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Builder extends PropertiesCollection.Builder implements ScriptEvaluationContextDataKeys {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(Iterable<ScriptEvaluationContextData> baseConfigurations) {
            super(baseConfigurations);
            Intrinsics.checkNotNullParameter(baseConfigurations, "baseConfigurations");
        }
    }
}
