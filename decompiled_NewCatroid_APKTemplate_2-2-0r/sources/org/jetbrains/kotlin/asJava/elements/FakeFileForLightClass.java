package org.jetbrains.kotlin.asJava.elements;

import java.lang.ref.Reference;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.asJava.classes.KtLightClass;
import org.jetbrains.kotlin.asJava.classes.KtLightClassForFacade;
import org.jetbrains.kotlin.asJava.classes.KtLightClassForSourceDeclaration;
import org.jetbrains.kotlin.com.intellij.lang.Language;
import org.jetbrains.kotlin.com.intellij.lang.java.JavaLanguage;
import org.jetbrains.kotlin.com.intellij.openapi.editor.Document;
import org.jetbrains.kotlin.com.intellij.openapi.fileEditor.FileDocumentManager;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.com.intellij.pom.java.LanguageLevel;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiFileFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.ClsStubPsiFactory;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.PsiJavaFileStub;
import org.jetbrains.kotlin.com.intellij.psi.impl.java.stubs.impl.PsiJavaFileStubImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.SourceTreeToPsiMap;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.TreeElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiUtil;
import org.jetbrains.kotlin.com.intellij.reference.SoftReference;
import org.jetbrains.kotlin.com.intellij.util.AstLoadingFilter;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtPackageDirective;

