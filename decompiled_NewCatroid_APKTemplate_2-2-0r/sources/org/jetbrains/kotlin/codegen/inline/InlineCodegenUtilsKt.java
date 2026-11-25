package org.jetbrains.kotlin.codegen.inline;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.output.OutputFile;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.AssertCodegenUtilKt;
import org.jetbrains.kotlin.codegen.SamWrapperCodegen;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.codegen.binding.CodegenBinding;
import org.jetbrains.kotlin.codegen.context.CodegenContext;
import org.jetbrains.kotlin.codegen.context.CodegenContextUtil;
import org.jetbrains.kotlin.codegen.context.InlineLambdaContext;
import org.jetbrains.kotlin.codegen.context.MethodContext;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.common.InsnSequence;
import org.jetbrains.kotlin.codegen.optimization.common.UtilKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapperBase;
import org.jetbrains.kotlin.codegen.when.WhenByEnumsMapping;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.fileClasses.JvmFileClassUtil;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.kotlin.VirtualFileFinder;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeConstructor;
import org.jetbrains.kotlin.types.TypeProjectionImpl;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.util.OperatorNameConventions;
import org.jetbrains.org.objectweb.asm.ClassReader;
import org.jetbrains.org.objectweb.asm.ClassVisitor;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.commons.Method;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;
import org.jetbrains.org.objectweb.asm.tree.FrameNode;
import org.jetbrains.org.objectweb.asm.tree.IincInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.jetbrains.org.objectweb.asm.tree.InsnNode;
import org.jetbrains.org.objectweb.asm.tree.IntInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InvokeDynamicInsnNode;
import org.jetbrains.org.objectweb.asm.tree.JumpInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LabelNode;
import org.jetbrains.org.objectweb.asm.tree.LdcInsnNode;
import org.jetbrains.org.objectweb.asm.tree.LineNumberNode;
import org.jetbrains.org.objectweb.asm.tree.LocalVariableNode;
import org.jetbrains.org.objectweb.asm.tree.LookupSwitchInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodInsnNode;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;
import org.jetbrains.org.objectweb.asm.tree.MultiANewArrayInsnNode;
import org.jetbrains.org.objectweb.asm.tree.TableSwitchInsnNode;
import org.jetbrains.org.objectweb.asm.tree.TryCatchBlockNode;
import org.jetbrains.org.objectweb.asm.tree.TypeInsnNode;
import org.jetbrains.org.objectweb.asm.tree.VarInsnNode;
import org.jetbrains.org.objectweb.asm.util.Printer;
import org.jetbrains.org.objectweb.asm.util.Textifier;
import org.jetbrains.org.objectweb.asm.util.TraceMethodVisitor;

