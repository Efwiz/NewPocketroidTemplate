package org.jetbrains.kotlin.resolve.calls.util;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.AnnotationsKt;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;

/* JADX INFO: compiled from: isFromStdlibJre7Or8.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\n\u001a\u00020\u000b*\u00020\f\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"get", "Lorg/jetbrains/kotlin/name/Name;", "getOrDefault", IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "Lorg/jetbrains/kotlin/name/FqName;", "kotlinCollections", "kotlinStreams", "kotlinText", ProductAction.ACTION_REMOVE, "use", "isLowPriorityFromStdlibJre7Or8", "", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IsFromStdlibJre7Or8Kt {
    private static final Name get;
    private static final Name getOrDefault;

    /* JADX INFO: renamed from: kotlin, reason: collision with root package name */
    private static final FqName f964kotlin;
    private static final FqName kotlinCollections;
    private static final FqName kotlinStreams;
    private static final FqName kotlinText;
    private static final Name remove;
    private static final Name use;

    static {
        FqName fqName = StandardNames.BUILT_INS_PACKAGE_FQ_NAME;
        f964kotlin = fqName;
        kotlinText = StandardNames.TEXT_PACKAGE_FQ_NAME;
        kotlinCollections = StandardNames.COLLECTIONS_PACKAGE_FQ_NAME;
        FqName fqNameChild = fqName.child(Name.identifier("streams"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        kotlinStreams = fqNameChild;
        Name nameIdentifier = Name.identifier("use");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        use = nameIdentifier;
        Name nameIdentifier2 = Name.identifier("get");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        get = nameIdentifier2;
        Name nameIdentifier3 = Name.identifier("getOrDefault");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        getOrDefault = nameIdentifier3;
        Name nameIdentifier4 = Name.identifier(ProductAction.ACTION_REMOVE);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
        remove = nameIdentifier4;
    }

    public static final boolean isLowPriorityFromStdlibJre7Or8(CallableDescriptor callableDescriptor) {
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation;
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        DeclarationDescriptor containingDeclaration = callableDescriptor.getContainingDeclaration();
        PackageFragmentDescriptor packageFragmentDescriptor = containingDeclaration instanceof PackageFragmentDescriptor ? (PackageFragmentDescriptor) containingDeclaration : null;
        if (packageFragmentDescriptor == null) {
            return false;
        }
        FqName fqName = packageFragmentDescriptor.getFqName();
        if (!fqName.startsWith(StandardNames.BUILT_INS_PACKAGE_NAME)) {
            return false;
        }
        if (!((Intrinsics.areEqual(fqName, f964kotlin) && Intrinsics.areEqual(callableDescriptor.getShortName(), use)) || ((Intrinsics.areEqual(fqName, kotlinText) && Intrinsics.areEqual(callableDescriptor.getShortName(), get)) || ((Intrinsics.areEqual(fqName, kotlinCollections) && (Intrinsics.areEqual(callableDescriptor.getShortName(), getOrDefault) || Intrinsics.areEqual(callableDescriptor.getShortName(), remove))) || Intrinsics.areEqual(fqName, kotlinStreams)))) || (annotationDescriptorMo6752findAnnotation = callableDescriptor.getAnnotations().mo6752findAnnotation(AnnotationsKt.getSINCE_KOTLIN_FQ_NAME())) == null) {
            return false;
        }
        ConstantValue constantValue = (ConstantValue) CollectionsKt.singleOrNull(annotationDescriptorMo6752findAnnotation.getAllValueArguments().values());
        Object value = constantValue != null ? constantValue.getValue() : null;
        String str = value instanceof String ? (String) value : null;
        if (str == null) {
            return false;
        }
        return Intrinsics.areEqual(str, LanguageVersion.KOTLIN_1_1.getVersionString());
    }
}
