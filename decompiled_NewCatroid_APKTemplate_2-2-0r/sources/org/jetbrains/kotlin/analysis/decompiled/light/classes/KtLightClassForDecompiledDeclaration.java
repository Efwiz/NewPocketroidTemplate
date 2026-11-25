package org.jetbrains.kotlin.analysis.decompiled.light.classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.analysis.decompiled.light.classes.origin.LightMemberOriginForCompiledField;
import org.jetbrains.kotlin.analysis.decompiled.light.classes.origin.LightMemberOriginForCompiledMethod;
import org.jetbrains.kotlin.analysis.decompiler.psi.file.KtClsFile;
import org.jetbrains.kotlin.analysis.providers.KotlinModificationTrackerFactoryKt;
import org.jetbrains.kotlin.asJava.SyntheticElementUtilsKt;
import org.jetbrains.kotlin.asJava.classes.ClassInnerStuffCache;
import org.jetbrains.kotlin.asJava.classes.ImplUtilsKt;
import org.jetbrains.kotlin.asJava.classes.KotlinClassInnerStuffCacheKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.HierarchicalMethodSignature;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassInitializer;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassType;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiEnumConstant;
import org.jetbrains.kotlin.com.intellij.psi.PsiField;
import org.jetbrains.kotlin.com.intellij.psi.PsiIdentifier;
import org.jetbrains.kotlin.com.intellij.psi.PsiMember;
import org.jetbrains.kotlin.com.intellij.psi.PsiMethod;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiReferenceList;
import org.jetbrains.kotlin.com.intellij.psi.PsiSubstitutor;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameter;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterList;
import org.jetbrains.kotlin.com.intellij.psi.ResolveState;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiClassImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiSuperMethodImplUtil;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider;
import org.jetbrains.kotlin.com.intellij.psi.util.CachedValuesManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.structure.LightClassOriginKind;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;

