package org.jetbrains.kotlin.diagnostics.rendering;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.container.SpecTypePair;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import name.antonsmirnov.firmata.FormatHelper;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.analyzer.AnalyzerFacadeKt;
import org.jetbrains.kotlin.analyzer.ModuleInfo;
import org.jetbrains.kotlin.analyzer.ModuleInfoUtilsKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.util.Function;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptorWithTypeParameters;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.Named;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.diagnostics.WhenMissingCase;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.platform.SimplePlatform;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.platform.TargetPlatformKt;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.renderer.DescriptorRendererOptions;
import org.jetbrains.kotlin.renderer.PropertyAccessorRenderingPolicy;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.MemberComparator;
import org.jetbrains.kotlin.resolve.calls.inference.CallHandle;
import org.jetbrains.kotlin.resolve.calls.inference.CannotCapture;
import org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructor;
import org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructorKt;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintError;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystem;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemStatus;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintsUtil;
import org.jetbrains.kotlin.resolve.calls.inference.InferenceErrorData;
import org.jetbrains.kotlin.resolve.calls.inference.ParameterConstraintError;
import org.jetbrains.kotlin.resolve.calls.inference.TypeBounds;
import org.jetbrains.kotlin.resolve.calls.inference.TypeVariable;
import org.jetbrains.kotlin.resolve.calls.inference.TypeVariableKt;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPositionKind;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPositionKt;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ValidityConstraintForConstituentType;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.AbstractTypeConstructor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeConstructorSubstitution;
import org.jetbrains.kotlin.types.TypeIntersector;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeChecker;
import org.jetbrains.kotlin.utils.IDEAPlatforms;
import org.jetbrains.kotlin.utils.IDEAPluginsCompatibilityAPI;

