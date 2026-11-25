package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.PackageParts;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: PackagePartRegistry.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0012R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/codegen/PackagePartRegistry;", "", "()V", "optionalAnnotations", "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getOptionalAnnotations", "()Ljava/util/List;", "parts", "", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/metadata/jvm/deserialization/PackageParts;", "getParts", "()Ljava/util/Map;", "addPart", "", "packageFqName", "partInternalName", "", "facadeInternalName", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PackagePartRegistry {
    private final Map<FqName, PackageParts> parts = new LinkedHashMap();
    private final List<ClassDescriptor> optionalAnnotations = new ArrayList();

    public final Map<FqName, PackageParts> getParts() {
        return this.parts;
    }

    public final List<ClassDescriptor> getOptionalAnnotations() {
        return this.optionalAnnotations;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final PackageParts addPart$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (PackageParts) tmp0.invoke(obj);
    }

    public final void addPart(FqName packageFqName, String partInternalName, String facadeInternalName) {
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(partInternalName, "partInternalName");
        Map<FqName, PackageParts> map = this.parts;
        final AnonymousClass1 anonymousClass1 = new Function1<FqName, PackageParts>() { // from class: org.jetbrains.kotlin.codegen.PackagePartRegistry.addPart.1
            @Override // kotlin.jvm.functions.Function1
            public final PackageParts invoke(FqName it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String strAsString = it.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return new PackageParts(strAsString);
            }
        };
        map.computeIfAbsent(packageFqName, new Function() { // from class: org.jetbrains.kotlin.codegen.PackagePartRegistry$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return PackagePartRegistry.addPart$lambda$0(anonymousClass1, obj);
            }
        }).addPart(partInternalName, facadeInternalName);
    }
}
