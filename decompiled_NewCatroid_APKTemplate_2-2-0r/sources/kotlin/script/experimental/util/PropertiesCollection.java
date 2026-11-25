package kotlin.script.experimental.util;

import androidx.exifinterface.media.ExifInterface;
import com.android.dx.rop.code.RegisterSpec;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.container.SpecTypePair;
import com.reandroid.arsc.value.CompoundEntry;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import kotlin.reflect.KType;
import kotlin.script.experimental.api.KotlinType;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: propertiesCollection.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010&\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0016\u0018\u0000 #2\u00020\u0001:\u0007\"#$%&'(B!\u0012\u001a\b\u0002\u0010\u0002\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0003¢\u0006\u0002\u0010\u0006J\u001a\u0010\f\u001a\u00020\r\"\u0004\b\u0000\u0010\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0004J\u001e\u0010\u0010\u001a\u001a\u0012\u0016\u0012\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00120\u0011J\u0013\u0010\u0013\u001a\u00020\r2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0005H\u0096\u0002J$\u0010\u0015\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0004H\u0086\u0002¢\u0006\u0002\u0010\u0016J!\u0010\u0017\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0004¢\u0006\u0002\u0010\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0006\u0010\u001a\u001a\u00020\rJ\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\u0010\u0010\u001f\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020!H\u0002R#\u0010\u0007\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00038F¢\u0006\u0006\u001a\u0004\b\b\u0010\tR,\u0010\u0002\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0003X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\t\"\u0004\b\u000b\u0010\u0006¨\u0006)"}, d2 = {"Lkotlin/script/experimental/util/PropertiesCollection;", "Ljava/io/Serializable;", "properties", "", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "", "(Ljava/util/Map;)V", "notTransientData", "getNotTransientData", "()Ljava/util/Map;", "getProperties", "setProperties", "containsKey", "", ExifInterface.GPS_DIRECTION_TRUE, ASTExpr.DEFAULT_MAP_KEY_NAME, TypeBlock.NAME_entries, "", "", Namer.EQUALS_METHOD_NAME, "other", "get", "(Lkotlin/script/experimental/util/PropertiesCollection$Key;)Ljava/lang/Object;", "getNoDefault", "hashCode", "", "isEmpty", "readObject", "", "inputStream", "Ljava/io/ObjectInputStream;", "writeObject", "outputStream", "Ljava/io/ObjectOutputStream;", "Builder", "Companion", "CopiedKey", "Key", "PropertyKeyCopyDelegate", "PropertyKeyDelegate", "TransientKey", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class PropertiesCollection implements Serializable {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final long serialVersionUID = 1;
    private Map<Key<?>, ? extends Object> properties;

    /* JADX WARN: Multi-variable type inference failed */
    public PropertiesCollection() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public PropertiesCollection(Map<Key<?>, ? extends Object> properties) {
        Intrinsics.checkNotNullParameter(properties, "properties");
        this.properties = properties;
    }

    public /* synthetic */ PropertiesCollection(Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? MapsKt.emptyMap() : map);
    }

    protected final Map<Key<?>, Object> getProperties() {
        return this.properties;
    }

    protected final void setProperties(Map<Key<?>, ? extends Object> map) {
        Intrinsics.checkNotNullParameter(map, "<set-?>");
        this.properties = map;
    }

    /* JADX INFO: compiled from: propertiesCollection.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\u0016\u0018\u0000 \u0017*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0001\u0017B\u001b\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010\u0006B(\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0019\u0010\u0007\u001a\u0015\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00018\u00000\b¢\u0006\u0002\b\n¢\u0006\u0002\u0010\u000bJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0096\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0004H\u0016R$\u0010\u0007\u001a\u0015\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00018\u00000\b¢\u0006\u0002\b\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0018"}, d2 = {"Lkotlin/script/experimental/util/PropertiesCollection$Key;", ExifInterface.GPS_DIRECTION_TRUE, "Ljava/io/Serializable;", "name", "", "defaultValue", "(Ljava/lang/String;Ljava/lang/Object;)V", "getDefaultValue", "Lkotlin/Function1;", "Lkotlin/script/experimental/util/PropertiesCollection;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)V", "getGetDefaultValue", "()Lkotlin/jvm/functions/Function1;", "getName", "()Ljava/lang/String;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "Companion", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class Key<T> implements Serializable {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static final long serialVersionUID = 0;
        private final transient Function1<PropertiesCollection, T> getDefaultValue;
        private final String name;

        /* JADX WARN: Multi-variable type inference failed */
        public Key(String name2, Function1<? super PropertiesCollection, ? extends T> getDefaultValue) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(getDefaultValue, "getDefaultValue");
            this.name = name2;
            this.getDefaultValue = getDefaultValue;
        }

        public final String getName() {
            return this.name;
        }

        public final Function1<PropertiesCollection, T> getGetDefaultValue() {
            return this.getDefaultValue;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public Key(String name2, final T t) {
            this(name2, (Function1) new Function1<PropertiesCollection, T>() { // from class: kotlin.script.experimental.util.PropertiesCollection.Key.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final T invoke(PropertiesCollection propertiesCollection) {
                    Intrinsics.checkNotNullParameter(propertiesCollection, "$this$null");
                    return t;
                }
            });
            Intrinsics.checkNotNullParameter(name2, "name");
        }

        public /* synthetic */ Key(String str, Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? null : obj);
        }

        public boolean equals(Object other) {
            if (other instanceof Key) {
                return Intrinsics.areEqual(this.name, ((Key) other).name);
            }
            return false;
        }

        public int hashCode() {
            return this.name.hashCode();
        }

        public String toString() {
            return "Key(" + this.name + ')';
        }

        /* JADX INFO: compiled from: propertiesCollection.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u00048\u0002X\u0083D¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002¨\u0006\u0006"}, d2 = {"Lkotlin/script/experimental/util/PropertiesCollection$Key$Companion;", "", "()V", CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME, "", "getSerialVersionUID$annotations", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            @JvmStatic
            private static /* synthetic */ void getSerialVersionUID$annotations() {
            }

            private Companion() {
            }
        }
    }

    /* JADX INFO: compiled from: propertiesCollection.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B(\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0019\u0010\u0005\u001a\u0015\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u0006¢\u0006\u0002\b\b¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lkotlin/script/experimental/util/PropertiesCollection$TransientKey;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "name", "", "getDefaultValue", "Lkotlin/Function1;", "Lkotlin/script/experimental/util/PropertiesCollection;", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)V", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TransientKey<T> extends Key<T> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public TransientKey(String name2, Function1<? super PropertiesCollection, ? extends T> getDefaultValue) {
            super(name2, (Function1) getDefaultValue);
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(getDefaultValue, "getDefaultValue");
        }
    }

    /* JADX INFO: compiled from: propertiesCollection.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B.\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\u0019\u0010\u0004\u001a\u0015\u0012\u0004\u0012\u00020\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0005¢\u0006\u0002\b\u0007¢\u0006\u0002\u0010\b¨\u0006\t"}, d2 = {"Lkotlin/script/experimental/util/PropertiesCollection$CopiedKey;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/script/experimental/util/PropertiesCollection$Key;", FirebaseAnalytics.Param.SOURCE, "getSourceProperties", "Lkotlin/Function1;", "Lkotlin/script/experimental/util/PropertiesCollection;", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/script/experimental/util/PropertiesCollection$Key;Lkotlin/jvm/functions/Function1;)V", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CopiedKey<T> extends Key<T> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CopiedKey(final Key<T> source, final Function1<? super PropertiesCollection, ? extends PropertiesCollection> getSourceProperties) {
            super(source.getName(), (Function1) new Function1<PropertiesCollection, T>() { // from class: kotlin.script.experimental.util.PropertiesCollection.CopiedKey.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final T invoke(PropertiesCollection propertiesCollection) {
                    Intrinsics.checkNotNullParameter(propertiesCollection, "$this$null");
                    PropertiesCollection propertiesCollectionInvoke = getSourceProperties.invoke(propertiesCollection);
                    if (propertiesCollectionInvoke == null) {
                        return source.getGetDefaultValue().invoke(propertiesCollection);
                    }
                    return (T) propertiesCollectionInvoke.get(source);
                }
            });
            Intrinsics.checkNotNullParameter(source, "source");
            Intrinsics.checkNotNullParameter(getSourceProperties, "getSourceProperties");
        }
    }

    /* JADX INFO: compiled from: propertiesCollection.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u001b\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00018\u0000\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B*\u0012\u0019\u0010\u0007\u001a\u0015\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00018\u00000\b¢\u0006\u0002\b\n\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u000bJ%\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u00022\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0011H\u0086\u0002R!\u0010\u0007\u001a\u0015\u0012\u0004\u0012\u00020\t\u0012\u0006\u0012\u0004\u0018\u00018\u00000\b¢\u0006\u0002\b\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\f¨\u0006\u0012"}, d2 = {"Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyDelegate;", ExifInterface.GPS_DIRECTION_TRUE, "", "defaultValue", "isTransient", "", "(Ljava/lang/Object;Z)V", "getDefaultValue", "Lkotlin/Function1;", "Lkotlin/script/experimental/util/PropertiesCollection;", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function1;Z)V", "()Z", "getValue", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "thisRef", "property", "Lkotlin/reflect/KProperty;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PropertyKeyDelegate<T> {
        private final Function1<PropertiesCollection, T> getDefaultValue;
        private final boolean isTransient;

        /* JADX WARN: Multi-variable type inference failed */
        public PropertyKeyDelegate(Function1<? super PropertiesCollection, ? extends T> getDefaultValue, boolean z) {
            Intrinsics.checkNotNullParameter(getDefaultValue, "getDefaultValue");
            this.getDefaultValue = getDefaultValue;
            this.isTransient = z;
        }

        public /* synthetic */ PropertyKeyDelegate(Function1 function1, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(function1, (i & 2) != 0 ? false : z);
        }

        /* JADX INFO: renamed from: isTransient, reason: from getter */
        public final boolean getIsTransient() {
            return this.isTransient;
        }

        public PropertyKeyDelegate(final T t, boolean z) {
            this((Function1) new Function1<PropertiesCollection, T>() { // from class: kotlin.script.experimental.util.PropertiesCollection.PropertyKeyDelegate.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final T invoke(PropertiesCollection propertiesCollection) {
                    Intrinsics.checkNotNullParameter(propertiesCollection, "$this$null");
                    return t;
                }
            }, z);
        }

        public /* synthetic */ PropertyKeyDelegate(Object obj, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(obj, (i & 2) != 0 ? false : z);
        }

        public final Key<T> getValue(Object thisRef, KProperty<?> property) {
            Intrinsics.checkNotNullParameter(property, "property");
            return this.isTransient ? new TransientKey(property.getName(), this.getDefaultValue) : new Key<>(property.getName(), (Function1) this.getDefaultValue);
        }
    }

    /* JADX INFO: compiled from: propertiesCollection.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B0\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u001b\b\u0002\u0010\u0005\u001a\u0015\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006¢\u0006\u0002\b\b¢\u0006\u0002\u0010\tJ%\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\b\u0010\u000f\u001a\u0004\u0018\u00010\u00022\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u0011H\u0086\u0002R$\u0010\u0005\u001a\u0015\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006¢\u0006\u0002\b\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0012"}, d2 = {"Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyCopyDelegate;", ExifInterface.GPS_DIRECTION_TRUE, "", FirebaseAnalytics.Param.SOURCE, "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "getSourceProperties", "Lkotlin/Function1;", "Lkotlin/script/experimental/util/PropertiesCollection;", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/script/experimental/util/PropertiesCollection$Key;Lkotlin/jvm/functions/Function1;)V", "getGetSourceProperties", "()Lkotlin/jvm/functions/Function1;", "getSource", "()Lkotlin/script/experimental/util/PropertiesCollection$Key;", "getValue", "thisRef", "property", "Lkotlin/reflect/KProperty;", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PropertyKeyCopyDelegate<T> {
        private final Function1<PropertiesCollection, PropertiesCollection> getSourceProperties;
        private final Key<T> source;

        /* JADX WARN: Multi-variable type inference failed */
        public PropertyKeyCopyDelegate(Key<T> source, Function1<? super PropertiesCollection, ? extends PropertiesCollection> getSourceProperties) {
            Intrinsics.checkNotNullParameter(source, "source");
            Intrinsics.checkNotNullParameter(getSourceProperties, "getSourceProperties");
            this.source = source;
            this.getSourceProperties = getSourceProperties;
        }

        public /* synthetic */ PropertyKeyCopyDelegate(Key key, AnonymousClass1 anonymousClass1, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(key, (i & 2) != 0 ? new Function1() { // from class: kotlin.script.experimental.util.PropertiesCollection.PropertyKeyCopyDelegate.1
                @Override // kotlin.jvm.functions.Function1
                public final Void invoke(PropertiesCollection propertiesCollection) {
                    Intrinsics.checkNotNullParameter(propertiesCollection, "$this$null");
                    return null;
                }
            } : anonymousClass1);
        }

        public final Function1<PropertiesCollection, PropertiesCollection> getGetSourceProperties() {
            return this.getSourceProperties;
        }

        public final Key<T> getSource() {
            return this.source;
        }

        public final Key<T> getValue(Object thisRef, KProperty<?> property) {
            Intrinsics.checkNotNullParameter(property, "property");
            return new CopiedKey(this.source, this.getSourceProperties);
        }
    }

    public final <T> T get(Key<T> key) {
        Intrinsics.checkNotNullParameter(key, "key");
        T tInvoke = (T) this.properties.get(key);
        if (tInvoke == null) {
            tInvoke = this.properties.containsKey(key) ? null : key.getGetDefaultValue().invoke(this);
        }
        if (tInvoke == null) {
            return null;
        }
        return tInvoke;
    }

    public final <T> T getNoDefault(Key<T> key) {
        Intrinsics.checkNotNullParameter(key, "key");
        T t = (T) this.properties.get(key);
        if (t != null) {
            return t;
        }
        return null;
    }

    public final <T> boolean containsKey(Key<T> key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.properties.containsKey(key);
    }

    public final Set<Map.Entry<Key<?>, Object>> entries() {
        return this.properties.entrySet();
    }

    public final Map<Key<?>, Object> getNotTransientData() {
        Map<Key<?>, ? extends Object> map = this.properties;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<Key<?>, ? extends Object> entry : map.entrySet()) {
            Key<?> key = entry.getKey();
            Object value = entry.getValue();
            if (!(key instanceof TransientKey) && (value instanceof Serializable)) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return linkedHashMap;
    }

    public final boolean isEmpty() {
        return this.properties.isEmpty();
    }

    public boolean equals(Object other) {
        PropertiesCollection propertiesCollection = other instanceof PropertiesCollection ? (PropertiesCollection) other : null;
        return propertiesCollection != null && Intrinsics.areEqual(propertiesCollection.properties, this.properties);
    }

    public int hashCode() {
        return this.properties.hashCode();
    }

    private final void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(getNotTransientData());
    }

    private final void readObject(ObjectInputStream inputStream) throws ClassNotFoundException, IOException {
        Object object = inputStream.readObject();
        Intrinsics.checkNotNull(object, "null cannot be cast to non-null type kotlin.collections.Map<kotlin.script.experimental.util.PropertiesCollection.Key<*>, kotlin.Any?>");
        this.properties = (Map) object;
    }

    /* JADX INFO: compiled from: propertiesCollection.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J-\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u0001H\b2\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ7\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0000\u0010\b2\u0019\u0010\r\u001a\u0015\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u0001H\b0\u000e¢\u0006\u0002\b\u00102\b\b\u0002\u0010\n\u001a\u00020\u000bJ=\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\b0\u0012\"\u0004\b\u0000\u0010\b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\b0\u00142\u001b\b\u0002\u0010\u0015\u001a\u0015\u0012\u0004\u0012\u00020\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000e¢\u0006\u0002\b\u0010R\u0016\u0010\u0003\u001a\u00020\u00048\u0002X\u0083D¢\u0006\b\n\u0000\u0012\u0004\b\u0005\u0010\u0002¨\u0006\u0016"}, d2 = {"Lkotlin/script/experimental/util/PropertiesCollection$Companion;", "", "()V", CommonClassNames.SERIAL_VERSION_UID_FIELD_NAME, "", "getSerialVersionUID$annotations", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyDelegate;", ExifInterface.GPS_DIRECTION_TRUE, "defaultValue", "isTransient", "", "(Ljava/lang/Object;Z)Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyDelegate;", "getDefaultValue", "Lkotlin/Function1;", "Lkotlin/script/experimental/util/PropertiesCollection;", "Lkotlin/ExtensionFunctionType;", "keyCopy", "Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyCopyDelegate;", FirebaseAnalytics.Param.SOURCE, "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "getSourceProperties", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        private static /* synthetic */ void getSerialVersionUID$annotations() {
        }

        private Companion() {
        }

        public static /* synthetic */ PropertyKeyDelegate key$default(Companion companion, Object obj, boolean z, int i, Object obj2) {
            if ((i & 1) != 0) {
                obj = null;
            }
            if ((i & 2) != 0) {
                z = false;
            }
            return companion.key(obj, z);
        }

        public final <T> PropertyKeyDelegate<T> key(T defaultValue, boolean isTransient) {
            return new PropertyKeyDelegate<>(defaultValue, isTransient);
        }

        public static /* synthetic */ PropertyKeyDelegate key$default(Companion companion, Function1 function1, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = false;
            }
            return companion.key(function1, z);
        }

        public final <T> PropertyKeyDelegate<T> key(Function1<? super PropertiesCollection, ? extends T> getDefaultValue, boolean isTransient) {
            Intrinsics.checkNotNullParameter(getDefaultValue, "getDefaultValue");
            return new PropertyKeyDelegate<>((Function1) getDefaultValue, isTransient);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ PropertyKeyCopyDelegate keyCopy$default(Companion companion, Key key, Function1 function1, int i, Object obj) {
            if ((i & 2) != 0) {
                function1 = new Function1() { // from class: kotlin.script.experimental.util.PropertiesCollection$Companion$keyCopy$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Void invoke(PropertiesCollection propertiesCollection) {
                        Intrinsics.checkNotNullParameter(propertiesCollection, "$this$null");
                        return null;
                    }
                };
            }
            return companion.keyCopy(key, function1);
        }

        public final <T> PropertyKeyCopyDelegate<T> keyCopy(Key<T> source, Function1<? super PropertiesCollection, ? extends PropertiesCollection> getSourceProperties) {
            Intrinsics.checkNotNullParameter(source, "source");
            Intrinsics.checkNotNullParameter(getSourceProperties, "getSourceProperties");
            return new PropertyKeyCopyDelegate<>(source, getSourceProperties);
        }
    }

    /* JADX INFO: compiled from: propertiesCollection.kt */
    @Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J*\u0010\u000b\u001a\u0004\u0018\u0001H\f\"\b\b\u0000\u0010\f*\u00020\u00012\u000e\u0010\r\u001a\n\u0012\u0006\b\u0000\u0012\u0002H\f0\bH\u0086\u0002¢\u0006\u0002\u0010\u000eJ\u001c\u0010\u000f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\f2\u000e\u0010\r\u001a\n\u0012\u0006\b\u0000\u0012\u0002H\f0\bJ,\u0010\u0011\u001a\u00020\u0010\"\u0004\b\u0000\u0010\f2\u000e\u0010\r\u001a\n\u0012\u0006\b\u0000\u0012\u0002H\f0\b2\u0006\u0010\u0012\u001a\u0002H\fH\u0086\u0002¢\u0006\u0002\u0010\u0013J7\u0010\u0014\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u0015*\u0010\u0012\f\b\u0000\u0012\b\u0012\u0004\u0012\u0002H\u00150\u00160\b2\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00150\u0018\"\u0002H\u0015¢\u0006\u0002\u0010\u0019J3\u0010\u0014\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u0015*\u0010\u0012\f\b\u0000\u0012\b\u0012\u0004\u0012\u0002H\u00150\u00160\b2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00150\u0003H\u0007¢\u0006\u0002\b\u001aJK\u0010\u0014\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u001b\"\u0004\b\u0001\u0010\u0015*\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002H\u00150\u001c0\b2\u0018\u0010\u0017\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002H\u00150\u001d0\u0003H\u0007¢\u0006\u0002\b\u001eJ>\u0010\u0014\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u001b\"\u0004\b\u0001\u0010\u0015*\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002H\u00150\u001c0\b2\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002H\u00150\u001cJ5\u0010\u001f\u001a\u00020\u0010\"\b\b\u0000\u0010\f*\u00020\u0000*\u0002H\f2\u0017\u0010 \u001a\u0013\u0012\u0004\u0012\u0002H\f\u0012\u0004\u0012\u00020\u00100!¢\u0006\u0002\b\"H\u0086\u0002¢\u0006\u0002\u0010#J$\u0010\u001f\u001a\u0004\u0018\u0001H\f\"\b\b\u0000\u0010\f*\u00020\u0001*\b\u0012\u0004\u0012\u0002H\f0\bH\u0086\u0002¢\u0006\u0002\u0010\u000eJ&\u0010\u001f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\b2\u0006\u0010$\u001a\u0002H\fH\u0086\u0002¢\u0006\u0002\u0010\u0013J:\u0010\u001f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\f*\u0010\u0012\f\b\u0000\u0012\b\u0012\u0004\u0012\u0002H\f0\u00160\b2\u0012\u0010%\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\f0\u0018\"\u0002H\fH\u0086\u0002¢\u0006\u0002\u0010\u0019J>\u0010\u001f\u001a\u00020\u0010*\u0010\u0012\f\b\u0000\u0012\b\u0012\u0004\u0012\u00020&0\u00160\b2\u001a\u0010'\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030(0\u0018\"\u0006\u0012\u0002\b\u00030(H\u0087\u0002¢\u0006\u0004\b)\u0010*J$\u0010\u001f\u001a\u00020\u0010*\b\u0012\u0004\u0012\u00020&0\b2\n\u0010+\u001a\u0006\u0012\u0002\b\u00030(H\u0087\u0002¢\u0006\u0002\b,J2\u0010\u001f\u001a\u00020\u0010*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020-0\u00160\b2\u0012\u0010.\u001a\n\u0012\u0006\b\u0001\u0012\u00020&0\u0018\"\u00020&H\u0086\u0002¢\u0006\u0002\u0010/J:\u0010\u001f\u001a\u00020\u0010*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020-0\u00160\b2\u001a\u00100\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030(0\u0018\"\u0006\u0012\u0002\b\u00030(H\u0086\u0002¢\u0006\u0002\u0010*J2\u0010\u001f\u001a\u00020\u0010*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020-0\u00160\b2\u0012\u00101\u001a\n\u0012\u0006\b\u0001\u0012\u0002020\u0018\"\u000202H\u0086\u0002¢\u0006\u0002\u00103J\\\u0010\u001f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u001b\"\u0004\b\u0001\u0010\u0015*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002H\u00150\u001c0\b2*\u00104\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002H\u00150\u001d0\u0018\"\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002H\u00150\u001dH\u0086\u0002¢\u0006\u0002\u00105JX\u0010\u001f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u001b*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u00020-0\u001c0\b2*\u0010.\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u00020&0\u001d0\u0018\"\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u00020&0\u001dH\u0087\u0002¢\u0006\u0004\b6\u00105J`\u0010\u001f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u001b*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u00020-0\u001c0\b22\u00100\u001a\u001a\u0012\u0016\b\u0001\u0012\u0012\u0012\u0004\u0012\u0002H\u001b\u0012\b\u0012\u0006\u0012\u0002\b\u00030(0\u001d0\u0018\"\u0012\u0012\u0004\u0012\u0002H\u001b\u0012\b\u0012\u0006\u0012\u0002\b\u00030(0\u001dH\u0087\u0002¢\u0006\u0004\b7\u00105JX\u0010\u001f\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u001b*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u00020-0\u001c0\b2*\u00101\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002020\u001d0\u0018\"\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002020\u001dH\u0087\u0002¢\u0006\u0004\b8\u00105J\u001b\u0010\u001f\u001a\u00020\u0010*\b\u0012\u0004\u0012\u00020-0\b2\u0006\u00109\u001a\u00020&H\u0086\u0002J\u001f\u0010\u001f\u001a\u00020\u0010*\b\u0012\u0004\u0012\u00020-0\b2\n\u0010+\u001a\u0006\u0012\u0002\b\u00030(H\u0086\u0002J\u001b\u0010\u001f\u001a\u00020\u0010*\b\u0012\u0004\u0012\u00020-0\b2\u0006\u0010:\u001a\u000202H\u0086\u0002J#\u0010;\u001a\u00020\u0010\"\u0004\b\u0000\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\b2\u0006\u0010$\u001a\u0002H\f¢\u0006\u0002\u0010\u0013J.\u0010<\u001a\u00020\u0010\"\u0004\b\u0000\u0010\f*\u0010\u0012\f\b\u0000\u0012\b\u0012\u0004\u0012\u0002H\f0\u00160\b2\u000e\u0010%\u001a\n\u0012\u0004\u0012\u0002H\f\u0018\u00010\u0003JM\u0010<\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u001b\"\u0004\b\u0001\u0010\u0015*\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002H\u00150\u001c0\b2\u001a\u0010%\u001a\u0016\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002H\u00150\u001d\u0018\u00010\u0003H\u0007¢\u0006\u0002\b=J@\u0010<\u001a\u00020\u0010\"\u0004\b\u0000\u0010\u001b\"\u0004\b\u0001\u0010\u0015*\u0016\u0012\u0012\b\u0000\u0012\u000e\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002H\u00150\u001c0\b2\u0014\u0010%\u001a\u0010\u0012\u0004\u0012\u0002H\u001b\u0012\u0004\u0012\u0002H\u0015\u0018\u00010\u001cJ%\u0010>\u001a\u00020\u0010\"\u0004\b\u0000\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\b2\b\u0010$\u001a\u0004\u0018\u0001H\f¢\u0006\u0002\u0010\u0013J%\u0010?\u001a\u00020\u0010\"\u0004\b\u0000\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\b2\b\u0010$\u001a\u0004\u0018\u0001H\f¢\u0006\u0002\u0010\u0013J.\u0010@\u001a\u00020\u0010\"\u0004\b\u0000\u0010\f*\b\u0012\u0004\u0012\u0002H\f0\b2\u0016\u0010 \u001a\u0012\u0012\u0006\u0012\u0004\u0018\u0001H\f\u0012\u0006\u0012\u0004\u0018\u0001H\f0!R#\u0010\u0006\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006A"}, d2 = {"Lkotlin/script/experimental/util/PropertiesCollection$Builder;", "", "baseProperties", "", "Lkotlin/script/experimental/util/PropertiesCollection;", "(Ljava/lang/Iterable;)V", "data", "", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "getData", "()Ljava/util/Map;", "get", ExifInterface.GPS_DIRECTION_TRUE, ASTExpr.DEFAULT_MAP_KEY_NAME, "(Lkotlin/script/experimental/util/PropertiesCollection$Key;)Ljava/lang/Object;", "reset", "", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "value", "(Lkotlin/script/experimental/util/PropertiesCollection$Key;Ljava/lang/Object;)V", "append", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", CompoundEntry.NAME_values, "", "(Lkotlin/script/experimental/util/PropertiesCollection$Key;[Ljava/lang/Object;)V", "appendToList", "K", "", "Lkotlin/Pair;", "appendToMap", "invoke", "body", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/script/experimental/util/PropertiesCollection$Builder;Lkotlin/jvm/functions/Function1;)V", RegisterSpec.PREFIX, "vals", "", "kclasses", "Lkotlin/reflect/KClass;", "invoke_string_list_fqn_from_reflected_class", "(Lkotlin/script/experimental/util/PropertiesCollection$Key;[Lkotlin/reflect/KClass;)V", MangleConstant.CLASS_PREFIX, "invoke_string_fqn_from_reflected_class", "Lkotlin/script/experimental/api/KotlinType;", "fqnames", "(Lkotlin/script/experimental/util/PropertiesCollection$Key;[Ljava/lang/String;)V", "classes", SpecTypePair.NAME_types, "Lkotlin/reflect/KType;", "(Lkotlin/script/experimental/util/PropertiesCollection$Key;[Lkotlin/reflect/KType;)V", "vs", "(Lkotlin/script/experimental/util/PropertiesCollection$Key;[Lkotlin/Pair;)V", "invoke_kotlintype_map_from_fqname", "invoke_kotlintype_map_from_kclass", "invoke_kotlintype_map_from_ktype", "fqname", "ktype", "put", "putIfAny", "putIfAny_map", "putIfNotNull", "replaceOnlyDefault", ConfigConstants.CONFIG_KEY_UPDATE, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class Builder {
        private final Map<Key<?>, Object> data;

        /* JADX WARN: Multi-variable type inference failed */
        public Builder() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public Builder(Iterable<? extends PropertiesCollection> baseProperties) {
            Intrinsics.checkNotNullParameter(baseProperties, "baseProperties");
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            Iterator<? extends PropertiesCollection> it = baseProperties.iterator();
            while (it.hasNext()) {
                linkedHashMap.putAll(it.next().getProperties());
            }
            this.data = linkedHashMap;
        }

        public /* synthetic */ Builder(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? CollectionsKt.emptyList() : list);
        }

        public final Map<Key<?>, Object> getData() {
            return this.data;
        }

        public final <T> void invoke(Key<T> key, T t) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            this.data.put(key, t);
        }

        public final <T> void put(Key<T> key, T t) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            this.data.put(key, t);
        }

        public final <T> void putIfNotNull(Key<T> key, T t) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            if (t != null) {
                this.data.put(key, t);
            }
        }

        public final <T> void replaceOnlyDefault(Key<T> key, T t) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            if (!this.data.containsKey(key) || Intrinsics.areEqual(this.data.get(key), key.getGetDefaultValue().invoke(new PropertiesCollection(this.data)))) {
                this.data.put(key, t);
            }
        }

        public final <T> void update(Key<T> key, Function1<? super T, ? extends T> body) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            Intrinsics.checkNotNullParameter(body, "body");
            Object obj = this.data.get(key);
            if (obj == null) {
                obj = null;
            }
            putIfNotNull(key, body.invoke(obj));
        }

        public final <T> void putIfAny(Key<? super List<? extends T>> key, Iterable<? extends T> iterable) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            if (iterable == null || !CollectionsKt.any(iterable)) {
                return;
            }
            this.data.put(key, iterable instanceof List ? (List) iterable : CollectionsKt.toList(iterable));
        }

        public final <T> void invoke(Key<? super List<? extends T>> key, T... vals) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            Intrinsics.checkNotNullParameter(vals, "vals");
            appendToList(key, ArraysKt.asIterable(vals));
        }

        public final <K, V> void putIfAny_map(Key<? super Map<K, ? extends V>> key, Iterable<? extends Pair<? extends K, ? extends V>> iterable) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            if (iterable == null || !CollectionsKt.any(iterable)) {
                return;
            }
            this.data.put(key, MapsKt.toMap(iterable));
        }

        public final <K, V> void putIfAny(Key<? super Map<K, ? extends V>> key, Map<K, ? extends V> map) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            if (map == null || !(!map.isEmpty())) {
                return;
            }
            this.data.put(key, map);
        }

        public final <K, V> void invoke(Key<Map<K, V>> key, Pair<? extends K, ? extends V>... vs) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            Intrinsics.checkNotNullParameter(vs, "vs");
            appendToMap(key, ArraysKt.asIterable(vs));
        }

        public final void invoke_string_fqn_from_reflected_class(Key<String> key, KClass<?> kclass) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            Intrinsics.checkNotNullParameter(kclass, "kclass");
            this.data.put(key, JvmClassMappingKt.getJavaClass((KClass) kclass).getName());
        }

        public final void invoke(Key<KotlinType> key, KClass<?> kclass) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            Intrinsics.checkNotNullParameter(kclass, "kclass");
            this.data.put(key, new KotlinType((KClass) kclass, false, 2, (DefaultConstructorMarker) null));
        }

        public final void invoke(Key<KotlinType> key, KType ktype) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            Intrinsics.checkNotNullParameter(ktype, "ktype");
            this.data.put(key, new KotlinType(ktype));
        }

        public final void invoke(Key<KotlinType> key, String fqname) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            Intrinsics.checkNotNullParameter(fqname, "fqname");
            this.data.put(key, new KotlinType(fqname, false, 2, (DefaultConstructorMarker) null));
        }

        public final <T> void set(Key<? super T> key, T value) {
            Intrinsics.checkNotNullParameter(key, "key");
            this.data.put(key, value);
        }

        public final <T> void reset(Key<? super T> key) {
            Intrinsics.checkNotNullParameter(key, "key");
            this.data.remove(key);
        }

        public final <T> T get(Key<? super T> key) {
            Intrinsics.checkNotNullParameter(key, "key");
            T t = (T) this.data.get(key);
            if (t != null) {
                return t;
            }
            return null;
        }

        public final <T> T invoke(Key<T> key) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            return (T) get(key);
        }

        public final <V> void appendToList(Key<? super List<? extends V>> key, Iterable<? extends V> values) {
            List list;
            Intrinsics.checkNotNullParameter(key, "<this>");
            Intrinsics.checkNotNullParameter(values, "values");
            List list2 = (List) get(key);
            if (list2 == null || (list = CollectionsKt.plus((Collection) list2, (Iterable) values)) == null) {
                list = CollectionsKt.toList(values);
            }
            this.data.put(key, list);
        }

        public final <V> void append(Key<? super List<? extends V>> key, V... values) {
            List list;
            Intrinsics.checkNotNullParameter(key, "<this>");
            Intrinsics.checkNotNullParameter(values, "values");
            List list2 = (List) get(key);
            if (list2 == null || (list = CollectionsKt.plus((Collection) list2, (Object[]) values)) == null) {
                list = ArraysKt.toList(values);
            }
            this.data.put(key, list);
        }

        public final <K, V> void append(Key<? super Map<K, ? extends V>> key, Map<K, ? extends V> values) {
            Map<K, ? extends V> mapPlus;
            Intrinsics.checkNotNullParameter(key, "<this>");
            Intrinsics.checkNotNullParameter(values, "values");
            Map map = (Map) get(key);
            if (map != null && (mapPlus = MapsKt.plus(map, values)) != null) {
                values = mapPlus;
            }
            this.data.put(key, values);
        }

        public final <K, V> void appendToMap(Key<? super Map<K, ? extends V>> key, Iterable<? extends Pair<? extends K, ? extends V>> values) {
            Map map;
            Intrinsics.checkNotNullParameter(key, "<this>");
            Intrinsics.checkNotNullParameter(values, "values");
            Map map2 = (Map) get(key);
            if (map2 == null || (map = MapsKt.plus(map2, values)) == null) {
                map = MapsKt.toMap(values);
            }
            this.data.put(key, map);
        }

        public final <T extends Builder> void invoke(T t, Function1<? super T, Unit> body) {
            Intrinsics.checkNotNullParameter(t, "<this>");
            Intrinsics.checkNotNullParameter(body, "body");
            body.invoke(t);
            this.data.putAll(t.data);
        }

        public final void invoke_string_list_fqn_from_reflected_class(Key<? super List<String>> key, KClass<?>... kclasses) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            Intrinsics.checkNotNullParameter(kclasses, "kclasses");
            ArrayList arrayList = new ArrayList(kclasses.length);
            for (KClass<?> kClass : kclasses) {
                arrayList.add(JvmClassMappingKt.getJavaClass((KClass) kClass).getName());
            }
            appendToList(key, arrayList);
        }

        public final void invoke(Key<List<KotlinType>> key, KClass<?>... classes) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            Intrinsics.checkNotNullParameter(classes, "classes");
            ArrayList arrayList = new ArrayList(classes.length);
            boolean z = false;
            for (KClass<?> kClass : classes) {
                arrayList.add(new KotlinType(kClass, z, 2, (DefaultConstructorMarker) null));
            }
            appendToList(key, arrayList);
        }

        public final void invoke(Key<List<KotlinType>> key, KType... types) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            Intrinsics.checkNotNullParameter(types, "types");
            ArrayList arrayList = new ArrayList(types.length);
            for (KType kType : types) {
                arrayList.add(new KotlinType(kType));
            }
            appendToList(key, arrayList);
        }

        public final void invoke(Key<List<KotlinType>> key, String... fqnames) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            Intrinsics.checkNotNullParameter(fqnames, "fqnames");
            ArrayList arrayList = new ArrayList(fqnames.length);
            boolean z = false;
            for (String str : fqnames) {
                arrayList.add(new KotlinType(str, z, 2, (DefaultConstructorMarker) null));
            }
            appendToList(key, arrayList);
        }

        public final <K> void invoke_kotlintype_map_from_kclass(Key<Map<K, KotlinType>> key, Pair<? extends K, ? extends KClass<?>>... classes) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            Intrinsics.checkNotNullParameter(classes, "classes");
            ArrayList arrayList = new ArrayList(classes.length);
            boolean z = false;
            for (Pair<? extends K, ? extends KClass<?>> pair : classes) {
                arrayList.add(TuplesKt.to(pair.component1(), new KotlinType(pair.component2(), z, 2, (DefaultConstructorMarker) null)));
            }
            appendToMap(key, arrayList);
        }

        public final <K> void invoke_kotlintype_map_from_ktype(Key<Map<K, KotlinType>> key, Pair<? extends K, ? extends KType>... types) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            Intrinsics.checkNotNullParameter(types, "types");
            ArrayList arrayList = new ArrayList(types.length);
            for (Pair<? extends K, ? extends KType> pair : types) {
                arrayList.add(TuplesKt.to(pair.component1(), new KotlinType(pair.component2())));
            }
            appendToMap(key, arrayList);
        }

        public final <K> void invoke_kotlintype_map_from_fqname(Key<Map<K, KotlinType>> key, Pair<? extends K, String>... fqnames) {
            Intrinsics.checkNotNullParameter(key, "<this>");
            Intrinsics.checkNotNullParameter(fqnames, "fqnames");
            ArrayList arrayList = new ArrayList(fqnames.length);
            boolean z = false;
            for (Pair<? extends K, String> pair : fqnames) {
                arrayList.add(TuplesKt.to(pair.component1(), new KotlinType(pair.component2(), z, 2, (DefaultConstructorMarker) null)));
            }
            appendToMap(key, arrayList);
        }
    }
}
