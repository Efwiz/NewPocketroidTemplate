package org.jetbrains.kotlin.psi;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.KtNodeTypes;
import org.jetbrains.kotlin.com.intellij.extapi.psi.PsiFileBase;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.fileTypes.FileType;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFileWithId;
import org.jetbrains.kotlin.com.intellij.psi.FileViewProvider;
import org.jetbrains.kotlin.com.intellij.psi.PsiClass;
import org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifiableCodeBlock;
import org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement;
import org.jetbrains.kotlin.com.intellij.psi.stubs.StubElement;
import org.jetbrains.kotlin.com.intellij.psi.tree.TokenSet;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.ArrayFactory;
import org.jetbrains.kotlin.com.intellij.util.FileContentUtilCore;
import org.jetbrains.kotlin.com.intellij.util.IncorrectOperationException;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.idea.KotlinLanguage;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.parsing.KotlinParserDefinition;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.psi.stubs.KotlinFileStub;
import org.jetbrains.kotlin.psi.stubs.KotlinPlaceHolderStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtPlaceHolderStubElementType;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.stubs.elements.KtTokenSets;

/* JADX INFO: compiled from: KtFile.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000ì\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u0000 w2\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u00052\u00020\u00062\u00020\u0007:\u0001wB\u0015\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020EH\u0016J5\u0010B\u001a\u0002HF\"\u0004\b\u0000\u0010F\"\u0004\b\u0001\u0010G2\u0012\u0010D\u001a\u000e\u0012\u0004\u0012\u0002HF\u0012\u0004\u0012\u0002HG0H2\u0006\u0010I\u001a\u0002HGH\u0016¢\u0006\u0002\u0010JJ/\u0010K\u001a\u00020C\"\u0004\b\u0000\u0010G2\u0012\u0010D\u001a\u000e\u0012\u0004\u0012\u00020L\u0012\u0004\u0012\u0002HG0H2\u0006\u0010I\u001a\u0002HGH\u0016¢\u0006\u0002\u0010MJ\b\u0010N\u001a\u00020CH\u0016J\u0010\u0010O\u001a\u0004\u0018\u00010P2\u0006\u0010Q\u001a\u000201J?\u0010R\u001a\u0004\u0018\u0001HS\"\u0014\b\u0000\u0010S*\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030U0T2\f\u0010V\u001a\b\u0012\u0004\u0012\u0002HS0W2\f\u0010X\u001a\b\u0012\u0004\u0012\u0002HS0Y¢\u0006\u0002\u0010ZJE\u0010[\u001a\n\u0012\u0006\b\u0001\u0012\u0002HS0\u0013\"\u0014\b\u0000\u0010S*\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030U0T2\f\u0010V\u001a\b\u0012\u0004\u0012\u0002HS0W2\f\u0010X\u001a\b\u0012\u0004\u0012\u0002HS0Y¢\u0006\u0002\u0010\\J\u0010\u0010]\u001a\u0004\u0018\u00010\u001e2\u0006\u0010^\u001a\u00020:J\u000e\u0010_\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0016J\u000e\u0010`\u001a\b\u0012\u0004\u0012\u00020a0\u000eH\u0016J\u0013\u0010b\u001a\b\u0012\u0004\u0012\u00020c0\u0013H\u0016¢\u0006\u0002\u0010dJ\b\u0010e\u001a\u00020\u0000H\u0016J\u000e\u0010f\u001a\b\u0012\u0004\u0012\u00020g0\u000eH\u0016J\b\u0010h\u001a\u00020iH\u0016J\u0010\u0010j\u001a\u0004\u0018\u00010k2\u0006\u0010^\u001a\u00020kJ\b\u0010l\u001a\u00020:H\u0017J\b\u0010m\u001a\u00020\u0004H\u0016J\n\u0010n\u001a\u0004\u0018\u00010oH\u0016J\u0006\u0010\u001b\u001a\u00020\u000bJ\u0006\u0010(\u001a\u00020\u000bJ\u0010\u0010p\u001a\u00020q2\u0006\u0010^\u001a\u00020:H\u0016J\u0010\u0010r\u001a\u00020C2\u0006\u0010s\u001a\u00020:H\u0016J\u0010\u0010t\u001a\u00020\u000b2\u0006\u0010u\u001a\u00020qH\u0016J\b\u0010v\u001a\u00020:H\u0016R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0019\u0010\u0012\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00140\u00138F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\u0017\u001a\u0004\u0018\u00010\u00188F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u001cR\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0011R\u0013\u0010 \u001a\u0004\u0018\u00010!8F¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u001c\u0010$\u001a\n\u0012\u0006\b\u0001\u0012\u00020!0\u00138BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b%\u0010&R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010'R\u0012\u0010(\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u001cR\u0011\u0010)\u001a\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b)\u0010'R\u0013\u0010*\u001a\u0004\u0018\u00010+8F¢\u0006\u0006\u001a\u0004\b,\u0010-R\u0016\u0010.\u001a\u0004\u0018\u00010+8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b/\u0010-R$\u00102\u001a\u0002012\u0006\u00100\u001a\u0002018F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u0011\u00107\u001a\u0002018F¢\u0006\u0006\u001a\u0004\b8\u00104R\u0010\u00109\u001a\u0004\u0018\u00010:X\u0082\u000e¢\u0006\u0002\n\u0000R\u0013\u0010;\u001a\u0004\u0018\u00010<8F¢\u0006\u0006\u001a\u0004\b=\u0010>R\u0011\u0010?\u001a\u00020:8F¢\u0006\u0006\u001a\u0004\b@\u0010A¨\u0006x"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtFile;", "Lorg/jetbrains/kotlin/com/intellij/extapi/psi/PsiFileBase;", "Lorg/jetbrains/kotlin/psi/KtDeclarationContainer;", "Lorg/jetbrains/kotlin/psi/KtAnnotated;", "Lorg/jetbrains/kotlin/psi/KtElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClassOwner;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiNamedElement;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifiableCodeBlock;", "viewProvider", "Lorg/jetbrains/kotlin/com/intellij/psi/FileViewProvider;", "isCompiled", "", "(Lcom/intellij/psi/FileViewProvider;Z)V", "danglingAnnotations", "", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "getDanglingAnnotations", "()Ljava/util/List;", "danglingModifierLists", "", "Lorg/jetbrains/kotlin/psi/KtModifierList;", "getDanglingModifierLists", "()[Lorg/jetbrains/kotlin/psi/KtModifierList;", "fileAnnotationList", "Lorg/jetbrains/kotlin/psi/KtFileAnnotationList;", "getFileAnnotationList", "()Lorg/jetbrains/kotlin/psi/KtFileAnnotationList;", "hasTopLevelCallables", "Ljava/lang/Boolean;", "importDirectives", "Lorg/jetbrains/kotlin/psi/KtImportDirective;", "getImportDirectives", "importList", "Lorg/jetbrains/kotlin/psi/KtImportList;", "getImportList", "()Lorg/jetbrains/kotlin/psi/KtImportList;", "importLists", "getImportLists", "()[Lorg/jetbrains/kotlin/psi/KtImportList;", "()Z", "isScript", "isScriptByTree", "packageDirective", "Lorg/jetbrains/kotlin/psi/KtPackageDirective;", "getPackageDirective", "()Lorg/jetbrains/kotlin/psi/KtPackageDirective;", "packageDirectiveByTree", "getPackageDirectiveByTree", "value", "Lorg/jetbrains/kotlin/name/FqName;", "packageFqName", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "setPackageFqName", "(Lorg/jetbrains/kotlin/name/FqName;)V", "packageFqNameByTree", "getPackageFqNameByTree", "pathCached", "", "script", "Lorg/jetbrains/kotlin/psi/KtScript;", "getScript", "()Lorg/jetbrains/kotlin/psi/KtScript;", "virtualFilePath", "getVirtualFilePath", "()Ljava/lang/String;", "accept", "", "visitor", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElementVisitor;", "R", "D", "Lorg/jetbrains/kotlin/psi/KtVisitor;", "data", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "acceptChildren", "Ljava/lang/Void;", "(Lorg/jetbrains/kotlin/psi/KtVisitor;Ljava/lang/Object;)V", "clearCaches", "findAliasByFqName", "Lorg/jetbrains/kotlin/psi/KtImportAlias;", "fqName", "findChildByTypeOrClass", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtElementImplStub;", "Lorg/jetbrains/kotlin/com/intellij/psi/stubs/StubElement;", "elementType", "Lorg/jetbrains/kotlin/psi/stubs/elements/KtPlaceHolderStubElementType;", "elementClass", "Ljava/lang/Class;", "(Lorg/jetbrains/kotlin/psi/stubs/elements/KtPlaceHolderStubElementType;Ljava/lang/Class;)Lorg/jetbrains/kotlin/psi/KtElementImplStub;", "findChildrenByTypeOrClass", "(Lorg/jetbrains/kotlin/psi/stubs/elements/KtPlaceHolderStubElementType;Ljava/lang/Class;)[Lorg/jetbrains/kotlin/psi/KtElementImplStub;", "findImportByAlias", "name", "getAnnotationEntries", "getAnnotations", "Lorg/jetbrains/kotlin/psi/KtAnnotation;", "getClasses", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiClass;", "()[Lcom/intellij/psi/PsiClass;", "getContainingKtFile", "getDeclarations", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getFileType", "Lorg/jetbrains/kotlin/com/intellij/openapi/fileTypes/FileType;", "getNameForGivenImportAlias", "Lorg/jetbrains/kotlin/name/Name;", "getPackageName", "getPsiOrParent", "getStub", "Lorg/jetbrains/kotlin/psi/stubs/KotlinFileStub;", "setName", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "setPackageName", "packageName", "shouldChangeModificationCount", "place", "toString", "Companion", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtFile extends PsiFileBase implements PsiClassOwner, PsiModifiableCodeBlock, PsiNamedElement, KtAnnotated, KtDeclarationContainer, KtElement {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final TokenSet FILE_DECLARATION_TYPES;
    private volatile Boolean hasTopLevelCallables;
    private final boolean isCompiled;
    private volatile Boolean isScript;
    private volatile String pathCached;

    @Override // org.jetbrains.kotlin.psi.KtPureElement
    public KtFile getContainingKtFile() {
        return this;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner
    public void setPackageName(String packageName) {
        Intrinsics.checkNotNullParameter(packageName, "packageName");
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiModifiableCodeBlock
    public boolean shouldChangeModificationCount(PsiElement place) {
        Intrinsics.checkNotNullParameter(place, "place");
        return false;
    }

    /* JADX INFO: renamed from: isCompiled, reason: from getter */
    public final boolean getIsCompiled() {
        return this.isCompiled;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtFile(FileViewProvider viewProvider, boolean z) {
        super(viewProvider, KotlinLanguage.INSTANCE);
        Intrinsics.checkNotNullParameter(viewProvider, "viewProvider");
        this.isCompiled = z;
    }

    public final KtImportList getImportList() {
        return (KtImportList) ArraysKt.firstOrNull(getImportLists());
    }

    private final KtImportList[] getImportLists() {
        KtPlaceHolderStubElementType<KtImportList> IMPORT_LIST = KtStubElementTypes.IMPORT_LIST;
        Intrinsics.checkNotNullExpressionValue(IMPORT_LIST, "IMPORT_LIST");
        return (KtImportList[]) findChildrenByTypeOrClass(IMPORT_LIST, KtImportList.class);
    }

    public final KtFileAnnotationList getFileAnnotationList() {
        KtPlaceHolderStubElementType<KtFileAnnotationList> FILE_ANNOTATION_LIST = KtStubElementTypes.FILE_ANNOTATION_LIST;
        Intrinsics.checkNotNullExpressionValue(FILE_ANNOTATION_LIST, "FILE_ANNOTATION_LIST");
        return (KtFileAnnotationList) findChildByTypeOrClass(FILE_ANNOTATION_LIST, KtFileAnnotationList.class);
    }

    public List<KtImportDirective> getImportDirectives() {
        KtImportList[] importLists = getImportLists();
        ArrayList arrayList = new ArrayList();
        for (KtImportList ktImportList : importLists) {
            List<KtImportDirective> imports = ktImportList.getImports();
            Intrinsics.checkNotNullExpressionValue(imports, "getImports(...)");
            CollectionsKt.addAll(arrayList, imports);
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final KtPackageDirective getPackageDirective() {
        KotlinFileStub stub = getStub();
        if (stub != null) {
            KotlinPlaceHolderStub kotlinPlaceHolderStub = (KotlinPlaceHolderStub) stub.findChildStubByType(KtStubElementTypes.PACKAGE_DIRECTIVE);
            if (kotlinPlaceHolderStub != null) {
                return (KtPackageDirective) kotlinPlaceHolderStub.getPsi();
            }
            return null;
        }
        return getPackageDirectiveByTree();
    }

    private final KtPackageDirective getPackageDirectiveByTree() {
        ASTNode aSTNodeFindChildByType = getNode().findChildByType(KtNodeTypes.PACKAGE_DIRECTIVE);
        if (aSTNodeFindChildByType == null) {
            return null;
        }
        PsiElement psi = aSTNodeFindChildByType.getPsi();
        Intrinsics.checkNotNull(psi, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtPackageDirective");
        return (KtPackageDirective) psi;
    }

    public final FqName getPackageFqName() {
        FqName packageFqName;
        KotlinFileStub stub = getStub();
        return (stub == null || (packageFqName = stub.getPackageFqName()) == null) ? getPackageFqNameByTree() : packageFqName;
    }

    public final void setPackageFqName(FqName value) {
        Intrinsics.checkNotNullParameter(value, "value");
        KtPackageDirective packageDirective = getPackageDirective();
        if (packageDirective != null) {
            packageDirective.setFqName(value);
            return;
        }
        Project project = getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        KtPackageDirective ktPackageDirectiveCreatePackageDirectiveIfNeeded = new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null).createPackageDirectiveIfNeeded(value);
        if (ktPackageDirectiveCreatePackageDirectiveIfNeeded == null) {
            return;
        }
        addAfter(ktPackageDirectiveCreatePackageDirectiveIfNeeded, null);
    }

    public final FqName getPackageFqNameByTree() {
        KtPackageDirective packageDirectiveByTree = getPackageDirectiveByTree();
        FqName fqName = packageDirectiveByTree != null ? packageDirectiveByTree.getFqName() : null;
        if (fqName != null) {
            return fqName;
        }
        FqName ROOT = FqName.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
        return ROOT;
    }

    public final KtScript getScript() {
        Boolean bool = this.isScript;
        if (bool != null && !bool.booleanValue()) {
            return null;
        }
        KotlinFileStub stub = getStub();
        if (stub != null && !stub.isScript()) {
            return null;
        }
        KtScript ktScript = (KtScript) PsiTreeUtil.getChildOfType(this, KtScript.class);
        if (this.isScript == null) {
            this.isScript = Boolean.valueOf(ktScript != null);
        }
        return ktScript;
    }

    public final String getVirtualFilePath() {
        String str = this.pathCached;
        if (str != null) {
            return str;
        }
        String path = getVirtualFile().getPath();
        Intrinsics.checkNotNullExpressionValue(path, "getPath(...)");
        this.pathCached = path;
        return path;
    }

    public final boolean isScriptByTree() {
        return getScript() != null;
    }

    public final KtModifierList[] getDanglingModifierLists() {
        KotlinFileStub stub = getStub();
        KtDeclarationModifierList[] ktDeclarationModifierListArr = stub != null ? (KtDeclarationModifierList[]) stub.getChildrenByType(KtStubElementTypes.MODIFIER_LIST, KtStubElementTypes.MODIFIER_LIST.getArrayFactory()) : null;
        if (ktDeclarationModifierListArr != null) {
            return ktDeclarationModifierListArr;
        }
        Object[] objArrFindChildrenByClass = findChildrenByClass(KtModifierList.class);
        Intrinsics.checkNotNullExpressionValue(objArrFindChildrenByClass, "findChildrenByClass(...)");
        return (KtModifierList[]) objArrFindChildrenByClass;
    }

    public final List<KtAnnotationEntry> getDanglingAnnotations() {
        KtModifierList[] danglingModifierLists = getDanglingModifierLists();
        ArrayList arrayList = new ArrayList();
        for (KtModifierList ktModifierList : danglingModifierLists) {
            List<KtAnnotationEntry> annotationEntries = ktModifierList.getAnnotationEntries();
            Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
            CollectionsKt.addAll(arrayList, annotationEntries);
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public FileType getFileType() {
        KotlinFileType INSTANCE2 = KotlinFileType.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(INSTANCE2, "INSTANCE");
        return INSTANCE2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, java.util.concurrent.atomic.AtomicReference, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public String toString() {
        return "KtFile: " + getName();
    }

    @Override // org.jetbrains.kotlin.psi.KtDeclarationContainer
    public List<KtDeclaration> getDeclarations() {
        KtDeclaration[] ktDeclarationArr;
        List<KtDeclaration> list;
        KotlinFileStub stub = getStub();
        if (stub != null && (ktDeclarationArr = (KtDeclaration[]) stub.getChildrenByType(FILE_DECLARATION_TYPES, KtDeclaration.ARRAY_FACTORY)) != null && (list = ArraysKt.toList(ktDeclarationArr)) != null) {
            return list;
        }
        List<KtDeclaration> childrenOfTypeAsList = PsiTreeUtil.getChildrenOfTypeAsList(this, KtDeclaration.class);
        Intrinsics.checkNotNullExpressionValue(childrenOfTypeAsList, "getChildrenOfTypeAsList(...)");
        return childrenOfTypeAsList;
    }

    public final <T extends KtElementImplStub<? extends StubElement<?>>> T findChildByTypeOrClass(KtPlaceHolderStubElementType<T> elementType, Class<T> elementClass) {
        Intrinsics.checkNotNullParameter(elementType, "elementType");
        Intrinsics.checkNotNullParameter(elementClass, "elementClass");
        KotlinFileStub stub = getStub();
        if (stub != null) {
            KotlinPlaceHolderStub kotlinPlaceHolderStub = (KotlinPlaceHolderStub) stub.findChildStubByType(elementType);
            if (kotlinPlaceHolderStub != null) {
                return (T) kotlinPlaceHolderStub.getPsi();
            }
            return null;
        }
        return (T) findChildByClass(elementClass);
    }

    public final <T extends KtElementImplStub<? extends StubElement<?>>> T[] findChildrenByTypeOrClass(KtPlaceHolderStubElementType<T> elementType, Class<T> elementClass) {
        Intrinsics.checkNotNullParameter(elementType, "elementType");
        Intrinsics.checkNotNullParameter(elementClass, "elementClass");
        KotlinFileStub stub = getStub();
        if (stub != null) {
            ArrayFactory<T> arrayFactory = elementType.getArrayFactory();
            Intrinsics.checkNotNullExpressionValue(arrayFactory, "getArrayFactory(...)");
            PsiElement[] childrenByType = stub.getChildrenByType(elementType, arrayFactory);
            Intrinsics.checkNotNullExpressionValue(childrenByType, "getChildrenByType(...)");
            return (T[]) ((KtElementImplStub[]) childrenByType);
        }
        Object[] objArrFindChildrenByClass = findChildrenByClass(elementClass);
        Intrinsics.checkNotNullExpressionValue(objArrFindChildrenByClass, "findChildrenByClass(...)");
        return (T[]) ((KtElementImplStub[]) objArrFindChildrenByClass);
    }

    public final KtImportDirective findImportByAlias(String name2) {
        Object next;
        Intrinsics.checkNotNullParameter(name2, "name");
        Iterator<T> it = getImportDirectives().iterator();
        while (it.hasNext()) {
            next = it.next();
            if (Intrinsics.areEqual(name2, ((KtImportDirective) next).getAliasName())) {
                return (KtImportDirective) next;
            }
        }
        next = null;
        return (KtImportDirective) next;
    }

    public final KtImportAlias findAliasByFqName(FqName fqName) {
        Object next;
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Iterator<T> it = getImportDirectives().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            KtImportDirective ktImportDirective = (KtImportDirective) next;
            if (ktImportDirective.getAlias() != null && Intrinsics.areEqual(fqName, ktImportDirective.getImportedFqName())) {
                break;
            }
        }
        KtImportDirective ktImportDirective2 = (KtImportDirective) next;
        if (ktImportDirective2 != null) {
            return ktImportDirective2.getAlias();
        }
        return null;
    }

    public final Name getNameForGivenImportAlias(Name name2) {
        Object next;
        FqName importedFqName;
        List<Name> listPathSegments;
        Intrinsics.checkNotNullParameter(name2, "name");
        Iterator<T> it = getImportDirectives().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((KtImportDirective) next).getImportedName(), name2));
        KtImportDirective ktImportDirective = (KtImportDirective) next;
        if (ktImportDirective == null || (importedFqName = ktImportDirective.getImportedFqName()) == null || (listPathSegments = importedFqName.pathSegments()) == null) {
            return null;
        }
        return (Name) CollectionsKt.last((List) listPathSegments);
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner, org.jetbrains.kotlin.com.intellij.psi.PsiJavaFile
    @Deprecated(message = "")
    public String getPackageName() {
        String strAsString = getPackageFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl
    public KotlinFileStub getStub() {
        if (!(getVirtualFile() instanceof VirtualFileWithId)) {
            return null;
        }
        StubElement stub = super.getStub();
        if (stub == null ? true : stub instanceof KotlinFileStub) {
            return (KotlinFileStub) stub;
        }
        throw new IllegalStateException(("Illegal stub for KtFile: type=" + getClass() + ", stub=" + (stub != null ? stub.getClass() : null) + " name=" + getName()).toString());
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.PsiClassOwner
    public PsiClass[] getClasses() {
        PsiClass[] fileClasses;
        KtFileClassProvider ktFileClassProvider = (KtFileClassProvider) getProject().getService(KtFileClassProvider.class);
        if (ktFileClassProvider != null && (fileClasses = ktFileClassProvider.getFileClasses(this)) != null) {
            return fileClasses;
        }
        PsiClass[] EMPTY_ARRAY = PsiClass.EMPTY_ARRAY;
        Intrinsics.checkNotNullExpressionValue(EMPTY_ARRAY, "EMPTY_ARRAY");
        return EMPTY_ARRAY;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiFile
    public void clearCaches() {
        super.clearCaches();
        this.isScript = null;
        this.hasTopLevelCallables = null;
        this.pathCached = null;
    }

    public final boolean isScript() {
        Boolean boolValueOf = this.isScript;
        if (boolValueOf == null) {
            KotlinFileStub stub = getStub();
            boolValueOf = stub != null ? Boolean.valueOf(stub.isScript()) : null;
            if (boolValueOf == null) {
                return isScriptByTree();
            }
        }
        return boolValueOf.booleanValue();
    }

    public final boolean hasTopLevelCallables() {
        Boolean bool = this.hasTopLevelCallables;
        if (bool != null) {
            return bool.booleanValue();
        }
        List<KtDeclaration> declarations = getDeclarations();
        boolean z = false;
        if (!(declarations instanceof Collection) || !declarations.isEmpty()) {
            for (KtDeclaration ktDeclaration : declarations) {
                if ((ktDeclaration instanceof KtProperty) || (ktDeclaration instanceof KtNamedFunction) || (ktDeclaration instanceof KtScript) || (ktDeclaration instanceof KtTypeAlias)) {
                    if (!PsiUtilsKt.hasExpectModifier(ktDeclaration)) {
                        z = true;
                        break;
                    }
                }
            }
        }
        this.hasTopLevelCallables = Boolean.valueOf(z);
        return z;
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.PsiFileBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public void accept(PsiElementVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        if (visitor instanceof KtVisitor) {
            accept((KtVisitor) visitor, null);
        } else {
            visitor.visitFile(this);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElement
    public <D> void acceptChildren(KtVisitor<Void, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        KtPsiUtil.visitChildren(this, visitor, data);
    }

    @Override // org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitKtFile(this, data);
    }

    @Override // org.jetbrains.kotlin.psi.KtAnnotated
    public List<KtAnnotation> getAnnotations() {
        KtFileAnnotationList fileAnnotationList = getFileAnnotationList();
        List<KtAnnotation> annotations = fileAnnotationList != null ? fileAnnotationList.getAnnotations() : null;
        return annotations == null ? CollectionsKt.emptyList() : annotations;
    }

    @Override // org.jetbrains.kotlin.psi.KtAnnotated
    public List<KtAnnotationEntry> getAnnotationEntries() {
        KtFileAnnotationList fileAnnotationList = getFileAnnotationList();
        List<KtAnnotationEntry> annotationEntries = fileAnnotationList != null ? fileAnnotationList.getAnnotationEntries() : null;
        return annotationEntries == null ? CollectionsKt.emptyList() : annotationEntries;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.source.PsiFileImpl, org.jetbrains.kotlin.com.intellij.psi.PsiNamedElement, org.jetbrains.kotlin.com.intellij.psi.PsiMethod, org.jetbrains.kotlin.com.intellij.pom.PomRenameableTarget
    public PsiElement setName(String name2) throws IncorrectOperationException {
        Intrinsics.checkNotNullParameter(name2, "name");
        PsiElement name3 = super.setName(name2);
        if (isScript() != StringsKt.endsWith$default(name2, KotlinParserDefinition.STD_SCRIPT_EXT, false, 2, (Object) null)) {
            FileContentUtilCore.reparseFiles(CollectionsKt.listOfNotNull(getVirtualFile()));
        }
        Intrinsics.checkNotNull(name3);
        return name3;
    }

    @Override // org.jetbrains.kotlin.psi.KtPureElement
    public KtElement getPsiOrParent() {
        return this;
    }

    /* JADX INFO: compiled from: KtFile.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00070\u0004¢\u0006\u0002\b\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/psi/KtFile$Companion;", "", "()V", "FILE_DECLARATION_TYPES", "Lorg/jetbrains/kotlin/com/intellij/psi/tree/TokenSet;", "Lorg/jetbrains/annotations/NotNull;", "getFILE_DECLARATION_TYPES", "()Lcom/intellij/psi/tree/TokenSet;", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final TokenSet getFILE_DECLARATION_TYPES() {
            return KtFile.FILE_DECLARATION_TYPES;
        }
    }

    static {
        TokenSet tokenSetOrSet = TokenSet.orSet(KtTokenSets.DECLARATION_TYPES, TokenSet.create(KtStubElementTypes.SCRIPT));
        Intrinsics.checkNotNullExpressionValue(tokenSetOrSet, "orSet(...)");
        FILE_DECLARATION_TYPES = tokenSetOrSet;
    }
}
