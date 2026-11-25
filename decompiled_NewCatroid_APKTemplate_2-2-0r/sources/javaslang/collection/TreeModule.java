package javaslang.collection;

import java.util.LinkedList;
import java.util.function.BiFunction;

/* JADX INFO: compiled from: Tree.java */
/* JADX INFO: loaded from: classes11.dex */
interface TreeModule {

    /* JADX INFO: compiled from: Tree.java */
    public static final class Traversal {
        static <T> Stream<Tree.Node<T>> preOrder(Tree.Node<T> node) {
            return (Stream) node.getChildren().foldLeft(Stream.of(node), new BiFunction() { // from class: javaslang.collection.TreeModule$Traversal$$ExternalSyntheticLambda1
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ((Stream) obj).appendAll(TreeModule.Traversal.preOrder((Tree.Node) obj2));
                }
            });
        }

        static <T> Stream<Tree.Node<T>> inOrder(Tree.Node<T> node) {
            if (node.isLeaf()) {
                return Stream.of(node);
            }
            List<Tree.Node<T>> children = node.getChildren();
            return ((Stream) children.tail().foldLeft(Stream.empty(), new BiFunction() { // from class: javaslang.collection.TreeModule$Traversal$$ExternalSyntheticLambda0
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ((Stream) obj).appendAll(TreeModule.Traversal.inOrder((Tree.Node) obj2));
                }
            })).prepend(node).prependAll(inOrder(children.head()));
        }

        static <T> Stream<Tree.Node<T>> postOrder(Tree.Node<T> node) {
            return ((Stream) node.getChildren().foldLeft(Stream.empty(), new BiFunction() { // from class: javaslang.collection.TreeModule$Traversal$$ExternalSyntheticLambda2
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return ((Stream) obj).appendAll(TreeModule.Traversal.postOrder((Tree.Node) obj2));
                }
            })).append(node);
        }

        static <T> Stream<Tree.Node<T>> levelOrder(Tree.Node<T> node) {
            Stream streamEmpty = Stream.empty();
            LinkedList linkedList = new LinkedList();
            linkedList.add(node);
            while (!linkedList.isEmpty()) {
                Tree.Node node2 = (Tree.Node) linkedList.remove();
                streamEmpty = streamEmpty.prepend(node2);
                linkedList.addAll(node2.getChildren().toJavaList());
            }
            return streamEmpty.reverse();
        }
    }
}
