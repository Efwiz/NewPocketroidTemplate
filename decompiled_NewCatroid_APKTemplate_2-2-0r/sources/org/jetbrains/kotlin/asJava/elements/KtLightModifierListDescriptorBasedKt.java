package org.jetbrains.kotlin.asJava.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.LightClassGenerationSupport;
import org.jetbrains.kotlin.asJava.builder.LightMemberOriginForDeclaration;
import org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.source.PsiSourceElementKt;

/* JADX INFO: compiled from: KtLightModifierListDescriptorBased.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a&\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006H\u0002\u001a\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\u0016\u0010\t\u001a\u00020\b2\u000e\u0010\n\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0006\u001a\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00012\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0002¨\u0006\u000f"}, d2 = {"getAnnotationDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "declaration", "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "annotatedLightElement", "Lorg/jetbrains/kotlin/asJava/elements/KtLightElement;", "hasAnnotationsInSource", "", "isFromSources", "lightElement", "lightAnnotationsForEntries", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAnnotationForSourceEntry;", "lightModifierList", "Lorg/jetbrains/kotlin/asJava/elements/KtLightModifierList;", "light-classes"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtLightModifierListDescriptorBasedKt {
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:31:0x0077  */
    public static final List<KtLightAnnotationForSourceEntry> lightAnnotationsForEntries(KtLightModifierList<?> ktLightModifierList) {
        Pair pair;
        String strAsString;
        KtLightElement lightParent = ktLightModifierList.getLightParent();
        if (!isFromSources(ktLightModifierList)) {
            return CollectionsKt.emptyList();
        }
        KtElement kotlinOrigin = lightParent.getKotlinOrigin();
        KtDeclaration ktDeclaration = kotlinOrigin instanceof KtDeclaration ? (KtDeclaration) kotlinOrigin : null;
        if (ktDeclaration != null && ktDeclaration.isValid()) {
            KtDeclaration ktDeclaration2 = ktDeclaration;
            if (hasAnnotationsInSource(ktDeclaration2)) {
                List<AnnotationDescriptor> annotationDescriptors = getAnnotationDescriptors(ktDeclaration2, lightParent);
                ArrayList<Pair> arrayList = new ArrayList();
                for (AnnotationDescriptor annotationDescriptor : annotationDescriptors) {
                    FqName fqName = annotationDescriptor.getFqName();
                    if (fqName == null || (strAsString = fqName.asString()) == null) {
                        pair = null;
                    } else {
                        Intrinsics.checkNotNull(strAsString);
                        PsiElement psi = PsiSourceElementKt.getPsi(annotationDescriptor.getSource());
                        KtAnnotationEntry ktAnnotationEntry = psi instanceof KtAnnotationEntry ? (KtAnnotationEntry) psi : null;
                        if (ktAnnotationEntry == null) {
                            pair = null;
                        } else {
                            pair = new Pair(strAsString, ktAnnotationEntry);
                        }
                    }
                    if (pair != null) {
                        arrayList.add(pair);
                    }
                }
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Pair pair2 : arrayList) {
                    String str = (String) pair2.getFirst();
                    Object obj = linkedHashMap.get(str);
                    if (obj == null) {
                        obj = (List) new ArrayList();
                        linkedHashMap.put(str, obj);
                    }
                    ((List) obj).add((KtAnnotationEntry) pair2.getSecond());
                }
                ArrayList arrayList2 = new ArrayList();
                for (Map.Entry entry : linkedHashMap.entrySet()) {
                    final String str2 = (String) entry.getKey();
                    List<KtAnnotationEntry> list = (List) entry.getValue();
                    ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    for (KtAnnotationEntry ktAnnotationEntry2 : list) {
                        Name shortName = ktAnnotationEntry2.getShortName();
                        arrayList3.add(new KtLightAnnotationForSourceEntry(shortName != null ? shortName.getIdentifier() : null, new Function0<String>() { // from class: org.jetbrains.kotlin.asJava.elements.KtLightModifierListDescriptorBasedKt$lightAnnotationsForEntries$4$1$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final String invoke() {
                                return str2;
                            }
                        }, ktAnnotationEntry2, ktLightModifierList));
                    }
                    CollectionsKt.addAll(arrayList2, arrayList3);
                }
                return arrayList2;
            }
        }
        return CollectionsKt.emptyList();
    }

    public static final boolean isFromSources(KtLightElement<?, ?> lightElement) {
        KtLightMember ktLightMember;
        Intrinsics.checkNotNullParameter(lightElement, "lightElement");
        return (lightElement instanceof KtLightClassForSourceDeclaration) || (lightElement.getLightParent() instanceof KtLightClassForSourceDeclaration) || (ktLightMember = (KtLightMember) PsiTreeUtil.getParentOfType(lightElement, KtLightMember.class, false)) == null || (ktLightMember.getLightMemberOrigin() instanceof LightMemberOriginForDeclaration);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final List<AnnotationDescriptor> getAnnotationDescriptors(KtAnnotated ktAnnotated, KtLightElement<?, ?> ktLightElement) {
        VariableDescriptor variableDescriptor;
        DeclarationDescriptor setter;
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation;
        VariableDescriptor variableDescriptor2;
        LightClassGenerationSupport.Companion companion = LightClassGenerationSupport.INSTANCE;
        Project project = ktAnnotated.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        BindingContext bindingContextAnalyze = companion.getInstance(project).analyze(ktAnnotated);
        if ((ktAnnotated instanceof KtParameter) && KtPsiUtilKt.isPropertyParameter((KtParameter) ktAnnotated)) {
            if ((ktLightElement instanceof LightParameter) && ((LightParameter) ktLightElement).getMethod().isConstructor()) {
                variableDescriptor2 = (VariableDescriptor) bindingContextAnalyze.get(BindingContext.VALUE_PARAMETER, ktAnnotated);
            } else {
                variableDescriptor2 = (VariableDescriptor) bindingContextAnalyze.get(BindingContext.PRIMARY_CONSTRUCTOR_PARAMETER, ktAnnotated);
            }
            variableDescriptor = variableDescriptor2;
        } else {
            variableDescriptor = (DeclarationDescriptor) bindingContextAnalyze.get(BindingContext.DECLARATION_TO_DESCRIPTOR, ktAnnotated);
        }
        if ((variableDescriptor instanceof ClassDescriptor) && (ktLightElement instanceof KtLightMethod) && ((KtLightMethod) ktLightElement).isConstructor()) {
            setter = ((ClassDescriptor) variableDescriptor).mo6347getUnsubstitutedPrimaryConstructor();
        } else if (!(variableDescriptor instanceof PropertyDescriptor)) {
            setter = variableDescriptor;
        } else if (ktLightElement instanceof KtLightField) {
            setter = ((PropertyDescriptor) variableDescriptor).getBackingField();
        } else if (ktLightElement instanceof KtLightMethod) {
            KtLightMethod ktLightMethod = (KtLightMethod) ktLightElement;
            if (KtLightMethodImplKt.isGetter(ktLightMethod)) {
                setter = ((PropertyDescriptor) variableDescriptor).getGetter();
            } else {
                setter = KtLightMethodImplKt.isSetter(ktLightMethod) ? ((PropertyDescriptor) variableDescriptor).getSetter() : variableDescriptor;
            }
        } else {
            setter = variableDescriptor;
        }
        if (setter == null) {
            return CollectionsKt.emptyList();
        }
        List<AnnotationDescriptor> mutableList = CollectionsKt.toMutableList(setter.getAnnotations());
        if ((variableDescriptor instanceof PropertyDescriptor) && (annotationDescriptorMo6752findAnnotation = ((PropertyDescriptor) variableDescriptor).getAnnotations().mo6752findAnnotation(JvmNames.INSTANCE.getJVM_DEFAULT_FQ_NAME())) != null) {
            mutableList.add(annotationDescriptorMo6752findAnnotation);
        }
        return mutableList;
    }

    private static final boolean hasAnnotationsInSource(KtAnnotated ktAnnotated) {
        List<KtAnnotationEntry> annotationEntries = ktAnnotated.getAnnotationEntries();
        Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
        if (!annotationEntries.isEmpty()) {
            return true;
        }
        if (!(ktAnnotated instanceof KtProperty)) {
            return false;
        }
        List<KtPropertyAccessor> accessors = ((KtProperty) ktAnnotated).getAccessors();
        Intrinsics.checkNotNullExpressionValue(accessors, "getAccessors(...)");
        List<KtPropertyAccessor> list = accessors;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            for (KtPropertyAccessor ktPropertyAccessor : list) {
                Intrinsics.checkNotNull(ktPropertyAccessor);
                if (hasAnnotationsInSource(ktPropertyAccessor)) {
                    return true;
                }
            }
        }
        return false;
    }
}
