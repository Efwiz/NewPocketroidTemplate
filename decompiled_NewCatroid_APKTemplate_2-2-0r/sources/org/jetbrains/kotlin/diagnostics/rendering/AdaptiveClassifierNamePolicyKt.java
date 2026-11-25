package org.jetbrains.kotlin.diagnostics.rendering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.renderer.ClassifierNamePolicy;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.SpecialTypesKt;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: adaptiveClassifierNamePolicy.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000;\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001c\n\u0000\n\u0002\u0010#\n\u0000*\u0001\u0001\u001a\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u000e\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\fH\u0002\u001a&\u0010\u000e\u001a\u00020\u000f2\u000e\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\n0\u0013H\u0002\"\u0010\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0002\"\u0015\u0010\u0003\u001a\u00020\u0004*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0014"}, d2 = {"ADAPTIVE_CLASSIFIER_POLICY_KEY", "org/jetbrains/kotlin/diagnostics/rendering/AdaptiveClassifierNamePolicyKt$ADAPTIVE_CLASSIFIER_POLICY_KEY$1", "Lorg/jetbrains/kotlin/diagnostics/rendering/AdaptiveClassifierNamePolicyKt$ADAPTIVE_CLASSIFIER_POLICY_KEY$1;", "adaptiveClassifierPolicy", "Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "getAdaptiveClassifierPolicy", "(Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;)Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;", "collectClassifiersFqNames", "", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "objectsToRender", "", "", "collectMentionedClassifiersFqNames", "", "contextObjects", "", "result", "", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AdaptiveClassifierNamePolicyKt {
    private static final AdaptiveClassifierNamePolicyKt$ADAPTIVE_CLASSIFIER_POLICY_KEY$1 ADAPTIVE_CLASSIFIER_POLICY_KEY = new RenderingContext.Key<ClassifierNamePolicy>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.AdaptiveClassifierNamePolicyKt$ADAPTIVE_CLASSIFIER_POLICY_KEY$1
        @Override // org.jetbrains.kotlin.diagnostics.rendering.RenderingContext.Key
        public /* bridge */ /* synthetic */ ClassifierNamePolicy compute(Collection collection) {
            return compute2((Collection<? extends Object>) collection);
        }

        @Override // org.jetbrains.kotlin.diagnostics.rendering.RenderingContext.Key
        /* JADX INFO: renamed from: compute, reason: avoid collision after fix types in other method */
        public ClassifierNamePolicy compute2(Collection<? extends Object> objectsToRender) {
            Intrinsics.checkNotNullParameter(objectsToRender, "objectsToRender");
            Set setCollectClassifiersFqNames = AdaptiveClassifierNamePolicyKt.collectClassifiersFqNames(objectsToRender);
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Object obj : setCollectClassifiersFqNames) {
                Name nameShortNameOrSpecial = ((FqNameUnsafe) obj).shortNameOrSpecial();
                Object obj2 = linkedHashMap.get(nameShortNameOrSpecial);
                if (obj2 == null) {
                    obj2 = (List) new ArrayList();
                    linkedHashMap.put(nameShortNameOrSpecial, obj2);
                }
                ((List) obj2).add(obj);
            }
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            for (Map.Entry entry : linkedHashMap.entrySet()) {
                if (((List) entry.getValue()).size() > 1) {
                    linkedHashMap2.put(entry.getKey(), entry.getValue());
                }
            }
            ArrayList arrayList = new ArrayList(linkedHashMap2.size());
            Iterator it = linkedHashMap2.entrySet().iterator();
            while (it.hasNext()) {
                arrayList.add((Name) ((Map.Entry) it.next()).getKey());
            }
            return new AdaptiveClassifierNamePolicy(arrayList);
        }
    };

    public static final ClassifierNamePolicy getAdaptiveClassifierPolicy(RenderingContext renderingContext) {
        Intrinsics.checkNotNullParameter(renderingContext, "<this>");
        return (ClassifierNamePolicy) renderingContext.get(ADAPTIVE_CLASSIFIER_POLICY_KEY);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set<FqNameUnsafe> collectClassifiersFqNames(Collection<? extends Object> collection) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        collectMentionedClassifiersFqNames(collection, linkedHashSet);
        return linkedHashSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void collectMentionedClassifiersFqNames$addMentionedTypeConstructor(KotlinType kotlinType, Set<FqNameUnsafe> set) {
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (this$0 != null) {
            set.add(DescriptorUtilsKt.getFqNameUnsafe(this$0));
        }
    }

    private static final void collectMentionedClassifiersFqNames(Iterable<? extends Object> iterable, final Set<FqNameUnsafe> set) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : iterable) {
            if (obj instanceof KotlinType) {
                arrayList.add(obj);
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            TypeUtilsKt.contains((KotlinType) it.next(), new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.AdaptiveClassifierNamePolicyKt$collectMentionedClassifiersFqNames$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(UnwrappedType innerType) {
                    Intrinsics.checkNotNullParameter(innerType, "innerType");
                    UnwrappedType unwrappedType = innerType;
                    AdaptiveClassifierNamePolicyKt.collectMentionedClassifiersFqNames$addMentionedTypeConstructor(unwrappedType, set);
                    SimpleType abbreviation = SpecialTypesKt.getAbbreviation(unwrappedType);
                    if (abbreviation != null) {
                        AdaptiveClassifierNamePolicyKt.collectMentionedClassifiersFqNames$addMentionedTypeConstructor(abbreviation, set);
                    }
                    return false;
                }
            });
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : iterable) {
            if (obj2 instanceof Iterable) {
                arrayList2.add(obj2);
            }
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            collectMentionedClassifiersFqNames((Iterable) it2.next(), set);
        }
        ArrayList arrayList3 = new ArrayList();
        for (Object obj3 : iterable) {
            if (obj3 instanceof ClassifierDescriptor) {
                arrayList3.add(obj3);
            }
        }
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            set.add(DescriptorUtilsKt.getFqNameUnsafe((ClassifierDescriptor) it3.next()));
        }
        ArrayList arrayList4 = new ArrayList();
        for (Object obj4 : iterable) {
            if (obj4 instanceof TypeParameterDescriptor) {
                arrayList4.add(obj4);
            }
        }
        Iterator it4 = arrayList4.iterator();
        while (it4.hasNext()) {
            List<KotlinType> upperBounds = ((TypeParameterDescriptor) it4.next()).getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
            collectMentionedClassifiersFqNames(upperBounds, set);
        }
        ArrayList<CallableDescriptor> arrayList5 = new ArrayList();
        for (Object obj5 : iterable) {
            if (obj5 instanceof CallableDescriptor) {
                arrayList5.add(obj5);
            }
        }
        for (CallableDescriptor callableDescriptor : arrayList5) {
            List<TypeParameterDescriptor> typeParameters = callableDescriptor.getTypeParameters();
            KotlinType returnType = callableDescriptor.getReturnType();
            List<ValueParameterDescriptor> valueParameters = callableDescriptor.getValueParameters();
            ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = callableDescriptor.mo6753getDispatchReceiverParameter();
            KotlinType type = null;
            KotlinType type2 = receiverParameterDescriptorMo6753getDispatchReceiverParameter != null ? receiverParameterDescriptorMo6753getDispatchReceiverParameter.getType() : null;
            ReceiverParameterDescriptor extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter();
            if (extensionReceiverParameter != null) {
                type = extensionReceiverParameter.getType();
            }
            collectMentionedClassifiersFqNames(CollectionsKt.listOf(typeParameters, returnType, valueParameters, type2, type), set);
        }
    }
}
