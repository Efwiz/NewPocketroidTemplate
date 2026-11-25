package org.jetbrains.kotlin.analysis.decompiler.psi.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.analysis.decompiler.psi.file.KtDecompiledFile;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtClassOrObjectKt;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDeclarationModifierList;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtParameterList;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtTypeConstraint;
import org.jetbrains.kotlin.psi.KtTypeElement;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForObject;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.AbbreviatedType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: ByDescriptorIndexer.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u0018\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016J\u0018\u0010\u0017\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u001c\u0010\u0018\u001a\u00020\u00072\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0002J\u0018\u0010\u001b\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u0016H\u0002J\u0018\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u0016H\u0002R\u0013\u0010\u0003\u001a\u00070\u0004¢\u0006\u0002\b\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/psi/text/ByDescriptorIndexer;", "", "()V", "LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "areTypesTheSame", "", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "ktTypeReference", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "getDeclarationForDescriptor", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "file", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtDecompiledFile;", "isSameCallable", "declaration", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "original", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "parametersMatch", "receiverTypesMatch", "receiverParameter", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "returnTypesMatch", "typeParametersMatch", "decompiler-to-psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ByDescriptorIndexer {
    public static final ByDescriptorIndexer INSTANCE;
    private static final Logger LOG;

    private ByDescriptorIndexer() {
    }

    public final KtDeclaration getDeclarationForDescriptor(DeclarationDescriptor descriptor, KtDecompiledFile file) {
        KtClassOrObject ktClassOrObject;
        ArrayList allConstructors;
        KtPrimaryConstructor primaryConstructor;
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(file, "file");
        DeclarationDescriptor original = descriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        if (original instanceof TypeAliasConstructorDescriptor) {
            return getDeclarationForDescriptor(((TypeAliasConstructorDescriptor) original).getTypeAliasDescriptor(), file);
        }
        Object obj = null;
        if (original instanceof ValueParameterDescriptor) {
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) original;
            CallableDescriptor containingModule = valueParameterDescriptor.getContainingModule();
            KtDeclaration declarationForDescriptor = getDeclarationForDescriptor(containingModule, file);
            KtCallableDeclaration ktCallableDeclaration = declarationForDescriptor instanceof KtCallableDeclaration ? (KtCallableDeclaration) declarationForDescriptor : null;
            if (ktCallableDeclaration == null) {
                return null;
            }
            if (valueParameterDescriptor.getIndex() >= ktCallableDeclaration.getValueParameters().size()) {
                Logger logger = LOG;
                StringBuilder sbAppend = new StringBuilder("Parameter count mismatch for ").append(DescriptorRenderer.DEBUG_TEXT.render(containingModule)).append('[').append(valueParameterDescriptor.getIndex()).append("] vs ");
                KtParameterList valueParameterList = ktCallableDeclaration.getValueParameterList();
                logger.error(sbAppend.append(valueParameterList != null ? valueParameterList.getText() : null).toString());
                return null;
            }
            return ktCallableDeclaration.getValueParameters().get(valueParameterDescriptor.getIndex());
        }
        boolean z = original instanceof ConstructorDescriptor;
        if (z) {
            ConstructorDescriptor constructorDescriptor = (ConstructorDescriptor) original;
            if (constructorDescriptor.isPrimary()) {
                ClassifierDescriptorWithTypeParameters containingModule2 = constructorDescriptor.getContainingModule();
                Intrinsics.checkNotNullExpressionValue(containingModule2, "getContainingDeclaration(...)");
                KtDeclaration declarationForDescriptor2 = getDeclarationForDescriptor(containingModule2, file);
                KtClassOrObject ktClassOrObject2 = declarationForDescriptor2 instanceof KtClassOrObject ? (KtClassOrObject) declarationForDescriptor2 : null;
                return (ktClassOrObject2 == null || (primaryConstructor = ktClassOrObject2.getPrimaryConstructor()) == null) ? ktClassOrObject2 : primaryConstructor;
            }
        }
        CallableMemberDescriptor callableMemberDescriptor = original instanceof CallableMemberDescriptor ? (CallableMemberDescriptor) original : null;
        if (callableMemberDescriptor != null && BuildDecompiledTextKt.mustNotBeWrittenToDecompiledText(callableMemberDescriptor)) {
            CallableMemberDescriptor callableMemberDescriptor2 = (CallableMemberDescriptor) original;
            if (callableMemberDescriptor2.getContainingModule() instanceof ClassDescriptor) {
                DeclarationDescriptor containingDeclaration = callableMemberDescriptor2.getContainingModule();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                return getDeclarationForDescriptor(containingDeclaration, file);
            }
        }
        if (original instanceof FakeCallableDescriptorForObject) {
            return getDeclarationForDescriptor(((FakeCallableDescriptorForObject) original).getClassDescriptor(), file);
        }
        if (original instanceof MemberDescriptor) {
            if (DescriptorUtils.isTopLevelDeclaration(original)) {
                ktClassOrObject = file;
            } else {
                MemberDescriptor memberDescriptor = (MemberDescriptor) original;
                if (memberDescriptor.getContainingModule() instanceof ClassDescriptor) {
                    DeclarationDescriptor containingDeclaration2 = memberDescriptor.getContainingModule();
                    Intrinsics.checkNotNull(containingDeclaration2, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                    KtDeclaration declarationForDescriptor3 = getDeclarationForDescriptor((ClassDescriptor) containingDeclaration2, file);
                    ktClassOrObject = declarationForDescriptor3 instanceof KtClassOrObject ? (KtClassOrObject) declarationForDescriptor3 : null;
                } else {
                    throw new IllegalStateException(("Unexpected " + original + " with container: " + memberDescriptor.getContainingModule()).toString());
                }
            }
            if (ktClassOrObject != null) {
                String strAsString = ((MemberDescriptor) original).getShortName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                if (z && (ktClassOrObject instanceof KtClass)) {
                    allConstructors = KtClassOrObjectKt.getAllConstructors((KtClassOrObject) ktClassOrObject);
                } else {
                    List<KtDeclaration> declarations = ktClassOrObject.getDeclarations();
                    Intrinsics.checkNotNullExpressionValue(declarations, "getDeclarations(...)");
                    ArrayList arrayList = new ArrayList();
                    for (Object obj2 : declarations) {
                        if (Intrinsics.areEqual(((KtDeclaration) obj2).get$name(), strAsString)) {
                            arrayList.add(obj2);
                        }
                    }
                    allConstructors = arrayList;
                }
                for (Object obj3 : allConstructors) {
                    KtDeclaration ktDeclaration = (KtDeclaration) obj3;
                    if (original instanceof CallableDescriptor) {
                        if ((ktDeclaration instanceof KtCallableDeclaration) && INSTANCE.isSameCallable((KtCallableDeclaration) ktDeclaration, (CallableDescriptor) original)) {
                            obj = obj3;
                            break;
                        }
                    } else if (!(ktDeclaration instanceof KtCallableDeclaration)) {
                        obj = obj3;
                        break;
                    }
                }
                return (KtDeclaration) obj;
            }
        }
        throw new IllegalStateException(("Should not be reachable: " + descriptor).toString());
    }

    public final boolean isSameCallable(KtCallableDeclaration declaration, CallableDescriptor original) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(original, "original");
        return receiverTypesMatch(declaration.mo7393getReceiverTypeReference(), original.getExtensionReceiverParameter()) && returnTypesMatch(declaration, original) && typeParametersMatch(declaration, original) && parametersMatch(declaration, original);
    }

    private final boolean returnTypesMatch(KtCallableDeclaration declaration, CallableDescriptor descriptor) {
        KtTypeReference ktTypeReferenceMo7396getTypeReference;
        if ((declaration instanceof KtConstructor) || (ktTypeReferenceMo7396getTypeReference = declaration.mo7396getTypeReference()) == null) {
            return true;
        }
        KotlinType returnType = descriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        return areTypesTheSame(returnType, ktTypeReferenceMo7396getTypeReference);
    }

    private final boolean typeParametersMatch(KtCallableDeclaration declaration, CallableDescriptor descriptor) {
        if (declaration.getTypeParameters().size() != declaration.getTypeParameters().size()) {
            return false;
        }
        List<KtTypeConstraint> typeConstraints = declaration.getTypeConstraints();
        Intrinsics.checkNotNullExpressionValue(typeConstraints, "getTypeConstraints(...)");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : typeConstraints) {
            KtSimpleNameExpression subjectTypeParameterName = ((KtTypeConstraint) obj).getSubjectTypeParameterName();
            String referencedName = subjectTypeParameterName != null ? subjectTypeParameterName.getReferencedName() : null;
            Object obj2 = linkedHashMap.get(referencedName);
            if (obj2 == null) {
                obj2 = (List) new ArrayList();
                linkedHashMap.put(referencedName, obj2);
            }
            ((List) obj2).add(obj);
        }
        List<TypeParameterDescriptor> typeParameters = descriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterDescriptor> list = typeParameters;
        List<KtTypeParameter> typeParameters2 = declaration.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters2, "getTypeParameters(...)");
        List<KtTypeParameter> list2 = typeParameters2;
        Iterator<T> it = list.iterator();
        Iterator<T> it2 = list2.iterator();
        ArrayList arrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(list, 10), CollectionsKt.collectionSizeOrDefault(list2, 10)));
        while (it.hasNext() && it2.hasNext()) {
            Object next = it.next();
            KtTypeParameter ktTypeParameter = (KtTypeParameter) it2.next();
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) next;
            if (!Intrinsics.areEqual(typeParameterDescriptor.getShortName().toString(), ktTypeParameter.get$name())) {
                return false;
            }
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = arrayList2;
            org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList3, ktTypeParameter.getExtendsBound());
            List list3 = (List) linkedHashMap.get(ktTypeParameter.get$name());
            if (list3 != null) {
                Iterator it3 = list3.iterator();
                while (it3.hasNext()) {
                    org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(arrayList3, ((KtTypeConstraint) it3.next()).getBoundTypeReference());
                }
            }
            List<KotlinType> upperBounds = typeParameterDescriptor.getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(upperBounds, "getUpperBounds(...)");
            ArrayList arrayList4 = new ArrayList();
            for (Object obj3 : upperBounds) {
                KotlinType kotlinType = (KotlinType) obj3;
                Intrinsics.checkNotNull(kotlinType);
                if (!TypeUtilsKt.isNullableAny(kotlinType)) {
                    arrayList4.add(obj3);
                }
            }
            ArrayList arrayList5 = arrayList4;
            if (arrayList2.size() != arrayList5.size()) {
                return false;
            }
            ArrayList arrayList6 = arrayList5;
            Iterator it4 = arrayList6.iterator();
            ArrayList arrayList7 = arrayList2;
            Iterator it5 = arrayList7.iterator();
            ArrayList arrayList8 = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(arrayList6, 10), CollectionsKt.collectionSizeOrDefault(arrayList7, 10)));
            while (it4.hasNext() && it5.hasNext()) {
                Object next2 = it4.next();
                KtTypeReference ktTypeReference = (KtTypeReference) it5.next();
                KotlinType kotlinType2 = (KotlinType) next2;
                ByDescriptorIndexer byDescriptorIndexer = INSTANCE;
                Intrinsics.checkNotNull(kotlinType2);
                if (!byDescriptorIndexer.areTypesTheSame(kotlinType2, ktTypeReference)) {
                    return false;
                }
                arrayList8.add(Unit.INSTANCE);
            }
            arrayList.add(arrayList8);
        }
        return true;
    }

    private final boolean parametersMatch(KtCallableDeclaration declaration, CallableDescriptor original) {
        ByDescriptorIndexer byDescriptorIndexer;
        KotlinType type;
        KtTypeReference ktTypeReferenceMo7396getTypeReference;
        if (declaration.getValueParameters().size() != original.getValueParameters().size()) {
            return false;
        }
        List<KtParameter> valueParameters = declaration.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<ValueParameterDescriptor> valueParameters2 = original.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters2, "getValueParameters(...)");
        Iterator it = CollectionsKt.zip(valueParameters, valueParameters2).iterator();
        do {
            if (!it.hasNext()) {
                return true;
            }
            Pair pair = (Pair) it.next();
            KtParameter ktParameter = (KtParameter) pair.component1();
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) pair.component2();
            boolean zIsVarArg = ktParameter.isVarArg();
            if (zIsVarArg != (valueParameterDescriptor.getVarargElementType() != null)) {
                return false;
            }
            byDescriptorIndexer = INSTANCE;
            if (zIsVarArg) {
                type = valueParameterDescriptor.getVarargElementType();
                Intrinsics.checkNotNull(type);
            } else {
                type = valueParameterDescriptor.getType();
                Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            }
            ktTypeReferenceMo7396getTypeReference = ktParameter.mo7396getTypeReference();
            Intrinsics.checkNotNull(ktTypeReferenceMo7396getTypeReference);
        } while (byDescriptorIndexer.areTypesTheSame(type, ktTypeReferenceMo7396getTypeReference));
        return false;
    }

    private final boolean receiverTypesMatch(KtTypeReference ktTypeReference, ReceiverParameterDescriptor receiverParameter) {
        if (ktTypeReference == null) {
            return receiverParameter == null;
        }
        if (receiverParameter == null) {
            return false;
        }
        KotlinType type = receiverParameter.getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        return areTypesTheSame(type, ktTypeReference);
    }

    private final boolean areTypesTheSame(KotlinType kotlinType, KtTypeReference ktTypeReference) {
        boolean z;
        SimpleType abbreviation;
        KtTypeElement typeElement = ktTypeReference.getTypeElement();
        KtDeclarationModifierList[] allModifierLists = ByDescriptorIndexerKt.getAllModifierLists(ktTypeReference);
        int length = allModifierLists.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            }
            if (PsiUtilsKt.hasSuspendModifier(allModifierLists[i])) {
                z = true;
                break;
            }
            i++;
        }
        String qualifiedName = ByDescriptorIndexerKt.getQualifiedName(typeElement, z);
        if (qualifiedName == null) {
            return false;
        }
        AbbreviatedType abbreviatedType = kotlinType instanceof AbbreviatedType ? (AbbreviatedType) kotlinType : null;
        if (abbreviatedType != null && (abbreviation = abbreviatedType.getAbbreviation()) != null) {
            kotlinType = abbreviation;
        }
        ClassifierDescriptor this$0 = kotlinType.getConstructor().getThis$0();
        if (this$0 == null) {
            return false;
        }
        if (this$0 instanceof TypeParameterDescriptor) {
            return Intrinsics.areEqual(((TypeParameterDescriptor) this$0).getShortName().asString(), qualifiedName);
        }
        return Intrinsics.areEqual(DescriptorUtilsKt.getFqNameSafe(this$0).asString(), qualifiedName);
    }

    static {
        ByDescriptorIndexer byDescriptorIndexer = new ByDescriptorIndexer();
        INSTANCE = byDescriptorIndexer;
        Logger logger = Logger.getInstance(byDescriptorIndexer.getClass());
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
    }
}
