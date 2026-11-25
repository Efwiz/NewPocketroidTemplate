package org.jetbrains.kotlin.psi.typeRefHelpers;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtFunctionType;
import org.jetbrains.kotlin.psi.KtFunctionTypeReceiver;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtParameterList;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;

/* JADX INFO: compiled from: TypeRefHelpers.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a$\u0010\u0004\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001\u001aq\u0010\b\u001a\u0004\u0018\u00010\u0001\"\b\b\u0000\u0010\t*\u00020\n*\u0002H\t2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00012\u0019\u0010\u000b\u001a\u0015\u0012\u0004\u0012\u0002H\t\u0012\u0006\u0012\u0004\u0018\u00010\u00010\f¢\u0006\u0002\b\r2,\u0010\u000e\u001a(\u0012\u0004\u0012\u0002H\t\u0012\u0013\u0012\u00110\u0001¢\u0006\f\b\u0010\u0012\b\b\u0011\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\u00010\u000f¢\u0006\u0002\b\rH\u0082\b¢\u0006\u0002\u0010\u0012\u001a\u0016\u0010\u0013\u001a\u0004\u0018\u00010\u0001*\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001\u001a\u0016\u0010\u0013\u001a\u0004\u0018\u00010\u0001*\u00020\u00142\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001¨\u0006\u0015"}, d2 = {"getTypeReference", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "declaration", "Lorg/jetbrains/kotlin/psi/KtCallableDeclaration;", "setTypeReference", "addAfter", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "typeRef", "doSetReceiverTypeReference", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtElement;", "getReceiverTypeReference", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "addReceiverTypeReference", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "(Lorg/jetbrains/kotlin/psi/KtElement;Lorg/jetbrains/kotlin/psi/KtTypeReference;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/psi/KtTypeReference;", "setReceiverTypeReference", "Lorg/jetbrains/kotlin/psi/KtFunctionType;", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeRefHelpersKt {
    public static final KtTypeReference getTypeReference(KtCallableDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        PsiElement firstChild = declaration.getFirstChild();
        Intrinsics.checkNotNull(firstChild);
        Object obj = null;
        for (Object obj2 : SequencesKt.dropWhile(PsiUtilsKt.siblings$default(firstChild, true, false, 2, null), new Function1<PsiElement, Boolean>() { // from class: org.jetbrains.kotlin.psi.typeRefHelpers.TypeRefHelpersKt.getTypeReference.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(PsiElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                ASTNode node = it.getNode();
                Intrinsics.checkNotNull(node);
                return Boolean.valueOf(!Intrinsics.areEqual(node.getElementType(), KtTokens.COLON));
            }
        })) {
            if (obj2 instanceof KtTypeReference) {
                obj = obj2;
                break;
            }
        }
        return (KtTypeReference) obj;
    }

    public static final KtTypeReference setTypeReference(KtCallableDeclaration declaration, PsiElement psiElement, KtTypeReference ktTypeReference) {
        Sequence sequenceSiblings$default;
        Object next;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        KtTypeReference typeReference = getTypeReference(declaration);
        DefaultConstructorMarker defaultConstructorMarker = null;
        if (ktTypeReference == null) {
            if (typeReference != null) {
                PsiWhiteSpace colon = declaration.getColon();
                Intrinsics.checkNotNull(colon);
                PsiElement prevSibling = colon.getPrevSibling();
                PsiWhiteSpace psiWhiteSpace = prevSibling instanceof PsiWhiteSpace ? (PsiWhiteSpace) prevSibling : null;
                if (psiWhiteSpace != null) {
                    colon = psiWhiteSpace;
                }
                declaration.deleteChildRange(colon, typeReference);
            }
            return null;
        }
        if (typeReference != null) {
            PsiElement psiElementReplace = typeReference.replace(ktTypeReference);
            Intrinsics.checkNotNull(psiElementReplace, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtTypeReference");
            return (KtTypeReference) psiElementReplace;
        }
        int i = 2;
        boolean z = false;
        if (psiElement == null) {
            PsiElement nameIdentifier = declaration.mo7392getNameIdentifier();
            if (nameIdentifier == null || (sequenceSiblings$default = PsiUtilsKt.siblings$default(nameIdentifier, true, false, 2, null)) == null) {
                psiElement = null;
            } else {
                Iterator it = sequenceSiblings$default.iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!(((PsiElement) next) instanceof PsiErrorElement));
                psiElement = (PsiElement) next;
            }
            if (psiElement == null) {
                KtParameter ktParameter = declaration instanceof KtParameter ? (KtParameter) declaration : null;
                psiElement = ktParameter != null ? ktParameter.getDestructuringDeclaration() : null;
            }
        }
        PsiElement psiElementAddAfter = declaration.addAfter(ktTypeReference, psiElement);
        Intrinsics.checkNotNull(psiElementAddAfter, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtTypeReference");
        KtTypeReference ktTypeReference2 = (KtTypeReference) psiElementAddAfter;
        Project project = declaration.getProject();
        Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
        declaration.addAfter(new KtPsiFactory(project, z, i, defaultConstructorMarker).createColon(), psiElement);
        return ktTypeReference2;
    }

    public static final KtTypeReference setReceiverTypeReference(KtCallableDeclaration ktCallableDeclaration, KtTypeReference ktTypeReference) {
        Object next;
        KtTypeReference ktTypeReference2;
        PsiElement next2;
        Intrinsics.checkNotNullParameter(ktCallableDeclaration, "<this>");
        KtCallableDeclaration ktCallableDeclaration2 = ktCallableDeclaration;
        boolean z = false;
        boolean z2 = (ktTypeReference == null || !(ktTypeReference.getTypeElement() instanceof KtFunctionType) || ktTypeReference.hasParentheses()) ? false : true;
        KtCallableDeclaration ktCallableDeclaration3 = ktCallableDeclaration2;
        KtTypeReference ktTypeReferenceMo7393getReceiverTypeReference = ktCallableDeclaration3.mo7393getReceiverTypeReference();
        int i = 2;
        DefaultConstructorMarker defaultConstructorMarker = null;
        if (ktTypeReference == null) {
            if (ktTypeReferenceMo7393getReceiverTypeReference == null) {
                return null;
            }
            PsiElement parent = ktTypeReferenceMo7393getReceiverTypeReference.getLightParent();
            KtFunctionTypeReceiver ktFunctionTypeReceiver = parent instanceof KtFunctionTypeReceiver ? (KtFunctionTypeReceiver) parent : null;
            PsiElement psiElement = ktFunctionTypeReceiver != null ? ktFunctionTypeReceiver : ktTypeReferenceMo7393getReceiverTypeReference;
            Iterator it = PsiUtilsKt.siblings$default(psiElement, true, false, 2, null).iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!Intrinsics.areEqual(((PsiElement) next).getNode().getElementType(), KtTokens.DOT));
            PsiElement psiElement2 = (PsiElement) next;
            if (psiElement2 == null) {
                psiElement2 = psiElement;
            }
            ktCallableDeclaration2.deleteChildRange(psiElement, psiElement2);
            return null;
        }
        if (ktTypeReferenceMo7393getReceiverTypeReference == null) {
            KtTypeReference ktTypeReference3 = ktTypeReference;
            KtParameterList nameIdentifier = ktCallableDeclaration3.mo7392getNameIdentifier();
            if (nameIdentifier == null) {
                nameIdentifier = ktCallableDeclaration3.getValueParameterList();
            }
            PsiElement psiElementAddBefore = ktCallableDeclaration3.addBefore(ktTypeReference3, nameIdentifier);
            Intrinsics.checkNotNull(psiElementAddBefore, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtTypeReference");
            ktTypeReference2 = (KtTypeReference) psiElementAddBefore;
            Project project = ktCallableDeclaration2.getProject();
            Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
            PsiElement psiElementCreateDot = new KtPsiFactory(project, z, i, defaultConstructorMarker).createDot();
            Iterator<PsiElement> it2 = PsiUtilsKt.getParentsWithSelf(ktTypeReference2).iterator();
            do {
                if (!it2.hasNext()) {
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
                next2 = it2.next();
            } while (!Intrinsics.areEqual(next2.getLightParent(), ktCallableDeclaration2));
            ktCallableDeclaration2.addAfter(psiElementCreateDot, next2);
        } else {
            PsiElement psiElementReplace = ktTypeReferenceMo7393getReceiverTypeReference.replace(ktTypeReference);
            Intrinsics.checkNotNull(psiElementReplace, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtTypeReference");
            ktTypeReference2 = (KtTypeReference) psiElementReplace;
        }
        if (z2) {
            Project project2 = ktCallableDeclaration2.getProject();
            Intrinsics.checkNotNullExpressionValue(project2, "getProject(...)");
            KtValueArgumentList ktValueArgumentListCreateCallArguments = new KtPsiFactory(project2, z, i, defaultConstructorMarker).createCallArguments("()");
            PsiElement leftParenthesis = ktValueArgumentListCreateCallArguments.getLeftParenthesis();
            Intrinsics.checkNotNull(leftParenthesis);
            ktTypeReference2.addBefore(leftParenthesis, ktTypeReference2.getFirstChild());
            PsiElement rightParenthesis = ktValueArgumentListCreateCallArguments.getRightParenthesis();
            Intrinsics.checkNotNull(rightParenthesis);
            ktTypeReference2.add(rightParenthesis);
        }
        return ktTypeReference2;
    }

    public static final KtTypeReference setReceiverTypeReference(KtFunctionType ktFunctionType, KtTypeReference ktTypeReference) {
        Object next;
        KtTypeReference typeReference;
        PsiElement next2;
        Intrinsics.checkNotNullParameter(ktFunctionType, "<this>");
        KtFunctionType ktFunctionType2 = ktFunctionType;
        boolean z = false;
        boolean z2 = (ktTypeReference == null || !(ktTypeReference.getTypeElement() instanceof KtFunctionType) || ktTypeReference.hasParentheses()) ? false : true;
        KtFunctionType ktFunctionType3 = ktFunctionType2;
        KtTypeReference receiverTypeReference = ktFunctionType3.getReceiverTypeReference();
        int i = 2;
        DefaultConstructorMarker defaultConstructorMarker = null;
        if (ktTypeReference == null) {
            if (receiverTypeReference == null) {
                return null;
            }
            PsiElement parent = receiverTypeReference.getLightParent();
            KtFunctionTypeReceiver ktFunctionTypeReceiver = parent instanceof KtFunctionTypeReceiver ? (KtFunctionTypeReceiver) parent : null;
            PsiElement psiElement = ktFunctionTypeReceiver != null ? ktFunctionTypeReceiver : receiverTypeReference;
            Iterator it = PsiUtilsKt.siblings$default(psiElement, true, false, 2, null).iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!Intrinsics.areEqual(((PsiElement) next).getNode().getElementType(), KtTokens.DOT));
            PsiElement psiElement2 = (PsiElement) next;
            if (psiElement2 == null) {
                psiElement2 = psiElement;
            }
            ktFunctionType2.deleteChildRange(psiElement, psiElement2);
            return null;
        }
        if (receiverTypeReference == null) {
            Project project = ktFunctionType3.getProject();
            Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
            KtFunctionTypeReceiver ktFunctionTypeReceiverCreateFunctionTypeReceiver = new KtPsiFactory(project, z, i, defaultConstructorMarker).createFunctionTypeReceiver(ktTypeReference);
            KtParameterList parameterList = ktFunctionType3.getParameterList();
            PsiElement psiElementAddBefore = ktFunctionType3.addBefore(ktFunctionTypeReceiverCreateFunctionTypeReceiver, parameterList != null ? parameterList : ktFunctionType3.getFirstChild());
            Intrinsics.checkNotNull(psiElementAddBefore, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtFunctionTypeReceiver");
            typeReference = ((KtFunctionTypeReceiver) psiElementAddBefore).getTypeReference();
            Intrinsics.checkNotNullExpressionValue(typeReference, "getTypeReference(...)");
            Project project2 = ktFunctionType2.getProject();
            Intrinsics.checkNotNullExpressionValue(project2, "getProject(...)");
            PsiElement psiElementCreateDot = new KtPsiFactory(project2, z, i, defaultConstructorMarker).createDot();
            Iterator<PsiElement> it2 = PsiUtilsKt.getParentsWithSelf(typeReference).iterator();
            do {
                if (!it2.hasNext()) {
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
                next2 = it2.next();
            } while (!Intrinsics.areEqual(next2.getLightParent(), ktFunctionType2));
            ktFunctionType2.addAfter(psiElementCreateDot, next2);
        } else {
            PsiElement psiElementReplace = receiverTypeReference.replace(ktTypeReference);
            Intrinsics.checkNotNull(psiElementReplace, "null cannot be cast to non-null type org.jetbrains.kotlin.psi.KtTypeReference");
            typeReference = (KtTypeReference) psiElementReplace;
        }
        if (z2) {
            Project project3 = ktFunctionType2.getProject();
            Intrinsics.checkNotNullExpressionValue(project3, "getProject(...)");
            KtValueArgumentList ktValueArgumentListCreateCallArguments = new KtPsiFactory(project3, z, i, defaultConstructorMarker).createCallArguments("()");
            PsiElement leftParenthesis = ktValueArgumentListCreateCallArguments.getLeftParenthesis();
            Intrinsics.checkNotNull(leftParenthesis);
            typeReference.addBefore(leftParenthesis, typeReference.getFirstChild());
            PsiElement rightParenthesis = ktValueArgumentListCreateCallArguments.getRightParenthesis();
            Intrinsics.checkNotNull(rightParenthesis);
            typeReference.add(rightParenthesis);
        }
        return typeReference;
    }
}