/* JADX INFO: compiled from: FakeFileForLightClass.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B'\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tB\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\nJ\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\u0013\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00060\u001cH\u0016¢\u0006\u0002\u0010\u001dJ\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020!H\u0016J\b\u0010\"\u001a\u00020\u0003H\u0016J\b\u0010#\u001a\u00020$H\u0016J\b\u0010%\u001a\u00020\u0017H\u0016J\b\u0010&\u001a\u00020'H\u0016J\b\u0010(\u001a\u00020)H\u0016J\u0012\u0010*\u001a\u00020\u00192\b\u0010+\u001a\u0004\u0018\u00010!H\u0016J\b\u0010,\u001a\u00020\u0019H\u0016J\u0010\u0010-\u001a\u00020\u00132\u0006\u0010.\u001a\u00020$H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\u0004\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/asJava/elements/FakeFileForLightClass;", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/compiled/ClsFileImpl;", "ktFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "lightClass", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/psi/KtFile;Lkotlin/jvm/functions/Function0;Lorg/jetbrains/kotlin/name/FqName;)V", "(Lorg/jetbrains/kotlin/psi/KtFile;Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;Lorg/jetbrains/kotlin/name/FqName;)V", "getKtFile", "()Lorg/jetbrains/kotlin/psi/KtFile;", "myMirrorFileElement", "Ljava/lang/ref/Reference;", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/source/tree/TreeElement;", "myMirrorLock", "", "accept", "", "visitor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElementVisitor;", "createFakeJavaFileStub", "Lorg/jetbrains/kotlin/com/intellij/psi/impl/java/stubs/PsiJavaFileStub;", Namer.EQUALS_METHOD_NAME, "", "other", "getClasses", "", "()[Lorg/jetbrains/kotlin/asJava/classes/KtLightClass;", "getLanguageLevel", "Lorg/jetbrains/kotlin/com/intellij/pom/java/LanguageLevel;", "getMirror", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "getNavigationElement", "getPackageName", "", "getStub", "getVirtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "hashCode", "", "isEquivalentTo", "another", "isPhysical", "setPackageName", "packageName", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FakeFileForLightClass extends ClsFileImpl {
    private final KtFile ktFile;
    private final KtLightClass lightClass;
    private volatile Reference<TreeElement> myMirrorFileElement;
    private final Object myMirrorLock;
    private final FqName packageFqName;

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl, org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    /* JADX INFO: renamed from: isPhysical */
    public boolean getIsPhysical() {
        return false;
    }

    public final KtFile getKtFile() {
        return this.ktFile;
    }

    public /* synthetic */ FakeFileForLightClass(KtFile ktFile, KtLightClass ktLightClass, FqName fqName, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktFile, ktLightClass, (i & 4) != 0 ? ktFile.getPackageFqName() : fqName);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FakeFileForLightClass(KtFile ktFile, KtLightClass lightClass, FqName packageFqName) {
        super(ktFile.getViewProvider());
        Intrinsics.checkNotNullParameter(ktFile, "ktFile");
        Intrinsics.checkNotNullParameter(lightClass, "lightClass");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        this.ktFile = ktFile;
        this.lightClass = lightClass;
        this.packageFqName = packageFqName;
        this.myMirrorLock = new Object();
    }

    public /* synthetic */ FakeFileForLightClass(KtFile ktFile, Function0 function0, FqName fqName, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktFile, (Function0<? extends KtLightClass>) function0, (i & 4) != 0 ? ktFile.getPackageFqName() : fqName);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @Deprecated(message = "A light class should be provided directly")
    public FakeFileForLightClass(KtFile ktFile, Function0<? extends KtLightClass> lightClass, FqName packageFqName) {
        this(ktFile, lightClass.invoke(), packageFqName);
        Intrinsics.checkNotNullParameter(ktFile, "ktFile");
        Intrinsics.checkNotNullParameter(lightClass, "lightClass");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.file.PsiBinaryFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiFile, org.jetbrains.kotlin.com.intellij.psi.PsiFileSystemItem
    public VirtualFile getVirtualFile() {
        VirtualFile virtualFile = this.ktFile.getVirtualFile();
        if (virtualFile == null) {
            virtualFile = this.ktFile.getOriginalFile().getVirtualFile();
        }
        if (virtualFile != null) {
            return virtualFile;
        }
        VirtualFile virtualFile2 = super.getVirtualFile();
        Intrinsics.checkNotNullExpressionValue(virtualFile2, "getVirtualFile(...)");
        return virtualFile2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner, org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public String getPackageName() {
        String strAsString = this.packageFqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    private final PsiJavaFileStub createFakeJavaFileStub() {
        PsiJavaFileStubImpl psiJavaFileStubImpl = new PsiJavaFileStubImpl(this.packageFqName.asString(), true);
        psiJavaFileStubImpl.setPsiFactory(ClsStubPsiFactory.INSTANCE);
        psiJavaFileStubImpl.setPsi(this);
        return psiJavaFileStubImpl;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl
    public PsiJavaFileStub getStub() {
        return createFakeJavaFileStub();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner
    public KtLightClass[] getClasses() {
        return new KtLightClass[]{this.lightClass};
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public KtFile getNavigationElement() {
        return this.ktFile;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiCompiledElement
    public PsiElement getMirror() {
        TreeElement treeElement;
        TreeElement treeElement2 = (TreeElement) SoftReference.dereference(this.myMirrorFileElement);
        if (treeElement2 != null) {
            PsiElement psi = treeElement2.getPsi();
            Intrinsics.checkNotNullExpressionValue(psi, "getPsi(...)");
            return psi;
        }
        synchronized (this.myMirrorLock) {
            treeElement = (TreeElement) SoftReference.dereference(this.myMirrorFileElement);
            if (treeElement == null) {
                VirtualFile virtualFile = getVirtualFile();
                AstLoadingFilter.assertTreeLoadingAllowed(virtualFile);
                KtLightClass[] classes = getClasses();
                String str = (!(classes.length == 0) ? classes[0].getName() : virtualFile.getNameWithoutExtension()) + SuffixConstants.SUFFIX_STRING_java;
                Document document = FileDocumentManager.getInstance().getDocument(virtualFile);
                if (document == null) {
                    String url = virtualFile.getUrl();
                    Intrinsics.checkNotNullExpressionValue(url, "getUrl(...)");
                    throw new IllegalStateException(url.toString());
                }
                Intrinsics.checkNotNull(document);
                PsiFile psiFileCreateFileFromText = PsiFileFactory.getInstance(getManager().getProject()).createFileFromText(str, (Language) JavaLanguage.INSTANCE, document.getImmutableCharSequence(), false, false, true);
                psiFileCreateFileFromText.putUserData(PsiUtil.FILE_LANGUAGE_LEVEL_KEY, getLanguageLevel());
                TreeElement treeElementPsiToTreeNotNull = SourceTreeToPsiMap.psiToTreeNotNull(psiFileCreateFileFromText);
                Intrinsics.checkNotNullExpressionValue(treeElementPsiToTreeNotNull, "psiToTreeNotNull(...)");
                if (psiFileCreateFileFromText instanceof PsiFileImpl) {
                    ((PsiFileImpl) psiFileCreateFileFromText).setOriginalFile(this);
                }
                this.myMirrorFileElement = new SoftReference(treeElementPsiToTreeNotNull);
                treeElement = treeElementPsiToTreeNotNull;
            }
        }
        Intrinsics.checkNotNullExpressionValue(treeElement, "synchronized(...)");
        PsiElement psi2 = treeElement.getPsi();
        Intrinsics.checkNotNullExpressionValue(psi2, "getPsi(...)");
        return psi2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    public LanguageLevel getLanguageLevel() {
        return LanguageLevel.JDK_1_8;
    }

    public int hashCode() {
        KtLightClass ktLightClass = this.lightClass;
        return ktLightClass instanceof KtLightClassForSourceDeclaration ? this.ktFile.hashCode() : ktLightClass.hashCode();
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FakeFileForLightClass)) {
            return false;
        }
        FakeFileForLightClass fakeFileForLightClass = (FakeFileForLightClass) other;
        KtLightClass ktLightClass = fakeFileForLightClass.lightClass;
        KtLightClass ktLightClass2 = this.lightClass;
        if (ktLightClass2 instanceof KtLightClassForSourceDeclaration) {
            return (ktLightClass instanceof KtLightClassForSourceDeclaration) && Intrinsics.areEqual(this.ktFile, fakeFileForLightClass.ktFile);
        }
        return Intrinsics.areEqual(ktLightClass2, ktLightClass);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public boolean isEquivalentTo(PsiElement another) {
        return Intrinsics.areEqual(this, another);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.compiled.ClsFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner
    public void setPackageName(String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        if (this.lightClass instanceof KtLightClassForFacade) {
            KtPackageDirective packageDirective = this.ktFile.getPackageDirective();
            if (packageDirective == null) {
                return;
            }
            packageDirective.setFqName(new FqName(packageName));
            return;
        }
        super.setPackageName(packageName);
    }
}
