package masses;

import jgame.JGColor;

import org.jbox2d.common.Vec2;

import springies.Constants;

/**
 * A template for a mass to be created by the user based 
 * on position of a mouse click.
 * 
 * @author Thanh-Ha Nguyen & Dennis Park
 */
public class MouseMass extends FixedMass {

	/**
     * Constructor for MouseMass with private member attributes.
     * Default color is red.
     * @param id
     * @param x
     * @param y
     */
    public MouseMass (String id, double x, double y) {
        super(id, x, y);
        this.setColor(JGColor.red);
    }

    /**
     * MouseMass does not move according to spring
     */
    public void move () {
        myBody.setLinearVelocity(Constants.ZERO_VECTOR);
    }

}
