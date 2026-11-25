package org.koin.ext;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KMutableProperty0;
import org.koin.core.Koin;
import org.koin.core.context.KoinContextHandler;
import org.koin.core.parameter.DefinitionParameters;
import org.koin.core.qualifier.Qualifier;
import org.koin.core.scope.Scope;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/koin-core.jar:org/koin/ext/InjectPropertyKt.class
  classes6.dex
 */
/* JADX INFO: compiled from: InjectProperty.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001b\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0086\b\u001a#\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0086\b\u001a#\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u0086\b¨\u0006\b"}, d2 = {"inject", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/reflect/KMutableProperty0;", "koin", "Lorg/koin/core/Koin;", "scope", "Lorg/koin/core/scope/Scope;", "koin-core"}, k = 2, mv = {1, 1, 16})
public final class InjectPropertyKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final /* synthetic */ <T> void inject(KMutableProperty0<T> inject) {
        Intrinsics.checkParameterIsNotNull(inject, "$this$inject");
        Scope rootScope = KoinContextHandler.INSTANCE.get().get_scopeRegistry().getRootScope();
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        inject.set(rootScope.get(Reflection.getOrCreateKotlinClass(Object.class), (Qualifier) null, (Function0<DefinitionParameters>) null));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final /* synthetic */ <T> void inject(KMutableProperty0<T> inject, Koin koin) {
        Intrinsics.checkParameterIsNotNull(inject, "$this$inject");
        Intrinsics.checkParameterIsNotNull(koin, "koin");
        Scope rootScope = koin.get_scopeRegistry().getRootScope();
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        inject.set(rootScope.get(Reflection.getOrCreateKotlinClass(Object.class), (Qualifier) null, (Function0<DefinitionParameters>) null));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final /* synthetic */ <T> void inject(KMutableProperty0<T> inject, Scope scope) {
        Intrinsics.checkParameterIsNotNull(inject, "$this$inject");
        Intrinsics.checkParameterIsNotNull(scope, "scope");
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        inject.set(scope.get(Reflection.getOrCreateKotlinClass(Object.class), (Qualifier) null, (Function0<DefinitionParameters>) null));
    }
}
