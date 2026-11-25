package org.jetbrains.kotlin.js.translate.intrinsic.operation;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.js.translate.context.TranslationContext;
import org.jetbrains.kotlin.js.translate.utils.BindingUtils;
import org.jetbrains.kotlin.js.translate.utils.PsiUtils;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: binaryOperationIntrinsics.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0011B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\n\u001a\u0004\u0018\u00010\t2\u0006\u0010\u000b\u001a\u00020\bH\u0002J\u0018\u0010\f\u001a\u0004\u0018\u00010\t2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/operation/BinaryOperationIntrinsics;", "", "()V", "factories", "", "Lorg/jetbrains/kotlin/js/translate/intrinsic/operation/BinaryOperationIntrinsicFactory;", "intrinsicCache", "", "Lorg/jetbrains/kotlin/js/translate/intrinsic/operation/BinaryOperationIntrinsics$IntrinsicKey;", "Lorg/jetbrains/kotlin/js/translate/intrinsic/operation/BinaryOperationIntrinsic;", "computeAndCache", ASTExpr.DEFAULT_MAP_KEY_NAME, "getIntrinsic", "expression", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "context", "Lorg/jetbrains/kotlin/js/translate/context/TranslationContext;", "IntrinsicKey", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BinaryOperationIntrinsics {
    private final Map<IntrinsicKey, BinaryOperationIntrinsic> intrinsicCache = new LinkedHashMap();
    private final List<BinaryOperationIntrinsicFactory> factories = CollectionsKt.listOf((Object[]) new BinaryOperationIntrinsicFactory[]{CompareToBOIF.INSTANCE, EqualsBOIF.INSTANCE});

    /* JADX INFO: compiled from: binaryOperationIntrinsics.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u0013\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\u0007HÆ\u0003J5\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/js/translate/intrinsic/operation/BinaryOperationIntrinsics$IntrinsicKey;", "", "token", "Lorg/jetbrains/kotlin/lexer/KtToken;", "function", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "leftType", "Lorg/jetbrains/kotlin/types/KotlinType;", "rightType", "(Lorg/jetbrains/kotlin/lexer/KtToken;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;)V", "getFunction", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "getLeftType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getRightType", "getToken", "()Lorg/jetbrains/kotlin/lexer/KtToken;", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "js.translator"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class IntrinsicKey {
        private final FunctionDescriptor function;
        private final KotlinType leftType;
        private final KotlinType rightType;
        private final KtToken token;

        public static /* synthetic */ IntrinsicKey copy$default(IntrinsicKey intrinsicKey, KtToken ktToken, FunctionDescriptor functionDescriptor, KotlinType kotlinType, KotlinType kotlinType2, int i, Object obj) {
            if ((i & 1) != 0) {
                ktToken = intrinsicKey.token;
            }
            if ((i & 2) != 0) {
                functionDescriptor = intrinsicKey.function;
            }
            if ((i & 4) != 0) {
                kotlinType = intrinsicKey.leftType;
            }
            if ((i & 8) != 0) {
                kotlinType2 = intrinsicKey.rightType;
            }
            return intrinsicKey.copy(ktToken, functionDescriptor, kotlinType, kotlinType2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final KtToken getToken() {
            return this.token;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final FunctionDescriptor getFunction() {
            return this.function;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final KotlinType getLeftType() {
            return this.leftType;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final KotlinType getRightType() {
            return this.rightType;
        }

        public final IntrinsicKey copy(KtToken token, FunctionDescriptor function, KotlinType leftType, KotlinType rightType) {
            Intrinsics.checkNotNullParameter(token, "token");
            Intrinsics.checkNotNullParameter(function, "function");
            return new IntrinsicKey(token, function, leftType, rightType);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof IntrinsicKey)) {
                return false;
            }
            IntrinsicKey intrinsicKey = (IntrinsicKey) other;
            return Intrinsics.areEqual(this.token, intrinsicKey.token) && Intrinsics.areEqual(this.function, intrinsicKey.function) && Intrinsics.areEqual(this.leftType, intrinsicKey.leftType) && Intrinsics.areEqual(this.rightType, intrinsicKey.rightType);
        }

        public int hashCode() {
            int iHashCode = ((this.token.hashCode() * 31) + this.function.hashCode()) * 31;
            KotlinType kotlinType = this.leftType;
            int iHashCode2 = (iHashCode + (kotlinType == null ? 0 : kotlinType.hashCode())) * 31;
            KotlinType kotlinType2 = this.rightType;
            return iHashCode2 + (kotlinType2 != null ? kotlinType2.hashCode() : 0);
        }

        public String toString() {
            return "IntrinsicKey(token=" + this.token + ", function=" + this.function + ", leftType=" + this.leftType + ", rightType=" + this.rightType + ')';
        }

        public IntrinsicKey(KtToken token, FunctionDescriptor function, KotlinType kotlinType, KotlinType kotlinType2) {
            Intrinsics.checkNotNullParameter(token, "token");
            Intrinsics.checkNotNullParameter(function, "function");
            this.token = token;
            this.function = function;
            this.leftType = kotlinType;
            this.rightType = kotlinType2;
        }

        public final KtToken getToken() {
            return this.token;
        }

        public final FunctionDescriptor getFunction() {
            return this.function;
        }

        public final KotlinType getLeftType() {
            return this.leftType;
        }

        public final KotlinType getRightType() {
            return this.rightType;
        }
    }

    public final BinaryOperationIntrinsic getIntrinsic(KtBinaryExpression expression, TranslationContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        KtBinaryExpression ktBinaryExpression = expression;
        CallableDescriptor callableDescriptorForOperationExpression = BindingUtils.getCallableDescriptorForOperationExpression(context.bindingContext(), ktBinaryExpression);
        FunctionDescriptor functionDescriptor = callableDescriptorForOperationExpression instanceof FunctionDescriptor ? (FunctionDescriptor) callableDescriptorForOperationExpression : null;
        if (functionDescriptor == null) {
            return null;
        }
        Pair<KotlinType, KotlinType> pairBinaryOperationTypes = BinaryOperationIntrinsicsKt.binaryOperationTypes(expression, context);
        KotlinType kotlinTypeComponent1 = pairBinaryOperationTypes.component1();
        KotlinType kotlinTypeComponent2 = pairBinaryOperationTypes.component2();
        KtToken operationToken = PsiUtils.getOperationToken(ktBinaryExpression);
        Intrinsics.checkNotNullExpressionValue(operationToken, "getOperationToken(...)");
        return computeAndCache(new IntrinsicKey(operationToken, functionDescriptor, kotlinTypeComponent1, kotlinTypeComponent2));
    }

    private final BinaryOperationIntrinsic computeAndCache(IntrinsicKey key) {
        BinaryOperationIntrinsic intrinsic;
        if (this.intrinsicCache.containsKey(key)) {
            return this.intrinsicCache.get(key);
        }
        Iterator<T> it = this.factories.iterator();
        do {
            intrinsic = null;
            if (!it.hasNext()) {
                break;
            }
            BinaryOperationIntrinsicFactory binaryOperationIntrinsicFactory = (BinaryOperationIntrinsicFactory) it.next();
            if (binaryOperationIntrinsicFactory.getSupportTokens().contains(key.getToken())) {
                intrinsic = binaryOperationIntrinsicFactory.getIntrinsic(key.getFunction(), key.getLeftType(), key.getRightType());
            }
        } while (intrinsic == null);
        this.intrinsicCache.put(key, intrinsic);
        return intrinsic;
    }
}
