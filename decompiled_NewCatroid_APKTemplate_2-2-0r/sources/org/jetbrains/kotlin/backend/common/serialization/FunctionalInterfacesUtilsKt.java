package org.jetbrains.kotlin.backend.common.serialization;

import java.util.List;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: FunctionalInterfacesUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r\u001a\u000e\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u0007\"\u001c\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004\"\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"functionPattern", "Ljava/util/regex/Pattern;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getFunctionPattern", "()Ljava/util/regex/Pattern;", "functionTypeInterfacePackages", "", "", "getFunctionTypeInterfacePackages", "()Ljava/util/List;", "checkIsFunctionInterface", "", "idSig", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "checkIsFunctionTypeInterfacePackageFqName", "fqName", "ir.serialization.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FunctionalInterfacesUtilsKt {
    private static final Pattern functionPattern = Pattern.compile("^K?(Suspend)?Function\\d+$");
    private static final List<String> functionTypeInterfacePackages = CollectionsKt.listOf((Object[]) new String[]{IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "kotlin.coroutines", "kotlin.reflect"});

    public static final Pattern getFunctionPattern() {
        return functionPattern;
    }

    public static final List<String> getFunctionTypeInterfacePackages() {
        return functionTypeInterfacePackages;
    }

    public static final boolean checkIsFunctionTypeInterfacePackageFqName(String fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return functionTypeInterfacePackages.contains(fqName);
    }

    public static final boolean checkIsFunctionInterface(IdSignature idSignature) {
        IdSignature.CommonSignature accessorSignature = idSignature != null ? idSignature.getAccessorSignature() : null;
        return accessorSignature != null && checkIsFunctionTypeInterfacePackageFqName(accessorSignature.getPackageFqName()) && accessorSignature.getDeclarationFqName().length() > 0 && functionPattern.matcher(accessorSignature.getFirstNameSegment()).find();
    }
}
