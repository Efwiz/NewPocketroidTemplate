package org.jetbrains.kotlin.fir.extensions;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.ranges.RangesKt;
import kotlin.reflect.KClass;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension;
import org.jetbrains.kotlin.fir.backend.Fir2IrScriptConfiguratorExtension;
import org.jetbrains.kotlin.fir.builder.FirScriptConfiguratorExtension;
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar.ExtensionRegistrarContext;
import org.jetbrains.kotlin.fir.resolve.FirSamConversionTransformerExtension;

/* JADX INFO: compiled from: FirExtensionRegistrar.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000 \u00182\u00020\u0001:\u0002\u0018\u0019B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u000f\u001a\u00020\u0010J0\u0010\u0011\u001a\u00020\u0012\"\b\b\u0000\u0010\u0013*\u00020\u00062\u000e\u0010\u0014\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00130\u00052\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u0002H\u00130\bH\u0002J\u0010\u0010\u0016\u001a\u00020\u0012*\u00060\u0017R\u00020\u0000H$R;\u0010\u0003\u001a\"\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\b0\u00070\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR.\u0010\r\u001a\"\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\b0\u000e0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrar;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrarAdapter;", "()V", "configuredExtensionFactories", "", "Lkotlin/reflect/KClass;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension;", "", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension$Factory;", "getConfiguredExtensionFactories", "()Ljava/util/Map;", "configuredExtensionFactories$delegate", "Lkotlin/Lazy;", "extensionFactories", "", "configure", "Lorg/jetbrains/kotlin/fir/extensions/BunchOfRegisteredExtensions;", "registerExtension", "", "P", "kClass", "factory", "configurePlugin", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrar$ExtensionRegistrarContext;", "Companion", "ExtensionRegistrarContext", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirExtensionRegistrar extends FirExtensionRegistrarAdapter {

    /* JADX INFO: renamed from: configuredExtensionFactories$delegate, reason: from kotlin metadata */
    private final Lazy configuredExtensionFactories;
    private final Map<KClass<? extends FirExtension>, List<FirExtension.Factory<FirExtension>>> extensionFactories;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final List<KClass<? extends FirExtension>> AVAILABLE_EXTENSIONS = CollectionsKt.listOf((Object[]) new KClass[]{Reflection.getOrCreateKotlinClass(FirStatusTransformerExtension.class), Reflection.getOrCreateKotlinClass(FirDeclarationGenerationExtension.class), Reflection.getOrCreateKotlinClass(FirAdditionalCheckersExtension.class), Reflection.getOrCreateKotlinClass(FirSupertypeGenerationExtension.class), Reflection.getOrCreateKotlinClass(FirTypeAttributeExtension.class), Reflection.getOrCreateKotlinClass(FirExpressionResolutionExtension.class), Reflection.getOrCreateKotlinClass(FirExtensionSessionComponent.class), Reflection.getOrCreateKotlinClass(FirSamConversionTransformerExtension.class), Reflection.getOrCreateKotlinClass(FirAssignExpressionAltererExtension.class), Reflection.getOrCreateKotlinClass(FirScriptConfiguratorExtension.class), Reflection.getOrCreateKotlinClass(Fir2IrScriptConfiguratorExtension.class), Reflection.getOrCreateKotlinClass(FirFunctionTypeKindExtension.class), Reflection.getOrCreateKotlinClass(FirDeclarationsForMetadataProviderExtension.class)});
    private static final List<KClass<? extends FirExtension>> ALLOWED_EXTENSIONS_FOR_LIBRARY_SESSION = CollectionsKt.listOf((Object[]) new KClass[]{Reflection.getOrCreateKotlinClass(FirTypeAttributeExtension.class), Reflection.getOrCreateKotlinClass(FirFunctionTypeKindExtension.class)});

    protected abstract void configurePlugin(ExtensionRegistrarContext extensionRegistrarContext);

    /* JADX INFO: compiled from: FirExtensionRegistrar.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00042\u0006\u0010\r\u001a\u00020\u000eR\"\u0010\u0003\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u00050\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\"\u0010\t\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u00050\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrar$Companion;", "", "()V", "ALLOWED_EXTENSIONS_FOR_LIBRARY_SESSION", "", "Lkotlin/reflect/KClass;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtension;", "getALLOWED_EXTENSIONS_FOR_LIBRARY_SESSION$entrypoint", "()Ljava/util/List;", "AVAILABLE_EXTENSIONS", "getAVAILABLE_EXTENSIONS$entrypoint", "getInstances", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrar;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final List<FirExtensionRegistrar> getInstances(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            List instances = FirExtensionRegistrarAdapter.INSTANCE.getInstances(project);
            Intrinsics.checkNotNull(instances, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar>");
            return instances;
        }

        public final List<KClass<? extends FirExtension>> getAVAILABLE_EXTENSIONS$entrypoint() {
            return FirExtensionRegistrar.AVAILABLE_EXTENSIONS;
        }

        public final List<KClass<? extends FirExtension>> getALLOWED_EXTENSIONS_FOR_LIBRARY_SESSION$entrypoint() {
            return FirExtensionRegistrar.ALLOWED_EXTENSIONS_FOR_LIBRARY_SESSION;
        }
    }

    public FirExtensionRegistrar() {
        List<KClass<? extends FirExtension>> list = AVAILABLE_EXTENSIONS;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (Object obj : list) {
            linkedHashMap.put(obj, new ArrayList());
        }
        this.extensionFactories = linkedHashMap;
        this.configuredExtensionFactories = LazyKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) new Function0<Map<KClass<? extends FirExtension>, ? extends List<FirExtension.Factory<? extends FirExtension>>>>() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar$configuredExtensionFactories$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<KClass<? extends FirExtension>, ? extends List<FirExtension.Factory<? extends FirExtension>>> invoke() {
                this.this$0.configurePlugin(this.this$0.new ExtensionRegistrarContext());
                return this.this$0.extensionFactories;
            }
        });
    }

    /* JADX INFO: compiled from: FirExtensionRegistrar.kt */
    @Metadata(d1 = {"\u0000¨\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0084\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002JE\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u00060\u0004\"\u0004\b\u0000\u0010\u0007\"\u0004\b\u0001\u0010\u0006*\u0014\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u00060\b2\u0006\u0010\t\u001a\u0002H\u0007H\u0007¢\u0006\u0004\b\n\u0010\u000bJE\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u00060\u0004\"\u0004\b\u0000\u0010\u0007\"\u0004\b\u0001\u0010\u0006*\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\u00060\b2\u0006\u0010\t\u001a\u0002H\u0007H\u0007¢\u0006\u0004\b\f\u0010\u000bJ\u001e\u0010\r\u001a\u00020\u000e*\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000f0\u0004H\u0087\u0002¢\u0006\u0002\b\u0010J\u001e\u0010\r\u001a\u00020\u000e*\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00110\u0004H\u0087\u0002¢\u0006\u0002\b\u0012J\u001e\u0010\r\u001a\u00020\u000e*\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00130\u0004H\u0087\u0002¢\u0006\u0002\b\u0014J\u001e\u0010\r\u001a\u00020\u000e*\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00150\u0004H\u0087\u0002¢\u0006\u0002\b\u0016J\u001e\u0010\r\u001a\u00020\u000e*\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00170\u0004H\u0087\u0002¢\u0006\u0002\b\u0018J\u001e\u0010\r\u001a\u00020\u000e*\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00190\u0004H\u0087\u0002¢\u0006\u0002\b\u001aJ\u001e\u0010\r\u001a\u00020\u000e*\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u001b0\u0004H\u0087\u0002¢\u0006\u0002\b\u001cJ\u001e\u0010\r\u001a\u00020\u000e*\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u001d0\u0004H\u0087\u0002¢\u0006\u0002\b\u001eJ\u001e\u0010\r\u001a\u00020\u000e*\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u001f0\u0004H\u0087\u0002¢\u0006\u0002\b J\u001e\u0010\r\u001a\u00020\u000e*\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020!0\u0004H\u0087\u0002¢\u0006\u0002\b\"J\u001e\u0010\r\u001a\u00020\u000e*\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020#0\u0004H\u0087\u0002¢\u0006\u0002\b$J\u001e\u0010\r\u001a\u00020\u000e*\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020%0\u0004H\u0087\u0002¢\u0006\u0002\b&J\u001e\u0010\r\u001a\u00020\u000e*\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020'0\u0004H\u0087\u0002¢\u0006\u0002\b(J\u0012\u0010\r\u001a\u00020\u000e*\u00020)H\u0087\u0002¢\u0006\u0002\b\u0010J\u0012\u0010\r\u001a\u00020\u000e*\u00020*H\u0087\u0002¢\u0006\u0002\b\u0012J\u0012\u0010\r\u001a\u00020\u000e*\u00020+H\u0087\u0002¢\u0006\u0002\b\u0014J\u0012\u0010\r\u001a\u00020\u000e*\u00020,H\u0087\u0002¢\u0006\u0002\b\u0016J\u0012\u0010\r\u001a\u00020\u000e*\u00020-H\u0087\u0002¢\u0006\u0002\b\u0018J\u0012\u0010\r\u001a\u00020\u000e*\u00020.H\u0087\u0002¢\u0006\u0002\b\u001aJ\u0012\u0010\r\u001a\u00020\u000e*\u00020/H\u0087\u0002¢\u0006\u0002\b\u001cJ\u0012\u0010\r\u001a\u00020\u000e*\u000200H\u0087\u0002¢\u0006\u0002\b\u001eJ\u0012\u0010\r\u001a\u00020\u000e*\u000201H\u0087\u0002¢\u0006\u0002\b J\u0012\u0010\r\u001a\u00020\u000e*\u000202H\u0087\u0002¢\u0006\u0002\b\"J\u0012\u0010\r\u001a\u00020\u000e*\u000203H\u0087\u0002¢\u0006\u0002\b$J\u0012\u0010\r\u001a\u00020\u000e*\u000204H\u0087\u0002¢\u0006\u0002\b&J\u0012\u0010\r\u001a\u00020\u000e*\u000205H\u0087\u0002¢\u0006\u0002\b(¨\u00066"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrar$ExtensionRegistrarContext;", "", "(Lorg/jetbrains/kotlin/fir/extensions/FirExtensionRegistrar;)V", "bind", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/FirSession;", "R", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/Function2;", "value", "bindLeft", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;)Lkotlin/jvm/functions/Function1;", "bindRight", "unaryPlus", "", "Lorg/jetbrains/kotlin/fir/analysis/extensions/FirAdditionalCheckersExtension;", "plusAdditionalCheckersExtension", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrScriptConfiguratorExtension;", "plusFir2IrScriptConfiguratorExtension", "Lorg/jetbrains/kotlin/fir/builder/FirScriptConfiguratorExtension;", "plusScriptConfiguratorExtension", "Lorg/jetbrains/kotlin/fir/extensions/FirAssignExpressionAltererExtension;", "plusAssignExpressionAltererExtension", "Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationGenerationExtension;", "plusClassGenerationExtension", "Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationsForMetadataProviderExtension;", "plusDeclarationForMetadataProviderExtension", "Lorg/jetbrains/kotlin/fir/extensions/FirExpressionResolutionExtension;", "plusExpressionResolutionExtension", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionSessionComponent;", "plusExtensionSessionComponent", "Lorg/jetbrains/kotlin/fir/extensions/FirFunctionTypeKindExtension;", "plusFunctionTypeKindExtension", "Lorg/jetbrains/kotlin/fir/extensions/FirStatusTransformerExtension;", "plusStatusTransformerExtension", "Lorg/jetbrains/kotlin/fir/extensions/FirSupertypeGenerationExtension;", "plusSupertypeGenerationExtension", "Lorg/jetbrains/kotlin/fir/extensions/FirTypeAttributeExtension;", "plusTypeAttributeExtension", "Lorg/jetbrains/kotlin/fir/resolve/FirSamConversionTransformerExtension;", "plusSamConversionTransformerExtension", "Lorg/jetbrains/kotlin/fir/analysis/extensions/FirAdditionalCheckersExtension$Factory;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrScriptConfiguratorExtension$Factory;", "Lorg/jetbrains/kotlin/fir/builder/FirScriptConfiguratorExtension$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirAssignExpressionAltererExtension$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationGenerationExtension$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirDeclarationsForMetadataProviderExtension$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirExpressionResolutionExtension$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirExtensionSessionComponent$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirFunctionTypeKindExtension$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirStatusTransformerExtension$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirSupertypeGenerationExtension$Factory;", "Lorg/jetbrains/kotlin/fir/extensions/FirTypeAttributeExtension$Factory;", "Lorg/jetbrains/kotlin/fir/resolve/FirSamConversionTransformerExtension$Factory;", "entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected final class ExtensionRegistrarContext {
        public ExtensionRegistrarContext() {
        }

        public final void plusStatusTransformerExtension(FirStatusTransformerExtension.Factory factory) {
            Intrinsics.checkNotNullParameter(factory, "<this>");
            FirExtensionRegistrar.this.registerExtension(Reflection.getOrCreateKotlinClass(FirStatusTransformerExtension.class), factory);
        }

        public final void plusClassGenerationExtension(FirDeclarationGenerationExtension.Factory factory) {
            Intrinsics.checkNotNullParameter(factory, "<this>");
            FirExtensionRegistrar.this.registerExtension(Reflection.getOrCreateKotlinClass(FirDeclarationGenerationExtension.class), factory);
        }

        public final void plusAdditionalCheckersExtension(FirAdditionalCheckersExtension.Factory factory) {
            Intrinsics.checkNotNullParameter(factory, "<this>");
            FirExtensionRegistrar.this.registerExtension(Reflection.getOrCreateKotlinClass(FirAdditionalCheckersExtension.class), factory);
        }

        public final void plusSupertypeGenerationExtension(FirSupertypeGenerationExtension.Factory factory) {
            Intrinsics.checkNotNullParameter(factory, "<this>");
            FirExtensionRegistrar.this.registerExtension(Reflection.getOrCreateKotlinClass(FirSupertypeGenerationExtension.class), factory);
        }

        public final void plusTypeAttributeExtension(FirTypeAttributeExtension.Factory factory) {
            Intrinsics.checkNotNullParameter(factory, "<this>");
            FirExtensionRegistrar.this.registerExtension(Reflection.getOrCreateKotlinClass(FirTypeAttributeExtension.class), factory);
        }

        public final void plusExpressionResolutionExtension(FirExpressionResolutionExtension.Factory factory) {
            Intrinsics.checkNotNullParameter(factory, "<this>");
            FirExtensionRegistrar.this.registerExtension(Reflection.getOrCreateKotlinClass(FirExpressionResolutionExtension.class), factory);
        }

        public final void plusExtensionSessionComponent(FirExtensionSessionComponent.Factory factory) {
            Intrinsics.checkNotNullParameter(factory, "<this>");
            FirExtensionRegistrar.this.registerExtension(Reflection.getOrCreateKotlinClass(FirExtensionSessionComponent.class), factory);
        }

        public final void plusSamConversionTransformerExtension(FirSamConversionTransformerExtension.Factory factory) {
            Intrinsics.checkNotNullParameter(factory, "<this>");
            FirExtensionRegistrar.this.registerExtension(Reflection.getOrCreateKotlinClass(FirSamConversionTransformerExtension.class), factory);
        }

        public final void plusAssignExpressionAltererExtension(FirAssignExpressionAltererExtension.Factory factory) {
            Intrinsics.checkNotNullParameter(factory, "<this>");
            FirExtensionRegistrar.this.registerExtension(Reflection.getOrCreateKotlinClass(FirAssignExpressionAltererExtension.class), factory);
        }

        public final void plusScriptConfiguratorExtension(FirScriptConfiguratorExtension.Factory factory) {
            Intrinsics.checkNotNullParameter(factory, "<this>");
            FirExtensionRegistrar.this.registerExtension(Reflection.getOrCreateKotlinClass(FirScriptConfiguratorExtension.class), factory);
        }

        public final void plusFir2IrScriptConfiguratorExtension(Fir2IrScriptConfiguratorExtension.Factory factory) {
            Intrinsics.checkNotNullParameter(factory, "<this>");
            FirExtensionRegistrar.this.registerExtension(Reflection.getOrCreateKotlinClass(Fir2IrScriptConfiguratorExtension.class), factory);
        }

        public final void plusFunctionTypeKindExtension(FirFunctionTypeKindExtension.Factory factory) {
            Intrinsics.checkNotNullParameter(factory, "<this>");
            FirExtensionRegistrar.this.registerExtension(Reflection.getOrCreateKotlinClass(FirFunctionTypeKindExtension.class), factory);
        }

        public final void plusDeclarationForMetadataProviderExtension(FirDeclarationsForMetadataProviderExtension.Factory factory) {
            Intrinsics.checkNotNullParameter(factory, "<this>");
            FirExtensionRegistrar.this.registerExtension(Reflection.getOrCreateKotlinClass(FirDeclarationsForMetadataProviderExtension.class), factory);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final FirStatusTransformerExtension unaryPlus$lambda$0(Function1 this_unaryPlus, FirSession it) {
            Intrinsics.checkNotNullParameter(this_unaryPlus, "$this_unaryPlus");
            Intrinsics.checkNotNullParameter(it, "it");
            return (FirStatusTransformerExtension) this_unaryPlus.invoke(it);
        }

        public final void plusStatusTransformerExtension(final Function1<? super FirSession, ? extends FirStatusTransformerExtension> function1) {
            Intrinsics.checkNotNullParameter(function1, "<this>");
            plusStatusTransformerExtension(new FirStatusTransformerExtension.Factory() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar$ExtensionRegistrarContext$$ExternalSyntheticLambda8
                @Override // org.jetbrains.kotlin.fir.extensions.FirExtension.Factory
                public final FirExtension create(FirSession firSession) {
                    return FirExtensionRegistrar.ExtensionRegistrarContext.unaryPlus$lambda$0(function1, firSession);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final FirDeclarationGenerationExtension unaryPlus$lambda$1(Function1 this_unaryPlus, FirSession it) {
            Intrinsics.checkNotNullParameter(this_unaryPlus, "$this_unaryPlus");
            Intrinsics.checkNotNullParameter(it, "it");
            return (FirDeclarationGenerationExtension) this_unaryPlus.invoke(it);
        }

        public final void plusClassGenerationExtension(final Function1<? super FirSession, ? extends FirDeclarationGenerationExtension> function1) {
            Intrinsics.checkNotNullParameter(function1, "<this>");
            plusClassGenerationExtension(new FirDeclarationGenerationExtension.Factory() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar$ExtensionRegistrarContext$$ExternalSyntheticLambda9
                @Override // org.jetbrains.kotlin.fir.extensions.FirExtension.Factory
                public final FirExtension create(FirSession firSession) {
                    return FirExtensionRegistrar.ExtensionRegistrarContext.unaryPlus$lambda$1(function1, firSession);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final FirAdditionalCheckersExtension unaryPlus$lambda$2(Function1 this_unaryPlus, FirSession it) {
            Intrinsics.checkNotNullParameter(this_unaryPlus, "$this_unaryPlus");
            Intrinsics.checkNotNullParameter(it, "it");
            return (FirAdditionalCheckersExtension) this_unaryPlus.invoke(it);
        }

        public final void plusAdditionalCheckersExtension(final Function1<? super FirSession, ? extends FirAdditionalCheckersExtension> function1) {
            Intrinsics.checkNotNullParameter(function1, "<this>");
            plusAdditionalCheckersExtension(new FirAdditionalCheckersExtension.Factory() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar$ExtensionRegistrarContext$$ExternalSyntheticLambda1
                @Override // org.jetbrains.kotlin.fir.extensions.FirExtension.Factory
                public final FirExtension create(FirSession firSession) {
                    return FirExtensionRegistrar.ExtensionRegistrarContext.unaryPlus$lambda$2(function1, firSession);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final FirSupertypeGenerationExtension unaryPlus$lambda$3(Function1 this_unaryPlus, FirSession it) {
            Intrinsics.checkNotNullParameter(this_unaryPlus, "$this_unaryPlus");
            Intrinsics.checkNotNullParameter(it, "it");
            return (FirSupertypeGenerationExtension) this_unaryPlus.invoke(it);
        }

        public final void plusSupertypeGenerationExtension(final Function1<? super FirSession, ? extends FirSupertypeGenerationExtension> function1) {
            Intrinsics.checkNotNullParameter(function1, "<this>");
            plusSupertypeGenerationExtension(new FirSupertypeGenerationExtension.Factory() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar$ExtensionRegistrarContext$$ExternalSyntheticLambda12
                @Override // org.jetbrains.kotlin.fir.extensions.FirExtension.Factory
                public final FirExtension create(FirSession firSession) {
                    return FirExtensionRegistrar.ExtensionRegistrarContext.unaryPlus$lambda$3(function1, firSession);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final FirTypeAttributeExtension unaryPlus$lambda$4(Function1 this_unaryPlus, FirSession it) {
            Intrinsics.checkNotNullParameter(this_unaryPlus, "$this_unaryPlus");
            Intrinsics.checkNotNullParameter(it, "it");
            return (FirTypeAttributeExtension) this_unaryPlus.invoke(it);
        }

        public final void plusTypeAttributeExtension(final Function1<? super FirSession, ? extends FirTypeAttributeExtension> function1) {
            Intrinsics.checkNotNullParameter(function1, "<this>");
            plusTypeAttributeExtension(new FirTypeAttributeExtension.Factory() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar$ExtensionRegistrarContext$$ExternalSyntheticLambda10
                @Override // org.jetbrains.kotlin.fir.extensions.FirExtension.Factory
                public final FirExtension create(FirSession firSession) {
                    return FirExtensionRegistrar.ExtensionRegistrarContext.unaryPlus$lambda$4(function1, firSession);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final FirExpressionResolutionExtension unaryPlus$lambda$5(Function1 this_unaryPlus, FirSession it) {
            Intrinsics.checkNotNullParameter(this_unaryPlus, "$this_unaryPlus");
            Intrinsics.checkNotNullParameter(it, "it");
            return (FirExpressionResolutionExtension) this_unaryPlus.invoke(it);
        }

        public final void plusExpressionResolutionExtension(final Function1<? super FirSession, ? extends FirExpressionResolutionExtension> function1) {
            Intrinsics.checkNotNullParameter(function1, "<this>");
            plusExpressionResolutionExtension(new FirExpressionResolutionExtension.Factory() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar$ExtensionRegistrarContext$$ExternalSyntheticLambda3
                @Override // org.jetbrains.kotlin.fir.extensions.FirExtension.Factory
                public final FirExtension create(FirSession firSession) {
                    return FirExtensionRegistrar.ExtensionRegistrarContext.unaryPlus$lambda$5(function1, firSession);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final FirExtensionSessionComponent unaryPlus$lambda$6(Function1 this_unaryPlus, FirSession it) {
            Intrinsics.checkNotNullParameter(this_unaryPlus, "$this_unaryPlus");
            Intrinsics.checkNotNullParameter(it, "it");
            return (FirExtensionSessionComponent) this_unaryPlus.invoke(it);
        }

        public final void plusExtensionSessionComponent(final Function1<? super FirSession, ? extends FirExtensionSessionComponent> function1) {
            Intrinsics.checkNotNullParameter(function1, "<this>");
            plusExtensionSessionComponent(new FirExtensionSessionComponent.Factory() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar$ExtensionRegistrarContext$$ExternalSyntheticLambda11
                @Override // org.jetbrains.kotlin.fir.extensions.FirExtension.Factory
                public final FirExtension create(FirSession firSession) {
                    return FirExtensionRegistrar.ExtensionRegistrarContext.unaryPlus$lambda$6(function1, firSession);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final FirSamConversionTransformerExtension unaryPlus$lambda$7(Function1 this_unaryPlus, FirSession it) {
            Intrinsics.checkNotNullParameter(this_unaryPlus, "$this_unaryPlus");
            Intrinsics.checkNotNullParameter(it, "it");
            return (FirSamConversionTransformerExtension) this_unaryPlus.invoke(it);
        }

        public final void plusSamConversionTransformerExtension(final Function1<? super FirSession, ? extends FirSamConversionTransformerExtension> function1) {
            Intrinsics.checkNotNullParameter(function1, "<this>");
            plusSamConversionTransformerExtension(new FirSamConversionTransformerExtension.Factory() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar$ExtensionRegistrarContext$$ExternalSyntheticLambda7
                @Override // org.jetbrains.kotlin.fir.extensions.FirExtension.Factory
                public final FirExtension create(FirSession firSession) {
                    return FirExtensionRegistrar.ExtensionRegistrarContext.unaryPlus$lambda$7(function1, firSession);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final FirAssignExpressionAltererExtension unaryPlus$lambda$8(Function1 this_unaryPlus, FirSession it) {
            Intrinsics.checkNotNullParameter(this_unaryPlus, "$this_unaryPlus");
            Intrinsics.checkNotNullParameter(it, "it");
            return (FirAssignExpressionAltererExtension) this_unaryPlus.invoke(it);
        }

        public final void plusAssignExpressionAltererExtension(final Function1<? super FirSession, ? extends FirAssignExpressionAltererExtension> function1) {
            Intrinsics.checkNotNullParameter(function1, "<this>");
            plusAssignExpressionAltererExtension(new FirAssignExpressionAltererExtension.Factory() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar$ExtensionRegistrarContext$$ExternalSyntheticLambda2
                @Override // org.jetbrains.kotlin.fir.extensions.FirExtension.Factory
                public final FirExtension create(FirSession firSession) {
                    return FirExtensionRegistrar.ExtensionRegistrarContext.unaryPlus$lambda$8(function1, firSession);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final FirScriptConfiguratorExtension unaryPlus$lambda$9(Function1 this_unaryPlus, FirSession it) {
            Intrinsics.checkNotNullParameter(this_unaryPlus, "$this_unaryPlus");
            Intrinsics.checkNotNullParameter(it, "it");
            return (FirScriptConfiguratorExtension) this_unaryPlus.invoke(it);
        }

        public final void plusScriptConfiguratorExtension(final Function1<? super FirSession, ? extends FirScriptConfiguratorExtension> function1) {
            Intrinsics.checkNotNullParameter(function1, "<this>");
            plusScriptConfiguratorExtension(new FirScriptConfiguratorExtension.Factory() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar$ExtensionRegistrarContext$$ExternalSyntheticLambda6
                @Override // org.jetbrains.kotlin.fir.extensions.FirExtension.Factory
                public final FirExtension create(FirSession firSession) {
                    return FirExtensionRegistrar.ExtensionRegistrarContext.unaryPlus$lambda$9(function1, firSession);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Fir2IrScriptConfiguratorExtension unaryPlus$lambda$10(Function1 this_unaryPlus, FirSession it) {
            Intrinsics.checkNotNullParameter(this_unaryPlus, "$this_unaryPlus");
            Intrinsics.checkNotNullParameter(it, "it");
            return (Fir2IrScriptConfiguratorExtension) this_unaryPlus.invoke(it);
        }

        public final void plusFir2IrScriptConfiguratorExtension(final Function1<? super FirSession, ? extends Fir2IrScriptConfiguratorExtension> function1) {
            Intrinsics.checkNotNullParameter(function1, "<this>");
            plusFir2IrScriptConfiguratorExtension(new Fir2IrScriptConfiguratorExtension.Factory() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar$ExtensionRegistrarContext$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.fir.extensions.FirExtension.Factory
                public final FirExtension create(FirSession firSession) {
                    return FirExtensionRegistrar.ExtensionRegistrarContext.unaryPlus$lambda$10(function1, firSession);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final FirFunctionTypeKindExtension unaryPlus$lambda$11(Function1 this_unaryPlus, FirSession it) {
            Intrinsics.checkNotNullParameter(this_unaryPlus, "$this_unaryPlus");
            Intrinsics.checkNotNullParameter(it, "it");
            return (FirFunctionTypeKindExtension) this_unaryPlus.invoke(it);
        }

        public final void plusFunctionTypeKindExtension(final Function1<? super FirSession, ? extends FirFunctionTypeKindExtension> function1) {
            Intrinsics.checkNotNullParameter(function1, "<this>");
            plusFunctionTypeKindExtension(new FirFunctionTypeKindExtension.Factory() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar$ExtensionRegistrarContext$$ExternalSyntheticLambda5
                @Override // org.jetbrains.kotlin.fir.extensions.FirExtension.Factory
                public final FirExtension create(FirSession firSession) {
                    return FirExtensionRegistrar.ExtensionRegistrarContext.unaryPlus$lambda$11(function1, firSession);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final FirDeclarationsForMetadataProviderExtension unaryPlus$lambda$12(Function1 this_unaryPlus, FirSession it) {
            Intrinsics.checkNotNullParameter(this_unaryPlus, "$this_unaryPlus");
            Intrinsics.checkNotNullParameter(it, "it");
            return (FirDeclarationsForMetadataProviderExtension) this_unaryPlus.invoke(it);
        }

        public final void plusDeclarationForMetadataProviderExtension(final Function1<? super FirSession, ? extends FirDeclarationsForMetadataProviderExtension> function1) {
            Intrinsics.checkNotNullParameter(function1, "<this>");
            plusDeclarationForMetadataProviderExtension(new FirDeclarationsForMetadataProviderExtension.Factory() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar$ExtensionRegistrarContext$$ExternalSyntheticLambda4
                @Override // org.jetbrains.kotlin.fir.extensions.FirExtension.Factory
                public final FirExtension create(FirSession firSession) {
                    return FirExtensionRegistrar.ExtensionRegistrarContext.unaryPlus$lambda$12(function1, firSession);
                }
            });
        }

        public final <T, R> Function1<FirSession, R> bindLeft(final Function2<? super T, ? super FirSession, ? extends R> function2, final T t) {
            Intrinsics.checkNotNullParameter(function2, "<this>");
            return new Function1<FirSession, R>() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar$ExtensionRegistrarContext$bind$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                /* JADX WARN: Type inference fix 'apply assigned field type' failed
                java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
                	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
                	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
                	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
                 */
                @Override // kotlin.jvm.functions.Function1
                public final R invoke(FirSession it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return function2.invoke(t, it);
                }
            };
        }

        public final <T, R> Function1<FirSession, R> bindRight(final Function2<? super FirSession, ? super T, ? extends R> function2, final T t) {
            Intrinsics.checkNotNullParameter(function2, "<this>");
            return new Function1<FirSession, R>() { // from class: org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar$ExtensionRegistrarContext$bind$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(1);
                }

                /* JADX WARN: Type inference fix 'apply assigned field type' failed
                java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
                	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
                	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
                	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
                	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
                 */
                @Override // kotlin.jvm.functions.Function1
                public final R invoke(FirSession it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return function2.invoke(it, t);
                }
            };
        }
    }

    public final BunchOfRegisteredExtensions configure() {
        return new BunchOfRegisteredExtensions(getConfiguredExtensionFactories());
    }

    private final Map<KClass<? extends FirExtension>, List<FirExtension.Factory<FirExtension>>> getConfiguredExtensionFactories() {
        return (Map) this.configuredExtensionFactories.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final <P extends FirExtension> void registerExtension(KClass<? extends P> kClass, FirExtension.Factory<? extends P> factory) {
        ((List) MapsKt.getValue(this.extensionFactories, kClass)).add(factory);
    }
}
