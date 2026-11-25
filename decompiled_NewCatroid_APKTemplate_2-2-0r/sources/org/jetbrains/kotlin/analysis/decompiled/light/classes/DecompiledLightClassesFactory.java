package org.jetbrains.kotlin.analysis.decompiled.light.classes;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import org.jetbrains.kotlin.analysis.decompiler.psi.file.KtClsFile;
import org.jetbrains.kotlin.asJava.builder.ClsWrapperStubPsiFactory;
import org.jetbrains.kotlin.asJava.classes.KtLightClassForFacade;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.psi.ClassFileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsClassImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiJavaFileStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtilKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments;

/* JADX INFO: compiled from: DecompiledLightClassesFactory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J*\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fJt\u0010\r\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00102M\u0010\u0011\u001aI\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0016¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0017\u0012\u0015\u0012\u0013\u0018\u00010\n¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0018\u0012\u0004\u0012\u0002H\u000e0\u0012H\u0002¢\u0006\u0002\u0010\u0019J\u0018\u0010\r\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\fJ&\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00060 J\u001a\u0010!\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\"\u001a\u00020\n2\u0006\u0010#\u001a\u00020\u001aH\u0002J\u0012\u0010$\u001a\u0004\u0018\u00010\u001e2\u0006\u0010\"\u001a\u00020\nH\u0002J\u0018\u0010%\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\"\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/DecompiledLightClassesFactory;", "", "()V", "createClsJavaClassFromVirtualFile", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsClassImpl;", "mirrorFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "classFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "correspondingClassOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "createLightClassForDecompiledKotlinFile", ExifInterface.GPS_DIRECTION_TRUE, "file", "Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtClsFile;", "builder", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "kotlinClsFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "javaClsClass", "classOrObject", "(Lcom/intellij/openapi/project/Project;Lorg/jetbrains/kotlin/analysis/decompiler/psi/file/KtClsFile;Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "Lorg/jetbrains/kotlin/analysis/decompiled/light/classes/KtLightClassForDecompiledDeclaration;", "createLightFacadeForDecompiledKotlinFile", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForFacade;", "facadeClassFqName", "Lorg/jetbrains/kotlin/name/FqName;", "files", "", "findCorrespondingLightClass", "decompiledClassOrObject", "rootLightClassForDecompiledFile", "getClassRelativeName", "getLightClassForDecompiledClassOrObject", "light-classes-for-decompiled"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DecompiledLightClassesFactory {
    public static final DecompiledLightClassesFactory INSTANCE = new DecompiledLightClassesFactory();

    private DecompiledLightClassesFactory() {
    }

    public final KtLightClassForDecompiledDeclaration getLightClassForDecompiledClassOrObject(KtClassOrObject decompiledClassOrObject, Project project) {
        KtLightClassForDecompiledDeclaration ktLightClassForDecompiledDeclarationCreateLightClassForDecompiledKotlinFile;
        Intrinsics.checkNotNullParameter(decompiledClassOrObject, "decompiledClassOrObject");
        Intrinsics.checkNotNullParameter(project, "project");
        if (decompiledClassOrObject instanceof KtEnumEntry) {
            return null;
        }
        PsiFile containingFile = decompiledClassOrObject.getContainingFile();
        KtClsFile ktClsFile = containingFile instanceof KtClsFile ? (KtClsFile) containingFile : null;
        if (ktClsFile == null || (ktLightClassForDecompiledDeclarationCreateLightClassForDecompiledKotlinFile = createLightClassForDecompiledKotlinFile(ktClsFile, project)) == null) {
            return null;
        }
        return findCorrespondingLightClass(decompiledClassOrObject, ktLightClassForDecompiledDeclarationCreateLightClassForDecompiledKotlinFile);
    }

    private final KtLightClassForDecompiledDeclaration findCorrespondingLightClass(KtClassOrObject decompiledClassOrObject, KtLightClassForDecompiledDeclaration rootLightClassForDecompiledFile) {
        FqName classRelativeName = getClassRelativeName(decompiledClassOrObject);
        if (classRelativeName == null) {
            return null;
        }
        Iterator<Name> it = classRelativeName.pathSegments().iterator();
        if (!Intrinsics.areEqual(rootLightClassForDecompiledFile.get$name(), it.next().asString())) {
            return null;
        }
        KtLightClassForDecompiledDeclaration ktLightClassForDecompiledDeclaration = rootLightClassForDecompiledFile;
        while (it.hasNext()) {
            Name next = it.next();
            String strAsString = next.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            PsiClass psiClassFindInnerClassByName = ktLightClassForDecompiledDeclaration.findInnerClassByName(strAsString, false);
            if (psiClassFindInnerClassByName != null) {
                Intrinsics.checkNotNull(psiClassFindInnerClassByName, "null cannot be cast to non-null type org.jetbrains.kotlin.analysis.decompiled.light.classes.KtLightClassForDecompiledDeclaration");
                ktLightClassForDecompiledDeclaration = (KtLightClassForDecompiledDeclaration) psiClassFindInnerClassByName;
            } else {
                KotlinExceptionWithAttachments kotlinExceptionWithAttachments = new KotlinExceptionWithAttachments("Could not find corresponding inner/nested class " + classRelativeName + " in class " + decompiledClassOrObject.mo7391getFqName() + "\nFile: " + decompiledClassOrObject.getContainingKtFile().getVirtualFile().getName());
                kotlinExceptionWithAttachments.withPsiAttachment("decompiledClassOrObject.txt", decompiledClassOrObject);
                kotlinExceptionWithAttachments.withAttachment("fileClass.txt", (Object) Reflection.getOrCreateKotlinClass(decompiledClassOrObject.getContainingFile().getClass()));
                kotlinExceptionWithAttachments.withPsiAttachment("file.txt", decompiledClassOrObject.getContainingFile());
                kotlinExceptionWithAttachments.withPsiAttachment("root.txt", rootLightClassForDecompiledFile);
                kotlinExceptionWithAttachments.withAttachment("currentName.txt", (Object) ktLightClassForDecompiledDeclaration.get$name());
                kotlinExceptionWithAttachments.withPsiAttachment("current.txt", ktLightClassForDecompiledDeclaration);
                PsiClass[] innerClasses = ktLightClassForDecompiledDeclaration.getInnerClasses();
                ArrayList arrayList = new ArrayList(innerClasses.length);
                for (PsiClass psiClass : innerClasses) {
                    arrayList.add(psiClass.get$name());
                }
                kotlinExceptionWithAttachments.withAttachment("innerClasses.txt", (Object) arrayList);
                kotlinExceptionWithAttachments.withAttachment("innerName.txt", (Object) next.asString());
                throw kotlinExceptionWithAttachments;
            }
        }
        return ktLightClassForDecompiledDeclaration;
    }

    private final FqName getClassRelativeName(KtClassOrObject decompiledClassOrObject) {
        Name nameAsName = decompiledClassOrObject.getNameAsName();
        if (nameAsName == null) {
            return null;
        }
        KtClassOrObject ktClassOrObject = (KtClassOrObject) PsiTreeUtil.getParentOfType(decompiledClassOrObject, KtClassOrObject.class, true);
        if (ktClassOrObject == null) {
            decompiledClassOrObject.isTopLevel();
            return FqName.topLevel(nameAsName);
        }
        FqName classRelativeName = getClassRelativeName(ktClassOrObject);
        if (classRelativeName != null) {
            return classRelativeName.child(nameAsName);
        }
        return null;
    }

    public final KtLightClassForDecompiledDeclaration createLightClassForDecompiledKotlinFile(KtClsFile file, Project project) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(project, "project");
        return (KtLightClassForDecompiledDeclaration) createLightClassForDecompiledKotlinFile(project, file, new Function3<KtClsFile, PsiClass, KtClassOrObject, KtLightClassForDecompiledDeclaration>() { // from class: org.jetbrains.kotlin.analysis.decompiled.light.classes.DecompiledLightClassesFactory.createLightClassForDecompiledKotlinFile.1
            @Override // kotlin.jvm.functions.Function3
            public final KtLightClassForDecompiledDeclaration invoke(KtClsFile kotlinClsFile, PsiClass javaClsClass, KtClassOrObject ktClassOrObject) {
                Intrinsics.checkNotNullParameter(kotlinClsFile, "kotlinClsFile");
                Intrinsics.checkNotNullParameter(javaClsClass, "javaClsClass");
                PsiElement parent = javaClsClass.getLightParent();
                Intrinsics.checkNotNullExpressionValue(parent, "getParent(...)");
                return new KtLightClassForDecompiledDeclaration(javaClsClass, parent, kotlinClsFile, ktClassOrObject);
            }
        });
    }

    private final <T> T createLightClassForDecompiledKotlinFile(Project project, KtClsFile ktClsFile, Function3<? super KtClsFile, ? super PsiClass, ? super KtClassOrObject, ? extends T> function3) {
        VirtualFile virtualFile = ktClsFile.getVirtualFile();
        if (virtualFile == null) {
            return null;
        }
        List<KtDeclaration> declarations = ktClsFile.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (T t : declarations) {
            if (t instanceof KtClassOrObject) {
                arrayList.add(t);
            }
        }
        KtClassOrObject ktClassOrObject = (KtClassOrObject) CollectionsKt.singleOrNull((List) arrayList);
        ClsClassImpl clsClassImplCreateClsJavaClassFromVirtualFile = createClsJavaClassFromVirtualFile(ktClsFile, virtualFile, ktClassOrObject, project);
        if (clsClassImplCreateClsJavaClassFromVirtualFile == null) {
            return null;
        }
        return function3.invoke(ktClsFile, clsClassImplCreateClsJavaClassFromVirtualFile, ktClassOrObject);
    }

    public final KtLightClassForFacade createLightFacadeForDecompiledKotlinFile(Project project, FqName facadeClassFqName, final List<? extends KtFile> files) {
        Object next;
        Intrinsics.checkNotNullParameter(project, "project");
        Intrinsics.checkNotNullParameter(facadeClassFqName, "facadeClassFqName");
        Intrinsics.checkNotNullParameter(files, "files");
        List<? extends KtFile> list = files;
        if (!(list instanceof Collection) || !list.isEmpty()) {
            Iterator<T> it = list.iterator();
            while (it.hasNext() && ((KtFile) it.next()).getIsCompiled()) {
            }
        }
        Iterator<T> it2 = list.iterator();
        do {
            if (!it2.hasNext()) {
                next = null;
                break;
            }
            next = it2.next();
        } while (!Intrinsics.areEqual(JvmFileClassUtilKt.getJavaFileFacadeFqName((KtFile) next), facadeClassFqName));
        KtClsFile ktClsFile = next instanceof KtClsFile ? (KtClsFile) next : null;
        if (ktClsFile == null) {
            StringBuilder sbAppend = new StringBuilder("Can't find the representative decompiled file for ").append(facadeClassFqName).append(" in ");
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it3 = list.iterator();
            while (it3.hasNext()) {
                arrayList.add(((KtFile) it3.next()).get$name());
            }
            throw new IllegalStateException(sbAppend.append(arrayList).toString().toString());
        }
        return (KtLightClassForFacade) createLightClassForDecompiledKotlinFile(project, ktClsFile, new Function3<KtClsFile, PsiClass, KtClassOrObject, KtLightClassForDecompiledFacade>() { // from class: org.jetbrains.kotlin.analysis.decompiled.light.classes.DecompiledLightClassesFactory.createLightFacadeForDecompiledKotlinFile.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final KtLightClassForDecompiledFacade invoke(KtClsFile kotlinClsFile, PsiClass javaClsClass, KtClassOrObject ktClassOrObject) {
                Intrinsics.checkNotNullParameter(kotlinClsFile, "kotlinClsFile");
                Intrinsics.checkNotNullParameter(javaClsClass, "javaClsClass");
                PsiElement parent = javaClsClass.getLightParent();
                Intrinsics.checkNotNullExpressionValue(parent, "getParent(...)");
                return new KtLightClassForDecompiledFacade(javaClsClass, parent, kotlinClsFile, ktClassOrObject, files);
            }
        });
    }

    public final ClsClassImpl createClsJavaClassFromVirtualFile(final KtFile mirrorFile, VirtualFile classFile, final KtClassOrObject correspondingClassOrObject, Project project) {
        Intrinsics.checkNotNullParameter(mirrorFile, "mirrorFile");
        Intrinsics.checkNotNullParameter(classFile, "classFile");
        Intrinsics.checkNotNullParameter(project, "project");
        final PsiJavaFileStubImpl psiJavaFileStubImpl = ClsJavaStubByVirtualFileCache.INSTANCE.getInstance(project).get(classFile);
        if (psiJavaFileStubImpl == null) {
            return null;
        }
        psiJavaFileStubImpl.setPsiFactory(ClsWrapperStubPsiFactory.INSTANCE);
        PsiManager psiManager = PsiManager.getInstance(mirrorFile.getProject());
        Intrinsics.checkNotNullExpressionValue(psiManager, "getInstance(...)");
        final ClassFileViewProvider classFileViewProvider = new ClassFileViewProvider(psiManager, classFile);
        ClsFileImpl clsFileImpl = new ClsFileImpl(psiJavaFileStubImpl, mirrorFile, classFileViewProvider) { // from class: org.jetbrains.kotlin.analysis.decompiled.light.classes.DecompiledLightClassesFactory$createClsJavaClassFromVirtualFile$fakeFile$1
            final /* synthetic */ PsiJavaFileStubImpl $javaFileStub;
            final /* synthetic */ KtFile $mirrorFile;

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
            /* JADX INFO: renamed from: isPhysical */
            public boolean getIsPhysical() {
                return false;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(classFileViewProvider);
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
            public PsiElement getNavigationElement() {
                KtClassOrObject ktClassOrObject = this.$correspondingClassOrObject;
                if (ktClassOrObject != null) {
                    PsiFile containingFile = ktClassOrObject.getNavigationElement().getContainingFile();
                    Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
                    return containingFile;
                }
                PsiElement navigationElement = super.getNavigationElement();
                Intrinsics.checkNotNullExpressionValue(navigationElement, "getNavigationElement(...)");
                return navigationElement;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl
            /* JADX INFO: renamed from: getStub, reason: from getter */
            public PsiJavaFileStubImpl get$javaFileStub() {
                return this.$javaFileStub;
            }

            @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiCompiledElement
            /* JADX INFO: renamed from: getMirror, reason: from getter */
            public KtFile get$mirrorFile() {
                return this.$mirrorFile;
            }
        };
        psiJavaFileStubImpl.setPsi(clsFileImpl);
        PsiClass[] classes = clsFileImpl.getClasses();
        Intrinsics.checkNotNullExpressionValue(classes, "getClasses(...)");
        Object objSingle = ArraysKt.single(classes);
        Intrinsics.checkNotNull(objSingle, "null cannot be cast to non-null type com.intellij.psi.impl.compiled.ClsClassImpl");
        return (ClsClassImpl) objSingle;
    }
}
