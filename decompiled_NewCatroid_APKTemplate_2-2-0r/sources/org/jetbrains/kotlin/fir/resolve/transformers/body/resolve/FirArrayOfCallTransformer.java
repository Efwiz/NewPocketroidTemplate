package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceElementKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.diagnostics.FirDiagnosticHolder;
import org.jetbrains.kotlin.fir.expressions.FirArrayLiteral;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirExpressionUtilKt;
import org.jetbrains.kotlin.fir.expressions.FirFunctionCall;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.expressions.FirVarargArgumentsExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirArgumentListBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirArrayLiteralBuilder;
import org.jetbrains.kotlin.fir.references.FirReference;
import org.jetbrains.kotlin.fir.references.FirReferenceUtilsKt;
import org.jetbrains.kotlin.fir.references.FirResolvedNamedReference;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer;

/* JADX INFO: compiled from: FirArrayOfCallTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00112\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0011B\u0005¢\u0006\u0002\u0010\u0003J\u001a\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0002H\u0002J'\u0010\t\u001a\u0002H\n\"\b\b\u0000\u0010\n*\u00020\u000b2\u0006\u0010\f\u001a\u0002H\n2\u0006\u0010\r\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010\u000eJ\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0002H\u0016¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirArrayOfCallTransformer;", "Lorg/jetbrains/kotlin/fir/visitors/FirDefaultTransformer;", "Lorg/jetbrains/kotlin/fir/FirSession;", "()V", "toArrayLiteral", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "functionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "session", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "data", "(Lorg/jetbrains/kotlin/fir/FirElement;Lorg/jetbrains/kotlin/fir/FirSession;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformFunctionCall", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "Companion", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirArrayOfCallTransformer extends FirDefaultTransformer<FirSession> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Set<String> arrayOfNames;

    private final FirExpression toArrayLiteral(FirFunctionCall functionCall, FirSession session) {
        if (!INSTANCE.isArrayOfCall(functionCall, session) || !(functionCall.getCalleeReference() instanceof FirResolvedNamedReference)) {
            return null;
        }
        FirArrayLiteralBuilder firArrayLiteralBuilder = new FirArrayLiteralBuilder();
        firArrayLiteralBuilder.setSource(functionCall.getSource());
        CollectionsKt.addAll(firArrayLiteralBuilder.getAnnotations(), functionCall.getAnnotations());
        FirArgumentListBuilder firArgumentListBuilder = new FirArgumentListBuilder();
        FirFunctionCall firFunctionCall = functionCall;
        if (!firFunctionCall.getArgumentList().getArguments().isEmpty()) {
            List<FirExpression> arguments = firFunctionCall.getArgumentList().getArguments();
            List<FirExpression> arguments2 = firArgumentListBuilder.getArguments();
            for (FirExpression firExpression : arguments) {
                CollectionsKt.addAll(arguments2, firExpression instanceof FirVarargArgumentsExpression ? ((FirVarargArgumentsExpression) firExpression).getArguments() : CollectionsKt.listOf(firExpression));
            }
        }
        firArrayLiteralBuilder.setArgumentList(firArgumentListBuilder.build());
        firArrayLiteralBuilder.setTypeRef(functionCall.getTypeRef());
        FirArrayLiteral firArrayLiteralMo6809build = firArrayLiteralBuilder.mo6809build();
        Object calleeReference = functionCall.getCalleeReference();
        if (FirReferenceUtilsKt.isError((FirReference) calleeReference)) {
            KtSourceElement source = functionCall.getSource();
            return FirExpressionUtilKt.buildErrorExpression(source != null ? KtSourceElementKt.fakeElement$default(source, KtFakeSourceElementKind.ErrorTypeRef.INSTANCE, 0, 0, 6, null) : null, ((FirDiagnosticHolder) calleeReference).getDiagnostic(), firArrayLiteralMo6809build);
        }
        return firArrayLiteralMo6809build;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformFunctionCall(FirFunctionCall functionCall, FirSession data) {
        Intrinsics.checkNotNullParameter(functionCall, "functionCall");
        Intrinsics.checkNotNullParameter(data, "data");
        functionCall.transformChildren(this, data);
        FirExpression arrayLiteral = toArrayLiteral(functionCall, data);
        return arrayLiteral != null ? arrayLiteral : functionCall;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <E extends FirElement> E transformElement(E element, FirSession data) {
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(data, "data");
        E e = (E) element.transformChildren(this, data);
        Intrinsics.checkNotNull(e, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirArrayOfCallTransformer.transformElement");
        return e;
    }

    /* JADX INFO: compiled from: FirArrayOfCallTransformer.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0002J\u0012\u0010\r\u001a\u00020\u0007*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirArrayOfCallTransformer$Companion;", "", "()V", "arrayOfNames", "", "", "isArrayOf", "", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "arguments", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "isArrayOfCall", "Lorg/jetbrains/kotlin/fir/expressions/FirFunctionCall;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean isArrayOfCall(FirFunctionCall firFunctionCall, FirSession session) {
            ConeKotlinType coneKotlinTypeFullyExpandedType;
            Intrinsics.checkNotNullParameter(firFunctionCall, "<this>");
            Intrinsics.checkNotNullParameter(session, "session");
            FirCallableDeclaration originalFunction = FirArrayOfCallTransformerKt.getOriginalFunction(firFunctionCall);
            if (originalFunction == null) {
                return false;
            }
            FirTypeRef returnTypeRef = originalFunction.getReturnTypeRef();
            if (!(originalFunction instanceof FirSimpleFunction)) {
                return false;
            }
            FirResolvedTypeRef firResolvedTypeRef = returnTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) returnTypeRef : null;
            ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
            ConeKotlinType coneKotlinType = type instanceof ConeKotlinType ? type : null;
            return coneKotlinType != null && (coneKotlinTypeFullyExpandedType = TypeExpansionUtilsKt.fullyExpandedType(coneKotlinType, session)) != null && ConeBuiltinTypeUtilsKt.isArrayType(coneKotlinTypeFullyExpandedType) && isArrayOf((FirSimpleFunction) originalFunction, firFunctionCall.getArgumentList().getArguments()) && originalFunction.getReceiverParameter() == null;
        }

        private final boolean isArrayOf(FirSimpleFunction function, List<? extends FirExpression> arguments) {
            String string = function.getSymbol().getCallableId().toString();
            if (Intrinsics.areEqual(string, "kotlin/emptyArray")) {
                if (function.getValueParameters().isEmpty() && arguments.isEmpty()) {
                    return true;
                }
            } else if (FirArrayOfCallTransformer.arrayOfNames.contains(string) && function.getValueParameters().size() == 1 && function.getValueParameters().get(0).getIsVararg() && arguments.size() <= 1) {
                return true;
            }
            return false;
        }
    }

    static {
        HashSet hashSetHashSetOf = SetsKt.hashSetOf("kotlin/arrayOf");
        HashSet hashSetHashSetOf2 = SetsKt.hashSetOf("boolean", "byte", PsiKeyword.CHAR, "double", "float", "int", "long", "short", "ubyte", "uint", "ulong", "ushort");
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(hashSetHashSetOf2, 10));
        Iterator it = hashSetHashSetOf2.iterator();
        while (it.hasNext()) {
            arrayList.add("kotlin/" + ((String) it.next()) + "ArrayOf");
        }
        arrayOfNames = SetsKt.plus((Set) hashSetHashSetOf, (Iterable) arrayList);
    }
}
