package org.catrobat.catroid.content.backwardcompatibility;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.CompositeBrick;
import org.catrobat.catroid.content.bricks.IfLogicElseBrick;
import org.catrobat.catroid.content.bricks.IfLogicEndBrick;
import org.catrobat.catroid.content.bricks.IfThenLogicEndBrick;
import org.catrobat.catroid.content.bricks.LoopEndBrick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/backwardcompatibility/BrickTreeBuilder.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BrickTreeBuilder {
    private List<Brick> convertedBricks = new ArrayList();
    private Deque<List<Brick>> parentListDeque;

    public BrickTreeBuilder() {
        ArrayDeque arrayDeque = new ArrayDeque();
        this.parentListDeque = arrayDeque;
        arrayDeque.push(this.convertedBricks);
    }

    public void convertBricks(List<Brick> bricks) {
        for (Brick brick : bricks) {
            if (brick instanceof CompositeBrick) {
                addCompositeBrick((CompositeBrick) brick);
            } else if (brick instanceof IfLogicElseBrick) {
                addElseBrick();
            } else if (brick instanceof IfLogicEndBrick) {
                addEndBrick();
            } else if (brick instanceof IfThenLogicEndBrick) {
                addEndBrick();
            } else if (brick instanceof LoopEndBrick) {
                addEndBrick();
            } else {
                addBrick(brick);
            }
        }
    }

    private void addCompositeBrick(CompositeBrick brick) {
        this.parentListDeque.getFirst().add(brick);
        this.parentListDeque.push(brick.getNestedBricks());
    }

    private void addElseBrick() {
        if (this.parentListDeque.size() > 1) {
            this.parentListDeque.pop();
            List<Brick> first = this.parentListDeque.getFirst();
            if (first.size() > 0) {
                Brick brick = this.parentListDeque.getFirst().get(first.size() - 1);
                if (brick instanceof CompositeBrick) {
                    CompositeBrick compositeBrick = (CompositeBrick) brick;
                    if (compositeBrick.hasSecondaryList()) {
                        this.parentListDeque.push(compositeBrick.getSecondaryNestedBricks());
                    }
                }
            }
        }
    }

    private void addEndBrick() {
        if (this.parentListDeque.size() > 1) {
            this.parentListDeque.pop();
        }
    }

    private void addBrick(Brick brick) {
        this.parentListDeque.getFirst().add(brick);
    }

    public List<Brick> toList() {
        return this.convertedBricks;
    }
}
