package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.builder.FirConstructedClassTypeParameterRefBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirReceiverParameterBuilder;
import org.jetbrains.kotlin.fir.resolve.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;

/* JADX INFO: compiled from: ConstructorProcessing.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u001c\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\t0\u000bH\u0016R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/TypeAliasConstructorsSubstitutingScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "typeAliasSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeAliasSymbol;", "delegatingScope", "outerType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeAliasSymbol;Lorg/jetbrains/kotlin/fir/scopes/FirScope;Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;)V", "processDeclaredConstructors", "", "processor", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class TypeAliasConstructorsSubstitutingScope extends FirScope {
    private final FirScope delegatingScope;
    private final ConeClassLikeType outerType;
    private final FirTypeAliasSymbol typeAliasSymbol;

    public TypeAliasConstructorsSubstitutingScope(FirTypeAliasSymbol typeAliasSymbol, FirScope delegatingScope, ConeClassLikeType coneClassLikeType) {
        Intrinsics.checkNotNullParameter(typeAliasSymbol, "typeAliasSymbol");
        Intrinsics.checkNotNullParameter(delegatingScope, "delegatingScope");
        this.typeAliasSymbol = typeAliasSymbol;
        this.delegatingScope = delegatingScope;
        this.outerType = coneClassLikeType;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processDeclaredConstructors(final Function1<? super FirConstructorSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.delegatingScope.processDeclaredConstructors(new Function1<FirConstructorSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.resolve.calls.TypeAliasConstructorsSubstitutingScope.processDeclaredConstructors.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirConstructorSymbol firConstructorSymbol) {
                invoke2(firConstructorSymbol);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirConstructorSymbol originalConstructorSymbol) {
                Intrinsics.checkNotNullParameter(originalConstructorSymbol, "originalConstructorSymbol");
                List<FirTypeParameter> typeParameters = ((FirTypeAlias) TypeAliasConstructorsSubstitutingScope.this.typeAliasSymbol.getFir()).getTypeParameters();
                Function1<FirConstructorSymbol, Unit> function1 = processor;
                FirConstructor firConstructor = (FirConstructor) originalConstructorSymbol.getFir();
                TypeAliasConstructorsSubstitutingScope typeAliasConstructorsSubstitutingScope = TypeAliasConstructorsSubstitutingScope.this;
                FirConstructorBuilder firConstructorBuilder = new FirConstructorBuilder();
                firConstructorBuilder.setSource(firConstructor.getSource());
                firConstructorBuilder.setResolvePhase(FirResolveStateKt.getResolvePhase(firConstructor));
                firConstructorBuilder.setModuleData(firConstructor.getModuleData());
                firConstructorBuilder.setOrigin(firConstructor.getOrigin());
                firConstructorBuilder.setAttributes(firConstructor.getAttributes().copy());
                firConstructorBuilder.getTypeParameters().addAll(firConstructor.getTypeParameters());
                firConstructorBuilder.setStatus(firConstructor.getStatus());
                firConstructorBuilder.setReturnTypeRef(firConstructor.getReturnTypeRef());
                firConstructorBuilder.setReceiverParameter(firConstructor.getReceiverParameter());
                firConstructorBuilder.setDeprecationsProvider(firConstructor.getDeprecationsProvider());
                firConstructorBuilder.setContainerSource(firConstructor.getContainerSource());
                firConstructorBuilder.setDispatchReceiverType(firConstructor.getDispatchReceiverType());
                firConstructorBuilder.getContextReceivers().addAll(firConstructor.getContextReceivers());
                firConstructorBuilder.getValueParameters().addAll(firConstructor.getValueParameters());
                firConstructorBuilder.setContractDescription(firConstructor.getContractDescription());
                firConstructorBuilder.getAnnotations().addAll(firConstructor.getAnnotations());
                firConstructorBuilder.setSymbol(firConstructor.getSymbol());
                firConstructorBuilder.setDelegatedConstructor(firConstructor.getDelegatedConstructor());
                firConstructorBuilder.setBody(firConstructor.getBody());
                firConstructorBuilder.setSymbol(new FirConstructorSymbol(originalConstructorSymbol.getCallableId()));
                firConstructorBuilder.setOrigin(FirDeclarationOrigin.Synthetic.TypeAliasConstructor.INSTANCE);
                firConstructorBuilder.getTypeParameters().clear();
                List<FirTypeParameterRef> typeParameters2 = firConstructorBuilder.getTypeParameters();
                for (FirTypeParameter firTypeParameter : typeParameters) {
                    FirConstructedClassTypeParameterRefBuilder firConstructedClassTypeParameterRefBuilder = new FirConstructedClassTypeParameterRefBuilder();
                    firConstructedClassTypeParameterRefBuilder.setSymbol(firTypeParameter.getSymbol());
                    typeParameters2.add(firConstructedClassTypeParameterRefBuilder.build());
                }
                if (typeAliasConstructorsSubstitutingScope.outerType != null) {
                    FirResolvedTypeRef firResolvedTypeRefWithReplacedConeType$default = TypeUtilsKt.withReplacedConeType$default(((FirConstructor) originalConstructorSymbol.getFir()).getReturnTypeRef(), typeAliasConstructorsSubstitutingScope.outerType, null, 2, null);
                    FirReceiverParameterBuilder firReceiverParameterBuilder = new FirReceiverParameterBuilder();
                    firReceiverParameterBuilder.setTypeRef(firResolvedTypeRefWithReplacedConeType$default);
                    firConstructorBuilder.setReceiverParameter(firReceiverParameterBuilder.mo6809build());
                }
                FirConstructor firConstructorMo6809build = firConstructorBuilder.mo6809build();
                TypeAliasConstructorsSubstitutingScope typeAliasConstructorsSubstitutingScope2 = TypeAliasConstructorsSubstitutingScope.this;
                DeclarationUtilsKt.setOriginalConstructorIfTypeAlias(firConstructorMo6809build, (FirConstructor) originalConstructorSymbol.getFir());
                DeclarationUtilsKt.setTypeAliasForConstructor(firConstructorMo6809build, typeAliasConstructorsSubstitutingScope2.typeAliasSymbol);
                function1.invoke(firConstructorMo6809build.getSymbol());
            }
        });
    }
}
