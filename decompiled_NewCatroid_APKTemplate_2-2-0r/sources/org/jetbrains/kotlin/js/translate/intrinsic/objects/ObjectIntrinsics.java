package org.jetbrains.kotlin.js.translate.intrinsic.objects;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.CompanionObjectMapping;
import org.jetbrains.kotlin.builtins.CompanionObjectMappingUtilsKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;

/* JADX INFO: compiled from: objectsIntrinsics.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u0005H\u0002J\u0010\u0010\t\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u0005R\u001c\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/objects/ObjectIntrinsics;", "", "()V", ResponseCacheMiddleware.CACHE, "", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/objects/ObjectIntrinsic;", "createIntrinsic", "classDescriptor", "getIntrinsic", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ObjectIntrinsics {
    private final Map<ClassDescriptor, ObjectIntrinsic> cache = new LinkedHashMap();

    public final ObjectIntrinsic getIntrinsic(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "classDescriptor");
        if (this.cache.containsKey(classDescriptor)) {
            return this.cache.get(classDescriptor);
        }
        ObjectIntrinsic objectIntrinsicCreateIntrinsic = createIntrinsic(classDescriptor);
        this.cache.put(classDescriptor, objectIntrinsicCreateIntrinsic);
        return objectIntrinsicCreateIntrinsic;
    }

    private final ObjectIntrinsic createIntrinsic(ClassDescriptor classDescriptor) {
        if (Intrinsics.areEqual(DescriptorUtilsKt.getFqNameUnsafe(classDescriptor), StandardNames.FqNames._enum) || !CompanionObjectMappingUtilsKt.isMappedIntrinsicCompanionObject(CompanionObjectMapping.INSTANCE, classDescriptor)) {
            return null;
        }
        DeclarationDescriptor containingDeclaration = classDescriptor.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
        Name nameIdentifier = Name.identifier(containingDeclaration.getShortName().asString() + "CompanionObject");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        FqName fqNameChild = new FqName("kotlin.js.internal").child(nameIdentifier);
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        return new DefaultClassObjectIntrinsic(fqNameChild);
    }
}
