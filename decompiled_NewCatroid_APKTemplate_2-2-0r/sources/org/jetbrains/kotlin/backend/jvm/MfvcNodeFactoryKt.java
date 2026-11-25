package org.jetbrains.kotlin.backend.jvm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder;
import org.jetbrains.kotlin.backend.common.lower.LowerUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilder;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrBuilderKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.MultiFieldValueClassRepresentation;
import org.jetbrains.kotlin.ir.DeepCopyIrTreeWithDeclarationsKt;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBlockBodyBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.util.OperatorNameConventions;

/* JADX INFO: compiled from: MfvcNodeFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000è\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\u001a&\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00020\u0006j\b\u0012\u0004\u0012\u00020\u0002`\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b\u001a&\u0010\f\u001a\u0012\u0012\u0004\u0012\u00020\r0\u0006j\b\u0012\u0004\u0012\u00020\r`\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b\u001a\u0092\u0001\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u00122\u0016\u0010\u0013\u001a\u0012\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014j\u0002`\u00172\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001b2\u0006\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u000f2\b\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010\u0001\u001a\u001e\u0010)\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010*\u001a\u00020\u0002\u001a\u001e\u0010+\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010,\u001a\u00020\u0001\u001ax\u0010-\u001a\u00020.2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001b2\u0006\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010.2\u0006\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010\u0001\u001a\u0090\u0001\u0010/\u001a\u0002002\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u00122\u0016\u0010\u0013\u001a\u0012\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014j\u0002`\u00172\u0006\u0010\u0018\u001a\u00020\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001b2\u0006\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u0001002\u0006\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010&\u001a\u00020'2\b\u0010(\u001a\u0004\u0018\u00010\u0001\u001a\u001a\u00101\u001a\u0004\u0018\u0001002\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u00020%H\u0002\u001a\u0016\u00105\u001a\u0002062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u00107\u001a\u00020\t\u001a0\u00108\u001a\u00020%2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u00107\u001a\u00020\t2\f\u00109\u001a\b\u0012\u0004\u0012\u00020.0\u001b2\b\u0010:\u001a\u0004\u0018\u00010;H\u0002\u001a>\u0010<\u001a\u00020;2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010=\u001a\u00020;2\u0006\u00107\u001a\u00020\t2\f\u00109\u001a\b\u0012\u0004\u0012\u00020.0\u001b2\u000e\u0010>\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u001bH\u0002\u001a<\u0010?\u001a\u00020%2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010=\u001a\u00020;2\u0006\u00107\u001a\u00020\t2\f\u00109\u001a\b\u0012\u0004\u0012\u00020.0\u001b2\f\u0010@\u001a\b\u0012\u0004\u0012\u0002000\u001bH\u0002\u001aL\u0010A\u001a\b\u0012\u0004\u0012\u0002000\u001b2\f\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00120C2\u001e\u0010D\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020 \u0012\u0004\u0012\u00020\u001c0E\u0012\u0004\u0012\u00020\u00020\u00142\u0006\u0010\n\u001a\u00020\u000b2\u0006\u00107\u001a\u00020\tH\u0002\u001a:\u0010F\u001a\u00020%2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u00107\u001a\u00020\t2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00010\u001b2\b\u0010H\u001a\u0004\u0018\u00010%2\b\u0010I\u001a\u0004\u0018\u00010%H\u0002\u001a\u0084\u0001\u0010J\u001a\u00020%2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010K\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\t2\b\u0010!\u001a\u0004\u0018\u0001002\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010&\u001a\u00020'2.\u0010L\u001a*\u0012\u0004\u0012\u00020N\u0012\u0015\u0012\u0013\u0018\u00010O¢\u0006\f\bP\u0012\b\bQ\u0012\u0004\b\b(R\u0012\u0004\u0012\u00020S0M¢\u0006\u0002\bTH\u0002\u001a \u0010U\u001a\u00020V2\u0006\u0010*\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010W\u001a\u00020XH\u0002\u001a\u0016\u0010Y\u001a\u0004\u0018\u00010%*\u00020\u00022\u0006\u0010\u0010\u001a\u00020ZH\u0002\u001a\n\u0010[\u001a\u00020 *\u00020\t\u001a\u0014\u0010\\\u001a\u00020 *\u00020\u00022\u0006\u0010]\u001a\u00020ZH\u0002\u001a!\u0010^\u001a\u00020V*\u0002062\b\u0010_\u001a\u0004\u0018\u00010`\u0082\u0002\n\n\b\b\u0000\u001a\u0004\b\u0003\u0010\u0001\u001a#\u0010^\u001a\u00020V*\u00020\t2\b\u0010_\u001a\u0004\u0018\u00010`H\u0002\u0082\u0002\n\n\b\b\u0000\u001a\u0004\b\u0003\u0010\u0001\"\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004¨\u0006a"}, d2 = {"backingFieldIfNotDelegate", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "getBackingFieldIfNotDelegate", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;)Lorg/jetbrains/kotlin/ir/declarations/IrField;", "collectPropertiesAfterLowering", "Ljava/util/LinkedHashSet;", "Lkotlin/collections/LinkedHashSet;", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "collectPropertiesOrFieldsAfterLowering", "Lorg/jetbrains/kotlin/backend/jvm/IrPropertyOrIrField;", "createIntermediateMfvcNode", "Lorg/jetbrains/kotlin/backend/jvm/IntermediateMfvcNode;", "parent", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "typeArguments", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/backend/jvm/TypeArguments;", "methodFullNameMode", "Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNodeImpl$Companion$MethodFullNameMode;", "nameParts", "", "Lorg/jetbrains/kotlin/name/Name;", "fieldAnnotations", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "static", "", "overriddenNode", "defaultMethodsImplementationSourceNode", "Lorg/jetbrains/kotlin/backend/jvm/UnboxFunctionImplementation;", "oldGetter", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "oldPropertyBackingField", "createIntermediateNodeForMfvcPropertyOfRegularClass", "oldProperty", "createIntermediateNodeForStandaloneMfvcField", "oldField", "createLeafMfvcNode", "Lorg/jetbrains/kotlin/backend/jvm/LeafMfvcNode;", "createNameableMfvcNodes", "Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNode;", "getOverriddenNode", "replacements", "Lorg/jetbrains/kotlin/backend/jvm/MemoizedMultiFieldValueClassReplacements;", "getter", "getRootNode", "Lorg/jetbrains/kotlin/backend/jvm/RootMfvcNode;", "mfvc", "makeBoxMethod", "leaves", "newPrimaryConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "makeMfvcPrimaryConstructor", "oldPrimaryConstructor", "fields", "makePrimaryConstructorImpl", "subnodes", "makeRootMfvcNodeSubnodes", "representation", "Lorg/jetbrains/kotlin/descriptors/MultiFieldValueClassRepresentation;", "properties", "Lkotlin/Pair;", "makeSpecializedEqualsMethod", "oldFields", "customEqualsAny", "customEqualsMfvc", "makeUnboxMethod", "fullMethodName", "makeOptimizedExpression", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "Lkotlin/ParameterName;", "name", "receiver", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "Lkotlin/ExtensionFunctionType;", "updateAnnotationsAndPropertyFromOldProperty", "", "node", "Lorg/jetbrains/kotlin/backend/jvm/MfvcNode;", "getterIfDeclared", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;", "isKotlinExternalStub", "isStatic", "currentContainer", "throwWhenNotExternalIsNull", "value", "", "backend.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MfvcNodeFactoryKt {
    public static final LeafMfvcNode createLeafMfvcNode(IrClass parent, JvmBackendContext context, IrType type, NameableMfvcNodeImpl.Companion.MethodFullNameMode methodFullNameMode, List<Name> nameParts, List<? extends IrConstructorCall> fieldAnnotations, boolean z, LeafMfvcNode leafMfvcNode, UnboxFunctionImplementation defaultMethodsImplementationSourceNode, IrSimpleFunction irSimpleFunction, Modality modality, IrField irField) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(methodFullNameMode, "methodFullNameMode");
        Intrinsics.checkNotNullParameter(nameParts, "nameParts");
        Intrinsics.checkNotNullParameter(fieldAnnotations, "fieldAnnotations");
        Intrinsics.checkNotNullParameter(defaultMethodsImplementationSourceNode, "defaultMethodsImplementationSourceNode");
        Intrinsics.checkNotNullParameter(modality, "modality");
        IrField irFieldBuildField = null;
        if (MfvcNodeKt.needsMfvcFlattening(type)) {
            throw new IllegalArgumentException((RenderIrElementKt.render$default(type, (DumpIrTreeOptions) null, 1, (Object) null) + " requires flattening").toString());
        }
        Name nameMakeFullMethodName = NameableMfvcNodeImpl.INSTANCE.makeFullMethodName(methodFullNameMode, nameParts);
        Name nameMakeFullFieldName = NameableMfvcNodeImpl.INSTANCE.makeFullFieldName(nameParts);
        if (irField != null) {
            IrFactory irFactory = context.getIrFactory();
            IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
            irFieldBuilder.updateFrom(irField);
            irFieldBuilder.setName(nameMakeFullFieldName);
            irFieldBuilder.setType(type);
            DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
            Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
            irFieldBuilder.setVisibility(PRIVATE);
            irField.setMetadata(null);
            irFieldBuildField = DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
            irFieldBuildField.setParent(irField.getParent());
            List<? extends IrConstructorCall> list = fieldAnnotations;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add((IrConstructorCall) DeepCopyIrTreeWithDeclarationsKt.deepCopyWithVariables((IrConstructorCall) it.next()));
            }
            irFieldBuildField.setAnnotations(arrayList);
        }
        final IrField irField2 = irFieldBuildField;
        return new LeafMfvcNode(type, methodFullNameMode, nameParts, irField2, makeUnboxMethod(context, nameMakeFullMethodName, type, parent, leafMfvcNode, z, defaultMethodsImplementationSourceNode, irSimpleFunction, modality, new Function2<IrBuilderWithScope, IrValueDeclaration, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.MfvcNodeFactoryKt$createLeafMfvcNode$unboxMethod$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final IrExpression invoke(IrBuilderWithScope makeUnboxMethod, IrValueDeclaration irValueDeclaration) {
                IrGetValueImpl irGetValueImplIrGet;
                Intrinsics.checkNotNullParameter(makeUnboxMethod, "$this$makeUnboxMethod");
                IrField irField3 = irField2;
                Intrinsics.checkNotNull(irField3);
                if (irField3.getIsStatic()) {
                    irGetValueImplIrGet = null;
                } else {
                    Intrinsics.checkNotNull(irValueDeclaration);
                    irGetValueImplIrGet = ExpressionHelpersKt.irGet(makeUnboxMethod, irValueDeclaration);
                }
                return ExpressionHelpersKt.irGetField$default(makeUnboxMethod, irGetValueImplIrGet, irField2, null, 4, null);
            }
        }), defaultMethodsImplementationSourceNode);
    }

    public static final boolean isKotlinExternalStub(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return Intrinsics.areEqual(irClass.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_DECLARATION_STUB.INSTANCE);
    }

    private static final void throwWhenNotExternalIsNull(IrClass irClass, Object obj) {
        if (obj == null) {
            throw new IllegalStateException(irClass.getName() + " is not external but has no primary constructor:\n" + DumpIrTreeKt.dump$default(irClass, null, 1, null));
        }
    }

    public static final void throwWhenNotExternalIsNull(RootMfvcNode rootMfvcNode, Object obj) {
        Intrinsics.checkNotNullParameter(rootMfvcNode, "<this>");
        throwWhenNotExternalIsNull(rootMfvcNode.getMfvc(), obj);
    }

    private static final IrSimpleFunction makeUnboxMethod(JvmBackendContext jvmBackendContext, Name name2, IrType irType, IrClass irClass, NameableMfvcNode nameableMfvcNode, boolean z, UnboxFunctionImplementation unboxFunctionImplementation, IrSimpleFunction irSimpleFunction, Modality modality, Function2<? super IrBuilderWithScope, ? super IrValueDeclaration, ? extends IrExpression> function2) {
        IrSimpleFunction irSimpleFunctionBuildFunction;
        List listListOfNotNull;
        IrExpressionBody irExpressionBodyIrExprBody;
        List listEmptyList;
        if (irSimpleFunction == null) {
            IrFactory irFactory = jvmBackendContext.getIrFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.setName(name2);
            irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.SYNTHETIC_MULTI_FIELD_VALUE_CLASS_MEMBER.INSTANCE);
            irFunctionBuilder.setReturnType(irType);
            irFunctionBuilder.setModality(modality);
            irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
            irSimpleFunctionBuildFunction.setParent(irClass);
            if (nameableMfvcNode == null || (listEmptyList = CollectionsKt.plus((Collection<? extends IrSimpleFunctionSymbol>) MfvcNodeKt.getUnboxMethod(nameableMfvcNode).getOverriddenSymbols(), MfvcNodeKt.getUnboxMethod(nameableMfvcNode).getSymbol())) == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            irSimpleFunctionBuildFunction.setOverriddenSymbols(listEmptyList);
            if (!z) {
                IrUtilsKt.createDispatchReceiverParameter$default(irSimpleFunctionBuildFunction, null, 1, null);
            }
        } else {
            irSimpleFunctionBuildFunction = irSimpleFunction;
        }
        if (!isKotlinExternalStub(irClass)) {
            JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(jvmBackendContext, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, null);
            IrValueParameter dispatchReceiverParameter = irSimpleFunctionBuildFunction.getDispatchReceiverParameter();
            if (unboxFunctionImplementation.getHasPureUnboxMethod()) {
                irExpressionBodyIrExprBody = ExpressionHelpersKt.irExprBody(jvmIrBuilderCreateJvmIrBuilder$default, function2.invoke(jvmIrBuilderCreateJvmIrBuilder$default, dispatchReceiverParameter));
            } else {
                IrFunctionAccessExpression irFunctionAccessExpressionIrGet = dispatchReceiverParameter != null ? ExpressionHelpersKt.irGet(jvmIrBuilderCreateJvmIrBuilder$default, dispatchReceiverParameter) : null;
                if (Intrinsics.areEqual(unboxFunctionImplementation, UnboxFunctionImplementation.DefaultUnboxFunctionImplementation.INSTANCE)) {
                    throw new IllegalStateException((unboxFunctionImplementation + " is expected to be pure").toString());
                }
                if (Intrinsics.areEqual(unboxFunctionImplementation, UnboxFunctionImplementation.ExternalUnboxFunctionImplementation.INSTANCE)) {
                    throw new IllegalStateException((RenderIrElementKt.render$default(irClass, (DumpIrTreeOptions) null, 1, (Object) null) + " is expected to be external").toString());
                }
                if (unboxFunctionImplementation instanceof UnboxFunctionImplementation.DelegatingUnboxFunctionImplementation) {
                    listListOfNotNull = CollectionsKt.listOf(MfvcNodeKt.getUnboxMethod(((UnboxFunctionImplementation.DelegatingUnboxFunctionImplementation) unboxFunctionImplementation).getNode()));
                } else {
                    if (!(unboxFunctionImplementation instanceof UnboxFunctionImplementation.CustomUnboxFunctionImplementation)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    IrSimpleFunction[] irSimpleFunctionArr = new IrSimpleFunction[2];
                    UnboxFunctionImplementation.CustomUnboxFunctionImplementation customUnboxFunctionImplementation = (UnboxFunctionImplementation.CustomUnboxFunctionImplementation) unboxFunctionImplementation;
                    irSimpleFunctionArr[0] = customUnboxFunctionImplementation.getGetter();
                    MfvcNode node = customUnboxFunctionImplementation.getNode();
                    NameableMfvcNode nameableMfvcNode2 = node instanceof NameableMfvcNode ? (NameableMfvcNode) node : null;
                    irSimpleFunctionArr[1] = nameableMfvcNode2 != null ? MfvcNodeKt.getUnboxMethod(nameableMfvcNode2) : null;
                    listListOfNotNull = CollectionsKt.listOfNotNull((Object[]) irSimpleFunctionArr);
                }
                Iterator it = listListOfNotNull.iterator();
                while (it.hasNext()) {
                    IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(jvmIrBuilderCreateJvmIrBuilder$default, (IrSimpleFunction) it.next());
                    irFunctionAccessExpressionIrCall.setDispatchReceiver(irFunctionAccessExpressionIrGet);
                    irFunctionAccessExpressionIrGet = irFunctionAccessExpressionIrCall;
                }
                Intrinsics.checkNotNull(irFunctionAccessExpressionIrGet);
                irExpressionBodyIrExprBody = ExpressionHelpersKt.irExprBody(jvmIrBuilderCreateJvmIrBuilder$default, irFunctionAccessExpressionIrGet);
            }
            irSimpleFunctionBuildFunction.setBody(irExpressionBodyIrExprBody);
        }
        return irSimpleFunctionBuildFunction;
    }

    public static final NameableMfvcNode createNameableMfvcNodes(IrClass parent, JvmBackendContext context, IrSimpleType type, Map<IrTypeParameterSymbol, ? extends IrType> typeArguments, NameableMfvcNodeImpl.Companion.MethodFullNameMode methodFullNameMode, List<Name> nameParts, List<? extends IrConstructorCall> fieldAnnotations, boolean z, NameableMfvcNode nameableMfvcNode, UnboxFunctionImplementation defaultMethodsImplementationSourceNode, IrSimpleFunction irSimpleFunction, Modality modality, IrField irField) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(methodFullNameMode, "methodFullNameMode");
        Intrinsics.checkNotNullParameter(nameParts, "nameParts");
        Intrinsics.checkNotNullParameter(fieldAnnotations, "fieldAnnotations");
        Intrinsics.checkNotNullParameter(defaultMethodsImplementationSourceNode, "defaultMethodsImplementationSourceNode");
        Intrinsics.checkNotNullParameter(modality, "modality");
        IrSimpleType irSimpleType = type;
        if (MfvcNodeKt.needsMfvcFlattening(irSimpleType)) {
            return createIntermediateMfvcNode(parent, context, type, typeArguments, methodFullNameMode, nameParts, fieldAnnotations, z, (IntermediateMfvcNode) nameableMfvcNode, defaultMethodsImplementationSourceNode, irSimpleFunction, modality, irField);
        }
        return createLeafMfvcNode(parent, context, irSimpleType, methodFullNameMode, nameParts, fieldAnnotations, z, (LeafMfvcNode) nameableMfvcNode, defaultMethodsImplementationSourceNode, irSimpleFunction, modality, irField);
    }

    /* JADX WARN: Code duplicated, block: B:42:0x0131  */
    /* JADX WARN: Code duplicated, block: B:43:0x013b  */
    public static final IntermediateMfvcNode createIntermediateMfvcNode(IrClass parent, JvmBackendContext context, IrSimpleType type, final Map<IrTypeParameterSymbol, ? extends IrType> map, NameableMfvcNodeImpl.Companion.MethodFullNameMode methodFullNameMode, List<Name> list, List<? extends IrConstructorCall> fieldAnnotations, boolean z, IntermediateMfvcNode intermediateMfvcNode, UnboxFunctionImplementation unboxFunctionImplementation, IrSimpleFunction irSimpleFunction, Modality modality, IrField irField) {
        final RootMfvcNode rootMfvcNode;
        IrSimpleFunction irSimpleFunctionMakeUnboxMethod;
        UnboxFunctionImplementation unboxFunctionImplementation2;
        UnboxFunctionImplementation.CustomUnboxFunctionImplementation customUnboxFunctionImplementation;
        UnboxFunctionImplementation.DefaultUnboxFunctionImplementation customUnboxFunctionImplementation2;
        UnboxFunctionImplementation unboxFunctionImplementation3;
        UnboxFunctionImplementation.DelegatingUnboxFunctionImplementation delegatingUnboxFunctionImplementation;
        NameableMfvcNode nameableMfvcNode;
        IrField getterField;
        IrPropertySymbol correspondingPropertySymbol;
        IrPropertySymbol correspondingPropertySymbol2;
        IrProperty owner;
        Map<IrTypeParameterSymbol, ? extends IrType> typeArguments = map;
        List<Name> nameParts = list;
        IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(typeArguments, "typeArguments");
        Intrinsics.checkNotNullParameter(methodFullNameMode, "methodFullNameMode");
        Intrinsics.checkNotNullParameter(nameParts, "nameParts");
        Intrinsics.checkNotNullParameter(fieldAnnotations, "fieldAnnotations");
        Intrinsics.checkNotNullParameter(modality, "modality");
        IrSimpleType irSimpleType = type;
        boolean z2 = true;
        if (!MfvcNodeKt.needsMfvcFlattening(irSimpleType)) {
            throw new IllegalArgumentException((RenderIrElementKt.render$default(type, (DumpIrTreeOptions) null, 1, (Object) null) + " does not require flattening").toString());
        }
        IrClass erasedUpperBound = JvmIrTypeUtilsKt.getErasedUpperBound(irSimpleType);
        MultiFieldValueClassRepresentation<IrSimpleType> multiFieldValueClassRepresentation = IrDeclarationsKt.getMultiFieldValueClassRepresentation(erasedUpperBound);
        Intrinsics.checkNotNull(multiFieldValueClassRepresentation);
        MemoizedMultiFieldValueClassReplacements multiFieldValueClassReplacements = context.getMultiFieldValueClassReplacements();
        RootMfvcNode rootMfvcNodeInvoke = multiFieldValueClassReplacements.getGetRootMfvcNode().invoke(erasedUpperBound);
        IrField backingFieldIfNotDelegate = (irSimpleFunction2 == null || (correspondingPropertySymbol2 = irSimpleFunction.getCorrespondingPropertySymbol()) == null || (owner = correspondingPropertySymbol2.getOwner()) == null) ? null : getBackingFieldIfNotDelegate(owner);
        IrProperty owner2 = (backingFieldIfNotDelegate != null || irSimpleFunction2 == null || (getterField = MfvcNodeKt.getGetterField(irSimpleFunction)) == null || (correspondingPropertySymbol = getterField.getCorrespondingPropertySymbol()) == null) ? null : correspondingPropertySymbol.getOwner();
        if (irSimpleFunction2 == null || (backingFieldIfNotDelegate != null && MfvcNodeKt.isDefaultGetter(irSimpleFunction2, backingFieldIfNotDelegate))) {
            z2 = false;
        }
        boolean z3 = z2;
        Iterable<Pair> underlyingPropertyNamesToTypes = multiFieldValueClassRepresentation.getUnderlyingPropertyNamesToTypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(underlyingPropertyNamesToTypes, 10));
        for (Pair pair : underlyingPropertyNamesToTypes) {
            Name name2 = (Name) pair.component1();
            IrType irTypeSubstitute = IrTypeUtilsKt.substitute((IrSimpleType) pair.component2(), typeArguments);
            ArrayList arrayList2 = arrayList;
            Intrinsics.checkNotNull(irTypeSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            IrSimpleType irSimpleType2 = (IrSimpleType) irTypeSubstitute;
            Map mutableMap = MapsKt.toMutableMap(map);
            mutableMap.putAll(MfvcNodeKt.makeTypeArgumentsFromType(irSimpleType2));
            if (unboxFunctionImplementation != 0) {
                delegatingUnboxFunctionImplementation = unboxFunctionImplementation.get(name2);
            } else {
                if (owner2 != null) {
                    NameableMfvcNode mfvcPropertyNode = multiFieldValueClassReplacements.getMfvcPropertyNode(owner2);
                    Intrinsics.checkNotNull(mfvcPropertyNode);
                    delegatingUnboxFunctionImplementation = new UnboxFunctionImplementation.DelegatingUnboxFunctionImplementation(mfvcPropertyNode);
                } else {
                    mutableMap = mutableMap;
                    if (z3) {
                        Intrinsics.checkNotNull(irSimpleFunction);
                        NameableMfvcNode nameableMfvcNode2 = rootMfvcNodeInvoke.get(name2);
                        Intrinsics.checkNotNull(nameableMfvcNode2);
                        customUnboxFunctionImplementation2 = new UnboxFunctionImplementation.CustomUnboxFunctionImplementation(irSimpleFunction2, nameableMfvcNode2);
                    } else {
                        customUnboxFunctionImplementation2 = UnboxFunctionImplementation.DefaultUnboxFunctionImplementation.INSTANCE;
                    }
                    unboxFunctionImplementation3 = customUnboxFunctionImplementation2;
                }
                List listPlus = CollectionsKt.plus((Collection<? extends Name>) nameParts, name2);
                if (intermediateMfvcNode != 0) {
                    NameableMfvcNode nameableMfvcNode3 = intermediateMfvcNode.get(name2);
                    Intrinsics.checkNotNull(nameableMfvcNode3);
                    nameableMfvcNode = nameableMfvcNode3;
                } else {
                    nameableMfvcNode = null;
                }
                arrayList2.add(createNameableMfvcNodes(parent, context, irSimpleType2, mutableMap, methodFullNameMode, listPlus, fieldAnnotations, z, nameableMfvcNode, unboxFunctionImplementation3, null, modality, irField));
                nameParts = nameParts;
                irSimpleFunction2 = irSimpleFunction2;
                arrayList = arrayList2;
                owner2 = owner2;
                multiFieldValueClassReplacements = multiFieldValueClassReplacements;
                rootMfvcNodeInvoke = rootMfvcNodeInvoke;
                typeArguments = map;
            }
            unboxFunctionImplementation3 = delegatingUnboxFunctionImplementation;
            List listPlus2 = CollectionsKt.plus((Collection<? extends Name>) nameParts, name2);
            if (intermediateMfvcNode != 0) {
                NameableMfvcNode nameableMfvcNode4 = intermediateMfvcNode.get(name2);
                Intrinsics.checkNotNull(nameableMfvcNode4);
                nameableMfvcNode = nameableMfvcNode4;
            } else {
                nameableMfvcNode = null;
            }
            arrayList2.add(createNameableMfvcNodes(parent, context, irSimpleType2, mutableMap, methodFullNameMode, listPlus2, fieldAnnotations, z, nameableMfvcNode, unboxFunctionImplementation3, null, modality, irField));
            nameParts = nameParts;
            irSimpleFunction2 = irSimpleFunction2;
            arrayList = arrayList2;
            owner2 = owner2;
            multiFieldValueClassReplacements = multiFieldValueClassReplacements;
            rootMfvcNodeInvoke = rootMfvcNodeInvoke;
            typeArguments = map;
        }
        RootMfvcNode rootMfvcNode2 = rootMfvcNodeInvoke;
        IrSimpleFunction irSimpleFunction3 = irSimpleFunction2;
        final ArrayList arrayList3 = arrayList;
        Name nameMakeFullMethodName = NameableMfvcNodeImpl.INSTANCE.makeFullMethodName(methodFullNameMode, nameParts);
        if (z3) {
            Intrinsics.checkNotNull(irSimpleFunction);
            if (unboxFunctionImplementation == null) {
                rootMfvcNode = rootMfvcNode2;
                customUnboxFunctionImplementation = new UnboxFunctionImplementation.CustomUnboxFunctionImplementation(irSimpleFunction3, rootMfvcNode);
            } else {
                rootMfvcNode = rootMfvcNode2;
                customUnboxFunctionImplementation = unboxFunctionImplementation;
            }
            irSimpleFunctionMakeUnboxMethod = irSimpleFunction3;
            unboxFunctionImplementation2 = customUnboxFunctionImplementation;
        } else {
            rootMfvcNode = rootMfvcNode2;
            UnboxFunctionImplementation unboxFunctionImplementation4 = unboxFunctionImplementation == null ? UnboxFunctionImplementation.DefaultUnboxFunctionImplementation.INSTANCE : unboxFunctionImplementation;
            irSimpleFunctionMakeUnboxMethod = makeUnboxMethod(context, nameMakeFullMethodName, irSimpleType, parent, intermediateMfvcNode, z, unboxFunctionImplementation4, irSimpleFunction, modality, new Function2<IrBuilderWithScope, IrValueDeclaration, IrExpression>() { // from class: org.jetbrains.kotlin.backend.jvm.MfvcNodeFactoryKt.createIntermediateMfvcNode.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final IrExpression invoke(IrBuilderWithScope makeUnboxMethod, IrValueDeclaration irValueDeclaration) {
                    IrGetValueImpl irGetValueImplIrGet;
                    Intrinsics.checkNotNullParameter(makeUnboxMethod, "$this$makeUnboxMethod");
                    List<NameableMfvcNode> list2 = arrayList3;
                    ArrayList arrayList4 = new ArrayList();
                    Iterator<T> it = list2.iterator();
                    while (it.hasNext()) {
                        List<IrField> fields = MfvcNodeKt.getFields((NameableMfvcNode) it.next());
                        Intrinsics.checkNotNull(fields);
                        CollectionsKt.addAll(arrayList4, fields);
                    }
                    ArrayList<IrField> arrayList5 = arrayList4;
                    ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList5, 10));
                    for (IrField irField2 : arrayList5) {
                        if (irField2.getIsStatic()) {
                            irGetValueImplIrGet = null;
                        } else {
                            Intrinsics.checkNotNull(irValueDeclaration);
                            irGetValueImplIrGet = ExpressionHelpersKt.irGet(makeUnboxMethod, irValueDeclaration);
                        }
                        arrayList6.add(ExpressionHelpersKt.irGetField$default(makeUnboxMethod, irGetValueImplIrGet, irField2, null, 4, null));
                    }
                    return MfvcNodeKt.makeBoxedExpression(rootMfvcNode, makeUnboxMethod, map, arrayList6, new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.MfvcNodeFactoryKt.createIntermediateMfvcNode.2.1
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2() {
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public /* bridge */ /* synthetic */ Unit invoke() {
                            invoke2();
                            return Unit.INSTANCE;
                        }
                    });
                }
            });
            unboxFunctionImplementation2 = unboxFunctionImplementation4;
        }
        return new IntermediateMfvcNode(type, methodFullNameMode, list, arrayList3, irSimpleFunctionMakeUnboxMethod, unboxFunctionImplementation2, rootMfvcNode);
    }

    public static final LinkedHashSet<IrProperty> collectPropertiesAfterLowering(IrClass irClass, JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(context, "context");
        LinkedHashSet<IrPropertyOrIrField> linkedHashSetCollectPropertiesOrFieldsAfterLowering = collectPropertiesOrFieldsAfterLowering(irClass, context);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(linkedHashSetCollectPropertiesOrFieldsAfterLowering, 10));
        for (IrPropertyOrIrField irPropertyOrIrField : linkedHashSetCollectPropertiesOrFieldsAfterLowering) {
            Intrinsics.checkNotNull(irPropertyOrIrField, "null cannot be cast to non-null type org.jetbrains.kotlin.backend.jvm.IrPropertyOrIrField.Property");
            arrayList.add(((IrPropertyOrIrField.Property) irPropertyOrIrField).getProperty());
        }
        return new LinkedHashSet<>(arrayList);
    }

    public static final LinkedHashSet<IrPropertyOrIrField> collectPropertiesOrFieldsAfterLowering(IrClass irClass, JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(context, "context");
        LinkedHashSet<IrPropertyOrIrField> linkedHashSet = new LinkedHashSet<>();
        for (IrDeclaration irDeclaration : irClass.getDeclarations()) {
            if (irDeclaration instanceof IrField) {
                collectPropertiesOrFieldsAfterLowering$lambda$22$handleField(context, linkedHashSet, (IrField) irDeclaration);
            } else if (irDeclaration instanceof IrSimpleFunction) {
                collectPropertiesOrFieldsAfterLowering$lambda$22$handleAccessor(linkedHashSet, (IrSimpleFunction) irDeclaration);
            } else if (irDeclaration instanceof IrProperty) {
                IrProperty irProperty = (IrProperty) irDeclaration;
                IrField backingField = irProperty.getBackingField();
                if (backingField != null) {
                    collectPropertiesOrFieldsAfterLowering$lambda$22$handleField(context, linkedHashSet, backingField);
                }
                IrSimpleFunction getter = irProperty.getGetter();
                if (getter != null) {
                    collectPropertiesOrFieldsAfterLowering$lambda$22$handleAccessor(linkedHashSet, getter);
                }
            }
        }
        return linkedHashSet;
    }

    private static final void collectPropertiesOrFieldsAfterLowering$lambda$22$handleField(JvmBackendContext jvmBackendContext, LinkedHashSet<IrPropertyOrIrField> linkedHashSet, IrField irField) {
        IrPropertySymbol correspondingPropertySymbol = irField.getCorrespondingPropertySymbol();
        IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
        if (owner != null && !owner.getIsDelegated() && !jvmBackendContext.getMultiFieldValueClassReplacements().getFieldsToRemove(IrUtilsKt.getParentAsClass(irField)).contains(irField)) {
            linkedHashSet.add(new IrPropertyOrIrField.Property(owner));
        } else {
            linkedHashSet.add(new IrPropertyOrIrField.Field(irField));
        }
    }

    private static final void collectPropertiesOrFieldsAfterLowering$lambda$22$handleAccessor(LinkedHashSet<IrPropertyOrIrField> linkedHashSet, IrSimpleFunction irSimpleFunction) {
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        if (irSimpleFunction.getExtensionReceiverParameter() != null || irSimpleFunction.getContextReceiverParametersCount() != 0 || (correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol()) == null || (owner = correspondingPropertySymbol.getOwner()) == null) {
            return;
        }
        linkedHashSet.add(new IrPropertyOrIrField.Property(owner));
    }

    private static final boolean isStatic(IrProperty irProperty, IrDeclarationContainer irDeclarationContainer) {
        IrSimpleFunction irSimpleFunction = getterIfDeclared(irProperty, irDeclarationContainer);
        if (irSimpleFunction != null) {
            return IrUtilsKt.isStatic(irSimpleFunction);
        }
        IrField backingFieldIfNotDelegate = getBackingFieldIfNotDelegate(irProperty);
        if (backingFieldIfNotDelegate != null) {
            return backingFieldIfNotDelegate.getIsStatic();
        }
        throw new IllegalStateException(("Property without both getter and backing field:\n" + DumpIrTreeKt.dump$default(irProperty, null, 1, null)).toString());
    }

    public static final RootMfvcNode getRootNode(JvmBackendContext context, IrClass mfvc) {
        IrSimpleFunction irSimpleFunction;
        IrType type;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(mfvc, "mfvc");
        if (!IrDeclarationsKt.isMultiFieldValueClass(mfvc)) {
            throw new IllegalArgumentException((RenderIrElementKt.render$default(IrUtilsKt.getDefaultType(mfvc), (DumpIrTreeOptions) null, 1, (Object) null) + " does not require flattening").toString());
        }
        IrConstructor primaryConstructor = IrUtilsKt.getPrimaryConstructor(mfvc);
        List<IrDeclaration> declarations = mfvc.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (IrDeclaration irDeclaration : declarations) {
            IrField backingField = irDeclaration instanceof IrField ? (IrField) irDeclaration : null;
            if (backingField == null) {
                IrProperty irProperty = irDeclaration instanceof IrProperty ? (IrProperty) irDeclaration : null;
                backingField = irProperty != null ? irProperty.getBackingField() : null;
            }
            if (backingField != null) {
                arrayList.add(backingField);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            if (!((IrField) obj).getIsStatic()) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayList3 = arrayList2;
        MultiFieldValueClassRepresentation<IrSimpleType> multiFieldValueClassRepresentation = IrDeclarationsKt.getMultiFieldValueClassRepresentation(mfvc);
        Intrinsics.checkNotNull(multiFieldValueClassRepresentation);
        LinkedHashSet<IrProperty> linkedHashSetCollectPropertiesAfterLowering = collectPropertiesAfterLowering(mfvc, context);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(linkedHashSetCollectPropertiesAfterLowering, 10)), 16));
        for (Object obj2 : linkedHashSetCollectPropertiesAfterLowering) {
            IrProperty irProperty2 = (IrProperty) obj2;
            linkedHashMap.put(TuplesKt.to(Boolean.valueOf(isStatic(irProperty2, mfvc)), irProperty2.getName()), obj2);
        }
        List<NameableMfvcNode> listMakeRootMfvcNodeSubnodes = makeRootMfvcNodeSubnodes(multiFieldValueClassRepresentation, linkedHashMap, context, mfvc);
        List<LeafMfvcNode> leaves = MfvcNodeKt.getLeaves(listMakeRootMfvcNodeSubnodes);
        IrConstructor irConstructorMakeMfvcPrimaryConstructor = primaryConstructor != null ? makeMfvcPrimaryConstructor(context, primaryConstructor, mfvc, leaves, MfvcNodeKt.getFields(listMakeRootMfvcNodeSubnodes)) : null;
        IrSimpleFunction irSimpleFunctionMakePrimaryConstructorImpl = primaryConstructor != null ? makePrimaryConstructorImpl(context, primaryConstructor, mfvc, leaves, listMakeRootMfvcNodeSubnodes) : null;
        IrSimpleFunction irSimpleFunctionMakeBoxMethod = makeBoxMethod(context, mfvc, leaves, irConstructorMakeMfvcPrimaryConstructor);
        Iterator<IrSimpleFunction> it = IrUtilsKt.getFunctions(mfvc).iterator();
        boolean z = false;
        IrSimpleFunction irSimpleFunction2 = null;
        while (true) {
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                break;
            }
            IrSimpleFunction next = it.next();
            IrSimpleFunction irSimpleFunction3 = next;
            if (IrUtilsKt.isEquals(irSimpleFunction3) && !Intrinsics.areEqual(irSimpleFunction3.getOrigin(), IrDeclarationOrigin.GENERATED_MULTI_FIELD_VALUE_CLASS_MEMBER.INSTANCE)) {
                if (!z) {
                    irSimpleFunction2 = next;
                    z = true;
                }
            }
            irSimpleFunction2 = null;
            break;
        }
        IrSimpleFunction irSimpleFunction4 = irSimpleFunction2;
        Iterator<IrSimpleFunction> it2 = IrUtilsKt.getFunctions(mfvc).iterator();
        boolean z2 = false;
        IrSimpleFunction irSimpleFunction5 = null;
        while (true) {
            if (!it2.hasNext()) {
                if (!z2) {
                    break;
                }
                irSimpleFunction = irSimpleFunction5;
                break;
            }
            IrSimpleFunction next2 = it2.next();
            IrSimpleFunction irSimpleFunction6 = next2;
            if (Intrinsics.areEqual(irSimpleFunction6.getName(), OperatorNameConventions.EQUALS) && irSimpleFunction6.getContextReceiverParametersCount() == 0 && irSimpleFunction6.getExtensionReceiverParameter() == null) {
                IrValueParameter irValueParameter = (IrValueParameter) CollectionsKt.singleOrNull((List) irSimpleFunction6.getValueParameters());
                if (!Intrinsics.areEqual((irValueParameter == null || (type = irValueParameter.getType()) == null) ? null : JvmIrTypeUtilsKt.getErasedUpperBound(type), mfvc)) {
                    continue;
                } else if (!z2) {
                    irSimpleFunction5 = next2;
                    z2 = true;
                }
            }
            irSimpleFunction = null;
            break;
        }
        IrSimpleFunction irSimpleFunction7 = irSimpleFunction;
        return new RootMfvcNode(mfvc, listMakeRootMfvcNodeSubnodes, primaryConstructor, irConstructorMakeMfvcPrimaryConstructor, irSimpleFunctionMakePrimaryConstructorImpl, irSimpleFunctionMakeBoxMethod, makeSpecializedEqualsMethod(context, mfvc, arrayList3, irSimpleFunction4, irSimpleFunction7), irSimpleFunction7 == null);
    }

    private static final IrSimpleFunction makeSpecializedEqualsMethod(JvmBackendContext jvmBackendContext, IrClass irClass, List<? extends IrField> list, IrSimpleFunction irSimpleFunction, IrSimpleFunction irSimpleFunction2) {
        IrExpressionBody irExpressionBodyIrExprBody;
        if (irSimpleFunction2 != null) {
            return irSimpleFunction2;
        }
        IrFactory irFactory = jvmBackendContext.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setName(OperatorNameConventions.EQUALS);
        irFunctionBuilder.setOrigin(IrDeclarationOrigin.GENERATED_MULTI_FIELD_VALUE_CLASS_MEMBER.INSTANCE);
        irFunctionBuilder.setReturnType(jvmBackendContext.getIrBuiltIns().getBooleanType());
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(irClass);
        IrSimpleFunction irSimpleFunction3 = irSimpleFunctionBuildFunction;
        IrUtilsKt.createDispatchReceiverParameter$default(irSimpleFunction3, null, 1, null);
        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
        Name nameIdentifier = Name.identifier("other");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irValueParameterBuilder.setName(nameIdentifier);
        irValueParameterBuilder.setType(JvmIrTypeUtilsKt.getUpperBound(IrUtilsKt.getDefaultType(irClass)));
        if (irValueParameterBuilder.getIndex() == -1) {
            irValueParameterBuilder.setIndex(irSimpleFunction3.getValueParameters().size());
        }
        IrValueParameter irValueParameterBuildValueParameter = DeclarationBuildersKt.buildValueParameter(irSimpleFunction3.getFactory(), irValueParameterBuilder, irSimpleFunction3);
        irSimpleFunction3.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction3.getValueParameters(), irValueParameterBuildValueParameter));
        if (isKotlinExternalStub(irClass)) {
            return irSimpleFunctionBuildFunction;
        }
        JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(jvmBackendContext, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, null);
        if (irSimpleFunction != null) {
            JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder$default;
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(jvmIrBuilder, irSimpleFunction);
            IrValueParameter dispatchReceiverParameter = irSimpleFunctionBuildFunction.getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter);
            irFunctionAccessExpressionIrCall.setDispatchReceiver(ExpressionHelpersKt.irGet(jvmIrBuilder, dispatchReceiverParameter));
            irFunctionAccessExpressionIrCall.putValueArgument(0, ExpressionHelpersKt.irGet(jvmIrBuilder, irValueParameterBuildValueParameter));
            irExpressionBodyIrExprBody = ExpressionHelpersKt.irExprBody(jvmIrBuilder, irFunctionAccessExpressionIrCall);
        } else {
            List<? extends IrField> list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            for (IrField irField : list2) {
                JvmIrBuilder jvmIrBuilder2 = jvmIrBuilderCreateJvmIrBuilder$default;
                IrValueParameter dispatchReceiverParameter2 = irSimpleFunctionBuildFunction.getDispatchReceiverParameter();
                Intrinsics.checkNotNull(dispatchReceiverParameter2);
                arrayList.add(ExpressionHelpersKt.irGetField$default(jvmIrBuilder2, ExpressionHelpersKt.irGet(jvmIrBuilder2, dispatchReceiverParameter2), irField, null, 4, null));
            }
            ArrayList arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                JvmIrBuilder jvmIrBuilder3 = jvmIrBuilderCreateJvmIrBuilder$default;
                arrayList3.add(ExpressionHelpersKt.irGetField$default(jvmIrBuilder3, ExpressionHelpersKt.irGet(jvmIrBuilder3, irValueParameterBuildValueParameter), (IrField) it.next(), null, 4, null));
            }
            ArrayList arrayList4 = arrayList2;
            Iterator it2 = arrayList4.iterator();
            ArrayList arrayList5 = arrayList3;
            Iterator it3 = arrayList5.iterator();
            ArrayList arrayList6 = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(arrayList4, 10), CollectionsKt.collectionSizeOrDefault(arrayList5, 10)));
            while (it2.hasNext() && it3.hasNext()) {
                arrayList6.add(ExpressionHelpersKt.irEquals$default(jvmIrBuilderCreateJvmIrBuilder$default, (IrGetFieldImpl) it2.next(), (IrGetFieldImpl) it3.next(), null, 4, null));
            }
            JvmIrBuilder jvmIrBuilder4 = jvmIrBuilderCreateJvmIrBuilder$default;
            Iterator it4 = arrayList6.iterator();
            if (!it4.hasNext()) {
                throw new UnsupportedOperationException("Empty collection can't be reduced.");
            }
            IrCall next = it4.next();
            while (it4.hasNext()) {
                IrExpression irExpression = (IrExpression) it4.next();
                IrCall irCall = ExpressionHelpersKt.irCall((IrBuilderWithScope) jvmIrBuilder4, jvmBackendContext.getIrBuiltIns().getAndandSymbol());
                irCall.putValueArgument(0, (IrExpression) next);
                irCall.putValueArgument(1, irExpression);
                next = irCall;
            }
            irExpressionBodyIrExprBody = ExpressionHelpersKt.irExprBody(jvmIrBuilder4, (IrExpression) next);
        }
        irSimpleFunctionBuildFunction.setBody(irExpressionBodyIrExprBody);
        return irSimpleFunctionBuildFunction;
    }

    private static final IrSimpleFunction makeBoxMethod(JvmBackendContext jvmBackendContext, IrClass irClass, List<LeafMfvcNode> list, IrConstructor irConstructor) {
        IrFactory irFactory = jvmBackendContext.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameIdentifier = Name.identifier(KotlinTypeMapper.BOX_JVM_METHOD_NAME);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        irFunctionBuilder.setName(nameIdentifier);
        irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.SYNTHETIC_MULTI_FIELD_VALUE_CLASS_MEMBER.INSTANCE);
        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClass));
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(irClass);
        IrUtilsKt.copyTypeParametersFrom$default(irSimpleFunctionBuildFunction, irClass, null, null, 6, null);
        List<IrTypeParameter> typeParameters = irClass.getTypeParameters();
        List<IrTypeParameter> typeParameters2 = irSimpleFunctionBuildFunction.getTypeParameters();
        Iterator<T> it = typeParameters.iterator();
        Iterator<T> it2 = typeParameters2.iterator();
        ArrayList arrayList = new ArrayList(Math.min(CollectionsKt.collectionSizeOrDefault(typeParameters, 10), CollectionsKt.collectionSizeOrDefault(typeParameters2, 10)));
        while (it.hasNext() && it2.hasNext()) {
            arrayList.add(TuplesKt.to(((IrTypeParameter) it.next()).getSymbol(), IrTypesKt.getDefaultType((IrTypeParameter) it2.next())));
        }
        Map map = MapsKt.toMap(arrayList);
        irSimpleFunctionBuildFunction.setReturnType(IrTypeUtilsKt.substitute(irSimpleFunctionBuildFunction.getReturnType(), map));
        List<LeafMfvcNode> list2 = list;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        for (LeafMfvcNode leafMfvcNode : list2) {
            arrayList2.add(DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionBuildFunction, MfvcNodeKt.getFullFieldName(leafMfvcNode), IrTypeUtilsKt.substitute(leafMfvcNode.getType(), map), (IrDeclarationOrigin) null, 4, (Object) null));
        }
        ArrayList arrayList3 = arrayList2;
        if (!isKotlinExternalStub(irClass)) {
            JvmIrBuilder jvmIrBuilderCreateJvmIrBuilder$default = JvmIrBuilderKt.createJvmIrBuilder$default(jvmBackendContext, irSimpleFunctionBuildFunction.getSymbol(), 0, 0, 6, null);
            throwWhenNotExternalIsNull(irClass, irConstructor);
            JvmIrBuilder jvmIrBuilder = jvmIrBuilderCreateJvmIrBuilder$default;
            IrFunctionAccessExpression irFunctionAccessExpressionIrCall = ExpressionHelpersKt.irCall(jvmIrBuilder, irConstructor);
            Iterator it3 = arrayList3.iterator();
            int i = 0;
            while (it3.hasNext()) {
                irFunctionAccessExpressionIrCall.putValueArgument(i, ExpressionHelpersKt.irGet(jvmIrBuilder, (IrValueParameter) it3.next()));
                i++;
            }
            irSimpleFunctionBuildFunction.setBody(ExpressionHelpersKt.irExprBody(jvmIrBuilder, irFunctionAccessExpressionIrCall));
        }
        return irSimpleFunctionBuildFunction;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final IrSimpleFunction makePrimaryConstructorImpl(JvmBackendContext jvmBackendContext, IrConstructor irConstructor, IrClass irClass, List<LeafMfvcNode> list, List<? extends NameableMfvcNode> list2) {
        IrFactory irFactory = jvmBackendContext.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        int i = 0;
        irFunctionBuilder.setName(InlineClassAbi.INSTANCE.mangledNameFor(jvmBackendContext, irConstructor, false, false));
        irFunctionBuilder.setVisibility(irConstructor.getVisibility());
        irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.STATIC_MULTI_FIELD_VALUE_CLASS_CONSTRUCTOR.INSTANCE);
        irFunctionBuilder.setReturnType(jvmBackendContext.getIrBuiltIns().getUnitType());
        irFunctionBuilder.setModality(Modality.FINAL);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(irClass);
        IrUtilsKt.copyTypeParametersFrom$default(irSimpleFunctionBuildFunction, irClass, null, null, 6, null);
        for (LeafMfvcNode leafMfvcNode : list) {
            IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
            Name fullFieldName = MfvcNodeKt.getFullFieldName(leafMfvcNode);
            IrType type = leafMfvcNode.getType();
            List<IrTypeParameter> typeParameters = irClass.getTypeParameters();
            List<IrTypeParameter> typeParameters2 = irSimpleFunctionBuildFunction.getTypeParameters();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters2, 10));
            Iterator<T> it = typeParameters2.iterator();
            while (it.hasNext()) {
                arrayList.add(IrTypesKt.getDefaultType((IrTypeParameter) it.next()));
            }
            DeclarationBuildersKt.addValueParameter$default(irSimpleFunction, fullFieldName, IrTypeUtilsKt.substitute(type, typeParameters, arrayList), (IrDeclarationOrigin) null, 4, (Object) null);
        }
        for (IrValueParameter irValueParameter : irConstructor.getValueParameters()) {
            int i2 = i + 1;
            NameableMfvcNode nameableMfvcNode = list2.get(i);
            Map<NameableMfvcNode, IntRange> subnodeIndices = MfvcNodeKt.getSubnodeIndices(list2);
            if (nameableMfvcNode instanceof LeafMfvcNode) {
                IntRange intRange = subnodeIndices.get(nameableMfvcNode);
                Intrinsics.checkNotNull(intRange);
                irSimpleFunctionBuildFunction.getValueParameters().get(intRange.getFirst()).setAnnotations(irValueParameter.getAnnotations());
            }
            i = i2;
        }
        irSimpleFunctionBuildFunction.setAnnotations(irConstructor.getAnnotations());
        if (irConstructor.getMetadata() != null) {
            irSimpleFunctionBuildFunction.setMetadata(irConstructor.getMetadata());
            irConstructor.setMetadata(null);
        }
        IrDeclarationsKt.copyAttributes(irSimpleFunctionBuildFunction, irConstructor instanceof IrAttributeContainer ? (IrAttributeContainer) irConstructor : null);
        return irSimpleFunctionBuildFunction;
    }

    private static final IrConstructor makeMfvcPrimaryConstructor(JvmBackendContext jvmBackendContext, IrConstructor irConstructor, IrClass irClass, List<LeafMfvcNode> list, List<? extends IrField> list2) {
        IrFactory irFactory = jvmBackendContext.getIrFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.updateFrom((IrFunction) irConstructor);
        DescriptorVisibility PRIVATE = DescriptorVisibilities.PRIVATE;
        Intrinsics.checkNotNullExpressionValue(PRIVATE, "PRIVATE");
        irFunctionBuilder.setVisibility(PRIVATE);
        irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.SYNTHETIC_MULTI_FIELD_VALUE_CLASS_MEMBER.INSTANCE);
        irFunctionBuilder.setReturnType(irConstructor.getReturnType());
        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(irFactory, irFunctionBuilder);
        if (!irConstructor.getTypeParameters().isEmpty()) {
            throw new IllegalArgumentException("Constructors do not support type parameters yet".toString());
        }
        irConstructorBuildConstructor.setParent(irClass);
        List<LeafMfvcNode> list3 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
        for (LeafMfvcNode leafMfvcNode : list3) {
            arrayList.add(DeclarationBuildersKt.addValueParameter$default(irConstructorBuildConstructor, MfvcNodeKt.getFullFieldName(leafMfvcNode), leafMfvcNode.getType(), (IrDeclarationOrigin) null, 4, (Object) null));
        }
        ArrayList arrayList2 = arrayList;
        if (!isKotlinExternalStub(irClass)) {
            DeclarationIrBuilder declarationIrBuilderCreateIrBuilder$default = LowerUtilsKt.createIrBuilder$default(jvmBackendContext, irConstructorBuildConstructor.getSymbol(), 0, 0, 6, (Object) null);
            IrConstructor irConstructor2 = irConstructorBuildConstructor;
            IrBlockBodyBuilder irBlockBodyBuilder = new IrBlockBodyBuilder(declarationIrBuilderCreateIrBuilder$default.getContext(), declarationIrBuilderCreateIrBuilder$default.getScope(), irConstructor2.getStartOffset(), irConstructor2.getEndOffset());
            IrBlockBodyBuilder irBlockBodyBuilder2 = irBlockBodyBuilder;
            irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irDelegatingConstructorCall(irBlockBodyBuilder2, (IrConstructor) SequencesKt.single(IrUtilsKt.getConstructors(jvmBackendContext.getIrBuiltIns().getAnyClass().getOwner()))));
            Intrinsics.checkNotNull(list2);
            for (Pair pair : CollectionsKt.zip(list2, arrayList2)) {
                IrField irField = (IrField) pair.component1();
                IrValueParameter irValueParameter = (IrValueParameter) pair.component2();
                IrValueParameter thisReceiver = irClass.getThisReceiver();
                Intrinsics.checkNotNull(thisReceiver);
                irBlockBodyBuilder.unaryPlus(ExpressionHelpersKt.irSetField$default(irBlockBodyBuilder2, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, thisReceiver), irField, ExpressionHelpersKt.irGet(irBlockBodyBuilder2, irValueParameter), null, 8, null));
            }
            irConstructorBuildConstructor.setBody(irBlockBodyBuilder.getIrBlockBody());
        }
        return irConstructorBuildConstructor;
    }

    private static final List<NameableMfvcNode> makeRootMfvcNodeSubnodes(MultiFieldValueClassRepresentation<IrSimpleType> multiFieldValueClassRepresentation, Map<Pair<Boolean, Name>, ? extends IrProperty> map, JvmBackendContext jvmBackendContext, IrClass irClass) {
        List<IrConstructorCall> listEmptyList;
        Iterable<Pair> underlyingPropertyNamesToTypes = multiFieldValueClassRepresentation.getUnderlyingPropertyNamesToTypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(underlyingPropertyNamesToTypes, 10));
        for (Pair pair : underlyingPropertyNamesToTypes) {
            Name name2 = (Name) pair.component1();
            IrSimpleType irSimpleType = (IrSimpleType) pair.component2();
            Map<IrTypeParameterSymbol, IrType> mapMakeTypeArgumentsFromType = MfvcNodeKt.makeTypeArgumentsFromType(irSimpleType);
            IrProperty irProperty = map.get(TuplesKt.to(false, name2));
            Intrinsics.checkNotNull(irProperty);
            IrProperty irProperty2 = irProperty;
            IrField backingFieldIfNotDelegate = getBackingFieldIfNotDelegate(irProperty2);
            IrClass irClass2 = irClass;
            IrSimpleFunction irSimpleFunction = getterIfDeclared(irProperty2, irClass2);
            IntermediateMfvcNode intermediateMfvcNode = irSimpleFunction != null ? (IntermediateMfvcNode) getOverriddenNode(jvmBackendContext.getMultiFieldValueClassReplacements(), irSimpleFunction) : null;
            boolean zIsStatic = isStatic(irProperty2, irClass2);
            NameableMfvcNodeImpl.Companion.MethodFullNameMode methodFullNameMode = NameableMfvcNodeImpl.Companion.MethodFullNameMode.UnboxFunction;
            List listListOf = CollectionsKt.listOf(name2);
            if (backingFieldIfNotDelegate == null || (listEmptyList = backingFieldIfNotDelegate.getAnnotations()) == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            NameableMfvcNode nameableMfvcNodeCreateNameableMfvcNodes = createNameableMfvcNodes(irClass, jvmBackendContext, irSimpleType, mapMakeTypeArgumentsFromType, methodFullNameMode, listListOf, listEmptyList, zIsStatic, intermediateMfvcNode, UnboxFunctionImplementation.DefaultUnboxFunctionImplementation.INSTANCE, zIsStatic ? irSimpleFunction : null, Modality.FINAL, backingFieldIfNotDelegate);
            updateAnnotationsAndPropertyFromOldProperty(irProperty2, jvmBackendContext, nameableMfvcNodeCreateNameableMfvcNodes);
            MfvcNodeKt.getUnboxMethod(nameableMfvcNodeCreateNameableMfvcNodes).setOverriddenSymbols(CollectionsKt.emptyList());
            arrayList.add(nameableMfvcNodeCreateNameableMfvcNodes);
        }
        return arrayList;
    }

    private static final void updateAnnotationsAndPropertyFromOldProperty(IrProperty irProperty, JvmBackendContext jvmBackendContext, MfvcNode mfvcNode) {
        IrField backingField;
        if ((mfvcNode instanceof LeafMfvcNode) || (backingField = irProperty.getBackingField()) == null) {
            return;
        }
        jvmBackendContext.getMultiFieldValueClassReplacements().addFieldToRemove(IrUtilsKt.getParentAsClass(backingField), backingField);
    }

    public static final IntermediateMfvcNode createIntermediateNodeForMfvcPropertyOfRegularClass(IrClass parent, JvmBackendContext context, IrProperty oldProperty) {
        IrType type;
        List<IrConstructorCall> listEmptyList;
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(oldProperty, "oldProperty");
        IrClass irClass = parent;
        IrSimpleFunction irSimpleFunction = getterIfDeclared(oldProperty, irClass);
        IrField backingFieldIfNotDelegate = getBackingFieldIfNotDelegate(oldProperty);
        IrSimpleFunction getter = oldProperty.getGetter();
        if (getter == null || (type = getter.getReturnType()) == null) {
            if (backingFieldIfNotDelegate == null) {
                throw new IllegalStateException("Either getter or field must exist".toString());
            }
            type = backingFieldIfNotDelegate.getType();
        }
        if (!(type instanceof IrSimpleType) || !MfvcNodeKt.needsMfvcFlattening(type)) {
            throw new IllegalArgumentException(("Expected MFVC but got " + RenderIrElementKt.render$default(type, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        if (backingFieldIfNotDelegate == null || (listEmptyList = backingFieldIfNotDelegate.getAnnotations()) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        IrSimpleType irSimpleType = (IrSimpleType) type;
        IntermediateMfvcNode intermediateMfvcNodeCreateIntermediateMfvcNode = createIntermediateMfvcNode(parent, context, irSimpleType, MfvcNodeKt.makeTypeArgumentsFromType(irSimpleType), NameableMfvcNodeImpl.Companion.MethodFullNameMode.Getter, CollectionsKt.listOf(oldProperty.getName()), listEmptyList, isStatic(oldProperty, irClass), irSimpleFunction != null ? (IntermediateMfvcNode) getOverriddenNode(context.getMultiFieldValueClassReplacements(), irSimpleFunction) : null, isKotlinExternalStub(parent) ? UnboxFunctionImplementation.ExternalUnboxFunctionImplementation.INSTANCE : null, irSimpleFunction, (irSimpleFunction == null || irSimpleFunction.getModality() == Modality.FINAL) ? Modality.FINAL : irSimpleFunction.getModality(), backingFieldIfNotDelegate);
        updateAnnotationsAndPropertyFromOldProperty(oldProperty, context, intermediateMfvcNodeCreateIntermediateMfvcNode);
        return intermediateMfvcNodeCreateIntermediateMfvcNode;
    }

    public static final IntermediateMfvcNode createIntermediateNodeForStandaloneMfvcField(IrClass parent, JvmBackendContext context, IrField oldField) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(oldField, "oldField");
        IrType type = oldField.getType();
        if (!(type instanceof IrSimpleType) || !MfvcNodeKt.needsMfvcFlattening(type)) {
            throw new IllegalArgumentException(("Expected MFVC but got " + RenderIrElementKt.render$default(type, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        IrSimpleType irSimpleType = (IrSimpleType) type;
        return createIntermediateMfvcNode(parent, context, irSimpleType, MfvcNodeKt.makeTypeArgumentsFromType(irSimpleType), NameableMfvcNodeImpl.Companion.MethodFullNameMode.Getter, CollectionsKt.listOf(oldField.getName()), oldField.getAnnotations(), oldField.getIsStatic(), null, null, null, Modality.FINAL, oldField);
    }

    private static final NameableMfvcNode getOverriddenNode(MemoizedMultiFieldValueClassReplacements memoizedMultiFieldValueClassReplacements, IrSimpleFunction irSimpleFunction) {
        Object next;
        Iterator<T> it = irSimpleFunction.getOverriddenSymbols().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (((IrSimpleFunctionSymbol) next).getOwner().getIsFakeOverride());
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) next;
        if (irSimpleFunctionSymbol == null) {
            return null;
        }
        IrPropertySymbol correspondingPropertySymbol = irSimpleFunctionSymbol.getOwner().getCorrespondingPropertySymbol();
        Intrinsics.checkNotNull(correspondingPropertySymbol);
        return memoizedMultiFieldValueClassReplacements.getMfvcPropertyNode(correspondingPropertySymbol.getOwner());
    }

    private static final IrSimpleFunction getterIfDeclared(IrProperty irProperty, IrDeclarationContainer irDeclarationContainer) {
        IrSimpleFunction getter = irProperty.getGetter();
        if (getter == null || !(irDeclarationContainer.getDeclarations().contains(getter) || irDeclarationContainer.getDeclarations().contains(irProperty))) {
            return null;
        }
        return getter;
    }

    private static final IrField getBackingFieldIfNotDelegate(IrProperty irProperty) {
        IrField backingField = irProperty.getBackingField();
        if (backingField == null || irProperty.getIsDelegated()) {
            return null;
        }
        return backingField;
    }
}
