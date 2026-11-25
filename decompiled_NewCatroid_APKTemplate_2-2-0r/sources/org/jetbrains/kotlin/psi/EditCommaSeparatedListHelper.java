package org.jetbrains.kotlin.psi;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiComment;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.lexer.KtSingleValueToken;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: EditCommaSeparatedListHelper.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J?\u0010\u0003\u001a\u0002H\u0004\"\b\b\u0000\u0010\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00040\b2\u0006\u0010\t\u001a\u0002H\u00042\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007¢\u0006\u0002\u0010\fJI\u0010\r\u001a\u0002H\u0004\"\b\b\u0000\u0010\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00040\b2\u0006\u0010\t\u001a\u0002H\u00042\b\u0010\u000e\u001a\u0004\u0018\u0001H\u00042\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007¢\u0006\u0002\u0010\u000fJI\u0010\u0010\u001a\u0002H\u0004\"\b\b\u0000\u0010\u0004*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00040\b2\u0006\u0010\t\u001a\u0002H\u00042\b\u0010\u000e\u001a\u0004\u0018\u0001H\u00042\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007¢\u0006\u0002\u0010\u000fJ\u001d\u0010\u0011\u001a\u00020\u0012\"\b\b\u0000\u0010\u0004*\u00020\u00052\u0006\u0010\t\u001a\u0002H\u0004¢\u0006\u0002\u0010\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/psi/EditCommaSeparatedListHelper;", "", "()V", "addItem", "TItem", "Lorg/jetbrains/kotlin/psi/KtElement;", SchemaSymbols.ATTVAL_LIST, "allItems", "", "item", "prefix", "Lorg/jetbrains/kotlin/lexer/KtToken;", "(Lorg/jetbrains/kotlin/psi/KtElement;Ljava/util/List;Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/lexer/KtToken;)Lorg/jetbrains/kotlin/psi/KtElement;", "addItemAfter", "anchor", "(Lorg/jetbrains/kotlin/psi/KtElement;Ljava/util/List;Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/lexer/KtToken;)Lorg/jetbrains/kotlin/psi/KtElement;", "addItemBefore", "removeItem", "", "(Lorg/jetbrains/kotlin/psi/KtElement;)V", "psi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EditCommaSeparatedListHelper {
    public static final EditCommaSeparatedListHelper INSTANCE = new EditCommaSeparatedListHelper();

    public final <TItem extends KtElement> TItem addItem(KtElement list, List<? extends TItem> allItems, TItem item) {
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(allItems, "allItems");
        Intrinsics.checkNotNullParameter(item, "item");
        return (TItem) addItem$default(this, list, allItems, item, null, 8, null);
    }

    public final <TItem extends KtElement> TItem addItemAfter(KtElement list, List<? extends TItem> allItems, TItem item, TItem titem) {
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(allItems, "allItems");
        Intrinsics.checkNotNullParameter(item, "item");
        return (TItem) addItemAfter$default(this, list, allItems, item, titem, null, 16, null);
    }

    public final <TItem extends KtElement> TItem addItemBefore(KtElement list, List<? extends TItem> allItems, TItem item, TItem titem) {
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(allItems, "allItems");
        Intrinsics.checkNotNullParameter(item, "item");
        return (TItem) addItemBefore$default(this, list, allItems, item, titem, null, 16, null);
    }

    private EditCommaSeparatedListHelper() {
    }

    public static /* synthetic */ KtElement addItem$default(EditCommaSeparatedListHelper editCommaSeparatedListHelper, KtElement ktElement, List list, KtElement ktElement2, KtToken ktToken, int i, Object obj) {
        if ((i & 8) != 0) {
            KtSingleValueToken LPAR = KtTokens.LPAR;
            Intrinsics.checkNotNullExpressionValue(LPAR, "LPAR");
            ktToken = LPAR;
        }
        return editCommaSeparatedListHelper.addItem(ktElement, list, ktElement2, ktToken);
    }

    public final <TItem extends KtElement> TItem addItem(KtElement list, List<? extends TItem> allItems, TItem item, KtToken prefix) {
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(allItems, "allItems");
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        return (TItem) addItemBefore(list, allItems, item, null, prefix);
    }

    public static /* synthetic */ KtElement addItemAfter$default(EditCommaSeparatedListHelper editCommaSeparatedListHelper, KtElement ktElement, List list, KtElement ktElement2, KtElement ktElement3, KtToken ktToken, int i, Object obj) {
        if ((i & 16) != 0) {
            KtSingleValueToken LPAR = KtTokens.LPAR;
            Intrinsics.checkNotNullExpressionValue(LPAR, "LPAR");
            ktToken = LPAR;
        }
        return editCommaSeparatedListHelper.addItemAfter(ktElement, list, ktElement2, ktElement3, ktToken);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <TItem extends KtElement> TItem addItemAfter(KtElement list, List<? extends TItem> allItems, TItem item, TItem anchor, KtToken prefix) {
        ASTNode node;
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(allItems, "allItems");
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        if (anchor != null) {
            Intrinsics.areEqual(anchor.getMyParent(), list);
        }
        DefaultConstructorMarker elementType = 0;
        elementType = 0;
        if (allItems.isEmpty()) {
            PsiElement firstChild = list.getFirstChild();
            if (firstChild != null && (node = firstChild.getNode()) != null) {
                elementType = node.getElementType();
            }
            if (Intrinsics.areEqual(elementType, prefix)) {
                PsiElement psiElementAddAfter = list.addAfter(item, list.getFirstChild());
                Intrinsics.checkNotNull(psiElementAddAfter, "null cannot be cast to non-null type TItem of org.jetbrains.kotlin.psi.EditCommaSeparatedListHelper.addItemAfter");
                return (TItem) psiElementAddAfter;
            }
            PsiElement psiElementAdd = list.add(item);
            Intrinsics.checkNotNull(psiElementAdd, "null cannot be cast to non-null type TItem of org.jetbrains.kotlin.psi.EditCommaSeparatedListHelper.addItemAfter");
            return (TItem) psiElementAdd;
        }
        Project project = list.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        PsiElement psiElementCreateComma = new KtPsiFactory(project, false, 2, elementType).createComma();
        if (anchor != null) {
            PsiElement psiElementAddAfter2 = list.addAfter(psiElementCreateComma, anchor);
            Intrinsics.checkNotNullExpressionValue(psiElementAddAfter2, "addAfter(...)");
            PsiElement psiElementAddAfter3 = list.addAfter(item, psiElementAddAfter2);
            Intrinsics.checkNotNull(psiElementAddAfter3, "null cannot be cast to non-null type TItem of org.jetbrains.kotlin.psi.EditCommaSeparatedListHelper.addItemAfter");
            return (TItem) psiElementAddAfter3;
        }
        PsiElement psiElementAddBefore = list.addBefore(psiElementCreateComma, (PsiElement) CollectionsKt.first((List) allItems));
        Intrinsics.checkNotNullExpressionValue(psiElementAddBefore, "addBefore(...)");
        PsiElement psiElementAddBefore2 = list.addBefore(item, psiElementAddBefore);
        Intrinsics.checkNotNull(psiElementAddBefore2, "null cannot be cast to non-null type TItem of org.jetbrains.kotlin.psi.EditCommaSeparatedListHelper.addItemAfter");
        return (TItem) psiElementAddBefore2;
    }

    public static /* synthetic */ KtElement addItemBefore$default(EditCommaSeparatedListHelper editCommaSeparatedListHelper, KtElement ktElement, List list, KtElement ktElement2, KtElement ktElement3, KtToken ktToken, int i, Object obj) {
        if ((i & 16) != 0) {
            KtSingleValueToken LPAR = KtTokens.LPAR;
            Intrinsics.checkNotNullExpressionValue(LPAR, "LPAR");
            ktToken = LPAR;
        }
        return editCommaSeparatedListHelper.addItemBefore(ktElement, list, ktElement2, ktElement3, ktToken);
    }

    public final <TItem extends KtElement> TItem addItemBefore(KtElement list, List<? extends TItem> allItems, TItem item, TItem anchor, KtToken prefix) {
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(allItems, "allItems");
        Intrinsics.checkNotNullParameter(item, "item");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        TItem titem = null;
        if (!allItems.isEmpty()) {
            if (anchor != null) {
                int iIndexOf = allItems.indexOf(anchor);
                if (iIndexOf > 0) {
                    titem = allItems.get(iIndexOf - 1);
                }
            } else {
                titem = allItems.get(allItems.size() - 1);
            }
        }
        return (TItem) addItemAfter(list, allItems, item, titem, prefix);
    }

    public final <TItem extends KtElement> void removeItem(TItem item) {
        Object next;
        PsiElement next2;
        ASTNode node;
        ASTNode node2;
        Intrinsics.checkNotNullParameter(item, "item");
        TItem titem = item;
        PsiElement psiElement = null;
        Iterator it = PsiUtilsKt.siblings$default(titem, false, false, 1, null).iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            PsiElement psiElement2 = (PsiElement) next;
            if (!(psiElement2 instanceof PsiWhiteSpace) && !(psiElement2 instanceof PsiComment)) {
                break;
            }
        }
        PsiElement psiElement3 = (PsiElement) next;
        if (Intrinsics.areEqual((psiElement3 == null || (node2 = psiElement3.getNode()) == null) ? null : node2.getElementType(), KtTokens.COMMA)) {
            psiElement = psiElement3;
        } else {
            Iterator<PsiElement> it2 = PsiUtilsKt.siblings(titem, false, false).iterator();
            while (true) {
                if (!it2.hasNext()) {
                    next2 = null;
                    break;
                }
                next2 = it2.next();
                PsiElement psiElement4 = next2;
                if (!(psiElement4 instanceof PsiWhiteSpace) && !(psiElement4 instanceof PsiComment)) {
                    break;
                }
            }
            PsiElement psiElement5 = next2;
            if (Intrinsics.areEqual((psiElement5 == null || (node = psiElement5.getNode()) == null) ? null : node.getElementType(), KtTokens.COMMA)) {
                psiElement = psiElement5;
            }
        }
        item.delete();
        if (psiElement != null) {
            psiElement.delete();
        }
    }
}
