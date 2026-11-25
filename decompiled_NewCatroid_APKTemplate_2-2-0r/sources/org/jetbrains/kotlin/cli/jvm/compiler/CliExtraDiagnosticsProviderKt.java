package org.jetbrains.kotlin.cli.jvm.compiler;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclarationKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValue;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;

/* JADX INFO: compiled from: CliExtraDiagnosticsProvider.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\u0007\u001a\u00020\bH\u0002\"-\u0010\u0000\u001a!\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00020\u0003 \u0004*\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00020\u00020\u0001¢\u0006\u0002\b\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"JAVA_API_STUB", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Key;", "Lorg/jetbrains/kotlin/com/intellij/psi/util/CachedValue;", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/annotations/NotNull;", "computeLightClassCachedValue", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getLightClassCachedValue", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CliExtraDiagnosticsProviderKt {
    private static final Key<CachedValue<Diagnostics>> JAVA_API_STUB;

    static {
        Key<CachedValue<Diagnostics>> keyCreate = Key.create("JAVA_API_STUB");
        Intrinsics.checkNotNullExpressionValue(keyCreate, "create(...)");
        JAVA_API_STUB = keyCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CachedValue<Diagnostics> getLightClassCachedValue(KtClassOrObject ktClassOrObject) {
        CachedValue<Diagnostics> cachedValue = (CachedValue) KtLightClassForSourceDeclarationKt.getOutermostClassOrObject(ktClassOrObject).getUserData(JAVA_API_STUB);
        return cachedValue != null ? cachedValue : computeLightClassCachedValue(ktClassOrObject);
    }

    private static final CachedValue<Diagnostics> computeLightClassCachedValue(KtClassOrObject ktClassOrObject) {
        Key<CachedValue<Diagnostics>> key = JAVA_API_STUB;
        CachedValue<Diagnostics> cachedValue = (CachedValue) ktClassOrObject.getUserData(key);
        if (cachedValue == null) {
            CachedValue cachedValueCreateCachedValue = CachedValuesManager.getManager(ktClassOrObject.getProject()).createCachedValue(new LightClassDataProviderForClassOrObject(ktClassOrObject));
            Intrinsics.checkNotNullExpressionValue(cachedValueCreateCachedValue, "createCachedValue(...)");
            cachedValue = (CachedValue) ktClassOrObject.putUserDataIfAbsent(key, cachedValueCreateCachedValue);
        }
        Intrinsics.checkNotNull(cachedValue);
        return cachedValue;
    }
}