/* JADX INFO: compiled from: Renderers.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u009c\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001|B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J(\u0010H\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002HI0\u00050\u0017\"\u0004\b\u0000\u0010I2\f\u0010J\u001a\b\u0012\u0004\u0012\u0002HI0\u0017H\u0007J\"\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020L2\u0006\u0010N\u001a\u0002082\b\b\u0002\u0010O\u001a\u00020PH\u0002J\u0016\u0010Q\u001a\u00020L2\f\u0010R\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0002J\u0018\u0010S\u001a\u00020L2\b\u0010T\u001a\u0004\u0018\u00010U2\u0006\u0010V\u001a\u00020LJ\u0018\u0010W\u001a\u00020X2\u0006\u0010N\u001a\u0002082\u0006\u0010Y\u001a\u00020XH\u0007J\u0018\u0010Z\u001a\u00020X2\u0006\u0010N\u001a\u0002082\u0006\u0010Y\u001a\u00020XH\u0007J\u0016\u0010[\u001a\u00020L2\u0006\u0010\\\u001a\u00020]2\u0006\u0010O\u001a\u00020PJ \u0010^\u001a\u00020L2\b\u0010_\u001a\u0004\u0018\u00010.2\u000e\u0010`\u001a\n\u0012\u0004\u0012\u00020.\u0018\u00010aJ\u0018\u0010b\u001a\u00020X2\u0006\u0010N\u001a\u0002082\u0006\u0010Y\u001a\u00020XH\u0007J\u0018\u0010c\u001a\u00020X2\u0006\u0010N\u001a\u0002082\u0006\u0010d\u001a\u00020XH\u0007J\u0018\u0010e\u001a\u00020L2\u0006\u0010f\u001a\u00020g2\u0006\u0010O\u001a\u00020PH\u0002J\u0018\u0010h\u001a\u00020L2\u0006\u0010i\u001a\u00020j2\u0006\u0010O\u001a\u00020PH\u0002J\u0010\u0010k\u001a\u00020L2\u0006\u0010l\u001a\u00020mH\u0002J\u0018\u0010n\u001a\u00020L2\u0006\u0010o\u001a\u00020p2\u0006\u0010q\u001a\u00020EH\u0002J,\u0010r\u001a\u00020L2\f\u0010s\u001a\b\u0012\u0004\u0012\u00020.0\u00052\f\u0010t\u001a\b\u0012\u0004\u0012\u00020.0\u00172\u0006\u0010u\u001a\u00020vH\u0002J\u0018\u0010w\u001a\u00020X2\u0006\u0010N\u001a\u0002082\u0006\u0010Y\u001a\u00020XH\u0007J\"\u0010x\u001a\u0004\u0018\u00010X2\u0006\u0010N\u001a\u0002082\u0006\u0010Y\u001a\u00020X2\u0006\u0010y\u001a\u00020]H\u0002J\f\u0010z\u001a\u00020L*\u00020LH\u0002J\f\u0010z\u001a\u00020L*\u00020{H\u0002R\u001c\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R \u0010\u0007\u001a\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\b0\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00050\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\n0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00050\u00178\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u00020\u001e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0013\u0010 \u001a\u00070!¢\u0006\u0002\b\"X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010%\u001a\b\u0012\u0004\u0012\u00020$0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010&\u001a\b\u0012\u0004\u0012\u00020'0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010(\u001a\b\u0012\u0004\u0012\u00020\n0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010+\u001a\b\u0012\u0004\u0012\u00020,0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001c\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020.0\u00050\u00178\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010/\u001a\u0002008\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u00101\u001a\u0002008\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001c\u00102\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u000204030\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u00105\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u00106\u001a\b\u0012\u0004\u0012\u00020\u00010\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u00107\u001a\b\u0012\u0004\u0012\u0002080\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u00109\u001a\b\u0012\u0004\u0012\u0002080\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010:\u001a\b\u0012\u0004\u0012\u0002080\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010;\u001a\b\u0012\u0004\u0012\u0002080\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010<\u001a\b\u0012\u0004\u0012\u0002080\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010=\u001a\b\u0012\u0004\u0012\u00020>0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010?\u001a\b\u0012\u0004\u0012\u00020@0\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020BX\u0082D¢\u0006\u0002\n\u0000R\u0010\u0010C\u001a\u00020\u000e8\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u001e\u0010D\u001a\u00020E*\b\u0012\u0004\u0012\u000204038BX\u0082\u0004¢\u0006\u0006\u001a\u0004\bF\u0010G¨\u0006}"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/Renderers;", "", "()V", "AMBIGUOUS_CALLABLE_REFERENCES", "Lorg/jetbrains/kotlin/diagnostics/rendering/ContextIndependentParameterRenderer;", "", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "AMBIGUOUS_CALLS", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "CAPITALIZED_DECLARATION_NAME_WITH_KIND_AND_PLATFORM", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "CLASSES_OR_SEPARATED", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "COMPACT", "Lorg/jetbrains/kotlin/diagnostics/rendering/SmartDescriptorRenderer;", "COMPACT_WITHOUT_SUPERTYPES", "COMPACT_WITH_MODIFIERS", "COMPATIBILITY_CANDIDATE", "DECLARATION_NAME", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "DECLARATION_NAME_WITH_KIND", "DEPRECATION_RENDERER", "DESCRIPTORS_ON_NEWLINE_WITH_INDENT", "Lorg/jetbrains/kotlin/diagnostics/rendering/DiagnosticParameterRenderer;", "ELEMENT_TEXT", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "FQ_NAME", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "FQ_NAMES_IN_TYPES", "FQ_NAMES_IN_TYPES_ANNOTATIONS_WHITELIST", "Lorg/jetbrains/kotlin/diagnostics/rendering/AnnotationsWhitelistDescriptorRenderer;", "FQ_NAMES_IN_TYPES_WITH_ANNOTATIONS", "LOG", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Logger;", "Lorg/jetbrains/annotations/NotNull;", "MODULE", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "MODULE_WITH_PLATFORM", "NAME", "Lorg/jetbrains/kotlin/descriptors/Named;", "NAME_OF_CONTAINING_DECLARATION_OR_FILE", "RENDER_CLASS_OR_OBJECT", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "RENDER_CLASS_OR_OBJECT_NAME", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptorWithTypeParameters;", "RENDER_COLLECTION_OF_TYPES", "Lorg/jetbrains/kotlin/types/KotlinType;", "RENDER_TYPE", "Lorg/jetbrains/kotlin/diagnostics/rendering/SmartTypeRenderer;", "RENDER_TYPE_WITH_ANNOTATIONS", "RENDER_WHEN_MISSING_CASES", "", "Lorg/jetbrains/kotlin/diagnostics/WhenMissingCase;", "SHORT_NAMES_IN_TYPES", "TO_STRING", "TYPE_INFERENCE_CANNOT_CAPTURE_TYPES_RENDERER", "Lorg/jetbrains/kotlin/resolve/calls/inference/InferenceErrorData;", "TYPE_INFERENCE_CONFLICTING_SUBSTITUTIONS_RENDERER", "TYPE_INFERENCE_NO_INFORMATION_FOR_PARAMETER_RENDERER", "TYPE_INFERENCE_PARAMETER_CONSTRAINT_ERROR_RENDERER", "TYPE_INFERENCE_UPPER_BOUND_VIOLATED_RENDERER", "TYPE_PROJECTION", "Lorg/jetbrains/kotlin/types/TypeProjection;", "VISIBILITY", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "WHEN_MISSING_LIMIT", "", "WITHOUT_MODIFIERS", "assumesElseBranchOnly", "", "getAssumesElseBranchOnly", "(Ljava/util/List;)Z", "commaSeparated", ExifInterface.GPS_DIRECTION_TRUE, "itemRenderer", "debugMessage", "", CrashHianalyticsData.MESSAGE, "inferenceErrorData", "verbosity", "Lorg/jetbrains/kotlin/diagnostics/rendering/Renderers$ConstraintSystemRenderingVerbosity;", "renderAmbiguousDescriptors", "descriptors", "renderCallInfo", "fqName", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "typeCall", "renderCannotCaptureTypeParameterError", "Lorg/jetbrains/kotlin/diagnostics/rendering/TabledDescriptorRenderer;", "result", "renderConflictingSubstitutionsInferenceError", "renderConstraintSystem", "constraintSystem", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystem;", "renderExpressionType", ModuleXmlParser.TYPE, "dataFlowTypes", "", "renderNoInformationForParameterError", "renderParameterConstraintError", "renderer", "renderTypeBound", "bound", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds$Bound;", "renderTypeBounds", "typeBounds", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeBounds;", "renderTypeConstructor", "typeConstructor", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "renderTypeVariable", "typeVariable", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;", "includeTypeConstructor", "renderTypes", SpecTypePair.NAME_types, "typeRenderer", "context", "Lorg/jetbrains/kotlin/diagnostics/rendering/RenderingContext;", "renderUpperBoundViolatedInferenceError", "renderUpperBoundViolatedInferenceErrorForTypeAliasConstructor", "systemWithoutWeakConstraints", "wrapIntoQuotes", "Lorg/jetbrains/kotlin/name/Name;", "ConstraintSystemRenderingVerbosity", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Renderers {
    public static final ContextIndependentParameterRenderer<Collection<? extends CallableDescriptor>> AMBIGUOUS_CALLABLE_REFERENCES;
    public static final ContextIndependentParameterRenderer<Collection<? extends ResolvedCall<?>>> AMBIGUOUS_CALLS;
    public static final ContextIndependentParameterRenderer<DeclarationDescriptor> CAPITALIZED_DECLARATION_NAME_WITH_KIND_AND_PLATFORM;
    public static final ContextIndependentParameterRenderer<Collection<? extends ClassDescriptor>> CLASSES_OR_SEPARATED;
    public static final SmartDescriptorRenderer COMPACT;
    public static final SmartDescriptorRenderer COMPACT_WITHOUT_SUPERTYPES;
    public static final SmartDescriptorRenderer COMPACT_WITH_MODIFIERS;
    public static final ContextIndependentParameterRenderer<CallableDescriptor> COMPATIBILITY_CANDIDATE;
    public static final ContextIndependentParameterRenderer<KtNamedDeclaration> DECLARATION_NAME;
    public static final ContextIndependentParameterRenderer<DeclarationDescriptor> DECLARATION_NAME_WITH_KIND;
    public static final SmartDescriptorRenderer DEPRECATION_RENDERER;
    public static final DiagnosticParameterRenderer<Collection<? extends DeclarationDescriptor>> DESCRIPTORS_ON_NEWLINE_WITH_INDENT;
    public static final ContextIndependentParameterRenderer<PsiElement> ELEMENT_TEXT;
    public static final ContextIndependentParameterRenderer<MemberDescriptor> FQ_NAME;
    public static final SmartDescriptorRenderer FQ_NAMES_IN_TYPES;
    public static final AnnotationsWhitelistDescriptorRenderer FQ_NAMES_IN_TYPES_ANNOTATIONS_WHITELIST;
    public static final SmartDescriptorRenderer FQ_NAMES_IN_TYPES_WITH_ANNOTATIONS;
    public static final Renderers INSTANCE = new Renderers();
    private static final Logger LOG;
    public static final ContextIndependentParameterRenderer<ModuleDescriptor> MODULE;
    public static final ContextIndependentParameterRenderer<ModuleDescriptor> MODULE_WITH_PLATFORM;
    public static final ContextIndependentParameterRenderer<Named> NAME;
    public static final ContextIndependentParameterRenderer<DeclarationDescriptor> NAME_OF_CONTAINING_DECLARATION_OR_FILE;
    public static final ContextIndependentParameterRenderer<KtClassOrObject> RENDER_CLASS_OR_OBJECT;
    public static final ContextIndependentParameterRenderer<ClassifierDescriptorWithTypeParameters> RENDER_CLASS_OR_OBJECT_NAME;
    public static final DiagnosticParameterRenderer<Collection<? extends KotlinType>> RENDER_COLLECTION_OF_TYPES;
    public static final SmartTypeRenderer RENDER_TYPE;
    public static final SmartTypeRenderer RENDER_TYPE_WITH_ANNOTATIONS;
    public static final ContextIndependentParameterRenderer<List<? extends WhenMissingCase>> RENDER_WHEN_MISSING_CASES;
    public static final SmartDescriptorRenderer SHORT_NAMES_IN_TYPES;
    public static final ContextIndependentParameterRenderer<Object> TO_STRING;
    public static final ContextIndependentParameterRenderer<InferenceErrorData> TYPE_INFERENCE_CANNOT_CAPTURE_TYPES_RENDERER;
    public static final ContextIndependentParameterRenderer<InferenceErrorData> TYPE_INFERENCE_CONFLICTING_SUBSTITUTIONS_RENDERER;
    public static final ContextIndependentParameterRenderer<InferenceErrorData> TYPE_INFERENCE_NO_INFORMATION_FOR_PARAMETER_RENDERER;
    public static final ContextIndependentParameterRenderer<InferenceErrorData> TYPE_INFERENCE_PARAMETER_CONSTRAINT_ERROR_RENDERER;
    public static final ContextIndependentParameterRenderer<InferenceErrorData> TYPE_INFERENCE_UPPER_BOUND_VIOLATED_RENDERER;
    public static final ContextIndependentParameterRenderer<TypeProjection> TYPE_PROJECTION;
    public static final ContextIndependentParameterRenderer<DescriptorVisibility> VISIBILITY;
    private static final int WHEN_MISSING_LIMIT;
    public static final SmartDescriptorRenderer WITHOUT_MODIFIERS;

    /* JADX INFO: compiled from: Renderers.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/rendering/Renderers$ConstraintSystemRenderingVerbosity;", "", "(Ljava/lang/String;I)V", "COMPACT", "DEBUG", "EXTRA_VERBOSE", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum ConstraintSystemRenderingVerbosity {
        COMPACT,
        DEBUG,
        EXTRA_VERBOSE;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<ConstraintSystemRenderingVerbosity> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: Renderers.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[TypeBounds.BoundKind.values().length];
            try {
                iArr[TypeBounds.BoundKind.LOWER_BOUND.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeBounds.BoundKind.UPPER_BOUND.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ConstraintSystemRenderingVerbosity.values().length];
            try {
                iArr2[ConstraintSystemRenderingVerbosity.COMPACT.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr2[ConstraintSystemRenderingVerbosity.DEBUG.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[ConstraintSystemRenderingVerbosity.EXTRA_VERBOSE.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    private Renderers() {
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) Renderers.class);
        Intrinsics.checkNotNullExpressionValue(logger, "getInstance(...)");
        LOG = logger;
        TO_STRING = DiagnosticParameterRendererKt.Renderer(new Function1<Object, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$TO_STRING$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(Object element) {
                Intrinsics.checkNotNullParameter(element, "element");
                if (element instanceof DeclarationDescriptor) {
                    Renderers.LOG.warn("Diagnostic renderer TO_STRING was used to render an instance of DeclarationDescriptor.\nThis is usually a bad idea, because descriptors' toString() includes some debug information, which should not be seen by the user.\nDescriptor: " + element);
                }
                return element.toString();
            }
        });
        NAME = DiagnosticParameterRendererKt.Renderer(new Function1<Named, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$NAME$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(Named it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String strAsString = it.getShortName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return strAsString;
            }
        });
        FQ_NAME = DiagnosticParameterRendererKt.Renderer(new Function1<MemberDescriptor, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$FQ_NAME$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(MemberDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String strAsString = DescriptorUtilsKt.getFqNameSafe(it).asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return strAsString;
            }
        });
        MODULE_WITH_PLATFORM = DiagnosticParameterRendererKt.Renderer(new Function1<ModuleDescriptor, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$MODULE_WITH_PLATFORM$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(ModuleDescriptor module) {
                Intrinsics.checkNotNullParameter(module, "module");
                TargetPlatform platform = module.getPlatform();
                return Renderers.MODULE.render(module) + ((platform == null || TargetPlatformKt.isCommon(platform)) ? "" : " for " + ((SimplePlatform) CollectionsKt.single(platform)).getPlatformName());
            }
        });
        MODULE = DiagnosticParameterRendererKt.Renderer(new Function1<ModuleDescriptor, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$MODULE$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(ModuleDescriptor module) {
                ModuleInfo moduleInfoUnwrapPlatform;
                String displayedName;
                Intrinsics.checkNotNullParameter(module, "module");
                ModuleInfo moduleInfo = ModuleInfoUtilsKt.getModuleInfo(module);
                if (moduleInfo != null && (moduleInfoUnwrapPlatform = AnalyzerFacadeKt.unwrapPlatform(moduleInfo)) != null && (displayedName = moduleInfoUnwrapPlatform.getDisplayedName()) != null) {
                    return displayedName;
                }
                String strAsString = module.getShortName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return strAsString;
            }
        });
        VISIBILITY = DiagnosticParameterRendererKt.Renderer(new Function1<DescriptorVisibility, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$VISIBILITY$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(DescriptorVisibility it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getExternalDisplayName();
            }
        });
        DECLARATION_NAME_WITH_KIND = DiagnosticParameterRendererKt.Renderer(new Function1<DeclarationDescriptor, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$DECLARATION_NAME_WITH_KIND$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(DeclarationDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String strAsString = it.getShortName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                if (it instanceof PackageFragmentDescriptor) {
                    return "package '" + strAsString + '\'';
                }
                if (it instanceof ClassDescriptor) {
                    return DiagnosticRendererUtilKt.renderKind((ClassDescriptor) it) + " '" + strAsString + '\'';
                }
                if (it instanceof TypeAliasDescriptor) {
                    return "typealias '" + strAsString + '\'';
                }
                if (it instanceof TypeAliasConstructorDescriptor) {
                    return "constructor of '" + ((TypeAliasConstructorDescriptor) it).getTypeAliasDescriptor().getShortName().asString() + '\'';
                }
                if (it instanceof ConstructorDescriptor) {
                    return "constructor of '" + ((ConstructorDescriptor) it).getConstructedClass().getShortName().asString() + '\'';
                }
                if (it instanceof PropertyGetterDescriptor) {
                    return "getter of property '" + ((PropertyGetterDescriptor) it).getCorrespondingProperty().getShortName().asString() + '\'';
                }
                if (it instanceof PropertySetterDescriptor) {
                    return "setter of property '" + ((PropertySetterDescriptor) it).getCorrespondingProperty().getShortName().asString() + '\'';
                }
                if (it instanceof FunctionDescriptor) {
                    return "function '" + strAsString + '\'';
                }
                if (it instanceof PropertyDescriptor) {
                    return "property '" + strAsString + '\'';
                }
                throw new AssertionError("Unexpected declaration kind: " + it);
            }
        });
        CAPITALIZED_DECLARATION_NAME_WITH_KIND_AND_PLATFORM = DiagnosticParameterRendererKt.Renderer(new Function1<DeclarationDescriptor, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$CAPITALIZED_DECLARATION_NAME_WITH_KIND_AND_PLATFORM$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(DeclarationDescriptor descriptor) {
                Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                String strRender = Renderers.DECLARATION_NAME_WITH_KIND.render(descriptor);
                if ((descriptor instanceof MemberDescriptor) && ((MemberDescriptor) descriptor).isActual()) {
                    strRender = "actual " + strRender;
                }
                if (strRender.length() <= 0) {
                    return strRender;
                }
                char upperCase = Character.toUpperCase(strRender.charAt(0));
                String strSubstring = strRender.substring(1);
                Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                return upperCase + strSubstring;
            }
        });
        NAME_OF_CONTAINING_DECLARATION_OR_FILE = DiagnosticParameterRendererKt.Renderer(new Function1<DeclarationDescriptor, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$NAME_OF_CONTAINING_DECLARATION_OR_FILE$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(DeclarationDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (DescriptorUtils.isTopLevelDeclaration(it) && (it instanceof DeclarationDescriptorWithVisibility) && Intrinsics.areEqual(((DeclarationDescriptorWithVisibility) it).getVisibility(), DescriptorVisibilities.PRIVATE)) {
                    return "file";
                }
                DeclarationDescriptor containingDeclaration = it.getContainingDeclaration();
                if (containingDeclaration instanceof PackageFragmentDescriptor) {
                    Renderers renderers = Renderers.INSTANCE;
                    String strAsString = ((PackageFragmentDescriptor) containingDeclaration).getFqName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    return renderers.wrapIntoQuotes(strAsString);
                }
                Renderers renderers2 = Renderers.INSTANCE;
                Intrinsics.checkNotNull(containingDeclaration);
                String strAsString2 = containingDeclaration.getShortName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                return renderers2.wrapIntoQuotes(strAsString2);
            }
        });
        ELEMENT_TEXT = DiagnosticParameterRendererKt.Renderer(new Function1<PsiElement, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$ELEMENT_TEXT$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(PsiElement it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String text = it.getText();
                Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
                return text;
            }
        });
        DECLARATION_NAME = DiagnosticParameterRendererKt.Renderer(new Function1<KtNamedDeclaration, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$DECLARATION_NAME$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(KtNamedDeclaration it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String strAsString = it.getNameAsSafeName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return strAsString;
            }
        });
        RENDER_CLASS_OR_OBJECT = DiagnosticParameterRendererKt.Renderer(new Function1<KtClassOrObject, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$RENDER_CLASS_OR_OBJECT$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(KtClassOrObject classOrObject) {
                String str;
                Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
                String name2 = classOrObject.get_name();
                if (name2 == null || (str = " " + Renderers.INSTANCE.wrapIntoQuotes(name2)) == null) {
                    str = "";
                }
                if (classOrObject instanceof KtClass) {
                    return ((KtClass) classOrObject).isInterface() ? "Interface" + str : "Class" + str;
                }
                return CommonClassNames.JAVA_LANG_OBJECT_SHORT + str;
            }
        });
        RENDER_CLASS_OR_OBJECT_NAME = DiagnosticParameterRendererKt.Renderer(new Function1<ClassifierDescriptorWithTypeParameters, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$RENDER_CLASS_OR_OBJECT_NAME$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(ClassifierDescriptorWithTypeParameters it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return DiagnosticRendererUtilKt.renderKindWithName(it);
            }
        });
        RENDER_TYPE = new SmartTypeRenderer(DescriptorRenderer.FQ_NAMES_IN_TYPES.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$RENDER_TYPE$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                invoke2(descriptorRendererOptions);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DescriptorRendererOptions withOptions) {
                Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                withOptions.setParameterNamesInFunctionalTypes(false);
            }
        }));
        RENDER_TYPE_WITH_ANNOTATIONS = new SmartTypeRenderer(DescriptorRenderer.FQ_NAMES_IN_TYPES_WITH_ANNOTATIONS.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$RENDER_TYPE_WITH_ANNOTATIONS$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                invoke2(descriptorRendererOptions);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DescriptorRendererOptions withOptions) {
                Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                withOptions.setParameterNamesInFunctionalTypes(false);
            }
        }));
        TYPE_PROJECTION = DiagnosticParameterRendererKt.Renderer(new Function1<TypeProjection, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$TYPE_PROJECTION$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(TypeProjection projection) {
                Intrinsics.checkNotNullParameter(projection, "projection");
                if (projection.isStarProjection()) {
                    return "*";
                }
                if (projection.getProjectionKind() == Variance.INVARIANT) {
                    SmartTypeRenderer smartTypeRenderer = Renderers.RENDER_TYPE;
                    KotlinType nullableAnyType = projection.getNullableAnyType();
                    Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                    return smartTypeRenderer.render(nullableAnyType, RenderingContext.INSTANCE.of(projection.getNullableAnyType()));
                }
                StringBuilder sbAppend = new StringBuilder().append(projection.getProjectionKind()).append(StringUtil.SPACE);
                SmartTypeRenderer smartTypeRenderer2 = Renderers.RENDER_TYPE;
                KotlinType nullableAnyType2 = projection.getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(nullableAnyType2, "getType(...)");
                return sbAppend.append(smartTypeRenderer2.render(nullableAnyType2, RenderingContext.INSTANCE.of(projection.getNullableAnyType()))).toString();
            }
        });
        AMBIGUOUS_CALLS = DiagnosticParameterRendererKt.Renderer(new Function1<Collection<? extends ResolvedCall<?>>, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$AMBIGUOUS_CALLS$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(Collection<? extends ResolvedCall<?>> calls) {
                Intrinsics.checkNotNullParameter(calls, "calls");
                Collection<? extends ResolvedCall<?>> collection = calls;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
                Iterator<T> it = collection.iterator();
                while (it.hasNext()) {
                    arrayList.add(((ResolvedCall) it.next()).getResultingDescriptor());
                }
                return Renderers.INSTANCE.renderAmbiguousDescriptors(arrayList);
            }
        });
        COMPATIBILITY_CANDIDATE = DiagnosticParameterRendererKt.Renderer(new Function1<CallableDescriptor, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$COMPATIBILITY_CANDIDATE$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(CallableDescriptor call) {
                Intrinsics.checkNotNullParameter(call, "call");
                return Renderers.INSTANCE.renderAmbiguousDescriptors(CollectionsKt.listOf(call));
            }
        });
        AMBIGUOUS_CALLABLE_REFERENCES = DiagnosticParameterRendererKt.Renderer(new Function1<Collection<? extends CallableDescriptor>, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$AMBIGUOUS_CALLABLE_REFERENCES$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(Collection<? extends CallableDescriptor> references) {
                Intrinsics.checkNotNullParameter(references, "references");
                return Renderers.INSTANCE.renderAmbiguousDescriptors(references);
            }
        });
        TYPE_INFERENCE_CONFLICTING_SUBSTITUTIONS_RENDERER = DiagnosticParameterRendererKt.Renderer(new Function1<InferenceErrorData, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$TYPE_INFERENCE_CONFLICTING_SUBSTITUTIONS_RENDERER$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(InferenceErrorData it) {
                Intrinsics.checkNotNullParameter(it, "it");
                TabledDescriptorRenderer tabledDescriptorRendererCreate = TabledDescriptorRenderer.create();
                Intrinsics.checkNotNullExpressionValue(tabledDescriptorRendererCreate, "create(...)");
                String string = Renderers.renderConflictingSubstitutionsInferenceError(it, tabledDescriptorRendererCreate).toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        });
        TYPE_INFERENCE_PARAMETER_CONSTRAINT_ERROR_RENDERER = DiagnosticParameterRendererKt.Renderer(new Function1<InferenceErrorData, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$TYPE_INFERENCE_PARAMETER_CONSTRAINT_ERROR_RENDERER$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(InferenceErrorData it) {
                Intrinsics.checkNotNullParameter(it, "it");
                TabledDescriptorRenderer tabledDescriptorRendererCreate = TabledDescriptorRenderer.create();
                Intrinsics.checkNotNullExpressionValue(tabledDescriptorRendererCreate, "create(...)");
                String string = Renderers.renderParameterConstraintError(it, tabledDescriptorRendererCreate).toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        });
        TYPE_INFERENCE_NO_INFORMATION_FOR_PARAMETER_RENDERER = DiagnosticParameterRendererKt.Renderer(new Function1<InferenceErrorData, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$TYPE_INFERENCE_NO_INFORMATION_FOR_PARAMETER_RENDERER$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(InferenceErrorData it) {
                Intrinsics.checkNotNullParameter(it, "it");
                TabledDescriptorRenderer tabledDescriptorRendererCreate = TabledDescriptorRenderer.create();
                Intrinsics.checkNotNullExpressionValue(tabledDescriptorRendererCreate, "create(...)");
                String string = Renderers.renderNoInformationForParameterError(it, tabledDescriptorRendererCreate).toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        });
        TYPE_INFERENCE_UPPER_BOUND_VIOLATED_RENDERER = DiagnosticParameterRendererKt.Renderer(new Function1<InferenceErrorData, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$TYPE_INFERENCE_UPPER_BOUND_VIOLATED_RENDERER$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(InferenceErrorData it) {
                Intrinsics.checkNotNullParameter(it, "it");
                TabledDescriptorRenderer tabledDescriptorRendererCreate = TabledDescriptorRenderer.create();
                Intrinsics.checkNotNullExpressionValue(tabledDescriptorRendererCreate, "create(...)");
                String string = Renderers.renderUpperBoundViolatedInferenceError(it, tabledDescriptorRendererCreate).toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        });
        TYPE_INFERENCE_CANNOT_CAPTURE_TYPES_RENDERER = DiagnosticParameterRendererKt.Renderer(new Function1<InferenceErrorData, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$TYPE_INFERENCE_CANNOT_CAPTURE_TYPES_RENDERER$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(InferenceErrorData it) {
                Intrinsics.checkNotNullParameter(it, "it");
                TabledDescriptorRenderer tabledDescriptorRendererCreate = TabledDescriptorRenderer.create();
                Intrinsics.checkNotNullExpressionValue(tabledDescriptorRendererCreate, "create(...)");
                String string = Renderers.renderCannotCaptureTypeParameterError(it, tabledDescriptorRendererCreate).toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        });
        CLASSES_OR_SEPARATED = DiagnosticParameterRendererKt.Renderer(new Function1<Collection<? extends ClassDescriptor>, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$CLASSES_OR_SEPARATED$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(Collection<? extends ClassDescriptor> descriptors) {
                Intrinsics.checkNotNullParameter(descriptors, "descriptors");
                StringBuilder sb = new StringBuilder();
                Iterator<? extends ClassDescriptor> it = descriptors.iterator();
                int i = 0;
                while (it.hasNext()) {
                    sb.append(DescriptorUtils.getFqName(it.next()).asString());
                    i++;
                    if (i <= descriptors.size() - 2) {
                        sb.append(", ");
                    } else if (i == descriptors.size() - 1) {
                        sb.append(" or ");
                    }
                }
                String string = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        });
        RENDER_COLLECTION_OF_TYPES = DiagnosticParameterRendererKt.ContextDependentRenderer(new Function2<Collection<? extends KotlinType>, RenderingContext, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$RENDER_COLLECTION_OF_TYPES$1
            @Override // kotlin.jvm.functions.Function2
            public final String invoke(Collection<? extends KotlinType> types, RenderingContext context) {
                Intrinsics.checkNotNullParameter(types, "types");
                Intrinsics.checkNotNullParameter(context, "context");
                return Renderers.INSTANCE.renderTypes(types, Renderers.RENDER_TYPE, context);
            }
        });
        WHEN_MISSING_LIMIT = 7;
        RENDER_WHEN_MISSING_CASES = DiagnosticParameterRendererKt.Renderer(new Function1<List<? extends WhenMissingCase>, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$RENDER_WHEN_MISSING_CASES$1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(List<? extends WhenMissingCase> it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (!Renderers.INSTANCE.getAssumesElseBranchOnly(it)) {
                    return CollectionsKt.joinToString$default(it, ", ", null, null, Renderers.WHEN_MISSING_LIMIT, null, new Function1<WhenMissingCase, CharSequence>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$RENDER_WHEN_MISSING_CASES$1$list$1
                        @Override // kotlin.jvm.functions.Function1
                        public final CharSequence invoke(WhenMissingCase it2) {
                            Intrinsics.checkNotNullParameter(it2, "it");
                            return FormatHelper.QUOTE + it2 + '\'';
                        }
                    }, 22, null) + StringUtil.SPACE + (it.size() > 1 ? "branches" : ConfigConstants.CONFIG_BRANCH_SECTION) + " or 'else' branch instead";
                }
                return "'else' branch";
            }
        });
        FQ_NAMES_IN_TYPES = RenderersKt.asRenderer(DescriptorRenderer.FQ_NAMES_IN_TYPES);
        FQ_NAMES_IN_TYPES_ANNOTATIONS_WHITELIST = AnnotationsWhitelistDescriptorRendererKt.withAnnotationsWhitelist$default(DescriptorRenderer.FQ_NAMES_IN_TYPES_WITH_ANNOTATIONS, null, 1, null);
        FQ_NAMES_IN_TYPES_WITH_ANNOTATIONS = RenderersKt.asRenderer(DescriptorRenderer.FQ_NAMES_IN_TYPES_WITH_ANNOTATIONS);
        COMPACT = RenderersKt.asRenderer(DescriptorRenderer.COMPACT);
        COMPACT_WITHOUT_SUPERTYPES = RenderersKt.asRenderer(DescriptorRenderer.COMPACT_WITHOUT_SUPERTYPES);
        WITHOUT_MODIFIERS = RenderersKt.asRenderer(DescriptorRenderer.INSTANCE.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$WITHOUT_MODIFIERS$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                invoke2(descriptorRendererOptions);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DescriptorRendererOptions withOptions) {
                Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                withOptions.setModifiers(SetsKt.emptySet());
            }
        }));
        SHORT_NAMES_IN_TYPES = RenderersKt.asRenderer(DescriptorRenderer.SHORT_NAMES_IN_TYPES);
        COMPACT_WITH_MODIFIERS = RenderersKt.asRenderer(DescriptorRenderer.COMPACT_WITH_MODIFIERS);
        DEPRECATION_RENDERER = RenderersKt.asRenderer(DescriptorRenderer.ONLY_NAMES_WITH_SHORT_TYPES.withOptions(new Function1<DescriptorRendererOptions, Unit>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$DEPRECATION_RENDERER$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DescriptorRendererOptions descriptorRendererOptions) {
                invoke2(descriptorRendererOptions);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DescriptorRendererOptions withOptions) {
                Intrinsics.checkNotNullParameter(withOptions, "$this$withOptions");
                withOptions.setWithoutTypeParameters(false);
                withOptions.setReceiverAfterName(false);
                withOptions.setPropertyAccessorRenderingPolicy(PropertyAccessorRenderingPolicy.PRETTY);
            }
        }));
        DESCRIPTORS_ON_NEWLINE_WITH_INDENT = new DiagnosticParameterRenderer<Collection<? extends DeclarationDescriptor>>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$DESCRIPTORS_ON_NEWLINE_WITH_INDENT$1
            private final MultiplatformDiagnosticRenderingMode mode = new MultiplatformDiagnosticRenderingMode();

            @Override // org.jetbrains.kotlin.diagnostics.rendering.DiagnosticParameterRenderer
            public String render(Collection<? extends DeclarationDescriptor> obj, RenderingContext renderingContext) {
                Intrinsics.checkNotNullParameter(obj, "obj");
                Intrinsics.checkNotNullParameter(renderingContext, "renderingContext");
                StringBuilder sb = new StringBuilder();
                for (DeclarationDescriptor declarationDescriptor : obj) {
                    this.mode.newLine(sb);
                    this.mode.renderDescriptor(sb, declarationDescriptor, renderingContext, "");
                }
                String string = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                return string;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String renderAmbiguousDescriptors(Collection<? extends CallableDescriptor> descriptors) {
        final RenderingContext.Impl impl = new RenderingContext.Impl(descriptors);
        MemberComparator INSTANCE2 = MemberComparator.INSTANCE;
        Intrinsics.checkNotNullExpressionValue(INSTANCE2, "INSTANCE");
        return CollectionsKt.joinToString$default(CollectionsKt.sortedWith(descriptors, INSTANCE2), "\n", "\n", null, 0, null, new Function1<CallableDescriptor, CharSequence>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers.renderAmbiguousDescriptors.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(CallableDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Renderers.FQ_NAMES_IN_TYPES.render((DeclarationDescriptor) it, (RenderingContext) impl);
            }
        }, 28, null);
    }

    @JvmStatic
    @IDEAPluginsCompatibilityAPI(message = "Please use the CommonRenderers.commaSeparated instead", plugins = "Android plugin in IDEA", usedIn = {IDEAPlatforms._213})
    public static final <T> DiagnosticParameterRenderer<Collection<? extends T>> commaSeparated(DiagnosticParameterRenderer<? super T> itemRenderer) {
        Intrinsics.checkNotNullParameter(itemRenderer, "itemRenderer");
        return CommonRenderers.commaSeparated(itemRenderer);
    }

    @JvmStatic
    public static final TabledDescriptorRenderer renderConflictingSubstitutionsInferenceError(InferenceErrorData inferenceErrorData, TabledDescriptorRenderer result) {
        Intrinsics.checkNotNullParameter(inferenceErrorData, "inferenceErrorData");
        Intrinsics.checkNotNullParameter(result, "result");
        LOG.assertTrue(inferenceErrorData.constraintSystem.getStatus().hasConflictingConstraints(), debugMessage$default(INSTANCE, "Conflicting substitutions inference error renderer is applied for incorrect status", inferenceErrorData, null, 4, null));
        ArrayList<CallableDescriptor> arrayListNewArrayList = Lists.newArrayList();
        Collection<TypeSubstitutor> substitutorsForConflictingParameters = ConstraintsUtil.getSubstitutorsForConflictingParameters(inferenceErrorData.constraintSystem);
        Intrinsics.checkNotNullExpressionValue(substitutorsForConflictingParameters, "getSubstitutorsForConflictingParameters(...)");
        for (TypeSubstitutor typeSubstitutor : substitutorsForConflictingParameters) {
            CallableDescriptor callableDescriptor = inferenceErrorData.descriptor;
            Intrinsics.checkNotNull(typeSubstitutor);
            arrayListNewArrayList.add(callableDescriptor.substitute(typeSubstitutor));
        }
        TypeVariable firstConflictingVariable = ConstraintsUtil.getFirstConflictingVariable(inferenceErrorData.constraintSystem);
        if (firstConflictingVariable == null) {
            LOG.error(debugMessage$default(INSTANCE, "There is no conflicting parameter for 'conflicting constraints' error.", inferenceErrorData, null, 4, null));
            return result;
        }
        result.text(TabledDescriptorRenderer.newText().normal("Cannot infer type parameter ").strong(firstConflictingVariable.getName()).normal(" in "));
        TabledDescriptorRenderer.TableRenderer tableRendererNewTable = TabledDescriptorRenderer.newTable();
        result.table(tableRendererNewTable);
        tableRendererNewTable.descriptor(inferenceErrorData.descriptor).text("None of the following substitutions");
        for (CallableDescriptor callableDescriptor2 : arrayListNewArrayList) {
            KotlinType receiverParameterType = DescriptorUtils.getReceiverParameterType(callableDescriptor2.getExtensionReceiverParameter());
            final HashSet hashSet = new HashSet();
            ArrayList arrayListNewArrayList2 = Lists.newArrayList();
            for (ValueParameterDescriptor valueParameterDescriptor : callableDescriptor2.getValueParameters()) {
                arrayListNewArrayList2.add(valueParameterDescriptor.getType());
                if (valueParameterDescriptor.getIndex() < inferenceErrorData.valueArgumentsTypes.size()) {
                    if (!KotlinTypeChecker.DEFAULT.isSubtypeOf(inferenceErrorData.valueArgumentsTypes.get(valueParameterDescriptor.getIndex()), valueParameterDescriptor.getType())) {
                        hashSet.add(ConstraintPositionKind.VALUE_PARAMETER_POSITION.position(valueParameterDescriptor.getIndex()));
                    }
                }
            }
            if (receiverParameterType != null && inferenceErrorData.receiverArgumentType != null && !KotlinTypeChecker.DEFAULT.isSubtypeOf(inferenceErrorData.receiverArgumentType, receiverParameterType)) {
                hashSet.add(ConstraintPositionKind.RECEIVER_POSITION.position());
            }
            tableRendererNewTable.functionArgumentTypeList(receiverParameterType, arrayListNewArrayList2, new Predicate() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Renderers.renderConflictingSubstitutionsInferenceError$lambda$0(hashSet, (ConstraintPosition) obj);
                }
            });
        }
        tableRendererNewTable.text("can be applied to").functionArgumentTypeList(inferenceErrorData.receiverArgumentType, inferenceErrorData.valueArgumentsTypes);
        return result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean renderConflictingSubstitutionsInferenceError$lambda$0(HashSet errorPositions, ConstraintPosition constraintPosition) {
        Intrinsics.checkNotNullParameter(errorPositions, "$errorPositions");
        return errorPositions.contains(constraintPosition);
    }

    @JvmStatic
    public static final TabledDescriptorRenderer renderParameterConstraintError(InferenceErrorData inferenceErrorData, TabledDescriptorRenderer renderer) {
        Intrinsics.checkNotNullParameter(inferenceErrorData, "inferenceErrorData");
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        List<ConstraintError> constraintErrors = inferenceErrorData.constraintSystem.getStatus().getConstraintErrors();
        ArrayList arrayList = new ArrayList();
        for (Object obj : constraintErrors) {
            if (((ConstraintError) obj) instanceof ParameterConstraintError) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((ConstraintError) it.next()).getConstraintPosition());
        }
        final ArrayList arrayList4 = arrayList3;
        TabledDescriptorRenderer tabledDescriptorRendererTable = renderer.table(TabledDescriptorRenderer.newTable().descriptor(inferenceErrorData.descriptor).text("cannot be applied to").functionArgumentTypeList(inferenceErrorData.receiverArgumentType, inferenceErrorData.valueArgumentsTypes, new Predicate() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj2) {
                return Renderers.renderParameterConstraintError$lambda$3(arrayList4, (ConstraintPosition) obj2);
            }
        }));
        Intrinsics.checkNotNullExpressionValue(tabledDescriptorRendererTable, "table(...)");
        return tabledDescriptorRendererTable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean renderParameterConstraintError$lambda$3(List errorPositions, ConstraintPosition constraintPosition) {
        Intrinsics.checkNotNullParameter(errorPositions, "$errorPositions");
        return errorPositions.contains(constraintPosition);
    }

    @JvmStatic
    public static final TabledDescriptorRenderer renderNoInformationForParameterError(InferenceErrorData inferenceErrorData, TabledDescriptorRenderer result) {
        Object next;
        Intrinsics.checkNotNullParameter(inferenceErrorData, "inferenceErrorData");
        Intrinsics.checkNotNullParameter(result, "result");
        Iterator<T> it = inferenceErrorData.constraintSystem.getTypeVariables().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!inferenceErrorData.constraintSystem.getTypeBounds((TypeVariable) next).getValues().isEmpty());
        TypeVariable typeVariable = (TypeVariable) next;
        if (typeVariable == null) {
            LOG.error(debugMessage$default(INSTANCE, "There is no unknown parameter for 'no information for parameter error'.", inferenceErrorData, null, 4, null));
            return result;
        }
        TabledDescriptorRenderer tabledDescriptorRendererTable = result.text(TabledDescriptorRenderer.newText().normal("Not enough information to infer parameter ").strong(typeVariable.getName()).normal(" in ")).table(TabledDescriptorRenderer.newTable().descriptor(inferenceErrorData.descriptor).text("Please specify it explicitly."));
        Intrinsics.checkNotNullExpressionValue(tabledDescriptorRendererTable, "table(...)");
        return tabledDescriptorRendererTable;
    }

    @JvmStatic
    public static final TabledDescriptorRenderer renderUpperBoundViolatedInferenceError(InferenceErrorData inferenceErrorData, TabledDescriptorRenderer result) {
        KotlinType kotlinType;
        Object next;
        TabledDescriptorRenderer tabledDescriptorRendererRenderUpperBoundViolatedInferenceErrorForTypeAliasConstructor;
        Intrinsics.checkNotNullParameter(inferenceErrorData, "inferenceErrorData");
        Intrinsics.checkNotNullParameter(result, "result");
        ConstraintSystem constraintSystem = inferenceErrorData.constraintSystem;
        Intrinsics.checkNotNullExpressionValue(constraintSystem, "constraintSystem");
        ConstraintSystemStatus status = constraintSystem.getStatus();
        LOG.assertTrue(status.hasViolatedUpperBound(), debugMessage$default(INSTANCE, "Upper bound violated renderer is applied for incorrect status", inferenceErrorData, null, 4, null));
        ConstraintSystem constraintSystemFilterConstraintsOut = ConstraintSystemUtilsKt.filterConstraintsOut(constraintSystem, ConstraintPositionKind.TYPE_BOUND_POSITION);
        List<TypeParameterDescriptor> typeParameters = inferenceErrorData.descriptor.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        Iterator<T> it = typeParameters.iterator();
        do {
            kotlinType = null;
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (ConstraintsUtil.checkUpperBoundIsSatisfied(constraintSystemFilterConstraintsOut, (TypeParameterDescriptor) next, inferenceErrorData.call, true));
        TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) next;
        if (typeParameterDescriptor == null) {
            if ((inferenceErrorData.descriptor instanceof TypeAliasConstructorDescriptor) && (tabledDescriptorRendererRenderUpperBoundViolatedInferenceErrorForTypeAliasConstructor = INSTANCE.renderUpperBoundViolatedInferenceErrorForTypeAliasConstructor(inferenceErrorData, result, constraintSystemFilterConstraintsOut)) != null) {
                return tabledDescriptorRendererRenderUpperBoundViolatedInferenceErrorForTypeAliasConstructor;
            }
            if (status.hasConflictingConstraints()) {
                return renderConflictingSubstitutionsInferenceError(inferenceErrorData, result);
            }
            LOG.error(INSTANCE.debugMessage("There is no type parameter with violated upper bound for 'upper bound violated' error", inferenceErrorData, ConstraintSystemRenderingVerbosity.EXTRA_VERBOSE));
            return result;
        }
        Call call = inferenceErrorData.call;
        Intrinsics.checkNotNullExpressionValue(call, "call");
        KotlinType value = constraintSystemFilterConstraintsOut.getTypeBounds(ConstraintSystemUtilsKt.descriptorToVariable(constraintSystemFilterConstraintsOut, TypeVariableKt.toHandle(call), typeParameterDescriptor)).getValue();
        if (value == null) {
            LOG.error(debugMessage$default(INSTANCE, "System without weak constraints is not successful, there is no value for type parameter " + typeParameterDescriptor.getShortName() + "\n: " + constraintSystemFilterConstraintsOut, inferenceErrorData, null, 4, null));
            return result;
        }
        result.text(TabledDescriptorRenderer.newText().normal("Type parameter bound for ").strong(typeParameterDescriptor.getShortName()).normal(" in ")).table(TabledDescriptorRenderer.newTable().descriptor(inferenceErrorData.descriptor));
        Iterator<KotlinType> it2 = typeParameterDescriptor.getUpperBounds().iterator();
        while (it2.hasNext()) {
            KotlinType kotlinTypeSubstitute = constraintSystemFilterConstraintsOut.getResultingSubstitutor().substitute(it2.next(), Variance.INVARIANT);
            if (kotlinTypeSubstitute != null && !KotlinTypeChecker.DEFAULT.isSubtypeOf(value, kotlinTypeSubstitute)) {
                kotlinType = kotlinTypeSubstitute;
                break;
            }
        }
        if (kotlinType == null) {
            LOG.error(debugMessage$default(INSTANCE, "Type parameter (chosen as violating its upper bound)" + typeParameterDescriptor.getShortName() + " violates no bounds after substitution", inferenceErrorData, null, 4, null));
            return result;
        }
        RenderingContext renderingContextOf = RenderingContext.INSTANCE.of(value, kotlinType);
        DiagnosticParameterRenderer<KotlinType> typeRenderer = result.getTypeRenderer();
        Intrinsics.checkNotNullExpressionValue(typeRenderer, "getTypeRenderer(...)");
        result.text(TabledDescriptorRenderer.newText().normal(" is not satisfied: inferred type ").error(typeRenderer.render(value, renderingContextOf)).normal(" is not a subtype of ").strong(typeRenderer.render(kotlinType, renderingContextOf)));
        return result;
    }

    private final TabledDescriptorRenderer renderUpperBoundViolatedInferenceErrorForTypeAliasConstructor(InferenceErrorData inferenceErrorData, TabledDescriptorRenderer result, ConstraintSystem systemWithoutWeakConstraints) {
        final CallableDescriptor descriptor = inferenceErrorData.descriptor;
        Intrinsics.checkNotNullExpressionValue(descriptor, "descriptor");
        if (!(descriptor instanceof TypeAliasConstructorDescriptor)) {
            LOG.error("Type alias constructor descriptor expected: " + descriptor);
            return result;
        }
        List<TypeParameterDescriptor> typeParameters = ((TypeAliasConstructorDescriptor) descriptor).getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        List<TypeParameterDescriptor> list = typeParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (TypeParameterDescriptor typeParameterDescriptor : list) {
            Call call = inferenceErrorData.call;
            Intrinsics.checkNotNullExpressionValue(call, "call");
            CallHandle handle = TypeVariableKt.toHandle(call);
            Intrinsics.checkNotNull(typeParameterDescriptor);
            arrayList.add(systemWithoutWeakConstraints.getTypeBounds(ConstraintSystemUtilsKt.descriptorToVariable(systemWithoutWeakConstraints, handle, typeParameterDescriptor)).getValue());
        }
        final ArrayList arrayList2 = arrayList;
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(new TypeConstructorSubstitution() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$renderUpperBoundViolatedInferenceErrorForTypeAliasConstructor$inferredTypeSubstitutor$1
            @Override // org.jetbrains.kotlin.types.TypeConstructorSubstitution
            public TypeProjection get(TypeConstructor key) {
                Intrinsics.checkNotNullParameter(key, "key");
                ClassifierDescriptor this$0 = key.getThis$0();
                TypeParameterDescriptor typeParameterDescriptor2 = this$0 instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) this$0 : null;
                if (typeParameterDescriptor2 == null || !Intrinsics.areEqual(typeParameterDescriptor2.getContainingDeclaration(), ((TypeAliasConstructorDescriptor) descriptor).getTypeAliasDescriptor())) {
                    return null;
                }
                KotlinType kotlinType = arrayList2.get(typeParameterDescriptor2.getIndex());
                return kotlinType != null ? new TypeProjectionImpl(kotlinType) : null;
            }
        });
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        Iterator<ConstraintError> it = inferenceErrorData.constraintSystem.getStatus().getConstraintErrors().iterator();
        while (it.hasNext()) {
            ValidityConstraintForConstituentType validityConstraintForConstituentType = ConstraintPositionKt.getValidityConstraintForConstituentType(it.next().getConstraintPosition());
            if (validityConstraintForConstituentType != null) {
                KotlinType kotlinTypeSafeSubstitute = typeSubstitutorCreate.safeSubstitute(validityConstraintForConstituentType.getBound(), Variance.INVARIANT);
                Intrinsics.checkNotNullExpressionValue(kotlinTypeSafeSubstitute, "safeSubstitute(...)");
                KotlinType kotlinTypeSafeSubstitute2 = typeSubstitutorCreate.safeSubstitute(validityConstraintForConstituentType.getTypeArgument(), Variance.INVARIANT);
                Intrinsics.checkNotNullExpressionValue(kotlinTypeSafeSubstitute2, "safeSubstitute(...)");
                RenderingContext renderingContextOf = RenderingContext.INSTANCE.of(kotlinTypeSafeSubstitute2, kotlinTypeSafeSubstitute);
                DiagnosticParameterRenderer<KotlinType> typeRenderer = result.getTypeRenderer();
                Intrinsics.checkNotNullExpressionValue(typeRenderer, "getTypeRenderer(...)");
                result.text(TabledDescriptorRenderer.newText().normal("Type parameter bound for ").strong(validityConstraintForConstituentType.getTypeParameter().getShortName()).normal(" in type inferred from type alias expansion for ")).table(TabledDescriptorRenderer.newTable().descriptor(inferenceErrorData.descriptor));
                result.text(TabledDescriptorRenderer.newText().normal(" is not satisfied: inferred type ").error(typeRenderer.render(kotlinTypeSafeSubstitute2, renderingContextOf)).normal(" is not a subtype of ").strong(typeRenderer.render(kotlinTypeSafeSubstitute, renderingContextOf)));
                return result;
            }
        }
        return null;
    }

    @JvmStatic
    public static final TabledDescriptorRenderer renderCannotCaptureTypeParameterError(InferenceErrorData inferenceErrorData, TabledDescriptorRenderer result) {
        Object next;
        Object next2;
        KotlinType constrainingType;
        Intrinsics.checkNotNullParameter(inferenceErrorData, "inferenceErrorData");
        Intrinsics.checkNotNullParameter(result, "result");
        ConstraintSystem constraintSystem = inferenceErrorData.constraintSystem;
        Intrinsics.checkNotNullExpressionValue(constraintSystem, "constraintSystem");
        Iterator<T> it = constraintSystem.getStatus().getConstraintErrors().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!(next instanceof CannotCapture));
        CannotCapture cannotCapture = (CannotCapture) next;
        TypeVariable typeVariable = cannotCapture != null ? cannotCapture.getTypeVariable() : null;
        if (typeVariable == null) {
            LOG.error(debugMessage$default(INSTANCE, "An error 'cannot capture type parameter' is not found in errors", inferenceErrorData, null, 4, null));
            return result;
        }
        Iterator<T> it2 = constraintSystem.getTypeBounds(typeVariable).getBounds().iterator();
        do {
            if (!it2.hasNext()) {
                next2 = null;
                break;
            }
            next2 = it2.next();
        } while (!CapturedTypeConstructorKt.isCaptured(((TypeBounds.Bound) next2).getConstrainingType()));
        TypeBounds.Bound bound = (TypeBounds.Bound) next2;
        TypeConstructor constructor = (bound == null || (constrainingType = bound.getConstrainingType()) == null) ? null : constrainingType.getConstructor();
        CapturedTypeConstructor capturedTypeConstructor = constructor instanceof CapturedTypeConstructor ? (CapturedTypeConstructor) constructor : null;
        if (capturedTypeConstructor == null) {
            LOG.error(debugMessage$default(INSTANCE, "There is no captured type in bounds, but there is an error 'cannot capture type parameter'", inferenceErrorData, null, 4, null));
            return result;
        }
        TypeParameterDescriptor originalTypeParameter = typeVariable.getOriginalTypeParameter();
        KotlinType upperBoundsAsType = TypeIntersector.getUpperBoundsAsType(originalTypeParameter);
        Intrinsics.checkNotNullExpressionValue(upperBoundsAsType, "getUpperBoundsAsType(...)");
        if (!KotlinBuiltIns.isNullableAny(upperBoundsAsType)) {
            capturedTypeConstructor.getProjection().getProjectionKind();
            Variance variance = Variance.IN_VARIANCE;
        }
        StringBuilder sb = new StringBuilder("Type parameter has an upper bound ");
        Renderers renderers = INSTANCE;
        String string = sb.append(renderers.wrapIntoQuotes(result.getTypeRenderer().render(upperBoundsAsType, RenderingContext.INSTANCE.of(upperBoundsAsType)))).append(" that cannot be satisfied capturing 'in' projection").toString();
        TabledDescriptorRenderer.TextRenderer textRendererNewText = TabledDescriptorRenderer.newText();
        StringBuilder sb2 = new StringBuilder();
        Name name2 = originalTypeParameter.getShortName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        result.text(textRendererNewText.normal(sb2.append(renderers.wrapIntoQuotes(name2)).append(" cannot capture ").append(renderers.wrapIntoQuotes(result.getTypeProjectionRenderer().render(capturedTypeConstructor.getProjection(), RenderingContext.INSTANCE.of(capturedTypeConstructor.getProjection())))).append(". ").append(string).toString()));
        return result;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String renderTypes(Collection<? extends KotlinType> types, final DiagnosticParameterRenderer<? super KotlinType> typeRenderer, final RenderingContext context) {
        final Function1<KotlinType, String> function1 = new Function1<KotlinType, String>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers.renderTypes.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final String invoke(KotlinType kotlinType) {
                DiagnosticParameterRenderer<KotlinType> diagnosticParameterRenderer = typeRenderer;
                Intrinsics.checkNotNull(kotlinType);
                return diagnosticParameterRenderer.render(kotlinType, context);
            }
        };
        String strJoin = org.jetbrains.kotlin.com.intellij.openapi.util.text.StringUtil.join((Collection) types, new Function() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.com.intellij.util.Function
            public final Object fun(Object obj) {
                return Renderers.renderTypes$lambda$11(function1, obj);
            }
        }, ", ");
        Intrinsics.checkNotNullExpressionValue(strJoin, "join(...)");
        return strJoin;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String renderTypes$lambda$11(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (String) tmp0.invoke(obj);
    }

    public final String renderConstraintSystem(ConstraintSystem constraintSystem, final ConstraintSystemRenderingVerbosity verbosity) {
        Intrinsics.checkNotNullParameter(constraintSystem, "constraintSystem");
        Intrinsics.checkNotNullParameter(verbosity, "verbosity");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<TypeVariable> it = constraintSystem.getTypeVariables().iterator();
        while (it.hasNext()) {
            linkedHashSet.add(constraintSystem.getTypeBounds(it.next()));
        }
        return "type parameter bounds:\n" + CollectionsKt.joinToString$default(linkedHashSet, verbosity == ConstraintSystemRenderingVerbosity.EXTRA_VERBOSE ? "\n\n" : "\n", null, null, 0, null, new Function1<TypeBounds, CharSequence>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers.renderConstraintSystem.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(TypeBounds it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return Renderers.INSTANCE.renderTypeBounds(it2, verbosity);
            }
        }, 30, null) + "\n\nstatus:\n" + ConstraintsUtil.getDebugMessageForStatus(constraintSystem.getStatus());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String renderTypeBounds(TypeBounds typeBounds, final ConstraintSystemRenderingVerbosity verbosity) {
        String strRenderTypeVariable = renderTypeVariable(typeBounds.getTypeVariable(), verbosity == ConstraintSystemRenderingVerbosity.EXTRA_VERBOSE);
        if (typeBounds.getBounds().isEmpty()) {
            return strRenderTypeVariable;
        }
        return strRenderTypeVariable + (verbosity == ConstraintSystemRenderingVerbosity.EXTRA_VERBOSE ? "\n" : " ") + CollectionsKt.joinToString$default(typeBounds.getBounds(), verbosity != ConstraintSystemRenderingVerbosity.EXTRA_VERBOSE ? ", " : "\n", null, null, 0, null, new Function1<TypeBounds.Bound, CharSequence>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers$renderTypeBounds$renderedBounds$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(TypeBounds.Bound it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Renderers.INSTANCE.renderTypeBound(it, verbosity);
            }
        }, 30, null);
    }

    private final String renderTypeVariable(TypeVariable typeVariable, boolean includeTypeConstructor) {
        String strAsString = typeVariable.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        if (!includeTypeConstructor) {
            return strAsString;
        }
        StringBuilder sbAppend = new StringBuilder("TypeVariable ").append(strAsString).append(", descriptor = ").append(typeVariable.getFreshTypeParameter()).append(", typeConstructor = ");
        TypeConstructor typeConstructor = typeVariable.getFreshTypeParameter().getTypeConstructor();
        Intrinsics.checkNotNullExpressionValue(typeConstructor, "getTypeConstructor(...)");
        return sbAppend.append(renderTypeConstructor(typeConstructor)).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String renderTypeBound(TypeBounds.Bound bound, ConstraintSystemRenderingVerbosity verbosity) {
        String str;
        final DescriptorRenderer descriptorRenderer = verbosity == ConstraintSystemRenderingVerbosity.COMPACT ? DescriptorRenderer.SHORT_NAMES_IN_TYPES : DescriptorRenderer.FQ_NAMES_IN_TYPES;
        int i = WhenMappings.$EnumSwitchMapping$0[bound.getKind().ordinal()];
        if (i != 1) {
            str = i != 2 ? ":= " : "<: ";
        } else {
            str = ">: ";
        }
        String str2 = str + descriptorRenderer.renderType(bound.getConstrainingType()) + (!bound.getIsProper() ? "*" : "");
        int i2 = WhenMappings.$EnumSwitchMapping$1[verbosity.ordinal()];
        if (i2 == 1) {
            return str2;
        }
        if (i2 == 2) {
            return str2 + " (" + bound.getPosition() + ") ";
        }
        if (i2 != 3) {
            throw new NoWhenBranchMatchedException();
        }
        StringBuilder sbAppend = new StringBuilder().append(str2).append(" (").append(bound.getPosition()).append(")\nConstraining type additional info: ").append(renderTypeConstructor(bound.getConstrainingType().getConstructor())).append("\nSupertypes of constraining type:\n");
        Set<KotlinType> allSupertypes = TypeUtils.getAllSupertypes(bound.getConstrainingType());
        Intrinsics.checkNotNullExpressionValue(allSupertypes, "getAllSupertypes(...)");
        return sbAppend.append(CollectionsKt.joinToString$default(allSupertypes, "\n", null, null, 0, null, new Function1<KotlinType, CharSequence>() { // from class: org.jetbrains.kotlin.diagnostics.rendering.Renderers.renderTypeBound.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(KotlinType kotlinType) {
                StringBuilder sb = new StringBuilder("- ");
                DescriptorRenderer descriptorRenderer2 = descriptorRenderer;
                Intrinsics.checkNotNull(kotlinType);
                return sb.append(descriptorRenderer2.renderType(kotlinType)).append(", TypeConstructor info: ").append(Renderers.INSTANCE.renderTypeConstructor(kotlinType.getConstructor())).toString();
            }
        }, 30, null)).append('\n').toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String renderTypeConstructor(TypeConstructor typeConstructor) {
        StringBuilder sbAppend = new StringBuilder().append(typeConstructor).append('[').append(typeConstructor.getClass().getName()).append("], ");
        AbstractTypeConstructor abstractTypeConstructor = typeConstructor instanceof AbstractTypeConstructor ? (AbstractTypeConstructor) typeConstructor : null;
        return sbAppend.append(abstractTypeConstructor != null ? abstractTypeConstructor.renderAdditionalDebugInformation() : null).toString();
    }

    static /* synthetic */ String debugMessage$default(Renderers renderers, String str, InferenceErrorData inferenceErrorData, ConstraintSystemRenderingVerbosity constraintSystemRenderingVerbosity, int i, Object obj) {
        if ((i & 4) != 0) {
            constraintSystemRenderingVerbosity = ConstraintSystemRenderingVerbosity.DEBUG;
        }
        return renderers.debugMessage(str, inferenceErrorData, constraintSystemRenderingVerbosity);
    }

    private final String debugMessage(String message, InferenceErrorData inferenceErrorData, ConstraintSystemRenderingVerbosity verbosity) {
        StringBuilder sb = new StringBuilder();
        sb.append(message);
        sb.append("\nConstraint system: \n");
        Renderers renderers = INSTANCE;
        ConstraintSystem constraintSystem = inferenceErrorData.constraintSystem;
        Intrinsics.checkNotNullExpressionValue(constraintSystem, "constraintSystem");
        sb.append(renderers.renderConstraintSystem(constraintSystem, verbosity));
        sb.append("\nDescriptor:\n");
        sb.append(inferenceErrorData.descriptor);
        sb.append("\nExpected type:\n");
        RenderingContext.Empty empty = RenderingContext.Empty.INSTANCE;
        if (TypeUtils.noExpectedType(inferenceErrorData.expectedType)) {
            sb.append(inferenceErrorData.expectedType);
        } else {
            SmartTypeRenderer smartTypeRenderer = RENDER_TYPE_WITH_ANNOTATIONS;
            KotlinType expectedType = inferenceErrorData.expectedType;
            Intrinsics.checkNotNullExpressionValue(expectedType, "expectedType");
            sb.append(smartTypeRenderer.render(expectedType, (RenderingContext) empty));
        }
        sb.append("\nArgument types:\n");
        if (inferenceErrorData.receiverArgumentType != null) {
            SmartTypeRenderer smartTypeRenderer2 = RENDER_TYPE_WITH_ANNOTATIONS;
            KotlinType receiverArgumentType = inferenceErrorData.receiverArgumentType;
            Intrinsics.checkNotNullExpressionValue(receiverArgumentType, "receiverArgumentType");
            sb.append(smartTypeRenderer2.render(receiverArgumentType, (RenderingContext) empty)).append(".");
        }
        StringBuilder sbAppend = sb.append("(");
        List<KotlinType> valueArgumentsTypes = inferenceErrorData.valueArgumentsTypes;
        Intrinsics.checkNotNullExpressionValue(valueArgumentsTypes, "valueArgumentsTypes");
        sbAppend.append(renderers.renderTypes(valueArgumentsTypes, RENDER_TYPE_WITH_ANNOTATIONS, empty)).append(Constant.AFTER_QUTO);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final String wrapIntoQuotes(String str) {
        return FormatHelper.QUOTE + str + '\'';
    }

    private final String wrapIntoQuotes(Name name2) {
        return FormatHelper.QUOTE + name2.asString() + '\'';
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean getAssumesElseBranchOnly(List<? extends WhenMissingCase> list) {
        List<? extends WhenMissingCase> list2 = list;
        if ((list2 instanceof Collection) && list2.isEmpty()) {
            return false;
        }
        for (WhenMissingCase whenMissingCase : list2) {
            if (Intrinsics.areEqual(whenMissingCase, WhenMissingCase.Unknown.INSTANCE) || (whenMissingCase instanceof WhenMissingCase.ConditionTypeIsExpect)) {
                return true;
            }
        }
        return false;
    }

    public final String renderExpressionType(KotlinType type, Set<? extends KotlinType> dataFlowTypes) {
        if (type == null) {
            return "Type is unknown";
        }
        if (dataFlowTypes == null) {
            return DescriptorRenderer.DEBUG_TEXT.renderType(type);
        }
        Set<? extends KotlinType> set = dataFlowTypes;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(DescriptorRenderer.DEBUG_TEXT.renderType((KotlinType) it.next()));
        }
        Set mutableSet = CollectionsKt.toMutableSet(arrayList);
        mutableSet.add(DescriptorRenderer.DEBUG_TEXT.renderType(type));
        return CollectionsKt.joinToString$default(CollectionsKt.sorted(mutableSet), " & ", null, null, 0, null, null, 62, null);
    }

    public final String renderCallInfo(FqNameUnsafe fqName, String typeCall) {
        String strAsString;
        Intrinsics.checkNotNullParameter(typeCall, "typeCall");
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder("fqName: ");
        if (fqName == null || (strAsString = fqName.asString()) == null) {
            strAsString = "fqName is unknown";
        }
        sb.append(sb2.append(strAsString).append("; ").toString());
        sb.append("typeCall: " + typeCall);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
