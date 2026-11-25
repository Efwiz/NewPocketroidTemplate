package org.jetbrains.kotlin.asJava.classes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.asJava.elements.KtLightAbstractAnnotation;
import org.jetbrains.kotlin.asJava.elements.KtLightAnnotationForSourceEntry;
import org.jetbrains.kotlin.asJava.elements.KtLightField;
import org.jetbrains.kotlin.asJava.elements.KtLightMethod;
import org.jetbrains.kotlin.asJava.elements.KtUltraLightSimpleModifierList;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiModifierList;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;

/* JADX INFO: compiled from: ultraKtLightClassForFacade.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0000\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0018\u0010\u0007\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\b0\u0005¢\u0006\u0002\u0010\nJ\b\u0010\u001e\u001a\u00020\u001fH\u0016J\b\u0010 \u001a\u00020\u0013H\u0016J\u000e\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\fH\u0016J\u000e\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\fH\u0016J\n\u0010%\u001a\u0004\u0018\u00010&H\u0016J.\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\t2\u0006\u0010+\u001a\u00020\u00192\f\u0010,\u001a\b\u0012\u0004\u0012\u00020$0-H\u0002R#\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000fR\u001b\u0010\u0012\u001a\u00020\u00138BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0011\u001a\u0004\b\u0014\u0010\u0015R \u0010\u0007\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\b0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R3\u0010\u0017\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00190\u00180\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u0011\u001a\u0004\b\u001a\u0010\u000fR\u001c\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u000f¨\u0006."}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightClassForFacade;", "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForFacadeBase;", "facadeClassFqName", "Lorg/jetbrains/kotlin/name/FqName;", "files", "", "Lorg/jetbrains/kotlin/psi/KtFile;", "filesWithSupports", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/asJava/classes/KtUltraLightSupport;", "(Lorg/jetbrains/kotlin/name/FqName;Ljava/util/Collection;Ljava/util/Collection;)V", "_givenAnnotations", "", "Lorg/jetbrains/kotlin/asJava/elements/KtLightAbstractAnnotation;", "get_givenAnnotations", "()Ljava/util/List;", "_givenAnnotations$delegate", "Lkotlin/Lazy;", "_modifierListForSimpleFacade", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiModifierList;", "get_modifierListForSimpleFacade", "()Lcom/intellij/psi/PsiModifierList;", "_modifierListForSimpleFacade$delegate", "filesWithSupportsWithCreators", "Lkotlin/Triple;", "Lorg/jetbrains/kotlin/asJava/classes/UltraLightMembersCreator;", "getFilesWithSupportsWithCreators", "filesWithSupportsWithCreators$delegate", "givenAnnotations", "getGivenAnnotations", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "Lorg/jetbrains/kotlin/asJava/classes/KtLightClassForFacade;", "createModifierListForSimpleFacade", "createOwnFields", "Lorg/jetbrains/kotlin/asJava/elements/KtLightField;", "createOwnMethods", "Lorg/jetbrains/kotlin/asJava/elements/KtLightMethod;", "getScope", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "loadMethodsFromFile", "", "file", "support", "creator", "result", "", "light-classes"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtUltraLightClassForFacade extends KtLightClassForFacadeBase {

    /* JADX INFO: renamed from: _givenAnnotations$delegate, reason: from kotlin metadata */
    private final Lazy _givenAnnotations;

    /* JADX INFO: renamed from: _modifierListForSimpleFacade$delegate, reason: from kotlin metadata */
    private final Lazy _modifierListForSimpleFacade;
    private final Collection<Pair<KtFile, KtUltraLightSupport>> filesWithSupports;

    /* JADX INFO: renamed from: filesWithSupportsWithCreators$delegate, reason: from kotlin metadata */
    private final Lazy filesWithSupportsWithCreators;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public KtUltraLightClassForFacade(FqName facadeClassFqName, final Collection<? extends KtFile> files, Collection<? extends Pair<? extends KtFile, ? extends KtUltraLightSupport>> filesWithSupports) {
        super(facadeClassFqName, files);
        Intrinsics.checkNotNullParameter(facadeClassFqName, "facadeClassFqName");
        Intrinsics.checkNotNullParameter(files, "files");
        Intrinsics.checkNotNullParameter(filesWithSupports, "filesWithSupports");
        this.filesWithSupports = filesWithSupports;
        this._modifierListForSimpleFacade = ImplUtilsKt.lazyPub(new Function0<KtUltraLightSimpleModifierList>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForFacade$_modifierListForSimpleFacade$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KtUltraLightSimpleModifierList invoke() {
                return new KtUltraLightSimpleModifierList(this.this$0, SetsKt.setOf((Object[]) new String[]{"public", "final"}));
            }
        });
        this._givenAnnotations = ImplUtilsKt.lazyPub(new Function0<List<? extends KtLightAnnotationForSourceEntry>>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForFacade$_givenAnnotations$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends KtLightAnnotationForSourceEntry> invoke() {
                Collection<KtFile> collection = files;
                KtUltraLightClassForFacade ktUltraLightClassForFacade = this;
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = collection.iterator();
                while (it.hasNext()) {
                    List<KtAnnotationEntry> annotationEntries = ((KtFile) it.next()).getAnnotationEntries();
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(annotationEntries, 10));
                    for (final KtAnnotationEntry ktAnnotationEntry : annotationEntries) {
                        Name shortName = ktAnnotationEntry.getShortName();
                        arrayList2.add(new KtLightAnnotationForSourceEntry(shortName != null ? shortName.getIdentifier() : null, new Function0<String>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForFacade$_givenAnnotations$2$1$1$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            public final String invoke() {
                                FqName fqName;
                                AnnotationDescriptor annotationDescriptorAnalyzeAnnotation = UltraLightUtilsKt.analyzeAnnotation(ktAnnotationEntry);
                                if (annotationDescriptorAnalyzeAnnotation == null || (fqName = annotationDescriptorAnalyzeAnnotation.getFqName()) == null) {
                                    return null;
                                }
                                return fqName.asString();
                            }
                        }, ktAnnotationEntry, ktUltraLightClassForFacade.getModifierList()));
                    }
                    CollectionsKt.addAll(arrayList, arrayList2);
                }
                return arrayList;
            }
        });
        this.filesWithSupportsWithCreators = ImplUtilsKt.lazyPub(new Function0<List<? extends Triple<? extends KtFile, ? extends KtUltraLightSupport, ? extends UltraLightMembersCreator>>>() { // from class: org.jetbrains.kotlin.asJava.classes.KtUltraLightClassForFacade$filesWithSupportsWithCreators$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends Triple<? extends KtFile, ? extends KtUltraLightSupport, ? extends UltraLightMembersCreator>> invoke() {
                Collection<Pair> collection = this.this$0.filesWithSupports;
                KtUltraLightClassForFacade ktUltraLightClassForFacade = this.this$0;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
                for (Pair pair : collection) {
                    KtFile ktFile = (KtFile) pair.component1();
                    KtUltraLightSupport ktUltraLightSupport = (KtUltraLightSupport) pair.component2();
                    arrayList.add(new Triple(ktFile, ktUltraLightSupport, new UltraLightMembersCreator(ktUltraLightClassForFacade, false, true, false, ktUltraLightSupport)));
                }
                return arrayList;
            }
        });
    }

    private final PsiModifierList get_modifierListForSimpleFacade() {
        return (PsiModifierList) this._modifierListForSimpleFacade.getValue();
    }

    private final List<KtLightAbstractAnnotation> get_givenAnnotations() {
        return (List) this._givenAnnotations.getValue();
    }

    @Override // org.jetbrains.kotlin.asJava.elements.KtLightElement
    public List<KtLightAbstractAnnotation> getGivenAnnotations() {
        return getMultiFileClass() ? CollectionsKt.emptyList() : get_givenAnnotations();
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForFacadeBase
    public PsiModifierList createModifierListForSimpleFacade() {
        return get_modifierListForSimpleFacade();
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.PsiClass
    public PsiElement getScope() {
        return getLightParent();
    }

    private final List<Triple<KtFile, KtUltraLightSupport, UltraLightMembersCreator>> getFilesWithSupportsWithCreators() {
        return (List) this.filesWithSupportsWithCreators.getValue();
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0077  */
    private final void loadMethodsFromFile(KtFile file, KtUltraLightSupport support, UltraLightMembersCreator creator, List<KtLightMethod> result) {
        List listEmptyList;
        List listPropertyAccessors;
        List<KtDeclaration> declarations = file.getDeclarations();
        ArrayList<KtDeclaration> arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (!UltraLightUtilsKt.isHiddenByDeprecation((KtDeclaration) obj, support)) {
                arrayList.add(obj);
            }
        }
        for (KtDeclaration ktDeclaration : arrayList) {
            if (ktDeclaration instanceof KtNamedFunction) {
                listEmptyList = UltraLightMembersCreator.createMethods$default(creator, (KtFunction) ktDeclaration, true, false, false, null, 28, null);
            } else if (ktDeclaration instanceof KtProperty) {
                if (KtPsiUtilKt.isPrivate(ktDeclaration)) {
                    List<KtPropertyAccessor> accessors = ((KtProperty) ktDeclaration).getAccessors();
                    Intrinsics.checkNotNullExpressionValue(accessors, "getAccessors(...)");
                    if (accessors.isEmpty()) {
                        listPropertyAccessors = CollectionsKt.emptyList();
                    } else {
                        listPropertyAccessors = creator.propertyAccessors((KtCallableDeclaration) ktDeclaration, ((KtProperty) ktDeclaration).isVar(), true, false, (48 & 16) != 0 ? false : false, (48 & 32) != 0 ? false : false, (48 & 64) != 0 ? false : false, (48 & 128) != 0 ? null : null);
                    }
                } else {
                    listPropertyAccessors = creator.propertyAccessors((KtCallableDeclaration) ktDeclaration, ((KtProperty) ktDeclaration).isVar(), true, false, (48 & 16) != 0 ? false : false, (48 & 32) != 0 ? false : false, (48 & 64) != 0 ? false : false, (48 & 128) != 0 ? null : null);
                }
                listEmptyList = listPropertyAccessors;
            } else {
                listEmptyList = CollectionsKt.emptyList();
            }
            result.addAll(listEmptyList);
        }
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForFacadeBase
    public List<KtLightField> createOwnFields() {
        HashSet<String> hashSet = new HashSet<>();
        List<Triple<KtFile, KtUltraLightSupport, UltraLightMembersCreator>> filesWithSupportsWithCreators = getFilesWithSupportsWithCreators();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = filesWithSupportsWithCreators.iterator();
        while (it.hasNext()) {
            Triple triple = (Triple) it.next();
            KtFile ktFile = (KtFile) triple.component1();
            UltraLightMembersCreator ultraLightMembersCreator = (UltraLightMembersCreator) triple.component3();
            List<KtDeclaration> declarations = ktFile.getDeclarations();
            ArrayList arrayList2 = new ArrayList();
            for (Object obj : declarations) {
                if (obj instanceof KtProperty) {
                    arrayList2.add(obj);
                }
            }
            ArrayList arrayList3 = arrayList2;
            if (getMultiFileClass()) {
                ArrayList arrayList4 = new ArrayList();
                for (Object obj2 : arrayList3) {
                    if (((KtProperty) obj2).hasModifier(KtTokens.CONST_KEYWORD)) {
                        arrayList4.add(obj2);
                    }
                }
                arrayList3 = arrayList4;
            }
            ArrayList arrayList5 = new ArrayList();
            Iterator it2 = arrayList3.iterator();
            while (it2.hasNext()) {
                KtLightField ktLightFieldCreatePropertyField = ultraLightMembersCreator.createPropertyField((KtProperty) it2.next(), hashSet, true);
                if (ktLightFieldCreatePropertyField != null) {
                    arrayList5.add(ktLightFieldCreatePropertyField);
                }
            }
            CollectionsKt.addAll(arrayList, arrayList5);
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KtLightClassForFacadeBase
    public List<KtLightMethod> createOwnMethods() {
        ArrayList arrayList = new ArrayList();
        for (Triple<KtFile, KtUltraLightSupport, UltraLightMembersCreator> triple : getFilesWithSupportsWithCreators()) {
            loadMethodsFromFile(triple.component1(), triple.component2(), triple.component3(), arrayList);
        }
        if (!getMultiFileClass()) {
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (!((KtLightMethod) obj).hasModifierProperty("private")) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    @Override // org.jetbrains.kotlin.com.intellij.psi.impl.light.AbstractLightClass, org.jetbrains.kotlin.com.intellij.psi.impl.light.LightElement, org.jetbrains.kotlin.com.intellij.psi.impl.PsiElementBase, org.jetbrains.kotlin.com.intellij.psi.PsiElement
    public KtLightClassForFacade copy() {
        return new KtUltraLightClassForFacade(getFacadeClassFqName(), getFiles(), this.filesWithSupports);
    }
}
