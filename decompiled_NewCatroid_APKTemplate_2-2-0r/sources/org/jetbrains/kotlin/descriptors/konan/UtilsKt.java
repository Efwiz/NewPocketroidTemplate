package org.jetbrains.kotlin.descriptors.konan;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.NativeForwardDeclarationKind;

/* JADX INFO: compiled from: utils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0004\u001a\u0004\u0018\u00010\u0005*\u00020\u0006\u001a\n\u0010\u0007\u001a\u00020\b*\u00020\t\"\u0011\u0010\u0000\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0003¨\u0006\n"}, d2 = {"NATIVE_STDLIB_MODULE_NAME", "Lorg/jetbrains/kotlin/name/Name;", "getNATIVE_STDLIB_MODULE_NAME", "()Lorg/jetbrains/kotlin/name/Name;", "getForwardDeclarationKindOrNull", "Lorg/jetbrains/kotlin/name/NativeForwardDeclarationKind;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "isNativeStdlib", "", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "frontend.native"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    private static final Name NATIVE_STDLIB_MODULE_NAME;

    static {
        Name nameSpecial = Name.special("<stdlib>");
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        NATIVE_STDLIB_MODULE_NAME = nameSpecial;
    }

    public static final Name getNATIVE_STDLIB_MODULE_NAME() {
        return NATIVE_STDLIB_MODULE_NAME;
    }

    public static final boolean isNativeStdlib(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "<this>");
        return Intrinsics.areEqual(moduleDescriptor.getName(), NATIVE_STDLIB_MODULE_NAME);
    }

    public static final NativeForwardDeclarationKind getForwardDeclarationKindOrNull(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        Map<FqName, NativeForwardDeclarationKind> packageFqNameToKind = NativeForwardDeclarationKind.INSTANCE.getPackageFqNameToKind();
        DeclarationDescriptor moduleDescriptor = classDescriptor.getModuleDescriptor();
        PackageFragmentDescriptor packageFragmentDescriptor = moduleDescriptor instanceof PackageFragmentDescriptor ? (PackageFragmentDescriptor) moduleDescriptor : null;
        return packageFqNameToKind.get(packageFragmentDescriptor != null ? packageFragmentDescriptor.getFqName() : null);
    }
}
