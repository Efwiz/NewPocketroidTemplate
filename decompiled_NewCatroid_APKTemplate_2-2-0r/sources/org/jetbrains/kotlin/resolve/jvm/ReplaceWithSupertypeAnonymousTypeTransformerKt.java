package org.jetbrains.kotlin.resolve.jvm;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutionKt;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ReplaceWithSupertypeAnonymousTypeTransformer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001¨\u0006\u0003"}, d2 = {"replaceAnonymousTypeWithSuperType", "Lorg/jetbrains/kotlin/types/KotlinType;", ModuleXmlParser.TYPE, "frontend.java"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ReplaceWithSupertypeAnonymousTypeTransformerKt {
    public static final KotlinType replaceAnonymousTypeWithSuperType(KotlinType type) {
        TypeProjection typeProjection;
        Intrinsics.checkNotNullParameter(type, "type");
        ClassifierDescriptor this$0 = type.getConstructor().getThis$0();
        ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
        if (classDescriptor == null) {
            return type;
        }
        if (KotlinBuiltIns.isArray(type) && (typeProjection = (TypeProjection) CollectionsKt.singleOrNull((List) type.getArguments())) != null && !typeProjection.isStarProjection()) {
            KotlinBuiltIns builtIns = TypeUtilsKt.getBuiltIns(type);
            Variance projectionKind = typeProjection.getProjectionKind();
            KotlinType type2 = typeProjection.getType();
            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
            SimpleType arrayType = builtIns.getArrayType(projectionKind, replaceAnonymousTypeWithSuperType(type2));
            Intrinsics.checkNotNullExpressionValue(arrayType, "getArrayType(...)");
            return arrayType;
        }
        ClassDescriptor classDescriptor2 = classDescriptor;
        if (DescriptorUtils.isAnonymousObject(classDescriptor2) || DescriptorUtils.isLocal(classDescriptor2)) {
            if (type.getConstructor().mo7438getSupertypes().size() == 1) {
                KotlinType next = type.getConstructor().mo7438getSupertypes().iterator().next();
                Intrinsics.checkNotNullExpressionValue(next, "next(...)");
                type = replaceAnonymousTypeWithSuperType(next);
            } else {
                SimpleType anyType = TypeUtilsKt.getBuiltIns(type).getAnyType();
                Intrinsics.checkNotNull(anyType);
                type = anyType;
            }
        }
        KotlinType kotlinType = type;
        if (kotlinType.getArguments().isEmpty()) {
            return kotlinType;
        }
        List<TypeProjection> arguments = kotlinType.getArguments();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(arguments, 10));
        for (TypeProjectionImpl typeProjectionImpl : arguments) {
            if (!typeProjectionImpl.isStarProjection()) {
                Variance projectionKind2 = typeProjectionImpl.getProjectionKind();
                KotlinType type3 = typeProjectionImpl.getType();
                Intrinsics.checkNotNullExpressionValue(type3, "getType(...)");
                typeProjectionImpl = new TypeProjectionImpl(projectionKind2, replaceAnonymousTypeWithSuperType(type3));
            }
            arrayList.add(typeProjectionImpl);
        }
        return TypeSubstitutionKt.replace$default(kotlinType, arrayList, null, null, 6, null);
    }
}
