package org.jetbrains.kotlin.fir;

import kotlin.Metadata;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.name.StandardClassIds;

/* JADX INFO: compiled from: Primitives.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0015\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006R\u0011\u0010\u0015\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0006R\u0011\u0010\u0017\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0006¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/StandardTypes;", "", "()V", "Any", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "getAny", "()Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "Boolean", "getBoolean", "Byte", "getByte", "Char", "getChar", "Double", "getDouble", "Float", "getFloat", "Int", "getInt", "Long", "getLong", "NullableAny", "getNullableAny", "Short", "getShort", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class StandardTypes {
    public static final StandardTypes INSTANCE = new StandardTypes();
    private static final ConeClassLikeType Boolean = PrimitivesKt.createType$default(StandardClassIds.INSTANCE.getBoolean(), false, 1, null);
    private static final ConeClassLikeType Char = PrimitivesKt.createType$default(StandardClassIds.INSTANCE.getChar(), false, 1, null);
    private static final ConeClassLikeType Byte = PrimitivesKt.createType$default(StandardClassIds.INSTANCE.getByte(), false, 1, null);
    private static final ConeClassLikeType Short = PrimitivesKt.createType$default(StandardClassIds.INSTANCE.getShort(), false, 1, null);
    private static final ConeClassLikeType Int = PrimitivesKt.createType$default(StandardClassIds.INSTANCE.getInt(), false, 1, null);
    private static final ConeClassLikeType Long = PrimitivesKt.createType$default(StandardClassIds.INSTANCE.getLong(), false, 1, null);
    private static final ConeClassLikeType Float = PrimitivesKt.createType$default(StandardClassIds.INSTANCE.getFloat(), false, 1, null);
    private static final ConeClassLikeType Double = PrimitivesKt.createType$default(StandardClassIds.INSTANCE.getDouble(), false, 1, null);
    private static final ConeClassLikeType Any = PrimitivesKt.createType$default(StandardClassIds.INSTANCE.getAny(), false, 1, null);
    private static final ConeClassLikeType NullableAny = PrimitivesKt.createType(StandardClassIds.INSTANCE.getAny(), true);

    private StandardTypes() {
    }

    public final ConeClassLikeType getBoolean() {
        return Boolean;
    }

    public final ConeClassLikeType getChar() {
        return Char;
    }

    public final ConeClassLikeType getByte() {
        return Byte;
    }

    public final ConeClassLikeType getShort() {
        return Short;
    }

    public final ConeClassLikeType getInt() {
        return Int;
    }

    public final ConeClassLikeType getLong() {
        return Long;
    }

    public final ConeClassLikeType getFloat() {
        return Float;
    }

    public final ConeClassLikeType getDouble() {
        return Double;
    }

    public final ConeClassLikeType getAny() {
        return Any;
    }

    public final ConeClassLikeType getNullableAny() {
        return NullableAny;
    }
}