/* JADX INFO: compiled from: KtLightClassForDecompiledDeclaration.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000Ì\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0005H\u0016J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0018H\u0096\u0002J\u001a\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u001bH\u0016J\u001a\u0010\"\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u001bH\u0016J\u001c\u0010#\u001a\u0004\u0018\u00010$2\b\u0010%\u001a\u0004\u0018\u00010$2\u0006\u0010!\u001a\u00020\u001bH\u0016J.\u0010&\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020)0(0'2\n\b\u0001\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\u001bH\u0016J#\u0010*\u001a\b\u0012\u0004\u0012\u00020$0+2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u001bH\u0016¢\u0006\u0002\u0010,J%\u0010-\u001a\b\u0012\u0004\u0012\u00020$0+2\b\u0010%\u001a\u0004\u0018\u00010$2\u0006\u0010!\u001a\u00020\u001bH\u0016¢\u0006\u0002\u0010.J\u0013\u0010/\u001a\b\u0012\u0004\u0012\u00020\u001e0+H\u0016¢\u0006\u0002\u00100J\u0013\u00101\u001a\b\u0012\u0004\u0012\u00020\u00030+H\u0016¢\u0006\u0002\u00102J\u0013\u00103\u001a\b\u0012\u0004\u0012\u00020$0+H\u0016¢\u0006\u0002\u00104J \u00105\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0006\u0012\u0004\u0018\u00010$\u0012\u0006\u0012\u0004\u0018\u00010)\u0018\u00010(0'H\u0016J\u0013\u00106\u001a\b\u0012\u0004\u0012\u00020$0+H\u0016¢\u0006\u0002\u00104J\n\u00107\u001a\u0004\u0018\u00010\u0003H\u0016J\n\u00108\u001a\u0004\u0018\u000109H\u0016J\n\u0010:\u001a\u0004\u0018\u00010;H\u0016J\u0013\u0010<\u001a\b\u0012\u0004\u0012\u00020=0+H\u0016¢\u0006\u0002\u0010>J\u0013\u0010?\u001a\b\u0012\u0004\u0012\u00020\u001e0+H\u0016¢\u0006\u0002\u00100J\n\u0010@\u001a\u0004\u0018\u00010;H\u0016J\u0013\u0010A\u001a\b\u0012\u0004\u0012\u00020=0+H\u0016¢\u0006\u0002\u0010>J\u0013\u0010B\u001a\b\u0012\u0004\u0012\u00020C0+H\u0016¢\u0006\u0002\u0010DJ\u0013\u0010E\u001a\b\u0012\u0004\u0012\u00020\u00030+H\u0016¢\u0006\u0002\u00102J\u0013\u0010F\u001a\b\u0012\u0004\u0012\u00020\u00030+H\u0016¢\u0006\u0002\u00102J\n\u0010G\u001a\u0004\u0018\u00010\u0005H\u0016J\u0013\u0010H\u001a\b\u0012\u0004\u0012\u00020$0+H\u0016¢\u0006\u0002\u00104J\n\u0010I\u001a\u0004\u0018\u00010JH\u0016J\n\u0010K\u001a\u0004\u0018\u00010 H\u0016J\n\u0010L\u001a\u0004\u0018\u00010MH\u0016J\b\u0010N\u001a\u00020\u0005H\u0016J\u000e\u0010O\u001a\b\u0012\u0004\u0012\u00020\u001e0'H\u0016J\u000e\u0010P\u001a\b\u0012\u0004\u0012\u00020\u00030'H\u0016J\u000e\u0010Q\u001a\b\u0012\u0004\u0012\u00020$0'H\u0016J\n\u0010R\u001a\u0004\u0018\u00010 H\u0016J\n\u0010S\u001a\u0004\u0018\u00010\u0005H\u0016J\b\u0010T\u001a\u00020\u0005H\u0016J\n\u0010U\u001a\u0004\u0018\u00010\u0003H\u0016J\u0013\u0010V\u001a\b\u0012\u0004\u0012\u00020=0+H\u0016¢\u0006\u0002\u0010>J\u0013\u0010W\u001a\b\u0012\u0004\u0012\u00020\u00030+H\u0016¢\u0006\u0002\u00102J\n\u0010X\u001a\u0004\u0018\u00010YH\u0016J\u0013\u0010Z\u001a\b\u0012\u0004\u0012\u00020[0+H\u0016¢\u0006\u0002\u0010\\J\u000e\u0010]\u001a\b\u0012\u0004\u0012\u00020_0^H\u0016J\u0010\u0010`\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020 H\u0016J\b\u0010a\u001a\u00020\u001bH\u0016J\b\u0010b\u001a\u00020cH\u0016J\b\u0010d\u001a\u00020\u001bH\u0016J\b\u0010e\u001a\u00020\u001bH\u0016J\b\u0010f\u001a\u00020\u001bH\u0016J\u0018\u0010g\u001a\u00020\u001b2\u0006\u0010h\u001a\u00020\u00032\u0006\u0010i\u001a\u00020\u001bH\u0016J\u001c\u0010j\u001a\u00020\u001b2\b\u0010h\u001a\u0004\u0018\u00010\u00032\b\u0010k\u001a\u0004\u0018\u00010\u0003H\u0016J\b\u0010l\u001a\u00020\u001bH\u0016J\b\u0010m\u001a\u00020\u001bH\u0016J*\u0010n\u001a\u00020\u001b2\u0006\u0010o\u001a\u00020p2\u0006\u0010q\u001a\u00020r2\b\u0010s\u001a\u0004\u0018\u00010\u00052\u0006\u0010t\u001a\u00020\u0005H\u0016J\u0010\u0010u\u001a\u00020\u00052\u0006\u0010\u001f\u001a\u00020 H\u0016J\b\u0010v\u001a\u00020 H\u0016R\u0014\u0010\u0006\u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0013\u001a\u00020\u0014X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006w"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/KtLightClassForDecompiledDeclaration;", "Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/KtLightClassForDecompiledDeclarationBase;", "clsDelegate", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "clsParent", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "file", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtClsFile;", "kotlinOrigin", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "(Lcom/intellij/psi/PsiClass;Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtClsFile;Lorg/jetbrains/kotlin/psi/KtClassOrObject;)V", "getFile", "()Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtClsFile;", "myInnersCache", "Lorg/jetbrains/kotlin/asJava/classes/ClassInnerStuffCache;", "getMyInnersCache", "()Lorg/jetbrains/kotlin/asJava/classes/ClassInnerStuffCache;", "myInnersCache$delegate", "Lkotlin/Lazy;", "originKind", "Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "getOriginKind", "()Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "clone", "", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "findFieldByName", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiField;", "name", "", "checkBases", "findInnerClassByName", "findMethodBySignature", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiMethod;", "patternMethod", "findMethodsAndTheirSubstitutorsByName", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/Pair;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiSubstitutor;", "findMethodsByName", "", "(Ljava/lang/String;Z)[Lcom/intellij/psi/PsiMethod;", "findMethodsBySignature", "(Lcom/intellij/psi/PsiMethod;Z)[Lcom/intellij/psi/PsiMethod;", "getAllFields", "()[Lcom/intellij/psi/PsiField;", "getAllInnerClasses", "()[Lcom/intellij/psi/PsiClass;", "getAllMethods", "()[Lcom/intellij/psi/PsiMethod;", "getAllMethodsAndTheirSubstitutors", "getConstructors", "getContainingClass", "getDocComment", "Lorg/jetbrains/kotlin/com/intellij/psi/javadoc/PsiDocComment;", "getExtendsList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiReferenceList;", "getExtendsListTypes", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassType;", "()[Lcom/intellij/psi/PsiClassType;", "getFields", "getImplementsList", "getImplementsListTypes", "getInitializers", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassInitializer;", "()[Lcom/intellij/psi/PsiClassInitializer;", "getInnerClasses", "getInterfaces", "getLBrace", "getMethods", "getModifierList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "getName", "getNameIdentifier", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiIdentifier;", "getNavigationElement", "getOwnFields", "getOwnInnerClasses", "getOwnMethods", "getQualifiedName", "getRBrace", "getScope", "getSuperClass", "getSuperTypes", "getSupers", "getTypeParameterList", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameterList;", "getTypeParameters", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiTypeParameter;", "()[Lcom/intellij/psi/PsiTypeParameter;", "getVisibleSignatures", "", "Lorg/jetbrains/kotlin/com/intellij/psi/HierarchicalMethodSignature;", "hasModifierProperty", "hasTypeParameters", "hashCode", "", "isAnnotationType", "isDeprecated", "isEnum", "isInheritor", "baseClass", "checkDeep", "isInheritorDeep", "classToByPass", "isInterface", "isValid", "processDeclarations", "processor", "Lorg/jetbrains/kotlin/com/intellij/psi/scope/PsiScopeProcessor;", "state", "Lorg/jetbrains/kotlin/com/intellij/psi/ResolveState;", "lastParent", "place", "setName", "toString", "light-classes-for-decompiled"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtLightClassForDecompiledDeclaration extends KtLightClassForDecompiledDeclarationBase {
    private final KtClsFile file;

    /* JADX INFO: renamed from: myInnersCache$delegate, reason: from kotlin metadata */
    private final Lazy myInnersCache;
    private final LightClassOriginKind originKind;

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.UserDataHolderBase
    public Object clone() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiElement getLBrace() {
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiElement getRBrace() {
        return null;
    }

    protected final KtClsFile getFile() {
        return this.file;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtLightClassForDecompiledDeclaration(PsiClass clsDelegate, PsiElement clsParent, KtClsFile file, KtClassOrObject ktClassOrObject) {
        super(clsDelegate, clsParent, ktClassOrObject);
        Intrinsics.checkNotNullParameter(clsDelegate, "clsDelegate");
        Intrinsics.checkNotNullParameter(clsParent, "clsParent");
        Intrinsics.checkNotNullParameter(file, "file");
        this.file = file;
        this.myInnersCache = ImplUtilsKt.lazyPub(new Function0<ClassInnerStuffCache>() { // from class: org.jetbrains.kotlin.analysis.decompiled.light.classes.KtLightClassForDecompiledDeclaration$myInnersCache$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassInnerStuffCache invoke() {
                KtLightClassForDecompiledDeclaration ktLightClassForDecompiledDeclaration = this.this$0;
                KtLightClassForDecompiledDeclaration ktLightClassForDecompiledDeclaration2 = ktLightClassForDecompiledDeclaration;
                Project project = ktLightClassForDecompiledDeclaration.getProject();
                Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                return new ClassInnerStuffCache(ktLightClassForDecompiledDeclaration2, true, KotlinModificationTrackerFactoryKt.createAllLibrariesModificationTracker(project));
            }
        });
        this.originKind = LightClassOriginKind.BINARY;
    }

    private final ClassInnerStuffCache getMyInnersCache() {
        return (ClassInnerStuffCache) this.myInnersCache.getValue();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField[] getFields() {
        PsiField[] fields = getMyInnersCache().getFields();
        Intrinsics.checkNotNullExpressionValue(fields, "getFields(...)");
        return fields;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getMethods() {
        PsiMethod[] methods = getMyInnersCache().getMethods();
        Intrinsics.checkNotNullExpressionValue(methods, "getMethods(...)");
        return methods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getConstructors() {
        PsiMethod[] constructors = getMyInnersCache().getConstructors();
        Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
        return constructors;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getInnerClasses() {
        PsiClass[] innerClasses = getMyInnersCache().getInnerClasses();
        Intrinsics.checkNotNullExpressionValue(innerClasses, "getInnerClasses(...)");
        return innerClasses;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField findFieldByName(String name2, boolean checkBases) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return getMyInnersCache().findFieldByName(name2, checkBases);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] findMethodsByName(String name2, boolean z) {
        Intrinsics.checkNotNullParameter(name2, "name");
        PsiMethod[] psiMethodArrFindMethodsByName = getMyInnersCache().findMethodsByName(name2, z);
        Intrinsics.checkNotNullExpressionValue(psiMethodArrFindMethodsByName, "findMethodsByName(...)");
        return psiMethodArrFindMethodsByName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass findInnerClassByName(String name2, boolean checkBases) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return getMyInnersCache().findInnerClassByName(name2, checkBases);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    public boolean hasModifierProperty(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return getClsDelegate().hasModifierProperty(name2);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod findMethodBySignature(PsiMethod patternMethod, boolean checkBases) {
        if (patternMethod != null) {
            return PsiClassImplUtil.findMethodBySignature(this, patternMethod, checkBases);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] findMethodsBySignature(PsiMethod psiMethod, boolean z) {
        PsiMethod[] psiMethodArrFindMethodsBySignature = psiMethod != null ? PsiClassImplUtil.findMethodsBySignature(this, psiMethod, z) : null;
        if (psiMethodArrFindMethodsBySignature != null) {
            return psiMethodArrFindMethodsBySignature;
        }
        PsiMethod[] EMPTY_ARRAY = PsiMethod.EMPTY_ARRAY;
        Intrinsics.checkNotNullExpressionValue(EMPTY_ARRAY, "EMPTY_ARRAY");
        return EMPTY_ARRAY;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public List<Pair<PsiMethod, PsiSubstitutor>> findMethodsAndTheirSubstitutorsByName(String name2, boolean checkBases) {
        List<Pair<PsiMethod, PsiSubstitutor>> listFindMethodsAndTheirSubstitutorsByName = PsiClassImplUtil.findMethodsAndTheirSubstitutorsByName(this, name2, checkBases);
        Intrinsics.checkNotNullExpressionValue(listFindMethodsAndTheirSubstitutorsByName, "findMethodsAndTheirSubstitutorsByName(...)");
        return listFindMethodsAndTheirSubstitutorsByName;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getImplementsList() {
        return getClsDelegate().getImplementsList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassInitializer[] getInitializers() {
        PsiClassInitializer[] initializers = getClsDelegate().getInitializers();
        Intrinsics.checkNotNullExpressionValue(initializers, "getInitializers(...)");
        return initializers;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiJvmMember
    /* JADX INFO: renamed from: getContainingClass, reason: collision with other method in class */
    public PsiClass mo6092getContainingClass() {
        PsiElement parent = getMyParent();
        if (parent instanceof PsiClass) {
            return (PsiClass) parent;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritorDeep(PsiClass baseClass, PsiClass classToByPass) {
        return getClsDelegate().isInheritorDeep(baseClass, classToByPass);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public List<Pair<PsiMethod, PsiSubstitutor>> getAllMethodsAndTheirSubstitutors() {
        List<Pair<PsiMethod, PsiSubstitutor>> allWithSubstitutorsByMap = PsiClassImplUtil.getAllWithSubstitutorsByMap(this, PsiClassImplUtil.MemberType.METHOD);
        Intrinsics.checkNotNullExpressionValue(allWithSubstitutorsByMap, "getAllWithSubstitutorsByMap(...)");
        return allWithSubstitutorsByMap;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInterface() {
        return getClsDelegate().isInterface();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public PsiTypeParameter[] getTypeParameters() {
        PsiTypeParameter[] typeParameters = getClsDelegate().getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        return typeParameters;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isInheritor(PsiClass baseClass, boolean checkDeep) {
        Intrinsics.checkNotNullParameter(baseClass, "baseClass");
        return getClsDelegate().isInheritor(baseClass, checkDeep);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean processDeclarations(PsiScopeProcessor processor, ResolveState state, PsiElement lastParent, PsiElement place) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(place, "place");
        return PsiClassImplUtil.processDeclarationsInClass(this, processor, state, null, lastParent, place, PsiUtil.getLanguageLevel(place), false);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isEnum() {
        return getClsDelegate().isEnum();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getExtendsListTypes() {
        PsiClassType[] extendsListTypes = PsiClassImplUtil.getExtendsListTypes(this);
        Intrinsics.checkNotNullExpressionValue(extendsListTypes, "getExtendsListTypes(...)");
        return extendsListTypes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameterList */
    public PsiTypeParameterList mo6093getTypeParameterList() {
        return getClsDelegate().mo6093getTypeParameterList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public boolean isAnnotationType() {
        return getClsDelegate().isAnnotationType();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiNameIdentifierOwner
    /* JADX INFO: renamed from: getNameIdentifier */
    public PsiIdentifier mo7392getNameIdentifier() {
        return getClsDelegate().mo7392getNameIdentifier();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getInterfaces() {
        PsiClass[] interfaces = PsiClassImplUtil.getInterfaces(this);
        Intrinsics.checkNotNullExpressionValue(interfaces, "getInterfaces(...)");
        return interfaces;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass getSuperClass() {
        return PsiClassImplUtil.getSuperClass(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getSupers() {
        PsiClass[] supers = PsiClassImplUtil.getSupers(this);
        Intrinsics.checkNotNullExpressionValue(supers, "getSupers(...)");
        return supers;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getSuperTypes() {
        PsiClassType[] superTypes = PsiClassImplUtil.getSuperTypes(this);
        Intrinsics.checkNotNullExpressionValue(superTypes, "getSuperTypes(...)");
        return superTypes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public Collection<HierarchicalMethodSignature> getVisibleSignatures() {
        Collection<HierarchicalMethodSignature> visibleSignatures = PsiSuperMethodImplUtil.getVisibleSignatures(this);
        Intrinsics.checkNotNullExpressionValue(visibleSignatures, "getVisibleSignatures(...)");
        return visibleSignatures;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    public String getQualifiedName() {
        return getClsDelegate().getQualifiedName();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClassType[] getImplementsListTypes() {
        PsiClassType[] implementsListTypes = PsiClassImplUtil.getImplementsListTypes(this);
        Intrinsics.checkNotNullExpressionValue(implementsListTypes, "getImplementsListTypes(...)");
        return implementsListTypes;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiDocCommentOwner
    public boolean isDeprecated() {
        return getClsDelegate().isDeprecated();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        PsiElement name3 = getClsDelegate().setName(name2);
        Intrinsics.checkNotNullExpressionValue(name3, "setName(...)");
        return name3;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiTypeParameterListOwner
    public boolean hasTypeParameters() {
        return PsiImplUtil.hasTypeParameters(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiReferenceList getExtendsList() {
        return getClsDelegate().getExtendsList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiJavaDocumentedElement
    /* JADX INFO: renamed from: getDocComment */
    public PsiDocComment mo6091getDocComment() {
        return getClsDelegate().mo6091getDocComment();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifierListOwner
    /* JADX INFO: renamed from: getModifierList */
    public PsiModifierList mo6095getModifierList() {
        return getClsDelegate().mo6095getModifierList();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiElement getScope() {
        PsiElement scope = getClsDelegate().getScope();
        Intrinsics.checkNotNullExpressionValue(scope, "getScope(...)");
        return scope;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiClass[] getAllInnerClasses() {
        PsiClass[] allInnerClasses = PsiClassImplUtil.getAllInnerClasses(this);
        Intrinsics.checkNotNullExpressionValue(allInnerClasses, "getAllInnerClasses(...)");
        return allInnerClasses;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiMethod[] getAllMethods() {
        PsiMethod[] allMethods = PsiClassImplUtil.getAllMethods(this);
        Intrinsics.checkNotNullExpressionValue(allMethods, "getAllMethods(...)");
        return allMethods;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiField[] getAllFields() {
        PsiField[] allFields = PsiClassImplUtil.getAllFields(this);
        Intrinsics.checkNotNullExpressionValue(allFields, "getAllFields(...)");
        return allFields;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<PsiMethod> getOwnMethods() {
        final KtLightClassForDecompiledDeclaration ktLightClassForDecompiledDeclaration = this;
        return (List) CachedValuesManager.getCachedValue((PsiElement) ktLightClassForDecompiledDeclaration, new CachedValueProvider() { // from class: org.jetbrains.kotlin.analysis.decompiled.light.classes.KtLightClassForDecompiledDeclaration$getOwnMethods$$inlined$cachedValueWithLibraryTracker$1
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result<T> compute() {
                PsiMember enumEntriesPsiMethod;
                boolean zIsEnum = this.isEnum();
                PsiMethod[] methods = this.getClsDelegate().getMethods();
                Intrinsics.checkNotNullExpressionValue(methods, "getMethods(...)");
                ArrayList arrayList = new ArrayList();
                for (PsiMethod psiMethod : methods) {
                    Intrinsics.checkNotNull(psiMethod);
                    if (SyntheticElementUtilsKt.isSyntheticValuesOrValueOfMethod(psiMethod)) {
                        enumEntriesPsiMethod = null;
                    } else if (zIsEnum && SyntheticElementUtilsKt.isGetEntriesMethod(psiMethod)) {
                        enumEntriesPsiMethod = KotlinClassInnerStuffCacheKt.getEnumEntriesPsiMethod(this);
                    } else {
                        enumEntriesPsiMethod = (PsiMethod) new KtLightMethodForDecompiledDeclaration(psiMethod, this, new LightMemberOriginForCompiledMethod(psiMethod, this.getFile()));
                    }
                    if (enumEntriesPsiMethod != null) {
                        arrayList.add(enumEntriesPsiMethod);
                    }
                }
                Project project = ktLightClassForDecompiledDeclaration.getProject();
                Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                return CachedValueProvider.Result.createSingleDependency(arrayList, KotlinModificationTrackerFactoryKt.createAllLibrariesModificationTracker(project));
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<PsiField> getOwnFields() {
        final KtLightClassForDecompiledDeclaration ktLightClassForDecompiledDeclaration = this;
        return (List) CachedValuesManager.getCachedValue((PsiElement) ktLightClassForDecompiledDeclaration, new CachedValueProvider() { // from class: org.jetbrains.kotlin.analysis.decompiled.light.classes.KtLightClassForDecompiledDeclaration$getOwnFields$$inlined$cachedValueWithLibraryTracker$1
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result<T> compute() {
                KtLightEnumEntryForDecompiledDeclaration ktLightFieldForDecompiledDeclaration;
                PsiField[] fields = this.getClsDelegate().getFields();
                Intrinsics.checkNotNullExpressionValue(fields, "getFields(...)");
                PsiField[] psiFieldArr = fields;
                ArrayList arrayList = new ArrayList(psiFieldArr.length);
                for (PsiField psiField : psiFieldArr) {
                    if (psiField instanceof PsiEnumConstant) {
                        ktLightFieldForDecompiledDeclaration = new KtLightEnumEntryForDecompiledDeclaration((PsiEnumConstant) psiField, this, new LightMemberOriginForCompiledField(psiField, this.getFile()), this.getFile());
                    } else {
                        Intrinsics.checkNotNull(psiField);
                        ktLightFieldForDecompiledDeclaration = new KtLightFieldForDecompiledDeclaration(psiField, this, new LightMemberOriginForCompiledField(psiField, this.getFile()));
                    }
                    arrayList.add(ktLightFieldForDecompiledDeclaration);
                }
                Project project = ktLightClassForDecompiledDeclaration.getProject();
                Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                return CachedValueProvider.Result.createSingleDependency(arrayList, KotlinModificationTrackerFactoryKt.createAllLibrariesModificationTracker(project));
            }
        });
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiExtensibleClass
    public List<PsiClass> getOwnInnerClasses() {
        final KtLightClassForDecompiledDeclaration ktLightClassForDecompiledDeclaration = this;
        return (List) CachedValuesManager.getCachedValue((PsiElement) ktLightClassForDecompiledDeclaration, new CachedValueProvider() { // from class: org.jetbrains.kotlin.analysis.decompiled.light.classes.KtLightClassForDecompiledDeclaration$getOwnInnerClasses$$inlined$cachedValueWithLibraryTracker$1
            @Override // org.jetbrains.kotlin.com.intellij.psi.util.CachedValueProvider
            public final CachedValueProvider.Result<T> compute() {
                List<KtDeclaration> declarations;
                PsiClass[] innerClasses = this.getClsDelegate().getInnerClasses();
                Intrinsics.checkNotNullExpressionValue(innerClasses, "getInnerClasses(...)");
                PsiClass[] psiClassArr = innerClasses;
                ArrayList arrayList = new ArrayList(psiClassArr.length);
                for (PsiClass psiClass : psiClassArr) {
                    KtClassOrObject kotlinOrigin = this.getKotlinOrigin();
                    KtClassOrObject ktClassOrObject = null;
                    if (kotlinOrigin != null && (declarations = kotlinOrigin.getDeclarations()) != null) {
                        ArrayList arrayList2 = new ArrayList();
                        for (T t : declarations) {
                            if (t instanceof KtClassOrObject) {
                                arrayList2.add(t);
                            }
                        }
                        for (T t2 : arrayList2) {
                            if (Intrinsics.areEqual(((KtClassOrObject) t2).getMyName(), this.getClsDelegate().getMyName())) {
                                ktClassOrObject = t2;
                                break;
                            }
                        }
                        ktClassOrObject = ktClassOrObject;
                    }
                    Intrinsics.checkNotNull(psiClass);
                    KtLightClassForDecompiledDeclaration ktLightClassForDecompiledDeclaration2 = this;
                    arrayList.add(new KtLightClassForDecompiledDeclaration(psiClass, ktLightClassForDecompiledDeclaration2, ktLightClassForDecompiledDeclaration2.getFile(), ktClassOrObject));
                }
                Project project = ktLightClassForDecompiledDeclaration.getProject();
                Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                return CachedValueProvider.Result.createSingleDependency(arrayList, KotlinModificationTrackerFactoryKt.createAllLibrariesModificationTracker(project));
            }
        });
    }

    @Override // org.jetbrains.kotlin.asJava.KtLightClassMarker
    public LightClassOriginKind getOriginKind() {
        return this.originKind;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement getNavigationElement() {
        KtClsFile navigationElement;
        KtClassOrObject kotlinOrigin = getKotlinOrigin();
        if (kotlinOrigin == null || (navigationElement = kotlinOrigin.getNavigationElement()) == null) {
            navigationElement = this.file;
        }
        Intrinsics.checkNotNull(navigationElement);
        return navigationElement;
    }

    public boolean equals(Object other) {
        if (this != other) {
            if (other instanceof KtLightClassForDecompiledDeclaration) {
                KtLightClassForDecompiledDeclaration ktLightClassForDecompiledDeclaration = (KtLightClassForDecompiledDeclaration) other;
                if (!Intrinsics.areEqual(getQualifiedName(), ktLightClassForDecompiledDeclaration.getQualifiedName()) || !Intrinsics.areEqual(this.file, ktLightClassForDecompiledDeclaration.file)) {
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        FqName fqName;
        String qualifiedName = getQualifiedName();
        if (qualifiedName != null) {
            return qualifiedName.hashCode();
        }
        KtClassOrObject kotlinOrigin = getKotlinOrigin();
        if (kotlinOrigin == null || (fqName = kotlinOrigin.mo7391getFqName()) == null) {
            return 0;
        }
        return fqName.hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public PsiElement copy() {
        return this;
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return getClass().getSimpleName() + " of " + getMyParent();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.navigation.NavigationItem, org.jetbrains.kotlin.com.intellij.pom.PomNamedTarget, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiQualifiedNamedElement
    /* JADX INFO: renamed from: getName */
    public String getMyName() {
        return getClsDelegate().getMyName();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElementBase, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement, org.jetbrains.kotlin.com.intellij.pom.PomTarget
    public boolean isValid() {
        KtClassOrObject kotlinOrigin;
        return this.file.isValid() && getClsDelegate().isValid() && ((kotlinOrigin = getKotlinOrigin()) == null || kotlinOrigin.isValid());
    }
}
