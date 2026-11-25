package org.jetbrains.kotlin.fir.java.enhancement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributes;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributesKt;
import org.jetbrains.kotlin.fir.types.ConeAttributes;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionIn;
import org.jetbrains.kotlin.fir.types.ConeKotlinTypeProjectionOut;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeRawType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeStarProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeProjectionKt;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ProjectionKind;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.load.java.typeEnhancement.JavaTypeQualifiers;
import org.jetbrains.kotlin.load.java.typeEnhancement.MutabilityQualifier;
import org.jetbrains.kotlin.load.java.typeEnhancement.NullabilityQualifier;
import org.jetbrains.kotlin.load.java.typeEnhancement.TypeComponentPosition;
import org.jetbrains.kotlin.load.java.typeEnhancement.TypeComponentPositionKt;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: javaTypeUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000P\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004H\u0002\u001a.\u0010\u0005\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\u0016\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\n0\tj\u0002`\u000bH\u0000\u001aD\u0010\f\u001a\u0004\u0018\u00010\u0002*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\u0016\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\n0\tj\u0002`\u000b2\u0006\u0010\r\u001a\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000fH\u0002\u001aV\u0010\u0010\u001a\u0004\u0018\u00010\u0011*\u00020\u00112\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00132\u0016\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\n0\tj\u0002`\u000b2\u0006\u0010\r\u001a\u00020\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u000f2\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u0002\u001a\u001c\u0010\u0016\u001a\u00020\u0017*\u00020\u00172\u0006\u0010\b\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a\u000e\u0010\u0018\u001a\u0004\u0018\u00010\u0019*\u00020\u0019H\u0002\u001a\u000e\u0010\u001a\u001a\u0004\u0018\u00010\u0019*\u00020\u0019H\u0000¨\u0006\u001b"}, d2 = {"computeSubtreeSizes", "", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "result", "", "enhance", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "qualifiers", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/JavaTypeQualifiers;", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/IndexedJavaTypeQualifiers;", "enhanceConeKotlinType", "index", "subtreeSizes", "", "enhanceInflexibleType", "Lorg/jetbrains/kotlin/fir/types/ConeSimpleKotlinType;", "position", "Lorg/jetbrains/kotlin/load/java/typeEnhancement/TypeComponentPosition;", "isFromDefinitelyNotNullType", "", "enhanceMutability", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassifierLookupTag;", "mutableToReadOnly", "Lorg/jetbrains/kotlin/name/ClassId;", "readOnlyToMutable", SuffixConstants.EXTENSION_java}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JavaTypeUtilsKt {

    /* JADX INFO: compiled from: javaTypeUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;

        static {
            int[] iArr = new int[NullabilityQualifier.values().length];
            try {
                iArr[NullabilityQualifier.NULLABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[NullabilityQualifier.NOT_NULL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ProjectionKind.values().length];
            try {
                iArr2[ProjectionKind.IN.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[ProjectionKind.OUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[ProjectionKind.STAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[ProjectionKind.INVARIANT.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[MutabilityQualifier.values().length];
            try {
                iArr3[MutabilityQualifier.READ_ONLY.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr3[MutabilityQualifier.MUTABLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$2 = iArr3;
        }
    }

    public static final ConeKotlinType enhance(ConeKotlinType coneKotlinType, FirSession session, Function1<? super Integer, JavaTypeQualifiers> qualifiers) {
        Intrinsics.checkNotNullParameter(coneKotlinType, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(qualifiers, "qualifiers");
        ArrayList arrayList = new ArrayList();
        computeSubtreeSizes(coneKotlinType, arrayList);
        Unit unit = Unit.INSTANCE;
        return enhanceConeKotlinType(coneKotlinType, session, qualifiers, 0, arrayList);
    }

    private static final int computeSubtreeSizes(ConeKotlinType coneKotlinType, List<Integer> list) {
        int size = list.size();
        int i = 0;
        list.add(0);
        ConeTypeProjection[] typeArguments = coneKotlinType.getTypeArguments();
        int length = typeArguments.length;
        int i2 = 0;
        while (true) {
            int iComputeSubtreeSizes = 1;
            if (i >= length) {
                list.set(size, Integer.valueOf(1 + i2));
                return list.get(size).intValue();
            }
            ConeKotlinType type = ConeTypeProjectionKt.getType(typeArguments[i]);
            if (type != null) {
                iComputeSubtreeSizes = computeSubtreeSizes(type, list);
            } else {
                list.add(1);
            }
            i2 += iComputeSubtreeSizes;
            i++;
        }
    }

    private static final ConeKotlinType enhanceConeKotlinType(ConeKotlinType coneKotlinType, FirSession firSession, Function1<? super Integer, JavaTypeQualifiers> function1, int i, List<Integer> list) {
        if (coneKotlinType instanceof ConeFlexibleType) {
            if (function1.invoke(Integer.valueOf(i)).getIsNullabilityQualifierForWarning()) {
                return null;
            }
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) coneKotlinType;
            ConeSimpleKotlinType coneSimpleKotlinTypeEnhanceInflexibleType$default = enhanceInflexibleType$default(coneFlexibleType.getLowerBound(), firSession, TypeComponentPosition.FLEXIBLE_LOWER, function1, i, list, false, 32, null);
            ConeSimpleKotlinType coneSimpleKotlinTypeEnhanceInflexibleType$default2 = enhanceInflexibleType$default(coneFlexibleType.getUpperBound(), firSession, TypeComponentPosition.FLEXIBLE_UPPER, function1, i, list, false, 32, null);
            if (coneSimpleKotlinTypeEnhanceInflexibleType$default == null && coneSimpleKotlinTypeEnhanceInflexibleType$default2 == null) {
                return null;
            }
            if (coneKotlinType instanceof ConeRawType) {
                ConeRawType.Companion companion = ConeRawType.INSTANCE;
                if (coneSimpleKotlinTypeEnhanceInflexibleType$default == null) {
                    coneSimpleKotlinTypeEnhanceInflexibleType$default = coneFlexibleType.getLowerBound();
                }
                if (coneSimpleKotlinTypeEnhanceInflexibleType$default2 == null) {
                    coneSimpleKotlinTypeEnhanceInflexibleType$default2 = coneFlexibleType.getUpperBound();
                }
                return companion.create(coneSimpleKotlinTypeEnhanceInflexibleType$default, coneSimpleKotlinTypeEnhanceInflexibleType$default2);
            }
            ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(firSession);
            if (coneSimpleKotlinTypeEnhanceInflexibleType$default == null) {
                coneSimpleKotlinTypeEnhanceInflexibleType$default = coneFlexibleType.getLowerBound();
            }
            ConeSimpleKotlinType coneSimpleKotlinType = coneSimpleKotlinTypeEnhanceInflexibleType$default;
            if (coneSimpleKotlinTypeEnhanceInflexibleType$default2 == null) {
                coneSimpleKotlinTypeEnhanceInflexibleType$default2 = coneFlexibleType.getUpperBound();
            }
            return TypeUtilsKt.coneFlexibleOrSimpleType(typeContext, coneSimpleKotlinType, coneSimpleKotlinTypeEnhanceInflexibleType$default2);
        }
        if (coneKotlinType instanceof ConeSimpleKotlinType) {
            return enhanceInflexibleType$default((ConeSimpleKotlinType) coneKotlinType, firSession, TypeComponentPosition.INFLEXIBLE, function1, i, list, false, 32, null);
        }
        return null;
    }

    public static final ClassId readOnlyToMutable(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "<this>");
        return JavaToKotlinClassMap.INSTANCE.readOnlyToMutable(classId);
    }

    private static final ClassId mutableToReadOnly(ClassId classId) {
        return JavaToKotlinClassMap.INSTANCE.mutableToReadOnly(classId);
    }

    static /* synthetic */ ConeSimpleKotlinType enhanceInflexibleType$default(ConeSimpleKotlinType coneSimpleKotlinType, FirSession firSession, TypeComponentPosition typeComponentPosition, Function1 function1, int i, List list, boolean z, int i2, Object obj) {
        if ((i2 & 32) != 0) {
            z = false;
        }
        return enhanceInflexibleType(coneSimpleKotlinType, firSession, typeComponentPosition, function1, i, list, z);
    }

    private static final ConeSimpleKotlinType enhanceInflexibleType(ConeSimpleKotlinType coneSimpleKotlinType, FirSession firSession, TypeComponentPosition typeComponentPosition, Function1<? super Integer, JavaTypeQualifiers> function1, int i, List<Integer> list, boolean z) {
        boolean zIsNullable;
        ConeKotlinTypeProjectionIn coneKotlinTypeProjectionIn;
        ConeKotlinType coneKotlinTypeEnhanceConeKotlinType;
        if (coneSimpleKotlinType instanceof ConeDefinitelyNotNullType) {
            return enhanceInflexibleType(((ConeDefinitelyNotNullType) coneSimpleKotlinType).getOriginal(), firSession, typeComponentPosition, function1, i, list, true);
        }
        boolean zShouldEnhance = TypeComponentPositionKt.shouldEnhance(typeComponentPosition);
        if ((!zShouldEnhance && coneSimpleKotlinType.getTypeArguments().length == 0) || !(coneSimpleKotlinType instanceof ConeLookupTagBasedType)) {
            return null;
        }
        JavaTypeQualifiers javaTypeQualifiersInvoke = function1.invoke(Integer.valueOf(i));
        ConeLookupTagBasedType coneLookupTagBasedType = (ConeLookupTagBasedType) coneSimpleKotlinType;
        ConeClassifierLookupTag coneClassifierLookupTagEnhanceMutability = enhanceMutability(coneLookupTagBasedType.getLookupTag(), javaTypeQualifiersInvoke, typeComponentPosition);
        NullabilityQualifier nullability = javaTypeQualifiersInvoke.getNullability();
        if (!zShouldEnhance || javaTypeQualifiersInvoke.getIsNullabilityQualifierForWarning()) {
            nullability = null;
        }
        int i2 = nullability == null ? -1 : WhenMappings.$EnumSwitchMapping$0[nullability.ordinal()];
        int i3 = 1;
        if (i2 != 1) {
            zIsNullable = i2 != 2 ? ConeTypeUtilsKt.isNullable(coneSimpleKotlinType) : false;
        } else {
            zIsNullable = true;
        }
        int i4 = i + 1;
        ConeTypeProjection[] typeArguments = coneSimpleKotlinType.getTypeArguments();
        ArrayList arrayList = new ArrayList(typeArguments.length);
        int length = typeArguments.length;
        int i5 = 0;
        while (i5 < length) {
            ConeTypeProjection coneTypeProjection = typeArguments[i5];
            int iIntValue = i4 + list.get(i4).intValue();
            ConeKotlinType type = ConeTypeProjectionKt.getType(coneTypeProjection);
            if (type == null || (coneKotlinTypeEnhanceConeKotlinType = enhanceConeKotlinType(type, firSession, function1, i4, list)) == null) {
                coneKotlinTypeProjectionIn = null;
            } else {
                int i6 = WhenMappings.$EnumSwitchMapping$1[coneTypeProjection.getKind().ordinal()];
                if (i6 == i3) {
                    coneKotlinTypeProjectionIn = new ConeKotlinTypeProjectionIn(coneKotlinTypeEnhanceConeKotlinType);
                } else if (i6 == 2) {
                    coneKotlinTypeProjectionIn = new ConeKotlinTypeProjectionOut(coneKotlinTypeEnhanceConeKotlinType);
                } else if (i6 == 3) {
                    coneKotlinTypeProjectionIn = ConeStarProjection.INSTANCE;
                } else {
                    if (i6 != 4) {
                        throw new NoWhenBranchMatchedException();
                    }
                    coneKotlinTypeProjectionIn = coneKotlinTypeEnhanceConeKotlinType;
                }
            }
            arrayList.add(coneKotlinTypeProjectionIn);
            i5++;
            i4 = iIntValue;
            i3 = 1;
        }
        ArrayList arrayList2 = arrayList;
        boolean z2 = nullability == NullabilityQualifier.NOT_NULL && !CompilerConeAttributesKt.getHasEnhancedNullability(coneSimpleKotlinType);
        if (Intrinsics.areEqual(coneLookupTagBasedType.getLookupTag(), coneClassifierLookupTagEnhanceMutability) && zIsNullable == ConeTypeUtilsKt.isNullable(coneSimpleKotlinType) && !z2) {
            ArrayList arrayList3 = arrayList2;
            if ((arrayList3 instanceof Collection) && arrayList3.isEmpty()) {
                return null;
            }
            Iterator it = arrayList3.iterator();
            while (it.hasNext()) {
                if (((ConeTypeProjection) it.next()) == null) {
                }
            }
            return null;
        }
        int length2 = coneSimpleKotlinType.getTypeArguments().length;
        ConeTypeProjection[] coneTypeProjectionArr = new ConeTypeProjection[length2];
        for (int i7 = 0; i7 < length2; i7++) {
            ConeTypeProjection coneTypeProjection2 = (ConeTypeProjection) arrayList2.get(i7);
            if (coneTypeProjection2 == null) {
                coneTypeProjection2 = coneSimpleKotlinType.getTypeArguments()[i7];
            }
            coneTypeProjectionArr[i7] = coneTypeProjection2;
        }
        ConeAttributes attributes = coneSimpleKotlinType.getAttributes();
        if (z2) {
            attributes = attributes.plus(CompilerConeAttributes.EnhancedNullability.INSTANCE);
        }
        ConeLookupTagBasedType coneLookupTagBasedTypeConstructType = TypeConstructionUtilsKt.constructType(coneClassifierLookupTagEnhanceMutability, coneTypeProjectionArr, zIsNullable, attributes);
        if (javaTypeQualifiersInvoke.getDefinitelyNotNull() || (z && nullability == null)) {
            ConeDefinitelyNotNullType coneDefinitelyNotNullTypeCreate$default = TypeUtilsKt.create$default(ConeDefinitelyNotNullType.INSTANCE, coneLookupTagBasedTypeConstructType, TypeComponentsKt.getTypeContext(firSession), false, 4, null);
            return coneDefinitelyNotNullTypeCreate$default != null ? coneDefinitelyNotNullTypeCreate$default : coneLookupTagBasedTypeConstructType;
        }
        return coneLookupTagBasedTypeConstructType;
    }

    private static final ConeClassifierLookupTag enhanceMutability(ConeClassifierLookupTag coneClassifierLookupTag, JavaTypeQualifiers javaTypeQualifiers, TypeComponentPosition typeComponentPosition) {
        if (!TypeComponentPositionKt.shouldEnhance(typeComponentPosition) || !(coneClassifierLookupTag instanceof ConeClassLikeLookupTag)) {
            return coneClassifierLookupTag;
        }
        MutabilityQualifier mutability = javaTypeQualifiers.getMutability();
        int i = mutability == null ? -1 : WhenMappings.$EnumSwitchMapping$2[mutability.ordinal()];
        if (i == 1) {
            ClassId classIdMutableToReadOnly = mutableToReadOnly(((ConeClassLikeLookupTag) coneClassifierLookupTag).getClassId());
            return (typeComponentPosition != TypeComponentPosition.FLEXIBLE_LOWER || classIdMutableToReadOnly == null) ? coneClassifierLookupTag : TypeConstructionUtilsKt.toLookupTag(classIdMutableToReadOnly);
        }
        if (i != 2) {
            return coneClassifierLookupTag;
        }
        ClassId onlyToMutable = readOnlyToMutable(((ConeClassLikeLookupTag) coneClassifierLookupTag).getClassId());
        return (typeComponentPosition != TypeComponentPosition.FLEXIBLE_UPPER || onlyToMutable == null) ? coneClassifierLookupTag : TypeConstructionUtilsKt.toLookupTag(onlyToMutable);
    }
}
