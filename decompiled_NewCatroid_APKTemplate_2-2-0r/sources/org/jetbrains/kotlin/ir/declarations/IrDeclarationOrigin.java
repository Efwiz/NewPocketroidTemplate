package org.jetbrains.kotlin.ir.declarations;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.GeneratedDeclarationKey;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IrDeclarationOrigin.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\bJ\bf\u0018\u00002\u00020\u0001:G\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOR\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004R\u0012\u0010\u0005\u001a\u00020\u0006X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006P"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "", "isSynthetic", "", "()Z", "name", "", "getName", "()Ljava/lang/String;", "ADAPTER_FOR_CALLABLE_REFERENCE", "ADAPTER_FOR_FUN_INTERFACE_CONSTRUCTOR", "ADAPTER_FOR_SUSPEND_CONVERSION", "ADAPTER_PARAMETER_FOR_CALLABLE_REFERENCE", "ADAPTER_PARAMETER_FOR_SUSPEND_CONVERSION", "BRIDGE", "BRIDGE_SPECIAL", "CATCH_PARAMETER", "CONTINUATION", "DEFAULT_CONSTRUCTOR_MARKER", "DEFAULT_PROPERTY_ACCESSOR", "DEFINED", "DELEGATE", "DELEGATED_MEMBER", "DELEGATED_PROPERTY_ACCESSOR", "DESTRUCTURED_OBJECT_PARAMETER", "ENUM_CLASS_SPECIAL_MEMBER", "ERROR_CLASS", "FAKE_OVERRIDE", "FIELD_FOR_CLASS_CONTEXT_RECEIVER", "FIELD_FOR_ENUM_ENTRIES", "FIELD_FOR_ENUM_ENTRY", "FIELD_FOR_ENUM_VALUES", "FIELD_FOR_OBJECT_INSTANCE", "FIELD_FOR_OUTER_THIS", "FILE_CLASS", "FOR_LOOP_IMPLICIT_VARIABLE", "FOR_LOOP_ITERATOR", "FOR_LOOP_VARIABLE", "FUNCTION_FOR_DEFAULT_PARAMETER", "GENERATED_DATA_CLASS_MEMBER", "GENERATED_MULTI_FIELD_VALUE_CLASS_MEMBER", "GENERATED_SAM_IMPLEMENTATION", "GENERATED_SETTER_GETTER", "GENERATED_SINGLE_FIELD_VALUE_CLASS_MEMBER", "GeneratedByPlugin", "INSTANCE_RECEIVER", "IR_BUILTINS_STUB", "IR_EXTERNAL_DECLARATION_STUB", "IR_EXTERNAL_JAVA_DECLARATION_STUB", "IR_TEMPORARY_VARIABLE", "IR_TEMPORARY_VARIABLE_FOR_INLINED_EXTENSION_RECEIVER", "IR_TEMPORARY_VARIABLE_FOR_INLINED_PARAMETER", "JVM_MULTIFILE_CLASS", "LOCAL_FUNCTION", "LOCAL_FUNCTION_FOR_LAMBDA", "LOWERED_SUSPEND_FUNCTION", "MASK_FOR_DEFAULT_FUNCTION", "METHOD_HANDLER_IN_DEFAULT_FUNCTION", "MOVED_CONTEXT_RECEIVER", "MOVED_DISPATCH_RECEIVER", "MOVED_EXTENSION_RECEIVER", "PRIMARY_CONSTRUCTOR_PARAMETER", "PROPERTY_BACKING_FIELD", "PROPERTY_DELEGATE", "PROPERTY_FOR_ENUM_ENTRIES", "SCRIPT_CALL_PARAMETER", "SCRIPT_CLASS", "SCRIPT_EARLIER_SCRIPTS", "SCRIPT_IMPLICIT_RECEIVER", "SCRIPT_PROVIDED_PROPERTY", "SCRIPT_RESULT_PROPERTY", "SCRIPT_STATEMENT", "SCRIPT_THIS_RECEIVER", "SHARED_VARIABLE_IN_EVALUATOR_FRAGMENT", "SYNTHETIC_FILE_CLASS", "SYNTHETIC_GENERATED_SAM_IMPLEMENTATION", "SYNTHETIC_HELPER_FOR_ENUM_ENTRIES", "SYNTHETIC_HELPER_FOR_ENUM_VALUES", "SYNTHETIC_JAVA_PROPERTY_DELEGATE", "UNDERSCORE_PARAMETER", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrDeclarationOrigin {

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static boolean isSynthetic(IrDeclarationOrigin irDeclarationOrigin) {
            return false;
        }
    }

    String getName();

    /* JADX INFO: renamed from: isSynthetic */
    boolean getIsSynthetic();

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$DEFINED;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DEFINED extends IrDeclarationOriginImpl {
        public static final DEFINED INSTANCE = new DEFINED();

        private DEFINED() {
            super("DEFINED", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$FAKE_OVERRIDE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FAKE_OVERRIDE extends IrDeclarationOriginImpl {
        public static final FAKE_OVERRIDE INSTANCE = new FAKE_OVERRIDE();

        private FAKE_OVERRIDE() {
            super("FAKE_OVERRIDE", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$FOR_LOOP_ITERATOR;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FOR_LOOP_ITERATOR extends IrDeclarationOriginImpl {
        public static final FOR_LOOP_ITERATOR INSTANCE = new FOR_LOOP_ITERATOR();

        private FOR_LOOP_ITERATOR() {
            super("FOR_LOOP_ITERATOR", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$FOR_LOOP_VARIABLE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FOR_LOOP_VARIABLE extends IrDeclarationOriginImpl {
        public static final FOR_LOOP_VARIABLE INSTANCE = new FOR_LOOP_VARIABLE();

        private FOR_LOOP_VARIABLE() {
            super("FOR_LOOP_VARIABLE", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$FOR_LOOP_IMPLICIT_VARIABLE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FOR_LOOP_IMPLICIT_VARIABLE extends IrDeclarationOriginImpl {
        public static final FOR_LOOP_IMPLICIT_VARIABLE INSTANCE = new FOR_LOOP_IMPLICIT_VARIABLE();

        private FOR_LOOP_IMPLICIT_VARIABLE() {
            super("FOR_LOOP_IMPLICIT_VARIABLE", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$PROPERTY_BACKING_FIELD;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PROPERTY_BACKING_FIELD extends IrDeclarationOriginImpl {
        public static final PROPERTY_BACKING_FIELD INSTANCE = new PROPERTY_BACKING_FIELD();

        private PROPERTY_BACKING_FIELD() {
            super("PROPERTY_BACKING_FIELD", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$DEFAULT_PROPERTY_ACCESSOR;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DEFAULT_PROPERTY_ACCESSOR extends IrDeclarationOriginImpl {
        public static final DEFAULT_PROPERTY_ACCESSOR INSTANCE = new DEFAULT_PROPERTY_ACCESSOR();

        private DEFAULT_PROPERTY_ACCESSOR() {
            super("DEFAULT_PROPERTY_ACCESSOR", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$DELEGATE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DELEGATE extends IrDeclarationOriginImpl {
        public static final DELEGATE INSTANCE = new DELEGATE();

        private DELEGATE() {
            super("DELEGATE", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$PROPERTY_DELEGATE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PROPERTY_DELEGATE extends IrDeclarationOriginImpl {
        public static final PROPERTY_DELEGATE INSTANCE = new PROPERTY_DELEGATE();

        private PROPERTY_DELEGATE() {
            super("PROPERTY_DELEGATE", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$DELEGATED_PROPERTY_ACCESSOR;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DELEGATED_PROPERTY_ACCESSOR extends IrDeclarationOriginImpl {
        public static final DELEGATED_PROPERTY_ACCESSOR INSTANCE = new DELEGATED_PROPERTY_ACCESSOR();

        private DELEGATED_PROPERTY_ACCESSOR() {
            super("DELEGATED_PROPERTY_ACCESSOR", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$DELEGATED_MEMBER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DELEGATED_MEMBER extends IrDeclarationOriginImpl {
        public static final DELEGATED_MEMBER INSTANCE = new DELEGATED_MEMBER();

        private DELEGATED_MEMBER() {
            super("DELEGATED_MEMBER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$ENUM_CLASS_SPECIAL_MEMBER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ENUM_CLASS_SPECIAL_MEMBER extends IrDeclarationOriginImpl {
        public static final ENUM_CLASS_SPECIAL_MEMBER INSTANCE = new ENUM_CLASS_SPECIAL_MEMBER();

        private ENUM_CLASS_SPECIAL_MEMBER() {
            super("ENUM_CLASS_SPECIAL_MEMBER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$FUNCTION_FOR_DEFAULT_PARAMETER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FUNCTION_FOR_DEFAULT_PARAMETER extends IrDeclarationOriginImpl {
        public static final FUNCTION_FOR_DEFAULT_PARAMETER INSTANCE = new FUNCTION_FOR_DEFAULT_PARAMETER();

        private FUNCTION_FOR_DEFAULT_PARAMETER() {
            super("FUNCTION_FOR_DEFAULT_PARAMETER", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$MASK_FOR_DEFAULT_FUNCTION;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MASK_FOR_DEFAULT_FUNCTION extends IrDeclarationOriginImpl {
        public static final MASK_FOR_DEFAULT_FUNCTION INSTANCE = new MASK_FOR_DEFAULT_FUNCTION();

        private MASK_FOR_DEFAULT_FUNCTION() {
            super("MASK_FOR_DEFAULT_FUNCTION", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$DEFAULT_CONSTRUCTOR_MARKER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DEFAULT_CONSTRUCTOR_MARKER extends IrDeclarationOriginImpl {
        public static final DEFAULT_CONSTRUCTOR_MARKER INSTANCE = new DEFAULT_CONSTRUCTOR_MARKER();

        private DEFAULT_CONSTRUCTOR_MARKER() {
            super("DEFAULT_CONSTRUCTOR_MARKER", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$METHOD_HANDLER_IN_DEFAULT_FUNCTION;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class METHOD_HANDLER_IN_DEFAULT_FUNCTION extends IrDeclarationOriginImpl {
        public static final METHOD_HANDLER_IN_DEFAULT_FUNCTION INSTANCE = new METHOD_HANDLER_IN_DEFAULT_FUNCTION();

        private METHOD_HANDLER_IN_DEFAULT_FUNCTION() {
            super("METHOD_HANDLER_IN_DEFAULT_FUNCTION", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$MOVED_DISPATCH_RECEIVER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MOVED_DISPATCH_RECEIVER extends IrDeclarationOriginImpl {
        public static final MOVED_DISPATCH_RECEIVER INSTANCE = new MOVED_DISPATCH_RECEIVER();

        private MOVED_DISPATCH_RECEIVER() {
            super("MOVED_DISPATCH_RECEIVER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$MOVED_EXTENSION_RECEIVER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MOVED_EXTENSION_RECEIVER extends IrDeclarationOriginImpl {
        public static final MOVED_EXTENSION_RECEIVER INSTANCE = new MOVED_EXTENSION_RECEIVER();

        private MOVED_EXTENSION_RECEIVER() {
            super("MOVED_EXTENSION_RECEIVER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$MOVED_CONTEXT_RECEIVER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MOVED_CONTEXT_RECEIVER extends IrDeclarationOriginImpl {
        public static final MOVED_CONTEXT_RECEIVER INSTANCE = new MOVED_CONTEXT_RECEIVER();

        private MOVED_CONTEXT_RECEIVER() {
            super("MOVED_CONTEXT_RECEIVER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$FILE_CLASS;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FILE_CLASS extends IrDeclarationOriginImpl {
        public static final FILE_CLASS INSTANCE = new FILE_CLASS();

        private FILE_CLASS() {
            super("FILE_CLASS", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$SYNTHETIC_FILE_CLASS;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SYNTHETIC_FILE_CLASS extends IrDeclarationOriginImpl {
        public static final SYNTHETIC_FILE_CLASS INSTANCE = new SYNTHETIC_FILE_CLASS();

        private SYNTHETIC_FILE_CLASS() {
            super("SYNTHETIC_FILE_CLASS", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$JVM_MULTIFILE_CLASS;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class JVM_MULTIFILE_CLASS extends IrDeclarationOriginImpl {
        public static final JVM_MULTIFILE_CLASS INSTANCE = new JVM_MULTIFILE_CLASS();

        private JVM_MULTIFILE_CLASS() {
            super("JVM_MULTIFILE_CLASS", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$ERROR_CLASS;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ERROR_CLASS extends IrDeclarationOriginImpl {
        public static final ERROR_CLASS INSTANCE = new ERROR_CLASS();

        private ERROR_CLASS() {
            super("ERROR_CLASS", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$SCRIPT_CLASS;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SCRIPT_CLASS extends IrDeclarationOriginImpl {
        public static final SCRIPT_CLASS INSTANCE = new SCRIPT_CLASS();

        private SCRIPT_CLASS() {
            super("SCRIPT_CLASS", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$SCRIPT_THIS_RECEIVER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SCRIPT_THIS_RECEIVER extends IrDeclarationOriginImpl {
        public static final SCRIPT_THIS_RECEIVER INSTANCE = new SCRIPT_THIS_RECEIVER();

        private SCRIPT_THIS_RECEIVER() {
            super("SCRIPT_THIS_RECEIVER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$SCRIPT_STATEMENT;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SCRIPT_STATEMENT extends IrDeclarationOriginImpl {
        public static final SCRIPT_STATEMENT INSTANCE = new SCRIPT_STATEMENT();

        private SCRIPT_STATEMENT() {
            super("SCRIPT_STATEMENT", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$SCRIPT_EARLIER_SCRIPTS;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SCRIPT_EARLIER_SCRIPTS extends IrDeclarationOriginImpl {
        public static final SCRIPT_EARLIER_SCRIPTS INSTANCE = new SCRIPT_EARLIER_SCRIPTS();

        private SCRIPT_EARLIER_SCRIPTS() {
            super("SCRIPT_EARLIER_SCRIPTS", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$SCRIPT_CALL_PARAMETER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SCRIPT_CALL_PARAMETER extends IrDeclarationOriginImpl {
        public static final SCRIPT_CALL_PARAMETER INSTANCE = new SCRIPT_CALL_PARAMETER();

        private SCRIPT_CALL_PARAMETER() {
            super("SCRIPT_CALL_PARAMETER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$SCRIPT_IMPLICIT_RECEIVER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SCRIPT_IMPLICIT_RECEIVER extends IrDeclarationOriginImpl {
        public static final SCRIPT_IMPLICIT_RECEIVER INSTANCE = new SCRIPT_IMPLICIT_RECEIVER();

        private SCRIPT_IMPLICIT_RECEIVER() {
            super("SCRIPT_IMPLICIT_RECEIVER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$SCRIPT_PROVIDED_PROPERTY;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SCRIPT_PROVIDED_PROPERTY extends IrDeclarationOriginImpl {
        public static final SCRIPT_PROVIDED_PROPERTY INSTANCE = new SCRIPT_PROVIDED_PROPERTY();

        private SCRIPT_PROVIDED_PROPERTY() {
            super("SCRIPT_PROVIDED_PROPERTY", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$SCRIPT_RESULT_PROPERTY;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SCRIPT_RESULT_PROPERTY extends IrDeclarationOriginImpl {
        public static final SCRIPT_RESULT_PROPERTY INSTANCE = new SCRIPT_RESULT_PROPERTY();

        private SCRIPT_RESULT_PROPERTY() {
            super("SCRIPT_RESULT_PROPERTY", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$GENERATED_DATA_CLASS_MEMBER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GENERATED_DATA_CLASS_MEMBER extends IrDeclarationOriginImpl {
        public static final GENERATED_DATA_CLASS_MEMBER INSTANCE = new GENERATED_DATA_CLASS_MEMBER();

        private GENERATED_DATA_CLASS_MEMBER() {
            super("GENERATED_DATA_CLASS_MEMBER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$GENERATED_SINGLE_FIELD_VALUE_CLASS_MEMBER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GENERATED_SINGLE_FIELD_VALUE_CLASS_MEMBER extends IrDeclarationOriginImpl {
        public static final GENERATED_SINGLE_FIELD_VALUE_CLASS_MEMBER INSTANCE = new GENERATED_SINGLE_FIELD_VALUE_CLASS_MEMBER();

        private GENERATED_SINGLE_FIELD_VALUE_CLASS_MEMBER() {
            super("GENERATED_SINGLE_FIELD_VALUE_CLASS_MEMBER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$GENERATED_MULTI_FIELD_VALUE_CLASS_MEMBER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GENERATED_MULTI_FIELD_VALUE_CLASS_MEMBER extends IrDeclarationOriginImpl {
        public static final GENERATED_MULTI_FIELD_VALUE_CLASS_MEMBER INSTANCE = new GENERATED_MULTI_FIELD_VALUE_CLASS_MEMBER();

        private GENERATED_MULTI_FIELD_VALUE_CLASS_MEMBER() {
            super("GENERATED_MULTI_FIELD_VALUE_CLASS_MEMBER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$LOCAL_FUNCTION;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LOCAL_FUNCTION extends IrDeclarationOriginImpl {
        public static final LOCAL_FUNCTION INSTANCE = new LOCAL_FUNCTION();

        private LOCAL_FUNCTION() {
            super("LOCAL_FUNCTION", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$LOCAL_FUNCTION_FOR_LAMBDA;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LOCAL_FUNCTION_FOR_LAMBDA extends IrDeclarationOriginImpl {
        public static final LOCAL_FUNCTION_FOR_LAMBDA INSTANCE = new LOCAL_FUNCTION_FOR_LAMBDA();

        private LOCAL_FUNCTION_FOR_LAMBDA() {
            super("LOCAL_FUNCTION_FOR_LAMBDA", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$CATCH_PARAMETER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CATCH_PARAMETER extends IrDeclarationOriginImpl {
        public static final CATCH_PARAMETER INSTANCE = new CATCH_PARAMETER();

        private CATCH_PARAMETER() {
            super("CATCH_PARAMETER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$UNDERSCORE_PARAMETER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UNDERSCORE_PARAMETER extends IrDeclarationOriginImpl {
        public static final UNDERSCORE_PARAMETER INSTANCE = new UNDERSCORE_PARAMETER();

        private UNDERSCORE_PARAMETER() {
            super("UNDERSCORE_PARAMETER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$DESTRUCTURED_OBJECT_PARAMETER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DESTRUCTURED_OBJECT_PARAMETER extends IrDeclarationOriginImpl {
        public static final DESTRUCTURED_OBJECT_PARAMETER INSTANCE = new DESTRUCTURED_OBJECT_PARAMETER();

        private DESTRUCTURED_OBJECT_PARAMETER() {
            super("DESTRUCTURED_OBJECT_PARAMETER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$INSTANCE_RECEIVER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class INSTANCE_RECEIVER extends IrDeclarationOriginImpl {
        public static final INSTANCE_RECEIVER INSTANCE = new INSTANCE_RECEIVER();

        private INSTANCE_RECEIVER() {
            super("INSTANCE_RECEIVER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$PRIMARY_CONSTRUCTOR_PARAMETER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PRIMARY_CONSTRUCTOR_PARAMETER extends IrDeclarationOriginImpl {
        public static final PRIMARY_CONSTRUCTOR_PARAMETER INSTANCE = new PRIMARY_CONSTRUCTOR_PARAMETER();

        private PRIMARY_CONSTRUCTOR_PARAMETER() {
            super("PRIMARY_CONSTRUCTOR_PARAMETER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$IR_TEMPORARY_VARIABLE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IR_TEMPORARY_VARIABLE extends IrDeclarationOriginImpl {
        public static final IR_TEMPORARY_VARIABLE INSTANCE = new IR_TEMPORARY_VARIABLE();

        private IR_TEMPORARY_VARIABLE() {
            super("IR_TEMPORARY_VARIABLE", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$IR_TEMPORARY_VARIABLE_FOR_INLINED_PARAMETER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IR_TEMPORARY_VARIABLE_FOR_INLINED_PARAMETER extends IrDeclarationOriginImpl {
        public static final IR_TEMPORARY_VARIABLE_FOR_INLINED_PARAMETER INSTANCE = new IR_TEMPORARY_VARIABLE_FOR_INLINED_PARAMETER();

        private IR_TEMPORARY_VARIABLE_FOR_INLINED_PARAMETER() {
            super("IR_TEMPORARY_VARIABLE_FOR_INLINED_PARAMETER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$IR_TEMPORARY_VARIABLE_FOR_INLINED_EXTENSION_RECEIVER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IR_TEMPORARY_VARIABLE_FOR_INLINED_EXTENSION_RECEIVER extends IrDeclarationOriginImpl {
        public static final IR_TEMPORARY_VARIABLE_FOR_INLINED_EXTENSION_RECEIVER INSTANCE = new IR_TEMPORARY_VARIABLE_FOR_INLINED_EXTENSION_RECEIVER();

        private IR_TEMPORARY_VARIABLE_FOR_INLINED_EXTENSION_RECEIVER() {
            super("IR_TEMPORARY_VARIABLE_FOR_INLINED_EXTENSION_RECEIVER", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$IR_EXTERNAL_DECLARATION_STUB;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IR_EXTERNAL_DECLARATION_STUB extends IrDeclarationOriginImpl {
        public static final IR_EXTERNAL_DECLARATION_STUB INSTANCE = new IR_EXTERNAL_DECLARATION_STUB();

        private IR_EXTERNAL_DECLARATION_STUB() {
            super("IR_EXTERNAL_DECLARATION_STUB", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$IR_EXTERNAL_JAVA_DECLARATION_STUB;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IR_EXTERNAL_JAVA_DECLARATION_STUB extends IrDeclarationOriginImpl {
        public static final IR_EXTERNAL_JAVA_DECLARATION_STUB INSTANCE = new IR_EXTERNAL_JAVA_DECLARATION_STUB();

        private IR_EXTERNAL_JAVA_DECLARATION_STUB() {
            super("IR_EXTERNAL_JAVA_DECLARATION_STUB", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$IR_BUILTINS_STUB;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IR_BUILTINS_STUB extends IrDeclarationOriginImpl {
        public static final IR_BUILTINS_STUB INSTANCE = new IR_BUILTINS_STUB();

        private IR_BUILTINS_STUB() {
            super("IR_BUILTINS_STUB", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$BRIDGE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class BRIDGE extends IrDeclarationOriginImpl {
        public static final BRIDGE INSTANCE = new BRIDGE();

        private BRIDGE() {
            super("BRIDGE", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$BRIDGE_SPECIAL;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class BRIDGE_SPECIAL extends IrDeclarationOriginImpl {
        public static final BRIDGE_SPECIAL INSTANCE = new BRIDGE_SPECIAL();

        private BRIDGE_SPECIAL() {
            super("BRIDGE_SPECIAL", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$GENERATED_SETTER_GETTER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GENERATED_SETTER_GETTER extends IrDeclarationOriginImpl {
        public static final GENERATED_SETTER_GETTER INSTANCE = new GENERATED_SETTER_GETTER();

        private GENERATED_SETTER_GETTER() {
            super("GENERATED_SETTER_GETTER", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$FIELD_FOR_ENUM_ENTRY;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FIELD_FOR_ENUM_ENTRY extends IrDeclarationOriginImpl {
        public static final FIELD_FOR_ENUM_ENTRY INSTANCE = new FIELD_FOR_ENUM_ENTRY();

        private FIELD_FOR_ENUM_ENTRY() {
            super("FIELD_FOR_ENUM_ENTRY", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$SYNTHETIC_HELPER_FOR_ENUM_VALUES;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SYNTHETIC_HELPER_FOR_ENUM_VALUES extends IrDeclarationOriginImpl {
        public static final SYNTHETIC_HELPER_FOR_ENUM_VALUES INSTANCE = new SYNTHETIC_HELPER_FOR_ENUM_VALUES();

        private SYNTHETIC_HELPER_FOR_ENUM_VALUES() {
            super("SYNTHETIC_HELPER_FOR_ENUM_VALUES", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$SYNTHETIC_HELPER_FOR_ENUM_ENTRIES;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SYNTHETIC_HELPER_FOR_ENUM_ENTRIES extends IrDeclarationOriginImpl {
        public static final SYNTHETIC_HELPER_FOR_ENUM_ENTRIES INSTANCE = new SYNTHETIC_HELPER_FOR_ENUM_ENTRIES();

        private SYNTHETIC_HELPER_FOR_ENUM_ENTRIES() {
            super("SYNTHETIC_HELPER_FOR_ENUM_ENTRIES", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$FIELD_FOR_ENUM_VALUES;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FIELD_FOR_ENUM_VALUES extends IrDeclarationOriginImpl {
        public static final FIELD_FOR_ENUM_VALUES INSTANCE = new FIELD_FOR_ENUM_VALUES();

        private FIELD_FOR_ENUM_VALUES() {
            super("FIELD_FOR_ENUM_VALUES", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$FIELD_FOR_ENUM_ENTRIES;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FIELD_FOR_ENUM_ENTRIES extends IrDeclarationOriginImpl {
        public static final FIELD_FOR_ENUM_ENTRIES INSTANCE = new FIELD_FOR_ENUM_ENTRIES();

        private FIELD_FOR_ENUM_ENTRIES() {
            super("FIELD_FOR_ENUM_ENTRIES", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$PROPERTY_FOR_ENUM_ENTRIES;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PROPERTY_FOR_ENUM_ENTRIES extends IrDeclarationOriginImpl {
        public static final PROPERTY_FOR_ENUM_ENTRIES INSTANCE = new PROPERTY_FOR_ENUM_ENTRIES();

        private PROPERTY_FOR_ENUM_ENTRIES() {
            super("PROPERTY_FOR_ENUM_ENTRIES", false);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$FIELD_FOR_OBJECT_INSTANCE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FIELD_FOR_OBJECT_INSTANCE extends IrDeclarationOriginImpl {
        public static final FIELD_FOR_OBJECT_INSTANCE INSTANCE = new FIELD_FOR_OBJECT_INSTANCE();

        private FIELD_FOR_OBJECT_INSTANCE() {
            super("FIELD_FOR_OBJECT_INSTANCE", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$FIELD_FOR_CLASS_CONTEXT_RECEIVER;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FIELD_FOR_CLASS_CONTEXT_RECEIVER extends IrDeclarationOriginImpl {
        public static final FIELD_FOR_CLASS_CONTEXT_RECEIVER INSTANCE = new FIELD_FOR_CLASS_CONTEXT_RECEIVER();

        private FIELD_FOR_CLASS_CONTEXT_RECEIVER() {
            super("FIELD_FOR_CLASS_CONTEXT_RECEIVER", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$ADAPTER_FOR_CALLABLE_REFERENCE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ADAPTER_FOR_CALLABLE_REFERENCE extends IrDeclarationOriginImpl {
        public static final ADAPTER_FOR_CALLABLE_REFERENCE INSTANCE = new ADAPTER_FOR_CALLABLE_REFERENCE();

        private ADAPTER_FOR_CALLABLE_REFERENCE() {
            super("ADAPTER_FOR_CALLABLE_REFERENCE", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$ADAPTER_PARAMETER_FOR_CALLABLE_REFERENCE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ADAPTER_PARAMETER_FOR_CALLABLE_REFERENCE extends IrDeclarationOriginImpl {
        public static final ADAPTER_PARAMETER_FOR_CALLABLE_REFERENCE INSTANCE = new ADAPTER_PARAMETER_FOR_CALLABLE_REFERENCE();

        private ADAPTER_PARAMETER_FOR_CALLABLE_REFERENCE() {
            super("ADAPTER_PARAMETER_FOR_CALLABLE_REFERENCE", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$ADAPTER_FOR_SUSPEND_CONVERSION;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ADAPTER_FOR_SUSPEND_CONVERSION extends IrDeclarationOriginImpl {
        public static final ADAPTER_FOR_SUSPEND_CONVERSION INSTANCE = new ADAPTER_FOR_SUSPEND_CONVERSION();

        private ADAPTER_FOR_SUSPEND_CONVERSION() {
            super("ADAPTER_FOR_SUSPEND_CONVERSION", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$ADAPTER_PARAMETER_FOR_SUSPEND_CONVERSION;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ADAPTER_PARAMETER_FOR_SUSPEND_CONVERSION extends IrDeclarationOriginImpl {
        public static final ADAPTER_PARAMETER_FOR_SUSPEND_CONVERSION INSTANCE = new ADAPTER_PARAMETER_FOR_SUSPEND_CONVERSION();

        private ADAPTER_PARAMETER_FOR_SUSPEND_CONVERSION() {
            super("ADAPTER_PARAMETER_FOR_SUSPEND_CONVERSION", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$ADAPTER_FOR_FUN_INTERFACE_CONSTRUCTOR;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ADAPTER_FOR_FUN_INTERFACE_CONSTRUCTOR extends IrDeclarationOriginImpl {
        public static final ADAPTER_FOR_FUN_INTERFACE_CONSTRUCTOR INSTANCE = new ADAPTER_FOR_FUN_INTERFACE_CONSTRUCTOR();

        private ADAPTER_FOR_FUN_INTERFACE_CONSTRUCTOR() {
            super("ADAPTER_FOR_FUN_INTERFACE_CONSTRUCTOR", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$GENERATED_SAM_IMPLEMENTATION;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GENERATED_SAM_IMPLEMENTATION extends IrDeclarationOriginImpl {
        public static final GENERATED_SAM_IMPLEMENTATION INSTANCE = new GENERATED_SAM_IMPLEMENTATION();

        private GENERATED_SAM_IMPLEMENTATION() {
            super("GENERATED_SAM_IMPLEMENTATION", false, 2, null);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$SYNTHETIC_GENERATED_SAM_IMPLEMENTATION;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SYNTHETIC_GENERATED_SAM_IMPLEMENTATION extends IrDeclarationOriginImpl {
        public static final SYNTHETIC_GENERATED_SAM_IMPLEMENTATION INSTANCE = new SYNTHETIC_GENERATED_SAM_IMPLEMENTATION();

        private SYNTHETIC_GENERATED_SAM_IMPLEMENTATION() {
            super("SYNTHETIC_GENERATED_SAM_IMPLEMENTATION", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$SYNTHETIC_JAVA_PROPERTY_DELEGATE;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SYNTHETIC_JAVA_PROPERTY_DELEGATE extends IrDeclarationOriginImpl {
        public static final SYNTHETIC_JAVA_PROPERTY_DELEGATE INSTANCE = new SYNTHETIC_JAVA_PROPERTY_DELEGATE();

        private SYNTHETIC_JAVA_PROPERTY_DELEGATE() {
            super("SYNTHETIC_JAVA_PROPERTY_DELEGATE", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$FIELD_FOR_OUTER_THIS;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FIELD_FOR_OUTER_THIS extends IrDeclarationOriginImpl {
        public static final FIELD_FOR_OUTER_THIS INSTANCE = new FIELD_FOR_OUTER_THIS();

        private FIELD_FOR_OUTER_THIS() {
            super("FIELD_FOR_OUTER_THIS", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$CONTINUATION;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CONTINUATION extends IrDeclarationOriginImpl {
        public static final CONTINUATION INSTANCE = new CONTINUATION();

        private CONTINUATION() {
            super("CONTINUATION", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$LOWERED_SUSPEND_FUNCTION;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LOWERED_SUSPEND_FUNCTION extends IrDeclarationOriginImpl {
        public static final LOWERED_SUSPEND_FUNCTION INSTANCE = new LOWERED_SUSPEND_FUNCTION();

        private LOWERED_SUSPEND_FUNCTION() {
            super("LOWERED_SUSPEND_FUNCTION", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$SHARED_VARIABLE_IN_EVALUATOR_FRAGMENT;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "()V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SHARED_VARIABLE_IN_EVALUATOR_FRAGMENT extends IrDeclarationOriginImpl {
        public static final SHARED_VARIABLE_IN_EVALUATOR_FRAGMENT INSTANCE = new SHARED_VARIABLE_IN_EVALUATOR_FRAGMENT();

        private SHARED_VARIABLE_IN_EVALUATOR_FRAGMENT() {
            super("SHARED_VARIABLE_IN_EVALUATOR_FRAGMENT", true);
        }
    }

    /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0019\b\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0006H\u0016R\u0014\u0010\b\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$GeneratedByPlugin;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "pluginKey", "Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", "(Lorg/jetbrains/kotlin/GeneratedDeclarationKey;)V", "pluginId", "", "(Ljava/lang/String;Lorg/jetbrains/kotlin/GeneratedDeclarationKey;)V", "name", "getName", "()Ljava/lang/String;", "getPluginId", "getPluginKey", "()Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "Companion", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GeneratedByPlugin implements IrDeclarationOrigin {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final String pluginId;
        private final GeneratedDeclarationKey pluginKey;

        public /* synthetic */ GeneratedByPlugin(String str, GeneratedDeclarationKey generatedDeclarationKey, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, generatedDeclarationKey);
        }

        private GeneratedByPlugin(String str, GeneratedDeclarationKey generatedDeclarationKey) {
            this.pluginId = str;
            this.pluginKey = generatedDeclarationKey;
        }

        public final String getPluginId() {
            return this.pluginId;
        }

        public final GeneratedDeclarationKey getPluginKey() {
            return this.pluginKey;
        }

        @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
        /* JADX INFO: renamed from: isSynthetic */
        public boolean getIsSynthetic() {
            return DefaultImpls.isSynthetic(this);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public GeneratedByPlugin(GeneratedDeclarationKey pluginKey) {
            Intrinsics.checkNotNullParameter(pluginKey, "pluginKey");
            String qualifiedName = Reflection.getOrCreateKotlinClass(pluginKey.getClass()).getQualifiedName();
            Intrinsics.checkNotNull(qualifiedName);
            this(qualifiedName, pluginKey);
        }

        /* JADX INFO: compiled from: IrDeclarationOrigin.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$GeneratedByPlugin$Companion;", "", "()V", "fromSerializedString", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin$GeneratedByPlugin;", "name", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            /* JADX WARN: Multi-variable type inference failed */
            public final GeneratedByPlugin fromSerializedString(String name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                String strRemoveSurrounding = StringsKt.removeSurrounding(name2, (CharSequence) "GENERATED[", (CharSequence) "]");
                GeneratedDeclarationKey generatedDeclarationKey = null;
                Object[] objArr = 0;
                if (Intrinsics.areEqual(strRemoveSurrounding, name2)) {
                    strRemoveSurrounding = null;
                }
                if (strRemoveSurrounding == null) {
                    return null;
                }
                return new GeneratedByPlugin(strRemoveSurrounding, generatedDeclarationKey, objArr == true ? 1 : 0);
            }
        }

        @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
        public String getName() {
            return "GENERATED[" + this.pluginId + ']';
        }

        public String toString() {
            return getName();
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other instanceof GeneratedByPlugin) {
                return Intrinsics.areEqual(this.pluginKey, ((GeneratedByPlugin) other).pluginKey);
            }
            return false;
        }

        public int hashCode() {
            GeneratedDeclarationKey generatedDeclarationKey = this.pluginKey;
            if (generatedDeclarationKey != null) {
                return generatedDeclarationKey.hashCode();
            }
            return 0;
        }
    }
}
