package kotlin.script.experimental.jvmhost;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptEvaluationConfiguration;
import kotlin.script.experimental.host.ConfigurationFromTemplateKt;
import kotlin.script.experimental.host.HostConfigurationKt;
import kotlin.script.experimental.host.ScriptDefinition;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.jvm.JvmScriptingHostConfigurationKt;

/* JADX INFO: compiled from: BasicJvmScriptingHost.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a?\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0019\b\n\u0010\u0006\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\nH\u0086\bø\u0001\u0000\u001a?\u0010\u000b\u001a\u00020\f\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0019\b\n\u0010\u0006\u001a\u0013\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\nH\u0086\bø\u0001\u0000\u001aZ\u0010\u000e\u001a\u00020\u000f\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0019\b\n\u0010\u0010\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\n2\u0019\b\n\u0010\u0011\u001a\u0013\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\nH\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0012"}, d2 = {"createJvmCompilationConfigurationFromTemplate", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", ExifInterface.GPS_DIRECTION_TRUE, "", "baseHostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "body", "Lkotlin/Function1;", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration$Builder;", "", "Lkotlin/ExtensionFunctionType;", "createJvmEvaluationConfigurationFromTemplate", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration;", "Lkotlin/script/experimental/api/ScriptEvaluationConfiguration$Builder;", "createJvmScriptDefinitionFromTemplate", "Lkotlin/script/experimental/host/ScriptDefinition;", "compilation", "evaluation", "kotlin-scripting-jvm-host-unshaded"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BasicJvmScriptingHostKt {
    public static /* synthetic */ ScriptCompilationConfiguration createJvmCompilationConfigurationFromTemplate$default(ScriptingHostConfiguration scriptingHostConfiguration, Function1 body, int i, Object obj) {
        if ((i & 1) != 0) {
            scriptingHostConfiguration = null;
        }
        if ((i & 2) != 0) {
            body = new Function1<ScriptCompilationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvmhost.BasicJvmScriptingHostKt.createJvmCompilationConfigurationFromTemplate.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ScriptCompilationConfiguration.Builder builder) {
                    Intrinsics.checkNotNullParameter(builder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ScriptCompilationConfiguration.Builder builder) {
                    invoke2(builder);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return ConfigurationFromTemplateKt.createCompilationConfigurationFromTemplate(new KotlinType(Reflection.getOrCreateKotlinClass(Object.class), false, 2, (DefaultConstructorMarker) null), HostConfigurationKt.withDefaultsFrom(scriptingHostConfiguration, JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration()), Reflection.getOrCreateKotlinClass(ScriptCompilationConfiguration.class), body);
    }

    public static final /* synthetic */ <T> ScriptCompilationConfiguration createJvmCompilationConfigurationFromTemplate(ScriptingHostConfiguration scriptingHostConfiguration, Function1<? super ScriptCompilationConfiguration.Builder, Unit> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return ConfigurationFromTemplateKt.createCompilationConfigurationFromTemplate(new KotlinType(Reflection.getOrCreateKotlinClass(Object.class), false, 2, (DefaultConstructorMarker) null), HostConfigurationKt.withDefaultsFrom(scriptingHostConfiguration, JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration()), Reflection.getOrCreateKotlinClass(ScriptCompilationConfiguration.class), body);
    }

    public static /* synthetic */ ScriptEvaluationConfiguration createJvmEvaluationConfigurationFromTemplate$default(ScriptingHostConfiguration scriptingHostConfiguration, Function1 body, int i, Object obj) {
        if ((i & 1) != 0) {
            scriptingHostConfiguration = null;
        }
        if ((i & 2) != 0) {
            body = new Function1<ScriptEvaluationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvmhost.BasicJvmScriptingHostKt.createJvmEvaluationConfigurationFromTemplate.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ScriptEvaluationConfiguration.Builder builder) {
                    Intrinsics.checkNotNullParameter(builder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ScriptEvaluationConfiguration.Builder builder) {
                    invoke2(builder);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return ConfigurationFromTemplateKt.createEvaluationConfigurationFromTemplate(new KotlinType(Reflection.getOrCreateKotlinClass(Object.class), false, 2, (DefaultConstructorMarker) null), HostConfigurationKt.withDefaultsFrom(scriptingHostConfiguration, JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration()), Reflection.getOrCreateKotlinClass(ScriptEvaluationConfiguration.class), body);
    }

    public static final /* synthetic */ <T> ScriptEvaluationConfiguration createJvmEvaluationConfigurationFromTemplate(ScriptingHostConfiguration scriptingHostConfiguration, Function1<? super ScriptEvaluationConfiguration.Builder, Unit> body) {
        Intrinsics.checkNotNullParameter(body, "body");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return ConfigurationFromTemplateKt.createEvaluationConfigurationFromTemplate(new KotlinType(Reflection.getOrCreateKotlinClass(Object.class), false, 2, (DefaultConstructorMarker) null), HostConfigurationKt.withDefaultsFrom(scriptingHostConfiguration, JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration()), Reflection.getOrCreateKotlinClass(ScriptEvaluationConfiguration.class), body);
    }

    public static /* synthetic */ ScriptDefinition createJvmScriptDefinitionFromTemplate$default(ScriptingHostConfiguration scriptingHostConfiguration, Function1 compilation, Function1 evaluation, int i, Object obj) {
        if ((i & 1) != 0) {
            scriptingHostConfiguration = null;
        }
        if ((i & 2) != 0) {
            compilation = new Function1<ScriptCompilationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvmhost.BasicJvmScriptingHostKt.createJvmScriptDefinitionFromTemplate.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ScriptCompilationConfiguration.Builder builder) {
                    Intrinsics.checkNotNullParameter(builder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ScriptCompilationConfiguration.Builder builder) {
                    invoke2(builder);
                    return Unit.INSTANCE;
                }
            };
        }
        if ((i & 4) != 0) {
            evaluation = new Function1<ScriptEvaluationConfiguration.Builder, Unit>() { // from class: kotlin.script.experimental.jvmhost.BasicJvmScriptingHostKt.createJvmScriptDefinitionFromTemplate.2
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(ScriptEvaluationConfiguration.Builder builder) {
                    Intrinsics.checkNotNullParameter(builder, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(ScriptEvaluationConfiguration.Builder builder) {
                    invoke2(builder);
                    return Unit.INSTANCE;
                }
            };
        }
        Intrinsics.checkNotNullParameter(compilation, "compilation");
        Intrinsics.checkNotNullParameter(evaluation, "evaluation");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return ConfigurationFromTemplateKt.createScriptDefinitionFromTemplate(new KotlinType(Reflection.getOrCreateKotlinClass(Object.class), false, 2, (DefaultConstructorMarker) null), HostConfigurationKt.withDefaultsFrom(scriptingHostConfiguration, JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration()), Reflection.getOrCreateKotlinClass(ScriptCompilationConfiguration.class), compilation, evaluation);
    }

    public static final /* synthetic */ <T> ScriptDefinition createJvmScriptDefinitionFromTemplate(ScriptingHostConfiguration scriptingHostConfiguration, Function1<? super ScriptCompilationConfiguration.Builder, Unit> compilation, Function1<? super ScriptEvaluationConfiguration.Builder, Unit> evaluation) {
        Intrinsics.checkNotNullParameter(compilation, "compilation");
        Intrinsics.checkNotNullParameter(evaluation, "evaluation");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        return ConfigurationFromTemplateKt.createScriptDefinitionFromTemplate(new KotlinType(Reflection.getOrCreateKotlinClass(Object.class), false, 2, (DefaultConstructorMarker) null), HostConfigurationKt.withDefaultsFrom(scriptingHostConfiguration, JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration()), Reflection.getOrCreateKotlinClass(ScriptCompilationConfiguration.class), compilation, evaluation);
    }
}
