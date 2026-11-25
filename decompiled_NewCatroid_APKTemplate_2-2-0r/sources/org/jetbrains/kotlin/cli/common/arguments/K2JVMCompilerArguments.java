package org.jetbrains.kotlin.cli.common.arguments;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity;
import org.jetbrains.kotlin.cli.common.messages.MessageCollector;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.config.AnalysisFlag;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.JVMAssertionsMode;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.config.JvmDefaultMode;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersion;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.config.LanguageVersionSettingsKt;

/* JADX INFO: compiled from: K2JVMCompilerArguments.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000]\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0003\bå\u0001\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u0088\u00022\u00020\u0001:\u0002\u0088\u0002B\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010ö\u0001\u001a\u00030÷\u00012\b\u0010ø\u0001\u001a\u00030ù\u00012\b\u0010ú\u0001\u001a\u00030û\u0001H\u0014J0\u0010ü\u0001\u001a\u0015\u0012\t\u0012\u0007\u0012\u0002\b\u00030þ\u0001\u0012\u0005\u0012\u00030ÿ\u00010ý\u00012\b\u0010ú\u0001\u001a\u00030û\u00012\b\u0010\u0080\u0002\u001a\u00030\u0081\u0002H\u0016J\"\u0010\u0082\u0002\u001a\u0011\u0012\u0005\u0012\u00030\u0083\u0002\u0012\u0005\u0012\u00030\u0084\u00020ý\u00012\b\u0010ú\u0001\u001a\u00030û\u0001H\u0016J\n\u0010\u0085\u0002\u001a\u00030\u0086\u0002H\u0014J\u0014\u0010\u0087\u0002\u001a\u00030\u0081\u00022\b\u0010ú\u0001\u001a\u00030û\u0001H\u0014R*\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR8\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\n2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\n8\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0010\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR&\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R&\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0014\"\u0004\b\u0019\u0010\u0016R*\u0010\u001a\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0007\"\u0004\b\u001c\u0010\tR&\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0007\"\u0004\b\u001f\u0010\tR*\u0010 \u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0007\"\u0004\b\"\u0010\tR*\u0010#\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0007\"\u0004\b%\u0010\tR&\u0010&\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\u0014\"\u0004\b(\u0010\u0016R*\u0010)\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0007\"\u0004\b+\u0010\tR*\u0010,\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u0007\"\u0004\b.\u0010\tR*\u0010/\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\u0007\"\u0004\b1\u0010\tR&\u00102\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u0014\"\u0004\b4\u0010\u0016R&\u00105\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u0010\u0014\"\u0004\b7\u0010\u0016R&\u00108\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010\u0014\"\u0004\b:\u0010\u0016R&\u0010;\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010\u0014\"\u0004\b=\u0010\u0016R&\u0010>\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010\u0014\"\u0004\b@\u0010\u0016R&\u0010A\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010\u0014\"\u0004\bC\u0010\u0016R&\u0010D\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bE\u0010\u0014\"\u0004\bF\u0010\u0016R*\u0010G\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010\u0007\"\u0004\bI\u0010\tR8\u0010J\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\n2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\n8\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0010\u001a\u0004\bK\u0010\r\"\u0004\bL\u0010\u000fR&\u0010M\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bN\u0010\u0014\"\u0004\bO\u0010\u0016R&\u0010P\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010\u0014\"\u0004\bR\u0010\u0016R*\u0010S\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bT\u0010\u0007\"\u0004\bU\u0010\tR*\u0010V\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bW\u0010\u0007\"\u0004\bX\u0010\tR,\u0010Y\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\bZ\u0010\u0002\u001a\u0004\b[\u0010\u0014\"\u0004\b\\\u0010\u0016R8\u0010]\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\n2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\n8\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0010\u001a\u0004\b^\u0010\r\"\u0004\b_\u0010\u000fR8\u0010`\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\n2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\n8\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0010\u001a\u0004\ba\u0010\r\"\u0004\bb\u0010\u000fR*\u0010c\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bd\u0010\u0007\"\u0004\be\u0010\tR*\u0010f\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bg\u0010\u0007\"\u0004\bh\u0010\tR*\u0010i\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bj\u0010\u0007\"\u0004\bk\u0010\tR8\u0010l\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\n2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\n8\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0002\u0010\u0010\u001a\u0004\bm\u0010\r\"\u0004\bn\u0010\u000fR&\u0010o\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bp\u0010\u0007\"\u0004\bq\u0010\tR0\u0010r\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\bs\u0010\u0002\u001a\u0004\bt\u0010\u0007\"\u0004\bu\u0010\tR*\u0010v\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bw\u0010\u0007\"\u0004\bx\u0010\tR*\u0010y\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bz\u0010\u0007\"\u0004\b{\u0010\tR&\u0010|\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b}\u0010\u0014\"\u0004\b~\u0010\u0016R3\u0010\u007f\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0017\n\u0000\u0012\u0005\b\u0080\u0001\u0010\u0002\u001a\u0005\b\u0081\u0001\u0010\u0007\"\u0005\b\u0082\u0001\u0010\tR)\u0010\u0083\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0084\u0001\u0010\u0014\"\u0005\b\u0085\u0001\u0010\u0016R0\u0010\u0086\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0017\n\u0000\u0012\u0005\b\u0087\u0001\u0010\u0002\u001a\u0005\b\u0088\u0001\u0010\u0014\"\u0005\b\u0089\u0001\u0010\u0016R)\u0010\u008a\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u008b\u0001\u0010\u0014\"\u0005\b\u008c\u0001\u0010\u0016R)\u0010\u008d\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u008e\u0001\u0010\u0014\"\u0005\b\u008f\u0001\u0010\u0016R)\u0010\u0090\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0091\u0001\u0010\u0014\"\u0005\b\u0092\u0001\u0010\u0016R)\u0010\u0093\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0094\u0001\u0010\u0014\"\u0005\b\u0095\u0001\u0010\u0016R)\u0010\u0096\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0097\u0001\u0010\u0014\"\u0005\b\u0098\u0001\u0010\u0016R)\u0010\u0099\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u009a\u0001\u0010\u0014\"\u0005\b\u009b\u0001\u0010\u0016R)\u0010\u009c\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u009d\u0001\u0010\u0014\"\u0005\b\u009e\u0001\u0010\u0016R)\u0010\u009f\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b \u0001\u0010\u0014\"\u0005\b¡\u0001\u0010\u0016R)\u0010¢\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b£\u0001\u0010\u0014\"\u0005\b¤\u0001\u0010\u0016R)\u0010¥\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b¦\u0001\u0010\u0014\"\u0005\b§\u0001\u0010\u0016R)\u0010¨\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b©\u0001\u0010\u0014\"\u0005\bª\u0001\u0010\u0016R;\u0010«\u0001\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\n2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\n8\u0006@FX\u0087\u000e¢\u0006\u0012\n\u0002\u0010\u0010\u001a\u0005\b¬\u0001\u0010\r\"\u0005\b\u00ad\u0001\u0010\u000fR)\u0010®\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b¯\u0001\u0010\u0014\"\u0005\b°\u0001\u0010\u0016R-\u0010±\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b²\u0001\u0010\u0007\"\u0005\b³\u0001\u0010\tR-\u0010´\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bµ\u0001\u0010\u0007\"\u0005\b¶\u0001\u0010\tR-\u0010·\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b¸\u0001\u0010\u0007\"\u0005\b¹\u0001\u0010\tR)\u0010º\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b»\u0001\u0010\u0014\"\u0005\b¼\u0001\u0010\u0016R;\u0010½\u0001\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\n2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\n8\u0006@FX\u0087\u000e¢\u0006\u0012\n\u0002\u0010\u0010\u001a\u0005\b¾\u0001\u0010\r\"\u0005\b¿\u0001\u0010\u000fR;\u0010À\u0001\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\n2\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\n8\u0006@FX\u0087\u000e¢\u0006\u0012\n\u0002\u0010\u0010\u001a\u0005\bÁ\u0001\u0010\r\"\u0005\bÂ\u0001\u0010\u000fR)\u0010Ã\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÄ\u0001\u0010\u0007\"\u0005\bÅ\u0001\u0010\tR)\u0010Æ\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÇ\u0001\u0010\u0014\"\u0005\bÈ\u0001\u0010\u0016R-\u0010É\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÊ\u0001\u0010\u0007\"\u0005\bË\u0001\u0010\tR-\u0010Ì\u0001\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÍ\u0001\u0010\u0007\"\u0005\bÎ\u0001\u0010\tR)\u0010Ï\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÐ\u0001\u0010\u0014\"\u0005\bÑ\u0001\u0010\u0016R)\u0010Ò\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÓ\u0001\u0010\u0014\"\u0005\bÔ\u0001\u0010\u0016R)\u0010Õ\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÖ\u0001\u0010\u0014\"\u0005\b×\u0001\u0010\u0016R)\u0010Ø\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÙ\u0001\u0010\u0014\"\u0005\bÚ\u0001\u0010\u0016R)\u0010Û\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bÜ\u0001\u0010\u0014\"\u0005\bÝ\u0001\u0010\u0016R)\u0010Þ\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bß\u0001\u0010\u0014\"\u0005\bà\u0001\u0010\u0016R)\u0010á\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bâ\u0001\u0010\u0014\"\u0005\bã\u0001\u0010\u0016R)\u0010ä\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bå\u0001\u0010\u0014\"\u0005\bæ\u0001\u0010\u0016R)\u0010ç\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bè\u0001\u0010\u0014\"\u0005\bé\u0001\u0010\u0016R)\u0010ê\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bë\u0001\u0010\u0014\"\u0005\bì\u0001\u0010\u0016R)\u0010í\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bî\u0001\u0010\u0014\"\u0005\bï\u0001\u0010\u0016R)\u0010ð\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bñ\u0001\u0010\u0014\"\u0005\bò\u0001\u0010\u0016R)\u0010ó\u0001\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118\u0006@FX\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\bô\u0001\u0010\u0014\"\u0005\bõ\u0001\u0010\u0016¨\u0006\u0089\u0002"}, d2 = {"Lorg/jetbrains/kotlin/cli/common/arguments/K2JVMCompilerArguments;", "Lorg/jetbrains/kotlin/cli/common/arguments/CommonCompilerArguments;", "()V", "value", "", "abiStability", "getAbiStability", "()Ljava/lang/String;", "setAbiStability", "(Ljava/lang/String;)V", "", "additionalJavaModules", "getAdditionalJavaModules", "()[Ljava/lang/String;", "setAdditionalJavaModules", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "", "allowNoSourceFiles", "getAllowNoSourceFiles", "()Z", "setAllowNoSourceFiles", "(Z)V", "allowUnstableDependencies", "getAllowUnstableDependencies", "setAllowUnstableDependencies", "assertionsMode", "getAssertionsMode", "setAssertionsMode", "backendThreads", "getBackendThreads", "setBackendThreads", "buildFile", "getBuildFile", "setBuildFile", ModuleXmlParser.CLASSPATH, "getClasspath", "setClasspath", "compileJava", "getCompileJava", "setCompileJava", "declarationsOutputPath", "getDeclarationsOutputPath", "setDeclarationsOutputPath", "defaultScriptExtension", "getDefaultScriptExtension", "setDefaultScriptExtension", FirebaseAnalytics.Param.DESTINATION, "getDestination", "setDestination", "disableStandardScript", "getDisableStandardScript", "setDisableStandardScript", "doNotClearBindingContext", "getDoNotClearBindingContext", "setDoNotClearBindingContext", "emitJvmTypeAnnotations", "getEmitJvmTypeAnnotations", "setEmitJvmTypeAnnotations", "enableDebugMode", "getEnableDebugMode", "setEnableDebugMode", "enableIrInliner", "getEnableIrInliner", "setEnableIrInliner", "enableJvmPreview", "getEnableJvmPreview", "setEnableJvmPreview", "enhanceTypeParameterTypesToDefNotNull", "getEnhanceTypeParameterTypesToDefNotNull", "setEnhanceTypeParameterTypesToDefNotNull", "expression", "getExpression", "setExpression", "friendPaths", "getFriendPaths", "setFriendPaths", "includeRuntime", "getIncludeRuntime", "setIncludeRuntime", "inheritMultifileParts", "getInheritMultifileParts", "setInheritMultifileParts", "javaModulePath", "getJavaModulePath", "setJavaModulePath", "javaPackagePrefix", "getJavaPackagePrefix", "setJavaPackagePrefix", "javaParameters", "getJavaParameters$annotations", "getJavaParameters", "setJavaParameters", ModuleXmlParser.JAVA_SOURCE_ROOTS, "getJavaSourceRoots", "setJavaSourceRoots", "javacArguments", "getJavacArguments", "setJavacArguments", "jdkHome", "getJdkHome", "setJdkHome", "jdkRelease", "getJdkRelease", "setJdkRelease", "jspecifyAnnotations", "getJspecifyAnnotations", "setJspecifyAnnotations", "jsr305", "getJsr305", "setJsr305", "jvmDefault", "getJvmDefault", "setJvmDefault", "jvmTarget", "getJvmTarget$annotations", "getJvmTarget", "setJvmTarget", "klibLibraries", "getKlibLibraries", "setKlibLibraries", "lambdas", "getLambdas", "setLambdas", "linkViaSignatures", "getLinkViaSignatures", "setLinkViaSignatures", "moduleName", "getModuleName$annotations", "getModuleName", "setModuleName", "noCallAssertions", "getNoCallAssertions", "setNoCallAssertions", "noJdk", "getNoJdk$annotations", "getNoJdk", "setNoJdk", "noKotlinNothingValueException", "getNoKotlinNothingValueException", "setNoKotlinNothingValueException", "noNewJavaAnnotationTargets", "getNoNewJavaAnnotationTargets", "setNoNewJavaAnnotationTargets", "noOptimize", "getNoOptimize", "setNoOptimize", "noOptimizedCallableReferences", "getNoOptimizedCallableReferences", "setNoOptimizedCallableReferences", "noParamAssertions", "getNoParamAssertions", "setNoParamAssertions", "noReceiverAssertions", "getNoReceiverAssertions", "setNoReceiverAssertions", "noReflect", "getNoReflect", "setNoReflect", "noResetJarTimestamps", "getNoResetJarTimestamps", "setNoResetJarTimestamps", "noSourceDebugExtension", "getNoSourceDebugExtension", "setNoSourceDebugExtension", "noStdlib", "getNoStdlib", "setNoStdlib", "noUnifiedNullChecks", "getNoUnifiedNullChecks", "setNoUnifiedNullChecks", "nullabilityAnnotations", "getNullabilityAnnotations", "setNullabilityAnnotations", "oldInnerClassesLogic", "getOldInnerClassesLogic", "setOldInnerClassesLogic", "profileCompilerCommand", "getProfileCompilerCommand", "setProfileCompilerCommand", "repeatCompileModules", "getRepeatCompileModules", "setRepeatCompileModules", "samConversions", "getSamConversions", "setSamConversions", "sanitizeParentheses", "getSanitizeParentheses", "setSanitizeParentheses", "scriptResolverEnvironment", "getScriptResolverEnvironment", "setScriptResolverEnvironment", "scriptTemplates", "getScriptTemplates", "setScriptTemplates", "serializeIr", "getSerializeIr", "setSerializeIr", "strictMetadataVersionSemantics", "getStrictMetadataVersionSemantics", "setStrictMetadataVersionSemantics", "stringConcat", "getStringConcat", "setStringConcat", "supportCompatqualCheckerFrameworkAnnotations", "getSupportCompatqualCheckerFrameworkAnnotations", "setSupportCompatqualCheckerFrameworkAnnotations", "suppressDeprecatedJvmTargetWarning", "getSuppressDeprecatedJvmTargetWarning", "setSuppressDeprecatedJvmTargetWarning", "suppressMissingBuiltinsError", "getSuppressMissingBuiltinsError", "setSuppressMissingBuiltinsError", "typeEnhancementImprovementsInStrictMode", "getTypeEnhancementImprovementsInStrictMode", "setTypeEnhancementImprovementsInStrictMode", "useFastJarFileSystem", "getUseFastJarFileSystem", "setUseFastJarFileSystem", "useJavac", "getUseJavac", "setUseJavac", "useKapt4", "getUseKapt4", "setUseKapt4", "useOldBackend", "getUseOldBackend", "setUseOldBackend", "useOldClassFilesReading", "getUseOldClassFilesReading", "setUseOldClassFilesReading", "useOldInlineClassesManglingScheme", "getUseOldInlineClassesManglingScheme", "setUseOldInlineClassesManglingScheme", "useTypeTable", "getUseTypeTable", "setUseTypeTable", "validateBytecode", "getValidateBytecode", "setValidateBytecode", "validateIr", "getValidateIr", "setValidateIr", "valueClasses", "getValueClasses", "setValueClasses", "checkPlatformSpecificSettings", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "collector", "Lorg/jetbrains/kotlin/cli/common/messages/MessageCollector;", "configureAnalysisFlags", "", "Lorg/jetbrains/kotlin/config/AnalysisFlag;", "", "languageVersion", "Lorg/jetbrains/kotlin/config/LanguageVersion;", "configureLanguageFeatures", "Lorg/jetbrains/kotlin/config/LanguageFeature;", "Lorg/jetbrains/kotlin/config/LanguageFeature$State;", "copyOf", "Lorg/jetbrains/kotlin/cli/common/arguments/Freezable;", "defaultLanguageVersion", "Companion", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class K2JVMCompilerArguments extends CommonCompilerArguments {
    private static final long serialVersionUID = 0;

    @Argument(description = "When using unstable compiler features such as FIR, use 'stable' to mark generated class files as stable\nto prevent diagnostics from stable compilers at the call site.\nWhen using the JVM IR backend, conversely, use 'unstable' to mark generated class files as unstable\nto force diagnostics to be reported.", value = "-Xabi-stability", valueDescription = "{stable|unstable}")
    private String abiStability;

    @Argument(description = "Root modules to resolve in addition to the initial modules,\nor all modules on the module path if <module> is ALL-MODULE-PATH", value = "-Xadd-modules", valueDescription = "<module[,]>")
    private String[] additionalJavaModules;

    @Argument(description = "Allow no source files", value = "-Xallow-no-source-files")
    private boolean allowNoSourceFiles;

    @Argument(description = "Do not report errors on classes in dependencies, which were compiled by an unstable version of the Kotlin compiler", value = "-Xallow-unstable-dependencies")
    private boolean allowUnstableDependencies;

    @Argument(deprecatedName = "-module", description = "Path to the .xml build file to compile", value = "-Xbuild-file", valueDescription = "<path>")
    private String buildFile;

    @Argument(description = "List of directories and JAR/ZIP archives to search for user class files", shortName = "-cp", value = "-classpath", valueDescription = "<path>")
    private String classpath;

    @Argument(description = "Reuse javac analysis and compile Java source files", value = "-Xcompile-java")
    private boolean compileJava;

    @Argument(description = "Path to JSON file to dump Java to Kotlin declaration mappings", value = "-Xdump-declarations-to", valueDescription = "<path>")
    private String declarationsOutputPath;

    @Argument(description = "Compile expressions and unrecognized scripts passed with the -script argument as scripts with given filename extension", value = "-Xdefault-script-extension", valueDescription = "<script filename extension>")
    private String defaultScriptExtension;

    @Argument(description = "Destination for generated class files", value = "-d", valueDescription = "<directory|jar>")
    private String destination;

    @Argument(description = "Disable standard kotlin script support", value = "-Xdisable-standard-script")
    private boolean disableStandardScript;

    @Argument(description = "When using the IR backend, do not clear BindingContext between psi2ir and lowerings", value = "-Xir-do-not-clear-binding-context")
    private boolean doNotClearBindingContext;

    @Argument(description = "Emit JVM type annotations in bytecode", value = "-Xemit-jvm-type-annotations")
    private boolean emitJvmTypeAnnotations;

    @Argument(description = "Enable debug mode for compilation.\nCurrently this includes spilling all variables in a suspending context regardless their liveness.", value = "-Xdebug")
    private boolean enableDebugMode;

    @Argument(description = "Inline functions using IR inliner instead of bytecode inliner", value = "-Xir-inliner")
    private boolean enableIrInliner;

    @Argument(description = "Allow using features from Java language that are in preview phase.\nWorks as `--enable-preview` in Java. All class files are marked as preview-generated thus it won't be possible to use them in release environment", value = "-Xjvm-enable-preview")
    private boolean enableJvmPreview;

    @Argument(description = "Enhance not null annotated type parameter's types to definitely not null types (@NotNull T => T & Any)", value = "-Xenhance-type-parameter-types-to-def-not-null")
    private boolean enhanceTypeParameterTypesToDefNotNull;

    @Argument(description = "Evaluate the given string as a Kotlin script", shortName = "-e", value = "-expression")
    private String expression;

    @Argument(description = "Paths to output directories for friend modules (whose internals should be visible)", value = "-Xfriend-paths", valueDescription = "<path>")
    private String[] friendPaths;

    @Argument(description = "Include Kotlin runtime into the resulting JAR", value = "-include-runtime")
    private boolean includeRuntime;

    @Argument(description = "Compile multifile classes as a hierarchy of parts and facade", value = "-Xmultifile-parts-inherit")
    private boolean inheritMultifileParts;

    @Argument(description = "Paths where to find Java 9+ modules", value = "-Xmodule-path", valueDescription = "<path>")
    private String javaModulePath;

    @Argument(description = "Package prefix for Java files", value = "-Xjava-package-prefix")
    private String javaPackagePrefix;

    @Argument(description = "Generate metadata for Java 1.8 reflection on method parameters", value = "-java-parameters")
    private boolean javaParameters;

    @Argument(description = "Paths to directories with Java source files", value = "-Xjava-source-roots", valueDescription = "<path>")
    private String[] javaSourceRoots;

    @Argument(description = "Java compiler arguments", value = "-Xjavac-arguments", valueDescription = "<option[,]>")
    private String[] javacArguments;

    @Argument(description = "Include a custom JDK from the specified location into the classpath instead of the default JAVA_HOME", value = "-jdk-home", valueDescription = "<path>")
    private String jdkHome;

    @Argument(description = "Compile against the specified JDK API version, similarly to javac's `-release`. Requires JDK 9 or newer.\nSupported versions depend on the used JDK; for JDK 17+ supported versions are 1.8, 9, 10, ..., 21.\nAlso sets `-jvm-target` value equal to the selected JDK version", value = "-Xjdk-release", valueDescription = "<version>")
    private String jdkRelease;

    @Argument(description = "Specify behavior for jspecify annotations.\nDefault value is 'warn'", value = "-Xjspecify-annotations", valueDescription = "ignore|strict|warn")
    private String jspecifyAnnotations;

    @Argument(deprecatedName = "-Xjsr305-annotations", description = "Specify behavior for JSR-305 nullability annotations:\n-Xjsr305={ignore/strict/warn}                   globally (all non-@UnderMigration annotations)\n-Xjsr305=under-migration:{ignore/strict/warn}   all @UnderMigration annotations\n-Xjsr305=@<fq.name>:{ignore/strict/warn}        annotation with the given fully qualified class name\nModes:\n  * ignore\n  * strict (experimental; treat as other supported nullability annotations)\n  * warn (report a warning)", value = "-Xjsr305", valueDescription = "{ignore/strict/warn}|under-migration:{ignore/strict/warn}|@<fq.name>:{ignore/strict/warn}")
    private String[] jsr305;

    @Argument(description = "Target version of the generated JVM bytecode (1.8, 9, 10, ..., 21), default is 1.8", value = "-jvm-target", valueDescription = "<version>")
    private String jvmTarget;

    @Argument(description = "Paths to cross-platform libraries in .klib format", value = "-Xklib", valueDescription = "<path>")
    private String klibLibraries;

    @Argument(description = "Select code generation scheme for lambdas.\n-Xlambdas=indy                      Generate lambdas using `invokedynamic` with `LambdaMetafactory.metafactory`. Requires `-jvm-target 1.8` or greater.\n                                    Lambda objects created using `LambdaMetafactory.metafactory` will have different `toString()`.\n-Xlambdas=class                     Generate lambdas as explicit classes", value = "-Xlambdas", valueDescription = "{class|indy}")
    private String lambdas;

    @Argument(description = "Link JVM IR symbols via signatures, instead of descriptors.\nThis mode is slower, but can be useful in troubleshooting problems with the JVM IR backend", value = "-Xlink-via-signatures")
    private boolean linkViaSignatures;

    @Argument(description = "Name of the generated .kotlin_module file", value = "-module-name", valueDescription = "<name>")
    private String moduleName;

    @Argument(description = "Don't generate not-null assertions for arguments of platform types", value = "-Xno-call-assertions")
    private boolean noCallAssertions;

    @Argument(description = "Don't automatically include the Java runtime into the classpath", value = "-no-jdk")
    private boolean noJdk;

    @Argument(description = "Do not use KotlinNothingValueException available since 1.4", value = "-Xno-kotlin-nothing-value-exception")
    private boolean noKotlinNothingValueException;

    @Argument(description = "Do not generate Java 1.8+ targets for Kotlin annotation classes", value = "-Xno-new-java-annotation-targets")
    private boolean noNewJavaAnnotationTargets;

    @Argument(description = "Disable optimizations", value = "-Xno-optimize")
    private boolean noOptimize;

    @Argument(description = "Do not use optimized callable reference superclasses available from 1.4", value = "-Xno-optimized-callable-references")
    private boolean noOptimizedCallableReferences;

    @Argument(description = "Don't generate not-null assertions on parameters of methods accessible from Java", value = "-Xno-param-assertions")
    private boolean noParamAssertions;

    @Argument(description = "Don't generate not-null assertion for extension receiver arguments of platform types", value = "-Xno-receiver-assertions")
    private boolean noReceiverAssertions;

    @Argument(description = "Don't automatically include Kotlin reflection into the classpath", value = "-no-reflect")
    private boolean noReflect;

    @Argument(description = "Do not reset jar entry timestamps to a fixed date", value = "-Xno-reset-jar-timestamps")
    private boolean noResetJarTimestamps;

    @Argument(description = "Do not generate @kotlin.jvm.internal.SourceDebugExtension annotation on a class with the copy of SMAP", value = "-Xno-source-debug-extension")
    private boolean noSourceDebugExtension;

    @Argument(description = "Don't automatically include the Kotlin/JVM stdlib and Kotlin reflection into the classpath", value = "-no-stdlib")
    private boolean noStdlib;

    @Argument(description = "Use pre-1.4 exception types in null checks instead of java.lang.NPE. See KT-22275 for more details", value = "-Xno-unified-null-checks")
    private boolean noUnifiedNullChecks;

    @Argument(description = "Specify behavior for specific Java nullability annotations (provided with fully qualified package name)\nModes:\n  * ignore\n  * strict\n  * warn (report a warning)", value = "-Xnullability-annotations", valueDescription = "@<fq.name>:{ignore/strict/warn}")
    private String[] nullabilityAnnotations;

    @Argument(description = "Use old logic for generation of InnerClasses attributes.\nThis option is deprecated and will be deleted in future versions.", value = "-Xuse-old-innerclasses-logic")
    private boolean oldInnerClassesLogic;

    @Argument(description = "Debug option: Run compiler with async profiler and save snapshots to `outputDir`; `command` is passed to async-profiler on start.\n`profilerPath` is a path to libasyncProfiler.so; async-profiler.jar should be on the compiler classpath.\nIf it's not on the classpath, the compiler will attempt to load async-profiler.jar from the containing directory of profilerPath.\nExample: -Xprofile=<PATH_TO_ASYNC_PROFILER>/async-profiler/build/libasyncProfiler.so:event=cpu,interval=1ms,threads,start:<SNAPSHOT_DIR_PATH>", value = "-Xprofile", valueDescription = "<profilerPath:command:outputDir>")
    private String profileCompilerCommand;

    @Argument(description = "Debug option: Repeats modules compilation <number> times", value = "-Xrepeat", valueDescription = "<number>")
    private String repeatCompileModules;

    @Argument(description = "Select code generation scheme for SAM conversions.\n-Xsam-conversions=indy              Generate SAM conversions using `invokedynamic` with `LambdaMetafactory.metafactory`. Requires `-jvm-target 1.8` or greater.\n-Xsam-conversions=class             Generate SAM conversions as explicit classes", value = "-Xsam-conversions", valueDescription = "{class|indy}")
    private String samConversions;

    @Argument(description = "Transform '(' and ')' in method names to some other character sequence.\nThis mode can BREAK BINARY COMPATIBILITY and is only supposed to be used to workaround\nproblems with parentheses in identifiers on certain platforms", value = "-Xsanitize-parentheses")
    private boolean sanitizeParentheses;

    @Argument(description = "Script resolver environment in key-value pairs (the value could be quoted and escaped)", value = "-Xscript-resolver-environment", valueDescription = "<key=value[,]>")
    private String[] scriptResolverEnvironment;

    @Argument(description = "Script definition template classes", value = "-script-templates", valueDescription = "<fully qualified class name[,]>")
    private String[] scriptTemplates;

    @Argument(description = "Generate metadata with strict version semantics (see kdoc on Metadata.extraInt)", value = "-Xgenerate-strict-metadata-version")
    private boolean strictMetadataVersionSemantics;

    @Argument(description = "Select code generation scheme for string concatenation.\n-Xstring-concat=indy-with-constants   Concatenate strings using `invokedynamic` `makeConcatWithConstants`. Requires `-jvm-target 9` or greater.\n-Xstring-concat=indy                Concatenate strings using `invokedynamic` `makeConcat`. Requires `-jvm-target 9` or greater.\n-Xstring-concat=inline              Concatenate strings using `StringBuilder`\ndefault: `indy-with-constants` for JVM target 9 or greater, `inline` otherwise", value = "-Xstring-concat", valueDescription = "{indy-with-constants|indy|inline}")
    private String stringConcat;

    @Argument(description = "Specify behavior for Checker Framework compatqual annotations (NullableDecl/NonNullDecl).\nDefault value is 'enable'", value = "-Xsupport-compatqual-checker-framework-annotations", valueDescription = "enable|disable")
    private String supportCompatqualCheckerFrameworkAnnotations;

    @Argument(description = "Suppress deprecation warning about deprecated JVM target versions.\nThis option has no effect and will be deleted in a future version.", value = "-Xsuppress-deprecated-jvm-target-warning")
    private boolean suppressDeprecatedJvmTargetWarning;

    @Argument(description = "Suppress the \"cannot access built-in declaration\" error (useful with -no-stdlib)", value = "-Xsuppress-missing-builtins-error")
    private boolean suppressMissingBuiltinsError;

    @Argument(description = "Enable strict mode for some improvements in the type enhancement for loaded Java types based on nullability annotations,\nincluding freshly supported reading of the type use annotations from class files.\nSee KT-45671 for more details", value = "-Xtype-enhancement-improvements-strict-mode")
    private boolean typeEnhancementImprovementsInStrictMode;

    @Argument(description = "Use fast implementation on Jar FS. This may speed up compilation time, but currently it's an experimental mode", value = "-Xuse-fast-jar-file-system")
    private boolean useFastJarFileSystem;

    @Argument(description = "Use javac for Java source and class files analysis", value = "-Xuse-javac")
    private boolean useJavac;

    @Argument(description = "Enable the experimental KAPT 4.", value = "-Xuse-kapt4")
    private boolean useKapt4;

    @Argument(description = "Use the old JVM backend", value = "-Xuse-old-backend")
    private boolean useOldBackend;

    @Argument(description = "Use old class files reading implementation. This may slow down the build and cause problems with Groovy interop.\nShould be used in case of problems with the new implementation", value = "-Xuse-old-class-files-reading")
    private boolean useOldClassFilesReading;

    @Argument(description = "Use 1.4 inline classes mangling scheme instead of 1.4.30 one", value = "-Xuse-14-inline-classes-mangling-scheme")
    private boolean useOldInlineClassesManglingScheme;

    @Argument(description = "Use type table in metadata serialization", value = "-Xuse-type-table")
    private boolean useTypeTable;

    @Argument(description = "Validate generated JVM bytecode before and after optimizations", value = "-Xvalidate-bytecode")
    private boolean validateBytecode;

    @Argument(description = "Validate IR before and after lowering", value = "-Xvalidate-ir")
    private boolean validateIr;

    @Argument(description = "Enable experimental value classes", value = "-Xvalue-classes")
    private boolean valueClasses;

    @Argument(description = "When using the IR backend, run lowerings by file in N parallel threads.\n0 means use a thread per processor core.\nDefault value is 1", value = "-Xbackend-threads", valueDescription = "<N>")
    private String backendThreads = SchemaSymbols.ATTVAL_TRUE_1;

    @Argument(description = "Assert calls behaviour\n-Xassertions=always-enable:  enable, ignore jvm assertion settings;\n-Xassertions=always-disable: disable, ignore jvm assertion settings;\n-Xassertions=jvm:            enable, depend on jvm assertion settings;\n-Xassertions=legacy:         calculate condition on each call, check depends on jvm assertion settings in the kotlin package;\ndefault: legacy", value = "-Xassertions", valueDescription = "{always-enable|always-disable|jvm|legacy}")
    private String assertionsMode = JVMAssertionsMode.DEFAULT.getDescription();

    @Argument(description = "Emit JVM default methods for interface declarations with bodies. Default is 'disable'.\n-Xjvm-default=all                Generate JVM default methods for all interface declarations with bodies in the module.\n                                 Do not generate DefaultImpls stubs for interface declarations with bodies, which are generated by default\n                                 in the 'disable' mode. If interface inherits a method with body from an interface compiled in the 'disable'\n                                 mode and doesn't override it, then a DefaultImpls stub will be generated for it.\n                                 BREAKS BINARY COMPATIBILITY if some client code relies on the presence of DefaultImpls classes.\n                                 Note that if interface delegation is used, all interface methods are delegated.\n-Xjvm-default=all-compatibility  In addition to the 'all' mode, generate compatibility stubs in the DefaultImpls classes.\n                                 Compatibility stubs could be useful for library and runtime authors to keep backward binary compatibility\n                                 for existing clients compiled against previous library versions.\n                                 'all' and 'all-compatibility' modes are changing the library ABI surface that will be used by clients after\n                                 the recompilation of the library. In that sense, clients might be incompatible with previous library\n                                 versions. This usually means that proper library versioning is required, e.g. major version increase in SemVer.\n                                 In case of inheritance from a Kotlin interface compiled in 'all' or 'all-compatibility' modes, DefaultImpls\n                                 compatibility stubs will invoke the default method of the interface with standard JVM runtime resolution semantics.\n                                 Perform additional compatibility checks for classes inheriting generic interfaces where in some cases\n                                 additional implicit method with specialized signatures was generated in the 'disable' mode:\n                                 unlike in the 'disable' mode, the compiler will report an error if such method is not overridden explicitly\n                                 and the class is not annotated with @JvmDefaultWithoutCompatibility (see KT-39603 for more details).\n-Xjvm-default=disable            Default behavior. Do not generate JVM default methods.", value = "-Xjvm-default", valueDescription = "{all|all-compatibility|disable}")
    private String jvmDefault = JvmDefaultMode.DEFAULT.getDescription();

    @Argument(description = "Save IR to metadata (EXPERIMENTAL)", value = "-Xserialize-ir", valueDescription = "{none|inline|all}")
    private String serializeIr = "none";

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.BOOLEAN_FALSE_DEFAULT)
    public static /* synthetic */ void getJavaParameters$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.JVM_TARGET_VERSIONS)
    public static /* synthetic */ void getJvmTarget$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.STRING_NULL_DEFAULT)
    public static /* synthetic */ void getModuleName$annotations() {
    }

    @GradleOption(gradleInputType = GradleInputTypes.INPUT, shouldGenerateDeprecatedKotlinOptions = true, value = DefaultValue.BOOLEAN_FALSE_DEFAULT)
    public static /* synthetic */ void getNoJdk$annotations() {
    }

    public final String getDestination() {
        return this.destination;
    }

    public final void setDestination(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.destination = str;
    }

    public final String getClasspath() {
        return this.classpath;
    }

    public final void setClasspath(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.classpath = str;
    }

    public final boolean getIncludeRuntime() {
        return this.includeRuntime;
    }

    public final void setIncludeRuntime(boolean z) {
        checkFrozen();
        this.includeRuntime = z;
    }

    public final String getJdkHome() {
        return this.jdkHome;
    }

    public final void setJdkHome(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.jdkHome = str;
    }

    public final boolean getNoJdk() {
        return this.noJdk;
    }

    public final void setNoJdk(boolean z) {
        checkFrozen();
        this.noJdk = z;
    }

    public final boolean getNoStdlib() {
        return this.noStdlib;
    }

    public final void setNoStdlib(boolean z) {
        checkFrozen();
        this.noStdlib = z;
    }

    public final boolean getNoReflect() {
        return this.noReflect;
    }

    public final void setNoReflect(boolean z) {
        checkFrozen();
        this.noReflect = z;
    }

    public final String getExpression() {
        return this.expression;
    }

    public final void setExpression(String str) {
        checkFrozen();
        this.expression = str;
    }

    public final String[] getScriptTemplates() {
        return this.scriptTemplates;
    }

    public final void setScriptTemplates(String[] strArr) {
        checkFrozen();
        this.scriptTemplates = strArr;
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

    public final String getJvmTarget() {
        return this.jvmTarget;
    }

    public final void setJvmTarget(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.jvmTarget = str;
    }

    public final boolean getJavaParameters() {
        return this.javaParameters;
    }

    public final void setJavaParameters(boolean z) {
        checkFrozen();
        this.javaParameters = z;
    }

    public final boolean getUseOldBackend() {
        return this.useOldBackend;
    }

    public final void setUseOldBackend(boolean z) {
        checkFrozen();
        this.useOldBackend = z;
    }

    public final boolean getAllowUnstableDependencies() {
        return this.allowUnstableDependencies;
    }

    public final void setAllowUnstableDependencies(boolean z) {
        checkFrozen();
        this.allowUnstableDependencies = z;
    }

    public final String getAbiStability() {
        return this.abiStability;
    }

    public final void setAbiStability(String str) {
        checkFrozen();
        this.abiStability = str;
    }

    public final boolean getDoNotClearBindingContext() {
        return this.doNotClearBindingContext;
    }

    public final void setDoNotClearBindingContext(boolean z) {
        checkFrozen();
        this.doNotClearBindingContext = z;
    }

    public final String getBackendThreads() {
        return this.backendThreads;
    }

    public final void setBackendThreads(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        checkFrozen();
        this.backendThreads = value;
    }

    public final String getJavaModulePath() {
        return this.javaModulePath;
    }

    public final void setJavaModulePath(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.javaModulePath = str;
    }

    public final String[] getAdditionalJavaModules() {
        return this.additionalJavaModules;
    }

    public final void setAdditionalJavaModules(String[] strArr) {
        checkFrozen();
        this.additionalJavaModules = strArr;
    }

    public final boolean getNoCallAssertions() {
        return this.noCallAssertions;
    }

    public final void setNoCallAssertions(boolean z) {
        checkFrozen();
        this.noCallAssertions = z;
    }

    public final boolean getNoReceiverAssertions() {
        return this.noReceiverAssertions;
    }

    public final void setNoReceiverAssertions(boolean z) {
        checkFrozen();
        this.noReceiverAssertions = z;
    }

    public final boolean getNoParamAssertions() {
        return this.noParamAssertions;
    }

    public final void setNoParamAssertions(boolean z) {
        checkFrozen();
        this.noParamAssertions = z;
    }

    public final boolean getNoOptimize() {
        return this.noOptimize;
    }

    public final void setNoOptimize(boolean z) {
        checkFrozen();
        this.noOptimize = z;
    }

    public final String getAssertionsMode() {
        return this.assertionsMode;
    }

    public final void setAssertionsMode(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = JVMAssertionsMode.DEFAULT.getDescription();
        }
        this.assertionsMode = str;
    }

    public final String getBuildFile() {
        return this.buildFile;
    }

    public final void setBuildFile(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.buildFile = str;
    }

    public final boolean getInheritMultifileParts() {
        return this.inheritMultifileParts;
    }

    public final void setInheritMultifileParts(boolean z) {
        checkFrozen();
        this.inheritMultifileParts = z;
    }

    public final boolean getUseTypeTable() {
        return this.useTypeTable;
    }

    public final void setUseTypeTable(boolean z) {
        checkFrozen();
        this.useTypeTable = z;
    }

    public final boolean getUseOldClassFilesReading() {
        return this.useOldClassFilesReading;
    }

    public final void setUseOldClassFilesReading(boolean z) {
        checkFrozen();
        this.useOldClassFilesReading = z;
    }

    public final boolean getUseFastJarFileSystem() {
        return this.useFastJarFileSystem;
    }

    public final void setUseFastJarFileSystem(boolean z) {
        checkFrozen();
        this.useFastJarFileSystem = z;
    }

    public final String getDeclarationsOutputPath() {
        return this.declarationsOutputPath;
    }

    public final void setDeclarationsOutputPath(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.declarationsOutputPath = str;
    }

    public final boolean getSuppressMissingBuiltinsError() {
        return this.suppressMissingBuiltinsError;
    }

    public final void setSuppressMissingBuiltinsError(boolean z) {
        checkFrozen();
        this.suppressMissingBuiltinsError = z;
    }

    public final String[] getScriptResolverEnvironment() {
        return this.scriptResolverEnvironment;
    }

    public final void setScriptResolverEnvironment(String[] strArr) {
        checkFrozen();
        this.scriptResolverEnvironment = strArr;
    }

    public final boolean getUseJavac() {
        return this.useJavac;
    }

    public final void setUseJavac(boolean z) {
        checkFrozen();
        this.useJavac = z;
    }

    public final boolean getCompileJava() {
        return this.compileJava;
    }

    public final void setCompileJava(boolean z) {
        checkFrozen();
        this.compileJava = z;
    }

    public final String[] getJavacArguments() {
        return this.javacArguments;
    }

    public final void setJavacArguments(String[] strArr) {
        checkFrozen();
        this.javacArguments = strArr;
    }

    public final String[] getJavaSourceRoots() {
        return this.javaSourceRoots;
    }

    public final void setJavaSourceRoots(String[] strArr) {
        checkFrozen();
        this.javaSourceRoots = strArr;
    }

    public final String getJavaPackagePrefix() {
        return this.javaPackagePrefix;
    }

    public final void setJavaPackagePrefix(String str) {
        checkFrozen();
        this.javaPackagePrefix = str;
    }

    public final String[] getJsr305() {
        return this.jsr305;
    }

    public final void setJsr305(String[] strArr) {
        checkFrozen();
        this.jsr305 = strArr;
    }

    public final String[] getNullabilityAnnotations() {
        return this.nullabilityAnnotations;
    }

    public final void setNullabilityAnnotations(String[] strArr) {
        checkFrozen();
        this.nullabilityAnnotations = strArr;
    }

    public final String getSupportCompatqualCheckerFrameworkAnnotations() {
        return this.supportCompatqualCheckerFrameworkAnnotations;
    }

    public final void setSupportCompatqualCheckerFrameworkAnnotations(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.supportCompatqualCheckerFrameworkAnnotations = str;
    }

    public final String getJspecifyAnnotations() {
        return this.jspecifyAnnotations;
    }

    public final void setJspecifyAnnotations(String str) {
        checkFrozen();
        this.jspecifyAnnotations = str;
    }

    public final String getJvmDefault() {
        return this.jvmDefault;
    }

    public final void setJvmDefault(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        checkFrozen();
        this.jvmDefault = value;
    }

    public final String getDefaultScriptExtension() {
        return this.defaultScriptExtension;
    }

    public final void setDefaultScriptExtension(String str) {
        checkFrozen();
        this.defaultScriptExtension = str;
    }

    public final boolean getDisableStandardScript() {
        return this.disableStandardScript;
    }

    public final void setDisableStandardScript(boolean z) {
        checkFrozen();
        this.disableStandardScript = z;
    }

    public final boolean getStrictMetadataVersionSemantics() {
        return this.strictMetadataVersionSemantics;
    }

    public final void setStrictMetadataVersionSemantics(boolean z) {
        checkFrozen();
        this.strictMetadataVersionSemantics = z;
    }

    public final boolean getSanitizeParentheses() {
        return this.sanitizeParentheses;
    }

    public final void setSanitizeParentheses(boolean z) {
        checkFrozen();
        this.sanitizeParentheses = z;
    }

    public final String[] getFriendPaths() {
        return this.friendPaths;
    }

    public final void setFriendPaths(String[] strArr) {
        checkFrozen();
        this.friendPaths = strArr;
    }

    public final boolean getAllowNoSourceFiles() {
        return this.allowNoSourceFiles;
    }

    public final void setAllowNoSourceFiles(boolean z) {
        checkFrozen();
        this.allowNoSourceFiles = z;
    }

    public final boolean getEmitJvmTypeAnnotations() {
        return this.emitJvmTypeAnnotations;
    }

    public final void setEmitJvmTypeAnnotations(boolean z) {
        checkFrozen();
        this.emitJvmTypeAnnotations = z;
    }

    public final String getStringConcat() {
        return this.stringConcat;
    }

    public final void setStringConcat(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.stringConcat = str;
    }

    public final String getJdkRelease() {
        return this.jdkRelease;
    }

    public final void setJdkRelease(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.jdkRelease = str;
    }

    public final String getSamConversions() {
        return this.samConversions;
    }

    public final void setSamConversions(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.samConversions = str;
    }

    public final String getLambdas() {
        return this.lambdas;
    }

    public final void setLambdas(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.lambdas = str;
    }

    public final String getKlibLibraries() {
        return this.klibLibraries;
    }

    public final void setKlibLibraries(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.klibLibraries = str;
    }

    public final boolean getNoOptimizedCallableReferences() {
        return this.noOptimizedCallableReferences;
    }

    public final void setNoOptimizedCallableReferences(boolean z) {
        checkFrozen();
        this.noOptimizedCallableReferences = z;
    }

    public final boolean getNoKotlinNothingValueException() {
        return this.noKotlinNothingValueException;
    }

    public final void setNoKotlinNothingValueException(boolean z) {
        checkFrozen();
        this.noKotlinNothingValueException = z;
    }

    public final boolean getNoResetJarTimestamps() {
        return this.noResetJarTimestamps;
    }

    public final void setNoResetJarTimestamps(boolean z) {
        checkFrozen();
        this.noResetJarTimestamps = z;
    }

    public final boolean getNoUnifiedNullChecks() {
        return this.noUnifiedNullChecks;
    }

    public final void setNoUnifiedNullChecks(boolean z) {
        checkFrozen();
        this.noUnifiedNullChecks = z;
    }

    public final boolean getNoSourceDebugExtension() {
        return this.noSourceDebugExtension;
    }

    public final void setNoSourceDebugExtension(boolean z) {
        checkFrozen();
        this.noSourceDebugExtension = z;
    }

    public final String getProfileCompilerCommand() {
        return this.profileCompilerCommand;
    }

    public final void setProfileCompilerCommand(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.profileCompilerCommand = str;
    }

    public final String getRepeatCompileModules() {
        return this.repeatCompileModules;
    }

    public final void setRepeatCompileModules(String str) {
        checkFrozen();
        String str2 = str;
        if (str2 == null || str2.length() == 0) {
            str = null;
        }
        this.repeatCompileModules = str;
    }

    public final boolean getUseOldInlineClassesManglingScheme() {
        return this.useOldInlineClassesManglingScheme;
    }

    public final void setUseOldInlineClassesManglingScheme(boolean z) {
        checkFrozen();
        this.useOldInlineClassesManglingScheme = z;
    }

    public final boolean getEnableJvmPreview() {
        return this.enableJvmPreview;
    }

    public final void setEnableJvmPreview(boolean z) {
        checkFrozen();
        this.enableJvmPreview = z;
    }

    public final boolean getSuppressDeprecatedJvmTargetWarning() {
        return this.suppressDeprecatedJvmTargetWarning;
    }

    public final void setSuppressDeprecatedJvmTargetWarning(boolean z) {
        checkFrozen();
        this.suppressDeprecatedJvmTargetWarning = z;
    }

    public final boolean getTypeEnhancementImprovementsInStrictMode() {
        return this.typeEnhancementImprovementsInStrictMode;
    }

    public final void setTypeEnhancementImprovementsInStrictMode(boolean z) {
        checkFrozen();
        this.typeEnhancementImprovementsInStrictMode = z;
    }

    public final String getSerializeIr() {
        return this.serializeIr;
    }

    public final void setSerializeIr(String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        checkFrozen();
        this.serializeIr = value;
    }

    public final boolean getValidateIr() {
        return this.validateIr;
    }

    public final void setValidateIr(boolean z) {
        checkFrozen();
        this.validateIr = z;
    }

    public final boolean getValidateBytecode() {
        return this.validateBytecode;
    }

    public final void setValidateBytecode(boolean z) {
        checkFrozen();
        this.validateBytecode = z;
    }

    public final boolean getEnhanceTypeParameterTypesToDefNotNull() {
        return this.enhanceTypeParameterTypesToDefNotNull;
    }

    public final void setEnhanceTypeParameterTypesToDefNotNull(boolean z) {
        checkFrozen();
        this.enhanceTypeParameterTypesToDefNotNull = z;
    }

    public final boolean getLinkViaSignatures() {
        return this.linkViaSignatures;
    }

    public final void setLinkViaSignatures(boolean z) {
        checkFrozen();
        this.linkViaSignatures = z;
    }

    public final boolean getEnableDebugMode() {
        return this.enableDebugMode;
    }

    public final void setEnableDebugMode(boolean z) {
        checkFrozen();
        this.enableDebugMode = z;
    }

    public final boolean getNoNewJavaAnnotationTargets() {
        return this.noNewJavaAnnotationTargets;
    }

    public final void setNoNewJavaAnnotationTargets(boolean z) {
        checkFrozen();
        this.noNewJavaAnnotationTargets = z;
    }

    public final boolean getOldInnerClassesLogic() {
        return this.oldInnerClassesLogic;
    }

    public final void setOldInnerClassesLogic(boolean z) {
        checkFrozen();
        this.oldInnerClassesLogic = z;
    }

    public final boolean getValueClasses() {
        return this.valueClasses;
    }

    public final void setValueClasses(boolean z) {
        checkFrozen();
        this.valueClasses = z;
    }

    public final boolean getEnableIrInliner() {
        return this.enableIrInliner;
    }

    public final void setEnableIrInliner(boolean z) {
        checkFrozen();
        this.enableIrInliner = z;
    }

    public final boolean getUseKapt4() {
        return this.useKapt4;
    }

    public final void setUseKapt4(boolean z) {
        checkFrozen();
        this.useKapt4 = z;
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments
    public Map<AnalysisFlag<?>, Object> configureAnalysisFlags(MessageCollector collector, LanguageVersion languageVersion) {
        Unit unit;
        Intrinsics.checkNotNullParameter(collector, "collector");
        Intrinsics.checkNotNullParameter(languageVersion, "languageVersion");
        Map<AnalysisFlag<?>, Object> mapConfigureAnalysisFlags = super.configureAnalysisFlags(collector, languageVersion);
        mapConfigureAnalysisFlags.put(JvmAnalysisFlags.getStrictMetadataVersionSemantics(), Boolean.valueOf(this.strictMetadataVersionSemantics));
        mapConfigureAnalysisFlags.put(JvmAnalysisFlags.getJavaTypeEnhancementState(), new JavaTypeEnhancementStateParser(collector, LanguageVersionSettingsKt.toKotlinVersion(languageVersion)).parse(this.jsr305, this.supportCompatqualCheckerFrameworkAnnotations, this.jspecifyAnnotations, this.nullabilityAnnotations));
        mapConfigureAnalysisFlags.put(AnalysisFlags.getIgnoreDataFlowInAssert(), Boolean.valueOf(JVMAssertionsMode.INSTANCE.fromString(this.assertionsMode) != JVMAssertionsMode.LEGACY));
        JvmDefaultMode jvmDefaultModeFromStringOrNull = JvmDefaultMode.INSTANCE.fromStringOrNull(this.jvmDefault);
        if (jvmDefaultModeFromStringOrNull != null) {
            mapConfigureAnalysisFlags.put(JvmAnalysisFlags.getJvmDefaultMode(), jvmDefaultModeFromStringOrNull);
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            CompilerMessageSeverity compilerMessageSeverity = CompilerMessageSeverity.ERROR;
            StringBuilder sbAppend = new StringBuilder("Unknown -Xjvm-default mode: ").append(this.jvmDefault).append(", supported modes: ");
            JvmDefaultMode[] jvmDefaultModeArrValues = JvmDefaultMode.values();
            ArrayList arrayList = new ArrayList();
            for (JvmDefaultMode jvmDefaultMode : jvmDefaultModeArrValues) {
                String description = jvmDefaultMode.getDescription();
                if (JvmDefaultMode.INSTANCE.fromStringOrNull(description) == null) {
                    description = null;
                }
                if (description != null) {
                    arrayList.add(description);
                }
            }
            MessageCollector.report$default(collector, compilerMessageSeverity, sbAppend.append(arrayList).toString(), null, 4, null);
        }
        mapConfigureAnalysisFlags.put(JvmAnalysisFlags.getInheritMultifileParts(), Boolean.valueOf(this.inheritMultifileParts));
        mapConfigureAnalysisFlags.put(JvmAnalysisFlags.getSanitizeParentheses(), Boolean.valueOf(this.sanitizeParentheses));
        mapConfigureAnalysisFlags.put(JvmAnalysisFlags.getSuppressMissingBuiltinsError(), Boolean.valueOf(this.suppressMissingBuiltinsError));
        mapConfigureAnalysisFlags.put(JvmAnalysisFlags.getEnableJvmPreview(), Boolean.valueOf(this.enableJvmPreview));
        mapConfigureAnalysisFlags.put(AnalysisFlags.getAllowUnstableDependencies(), Boolean.valueOf(this.allowUnstableDependencies || getUseK2() || languageVersion.getUsesK2()));
        mapConfigureAnalysisFlags.put(JvmAnalysisFlags.getDisableUltraLightClasses(), Boolean.valueOf(getDisableUltraLightClasses()));
        mapConfigureAnalysisFlags.put(JvmAnalysisFlags.getUseIR(), Boolean.valueOf(!this.useOldBackend));
        return mapConfigureAnalysisFlags;
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments
    public Map<LanguageFeature, LanguageFeature.State> configureLanguageFeatures(MessageCollector collector) {
        Intrinsics.checkNotNullParameter(collector, "collector");
        Map<LanguageFeature, LanguageFeature.State> mapConfigureLanguageFeatures = super.configureLanguageFeatures(collector);
        if (this.typeEnhancementImprovementsInStrictMode) {
            mapConfigureLanguageFeatures.put(LanguageFeature.TypeEnhancementImprovementsInStrictMode, LanguageFeature.State.ENABLED);
        }
        if (this.enhanceTypeParameterTypesToDefNotNull) {
            mapConfigureLanguageFeatures.put(LanguageFeature.ProhibitUsingNullableTypeParameterAgainstNotNullAnnotated, LanguageFeature.State.ENABLED);
        }
        JvmDefaultMode jvmDefaultModeFromStringOrNull = JvmDefaultMode.INSTANCE.fromStringOrNull(this.jvmDefault);
        if (jvmDefaultModeFromStringOrNull != null && jvmDefaultModeFromStringOrNull.getForAllMethodsWithBody()) {
            mapConfigureLanguageFeatures.put(LanguageFeature.ForbidSuperDelegationToAbstractFakeOverride, LanguageFeature.State.ENABLED);
            mapConfigureLanguageFeatures.put(LanguageFeature.AbstractClassMemberNotImplementedWithIntermediateAbstractClass, LanguageFeature.State.ENABLED);
        }
        if (this.valueClasses) {
            mapConfigureLanguageFeatures.put(LanguageFeature.ValueClasses, LanguageFeature.State.ENABLED);
        }
        return mapConfigureLanguageFeatures;
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments
    protected LanguageVersion defaultLanguageVersion(MessageCollector collector) {
        Intrinsics.checkNotNullParameter(collector, "collector");
        if (this.useOldBackend) {
            if (!getSuppressVersionWarnings()) {
                MessageCollector.report$default(collector, CompilerMessageSeverity.STRONG_WARNING, "Language version is automatically inferred to " + LanguageVersion.KOTLIN_1_5.getVersionString() + " when using the old JVM backend. Consider specifying -language-version explicitly, or remove -Xuse-old-backend", null, 4, null);
            }
            return LanguageVersion.KOTLIN_1_5;
        }
        return super.defaultLanguageVersion(collector);
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments
    protected void checkPlatformSpecificSettings(LanguageVersionSettings languageVersionSettings, MessageCollector collector) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(collector, "collector");
        if (this.useOldBackend && languageVersionSettings.getLanguageVersion().compareTo(LanguageVersion.KOTLIN_1_6) >= 0) {
            MessageCollector.report$default(collector, CompilerMessageSeverity.ERROR, "Old JVM backend does not support language version 1.6 or above. Please use language version 1.5 or below, or remove -Xuse-old-backend", null, 4, null);
        }
        if (this.oldInnerClassesLogic) {
            MessageCollector.report$default(collector, CompilerMessageSeverity.WARNING, "The -Xuse-old-innerclasses-logic option is deprecated and will be deleted in future versions.", null, 4, null);
        }
    }

    @Override // org.jetbrains.kotlin.cli.common.arguments.Freezable
    protected Freezable copyOf() {
        return K2JVMCompilerArgumentsCopyGeneratedKt.copyK2JVMCompilerArguments(this, new K2JVMCompilerArguments());
    }
}
