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
import org.jetbrains.kotlin.protobuf.MessageLite;

/* JADX INFO: compiled from: protoDifferenceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \f2\u00020\u0001:\u0001\fB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0016R\u0014\u0010\u0006\u001a\u00020\u0007X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/incremental/DifferenceCalculatorForPackageFacade;", "Lorg/jetbrains/kotlin/incremental/DifferenceCalculator;", "oldData", "Lorg/jetbrains/kotlin/incremental/PackagePartProtoData;", "newData", "(Lorg/jetbrains/kotlin/incremental/PackagePartProtoData;Lorg/jetbrains/kotlin/incremental/PackagePartProtoData;)V", "compareObject", "Lorg/jetbrains/kotlin/incremental/ProtoCompareGenerated;", "getCompareObject", "()Lorg/jetbrains/kotlin/incremental/ProtoCompareGenerated;", "difference", "Lorg/jetbrains/kotlin/incremental/Difference;", "Companion", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DifferenceCalculatorForPackageFacade extends DifferenceCalculator {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ProtoCompareGenerated compareObject;
    private final PackagePartProtoData newData;
    private final PackagePartProtoData oldData;

    /* JADX INFO: compiled from: protoDifferenceUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ProtoCompareGenerated.ProtoBufPackageKind.values().length];
            try {
                iArr[ProtoCompareGenerated.ProtoBufPackageKind.FUNCTION_LIST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufPackageKind.PROPERTY_LIST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufPackageKind.TYPE_ALIAS_LIST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufPackageKind.VERSION_REQUIREMENT_TABLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufPackageKind.JVM_EXT_PACKAGE_MODULE_NAME.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufPackageKind.JS_EXT_PACKAGE_FQ_NAME.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufPackageKind.JVM_EXT_PACKAGE_LOCAL_VARIABLE_LIST.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufPackageKind.BUILT_INS_EXT_PACKAGE_FQ_NAME.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[ProtoCompareGenerated.ProtoBufPackageKind.KLIB_EXT_PACKAGE_FQ_NAME.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public DifferenceCalculatorForPackageFacade(PackagePartProtoData oldData, PackagePartProtoData newData) {
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
        ProtoBuf.Package proto = this.oldData.getProto();
        ProtoBuf.Package proto2 = this.newData.getProto();
        EnumSet<ProtoCompareGenerated.ProtoBufPackageKind> enumSetDifference = getCompareObject().difference(proto, proto2);
        HashSet hashSet = new HashSet();
        for (ProtoCompareGenerated.ProtoBufPackageKind protoBufPackageKind : enumSetDifference) {
            Intrinsics.checkNotNull(protoBufPackageKind);
            switch (WhenMappings.$EnumSwitchMapping$0[protoBufPackageKind.ordinal()]) {
                case 1:
                    Boolean.valueOf(hashSet.addAll(difference$calcDifferenceForNonPrivateMembers(proto, proto2, this, DifferenceCalculatorForPackageFacade$difference$unused$1.INSTANCE)));
                    break;
                case 2:
                    Boolean.valueOf(hashSet.addAll(difference$calcDifferenceForNonPrivateMembers(proto, proto2, this, DifferenceCalculatorForPackageFacade$difference$unused$2.INSTANCE)));
                    break;
                case 3:
                    Boolean.valueOf(hashSet.addAll(difference$calcDifferenceForNonPrivateMembers(proto, proto2, this, DifferenceCalculatorForPackageFacade$difference$unused$3.INSTANCE)));
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    Unit unit = Unit.INSTANCE;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
        }
        return new Difference(false, false, hashSet, null, 11, null);
    }

    private static final Collection<String> difference$calcDifferenceForNonPrivateMembers(ProtoBuf.Package r3, ProtoBuf.Package r4, DifferenceCalculatorForPackageFacade differenceCalculatorForPackageFacade, Function1<? super ProtoBuf.Package, ? extends List<? extends MessageLite>> function1) {
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
        return differenceCalculatorForPackageFacade.calcDifferenceForMembers(arrayList2, arrayList3);
    }

    /* JADX INFO: compiled from: protoDifferenceUtils.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004*\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/incremental/DifferenceCalculatorForPackageFacade$Companion;", "", "()V", "getNonPrivateMembers", "", "", "Lorg/jetbrains/kotlin/incremental/PackagePartProtoData;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<String> getNonPrivateMembers(PackagePartProtoData packagePartProtoData) {
            Intrinsics.checkNotNullParameter(packagePartProtoData, "<this>");
            List listListOf = CollectionsKt.listOf((Object[]) new KFunction[]{DifferenceCalculatorForPackageFacade$Companion$getNonPrivateMembers$membersResolvers$1.INSTANCE, DifferenceCalculatorForPackageFacade$Companion$getNonPrivateMembers$membersResolvers$2.INSTANCE, DifferenceCalculatorForPackageFacade$Companion$getNonPrivateMembers$membersResolvers$3.INSTANCE});
            ArrayList arrayList = new ArrayList();
            Iterator it = listListOf.iterator();
            while (it.hasNext()) {
                Iterable iterable = (Iterable) ((Function1) it.next()).invoke(packagePartProtoData.getProto());
                ArrayList arrayList2 = new ArrayList();
                for (Object obj : iterable) {
                    if (!ProtoDifferenceUtilsKt.isPrivate((MessageLite) obj)) {
                        arrayList2.add(obj);
                    }
                }
                CollectionsKt.addAll(arrayList, ProtoDifferenceUtilsKt.names(arrayList2, packagePartProtoData.getNameResolver()));
            }
            return arrayList;
        }
    }
}
