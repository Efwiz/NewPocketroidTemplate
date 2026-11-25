package org.jetbrains.kotlin.psi.addRemoveModifier;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType;
import org.jetbrains.kotlin.lexer.KtModifierKeywordToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.psi.KtAnnotation;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtContextReceiverList;
import org.jetbrains.kotlin.psi.KtModifierList;
import org.jetbrains.kotlin.psi.KtModifierListOwner;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: addRemoveModifier.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\u001a\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\t\u001a\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0002H\u0000\u001a\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0002\u001a\u0018\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a\u0016\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0002\u001a\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\u001a\u0014\u0010\u0018\u001a\u00020\u0010*\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u0010H\u0002\u001a\u0012\u0010\u001a\u001a\u00020\u000e*\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u0010\"\u001f\u0010\u0000\u001a\u0010\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0005\"0\u0010\u0006\u001a$\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u0002\u0012\u0012\u0012\u0010\u0012\f\u0012\n \u0003*\u0004\u0018\u00010\u00020\u00020\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"MODIFIERS_ORDER", "", "Lorg/jetbrains/kotlin/lexer/KtModifierKeywordToken;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getMODIFIERS_ORDER", "()Ljava/util/List;", "MODIFIERS_TO_REPLACE", "", "addAnnotationEntry", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "owner", "Lorg/jetbrains/kotlin/psi/KtModifierListOwner;", "annotationEntry", "addModifier", "", "modifierList", "Lorg/jetbrains/kotlin/psi/KtModifierList;", "modifier", "createModifierList", "text", "", "removeModifier", "sortModifiers", "modifiers", "addModifierList", "newModifierList", "setModifierList", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AddRemoveModifierKt {
    private static final Map<KtModifierKeywordToken, List<KtModifierKeywordToken>> MODIFIERS_TO_REPLACE = MapsKt.mapOf(TuplesKt.to(KtTokens.OVERRIDE_KEYWORD, CollectionsKt.listOf(KtTokens.OPEN_KEYWORD)), TuplesKt.to(KtTokens.ABSTRACT_KEYWORD, CollectionsKt.listOf((Object[]) new KtModifierKeywordToken[]{KtTokens.OPEN_KEYWORD, KtTokens.FINAL_KEYWORD})), TuplesKt.to(KtTokens.OPEN_KEYWORD, CollectionsKt.listOf((Object[]) new KtModifierKeywordToken[]{KtTokens.FINAL_KEYWORD, KtTokens.ABSTRACT_KEYWORD})), TuplesKt.to(KtTokens.FINAL_KEYWORD, CollectionsKt.listOf((Object[]) new KtModifierKeywordToken[]{KtTokens.ABSTRACT_KEYWORD, KtTokens.OPEN_KEYWORD})), TuplesKt.to(KtTokens.PUBLIC_KEYWORD, CollectionsKt.listOf((Object[]) new KtModifierKeywordToken[]{KtTokens.PROTECTED_KEYWORD, KtTokens.PRIVATE_KEYWORD, KtTokens.INTERNAL_KEYWORD})), TuplesKt.to(KtTokens.PROTECTED_KEYWORD, CollectionsKt.listOf((Object[]) new KtModifierKeywordToken[]{KtTokens.PUBLIC_KEYWORD, KtTokens.PRIVATE_KEYWORD, KtTokens.INTERNAL_KEYWORD})), TuplesKt.to(KtTokens.PRIVATE_KEYWORD, CollectionsKt.listOf((Object[]) new KtModifierKeywordToken[]{KtTokens.PUBLIC_KEYWORD, KtTokens.PROTECTED_KEYWORD, KtTokens.INTERNAL_KEYWORD})), TuplesKt.to(KtTokens.INTERNAL_KEYWORD, CollectionsKt.listOf((Object[]) new KtModifierKeywordToken[]{KtTokens.PUBLIC_KEYWORD, KtTokens.PROTECTED_KEYWORD, KtTokens.PRIVATE_KEYWORD})), TuplesKt.to(KtTokens.HEADER_KEYWORD, CollectionsKt.listOf((Object[]) new KtModifierKeywordToken[]{KtTokens.IMPL_KEYWORD, KtTokens.ACTUAL_KEYWORD, KtTokens.EXPECT_KEYWORD})), TuplesKt.to(KtTokens.IMPL_KEYWORD, CollectionsKt.listOf((Object[]) new KtModifierKeywordToken[]{KtTokens.HEADER_KEYWORD, KtTokens.EXPECT_KEYWORD, KtTokens.ACTUAL_KEYWORD})), TuplesKt.to(KtTokens.EXPECT_KEYWORD, CollectionsKt.listOf((Object[]) new KtModifierKeywordToken[]{KtTokens.IMPL_KEYWORD, KtTokens.ACTUAL_KEYWORD, KtTokens.HEADER_KEYWORD})), TuplesKt.to(KtTokens.ACTUAL_KEYWORD, CollectionsKt.listOf((Object[]) new KtModifierKeywordToken[]{KtTokens.HEADER_KEYWORD, KtTokens.EXPECT_KEYWORD, KtTokens.IMPL_KEYWORD})));
    private static final List<KtModifierKeywordToken> MODIFIERS_ORDER = CollectionsKt.listOf((Object[]) new KtModifierKeywordToken[]{KtTokens.PUBLIC_KEYWORD, KtTokens.PROTECTED_KEYWORD, KtTokens.PRIVATE_KEYWORD, KtTokens.INTERNAL_KEYWORD, KtTokens.HEADER_KEYWORD, KtTokens.IMPL_KEYWORD, KtTokens.EXPECT_KEYWORD, KtTokens.ACTUAL_KEYWORD, KtTokens.FINAL_KEYWORD, KtTokens.OPEN_KEYWORD, KtTokens.ABSTRACT_KEYWORD, KtTokens.SEALED_KEYWORD, KtTokens.CONST_KEYWORD, KtTokens.EXTERNAL_KEYWORD, KtTokens.OVERRIDE_KEYWORD, KtTokens.LATEINIT_KEYWORD, KtTokens.TAILREC_KEYWORD, KtTokens.VARARG_KEYWORD, KtTokens.SUSPEND_KEYWORD, KtTokens.INNER_KEYWORD, KtTokens.ENUM_KEYWORD, KtTokens.ANNOTATION_KEYWORD, KtTokens.FUN_KEYWORD, KtTokens.COMPANION_KEYWORD, KtTokens.INLINE_KEYWORD, KtTokens.VALUE_KEYWORD, KtTokens.INFIX_KEYWORD, KtTokens.OPERATOR_KEYWORD, KtTokens.DATA_KEYWORD});

    private static final KtModifierList addModifierList(KtModifierListOwner ktModifierListOwner, KtModifierList ktModifierList) {
        PsiElement firstChild = ktModifierListOwner.getFirstChild();
        Intrinsics.checkNotNull(firstChild);
        PsiElement psiElementAddBefore = ktModifierListOwner.addBefore(ktModifierList, (PsiElement) SequencesKt.first(SequencesKt.dropWhile(PsiUtilsKt.siblings$default(firstChild, true, false, 2, null), new Function1<PsiElement, Boolean>() { // from class: org.jetbrains.kotlin.psi.addRemoveModifier.AddRemoveModifierKt$addModifierList$anchor$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(PsiElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf((it instanceof PsiComment) || (it instanceof PsiWhiteSpace) || (it instanceof KtContextReceiverList));
            }
        })));
        Intrinsics.checkNotNull(psiElementAddBefore, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtModifierList");
        return (KtModifierList) psiElementAddBefore;
    }

    private static final KtModifierList createModifierList(String str, KtModifierListOwner ktModifierListOwner) {
        Project project = ktModifierListOwner.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        return addModifierList(ktModifierListOwner, new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null).createModifierList(str));
    }

    public static final void setModifierList(KtModifierListOwner ktModifierListOwner, KtModifierList newModifierList) {
        Intrinsics.checkNotNullParameter(ktModifierListOwner, "<this>");
        Intrinsics.checkNotNullParameter(newModifierList, "newModifierList");
        KtModifierList modifierList = ktModifierListOwner.getModifierList();
        if (modifierList != null) {
            modifierList.replace(newModifierList);
        } else {
            addModifierList(ktModifierListOwner, newModifierList);
        }
    }

    public static final void addModifier(KtModifierListOwner owner, KtModifierKeywordToken modifier) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        KtModifierList modifierList = owner.getModifierList();
        if (modifierList == null) {
            String value = modifier.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
            createModifierList(value, owner);
            return;
        }
        addModifier(modifierList, modifier);
    }

    public static final KtAnnotationEntry addAnnotationEntry(KtModifierListOwner owner, KtAnnotationEntry annotationEntry) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(annotationEntry, "annotationEntry");
        KtModifierList modifierList = owner.getModifierList();
        if (modifierList == null) {
            String text = annotationEntry.getText();
            Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
            List<KtAnnotationEntry> annotationEntries = createModifierList(text, owner).getAnnotationEntries();
            Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
            Object objFirst = CollectionsKt.first((List<? extends Object>) annotationEntries);
            Intrinsics.checkNotNull(objFirst);
            return (KtAnnotationEntry) objFirst;
        }
        PsiElement psiElementAddBefore = modifierList.addBefore(annotationEntry, modifierList.getFirstChild());
        Intrinsics.checkNotNull(psiElementAddBefore, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtAnnotationEntry");
        return (KtAnnotationEntry) psiElementAddBefore;
    }

    public static final void addModifier(KtModifierList modifierList, KtModifierKeywordToken modifier) {
        PsiElement psiElement;
        PsiElement firstChild;
        Sequence sequenceSiblings$default;
        Object next;
        Intrinsics.checkNotNullParameter(modifierList, "modifierList");
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        if (modifierList.hasModifier(modifier)) {
            return;
        }
        Project project = modifierList.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        PsiElement psiElementCreateModifier = new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null).createModifier(modifier);
        List<KtModifierKeywordToken> list = MODIFIERS_TO_REPLACE.get(modifier);
        if (list != null) {
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                PsiElement modifier2 = modifierList.getModifier((KtModifierKeywordToken) it.next());
                if (modifier2 != null) {
                    arrayList.add(modifier2);
                }
            }
            psiElement = (PsiElement) CollectionsKt.firstOrNull((List) arrayList);
        } else {
            psiElement = null;
        }
        if (Intrinsics.areEqual(modifier, KtTokens.FINAL_KEYWORD) && !modifierList.hasModifier(KtTokens.OVERRIDE_KEYWORD)) {
            if (psiElement != null) {
                psiElement.delete();
                if (modifierList.getFirstChild() == null) {
                    modifierList.delete();
                    return;
                }
                return;
            }
            return;
        }
        if (psiElement != null && Intrinsics.areEqual(modifierList.getFirstChild(), modifierList.getLastChild())) {
            psiElement.replace(psiElementCreateModifier);
            return;
        }
        if (psiElement != null) {
            psiElement.delete();
        }
        int iIndexOf = MODIFIERS_ORDER.indexOf(modifier);
        PsiElement lastChild = modifierList.getLastChild();
        if (lastChild == null || (sequenceSiblings$default = PsiUtilsKt.siblings$default(lastChild, false, false, 2, null)) == null) {
            firstChild = null;
        } else {
            Iterator it2 = sequenceSiblings$default.iterator();
            do {
                if (!it2.hasNext()) {
                    next = null;
                    break;
                }
                next = it2.next();
            } while (!addModifier$placeAfter(iIndexOf, (PsiElement) next));
            firstChild = (PsiElement) next;
        }
        if (((firstChild != null ? firstChild.getNextSibling() : null) instanceof PsiWhiteSpace) && ((firstChild instanceof KtAnnotation) || (firstChild instanceof KtAnnotationEntry) || (firstChild instanceof PsiComment))) {
            firstChild = firstChild.getNextSibling();
        } else if (firstChild == null && (modifierList.getFirstChild() instanceof PsiWhiteSpace)) {
            firstChild = modifierList.getFirstChild();
        }
        modifierList.addAfter(psiElementCreateModifier, firstChild);
        if (Intrinsics.areEqual(firstChild, lastChild)) {
            PsiElement nextSibling = modifierList.getNextSibling();
            PsiWhiteSpace psiWhiteSpace = nextSibling instanceof PsiWhiteSpace ? (PsiWhiteSpace) nextSibling : null;
            if (psiWhiteSpace != null) {
                String text = psiWhiteSpace.getText();
                Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
                if (StringsKt.contains$default((CharSequence) text, '\n', false, 2, (Object) null)) {
                    modifierList.addAfter(psiWhiteSpace, firstChild);
                    psiWhiteSpace.delete();
                }
            }
        }
    }

    private static final boolean addModifier$placeAfter(int i, PsiElement psiElement) {
        if (psiElement instanceof PsiWhiteSpace) {
            return false;
        }
        if ((psiElement instanceof KtAnnotation) || (psiElement instanceof KtAnnotationEntry)) {
            return true;
        }
        ASTNode node = psiElement.getNode();
        Intrinsics.checkNotNull(node);
        IElementType elementType = node.getElementType();
        Intrinsics.checkNotNullExpressionValue(elementType, "getElementType(...)");
        return i > CollectionsKt.indexOf((List<? extends IElementType>) MODIFIERS_ORDER, elementType);
    }

    public static final void removeModifier(KtModifierListOwner owner, KtModifierKeywordToken modifier) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(modifier, "modifier");
        KtModifierList modifierList = owner.getModifierList();
        if (modifierList != null) {
            PsiElement modifier2 = modifierList.getModifier(modifier);
            if (modifier2 != null) {
                modifier2.delete();
            }
            if (modifierList.getFirstChild() == null) {
                modifierList.delete();
                return;
            }
            PsiElement lastChild = modifierList.getLastChild();
            if (lastChild instanceof PsiComment) {
                Project project = owner.getProject();
                Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                modifierList.addAfter(new KtPsiFactory(project, false, 2, (DefaultConstructorMarker) null).createNewLine(), lastChild);
            }
        }
    }

    public static final List<KtModifierKeywordToken> sortModifiers(List<KtModifierKeywordToken> modifiers) {
        Intrinsics.checkNotNullParameter(modifiers, "modifiers");
        return CollectionsKt.sortedWith(modifiers, new Comparator() { // from class: org.jetbrains.kotlin.psi.addRemoveModifier.AddRemoveModifierKt$sortModifiers$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                int iIndexOf = AddRemoveModifierKt.getMODIFIERS_ORDER().indexOf((KtModifierKeywordToken) t);
                if (iIndexOf == -1) {
                    iIndexOf = Integer.MAX_VALUE;
                }
                Integer numValueOf = Integer.valueOf(iIndexOf);
                int iIndexOf2 = AddRemoveModifierKt.getMODIFIERS_ORDER().indexOf((KtModifierKeywordToken) t2);
                return ComparisonsKt.compareValues(numValueOf, Integer.valueOf(iIndexOf2 != -1 ? iIndexOf2 : Integer.MAX_VALUE));
            }
        });
    }

    public static final List<KtModifierKeywordToken> getMODIFIERS_ORDER() {
        return MODIFIERS_ORDER;
    }
}
