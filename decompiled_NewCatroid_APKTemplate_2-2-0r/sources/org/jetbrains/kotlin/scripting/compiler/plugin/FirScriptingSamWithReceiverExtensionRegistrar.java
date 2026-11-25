package org.jetbrains.kotlin.scripting.compiler.plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.builder.FirScriptConfiguratorExtension;
import org.jetbrains.kotlin.fir.builder.FirScriptConfiguratorExtensionKt;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar;
import org.jetbrains.kotlin.fir.extensions.FirExtensionServiceKt;
import org.jetbrains.kotlin.fir.resolve.FirSamConversionTransformerExtension;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ResolveUtilsKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.FirFunctionTypeKindServiceKt;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.scripting.compiler.plugin.services.FirScriptConfiguratorExtensionImpl;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: FirScriptingSamWithReceiverExtensionRegistrar.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0006B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u0004*\u00060\u0005R\u00020\u0001H\u0014¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/FirScriptingSamWithReceiverExtensionRegistrar;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrar;", "()V", "configurePlugin", "", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrar$ExtensionRegistrarContext;", "FirScriptSamWithReceiverConventionTransformer", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirScriptingSamWithReceiverExtensionRegistrar extends FirExtensionRegistrar {

    /* JADX INFO: renamed from: org.jetbrains.kotlin.scripting.compiler.plugin.FirScriptingSamWithReceiverExtensionRegistrar$configurePlugin$1, reason: invalid class name */
    /* JADX INFO: compiled from: FirScriptingSamWithReceiverExtensionRegistrar.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<FirSession, FirScriptSamWithReceiverConventionTransformer> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(1);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(Lorg/jetbrains/kotlin/fir/FirSession;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(FirScriptSamWithReceiverConventionTransformer.class);
        }

        @Override // kotlin.jvm.functions.Function1
        public final FirScriptSamWithReceiverConventionTransformer invoke(FirSession p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return new FirScriptSamWithReceiverConventionTransformer(p0);
        }
    }

    @Override // org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar
    protected void configurePlugin(FirExtensionRegistrar.ExtensionRegistrarContext extensionRegistrarContext) {
        Intrinsics.checkNotNullParameter(extensionRegistrarContext, "<this>");
        extensionRegistrarContext.plusSamConversionTransformerExtension(AnonymousClass1.INSTANCE);
    }

    /* JADX INFO: compiled from: FirScriptingSamWithReceiverExtensionRegistrar.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\f\u001a\u0004\u0018\u00010\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R!\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/FirScriptingSamWithReceiverExtensionRegistrar$FirScriptSamWithReceiverConventionTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/FirSamConversionTransformerExtension;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "knownAnnotations", "", "", "getKnownAnnotations", "()Ljava/util/Set;", "knownAnnotations$delegate", "Lkotlin/Lazy;", "getCustomFunctionTypeForSamConversion", "Lorg/jetbrains/kotlin/fir/types/ConeLookupTagBasedType;", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FirScriptSamWithReceiverConventionTransformer extends FirSamConversionTransformerExtension {

        /* JADX INFO: renamed from: knownAnnotations$delegate, reason: from kotlin metadata */
        private final Lazy knownAnnotations;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FirScriptSamWithReceiverConventionTransformer(final FirSession session) {
            super(session);
            Intrinsics.checkNotNullParameter(session, "session");
            this.knownAnnotations = LazyKt.lazy(new Function0<Set<String>>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.FirScriptingSamWithReceiverExtensionRegistrar$FirScriptSamWithReceiverConventionTransformer$knownAnnotations$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Set<String> invoke() {
                    Set<String> setEmptySet;
                    List<FirScriptConfiguratorExtension> scriptConfigurators = FirScriptConfiguratorExtensionKt.getScriptConfigurators(FirExtensionServiceKt.getExtensionService(session));
                    LinkedHashSet linkedHashSet = new LinkedHashSet();
                    for (FirScriptConfiguratorExtension firScriptConfiguratorExtension : scriptConfigurators) {
                        FirScriptConfiguratorExtensionImpl firScriptConfiguratorExtensionImpl = firScriptConfiguratorExtension instanceof FirScriptConfiguratorExtensionImpl ? (FirScriptConfiguratorExtensionImpl) firScriptConfiguratorExtension : null;
                        if (firScriptConfiguratorExtensionImpl == null || (setEmptySet = firScriptConfiguratorExtensionImpl.getKnownAnnotationsForSamWithReceiver$kotlin_scripting_compiler()) == null) {
                            setEmptySet = SetsKt.emptySet();
                        }
                        CollectionsKt.addAll(linkedHashSet, setEmptySet);
                    }
                    return linkedHashSet;
                }
            });
        }

        public final Set<String> getKnownAnnotations() {
            return (Set) this.knownAnnotations.getValue();
        }

        @Override // org.jetbrains.kotlin.fir.resolve.FirSamConversionTransformerExtension
        public ConeLookupTagBasedType getCustomFunctionTypeForSamConversion(FirSimpleFunction function) {
            FirRegularClassSymbol firRegularClassSymbol;
            boolean z;
            Intrinsics.checkNotNullParameter(function, "function");
            ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(function);
            if (coneClassLikeLookupTagContainingClassLookupTag == null || (firRegularClassSymbol = LookupTagUtilsKt.toFirRegularClassSymbol(coneClassLikeLookupTagContainingClassLookupTag, getSession())) == null) {
                return null;
            }
            List<ClassId> resolvedAnnotationClassIds = firRegularClassSymbol.getResolvedAnnotationClassIds();
            if (!(resolvedAnnotationClassIds instanceof Collection) || !resolvedAnnotationClassIds.isEmpty()) {
                Iterator<T> it = resolvedAnnotationClassIds.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        z = false;
                        break;
                    }
                    if (getKnownAnnotations().contains(((ClassId) it.next()).asSingleFqName().asString())) {
                        z = true;
                        break;
                    }
                }
            } else {
                z = false;
                break;
            }
            if (!z) {
                return null;
            }
            List<FirValueParameter> valueParameters = function.getValueParameters();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
            Iterator<T> it2 = valueParameters.iterator();
            while (it2.hasNext()) {
                arrayList.add(FirTypeUtilsKt.getConeType(((FirValueParameter) it2.next()).getReturnTypeRef()));
            }
            ArrayList arrayList2 = arrayList;
            if (arrayList2.isEmpty()) {
                return null;
            }
            FunctionTypeKind.Function functionExtractSingleSpecialKindForFunction = FirFunctionTypeKindServiceKt.getFunctionTypeService(getSession()).extractSingleSpecialKindForFunction(function.getSymbol());
            if (functionExtractSingleSpecialKindForFunction == null) {
                functionExtractSingleSpecialKindForFunction = FunctionTypeKind.Function.INSTANCE;
            }
            return ResolveUtilsKt.createFunctionType$default(functionExtractSingleSpecialKindForFunction, arrayList2.subList(1, arrayList2.size()), (ConeKotlinType) arrayList2.get(0), FirTypeUtilsKt.getConeType(function.getReturnTypeRef()), null, 16, null);
        }
    }
}
