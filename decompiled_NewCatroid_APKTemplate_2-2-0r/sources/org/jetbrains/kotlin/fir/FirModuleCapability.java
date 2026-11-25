package org.jetbrains.kotlin.fir;

import kotlin.Metadata;
import kotlin.reflect.KClass;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.Constants;

/* JADX INFO: compiled from: FirModuleCapabilities.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00000\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirModuleCapability;", "", "()V", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "getKey", "()Lkotlin/reflect/KClass;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirModuleCapability {
    public abstract KClass<? extends FirModuleCapability> getKey();
}