/* JADX INFO: compiled from: inlineCodegenUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000Ö\u0001\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\u0002\u001a\u0010\u0010-\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\u0002\u001a\u0016\u0010.\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010/\u001a\u00020\u0006\u001a\u000e\u00100\u001a\u00020*2\u0006\u0010+\u001a\u00020,\u001a\u0016\u00101\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010/\u001a\u00020\u0006\u001a\u000e\u00102\u001a\u00020*2\u0006\u0010+\u001a\u00020,\u001a\u001c\u00103\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\n\u00104\u001a\u0006\u0012\u0002\b\u000305H\u0000\u001a \u00106\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010/\u001a\u00020\u00062\b\b\u0002\u00107\u001a\u00020\u0006\u001a \u00108\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020<H\u0000\u001a\u0018\u0010=\u001a\u00020\u00062\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020?H\u0000\u001a\u0018\u0010A\u001a\u00020\u000b2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020&H\u0000\u001a\u000e\u0010E\u001a\u00020&2\u0006\u0010F\u001a\u00020&\u001a\u0006\u0010G\u001a\u00020&\u001a\b\u0010H\u001a\u00020&H\u0000\u001a\u001a\u0010I\u001a\u0004\u0018\u00010J2\u0006\u0010K\u001a\u00020L2\u0006\u0010M\u001a\u00020NH\u0000\u001a\u001a\u0010O\u001a\u0004\u0018\u00010J2\u0006\u0010K\u001a\u00020L2\u0006\u0010P\u001a\u00020\u0001H\u0000\u001a\u0010\u0010Q\u001a\u00020?2\u0006\u0010D\u001a\u00020?H\u0000\u001a\u001e\u0010R\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010S\u001a\u00020\u000b2\u0006\u0010T\u001a\u00020\u0006\u001a\u0016\u0010U\u001a\u00020*2\u0006\u0010V\u001a\u00020,2\u0006\u0010W\u001a\u00020\u0001\u001a\u001e\u0010X\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0006\u0010Y\u001a\u00020Z2\u0006\u0010;\u001a\u00020[\u001a\u000e\u0010\\\u001a\u00020\u000b2\u0006\u0010]\u001a\u00020 \u001a\u0010\u0010^\u001a\u00020\u000b2\u0006\u0010D\u001a\u00020&H\u0002\u001a\u001c\u0010_\u001a\u00020\u00012\n\u0010`\u001a\u0006\u0012\u0002\b\u00030a2\u0006\u0010;\u001a\u00020<H\u0000\u001a$\u0010_\u001a\u00020\u00012\n\u0010`\u001a\u0006\u0012\u0002\b\u00030a2\u0006\u0010b\u001a\u00020c2\u0006\u0010;\u001a\u00020<H\u0002\u001a\u0010\u0010d\u001a\u00020\u000b2\u0006\u0010e\u001a\u00020\u000bH\u0000\u001a\u0012\u0010f\u001a\u0004\u0018\u00010\u00012\u0006\u0010g\u001a\u00020 H\u0000\u001a4\u0010h\u001a\u0004\u0018\u00010i2\u0006\u0010j\u001a\u00020k2\u0006\u0010l\u001a\u00020m2\u0014\b\u0004\u0010n\u001a\u000e\u0012\u0004\u0012\u00020p\u0012\u0004\u0012\u00020\u00060oH\u0080\bø\u0001\u0000\u001a\"\u0010h\u001a\u0004\u0018\u00010i2\u0006\u0010j\u001a\u00020k2\u0006\u0010l\u001a\u00020m2\u0006\u0010q\u001a\u00020pH\u0000\u001a\u0010\u0010r\u001a\u00020m2\u0006\u0010e\u001a\u00020\u000bH\u0000\u001a\u001e\u0010s\u001a\u00020*2\u0006\u0010t\u001a\u00020&2\u0006\u0010u\u001a\u00020&2\u0006\u0010v\u001a\u00020 \u001a\u0010\u0010w\u001a\u00020\u00062\u0006\u0010x\u001a\u00020 H\u0000\u001a\u0010\u0010y\u001a\u00020\u00062\u0006\u0010x\u001a\u00020 H\u0000\u001a\u0010\u0010z\u001a\u00020\u00062\u0006\u0010x\u001a\u00020 H\u0000\u001a\u0010\u0010{\u001a\u00020\u00062\u0006\u0010x\u001a\u00020 H\u0000\u001a\u0010\u0010|\u001a\u00020\u00062\u0006\u0010x\u001a\u00020 H\u0000\u001a\u0010\u0010}\u001a\u00020\u00062\u0006\u0010~\u001a\u00020\u0001H\u0000\u001a\u0019\u0010\u007f\u001a\u00020\u00062\u0006\u0010~\u001a\u00020\u00012\u0007\u0010\u0080\u0001\u001a\u00020\u0001H\u0000\u001a\u001a\u0010\u0081\u0001\u001a\u00020\u00062\u0006\u0010~\u001a\u00020\u00012\u0007\u0010\u0082\u0001\u001a\u00020\u0001H\u0000\u001a\u0011\u0010\u0083\u0001\u001a\u00020\u00062\u0006\u0010x\u001a\u00020 H\u0000\u001a\u0011\u0010\u0084\u0001\u001a\u00020\u00062\u0006\u0010x\u001a\u00020 H\u0000\u001a\u0011\u0010\u0085\u0001\u001a\u00020\u00062\u0006\u0010x\u001a\u00020 H\u0000\u001a\u0011\u0010\u0086\u0001\u001a\u00020\u00062\u0006\u0010x\u001a\u00020 H\u0000\u001a\u0011\u0010\u0087\u0001\u001a\u00020\u00062\u0006\u0010x\u001a\u00020 H\u0000\u001a\u0012\u0010\u0088\u0001\u001a\u00020\u00062\u0007\u0010\u0082\u0001\u001a\u00020\u0001H\u0000\u001a\u0012\u0010\u0089\u0001\u001a\u00020\u00062\u0007\u0010\u0080\u0001\u001a\u00020\u0001H\u0002\u001a\u0011\u0010\u008a\u0001\u001a\u00020\u00062\u0006\u0010x\u001a\u00020 H\u0000\u001a\u0010\u0010\u008b\u0001\u001a\u00020\u00062\u0007\u0010\u008c\u0001\u001a\u00020\u0001\u001a\u000f\u0010\u008d\u0001\u001a\u00020\u00062\u0006\u0010D\u001a\u00020 \u001a\u001a\u0010\u008e\u0001\u001a\u00020\u00062\u0006\u0010D\u001a\u00020 2\u0007\u0010\u008c\u0001\u001a\u00020\u0001H\u0002\u001a\u0011\u0010\u008e\u0001\u001a\u00020\u00062\b\u0010D\u001a\u0004\u0018\u00010 \u001a\u0013\u0010\u008f\u0001\u001a\u00020\u00062\b\u0010\u0090\u0001\u001a\u00030\u0091\u0001H\u0000\u001a\u000f\u0010\u0092\u0001\u001a\u00020\u00062\u0006\u0010D\u001a\u00020 \u001a\u0011\u0010\u0093\u0001\u001a\u00020\u00062\u0006\u0010x\u001a\u00020 H\u0000\u001a\u001c\u0010\u0093\u0001\u001a\u00020\u00062\u0006\u0010x\u001a\u00020 2\t\u0010\u008c\u0001\u001a\u0004\u0018\u00010\u0001H\u0000\u001a\u001b\u0010\u0094\u0001\u001a\u00020\u00062\u0007\u0010\u0095\u0001\u001a\u00020\u00012\u0007\u0010\u008c\u0001\u001a\u00020\u0001H\u0000\u001a\u0012\u0010\u0096\u0001\u001a\u00020\u00062\u0007\u0010\u0097\u0001\u001a\u00020 H\u0000\u001a\u0011\u0010\u0098\u0001\u001a\u00020\u00062\u0006\u0010~\u001a\u00020\u0001H\u0002\u001a\u0011\u0010\u0099\u0001\u001a\u00020\u00062\u0006\u0010e\u001a\u00020\u000bH\u0000\u001a\u0011\u0010\u009a\u0001\u001a\u00020\u00062\u0006\u0010x\u001a\u00020 H\u0000\u001a\u0011\u0010\u009b\u0001\u001a\u00020\u00062\u0006\u0010~\u001a\u00020\u0001H\u0000\u001a\u001a\u0010\u009c\u0001\u001a\u00020\u00062\u0006\u0010~\u001a\u00020\u00012\u0007\u0010\u0080\u0001\u001a\u00020\u0001H\u0000\u001a\u0011\u0010\u009d\u0001\u001a\u00020\u00062\u0006\u0010e\u001a\u00020\u000bH\u0000\u001a\u0011\u0010\u009e\u0001\u001a\u00020\u00062\u0006\u0010x\u001a\u00020 H\u0000\u001a\u001a\u0010\u009f\u0001\u001a\u00020\u00062\u0006\u0010x\u001a\u00020 2\u0007\u0010 \u0001\u001a\u00020\u000bH\u0002\u001a\u0012\u0010¡\u0001\u001a\u00020\u00062\u0007\u0010\u008c\u0001\u001a\u00020\u0001H\u0000\u001a\u001a\u0010¢\u0001\u001a\u00020\u00062\u0006\u0010~\u001a\u00020\u00012\u0007\u0010\u0082\u0001\u001a\u00020\u0001H\u0000\u001a\u0019\u0010£\u0001\u001a\u00020k2\u0006\u0010K\u001a\u00020L2\u0006\u0010~\u001a\u00020\u0001H\u0000\u001a\"\u0010¤\u0001\u001a\u00020&2\u0013\u0010¥\u0001\u001a\u000e\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020*0oH\u0086\bø\u0001\u0000\u001a\u0012\u0010¦\u0001\u001a\u00020*2\u0007\u0010§\u0001\u001a\u00020&H\u0000\u001a\u0010\u0010¨\u0001\u001a\u00030©\u00012\u0006\u0010F\u001a\u00020&\u001a\u000b\u0010ª\u0001\u001a\u00020\u0001*\u00020&\u001a\u0016\u0010«\u0001\u001a\u00020**\u00020,2\u0007\u0010 \u0001\u001a\u00020\u000bH\u0002\u001a\u0016\u0010#\u001a\u00020\u0001*\u0004\u0018\u00010 2\b\u0010¬\u0001\u001a\u00030\u00ad\u0001\u001a\u0018\u0010®\u0001\u001a\u00020\u0006*\u00020\u00012\t\b\u0002\u0010¯\u0001\u001a\u00020\u000bH\u0002\u001a\r\u0010°\u0001\u001a\u00020\u0006*\u00020\u0001H\u0000\u001a!\u0010±\u0001\u001a\u000f\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0²\u0001*\t\u0012\u0004\u0012\u00020&0³\u0001H\u0000\u001a\u001f\u0010´\u0001\u001a\u00020**\u00020&2\u0007\u0010µ\u0001\u001a\u00020\u00062\t\b\u0002\u0010¶\u0001\u001a\u00020\u0006\u001a\u0018\u0010·\u0001\u001a\u00020\u0001*\u00030¸\u00012\b\u0010¹\u0001\u001a\u00030\u00ad\u0001H\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\t\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\n\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\f\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\r\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000e\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u000f\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0010\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0011\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0012\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0013\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0014\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0015\u001a\u00020\u0001X\u0080T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0016\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0017\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0018\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0019\u001a\u00020\u000bX\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001a\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001b\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001c\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001d\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u000e\u0010\u001e\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000\"\u001a\u0010\u001f\u001a\u00020\u0001*\u0004\u0018\u00010 8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"\"\u001a\u0010#\u001a\u00020\u0001*\u0004\u0018\u00010 8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b$\u0010\"\"\u0017\u0010%\u001a\u00020\u0001*\u0004\u0018\u00010&8F¢\u0006\u0006\u001a\u0004\b'\u0010(\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006º\u0001"}, d2 = {"CAPTURED_FIELD_FOLD_PREFIX", "", "CAPTURED_FIELD_PREFIX", "DEFAULT_LAMBDA_FAKE_CALL", "FIRST_FUN_LABEL", "GENERATE_SMAP", "", "INLINE_CALL_TRANSFORMATION_SUFFIX", "INLINE_FUN_THIS_0_SUFFIX", "INLINE_FUN_VAR_SUFFIX", "INLINE_MARKER_AFTER_FAKE_CONTINUATION_CONSTRUCTOR_CALL", "", "INLINE_MARKER_AFTER_INLINE_SUSPEND_ID", "INLINE_MARKER_AFTER_METHOD_NAME", "INLINE_MARKER_AFTER_SUSPEND_ID", "INLINE_MARKER_AFTER_UNBOX_INLINE_CLASS", "INLINE_MARKER_BEFORE_FAKE_CONTINUATION_CONSTRUCTOR_CALL", "INLINE_MARKER_BEFORE_INLINE_SUSPEND_ID", "INLINE_MARKER_BEFORE_METHOD_NAME", "INLINE_MARKER_BEFORE_SUSPEND_ID", "INLINE_MARKER_BEFORE_UNBOX_INLINE_CLASS", "INLINE_MARKER_CLASS_NAME", "INLINE_MARKER_FAKE_CONTINUATION", "INLINE_MARKER_FINALLY_END", "INLINE_MARKER_FINALLY_START", "INLINE_MARKER_RETURNS_UNIT", "INLINE_TRANSFORMATION_SUFFIX", "NON_CAPTURED_FIELD_PREFIX", "NON_LOCAL_RETURN", "NUMBERED_FUNCTION_PREFIX", "SPECIAL_TRANSFORMATION_NAME", "insnOpcodeText", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "getInsnOpcodeText", "(Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;)Ljava/lang/String;", "insnText", "getInsnText", "nodeText", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "getNodeText", "(Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;)Ljava/lang/String;", "addAfterUnboxInlineClassMarker", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "addBeforeUnboxInlineClassMarker", "addFakeContinuationConstructorCallMarker", "isStartNotEnd", "addFakeContinuationMarker", "addInlineMarker", "addReturnsUnitMarker", "addReturnsUnitMarkerIfNecessary", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "addSuspendMarker", "inlinable", "addUnboxInlineClassMarkersIfNeeded", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "typeMapper", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapper;", "areLabelsBeforeSameInsn", "first", "Lorg/jetbrains/org/objectweb/asm/tree/LabelNode;", "second", "calcMarkerShift", "parameters", "Lorg/jetbrains/kotlin/codegen/inline/Parameters;", "node", "cloneMethodNode", "methodNode", "createEmptyMethodNode", "createFakeContinuationMethodNodeForInline", "findVirtualFile", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "findVirtualFileImprecise", "internalClassName", "firstLabelInChain", "generateFinallyMarker", ConfigConstants.CONFIG_KEY_DEPTH, "start", "generateGlobalReturnFlag", "iv", "labelName", "generateResumePathUnboxing", "inlineClass", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapperBase;", "getConstant", "ins", "getIndexAfterLastMarker", "getInlineName", "codegenContext", "Lorg/jetbrains/kotlin/codegen/context/CodegenContext;", "currentDescriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getLoadStoreArgSize", "opcode", "getMarkedReturnLabelOrNull", "returnInsn", "getMethodNode", "Lorg/jetbrains/kotlin/codegen/inline/SMAPAndMethodNode;", "classData", "", "classType", "Lorg/jetbrains/org/objectweb/asm/Type;", "match", "Lkotlin/Function1;", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "method", "getReturnType", "insertNodeBefore", TypedValues.TransitionType.S_FROM, "to", "beforeNode", "isAfterFakeContinuationConstructorCallMarker", "insn", "isAfterInlineMarker", "isAfterInlineSuspendMarker", "isAfterSuspendMarker", "isAfterUnboxInlineClassMarker", "isAnonymousClass", "internalName", "isAnonymousConstructorCall", "methodName", "isAnonymousSingletonLoad", "fieldName", "isBeforeFakeContinuationConstructorCallMarker", "isBeforeInlineMarker", "isBeforeInlineSuspendMarker", "isBeforeSuspendMarker", "isBeforeUnboxInlineClassMarker", "isCapturedFieldName", "isConstructor", "isFakeContinuationMarker", "isFakeLocalVariableForInline", "name", "isFinallyEnd", "isFinallyMarker", "isFinallyMarkerRequired", "context", "Lorg/jetbrains/kotlin/codegen/context/MethodContext;", "isFinallyStart", "isInlineMarker", "isInvokeOnLambda", "owner", "isMarkedReturn", "returnIns", "isOldSamWrapper", "isReturnOpcode", "isReturnsUnitMarker", "isSamWrapper", "isSamWrapperConstructorCall", "isStoreInstruction", "isSuspendInlineMarker", "isSuspendMarker", "id", "isThis0", "isWhenMappingAccess", "loadClassBytesByInternalName", "newMethodNodeWithCorrectStackSize", "block", "removeFinallyMarkers", "intoNode", "wrapWithMaxLocalCalc", "Lorg/jetbrains/kotlin/codegen/inline/MaxStackFrameSizeAndLocalsCalculator;", "dumpBody", "emitInlineMarker", "insnList", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", "isInteger", "radix", "isNumberedFunctionInternalName", "lineNumberRange", "Lkotlin/Pair;", "", "preprocessSuspendMarkers", "forInline", "keepFakeContinuation", "text", "Lorg/jetbrains/org/objectweb/asm/tree/TryCatchBlockNode;", "insns", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class InlineCodegenUtilsKt {
    public static final String CAPTURED_FIELD_FOLD_PREFIX = "$$$";
    public static final String CAPTURED_FIELD_PREFIX = "$";
    public static final String DEFAULT_LAMBDA_FAKE_CALL = "$$$DEFAULT_LAMBDA_FAKE_CALL$$$";
    public static final String FIRST_FUN_LABEL = "$$$$$ROOT$$$$$";
    public static final boolean GENERATE_SMAP = true;
    public static final String INLINE_CALL_TRANSFORMATION_SUFFIX = "$$inlined";
    public static final String INLINE_FUN_THIS_0_SUFFIX = "$inline_fun";
    public static final String INLINE_FUN_VAR_SUFFIX = "$iv";
    public static final String INLINE_MARKER_CLASS_NAME = "kotlin/jvm/internal/InlineMarker";
    public static final String INLINE_TRANSFORMATION_SUFFIX = "$inlined";
    public static final String NUMBERED_FUNCTION_PREFIX = "kotlin/jvm/functions/Function";
    public static final String SPECIAL_TRANSFORMATION_NAME = "$special";

    public static final int getLoadStoreArgSize(int i) {
        return (i == 22 || i == 24 || i == 55 || i == 57) ? 2 : 1;
    }

    public static final boolean isReturnOpcode(int i) {
        return i >= 172 && i <= 177;
    }

    public static final boolean isStoreInstruction(int i) {
        return i >= 54 && i <= 58;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final SMAPAndMethodNode getMethodNode(byte[] classData, Type classType, final Function1<? super Method, Boolean> match) {
        Intrinsics.checkNotNullParameter(classData, "classData");
        Intrinsics.checkNotNullParameter(classType, "classType");
        Intrinsics.checkNotNullParameter(match, "match");
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef3 = new Ref.ObjectRef();
        new ClassReader(classData).accept(new ClassVisitor() { // from class: org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt.getMethodNode.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(589824);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public void visitSource(String source, String debug) {
                objectRef2.element = source;
                objectRef3.element = debug;
            }

            /* JADX WARN: Type inference failed for: r8v0, types: [T, org.jetbrains.org.objectweb.asm.tree.MethodNode] */
            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public MethodVisitor visitMethod(int access, String name2, String desc, String signature, String[] exceptions) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                if (!match.invoke(new Method(name2, desc)).booleanValue()) {
                    return null;
                }
                MethodNode methodNode = objectRef.element;
                if (methodNode != null) {
                    throw new AssertionError("Can't find proper '" + name2 + "' method for inline: ambiguity between '" + methodNode.f1016name + methodNode.desc + "' and '" + name2 + desc + '\'');
                }
                objectRef.element = new MethodNode(589824, access, name2, desc, signature, exceptions);
                MethodNode methodNode2 = objectRef.element;
                Intrinsics.checkNotNull(methodNode2);
                return methodNode2;
            }
        }, 4);
        MethodNode methodNode = (MethodNode) objectRef.element;
        if (methodNode == null) {
            return null;
        }
        Pair<Integer, Integer> pairLineNumberRange = lineNumberRange(CollectionsKt.listOfNotNull(methodNode));
        int iIntValue = pairLineNumberRange.component1().intValue();
        int iIntValue2 = pairLineNumberRange.component2().intValue();
        String str = (String) objectRef3.element;
        String str2 = (String) objectRef2.element;
        String internalName = classType.getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        return new SMAPAndMethodNode(methodNode, SMAPParser.parseOrCreateDefault(str, str2, internalName, iIntValue, iIntValue2));
    }

    public static final Pair<Integer, Integer> lineNumberRange(Collection<? extends MethodNode> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        Iterator<? extends MethodNode> it = collection.iterator();
        int iMin = Integer.MAX_VALUE;
        int iMax = Integer.MIN_VALUE;
        while (it.hasNext()) {
            InsnList instructions = it.next().instructions;
            Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
            for (AbstractInsnNode abstractInsnNode : UtilKt.asSequence(instructions)) {
                if (abstractInsnNode instanceof LineNumberNode) {
                    LineNumberNode lineNumberNode = (LineNumberNode) abstractInsnNode;
                    iMin = Math.min(iMin, lineNumberNode.line);
                    iMax = Math.max(iMax, lineNumberNode.line);
                }
            }
        }
        return TuplesKt.to(Integer.valueOf(iMin), Integer.valueOf(iMax));
    }

    public static final VirtualFile findVirtualFile(GenerationState state, ClassId classId) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(classId, "classId");
        return VirtualFileFinder.INSTANCE.getInstance(state.getProject(), state.getModule()).findVirtualFileWithHeader(classId);
    }

    public static final VirtualFile findVirtualFileImprecise(GenerationState state, String internalClassName) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        FqName packageFqName = JvmClassName.byInternalName(internalClassName).getPackageFqName();
        Intrinsics.checkNotNullExpressionValue(packageFqName, "getPackageFqName(...)");
        return findVirtualFile(state, new ClassId(packageFqName, Name.identifier(StringsKt.substringAfterLast(internalClassName, "/", internalClassName))));
    }

    public static final String getInlineName(CodegenContext<?> codegenContext, KotlinTypeMapper typeMapper) {
        Intrinsics.checkNotNullParameter(codegenContext, "codegenContext");
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
        DeclarationDescriptor contextDescriptor = codegenContext.getContextDescriptor();
        Intrinsics.checkNotNullExpressionValue(contextDescriptor, "getContextDescriptor(...)");
        return getInlineName(codegenContext, contextDescriptor, typeMapper);
    }

    private static final String getInlineName(CodegenContext<?> codegenContext, DeclarationDescriptor declarationDescriptor, KotlinTypeMapper kotlinTypeMapper) {
        String strAsString;
        ClassDescriptor classDescriptor;
        String fileClassInternalName;
        if (declarationDescriptor instanceof PackageFragmentDescriptor) {
            DeclarationDescriptor contextDescriptor = codegenContext.getContextDescriptor();
            Intrinsics.checkNotNullExpressionValue(contextDescriptor, "getContextDescriptor(...)");
            KtFile containingFile = DescriptorToSourceUtils.getContainingFile(contextDescriptor);
            if (containingFile == null) {
                Type implementationOwnerClassType = CodegenContextUtil.getImplementationOwnerClassType(codegenContext);
                fileClassInternalName = implementationOwnerClassType != null ? implementationOwnerClassType.getInternalName() : null;
            } else {
                fileClassInternalName = JvmFileClassUtil.getFileClassInternalName(containingFile);
            }
            if (fileClassInternalName != null) {
                return fileClassInternalName;
            }
            DeclarationDescriptor contextDescriptor2 = codegenContext.getContextDescriptor();
            StringBuilder sb = new StringBuilder("Couldn't find declaration for ");
            DeclarationDescriptor containingDeclaration = contextDescriptor2.getDeclarationDescriptor();
            Intrinsics.checkNotNull(containingDeclaration);
            throw new RuntimeException(sb.append(containingDeclaration.getShortName()).append('.').append(contextDescriptor2.getShortName()).append("; context: ").append(codegenContext).toString());
        }
        if (declarationDescriptor instanceof ClassifierDescriptor) {
            String internalName = kotlinTypeMapper.mapClass((ClassifierDescriptor) declarationDescriptor).getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
            return internalName;
        }
        if ((declarationDescriptor instanceof FunctionDescriptor) && (classDescriptor = (ClassDescriptor) kotlinTypeMapper.getBindingContext().get(CodegenBinding.CLASS_FOR_CALLABLE, declarationDescriptor)) != null) {
            String internalName2 = kotlinTypeMapper.mapClass(classDescriptor).getInternalName();
            Intrinsics.checkNotNullExpressionValue(internalName2, "getInternalName(...)");
            return internalName2;
        }
        if (declarationDescriptor.getShortName().isSpecial()) {
            strAsString = "";
        } else {
            strAsString = declarationDescriptor.getShortName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        }
        StringBuilder sb2 = new StringBuilder();
        DeclarationDescriptor containingDeclaration2 = declarationDescriptor.getDeclarationDescriptor();
        Intrinsics.checkNotNull(containingDeclaration2);
        return sb2.append(getInlineName(codegenContext, containingDeclaration2, kotlinTypeMapper)).append('$').append(strAsString).toString();
    }

    public static final boolean isInvokeOnLambda(String owner, String name2) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(name2, "name");
        return Intrinsics.areEqual(OperatorNameConventions.INVOKE.asString(), name2) && isNumberedFunctionInternalName(owner);
    }

    public static final boolean isNumberedFunctionInternalName(String str) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        if (!StringsKt.startsWith$default(str, NUMBERED_FUNCTION_PREFIX, false, 2, (Object) null)) {
            return false;
        }
        String strSubstring = str.substring(29);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        return isInteger$default(strSubstring, 0, 1, null);
    }

    public static final boolean isAnonymousConstructorCall(String internalName, String methodName) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        Intrinsics.checkNotNullParameter(methodName, "methodName");
        return isConstructor(methodName) && isAnonymousClass(internalName);
    }

    private static final boolean isConstructor(String str) {
        return Intrinsics.areEqual("<init>", str);
    }

    public static final boolean isWhenMappingAccess(String internalName, String fieldName) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        return StringsKt.startsWith$default(fieldName, WhenByEnumsMapping.MAPPING_ARRAY_FIELD_PREFIX, false, 2, (Object) null) && StringsKt.endsWith$default(internalName, WhenByEnumsMapping.MAPPINGS_CLASS_NAME_POSTFIX, false, 2, (Object) null);
    }

    public static final boolean isAnonymousSingletonLoad(String internalName, String fieldName) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        return Intrinsics.areEqual(JvmAbi.INSTANCE_FIELD, fieldName) && isAnonymousClass(internalName);
    }

    private static final boolean isOldSamWrapper(String str) {
        if (!StringsKt.contains$default((CharSequence) str, (CharSequence) "$sam$", false, 2, (Object) null)) {
            return false;
        }
        String strSubstringAfter = StringsKt.substringAfter(str, "$i$", "");
        return strSubstringAfter.length() == 8 && StringsKt.toLongOrNull(strSubstringAfter, 16) != null;
    }

    public static final boolean isSamWrapper(String internalName) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        return (StringsKt.endsWith$default(internalName, SamWrapperCodegen.SAM_WRAPPER_SUFFIX, false, 2, (Object) null) && StringsKt.contains$default((CharSequence) internalName, (CharSequence) "$sam$i$", false, 2, (Object) null)) || isOldSamWrapper(internalName);
    }

    public static final boolean isSamWrapperConstructorCall(String internalName, String methodName) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        Intrinsics.checkNotNullParameter(methodName, "methodName");
        return isConstructor(methodName) && isSamWrapper(internalName);
    }

    public static final boolean isAnonymousClass(String internalName) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        return !StringsKt.contains$default((CharSequence) internalName, (CharSequence) "$sam$", false, 2, (Object) null) && isInteger$default(StringsKt.substringAfterLast(StringsKt.substringAfterLast$default(internalName, '/', (String) null, 2, (Object) null), "$", ""), 0, 1, null);
    }

    public static final MaxStackFrameSizeAndLocalsCalculator wrapWithMaxLocalCalc(MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        return new MaxStackFrameSizeAndLocalsCalculator(589824, methodNode.access, methodNode.desc, methodNode);
    }

    public static final MethodNode newMethodNodeWithCorrectStackSize(Function1<? super InstructionAdapter, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        MethodNode methodNode = new MethodNode(589824, "fake", "()V", null, null);
        MaxStackFrameSizeAndLocalsCalculator maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc = wrapWithMaxLocalCalc(methodNode);
        block.invoke(new InstructionAdapter(maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc));
        maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc.visitInsn(177);
        maxStackFrameSizeAndLocalsCalculatorWrapWithMaxLocalCalc.visitMaxs(-1, -1);
        InsnList insnList = methodNode.instructions;
        insnList.remove(insnList.getLast());
        return methodNode;
    }

    private static final boolean isInteger(String str, int i) {
        return StringsKt.toIntOrNull(str, i) != null;
    }

    static /* synthetic */ boolean isInteger$default(String str, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 10;
        }
        return isInteger(str, i);
    }

    public static final boolean isCapturedFieldName(String fieldName) {
        Intrinsics.checkNotNullParameter(fieldName, "fieldName");
        return !(!StringsKt.startsWith$default(fieldName, "$", false, 2, (Object) null) || StringsKt.startsWith$default(fieldName, "$$", false, 2, (Object) null) || Intrinsics.areEqual(fieldName, AssertCodegenUtilKt.ASSERTIONS_DISABLED_FIELD_NAME)) || Intrinsics.areEqual(AsmUtil.CAPTURED_THIS_FIELD, fieldName) || Intrinsics.areEqual(AsmUtil.CAPTURED_RECEIVER_FIELD, fieldName);
    }

    public static final boolean isMarkedReturn(AbstractInsnNode returnIns) {
        Intrinsics.checkNotNullParameter(returnIns, "returnIns");
        return getMarkedReturnLabelOrNull(returnIns) != null;
    }

    public static final String getMarkedReturnLabelOrNull(AbstractInsnNode returnInsn) {
        Intrinsics.checkNotNullParameter(returnInsn, "returnInsn");
        if (!isReturnOpcode(returnInsn.getOpcode())) {
            return null;
        }
        AbstractInsnNode previous = returnInsn.getPrevious();
        if (previous instanceof MethodInsnNode) {
            MethodInsnNode methodInsnNode = (MethodInsnNode) previous;
            if (Intrinsics.areEqual("$$$$$NON_LOCAL_RETURN$$$$$", methodInsnNode.owner)) {
                return methodInsnNode.f1015name;
            }
        }
        return null;
    }

    public static final void generateGlobalReturnFlag(InstructionAdapter iv, String labelName) {
        Intrinsics.checkNotNullParameter(iv, "iv");
        Intrinsics.checkNotNullParameter(labelName, "labelName");
        iv.invokestatic("$$$$$NON_LOCAL_RETURN$$$$$", labelName, "()V", false);
    }

    public static final Type getReturnType(int i) {
        switch (i) {
            case 172:
                Type INT_TYPE = Type.INT_TYPE;
                Intrinsics.checkNotNullExpressionValue(INT_TYPE, "INT_TYPE");
                return INT_TYPE;
            case 173:
                Type LONG_TYPE = Type.LONG_TYPE;
                Intrinsics.checkNotNullExpressionValue(LONG_TYPE, "LONG_TYPE");
                return LONG_TYPE;
            case 174:
                Type FLOAT_TYPE = Type.FLOAT_TYPE;
                Intrinsics.checkNotNullExpressionValue(FLOAT_TYPE, "FLOAT_TYPE");
                return FLOAT_TYPE;
            case 175:
                Type DOUBLE_TYPE = Type.DOUBLE_TYPE;
                Intrinsics.checkNotNullExpressionValue(DOUBLE_TYPE, "DOUBLE_TYPE");
                return DOUBLE_TYPE;
            case 176:
            default:
                Type OBJECT_TYPE = AsmTypes.OBJECT_TYPE;
                Intrinsics.checkNotNullExpressionValue(OBJECT_TYPE, "OBJECT_TYPE");
                return OBJECT_TYPE;
            case 177:
                Type VOID_TYPE = Type.VOID_TYPE;
                Intrinsics.checkNotNullExpressionValue(VOID_TYPE, "VOID_TYPE");
                return VOID_TYPE;
        }
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [java.lang.Object, java.util.ListIterator] */
    public static final void insertNodeBefore(MethodNode from, MethodNode to, AbstractInsnNode beforeNode) {
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(to, "to");
        Intrinsics.checkNotNullParameter(beforeNode, "beforeNode");
        ?? it = from.instructions.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            to.instructions.insertBefore(beforeNode, (AbstractInsnNode) it.next());
        }
    }

    public static final MethodNode createEmptyMethodNode() {
        return new MethodNode(589824, 0, "fake", "()V", null, null);
    }

    public static final MethodNode createFakeContinuationMethodNodeForInline() {
        MethodNode methodNodeCreateEmptyMethodNode = createEmptyMethodNode();
        addFakeContinuationMarker(new InstructionAdapter(methodNodeCreateEmptyMethodNode));
        return methodNodeCreateEmptyMethodNode;
    }

    public static final LabelNode firstLabelInChain(LabelNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        while (node.getPrevious() instanceof LabelNode) {
            AbstractInsnNode previous = node.getPrevious();
            Intrinsics.checkNotNull(previous, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LabelNode");
            node = (LabelNode) previous;
        }
        return node;
    }

    public static final boolean areLabelsBeforeSameInsn(LabelNode first, LabelNode second) {
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        return Intrinsics.areEqual(firstLabelInChain(first), firstLabelInChain(second));
    }

    public static final String getNodeText(MethodNode methodNode) {
        if (methodNode == null) {
            return "Not generated";
        }
        Textifier textifier = new Textifier();
        methodNode.accept(new TraceMethodVisitor(textifier));
        StringWriter stringWriter = new StringWriter();
        textifier.print(new PrintWriter(stringWriter));
        stringWriter.flush();
        return methodNode.f1016name + StringUtil.SPACE + methodNode.desc + ":\n" + ((Object) stringWriter.getBuffer());
    }

    public static final String getInsnText(AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode == null) {
            return "<null>";
        }
        Textifier textifier = new Textifier();
        abstractInsnNode.accept(new TraceMethodVisitor(textifier));
        StringWriter stringWriter = new StringWriter();
        textifier.print(new PrintWriter(stringWriter));
        stringWriter.flush();
        String string = stringWriter.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return StringsKt.trim((CharSequence) string).toString();
    }

    public static final String insnText(AbstractInsnNode abstractInsnNode, final InsnList insnList) {
        Intrinsics.checkNotNullParameter(insnList, "insnList");
        if (abstractInsnNode == null) {
            return "<null>";
        }
        if (abstractInsnNode instanceof LabelNode) {
            return insnText$labelText((LabelNode) abstractInsnNode, insnList);
        }
        if (abstractInsnNode instanceof JumpInsnNode) {
            StringBuilder sbAppend = new StringBuilder().append(getInsnOpcodeText(abstractInsnNode)).append(StringUtil.SPACE);
            LabelNode label = ((JumpInsnNode) abstractInsnNode).label;
            Intrinsics.checkNotNullExpressionValue(label, "label");
            return sbAppend.append(insnText$labelText(label, insnList)).toString();
        }
        if (abstractInsnNode instanceof LookupSwitchInsnNode) {
            StringBuilder sbAppend2 = new StringBuilder().append(getInsnOpcodeText(abstractInsnNode)).append(StringUtil.SPACE);
            LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode) abstractInsnNode;
            List<Integer> keys = lookupSwitchInsnNode.keys;
            Intrinsics.checkNotNullExpressionValue(keys, "keys");
            List<LabelNode> labels = lookupSwitchInsnNode.labels;
            Intrinsics.checkNotNullExpressionValue(labels, "labels");
            return sbAppend2.append(CollectionsKt.joinToString$default(CollectionsKt.zip(keys, labels), null, "[", "]", 0, null, new Function1<Pair<? extends Integer, ? extends LabelNode>, CharSequence>() { // from class: org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt.insnText.1
                {
                    super(1);
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final CharSequence invoke2(Pair<Integer, ? extends LabelNode> pair) {
                    Intrinsics.checkNotNullParameter(pair, "<name for destructuring parameter 0>");
                    Integer numComponent1 = pair.component1();
                    LabelNode labelNodeComponent2 = pair.component2();
                    StringBuilder sbAppend3 = new StringBuilder().append(numComponent1).append(':');
                    Intrinsics.checkNotNull(labelNodeComponent2);
                    return sbAppend3.append(InlineCodegenUtilsKt.insnText$labelText(labelNodeComponent2, insnList)).toString();
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ CharSequence invoke(Pair<? extends Integer, ? extends LabelNode> pair) {
                    return invoke2((Pair<Integer, ? extends LabelNode>) pair);
                }
            }, 25, null)).toString();
        }
        if (abstractInsnNode instanceof TableSwitchInsnNode) {
            StringBuilder sbAppend3 = new StringBuilder().append(getInsnOpcodeText(abstractInsnNode)).append(StringUtil.SPACE);
            TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode) abstractInsnNode;
            IntRange intRange = new IntRange(tableSwitchInsnNode.min, tableSwitchInsnNode.max);
            List<LabelNode> labels2 = tableSwitchInsnNode.labels;
            Intrinsics.checkNotNullExpressionValue(labels2, "labels");
            return sbAppend3.append(CollectionsKt.joinToString$default(CollectionsKt.zip(intRange, labels2), null, "[", "]", 0, null, new Function1<Pair<? extends Integer, ? extends LabelNode>, CharSequence>() { // from class: org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt.insnText.2
                {
                    super(1);
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final CharSequence invoke2(Pair<Integer, ? extends LabelNode> pair) {
                    Intrinsics.checkNotNullParameter(pair, "<name for destructuring parameter 0>");
                    int iIntValue = pair.component1().intValue();
                    LabelNode labelNodeComponent2 = pair.component2();
                    StringBuilder sbAppend4 = new StringBuilder().append(iIntValue).append(':');
                    Intrinsics.checkNotNull(labelNodeComponent2);
                    return sbAppend4.append(InlineCodegenUtilsKt.insnText$labelText(labelNodeComponent2, insnList)).toString();
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ CharSequence invoke(Pair<? extends Integer, ? extends LabelNode> pair) {
                    return invoke2((Pair<Integer, ? extends LabelNode>) pair);
                }
            }, 25, null)).toString();
        }
        return getInsnText(abstractInsnNode);
    }

    private static final int insnText$indexOf(AbstractInsnNode abstractInsnNode, InsnList insnList) {
        return insnList.indexOf(abstractInsnNode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String insnText$labelText(LabelNode labelNode, InsnList insnList) {
        return "L#" + insnText$indexOf(labelNode, insnList);
    }

    public static final String dumpBody(MethodNode methodNode) {
        AbstractInsnNode[] abstractInsnNodeArr;
        String str;
        Intrinsics.checkNotNullParameter(methodNode, "<this>");
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        StringBuilder sbAppend = new StringBuilder().append(methodNode.f1016name);
        char c = StringUtil.SPACE;
        printWriter.println(sbAppend.append(StringUtil.SPACE).append(methodNode.desc).toString());
        for (TryCatchBlockNode tryCatchBlockNode : methodNode.tryCatchBlocks) {
            StringBuilder sb = new StringBuilder("  TRYCATCHBLOCK start:");
            LabelNode start = tryCatchBlockNode.start;
            Intrinsics.checkNotNullExpressionValue(start, "start");
            StringBuilder sbAppend2 = sb.append(dumpBody$labelRef(start, methodNode)).append(" end:");
            LabelNode end = tryCatchBlockNode.end;
            Intrinsics.checkNotNullExpressionValue(end, "end");
            StringBuilder sbAppend3 = sbAppend2.append(dumpBody$labelRef(end, methodNode)).append(" handler:");
            LabelNode handler = tryCatchBlockNode.handler;
            Intrinsics.checkNotNullExpressionValue(handler, "handler");
            printWriter.println(sbAppend3.append(dumpBody$labelRef(handler, methodNode)).toString());
        }
        AbstractInsnNode[] array = methodNode.instructions.toArray();
        Intrinsics.checkNotNullExpressionValue(array, "toArray(...)");
        int length = array.length;
        int i = 0;
        while (i < length) {
            AbstractInsnNode abstractInsnNode = array[i];
            switch (abstractInsnNode.getType()) {
                case 0:
                    abstractInsnNodeArr = array;
                    printWriter.println(i + '\t' + Printer.OPCODES[abstractInsnNode.getOpcode()]);
                    continue;
                    i++;
                    array = abstractInsnNodeArr;
                    c = StringUtil.SPACE;
                    break;
                case 1:
                    abstractInsnNodeArr = array;
                    StringBuilder sbAppend4 = new StringBuilder().append(i).append('\t').append(Printer.OPCODES[abstractInsnNode.getOpcode()]).append(StringUtil.SPACE);
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.IntInsnNode");
                    printWriter.println(sbAppend4.append(((IntInsnNode) abstractInsnNode).operand).toString());
                    continue;
                    i++;
                    array = abstractInsnNodeArr;
                    c = StringUtil.SPACE;
                    break;
                case 2:
                    abstractInsnNodeArr = array;
                    StringBuilder sbAppend5 = new StringBuilder().append(i).append('\t').append(Printer.OPCODES[abstractInsnNode.getOpcode()]).append(StringUtil.SPACE);
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.VarInsnNode");
                    printWriter.println(sbAppend5.append(((VarInsnNode) abstractInsnNode).var).toString());
                    continue;
                    i++;
                    array = abstractInsnNodeArr;
                    c = StringUtil.SPACE;
                    break;
                case 3:
                    abstractInsnNodeArr = array;
                    StringBuilder sbAppend6 = new StringBuilder().append(i).append('\t').append(Printer.OPCODES[abstractInsnNode.getOpcode()]).append(StringUtil.SPACE);
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.TypeInsnNode");
                    printWriter.println(sbAppend6.append(((TypeInsnNode) abstractInsnNode).desc).toString());
                    continue;
                    i++;
                    array = abstractInsnNodeArr;
                    c = StringUtil.SPACE;
                    break;
                case 4:
                    abstractInsnNodeArr = array;
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.FieldInsnNode");
                    FieldInsnNode fieldInsnNode = (FieldInsnNode) abstractInsnNode;
                    printWriter.println(i + '\t' + Printer.OPCODES[fieldInsnNode.getOpcode()] + StringUtil.SPACE + fieldInsnNode.owner + '#' + fieldInsnNode.f1010name + StringUtil.SPACE + fieldInsnNode.desc);
                    continue;
                    i++;
                    array = abstractInsnNodeArr;
                    c = StringUtil.SPACE;
                    break;
                case 5:
                    abstractInsnNodeArr = array;
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.MethodInsnNode");
                    MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
                    printWriter.println(i + '\t' + Printer.OPCODES[methodInsnNode.getOpcode()] + StringUtil.SPACE + methodInsnNode.owner + '#' + methodInsnNode.f1015name + StringUtil.SPACE + methodInsnNode.desc);
                    continue;
                    i++;
                    array = abstractInsnNodeArr;
                    c = StringUtil.SPACE;
                    break;
                case 6:
                    abstractInsnNodeArr = array;
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.InvokeDynamicInsnNode");
                    InvokeDynamicInsnNode invokeDynamicInsnNode = (InvokeDynamicInsnNode) abstractInsnNode;
                    printWriter.println(i + '\t' + Printer.OPCODES[invokeDynamicInsnNode.getOpcode()] + StringUtil.SPACE + invokeDynamicInsnNode.f1013name + StringUtil.SPACE + invokeDynamicInsnNode.desc);
                    int tag = invokeDynamicInsnNode.bsm.getTag();
                    switch (tag) {
                        case 1:
                            str = "H_GETFIELD";
                            break;
                        case 2:
                            str = "H_GETSTATIC";
                            break;
                        case 3:
                            str = "H_PUTFIELD";
                            break;
                        case 4:
                            str = "H_PUTSTATIC";
                            break;
                        case 5:
                            str = "H_INVOKEVIRTUAL";
                            break;
                        case 6:
                            str = "H_INVOKESTATIC";
                            break;
                        case 7:
                            str = "H_INVOKESPECIAL";
                            break;
                        case 8:
                            str = "H_NEWINVOKESPECIAL";
                            break;
                        case 9:
                            str = "H_INVOKEINTERFACE";
                            break;
                        default:
                            str = "<" + tag + '>';
                            break;
                    }
                    printWriter.println("\t" + str + StringUtil.SPACE + invokeDynamicInsnNode.bsm.getOwner() + '#' + invokeDynamicInsnNode.bsm.getName() + StringUtil.SPACE + invokeDynamicInsnNode.bsm.getDesc() + " [");
                    Object[] bsmArgs = invokeDynamicInsnNode.bsmArgs;
                    Intrinsics.checkNotNullExpressionValue(bsmArgs, "bsmArgs");
                    for (Object obj : bsmArgs) {
                        printWriter.println("\t" + obj);
                    }
                    printWriter.println("\t]");
                    continue;
                    i++;
                    array = abstractInsnNodeArr;
                    c = StringUtil.SPACE;
                    break;
                case 7:
                    abstractInsnNodeArr = array;
                    StringBuilder sbAppend7 = new StringBuilder().append(i).append('\t').append(Printer.OPCODES[abstractInsnNode.getOpcode()]).append(StringUtil.SPACE);
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.JumpInsnNode");
                    LabelNode label = ((JumpInsnNode) abstractInsnNode).label;
                    Intrinsics.checkNotNullExpressionValue(label, "label");
                    printWriter.println(sbAppend7.append(dumpBody$labelRef(label, methodNode)).toString());
                    continue;
                    i++;
                    array = abstractInsnNodeArr;
                    c = StringUtil.SPACE;
                    break;
                case 8:
                    abstractInsnNodeArr = array;
                    printWriter.println(i + "\tL#" + i);
                    continue;
                    i++;
                    array = abstractInsnNodeArr;
                    c = StringUtil.SPACE;
                    break;
                case 9:
                    abstractInsnNodeArr = array;
                    StringBuilder sbAppend8 = new StringBuilder().append(i).append("\tLDC ");
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LdcInsnNode");
                    printWriter.println(sbAppend8.append(((LdcInsnNode) abstractInsnNode).cst).toString());
                    continue;
                    i++;
                    array = abstractInsnNodeArr;
                    c = StringUtil.SPACE;
                    break;
                case 10:
                    abstractInsnNodeArr = array;
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.IincInsnNode");
                    IincInsnNode iincInsnNode = (IincInsnNode) abstractInsnNode;
                    printWriter.println(i + "\tIINC " + iincInsnNode.var + " incr:" + iincInsnNode.incr);
                    continue;
                    i++;
                    array = abstractInsnNodeArr;
                    c = StringUtil.SPACE;
                    break;
                case 11:
                    abstractInsnNodeArr = array;
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.TableSwitchInsnNode");
                    TableSwitchInsnNode tableSwitchInsnNode = (TableSwitchInsnNode) abstractInsnNode;
                    printWriter.println(i + "\tTABLESWITCH min:" + tableSwitchInsnNode.min + " max:" + tableSwitchInsnNode.max + '{');
                    int size = tableSwitchInsnNode.labels.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        StringBuilder sbAppend9 = new StringBuilder("\t").append(tableSwitchInsnNode.min + i2).append(": ");
                        LabelNode labelNode = tableSwitchInsnNode.labels.get(i2);
                        Intrinsics.checkNotNullExpressionValue(labelNode, "get(...)");
                        printWriter.println(sbAppend9.append(dumpBody$labelRef(labelNode, methodNode)).toString());
                    }
                    StringBuilder sb2 = new StringBuilder("\tdefault: ");
                    LabelNode dflt = tableSwitchInsnNode.dflt;
                    Intrinsics.checkNotNullExpressionValue(dflt, "dflt");
                    printWriter.println(sb2.append(dumpBody$labelRef(dflt, methodNode)).toString());
                    printWriter.println("\t}");
                    continue;
                    i++;
                    array = abstractInsnNodeArr;
                    c = StringUtil.SPACE;
                    break;
                case 12:
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LookupSwitchInsnNode");
                    LookupSwitchInsnNode lookupSwitchInsnNode = (LookupSwitchInsnNode) abstractInsnNode;
                    printWriter.println(i + "\tLOOKUPSWITCH {");
                    int size2 = lookupSwitchInsnNode.labels.size();
                    int i3 = 0;
                    while (i3 < size2) {
                        Integer num = lookupSwitchInsnNode.keys.get(i3);
                        LabelNode labelNode2 = lookupSwitchInsnNode.labels.get(i3);
                        AbstractInsnNode[] abstractInsnNodeArr2 = array;
                        StringBuilder sbAppend10 = new StringBuilder("\t").append(num).append(": ");
                        Intrinsics.checkNotNull(labelNode2);
                        printWriter.println(sbAppend10.append(dumpBody$labelRef(labelNode2, methodNode)).toString());
                        i3++;
                        array = abstractInsnNodeArr2;
                    }
                    abstractInsnNodeArr = array;
                    printWriter.println("\t}");
                    continue;
                    i++;
                    array = abstractInsnNodeArr;
                    c = StringUtil.SPACE;
                    break;
                case 13:
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.MultiANewArrayInsnNode");
                    MultiANewArrayInsnNode multiANewArrayInsnNode = (MultiANewArrayInsnNode) abstractInsnNode;
                    printWriter.println(i + '\t' + Printer.OPCODES[multiANewArrayInsnNode.getOpcode()] + c + multiANewArrayInsnNode.desc + " dims:" + multiANewArrayInsnNode.dims + c);
                    break;
                case 14:
                    printWriter.println(i + "\tFRAME {...}");
                    break;
                case 15:
                    Intrinsics.checkNotNull(abstractInsnNode, "null cannot be cast to non-null type org.jetbrains.org.objectweb.asm.tree.LineNumberNode");
                    LineNumberNode lineNumberNode = (LineNumberNode) abstractInsnNode;
                    StringBuilder sbAppend11 = new StringBuilder().append(i).append("\tLINENUMBER ").append(lineNumberNode.line).append(c);
                    LabelNode start2 = lineNumberNode.start;
                    Intrinsics.checkNotNullExpressionValue(start2, "start");
                    printWriter.println(sbAppend11.append(dumpBody$labelRef(start2, methodNode)).toString());
                    break;
                default:
                    abstractInsnNodeArr = array;
                    printWriter.println(i + "\t??? " + abstractInsnNode);
                    continue;
                    i++;
                    array = abstractInsnNodeArr;
                    c = StringUtil.SPACE;
                    break;
            }
            abstractInsnNodeArr = array;
            i++;
            array = abstractInsnNodeArr;
            c = StringUtil.SPACE;
        }
        for (LocalVariableNode localVariableNode : methodNode.localVariables) {
            StringBuilder sbAppend12 = new StringBuilder("  LOCALVARIABLE ").append(localVariableNode.index).append(StringUtil.SPACE).append(localVariableNode.f1014name).append(StringUtil.SPACE).append(localVariableNode.desc).append(StringUtil.SPACE);
            LabelNode start3 = localVariableNode.start;
            Intrinsics.checkNotNullExpressionValue(start3, "start");
            StringBuilder sbAppend13 = sbAppend12.append(dumpBody$labelRef(start3, methodNode)).append(StringUtil.SPACE);
            LabelNode end2 = localVariableNode.end;
            Intrinsics.checkNotNullExpressionValue(end2, "end");
            printWriter.println(sbAppend13.append(dumpBody$labelRef(end2, methodNode)).toString());
            if (localVariableNode.signature != null) {
                printWriter.println("    // signature: " + localVariableNode.signature);
            }
        }
        printWriter.flush();
        String string = stringWriter.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private static final String dumpBody$labelRef(LabelNode labelNode, MethodNode methodNode) {
        return "L#" + methodNode.instructions.indexOf(labelNode);
    }

    public static final String getInsnOpcodeText(AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode == null) {
            return "null";
        }
        if (abstractInsnNode instanceof LabelNode) {
            return "LABEL";
        }
        if (abstractInsnNode instanceof LineNumberNode) {
            return "LINENUMBER";
        }
        if (abstractInsnNode instanceof FrameNode) {
            return "FRAME";
        }
        String str = Printer.OPCODES[abstractInsnNode.getOpcode()];
        Intrinsics.checkNotNullExpressionValue(str, "get(...)");
        return str;
    }

    public static final String text(TryCatchBlockNode tryCatchBlockNode, InsnList insns) {
        Intrinsics.checkNotNullParameter(tryCatchBlockNode, "<this>");
        Intrinsics.checkNotNullParameter(insns, "insns");
        return "[" + insns.indexOf(tryCatchBlockNode.start) + " .. " + insns.indexOf(tryCatchBlockNode.end) + " -> " + insns.indexOf(tryCatchBlockNode.handler) + ']';
    }

    public static final byte[] loadClassBytesByInternalName(GenerationState state, String internalName) throws IOException {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        OutputFile outputFile = state.getFactory().get(internalName + ".class");
        if (outputFile != null) {
            return outputFile.getContent();
        }
        VirtualFile virtualFileFindVirtualFileImprecise = findVirtualFileImprecise(state, internalName);
        if (virtualFileFindVirtualFileImprecise == null) {
            throw new RuntimeException("Couldn't find virtual file for " + internalName);
        }
        byte[] bArrContentsToByteArray = virtualFileFindVirtualFileImprecise.contentsToByteArray();
        Intrinsics.checkNotNullExpressionValue(bArrContentsToByteArray, "contentsToByteArray(...)");
        return bArrContentsToByteArray;
    }

    public static final void generateFinallyMarker(InstructionAdapter v, int i, boolean z) {
        Intrinsics.checkNotNullParameter(v, "v");
        v.iconst(i);
        v.invokestatic(INLINE_MARKER_CLASS_NAME, z ? "finallyStart" : "finallyEnd", "(I)V", false);
    }

    public static final boolean isFinallyEnd(AbstractInsnNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        return isFinallyMarker(node, "finallyEnd");
    }

    public static final boolean isFinallyStart(AbstractInsnNode node) {
        Intrinsics.checkNotNullParameter(node, "node");
        return isFinallyMarker(node, "finallyStart");
    }

    public static final boolean isFinallyMarker(AbstractInsnNode abstractInsnNode) {
        return abstractInsnNode != null && (isFinallyStart(abstractInsnNode) || isFinallyEnd(abstractInsnNode));
    }

    private static final boolean isFinallyMarker(AbstractInsnNode abstractInsnNode, String str) {
        if (!(abstractInsnNode instanceof MethodInsnNode)) {
            return false;
        }
        MethodInsnNode methodInsnNode = (MethodInsnNode) abstractInsnNode;
        return Intrinsics.areEqual(INLINE_MARKER_CLASS_NAME, methodInsnNode.owner) && Intrinsics.areEqual(str, methodInsnNode.f1015name);
    }

    public static final boolean isFinallyMarkerRequired(MethodContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.isInlineMethodContext() || (context instanceof InlineLambdaContext);
    }

    public static final int getConstant(AbstractInsnNode ins) {
        Intrinsics.checkNotNullParameter(ins, "ins");
        int opcode = ins.getOpcode();
        if (3 <= opcode && opcode < 9) {
            return opcode - 3;
        }
        if (opcode == 16 || opcode == 17) {
            return ((IntInsnNode) ins).operand;
        }
        Object obj = ((LdcInsnNode) ins).cst;
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Int");
        return ((Integer) obj).intValue();
    }

    public static final void removeFinallyMarkers(MethodNode intoNode) {
        Intrinsics.checkNotNullParameter(intoNode, "intoNode");
        InsnList insnList = intoNode.instructions;
        AbstractInsnNode first = insnList.getFirst();
        while (first != null) {
            if (isFinallyMarker(first)) {
                AbstractInsnNode previous = first.getPrevious();
                Intrinsics.checkNotNullExpressionValue(previous, "getPrevious(...)");
                getConstant(previous);
                AbstractInsnNode next = first.getNext();
                insnList.remove(first.getPrevious());
                insnList.remove(first);
                first = next;
            } else {
                first = first.getNext();
            }
        }
    }

    public static final void addInlineMarker(InstructionAdapter v, boolean z) {
        Intrinsics.checkNotNullParameter(v, "v");
        v.visitMethodInsn(184, INLINE_MARKER_CLASS_NAME, z ? "beforeInlineCall" : "afterInlineCall", "()V", false);
    }

    public static final void addUnboxInlineClassMarkersIfNeeded(InstructionAdapter v, CallableDescriptor descriptor, KotlinTypeMapper typeMapper) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
        FunctionDescriptor functionDescriptor = descriptor instanceof FunctionDescriptor ? (FunctionDescriptor) descriptor : null;
        KotlinType kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass = functionDescriptor != null ? CoroutineCodegenUtilKt.originalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass(functionDescriptor, typeMapper) : null;
        if (kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass != null) {
            generateResumePathUnboxing(v, kotlinTypeOriginalReturnTypeOfSuspendFunctionReturningUnboxedInlineClass, typeMapper);
        }
    }

    public static final void generateResumePathUnboxing(InstructionAdapter v, KotlinTypeMarker inlineClass, KotlinTypeMapperBase typeMapper) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(inlineClass, "inlineClass");
        Intrinsics.checkNotNullParameter(typeMapper, "typeMapper");
        addBeforeUnboxInlineClassMarker(v);
        StackValue.unboxInlineClass(AsmTypes.OBJECT_TYPE, inlineClass, v, typeMapper);
        v.checkcast(AsmTypes.OBJECT_TYPE);
        addAfterUnboxInlineClassMarker(v);
    }

    private static final void addBeforeUnboxInlineClassMarker(InstructionAdapter instructionAdapter) {
        emitInlineMarker(instructionAdapter, 8);
    }

    private static final void addAfterUnboxInlineClassMarker(InstructionAdapter instructionAdapter) {
        emitInlineMarker(instructionAdapter, 9);
    }

    public static final void addReturnsUnitMarkerIfNecessary(InstructionAdapter v, ResolvedCall<?> resolvedCall) {
        KotlinType returnType;
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        CallableDescriptor candidateDescriptor = resolvedCall.get$suspendPropertyDescriptor();
        FunctionDescriptor functionDescriptor = candidateDescriptor instanceof FunctionDescriptor ? (FunctionDescriptor) candidateDescriptor : null;
        if (functionDescriptor == null) {
            return;
        }
        FunctionDescriptor functionDescriptor2 = (FunctionDescriptor) CoroutineCodegenUtilKt.unwrapInitialDescriptorForSuspendFunction(functionDescriptor);
        List<TypeParameterDescriptor> typeParameters = functionDescriptor2.getTypeParameters();
        Intrinsics.checkNotNullExpressionValue(typeParameters, "getTypeParameters(...)");
        Iterable<IndexedValue> iterableWithIndex = CollectionsKt.withIndex(typeParameters);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(iterableWithIndex, 10)), 16));
        for (IndexedValue indexedValue : iterableWithIndex) {
            TypeConstructor typeConstructor = ((TypeParameterDescriptor) indexedValue.getValue()).getTypeConstructor();
            KotlinType kotlinType = resolvedCall.getTypeArguments().get(functionDescriptor.getTypeParameters().get(indexedValue.getIndex()));
            if (kotlinType == null) {
                return;
            } else {
                linkedHashMap.put(typeConstructor, new TypeProjectionImpl(kotlinType));
            }
        }
        TypeSubstitutor typeSubstitutorCreate = TypeSubstitutor.create(linkedHashMap);
        Intrinsics.checkNotNullExpressionValue(typeSubstitutorCreate, "create(...)");
        FunctionDescriptor functionDescriptorSubstitute = functionDescriptor2.substitute(typeSubstitutorCreate);
        if (functionDescriptorSubstitute == null || (returnType = functionDescriptorSubstitute.getReturnType()) == null || !KotlinBuiltIns.isUnit(returnType)) {
            return;
        }
        addReturnsUnitMarker(v);
    }

    public static final void addReturnsUnitMarker(InstructionAdapter v) {
        Intrinsics.checkNotNullParameter(v, "v");
        emitInlineMarker(v, 2);
    }

    public static /* synthetic */ void addSuspendMarker$default(InstructionAdapter instructionAdapter, boolean z, boolean z2, int i, Object obj) {
        if ((i & 4) != 0) {
            z2 = false;
        }
        addSuspendMarker(instructionAdapter, z, z2);
    }

    public static final void addSuspendMarker(InstructionAdapter v, boolean z, boolean z2) {
        int i;
        Intrinsics.checkNotNullParameter(v, "v");
        if (z2 && z) {
            i = 6;
        } else if (z2) {
            i = 7;
        } else {
            i = z ? 0 : 1;
        }
        emitInlineMarker(v, i);
    }

    public static final void addFakeContinuationConstructorCallMarker(InstructionAdapter v, boolean z) {
        Intrinsics.checkNotNullParameter(v, "v");
        emitInlineMarker(v, z ? 4 : 5);
    }

    public static final void addFakeContinuationMarker(InstructionAdapter v) {
        Intrinsics.checkNotNullParameter(v, "v");
        emitInlineMarker(v, 3);
        v.aconst(null);
    }

    private static final void emitInlineMarker(InstructionAdapter instructionAdapter, int i) {
        instructionAdapter.iconst(i);
        instructionAdapter.invokestatic(INLINE_MARKER_CLASS_NAME, "mark", "(I)V", false);
    }

    public static final boolean isBeforeSuspendMarker(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return isSuspendMarker(insn, 0);
    }

    public static final boolean isAfterSuspendMarker(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return isSuspendMarker(insn, 1);
    }

    public static final boolean isBeforeInlineSuspendMarker(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return isSuspendMarker(insn, 6);
    }

    public static final boolean isAfterInlineSuspendMarker(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return isSuspendMarker(insn, 7);
    }

    public static final boolean isReturnsUnitMarker(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return isSuspendMarker(insn, 2);
    }

    public static final boolean isFakeContinuationMarker(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        if (insn.getPrevious() != null) {
            AbstractInsnNode previous = insn.getPrevious();
            Intrinsics.checkNotNullExpressionValue(previous, "getPrevious(...)");
            if (isSuspendMarker(previous, 3) && insn.getOpcode() == 1) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isBeforeUnboxInlineClassMarker(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return isSuspendMarker(insn, 8);
    }

    public static final boolean isAfterUnboxInlineClassMarker(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return isSuspendMarker(insn, 9);
    }

    public static final boolean isBeforeFakeContinuationConstructorCallMarker(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return isSuspendMarker(insn, 4);
    }

    public static final boolean isAfterFakeContinuationConstructorCallMarker(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return isSuspendMarker(insn, 5);
    }

    public static final boolean isSuspendInlineMarker(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return isInlineMarker(insn, "mark");
    }

    private static final boolean isSuspendMarker(AbstractInsnNode abstractInsnNode, int i) {
        if (isInlineMarker(abstractInsnNode, "mark")) {
            AbstractInsnNode previous = abstractInsnNode.getPrevious();
            Intrinsics.checkNotNullExpressionValue(previous, "getPrevious(...)");
            Integer intConstant = UtilKt.getIntConstant(previous);
            if (intConstant != null && intConstant.intValue() == i) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isInlineMarker(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return isInlineMarker(insn, null);
    }

    public static final boolean isInlineMarker(AbstractInsnNode insn, String str) {
        boolean zAreEqual;
        Intrinsics.checkNotNullParameter(insn, "insn");
        if (insn.getOpcode() != 184) {
            return false;
        }
        MethodInsnNode methodInsnNode = (MethodInsnNode) insn;
        if (!Intrinsics.areEqual(methodInsnNode.owner, INLINE_MARKER_CLASS_NAME)) {
            return false;
        }
        if (str != null) {
            zAreEqual = Intrinsics.areEqual(methodInsnNode.f1015name, str);
        } else {
            zAreEqual = Intrinsics.areEqual(methodInsnNode.f1015name, "beforeInlineCall") || Intrinsics.areEqual(methodInsnNode.f1015name, "afterInlineCall");
        }
        return zAreEqual;
    }

    public static final boolean isBeforeInlineMarker(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return isInlineMarker(insn, "beforeInlineCall");
    }

    public static final boolean isAfterInlineMarker(AbstractInsnNode insn) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        return isInlineMarker(insn, "afterInlineCall");
    }

    public static final int calcMarkerShift(Parameters parameters, MethodNode node) {
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(node, "node");
        return (getIndexAfterLastMarker(node) - parameters.getRealParametersSizeOnStack()) + parameters.getArgsSizeOnStack();
    }

    private static final int getIndexAfterLastMarker(MethodNode methodNode) {
        int iMax = -1;
        for (LocalVariableNode localVariableNode : methodNode.localVariables) {
            String name2 = localVariableNode.f1014name;
            Intrinsics.checkNotNullExpressionValue(name2, "name");
            if (isFakeLocalVariableForInline(name2)) {
                iMax = Math.max(iMax, localVariableNode.index + 1);
            }
        }
        return iMax;
    }

    public static final boolean isFakeLocalVariableForInline(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return StringsKt.startsWith$default(name2, JvmAbi.LOCAL_VARIABLE_NAME_PREFIX_INLINE_FUNCTION, false, 2, (Object) null) || StringsKt.startsWith$default(name2, JvmAbi.LOCAL_VARIABLE_NAME_PREFIX_INLINE_ARGUMENT, false, 2, (Object) null);
    }

    public static final boolean isThis0(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return Intrinsics.areEqual(AsmUtil.CAPTURED_THIS_FIELD, name2);
    }

    public static /* synthetic */ void preprocessSuspendMarkers$default(MethodNode methodNode, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            z2 = true;
        }
        preprocessSuspendMarkers(methodNode, z, z2);
    }

    public static final void preprocessSuspendMarkers(MethodNode methodNode, boolean z, boolean z2) {
        AbstractInsnNode next;
        AbstractInsnNode next2;
        AbstractInsnNode next3;
        AbstractInsnNode next4;
        Intrinsics.checkNotNullParameter(methodNode, "<this>");
        if (methodNode.instructions.getFirst() == null) {
            return;
        }
        if (!z2) {
            InsnList instructions = methodNode.instructions;
            Intrinsics.checkNotNullExpressionValue(instructions, "instructions");
            Sequence<AbstractInsnNode> sequenceAsSequence = UtilKt.asSequence(instructions);
            Iterator<AbstractInsnNode> it = sequenceAsSequence.iterator();
            do {
                next = null;
                if (!it.hasNext()) {
                    next2 = null;
                    break;
                }
                next2 = it.next();
            } while (!isBeforeFakeContinuationConstructorCallMarker(next2));
            AbstractInsnNode abstractInsnNode = next2;
            Iterator<AbstractInsnNode> it2 = sequenceAsSequence.iterator();
            do {
                if (!it2.hasNext()) {
                    next3 = null;
                    break;
                }
                next3 = it2.next();
            } while (!isAfterFakeContinuationConstructorCallMarker(next3));
            AbstractInsnNode abstractInsnNode2 = next3;
            if (abstractInsnNode != null) {
                AbstractInsnNode previous = abstractInsnNode.getPrevious();
                Intrinsics.checkNotNullExpressionValue(previous, "getPrevious(...)");
                if (abstractInsnNode2 != null && (next4 = abstractInsnNode2.getNext()) != null) {
                    next = next4.getNext();
                }
                InsnSequence insnSequence = new InsnSequence(previous, next);
                InsnList instructions2 = methodNode.instructions;
                Intrinsics.checkNotNullExpressionValue(instructions2, "instructions");
                Iterator<AbstractInsnNode> it3 = insnSequence.iterator();
                while (it3.hasNext()) {
                    instructions2.remove(it3.next());
                }
            }
        }
        InsnList instructions3 = methodNode.instructions;
        Intrinsics.checkNotNullExpressionValue(instructions3, "instructions");
        for (AbstractInsnNode abstractInsnNode3 : SequencesKt.filter(UtilKt.asSequence(instructions3), new Function1<AbstractInsnNode, Boolean>() { // from class: org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt.preprocessSuspendMarkers.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(AbstractInsnNode it4) {
                Intrinsics.checkNotNullParameter(it4, "it");
                return Boolean.valueOf(InlineCodegenUtilsKt.isBeforeInlineSuspendMarker(it4) || InlineCodegenUtilsKt.isAfterInlineSuspendMarker(it4));
            }
        })) {
            if (z || z2) {
                AbstractInsnNode previous2 = abstractInsnNode3.getPrevious().getPrevious();
                Intrinsics.checkNotNull(previous2);
                if (isReturnsUnitMarker(previous2)) {
                    methodNode.instructions.remove(previous2.getPrevious());
                    methodNode.instructions.remove(previous2);
                }
                methodNode.instructions.remove(abstractInsnNode3.getPrevious());
                methodNode.instructions.remove(abstractInsnNode3);
            } else {
                methodNode.instructions.set(abstractInsnNode3.getPrevious(), new InsnNode((!isBeforeInlineSuspendMarker(abstractInsnNode3) ? 1 : 0) + 3));
            }
        }
    }

    public static final MethodNode cloneMethodNode(MethodNode methodNode) {
        MethodNode methodNode2;
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        synchronized (methodNode) {
            methodNode.instructions.resetLabels();
            int i = methodNode.access;
            String str = methodNode.f1016name;
            String str2 = methodNode.desc;
            String str3 = methodNode.signature;
            List<String> exceptions = methodNode.exceptions;
            Intrinsics.checkNotNullExpressionValue(exceptions, "exceptions");
            methodNode2 = new MethodNode(589824, i, str, str2, str3, (String[]) exceptions.toArray(new String[0]));
            methodNode.accept(methodNode2);
        }
        return methodNode2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final SMAPAndMethodNode getMethodNode(byte[] classData, Type classType, final Method method) {
        Intrinsics.checkNotNullParameter(classData, "classData");
        Intrinsics.checkNotNullParameter(classType, "classType");
        Intrinsics.checkNotNullParameter(method, "method");
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        final Ref.ObjectRef objectRef3 = new Ref.ObjectRef();
        new ClassReader(classData).accept(new ClassVisitor() { // from class: org.jetbrains.kotlin.codegen.inline.InlineCodegenUtilsKt$getMethodNode$$inlined$getMethodNode$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(589824);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public void visitSource(String source, String debug) {
                objectRef2.element = source;
                objectRef3.element = debug;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r8v0, types: [T, org.jetbrains.org.objectweb.asm.tree.MethodNode] */
            @Override // org.jetbrains.org.objectweb.asm.ClassVisitor
            public MethodVisitor visitMethod(int access, String name2, String desc, String signature, String[] exceptions) {
                Intrinsics.checkNotNullParameter(name2, "name");
                Intrinsics.checkNotNullParameter(desc, "desc");
                if (!Intrinsics.areEqual(new Method(name2, desc), method)) {
                    return null;
                }
                MethodNode methodNode = (MethodNode) objectRef.element;
                if (methodNode != null) {
                    throw new AssertionError("Can't find proper '" + name2 + "' method for inline: ambiguity between '" + methodNode.f1016name + methodNode.desc + "' and '" + name2 + desc + '\'');
                }
                objectRef.element = new MethodNode(589824, access, name2, desc, signature, exceptions);
                T t = objectRef.element;
                Intrinsics.checkNotNull(t);
                return (MethodVisitor) t;
            }
        }, 4);
        MethodNode methodNode = (MethodNode) objectRef.element;
        if (methodNode == null) {
            return null;
        }
        Pair<Integer, Integer> pairLineNumberRange = lineNumberRange(CollectionsKt.listOfNotNull(methodNode));
        int iIntValue = pairLineNumberRange.component1().intValue();
        int iIntValue2 = pairLineNumberRange.component2().intValue();
        String str = (String) objectRef3.element;
        String str2 = (String) objectRef2.element;
        String internalName = classType.getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        return new SMAPAndMethodNode(methodNode, SMAPParser.parseOrCreateDefault(str, str2, internalName, iIntValue, iIntValue2));
    }
}
