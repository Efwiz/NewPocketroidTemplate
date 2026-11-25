package org.catrobat.catroid.content;

import androidx.constraintlayout.motion.widget.Key;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/SquareController.class
  classes14.dex
 */
/* JADX INFO: compiled from: SquareController.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\bJ\\\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00052\b\b\u0002\u0010\u0011\u001a\u00020\f2\b\b\u0002\u0010\u0012\u001a\u00020\f2\b\b\u0002\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/catrobat/catroid/content/SquareController;", "", "()V", "squares", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lorg/catrobat/catroid/content/SquareActor;", "clearSquares", "", "createOrUpdateSquare", "name", "x", "", "y", "width", "height", "colorHex", Key.ROTATION, "transparency", StageActivity.STYLE_CORNER_RADIUS, "stage", "Lcom/badlogic/gdx/scenes/scene2d/Stage;", "removeSquare", "Companion", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SquareController {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Lazy<SquareController> instance$delegate = LazyKt.lazy(new Function0<SquareController>() { // from class: org.catrobat.catroid.content.SquareController$Companion$instance$2
        @Override // kotlin.jvm.functions.Function0
        public final SquareController invoke() {
            return new SquareController(null);
        }
    });
    private final ConcurrentHashMap<String, SquareActor> squares;

    public /* synthetic */ SquareController(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public static final SquareController getInstance() {
        return INSTANCE.getInstance();
    }

    private SquareController() {
        this.squares = new ConcurrentHashMap<>();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/SquareController$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: SquareController.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R!\u0010\u0003\u001a\u00020\u00048FX\u0087\u0084\u0002¢\u0006\u0012\n\u0004\b\b\u0010\t\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007¨\u0006\n"}, d2 = {"Lorg/catrobat/catroid/content/SquareController$Companion;", "", "()V", "instance", "Lorg/catrobat/catroid/content/SquareController;", "getInstance$annotations", "getInstance", "()Lorg/catrobat/catroid/content/SquareController;", "instance$delegate", "Lkotlin/Lazy;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        public static /* synthetic */ void getInstance$annotations() {
        }

        private Companion() {
        }

        public final SquareController getInstance() {
            return (SquareController) SquareController.instance$delegate.getValue();
        }
    }

    public final void createOrUpdateSquare(String name2, float x, float y, float width, float height, String colorHex, float rotation, float transparency, float cornerRadius, final Stage stage) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(colorHex, "colorHex");
        Intrinsics.checkNotNullParameter(stage, "stage");
        final SquareActor squareActor = new SquareActor(x, y, width, height, colorHex, rotation, transparency, cornerRadius);
        final SquareActor squareActorPut = this.squares.put(name2, squareActor);
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.SquareController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SquareController.createOrUpdateSquare$lambda$0(squareActorPut, stage, squareActor);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void createOrUpdateSquare$lambda$0(SquareActor squareActor, Stage stage, SquareActor squareActor2) {
        Intrinsics.checkNotNullParameter(stage, "$stage");
        Intrinsics.checkNotNullParameter(squareActor2, "$squareActor");
        if (squareActor != null) {
            squareActor.remove();
        }
        stage.addActor(squareActor2);
    }

    public final void removeSquare(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        final SquareActor squareActorRemove = this.squares.remove(name2);
        if (squareActorRemove != null) {
            Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.SquareController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SquareController.removeSquare$lambda$2$lambda$1(squareActorRemove);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void removeSquare$lambda$2$lambda$1(SquareActor square) {
        Intrinsics.checkNotNullParameter(square, "$square");
        square.remove();
    }

    public final void clearSquares() {
        Collection<SquareActor> collectionValues = this.squares.values();
        Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
        final List list = CollectionsKt.toList(collectionValues);
        this.squares.clear();
        Gdx.f82app.postRunnable(new Runnable() { // from class: org.catrobat.catroid.content.SquareController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SquareController.clearSquares$lambda$4(list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void clearSquares$lambda$4(List actorsToRemove) {
        Intrinsics.checkNotNullParameter(actorsToRemove, "$actorsToRemove");
        Iterator it = actorsToRemove.iterator();
        while (it.hasNext()) {
            ((SquareActor) it.next()).remove();
        }
    }
}
