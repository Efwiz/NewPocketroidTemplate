package com.google.firebase.database.snapshot;

/* JADX INFO: loaded from: classes10.dex */
public final class NamedNode {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private final ChildKey f181name;
    private final Node node;
    private static final NamedNode MIN_NODE = new NamedNode(ChildKey.getMinName(), EmptyNode.Empty());
    private static final NamedNode MAX_NODE = new NamedNode(ChildKey.getMaxName(), Node.MAX_NODE);

    public static NamedNode getMinNode() {
        return MIN_NODE;
    }

    public static NamedNode getMaxNode() {
        return MAX_NODE;
    }

    public NamedNode(ChildKey childKey, Node node) {
        this.f181name = childKey;
        this.node = node;
    }

    public ChildKey getName() {
        return this.f181name;
    }

    public Node getNode() {
        return this.node;
    }

    public String toString() {
        return "NamedNode{name=" + this.f181name + ", node=" + this.node + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NamedNode namedNode = (NamedNode) obj;
        return this.f181name.equals(namedNode.f181name) && this.node.equals(namedNode.node);
    }

    public int hashCode() {
        return (this.f181name.hashCode() * 31) + this.node.hashCode();
    }
}
