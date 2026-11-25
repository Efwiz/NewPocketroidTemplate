package org.jetbrains.kotlin.js.resolve.diagnostics;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JsReflectionAPICallChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0003"}, d2 = {"ADDITIONAL_ALLOWED_CLASSES", "", "Lorg/jetbrains/kotlin/name/FqName;", "js.frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JsReflectionAPICallCheckerKt {
    private static final Set<FqName> ADDITIONAL_ALLOWED_CLASSES = SetsKt.setOf((Object[]) new FqName[]{new FqName("kotlin.reflect.AssociatedObjectKey"), new FqName("kotlin.reflect.ExperimentalAssociatedObjects")});
}
