package org.jetbrains.kotlin.backend.jvm.codegen;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.FrameMapBase;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.org.objectweb.asm.Type;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: irCodegenUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u0006H\u0016J\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0002H\u0016J\u000e\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u0002R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/IrFrameMap;", "Lorg/jetbrains/kotlin/codegen/FrameMapBase;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "()V", "typeMap", "", "Lorg/jetbrains/org/objectweb/asm/Type;", "enter", "", ASTExpr.DEFAULT_MAP_KEY_NAME, ModuleXmlParser.TYPE, "leave", "typeOf", NativeSymbol.TYPE_NAME, "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrFrameMap extends FrameMapBase<IrSymbol> {
    private final Map<IrSymbol, Type> typeMap = new LinkedHashMap();

    @Override // org.jetbrains.kotlin.codegen.FrameMapBase
    public int enter(IrSymbol key, Type type) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(type, "type");
        this.typeMap.put(key, type);
        return super.enter(key, type);
    }

    @Override // org.jetbrains.kotlin.codegen.FrameMapBase
    public int leave(IrSymbol key) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.typeMap.remove(key);
        return super.leave(key);
    }

    public final Type typeOf(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Type type = this.typeMap.get(symbol);
        if (type != null) {
            return type;
        }
        throw new IllegalStateException(("No mapping for symbol: " + RenderIrElementKt.render$default(symbol.getOwner(), (DumpIrTreeOptions) null, 1, (Object) null)).toString());
    }
}
