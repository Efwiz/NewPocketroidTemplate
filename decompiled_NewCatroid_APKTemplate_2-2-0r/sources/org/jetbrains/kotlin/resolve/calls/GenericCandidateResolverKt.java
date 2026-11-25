package org.jetbrains.kotlin.resolve.calls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.ReflectionTypes;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionContext;
import org.jetbrains.kotlin.resolve.calls.context.ResolutionResultsCache;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeConstructorSubstitution;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.expressions.ControlStructureTypingUtils;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingUtils;

/* JADX INFO: compiled from: GenericCandidateResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\u001e\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\t2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000b\u001a\u001c\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u0012\u001a\f\u0010\u0013\u001a\u00020\u0014*\u00020\u0012H\u0002\"\u001f\u0010\u0000\u001a\u0010\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0015"}, d2 = {"SPECIAL_FUNCTION_NAMES", "", "Lorg/jetbrains/kotlin/name/Name;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getSPECIAL_FUNCTION_NAMES", "()Ljava/util/Set;", "getResolutionResultsCachedData", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionResultsCache$CachedData;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "context", "Lorg/jetbrains/kotlin/resolve/calls/context/ResolutionContext;", "makeConstantSubstitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "typeParameterDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "isApplicableExpectedTypeForCallableReference", "", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class GenericCandidateResolverKt {
    private static final Set<Name> SPECIAL_FUNCTION_NAMES;

    static {
        ControlStructureTypingUtils.ResolveConstruct[] resolveConstructArrValues = ControlStructureTypingUtils.ResolveConstruct.values();
        ArrayList arrayList = new ArrayList(resolveConstructArrValues.length);
        for (ControlStructureTypingUtils.ResolveConstruct resolveConstruct : resolveConstructArrValues) {
            arrayList.add(resolveConstruct.getSpecialFunctionName());
        }
        SPECIAL_FUNCTION_NAMES = CollectionsKt.toSet(arrayList);
    }

    public static final Set<Name> getSPECIAL_FUNCTION_NAMES() {
        return SPECIAL_FUNCTION_NAMES;
    }

    public static final ResolutionResultsCache.CachedData getResolutionResultsCachedData(KtExpression ktExpression, ResolutionContext<?> context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (ExpressionTypingUtils.dependsOnExpectedType(ktExpression) && ktExpression != null) {
            BindingContext context2 = context.trace.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
            Call call = CallUtilKt.getCall(ktExpression, context2);
            if (call != null) {
                return context.resolutionResultsCache.get(call);
            }
        }
        return null;
    }

    public static final TypeSubstitutor makeConstantSubstitutor(Collection<? extends TypeParameterDescriptor> typeParameterDescriptors, KotlinType type) {
        Intrinsics.checkNotNullParameter(typeParameterDescriptors, "typeParameterDescriptors");
        Intrinsics.checkNotNullParameter(type, "type");
        Collection<? extends TypeParameterDescriptor> collection = typeParameterDescriptors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeParameterDescriptor) it.next()).getTypeConstructor());
        }
        final Set set = CollectionsKt.toSet(arrayList);
        final TypeProjectionImpl typeProjectionImpl = new TypeProjectionImpl(type);
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(new TypeConstructorSubstitution() { // from class: org.jetbrains.kotlin.resolve.calls.GenericCandidateResolverKt.makeConstantSubstitutor.1
            @Override // org.jetbrains.kotlin.types.TypeSubstitution
            public boolean isEmpty() {
                return false;
            }

            @Override // org.jetbrains.kotlin.types.TypeConstructorSubstitution
            public TypeProjectionImpl get(TypeConstructor key) {
                Intrinsics.checkNotNullParameter(key, "key");
                if (set.contains(key)) {
                    return typeProjectionImpl;
                }
                return null;
            }
        });
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        return typeSubstitutorCreate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isApplicableExpectedTypeForCallableReference(KotlinType kotlinType) {
        return FunctionTypesKt.isFunctionType(kotlinType) || ReflectionTypes.INSTANCE.isBaseTypeForNumberedReferenceTypes(kotlinType) || ReflectionTypes.INSTANCE.isNumberedKFunctionOrKSuspendFunction(kotlinType) || ReflectionTypes.INSTANCE.isNumberedKPropertyOrKMutablePropertyType(kotlinType);
    }
}
