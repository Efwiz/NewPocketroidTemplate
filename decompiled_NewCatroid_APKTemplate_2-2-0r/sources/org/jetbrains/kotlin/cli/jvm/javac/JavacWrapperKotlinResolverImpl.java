package org.jetbrains.kotlin.cli.jvm.javac;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.asJava.LightClassGenerationSupport;
import org.jetbrains.kotlin.asJava.LightClassUtilsKt;
import org.jetbrains.kotlin.asJava.classes.KtLightClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.javac.JavacWrapperKotlinResolver;
import org.jetbrains.kotlin.javac.resolve.MockKotlinField;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: JavacWrapperKotlinResolverImpl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u001c\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\r\u001a\u00020\u0007H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R6\u0010\u0005\u001a*\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006j\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b`\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/javac/JavacWrapperKotlinResolverImpl;", "Lorg/jetbrains/kotlin/javac/JavacWrapperKotlinResolver;", "lightClassGenerationSupport", "Lorg/jetbrains/kotlin/asJava/LightClassGenerationSupport;", "(Lorg/jetbrains/kotlin/asJava/LightClassGenerationSupport;)V", "supersCache", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "", "Lorg/jetbrains/kotlin/name/ClassId;", "Lkotlin/collections/HashMap;", "findField", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "classOrObject", "name", "", "ktFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "resolveSupertypes", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavacWrapperKotlinResolverImpl implements JavacWrapperKotlinResolver {
    private final LightClassGenerationSupport lightClassGenerationSupport;
    private final HashMap<KtClassOrObject, List<ClassId>> supersCache;

    public JavacWrapperKotlinResolverImpl(LightClassGenerationSupport lightClassGenerationSupport) {
        Intrinsics.checkNotNullParameter(lightClassGenerationSupport, "lightClassGenerationSupport");
        this.lightClassGenerationSupport = lightClassGenerationSupport;
        this.supersCache = new HashMap<>();
    }

    @Override // org.jetbrains.kotlin.javac.JavacWrapperKotlinResolver
    public List<ClassId> resolveSupertypes(KtClassOrObject classOrObject) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        List<ClassId> list = this.supersCache.get(classOrObject);
        if (list != null) {
            return list;
        }
        ClassDescriptor classDescriptor = (ClassDescriptor) this.lightClassGenerationSupport.analyze(classOrObject).get(BindingContext.CLASS, classOrObject);
        if (classDescriptor == null) {
            return CollectionsKt.emptyList();
        }
        Collection<KotlinType> collectionMo7438getSupertypes = classDescriptor.getDefaultType().getConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = collectionMo7438getSupertypes.iterator();
        while (it.hasNext()) {
            ClassifierDescriptor this$0 = ((KotlinType) it.next()).getConstructor().getThis$0();
            ClassDescriptor classDescriptor2 = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
            ClassId classId = classDescriptor2 != null ? DescriptorUtilsKt.getClassId((ClassifierDescriptor) classDescriptor2) : null;
            if (classId != null) {
                arrayList.add(classId);
            }
        }
        ArrayList arrayList2 = arrayList;
        this.supersCache.put(classOrObject, arrayList2);
        return arrayList2;
    }

    @Override // org.jetbrains.kotlin.javac.JavacWrapperKotlinResolver
    public JavaField findField(KtClassOrObject classOrObject, String name2) {
        PsiField psiField;
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        Intrinsics.checkNotNullParameter(name2, "name");
        KtLightClass lightClass = LightClassUtilsKt.toLightClass(classOrObject);
        if (lightClass == null) {
            return null;
        }
        PsiField[] allFields = lightClass.getAllFields();
        Intrinsics.checkNotNullExpressionValue(allFields, "getAllFields(...)");
        PsiField[] psiFieldArr = allFields;
        int length = psiFieldArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                psiField = null;
                break;
            }
            psiField = psiFieldArr[i];
            if (Intrinsics.areEqual(psiField.getMyName(), name2)) {
                break;
            }
            i++;
        }
        PsiField psiField2 = psiField;
        return psiField2 != null ? new MockKotlinField(psiField2) : null;
    }

    @Override // org.jetbrains.kotlin.javac.JavacWrapperKotlinResolver
    public JavaField findField(KtFile ktFile, String name2) {
        KtLightClass ktLightClassFindFacadeClass;
        PsiField psiField;
        Intrinsics.checkNotNullParameter(name2, "name");
        if (ktFile == null || (ktLightClassFindFacadeClass = LightClassUtilsKt.findFacadeClass(ktFile)) == null) {
            return null;
        }
        PsiField[] allFields = ktLightClassFindFacadeClass.getAllFields();
        Intrinsics.checkNotNullExpressionValue(allFields, "getAllFields(...)");
        PsiField[] psiFieldArr = allFields;
        int length = psiFieldArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                psiField = null;
                break;
            }
            psiField = psiFieldArr[i];
            if (Intrinsics.areEqual(psiField.getMyName(), name2)) {
                break;
            }
            i++;
        }
        PsiField psiField2 = psiField;
        return psiField2 != null ? new MockKotlinField(psiField2) : null;
    }
}
