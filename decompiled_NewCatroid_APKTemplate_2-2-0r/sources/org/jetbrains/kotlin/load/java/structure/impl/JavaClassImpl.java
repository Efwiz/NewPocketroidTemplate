package org.jetbrains.kotlin.load.java.structure.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.KtLightClassMarker;
import org.jetbrains.kotlin.asJava.SyntheticElementUtilsKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiRecordComponent;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaConstructor;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.load.java.structure.JavaRecordComponent;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.LightClassOriginKind;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtPsiUtil;

/* JADX INFO: compiled from: JavaClassImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¬\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 Q2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\u00020\u00042\u00020\u0005:\u0001QB\u000f\b\u0016\u0012\u0006\u0010\u0006\u001a\u00020\u0002¢\u0006\u0002\u0010\u0007B\u0013\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010F\u001a\u00020GH\u0002J\u0012\u0010H\u001a\u0004\u0018\u00010I2\u0006\u0010+\u001a\u00020\u0018H\u0016J\u000f\u0010J\u001a\t\u0018\u00010K¢\u0006\u0002\bLH\u0016J\b\u0010M\u001a\u00020\u001bH\u0016J\u0010\u0010N\u001a\u00020\u001b2\u0006\u0010O\u001a\u00020PH\u0016R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000fR\u0016\u0010\u0013\u001a\u0004\u0018\u00010\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u000fR\u0014\u0010\u001a\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001cR\u0014\u0010\u001d\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001cR\u0014\u0010\u001e\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001cR\u0014\u0010\u001f\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u001cR\u0014\u0010 \u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010\u001cR\u0014\u0010!\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\u001cR\u0014\u0010\"\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010\u001cR\u0014\u0010#\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010\u001cR\u0016\u0010$\u001a\u0004\u0018\u00010%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b&\u0010'R\u001a\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010\u000fR\u0014\u0010+\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0016\u0010.\u001a\u0004\u0018\u00010\u00008VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b/\u00100R\u001a\u00101\u001a\b\u0012\u0004\u0012\u0002020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b3\u0010\u000fR\u001a\u00104\u001a\b\u0012\u0004\u0012\u0002050\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b6\u0010\u000fR\u001a\u00107\u001a\b\u0012\u0004\u0012\u0002020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b8\u0010\u000fR\u001a\u00109\u001a\b\u0012\u0004\u0012\u00020;0:8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R\u0016\u0010>\u001a\u0004\u0018\u00010?8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b@\u0010AR\u0014\u0010B\u001a\u00020C8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bD\u0010E¨\u0006R"}, d2 = {"Lorg/jetbrains/kotlin/load/java/structure/impl/JavaClassImpl;", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaClassifierImpl;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "Lorg/jetbrains/kotlin/load/java/structure/impl/VirtualFileBoundJavaClass;", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaAnnotationOwnerImpl;", "Lorg/jetbrains/kotlin/load/java/structure/impl/JavaModifierListOwnerImpl;", "psiClass", "(Lcom/intellij/psi/PsiClass;)V", "psiClassSource", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;", "(Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementPsiSource;)V", "constructors", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaConstructor;", "getConstructors", "()Ljava/util/Collection;", "fields", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "getFields", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "innerClassNames", "Lorg/jetbrains/kotlin/name/Name;", "getInnerClassNames", "isAbstract", "", "()Z", "isAnnotationType", "isEnum", "isFinal", "isInterface", "isRecord", "isSealed", "isStatic", "lightClassOriginKind", "Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "getLightClassOriginKind", "()Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "methods", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", "getMethods", "name", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "outerClass", "getOuterClass", "()Lorg/jetbrains/kotlin/load/java/structure/impl/JavaClassImpl;", "permittedTypes", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "getPermittedTypes", "recordComponents", "Lorg/jetbrains/kotlin/load/java/structure/JavaRecordComponent;", "getRecordComponents", "supertypes", "getSupertypes", "typeParameters", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "getTypeParameters", "()Ljava/util/List;", "virtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "getVirtualFile", "()Lcom/intellij/openapi/vfs/VirtualFile;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "assertNotLightClass", "", "findInnerClass", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "getAnnotationOwnerPsi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "Lorg/jetbrains/annotations/Nullable;", "hasDefaultConstructor", "isFromSourceCodeInScope", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "Companion", "resolution.common.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaClassImpl extends JavaClassifierImpl<PsiClass> implements JavaAnnotationOwnerImpl, JavaModifierListOwnerImpl, VirtualFileBoundJavaClass {
    private static final Logger LOGGER;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JavaClassImpl(JavaElementPsiSource<PsiClass> psiClassSource) {
        super(psiClassSource);
        Intrinsics.checkNotNullParameter(psiClassSource, "psiClassSource");
        boolean z = psiClassSource.getPsi() instanceof PsiTypeParameter;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.JavaElementImpl, org.jetbrains.kotlin.load.java.structure.impl.JavaModifierListOwnerImpl
    public /* bridge */ /* synthetic */ PsiModifierListOwner getPsi() {
        return (PsiModifierListOwner) getPsi();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public JavaClassImpl(PsiClass psiClass) {
        Intrinsics.checkNotNullParameter(psiClass, "psiClass");
        JavaElementSourceFactory.Companion companion = JavaElementSourceFactory.INSTANCE;
        Project project = psiClass.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        this((JavaElementPsiSource<PsiClass>) companion.getInstance(project).createPsiSource(psiClass));
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public Collection<Name> getInnerClassNames() {
        PsiClass[] innerClasses = ((PsiClass) getPsi()).getInnerClasses();
        Intrinsics.checkNotNullExpressionValue(innerClasses, "getInnerClasses(...)");
        ArrayList arrayList = new ArrayList();
        for (PsiClass psiClass : innerClasses) {
            String name2 = psiClass.getName();
            Name nameIdentifier = null;
            if (name2 != null) {
                if (!Name.isValidIdentifier(name2)) {
                    name2 = null;
                }
                if (name2 != null) {
                    nameIdentifier = Name.identifier(name2);
                }
            }
            if (nameIdentifier != null) {
                arrayList.add(nameIdentifier);
            }
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public JavaClass findInnerClass(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        PsiClass psiClassFindInnerClassByName = ((PsiClass) getPsi()).findInnerClassByName(name2.asString(), false);
        return psiClassFindInnerClassByName != null ? new JavaClassImpl((JavaElementPsiSource<PsiClass>) this.psiElementSource.getFactory().createPsiSource(psiClassFindInnerClassByName)) : null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public FqName getFqName() {
        String qualifiedName = ((PsiClass) getPsi()).getQualifiedName();
        if (qualifiedName == null) {
            return null;
        }
        return new FqName(qualifiedName);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        Name nameSafeName = KtPsiUtil.safeName(((PsiClass) getPsi()).getName());
        Intrinsics.checkNotNullExpressionValue(nameSafeName, "safeName(...)");
        return nameSafeName;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isInterface */
    public boolean mo7265isInterface() {
        return ((PsiClass) getPsi()).isInterface();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isAnnotationType */
    public boolean mo7261isAnnotationType() {
        return ((PsiClass) getPsi()).isAnnotationType();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isEnum */
    public boolean mo7263isEnum() {
        return ((PsiClass) getPsi()).isEnum();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public boolean isSealed() {
        return JavaElementUtil.isSealed(this);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public Collection<JavaClassifierType> getPermittedTypes() {
        ArrayList arrayListEmptyList;
        PsiClassType[] permitsListTypes = ((PsiClass) getPsi()).getPermitsListTypes();
        Intrinsics.checkNotNullExpressionValue(permitsListTypes, "getPermitsListTypes(...)");
        PsiClassType[] psiClassTypeArr = permitsListTypes;
        int length = psiClassTypeArr.length;
        if (length != 0) {
            int i = 0;
            if (length == 1) {
                JavaElementSourceFactory sourceFactory = getSourceFactory();
                JavaElementPsiSource<Psi> psiElementSource = this.psiElementSource;
                Intrinsics.checkNotNullExpressionValue(psiElementSource, "psiElementSource");
                arrayListEmptyList = CollectionsKt.listOf(new JavaClassifierTypeImpl(sourceFactory.createPermittedTypeSource(psiElementSource, 0)));
            } else {
                ArrayList arrayList = new ArrayList(psiClassTypeArr.length);
                int length2 = psiClassTypeArr.length;
                int i2 = 0;
                while (i < length2) {
                    PsiClassType psiClassType = psiClassTypeArr[i];
                    JavaElementSourceFactory sourceFactory2 = getSourceFactory();
                    JavaElementPsiSource<Psi> psiElementSource2 = this.psiElementSource;
                    Intrinsics.checkNotNullExpressionValue(psiElementSource2, "psiElementSource");
                    arrayList.add(new JavaClassifierTypeImpl(sourceFactory2.createPermittedTypeSource(psiElementSource2, i2)));
                    i++;
                    i2++;
                }
                arrayListEmptyList = arrayList;
            }
        } else {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        return arrayListEmptyList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isRecord */
    public boolean mo7266isRecord() {
        return ((PsiClass) getPsi()).isRecord();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public JavaClassImpl getOuterClass() {
        PsiClass psiClassMo6092getContainingClass = ((PsiClass) getPsi()).mo6092getContainingClass();
        if (psiClassMo6092getContainingClass == null) {
            return null;
        }
        return new JavaClassImpl((JavaElementPsiSource<PsiClass>) this.psiElementSource.getFactory().createPsiSource(psiClassMo6092getContainingClass));
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameters */
    public List<JavaTypeParameter> mo7258getTypeParameters() {
        PsiTypeParameter[] typeParameters = ((PsiClass) getPsi()).getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        JavaElementSourceFactory sourceFactory = getSourceFactory();
        Intrinsics.checkNotNullExpressionValue(sourceFactory, "<get-sourceFactory>(...)");
        return JavaElementCollectionFromPsiArrayUtil.typeParameters(typeParameters, sourceFactory);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public Collection<JavaClassifierType> getSupertypes() {
        ArrayList arrayListEmptyList;
        PsiClassType[] superTypes = ((PsiClass) getPsi()).getSuperTypes();
        Intrinsics.checkNotNullExpressionValue(superTypes, "getSuperTypes(...)");
        PsiClassType[] psiClassTypeArr = superTypes;
        int length = psiClassTypeArr.length;
        if (length != 0) {
            int i = 0;
            if (length == 1) {
                JavaElementSourceFactory sourceFactory = getSourceFactory();
                JavaElementPsiSource<Psi> psiElementSource = this.psiElementSource;
                Intrinsics.checkNotNullExpressionValue(psiElementSource, "psiElementSource");
                arrayListEmptyList = CollectionsKt.listOf(new JavaClassifierTypeImpl(sourceFactory.createSuperTypeSource(psiElementSource, 0)));
            } else {
                ArrayList arrayList = new ArrayList(psiClassTypeArr.length);
                int length2 = psiClassTypeArr.length;
                int i2 = 0;
                while (i < length2) {
                    PsiClassType psiClassType = psiClassTypeArr[i];
                    JavaElementSourceFactory sourceFactory2 = getSourceFactory();
                    JavaElementPsiSource<Psi> psiElementSource2 = this.psiElementSource;
                    Intrinsics.checkNotNullExpressionValue(psiElementSource2, "psiElementSource");
                    arrayList.add(new JavaClassifierTypeImpl(sourceFactory2.createSuperTypeSource(psiElementSource2, i2)));
                    i++;
                    i2++;
                }
                arrayListEmptyList = arrayList;
            }
        } else {
            arrayListEmptyList = CollectionsKt.emptyList();
        }
        return arrayListEmptyList;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x003f  */
    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getMethods */
    public Collection<JavaMethod> mo7255getMethods() {
        assertNotLightClass();
        PsiMethod[] methods = ((PsiClass) getPsi()).getMethods();
        Intrinsics.checkNotNullExpressionValue(methods, "getMethods(...)");
        ArrayList arrayList = new ArrayList();
        for (PsiMethod psiMethod : methods) {
            PsiMethod psiMethod2 = psiMethod;
            if (!psiMethod2.isConstructor() && psiMethod2.getReturnType() != null) {
                if (mo7263isEnum()) {
                    Intrinsics.checkNotNull(psiMethod2);
                    if (!SyntheticElementUtilsKt.isSyntheticValuesOrValueOfMethod(psiMethod2)) {
                        arrayList.add(psiMethod);
                    }
                } else {
                    arrayList.add(psiMethod);
                }
            }
        }
        JavaElementSourceFactory sourceFactory = getSourceFactory();
        Intrinsics.checkNotNullExpressionValue(sourceFactory, "<get-sourceFactory>(...)");
        return CollectionsKt.distinct(JavaElementCollectionFromPsiArrayUtil.methods(arrayList, sourceFactory));
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getFields */
    public Collection<JavaField> mo7254getFields() {
        assertNotLightClass();
        PsiField[] fields = ((PsiClass) getPsi()).getFields();
        Intrinsics.checkNotNullExpressionValue(fields, "getFields(...)");
        ArrayList arrayList = new ArrayList();
        for (PsiField psiField : fields) {
            if (Name.isValidIdentifier(psiField.getName())) {
                arrayList.add(psiField);
            }
        }
        JavaElementSourceFactory sourceFactory = getSourceFactory();
        Intrinsics.checkNotNullExpressionValue(sourceFactory, "<get-sourceFactory>(...)");
        return JavaElementCollectionFromPsiArrayUtil.fields(arrayList, sourceFactory);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getConstructors */
    public Collection<JavaConstructor> mo7253getConstructors() {
        assertNotLightClass();
        PsiMethod[] constructors = ((PsiClass) getPsi()).getConstructors();
        Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
        ArrayList arrayList = new ArrayList();
        for (PsiMethod psiMethod : constructors) {
            if (psiMethod.isConstructor()) {
                arrayList.add(psiMethod);
            }
        }
        JavaElementSourceFactory sourceFactory = getSourceFactory();
        Intrinsics.checkNotNullExpressionValue(sourceFactory, "<get-sourceFactory>(...)");
        return JavaElementCollectionFromPsiArrayUtil.constructors(arrayList, sourceFactory);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getRecordComponents */
    public Collection<JavaRecordComponent> mo7257getRecordComponents() {
        ArrayList arrayListEmptyList;
        assertNotLightClass();
        PsiRecordComponent[] recordComponents = ((PsiClass) getPsi()).getRecordComponents();
        Intrinsics.checkNotNullExpressionValue(recordComponents, "getRecordComponents(...)");
        PsiRecordComponent[] psiRecordComponentArr = recordComponents;
        int length = psiRecordComponentArr.length;
        if (length == 0) {
            arrayListEmptyList = CollectionsKt.emptyList();
        } else if (length == 1) {
            PsiRecordComponent psiRecordComponent = (PsiRecordComponent) ArraysKt.first(psiRecordComponentArr);
            JavaElementSourceFactory factory = this.psiElementSource.getFactory();
            Intrinsics.checkNotNull(psiRecordComponent);
            arrayListEmptyList = CollectionsKt.listOf(new JavaRecordComponentImpl(factory.createPsiSource(psiRecordComponent)));
        } else {
            ArrayList arrayList = new ArrayList(psiRecordComponentArr.length);
            for (PsiRecordComponent psiRecordComponent2 : psiRecordComponentArr) {
                JavaElementSourceFactory factory2 = this.psiElementSource.getFactory();
                Intrinsics.checkNotNull(psiRecordComponent2);
                arrayList.add(new JavaRecordComponentImpl(factory2.createPsiSource(psiRecordComponent2)));
            }
            arrayListEmptyList = arrayList;
        }
        return arrayListEmptyList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: hasDefaultConstructor */
    public boolean mo7259hasDefaultConstructor() {
        return !mo7265isInterface() && mo7253getConstructors().isEmpty();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isAbstract */
    public boolean mo7260isAbstract() {
        return JavaElementUtil.isAbstract(this);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isStatic */
    public boolean mo7267isStatic() {
        return JavaElementUtil.isStatic(this);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isFinal */
    public boolean mo7264isFinal() {
        return JavaElementUtil.isFinal(this);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    public Visibility getVisibility() {
        Visibility visibility = JavaElementUtil.getVisibility(this);
        Intrinsics.checkNotNullExpressionValue(visibility, "getVisibility(...)");
        return visibility;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public LightClassOriginKind getLightClassOriginKind() {
        PsiElement psi = getPsi();
        KtLightClassMarker ktLightClassMarker = psi instanceof KtLightClassMarker ? (KtLightClassMarker) psi : null;
        if (ktLightClassMarker != null) {
            return ktLightClassMarker.getOriginKind();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.VirtualFileBoundJavaClass
    public VirtualFile getVirtualFile() {
        PsiFile containingFile = ((PsiClass) getPsi()).getContainingFile();
        if (containingFile != null) {
            return containingFile.getVirtualFile();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.VirtualFileBoundJavaClass
    public boolean isFromSourceCodeInScope(SearchScope scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        return scope.contains(((PsiClass) getPsi()).getContainingFile().getVirtualFile());
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.JavaAnnotationOwnerImpl
    public PsiModifierList getAnnotationOwnerPsi() {
        return ((PsiClass) getPsi()).mo6095getModifierList();
    }

    private final void assertNotLightClass() {
        VirtualFile virtualFile;
        PsiElement psi = getPsi();
        Intrinsics.checkNotNullExpressionValue(psi, "getPsi(...)");
        PsiClass psiClass = (PsiClass) psi;
        if (psiClass instanceof KtLightClassMarker) {
            StringBuilder sbAppend = new StringBuilder("Querying members of JavaClass created for ").append(psiClass).append(" of type ").append(psiClass.getClass()).append(" defined in file ");
            PsiFile containingFile = psiClass.getContainingFile();
            LOGGER.error(sbAppend.append((containingFile == null || (virtualFile = containingFile.getVirtualFile()) == null) ? null : virtualFile.getCanonicalPath()).toString());
        }
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) JavaClassImpl.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOGGER = logger;
    }
}
