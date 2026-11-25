package com.android.tools.r8.experimental.graphinfo;

import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.position.TextPosition;
import com.android.tools.r8.position.TextRange;
import com.android.tools.r8.shaking.C3732h3;
import com.android.tools.r8.shaking.C3737i3;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class KeepRuleGraphNode extends GraphNode {
    static final /* synthetic */ boolean g = true;
    private final Origin c;
    private final Position d;
    private final String e;
    private final Set f;

    public KeepRuleGraphNode(C3732h3 c3732h3) {
        this(c3732h3, Collections.emptySet());
    }

    private static String a(Position position) {
        if (position instanceof TextRange) {
            TextPosition start = ((TextRange) position).getStart();
            return start.getLine() + ":" + start.getColumn();
        }
        if (!(position instanceof TextPosition)) {
            return position.getDescription();
        }
        TextPosition textPosition = (TextPosition) position;
        return textPosition.getLine() + ":" + textPosition.getColumn();
    }

    @Override // com.android.tools.r8.experimental.graphinfo.GraphNode
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeepRuleGraphNode)) {
            return false;
        }
        KeepRuleGraphNode keepRuleGraphNode = (KeepRuleGraphNode) obj;
        return this.c.equals(keepRuleGraphNode.getOrigin()) && this.d.equals(keepRuleGraphNode.getPosition()) && Objects.equals(this.e, keepRuleGraphNode.getContent()) && this.f.equals(keepRuleGraphNode.getPreconditions());
    }

    public String getContent() {
        return this.e;
    }

    public Origin getOrigin() {
        return this.c;
    }

    public Position getPosition() {
        return this.d;
    }

    public Set<GraphNode> getPreconditions() {
        return this.f;
    }

    @Override // com.android.tools.r8.experimental.graphinfo.GraphNode
    public int hashCode() {
        return Objects.hash(this.c, this.d, this.e, this.f);
    }

    @Override // com.android.tools.r8.experimental.graphinfo.GraphNode
    public String toString() {
        return (getOrigin() == Origin.unknown() ? getContent() : getOrigin()) + ":" + a(getPosition());
    }

    public KeepRuleGraphNode(C3737i3 c3737i3, Set<GraphNode> set) {
        super(false);
        boolean z = g;
        if (!z && c3737i3 == null) {
            throw new AssertionError();
        }
        if (!z && set == null) {
            throw new AssertionError();
        }
        this.c = c3737i3.l();
        this.d = c3737i3.m();
        this.e = c3737i3.n();
        this.f = set;
    }
}
