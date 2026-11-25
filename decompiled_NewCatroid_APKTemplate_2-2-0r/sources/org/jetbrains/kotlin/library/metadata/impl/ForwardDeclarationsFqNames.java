package org.jetbrains.kotlin.library.metadata.impl;

import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.NativeStandardInteropNames;

/* JADX INFO: compiled from: KlibResolvedModuleDescriptorsFactoryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Deprecated(level = DeprecationLevel.ERROR, message = "This class was moved to org.jetbrains.kotlin.name.NativeStandardInteropNames.ForwardDeclarations")
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\"\n\u0002\b\u0003\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0014\u0010\u000b\u001a\u00020\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/library/metadata/impl/ForwardDeclarationsFqNames;", "", "()V", "cInterop", "Lorg/jetbrains/kotlin/name/FqName;", "getCInterop$kotlin_util_klib_metadata", "()Lorg/jetbrains/kotlin/name/FqName;", "cNamesStructs", "getCNamesStructs$kotlin_util_klib_metadata", "objCNamesClasses", "getObjCNamesClasses$kotlin_util_klib_metadata", "objCNamesProtocols", "getObjCNamesProtocols$kotlin_util_klib_metadata", "syntheticPackages", "", "getSyntheticPackages", "()Ljava/util/Set;", "kotlin-util-klib-metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ForwardDeclarationsFqNames {
    public static final ForwardDeclarationsFqNames INSTANCE = new ForwardDeclarationsFqNames();
    private static final FqName cInterop = NativeStandardInteropNames.INSTANCE.getCInteropPackage();
    private static final FqName cNamesStructs = NativeStandardInteropNames.ForwardDeclarations.INSTANCE.getCNamesStructsPackage();
    private static final FqName objCNamesClasses = NativeStandardInteropNames.ForwardDeclarations.INSTANCE.getObjCNamesClassesPackage();
    private static final FqName objCNamesProtocols = NativeStandardInteropNames.ForwardDeclarations.INSTANCE.getObjCNamesProtocolsPackage();
    private static final Set<FqName> syntheticPackages = NativeStandardInteropNames.ForwardDeclarations.INSTANCE.getSyntheticPackages();

    private ForwardDeclarationsFqNames() {
    }

    public final FqName getCInterop$kotlin_util_klib_metadata() {
        return cInterop;
    }

    public final FqName getCNamesStructs$kotlin_util_klib_metadata() {
        return cNamesStructs;
    }

    public final FqName getObjCNamesClasses$kotlin_util_klib_metadata() {
        return objCNamesClasses;
    }

    public final FqName getObjCNamesProtocols$kotlin_util_klib_metadata() {
        return objCNamesProtocols;
    }

    public final Set<FqName> getSyntheticPackages() {
        return syntheticPackages;
    }
}
