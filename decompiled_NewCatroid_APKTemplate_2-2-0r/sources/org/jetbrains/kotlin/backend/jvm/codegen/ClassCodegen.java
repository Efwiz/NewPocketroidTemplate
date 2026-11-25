package org.jetbrains.kotlin.backend.jvm.codegen;

import com.badlogic.gdx.physics.bullet.collision.ContactCallbackEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import net.bytebuddy.description.method.MethodDescription;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.backend.common.lower.AnnotationImplementationTransformerKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmBackendExtension;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.MultifileFacadeFileEntry;
import org.jetbrains.kotlin.backend.jvm.extensions.JvmIrDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.extensions.JvmIrDeclarationOriginKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrCoroutineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrInlineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMappingKt;
import org.jetbrains.kotlin.backend.jvm.mapping.MethodSignatureMapper;
import org.jetbrains.kotlin.backend.jvm.metadata.MetadataSerializer;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.ClassBuilder;
import org.jetbrains.kotlin.codegen.ClassBuilderRecordKt;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.codegen.SourceInfo;
import org.jetbrains.kotlin.codegen.WriteAnnotationUtilKt;
import org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt;
import org.jetbrains.kotlin.codegen.inline.NameGenerator;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeParametersUsages;
import org.jetbrains.kotlin.codegen.inline.SMAP;
import org.jetbrains.kotlin.codegen.inline.SMAPAndMethodNode;
import org.jetbrains.kotlin.codegen.inline.SourceMapCopier;
import org.jetbrains.kotlin.codegen.inline.SourceMapper;
import org.jetbrains.kotlin.codegen.serialization.JvmSerializationBindings;
import org.jetbrains.kotlin.codegen.signature.JvmSignatureWriter;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.config.JvmTarget;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockBodyImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrSetFieldImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.kotlin.SignatureBuildingComponentsKt;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.kotlin.metadata.jvm.deserialization.BitEncoding;
import org.jetbrains.kotlin.metadata.jvm.serialization.JvmStringTable;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.resolve.jvm.checkers.JvmSimpleNameBacktickChecker;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.MemberKind;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.RawSignature;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmClassSignature;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.ClassVisitor;
import org.jetbrains.org.objectweb.asm.FieldVisitor;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.TypePath;
import org.jetbrains.org.objectweb.asm.TypeReference;
import org.jetbrains.org.objectweb.asm.commons.Method;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: ClassCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000º\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 c2\u00020\u0001:\u0001cB!\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020\u0003J\u0006\u0010I\u001a\u00020GJ\u0010\u0010J\u001a\u0004\u0018\u00010K2\u0006\u0010L\u001a\u00020\fJ\u0010\u0010M\u001a\u00020G2\u0006\u0010N\u001a\u00020OH\u0002J\b\u0010P\u001a\u00020GH\u0002J\b\u0010Q\u001a\u00020GH\u0002J\u0018\u0010R\u001a\u00020G2\u0006\u0010S\u001a\u00020\u00072\u0006\u0010T\u001a\u00020,H\u0002J\u000e\u0010U\u001a\u00020\u000f2\u0006\u0010S\u001a\u00020\u0007J\b\u0010V\u001a\u00020GH\u0002J\u000e\u0010W\u001a\u00020&2\u0006\u0010X\u001a\u00020\u0007J\b\u0010Y\u001a\u00020\fH\u0002J\u0010\u0010Z\u001a\u00020G2\u0006\u0010[\u001a\u00020\\H\u0002J\u0012\u0010]\u001a\b\u0012\u0004\u0012\u00020_0^*\u00020`H\u0002J\u000e\u0010a\u001a\u0004\u0018\u00010b*\u00020\u0003H\u0002R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00030\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0016\u001a\u00020\u00178BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001c\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u001d\u0010 \u001a\u0004\u0018\u00010\u00008BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u001b\u001a\u0004\b!\u0010\"R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010$\u001a\u000e\u0012\u0004\u0012\u00020%\u0012\u0004\u0012\u00020&0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010'\u001a\u00020(¢\u0006\b\n\u0000\u001a\u0004\b)\u0010*R\u0011\u0010+\u001a\u00020,¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0014\u0010/\u001a\u0002008BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b1\u00102R\u0011\u00103\u001a\u000204¢\u0006\b\n\u0000\u001a\u0004\b5\u00106R\u0011\u00107\u001a\u000208¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u000e\u0010;\u001a\u00020<X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010=\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b@\u0010\u001b\u001a\u0004\b>\u0010?R\u0018\u0010A\u001a\u00020\f*\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bA\u0010BR\u0018\u0010C\u001a\u00020\f*\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bC\u0010BR\u0018\u0010D\u001a\u00020\f*\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bD\u0010BR\u0018\u0010E\u001a\u00020\f*\u00020\u00038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bE\u0010B¨\u0006d"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "parentFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "generated", "", "generatedInlineMethods", "", "Lorg/jetbrains/kotlin/codegen/inline/SMAPAndMethodNode;", "innerClasses", "", "getIrClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "jvmSignatureClashDetector", "Lorg/jetbrains/kotlin/backend/jvm/codegen/JvmSignatureClashDetector;", "metadataSerializer", "Lorg/jetbrains/kotlin/backend/jvm/metadata/MetadataSerializer;", "getMetadataSerializer", "()Lorg/jetbrains/kotlin/backend/jvm/metadata/MetadataSerializer;", "metadataSerializer$delegate", "Lkotlin/Lazy;", "methodSignatureMapper", "Lorg/jetbrains/kotlin/backend/jvm/mapping/MethodSignatureMapper;", "getMethodSignatureMapper", "()Lorg/jetbrains/kotlin/backend/jvm/mapping/MethodSignatureMapper;", "parentClassCodegen", "getParentClassCodegen", "()Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "parentClassCodegen$delegate", "regeneratedObjectNameGenerators", "", "Lorg/jetbrains/kotlin/codegen/inline/NameGenerator;", "reifiedTypeParametersUsages", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", "getReifiedTypeParametersUsages", "()Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", "smap", "Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "getSmap", "()Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "getState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "getType", "()Lorg/jetbrains/org/objectweb/asm/Type;", "typeMapper", "Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;", "getTypeMapper", "()Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;", "visitor", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "withinInline", "getWithinInline", "()Z", "withinInline$delegate", "isAnnotationImplementation", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Z", "isAnonymousInnerClass", "isInlineSamWrapper", "isSamWrapper", "addInnerClassInfo", "", "innerClass", CompilerOptions.GENERATE, "generateAssertFieldIfNeeded", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "generatingClInit", "generateField", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "generateInnerAndOuterClasses", "generateKotlinMetadataAnnotation", "generateMethod", "method", "classSMAP", "generateMethodNode", "generatePermittedSubclasses", "getRegeneratedObjectNameGenerator", "function", "shouldSkipCodeGenerationAccordingToGenerationFilter", "storeSerializedIr", "serializedIr", "", "loadSourceFilesInfo", "", "Ljava/io/File;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "mapToJava", "Lorg/jetbrains/kotlin/name/ClassId;", "Companion", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassCodegen {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final JvmBackendContext context;
    private boolean generated;
    private final Map<IrFunction, SMAPAndMethodNode> generatedInlineMethods;
    private final Set<IrClass> innerClasses;
    private final IrClass irClass;
    private final JvmSignatureClashDetector jvmSignatureClashDetector;

    /* JADX INFO: renamed from: metadataSerializer$delegate, reason: from kotlin metadata */
    private final Lazy metadataSerializer;
    private final MethodSignatureMapper methodSignatureMapper;

    /* JADX INFO: renamed from: parentClassCodegen$delegate, reason: from kotlin metadata */
    private final Lazy parentClassCodegen;
    private final IrFunction parentFunction;
    private final Map<String, NameGenerator> regeneratedObjectNameGenerators;
    private final ReifiedTypeParametersUsages reifiedTypeParametersUsages;
    private final SourceMapper smap;
    private final Type type;
    private final IrTypeMapper typeMapper;
    private final ClassBuilder visitor;

    /* JADX INFO: renamed from: withinInline$delegate, reason: from kotlin metadata */
    private final Lazy withinInline;

    public /* synthetic */ ClassCodegen(IrClass irClass, JvmBackendContext jvmBackendContext, IrFunction irFunction, DefaultConstructorMarker defaultConstructorMarker) {
        this(irClass, jvmBackendContext, irFunction);
    }

    private ClassCodegen(IrClass irClass, final JvmBackendContext jvmBackendContext, IrFunction irFunction) {
        IrTypeMapper defaultTypeMapper;
        this.irClass = irClass;
        this.context = jvmBackendContext;
        this.parentFunction = irFunction;
        this.parentClassCodegen = LazyKt.lazy(new Function0<ClassCodegen>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen$parentClassCodegen$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassCodegen invoke() {
                IrClass irClass2;
                IrFunction irFunction2 = this.this$0.parentFunction;
                if (irFunction2 == null || (parentAsClass = IrUtilsKt.getParentAsClass(irFunction2)) == null) {
                    IrDeclarationParent parent = this.this$0.getIrClass().getParent();
                    if (parent instanceof IrClass) {
                        IrClass parentAsClass = (IrClass) parent;
                        irClass2 = parentAsClass;
                    } else {
                        irClass2 = null;
                    }
                } else {
                    irClass2 = parentAsClass;
                }
                if (irClass2 != null) {
                    return ClassCodegen.Companion.getOrCreate$default(ClassCodegen.INSTANCE, irClass2, this.this$0.getContext(), null, 4, null);
                }
                return null;
            }
        });
        this.withinInline = LazyKt.lazy(new Function0<Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen$withinInline$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() {
                IrFunction irFunction2;
                ClassCodegen parentClassCodegen = this.this$0.getParentClassCodegen();
                boolean z = true;
                if ((parentClassCodegen == null || !parentClassCodegen.getWithinInline()) && ((irFunction2 = this.this$0.parentFunction) == null || !irFunction2.getIsInline())) {
                    z = false;
                }
                return Boolean.valueOf(z);
            }
        });
        this.metadataSerializer = LazyKt.lazy(new Function0<MetadataSerializer>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen$metadataSerializer$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final MetadataSerializer invoke() {
                JvmBackendExtension backendExtension = this.this$0.getContext().getBackendExtension();
                JvmBackendContext context = this.this$0.getContext();
                IrClass irClass2 = this.this$0.getIrClass();
                Type type = this.this$0.getType();
                JvmSerializationBindings serializationBindings = this.this$0.visitor.getSerializationBindings();
                Intrinsics.checkNotNullExpressionValue(serializationBindings, "getSerializationBindings(...)");
                ClassCodegen parentClassCodegen = this.this$0.getParentClassCodegen();
                return backendExtension.createSerializer(context, irClass2, type, serializationBindings, parentClassCodegen != null ? parentClassCodegen.getMetadataSerializer() : null);
            }
        });
        this.smap = IrCodegenUtilsKt.getSourceMapper(jvmBackendContext, irClass);
        this.innerClasses = new LinkedHashSet();
        if (jvmBackendContext.getConfig().getOldInnerClassesLogic()) {
            defaultTypeMapper = jvmBackendContext.getDefaultTypeMapper();
        } else {
            defaultTypeMapper = new IrTypeMapper(jvmBackendContext) { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen$typeMapper$1
                @Override // org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper
                public Type mapType(IrType type, TypeMappingMode mode, JvmSignatureWriter sw, boolean materialized) {
                    IrClassSymbol classOrNull;
                    IrClass owner;
                    Intrinsics.checkNotNullParameter(type, "type");
                    Intrinsics.checkNotNullParameter(mode, "mode");
                    IrType arrayElementType = type;
                    while (IrTypePredicatesKt.isArray(arrayElementType)) {
                        arrayElementType = IrTypeUtilsKt.getArrayElementType(arrayElementType, this.this$0.getContext().getIrBuiltIns());
                    }
                    if (materialized && (classOrNull = IrTypesKt.getClassOrNull(arrayElementType)) != null && (owner = classOrNull.getOwner()) != null) {
                        this.this$0.addInnerClassInfo(owner);
                    }
                    return super.mapType(type, mode, sw, materialized);
                }
            };
        }
        this.typeMapper = defaultTypeMapper;
        this.methodSignatureMapper = new MethodSignatureMapper(jvmBackendContext, defaultTypeMapper);
        Type typeMapClass = IrTypeMappingKt.mapClass(defaultTypeMapper, irClass);
        this.type = typeMapClass;
        this.reifiedTypeParametersUsages = new ReifiedTypeParametersUsages();
        this.jvmSignatureClashDetector = new JvmSignatureClashDetector(this);
        ClassBuilder classBuilderNewVisitor = getState().getFactory().newVisitor((JvmDeclarationOrigin) JvmIrDeclarationOriginKt.getDescriptorOrigin(irClass), typeMapClass, loadSourceFilesInfo(JvmIrUtilsKt.getFileParent(irClass)));
        JvmClassSignature jvmClassSignatureMapClassSignature = IrCodegenUtilsKt.mapClassSignature(defaultTypeMapper, irClass, typeMapClass, jvmBackendContext.getState().getClassBuilderMode().generateBodies);
        if (jvmBackendContext.getState().getClassBuilderMode().generateBodies && INSTANCE.hasInvalidName(jvmClassSignatureMapClassSignature)) {
            throw new IllegalStateException("Generating class with invalid name '" + typeMapClass.getClassName() + "': " + DumpIrTreeKt.dump$default(irClass, null, 1, null));
        }
        PsiElement psiElement = JvmIrUtilsKt.getPsiElement(irClass);
        int classFileVersion = getState().getConfig().getClassFileVersion();
        int flags = ClassCodegenKt.getFlags(irClass, jvmBackendContext.getState().getLanguageVersionSettings());
        String name2 = jvmClassSignatureMapClassSignature.getName();
        String javaGenericSignature = jvmClassSignatureMapClassSignature.getJavaGenericSignature();
        String superclassName = jvmClassSignatureMapClassSignature.getSuperclassName();
        List<String> interfaces = jvmClassSignatureMapClassSignature.getInterfaces();
        Intrinsics.checkNotNullExpressionValue(interfaces, "getInterfaces(...)");
        classBuilderNewVisitor.defineClass(psiElement, classFileVersion, flags, name2, javaGenericSignature, superclassName, (String[]) interfaces.toArray(new String[0]));
        Intrinsics.checkNotNullExpressionValue(classBuilderNewVisitor, "apply(...)");
        this.visitor = classBuilderNewVisitor;
        this.regeneratedObjectNameGenerators = new LinkedHashMap();
        this.generatedInlineMethods = new LinkedHashMap();
    }

    public final IrClass getIrClass() {
        return this.irClass;
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ClassCodegen getParentClassCodegen() {
        return (ClassCodegen) this.parentClassCodegen.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getWithinInline() {
        return ((Boolean) this.withinInline.getValue()).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final MetadataSerializer getMetadataSerializer() {
        return (MetadataSerializer) this.metadataSerializer.getValue();
    }

    public final SourceMapper getSmap() {
        return this.smap;
    }

    private final GenerationState getState() {
        return this.context.getState();
    }

    public final IrTypeMapper getTypeMapper() {
        return this.typeMapper;
    }

    public final MethodSignatureMapper getMethodSignatureMapper() {
        return this.methodSignatureMapper;
    }

    public final Type getType() {
        return this.type;
    }

    public final ReifiedTypeParametersUsages getReifiedTypeParametersUsages() {
        return this.reifiedTypeParametersUsages;
    }

    public final NameGenerator getRegeneratedObjectNameGenerator(IrFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        String str = function.getName().isSpecial() ? InlineCodegenUtilsKt.SPECIAL_TRANSFORMATION_NAME : "$" + function.getName().asString();
        Map<String, NameGenerator> map = this.regeneratedObjectNameGenerators;
        NameGenerator nameGenerator = map.get(str);
        if (nameGenerator == null) {
            nameGenerator = new NameGenerator(this.type.getInternalName() + str + InlineCodegenUtilsKt.INLINE_CALL_TRANSFORMATION_SUFFIX);
            map.put(str, nameGenerator);
        }
        return nameGenerator;
    }

    public final void generate() {
        IrSimpleFunction next;
        if (this.generated) {
            return;
        }
        this.generated = true;
        if (shouldSkipCodeGenerationAccordingToGenerationFilter()) {
            return;
        }
        if (getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.JvmPermittedSubclassesAttributeForSealed) && this.irClass.getModality() == Modality.SEALED && getState().getConfig().getTarget().compareTo(JvmTarget.JVM_17) >= 0) {
            generatePermittedSubclasses();
        }
        List<IrDeclaration> declarations = this.irClass.getDeclarations();
        ArrayList<IrFunction> arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrFunction) {
                arrayList.add(obj);
            }
        }
        for (IrFunction irFunction : arrayList) {
            if (!Intrinsics.areEqual(irFunction.getName().asString(), MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME) && !Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.INLINE_LAMBDA.INSTANCE) && !Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.ADAPTER_FOR_FUN_INTERFACE_CONSTRUCTOR.INSTANCE) && (!Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.ADAPTER_FOR_CALLABLE_REFERENCE.INSTANCE) || irFunction.getBody() != null)) {
                generateMethod(irFunction, this.smap);
            }
        }
        Iterator<IrSimpleFunction> it = IrUtilsKt.getFunctions(this.irClass).iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(next.getName().asString(), MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME));
        IrSimpleFunction irSimpleFunction = next;
        if (irSimpleFunction != null) {
            generateMethod(irSimpleFunction, this.smap);
        }
        Iterator<IrField> it2 = IrUtilsKt.getFields(this.irClass).iterator();
        while (it2.hasNext()) {
            generateField(it2.next());
        }
        for (IrDeclaration irDeclaration : this.irClass.getDeclarations()) {
            if (irDeclaration instanceof IrClass) {
                Companion.getOrCreate$default(INSTANCE, (IrClass) irDeclaration, this.context, null, 4, null).generate();
            }
        }
        new AnnotationCodegen() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen.generate.3
            {
                super(ClassCodegen.this, false, 2, null);
            }

            @Override // org.jetbrains.kotlin.backend.jvm.codegen.AnnotationCodegen
            public AnnotationVisitor visitAnnotation(String descr, boolean visible) {
                Intrinsics.checkNotNullParameter(descr, "descr");
                AnnotationVisitor annotationVisitorVisitAnnotation = ClassCodegen.this.visitor.getVisitor().visitAnnotation(descr, visible);
                Intrinsics.checkNotNullExpressionValue(annotationVisitorVisitAnnotation, "visitAnnotation(...)");
                return annotationVisitorVisitAnnotation;
            }
        }.genAnnotations(this.irClass, null, null);
        AnnotationCodegen.INSTANCE.genAnnotationsOnTypeParametersAndBounds(this.context, this.irClass, this, 0, 17, new Function4<Integer, TypePath, String, Boolean, AnnotationVisitor>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen.generate.4
            {
                super(4);
            }

            @Override // kotlin.jvm.functions.Function4
            public /* bridge */ /* synthetic */ AnnotationVisitor invoke(Integer num, TypePath typePath, String str, Boolean bool) {
                return invoke(num.intValue(), typePath, str, bool.booleanValue());
            }

            public final AnnotationVisitor invoke(int i, TypePath typePath, String descriptor, boolean z) {
                Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                AnnotationVisitor annotationVisitorVisitTypeAnnotation = ClassCodegen.this.visitor.getVisitor().visitTypeAnnotation(i, typePath, descriptor, z);
                Intrinsics.checkNotNullExpressionValue(annotationVisitorVisitTypeAnnotation, "visitTypeAnnotation(...)");
                return annotationVisitorVisitTypeAnnotation;
            }
        });
        generateKotlinMetadataAnnotation();
        if (getWithinInline() || !this.smap.isTrivial()) {
            this.visitor.visitSMAP(this.smap, true ^ this.context.getState().getLanguageVersionSettings().supportsFeature(LanguageFeature.CorrectSourceMappingSyntax));
        } else {
            SourceInfo sourceInfo = this.smap.getSourceInfo();
            Intrinsics.checkNotNull(sourceInfo);
            String sourceFileName = sourceInfo.getSourceFileName();
            if (sourceFileName != null) {
                this.visitor.visitSource(sourceFileName, null);
            }
        }
        this.reifiedTypeParametersUsages.mergeAll(IrCodegenUtilsKt.getReifiedTypeParameters(this.irClass));
        generateInnerAndOuterClasses();
        this.visitor.done(getState().getConfig().getGenerateSmapCopyToAnnotation());
        this.jvmSignatureClashDetector.reportErrors();
    }

    private final boolean shouldSkipCodeGenerationAccordingToGenerationFilter() {
        GenerationState.GenerateClassFilter generateDeclaredClassFilter = getState().getGenerateDeclaredClassFilter();
        PsiElement element = JvmIrDeclarationOriginKt.getDescriptorOrigin(this.irClass).getElement();
        KtFile ktFile = element instanceof KtFile ? (KtFile) element : null;
        PsiElement psiElement = JvmIrUtilsKt.getPsiElement(this.irClass);
        KtClassOrObject ktClassOrObject = psiElement instanceof KtClassOrObject ? (KtClassOrObject) psiElement : null;
        return ((ktFile == null || generateDeclaredClassFilter.shouldGeneratePackagePart(ktFile)) && (ktClassOrObject == null || generateDeclaredClassFilter.shouldGenerateClass(ktClassOrObject))) ? false : true;
    }

    private final void generatePermittedSubclasses() {
        List<IrClassSymbol> sealedSubclasses = this.irClass.getSealedSubclasses();
        if (sealedSubclasses.isEmpty()) {
            return;
        }
        ClassVisitor visitor = this.visitor.getVisitor();
        Intrinsics.checkNotNullExpressionValue(visitor, "getVisitor(...)");
        Iterator<IrClassSymbol> it = sealedSubclasses.iterator();
        while (it.hasNext()) {
            visitor.visitPermittedSubclass(IrTypeMappingKt.mapClass(this.typeMapper, it.next().getOwner()).getInternalName());
        }
    }

    public final IrExpression generateAssertFieldIfNeeded(boolean generatingClInit) {
        if (JvmIrUtilsKt.hasAssertionsDisabledField(this.irClass, this.context)) {
            return null;
        }
        IrField irFieldBuildAssertionsDisabledField = JvmIrUtilsKt.buildAssertionsDisabledField(this.irClass, this.context, ((ClassCodegen) SequencesKt.last(SequencesKt.generateSequence(this, new Function1<ClassCodegen, ClassCodegen>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen$generateAssertFieldIfNeeded$topLevelClass$1
            @Override // kotlin.jvm.functions.Function1
            public final ClassCodegen invoke(ClassCodegen it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getParentClassCodegen();
            }
        }))).irClass);
        this.irClass.getDeclarations().add(0, irFieldBuildAssertionsDisabledField);
        int startOffset = irFieldBuildAssertionsDisabledField.getStartOffset();
        int endOffset = irFieldBuildAssertionsDisabledField.getEndOffset();
        IrFieldSymbol symbol = irFieldBuildAssertionsDisabledField.getSymbol();
        IrExpressionBody initializer = irFieldBuildAssertionsDisabledField.getInitializer();
        Intrinsics.checkNotNull(initializer);
        IrSetFieldImpl irSetFieldImpl = new IrSetFieldImpl(startOffset, endOffset, symbol, null, initializer.getExpression(), this.context.getIrBuiltIns().getUnitType(), null, null, 192, null);
        if (generatingClInit) {
            return irSetFieldImpl;
        }
        Iterator<IrSimpleFunction> it = IrUtilsKt.getFunctions(this.irClass).iterator();
        IrSimpleFunction irSimpleFunction = null;
        boolean z = false;
        while (true) {
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                break;
            }
            IrSimpleFunction next = it.next();
            if (Intrinsics.areEqual(next.getName().asString(), MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME)) {
                if (!z) {
                    z = true;
                    irSimpleFunction = next;
                }
            }
            irSimpleFunction = null;
            break;
        }
        IrSimpleFunction irSimpleFunctionBuildFunction = irSimpleFunction;
        if (irSimpleFunctionBuildFunction == null) {
            IrClass irClass = this.irClass;
            IrFactory factory = irClass.getFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            Name nameSpecial = Name.special(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME);
            Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
            irFunctionBuilder.setName(nameSpecial);
            irFunctionBuilder.setReturnType(this.context.getIrBuiltIns().getUnitType());
            irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
            IrClass irClass2 = irClass;
            irClass2.getDeclarations().add(irSimpleFunctionBuildFunction);
            irSimpleFunctionBuildFunction.setParent(irClass2);
            irSimpleFunctionBuildFunction.setBody(new IrBlockBodyImpl(irSimpleFunctionBuildFunction.getStartOffset(), irSimpleFunctionBuildFunction.getEndOffset()));
        }
        IrBody body = irSimpleFunctionBuildFunction.getBody();
        Intrinsics.checkNotNull(body, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrBlockBody");
        ((IrBlockBody) body).getStatements().add(0, irSetFieldImpl);
        return null;
    }

    private final void generateKotlinMetadataAnnotation() {
        KotlinClassHeader.Kind kind;
        byte[] serializedIr;
        final JvmClassName jvmClassName = this.context.getMultifileFacadeForPart().get(this.irClass.getAttributeOwnerId());
        final MetadataSource metadata = this.irClass.getMetadata();
        final IrFileEntry fileEntry = JvmIrUtilsKt.getFileParent(this.irClass).getFileEntry();
        if (jvmClassName != null) {
            kind = KotlinClassHeader.Kind.MULTIFILE_CLASS_PART;
        } else if ((metadata instanceof MetadataSource.Class) || (metadata instanceof MetadataSource.Script)) {
            kind = KotlinClassHeader.Kind.CLASS;
        } else if (metadata instanceof MetadataSource.File) {
            kind = KotlinClassHeader.Kind.FILE_FACADE;
        } else {
            kind = (!(metadata instanceof MetadataSource.Function) && (fileEntry instanceof MultifileFacadeFileEntry)) ? KotlinClassHeader.Kind.MULTIFILE_CLASS : KotlinClassHeader.Kind.SYNTHETIC_CLASS;
        }
        final KotlinClassHeader.Kind kind2 = kind;
        if (metadata instanceof MetadataSource.Class) {
            serializedIr = ((MetadataSource.Class) metadata).getSerializedIr();
        } else {
            serializedIr = metadata instanceof MetadataSource.File ? ((MetadataSource.File) metadata).getSerializedIr() : null;
        }
        boolean z = kind2 == KotlinClassHeader.Kind.MULTIFILE_CLASS || kind2 == KotlinClassHeader.Kind.MULTIFILE_CLASS_PART;
        int iGenerateMetadataExtraFlags = this.context.getBackendExtension().generateMetadataExtraFlags(getState().getConfig().getAbiStability());
        if (z && ((Boolean) getState().getLanguageVersionSettings().getFlag(JvmAnalysisFlags.getInheritMultifileParts())).booleanValue()) {
            iGenerateMetadataExtraFlags |= 1;
        }
        if (metadata instanceof MetadataSource.Script) {
            iGenerateMetadataExtraFlags |= 4;
        }
        final boolean z2 = z;
        WriteAnnotationUtilKt.writeKotlinMetadata(this.visitor, getState(), kind2, this.context.getPublicAbiSymbols().contains(this.irClass.getSymbol()) || isInlineSamWrapper(this.irClass) || (ClassCodegenKt.isAnonymousClass(this.type) && JvmIrInlineUtilsKt.isInPublicInlineScope(this.irClass)), iGenerateMetadataExtraFlags, new Function1<AnnotationVisitor, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen.generateKotlinMetadataAnnotation.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AnnotationVisitor annotationVisitor) {
                invoke2(annotationVisitor);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(AnnotationVisitor av) {
                Pair<MessageLite, JvmStringTable> pairSerialize;
                Intrinsics.checkNotNullParameter(av, "av");
                if (metadata != null && (pairSerialize = this.getMetadataSerializer().serialize(metadata)) != null) {
                    DescriptorAsmUtil.writeAnnotationData(av, pairSerialize.component1(), pairSerialize.component2());
                }
                if (fileEntry instanceof MultifileFacadeFileEntry) {
                    AnnotationVisitor annotationVisitorVisitArray = av.visitArray(JvmAnnotationNames.METADATA_DATA_FIELD_NAME);
                    Iterator<IrFile> it = ((MultifileFacadeFileEntry) fileEntry).getPartFiles().iterator();
                    while (it.hasNext()) {
                        Iterator<T> it2 = it.next().getDeclarations().iterator();
                        boolean z3 = false;
                        Object obj = null;
                        while (true) {
                            if (!it2.hasNext()) {
                                if (!z3) {
                                    break;
                                } else {
                                    break;
                                }
                            } else {
                                Object next = it2.next();
                                if (IrUtilsKt.isFileClass((IrDeclaration) next)) {
                                    if (!z3) {
                                        z3 = true;
                                        obj = next;
                                    }
                                }
                            }
                            obj = null;
                            break;
                        }
                        IrClass irClass = (IrClass) obj;
                        if (irClass != null) {
                            annotationVisitorVisitArray.visit(null, IrTypeMappingKt.mapClass(this.getTypeMapper(), irClass).getInternalName());
                        }
                    }
                    annotationVisitorVisitArray.visitEnd();
                }
                JvmClassName jvmClassName2 = jvmClassName;
                if (jvmClassName2 != null) {
                    av.visit("xs", jvmClassName2.getInternalName());
                }
                if (this.getContext().getClassNameOverride().containsKey(this.getIrClass())) {
                    if (!z2) {
                        KotlinClassHeader.Kind kind3 = kind2;
                        KotlinClassHeader.Kind kind4 = KotlinClassHeader.Kind.FILE_FACADE;
                    }
                    FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(this.getIrClass());
                    Intrinsics.checkNotNull(fqNameWhenAvailable);
                    av.visit(JvmAnnotationNames.METADATA_PACKAGE_NAME_FIELD_NAME, fqNameWhenAvailable.parent().asString());
                }
            }
        });
        if (serializedIr != null) {
            storeSerializedIr(serializedIr);
        }
    }

    private final List<File> loadSourceFilesInfo(IrFile irFile) {
        IrFileEntry fileEntry = irFile.getFileEntry();
        if (fileEntry instanceof MultifileFacadeFileEntry) {
            List<IrFile> partFiles = ((MultifileFacadeFileEntry) fileEntry).getPartFiles();
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = partFiles.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList, loadSourceFilesInfo((IrFile) it.next()));
            }
            return arrayList;
        }
        return CollectionsKt.listOf(new File(fileEntry.getName()));
    }

    private final void generateField(IrField field) {
        Type typeMapType = IrTypeMappingKt.mapType(this.typeMapper, field);
        String strMapFieldSignature = Intrinsics.areEqual(field.getOrigin(), IrDeclarationOrigin.PROPERTY_DELEGATE.INSTANCE) ? null : this.methodSignatureMapper.mapFieldSignature(field);
        String strAsString = field.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        int iComputeFieldFlags = ClassCodegenKt.computeFieldFlags(field, this.context, getState().getLanguageVersionSettings());
        ClassBuilder classBuilder = this.visitor;
        JvmIrDeclarationOrigin descriptorOrigin = JvmIrDeclarationOriginKt.getDescriptorOrigin(field);
        String descriptor = typeMapType.getDescriptor();
        IrExpressionBody initializer = field.getInitializer();
        IrExpression expression = initializer != null ? initializer.getExpression() : null;
        IrConst irConst = expression instanceof IrConst ? (IrConst) expression : null;
        final FieldVisitor fieldVisitorNewField = classBuilder.newField(descriptorOrigin, iComputeFieldFlags, strAsString, descriptor, strMapFieldSignature, irConst != null ? irConst.getValue() : null);
        Intrinsics.checkNotNullExpressionValue(fieldVisitorNewField, "newField(...)");
        JvmSignatureClashDetector jvmSignatureClashDetector = this.jvmSignatureClashDetector;
        String descriptor2 = typeMapType.getDescriptor();
        Intrinsics.checkNotNullExpressionValue(descriptor2, "getDescriptor(...)");
        jvmSignatureClashDetector.trackField(field, new RawSignature(strAsString, descriptor2, MemberKind.FIELD));
        if (!Intrinsics.areEqual(field.getOrigin(), JvmLoweredDeclarationOrigin.CONTINUATION_CLASS_RESULT_FIELD.INSTANCE)) {
            new AnnotationCodegen(this, (iComputeFieldFlags & ContactCallbackEvent.ON_STARTED_FILTERED_MANIFOLD) != 0 || (Intrinsics.areEqual(field.getOrigin(), IrDeclarationOrigin.FIELD_FOR_OBJECT_INSTANCE.INSTANCE) && JvmIrUtilsKt.isSyntheticSingleton(this.irClass))) { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen.generateField.1
                @Override // org.jetbrains.kotlin.backend.jvm.codegen.AnnotationCodegen
                public AnnotationVisitor visitAnnotation(String descr, boolean visible) {
                    Intrinsics.checkNotNullParameter(descr, "descr");
                    AnnotationVisitor annotationVisitorVisitAnnotation = fieldVisitorNewField.visitAnnotation(descr, visible);
                    Intrinsics.checkNotNullExpressionValue(annotationVisitorVisitAnnotation, "visitAnnotation(...)");
                    return annotationVisitorVisitAnnotation;
                }

                @Override // org.jetbrains.kotlin.backend.jvm.codegen.AnnotationCodegen
                public AnnotationVisitor visitTypeAnnotation(String descr, TypePath path, boolean visible) {
                    Intrinsics.checkNotNullParameter(descr, "descr");
                    AnnotationVisitor annotationVisitorVisitTypeAnnotation = fieldVisitorNewField.visitTypeAnnotation(TypeReference.newTypeReference(19).getValue(), path, descr, visible);
                    Intrinsics.checkNotNullExpressionValue(annotationVisitorVisitTypeAnnotation, "visitTypeAnnotation(...)");
                    return annotationVisitorVisitTypeAnnotation;
                }
            }.genAnnotations(field, typeMapType, field.getType());
        }
        MetadataSource metadata = field.getMetadata();
        MetadataSource.Property property = metadata instanceof MetadataSource.Property ? (MetadataSource.Property) metadata : null;
        if (property != null) {
            getMetadataSerializer().bindFieldMetadata(property, TuplesKt.to(typeMapType, strAsString));
        }
        if (!IrUtilsKt.hasAnnotation(this.irClass, JvmNames.JVM_RECORD_ANNOTATION_FQ_NAME) || field.getIsStatic()) {
            return;
        }
        ClassBuilder classBuilder2 = this.visitor;
        String descriptor3 = typeMapType.getDescriptor();
        Intrinsics.checkNotNullExpressionValue(descriptor3, "getDescriptor(...)");
        ClassBuilderRecordKt.addRecordComponent(classBuilder2, strAsString, descriptor3, strMapFieldSignature);
    }

    public final SMAPAndMethodNode generateMethodNode(IrFunction method) {
        SMAPAndMethodNode sMAPAndMethodNode;
        Intrinsics.checkNotNullParameter(method, "method");
        if (!method.getIsInline() && !CoroutineCodegenKt.isSuspendCapturingCrossinline(method)) {
            return FunctionCodegen.generate$default(new FunctionCodegen(method, this), null, 1, null);
        }
        SMAPAndMethodNode sMAPAndMethodNode2 = this.generatedInlineMethods.get(method);
        if (sMAPAndMethodNode2 == null) {
            synchronized (this.context.getInlineMethodGenerationLock()) {
                Map<IrFunction, SMAPAndMethodNode> map = this.generatedInlineMethods;
                SMAPAndMethodNode sMAPAndMethodNodeGenerate$default = map.get(method);
                if (sMAPAndMethodNodeGenerate$default == null) {
                    sMAPAndMethodNodeGenerate$default = FunctionCodegen.generate$default(new FunctionCodegen(method, this), null, 1, null);
                    map.put(method, sMAPAndMethodNodeGenerate$default);
                }
                sMAPAndMethodNode = sMAPAndMethodNodeGenerate$default;
            }
            sMAPAndMethodNode2 = sMAPAndMethodNode;
        }
        MethodNode node = sMAPAndMethodNode2.getNode();
        return new SMAPAndMethodNode(InlineCodegenUtilsKt.cloneMethodNode(node), sMAPAndMethodNode2.getClassSMAP());
    }

    private final void generateMethod(final IrFunction method, SourceMapper classSMAP) {
        IrAttributeContainer attributeOwnerId;
        IrFunction irFunction = method;
        if (IrFakeOverrideUtilsKt.isFakeOverride(irFunction)) {
            this.jvmSignatureClashDetector.trackFakeOverrideMethod(method);
            return;
        }
        SMAPAndMethodNode sMAPAndMethodNodeGenerateMethodNode = generateMethodNode(method);
        MethodNode node = sMAPAndMethodNodeGenerateMethodNode.getNode();
        SMAP classSMAP2 = sMAPAndMethodNodeGenerateMethodNode.getClassSMAP();
        InlineCodegenUtilsKt.preprocessSuspendMarkers(node, Intrinsics.areEqual(method.getOrigin(), JvmLoweredDeclarationOrigin.FOR_INLINE_STATE_MACHINE_TEMPLATE.INSTANCE) || JvmIrInlineUtilsKt.isEffectivelyInlineOnly(method), Intrinsics.areEqual(method.getOrigin(), JvmLoweredDeclarationOrigin.FOR_INLINE_STATE_MACHINE_TEMPLATE_CAPTURES_CROSSINLINE.INSTANCE));
        ClassBuilder classBuilder = this.visitor;
        JvmIrDeclarationOrigin descriptorOrigin = JvmIrDeclarationOriginKt.getDescriptorOrigin(irFunction);
        int i = node.access;
        String str = node.f1016name;
        String str2 = node.desc;
        String str3 = node.signature;
        List<String> exceptions = node.exceptions;
        Intrinsics.checkNotNullExpressionValue(exceptions, "exceptions");
        final MethodVisitor methodVisitorNewMethod = classBuilder.newMethod(descriptorOrigin, i, str, str2, str3, (String[]) exceptions.toArray(new String[0]));
        Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "with(...)");
        final SourceMapCopier sourceMapCopier = new SourceMapCopier(classSMAP, classSMAP2, null, 4, null);
        MethodVisitor methodVisitor = new MethodVisitor(methodVisitorNewMethod) { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen$generateMethod$smapCopyingVisitor$1
            @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
            public void visitLineNumber(int line, Label start) {
                Intrinsics.checkNotNullParameter(start, "start");
                super.visitLineNumber(sourceMapCopier.mapLineNumber(line), start);
            }
        };
        if (JvmIrCoroutineUtilsKt.hasContinuation(method)) {
            final IrClass irClassContinuationClass = CoroutineCodegenKt.continuationClass(method);
            final Lazy lazy = LazyKt.lazy(new Function0<ClassCodegen>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen$generateMethod$continuationClassCodegen$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final ClassCodegen invoke() {
                    return irClassContinuationClass != null ? ClassCodegen.INSTANCE.getOrCreate(irClassContinuationClass, this.getContext(), method) : this;
                }
            });
            if (irClassContinuationClass == null || (attributeOwnerId = irClassContinuationClass.getAttributeOwnerId()) == null) {
                attributeOwnerId = this.irClass.getAttributeOwnerId();
            }
            MethodVisitor methodVisitor2 = methodVisitor;
            Map<Type, Integer> mapEmptyMap = this.context.getContinuationClassesVarsCountByType().get(attributeOwnerId);
            if (mapEmptyMap == null) {
                mapEmptyMap = MapsKt.emptyMap();
            }
            CoroutineCodegenKt.acceptWithStateMachine(node, method, this, methodVisitor2, mapEmptyMap, new Function0<ClassBuilder>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen.generateMethod.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final ClassBuilder invoke() {
                    return lazy.getValue().visitor;
                }
            });
            if (irClassContinuationClass != null && (lazy.isInitialized() || CoroutineCodegenKt.isSuspendCapturingCrossinline(method))) {
                ((ClassCodegen) lazy.getValue()).generate();
            }
        } else {
            node.accept(methodVisitor);
        }
        JvmSignatureClashDetector jvmSignatureClashDetector = this.jvmSignatureClashDetector;
        String name2 = node.f1016name;
        Intrinsics.checkNotNullExpressionValue(name2, "name");
        String desc = node.desc;
        Intrinsics.checkNotNullExpressionValue(desc, "desc");
        jvmSignatureClashDetector.trackMethod(method, new RawSignature(name2, desc, MemberKind.METHOD));
        MetadataSource metadata = method.getMetadata();
        if (metadata instanceof MetadataSource.Property) {
            getMetadataSerializer().bindPropertyMetadata((MetadataSource.Property) metadata, new Method(node.f1016name, node.desc), method.getOrigin());
        } else if (metadata instanceof MetadataSource.Function) {
            getMetadataSerializer().bindMethodMetadata((MetadataSource.Function) metadata, new Method(node.f1016name, node.desc));
        } else if (metadata != null) {
            throw new IllegalStateException(("Incorrect metadata source " + metadata + " for:\n" + DumpIrTreeKt.dump$default(method, null, 1, null)).toString());
        }
    }

    /* JADX WARN: Code duplicated, block: B:83:0x0171  */
    /* JADX WARN: Code duplicated, block: B:84:0x0173  */
    /* JADX WARN: Code duplicated, block: B:89:0x0181  */
    private final void generateInnerAndOuterClasses() {
        String strClassInternalName;
        String strClassInternalName2;
        String strAsString;
        Name shortClassName;
        IrConstructor irConstructor;
        Method methodMapAsmMethod;
        Object next;
        Set<IrClass> set;
        ClassCodegen parentClassCodegen = getParentClassCodegen();
        if (parentClassCodegen != null && (set = parentClassCodegen.innerClasses) != null) {
            set.add(this.irClass);
        }
        Iterator it = SequencesKt.takeWhile(SequencesKt.generateSequence(this, new Function1<ClassCodegen, ClassCodegen>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen.generateInnerAndOuterClasses.1
            @Override // kotlin.jvm.functions.Function1
            public final ClassCodegen invoke(ClassCodegen it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return it2.getParentClassCodegen();
            }
        }), new Function1<ClassCodegen, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen.generateInnerAndOuterClasses.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(ClassCodegen it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Boolean.valueOf(it2.getParentClassCodegen() != null);
            }
        }).iterator();
        while (it.hasNext()) {
            this.innerClasses.add(((ClassCodegen) it.next()).irClass);
        }
        if (getParentClassCodegen() != null) {
            if (this.context.isEnclosedInConstructor().contains(this.irClass.getAttributeOwnerId())) {
                ClassCodegen parentClassCodegen2 = getParentClassCodegen();
                Intrinsics.checkNotNull(parentClassCodegen2);
                IrClass irClass = parentClassCodegen2.irClass;
                IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(irClass);
                if (primaryConstructor == null) {
                    Iterator<T> it2 = irClass.getDeclarations().iterator();
                    do {
                        if (!it2.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it2.next();
                    } while (!(next instanceof IrConstructor));
                    primaryConstructor = (IrConstructor) next;
                    if (primaryConstructor == null) {
                        throw new IllegalStateException(("Class in a non-static initializer found, but container has no constructors: " + RenderIrElementKt.render$default(irClass, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                    }
                }
                irConstructor = primaryConstructor;
            } else {
                irConstructor = this.parentFunction;
            }
            if (irConstructor != null || isAnonymousInnerClass(this.irClass)) {
                if (irConstructor == null || (methodMapAsmMethod = this.methodSignatureMapper.mapAsmMethod(irConstructor)) == null || Intrinsics.areEqual(methodMapAsmMethod.getName(), MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME)) {
                    methodMapAsmMethod = null;
                }
                ClassBuilder classBuilder = this.visitor;
                ClassCodegen parentClassCodegen3 = getParentClassCodegen();
                Intrinsics.checkNotNull(parentClassCodegen3);
                classBuilder.visitOuterClass(parentClassCodegen3.type.getInternalName(), methodMapAsmMethod != null ? methodMapAsmMethod.getName() : null, methodMapAsmMethod != null ? methodMapAsmMethod.getDescriptor() : null);
            }
        }
        for (IrClass irClass2 : CollectionsKt.sortedWith(this.innerClasses, new Comparator() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen$generateInnerAndOuterClasses$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable((IrClass) t);
                String strAsString2 = fqNameWhenAvailable != null ? fqNameWhenAvailable.asString() : null;
                FqName fqNameWhenAvailable2 = IrUtilsKt.getFqNameWhenAvailable((IrClass) t2);
                return ComparisonsKt.compareValues(strAsString2, fqNameWhenAvailable2 != null ? fqNameWhenAvailable2.asString() : null);
            }
        })) {
            ClassId classIdMapToJava = mapToJava(irClass2);
            if (classIdMapToJava == null || (strClassInternalName = SignatureBuildingComponentsKt.getInternalName(classIdMapToJava)) == null) {
                strClassInternalName = this.typeMapper.classInternalName(irClass2);
            }
            if (!isSamWrapper(irClass2) && !isAnnotationImplementation(irClass2) && !this.context.isEnclosedInConstructor().contains(irClass2.getAttributeOwnerId())) {
                IrDeclarationParent parent = irClass2.getParent();
                if (parent instanceof IrClass) {
                    IrClass irClass3 = (IrClass) parent;
                    ClassId classIdMapToJava2 = mapToJava(irClass3);
                    if (Intrinsics.areEqual(classIdMapToJava != null ? classIdMapToJava.getPackageFqName() : null, classIdMapToJava2 != null ? classIdMapToJava2.getPackageFqName() : null)) {
                        if (classIdMapToJava2 == null || (strClassInternalName2 = SignatureBuildingComponentsKt.getInternalName(classIdMapToJava2)) == null) {
                            strClassInternalName2 = this.typeMapper.classInternalName(irClass3);
                        }
                        if (isAnonymousInnerClass(irClass2)) {
                            strAsString = null;
                        } else if (classIdMapToJava != null || (shortClassName = classIdMapToJava.getShortClassName()) == null || (strAsString = shortClassName.asString()) == null) {
                            strAsString = irClass2.getName().asString();
                        }
                        this.visitor.visitInnerClass(strClassInternalName, strClassInternalName2, strAsString, IrCodegenUtilsKt.calculateInnerClassAccessFlags(irClass2, this.context));
                    }
                }
            }
            strClassInternalName2 = null;
            if (isAnonymousInnerClass(irClass2)) {
                strAsString = null;
            } else if (classIdMapToJava != null) {
                strAsString = irClass2.getName().asString();
            } else {
                strAsString = irClass2.getName().asString();
            }
            this.visitor.visitInnerClass(strClassInternalName, strClassInternalName2, strAsString, IrCodegenUtilsKt.calculateInnerClassAccessFlags(irClass2, this.context));
        }
    }

    private final ClassId mapToJava(IrClass irClass) {
        FqNameUnsafe unsafe;
        FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(irClass);
        if (fqNameWhenAvailable == null || (unsafe = fqNameWhenAvailable.toUnsafe()) == null) {
            return null;
        }
        return JavaToKotlinClassMap.INSTANCE.mapKotlinToJava(unsafe);
    }

    private final boolean isAnonymousInnerClass(IrClass irClass) {
        return isSamWrapper(irClass) || irClass.getName().isSpecial() || isAnnotationImplementation(irClass);
    }

    private final boolean isInlineSamWrapper(IrClass irClass) {
        return isSamWrapper(irClass) && Intrinsics.areEqual(irClass.getVisibility(), DescriptorVisibilities.PUBLIC);
    }

    private final boolean isSamWrapper(IrClass irClass) {
        return Intrinsics.areEqual(irClass.getOrigin(), IrDeclarationOrigin.GENERATED_SAM_IMPLEMENTATION.INSTANCE);
    }

    private final boolean isAnnotationImplementation(IrClass irClass) {
        return Intrinsics.areEqual(irClass.getOrigin(), AnnotationImplementationTransformerKt.getANNOTATION_IMPLEMENTATION());
    }

    public final void addInnerClassInfo(IrClass innerClass) {
        Intrinsics.checkNotNullParameter(innerClass, "innerClass");
        do {
            IrClass irClass = innerClass;
            if (AdditionalIrUtilsKt.isTopLevelDeclaration(irClass) || !this.innerClasses.add(innerClass)) {
                return;
            } else {
                innerClass = IrUtilsKt.getParentClassOrNull(irClass);
            }
        } while (innerClass != null);
    }

    private final void storeSerializedIr(byte[] serializedIr) {
        AnnotationVisitor annotationVisitorNewAnnotation = this.visitor.newAnnotation(JvmAnnotationNames.SERIALIZED_IR_DESC, true);
        Intrinsics.checkNotNullExpressionValue(annotationVisitorNewAnnotation, "newAnnotation(...)");
        AnnotationVisitor annotationVisitorVisitArray = annotationVisitorNewAnnotation.visitArray(JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME);
        String[] strArrEncodeBytes = BitEncoding.encodeBytes(serializedIr);
        Intrinsics.checkNotNullExpressionValue(strArrEncodeBytes, "encodeBytes(...)");
        for (String str : strArrEncodeBytes) {
            annotationVisitorVisitArray.visit(null, str);
        }
        annotationVisitorVisitArray.visitEnd();
        annotationVisitorNewAnnotation.visitEnd();
    }

    /* JADX INFO: compiled from: ClassCodegen.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\nJ\f\u0010\u000b\u001a\u00020\f*\u00020\rH\u0002¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen$Companion;", "", "()V", "getOrCreate", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "parentFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "hasInvalidName", "", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmClassSignature;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ ClassCodegen getOrCreate$default(Companion companion, IrClass irClass, JvmBackendContext jvmBackendContext, IrFunction irFunction, int i, Object obj) {
            if ((i & 4) != 0) {
                IrDeclarationParent parent = irClass.getParent();
                irFunction = parent instanceof IrFunction ? (IrFunction) parent : null;
                if (irFunction == null || !Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.CLASS_STATIC_INITIALIZER.INSTANCE)) {
                    irFunction = null;
                }
            }
            return companion.getOrCreate(irClass, jvmBackendContext, irFunction);
        }

        public final ClassCodegen getOrCreate(final IrClass irClass, final JvmBackendContext context, final IrFunction parentFunction) {
            Intrinsics.checkNotNullParameter(irClass, "irClass");
            Intrinsics.checkNotNullParameter(context, "context");
            ClassCodegen classCodegen = (ClassCodegen) context.getOrCreateClassCodegen(irClass, new Function1<IrClass, ClassCodegen>() { // from class: org.jetbrains.kotlin.backend.jvm.codegen.ClassCodegen$Companion$getOrCreate$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final ClassCodegen invoke(IrClass it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return new ClassCodegen(irClass, context, parentFunction, null);
                }
            });
            if (parentFunction != null) {
                Intrinsics.areEqual(classCodegen.parentFunction, parentFunction);
            }
            return classCodegen;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean hasInvalidName(JvmClassSignature jvmClassSignature) {
            String name2 = jvmClassSignature.getName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            for (String str : StringsKt.splitToSequence$default((CharSequence) name2, new char[]{'/'}, false, 0, 6, (Object) null)) {
                for (int i = 0; i < str.length(); i++) {
                    if (JvmSimpleNameBacktickChecker.INSTANCE.getINVALID_CHARS().contains(Character.valueOf(str.charAt(i)))) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
