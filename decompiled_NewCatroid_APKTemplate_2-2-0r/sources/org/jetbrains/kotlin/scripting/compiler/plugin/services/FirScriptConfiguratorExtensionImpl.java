package org.jetbrains.kotlin.scripting.compiler.plugin.services;

import com.gaurav.avnc.vnc.XKeySym;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.KotlinType;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.KtFakeSourceElement;
import org.jetbrains.kotlin.KtFakeSourceElementKind;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.builder.FirScriptConfiguratorExtension;
import org.jetbrains.kotlin.fir.declarations.DeclarationUtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.builder.FirContextReceiverBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirFileBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirImportBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirPropertyBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirScriptBuilder;
import org.jetbrains.kotlin.fir.declarations.impl.FirDeclarationStatusImpl;
import org.jetbrains.kotlin.fir.declarations.impl.FirDefaultPropertyGetter;
import org.jetbrains.kotlin.fir.declarations.utils.FirScriptUtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.extensions.FirExtension;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.types.FirQualifierPart;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;
import org.jetbrains.kotlin.fir.types.builder.FirUserTypeRefBuilder;
import org.jetbrains.kotlin.fir.types.impl.FirQualifierPartImpl;
import org.jetbrains.kotlin.fir.types.impl.FirTypeArgumentListImpl;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtPsiFactory;
import org.jetbrains.kotlin.scripting.definitions.ScriptCompilationConfigurationFromDefinitionKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: FirScriptConfigurationExtensionImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 !2\u00020\u0001:\u0001!B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001c\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0002J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J\f\u0010\u0019\u001a\u00020\t*\u00020\u0018H\u0002J\u0014\u0010\u001a\u001a\u00020\u001b*\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0018H\u0016J\u0014\u0010\u001e\u001a\u00020\u001b*\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 H\u0016R\u001e\u0010\u0007\u001a\u0012\u0012\u0004\u0012\u00020\t0\bj\b\u0012\u0004\u0012\u00020\t`\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\f8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/services/FirScriptConfiguratorExtensionImpl;", "Lorg/jetbrains/kotlin/fir/builder/FirScriptConfiguratorExtension;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lkotlin/script/experimental/host/ScriptingHostConfiguration;)V", "_knownAnnotationsForSamWithReceiver", "Ljava/util/HashSet;", "", "Lkotlin/collections/HashSet;", "knownAnnotationsForSamWithReceiver", "", "getKnownAnnotationsForSamWithReceiver$kotlin_scripting_compiler", "()Ljava/util/Set;", "buildContextReceiverWithFqName", "Lorg/jetbrains/kotlin/fir/declarations/FirContextReceiver;", "classFqn", "Lorg/jetbrains/kotlin/name/FqName;", "customName", "Lorg/jetbrains/kotlin/name/Name;", "getOrLoadConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "file", "Lorg/jetbrains/kotlin/KtSourceFile;", "asString", "configure", "", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirScriptBuilder;", "sourceFile", "configureContainingFile", "fileBuilder", "Lorg/jetbrains/kotlin/fir/declarations/builder/FirFileBuilder;", "Companion", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirScriptConfiguratorExtensionImpl extends FirScriptConfiguratorExtension {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final HashSet<String> _knownAnnotationsForSamWithReceiver;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirScriptConfiguratorExtensionImpl(FirSession session, ScriptingHostConfiguration hostConfiguration) {
        super(session);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
        this._knownAnnotationsForSamWithReceiver = new HashSet<>();
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirScriptConfiguratorExtension
    public void configureContainingFile(FirScriptBuilder firScriptBuilder, FirFileBuilder fileBuilder) {
        Project project;
        Intrinsics.checkNotNullParameter(firScriptBuilder, "<this>");
        Intrinsics.checkNotNullParameter(fileBuilder, "fileBuilder");
        KtSourceFile sourceFile = fileBuilder.getSourceFile();
        if (sourceFile == null) {
            return;
        }
        ScriptCompilationConfiguration orLoadConfiguration = getOrLoadConfiguration(sourceFile);
        if (orLoadConfiguration != null) {
            List list = (List) orLoadConfiguration.get(ScriptCompilationKt.getDefaultImports(ScriptCompilationConfiguration.INSTANCE));
            if (list != null) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    String string = StringsKt.trim((CharSequence) it.next()).toString();
                    boolean zEndsWith$default = StringsKt.endsWith$default(string, "*", false, 2, (Object) null);
                    if (zEndsWith$default) {
                        string = string.substring(0, string.length() - 2);
                        Intrinsics.checkNotNullExpressionValue(string, "substring(...)");
                    }
                    FqName fqNameFromSegments = FqName.fromSegments(StringsKt.split$default((CharSequence) string, new String[]{"."}, false, 0, 6, (Object) null));
                    Intrinsics.checkNotNullExpressionValue(fqNameFromSegments, "fromSegments(...)");
                    List<FirImport> imports = fileBuilder.getImports();
                    FirImportBuilder firImportBuilder = new FirImportBuilder();
                    KtSourceFile sourceFile2 = fileBuilder.getSourceFile();
                    if (sourceFile2 != null && (project = FirScriptConfigurationExtensionImplKt.project(sourceFile2)) != null) {
                        firImportBuilder.setSource(new KtFakeSourceElement(new KtPsiFactory(project, true).createColon(), KtFakeSourceElementKind.ImplicitImport.INSTANCE));
                    }
                    firImportBuilder.setImportedFqName(fqNameFromSegments);
                    firImportBuilder.setAllUnder(zEndsWith$default);
                    imports.add(firImportBuilder.build());
                }
                return;
            }
            return;
        }
        INSTANCE.getLog().warn("Configuration for " + asString(sourceFile) + " wasn't found. FirScriptBuilder wasn't configured.");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.builder.FirScriptConfiguratorExtension
    public void configure(FirScriptBuilder firScriptBuilder, KtSourceFile sourceFile) {
        FirStatement firStatement;
        FirConstructorSymbol firConstructorSymbolPrimaryConstructorIfAny;
        FirConstructor firConstructor;
        List<FirValueParameter> valueParameters;
        Intrinsics.checkNotNullParameter(firScriptBuilder, "<this>");
        Intrinsics.checkNotNullParameter(sourceFile, "sourceFile");
        ScriptCompilationConfiguration orLoadConfiguration = getOrLoadConfiguration(sourceFile);
        if (orLoadConfiguration != null) {
            KotlinType kotlinType = (KotlinType) orLoadConfiguration.getNoDefault(ScriptCompilationKt.getBaseClass(ScriptCompilationConfiguration.INSTANCE));
            if (kotlinType != null) {
                FqName fqNameFromSegments = FqName.fromSegments(StringsKt.split$default((CharSequence) kotlinType.getTypeName(), new String[]{"."}, false, 0, 6, (Object) null));
                Intrinsics.checkNotNullExpressionValue(fqNameFromSegments, "fromSegments(...)");
                firScriptBuilder.getContextReceivers().add(buildContextReceiverWithFqName(fqNameFromSegments, Name.special(FirScriptUtilsKt.SCRIPT_SPECIAL_NAME_STRING)));
                FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getDependenciesSymbolProvider(getSession()).getClassLikeSymbolByClassId(new ClassId(fqNameFromSegments.parent(), fqNameFromSegments.shortName()));
                FirRegularClassSymbol firRegularClassSymbol = classLikeSymbolByClassId instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) classLikeSymbolByClassId : null;
                if (firRegularClassSymbol != null && (firConstructorSymbolPrimaryConstructorIfAny = DeclarationUtilsKt.primaryConstructorIfAny((FirClass) firRegularClassSymbol.getFir(), getSession())) != null && (firConstructor = (FirConstructor) firConstructorSymbolPrimaryConstructorIfAny.getFir()) != null && (valueParameters = firConstructor.getValueParameters()) != null) {
                    for (FirValueParameter firValueParameter : valueParameters) {
                        List<FirVariable> parameters = firScriptBuilder.getParameters();
                        FirPropertyBuilder firPropertyBuilder = new FirPropertyBuilder();
                        firPropertyBuilder.setModuleData(FirModuleDataKt.getModuleData(getSession()));
                        firPropertyBuilder.setOrigin(FirDeclarationOrigin.ScriptCustomization.Default.INSTANCE);
                        firPropertyBuilder.setReturnTypeRef(firValueParameter.getReturnTypeRef());
                        firPropertyBuilder.setName(firValueParameter.getName());
                        firPropertyBuilder.setSymbol(new FirPropertySymbol(firPropertyBuilder.getName()));
                        firPropertyBuilder.setStatus(new FirDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL));
                        firPropertyBuilder.setLocal(true);
                        firPropertyBuilder.setVar(false);
                        parameters.add(firPropertyBuilder.mo6809build());
                    }
                }
            }
            List<KotlinType> list = (List) orLoadConfiguration.get(ScriptCompilationKt.getImplicitReceivers(ScriptCompilationConfiguration.INSTANCE));
            if (list != null) {
                for (KotlinType kotlinType2 : list) {
                    List<FirContextReceiver> contextReceivers = firScriptBuilder.getContextReceivers();
                    FqName fqNameFromSegments2 = FqName.fromSegments(StringsKt.split$default((CharSequence) kotlinType2.getTypeName(), new String[]{"."}, false, 0, 6, (Object) null));
                    Intrinsics.checkNotNullExpressionValue(fqNameFromSegments2, "fromSegments(...)");
                    contextReceivers.add(buildContextReceiverWithFqName$default(this, fqNameFromSegments2, null, 2, null));
                }
            }
            Map map = (Map) orLoadConfiguration.get(ScriptCompilationKt.getProvidedProperties(ScriptCompilationConfiguration.INSTANCE));
            if (map != null) {
                for (Map.Entry entry : map.entrySet()) {
                    String str = (String) entry.getKey();
                    KotlinType kotlinType3 = (KotlinType) entry.getValue();
                    FirUserTypeRefBuilder firUserTypeRefBuilder = new FirUserTypeRefBuilder();
                    firUserTypeRefBuilder.setMarkedNullable(kotlinType3.getIsNullable());
                    for (String str2 : StringsKt.split$default((CharSequence) kotlinType3.getTypeName(), new String[]{"."}, false, 0, 6, (Object) null)) {
                        List<FirQualifierPart> qualifier = firUserTypeRefBuilder.getQualifier();
                        Name nameIdentifier = Name.identifier(str2);
                        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                        qualifier.add(new FirQualifierPartImpl(null, nameIdentifier, new FirTypeArgumentListImpl(null)));
                    }
                    FirUserTypeRef firUserTypeRefMo6810build = firUserTypeRefBuilder.mo6809build();
                    List<FirVariable> parameters2 = firScriptBuilder.getParameters();
                    FirPropertyBuilder firPropertyBuilder2 = new FirPropertyBuilder();
                    firPropertyBuilder2.setModuleData(FirModuleDataKt.getModuleData(getSession()));
                    firPropertyBuilder2.setOrigin(FirDeclarationOrigin.ScriptCustomization.Default.INSTANCE);
                    firPropertyBuilder2.setReturnTypeRef(firUserTypeRefMo6810build);
                    Name nameIdentifier2 = Name.identifier(str);
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
                    firPropertyBuilder2.setName(nameIdentifier2);
                    firPropertyBuilder2.setSymbol(new FirPropertySymbol(firPropertyBuilder2.getName()));
                    firPropertyBuilder2.setStatus(new FirDeclarationStatusImpl(Visibilities.Local.INSTANCE, Modality.FINAL));
                    firPropertyBuilder2.setLocal(true);
                    firPropertyBuilder2.setVar(false);
                    parameters2.add(firPropertyBuilder2.mo6809build());
                }
            }
            List list2 = (List) orLoadConfiguration.get(ScriptCompilationConfigurationFromDefinitionKt.getAnnotationsForSamWithReceivers(ScriptCompilationConfiguration.INSTANCE));
            if (list2 != null) {
                Iterator it = list2.iterator();
                while (it.hasNext()) {
                    this._knownAnnotationsForSamWithReceiver.add(((KotlinType) it.next()).getTypeName());
                }
            }
            String str3 = (String) orLoadConfiguration.get(ScriptCompilationKt.getResultField(ScriptCompilationConfiguration.INSTANCE));
            if (str3 != null) {
                String str4 = StringsKt.isBlank(str3) ? null : str3;
                if (str4 == null || (firStatement = (FirStatement) CollectionsKt.lastOrNull((List) firScriptBuilder.getStatements())) == null || !(firStatement instanceof FirExpression)) {
                    return;
                }
                firScriptBuilder.getStatements().remove(firScriptBuilder.getStatements().size() - 1);
                List<FirStatement> statements = firScriptBuilder.getStatements();
                FirPropertyBuilder firPropertyBuilder3 = new FirPropertyBuilder();
                Name nameIdentifier3 = Name.identifier(str4);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
                firPropertyBuilder3.setName(nameIdentifier3);
                firPropertyBuilder3.setSymbol(new FirPropertySymbol(firPropertyBuilder3.getName()));
                FirExpression firExpression = (FirExpression) firStatement;
                firPropertyBuilder3.setSource(firExpression.getSource());
                firPropertyBuilder3.setModuleData(FirModuleDataKt.getModuleData(getSession()));
                firPropertyBuilder3.setOrigin(FirDeclarationOrigin.ScriptCustomization.ResultProperty.INSTANCE);
                firPropertyBuilder3.setInitializer(firExpression);
                firPropertyBuilder3.setReturnTypeRef(firExpression.getTypeRef());
                firPropertyBuilder3.setGetter(new FirDefaultPropertyGetter(firExpression.getSource(), FirModuleDataKt.getModuleData(getSession()), FirDeclarationOrigin.ScriptCustomization.ResultProperty.INSTANCE, firExpression.getTypeRef(), Visibilities.Public.INSTANCE, firPropertyBuilder3.getSymbol(), null, null, null, null, XKeySym.XK_Amacron, null));
                firPropertyBuilder3.setStatus(new FirDeclarationStatusImpl(Visibilities.Public.INSTANCE, Modality.FINAL));
                firPropertyBuilder3.setLocal(false);
                firPropertyBuilder3.setVar(false);
                FirProperty firPropertyMo6810build = firPropertyBuilder3.mo6809build();
                firScriptBuilder.setResultPropertyName(firPropertyMo6810build.getName());
                statements.add(firPropertyMo6810build);
                return;
            }
            return;
        }
        INSTANCE.getLog().warn("Configuration for " + asString(sourceFile) + " wasn't found. FirScriptBuilder wasn't configured.");
    }

    private final String asString(KtSourceFile ktSourceFile) {
        String path = ktSourceFile.getPath();
        return path == null ? ktSourceFile.getName() : path;
    }

    private final ScriptCompilationConfiguration getOrLoadConfiguration(KtSourceFile file) {
        ScriptCompilationConfiguration scriptCompilationConfigurationConfigurationFor;
        FirScriptDefinitionProviderService scriptDefinitionProviderService = FirScriptDefinitionProviderServiceKt.getScriptDefinitionProviderService(getSession());
        if (scriptDefinitionProviderService == null) {
            throw new IllegalStateException("Required value was null.".toString());
        }
        SourceCode sourceCode = FirScriptConfigurationExtensionImplKt.toSourceCode(file);
        KtFile ktFileOriginalKtFile = sourceCode != null ? FirScriptConfigurationExtensionImplKt.originalKtFile(sourceCode) : null;
        if (ktFileOriginalKtFile != null && (scriptCompilationConfigurationConfigurationFor = FirScriptConfigurationExtensionImplKt.configurationFor(scriptDefinitionProviderService, ktFileOriginalKtFile)) != null) {
            return scriptCompilationConfigurationConfigurationFor;
        }
        ScriptCompilationConfiguration scriptCompilationConfigurationConfigurationFor2 = sourceCode != null ? FirScriptConfigurationExtensionImplKt.configurationFor(scriptDefinitionProviderService, sourceCode) : null;
        if (scriptCompilationConfigurationConfigurationFor2 != null) {
            return scriptCompilationConfigurationConfigurationFor2;
        }
        ScriptCompilationConfiguration scriptCompilationConfigurationDefaultConfiguration = FirScriptConfigurationExtensionImplKt.defaultConfiguration(scriptDefinitionProviderService);
        if (scriptCompilationConfigurationDefaultConfiguration == null) {
            return null;
        }
        INSTANCE.getLog().debug("Default configuration loaded for " + asString(file));
        return scriptCompilationConfigurationDefaultConfiguration;
    }

    static /* synthetic */ FirContextReceiver buildContextReceiverWithFqName$default(FirScriptConfiguratorExtensionImpl firScriptConfiguratorExtensionImpl, FqName fqName, Name name2, int i, Object obj) {
        if ((i & 2) != 0) {
            name2 = null;
        }
        return firScriptConfiguratorExtensionImpl.buildContextReceiverWithFqName(fqName, name2);
    }

    public final Set<String> getKnownAnnotationsForSamWithReceiver$kotlin_scripting_compiler() {
        return this._knownAnnotationsForSamWithReceiver;
    }

    /* JADX INFO: compiled from: FirScriptConfigurationExtensionImpl.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/services/FirScriptConfiguratorExtensionImpl$Companion;", "", "()V", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "getLog", "()Lcom/intellij/openapi/diagnostic/Logger;", "getFactory", "Lorg/jetbrains/kotlin/fir/builder/FirScriptConfiguratorExtension$Factory;", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Logger getLog() {
            Logger logger = Logger.getInstance((Class<?>) FirScriptConfiguratorExtensionImpl.class);
            Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
            return logger;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final FirScriptConfiguratorExtension getFactory$lambda$0(ScriptingHostConfiguration hostConfiguration, FirSession session) {
            Intrinsics.checkNotNullParameter(hostConfiguration, "$hostConfiguration");
            Intrinsics.checkNotNullParameter(session, "session");
            return new FirScriptConfiguratorExtensionImpl(session, hostConfiguration);
        }

        public final FirScriptConfiguratorExtension.Factory getFactory(final ScriptingHostConfiguration hostConfiguration) {
            Intrinsics.checkNotNullParameter(hostConfiguration, "hostConfiguration");
            return new FirScriptConfiguratorExtension.Factory() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.services.FirScriptConfiguratorExtensionImpl$Companion$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.fir.extensions.FirExtension.Factory
                public final FirExtension create(FirSession firSession) {
                    return FirScriptConfiguratorExtensionImpl.Companion.getFactory$lambda$0(hostConfiguration, firSession);
                }
            };
        }
    }

    private final FirContextReceiver buildContextReceiverWithFqName(FqName classFqn, Name customName) {
        FirContextReceiverBuilder firContextReceiverBuilder = new FirContextReceiverBuilder();
        FirUserTypeRefBuilder firUserTypeRefBuilder = new FirUserTypeRefBuilder();
        firUserTypeRefBuilder.setMarkedNullable(false);
        List<FirQualifierPart> qualifier = firUserTypeRefBuilder.getQualifier();
        List<Name> listPathSegments = classFqn.pathSegments();
        Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
        List<Name> list = listPathSegments;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (Name name2 : list) {
            Intrinsics.checkNotNull(name2);
            arrayList.add(new FirQualifierPartImpl(null, name2, new FirTypeArgumentListImpl(null)));
        }
        qualifier.addAll(arrayList);
        firContextReceiverBuilder.setTypeRef(firUserTypeRefBuilder.mo6809build());
        if (customName != null) {
            firContextReceiverBuilder.setCustomLabelName(customName);
        }
        return firContextReceiverBuilder.build();
    }
}
