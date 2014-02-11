package masses;

import org.jbox2d.common.Vec2;
import springies.Constants;


public class FixedMass extends Mass {
    private double mStartX;
    private double mStartY;

    public FixedMass (String id, double x, double y, double mass) {
        super(id, x, y, mass);
        mStartX = x;
        mStartY = y;
        this.myBody.setLinearVelocity(new Vec2(0.0f, 0.0f));
    }

    public FixedMass (String id, double x, double y) {
        this(id, x, y, Constants.DEFAULT_MASS);
    }

    @Override
    public void applyForceVector (Vec2 force) {
        myBody.applyForce(new Vec2(0.0f, 0.0f), myBody.m_xf.position);
    }

    @Override
    public void move () {
        myBody.setLinearVelocity(new Vec2(0.0f, 0.0f));
        setPos(mStartX, mStartY);
        super.move();
        // System.out.printf("Position (JGAME) of fixed mass: (%.2f, %.2f)\n", this.x, this.y);
        // System.out.printf("Position (JBOX) of fixed mass: (%.2f, %.2f)\n",
        // myBody.getPosition().x, myBody.getPosition().y);
    }

}
