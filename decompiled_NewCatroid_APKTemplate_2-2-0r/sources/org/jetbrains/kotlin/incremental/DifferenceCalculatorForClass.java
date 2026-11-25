package org.jetbrains.kotlin.incremental;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;

/* JADX INFO: compiled from: protoDifferenceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0016R\u0014\u0010\u0006\u001a\u00020\u0007X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/incremental/DifferenceCalculatorForClass;", "Lorg/jetbrains/kotlin/incremental/DifferenceCalculator;", "oldData", "Lorg/jetbrains/kotlin/incremental/ClassProtoData;", "newData", "(Lorg/jetbrains/kotlin/incremental/ClassProtoData;Lorg/jetbrains/kotlin/incremental/ClassProtoData;)V", "compareObject", "Lorg/jetbrains/kotlin/incremental/ProtoCompareGenerated;", "getCompareObject", "()Lorg/jetbrains/kotlin/incremental/ProtoCompareGenerated;", "difference", "Lorg/jetbrains/kotlin/incremental/Difference;", "Companion", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DifferenceCalculatorForClass extends DifferenceCalculator {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ProtoCompareGenerated compareObject;
    private final ClassProtoData newData;
    private final ClassProtoData oldData;

    /* JADX INFO: compiled from: protoDifferenceUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProtoCompareGenerated.ProtoBufClassKind.values().length];
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.COMPANION_OBJECT_NAME.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.NESTED_CLASS_NAME_LIST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.CONSTRUCTOR_LIST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.FUNCTION_LIST.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.PROPERTY_LIST.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.TYPE_ALIAS_LIST.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.ENUM_ENTRY_LIST.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.SEALED_SUBCLASS_FQ_NAME_LIST.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.VERSION_REQUIREMENT_LIST.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.VERSION_REQUIREMENT_TABLE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.FLAGS.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.FQ_NAME.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.TYPE_PARAMETER_LIST.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.JS_EXT_CLASS_ANNOTATION_LIST.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.SUPERTYPE_LIST.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.SUPERTYPE_ID_LIST.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.JVM_EXT_CLASS_MODULE_NAME.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.JS_EXT_CLASS_CONTAINING_FILE_ID.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.JVM_EXT_CLASS_LOCAL_VARIABLE_LIST.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.JAVA_EXT_IS_PACKAGE_PRIVATE_CLASS.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.BUILT_INS_EXT_CLASS_ANNOTATION_LIST.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.JVM_EXT_ANONYMOUS_OBJECT_ORIGIN_NAME.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.KLIB_EXT_CLASS_ANNOTATION_LIST.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.JVM_EXT_JVM_CLASS_FLAGS.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.INLINE_CLASS_UNDERLYING_PROPERTY_NAME.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.INLINE_CLASS_UNDERLYING_TYPE.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.INLINE_CLASS_UNDERLYING_TYPE_ID.ordinal()] = 27;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.MULTI_FIELD_VALUE_CLASS_UNDERLYING_NAME_LIST.ordinal()] = 28;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.MULTI_FIELD_VALUE_CLASS_UNDERLYING_TYPE_LIST.ordinal()] = 29;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.MULTI_FIELD_VALUE_CLASS_UNDERLYING_TYPE_ID_LIST.ordinal()] = 30;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.CONTEXT_RECEIVER_TYPE_LIST.ordinal()] = 31;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufClassKind.CONTEXT_RECEIVER_TYPE_ID_LIST.ordinal()] = 32;
            } catch (NoSuchFieldError unused32) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public DifferenceCalculatorForClass(ClassProtoData oldData, ClassProtoData newData) {
        Intrinsics.checkNotNullParameter(oldData, "oldData");
        Intrinsics.checkNotNullParameter(newData, "newData");
        this.oldData = oldData;
        this.newData = newData;
        this.compareObject = new ProtoCompareGenerated(oldData.getNameResolver(), newData.getNameResolver(), ProtoDifferenceUtilsKt.getTypeTableOrNull(oldData.getProto()), ProtoDifferenceUtilsKt.getTypeTableOrNull(newData.getProto()));
    }

    @Override // org.jetbrains.kotlin.incremental.DifferenceCalculator
    protected ProtoCompareGenerated getCompareObject() {
        return this.compareObject;
    }

    @Override // org.jetbrains.kotlin.incremental.DifferenceCalculator
    public Difference difference() {
        ClassProtoData classProtoData = this.oldData;
        ProtoBuf.Class proto = classProtoData.getProto();
        NameResolver nameResolver = classProtoData.getNameResolver();
        ClassProtoData classProtoData2 = this.newData;
        ProtoBuf.Class proto2 = classProtoData2.getProto();
        NameResolver nameResolver2 = classProtoData2.getNameResolver();
        EnumSet<ProtoCompareGenerated.ProtoBufClassKind> enumSetDifference = getCompareObject().difference(proto, proto2);
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        boolean z = ProtoDifferenceUtilsKt.isSealed(proto2) && ProtoDifferenceUtilsKt.isSealed(proto);
        boolean z2 = false;
        while (true) {
            boolean z3 = z2;
            for (ProtoCompareGenerated.ProtoBufClassKind protoBufClassKind : enumSetDifference) {
                Intrinsics.checkNotNull(protoBufClassKind);
                switch (WhenMappings.$EnumSwitchMapping$0[protoBufClassKind.ordinal()]) {
                    case 1:
                        if (proto.hasCompanionObjectName()) {
                            difference$oldToNames(proto.getCompanionObjectName(), hashSet2, nameResolver);
                        }
                        if (proto2.hasCompanionObjectName()) {
                            difference$newToNames(proto2.getCompanionObjectName(), hashSet2, nameResolver2);
                        }
                        Unit unit = Unit.INSTANCE;
                        z2 = true;
                        break;
                    case 2:
                        if (z) {
                            z2 = true;
                        }
                        List<Integer> nestedClassNameList = proto.getNestedClassNameList();
                        Intrinsics.checkNotNullExpressionValue(nestedClassNameList, "getNestedClassNameList(...)");
                        List<Integer> nestedClassNameList2 = proto2.getNestedClassNameList();
                        Intrinsics.checkNotNullExpressionValue(nestedClassNameList2, "getNestedClassNameList(...)");
                        Boolean.valueOf(hashSet2.addAll(calcDifferenceForNames(nestedClassNameList, nestedClassNameList2)));
                        break;
                    case 3:
                        z2 = z2 || !difference$calcDifferenceForNonPrivateMembers(proto, proto2, this, DifferenceCalculatorForClass$difference$unused$differentNonPrivateConstructors$1.INSTANCE).isEmpty();
                        Unit unit2 = Unit.INSTANCE;
                        break;
                    case 4:
                        Boolean.valueOf(hashSet2.addAll(difference$calcDifferenceForNonPrivateMembers(proto, proto2, this, DifferenceCalculatorForClass$difference$unused$1.INSTANCE)));
                        break;
                    case 5:
                        Boolean.valueOf(hashSet2.addAll(difference$calcDifferenceForNonPrivateMembers(proto, proto2, this, DifferenceCalculatorForClass$difference$unused$2.INSTANCE)));
                        break;
                    case 6:
                        Boolean.valueOf(hashSet2.addAll(difference$calcDifferenceForNonPrivateMembers(proto, proto2, this, DifferenceCalculatorForClass$difference$unused$3.INSTANCE)));
                        break;
                    case 7:
                        Unit unit3 = Unit.INSTANCE;
                        z2 = true;
                        break;
                    case 8:
                        Unit unit4 = Unit.INSTANCE;
                        z2 = true;
                        break;
                    case 9:
                    case 10:
                        Unit unit5 = Unit.INSTANCE;
                        break;
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                        Unit unit6 = Unit.INSTANCE;
                        z2 = true;
                        break;
                    case 15:
                    case 16:
                        List<ProtoBuf.Type> supertypeList = proto.getSupertypeList();
                        Intrinsics.checkNotNullExpressionValue(supertypeList, "getSupertypeList(...)");
                        List<ProtoBuf.Type> list = supertypeList;
                        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                        Iterator<T> it = list.iterator();
                        while (it.hasNext()) {
                            arrayList.add(NameResolverUtilKt.getClassId(nameResolver, ((ProtoBuf.Type) it.next()).getClassName()).asSingleFqName());
                        }
                        ArrayList arrayList2 = arrayList;
                        List<ProtoBuf.Type> supertypeList2 = proto2.getSupertypeList();
                        Intrinsics.checkNotNullExpressionValue(supertypeList2, "getSupertypeList(...)");
                        List<ProtoBuf.Type> list2 = supertypeList2;
                        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                        Iterator<T> it2 = list2.iterator();
                        while (it2.hasNext()) {
                            arrayList3.add(NameResolverUtilKt.getClassId(nameResolver2, ((ProtoBuf.Type) it2.next()).getClassName()).asSingleFqName());
                        }
                        ArrayList arrayList4 = arrayList2;
                        ArrayList arrayList5 = arrayList3;
                        Boolean.valueOf(hashSet.addAll(CollectionsKt.subtract(CollectionsKt.union(arrayList4, arrayList5), CollectionsKt.intersect(arrayList4, arrayList5))));
                        z2 = true;
                        break;
                    case 17:
                    case 18:
                    case 19:
                        Unit unit7 = Unit.INSTANCE;
                        break;
                    case 20:
                        Unit unit8 = Unit.INSTANCE;
                        z2 = true;
                        break;
                    case 21:
                        Unit unit9 = Unit.INSTANCE;
                        z2 = true;
                        break;
                    case 22:
                        Unit unit10 = Unit.INSTANCE;
                        break;
                    case 23:
                        Unit unit11 = Unit.INSTANCE;
                        z2 = true;
                        break;
                    case 24:
                        Unit unit12 = Unit.INSTANCE;
                        z2 = true;
                        break;
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                        Unit unit13 = Unit.INSTANCE;
                        z2 = true;
                        break;
                    case 31:
                    case 32:
                        Unit unit14 = Unit.INSTANCE;
                        z2 = true;
                        break;
                    default:
                        throw new NoWhenBranchMatchedException();
                }
            }
            return new Difference(z2, z3, hashSet2, hashSet);
        }
    }

    private static final boolean difference$oldToNames(int i, HashSet<String> hashSet, NameResolver nameResolver) {
        return hashSet.add(nameResolver.getString(i));
    }

    private static final boolean difference$newToNames(int i, HashSet<String> hashSet, NameResolver nameResolver) {
        return hashSet.add(nameResolver.getString(i));
    }

    private static final Collection<String> difference$calcDifferenceForNonPrivateMembers(ProtoBuf.Class r3, ProtoBuf.Class r4, DifferenceCalculatorForClass differenceCalculatorForClass, Function1<? super ProtoBuf.Class, ? extends List<? extends MessageLite>> function1) {
        List<? extends MessageLite> listInvoke = function1.invoke(r3);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listInvoke) {
            if (!ProtoDifferenceUtilsKt.isPrivate((MessageLite) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        List<? extends MessageLite> listInvoke2 = function1.invoke(r4);
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : listInvoke2) {
            if (!ProtoDifferenceUtilsKt.isPrivate((MessageLite) obj2)) {
                arrayList3.add(obj2);
            }
        }
        return differenceCalculatorForClass.calcDifferenceForMembers(arrayList2, arrayList3);
    }

    /* JADX INFO: compiled from: protoDifferenceUtils.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004*\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/DifferenceCalculatorForClass$Companion;", "", "()V", "getNonPrivateMembers", "", "", "Lorg/jetbrains/kotlin/incremental/ClassProtoData;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<String> getNonPrivateMembers(ClassProtoData classProtoData) {
            Intrinsics.checkNotNullParameter(classProtoData, "<this>");
            List listListOf = CollectionsKt.listOf((Object[]) new KFunction[]{DifferenceCalculatorForClass$Companion$getNonPrivateMembers$membersResolvers$1.INSTANCE, DifferenceCalculatorForClass$Companion$getNonPrivateMembers$membersResolvers$2.INSTANCE, DifferenceCalculatorForClass$Companion$getNonPrivateMembers$membersResolvers$3.INSTANCE, DifferenceCalculatorForClass$Companion$getNonPrivateMembers$membersResolvers$4.INSTANCE, DifferenceCalculatorForClass$Companion$getNonPrivateMembers$membersResolvers$5.INSTANCE});
            ArrayList arrayList = new ArrayList();
            Iterator it = listListOf.iterator();
            while (it.hasNext()) {
                Iterable iterable = (Iterable) ((Function1) it.next()).invoke(classProtoData.getProto());
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : iterable) {
                    if (!ProtoDifferenceUtilsKt.isPrivate((MessageLite) obj)) {
                        arrayList2.add(obj);
                    }
                }
                CollectionsKt.addAll(arrayList, ProtoDifferenceUtilsKt.names(arrayList2, classProtoData.getNameResolver()));
            }
            return arrayList;
        }
    }
}
