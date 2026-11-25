package org.jetbrains.kotlin.backend.common;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;

/* JADX INFO: compiled from: Mappings.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000'\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\b\u0003*\u0001\f\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060\u0004\"\b\b\u0000\u0010\u0005*\u00020\u0007\"\u000e\b\u0001\u0010\u0006*\b\u0012\u0004\u0012\u00020\u00070\bH\u0016J(\u0010\t\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060\u0004\"\b\b\u0000\u0010\u0005*\u00020\u0007\"\b\b\u0001\u0010\u0006*\u00020\u0007H\u0016J\"\u0010\n\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060\u0004\"\b\b\u0000\u0010\u0005*\u00020\u0007\"\u0004\b\u0001\u0010\u0006J)\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u0002H\u00060\f\"\b\b\u0000\u0010\u0005*\u00020\u0007\"\u0004\b\u0001\u0010\u0006H\u0002¢\u0006\u0002\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/DefaultDelegateFactory;", "Lorg/jetbrains/kotlin/backend/common/DelegateFactory;", "()V", "newDeclarationToDeclarationCollectionMapping", "Lorg/jetbrains/kotlin/backend/common/Mapping$Delegate;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "", "newDeclarationToDeclarationMapping", "newDeclarationToValueMapping", "newMappingImpl", "org/jetbrains/kotlin/backend/common/DefaultDelegateFactory$newMappingImpl$1", "()Lorg/jetbrains/kotlin/backend/common/DefaultDelegateFactory$newMappingImpl$1;", "ir.backend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DefaultDelegateFactory implements DelegateFactory {
    public static final DefaultDelegateFactory INSTANCE = new DefaultDelegateFactory();

    private DefaultDelegateFactory() {
    }

    public final <K extends IrDeclaration, V> Mapping.Delegate<K, V> newDeclarationToValueMapping() {
        return newMappingImpl();
    }

    @Override // org.jetbrains.kotlin.backend.common.DelegateFactory
    public <K extends IrDeclaration, V extends IrDeclaration> Mapping.Delegate<K, V> newDeclarationToDeclarationMapping() {
        return newMappingImpl();
    }

    @Override // org.jetbrains.kotlin.backend.common.DelegateFactory
    public <K extends IrDeclaration, V extends Collection<? extends IrDeclaration>> Mapping.Delegate<K, V> newDeclarationToDeclarationCollectionMapping() {
        return newMappingImpl();
    }

    private final <K extends IrDeclaration, V> AnonymousClass1<K, V> newMappingImpl() {
        return (AnonymousClass1<K, V>) new Mapping.Delegate<K, V>() { // from class: org.jetbrains.kotlin.backend.common.DefaultDelegateFactory.newMappingImpl.1
            private final Map<K, V> map = new ConcurrentHashMap();

            /* JADX WARN: Incorrect types in method signature: (TK;)TV; */
            @Override // org.jetbrains.kotlin.backend.common.Mapping.Delegate
            public Object get(IrDeclaration key) {
                Intrinsics.checkNotNullParameter(key, "key");
                return this.map.get(key);
            }

            /* JADX WARN: Incorrect types in method signature: (TK;TV;)V */
            @Override // org.jetbrains.kotlin.backend.common.Mapping.Delegate
            public void set(IrDeclaration key, Object value) {
                Intrinsics.checkNotNullParameter(key, "key");
                if (value == null) {
                    this.map.remove(key);
                } else {
                    this.map.put(key, value);
                }
            }

            @Override // org.jetbrains.kotlin.backend.common.Mapping.Delegate
            public Set<K> getKeys() {
                return this.map.keySet();
            }
        };
    }
}
