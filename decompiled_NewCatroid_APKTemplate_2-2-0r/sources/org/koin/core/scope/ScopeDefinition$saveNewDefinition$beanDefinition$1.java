package org.koin.core.scope;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.name.SpecialNames;
import org.koin.core.parameter.DefinitionParameters;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/koin-core.jar:org/koin/core/scope/ScopeDefinition$saveNewDefinition$beanDefinition$1.class
  classes6.dex
 */
/* JADX INFO: compiled from: ScopeDefinition.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\n¢\u0006\u0004\b\u0006\u0010\u0007"}, d2 = {SpecialNames.ANONYMOUS_STRING, ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/koin/core/scope/Scope;", "it", "Lorg/koin/core/parameter/DefinitionParameters;", "invoke", "(Lorg/koin/core/scope/Scope;Lorg/koin/core/parameter/DefinitionParameters;)Ljava/lang/Object;"}, k = 3, mv = {1, 1, 16})
public final class ScopeDefinition$saveNewDefinition$beanDefinition$1<T> extends Lambda implements Function2<Scope, DefinitionParameters, T> {
    final /* synthetic */ Object $instance;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScopeDefinition$saveNewDefinition$beanDefinition$1(Object obj) {
        super(2);
        this.$instance = obj;
    }

    @Override // kotlin.jvm.functions.Function2
    public final T invoke(Scope receiver, DefinitionParameters it) {
        Intrinsics.checkParameterIsNotNull(receiver, "$receiver");
        Intrinsics.checkParameterIsNotNull(it, "it");
        return (T) this.$instance;
    }
}
