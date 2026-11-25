package org.jetbrains.kotlin.cli.jvm.modules;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModule;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModuleFinder;

/* JADX INFO: compiled from: JavaModuleGraph.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class JavaModuleGraph$module$1 extends FunctionReference implements Function1<String, JavaModule> {
    JavaModuleGraph$module$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "findModule(Ljava/lang/String;)Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "findModule";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(JavaModuleFinder.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final JavaModule invoke(String p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return ((JavaModuleFinder) this.receiver).findModule(p0);
    }
}
