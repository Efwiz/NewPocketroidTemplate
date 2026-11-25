package org.jetbrains.kotlin.fir.plugin;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.GeneratedDeclarationKey;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOriginKt;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.builder.FirValueParameterBuilder;
import org.jetbrains.kotlin.fir.expressions.builder.FirExpressionStubBuilder;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirValueParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FunctionBuildingContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001(B-\b\u0004\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ*\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00112\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aH\u0004JZ\u0010\u0016\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0018\u0010\u001f\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u001a\u0012\u0004\u0012\u00020!0 2\b\b\u0002\u0010\"\u001a\u00020#2\b\b\u0002\u0010$\u001a\u00020#2\b\b\u0002\u0010%\u001a\u00020#2\b\b\u0002\u0010&\u001a\u00020#2\b\b\u0002\u0010\b\u001a\u00020\tJH\u0010\u0016\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010'\u001a\u00020!2\b\b\u0002\u0010\"\u001a\u00020#2\b\b\u0002\u0010$\u001a\u00020#2\b\b\u0002\u0010%\u001a\u00020#2\b\b\u0002\u0010&\u001a\u00020#2\b\b\u0002\u0010\b\u001a\u00020\tR\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u0082\u0001\u0002)*¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/fir/plugin/FunctionBuildingContext;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/plugin/DeclarationBuildingContext;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "(Lorg/jetbrains/kotlin/name/CallableId;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/GeneratedDeclarationKey;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;)V", "getCallableId", "()Lorg/jetbrains/kotlin/name/CallableId;", "valueParameters", "", "Lorg/jetbrains/kotlin/fir/plugin/FunctionBuildingContext$ValueParameterData;", "getValueParameters", "()Ljava/util/List;", "generateValueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "valueParameter", "containingFunctionSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFunctionSymbol;", "functionTypeParameters", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "typeProvider", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "isCrossinline", "", "isNoinline", "isVararg", "hasDefaultValue", ModuleXmlParser.TYPE, "ValueParameterData", "Lorg/jetbrains/kotlin/fir/plugin/ConstructorBuildingContext;", "Lorg/jetbrains/kotlin/fir/plugin/SimpleFunctionBuildingContext;", "plugin-utils"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FunctionBuildingContext<T extends FirFunction> extends DeclarationBuildingContext<T> {
    private final CallableId callableId;
    private final List<ValueParameterData> valueParameters;

    public /* synthetic */ FunctionBuildingContext(CallableId callableId, FirSession firSession, GeneratedDeclarationKey generatedDeclarationKey, FirClassSymbol firClassSymbol, DefaultConstructorMarker defaultConstructorMarker) {
        this(callableId, firSession, generatedDeclarationKey, firClassSymbol);
    }

    protected final CallableId getCallableId() {
        return this.callableId;
    }

    /* JADX INFO: compiled from: FunctionBuildingContext.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0084\b\u0018\u00002\u00020\u0001BO\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0018\u0010\u0004\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0004\u0012\u00020\b0\u0005\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\n\u0012\u0006\u0010\r\u001a\u00020\n\u0012\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\u001b\u0010\u001a\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0004\u0012\u00020\b0\u0005HÆ\u0003J\t\u0010\u001b\u001a\u00020\nHÆ\u0003J\t\u0010\u001c\u001a\u00020\nHÆ\u0003J\t\u0010\u001d\u001a\u00020\nHÆ\u0003J\t\u0010\u001e\u001a\u00020\nHÆ\u0003J\t\u0010\u001f\u001a\u00020\u000fHÆ\u0003Ja\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u001a\b\u0002\u0010\u0004\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0004\u0012\u00020\b0\u00052\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\n2\b\b\u0002\u0010\r\u001a\u00020\n2\b\b\u0002\u0010\u000e\u001a\u00020\u000fHÆ\u0001J\u0013\u0010!\u001a\u00020\n2\b\u0010\"\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010#\u001a\u00020$HÖ\u0001J\t\u0010%\u001a\u00020&HÖ\u0001R\u0011\u0010\r\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0012R\u0011\u0010\u000b\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0012R\u0011\u0010\f\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0012R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R#\u0010\u0004\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/fir/plugin/FunctionBuildingContext$ValueParameterData;", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "typeProvider", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "isCrossinline", "", "isNoinline", "isVararg", "hasDefaultValue", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", "(Lorg/jetbrains/kotlin/name/Name;Lkotlin/jvm/functions/Function1;ZZZZLorg/jetbrains/kotlin/GeneratedDeclarationKey;)V", "getHasDefaultValue", "()Z", "getKey", "()Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getTypeProvider", "()Lkotlin/jvm/functions/Function1;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "plugin-utils"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final /* data */ class ValueParameterData {
        private final boolean hasDefaultValue;
        private final boolean isCrossinline;
        private final boolean isNoinline;
        private final boolean isVararg;
        private final GeneratedDeclarationKey key;
        private final Name name;
        private final Function1<List<? extends FirTypeParameterRef>, ConeKotlinType> typeProvider;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ ValueParameterData copy$default(ValueParameterData valueParameterData, Name name2, Function1 function1, boolean z, boolean z2, boolean z3, boolean z4, GeneratedDeclarationKey generatedDeclarationKey, int i, Object obj) {
            if ((i & 1) != 0) {
                name2 = valueParameterData.name;
            }
            if ((i & 2) != 0) {
                function1 = valueParameterData.typeProvider;
            }
            Function1 function2 = function1;
            if ((i & 4) != 0) {
                z = valueParameterData.isCrossinline;
            }
            boolean z5 = z;
            if ((i & 8) != 0) {
                z2 = valueParameterData.isNoinline;
            }
            boolean z6 = z2;
            if ((i & 16) != 0) {
                z3 = valueParameterData.isVararg;
            }
            boolean z7 = z3;
            if ((i & 32) != 0) {
                z4 = valueParameterData.hasDefaultValue;
            }
            boolean z8 = z4;
            if ((i & 64) != 0) {
                generatedDeclarationKey = valueParameterData.key;
            }
            return valueParameterData.copy(name2, function2, z5, z6, z7, z8, generatedDeclarationKey);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final Name getName() {
            return this.name;
        }

        public final Function1<List<? extends FirTypeParameterRef>, ConeKotlinType> component2() {
            return this.typeProvider;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final boolean getIsCrossinline() {
            return this.isCrossinline;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final boolean getIsNoinline() {
            return this.isNoinline;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final boolean getIsVararg() {
            return this.isVararg;
        }

        /* JADX INFO: renamed from: component6, reason: from getter */
        public final boolean getHasDefaultValue() {
            return this.hasDefaultValue;
        }

        /* JADX INFO: renamed from: component7, reason: from getter */
        public final GeneratedDeclarationKey getKey() {
            return this.key;
        }

        public final ValueParameterData copy(Name name2, Function1<? super List<? extends FirTypeParameterRef>, ? extends ConeKotlinType> typeProvider, boolean isCrossinline, boolean isNoinline, boolean isVararg, boolean hasDefaultValue, GeneratedDeclarationKey key) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(typeProvider, "typeProvider");
            Intrinsics.checkNotNullParameter(key, "key");
            return new ValueParameterData(name2, typeProvider, isCrossinline, isNoinline, isVararg, hasDefaultValue, key);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ValueParameterData)) {
                return false;
            }
            ValueParameterData valueParameterData = (ValueParameterData) other;
            return Intrinsics.areEqual(this.name, valueParameterData.name) && Intrinsics.areEqual(this.typeProvider, valueParameterData.typeProvider) && this.isCrossinline == valueParameterData.isCrossinline && this.isNoinline == valueParameterData.isNoinline && this.isVararg == valueParameterData.isVararg && this.hasDefaultValue == valueParameterData.hasDefaultValue && Intrinsics.areEqual(this.key, valueParameterData.key);
        }

        public int hashCode() {
            return (((((((((((this.name.hashCode() * 31) + this.typeProvider.hashCode()) * 31) + Boolean.hashCode(this.isCrossinline)) * 31) + Boolean.hashCode(this.isNoinline)) * 31) + Boolean.hashCode(this.isVararg)) * 31) + Boolean.hashCode(this.hasDefaultValue)) * 31) + this.key.hashCode();
        }

        public String toString() {
            return "ValueParameterData(name=" + this.name + ", typeProvider=" + this.typeProvider + ", isCrossinline=" + this.isCrossinline + ", isNoinline=" + this.isNoinline + ", isVararg=" + this.isVararg + ", hasDefaultValue=" + this.hasDefaultValue + ", key=" + this.key + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public ValueParameterData(Name name2, Function1<? super List<? extends FirTypeParameterRef>, ? extends ConeKotlinType> typeProvider, boolean z, boolean z2, boolean z3, boolean z4, GeneratedDeclarationKey key) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(typeProvider, "typeProvider");
            Intrinsics.checkNotNullParameter(key, "key");
            this.name = name2;
            this.typeProvider = typeProvider;
            this.isCrossinline = z;
            this.isNoinline = z2;
            this.isVararg = z3;
            this.hasDefaultValue = z4;
            this.key = key;
        }

        public final Name getName() {
            return this.name;
        }

        public final Function1<List<? extends FirTypeParameterRef>, ConeKotlinType> getTypeProvider() {
            return this.typeProvider;
        }

        public final boolean isCrossinline() {
            return this.isCrossinline;
        }

        public final boolean isNoinline() {
            return this.isNoinline;
        }

        public final boolean isVararg() {
            return this.isVararg;
        }

        public final boolean getHasDefaultValue() {
            return this.hasDefaultValue;
        }

        public final GeneratedDeclarationKey getKey() {
            return this.key;
        }
    }

    private FunctionBuildingContext(CallableId callableId, FirSession firSession, GeneratedDeclarationKey generatedDeclarationKey, FirClassSymbol<?> firClassSymbol) {
        super(firSession, generatedDeclarationKey, firClassSymbol, null);
        this.callableId = callableId;
        this.valueParameters = new ArrayList();
    }

    protected final List<ValueParameterData> getValueParameters() {
        return this.valueParameters;
    }

    public static /* synthetic */ void valueParameter$default(FunctionBuildingContext functionBuildingContext, Name name2, ConeKotlinType coneKotlinType, boolean z, boolean z2, boolean z3, boolean z4, GeneratedDeclarationKey generatedDeclarationKey, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: valueParameter");
        }
        functionBuildingContext.valueParameter(name2, coneKotlinType, (i & 4) != 0 ? false : z, (i & 8) != 0 ? false : z2, (i & 16) != 0 ? false : z3, (i & 32) != 0 ? false : z4, (i & 64) != 0 ? functionBuildingContext.getKey() : generatedDeclarationKey);
    }

    public final void valueParameter(Name name2, final ConeKotlinType type, boolean isCrossinline, boolean isNoinline, boolean isVararg, boolean hasDefaultValue, GeneratedDeclarationKey key) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(key, "key");
        valueParameter(name2, new Function1<List<? extends FirTypeParameterRef>, ConeKotlinType>() { // from class: org.jetbrains.kotlin.fir.plugin.FunctionBuildingContext.valueParameter.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ConeKotlinType invoke(List<? extends FirTypeParameterRef> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return type;
            }
        }, isCrossinline, isNoinline, isVararg, hasDefaultValue, key);
    }

    public static /* synthetic */ void valueParameter$default(FunctionBuildingContext functionBuildingContext, Name name2, Function1 function1, boolean z, boolean z2, boolean z3, boolean z4, GeneratedDeclarationKey generatedDeclarationKey, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: valueParameter");
        }
        functionBuildingContext.valueParameter(name2, (Function1<? super List<? extends FirTypeParameterRef>, ? extends ConeKotlinType>) function1, (i & 4) != 0 ? false : z, (i & 8) != 0 ? false : z2, (i & 16) != 0 ? false : z3, (i & 32) != 0 ? false : z4, (i & 64) != 0 ? functionBuildingContext.getKey() : generatedDeclarationKey);
    }

    public final void valueParameter(Name name2, Function1<? super List<? extends FirTypeParameterRef>, ? extends ConeKotlinType> typeProvider, boolean isCrossinline, boolean isNoinline, boolean isVararg, boolean hasDefaultValue, GeneratedDeclarationKey key) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(typeProvider, "typeProvider");
        Intrinsics.checkNotNullParameter(key, "key");
        this.valueParameters.add(new ValueParameterData(name2, typeProvider, isCrossinline, isNoinline, isVararg, hasDefaultValue, key));
    }

    protected final FirValueParameter generateValueParameter(ValueParameterData valueParameter, FirFunctionSymbol<?> containingFunctionSymbol, List<? extends FirTypeParameterRef> functionTypeParameters) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        Intrinsics.checkNotNullParameter(containingFunctionSymbol, "containingFunctionSymbol");
        Intrinsics.checkNotNullParameter(functionTypeParameters, "functionTypeParameters");
        FirValueParameterBuilder firValueParameterBuilder = new FirValueParameterBuilder();
        firValueParameterBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
        firValueParameterBuilder.setModuleData(FirModuleDataKt.getModuleData(getSession()));
        firValueParameterBuilder.setOrigin(FirDeclarationOriginKt.getOrigin(valueParameter.getKey()));
        firValueParameterBuilder.setReturnTypeRef(org.jetbrains.kotlin.fir.types.TypeUtilsKt.toFirResolvedTypeRef$default(valueParameter.getTypeProvider().invoke(functionTypeParameters), null, null, 3, null));
        firValueParameterBuilder.setName(valueParameter.getName());
        firValueParameterBuilder.setSymbol(new FirValueParameterSymbol(firValueParameterBuilder.getName()));
        if (valueParameter.getHasDefaultValue()) {
            FirExpressionStubBuilder firExpressionStubBuilder = new FirExpressionStubBuilder();
            firExpressionStubBuilder.setTypeRef(getSession().getBuiltinTypes().getNothingType());
            firValueParameterBuilder.setDefaultValue(firExpressionStubBuilder.mo6809build());
        }
        firValueParameterBuilder.setContainingFunctionSymbol(containingFunctionSymbol);
        firValueParameterBuilder.setCrossinline(valueParameter.isCrossinline());
        firValueParameterBuilder.setNoinline(valueParameter.isNoinline());
        firValueParameterBuilder.setVararg(valueParameter.isVararg());
        return firValueParameterBuilder.mo6809build();
    }
}
