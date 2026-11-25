package org.jetbrains.kotlin.name;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.descriptors.ClassKind;

/* JADX INFO: compiled from: NativeForwardDeclarationKind.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000f\b\u0086\u0081\u0002\u0018\u0000 \u001e2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u001eB'\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000eR\u0011\u0010\u0016\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000eR\u0011\u0010\u0018\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014j\u0002\b\u001bj\u0002\b\u001cj\u0002\b\u001d¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/name/NativeForwardDeclarationKind;", "", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "superClassName", "Lorg/jetbrains/kotlin/name/Name;", "matchSuperClassName", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "(Ljava/lang/String;ILorg/jetbrains/kotlin/name/FqName;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/ClassKind;)V", "getClassKind", "()Lorg/jetbrains/kotlin/descriptors/ClassKind;", "matchSuperClassFqName", "getMatchSuperClassFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "matchSuperClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "getMatchSuperClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getMatchSuperClassName", "()Lorg/jetbrains/kotlin/name/Name;", "getPackageFqName", "superClassFqName", "getSuperClassFqName", "superClassId", "getSuperClassId", "getSuperClassName", "Struct", "ObjCClass", "ObjCProtocol", "Companion", "compiler.common.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum NativeForwardDeclarationKind {
    Struct(NativeStandardInteropNames.ForwardDeclarations.INSTANCE.getCNamesStructsPackage(), NativeStandardInteropNames.INSTANCE.getCOpaque$compiler_common_native(), NativeStandardInteropNames.INSTANCE.getCStructVar$compiler_common_native(), ClassKind.CLASS),
    ObjCClass(NativeStandardInteropNames.ForwardDeclarations.INSTANCE.getObjCNamesClassesPackage(), NativeStandardInteropNames.INSTANCE.getObjCObjectBase$compiler_common_native(), NativeStandardInteropNames.INSTANCE.getObjCObjectBase$compiler_common_native(), ClassKind.CLASS),
    ObjCProtocol(NativeStandardInteropNames.ForwardDeclarations.INSTANCE.getObjCNamesProtocolsPackage(), NativeStandardInteropNames.INSTANCE.getObjCObject$compiler_common_native(), NativeStandardInteropNames.INSTANCE.getObjCObject$compiler_common_native(), ClassKind.INTERFACE);

    private static final Map<FqName, NativeForwardDeclarationKind> packageFqNameToKind;
    private final ClassKind classKind;
    private final FqName matchSuperClassFqName;
    private final ClassId matchSuperClassId;
    private final Name matchSuperClassName;
    private final FqName packageFqName;
    private final FqName superClassFqName;
    private final ClassId superClassId;
    private final Name superClassName;
    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    public static EnumEntries<NativeForwardDeclarationKind> getEntries() {
        return $ENTRIES;
    }

    NativeForwardDeclarationKind(FqName fqName, Name name2, Name name3, ClassKind classKind) {
        this.packageFqName = fqName;
        this.superClassName = name2;
        this.matchSuperClassName = name3;
        this.classKind = classKind;
        FqName fqNameChild = NativeStandardInteropNames.INSTANCE.getCInteropPackage().child(name2);
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        this.superClassFqName = fqNameChild;
        FqName fqNameChild2 = NativeStandardInteropNames.INSTANCE.getCInteropPackage().child(name3);
        Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
        this.matchSuperClassFqName = fqNameChild2;
        ClassId classId = ClassId.topLevel(fqNameChild);
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        this.superClassId = classId;
        ClassId classId2 = ClassId.topLevel(fqNameChild2);
        Intrinsics.checkNotNullExpressionValue(classId2, "topLevel(...)");
        this.matchSuperClassId = classId2;
    }

    public final ClassKind getClassKind() {
        return this.classKind;
    }

    public final Name getMatchSuperClassName() {
        return this.matchSuperClassName;
    }

    public final FqName getPackageFqName() {
        return this.packageFqName;
    }

    public final Name getSuperClassName() {
        return this.superClassName;
    }

    static {
        EnumEntries<NativeForwardDeclarationKind> entries = getEntries();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(entries, 10)), 16));
        for (NativeForwardDeclarationKind nativeForwardDeclarationKind : entries) {
            linkedHashMap.put(nativeForwardDeclarationKind.packageFqName, nativeForwardDeclarationKind);
        }
        packageFqNameToKind = linkedHashMap;
    }

    public final FqName getSuperClassFqName() {
        return this.superClassFqName;
    }

    public final FqName getMatchSuperClassFqName() {
        return this.matchSuperClassFqName;
    }

    public final ClassId getSuperClassId() {
        return this.superClassId;
    }

    public final ClassId getMatchSuperClassId() {
        return this.matchSuperClassId;
    }

    /* JADX INFO: compiled from: NativeForwardDeclarationKind.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/name/NativeForwardDeclarationKind$Companion;", "", "()V", "packageFqNameToKind", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/name/NativeForwardDeclarationKind;", "getPackageFqNameToKind", "()Ljava/util/Map;", "compiler.common.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Map<FqName, NativeForwardDeclarationKind> getPackageFqNameToKind() {
            return NativeForwardDeclarationKind.packageFqNameToKind;
        }
    }
}
