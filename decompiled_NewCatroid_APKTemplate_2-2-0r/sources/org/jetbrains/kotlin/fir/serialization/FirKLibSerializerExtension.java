package org.jetbrains.kotlin.fir.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.lang.LighterASTNode;
import org.jetbrains.kotlin.com.intellij.openapi.util.Ref;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.resolve.providers.FirProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderKt;
import org.jetbrains.kotlin.fir.serialization.constant.ConstValueProvider;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.types.ConeErrorType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.library.metadata.KlibMetadataProtoBuf;
import org.jetbrains.kotlin.library.metadata.KlibMetadataSerializerProtocol;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.serialization.MutableVersionRequirementTable;
import org.jetbrains.kotlin.protobuf.GeneratedMessageLite;

/* JADX INFO: compiled from: FirKLibSerializerExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¶\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B9\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f¢\u0006\u0002\u0010\rJ\u0017\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0002¢\u0006\u0002\u0010\u001cJ(\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0016J \u0010'\u001a\u00020\u001e2\u0006\u0010(\u001a\u00020)2\u0006\u0010!\u001a\u00020*2\u0006\u0010%\u001a\u00020&H\u0016J\u0018\u0010+\u001a\u00020\u001e2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0016J \u00100\u001a\u00020\u001e2\u0006\u0010,\u001a\u0002012\u0006\u00102\u001a\u00020/2\u0006\u00103\u001a\u00020/H\u0016J*\u00104\u001a\u00020\u001e2\u0006\u00105\u001a\u0002062\u0006\u0010!\u001a\u0002072\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020&H\u0016J*\u00108\u001a\u00020\u001e2\u0006\u00109\u001a\u00020:2\u0006\u0010!\u001a\u00020;2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020&H\u0016J\b\u0010<\u001a\u00020\tH\u0016J\u000e\u0010=\u001a\u0004\u0018\u00010>*\u00020\u001bH\u0002JZ\u0010?\u001a\u00020\u001e\"\u000e\b\u0000\u0010@*\b\u0012\u0004\u0012\u0002H@0A\"\u0014\b\u0001\u0010B*\u000e\u0012\u0004\u0012\u0002H@\u0012\u0004\u0012\u0002HB0C*\u00020\u001b2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u0002H@\u0012\u0004\u0012\u0002HB0C2\u0012\u0010D\u001a\u000e\u0012\u0004\u0012\u0002H@\u0012\u0004\u0012\u00020\u00190EH\u0002JZ\u0010F\u001a\u00020\u001e\"\u000e\b\u0000\u0010@*\b\u0012\u0004\u0012\u0002H@0A\"\u0014\b\u0001\u0010B*\u000e\u0012\u0004\u0012\u0002H@\u0012\u0004\u0012\u0002HB0C*\u00020\u001b2\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u0002H@\u0012\u0004\u0012\u0002HB0C2\u0012\u0010D\u001a\u000e\u0012\u0004\u0012\u0002H@\u0012\u0004\u0012\u00020>0EH\u0002R\u0016\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0094\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006G"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/FirKLibSerializerExtension;", "Lorg/jetbrains/kotlin/fir/serialization/FirSerializerExtensionBase;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "constValueProvider", "Lorg/jetbrains/kotlin/fir/serialization/constant/ConstValueProvider;", "allowErrorTypes", "", "exportKDoc", "additionalAnnotationsProvider", "Lorg/jetbrains/kotlin/fir/serialization/FirAdditionalMetadataAnnotationsProvider;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;Lorg/jetbrains/kotlin/fir/serialization/constant/ConstValueProvider;ZZLorg/jetbrains/kotlin/fir/serialization/FirAdditionalMetadataAnnotationsProvider;)V", "getAdditionalAnnotationsProvider", "()Lorg/jetbrains/kotlin/fir/serialization/FirAdditionalMetadataAnnotationsProvider;", "getConstValueProvider", "()Lorg/jetbrains/kotlin/fir/serialization/constant/ConstValueProvider;", "firProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirProvider;", "getMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "declarationFileId", "", "declaration", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;)Ljava/lang/Integer;", "serializeClass", "", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class$Builder;", "versionRequirementTable", "Lorg/jetbrains/kotlin/metadata/serialization/MutableVersionRequirementTable;", "childSerializer", "Lorg/jetbrains/kotlin/fir/serialization/FirElementSerializer;", "serializeConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Constructor$Builder;", "serializeErrorType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeErrorType;", "builder", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Builder;", "serializeFlexibleType", "Lorg/jetbrains/kotlin/fir/types/ConeFlexibleType;", "lowerProto", "upperProto", "serializeFunction", "function", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Function$Builder;", "serializeProperty", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Property$Builder;", "shouldUseTypeTable", "findKDocString", "", "setFileId", "MessageType", "Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$ExtendableMessage;", "BuilderType", "Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$ExtendableBuilder;", SchemaSymbols.ATTVAL_EXTENSION, "Lorg/jetbrains/kotlin/protobuf/GeneratedMessageLite$GeneratedExtension;", "setKDoc", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirKLibSerializerExtension extends FirSerializerExtensionBase {
    private final FirAdditionalMetadataAnnotationsProvider additionalAnnotationsProvider;
    private final boolean allowErrorTypes;
    private final ConstValueProvider constValueProvider;
    private final boolean exportKDoc;
    private final FirProvider firProvider;
    private final BinaryVersion metadataVersion;
    private final FirSession session;

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    /* JADX INFO: renamed from: shouldUseTypeTable */
    public boolean getUseTypeTable() {
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public FirSession getSession() {
        return this.session;
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public BinaryVersion getMetadataVersion() {
        return this.metadataVersion;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public ConstValueProvider getConstValueProvider() {
        return this.constValueProvider;
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    protected FirAdditionalMetadataAnnotationsProvider getAdditionalAnnotationsProvider() {
        return this.additionalAnnotationsProvider;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirKLibSerializerExtension(FirSession session, BinaryVersion metadataVersion, ConstValueProvider constValueProvider, boolean z, boolean z2, FirAdditionalMetadataAnnotationsProvider firAdditionalMetadataAnnotationsProvider) {
        super(KlibMetadataSerializerProtocol.INSTANCE);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        this.session = session;
        this.metadataVersion = metadataVersion;
        this.constValueProvider = constValueProvider;
        this.allowErrorTypes = z;
        this.exportKDoc = z2;
        this.additionalAnnotationsProvider = firAdditionalMetadataAnnotationsProvider;
        this.firProvider = FirProviderKt.getFirProvider(getSession());
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeFlexibleType(ConeFlexibleType type, ProtoBuf.Type.Builder lowerProto, ProtoBuf.Type.Builder upperProto) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(lowerProto, "lowerProto");
        Intrinsics.checkNotNullParameter(upperProto, "upperProto");
        lowerProto.setFlexibleTypeCapabilitiesId(getStringTable().getStringIndex("kotlin.DynamicType"));
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeErrorType(ConeErrorType type, ProtoBuf.Type.Builder builder) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(builder, "builder");
        if (this.allowErrorTypes) {
            return;
        }
        super.serializeErrorType(type, builder);
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtensionBase, org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeClass(FirClass klass, ProtoBuf.Class.Builder proto, MutableVersionRequirementTable versionRequirementTable, FirElementSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(versionRequirementTable, "versionRequirementTable");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        FirClass firClass = klass;
        ProtoBuf.Class.Builder builder = proto;
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, Integer> classFile = KlibMetadataProtoBuf.classFile;
        Intrinsics.checkNotNullExpressionValue(classFile, "classFile");
        setFileId(firClass, builder, classFile);
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Class, String> classKdoc = KlibMetadataProtoBuf.classKdoc;
        Intrinsics.checkNotNullExpressionValue(classKdoc, "classKdoc");
        setKDoc(firClass, builder, classKdoc);
        super.serializeClass(klass, proto, versionRequirementTable, childSerializer);
        ProtoBuf.TypeTable typeTableSerialize = childSerializer.getTypeTable().serialize();
        if (typeTableSerialize != null) {
            proto.mergeTypeTable(typeTableSerialize);
        }
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtensionBase, org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeConstructor(FirConstructor constructor, ProtoBuf.Constructor.Builder proto, FirElementSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Constructor, String> constructorKdoc = KlibMetadataProtoBuf.constructorKdoc;
        Intrinsics.checkNotNullExpressionValue(constructorKdoc, "constructorKdoc");
        setKDoc(constructor, proto, constructorKdoc);
        super.serializeConstructor(constructor, proto, childSerializer);
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtensionBase, org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeProperty(FirProperty property, ProtoBuf.Property.Builder proto, MutableVersionRequirementTable versionRequirementTable, FirElementSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        FirProperty firProperty = property;
        ProtoBuf.Property.Builder builder = proto;
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, Integer> propertyFile = KlibMetadataProtoBuf.propertyFile;
        Intrinsics.checkNotNullExpressionValue(propertyFile, "propertyFile");
        setFileId(firProperty, builder, propertyFile);
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Property, String> propertyKdoc = KlibMetadataProtoBuf.propertyKdoc;
        Intrinsics.checkNotNullExpressionValue(propertyKdoc, "propertyKdoc");
        setKDoc(firProperty, builder, propertyKdoc);
        super.serializeProperty(property, proto, versionRequirementTable, childSerializer);
    }

    @Override // org.jetbrains.kotlin.fir.serialization.FirSerializerExtensionBase, org.jetbrains.kotlin.fir.serialization.FirSerializerExtension
    public void serializeFunction(FirFunction function, ProtoBuf.Function.Builder proto, MutableVersionRequirementTable versionRequirementTable, FirElementSerializer childSerializer) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(childSerializer, "childSerializer");
        FirFunction firFunction = function;
        ProtoBuf.Function.Builder builder = proto;
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, Integer> functionFile = KlibMetadataProtoBuf.functionFile;
        Intrinsics.checkNotNullExpressionValue(functionFile, "functionFile");
        setFileId(firFunction, builder, functionFile);
        GeneratedMessageLite.GeneratedExtension<ProtoBuf.Function, String> functionKdoc = KlibMetadataProtoBuf.functionKdoc;
        Intrinsics.checkNotNullExpressionValue(functionKdoc, "functionKdoc");
        setKDoc(firFunction, builder, functionKdoc);
        super.serializeFunction(function, proto, versionRequirementTable, childSerializer);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <MessageType extends GeneratedMessageLite.ExtendableMessage<MessageType>, BuilderType extends GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType>> void setKDoc(FirDeclaration firDeclaration, GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType> extendableBuilder, GeneratedMessageLite.GeneratedExtension<MessageType, String> generatedExtension) {
        String strFindKDocString;
        if (!this.exportKDoc || (strFindKDocString = findKDocString(firDeclaration)) == null) {
            return;
        }
        extendableBuilder.setExtension(generatedExtension, strFindKDocString);
    }

    private final String findKDocString(FirDeclaration firDeclaration) {
        KtSourceElement source = firDeclaration.getSource();
        if (source == null) {
            return null;
        }
        Ref<LighterASTNode[]> ref = new Ref<>();
        source.getTreeStructure().getChildren(source.getLighterASTNode(), ref);
        LighterASTNode[] lighterASTNodeArr = ref.get();
        Intrinsics.checkNotNullExpressionValue(lighterASTNodeArr, "get(...)");
        LighterASTNode[] lighterASTNodeArr2 = lighterASTNodeArr;
        int length = lighterASTNodeArr2.length;
        int i = 0;
        LighterASTNode lighterASTNode = null;
        boolean z = false;
        while (true) {
            if (i >= length) {
                if (!z) {
                    break;
                }
                break;
            }
            LighterASTNode lighterASTNode2 = lighterASTNodeArr2[i];
            LighterASTNode lighterASTNode3 = lighterASTNode2;
            if (Intrinsics.areEqual(lighterASTNode3 != null ? lighterASTNode3.getTokenType() : null, KtTokens.DOC_COMMENT)) {
                if (!z) {
                    z = true;
                    lighterASTNode = lighterASTNode2;
                }
            }
            i++;
            lighterASTNode = null;
            break;
        }
        LighterASTNode lighterASTNode4 = lighterASTNode;
        if (lighterASTNode4 != null) {
            return lighterASTNode4.toString();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final <MessageType extends GeneratedMessageLite.ExtendableMessage<MessageType>, BuilderType extends GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType>> void setFileId(FirDeclaration firDeclaration, GeneratedMessageLite.ExtendableBuilder<MessageType, BuilderType> extendableBuilder, GeneratedMessageLite.GeneratedExtension<MessageType, Integer> generatedExtension) {
        Integer numDeclarationFileId = declarationFileId(firDeclaration);
        if (numDeclarationFileId != null) {
            extendableBuilder.setExtension(generatedExtension, Integer.valueOf(numDeclarationFileId.intValue()));
        }
    }

    private final Integer declarationFileId(FirDeclaration declaration) {
        FirFile firClassifierContainerFileIfAny;
        FirBasedSymbol<? extends FirDeclaration> symbol = declaration.getSymbol();
        if (symbol instanceof FirCallableSymbol) {
            firClassifierContainerFileIfAny = this.firProvider.getFirCallableContainerFile((FirCallableSymbol) symbol);
        } else {
            firClassifierContainerFileIfAny = symbol instanceof FirClassLikeSymbol ? this.firProvider.getFirClassifierContainerFileIfAny((FirClassLikeSymbol<?>) symbol) : null;
        }
        if (firClassifierContainerFileIfAny == null) {
            return null;
        }
        return Integer.valueOf(getStringTable().getStringIndex(firClassifierContainerFileIfAny.getName()));
    }
}
