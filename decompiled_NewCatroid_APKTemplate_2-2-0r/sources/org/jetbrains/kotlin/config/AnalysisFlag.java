package org.jetbrains.kotlin.config;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.chunk.SpecBlock;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KProperty;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: AnalysisFlag.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002:\u0002\u0010\u0011B\u0017\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00028\u0000¢\u0006\u0002\u0010\u0006J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0002H\u0096\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0004H\u0016R\u0013\u0010\u0005\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\t\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/config/AnalysisFlag;", ExifInterface.GPS_DIRECTION_TRUE, "", "name", "", "defaultValue", "(Ljava/lang/String;Ljava/lang/Object;)V", "getDefaultValue", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "Delegate", "Delegates", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnalysisFlag<T> {
    private final T defaultValue;
    private final String name;

    public AnalysisFlag(String name2, T t) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.name = name2;
        this.defaultValue = t;
    }

    public final T getDefaultValue() {
        return this.defaultValue;
    }

    public boolean equals(Object other) {
        return (other instanceof AnalysisFlag) && Intrinsics.areEqual(((AnalysisFlag) other).name, this.name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    /* JADX INFO: renamed from: toString, reason: from getter */
    public String getName() {
        return this.name;
    }

    /* JADX INFO: compiled from: AnalysisFlag.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00040\u0002B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00028\u0001¢\u0006\u0002\u0010\bJ%\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u00042\b\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\u0010\f\u001a\u0006\u0012\u0002\b\u00030\rH\u0096\u0002R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/config/AnalysisFlag$Delegate;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/properties/ReadOnlyProperty;", "", "Lorg/jetbrains/kotlin/config/AnalysisFlag;", "name", "", "defaultValue", "(Ljava/lang/String;Ljava/lang/Object;)V", SpecBlock.NAME_flag, "getValue", "thisRef", "property", "Lkotlin/reflect/KProperty;", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Delegate<T> implements ReadOnlyProperty<Object, AnalysisFlag<? extends T>> {
        private final AnalysisFlag<T> flag;

        public Delegate(String name2, T t) {
            Intrinsics.checkNotNullParameter(name2, "name");
            this.flag = new AnalysisFlag<>(name2, t);
        }

        @Override // kotlin.properties.ReadOnlyProperty
        public /* bridge */ /* synthetic */ Object getValue(Object obj, KProperty kProperty) {
            return getValue(obj, (KProperty<?>) kProperty);
        }

        @Override // kotlin.properties.ReadOnlyProperty
        public AnalysisFlag<T> getValue(Object thisRef, KProperty<?> property) {
            Intrinsics.checkNotNullParameter(property, "property");
            return this.flag;
        }
    }

    /* JADX INFO: compiled from: AnalysisFlag.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/config/AnalysisFlag$Delegates;", "", "()V", "ApiModeDisabledByDefault", "Boolean", "ListOfStrings", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Delegates {
        public static final Delegates INSTANCE = new Delegates();

        /* JADX INFO: compiled from: AnalysisFlag.kt */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0086\u0002¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/config/AnalysisFlag$Delegates$Boolean;", "", "()V", "provideDelegate", "Lorg/jetbrains/kotlin/config/AnalysisFlag$Delegate;", "", "instance", "property", "Lkotlin/reflect/KProperty;", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Boolean {
            public static final Boolean INSTANCE = new Boolean();

            private Boolean() {
            }

            public final Delegate<java.lang.Boolean> provideDelegate(Object instance, KProperty<?> property) {
                Intrinsics.checkNotNullParameter(property, "property");
                return new Delegate<>(property.getName(), false);
            }
        }

        private Delegates() {
        }

        /* JADX INFO: compiled from: AnalysisFlag.kt */
        @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u00012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\bH\u0086\u0002¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/config/AnalysisFlag$Delegates$ApiModeDisabledByDefault;", "", "()V", "provideDelegate", "Lorg/jetbrains/kotlin/config/AnalysisFlag$Delegate;", "Lorg/jetbrains/kotlin/config/ExplicitApiMode;", "instance", "property", "Lkotlin/reflect/KProperty;", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ApiModeDisabledByDefault {
            public static final ApiModeDisabledByDefault INSTANCE = new ApiModeDisabledByDefault();

            private ApiModeDisabledByDefault() {
            }

            public final Delegate<ExplicitApiMode> provideDelegate(Object instance, KProperty<?> property) {
                Intrinsics.checkNotNullParameter(property, "property");
                return new Delegate<>(property.getName(), ExplicitApiMode.DISABLED);
            }
        }

        /* JADX INFO: compiled from: AnalysisFlag.kt */
        @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J+\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\u00012\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\tH\u0086\u0002¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/config/AnalysisFlag$Delegates$ListOfStrings;", "", "()V", "provideDelegate", "Lorg/jetbrains/kotlin/config/AnalysisFlag$Delegate;", "", "", "instance", "property", "Lkotlin/reflect/KProperty;", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ListOfStrings {
            public static final ListOfStrings INSTANCE = new ListOfStrings();

            private ListOfStrings() {
            }

            public final Delegate<List<String>> provideDelegate(Object instance, KProperty<?> property) {
                Intrinsics.checkNotNullParameter(property, "property");
                return new Delegate<>(property.getName(), CollectionsKt.emptyList());
            }
        }
    }
}
