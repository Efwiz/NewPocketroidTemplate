package kotlin.script.experimental.host;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: hostConfiguration.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0004\b\u0016\u0018\u0000 \u000f2\u00020\u0001:\u0002\u000e\u000fB\"\b\u0016\u0012\u0019\b\u0002\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006¢\u0006\u0002\u0010\u0007B6\b\u0016\u0012\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000\t\"\u00020\u0000\u0012\u0019\b\u0002\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006¢\u0006\u0002\u0010\nB,\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00000\f\u0012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006¢\u0006\u0002\u0010\r¨\u0006\u0010"}, d2 = {"Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "Lkotlin/script/experimental/util/PropertiesCollection;", "body", "Lkotlin/Function1;", "Lkotlin/script/experimental/host/ScriptingHostConfiguration$Builder;", "", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function1;)V", "baseConfigurations", "", "([Lkotlin/script/experimental/host/ScriptingHostConfiguration;Lkotlin/jvm/functions/Function1;)V", "baseScriptingConfigurations", "", "(Ljava/lang/Iterable;Lkotlin/jvm/functions/Function1;)V", "Builder", "Companion", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ScriptingHostConfiguration extends PropertiesCollection {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public ScriptingHostConfiguration(Iterable<? extends ScriptingHostConfiguration> baseScriptingConfigurations, Function1<? super Builder, Unit> body) {
        Intrinsics.checkNotNullParameter(baseScriptingConfigurations, "baseScriptingConfigurations");
        Intrinsics.checkNotNullParameter(body, "body");
        Builder builder = new Builder(baseScriptingConfigurations);
        body.invoke(builder);
        super(builder.getData());
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ScriptingHostConfiguration(Function1<? super Builder, Unit> body) {
        this(CollectionsKt.emptyList(), body);
        Intrinsics.checkNotNullParameter(body, "body");
    }

    public /* synthetic */ ScriptingHostConfiguration(AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new Function1<Builder, Unit>() { // from class: kotlin.script.experimental.host.ScriptingHostConfiguration.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Builder builder) {
                Intrinsics.checkNotNullParameter(builder, "$this$null");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }
        } : anonymousClass1);
    }

    public /* synthetic */ ScriptingHostConfiguration(ScriptingHostConfiguration[] scriptingHostConfigurationArr, AnonymousClass2 anonymousClass2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(scriptingHostConfigurationArr, (Function1<? super Builder, Unit>) ((i & 2) != 0 ? new Function1<Builder, Unit>() { // from class: kotlin.script.experimental.host.ScriptingHostConfiguration.2
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Builder builder) {
                Intrinsics.checkNotNullParameter(builder, "$this$null");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Builder builder) {
                invoke2(builder);
                return Unit.INSTANCE;
            }
        } : anonymousClass2));
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ScriptingHostConfiguration(ScriptingHostConfiguration[] baseConfigurations, Function1<? super Builder, Unit> body) {
        this((Iterable<? extends ScriptingHostConfiguration>) ArraysKt.asIterable(baseConfigurations), body);
        Intrinsics.checkNotNullParameter(baseConfigurations, "baseConfigurations");
        Intrinsics.checkNotNullParameter(body, "body");
    }

    /* JADX INFO: compiled from: hostConfiguration.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\b\u0000\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lkotlin/script/experimental/host/ScriptingHostConfiguration$Builder;", "Lkotlin/script/experimental/host/ScriptingHostConfigurationKeys;", "Lkotlin/script/experimental/util/PropertiesCollection$Builder;", "baseScriptingHostConfigurations", "", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "(Ljava/lang/Iterable;)V", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Builder extends PropertiesCollection.Builder implements ScriptingHostConfigurationKeys {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(Iterable<? extends ScriptingHostConfiguration> baseScriptingHostConfigurations) {
            super(baseScriptingHostConfigurations);
            Intrinsics.checkNotNullParameter(baseScriptingHostConfigurations, "baseScriptingHostConfigurations");
        }
    }

    /* JADX INFO: compiled from: hostConfiguration.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lkotlin/script/experimental/host/ScriptingHostConfiguration$Companion;", "Lkotlin/script/experimental/host/ScriptingHostConfigurationKeys;", "()V", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion implements ScriptingHostConfigurationKeys {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
