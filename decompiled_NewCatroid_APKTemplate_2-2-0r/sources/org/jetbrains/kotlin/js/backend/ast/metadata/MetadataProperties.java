package org.jetbrains.kotlin.js.backend.ast.metadata;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsImportedModule;
import org.jetbrains.kotlin.js.backend.ast.JsInvocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.js.backend.ast.JsParameter;
import org.jetbrains.kotlin.js.backend.ast.JsReturn;
import org.jetbrains.kotlin.js.backend.ast.JsVars;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: metadataProperties.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Î\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\b\"\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\"/\u0010\u0002\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\u0000\u001a\u00020\u00018F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\b\u0010\t\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007\"3\u0010\u000b\u001a\u0004\u0018\u00010\n*\u00020\u00032\b\u0010\u0000\u001a\u0004\u0018\u00010\n8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0010\u0010\t\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\"/\u0010\u0012\u001a\u00020\u0011*\u00020\u00132\u0006\u0010\u0000\u001a\u00020\u00118F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u0018\u0010\t\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017\"3\u0010\u001a\u001a\u0004\u0018\u00010\u0019*\u00020\u001b2\b\u0010\u0000\u001a\u0004\u0018\u00010\u00198F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b \u0010\t\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001f\"/\u0010!\u001a\u00020\u0011*\u00020\u00132\u0006\u0010\u0000\u001a\u00020\u00118F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b$\u0010\t\u001a\u0004\b\"\u0010\u0015\"\u0004\b#\u0010\u0017\"/\u0010%\u001a\u00020\u0011*\u00020\u00132\u0006\u0010\u0000\u001a\u00020\u00118F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b(\u0010\t\u001a\u0004\b&\u0010\u0015\"\u0004\b'\u0010\u0017\"3\u0010*\u001a\u0004\u0018\u00010)*\u00020\u00032\b\u0010\u0000\u001a\u0004\u0018\u00010)8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b/\u0010\t\u001a\u0004\b+\u0010,\"\u0004\b-\u0010.\"3\u0010*\u001a\u0004\u0018\u000100*\u0002012\b\u0010\u0000\u001a\u0004\u0018\u0001008F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b4\u0010\t\u001a\u0004\b+\u00102\"\u0004\b-\u00103\"3\u0010*\u001a\u0004\u0018\u00010)*\u00020\u00132\b\u0010\u0000\u001a\u0004\u0018\u00010)8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b7\u0010\t\u001a\u0004\b+\u00105\"\u0004\b-\u00106\"3\u00109\u001a\u0004\u0018\u000108*\u00020:2\b\u0010\u0000\u001a\u0004\u0018\u0001088F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b?\u0010\t\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>\"3\u0010@\u001a\u0004\u0018\u000108*\u00020A2\b\u0010\u0000\u001a\u0004\u0018\u0001088F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\bF\u0010\t\u001a\u0004\bB\u0010C\"\u0004\bD\u0010E\"/\u0010G\u001a\u00020\u0011*\u00020\u001b2\u0006\u0010\u0000\u001a\u00020\u00118F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\bL\u0010\t\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010K\"3\u0010M\u001a\u0004\u0018\u000101*\u00020\u001b2\b\u0010\u0000\u001a\u0004\u0018\u0001018F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\bR\u0010\t\u001a\u0004\bN\u0010O\"\u0004\bP\u0010Q\"3\u0010T\u001a\u0004\u0018\u00010S*\u00020\u001b2\b\u0010\u0000\u001a\u0004\u0018\u00010S8F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\bY\u0010\t\u001a\u0004\bU\u0010V\"\u0004\bW\u0010X\"/\u0010Z\u001a\u00020\u0011*\u00020[2\u0006\u0010\u0000\u001a\u00020\u00118F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b`\u0010\t\u001a\u0004\b\\\u0010]\"\u0004\b^\u0010_\"/\u0010a\u001a\u00020\u0011*\u0002012\u0006\u0010\u0000\u001a\u00020\u00118F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\bf\u0010\t\u001a\u0004\bb\u0010c\"\u0004\bd\u0010e\"/\u0010g\u001a\u00020\u0011*\u00020\u00032\u0006\u0010\u0000\u001a\u00020\u00118F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\bk\u0010\t\u001a\u0004\bg\u0010h\"\u0004\bi\u0010j\"3\u0010l\u001a\u0004\u0018\u00010\u0011*\u00020\u00032\b\u0010\u0000\u001a\u0004\u0018\u00010\u00118F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\bp\u0010\t\u001a\u0004\bl\u0010m\"\u0004\bn\u0010o\"3\u0010l\u001a\u0004\u0018\u00010\u0011*\u00020\u00132\b\u0010\u0000\u001a\u0004\u0018\u00010\u00118F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\bs\u0010\t\u001a\u0004\bl\u0010q\"\u0004\bn\u0010r\"/\u0010t\u001a\u00020\u0011*\u00020u2\u0006\u0010\u0000\u001a\u00020\u00118F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\by\u0010\t\u001a\u0004\bt\u0010v\"\u0004\bw\u0010x\"/\u0010z\u001a\u00020\u0011*\u00020u2\u0006\u0010\u0000\u001a\u00020\u00118F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b|\u0010\t\u001a\u0004\bz\u0010v\"\u0004\b{\u0010x\"/\u0010}\u001a\u00020\u0011*\u00020\u001b2\u0006\u0010\u0000\u001a\u00020\u00118F@FX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u007f\u0010\t\u001a\u0004\b}\u0010I\"\u0004\b~\u0010K\"3\u0010\u0080\u0001\u001a\u00020\u0011*\u00020\u00132\u0006\u0010\u0000\u001a\u00020\u00118F@FX\u0086\u008e\u0002¢\u0006\u0015\n\u0005\b\u0082\u0001\u0010\t\u001a\u0005\b\u0080\u0001\u0010\u0015\"\u0005\b\u0081\u0001\u0010\u0017\"3\u0010\u0083\u0001\u001a\u00020\u0011*\u00020\u001b2\u0006\u0010\u0000\u001a\u00020\u00118F@FX\u0086\u008e\u0002¢\u0006\u0015\n\u0005\b\u0085\u0001\u0010\t\u001a\u0005\b\u0083\u0001\u0010I\"\u0005\b\u0084\u0001\u0010K\"5\u0010\u0086\u0001\u001a\u00020\u0011*\u00020\n2\u0006\u0010\u0000\u001a\u00020\u00118F@FX\u0086\u008e\u0002¢\u0006\u0017\n\u0005\b\u008a\u0001\u0010\t\u001a\u0006\b\u0086\u0001\u0010\u0087\u0001\"\u0006\b\u0088\u0001\u0010\u0089\u0001\"5\u0010\u008b\u0001\u001a\u00020\u0011*\u00020\n2\u0006\u0010\u0000\u001a\u00020\u00118F@FX\u0086\u008e\u0002¢\u0006\u0017\n\u0005\b\u008d\u0001\u0010\t\u001a\u0006\b\u008b\u0001\u0010\u0087\u0001\"\u0006\b\u008c\u0001\u0010\u0089\u0001\"9\u0010\u008e\u0001\u001a\u0004\u0018\u00010\n*\u00020\n2\b\u0010\u0000\u001a\u0004\u0018\u00010\n8F@FX\u0086\u008e\u0002¢\u0006\u0017\n\u0005\b\u0093\u0001\u0010\t\u001a\u0006\b\u008f\u0001\u0010\u0090\u0001\"\u0006\b\u0091\u0001\u0010\u0092\u0001\"5\u0010\u0094\u0001\u001a\u00020\u0011*\u00020\n2\u0006\u0010\u0000\u001a\u00020\u00118F@FX\u0086\u008e\u0002¢\u0006\u0017\n\u0005\b\u0097\u0001\u0010\t\u001a\u0006\b\u0095\u0001\u0010\u0087\u0001\"\u0006\b\u0096\u0001\u0010\u0089\u0001\";\u0010\u0099\u0001\u001a\u0005\u0018\u00010\u0098\u0001*\u00020\n2\t\u0010\u0000\u001a\u0005\u0018\u00010\u0098\u00018F@FX\u0086\u008e\u0002¢\u0006\u0017\n\u0005\b\u009e\u0001\u0010\t\u001a\u0006\b\u009a\u0001\u0010\u009b\u0001\"\u0006\b\u009c\u0001\u0010\u009d\u0001\";\u0010\u0099\u0001\u001a\u0005\u0018\u00010\u009f\u0001*\u0002012\t\u0010\u0000\u001a\u0005\u0018\u00010\u009f\u00018F@FX\u0086\u008e\u0002¢\u0006\u0017\n\u0005\b¢\u0001\u0010\t\u001a\u0006\b\u009a\u0001\u0010 \u0001\"\u0006\b\u009c\u0001\u0010¡\u0001\"9\u0010£\u0001\u001a\u0004\u0018\u00010\n*\u00020\n2\b\u0010\u0000\u001a\u0004\u0018\u00010\n8F@FX\u0086\u008e\u0002¢\u0006\u0017\n\u0005\b¦\u0001\u0010\t\u001a\u0006\b¤\u0001\u0010\u0090\u0001\"\u0006\b¥\u0001\u0010\u0092\u0001\";\u0010¨\u0001\u001a\u0005\u0018\u00010§\u0001*\u00020\u00032\t\u0010\u0000\u001a\u0005\u0018\u00010§\u00018F@FX\u0086\u008e\u0002¢\u0006\u0017\n\u0005\b\u00ad\u0001\u0010\t\u001a\u0006\b©\u0001\u0010ª\u0001\"\u0006\b«\u0001\u0010¬\u0001\";\u0010¨\u0001\u001a\u0005\u0018\u00010§\u0001*\u00020\u00132\t\u0010\u0000\u001a\u0005\u0018\u00010§\u00018F@FX\u0086\u008e\u0002¢\u0006\u0017\n\u0005\b°\u0001\u0010\t\u001a\u0006\b©\u0001\u0010®\u0001\"\u0006\b«\u0001\u0010¯\u0001\"W\u0010´\u0001\u001a\u0013\u0012\u0005\u0012\u00030²\u0001\u0012\u0005\u0012\u00030³\u0001\u0018\u00010±\u0001*\u00020\n2\u0017\u0010\u0000\u001a\u0013\u0012\u0005\u0012\u00030²\u0001\u0012\u0005\u0012\u00030³\u0001\u0018\u00010±\u00018F@FX\u0086\u008e\u0002¢\u0006\u0017\n\u0005\b¹\u0001\u0010\t\u001a\u0006\bµ\u0001\u0010¶\u0001\"\u0006\b·\u0001\u0010¸\u0001\":\u0010º\u0001\u001a\u0004\u0018\u00010S*\u00030»\u00012\b\u0010\u0000\u001a\u0004\u0018\u00010S8F@FX\u0086\u008e\u0002¢\u0006\u0017\n\u0005\bÀ\u0001\u0010\t\u001a\u0006\b¼\u0001\u0010½\u0001\"\u0006\b¾\u0001\u0010¿\u0001\"7\u0010Â\u0001\u001a\u00030Á\u0001*\u00020u2\u0007\u0010\u0000\u001a\u00030Á\u00018F@FX\u0086\u008e\u0002¢\u0006\u0017\n\u0005\bÇ\u0001\u0010\t\u001a\u0006\bÃ\u0001\u0010Ä\u0001\"\u0006\bÅ\u0001\u0010Æ\u0001\";\u0010É\u0001\u001a\u0005\u0018\u00010È\u0001*\u0002012\t\u0010\u0000\u001a\u0005\u0018\u00010È\u00018F@FX\u0086\u008e\u0002¢\u0006\u0017\n\u0005\bÎ\u0001\u0010\t\u001a\u0006\bÊ\u0001\u0010Ë\u0001\"\u0006\bÌ\u0001\u0010Í\u0001\";\u0010Ð\u0001\u001a\u0005\u0018\u00010Ï\u0001*\u0002012\t\u0010\u0000\u001a\u0005\u0018\u00010Ï\u00018F@FX\u0086\u008e\u0002¢\u0006\u0017\n\u0005\bÕ\u0001\u0010\t\u001a\u0006\bÑ\u0001\u0010Ò\u0001\"\u0006\bÓ\u0001\u0010Ô\u0001\"3\u0010Ö\u0001\u001a\u00020\u0011*\u00020u2\u0006\u0010\u0000\u001a\u00020\u00118F@FX\u0086\u008e\u0002¢\u0006\u0015\n\u0005\bÙ\u0001\u0010\t\u001a\u0005\b×\u0001\u0010v\"\u0005\bØ\u0001\u0010x\";\u0010Û\u0001\u001a\u0005\u0018\u00010Ú\u0001*\u00020\n2\t\u0010\u0000\u001a\u0005\u0018\u00010Ú\u00018F@FX\u0086\u008e\u0002¢\u0006\u0017\n\u0005\bà\u0001\u0010\t\u001a\u0006\bÜ\u0001\u0010Ý\u0001\"\u0006\bÞ\u0001\u0010ß\u0001\";\u0010â\u0001\u001a\u0005\u0018\u00010á\u0001*\u00020\u00032\t\u0010\u0000\u001a\u0005\u0018\u00010á\u00018F@FX\u0086\u008e\u0002¢\u0006\u0017\n\u0005\bç\u0001\u0010\t\u001a\u0006\bã\u0001\u0010ä\u0001\"\u0006\bå\u0001\u0010æ\u0001¨\u0006è\u0001"}, d2 = {"<set-?>", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/BoxingKind;", "boxing", "Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;", "getBoxing", "(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;)Lorg/jetbrains/kotlin/js/backend/ast/metadata/BoxingKind;", "setBoxing", "(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;Lorg/jetbrains/kotlin/js/backend/ast/metadata/BoxingKind;)V", "boxing$delegate", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/MetadataProperty;", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "callableReferenceReceiver", "getCallableReferenceReceiver", "(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;)Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "setCallableReferenceReceiver", "(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)V", "callableReferenceReceiver$delegate", "", "coroutineController", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "getCoroutineController", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;)Z", "setCoroutineController", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;Z)V", "coroutineController$delegate", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/CoroutineMetadata;", "coroutineMetadata", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "getCoroutineMetadata", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;)Lorg/jetbrains/kotlin/js/backend/ast/metadata/CoroutineMetadata;", "setCoroutineMetadata", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;Lorg/jetbrains/kotlin/js/backend/ast/metadata/CoroutineMetadata;)V", "coroutineMetadata$delegate", "coroutineReceiver", "getCoroutineReceiver", "setCoroutineReceiver", "coroutineReceiver$delegate", "coroutineResult", "getCoroutineResult", "setCoroutineResult", "coroutineResult$delegate", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "descriptor", "getDescriptor", "(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;)Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "setDescriptor", "(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)V", "descriptor$delegate$1", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;)Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)V", "descriptor$delegate", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;)Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;)V", "descriptor$delegate$2", "", "exportedPackage", "Lorg/jetbrains/kotlin/js/backend/ast/JsVars;", "getExportedPackage", "(Lorg/jetbrains/kotlin/js/backend/ast/JsVars;)Ljava/lang/String;", "setExportedPackage", "(Lorg/jetbrains/kotlin/js/backend/ast/JsVars;Ljava/lang/String;)V", "exportedPackage$delegate", "exportedTag", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;", "getExportedTag", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;)Ljava/lang/String;", "setExportedTag", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;Ljava/lang/String;)V", "exportedTag$delegate", "forceStateMachine", "getForceStateMachine", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;)Z", "setForceStateMachine", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;Z)V", "forceStateMachine$delegate", "forcedReturnVariable", "getForcedReturnVariable", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;)Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "setForcedReturnVariable", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;Lorg/jetbrains/kotlin/js/backend/ast/JsName;)V", "forcedReturnVariable$delegate", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "functionDescriptor", "getFunctionDescriptor", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;)Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "setFunctionDescriptor", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)V", "functionDescriptor$delegate", "hasDefaultValue", "Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;", "getHasDefaultValue", "(Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;)Z", "setHasDefaultValue", "(Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;Z)V", "hasDefaultValue$delegate", "imported", "getImported", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;)Z", "setImported", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;Z)V", "imported$delegate", "isCallableReference", "(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;)Z", "setCallableReference", "(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;Z)V", "isCallableReference$delegate", "isInline", "(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;)Ljava/lang/Boolean;", "setInline", "(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;Ljava/lang/Boolean;)V", "isInline$delegate", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;)Ljava/lang/Boolean;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;Ljava/lang/Boolean;)V", "isInline$delegate$1", "isInlineClassBoxing", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/HasMetadata;", "(Lorg/jetbrains/kotlin/js/backend/ast/metadata/HasMetadata;)Z", "setInlineClassBoxing", "(Lorg/jetbrains/kotlin/js/backend/ast/metadata/HasMetadata;Z)V", "isInlineClassBoxing$delegate", "isInlineClassUnboxing", "setInlineClassUnboxing", "isInlineClassUnboxing$delegate", "isInlineableCoroutineBody", "setInlineableCoroutineBody", "isInlineableCoroutineBody$delegate", "isJsCall", "setJsCall", "isJsCall$delegate", "isLocal", "setLocal", "isLocal$delegate", "isSuspend", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Z", "setSuspend", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;Z)V", "isSuspend$delegate", "isUnit", "setUnit", "isUnit$delegate", "kType", "getKType", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "setKType", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)V", "kType$delegate", "kTypeWithRecursion", "getKTypeWithRecursion", "setKTypeWithRecursion", "kTypeWithRecursion$delegate", "Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;", "localAlias", "getLocalAlias", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;", "setLocalAlias", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;)V", "localAlias$delegate$1", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/LocalAlias;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;)Lorg/jetbrains/kotlin/js/backend/ast/metadata/LocalAlias;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;Lorg/jetbrains/kotlin/js/backend/ast/metadata/LocalAlias;)V", "localAlias$delegate", "primitiveKClass", "getPrimitiveKClass", "setPrimitiveKClass", "primitiveKClass$delegate", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "psiElement", "getPsiElement", "(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;)Lcom/intellij/psi/PsiElement;", "setPsiElement", "(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;Lcom/intellij/psi/PsiElement;)V", "psiElement$delegate", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;)Lcom/intellij/psi/PsiElement;", "(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;Lcom/intellij/psi/PsiElement;)V", "psiElement$delegate$1", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/RangeType;", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/RangeKind;", "range", "getRange", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Lkotlin/Pair;", "setRange", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;Lkotlin/Pair;)V", "range$delegate", "returnTarget", "Lorg/jetbrains/kotlin/js/backend/ast/JsReturn;", "getReturnTarget", "(Lorg/jetbrains/kotlin/js/backend/ast/JsReturn;)Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "setReturnTarget", "(Lorg/jetbrains/kotlin/js/backend/ast/JsReturn;Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)V", "returnTarget$delegate", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/SideEffectKind;", "sideEffects", "getSideEffects", "(Lorg/jetbrains/kotlin/js/backend/ast/metadata/HasMetadata;)Lorg/jetbrains/kotlin/js/backend/ast/metadata/SideEffectKind;", "setSideEffects", "(Lorg/jetbrains/kotlin/js/backend/ast/metadata/HasMetadata;Lorg/jetbrains/kotlin/js/backend/ast/metadata/SideEffectKind;)V", "sideEffects$delegate", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/SpecialFunction;", "specialFunction", "getSpecialFunction", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;)Lorg/jetbrains/kotlin/js/backend/ast/metadata/SpecialFunction;", "setSpecialFunction", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;Lorg/jetbrains/kotlin/js/backend/ast/metadata/SpecialFunction;)V", "specialFunction$delegate", "Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "staticRef", "getStaticRef", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;)Lorg/jetbrains/kotlin/js/backend/ast/JsNode;", "setStaticRef", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;Lorg/jetbrains/kotlin/js/backend/ast/JsNode;)V", "staticRef$delegate", "synthetic", "getSynthetic", "setSynthetic", "synthetic$delegate", "Lorg/jetbrains/kotlin/types/KotlinType;", ModuleXmlParser.TYPE, "getType", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Lorg/jetbrains/kotlin/types/KotlinType;", "setType", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;Lorg/jetbrains/kotlin/types/KotlinType;)V", "type$delegate", "Lorg/jetbrains/kotlin/js/backend/ast/metadata/TypeCheck;", "typeCheck", "getTypeCheck", "(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;)Lorg/jetbrains/kotlin/js/backend/ast/metadata/TypeCheck;", "setTypeCheck", "(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;Lorg/jetbrains/kotlin/js/backend/ast/metadata/TypeCheck;)V", "typeCheck$delegate", "js.ast"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class MetadataProperties {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "staticRef", "getStaticRef(Lorg/jetbrains/kotlin/js/backend/ast/JsName;)Lorg/jetbrains/kotlin/js/backend/ast/JsNode;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "descriptor", "getDescriptor(Lorg/jetbrains/kotlin/js/backend/ast/JsName;)Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "localAlias", "getLocalAlias(Lorg/jetbrains/kotlin/js/backend/ast/JsName;)Lorg/jetbrains/kotlin/js/backend/ast/metadata/LocalAlias;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "specialFunction", "getSpecialFunction(Lorg/jetbrains/kotlin/js/backend/ast/JsName;)Lorg/jetbrains/kotlin/js/backend/ast/metadata/SpecialFunction;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "localAlias", "getLocalAlias(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Lorg/jetbrains/kotlin/js/backend/ast/JsImportedModule;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "isInline", "isInline(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;)Ljava/lang/Boolean;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "isCallableReference", "isCallableReference(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;)Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "callableReferenceReceiver", "getCallableReferenceReceiver(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;)Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "descriptor", "getDescriptor(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;)Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "psiElement", "getPsiElement(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;)Lcom/intellij/psi/PsiElement;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "isJsCall", "isJsCall(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;)Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "isInline", "isInline(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;)Ljava/lang/Boolean;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "descriptor", "getDescriptor(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;)Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "psiElement", "getPsiElement(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;)Lcom/intellij/psi/PsiElement;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "isLocal", "isLocal(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;)Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "forcedReturnVariable", "getForcedReturnVariable(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;)Lorg/jetbrains/kotlin/js/backend/ast/JsName;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "hasDefaultValue", "getHasDefaultValue(Lorg/jetbrains/kotlin/js/backend/ast/JsParameter;)Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "typeCheck", "getTypeCheck(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;)Lorg/jetbrains/kotlin/js/backend/ast/metadata/TypeCheck;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "boxing", "getBoxing(Lorg/jetbrains/kotlin/js/backend/ast/JsInvocation;)Lorg/jetbrains/kotlin/js/backend/ast/metadata/BoxingKind;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "exportedPackage", "getExportedPackage(Lorg/jetbrains/kotlin/js/backend/ast/JsVars;)Ljava/lang/String;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "exportedTag", "getExportedTag(Lorg/jetbrains/kotlin/js/backend/ast/JsExpressionStatement;)Ljava/lang/String;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), ModuleXmlParser.TYPE, "getType(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Lorg/jetbrains/kotlin/types/KotlinType;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "isUnit", "isUnit(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "functionDescriptor", "getFunctionDescriptor(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;)Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "returnTarget", "getReturnTarget(Lorg/jetbrains/kotlin/js/backend/ast/JsReturn;)Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "synthetic", "getSynthetic(Lorg/jetbrains/kotlin/js/backend/ast/metadata/HasMetadata;)Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "isInlineClassBoxing", "isInlineClassBoxing(Lorg/jetbrains/kotlin/js/backend/ast/metadata/HasMetadata;)Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "isInlineClassUnboxing", "isInlineClassUnboxing(Lorg/jetbrains/kotlin/js/backend/ast/metadata/HasMetadata;)Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "sideEffects", "getSideEffects(Lorg/jetbrains/kotlin/js/backend/ast/metadata/HasMetadata;)Lorg/jetbrains/kotlin/js/backend/ast/metadata/SideEffectKind;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "isSuspend", "isSuspend(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "coroutineResult", "getCoroutineResult(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;)Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "coroutineController", "getCoroutineController(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;)Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "coroutineReceiver", "getCoroutineReceiver(Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;)Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "forceStateMachine", "getForceStateMachine(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;)Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "isInlineableCoroutineBody", "isInlineableCoroutineBody(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;)Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "imported", "getImported(Lorg/jetbrains/kotlin/js/backend/ast/JsName;)Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "coroutineMetadata", "getCoroutineMetadata(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;)Lorg/jetbrains/kotlin/js/backend/ast/metadata/CoroutineMetadata;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "range", "getRange(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Lkotlin/Pair;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "primitiveKClass", "getPrimitiveKClass(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "kType", "getKType(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(MetadataProperties.class, "js.ast"), "kTypeWithRecursion", "getKTypeWithRecursion(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)Z"))};
    private static final MetadataProperty staticRef$delegate = new MetadataProperty(null);
    private static final MetadataProperty descriptor$delegate = new MetadataProperty(null);
    private static final MetadataProperty localAlias$delegate = new MetadataProperty(null);
    private static final MetadataProperty specialFunction$delegate = new MetadataProperty(null);
    private static final MetadataProperty localAlias$delegate$1 = new MetadataProperty(null);
    private static final MetadataProperty isInline$delegate = new MetadataProperty(null);
    private static final MetadataProperty isCallableReference$delegate = new MetadataProperty(false);
    private static final MetadataProperty callableReferenceReceiver$delegate = new MetadataProperty(null);
    private static final MetadataProperty descriptor$delegate$1 = new MetadataProperty(null);
    private static final MetadataProperty psiElement$delegate = new MetadataProperty(null);
    private static final MetadataProperty isJsCall$delegate = new MetadataProperty(false);
    private static final MetadataProperty isInline$delegate$1 = new MetadataProperty(null);
    private static final MetadataProperty descriptor$delegate$2 = new MetadataProperty(null);
    private static final MetadataProperty psiElement$delegate$1 = new MetadataProperty(null);
    private static final MetadataProperty isLocal$delegate = new MetadataProperty(false);
    private static final MetadataProperty forcedReturnVariable$delegate = new MetadataProperty(null);
    private static final MetadataProperty hasDefaultValue$delegate = new MetadataProperty(false);
    private static final MetadataProperty typeCheck$delegate = new MetadataProperty(null);
    private static final MetadataProperty boxing$delegate = new MetadataProperty(BoxingKind.NONE);
    private static final MetadataProperty exportedPackage$delegate = new MetadataProperty(null);
    private static final MetadataProperty exportedTag$delegate = new MetadataProperty(null);
    private static final MetadataProperty type$delegate = new MetadataProperty(null);
    private static final MetadataProperty isUnit$delegate = new MetadataProperty(false);
    private static final MetadataProperty functionDescriptor$delegate = new MetadataProperty(null);
    private static final MetadataProperty returnTarget$delegate = new MetadataProperty(null);
    private static final MetadataProperty synthetic$delegate = new MetadataProperty(false);
    private static final MetadataProperty isInlineClassBoxing$delegate = new MetadataProperty(false);
    private static final MetadataProperty isInlineClassUnboxing$delegate = new MetadataProperty(false);
    private static final MetadataProperty sideEffects$delegate = new MetadataProperty(SideEffectKind.AFFECTS_STATE);
    private static final MetadataProperty isSuspend$delegate = new MetadataProperty(false);
    private static final MetadataProperty coroutineResult$delegate = new MetadataProperty(false);
    private static final MetadataProperty coroutineController$delegate = new MetadataProperty(false);
    private static final MetadataProperty coroutineReceiver$delegate = new MetadataProperty(false);
    private static final MetadataProperty forceStateMachine$delegate = new MetadataProperty(false);
    private static final MetadataProperty isInlineableCoroutineBody$delegate = new MetadataProperty(false);
    private static final MetadataProperty imported$delegate = new MetadataProperty(false);
    private static final MetadataProperty coroutineMetadata$delegate = new MetadataProperty(null);
    private static final MetadataProperty range$delegate = new MetadataProperty(null);
    private static final MetadataProperty primitiveKClass$delegate = new MetadataProperty(null);
    private static final MetadataProperty kType$delegate = new MetadataProperty(null);
    private static final MetadataProperty kTypeWithRecursion$delegate = new MetadataProperty(false);

    public static final JsNode getStaticRef(JsName jsName) {
        Intrinsics.checkNotNullParameter(jsName, "<this>");
        return (JsNode) staticRef$delegate.getValue(jsName, $$delegatedProperties[0]);
    }

    public static final void setStaticRef(JsName jsName, JsNode jsNode) {
        Intrinsics.checkNotNullParameter(jsName, "<this>");
        staticRef$delegate.setValue(jsName, $$delegatedProperties[0], jsNode);
    }

    public static final DeclarationDescriptor getDescriptor(JsName jsName) {
        Intrinsics.checkNotNullParameter(jsName, "<this>");
        return (DeclarationDescriptor) descriptor$delegate.getValue(jsName, $$delegatedProperties[1]);
    }

    public static final void setDescriptor(JsName jsName, DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(jsName, "<this>");
        descriptor$delegate.setValue(jsName, $$delegatedProperties[1], declarationDescriptor);
    }

    public static final LocalAlias getLocalAlias(JsName jsName) {
        Intrinsics.checkNotNullParameter(jsName, "<this>");
        return (LocalAlias) localAlias$delegate.getValue(jsName, $$delegatedProperties[2]);
    }

    public static final void setLocalAlias(JsName jsName, LocalAlias localAlias) {
        Intrinsics.checkNotNullParameter(jsName, "<this>");
        localAlias$delegate.setValue(jsName, $$delegatedProperties[2], localAlias);
    }

    public static final SpecialFunction getSpecialFunction(JsName jsName) {
        Intrinsics.checkNotNullParameter(jsName, "<this>");
        return (SpecialFunction) specialFunction$delegate.getValue(jsName, $$delegatedProperties[3]);
    }

    public static final void setSpecialFunction(JsName jsName, SpecialFunction specialFunction) {
        Intrinsics.checkNotNullParameter(jsName, "<this>");
        specialFunction$delegate.setValue(jsName, $$delegatedProperties[3], specialFunction);
    }

    public static final JsImportedModule getLocalAlias(JsExpression jsExpression) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        return (JsImportedModule) localAlias$delegate$1.getValue(jsExpression, $$delegatedProperties[4]);
    }

    public static final void setLocalAlias(JsExpression jsExpression, JsImportedModule jsImportedModule) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        localAlias$delegate$1.setValue(jsExpression, $$delegatedProperties[4], jsImportedModule);
    }

    public static final Boolean isInline(JsInvocation jsInvocation) {
        Intrinsics.checkNotNullParameter(jsInvocation, "<this>");
        return (Boolean) isInline$delegate.getValue(jsInvocation, $$delegatedProperties[5]);
    }

    public static final void setInline(JsInvocation jsInvocation, Boolean bool) {
        Intrinsics.checkNotNullParameter(jsInvocation, "<this>");
        isInline$delegate.setValue(jsInvocation, $$delegatedProperties[5], bool);
    }

    public static final boolean isCallableReference(JsInvocation jsInvocation) {
        Intrinsics.checkNotNullParameter(jsInvocation, "<this>");
        return ((Boolean) isCallableReference$delegate.getValue(jsInvocation, $$delegatedProperties[6])).booleanValue();
    }

    public static final void setCallableReference(JsInvocation jsInvocation, boolean z) {
        Intrinsics.checkNotNullParameter(jsInvocation, "<this>");
        isCallableReference$delegate.setValue(jsInvocation, $$delegatedProperties[6], Boolean.valueOf(z));
    }

    public static final JsExpression getCallableReferenceReceiver(JsInvocation jsInvocation) {
        Intrinsics.checkNotNullParameter(jsInvocation, "<this>");
        return (JsExpression) callableReferenceReceiver$delegate.getValue(jsInvocation, $$delegatedProperties[7]);
    }

    public static final void setCallableReferenceReceiver(JsInvocation jsInvocation, JsExpression jsExpression) {
        Intrinsics.checkNotNullParameter(jsInvocation, "<this>");
        callableReferenceReceiver$delegate.setValue(jsInvocation, $$delegatedProperties[7], jsExpression);
    }

    public static final CallableDescriptor getDescriptor(JsInvocation jsInvocation) {
        Intrinsics.checkNotNullParameter(jsInvocation, "<this>");
        return (CallableDescriptor) descriptor$delegate$1.getValue(jsInvocation, $$delegatedProperties[8]);
    }

    public static final void setDescriptor(JsInvocation jsInvocation, CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(jsInvocation, "<this>");
        descriptor$delegate$1.setValue(jsInvocation, $$delegatedProperties[8], callableDescriptor);
    }

    public static final PsiElement getPsiElement(JsInvocation jsInvocation) {
        Intrinsics.checkNotNullParameter(jsInvocation, "<this>");
        return (PsiElement) psiElement$delegate.getValue(jsInvocation, $$delegatedProperties[9]);
    }

    public static final void setPsiElement(JsInvocation jsInvocation, PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(jsInvocation, "<this>");
        psiElement$delegate.setValue(jsInvocation, $$delegatedProperties[9], psiElement);
    }

    public static final boolean isJsCall(JsNameRef jsNameRef) {
        Intrinsics.checkNotNullParameter(jsNameRef, "<this>");
        return ((Boolean) isJsCall$delegate.getValue(jsNameRef, $$delegatedProperties[10])).booleanValue();
    }

    public static final void setJsCall(JsNameRef jsNameRef, boolean z) {
        Intrinsics.checkNotNullParameter(jsNameRef, "<this>");
        isJsCall$delegate.setValue(jsNameRef, $$delegatedProperties[10], Boolean.valueOf(z));
    }

    public static final Boolean isInline(JsNameRef jsNameRef) {
        Intrinsics.checkNotNullParameter(jsNameRef, "<this>");
        return (Boolean) isInline$delegate$1.getValue(jsNameRef, $$delegatedProperties[11]);
    }

    public static final void setInline(JsNameRef jsNameRef, Boolean bool) {
        Intrinsics.checkNotNullParameter(jsNameRef, "<this>");
        isInline$delegate$1.setValue(jsNameRef, $$delegatedProperties[11], bool);
    }

    public static final CallableDescriptor getDescriptor(JsNameRef jsNameRef) {
        Intrinsics.checkNotNullParameter(jsNameRef, "<this>");
        return (CallableDescriptor) descriptor$delegate$2.getValue(jsNameRef, $$delegatedProperties[12]);
    }

    public static final void setDescriptor(JsNameRef jsNameRef, CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(jsNameRef, "<this>");
        descriptor$delegate$2.setValue(jsNameRef, $$delegatedProperties[12], callableDescriptor);
    }

    public static final PsiElement getPsiElement(JsNameRef jsNameRef) {
        Intrinsics.checkNotNullParameter(jsNameRef, "<this>");
        return (PsiElement) psiElement$delegate$1.getValue(jsNameRef, $$delegatedProperties[13]);
    }

    public static final void setPsiElement(JsNameRef jsNameRef, PsiElement psiElement) {
        Intrinsics.checkNotNullParameter(jsNameRef, "<this>");
        psiElement$delegate$1.setValue(jsNameRef, $$delegatedProperties[13], psiElement);
    }

    public static final boolean isLocal(JsFunction jsFunction) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        return ((Boolean) isLocal$delegate.getValue(jsFunction, $$delegatedProperties[14])).booleanValue();
    }

    public static final void setLocal(JsFunction jsFunction, boolean z) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        isLocal$delegate.setValue(jsFunction, $$delegatedProperties[14], Boolean.valueOf(z));
    }

    public static final JsName getForcedReturnVariable(JsFunction jsFunction) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        return (JsName) forcedReturnVariable$delegate.getValue(jsFunction, $$delegatedProperties[15]);
    }

    public static final void setForcedReturnVariable(JsFunction jsFunction, JsName jsName) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        forcedReturnVariable$delegate.setValue(jsFunction, $$delegatedProperties[15], jsName);
    }

    public static final boolean getHasDefaultValue(JsParameter jsParameter) {
        Intrinsics.checkNotNullParameter(jsParameter, "<this>");
        return ((Boolean) hasDefaultValue$delegate.getValue(jsParameter, $$delegatedProperties[16])).booleanValue();
    }

    public static final void setHasDefaultValue(JsParameter jsParameter, boolean z) {
        Intrinsics.checkNotNullParameter(jsParameter, "<this>");
        hasDefaultValue$delegate.setValue(jsParameter, $$delegatedProperties[16], Boolean.valueOf(z));
    }

    public static final TypeCheck getTypeCheck(JsInvocation jsInvocation) {
        Intrinsics.checkNotNullParameter(jsInvocation, "<this>");
        return (TypeCheck) typeCheck$delegate.getValue(jsInvocation, $$delegatedProperties[17]);
    }

    public static final void setTypeCheck(JsInvocation jsInvocation, TypeCheck typeCheck) {
        Intrinsics.checkNotNullParameter(jsInvocation, "<this>");
        typeCheck$delegate.setValue(jsInvocation, $$delegatedProperties[17], typeCheck);
    }

    public static final BoxingKind getBoxing(JsInvocation jsInvocation) {
        Intrinsics.checkNotNullParameter(jsInvocation, "<this>");
        return (BoxingKind) boxing$delegate.getValue(jsInvocation, $$delegatedProperties[18]);
    }

    public static final void setBoxing(JsInvocation jsInvocation, BoxingKind boxingKind) {
        Intrinsics.checkNotNullParameter(jsInvocation, "<this>");
        Intrinsics.checkNotNullParameter(boxingKind, "<set-?>");
        boxing$delegate.setValue(jsInvocation, $$delegatedProperties[18], boxingKind);
    }

    public static final String getExportedPackage(JsVars jsVars) {
        Intrinsics.checkNotNullParameter(jsVars, "<this>");
        return (String) exportedPackage$delegate.getValue(jsVars, $$delegatedProperties[19]);
    }

    public static final void setExportedPackage(JsVars jsVars, String str) {
        Intrinsics.checkNotNullParameter(jsVars, "<this>");
        exportedPackage$delegate.setValue(jsVars, $$delegatedProperties[19], str);
    }

    public static final String getExportedTag(JsExpressionStatement jsExpressionStatement) {
        Intrinsics.checkNotNullParameter(jsExpressionStatement, "<this>");
        return (String) exportedTag$delegate.getValue(jsExpressionStatement, $$delegatedProperties[20]);
    }

    public static final void setExportedTag(JsExpressionStatement jsExpressionStatement, String str) {
        Intrinsics.checkNotNullParameter(jsExpressionStatement, "<this>");
        exportedTag$delegate.setValue(jsExpressionStatement, $$delegatedProperties[20], str);
    }

    public static final KotlinType getType(JsExpression jsExpression) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        return (KotlinType) type$delegate.getValue(jsExpression, $$delegatedProperties[21]);
    }

    public static final void setType(JsExpression jsExpression, KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        type$delegate.setValue(jsExpression, $$delegatedProperties[21], kotlinType);
    }

    public static final boolean isUnit(JsExpression jsExpression) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        return ((Boolean) isUnit$delegate.getValue(jsExpression, $$delegatedProperties[22])).booleanValue();
    }

    public static final void setUnit(JsExpression jsExpression, boolean z) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        isUnit$delegate.setValue(jsExpression, $$delegatedProperties[22], Boolean.valueOf(z));
    }

    public static final FunctionDescriptor getFunctionDescriptor(JsFunction jsFunction) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        return (FunctionDescriptor) functionDescriptor$delegate.getValue(jsFunction, $$delegatedProperties[23]);
    }

    public static final void setFunctionDescriptor(JsFunction jsFunction, FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        functionDescriptor$delegate.setValue(jsFunction, $$delegatedProperties[23], functionDescriptor);
    }

    public static final FunctionDescriptor getReturnTarget(JsReturn jsReturn) {
        Intrinsics.checkNotNullParameter(jsReturn, "<this>");
        return (FunctionDescriptor) returnTarget$delegate.getValue(jsReturn, $$delegatedProperties[24]);
    }

    public static final void setReturnTarget(JsReturn jsReturn, FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(jsReturn, "<this>");
        returnTarget$delegate.setValue(jsReturn, $$delegatedProperties[24], functionDescriptor);
    }

    public static final boolean getSynthetic(HasMetadata hasMetadata) {
        Intrinsics.checkNotNullParameter(hasMetadata, "<this>");
        return ((Boolean) synthetic$delegate.getValue(hasMetadata, $$delegatedProperties[25])).booleanValue();
    }

    public static final void setSynthetic(HasMetadata hasMetadata, boolean z) {
        Intrinsics.checkNotNullParameter(hasMetadata, "<this>");
        synthetic$delegate.setValue(hasMetadata, $$delegatedProperties[25], Boolean.valueOf(z));
    }

    public static final boolean isInlineClassBoxing(HasMetadata hasMetadata) {
        Intrinsics.checkNotNullParameter(hasMetadata, "<this>");
        return ((Boolean) isInlineClassBoxing$delegate.getValue(hasMetadata, $$delegatedProperties[26])).booleanValue();
    }

    public static final void setInlineClassBoxing(HasMetadata hasMetadata, boolean z) {
        Intrinsics.checkNotNullParameter(hasMetadata, "<this>");
        isInlineClassBoxing$delegate.setValue(hasMetadata, $$delegatedProperties[26], Boolean.valueOf(z));
    }

    public static final boolean isInlineClassUnboxing(HasMetadata hasMetadata) {
        Intrinsics.checkNotNullParameter(hasMetadata, "<this>");
        return ((Boolean) isInlineClassUnboxing$delegate.getValue(hasMetadata, $$delegatedProperties[27])).booleanValue();
    }

    public static final void setInlineClassUnboxing(HasMetadata hasMetadata, boolean z) {
        Intrinsics.checkNotNullParameter(hasMetadata, "<this>");
        isInlineClassUnboxing$delegate.setValue(hasMetadata, $$delegatedProperties[27], Boolean.valueOf(z));
    }

    public static final SideEffectKind getSideEffects(HasMetadata hasMetadata) {
        Intrinsics.checkNotNullParameter(hasMetadata, "<this>");
        return (SideEffectKind) sideEffects$delegate.getValue(hasMetadata, $$delegatedProperties[28]);
    }

    public static final void setSideEffects(HasMetadata hasMetadata, SideEffectKind sideEffectKind) {
        Intrinsics.checkNotNullParameter(hasMetadata, "<this>");
        Intrinsics.checkNotNullParameter(sideEffectKind, "<set-?>");
        sideEffects$delegate.setValue(hasMetadata, $$delegatedProperties[28], sideEffectKind);
    }

    public static final boolean isSuspend(JsExpression jsExpression) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        return ((Boolean) isSuspend$delegate.getValue(jsExpression, $$delegatedProperties[29])).booleanValue();
    }

    public static final void setSuspend(JsExpression jsExpression, boolean z) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        isSuspend$delegate.setValue(jsExpression, $$delegatedProperties[29], Boolean.valueOf(z));
    }

    public static final boolean getCoroutineResult(JsNameRef jsNameRef) {
        Intrinsics.checkNotNullParameter(jsNameRef, "<this>");
        return ((Boolean) coroutineResult$delegate.getValue(jsNameRef, $$delegatedProperties[30])).booleanValue();
    }

    public static final void setCoroutineResult(JsNameRef jsNameRef, boolean z) {
        Intrinsics.checkNotNullParameter(jsNameRef, "<this>");
        coroutineResult$delegate.setValue(jsNameRef, $$delegatedProperties[30], Boolean.valueOf(z));
    }

    public static final boolean getCoroutineController(JsNameRef jsNameRef) {
        Intrinsics.checkNotNullParameter(jsNameRef, "<this>");
        return ((Boolean) coroutineController$delegate.getValue(jsNameRef, $$delegatedProperties[31])).booleanValue();
    }

    public static final void setCoroutineController(JsNameRef jsNameRef, boolean z) {
        Intrinsics.checkNotNullParameter(jsNameRef, "<this>");
        coroutineController$delegate.setValue(jsNameRef, $$delegatedProperties[31], Boolean.valueOf(z));
    }

    public static final boolean getCoroutineReceiver(JsNameRef jsNameRef) {
        Intrinsics.checkNotNullParameter(jsNameRef, "<this>");
        return ((Boolean) coroutineReceiver$delegate.getValue(jsNameRef, $$delegatedProperties[32])).booleanValue();
    }

    public static final void setCoroutineReceiver(JsNameRef jsNameRef, boolean z) {
        Intrinsics.checkNotNullParameter(jsNameRef, "<this>");
        coroutineReceiver$delegate.setValue(jsNameRef, $$delegatedProperties[32], Boolean.valueOf(z));
    }

    public static final boolean getForceStateMachine(JsFunction jsFunction) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        return ((Boolean) forceStateMachine$delegate.getValue(jsFunction, $$delegatedProperties[33])).booleanValue();
    }

    public static final void setForceStateMachine(JsFunction jsFunction, boolean z) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        forceStateMachine$delegate.setValue(jsFunction, $$delegatedProperties[33], Boolean.valueOf(z));
    }

    public static final boolean isInlineableCoroutineBody(JsFunction jsFunction) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        return ((Boolean) isInlineableCoroutineBody$delegate.getValue(jsFunction, $$delegatedProperties[34])).booleanValue();
    }

    public static final void setInlineableCoroutineBody(JsFunction jsFunction, boolean z) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        isInlineableCoroutineBody$delegate.setValue(jsFunction, $$delegatedProperties[34], Boolean.valueOf(z));
    }

    public static final boolean getImported(JsName jsName) {
        Intrinsics.checkNotNullParameter(jsName, "<this>");
        return ((Boolean) imported$delegate.getValue(jsName, $$delegatedProperties[35])).booleanValue();
    }

    public static final void setImported(JsName jsName, boolean z) {
        Intrinsics.checkNotNullParameter(jsName, "<this>");
        imported$delegate.setValue(jsName, $$delegatedProperties[35], Boolean.valueOf(z));
    }

    public static final CoroutineMetadata getCoroutineMetadata(JsFunction jsFunction) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        return (CoroutineMetadata) coroutineMetadata$delegate.getValue(jsFunction, $$delegatedProperties[36]);
    }

    public static final void setCoroutineMetadata(JsFunction jsFunction, CoroutineMetadata coroutineMetadata) {
        Intrinsics.checkNotNullParameter(jsFunction, "<this>");
        coroutineMetadata$delegate.setValue(jsFunction, $$delegatedProperties[36], coroutineMetadata);
    }

    public static final Pair<RangeType, RangeKind> getRange(JsExpression jsExpression) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        return (Pair) range$delegate.getValue(jsExpression, $$delegatedProperties[37]);
    }

    public static final void setRange(JsExpression jsExpression, Pair<? extends RangeType, ? extends RangeKind> pair) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        range$delegate.setValue(jsExpression, $$delegatedProperties[37], pair);
    }

    public static final JsExpression getPrimitiveKClass(JsExpression jsExpression) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        return (JsExpression) primitiveKClass$delegate.getValue(jsExpression, $$delegatedProperties[38]);
    }

    public static final void setPrimitiveKClass(JsExpression jsExpression, JsExpression jsExpression2) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        primitiveKClass$delegate.setValue(jsExpression, $$delegatedProperties[38], jsExpression2);
    }

    public static final JsExpression getKType(JsExpression jsExpression) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        return (JsExpression) kType$delegate.getValue(jsExpression, $$delegatedProperties[39]);
    }

    public static final void setKType(JsExpression jsExpression, JsExpression jsExpression2) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        kType$delegate.setValue(jsExpression, $$delegatedProperties[39], jsExpression2);
    }

    public static final boolean getKTypeWithRecursion(JsExpression jsExpression) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        return ((Boolean) kTypeWithRecursion$delegate.getValue(jsExpression, $$delegatedProperties[40])).booleanValue();
    }

    public static final void setKTypeWithRecursion(JsExpression jsExpression, boolean z) {
        Intrinsics.checkNotNullParameter(jsExpression, "<this>");
        kTypeWithRecursion$delegate.setValue(jsExpression, $$delegatedProperties[40], Boolean.valueOf(z));
    }
}
