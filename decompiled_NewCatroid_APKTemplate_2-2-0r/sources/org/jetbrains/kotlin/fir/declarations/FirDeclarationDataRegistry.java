package org.jetbrains.kotlin.fir.declarations;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.util.ConeTypeRegistry;
import org.jetbrains.kotlin.util.AbstractArrayMapOwner;
import org.jetbrains.kotlin.util.NullableArrayMapAccessor;

/* JADX INFO: compiled from: FirDeclarationAttributes.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0003\u0012\u0013\u0014B\u0007\b\u0002¢\u0006\u0002\u0010\u0004J5\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u0001H\b0\u0006\"\b\b\u0000\u0010\t*\u00020\u0002\"\b\b\u0001\u0010\b*\u00020\u00032\u0006\u0010\n\u001a\u0002H\t¢\u0006\u0002\u0010\u000bJ\u001d\u0010\f\u001a\u00020\r\"\b\b\u0000\u0010\t*\u00020\u00022\u0006\u0010\n\u001a\u0002H\t¢\u0006\u0002\u0010\u000eJ\u001d\u0010\u000f\u001a\u00020\u0010\"\b\b\u0000\u0010\t*\u00020\u00022\u0006\u0010\n\u001a\u0002H\t¢\u0006\u0002\u0010\u0011¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry;", "Lorg/jetbrains/kotlin/fir/util/ConeTypeRegistry;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataKey;", "", "()V", "attributesAccessor", "Lkotlin/properties/ReadWriteProperty;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "K", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataKey;)Lkotlin/properties/ReadWriteProperty;", "data", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$DeclarationDataAccessor;", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataKey;)Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$DeclarationDataAccessor;", "symbolAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$SymbolDataAccessor;", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataKey;)Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$SymbolDataAccessor;", "AttributeDataAccessor", "DeclarationDataAccessor", "SymbolDataAccessor", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDeclarationDataRegistry extends ConeTypeRegistry<FirDeclarationDataKey, Object> {
    public static final FirDeclarationDataRegistry INSTANCE = new FirDeclarationDataRegistry();

    private FirDeclarationDataRegistry() {
    }

    public final <K extends FirDeclarationDataKey> DeclarationDataAccessor data(K key) {
        Intrinsics.checkNotNullParameter(key, "key");
        KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(key.getClass());
        return new DeclarationDataAccessor(generateAnyNullableAccessor(orCreateKotlinClass), orCreateKotlinClass);
    }

    public final <K extends FirDeclarationDataKey> SymbolDataAccessor symbolAccessor(K key) {
        Intrinsics.checkNotNullParameter(key, "key");
        KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(key.getClass());
        return new SymbolDataAccessor(generateAnyNullableAccessor(orCreateKotlinClass), orCreateKotlinClass);
    }

    public final <K extends FirDeclarationDataKey, V> ReadWriteProperty<FirDeclarationAttributes, V> attributesAccessor(K key) {
        Intrinsics.checkNotNullParameter(key, "key");
        KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(key.getClass());
        return new AttributeDataAccessor(generateNullableAccessor(orCreateKotlinClass), orCreateKotlinClass);
    }

    /* JADX INFO: compiled from: FirDeclarationAttributes.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B-\u0012\u0016\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0001\u0012\u0002\b\u00030\u0003\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0006¢\u0006\u0002\u0010\u0007J*\u0010\n\u001a\u0004\u0018\u0001H\u000b\"\u0004\b\u0000\u0010\u000b2\u0006\u0010\f\u001a\u00020\r2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000fH\u0086\u0002¢\u0006\u0002\u0010\u0010J2\u0010\u0011\u001a\u00020\u0012\"\u0004\b\u0000\u0010\u000b2\u0006\u0010\f\u001a\u00020\r2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f2\b\u0010\u0013\u001a\u0004\u0018\u0001H\u000bH\u0086\u0002¢\u0006\u0002\u0010\u0014R\u001e\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0001\u0012\u0002\b\u00030\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$DeclarationDataAccessor;", "", "dataAccessor", "Lorg/jetbrains/kotlin/util/NullableArrayMapAccessor;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataKey;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "(Lorg/jetbrains/kotlin/util/NullableArrayMapAccessor;Lkotlin/reflect/KClass;)V", "getKey", "()Lkotlin/reflect/KClass;", "getValue", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "thisRef", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "property", "Lkotlin/reflect/KProperty;", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "setValue", "", "value", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DeclarationDataAccessor {
        private final NullableArrayMapAccessor<FirDeclarationDataKey, Object, ?> dataAccessor;
        private final KClass<? extends FirDeclarationDataKey> key;

        public DeclarationDataAccessor(NullableArrayMapAccessor<FirDeclarationDataKey, Object, ?> dataAccessor, KClass<? extends FirDeclarationDataKey> key) {
            Intrinsics.checkNotNullParameter(dataAccessor, "dataAccessor");
            Intrinsics.checkNotNullParameter(key, "key");
            this.dataAccessor = dataAccessor;
            this.key = key;
        }

        public final KClass<? extends FirDeclarationDataKey> getKey() {
            return this.key;
        }

        public final <V> V getValue(FirDeclaration thisRef, KProperty<?> property) {
            Intrinsics.checkNotNullParameter(thisRef, "thisRef");
            Intrinsics.checkNotNullParameter(property, "property");
            V v = (V) this.dataAccessor.getValue((AbstractArrayMapOwner<FirDeclarationDataKey, Object>) thisRef.getAttributes(), property);
            if (v == null) {
                return null;
            }
            return v;
        }

        public final <V> void setValue(FirDeclaration thisRef, KProperty<?> property, V value) {
            Intrinsics.checkNotNullParameter(thisRef, "thisRef");
            Intrinsics.checkNotNullParameter(property, "property");
            thisRef.getAttributes().set$tree(this.key, value);
        }
    }

    /* JADX INFO: compiled from: FirDeclarationAttributes.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B-\u0012\u0016\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0001\u0012\u0002\b\u00030\u0003\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0006¢\u0006\u0002\u0010\u0007J.\u0010\n\u001a\u0004\u0018\u0001H\u000b\"\u0004\b\u0000\u0010\u000b2\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\r2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000fH\u0086\u0002¢\u0006\u0002\u0010\u0010R\u001e\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0001\u0012\u0002\b\u00030\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0019\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$SymbolDataAccessor;", "", "dataAccessor", "Lorg/jetbrains/kotlin/util/NullableArrayMapAccessor;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataKey;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "(Lorg/jetbrains/kotlin/util/NullableArrayMapAccessor;Lkotlin/reflect/KClass;)V", "getKey", "()Lkotlin/reflect/KClass;", "getValue", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "thisRef", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "property", "Lkotlin/reflect/KProperty;", "(Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SymbolDataAccessor {
        private final NullableArrayMapAccessor<FirDeclarationDataKey, Object, ?> dataAccessor;
        private final KClass<? extends FirDeclarationDataKey> key;

        public SymbolDataAccessor(NullableArrayMapAccessor<FirDeclarationDataKey, Object, ?> dataAccessor, KClass<? extends FirDeclarationDataKey> key) {
            Intrinsics.checkNotNullParameter(dataAccessor, "dataAccessor");
            Intrinsics.checkNotNullParameter(key, "key");
            this.dataAccessor = dataAccessor;
            this.key = key;
        }

        public final KClass<? extends FirDeclarationDataKey> getKey() {
            return this.key;
        }

        public final <V> V getValue(FirBasedSymbol<?> thisRef, KProperty<?> property) {
            Intrinsics.checkNotNullParameter(thisRef, "thisRef");
            Intrinsics.checkNotNullParameter(property, "property");
            V v = (V) this.dataAccessor.getValue((AbstractArrayMapOwner<FirDeclarationDataKey, Object>) thisRef.getFir().getAttributes(), property);
            if (v == null) {
                return null;
            }
            return v;
        }
    }

    /* JADX INFO: compiled from: FirDeclarationAttributes.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u0001H\u00010\u0003B/\u0012\u0018\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00028\u00000\u0006\u0012\u000e\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\t¢\u0006\u0002\u0010\nJ$\u0010\u000f\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0010\u001a\u00020\u00042\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u0012H\u0096\u0002¢\u0006\u0002\u0010\u0013J,\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0010\u001a\u00020\u00042\n\u0010\u0011\u001a\u0006\u0012\u0002\b\u00030\u00122\b\u0010\u0016\u001a\u0004\u0018\u00018\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0017R#\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0019\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\t¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataRegistry$AttributeDataAccessor;", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "Lkotlin/properties/ReadWriteProperty;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "dataAccessor", "Lorg/jetbrains/kotlin/util/NullableArrayMapAccessor;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationDataKey;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/reflect/KClass;", "(Lorg/jetbrains/kotlin/util/NullableArrayMapAccessor;Lkotlin/reflect/KClass;)V", "getDataAccessor", "()Lorg/jetbrains/kotlin/util/NullableArrayMapAccessor;", "getKey", "()Lkotlin/reflect/KClass;", "getValue", "thisRef", "property", "Lkotlin/reflect/KProperty;", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;Lkotlin/reflect/KProperty;)Ljava/lang/Object;", "setValue", "", "value", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;Lkotlin/reflect/KProperty;Ljava/lang/Object;)V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class AttributeDataAccessor<V> implements ReadWriteProperty<FirDeclarationAttributes, V> {
        private final NullableArrayMapAccessor<FirDeclarationDataKey, Object, V> dataAccessor;
        private final KClass<? extends FirDeclarationDataKey> key;

        public AttributeDataAccessor(NullableArrayMapAccessor<FirDeclarationDataKey, Object, V> dataAccessor, KClass<? extends FirDeclarationDataKey> key) {
            Intrinsics.checkNotNullParameter(dataAccessor, "dataAccessor");
            Intrinsics.checkNotNullParameter(key, "key");
            this.dataAccessor = dataAccessor;
            this.key = key;
        }

        @Override // kotlin.properties.ReadWriteProperty, kotlin.properties.ReadOnlyProperty
        public /* bridge */ /* synthetic */ Object getValue(Object obj, KProperty kProperty) {
            return getValue((FirDeclarationAttributes) obj, (KProperty<?>) kProperty);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // kotlin.properties.ReadWriteProperty
        public /* bridge */ /* synthetic */ void setValue(FirDeclarationAttributes firDeclarationAttributes, KProperty kProperty, Object obj) {
            setValue2(firDeclarationAttributes, (KProperty<?>) kProperty, obj);
        }

        public final NullableArrayMapAccessor<FirDeclarationDataKey, Object, V> getDataAccessor() {
            return this.dataAccessor;
        }

        public final KClass<? extends FirDeclarationDataKey> getKey() {
            return this.key;
        }

        public V getValue(FirDeclarationAttributes thisRef, KProperty<?> property) {
            Intrinsics.checkNotNullParameter(thisRef, "thisRef");
            Intrinsics.checkNotNullParameter(property, "property");
            return (V) this.dataAccessor.getValue((AbstractArrayMapOwner<FirDeclarationDataKey, Object>) thisRef, property);
        }

        /* JADX INFO: renamed from: setValue, reason: avoid collision after fix types in other method */
        public void setValue2(FirDeclarationAttributes thisRef, KProperty<?> property, V value) {
            Intrinsics.checkNotNullParameter(thisRef, "thisRef");
            Intrinsics.checkNotNullParameter(property, "property");
            thisRef.set$tree(this.key, value);
        }
    }
}
