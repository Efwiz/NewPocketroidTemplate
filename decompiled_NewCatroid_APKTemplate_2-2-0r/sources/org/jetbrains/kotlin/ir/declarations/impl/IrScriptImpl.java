package org.jetbrains.kotlin.ir.declarations.impl;

import com.android.dx.rop.code.RegisterSpec;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.UninitializedPropertyAccessException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.ObsoleteDescriptorBasedAPI;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.utils.SmartList;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrScriptImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0002\u0010\u000bR\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\"8VX\u0097\u0004¢\u0006\f\u0012\u0004\b#\u0010$\u001a\u0004\b%\u0010&R\"\u0010'\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0012\"\u0004\b)\u0010\u0014R\u001c\u0010*\u001a\u0004\u0018\u00010+X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u0014\u0010\n\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u00101R \u00102\u001a\b\u0012\u0004\u0012\u0002030\u000fX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b4\u0010\u0012\"\u0004\b5\u0010\u0014R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b6\u00107R \u00108\u001a\b\u0012\u0004\u0012\u00020+0\u000fX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010\u0012\"\u0004\b:\u0010\u0014R\"\u0010;\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010\u0012\"\u0004\b=\u0010\u0014R\u001c\u0010>\u001a\u0004\u0018\u00010?X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010A\"\u0004\bB\u0010CR\u001a\u0010\u0004\u001a\u00020\u0005X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bD\u0010E\"\u0004\bF\u0010GR\u001a\u0010H\u001a\u00020IX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010K\"\u0004\bL\u0010MR$\u0010O\u001a\u00020\r2\u0006\u0010N\u001a\u00020\r8V@VX\u0096\u000e¢\u0006\f\u001a\u0004\bP\u0010Q\"\u0004\bR\u0010SR \u0010T\u001a\b\u0012\u0004\u0012\u00020U0\u000fX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bV\u0010\u0012\"\u0004\bW\u0010\u0014R \u0010X\u001a\b\u0012\u0004\u0012\u00020+0\u000fX\u0096.¢\u0006\u000e\n\u0000\u001a\u0004\bY\u0010\u0012\"\u0004\bZ\u0010\u0014R\u001c\u0010[\u001a\u0004\u0018\u00010UX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\\\u0010]\"\u0004\b^\u0010_R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b`\u00101R\u001a\u0010a\u001a\b\u0012\u0004\u0012\u00020c0bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bd\u0010\u0012R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\be\u0010fR\u001c\u0010g\u001a\u0004\u0018\u00010hX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bi\u0010j\"\u0004\bk\u0010lR\u001c\u0010m\u001a\u0004\u0018\u00010+X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bn\u0010-\"\u0004\bo\u0010/¨\u0006p"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/impl/IrScriptImpl;", "Lorg/jetbrains/kotlin/ir/declarations/IrScript;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "name", "Lorg/jetbrains/kotlin/name/Name;", "factory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "startOffset", "", "endOffset", "(Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/ir/declarations/IrFactory;II)V", "_parent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "annotations", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotations", "()Ljava/util/List;", "setAnnotations", "(Ljava/util/List;)V", "baseClass", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getBaseClass", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "setBaseClass", "(Lorg/jetbrains/kotlin/ir/types/IrType;)V", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getConstructor", "()Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "setConstructor", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;)V", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "getDescriptor$annotations", "()V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "earlierScripts", "getEarlierScripts", "setEarlierScripts", "earlierScriptsParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "getEarlierScriptsParameter", "()Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "setEarlierScriptsParameter", "(Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;)V", "getEndOffset", "()I", "explicitCallParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "getExplicitCallParameters", "setExplicitCallParameters", "getFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "implicitReceiversParameters", "getImplicitReceiversParameters", "setImplicitReceiversParameters", "importedScripts", "getImportedScripts", "setImportedScripts", "metadata", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "getMetadata", "()Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "setMetadata", "(Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "setName", "(Lorg/jetbrains/kotlin/name/Name;)V", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)V", RegisterSpec.PREFIX, "parent", "getParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "setParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;)V", "providedProperties", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getProvidedProperties", "setProvidedProperties", "providedPropertiesParameters", "getProvidedPropertiesParameters", "setProvidedPropertiesParameters", "resultProperty", "getResultProperty", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "setResultProperty", "(Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;)V", "getStartOffset", "statements", "", "Lorg/jetbrains/kotlin/ir/IrStatement;", "getStatements", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "targetClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getTargetClass", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "setTargetClass", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)V", "thisReceiver", "getThisReceiver", "setThisReceiver", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrScriptImpl extends IrScript {
    private IrDeclarationParent _parent;
    private List<? extends IrConstructorCall> annotations;
    private IrType baseClass;
    private IrConstructor constructor;
    private List<? extends IrScriptSymbol> earlierScripts;
    private IrValueParameter earlierScriptsParameter;
    private final int endOffset;
    public List<? extends IrVariable> explicitCallParameters;
    private final IrFactory factory;
    public List<? extends IrValueParameter> implicitReceiversParameters;
    private List<? extends IrScriptSymbol> importedScripts;
    private MetadataSource metadata;
    private Name name;
    private IrDeclarationOrigin origin;
    public List<? extends IrPropertySymbol> providedProperties;
    public List<? extends IrValueParameter> providedPropertiesParameters;
    private IrPropertySymbol resultProperty;
    private final int startOffset;
    private final List<IrStatement> statements;
    private final IrScriptSymbol symbol;
    private IrClassSymbol targetClass;
    private IrValueParameter thisReceiver;

    @ObsoleteDescriptorBasedAPI
    public static /* synthetic */ void getDescriptor$annotations() {
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrSymbolOwner, org.jetbrains.kotlin.ir.declarations.IrReturnTarget, org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration
    public IrScriptSymbol getSymbol() {
        return this.symbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName
    public Name getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName
    public void setName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "<set-?>");
        this.name = name2;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration, org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    public IrFactory getFactory() {
        return this.factory;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.ir.IrElement
    public int getEndOffset() {
        return this.endOffset;
    }

    public IrScriptImpl(IrScriptSymbol symbol, Name name2, IrFactory factory, int i, int i2) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(factory, "factory");
        this.symbol = symbol;
        this.name = name2;
        this.factory = factory;
        this.startOffset = i;
        this.endOffset = i2;
        this.origin = IrScriptImplKt.SCRIPT_ORIGIN;
        this.annotations = new SmartList();
        this.statements = new ArrayList();
        getSymbol().bind(this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public IrDeclarationOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public void setOrigin(IrDeclarationOrigin irDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(irDeclarationOrigin, "<set-?>");
        this.origin = irDeclarationOrigin;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public IrDeclarationParent getParent() {
        IrDeclarationParent irDeclarationParent = this._parent;
        if (irDeclarationParent != null) {
            return irDeclarationParent;
        }
        throw new UninitializedPropertyAccessException("Parent not initialized: " + this);
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public void setParent(IrDeclarationParent v) {
        Intrinsics.checkNotNullParameter(v, "v");
        this._parent = v;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer, org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer
    public List<IrConstructorCall> getAnnotations() {
        return this.annotations;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMutableAnnotationContainer
    public void setAnnotations(List<? extends IrConstructorCall> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.annotations = list;
    }

    @Override // org.jetbrains.kotlin.ir.expressions.IrStatementContainer
    public List<IrStatement> getStatements() {
        return this.statements;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public MetadataSource getMetadata() {
        return this.metadata;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrMetadataSourceOwner
    public void setMetadata(MetadataSource metadataSource) {
        this.metadata = metadataSource;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public IrValueParameter getThisReceiver() {
        return this.thisReceiver;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public void setThisReceiver(IrValueParameter irValueParameter) {
        this.thisReceiver = irValueParameter;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public IrType getBaseClass() {
        return this.baseClass;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public void setBaseClass(IrType irType) {
        this.baseClass = irType;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public List<IrVariable> getExplicitCallParameters() {
        List list = this.explicitCallParameters;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("explicitCallParameters");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public void setExplicitCallParameters(List<? extends IrVariable> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.explicitCallParameters = list;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public List<IrValueParameter> getImplicitReceiversParameters() {
        List list = this.implicitReceiversParameters;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("implicitReceiversParameters");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public void setImplicitReceiversParameters(List<? extends IrValueParameter> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.implicitReceiversParameters = list;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public List<IrPropertySymbol> getProvidedProperties() {
        List list = this.providedProperties;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("providedProperties");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public void setProvidedProperties(List<? extends IrPropertySymbol> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.providedProperties = list;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public List<IrValueParameter> getProvidedPropertiesParameters() {
        List list = this.providedPropertiesParameters;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("providedPropertiesParameters");
        return null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public void setProvidedPropertiesParameters(List<? extends IrValueParameter> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.providedPropertiesParameters = list;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public IrPropertySymbol getResultProperty() {
        return this.resultProperty;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public void setResultProperty(IrPropertySymbol irPropertySymbol) {
        this.resultProperty = irPropertySymbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public IrValueParameter getEarlierScriptsParameter() {
        return this.earlierScriptsParameter;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public void setEarlierScriptsParameter(IrValueParameter irValueParameter) {
        this.earlierScriptsParameter = irValueParameter;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public List<IrScriptSymbol> getImportedScripts() {
        return this.importedScripts;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public void setImportedScripts(List<? extends IrScriptSymbol> list) {
        this.importedScripts = list;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public List<IrScriptSymbol> getEarlierScripts() {
        return this.earlierScripts;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public void setEarlierScripts(List<? extends IrScriptSymbol> list) {
        this.earlierScripts = list;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public IrClassSymbol getTargetClass() {
        return this.targetClass;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public void setTargetClass(IrClassSymbol irClassSymbol) {
        this.targetClass = irClassSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public IrConstructor getConstructor() {
        return this.constructor;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrScript
    public void setConstructor(IrConstructor irConstructor) {
        this.constructor = irConstructor;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration
    public ScriptDescriptor getDescriptor() {
        return getSymbol().getDescriptor();
    }
}
