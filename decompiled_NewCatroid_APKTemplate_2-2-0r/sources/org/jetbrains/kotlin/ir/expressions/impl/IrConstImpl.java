package org.jetbrains.kotlin.ir.expressions.impl;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: IrConstImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\u0018\u0000 \u001c*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0001\u001cB3\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t\u0012\u0006\u0010\n\u001a\u00028\u0000¢\u0006\u0002\u0010\u000bR\u0014\u0010\u0005\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR \u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u001a\u0010\u0006\u001a\u00020\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001c\u0010\n\u001a\u00028\u0000X\u0096\u000e¢\u0006\u0010\n\u0002\u0010\u001b\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001a¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;", "value", "(IILorg/jetbrains/kotlin/ir/types/IrType;Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;Ljava/lang/Object;)V", "getEndOffset", "()I", "getKind", "()Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;", "setKind", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstKind;)V", "getStartOffset", "getType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setType", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", "getValue", "()Ljava/lang/Object;", "setValue", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "Companion", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrConstImpl<T> extends IrConst<T> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int endOffset;
    private IrConstKind<T> kind;
    private final int startOffset;
    private IrType type;
    private T value;

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrExpression
    public IrType getType() {
        return this.type;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrExpression
    public void setType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<set-?>");
        this.type = irType;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConst
    public IrConstKind<T> getKind() {
        return this.kind;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConst
    public void setKind(IrConstKind<T> irConstKind) {
        Intrinsics.checkNotNullParameter(irConstKind, "<set-?>");
        this.kind = irConstKind;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConst
    public T getValue() {
        return this.value;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrConst
    public void setValue(T t) {
        this.value = t;
    }

    /* JADX INFO: compiled from: IrConstImpl.kt */
    @Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0000\n\u0002\u0010\f\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\n\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J,\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0005J,\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\rJ,\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u000fJ$\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nJ&\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nJ$\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nJ\"\u0010\u0014\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nJ,\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0016J,\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0018J,\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0007J,\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u001bJ,\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u001dJ,\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u00042\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u001f¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl$Companion;", "", "()V", "boolean", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", "", "startOffset", "", "endOffset", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "value", "byte", "", PsiKeyword.CHAR, "", "constFalse", "constNull", "", "constTrue", "defaultValueForType", "double", "", "float", "", "int", "long", "", "short", "", "string", "", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: IrConstImpl.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[PrimitiveType.values().length];
                try {
                    iArr[PrimitiveType.BOOLEAN.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[PrimitiveType.CHAR.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[PrimitiveType.BYTE.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[PrimitiveType.SHORT.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr[PrimitiveType.INT.ordinal()] = 5;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr[PrimitiveType.FLOAT.ordinal()] = 6;
                } catch (NoSuchFieldError unused6) {
                }
                try {
                    iArr[PrimitiveType.LONG.ordinal()] = 7;
                } catch (NoSuchFieldError unused7) {
                }
                try {
                    iArr[PrimitiveType.DOUBLE.ordinal()] = 8;
                } catch (NoSuchFieldError unused8) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final IrConstImpl<String> string(int startOffset, int endOffset, IrType type, String value) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(value, "value");
            return new IrConstImpl<>(startOffset, endOffset, type, IrConstKind.String.INSTANCE, value);
        }

        /* JADX INFO: renamed from: int, reason: not valid java name */
        public final IrConstImpl<Integer> m7211int(int startOffset, int endOffset, IrType type, int value) {
            Intrinsics.checkNotNullParameter(type, "type");
            return new IrConstImpl<>(startOffset, endOffset, type, IrConstKind.Int.INSTANCE, Integer.valueOf(value));
        }

        public final IrConstImpl constNull(int startOffset, int endOffset, IrType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return new IrConstImpl(startOffset, endOffset, type, IrConstKind.Null.INSTANCE, null);
        }

        /* JADX INFO: renamed from: boolean, reason: not valid java name */
        public final IrConstImpl<Boolean> m7206boolean(int startOffset, int endOffset, IrType type, boolean value) {
            Intrinsics.checkNotNullParameter(type, "type");
            return new IrConstImpl<>(startOffset, endOffset, type, IrConstKind.Boolean.INSTANCE, Boolean.valueOf(value));
        }

        public final IrConstImpl<Boolean> constTrue(int startOffset, int endOffset, IrType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return m7206boolean(startOffset, endOffset, type, true);
        }

        public final IrConstImpl<Boolean> constFalse(int startOffset, int endOffset, IrType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return m7206boolean(startOffset, endOffset, type, false);
        }

        /* JADX INFO: renamed from: long, reason: not valid java name */
        public final IrConstImpl<Long> m7212long(int startOffset, int endOffset, IrType type, long value) {
            Intrinsics.checkNotNullParameter(type, "type");
            return new IrConstImpl<>(startOffset, endOffset, type, IrConstKind.Long.INSTANCE, Long.valueOf(value));
        }

        /* JADX INFO: renamed from: float, reason: not valid java name */
        public final IrConstImpl<Float> m7210float(int startOffset, int endOffset, IrType type, float value) {
            Intrinsics.checkNotNullParameter(type, "type");
            return new IrConstImpl<>(startOffset, endOffset, type, IrConstKind.Float.INSTANCE, Float.valueOf(value));
        }

        /* JADX INFO: renamed from: double, reason: not valid java name */
        public final IrConstImpl<Double> m7209double(int startOffset, int endOffset, IrType type, double value) {
            Intrinsics.checkNotNullParameter(type, "type");
            return new IrConstImpl<>(startOffset, endOffset, type, IrConstKind.Double.INSTANCE, Double.valueOf(value));
        }

        /* JADX INFO: renamed from: char, reason: not valid java name */
        public final IrConstImpl<Character> m7208char(int startOffset, int endOffset, IrType type, char value) {
            Intrinsics.checkNotNullParameter(type, "type");
            return new IrConstImpl<>(startOffset, endOffset, type, IrConstKind.Char.INSTANCE, Character.valueOf(value));
        }

        /* JADX INFO: renamed from: byte, reason: not valid java name */
        public final IrConstImpl<Byte> m7207byte(int startOffset, int endOffset, IrType type, byte value) {
            Intrinsics.checkNotNullParameter(type, "type");
            return new IrConstImpl<>(startOffset, endOffset, type, IrConstKind.Byte.INSTANCE, Byte.valueOf(value));
        }

        /* JADX INFO: renamed from: short, reason: not valid java name */
        public final IrConstImpl<Short> m7213short(int startOffset, int endOffset, IrType type, short value) {
            Intrinsics.checkNotNullParameter(type, "type");
            return new IrConstImpl<>(startOffset, endOffset, type, IrConstKind.Short.INSTANCE, Short.valueOf(value));
        }

        public final IrConstImpl<?> defaultValueForType(int startOffset, int endOffset, IrType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            if (IrTypePredicatesKt.isMarkedNullable(type)) {
                return constNull(startOffset, endOffset, type);
            }
            PrimitiveType primitiveType = IrTypePredicatesKt.getPrimitiveType(type);
            switch (primitiveType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[primitiveType.ordinal()]) {
                case 1:
                    return m7206boolean(startOffset, endOffset, type, false);
                case 2:
                    return m7208char(startOffset, endOffset, type, (char) 0);
                case 3:
                    return m7207byte(startOffset, endOffset, type, (byte) 0);
                case 4:
                    return m7213short(startOffset, endOffset, type, (short) 0);
                case 5:
                    return m7211int(startOffset, endOffset, type, 0);
                case 6:
                    return m7210float(startOffset, endOffset, type, 0.0f);
                case 7:
                    return m7212long(startOffset, endOffset, type, 0L);
                case 8:
                    return m7209double(startOffset, endOffset, type, 0.0d);
                default:
                    return constNull(startOffset, endOffset, IrTypesKt.makeNullable(type));
            }
        }
    }

    public IrConstImpl(int i, int i2, IrType type, IrConstKind<T> kind, T t) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.startOffset = i;
        this.endOffset = i2;
        this.type = type;
        this.kind = kind;
        this.value = t;
    }
}
