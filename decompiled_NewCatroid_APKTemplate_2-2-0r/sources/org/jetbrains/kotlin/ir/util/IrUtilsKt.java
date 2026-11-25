package org.jetbrains.kotlin.ir.util;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.UnsignedType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.KotlinRetention;
import org.jetbrains.kotlin.ir.DeepCopyIrTreeWithDeclarationsKt;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.builders.ExpressionHelpersKt;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrTypeParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrOverridableMember;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrPossiblyExternalDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrBranch;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstKind;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrContainerExpression;
import org.jetbrains.kotlin.ir.expressions.IrElseBranch;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.expressions.IrExpressionsKt;
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression;
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrGetField;
import org.jetbrains.kotlin.ir.expressions.IrGetObjectValue;
import org.jetbrains.kotlin.ir.expressions.IrGetSingletonValue;
import org.jetbrains.kotlin.ir.expressions.IrGetValue;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrPropertyReference;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.expressions.IrSyntheticBody;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperator;
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrErrorExpressionImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetEnumValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetFieldImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetObjectValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrInstanceInitializerCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrTypeOperatorCallImpl;
import org.jetbrains.kotlin.ir.overrides.IrOverridingUtil;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFieldSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrStarProjection;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContextKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.SimpleTypeNullability;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.name.StandardClassIds;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.kotlin.utils.DFS;
import org.jetbrains.kotlin.utils.MemoryOptimizedCollectionUtilKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000ª\u0004\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a;\u0010\u008e\u0001\u001a\u00030\u008f\u0001\"\u000e\b\u0000\u0010\u0090\u0001*\u0007\u0012\u0002\b\u00030\u0091\u00012\b\u0010\u0092\u0001\u001a\u0003H\u0090\u00012\u000f\u0010\u0093\u0001\u001a\n\u0012\u0005\u0012\u0003H\u0090\u00010\u0094\u0001H\u0002¢\u0006\u0003\u0010\u0095\u0001\u001a\u0014\u0010\u0096\u0001\u001a\u00030\u0097\u00012\n\u0010\u0098\u0001\u001a\u0005\u0018\u00010\u0099\u0001\u001a\u0014\u0010\u009a\u0001\u001a\u00030\u0097\u00012\n\u0010\u0098\u0001\u001a\u0005\u0018\u00010\u0099\u0001\u001aJ\u0010\u009b\u0001\u001a\u00020c2\b\u0010\u009c\u0001\u001a\u00030\u008a\u00012\u0007\u0010\u009d\u0001\u001a\u00020:2\t\b\u0002\u0010\u009e\u0001\u001a\u00020A2\t\b\u0002\u0010\u009f\u0001\u001a\u00020A2\u000b\b\u0002\u0010 \u0001\u001a\u0004\u0018\u00010\u001c2\u000b\b\u0002\u0010¡\u0001\u001a\u0004\u0018\u00010\u0012\u001aJ\u0010\u009b\u0001\u001a\u00020c2\b\u0010\u009c\u0001\u001a\u00030\u008a\u00012\u0007\u0010¢\u0001\u001a\u00020<2\t\b\u0002\u0010\u009e\u0001\u001a\u00020A2\t\b\u0002\u0010\u009f\u0001\u001a\u00020A2\u000b\b\u0002\u0010 \u0001\u001a\u0004\u0018\u00010\u001c2\u000b\b\u0002\u0010¡\u0001\u001a\u0004\u0018\u00010\u0012\u001a1\u0010£\u0001\u001a\u00030¤\u00012\b\u0010\u009c\u0001\u001a\u00030\u008a\u00012\u0007\u0010¢\u0001\u001a\u00020\u001b2\t\b\u0002\u0010\u009e\u0001\u001a\u00020A2\t\b\u0002\u0010¥\u0001\u001a\u00020A\u001a\u0011\u0010¦\u0001\u001a\u00020A2\b\u0010§\u0001\u001a\u00030¨\u0001\u001a'\u0010©\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u0089\u0001\u0012\u0004\u0012\u00020\u00120\u0088\u00012\u0007\u0010ª\u0001\u001a\u00020\u000e2\u0007\u0010«\u0001\u001a\u00020\u000e\u001a*\u0010¬\u0001\u001a\u00030\u008f\u0001*\u0007\u0012\u0002\b\u00030\u00ad\u00012\u0015\u0010®\u0001\u001a\u0010\u0012\u0005\u0012\u00030¯\u0001\u0012\u0004\u0012\u00020F0\u0088\u0001H\u0007\u001a\u0015\u0010°\u0001\u001a\u00030\u008f\u0001*\u00020|2\u0007\u0010±\u0001\u001a\u00020/\u001a\u001c\u0010²\u0001\u001a\u00030\u008f\u0001*\u00020\u00032\u000e\u0010³\u0001\u001a\t\u0012\u0004\u0012\u00020\u00020´\u0001\u001a9\u0010µ\u0001\u001a\u00030\u008f\u0001*\u00020\u00182\b\u0010¶\u0001\u001a\u00030·\u00012\u0010\b\u0002\u0010¸\u0001\u001a\t\u0012\u0005\u0012\u00030¹\u00010\u00012\u000f\b\u0002\u0010º\u0001\u001a\b\u0012\u0004\u0012\u00020g0\u0001\u001a7\u0010»\u0001\u001a\u00020\u0013*\u00020\u00182\u0007\u0010¼\u0001\u001a\u00020\u00132\b\u0010½\u0001\u001a\u00030¾\u00012\t\b\u0002\u0010¿\u0001\u001a\u00020A2\f\b\u0002\u0010À\u0001\u001a\u0005\u0018\u00010Á\u0001\u001a4\u0010Â\u0001\u001a\t\u0012\u0005\u0012\u0003H\u0090\u00010\u0001\"\u000e\b\u0000\u0010\u0090\u0001*\u0007\u0012\u0002\b\u00030\u0091\u0001*\u0003H\u0090\u00012\t\b\u0002\u0010Ã\u0001\u001a\u00020A¢\u0006\u0003\u0010Ä\u0001\u001a\u001f\u0010Å\u0001\u001a\u00020F*\u00020F2\b\u0010Æ\u0001\u001a\u00030¾\u00012\b\u0010¶\u0001\u001a\u00030·\u0001\u001a(\u0010Ç\u0001\u001a\u00020F*\u00020F2\u0007\u0010È\u0001\u001a\u00020\u00122\b\u0010½\u0001\u001a\u00030¾\u00012\b\u0010¶\u0001\u001a\u00030·\u0001\u001a2\u0010É\u0001\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0001*\u00020:2\r\u0010Ê\u0001\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00012\u000e\u0010Ë\u0001\u001a\t\u0012\u0004\u0012\u00020\u000b0Ì\u0001H\u0002\u001a\u0013\u0010Í\u0001\u001a\t\u0012\u0005\u0012\u00030¤\u00010\u0001*\u00030Î\u0001\u001a\u0017\u0010Ï\u0001\u001a\u00030\u008f\u0001*\u00030Ð\u00012\b\u0010Ñ\u0001\u001a\u00030Î\u0001\u001a0\u0010Ò\u0001\u001a\t\u0012\u0005\u0012\u00030¤\u00010\u0001*\u00030Î\u00012\u001b\u0010Ó\u0001\u001a\u0016\u0012\u0005\u0012\u00030¤\u0001\u0012\u0004\u0012\u00020A0Ô\u0001¢\u0006\u0003\bÕ\u0001\u001a\u0015\u0010Ö\u0001\u001a\u00030\u008f\u0001*\u00020\u00032\u0007\u0010×\u0001\u001a\u00020\u0003\u001a,\u0010Ø\u0001\u001a\u00030\u008f\u0001*\u00020\u00032\u0007\u0010×\u0001\u001a\u00020\u00032\u0015\u0010Ù\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u0089\u0001\u0012\u0004\u0012\u00020\u00120\u0088\u0001\u001a-\u0010Ú\u0001\u001a\u00030\u008f\u0001*\u00020\u000b2\u0007\u0010Ñ\u0001\u001a\u00020\u000b2\u0014\u0010Û\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\u0088\u0001H\u0002\u001a¬\u0001\u0010Ü\u0001\u001a\u00020\u0002*\u00020\u00022\u0007\u0010Ý\u0001\u001a\u00020\u00032\n\b\u0002\u0010À\u0001\u001a\u00030Á\u00012\t\b\u0002\u0010Þ\u0001\u001a\u00020\u00072\t\b\u0002\u0010ß\u0001\u001a\u00020\u00072\t\b\u0002\u0010à\u0001\u001a\u00020\u00072\n\b\u0002\u0010á\u0001\u001a\u00030â\u00012\u0016\b\u0002\u0010ã\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\u0088\u00012\t\b\u0002\u0010ä\u0001\u001a\u00020\u00122\u000b\b\u0002\u0010å\u0001\u001a\u0004\u0018\u00010\u00122\f\b\u0002\u0010æ\u0001\u001a\u0005\u0018\u00010ç\u00012\t\b\u0002\u0010è\u0001\u001a\u00020A2\t\b\u0002\u0010é\u0001\u001a\u00020A2\t\b\u0002\u0010ê\u0001\u001a\u00020A\u001a+\u0010ë\u0001\u001a\u00020\u000b*\u00020\u000b2\u0007\u0010ì\u0001\u001a\u00020\u000e2\t\b\u0002\u0010Þ\u0001\u001a\u00020\u00072\n\b\u0002\u0010À\u0001\u001a\u00030Á\u0001\u001a;\u0010í\u0001\u001a\u00030\u008f\u0001*\n\u0012\u0005\u0012\u00030î\u00010\u00ad\u00012\u000f\u0010ï\u0001\u001a\n\u0012\u0005\u0012\u00030î\u00010\u00ad\u00012\t\b\u0002\u0010\u009e\u0001\u001a\u00020A2\t\b\u0002\u0010\u009f\u0001\u001a\u00020A\u001aH\u0010ð\u0001\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0001*\u00020\u000e2\r\u0010ñ\u0001\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00012\f\b\u0002\u0010À\u0001\u001a\u0005\u0018\u00010Á\u00012\u0018\b\u0002\u0010ò\u0001\u001a\u0011\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0088\u0001\u001aB\u0010ó\u0001\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0001*\u00020\u000e2\u0007\u0010Ñ\u0001\u001a\u00020\u000e2\f\b\u0002\u0010À\u0001\u001a\u0005\u0018\u00010Á\u00012\u0018\b\u0002\u0010ò\u0001\u001a\u0011\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0088\u0001\u001aD\u0010ô\u0001\u001a\u00030\u008f\u0001*\n\u0012\u0005\u0012\u00030î\u00010\u00ad\u00012\u000f\u0010ï\u0001\u001a\n\u0012\u0005\u0012\u00030î\u00010\u00ad\u00012\u0007\u0010õ\u0001\u001a\u00020\u00032\t\b\u0002\u0010\u009e\u0001\u001a\u00020A2\t\b\u0002\u0010\u009f\u0001\u001a\u00020A\u001a\u0015\u0010ö\u0001\u001a\u00030\u008f\u0001*\u00020\u00032\u0007\u0010×\u0001\u001a\u00020\u0003\u001a,\u0010ö\u0001\u001a\u00030\u008f\u0001*\u00020\u00032\u0007\u0010×\u0001\u001a\u00020\u00032\u0015\u0010Ù\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u0089\u0001\u0012\u0004\u0012\u00020\u00120\u0088\u0001\u001a7\u0010÷\u0001\u001a\u00030\u008f\u0001*\u00020\u00032\u0007\u0010Ñ\u0001\u001a\u00020\u00032\b\u0010À\u0001\u001a\u00030Á\u00012\u000b\b\u0002\u0010ø\u0001\u001a\u0004\u0018\u00010\u00122\t\b\u0002\u0010ù\u0001\u001a\u00020\u0007\u001a\u001a\u0010ú\u0001\u001a\u00030\u008f\u0001*\u00020\u00032\f\b\u0002\u0010À\u0001\u001a\u0005\u0018\u00010Á\u0001\u001a\f\u0010û\u0001\u001a\u00030\u008f\u0001*\u00020\u0018\u001a\f\u0010ü\u0001\u001a\u00030\u008f\u0001*\u00020\u0018\u001a\u001f\u0010ý\u0001\u001a\u00020\u0018*\u00030þ\u00012\u0007\u0010ÿ\u0001\u001a\u0002052\b\u0010\u0080\u0002\u001a\u00030\u0081\u0002\u001a²\u0001\u0010\u0082\u0002\u001a\u00020:*\u00030þ\u00012\u0007\u0010\u0083\u0002\u001a\u00020K2\b\u0010á\u0001\u001a\u00030â\u00012\u0007\u0010\u0084\u0002\u001a\u00020\u00032\u000b\b\u0002\u0010ø\u0001\u001a\u0004\u0018\u00010\u00122\n\b\u0002\u0010À\u0001\u001a\u00030Á\u00012\n\b\u0002\u0010\u0085\u0002\u001a\u00030\u0086\u00022\n\b\u0002\u0010\u0087\u0002\u001a\u00030\u0088\u00022\t\b\u0002\u0010\u0089\u0002\u001a\u00020A2\t\b\u0002\u0010\u008a\u0002\u001a\u00020A2\u000f\b\u0002\u0010\u008b\u0002\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000120\u0010\u008c\u0002\u001a+\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0003\u0012\u0013\u0012\u0011\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0088\u0001\u0012\u0005\u0012\u00030\u008f\u00010\u008d\u0002\u001a\f\u0010\u008e\u0002\u001a\u00030ç\u0001*\u00020\u0002\u001a\u000e\u0010\u008f\u0002\u001a\u0004\u0018\u00010/*\u00030\u0090\u0002\u001a>\u0010\u0091\u0002\u001a\u0005\u0018\u0001H\u0090\u0001\"\u000b\b\u0000\u0010\u0090\u0001\u0018\u0001*\u00020/*\u00020|2\u0015\u0010\u0092\u0002\u001a\u0010\u0012\u0005\u0012\u0003H\u0090\u0001\u0012\u0004\u0012\u00020A0Ô\u0001H\u0086\bø\u0001\u0000¢\u0006\u0003\u0010\u0093\u0002\u001a\r\u0010\u0094\u0002\u001a\u0004\u0018\u00010:*\u00020:\u001a%\u0010\u0095\u0002\u001a\u0017\u0012\u0013\u0012\u0011\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010F0\u0096\u00020\u0001*\u0007\u0012\u0002\b\u00030\u00ad\u0001\u001a.\u0010\u0095\u0002\u001a\u0017\u0012\u0013\u0012\u0011\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010F0\u0096\u00020\u0001*\u0007\u0012\u0002\b\u00030\u00ad\u00012\u0007\u0010Ý\u0001\u001a\u00020\u0003\u001a\u0018\u0010\u0097\u0002\u001a\u0005\u0018\u00010¤\u0001*\u00030Î\u00012\u0007\u0010á\u0001\u001a\u000205\u001a\u000e\u0010\u0098\u0002\u001a\u0005\u0018\u00010\u0099\u0002*\u00020\u0018\u001a&\u0010\u009a\u0002\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0005\u0012\u00030¯\u0001\u0012\u0004\u0012\u00020F0\u0096\u00020\u0001*\u0007\u0012\u0002\b\u00030\u00ad\u0001H\u0007\u001a#\u0010\u009b\u0002\u001a\u0015\u0012\u0011\u0012\u000f\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020F0\u0096\u00020\u0001*\u0007\u0012\u0002\b\u00030\u00ad\u0001\u001a\"\u0010\u009c\u0002\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0005\u0012\u00030\u009d\u0002\u0012\u0004\u0012\u00020F0\u0096\u00020\u0001*\u00030\u008a\u0001H\u0007\u001a$\u0010\u009e\u0002\u001a\u00020\"*\u00030¾\u00012\u0007\u0010\u009f\u0002\u001a\u00020\u00122\r\u0010 \u0002\u001a\b\u0012\u0004\u0012\u00020\u00120\u0001\u001a\u000f\u0010¡\u0002\u001a\u0005\u0018\u00010\u0081\u0002*\u00030\u0099\u0001\u001a\u000f\u0010¡\u0002\u001a\u00030\u0081\u0002*\u00020/H\u0086\u0010\u001a'\u0010\u008b\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u0089\u0001\u0012\u0004\u0012\u00020\u00120\u0088\u0001*\u0007\u0012\u0002\b\u00030\u00ad\u00012\u0007\u0010Ý\u0001\u001a\u00020\u0003\u001a\u0018\u0010¢\u0002\u001a\u0004\u0018\u00010F*\u00030¤\u00012\b\u0010á\u0001\u001a\u00030â\u0001\u001a\u0015\u0010£\u0002\u001a\u00020A*\u00030Î\u00012\u0007\u0010¤\u0002\u001a\u00020\u001c\u001a\u0015\u0010£\u0002\u001a\u00020A*\u00030Î\u00012\u0007\u0010á\u0001\u001a\u000205\u001a\u000b\u0010¥\u0002\u001a\u00020A*\u00020\u0002\u001a\u000b\u0010¦\u0002\u001a\u00020A*\u00020/\u001a\u0014\u0010§\u0002\u001a\u00020F*\u00020F2\u0007\u0010ä\u0001\u001a\u00020\u0012\u001a\u001e\u0010¨\u0002\u001a\u00020F*\u00030©\u00022\u0007\u0010ª\u0002\u001a\u00020F2\u0007\u0010«\u0002\u001a\u00020\u0012\u001a\u0015\u0010¬\u0002\u001a\u00020A*\u00030¤\u00012\u0007\u0010á\u0001\u001a\u000205\u001a\u000e\u0010\u00ad\u0002\u001a\u00020A*\u0005\u0018\u00010®\u0002\u001a\u000b\u0010¯\u0002\u001a\u00020A*\u00020/\u001a\f\u0010¯\u0002\u001a\u00020A*\u00030°\u0002\u001a\u000b\u0010±\u0002\u001a\u00020A*\u00020\u0003\u001a\u000b\u0010²\u0002\u001a\u00020A*\u00020\u0003\u001a\u000b\u0010³\u0002\u001a\u00020A*\u00020\u0003\u001a\u000b\u0010´\u0002\u001a\u00020A*\u00020F\u001a\u000b\u0010µ\u0002\u001a\u00020A*\u00020/\u001a\u000b\u0010¶\u0002\u001a\u00020A*\u00020\u0003\u001a\u0014\u0010·\u0002\u001a\u00020A*\u00020F2\u0007\u0010¸\u0002\u001a\u00020\u0007\u001a\u000b\u0010¹\u0002\u001a\u00020A*\u00020\u0003\u001a\u000b\u0010º\u0002\u001a\u00020A*\u00020F\u001a\u0014\u0010»\u0002\u001a\u00020A*\u00020\u00182\u0007\u0010¼\u0002\u001a\u00020\u0018\u001a\u000b\u0010½\u0002\u001a\u00020A*\u00020c\u001a\u000b\u0010¾\u0002\u001a\u00020A*\u00020\u0003\u001a\u000b\u0010¿\u0002\u001a\u00020A*\u00020F\u001a\u000b\u0010À\u0002\u001a\u00020A*\u00020F\u001a!\u0010Á\u0002\u001a\u00030\u008f\u0001*\u00030\u008a\u00012\u0007\u0010Ý\u0001\u001a\u00020\u000e2\t\b\u0002\u0010Â\u0002\u001a\u00020\u0007\u001a\u0019\u0010Ã\u0002\u001a\u00030Ä\u0002*\u00030Å\u00022\b\u0010Æ\u0002\u001a\u00030Ç\u0002H\u0007\u001a\u0019\u0010È\u0002\u001a\u00030î\u0001*\u00030Å\u00022\b\u0010É\u0002\u001a\u00030Ê\u0002H\u0007\u001a!\u0010Ë\u0002\u001a\u00020F*\u00020F2\t\u0010Ì\u0002\u001a\u0004\u0018\u00010\u00022\t\u0010Í\u0002\u001a\u0004\u0018\u00010\u0002\u001a7\u0010Î\u0002\u001a\u00020\u0012*\u00020\u00122\u0007\u0010Ñ\u0001\u001a\u00020\u000e2\u0007\u0010ì\u0001\u001a\u00020\u000e2\u0018\b\u0002\u0010Û\u0001\u001a\u0011\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b\u0018\u00010\u0088\u0001\u001a(\u0010Ï\u0002\u001a\u0003H\u0090\u0001\"\n\b\u0000\u0010\u0090\u0001*\u00030\u0099\u0001*\u0003H\u0090\u00012\u0007\u0010\u0080\u0002\u001a\u00020K¢\u0006\u0003\u0010Ð\u0002\u001a$\u0010Ñ\u0002\u001a\n\u0012\u0005\u0012\u0003H\u0090\u00010Ò\u0002\"\u0005\b\u0000\u0010\u0090\u0001*\n\u0012\u0005\u0012\u0003H\u0090\u00010Ó\u0002H\u0000\u001a\u000b\u0010Ñ\u0002\u001a\u00020F*\u00020F\u001a\r\u0010Ô\u0002\u001a\u0004\u0018\u00010F*\u00020F\u001a\u0011\u0010Õ\u0002\u001a\b\u0012\u0004\u0012\u00020:0\u0001*\u00020|\u001a\r\u0010Ö\u0002\u001a\u00020\u0007*\u00020AH\u0002\u001a2\u0010×\u0002\u001a\u0007\u0012\u0002\b\u00030Ó\u0002*\u0005\u0018\u00010Ø\u00022\u0007\u0010Ù\u0002\u001a\u00020\u00122\t\b\u0002\u0010ß\u0001\u001a\u00020\u00072\t\b\u0002\u0010à\u0001\u001a\u00020\u0007\u001a6\u0010Ú\u0002\u001a\t\u0012\u0002\b\u0003\u0018\u00010Ó\u0002*\u0005\u0018\u00010Ø\u00022\u0007\u0010Ù\u0002\u001a\u00020\u00122\t\b\u0002\u0010ß\u0001\u001a\u00020\u00072\t\b\u0002\u0010à\u0001\u001a\u00020\u0007H\u0002\u001a\u001c\u0010Û\u0002\u001a\u00030Ü\u0002\"\u000b\b\u0000\u0010Ý\u0002\u0018\u0001*\u00020g*\u00020gH\u0086\b\u001a\f\u0010Þ\u0002\u001a\u00020F*\u00030ß\u0002\u001a\f\u0010à\u0002\u001a\u00020A*\u00030\u008a\u0001\"\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\b\u0010\t\"\u001b\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\f\u0010\u0005\"\u0015\u0010\r\u001a\u00020\u000e*\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\"\u0015\u0010\u0011\u001a\u00020\u0012*\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\"\u001b\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00130\u0017*\u00020\u00188F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a\"\u001b\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0017*\u00020\u001c8F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001d\"\u0017\u0010\u001e\u001a\u0004\u0018\u00010\u0013*\u00020\u00188F¢\u0006\u0006\u001a\u0004\b\u001f\u0010 \"\u0015\u0010!\u001a\u00020\"*\u00020\u00188F¢\u0006\u0006\u001a\u0004\b#\u0010$\"\u001b\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b&\u0010\u0005\"\u0015\u0010'\u001a\u00020\u0007*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b(\u0010\t\"\u001b\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u0017*\u00020\u00188F¢\u0006\u0006\u001a\u0004\b+\u0010\u001a\"\u001b\u0010)\u001a\b\u0012\u0004\u0012\u00020,0\u0017*\u00020\u001c8F¢\u0006\u0006\u001a\u0004\b+\u0010\u001d\"\u0015\u0010-\u001a\u00020.*\u00020/8F¢\u0006\u0006\u001a\u0004\b0\u00101\"\u0017\u00102\u001a\u0004\u0018\u00010.*\u00020/8F¢\u0006\u0006\u001a\u0004\b3\u00101\"\u0017\u00104\u001a\u0004\u0018\u000105*\u0002068F¢\u0006\u0006\u001a\u0004\b7\u00108\"\u001b\u00109\u001a\b\u0012\u0004\u0012\u00020:0\u0017*\u00020\u00188F¢\u0006\u0006\u001a\u0004\b;\u0010\u001a\"\u001b\u00109\u001a\b\u0012\u0004\u0012\u00020<0\u0017*\u00020\u001c8F¢\u0006\u0006\u001a\u0004\b;\u0010\u001d\"\u0017\u0010=\u001a\u0004\u0018\u00010:*\u00020\u00188F¢\u0006\u0006\u001a\u0004\b>\u0010?\"\u0015\u0010@\u001a\u00020A*\u00020\u00188F¢\u0006\u0006\u001a\u0004\b@\u0010B\"\u0015\u0010C\u001a\u00020A*\u00020\u00188F¢\u0006\u0006\u001a\u0004\bC\u0010B\"\u0015\u0010D\u001a\u00020A*\u00020\u00188F¢\u0006\u0006\u001a\u0004\bD\u0010B\"\u0015\u0010E\u001a\u00020A*\u00020F8F¢\u0006\u0006\u001a\u0004\bE\u0010G\"\u0015\u0010H\u001a\u00020A*\u00020\u00188F¢\u0006\u0006\u001a\u0004\bH\u0010B\"\u0015\u0010I\u001a\u00020A*\u00020\u00188F¢\u0006\u0006\u001a\u0004\bI\u0010B\"\u0015\u0010J\u001a\u00020A*\u00020K8F¢\u0006\u0006\u001a\u0004\bJ\u0010L\"\u0015\u0010M\u001a\u00020A*\u00020/8F¢\u0006\u0006\u001a\u0004\bM\u0010N\"\u0015\u0010O\u001a\u00020A*\u00020\u00188F¢\u0006\u0006\u001a\u0004\bO\u0010B\"\u0015\u0010P\u001a\u00020A*\u00020Q8F¢\u0006\u0006\u001a\u0004\bP\u0010R\"\u0015\u0010S\u001a\u00020A*\u00020\u00188F¢\u0006\u0006\u001a\u0004\bS\u0010B\"\u0017\u0010T\u001a\u00020A*\u0004\u0018\u00010U8F¢\u0006\u0006\u001a\u0004\bT\u0010V\"\u0015\u0010W\u001a\u00020A*\u00020/8F¢\u0006\u0006\u001a\u0004\bW\u0010N\"\u0015\u0010X\u001a\u00020A*\u00020\u00188F¢\u0006\u0006\u001a\u0004\bX\u0010B\"\u0015\u0010Y\u001a\u00020A*\u00020\u00188F¢\u0006\u0006\u001a\u0004\bY\u0010B\"\u0015\u0010Z\u001a\u00020A*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bZ\u0010[\"\u0015\u0010Z\u001a\u00020A*\u00020:8F¢\u0006\u0006\u001a\u0004\bZ\u0010\\\"\u0015\u0010]\u001a\u00020A*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b]\u0010[\"\u0015\u0010^\u001a\u00020A*\u00020_8F¢\u0006\u0006\u001a\u0004\b^\u0010`\"\u0015\u0010a\u001a\u00020A*\u00020\u00038F¢\u0006\u0006\u001a\u0004\ba\u0010[\"\u0015\u0010b\u001a\u00020A*\u00020c8F¢\u0006\u0006\u001a\u0004\bb\u0010d\"\u0015\u0010b\u001a\u00020A*\u00020e8F¢\u0006\u0006\u001a\u0004\bb\u0010f\"\u0015\u0010b\u001a\u00020A*\u00020g8F¢\u0006\u0006\u001a\u0004\bb\u0010h\"\u0015\u0010i\u001a\u00020A*\u00020/8F¢\u0006\u0006\u001a\u0004\bi\u0010N\"\u0015\u0010j\u001a\u00020A*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bj\u0010[\"\u0015\u0010k\u001a\u00020\u0003*\u00020\u00038F¢\u0006\u0006\u001a\u0004\bl\u0010m\"\u0015\u0010n\u001a\u00020_*\u00020_8F¢\u0006\u0006\u001a\u0004\bo\u0010p\"\u0015\u0010q\u001a\u00020\u0018*\u00020/8F¢\u0006\u0006\u001a\u0004\br\u0010s\"\u0017\u0010t\u001a\u0004\u0018\u00010\u0018*\u00020/8F¢\u0006\u0006\u001a\u0004\bu\u0010s\"\u0015\u0010v\u001a\u00020\u0007*\u00020\u00078F¢\u0006\u0006\u001a\u0004\bw\u0010x\"\u0017\u0010y\u001a\u0004\u0018\u00010\u0013*\u00020\u00188F¢\u0006\u0006\u001a\u0004\bz\u0010 \"\u001b\u0010{\u001a\b\u0012\u0004\u0012\u00020_0\u0017*\u00020|8F¢\u0006\u0006\u001a\u0004\b}\u0010~\"\u001f\u0010\u007f\u001a\t\u0012\u0005\u0012\u00030\u0080\u00010\u0001*\u00030\u0081\u00018F¢\u0006\b\u001a\u0006\b\u0082\u0001\u0010\u0083\u0001\"\u001a\u0010\u0084\u0001\u001a\u0004\u0018\u00010\u0018*\u00020\u00188F¢\u0006\b\u001a\u0006\b\u0085\u0001\u0010\u0086\u0001\"'\u0010\u0087\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u0089\u0001\u0012\u0004\u0012\u00020\u00120\u0088\u0001*\u00030\u008a\u00018F¢\u0006\b\u001a\u0006\b\u008b\u0001\u0010\u008c\u0001\"&\u0010\u0087\u0001\u001a\u0010\u0012\u0005\u0012\u00030\u0089\u0001\u0012\u0004\u0012\u00020\u00120\u0088\u0001*\u00020e8F¢\u0006\b\u001a\u0006\b\u008b\u0001\u0010\u008d\u0001\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006á\u0002"}, d2 = {"allParameters", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getAllParameters", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Ljava/util/List;", "allParametersCount", "", "getAllParametersCount", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)I", "allTypeParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "getAllTypeParameters", "classIfConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "getClassIfConstructor", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;)Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "constructedClassType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getConstructedClassType", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)Lorg/jetbrains/kotlin/ir/types/IrType;", "constructors", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getConstructors", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)Lkotlin/sequences/Sequence;", "defaultConstructor", "getDefaultConstructor", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "defaultType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "getDefaultType", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "explicitParameters", "getExplicitParameters", "explicitParametersCount", "getExplicitParametersCount", "fields", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getFields", "Lorg/jetbrains/kotlin/ir/symbols/IrFieldSymbol;", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getFile", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "fileOrNull", "getFileOrNull", "fqNameWhenAvailable", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "getFqNameWhenAvailable", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;)Lorg/jetbrains/kotlin/name/FqName;", "functions", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getFunctions", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "invokeFun", "getInvokeFun", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "isAnnotationClass", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Z", "isAnonymousObject", "isClass", "isConstantLike", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)Z", "isEnumClass", "isEnumEntry", "isFacadeClass", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)Z", "isFileClass", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Z", "isFinalClass", "isImmutable", "Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueDeclaration;)Z", "isInterface", "isLambda", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "(Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;)Z", "isMemberOfOpenClass", "isNonCompanionObject", "isObject", "isOverridable", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Z", "(Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)Z", "isOverridableOrOverrides", "isSimpleProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;)Z", "isStatic", "isSuspend", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrCall;)Z", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;)Z", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)Z", "isTopLevel", "isValueClassTypedEquals", "originalFunction", "getOriginalFunction", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "originalProperty", "getOriginalProperty", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;)Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "parentAsClass", "getParentAsClass", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "parentClassOrNull", "getParentClassOrNull", "previousOffset", "getPreviousOffset", "(I)I", "primaryConstructor", "getPrimaryConstructor", "properties", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;", "getProperties", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;)Lkotlin/sequences/Sequence;", "statements", "Lorg/jetbrains/kotlin/ir/IrStatement;", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "getStatements", "(Lorg/jetbrains/kotlin/ir/expressions/IrBody;)Ljava/util/List;", "superClass", "getSuperClass", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "typeSubstitutionMap", "", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;", "getTypeSubstitutionMap", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionAccessExpression;)Ljava/util/Map;", "(Lorg/jetbrains/kotlin/ir/expressions/IrFunctionReference;)Ljava/util/Map;", "computeAllOverridden", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "overridable", "result", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;Ljava/util/Set;)V", "ir2string", "", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/ir/IrElement;", "ir2stringWhole", "irCall", "call", "newFunction", "receiversAsArguments", "argumentsAsReceivers", "newSuperQualifierSymbol", "newReturnType", "newSymbol", "irConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "argumentsAsDispatchers", "isElseBranch", ConfigConstants.CONFIG_BRANCH_SECTION, "Lorg/jetbrains/kotlin/ir/expressions/IrBranch;", "makeTypeParameterSubstitutionMap", "original", "transformed", "addArguments", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "args", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "addChild", "declaration", "addExplicitParametersTo", "parametersList", "", "addFakeOverrides", "typeSystem", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "implementedMembers", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableMember;", "ignoredParentSymbols", "addSimpleDelegatingConstructor", "superConstructor", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "isPrimary", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "allOverridden", "includeSelf", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;Z)Ljava/util/List;", "coerceToUnit", "builtins", "coerceToUnitIfNeeded", "valueType", "copyAndRenameConflictingTypeParametersFrom", "contextParameters", "existingParameters", "", "copyAnnotations", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "copyAnnotationsFrom", "Lorg/jetbrains/kotlin/ir/declarations/IrMutableAnnotationContainer;", FirebaseAnalytics.Param.SOURCE, "copyAnnotationsWhen", "filter", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "copyParameterDeclarationsFrom", TypedValues.TransitionType.S_FROM, "copyReceiverParametersFrom", "substitutionMap", "copySuperTypesFrom", "srcToDstParameterMap", "copyTo", "irFunction", "index", "startOffset", "endOffset", "name", "Lorg/jetbrains/kotlin/name/Name;", "remapTypeMap", ModuleXmlParser.TYPE, "varargElementType", "defaultValue", "Lorg/jetbrains/kotlin/ir/expressions/IrExpressionBody;", "isCrossinline", "isNoinline", "isAssignable", "copyToWithoutSuperTypes", "target", "copyTypeAndValueArgumentsFrom", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "src", "copyTypeParameters", "srcTypeParameters", "parameterMap", "copyTypeParametersFrom", "copyValueArgumentsFrom", "destFunction", "copyValueParametersFrom", "copyValueParametersToStatic", "dispatchReceiverType", "numValueParametersToCopy", "createDispatchReceiverParameter", "createImplicitParameterDeclarationWithWrappedDescriptor", "createParameterDeclarations", "createSpecialAnnotationClass", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "fqn", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "createStaticFunctionWithReceivers", "irParent", "oldFunction", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "visibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "isFakeOverride", "copyMetadata", "typeParametersFromContext", "remapMultiFieldValueClassStructure", "Lkotlin/Function3;", "createStubDefaultValue", "extractRelatedDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "findDeclaration", "predicate", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;Lkotlin/jvm/functions/Function1;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "findInterfaceImplementation", "getAllArgumentsWithIr", "Lkotlin/Pair;", "getAnnotation", "getAnnotationRetention", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinRetention;", "getArguments", "getArgumentsWithIr", "getArgumentsWithSymbols", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "getKFunctionType", "returnType", "parameterTypes", "getPackageFragment", "getValueArgument", "hasAnnotation", NativeSymbol.TYPE_NAME, "hasDefaultValue", "hasInterfaceParent", "implicitCastIfNeededTo", "irCastIfNeeded", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "expression", "to", "isAnnotation", "isComposite", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "isEffectivelyExternal", "Lorg/jetbrains/kotlin/ir/declarations/IrPossiblyExternalDeclaration;", "isEquals", "isExternalOrInheritedFromExternal", "isFakeOverriddenFromAny", "isFalseConst", "isFromJava", "isHashCode", "isIntegerConst", "value", "isMethodOfAny", "isNullConst", "isSubclassOf", "ancestor", "isSuperToAny", "isToString", "isTrivial", "isTrueConst", "passTypeArgumentsFrom", TypedValues.CycleType.S_WAVE_OFFSET, "referenceClassifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "classifier", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "referenceFunction", "callable", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "remapReceiver", "oldReceiver", "newReceiver", "remapTypeParameters", "setDeclarationsParent", "(Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)Lorg/jetbrains/kotlin/ir/IrElement;", "shallowCopy", "Lorg/jetbrains/kotlin/ir/expressions/impl/IrConstImpl;", "Lorg/jetbrains/kotlin/ir/expressions/IrConst;", "shallowCopyOrNull", "simpleFunctions", "toInt", "toIrConst", "", "irType", "toIrConstOrNull", "unexpectedSymbolKind", "", NativeSymbol.CLASS_NAME, "unwrapBlock", "Lorg/jetbrains/kotlin/ir/expressions/IrContainerExpression;", "usesDefaultArguments", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrUtilsKt {

    /* JADX INFO: compiled from: IrUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[UnsignedType.values().length];
            try {
                iArr[UnsignedType.UBYTE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[UnsignedType.USHORT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[UnsignedType.UINT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[UnsignedType.ULONG.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[PrimitiveType.values().length];
            try {
                iArr2[PrimitiveType.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[PrimitiveType.CHAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[PrimitiveType.BYTE.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr2[PrimitiveType.SHORT.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[PrimitiveType.INT.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[PrimitiveType.FLOAT.ordinal()] = 6;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr2[PrimitiveType.LONG.ordinal()] = 7;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr2[PrimitiveType.DOUBLE.ordinal()] = 8;
            } catch (NoSuchFieldError unused12) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public static final IrFile getFile(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        IrFile fileOrNull = getFileOrNull(irDeclaration);
        if (fileOrNull != null) {
            return fileOrNull;
        }
        throw new NotImplementedError("An operation is not implemented: Unknown file");
    }

    private static final int toInt(boolean z) {
        return z ? 1 : 0;
    }

    @ObsoleteDescriptorBasedAPI
    public static final List<Pair<ParameterDescriptor, IrExpression>> getArguments(IrMemberAccessExpression<?> irMemberAccessExpression) {
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        ArrayList arrayList = new ArrayList();
        DeclarationDescriptor descriptor = irMemberAccessExpression.getSymbol().getDescriptor();
        Intrinsics.checkNotNull(descriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableDescriptor");
        CallableDescriptor callableDescriptor = (CallableDescriptor) descriptor;
        IrExpression dispatchReceiver = irMemberAccessExpression.getDispatchReceiver();
        if (dispatchReceiver != null) {
            ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = callableDescriptor.mo6753getDispatchReceiverParameter();
            Intrinsics.checkNotNull(receiverParameterDescriptorMo6753getDispatchReceiverParameter);
            arrayList.add(TuplesKt.to(receiverParameterDescriptorMo6753getDispatchReceiverParameter, dispatchReceiver));
        }
        IrExpression extensionReceiver = irMemberAccessExpression.getExtensionReceiver();
        if (extensionReceiver != null) {
            ReceiverParameterDescriptor extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter);
            arrayList.add(TuplesKt.to(extensionReceiverParameter, extensionReceiver));
        }
        List<ValueParameterDescriptor> valueParameters = callableDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        for (ValueParameterDescriptor valueParameterDescriptor : valueParameters) {
            IrExpression valueArgument = irMemberAccessExpression.getValueArgument(valueParameterDescriptor.getIndex());
            if (valueArgument != null) {
                arrayList.add(TuplesKt.to(valueParameterDescriptor, valueArgument));
            }
        }
        return arrayList;
    }

    @ObsoleteDescriptorBasedAPI
    public static final List<Pair<IrValueParameterSymbol, IrExpression>> getArgumentsWithSymbols(IrFunctionAccessExpression irFunctionAccessExpression) {
        Intrinsics.checkNotNullParameter(irFunctionAccessExpression, "<this>");
        ArrayList arrayList = new ArrayList();
        IrFunction owner = irFunctionAccessExpression.getSymbol().getOwner();
        IrExpression dispatchReceiver = irFunctionAccessExpression.getDispatchReceiver();
        if (dispatchReceiver != null) {
            IrValueParameter dispatchReceiverParameter = owner.getDispatchReceiverParameter();
            Intrinsics.checkNotNull(dispatchReceiverParameter);
            arrayList.add(TuplesKt.to(dispatchReceiverParameter.getSymbol(), dispatchReceiver));
        }
        IrExpression extensionReceiver = irFunctionAccessExpression.getExtensionReceiver();
        if (extensionReceiver != null) {
            IrValueParameter extensionReceiverParameter = owner.getExtensionReceiverParameter();
            Intrinsics.checkNotNull(extensionReceiverParameter);
            arrayList.add(TuplesKt.to(extensionReceiverParameter.getSymbol(), extensionReceiver));
        }
        for (IrValueParameter irValueParameter : owner.getValueParameters()) {
            ParameterDescriptor descriptor = irValueParameter.getDescriptor();
            Intrinsics.checkNotNull(descriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ValueParameterDescriptor");
            IrExpression valueArgument = irFunctionAccessExpression.getValueArgument(((ValueParameterDescriptor) descriptor).getIndex());
            if (valueArgument != null) {
                arrayList.add(TuplesKt.to(irValueParameter.getSymbol(), valueArgument));
            }
        }
        return arrayList;
    }

    public static final List<Pair<IrValueParameter, IrExpression>> getAllArgumentsWithIr(IrMemberAccessExpression<?> irMemberAccessExpression) {
        IrFunction owner;
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        if (irMemberAccessExpression instanceof IrFunctionAccessExpression) {
            owner = ((IrFunctionAccessExpression) irMemberAccessExpression).getSymbol().getOwner();
        } else if (irMemberAccessExpression instanceof IrFunctionReference) {
            owner = ((IrFunctionReference) irMemberAccessExpression).getSymbol().getOwner();
        } else if (irMemberAccessExpression instanceof IrPropertyReference) {
            IrPropertyReference irPropertyReference = (IrPropertyReference) irMemberAccessExpression;
            irPropertyReference.getField();
            IrSimpleFunctionSymbol getter = irPropertyReference.getGetter();
            Intrinsics.checkNotNull(getter);
            owner = getter.getOwner();
        } else {
            throw new IllegalStateException(irMemberAccessExpression.toString());
        }
        return getAllArgumentsWithIr(irMemberAccessExpression, owner);
    }

    public static final List<Pair<IrValueParameter, IrExpression>> getAllArgumentsWithIr(IrMemberAccessExpression<?> irMemberAccessExpression, IrFunction irFunction) {
        IrValueParameter extensionReceiverParameter;
        IrValueParameter dispatchReceiverParameter;
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        ArrayList arrayList = new ArrayList();
        IrExpression dispatchReceiver = irMemberAccessExpression.getDispatchReceiver();
        if (dispatchReceiver != null && (dispatchReceiverParameter = irFunction.getDispatchReceiverParameter()) != null) {
            arrayList.add(TuplesKt.to(dispatchReceiverParameter, dispatchReceiver));
        }
        IrExpression extensionReceiver = irMemberAccessExpression.getExtensionReceiver();
        if (extensionReceiver != null && (extensionReceiverParameter = irFunction.getExtensionReceiverParameter()) != null) {
            arrayList.add(TuplesKt.to(extensionReceiverParameter, extensionReceiver));
        }
        int i = 0;
        for (Object obj : irFunction.getValueParameters()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            arrayList.add(TuplesKt.to((IrValueParameter) obj, irMemberAccessExpression.getValueArgument(i)));
            i = i2;
        }
        return arrayList;
    }

    public static final List<Pair<IrValueParameter, IrExpression>> getArgumentsWithIr(IrMemberAccessExpression<?> irMemberAccessExpression) {
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        List<Pair<IrValueParameter, IrExpression>> allArgumentsWithIr = getAllArgumentsWithIr(irMemberAccessExpression);
        ArrayList arrayList = new ArrayList();
        for (Object obj : allArgumentsWithIr) {
            if (((Pair) obj).getSecond() != null) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @ObsoleteDescriptorBasedAPI
    public static final void addArguments(IrMemberAccessExpression<?> irMemberAccessExpression, Map<ParameterDescriptor, ? extends IrExpression> args) {
        IrExpression irExpression;
        IrExpression irExpression2;
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        Intrinsics.checkNotNullParameter(args, "args");
        DeclarationDescriptor descriptor = irMemberAccessExpression.getSymbol().getDescriptor();
        Intrinsics.checkNotNull(descriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.CallableDescriptor");
        CallableDescriptor callableDescriptor = (CallableDescriptor) descriptor;
        ReceiverParameterDescriptor receiverParameterDescriptorMo6753getDispatchReceiverParameter = callableDescriptor.mo6753getDispatchReceiverParameter();
        if (receiverParameterDescriptorMo6753getDispatchReceiverParameter != null && (irExpression2 = args.get(receiverParameterDescriptorMo6753getDispatchReceiverParameter)) != null) {
            irMemberAccessExpression.setDispatchReceiver(irExpression2);
        }
        ReceiverParameterDescriptor extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null && (irExpression = args.get(extensionReceiverParameter)) != null) {
            irMemberAccessExpression.setExtensionReceiver(irExpression);
        }
        List<ValueParameterDescriptor> valueParameters = callableDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        for (ValueParameterDescriptor valueParameterDescriptor : valueParameters) {
            IrExpression irExpression3 = args.get(valueParameterDescriptor);
            if (irExpression3 != null) {
                irMemberAccessExpression.putValueArgument(valueParameterDescriptor.getIndex(), irExpression3);
            }
        }
    }

    public static final boolean isNullConst(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        return (irExpression instanceof IrConst) && Intrinsics.areEqual(((IrConst) irExpression).getKind(), IrConstKind.Null.INSTANCE);
    }

    public static final boolean isTrueConst(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        if (irExpression instanceof IrConst) {
            IrConst irConst = (IrConst) irExpression;
            if (Intrinsics.areEqual(irConst.getKind(), IrConstKind.Boolean.INSTANCE) && Intrinsics.areEqual(irConst.getValue(), (Object) true)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isFalseConst(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        if (!(irExpression instanceof IrConst)) {
            return false;
        }
        IrConst irConst = (IrConst) irExpression;
        return Intrinsics.areEqual(irConst.getKind(), IrConstKind.Boolean.INSTANCE) && Intrinsics.areEqual(irConst.getValue(), (Object) false);
    }

    public static final boolean isIntegerConst(IrExpression irExpression, int i) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        if (irExpression instanceof IrConst) {
            IrConst irConst = (IrConst) irExpression;
            if (Intrinsics.areEqual(irConst.getKind(), IrConstKind.Int.INSTANCE) && Intrinsics.areEqual(irConst.getValue(), Integer.valueOf(i))) {
                return true;
            }
        }
        return false;
    }

    public static final IrExpression coerceToUnit(IrExpression irExpression, IrBuiltIns builtins, IrTypeSystemContext typeSystem) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(builtins, "builtins");
        Intrinsics.checkNotNullParameter(typeSystem, "typeSystem");
        return coerceToUnitIfNeeded(irExpression, irExpression.getType(), builtins, typeSystem);
    }

    public static final IrExpression coerceToUnitIfNeeded(IrExpression irExpression, IrType valueType, IrBuiltIns irBuiltIns, IrTypeSystemContext typeSystem) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(valueType, "valueType");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(typeSystem, "typeSystem");
        return org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isSubtypeOf(valueType, irBuiltIns.getUnitType(), typeSystem) ? irExpression : new IrTypeOperatorCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irBuiltIns.getUnitType(), IrTypeOperator.IMPLICIT_COERCION_TO_UNIT, irBuiltIns.getUnitType(), irExpression);
    }

    public static final IrExpression implicitCastIfNeededTo(IrExpression irExpression, IrType type) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        return (Intrinsics.areEqual(type, irExpression.getType()) || IrTypePredicatesKt.isNothing(irExpression.getType())) ? irExpression : new IrTypeOperatorCallImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), type, IrTypeOperator.IMPLICIT_CAST, type, irExpression);
    }

    public static final boolean usesDefaultArguments(IrFunctionAccessExpression irFunctionAccessExpression) {
        Intrinsics.checkNotNullParameter(irFunctionAccessExpression, "<this>");
        List<IrValueParameter> valueParameters = irFunctionAccessExpression.getSymbol().getOwner().getValueParameters();
        if ((valueParameters instanceof Collection) && valueParameters.isEmpty()) {
            return false;
        }
        for (IrValueParameter irValueParameter : valueParameters) {
            if (irFunctionAccessExpression.getValueArgument(irValueParameter.getIndex()) == null && (!AdditionalIrUtilsKt.isVararg(irValueParameter) || irValueParameter.getDefaultValue() != null)) {
                return true;
            }
        }
        return false;
    }

    public static final IrExpressionBody createStubDefaultValue(IrValueParameter irValueParameter) {
        Intrinsics.checkNotNullParameter(irValueParameter, "<this>");
        return IrFactoryHelpersKt.createExpressionBody(irValueParameter.getFactory(), new IrErrorExpressionImpl(-1, -1, irValueParameter.getType(), "Stub expression for default value of " + irValueParameter.getName()));
    }

    public static final boolean isSimpleProperty(IrProperty irProperty) {
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        IrSimpleFunction getter = irProperty.getGetter();
        IrSimpleFunction setter = irProperty.getSetter();
        return !irProperty.getIsFakeOverride() && !irProperty.getIsLateinit() && irProperty.getModality() == Modality.FINAL && (getter == null || Intrinsics.areEqual(getter.getOrigin(), IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR.INSTANCE)) && (setter == null || Intrinsics.areEqual(setter.getOrigin(), IrDeclarationOrigin.DEFAULT_PROPERTY_ACCESSOR.INSTANCE));
    }

    public static final Sequence<IrSimpleFunction> getFunctions(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Sequence<IrSimpleFunction> sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(irClass.getDeclarations()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.ir.util.IrUtilsKt$special$$inlined$filterIsInstance$1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof IrSimpleFunction);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return sequenceFilter;
    }

    public static final IrClass getSuperClass(IrClass irClass) {
        Object next;
        IrClassSymbol classOrNull;
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Iterator<T> it = irClass.getSuperTypes().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            IrType irType = (IrType) next;
            if (!IrTypeUtilsKt.isInterface(irType) && !IrTypePredicatesKt.isAny(irType)) {
                break;
            }
        }
        IrType irType2 = (IrType) next;
        if (irType2 == null || (classOrNull = IrTypesKt.getClassOrNull(irType2)) == null) {
            return null;
        }
        return classOrNull.getOwner();
    }

    public static final Sequence<IrSimpleFunctionSymbol> getFunctions(IrClassSymbol irClassSymbol) {
        Intrinsics.checkNotNullParameter(irClassSymbol, "<this>");
        return SequencesKt.map(getFunctions(irClassSymbol.getOwner()), new Function1<IrSimpleFunction, IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.ir.util.IrUtilsKt$functions$1
            @Override // kotlin.jvm.functions.Function1
            public final IrSimpleFunctionSymbol invoke(IrSimpleFunction it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getSymbol();
            }
        });
    }

    public static final Sequence<IrConstructor> getConstructors(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Sequence<IrConstructor> sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(irClass.getDeclarations()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.ir.util.IrUtilsKt$special$$inlined$filterIsInstance$2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof IrConstructor);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return sequenceFilter;
    }

    public static final IrConstructor getDefaultConstructor(IrClass irClass) {
        IrConstructor next;
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Iterator<IrConstructor> it = getConstructors(irClass).iterator();
        while (it.hasNext()) {
            next = it.next();
            List<IrValueParameter> valueParameters = next.getValueParameters();
            if (!(valueParameters instanceof Collection) || !valueParameters.isEmpty()) {
                Iterator<T> it2 = valueParameters.iterator();
                do {
                    if (it2.hasNext()) {
                    }
                } while (((IrValueParameter) it2.next()).getDefaultValue() != null);
            }
            return next;
        }
        next = null;
        return next;
    }

    public static final Sequence<IrConstructorSymbol> getConstructors(IrClassSymbol irClassSymbol) {
        Intrinsics.checkNotNullParameter(irClassSymbol, "<this>");
        return SequencesKt.map(getConstructors(irClassSymbol.getOwner()), new Function1<IrConstructor, IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.ir.util.IrUtilsKt$constructors$1
            @Override // kotlin.jvm.functions.Function1
            public final IrConstructorSymbol invoke(IrConstructor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getSymbol();
            }
        });
    }

    public static final Sequence<IrField> getFields(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Sequence<IrField> sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(irClass.getDeclarations()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.ir.util.IrUtilsKt$special$$inlined$filterIsInstance$3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof IrField);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return sequenceFilter;
    }

    public static final Sequence<IrFieldSymbol> getFields(IrClassSymbol irClassSymbol) {
        Intrinsics.checkNotNullParameter(irClassSymbol, "<this>");
        return SequencesKt.map(getFields(irClassSymbol.getOwner()), new Function1<IrField, IrFieldSymbol>() { // from class: org.jetbrains.kotlin.ir.util.IrUtilsKt$fields$1
            @Override // kotlin.jvm.functions.Function1
            public final IrFieldSymbol invoke(IrField it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getSymbol();
            }
        });
    }

    public static final IrConstructor getPrimaryConstructor(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Object obj = null;
        boolean z = false;
        Object obj2 = null;
        for (Object obj3 : irClass.getDeclarations()) {
            IrDeclaration irDeclaration = (IrDeclaration) obj3;
            if ((irDeclaration instanceof IrConstructor) && ((IrConstructor) irDeclaration).getIsPrimary()) {
                if (z) {
                    return (IrConstructor) obj;
                }
                z = true;
                obj2 = obj3;
            }
        }
        if (z) {
            obj = obj2;
        }
        return (IrConstructor) obj;
    }

    public static final IrSimpleFunction getInvokeFun(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        List<IrDeclaration> declarations = irClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrSimpleFunction) {
                arrayList.add(obj);
            }
        }
        Object obj2 = null;
        boolean z = false;
        Object obj3 = null;
        for (Object obj4 : arrayList) {
            if (Intrinsics.areEqual(((IrSimpleFunction) obj4).getName().asString(), "invoke")) {
                if (z) {
                    return (IrSimpleFunction) obj2;
                }
                z = true;
                obj3 = obj4;
            }
        }
        if (z) {
            obj2 = obj3;
        }
        return (IrSimpleFunction) obj2;
    }

    public static final Sequence<IrProperty> getProperties(IrDeclarationContainer irDeclarationContainer) {
        Intrinsics.checkNotNullParameter(irDeclarationContainer, "<this>");
        Sequence<IrProperty> sequenceFilter = SequencesKt.filter(CollectionsKt.asSequence(irDeclarationContainer.getDeclarations()), new Function1<Object, Boolean>() { // from class: org.jetbrains.kotlin.ir.util.IrUtilsKt$special$$inlined$filterIsInstance$4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof IrProperty);
            }
        });
        Intrinsics.checkNotNull(sequenceFilter, "null cannot be cast to non-null type kotlin.sequences.Sequence<R of kotlin.sequences.SequencesKt___SequencesKt.filterIsInstance>");
        return sequenceFilter;
    }

    public static final void addExplicitParametersTo(IrFunction irFunction, List<IrValueParameter> parametersList) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(parametersList, "parametersList");
        List<IrValueParameter> list = parametersList;
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(list, irFunction.getDispatchReceiverParameter());
        parametersList.addAll(CollectionsKt.take(irFunction.getValueParameters(), irFunction.getContextReceiverParametersCount()));
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(list, irFunction.getExtensionReceiverParameter());
        parametersList.addAll(CollectionsKt.drop(irFunction.getValueParameters(), irFunction.getContextReceiverParametersCount()));
    }

    public static final int getExplicitParametersCount(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return toInt(irFunction.getDispatchReceiverParameter() != null) + toInt(irFunction.getExtensionReceiverParameter() != null) + irFunction.getValueParameters().size();
    }

    public static final List<IrValueParameter> getExplicitParameters(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        ArrayList arrayList = new ArrayList(getExplicitParametersCount(irFunction));
        addExplicitParametersTo(irFunction, arrayList);
        return arrayList;
    }

    public static final List<IrStatement> getStatements(IrBody irBody) {
        Intrinsics.checkNotNullParameter(irBody, "<this>");
        if (irBody instanceof IrBlockBody) {
            return ((IrBlockBody) irBody).getStatements();
        }
        if (irBody instanceof IrExpressionBody) {
            return CollectionsKt.listOf(((IrExpressionBody) irBody).getExpression());
        }
        if (irBody instanceof IrSyntheticBody) {
            throw new IllegalStateException(("Synthetic body contains no statements: " + irBody).toString());
        }
        throw new IllegalStateException(("Unknown subclass of IrBody: " + irBody).toString());
    }

    public static final IrSimpleType getDefaultType(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        IrValueParameter thisReceiver = irClass.getThisReceiver();
        Intrinsics.checkNotNull(thisReceiver);
        IrType type = thisReceiver.getType();
        Intrinsics.checkNotNull(type, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        return (IrSimpleType) type;
    }

    public static final boolean isSubclassOf(IrClass irClass, IrClass ancestor) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(ancestor, "ancestor");
        return isSubclassOf$hasAncestorInSuperTypes(irClass, ancestor, new LinkedHashSet());
    }

    private static final boolean isSubclassOf$hasAncestorInSuperTypes(IrClass irClass, IrClass irClass2, Set<IrClass> set) {
        if (irClass == irClass2) {
            return true;
        }
        if (!set.contains(irClass)) {
            set.add(irClass);
            List<IrType> superTypes = irClass.getSuperTypes();
            ArrayList arrayList = new ArrayList();
            for (IrType irType : superTypes) {
                IrSimpleType irSimpleType = irType instanceof IrSimpleType ? (IrSimpleType) irType : null;
                IrClassifierSymbol classifier = irSimpleType != null ? irSimpleType.getClassifier() : null;
                IrClassSymbol irClassSymbol = classifier instanceof IrClassSymbol ? (IrClassSymbol) classifier : null;
                IrClass owner = irClassSymbol != null ? irClassSymbol.getOwner() : null;
                if (owner != null) {
                    arrayList.add(owner);
                }
            }
            ArrayList arrayList2 = arrayList;
            if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    if (isSubclassOf$hasAncestorInSuperTypes((IrClass) it.next(), irClass2, set)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static final IrSimpleFunction findInterfaceImplementation(IrSimpleFunction irSimpleFunction) {
        IrSimpleFunction irSimpleFunction2;
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        if (AdditionalIrUtilsKt.isReal(irSimpleFunction) || (irSimpleFunction2 = (IrSimpleFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default(irSimpleFunction, false, null, 3, null)) == null || !isInterface(getParentAsClass(irSimpleFunction2))) {
            return null;
        }
        return irSimpleFunction2;
    }

    public static final boolean isAnnotationClass(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return irClass.getKind() == ClassKind.ANNOTATION_CLASS;
    }

    public static final boolean isEnumClass(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return irClass.getKind() == ClassKind.ENUM_CLASS;
    }

    public static final boolean isEnumEntry(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return irClass.getKind() == ClassKind.ENUM_ENTRY;
    }

    public static final boolean isInterface(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return irClass.getKind() == ClassKind.INTERFACE;
    }

    public static final boolean isClass(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return irClass.getKind() == ClassKind.CLASS;
    }

    public static final boolean isObject(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return irClass.getKind() == ClassKind.OBJECT;
    }

    public static final boolean isAnonymousObject(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return isClass(irClass) && Intrinsics.areEqual(irClass.getName(), SpecialNames.NO_NAME_PROVIDED);
    }

    public static final boolean isNonCompanionObject(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return isObject(irClass) && !irClass.getIsCompanion();
    }

    public static final FqName getFqNameWhenAvailable(IrDeclarationWithName irDeclarationWithName) {
        Intrinsics.checkNotNullParameter(irDeclarationWithName, "<this>");
        IrDeclarationParent parent = irDeclarationWithName.getParent();
        if (!(parent instanceof IrDeclarationWithName)) {
            if (parent instanceof IrPackageFragment) {
                return ((IrPackageFragment) parent).getPackageFqName().child(irDeclarationWithName.getName());
            }
            return null;
        }
        FqName fqNameWhenAvailable = getFqNameWhenAvailable((IrDeclarationWithName) parent);
        if (fqNameWhenAvailable != null) {
            return fqNameWhenAvailable.child(irDeclarationWithName.getName());
        }
        return null;
    }

    public static final IrClass getParentAsClass(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        IrDeclarationParent parent = irDeclaration.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass != null) {
            return irClass;
        }
        throw new IllegalStateException(("Parent of this declaration is not a class: " + RenderIrElementKt.render$default(irDeclaration, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }

    public static final IrPackageFragment getPackageFragment(IrElement irElement) {
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        IrPackageFragment irPackageFragment = irElement instanceof IrPackageFragment ? (IrPackageFragment) irElement : null;
        if (irPackageFragment != null) {
            return irPackageFragment;
        }
        IrDeclaration irDeclaration = irElement instanceof IrDeclaration ? (IrDeclaration) irElement : null;
        if (irDeclaration != null) {
            return getPackageFragment(irDeclaration);
        }
        return null;
    }

    public static final IrPackageFragment getPackageFragment(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        while (true) {
            IrDeclarationParent parent = irDeclaration.getParent();
            IrPackageFragment irPackageFragment = parent instanceof IrPackageFragment ? (IrPackageFragment) parent : null;
            if (irPackageFragment != null) {
                return irPackageFragment;
            }
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
            irDeclaration = (IrDeclaration) parent;
        }
    }

    public static final boolean isAnnotation(IrConstructorCall irConstructorCall, FqName name2) {
        Intrinsics.checkNotNullParameter(irConstructorCall, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        return Intrinsics.areEqual(getFqNameWhenAvailable(getParentAsClass(irConstructorCall.getSymbol().getOwner())), name2);
    }

    public static final IrConstructorCall getAnnotation(IrAnnotationContainer irAnnotationContainer, FqName name2) {
        Object next;
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Iterator<T> it = irAnnotationContainer.getAnnotations().iterator();
        while (it.hasNext()) {
            next = it.next();
            if (isAnnotation((IrConstructorCall) next, name2)) {
                return (IrConstructorCall) next;
            }
        }
        next = null;
        return (IrConstructorCall) next;
    }

    public static final boolean hasAnnotation(IrAnnotationContainer irAnnotationContainer, FqName name2) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        List<IrConstructorCall> annotations = irAnnotationContainer.getAnnotations();
        if ((annotations instanceof Collection) && annotations.isEmpty()) {
            return false;
        }
        Iterator<T> it = annotations.iterator();
        while (it.hasNext()) {
            if (AdditionalIrUtilsKt.hasEqualFqName(getParentAsClass(((IrConstructorCall) it.next()).getSymbol().getOwner()), name2)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean hasAnnotation(IrAnnotationContainer irAnnotationContainer, IrClassSymbol symbol) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        List<IrConstructorCall> annotations = irAnnotationContainer.getAnnotations();
        if ((annotations instanceof Collection) && annotations.isEmpty()) {
            return false;
        }
        Iterator<T> it = annotations.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(getParentAsClass(((IrConstructorCall) it.next()).getSymbol().getOwner()).getSymbol(), symbol)) {
                return true;
            }
        }
        return false;
    }

    public static final KotlinRetention getAnnotationRetention(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        IrConstructorCall annotation = getAnnotation(irClass, StandardNames.FqNames.retention);
        IrExpression valueArgument = annotation != null ? getValueArgument(annotation, StandardClassIds.Annotations.ParameterNames.INSTANCE.getRetentionValue()) : null;
        IrGetEnumValue irGetEnumValue = valueArgument instanceof IrGetEnumValue ? (IrGetEnumValue) valueArgument : null;
        if (irGetEnumValue == null) {
            return null;
        }
        String strAsString = irGetEnumValue.getSymbol().getOwner().getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return KotlinRetention.valueOf(strAsString);
    }

    public static final IrExpression getValueArgument(IrConstructorCall irConstructorCall, Name name2) {
        Object next;
        Intrinsics.checkNotNullParameter(irConstructorCall, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Iterator<T> it = irConstructorCall.getSymbol().getOwner().getValueParameters().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((IrValueParameter) next).getName(), name2));
        IrValueParameter irValueParameter = (IrValueParameter) next;
        if (irValueParameter != null) {
            return irConstructorCall.getValueArgument(irValueParameter.getIndex());
        }
        return null;
    }

    public static final IrType getConstructedClassType(IrConstructor irConstructor) {
        Intrinsics.checkNotNullParameter(irConstructor, "<this>");
        IrDeclarationParent parent = irConstructor.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        IrValueParameter thisReceiver = ((IrClass) parent).getThisReceiver();
        IrType type = thisReceiver != null ? thisReceiver.getType() : null;
        Intrinsics.checkNotNull(type);
        return type;
    }

    public static final boolean isFakeOverriddenFromAny(IrFunction irFunction) {
        IrValueParameter thisReceiver;
        IrType type;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrSimpleFunction irSimpleFunction = irFunction instanceof IrSimpleFunction ? (IrSimpleFunction) irFunction : null;
        if (irSimpleFunction == null) {
            return false;
        }
        if (!irSimpleFunction.getIsFakeOverride()) {
            IrDeclarationParent parent = irFunction.getParent();
            IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
            if (irClass == null || (thisReceiver = irClass.getThisReceiver()) == null || (type = thisReceiver.getType()) == null) {
                return false;
            }
            return IrTypePredicatesKt.isAny(type);
        }
        List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunction.getOverriddenSymbols();
        if (!(overriddenSymbols instanceof Collection) || !overriddenSymbols.isEmpty()) {
            Iterator<T> it = overriddenSymbols.iterator();
            while (it.hasNext()) {
                if (!isFakeOverriddenFromAny(((IrSimpleFunctionSymbol) it.next()).getOwner())) {
                    return false;
                }
            }
        }
        return true;
    }

    public static final boolean isSuperToAny(IrCall irCall) {
        Intrinsics.checkNotNullParameter(irCall, "<this>");
        if (irCall.getSuperQualifierSymbol() != null) {
            return isFakeOverriddenFromAny(irCall.getSymbol().getOwner());
        }
        return false;
    }

    public static final boolean hasInterfaceParent(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        IrDeclarationParent parent = irDeclaration.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        return irClass != null && isInterface(irClass);
    }

    public static final boolean isEffectivelyExternal(IrPossiblyExternalDeclaration irPossiblyExternalDeclaration) {
        Intrinsics.checkNotNullParameter(irPossiblyExternalDeclaration, "<this>");
        return irPossiblyExternalDeclaration.getIsExternal();
    }

    public static final boolean isEffectivelyExternal(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return (irDeclaration instanceof IrPossiblyExternalDeclaration) && ((IrPossiblyExternalDeclaration) irDeclaration).getIsExternal();
    }

    private static final boolean isExternalOrInheritedFromExternal$isExternalOrInheritedFromExternalImpl(IrSimpleFunction irSimpleFunction) {
        if (!isEffectivelyExternal((IrPossiblyExternalDeclaration) irSimpleFunction)) {
            List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunction.getOverriddenSymbols();
            if (!(overriddenSymbols instanceof Collection) || !overriddenSymbols.isEmpty()) {
                Iterator<T> it = overriddenSymbols.iterator();
                while (it.hasNext()) {
                    if (isExternalOrInheritedFromExternal$isExternalOrInheritedFromExternalImpl(((IrSimpleFunctionSymbol) it.next()).getOwner())) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public static final boolean isExternalOrInheritedFromExternal(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return isEffectivelyExternal((IrPossiblyExternalDeclaration) irFunction) || ((irFunction instanceof IrSimpleFunction) && isExternalOrInheritedFromExternal$isExternalOrInheritedFromExternalImpl((IrSimpleFunction) irFunction));
    }

    public static final /* synthetic */ <T extends IrDeclaration> T findDeclaration(IrDeclarationContainer irDeclarationContainer, Function1<? super T, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(irDeclarationContainer, "<this>");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        for (Object obj : irDeclarationContainer.getDeclarations()) {
            IrDeclaration irDeclaration = (IrDeclaration) obj;
            Intrinsics.reifiedOperationMarker(3, ExifInterface.GPS_DIRECTION_TRUE);
            if ((irDeclaration instanceof IrDeclaration) && predicate.invoke(irDeclaration).booleanValue()) {
                Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
                return (T) obj;
            }
        }
        obj = null;
        Intrinsics.reifiedOperationMarker(2, ExifInterface.GPS_DIRECTION_TRUE);
        return (T) obj;
    }

    public static final boolean hasDefaultValue(IrValueParameter irValueParameter) {
        Intrinsics.checkNotNullParameter(irValueParameter, "<this>");
        Boolean boolIfAny = DFS.ifAny(CollectionsKt.listOf(irValueParameter), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.ir.util.IrUtilsKt$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return IrUtilsKt.hasDefaultValue$lambda$35((IrValueParameter) obj);
            }
        }, new Function1<IrValueParameter, Boolean>() { // from class: org.jetbrains.kotlin.ir.util.IrUtilsKt.hasDefaultValue.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrValueParameter irValueParameter2) {
                return Boolean.valueOf(irValueParameter2.getDefaultValue() != null);
            }
        });
        Intrinsics.checkNotNullExpressionValue(boolIfAny, "ifAny(...)");
        return boolIfAny.booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable hasDefaultValue$lambda$35(IrValueParameter irValueParameter) {
        List<IrSimpleFunctionSymbol> overriddenSymbols;
        IrDeclarationParent parent = irValueParameter.getParent();
        IrSimpleFunction irSimpleFunction = parent instanceof IrSimpleFunction ? (IrSimpleFunction) parent : null;
        if (irSimpleFunction == null || (overriddenSymbols = irSimpleFunction.getOverriddenSymbols()) == null) {
            return CollectionsKt.emptyList();
        }
        List<IrSimpleFunctionSymbol> list = overriddenSymbols;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((IrSimpleFunctionSymbol) it.next()).getOwner().getValueParameters().get(irValueParameter.getIndex()));
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @ObsoleteDescriptorBasedAPI
    public static final IrClassifierSymbol referenceClassifier(ReferenceSymbolTable referenceSymbolTable, ClassifierDescriptor classifier) {
        Intrinsics.checkNotNullParameter(referenceSymbolTable, "<this>");
        Intrinsics.checkNotNullParameter(classifier, "classifier");
        if (classifier instanceof TypeParameterDescriptor) {
            return referenceSymbolTable.getDescriptorExtension().referenceTypeParameter(classifier);
        }
        if (classifier instanceof ScriptDescriptor) {
            return referenceSymbolTable.getDescriptorExtension().referenceScript(classifier);
        }
        if (classifier instanceof ClassDescriptor) {
            return referenceSymbolTable.getDescriptorExtension().referenceClass(classifier);
        }
        throw new IllegalArgumentException("Unexpected classifier descriptor: " + classifier);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @ObsoleteDescriptorBasedAPI
    public static final IrFunctionSymbol referenceFunction(ReferenceSymbolTable referenceSymbolTable, CallableDescriptor callable) {
        Intrinsics.checkNotNullParameter(referenceSymbolTable, "<this>");
        Intrinsics.checkNotNullParameter(callable, "callable");
        if (callable instanceof ClassConstructorDescriptor) {
            return referenceSymbolTable.getDescriptorExtension().referenceConstructor(callable);
        }
        if (callable instanceof FunctionDescriptor) {
            return referenceSymbolTable.getDescriptorExtension().referenceSimpleFunction(callable);
        }
        throw new IllegalArgumentException("Unexpected callable descriptor: " + callable);
    }

    public static /* synthetic */ IrConstructorCall irConstructorCall$default(IrFunctionAccessExpression irFunctionAccessExpression, IrConstructorSymbol irConstructorSymbol, boolean z, boolean z2, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            z2 = false;
        }
        return irConstructorCall(irFunctionAccessExpression, irConstructorSymbol, z, z2);
    }

    public static final IrConstructorCall irConstructorCall(IrFunctionAccessExpression call, IrConstructorSymbol newSymbol, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(newSymbol, "newSymbol");
        IrConstructorCallImpl irConstructorCallImpl = new IrConstructorCallImpl(call.getStartOffset(), call.getEndOffset(), call.getType(), newSymbol, call.getTypeArgumentsCount(), 0, call.getValueArgumentsCount(), call.getOrigin(), null, 256, null);
        copyTypeAndValueArgumentsFrom(irConstructorCallImpl, call, z, z2);
        return irConstructorCallImpl;
    }

    public static final IrCall irCall(IrFunctionAccessExpression call, IrSimpleFunction newFunction, boolean z, boolean z2, IrClassSymbol irClassSymbol, IrType irType) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(newFunction, "newFunction");
        return irCall(call, newFunction.getSymbol(), z, z2, irClassSymbol, irType);
    }

    public static final IrCall irCall(IrFunctionAccessExpression call, IrSimpleFunctionSymbol newSymbol, boolean z, boolean z2, IrClassSymbol irClassSymbol, IrType irType) {
        Intrinsics.checkNotNullParameter(call, "call");
        Intrinsics.checkNotNullParameter(newSymbol, "newSymbol");
        int startOffset = call.getStartOffset();
        int endOffset = call.getEndOffset();
        if (irType == null) {
            irType = call.getType();
        }
        IrCallImpl irCallImpl = new IrCallImpl(startOffset, endOffset, irType, newSymbol, call.getTypeArgumentsCount(), newSymbol.getOwner().getValueParameters().size(), call.getOrigin(), irClassSymbol);
        copyTypeAndValueArgumentsFrom(irCallImpl, call, z, z2);
        return irCallImpl;
    }

    public static /* synthetic */ void copyTypeAndValueArgumentsFrom$default(IrMemberAccessExpression irMemberAccessExpression, IrMemberAccessExpression irMemberAccessExpression2, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        if ((i & 4) != 0) {
            z2 = false;
        }
        copyTypeAndValueArgumentsFrom(irMemberAccessExpression, irMemberAccessExpression2, z, z2);
    }

    public static final void copyTypeAndValueArgumentsFrom(IrMemberAccessExpression<IrFunctionSymbol> irMemberAccessExpression, IrMemberAccessExpression<IrFunctionSymbol> src, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        Intrinsics.checkNotNullParameter(src, "src");
        IrExpressionsKt.copyTypeArgumentsFrom$default(irMemberAccessExpression, src, 0, 2, null);
        copyValueArgumentsFrom(irMemberAccessExpression, src, ((IrFunctionSymbol) irMemberAccessExpression.getSymbol()).getOwner(), z, z2);
    }

    public static /* synthetic */ void copyValueArgumentsFrom$default(IrMemberAccessExpression irMemberAccessExpression, IrMemberAccessExpression irMemberAccessExpression2, IrFunction irFunction, boolean z, boolean z2, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            z2 = false;
        }
        copyValueArgumentsFrom(irMemberAccessExpression, irMemberAccessExpression2, irFunction, z, z2);
    }

    public static final void copyValueArgumentsFrom(IrMemberAccessExpression<IrFunctionSymbol> irMemberAccessExpression, IrMemberAccessExpression<IrFunctionSymbol> src, IrFunction destFunction, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        Intrinsics.checkNotNullParameter(src, "src");
        Intrinsics.checkNotNullParameter(destFunction, "destFunction");
        IrFunction owner = ((IrFunctionSymbol) src.getSymbol()).getOwner();
        int i = 1;
        int i2 = 0;
        if (z && owner.getDispatchReceiverParameter() != null) {
            irMemberAccessExpression.putValueArgument(0, src.getDispatchReceiver());
            i2 = 1;
            i = 0;
        } else if (z2 && destFunction.getDispatchReceiverParameter() != null) {
            irMemberAccessExpression.setDispatchReceiver(src.getValueArgument(0));
        } else {
            irMemberAccessExpression.setDispatchReceiver(src.getDispatchReceiver());
            i = 0;
        }
        while (i < ((IrFunctionSymbol) src.getSymbol()).getOwner().getContextReceiverParametersCount()) {
            irMemberAccessExpression.putValueArgument(i2, src.getValueArgument(i));
            i2++;
            i++;
        }
        if (z && owner.getExtensionReceiverParameter() != null) {
            irMemberAccessExpression.putValueArgument(i2, src.getExtensionReceiver());
            i2++;
        } else if (z2 && destFunction.getExtensionReceiverParameter() != null) {
            irMemberAccessExpression.setExtensionReceiver(src.getValueArgument(i));
            i++;
        } else {
            irMemberAccessExpression.setExtensionReceiver(src.getExtensionReceiver());
        }
        while (i < src.getValueArgumentsCount()) {
            irMemberAccessExpression.putValueArgument(i2, src.getValueArgument(i));
            i2++;
            i++;
        }
    }

    public static final IrFile getFileOrNull(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        IrPackageFragment packageFragment = getPackageFragment(irDeclaration);
        if (packageFragment instanceof IrFile) {
            return (IrFile) packageFragment;
        }
        return null;
    }

    public static final IrClass getParentClassOrNull(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        IrDeclarationParent parent = irDeclaration.getParent();
        if (parent instanceof IrClass) {
            return (IrClass) parent;
        }
        if (parent instanceof IrDeclaration) {
            return getParentClassOrNull((IrDeclaration) parent);
        }
        return null;
    }

    public static final List<IrTypeParameter> getAllTypeParameters(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if (irFunction instanceof IrConstructor) {
            return CollectionsKt.plus((Collection) getParentAsClass(irFunction).getTypeParameters(), (Iterable) irFunction.getTypeParameters());
        }
        return irFunction.getTypeParameters();
    }

    /* JADX WARN: Code duplicated, block: B:13:0x002f  */
    public static final Map<IrTypeParameterSymbol, IrType> getTypeSubstitutionMap(IrMemberAccessExpression<?> irMemberAccessExpression, IrFunction irFunction) {
        IrSimpleType irSimpleType;
        List<IrTypeArgument> listEmptyList;
        List<IrTypeParameter> listExtractTypeParameters;
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        List<IrTypeParameter> allTypeParameters = getAllTypeParameters(irFunction);
        IrCallImpl irCallImpl = irMemberAccessExpression instanceof IrCallImpl ? (IrCallImpl) irMemberAccessExpression : null;
        IrClassSymbol superQualifierSymbol = irCallImpl != null ? irCallImpl.getSuperQualifierSymbol() : null;
        if (superQualifierSymbol != null) {
            IrType defaultType = IrTypesKt.getDefaultType(superQualifierSymbol);
            if (defaultType instanceof IrSimpleType) {
                irSimpleType = (IrSimpleType) defaultType;
            } else {
                irSimpleType = null;
            }
        } else {
            IrExpression dispatchReceiver = irMemberAccessExpression.getDispatchReceiver();
            IrType type = dispatchReceiver != null ? dispatchReceiver.getType() : null;
            if (type instanceof IrSimpleType) {
                irSimpleType = (IrSimpleType) type;
            } else {
                irSimpleType = null;
            }
        }
        if (irSimpleType == null || (listEmptyList = irSimpleType.getArguments()) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        if (allTypeParameters.isEmpty() && listEmptyList.isEmpty()) {
            return MapsKt.emptyMap();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (!listEmptyList.isEmpty()) {
            if (irFunction instanceof IrConstructor) {
                IrClass parentAsClass = getParentAsClass(irFunction);
                if (!parentAsClass.getIsInner() && irMemberAccessExpression.getDispatchReceiver() != null) {
                    throw new AssertionError("Non-inner class constructor reference with dispatch receiver:\n" + DumpIrTreeKt.dump$default(irMemberAccessExpression, null, 1, null));
                }
                IrDeclarationParent parent = parentAsClass.getParent();
                Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                listExtractTypeParameters = IrTypeSystemContextKt.extractTypeParameters((IrClass) parent);
            } else {
                IrClass parentClassOrNull = getParentClassOrNull(irFunction);
                Intrinsics.checkNotNull(parentClassOrNull);
                listExtractTypeParameters = IrTypeSystemContextKt.extractTypeParameters(parentClassOrNull);
            }
            int i = 0;
            for (IrTypeParameter irTypeParameter : listExtractTypeParameters) {
                int i2 = i + 1;
                IrType typeOrNull = IrTypesKt.getTypeOrNull(listEmptyList.get(i));
                if (typeOrNull != null) {
                    linkedHashMap.put(irTypeParameter.getSymbol(), typeOrNull);
                }
                i = i2;
            }
        }
        for (IndexedValue indexedValue : CollectionsKt.withIndex(allTypeParameters)) {
            IrTypeParameterSymbol symbol = ((IrTypeParameter) indexedValue.getValue()).getSymbol();
            IrType typeArgument = irMemberAccessExpression.getTypeArgument(indexedValue.getIndex());
            Intrinsics.checkNotNull(typeArgument);
            Pair pair = TuplesKt.to(symbol, typeArgument);
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return linkedHashMap;
    }

    public static final Map<IrTypeParameterSymbol, IrType> getTypeSubstitutionMap(IrFunctionReference irFunctionReference) {
        Intrinsics.checkNotNullParameter(irFunctionReference, "<this>");
        return getTypeSubstitutionMap(irFunctionReference, irFunctionReference.getSymbol().getOwner());
    }

    public static final Map<IrTypeParameterSymbol, IrType> getTypeSubstitutionMap(IrFunctionAccessExpression irFunctionAccessExpression) {
        Intrinsics.checkNotNullParameter(irFunctionAccessExpression, "<this>");
        return getTypeSubstitutionMap(irFunctionAccessExpression, irFunctionAccessExpression.getSymbol().getOwner());
    }

    public static final boolean isFileClass(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return Intrinsics.areEqual(irDeclaration.getOrigin(), IrDeclarationOrigin.FILE_CLASS.INSTANCE) || Intrinsics.areEqual(irDeclaration.getOrigin(), IrDeclarationOrigin.SYNTHETIC_FILE_CLASS.INSTANCE) || Intrinsics.areEqual(irDeclaration.getOrigin(), IrDeclarationOrigin.JVM_MULTIFILE_CLASS.INSTANCE);
    }

    public static final boolean isFromJava(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        if (!Intrinsics.areEqual(irDeclaration.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE)) {
            if (irDeclaration.getParent() instanceof IrDeclaration) {
                IrDeclarationParent parent = irDeclaration.getParent();
                Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
                if (isFromJava((IrDeclaration) parent)) {
                }
            }
            return false;
        }
        return true;
    }

    public static final boolean isImmutable(IrValueDeclaration irValueDeclaration) {
        Intrinsics.checkNotNullParameter(irValueDeclaration, "<this>");
        return (irValueDeclaration instanceof IrValueParameter) || ((irValueDeclaration instanceof IrVariable) && !((IrVariable) irValueDeclaration).getIsVar());
    }

    public static final boolean isLambda(IrStatementOrigin irStatementOrigin) {
        return Intrinsics.areEqual(irStatementOrigin, IrStatementOrigin.LAMBDA.INSTANCE) || Intrinsics.areEqual(irStatementOrigin, IrStatementOrigin.ANONYMOUS_FUNCTION.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrFunction getOriginalFunction(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrAttributeContainer irAttributeContainer = irFunction instanceof IrAttributeContainer ? (IrAttributeContainer) irFunction : null;
        IrAttributeContainer attributeOwnerId = irAttributeContainer != null ? irAttributeContainer.getAttributeOwnerId() : null;
        IrFunction irFunction2 = attributeOwnerId instanceof IrFunction ? (IrFunction) attributeOwnerId : null;
        return irFunction2 == null ? irFunction : irFunction2;
    }

    public static final IrProperty getOriginalProperty(IrProperty irProperty) {
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        IrAttributeContainer attributeOwnerId = irProperty.getAttributeOwnerId();
        IrProperty irProperty2 = attributeOwnerId instanceof IrProperty ? (IrProperty) attributeOwnerId : null;
        return irProperty2 == null ? irProperty : irProperty2;
    }

    public static final boolean isTrivial(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        return (irExpression instanceof IrConst) || (irExpression instanceof IrGetValue) || (irExpression instanceof IrGetObjectValue) || (irExpression instanceof IrErrorExpressionImpl);
    }

    public static final boolean isConstantLike(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        return (irExpression instanceof IrConst) || (irExpression instanceof IrGetSingletonValue) || ((irExpression instanceof IrGetValue) && Intrinsics.areEqual(((IrGetValue) irExpression).getSymbol().getOwner().getOrigin(), IrDeclarationOrigin.INSTANCE_RECEIVER.INSTANCE));
    }

    public static final IrExpression shallowCopy(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        IrExpression irExpressionShallowCopyOrNull = shallowCopyOrNull(irExpression);
        if (irExpressionShallowCopyOrNull != null) {
            return irExpressionShallowCopyOrNull;
        }
        throw new IllegalStateException(("Not a copyable expression: " + RenderIrElementKt.render$default(irExpression, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }

    public static final IrExpression shallowCopyOrNull(IrExpression irExpression) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        if (irExpression instanceof IrConst) {
            return shallowCopy((IrConst) irExpression);
        }
        if (irExpression instanceof IrGetEnumValue) {
            return new IrGetEnumValueImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression.getType(), ((IrGetEnumValue) irExpression).getSymbol());
        }
        if (irExpression instanceof IrGetObjectValue) {
            return new IrGetObjectValueImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression.getType(), ((IrGetObjectValue) irExpression).getSymbol());
        }
        if (irExpression instanceof IrGetValueImpl) {
            int startOffset = irExpression.getStartOffset();
            int endOffset = irExpression.getEndOffset();
            IrType type = irExpression.getType();
            IrGetValueImpl irGetValueImpl = (IrGetValueImpl) irExpression;
            return new IrGetValueImpl(startOffset, endOffset, type, irGetValueImpl.getSymbol(), irGetValueImpl.getOrigin());
        }
        if (irExpression instanceof IrErrorExpressionImpl) {
            return new IrErrorExpressionImpl(irExpression.getStartOffset(), irExpression.getEndOffset(), irExpression.getType(), ((IrErrorExpressionImpl) irExpression).getDescription());
        }
        return null;
    }

    public static final <T> IrConstImpl<T> shallowCopy(IrConst<T> irConst) {
        Intrinsics.checkNotNullParameter(irConst, "<this>");
        return new IrConstImpl<>(irConst.getStartOffset(), irConst.getEndOffset(), irConst.getType(), irConst.getKind(), irConst.getValue());
    }

    public static final IrExpression remapReceiver(IrExpression irExpression, IrValueParameter irValueParameter, IrValueParameter irValueParameter2) {
        Intrinsics.checkNotNullParameter(irExpression, "<this>");
        if (irExpression instanceof IrGetField) {
            int startOffset = irExpression.getStartOffset();
            int endOffset = irExpression.getEndOffset();
            IrGetField irGetField = (IrGetField) irExpression;
            IrFieldSymbol symbol = irGetField.getSymbol();
            IrType type = irExpression.getType();
            IrExpression receiver = irGetField.getReceiver();
            return new IrGetFieldImpl(startOffset, endOffset, symbol, type, receiver != null ? remapReceiver(receiver, irValueParameter, irValueParameter2) : null, irGetField.getOrigin(), irGetField.getSuperQualifierSymbol());
        }
        if (irExpression instanceof IrGetValue) {
            int startOffset2 = irExpression.getStartOffset();
            int endOffset2 = irExpression.getEndOffset();
            IrType type2 = irExpression.getType();
            IrGetValue irGetValue = (IrGetValue) irExpression;
            IrValueParameterSymbol symbol2 = Intrinsics.areEqual(irGetValue.getSymbol(), irValueParameter != null ? irValueParameter.getSymbol() : null) ? irValueParameter2 != null ? irValueParameter2.getSymbol() : null : null;
            return new IrGetValueImpl(startOffset2, endOffset2, type2, symbol2 != null ? symbol2 : irGetValue.getSymbol(), irGetValue.getOrigin());
        }
        if (irExpression instanceof IrCall) {
            int startOffset3 = irExpression.getStartOffset();
            int endOffset3 = irExpression.getEndOffset();
            IrType type3 = irExpression.getType();
            IrCall irCall = (IrCall) irExpression;
            IrCallImpl irCallImpl = new IrCallImpl(startOffset3, endOffset3, type3, irCall.getSymbol(), irCall.getTypeArgumentsCount(), irCall.getValueArgumentsCount(), irCall.getOrigin(), irCall.getSuperQualifierSymbol());
            IrExpression dispatchReceiver = irCall.getDispatchReceiver();
            irCallImpl.setDispatchReceiver(dispatchReceiver != null ? remapReceiver(dispatchReceiver, irValueParameter, irValueParameter2) : null);
            IrExpression extensionReceiver = irCall.getExtensionReceiver();
            irCallImpl.setExtensionReceiver(extensionReceiver != null ? remapReceiver(extensionReceiver, irValueParameter, irValueParameter2) : null);
            return irCallImpl;
        }
        return shallowCopy(irExpression);
    }

    public static final boolean isFacadeClass(IrDeclarationParent irDeclarationParent) {
        Intrinsics.checkNotNullParameter(irDeclarationParent, "<this>");
        if (irDeclarationParent instanceof IrClass) {
            IrClass irClass = (IrClass) irDeclarationParent;
            if (Intrinsics.areEqual(irClass.getOrigin(), IrDeclarationOrigin.JVM_MULTIFILE_CLASS.INSTANCE) || Intrinsics.areEqual(irClass.getOrigin(), IrDeclarationOrigin.FILE_CLASS.INSTANCE) || Intrinsics.areEqual(irClass.getOrigin(), IrDeclarationOrigin.SYNTHETIC_FILE_CLASS.INSTANCE)) {
                return true;
            }
        }
        return false;
    }

    public static final String ir2string(IrElement irElement) {
        String strRender$default;
        return (irElement == null || (strRender$default = RenderIrElementKt.render$default(irElement, (DumpIrTreeOptions) null, 1, (Object) null)) == null) ? "" : strRender$default;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final String ir2stringWhole(IrElement irElement) {
        StringWriter stringWriter = new StringWriter();
        if (irElement != null) {
            irElement.accept(new DumpIrTreeVisitor(stringWriter, null, 2, 0 == true ? 1 : 0), "");
        }
        String string = stringWriter.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public static /* synthetic */ IrConstructor addSimpleDelegatingConstructor$default(IrClass irClass, IrConstructor irConstructor, IrBuiltIns irBuiltIns, boolean z, IrDeclarationOrigin irDeclarationOrigin, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            irDeclarationOrigin = null;
        }
        return addSimpleDelegatingConstructor(irClass, irConstructor, irBuiltIns, z, irDeclarationOrigin);
    }

    public static final boolean isSuspend(IrCall irCall) {
        Intrinsics.checkNotNullParameter(irCall, "<this>");
        IrSimpleFunction owner = irCall.getSymbol().getOwner();
        IrSimpleFunction irSimpleFunction = owner instanceof IrSimpleFunction ? owner : null;
        return irSimpleFunction != null && irSimpleFunction.getIsSuspend();
    }

    public static final boolean isSuspend(IrFunctionReference irFunctionReference) {
        Intrinsics.checkNotNullParameter(irFunctionReference, "<this>");
        IrFunction owner = irFunctionReference.getSymbol().getOwner();
        IrSimpleFunction irSimpleFunction = owner instanceof IrSimpleFunction ? (IrSimpleFunction) owner : null;
        return irSimpleFunction != null && irSimpleFunction.getIsSuspend();
    }

    public static final boolean isOverridable(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return (irFunction instanceof IrSimpleFunction) && isOverridable((IrSimpleFunction) irFunction);
    }

    public static final boolean isOverridable(IrSimpleFunction irSimpleFunction) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        if (!Intrinsics.areEqual(irSimpleFunction.getVisibility(), DescriptorVisibilities.PRIVATE) && irSimpleFunction.getModality() != Modality.FINAL) {
            IrDeclarationParent parent = irSimpleFunction.getParent();
            IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
            if (irClass == null || !isFinalClass(irClass)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isOverridableOrOverrides(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if (irFunction instanceof IrSimpleFunction) {
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irFunction;
            if (isOverridable(irSimpleFunction) || !irSimpleFunction.getOverriddenSymbols().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isMemberOfOpenClass(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        IrDeclarationParent parent = irDeclaration.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass == null) {
            return false;
        }
        return !isFinalClass(irClass);
    }

    public static final boolean isFinalClass(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        return irClass.getModality() == Modality.FINAL && irClass.getKind() != ClassKind.ENUM_CLASS;
    }

    public static final IrTypeParametersContainer getClassIfConstructor(IrTypeParametersContainer irTypeParametersContainer) {
        Intrinsics.checkNotNullParameter(irTypeParametersContainer, "<this>");
        return irTypeParametersContainer instanceof IrConstructor ? getParentAsClass(irTypeParametersContainer) : irTypeParametersContainer;
    }

    public static /* synthetic */ IrValueParameter copyTo$default(IrValueParameter irValueParameter, IrFunction irFunction, IrDeclarationOrigin irDeclarationOrigin, int i, int i2, int i3, Name name2, Map map, IrType irType, IrType irType2, IrExpressionBody irExpressionBody, boolean z, boolean z2, boolean z3, int i4, Object obj) {
        IrType irTypeRemapTypeParameters;
        IrDeclarationOrigin origin = (i4 & 2) != 0 ? irValueParameter.getOrigin() : irDeclarationOrigin;
        int index = (i4 & 4) != 0 ? irValueParameter.getIndex() : i;
        int startOffset = (i4 & 8) != 0 ? irValueParameter.getStartOffset() : i2;
        int endOffset = (i4 & 16) != 0 ? irValueParameter.getEndOffset() : i3;
        Name name3 = (i4 & 32) != 0 ? irValueParameter.getName() : name2;
        Map mapEmptyMap = (i4 & 64) != 0 ? MapsKt.emptyMap() : map;
        if ((i4 & 128) != 0) {
            IrType type = irValueParameter.getType();
            IrDeclarationParent parent = irValueParameter.getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer");
            irTypeRemapTypeParameters = remapTypeParameters(type, getClassIfConstructor((IrTypeParametersContainer) parent), getClassIfConstructor(irFunction), mapEmptyMap);
        } else {
            irTypeRemapTypeParameters = irType;
        }
        return copyTo(irValueParameter, irFunction, origin, index, startOffset, endOffset, name3, mapEmptyMap, irTypeRemapTypeParameters, (i4 & 256) != 0 ? irValueParameter.getVarargElementType() : irType2, (i4 & 512) != 0 ? irValueParameter.getDefaultValue() : irExpressionBody, (i4 & 1024) != 0 ? irValueParameter.getIsCrossinline() : z, (i4 & 2048) != 0 ? irValueParameter.getIsNoinline() : z2, (i4 & 4096) != 0 ? irValueParameter.getIsAssignable() : z3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrValueParameter copyTo(IrValueParameter irValueParameter, IrFunction irFunction, IrDeclarationOrigin origin, int i, int i2, int i3, Name name2, Map<IrTypeParameter, ? extends IrTypeParameter> remapTypeMap, IrType type, IrType irType, IrExpressionBody irExpressionBody, boolean z, boolean z2, boolean z3) {
        Intrinsics.checkNotNullParameter(irValueParameter, "<this>");
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(remapTypeMap, "remapTypeMap");
        Intrinsics.checkNotNullParameter(type, "type");
        IrExpressionBody irExpressionBodyCreateExpressionBody = null;
        IrValueParameterSymbolImpl irValueParameterSymbolImpl = new IrValueParameterSymbolImpl(0 == true ? 1 : 0, 1, 0 == true ? 1 : 0);
        if (irExpressionBody != null) {
            irExpressionBodyCreateExpressionBody = irValueParameter.getFactory().createExpressionBody(irExpressionBody.getStartOffset(), irExpressionBody.getEndOffset(), (IrExpression) DeepCopyIrTreeWithDeclarationsKt.deepCopyWithVariables(irExpressionBody.getExpression()));
            PatchDeclarationParentsKt.patchDeclarationParents(irExpressionBodyCreateExpressionBody.getExpression(), irFunction);
        }
        IrValueParameter irValueParameterCreateValueParameter = irValueParameter.getFactory().createValueParameter(i2, i3, origin, name2, type, z3, (IrValueParameterSymbol) irValueParameterSymbolImpl, i, irType, z, z2, false);
        irValueParameterCreateValueParameter.setParent(irFunction);
        irValueParameterCreateValueParameter.setDefaultValue(irExpressionBodyCreateExpressionBody);
        copyAnnotationsFrom(irValueParameterCreateValueParameter, irValueParameter);
        return irValueParameterCreateValueParameter;
    }

    public static /* synthetic */ IrTypeParameter copyToWithoutSuperTypes$default(IrTypeParameter irTypeParameter, IrTypeParametersContainer irTypeParametersContainer, int i, IrDeclarationOrigin irDeclarationOrigin, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = irTypeParameter.getIndex();
        }
        if ((i2 & 4) != 0) {
            irDeclarationOrigin = irTypeParameter.getOrigin();
        }
        return copyToWithoutSuperTypes(irTypeParameter, irTypeParametersContainer, i, irDeclarationOrigin);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void copyReceiverParametersFrom(IrFunction irFunction, IrFunction from, Map<IrTypeParameterSymbol, ? extends IrType> substitutionMap) {
        IrValueParameter irValueParameterCreateValueParameter;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(substitutionMap, "substitutionMap");
        IrValueParameter dispatchReceiverParameter = from.getDispatchReceiverParameter();
        Object[] objArr = 0;
        Object[] objArr2 = 0;
        if (dispatchReceiverParameter != null) {
            IrFactory factory = dispatchReceiverParameter.getFactory();
            int startOffset = dispatchReceiverParameter.getStartOffset();
            int endOffset = dispatchReceiverParameter.getEndOffset();
            IrDeclarationOrigin origin = dispatchReceiverParameter.getOrigin();
            Name name2 = dispatchReceiverParameter.getName();
            IrType irTypeSubstitute = IrTypeUtilsKt.substitute(dispatchReceiverParameter.getType(), substitutionMap);
            boolean zIsAssignable = dispatchReceiverParameter.getIsAssignable();
            IrValueParameterSymbolImpl irValueParameterSymbolImpl = new IrValueParameterSymbolImpl(objArr2 == true ? 1 : 0, 1, objArr == true ? 1 : 0);
            int index = dispatchReceiverParameter.getIndex();
            IrType varargElementType = dispatchReceiverParameter.getVarargElementType();
            irValueParameterCreateValueParameter = factory.createValueParameter(startOffset, endOffset, origin, name2, irTypeSubstitute, zIsAssignable, irValueParameterSymbolImpl, index, varargElementType != null ? IrTypeUtilsKt.substitute(varargElementType, substitutionMap) : null, dispatchReceiverParameter.getIsCrossinline(), dispatchReceiverParameter.getIsNoinline(), dispatchReceiverParameter.getIsHidden());
            irValueParameterCreateValueParameter.setParent(irFunction);
        } else {
            irValueParameterCreateValueParameter = null;
        }
        irFunction.setDispatchReceiverParameter(irValueParameterCreateValueParameter);
        IrValueParameter extensionReceiverParameter = from.getExtensionReceiverParameter();
        irFunction.setExtensionReceiverParameter(extensionReceiverParameter != null ? copyTo$default(extensionReceiverParameter, irFunction, null, 0, 0, 0, null, null, null, null, null, false, false, false, 8190, null) : null);
    }

    public static final void copyValueParametersFrom(IrFunction irFunction, IrFunction from, Map<IrTypeParameterSymbol, ? extends IrType> map) {
        Map<IrTypeParameterSymbol, ? extends IrType> substitutionMap = map;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(substitutionMap, "substitutionMap");
        copyReceiverParametersFrom(irFunction, from, map);
        int size = irFunction.getValueParameters().size();
        List<IrValueParameter> valueParameters = irFunction.getValueParameters();
        List<IrValueParameter> valueParameters2 = from.getValueParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters2, 10));
        for (IrValueParameter irValueParameter : valueParameters2) {
            ArrayList arrayList2 = arrayList;
            arrayList2.add(copyTo$default(irValueParameter, irFunction, null, irValueParameter.getIndex() + size, 0, 0, null, null, IrTypeUtilsKt.substitute(irValueParameter.getType(), substitutionMap), null, null, false, false, false, 8058, null));
            substitutionMap = map;
            arrayList = arrayList2;
            valueParameters = valueParameters;
        }
        irFunction.setValueParameters(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) valueParameters, (List) arrayList));
    }

    public static final void copyParameterDeclarationsFrom(IrFunction irFunction, IrFunction from) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(from, "from");
        irFunction.getTypeParameters().isEmpty();
        copyTypeParametersFrom$default(irFunction, from, null, null, 6, null);
        copyValueParametersFrom(irFunction, from);
    }

    public static final void copyValueParametersFrom(IrFunction irFunction, IrFunction from) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(from, "from");
        copyValueParametersFrom(irFunction, from, makeTypeParameterSubstitutionMap(from, irFunction));
    }

    public static /* synthetic */ List copyTypeParameters$default(IrTypeParametersContainer irTypeParametersContainer, List list, IrDeclarationOrigin irDeclarationOrigin, Map map, int i, Object obj) {
        if ((i & 2) != 0) {
            irDeclarationOrigin = null;
        }
        if ((i & 4) != 0) {
            map = null;
        }
        return copyTypeParameters(irTypeParametersContainer, list, irDeclarationOrigin, map);
    }

    public static final List<IrTypeParameter> copyTypeParameters(IrTypeParametersContainer irTypeParametersContainer, List<? extends IrTypeParameter> srcTypeParameters, IrDeclarationOrigin irDeclarationOrigin, Map<IrTypeParameter, ? extends IrTypeParameter> map) {
        Intrinsics.checkNotNullParameter(irTypeParametersContainer, "<this>");
        Intrinsics.checkNotNullParameter(srcTypeParameters, "srcTypeParameters");
        int size = irTypeParametersContainer.getTypeParameters().size();
        if (map == null) {
            map = MapsKt.emptyMap();
        }
        Map mutableMap = MapsKt.toMutableMap(map);
        List<? extends IrTypeParameter> list = srcTypeParameters;
        ArrayList arrayList = new ArrayList(list.size());
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrTypeParameter irTypeParameter = (IrTypeParameter) obj;
            IrTypeParameter irTypeParameterCopyToWithoutSuperTypes = copyToWithoutSuperTypes(irTypeParameter, irTypeParametersContainer, i + size, irDeclarationOrigin == null ? irTypeParameter.getOrigin() : irDeclarationOrigin);
            mutableMap.put(irTypeParameter, irTypeParameterCopyToWithoutSuperTypes);
            arrayList.add(irTypeParameterCopyToWithoutSuperTypes);
            i = i2;
        }
        List<IrTypeParameter> listCompactIfPossible = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
        irTypeParametersContainer.setTypeParameters(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) irTypeParametersContainer.getTypeParameters(), (List) listCompactIfPossible));
        for (Pair pair : CollectionsKt.zip(srcTypeParameters, listCompactIfPossible)) {
            copySuperTypesFrom((IrTypeParameter) pair.component2(), (IrTypeParameter) pair.component1(), mutableMap);
        }
        return listCompactIfPossible;
    }

    public static /* synthetic */ List copyTypeParametersFrom$default(IrTypeParametersContainer irTypeParametersContainer, IrTypeParametersContainer irTypeParametersContainer2, IrDeclarationOrigin irDeclarationOrigin, Map map, int i, Object obj) {
        if ((i & 2) != 0) {
            irDeclarationOrigin = null;
        }
        if ((i & 4) != 0) {
            map = null;
        }
        return copyTypeParametersFrom(irTypeParametersContainer, irTypeParametersContainer2, irDeclarationOrigin, map);
    }

    public static final List<IrTypeParameter> copyTypeParametersFrom(IrTypeParametersContainer irTypeParametersContainer, IrTypeParametersContainer source, IrDeclarationOrigin irDeclarationOrigin, Map<IrTypeParameter, ? extends IrTypeParameter> map) {
        Intrinsics.checkNotNullParameter(irTypeParametersContainer, "<this>");
        Intrinsics.checkNotNullParameter(source, "source");
        return copyTypeParameters(irTypeParametersContainer, source.getTypeParameters(), irDeclarationOrigin, map);
    }

    private static final void copySuperTypesFrom(IrTypeParameter irTypeParameter, IrTypeParameter irTypeParameter2, Map<IrTypeParameter, ? extends IrTypeParameter> map) {
        IrDeclarationParent parent = irTypeParameter2.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer");
        IrTypeParametersContainer irTypeParametersContainer = (IrTypeParametersContainer) parent;
        IrDeclarationParent parent2 = irTypeParameter.getParent();
        Intrinsics.checkNotNull(parent2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer");
        IrTypeParametersContainer irTypeParametersContainer2 = (IrTypeParametersContainer) parent2;
        List<IrType> superTypes = irTypeParameter2.getSuperTypes();
        ArrayList arrayList = new ArrayList(superTypes.size());
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(remapTypeParameters((IrType) it.next(), irTypeParametersContainer, irTypeParametersContainer2, map));
        }
        irTypeParameter.setSuperTypes(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final List<IrConstructorCall> copyAnnotations(IrAnnotationContainer irAnnotationContainer) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        List<IrConstructorCall> annotations = irAnnotationContainer.getAnnotations();
        ArrayList arrayList = new ArrayList(annotations.size());
        for (IrConstructorCall irConstructorCall : annotations) {
            DescriptorsRemapper descriptorsRemapper = null;
            Object[] objArr = 0;
            IrDeclarationParent irDeclarationParent = irAnnotationContainer instanceof IrDeclarationParent ? (IrDeclarationParent) irAnnotationContainer : null;
            DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(descriptorsRemapper, 1, objArr == true ? 1 : 0);
            IrVisitorsKt.acceptVoid(irConstructorCall, deepCopySymbolRemapper);
            DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
            IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irConstructorCall.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), irDeclarationParent);
            if (irElementPatchDeclarationParents != null) {
                arrayList.add((IrConstructorCall) irElementPatchDeclarationParents);
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConstructorCall");
            }
        }
        return org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final List<IrConstructorCall> copyAnnotationsWhen(IrAnnotationContainer irAnnotationContainer, Function1<? super IrConstructorCall, Boolean> filter) {
        Intrinsics.checkNotNullParameter(irAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(filter, "filter");
        List<IrConstructorCall> annotations = irAnnotationContainer.getAnnotations();
        ArrayList arrayList = new ArrayList();
        for (IrConstructorCall irConstructorCall : annotations) {
            IrConstructorCall irConstructorCall2 = null;
            Object[] objArr = 0;
            Object[] objArr2 = 0;
            if (filter.invoke(irConstructorCall).booleanValue()) {
                IrConstructorCall irConstructorCall3 = irConstructorCall;
                IrDeclarationParent irDeclarationParent = irAnnotationContainer instanceof IrDeclarationParent ? (IrDeclarationParent) irAnnotationContainer : null;
                DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(objArr2 == true ? 1 : 0, 1, objArr == true ? 1 : 0);
                IrVisitorsKt.acceptVoid(irConstructorCall3, deepCopySymbolRemapper);
                DeepCopySymbolRemapper deepCopySymbolRemapper2 = deepCopySymbolRemapper;
                IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irConstructorCall3.transform((IrElementTransformer<? super Object>) new DeepCopyIrTreeWithSymbols(deepCopySymbolRemapper2, new DeepCopyTypeRemapper(deepCopySymbolRemapper2)), (Object) null), irDeclarationParent);
                if (irElementPatchDeclarationParents != null) {
                    irConstructorCall2 = (IrConstructorCall) irElementPatchDeclarationParents;
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrConstructorCall");
                }
            }
            if (irConstructorCall2 != null) {
                arrayList.add(irConstructorCall2);
            }
        }
        return arrayList;
    }

    public static final void copyAnnotationsFrom(IrMutableAnnotationContainer irMutableAnnotationContainer, IrAnnotationContainer source) {
        Intrinsics.checkNotNullParameter(irMutableAnnotationContainer, "<this>");
        Intrinsics.checkNotNullParameter(source, "source");
        irMutableAnnotationContainer.setAnnotations(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) irMutableAnnotationContainer.getAnnotations(), (List) copyAnnotations(source)));
    }

    public static final Map<IrTypeParameterSymbol, IrType> makeTypeParameterSubstitutionMap(IrTypeParametersContainer original, IrTypeParametersContainer transformed) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(transformed, "transformed");
        List<IrTypeParameter> typeParameters = original.getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((IrTypeParameter) it.next()).getSymbol());
        }
        ArrayList arrayList2 = arrayList;
        List<IrTypeParameter> typeParameters2 = transformed.getTypeParameters();
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters2, 10));
        Iterator<T> it2 = typeParameters2.iterator();
        while (it2.hasNext()) {
            arrayList3.add(IrTypesKt.getDefaultType((IrTypeParameter) it2.next()));
        }
        return MapsKt.toMap(CollectionsKt.zip(arrayList2, arrayList3));
    }

    public static /* synthetic */ void copyValueParametersToStatic$default(IrFunction irFunction, IrFunction irFunction2, IrDeclarationOrigin irDeclarationOrigin, IrType irType, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            IrValueParameter dispatchReceiverParameter = irFunction2.getDispatchReceiverParameter();
            irType = dispatchReceiverParameter != null ? dispatchReceiverParameter.getType() : null;
        }
        if ((i2 & 8) != 0) {
            i = irFunction2.getValueParameters().size();
        }
        copyValueParametersToStatic(irFunction, irFunction2, irDeclarationOrigin, irType, i);
    }

    public static final void copyValueParametersToStatic(IrFunction irFunction, IrFunction source, IrDeclarationOrigin origin, IrType irType, int i) {
        int i2;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(origin, "origin");
        irFunction.getValueParameters().isEmpty();
        IrValueParameter dispatchReceiverParameter = source.getDispatchReceiverParameter();
        int i3 = 0;
        if (dispatchReceiverParameter != null) {
            Intrinsics.checkNotNull(irType);
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(dispatchReceiverParameter.getType());
            Intrinsics.checkNotNull(classOrNull);
            org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isSubtypeOfClass(irType, classOrNull);
            IrDeclarationParent parent = dispatchReceiverParameter.getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer");
            IrType irTypeRemapTypeParameters$default = remapTypeParameters$default(irType, getClassIfConstructor((IrTypeParametersContainer) parent), getClassIfConstructor(irFunction), null, 4, null);
            List<IrValueParameter> valueParameters = irFunction.getValueParameters();
            IrDeclarationOrigin origin2 = dispatchReceiverParameter.getOrigin();
            Name nameIdentifier = Name.identifier("$this");
            Intrinsics.checkNotNull(nameIdentifier);
            irFunction.setValueParameters(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(valueParameters, copyTo$default(dispatchReceiverParameter, irFunction, origin2, 0, 0, 0, nameIdentifier, null, irTypeRemapTypeParameters$default, null, null, false, false, false, 8024, null)));
            i3 = 1;
        }
        IrValueParameter extensionReceiverParameter = source.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            List<IrValueParameter> valueParameters2 = irFunction.getValueParameters();
            IrDeclarationOrigin origin3 = extensionReceiverParameter.getOrigin();
            i2 = i3 + 1;
            Name nameIdentifier2 = Name.identifier(AsmUtil.RECEIVER_PARAMETER_NAME);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
            irFunction.setValueParameters(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(valueParameters2, copyTo$default(extensionReceiverParameter, irFunction, origin3, i3, 0, 0, nameIdentifier2, null, null, null, null, false, false, false, 8152, null)));
        } else {
            i2 = i3;
        }
        for (IrValueParameter irValueParameter : source.getValueParameters()) {
            if (irValueParameter.getIndex() >= i) {
                return;
            } else {
                irFunction.setValueParameters(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus(irFunction.getValueParameters(), copyTo$default(irValueParameter, irFunction, origin, irValueParameter.getIndex() + i2, 0, 0, null, null, null, null, null, false, false, false, 8184, null)));
            }
        }
    }

    public static /* synthetic */ void passTypeArgumentsFrom$default(IrFunctionAccessExpression irFunctionAccessExpression, IrTypeParametersContainer irTypeParametersContainer, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        passTypeArgumentsFrom(irFunctionAccessExpression, irTypeParametersContainer, i);
    }

    public static final void passTypeArgumentsFrom(IrFunctionAccessExpression irFunctionAccessExpression, IrTypeParametersContainer irFunction, int i) {
        Intrinsics.checkNotNullParameter(irFunctionAccessExpression, "<this>");
        Intrinsics.checkNotNullParameter(irFunction, "irFunction");
        int i2 = 0;
        for (Object obj : irFunction.getTypeParameters()) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            irFunctionAccessExpression.putTypeArgument(i2 + i, IrTypesKt.getDefaultType((IrTypeParameter) obj));
            i2 = i3;
        }
    }

    public static /* synthetic */ IrType remapTypeParameters$default(IrType irType, IrTypeParametersContainer irTypeParametersContainer, IrTypeParametersContainer irTypeParametersContainer2, Map map, int i, Object obj) {
        if ((i & 4) != 0) {
            map = null;
        }
        return remapTypeParameters(irType, irTypeParametersContainer, irTypeParametersContainer2, map);
    }

    public static final IrType remapTypeParameters(IrType irType, IrTypeParametersContainer source, IrTypeParametersContainer target, Map<IrTypeParameter, ? extends IrTypeParameter> map) {
        IrTypeParameter irTypeParameter;
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(target, "target");
        if (!(irType instanceof IrSimpleType)) {
            return irType;
        }
        IrSimpleTypeImpl irSimpleTypeImpl = (IrSimpleType) irType;
        IrSymbolOwner owner = irSimpleTypeImpl.getClassifier().getOwner();
        if (owner instanceof IrTypeParameter) {
            if (map == null || (irTypeParameter = map.get(owner)) == null) {
                irTypeParameter = (IrTypeParameter) owner;
                if (Intrinsics.areEqual(irTypeParameter.getParent(), source)) {
                    irTypeParameter = target.getTypeParameters().get(irTypeParameter.getIndex());
                }
            }
            irSimpleTypeImpl = new IrSimpleTypeImpl(irTypeParameter.getSymbol(), irSimpleTypeImpl.getNullability(), irSimpleTypeImpl.getArguments(), irType.getAnnotations(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
        } else if (owner instanceof IrClass) {
            IrClassSymbol symbol = ((IrClass) owner).getSymbol();
            SimpleTypeNullability nullability = irSimpleTypeImpl.getNullability();
            List<IrTypeArgument> arguments = irSimpleTypeImpl.getArguments();
            ArrayList arrayList = new ArrayList(arguments.size());
            for (IrTypeProjection irTypeProjectionMakeTypeProjection : arguments) {
                if (irTypeProjectionMakeTypeProjection instanceof IrTypeProjection) {
                    IrTypeProjection irTypeProjection = (IrTypeProjection) irTypeProjectionMakeTypeProjection;
                    irTypeProjectionMakeTypeProjection = IrSimpleTypeImplKt.makeTypeProjection(remapTypeParameters(irTypeProjection.getType(), source, target, map), irTypeProjection.getVariance());
                } else if (!(irTypeProjectionMakeTypeProjection instanceof IrStarProjection)) {
                    throw new NoWhenBranchMatchedException();
                }
                arrayList.add(irTypeProjectionMakeTypeProjection);
            }
            irSimpleTypeImpl = new IrSimpleTypeImpl(symbol, nullability, org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList), irType.getAnnotations(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
        }
        return irSimpleTypeImpl;
    }

    public static final void addChild(IrDeclarationContainer irDeclarationContainer, IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(irDeclarationContainer, "<this>");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        irDeclarationContainer.getDeclarations().add(declaration);
        setDeclarationsParent(declaration, irDeclarationContainer);
    }

    public static final <T extends IrElement> T setDeclarationsParent(T t, IrDeclarationParent parent) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(parent, "parent");
        t.accept(SetDeclarationsParentVisitor.INSTANCE, parent);
        return t;
    }

    public static final boolean isStatic(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return (irFunction.getParent() instanceof IrClass) && irFunction.getDispatchReceiverParameter() == null;
    }

    public static final boolean isTopLevel(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        if (irDeclaration.getParent() instanceof IrPackageFragment) {
            return true;
        }
        IrDeclarationParent parent = irDeclaration.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        return irClass != null && isFileClass(irClass) && (irClass.getParent() instanceof IrPackageFragment);
    }

    public static final void createImplicitParameterDeclarationWithWrappedDescriptor(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        irClass.setThisReceiver(DeclarationBuildersKt.buildReceiverParameter$default(irClass, IrDeclarationOrigin.INSTANCE_RECEIVER.INSTANCE, IrTypesKt.typeWithParameters(irClass.getSymbol(), irClass.getTypeParameters()), 0, 0, 24, null));
    }

    public static final boolean isElseBranch(IrBranch branch) {
        Intrinsics.checkNotNullParameter(branch, "branch");
        if (branch instanceof IrElseBranch) {
            return true;
        }
        IrExpression condition = branch.getCondition();
        IrConst irConst = condition instanceof IrConst ? (IrConst) condition : null;
        return irConst != null && ((Boolean) irConst.getValue()).booleanValue();
    }

    public static final boolean isMethodOfAny(IrFunction irFunction) {
        boolean zIsEmpty;
        IrValueParameter irValueParameter;
        IrType type;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if (irFunction.getExtensionReceiverParameter() != null || irFunction.getDispatchReceiverParameter() == null) {
            return false;
        }
        Name name2 = irFunction.getName();
        if (Intrinsics.areEqual(name2, OperatorNameConventions.HASH_CODE) ? true : Intrinsics.areEqual(name2, OperatorNameConventions.TO_STRING)) {
            zIsEmpty = irFunction.getValueParameters().isEmpty();
        } else {
            zIsEmpty = Intrinsics.areEqual(name2, OperatorNameConventions.EQUALS) && (irValueParameter = (IrValueParameter) CollectionsKt.singleOrNull((List) irFunction.getValueParameters())) != null && (type = irValueParameter.getType()) != null && IrTypePredicatesKt.isNullableAny(type);
        }
        return zIsEmpty;
    }

    public static final List<IrSimpleFunction> simpleFunctions(IrDeclarationContainer irDeclarationContainer) {
        List listEmptyList;
        Intrinsics.checkNotNullParameter(irDeclarationContainer, "<this>");
        List<IrDeclaration> declarations = irDeclarationContainer.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (IrDeclaration irDeclaration : declarations) {
            if (irDeclaration instanceof IrSimpleFunction) {
                listEmptyList = CollectionsKt.listOf(irDeclaration);
            } else if (irDeclaration instanceof IrProperty) {
                IrProperty irProperty = (IrProperty) irDeclaration;
                listEmptyList = CollectionsKt.listOfNotNull((Object[]) new IrSimpleFunction[]{irProperty.getGetter(), irProperty.getSetter()});
            } else {
                listEmptyList = CollectionsKt.emptyList();
            }
            CollectionsKt.addAll(arrayList, listEmptyList);
        }
        return arrayList;
    }

    public static final void createParameterDeclarations(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        irClass.getThisReceiver();
        irClass.setThisReceiver(DeclarationBuildersKt.buildReceiverParameter$default(irClass, IrDeclarationOrigin.INSTANCE_RECEIVER.INSTANCE, IrTypesKt.typeWithParameters(irClass.getSymbol(), irClass.getTypeParameters()), 0, 0, 24, null));
    }

    public static /* synthetic */ void createDispatchReceiverParameter$default(IrFunction irFunction, IrDeclarationOrigin irDeclarationOrigin, int i, Object obj) {
        if ((i & 1) != 0) {
            irDeclarationOrigin = null;
        }
        createDispatchReceiverParameter(irFunction, irDeclarationOrigin);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void createDispatchReceiverParameter(IrFunction irFunction, IrDeclarationOrigin irDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        irFunction.getDispatchReceiverParameter();
        IrFactory factory = irFunction.getFactory();
        int startOffset = irFunction.getStartOffset();
        int endOffset = irFunction.getEndOffset();
        if (irDeclarationOrigin == null) {
            irDeclarationOrigin = getParentAsClass(irFunction).getOrigin();
        }
        IrValueParameter irValueParameterCreateValueParameter = factory.createValueParameter(startOffset, endOffset, irDeclarationOrigin, SpecialNames.THIS, (IrType) getDefaultType(getParentAsClass(irFunction)), false, (IrValueParameterSymbol) new IrValueParameterSymbolImpl(null, 1, 0 == true ? 1 : 0), -1, (IrType) null, false, false, false);
        irValueParameterCreateValueParameter.setParent(irFunction);
        irFunction.setDispatchReceiverParameter(irValueParameterCreateValueParameter);
    }

    public static final List<IrValueParameter> getAllParameters(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        if (irFunction instanceof IrConstructor) {
            ArrayList arrayList = new ArrayList(getAllParametersCount(irFunction));
            IrValueParameter thisReceiver = AdditionalIrUtilsKt.getConstructedClass((IrConstructor) irFunction).getThisReceiver();
            if (thisReceiver != null) {
                arrayList.add(thisReceiver);
                ArrayList arrayList2 = arrayList;
                addExplicitParametersTo(irFunction, arrayList2);
                return arrayList2;
            }
            throw new IllegalStateException(RenderIrElementKt.render$default(irFunction, (DumpIrTreeOptions) null, 1, (Object) null).toString());
        }
        return getExplicitParameters(irFunction);
    }

    public static final int getAllParametersCount(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        boolean z = irFunction instanceof IrConstructor;
        int explicitParametersCount = getExplicitParametersCount(irFunction);
        return z ? explicitParametersCount + 1 : explicitParametersCount;
    }

    public static /* synthetic */ void addFakeOverrides$default(IrClass irClass, IrTypeSystemContext irTypeSystemContext, List list, List list2, int i, Object obj) {
        if ((i & 2) != 0) {
            list = CollectionsKt.emptyList();
        }
        if ((i & 4) != 0) {
            list2 = CollectionsKt.emptyList();
        }
        addFakeOverrides(irClass, irTypeSystemContext, list, list2);
    }

    public static final void addFakeOverrides(IrClass irClass, IrTypeSystemContext typeSystem, List<? extends IrOverridableMember> implementedMembers, List<? extends IrSymbol> ignoredParentSymbols) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(typeSystem, "typeSystem");
        Intrinsics.checkNotNullParameter(implementedMembers, "implementedMembers");
        Intrinsics.checkNotNullParameter(ignoredParentSymbols, "ignoredParentSymbols");
        Iterator<T> it = new IrOverridingUtil(typeSystem, new FakeOverrideBuilderForLowerings()).buildFakeOverridesForClassUsingOverriddenSymbols(irClass, implementedMembers, false, ignoredParentSymbols).iterator();
        while (it.hasNext()) {
            addChild(irClass, (IrOverridableMember) it.next());
        }
    }

    public static /* synthetic */ IrSimpleFunction createStaticFunctionWithReceivers$default(IrFactory irFactory, IrDeclarationParent irDeclarationParent, Name name2, IrFunction irFunction, IrType irType, IrDeclarationOrigin irDeclarationOrigin, Modality modality, DescriptorVisibility descriptorVisibility, boolean z, boolean z2, List list, Function3 function3, int i, Object obj) {
        IrType type;
        if ((i & 8) != 0) {
            IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
            type = dispatchReceiverParameter != null ? dispatchReceiverParameter.getType() : null;
        } else {
            type = irType;
        }
        return createStaticFunctionWithReceivers(irFactory, irDeclarationParent, name2, irFunction, type, (i & 16) != 0 ? irFunction.getOrigin() : irDeclarationOrigin, (i & 32) != 0 ? Modality.FINAL : modality, (i & 64) != 0 ? irFunction.getVisibility() : descriptorVisibility, (i & 128) != 0 ? IrFakeOverrideUtilsKt.isFakeOverride(irFunction) : z, (i & 256) != 0 ? true : z2, (i & 512) != 0 ? CollectionsKt.emptyList() : list, function3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final IrSimpleFunction createStaticFunctionWithReceivers(IrFactory irFactory, IrDeclarationParent irParent, Name name2, IrFunction oldFunction, IrType irType, IrDeclarationOrigin origin, Modality modality, DescriptorVisibility visibility, boolean z, boolean z2, List<? extends IrTypeParameter> typeParametersFromContext, Function3<? super IrFunction, ? super IrFunction, ? super Map<IrValueParameter, ? extends IrValueParameter>, Unit> remapMultiFieldValueClassStructure) {
        IrValueParameter irValueParameterCopyTo$default;
        List list;
        IrValueParameter irValueParameterCopyTo$default2;
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(irParent, "irParent");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(oldFunction, "oldFunction");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(modality, "modality");
        Intrinsics.checkNotNullParameter(visibility, "visibility");
        Intrinsics.checkNotNullParameter(typeParametersFromContext, "typeParametersFromContext");
        Intrinsics.checkNotNullParameter(remapMultiFieldValueClassStructure, "remapMultiFieldValueClassStructure");
        boolean z3 = oldFunction instanceof IrSimpleFunction;
        final IrSimpleFunction irSimpleFunctionCreateSimpleFunction = irFactory.createSimpleFunction(oldFunction.getStartOffset(), oldFunction.getEndOffset(), origin, name2, visibility, oldFunction.getIsInline(), oldFunction.getIsExpect(), oldFunction.getReturnType(), modality, new IrSimpleFunctionSymbolImpl(null, 1, 0 == true ? 1 : 0), false, AdditionalIrUtilsKt.isSuspend(oldFunction), z3 && ((IrSimpleFunction) oldFunction).getIsOperator(), z3 && ((IrSimpleFunction) oldFunction).getIsInfix(), false, oldFunction.getContainerSource(), z);
        irSimpleFunctionCreateSimpleFunction.setParent(irParent);
        final Map map = MapsKt.toMap(CollectionsKt.zip(CollectionsKt.plus((Collection) typeParametersFromContext, (Iterable) oldFunction.getTypeParameters()), CollectionsKt.plus((Collection) copyAndRenameConflictingTypeParametersFrom(irSimpleFunctionCreateSimpleFunction, typeParametersFromContext, oldFunction.getTypeParameters()), (Iterable) copyTypeParametersFrom$default(irSimpleFunctionCreateSimpleFunction, oldFunction, null, null, 6, null))));
        for (IrTypeParameter irTypeParameter : irSimpleFunctionCreateSimpleFunction.getTypeParameters()) {
            List<IrType> superTypes = irTypeParameter.getSuperTypes();
            ArrayList arrayList = new ArrayList(superTypes.size());
            Iterator<T> it = superTypes.iterator();
            while (it.hasNext()) {
                arrayList.add(createStaticFunctionWithReceivers$lambda$79$remap(oldFunction, irSimpleFunctionCreateSimpleFunction, map, (IrType) it.next()));
            }
            irTypeParameter.setSuperTypes(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
        }
        irSimpleFunctionCreateSimpleFunction.setAnnotations(oldFunction.getAnnotations());
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        final Ref.IntRef intRef = new Ref.IntRef();
        List list2 = listCreateListBuilder;
        IrValueParameter dispatchReceiverParameter = oldFunction.getDispatchReceiverParameter();
        if (dispatchReceiverParameter != null) {
            Name nameIdentifier = Name.identifier("$this");
            int i = intRef.element;
            intRef.element = i + 1;
            Intrinsics.checkNotNull(irType);
            IrType irTypeCreateStaticFunctionWithReceivers$lambda$79$remap = createStaticFunctionWithReceivers$lambda$79$remap(oldFunction, irSimpleFunctionCreateSimpleFunction, map, irType);
            IrDeclarationOrigin.MOVED_DISPATCH_RECEIVER moved_dispatch_receiver = IrDeclarationOrigin.MOVED_DISPATCH_RECEIVER.INSTANCE;
            Intrinsics.checkNotNull(nameIdentifier);
            irValueParameterCopyTo$default = copyTo$default(dispatchReceiverParameter, irSimpleFunctionCreateSimpleFunction, moved_dispatch_receiver, i, 0, 0, nameIdentifier, null, irTypeCreateStaticFunctionWithReceivers$lambda$79$remap, null, null, false, false, false, 8024, null);
        } else {
            irValueParameterCopyTo$default = null;
        }
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(list2, irValueParameterCopyTo$default);
        CollectionsKt.addAll(list2, SequencesKt.map(SequencesKt.take(CollectionsKt.asSequence(oldFunction.getValueParameters()), oldFunction.getContextReceiverParametersCount()), new Function1<IrValueParameter, IrValueParameter>() { // from class: org.jetbrains.kotlin.ir.util.IrUtilsKt$createStaticFunctionWithReceivers$1$2$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrValueParameter invoke(IrValueParameter it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                IrSimpleFunction irSimpleFunction = irSimpleFunctionCreateSimpleFunction;
                int i2 = intRef.element;
                intRef.element = i2 + 1;
                return IrUtilsKt.copyTo$default(it2, irSimpleFunction, null, i2, 0, 0, null, map, null, null, null, false, false, false, 8122, null);
            }
        }));
        IrValueParameter extensionReceiverParameter = oldFunction.getExtensionReceiverParameter();
        if (extensionReceiverParameter != null) {
            Name nameIdentifier2 = Name.identifier(AsmUtil.RECEIVER_PARAMETER_NAME);
            int i2 = intRef.element;
            intRef.element = i2 + 1;
            IrDeclarationOrigin.MOVED_EXTENSION_RECEIVER moved_extension_receiver = IrDeclarationOrigin.MOVED_EXTENSION_RECEIVER.INSTANCE;
            Intrinsics.checkNotNull(nameIdentifier2);
            irValueParameterCopyTo$default2 = copyTo$default(extensionReceiverParameter, irSimpleFunctionCreateSimpleFunction, moved_extension_receiver, i2, 0, 0, nameIdentifier2, map, null, null, null, false, false, false, 8088, null);
            list = list2;
        } else {
            list = list2;
            irValueParameterCopyTo$default2 = null;
        }
        org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(list, irValueParameterCopyTo$default2);
        CollectionsKt.addAll(list, SequencesKt.map(SequencesKt.drop(CollectionsKt.asSequence(oldFunction.getValueParameters()), oldFunction.getContextReceiverParametersCount()), new Function1<IrValueParameter, IrValueParameter>() { // from class: org.jetbrains.kotlin.ir.util.IrUtilsKt$createStaticFunctionWithReceivers$1$2$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final IrValueParameter invoke(IrValueParameter it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                IrSimpleFunction irSimpleFunction = irSimpleFunctionCreateSimpleFunction;
                int i3 = intRef.element;
                intRef.element = i3 + 1;
                return IrUtilsKt.copyTo$default(it2, irSimpleFunction, null, i3, 0, 0, null, map, null, null, null, false, false, false, 8122, null);
            }
        }));
        irSimpleFunctionCreateSimpleFunction.setValueParameters(CollectionsKt.build(listCreateListBuilder));
        remapMultiFieldValueClassStructure.invoke(oldFunction, irSimpleFunctionCreateSimpleFunction, null);
        if (z2) {
            irSimpleFunctionCreateSimpleFunction.setMetadata(oldFunction.getMetadata());
        }
        IrDeclarationsKt.copyAttributes(irSimpleFunctionCreateSimpleFunction, oldFunction instanceof IrAttributeContainer ? (IrAttributeContainer) oldFunction : null);
        return irSimpleFunctionCreateSimpleFunction;
    }

    private static final IrType createStaticFunctionWithReceivers$lambda$79$remap(IrFunction irFunction, IrSimpleFunction irSimpleFunction, Map<IrTypeParameter, ? extends IrTypeParameter> map, IrType irType) {
        return remapTypeParameters(irType, irFunction, irSimpleFunction, map);
    }

    public static final IrExpression irCastIfNeeded(IrBuilderWithScope irBuilderWithScope, IrExpression expression, IrType to) {
        Intrinsics.checkNotNullParameter(irBuilderWithScope, "<this>");
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(to, "to");
        return (Intrinsics.areEqual(expression.getType(), to) || IrTypePredicatesKt.isAny(to) || IrTypePredicatesKt.isNullableAny(to)) ? expression : ExpressionHelpersKt.irImplicitCast(irBuilderWithScope, expression, to);
    }

    public static final IrExpression unwrapBlock(IrContainerExpression irContainerExpression) {
        Intrinsics.checkNotNullParameter(irContainerExpression, "<this>");
        Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) irContainerExpression.getStatements());
        IrExpression irExpression = objSingleOrNull instanceof IrExpression ? (IrExpression) objSingleOrNull : null;
        return irExpression == null ? irContainerExpression : irExpression;
    }

    private static final List<IrTypeParameter> copyAndRenameConflictingTypeParametersFrom(IrSimpleFunction irSimpleFunction, List<? extends IrTypeParameter> list, Collection<? extends IrTypeParameter> collection) {
        Number number;
        String string;
        ArrayList arrayList = new ArrayList();
        List<? extends IrTypeParameter> list2 = list;
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
        Iterator<T> it = list2.iterator();
        while (it.hasNext()) {
            arrayList2.add(((IrTypeParameter) it.next()).getName().asString());
        }
        ArrayList arrayList3 = arrayList2;
        Collection<? extends IrTypeParameter> collection2 = collection;
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection2, 10));
        Iterator<T> it2 = collection2.iterator();
        while (it2.hasNext()) {
            arrayList4.add(((IrTypeParameter) it2.next()).getName().asString());
        }
        Set mutableSet = CollectionsKt.toMutableSet(CollectionsKt.plus((Collection) arrayList3, (Iterable) arrayList4));
        int i = 0;
        for (Object obj : list2) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrTypeParameter irTypeParameter = (IrTypeParameter) obj;
            if (!(collection2 instanceof Collection) || !collection2.isEmpty()) {
                Iterator<T> it3 = collection2.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        string = irTypeParameter.getName().asString();
                        Intrinsics.checkNotNull(string);
                        break;
                    }
                    if (Intrinsics.areEqual(((IrTypeParameter) it3.next()).getName().asString(), irTypeParameter.getName().asString())) {
                        String str = irTypeParameter.getName().asString() + "_I";
                        StringBuilder sbAppend = new StringBuilder().append(str);
                        Iterator it4 = SequencesKt.generateSequence(1, new Function1<Integer, Integer>() { // from class: org.jetbrains.kotlin.ir.util.IrUtilsKt$copyAndRenameConflictingTypeParametersFrom$1$newName$newName$1
                            public final Integer invoke(int i3) {
                                return Integer.valueOf(i3 + 1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Integer invoke(Integer num) {
                                return invoke(num.intValue());
                            }
                        }).iterator();
                        do {
                            if (!it4.hasNext()) {
                                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                            }
                            number = (Number) it4.next();
                        } while (mutableSet.contains(str + number.intValue()));
                        string = sbAppend.append(number.intValue()).toString();
                        mutableSet.add(string);
                        break;
                    }
                }
            } else {
                string = irTypeParameter.getName().asString();
                Intrinsics.checkNotNull(string);
                break;
                break;
            }
            IrTypeParameterBuilder irTypeParameterBuilder = new IrTypeParameterBuilder();
            irTypeParameterBuilder.updateFrom(irTypeParameter);
            irTypeParameterBuilder.setIndex(i);
            Name nameIdentifier = Name.identifier(string);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irTypeParameterBuilder.setName(nameIdentifier);
            IrSimpleFunction irSimpleFunction2 = irSimpleFunction;
            arrayList.add(DeclarationBuildersKt.buildTypeParameter(irSimpleFunction2.getFactory(), irTypeParameterBuilder, irSimpleFunction2));
            i = i2;
        }
        List<Pair> listZip = CollectionsKt.zip(list2, arrayList);
        Map map = MapsKt.toMap(listZip);
        for (Pair pair : listZip) {
            copySuperTypesFrom((IrTypeParameter) pair.component2(), (IrTypeParameter) pair.component1(), map);
        }
        irSimpleFunction.setTypeParameters(MemoryOptimizedCollectionUtilKt.memoryOptimizedPlus((List) irSimpleFunction.getTypeParameters(), (List) arrayList));
        return arrayList;
    }

    public static final boolean isSuspend(IrSymbol irSymbol) {
        Intrinsics.checkNotNullParameter(irSymbol, "<this>");
        return (irSymbol instanceof IrSimpleFunctionSymbol) && ((IrSimpleFunctionSymbol) irSymbol).getOwner().getIsSuspend();
    }

    public static /* synthetic */ List allOverridden$default(IrOverridableDeclaration irOverridableDeclaration, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return allOverridden(irOverridableDeclaration, z);
    }

    public static final <T extends IrOverridableDeclaration<?>> List<T> allOverridden(T t, boolean z) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        ArrayList arrayList = new ArrayList();
        if (z) {
            arrayList.add(t);
        }
        while (true) {
            List overriddenSymbols = t.getOverriddenSymbols();
            int size = overriddenSymbols.size();
            if (size == 0) {
                return arrayList;
            }
            if (size == 1) {
                IrSymbolOwner owner = ((IrSymbol) overriddenSymbols.get(0)).getOwner();
                Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type T of org.jetbrains.kotlin.ir.util.IrUtilsKt.allOverridden");
                t = (T) owner;
                arrayList.add(t);
            } else {
                Set mutableSet = CollectionsKt.toMutableSet(arrayList);
                computeAllOverridden(t, mutableSet);
                return CollectionsKt.toList(mutableSet);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static final <T extends IrOverridableDeclaration<?>> void computeAllOverridden(T t, Set<T> set) {
        Iterator it = t.getOverriddenSymbols().iterator();
        while (it.hasNext()) {
            IrSymbolOwner owner = ((IrSymbol) it.next()).getOwner();
            Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type T of org.jetbrains.kotlin.ir.util.IrUtilsKt.computeAllOverridden");
            IrOverridableDeclaration irOverridableDeclaration = (IrOverridableDeclaration) owner;
            if (set.add(irOverridableDeclaration)) {
                computeAllOverridden(irOverridableDeclaration, set);
            }
        }
    }

    public static final IrSimpleType getKFunctionType(IrBuiltIns irBuiltIns, IrType returnType, List<? extends IrType> parameterTypes) {
        Intrinsics.checkNotNullParameter(irBuiltIns, "<this>");
        Intrinsics.checkNotNullParameter(returnType, "returnType");
        Intrinsics.checkNotNullParameter(parameterTypes, "parameterTypes");
        return IrTypesKt.typeWith(irBuiltIns.kFunctionN(parameterTypes.size()), (List<? extends IrType>) CollectionsKt.plus((Collection<? extends IrType>) parameterTypes, returnType));
    }

    public static final boolean isComposite(IdSignature idSignature) {
        return idSignature instanceof IdSignature.CompositeSignature;
    }

    public static final boolean isToString(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return Intrinsics.areEqual(irFunction.getName(), OperatorNameConventions.TO_STRING) && irFunction.getExtensionReceiverParameter() == null && irFunction.getContextReceiverParametersCount() == 0 && irFunction.getValueParameters().isEmpty();
    }

    public static final boolean isHashCode(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return Intrinsics.areEqual(irFunction.getName(), OperatorNameConventions.HASH_CODE) && irFunction.getExtensionReceiverParameter() == null && irFunction.getContextReceiverParametersCount() == 0 && irFunction.getValueParameters().isEmpty();
    }

    public static final boolean isEquals(IrFunction irFunction) {
        IrValueParameter irValueParameter;
        IrType type;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return Intrinsics.areEqual(irFunction.getName(), OperatorNameConventions.EQUALS) && irFunction.getExtensionReceiverParameter() == null && irFunction.getContextReceiverParametersCount() == 0 && (irValueParameter = (IrValueParameter) CollectionsKt.singleOrNull((List) irFunction.getValueParameters())) != null && (type = irValueParameter.getType()) != null && IrTypePredicatesKt.isNullableAny(type);
    }

    public static final boolean isValueClassTypedEquals(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        IrDeclarationParent parent = irFunction.getParent();
        IrClass irClass = parent instanceof IrClass ? (IrClass) parent : null;
        if (irClass == null) {
            return false;
        }
        IrSimpleType starProjectedType = IrTypesKt.getStarProjectedType(irClass.getSymbol());
        if (Intrinsics.areEqual(irFunction.getName(), OperatorNameConventions.EQUALS)) {
            return (IrTypePredicatesKt.isBoolean(irFunction.getReturnType()) || IrTypePredicatesKt.isNothing(irFunction.getReturnType())) && irFunction.getValueParameters().size() == 1 && Intrinsics.areEqual(irFunction.getValueParameters().get(0).getType(), starProjectedType) && irFunction.getContextReceiverParametersCount() == 0 && irFunction.getExtensionReceiverParameter() == null && irClass.getIsValue();
        }
        return false;
    }

    public static final int getPreviousOffset(int i) {
        if (i == -2) {
            return -2;
        }
        if (i == -1) {
            return -1;
        }
        if (i == 0) {
            return 0;
        }
        if (i > 0) {
            return i - 1;
        }
        throw new IllegalStateException("Invalid offset appear".toString());
    }

    public static final IrDeclaration extractRelatedDeclaration(IrAttributeContainer irAttributeContainer) {
        Intrinsics.checkNotNullParameter(irAttributeContainer, "<this>");
        if (irAttributeContainer instanceof IrClass) {
            return (IrDeclaration) irAttributeContainer;
        }
        if (irAttributeContainer instanceof IrFunctionExpression) {
            return ((IrFunctionExpression) irAttributeContainer).getFunction();
        }
        if (irAttributeContainer instanceof IrFunctionReference) {
            return ((IrFunctionReference) irAttributeContainer).getSymbol().getOwner();
        }
        return null;
    }

    public static final /* synthetic */ <Symbol extends IrSymbol> Void unexpectedSymbolKind(IrSymbol irSymbol) {
        Intrinsics.checkNotNullParameter(irSymbol, "<this>");
        StringBuilder sb = new StringBuilder("Unexpected kind of ");
        Intrinsics.reifiedOperationMarker(4, NativeSymbol.CLASS_NAME);
        throw new IllegalArgumentException(sb.append(IrSymbol.class.getTypeName()).append(": ").append(irSymbol).toString());
    }

    private static final IrConst<?> toIrConstOrNull(Object obj, IrType irType, int i, int i2) {
        IrConst<?> irConstString;
        if (obj == null) {
            return IrConstImpl.INSTANCE.constNull(i, i2, irType);
        }
        IrType irTypeRemoveAnnotations = IrTypesKt.removeAnnotations(IrTypesKt.makeNotNull(irType));
        PrimitiveType primitiveType = IrTypePredicatesKt.getPrimitiveType(irType);
        switch (primitiveType == null ? -1 : WhenMappings.$EnumSwitchMapping$1[primitiveType.ordinal()]) {
            case -1:
                UnsignedType unsignedType = IrTypePredicatesKt.getUnsignedType(irTypeRemoveAnnotations);
                int i3 = unsignedType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[unsignedType.ordinal()];
                if (i3 == -1) {
                    irConstString = IrTypePredicatesKt.isString(irTypeRemoveAnnotations) ? IrConstImpl.INSTANCE.string(i, i2, irTypeRemoveAnnotations, (String) obj) : null;
                } else if (i3 == 1) {
                    irConstString = IrConstImpl.INSTANCE.m7207byte(i, i2, irTypeRemoveAnnotations, ((Number) obj).byteValue());
                } else if (i3 == 2) {
                    irConstString = IrConstImpl.INSTANCE.m7213short(i, i2, irTypeRemoveAnnotations, ((Number) obj).shortValue());
                } else if (i3 == 3) {
                    irConstString = IrConstImpl.INSTANCE.m7211int(i, i2, irTypeRemoveAnnotations, ((Number) obj).intValue());
                } else if (i3 == 4) {
                    irConstString = IrConstImpl.INSTANCE.m7212long(i, i2, irTypeRemoveAnnotations, ((Number) obj).longValue());
                } else {
                    throw new NoWhenBranchMatchedException();
                }
                return irConstString;
            case 0:
            default:
                throw new NoWhenBranchMatchedException();
            case 1:
                return IrConstImpl.INSTANCE.m7206boolean(i, i2, irTypeRemoveAnnotations, ((Boolean) obj).booleanValue());
            case 2:
                return IrConstImpl.INSTANCE.m7208char(i, i2, irTypeRemoveAnnotations, ((Character) obj).charValue());
            case 3:
                return IrConstImpl.INSTANCE.m7207byte(i, i2, irTypeRemoveAnnotations, ((Number) obj).byteValue());
            case 4:
                return IrConstImpl.INSTANCE.m7213short(i, i2, irTypeRemoveAnnotations, ((Number) obj).shortValue());
            case 5:
                return IrConstImpl.INSTANCE.m7211int(i, i2, irTypeRemoveAnnotations, ((Number) obj).intValue());
            case 6:
                return IrConstImpl.INSTANCE.m7210float(i, i2, irTypeRemoveAnnotations, ((Number) obj).floatValue());
            case 7:
                return IrConstImpl.INSTANCE.m7212long(i, i2, irTypeRemoveAnnotations, ((Number) obj).longValue());
            case 8:
                return IrConstImpl.INSTANCE.m7209double(i, i2, irTypeRemoveAnnotations, ((Number) obj).doubleValue());
        }
    }

    public static /* synthetic */ IrConst toIrConst$default(Object obj, IrType irType, int i, int i2, int i3, Object obj2) {
        if ((i3 & 2) != 0) {
            i = -2;
        }
        if ((i3 & 4) != 0) {
            i2 = -2;
        }
        return toIrConst(obj, irType, i, i2);
    }

    public static final IrConst<?> toIrConst(Object obj, IrType irType, int i, int i2) {
        Intrinsics.checkNotNullParameter(irType, "irType");
        IrConst<?> irConstOrNull = toIrConstOrNull(obj, irType, i, i2);
        if (irConstOrNull != null) {
            return irConstOrNull;
        }
        throw new UnsupportedOperationException("Unsupported const element type " + RenderIrElementKt.render$default(IrTypesKt.makeNotNull(irType), (DumpIrTreeOptions) null, 1, (Object) null));
    }

    public static final IrConstructor addSimpleDelegatingConstructor(IrClass irClass, IrConstructor superConstructor, IrBuiltIns irBuiltIns, boolean z, IrDeclarationOrigin irDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(superConstructor, "superConstructor");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        IrFactory factory = irClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setStartOffset(irClass.getStartOffset());
        irFunctionBuilder.setEndOffset(irClass.getEndOffset());
        irFunctionBuilder.setOrigin(irDeclarationOrigin == null ? irClass.getOrigin() : irDeclarationOrigin);
        irFunctionBuilder.setVisibility(superConstructor.getVisibility());
        irFunctionBuilder.setPrimary(z);
        irFunctionBuilder.setReturnType(getDefaultType(irClass));
        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
        irClass.getDeclarations().add(irConstructorBuildConstructor);
        irConstructorBuildConstructor.setParent(irClass);
        List<IrValueParameter> valueParameters = superConstructor.getValueParameters();
        ArrayList arrayList = new ArrayList(valueParameters.size());
        int i = 0;
        for (Object obj : valueParameters) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            arrayList.add(copyTo$default((IrValueParameter) obj, irConstructorBuildConstructor, null, i, 0, 0, null, null, null, null, null, false, false, false, 8186, null));
            i = i2;
        }
        irConstructorBuildConstructor.setValueParameters(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
        IrFactory factory2 = irClass.getFactory();
        int startOffset = irClass.getStartOffset();
        int endOffset = irClass.getEndOffset();
        IrExpression[] irExpressionArr = new IrExpression[2];
        IrDelegatingConstructorCallImpl irDelegatingConstructorCallImpl = new IrDelegatingConstructorCallImpl(irClass.getStartOffset(), irClass.getEndOffset(), irBuiltIns.getUnitType(), superConstructor.getSymbol(), 0, superConstructor.getValueParameters().size());
        int i3 = 0;
        for (Object obj2 : irConstructorBuildConstructor.getValueParameters()) {
            int i4 = i3 + 1;
            if (i3 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IrValueParameter irValueParameter = (IrValueParameter) obj2;
            irDelegatingConstructorCallImpl.putValueArgument(i3, new IrGetValueImpl(irDelegatingConstructorCallImpl.getStartOffset(), irDelegatingConstructorCallImpl.getEndOffset(), irValueParameter.getType(), irValueParameter.getSymbol(), null, 16, null));
            i3 = i4;
        }
        Unit unit = Unit.INSTANCE;
        irExpressionArr[0] = irDelegatingConstructorCallImpl;
        irExpressionArr[1] = new IrInstanceInitializerCallImpl(irClass.getStartOffset(), irClass.getEndOffset(), irClass.getSymbol(), irBuiltIns.getUnitType());
        irConstructorBuildConstructor.setBody(IrFactoryHelpersKt.createBlockBody(factory2, startOffset, endOffset, (List<? extends IrStatement>) CollectionsKt.listOf((Object[]) irExpressionArr)));
        return irConstructorBuildConstructor;
    }

    public static final IrTypeParameter copyToWithoutSuperTypes(IrTypeParameter irTypeParameter, IrTypeParametersContainer target, int i, IrDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(irTypeParameter, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(origin, "origin");
        IrTypeParameterBuilder irTypeParameterBuilder = new IrTypeParameterBuilder();
        irTypeParameterBuilder.updateFrom(irTypeParameter);
        irTypeParameterBuilder.setName(irTypeParameter.getName());
        irTypeParameterBuilder.setOrigin(origin);
        irTypeParameterBuilder.setIndex(i);
        return DeclarationBuildersKt.buildTypeParameter(target.getFactory(), irTypeParameterBuilder, target);
    }

    public static final IrClass createSpecialAnnotationClass(IrFactory irFactory, FqName fqn, IrPackageFragment parent) {
        Intrinsics.checkNotNullParameter(irFactory, "<this>");
        Intrinsics.checkNotNullParameter(fqn, "fqn");
        Intrinsics.checkNotNullParameter(parent, "parent");
        IrClassBuilder irClassBuilder = new IrClassBuilder();
        irClassBuilder.setKind(ClassKind.ANNOTATION_CLASS);
        Name nameShortName = fqn.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        irClassBuilder.setName(nameShortName);
        IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
        createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
        irClassBuildClass.setParent(parent);
        IrFactory factory = irClassBuildClass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setPrimary(true);
        irFunctionBuilder.setReturnType(getDefaultType(irClassBuildClass));
        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
        irClassBuildClass.getDeclarations().add(irConstructorBuildConstructor);
        irConstructorBuildConstructor.setParent(irClassBuildClass);
        return irClassBuildClass;
    }
}
