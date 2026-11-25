package org.jetbrains.kotlin.fir.backend.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.functions.FunctionTypeKind;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.signature.JvmSignatureWriter;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.FirSessionComponent;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FunctionalTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.types.TypeConstructionUtilsKt;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.types.AbstractTypeMapper;
import org.jetbrains.kotlin.types.TypeMappingContext;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: FirJvmTypeMapper.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001f2\u00020\u0001:\u0003\u001f !B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0015\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0000¢\u0006\u0002\b\u0011J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J>\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0014\u001a\u00020\u00152\b\b\u0002\u0010\u0018\u001a\u00020\u00192\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0018\b\u0002\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u000e\u0018\u00010\u001dJ\u0010\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002R\u0012\u0010\u0005\u001a\u00060\u0006R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmTypeMapper;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", "defaultContext", "Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmTypeMapper$Context;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "typeContext", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "getTypeContext", "()Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "getJvmShortName", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getJvmShortName$jvm_backend", "isPrimitiveBacked", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "mapType", "Lorg/jetbrains/org/objectweb/asm/Type;", "mode", "Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;", "sw", "Lorg/jetbrains/kotlin/codegen/signature/JvmSignatureWriter;", "unresolvedQualifierRemapper", "Lkotlin/Function1;", "safeShortClassName", "Companion", "Context", "PossiblyInnerConeType", "jvm-backend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirJvmTypeMapper implements FirSessionComponent {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ClassId NON_EXISTENT_ID;
    private static final ConeClassLikeType typeForNonExistentClass;
    private final Context defaultContext;
    private final FirSession session;

    /* JADX INFO: compiled from: FirJvmTypeMapper.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmTypeMapper$Companion;", "", "()V", "NON_EXISTENT_ID", "Lorg/jetbrains/kotlin/name/ClassId;", "getNON_EXISTENT_ID", "()Lorg/jetbrains/kotlin/name/ClassId;", "typeForNonExistentClass", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "jvm-backend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ClassId getNON_EXISTENT_ID() {
            return FirJvmTypeMapper.NON_EXISTENT_ID;
        }
    }

    public FirJvmTypeMapper(FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        this.session = session;
        this.defaultContext = new Context(this, new Function1<String, String>() { // from class: org.jetbrains.kotlin.fir.backend.jvm.FirJvmTypeMapper$defaultContext$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return null;
            }
        });
    }

    public final FirSession getSession() {
        return this.session;
    }

    static {
        ClassId classId = ClassId.topLevel(StandardNames.INSTANCE.getNON_EXISTENT_CLASS());
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        NON_EXISTENT_ID = classId;
        typeForNonExistentClass = TypeConstructionUtilsKt.constructClassType$default(TypeConstructionUtilsKt.toLookupTag(classId), new ConeTypeProjection[0], false, null, 4, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Type mapType$default(FirJvmTypeMapper firJvmTypeMapper, ConeKotlinType coneKotlinType, TypeMappingMode typeMappingMode, JvmSignatureWriter jvmSignatureWriter, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            typeMappingMode = TypeMappingMode.DEFAULT;
        }
        if ((i & 4) != 0) {
            jvmSignatureWriter = null;
        }
        if ((i & 8) != 0) {
            function1 = null;
        }
        return firJvmTypeMapper.mapType(coneKotlinType, typeMappingMode, jvmSignatureWriter, function1);
    }

    public final Type mapType(ConeKotlinType type, TypeMappingMode mode, JvmSignatureWriter sw, Function1<? super String, String> unresolvedQualifierRemapper) {
        Context context;
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(mode, "mode");
        if (unresolvedQualifierRemapper != null) {
            context = new Context(this, unresolvedQualifierRemapper);
        } else {
            context = this.defaultContext;
        }
        return AbstractTypeMapper.mapType$default(AbstractTypeMapper.INSTANCE, context, type, mode, sw, false, 16, null);
    }

    public final boolean isPrimitiveBacked(ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return AbstractTypeMapper.INSTANCE.isPrimitiveBacked(this.defaultContext, type);
    }

    public final TypeSystemCommonBackendContext getTypeContext() {
        return this.defaultContext.getTypeContext();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: FirJvmTypeMapper.kt */
    @Metadata(d1 = {"\u0000|\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001b\u0012\u0014\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J&\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0015\u001a\u00020\u00162\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0002H\u0002J4\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0017\u001a\u00020\u00022\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J \u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u001f2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J.\u0010 \u001a\u00020\u00192\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001b2\u0006\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020#H\u0002J \u0010$\u001a\u0004\u0018\u00010\u001f*\u00020%2\b\u0010&\u001a\u0004\u0018\u00010'2\u0006\u0010\"\u001a\u00020#H\u0002J\f\u0010$\u001a\u00020\u001f*\u00020\u0012H\u0002J\u000e\u0010(\u001a\u0004\u0018\u00010'*\u00020%H\u0002J$\u0010)\u001a\u00020\u0019*\u00020\u00022\u0006\u0010\u0011\u001a\u00020*2\u0006\u0010+\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmTypeMapper$Context;", "Lorg/jetbrains/kotlin/types/TypeMappingContext;", "Lorg/jetbrains/kotlin/codegen/signature/JvmSignatureWriter;", "unresolvedQualifierRemapper", "Lkotlin/Function1;", "", "(Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmTypeMapper;Lkotlin/jvm/functions/Function1;)V", "typeContext", "Lorg/jetbrains/kotlin/fir/backend/jvm/ConeTypeSystemCommonBackendContextForTypeMapping;", "getTypeContext", "()Lorg/jetbrains/kotlin/fir/backend/jvm/ConeTypeSystemCommonBackendContextForTypeMapping;", "getClassInternalName", "typeConstructor", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "getScriptInternalName", "hasNothingInNonContravariantPosition", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "mapType", "Lorg/jetbrains/org/objectweb/asm/Type;", "mode", "Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;", "sw", "writeGenericArguments", "", "arguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "parameterSymbols", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmTypeMapper$PossiblyInnerConeType;", "writeInnerParts", "innerTypesAsList", "index", "", "buildPossiblyInnerType", "Lorg/jetbrains/kotlin/fir/types/ConeClassLikeType;", "classifier", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "parentClassOrNull", "writeGenericType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "asmType", "jvm-backend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class Context implements TypeMappingContext<JvmSignatureWriter> {
        final /* synthetic */ FirJvmTypeMapper this$0;
        private final ConeTypeSystemCommonBackendContextForTypeMapping typeContext;

        public Context(FirJvmTypeMapper firJvmTypeMapper, Function1<? super String, String> unresolvedQualifierRemapper) {
            Intrinsics.checkNotNullParameter(unresolvedQualifierRemapper, "unresolvedQualifierRemapper");
            this.this$0 = firJvmTypeMapper;
            this.typeContext = new ConeTypeSystemCommonBackendContextForTypeMapping(TypeComponentsKt.getTypeContext(firJvmTypeMapper.getSession()), unresolvedQualifierRemapper);
        }

        static /* synthetic */ Type mapType$default(Context context, ConeKotlinType coneKotlinType, TypeMappingMode typeMappingMode, JvmSignatureWriter jvmSignatureWriter, int i, Object obj) {
            if ((i & 2) != 0) {
                typeMappingMode = TypeMappingMode.DEFAULT;
            }
            if ((i & 4) != 0) {
                jvmSignatureWriter = null;
            }
            return context.mapType(coneKotlinType, typeMappingMode, jvmSignatureWriter);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Type mapType(ConeKotlinType type, TypeMappingMode mode, JvmSignatureWriter sw) {
            return AbstractTypeMapper.mapType$default(AbstractTypeMapper.INSTANCE, this, type, mode, sw, false, 16, null);
        }

        @Override // org.jetbrains.kotlin.types.TypeMappingContext
        public ConeTypeSystemCommonBackendContextForTypeMapping getTypeContext() {
            return this.typeContext;
        }

        @Override // org.jetbrains.kotlin.types.TypeMappingContext
        public String getClassInternalName(TypeConstructorMarker typeConstructor) {
            String strAsString;
            Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
            if (!(typeConstructor instanceof ConeClassLikeLookupTag)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            ClassId classId = ((ConeClassLikeLookupTag) typeConstructor).getClassId();
            if (classId.isLocal()) {
                strAsString = this.this$0.safeShortClassName(classId);
            } else {
                strAsString = classId.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            }
            return StringsKt.replace$default(strAsString, ".", "$", false, 4, (Object) null);
        }

        @Override // org.jetbrains.kotlin.types.TypeMappingContext
        public String getScriptInternalName(TypeConstructorMarker typeConstructor) {
            Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
            throw new NotImplementedError("An operation is not implemented: Not yet implemented");
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.types.TypeMappingContext
        public void writeGenericType(JvmSignatureWriter jvmSignatureWriter, KotlinTypeMarker type, Type asmType, TypeMappingMode mode) {
            ConeClassLikeType coneClassLikeTypeDefaultType;
            FirRegularClass firRegularClass;
            Intrinsics.checkNotNullParameter(jvmSignatureWriter, "<this>");
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(asmType, "asmType");
            Intrinsics.checkNotNullParameter(mode, "mode");
            if (type instanceof ConeKotlinType) {
                if (!jvmSignatureWriter.skipGenericSignature()) {
                    ConeKotlinType coneKotlinType = (ConeKotlinType) type;
                    if (!hasNothingInNonContravariantPosition(coneKotlinType) && coneKotlinType.getTypeArguments().length != 0) {
                        PossiblyInnerConeType possiblyInnerConeTypeBuildPossiblyInnerType = buildPossiblyInnerType(coneKotlinType);
                        List<PossiblyInnerConeType> listSegments = possiblyInnerConeTypeBuildPossiblyInnerType.segments();
                        Iterator<PossiblyInnerConeType> it = listSegments.iterator();
                        int i = 0;
                        while (true) {
                            if (!it.hasNext()) {
                                i = -1;
                                break;
                            } else if (!it.next().getArguments().isEmpty()) {
                                break;
                            } else {
                                i++;
                            }
                        }
                        if (i < 0 || listSegments.size() == 1) {
                            jvmSignatureWriter.writeClassBegin(asmType);
                            writeGenericArguments(jvmSignatureWriter, possiblyInnerConeTypeBuildPossiblyInnerType, mode);
                        } else {
                            PossiblyInnerConeType possiblyInnerConeType = listSegments.get(i);
                            FirRegularClassSymbol classifier = possiblyInnerConeType.getClassifier();
                            if (classifier == null || (firRegularClass = (FirRegularClass) classifier.getFir()) == null || (coneClassLikeTypeDefaultType = ScopeUtilsKt.defaultType(firRegularClass)) == null) {
                                coneClassLikeTypeDefaultType = FirJvmTypeMapper.typeForNonExistentClass;
                            }
                            jvmSignatureWriter.writeOuterClassBegin(asmType, mapType$default(this, coneClassLikeTypeDefaultType, null, null, 6, null).getInternalName());
                            writeGenericArguments(jvmSignatureWriter, possiblyInnerConeType, mode);
                            writeInnerParts(listSegments, jvmSignatureWriter, mode, i + 1);
                        }
                        jvmSignatureWriter.writeClassEnd();
                        return;
                    }
                }
                jvmSignatureWriter.writeAsmType(asmType);
            }
        }

        private final boolean hasNothingInNonContravariantPosition(ConeKotlinType type) {
            return KotlinTypeMapper.INSTANCE.hasNothingInNonContravariantPosition(getTypeContext(), type);
        }

        private static final PossiblyInnerConeType buildPossiblyInnerType$createForError(ConeKotlinType coneKotlinType) {
            return new PossiblyInnerConeType(null, ArraysKt.toList(coneKotlinType.getTypeArguments()), null);
        }

        /* JADX WARN: Code duplicated, block: B:26:0x005f  */
        private final PossiblyInnerConeType buildPossiblyInnerType(ConeKotlinType coneKotlinType) {
            PossiblyInnerConeType possiblyInnerConeTypeBuildPossiblyInnerType;
            ConeClassLikeLookupTag lookupTag;
            if (!(coneKotlinType instanceof ConeClassLikeType)) {
                return buildPossiblyInnerType$createForError(coneKotlinType);
            }
            ConeClassLikeType coneClassLikeType = (ConeClassLikeType) coneKotlinType;
            FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), this.this$0.getSession());
            if (symbol instanceof FirRegularClassSymbol) {
                possiblyInnerConeTypeBuildPossiblyInnerType = buildPossiblyInnerType(coneClassLikeType, (FirRegularClassSymbol) symbol, 0);
            } else if (symbol instanceof FirTypeAliasSymbol) {
                ConeClassLikeType coneClassLikeTypeFullyExpandedType$default = TypeExpansionUtilsKt.fullyExpandedType$default(coneClassLikeType, this.this$0.getSession(), null, 2, null);
                if (!(coneClassLikeTypeFullyExpandedType$default instanceof ConeClassLikeType)) {
                    coneClassLikeTypeFullyExpandedType$default = null;
                }
                FirClassLikeSymbol<?> symbol2 = (coneClassLikeTypeFullyExpandedType$default == null || (lookupTag = coneClassLikeTypeFullyExpandedType$default.getLookupTag()) == null) ? null : LookupTagUtilsKt.toSymbol(lookupTag, this.this$0.getSession());
                FirRegularClassSymbol firRegularClassSymbol = symbol2 instanceof FirRegularClassSymbol ? (FirRegularClassSymbol) symbol2 : null;
                if (firRegularClassSymbol != null) {
                    possiblyInnerConeTypeBuildPossiblyInnerType = buildPossiblyInnerType(coneClassLikeTypeFullyExpandedType$default, firRegularClassSymbol, 0);
                } else {
                    possiblyInnerConeTypeBuildPossiblyInnerType = null;
                }
            } else {
                possiblyInnerConeTypeBuildPossiblyInnerType = null;
            }
            return possiblyInnerConeTypeBuildPossiblyInnerType == null ? buildPossiblyInnerType$createForError(coneKotlinType) : possiblyInnerConeTypeBuildPossiblyInnerType;
        }

        private final FirRegularClassSymbol parentClassOrNull(ConeClassLikeType coneClassLikeType) {
            ClassId outerClassId;
            ClassId classId = ConeTypeUtilsKt.getClassId(coneClassLikeType);
            if (classId == null || (outerClassId = classId.getOuterClassId()) == null) {
                return null;
            }
            FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(this.this$0.getSession()).getClassLikeSymbolByClassId(outerClassId);
            if (classLikeSymbolByClassId instanceof FirRegularClassSymbol) {
                return (FirRegularClassSymbol) classLikeSymbolByClassId;
            }
            return null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        private final PossiblyInnerConeType buildPossiblyInnerType(ConeClassLikeType coneClassLikeType, FirRegularClassSymbol firRegularClassSymbol, int i) {
            if (firRegularClassSymbol == null) {
                return null;
            }
            FirRegularClass firRegularClass = (FirRegularClass) firRegularClassSymbol.getFir();
            List<FirTypeParameterRef> typeParameters = firRegularClass.getTypeParameters();
            int i2 = 0;
            if (!(typeParameters instanceof Collection) || !typeParameters.isEmpty()) {
                Iterator<T> it = typeParameters.iterator();
                while (it.hasNext()) {
                    if ((((FirTypeParameterRef) it.next()) instanceof FirTypeParameter) && (i2 = i2 + 1) < 0) {
                        CollectionsKt.throwCountOverflow();
                    }
                }
            }
            int i3 = i2 + i;
            if (!firRegularClass.getStatus().isInner()) {
                if (i3 != coneClassLikeType.getTypeArguments().length) {
                    firRegularClass.getSymbol().getClassId().isLocal();
                }
                return new PossiblyInnerConeType(firRegularClassSymbol, ArraysKt.toList(coneClassLikeType.getTypeArguments()).subList(i, coneClassLikeType.getTypeArguments().length), null);
            }
            return new PossiblyInnerConeType(firRegularClassSymbol, ArraysKt.toList(coneClassLikeType.getTypeArguments()).subList(i, i3), buildPossiblyInnerType(coneClassLikeType, parentClassOrNull(ScopeUtilsKt.defaultType(firRegularClass)), i3));
        }

        /* JADX WARN: Multi-variable type inference failed */
        private final void writeGenericArguments(JvmSignatureWriter sw, PossiblyInnerConeType type, TypeMappingMode mode) {
            ConeClassLikeType coneClassLikeTypeDefaultType;
            FirRegularClassSymbol classifier = type.getClassifier();
            FirRegularClass firRegularClass = classifier != null ? (FirRegularClass) classifier.getFir() : null;
            if (firRegularClass == null || (coneClassLikeTypeDefaultType = ScopeUtilsKt.defaultType(firRegularClass)) == null) {
                coneClassLikeTypeDefaultType = FirJvmTypeMapper.typeForNonExistentClass;
            }
            List<FirTypeParameterRef> typeParameters = firRegularClass != null ? firRegularClass.getTypeParameters() : null;
            if (typeParameters == null) {
                typeParameters = CollectionsKt.emptyList();
            }
            List<FirTypeParameterRef> list = typeParameters;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((FirTypeParameterRef) it.next()).getSymbol());
            }
            ArrayList arrayList2 = arrayList;
            List<ConeTypeProjection> arguments = type.getArguments();
            ConeClassLikeType coneClassLikeType = coneClassLikeTypeDefaultType;
            FunctionTypeKind functionTypeKind = FunctionalTypeUtilsKt.functionTypeKind(coneClassLikeType, this.this$0.getSession());
            if (((Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.Function.INSTANCE) || Intrinsics.areEqual(functionTypeKind, FunctionTypeKind.SuspendFunction.INSTANCE)) && arguments.size() > 23) || FunctionalTypeUtilsKt.isReflectFunctionType(coneClassLikeType, this.this$0.getSession())) {
                writeGenericArguments(sw, CollectionsKt.listOf(CollectionsKt.last((List) arguments)), CollectionsKt.listOf(CollectionsKt.last((List) arrayList2)), mode);
            } else {
                writeGenericArguments(sw, arguments, arrayList2, mode);
            }
        }

        private final void writeGenericArguments(JvmSignatureWriter sw, List<? extends ConeTypeProjection> arguments, List<FirTypeParameterSymbol> parameterSymbols, TypeMappingMode mode) {
            KotlinTypeMapper.Companion companion = KotlinTypeMapper.INSTANCE;
            List<FirTypeParameterSymbol> list = parameterSymbols;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(new ConeTypeParameterLookupTag((FirTypeParameterSymbol) it.next()));
            }
            companion.writeGenericArguments(getTypeContext(), sw, arguments, arrayList, mode, new Function3<KotlinTypeMarker, JvmSignatureWriter, TypeMappingMode, Type>() { // from class: org.jetbrains.kotlin.fir.backend.jvm.FirJvmTypeMapper$Context$writeGenericArguments$2$1
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public final Type invoke(KotlinTypeMarker type, JvmSignatureWriter sw2, TypeMappingMode mode2) {
                    Intrinsics.checkNotNullParameter(type, "type");
                    Intrinsics.checkNotNullParameter(sw2, "sw");
                    Intrinsics.checkNotNullParameter(mode2, "mode");
                    return this.this$0.mapType((ConeKotlinType) type, mode2, sw2);
                }
            });
        }

        private final void writeInnerParts(List<PossiblyInnerConeType> innerTypesAsList, JvmSignatureWriter sw, TypeMappingMode mode, int index) {
            ClassId non_existent_id;
            for (PossiblyInnerConeType possiblyInnerConeType : innerTypesAsList.subList(index, innerTypesAsList.size())) {
                FirJvmTypeMapper firJvmTypeMapper = this.this$0;
                FirRegularClassSymbol classifier = possiblyInnerConeType.getClassifier();
                if (classifier == null || (non_existent_id = classifier.getClassId()) == null) {
                    non_existent_id = FirJvmTypeMapper.INSTANCE.getNON_EXISTENT_ID();
                }
                sw.writeInnerClass(firJvmTypeMapper.getJvmShortName$jvm_backend(non_existent_id));
                writeGenericArguments(sw, possiblyInnerConeType, mode);
            }
        }
    }

    /* JADX INFO: compiled from: FirJvmTypeMapper.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B'\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\bJ\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00000\u0005R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0000X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmTypeMapper$PossiblyInnerConeType;", "", "classifier", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "arguments", "", "Lorg/jetbrains/kotlin/fir/types/ConeTypeProjection;", "outerType", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;Ljava/util/List;Lorg/jetbrains/kotlin/fir/backend/jvm/FirJvmTypeMapper$PossiblyInnerConeType;)V", "getArguments", "()Ljava/util/List;", "getClassifier", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "segments", "jvm-backend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class PossiblyInnerConeType {
        private final List<ConeTypeProjection> arguments;
        private final FirRegularClassSymbol classifier;
        private final PossiblyInnerConeType outerType;

        /* JADX WARN: Multi-variable type inference failed */
        public PossiblyInnerConeType(FirRegularClassSymbol firRegularClassSymbol, List<? extends ConeTypeProjection> arguments, PossiblyInnerConeType possiblyInnerConeType) {
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            this.classifier = firRegularClassSymbol;
            this.arguments = arguments;
            this.outerType = possiblyInnerConeType;
        }

        public final FirRegularClassSymbol getClassifier() {
            return this.classifier;
        }

        public final List<ConeTypeProjection> getArguments() {
            return this.arguments;
        }

        public final List<PossiblyInnerConeType> segments() {
            PossiblyInnerConeType possiblyInnerConeType = this.outerType;
            List<PossiblyInnerConeType> listSegments = possiblyInnerConeType != null ? possiblyInnerConeType.segments() : null;
            if (listSegments == null) {
                listSegments = CollectionsKt.emptyList();
            }
            return CollectionsKt.plus((Collection<? extends PossiblyInnerConeType>) listSegments, this);
        }
    }

    public final String getJvmShortName$jvm_backend(ClassId classId) {
        Name shortClassName;
        Intrinsics.checkNotNullParameter(classId, "classId");
        String strAsString = null;
        if (!classId.isLocal()) {
            FqNameUnsafe unsafe = classId.asSingleFqName().toUnsafe();
            JavaToKotlinClassMap javaToKotlinClassMap = JavaToKotlinClassMap.INSTANCE;
            Intrinsics.checkNotNull(unsafe);
            ClassId classIdMapKotlinToJava = javaToKotlinClassMap.mapKotlinToJava(unsafe);
            if (classIdMapKotlinToJava != null && (shortClassName = classIdMapKotlinToJava.getShortClassName()) != null) {
                strAsString = shortClassName.asString();
            }
        }
        return strAsString == null ? safeShortClassName(classId) : strAsString;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String safeShortClassName(ClassId classId) {
        String identifier = SpecialNames.safeIdentifier(classId.getShortClassName()).getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        return identifier;
    }
}
