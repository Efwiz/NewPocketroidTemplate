package org.jetbrains.kotlin.fir.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.fir.FirModuleCapability;

/* JADX INFO: compiled from: ImplicitIntegerCoercion.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/ImplicitIntegerCoercionModuleCapability;", "Lorg/jetbrains/kotlin/fir/FirModuleCapability;", "()V", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "getKey", "()Lkotlin/reflect/KClass;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImplicitIntegerCoercionModuleCapability extends FirModuleCapability {
    public static final ImplicitIntegerCoercionModuleCapability INSTANCE = new ImplicitIntegerCoercionModuleCapability();
    private static final KClass<? extends FirModuleCapability> key = Reflection.getOrCreateKotlinClass(ImplicitIntegerCoercionModuleCapability.class);

    private ImplicitIntegerCoercionModuleCapability() {
    }

    @Override // org.jetbrains.kotlin.fir.FirModuleCapability
    public KClass<? extends FirModuleCapability> getKey() {
        return key;
    }
}
