package org.jetbrains.kotlin.fir.builder;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.builder.FirRegularClassBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirSimpleFunctionBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.expressions.FirPropertyAccessExpression;
import org.jetbrains.kotlin.fir.expressions.builder.FirPropertyAccessExpressionBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirThisReceiverExpressionBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirImplicitThisReferenceBuilder;
import org.jetbrains.kotlin.fir.references.builder.FirResolvedNamedReferenceBuilder;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: AbstractRawFirBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\\\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001aÜ\u0001\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0003*\u0002H\u0002\"\b\b\u0002\u0010\u0004*\u0002H\u0002*\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u0002H\u00032\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0018\u0010\u000b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u00020\u000e0\r0\f2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u00102\u0018\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00120\u00142\u001c\u0010\u0015\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u0001H\u0002\u0012\u0004\u0012\u00020\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u00160\u00142\u001d\u0010\u0017\u001a\u0019\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u00020\u00190\u0014¢\u0006\u0002\b\u001a2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u0002H\u0004\u0012\u0004\u0012\u00020\u001c0\u0010¢\u0006\u0002\u0010\u001d¨\u0006\u001e"}, d2 = {"createDataClassCopyFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "TBase", "TSource", "TParameter", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirRegularClassBuilder;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "sourceElement", "dispatchReceiver", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "zippedParameters", "", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "createClassTypeRefWithSourceKind", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "createParameterTypeRefWithSourceKind", "Lkotlin/Function2;", "toFirSource", "Lorg/jetbrains/kotlin/KtSourceElement;", "addValueParameterAnnotations", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirValueParameterBuilder;", "", "Lkotlin/ExtensionFunctionType;", "isVararg", "", "(Lorg/jetbrains/kotlin/fir/declarations/builder/FirRegularClassBuilder;Lorg/jetbrains/kotlin/name/ClassId;Ljava/lang/Object;Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "raw-fir.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class AbstractRawFirBuilderKt {
    public static final <TBase, TSource extends TBase, TParameter extends TBase> FirSimpleFunction createDataClassCopyFunction(FirRegularClassBuilder firRegularClassBuilder, ClassId classId, TSource tsource, ConeClassLikeType coneClassLikeType, List<? extends Pair<? extends TParameter, ? extends FirProperty>> zippedParameters, Function1<? super KtFakeSourceElementKind, ? extends FirTypeRef> createClassTypeRefWithSourceKind, Function2<? super FirProperty, ? super KtFakeSourceElementKind, ? extends FirTypeRef> createParameterTypeRefWithSourceKind, Function2<? super TBase, ? super KtFakeSourceElementKind, ? extends KtSourceElement> toFirSource, Function2<? super FirValueParameterBuilder, ? super TParameter, Unit> addValueParameterAnnotations, Function1<? super TParameter, Boolean> isVararg) {
        Intrinsics.checkNotNullParameter(firRegularClassBuilder, "<this>");
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(zippedParameters, "zippedParameters");
        Intrinsics.checkNotNullParameter(createClassTypeRefWithSourceKind, "createClassTypeRefWithSourceKind");
        Intrinsics.checkNotNullParameter(createParameterTypeRefWithSourceKind, "createParameterTypeRefWithSourceKind");
        Intrinsics.checkNotNullParameter(toFirSource, "toFirSource");
        Intrinsics.checkNotNullParameter(addValueParameterAnnotations, "addValueParameterAnnotations");
        Intrinsics.checkNotNullParameter(isVararg, "isVararg");
        FirSimpleFunctionBuilder firSimpleFunctionBuilder = new FirSimpleFunctionBuilder();
        FirTypeRef firTypeRefInvoke = createClassTypeRefWithSourceKind.invoke(KtFakeSourceElementKind.DataClassGeneratedMembers.INSTANCE);
        firSimpleFunctionBuilder.setSource(toFirSource.invoke(tsource, KtFakeSourceElementKind.DataClassGeneratedMembers.INSTANCE));
        firSimpleFunctionBuilder.setModuleData(firRegularClassBuilder.getModuleData());
        firSimpleFunctionBuilder.setOrigin(FirDeclarationOrigin.Synthetic.DataClassMember.INSTANCE);
        firSimpleFunctionBuilder.setReturnTypeRef(firTypeRefInvoke);
        firSimpleFunctionBuilder.setName(StandardNames.DATA_CLASS_COPY);
        firSimpleFunctionBuilder.setStatus(new FirDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.FINAL));
        FqName packageFqName = classId.getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        firSimpleFunctionBuilder.setSymbol(new FirNamedFunctionSymbol(new CallableId(packageFqName, classId.getRelativeClassName(), StandardNames.DATA_CLASS_COPY, null, 8, null)));
        firSimpleFunctionBuilder.setDispatchReceiverType(coneClassLikeType);
        firSimpleFunctionBuilder.setResolvePhase(firRegularClassBuilder.getResolvePhase());
        for (Pair<? extends TParameter, ? extends FirProperty> pair : zippedParameters) {
            TParameter tparameterComponent1 = pair.component1();
            FirProperty firPropertyComponent2 = pair.component2();
            Name name2 = firPropertyComponent2.getName();
            KtSourceElement ktSourceElementInvoke = toFirSource.invoke(tparameterComponent1, KtFakeSourceElementKind.DataClassGeneratedMembers.INSTANCE);
            FirTypeRef firTypeRefInvoke2 = createParameterTypeRefWithSourceKind.invoke(firPropertyComponent2, KtFakeSourceElementKind.DataClassGeneratedMembers.INSTANCE);
            List<FirValueParameter> valueParameters = firSimpleFunctionBuilder.getValueParameters();
            FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
            firValueParameterBuilder.setSource(ktSourceElementInvoke);
            firValueParameterBuilder.setContainingFunctionSymbol(firSimpleFunctionBuilder.getSymbol());
            firValueParameterBuilder.setModuleData(firRegularClassBuilder.getModuleData());
            firValueParameterBuilder.setOrigin(FirDeclarationOrigin.Synthetic.DataClassMember.INSTANCE);
            firValueParameterBuilder.setReturnTypeRef(firTypeRefInvoke2);
            firValueParameterBuilder.setName(name2);
            firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(name2));
            firValueParameterBuilder.setDefaultValue(createDataClassCopyFunction$generateComponentAccess(firRegularClassBuilder, ktSourceElementInvoke, firPropertyComponent2, firTypeRefInvoke, firTypeRefInvoke2));
            firValueParameterBuilder.setCrossinline(false);
            firValueParameterBuilder.setNoinline(false);
            firValueParameterBuilder.setVararg(isVararg.invoke(tparameterComponent1).booleanValue());
            addValueParameterAnnotations.invoke(firValueParameterBuilder, tparameterComponent1);
            Iterator<FirAnnotation> it = firValueParameterBuilder.getAnnotations().iterator();
            while (it.hasNext()) {
                it.next().replaceUseSiteTarget(null);
            }
            valueParameters.add(firValueParameterBuilder.mo6809build());
        }
        return firSimpleFunctionBuilder.mo6809build();
    }

    private static final FirPropertyAccessExpression createDataClassCopyFunction$generateComponentAccess(FirRegularClassBuilder firRegularClassBuilder, KtSourceElement ktSourceElement, FirProperty firProperty, FirTypeRef firTypeRef, FirTypeRef firTypeRef2) {
        FirPropertyAccessExpressionBuilder firPropertyAccessExpressionBuilder = new FirPropertyAccessExpressionBuilder();
        firPropertyAccessExpressionBuilder.setSource(ktSourceElement);
        firPropertyAccessExpressionBuilder.setTypeRef(firTypeRef2);
        FirThisReceiverExpressionBuilder firThisReceiverExpressionBuilder = new FirThisReceiverExpressionBuilder();
        firThisReceiverExpressionBuilder.setSource(ktSourceElement);
        FirImplicitThisReferenceBuilder firImplicitThisReferenceBuilder = new FirImplicitThisReferenceBuilder();
        firImplicitThisReferenceBuilder.setBoundSymbol(firRegularClassBuilder.getSymbol());
        firThisReceiverExpressionBuilder.setCalleeReference(firImplicitThisReferenceBuilder.build());
        firThisReceiverExpressionBuilder.setTypeRef(firTypeRef);
        firPropertyAccessExpressionBuilder.setDispatchReceiver(firThisReceiverExpressionBuilder.mo6809build());
        FirResolvedNamedReferenceBuilder firResolvedNamedReferenceBuilder = new FirResolvedNamedReferenceBuilder();
        firResolvedNamedReferenceBuilder.setSource(ktSourceElement);
        firResolvedNamedReferenceBuilder.setName(firProperty.getName());
        firResolvedNamedReferenceBuilder.setResolvedSymbol(firProperty.getSymbol());
        firPropertyAccessExpressionBuilder.setCalleeReference(firResolvedNamedReferenceBuilder.build());
        return firPropertyAccessExpressionBuilder.mo6809build();
    }
}
