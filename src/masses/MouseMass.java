package masses;

import jgame.JGColor;
import org.jbox2d.common.Vec2;

public class MouseMass extends FixedMass {

    public MouseMass (String id, double x, double y) {
        super(id, x, y);
        this.setColor(JGColor.red);
        // TODO Auto-generated constructor stub
    }

    public void move () {
        myBody.setLinearVelocity(new Vec2(0.0f, 0.0f));
    }

}
