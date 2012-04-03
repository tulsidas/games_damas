package damas.client;

import pulpcore.image.Colors;
import pulpcore.image.CoreFont;
import pulpcore.sprite.Button;
import pulpcore.sprite.FilledSprite;
import pulpcore.sprite.Group;
import pulpcore.sprite.Label;

/**
 * Popup cuando me ofrecen tablas con dos botones
 * 
 * @author Tulsi
 */
public class TablasPopUp extends Group {

    private Button si, no;

    private DamasScene scene;

    public TablasPopUp(DamasScene scene, int x, int y) {
        super(x, y, 200, 100);

        this.scene = scene;

        add(new FilledSprite(0, 0, 200, 100, Colors.DARKGRAY));

        add(new Label(CoreFont.getSystemFont().tint(Colors.WHITE),
                "¿Aceptas tablas?", 40, 20));

        si = Button.createLabeledButton("SI", 20, 50);
        add(si);

        no = Button.createLabeledButton("NO", 100, 50);
        add(no);
    }

    @Override
    public void update(int elapsedTime) {
        super.update(elapsedTime);

        if (enabled.get()) {
            if (si.isClicked()) {
                scene.setAceptarTablas(true);
                getParent().remove(this);
            }
            else if (no.isClicked()) {
                scene.setAceptarTablas(false);
                getParent().remove(this);
            }
        }
    }
}
