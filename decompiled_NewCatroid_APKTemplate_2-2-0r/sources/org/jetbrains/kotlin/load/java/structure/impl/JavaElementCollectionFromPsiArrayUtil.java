package org.jetbrains.kotlin.load.java.structure.impl;

import android.Manifest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotationMemberValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiNameValuePair;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.PsiParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.load.java.JvmAnnotationNamesKt;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotationArgument;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaConstructor;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.load.java.structure.JavaPackage;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.JavaValueParameter;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementPsiSource;
import org.jetbrains.kotlin.load.java.structure.impl.source.JavaElementSourceFactory;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JavaElementCollectionFromPsiArrayUtil.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¢\u0001\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u001a+\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u000e\u0010\u0000\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0000¢\u0006\u0002\u0010\u0007\u001a)\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0000¢\u0006\u0002\u0010\u000b\u001a$\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0000\u001a)\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0000¢\u0006\u0002\u0010\u0010\u001a$\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u00012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0000\u001a$\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00012\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0000\u001a$\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00180\u00012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0000\u001a)\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00012\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0000¢\u0006\u0002\u0010\u001d\u001a+\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u000e\u0010\u0000\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0000¢\u0006\u0002\u0010\u0007\u001a1\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u00012\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0\u00032\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u0005\u001a\u00020\u0006H\u0000¢\u0006\u0002\u0010$\u001a)\u0010%\u001a\b\u0012\u0004\u0012\u00020'0&2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020(0\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0000¢\u0006\u0002\u0010)\u001a)\u0010*\u001a\b\u0012\u0004\u0012\u00020+0&2\f\u0010,\u001a\b\u0012\u0004\u0012\u00020-0\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u0000¢\u0006\u0002\u0010.\u001aA\u0010/\u001a\b\u0012\u0004\u0012\u0002H00&\"\u0004\b\u0000\u00101\"\u0004\b\u0001\u00100*\b\u0012\u0004\u0012\u0002H10\u00032\u0012\u00102\u001a\u000e\u0012\u0004\u0012\u0002H1\u0012\u0004\u0012\u0002H003H\u0086\bø\u0001\u0000¢\u0006\u0002\u00104\u001a6\u0010/\u001a\b\u0012\u0004\u0012\u0002H00&\"\u0004\b\u0000\u00101\"\u0004\b\u0001\u00100*\b\u0012\u0004\u0012\u0002H10\u00012\u0012\u00102\u001a\u000e\u0012\u0004\u0012\u0002H1\u0012\u0004\u0012\u0002H003\u001aG\u00105\u001a\b\u0012\u0004\u0012\u0002H00&\"\u0004\b\u0000\u00101\"\u0004\b\u0001\u00100*\b\u0012\u0004\u0012\u0002H10\u00032\u0018\u00102\u001a\u0014\u0012\u0004\u0012\u000207\u0012\u0004\u0012\u0002H1\u0012\u0004\u0012\u0002H006H\u0086\bø\u0001\u0000¢\u0006\u0002\u00108\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u00069"}, d2 = {"annotations", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotation;", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotation;", "sourceFactory", "Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;", "([Lcom/intellij/psi/PsiAnnotation;Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;)Ljava/util/Collection;", "classes", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "([Lcom/intellij/psi/PsiClass;Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;)Ljava/util/Collection;", "classifierTypes", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "classTypes", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassType;", "([Lcom/intellij/psi/PsiClassType;Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;)Ljava/util/Collection;", "constructors", "Lorg/jetbrains/kotlin/load/java/structure/JavaConstructor;", "methods", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "fields", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiField;", "Lorg/jetbrains/kotlin/load/java/structure/JavaMethod;", "namedAnnotationArguments", "Lorg/jetbrains/kotlin/load/java/structure/JavaAnnotationArgument;", "nameValuePairs", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiNameValuePair;", "([Lcom/intellij/psi/PsiNameValuePair;Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;)Ljava/util/Collection;", "nullabilityAnnotations", "packages", "Lorg/jetbrains/kotlin/load/java/structure/JavaPackage;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiPackage;", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "([Lcom/intellij/psi/PsiPackage;Lcom/intellij/psi/search/GlobalSearchScope;Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;)Ljava/util/Collection;", "typeParameters", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameter;", "([Lcom/intellij/psi/PsiTypeParameter;Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;)Ljava/util/List;", "valueParameters", "Lorg/jetbrains/kotlin/load/java/structure/JavaValueParameter;", "parameters", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiParameter;", "([Lcom/intellij/psi/PsiParameter;Lorg/jetbrains/kotlin/load/java/structure/impl/source/JavaElementSourceFactory;)Ljava/util/List;", "convert", "Java", "Psi", "factory", "Lkotlin/Function1;", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/util/List;", "convertIndexed", "Lkotlin/Function2;", "", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Ljava/util/List;", "resolution.common.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class JavaElementCollectionFromPsiArrayUtil {
    /* JADX WARN: Multi-variable type inference failed */
    public static final <Psi, Java> List<Java> convert(Psi[] psiArr, Function1<? super Psi, ? extends Java> factory) {
        Intrinsics.checkNotNullParameter(psiArr, "<this>");
        Intrinsics.checkNotNullParameter(factory, "factory");
        int length = psiArr.length;
        if (length == 0) {
            return CollectionsKt.emptyList();
        }
        if (length == 1) {
            return CollectionsKt.listOf(factory.invoke((Object) ArraysKt.first(psiArr)));
        }
        ArrayList arrayList = new ArrayList(psiArr.length);
        for (Manifest manifest : psiArr) {
            arrayList.add(factory.invoke(manifest));
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <Psi, Java> List<Java> convertIndexed(Psi[] psiArr, Function2<? super Integer, ? super Psi, ? extends Java> factory) {
        Intrinsics.checkNotNullParameter(psiArr, "<this>");
        Intrinsics.checkNotNullParameter(factory, "factory");
        int length = psiArr.length;
        if (length == 0) {
            return CollectionsKt.emptyList();
        }
        int i = 0;
        if (length == 1) {
            return CollectionsKt.listOf(factory.invoke(0, (Object) ArraysKt.first(psiArr)));
        }
        ArrayList arrayList = new ArrayList(psiArr.length);
        int length2 = psiArr.length;
        int i2 = 0;
        while (i < length2) {
            arrayList.add(factory.invoke(Integer.valueOf(i2), psiArr[i]));
            i++;
            i2++;
        }
        return arrayList;
    }

    public static final <Psi, Java> List<Java> convert(Collection<? extends Psi> collection, Function1<? super Psi, ? extends Java> factory) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Intrinsics.checkNotNullParameter(factory, "factory");
        int size = collection.size();
        if (size == 0) {
            return CollectionsKt.emptyList();
        }
        if (size == 1) {
            return CollectionsKt.listOf(factory.invoke((Object) CollectionsKt.first(collection)));
        }
        Collection<? extends Psi> collection2 = collection;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
        Iterator<T> it = collection2.iterator();
        while (it.hasNext()) {
            arrayList.add(factory.invoke((Object) it.next()));
        }
        return arrayList;
    }

    public static final Collection<JavaClass> classes(Collection<? extends PsiClass> classes, final JavaElementSourceFactory sourceFactory) {
        Intrinsics.checkNotNullParameter(classes, "classes");
        Intrinsics.checkNotNullParameter(sourceFactory, "sourceFactory");
        return convert(classes, new Function1<PsiClass, JavaClassImpl>() { // from class: org.jetbrains.kotlin.load.java.structure.impl.JavaElementCollectionFromPsiArrayUtil.classes.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final JavaClassImpl invoke(PsiClass it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new JavaClassImpl((JavaElementPsiSource<PsiClass>) sourceFactory.createPsiSource(it));
            }
        });
    }

    public static final Collection<JavaMethod> methods(Collection<? extends PsiMethod> methods, final JavaElementSourceFactory sourceFactory) {
        Intrinsics.checkNotNullParameter(methods, "methods");
        Intrinsics.checkNotNullParameter(sourceFactory, "sourceFactory");
        return convert(methods, new Function1<PsiMethod, JavaMethodImpl>() { // from class: org.jetbrains.kotlin.load.java.structure.impl.JavaElementCollectionFromPsiArrayUtil.methods.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final JavaMethodImpl invoke(PsiMethod it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new JavaMethodImpl((JavaElementPsiSource<PsiMethod>) sourceFactory.createPsiSource(it));
            }
        });
    }

    public static final Collection<JavaConstructor> constructors(Collection<? extends PsiMethod> methods, final JavaElementSourceFactory sourceFactory) {
        Intrinsics.checkNotNullParameter(methods, "methods");
        Intrinsics.checkNotNullParameter(sourceFactory, "sourceFactory");
        return convert(methods, new Function1<PsiMethod, JavaConstructorImpl>() { // from class: org.jetbrains.kotlin.load.java.structure.impl.JavaElementCollectionFromPsiArrayUtil.constructors.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final JavaConstructorImpl invoke(PsiMethod it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new JavaConstructorImpl((JavaElementPsiSource<PsiMethod>) sourceFactory.createPsiSource(it));
            }
        });
    }

    public static final Collection<JavaField> fields(Collection<? extends PsiField> fields, final JavaElementSourceFactory sourceFactory) {
        Intrinsics.checkNotNullParameter(fields, "fields");
        Intrinsics.checkNotNullParameter(sourceFactory, "sourceFactory");
        return convert(fields, new Function1<PsiField, JavaFieldImpl>() { // from class: org.jetbrains.kotlin.load.java.structure.impl.JavaElementCollectionFromPsiArrayUtil.fields.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final JavaFieldImpl invoke(PsiField it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return new JavaFieldImpl((JavaElementPsiSource<PsiField>) sourceFactory.createPsiSource(it));
            }
        });
    }

    public static final Collection<JavaClass> classes(PsiClass[] classes, JavaElementSourceFactory sourceFactory) {
        ArrayList arrayListEmptyList;
        Intrinsics.checkNotNullParameter(classes, "classes");
        Intrinsics.checkNotNullParameter(sourceFactory, "sourceFactory");
        int length = classes.length;
        if (length == 0) {
            arrayListEmptyList = CollectionsKt.emptyList();
        } else if (length == 1) {
            arrayListEmptyList = CollectionsKt.listOf(new JavaClassImpl((JavaElementPsiSource<PsiClass>) sourceFactory.createPsiSource((PsiClass) ArraysKt.first(classes))));
        } else {
            ArrayList arrayList = new ArrayList(classes.length);
            for (PsiClass psiClass : classes) {
                arrayList.add(new JavaClassImpl((JavaElementPsiSource<PsiClass>) sourceFactory.createPsiSource(psiClass)));
            }
            arrayListEmptyList = arrayList;
        }
        return arrayListEmptyList;
    }

    public static final Collection<JavaPackage> packages(PsiPackage[] packages, GlobalSearchScope scope, JavaElementSourceFactory sourceFactory) {
        ArrayList arrayListEmptyList;
        Intrinsics.checkNotNullParameter(packages, "packages");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(sourceFactory, "sourceFactory");
        int length = packages.length;
        if (length == 0) {
            arrayListEmptyList = CollectionsKt.emptyList();
        } else if (length == 1) {
            arrayListEmptyList = CollectionsKt.listOf(new JavaPackageImpl(sourceFactory.createPsiSource((PsiPackage) ArraysKt.first(packages)), scope, false, 4, null));
        } else {
            ArrayList arrayList = new ArrayList(packages.length);
            for (PsiPackage psiPackage : packages) {
                arrayList.add(new JavaPackageImpl(sourceFactory.createPsiSource(psiPackage), scope, false, 4, null));
            }
            arrayListEmptyList = arrayList;
        }
        return arrayListEmptyList;
    }

    public static final List<JavaValueParameter> valueParameters(PsiParameter[] parameters, JavaElementSourceFactory sourceFactory) {
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(sourceFactory, "sourceFactory");
        int length = parameters.length;
        if (length == 0) {
            return CollectionsKt.emptyList();
        }
        if (length == 1) {
            return CollectionsKt.listOf(new JavaValueParameterImpl(sourceFactory.createPsiSource((PsiParameter) ArraysKt.first(parameters))));
        }
        ArrayList arrayList = new ArrayList(parameters.length);
        for (PsiParameter psiParameter : parameters) {
            arrayList.add(new JavaValueParameterImpl(sourceFactory.createPsiSource(psiParameter)));
        }
        return arrayList;
    }

    public static final List<JavaTypeParameter> typeParameters(PsiTypeParameter[] typeParameters, JavaElementSourceFactory sourceFactory) {
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Intrinsics.checkNotNullParameter(sourceFactory, "sourceFactory");
        int length = typeParameters.length;
        if (length == 0) {
            return CollectionsKt.emptyList();
        }
        if (length == 1) {
            return CollectionsKt.listOf(new JavaTypeParameterImpl((JavaElementPsiSource<PsiTypeParameter>) sourceFactory.createPsiSource((PsiTypeParameter) ArraysKt.first(typeParameters))));
        }
        ArrayList arrayList = new ArrayList(typeParameters.length);
        for (PsiTypeParameter psiTypeParameter : typeParameters) {
            arrayList.add(new JavaTypeParameterImpl((JavaElementPsiSource<PsiTypeParameter>) sourceFactory.createPsiSource(psiTypeParameter)));
        }
        return arrayList;
    }

    public static final Collection<JavaClassifierType> classifierTypes(PsiClassType[] classTypes, JavaElementSourceFactory sourceFactory) {
        ArrayList arrayListEmptyList;
        Intrinsics.checkNotNullParameter(classTypes, "classTypes");
        Intrinsics.checkNotNullParameter(sourceFactory, "sourceFactory");
        int length = classTypes.length;
        if (length == 0) {
            arrayListEmptyList = CollectionsKt.emptyList();
        } else if (length == 1) {
            arrayListEmptyList = CollectionsKt.listOf(new JavaClassifierTypeImpl(sourceFactory.createTypeSource((PsiClassType) ArraysKt.first(classTypes))));
        } else {
            ArrayList arrayList = new ArrayList(classTypes.length);
            for (PsiClassType psiClassType : classTypes) {
                arrayList.add(new JavaClassifierTypeImpl(sourceFactory.createTypeSource(psiClassType)));
            }
            arrayListEmptyList = arrayList;
        }
        return arrayListEmptyList;
    }

    public static final Collection<JavaAnnotation> annotations(PsiAnnotation[] annotations, JavaElementSourceFactory sourceFactory) {
        ArrayList arrayListEmptyList;
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(sourceFactory, "sourceFactory");
        int length = annotations.length;
        if (length == 0) {
            arrayListEmptyList = CollectionsKt.emptyList();
        } else if (length == 1) {
            arrayListEmptyList = CollectionsKt.listOf(new JavaAnnotationImpl(sourceFactory.createPsiSource((PsiAnnotation) ArraysKt.first(annotations))));
        } else {
            ArrayList arrayList = new ArrayList(annotations.length);
            for (PsiAnnotation psiAnnotation : annotations) {
                arrayList.add(new JavaAnnotationImpl(sourceFactory.createPsiSource(psiAnnotation)));
            }
            arrayListEmptyList = arrayList;
        }
        return arrayListEmptyList;
    }

    public static final Collection<JavaAnnotation> nullabilityAnnotations(PsiAnnotation[] annotations, JavaElementSourceFactory sourceFactory) {
        ArrayList arrayListEmptyList;
        boolean zContains;
        FqName fqNameAsSingleFqName;
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(sourceFactory, "sourceFactory");
        int length = annotations.length;
        if (length == 0) {
            arrayListEmptyList = CollectionsKt.emptyList();
        } else if (length == 1) {
            arrayListEmptyList = CollectionsKt.listOf(new JavaAnnotationImpl(sourceFactory.createPsiSource((PsiAnnotation) ArraysKt.first(annotations))));
        } else {
            ArrayList arrayList = new ArrayList(annotations.length);
            for (PsiAnnotation psiAnnotation : annotations) {
                arrayList.add(new JavaAnnotationImpl(sourceFactory.createPsiSource(psiAnnotation)));
            }
            arrayListEmptyList = arrayList;
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayListEmptyList) {
            ClassId classId = ((JavaAnnotationImpl) obj).getClassId();
            if (classId == null || (fqNameAsSingleFqName = classId.asSingleFqName()) == null) {
                zContains = false;
            } else {
                Intrinsics.checkNotNull(fqNameAsSingleFqName);
                zContains = JvmAnnotationNamesKt.getNULLABILITY_ANNOTATIONS().contains(fqNameAsSingleFqName);
            }
            if (zContains) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    public static final Collection<JavaAnnotationArgument> namedAnnotationArguments(PsiNameValuePair[] nameValuePairs, JavaElementSourceFactory sourceFactory) {
        ArrayList arrayListEmptyList;
        Intrinsics.checkNotNullParameter(nameValuePairs, "nameValuePairs");
        Intrinsics.checkNotNullParameter(sourceFactory, "sourceFactory");
        int length = nameValuePairs.length;
        if (length == 0) {
            arrayListEmptyList = CollectionsKt.emptyList();
        } else if (length != 1) {
            ArrayList arrayList = new ArrayList(nameValuePairs.length);
            for (PsiNameValuePair psiNameValuePair : nameValuePairs) {
                String name2 = psiNameValuePair.getName();
                Name nameIdentifier = name2 != null ? Name.identifier(name2) : null;
                PsiAnnotationMemberValue value = psiNameValuePair.getValue();
                if (value == null) {
                    throw new IllegalStateException(("Annotation argument value cannot be null: " + nameIdentifier).toString());
                }
                Intrinsics.checkNotNull(value);
                arrayList.add(JavaAnnotationArgumentImpl.INSTANCE.create(value, nameIdentifier, sourceFactory));
            }
            arrayListEmptyList = arrayList;
        } else {
            PsiNameValuePair psiNameValuePair2 = (PsiNameValuePair) ArraysKt.first(nameValuePairs);
            String name3 = psiNameValuePair2.getName();
            Name nameIdentifier2 = name3 != null ? Name.identifier(name3) : null;
            PsiAnnotationMemberValue value2 = psiNameValuePair2.getValue();
            if (value2 == null) {
                throw new IllegalStateException(("Annotation argument value cannot be null: " + nameIdentifier2).toString());
            }
            Intrinsics.checkNotNull(value2);
            arrayListEmptyList = CollectionsKt.listOf(JavaAnnotationArgumentImpl.INSTANCE.create(value2, nameIdentifier2, sourceFactory));
        }
        return arrayListEmptyList;
    }
}
