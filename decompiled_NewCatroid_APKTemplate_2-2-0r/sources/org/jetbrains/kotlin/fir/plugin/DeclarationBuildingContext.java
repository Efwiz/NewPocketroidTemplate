package org.jetbrains.kotlin.fir.plugin;

import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.GeneratedDeclarationKey;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.fir.EffectiveVisibilityUtilsKt;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOriginKt;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.builder.FirContextReceiverBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirTypeParameterBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirResolvedDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: DeclarationBuildingContext.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¢\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0002FGB%\b\u0004\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\t¢\u0006\u0002\u0010\nJ\r\u0010+\u001a\u00028\u0000H&¢\u0006\u0002\u0010,J\"\u0010-\u001a\u00020\u001f2\u0018\u0010.\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\u0004\u0012\u00020\u00100\rH\u0016J\u0010\u0010-\u001a\u00020\u001f2\u0006\u0010/\u001a\u00020\u0010H\u0016J\b\u00100\u001a\u00020\u001eH\u0004J\u001c\u00101\u001a\u0002022\u0006\u00103\u001a\u00020\"2\n\u00104\u001a\u0006\u0012\u0002\b\u000305H\u0004J$\u00106\u001a\u00020\u001f2\f\u00107\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\f\u00108\u001a\b\u0012\u0004\u0012\u0002020\u000eH\u0004J$\u00109\u001a\u00020\u001f2\f\u0010:\u001a\b\u0012\u0004\u0012\u00020;0\f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u0004J\u001f\u0010<\u001a\u00020\u001f2\u0017\u0010=\u001a\u0013\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001f0\r¢\u0006\u0002\b JI\u00103\u001a\u00020\u001f2\u0006\u0010>\u001a\u00020?2\b\b\u0002\u0010@\u001a\u00020A2\b\b\u0002\u0010B\u001a\u00020C2\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u0019\b\u0002\u0010D\u001a\u0013\u0012\u0004\u0012\u00020E\u0012\u0004\u0012\u00020\u001f0\r¢\u0006\u0002\b H\u0016R&\u0010\u000b\u001a\u001a\u0012\u0016\u0012\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e\u0012\u0004\u0012\u00020\u00100\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\tX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR%\u0010\u001d\u001a\u0019\u0012\u0015\u0012\u0013\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u001f0\r¢\u0006\u0002\b 0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020&X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*\u0082\u0001\u0003HIJ¨\u0006K"}, d2 = {"Lorg/jetbrains/kotlin/fir/plugin/DeclarationBuildingContext;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/GeneratedDeclarationKey;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;)V", "contextReceiverTypeProviders", "", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getKey", "()Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "setModality", "(Lorg/jetbrains/kotlin/descriptors/Modality;)V", "getOwner", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "statusConfigs", "Lorg/jetbrains/kotlin/fir/declarations/impl/FirResolvedDeclarationStatusImpl;", "", "Lkotlin/ExtensionFunctionType;", "typeParameters", "Lorg/jetbrains/kotlin/fir/plugin/DeclarationBuildingContext$TypeParameterData;", "getTypeParameters", "()Ljava/util/List;", "visibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "getVisibility", "()Lorg/jetbrains/kotlin/descriptors/Visibility;", "setVisibility", "(Lorg/jetbrains/kotlin/descriptors/Visibility;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "contextReceiver", "typeProvider", ModuleXmlParser.TYPE, "generateStatus", "generateTypeParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "typeParameter", "containingDeclarationSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "initTypeParameterBounds", "allParameters", "ownTypeParameters", "produceContextReceiversTo", FirebaseAnalytics.Param.DESTINATION, "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", NotificationCompat.CATEGORY_STATUS, "statusConfig", "name", "Lorg/jetbrains/kotlin/name/Name;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "isReified", "", "config", "Lorg/jetbrains/kotlin/fir/plugin/DeclarationBuildingContext$TypeParameterBuildingContext;", "TypeParameterBuildingContext", "TypeParameterData", "Lorg/jetbrains/kotlin/fir/plugin/ClassBuildingContext;", "Lorg/jetbrains/kotlin/fir/plugin/FunctionBuildingContext;", "Lorg/jetbrains/kotlin/fir/plugin/PropertyBuildingContext;", "plugin-utils"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DeclarationBuildingContext<T extends FirDeclaration> {
    private final List<Function1<List<? extends FirTypeParameterRef>, ConeKotlinType>> contextReceiverTypeProviders;
    private final GeneratedDeclarationKey key;
    private Modality modality;
    private final FirClassSymbol<?> owner;
    private final FirSession session;
    private final List<Function1<FirResolvedDeclarationStatusImpl, Unit>> statusConfigs;
    private final List<TypeParameterData> typeParameters;
    private Visibility visibility;

    public /* synthetic */ DeclarationBuildingContext(FirSession firSession, GeneratedDeclarationKey generatedDeclarationKey, FirClassSymbol firClassSymbol, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, generatedDeclarationKey, firClassSymbol);
    }

    public abstract T build();

    private DeclarationBuildingContext(FirSession firSession, GeneratedDeclarationKey generatedDeclarationKey, FirClassSymbol<?> firClassSymbol) {
        this.session = firSession;
        this.key = generatedDeclarationKey;
        this.owner = firClassSymbol;
        this.visibility = Visibilities.Public.INSTANCE;
        this.modality = Modality.FINAL;
        this.contextReceiverTypeProviders = new ArrayList();
        this.typeParameters = new ArrayList();
        this.statusConfigs = new ArrayList();
    }

    protected final FirSession getSession() {
        return this.session;
    }

    protected final GeneratedDeclarationKey getKey() {
        return this.key;
    }

    protected final FirClassSymbol<?> getOwner() {
        return this.owner;
    }

    public final Visibility getVisibility() {
        return this.visibility;
    }

    public final void setVisibility(Visibility visibility) {
        Intrinsics.checkNotNullParameter(visibility, "<set-?>");
        this.visibility = visibility;
    }

    public final Modality getModality() {
        return this.modality;
    }

    public final void setModality(Modality modality) {
        Intrinsics.checkNotNullParameter(modality, "<set-?>");
        this.modality = modality;
    }

    public final void status(Function1<? super FirResolvedDeclarationStatusImpl, Unit> statusConfig) {
        Intrinsics.checkNotNullParameter(statusConfig, "statusConfig");
        this.statusConfigs.add(statusConfig);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void typeParameter$default(DeclarationBuildingContext declarationBuildingContext, Name name2, Variance variance, boolean z, GeneratedDeclarationKey generatedDeclarationKey, Function1 function1, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: typeParameter");
        }
        if ((i & 2) != 0) {
            variance = Variance.INVARIANT;
        }
        Variance variance2 = variance;
        if ((i & 4) != 0) {
            z = false;
        }
        boolean z2 = z;
        if ((i & 8) != 0) {
            generatedDeclarationKey = declarationBuildingContext.key;
        }
        GeneratedDeclarationKey generatedDeclarationKey2 = generatedDeclarationKey;
        if ((i & 16) != 0) {
            function1 = new Function1<TypeParameterBuildingContext, Unit>() { // from class: org.jetbrains.kotlin.fir.plugin.DeclarationBuildingContext.typeParameter.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(TypeParameterBuildingContext typeParameterBuildingContext) {
                    Intrinsics.checkNotNullParameter(typeParameterBuildingContext, "$this$null");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(TypeParameterBuildingContext typeParameterBuildingContext) {
                    invoke2(typeParameterBuildingContext);
                    return Unit.INSTANCE;
                }
            };
        }
        declarationBuildingContext.typeParameter(name2, variance2, z2, generatedDeclarationKey2, function1);
    }

    public void typeParameter(Name name2, Variance variance, boolean isReified, GeneratedDeclarationKey key, Function1<? super TypeParameterBuildingContext, Unit> config) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(variance, "variance");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(config, "config");
        List<TypeParameterData> list = this.typeParameters;
        TypeParameterBuildingContext typeParameterBuildingContext = new TypeParameterBuildingContext();
        config.invoke(typeParameterBuildingContext);
        list.add(new TypeParameterData(name2, variance, isReified, typeParameterBuildingContext.getBoundProviders$plugin_utils(), key));
    }

    /* JADX INFO: compiled from: DeclarationBuildingContext.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J \u0010\u000b\u001a\u00020\f2\u0018\u0010\r\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0004\u0012\u00020\b0\u0005J\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\bR,\u0010\u0003\u001a\u001a\u0012\u0016\u0012\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0004\u0012\u00020\b0\u00050\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/plugin/DeclarationBuildingContext$TypeParameterBuildingContext;", "", "()V", "boundProviders", "", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "getBoundProviders$plugin_utils", "()Ljava/util/List;", "bound", "", "typeProvider", ModuleXmlParser.TYPE, "plugin-utils"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class TypeParameterBuildingContext {
        private final List<Function1<List<? extends FirTypeParameterRef>, ConeKotlinType>> boundProviders = new ArrayList();

        public final void bound(final ConeKotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            bound(new Function1<List<? extends FirTypeParameterRef>, ConeKotlinType>() { // from class: org.jetbrains.kotlin.fir.plugin.DeclarationBuildingContext$TypeParameterBuildingContext$bound$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final ConeKotlinType invoke(List<? extends FirTypeParameterRef> it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return type;
                }
            });
        }

        public final void bound(Function1<? super List<? extends FirTypeParameterRef>, ? extends ConeKotlinType> typeProvider) {
            Intrinsics.checkNotNullParameter(typeProvider, "typeProvider");
            this.boundProviders.add(typeProvider);
        }

        public final List<Function1<List<? extends FirTypeParameterRef>, ConeKotlinType>> getBoundProviders$plugin_utils() {
            return this.boundProviders;
        }
    }

    public void contextReceiver(final ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        contextReceiver(new Function1<List<? extends FirTypeParameterRef>, ConeKotlinType>() { // from class: org.jetbrains.kotlin.fir.plugin.DeclarationBuildingContext.contextReceiver.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final ConeKotlinType invoke(List<? extends FirTypeParameterRef> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return type;
            }
        });
    }

    public void contextReceiver(Function1<? super List<? extends FirTypeParameterRef>, ? extends ConeKotlinType> typeProvider) {
        Intrinsics.checkNotNullParameter(typeProvider, "typeProvider");
        this.contextReceiverTypeProviders.add(typeProvider);
    }

    protected final void produceContextReceiversTo(List<FirContextReceiver> destination, List<? extends FirTypeParameterRef> typeParameters) {
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Iterator<T> it = this.contextReceiverTypeProviders.iterator();
        while (it.hasNext()) {
            Function1 function1 = (Function1) it.next();
            FirContextReceiverBuilder firContextReceiverBuilder = new FirContextReceiverBuilder();
            firContextReceiverBuilder.setTypeRef(org.jetbrains.kotlin.fir.types.TypeUtilsKt.toFirResolvedTypeRef$default((ConeKotlinType) function1.invoke(typeParameters), null, null, 3, null));
            destination.add(firContextReceiverBuilder.build());
        }
    }

    /* JADX INFO: compiled from: DeclarationBuildingContext.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0084\b\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u001e\u0010\b\u001a\u001a\u0012\u0016\u0012\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\t\u0012\u0004\u0012\u00020\f0\n0\t\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0007HÆ\u0003J!\u0010\u001c\u001a\u001a\u0012\u0016\u0012\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\t\u0012\u0004\u0012\u00020\f0\n0\tHÆ\u0003J\t\u0010\u001d\u001a\u00020\u000eHÆ\u0003JS\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072 \b\u0002\u0010\b\u001a\u001a\u0012\u0016\u0012\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\t\u0012\u0004\u0012\u00020\f0\n0\t2\b\b\u0002\u0010\r\u001a\u00020\u000eHÆ\u0001J\u0013\u0010\u001f\u001a\u00020\u00072\b\u0010 \u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010!\u001a\u00020\"HÖ\u0001J\t\u0010#\u001a\u00020$HÖ\u0001R)\u0010\b\u001a\u001a\u0012\u0016\u0012\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\t\u0012\u0004\u0012\u00020\f0\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0012R\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/fir/plugin/DeclarationBuildingContext$TypeParameterData;", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "isReified", "", "boundProviders", "", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", ASTExpr.DEFAULT_MAP_KEY_NAME, "Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/types/Variance;ZLjava/util/List;Lorg/jetbrains/kotlin/GeneratedDeclarationKey;)V", "getBoundProviders", "()Ljava/util/List;", "()Z", "getKey", "()Lorg/jetbrains/kotlin/GeneratedDeclarationKey;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getVariance", "()Lorg/jetbrains/kotlin/types/Variance;", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "plugin-utils"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static final /* data */ class TypeParameterData {
        private final List<Function1<List<? extends FirTypeParameterRef>, ConeKotlinType>> boundProviders;
        private final boolean isReified;
        private final GeneratedDeclarationKey key;
        private final Name name;
        private final Variance variance;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ TypeParameterData copy$default(TypeParameterData typeParameterData, Name name2, Variance variance, boolean z, List list, GeneratedDeclarationKey generatedDeclarationKey, int i, Object obj) {
            if ((i & 1) != 0) {
                name2 = typeParameterData.name;
            }
            if ((i & 2) != 0) {
                variance = typeParameterData.variance;
            }
            Variance variance2 = variance;
            if ((i & 4) != 0) {
                z = typeParameterData.isReified;
            }
            boolean z2 = z;
            if ((i & 8) != 0) {
                list = typeParameterData.boundProviders;
            }
            List list2 = list;
            if ((i & 16) != 0) {
                generatedDeclarationKey = typeParameterData.key;
            }
            return typeParameterData.copy(name2, variance2, z2, list2, generatedDeclarationKey);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final Name getName() {
            return this.name;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Variance getVariance() {
            return this.variance;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final boolean getIsReified() {
            return this.isReified;
        }

        public final List<Function1<List<? extends FirTypeParameterRef>, ConeKotlinType>> component4() {
            return this.boundProviders;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final GeneratedDeclarationKey getKey() {
            return this.key;
        }

        public final TypeParameterData copy(Name name2, Variance variance, boolean isReified, List<? extends Function1<? super List<? extends FirTypeParameterRef>, ? extends ConeKotlinType>> boundProviders, GeneratedDeclarationKey key) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(variance, "variance");
            Intrinsics.checkNotNullParameter(boundProviders, "boundProviders");
            Intrinsics.checkNotNullParameter(key, "key");
            return new TypeParameterData(name2, variance, isReified, boundProviders, key);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof TypeParameterData)) {
                return false;
            }
            TypeParameterData typeParameterData = (TypeParameterData) other;
            return Intrinsics.areEqual(this.name, typeParameterData.name) && this.variance == typeParameterData.variance && this.isReified == typeParameterData.isReified && Intrinsics.areEqual(this.boundProviders, typeParameterData.boundProviders) && Intrinsics.areEqual(this.key, typeParameterData.key);
        }

        public int hashCode() {
            return (((((((this.name.hashCode() * 31) + this.variance.hashCode()) * 31) + Boolean.hashCode(this.isReified)) * 31) + this.boundProviders.hashCode()) * 31) + this.key.hashCode();
        }

        public String toString() {
            return "TypeParameterData(name=" + this.name + ", variance=" + this.variance + ", isReified=" + this.isReified + ", boundProviders=" + this.boundProviders + ", key=" + this.key + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public TypeParameterData(Name name2, Variance variance, boolean z, List<? extends Function1<? super List<? extends FirTypeParameterRef>, ? extends ConeKotlinType>> boundProviders, GeneratedDeclarationKey key) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(variance, "variance");
            Intrinsics.checkNotNullParameter(boundProviders, "boundProviders");
            Intrinsics.checkNotNullParameter(key, "key");
            this.name = name2;
            this.variance = variance;
            this.isReified = z;
            this.boundProviders = boundProviders;
            this.key = key;
        }

        public final Name getName() {
            return this.name;
        }

        public final Variance getVariance() {
            return this.variance;
        }

        public final boolean isReified() {
            return this.isReified;
        }

        public final List<Function1<List<? extends FirTypeParameterRef>, ConeKotlinType>> getBoundProviders() {
            return this.boundProviders;
        }

        public final GeneratedDeclarationKey getKey() {
            return this.key;
        }
    }

    protected final List<TypeParameterData> getTypeParameters() {
        return this.typeParameters;
    }

    protected final FirResolvedDeclarationStatusImpl generateStatus() {
        Visibility visibility = this.visibility;
        FirResolvedDeclarationStatusImpl firResolvedDeclarationStatusImpl = new FirResolvedDeclarationStatusImpl(visibility, this.modality, EffectiveVisibilityUtilsKt.toEffectiveVisibility$default(visibility, (FirClassLikeSymbol) this.owner, true, false, 4, (Object) null));
        Iterator<Function1<FirResolvedDeclarationStatusImpl, Unit>> it = this.statusConfigs.iterator();
        while (it.hasNext()) {
            it.next().invoke(firResolvedDeclarationStatusImpl);
        }
        return firResolvedDeclarationStatusImpl;
    }

    protected final void initTypeParameterBounds(List<? extends FirTypeParameterRef> allParameters, List<? extends FirTypeParameter> ownTypeParameters) {
        ArrayList arrayListListOf;
        Intrinsics.checkNotNullParameter(allParameters, "allParameters");
        Intrinsics.checkNotNullParameter(ownTypeParameters, "ownTypeParameters");
        for (Pair pair : CollectionsKt.zip(ownTypeParameters, this.typeParameters)) {
            FirTypeParameter firTypeParameter = (FirTypeParameter) pair.component1();
            List<Function1<List<? extends FirTypeParameterRef>, ConeKotlinType>> boundProviders = ((TypeParameterData) pair.component2()).getBoundProviders();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(boundProviders, 10));
            Iterator<T> it = boundProviders.iterator();
            while (it.hasNext()) {
                arrayList.add((ConeKotlinType) ((Function1) it.next()).invoke(allParameters));
            }
            ArrayList arrayList2 = arrayList;
            if (arrayList2.isEmpty()) {
                arrayListListOf = CollectionsKt.listOf(this.session.getBuiltinTypes().getNullableAnyType());
            } else {
                ArrayList arrayList3 = arrayList2;
                ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
                Iterator it2 = arrayList3.iterator();
                while (it2.hasNext()) {
                    arrayList4.add(org.jetbrains.kotlin.fir.types.TypeUtilsKt.toFirResolvedTypeRef$default((ConeKotlinType) it2.next(), null, null, 3, null));
                }
                arrayListListOf = arrayList4;
            }
            firTypeParameter.replaceBounds(arrayListListOf);
        }
    }

    protected final FirTypeParameter generateTypeParameter(TypeParameterData typeParameter, FirBasedSymbol<?> containingDeclarationSymbol) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(containingDeclarationSymbol, "containingDeclarationSymbol");
        FirTypeParameterBuilder firTypeParameterBuilder = new FirTypeParameterBuilder();
        firTypeParameterBuilder.setResolvePhase(FirResolvePhase.BODY_RESOLVE);
        firTypeParameterBuilder.setModuleData(FirModuleDataKt.getModuleData(this.session));
        firTypeParameterBuilder.setOrigin(FirDeclarationOriginKt.getOrigin(typeParameter.getKey()));
        firTypeParameterBuilder.setName(typeParameter.getName());
        firTypeParameterBuilder.setSymbol(new FirTypeParameterSymbol());
        firTypeParameterBuilder.setContainingDeclarationSymbol(containingDeclarationSymbol);
        firTypeParameterBuilder.setVariance(typeParameter.getVariance());
        firTypeParameterBuilder.setReified(typeParameter.isReified());
        return firTypeParameterBuilder.mo6809build();
    }
}
