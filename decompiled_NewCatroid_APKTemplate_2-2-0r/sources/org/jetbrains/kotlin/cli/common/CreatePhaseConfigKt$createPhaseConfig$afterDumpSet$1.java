package org.jetbrains.kotlin.cli.common;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;

/* JADX INFO: compiled from: createPhaseConfig.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class CreatePhaseConfigKt$createPhaseConfig$afterDumpSet$1 extends FunctionReference implements Function1<String, Unit> {
    final /* synthetic */ MessageCollector $messageCollector;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    CreatePhaseConfigKt$createPhaseConfig$afterDumpSet$1(MessageCollector messageCollector) {
        super(1);
        this.$messageCollector = messageCollector;
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "createPhaseConfig$report(Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;Ljava/lang/String;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "report";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(String str) {
        invoke2(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(String p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        CreatePhaseConfigKt.createPhaseConfig$report(this.$messageCollector, p0);
    }
}
