package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirLambdaArgumentExpression;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponents;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: FirArgumentsToParametersMapper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a2\u0010\u0002\u001a\u00020\u0001*\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"EmptyArgumentMapping", "Lorg/jetbrains/kotlin/fir/resolve/calls/ArgumentMapping;", "mapArguments", "Lorg/jetbrains/kotlin/fir/resolve/BodyResolveComponents;", "arguments", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "originScope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "callSiteIsOperatorCall", "", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirArgumentsToParametersMapperKt {
    private static final ArgumentMapping EmptyArgumentMapping = new ArgumentMapping(new LinkedHashMap(), CollectionsKt.emptyList());

    public static final ArgumentMapping mapArguments(BodyResolveComponents bodyResolveComponents, List<? extends FirExpression> arguments, FirFunction function, FirScope firScope, boolean z) {
        Intrinsics.checkNotNullParameter(bodyResolveComponents, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(function, "function");
        if (arguments.isEmpty() && function.getValueParameters().isEmpty()) {
            return EmptyArgumentMapping;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        FirExpression firExpression = null;
        for (FirExpression firExpression2 : arguments) {
            if (!(firExpression2 instanceof FirLambdaArgumentExpression)) {
                arrayList.add(firExpression2);
            } else if (firExpression == null) {
                firExpression = firExpression2;
            } else {
                arrayList2.add(firExpression2);
            }
        }
        FirCallArgumentsProcessor firCallArgumentsProcessor = new FirCallArgumentsProcessor(bodyResolveComponents.getSession(), function, bodyResolveComponents, firScope, z && (function instanceof FirSimpleFunction) && function.getStatus().isOperator() && Intrinsics.areEqual(((FirSimpleFunction) function).getName(), OperatorNameConventions.SET) && !(function.getOrigin() instanceof FirDeclarationOrigin.DynamicScope));
        firCallArgumentsProcessor.processNonLambdaArguments(arrayList);
        if (firExpression != null) {
            firCallArgumentsProcessor.processExternalArgument(firExpression);
        }
        firCallArgumentsProcessor.processExcessLambdaArguments(arrayList2);
        firCallArgumentsProcessor.processDefaultsAndRunChecks();
        LinkedHashMap<FirValueParameter, ResolvedCallArgument> result = firCallArgumentsProcessor.getResult();
        List<ResolutionDiagnostic> diagnostics = firCallArgumentsProcessor.getDiagnostics();
        if (diagnostics == null) {
            diagnostics = CollectionsKt.emptyList();
        }
        return new ArgumentMapping(result, diagnostics);
    }
}
