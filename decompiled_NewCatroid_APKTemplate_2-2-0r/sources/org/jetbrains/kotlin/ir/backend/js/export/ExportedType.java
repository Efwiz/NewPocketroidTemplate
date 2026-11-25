package org.jetbrains.kotlin.ir.backend.js.export;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;

/* JADX INFO: compiled from: ExportModel.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u000e\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0000J\u0010\u0010\u0007\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0005H\u0016\u0082\u0001\u000e\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "", "()V", "withImplicitlyExported", "implicitlyExportedType", "", "exportedSupertype", "withNullability", "nullable", "Array", "ClassType", "ErrorType", "Function", "ImplicitlyExportedType", "InlineInterfaceType", "IntersectionType", "LiteralType", "Nullable", "Primitive", "PropertyType", "TypeOf", "TypeParameter", "UnionType", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Array;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$ClassType;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$ErrorType;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Function;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$ImplicitlyExportedType;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$InlineInterfaceType;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$IntersectionType;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$LiteralType;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Nullable;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$PropertyType;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$TypeOf;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$TypeParameter;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$UnionType;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ExportedType {
    public /* synthetic */ ExportedType(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: ExportModel.kt */
    @Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u000f\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015B\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u000f\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "typescript", "", "(Ljava/lang/String;)V", "getTypescript", "()Ljava/lang/String;", "Any", "Boolean", "ByteArray", "DoubleArray", "FloatArray", "IntArray", "Nothing", "Number", "ShortArray", CommonClassNames.JAVA_LANG_STRING_SHORT, "Throwable", "Undefined", "UniqueSymbol", "Unit", "Unknown", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$Any;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$Boolean;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$ByteArray;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$DoubleArray;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$FloatArray;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$IntArray;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$Nothing;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$Number;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$ShortArray;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$String;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$Throwable;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$Undefined;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$UniqueSymbol;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$Unit;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$Unknown;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class Primitive extends ExportedType {
        private final java.lang.String typescript;

        public /* synthetic */ Primitive(java.lang.String str, DefaultConstructorMarker defaultConstructorMarker) {
            this(str);
        }

        /* JADX INFO: compiled from: ExportModel.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$Boolean;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Boolean extends Primitive {
            public static final Boolean INSTANCE = new Boolean();

            private Boolean() {
                super("boolean", null);
            }
        }

        private Primitive(java.lang.String str) {
            super(null);
            this.typescript = str;
        }

        public final java.lang.String getTypescript() {
            return this.typescript;
        }

        /* JADX INFO: compiled from: ExportModel.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$Number;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Number extends Primitive {
            public static final Number INSTANCE = new Number();

            private Number() {
                super("number", null);
            }
        }

        /* JADX INFO: compiled from: ExportModel.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$ByteArray;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ByteArray extends Primitive {
            public static final ByteArray INSTANCE = new ByteArray();

            private ByteArray() {
                super("Int8Array", null);
            }
        }

        /* JADX INFO: compiled from: ExportModel.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$ShortArray;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class ShortArray extends Primitive {
            public static final ShortArray INSTANCE = new ShortArray();

            private ShortArray() {
                super("Int16Array", null);
            }
        }

        /* JADX INFO: compiled from: ExportModel.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$IntArray;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class IntArray extends Primitive {
            public static final IntArray INSTANCE = new IntArray();

            private IntArray() {
                super("Int32Array", null);
            }
        }

        /* JADX INFO: compiled from: ExportModel.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$FloatArray;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class FloatArray extends Primitive {
            public static final FloatArray INSTANCE = new FloatArray();

            private FloatArray() {
                super("Float32Array", null);
            }
        }

        /* JADX INFO: compiled from: ExportModel.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$DoubleArray;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class DoubleArray extends Primitive {
            public static final DoubleArray INSTANCE = new DoubleArray();

            private DoubleArray() {
                super("Float64Array", null);
            }
        }

        /* JADX INFO: compiled from: ExportModel.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$String;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class String extends Primitive {
            public static final String INSTANCE = new String();

            private String() {
                super("string", null);
            }
        }

        /* JADX INFO: compiled from: ExportModel.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$Throwable;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Throwable extends Primitive {
            public static final Throwable INSTANCE = new Throwable();

            private Throwable() {
                super("Error", null);
            }
        }

        /* JADX INFO: compiled from: ExportModel.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$Any;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Any extends Primitive {
            public static final Any INSTANCE = new Any();

            private Any() {
                super("any", null);
            }
        }

        /* JADX INFO: compiled from: ExportModel.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$Unknown;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Unknown extends Primitive {
            public static final Unknown INSTANCE = new Unknown();

            private Unknown() {
                super("unknown", null);
            }
        }

        /* JADX INFO: compiled from: ExportModel.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$Undefined;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Undefined extends Primitive {
            public static final Undefined INSTANCE = new Undefined();

            private Undefined() {
                super("undefined", null);
            }
        }

        /* JADX INFO: compiled from: ExportModel.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$Unit;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Unit extends Primitive {
            public static final Unit INSTANCE = new Unit();

            private Unit() {
                super(PsiKeyword.VOID, null);
            }
        }

        /* JADX INFO: compiled from: ExportModel.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$Nothing;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Nothing extends Primitive {
            public static final Nothing INSTANCE = new Nothing();

            private Nothing() {
                super("never", null);
            }
        }

        /* JADX INFO: compiled from: ExportModel.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive$UniqueSymbol;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Primitive;", "()V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class UniqueSymbol extends Primitive {
            public static final UniqueSymbol INSTANCE = new UniqueSymbol();

            private UniqueSymbol() {
                super("unique symbol", null);
            }
        }
    }

    private ExportedType() {
    }

    /* JADX INFO: compiled from: ExportModel.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0002\t\nB\u000f\b\u0004\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005R\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\u0002\u000b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$LiteralType;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "value", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getValue", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "NumberLiteralType", "StringLiteralType", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$LiteralType$NumberLiteralType;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$LiteralType$StringLiteralType;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class LiteralType<T> extends ExportedType {
        private final T value;

        public /* synthetic */ LiteralType(Object obj, DefaultConstructorMarker defaultConstructorMarker) {
            this(obj);
        }

        /* JADX INFO: compiled from: ExportModel.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$LiteralType$StringLiteralType;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$LiteralType;", "", "value", "(Ljava/lang/String;)V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class StringLiteralType extends LiteralType<String> {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public StringLiteralType(String value) {
                super(value, null);
                Intrinsics.checkNotNullParameter(value, "value");
            }
        }

        private LiteralType(T t) {
            super(null);
            this.value = t;
        }

        public final T getValue() {
            return this.value;
        }

        /* JADX INFO: compiled from: ExportModel.kt */
        @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0004\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$LiteralType$NumberLiteralType;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$LiteralType;", "", "value", "(Ljava/lang/Number;)V", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class NumberLiteralType extends LiteralType<Number> {
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public NumberLiteralType(Number value) {
                super(value, null);
                Intrinsics.checkNotNullParameter(value, "value");
            }
        }
    }

    /* JADX INFO: compiled from: ExportModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Array;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "elementType", "(Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;)V", "getElementType", "()Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Array extends ExportedType {
        private final ExportedType elementType;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Array(ExportedType elementType) {
            super(null);
            Intrinsics.checkNotNullParameter(elementType, "elementType");
            this.elementType = elementType;
        }

        public final ExportedType getElementType() {
            return this.elementType;
        }
    }

    /* JADX INFO: compiled from: ExportModel.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Function;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "parameterTypes", "", "returnType", "(Ljava/util/List;Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;)V", "getParameterTypes", "()Ljava/util/List;", "getReturnType", "()Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Function extends ExportedType {
        private final List<ExportedType> parameterTypes;
        private final ExportedType returnType;

        public final List<ExportedType> getParameterTypes() {
            return this.parameterTypes;
        }

        public final ExportedType getReturnType() {
            return this.returnType;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public Function(List<? extends ExportedType> parameterTypes, ExportedType returnType) {
            super(null);
            Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
            Intrinsics.checkNotNullParameter(returnType, "returnType");
            this.parameterTypes = parameterTypes;
            this.returnType = returnType;
        }
    }

    /* JADX INFO: compiled from: ExportModel.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$ClassType;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "name", "", "arguments", "", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Ljava/lang/String;Ljava/util/List;Lorg/jetbrains/kotlin/ir/declarations/IrClass;)V", "getArguments", "()Ljava/util/List;", "getIr", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getName", "()Ljava/lang/String;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ClassType extends ExportedType {
        private final List<ExportedType> arguments;
        private final IrClass ir;
        private final String name;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public ClassType(String name2, List<? extends ExportedType> arguments, IrClass ir) {
            super(null);
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            Intrinsics.checkNotNullParameter(ir, "ir");
            this.name = name2;
            this.arguments = arguments;
            this.ir = ir;
        }

        public final List<ExportedType> getArguments() {
            return this.arguments;
        }

        public final IrClass getIr() {
            return this.ir;
        }

        public final String getName() {
            return this.name;
        }
    }

    /* JADX INFO: compiled from: ExportModel.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0005R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$TypeParameter;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "name", "", "constraint", "(Ljava/lang/String;Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;)V", "getConstraint", "()Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "getName", "()Ljava/lang/String;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TypeParameter extends ExportedType {
        private final ExportedType constraint;
        private final String name;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public TypeParameter(String name2, ExportedType exportedType) {
            super(null);
            Intrinsics.checkNotNullParameter(name2, "name");
            this.name = name2;
            this.constraint = exportedType;
        }

        public /* synthetic */ TypeParameter(String str, ExportedType exportedType, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? null : exportedType);
        }

        public final ExportedType getConstraint() {
            return this.constraint;
        }

        public final String getName() {
            return this.name;
        }
    }

    /* JADX INFO: compiled from: ExportModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$Nullable;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "baseType", "(Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;)V", "getBaseType", "()Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Nullable extends ExportedType {
        private final ExportedType baseType;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Nullable(ExportedType baseType) {
            super(null);
            Intrinsics.checkNotNullParameter(baseType, "baseType");
            this.baseType = baseType;
        }

        public final ExportedType getBaseType() {
            return this.baseType;
        }
    }

    /* JADX INFO: compiled from: ExportModel.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$ErrorType;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "comment", "", "(Ljava/lang/String;)V", "getComment", "()Ljava/lang/String;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ErrorType extends ExportedType {
        private final String comment;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ErrorType(String comment) {
            super(null);
            Intrinsics.checkNotNullParameter(comment, "comment");
            this.comment = comment;
        }

        public final String getComment() {
            return this.comment;
        }
    }

    /* JADX INFO: compiled from: ExportModel.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$TypeOf;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "name", "", "(Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TypeOf extends ExportedType {
        private final String name;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public TypeOf(String name2) {
            super(null);
            Intrinsics.checkNotNullParameter(name2, "name");
            this.name = name2;
        }

        public final String getName() {
            return this.name;
        }
    }

    /* JADX INFO: compiled from: ExportModel.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$InlineInterfaceType;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "members", "", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedDeclaration;", "(Ljava/util/List;)V", "getMembers", "()Ljava/util/List;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class InlineInterfaceType extends ExportedType {
        private final List<ExportedDeclaration> members;

        public final List<ExportedDeclaration> getMembers() {
            return this.members;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public InlineInterfaceType(List<? extends ExportedDeclaration> members) {
            super(null);
            Intrinsics.checkNotNullParameter(members, "members");
            this.members = members;
        }
    }

    /* JADX INFO: compiled from: ExportModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0003\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$UnionType;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "lhs", "rhs", "(Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;)V", "getLhs", "()Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "getRhs", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UnionType extends ExportedType {
        private final ExportedType lhs;
        private final ExportedType rhs;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public UnionType(ExportedType lhs, ExportedType rhs) {
            super(null);
            Intrinsics.checkNotNullParameter(lhs, "lhs");
            Intrinsics.checkNotNullParameter(rhs, "rhs");
            this.lhs = lhs;
            this.rhs = rhs;
        }

        public final ExportedType getLhs() {
            return this.lhs;
        }

        public final ExportedType getRhs() {
            return this.rhs;
        }
    }

    /* JADX INFO: compiled from: ExportModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0003\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$IntersectionType;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "lhs", "rhs", "(Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;)V", "getLhs", "()Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "getRhs", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IntersectionType extends ExportedType {
        private final ExportedType lhs;
        private final ExportedType rhs;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IntersectionType(ExportedType lhs, ExportedType rhs) {
            super(null);
            Intrinsics.checkNotNullParameter(lhs, "lhs");
            Intrinsics.checkNotNullParameter(rhs, "rhs");
            this.lhs = lhs;
            this.rhs = rhs;
        }

        public final ExportedType getLhs() {
            return this.lhs;
        }

        public final ExportedType getRhs() {
            return this.rhs;
        }
    }

    /* JADX INFO: compiled from: ExportModel.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0003\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$PropertyType;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "container", "propertyName", "(Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;)V", "getContainer", "()Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "getPropertyName", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PropertyType extends ExportedType {
        private final ExportedType container;
        private final ExportedType propertyName;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PropertyType(ExportedType container, ExportedType propertyName) {
            super(null);
            Intrinsics.checkNotNullParameter(container, "container");
            Intrinsics.checkNotNullParameter(propertyName, "propertyName");
            this.container = container;
            this.propertyName = propertyName;
        }

        public final ExportedType getContainer() {
            return this.container;
        }

        public final ExportedType getPropertyName() {
            return this.propertyName;
        }
    }

    /* JADX INFO: compiled from: ExportModel.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004J\t\u0010\b\u001a\u00020\u0001HÆ\u0003J\t\u0010\t\u001a\u00020\u0001HÆ\u0003J\u001d\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u0001HÆ\u0001J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eHÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\u0010\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\fH\u0016R\u0011\u0010\u0003\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0006¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType$ImplicitlyExportedType;", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", ModuleXmlParser.TYPE, "exportedSupertype", "(Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;)V", "getExportedSupertype", "()Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedType;", "getType", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "withNullability", "nullable", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class ImplicitlyExportedType extends ExportedType {
        private final ExportedType exportedSupertype;
        private final ExportedType type;

        public static /* synthetic */ ImplicitlyExportedType copy$default(ImplicitlyExportedType implicitlyExportedType, ExportedType exportedType, ExportedType exportedType2, int i, Object obj) {
            if ((i & 1) != 0) {
                exportedType = implicitlyExportedType.type;
            }
            if ((i & 2) != 0) {
                exportedType2 = implicitlyExportedType.exportedSupertype;
            }
            return implicitlyExportedType.copy(exportedType, exportedType2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final ExportedType getType() {
            return this.type;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final ExportedType getExportedSupertype() {
            return this.exportedSupertype;
        }

        public final ImplicitlyExportedType copy(ExportedType type, ExportedType exportedSupertype) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(exportedSupertype, "exportedSupertype");
            return new ImplicitlyExportedType(type, exportedSupertype);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ImplicitlyExportedType)) {
                return false;
            }
            ImplicitlyExportedType implicitlyExportedType = (ImplicitlyExportedType) other;
            return Intrinsics.areEqual(this.type, implicitlyExportedType.type) && Intrinsics.areEqual(this.exportedSupertype, implicitlyExportedType.exportedSupertype);
        }

        public int hashCode() {
            return (this.type.hashCode() * 31) + this.exportedSupertype.hashCode();
        }

        public String toString() {
            return "ImplicitlyExportedType(type=" + this.type + ", exportedSupertype=" + this.exportedSupertype + ')';
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ImplicitlyExportedType(ExportedType type, ExportedType exportedSupertype) {
            super(null);
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(exportedSupertype, "exportedSupertype");
            this.type = type;
            this.exportedSupertype = exportedSupertype;
        }

        public final ExportedType getExportedSupertype() {
            return this.exportedSupertype;
        }

        public final ExportedType getType() {
            return this.type;
        }

        @Override // org.jetbrains.kotlin.ir.backend.js.export.ExportedType
        public ImplicitlyExportedType withNullability(boolean nullable) {
            return new ImplicitlyExportedType(this.type.withNullability(nullable), this.exportedSupertype.withNullability(nullable));
        }
    }

    public ExportedType withNullability(boolean nullable) {
        return nullable ? new Nullable(this) : this;
    }

    public final ExportedType withImplicitlyExported(boolean implicitlyExportedType, ExportedType exportedSupertype) {
        Intrinsics.checkNotNullParameter(exportedSupertype, "exportedSupertype");
        return implicitlyExportedType ? new ImplicitlyExportedType(this, exportedSupertype) : this;
    }
}
