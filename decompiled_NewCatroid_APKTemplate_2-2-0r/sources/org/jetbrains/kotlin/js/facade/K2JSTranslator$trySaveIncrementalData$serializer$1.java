package org.jetbrains.kotlin.js.facade;

import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsProgramFragment;

/* JADX INFO: compiled from: K2JSTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class K2JSTranslator$trySaveIncrementalData$serializer$1 extends FunctionReference implements Function2<JsProgramFragment, Set<? extends JsName>, Unit> {
    K2JSTranslator$trySaveIncrementalData$serializer$1(Object obj) {
        super(2, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "validateJsAst(Lorg/jetbrains/kotlin/js/backend/ast/JsProgramFragment;Ljava/util/Set;)V";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "validateJsAst";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(K2JSTranslator.class);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Unit invoke(JsProgramFragment jsProgramFragment, Set<? extends JsName> set) {
        invoke2(jsProgramFragment, set);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(JsProgramFragment p0, Set<? extends JsName> p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        ((K2JSTranslator) this.receiver).validateJsAst(p0, p1);
    }
}
