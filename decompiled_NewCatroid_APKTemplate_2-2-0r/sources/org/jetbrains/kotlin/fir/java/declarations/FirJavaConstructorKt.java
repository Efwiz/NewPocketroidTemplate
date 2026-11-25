package org.jetbrains.kotlin.fir.java.declarations;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.eclipse.jgit.lib.ConfigConstants;

/* JADX INFO: compiled from: FirJavaConstructor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a%\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0007"}, d2 = {"buildJavaConstructor", "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaConstructor;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/java/declarations/FirJavaConstructorBuilder;", "", "Lkotlin/ExtensionFunctionType;", SuffixConstants.EXTENSION_java}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirJavaConstructorKt {
    public static final FirJavaConstructor buildJavaConstructor(Function1<? super FirJavaConstructorBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirJavaConstructorBuilder firJavaConstructorBuilder = new FirJavaConstructorBuilder();
        init.invoke(firJavaConstructorBuilder);
        return firJavaConstructorBuilder.build();
    }
}
