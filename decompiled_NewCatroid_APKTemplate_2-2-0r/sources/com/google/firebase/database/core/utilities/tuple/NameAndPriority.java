package com.google.firebase.database.core.utilities.tuple;

import com.google.firebase.database.snapshot.ChildKey;
import com.google.firebase.database.snapshot.Node;
import com.google.firebase.database.snapshot.NodeUtilities;

/* JADX INFO: loaded from: classes10.dex */
public class NameAndPriority implements Comparable<NameAndPriority> {

    /* JADX INFO: renamed from: name, reason: collision with root package name */
    private ChildKey f179name;
    private Node priority;

    public NameAndPriority(ChildKey childKey, Node node) {
        this.f179name = childKey;
        this.priority = node;
    }

    public ChildKey getName() {
        return this.f179name;
    }

    public Node getPriority() {
        return this.priority;
    }

    @Override // java.lang.Comparable
    public int compareTo(NameAndPriority nameAndPriority) {
        return NodeUtilities.nameAndPriorityCompare(this.f179name, this.priority, nameAndPriority.f179name, nameAndPriority.priority);
    }
}
