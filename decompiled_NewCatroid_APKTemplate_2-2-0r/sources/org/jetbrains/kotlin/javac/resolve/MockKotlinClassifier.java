package org.jetbrains.kotlin.javac.resolve;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.search.SearchScope;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.descriptors.java.JavaVisibilities;
import org.jetbrains.kotlin.javac.JavaClassWithClassId;
import org.jetbrains.kotlin.javac.JavacWrapper;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.LightClassOriginKind;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtTypeParameter;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;

/* JADX INFO: compiled from: KotlinClassifiersCache.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0001\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010S\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010T\u001a\u0004\u0018\u00010U2\u0006\u00109\u001a\u00020VJ\u0012\u0010W\u001a\u0004\u0018\u00010&2\u0006\u00109\u001a\u00020!H\u0016J\b\u0010X\u001a\u00020\u000eH\u0016J\u0010\u0010Y\u001a\u00020\u001c2\u0006\u0010Z\u001a\u00020[H\u0016R\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0010R\u0014\u0010\u0015\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0010R\u0014\u0010\u0017\u001a\u00020\u00188VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001b\u001a\u00020\u001c8F¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0017\u0010$\u001a\b\u0012\u0004\u0012\u00020&0%8F¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0014\u0010)\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b)\u0010\u0010R\u0014\u0010*\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b*\u0010\u0010R\u0014\u0010+\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b+\u0010\u0010R\u0014\u0010,\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010\u0010R\u0014\u0010-\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b-\u0010\u0010R\u0014\u0010.\u001a\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b.\u0010\u001eR\u0014\u0010/\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b/\u0010\u0010R\u0014\u00100\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b0\u0010\u0010R\u0014\u00101\u001a\u00020\u001c8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b1\u0010\u001eR\u0014\u00102\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b2\u0010\u0010R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u00103\u001a\u0002048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b5\u00106R\u0014\u00107\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b8\u0010\u0010R\u0014\u00109\u001a\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b:\u0010;R\u0014\u0010<\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b=\u0010\u0010R\u001a\u0010>\u001a\b\u0012\u0004\u0012\u00020?0%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b@\u0010(R\u0014\u0010A\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bB\u0010\u0010R\u001a\u0010C\u001a\b\u0012\u0004\u0012\u00020?0%8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bD\u0010(R\u0014\u0010E\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bF\u0010\u0010R\u0011\u0010G\u001a\u00020H8F¢\u0006\u0006\u001a\u0004\bI\u0010JR\u0016\u0010K\u001a\u0004\u0018\u00010L8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bM\u0010NR\u0014\u0010O\u001a\u00020P8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bQ\u0010R¨\u0006\\"}, d2 = {"Lorg/jetbrains/kotlin/javac/resolve/MockKotlinClassifier;", "Lorg/jetbrains/kotlin/javac/JavaClassWithClassId;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "ktFile", "Lorg/jetbrains/kotlin/psi/KtFile;", ResponseCacheMiddleware.CACHE, "Lorg/jetbrains/kotlin/javac/resolve/KotlinClassifiersCache;", "javac", "Lorg/jetbrains/kotlin/javac/JavacWrapper;", "(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/psi/KtClassOrObject;Lorg/jetbrains/kotlin/psi/KtFile;Lorg/jetbrains/kotlin/javac/resolve/KotlinClassifiersCache;Lorg/jetbrains/kotlin/javac/JavacWrapper;)V", "annotations", "", "getAnnotations", "()Ljava/lang/Void;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "constructors", "getConstructors", "fields", "getFields", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "hasTypeParameters", "", "getHasTypeParameters", "()Z", "innerClassNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getInnerClassNames", "()Ljava/util/List;", "innerClasses", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaClass;", "getInnerClasses", "()Ljava/util/Collection;", "isAbstract", "isAnnotationType", "isDeprecatedInJavaDoc", "isEnum", "isFinal", "isFromSource", "isInterface", "isRecord", "isSealed", "isStatic", "lightClassOriginKind", "Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "getLightClassOriginKind", "()Lorg/jetbrains/kotlin/load/java/structure/LightClassOriginKind;", "methods", "getMethods", "name", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "outerClass", "getOuterClass", "permittedTypes", "Lorg/jetbrains/kotlin/load/java/structure/JavaClassifierType;", "getPermittedTypes", "recordComponents", "getRecordComponents", "supertypes", "getSupertypes", "typeParameters", "getTypeParameters", "typeParametersNumber", "", "getTypeParametersNumber", "()I", "virtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "getVirtualFile", "()Lcom/intellij/openapi/vfs/VirtualFile;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "findAnnotation", "findField", "Lorg/jetbrains/kotlin/load/java/structure/JavaField;", "", "findInnerClass", "hasDefaultConstructor", "isFromSourceCodeInScope", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/SearchScope;", "javac-wrapper"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MockKotlinClassifier implements JavaClassWithClassId {
    private final KotlinClassifiersCache cache;
    private final ClassId classId;
    private final KtClassOrObject classOrObject;
    private final JavacWrapper javac;
    private final KtFile ktFile;

    @Override // org.jetbrains.kotlin.load.java.structure.impl.VirtualFileBoundJavaClass
    public VirtualFile getVirtualFile() {
        return null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public boolean isFromSource() {
        return true;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.impl.VirtualFileBoundJavaClass
    public boolean isFromSourceCodeInScope(SearchScope scope) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        return true;
    }

    public MockKotlinClassifier(ClassId classId, KtClassOrObject ktClassOrObject, KtFile ktFile, KotlinClassifiersCache cache, JavacWrapper javac) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(cache, "cache");
        Intrinsics.checkNotNullParameter(javac, "javac");
        this.classId = classId;
        this.classOrObject = ktClassOrObject;
        this.ktFile = ktFile;
        this.cache = cache;
        this.javac = javac;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: findAnnotation, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ JavaAnnotation mo7251findAnnotation(FqName fqName) {
        return (JavaAnnotation) findAnnotation(fqName);
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: getAnnotations, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ Collection mo7252getAnnotations() {
        return (Collection) getAnnotations();
    }

    @Override // org.jetbrains.kotlin.javac.JavaClassWithClassId
    public ClassId getClassId() {
        return this.classId;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getConstructors, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ Collection mo7253getConstructors() {
        return (Collection) getConstructors();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getFields, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ Collection mo7254getFields() {
        return (Collection) getFields();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getMethods, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ Collection mo7255getMethods() {
        return (Collection) getMethods();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public /* bridge */ /* synthetic */ JavaClass getOuterClass() {
        return (JavaClass) getOuterClass();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: getRecordComponents, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ Collection mo7257getRecordComponents() {
        return (Collection) getRecordComponents();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaTypeParameterListOwner
    /* JADX INFO: renamed from: getTypeParameters, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ List mo7258getTypeParameters() {
        return (List) getTypeParameters();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: hasDefaultConstructor, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ boolean mo7259hasDefaultConstructor() {
        return ((Boolean) hasDefaultConstructor()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isAbstract, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ boolean mo7260isAbstract() {
        return ((Boolean) isAbstract()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isAnnotationType, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ boolean mo7261isAnnotationType() {
        return ((Boolean) isAnnotationType()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaAnnotationOwner
    /* JADX INFO: renamed from: isDeprecatedInJavaDoc, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ boolean getIsDeprecatedInJavaDoc() {
        return ((Boolean) isDeprecatedInJavaDoc()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isEnum, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ boolean mo7263isEnum() {
        return ((Boolean) isEnum()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isFinal, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ boolean mo7264isFinal() {
        return ((Boolean) isFinal()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isInterface, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ boolean mo7265isInterface() {
        return ((Boolean) isInterface()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    /* JADX INFO: renamed from: isRecord, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ boolean mo7266isRecord() {
        return ((Boolean) isRecord()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    /* JADX INFO: renamed from: isStatic, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ boolean mo7267isStatic() {
        return ((Boolean) isStatic()).booleanValue();
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public FqName getFqName() {
        FqName fqNameAsSingleFqName = getClassId().asSingleFqName();
        Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
        return fqNameAsSingleFqName;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaModifierListOwner
    public Visibility getVisibility() {
        KtClassOrObject ktClassOrObject = this.classOrObject;
        if (ktClassOrObject == null) {
            return Visibilities.Public.INSTANCE;
        }
        KtModifierKeywordToken ktModifierKeywordTokenVisibilityModifierType = KtPsiUtilKt.visibilityModifierType(ktClassOrObject);
        if (ktModifierKeywordTokenVisibilityModifierType == null ? true : Intrinsics.areEqual(ktModifierKeywordTokenVisibilityModifierType, KtTokens.PUBLIC_KEYWORD)) {
            return Visibilities.Public.INSTANCE;
        }
        if (Intrinsics.areEqual(ktModifierKeywordTokenVisibilityModifierType, KtTokens.PRIVATE_KEYWORD)) {
            return Visibilities.Private.INSTANCE;
        }
        return Intrinsics.areEqual(ktModifierKeywordTokenVisibilityModifierType, KtTokens.PROTECTED_KEYWORD) ? Visibilities.Protected.INSTANCE : JavaVisibilities.PackageVisibility.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public Collection<JavaClassifierType> getSupertypes() {
        if (this.classOrObject == null) {
            return CollectionsKt.emptyList();
        }
        List<ClassId> listResolveSupertypes = this.javac.getKotlinResolver().resolveSupertypes(this.classOrObject);
        ArrayList arrayList = new ArrayList();
        for (ClassId classId : listResolveSupertypes) {
            JavaClass kotlinClassifier = this.javac.getKotlinClassifier(classId);
            if (kotlinClassifier == null) {
                kotlinClassifier = JavacWrapper.findClass$default(this.javac, classId, null, 2, null);
            }
            if (kotlinClassifier != null) {
                arrayList.add(kotlinClassifier);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(new MockKotlinClassifierType((JavaClass) it.next()));
        }
        return arrayList3;
    }

    public final Collection<JavaClass> getInnerClasses() {
        List<KtDeclaration> declarations;
        KtClassOrObject ktClassOrObject = this.classOrObject;
        if (ktClassOrObject == null || (declarations = ktClassOrObject.getDeclarations()) == null) {
            return CollectionsKt.emptyList();
        }
        ArrayList<KtClassOrObject> arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof KtClassOrObject) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (KtClassOrObject ktClassOrObject2 : arrayList) {
            KotlinClassifiersCache kotlinClassifiersCache = this.cache;
            KtFile ktFile = this.ktFile;
            ClassId classIdCreateNestedClassId = getClassId().createNestedClassId(ktClassOrObject2.getNameAsSafeName());
            Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
            MockKotlinClassifier mockKotlinClassifierCreateMockKotlinClassifier = kotlinClassifiersCache.createMockKotlinClassifier(ktClassOrObject2, ktFile, classIdCreateNestedClassId);
            if (mockKotlinClassifierCreateMockKotlinClassifier != null) {
                arrayList2.add(mockKotlinClassifierCreateMockKotlinClassifier);
            }
        }
        return arrayList2;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public LightClassOriginKind getLightClassOriginKind() {
        return LightClassOriginKind.SOURCE;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaNamedElement
    public Name getName() {
        Name nameShortNameOrSpecial = getFqName().shortNameOrSpecial();
        Intrinsics.checkNotNullExpressionValue(nameShortNameOrSpecial, "shortNameOrSpecial(...)");
        return nameShortNameOrSpecial;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public List<Name> getInnerClassNames() {
        Collection<JavaClass> innerClasses = getInnerClasses();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(innerClasses, 10));
        Iterator<T> it = innerClasses.iterator();
        while (it.hasNext()) {
            arrayList.add(((JavaClass) it.next()).getName());
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public JavaClass findInnerClass(Name name2) {
        Object next;
        Intrinsics.checkNotNullParameter(name2, "name");
        Iterator<T> it = getInnerClasses().iterator();
        while (it.hasNext()) {
            next = it.next();
            if (Intrinsics.areEqual(((JavaClass) next).getName(), name2)) {
                return (JavaClass) next;
            }
        }
        next = null;
        return (JavaClass) next;
    }

    public final int getTypeParametersNumber() {
        List<KtTypeParameter> typeParameters;
        KtClassOrObject ktClassOrObject = this.classOrObject;
        if (ktClassOrObject == null || (typeParameters = ktClassOrObject.getTypeParameters()) == null) {
            return 0;
        }
        return typeParameters.size();
    }

    public final boolean getHasTypeParameters() {
        return getTypeParametersNumber() > 0;
    }

    public final JavaField findField(String name2) {
        JavaField javaFieldFindField;
        Intrinsics.checkNotNullParameter(name2, "name");
        KtClassOrObject ktClassOrObject = this.classOrObject;
        return (ktClassOrObject == null || (javaFieldFindField = this.javac.getKotlinResolver().findField(ktClassOrObject, name2)) == null) ? this.javac.getKotlinResolver().findField(this.ktFile, name2) : javaFieldFindField;
    }

    public Void isAbstract() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void isStatic() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void isFinal() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void getTypeParameters() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void getOuterClass() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void isInterface() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void isAnnotationType() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void isEnum() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void isRecord() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public boolean isSealed() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    @Override // org.jetbrains.kotlin.load.java.structure.JavaClass
    public Collection<JavaClassifierType> getPermittedTypes() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void getMethods() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void getFields() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void getConstructors() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void getRecordComponents() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void hasDefaultConstructor() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void getAnnotations() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void isDeprecatedInJavaDoc() {
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }

    public Void findAnnotation(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        KotlinClassifiersCacheKt.shouldNotBeCalled();
        throw null;
    }
}
