package org.jetbrains.kotlin.js.translate.expression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsBlock;
import org.jetbrains.kotlin.js.backend.ast.JsCatch;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsIf;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNullLiteral;
import org.jetbrains.kotlin.js.backend.ast.JsScope;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.backend.ast.JsThrow;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.general.AbstractTranslator;
import org.jetbrains.kotlin.js.translate.general.Translation;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.JsAstUtils;
import org.jetbrains.kotlin.psi.KtCatchClause;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.types.DynamicTypesKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: CatchTranslator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0000\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u0004H\u0002J&\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u001bH\u0002R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\u00020\u000f*\u00020\u00108BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0011¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/expression/CatchTranslator;", "Lorg/jetbrains/kotlin/js/translate/general/AbstractTranslator;", "catches", "", "Lorg/jetbrains/kotlin/psi/KtCatchClause;", "psi", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "(Ljava/util/List;Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;)V", "getCatches", "()Ljava/util/List;", "getPsi", "()Lcom/intellij/psi/PsiElement;", "isDynamic", "", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "(Lorg/jetbrains/kotlin/psi/KtTypeReference;)Z", "translate", "Lorg/jetbrains/kotlin/js/backend/ast/JsCatch;", "translateCatchBody", "Lorg/jetbrains/kotlin/js/backend/ast/JsBlock;", "catchClause", "translateCatches", "Lorg/jetbrains/kotlin/js/backend/ast/JsStatement;", "initialCatchParameterRef", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CatchTranslator extends AbstractTranslator {
    private final List<KtCatchClause> catches;
    private final PsiElement psi;

    public final List<KtCatchClause> getCatches() {
        return this.catches;
    }

    public final PsiElement getPsi() {
        return this.psi;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public CatchTranslator(List<? extends KtCatchClause> catches, PsiElement psi, TranslationContext context) {
        super(context);
        Intrinsics.checkNotNullParameter(catches, "catches");
        Intrinsics.checkNotNullParameter(psi, "psi");
        Intrinsics.checkNotNullParameter(context, "context");
        this.catches = catches;
        this.psi = psi;
    }

    public final JsCatch translate() {
        if (this.catches.isEmpty()) {
            return null;
        }
        KtParameter catchParameter = ((KtCatchClause) CollectionsKt.first((List) this.catches)).getCatchParameter();
        BindingContext bindingContext = bindingContext();
        Intrinsics.checkNotNull(catchParameter);
        DeclarationDescriptor descriptorForElement = BindingUtils.getDescriptorForElement(bindingContext, catchParameter);
        Intrinsics.checkNotNullExpressionValue(descriptorForElement, "getDescriptorForElement(...)");
        String ident = context().getNameForDescriptor(descriptorForElement).getIdent();
        Intrinsics.checkNotNullExpressionValue(ident, "getIdent(...)");
        JsCatch jsCatch = new JsCatch(context().scope(), ident);
        JsNameRef jsNameRefMakeRef = jsCatch.getParameter().getName().makeRef();
        Intrinsics.checkNotNullExpressionValue(jsNameRefMakeRef, "makeRef(...)");
        TranslationContext translationContextInnerContextWithAliased = context().innerContextWithAliased(descriptorForElement, jsNameRefMakeRef);
        Intrinsics.checkNotNullExpressionValue(translationContextInnerContextWithAliased, "innerContextWithAliased(...)");
        jsCatch.setBody(new JsBlock(translateCatches(translationContextInnerContextWithAliased, jsNameRefMakeRef, this.catches.iterator())));
        return jsCatch;
    }

    private final JsStatement translateCatches(TranslationContext context, JsNameRef initialCatchParameterRef, Iterator<? extends KtCatchClause> catches) {
        JsNameRef jsNameRefPureFqn;
        if (!catches.hasNext()) {
            JsThrow jsThrow = new JsThrow(initialCatchParameterRef);
            jsThrow.setSource(this.psi);
            return jsThrow;
        }
        KtCatchClause next = catches.next();
        KtParameter catchParameter = next.getCatchParameter();
        Intrinsics.checkNotNull(catchParameter);
        BindingContext bindingContext = bindingContext();
        KtParameter catchParameter2 = next.getCatchParameter();
        Intrinsics.checkNotNull(catchParameter2);
        DeclarationDescriptor descriptorForElement = BindingUtils.getDescriptorForElement(bindingContext, catchParameter2);
        Intrinsics.checkNotNullExpressionValue(descriptorForElement, "getDescriptorForElement(...)");
        JsName nameForDescriptor = context().getNameForDescriptor(descriptorForElement);
        Intrinsics.checkNotNullExpressionValue(nameForDescriptor, "getNameForDescriptor(...)");
        KtTypeReference ktTypeReferenceMo7396getTypeReference = catchParameter.mo7396getTypeReference();
        Intrinsics.checkNotNull(ktTypeReferenceMo7396getTypeReference);
        ArrayList arrayList = new ArrayList();
        if (Intrinsics.areEqual(nameForDescriptor.getIdent(), initialCatchParameterRef.getIdent())) {
            jsNameRefPureFqn = initialCatchParameterRef;
        } else {
            JsName jsNameDeclareTemporaryName = JsScope.declareTemporaryName(nameForDescriptor.getIdent());
            Intrinsics.checkNotNullExpressionValue(jsNameDeclareTemporaryName, "declareTemporaryName(...)");
            arrayList.add(JsAstUtils.newVar(jsNameDeclareTemporaryName, initialCatchParameterRef));
            jsNameRefPureFqn = JsAstUtils.pureFqn(jsNameDeclareTemporaryName, (JsExpression) null);
            Intrinsics.checkNotNullExpressionValue(jsNameRefPureFqn, "pureFqn(...)");
        }
        TranslationContext translationContextInnerContextWithAliased = context.innerContextWithAliased(descriptorForElement, jsNameRefPureFqn);
        Intrinsics.checkNotNullExpressionValue(translationContextInnerContextWithAliased, "innerContextWithAliased(...)");
        JsBlock jsBlockTranslateCatchBody = translateCatchBody(translationContextInnerContextWithAliased, next);
        jsBlockTranslateCatchBody.getStatements().addAll(0, arrayList);
        if (isDynamic(ktTypeReferenceMo7396getTypeReference)) {
            return jsBlockTranslateCatchBody;
        }
        JsExpression jsExpressionTranslateIsCheck = Translation.patternTranslator(translationContextInnerContextWithAliased).translateIsCheck(initialCatchParameterRef, ktTypeReferenceMo7396getTypeReference);
        Intrinsics.checkNotNull(jsExpressionTranslateIsCheck);
        JsIf jsIf = new JsIf(jsExpressionTranslateIsCheck.source((Object) next), jsBlockTranslateCatchBody, translateCatches(context, initialCatchParameterRef, catches));
        jsIf.setSource(next);
        return jsIf;
    }

    private final JsBlock translateCatchBody(TranslationContext context, KtCatchClause catchClause) {
        JsStatement jsStatementAsSyntheticStatement;
        KtExpression catchBody = catchClause.getCatchBody();
        if (catchBody != null) {
            jsStatementAsSyntheticStatement = Translation.translateAsStatementAndMergeInBlockIfNeeded(catchBody, context);
        } else {
            jsStatementAsSyntheticStatement = JsAstUtils.asSyntheticStatement(new JsNullLiteral());
        }
        JsBlock jsBlockConvertToBlock = JsAstUtils.convertToBlock(jsStatementAsSyntheticStatement);
        Intrinsics.checkNotNullExpressionValue(jsBlockConvertToBlock, "convertToBlock(...)");
        return jsBlockConvertToBlock;
    }

    private final boolean isDynamic(KtTypeReference ktTypeReference) {
        Object notNull = BindingContextUtils.getNotNull(bindingContext(), BindingContext.TYPE, ktTypeReference);
        Intrinsics.checkNotNullExpressionValue(notNull, "getNotNull(...)");
        return DynamicTypesKt.isDynamic((KotlinType) notNull);
    }
}
