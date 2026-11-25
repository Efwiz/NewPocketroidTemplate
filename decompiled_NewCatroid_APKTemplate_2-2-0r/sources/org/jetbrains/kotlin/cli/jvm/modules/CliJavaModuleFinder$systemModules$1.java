package org.jetbrains.kotlin.cli.jvm.modules;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.resolve.jvm.modules.JavaModule;

/* JADX INFO: compiled from: CliJavaModuleFinder.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class CliJavaModuleFinder$systemModules$1 extends FunctionReference implements Function1<VirtualFile, JavaModule.Explicit> {
    CliJavaModuleFinder$systemModules$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "_get_systemModules_$findSystemModule(Lorg/jetbrains/kotlin/cli/jvm/modules/CliJavaModuleFinder;Lcom/intellij/openapi/vfs/VirtualFile;)Lorg/jetbrains/kotlin/resolve/jvm/modules/JavaModule$Explicit;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "findSystemModule";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(Intrinsics.Kotlin.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final JavaModule.Explicit invoke(VirtualFile p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return CliJavaModuleFinder.findSystemModule$default((CliJavaModuleFinder) this.receiver, p0, false, 2, null);
    }
}
