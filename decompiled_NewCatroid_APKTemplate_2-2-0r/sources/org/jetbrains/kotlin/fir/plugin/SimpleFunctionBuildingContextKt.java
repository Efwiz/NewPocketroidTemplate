package org.jetbrains.kotlin.fir.plugin;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.GeneratedDeclarationKey;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.extensions.FirExtension;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: SimpleFunctionBuildingContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a[\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0018\u0010\t\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0004\u0012\u00020\r0\n2\u0019\b\u0002\u0010\u000e\u001a\u0013\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\n¢\u0006\u0002\b\u0011\u001aI\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\r2\u0019\b\u0002\u0010\u000e\u001a\u0013\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\n¢\u0006\u0002\b\u0011\u001aO\u0010\u0013\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00152\u0018\u0010\t\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0004\u0012\u00020\r0\n2\u0019\b\u0002\u0010\u000e\u001a\u0013\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\n¢\u0006\u0002\b\u0011\u001a=\u0010\u0013\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0012\u001a\u00020\r2\u0019\b\u0002\u0010\u000e\u001a\u0013\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\n¢\u0006\u0002\b\u0011¨\u0006\u0016"}, d2 = {"createMemberFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", "name", "Lorg/jetbrains/kotlin/name/Name;", "returnTypeProvider", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "config", "Lorg/jetbrains/kotlin/fir/plugin/SimpleFunctionBuildingContext;", "", "Lkotlin/ExtensionFunctionType;", "returnType", "createTopLevelFunction", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "plugin-utils"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SimpleFunctionBuildingContextKt {
    public static /* synthetic */ FirSimpleFunction createMemberFunction$default(FirExtension firExtension, FirClassSymbol firClassSymbol, GeneratedDeclarationKey generatedDeclarationKey, Name name2, ConeKotlinType coneKotlinType, Function1 function1, int i, Object obj) {
        if ((i & 16) != 0) {
            function1 = new Function1<SimpleFunctionBuildingContext, Unit>() { // from class: org.jetbrains.kotlin.fir.plugin.SimpleFunctionBuildingContextKt.createMemberFunction.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(SimpleFunctionBuildingContext simpleFunctionBuildingContext) {
                    Intrinsics.checkNotNullParameter(simpleFunctionBuildingContext, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(SimpleFunctionBuildingContext simpleFunctionBuildingContext) {
                    invoke2(simpleFunctionBuildingContext);
                    return Unit.INSTANCE;
                }
            };
        }
        return createMemberFunction(firExtension, (FirClassSymbol<?>) firClassSymbol, generatedDeclarationKey, name2, coneKotlinType, (Function1<? super SimpleFunctionBuildingContext, Unit>) function1);
    }

    public static final FirSimpleFunction createMemberFunction(FirExtension firExtension, FirClassSymbol<?> owner, GeneratedDeclarationKey key, Name name2, final ConeKotlinType returnType, Function1<? super SimpleFunctionBuildingContext, Unit> config) {
        Intrinsics.checkNotNullParameter(firExtension, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(config, "config");
        return createMemberFunction(firExtension, owner, key, name2, new Function1<List<? extends FirTypeParameter>, ConeKotlinType>() { // from class: org.jetbrains.kotlin.fir.plugin.SimpleFunctionBuildingContextKt.createMemberFunction.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ConeKotlinType invoke(List<? extends FirTypeParameter> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return returnType;
            }
        }, config);
    }

    public static /* synthetic */ FirSimpleFunction createMemberFunction$default(FirExtension firExtension, FirClassSymbol firClassSymbol, GeneratedDeclarationKey generatedDeclarationKey, Name name2, Function1 function1, Function1 function2, int i, Object obj) {
        if ((i & 16) != 0) {
            function2 = new Function1<SimpleFunctionBuildingContext, Unit>() { // from class: org.jetbrains.kotlin.fir.plugin.SimpleFunctionBuildingContextKt.createMemberFunction.3
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(SimpleFunctionBuildingContext simpleFunctionBuildingContext) {
                    Intrinsics.checkNotNullParameter(simpleFunctionBuildingContext, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(SimpleFunctionBuildingContext simpleFunctionBuildingContext) {
                    invoke2(simpleFunctionBuildingContext);
                    return Unit.INSTANCE;
                }
            };
        }
        return createMemberFunction(firExtension, (FirClassSymbol<?>) firClassSymbol, generatedDeclarationKey, name2, (Function1<? super List<? extends FirTypeParameter>, ? extends ConeKotlinType>) function1, (Function1<? super SimpleFunctionBuildingContext, Unit>) function2);
    }

    public static final FirSimpleFunction createMemberFunction(FirExtension firExtension, final FirClassSymbol<?> owner, GeneratedDeclarationKey key, Name name2, Function1<? super List<? extends FirTypeParameter>, ? extends ConeKotlinType> returnTypeProvider, Function1<? super SimpleFunctionBuildingContext, Unit> config) {
        Intrinsics.checkNotNullParameter(firExtension, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(returnTypeProvider, "returnTypeProvider");
        Intrinsics.checkNotNullParameter(config, "config");
        SimpleFunctionBuildingContext simpleFunctionBuildingContext = new SimpleFunctionBuildingContext(firExtension.getSession(), key, owner, new CallableId(owner.getClassId(), name2), returnTypeProvider);
        config.invoke(simpleFunctionBuildingContext);
        simpleFunctionBuildingContext.status(new Function1<FirResolvedDeclarationStatusImpl, Unit>() { // from class: org.jetbrains.kotlin.fir.plugin.SimpleFunctionBuildingContextKt$createMemberFunction$4$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl) {
                invoke2(firResolvedDeclarationStatusImpl);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirResolvedDeclarationStatusImpl status) {
                Intrinsics.checkNotNullParameter(status, "$this$status");
                status.setExpect(owner.getRawStatus().isExpect());
            }
        });
        return simpleFunctionBuildingContext.build();
    }

    public static /* synthetic */ FirSimpleFunction createTopLevelFunction$default(FirExtension firExtension, GeneratedDeclarationKey generatedDeclarationKey, CallableId callableId, ConeKotlinType coneKotlinType, Function1 function1, int i, Object obj) {
        if ((i & 8) != 0) {
            function1 = new Function1<SimpleFunctionBuildingContext, Unit>() { // from class: org.jetbrains.kotlin.fir.plugin.SimpleFunctionBuildingContextKt.createTopLevelFunction.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(SimpleFunctionBuildingContext simpleFunctionBuildingContext) {
                    Intrinsics.checkNotNullParameter(simpleFunctionBuildingContext, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(SimpleFunctionBuildingContext simpleFunctionBuildingContext) {
                    invoke2(simpleFunctionBuildingContext);
                    return Unit.INSTANCE;
                }
            };
        }
        return createTopLevelFunction(firExtension, generatedDeclarationKey, callableId, coneKotlinType, (Function1<? super SimpleFunctionBuildingContext, Unit>) function1);
    }

    public static final FirSimpleFunction createTopLevelFunction(FirExtension firExtension, GeneratedDeclarationKey key, CallableId callableId, final ConeKotlinType returnType, Function1<? super SimpleFunctionBuildingContext, Unit> config) {
        Intrinsics.checkNotNullParameter(firExtension, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(config, "config");
        return createTopLevelFunction(firExtension, key, callableId, new Function1<List<? extends FirTypeParameter>, ConeKotlinType>() { // from class: org.jetbrains.kotlin.fir.plugin.SimpleFunctionBuildingContextKt.createTopLevelFunction.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ConeKotlinType invoke(List<? extends FirTypeParameter> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return returnType;
            }
        }, config);
    }

    public static /* synthetic */ FirSimpleFunction createTopLevelFunction$default(FirExtension firExtension, GeneratedDeclarationKey generatedDeclarationKey, CallableId callableId, Function1 function1, Function1 function2, int i, Object obj) {
        if ((i & 8) != 0) {
            function2 = new Function1<SimpleFunctionBuildingContext, Unit>() { // from class: org.jetbrains.kotlin.fir.plugin.SimpleFunctionBuildingContextKt.createTopLevelFunction.3
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(SimpleFunctionBuildingContext simpleFunctionBuildingContext) {
                    Intrinsics.checkNotNullParameter(simpleFunctionBuildingContext, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(SimpleFunctionBuildingContext simpleFunctionBuildingContext) {
                    invoke2(simpleFunctionBuildingContext);
                    return Unit.INSTANCE;
                }
            };
        }
        return createTopLevelFunction(firExtension, generatedDeclarationKey, callableId, (Function1<? super List<? extends FirTypeParameter>, ? extends ConeKotlinType>) function1, (Function1<? super SimpleFunctionBuildingContext, Unit>) function2);
    }

    public static final FirSimpleFunction createTopLevelFunction(FirExtension firExtension, GeneratedDeclarationKey key, CallableId callableId, Function1<? super List<? extends FirTypeParameter>, ? extends ConeKotlinType> returnTypeProvider, Function1<? super SimpleFunctionBuildingContext, Unit> config) {
        Intrinsics.checkNotNullParameter(firExtension, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(callableId, "callableId");
        Intrinsics.checkNotNullParameter(returnTypeProvider, "returnTypeProvider");
        Intrinsics.checkNotNullParameter(config, "config");
        if (callableId.getClassId() != null) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        SimpleFunctionBuildingContext simpleFunctionBuildingContext = new SimpleFunctionBuildingContext(firExtension.getSession(), key, null, callableId, returnTypeProvider);
        config.invoke(simpleFunctionBuildingContext);
        return simpleFunctionBuildingContext.build();
    }
}
