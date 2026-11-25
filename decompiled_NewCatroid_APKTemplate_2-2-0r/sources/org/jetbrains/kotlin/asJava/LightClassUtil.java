package org.jetbrains.kotlin.asJava;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.asJava.classes.KtLightClass;
import org.jetbrains.kotlin.asJava.elements.KtLightElement;
import org.jetbrains.kotlin.asJava.elements.KtLightField;
import org.jetbrains.kotlin.asJava.elements.KtLightMethod;
import org.jetbrains.kotlin.asJava.elements.KtLightMethodImplKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.PsiFileStub;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationUseSiteTarget;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtAnnotationUseSiteTarget;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassBody;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.psi.KtValVarKeywordOwner;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.DataClassResolver;
import org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments;

/* JADX INFO: compiled from: LightClassUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001;B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J7\u0010\u0007\u001a\u00020\b\"\f\b\u0000\u0010\t*\u00020\n*\u00020\u000b2\u0006\u0010\f\u001a\u0002H\t2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u000eH\u0002¢\u0006\u0002\u0010\u0010J,\u0010\u0011\u001a\u0004\u0018\u00010\u00122\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u00142\u0016\u0010\u0015\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0017\u0012\u0004\u0012\u00020\u00040\u0016J\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0019\u001a\u00020\u001aJ\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000e0\u001c2\u0006\u0010\u0019\u001a\u00020\u001aJ\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u000e2\u0006\u0010 \u001a\u00020!J\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\"\u001a\u00020#J\u0014\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000e0\u001c2\u0006\u0010 \u001a\u00020!J\u001c\u0010%\u001a\b\u0012\u0004\u0012\u00020'0&2\u0006\u0010 \u001a\u00020!2\u0006\u0010(\u001a\u00020)J\u000e\u0010*\u001a\u00020\b2\u0006\u0010\"\u001a\u00020#J\u000e\u0010*\u001a\u00020\b2\u0006\u0010+\u001a\u00020,J\u0010\u0010-\u001a\u0004\u0018\u00010\u001e2\u0006\u0010.\u001a\u00020/J\u0012\u00100\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J8\u00101\u001a\b\u0012\u0004\u0012\u00020'0&2\u0006\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010(\u001a\u0004\u0018\u00010)2\u0014\b\u0002\u00102\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020\u00040\u0016H\u0002J\u0012\u00103\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0016\u00104\u001a\b\u0012\u0004\u0012\u00020\u00120&2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u001d\u00105\u001a\u00020\u00042\u000b\u00106\u001a\u00070)¢\u0006\u0002\b72\u0006\u00108\u001a\u00020)H\u0002J$\u00109\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020,2\b\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002J\f\u0010:\u001a\u00020\u0004*\u00020\u0006H\u0002¨\u0006<"}, d2 = {"Lorg/jetbrains/kotlin/asJava/LightClassUtil;", "", "()V", "canGenerateLightClass", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "extractPropertyAccessors", "Lorg/jetbrains/kotlin/asJava/LightClassUtil$PropertyAccessorsPsiMethods;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtValVarKeywordOwner;", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "ktDeclaration", "specialGetter", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "specialSetter", "(Lorg/jetbrains/kotlin/psi/KtValVarKeywordOwner;Lcom/intellij/psi/PsiMethod;Lcom/intellij/psi/PsiMethod;)Lorg/jetbrains/kotlin/asJava/LightClassUtil$PropertyAccessorsPsiMethods;", "findClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "stub", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "predicate", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/PsiClassStub;", "getLightClassAccessorMethod", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/psi/KtPropertyAccessor;", "getLightClassAccessorMethods", "", "getLightClassBackingField", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiField;", "getLightClassMethod", "function", "Lorg/jetbrains/kotlin/psi/KtFunction;", "parameter", "Lorg/jetbrains/kotlin/psi/KtParameter;", "getLightClassMethods", "getLightClassMethodsByName", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "name", "", "getLightClassPropertyMethods", "property", "Lorg/jetbrains/kotlin/psi/KtProperty;", "getLightFieldForCompanionObject", "companionObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "getPsiMethodWrapper", "getPsiMethodWrappers", "nameFilter", "getWrappingClass", "getWrappingClasses", "isMangled", "wrapperName", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/NlsSafe;", "prefix", "noAdditionalAccessorsExpected", "isSpecialNameProvided", "PropertyAccessorsPsiMethods", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LightClassUtil {
    public static final LightClassUtil INSTANCE = new LightClassUtil();

    private LightClassUtil() {
    }

    public final PsiClass findClass(StubElement<?> stub, Function1<? super PsiClassStub<?>, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(stub, "stub");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        boolean z = stub instanceof PsiClassStub;
        if (z && predicate.invoke(stub).booleanValue()) {
            return (PsiClass) ((PsiClassStub) stub).getPsi();
        }
        if (!z && !(stub instanceof PsiFileStub)) {
            return null;
        }
        for (StubElement stubElement : stub.getChildrenStubs()) {
            Intrinsics.checkNotNull(stubElement);
            PsiClass psiClassFindClass = findClass(stubElement, predicate);
            if (psiClassFindClass != null) {
                return psiClassFindClass;
            }
        }
        return null;
    }

    public final PsiMethod getLightClassAccessorMethod(KtPropertyAccessor accessor) {
        Intrinsics.checkNotNullParameter(accessor, "accessor");
        return (PsiMethod) CollectionsKt.firstOrNull((List) getLightClassAccessorMethods(accessor));
    }

    public final List<PsiMethod> getLightClassAccessorMethods(final KtPropertyAccessor accessor) {
        Object next;
        Intrinsics.checkNotNullParameter(accessor, "accessor");
        KtProperty property = accessor.getProperty();
        Intrinsics.checkNotNullExpressionValue(property, "getProperty(...)");
        List<KtAnnotationEntry> annotationEntries = accessor.getAnnotationEntries();
        Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
        Iterator<T> it = annotationEntries.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(JvmNames.JVM_NAME.shortName(), ((KtAnnotationEntry) next).getShortName()));
        final boolean z = next != null || isSpecialNameProvided(property);
        final String name2 = accessor.getProperty().get_name();
        return name2 == null ? CollectionsKt.emptyList() : SequencesKt.toList(getPsiMethodWrappers$default(this, property, null, new Function1<KtLightMethod, Boolean>() { // from class: org.jetbrains.kotlin.asJava.LightClassUtil$getLightClassAccessorMethods$wrappers$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Code duplicated, block: B:23:0x005b  */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KtLightMethod wrapper) {
                Intrinsics.checkNotNullParameter(wrapper, "wrapper");
                String name3 = wrapper.get_name();
                Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                boolean z2 = false;
                if (z) {
                    if (accessor.isGetter()) {
                    }
                } else if (accessor.isGetter()) {
                    String str = JvmAbi.getterName(name2);
                    if (Intrinsics.areEqual(name3, str) || LightClassUtil.INSTANCE.isMangled(name3, str)) {
                        z2 = true;
                    }
                } else {
                    String str2 = JvmAbi.setterName(name2);
                    if (Intrinsics.areEqual(name3, str2) || LightClassUtil.INSTANCE.isMangled(name3, str2)) {
                        z2 = true;
                    }
                }
                return Boolean.valueOf(z2);
            }
        }, 2, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isMangled(String wrapperName, String prefix) {
        return StringsKt.startsWith$default(wrapperName, prefix + '$', false, 2, (Object) null);
    }

    public final PsiField getLightFieldForCompanionObject(KtClassOrObject companionObject) {
        Intrinsics.checkNotNullParameter(companionObject, "companionObject");
        PsiClass wrappingClass = getWrappingClass(companionObject);
        if (wrappingClass == null) {
            return null;
        }
        PsiField[] fields = wrappingClass.getFields();
        Intrinsics.checkNotNullExpressionValue(fields, "getFields(...)");
        for (PsiField psiField : fields) {
            if ((psiField instanceof KtLightElement) && ((KtLightElement) psiField).getKotlinOrigin() == companionObject.getOriginalElement()) {
                return psiField;
            }
        }
        return null;
    }

    public final PropertyAccessorsPsiMethods getLightClassPropertyMethods(KtProperty property) {
        Intrinsics.checkNotNullParameter(property, "property");
        KtPropertyAccessor getter = property.getGetter();
        KtPropertyAccessor setter = property.getSetter();
        return extractPropertyAccessors(property, getter != null ? getLightClassAccessorMethod(getter) : null, setter != null ? getLightClassAccessorMethod(setter) : null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final PsiField getLightClassBackingField(KtDeclaration declaration) {
        KtClass ktClass;
        KtLightClass lightClass;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        KtLightClass wrappingClass = getWrappingClass(declaration);
        if (wrappingClass == null) {
            return null;
        }
        if (wrappingClass instanceof KtLightClass) {
            KtClassOrObject ktClassOrObject = (KtClassOrObject) ((KtLightClass) wrappingClass).getKotlinOrigin();
            if ((ktClassOrObject instanceof KtObjectDeclaration) && ((KtObjectDeclaration) ktClassOrObject).isCompanion() && (ktClass = (KtClass) PsiTreeUtil.getParentOfType(ktClassOrObject, KtClass.class)) != null && (lightClass = LightClassUtilsKt.toLightClass(ktClass)) != null) {
                wrappingClass = lightClass;
            }
        }
        PsiField[] fields = wrappingClass.getFields();
        Intrinsics.checkNotNullExpressionValue(fields, "getFields(...)");
        for (PsiField psiField : fields) {
            if ((psiField instanceof KtLightField) && ((KtLightField) psiField).getKotlinOrigin() == declaration) {
                return psiField;
            }
        }
        return null;
    }

    public final PropertyAccessorsPsiMethods getLightClassPropertyMethods(KtParameter parameter) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        return extractPropertyAccessors(parameter, null, null);
    }

    public final PsiMethod getLightClassMethod(KtFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return getPsiMethodWrapper(function);
    }

    public final PsiMethod getLightClassMethod(KtParameter parameter) {
        Intrinsics.checkNotNullParameter(parameter, "parameter");
        return getPsiMethodWrapper(parameter);
    }

    public final List<PsiMethod> getLightClassMethods(KtFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return SequencesKt.toList(getPsiMethodWrappers$default(this, function, null, null, 6, null));
    }

    public final Sequence<KtLightMethod> getLightClassMethodsByName(KtFunction function, String name2) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(name2, "name");
        return getPsiMethodWrappers$default(this, function, name2, null, 4, null);
    }

    private final PsiMethod getPsiMethodWrapper(KtDeclaration declaration) {
        return (PsiMethod) SequencesKt.firstOrNull(getPsiMethodWrappers$default(this, declaration, null, null, 6, null));
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ Sequence getPsiMethodWrappers$default(LightClassUtil lightClassUtil, KtDeclaration ktDeclaration, final String str, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        if ((i & 4) != 0) {
            function1 = new Function1<KtLightMethod, Boolean>() { // from class: org.jetbrains.kotlin.asJava.LightClassUtil.getPsiMethodWrappers.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(KtLightMethod it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    String str2 = str;
                    return Boolean.valueOf(str2 == null || Intrinsics.areEqual(str2, it.get_name()));
                }
            };
        }
        return lightClassUtil.getPsiMethodWrappers(ktDeclaration, str, function1);
    }

    private final Sequence<KtLightMethod> getPsiMethodWrappers(final KtDeclaration declaration, String name2, Function1<? super KtLightMethod, Boolean> nameFilter) {
        Sequence sequenceFilter = SequencesKt.filter(SequencesKt.flatMap(getWrappingClasses(declaration), new Function1<PsiClass, Sequence<? extends PsiMethod>>() { // from class: org.jetbrains.kotlin.asJava.LightClassUtil.getPsiMethodWrappers.2
            @Override // kotlin.jvm.functions.Function1
            public final Sequence<PsiMethod> invoke(PsiClass it) {
                Intrinsics.checkNotNullParameter(it, "it");
                PsiMethod[] methods = it.getMethods();
                Intrinsics.checkNotNullExpressionValue(methods, "getMethods(...)");
                return ArraysKt.asSequence(methods);
            }
        }), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.asJava.LightClassUtil$getPsiMethodWrappers$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof KtLightMethod);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return SequencesKt.filter(SequencesKt.filter(sequenceFilter, nameFilter), new Function1<KtLightMethod, Boolean>() { // from class: org.jetbrains.kotlin.asJava.LightClassUtil.getPsiMethodWrappers.3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KtLightMethod it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.getKotlinOrigin() == declaration || it.getNavigationElement() == declaration);
            }
        });
    }

    private final PsiClass getWrappingClass(KtDeclaration declaration) {
        KtClassOrObject classIfParameterIsProperty;
        if ((declaration instanceof KtParameter) && (classIfParameterIsProperty = KtPsiUtil.getClassIfParameterIsProperty((KtParameter) declaration)) != null) {
            return LightClassUtilsKt.toLightClass(classIfParameterIsProperty);
        }
        if (declaration instanceof KtPropertyAccessor) {
            KtProperty property = ((KtPropertyAccessor) declaration).getProperty();
            Intrinsics.checkNotNullExpressionValue(property, "getProperty(...)");
            declaration = property;
        }
        if (declaration instanceof KtConstructor) {
            return LightClassUtilsKt.toLightClass(((KtConstructor) declaration).getContainingClassOrObject());
        }
        PsiElement parent = declaration.getLightOwner();
        if (parent instanceof KtFile) {
            return LightClassUtilsKt.findFacadeClass((KtFile) parent);
        }
        Class<?> cls = null;
        if (!(parent instanceof KtClassBody)) {
            return null;
        }
        KtClassBody ktClassBody = (KtClassBody) parent;
        if (!(ktClassBody.getLightOwner() instanceof KtClassOrObject)) {
            StringBuilder sb = new StringBuilder("Bad parent: ");
            PsiElement lightOwner = ktClassBody.getLightOwner();
            if (lightOwner != null) {
                Intrinsics.checkNotNull(lightOwner);
                cls = lightOwner.getClass();
            }
            KotlinExceptionWithAttachments kotlinExceptionWithAttachments = new KotlinExceptionWithAttachments(sb.append(cls).toString());
            kotlinExceptionWithAttachments.withPsiAttachment("parent", parent);
            throw kotlinExceptionWithAttachments;
        }
        PsiElement lightOwner2 = ktClassBody.getLightOwner();
        Intrinsics.checkNotNull(lightOwner2, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtClassOrObject");
        return LightClassUtilsKt.toLightClass((KtClassOrObject) lightOwner2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final Sequence<PsiClass> getWrappingClasses(KtDeclaration declaration) {
        PsiClass wrappingClass = getWrappingClass(declaration);
        if (wrappingClass == null) {
            return SequencesKt.emptySequence();
        }
        KtLightClass ktLightClass = (KtLightClass) wrappingClass;
        KtClassOrObject ktClassOrObject = (KtClassOrObject) ktLightClass.getKotlinOrigin();
        if (!(ktClassOrObject instanceof KtObjectDeclaration) || !((KtObjectDeclaration) ktClassOrObject).isCompanion() || !(ktLightClass.getLightOwner() instanceof PsiClass)) {
            return SequencesKt.sequenceOf(wrappingClass);
        }
        PsiElement parent = ktLightClass.getLightOwner();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type com.intellij.psi.PsiClass");
        return SequencesKt.sequenceOf(wrappingClass, parent);
    }

    public final boolean canGenerateLightClass(KtDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return PsiTreeUtil.getParentOfType(declaration, KtFunction.class, KtProperty.class) == null;
    }

    private final boolean isSpecialNameProvided(KtDeclaration ktDeclaration) {
        KtAnnotationUseSiteTarget useSiteTarget;
        List<KtAnnotationEntry> annotationEntries = ktDeclaration.getAnnotationEntries();
        Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
        List<KtAnnotationEntry> list = annotationEntries;
        if ((list instanceof Collection) && list.isEmpty()) {
            return false;
        }
        for (KtAnnotationEntry ktAnnotationEntry : list) {
            AnnotationUseSiteTarget annotationUseSiteTarget = null;
            if (Intrinsics.areEqual(JvmNames.JVM_NAME.shortName(), ktAnnotationEntry.getShortName()) && (useSiteTarget = ktAnnotationEntry.getUseSiteTarget()) != null) {
                annotationUseSiteTarget = useSiteTarget.getAnnotationUseSiteTarget();
            }
            if (annotationUseSiteTarget == AnnotationUseSiteTarget.PROPERTY_GETTER || annotationUseSiteTarget == AnnotationUseSiteTarget.PROPERTY_SETTER) {
                return true;
            }
        }
        return false;
    }

    private final <T extends KtValVarKeywordOwner & KtNamedDeclaration> PropertyAccessorsPsiMethods extractPropertyAccessors(T t, PsiMethod psiMethod, PsiMethod psiMethod2) {
        Sequence psiMethodWrappers$default;
        if ((t instanceof KtProperty) && noAdditionalAccessorsExpected((KtProperty) t, psiMethod2, psiMethod)) {
            psiMethodWrappers$default = SequencesKt.emptySequence();
        } else {
            KtDeclaration ktDeclaration = (KtDeclaration) t;
            if (isSpecialNameProvided(ktDeclaration)) {
                psiMethodWrappers$default = getPsiMethodWrappers$default(this, ktDeclaration, null, null, 6, null);
            } else {
                final String name2 = t.get_name();
                final String str = name2 != null ? JvmAbi.getterName(name2) : null;
                final String str2 = name2 != null ? JvmAbi.setterName(name2) : null;
                psiMethodWrappers$default = getPsiMethodWrappers$default(this, ktDeclaration, null, new Function1<KtLightMethod, Boolean>() { // from class: org.jetbrains.kotlin.asJava.LightClassUtil$extractPropertyAccessors$accessorWrappers$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    /* JADX WARN: Code duplicated, block: B:18:0x004d  */
                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(KtLightMethod wrapper) {
                        boolean z;
                        Intrinsics.checkNotNullParameter(wrapper, "wrapper");
                        String name3 = wrapper.get_name();
                        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                        String str3 = name2;
                        if (str3 == null || Intrinsics.areEqual(str3, name3) || Intrinsics.areEqual(name3, str)) {
                            z = true;
                        } else {
                            LightClassUtil lightClassUtil = LightClassUtil.INSTANCE;
                            String str4 = str;
                            Intrinsics.checkNotNull(str4);
                            if (lightClassUtil.isMangled(name3, str4) || Intrinsics.areEqual(name3, str2)) {
                                z = true;
                            } else {
                                LightClassUtil lightClassUtil2 = LightClassUtil.INSTANCE;
                                String str5 = str2;
                                Intrinsics.checkNotNull(str5);
                                if (lightClassUtil2.isMangled(name3, str5) || DataClassResolver.INSTANCE.isComponentLike(name3)) {
                                    z = true;
                                } else {
                                    z = false;
                                }
                            }
                        }
                        return Boolean.valueOf(z);
                    }
                }, 2, null);
            }
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : psiMethodWrappers$default) {
            if (KtLightMethodImplKt.isSetter((KtLightMethod) obj)) {
                arrayList.add(obj);
            } else {
                arrayList2.add(obj);
            }
        }
        Pair pair = new Pair(arrayList, arrayList2);
        List list = (List) pair.component1();
        List list2 = (List) pair.component2();
        List listListOfNotNull = CollectionsKt.listOfNotNull(psiMethod);
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : list2) {
            if (!Intrinsics.areEqual((KtLightMethod) obj2, psiMethod)) {
                arrayList3.add(obj2);
            }
        }
        List listPlus = CollectionsKt.plus((Collection) listListOfNotNull, (Iterable) arrayList3);
        List listListOfNotNull2 = CollectionsKt.listOfNotNull(psiMethod2);
        ArrayList arrayList4 = new ArrayList();
        for (Object obj3 : list) {
            if (!Intrinsics.areEqual((KtLightMethod) obj3, psiMethod2)) {
                arrayList4.add(obj3);
            }
        }
        List listPlus2 = CollectionsKt.plus((Collection) listListOfNotNull2, (Iterable) arrayList4);
        return new PropertyAccessorsPsiMethods((PsiMethod) CollectionsKt.firstOrNull(listPlus), (PsiMethod) CollectionsKt.firstOrNull(listPlus2), getLightClassBackingField((KtDeclaration) t), CollectionsKt.plus((Collection) CollectionsKt.drop(listPlus, 1), (Iterable) CollectionsKt.drop(listPlus2, 1)));
    }

    private final boolean noAdditionalAccessorsExpected(KtProperty ktDeclaration, PsiMethod specialSetter, PsiMethod specialGetter) {
        KtClassOrObject containingClassOrObject = KtPsiUtilKt.getContainingClassOrObject(ktDeclaration);
        KtObjectDeclaration ktObjectDeclaration = containingClassOrObject instanceof KtObjectDeclaration ? (KtObjectDeclaration) containingClassOrObject : null;
        if (ktObjectDeclaration != null && ktObjectDeclaration.isCompanion()) {
            return false;
        }
        if (ktDeclaration.isVar()) {
            if (specialSetter == null || specialGetter == null) {
                return false;
            }
        } else if (specialGetter == null) {
            return false;
        }
        return true;
    }

    /* JADX INFO: compiled from: LightClassUtil.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010(\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B1\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\b¢\u0006\u0002\u0010\tJ\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00020\u0015H\u0096\u0002R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0002¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/asJava/LightClassUtil$PropertyAccessorsPsiMethods;", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "getter", "setter", "backingField", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiField;", "additionalAccessors", "", "(Lcom/intellij/psi/PsiMethod;Lcom/intellij/psi/PsiMethod;Lcom/intellij/psi/PsiField;Ljava/util/List;)V", "allDeclarations", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiNamedElement;", "getAllDeclarations", "()Ljava/util/List;", "allMethods", "getBackingField", "()Lcom/intellij/psi/PsiField;", "getGetter", "()Lcom/intellij/psi/PsiMethod;", "getSetter", "iterator", "", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PropertyAccessorsPsiMethods implements Iterable<PsiMethod>, KMappedMarker {
        private final List<PsiNamedElement> allDeclarations;
        private final List<PsiMethod> allMethods;
        private final PsiField backingField;
        private final PsiMethod getter;
        private final PsiMethod setter;

        public PropertyAccessorsPsiMethods(PsiMethod psiMethod, PsiMethod psiMethod2, PsiField psiField, List<? extends PsiMethod> additionalAccessors) {
            Intrinsics.checkNotNullParameter(additionalAccessors, "additionalAccessors");
            this.getter = psiMethod;
            this.setter = psiMethod2;
            this.backingField = psiField;
            ArrayList arrayList = new ArrayList();
            this.allMethods = arrayList;
            ArraysKt.filterNotNullTo(new PsiMethod[]{psiMethod, psiMethod2}, arrayList);
            ArrayList arrayList2 = arrayList;
            for (Object obj : additionalAccessors) {
                if (obj instanceof PsiMethod) {
                    arrayList2.add(obj);
                }
            }
            ArrayList arrayList3 = new ArrayList();
            this.allDeclarations = arrayList3;
            ArraysKt.filterNotNullTo(new PsiNamedElement[]{this.getter, this.setter, this.backingField}, arrayList3);
            arrayList3.addAll(additionalAccessors);
        }

        public final PsiMethod getGetter() {
            return this.getter;
        }

        public final PsiMethod getSetter() {
            return this.setter;
        }

        public final PsiField getBackingField() {
            return this.backingField;
        }

        public final List<PsiNamedElement> getAllDeclarations() {
            return this.allDeclarations;
        }

        @Override // java.lang.Iterable
        public Iterator<PsiMethod> iterator() {
            return this.allMethods.iterator();
        }
    }
}
