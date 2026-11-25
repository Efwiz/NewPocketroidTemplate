package org.jetbrains.kotlin.ir.backend.js.lower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;

/* JADX INFO: compiled from: JsCodeOutliningLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class JsCodeOutlineTransformer$outlineJsCodeIfNeeded$localsUsageCollector$1 extends FunctionReference implements Function1<String, IrValueDeclaration> {
    JsCodeOutlineTransformer$outlineJsCodeIfNeeded$localsUsageCollector$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "findValueDeclarationWithName(Ljava/lang/String;)Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "findValueDeclarationWithName";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(JsCodeOutlineTransformer.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final IrValueDeclaration invoke(String p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return ((JsCodeOutlineTransformer) this.receiver).findValueDeclarationWithName(p0);
    }
}
