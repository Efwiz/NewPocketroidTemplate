package org.jetbrains.kotlin.cli.jvm.compiler.builder;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;

/* JADX INFO: compiled from: KotlinLightClassBuilderFactory.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\"\u0013\u0010\u0000\u001a\u00070\u0001¢\u0006\u0002\b\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0003"}, d2 = {"LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "cli-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinLightClassBuilderFactoryKt {
    private static final Logger LOG;

    static {
        Logger logger = Logger.getInstance((Class<?>) KotlinLightClassBuilderFactory.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
    }
}
