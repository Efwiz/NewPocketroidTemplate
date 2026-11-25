package org.jetbrains.kotlin.library.abi.impl;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import com.reandroid.arsc.chunk.TypeBlock;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.deserialization.Flags;

/* JADX INFO: compiled from: FlagFieldEx.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0012\b \u0018\u0000 \u0013*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0003\u0013\u0014\u0015B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J\u0015\u0010\u000b\u001a\u00028\u00002\u0006\u0010\f\u001a\u00020\u0004H&¢\u0006\u0002\u0010\rJ\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0004H\u0004J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0004J\u0015\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0012R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\t¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/FlagFieldEx;", ExifInterface.GPS_DIRECTION_TRUE, "", TypedValues.CycleType.S_WAVE_OFFSET, "", "bitWidth", "(II)V", "bitMask", "getBitWidth", "()I", "getOffset", "get", OverlayablePolicy.NAME_flags, "(I)Ljava/lang/Object;", "readSignificantBitsFromFlags", "storeSignificantBitsAsFlags", "value", "toFlags", "(Ljava/lang/Object;)I", "Companion", "EnumFlagFieldEx", "IntFlagFieldEx", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FlagFieldEx<T> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int bitMask;
    private final int bitWidth;
    private final int offset;

    public abstract T get(int flags);

    public abstract int toFlags(T value);

    public FlagFieldEx(int i, int i2) {
        this.offset = i;
        this.bitWidth = i2;
        if (i < 0) {
            throw new IllegalArgumentException(("Invalid offset: " + i).toString());
        }
        if (i2 <= 0) {
            throw new IllegalArgumentException(("Invalid bit width: " + i2).toString());
        }
        int i3 = (i + i2) - 32;
        if (i3 > 0) {
            throw new IllegalArgumentException(("Not enough bit space for storage. Offset=" + i + ", width=" + i2 + ", overflow=" + i3 + '.').toString());
        }
        this.bitMask = (1 << i2) - 1;
    }

    public final int getBitWidth() {
        return this.bitWidth;
    }

    public final int getOffset() {
        return this.offset;
    }

    protected final int readSignificantBitsFromFlags(int flags) {
        return (flags >>> this.offset) & this.bitMask;
    }

    protected final int storeSignificantBitsAsFlags(int value) {
        if ((value >>> this.bitWidth) != 0) {
            throw new IllegalArgumentException(("Not enough space to store " + value).toString());
        }
        return value << this.offset;
    }

    /* JADX INFO: compiled from: FlagFieldEx.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\t\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002¢\u0006\u0002\u0010\u0005J\u0015\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u0002H\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/FlagFieldEx$IntFlagFieldEx;", "Lorg/jetbrains/kotlin/library/abi/impl/FlagFieldEx;", "", TypedValues.CycleType.S_WAVE_OFFSET, "bitWidth", "(II)V", "get", OverlayablePolicy.NAME_flags, "(I)Ljava/lang/Integer;", "toFlags", "value", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IntFlagFieldEx extends FlagFieldEx<Integer> {
        public IntFlagFieldEx(int i, int i2) {
            super(i, i2);
        }

        @Override // org.jetbrains.kotlin.library.abi.impl.FlagFieldEx
        public /* bridge */ /* synthetic */ int toFlags(Integer num) {
            return toFlags(num.intValue());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.jetbrains.kotlin.library.abi.impl.FlagFieldEx
        public Integer get(int flags) {
            return Integer.valueOf(readSignificantBitsFromFlags(flags));
        }

        public int toFlags(int value) {
            return storeSignificantBitsAsFlags(value);
        }
    }

    /* JADX INFO: compiled from: FlagFieldEx.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\n\u0018\u0000 \u0010*\u000e\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001\u0010B\u001b\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007¢\u0006\u0002\u0010\bJ\u0015\u0010\n\u001a\u00028\u00012\u0006\u0010\u000b\u001a\u00020\u0005H\u0016¢\u0006\u0002\u0010\fJ\u0015\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010\u000fR\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\t¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/FlagFieldEx$EnumFlagFieldEx;", ExifInterface.LONGITUDE_EAST, "", "Lorg/jetbrains/kotlin/library/abi/impl/FlagFieldEx;", TypedValues.CycleType.S_WAVE_OFFSET, "", TypeBlock.NAME_entries, "", "(I[Ljava/lang/Enum;)V", "[Ljava/lang/Enum;", "get", OverlayablePolicy.NAME_flags, "(I)Ljava/lang/Enum;", "toFlags", "value", "(Ljava/lang/Enum;)I", "Companion", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EnumFlagFieldEx<E extends Enum<E>> extends FlagFieldEx<E> {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final E[] entries;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public EnumFlagFieldEx(int i, E[] entries) {
            super(i, INSTANCE.computeBitWidth(entries));
            Intrinsics.checkNotNullParameter(entries, "entries");
            this.entries = entries;
        }

        @Override // org.jetbrains.kotlin.library.abi.impl.FlagFieldEx
        public E get(int flags) {
            return this.entries[readSignificantBitsFromFlags(flags)];
        }

        @Override // org.jetbrains.kotlin.library.abi.impl.FlagFieldEx
        public int toFlags(E value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return storeSignificantBitsAsFlags(value.ordinal());
        }

        /* JADX INFO: compiled from: FlagFieldEx.kt */
        @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J+\u0010\u0003\u001a\u00020\u0004\"\u000e\b\u0002\u0010\u0005*\b\u0012\u0004\u0012\u0002H\u00050\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00050\bH\u0002¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/FlagFieldEx$EnumFlagFieldEx$Companion;", "", "()V", "computeBitWidth", "", ExifInterface.LONGITUDE_EAST, "", TypeBlock.NAME_entries, "", "([Ljava/lang/Enum;)I", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            /* JADX INFO: Access modifiers changed from: private */
            public final <E extends Enum<E>> int computeBitWidth(E[] entries) {
                if (entries.length == 0) {
                    throw new IllegalArgumentException("No enum entries".toString());
                }
                return Math.max(1, 32 - Integer.numberOfLeadingZeros(ArraysKt.getLastIndex(entries)));
            }
        }
    }

    /* JADX INFO: compiled from: FlagFieldEx.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J-\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0005\"\u0010\b\u0001\u0010\u000b\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u000b0\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0086\bJ-\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0005\"\u0010\b\u0001\u0010\u000b\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u000b0\f2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\bH\u0086\bJ!\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0005\"\u0010\b\u0001\u0010\u000b\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u000b0\fH\u0086\bJ \u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00052\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u00052\u0006\u0010\u0010\u001a\u00020\u0004J \u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00052\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\u0010\u001a\u00020\u0004J\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u00052\u0006\u0010\u0010\u001a\u00020\u0004R\u001c\u0010\u0003\u001a\u00020\u0004*\u0006\u0012\u0002\b\u00030\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001c\u0010\u0003\u001a\u00020\u0004*\u0006\u0012\u0002\b\u00030\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\t¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/library/abi/impl/FlagFieldEx$Companion;", "", "()V", "nextOffset", "", "Lorg/jetbrains/kotlin/library/abi/impl/FlagFieldEx;", "getNextOffset", "(Lorg/jetbrains/kotlin/library/abi/impl/FlagFieldEx;)I", "Lorg/jetbrains/kotlin/metadata/deserialization/Flags$FlagField;", "(Lorg/jetbrains/kotlin/metadata/deserialization/Flags$FlagField;)I", "after", ExifInterface.LONGITUDE_EAST, "", "previous", "first", "intAfter", "bitWidth", "intFirst", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FlagFieldEx<Integer> intFirst(int bitWidth) {
            return new IntFlagFieldEx(0, bitWidth);
        }

        public final FlagFieldEx<Integer> intAfter(Flags.FlagField<?> previous, int bitWidth) {
            Intrinsics.checkNotNullParameter(previous, "previous");
            return new IntFlagFieldEx(getNextOffset(previous), bitWidth);
        }

        public final FlagFieldEx<Integer> intAfter(FlagFieldEx<?> previous, int bitWidth) {
            Intrinsics.checkNotNullParameter(previous, "previous");
            return new IntFlagFieldEx(getNextOffset(previous), bitWidth);
        }

        public final /* synthetic */ <E extends Enum<E>> FlagFieldEx<E> first() {
            Intrinsics.reifiedOperationMarker(5, ExifInterface.LONGITUDE_EAST);
            return new EnumFlagFieldEx(0, new Enum[0]);
        }

        public final /* synthetic */ <E extends Enum<E>> FlagFieldEx<E> after(Flags.FlagField<?> previous) {
            Intrinsics.checkNotNullParameter(previous, "previous");
            int nextOffset = getNextOffset(previous);
            Intrinsics.reifiedOperationMarker(5, ExifInterface.LONGITUDE_EAST);
            return new EnumFlagFieldEx(nextOffset, new Enum[0]);
        }

        public final /* synthetic */ <E extends Enum<E>> FlagFieldEx<E> after(FlagFieldEx<?> previous) {
            Intrinsics.checkNotNullParameter(previous, "previous");
            int nextOffset = getNextOffset(previous);
            Intrinsics.reifiedOperationMarker(5, ExifInterface.LONGITUDE_EAST);
            return new EnumFlagFieldEx(nextOffset, new Enum[0]);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final int getNextOffset(Flags.FlagField<?> flagField) {
            return flagField.offset + flagField.bitWidth;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final int getNextOffset(FlagFieldEx<?> flagFieldEx) {
            return flagFieldEx.getOffset() + flagFieldEx.getBitWidth();
        }
    }
}
