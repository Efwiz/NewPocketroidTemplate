package org.jetbrains.kotlin.fir.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.LongRange;
import okhttp3.internal.ws.WebSocketProtocol;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.fir.PrimitivesKt;
import org.jetbrains.kotlin.fir.diagnostics.ConeSimpleDiagnostic;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.types.impl.ConeClassLikeTypeImpl;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.types.AbstractTypeChecker;

/* JADX INFO: compiled from: ConeIntegerLiteralTypeImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0012\u0010\u0014\u001a\u00020\u00062\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR!\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u000f8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeIntegerLiteralConstantTypeImpl;", "Lorg/jetbrains/kotlin/fir/types/ConeIntegerLiteralConstantType;", "value", "", "possibleTypes", "", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "isUnsigned", "", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "(JLjava/util/Collection;ZLorg/jetbrains/kotlin/fir/types/ConeNullability;)V", "getPossibleTypes", "()Ljava/util/Collection;", "supertypes", "", "getSupertypes", "()Ljava/util/List;", "supertypes$delegate", "Lkotlin/Lazy;", "getApproximatedType", "expectedType", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Companion", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeIntegerLiteralConstantTypeImpl extends ConeIntegerLiteralConstantType {
    private static final LongRange UBYTE_RANGE;
    private static final LongRange UINT_RANGE;
    private static final LongRange USHORT_RANGE;
    private final Collection<ConeClassLikeType> possibleTypes;

    /* JADX INFO: renamed from: supertypes$delegate, reason: from kotlin metadata */
    private final Lazy supertypes;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final LongRange INT_RANGE = new LongRange(-2147483648L, 2147483647L);
    private static final LongRange BYTE_RANGE = new LongRange(-128, 127);
    private static final LongRange SHORT_RANGE = new LongRange(-32768, 32767);

    @Override // org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType
    public Collection<ConeClassLikeType> getPossibleTypes() {
        return this.possibleTypes;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ConeIntegerLiteralConstantTypeImpl(long j, Collection<? extends ConeClassLikeType> possibleTypes, boolean z, ConeNullability nullability) {
        super(j, z, nullability);
        Intrinsics.checkNotNullParameter(possibleTypes, "possibleTypes");
        Intrinsics.checkNotNullParameter(nullability, "nullability");
        this.possibleTypes = possibleTypes;
        this.supertypes = LazyKt.lazy(new Function0<List<? extends ConeClassLikeType>>() { // from class: org.jetbrains.kotlin.fir.types.ConeIntegerLiteralConstantTypeImpl$supertypes$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends ConeClassLikeType> invoke() {
                return ConeIntegerLiteralTypeExtensions.INSTANCE.createSupertypeList(this.this$0);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType
    public List<ConeClassLikeType> getSupertypes() {
        return (List) this.supertypes.getValue();
    }

    @Override // org.jetbrains.kotlin.fir.types.ConeIntegerLiteralType
    public ConeClassLikeType getApproximatedType(ConeKotlinType expectedType) {
        return ConeIntegerLiteralTypeExtensions.INSTANCE.getApproximatedTypeImpl(this, expectedType);
    }

    /* JADX INFO: compiled from: ConeIntegerLiteralTypeImpl.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J4\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000f0\u00112\b\b\u0002\u0010\u0013\u001a\u00020\u0014J\u0010\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/types/ConeIntegerLiteralConstantTypeImpl$Companion;", "", "()V", "BYTE_RANGE", "Lkotlin/ranges/LongRange;", "INT_RANGE", "SHORT_RANGE", "UBYTE_RANGE", "UINT_RANGE", "USHORT_RANGE", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "value", "", "isUnsigned", "", "isTypePresent", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "nullability", "Lorg/jetbrains/kotlin/fir/types/ConeNullability;", "createType", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ ConeSimpleKotlinType create$default(Companion companion, long j, boolean z, Function1 function1, ConeNullability coneNullability, int i, Object obj) {
            if ((i & 8) != 0) {
                coneNullability = ConeNullability.NOT_NULL;
            }
            return companion.create(j, z, function1, coneNullability);
        }

        public final ConeSimpleKotlinType create(long value, boolean isUnsigned, Function1<? super ConeClassLikeType, Boolean> isTypePresent, ConeNullability nullability) {
            Intrinsics.checkNotNullParameter(isTypePresent, "isTypePresent");
            Intrinsics.checkNotNullParameter(nullability, "nullability");
            ArrayList arrayList = new ArrayList();
            if (isUnsigned) {
                create$addUnsignedPossibleType(arrayList, value);
                ArrayList arrayList2 = arrayList;
                if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
                    Iterator it = arrayList2.iterator();
                    while (it.hasNext()) {
                        if (!isTypePresent.invoke((ConeClassLikeType) it.next()).booleanValue()) {
                            return new ConeErrorType(new ConeSimpleDiagnostic("Unsigned integers need stdlib", DiagnosticKind.UnsignedNumbersAreNotPresent), false, null, null, 14, null);
                        }
                    }
                }
            } else {
                create$addSignedPossibleTypes(arrayList, value);
            }
            if (arrayList.size() == 1) {
                ConeClassLikeType coneClassLikeTypeWithNullabilityAndAttributes = ConeIntegerLiteralTypeExtensions.INSTANCE.withNullabilityAndAttributes((ConeClassLikeType) CollectionsKt.single((List) arrayList), nullability, ConeAttributes.INSTANCE.getEmpty());
                if (AbstractTypeChecker.RUN_SLOW_ASSERTIONS && !PrimitivesKt.isLong(coneClassLikeTypeWithNullabilityAndAttributes)) {
                    PrimitivesKt.isULong(coneClassLikeTypeWithNullabilityAndAttributes);
                }
                return coneClassLikeTypeWithNullabilityAndAttributes;
            }
            return new ConeIntegerLiteralConstantTypeImpl(value, arrayList, isUnsigned, nullability);
        }

        private static final void create$checkBoundsAndAddPossibleType(long j, List<ConeClassLikeType> list, ClassId classId, LongRange longRange) {
            long first = longRange.getFirst();
            if (j > longRange.getLast() || first > j) {
                return;
            }
            list.add(ConeIntegerLiteralConstantTypeImpl.INSTANCE.createType(classId));
        }

        private static final void create$addSignedPossibleTypes(List<ConeClassLikeType> list, long j) {
            create$checkBoundsAndAddPossibleType(j, list, StandardClassIds.INSTANCE.getInt(), ConeIntegerLiteralConstantTypeImpl.INT_RANGE);
            list.add(ConeIntegerLiteralConstantTypeImpl.INSTANCE.createType(StandardClassIds.INSTANCE.getLong()));
            create$checkBoundsAndAddPossibleType(j, list, StandardClassIds.INSTANCE.getByte(), ConeIntegerLiteralConstantTypeImpl.BYTE_RANGE);
            create$checkBoundsAndAddPossibleType(j, list, StandardClassIds.INSTANCE.getShort(), ConeIntegerLiteralConstantTypeImpl.SHORT_RANGE);
        }

        private static final void create$addUnsignedPossibleType(List<ConeClassLikeType> list, long j) {
            create$checkBoundsAndAddPossibleType(j, list, StandardClassIds.INSTANCE.getUInt(), ConeIntegerLiteralConstantTypeImpl.UINT_RANGE);
            list.add(ConeIntegerLiteralConstantTypeImpl.INSTANCE.createType(StandardClassIds.INSTANCE.getULong()));
            create$checkBoundsAndAddPossibleType(j, list, StandardClassIds.INSTANCE.getUByte(), ConeIntegerLiteralConstantTypeImpl.UBYTE_RANGE);
            create$checkBoundsAndAddPossibleType(j, list, StandardClassIds.INSTANCE.getUShort(), ConeIntegerLiteralConstantTypeImpl.USHORT_RANGE);
        }

        private final ConeClassLikeType createType(ClassId classId) {
            return new ConeClassLikeTypeImpl(TypeConstructionUtilsKt.toLookupTag(classId), new ConeTypeProjection[0], false, null, 8, null);
        }
    }

    static {
        long j = -1;
        UBYTE_RANGE = new LongRange(0L, 255 & j);
        USHORT_RANGE = new LongRange(0L, WebSocketProtocol.PAYLOAD_SHORT_MAX & j);
        UINT_RANGE = new LongRange(0L, j & 4294967295L);
    }
}
