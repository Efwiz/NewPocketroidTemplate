package org.jetbrains.kotlin.fir.declarations;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.util.ArrayMap;
import org.jetbrains.kotlin.util.AttributeArrayOwner;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: FirDeclarationAttributes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0016¢\u0006\u0002\u0010\u0004B\u0015\b\u0012\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010\f\u001a\u00020\u0000J(\u0010\r\u001a\u00020\u000e2\u000e\u0010\u000f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003H\u0080\u0002¢\u0006\u0002\b\u0012R \u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\t8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "Lorg/jetbrains/kotlin/util/AttributeArrayOwner;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataKey;", "", "()V", "arrayMap", "Lorg/jetbrains/kotlin/util/ArrayMap;", "(Lorg/jetbrains/kotlin/util/ArrayMap;)V", "typeRegistry", "Lorg/jetbrains/kotlin/util/TypeRegistry;", "getTypeRegistry", "()Lorg/jetbrains/kotlin/util/TypeRegistry;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "value", "set$tree", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDeclarationAttributes extends AttributeArrayOwner<FirDeclarationDataKey, Object> {
    @Override // org.jetbrains.kotlin.util.AbstractArrayMapOwner
    protected TypeRegistry<FirDeclarationDataKey, Object> getTypeRegistry() {
        return FirDeclarationDataRegistry.INSTANCE;
    }

    public FirDeclarationAttributes() {
    }

    private FirDeclarationAttributes(ArrayMap<Object> arrayMap) {
        super(arrayMap);
    }

    public final void set$tree(KClass<? extends FirDeclarationDataKey> key, Object value) {
        Intrinsics.checkNotNullParameter(key, "key");
        if (value == null) {
            removeComponent(key);
        } else {
            registerComponent(key, value);
        }
    }

    public final FirDeclarationAttributes copy() {
        return new FirDeclarationAttributes(getArrayMap().copy());
    }
}
