package org.jetbrains.kotlin.backend.jvm;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;

/* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b2\bf\u0018\u00002\u00020\u0001:1\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$%&'()*+,-./012ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u00063À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "ABSTRACT_BRIDGE_STUB", "CLASS_STATIC_INITIALIZER", "COMPANION_PROPERTY_BACKING_FIELD", "CONTINUATION_CLASS", "CONTINUATION_CLASS_RESULT_FIELD", "DEFAULT_IMPLS", "DESERIALIZE_LAMBDA_FUN", "ENUM_CONSTRUCTOR_SYNTHETIC_PARAMETER", "ENUM_MAPPINGS_FOR_ENTRIES", "ENUM_MAPPINGS_FOR_WHEN", "FIELD_FOR_OUTER_THIS", "FIELD_FOR_STATIC_CALLABLE_REFERENCE_INSTANCE", "FOR_INLINE_STATE_MACHINE_TEMPLATE", "FOR_INLINE_STATE_MACHINE_TEMPLATE_CAPTURES_CROSSINLINE", "FUNCTION_REFERENCE_IMPL", "GENERATED_ASSERTION_ENABLED_FIELD", "GENERATED_EXTENDED_MAIN", "GENERATED_MEMBER_IN_CALLABLE_REFERENCE", "GENERATED_MULTI_FIELD_VALUE_CLASS_PARAMETER", "GENERATED_PROPERTY_REFERENCE", "INLINE_CLASS_GENERATED_IMPL_METHOD", "INLINE_LAMBDA", "INTERFACE_COMPANION_PRIVATE_INSTANCE", "INVOKEDYNAMIC_CALL_TARGET", "JVM_OVERLOADS_WRAPPER", "JVM_STATIC_WRAPPER", "LAMBDA_IMPL", "MULTI_FIELD_VALUE_CLASS_GENERATED_IMPL_METHOD", "MULTI_FIELD_VALUE_CLASS_REPRESENTATION_VARIABLE", "OBJECT_SUPER_CONSTRUCTOR_PARAMETER", "POLYMORPHIC_SIGNATURE_INSTANTIATION", "PROXY_FUN_FOR_METAFACTORY", "STATIC_INLINE_CLASS_CONSTRUCTOR", "STATIC_INLINE_CLASS_REPLACEMENT", "STATIC_MULTI_FIELD_VALUE_CLASS_CONSTRUCTOR", "STATIC_MULTI_FIELD_VALUE_CLASS_REPLACEMENT", "SUPER_INTERFACE_METHOD_BRIDGE", "SUSPEND_IMPL_STATIC_FUNCTION", "SUSPEND_LAMBDA", "SYNTHETIC_ACCESSOR", "SYNTHETIC_ACCESSOR_FOR_HIDDEN_CONSTRUCTOR", "SYNTHETIC_INLINE_CLASS_MEMBER", "SYNTHETIC_MARKER_PARAMETER", "SYNTHETIC_METHOD_FOR_PROPERTY_OR_TYPEALIAS_ANNOTATIONS", "SYNTHETIC_MULTI_FIELD_VALUE_CLASS_MEMBER", "SYNTHETIC_PROXY_FUN_FOR_METAFACTORY", "TEMPORARY_MULTI_FIELD_VALUE_CLASS_PARAMETER", "TEMPORARY_MULTI_FIELD_VALUE_CLASS_VARIABLE", "TO_ARRAY", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JvmLoweredDeclarationOrigin extends IrDeclarationOrigin {

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static boolean isSynthetic(JvmLoweredDeclarationOrigin jvmLoweredDeclarationOrigin) {
            return IrDeclarationOrigin.DefaultImpls.isSynthetic(jvmLoweredDeclarationOrigin);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$CLASS_STATIC_INITIALIZER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CLASS_STATIC_INITIALIZER extends IrDeclarationOriginImpl {
        public static final CLASS_STATIC_INITIALIZER INSTANCE = new CLASS_STATIC_INITIALIZER();

        private CLASS_STATIC_INITIALIZER() {
            super("CLASS_STATIC_INITIALIZER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$DEFAULT_IMPLS;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DEFAULT_IMPLS extends IrDeclarationOriginImpl {
        public static final DEFAULT_IMPLS INSTANCE = new DEFAULT_IMPLS();

        private DEFAULT_IMPLS() {
            super("DEFAULT_IMPLS", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$SUPER_INTERFACE_METHOD_BRIDGE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SUPER_INTERFACE_METHOD_BRIDGE extends IrDeclarationOriginImpl {
        public static final SUPER_INTERFACE_METHOD_BRIDGE INSTANCE = new SUPER_INTERFACE_METHOD_BRIDGE();

        private SUPER_INTERFACE_METHOD_BRIDGE() {
            super("SUPER_INTERFACE_METHOD_BRIDGE", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$FIELD_FOR_OUTER_THIS;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FIELD_FOR_OUTER_THIS extends IrDeclarationOriginImpl {
        public static final FIELD_FOR_OUTER_THIS INSTANCE = new FIELD_FOR_OUTER_THIS();

        private FIELD_FOR_OUTER_THIS() {
            super("FIELD_FOR_OUTER_THIS", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$LAMBDA_IMPL;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LAMBDA_IMPL extends IrDeclarationOriginImpl {
        public static final LAMBDA_IMPL INSTANCE = new LAMBDA_IMPL();

        private LAMBDA_IMPL() {
            super("LAMBDA_IMPL", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$FUNCTION_REFERENCE_IMPL;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FUNCTION_REFERENCE_IMPL extends IrDeclarationOriginImpl {
        public static final FUNCTION_REFERENCE_IMPL INSTANCE = new FUNCTION_REFERENCE_IMPL();

        private FUNCTION_REFERENCE_IMPL() {
            super("FUNCTION_REFERENCE_IMPL", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$SYNTHETIC_ACCESSOR;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SYNTHETIC_ACCESSOR extends IrDeclarationOriginImpl {
        public static final SYNTHETIC_ACCESSOR INSTANCE = new SYNTHETIC_ACCESSOR();

        private SYNTHETIC_ACCESSOR() {
            super("SYNTHETIC_ACCESSOR", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$SYNTHETIC_ACCESSOR_FOR_HIDDEN_CONSTRUCTOR;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SYNTHETIC_ACCESSOR_FOR_HIDDEN_CONSTRUCTOR extends IrDeclarationOriginImpl {
        public static final SYNTHETIC_ACCESSOR_FOR_HIDDEN_CONSTRUCTOR INSTANCE = new SYNTHETIC_ACCESSOR_FOR_HIDDEN_CONSTRUCTOR();

        private SYNTHETIC_ACCESSOR_FOR_HIDDEN_CONSTRUCTOR() {
            super("SYNTHETIC_ACCESSOR_FOR_HIDDEN_CONSTRUCTOR", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$SYNTHETIC_MARKER_PARAMETER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SYNTHETIC_MARKER_PARAMETER extends IrDeclarationOriginImpl {
        public static final SYNTHETIC_MARKER_PARAMETER INSTANCE = new SYNTHETIC_MARKER_PARAMETER();

        private SYNTHETIC_MARKER_PARAMETER() {
            super("SYNTHETIC_MARKER_PARAMETER", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$TO_ARRAY;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TO_ARRAY extends IrDeclarationOriginImpl {
        public static final TO_ARRAY INSTANCE = new TO_ARRAY();

        private TO_ARRAY() {
            super("TO_ARRAY", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$JVM_STATIC_WRAPPER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class JVM_STATIC_WRAPPER extends IrDeclarationOriginImpl {
        public static final JVM_STATIC_WRAPPER INSTANCE = new JVM_STATIC_WRAPPER();

        private JVM_STATIC_WRAPPER() {
            super("JVM_STATIC_WRAPPER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$JVM_OVERLOADS_WRAPPER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class JVM_OVERLOADS_WRAPPER extends IrDeclarationOriginImpl {
        public static final JVM_OVERLOADS_WRAPPER INSTANCE = new JVM_OVERLOADS_WRAPPER();

        private JVM_OVERLOADS_WRAPPER() {
            super("JVM_OVERLOADS_WRAPPER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$SYNTHETIC_METHOD_FOR_PROPERTY_OR_TYPEALIAS_ANNOTATIONS;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SYNTHETIC_METHOD_FOR_PROPERTY_OR_TYPEALIAS_ANNOTATIONS extends IrDeclarationOriginImpl {
        public static final SYNTHETIC_METHOD_FOR_PROPERTY_OR_TYPEALIAS_ANNOTATIONS INSTANCE = new SYNTHETIC_METHOD_FOR_PROPERTY_OR_TYPEALIAS_ANNOTATIONS();

        private SYNTHETIC_METHOD_FOR_PROPERTY_OR_TYPEALIAS_ANNOTATIONS() {
            super("SYNTHETIC_METHOD_FOR_PROPERTY_OR_TYPEALIAS_ANNOTATIONS", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$GENERATED_PROPERTY_REFERENCE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GENERATED_PROPERTY_REFERENCE extends IrDeclarationOriginImpl {
        public static final GENERATED_PROPERTY_REFERENCE INSTANCE = new GENERATED_PROPERTY_REFERENCE();

        private GENERATED_PROPERTY_REFERENCE() {
            super("GENERATED_PROPERTY_REFERENCE", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$GENERATED_MEMBER_IN_CALLABLE_REFERENCE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GENERATED_MEMBER_IN_CALLABLE_REFERENCE extends IrDeclarationOriginImpl {
        public static final GENERATED_MEMBER_IN_CALLABLE_REFERENCE INSTANCE = new GENERATED_MEMBER_IN_CALLABLE_REFERENCE();

        private GENERATED_MEMBER_IN_CALLABLE_REFERENCE() {
            super("GENERATED_MEMBER_IN_CALLABLE_REFERENCE", false);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$ENUM_MAPPINGS_FOR_WHEN;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ENUM_MAPPINGS_FOR_WHEN extends IrDeclarationOriginImpl {
        public static final ENUM_MAPPINGS_FOR_WHEN INSTANCE = new ENUM_MAPPINGS_FOR_WHEN();

        private ENUM_MAPPINGS_FOR_WHEN() {
            super("ENUM_MAPPINGS_FOR_WHEN", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$ENUM_MAPPINGS_FOR_ENTRIES;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ENUM_MAPPINGS_FOR_ENTRIES extends IrDeclarationOriginImpl {
        public static final ENUM_MAPPINGS_FOR_ENTRIES INSTANCE = new ENUM_MAPPINGS_FOR_ENTRIES();

        private ENUM_MAPPINGS_FOR_ENTRIES() {
            super("ENUM_MAPPINGS_FOR_ENTRIES", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$SYNTHETIC_INLINE_CLASS_MEMBER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SYNTHETIC_INLINE_CLASS_MEMBER extends IrDeclarationOriginImpl {
        public static final SYNTHETIC_INLINE_CLASS_MEMBER INSTANCE = new SYNTHETIC_INLINE_CLASS_MEMBER();

        private SYNTHETIC_INLINE_CLASS_MEMBER() {
            super("SYNTHETIC_INLINE_CLASS_MEMBER", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$SYNTHETIC_MULTI_FIELD_VALUE_CLASS_MEMBER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SYNTHETIC_MULTI_FIELD_VALUE_CLASS_MEMBER extends IrDeclarationOriginImpl {
        public static final SYNTHETIC_MULTI_FIELD_VALUE_CLASS_MEMBER INSTANCE = new SYNTHETIC_MULTI_FIELD_VALUE_CLASS_MEMBER();

        private SYNTHETIC_MULTI_FIELD_VALUE_CLASS_MEMBER() {
            super("SYNTHETIC_MULTI_FIELD_VALUE_CLASS_MEMBER", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$INLINE_CLASS_GENERATED_IMPL_METHOD;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class INLINE_CLASS_GENERATED_IMPL_METHOD extends IrDeclarationOriginImpl {
        public static final INLINE_CLASS_GENERATED_IMPL_METHOD INSTANCE = new INLINE_CLASS_GENERATED_IMPL_METHOD();

        private INLINE_CLASS_GENERATED_IMPL_METHOD() {
            super("INLINE_CLASS_GENERATED_IMPL_METHOD", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$MULTI_FIELD_VALUE_CLASS_GENERATED_IMPL_METHOD;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MULTI_FIELD_VALUE_CLASS_GENERATED_IMPL_METHOD extends IrDeclarationOriginImpl {
        public static final MULTI_FIELD_VALUE_CLASS_GENERATED_IMPL_METHOD INSTANCE = new MULTI_FIELD_VALUE_CLASS_GENERATED_IMPL_METHOD();

        private MULTI_FIELD_VALUE_CLASS_GENERATED_IMPL_METHOD() {
            super("MULTI_FIELD_VALUE_CLASS_GENERATED_IMPL_METHOD", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$STATIC_INLINE_CLASS_REPLACEMENT;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class STATIC_INLINE_CLASS_REPLACEMENT extends IrDeclarationOriginImpl {
        public static final STATIC_INLINE_CLASS_REPLACEMENT INSTANCE = new STATIC_INLINE_CLASS_REPLACEMENT();

        private STATIC_INLINE_CLASS_REPLACEMENT() {
            super("STATIC_INLINE_CLASS_REPLACEMENT", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$STATIC_MULTI_FIELD_VALUE_CLASS_REPLACEMENT;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class STATIC_MULTI_FIELD_VALUE_CLASS_REPLACEMENT extends IrDeclarationOriginImpl {
        public static final STATIC_MULTI_FIELD_VALUE_CLASS_REPLACEMENT INSTANCE = new STATIC_MULTI_FIELD_VALUE_CLASS_REPLACEMENT();

        private STATIC_MULTI_FIELD_VALUE_CLASS_REPLACEMENT() {
            super("STATIC_MULTI_FIELD_VALUE_CLASS_REPLACEMENT", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$STATIC_INLINE_CLASS_CONSTRUCTOR;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class STATIC_INLINE_CLASS_CONSTRUCTOR extends IrDeclarationOriginImpl {
        public static final STATIC_INLINE_CLASS_CONSTRUCTOR INSTANCE = new STATIC_INLINE_CLASS_CONSTRUCTOR();

        private STATIC_INLINE_CLASS_CONSTRUCTOR() {
            super("STATIC_INLINE_CLASS_CONSTRUCTOR", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$STATIC_MULTI_FIELD_VALUE_CLASS_CONSTRUCTOR;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class STATIC_MULTI_FIELD_VALUE_CLASS_CONSTRUCTOR extends IrDeclarationOriginImpl {
        public static final STATIC_MULTI_FIELD_VALUE_CLASS_CONSTRUCTOR INSTANCE = new STATIC_MULTI_FIELD_VALUE_CLASS_CONSTRUCTOR();

        private STATIC_MULTI_FIELD_VALUE_CLASS_CONSTRUCTOR() {
            super("STATIC_MULTI_FIELD_VALUE_CLASS_CONSTRUCTOR", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$GENERATED_ASSERTION_ENABLED_FIELD;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GENERATED_ASSERTION_ENABLED_FIELD extends IrDeclarationOriginImpl {
        public static final GENERATED_ASSERTION_ENABLED_FIELD INSTANCE = new GENERATED_ASSERTION_ENABLED_FIELD();

        private GENERATED_ASSERTION_ENABLED_FIELD() {
            super("GENERATED_ASSERTION_ENABLED_FIELD", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$GENERATED_MULTI_FIELD_VALUE_CLASS_PARAMETER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GENERATED_MULTI_FIELD_VALUE_CLASS_PARAMETER extends IrDeclarationOriginImpl {
        public static final GENERATED_MULTI_FIELD_VALUE_CLASS_PARAMETER INSTANCE = new GENERATED_MULTI_FIELD_VALUE_CLASS_PARAMETER();

        private GENERATED_MULTI_FIELD_VALUE_CLASS_PARAMETER() {
            super("GENERATED_MULTI_FIELD_VALUE_CLASS_PARAMETER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$TEMPORARY_MULTI_FIELD_VALUE_CLASS_PARAMETER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TEMPORARY_MULTI_FIELD_VALUE_CLASS_PARAMETER extends IrDeclarationOriginImpl {
        public static final TEMPORARY_MULTI_FIELD_VALUE_CLASS_PARAMETER INSTANCE = new TEMPORARY_MULTI_FIELD_VALUE_CLASS_PARAMETER();

        private TEMPORARY_MULTI_FIELD_VALUE_CLASS_PARAMETER() {
            super("TEMPORARY_MULTI_FIELD_VALUE_CLASS_PARAMETER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$TEMPORARY_MULTI_FIELD_VALUE_CLASS_VARIABLE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TEMPORARY_MULTI_FIELD_VALUE_CLASS_VARIABLE extends IrDeclarationOriginImpl {
        public static final TEMPORARY_MULTI_FIELD_VALUE_CLASS_VARIABLE INSTANCE = new TEMPORARY_MULTI_FIELD_VALUE_CLASS_VARIABLE();

        private TEMPORARY_MULTI_FIELD_VALUE_CLASS_VARIABLE() {
            super("TEMPORARY_MULTI_FIELD_VALUE_CLASS_VARIABLE", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$MULTI_FIELD_VALUE_CLASS_REPRESENTATION_VARIABLE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MULTI_FIELD_VALUE_CLASS_REPRESENTATION_VARIABLE extends IrDeclarationOriginImpl {
        public static final MULTI_FIELD_VALUE_CLASS_REPRESENTATION_VARIABLE INSTANCE = new MULTI_FIELD_VALUE_CLASS_REPRESENTATION_VARIABLE();

        private MULTI_FIELD_VALUE_CLASS_REPRESENTATION_VARIABLE() {
            super("MULTI_FIELD_VALUE_CLASS_REPRESENTATION_VARIABLE", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$GENERATED_EXTENDED_MAIN;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GENERATED_EXTENDED_MAIN extends IrDeclarationOriginImpl {
        public static final GENERATED_EXTENDED_MAIN INSTANCE = new GENERATED_EXTENDED_MAIN();

        private GENERATED_EXTENDED_MAIN() {
            super("GENERATED_EXTENDED_MAIN", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$SUSPEND_IMPL_STATIC_FUNCTION;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SUSPEND_IMPL_STATIC_FUNCTION extends IrDeclarationOriginImpl {
        public static final SUSPEND_IMPL_STATIC_FUNCTION INSTANCE = new SUSPEND_IMPL_STATIC_FUNCTION();

        private SUSPEND_IMPL_STATIC_FUNCTION() {
            super("SUSPEND_IMPL_STATIC_FUNCTION", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$INTERFACE_COMPANION_PRIVATE_INSTANCE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class INTERFACE_COMPANION_PRIVATE_INSTANCE extends IrDeclarationOriginImpl {
        public static final INTERFACE_COMPANION_PRIVATE_INSTANCE INSTANCE = new INTERFACE_COMPANION_PRIVATE_INSTANCE();

        private INTERFACE_COMPANION_PRIVATE_INSTANCE() {
            super("INTERFACE_COMPANION_PRIVATE_INSTANCE", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$POLYMORPHIC_SIGNATURE_INSTANTIATION;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class POLYMORPHIC_SIGNATURE_INSTANTIATION extends IrDeclarationOriginImpl {
        public static final POLYMORPHIC_SIGNATURE_INSTANTIATION INSTANCE = new POLYMORPHIC_SIGNATURE_INSTANTIATION();

        private POLYMORPHIC_SIGNATURE_INSTANTIATION() {
            super("POLYMORPHIC_SIGNATURE_INSTANTIATION", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$ENUM_CONSTRUCTOR_SYNTHETIC_PARAMETER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ENUM_CONSTRUCTOR_SYNTHETIC_PARAMETER extends IrDeclarationOriginImpl {
        public static final ENUM_CONSTRUCTOR_SYNTHETIC_PARAMETER INSTANCE = new ENUM_CONSTRUCTOR_SYNTHETIC_PARAMETER();

        private ENUM_CONSTRUCTOR_SYNTHETIC_PARAMETER() {
            super("ENUM_CONSTRUCTOR_SYNTHETIC_PARAMETER", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$OBJECT_SUPER_CONSTRUCTOR_PARAMETER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class OBJECT_SUPER_CONSTRUCTOR_PARAMETER extends IrDeclarationOriginImpl {
        public static final OBJECT_SUPER_CONSTRUCTOR_PARAMETER INSTANCE = new OBJECT_SUPER_CONSTRUCTOR_PARAMETER();

        private OBJECT_SUPER_CONSTRUCTOR_PARAMETER() {
            super("OBJECT_SUPER_CONSTRUCTOR_PARAMETER", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$CONTINUATION_CLASS;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CONTINUATION_CLASS extends IrDeclarationOriginImpl {
        public static final CONTINUATION_CLASS INSTANCE = new CONTINUATION_CLASS();

        private CONTINUATION_CLASS() {
            super("CONTINUATION_CLASS", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$SUSPEND_LAMBDA;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SUSPEND_LAMBDA extends IrDeclarationOriginImpl {
        public static final SUSPEND_LAMBDA INSTANCE = new SUSPEND_LAMBDA();

        private SUSPEND_LAMBDA() {
            super("SUSPEND_LAMBDA", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$FOR_INLINE_STATE_MACHINE_TEMPLATE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FOR_INLINE_STATE_MACHINE_TEMPLATE extends IrDeclarationOriginImpl {
        public static final FOR_INLINE_STATE_MACHINE_TEMPLATE INSTANCE = new FOR_INLINE_STATE_MACHINE_TEMPLATE();

        private FOR_INLINE_STATE_MACHINE_TEMPLATE() {
            super("FOR_INLINE_TEMPLATE", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$FOR_INLINE_STATE_MACHINE_TEMPLATE_CAPTURES_CROSSINLINE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FOR_INLINE_STATE_MACHINE_TEMPLATE_CAPTURES_CROSSINLINE extends IrDeclarationOriginImpl {
        public static final FOR_INLINE_STATE_MACHINE_TEMPLATE_CAPTURES_CROSSINLINE INSTANCE = new FOR_INLINE_STATE_MACHINE_TEMPLATE_CAPTURES_CROSSINLINE();

        private FOR_INLINE_STATE_MACHINE_TEMPLATE_CAPTURES_CROSSINLINE() {
            super("FOR_INLINE_TEMPLATE_CROSSINLINE", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$CONTINUATION_CLASS_RESULT_FIELD;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CONTINUATION_CLASS_RESULT_FIELD extends IrDeclarationOriginImpl {
        public static final CONTINUATION_CLASS_RESULT_FIELD INSTANCE = new CONTINUATION_CLASS_RESULT_FIELD();

        private CONTINUATION_CLASS_RESULT_FIELD() {
            super("CONTINUATION_CLASS_RESULT_FIELD", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$COMPANION_PROPERTY_BACKING_FIELD;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class COMPANION_PROPERTY_BACKING_FIELD extends IrDeclarationOriginImpl {
        public static final COMPANION_PROPERTY_BACKING_FIELD INSTANCE = new COMPANION_PROPERTY_BACKING_FIELD();

        private COMPANION_PROPERTY_BACKING_FIELD() {
            super("COMPANION_PROPERTY_BACKING_FIELD", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$FIELD_FOR_STATIC_CALLABLE_REFERENCE_INSTANCE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FIELD_FOR_STATIC_CALLABLE_REFERENCE_INSTANCE extends IrDeclarationOriginImpl {
        public static final FIELD_FOR_STATIC_CALLABLE_REFERENCE_INSTANCE INSTANCE = new FIELD_FOR_STATIC_CALLABLE_REFERENCE_INSTANCE();

        private FIELD_FOR_STATIC_CALLABLE_REFERENCE_INSTANCE() {
            super("FIELD_FOR_STATIC_CALLABLE_REFERENCE_INSTANCE", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$ABSTRACT_BRIDGE_STUB;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ABSTRACT_BRIDGE_STUB extends IrDeclarationOriginImpl {
        public static final ABSTRACT_BRIDGE_STUB INSTANCE = new ABSTRACT_BRIDGE_STUB();

        private ABSTRACT_BRIDGE_STUB() {
            super("ABSTRACT_BRIDGE_STUB", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$INVOKEDYNAMIC_CALL_TARGET;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class INVOKEDYNAMIC_CALL_TARGET extends IrDeclarationOriginImpl {
        public static final INVOKEDYNAMIC_CALL_TARGET INSTANCE = new INVOKEDYNAMIC_CALL_TARGET();

        private INVOKEDYNAMIC_CALL_TARGET() {
            super("INVOKEDYNAMIC_CALL_TARGET", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$INLINE_LAMBDA;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class INLINE_LAMBDA extends IrDeclarationOriginImpl {
        public static final INLINE_LAMBDA INSTANCE = new INLINE_LAMBDA();

        private INLINE_LAMBDA() {
            super("INLINE_LAMBDA", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$PROXY_FUN_FOR_METAFACTORY;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PROXY_FUN_FOR_METAFACTORY extends IrDeclarationOriginImpl {
        public static final PROXY_FUN_FOR_METAFACTORY INSTANCE = new PROXY_FUN_FOR_METAFACTORY();

        private PROXY_FUN_FOR_METAFACTORY() {
            super("PROXY_FUN_FOR_METAFACTORY", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$SYNTHETIC_PROXY_FUN_FOR_METAFACTORY;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SYNTHETIC_PROXY_FUN_FOR_METAFACTORY extends IrDeclarationOriginImpl {
        public static final SYNTHETIC_PROXY_FUN_FOR_METAFACTORY INSTANCE = new SYNTHETIC_PROXY_FUN_FOR_METAFACTORY();

        private SYNTHETIC_PROXY_FUN_FOR_METAFACTORY() {
            super("SYNTHETIC_PROXY_FUN_FOR_METAFACTORY", true);
        }
    }

    /* JADX INFO: compiled from: JvmLoweredDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmLoweredDeclarationOrigin$DESERIALIZE_LAMBDA_FUN;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DESERIALIZE_LAMBDA_FUN extends IrDeclarationOriginImpl {
        public static final DESERIALIZE_LAMBDA_FUN INSTANCE = new DESERIALIZE_LAMBDA_FUN();

        private DESERIALIZE_LAMBDA_FUN() {
            super("DESERIALIZE_LAMBDA_FUN", true);
        }
    }
}
