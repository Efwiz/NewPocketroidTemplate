package org.jetbrains.kotlin.fir.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.util.Pair;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.PsiPackage;
import org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiPackageImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.JavaStubElementTypes;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiClassStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiClassStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiModifierListStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderKt;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.jvm.KotlinFinderMarker;

/* JADX INFO: compiled from: FirJavaElementFinder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J \u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u0013H\u0002J\u001a\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J#\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00150\u001b2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016¢\u0006\u0002\u0010\u001cJ\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J#\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00150\u001b2\u0006\u0010 \u001a\u00020\u001e2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016¢\u0006\u0002\u0010!R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u000b\u001a\u00070\f¢\u0006\u0002\b\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/java/FirJavaElementFinder;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElementFinder;", "Lorg/jetbrains/kotlin/resolve/jvm/KotlinFinderMarker;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lcom/intellij/openapi/project/Project;)V", "firProviders", "", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirProvider;", "psiManager", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiManager;", "Lorg/jetbrains/annotations/NotNull;", "buildStub", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/PsiClassStub;", "firClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "parent", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "findClass", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "qualifiedName", "", "scope", "Lorg/jetbrains/kotlin/com/intellij/psi/search/GlobalSearchScope;", "findClasses", "", "(Ljava/lang/String;Lcom/intellij/psi/search/GlobalSearchScope;)[Lcom/intellij/psi/PsiClass;", "findPackage", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiPackage;", "getClasses", "psiPackage", "(Lcom/intellij/psi/PsiPackage;Lcom/intellij/psi/search/GlobalSearchScope;)[Lcom/intellij/psi/PsiClass;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJavaElementFinder extends PsiElementFinder implements KotlinFinderMarker {
    private final List<FirProvider> firProviders;
    private final PsiManager psiManager;
    private final FirSession session;

    public FirJavaElementFinder(FirSession session, Project project) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(project, "project");
        this.session = session;
        PsiManager psiManager = PsiManager.getInstance(project);
        Intrinsics.checkNotNullExpressionValue(psiManager, "getInstance(...)");
        this.psiManager = psiManager;
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        listCreateListBuilder.add(FirProviderKt.getFirProvider(session));
        Iterator it = FirJavaElementFinderKt.collectAllDependentSourceSessions(session).iterator();
        while (it.hasNext()) {
            listCreateListBuilder.add(FirProviderKt.getFirProvider((FirSession) it.next()));
        }
        this.firProviders = CollectionsKt.build(listCreateListBuilder);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiPackage findPackage(String qualifiedName) {
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        List<FirProvider> list = this.firProviders;
        if ((list instanceof Collection) && list.isEmpty()) {
            return null;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (((FirProvider) it.next()).getSymbolProvider().getPackage(new FqName(qualifiedName)) != null) {
                return new PsiPackageImpl(this.psiManager, qualifiedName);
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiClass[] getClasses(PsiPackage psiPackage, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(psiPackage, "psiPackage");
        Intrinsics.checkNotNullParameter(scope, "scope");
        List<FirProvider> list = this.firProviders;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            Set<Name> classNamesInPackage = ((FirProvider) it.next()).getClassNamesInPackage(new FqName(psiPackage.getQualifiedName()));
            ArrayList arrayList2 = new ArrayList();
            Iterator<T> it2 = classNamesInPackage.iterator();
            while (it2.hasNext()) {
                PsiClass psiClassFindClass = findClass(psiPackage.getQualifiedName() + '.' + ((Name) it2.next()).getIdentifier(), scope);
                if (psiClassFindClass != null) {
                    arrayList2.add(psiClassFindClass);
                }
            }
            CollectionsKt.addAll(arrayList, arrayList2);
        }
        return (PsiClass[]) arrayList.toArray(new PsiClass[0]);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiClass[] findClasses(String qualifiedName, GlobalSearchScope scope) {
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        Intrinsics.checkNotNullParameter(scope, "scope");
        PsiClass psiClassFindClass = findClass(qualifiedName, scope);
        return psiClassFindClass != null ? new PsiClass[]{psiClassFindClass} : new PsiClass[0];
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiElementFinder
    public PsiClass findClass(String qualifiedName, GlobalSearchScope scope) {
        FirRegularClass firRegularClass;
        Intrinsics.checkNotNullParameter(qualifiedName, "qualifiedName");
        Intrinsics.checkNotNullParameter(scope, "scope");
        if (StringsKt.endsWith$default(qualifiedName, ".", false, 2, (Object) null)) {
            return null;
        }
        FqName fqName = new FqName(qualifiedName);
        for (FqName fqName2 : SequencesKt.generateSequence(fqName, new Function1<FqName, FqName>() { // from class: org.jetbrains.kotlin.fir.java.FirJavaElementFinder.findClass.1
            @Override // kotlin.jvm.functions.Function1
            public final FqName invoke(FqName it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return FqNamesUtilKt.parentOrNull(it);
            }
        })) {
            if (fqName2.isRoot()) {
                break;
            }
            ClassId classId = ClassId.topLevel(fqName2);
            Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
            Iterator<T> it = this.firProviders.iterator();
            do {
                if (!it.hasNext()) {
                    firRegularClass = null;
                    break;
                }
                FirClassLikeDeclaration firClassifierByFqName = ((FirProvider) it.next()).getFirClassifierByFqName(classId);
                firRegularClass = firClassifierByFqName instanceof FirRegularClass ? (FirRegularClass) firClassifierByFqName : null;
            } while (firRegularClass == null);
            if (firRegularClass != null) {
                FqName packageFqName = classId.getPackageFqName();
                Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
                PsiClass psiClassFindInnerClassByName = (PsiClass) buildStub(firRegularClass, FirJavaElementFinderKt.createJavaFileStub(packageFqName, this.psiManager)).getPsi();
                List<Name> listPathSegments = FqNamesUtilKt.tail(fqName, fqName2).pathSegments();
                Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
                Iterator<T> it2 = listPathSegments.iterator();
                while (it2.hasNext()) {
                    psiClassFindInnerClassByName = psiClassFindInnerClassByName.findInnerClassByName(((Name) it2.next()).getIdentifier(), false);
                    if (psiClassFindInnerClassByName == null) {
                        return null;
                    }
                }
                return psiClassFindInnerClassByName;
            }
        }
        return null;
    }

    private final PsiClassStub<?> buildStub(FirRegularClass firClass, StubElement<?> parent) {
        List<FirTypeRef> listResolveSupertypesOnAir;
        PsiClassStubImpl psiClassStubImpl = new PsiClassStubImpl(JavaStubElementTypes.CLASS, parent, FirDeclarationUtilKt.getClassId(firClass).asSingleFqName().asString(), firClass.getName().getIdentifier(), null, PsiClassStubImpl.packFlags(false, firClass.getClassKind() == ClassKind.INTERFACE, firClass.getClassKind() == ClassKind.ENUM_CLASS, false, false, firClass.getClassKind() == ClassKind.ANNOTATION_CLASS, false, false, false, false, false));
        PsiClassStubImpl psiClassStubImpl2 = psiClassStubImpl;
        new PsiModifierListStubImpl(psiClassStubImpl2, FirJavaElementFinderKt.packFlags(firClass));
        List<FirTypeParameterRef> typeParameters = firClass.getTypeParameters();
        ArrayList arrayList = new ArrayList();
        for (Object obj : typeParameters) {
            if (obj instanceof FirTypeParameter) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(new Pair(((FirTypeParameter) it.next()).getName().asString(), new String[]{CommonClassNames.JAVA_LANG_OBJECT}));
        }
        FirJavaElementFinderKt.newTypeParameterList(psiClassStubImpl2, arrayList3);
        List<FirTypeRef> superTypeRefs = firClass.getSuperTypeRefs();
        if ((superTypeRefs instanceof Collection) && superTypeRefs.isEmpty()) {
            listResolveSupertypesOnAir = firClass.getSuperTypeRefs();
            break;
        }
        Iterator<T> it2 = superTypeRefs.iterator();
        while (true) {
            if (it2.hasNext()) {
                if (!(((FirTypeRef) it2.next()) instanceof FirResolvedTypeRef)) {
                    listResolveSupertypesOnAir = FirJavaElementFinderKt.resolveSupertypesOnAir(firClass, this.session);
                    break;
                }
            } else {
                listResolveSupertypesOnAir = firClass.getSuperTypeRefs();
                break;
            }
        }
        FirJavaElementFinderKt.addSupertypesReferencesLists(psiClassStubImpl, firClass, listResolveSupertypesOnAir, this.session);
        List<FirDeclaration> declarations = firClass.getDeclarations();
        ArrayList arrayList4 = new ArrayList();
        for (Object obj2 : declarations) {
            if (obj2 instanceof FirRegularClass) {
                arrayList4.add(obj2);
            }
        }
        Iterator it3 = arrayList4.iterator();
        while (it3.hasNext()) {
            buildStub((FirRegularClass) it3.next(), psiClassStubImpl2);
        }
        return psiClassStubImpl;
    }
}
