package org.jetbrains.kotlin.name;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NativeStandardInteropNames.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u0013B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0014\u0010\u000b\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0014\u0010\r\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/name/NativeStandardInteropNames;", "", "()V", "COpaque", "Lorg/jetbrains/kotlin/name/Name;", "getCOpaque$compiler_common_native", "()Lorg/jetbrains/kotlin/name/Name;", "CStructVar", "getCStructVar$compiler_common_native", "ExperimentalForeignApi", "getExperimentalForeignApi", "ObjCObject", "getObjCObject$compiler_common_native", "ObjCObjectBase", "getObjCObjectBase$compiler_common_native", "cInteropPackage", "Lorg/jetbrains/kotlin/name/FqName;", "getCInteropPackage", "()Lorg/jetbrains/kotlin/name/FqName;", "ForwardDeclarations", "compiler.common.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeStandardInteropNames {
    private static final Name COpaque;
    private static final Name CStructVar;
    private static final Name ExperimentalForeignApi;
    private static final Name ObjCObject;
    private static final Name ObjCObjectBase;
    public static final NativeStandardInteropNames INSTANCE = new NativeStandardInteropNames();
    private static final FqName cInteropPackage = new FqName("kotlinx.cinterop");

    private NativeStandardInteropNames() {
    }

    static {
        Name nameIdentifier = Name.identifier("COpaque");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        COpaque = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("CStructVar");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        CStructVar = nameIdentifier2;
        Name nameIdentifier3 = Name.identifier("ObjCObjectBase");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        ObjCObjectBase = nameIdentifier3;
        Name nameIdentifier4 = Name.identifier("ObjCObject");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
        ObjCObject = nameIdentifier4;
        Name nameIdentifier5 = Name.identifier("ExperimentalForeignApi");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier5, "identifier(...)");
        ExperimentalForeignApi = nameIdentifier5;
    }

    public final FqName getCInteropPackage() {
        return cInteropPackage;
    }

    public final Name getCOpaque$compiler_common_native() {
        return COpaque;
    }

    public final Name getCStructVar$compiler_common_native() {
        return CStructVar;
    }

    public final Name getObjCObjectBase$compiler_common_native() {
        return ObjCObjectBase;
    }

    public final Name getObjCObject$compiler_common_native() {
        return ObjCObject;
    }

    public final Name getExperimentalForeignApi() {
        return ExperimentalForeignApi;
    }

    /* JADX INFO: compiled from: NativeStandardInteropNames.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\"\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0007R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/name/NativeStandardInteropNames$ForwardDeclarations;", "", "()V", "cNamesPackage", "Lorg/jetbrains/kotlin/name/FqName;", "cNamesStructsPackage", "getCNamesStructsPackage", "()Lorg/jetbrains/kotlin/name/FqName;", "objCNamesClassesPackage", "getObjCNamesClassesPackage", "objCNamesPackage", "objCNamesProtocolsPackage", "getObjCNamesProtocolsPackage", "syntheticPackages", "", "getSyntheticPackages", "()Ljava/util/Set;", "compiler.common.native"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ForwardDeclarations {
        public static final ForwardDeclarations INSTANCE = new ForwardDeclarations();
        private static final FqName cNamesPackage;
        private static final FqName cNamesStructsPackage;
        private static final FqName objCNamesClassesPackage;
        private static final FqName objCNamesPackage;
        private static final FqName objCNamesProtocolsPackage;
        private static final Set<FqName> syntheticPackages;

        private ForwardDeclarations() {
        }

        static {
            FqName fqName = new FqName("cnames");
            cNamesPackage = fqName;
            FqName fqNameChild = fqName.child(Name.identifier("structs"));
            Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
            cNamesStructsPackage = fqNameChild;
            FqName fqName2 = new FqName("objcnames");
            objCNamesPackage = fqName2;
            FqName fqNameChild2 = fqName2.child(Name.identifier("classes"));
            Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
            objCNamesClassesPackage = fqNameChild2;
            FqName fqNameChild3 = fqName2.child(Name.identifier("protocols"));
            Intrinsics.checkNotNullExpressionValue(fqNameChild3, "child(...)");
            objCNamesProtocolsPackage = fqNameChild3;
            syntheticPackages = SetsKt.setOf((Object[]) new FqName[]{fqName, fqName2});
        }

        public final FqName getCNamesStructsPackage() {
            return cNamesStructsPackage;
        }

        public final FqName getObjCNamesClassesPackage() {
            return objCNamesClassesPackage;
        }

        public final FqName getObjCNamesProtocolsPackage() {
            return objCNamesProtocolsPackage;
        }

        public final Set<FqName> getSyntheticPackages() {
            return syntheticPackages;
        }
    }
}
