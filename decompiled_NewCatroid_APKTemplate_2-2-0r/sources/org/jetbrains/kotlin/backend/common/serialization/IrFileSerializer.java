package org.jetbrains.kotlin.backend.common.serialization;

import com.google.android.gms.cast.MediaError;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinaryCoordinates;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinaryNameAndType;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinarySymbolData;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinaryTypeProjection;
import org.jetbrains.kotlin.backend.common.serialization.encodings.ClassFlags;
import org.jetbrains.kotlin.backend.common.serialization.encodings.FieldFlags;
import org.jetbrains.kotlin.backend.common.serialization.encodings.FunctionFlags;
import org.jetbrains.kotlin.backend.common.serialization.encodings.LocalVariableFlags;
import org.jetbrains.kotlin.backend.common.serialization.encodings.PropertyFlags;
import org.jetbrains.kotlin.backend.common.serialization.encodings.TypeAliasFlags;
import org.jetbrains.kotlin.backend.common.serialization.encodings.TypeParameterFlags;
import org.jetbrains.kotlin.backend.common.serialization.encodings.ValueParameterFlags;
import org.jetbrains.kotlin.backend.common.serialization.proto.AccessorIdSignature;
import org.jetbrains.kotlin.backend.common.serialization.proto.Actual;
import org.jetbrains.kotlin.backend.common.serialization.proto.CommonIdSignature;
import org.jetbrains.kotlin.backend.common.serialization.proto.CompositeSignature;
import org.jetbrains.kotlin.backend.common.serialization.proto.FieldAccessCommon;
import org.jetbrains.kotlin.backend.common.serialization.proto.FileEntry;
import org.jetbrains.kotlin.backend.common.serialization.proto.FileLocalIdSignature;
import org.jetbrains.kotlin.backend.common.serialization.proto.FileSignature;
import org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrAnonymousInit;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrBlock;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrBlockBody;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrBranch;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrBreak;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrCall;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrCatch;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrClassReference;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrComposite;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrConst;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructor;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrConstructorCall;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrContinue;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclarationBase;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDoWhile;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicMemberExpression;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicOperatorExpression;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDynamicType;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumConstructorCall;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrEnumEntry;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorCallExpression;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorDeclaration;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorExpression;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrErrorType;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionBase;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionExpression;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrFunctionReference;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrGetClass;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrGetEnumValue;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrGetField;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrGetObject;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrGetValue;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrInlineClassRepresentation;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrInstanceInitializerCall;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedProperty;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrLocalDelegatedPropertyReference;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrMultiFieldValueClassRepresentation;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrOperation;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrPropertyReference;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrSetField;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrSetValue;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleType;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrSimpleTypeNullability;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrSpreadElement;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrStatement;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrStringConcat;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrSyntheticBody;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrThrow;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrTry;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrType;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAlias;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOp;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeParameter;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrValueParameter;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrVararg;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrVariable;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrWhen;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrWhile;
import org.jetbrains.kotlin.backend.common.serialization.proto.LocalSignature;
import org.jetbrains.kotlin.backend.common.serialization.proto.Loop;
import org.jetbrains.kotlin.backend.common.serialization.proto.MemberAccessCommon;
import org.jetbrains.kotlin.backend.common.serialization.proto.NullableIrExpression;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.MultiFieldValueClassRepresentation;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBreakContinue;
import org.jetbrains.kotlin.ir.expressions.IrCallableReference;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrConstantArray;
import org.jetbrains.kotlin.ir.expressions.IrConstantObject;
import org.jetbrains.kotlin.ir.expressions.IrConstantPrimitive;
import org.jetbrains.kotlin.ir.expressions.IrConstantValue;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrDoWhileLoop;
import org.jetbrains.kotlin.ir.expressions.IrDynamicExpression;
import org.jetbrains.kotlin.ir.expressions.IrDynamicOperator;
import org.jetbrains.kotlin.ir.expressions.IrElseBranch;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrFieldAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetSingletonValue;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrStatementOriginImpl;
import org.jetbrains.kotlin.ir.expressions.IrStringConcatenation;
import org.jetbrains.kotlin.ir.expressions.IrSuspendableExpression;
import org.jetbrains.kotlin.ir.expressions.IrSuspensionPoint;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBodyKind;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.expressions.IrValueAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.IrWhileLoop;
import org.jetbrains.kotlin.ir.symbols.IrAnonymousInitializerSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrLocalDelegatedPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnableBlockSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrVariableSymbol;
import org.jetbrains.kotlin.ir.types.IrStarProjection;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IdSignatureRendererKt;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.SerializedDeclaration;
import org.jetbrains.kotlin.library.SerializedIrFile;
import org.jetbrains.kotlin.library.impl.IrMemoryArrayWriter;
import org.jetbrains.kotlin.library.impl.IrMemoryDeclarationWriter;
import org.jetbrains.kotlin.library.impl.IrMemoryStringWriter;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.SmartList;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrFileSerializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000ê\b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0016\u0018\u0000 \u0087\u00032\u00020\u0001:\u000e\u0087\u0003\u0088\u0003\u0089\u0003\u008a\u0003\u008b\u0003\u008c\u0003\u008d\u0003Be\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u000f\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013¢\u0006\u0002\u0010\u0015J\u0010\u0010@\u001a\u00020\u000f2\u0006\u0010A\u001a\u00020BH\u0016J\u0010\u0010C\u001a\u00020\u000f2\u0006\u0010A\u001a\u00020BH\u0016J\u0012\u0010D\u001a\u0004\u0018\u00010E2\u0006\u0010F\u001a\u00020GH\u0016J\u0010\u0010H\u001a\u00020\u000f2\u0006\u0010I\u001a\u00020JH\u0016J\u0018\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020G2\u0006\u0010N\u001a\u00020OH\u0002J\u0010\u0010P\u001a\u00020\u000f2\u0006\u0010Q\u001a\u00020RH\u0016J\u0010\u0010S\u001a\u00020\u000f2\u0006\u0010T\u001a\u00020UH\u0016J\u0010\u0010V\u001a\u00020\u00172\u0006\u0010W\u001a\u00020UH\u0002J\u0010\u0010V\u001a\u00020\u00172\u0006\u0010X\u001a\u00020.H\u0002J\u0010\u0010Y\u001a\u00020Z2\u0006\u0010[\u001a\u00020\\H\u0002J\u001c\u0010]\u001a\b\u0012\u0004\u0012\u00020_0^2\f\u0010`\u001a\b\u0012\u0004\u0012\u00020a0^H\u0002J\u0010\u0010b\u001a\u00020c2\u0006\u0010d\u001a\u00020eH\u0002J\u0010\u0010f\u001a\u00020g2\u0006\u0010h\u001a\u00020iH\u0002J\u0010\u0010j\u001a\u00020k2\u0006\u0010l\u001a\u00020mH\u0002J\u0010\u0010n\u001a\u00020o2\u0006\u0010h\u001a\u00020pH\u0002J\u0010\u0010q\u001a\u00020r2\u0006\u0010s\u001a\u00020tH\u0002J\u0010\u0010u\u001a\u00020v2\u0006\u0010w\u001a\u00020xH\u0002J\u0010\u0010y\u001a\u00020z2\u0006\u0010h\u001a\u00020{H\u0002J\u0010\u0010|\u001a\u00020}2\u0006\u0010~\u001a\u00020\u007fH\u0002J\u0013\u0010\u0080\u0001\u001a\u00030\u0081\u00012\u0007\u0010[\u001a\u00030\u0082\u0001H\u0002J\u0018\u0010\u0083\u0001\u001a\u00030\u0084\u00012\f\u0010\u0085\u0001\u001a\u0007\u0012\u0002\b\u00030\u0086\u0001H\u0002J\u0011\u0010\u0087\u0001\u001a\u00020_2\u0006\u0010s\u001a\u00020aH\u0002J\u0013\u0010\u0088\u0001\u001a\u00030\u0089\u00012\u0007\u0010h\u001a\u00030\u008a\u0001H\u0002J\u001c\u0010\u008b\u0001\u001a\u00030\u008c\u00012\u0007\u0010\u008d\u0001\u001a\u00020\u00172\u0007\u0010\u008e\u0001\u001a\u00020\u0017H\u0002J\u0012\u0010\u008f\u0001\u001a\u00020\u00172\u0007\u0010\u0085\u0001\u001a\u00020\u0014H\u0002J\u0010\u0010\u0090\u0001\u001a\u00030\u0091\u00012\u0006\u0010W\u001a\u00020UJ\u0013\u0010\u0092\u0001\u001a\u00030\u0093\u00012\u0007\u0010s\u001a\u00030\u0094\u0001H\u0002J\u0013\u0010\u0095\u0001\u001a\u00030\u0096\u00012\u0007\u0010h\u001a\u00030\u0097\u0001H\u0002J\u0013\u0010\u0098\u0001\u001a\u00030\u0099\u00012\u0007\u0010h\u001a\u00030\u009a\u0001H\u0002J\u0014\u0010\u009b\u0001\u001a\u00030\u009c\u00012\b\u0010\u009d\u0001\u001a\u00030\u009e\u0001H\u0002J\u0013\u0010\u009f\u0001\u001a\u00030 \u00012\u0007\u0010h\u001a\u00030¡\u0001H\u0002J\u0014\u0010¢\u0001\u001a\u00030£\u00012\b\u0010¤\u0001\u001a\u00030¥\u0001H\u0002J\u0013\u0010¦\u0001\u001a\u00030§\u00012\u0007\u0010s\u001a\u00030¨\u0001H\u0002J\u0014\u0010©\u0001\u001a\u00030ª\u00012\b\u0010«\u0001\u001a\u00030¬\u0001H\u0002J\u0013\u0010\u00ad\u0001\u001a\u00030®\u00012\u0007\u0010h\u001a\u00030¯\u0001H\u0002J\u0014\u0010°\u0001\u001a\u00030±\u00012\b\u0010¤\u0001\u001a\u00030²\u0001H\u0002J\u0011\u0010³\u0001\u001a\u00020L2\u0006\u0010N\u001a\u00020OH\u0002J\u0013\u0010´\u0001\u001a\u00030µ\u00012\u0007\u0010h\u001a\u00030¶\u0001H\u0002J\u0013\u0010·\u0001\u001a\u00030¸\u00012\u0007\u0010h\u001a\u00030¹\u0001H\u0002J\u0014\u0010º\u0001\u001a\u00030»\u00012\b\u0010¼\u0001\u001a\u00030½\u0001H\u0002J\u0013\u0010¾\u0001\u001a\u00030¿\u00012\u0007\u0010[\u001a\u00030À\u0001H\u0002J\u0018\u0010Á\u0001\u001a\b\u0012\u0004\u0012\u00020\u00170^2\u0007\u0010Â\u0001\u001a\u00020\u0014H\u0002J\u0014\u0010Ã\u0001\u001a\u00030Ä\u00012\b\u0010Å\u0001\u001a\u00030Æ\u0001H\u0002J\u0014\u0010Ç\u0001\u001a\u00030È\u00012\b\u0010É\u0001\u001a\u00030Ê\u0001H\u0002J\u0013\u0010Ë\u0001\u001a\u00030Ì\u00012\u0007\u0010h\u001a\u00030Í\u0001H\u0002J\u0013\u0010Î\u0001\u001a\u00030Ï\u00012\u0007\u0010h\u001a\u00030Ð\u0001H\u0002J\u0013\u0010Ñ\u0001\u001a\u00030Ò\u00012\u0007\u0010h\u001a\u00030Ó\u0001H\u0002J\u0013\u0010Ô\u0001\u001a\u00030Õ\u00012\u0007\u0010h\u001a\u00030Ö\u0001H\u0002J\u0013\u0010×\u0001\u001a\u00030Ø\u00012\u0007\u0010h\u001a\u00030Ù\u0001H\u0002J\u0012\u0010Ú\u0001\u001a\u00020+2\u0007\u0010Û\u0001\u001a\u00020.H\u0002J\u001b\u0010Ü\u0001\u001a\u00030Ý\u00012\u000f\u0010Þ\u0001\u001a\n\u0012\u0005\u0012\u00030à\u00010ß\u0001H\u0002J\u0013\u0010á\u0001\u001a\u00030â\u00012\u0007\u0010s\u001a\u00030ã\u0001H\u0002J\u0013\u0010ä\u0001\u001a\u00030å\u00012\u0007\u0010W\u001a\u00030æ\u0001H\u0002J\u0013\u0010ç\u0001\u001a\u00030è\u00012\u0007\u0010é\u0001\u001a\u00020JH\u0002J\u0013\u0010ê\u0001\u001a\u00030ë\u00012\u0007\u0010W\u001a\u00030ì\u0001H\u0002J$\u0010í\u0001\u001a\u00030î\u00012\u0006\u0010W\u001a\u00020U2\n\u0010ï\u0001\u001a\u0005\u0018\u00010\u008c\u0001H\u0002¢\u0006\u0003\u0010ð\u0001J\u0013\u0010ñ\u0001\u001a\u00020\u00172\b\u0010ò\u0001\u001a\u00030ó\u0001H\u0002J\u0014\u0010ô\u0001\u001a\u00030õ\u00012\b\u0010ö\u0001\u001a\u00030÷\u0001H\u0002J\u0014\u0010ø\u0001\u001a\u00030ù\u00012\b\u0010ú\u0001\u001a\u00030û\u0001H\u0002J\u0012\u0010ü\u0001\u001a\u00020\u00172\u0007\u0010h\u001a\u00030¶\u0001H\u0002J\u0014\u0010ý\u0001\u001a\u00030þ\u00012\b\u0010ÿ\u0001\u001a\u00030\u0080\u0002H\u0002J\u0010\u0010\u0081\u0002\u001a\u00030\u0082\u00022\u0006\u0010M\u001a\u00020GJ\u0012\u0010\u0083\u0002\u001a\u00030\u0082\u00022\u0006\u0010M\u001a\u00020GH\u0002J\u0013\u0010\u0084\u0002\u001a\u00030\u0085\u00022\u0007\u0010W\u001a\u00030\u0086\u0002H\u0002J\u001e\u0010\u0087\u0002\u001a\u00030\u0088\u00022\b\u0010\u0089\u0002\u001a\u00030\u008a\u00022\b\u0010ï\u0001\u001a\u00030\u008c\u0001H\u0002J\u0014\u0010\u008b\u0002\u001a\u00030\u008c\u00022\b\u0010\u008d\u0002\u001a\u00030\u008e\u0002H\u0002J\u0014\u0010\u008f\u0002\u001a\u00030\u0090\u00022\b\u0010É\u0001\u001a\u00030\u0091\u0002H\u0002J\u0012\u0010\u0092\u0002\u001a\u00030\u0093\u00022\u0006\u0010Q\u001a\u00020RH\u0002J\n\u0010\u0094\u0002\u001a\u00030\u008c\u0001H\u0002J\u0013\u0010\u0095\u0002\u001a\u00020\u00172\b\u0010\u0096\u0002\u001a\u00030\u0097\u0002H\u0002J\u0013\u0010\u0098\u0002\u001a\u00020\u00172\b\u0010ò\u0001\u001a\u00030\u0099\u0002H\u0002J\u0013\u0010\u009a\u0002\u001a\u00030\u008c\u00012\u0007\u0010\u009b\u0002\u001a\u00020\tH\u0002J\u0012\u0010\u009c\u0002\u001a\u00020\u00172\u0007\u0010¤\u0001\u001a\u00020=H\u0002J\u0014\u0010\u009d\u0002\u001a\u00030\u009e\u00022\b\u0010\u009f\u0002\u001a\u00030 \u0002H\u0002J\u0014\u0010¡\u0002\u001a\u00030¢\u00022\b\u0010£\u0002\u001a\u00030¤\u0002H\u0002J\u0012\u0010¥\u0002\u001a\u0002032\u0007\u0010¤\u0001\u001a\u00020=H\u0002J\u0014\u0010¦\u0002\u001a\u00030§\u00022\b\u0010¨\u0002\u001a\u00030©\u0002H\u0002J\u0014\u0010ª\u0002\u001a\u00030\u008c\u00012\b\u0010«\u0002\u001a\u00030¬\u0002H\u0002J\u0014\u0010\u00ad\u0002\u001a\u00030®\u00022\b\u0010¨\u0002\u001a\u00030¯\u0002H\u0002J\u0014\u0010°\u0002\u001a\u00030±\u00022\b\u0010\u008d\u0002\u001a\u00030²\u0002H\u0002J\u0013\u0010³\u0002\u001a\u00030´\u00022\u0007\u0010[\u001a\u00030µ\u0002H\u0002J\u0012\u0010¶\u0002\u001a\u00030·\u00022\u0006\u0010h\u001a\u00020\u001cH\u0002J\u0017\u0010¸\u0002\u001a\u00030¹\u00022\u000b\u0010s\u001a\u0007\u0012\u0002\b\u00030º\u0002H\u0002J\u001b\u0010»\u0002\u001a\u00030¼\u00022\u000f\u0010Þ\u0001\u001a\n\u0012\u0005\u0012\u00030à\u00010½\u0002H\u0002J\u0013\u0010¾\u0002\u001a\u00020\u00172\b\u0010¿\u0002\u001a\u00030À\u0002H\u0002J\u001d\u0010Á\u0002\u001a\u00030\u008c\u00012\b\u0010¿\u0002\u001a\u00030À\u00022\u0007\u0010¤\u0001\u001a\u00020=H\u0002J\u0014\u0010Â\u0002\u001a\u00030Ã\u00022\b\u0010Ä\u0002\u001a\u00030Å\u0002H\u0002J\u0013\u0010Æ\u0002\u001a\u00030Ç\u00022\u0007\u0010[\u001a\u00030È\u0002H\u0002J\u0014\u0010É\u0002\u001a\u00030Ê\u00022\b\u0010É\u0001\u001a\u00030Ë\u0002H\u0002J\u0013\u0010Ì\u0002\u001a\u00030Í\u00022\u0007\u0010[\u001a\u00030Î\u0002H\u0002J\u001c\u0010Ï\u0002\u001a\u00020L2\b\u0010Ð\u0002\u001a\u00030Ñ\u00022\u0007\u0010h\u001a\u00030Ò\u0002H\u0002J\u0012\u0010Ó\u0002\u001a\u00020\u00172\u0007\u0010[\u001a\u00030Ô\u0002H\u0002J\u0013\u0010Õ\u0002\u001a\u00030Ö\u00022\u0007\u0010h\u001a\u00030×\u0002H\u0002J\u0013\u0010Ø\u0002\u001a\u00030Ù\u00022\u0007\u0010h\u001a\u00030Ú\u0002H\u0002J\u0014\u0010Û\u0002\u001a\u00030Ü\u00022\b\u0010¤\u0001\u001a\u00030à\u0001H\u0002J\u0014\u0010Ý\u0002\u001a\u00030Þ\u00022\b\u0010ß\u0002\u001a\u00030à\u0002H\u0002J\u0014\u0010á\u0002\u001a\u00030â\u00022\b\u0010\u0096\u0002\u001a\u00030\u0097\u0002H\u0002J\u0012\u0010ã\u0002\u001a\u00020\u00172\u0007\u0010\u0085\u0001\u001a\u00020\u0014H\u0002J\u0013\u0010ä\u0002\u001a\u00030å\u00022\u0007\u0010h\u001a\u00030æ\u0002H\u0002J\u001d\u0010ç\u0002\u001a\r é\u0002*\u0005\u0018\u00010è\u00020è\u00022\u0007\u0010h\u001a\u00030ê\u0002H\u0002J\u0013\u0010ë\u0002\u001a\u00030ì\u00022\u0007\u0010h\u001a\u00030í\u0002H\u0002J\u0013\u0010î\u0002\u001a\u00030ï\u00022\u0007\u0010h\u001a\u00030ð\u0002H\u0002J\u0013\u0010ñ\u0002\u001a\u00030\u008c\u00012\u0007\u0010«\u0002\u001a\u000209H\u0002J\u0013\u0010ò\u0002\u001a\u00030ó\u00022\u0007\u0010h\u001a\u00030ô\u0002H\u0002J\u0014\u0010õ\u0002\u001a\u00030ö\u00022\b\u0010\u009d\u0001\u001a\u00030÷\u0002H\u0002J\u0013\u0010ø\u0002\u001a\u00030ù\u00022\u0007\u0010h\u001a\u00030ú\u0002H\u0002J\u0014\u0010û\u0002\u001a\u00030ü\u00022\b\u0010ß\u0002\u001a\u00030ý\u0002H\u0002J\u0013\u0010þ\u0002\u001a\u00030ÿ\u00022\u0007\u0010h\u001a\u00030\u0080\u0003H\u0002J\u0013\u0010\u0081\u0003\u001a\u00030\u0082\u00032\u0007\u0010h\u001a\u00030\u0083\u0003H\u0002J\u0014\u0010\u0084\u0003\u001a\u0004\u0018\u00010\u00142\u0007\u0010\u0085\u0003\u001a\u00020\u0014H\u0002J\u000e\u0010\u0086\u0003\u001a\u00020\u0014*\u00030½\u0001H\u0002R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u001a\u001a\u001e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00170\u001bj\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u0017`\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u000e\u0010\u0011\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R$\u0010%\u001a\u0012\u0012\u0004\u0012\u00020\u00140&j\b\u0012\u0004\u0012\u00020\u0014`'X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R$\u0010*\u001a\u0012\u0012\u0004\u0012\u00020+0&j\b\u0012\u0004\u0012\u00020+`'X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010)R\u001a\u0010-\u001a\u000e\u0012\u0004\u0012\u00020.\u0012\u0004\u0012\u00020\u00170\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010/\u001a\u0012\u0012\u0004\u0012\u00020\u00140&j\b\u0012\u0004\u0012\u00020\u0014`'X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u0010)R*\u00101\u001a\u001e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00170\u001bj\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u0017`\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R$\u00102\u001a\u0012\u0012\u0004\u0012\u0002030&j\b\u0012\u0004\u0012\u000203`'X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b4\u0010)R*\u00105\u001a\u001e\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020\u00170\u001bj\u000e\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020\u0017`\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u00107\u001a\u000208*\u0002098BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b:\u0010;R\u0018\u0010<\u001a\u000206*\u00020=8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b>\u0010?¨\u0006\u008e\u0003"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer;", "", "messageLogger", "Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "declarationTable", "Lorg/jetbrains/kotlin/backend/common/serialization/DeclarationTable;", "expectDescriptorToSymbol", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "compatibilityMode", "Lorg/jetbrains/kotlin/backend/common/serialization/CompatibilityMode;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "bodiesOnlyForInlines", "", "skipExpects", "normalizeAbsolutePaths", "sourceBaseDirs", "", "", "(Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;Lorg/jetbrains/kotlin/backend/common/serialization/DeclarationTable;Ljava/util/Map;Lorg/jetbrains/kotlin/backend/common/serialization/CompatibilityMode;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;ZZZLjava/util/Collection;)V", "currentLoopIndex", "", "expectActualTable", "Lorg/jetbrains/kotlin/backend/common/serialization/ExpectActualTable;", "loopIndex", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "Lkotlin/collections/HashMap;", "getMessageLogger", "()Lorg/jetbrains/kotlin/ir/util/IrMessageLogger;", "protoBodyArray", "", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$XStatementOrExpression;", "getProtoBodyArray", "()Ljava/util/List;", "protoDebugInfoArray", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "getProtoDebugInfoArray", "()Ljava/util/ArrayList;", "protoIdSignatureArray", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IdSignature;", "getProtoIdSignatureArray", "protoIdSignatureMap", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "protoStringArray", "getProtoStringArray", "protoStringMap", "protoTypeArray", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrType;", "getProtoTypeArray", "protoTypeMap", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$IrTypeKey;", "toIrTypeArgumentKey", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$IrTypeArgumentKey;", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "getToIrTypeArgumentKey", "(Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;)Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$IrTypeArgumentKey;", "toIrTypeKey", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getToIrTypeKey", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$IrTypeKey;", "backendSpecificExplicitRoot", "node", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "backendSpecificExplicitRootExclusion", "backendSpecificMetadata", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$FileBackendSpecificMetadata;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "backendSpecificSerializeAllMembers", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "fillPlatformExplicitlyExported", "", "file", "proto", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrFile$Builder;", "keepOrderOfProperties", "property", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "memberNeedsSerialization", "member", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "protoIdSignature", "declaration", "idSig", "serializeAccessorSignature", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/AccessorIdSignature;", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$AccessorSignature;", "serializeAnnotations", "", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrConstructorCall;", "annotations", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "serializeBlock", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrBlock;", "block", "Lorg/jetbrains/kotlin/ir/expressions/IrBlock;", "serializeBlockBody", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrBlockBody;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrBlockBody;", "serializeBranch", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrBranch;", ConfigConstants.CONFIG_BRANCH_SECTION, "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "serializeBreak", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrBreak;", "Lorg/jetbrains/kotlin/ir/expressions/IrBreak;", "serializeCall", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrCall;", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "serializeCatch", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrCatch;", PsiKeyword.CATCH, "Lorg/jetbrains/kotlin/ir/expressions/IrCatch;", "serializeClassReference", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrClassReference;", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "serializeComposite", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrComposite;", "composite", "Lorg/jetbrains/kotlin/ir/expressions/IrComposite;", "serializeCompositeSignature", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/CompositeSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CompositeSignature;", "serializeConst", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrConst;", "value", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "serializeConstructorCall", "serializeContinue", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrContinue;", "Lorg/jetbrains/kotlin/ir/expressions/IrContinue;", "serializeCoordinates", "", "start", "end", "serializeDebugInfo", "serializeDeclaration", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDeclaration;", "serializeDelegatingConstructorCall", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDelegatingConstructorCall;", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "serializeDoWhile", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDoWhile;", "Lorg/jetbrains/kotlin/ir/expressions/IrDoWhileLoop;", "serializeDynamicMemberExpression", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDynamicMemberExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicMemberExpression;", "serializeDynamicOperator", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDynamicOperatorExpression$IrDynamicOperator;", "operator", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperator;", "serializeDynamicOperatorExpression", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDynamicOperatorExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrDynamicOperatorExpression;", "serializeDynamicType", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDynamicType;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrDynamicType;", "serializeEnumConstructorCall", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrEnumConstructorCall;", "Lorg/jetbrains/kotlin/ir/expressions/IrEnumConstructorCall;", "serializeErrorCallExpression", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrErrorCallExpression;", "callExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorCallExpression;", "serializeErrorExpression", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrErrorExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrErrorExpression;", "serializeErrorType", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrErrorType;", "Lorg/jetbrains/kotlin/ir/types/IrErrorType;", "serializeExpectActualSubstitutionTable", "serializeExpression", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrExpression;", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "serializeFieldAccessCommon", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/FieldAccessCommon;", "Lorg/jetbrains/kotlin/ir/expressions/IrFieldAccessExpression;", "serializeFileEntry", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/FileEntry;", "entry", "Lorg/jetbrains/kotlin/ir/IrFileEntry;", "serializeFileSignature", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/FileSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;", "serializeFqName", "fqName", "serializeFunctionExpression", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrFunctionExpression;", "functionExpression", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionExpression;", "serializeFunctionReference", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrFunctionReference;", "callable", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "serializeGetClass", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrGetClass;", "Lorg/jetbrains/kotlin/ir/expressions/IrGetClass;", "serializeGetEnumValue", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrGetEnumValue;", "Lorg/jetbrains/kotlin/ir/expressions/IrGetEnumValue;", "serializeGetField", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrGetField;", "Lorg/jetbrains/kotlin/ir/expressions/IrGetField;", "serializeGetObject", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrGetObject;", "Lorg/jetbrains/kotlin/ir/expressions/IrGetObjectValue;", "serializeGetValue", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrGetValue;", "Lorg/jetbrains/kotlin/ir/expressions/IrGetValue;", "serializeIdSignature", "idSignature", "serializeInlineClassRepresentation", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrInlineClassRepresentation;", "representation", "Lorg/jetbrains/kotlin/descriptors/InlineClassRepresentation;", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "serializeInstanceInitializerCall", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrInstanceInitializerCall;", "Lorg/jetbrains/kotlin/ir/expressions/IrInstanceInitializerCall;", "serializeIrAnonymousInit", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrAnonymousInit;", "Lorg/jetbrains/kotlin/ir/declarations/IrAnonymousInitializer;", "serializeIrClass", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrClass;", "clazz", "serializeIrConstructor", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrConstructor;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "serializeIrDeclarationBase", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDeclarationBase;", OverlayablePolicy.NAME_flags, "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Ljava/lang/Long;)Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDeclarationBase;", "serializeIrDeclarationOrigin", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "serializeIrEnumEntry", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrEnumEntry;", "enumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "serializeIrErrorDeclaration", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrErrorDeclaration;", "errorDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrErrorDeclaration;", "serializeIrExpressionBody", "serializeIrField", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrField;", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "serializeIrFile", "Lorg/jetbrains/kotlin/library/SerializedIrFile;", "serializeIrFileImpl", "serializeIrFunction", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrFunction;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "serializeIrFunctionBase", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrFunctionBase;", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "serializeIrLocalDelegatedProperty", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrLocalDelegatedProperty;", "variable", "Lorg/jetbrains/kotlin/ir/declarations/IrLocalDelegatedProperty;", "serializeIrLocalDelegatedPropertyReference", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrLocalDelegatedPropertyReference;", "Lorg/jetbrains/kotlin/ir/expressions/IrLocalDelegatedPropertyReference;", "serializeIrProperty", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrProperty;", "serializeIrStarProjection", "serializeIrStatementBody", "statement", "Lorg/jetbrains/kotlin/ir/IrElement;", "serializeIrStatementOrigin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "serializeIrSymbol", NativeSymbol.TYPE_NAME, "serializeIrType", "serializeIrTypeAbbreviation", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrTypeAbbreviation;", "typeAbbreviation", "Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "serializeIrTypeAlias", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrTypeAlias;", "typeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "serializeIrTypeData", "serializeIrTypeParameter", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrTypeParameter;", "parameter", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "serializeIrTypeProjection", "argument", "Lorg/jetbrains/kotlin/ir/types/IrTypeProjection;", "serializeIrValueParameter", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "serializeIrVariable", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrVariable;", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "serializeLocalSignature", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/LocalSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$LocalSignature;", "serializeLoop", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/Loop;", "serializeMemberAccessCommon", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/MemberAccessCommon;", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "serializeMultiFieldValueClassRepresentation", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrMultiFieldValueClassRepresentation;", "Lorg/jetbrains/kotlin/descriptors/MultiFieldValueClassRepresentation;", "serializeName", "name", "Lorg/jetbrains/kotlin/name/Name;", "serializeNameAndType", "serializeNullability", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrSimpleTypeNullability;", "nullability", "Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;", "serializePrivateSignature", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/FileLocalIdSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$FileLocalSignature;", "serializePropertyReference", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrPropertyReference;", "Lorg/jetbrains/kotlin/ir/expressions/IrPropertyReference;", "serializePublicSignature", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/CommonIdSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CommonSignature;", "serializeReturn", "operation", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrOperation$Builder;", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "serializeScopeLocalSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$ScopeLocalDeclaration;", "serializeSetField", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrSetField;", "Lorg/jetbrains/kotlin/ir/expressions/IrSetField;", "serializeSetValue", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrSetValue;", "Lorg/jetbrains/kotlin/ir/expressions/IrSetValue;", "serializeSimpleType", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrSimpleType;", "serializeSpreadElement", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrSpreadElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/expressions/IrSpreadElement;", "serializeStatement", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrStatement;", "serializeString", "serializeStringConcat", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrStringConcat;", "Lorg/jetbrains/kotlin/ir/expressions/IrStringConcatenation;", "serializeSyntheticBody", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrSyntheticBody;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/kotlin/ir/expressions/IrSyntheticBody;", "serializeThrow", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrThrow;", "Lorg/jetbrains/kotlin/ir/expressions/IrThrow;", "serializeTry", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrTry;", "Lorg/jetbrains/kotlin/ir/expressions/IrTry;", "serializeTypeArgument", "serializeTypeOp", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrTypeOp;", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "serializeTypeOperator", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrTypeOperator;", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperator;", "serializeVararg", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrVararg;", "Lorg/jetbrains/kotlin/ir/expressions/IrVararg;", "serializeVarargElement", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrVarargElement;", "Lorg/jetbrains/kotlin/ir/expressions/IrVarargElement;", "serializeWhen", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrWhen;", "Lorg/jetbrains/kotlin/ir/expressions/IrWhen;", "serializeWhile", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrWhile;", "Lorg/jetbrains/kotlin/ir/expressions/IrWhileLoop;", "tryMatchPath", PsiTreeChangeEvent.PROP_FILE_NAME, "matchAndNormalizeFilePath", "Companion", "FileBackendSpecificMetadata", "IrTypeArgumentKey", "IrTypeArgumentKind", "IrTypeKey", "IrTypeKind", "XStatementOrExpression", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrFileSerializer {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final boolean bodiesOnlyForInlines;
    private final CompatibilityMode compatibilityMode;
    private int currentLoopIndex;
    private final DeclarationTable declarationTable;
    private final ExpectActualTable expectActualTable;
    private final Map<DeclarationDescriptor, IrSymbol> expectDescriptorToSymbol;
    private final LanguageVersionSettings languageVersionSettings;
    private final HashMap<IrLoop, Integer> loopIndex;
    private final IrMessageLogger messageLogger;
    private final boolean normalizeAbsolutePaths;
    private final List<XStatementOrExpression> protoBodyArray;
    private final ArrayList<String> protoDebugInfoArray;
    private final ArrayList<IdSignature> protoIdSignatureArray;
    private final Map<org.jetbrains.kotlin.ir.util.IdSignature, Integer> protoIdSignatureMap;
    private final ArrayList<String> protoStringArray;
    private final HashMap<String, Integer> protoStringMap;
    private final ArrayList<IrType> protoTypeArray;
    private final HashMap<IrTypeKey, Integer> protoTypeMap;
    private final boolean skipExpects;
    private final Collection<String> sourceBaseDirs;

    /* JADX INFO: compiled from: IrFileSerializer.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0004À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$FileBackendSpecificMetadata;", "", "toByteArray", "", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface FileBackendSpecificMetadata {
        byte[] toByteArray();
    }

    /* JADX INFO: compiled from: IrFileSerializer.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$IrTypeArgumentKind;", "", "(Ljava/lang/String;I)V", "STAR", "PROJECTION", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum IrTypeArgumentKind {
        STAR,
        PROJECTION;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<IrTypeArgumentKind> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: IrFileSerializer.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$IrTypeKind;", "", "(Ljava/lang/String;I)V", "SIMPLE", "DYNAMIC", MediaError.ERROR_TYPE_ERROR, "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum IrTypeKind {
        SIMPLE,
        DYNAMIC,
        ERROR;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<IrTypeKind> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: IrFileSerializer.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;
        public static final /* synthetic */ int[] $EnumSwitchMapping$3;

        static {
            int[] iArr = new int[SimpleTypeNullability.values().length];
            try {
                iArr[SimpleTypeNullability.MARKED_NULLABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SimpleTypeNullability.NOT_SPECIFIED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SimpleTypeNullability.DEFINITELY_NOT_NULL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[IrSyntheticBodyKind.values().length];
            try {
                iArr2[IrSyntheticBodyKind.ENUM_VALUES.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[IrSyntheticBodyKind.ENUM_VALUEOF.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[IrSyntheticBodyKind.ENUM_ENTRIES.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[IrTypeOperator.values().length];
            try {
                iArr3[IrTypeOperator.CAST.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr3[IrTypeOperator.IMPLICIT_CAST.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr3[IrTypeOperator.IMPLICIT_NOTNULL.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr3[IrTypeOperator.IMPLICIT_COERCION_TO_UNIT.ordinal()] = 4;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr3[IrTypeOperator.IMPLICIT_INTEGER_COERCION.ordinal()] = 5;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr3[IrTypeOperator.SAFE_CAST.ordinal()] = 6;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                iArr3[IrTypeOperator.INSTANCEOF.ordinal()] = 7;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                iArr3[IrTypeOperator.NOT_INSTANCEOF.ordinal()] = 8;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                iArr3[IrTypeOperator.SAM_CONVERSION.ordinal()] = 9;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                iArr3[IrTypeOperator.IMPLICIT_DYNAMIC_CAST.ordinal()] = 10;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                iArr3[IrTypeOperator.REINTERPRET_CAST.ordinal()] = 11;
            } catch (NoSuchFieldError unused17) {
            }
            $EnumSwitchMapping$2 = iArr3;
            int[] iArr4 = new int[IrDynamicOperator.values().length];
            try {
                iArr4[IrDynamicOperator.UNARY_PLUS.ordinal()] = 1;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                iArr4[IrDynamicOperator.UNARY_MINUS.ordinal()] = 2;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                iArr4[IrDynamicOperator.EXCL.ordinal()] = 3;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                iArr4[IrDynamicOperator.PREFIX_INCREMENT.ordinal()] = 4;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                iArr4[IrDynamicOperator.PREFIX_DECREMENT.ordinal()] = 5;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                iArr4[IrDynamicOperator.POSTFIX_INCREMENT.ordinal()] = 6;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                iArr4[IrDynamicOperator.POSTFIX_DECREMENT.ordinal()] = 7;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                iArr4[IrDynamicOperator.BINARY_PLUS.ordinal()] = 8;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                iArr4[IrDynamicOperator.BINARY_MINUS.ordinal()] = 9;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                iArr4[IrDynamicOperator.MUL.ordinal()] = 10;
            } catch (NoSuchFieldError unused27) {
            }
            try {
                iArr4[IrDynamicOperator.DIV.ordinal()] = 11;
            } catch (NoSuchFieldError unused28) {
            }
            try {
                iArr4[IrDynamicOperator.MOD.ordinal()] = 12;
            } catch (NoSuchFieldError unused29) {
            }
            try {
                iArr4[IrDynamicOperator.GT.ordinal()] = 13;
            } catch (NoSuchFieldError unused30) {
            }
            try {
                iArr4[IrDynamicOperator.LT.ordinal()] = 14;
            } catch (NoSuchFieldError unused31) {
            }
            try {
                iArr4[IrDynamicOperator.GE.ordinal()] = 15;
            } catch (NoSuchFieldError unused32) {
            }
            try {
                iArr4[IrDynamicOperator.LE.ordinal()] = 16;
            } catch (NoSuchFieldError unused33) {
            }
            try {
                iArr4[IrDynamicOperator.EQEQ.ordinal()] = 17;
            } catch (NoSuchFieldError unused34) {
            }
            try {
                iArr4[IrDynamicOperator.EXCLEQ.ordinal()] = 18;
            } catch (NoSuchFieldError unused35) {
            }
            try {
                iArr4[IrDynamicOperator.EQEQEQ.ordinal()] = 19;
            } catch (NoSuchFieldError unused36) {
            }
            try {
                iArr4[IrDynamicOperator.EXCLEQEQ.ordinal()] = 20;
            } catch (NoSuchFieldError unused37) {
            }
            try {
                iArr4[IrDynamicOperator.ANDAND.ordinal()] = 21;
            } catch (NoSuchFieldError unused38) {
            }
            try {
                iArr4[IrDynamicOperator.OROR.ordinal()] = 22;
            } catch (NoSuchFieldError unused39) {
            }
            try {
                iArr4[IrDynamicOperator.EQ.ordinal()] = 23;
            } catch (NoSuchFieldError unused40) {
            }
            try {
                iArr4[IrDynamicOperator.PLUSEQ.ordinal()] = 24;
            } catch (NoSuchFieldError unused41) {
            }
            try {
                iArr4[IrDynamicOperator.MINUSEQ.ordinal()] = 25;
            } catch (NoSuchFieldError unused42) {
            }
            try {
                iArr4[IrDynamicOperator.MULEQ.ordinal()] = 26;
            } catch (NoSuchFieldError unused43) {
            }
            try {
                iArr4[IrDynamicOperator.DIVEQ.ordinal()] = 27;
            } catch (NoSuchFieldError unused44) {
            }
            try {
                iArr4[IrDynamicOperator.MODEQ.ordinal()] = 28;
            } catch (NoSuchFieldError unused45) {
            }
            try {
                iArr4[IrDynamicOperator.ARRAY_ACCESS.ordinal()] = 29;
            } catch (NoSuchFieldError unused46) {
            }
            try {
                iArr4[IrDynamicOperator.INVOKE.ordinal()] = 30;
            } catch (NoSuchFieldError unused47) {
            }
            $EnumSwitchMapping$3 = iArr4;
        }
    }

    private final long serializeIrStarProjection() {
        return 0L;
    }

    private final int serializeIrStatementOrigin(IrStatementOrigin origin) {
        String debugName;
        IrStatementOriginImpl irStatementOriginImpl = origin instanceof IrStatementOriginImpl ? (IrStatementOriginImpl) origin : null;
        if (irStatementOriginImpl == null || (debugName = irStatementOriginImpl.getDebugName()) == null) {
            throw new IllegalStateException(("Unable to serialize origin " + origin.getClass().getName()).toString());
        }
        return serializeString(debugName);
    }

    public boolean backendSpecificExplicitRoot(IrAnnotationContainer node) {
        Intrinsics.checkNotNullParameter(node, "node");
        return false;
    }

    public boolean backendSpecificExplicitRootExclusion(IrAnnotationContainer node) {
        Intrinsics.checkNotNullParameter(node, "node");
        return false;
    }

    public FileBackendSpecificMetadata backendSpecificMetadata(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        return null;
    }

    public boolean backendSpecificSerializeAllMembers(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        return false;
    }

    public IrFileSerializer(IrMessageLogger messageLogger, DeclarationTable declarationTable, Map<DeclarationDescriptor, IrSymbol> expectDescriptorToSymbol, CompatibilityMode compatibilityMode, LanguageVersionSettings languageVersionSettings, boolean z, boolean z2, boolean z3, Collection<String> sourceBaseDirs) {
        Intrinsics.checkNotNullParameter(messageLogger, "messageLogger");
        Intrinsics.checkNotNullParameter(declarationTable, "declarationTable");
        Intrinsics.checkNotNullParameter(expectDescriptorToSymbol, "expectDescriptorToSymbol");
        Intrinsics.checkNotNullParameter(compatibilityMode, "compatibilityMode");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(sourceBaseDirs, "sourceBaseDirs");
        this.messageLogger = messageLogger;
        this.declarationTable = declarationTable;
        this.expectDescriptorToSymbol = expectDescriptorToSymbol;
        this.compatibilityMode = compatibilityMode;
        this.languageVersionSettings = languageVersionSettings;
        this.bodiesOnlyForInlines = z;
        this.skipExpects = z2;
        this.normalizeAbsolutePaths = z3;
        this.sourceBaseDirs = sourceBaseDirs;
        this.loopIndex = new HashMap<>();
        this.expectActualTable = new ExpectActualTable(expectDescriptorToSymbol);
        this.protoTypeMap = new HashMap<>();
        this.protoTypeArray = new ArrayList<>();
        this.protoStringMap = new HashMap<>();
        this.protoStringArray = new ArrayList<>();
        this.protoIdSignatureMap = new LinkedHashMap();
        this.protoIdSignatureArray = new ArrayList<>();
        this.protoBodyArray = new ArrayList();
        this.protoDebugInfoArray = new ArrayList<>();
    }

    public /* synthetic */ IrFileSerializer(IrMessageLogger irMessageLogger, DeclarationTable declarationTable, Map map, CompatibilityMode compatibilityMode, LanguageVersionSettings languageVersionSettings, boolean z, boolean z2, boolean z3, Collection collection, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irMessageLogger, declarationTable, map, compatibilityMode, languageVersionSettings, (i & 32) != 0 ? false : z, (i & 64) != 0 ? false : z2, (i & 128) != 0 ? false : z3, collection);
    }

    public final IrMessageLogger getMessageLogger() {
        return this.messageLogger;
    }

    protected final ArrayList<IrType> getProtoTypeArray() {
        return this.protoTypeArray;
    }

    protected final ArrayList<String> getProtoStringArray() {
        return this.protoStringArray;
    }

    protected final ArrayList<IdSignature> getProtoIdSignatureArray() {
        return this.protoIdSignatureArray;
    }

    protected final List<XStatementOrExpression> getProtoBodyArray() {
        return this.protoBodyArray;
    }

    protected final ArrayList<String> getProtoDebugInfoArray() {
        return this.protoDebugInfoArray;
    }

    /* JADX INFO: compiled from: IrFileSerializer.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\t\nB\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\bH\u0016\u0082\u0001\u0002\u000b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$XStatementOrExpression;", "", "()V", "toByteArray", "", "toProtoExpression", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrExpression;", "toProtoStatement", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrStatement;", "XExpression", "XStatement", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$XStatementOrExpression$XExpression;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$XStatementOrExpression$XStatement;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class XStatementOrExpression {
        public /* synthetic */ XStatementOrExpression(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract byte[] toByteArray();

        private XStatementOrExpression() {
        }

        /* JADX INFO: renamed from: toProtoStatement */
        public IrStatement getProto() {
            throw new IllegalStateException("It is not a ProtoStatement".toString());
        }

        /* JADX INFO: renamed from: toProtoExpression */
        public IrExpression getProto() {
            throw new IllegalStateException("It is not a ProtoExpression".toString());
        }

        /* JADX INFO: compiled from: IrFileSerializer.kt */
        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$XStatementOrExpression$XStatement;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$XStatementOrExpression;", "proto", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrStatement;", "(Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrStatement;)V", "toByteArray", "", "toProtoStatement", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class XStatement extends XStatementOrExpression {
            private final IrStatement proto;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public XStatement(IrStatement proto) {
                super(null);
                Intrinsics.checkNotNullParameter(proto, "proto");
                this.proto = proto;
            }

            @Override // org.jetbrains.kotlin.backend.common.serialization.IrFileSerializer.XStatementOrExpression
            public byte[] toByteArray() {
                byte[] byteArray = this.proto.toByteArray();
                Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
                return byteArray;
            }

            @Override // org.jetbrains.kotlin.backend.common.serialization.IrFileSerializer.XStatementOrExpression
            /* JADX INFO: renamed from: toProtoStatement, reason: from getter */
            public IrStatement getProto() {
                return this.proto;
            }
        }

        /* JADX INFO: compiled from: IrFileSerializer.kt */
        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0003H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$XStatementOrExpression$XExpression;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$XStatementOrExpression;", "proto", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrExpression;", "(Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrExpression;)V", "toByteArray", "", "toProtoExpression", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class XExpression extends XStatementOrExpression {
            private final IrExpression proto;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public XExpression(IrExpression proto) {
                super(null);
                Intrinsics.checkNotNullParameter(proto, "proto");
                this.proto = proto;
            }

            @Override // org.jetbrains.kotlin.backend.common.serialization.IrFileSerializer.XStatementOrExpression
            public byte[] toByteArray() {
                byte[] byteArray = this.proto.toByteArray();
                Intrinsics.checkNotNullExpressionValue(byteArray, "toByteArray(...)");
                return byteArray;
            }

            @Override // org.jetbrains.kotlin.backend.common.serialization.IrFileSerializer.XStatementOrExpression
            /* JADX INFO: renamed from: toProtoExpression, reason: from getter */
            public IrExpression getProto() {
                return this.proto;
            }
        }
    }

    private final int serializeIrExpressionBody(org.jetbrains.kotlin.ir.expressions.IrExpression expression) {
        this.protoBodyArray.add(new XStatementOrExpression.XExpression(serializeExpression(expression)));
        return this.protoBodyArray.size() - 1;
    }

    private final int serializeIrStatementBody(IrElement statement) {
        this.protoBodyArray.add(new XStatementOrExpression.XStatement(serializeStatement(statement)));
        return this.protoBodyArray.size() - 1;
    }

    private final int serializeIrDeclarationOrigin(IrDeclarationOrigin origin) {
        return serializeString(origin.getName());
    }

    private final long serializeCoordinates(int start, int end) {
        return BinaryCoordinates.INSTANCE.encode(start, end);
    }

    private final int serializeString(String value) {
        HashMap<String, Integer> map = this.protoStringMap;
        Integer numValueOf = map.get(value);
        if (numValueOf == null) {
            this.protoStringArray.add(value);
            numValueOf = Integer.valueOf(this.protoStringArray.size() - 1);
            map.put(value, numValueOf);
        }
        return numValueOf.intValue();
    }

    private final int serializeDebugInfo(String value) {
        this.protoDebugInfoArray.add(value);
        return this.protoDebugInfoArray.size() - 1;
    }

    private final int serializeName(Name name2) {
        String string = name2.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return serializeString(string);
    }

    private final CommonIdSignature serializePublicSignature(org.jetbrains.kotlin.ir.util.IdSignature.CommonSignature signature) {
        CommonIdSignature.Builder builderNewBuilder = CommonIdSignature.newBuilder();
        builderNewBuilder.addAllPackageFqName(serializeFqName(signature.getPackageFqName()));
        builderNewBuilder.addAllDeclarationFqName(serializeFqName(signature.getDeclarationFqName()));
        Long id2 = signature.getId();
        if (id2 != null) {
            builderNewBuilder.setMemberUniqId(id2.longValue());
        }
        if (signature.getMask() != 0) {
            builderNewBuilder.setFlags(signature.getMask());
        }
        String description = signature.getDescription();
        if (description != null) {
            builderNewBuilder.setDebugInfo(serializeDebugInfo(description));
        }
        CommonIdSignature commonIdSignatureBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(commonIdSignatureBuild, "build(...)");
        return commonIdSignatureBuild;
    }

    private final AccessorIdSignature serializeAccessorSignature(org.jetbrains.kotlin.ir.util.IdSignature.AccessorSignature signature) {
        AccessorIdSignature.Builder builderNewBuilder = AccessorIdSignature.newBuilder();
        builderNewBuilder.setPropertySignature(protoIdSignature(signature.getPropertySignature()));
        org.jetbrains.kotlin.ir.util.IdSignature.CommonSignature accessorSignature = signature.getAccessorSignature();
        builderNewBuilder.setName(serializeString(accessorSignature.getShortName()));
        Long id2 = accessorSignature.getId();
        if (id2 == null) {
            throw new IllegalStateException("Expected hash Id".toString());
        }
        builderNewBuilder.setAccessorHashId(id2.longValue());
        if (accessorSignature.getMask() != 0) {
            builderNewBuilder.setFlags(accessorSignature.getMask());
        }
        String description = accessorSignature.getDescription();
        if (description != null) {
            builderNewBuilder.setDebugInfo(serializeDebugInfo(description));
        }
        AccessorIdSignature accessorIdSignatureBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(accessorIdSignatureBuild, "build(...)");
        return accessorIdSignatureBuild;
    }

    private final FileLocalIdSignature serializePrivateSignature(org.jetbrains.kotlin.ir.util.IdSignature.FileLocalSignature signature) {
        FileLocalIdSignature.Builder builderNewBuilder = FileLocalIdSignature.newBuilder();
        builderNewBuilder.setContainer(protoIdSignature(signature.getContainer()));
        builderNewBuilder.setLocalId(signature.getId());
        String description = signature.getDescription();
        if (description != null) {
            builderNewBuilder.setDebugInfo(serializeDebugInfo(description));
        }
        FileLocalIdSignature fileLocalIdSignatureBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(fileLocalIdSignatureBuild, "build(...)");
        return fileLocalIdSignatureBuild;
    }

    private final int serializeScopeLocalSignature(org.jetbrains.kotlin.ir.util.IdSignature.ScopeLocalDeclaration signature) {
        return signature.getId();
    }

    private final CompositeSignature serializeCompositeSignature(org.jetbrains.kotlin.ir.util.IdSignature.CompositeSignature signature) {
        CompositeSignature.Builder builderNewBuilder = CompositeSignature.newBuilder();
        builderNewBuilder.setContainerSig(protoIdSignature(signature.getContainer()));
        builderNewBuilder.setInnerSig(protoIdSignature(signature.getInner()));
        CompositeSignature compositeSignatureBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(compositeSignatureBuild, "build(...)");
        return compositeSignatureBuild;
    }

    private final FileSignature serializeFileSignature(org.jetbrains.kotlin.ir.util.IdSignature.FileSignature signature) {
        FileSignature defaultInstance = FileSignature.getDefaultInstance();
        Intrinsics.checkNotNullExpressionValue(defaultInstance, "getDefaultInstance(...)");
        return defaultInstance;
    }

    private final LocalSignature serializeLocalSignature(org.jetbrains.kotlin.ir.util.IdSignature.LocalSignature signature) {
        LocalSignature.Builder builderNewBuilder = LocalSignature.newBuilder();
        builderNewBuilder.addAllLocalFqName(serializeFqName(signature.getLocalFqn()));
        Long hashSig = signature.getHashSig();
        if (hashSig != null) {
            builderNewBuilder.setLocalHash(hashSig.longValue());
        }
        String description = signature.getDescription();
        if (description != null) {
            builderNewBuilder.setDebugInfo(serializeDebugInfo(description));
        }
        LocalSignature localSignatureBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(localSignatureBuild, "build(...)");
        return localSignatureBuild;
    }

    private final IdSignature serializeIdSignature(org.jetbrains.kotlin.ir.util.IdSignature idSignature) {
        IdSignature.Builder builderNewBuilder = IdSignature.newBuilder();
        if (idSignature instanceof org.jetbrains.kotlin.ir.util.IdSignature.CommonSignature) {
            builderNewBuilder.setPublicSig(serializePublicSignature((org.jetbrains.kotlin.ir.util.IdSignature.CommonSignature) idSignature));
        } else if (idSignature instanceof org.jetbrains.kotlin.ir.util.IdSignature.AccessorSignature) {
            builderNewBuilder.setAccessorSig(serializeAccessorSignature((org.jetbrains.kotlin.ir.util.IdSignature.AccessorSignature) idSignature));
        } else if (idSignature instanceof org.jetbrains.kotlin.ir.util.IdSignature.FileLocalSignature) {
            builderNewBuilder.setPrivateSig(serializePrivateSignature((org.jetbrains.kotlin.ir.util.IdSignature.FileLocalSignature) idSignature));
        } else if (idSignature instanceof org.jetbrains.kotlin.ir.util.IdSignature.ScopeLocalDeclaration) {
            builderNewBuilder.setScopedLocalSig(serializeScopeLocalSignature((org.jetbrains.kotlin.ir.util.IdSignature.ScopeLocalDeclaration) idSignature));
        } else if (idSignature instanceof org.jetbrains.kotlin.ir.util.IdSignature.CompositeSignature) {
            builderNewBuilder.setCompositeSig(serializeCompositeSignature((org.jetbrains.kotlin.ir.util.IdSignature.CompositeSignature) idSignature));
        } else if (idSignature instanceof org.jetbrains.kotlin.ir.util.IdSignature.LocalSignature) {
            builderNewBuilder.setLocalSig(serializeLocalSignature((org.jetbrains.kotlin.ir.util.IdSignature.LocalSignature) idSignature));
        } else if (idSignature instanceof org.jetbrains.kotlin.ir.util.IdSignature.FileSignature) {
            builderNewBuilder.setFileSig(serializeFileSignature((org.jetbrains.kotlin.ir.util.IdSignature.FileSignature) idSignature));
        } else if (!(idSignature instanceof org.jetbrains.kotlin.ir.util.IdSignature.SpecialFakeOverrideSignature) && (idSignature instanceof org.jetbrains.kotlin.ir.util.IdSignature.LoweredDeclarationSignature)) {
            throw new IllegalStateException("LoweredDeclarationSignature is not expected here".toString());
        }
        IdSignature idSignatureBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(idSignatureBuild, "build(...)");
        return idSignatureBuild;
    }

    private final int protoIdSignature(IrDeclaration declaration) {
        return protoIdSignature(this.declarationTable.signatureByDeclaration(declaration, this.compatibilityMode.getOldSignatures()));
    }

    private final int protoIdSignature(org.jetbrains.kotlin.ir.util.IdSignature idSig) {
        Map<org.jetbrains.kotlin.ir.util.IdSignature, Integer> map = this.protoIdSignatureMap;
        Integer numValueOf = map.get(idSig);
        if (numValueOf == null) {
            this.protoIdSignatureArray.add(serializeIdSignature(idSig));
            numValueOf = Integer.valueOf(this.protoIdSignatureArray.size() - 1);
            map.put(idSig, numValueOf);
        }
        return numValueOf.intValue();
    }

    /* JADX INFO: compiled from: IrFileSerializer.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$Companion;", "", "()V", "protoSymbolKind", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final BinarySymbolData.SymbolKind protoSymbolKind(IrSymbol symbol) {
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            if (symbol instanceof IrAnonymousInitializerSymbol) {
                return BinarySymbolData.SymbolKind.ANONYMOUS_INIT_SYMBOL;
            }
            if (symbol instanceof IrClassSymbol) {
                return BinarySymbolData.SymbolKind.CLASS_SYMBOL;
            }
            if (symbol instanceof IrConstructorSymbol) {
                return BinarySymbolData.SymbolKind.CONSTRUCTOR_SYMBOL;
            }
            if (symbol instanceof IrTypeParameterSymbol) {
                return BinarySymbolData.SymbolKind.TYPE_PARAMETER_SYMBOL;
            }
            if (symbol instanceof IrEnumEntrySymbol) {
                return BinarySymbolData.SymbolKind.ENUM_ENTRY_SYMBOL;
            }
            if (symbol instanceof IrVariableSymbol) {
                return BinarySymbolData.SymbolKind.VARIABLE_SYMBOL;
            }
            if (symbol instanceof IrValueParameterSymbol) {
                if (((IrValueParameterSymbol) symbol).getDescriptor() instanceof ReceiverParameterDescriptor) {
                    return BinarySymbolData.SymbolKind.RECEIVER_PARAMETER_SYMBOL;
                }
                return BinarySymbolData.SymbolKind.VALUE_PARAMETER_SYMBOL;
            }
            if (symbol instanceof IrSimpleFunctionSymbol) {
                return BinarySymbolData.SymbolKind.FUNCTION_SYMBOL;
            }
            if (symbol instanceof IrReturnableBlockSymbol) {
                return BinarySymbolData.SymbolKind.RETURNABLE_BLOCK_SYMBOL;
            }
            if (symbol instanceof IrFieldSymbol) {
                IrPropertySymbol correspondingPropertySymbol = ((IrFieldSymbol) symbol).getOwner().getCorrespondingPropertySymbol();
                IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
                if (owner == null || owner.getIsDelegated() || (owner.getGetter() == null && owner.getSetter() == null)) {
                    return BinarySymbolData.SymbolKind.STANDALONE_FIELD_SYMBOL;
                }
                return BinarySymbolData.SymbolKind.FIELD_SYMBOL;
            }
            if (symbol instanceof IrPropertySymbol) {
                return BinarySymbolData.SymbolKind.PROPERTY_SYMBOL;
            }
            if (symbol instanceof IrLocalDelegatedPropertySymbol) {
                return BinarySymbolData.SymbolKind.LOCAL_DELEGATED_PROPERTY_SYMBOL;
            }
            if (symbol instanceof IrTypeAliasSymbol) {
                return BinarySymbolData.SymbolKind.TYPEALIAS_SYMBOL;
            }
            if (symbol instanceof IrFileSymbol) {
                return BinarySymbolData.SymbolKind.FILE_SYMBOL;
            }
            throw new NotImplementedError("An operation is not implemented: " + ("Unexpected symbol kind: " + symbol));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final long serializeIrSymbol(IrSymbol symbol) {
        int iProtoIdSignature;
        BinarySymbolData.SymbolKind symbolKindProtoSymbolKind = INSTANCE.protoSymbolKind(symbol);
        if (symbol instanceof IrFileSymbol) {
            iProtoIdSignature = protoIdSignature(new org.jetbrains.kotlin.ir.util.IdSignature.FileSignature((IrFileSymbol) symbol));
        } else {
            IrSymbolOwner owner = symbol.getOwner();
            IrDeclaration irDeclaration = owner instanceof IrDeclaration ? (IrDeclaration) owner : null;
            if (irDeclaration == null) {
                throw new IllegalStateException(("Expected IrDeclaration: " + RenderIrElementKt.render$default(symbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            iProtoIdSignature = protoIdSignature(irDeclaration);
        }
        return BinarySymbolData.INSTANCE.encode(symbolKindProtoSymbolKind, iProtoIdSignature);
    }

    private final List<IrConstructorCall> serializeAnnotations(List<? extends org.jetbrains.kotlin.ir.expressions.IrConstructorCall> annotations) {
        List<? extends org.jetbrains.kotlin.ir.expressions.IrConstructorCall> list = annotations;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(serializeConstructorCall((org.jetbrains.kotlin.ir.expressions.IrConstructorCall) it.next()));
        }
        return arrayList;
    }

    private final List<Integer> serializeFqName(String fqName) {
        List listSplit$default = StringsKt.split$default((CharSequence) fqName, new String[]{"."}, false, 0, 6, (Object) null);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSplit$default, 10));
        Iterator it = listSplit$default.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(serializeString((String) it.next())));
        }
        return arrayList;
    }

    private final long serializeIrTypeProjection(IrTypeProjection argument) {
        return BinaryTypeProjection.INSTANCE.encodeType(argument.getVariance(), serializeIrType(argument.getType()));
    }

    private final long serializeTypeArgument(IrTypeArgument argument) {
        if (argument instanceof IrStarProjection) {
            return serializeIrStarProjection();
        }
        if (argument instanceof IrTypeProjection) {
            return serializeIrTypeProjection((IrTypeProjection) argument);
        }
        throw new NoWhenBranchMatchedException();
    }

    private final IrSimpleTypeNullability serializeNullability(SimpleTypeNullability nullability) {
        int i = WhenMappings.$EnumSwitchMapping$0[nullability.ordinal()];
        if (i == 1) {
            return IrSimpleTypeNullability.MARKED_NULLABLE;
        }
        if (i == 2) {
            return IrSimpleTypeNullability.NOT_SPECIFIED;
        }
        if (i == 3) {
            return IrSimpleTypeNullability.DEFINITELY_NOT_NULL;
        }
        throw new NoWhenBranchMatchedException();
    }

    private final IrSimpleType serializeSimpleType(org.jetbrains.kotlin.ir.types.IrSimpleType type) {
        IrSimpleType.Builder classifier = IrSimpleType.newBuilder().addAllAnnotation(serializeAnnotations(type.getAnnotations())).setClassifier(serializeIrSymbol(type.getClassifier()));
        if (type.getNullability() != SimpleTypeNullability.NOT_SPECIFIED) {
            classifier.setNullability(serializeNullability(type.getNullability()));
        }
        IrTypeAbbreviation abbreviation = type.getAbbreviation();
        if (abbreviation != null) {
            classifier.setAbbreviation(serializeIrTypeAbbreviation(abbreviation));
        }
        Iterator<T> it = type.getArguments().iterator();
        while (it.hasNext()) {
            classifier.addArgument(serializeTypeArgument((IrTypeArgument) it.next()));
        }
        IrSimpleType irSimpleTypeBuild = classifier.build();
        Intrinsics.checkNotNullExpressionValue(irSimpleTypeBuild, "build(...)");
        return irSimpleTypeBuild;
    }

    private final org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAbbreviation serializeIrTypeAbbreviation(IrTypeAbbreviation typeAbbreviation) {
        org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAbbreviation.Builder hasQuestionMark = org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAbbreviation.newBuilder().addAllAnnotation(serializeAnnotations(typeAbbreviation.getAnnotations())).setTypeAlias(serializeIrSymbol(typeAbbreviation.getTypeAlias())).setHasQuestionMark(typeAbbreviation.getHasQuestionMark());
        Iterator<T> it = typeAbbreviation.getArguments().iterator();
        while (it.hasNext()) {
            hasQuestionMark.addArgument(serializeTypeArgument((IrTypeArgument) it.next()));
        }
        org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeAbbreviation irTypeAbbreviationBuild = hasQuestionMark.build();
        Intrinsics.checkNotNullExpressionValue(irTypeAbbreviationBuild, "build(...)");
        return irTypeAbbreviationBuild;
    }

    private final IrDynamicType serializeDynamicType(org.jetbrains.kotlin.ir.types.IrDynamicType type) {
        IrDynamicType irDynamicTypeBuild = IrDynamicType.newBuilder().addAllAnnotation(serializeAnnotations(type.getAnnotations())).build();
        Intrinsics.checkNotNullExpressionValue(irDynamicTypeBuild, "build(...)");
        return irDynamicTypeBuild;
    }

    private final IrErrorType serializeErrorType(org.jetbrains.kotlin.ir.types.IrErrorType type) {
        IrErrorType irErrorTypeBuild = IrErrorType.newBuilder().addAllAnnotation(serializeAnnotations(type.getAnnotations())).build();
        Intrinsics.checkNotNullExpressionValue(irErrorTypeBuild, "build(...)");
        return irErrorTypeBuild;
    }

    private final IrType serializeIrTypeData(org.jetbrains.kotlin.ir.types.IrType type) {
        IrType.Builder builderNewBuilder = IrType.newBuilder();
        if (type instanceof org.jetbrains.kotlin.ir.types.IrSimpleType) {
            builderNewBuilder.setSimple(serializeSimpleType((org.jetbrains.kotlin.ir.types.IrSimpleType) type));
        } else if (type instanceof org.jetbrains.kotlin.ir.types.IrDynamicType) {
            builderNewBuilder.setDynamic(serializeDynamicType((org.jetbrains.kotlin.ir.types.IrDynamicType) type));
        } else if (type instanceof org.jetbrains.kotlin.ir.types.IrErrorType) {
            builderNewBuilder.setError(serializeErrorType((org.jetbrains.kotlin.ir.types.IrErrorType) type));
        } else {
            throw new NotImplementedError("An operation is not implemented: " + ("IrType serialization not implemented yet: " + type + '.'));
        }
        IrType irTypeBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irTypeBuild, "build(...)");
        return irTypeBuild;
    }

    /* JADX INFO: compiled from: IrFileSerializer.kt */
    @Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001BI\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u000e\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\u0002\u0010\u000fJ\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0007HÆ\u0003J\u0011\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\tHÆ\u0003J\u000f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\f0\tHÆ\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u000eHÆ\u0003JY\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eHÆ\u0001J\u0013\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010%\u001a\u00020&HÖ\u0001J\t\u0010'\u001a\u00020(HÖ\u0001R\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0019\u0010\b\u001a\n\u0012\u0004\u0012\u00020\n\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$IrTypeKey;", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$IrTypeKind;", "classifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "nullability", "Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;", "arguments", "", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$IrTypeArgumentKey;", "annotations", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "abbreviation", "Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "(Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$IrTypeKind;Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;)V", "getAbbreviation", "()Lorg/jetbrains/kotlin/ir/types/IrTypeAbbreviation;", "getAnnotations", "()Ljava/util/List;", "getArguments", "getClassifier", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "getKind", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$IrTypeKind;", "getNullability", "()Lorg/jetbrains/kotlin/ir/types/SimpleTypeNullability;", "component1", "component2", "component3", "component4", "component5", "component6", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class IrTypeKey {
        private final IrTypeAbbreviation abbreviation;
        private final List<org.jetbrains.kotlin.ir.expressions.IrConstructorCall> annotations;
        private final List<IrTypeArgumentKey> arguments;
        private final IrClassifierSymbol classifier;
        private final IrTypeKind kind;
        private final SimpleTypeNullability nullability;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ IrTypeKey copy$default(IrTypeKey irTypeKey, IrTypeKind irTypeKind, IrClassifierSymbol irClassifierSymbol, SimpleTypeNullability simpleTypeNullability, List list, List list2, IrTypeAbbreviation irTypeAbbreviation, int i, Object obj) {
            if ((i & 1) != 0) {
                irTypeKind = irTypeKey.kind;
            }
            if ((i & 2) != 0) {
                irClassifierSymbol = irTypeKey.classifier;
            }
            IrClassifierSymbol irClassifierSymbol2 = irClassifierSymbol;
            if ((i & 4) != 0) {
                simpleTypeNullability = irTypeKey.nullability;
            }
            SimpleTypeNullability simpleTypeNullability2 = simpleTypeNullability;
            if ((i & 8) != 0) {
                list = irTypeKey.arguments;
            }
            List list3 = list;
            if ((i & 16) != 0) {
                list2 = irTypeKey.annotations;
            }
            List list4 = list2;
            if ((i & 32) != 0) {
                irTypeAbbreviation = irTypeKey.abbreviation;
            }
            return irTypeKey.copy(irTypeKind, irClassifierSymbol2, simpleTypeNullability2, list3, list4, irTypeAbbreviation);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrTypeKind getKind() {
            return this.kind;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final IrClassifierSymbol getClassifier() {
            return this.classifier;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final SimpleTypeNullability getNullability() {
            return this.nullability;
        }

        public final List<IrTypeArgumentKey> component4() {
            return this.arguments;
        }

        public final List<org.jetbrains.kotlin.ir.expressions.IrConstructorCall> component5() {
            return this.annotations;
        }

        /* JADX INFO: renamed from: component6, reason: from getter */
        public final IrTypeAbbreviation getAbbreviation() {
            return this.abbreviation;
        }

        public final IrTypeKey copy(IrTypeKind kind, IrClassifierSymbol classifier, SimpleTypeNullability nullability, List<IrTypeArgumentKey> arguments, List<? extends org.jetbrains.kotlin.ir.expressions.IrConstructorCall> annotations, IrTypeAbbreviation abbreviation) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            return new IrTypeKey(kind, classifier, nullability, arguments, annotations, abbreviation);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof IrTypeKey)) {
                return false;
            }
            IrTypeKey irTypeKey = (IrTypeKey) other;
            return this.kind == irTypeKey.kind && Intrinsics.areEqual(this.classifier, irTypeKey.classifier) && this.nullability == irTypeKey.nullability && Intrinsics.areEqual(this.arguments, irTypeKey.arguments) && Intrinsics.areEqual(this.annotations, irTypeKey.annotations) && Intrinsics.areEqual(this.abbreviation, irTypeKey.abbreviation);
        }

        public int hashCode() {
            int iHashCode = this.kind.hashCode() * 31;
            IrClassifierSymbol irClassifierSymbol = this.classifier;
            int iHashCode2 = (iHashCode + (irClassifierSymbol == null ? 0 : irClassifierSymbol.hashCode())) * 31;
            SimpleTypeNullability simpleTypeNullability = this.nullability;
            int iHashCode3 = (iHashCode2 + (simpleTypeNullability == null ? 0 : simpleTypeNullability.hashCode())) * 31;
            List<IrTypeArgumentKey> list = this.arguments;
            int iHashCode4 = (((iHashCode3 + (list == null ? 0 : list.hashCode())) * 31) + this.annotations.hashCode()) * 31;
            IrTypeAbbreviation irTypeAbbreviation = this.abbreviation;
            return iHashCode4 + (irTypeAbbreviation != null ? irTypeAbbreviation.hashCode() : 0);
        }

        public String toString() {
            return "IrTypeKey(kind=" + this.kind + ", classifier=" + this.classifier + ", nullability=" + this.nullability + ", arguments=" + this.arguments + ", annotations=" + this.annotations + ", abbreviation=" + this.abbreviation + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public IrTypeKey(IrTypeKind kind, IrClassifierSymbol irClassifierSymbol, SimpleTypeNullability simpleTypeNullability, List<IrTypeArgumentKey> list, List<? extends org.jetbrains.kotlin.ir.expressions.IrConstructorCall> annotations, IrTypeAbbreviation irTypeAbbreviation) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            Intrinsics.checkNotNullParameter(annotations, "annotations");
            this.kind = kind;
            this.classifier = irClassifierSymbol;
            this.nullability = simpleTypeNullability;
            this.arguments = list;
            this.annotations = annotations;
            this.abbreviation = irTypeAbbreviation;
        }

        public final IrTypeKind getKind() {
            return this.kind;
        }

        public final IrClassifierSymbol getClassifier() {
            return this.classifier;
        }

        public final SimpleTypeNullability getNullability() {
            return this.nullability;
        }

        public final List<IrTypeArgumentKey> getArguments() {
            return this.arguments;
        }

        public final List<org.jetbrains.kotlin.ir.expressions.IrConstructorCall> getAnnotations() {
            return this.annotations;
        }

        public final IrTypeAbbreviation getAbbreviation() {
            return this.abbreviation;
        }
    }

    /* JADX INFO: compiled from: IrFileSerializer.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0007HÆ\u0003J+\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$IrTypeArgumentKey;", "", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$IrTypeArgumentKind;", "variance", "Lorg/jetbrains/kotlin/types/Variance;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$IrTypeKey;", "(Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$IrTypeArgumentKind;Lorg/jetbrains/kotlin/types/Variance;Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$IrTypeKey;)V", "getKind", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$IrTypeArgumentKind;", "getType", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrFileSerializer$IrTypeKey;", "getVariance", "()Lorg/jetbrains/kotlin/types/Variance;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class IrTypeArgumentKey {
        private final IrTypeArgumentKind kind;
        private final IrTypeKey type;
        private final Variance variance;

        public static /* synthetic */ IrTypeArgumentKey copy$default(IrTypeArgumentKey irTypeArgumentKey, IrTypeArgumentKind irTypeArgumentKind, Variance variance, IrTypeKey irTypeKey, int i, Object obj) {
            if ((i & 1) != 0) {
                irTypeArgumentKind = irTypeArgumentKey.kind;
            }
            if ((i & 2) != 0) {
                variance = irTypeArgumentKey.variance;
            }
            if ((i & 4) != 0) {
                irTypeKey = irTypeArgumentKey.type;
            }
            return irTypeArgumentKey.copy(irTypeArgumentKind, variance, irTypeKey);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IrTypeArgumentKind getKind() {
            return this.kind;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Variance getVariance() {
            return this.variance;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final IrTypeKey getType() {
            return this.type;
        }

        public final IrTypeArgumentKey copy(IrTypeArgumentKind kind, Variance variance, IrTypeKey type) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            return new IrTypeArgumentKey(kind, variance, type);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof IrTypeArgumentKey)) {
                return false;
            }
            IrTypeArgumentKey irTypeArgumentKey = (IrTypeArgumentKey) other;
            return this.kind == irTypeArgumentKey.kind && this.variance == irTypeArgumentKey.variance && Intrinsics.areEqual(this.type, irTypeArgumentKey.type);
        }

        public int hashCode() {
            int iHashCode = this.kind.hashCode() * 31;
            Variance variance = this.variance;
            int iHashCode2 = (iHashCode + (variance == null ? 0 : variance.hashCode())) * 31;
            IrTypeKey irTypeKey = this.type;
            return iHashCode2 + (irTypeKey != null ? irTypeKey.hashCode() : 0);
        }

        public String toString() {
            return "IrTypeArgumentKey(kind=" + this.kind + ", variance=" + this.variance + ", type=" + this.type + ')';
        }

        public IrTypeArgumentKey(IrTypeArgumentKind kind, Variance variance, IrTypeKey irTypeKey) {
            Intrinsics.checkNotNullParameter(kind, "kind");
            this.kind = kind;
            this.variance = variance;
            this.type = irTypeKey;
        }

        public final IrTypeArgumentKind getKind() {
            return this.kind;
        }

        public final Variance getVariance() {
            return this.variance;
        }

        public final IrTypeKey getType() {
            return this.type;
        }
    }

    private final IrTypeKey getToIrTypeKey(org.jetbrains.kotlin.ir.types.IrType irType) {
        IrTypeKind irTypeKind;
        ArrayList arrayList;
        List<IrTypeArgument> arguments;
        boolean z = irType instanceof org.jetbrains.kotlin.ir.types.IrSimpleType;
        if (z) {
            irTypeKind = IrTypeKind.SIMPLE;
        } else if (irType instanceof org.jetbrains.kotlin.ir.types.IrDynamicType) {
            irTypeKind = IrTypeKind.DYNAMIC;
        } else {
            if (!(irType instanceof org.jetbrains.kotlin.ir.types.IrErrorType)) {
                throw new IllegalStateException(("Unexpected IrType kind: " + irType).toString());
            }
            irTypeKind = IrTypeKind.ERROR;
        }
        IrTypeKind irTypeKind2 = irTypeKind;
        IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
        org.jetbrains.kotlin.ir.types.IrSimpleType irSimpleType = z ? (org.jetbrains.kotlin.ir.types.IrSimpleType) irType : null;
        SimpleTypeNullability nullability = irSimpleType != null ? irSimpleType.getNullability() : null;
        org.jetbrains.kotlin.ir.types.IrSimpleType irSimpleType2 = z ? (org.jetbrains.kotlin.ir.types.IrSimpleType) irType : null;
        if (irSimpleType2 == null || (arguments = irSimpleType2.getArguments()) == null) {
            arrayList = null;
        } else {
            List<IrTypeArgument> list = arguments;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList2.add(getToIrTypeArgumentKey((IrTypeArgument) it.next()));
            }
            arrayList = arrayList2;
        }
        List<org.jetbrains.kotlin.ir.expressions.IrConstructorCall> annotations = irType.getAnnotations();
        org.jetbrains.kotlin.ir.types.IrSimpleType irSimpleType3 = z ? (org.jetbrains.kotlin.ir.types.IrSimpleType) irType : null;
        return new IrTypeKey(irTypeKind2, classifierOrNull, nullability, arrayList, annotations, irSimpleType3 != null ? irSimpleType3.getAbbreviation() : null);
    }

    private final IrTypeArgumentKey getToIrTypeArgumentKey(IrTypeArgument irTypeArgument) {
        IrTypeArgumentKind irTypeArgumentKind;
        org.jetbrains.kotlin.ir.types.IrType type;
        if (irTypeArgument instanceof IrStarProjection) {
            irTypeArgumentKind = IrTypeArgumentKind.STAR;
        } else {
            if (!(irTypeArgument instanceof IrTypeProjection)) {
                throw new NoWhenBranchMatchedException();
            }
            irTypeArgumentKind = IrTypeArgumentKind.PROJECTION;
        }
        boolean z = irTypeArgument instanceof IrTypeProjection;
        IrTypeKey toIrTypeKey = null;
        IrTypeProjection irTypeProjection = z ? (IrTypeProjection) irTypeArgument : null;
        Variance variance = irTypeProjection != null ? irTypeProjection.getVariance() : null;
        IrTypeProjection irTypeProjection2 = z ? (IrTypeProjection) irTypeArgument : null;
        if (irTypeProjection2 != null && (type = irTypeProjection2.getType()) != null) {
            toIrTypeKey = getToIrTypeKey(type);
        }
        return new IrTypeArgumentKey(irTypeArgumentKind, variance, toIrTypeKey);
    }

    private final int serializeIrType(org.jetbrains.kotlin.ir.types.IrType type) {
        HashMap<IrTypeKey, Integer> map = this.protoTypeMap;
        IrTypeKey toIrTypeKey = getToIrTypeKey(type);
        Integer numValueOf = map.get(toIrTypeKey);
        if (numValueOf == null) {
            this.protoTypeArray.add(serializeIrTypeData(type));
            numValueOf = Integer.valueOf(this.protoTypeArray.size() - 1);
            map.put(toIrTypeKey, numValueOf);
        }
        return numValueOf.intValue();
    }

    private final IrBlockBody serializeBlockBody(org.jetbrains.kotlin.ir.expressions.IrBlockBody expression) {
        IrBlockBody.Builder builderNewBuilder = IrBlockBody.newBuilder();
        Iterator<T> it = expression.getStatements().iterator();
        while (it.hasNext()) {
            builderNewBuilder.addStatement(serializeStatement((org.jetbrains.kotlin.ir.IrStatement) it.next()));
        }
        IrBlockBody irBlockBodyBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irBlockBodyBuild, "build(...)");
        return irBlockBodyBuild;
    }

    private final IrBranch serializeBranch(org.jetbrains.kotlin.ir.expressions.IrBranch branch) {
        IrBranch.Builder builderNewBuilder = IrBranch.newBuilder();
        builderNewBuilder.setCondition(serializeExpression(branch.getCondition()));
        builderNewBuilder.setResult(serializeExpression(branch.getResult()));
        IrBranch irBranchBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irBranchBuild, "build(...)");
        return irBranchBuild;
    }

    private final IrBlock serializeBlock(org.jetbrains.kotlin.ir.expressions.IrBlock block) {
        IrBlock.Builder builderNewBuilder = IrBlock.newBuilder();
        IrStatementOrigin origin = block.getOrigin();
        if (origin != null) {
            builderNewBuilder.setOriginName(serializeIrStatementOrigin(origin));
        }
        Iterator<T> it = block.getStatements().iterator();
        while (it.hasNext()) {
            builderNewBuilder.addStatement(serializeStatement((org.jetbrains.kotlin.ir.IrStatement) it.next()));
        }
        IrBlock irBlockBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irBlockBuild, "build(...)");
        return irBlockBuild;
    }

    private final IrComposite serializeComposite(org.jetbrains.kotlin.ir.expressions.IrComposite composite) {
        IrComposite.Builder builderNewBuilder = IrComposite.newBuilder();
        IrStatementOrigin origin = composite.getOrigin();
        if (origin != null) {
            builderNewBuilder.setOriginName(serializeIrStatementOrigin(origin));
        }
        Iterator<T> it = composite.getStatements().iterator();
        while (it.hasNext()) {
            builderNewBuilder.addStatement(serializeStatement((org.jetbrains.kotlin.ir.IrStatement) it.next()));
        }
        IrComposite irCompositeBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irCompositeBuild, "build(...)");
        return irCompositeBuild;
    }

    private final IrCatch serializeCatch(org.jetbrains.kotlin.ir.expressions.IrCatch irCatch) {
        IrCatch irCatchBuild = IrCatch.newBuilder().setCatchParameter(serializeIrVariable(irCatch.getCatchParameter())).setResult(serializeExpression(irCatch.getResult())).build();
        Intrinsics.checkNotNullExpressionValue(irCatchBuild, "build(...)");
        return irCatchBuild;
    }

    private final IrStringConcat serializeStringConcat(IrStringConcatenation expression) {
        IrStringConcat.Builder builderNewBuilder = IrStringConcat.newBuilder();
        Iterator<T> it = expression.getArguments().iterator();
        while (it.hasNext()) {
            builderNewBuilder.addArgument(serializeExpression((org.jetbrains.kotlin.ir.expressions.IrExpression) it.next()));
        }
        IrStringConcat irStringConcatBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irStringConcatBuild, "build(...)");
        return irStringConcatBuild;
    }

    private final MemberAccessCommon serializeMemberAccessCommon(IrMemberAccessExpression<?> call) {
        MemberAccessCommon.Builder builderNewBuilder = MemberAccessCommon.newBuilder();
        if (call.getExtensionReceiver() != null) {
            org.jetbrains.kotlin.ir.expressions.IrExpression extensionReceiver = call.getExtensionReceiver();
            Intrinsics.checkNotNull(extensionReceiver);
            builderNewBuilder.setExtensionReceiver(serializeExpression(extensionReceiver));
        }
        if (call.getDispatchReceiver() != null) {
            org.jetbrains.kotlin.ir.expressions.IrExpression dispatchReceiver = call.getDispatchReceiver();
            Intrinsics.checkNotNull(dispatchReceiver);
            builderNewBuilder.setDispatchReceiver(serializeExpression(dispatchReceiver));
        }
        int typeArgumentsCount = call.getTypeArgumentsCount();
        for (int i = 0; i < typeArgumentsCount; i++) {
            org.jetbrains.kotlin.ir.types.IrType typeArgument = call.getTypeArgument(i);
            builderNewBuilder.addTypeArgument(typeArgument != null ? serializeIrType(typeArgument) : -1);
        }
        int valueArgumentsCount = call.getValueArgumentsCount();
        for (int i2 = 0; i2 < valueArgumentsCount; i2++) {
            org.jetbrains.kotlin.ir.expressions.IrExpression valueArgument = call.getValueArgument(i2);
            NullableIrExpression.Builder builderNewBuilder2 = NullableIrExpression.newBuilder();
            if (valueArgument != null) {
                builderNewBuilder2.setExpression(serializeExpression(valueArgument));
            }
            builderNewBuilder.addValueArgument(builderNewBuilder2);
        }
        MemberAccessCommon memberAccessCommonBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(memberAccessCommonBuild, "build(...)");
        return memberAccessCommonBuild;
    }

    private final IrCall serializeCall(org.jetbrains.kotlin.ir.expressions.IrCall call) {
        IrCall.Builder builderNewBuilder = IrCall.newBuilder();
        builderNewBuilder.setSymbol(serializeIrSymbol(call.getSymbol()));
        IrStatementOrigin origin = call.getOrigin();
        if (origin != null) {
            builderNewBuilder.setOriginName(serializeIrStatementOrigin(origin));
        }
        IrClassSymbol superQualifierSymbol = call.getSuperQualifierSymbol();
        if (superQualifierSymbol != null) {
            builderNewBuilder.setSuper(serializeIrSymbol(superQualifierSymbol));
        }
        builderNewBuilder.setMemberAccess(serializeMemberAccessCommon(call));
        IrCall irCallBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irCallBuild, "build(...)");
        return irCallBuild;
    }

    private final IrConstructorCall serializeConstructorCall(org.jetbrains.kotlin.ir.expressions.IrConstructorCall call) {
        IrConstructorCall.Builder builderNewBuilder = IrConstructorCall.newBuilder();
        builderNewBuilder.setSymbol(serializeIrSymbol(call.getSymbol()));
        builderNewBuilder.setConstructorTypeArgumentsCount(call.getConstructorTypeArgumentsCount());
        builderNewBuilder.setMemberAccess(serializeMemberAccessCommon(call));
        IrStatementOrigin origin = call.getOrigin();
        if (origin != null) {
            builderNewBuilder.setOriginName(serializeIrStatementOrigin(origin));
        }
        IrConstructorCall irConstructorCallBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irConstructorCallBuild, "build(...)");
        return irConstructorCallBuild;
    }

    private final IrFunctionExpression serializeFunctionExpression(org.jetbrains.kotlin.ir.expressions.IrFunctionExpression functionExpression) {
        IrFunctionExpression.Builder builderNewBuilder = IrFunctionExpression.newBuilder();
        builderNewBuilder.setFunction(serializeIrFunction(functionExpression.getFunction()));
        builderNewBuilder.setOriginName(serializeIrStatementOrigin(functionExpression.getOrigin()));
        IrFunctionExpression irFunctionExpressionBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irFunctionExpressionBuild, "build(...)");
        return irFunctionExpressionBuild;
    }

    private final IrFunctionReference serializeFunctionReference(org.jetbrains.kotlin.ir.expressions.IrFunctionReference callable) {
        IrFunctionReference.Builder memberAccess = IrFunctionReference.newBuilder().setSymbol(serializeIrSymbol(callable.getSymbol())).setMemberAccess(serializeMemberAccessCommon(callable));
        IrFunctionSymbol reflectionTarget = callable.getReflectionTarget();
        if (reflectionTarget != null) {
            memberAccess.setReflectionTargetSymbol(serializeIrSymbol(reflectionTarget));
        }
        IrStatementOrigin origin = callable.getOrigin();
        if (origin != null) {
            memberAccess.setOriginName(serializeIrStatementOrigin(origin));
        }
        IrFunctionReference irFunctionReferenceBuild = memberAccess.build();
        Intrinsics.checkNotNullExpressionValue(irFunctionReferenceBuild, "build(...)");
        return irFunctionReferenceBuild;
    }

    private final IrLocalDelegatedPropertyReference serializeIrLocalDelegatedPropertyReference(org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference callable) {
        IrLocalDelegatedPropertyReference.Builder symbol = IrLocalDelegatedPropertyReference.newBuilder().setDelegate(serializeIrSymbol(callable.getDelegate())).setGetter(serializeIrSymbol(callable.getGetter())).setSymbol(serializeIrSymbol(callable.getSymbol()));
        IrStatementOrigin origin = callable.getOrigin();
        if (origin != null) {
            symbol.setOriginName(serializeIrStatementOrigin(origin));
        }
        IrSimpleFunctionSymbol setter = callable.getSetter();
        if (setter != null) {
            symbol.setSetter(serializeIrSymbol(setter));
        }
        IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReferenceBuild = symbol.build();
        Intrinsics.checkNotNullExpressionValue(irLocalDelegatedPropertyReferenceBuild, "build(...)");
        return irLocalDelegatedPropertyReferenceBuild;
    }

    private final IrPropertyReference serializePropertyReference(org.jetbrains.kotlin.ir.expressions.IrPropertyReference callable) {
        IrPropertyReference.Builder symbol = IrPropertyReference.newBuilder().setMemberAccess(serializeMemberAccessCommon(callable)).setSymbol(serializeIrSymbol(callable.getSymbol()));
        IrStatementOrigin origin = callable.getOrigin();
        if (origin != null) {
            symbol.setOriginName(serializeIrStatementOrigin(origin));
        }
        IrFieldSymbol field = callable.getField();
        if (field != null) {
            symbol.setField(serializeIrSymbol(field));
        }
        IrSimpleFunctionSymbol getter = callable.getGetter();
        if (getter != null) {
            symbol.setGetter(serializeIrSymbol(getter));
        }
        IrSimpleFunctionSymbol setter = callable.getSetter();
        if (setter != null) {
            symbol.setSetter(serializeIrSymbol(setter));
        }
        IrPropertyReference irPropertyReferenceBuild = symbol.build();
        Intrinsics.checkNotNullExpressionValue(irPropertyReferenceBuild, "build(...)");
        return irPropertyReferenceBuild;
    }

    private final IrClassReference serializeClassReference(org.jetbrains.kotlin.ir.expressions.IrClassReference expression) {
        IrClassReference irClassReferenceBuild = IrClassReference.newBuilder().setClassSymbol(serializeIrSymbol(expression.getSymbol())).setClassType(serializeIrType(expression.getClassType())).build();
        Intrinsics.checkNotNullExpressionValue(irClassReferenceBuild, "build(...)");
        return irClassReferenceBuild;
    }

    private final IrConst serializeConst(org.jetbrains.kotlin.ir.expressions.IrConst<?> value) {
        IrConst.Builder builderNewBuilder = IrConst.newBuilder();
        IrConstKind<?> kind = value.getKind();
        if (Intrinsics.areEqual(kind, IrConstKind.Null.INSTANCE)) {
            builderNewBuilder.setNull(true);
        } else if (Intrinsics.areEqual(kind, IrConstKind.Boolean.INSTANCE)) {
            Object value2 = value.getValue();
            Intrinsics.checkNotNull(value2, "null cannot be cast to non-null type kotlin.Boolean");
            builderNewBuilder.setBoolean(((Boolean) value2).booleanValue());
        } else if (Intrinsics.areEqual(kind, IrConstKind.Byte.INSTANCE)) {
            Object value3 = value.getValue();
            Intrinsics.checkNotNull(value3, "null cannot be cast to non-null type kotlin.Byte");
            builderNewBuilder.setByte(((Byte) value3).byteValue());
        } else if (Intrinsics.areEqual(kind, IrConstKind.Char.INSTANCE)) {
            Object value4 = value.getValue();
            Intrinsics.checkNotNull(value4, "null cannot be cast to non-null type kotlin.Char");
            builderNewBuilder.setChar(((Character) value4).charValue());
        } else if (Intrinsics.areEqual(kind, IrConstKind.Short.INSTANCE)) {
            Object value5 = value.getValue();
            Intrinsics.checkNotNull(value5, "null cannot be cast to non-null type kotlin.Short");
            builderNewBuilder.setShort(((Short) value5).shortValue());
        } else if (Intrinsics.areEqual(kind, IrConstKind.Int.INSTANCE)) {
            Object value6 = value.getValue();
            Intrinsics.checkNotNull(value6, "null cannot be cast to non-null type kotlin.Int");
            builderNewBuilder.setInt(((Integer) value6).intValue());
        } else if (Intrinsics.areEqual(kind, IrConstKind.Long.INSTANCE)) {
            Object value7 = value.getValue();
            Intrinsics.checkNotNull(value7, "null cannot be cast to non-null type kotlin.Long");
            builderNewBuilder.setLong(((Long) value7).longValue());
        } else if (Intrinsics.areEqual(kind, IrConstKind.String.INSTANCE)) {
            Object value8 = value.getValue();
            Intrinsics.checkNotNull(value8, "null cannot be cast to non-null type kotlin.String");
            builderNewBuilder.setString(serializeString((String) value8));
        } else if (Intrinsics.areEqual(kind, IrConstKind.Float.INSTANCE)) {
            Object value9 = value.getValue();
            Intrinsics.checkNotNull(value9, "null cannot be cast to non-null type kotlin.Float");
            builderNewBuilder.setFloatBits(Float.floatToIntBits(((Float) value9).floatValue()));
        } else if (Intrinsics.areEqual(kind, IrConstKind.Double.INSTANCE)) {
            Object value10 = value.getValue();
            Intrinsics.checkNotNull(value10, "null cannot be cast to non-null type kotlin.Double");
            builderNewBuilder.setDoubleBits(Double.doubleToLongBits(((Double) value10).doubleValue()));
        }
        IrConst irConstBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irConstBuild, "build(...)");
        return irConstBuild;
    }

    private final IrDelegatingConstructorCall serializeDelegatingConstructorCall(org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall call) {
        IrDelegatingConstructorCall irDelegatingConstructorCallBuild = IrDelegatingConstructorCall.newBuilder().setSymbol(serializeIrSymbol(call.getSymbol())).setMemberAccess(serializeMemberAccessCommon(call)).build();
        Intrinsics.checkNotNullExpressionValue(irDelegatingConstructorCallBuild, "build(...)");
        return irDelegatingConstructorCallBuild;
    }

    private final IrDoWhile serializeDoWhile(IrDoWhileLoop expression) {
        IrDoWhile irDoWhileBuild = IrDoWhile.newBuilder().setLoop(serializeLoop(expression)).build();
        Intrinsics.checkNotNullExpressionValue(irDoWhileBuild, "build(...)");
        return irDoWhileBuild;
    }

    private final IrEnumConstructorCall serializeEnumConstructorCall(org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall call) {
        IrEnumConstructorCall irEnumConstructorCallBuild = IrEnumConstructorCall.newBuilder().setSymbol(serializeIrSymbol(call.getSymbol())).setMemberAccess(serializeMemberAccessCommon(call)).build();
        Intrinsics.checkNotNullExpressionValue(irEnumConstructorCallBuild, "build(...)");
        return irEnumConstructorCallBuild;
    }

    private final IrGetClass serializeGetClass(org.jetbrains.kotlin.ir.expressions.IrGetClass expression) {
        IrGetClass irGetClassBuild = IrGetClass.newBuilder().setArgument(serializeExpression(expression.getArgument())).build();
        Intrinsics.checkNotNullExpressionValue(irGetClassBuild, "build(...)");
        return irGetClassBuild;
    }

    private final IrGetEnumValue serializeGetEnumValue(org.jetbrains.kotlin.ir.expressions.IrGetEnumValue expression) {
        IrGetEnumValue irGetEnumValueBuild = IrGetEnumValue.newBuilder().setSymbol(serializeIrSymbol(expression.getSymbol())).build();
        Intrinsics.checkNotNullExpressionValue(irGetEnumValueBuild, "build(...)");
        return irGetEnumValueBuild;
    }

    private final FieldAccessCommon serializeFieldAccessCommon(IrFieldAccessExpression expression) {
        FieldAccessCommon.Builder symbol = FieldAccessCommon.newBuilder().setSymbol(serializeIrSymbol(expression.getSymbol()));
        IrClassSymbol superQualifierSymbol = expression.getSuperQualifierSymbol();
        if (superQualifierSymbol != null) {
            symbol.setSuper(serializeIrSymbol(superQualifierSymbol));
        }
        org.jetbrains.kotlin.ir.expressions.IrExpression receiver = expression.getReceiver();
        if (receiver != null) {
            symbol.setReceiver(serializeExpression(receiver));
        }
        FieldAccessCommon fieldAccessCommonBuild = symbol.build();
        Intrinsics.checkNotNullExpressionValue(fieldAccessCommonBuild, "build(...)");
        return fieldAccessCommonBuild;
    }

    private final IrGetField serializeGetField(org.jetbrains.kotlin.ir.expressions.IrGetField expression) {
        IrGetField.Builder fieldAccess = IrGetField.newBuilder().setFieldAccess(serializeFieldAccessCommon(expression));
        IrStatementOrigin origin = expression.getOrigin();
        if (origin != null) {
            fieldAccess.setOriginName(serializeIrStatementOrigin(origin));
        }
        IrGetField irGetFieldBuild = fieldAccess.build();
        Intrinsics.checkNotNullExpressionValue(irGetFieldBuild, "build(...)");
        return irGetFieldBuild;
    }

    private final IrGetValue serializeGetValue(org.jetbrains.kotlin.ir.expressions.IrGetValue expression) {
        IrGetValue.Builder symbol = IrGetValue.newBuilder().setSymbol(serializeIrSymbol(expression.getSymbol()));
        IrStatementOrigin origin = expression.getOrigin();
        if (origin != null) {
            symbol.setOriginName(serializeIrStatementOrigin(origin));
        }
        IrGetValue irGetValueBuild = symbol.build();
        Intrinsics.checkNotNullExpressionValue(irGetValueBuild, "build(...)");
        return irGetValueBuild;
    }

    private final IrGetObject serializeGetObject(IrGetObjectValue expression) {
        IrGetObject irGetObjectBuild = IrGetObject.newBuilder().setSymbol(serializeIrSymbol(expression.getSymbol())).build();
        Intrinsics.checkNotNullExpressionValue(irGetObjectBuild, "build(...)");
        return irGetObjectBuild;
    }

    private final IrInstanceInitializerCall serializeInstanceInitializerCall(org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall call) {
        IrInstanceInitializerCall.Builder builderNewBuilder = IrInstanceInitializerCall.newBuilder();
        builderNewBuilder.setSymbol(serializeIrSymbol(call.getClassSymbol()));
        IrInstanceInitializerCall irInstanceInitializerCallBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irInstanceInitializerCallBuild, "build(...)");
        return irInstanceInitializerCallBuild;
    }

    private final void serializeReturn(IrOperation.Builder operation, IrReturn expression) {
        operation.setReturn(org.jetbrains.kotlin.backend.common.serialization.proto.IrReturn.newBuilder().setReturnTarget(serializeIrSymbol(expression.getReturnTargetSymbol())).setValue(serializeExpression(expression.getValue())).build());
    }

    private final IrSetField serializeSetField(org.jetbrains.kotlin.ir.expressions.IrSetField expression) {
        IrSetField.Builder value = IrSetField.newBuilder().setFieldAccess(serializeFieldAccessCommon(expression)).setValue(serializeExpression(expression.getValue()));
        IrStatementOrigin origin = expression.getOrigin();
        if (origin != null) {
            value.setOriginName(serializeIrStatementOrigin(origin));
        }
        IrSetField irSetFieldBuild = value.build();
        Intrinsics.checkNotNullExpressionValue(irSetFieldBuild, "build(...)");
        return irSetFieldBuild;
    }

    private final IrSetValue serializeSetValue(org.jetbrains.kotlin.ir.expressions.IrSetValue expression) {
        IrSetValue.Builder value = IrSetValue.newBuilder().setSymbol(serializeIrSymbol(expression.getSymbol())).setValue(serializeExpression(expression.getValue()));
        IrStatementOrigin origin = expression.getOrigin();
        if (origin != null) {
            value.setOriginName(serializeIrStatementOrigin(origin));
        }
        IrSetValue irSetValueBuild = value.build();
        Intrinsics.checkNotNullExpressionValue(irSetValueBuild, "build(...)");
        return irSetValueBuild;
    }

    private final IrSpreadElement serializeSpreadElement(org.jetbrains.kotlin.ir.expressions.IrSpreadElement element) {
        IrSpreadElement irSpreadElementBuild = IrSpreadElement.newBuilder().setExpression(serializeExpression(element.getExpression())).setCoordinates(serializeCoordinates(element.getStartOffset(), element.getEndOffset())).build();
        Intrinsics.checkNotNullExpressionValue(irSpreadElementBuild, "build(...)");
        return irSpreadElementBuild;
    }

    private final IrSyntheticBody serializeSyntheticBody(org.jetbrains.kotlin.ir.expressions.IrSyntheticBody expression) {
        org.jetbrains.kotlin.backend.common.serialization.proto.IrSyntheticBodyKind irSyntheticBodyKind;
        IrSyntheticBody.Builder builderNewBuilder = IrSyntheticBody.newBuilder();
        int i = WhenMappings.$EnumSwitchMapping$1[expression.getKind().ordinal()];
        if (i == 1) {
            irSyntheticBodyKind = org.jetbrains.kotlin.backend.common.serialization.proto.IrSyntheticBodyKind.ENUM_VALUES;
        } else if (i == 2) {
            irSyntheticBodyKind = org.jetbrains.kotlin.backend.common.serialization.proto.IrSyntheticBodyKind.ENUM_VALUEOF;
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            irSyntheticBodyKind = org.jetbrains.kotlin.backend.common.serialization.proto.IrSyntheticBodyKind.ENUM_ENTRIES;
        }
        return builderNewBuilder.setKind(irSyntheticBodyKind).build();
    }

    private final IrThrow serializeThrow(org.jetbrains.kotlin.ir.expressions.IrThrow expression) {
        IrThrow irThrowBuild = IrThrow.newBuilder().setValue(serializeExpression(expression.getValue())).build();
        Intrinsics.checkNotNullExpressionValue(irThrowBuild, "build(...)");
        return irThrowBuild;
    }

    private final IrTry serializeTry(org.jetbrains.kotlin.ir.expressions.IrTry expression) {
        IrTry.Builder result = IrTry.newBuilder().setResult(serializeExpression(expression.getTryResult()));
        Iterator<T> it = expression.getCatches().iterator();
        while (it.hasNext()) {
            result.addCatch(serializeStatement((org.jetbrains.kotlin.ir.expressions.IrCatch) it.next()));
        }
        org.jetbrains.kotlin.ir.expressions.IrExpression finallyExpression = expression.getFinallyExpression();
        if (finallyExpression != null) {
            result.setFinally(serializeExpression(finallyExpression));
        }
        IrTry irTryBuild = result.build();
        Intrinsics.checkNotNullExpressionValue(irTryBuild, "build(...)");
        return irTryBuild;
    }

    private final org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOperator serializeTypeOperator(IrTypeOperator operator) {
        switch (WhenMappings.$EnumSwitchMapping$2[operator.ordinal()]) {
            case 1:
                return org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOperator.CAST;
            case 2:
                return org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOperator.IMPLICIT_CAST;
            case 3:
                return org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOperator.IMPLICIT_NOTNULL;
            case 4:
                return org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOperator.IMPLICIT_COERCION_TO_UNIT;
            case 5:
                return org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOperator.IMPLICIT_INTEGER_COERCION;
            case 6:
                return org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOperator.SAFE_CAST;
            case 7:
                return org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOperator.INSTANCEOF;
            case 8:
                return org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOperator.NOT_INSTANCEOF;
            case 9:
                return org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOperator.SAM_CONVERSION;
            case 10:
                return org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOperator.IMPLICIT_DYNAMIC_CAST;
            case 11:
                return org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeOperator.REINTERPRET_CAST;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final IrTypeOp serializeTypeOp(IrTypeOperatorCall expression) {
        IrTypeOp irTypeOpBuild = IrTypeOp.newBuilder().setOperator(serializeTypeOperator(expression.getOperator())).setOperand(serializeIrType(expression.getTypeOperand())).setArgument(serializeExpression(expression.getArgument())).build();
        Intrinsics.checkNotNullExpressionValue(irTypeOpBuild, "build(...)");
        return irTypeOpBuild;
    }

    private final IrVararg serializeVararg(org.jetbrains.kotlin.ir.expressions.IrVararg expression) {
        IrVararg.Builder elementType = IrVararg.newBuilder().setElementType(serializeIrType(expression.getVarargElementType()));
        Iterator<T> it = expression.getElements().iterator();
        while (it.hasNext()) {
            elementType.addElement(serializeVarargElement((IrVarargElement) it.next()));
        }
        IrVararg irVarargBuild = elementType.build();
        Intrinsics.checkNotNullExpressionValue(irVarargBuild, "build(...)");
        return irVarargBuild;
    }

    private final org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargElement serializeVarargElement(IrVarargElement element) {
        org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargElement.Builder builderNewBuilder = org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargElement.newBuilder();
        if (element instanceof org.jetbrains.kotlin.ir.expressions.IrExpression) {
            builderNewBuilder.setExpression(serializeExpression((org.jetbrains.kotlin.ir.expressions.IrExpression) element));
        } else if (element instanceof org.jetbrains.kotlin.ir.expressions.IrSpreadElement) {
            builderNewBuilder.setSpreadElement(serializeSpreadElement((org.jetbrains.kotlin.ir.expressions.IrSpreadElement) element));
        } else {
            throw new IllegalStateException("Unknown vararg element kind".toString());
        }
        org.jetbrains.kotlin.backend.common.serialization.proto.IrVarargElement irVarargElementBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irVarargElementBuild, "build(...)");
        return irVarargElementBuild;
    }

    private final IrWhen serializeWhen(org.jetbrains.kotlin.ir.expressions.IrWhen expression) {
        IrWhen.Builder builderNewBuilder = IrWhen.newBuilder();
        IrStatementOrigin origin = expression.getOrigin();
        if (origin != null) {
            builderNewBuilder.setOriginName(serializeIrStatementOrigin(origin));
        }
        Iterator<T> it = expression.getBranches().iterator();
        while (it.hasNext()) {
            builderNewBuilder.addBranch(serializeStatement((org.jetbrains.kotlin.ir.expressions.IrBranch) it.next()));
        }
        IrWhen irWhenBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irWhenBuild, "build(...)");
        return irWhenBuild;
    }

    private final Loop serializeLoop(IrLoop expression) {
        int i = this.currentLoopIndex;
        this.currentLoopIndex = i + 1;
        this.loopIndex.put(expression, Integer.valueOf(i));
        Loop.Builder condition = Loop.newBuilder().setCondition(serializeExpression(expression.getCondition()));
        IrStatementOrigin origin = expression.getOrigin();
        if (origin != null) {
            condition.setOriginName(serializeIrStatementOrigin(origin));
        }
        String label = expression.getLabel();
        if (label != null) {
            condition.setLabel(serializeString(label));
        }
        condition.setLoopId(i);
        org.jetbrains.kotlin.ir.expressions.IrExpression body = expression.getBody();
        if (body != null) {
            condition.setBody(serializeExpression(body));
        }
        Loop loopBuild = condition.build();
        Intrinsics.checkNotNullExpressionValue(loopBuild, "build(...)");
        return loopBuild;
    }

    private final IrWhile serializeWhile(IrWhileLoop expression) {
        IrWhile irWhileBuild = IrWhile.newBuilder().setLoop(serializeLoop(expression)).build();
        Intrinsics.checkNotNullExpressionValue(irWhileBuild, "build(...)");
        return irWhileBuild;
    }

    private final IrDynamicMemberExpression serializeDynamicMemberExpression(org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression expression) {
        IrDynamicMemberExpression irDynamicMemberExpressionBuild = IrDynamicMemberExpression.newBuilder().setMemberName(serializeString(expression.getMemberName())).setReceiver(serializeExpression(expression.getReceiver())).build();
        Intrinsics.checkNotNullExpressionValue(irDynamicMemberExpressionBuild, "build(...)");
        return irDynamicMemberExpressionBuild;
    }

    private final IrDynamicOperatorExpression serializeDynamicOperatorExpression(org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression expression) {
        IrDynamicOperatorExpression.Builder receiver = IrDynamicOperatorExpression.newBuilder().setOperator(serializeDynamicOperator(expression.getOperator())).setReceiver(serializeExpression(expression.getReceiver()));
        Iterator<T> it = expression.getArguments().iterator();
        while (it.hasNext()) {
            receiver.addArgument(serializeExpression((org.jetbrains.kotlin.ir.expressions.IrExpression) it.next()));
        }
        IrDynamicOperatorExpression irDynamicOperatorExpressionBuild = receiver.build();
        Intrinsics.checkNotNullExpressionValue(irDynamicOperatorExpressionBuild, "build(...)");
        return irDynamicOperatorExpressionBuild;
    }

    private final IrErrorExpression serializeErrorExpression(org.jetbrains.kotlin.ir.expressions.IrErrorExpression expression) {
        IrErrorExpression irErrorExpressionBuild = IrErrorExpression.newBuilder().setDescription(serializeString(expression.getDescription())).build();
        Intrinsics.checkNotNullExpressionValue(irErrorExpressionBuild, "build(...)");
        return irErrorExpressionBuild;
    }

    private final IrErrorCallExpression serializeErrorCallExpression(org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression callExpression) {
        IrErrorCallExpression.Builder description = IrErrorCallExpression.newBuilder().setDescription(serializeString(callExpression.getDescription()));
        org.jetbrains.kotlin.ir.expressions.IrExpression explicitReceiver = callExpression.getExplicitReceiver();
        if (explicitReceiver != null) {
            description.setReceiver(serializeExpression(explicitReceiver));
        }
        Iterator<T> it = callExpression.getArguments().iterator();
        while (it.hasNext()) {
            description.addValueArgument(serializeExpression((org.jetbrains.kotlin.ir.expressions.IrExpression) it.next()));
        }
        IrErrorCallExpression irErrorCallExpressionBuild = description.build();
        Intrinsics.checkNotNullExpressionValue(irErrorCallExpressionBuild, "build(...)");
        return irErrorCallExpressionBuild;
    }

    private final IrDynamicOperatorExpression.IrDynamicOperator serializeDynamicOperator(IrDynamicOperator operator) {
        switch (WhenMappings.$EnumSwitchMapping$3[operator.ordinal()]) {
            case 1:
                return IrDynamicOperatorExpression.IrDynamicOperator.UNARY_PLUS;
            case 2:
                return IrDynamicOperatorExpression.IrDynamicOperator.UNARY_MINUS;
            case 3:
                return IrDynamicOperatorExpression.IrDynamicOperator.EXCL;
            case 4:
                return IrDynamicOperatorExpression.IrDynamicOperator.PREFIX_INCREMENT;
            case 5:
                return IrDynamicOperatorExpression.IrDynamicOperator.PREFIX_DECREMENT;
            case 6:
                return IrDynamicOperatorExpression.IrDynamicOperator.POSTFIX_INCREMENT;
            case 7:
                return IrDynamicOperatorExpression.IrDynamicOperator.POSTFIX_DECREMENT;
            case 8:
                return IrDynamicOperatorExpression.IrDynamicOperator.BINARY_PLUS;
            case 9:
                return IrDynamicOperatorExpression.IrDynamicOperator.BINARY_MINUS;
            case 10:
                return IrDynamicOperatorExpression.IrDynamicOperator.MUL;
            case 11:
                return IrDynamicOperatorExpression.IrDynamicOperator.DIV;
            case 12:
                return IrDynamicOperatorExpression.IrDynamicOperator.MOD;
            case 13:
                return IrDynamicOperatorExpression.IrDynamicOperator.GT;
            case 14:
                return IrDynamicOperatorExpression.IrDynamicOperator.LT;
            case 15:
                return IrDynamicOperatorExpression.IrDynamicOperator.GE;
            case 16:
                return IrDynamicOperatorExpression.IrDynamicOperator.LE;
            case 17:
                return IrDynamicOperatorExpression.IrDynamicOperator.EQEQ;
            case 18:
                return IrDynamicOperatorExpression.IrDynamicOperator.EXCLEQ;
            case 19:
                return IrDynamicOperatorExpression.IrDynamicOperator.EQEQEQ;
            case 20:
                return IrDynamicOperatorExpression.IrDynamicOperator.EXCLEQEQ;
            case 21:
                return IrDynamicOperatorExpression.IrDynamicOperator.ANDAND;
            case 22:
                return IrDynamicOperatorExpression.IrDynamicOperator.OROR;
            case 23:
                return IrDynamicOperatorExpression.IrDynamicOperator.EQ;
            case 24:
                return IrDynamicOperatorExpression.IrDynamicOperator.PLUSEQ;
            case 25:
                return IrDynamicOperatorExpression.IrDynamicOperator.MINUSEQ;
            case 26:
                return IrDynamicOperatorExpression.IrDynamicOperator.MULEQ;
            case 27:
                return IrDynamicOperatorExpression.IrDynamicOperator.DIVEQ;
            case 28:
                return IrDynamicOperatorExpression.IrDynamicOperator.MODEQ;
            case 29:
                return IrDynamicOperatorExpression.IrDynamicOperator.ARRAY_ACCESS;
            case 30:
                return IrDynamicOperatorExpression.IrDynamicOperator.INVOKE;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final IrBreak serializeBreak(org.jetbrains.kotlin.ir.expressions.IrBreak expression) {
        IrBreak.Builder builderNewBuilder = IrBreak.newBuilder();
        String label = expression.getLabel();
        if (label != null) {
            builderNewBuilder.setLabel(serializeString(label));
        }
        Integer num = this.loopIndex.get(expression.getLoop());
        if (num == null) {
            num = -1;
        }
        builderNewBuilder.setLoopId(num.intValue());
        IrBreak irBreakBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irBreakBuild, "build(...)");
        return irBreakBuild;
    }

    private final IrContinue serializeContinue(org.jetbrains.kotlin.ir.expressions.IrContinue expression) {
        IrContinue.Builder builderNewBuilder = IrContinue.newBuilder();
        String label = expression.getLabel();
        if (label != null) {
            builderNewBuilder.setLabel(serializeString(label));
        }
        Integer num = this.loopIndex.get(expression.getLoop());
        if (num == null) {
            num = -1;
        }
        builderNewBuilder.setLoopId(num.intValue());
        IrContinue irContinueBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irContinueBuild, "build(...)");
        return irContinueBuild;
    }

    private final IrExpression serializeExpression(org.jetbrains.kotlin.ir.expressions.IrExpression expression) {
        IrExpression.Builder coordinates = IrExpression.newBuilder().setType(serializeIrType(expression.getType())).setCoordinates(serializeCoordinates(expression.getStartOffset(), expression.getEndOffset()));
        IrOperation.Builder builderNewBuilder = IrOperation.newBuilder();
        if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrBlock) {
            builderNewBuilder.setBlock(serializeBlock((org.jetbrains.kotlin.ir.expressions.IrBlock) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrBreak) {
            builderNewBuilder.setBreak(serializeBreak((org.jetbrains.kotlin.ir.expressions.IrBreak) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrClassReference) {
            builderNewBuilder.setClassReference(serializeClassReference((org.jetbrains.kotlin.ir.expressions.IrClassReference) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrCall) {
            builderNewBuilder.setCall(serializeCall((org.jetbrains.kotlin.ir.expressions.IrCall) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrConstructorCall) {
            builderNewBuilder.setConstructorCall(serializeConstructorCall((org.jetbrains.kotlin.ir.expressions.IrConstructorCall) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrComposite) {
            builderNewBuilder.setComposite(serializeComposite((org.jetbrains.kotlin.ir.expressions.IrComposite) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrConst) {
            builderNewBuilder.setConst(serializeConst((org.jetbrains.kotlin.ir.expressions.IrConst) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrContinue) {
            builderNewBuilder.setContinue(serializeContinue((org.jetbrains.kotlin.ir.expressions.IrContinue) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall) {
            builderNewBuilder.setDelegatingConstructorCall(serializeDelegatingConstructorCall((org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall) expression));
        } else if (expression instanceof IrDoWhileLoop) {
            builderNewBuilder.setDoWhile(serializeDoWhile((IrDoWhileLoop) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall) {
            builderNewBuilder.setEnumConstructorCall(serializeEnumConstructorCall((org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrFunctionExpression) {
            builderNewBuilder.setFunctionExpression(serializeFunctionExpression((org.jetbrains.kotlin.ir.expressions.IrFunctionExpression) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrFunctionReference) {
            builderNewBuilder.setFunctionReference(serializeFunctionReference((org.jetbrains.kotlin.ir.expressions.IrFunctionReference) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrGetClass) {
            builderNewBuilder.setGetClass(serializeGetClass((org.jetbrains.kotlin.ir.expressions.IrGetClass) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrGetField) {
            builderNewBuilder.setGetField(serializeGetField((org.jetbrains.kotlin.ir.expressions.IrGetField) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrGetValue) {
            builderNewBuilder.setGetValue(serializeGetValue((org.jetbrains.kotlin.ir.expressions.IrGetValue) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrGetEnumValue) {
            builderNewBuilder.setGetEnumValue(serializeGetEnumValue((org.jetbrains.kotlin.ir.expressions.IrGetEnumValue) expression));
        } else if (expression instanceof IrGetObjectValue) {
            builderNewBuilder.setGetObject(serializeGetObject((IrGetObjectValue) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall) {
            builderNewBuilder.setInstanceInitializerCall(serializeInstanceInitializerCall((org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference) {
            builderNewBuilder.setLocalDelegatedPropertyReference(serializeIrLocalDelegatedPropertyReference((org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrPropertyReference) {
            builderNewBuilder.setPropertyReference(serializePropertyReference((org.jetbrains.kotlin.ir.expressions.IrPropertyReference) expression));
        } else if (expression instanceof IrReturn) {
            Intrinsics.checkNotNull(builderNewBuilder);
            serializeReturn(builderNewBuilder, (IrReturn) expression);
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrSetField) {
            builderNewBuilder.setSetField(serializeSetField((org.jetbrains.kotlin.ir.expressions.IrSetField) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrSetValue) {
            builderNewBuilder.setSetValue(serializeSetValue((org.jetbrains.kotlin.ir.expressions.IrSetValue) expression));
        } else if (expression instanceof IrStringConcatenation) {
            builderNewBuilder.setStringConcat(serializeStringConcat((IrStringConcatenation) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrThrow) {
            builderNewBuilder.setThrow(serializeThrow((org.jetbrains.kotlin.ir.expressions.IrThrow) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrTry) {
            builderNewBuilder.setTry(serializeTry((org.jetbrains.kotlin.ir.expressions.IrTry) expression));
        } else if (expression instanceof IrTypeOperatorCall) {
            builderNewBuilder.setTypeOp(serializeTypeOp((IrTypeOperatorCall) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrVararg) {
            builderNewBuilder.setVararg(serializeVararg((org.jetbrains.kotlin.ir.expressions.IrVararg) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrWhen) {
            builderNewBuilder.setWhen(serializeWhen((org.jetbrains.kotlin.ir.expressions.IrWhen) expression));
        } else if (expression instanceof IrWhileLoop) {
            builderNewBuilder.setWhile(serializeWhile((IrWhileLoop) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression) {
            builderNewBuilder.setDynamicMember(serializeDynamicMemberExpression((org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression) {
            builderNewBuilder.setDynamicOperator(serializeDynamicOperatorExpression((org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression) expression));
        } else if (expression instanceof org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression) {
            builderNewBuilder.setErrorCallExpression(serializeErrorCallExpression((org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression) expression));
        } else {
            if (!(expression instanceof org.jetbrains.kotlin.ir.expressions.IrErrorExpression)) {
                throw new NotImplementedError("An operation is not implemented: " + ("Expression serialization not implemented yet: " + RenderIrElementKt.render$default(expression, (DumpIrTreeOptions) null, 1, (Object) null) + '.'));
            }
            builderNewBuilder.setErrorExpression(serializeErrorExpression((org.jetbrains.kotlin.ir.expressions.IrErrorExpression) expression));
        }
        coordinates.setOperation(builderNewBuilder);
        IrExpression irExpressionBuild = coordinates.build();
        Intrinsics.checkNotNullExpressionValue(irExpressionBuild, "build(...)");
        return irExpressionBuild;
    }

    private final IrStatement serializeStatement(IrElement statement) {
        IrStatement.Builder coordinates = IrStatement.newBuilder().setCoordinates(serializeCoordinates(statement.getStartOffset(), statement.getEndOffset()));
        if (statement instanceof IrDeclaration) {
            coordinates.setDeclaration(serializeDeclaration((IrDeclaration) statement));
        } else if (statement instanceof org.jetbrains.kotlin.ir.expressions.IrExpression) {
            coordinates.setExpression(serializeExpression((org.jetbrains.kotlin.ir.expressions.IrExpression) statement));
        } else if (statement instanceof org.jetbrains.kotlin.ir.expressions.IrBlockBody) {
            coordinates.setBlockBody(serializeBlockBody((org.jetbrains.kotlin.ir.expressions.IrBlockBody) statement));
        } else if (statement instanceof org.jetbrains.kotlin.ir.expressions.IrBranch) {
            coordinates.setBranch(serializeBranch((org.jetbrains.kotlin.ir.expressions.IrBranch) statement));
        } else if (statement instanceof org.jetbrains.kotlin.ir.expressions.IrCatch) {
            coordinates.setCatch(serializeCatch((org.jetbrains.kotlin.ir.expressions.IrCatch) statement));
        } else if (statement instanceof org.jetbrains.kotlin.ir.expressions.IrSyntheticBody) {
            coordinates.setSyntheticBody(serializeSyntheticBody((org.jetbrains.kotlin.ir.expressions.IrSyntheticBody) statement));
        } else if (statement instanceof IrExpressionBody) {
            coordinates.setExpression(serializeExpression(((IrExpressionBody) statement).getExpression()));
        } else {
            throw new NotImplementedError("An operation is not implemented: " + ("Statement not implemented yet: " + RenderIrElementKt.render$default(statement, (DumpIrTreeOptions) null, 1, (Object) null)));
        }
        IrStatement irStatementBuild = coordinates.build();
        Intrinsics.checkNotNullExpressionValue(irStatementBuild, "build(...)");
        return irStatementBuild;
    }

    private final IrDeclarationBase serializeIrDeclarationBase(IrDeclaration declaration, Long flags) {
        if (!this.skipExpects) {
            this.expectActualTable.findExpectsForActuals(declaration);
        }
        IrDeclarationBase.Builder builderNewBuilder = IrDeclarationBase.newBuilder();
        Intrinsics.checkNotNull(declaration, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSymbolOwner");
        builderNewBuilder.setSymbol(serializeIrSymbol(declaration.getSymbol()));
        builderNewBuilder.setCoordinates(serializeCoordinates(declaration.getStartOffset(), declaration.getEndOffset()));
        builderNewBuilder.addAllAnnotation(serializeAnnotations(declaration.getAnnotations()));
        if (flags != null) {
            builderNewBuilder.setFlags(flags.longValue());
        }
        builderNewBuilder.setOriginName(serializeIrDeclarationOrigin(declaration.getOrigin()));
        IrDeclarationBase irDeclarationBaseBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irDeclarationBaseBuild, "with(...)");
        return irDeclarationBaseBuild;
    }

    private final long serializeNameAndType(Name name2, org.jetbrains.kotlin.ir.types.IrType type) {
        return BinaryNameAndType.INSTANCE.encode(serializeName(name2), serializeIrType(type));
    }

    private final IrValueParameter serializeIrValueParameter(org.jetbrains.kotlin.ir.declarations.IrValueParameter parameter) {
        IrValueParameter.Builder nameType = IrValueParameter.newBuilder().setBase(serializeIrDeclarationBase(parameter, Long.valueOf(ValueParameterFlags.INSTANCE.encode(parameter)))).setNameType(serializeNameAndType(parameter.getName(), parameter.getType()));
        org.jetbrains.kotlin.ir.types.IrType varargElementType = parameter.getVarargElementType();
        if (varargElementType != null) {
            nameType.setVarargElementType(serializeIrType(varargElementType));
        }
        IrExpressionBody defaultValue = parameter.getDefaultValue();
        if (defaultValue != null) {
            nameType.setDefaultValue(serializeIrExpressionBody(defaultValue.getExpression()));
        }
        IrValueParameter irValueParameterBuild = nameType.build();
        Intrinsics.checkNotNullExpressionValue(irValueParameterBuild, "build(...)");
        return irValueParameterBuild;
    }

    private final IrTypeParameter serializeIrTypeParameter(org.jetbrains.kotlin.ir.declarations.IrTypeParameter parameter) {
        IrTypeParameter.Builder name2 = IrTypeParameter.newBuilder().setBase(serializeIrDeclarationBase(parameter, Long.valueOf(TypeParameterFlags.INSTANCE.encode(parameter)))).setName(serializeName(parameter.getName()));
        Iterator<T> it = parameter.getSuperTypes().iterator();
        while (it.hasNext()) {
            name2.addSuperType(serializeIrType((org.jetbrains.kotlin.ir.types.IrType) it.next()));
        }
        IrTypeParameter irTypeParameterBuild = name2.build();
        Intrinsics.checkNotNullExpressionValue(irTypeParameterBuild, "build(...)");
        return irTypeParameterBuild;
    }

    private final IrFunctionBase serializeIrFunctionBase(IrFunction function, long flags) {
        IrBody body;
        IrFunctionBase.Builder nameType = IrFunctionBase.newBuilder().setBase(serializeIrDeclarationBase(function, Long.valueOf(flags))).setNameType(serializeNameAndType(function.getName(), function.getReturnType()));
        Iterator<T> it = function.getTypeParameters().iterator();
        while (it.hasNext()) {
            nameType.addTypeParameter(serializeIrTypeParameter((org.jetbrains.kotlin.ir.declarations.IrTypeParameter) it.next()));
        }
        org.jetbrains.kotlin.ir.declarations.IrValueParameter dispatchReceiverParameter = function.getDispatchReceiverParameter();
        if (dispatchReceiverParameter != null) {
            nameType.setDispatchReceiver(serializeIrValueParameter(dispatchReceiverParameter));
        }
        org.jetbrains.kotlin.ir.declarations.IrValueParameter extensionReceiverParameter = function.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            nameType.setExtensionReceiver(serializeIrValueParameter(extensionReceiverParameter));
        }
        int contextReceiverParametersCount = function.getContextReceiverParametersCount();
        if (contextReceiverParametersCount > 0) {
            nameType.setContextReceiverParametersCount(contextReceiverParametersCount);
        }
        Iterator<T> it2 = function.getValueParameters().iterator();
        while (it2.hasNext()) {
            nameType.addValueParameter(serializeIrValueParameter((org.jetbrains.kotlin.ir.declarations.IrValueParameter) it2.next()));
        }
        if ((!this.bodiesOnlyForInlines || function.getIsInline()) && (body = function.getBody()) != null) {
            nameType.setBody(serializeIrStatementBody(body));
        }
        IrFunctionBase irFunctionBaseBuild = nameType.build();
        Intrinsics.checkNotNullExpressionValue(irFunctionBaseBuild, "build(...)");
        return irFunctionBaseBuild;
    }

    private final IrConstructor serializeIrConstructor(org.jetbrains.kotlin.ir.declarations.IrConstructor declaration) {
        IrConstructor irConstructorBuild = IrConstructor.newBuilder().setBase(serializeIrFunctionBase(declaration, FunctionFlags.INSTANCE.encode(declaration))).build();
        Intrinsics.checkNotNullExpressionValue(irConstructorBuild, "build(...)");
        return irConstructorBuild;
    }

    private final org.jetbrains.kotlin.backend.common.serialization.proto.IrFunction serializeIrFunction(IrSimpleFunction declaration) {
        org.jetbrains.kotlin.backend.common.serialization.proto.IrFunction.Builder base = org.jetbrains.kotlin.backend.common.serialization.proto.IrFunction.newBuilder().setBase(serializeIrFunctionBase(declaration, FunctionFlags.INSTANCE.encode(declaration)));
        Iterator<T> it = declaration.getOverriddenSymbols().iterator();
        while (it.hasNext()) {
            base.addOverridden(serializeIrSymbol((IrSimpleFunctionSymbol) it.next()));
        }
        org.jetbrains.kotlin.backend.common.serialization.proto.IrFunction irFunctionBuild = base.build();
        Intrinsics.checkNotNullExpressionValue(irFunctionBuild, "build(...)");
        return irFunctionBuild;
    }

    private final IrAnonymousInit serializeIrAnonymousInit(IrAnonymousInitializer declaration) {
        IrAnonymousInit.Builder base = IrAnonymousInit.newBuilder().setBase(serializeIrDeclarationBase(declaration, null));
        base.setBody(serializeIrStatementBody(declaration.getBody()));
        IrAnonymousInit irAnonymousInitBuild = base.build();
        Intrinsics.checkNotNullExpressionValue(irAnonymousInitBuild, "build(...)");
        return irAnonymousInitBuild;
    }

    private final IrLocalDelegatedProperty serializeIrLocalDelegatedProperty(org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty variable) {
        IrLocalDelegatedProperty.Builder nameType = IrLocalDelegatedProperty.newBuilder().setBase(serializeIrDeclarationBase(variable, Long.valueOf(LocalVariableFlags.INSTANCE.encode(variable)))).setNameType(serializeNameAndType(variable.getName(), variable.getType()));
        nameType.setDelegate(serializeIrVariable(variable.getDelegate()));
        nameType.setGetter(serializeIrFunction(variable.getGetter()));
        IrSimpleFunction setter = variable.getSetter();
        if (setter != null) {
            nameType.setSetter(serializeIrFunction(setter));
        }
        IrLocalDelegatedProperty irLocalDelegatedPropertyBuild = nameType.build();
        Intrinsics.checkNotNullExpressionValue(irLocalDelegatedPropertyBuild, "build(...)");
        return irLocalDelegatedPropertyBuild;
    }

    private final org.jetbrains.kotlin.backend.common.serialization.proto.IrProperty serializeIrProperty(IrProperty property) {
        org.jetbrains.kotlin.backend.common.serialization.proto.IrProperty.Builder name2 = org.jetbrains.kotlin.backend.common.serialization.proto.IrProperty.newBuilder().setBase(serializeIrDeclarationBase(property, Long.valueOf(PropertyFlags.INSTANCE.encode(property)))).setName(serializeName(property.getName()));
        IrField backingField = property.getBackingField();
        if (backingField != null) {
            name2.setBackingField(serializeIrField(backingField));
        }
        IrSimpleFunction getter = property.getGetter();
        if (getter != null) {
            name2.setGetter(serializeIrFunction(getter));
        }
        IrSimpleFunction setter = property.getSetter();
        if (setter != null) {
            name2.setSetter(serializeIrFunction(setter));
        }
        org.jetbrains.kotlin.backend.common.serialization.proto.IrProperty irPropertyBuild = name2.build();
        Intrinsics.checkNotNullExpressionValue(irPropertyBuild, "build(...)");
        return irPropertyBuild;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x005a  */
    /* JADX WARN: Code duplicated, block: B:21:0x0060  */
    /* JADX WARN: Code duplicated, block: B:23:0x0066  */
    private final org.jetbrains.kotlin.backend.common.serialization.proto.IrField serializeIrField(IrField field) {
        org.jetbrains.kotlin.ir.expressions.IrExpression expression;
        org.jetbrains.kotlin.backend.common.serialization.proto.IrField.Builder nameType = org.jetbrains.kotlin.backend.common.serialization.proto.IrField.newBuilder().setBase(serializeIrDeclarationBase(field, Long.valueOf(FieldFlags.INSTANCE.encode(field)))).setNameType(serializeNameAndType(field.getName(), field.getType()));
        if (this.bodiesOnlyForInlines) {
            IrDeclarationParent parent = field.getParent();
            IrDeclarationWithVisibility irDeclarationWithVisibility = parent instanceof IrDeclarationWithVisibility ? (IrDeclarationWithVisibility) parent : null;
            if (!Intrinsics.areEqual(irDeclarationWithVisibility != null ? irDeclarationWithVisibility.getVisibility() : null, DescriptorVisibilities.LOCAL)) {
                IrExpressionBody initializer = field.getInitializer();
                if ((initializer != null ? initializer.getExpression() : null) instanceof org.jetbrains.kotlin.ir.expressions.IrConst) {
                    IrExpressionBody initializer2 = field.getInitializer();
                    if (initializer2 != null) {
                    }
                    if (expression != null) {
                        nameType.setInitializer(serializeIrExpressionBody(expression));
                    }
                }
            } else {
                IrExpressionBody initializer3 = field.getInitializer();
                expression = initializer3 != null ? initializer3.getExpression() : null;
                if (expression != null) {
                    nameType.setInitializer(serializeIrExpressionBody(expression));
                }
            }
        } else {
            IrExpressionBody initializer4 = field.getInitializer();
            if (initializer4 != null) {
            }
            if (expression != null) {
                nameType.setInitializer(serializeIrExpressionBody(expression));
            }
        }
        org.jetbrains.kotlin.backend.common.serialization.proto.IrField irFieldBuild = nameType.build();
        Intrinsics.checkNotNullExpressionValue(irFieldBuild, "build(...)");
        return irFieldBuild;
    }

    private final IrVariable serializeIrVariable(org.jetbrains.kotlin.ir.declarations.IrVariable variable) {
        IrVariable.Builder nameType = IrVariable.newBuilder().setBase(serializeIrDeclarationBase(variable, Long.valueOf(LocalVariableFlags.INSTANCE.encode(variable)))).setNameType(serializeNameAndType(variable.getName(), variable.getType()));
        org.jetbrains.kotlin.ir.expressions.IrExpression initializer = variable.getInitializer();
        if (initializer != null) {
            nameType.setInitializer(serializeExpression(initializer));
        }
        IrVariable irVariableBuild = nameType.build();
        Intrinsics.checkNotNullExpressionValue(irVariableBuild, "build(...)");
        return irVariableBuild;
    }

    private final org.jetbrains.kotlin.backend.common.serialization.proto.IrClass serializeIrClass(IrClass clazz) {
        org.jetbrains.kotlin.backend.common.serialization.proto.IrClass.Builder name2 = org.jetbrains.kotlin.backend.common.serialization.proto.IrClass.newBuilder().setBase(serializeIrDeclarationBase(clazz, Long.valueOf(ClassFlags.INSTANCE.encode(clazz, this.languageVersionSettings)))).setName(serializeName(clazz.getName()));
        ValueClassRepresentation<org.jetbrains.kotlin.ir.types.IrSimpleType> valueClassRepresentation = clazz.getValueClassRepresentation();
        if (valueClassRepresentation instanceof MultiFieldValueClassRepresentation) {
            name2.setMultiFieldValueClassRepresentation(serializeMultiFieldValueClassRepresentation((MultiFieldValueClassRepresentation) valueClassRepresentation));
        } else if (valueClassRepresentation instanceof InlineClassRepresentation) {
            name2.setInlineClassRepresentation(serializeInlineClassRepresentation((InlineClassRepresentation) valueClassRepresentation));
        }
        for (IrDeclaration irDeclaration : clazz.getDeclarations()) {
            if (memberNeedsSerialization(irDeclaration)) {
                name2.addDeclaration(serializeDeclaration(irDeclaration));
            }
        }
        Iterator<T> it = clazz.getTypeParameters().iterator();
        while (it.hasNext()) {
            name2.addTypeParameter(serializeIrTypeParameter((org.jetbrains.kotlin.ir.declarations.IrTypeParameter) it.next()));
        }
        org.jetbrains.kotlin.ir.declarations.IrValueParameter thisReceiver = clazz.getThisReceiver();
        if (thisReceiver != null) {
            name2.setThisReceiver(serializeIrValueParameter(thisReceiver));
        }
        Iterator<T> it2 = clazz.getSuperTypes().iterator();
        while (it2.hasNext()) {
            name2.addSuperType(serializeIrType((org.jetbrains.kotlin.ir.types.IrType) it2.next()));
        }
        Iterator<T> it3 = clazz.getSealedSubclasses().iterator();
        while (it3.hasNext()) {
            name2.addSealedSubclass(serializeIrSymbol((IrClassSymbol) it3.next()));
        }
        org.jetbrains.kotlin.backend.common.serialization.proto.IrClass irClassBuild = name2.build();
        Intrinsics.checkNotNullExpressionValue(irClassBuild, "build(...)");
        return irClassBuild;
    }

    private final IrInlineClassRepresentation serializeInlineClassRepresentation(InlineClassRepresentation<org.jetbrains.kotlin.ir.types.IrSimpleType> representation) {
        IrInlineClassRepresentation.Builder builderNewBuilder = IrInlineClassRepresentation.newBuilder();
        builderNewBuilder.setUnderlyingPropertyName(serializeName(representation.getUnderlyingPropertyName()));
        builderNewBuilder.setUnderlyingPropertyType(serializeIrType((org.jetbrains.kotlin.ir.types.IrType) representation.getUnderlyingType()));
        IrInlineClassRepresentation irInlineClassRepresentationBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irInlineClassRepresentationBuild, "build(...)");
        return irInlineClassRepresentationBuild;
    }

    private final IrMultiFieldValueClassRepresentation serializeMultiFieldValueClassRepresentation(MultiFieldValueClassRepresentation<org.jetbrains.kotlin.ir.types.IrSimpleType> representation) {
        IrMultiFieldValueClassRepresentation.Builder builderNewBuilder = IrMultiFieldValueClassRepresentation.newBuilder();
        Iterable underlyingPropertyNamesToTypes = representation.getUnderlyingPropertyNamesToTypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(underlyingPropertyNamesToTypes, 10));
        Iterator it = underlyingPropertyNamesToTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(serializeName((Name) ((Pair) it.next()).component1())));
        }
        builderNewBuilder.addAllUnderlyingPropertyName(arrayList);
        Iterable underlyingPropertyNamesToTypes2 = representation.getUnderlyingPropertyNamesToTypes();
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(underlyingPropertyNamesToTypes2, 10));
        Iterator it2 = underlyingPropertyNamesToTypes2.iterator();
        while (it2.hasNext()) {
            arrayList2.add(Integer.valueOf(serializeIrType((org.jetbrains.kotlin.ir.types.IrSimpleType) ((Pair) it2.next()).component2())));
        }
        builderNewBuilder.addAllUnderlyingPropertyType(arrayList2);
        IrMultiFieldValueClassRepresentation irMultiFieldValueClassRepresentationBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irMultiFieldValueClassRepresentationBuild, "build(...)");
        return irMultiFieldValueClassRepresentationBuild;
    }

    private final IrTypeAlias serializeIrTypeAlias(org.jetbrains.kotlin.ir.declarations.IrTypeAlias typeAlias) {
        IrTypeAlias.Builder builderNewBuilder = IrTypeAlias.newBuilder();
        builderNewBuilder.setBase(serializeIrDeclarationBase(typeAlias, Long.valueOf(TypeAliasFlags.INSTANCE.encode(typeAlias)))).setNameType(serializeNameAndType(typeAlias.getName(), typeAlias.getExpandedType()));
        Iterator<T> it = typeAlias.getTypeParameters().iterator();
        while (it.hasNext()) {
            builderNewBuilder.addTypeParameter(serializeIrTypeParameter((org.jetbrains.kotlin.ir.declarations.IrTypeParameter) it.next()));
        }
        IrTypeAlias irTypeAliasBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irTypeAliasBuild, "build(...)");
        return irTypeAliasBuild;
    }

    private final IrErrorDeclaration serializeIrErrorDeclaration(org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration errorDeclaration) {
        IrErrorDeclaration irErrorDeclarationBuild = IrErrorDeclaration.newBuilder().setCoordinates(serializeCoordinates(errorDeclaration.getStartOffset(), errorDeclaration.getEndOffset())).build();
        Intrinsics.checkNotNullExpressionValue(irErrorDeclarationBuild, "build(...)");
        return irErrorDeclarationBuild;
    }

    private final IrEnumEntry serializeIrEnumEntry(org.jetbrains.kotlin.ir.declarations.IrEnumEntry enumEntry) {
        IrEnumEntry.Builder name2 = IrEnumEntry.newBuilder().setBase(serializeIrDeclarationBase(enumEntry, null)).setName(serializeName(enumEntry.getName()));
        IrExpressionBody initializerExpression = enumEntry.getInitializerExpression();
        if (initializerExpression != null) {
            name2.setInitializer(serializeIrExpressionBody(initializerExpression.getExpression()));
        }
        IrClass correspondingClass = enumEntry.getCorrespondingClass();
        if (correspondingClass != null) {
            name2.setCorrespondingClass(serializeIrClass(correspondingClass));
        }
        IrEnumEntry irEnumEntryBuild = name2.build();
        Intrinsics.checkNotNullExpressionValue(irEnumEntryBuild, "build(...)");
        return irEnumEntryBuild;
    }

    public final org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclaration serializeDeclaration(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclaration.Builder builderNewBuilder = org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclaration.newBuilder();
        if (declaration instanceof IrAnonymousInitializer) {
            builderNewBuilder.setIrAnonymousInit(serializeIrAnonymousInit((IrAnonymousInitializer) declaration));
        } else if (declaration instanceof org.jetbrains.kotlin.ir.declarations.IrConstructor) {
            builderNewBuilder.setIrConstructor(serializeIrConstructor((org.jetbrains.kotlin.ir.declarations.IrConstructor) declaration));
        } else if (declaration instanceof IrField) {
            builderNewBuilder.setIrField(serializeIrField((IrField) declaration));
        } else if (declaration instanceof IrSimpleFunction) {
            builderNewBuilder.setIrFunction(serializeIrFunction((IrSimpleFunction) declaration));
        } else if (declaration instanceof org.jetbrains.kotlin.ir.declarations.IrTypeParameter) {
            builderNewBuilder.setIrTypeParameter(serializeIrTypeParameter((org.jetbrains.kotlin.ir.declarations.IrTypeParameter) declaration));
        } else if (declaration instanceof org.jetbrains.kotlin.ir.declarations.IrVariable) {
            builderNewBuilder.setIrVariable(serializeIrVariable((org.jetbrains.kotlin.ir.declarations.IrVariable) declaration));
        } else if (declaration instanceof org.jetbrains.kotlin.ir.declarations.IrValueParameter) {
            builderNewBuilder.setIrValueParameter(serializeIrValueParameter((org.jetbrains.kotlin.ir.declarations.IrValueParameter) declaration));
        } else if (declaration instanceof IrClass) {
            builderNewBuilder.setIrClass(serializeIrClass((IrClass) declaration));
        } else if (declaration instanceof org.jetbrains.kotlin.ir.declarations.IrEnumEntry) {
            builderNewBuilder.setIrEnumEntry(serializeIrEnumEntry((org.jetbrains.kotlin.ir.declarations.IrEnumEntry) declaration));
        } else if (declaration instanceof IrProperty) {
            builderNewBuilder.setIrProperty(serializeIrProperty((IrProperty) declaration));
        } else if (declaration instanceof org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty) {
            builderNewBuilder.setIrLocalDelegatedProperty(serializeIrLocalDelegatedProperty((org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty) declaration));
        } else if (declaration instanceof org.jetbrains.kotlin.ir.declarations.IrTypeAlias) {
            builderNewBuilder.setIrTypeAlias(serializeIrTypeAlias((org.jetbrains.kotlin.ir.declarations.IrTypeAlias) declaration));
        } else if (declaration instanceof org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration) {
            builderNewBuilder.setIrErrorDeclaration(serializeIrErrorDeclaration((org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration) declaration));
        } else {
            throw new NotImplementedError("An operation is not implemented: " + ("Declaration serialization not supported yet: " + declaration));
        }
        org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclaration irDeclarationBuild = builderNewBuilder.build();
        Intrinsics.checkNotNullExpressionValue(irDeclarationBuild, "build(...)");
        return irDeclarationBuild;
    }

    private final FileEntry serializeFileEntry(IrFileEntry entry) {
        FileEntry fileEntryBuild = FileEntry.newBuilder().setName(matchAndNormalizeFilePath(entry)).addAllLineStartOffset(ArraysKt.asIterable(AdditionalIrUtilsKt.getLineStartOffsets(entry))).build();
        Intrinsics.checkNotNullExpressionValue(fileEntryBuild, "build(...)");
        return fileEntryBuild;
    }

    public boolean keepOrderOfProperties(IrProperty property) {
        Intrinsics.checkNotNullParameter(property, "property");
        return !property.getIsConst();
    }

    public boolean memberNeedsSerialization(IrDeclaration member) {
        Intrinsics.checkNotNullParameter(member, "member");
        IrDeclarationParent parent = member.getParent();
        if (!(parent instanceof IrClass)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        IrClass irClass = (IrClass) parent;
        if (backendSpecificSerializeAllMembers(irClass)) {
            return true;
        }
        if (this.bodiesOnlyForInlines && (member instanceof IrAnonymousInitializer) && !Intrinsics.areEqual(irClass.getVisibility(), DescriptorVisibilities.LOCAL)) {
            return false;
        }
        return !IrFakeOverrideUtilsKt.isFakeOverride(member);
    }

    private final void fillPlatformExplicitlyExported(IrFile file, final org.jetbrains.kotlin.backend.common.serialization.proto.IrFile.Builder proto) {
        if (backendSpecificExplicitRoot(file)) {
            for (IrDeclaration irDeclaration : file.getDeclarations()) {
                if (!backendSpecificExplicitRootExclusion(irDeclaration)) {
                    proto.addExplicitlyExportedToCompiler(serializeIrSymbol(irDeclaration.getSymbol()));
                }
            }
            return;
        }
        IrVisitorsKt.acceptVoid(file, new IrElementVisitorVoid() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrFileSerializer.fillPlatformExplicitlyExported.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitAnonymousInitializer */
            public /* bridge */ /* synthetic */ Object visitAnonymousInitializer2(IrAnonymousInitializer irAnonymousInitializer, Object obj) {
                visitAnonymousInitializer(irAnonymousInitializer, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer) {
                IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitAnonymousInitializer(IrAnonymousInitializer irAnonymousInitializer, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitAnonymousInitializer(this, irAnonymousInitializer, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlock */
            public /* bridge */ /* synthetic */ Object visitBlock2(org.jetbrains.kotlin.ir.expressions.IrBlock irBlock, Object obj) {
                visitBlock(irBlock, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlock(org.jetbrains.kotlin.ir.expressions.IrBlock irBlock) {
                IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlock(org.jetbrains.kotlin.ir.expressions.IrBlock irBlock, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBlock(this, irBlock, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBlockBody */
            public /* bridge */ /* synthetic */ Object visitBlockBody2(org.jetbrains.kotlin.ir.expressions.IrBlockBody irBlockBody, Object obj) {
                visitBlockBody(irBlockBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlockBody(org.jetbrains.kotlin.ir.expressions.IrBlockBody irBlockBody) {
                IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBlockBody(org.jetbrains.kotlin.ir.expressions.IrBlockBody irBlockBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBlockBody(this, irBlockBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBody */
            public /* bridge */ /* synthetic */ Object visitBody2(IrBody irBody, Object obj) {
                visitBody(irBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBody(IrBody irBody) {
                IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBody(IrBody irBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBody(this, irBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBranch */
            public /* bridge */ /* synthetic */ Object visitBranch2(org.jetbrains.kotlin.ir.expressions.IrBranch irBranch, Object obj) {
                visitBranch(irBranch, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBranch(org.jetbrains.kotlin.ir.expressions.IrBranch irBranch) {
                IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBranch(org.jetbrains.kotlin.ir.expressions.IrBranch irBranch, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBranch(this, irBranch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreak */
            public /* bridge */ /* synthetic */ Object visitBreak2(org.jetbrains.kotlin.ir.expressions.IrBreak irBreak, Object obj) {
                visitBreak(irBreak, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreak(org.jetbrains.kotlin.ir.expressions.IrBreak irBreak) {
                IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreak(org.jetbrains.kotlin.ir.expressions.IrBreak irBreak, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBreak(this, irBreak, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitBreakContinue */
            public /* bridge */ /* synthetic */ Object visitBreakContinue2(IrBreakContinue irBreakContinue, Object obj) {
                visitBreakContinue(irBreakContinue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreakContinue(IrBreakContinue irBreakContinue) {
                IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitBreakContinue(IrBreakContinue irBreakContinue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitBreakContinue(this, irBreakContinue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCall */
            public /* bridge */ /* synthetic */ Object visitCall2(org.jetbrains.kotlin.ir.expressions.IrCall irCall, Object obj) {
                visitCall(irCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCall(org.jetbrains.kotlin.ir.expressions.IrCall irCall) {
                IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCall(org.jetbrains.kotlin.ir.expressions.IrCall irCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitCall(this, irCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitCallableReference(IrCallableReference irCallableReference, Object obj) {
                visitCallableReference((IrCallableReference<?>) irCallableReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCallableReference(IrCallableReference<?> irCallableReference) {
                IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCallableReference(IrCallableReference<?> irCallableReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitCallableReference(this, irCallableReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitCatch */
            public /* bridge */ /* synthetic */ Object visitCatch2(org.jetbrains.kotlin.ir.expressions.IrCatch irCatch, Object obj) {
                visitCatch(irCatch, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCatch(org.jetbrains.kotlin.ir.expressions.IrCatch irCatch) {
                IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitCatch(org.jetbrains.kotlin.ir.expressions.IrCatch irCatch, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitCatch(this, irCatch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClass */
            public /* bridge */ /* synthetic */ Object visitClass2(IrClass irClass, Object obj) {
                visitClass(irClass, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitClass(IrClass irClass, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitClass(this, irClass, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitClassReference */
            public /* bridge */ /* synthetic */ Object visitClassReference2(org.jetbrains.kotlin.ir.expressions.IrClassReference irClassReference, Object obj) {
                visitClassReference(irClassReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitClassReference(org.jetbrains.kotlin.ir.expressions.IrClassReference irClassReference) {
                IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitClassReference(org.jetbrains.kotlin.ir.expressions.IrClassReference irClassReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitClassReference(this, irClassReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitComposite */
            public /* bridge */ /* synthetic */ Object visitComposite2(org.jetbrains.kotlin.ir.expressions.IrComposite irComposite, Object obj) {
                visitComposite(irComposite, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitComposite(org.jetbrains.kotlin.ir.expressions.IrComposite irComposite) {
                IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitComposite(org.jetbrains.kotlin.ir.expressions.IrComposite irComposite, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitComposite(this, irComposite, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitConst(org.jetbrains.kotlin.ir.expressions.IrConst irConst, Object obj) {
                visitConst((org.jetbrains.kotlin.ir.expressions.IrConst<?>) irConst, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConst(org.jetbrains.kotlin.ir.expressions.IrConst<?> irConst) {
                IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConst(org.jetbrains.kotlin.ir.expressions.IrConst<?> irConst, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConst(this, irConst, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantArray */
            public /* bridge */ /* synthetic */ Object visitConstantArray2(IrConstantArray irConstantArray, Object obj) {
                visitConstantArray(irConstantArray, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantArray(IrConstantArray irConstantArray) {
                IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantArray(IrConstantArray irConstantArray, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantArray(this, irConstantArray, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantObject */
            public /* bridge */ /* synthetic */ Object visitConstantObject2(IrConstantObject irConstantObject, Object obj) {
                visitConstantObject(irConstantObject, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantObject(IrConstantObject irConstantObject) {
                IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantObject(IrConstantObject irConstantObject, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantObject(this, irConstantObject, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantPrimitive */
            public /* bridge */ /* synthetic */ Object visitConstantPrimitive2(IrConstantPrimitive irConstantPrimitive, Object obj) {
                visitConstantPrimitive(irConstantPrimitive, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive) {
                IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantPrimitive(IrConstantPrimitive irConstantPrimitive, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantPrimitive(this, irConstantPrimitive, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstantValue */
            public /* bridge */ /* synthetic */ Object visitConstantValue2(IrConstantValue irConstantValue, Object obj) {
                visitConstantValue(irConstantValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantValue(IrConstantValue irConstantValue) {
                IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstantValue(IrConstantValue irConstantValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstantValue(this, irConstantValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructor */
            public /* bridge */ /* synthetic */ Object visitConstructor2(org.jetbrains.kotlin.ir.declarations.IrConstructor irConstructor, Object obj) {
                visitConstructor(irConstructor, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructor(org.jetbrains.kotlin.ir.declarations.IrConstructor irConstructor) {
                IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructor(org.jetbrains.kotlin.ir.declarations.IrConstructor irConstructor, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstructor(this, irConstructor, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitConstructorCall */
            public /* bridge */ /* synthetic */ Object visitConstructorCall2(org.jetbrains.kotlin.ir.expressions.IrConstructorCall irConstructorCall, Object obj) {
                visitConstructorCall(irConstructorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructorCall(org.jetbrains.kotlin.ir.expressions.IrConstructorCall irConstructorCall) {
                IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitConstructorCall(org.jetbrains.kotlin.ir.expressions.IrConstructorCall irConstructorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitConstructorCall(this, irConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContainerExpression */
            public /* bridge */ /* synthetic */ Object visitContainerExpression2(IrContainerExpression irContainerExpression, Object obj) {
                visitContainerExpression(irContainerExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContainerExpression(IrContainerExpression irContainerExpression) {
                IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContainerExpression(IrContainerExpression irContainerExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitContainerExpression(this, irContainerExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitContinue */
            public /* bridge */ /* synthetic */ Object visitContinue2(org.jetbrains.kotlin.ir.expressions.IrContinue irContinue, Object obj) {
                visitContinue(irContinue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContinue(org.jetbrains.kotlin.ir.expressions.IrContinue irContinue) {
                IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitContinue(org.jetbrains.kotlin.ir.expressions.IrContinue irContinue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitContinue(this, irContinue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclaration */
            public /* bridge */ /* synthetic */ Object visitDeclaration2(org.jetbrains.kotlin.ir.declarations.IrDeclarationBase irDeclarationBase, Object obj) {
                visitDeclaration(irDeclarationBase, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclaration(org.jetbrains.kotlin.ir.declarations.IrDeclarationBase irDeclarationBase) {
                IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclaration(org.jetbrains.kotlin.ir.declarations.IrDeclarationBase irDeclarationBase, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, irDeclarationBase, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDeclarationReference */
            public /* bridge */ /* synthetic */ Object visitDeclarationReference2(IrDeclarationReference irDeclarationReference, Object obj) {
                visitDeclarationReference(irDeclarationReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclarationReference(IrDeclarationReference irDeclarationReference) {
                IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDeclarationReference(IrDeclarationReference irDeclarationReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDeclarationReference(this, irDeclarationReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDelegatingConstructorCall */
            public /* bridge */ /* synthetic */ Object visitDelegatingConstructorCall2(org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall irDelegatingConstructorCall, Object obj) {
                visitDelegatingConstructorCall(irDelegatingConstructorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDelegatingConstructorCall(org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall irDelegatingConstructorCall) {
                IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDelegatingConstructorCall(org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall irDelegatingConstructorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDelegatingConstructorCall(this, irDelegatingConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDoWhileLoop */
            public /* bridge */ /* synthetic */ Object visitDoWhileLoop2(IrDoWhileLoop irDoWhileLoop, Object obj) {
                visitDoWhileLoop(irDoWhileLoop, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop) {
                IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDoWhileLoop(IrDoWhileLoop irDoWhileLoop, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDoWhileLoop(this, irDoWhileLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicExpression */
            public /* bridge */ /* synthetic */ Object visitDynamicExpression2(IrDynamicExpression irDynamicExpression, Object obj) {
                visitDynamicExpression(irDynamicExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicExpression(IrDynamicExpression irDynamicExpression) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicExpression(IrDynamicExpression irDynamicExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicExpression(this, irDynamicExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicMemberExpression */
            public /* bridge */ /* synthetic */ Object visitDynamicMemberExpression2(org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression irDynamicMemberExpression, Object obj) {
                visitDynamicMemberExpression(irDynamicMemberExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicMemberExpression(org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression irDynamicMemberExpression) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicMemberExpression(org.jetbrains.kotlin.ir.expressions.IrDynamicMemberExpression irDynamicMemberExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicMemberExpression(this, irDynamicMemberExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitDynamicOperatorExpression */
            public /* bridge */ /* synthetic */ Object visitDynamicOperatorExpression2(org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression irDynamicOperatorExpression, Object obj) {
                visitDynamicOperatorExpression(irDynamicOperatorExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicOperatorExpression(org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression irDynamicOperatorExpression) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitDynamicOperatorExpression(org.jetbrains.kotlin.ir.expressions.IrDynamicOperatorExpression irDynamicOperatorExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitDynamicOperatorExpression(this, irDynamicOperatorExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElement */
            public /* bridge */ /* synthetic */ Object visitElement2(IrElement irElement, Object obj) {
                visitElement(irElement, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElement(IrElement irElement, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitElement(this, irElement, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitElseBranch */
            public /* bridge */ /* synthetic */ Object visitElseBranch2(IrElseBranch irElseBranch, Object obj) {
                visitElseBranch(irElseBranch, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElseBranch(IrElseBranch irElseBranch) {
                IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitElseBranch(IrElseBranch irElseBranch, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitElseBranch(this, irElseBranch, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumConstructorCall */
            public /* bridge */ /* synthetic */ Object visitEnumConstructorCall2(org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall irEnumConstructorCall, Object obj) {
                visitEnumConstructorCall(irEnumConstructorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumConstructorCall(org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall irEnumConstructorCall) {
                IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumConstructorCall(org.jetbrains.kotlin.ir.expressions.IrEnumConstructorCall irEnumConstructorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitEnumConstructorCall(this, irEnumConstructorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitEnumEntry */
            public /* bridge */ /* synthetic */ Object visitEnumEntry2(org.jetbrains.kotlin.ir.declarations.IrEnumEntry irEnumEntry, Object obj) {
                visitEnumEntry(irEnumEntry, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumEntry(org.jetbrains.kotlin.ir.declarations.IrEnumEntry irEnumEntry) {
                IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitEnumEntry(org.jetbrains.kotlin.ir.declarations.IrEnumEntry irEnumEntry, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitEnumEntry(this, irEnumEntry, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorCallExpression */
            public /* bridge */ /* synthetic */ Object visitErrorCallExpression2(org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression irErrorCallExpression, Object obj) {
                visitErrorCallExpression(irErrorCallExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorCallExpression(org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression irErrorCallExpression) {
                IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorCallExpression(org.jetbrains.kotlin.ir.expressions.IrErrorCallExpression irErrorCallExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitErrorCallExpression(this, irErrorCallExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorDeclaration */
            public /* bridge */ /* synthetic */ Object visitErrorDeclaration2(org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration irErrorDeclaration, Object obj) {
                visitErrorDeclaration(irErrorDeclaration, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorDeclaration(org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration irErrorDeclaration) {
                IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorDeclaration(org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration irErrorDeclaration, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitErrorDeclaration(this, irErrorDeclaration, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitErrorExpression */
            public /* bridge */ /* synthetic */ Object visitErrorExpression2(org.jetbrains.kotlin.ir.expressions.IrErrorExpression irErrorExpression, Object obj) {
                visitErrorExpression(irErrorExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorExpression(org.jetbrains.kotlin.ir.expressions.IrErrorExpression irErrorExpression) {
                IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitErrorExpression(org.jetbrains.kotlin.ir.expressions.IrErrorExpression irErrorExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitErrorExpression(this, irErrorExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpression */
            public /* bridge */ /* synthetic */ Object visitExpression2(org.jetbrains.kotlin.ir.expressions.IrExpression irExpression, Object obj) {
                visitExpression(irExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpression(org.jetbrains.kotlin.ir.expressions.IrExpression irExpression) {
                IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpression(org.jetbrains.kotlin.ir.expressions.IrExpression irExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitExpression(this, irExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExpressionBody */
            public /* bridge */ /* synthetic */ Object visitExpressionBody2(IrExpressionBody irExpressionBody, Object obj) {
                visitExpressionBody(irExpressionBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpressionBody(IrExpressionBody irExpressionBody) {
                IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExpressionBody(IrExpressionBody irExpressionBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitExpressionBody(this, irExpressionBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitExternalPackageFragment */
            public /* bridge */ /* synthetic */ Object visitExternalPackageFragment2(IrExternalPackageFragment irExternalPackageFragment, Object obj) {
                visitExternalPackageFragment(irExternalPackageFragment, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment) {
                IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitExternalPackageFragment(IrExternalPackageFragment irExternalPackageFragment, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitExternalPackageFragment(this, irExternalPackageFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitField */
            public /* bridge */ /* synthetic */ Object visitField2(IrField irField, Object obj) {
                visitField(irField, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitField(IrField irField) {
                IrElementVisitorVoid.DefaultImpls.visitField(this, irField);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitField(IrField irField, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitField(this, irField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFieldAccess */
            public /* bridge */ /* synthetic */ Object visitFieldAccess2(IrFieldAccessExpression irFieldAccessExpression, Object obj) {
                visitFieldAccess(irFieldAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFieldAccess(IrFieldAccessExpression irFieldAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFieldAccess(this, irFieldAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFile */
            public /* bridge */ /* synthetic */ Object visitFile2(IrFile irFile, Object obj) {
                visitFile(irFile, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFile(IrFile irFile) {
                IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFile(IrFile irFile, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFile(this, irFile, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunction */
            public /* bridge */ /* synthetic */ Object visitFunction2(IrFunction irFunction, Object obj) {
                visitFunction(irFunction, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunction(IrFunction irFunction, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunction(this, irFunction, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionAccess */
            public /* bridge */ /* synthetic */ Object visitFunctionAccess2(IrFunctionAccessExpression irFunctionAccessExpression, Object obj) {
                visitFunctionAccess(irFunctionAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionAccess(IrFunctionAccessExpression irFunctionAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionAccess(this, irFunctionAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionExpression */
            public /* bridge */ /* synthetic */ Object visitFunctionExpression2(org.jetbrains.kotlin.ir.expressions.IrFunctionExpression irFunctionExpression, Object obj) {
                visitFunctionExpression(irFunctionExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionExpression(org.jetbrains.kotlin.ir.expressions.IrFunctionExpression irFunctionExpression) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionExpression(org.jetbrains.kotlin.ir.expressions.IrFunctionExpression irFunctionExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionExpression(this, irFunctionExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitFunctionReference */
            public /* bridge */ /* synthetic */ Object visitFunctionReference2(org.jetbrains.kotlin.ir.expressions.IrFunctionReference irFunctionReference, Object obj) {
                visitFunctionReference(irFunctionReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionReference(org.jetbrains.kotlin.ir.expressions.IrFunctionReference irFunctionReference) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunctionReference(org.jetbrains.kotlin.ir.expressions.IrFunctionReference irFunctionReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitFunctionReference(this, irFunctionReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetClass */
            public /* bridge */ /* synthetic */ Object visitGetClass2(org.jetbrains.kotlin.ir.expressions.IrGetClass irGetClass, Object obj) {
                visitGetClass(irGetClass, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetClass(org.jetbrains.kotlin.ir.expressions.IrGetClass irGetClass) {
                IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetClass(org.jetbrains.kotlin.ir.expressions.IrGetClass irGetClass, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetClass(this, irGetClass, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetEnumValue */
            public /* bridge */ /* synthetic */ Object visitGetEnumValue2(org.jetbrains.kotlin.ir.expressions.IrGetEnumValue irGetEnumValue, Object obj) {
                visitGetEnumValue(irGetEnumValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetEnumValue(org.jetbrains.kotlin.ir.expressions.IrGetEnumValue irGetEnumValue) {
                IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetEnumValue(org.jetbrains.kotlin.ir.expressions.IrGetEnumValue irGetEnumValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetEnumValue(this, irGetEnumValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetField */
            public /* bridge */ /* synthetic */ Object visitGetField2(org.jetbrains.kotlin.ir.expressions.IrGetField irGetField, Object obj) {
                visitGetField(irGetField, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetField(org.jetbrains.kotlin.ir.expressions.IrGetField irGetField) {
                IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetField(org.jetbrains.kotlin.ir.expressions.IrGetField irGetField, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetField(this, irGetField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetObjectValue */
            public /* bridge */ /* synthetic */ Object visitGetObjectValue2(IrGetObjectValue irGetObjectValue, Object obj) {
                visitGetObjectValue(irGetObjectValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetObjectValue(IrGetObjectValue irGetObjectValue) {
                IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetObjectValue(IrGetObjectValue irGetObjectValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetObjectValue(this, irGetObjectValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitGetValue */
            public /* bridge */ /* synthetic */ Object visitGetValue2(org.jetbrains.kotlin.ir.expressions.IrGetValue irGetValue, Object obj) {
                visitGetValue(irGetValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetValue(org.jetbrains.kotlin.ir.expressions.IrGetValue irGetValue) {
                IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitGetValue(org.jetbrains.kotlin.ir.expressions.IrGetValue irGetValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitGetValue(this, irGetValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitInstanceInitializerCall */
            public /* bridge */ /* synthetic */ Object visitInstanceInitializerCall2(org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall irInstanceInitializerCall, Object obj) {
                visitInstanceInitializerCall(irInstanceInitializerCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitInstanceInitializerCall(org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall irInstanceInitializerCall) {
                IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitInstanceInitializerCall(org.jetbrains.kotlin.ir.expressions.IrInstanceInitializerCall irInstanceInitializerCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitInstanceInitializerCall(this, irInstanceInitializerCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedProperty */
            public /* bridge */ /* synthetic */ Object visitLocalDelegatedProperty2(org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty irLocalDelegatedProperty, Object obj) {
                visitLocalDelegatedProperty(irLocalDelegatedProperty, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedProperty(org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty irLocalDelegatedProperty) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedProperty(org.jetbrains.kotlin.ir.declarations.IrLocalDelegatedProperty irLocalDelegatedProperty, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedProperty(this, irLocalDelegatedProperty, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLocalDelegatedPropertyReference */
            public /* bridge */ /* synthetic */ Object visitLocalDelegatedPropertyReference2(org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Object obj) {
                visitLocalDelegatedPropertyReference(irLocalDelegatedPropertyReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedPropertyReference(org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLocalDelegatedPropertyReference(org.jetbrains.kotlin.ir.expressions.IrLocalDelegatedPropertyReference irLocalDelegatedPropertyReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitLocalDelegatedPropertyReference(this, irLocalDelegatedPropertyReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitLoop */
            public /* bridge */ /* synthetic */ Object visitLoop2(IrLoop irLoop, Object obj) {
                visitLoop(irLoop, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLoop(IrLoop irLoop) {
                IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitLoop(IrLoop irLoop, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitLoop(this, irLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            public /* bridge */ /* synthetic */ Object visitMemberAccess(IrMemberAccessExpression irMemberAccessExpression, Object obj) {
                visitMemberAccess((IrMemberAccessExpression<?>) irMemberAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitMemberAccess(IrMemberAccessExpression<?> irMemberAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitMemberAccess(this, irMemberAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitModuleFragment */
            public /* bridge */ /* synthetic */ Object visitModuleFragment2(IrModuleFragment irModuleFragment, Object obj) {
                visitModuleFragment(irModuleFragment, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitModuleFragment(IrModuleFragment irModuleFragment) {
                IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitModuleFragment(IrModuleFragment irModuleFragment, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitModuleFragment(this, irModuleFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPackageFragment */
            public /* bridge */ /* synthetic */ Object visitPackageFragment2(IrPackageFragment irPackageFragment, Object obj) {
                visitPackageFragment(irPackageFragment, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPackageFragment(IrPackageFragment irPackageFragment) {
                IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPackageFragment(IrPackageFragment irPackageFragment, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitPackageFragment(this, irPackageFragment, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitProperty */
            public /* bridge */ /* synthetic */ Object visitProperty2(IrProperty irProperty, Object obj) {
                visitProperty(irProperty, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitProperty(IrProperty irProperty, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitProperty(this, irProperty, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitPropertyReference */
            public /* bridge */ /* synthetic */ Object visitPropertyReference2(org.jetbrains.kotlin.ir.expressions.IrPropertyReference irPropertyReference, Object obj) {
                visitPropertyReference(irPropertyReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPropertyReference(org.jetbrains.kotlin.ir.expressions.IrPropertyReference irPropertyReference) {
                IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitPropertyReference(org.jetbrains.kotlin.ir.expressions.IrPropertyReference irPropertyReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitPropertyReference(this, irPropertyReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitRawFunctionReference */
            public /* bridge */ /* synthetic */ Object visitRawFunctionReference2(IrRawFunctionReference irRawFunctionReference, Object obj) {
                visitRawFunctionReference(irRawFunctionReference, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference) {
                IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitRawFunctionReference(IrRawFunctionReference irRawFunctionReference, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitRawFunctionReference(this, irRawFunctionReference, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitReturn */
            public /* bridge */ /* synthetic */ Object visitReturn2(IrReturn irReturn, Object obj) {
                visitReturn(irReturn, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitReturn(IrReturn irReturn) {
                IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitReturn(IrReturn irReturn, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitReturn(this, irReturn, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitScript */
            public /* bridge */ /* synthetic */ Object visitScript2(IrScript irScript, Object obj) {
                visitScript(irScript, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitScript(IrScript irScript) {
                IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitScript(IrScript irScript, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitScript(this, irScript, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetField */
            public /* bridge */ /* synthetic */ Object visitSetField2(org.jetbrains.kotlin.ir.expressions.IrSetField irSetField, Object obj) {
                visitSetField(irSetField, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetField(org.jetbrains.kotlin.ir.expressions.IrSetField irSetField) {
                IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetField(org.jetbrains.kotlin.ir.expressions.IrSetField irSetField, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSetField(this, irSetField, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSetValue */
            public /* bridge */ /* synthetic */ Object visitSetValue2(org.jetbrains.kotlin.ir.expressions.IrSetValue irSetValue, Object obj) {
                visitSetValue(irSetValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetValue(org.jetbrains.kotlin.ir.expressions.IrSetValue irSetValue) {
                IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSetValue(org.jetbrains.kotlin.ir.expressions.IrSetValue irSetValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSetValue(this, irSetValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSimpleFunction */
            public /* bridge */ /* synthetic */ Object visitSimpleFunction2(IrSimpleFunction irSimpleFunction, Object obj) {
                visitSimpleFunction(irSimpleFunction, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSimpleFunction(IrSimpleFunction irSimpleFunction) {
                IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSimpleFunction(IrSimpleFunction irSimpleFunction, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSimpleFunction(this, irSimpleFunction, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSingletonReference */
            public /* bridge */ /* synthetic */ Object visitSingletonReference2(IrGetSingletonValue irGetSingletonValue, Object obj) {
                visitSingletonReference(irGetSingletonValue, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue) {
                IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSingletonReference(IrGetSingletonValue irGetSingletonValue, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSingletonReference(this, irGetSingletonValue, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSpreadElement */
            public /* bridge */ /* synthetic */ Object visitSpreadElement2(org.jetbrains.kotlin.ir.expressions.IrSpreadElement irSpreadElement, Object obj) {
                visitSpreadElement(irSpreadElement, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSpreadElement(org.jetbrains.kotlin.ir.expressions.IrSpreadElement irSpreadElement) {
                IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSpreadElement(org.jetbrains.kotlin.ir.expressions.IrSpreadElement irSpreadElement, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSpreadElement(this, irSpreadElement, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitStringConcatenation */
            public /* bridge */ /* synthetic */ Object visitStringConcatenation2(IrStringConcatenation irStringConcatenation, Object obj) {
                visitStringConcatenation(irStringConcatenation, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitStringConcatenation(IrStringConcatenation irStringConcatenation) {
                IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitStringConcatenation(IrStringConcatenation irStringConcatenation, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitStringConcatenation(this, irStringConcatenation, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspendableExpression */
            public /* bridge */ /* synthetic */ Object visitSuspendableExpression2(IrSuspendableExpression irSuspendableExpression, Object obj) {
                visitSuspendableExpression(irSuspendableExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression) {
                IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspendableExpression(IrSuspendableExpression irSuspendableExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSuspendableExpression(this, irSuspendableExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSuspensionPoint */
            public /* bridge */ /* synthetic */ Object visitSuspensionPoint2(IrSuspensionPoint irSuspensionPoint, Object obj) {
                visitSuspensionPoint(irSuspensionPoint, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint) {
                IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSuspensionPoint(IrSuspensionPoint irSuspensionPoint, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSuspensionPoint(this, irSuspensionPoint, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitSyntheticBody */
            public /* bridge */ /* synthetic */ Object visitSyntheticBody2(org.jetbrains.kotlin.ir.expressions.IrSyntheticBody irSyntheticBody, Object obj) {
                visitSyntheticBody(irSyntheticBody, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSyntheticBody(org.jetbrains.kotlin.ir.expressions.IrSyntheticBody irSyntheticBody) {
                IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitSyntheticBody(org.jetbrains.kotlin.ir.expressions.IrSyntheticBody irSyntheticBody, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitSyntheticBody(this, irSyntheticBody, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitThrow */
            public /* bridge */ /* synthetic */ Object visitThrow2(org.jetbrains.kotlin.ir.expressions.IrThrow irThrow, Object obj) {
                visitThrow(irThrow, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitThrow(org.jetbrains.kotlin.ir.expressions.IrThrow irThrow) {
                IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitThrow(org.jetbrains.kotlin.ir.expressions.IrThrow irThrow, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitThrow(this, irThrow, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTry */
            public /* bridge */ /* synthetic */ Object visitTry2(org.jetbrains.kotlin.ir.expressions.IrTry irTry, Object obj) {
                visitTry(irTry, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTry(org.jetbrains.kotlin.ir.expressions.IrTry irTry) {
                IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTry(org.jetbrains.kotlin.ir.expressions.IrTry irTry, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTry(this, irTry, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeAlias */
            public /* bridge */ /* synthetic */ Object visitTypeAlias2(org.jetbrains.kotlin.ir.declarations.IrTypeAlias irTypeAlias, Object obj) {
                visitTypeAlias(irTypeAlias, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeAlias(org.jetbrains.kotlin.ir.declarations.IrTypeAlias irTypeAlias) {
                IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeAlias(org.jetbrains.kotlin.ir.declarations.IrTypeAlias irTypeAlias, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTypeAlias(this, irTypeAlias, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeOperator */
            public /* bridge */ /* synthetic */ Object visitTypeOperator2(IrTypeOperatorCall irTypeOperatorCall, Object obj) {
                visitTypeOperator(irTypeOperatorCall, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall) {
                IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeOperator(IrTypeOperatorCall irTypeOperatorCall, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTypeOperator(this, irTypeOperatorCall, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitTypeParameter */
            public /* bridge */ /* synthetic */ Object visitTypeParameter2(org.jetbrains.kotlin.ir.declarations.IrTypeParameter irTypeParameter, Object obj) {
                visitTypeParameter(irTypeParameter, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeParameter(org.jetbrains.kotlin.ir.declarations.IrTypeParameter irTypeParameter) {
                IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitTypeParameter(org.jetbrains.kotlin.ir.declarations.IrTypeParameter irTypeParameter, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitTypeParameter(this, irTypeParameter, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueAccess */
            public /* bridge */ /* synthetic */ Object visitValueAccess2(IrValueAccessExpression irValueAccessExpression, Object obj) {
                visitValueAccess(irValueAccessExpression, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueAccess(IrValueAccessExpression irValueAccessExpression) {
                IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueAccess(IrValueAccessExpression irValueAccessExpression, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitValueAccess(this, irValueAccessExpression, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitValueParameter */
            public /* bridge */ /* synthetic */ Object visitValueParameter2(org.jetbrains.kotlin.ir.declarations.IrValueParameter irValueParameter, Object obj) {
                visitValueParameter(irValueParameter, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueParameter(org.jetbrains.kotlin.ir.declarations.IrValueParameter irValueParameter) {
                IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitValueParameter(org.jetbrains.kotlin.ir.declarations.IrValueParameter irValueParameter, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitValueParameter(this, irValueParameter, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVararg */
            public /* bridge */ /* synthetic */ Object visitVararg2(org.jetbrains.kotlin.ir.expressions.IrVararg irVararg, Object obj) {
                visitVararg(irVararg, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVararg(org.jetbrains.kotlin.ir.expressions.IrVararg irVararg) {
                IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVararg(org.jetbrains.kotlin.ir.expressions.IrVararg irVararg, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitVararg(this, irVararg, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitVariable */
            public /* bridge */ /* synthetic */ Object visitVariable2(org.jetbrains.kotlin.ir.declarations.IrVariable irVariable, Object obj) {
                visitVariable(irVariable, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVariable(org.jetbrains.kotlin.ir.declarations.IrVariable irVariable) {
                IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitVariable(org.jetbrains.kotlin.ir.declarations.IrVariable irVariable, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitVariable(this, irVariable, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhen */
            public /* bridge */ /* synthetic */ Object visitWhen2(org.jetbrains.kotlin.ir.expressions.IrWhen irWhen, Object obj) {
                visitWhen(irWhen, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhen(org.jetbrains.kotlin.ir.expressions.IrWhen irWhen) {
                IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhen(org.jetbrains.kotlin.ir.expressions.IrWhen irWhen, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitWhen(this, irWhen, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitor
            /* JADX INFO: renamed from: visitWhileLoop */
            public /* bridge */ /* synthetic */ Object visitWhileLoop2(IrWhileLoop irWhileLoop, Object obj) {
                visitWhileLoop(irWhileLoop, (Void) obj);
                return Unit.INSTANCE;
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhileLoop(IrWhileLoop irWhileLoop) {
                IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitWhileLoop(IrWhileLoop irWhileLoop, Void r2) {
                IrElementVisitorVoid.DefaultImpls.visitWhileLoop(this, irWhileLoop, r2);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            /* JADX INFO: renamed from: visitElement */
            public void mo6335visitElement(IrElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
                IrVisitorsKt.acceptChildrenVoid(element, this);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitFunction(IrFunction declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                if (IrFileSerializer.this.backendSpecificExplicitRoot(declaration)) {
                    proto.addExplicitlyExportedToCompiler(IrFileSerializer.this.serializeIrSymbol(declaration.getSymbol()));
                }
                IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, declaration);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            /* JADX INFO: renamed from: visitClass */
            public void mo6112visitClass(IrClass declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                if (IrFileSerializer.this.backendSpecificExplicitRoot(declaration)) {
                    proto.addExplicitlyExportedToCompiler(IrFileSerializer.this.serializeIrSymbol(declaration.getSymbol()));
                }
                IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, declaration);
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
            public void visitProperty(IrProperty declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                if (IrFileSerializer.this.backendSpecificExplicitRoot(declaration)) {
                    proto.addExplicitlyExportedToCompiler(IrFileSerializer.this.serializeIrSymbol(declaration.getSymbol()));
                }
                IrElementVisitorVoid.DefaultImpls.visitDeclaration(this, declaration);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final SerializedIrFile serializeIrFile(final IrFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        this.declarationTable.inFile(file, new Function0<Unit>() { // from class: org.jetbrains.kotlin.backend.common.serialization.IrFileSerializer.serializeIrFile.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* JADX WARN: Type inference failed for: r1v1, types: [T, org.jetbrains.kotlin.library.SerializedIrFile] */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                objectRef.element = this.serializeIrFileImpl(file);
            }
        });
        T t = objectRef.element;
        Intrinsics.checkNotNull(t);
        return (SerializedIrFile) t;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SerializedIrFile serializeIrFileImpl(IrFile file) {
        Iterable<IrProperty> iterableEmptyList;
        IrFieldSymbol symbol;
        int iProtoIdSignature;
        ArrayList arrayList = new ArrayList();
        org.jetbrains.kotlin.backend.common.serialization.proto.IrFile.Builder fileEntry = org.jetbrains.kotlin.backend.common.serialization.proto.IrFile.newBuilder().setFileEntry(serializeFileEntry(file.getFileEntry()));
        String strAsString = file.getPackageFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        org.jetbrains.kotlin.backend.common.serialization.proto.IrFile.Builder builderAddAllAnnotation = fileEntry.addAllFqName(serializeFqName(strAsString)).addAllAnnotation(serializeAnnotations(file.getAnnotations()));
        Iterator<T> it = file.getDeclarations().iterator();
        while (true) {
            if (it.hasNext()) {
                IrDeclaration irDeclaration = (IrDeclaration) it.next();
                if (!this.skipExpects || !LegacyDescriptorUtilsKt.isExpectMember(irDeclaration.getDescriptor()) || LegacyDescriptorUtilsKt.isSerializableExpectClass(irDeclaration.getDescriptor())) {
                    byte[] byteArray = serializeDeclaration(irDeclaration).toByteArray();
                    org.jetbrains.kotlin.ir.util.IdSignature idSignatureSignatureByDeclaration = this.declarationTable.signatureByDeclaration(irDeclaration, this.compatibilityMode.getOldSignatures());
                    if (!Intrinsics.areEqual(idSignatureSignatureByDeclaration, idSignatureSignatureByDeclaration.topLevelSignature())) {
                        throw new IllegalArgumentException(("IdSig: " + idSignatureSignatureByDeclaration + "\ntopLevel: " + idSignatureSignatureByDeclaration.topLevelSignature()).toString());
                    }
                    if (idSignatureSignatureByDeclaration.isPackageSignature()) {
                        throw new IllegalArgumentException(("IsSig: " + idSignatureSignatureByDeclaration + "\nDeclaration: " + RenderIrElementKt.render$default(irDeclaration, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                    }
                    Integer num = this.protoIdSignatureMap.get(idSignatureSignatureByDeclaration);
                    if (num != null) {
                        iProtoIdSignature = num.intValue();
                    } else {
                        if (!(irDeclaration instanceof org.jetbrains.kotlin.ir.declarations.IrErrorDeclaration)) {
                            throw new IllegalStateException(("Not found ID for " + idSignatureSignatureByDeclaration + " (" + RenderIrElementKt.render$default(irDeclaration, (DumpIrTreeOptions) null, 1, (Object) null) + ')').toString());
                        }
                        iProtoIdSignature = protoIdSignature(idSignatureSignatureByDeclaration);
                    }
                    String strRender$default = IdSignatureRendererKt.render$default(idSignatureSignatureByDeclaration, null, 1, null);
                    Intrinsics.checkNotNull(byteArray);
                    arrayList.add(new SerializedDeclaration(iProtoIdSignature, strRender$default, byteArray));
                    builderAddAllAnnotation.addDeclarationId(iProtoIdSignature);
                }
            } else {
                List<IrDeclaration> declarations = file.getDeclarations();
                if (declarations.isEmpty()) {
                    iterableEmptyList = CollectionsKt.emptyList();
                } else {
                    SmartList smartList = new SmartList();
                    for (Object obj : declarations) {
                        if (obj instanceof IrProperty) {
                            IrProperty irProperty = (IrProperty) obj;
                            IrField backingField = irProperty.getBackingField();
                            if ((backingField != null ? backingField.getInitializer() : null) != null && keepOrderOfProperties(irProperty)) {
                                smartList.add(obj);
                            }
                        }
                    }
                    iterableEmptyList = smartList;
                }
                for (IrProperty irProperty2 : iterableEmptyList) {
                    IrField backingField2 = irProperty2.getBackingField();
                    if (backingField2 == null || (symbol = backingField2.getSymbol()) == null) {
                        throw new IllegalStateException(("Not found ID " + RenderIrElementKt.render$default(irProperty2, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
                    }
                    builderAddAllAnnotation.addExplicitlyExportedToCompiler(serializeIrSymbol(symbol));
                }
                Intrinsics.checkNotNull(builderAddAllAnnotation);
                fillPlatformExplicitlyExported(file, builderAddAllAnnotation);
                serializeExpectActualSubstitutionTable(builderAddAllAnnotation);
                byte[] byteArray2 = builderAddAllAnnotation.build().toByteArray();
                Intrinsics.checkNotNullExpressionValue(byteArray2, "toByteArray(...)");
                String strAsString2 = file.getPackageFqName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                String path = IrDeclarationsKt.getPath(file);
                ArrayList<IrType> arrayList2 = this.protoTypeArray;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                Iterator<T> it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    arrayList3.add(((IrType) it2.next()).toByteArray());
                }
                byte[] bArrWriteIntoMemory = new IrMemoryArrayWriter(arrayList3).writeIntoMemory();
                ArrayList<IdSignature> arrayList4 = this.protoIdSignatureArray;
                ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList4, 10));
                Iterator<T> it3 = arrayList4.iterator();
                while (it3.hasNext()) {
                    arrayList5.add(((IdSignature) it3.next()).toByteArray());
                }
                byte[] bArrWriteIntoMemory2 = new IrMemoryArrayWriter(arrayList5).writeIntoMemory();
                byte[] bArrWriteIntoMemory3 = new IrMemoryStringWriter(this.protoStringArray).writeIntoMemory();
                List<XStatementOrExpression> list = this.protoBodyArray;
                ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator<T> it4 = list.iterator();
                while (it4.hasNext()) {
                    arrayList6.add(((XStatementOrExpression) it4.next()).toByteArray());
                }
                byte[] bArrWriteIntoMemory4 = new IrMemoryArrayWriter(arrayList6).writeIntoMemory();
                byte[] bArrWriteIntoMemory5 = new IrMemoryDeclarationWriter(arrayList).writeIntoMemory();
                byte[] bArrWriteIntoMemory6 = new IrMemoryStringWriter(this.protoDebugInfoArray).writeIntoMemory();
                FileBackendSpecificMetadata fileBackendSpecificMetadataBackendSpecificMetadata = backendSpecificMetadata(file);
                return new SerializedIrFile(byteArray2, strAsString2, path, bArrWriteIntoMemory, bArrWriteIntoMemory2, bArrWriteIntoMemory3, bArrWriteIntoMemory4, bArrWriteIntoMemory5, bArrWriteIntoMemory6, fileBackendSpecificMetadataBackendSpecificMetadata != null ? fileBackendSpecificMetadataBackendSpecificMetadata.toByteArray() : null);
            }
        }
    }

    private final String tryMatchPath(String fileName) {
        File file = new File(fileName);
        Path path = file.toPath();
        for (String str : this.sourceBaseDirs) {
            if (path.startsWith(str)) {
                return FilesKt.toRelativeString(file, new File(str));
            }
        }
        return null;
    }

    private final String matchAndNormalizeFilePath(IrFileEntry irFileEntry) {
        String strTryMatchPath = tryMatchPath(irFileEntry.getName());
        if (strTryMatchPath != null) {
            return StringsKt.replace$default(strTryMatchPath, File.separatorChar, '/', false, 4, (Object) null);
        }
        return !this.normalizeAbsolutePaths ? irFileEntry.getName() : StringsKt.replace$default(irFileEntry.getName(), File.separatorChar, '/', false, 4, (Object) null);
    }

    private final void serializeExpectActualSubstitutionTable(org.jetbrains.kotlin.backend.common.serialization.proto.IrFile.Builder proto) {
        if (this.skipExpects) {
            return;
        }
        for (Map.Entry<DeclarationDescriptor, IrSymbol> entry : this.expectActualTable.getTable().entrySet()) {
            DeclarationDescriptor key = entry.getKey();
            IrSymbol value = entry.getValue();
            IrSymbol irSymbol = this.expectDescriptorToSymbol.get(key);
            if (irSymbol == null) {
                throw new IllegalStateException(("Could not find expect symbol for expect descriptor " + key).toString());
            }
            proto.addActual(Actual.newBuilder().setExpectSymbol(serializeIrSymbol(irSymbol)).setActualSymbol(serializeIrSymbol(value)).build());
        }
    }
}
