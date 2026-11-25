package org.jetbrains.kotlin.codegen;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.UnsignedTypes;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.context.CodegenContext;
import org.jetbrains.kotlin.codegen.context.FieldOwnerContext;
import org.jetbrains.kotlin.codegen.context.MultifileClassFacadeContext;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.inline.ReificationArgument;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeParametersUsages;
import org.jetbrains.kotlin.codegen.intrinsics.TypeIntrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilterKt;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.idea.MainFunctionDetector;
import org.jetbrains.kotlin.load.java.DescriptorsJvmAbiUtil;
import org.jetbrains.kotlin.load.java.SpecialGenericSignatures;
import org.jetbrains.kotlin.load.java.descriptors.JavaCallableMemberDescriptor;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunctionLiteral;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtLambdaArgument;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.annotations.AnnotationUtilKt;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKt;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmMethodGenericSignature;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.TransientReceiver;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.utils.DFS;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;

/* JADX INFO: compiled from: codegenUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000È\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a.\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014\u001a:\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\n\u0010!\u001a\u0006\u0012\u0002\b\u00030\"\u001a\u001e\u0010#\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0001\u001a0\u0010$\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010(\u001a\u00020\u0014H\u0002\u001a \u0010)\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010*\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0014H\u0002\u001a\u0018\u0010+\u001a\u0004\u0018\u00010\u00042\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/\u001a&\u00100\u001a\u00020\r2\u0006\u00101\u001a\u0002022\f\u00103\u001a\b\u0012\u0004\u0012\u000205042\b\u00106\u001a\u0004\u0018\u000107\u001a\u0006\u00108\u001a\u000207\u001a\"\u00109\u001a\u00020\r2\u0006\u0010:\u001a\u00020;2\n\u0010<\u001a\u0006\u0012\u0002\b\u00030=2\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u0016\u0010>\u001a\u00020\r2\u0006\u0010,\u001a\u00020-2\u0006\u0010?\u001a\u00020@\u001a.\u0010A\u001a\b\u0012\u0004\u0012\u00020B042\f\u0010C\u001a\b\u0012\u0004\u0012\u00020B042\n\u0010D\u001a\u0006\u0012\u0002\b\u00030\"2\u0006\u0010\u0017\u001a\u00020\u0018\u001a\u0014\u0010E\u001a\b\u0012\u0004\u0012\u00020\u0004042\u0006\u0010F\u001a\u00020\u0004\u001a\u000e\u0010G\u001a\u00020\u001c2\u0006\u0010H\u001a\u00020\u001c\u001a\u001c\u0010\u0012\u001a\u00020\u0001*\u0004\u0018\u00010I2\u0006\u0010J\u001a\u00020K2\u0006\u0010.\u001a\u00020/\u001a\u0012\u0010\u0012\u001a\u00020\u0001*\u00020\u00112\u0006\u0010J\u001a\u00020K\u001a\n\u0010L\u001a\u00020\u0007*\u00020\u0004\u001a<\u0010M\u001a\u00020\r*\u00020N2\u0006\u0010F\u001a\u00020O2\u0006\u0010P\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010Q\u001a\u000e\u0012\u0004\u0012\u00020O\u0012\u0004\u0012\u00020\r0RH\u0086\bø\u0001\u0000\u001a \u0010S\u001a\u0010\u0012\u0004\u0012\u00020U\u0012\u0004\u0012\u00020V\u0018\u00010T*\u00020W2\u0006\u0010X\u001a\u00020Y\u001a\u0012\u0010Z\u001a\u00020[*\u00020W2\u0006\u0010*\u001a\u00020Y\u001a\u0016\u0010\\\u001a\b\u0012\u0004\u0012\u00020]04*\b\u0012\u0004\u0012\u00020]0^\u001a\u001a\u0010_\u001a\u00020O*\u0002022\u000e\u0010`\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0a\u001a\u001a\u0010b\u001a\u00020O*\u0002022\u000e\u0010`\u001a\n\u0012\u0006\b\u0001\u0012\u00020\t0a\u001aU\u0010c\u001a\u00020\r*\u00020\u001a2\u0006\u0010d\u001a\u00020\u00042\u0006\u0010e\u001a\u00020\u00072\u0006\u0010f\u001a\u00020g2\b\u0010h\u001a\u0004\u0018\u00010&2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0017\u001a\u00020\u00182\u0017\u0010i\u001a\u0013\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\r0R¢\u0006\u0002\bj\u001a\"\u0010k\u001a\u00020\r*\u00020\u000f2\u0006\u0010l\u001a\u00020N2\u0006\u0010m\u001a\u00020\u00012\u0006\u0010n\u001a\u00020\u0004\u001a\n\u0010o\u001a\u00020\u0014*\u00020\u0004\u001a\n\u0010p\u001a\u00020\u0014*\u00020q\u001a\n\u0010r\u001a\u00020\u0014*\u00020\u001c\u001a\n\u0010s\u001a\u00020\u0014*\u00020\u0011\u001a \u0010t\u001a\u00020\u0014*\u00020\t2\u0012\u0010u\u001a\u000e\u0012\u0004\u0012\u00020v\u0012\u0004\u0012\u00020\u00140RH\u0002\u001a\n\u0010w\u001a\u00020\u0014*\u00020\t\u001a\n\u0010x\u001a\u00020\u0014*\u00020\t\u001a\n\u0010y\u001a\u00020\u0014*\u00020\t\u001a\n\u0010z\u001a\u00020\u0014*\u00020\u001c\u001a\n\u0010{\u001a\u00020\u0014*\u00020|\u001a\n\u0010}\u001a\u00020\u0014*\u00020~\u001a\u0016\u0010\u0010\u001a\u0004\u0018\u00010\u0011*\u0004\u0018\u00010I2\u0006\u0010.\u001a\u00020/\u001a\u000e\u0010\u007f\u001a\u00030\u0080\u0001*\u00030\u0080\u0001H\u0000\u001a\u0019\u0010\u0081\u0001\u001a\u0004\u0018\u00010\u0004*\u00030\u0082\u00012\t\u0010\u0083\u0001\u001a\u0004\u0018\u00010\u0004\u001a\f\u0010\u0084\u0001\u001a\u00020\u0001*\u00030\u0085\u0001\u001a\f\u0010\u0086\u0001\u001a\u00020\u0004*\u00030\u0085\u0001\u001a\u001e\u0010\u0087\u0001\u001a\u0003H\u0088\u0001\"\t\b\u0000\u0010\u0088\u0001*\u00020\t*\u0003H\u0088\u0001¢\u0006\u0003\u0010\u0089\u0001\u001a@\u0010\u008a\u0001\u001a\u00020\r*\u00020N2\u0006\u0010*\u001a\u00020\u00012%\u0010\u008b\u0001\u001a \u0012\u0016\u0012\u00140\u0007¢\u0006\u000f\b\u008c\u0001\u0012\n\b\u008d\u0001\u0012\u0005\b\b(\u008e\u0001\u0012\u0004\u0012\u00020\r0RH\u0086\bø\u0001\u0000\u001a#\u0010\u008f\u0001\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010T04*\b\u0012\u0004\u0012\u00020\u00010^\"\u0016\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0018\u0010\u0003\u001a\n \u0002*\u0004\u0018\u00010\u00040\u00048\u0000X\u0081\u0004¢\u0006\u0002\n\u0000\"\u0016\u0010\u0005\u001a\n \u0002*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000\"\u0015\u0010\b\u001a\u00020\u0007*\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0090\u0001"}, d2 = {"ARRAY_OF_STRINGS_TYPE", "Lorg/jetbrains/org/objectweb/asm/Type;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "JAVA_LANG_DEPRECATED", "", "METHOD_DESCRIPTOR_FOR_MAIN", "STRING_UTF8_ENCODING_BYTE_LIMIT", "", "arity", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "getArity", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)I", "generateAsCast", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "kotlinType", "Lorg/jetbrains/kotlin/types/KotlinType;", "asmType", "isSafe", "", "unifiedNullChecks", "generateBridgeForMainFunctionIfNecessary", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "packagePartClassBuilder", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "functionDescriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "signatureOfRealDeclaration", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmMethodGenericSignature;", "origin", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "parentContext", "Lorg/jetbrains/kotlin/codegen/context/CodegenContext;", "generateIsCheck", "generateLambdaForRunSuspend", "originElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "packagePartClassInternalName", "parameterless", "generateNullCheckForNonSafeAs", ModuleXmlParser.TYPE, "getCallLabelForLambdaArgument", "declaration", "Lorg/jetbrains/kotlin/psi/KtFunctionLiteral;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "initializeVariablesForDestructuredLambdaParameters", "codegen", "Lorg/jetbrains/kotlin/codegen/ExpressionCodegen;", "valueParameters", "", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "endLabel", "Lorg/jetbrains/org/objectweb/asm/Label;", "linkedLabel", "populateCompanionBackingFieldNamesToOuterContextIfNeeded", "companion", "Lorg/jetbrains/kotlin/psi/KtObjectDeclaration;", "outerContext", "Lorg/jetbrains/kotlin/codegen/context/FieldOwnerContext;", "recordCallLabelForLambdaArgument", "bindingTrace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "sortTopLevelClassesAndPrepareContextForSealedClasses", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "classOrObjects", "context", "splitStringConstant", "value", "unwrapInitialSignatureDescriptor", "function", "Lorg/jetbrains/kotlin/psi/KtExpression;", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "encodedUTF8Size", "evaluateOnce", "Lorg/jetbrains/kotlin/codegen/FrameMap;", "Lorg/jetbrains/kotlin/codegen/StackValue;", "asType", "body", "Lkotlin/Function1;", "extractReificationArgument", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "Lorg/jetbrains/kotlin/codegen/inline/ReificationArgument;", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "initialType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "extractUsedReifiedParameters", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", "filterOutDescriptorsWithSpecialNames", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", "", "generateCallReceiver", "call", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "generateCallSingleArgument", "generateMethod", "debugString", "access", "method", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, CompilerOptions.GENERATE, "Lkotlin/ExtensionFunctionType;", "generateNewInstanceDupAndPlaceBeforeStackTop", "frameMap", "topStackType", "newInstanceInternalName", "isDefinitelyFitEncodingLimit", "isDefinitelyNotDefaultImplsMethod", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "isGenericToArray", "isInlineClassTypeWithPrimitiveEquality", "isJvmStaticIn", "predicate", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "isJvmStaticInCompanionObject", "isJvmStaticInInlineClass", "isJvmStaticInObjectOrClassOrInterface", "isNonGenericToArray", "isPossiblyUninitializedSingleton", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "isToArrayFromCollection", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "linkWithLabel", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "replaceValueParametersIn", "Lorg/jetbrains/kotlin/load/java/SpecialGenericSignatures$SpecialSignatureInfo;", "sourceSignature", "topLevelClassAsmType", "Lorg/jetbrains/kotlin/name/FqName;", "topLevelClassInternalName", "unwrapFrontendVersion", "D", "(Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "useTmpVar", "block", "Lkotlin/ParameterName;", "name", "index", "withVariableIndices", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CodegenUtilKt {
    private static final Type ARRAY_OF_STRINGS_TYPE;
    public static final String JAVA_LANG_DEPRECATED = Type.getType((Class<?>) Deprecated.class).getDescriptor();
    private static final String METHOD_DESCRIPTOR_FOR_MAIN;
    public static final int STRING_UTF8_ENCODING_BYTE_LIMIT = 65535;

    static {
        Type type = Type.getType("[Ljava/lang/String;");
        ARRAY_OF_STRINGS_TYPE = type;
        METHOD_DESCRIPTOR_FOR_MAIN = Type.getMethodDescriptor(Type.VOID_TYPE, type);
    }

    public static final void generateIsCheck(InstructionAdapter v, KotlinType kotlinType, Type asmType) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        Intrinsics.checkNotNullParameter(asmType, "asmType");
        if (TypeUtils.isNullableType(kotlinType)) {
            Label label = new Label();
            Label label2 = new Label();
            v.dup();
            v.ifnull(label);
            TypeIntrinsics.instanceOf(v, kotlinType, asmType);
            v.goTo(label2);
            v.mark(label);
            v.pop();
            v.iconst(1);
            v.mark(label2);
            return;
        }
        TypeIntrinsics.instanceOf(v, kotlinType, asmType);
    }

    public static final void generateAsCast(InstructionAdapter v, KotlinType kotlinType, Type asmType, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(kotlinType, "kotlinType");
        Intrinsics.checkNotNullParameter(asmType, "asmType");
        if (!z) {
            if (!TypeUtils.isNullableType(kotlinType)) {
                generateNullCheckForNonSafeAs(v, kotlinType, z2);
            }
        } else {
            v.dup();
            TypeIntrinsics.instanceOf(v, kotlinType, asmType);
            Label label = new Label();
            v.ifne(label);
            v.pop();
            v.aconst(null);
            v.mark(label);
        }
        TypeIntrinsics.checkcast(v, kotlinType, asmType, z);
    }

    private static final void generateNullCheckForNonSafeAs(InstructionAdapter instructionAdapter, KotlinType kotlinType, boolean z) {
        instructionAdapter.dup();
        Label label = new Label();
        instructionAdapter.ifnonnull(label);
        AsmUtil.genThrow(instructionAdapter, z ? "java/lang/NullPointerException" : "kotlin/TypeCastException", "null cannot be cast to non-null type " + DescriptorRenderer.FQ_NAMES_IN_TYPES.renderType(kotlinType));
        instructionAdapter.mark(label);
    }

    public static final String replaceValueParametersIn(SpecialGenericSignatures.SpecialSignatureInfo specialSignatureInfo, String str) {
        Intrinsics.checkNotNullParameter(specialSignatureInfo, "<this>");
        String valueParametersSignature = specialSignatureInfo.getValueParametersSignature();
        if (valueParametersSignature == null || str == null) {
            return null;
        }
        return new Regex("^\\(.*\\)").replace(str, "(" + valueParametersSignature + ')');
    }

    public static final void populateCompanionBackingFieldNamesToOuterContextIfNeeded(KtObjectDeclaration companion, FieldOwnerContext<?> outerContext, GenerationState state) {
        Intrinsics.checkNotNullParameter(companion, "companion");
        Intrinsics.checkNotNullParameter(outerContext, "outerContext");
        Intrinsics.checkNotNullParameter(state, "state");
        ClassDescriptor classDescriptor = (ClassDescriptor) state.getBindingContext().get(BindingContext.CLASS, companion);
        if (classDescriptor != null) {
            ClassDescriptor classDescriptor2 = classDescriptor;
            if (!ErrorUtils.isError(classDescriptor2) && DescriptorsJvmAbiUtil.isClassCompanionObjectWithBackingFieldsInOuter(classDescriptor2)) {
                List<KtDeclaration> declarations = companion.getDeclarations();
                ArrayList<KtProperty> arrayList = new ArrayList();
                for (Object obj : declarations) {
                    if (obj instanceof KtProperty) {
                        arrayList.add(obj);
                    }
                }
                for (KtProperty ktProperty : arrayList) {
                    VariableDescriptor variableDescriptor = (VariableDescriptor) state.getBindingContext().get(BindingContext.VARIABLE, ktProperty);
                    if (variableDescriptor instanceof PropertyDescriptor) {
                        outerContext.getFieldName((PropertyDescriptor) variableDescriptor, ktProperty.hasDelegate());
                    }
                }
            }
        }
    }

    private static final void sortTopLevelClassesAndPrepareContextForSealedClasses$prepareContextIfNeeded(CodegenContext<?> codegenContext, GenerationState generationState, ClassDescriptor classDescriptor) {
        if (DescriptorUtils.isSealedClass(classDescriptor)) {
            Intrinsics.checkNotNull(classDescriptor);
            codegenContext.intoClass(classDescriptor, OwnerKind.IMPLEMENTATION, generationState);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final List<KtClassOrObject> sortTopLevelClassesAndPrepareContextForSealedClasses(List<? extends KtClassOrObject> classOrObjects, CodegenContext<?> context, GenerationState state) {
        Intrinsics.checkNotNullParameter(classOrObjects, "classOrObjects");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(state, "state");
        int size = classOrObjects.size();
        if (size == 0) {
            return CollectionsKt.emptyList();
        }
        if (size == 1) {
            sortTopLevelClassesAndPrepareContextForSealedClasses$prepareContextIfNeeded(context, state, (ClassDescriptor) state.getBindingContext().get(BindingContext.CLASS, CollectionsKt.first((List) classOrObjects)));
            return classOrObjects;
        }
        ArrayList arrayList = new ArrayList(classOrObjects.size());
        final LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (KtClassOrObject ktClassOrObject : classOrObjects) {
            ClassDescriptor classDescriptor = (ClassDescriptor) state.getBindingContext().get(BindingContext.CLASS, ktClassOrObject);
            if (classDescriptor == null) {
                arrayList.add(ktClassOrObject);
            } else {
                sortTopLevelClassesAndPrepareContextForSealedClasses$prepareContextIfNeeded(context, state, classDescriptor);
                linkedHashMap.put(classDescriptor, ktClassOrObject);
            }
        }
        Set setKeySet = linkedHashMap.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
        List list = DFS.topologicalOrder(CollectionsKt.reversed(setKeySet), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.codegen.CodegenUtilKt$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return CodegenUtilKt.sortTopLevelClassesAndPrepareContextForSealedClasses$lambda$7(linkedHashMap, (ClassDescriptor) obj);
            }
        });
        Intrinsics.checkNotNull(list);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Object obj = linkedHashMap.get((ClassDescriptor) it.next());
            Intrinsics.checkNotNull(obj);
            arrayList.add((KtClassOrObject) obj);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable sortTopLevelClassesAndPrepareContextForSealedClasses$lambda$7(LinkedHashMap descriptorToPsi, ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(descriptorToPsi, "$descriptorToPsi");
        Collection<KotlinType> collectionMo7438getSupertypes = classDescriptor.getTypeConstructor().mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        Collection<KotlinType> collection = collectionMo7438getSupertypes;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            ClassifierDescriptor classifierDescriptorMo7434getDeclarationDescriptor = ((KotlinType) it.next()).getConstructor().getThis$0();
            arrayList.add(classifierDescriptorMo7434getDeclarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) classifierDescriptorMo7434getDeclarationDescriptor : null);
        }
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            Set setKeySet = descriptorToPsi.keySet();
            Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
            if (CollectionsKt.contains(setKeySet, (ClassDescriptor) obj)) {
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    public static final boolean isDefinitelyNotDefaultImplsMethod(CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        return (callableMemberDescriptor instanceof JavaCallableMemberDescriptor) || callableMemberDescriptor.getAnnotations().hasAnnotation(PlatformDependentDeclarationFilterKt.getPLATFORM_DEPENDENT_ANNOTATION_FQ_NAME());
    }

    public static final void generateMethod(ClassBuilder classBuilder, String debugString, int i, Method method, PsiElement psiElement, JvmDeclarationOrigin origin, GenerationState state, Function1<? super InstructionAdapter, Unit> generate) {
        Intrinsics.checkNotNullParameter(classBuilder, "<this>");
        Intrinsics.checkNotNullParameter(debugString, "debugString");
        Intrinsics.checkNotNullParameter(method, "method");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(generate, "generate");
        MethodVisitor methodVisitorNewMethod = classBuilder.newMethod(origin, i, method.getName(), method.getDescriptor(), null, null);
        Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
        if (state.getClassBuilderMode().generateBodies) {
            InstructionAdapter instructionAdapter = new InstructionAdapter(methodVisitorNewMethod);
            instructionAdapter.visitCode();
            generate.invoke(instructionAdapter);
            instructionAdapter.areturn(method.getReturnType());
            FunctionCodegen.endVisit(methodVisitorNewMethod, debugString, psiElement);
        }
    }

    public static final boolean isJvmStaticInObjectOrClassOrInterface(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        return isJvmStaticIn(callableDescriptor, new Function1<DeclarationDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.codegen.CodegenUtilKt.isJvmStaticInObjectOrClassOrInterface.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(DeclarationDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(DescriptorUtils.isNonCompanionObject(it) || DescriptorUtils.isClassOrEnumClass(it) || JvmCodegenUtil.isJvmInterface(it));
            }
        });
    }

    public static final boolean isJvmStaticInCompanionObject(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        return isJvmStaticIn(callableDescriptor, new Function1<DeclarationDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.codegen.CodegenUtilKt.isJvmStaticInCompanionObject.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(DeclarationDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(DescriptorUtils.isCompanionObject(it));
            }
        });
    }

    public static final boolean isJvmStaticInInlineClass(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        return isJvmStaticIn(callableDescriptor, new Function1<DeclarationDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.codegen.CodegenUtilKt.isJvmStaticInInlineClass.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(DeclarationDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(InlineClassesUtilsKt.isInlineClass(it));
            }
        });
    }

    private static final boolean isJvmStaticIn(CallableDescriptor callableDescriptor, Function1<? super DeclarationDescriptor, Boolean> function1) {
        if (callableDescriptor instanceof PropertyAccessorDescriptor) {
            PropertyDescriptor correspondingProperty = ((PropertyAccessorDescriptor) callableDescriptor).getCorrespondingProperty();
            Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
            DeclarationDescriptor containingDeclaration = correspondingProperty.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
            if (function1.invoke(containingDeclaration).booleanValue() && (AnnotationUtilKt.hasJvmStaticAnnotation(callableDescriptor) || AnnotationUtilKt.hasJvmStaticAnnotation(correspondingProperty))) {
                return true;
            }
        } else {
            DeclarationDescriptor containingDeclaration2 = callableDescriptor.getContainingDeclaration();
            Intrinsics.checkNotNullExpressionValue(containingDeclaration2, "getContainingDeclaration(...)");
            if (function1.invoke(containingDeclaration2).booleanValue() && AnnotationUtilKt.hasJvmStaticAnnotation(callableDescriptor)) {
                return true;
            }
        }
        return false;
    }

    public static final List<VariableDescriptor> filterOutDescriptorsWithSpecialNames(Collection<? extends VariableDescriptor> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        ArrayList arrayList = new ArrayList();
        for (Object obj : collection) {
            if (!((VariableDescriptor) obj).getShortName().isSpecial()) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    public static final Type asmType(KotlinType kotlinType, KotlinTypeMapper typeMapper) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
        return KotlinTypeMapper.mapType$default(typeMapper, kotlinType, null, null, 6, null);
    }

    public static final Type asmType(KtExpression ktExpression, KotlinTypeMapper typeMapper, BindingContext bindingContext) {
        Type typeAsmType;
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        KotlinType kotlinType = kotlinType(ktExpression, bindingContext);
        if (kotlinType != null && (typeAsmType = asmType(kotlinType, typeMapper)) != null) {
            return typeAsmType;
        }
        Type VOID_TYPE = Type.VOID_TYPE;
        Intrinsics.checkNotNullExpressionValue(VOID_TYPE, "VOID_TYPE");
        return VOID_TYPE;
    }

    public static final KotlinType kotlinType(KtExpression ktExpression, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        if (ktExpression != null) {
            return bindingContext.getType(ktExpression);
        }
        return null;
    }

    public static final List<Pair<Integer, Type>> withVariableIndices(Collection<Type> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        ArrayList arrayList = new ArrayList();
        int size = 0;
        for (Type type : collection) {
            arrayList.add(TuplesKt.to(Integer.valueOf(size), type));
            size += type.getSize();
        }
        return arrayList;
    }

    public static final boolean isGenericToArray(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        if (!Intrinsics.areEqual(functionDescriptor.getShortName().asString(), "toArray") || functionDescriptor.getValueParameters().size() != 1 || functionDescriptor.getTypeParameters().size() != 1) {
            return false;
        }
        KotlinType returnType = functionDescriptor.getReturnType();
        if (returnType == null) {
            throw new AssertionError(functionDescriptor.toString());
        }
        KotlinType type = functionDescriptor.getValueParameters().get(0).getType();
        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
        if (!KotlinBuiltIns.isArray(returnType) || !KotlinBuiltIns.isArray(type)) {
            return false;
        }
        SimpleType defaultType = functionDescriptor.getTypeParameters().get(0).getDefaultType();
        Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
        SimpleType simpleType = defaultType;
        FunctionDescriptor functionDescriptor2 = functionDescriptor;
        return KotlinTypeChecker.DEFAULT.equalTypes(simpleType, DescriptorUtilsKt.getBuiltIns(functionDescriptor2).getArrayElementType(returnType)) && KotlinTypeChecker.DEFAULT.equalTypes(simpleType, DescriptorUtilsKt.getBuiltIns(functionDescriptor2).getArrayElementType(type));
    }

    public static final boolean isNonGenericToArray(FunctionDescriptor functionDescriptor) {
        KotlinType returnType;
        Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
        if (!Intrinsics.areEqual(functionDescriptor.getShortName().asString(), "toArray")) {
            return false;
        }
        List<ValueParameterDescriptor> valueParameters = functionDescriptor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        if (!valueParameters.isEmpty()) {
            return false;
        }
        List<TypeParameterDescriptor> typeParameters = functionDescriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        return typeParameters.isEmpty() && (returnType = functionDescriptor.getReturnType()) != null && KotlinBuiltIns.isArray(returnType);
    }

    public static final boolean isToArrayFromCollection(MemberDescriptor memberDescriptor) {
        Intrinsics.checkNotNullParameter(memberDescriptor, "<this>");
        if (!(memberDescriptor instanceof FunctionDescriptor)) {
            return false;
        }
        FunctionDescriptor functionDescriptor = (FunctionDescriptor) memberDescriptor;
        DeclarationDescriptor containingDeclaration = functionDescriptor.getContainingDeclaration();
        ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor == null || Intrinsics.areEqual(classDescriptor.getSourceElement(), SourceElement.NO_SOURCE)) {
            return false;
        }
        ClassDescriptor collection = DescriptorUtilsKt.getBuiltIns(memberDescriptor).getCollection();
        Intrinsics.checkNotNullExpressionValue(collection, "getCollection(...)");
        if (DescriptorUtils.isSubclass(classDescriptor, collection)) {
            return isGenericToArray(functionDescriptor) || isNonGenericToArray(functionDescriptor);
        }
        return false;
    }

    public static final int getArity(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "<this>");
        return callableDescriptor.getValueParameters().size() + (callableDescriptor.getExtensionReceiverParameter() != null ? 1 : 0) + (callableDescriptor.mo6753getDispatchReceiverParameter() == null ? 0 : 1);
    }

    public static final String topLevelClassInternalName(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        String internalName = JvmClassName.byClassId(new ClassId(fqName.parent(), fqName.shortName())).getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        return internalName;
    }

    public static final Type topLevelClassAsmType(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        Type objectType = Type.getObjectType(topLevelClassInternalName(fqName));
        Intrinsics.checkNotNullExpressionValue(objectType, "getObjectType(...)");
        return objectType;
    }

    public static final void initializeVariablesForDestructuredLambdaParameters(final ExpressionCodegen codegen, final List<? extends ValueParameterDescriptor> valueParameters, final Label label) {
        Intrinsics.checkNotNullParameter(codegen, "codegen");
        Intrinsics.checkNotNullParameter(valueParameters, "valueParameters");
        codegen.runWithShouldMarkLineNumbers(false, new Supplier() { // from class: org.jetbrains.kotlin.codegen.CodegenUtilKt$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return CodegenUtilKt.initializeVariablesForDestructuredLambdaParameters$lambda$12(valueParameters, codegen, label);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit initializeVariablesForDestructuredLambdaParameters$lambda$12(List valueParameters, ExpressionCodegen codegen, Label label) {
        KtDestructuringDeclaration destructuringDeclaration;
        Intrinsics.checkNotNullParameter(valueParameters, "$valueParameters");
        Intrinsics.checkNotNullParameter(codegen, "$codegen");
        Iterator it = valueParameters.iterator();
        while (it.hasNext()) {
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) it.next();
            if (valueParameterDescriptor instanceof ValueParameterDescriptorImpl.WithDestructuringDeclaration) {
                ValueParameterDescriptorImpl.WithDestructuringDeclaration withDestructuringDeclaration = (ValueParameterDescriptorImpl.WithDestructuringDeclaration) valueParameterDescriptor;
                List<VariableDescriptor> listFilterOutDescriptorsWithSpecialNames = filterOutDescriptorsWithSpecialNames(withDestructuringDeclaration.getDestructuringVariables());
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listFilterOutDescriptorsWithSpecialNames, 10));
                for (VariableDescriptor variableDescriptor : listFilterOutDescriptorsWithSpecialNames) {
                    FrameMap frameMap = codegen.myFrameMap;
                    KotlinTypeMapper typeMapper = codegen.typeMapper;
                    Intrinsics.checkNotNullExpressionValue(typeMapper, "typeMapper");
                    KotlinType type = variableDescriptor.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                    arrayList.add(Integer.valueOf(frameMap.enter(variableDescriptor, KotlinTypeMapper.mapType$default(typeMapper, type, null, null, 6, null))));
                }
                ArrayList arrayList2 = arrayList;
                ValueParameterDescriptor valueParameterDescriptor2 = valueParameterDescriptor;
                PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(valueParameterDescriptor2);
                KtParameter ktParameter = psiElementDescriptorToDeclaration instanceof KtParameter ? (KtParameter) psiElementDescriptorToDeclaration : null;
                if (ktParameter == null || (destructuringDeclaration = ktParameter.getDestructuringDeclaration()) == null) {
                    throw new IllegalStateException(("Destructuring declaration for descriptor " + valueParameterDescriptor + " not found").toString());
                }
                TransientReceiver transientReceiver = new TransientReceiver(withDestructuringDeclaration.getType());
                StackValue stackValueFindLocalOrCapturedValue = codegen.findLocalOrCapturedValue(valueParameterDescriptor2);
                if (stackValueFindLocalOrCapturedValue != null) {
                    codegen.initializeDestructuringDeclarationVariables(destructuringDeclaration, transientReceiver, stackValueFindLocalOrCapturedValue);
                    if (label != null) {
                        Label label2 = new Label();
                        codegen.v.mark(label2);
                        for (Pair pair : CollectionsKt.zip(arrayList2, listFilterOutDescriptorsWithSpecialNames)) {
                            int iIntValue = ((Number) pair.component1()).intValue();
                            VariableDescriptor variableDescriptor2 = (VariableDescriptor) pair.component2();
                            InstructionAdapter instructionAdapter = codegen.v;
                            String strAsString = variableDescriptor2.getShortName().asString();
                            KotlinTypeMapper typeMapper2 = codegen.typeMapper;
                            Intrinsics.checkNotNullExpressionValue(typeMapper2, "typeMapper");
                            KotlinType type2 = variableDescriptor2.getType();
                            Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                            instructionAdapter.visitLocalVariable(strAsString, KotlinTypeMapper.mapType$default(typeMapper2, type2, null, null, 6, null).getDescriptor(), null, label2, label, iIntValue);
                        }
                    }
                } else {
                    throw new IllegalStateException(("Local var not found for parameter " + valueParameterDescriptor).toString());
                }
            }
        }
        return Unit.INSTANCE;
    }

    public static final <D extends CallableDescriptor> D unwrapFrontendVersion(D d) {
        Intrinsics.checkNotNullParameter(d, "<this>");
        return (D) CoroutineCodegenUtilKt.unwrapInitialDescriptorForSuspendFunction(d);
    }

    public static final void useTmpVar(FrameMap frameMap, Type type, Function1<? super Integer, Unit> block) {
        Intrinsics.checkNotNullParameter(frameMap, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(block, "block");
        block.invoke(Integer.valueOf(frameMap.enterTemp(type)));
        frameMap.leaveTemp(type);
    }

    public static final Pair<TypeParameterMarker, ReificationArgument> extractReificationArgument(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker initialType) {
        Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(initialType, "initialType");
        boolean zIsMarkedNullable = typeSystemCommonBackendContext.isMarkedNullable(initialType);
        int i = 0;
        while (typeSystemCommonBackendContext.isArrayOrNullableArray(initialType)) {
            i++;
            TypeArgumentMarker argument = typeSystemCommonBackendContext.getArgument(initialType, 0);
            if (typeSystemCommonBackendContext.isStarProjection(argument)) {
                return null;
            }
            initialType = typeSystemCommonBackendContext.getType(argument);
        }
        TypeParameterMarker typeParameterClassifier = typeSystemCommonBackendContext.getTypeParameterClassifier(typeSystemCommonBackendContext.typeConstructor(initialType));
        if (typeParameterClassifier == null || !typeSystemCommonBackendContext.isReified(typeParameterClassifier)) {
            return null;
        }
        String strAsString = typeSystemCommonBackendContext.getName(typeParameterClassifier).asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return new Pair<>(typeParameterClassifier, new ReificationArgument(strAsString, zIsMarkedNullable, i));
    }

    public static final ReifiedTypeParametersUsages extractUsedReifiedParameters(TypeSystemCommonBackendContext typeSystemCommonBackendContext, KotlinTypeMarker type) {
        Intrinsics.checkNotNullParameter(typeSystemCommonBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        ReifiedTypeParametersUsages reifiedTypeParametersUsages = new ReifiedTypeParametersUsages();
        extractUsedReifiedParameters$lambda$14$visit(type, typeSystemCommonBackendContext, reifiedTypeParametersUsages);
        return reifiedTypeParametersUsages;
    }

    private static final void extractUsedReifiedParameters$lambda$14$visit(KotlinTypeMarker kotlinTypeMarker, TypeSystemCommonBackendContext typeSystemCommonBackendContext, ReifiedTypeParametersUsages reifiedTypeParametersUsages) {
        TypeParameterMarker typeParameterClassifier = typeSystemCommonBackendContext.getTypeParameterClassifier(typeSystemCommonBackendContext.typeConstructor(kotlinTypeMarker));
        if (typeParameterClassifier == null) {
            for (TypeArgumentMarker typeArgumentMarker : typeSystemCommonBackendContext.getArguments(kotlinTypeMarker)) {
                if (!typeSystemCommonBackendContext.isStarProjection(typeArgumentMarker)) {
                    extractUsedReifiedParameters$lambda$14$visit(typeSystemCommonBackendContext.getType(typeArgumentMarker), typeSystemCommonBackendContext, reifiedTypeParametersUsages);
                }
            }
            return;
        }
        if (typeSystemCommonBackendContext.isReified(typeParameterClassifier)) {
            String strAsString = typeSystemCommonBackendContext.getName(typeParameterClassifier).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            reifiedTypeParametersUsages.addUsedReifiedParameter(strAsString);
        }
    }

    public static final FunctionDescriptor unwrapInitialSignatureDescriptor(FunctionDescriptor function) {
        Intrinsics.checkNotNullParameter(function, "function");
        FunctionDescriptor initialSignatureDescriptor = function.getInitialSignatureDescriptor();
        return initialSignatureDescriptor == null ? function : initialSignatureDescriptor;
    }

    public static final StackValue generateCallReceiver(ExpressionCodegen expressionCodegen, ResolvedCall<? extends CallableDescriptor> call) {
        Intrinsics.checkNotNullParameter(expressionCodegen, "<this>");
        Intrinsics.checkNotNullParameter(call, "call");
        ReceiverValue receiverValueMo7305getExtensionReceiver = call.mo7305getExtensionReceiver();
        if (receiverValueMo7305getExtensionReceiver == null) {
            receiverValueMo7305getExtensionReceiver = call.mo7304getDispatchReceiver();
            Intrinsics.checkNotNull(receiverValueMo7305getExtensionReceiver);
        }
        StackValue stackValueGenerateReceiverValue = expressionCodegen.generateReceiverValue(receiverValueMo7305getExtensionReceiver, false);
        Intrinsics.checkNotNullExpressionValue(stackValueGenerateReceiverValue, "generateReceiverValue(...)");
        return stackValueGenerateReceiverValue;
    }

    public static final StackValue generateCallSingleArgument(ExpressionCodegen expressionCodegen, ResolvedCall<? extends CallableDescriptor> call) {
        Intrinsics.checkNotNullParameter(expressionCodegen, "<this>");
        Intrinsics.checkNotNullParameter(call, "call");
        KtExpression firstArgumentExpression = CallUtilKt.getFirstArgumentExpression(call);
        Intrinsics.checkNotNull(firstArgumentExpression);
        StackValue stackValueGen = expressionCodegen.gen(firstArgumentExpression);
        Intrinsics.checkNotNullExpressionValue(stackValueGen, "gen(...)");
        return stackValueGen;
    }

    public static final boolean isPossiblyUninitializedSingleton(ClassDescriptor classDescriptor) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        ClassDescriptor classDescriptor2 = classDescriptor;
        return DescriptorUtils.isEnumEntry(classDescriptor2) || (DescriptorUtils.isCompanionObject(classDescriptor2) && JvmCodegenUtil.isJvmInterface(classDescriptor.getContainingDeclaration()));
    }

    public static final void evaluateOnce(FrameMap frameMap, StackValue value, Type asType, InstructionAdapter v, Function1<? super StackValue, Unit> body) {
        StackValue.Local local;
        Intrinsics.checkNotNullParameter(frameMap, "<this>");
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(asType, "asType");
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(body, "body");
        if (value.canHaveSideEffects()) {
            StackValue.Local local2 = StackValue.local(frameMap.enterTemp(asType), asType);
            local2.store(value, v);
            Intrinsics.checkNotNullExpressionValue(local2, "apply(...)");
            local = local2;
        } else {
            local = value;
        }
        body.invoke(local);
        if (Intrinsics.areEqual(local, value)) {
            return;
        }
        frameMap.leaveTemp(asType);
    }

    public static final boolean isInlineClassTypeWithPrimitiveEquality(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        return InlineClassesUtilsKt.isInlineClassType(kotlinType) && UnsignedTypes.isUnsignedType(kotlinType);
    }

    public static final void recordCallLabelForLambdaArgument(KtFunctionLiteral declaration, BindingTrace bindingTrace) {
        SimpleFunctionDescriptor simpleFunctionDescriptor;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(bindingTrace, "bindingTrace");
        BindingContext bindingContext = bindingTrace.getContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        String callLabelForLambdaArgument = getCallLabelForLambdaArgument(declaration, bindingContext);
        if (callLabelForLambdaArgument == null || (simpleFunctionDescriptor = (SimpleFunctionDescriptor) bindingTrace.get(BindingContext.FUNCTION, declaration)) == null) {
            return;
        }
        bindingTrace.record(CodegenBinding.CALL_LABEL_FOR_LAMBDA_ARGUMENT, simpleFunctionDescriptor, callLabelForLambdaArgument);
    }

    public static final String getCallLabelForLambdaArgument(KtFunctionLiteral declaration, BindingContext bindingContext) {
        KtCallExpression ktCallExpression;
        String name2;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        PsiElement parent = declaration.getLightOwner();
        KtLambdaExpression ktLambdaExpression = parent instanceof KtLambdaExpression ? (KtLambdaExpression) parent : null;
        if (ktLambdaExpression == null) {
            return null;
        }
        PsiElement parent2 = ktLambdaExpression.getLightOwner();
        if ((parent2 instanceof KtLabeledExpression) && (name2 = ((KtLabeledExpression) parent2).get_name()) != null) {
            return name2;
        }
        PsiElement parent3 = ktLambdaExpression.getLightOwner();
        if (parent3 instanceof KtLambdaArgument) {
            PsiElement parent4 = ((KtLambdaArgument) parent3).getLightOwner();
            ktCallExpression = parent4 instanceof KtCallExpression ? (KtCallExpression) parent4 : null;
            if (ktCallExpression == null) {
                return null;
            }
        } else {
            if (!(parent3 instanceof KtValueArgument)) {
                return null;
            }
            PsiElement parent5 = ((KtValueArgument) parent3).getLightOwner();
            KtValueArgumentList ktValueArgumentList = parent5 instanceof KtValueArgumentList ? (KtValueArgumentList) parent5 : null;
            if (ktValueArgumentList == null) {
                return null;
            }
            PsiElement parent6 = ktValueArgumentList.getLightOwner();
            ktCallExpression = parent6 instanceof KtCallExpression ? (KtCallExpression) parent6 : null;
            if (ktCallExpression == null) {
                return null;
            }
        }
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(ktCallExpression, bindingContext);
        if (resolvedCall == null) {
            return null;
        }
        return resolvedCall.getResultingDescriptor().getShortName().asString();
    }

    public static final void generateBridgeForMainFunctionIfNecessary(GenerationState state, ClassBuilder packagePartClassBuilder, FunctionDescriptor functionDescriptor, JvmMethodGenericSignature signatureOfRealDeclaration, JvmDeclarationOrigin origin, CodegenContext<?> parentContext) {
        String strGenerateLambdaForRunSuspend;
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(packagePartClassBuilder, "packagePartClassBuilder");
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        Intrinsics.checkNotNullParameter(signatureOfRealDeclaration, "signatureOfRealDeclaration");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(parentContext, "parentContext");
        PsiElement element = origin.getElement();
        if (element != null && Intrinsics.areEqual(functionDescriptor.getShortName().asString(), "main")) {
            FunctionDescriptor functionDescriptor2 = functionDescriptor;
            if (DescriptorUtils.isTopLevelDeclaration(functionDescriptor2)) {
                FunctionDescriptor functionDescriptor3 = (FunctionDescriptor) CoroutineCodegenUtilKt.unwrapInitialDescriptorForSuspendFunction(functionDescriptor);
                boolean z = functionDescriptor3.getExtensionReceiverParameter() == null && functionDescriptor3.getValueParameters().isEmpty();
                if ((functionDescriptor.isSuspend() || z) && MainFunctionDetector.isMain$default(state.getMainFunctionDetector(), functionDescriptor3, false, true, false, 8, null)) {
                    JvmDeclarationOrigin jvmDeclarationOriginSynthetic = JvmDeclarationOriginKt.Synthetic(element, functionDescriptor2);
                    String str = METHOD_DESCRIPTOR_FOR_MAIN;
                    boolean z2 = z;
                    MethodVisitor methodVisitorNewMethod = packagePartClassBuilder.newMethod(jvmDeclarationOriginSynthetic, 4105, "main", str, null, null);
                    Intrinsics.checkNotNullExpressionValue(methodVisitorNewMethod, "newMethod(...)");
                    if (parentContext instanceof MultifileClassFacadeContext) {
                        methodVisitorNewMethod.visitCode();
                        FunctionCodegen.generateFacadeDelegateMethodBody(methodVisitorNewMethod, new Method("main", str), (MultifileClassFacadeContext) parentContext);
                        methodVisitorNewMethod.visitEnd();
                        return;
                    }
                    if (functionDescriptor.isSuspend()) {
                        String thisName = packagePartClassBuilder.getThisName();
                        Intrinsics.checkNotNullExpressionValue(thisName, "getThisName(...)");
                        strGenerateLambdaForRunSuspend = generateLambdaForRunSuspend(state, element, thisName, signatureOfRealDeclaration, z2);
                    } else {
                        strGenerateLambdaForRunSuspend = null;
                    }
                    methodVisitorNewMethod.visitCode();
                    if (strGenerateLambdaForRunSuspend != null) {
                        methodVisitorNewMethod.visitTypeInsn(187, strGenerateLambdaForRunSuspend);
                        methodVisitorNewMethod.visitInsn(89);
                        methodVisitorNewMethod.visitVarInsn(25, 0);
                        methodVisitorNewMethod.visitMethodInsn(183, strGenerateLambdaForRunSuspend, "<init>", str, false);
                        methodVisitorNewMethod.visitMethodInsn(184, "kotlin/coroutines/jvm/internal/RunSuspendKt", "runSuspend", Type.getMethodDescriptor(Type.VOID_TYPE, Type.getObjectType("kotlin/jvm/functions/Function1")), false);
                    } else {
                        methodVisitorNewMethod.visitMethodInsn(184, packagePartClassBuilder.getThisName(), "main", Type.getMethodDescriptor(Type.VOID_TYPE, new Type[0]), false);
                    }
                    methodVisitorNewMethod.visitInsn(177);
                    methodVisitorNewMethod.visitEnd();
                }
            }
        }
    }

    private static final String generateLambdaForRunSuspend(GenerationState generationState, PsiElement psiElement, String str, JvmMethodGenericSignature jvmMethodGenericSignature, boolean z) {
        String str2 = str + "$$$main";
        ClassBuilder classBuilderNewVisitor = generationState.getFactory().newVisitor(JvmDeclarationOrigin.NO_ORIGIN, Type.getObjectType(str2), psiElement.getContainingFile());
        Intrinsics.checkNotNullExpressionValue(classBuilderNewVisitor, "newVisitor(...)");
        classBuilderNewVisitor.defineClass(psiElement, generationState.getConfig().getClassFileVersion(), 4144, str2, null, AsmTypes.LAMBDA.getInternalName(), new String[]{"kotlin/jvm/functions/Function1"});
        JvmDeclarationOrigin jvmDeclarationOrigin = JvmDeclarationOrigin.NO_ORIGIN;
        Type type = ARRAY_OF_STRINGS_TYPE;
        classBuilderNewVisitor.newField(jvmDeclarationOrigin, 4114, "args", type.getDescriptor(), null, null);
        MethodVisitor methodVisitorNewMethod = classBuilderNewVisitor.newMethod(JvmDeclarationOrigin.NO_ORIGIN, 4096, "<init>", METHOD_DESCRIPTOR_FOR_MAIN, null, null);
        methodVisitorNewMethod.visitCode();
        methodVisitorNewMethod.visitVarInsn(25, 0);
        methodVisitorNewMethod.visitVarInsn(25, 1);
        methodVisitorNewMethod.visitFieldInsn(181, classBuilderNewVisitor.getThisName(), "args", type.getDescriptor());
        methodVisitorNewMethod.visitVarInsn(25, 0);
        methodVisitorNewMethod.visitInsn(4);
        methodVisitorNewMethod.visitMethodInsn(183, AsmTypes.LAMBDA.getInternalName(), "<init>", Type.getMethodDescriptor(Type.VOID_TYPE, Type.INT_TYPE), false);
        methodVisitorNewMethod.visitInsn(177);
        methodVisitorNewMethod.visitEnd();
        MethodVisitor methodVisitorNewMethod2 = classBuilderNewVisitor.newMethod(JvmDeclarationOrigin.NO_ORIGIN, 4113, "invoke", Type.getMethodDescriptor(AsmTypes.OBJECT_TYPE, AsmTypes.OBJECT_TYPE), null, null);
        methodVisitorNewMethod2.visitCode();
        if (!z) {
            methodVisitorNewMethod2.visitVarInsn(25, 0);
            methodVisitorNewMethod2.visitFieldInsn(180, classBuilderNewVisitor.getThisName(), "args", type.getDescriptor());
        }
        methodVisitorNewMethod2.visitVarInsn(25, 1);
        methodVisitorNewMethod2.visitTypeInsn(192, CoroutineCodegenUtilKt.CONTINUATION_ASM_TYPE.getInternalName());
        methodVisitorNewMethod2.visitMethodInsn(184, str, jvmMethodGenericSignature.getAsmMethod().getName(), jvmMethodGenericSignature.getAsmMethod().getDescriptor(), false);
        methodVisitorNewMethod2.visitInsn(176);
        methodVisitorNewMethod2.visitEnd();
        WriteAnnotationUtilKt.writeSyntheticClassMetadata(classBuilderNewVisitor, generationState, false);
        classBuilderNewVisitor.done(generationState.getConfig().getGenerateSmapCopyToAnnotation());
        String thisName = classBuilderNewVisitor.getThisName();
        Intrinsics.checkNotNullExpressionValue(thisName, "getThisName(...)");
        return thisName;
    }

    public static final LabelNode linkWithLabel(LabelNode labelNode) {
        Intrinsics.checkNotNullParameter(labelNode, "<this>");
        if (labelNode.getLabel().info == null) {
            labelNode.getLabel().info = labelNode;
        }
        return labelNode;
    }

    public static final Label linkedLabel() {
        Label label = linkWithLabel(new LabelNode()).getLabel();
        Intrinsics.checkNotNullExpressionValue(label, "getLabel(...)");
        return label;
    }

    public static final boolean isDefinitelyFitEncodingLimit(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return str.length() <= 21845;
    }

    public static final List<String> splitStringConstant(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        if (isDefinitelyFitEncodingLimit(value)) {
            return CollectionsKt.listOf(value);
        }
        ArrayList arrayList = new ArrayList();
        int length = value.length();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < length) {
            char cCharAt = value.charAt(i);
            int i4 = 1;
            if (1 > cCharAt || cCharAt >= 128) {
                i4 = cCharAt <= 2047 ? 2 : 3;
            }
            if (i3 + i4 > 65535) {
                String strSubstring = value.substring(i2, i);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                arrayList.add(strSubstring);
                i3 = 0;
                i2 = i;
            }
            i3 += i4;
            i++;
        }
        String strSubstring2 = value.substring(i2, i);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
        arrayList.add(strSubstring2);
        return arrayList;
    }

    public static final int encodedUTF8Size(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        int length = str.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            i = (1 > cCharAt || cCharAt >= 128) ? cCharAt <= 2047 ? i + 2 : i + 3 : i + 1;
        }
        return i;
    }

    public static final void generateNewInstanceDupAndPlaceBeforeStackTop(InstructionAdapter instructionAdapter, FrameMap frameMap, Type topStackType, String newInstanceInternalName) {
        Intrinsics.checkNotNullParameter(instructionAdapter, "<this>");
        Intrinsics.checkNotNullParameter(frameMap, "frameMap");
        Intrinsics.checkNotNullParameter(topStackType, "topStackType");
        Intrinsics.checkNotNullParameter(newInstanceInternalName, "newInstanceInternalName");
        int iEnterTemp = frameMap.enterTemp(topStackType);
        instructionAdapter.store(iEnterTemp, topStackType);
        instructionAdapter.anew(Type.getObjectType(newInstanceInternalName));
        instructionAdapter.dup();
        instructionAdapter.load(iEnterTemp, topStackType);
        frameMap.leaveTemp(topStackType);
    }
}
