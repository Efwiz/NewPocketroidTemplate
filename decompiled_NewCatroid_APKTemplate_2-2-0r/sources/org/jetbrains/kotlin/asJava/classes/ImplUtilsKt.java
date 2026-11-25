package org.jetbrains.kotlin.asJava.classes;

import androidx.exifinterface.media.ExifInterface;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.analyzer.KotlinModificationTrackerService;
import org.jetbrains.kotlin.com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiArrayType;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiJavaCodeReferenceElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiPrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiType;
import org.jetbrains.kotlin.com.intellij.psi.TypeAnnotationProvider;
import org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtSuperTypeCallEntry;
import org.jetbrains.kotlin.psi.KtSuperTypeList;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtUserType;

/* JADX INFO: compiled from: implUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000n\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a \u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00100\u000f\"\u0004\b\u0000\u0010\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00100\u0012\u001a\"\u0010\u0013\u001a\u00020\u0004*\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a\u001a\u001e\u0010\u001b\u001a\u00020\u0002*\u00020\u00022\u0012\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001e0\u001d\u001a\n\u0010 \u001a\u00020!*\u00020\"\u001a\u0014\u0010#\u001a\u0004\u0018\u00010\u0018*\u00020\u00162\u0006\u0010$\u001a\u00020\n\u001a\u0010\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u001e*\u00020'\"-\u0010\u0000\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00018BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006\"\u001a\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006("}, d2 = {"setPsiTypeAnnotationProvider", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiType;", "Lorg/jetbrains/kotlin/com/intellij/psi/TypeAnnotationProvider;", "", "getSetPsiTypeAnnotationProvider", "()Lkotlin/jvm/functions/Function2;", "setPsiTypeAnnotationProvider$delegate", "Lkotlin/Lazy;", "fqName", "", "Lorg/jetbrains/kotlin/psi/KtUserType;", "getFqName", "(Lorg/jetbrains/kotlin/psi/KtUserType;)Ljava/lang/String;", "lazyPub", "Lkotlin/Lazy;", ExifInterface.GPS_DIRECTION_TRUE, "initializer", "Lkotlin/Function0;", "addSuperTypeEntry", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReferenceList;", "superTypeList", "Lorg/jetbrains/kotlin/psi/KtSuperTypeList;", "entry", "Lorg/jetbrains/kotlin/psi/KtSuperTypeListEntry;", "reference", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiJavaCodeReferenceElement;", "annotateByTypeAnnotationProvider", "annotations", "Lkotlin/sequences/Sequence;", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotation;", "cannotModify", "", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/light/LightElement;", "findEntry", "fqNameToFind", "getExternalDependencies", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/ModificationTracker;", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "light-classes-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ImplUtilsKt {
    private static final Lazy setPsiTypeAnnotationProvider$delegate = lazyPub(new Function0<Function2<? super PsiType, ? super TypeAnnotationProvider, ? extends Unit>>() { // from class: org.jetbrains.kotlin.asJava.classes.ImplUtilsKt$setPsiTypeAnnotationProvider$2
        @Override // kotlin.jvm.functions.Function0
        public final Function2<? super PsiType, ? super TypeAnnotationProvider, ? extends Unit> invoke() throws NoSuchFieldException {
            final Field field = null;
            try {
                Field declaredField = PsiType.class.getDeclaredField("myAnnotationProvider");
                declaredField.setAccessible(true);
                field = declaredField;
            } catch (NoSuchFieldException e) {
                if (ApplicationManager.getApplication().isInternal()) {
                    throw e;
                }
            } catch (SecurityException e2) {
                if (ApplicationManager.getApplication().isInternal()) {
                    throw e2;
                }
            }
            return new Function2<PsiType, TypeAnnotationProvider, Unit>() { // from class: org.jetbrains.kotlin.asJava.classes.ImplUtilsKt$setPsiTypeAnnotationProvider$2.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(PsiType psiType, TypeAnnotationProvider typeAnnotationProvider) throws IllegalAccessException {
                    invoke2(psiType, typeAnnotationProvider);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(PsiType psiType, TypeAnnotationProvider provider) throws IllegalAccessException {
                    Intrinsics.checkNotNullParameter(psiType, "psiType");
                    Intrinsics.checkNotNullParameter(provider, "provider");
                    Field field2 = field;
                    if (field2 != null) {
                        field2.set(psiType, provider);
                    }
                }
            };
        }
    });

    public static final KtSuperTypeListEntry findEntry(KtSuperTypeList ktSuperTypeList, String fqNameToFind) {
        Object fqName;
        Object next;
        Intrinsics.checkNotNullParameter(ktSuperTypeList, "<this>");
        Intrinsics.checkNotNullParameter(fqNameToFind, "fqNameToFind");
        List<KtSuperTypeListEntry> entries = ktSuperTypeList.getEntries();
        Intrinsics.checkNotNullExpressionValue(entries, "getEntries(...)");
        Iterator<T> it = entries.iterator();
        do {
            fqName = null;
            if (it.hasNext()) {
                next = it.next();
                KtUserType typeAsUserType = ((KtSuperTypeListEntry) next).getTypeAsUserType();
                if (typeAsUserType != null) {
                    Intrinsics.checkNotNull(typeAsUserType);
                    fqName = getFqName(typeAsUserType);
                }
            }
            return (KtSuperTypeListEntry) fqName;
        } while (!Intrinsics.areEqual(fqName, fqNameToFind));
        fqName = next;
        return (KtSuperTypeListEntry) fqName;
    }

    private static final String getFqName(KtUserType ktUserType) {
        String string;
        KtUserType qualifier = ktUserType.getQualifier();
        return (qualifier == null || (string = new StringBuilder().append(getFqName(qualifier)).append('.').append(ktUserType.getReferencedName()).toString()) == null) ? ktUserType.getReferencedName() : string;
    }

    public static final <T> Lazy<T> lazyPub(Function0<? extends T> initializer) {
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        return LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) initializer);
    }

    public static final Void cannotModify(LightElement lightElement) {
        Intrinsics.checkNotNullParameter(lightElement, "<this>");
        throw new IncorrectOperationException("Modification not implemented.");
    }

    public static final void addSuperTypeEntry(PsiReferenceList psiReferenceList, KtSuperTypeList superTypeList, KtSuperTypeListEntry entry, PsiJavaCodeReferenceElement reference) {
        KtSuperTypeCallEntry ktSuperTypeCallEntryCreateSuperTypeCallEntry;
        Intrinsics.checkNotNullParameter(psiReferenceList, "<this>");
        Intrinsics.checkNotNullParameter(superTypeList, "superTypeList");
        Intrinsics.checkNotNullParameter(entry, "entry");
        Intrinsics.checkNotNullParameter(reference, "reference");
        PsiElement parent = reference.getLightParent();
        DefaultConstructorMarker defaultConstructorMarker = null;
        PsiReferenceList psiReferenceList2 = parent instanceof PsiReferenceList ? (PsiReferenceList) parent : null;
        if ((psiReferenceList2 != null ? psiReferenceList2.getRole() : null) == PsiReferenceList.Role.IMPLEMENTS_LIST && psiReferenceList.getRole() == PsiReferenceList.Role.EXTENDS_LIST) {
            Project project = psiReferenceList.getProject();
            Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
            ktSuperTypeCallEntryCreateSuperTypeCallEntry = new KtPsiFactory(project, false, 2, defaultConstructorMarker).createSuperTypeCallEntry(entry.getText() + "()");
        } else {
            ktSuperTypeCallEntryCreateSuperTypeCallEntry = entry;
        }
        if (!Intrinsics.areEqual(entry.getLightParent(), superTypeList)) {
            superTypeList.addEntry(ktSuperTypeCallEntryCreateSuperTypeCallEntry);
        } else {
            entry.replace(ktSuperTypeCallEntryCreateSuperTypeCallEntry);
        }
    }

    public static final List<ModificationTracker> getExternalDependencies(KtClassOrObject ktClassOrObject) {
        Intrinsics.checkNotNullParameter(ktClassOrObject, "<this>");
        KotlinModificationTrackerService.Companion companion = KotlinModificationTrackerService.INSTANCE;
        Project project = ktClassOrObject.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        KotlinModificationTrackerService companion2 = companion.getInstance(project);
        if (!ktClassOrObject.isLocal()) {
            return CollectionsKt.listOf(companion2.getOutOfBlockModificationTracker());
        }
        PsiFile containingFile = ktClassOrObject.getContainingFile();
        Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
        return containingFile instanceof KtFile ? CollectionsKt.listOf((Object[]) new ModificationTracker[]{companion2.getOutOfBlockModificationTracker(), companion2.fileModificationTracker((KtFile) containingFile)}) : CollectionsKt.listOf(companion2.getOutOfBlockModificationTracker());
    }

    private static final Function2<PsiType, TypeAnnotationProvider, Unit> getSetPsiTypeAnnotationProvider() {
        return (Function2) setPsiTypeAnnotationProvider$delegate.getValue();
    }

    public static final PsiType annotateByTypeAnnotationProvider(PsiType psiType, Sequence<? extends List<? extends PsiAnnotation>> annotations) {
        Intrinsics.checkNotNullParameter(psiType, "<this>");
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Iterator<? extends List<? extends PsiAnnotation>> it = annotations.iterator();
        if (!it.hasNext()) {
            return psiType;
        }
        if (psiType instanceof PsiPrimitiveType) {
            List<? extends PsiAnnotation> next = it.next();
            if (next.isEmpty()) {
                return psiType;
            }
            TypeAnnotationProvider typeAnnotationProviderCreate = TypeAnnotationProvider.Static.create((PsiAnnotation[]) next.toArray(new PsiAnnotation[0]));
            Intrinsics.checkNotNullExpressionValue(typeAnnotationProviderCreate, "create(...)");
            PsiPrimitiveType psiPrimitiveTypeAnnotate = ((PsiPrimitiveType) psiType).annotate(typeAnnotationProviderCreate);
            Intrinsics.checkNotNull(psiPrimitiveTypeAnnotate);
            return psiPrimitiveTypeAnnotate;
        }
        annotateByTypeAnnotationProvider$recursiveAnnotator(it, psiType);
        return psiType;
    }

    private static final void annotateByTypeAnnotationProvider$recursiveAnnotator(Iterator<? extends List<? extends PsiAnnotation>> it, PsiType psiType) {
        if (it.hasNext()) {
            List<? extends PsiAnnotation> next = it.next();
            if (psiType instanceof PsiPrimitiveType) {
                return;
            }
            if (psiType instanceof PsiClassType) {
                PsiType[] parameters = ((PsiClassType) psiType).getParameters();
                Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
                for (PsiType psiType2 : parameters) {
                    Intrinsics.checkNotNull(psiType2);
                    annotateByTypeAnnotationProvider$recursiveAnnotator(it, psiType2);
                }
            } else if (psiType instanceof PsiArrayType) {
                PsiType componentType = ((PsiArrayType) psiType).getComponentType();
                Intrinsics.checkNotNullExpressionValue(componentType, "getComponentType(...)");
                annotateByTypeAnnotationProvider$recursiveAnnotator(it, componentType);
            }
            if (next.isEmpty()) {
                return;
            }
            TypeAnnotationProvider typeAnnotationProviderCreate = TypeAnnotationProvider.Static.create((PsiAnnotation[]) next.toArray(new PsiAnnotation[0]));
            Intrinsics.checkNotNullExpressionValue(typeAnnotationProviderCreate, "create(...)");
            getSetPsiTypeAnnotationProvider().invoke(psiType, typeAnnotationProviderCreate);
        }
    }
}
