package org.jetbrains.kotlin.ir.backend.js.utils;

import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.expressions.IrLoop;
import org.jetbrains.kotlin.ir.expressions.IrReturnableBlock;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.js.backend.ast.JsLocation;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: JsGenerationContext.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000°\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000b\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0010J\u000e\u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020!J\u000e\u0010\"\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020!J\u0018\u0010#\u001a\u0004\u0018\u00010\u00172\u0006\u0010$\u001a\u00020%H\u0096\u0001¢\u0006\u0002\u0010&J\u001c\u0010'\u001a\u0004\u0018\u00010\u00182\u0006\u0010(\u001a\u00020\f2\n\b\u0002\u0010)\u001a\u0004\u0018\u00010*JJ\u0010+\u001a\u0004\u0018\u00010\u00182\u0006\u0010(\u001a\u00020\f2\b\u0010)\u001a\u0004\u0018\u00010*2\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00180\u000b2\u0017\u0010-\u001a\u0013\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00170.¢\u0006\u0002\b/H\u0082\bJ\u0011\u00100\u001a\u00020\r2\u0006\u00101\u001a\u00020%H\u0096\u0001J\u0011\u00102\u001a\u00020\r2\u0006\u00103\u001a\u000204H\u0096\u0001J\u0011\u00105\u001a\u00020\r2\u0006\u00106\u001a\u000207H\u0096\u0001J\u0010\u00108\u001a\u0004\u0018\u00010\r2\u0006\u00109\u001a\u00020:J\u0011\u0010;\u001a\u00020\r2\u0006\u00106\u001a\u000207H\u0096\u0001J\u0011\u0010<\u001a\u00020\r2\u0006\u0010=\u001a\u00020>H\u0096\u0001J\u0011\u0010?\u001a\u00020\r2\u0006\u0010@\u001a\u00020AH\u0096\u0001J\u0010\u0010B\u001a\u0004\u0018\u00010\r2\u0006\u0010C\u001a\u00020DJ\u0011\u0010E\u001a\u00020\r2\u0006\u0010F\u001a\u00020GH\u0096\u0001J\u0011\u0010H\u001a\u00020\r2\u0006\u0010=\u001a\u00020>H\u0096\u0001J\u000e\u0010I\u001a\u00020\r2\u0006\u0010F\u001a\u00020GJ\u0011\u0010J\u001a\u00020K2\u0006\u00101\u001a\u00020%H\u0096\u0001J\u001c\u0010L\u001a\u0004\u0018\u00010\u00182\u0006\u0010(\u001a\u00020\f2\n\b\u0002\u0010)\u001a\u0004\u0018\u00010*J\u001e\u0010M\u001a\u00020\u00002\n\b\u0002\u0010N\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tJ&\u0010O\u001a\u00020\u00002\u0006\u0010P\u001a\u00020\u00032\n\b\u0002\u0010N\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R*\u0010\u0015\u001a\u001e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00180\u0016j\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0018`\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\r0\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u001c\u001a\u001e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00180\u0016j\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u0018`\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006Q"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/JsGenerationContext;", "Lorg/jetbrains/kotlin/ir/backend/js/utils/IrNamer;", "currentFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "currentFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "staticContext", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsStaticContext;", "localNames", "Lorg/jetbrains/kotlin/ir/backend/js/utils/LocalNameGenerator;", "nameCache", "", "Lorg/jetbrains/kotlin/ir/IrElement;", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "useBareParameterNames", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrFile;Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lorg/jetbrains/kotlin/ir/backend/js/utils/JsStaticContext;Lorg/jetbrains/kotlin/ir/backend/js/utils/LocalNameGenerator;Ljava/util/Map;Z)V", "getCurrentFile", "()Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "getCurrentFunction", "()Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "endLocationCache", "Ljava/util/HashMap;", "", "Lorg/jetbrains/kotlin/js/backend/ast/JsLocation;", "Lkotlin/collections/HashMap;", "getLocalNames", "()Lorg/jetbrains/kotlin/ir/backend/js/utils/LocalNameGenerator;", "startLocationCache", "getStaticContext", "()Lorg/jetbrains/kotlin/ir/backend/js/utils/JsStaticContext;", "checkIfHasAssociatedJsCode", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "checkIfJsCode", "getAssociatedObjectKey", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Ljava/lang/Integer;", "getEndLocationForIrElement", "irElement", "originalName", "", "getLocationForIrElement", ResponseCacheMiddleware.CACHE, "offsetSelector", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "getNameForClass", "klass", "getNameForConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getNameForField", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getNameForLoop", "loop", "Lorg/jetbrains/kotlin/ir/expressions/IrLoop;", "getNameForMemberField", "getNameForMemberFunction", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getNameForProperty", "property", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "getNameForReturnableBlock", "block", "Lorg/jetbrains/kotlin/ir/expressions/IrReturnableBlock;", "getNameForStaticDeclaration", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "getNameForStaticFunction", "getNameForValueDeclaration", "getRefForExternalClass", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "getStartLocationForIrElement", "newDeclaration", "func", "newFile", "file", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsGenerationContext implements IrNamer {
    private final IrFile currentFile;
    private final IrFunction currentFunction;
    private final HashMap<Integer, JsLocation> endLocationCache;
    private final LocalNameGenerator localNames;
    private final Map<IrElement, JsName> nameCache;
    private final HashMap<Integer, JsLocation> startLocationCache;
    private final JsStaticContext staticContext;
    private final boolean useBareParameterNames;

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public Integer getAssociatedObjectKey(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        return this.staticContext.getAssociatedObjectKey(irClass);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForClass(IrClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return this.staticContext.getNameForClass(klass);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForConstructor(IrConstructor constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return this.staticContext.getNameForConstructor(constructor);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForField(IrField field) {
        Intrinsics.checkNotNullParameter(field, "field");
        return this.staticContext.getNameForField(field);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForMemberField(IrField field) {
        Intrinsics.checkNotNullParameter(field, "field");
        return this.staticContext.getNameForMemberField(field);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForMemberFunction(IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return this.staticContext.getNameForMemberFunction(function);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForProperty(IrProperty property) {
        Intrinsics.checkNotNullParameter(property, "property");
        return this.staticContext.getNameForProperty(property);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForStaticDeclaration(IrDeclarationWithName declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return this.staticContext.getNameForStaticDeclaration(declaration);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForStaticFunction(IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return this.staticContext.getNameForStaticFunction(function);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsNameRef getRefForExternalClass(IrClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return this.staticContext.getRefForExternalClass(klass);
    }

    public JsGenerationContext(IrFile currentFile, IrFunction irFunction, JsStaticContext staticContext, LocalNameGenerator localNameGenerator, Map<IrElement, JsName> nameCache, boolean z) {
        Intrinsics.checkNotNullParameter(currentFile, "currentFile");
        Intrinsics.checkNotNullParameter(staticContext, "staticContext");
        Intrinsics.checkNotNullParameter(nameCache, "nameCache");
        this.currentFile = currentFile;
        this.currentFunction = irFunction;
        this.staticContext = staticContext;
        this.localNames = localNameGenerator;
        this.nameCache = nameCache;
        this.useBareParameterNames = z;
        this.startLocationCache = new HashMap<>();
        this.endLocationCache = new HashMap<>();
    }

    public final IrFile getCurrentFile() {
        return this.currentFile;
    }

    public final IrFunction getCurrentFunction() {
        return this.currentFunction;
    }

    public final JsStaticContext getStaticContext() {
        return this.staticContext;
    }

    public final LocalNameGenerator getLocalNames() {
        return this.localNames;
    }

    public /* synthetic */ JsGenerationContext(IrFile irFile, IrFunction irFunction, JsStaticContext jsStaticContext, LocalNameGenerator localNameGenerator, HashMap map, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(irFile, irFunction, jsStaticContext, (i & 8) != 0 ? null : localNameGenerator, (i & 16) != 0 ? new HashMap() : map, (i & 32) != 0 ? false : z);
    }

    public static /* synthetic */ JsGenerationContext newFile$default(JsGenerationContext jsGenerationContext, IrFile irFile, IrFunction irFunction, LocalNameGenerator localNameGenerator, int i, Object obj) {
        if ((i & 2) != 0) {
            irFunction = null;
        }
        if ((i & 4) != 0) {
            localNameGenerator = null;
        }
        return jsGenerationContext.newFile(irFile, irFunction, localNameGenerator);
    }

    public final JsGenerationContext newFile(IrFile file, IrFunction func, LocalNameGenerator localNames) {
        Intrinsics.checkNotNullParameter(file, "file");
        return new JsGenerationContext(file, func, this.staticContext, localNames, this.nameCache, this.useBareParameterNames);
    }

    public static /* synthetic */ JsGenerationContext newDeclaration$default(JsGenerationContext jsGenerationContext, IrFunction irFunction, LocalNameGenerator localNameGenerator, int i, Object obj) {
        if ((i & 1) != 0) {
            irFunction = null;
        }
        if ((i & 2) != 0) {
            localNameGenerator = null;
        }
        return jsGenerationContext.newDeclaration(irFunction, localNameGenerator);
    }

    public final JsGenerationContext newDeclaration(IrFunction func, LocalNameGenerator localNames) {
        return new JsGenerationContext(this.currentFile, func, this.staticContext, localNames, this.nameCache, this.useBareParameterNames);
    }

    public final JsName getNameForValueDeclaration(IrDeclarationWithName declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Map<IrElement, JsName> map = this.nameCache;
        JsName jsName = map.get(declaration);
        if (jsName == null) {
            if (this.useBareParameterNames) {
                String strAsString = declaration.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                jsName = new JsName(NameTablesKt.sanitizeName$default(strAsString, false, 2, null), true);
            } else {
                LocalNameGenerator localNameGenerator = this.localNames;
                Intrinsics.checkNotNull(localNameGenerator);
                String str = localNameGenerator.getVariableNames().getNames().get(declaration);
                if (str == null) {
                    throw new IllegalStateException(("Variable name is not found " + declaration.getName()).toString());
                }
                jsName = new JsName(str, true);
            }
            map.put(declaration, jsName);
        }
        return jsName;
    }

    public final JsName getNameForLoop(IrLoop loop) {
        Intrinsics.checkNotNullParameter(loop, "loop");
        Map<IrElement, JsName> map = this.nameCache;
        JsName jsName = map.get(loop);
        if (jsName == null) {
            LocalNameGenerator localNameGenerator = this.localNames;
            Intrinsics.checkNotNull(localNameGenerator);
            String str = localNameGenerator.getLocalLoopNames().getNames().get(loop);
            if (str == null) {
                return null;
            }
            JsName jsName2 = new JsName(str, true);
            map.put(loop, jsName2);
            jsName = jsName2;
        }
        return jsName;
    }

    public final JsName getNameForReturnableBlock(IrReturnableBlock block) {
        Intrinsics.checkNotNullParameter(block, "block");
        Map<IrElement, JsName> map = this.nameCache;
        JsName jsName = map.get(block);
        if (jsName == null) {
            LocalNameGenerator localNameGenerator = this.localNames;
            Intrinsics.checkNotNull(localNameGenerator);
            String str = localNameGenerator.getLocalReturnableBlockNames().getNames().get(block);
            if (str == null) {
                return null;
            }
            JsName jsName2 = new JsName(str, true);
            map.put(block, jsName2);
            jsName = jsName2;
        }
        return jsName;
    }

    public final boolean checkIfJsCode(IrFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return Intrinsics.areEqual(symbol, this.staticContext.getBackendContext().getIntrinsics().getJsCode());
    }

    public final boolean checkIfHasAssociatedJsCode(IrFunctionSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        return this.staticContext.getBackendContext().getJsCodeForFunction(symbol) != null;
    }

    public static /* synthetic */ JsLocation getStartLocationForIrElement$default(JsGenerationContext jsGenerationContext, IrElement irElement, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        return jsGenerationContext.getStartLocationForIrElement(irElement, str);
    }

    public final JsLocation getStartLocationForIrElement(IrElement irElement, String originalName) {
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        HashMap<Integer, JsLocation> map = this.startLocationCache;
        Integer numValueOf = Integer.valueOf(irElement.getStartOffset());
        JsLocation jsLocation = map.get(numValueOf);
        if (jsLocation == null) {
            IrFileEntry fileEntry = this.currentFile.getFileEntry();
            if (irElement.getStartOffset() == -1 || irElement.getEndOffset() == -1) {
                jsLocation = null;
            } else {
                String name2 = fileEntry.getName();
                int startOffset = irElement.getStartOffset();
                jsLocation = new JsLocation(name2, fileEntry.getLineNumber(startOffset), fileEntry.getColumnNumber(startOffset), null, 8, null);
            }
            if (jsLocation == null) {
                return null;
            }
            map.put(numValueOf, jsLocation);
        }
        return JsLocation.copy$default(jsLocation, null, 0, 0, originalName, 7, null);
    }

    public static /* synthetic */ JsLocation getEndLocationForIrElement$default(JsGenerationContext jsGenerationContext, IrElement irElement, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = null;
        }
        return jsGenerationContext.getEndLocationForIrElement(irElement, str);
    }

    public final JsLocation getEndLocationForIrElement(IrElement irElement, String originalName) {
        Intrinsics.checkNotNullParameter(irElement, "irElement");
        HashMap<Integer, JsLocation> map = this.endLocationCache;
        Integer numValueOf = Integer.valueOf(irElement.getEndOffset());
        JsLocation jsLocation = map.get(numValueOf);
        if (jsLocation == null) {
            IrFileEntry fileEntry = this.currentFile.getFileEntry();
            if (irElement.getStartOffset() == -1 || irElement.getEndOffset() == -1) {
                jsLocation = null;
            } else {
                String name2 = fileEntry.getName();
                int endOffset = irElement.getEndOffset();
                jsLocation = new JsLocation(name2, fileEntry.getLineNumber(endOffset), fileEntry.getColumnNumber(endOffset), null, 8, null);
            }
            if (jsLocation == null) {
                return null;
            }
            map.put(numValueOf, jsLocation);
        }
        return JsLocation.copy$default(jsLocation, null, 0, 0, originalName, 7, null);
    }
}
