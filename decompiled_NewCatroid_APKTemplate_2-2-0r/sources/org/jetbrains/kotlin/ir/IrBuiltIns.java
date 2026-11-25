package org.jetbrains.kotlin.ir;

import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.backend.jvm.lower.VarargLoweringKt;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.UnsignedType;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOriginImpl;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: IrBuiltIns.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\bG\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0018\u0002\n\u0002\b7\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b%\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u000b\b&\u0018\u0000 \u0098\u00022\u00020\u0001:\u0002\u0098\u0002B\u0005¢\u0006\u0002\u0010\u0002J#\u0010ö\u0001\u001a\t\u0012\u0004\u0012\u00020\u00040÷\u00012\u0007\u0010ø\u0001\u001a\u00020\b2\b\u0010ù\u0001\u001a\u00030ú\u0001H&J5\u0010û\u0001\u001a\u0004\u0018\u00010\b2\b\u0010ù\u0001\u001a\u00030ú\u00012\u0018\b\u0002\u0010ü\u0001\u001a\f\u0012\u0007\b\u0001\u0012\u00030þ\u00010ý\u0001\"\u00030þ\u0001H&¢\u0006\u0003\u0010ÿ\u0001J\u001f\u0010û\u0001\u001a\u0004\u0018\u00010\b2\b\u0010ù\u0001\u001a\u00030ú\u00012\b\u0010\u0080\u0002\u001a\u00030\u0081\u0002H&J:\u0010\u0082\u0002\u001a\t\u0012\u0004\u0012\u00020\u00040÷\u00012\b\u0010ù\u0001\u001a\u00030ú\u00012\u0018\b\u0002\u0010ü\u0001\u001a\f\u0012\u0007\b\u0001\u0012\u00030þ\u00010ý\u0001\"\u00030þ\u0001H&¢\u0006\u0003\u0010\u0083\u0002J$\u0010\u0082\u0002\u001a\t\u0012\u0004\u0012\u00020\u00040÷\u00012\b\u0010ù\u0001\u001a\u00030ú\u00012\b\u0010\u0080\u0002\u001a\u00030\u0081\u0002H&J%\u0010\u0084\u0002\u001a\n\u0012\u0005\u0012\u00030\u0085\u00020÷\u00012\b\u0010ù\u0001\u001a\u00030ú\u00012\b\u0010\u0080\u0002\u001a\u00030\u0081\u0002H&J\u0014\u0010\u0086\u0002\u001a\u00030\u0087\u00022\b\u0010\u0088\u0002\u001a\u00030\u0089\u0002H&J%\u0010\u008a\u0002\u001a\u00020\u00042\b\u0010ù\u0001\u001a\u00030ú\u00012\u0007\u0010\u008b\u0002\u001a\u00020\f2\u0007\u0010\u008c\u0002\u001a\u00020\fH&J\u001d\u0010\u0085\u0001\u001a\u00020\b2\b\u0010\u008d\u0002\u001a\u00030\u008e\u00022\b\u0010\u008f\u0002\u001a\u00030\u0089\u0002H&J=\u0010\u0090\u0002\u001a\u000e\u0012\u0004\u0012\u00020U\u0012\u0004\u0012\u00020\u00040T2\b\u0010ù\u0001\u001a\u00030ú\u00012\u0016\u0010ü\u0001\u001a\f\u0012\u0007\b\u0001\u0012\u00030þ\u00010ý\u0001\"\u00030þ\u0001H&¢\u0006\u0003\u0010\u0091\u0002J=\u0010\u0092\u0002\u001a\u000e\u0012\u0004\u0012\u00020U\u0012\u0004\u0012\u00020\u00040T2\b\u0010ù\u0001\u001a\u00030ú\u00012\u0016\u0010ü\u0001\u001a\f\u0012\u0007\b\u0001\u0012\u00030þ\u00010ý\u0001\"\u00030þ\u0001H&¢\u0006\u0003\u0010\u0091\u0002J\u001c\u0010\u0093\u0002\u001a\u00020\u00042\b\u0010ù\u0001\u001a\u00030ú\u00012\u0007\u0010\u0094\u0002\u001a\u00020\fH&J\u0014\u0010\u0095\u0002\u001a\u00030\u0087\u00022\b\u0010\u0088\u0002\u001a\u00030\u0089\u0002H&J\u0014\u0010\u0096\u0002\u001a\u00030\u0087\u00022\b\u0010\u0088\u0002\u001a\u00030\u0089\u0002H&J\u0014\u0010\u0097\u0002\u001a\u00030\u0087\u00022\b\u0010\u0088\u0002\u001a\u00030\u0089\u0002H&R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\nR\u0012\u0010\u0011\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000eR\u0012\u0010\u0013\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u000eR\u0012\u0010\u0015\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\nR\u0012\u0010\u0017\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0006R\u0012\u0010\u0019\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0006R\u0012\u0010\u001b\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\nR\u0012\u0010\u001d\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\nR\u0012\u0010\u001f\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b \u0010\u0006R\u0012\u0010!\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010\u000eR\u0012\u0010#\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b$\u0010\nR\u0012\u0010%\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b&\u0010\nR\u0012\u0010'\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b(\u0010\u000eR\u0012\u0010)\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b*\u0010\nR\u0012\u0010+\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b,\u0010\nR\u0012\u0010-\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b.\u0010\nR\u0012\u0010/\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b0\u0010\u000eR\u0012\u00101\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b2\u0010\u0006R\u0012\u00103\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b4\u0010\nR\u0012\u00105\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b6\u0010\nR\u0012\u00107\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b8\u0010\u0006R\u0012\u00109\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b:\u0010\u0006R\u0012\u0010;\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b<\u0010\nR\u0012\u0010=\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b>\u0010\nR\u0012\u0010?\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b@\u0010\u000eR\u0012\u0010A\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\bB\u0010\nR\u0012\u0010C\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\bD\u0010\u0006R\u0012\u0010E\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\bF\u0010\u0006R\u0012\u0010G\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\bH\u0010\u0006R\u0012\u0010I\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\bJ\u0010\u0006R\u0012\u0010K\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\bL\u0010\nR\u0012\u0010M\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\bN\u0010\nR\u0012\u0010O\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\bP\u0010\u000eR\u0012\u0010Q\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\bR\u0010\nR\u001e\u0010S\u001a\u000e\u0012\u0004\u0012\u00020U\u0012\u0004\u0012\u00020\u00040TX¦\u0004¢\u0006\u0006\u001a\u0004\bV\u0010WR\u001e\u0010X\u001a\u000e\u0012\u0004\u0012\u00020U\u0012\u0004\u0012\u00020\u00040TX¦\u0004¢\u0006\u0006\u001a\u0004\bY\u0010WR\u001e\u0010Z\u001a\u000e\u0012\u0004\u0012\u00020U\u0012\u0004\u0012\u00020\u00040TX¦\u0004¢\u0006\u0006\u001a\u0004\b[\u0010WR\u0012\u0010\\\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b]\u0010\u0006R\u0012\u0010^\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b_\u0010\nR\u0012\u0010`\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\ba\u0010\nR\u0012\u0010b\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\bc\u0010\u0006R\u0012\u0010d\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\be\u0010\u0006R\u0012\u0010f\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\bg\u0010\u000eR\u0012\u0010h\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\bi\u0010\u0006R\u0012\u0010j\u001a\u00020kX¦\u0004¢\u0006\u0006\u001a\u0004\bl\u0010mR\u0012\u0010n\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\bo\u0010\nR\u0012\u0010p\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\bq\u0010\nR\u0012\u0010r\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\bs\u0010\nR\u0012\u0010t\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\bu\u0010\nR\u0012\u0010v\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\bw\u0010\nR\u0012\u0010x\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\by\u0010\nR\u0012\u0010z\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b{\u0010\nR\u0012\u0010|\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b}\u0010\nR\u0012\u0010~\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\u007f\u0010\nR\u0014\u0010\u0080\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b\u0081\u0001\u0010\nR\u0014\u0010\u0082\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b\u0083\u0001\u0010\nR\u0014\u0010\u0084\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b\u0085\u0001\u0010\nR\u0014\u0010\u0086\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b\u0087\u0001\u0010\nR\u0016\u0010\u0088\u0001\u001a\u00030\u0089\u0001X¦\u0004¢\u0006\b\u001a\u0006\b\u008a\u0001\u0010\u008b\u0001R \u0010\u008c\u0001\u001a\u000e\u0012\u0004\u0012\u00020U\u0012\u0004\u0012\u00020\u00040TX¦\u0004¢\u0006\u0007\u001a\u0005\b\u008d\u0001\u0010WR \u0010\u008e\u0001\u001a\u000e\u0012\u0004\u0012\u00020U\u0012\u0004\u0012\u00020\u00040TX¦\u0004¢\u0006\u0007\u001a\u0005\b\u008f\u0001\u0010WR\u0014\u0010\u0090\u0001\u001a\u00020\u0004X¦\u0004¢\u0006\u0007\u001a\u0005\b\u0091\u0001\u0010\u0006R\u0014\u0010\u0092\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b\u0093\u0001\u0010\nR\u0014\u0010\u0094\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b\u0095\u0001\u0010\nR\u0014\u0010\u0096\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b\u0097\u0001\u0010\nR\u0014\u0010\u0098\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b\u0099\u0001\u0010\nR\u0014\u0010\u009a\u0001\u001a\u00020\fX¦\u0004¢\u0006\u0007\u001a\u0005\b\u009b\u0001\u0010\u000eR\u0014\u0010\u009c\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b\u009d\u0001\u0010\nR\u0014\u0010\u009e\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b\u009f\u0001\u0010\nR\u0014\u0010 \u0001\u001a\u00020\u0004X¦\u0004¢\u0006\u0007\u001a\u0005\b¡\u0001\u0010\u0006R\u0014\u0010¢\u0001\u001a\u00020\u0004X¦\u0004¢\u0006\u0007\u001a\u0005\b£\u0001\u0010\u0006R\u0014\u0010¤\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b¥\u0001\u0010\nR\u0014\u0010¦\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b§\u0001\u0010\nR\u0014\u0010¨\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b©\u0001\u0010\nR\u0014\u0010ª\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b«\u0001\u0010\nR\u0014\u0010¬\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b\u00ad\u0001\u0010\nR\u0014\u0010®\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b¯\u0001\u0010\nR\u0014\u0010°\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b±\u0001\u0010\nR\u0014\u0010²\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b³\u0001\u0010\nR\u0014\u0010´\u0001\u001a\u00020\u0004X¦\u0004¢\u0006\u0007\u001a\u0005\bµ\u0001\u0010\u0006R\u0014\u0010¶\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b·\u0001\u0010\nR\u0014\u0010¸\u0001\u001a\u00020\fX¦\u0004¢\u0006\u0007\u001a\u0005\b¹\u0001\u0010\u000eR\u0014\u0010º\u0001\u001a\u00020\fX¦\u0004¢\u0006\u0007\u001a\u0005\b»\u0001\u0010\u000eR\u0014\u0010¼\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\b½\u0001\u0010\nR\u0014\u0010¾\u0001\u001a\u00020\fX¦\u0004¢\u0006\u0007\u001a\u0005\b¿\u0001\u0010\u000eR\u0016\u0010À\u0001\u001a\u00030Á\u0001X¦\u0004¢\u0006\b\u001a\u0006\bÂ\u0001\u0010Ã\u0001R\u0014\u0010Ä\u0001\u001a\u00020\u0004X¦\u0004¢\u0006\u0007\u001a\u0005\bÅ\u0001\u0010\u0006R\"\u0010Æ\u0001\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\f0TX¦\u0004¢\u0006\u0007\u001a\u0005\bÇ\u0001\u0010WR\"\u0010È\u0001\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\f\u0012\u0004\u0012\u00020\b0TX¦\u0004¢\u0006\u0007\u001a\u0005\bÉ\u0001\u0010WR!\u0010Ê\u0001\u001a\u000f\u0012\u0004\u0012\u00020\b\u0012\u0005\u0012\u00030Ë\u00010TX¦\u0004¢\u0006\u0007\u001a\u0005\bÌ\u0001\u0010WR\u001c\u0010Í\u0001\u001a\t\u0012\u0004\u0012\u00020\f0Î\u0001X¦\u0004¢\u0006\b\u001a\u0006\bÏ\u0001\u0010Ð\u0001R\u001c\u0010Ñ\u0001\u001a\t\u0012\u0004\u0012\u00020\f0Î\u0001X¦\u0004¢\u0006\b\u001a\u0006\bÒ\u0001\u0010Ð\u0001R\u001c\u0010Ó\u0001\u001a\t\u0012\u0004\u0012\u00020\f0Î\u0001X¦\u0004¢\u0006\b\u001a\u0006\bÔ\u0001\u0010Ð\u0001R!\u0010Õ\u0001\u001a\u000f\u0012\u0005\u0012\u00030Ë\u0001\u0012\u0004\u0012\u00020\f0TX¦\u0004¢\u0006\u0007\u001a\u0005\bÖ\u0001\u0010WR!\u0010×\u0001\u001a\u000f\u0012\u0005\u0012\u00030Ë\u0001\u0012\u0004\u0012\u00020\b0TX¦\u0004¢\u0006\u0007\u001a\u0005\bØ\u0001\u0010WR\u0014\u0010Ù\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\bÚ\u0001\u0010\nR\u0014\u0010Û\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\bÜ\u0001\u0010\nR\u0014\u0010Ý\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\bÞ\u0001\u0010\nR\u0014\u0010ß\u0001\u001a\u00020\fX¦\u0004¢\u0006\u0007\u001a\u0005\bà\u0001\u0010\u000eR\u0014\u0010á\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\bâ\u0001\u0010\nR\u0014\u0010ã\u0001\u001a\u00020\fX¦\u0004¢\u0006\u0007\u001a\u0005\bä\u0001\u0010\u000eR\u0014\u0010å\u0001\u001a\u00020\u0004X¦\u0004¢\u0006\u0007\u001a\u0005\bæ\u0001\u0010\u0006R\u0014\u0010ç\u0001\u001a\u00020\u0004X¦\u0004¢\u0006\u0007\u001a\u0005\bè\u0001\u0010\u0006R\u0014\u0010é\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\bê\u0001\u0010\nR\u0014\u0010ë\u0001\u001a\u00020\fX¦\u0004¢\u0006\u0007\u001a\u0005\bì\u0001\u0010\u000eR\u0014\u0010í\u0001\u001a\u00020\bX¦\u0004¢\u0006\u0007\u001a\u0005\bî\u0001\u0010\nR\u0014\u0010ï\u0001\u001a\u00020\fX¦\u0004¢\u0006\u0007\u001a\u0005\bð\u0001\u0010\u000eR\"\u0010ñ\u0001\u001a\u0010\u0012\u0004\u0012\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\f0TX¦\u0004¢\u0006\u0007\u001a\u0005\bò\u0001\u0010WR!\u0010ó\u0001\u001a\u000f\u0012\u0005\u0012\u00030ô\u0001\u0012\u0004\u0012\u00020\b0TX¦\u0004¢\u0006\u0007\u001a\u0005\bõ\u0001\u0010W¨\u0006\u0099\u0002"}, d2 = {"Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "", "()V", "andandSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getAndandSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "annotationClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getAnnotationClass", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "annotationType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getAnnotationType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "anyClass", "getAnyClass", "anyNType", "getAnyNType", SchemaSymbols.ATTVAL_ANYTYPE, "getAnyType", "arrayClass", "getArrayClass", VarargLoweringKt.ARRAY_OF_NAME, "getArrayOf", "arrayOfNulls", "getArrayOfNulls", "booleanArray", "getBooleanArray", "booleanClass", "getBooleanClass", "booleanNotSymbol", "getBooleanNotSymbol", "booleanType", "getBooleanType", "byteArray", "getByteArray", "byteClass", "getByteClass", "byteType", "getByteType", "charArray", "getCharArray", "charClass", "getCharClass", "charSequenceClass", "getCharSequenceClass", "charType", "getCharType", "checkNotNullSymbol", "getCheckNotNullSymbol", "collectionClass", "getCollectionClass", "comparableClass", "getComparableClass", "dataClassArrayMemberHashCodeSymbol", "getDataClassArrayMemberHashCodeSymbol", "dataClassArrayMemberToStringSymbol", "getDataClassArrayMemberToStringSymbol", "doubleArray", "getDoubleArray", "doubleClass", "getDoubleClass", "doubleType", "getDoubleType", "enumClass", "getEnumClass", "eqeqSymbol", "getEqeqSymbol", "eqeqeqSymbol", "getEqeqeqSymbol", "extensionStringPlus", "getExtensionStringPlus", "extensionToString", "getExtensionToString", "floatArray", "getFloatArray", "floatClass", "getFloatClass", "floatType", "getFloatType", "functionClass", "getFunctionClass", "greaterFunByOperandType", "", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "getGreaterFunByOperandType", "()Ljava/util/Map;", "greaterOrEqualFunByOperandType", "getGreaterOrEqualFunByOperandType", "ieee754equalsFunByOperandType", "getIeee754equalsFunByOperandType", "illegalArgumentExceptionSymbol", "getIllegalArgumentExceptionSymbol", "intArray", "getIntArray", "intClass", "getIntClass", "intPlusSymbol", "getIntPlusSymbol", "intTimesSymbol", "getIntTimesSymbol", "intType", "getIntType", "intXorSymbol", "getIntXorSymbol", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "iterableClass", "getIterableClass", "iteratorClass", "getIteratorClass", "kCallableClass", "getKCallableClass", "kClassClass", "getKClassClass", "kFunctionClass", "getKFunctionClass", "kMutableProperty0Class", "getKMutableProperty0Class", "kMutableProperty1Class", "getKMutableProperty1Class", "kMutableProperty2Class", "getKMutableProperty2Class", "kProperty0Class", "getKProperty0Class", "kProperty1Class", "getKProperty1Class", "kProperty2Class", "getKProperty2Class", "kPropertyClass", "getKPropertyClass", "kTypeClass", "getKTypeClass", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "lessFunByOperandType", "getLessFunByOperandType", "lessOrEqualFunByOperandType", "getLessOrEqualFunByOperandType", "linkageErrorSymbol", "getLinkageErrorSymbol", "listClass", "getListClass", "listIteratorClass", "getListIteratorClass", "longArray", "getLongArray", "longClass", "getLongClass", "longType", "getLongType", "mapClass", "getMapClass", "mapEntryClass", "getMapEntryClass", "memberStringPlus", "getMemberStringPlus", "memberToString", "getMemberToString", "mutableCollectionClass", "getMutableCollectionClass", "mutableIterableClass", "getMutableIterableClass", "mutableIteratorClass", "getMutableIteratorClass", "mutableListClass", "getMutableListClass", "mutableListIteratorClass", "getMutableListIteratorClass", "mutableMapClass", "getMutableMapClass", "mutableMapEntryClass", "getMutableMapEntryClass", "mutableSetClass", "getMutableSetClass", "noWhenBranchMatchedExceptionSymbol", "getNoWhenBranchMatchedExceptionSymbol", "nothingClass", "getNothingClass", "nothingNType", "getNothingNType", "nothingType", "getNothingType", "numberClass", "getNumberClass", "numberType", "getNumberType", "operatorsPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "getOperatorsPackageFragment", "()Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "ororSymbol", "getOrorSymbol", "primitiveArrayElementTypes", "getPrimitiveArrayElementTypes", "primitiveArrayForType", "getPrimitiveArrayForType", "primitiveArraysToPrimitiveTypes", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "getPrimitiveArraysToPrimitiveTypes", "primitiveFloatingPointIrTypes", "", "getPrimitiveFloatingPointIrTypes", "()Ljava/util/List;", "primitiveIrTypes", "getPrimitiveIrTypes", "primitiveIrTypesWithComparisons", "getPrimitiveIrTypesWithComparisons", "primitiveTypeToIrType", "getPrimitiveTypeToIrType", "primitiveTypesToPrimitiveArrays", "getPrimitiveTypesToPrimitiveArrays", "setClass", "getSetClass", "shortArray", "getShortArray", "shortClass", "getShortClass", "shortType", "getShortType", "stringClass", "getStringClass", "stringType", "getStringType", "throwCceSymbol", "getThrowCceSymbol", "throwIseSymbol", "getThrowIseSymbol", "throwableClass", "getThrowableClass", "throwableType", "getThrowableType", "unitClass", "getUnitClass", "unitType", "getUnitType", "unsignedArraysElementTypes", "getUnsignedArraysElementTypes", "unsignedTypesToUnsignedArrays", "Lorg/jetbrains/kotlin/builtins/UnsignedType;", "getUnsignedTypesToUnsignedArrays", "findBuiltInClassMemberFunctions", "", "builtInClass", "name", "Lorg/jetbrains/kotlin/name/Name;", "findClass", "packageNameSegments", "", "", "(Lorg/jetbrains/kotlin/name/Name;[Ljava/lang/String;)Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "findFunctions", "(Lorg/jetbrains/kotlin/name/Name;[Ljava/lang/String;)Ljava/lang/Iterable;", "findProperties", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "functionN", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "arity", "", "getBinaryOperator", "lhsType", "rhsType", "mutable", "", "n", "getNonBuiltInFunctionsByExtensionReceiver", "(Lorg/jetbrains/kotlin/name/Name;[Ljava/lang/String;)Ljava/util/Map;", "getNonBuiltinFunctionsByReturnType", "getUnaryOperator", "receiverType", "kFunctionN", "kSuspendFunctionN", "suspendFunctionN", "Companion", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrBuiltIns {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final FqName KOTLIN_INTERNAL_IR_FQN = new FqName("kotlin.internal.ir");
    private static final IrDeclarationOriginImpl BUILTIN_OPERATOR = new IrDeclarationOriginImpl() { // from class: org.jetbrains.kotlin.ir.IrBuiltIns$Companion$BUILTIN_OPERATOR$1
    };

    public abstract Iterable<IrSimpleFunctionSymbol> findBuiltInClassMemberFunctions(IrClassSymbol builtInClass, Name name2);

    public abstract IrClassSymbol findClass(Name name2, FqName packageFqName);

    public abstract IrClassSymbol findClass(Name name2, String... packageNameSegments);

    public abstract Iterable<IrSimpleFunctionSymbol> findFunctions(Name name2, FqName packageFqName);

    public abstract Iterable<IrSimpleFunctionSymbol> findFunctions(Name name2, String... packageNameSegments);

    public abstract Iterable<IrPropertySymbol> findProperties(Name name2, FqName packageFqName);

    public abstract IrClass functionN(int arity);

    public abstract IrSimpleFunctionSymbol getAndandSymbol();

    public abstract IrClassSymbol getAnnotationClass();

    public abstract IrType getAnnotationType();

    public abstract IrClassSymbol getAnyClass();

    public abstract IrType getAnyNType();

    public abstract IrType getAnyType();

    public abstract IrClassSymbol getArrayClass();

    public abstract IrSimpleFunctionSymbol getArrayOf();

    public abstract IrSimpleFunctionSymbol getArrayOfNulls();

    public abstract IrSimpleFunctionSymbol getBinaryOperator(Name name2, IrType lhsType, IrType rhsType);

    public abstract IrClassSymbol getBooleanArray();

    public abstract IrClassSymbol getBooleanClass();

    public abstract IrSimpleFunctionSymbol getBooleanNotSymbol();

    public abstract IrType getBooleanType();

    public abstract IrClassSymbol getByteArray();

    public abstract IrClassSymbol getByteClass();

    public abstract IrType getByteType();

    public abstract IrClassSymbol getCharArray();

    public abstract IrClassSymbol getCharClass();

    public abstract IrClassSymbol getCharSequenceClass();

    public abstract IrType getCharType();

    public abstract IrSimpleFunctionSymbol getCheckNotNullSymbol();

    public abstract IrClassSymbol getCollectionClass();

    public abstract IrClassSymbol getComparableClass();

    public abstract IrSimpleFunctionSymbol getDataClassArrayMemberHashCodeSymbol();

    public abstract IrSimpleFunctionSymbol getDataClassArrayMemberToStringSymbol();

    public abstract IrClassSymbol getDoubleArray();

    public abstract IrClassSymbol getDoubleClass();

    public abstract IrType getDoubleType();

    public abstract IrClassSymbol getEnumClass();

    public abstract IrSimpleFunctionSymbol getEqeqSymbol();

    public abstract IrSimpleFunctionSymbol getEqeqeqSymbol();

    public abstract IrSimpleFunctionSymbol getExtensionStringPlus();

    public abstract IrSimpleFunctionSymbol getExtensionToString();

    public abstract IrClassSymbol getFloatArray();

    public abstract IrClassSymbol getFloatClass();

    public abstract IrType getFloatType();

    public abstract IrClassSymbol getFunctionClass();

    public abstract Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getGreaterFunByOperandType();

    public abstract Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getGreaterOrEqualFunByOperandType();

    public abstract Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getIeee754equalsFunByOperandType();

    public abstract IrSimpleFunctionSymbol getIllegalArgumentExceptionSymbol();

    public abstract IrClassSymbol getIntArray();

    public abstract IrClassSymbol getIntClass();

    public abstract IrSimpleFunctionSymbol getIntPlusSymbol();

    public abstract IrSimpleFunctionSymbol getIntTimesSymbol();

    public abstract IrType getIntType();

    public abstract IrSimpleFunctionSymbol getIntXorSymbol();

    public abstract IrFactory getIrFactory();

    public abstract IrClassSymbol getIterableClass();

    public abstract IrClassSymbol getIteratorClass();

    public abstract IrClassSymbol getKCallableClass();

    public abstract IrClassSymbol getKClassClass();

    public abstract IrClassSymbol getKFunctionClass();

    public abstract IrClassSymbol getKMutableProperty0Class();

    public abstract IrClassSymbol getKMutableProperty1Class();

    public abstract IrClassSymbol getKMutableProperty2Class();

    public abstract IrClassSymbol getKProperty0Class();

    public abstract IrClassSymbol getKProperty1Class();

    public abstract IrClassSymbol getKProperty2Class();

    public abstract IrClassSymbol getKPropertyClass();

    public abstract IrClassSymbol getKPropertyClass(boolean mutable, int n);

    public abstract IrClassSymbol getKTypeClass();

    public abstract LanguageVersionSettings getLanguageVersionSettings();

    public abstract Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getLessFunByOperandType();

    public abstract Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getLessOrEqualFunByOperandType();

    public abstract IrSimpleFunctionSymbol getLinkageErrorSymbol();

    public abstract IrClassSymbol getListClass();

    public abstract IrClassSymbol getListIteratorClass();

    public abstract IrClassSymbol getLongArray();

    public abstract IrClassSymbol getLongClass();

    public abstract IrType getLongType();

    public abstract IrClassSymbol getMapClass();

    public abstract IrClassSymbol getMapEntryClass();

    public abstract IrSimpleFunctionSymbol getMemberStringPlus();

    public abstract IrSimpleFunctionSymbol getMemberToString();

    public abstract IrClassSymbol getMutableCollectionClass();

    public abstract IrClassSymbol getMutableIterableClass();

    public abstract IrClassSymbol getMutableIteratorClass();

    public abstract IrClassSymbol getMutableListClass();

    public abstract IrClassSymbol getMutableListIteratorClass();

    public abstract IrClassSymbol getMutableMapClass();

    public abstract IrClassSymbol getMutableMapEntryClass();

    public abstract IrClassSymbol getMutableSetClass();

    public abstract IrSimpleFunctionSymbol getNoWhenBranchMatchedExceptionSymbol();

    public abstract Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getNonBuiltInFunctionsByExtensionReceiver(Name name2, String... packageNameSegments);

    public abstract Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getNonBuiltinFunctionsByReturnType(Name name2, String... packageNameSegments);

    public abstract IrClassSymbol getNothingClass();

    public abstract IrType getNothingNType();

    public abstract IrType getNothingType();

    public abstract IrClassSymbol getNumberClass();

    public abstract IrType getNumberType();

    public abstract IrExternalPackageFragment getOperatorsPackageFragment();

    public abstract IrSimpleFunctionSymbol getOrorSymbol();

    public abstract Map<IrClassSymbol, IrType> getPrimitiveArrayElementTypes();

    public abstract Map<IrType, IrClassSymbol> getPrimitiveArrayForType();

    public abstract Map<IrClassSymbol, PrimitiveType> getPrimitiveArraysToPrimitiveTypes();

    public abstract List<IrType> getPrimitiveFloatingPointIrTypes();

    public abstract List<IrType> getPrimitiveIrTypes();

    public abstract List<IrType> getPrimitiveIrTypesWithComparisons();

    public abstract Map<PrimitiveType, IrType> getPrimitiveTypeToIrType();

    public abstract Map<PrimitiveType, IrClassSymbol> getPrimitiveTypesToPrimitiveArrays();

    public abstract IrClassSymbol getSetClass();

    public abstract IrClassSymbol getShortArray();

    public abstract IrClassSymbol getShortClass();

    public abstract IrType getShortType();

    public abstract IrClassSymbol getStringClass();

    public abstract IrType getStringType();

    public abstract IrSimpleFunctionSymbol getThrowCceSymbol();

    public abstract IrSimpleFunctionSymbol getThrowIseSymbol();

    public abstract IrClassSymbol getThrowableClass();

    public abstract IrType getThrowableType();

    public abstract IrSimpleFunctionSymbol getUnaryOperator(Name name2, IrType receiverType);

    public abstract IrClassSymbol getUnitClass();

    public abstract IrType getUnitType();

    public abstract Map<IrClassSymbol, IrType> getUnsignedArraysElementTypes();

    public abstract Map<UnsignedType, IrClassSymbol> getUnsignedTypesToUnsignedArrays();

    public abstract IrClass kFunctionN(int arity);

    public abstract IrClass kSuspendFunctionN(int arity);

    public abstract IrClass suspendFunctionN(int arity);

    public static /* synthetic */ Iterable findFunctions$default(IrBuiltIns irBuiltIns, Name name2, String[] strArr, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: findFunctions");
        }
        if ((i & 2) != 0) {
            strArr = new String[]{IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME};
        }
        return irBuiltIns.findFunctions(name2, strArr);
    }

    public static /* synthetic */ IrClassSymbol findClass$default(IrBuiltIns irBuiltIns, Name name2, String[] strArr, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: findClass");
        }
        if ((i & 2) != 0) {
            strArr = new String[]{IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME};
        }
        return irBuiltIns.findClass(name2, strArr);
    }

    /* JADX INFO: compiled from: IrBuiltIns.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/IrBuiltIns$Companion;", "", "()V", "BUILTIN_OPERATOR", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "getBUILTIN_OPERATOR", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOriginImpl;", "KOTLIN_INTERNAL_IR_FQN", "Lorg/jetbrains/kotlin/name/FqName;", "getKOTLIN_INTERNAL_IR_FQN", "()Lorg/jetbrains/kotlin/name/FqName;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FqName getKOTLIN_INTERNAL_IR_FQN() {
            return IrBuiltIns.KOTLIN_INTERNAL_IR_FQN;
        }

        public final IrDeclarationOriginImpl getBUILTIN_OPERATOR() {
            return IrBuiltIns.BUILTIN_OPERATOR;
        }
    }
}
