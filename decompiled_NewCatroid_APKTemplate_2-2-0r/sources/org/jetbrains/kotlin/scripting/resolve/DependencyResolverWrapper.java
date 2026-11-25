package org.jetbrains.kotlin.scripting.resolve;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.script.dependencies.ScriptDependenciesResolver;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: KotlinScriptDefinitionFromAnnotatedTemplate.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003R\u0012\u0010\u0004\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/DependencyResolverWrapper;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/script/dependencies/ScriptDependenciesResolver;", "", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "getDelegate", "()Lkotlin/script/dependencies/ScriptDependenciesResolver;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface DependencyResolverWrapper<T extends ScriptDependenciesResolver> {
    T getDelegate();
}
