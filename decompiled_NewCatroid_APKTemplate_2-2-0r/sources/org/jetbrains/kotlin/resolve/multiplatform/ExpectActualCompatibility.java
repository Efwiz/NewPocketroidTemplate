package org.jetbrains.kotlin.resolve.multiplatform;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ExpectActualCompatibility.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002:\u0002\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0003\u0082\u0001\u0002\u0006\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility;", "D", "", "()V", "Compatible", "Incompatible", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Compatible;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ExpectActualCompatibility<D> {
    public /* synthetic */ ExpectActualCompatibility(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private ExpectActualCompatibility() {
    }

    /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\"\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$%&'()B\u0011\b\u0004\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005R\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\u0002*+¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible;", "D", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility;", "reason", "", "(Ljava/lang/String;)V", "getReason", "()Ljava/lang/String;", "ActualFunctionWithDefaultParameters", "CallableKind", "ClassKind", "ClassModifiers", "ClassScopes", "ClassTypeParameterCount", "ClassTypeParameterUpperBounds", "EnumEntries", "FunInterfaceModifier", "FunctionModifiersDifferent", "FunctionModifiersNotSubset", "FunctionTypeParameterCount", "FunctionTypeParameterUpperBounds", "Modality", "ParameterCount", "ParameterNames", "ParameterShape", "ParameterTypes", "PropertyConstModifier", "PropertyKind", "PropertyLateinitModifier", "PropertySetterVisibility", "ReturnType", "StrongIncompatible", "Supertypes", "TypeParameterNames", "TypeParameterReified", "TypeParameterVariance", "Unknown", "ValueParameterCrossinline", "ValueParameterNoinline", "ValueParameterVararg", "Visibility", "WeakIncompatible", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$StrongIncompatible;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Incompatible<D> extends ExpectActualCompatibility<D> {
        private final String reason;

        public /* synthetic */ Incompatible(String str, DefaultConstructorMarker defaultConstructorMarker) {
            this(str);
        }

        private Incompatible(String str) {
            super(null);
            this.reason = str;
        }

        public final String getReason() {
            return this.reason;
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0006\b\u0002\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0011\b\u0004\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005\u0082\u0001\u0019\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "D", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible;", "reason", "", "(Ljava/lang/String;)V", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ActualFunctionWithDefaultParameters;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ClassKind;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ClassModifiers;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ClassScopes;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ClassTypeParameterCount;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ClassTypeParameterUpperBounds;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$EnumEntries;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$FunInterfaceModifier;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$FunctionModifiersDifferent;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$FunctionModifiersNotSubset;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$Modality;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ParameterNames;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$PropertyConstModifier;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$PropertyKind;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$PropertyLateinitModifier;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$PropertySetterVisibility;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$Supertypes;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$TypeParameterNames;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$TypeParameterReified;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$TypeParameterVariance;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$Unknown;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ValueParameterCrossinline;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ValueParameterNoinline;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ValueParameterVararg;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$Visibility;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static abstract class WeakIncompatible<D> extends Incompatible<D> {
            public /* synthetic */ WeakIncompatible(String str, DefaultConstructorMarker defaultConstructorMarker) {
                this(str);
            }

            private WeakIncompatible(String str) {
                super(str, null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0006\b\u0002\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0011\b\u0004\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005\u0082\u0001\u0007\u0006\u0007\b\t\n\u000b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$StrongIncompatible;", "D", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible;", "reason", "", "(Ljava/lang/String;)V", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$CallableKind;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$FunctionTypeParameterCount;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$FunctionTypeParameterUpperBounds;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ParameterCount;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ParameterShape;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ParameterTypes;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ReturnType;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static abstract class StrongIncompatible<D> extends Incompatible<D> {
            public /* synthetic */ StrongIncompatible(String str, DefaultConstructorMarker defaultConstructorMarker) {
                this(str);
            }

            private StrongIncompatible(String str) {
                super(str, null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$CallableKind;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$StrongIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class CallableKind extends StrongIncompatible {
            public static final CallableKind INSTANCE = new CallableKind();

            private CallableKind() {
                super("callable kinds are different (function vs property)", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ParameterShape;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$StrongIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ParameterShape extends StrongIncompatible {
            public static final ParameterShape INSTANCE = new ParameterShape();

            private ParameterShape() {
                super("parameter shapes are different (extension vs non-extension)", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ParameterCount;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$StrongIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ParameterCount extends StrongIncompatible {
            public static final ParameterCount INSTANCE = new ParameterCount();

            private ParameterCount() {
                super("number of value parameters is different", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$FunctionTypeParameterCount;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$StrongIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class FunctionTypeParameterCount extends StrongIncompatible {
            public static final FunctionTypeParameterCount INSTANCE = new FunctionTypeParameterCount();

            private FunctionTypeParameterCount() {
                super("number of type parameters is different", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ClassTypeParameterCount;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ClassTypeParameterCount extends WeakIncompatible {
            public static final ClassTypeParameterCount INSTANCE = new ClassTypeParameterCount();

            private ClassTypeParameterCount() {
                super(FunctionTypeParameterCount.INSTANCE.getReason(), null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ParameterTypes;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$StrongIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ParameterTypes extends StrongIncompatible {
            public static final ParameterTypes INSTANCE = new ParameterTypes();

            private ParameterTypes() {
                super("parameter types are different", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ReturnType;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$StrongIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ReturnType extends StrongIncompatible {
            public static final ReturnType INSTANCE = new ReturnType();

            private ReturnType() {
                super("return type is different", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ParameterNames;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ParameterNames extends WeakIncompatible {
            public static final ParameterNames INSTANCE = new ParameterNames();

            private ParameterNames() {
                super("parameter names are different", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$TypeParameterNames;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class TypeParameterNames extends WeakIncompatible {
            public static final TypeParameterNames INSTANCE = new TypeParameterNames();

            private TypeParameterNames() {
                super("names of type parameters are different", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ValueParameterVararg;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ValueParameterVararg extends WeakIncompatible {
            public static final ValueParameterVararg INSTANCE = new ValueParameterVararg();

            private ValueParameterVararg() {
                super("some value parameter is vararg in one declaration and non-vararg in the other", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ValueParameterNoinline;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ValueParameterNoinline extends WeakIncompatible {
            public static final ValueParameterNoinline INSTANCE = new ValueParameterNoinline();

            private ValueParameterNoinline() {
                super("some value parameter is noinline in one declaration and not noinline in the other", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ValueParameterCrossinline;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ValueParameterCrossinline extends WeakIncompatible {
            public static final ValueParameterCrossinline INSTANCE = new ValueParameterCrossinline();

            private ValueParameterCrossinline() {
                super("some value parameter is crossinline in one declaration and not crossinline in the other", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$FunctionModifiersDifferent;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class FunctionModifiersDifferent extends WeakIncompatible {
            public static final FunctionModifiersDifferent INSTANCE = new FunctionModifiersDifferent();

            private FunctionModifiersDifferent() {
                super("modifiers are different (suspend)", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$FunctionModifiersNotSubset;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class FunctionModifiersNotSubset extends WeakIncompatible {
            public static final FunctionModifiersNotSubset INSTANCE = new FunctionModifiersNotSubset();

            private FunctionModifiersNotSubset() {
                super("some modifiers on expected declaration are missing on the actual one (infix, inline, operator)", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ActualFunctionWithDefaultParameters;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ActualFunctionWithDefaultParameters extends WeakIncompatible {
            public static final ActualFunctionWithDefaultParameters INSTANCE = new ActualFunctionWithDefaultParameters();

            private ActualFunctionWithDefaultParameters() {
                super("actual function cannot have default argument values, they should be declared in the expected function", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$PropertyKind;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class PropertyKind extends WeakIncompatible {
            public static final PropertyKind INSTANCE = new PropertyKind();

            private PropertyKind() {
                super("property kinds are different (val vs var)", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$PropertyLateinitModifier;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class PropertyLateinitModifier extends WeakIncompatible {
            public static final PropertyLateinitModifier INSTANCE = new PropertyLateinitModifier();

            private PropertyLateinitModifier() {
                super("modifiers are different (lateinit)", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$PropertyConstModifier;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class PropertyConstModifier extends WeakIncompatible {
            public static final PropertyConstModifier INSTANCE = new PropertyConstModifier();

            private PropertyConstModifier() {
                super("modifiers are different (const)", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$PropertySetterVisibility;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class PropertySetterVisibility extends WeakIncompatible {
            public static final PropertySetterVisibility INSTANCE = new PropertySetterVisibility();

            private PropertySetterVisibility() {
                super("setter visibility is different", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ClassKind;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ClassKind extends WeakIncompatible {
            public static final ClassKind INSTANCE = new ClassKind();

            private ClassKind() {
                super("class kinds are different (class, interface, object, enum, annotation)", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ClassModifiers;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ClassModifiers extends WeakIncompatible {
            public static final ClassModifiers INSTANCE = new ClassModifiers();

            private ClassModifiers() {
                super("modifiers are different (companion, inner, inline)", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$FunInterfaceModifier;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class FunInterfaceModifier extends WeakIncompatible {
            public static final FunInterfaceModifier INSTANCE = new FunInterfaceModifier();

            private FunInterfaceModifier() {
                super("actual declaration for fun expect interface is not a functional interface", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$Supertypes;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Supertypes extends WeakIncompatible {
            public static final Supertypes INSTANCE = new Supertypes();

            private Supertypes() {
                super("some supertypes are missing in the actual declaration", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0004\u0018\u0000*\u0004\b\u0002\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B7\u00120\u0010\u0003\u001a,\u0012(\u0012&\u0012\u0004\u0012\u00028\u0002\u0012\u001c\u0012\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\b0\u00060\u00050\u0004¢\u0006\u0002\u0010\tR;\u0010\u0003\u001a,\u0012(\u0012&\u0012\u0004\u0012\u00028\u0002\u0012\u001c\u0012\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\b0\u00060\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ClassScopes;", "D", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "unfulfilled", "", "Lkotlin/Pair;", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible;", "", "(Ljava/util/List;)V", "getUnfulfilled", "()Ljava/util/List;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ClassScopes<D> extends WeakIncompatible<D> {
            private final List<Pair<D, Map<Incompatible<D>, Collection<D>>>> unfulfilled;

            public final List<Pair<D, Map<Incompatible<D>, Collection<D>>>> getUnfulfilled() {
                return this.unfulfilled;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            public ClassScopes(List<? extends Pair<? extends D, ? extends Map<Incompatible<D>, ? extends Collection<? extends D>>>> unfulfilled) {
                super("some expected members have no actual ones", null);
                Intrinsics.checkNotNullParameter(unfulfilled, "unfulfilled");
                this.unfulfilled = unfulfilled;
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$EnumEntries;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class EnumEntries extends WeakIncompatible {
            public static final EnumEntries INSTANCE = new EnumEntries();

            private EnumEntries() {
                super("some entries from expected enum are missing in the actual enum", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$Modality;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Modality extends WeakIncompatible {
            public static final Modality INSTANCE = new Modality();

            private Modality() {
                super("modality is different", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$Visibility;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Visibility extends WeakIncompatible {
            public static final Visibility INSTANCE = new Visibility();

            private Visibility() {
                super("visibility is different", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$FunctionTypeParameterUpperBounds;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$StrongIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class FunctionTypeParameterUpperBounds extends StrongIncompatible {
            public static final FunctionTypeParameterUpperBounds INSTANCE = new FunctionTypeParameterUpperBounds();

            private FunctionTypeParameterUpperBounds() {
                super("upper bounds of type parameters are different", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$ClassTypeParameterUpperBounds;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ClassTypeParameterUpperBounds extends WeakIncompatible {
            public static final ClassTypeParameterUpperBounds INSTANCE = new ClassTypeParameterUpperBounds();

            private ClassTypeParameterUpperBounds() {
                super(FunctionTypeParameterUpperBounds.INSTANCE.getReason(), null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$TypeParameterVariance;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class TypeParameterVariance extends WeakIncompatible {
            public static final TypeParameterVariance INSTANCE = new TypeParameterVariance();

            private TypeParameterVariance() {
                super("declaration-site variances of type parameters are different", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$TypeParameterReified;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class TypeParameterReified extends WeakIncompatible {
            public static final TypeParameterReified INSTANCE = new TypeParameterReified();

            private TypeParameterReified() {
                super("some type parameter is reified in one declaration and non-reified in the other", null);
            }
        }

        /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$Unknown;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Incompatible$WeakIncompatible;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Unknown extends WeakIncompatible {
            public static final Unknown INSTANCE = new Unknown();

            /* JADX WARN: Multi-variable type inference failed */
            private Unknown() {
                super(null, 0 == true ? 1 : 0);
            }
        }
    }

    /* JADX INFO: compiled from: ExpectActualCompatibility.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility$Compatible;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility;", "", "()V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Compatible extends ExpectActualCompatibility {
        public static final Compatible INSTANCE = new Compatible();

        private Compatible() {
            super(null);
        }
    }
}
