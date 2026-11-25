package defpackage;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.bricks.FormulaBrick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:LibaryBricks.class
  classes5.dex
 */
/* JADX INFO: compiled from: LibaryBricks.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"LLibaryBricks;", "", "()V", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LibaryBricks {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final List<TemporalAction> actions = new ArrayList();
    private static final List<FormulaBrick> bricks = new ArrayList();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:LibaryBricks$Companion.class
      classes5.dex
     */
    /* JADX INFO: compiled from: LibaryBricks.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u0005J\u000e\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u0007J\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000eJ\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\u000eJ\u000e\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\u0012R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"LLibaryBricks$Companion;", "", "()V", "actions", "", "Lcom/badlogic/gdx/scenes/scene2d/actions/TemporalAction;", "bricks", "Lorg/catrobat/catroid/content/bricks/FormulaBrick;", "addAction", "", "action", "addBrick", "brick", "getActions", "", "getBricks", "loadActionsFromFile", "filePath", "", "loadBricksFromFile", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void loadActionsFromFile(String filePath) {
            Intrinsics.checkNotNullParameter(filePath, "filePath");
        }

        public final void loadBricksFromFile(String filePath) {
            Intrinsics.checkNotNullParameter(filePath, "filePath");
        }

        private Companion() {
        }

        public final void addAction(TemporalAction action) {
            Intrinsics.checkNotNullParameter(action, "action");
            LibaryBricks.actions.add(action);
        }

        public final void addBrick(FormulaBrick brick) {
            Intrinsics.checkNotNullParameter(brick, "brick");
            LibaryBricks.bricks.add(brick);
        }

        public final List<TemporalAction> getActions() {
            return LibaryBricks.actions;
        }

        public final List<FormulaBrick> getBricks() {
            return LibaryBricks.bricks;
        }
    }
}
