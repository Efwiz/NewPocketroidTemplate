package org.jetbrains.kotlin.cli.common.arguments;

import java.util.Collection;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.ClassFileVersion;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.config.AnalysisFlag;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;

/* JADX INFO: compiled from: K2NativeCompilerArguments.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000S\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0003\b\u0080\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 ±\u00022\u00020\u0001:\u0002±\u0002B\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010£\u0002\u001a\u00030¤\u00022\b\u0010¥\u0002\u001a\u00030¦\u00022\b\u0010§\u0002\u001a\u00030¨\u0002H\u0014J0\u0010©\u0002\u001a\u0015\u0012\t\u0012\u0007\u0012\u0002\b\u00030«\u0002\u0012\u0005\u0012\u00030¬\u00020ª\u00022\b\u0010§\u0002\u001a\u00030¨\u00022\b\u0010\u00ad\u0002\u001a\u00030®\u0002H\u0016J\n\u0010¯\u0002\u001a\u00030°\u0002H\u0014R \u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR \u0010\t\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR(\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001e\u0010\u0013\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0006\"\u0004\b\u0015\u0010\bR(\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\b\u0017\u0010\u000f\"\u0004\b\u0018\u0010\u0011R \u0010\u0019\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0006\"\u0004\b\u001b\u0010\bR(\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\b\u001d\u0010\u000f\"\u0004\b\u001e\u0010\u0011R(\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\b \u0010\u000f\"\u0004\b!\u0010\u0011R\u001e\u0010\"\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R\u001e\u0010(\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010%\"\u0004\b*\u0010'R(\u0010+\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\b,\u0010\u000f\"\u0004\b-\u0010\u0011R \u0010.\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u0006\"\u0004\b0\u0010\bR\u001e\u00101\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010%\"\u0004\b3\u0010'R \u00104\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u0010\u0006\"\u0004\b6\u0010\bR(\u00107\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\b8\u0010\u000f\"\u0004\b9\u0010\u0011R\u001e\u0010:\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010%\"\u0004\b<\u0010'R\u001e\u0010=\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b>\u0010\u0006\"\u0004\b?\u0010\bR(\u0010@\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\bA\u0010\u000f\"\u0004\bB\u0010\u0011R \u0010C\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bD\u0010\u0006\"\u0004\bE\u0010\bR\u001e\u0010F\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010%\"\u0004\bH\u0010'R\u001e\u0010I\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010%\"\u0004\bK\u0010'R \u0010L\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bM\u0010\u0006\"\u0004\bN\u0010\bR\u001e\u0010O\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bP\u0010%\"\u0004\bQ\u0010'R\u001e\u0010R\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bS\u0010%\"\u0004\bT\u0010'R(\u0010U\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\bV\u0010\u000f\"\u0004\bW\u0010\u0011R \u0010X\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010\u0006\"\u0004\bZ\u0010\bR\u001e\u0010[\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\\\u0010%\"\u0004\b]\u0010'R(\u0010^\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\b_\u0010\u000f\"\u0004\b`\u0010\u0011R(\u0010a\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\bb\u0010\u000f\"\u0004\bc\u0010\u0011R \u0010d\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\be\u0010\u0006\"\u0004\bf\u0010\bR \u0010g\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bh\u0010\u0006\"\u0004\bi\u0010\bR \u0010j\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bk\u0010\u0006\"\u0004\bl\u0010\bR\u001e\u0010m\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bn\u0010%\"\u0004\bo\u0010'R\u001e\u0010p\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bq\u0010%\"\u0004\br\u0010'R\u001e\u0010s\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bt\u0010%\"\u0004\bu\u0010'R(\u0010v\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\bw\u0010\u000f\"\u0004\bx\u0010\u0011R(\u0010y\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0012\u001a\u0004\bz\u0010\u000f\"\u0004\b{\u0010\u0011R \u0010|\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b}\u0010\u0006\"\u0004\b~\u0010\bR\"\u0010\u007f\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0080\u0001\u0010\u0006\"\u0005\b\u0081\u0001\u0010\bR#\u0010\u0082\u0001\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0083\u0001\u0010\u0006\"\u0005\b\u0084\u0001\u0010\bR+\u0010\u0085\u0001\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0012\n\u0002\u0010\u0012\u001a\u0005\b\u0086\u0001\u0010\u000f\"\u0005\b\u0087\u0001\u0010\u0011R#\u0010\u0088\u0001\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0089\u0001\u0010\u0006\"\u0005\b\u008a\u0001\u0010\bR#\u0010\u008b\u0001\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u008c\u0001\u0010\u0006\"\u0005\b\u008d\u0001\u0010\bR!\u0010\u008e\u0001\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u008f\u0001\u0010%\"\u0005\b\u0090\u0001\u0010'R#\u0010\u0091\u0001\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0092\u0001\u0010\u0006\"\u0005\b\u0093\u0001\u0010\bR+\u0010\u0094\u0001\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0012\n\u0002\u0010\u0012\u001a\u0005\b\u0095\u0001\u0010\u000f\"\u0005\b\u0096\u0001\u0010\u0011R!\u0010\u0097\u0001\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0098\u0001\u0010%\"\u0005\b\u0099\u0001\u0010'R#\u0010\u009a\u0001\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u009b\u0001\u0010\u0006\"\u0005\b\u009c\u0001\u0010\bR#\u0010\u009d\u0001\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u009e\u0001\u0010\u0006\"\u0005\b\u009f\u0001\u0010\bR!\u0010 \u0001\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b¡\u0001\u0010%\"\u0005\b¢\u0001\u0010'R#\u0010£\u0001\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b¤\u0001\u0010\u0006\"\u0005\b¥\u0001\u0010\bR#\u0010¦\u0001\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b§\u0001\u0010\u0006\"\u0005\b¨\u0001\u0010\bR!\u0010©\u0001\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bª\u0001\u0010%\"\u0005\b«\u0001\u0010'R*\u0010¬\u0001\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0017\n\u0000\u0012\u0005\b\u00ad\u0001\u0010\u0002\u001a\u0005\b®\u0001\u0010\u0006\"\u0005\b¯\u0001\u0010\bR+\u0010°\u0001\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0012\n\u0002\u0010\u0012\u001a\u0005\b±\u0001\u0010\u000f\"\u0005\b²\u0001\u0010\u0011R!\u0010³\u0001\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b´\u0001\u0010%\"\u0005\bµ\u0001\u0010'R!\u0010¶\u0001\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b·\u0001\u0010%\"\u0005\b¸\u0001\u0010'R!\u0010¹\u0001\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bº\u0001\u0010%\"\u0005\b»\u0001\u0010'R!\u0010¼\u0001\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b½\u0001\u0010%\"\u0005\b¾\u0001\u0010'R!\u0010¿\u0001\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÀ\u0001\u0010%\"\u0005\bÁ\u0001\u0010'R!\u0010Â\u0001\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÃ\u0001\u0010%\"\u0005\bÄ\u0001\u0010'R!\u0010Å\u0001\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÆ\u0001\u0010%\"\u0005\bÇ\u0001\u0010'R!\u0010È\u0001\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÉ\u0001\u0010%\"\u0005\bÊ\u0001\u0010'R#\u0010Ë\u0001\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÌ\u0001\u0010\u0006\"\u0005\bÍ\u0001\u0010\bR+\u0010Î\u0001\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0012\n\u0002\u0010\u0012\u001a\u0005\bÏ\u0001\u0010\u000f\"\u0005\bÐ\u0001\u0010\u0011R.\u0010Ò\u0001\u001a\u0004\u0018\u00010\u00042\t\u0010Ñ\u0001\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÓ\u0001\u0010\u0006\"\u0005\bÔ\u0001\u0010\bR.\u0010Õ\u0001\u001a\u0004\u0018\u00010\u00042\t\u0010Ñ\u0001\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÖ\u0001\u0010\u0006\"\u0005\b×\u0001\u0010\bR#\u0010Ø\u0001\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÙ\u0001\u0010\u0006\"\u0005\bÚ\u0001\u0010\bR!\u0010Û\u0001\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÜ\u0001\u0010%\"\u0005\bÝ\u0001\u0010'R!\u0010Þ\u0001\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bß\u0001\u0010%\"\u0005\bà\u0001\u0010'R!\u0010á\u0001\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bâ\u0001\u0010%\"\u0005\bã\u0001\u0010'R#\u0010ä\u0001\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bå\u0001\u0010\u0006\"\u0005\bæ\u0001\u0010\bR#\u0010ç\u0001\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bè\u0001\u0010\u0006\"\u0005\bé\u0001\u0010\bR!\u0010ê\u0001\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bë\u0001\u0010%\"\u0005\bì\u0001\u0010'R+\u0010í\u0001\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0012\n\u0002\u0010\u0012\u001a\u0005\bî\u0001\u0010\u000f\"\u0005\bï\u0001\u0010\u0011R+\u0010ð\u0001\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0012\n\u0002\u0010\u0012\u001a\u0005\bñ\u0001\u0010\u000f\"\u0005\bò\u0001\u0010\u0011R#\u0010ó\u0001\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bô\u0001\u0010\u0006\"\u0005\bõ\u0001\u0010\bR#\u0010ö\u0001\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b÷\u0001\u0010\u0006\"\u0005\bø\u0001\u0010\bR#\u0010ù\u0001\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bú\u0001\u0010\u0006\"\u0005\bû\u0001\u0010\bR)\u0010ü\u0001\u001a\b\u0012\u0004\u0012\u00020\u00040\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0012\n\u0002\u0010\u0012\u001a\u0005\bý\u0001\u0010\u000f\"\u0005\bþ\u0001\u0010\u0011R#\u0010ÿ\u0001\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0080\u0002\u0010\u0006\"\u0005\b\u0081\u0002\u0010\bR#\u0010\u0082\u0002\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0083\u0002\u0010\u0006\"\u0005\b\u0084\u0002\u0010\bR#\u0010\u0085\u0002\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0086\u0002\u0010\u0006\"\u0005\b\u0087\u0002\u0010\bR+\u0010\u0088\u0002\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\r8\u0006@\u0006X\u0087\u000e¢\u0006\u0012\n\u0002\u0010\u0012\u001a\u0005\b\u0089\u0002\u0010\u000f\"\u0005\b\u008a\u0002\u0010\u0011R!\u0010\u008b\u0002\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u008c\u0002\u0010%\"\u0005\b\u008d\u0002\u0010'R#\u0010\u008e\u0002\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u008f\u0002\u0010\u0006\"\u0005\b\u0090\u0002\u0010\bR#\u0010\u0091\u0002\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0092\u0002\u0010\u0006\"\u0005\b\u0093\u0002\u0010\bR#\u0010\u0094\u0002\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0095\u0002\u0010\u0006\"\u0005\b\u0096\u0002\u0010\bR!\u0010\u0097\u0002\u001a\u00020#8\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0098\u0002\u0010%\"\u0005\b\u0099\u0002\u0010'R#\u0010\u009a\u0002\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u009b\u0002\u0010\u0006\"\u0005\b\u009c\u0002\u0010\bR#\u0010\u009d\u0002\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u009e\u0002\u0010\u0006\"\u0005\b\u009f\u0002\u0010\bR#\u0010 \u0002\u001a\u0004\u0018\u00010\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b¡\u0002\u0010\u0006\"\u0005\b¢\u0002\u0010\b¨\u0006²\u0002"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/K2NativeCompilerArguments;", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "()V", "allocator", "", "getAllocator", "()Ljava/lang/String;", "setAllocator", "(Ljava/lang/String;)V", "autoCacheDir", "getAutoCacheDir", "setAutoCacheDir", "autoCacheableFrom", "", "getAutoCacheableFrom", "()[Ljava/lang/String;", "setAutoCacheableFrom", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "backendThreads", "getBackendThreads", "setBackendThreads", "binaryOptions", "getBinaryOptions", "setBinaryOptions", "bundleId", "getBundleId", "setBundleId", "cacheDirectories", "getCacheDirectories", "setCacheDirectories", "cachedLibraries", "getCachedLibraries", "setCachedLibraries", "checkDependencies", "", "getCheckDependencies", "()Z", "setCheckDependencies", "(Z)V", "checkExternalCalls", "getCheckExternalCalls", "setCheckExternalCalls", "clangOptions", "getClangOptions", "setClangOptions", "compileFromBitcode", "getCompileFromBitcode", "setCompileFromBitcode", "coverage", "getCoverage", "setCoverage", "coverageFile", "getCoverageFile", "setCoverageFile", "coveredLibraries", "getCoveredLibraries", "setCoveredLibraries", "debug", "getDebug", "setDebug", "debugInfoFormatVersion", "getDebugInfoFormatVersion", "setDebugInfoFormatVersion", "debugPrefixMap", "getDebugPrefixMap", "setDebugPrefixMap", "destroyRuntimeMode", "getDestroyRuntimeMode", "setDestroyRuntimeMode", "embedBitcode", "getEmbedBitcode", "setEmbedBitcode", "embedBitcodeMarker", "getEmbedBitcodeMarker", "setEmbedBitcodeMarker", "emitLazyObjCHeader", "getEmitLazyObjCHeader", "setEmitLazyObjCHeader", "enableAssertions", "getEnableAssertions", "setEnableAssertions", "exportKDoc", "getExportKDoc", "setExportKDoc", "exportedLibraries", "getExportedLibraries", "setExportedLibraries", "externalDependencies", "getExternalDependencies", "setExternalDependencies", "fakeOverrideValidator", "getFakeOverrideValidator", "setFakeOverrideValidator", "filesToCache", "getFilesToCache", "setFilesToCache", "frameworkImportHeaders", "getFrameworkImportHeaders", "setFrameworkImportHeaders", "friendModules", "getFriendModules", "setFriendModules", ConfigConstants.CONFIG_GC_SECTION, "getGc", "setGc", "generateDebugTrampolineString", "getGenerateDebugTrampolineString", "setGenerateDebugTrampolineString", "generateNoExitTestRunner", "getGenerateNoExitTestRunner", "setGenerateNoExitTestRunner", "generateTestRunner", "getGenerateTestRunner", "setGenerateTestRunner", "generateWorkerTestRunner", "getGenerateWorkerTestRunner", "setGenerateWorkerTestRunner", "includeBinaries", "getIncludeBinaries", "setIncludeBinaries", "includes", "getIncludes", "setIncludes", "incrementalCacheDir", "getIncrementalCacheDir", "setIncrementalCacheDir", "konanDataDir", "getKonanDataDir", "setKonanDataDir", "lazyIrForCaches", "getLazyIrForCaches", "setLazyIrForCaches", "libraries", "getLibraries", "setLibraries", "libraryToAddToCache", "getLibraryToAddToCache", "setLibraryToAddToCache", "libraryVersion", "getLibraryVersion", "setLibraryVersion", "lightDebugDeprecated", "getLightDebugDeprecated", "setLightDebugDeprecated", "lightDebugString", "getLightDebugString", "setLightDebugString", "linkerArguments", "getLinkerArguments", "setLinkerArguments", "listTargets", "getListTargets", "setListTargets", "llvmVariant", "getLlvmVariant", "setLlvmVariant", "mainPackage", "getMainPackage", "setMainPackage", "makePerFileCache", "getMakePerFileCache", "setMakePerFileCache", "manifestFile", "getManifestFile", "setManifestFile", "memoryModel", "getMemoryModel", "setMemoryModel", "metadataKlib", "getMetadataKlib", "setMetadataKlib", "moduleName", "getModuleName$annotations", "getModuleName", "setModuleName", "nativeLibraries", "getNativeLibraries", "setNativeLibraries", "noObjcGenerics", "getNoObjcGenerics", "setNoObjcGenerics", "nodefaultlibs", "getNodefaultlibs", "setNodefaultlibs", "noendorsedlibs", "getNoendorsedlibs", "setNoendorsedlibs", "nomain", "getNomain", "setNomain", "nopack", "getNopack", "setNopack", "nostdlib", "getNostdlib", "setNostdlib", "omitFrameworkBinary", "getOmitFrameworkBinary", "setOmitFrameworkBinary", "optimization", "getOptimization", "setOptimization", "outputName", "getOutputName", "setOutputName", "overrideKonanProperties", "getOverrideKonanProperties", "setOverrideKonanProperties", "value", "partialLinkageLogLevel", "getPartialLinkageLogLevel", "setPartialLinkageLogLevel", "partialLinkageMode", "getPartialLinkageMode", "setPartialLinkageMode", "preLinkCaches", "getPreLinkCaches", "setPreLinkCaches", "printBitCode", "getPrintBitCode", "setPrintBitCode", "printFiles", "getPrintFiles", "setPrintFiles", "printIr", "getPrintIr", "setPrintIr", "produce", "getProduce", "setProduce", "propertyLazyInitialization", "getPropertyLazyInitialization", "setPropertyLazyInitialization", "purgeUserLibs", "getPurgeUserLibs", "setPurgeUserLibs", "refinesPaths", "getRefinesPaths", "setRefinesPaths", "repositories", "getRepositories", "setRepositories", "runtimeFile", "getRuntimeFile", "setRuntimeFile", "runtimeLogs", "getRuntimeLogs", "setRuntimeLogs", "saveDependenciesPath", "getSaveDependenciesPath", "setSaveDependenciesPath", "saveLlvmIrAfter", "getSaveLlvmIrAfter", "setSaveLlvmIrAfter", "saveLlvmIrDirectory", "getSaveLlvmIrDirectory", "setSaveLlvmIrDirectory", "serializedDependencies", "getSerializedDependencies", "setSerializedDependencies", "shortModuleName", "getShortModuleName", "setShortModuleName", "singleLinkerArguments", "getSingleLinkerArguments", "setSingleLinkerArguments", "staticFramework", "getStaticFramework", "setStaticFramework", "target", "getTarget", "setTarget", "temporaryFilesDir", "getTemporaryFilesDir", "setTemporaryFilesDir", "testDumpOutputPath", "getTestDumpOutputPath", "setTestDumpOutputPath", "verifyBitCode", "getVerifyBitCode", "setVerifyBitCode", "verifyCompiler", "getVerifyCompiler", "setVerifyCompiler", "verifyIr", "getVerifyIr", "setVerifyIr", "workerExceptionHandling", "getWorkerExceptionHandling", "setWorkerExceptionHandling", "checkIrSupport", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "collector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "configureAnalysisFlags", "", "Lorg/jetbrains/kotlin/config/AnalysisFlag;", "", "languageVersion", "Lorg/jetbrains/kotlin/config/LanguageVersion;", "copyOf", "Lorg/jetbrains/kotlin/cli/common/arguments/Freezable;", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class K2NativeCompilerArguments extends CommonCompilerArguments {
    public static final String ADD_CACHE = "-Xadd-cache";
    public static final String CACHED_LIBRARY = "-Xcached-library";
    public static final String EMBED_BITCODE_FLAG = "-Xembed-bitcode";
    public static final String EMBED_BITCODE_MARKER_FLAG = "-Xembed-bitcode-marker";
    public static final String INCLUDE_ARG = "-Xinclude";
    public static final String INCREMENTAL_CACHE_DIR = "-Xic-cache-dir";
    public static final String SHORT_MODULE_NAME_ARG = "-Xshort-module-name";
    public static final String STATIC_FRAMEWORK_FLAG = "-Xstatic-framework";

    @Argument(description = "Allocator used in runtime", value = "-Xallocator", valueDescription = "std | mimalloc | custom")
    private String allocator;

    @Argument(delimiter = "", description = "Path to the directory where to put caches for auto-cacheable dependencies", value = "-Xauto-cache-dir", valueDescription = "<path>")
    private String autoCacheDir;

    @Argument(delimiter = "", description = "Path to the root directory from which dependencies are to be cached automatically.\nBy default caches will be placed into the kotlin-native system cache directory.", value = "-Xauto-cache-from", valueDescription = "<path>")
    private String[] autoCacheableFrom;

    @Argument(description = "Specify binary option", value = "-Xbinary", valueDescription = "<option=value>")
    private String[] binaryOptions;

    @Argument(description = "Bundle ID to be set in Info.plist of a produced framework. Deprecated. Please use -Xbinary=bundleId=<id>.", value = "-Xbundle-id", valueDescription = "<id>")
    private String bundleId;

    @Argument(delimiter = "", description = "Path to the directory containing caches", value = "-Xcache-directory", valueDescription = "<path>")
    private String[] cacheDirectories;

    @Argument(delimiter = "", description = "Comma-separated paths of a library and its cache", value = CACHED_LIBRARY, valueDescription = "<library path>,<cache path>")
    private String[] cachedLibraries;

    @Argument(deprecatedName = "--check_dependencies", description = "Check dependencies and download the missing ones", value = "-Xcheck-dependencies")
    private boolean checkDependencies;

    @Argument(description = "Check all calls of possibly long external functions are done in Native state", value = "-Xcheck-state-at-external-calls")
    private boolean checkExternalCalls;

    @Argument(description = "Explicit list of Clang options", value = "-Xoverride-clang-options", valueDescription = "<arg1,arg2,...>")
    private String[] clangOptions;

    @Argument(description = "Continue compilation from bitcode file", value = "-Xcompile-from-bitcode", valueDescription = "<path>")
    private String compileFromBitcode;

    @Argument(description = "emit coverage", value = "-Xcoverage")
    private boolean coverage;

    @Argument(description = "Save coverage information to the given file", value = "-Xcoverage-file", valueDescription = "<path>")
    private String coverageFile;

    @Argument(delimiter = "", description = "Provide code coverage for the given library.\nMust be one of libraries passed with '-library'", value = "-Xlibrary-to-cover", valueDescription = "<path>")
    private String[] coveredLibraries;

    @Argument(description = "Enable emitting debug information", value = "-g")
    private boolean debug;

    @Argument(description = "Remap file source directory paths in debug info", value = "-Xdebug-prefix-map", valueDescription = "<old1=new1,old2=new2,...>")
    private String[] debugPrefixMap;

    @Argument(description = "When to destroy runtime. 'legacy' and 'on-shutdown' are currently supported. NOTE: 'legacy' mode is deprecated and will be removed.", value = "-Xdestroy-runtime-mode", valueDescription = "<mode>")
    private String destroyRuntimeMode;

    @Argument(description = "Embed LLVM IR bitcode as data", value = EMBED_BITCODE_FLAG)
    private boolean embedBitcode;

    @Argument(description = "Embed placeholder LLVM IR data as a marker", value = EMBED_BITCODE_MARKER_FLAG)
    private boolean embedBitcodeMarker;

    @Argument(description = "", value = "-Xemit-lazy-objc-header")
    private String emitLazyObjCHeader;

    @Argument(deprecatedName = "-enable_assertions", description = "Enable runtime assertions in generated code", shortName = ClassFileVersion.VersionLocator.EARLY_ACCESS, value = "-enable-assertions")
    private boolean enableAssertions;

    @Argument(description = "Export KDoc in framework header", value = "-Xexport-kdoc")
    private boolean exportKDoc;

    @Argument(delimiter = "", description = "A library to be included into produced framework API.\nMust be one of libraries passed with '-library'", value = "-Xexport-library", valueDescription = "<path>")
    private String[] exportedLibraries;

    @Argument(description = "Path to the file containing external dependencies.\nExternal dependencies are required for verbose output in case of IR linker errors,\nbut they do not affect compilation at all.", value = "-Xexternal-dependencies", valueDescription = "<path>")
    private String externalDependencies;

    @Argument(description = "Enable IR fake override validator", value = "-Xfake-override-validator")
    private boolean fakeOverrideValidator;

    @Argument(delimiter = "", description = "Path to file to cache", value = "-Xfile-to-cache", valueDescription = "<path>")
    private String[] filesToCache;

    @Argument(description = "Add additional header import to framework header", value = "-Xframework-import-header", valueDescription = "<header>")
    private String[] frameworkImportHeaders;

    @Argument(description = "Paths to friend modules", value = "-friend-modules", valueDescription = "<path>")
    private String friendModules;

    @Argument(description = "GC to use, 'noop', 'stms' and 'cms' are currently supported. Works only with -memory-model experimental", value = "-Xgc", valueDescription = "<gc>")
    private String gc;

    @Argument(description = "generates trampolines to make debugger breakpoint resolution more accurate (inlines, when, etc.)", value = "-Xg-generate-debug-trampoline", valueDescription = "{disable|enable}")
    private String generateDebugTrampolineString;

    @Argument(description = "Produce a runner for unit tests not forcing exit", shortName = "-trn", value = "-generate-no-exit-test-runner")
    private boolean generateNoExitTestRunner;

    @Argument(deprecatedName = "-generate_test_runner", description = "Produce a runner for unit tests", shortName = "-tr", value = "-generate-test-runner")
    private boolean generateTestRunner;

    @Argument(description = "Produce a worker runner for unit tests", shortName = "-trw", value = "-generate-worker-test-runner")
    private boolean generateWorkerTestRunner;

    @Argument(deprecatedName = "-includeBinary", description = "Pack external binary within the klib", shortName = "-ib", value = "-include-binary", valueDescription = "<path>")
    private String[] includeBinaries;

    @Argument(description = "A path to an intermediate library that should be processed in the same manner as source files", value = INCLUDE_ARG, valueDescription = "<path>")
    private String[] includes;

    @Argument(delimiter = "", description = "Path to the directory where to put incremental build caches", value = INCREMENTAL_CACHE_DIR, valueDescription = "<path>")
    private String incrementalCacheDir;

    @Argument(description = "Custom path for konan distributions location", value = "-Xkonan-data-dir")
    private String konanDataDir;

    @Argument(description = "Use lazy IR for cached libraries", value = "-Xlazy-ir-for-caches", valueDescription = "{disable|enable}")
    private String lazyIrForCaches;

    @Argument(delimiter = "", description = "Link with the library", shortName = "-l", value = "-library", valueDescription = "<path>")
    private String[] libraries;

    @Argument(delimiter = "", description = "Path to the library to be added to cache", value = ADD_CACHE, valueDescription = "<path>")
    private String libraryToAddToCache;

    @Argument(description = "Set library version", shortName = "-lv", value = "-library-version", valueDescription = "<version>")
    private String libraryVersion;

    @Argument(description = "Add light debug information. Deprecated option. Please use instead -Xadd-light-debug=enable", value = "-Xg0")
    private boolean lightDebugDeprecated;

    @Argument(description = "Add light debug information for optimized builds. This option is skipped in debug builds.\nIt's enabled by default on Darwin platforms where collected debug information is stored in .dSYM file.\nCurrently option is disabled by default on other platforms.", value = "-Xadd-light-debug", valueDescription = "{disable|enable}")
    private String lightDebugString;

    @Argument(delimiter = " ", deprecatedName = "-linkerOpts", description = "Pass arguments to linker", value = "-linker-options", valueDescription = "<arg>")
    private String[] linkerArguments;

    @Argument(deprecatedName = "-list_targets", description = "List available hardware targets", value = "-list-targets")
    private boolean listTargets;

    @Argument(description = "Choose LLVM distribution which will be used during compilation.", value = "-Xllvm-variant", valueDescription = "{dev|user|absolute path to llvm}")
    private String llvmVariant;

    @Argument(description = "Qualified entry point name", shortName = "-e", value = "-entry", valueDescription = "<name>")
    private String mainPackage;

    @Argument(description = "Force compiler to produce per-file cache", value = "-Xmake-per-file-cache")
    private boolean makePerFileCache;

    @Argument(description = "Provide a maniferst addend file", value = "-manifest", valueDescription = "<path>")
    private String manifestFile;

    @Argument(description = "Memory model to use, 'strict' and 'experimental' are currently supported", value = "-memory-model", valueDescription = "<model>")
    private String memoryModel;

    @Argument(description = "Produce a klib that only contains the declarations metadata", value = "-Xmetadata-klib")
    private boolean metadataKlib;

    @Argument(deprecatedName = "-module_name", description = "Specify a name for the compilation module", value = "-module-name", valueDescription = "<name>")
    private String moduleName;

    @Argument(delimiter = "", deprecatedName = "-nativelibrary", description = "Include the native bitcode library", shortName = "-nl", value = "-native-library", valueDescription = "<path>")
    private String[] nativeLibraries;

    @Argument(description = "Disable generics support for framework header", value = "-Xno-objc-generics")
    private boolean noObjcGenerics;

    @Argument(deprecatedName = "-nodefaultlibs", description = "Don't link the libraries from dist/klib automatically", value = "-no-default-libs")
    private boolean nodefaultlibs;

    @Argument(description = "Don't link the endorsed libraries from dist automatically. Deprecated option: the dist has no endorsed libraries anymore.", value = "-no-endorsed-libs")
    private boolean noendorsedlibs;

    @Argument(description = "Assume 'main' entry point to be provided by external libraries", value = "-nomain")
    private boolean nomain;

    @Argument(description = "Don't pack the library into a klib file", value = "-nopack")
    private boolean nopack;

    @Argument(description = "Don't link with stdlib", value = "-nostdlib")
    private boolean nostdlib;

    @Argument(description = "Omit binary when compiling framework", value = "-Xomit-framework-binary")
    private boolean omitFrameworkBinary;

    @Argument(description = "Enable optimizations during compilation", value = "-opt")
    private boolean optimization;

    @Argument(description = "Output name", shortName = "-o", value = "-output", valueDescription = "<name>")
    private String outputName;

    @Argument(delimiter = ";", description = "Override konan.properties.values", value = "-Xoverride-konan-properties", valueDescription = "key1=value1;key2=value2;...")
    private String[] overrideKonanProperties;

    @Argument(description = "Partial linkage compile-time log level", value = "-Xpartial-linkage-loglevel", valueDescription = "{info|warning|error}")
    private String partialLinkageLogLevel;

    @Argument(description = "Use partial linkage mode", value = "-Xpartial-linkage", valueDescription = "{enable|disable}")
    private String partialLinkageMode;

    @Argument(description = "Perform caches pre-link", value = "-Xpre-link-caches", valueDescription = "{disable|enable}")
    private String preLinkCaches;

    @Argument(deprecatedName = "--print_bitcode", description = "Print llvm bitcode", value = "-Xprint-bitcode")
    private boolean printBitCode;

    @Argument(description = "Print files", value = "-Xprint-files")
    private boolean printFiles;

    @Argument(deprecatedName = "--print_ir", description = "Print IR", value = "-Xprint-ir")
    private boolean printIr;

    @Argument(description = "Specify output file kind", shortName = "-p", value = "-produce", valueDescription = "{program|static|dynamic|framework|library|bitcode}")
    private String produce;

    @Argument(description = "Initialize top level properties lazily per file", value = "-Xir-property-lazy-initialization", valueDescription = "{disable|enable}")
    private String propertyLazyInitialization;

    @Argument(deprecatedName = "--purge_user_libs", description = "Don't link unused libraries even explicitly specified", value = "-Xpurge-user-libs")
    private boolean purgeUserLibs;

    @Argument(description = "Paths to output directories for refined modules (whose expects this module can actualize)", value = "-Xrefines-paths", valueDescription = "<path>")
    private String[] refinesPaths;

    @Argument(description = "Library search path", shortName = "-r", value = "-repo", valueDescription = "<path>")
    private String[] repositories;

    @Argument(deprecatedName = "--runtime", description = "Override standard 'runtime.bc' location", value = "-Xruntime", valueDescription = "<path>")
    private String runtimeFile;

    @Argument(description = "Enable logging for runtime with tags.", value = "-Xruntime-logs", valueDescription = "<tag1=level1,tag2=level2,...>")
    private String runtimeLogs;

    @Argument(description = "Path for writing backend dependencies", value = "-Xwrite-dependencies-to")
    private String saveDependenciesPath;

    @Argument(description = "Directory that should contain results of -Xsave-llvm-ir-after=<phase>", value = "-Xsave-llvm-ir-directory")
    private String saveLlvmIrDirectory;

    @Argument(description = "Serialized dependencies to use for linking", value = "-Xread-dependencies-from", valueDescription = "<path>")
    private String serializedDependencies;

    @Argument(description = "A short name used to denote this library in the IDE and in a generated Objective-C header", value = SHORT_MODULE_NAME_ARG, valueDescription = "<name>")
    private String shortModuleName;

    @Argument(delimiter = "", description = "Pass argument to linker", value = "-linker-option", valueDescription = "<arg>")
    private String[] singleLinkerArguments;

    @Argument(description = "Create a framework with a static library instead of a dynamic one", value = STATIC_FRAMEWORK_FLAG)
    private boolean staticFramework;

    @Argument(description = "Set hardware target", value = "-target", valueDescription = "<target>")
    private String target;

    @Argument(deprecatedName = "--temporary_files_dir", description = "Save temporary files to the given directory", value = "-Xtemporary-files-dir", valueDescription = "<path>")
    private String temporaryFilesDir;

    @Argument(description = "Path to a file to dump the list of all available tests", value = "-Xdump-tests-to", valueDescription = "<path>")
    private String testDumpOutputPath;

    @Argument(deprecatedName = "--verify_bitcode", description = "Verify llvm bitcode after each method", value = "-Xverify-bitcode")
    private boolean verifyBitCode;

    @Argument(description = "Verify compiler", value = "-Xverify-compiler")
    private String verifyCompiler;

    @Argument(description = "IR verification mode (no verification by default)", value = "-Xverify-ir", valueDescription = "{none|warning|error}")
    private String verifyIr;

    @Argument(description = "Unhandled exception processing in Worker.executeAfter. Possible values: 'legacy', 'use-hook'. The default value is 'legacy', for -memory-model experimental the default value is 'use-hook'", value = "-Xworker-exception-handling", valueDescription = "<mode>")
    private String workerExceptionHandling;

    @Argument(description = "Run codegen by file in N parallel threads.\n0 means use a thread per processor core.\nDefault value is 1", value = "-Xbackend-threads", valueDescription = "<N>")
    private String backendThreads = SchemaSymbols.ATTVAL_TRUE_1;

    @Argument(description = "Save result of Kotlin IR to LLVM IR translation to -Xsave-llvm-ir-directory.", value = "-Xsave-llvm-ir-after")
    private String[] saveLlvmIrAfter = new String[0];

    @Argument(description = "generate debug info of given version (1, 2)", value = "-Xdebug-info-version")
    private String debugInfoFormatVersion = SchemaSymbols.ATTVAL_TRUE_1;

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, value = DefaultValue.STRING_NULL_DEFAULT)
    public static /* synthetic */ void getModuleName$annotations() {
    }

    public final boolean getEnableAssertions() {
        return this.enableAssertions;
    }

    public final void setEnableAssertions(boolean z) {
        this.enableAssertions = z;
    }

    public final boolean getDebug() {
        return this.debug;
    }

    public final void setDebug(boolean z) {
        this.debug = z;
    }

    public final boolean getGenerateTestRunner() {
        return this.generateTestRunner;
    }

    public final void setGenerateTestRunner(boolean z) {
        this.generateTestRunner = z;
    }

    public final boolean getGenerateWorkerTestRunner() {
        return this.generateWorkerTestRunner;
    }

    public final void setGenerateWorkerTestRunner(boolean z) {
        this.generateWorkerTestRunner = z;
    }

    public final boolean getGenerateNoExitTestRunner() {
        return this.generateNoExitTestRunner;
    }

    public final void setGenerateNoExitTestRunner(boolean z) {
        this.generateNoExitTestRunner = z;
    }

    public final String[] getIncludeBinaries() {
        return this.includeBinaries;
    }

    public final void setIncludeBinaries(String[] strArr) {
        this.includeBinaries = strArr;
    }

    public final String[] getLibraries() {
        return this.libraries;
    }

    public final void setLibraries(String[] strArr) {
        this.libraries = strArr;
    }

    public final String getLibraryVersion() {
        return this.libraryVersion;
    }

    public final void setLibraryVersion(String str) {
        this.libraryVersion = str;
    }

    public final boolean getListTargets() {
        return this.listTargets;
    }

    public final void setListTargets(boolean z) {
        this.listTargets = z;
    }

    public final String getManifestFile() {
        return this.manifestFile;
    }

    public final void setManifestFile(String str) {
        this.manifestFile = str;
    }

    public final String getMemoryModel() {
        return this.memoryModel;
    }

    public final void setMemoryModel(String str) {
        this.memoryModel = str;
    }

    public final String getModuleName() {
        return this.moduleName;
    }

    public final void setModuleName(String str) {
        this.moduleName = str;
    }

    public final String[] getNativeLibraries() {
        return this.nativeLibraries;
    }

    public final void setNativeLibraries(String[] strArr) {
        this.nativeLibraries = strArr;
    }

    public final boolean getNodefaultlibs() {
        return this.nodefaultlibs;
    }

    public final void setNodefaultlibs(boolean z) {
        this.nodefaultlibs = z;
    }

    public final boolean getNoendorsedlibs() {
        return this.noendorsedlibs;
    }

    public final void setNoendorsedlibs(boolean z) {
        this.noendorsedlibs = z;
    }

    public final boolean getNomain() {
        return this.nomain;
    }

    public final void setNomain(boolean z) {
        this.nomain = z;
    }

    public final boolean getNopack() {
        return this.nopack;
    }

    public final void setNopack(boolean z) {
        this.nopack = z;
    }

    public final String[] getLinkerArguments() {
        return this.linkerArguments;
    }

    public final void setLinkerArguments(String[] strArr) {
        this.linkerArguments = strArr;
    }

    public final String[] getSingleLinkerArguments() {
        return this.singleLinkerArguments;
    }

    public final void setSingleLinkerArguments(String[] strArr) {
        this.singleLinkerArguments = strArr;
    }

    public final boolean getNostdlib() {
        return this.nostdlib;
    }

    public final void setNostdlib(boolean z) {
        this.nostdlib = z;
    }

    public final boolean getOptimization() {
        return this.optimization;
    }

    public final void setOptimization(boolean z) {
        this.optimization = z;
    }

    public final String getOutputName() {
        return this.outputName;
    }

    public final void setOutputName(String str) {
        this.outputName = str;
    }

    public final String getMainPackage() {
        return this.mainPackage;
    }

    public final void setMainPackage(String str) {
        this.mainPackage = str;
    }

    public final String getProduce() {
        return this.produce;
    }

    public final void setProduce(String str) {
        this.produce = str;
    }

    public final String[] getRepositories() {
        return this.repositories;
    }

    public final void setRepositories(String[] strArr) {
        this.repositories = strArr;
    }

    public final String getTarget() {
        return this.target;
    }

    public final void setTarget(String str) {
        this.target = str;
    }

    public final String getBundleId() {
        return this.bundleId;
    }

    public final void setBundleId(String str) {
        this.bundleId = str;
    }

    public final String[] getCacheDirectories() {
        return this.cacheDirectories;
    }

    public final void setCacheDirectories(String[] strArr) {
        this.cacheDirectories = strArr;
    }

    public final String[] getCachedLibraries() {
        return this.cachedLibraries;
    }

    public final void setCachedLibraries(String[] strArr) {
        this.cachedLibraries = strArr;
    }

    public final String[] getAutoCacheableFrom() {
        return this.autoCacheableFrom;
    }

    public final void setAutoCacheableFrom(String[] strArr) {
        this.autoCacheableFrom = strArr;
    }

    public final String getAutoCacheDir() {
        return this.autoCacheDir;
    }

    public final void setAutoCacheDir(String str) {
        this.autoCacheDir = str;
    }

    public final String getIncrementalCacheDir() {
        return this.incrementalCacheDir;
    }

    public final void setIncrementalCacheDir(String str) {
        this.incrementalCacheDir = str;
    }

    public final boolean getCheckDependencies() {
        return this.checkDependencies;
    }

    public final void setCheckDependencies(boolean z) {
        this.checkDependencies = z;
    }

    public final boolean getEmbedBitcode() {
        return this.embedBitcode;
    }

    public final void setEmbedBitcode(boolean z) {
        this.embedBitcode = z;
    }

    public final boolean getEmbedBitcodeMarker() {
        return this.embedBitcodeMarker;
    }

    public final void setEmbedBitcodeMarker(boolean z) {
        this.embedBitcodeMarker = z;
    }

    public final String getEmitLazyObjCHeader() {
        return this.emitLazyObjCHeader;
    }

    public final void setEmitLazyObjCHeader(String str) {
        this.emitLazyObjCHeader = str;
    }

    public final String[] getExportedLibraries() {
        return this.exportedLibraries;
    }

    public final void setExportedLibraries(String[] strArr) {
        this.exportedLibraries = strArr;
    }

    public final String getExternalDependencies() {
        return this.externalDependencies;
    }

    public final void setExternalDependencies(String str) {
        this.externalDependencies = str;
    }

    public final boolean getFakeOverrideValidator() {
        return this.fakeOverrideValidator;
    }

    public final void setFakeOverrideValidator(boolean z) {
        this.fakeOverrideValidator = z;
    }

    public final String[] getFrameworkImportHeaders() {
        return this.frameworkImportHeaders;
    }

    public final void setFrameworkImportHeaders(String[] strArr) {
        this.frameworkImportHeaders = strArr;
    }

    public final String getLightDebugString() {
        return this.lightDebugString;
    }

    public final void setLightDebugString(String str) {
        this.lightDebugString = str;
    }

    public final boolean getLightDebugDeprecated() {
        return this.lightDebugDeprecated;
    }

    public final void setLightDebugDeprecated(boolean z) {
        this.lightDebugDeprecated = z;
    }

    public final String getGenerateDebugTrampolineString() {
        return this.generateDebugTrampolineString;
    }

    public final void setGenerateDebugTrampolineString(String str) {
        this.generateDebugTrampolineString = str;
    }

    public final String getLibraryToAddToCache() {
        return this.libraryToAddToCache;
    }

    public final void setLibraryToAddToCache(String str) {
        this.libraryToAddToCache = str;
    }

    public final String[] getFilesToCache() {
        return this.filesToCache;
    }

    public final void setFilesToCache(String[] strArr) {
        this.filesToCache = strArr;
    }

    public final boolean getMakePerFileCache() {
        return this.makePerFileCache;
    }

    public final void setMakePerFileCache(boolean z) {
        this.makePerFileCache = z;
    }

    public final String getBackendThreads() {
        return this.backendThreads;
    }

    public final void setBackendThreads(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.backendThreads = str;
    }

    public final boolean getExportKDoc() {
        return this.exportKDoc;
    }

    public final void setExportKDoc(boolean z) {
        this.exportKDoc = z;
    }

    public final boolean getPrintBitCode() {
        return this.printBitCode;
    }

    public final void setPrintBitCode(boolean z) {
        this.printBitCode = z;
    }

    public final boolean getCheckExternalCalls() {
        return this.checkExternalCalls;
    }

    public final void setCheckExternalCalls(boolean z) {
        this.checkExternalCalls = z;
    }

    public final boolean getPrintIr() {
        return this.printIr;
    }

    public final void setPrintIr(boolean z) {
        this.printIr = z;
    }

    public final boolean getPrintFiles() {
        return this.printFiles;
    }

    public final void setPrintFiles(boolean z) {
        this.printFiles = z;
    }

    public final boolean getPurgeUserLibs() {
        return this.purgeUserLibs;
    }

    public final void setPurgeUserLibs(boolean z) {
        this.purgeUserLibs = z;
    }

    public final String getRuntimeFile() {
        return this.runtimeFile;
    }

    public final void setRuntimeFile(String str) {
        this.runtimeFile = str;
    }

    public final String[] getIncludes() {
        return this.includes;
    }

    public final void setIncludes(String[] strArr) {
        this.includes = strArr;
    }

    public final String getShortModuleName() {
        return this.shortModuleName;
    }

    public final void setShortModuleName(String str) {
        this.shortModuleName = str;
    }

    public final boolean getStaticFramework() {
        return this.staticFramework;
    }

    public final void setStaticFramework(boolean z) {
        this.staticFramework = z;
    }

    public final String getTemporaryFilesDir() {
        return this.temporaryFilesDir;
    }

    public final void setTemporaryFilesDir(String str) {
        this.temporaryFilesDir = str;
    }

    public final String[] getSaveLlvmIrAfter() {
        return this.saveLlvmIrAfter;
    }

    public final void setSaveLlvmIrAfter(String[] strArr) {
        Intrinsics.checkNotNullParameter(strArr, "<set-?>");
        this.saveLlvmIrAfter = strArr;
    }

    public final boolean getVerifyBitCode() {
        return this.verifyBitCode;
    }

    public final void setVerifyBitCode(boolean z) {
        this.verifyBitCode = z;
    }

    public final String getVerifyIr() {
        return this.verifyIr;
    }

    public final void setVerifyIr(String str) {
        this.verifyIr = str;
    }

    public final String getVerifyCompiler() {
        return this.verifyCompiler;
    }

    public final void setVerifyCompiler(String str) {
        this.verifyCompiler = str;
    }

    public final String getFriendModules() {
        return this.friendModules;
    }

    public final void setFriendModules(String str) {
        this.friendModules = str;
    }

    public final String[] getRefinesPaths() {
        return this.refinesPaths;
    }

    public final void setRefinesPaths(String[] strArr) {
        this.refinesPaths = strArr;
    }

    public final String getDebugInfoFormatVersion() {
        return this.debugInfoFormatVersion;
    }

    public final void setDebugInfoFormatVersion(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.debugInfoFormatVersion = str;
    }

    public final boolean getCoverage() {
        return this.coverage;
    }

    public final void setCoverage(boolean z) {
        this.coverage = z;
    }

    public final String[] getCoveredLibraries() {
        return this.coveredLibraries;
    }

    public final void setCoveredLibraries(String[] strArr) {
        this.coveredLibraries = strArr;
    }

    public final String getCoverageFile() {
        return this.coverageFile;
    }

    public final void setCoverageFile(String str) {
        this.coverageFile = str;
    }

    public final boolean getNoObjcGenerics() {
        return this.noObjcGenerics;
    }

    public final void setNoObjcGenerics(boolean z) {
        this.noObjcGenerics = z;
    }

    public final String[] getClangOptions() {
        return this.clangOptions;
    }

    public final void setClangOptions(String[] strArr) {
        this.clangOptions = strArr;
    }

    public final String getAllocator() {
        return this.allocator;
    }

    public final void setAllocator(String str) {
        this.allocator = str;
    }

    public final boolean getMetadataKlib() {
        return this.metadataKlib;
    }

    public final void setMetadataKlib(boolean z) {
        this.metadataKlib = z;
    }

    public final String[] getDebugPrefixMap() {
        return this.debugPrefixMap;
    }

    public final void setDebugPrefixMap(String[] strArr) {
        this.debugPrefixMap = strArr;
    }

    public final String getPreLinkCaches() {
        return this.preLinkCaches;
    }

    public final void setPreLinkCaches(String str) {
        this.preLinkCaches = str;
    }

    public final String[] getOverrideKonanProperties() {
        return this.overrideKonanProperties;
    }

    public final void setOverrideKonanProperties(String[] strArr) {
        this.overrideKonanProperties = strArr;
    }

    public final String getDestroyRuntimeMode() {
        return this.destroyRuntimeMode;
    }

    public final void setDestroyRuntimeMode(String str) {
        this.destroyRuntimeMode = str;
    }

    public final String getGc() {
        return this.gc;
    }

    public final void setGc(String str) {
        this.gc = str;
    }

    public final String getPropertyLazyInitialization() {
        return this.propertyLazyInitialization;
    }

    public final void setPropertyLazyInitialization(String str) {
        this.propertyLazyInitialization = str;
    }

    public final String getWorkerExceptionHandling() {
        return this.workerExceptionHandling;
    }

    public final void setWorkerExceptionHandling(String str) {
        this.workerExceptionHandling = str;
    }

    public final String getLlvmVariant() {
        return this.llvmVariant;
    }

    public final void setLlvmVariant(String str) {
        this.llvmVariant = str;
    }

    public final String[] getBinaryOptions() {
        return this.binaryOptions;
    }

    public final void setBinaryOptions(String[] strArr) {
        this.binaryOptions = strArr;
    }

    public final String getRuntimeLogs() {
        return this.runtimeLogs;
    }

    public final void setRuntimeLogs(String str) {
        this.runtimeLogs = str;
    }

    public final String getTestDumpOutputPath() {
        return this.testDumpOutputPath;
    }

    public final void setTestDumpOutputPath(String str) {
        this.testDumpOutputPath = str;
    }

    public final String getLazyIrForCaches() {
        return this.lazyIrForCaches;
    }

    public final void setLazyIrForCaches(String str) {
        this.lazyIrForCaches = str;
    }

    public final String getPartialLinkageMode() {
        return this.partialLinkageMode;
    }

    public final void setPartialLinkageMode(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.partialLinkageMode = str;
    }

    public final String getPartialLinkageLogLevel() {
        return this.partialLinkageLogLevel;
    }

    public final void setPartialLinkageLogLevel(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.partialLinkageLogLevel = str;
    }

    public final boolean getOmitFrameworkBinary() {
        return this.omitFrameworkBinary;
    }

    public final void setOmitFrameworkBinary(boolean z) {
        this.omitFrameworkBinary = z;
    }

    public final String getCompileFromBitcode() {
        return this.compileFromBitcode;
    }

    public final void setCompileFromBitcode(String str) {
        this.compileFromBitcode = str;
    }

    public final String getSerializedDependencies() {
        return this.serializedDependencies;
    }

    public final void setSerializedDependencies(String str) {
        this.serializedDependencies = str;
    }

    public final String getSaveDependenciesPath() {
        return this.saveDependenciesPath;
    }

    public final void setSaveDependenciesPath(String str) {
        this.saveDependenciesPath = str;
    }

    public final String getSaveLlvmIrDirectory() {
        return this.saveLlvmIrDirectory;
    }

    public final void setSaveLlvmIrDirectory(String str) {
        this.saveLlvmIrDirectory = str;
    }

    public final String getKonanDataDir() {
        return this.konanDataDir;
    }

    public final void setKonanDataDir(String str) {
        this.konanDataDir = str;
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments
    public Map<AnalysisFlag<?>, Object> configureAnalysisFlags(MessageCollector collector, LanguageVersion languageVersion) {
        Intrinsics.checkNotNullParameter(collector, "collector");
        Intrinsics.checkNotNullParameter(languageVersion, "languageVersion");
        Map<AnalysisFlag<?>, Object> mapConfigureAnalysisFlags = super.configureAnalysisFlags(collector, languageVersion);
        Object obj = mapConfigureAnalysisFlags.get(AnalysisFlags.getOptIn());
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.collections.List<*>");
        mapConfigureAnalysisFlags.put(AnalysisFlags.getOptIn(), CollectionsKt.plus((Collection) obj, (Iterable) CollectionsKt.listOf("kotlin.ExperimentalUnsignedTypes")));
        if (this.printIr) {
            setPhasesToDumpAfter(new String[]{"ALL"});
        }
        return mapConfigureAnalysisFlags;
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments
    protected void checkIrSupport(LanguageVersionSettings languageVersionSettings, MessageCollector collector) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(collector, "collector");
        if (languageVersionSettings.getLanguageVersion().compareTo(LanguageVersion.KOTLIN_1_4) < 0 || languageVersionSettings.getApiVersion().compareTo(ApiVersion.KOTLIN_1_4) < 0) {
            MessageCollector.report$default(collector, CompilerMessageSeverity.ERROR, "Native backend cannot be used with language or API version below 1.4", null, 4, null);
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.Freezable
    protected Freezable copyOf() {
        return K2NativeCompilerArgumentsCopyGeneratedKt.copyK2NativeCompilerArguments(this, new K2NativeCompilerArguments());
    }
}
