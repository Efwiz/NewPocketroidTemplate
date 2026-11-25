package org.jetbrains.kotlin.resolve;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.container.DefaultImplementation;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: AdditionalClassPartsProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@DefaultImplementation(impl = Default.class)
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001:\u0001\u0013J<\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\u000eH&J$\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H&¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/AdditionalClassPartsProvider;", "", "generateAdditionalMethods", "", "thisDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "result", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "fromSupertypes", "", "getAdditionalSupertypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "existingSupertypes", "Default", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface AdditionalClassPartsProvider {
    void generateAdditionalMethods(ClassDescriptor thisDescriptor, Collection<SimpleFunctionDescriptor> result, Name name2, LookupLocation location, Collection<? extends SimpleFunctionDescriptor> fromSupertypes);

    List<KotlinType> getAdditionalSupertypes(ClassDescriptor thisDescriptor, List<? extends KotlinType> existingSupertypes);

    /* JADX INFO: compiled from: AdditionalClassPartsProvider.kt */
    @Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J<\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000fH\u0016J$\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0016¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/AdditionalClassPartsProvider$Default;", "Lorg/jetbrains/kotlin/resolve/AdditionalClassPartsProvider;", "()V", "generateAdditionalMethods", "", "thisDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "result", "", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "fromSupertypes", "", "getAdditionalSupertypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "existingSupertypes", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Default implements AdditionalClassPartsProvider {
        public static final Default INSTANCE = new Default();

        @Override // org.jetbrains.kotlin.resolve.AdditionalClassPartsProvider
        public void generateAdditionalMethods(ClassDescriptor thisDescriptor, Collection<SimpleFunctionDescriptor> result, Name name2, LookupLocation location, Collection<? extends SimpleFunctionDescriptor> fromSupertypes) {
            Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
            Intrinsics.checkNotNullParameter(result, "result");
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            Intrinsics.checkNotNullParameter(fromSupertypes, "fromSupertypes");
        }

        private Default() {
        }

        @Override // org.jetbrains.kotlin.resolve.AdditionalClassPartsProvider
        public List<KotlinType> getAdditionalSupertypes(ClassDescriptor thisDescriptor, List<? extends KotlinType> existingSupertypes) {
            Intrinsics.checkNotNullParameter(thisDescriptor, "thisDescriptor");
            Intrinsics.checkNotNullParameter(existingSupertypes, "existingSupertypes");
            return CollectionsKt.emptyList();
        }
    }
}
