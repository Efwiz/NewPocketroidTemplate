package org.jetbrains.kotlin.js.backend.ast;

import com.android.dx.rop.code.RegisterSpec;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.js.backend.ast.metadata.HasMetadata;
import org.jetbrains.kotlin.js.common.Symbol;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: JsClass.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B9\u0012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n¢\u0006\u0002\u0010\u000bJ\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u0019H\u0016J\b\u0010\u001c\u001a\u00020\u0000H\u0016J\n\u0010\u001d\u001a\u0004\u0018\u00010\u0004H\u0016J\n\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\u0012\u0010 \u001a\u00020\u00172\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0016J\u001c\u0010!\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\"2\n\u0010#\u001a\u0006\u0012\u0002\b\u00030$H\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/js/backend/ast/JsClass;", "Lorg/jetbrains/kotlin/js/backend/ast/JsLiteral;", "Lorg/jetbrains/kotlin/js/backend/ast/HasName;", "name", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "baseClass", "Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "members", "", "(Lorg/jetbrains/kotlin/js/backend/ast/JsName;Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;Ljava/util/List;)V", "getBaseClass", "()Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;", "setBaseClass", "(Lorg/jetbrains/kotlin/js/backend/ast/JsExpression;)V", "getConstructor", "()Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "setConstructor", "(Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;)V", "getMembers", "()Ljava/util/List;", "accept", "", RegisterSpec.PREFIX, "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitor;", "acceptChildren", "visitor", "deepCopy", "getName", "getSymbol", "Lorg/jetbrains/kotlin/js/common/Symbol;", "setName", "traverse", "Lorg/jetbrains/kotlin/js/backend/ast/JsVisitorWithContext;", "ctx", "Lorg/jetbrains/kotlin/js/backend/ast/JsContext;", "js.ast"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsClass extends JsLiteral implements HasName {
    private JsExpression baseClass;
    private JsFunction constructor;
    private final List<JsFunction> members;
    private JsName name;

    public JsClass() {
        this(null, null, null, null, 15, null);
    }

    public final JsExpression getBaseClass() {
        return this.baseClass;
    }

    public final void setBaseClass(JsExpression jsExpression) {
        this.baseClass = jsExpression;
    }

    public final JsFunction getConstructor() {
        return this.constructor;
    }

    public final void setConstructor(JsFunction jsFunction) {
        this.constructor = jsFunction;
    }

    public /* synthetic */ JsClass(JsName jsName, JsExpression jsExpression, JsFunction jsFunction, ArrayList arrayList, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : jsName, (i & 2) != 0 ? null : jsExpression, (i & 4) != 0 ? null : jsFunction, (i & 8) != 0 ? new ArrayList() : arrayList);
    }

    public final List<JsFunction> getMembers() {
        return this.members;
    }

    public JsClass(JsName jsName, JsExpression jsExpression, JsFunction jsFunction, List<JsFunction> members) {
        Intrinsics.checkNotNullParameter(members, "members");
        this.name = jsName;
        this.baseClass = jsExpression;
        this.constructor = jsFunction;
        this.members = members;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.HasName
    public JsName getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.js.common.HasSymbol
    public Symbol getSymbol() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.HasName
    public void setName(JsName name2) {
        this.name = name2;
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void accept(JsVisitor v) {
        Intrinsics.checkNotNullParameter(v, "v");
        v.visitClass(this);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.SourceInfoAwareJsNode, org.jetbrains.kotlin.js.backend.ast.JsNode
    public void acceptChildren(JsVisitor visitor) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        visitor.accept(this.baseClass);
        visitor.accept(this.constructor);
        visitor.acceptList(this.members);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsNode
    public void traverse(JsVisitorWithContext v, JsContext<?> ctx) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        if (v.visit(this, ctx)) {
            this.baseClass = (JsExpression) v.accept(this.baseClass);
            this.constructor = (JsFunction) v.accept(this.constructor);
            v.acceptList(this.members);
        }
        v.endVisit(this, (JsContext) ctx);
    }

    @Override // org.jetbrains.kotlin.js.backend.ast.JsExpression, org.jetbrains.kotlin.js.backend.ast.JsNode
    public JsClass deepCopy() {
        JsName jsName = this.name;
        JsExpression jsExpression = this.baseClass;
        JsFunction jsFunction = this.constructor;
        JsFunction jsFunctionDeepCopy = jsFunction != null ? jsFunction.deepCopy() : null;
        List<JsFunction> list = this.members;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            JsFunction jsFunctionDeepCopy2 = ((JsFunction) it.next()).deepCopy();
            Intrinsics.checkNotNullExpressionValue(jsFunctionDeepCopy2, "deepCopy(...)");
            arrayList.add(jsFunctionDeepCopy2);
        }
        HasMetadata hasMetadataWithMetadataFrom = new JsClass(jsName, jsExpression, jsFunctionDeepCopy, arrayList).withMetadataFrom(this);
        Intrinsics.checkNotNullExpressionValue(hasMetadataWithMetadataFrom, "withMetadataFrom(...)");
        return (JsClass) hasMetadataWithMetadataFrom;
    }
}
