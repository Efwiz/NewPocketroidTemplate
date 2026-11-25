package kotlin.script.experimental.jvm;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.script.experimental.util.PropertiesCollection;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: jvmScriptCaching.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"%\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\u0004\u0010\u0005¨\u0006\b"}, d2 = {"compilationCache", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "Lkotlin/script/experimental/jvm/CompiledJvmScriptsCache;", "Lkotlin/script/experimental/jvm/JvmScriptingHostConfigurationKeys;", "getCompilationCache", "(Lkotlin/script/experimental/jvm/JvmScriptingHostConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "compilationCache$delegate", "Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyDelegate;", PathUtil.KOTLIN_SCRIPTING_JVM_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JvmScriptCachingKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(JvmScriptCachingKt.class, PathUtil.KOTLIN_SCRIPTING_JVM_NAME), "compilationCache", "getCompilationCache(Lkotlin/script/experimental/jvm/JvmScriptingHostConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;"))};
    private static final PropertiesCollection.PropertyKeyDelegate compilationCache$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, true, 1, (Object) null);

    public static final PropertiesCollection.Key<CompiledJvmScriptsCache> getCompilationCache(JvmScriptingHostConfigurationKeys jvmScriptingHostConfigurationKeys) {
        Intrinsics.checkNotNullParameter(jvmScriptingHostConfigurationKeys, "<this>");
        return compilationCache$delegate.getValue(jvmScriptingHostConfigurationKeys, $$delegatedProperties[0]);
    }
}
