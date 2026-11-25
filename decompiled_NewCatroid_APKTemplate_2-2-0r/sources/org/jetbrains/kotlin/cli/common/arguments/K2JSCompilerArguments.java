package org.jetbrains.kotlin.cli.common.arguments;

import java.util.Map;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import name.antonsmirnov.firmata.FormatHelper;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.AnalysisFlag;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;

/* JADX INFO: compiled from: K2JSCompilerArguments.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000U\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0003\bÎ\u0001\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 ì\u00012\u00020\u0001:\u0002ì\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010Ø\u0001\u001a\u00030Ù\u00012\b\u0010Ú\u0001\u001a\u00030Û\u00012\b\u0010Ü\u0001\u001a\u00030Ý\u0001H\u0014J0\u0010Þ\u0001\u001a\u0015\u0012\t\u0012\u0007\u0012\u0002\b\u00030à\u0001\u0012\u0005\u0012\u00030á\u00010ß\u00012\b\u0010Ü\u0001\u001a\u00030Ý\u00012\b\u0010â\u0001\u001a\u00030ã\u0001H\u0016J\"\u0010ä\u0001\u001a\u0011\u0012\u0005\u0012\u00030å\u0001\u0012\u0005\u0012\u00030æ\u00010ß\u00012\b\u0010Ü\u0001\u001a\u00030Ý\u0001H\u0016J\n\u0010ç\u0001\u001a\u00030è\u0001H\u0014J)\u0010é\u0001\u001a\u00030Ù\u0001*\u00030Ý\u00012\u0006\u0010\u0003\u001a\u00020\n2\u0007\u0010ê\u0001\u001a\u00020\n2\u0007\u0010ë\u0001\u001a\u00020\u0004H\u0002R*\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR&\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR*\u0010\u0010\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0007\"\u0004\b\u0012\u0010\tR&\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\r\"\u0004\b\u0015\u0010\u000fR&\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\r\"\u0004\b\u0018\u0010\u000fR&\u0010\u0019\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\r\"\u0004\b\u001b\u0010\u000fR*\u0010\u001c\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0007\"\u0004\b\u001e\u0010\tR,\u0010\u001f\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b \u0010\u0002\u001a\u0004\b!\u0010\r\"\u0004\b\"\u0010\u000fR&\u0010#\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\r\"\u0004\b%\u0010\u000fR&\u0010&\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\r\"\u0004\b(\u0010\u000fR*\u0010)\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0007\"\u0004\b+\u0010\tR&\u0010,\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\r\"\u0004\b.\u0010\u000fR&\u0010/\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\r\"\u0004\b1\u0010\u000fR&\u00102\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\r\"\u0004\b4\u0010\u000fR*\u00105\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u0010\u0007\"\u0004\b7\u0010\tR*\u00108\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010\u0007\"\u0004\b:\u0010\tR&\u0010;\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010\r\"\u0004\b=\u0010\u000fR*\u0010>\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010\u0007\"\u0004\b@\u0010\tR&\u0010A\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010\r\"\u0004\bC\u0010\u000fR*\u0010D\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010\u0007\"\u0004\bF\u0010\tR&\u0010G\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010\r\"\u0004\bI\u0010\u000fR*\u0010J\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bK\u0010\u0007\"\u0004\bL\u0010\tR&\u0010M\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bN\u0010\r\"\u0004\bO\u0010\u000fR&\u0010P\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010\r\"\u0004\bR\u0010\u000fR&\u0010S\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bT\u0010\r\"\u0004\bU\u0010\u000fR&\u0010V\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bW\u0010\r\"\u0004\bX\u0010\u000fR*\u0010Y\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bZ\u0010\u0007\"\u0004\b[\u0010\tR&\u0010\\\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b]\u0010\r\"\u0004\b^\u0010\u000fR&\u0010_\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b`\u0010\r\"\u0004\ba\u0010\u000fR&\u0010b\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bc\u0010\r\"\u0004\bd\u0010\u000fR&\u0010e\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bf\u0010\r\"\u0004\bg\u0010\u000fR&\u0010h\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bi\u0010\r\"\u0004\bj\u0010\u000fR*\u0010k\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bl\u0010\u0007\"\u0004\bm\u0010\tR*\u0010n\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bo\u0010\u0007\"\u0004\bp\u0010\tR0\u0010q\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\br\u0010\u0002\u001a\u0004\bs\u0010\u0007\"\u0004\bt\u0010\tR,\u0010u\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\bv\u0010\u0002\u001a\u0004\bw\u0010\r\"\u0004\bx\u0010\u000fR&\u0010y\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bz\u0010\r\"\u0004\b{\u0010\u000fR0\u0010|\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b}\u0010\u0002\u001a\u0004\b~\u0010\u0007\"\u0004\b\u007f\u0010\tR4\u0010\u0080\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0017\n\u0000\u0012\u0005\b\u0081\u0001\u0010\u0002\u001a\u0005\b\u0082\u0001\u0010\u0007\"\u0005\b\u0083\u0001\u0010\tR0\u0010\u0084\u0001\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u0017\n\u0000\u0012\u0005\b\u0085\u0001\u0010\u0002\u001a\u0005\b\u0086\u0001\u0010\r\"\u0005\b\u0087\u0001\u0010\u000fR)\u0010\u0088\u0001\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0089\u0001\u0010\r\"\u0005\b\u008a\u0001\u0010\u000fR-\u0010\u008b\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u008c\u0001\u0010\u0007\"\u0005\b\u008d\u0001\u0010\tR4\u0010\u008e\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0017\n\u0000\u0012\u0005\b\u008f\u0001\u0010\u0002\u001a\u0005\b\u0090\u0001\u0010\u0007\"\u0005\b\u0091\u0001\u0010\tR-\u0010\u0092\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0093\u0001\u0010\u0007\"\u0005\b\u0094\u0001\u0010\tR-\u0010\u0095\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0096\u0001\u0010\u0007\"\u0005\b\u0097\u0001\u0010\tR-\u0010\u0098\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0099\u0001\u0010\u0007\"\u0005\b\u009a\u0001\u0010\tR-\u0010\u009b\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u009c\u0001\u0010\u0007\"\u0005\b\u009d\u0001\u0010\tR0\u0010\u009e\u0001\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u0017\n\u0000\u0012\u0005\b\u009f\u0001\u0010\u0002\u001a\u0005\b \u0001\u0010\r\"\u0005\b¡\u0001\u0010\u000fR-\u0010¢\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b£\u0001\u0010\u0007\"\u0005\b¤\u0001\u0010\tR4\u0010¥\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0017\n\u0000\u0012\u0005\b¦\u0001\u0010\u0002\u001a\u0005\b§\u0001\u0010\u0007\"\u0005\b¨\u0001\u0010\tR4\u0010©\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0017\n\u0000\u0012\u0005\bª\u0001\u0010\u0002\u001a\u0005\b«\u0001\u0010\u0007\"\u0005\b¬\u0001\u0010\tR4\u0010\u00ad\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0017\n\u0000\u0012\u0005\b®\u0001\u0010\u0002\u001a\u0005\b¯\u0001\u0010\u0007\"\u0005\b°\u0001\u0010\tR)\u0010±\u0001\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b²\u0001\u0010\r\"\u0005\b³\u0001\u0010\u000fR4\u0010´\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0017\n\u0000\u0012\u0005\bµ\u0001\u0010\u0002\u001a\u0005\b¶\u0001\u0010\u0007\"\u0005\b·\u0001\u0010\tR0\u0010¸\u0001\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u0017\n\u0000\u0012\u0005\b¹\u0001\u0010\u0002\u001a\u0005\bº\u0001\u0010\r\"\u0005\b»\u0001\u0010\u000fR0\u0010¼\u0001\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u0017\n\u0000\u0012\u0005\b½\u0001\u0010\u0002\u001a\u0005\b¾\u0001\u0010\r\"\u0005\b¿\u0001\u0010\u000fR)\u0010À\u0001\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÁ\u0001\u0010\r\"\u0005\bÂ\u0001\u0010\u000fR)\u0010Ã\u0001\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÄ\u0001\u0010\r\"\u0005\bÅ\u0001\u0010\u000fR)\u0010Æ\u0001\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÇ\u0001\u0010\r\"\u0005\bÈ\u0001\u0010\u000fR)\u0010É\u0001\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÊ\u0001\u0010\r\"\u0005\bË\u0001\u0010\u000fR)\u0010Ì\u0001\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÍ\u0001\u0010\r\"\u0005\bÎ\u0001\u0010\u000fR)\u0010Ï\u0001\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÐ\u0001\u0010\r\"\u0005\bÑ\u0001\u0010\u000fR-\u0010Ò\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÓ\u0001\u0010\u0007\"\u0005\bÔ\u0001\u0010\tR)\u0010Õ\u0001\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\n8\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÖ\u0001\u0010\r\"\u0005\b×\u0001\u0010\u000f¨\u0006í\u0001"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/K2JSCompilerArguments;", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "()V", "value", "", "cacheDirectory", "getCacheDirectory", "()Ljava/lang/String;", "setCacheDirectory", "(Ljava/lang/String;)V", "", "enableJsScripting", "getEnableJsScripting", "()Z", "setEnableJsScripting", "(Z)V", "errorTolerancePolicy", "getErrorTolerancePolicy", "setErrorTolerancePolicy", "extensionFunctionsInExternals", "getExtensionFunctionsInExternals", "setExtensionFunctionsInExternals", "fakeOverrideValidator", "getFakeOverrideValidator", "setFakeOverrideValidator", "forceDeprecatedLegacyCompilerUsage", "getForceDeprecatedLegacyCompilerUsage", "setForceDeprecatedLegacyCompilerUsage", "friendModules", "getFriendModules", "setFriendModules", "friendModulesDisabled", "getFriendModulesDisabled$annotations", "getFriendModulesDisabled", "setFriendModulesDisabled", "generateDts", "getGenerateDts", "setGenerateDts", "generatePolyfills", "getGeneratePolyfills", "setGeneratePolyfills", "includes", "getIncludes", "setIncludes", "irBaseClassInMetadata", "getIrBaseClassInMetadata", "setIrBaseClassInMetadata", "irBuildCache", "getIrBuildCache", "setIrBuildCache", "irDce", "getIrDce", "setIrDce", "irDceDumpDeclarationIrSizesToFile", "getIrDceDumpDeclarationIrSizesToFile", "setIrDceDumpDeclarationIrSizesToFile", "irDceDumpReachabilityInfoToFile", "getIrDceDumpReachabilityInfoToFile", "setIrDceDumpReachabilityInfoToFile", "irDcePrintReachabilityInfo", "getIrDcePrintReachabilityInfo", "setIrDcePrintReachabilityInfo", "irDceRuntimeDiagnostic", "getIrDceRuntimeDiagnostic", "setIrDceRuntimeDiagnostic", "irGenerateInlineAnonymousFunctions", "getIrGenerateInlineAnonymousFunctions", "setIrGenerateInlineAnonymousFunctions", "irKeep", "getIrKeep", "setIrKeep", "irMinimizedMemberNames", "getIrMinimizedMemberNames", "setIrMinimizedMemberNames", "irModuleName", "getIrModuleName", "setIrModuleName", "irNewIr2Js", "getIrNewIr2Js", "setIrNewIr2Js", "irOnly", "getIrOnly", "setIrOnly", "irPerFile", "getIrPerFile", "setIrPerFile", "irPerModule", "getIrPerModule", "setIrPerModule", "irPerModuleOutputName", "getIrPerModuleOutputName", "setIrPerModuleOutputName", "irProduceJs", "getIrProduceJs", "setIrProduceJs", "irProduceKlibDir", "getIrProduceKlibDir", "setIrProduceKlibDir", "irProduceKlibFile", "getIrProduceKlibFile", "setIrProduceKlibFile", "irPropertyLazyInitialization", "getIrPropertyLazyInitialization", "setIrPropertyLazyInitialization", "irSafeExternalBoolean", "getIrSafeExternalBoolean", "setIrSafeExternalBoolean", "irSafeExternalBooleanDiagnostic", "getIrSafeExternalBooleanDiagnostic", "setIrSafeExternalBooleanDiagnostic", "libraries", "getLibraries", "setLibraries", "main", "getMain$annotations", "getMain", "setMain", "metaInfo", "getMetaInfo$annotations", "getMetaInfo", "setMetaInfo", "metadataOnly", "getMetadataOnly", "setMetadataOnly", "moduleKind", "getModuleKind$annotations", "getModuleKind", "setModuleKind", "moduleName", "getModuleName$annotations", "getModuleName", "setModuleName", "noStdlib", "getNoStdlib$annotations", "getNoStdlib", "setNoStdlib", "optimizeGeneratedJs", "getOptimizeGeneratedJs", "setOptimizeGeneratedJs", ModuleXmlParser.OUTPUT_DIR, "getOutputDir", "setOutputDir", "outputFile", "getOutputFile$annotations", "getOutputFile", "setOutputFile", "outputPostfix", "getOutputPostfix", "setOutputPostfix", "outputPrefix", "getOutputPrefix", "setOutputPrefix", "partialLinkageLogLevel", "getPartialLinkageLogLevel", "setPartialLinkageLogLevel", "partialLinkageMode", "getPartialLinkageMode", "setPartialLinkageMode", "sourceMap", "getSourceMap$annotations", "getSourceMap", "setSourceMap", "sourceMapBaseDirs", "getSourceMapBaseDirs", "setSourceMapBaseDirs", "sourceMapEmbedSources", "getSourceMapEmbedSources$annotations", "getSourceMapEmbedSources", "setSourceMapEmbedSources", "sourceMapNamesPolicy", "getSourceMapNamesPolicy$annotations", "getSourceMapNamesPolicy", "setSourceMapNamesPolicy", "sourceMapPrefix", "getSourceMapPrefix$annotations", "getSourceMapPrefix", "setSourceMapPrefix", "strictImplicitExportType", "getStrictImplicitExportType", "setStrictImplicitExportType", "target", "getTarget$annotations", "getTarget", "setTarget", "typedArrays", "getTypedArrays$annotations", "getTypedArrays", "setTypedArrays", "useEsClasses", "getUseEsClasses$annotations", "getUseEsClasses", "setUseEsClasses", "wasm", "getWasm", "setWasm", "wasmDebug", "getWasmDebug", "setWasmDebug", "wasmEnableArrayRangeChecks", "getWasmEnableArrayRangeChecks", "setWasmEnableArrayRangeChecks", "wasmEnableAsserts", "getWasmEnableAsserts", "setWasmEnableAsserts", "wasmGenerateWat", "getWasmGenerateWat", "setWasmGenerateWat", "wasmKClassFqn", "getWasmKClassFqn", "setWasmKClassFqn", "wasmTarget", "getWasmTarget", "setWasmTarget", "wasmUseTrapsInsteadOfExceptions", "getWasmUseTrapsInsteadOfExceptions", "setWasmUseTrapsInsteadOfExceptions", "checkIrSupport", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "collector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "configureAnalysisFlags", "", "Lorg/jetbrains/kotlin/config/AnalysisFlag;", "", "languageVersion", "Lorg/jetbrains/kotlin/config/LanguageVersion;", "configureLanguageFeatures", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "Lorg/jetbrains/kotlin/config/LanguageFeature$State;", "copyOf", "Lorg/jetbrains/kotlin/cli/common/arguments/Freezable;", "deprecationWarn", "defaultValue", "name", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class K2JSCompilerArguments extends CommonCompilerArguments {
    private static final long serialVersionUID = 0;

    @Argument(description = "A path to cache directory", value = "-Xcache-directory", valueDescription = "<path>")
    private String cacheDirectory;

    @Argument(description = "Enable experimental support of .kts files using K/JS (with -Xir only)", value = "-Xenable-js-scripting")
    private boolean enableJsScripting;

    @Argument(description = "Set up error tolerance policy (NONE, SEMANTIC, SYNTAX, ALL)", value = "-Xerror-tolerance-policy")
    private String errorTolerancePolicy;

    @Argument(description = "Enable extensions functions members in external interfaces", value = "-Xenable-extension-functions-in-externals")
    private boolean extensionFunctionsInExternals;

    @Argument(description = "Enable IR fake override validator", value = "-Xfake-override-validator")
    private boolean fakeOverrideValidator;

    @Argument(description = "The flag is used only for our inner infrastructure. It will be removed soon, so it's unsafe to use it nowadays.", value = "-Xforce-deprecated-legacy-compiler-usage")
    private boolean forceDeprecatedLegacyCompilerUsage;

    @Argument(description = "Paths to friend modules", value = "-Xfriend-modules", valueDescription = "<path>")
    private String friendModules;

    @Argument(description = "Disable internal declaration export", value = "-Xfriend-modules-disabled")
    private boolean friendModulesDisabled;

    @Argument(description = "Generate TypeScript declarations .d.ts file alongside JS file. Available in IR backend only.", value = "-Xgenerate-dts")
    private boolean generateDts;

    @Argument(description = "A path to an intermediate library that should be processed in the same manner as source files.", value = K2NativeCompilerArguments.INCLUDE_ARG, valueDescription = "<path>")
    private String includes;

    @Argument(description = "Write base class into metadata", value = "-Xir-base-class-in-metadata")
    private boolean irBaseClassInMetadata;

    @Argument(description = "Use compiler to build cache", value = "-Xir-build-cache")
    private boolean irBuildCache;

    @Argument(description = "Perform experimental dead code elimination", value = "-Xir-dce")
    private boolean irDce;

    @Argument(description = "Dump the IR size of each declaration to a file. The format will be chosen automatically depending on the file extension. Supported output formats include JSON for .json, JS const initialized with a plain object containing information for .js, and plain text for all other file types.", value = "-Xir-dump-declaration-ir-sizes-to-file", valueDescription = "<path>")
    private String irDceDumpDeclarationIrSizesToFile;

    @Argument(description = "Dump declarations' reachability info collected during performing DCE to a file. The format will be chosen automatically based on the file extension. Supported output formats include JSON for .json, JS const initialized with a plain object containing information for .js, and plain text for all other file types.", value = "-Xir-dce-dump-reachability-info-to-file", valueDescription = "<path>")
    private String irDceDumpReachabilityInfoToFile;

    @Argument(description = "Print declarations' reachability info to stdout during performing DCE", value = "-Xir-dce-print-reachability-info")
    private boolean irDcePrintReachabilityInfo;

    @Argument(description = "Enable runtime diagnostics when performing DCE instead of removing declarations", value = "-Xir-dce-runtime-diagnostic", valueDescription = "{log|exception}")
    private String irDceRuntimeDiagnostic;

    @Argument(description = "Lambda expressions that capture values are translated into in-line anonymous JavaScript functions", value = "-Xir-generate-inline-anonymous-functions")
    private boolean irGenerateInlineAnonymousFunctions;

    @Argument(description = "Comma-separated list of fully-qualified names to not be eliminated by DCE (if it can be reached), and for which to keep non-minified names.", value = "-Xir-keep")
    private String irKeep;

    @Argument(description = "Perform minimization for names of members", value = "-Xir-minimized-member-names")
    private boolean irMinimizedMemberNames;

    @Argument(description = "Specify a compilation module name for IR backend", value = "-Xir-module-name", valueDescription = "<name>")
    private String irModuleName;

    @Argument(description = "Disables pre-IR backend", value = "-Xir-only")
    private boolean irOnly;

    @Argument(description = "Splits generated .js per-file", value = "-Xir-per-file")
    private boolean irPerFile;

    @Argument(description = "Splits generated .js per-module", value = "-Xir-per-module")
    private boolean irPerModule;

    @Argument(description = "Adds a custom output name to the splitted js files", value = "-Xir-per-module-output-name")
    private String irPerModuleOutputName;

    @Argument(description = "Generates JS file using IR backend. Also disables pre-IR backend", value = "-Xir-produce-js")
    private boolean irProduceJs;

    @Argument(description = "Generate unpacked KLIB into parent directory of output JS file.\nIn combination with -meta-info generates both IR and pre-IR versions of library.", value = "-Xir-produce-klib-dir")
    private boolean irProduceKlibDir;

    @Argument(description = "Generate packed klib into file specified by -output. Disables pre-IR backend", value = "-Xir-produce-klib-file")
    private boolean irProduceKlibFile;

    @Argument(description = "Safe access via Boolean() to Boolean properties in externals to safely cast falsy values.", value = "-Xir-safe-external-boolean")
    private boolean irSafeExternalBoolean;

    @Argument(description = "Enable runtime diagnostics when access safely to boolean in external declarations", value = "-Xir-safe-external-boolean-diagnostic", valueDescription = "{log|exception}")
    private String irSafeExternalBooleanDiagnostic;

    @Argument(description = "Paths to Kotlin libraries with .meta.js and .kjsm files, separated by system path separator", value = "-libraries", valueDescription = "<path>")
    private String libraries;

    @Argument(description = "Define whether the `main` function should be called upon execution", value = "-main", valueDescription = "{call|noCall}")
    private String main;

    @Argument(description = "Generate .meta.js and .kjsm files with metadata. Use to create a library", value = "-meta-info")
    private boolean metaInfo;

    @Argument(description = "Generate *.meta.js and *.kjsm files only", value = "-Xmetadata-only")
    private boolean metadataOnly;

    @Argument(description = "Base name of generated files", value = "-ir-output-name")
    private String moduleName;

    @Argument(description = "Don't automatically include the default Kotlin/JS stdlib into compilation dependencies", value = "-no-stdlib")
    private boolean noStdlib;

    @Argument(description = "Destination for generated files", value = "-ir-output-dir", valueDescription = "<directory>")
    private String outputDir;

    @Argument(description = "Destination *.js file for the compilation result", value = "-output", valueDescription = "<filepath>")
    private String outputFile;

    @Argument(description = "Add the content of the specified file to the end of output file", value = "-output-postfix", valueDescription = "<path>")
    private String outputPostfix;

    @Argument(description = "Add the content of the specified file to the beginning of output file", value = "-output-prefix", valueDescription = "<path>")
    private String outputPrefix;

    @Argument(description = "Partial linkage compile-time log level", value = "-Xpartial-linkage-loglevel", valueDescription = "{info|warning|error}")
    private String partialLinkageLogLevel;

    @Argument(description = "Use partial linkage mode", value = "-Xpartial-linkage", valueDescription = "{enable|disable}")
    private String partialLinkageMode;

    @Argument(description = "Generate source map", value = "-source-map")
    private boolean sourceMap;

    @Argument(deprecatedName = "-source-map-source-roots", description = "Base directories for calculating relative paths to source files in source map", value = "-source-map-base-dirs", valueDescription = "<path>")
    private String sourceMapBaseDirs;

    @Argument(description = "Embed source files into source map", value = "-source-map-embed-sources", valueDescription = "{always|never|inlining}")
    private String sourceMapEmbedSources;

    @Argument(description = "How to map generated names to original names (IR backend only)", value = "-source-map-names-policy", valueDescription = "{no|simple-names|fully-qualified-names}")
    private String sourceMapNamesPolicy;

    @Argument(description = "Add the specified prefix to paths in the source map", value = "-source-map-prefix")
    private String sourceMapPrefix;

    @Argument(description = "Generate strict types for implicitly exported entities inside d.ts files. Available in IR backend only.", value = "-Xstrict-implicit-export-types")
    private boolean strictImplicitExportType;

    @Argument(description = "Generate JS files for specific ECMA version", value = "-target", valueDescription = "{ v5 }")
    private String target;

    @Argument(description = "Generated JavaScript will use ES2015 classes.", value = "-Xes-classes")
    private boolean useEsClasses;

    @Argument(description = "Use experimental WebAssembly compiler backend", value = "-Xwasm")
    private boolean wasm;

    @Argument(description = "Turn on range checks for the array access functions", value = "-Xwasm-enable-array-range-checks")
    private boolean wasmEnableArrayRangeChecks;

    @Argument(description = "Turn on asserts", value = "-Xwasm-enable-asserts")
    private boolean wasmEnableAsserts;

    @Argument(description = "Generate wat file", value = "-Xwasm-generate-wat")
    private boolean wasmGenerateWat;

    @Argument(description = "Enable support for FQ names in KClass", value = "-Xwasm-kclass-fqn")
    private boolean wasmKClassFqn;

    @Argument(description = "Set up Wasm target (wasm-js or wasm-wasi)", value = "-Xwasm-target")
    private String wasmTarget;

    @Argument(description = "Trap instead of throwing exceptions", value = "-Xwasm-use-traps-instead-of-exceptions")
    private boolean wasmUseTrapsInsteadOfExceptions;

    @Argument(description = "Kind of the JS module generated by the compiler", value = "-module-kind", valueDescription = "{plain|amd|commonjs|umd|es}")
    private String moduleKind = K2JsArgumentConstants.MODULE_PLAIN;

    @Argument(description = "Perform lazy initialization for properties", value = "-Xir-property-lazy-initialization")
    private boolean irPropertyLazyInitialization = true;

    @Argument(description = "New fragment-based ir2js", value = "-Xir-new-ir2js")
    private boolean irNewIr2Js = true;

    @Argument(description = "Generate polyfills for features from the ES6+ standards.", value = "-Xgenerate-polyfills")
    private boolean generatePolyfills = true;

    @Argument(description = "Translate primitive arrays to JS typed arrays", value = "-Xtyped-arrays")
    private boolean typedArrays = true;

    @Argument(description = "Add debug info to WebAssembly compiled module", value = "-Xwasm-debug-info")
    private boolean wasmDebug = true;

    @Argument(description = "Perform additional optimizations on the generated JS code", value = "-Xoptimize-generated-js")
    private boolean optimizeGeneratedJs = true;

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.BOOLEAN_FALSE_DEFAULT)
    public static /* synthetic */ void getFriendModulesDisabled$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.JS_MAIN)
    public static /* synthetic */ void getMain$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.BOOLEAN_TRUE_DEFAULT)
    public static /* synthetic */ void getMetaInfo$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.JS_MODULE_KINDS)
    public static /* synthetic */ void getModuleKind$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.STRING_NULL_DEFAULT)
    public static /* synthetic */ void getModuleName$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.BOOLEAN_TRUE_DEFAULT)
    public static /* synthetic */ void getNoStdlib$annotations() {
    }

    @GradleDeprecatedOption(level = DeprecationLevel.WARNING, message = "Only for legacy backend. For IR backend please use task.destinationDirectory and moduleName", removeAfter = "1.9.0")
    @GradleOption(gradleInputType = GradleInputTypes.INTERNAL, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.STRING_NULL_DEFAULT)
    public static /* synthetic */ void getOutputFile$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.BOOLEAN_FALSE_DEFAULT)
    public static /* synthetic */ void getSourceMap$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.JS_SOURCE_MAP_CONTENT_MODES)
    public static /* synthetic */ void getSourceMapEmbedSources$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.JS_SOURCE_MAP_NAMES_POLICY)
    public static /* synthetic */ void getSourceMapNamesPolicy$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.STRING_NULL_DEFAULT)
    public static /* synthetic */ void getSourceMapPrefix$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.JS_ECMA_VERSIONS)
    public static /* synthetic */ void getTarget$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.BOOLEAN_TRUE_DEFAULT)
    public static /* synthetic */ void getTypedArrays$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.BOOLEAN_FALSE_DEFAULT)
    public static /* synthetic */ void getUseEsClasses$annotations() {
    }

    public final String getOutputFile() {
        return this.outputFile;
    }

    public final void setOutputFile(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.outputFile = str;
    }

    public final String getOutputDir() {
        return this.outputDir;
    }

    public final void setOutputDir(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.outputDir = str;
    }

    public final String getModuleName() {
        return this.moduleName;
    }

    public final void setModuleName(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.moduleName = str;
    }

    public final boolean getNoStdlib() {
        return this.noStdlib;
    }

    public final void setNoStdlib(boolean z) {
        checkFrozen();
        this.noStdlib = z;
    }

    public final String getLibraries() {
        return this.libraries;
    }

    public final void setLibraries(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.libraries = str;
    }

    public final boolean getSourceMap() {
        return this.sourceMap;
    }

    public final void setSourceMap(boolean z) {
        checkFrozen();
        this.sourceMap = z;
    }

    public final String getSourceMapPrefix() {
        return this.sourceMapPrefix;
    }

    public final void setSourceMapPrefix(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.sourceMapPrefix = str;
    }

    public final String getSourceMapBaseDirs() {
        return this.sourceMapBaseDirs;
    }

    public final void setSourceMapBaseDirs(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.sourceMapBaseDirs = str;
    }

    public final String getSourceMapEmbedSources() {
        return this.sourceMapEmbedSources;
    }

    public final void setSourceMapEmbedSources(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.sourceMapEmbedSources = str;
    }

    public final String getSourceMapNamesPolicy() {
        return this.sourceMapNamesPolicy;
    }

    public final void setSourceMapNamesPolicy(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.sourceMapNamesPolicy = str;
    }

    public final boolean getMetaInfo() {
        return this.metaInfo;
    }

    public final void setMetaInfo(boolean z) {
        checkFrozen();
        this.metaInfo = z;
    }

    public final String getTarget() {
        return this.target;
    }

    public final void setTarget(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.target = str;
    }

    public final String getIrKeep() {
        return this.irKeep;
    }

    public final void setIrKeep(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.irKeep = str;
    }

    public final String getModuleKind() {
        return this.moduleKind;
    }

    public final void setModuleKind(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = K2JsArgumentConstants.MODULE_PLAIN;
        }
        this.moduleKind = str;
    }

    public final String getMain() {
        return this.main;
    }

    public final void setMain(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.main = str;
    }

    public final String getOutputPrefix() {
        return this.outputPrefix;
    }

    public final void setOutputPrefix(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.outputPrefix = str;
    }

    public final String getOutputPostfix() {
        return this.outputPostfix;
    }

    public final void setOutputPostfix(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.outputPostfix = str;
    }

    public final boolean getIrProduceKlibDir() {
        return this.irProduceKlibDir;
    }

    public final void setIrProduceKlibDir(boolean z) {
        checkFrozen();
        this.irProduceKlibDir = z;
    }

    public final boolean getIrProduceKlibFile() {
        return this.irProduceKlibFile;
    }

    public final void setIrProduceKlibFile(boolean z) {
        checkFrozen();
        this.irProduceKlibFile = z;
    }

    public final boolean getIrProduceJs() {
        return this.irProduceJs;
    }

    public final void setIrProduceJs(boolean z) {
        checkFrozen();
        this.irProduceJs = z;
    }

    public final boolean getIrDce() {
        return this.irDce;
    }

    public final void setIrDce(boolean z) {
        checkFrozen();
        this.irDce = z;
    }

    public final String getIrDceRuntimeDiagnostic() {
        return this.irDceRuntimeDiagnostic;
    }

    public final void setIrDceRuntimeDiagnostic(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.irDceRuntimeDiagnostic = str;
    }

    public final boolean getIrDcePrintReachabilityInfo() {
        return this.irDcePrintReachabilityInfo;
    }

    public final void setIrDcePrintReachabilityInfo(boolean z) {
        checkFrozen();
        this.irDcePrintReachabilityInfo = z;
    }

    public final String getIrDceDumpReachabilityInfoToFile() {
        return this.irDceDumpReachabilityInfoToFile;
    }

    public final void setIrDceDumpReachabilityInfoToFile(String str) {
        checkFrozen();
        this.irDceDumpReachabilityInfoToFile = str;
    }

    public final String getIrDceDumpDeclarationIrSizesToFile() {
        return this.irDceDumpDeclarationIrSizesToFile;
    }

    public final void setIrDceDumpDeclarationIrSizesToFile(String str) {
        checkFrozen();
        this.irDceDumpDeclarationIrSizesToFile = str;
    }

    public final boolean getIrPropertyLazyInitialization() {
        return this.irPropertyLazyInitialization;
    }

    public final void setIrPropertyLazyInitialization(boolean z) {
        checkFrozen();
        this.irPropertyLazyInitialization = z;
    }

    public final boolean getIrMinimizedMemberNames() {
        return this.irMinimizedMemberNames;
    }

    public final void setIrMinimizedMemberNames(boolean z) {
        checkFrozen();
        this.irMinimizedMemberNames = z;
    }

    public final boolean getIrOnly() {
        return this.irOnly;
    }

    public final void setIrOnly(boolean z) {
        checkFrozen();
        this.irOnly = z;
    }

    public final String getIrModuleName() {
        return this.irModuleName;
    }

    public final void setIrModuleName(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.irModuleName = str;
    }

    public final boolean getIrBaseClassInMetadata() {
        return this.irBaseClassInMetadata;
    }

    public final void setIrBaseClassInMetadata(boolean z) {
        checkFrozen();
        this.irBaseClassInMetadata = z;
    }

    public final boolean getIrSafeExternalBoolean() {
        return this.irSafeExternalBoolean;
    }

    public final void setIrSafeExternalBoolean(boolean z) {
        checkFrozen();
        this.irSafeExternalBoolean = z;
    }

    public final String getIrSafeExternalBooleanDiagnostic() {
        return this.irSafeExternalBooleanDiagnostic;
    }

    public final void setIrSafeExternalBooleanDiagnostic(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.irSafeExternalBooleanDiagnostic = str;
    }

    public final boolean getIrPerModule() {
        return this.irPerModule;
    }

    public final void setIrPerModule(boolean z) {
        checkFrozen();
        this.irPerModule = z;
    }

    public final String getIrPerModuleOutputName() {
        return this.irPerModuleOutputName;
    }

    public final void setIrPerModuleOutputName(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.irPerModuleOutputName = str;
    }

    public final boolean getIrPerFile() {
        return this.irPerFile;
    }

    public final void setIrPerFile(boolean z) {
        checkFrozen();
        this.irPerFile = z;
    }

    public final boolean getIrNewIr2Js() {
        return this.irNewIr2Js;
    }

    public final void setIrNewIr2Js(boolean z) {
        checkFrozen();
        this.irNewIr2Js = z;
    }

    public final boolean getIrGenerateInlineAnonymousFunctions() {
        return this.irGenerateInlineAnonymousFunctions;
    }

    public final void setIrGenerateInlineAnonymousFunctions(boolean z) {
        checkFrozen();
        this.irGenerateInlineAnonymousFunctions = z;
    }

    public final String getIncludes() {
        return this.includes;
    }

    public final void setIncludes(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.includes = str;
    }

    public final String getCacheDirectory() {
        return this.cacheDirectory;
    }

    public final void setCacheDirectory(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.cacheDirectory = str;
    }

    public final boolean getIrBuildCache() {
        return this.irBuildCache;
    }

    public final void setIrBuildCache(boolean z) {
        checkFrozen();
        this.irBuildCache = z;
    }

    public final boolean getGenerateDts() {
        return this.generateDts;
    }

    public final void setGenerateDts(boolean z) {
        checkFrozen();
        this.generateDts = z;
    }

    public final boolean getGeneratePolyfills() {
        return this.generatePolyfills;
    }

    public final void setGeneratePolyfills(boolean z) {
        checkFrozen();
        this.generatePolyfills = z;
    }

    public final boolean getStrictImplicitExportType() {
        return this.strictImplicitExportType;
    }

    public final void setStrictImplicitExportType(boolean z) {
        checkFrozen();
        this.strictImplicitExportType = z;
    }

    public final boolean getUseEsClasses() {
        return this.useEsClasses;
    }

    public final void setUseEsClasses(boolean z) {
        checkFrozen();
        this.useEsClasses = z;
    }

    public final boolean getTypedArrays() {
        return this.typedArrays;
    }

    public final void setTypedArrays(boolean z) {
        checkFrozen();
        this.typedArrays = z;
    }

    public final boolean getFriendModulesDisabled() {
        return this.friendModulesDisabled;
    }

    public final void setFriendModulesDisabled(boolean z) {
        checkFrozen();
        this.friendModulesDisabled = z;
    }

    public final String getFriendModules() {
        return this.friendModules;
    }

    public final void setFriendModules(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.friendModules = str;
    }

    public final boolean getExtensionFunctionsInExternals() {
        return this.extensionFunctionsInExternals;
    }

    public final void setExtensionFunctionsInExternals(boolean z) {
        checkFrozen();
        this.extensionFunctionsInExternals = z;
    }

    public final boolean getMetadataOnly() {
        return this.metadataOnly;
    }

    public final void setMetadataOnly(boolean z) {
        checkFrozen();
        this.metadataOnly = z;
    }

    public final boolean getEnableJsScripting() {
        return this.enableJsScripting;
    }

    public final void setEnableJsScripting(boolean z) {
        checkFrozen();
        this.enableJsScripting = z;
    }

    public final boolean getFakeOverrideValidator() {
        return this.fakeOverrideValidator;
    }

    public final void setFakeOverrideValidator(boolean z) {
        checkFrozen();
        this.fakeOverrideValidator = z;
    }

    public final String getErrorTolerancePolicy() {
        return this.errorTolerancePolicy;
    }

    public final void setErrorTolerancePolicy(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.errorTolerancePolicy = str;
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

    public final boolean getWasm() {
        return this.wasm;
    }

    public final void setWasm(boolean z) {
        checkFrozen();
        this.wasm = z;
    }

    public final boolean getWasmDebug() {
        return this.wasmDebug;
    }

    public final void setWasmDebug(boolean z) {
        checkFrozen();
        this.wasmDebug = z;
    }

    public final boolean getWasmKClassFqn() {
        return this.wasmKClassFqn;
    }

    public final void setWasmKClassFqn(boolean z) {
        checkFrozen();
        this.wasmKClassFqn = z;
    }

    public final boolean getWasmEnableArrayRangeChecks() {
        return this.wasmEnableArrayRangeChecks;
    }

    public final void setWasmEnableArrayRangeChecks(boolean z) {
        checkFrozen();
        this.wasmEnableArrayRangeChecks = z;
    }

    public final boolean getWasmEnableAsserts() {
        return this.wasmEnableAsserts;
    }

    public final void setWasmEnableAsserts(boolean z) {
        checkFrozen();
        this.wasmEnableAsserts = z;
    }

    public final boolean getWasmGenerateWat() {
        return this.wasmGenerateWat;
    }

    public final void setWasmGenerateWat(boolean z) {
        checkFrozen();
        this.wasmGenerateWat = z;
    }

    public final String getWasmTarget() {
        return this.wasmTarget;
    }

    public final void setWasmTarget(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.wasmTarget = str;
    }

    public final boolean getWasmUseTrapsInsteadOfExceptions() {
        return this.wasmUseTrapsInsteadOfExceptions;
    }

    public final void setWasmUseTrapsInsteadOfExceptions(boolean z) {
        checkFrozen();
        this.wasmUseTrapsInsteadOfExceptions = z;
    }

    public final boolean getForceDeprecatedLegacyCompilerUsage() {
        return this.forceDeprecatedLegacyCompilerUsage;
    }

    public final void setForceDeprecatedLegacyCompilerUsage(boolean z) {
        checkFrozen();
        this.forceDeprecatedLegacyCompilerUsage = z;
    }

    public final boolean getOptimizeGeneratedJs() {
        return this.optimizeGeneratedJs;
    }

    public final void setOptimizeGeneratedJs(boolean z) {
        checkFrozen();
        this.optimizeGeneratedJs = z;
    }

    private final void deprecationWarn(MessageCollector messageCollector, boolean z, boolean z2, String str) {
        if (z != z2) {
            MessageCollector.report$default(messageCollector, CompilerMessageSeverity.WARNING, FormatHelper.QUOTE + str + "' is deprecated and ignored, it will be removed in a future release", null, 4, null);
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments
    public Map<AnalysisFlag<?>, Object> configureAnalysisFlags(MessageCollector collector, LanguageVersion languageVersion) {
        Intrinsics.checkNotNullParameter(collector, "collector");
        Intrinsics.checkNotNullParameter(languageVersion, "languageVersion");
        boolean z = false;
        deprecationWarn(collector, this.enableJsScripting, false, "-Xenable-js-scripting");
        deprecationWarn(collector, this.irBaseClassInMetadata, false, "-Xir-base-class-in-metadata");
        deprecationWarn(collector, this.irNewIr2Js, true, "-Xir-new-ir2js");
        if (this.irPerFile && !Intrinsics.areEqual(this.moduleKind, "es")) {
            MessageCollector.report$default(collector, CompilerMessageSeverity.ERROR, "Per-file compilation can't be used with any `moduleKind` except `es` (ECMAScript Modules)", null, 4, null);
        }
        Map<AnalysisFlag<?>, Object> mapConfigureAnalysisFlags = super.configureAnalysisFlags(collector, languageVersion);
        AnalysisFlag<Boolean> allowFullyQualifiedNameInKClass = AnalysisFlags.getAllowFullyQualifiedNameInKClass();
        if (this.wasm && this.wasmKClassFqn) {
            z = true;
        }
        mapConfigureAnalysisFlags.put(allowFullyQualifiedNameInKClass, Boolean.valueOf(z));
        return mapConfigureAnalysisFlags;
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments
    protected void checkIrSupport(LanguageVersionSettings languageVersionSettings, MessageCollector collector) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(collector, "collector");
        if (K2JSCompilerArgumentsKt.isIrBackendEnabled(this)) {
            if (languageVersionSettings.getLanguageVersion().compareTo(LanguageVersion.KOTLIN_1_4) < 0 || languageVersionSettings.getApiVersion().compareTo(ApiVersion.KOTLIN_1_4) < 0) {
                MessageCollector.report$default(collector, CompilerMessageSeverity.ERROR, "IR backend cannot be used with language or API version below 1.4", null, 4, null);
            }
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments
    public Map<LanguageFeature, LanguageFeature.State> configureLanguageFeatures(MessageCollector collector) {
        Intrinsics.checkNotNullParameter(collector, "collector");
        Map<LanguageFeature, LanguageFeature.State> mapConfigureLanguageFeatures = super.configureLanguageFeatures(collector);
        if (this.extensionFunctionsInExternals) {
            mapConfigureLanguageFeatures.put(LanguageFeature.JsEnableExtensionFunctionInExternals, LanguageFeature.State.ENABLED);
        }
        if (!K2JSCompilerArgumentsKt.isIrBackendEnabled(this)) {
            mapConfigureLanguageFeatures.put(LanguageFeature.JsAllowInvalidCharsIdentifiersEscaping, LanguageFeature.State.DISABLED);
        }
        if (K2JSCompilerArgumentsKt.isIrBackendEnabled(this)) {
            mapConfigureLanguageFeatures.put(LanguageFeature.JsAllowValueClassesInExternals, LanguageFeature.State.ENABLED);
        }
        if (this.wasm) {
            mapConfigureLanguageFeatures.put(LanguageFeature.JsAllowImplementingFunctionInterface, LanguageFeature.State.ENABLED);
        }
        return mapConfigureLanguageFeatures;
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.Freezable
    protected Freezable copyOf() {
        return K2JSCompilerArgumentsCopyGeneratedKt.copyK2JSCompilerArguments(this, new K2JSCompilerArguments());
    }
}
