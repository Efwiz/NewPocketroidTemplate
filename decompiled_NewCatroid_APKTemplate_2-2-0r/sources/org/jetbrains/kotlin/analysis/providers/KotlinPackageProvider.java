package org.jetbrains.kotlin.analysis.providers;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.konan.library.NativeLibraryConstantsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.TargetPlatform;

/* JADX INFO: compiled from: KotlinPackageProvider.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH&J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH&J*\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00040\u000fH&J2\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00040\u000fH&J2\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00040\u000fH&¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/analysis/providers/KotlinPackageProvider;", "", "()V", "doesKotlinOnlyPackageExist", "", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "doesPackageExist", NativeLibraryConstantsKt.KONAN_DISTRIBUTION_PLATFORM_LIBS_DIR, "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "doesPlatformSpecificPackageExist", "getKotlinOnlySubPackagesFqNames", "", "Lorg/jetbrains/kotlin/name/Name;", "nameFilter", "Lkotlin/Function1;", "getPlatformSpecificSubPackagesFqNames", "getSubPackageFqNames", "analysis-api-providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KotlinPackageProvider {
    public abstract boolean doesKotlinOnlyPackageExist(FqName packageFqName);

    public abstract boolean doesPackageExist(FqName packageFqName, TargetPlatform platform);

    public abstract boolean doesPlatformSpecificPackageExist(FqName packageFqName, TargetPlatform platform);

    public abstract Set<Name> getKotlinOnlySubPackagesFqNames(FqName packageFqName, Function1<? super Name, Boolean> nameFilter);

    public abstract Set<Name> getPlatformSpecificSubPackagesFqNames(FqName packageFqName, TargetPlatform platform, Function1<? super Name, Boolean> nameFilter);

    public abstract Set<Name> getSubPackageFqNames(FqName packageFqName, TargetPlatform platform, Function1<? super Name, Boolean> nameFilter);
}
