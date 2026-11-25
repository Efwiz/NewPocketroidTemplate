package org.jetbrains.kotlin.resolve.jvm;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.AdditionalClassPartsProvider;
import org.jetbrains.kotlin.resolve.FunctionsFromAny;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.annotations.JvmAnnotationUtilKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;

/* JADX INFO: compiled from: JvmAdditionalClassPartsProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J<\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000fH\u0016J$\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0016¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/jvm/JvmAdditionalClassPartsProvider;", "Lorg/jetbrains/kotlin/resolve/AdditionalClassPartsProvider;", "()V", "generateAdditionalMethods", "", "thisDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "result", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "fromSupertypes", "", "getAdditionalSupertypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "existingSupertypes", "frontend.java"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmAdditionalClassPartsProvider implements AdditionalClassPartsProvider {
    @Override // org.jetbrains.kotlin.resolve.AdditionalClassPartsProvider
    public void generateAdditionalMethods(ClassDescriptor thisDescriptor, Collection<SimpleFunctionDescriptor> result, Name name2, LookupLocation location, Collection<? extends SimpleFunctionDescriptor> fromSupertypes) {
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        Intrinsics.checkNotNullParameter(fromSupertypes, "fromSupertypes");
        if (JvmAnnotationUtilKt.isJvmRecord(thisDescriptor)) {
            FunctionsFromAny.INSTANCE.addFunctionFromAnyIfNeeded(thisDescriptor, result, name2, fromSupertypes);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.AdditionalClassPartsProvider
    public List<KotlinType> getAdditionalSupertypes(ClassDescriptor thisDescriptor, List<? extends KotlinType> existingSupertypes) {
        ClassDescriptor classDescriptorResolveTopLevelClass;
        SimpleType defaultType;
        Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
        Intrinsics.checkNotNullParameter(existingSupertypes, "existingSupertypes");
        if (JvmAnnotationUtilKt.isJvmRecord(thisDescriptor)) {
            List<? extends KotlinType> list = existingSupertypes;
            if ((list instanceof Collection) && list.isEmpty()) {
                classDescriptorResolveTopLevelClass = DescriptorUtilsKt.resolveTopLevelClass(DescriptorUtilsKt.getModule(thisDescriptor), JdkClassesKt.getJAVA_LANG_RECORD_FQ_NAME(), NoLookupLocation.FOR_DEFAULT_IMPORTS);
                if (classDescriptorResolveTopLevelClass != null) {
                    return CollectionsKt.listOf(defaultType);
                }
            } else {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (JvmAdditionalClassPartsProviderKt.isJavaLangRecordType((KotlinType) it.next())) {
                    }
                }
                classDescriptorResolveTopLevelClass = DescriptorUtilsKt.resolveTopLevelClass(DescriptorUtilsKt.getModule(thisDescriptor), JdkClassesKt.getJAVA_LANG_RECORD_FQ_NAME(), NoLookupLocation.FOR_DEFAULT_IMPORTS);
                if (classDescriptorResolveTopLevelClass != null && (defaultType = classDescriptorResolveTopLevelClass.getDefaultType()) != null) {
                    return CollectionsKt.listOf(defaultType);
                }
            }
        }
        return CollectionsKt.emptyList();
    }
}
