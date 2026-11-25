package org.jetbrains.kotlin.resolve.checkers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFunctionType;
import org.jetbrains.kotlin.psi.KtNameReferenceExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.KtTreeVisitorVoid;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.calls.util.FakeCallableDescriptorForTypeAliasObject;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: ClassifierUsageChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a*\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t¨\u0006\n"}, d2 = {"checkClassifierUsages", "", "declarations", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "checkers", "", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageChecker;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageCheckerContext;", "frontend"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClassifierUsageCheckerKt {
    public static final void checkClassifierUsages(Collection<? extends PsiElement> declarations, final Iterable<? extends ClassifierUsageChecker> checkers, final ClassifierUsageCheckerContext context) {
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        Intrinsics.checkNotNullParameter(checkers, "checkers");
        Intrinsics.checkNotNullParameter(context, "context");
        KtTreeVisitorVoid ktTreeVisitorVoid = new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.resolve.checkers.ClassifierUsageCheckerKt$checkClassifierUsages$visitor$1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitReferenceExpression(KtReferenceExpression expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                super.visitReferenceExpression(expression);
                if ((expression instanceof KtNameReferenceExpression) && Intrinsics.areEqual(((KtNameReferenceExpression) expression).getReferencedNameElementType(), KtTokens.SUPER_KEYWORD)) {
                    return;
                }
                Iterator<ClassifierDescriptor> it = getReferencedClassifiers(expression).iterator();
                while (it.hasNext()) {
                    runCheckersWithTarget(it.next(), expression);
                }
            }

            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitFunctionType(KtFunctionType type) {
                KotlinType kotlinType;
                ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor;
                Intrinsics.checkNotNullParameter(type, "type");
                super.visitFunctionType(type);
                BindingTrace trace = context.getTrace();
                WritableSlice<KtTypeReference, KotlinType> writableSlice = BindingContext.TYPE;
                PsiElement parent = type.getParent();
                KtTypeReference ktTypeReference = parent instanceof KtTypeReference ? (KtTypeReference) parent : null;
                if (ktTypeReference == null || (kotlinType = (KotlinType) trace.get(writableSlice, ktTypeReference)) == null || (classifierDescriptorMo7434getDeclarationDescriptor = kotlinType.getConstructor().mo7434getDeclarationDescriptor()) == null) {
                    return;
                }
                runCheckersWithTarget(classifierDescriptorMo7434getDeclarationDescriptor, type);
            }

            private final void runCheckersWithTarget(ClassifierDescriptor target, KtElement expression) {
                Iterator<ClassifierUsageChecker> it = checkers.iterator();
                while (it.hasNext()) {
                    it.next().check(target, expression, context);
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            private final List<ClassifierDescriptor> getReferencedClassifiers(KtReferenceExpression expression) {
                DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) context.getTrace().get(BindingContext.REFERENCE_TARGET, expression);
                if (declarationDescriptor instanceof ClassifierDescriptor) {
                    return CollectionsKt.listOfNotNull((Object[]) new ClassifierDescriptor[]{declarationDescriptor, getClassifierUsedToReferenceCompanionObject((ClassifierDescriptor) declarationDescriptor, expression)});
                }
                if (declarationDescriptor instanceof ClassConstructorDescriptor) {
                    return CollectionsKt.listOf(((ClassConstructorDescriptor) declarationDescriptor).getConstructedClass());
                }
                if (declarationDescriptor instanceof FakeCallableDescriptorForTypeAliasObject) {
                    FakeCallableDescriptorForTypeAliasObject fakeCallableDescriptorForTypeAliasObject = (FakeCallableDescriptorForTypeAliasObject) declarationDescriptor;
                    ClassDescriptor referencedObject = fakeCallableDescriptorForTypeAliasObject.getReferencedObject();
                    TypeAliasDescriptor typeAliasDescriptor = fakeCallableDescriptorForTypeAliasObject.getTypeAliasDescriptor();
                    return !Intrinsics.areEqual(referencedObject, typeAliasDescriptor.getClassDescriptor()) ? CollectionsKt.listOf((Object[]) new ClassifierDescriptorWithTypeParameters[]{referencedObject, typeAliasDescriptor}) : CollectionsKt.listOf(typeAliasDescriptor);
                }
                Collection collection = (Collection) context.getTrace().get(BindingContext.AMBIGUOUS_REFERENCE_TARGET, expression);
                if (collection == null) {
                    return CollectionsKt.emptyList();
                }
                Collection collection2 = collection;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Object obj : collection2) {
                    DeclarationDescriptor declarationDescriptor2 = (DeclarationDescriptor) obj;
                    DeclarationDescriptorWithSource declarationDescriptorWithSource = declarationDescriptor2 instanceof DeclarationDescriptorWithSource ? (DeclarationDescriptorWithSource) declarationDescriptor2 : null;
                    SourceElement source = declarationDescriptorWithSource != null ? declarationDescriptorWithSource.getSource() : null;
                    Object obj2 = linkedHashMap.get(source);
                    if (obj2 == null) {
                        obj2 = (List) new ArrayList();
                        linkedHashMap.put(source, obj2);
                    }
                    ((List) obj2).add(obj);
                }
                if (linkedHashMap.size() != 1) {
                    return CollectionsKt.emptyList();
                }
                ArrayList arrayList = new ArrayList();
                for (Object obj3 : collection2) {
                    if (obj3 instanceof ClassifierDescriptor) {
                        arrayList.add(obj3);
                    }
                }
                ArrayList arrayList2 = arrayList;
                return arrayList2.size() == 1 ? arrayList2 : CollectionsKt.emptyList();
            }

            private final ClassifierDescriptor getClassifierUsedToReferenceCompanionObject(ClassifierDescriptor referencedObject, KtReferenceExpression expression) {
                if (DescriptorUtilsKt.isCompanionObject(referencedObject)) {
                    return (ClassifierDescriptor) context.getTrace().get(BindingContext.SHORT_REFERENCE_TO_COMPANION_OBJECT, expression);
                }
                return null;
            }
        };
        Iterator<? extends PsiElement> it = declarations.iterator();
        while (it.hasNext()) {
            it.next().accept(ktTreeVisitorVoid);
        }
    }
}
