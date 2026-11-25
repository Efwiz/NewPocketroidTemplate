package org.jetbrains.kotlin.fir.plugin;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Iterator;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.GeneratedDeclarationKey;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOriginKt;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.builder.FirAbstractConstructorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirConstructedClassTypeParameterRefBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirConstructorBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirContextReceiverBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPrimaryConstructorBuilder;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.FqNamesUtilKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: ConstructorBuildingContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B)\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\u0002H\u0016J\"\u0010\r\u001a\u00020\u000e2\u0018\u0010\u000f\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\u0004\u0012\u00020\u00130\u0010H\u0017J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0013H\u0017JA\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0017\u0010\u001b\u001a\u0013\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u000e0\u0010¢\u0006\u0002\b\u001dH\u0017R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/fir/plugin/ConstructorBuildingContext;", "Lorg/jetbrains/kotlin/fir/plugin/FunctionBuildingContext;", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "isPrimary", "", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/GeneratedDeclarationKey;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;Z)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "contextReceiver", "", "typeProvider", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", ModuleXmlParser.TYPE, "typeParameter", "name", "Lorg/jetbrains/kotlin/name/Name;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "isReified", "config", "Lorg/jetbrains/kotlin/fir/plugin/DeclarationBuildingContext$TypeParameterBuildingContext;", "Lkotlin/ExtensionFunctionType;", "plugin-utils"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConstructorBuildingContext extends FunctionBuildingContext<FirConstructor> {
    private final boolean isPrimary;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConstructorBuildingContext(FirSession session, GeneratedDeclarationKey key, FirClassSymbol<?> owner, boolean z) {
        super(FqNamesUtilKt.callableIdForConstructor(owner.getClassId()), session, key, owner, null);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(owner, "owner");
        this.isPrimary = z;
    }

    @Override // org.jetbrains.kotlin.fir.plugin.DeclarationBuildingContext
    public FirConstructor build() {
        FirConstructor firConstructorMo6809build;
        if (getOwner() == null) {
            throw new IllegalArgumentException("Required value was null.".toString());
        }
        Function1<FirAbstractConstructorBuilder, Unit> function1 = new Function1<FirAbstractConstructorBuilder, Unit>() { // from class: org.jetbrains.kotlin.fir.plugin.ConstructorBuildingContext$build$init$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirAbstractConstructorBuilder firAbstractConstructorBuilder) {
                invoke2(firAbstractConstructorBuilder);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirAbstractConstructorBuilder firAbstractConstructorBuilder) {
                Intrinsics.checkNotNullParameter(firAbstractConstructorBuilder, "$this$null");
                firAbstractConstructorBuilder.setSymbol(new FirConstructorSymbol(this.this$0.getOwner().getClassId()));
                firAbstractConstructorBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
                firAbstractConstructorBuilder.setModuleData(FirModuleDataKt.getModuleData(this.this$0.getSession()));
                firAbstractConstructorBuilder.setOrigin(FirDeclarationOriginKt.getOrigin(this.this$0.getKey()));
                List<FirTypeParameterSymbol> typeParameterSymbols = this.this$0.getOwner().getTypeParameterSymbols();
                List<FirTypeParameterRef> typeParameters = firAbstractConstructorBuilder.getTypeParameters();
                for (FirTypeParameterSymbol firTypeParameterSymbol : typeParameterSymbols) {
                    FirConstructedClassTypeParameterRefBuilder firConstructedClassTypeParameterRefBuilder = new FirConstructedClassTypeParameterRefBuilder();
                    firConstructedClassTypeParameterRefBuilder.setSymbol(firTypeParameterSymbol);
                    typeParameters.add(firConstructedClassTypeParameterRefBuilder.build());
                }
                firAbstractConstructorBuilder.setReturnTypeRef(org.jetbrains.kotlin.fir.types.TypeUtilsKt.toFirResolvedTypeRef$default(ScopeUtilsKt.defaultType(this.this$0.getOwner()), null, null, 3, null));
                firAbstractConstructorBuilder.setStatus(this.this$0.generateStatus());
                if (this.this$0.getOwner().getRawStatus().isInner()) {
                    ConeClassLikeLookupTag containingClassLookupTag = ClassMembersKt.getContainingClassLookupTag(this.this$0.getOwner());
                    FirClassLikeSymbol<?> symbol = containingClassLookupTag != null ? LookupTagUtilsKt.toSymbol(containingClassLookupTag, this.this$0.getSession()) : null;
                    FirClassSymbol firClassSymbol = symbol instanceof FirClassSymbol ? (FirClassSymbol) symbol : null;
                    if (firClassSymbol == null) {
                        throw new IllegalStateException(("Symbol for parent of " + this.this$0.getOwner() + " not found").toString());
                    }
                    firAbstractConstructorBuilder.setDispatchReceiverType(ScopeUtilsKt.defaultType((FirClassSymbol<?>) firClassSymbol));
                }
                List<FunctionBuildingContext.ValueParameterData> valueParameters = this.this$0.getValueParameters();
                List<FirValueParameter> valueParameters2 = firAbstractConstructorBuilder.getValueParameters();
                ConstructorBuildingContext constructorBuildingContext = this.this$0;
                Iterator<T> it = valueParameters.iterator();
                while (it.hasNext()) {
                    valueParameters2.add(constructorBuildingContext.generateValueParameter((FunctionBuildingContext.ValueParameterData) it.next(), firAbstractConstructorBuilder.getSymbol(), firAbstractConstructorBuilder.getTypeParameters()));
                }
                if (this.this$0.getOwner() instanceof FirRegularClassSymbol) {
                    List<FirContextReceiver> resolvedContextReceivers = ((FirRegularClassSymbol) this.this$0.getOwner()).getResolvedContextReceivers();
                    List<FirContextReceiver> contextReceivers = firAbstractConstructorBuilder.getContextReceivers();
                    for (FirContextReceiver firContextReceiver : resolvedContextReceivers) {
                        FirContextReceiverBuilder firContextReceiverBuilder = new FirContextReceiverBuilder();
                        firContextReceiverBuilder.setTypeRef(org.jetbrains.kotlin.fir.types.TypeUtilsKt.toFirResolvedTypeRef$default(FirTypeUtilsKt.getConeType(firContextReceiver.getTypeRef()), null, null, 3, null));
                        contextReceivers.add(firContextReceiverBuilder.build());
                    }
                }
            }
        };
        if (this.isPrimary) {
            FirPrimaryConstructorBuilder firPrimaryConstructorBuilder = new FirPrimaryConstructorBuilder();
            function1.invoke(firPrimaryConstructorBuilder);
            firConstructorMo6809build = firPrimaryConstructorBuilder.mo6809build();
        } else {
            FirConstructorBuilder firConstructorBuilder = new FirConstructorBuilder();
            function1.invoke(firConstructorBuilder);
            firConstructorMo6809build = firConstructorBuilder.mo6809build();
        }
        ClassMembersKt.setContainingClassForStaticMemberAttr(firConstructorMo6809build, getOwner().getLookupTag());
        return firConstructorMo6809build;
    }

    @Override // org.jetbrains.kotlin.fir.plugin.DeclarationBuildingContext
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This function does nothing and should not be called")
    public /* synthetic */ void typeParameter(Name name2, Variance variance, boolean isReified, GeneratedDeclarationKey key, Function1 config) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(variance, "variance");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(config, "config");
        AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.plugin.DeclarationBuildingContext
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This function does nothing and should not be called")
    public /* synthetic */ void contextReceiver(ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
        throw null;
    }

    @Override // org.jetbrains.kotlin.fir.plugin.DeclarationBuildingContext
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "This function does nothing and should not be called")
    public /* synthetic */ void contextReceiver(Function1 typeProvider) {
        Intrinsics.checkNotNullParameter(typeProvider, "typeProvider");
        AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
        throw null;
    }
}
