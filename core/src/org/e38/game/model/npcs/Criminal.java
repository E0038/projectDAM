package org.e38.game.model.npcs;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import org.e38.game.model.Level;
import org.e38.game.utils.Recurses;

/**
 * Created by sergi on 4/20/16.
 */
// TODO: 5/31/16 add Circles
public class Criminal implements Hittable {
    protected volatile State state = State.SPAWING;
    protected float hpPoints;
    /**
     * value between 0 and 1
     */
    protected float dodgeRate = 0;
    protected float protecion = 0;
    /**
     * diff time between pointer increment
     */
    protected long speed = 10;
    protected Orientation orientation = Orientation.LEFT;
    protected OrientationListener listener;
    protected OnEndListener onEndListener;
    protected int pathPointer = 0;
    protected transient Level level;
    protected float totalHpPoins = 10f;
    protected String name = Recurses.AnimatedCriminals.bane.name();
    protected long lastNext = 0;
    private transient volatile Circle circle = new Circle(0, 0, 0);

    public Criminal() {
    }

    public Criminal(Level level) {
        this.level = level;
    }

    public float getTotalHpPoins() {
        return totalHpPoins;
    }

    public void setTotalHpPoins(float totalHpPoins) {
        this.totalHpPoins = totalHpPoins;
    }

    public OnEndListener getOnEndListener() {
        return onEndListener;
    }

    public Criminal setOnEndListener(OnEndListener onEndListener) {
        this.onEndListener = onEndListener;
        return this;
    }

    public int getPathPointer() {
        return pathPointer;
    }

    public void setPathPointer(int pathPointer) {
        this.pathPointer = pathPointer;
    }

    public Level getLevel() {
        return level;
    }

    public Criminal setLevel(Level level) {
        this.level = level;
        return this;
    }

    public OrientationListener getListener() {
        return listener;
    }

    public Criminal setListener(OrientationListener listener) {
        this.listener = listener;
        return this;
    }

    public float getProtecion() {
        return protecion;
    }

    public Criminal setProtecion(float protecion) {
        this.protecion = protecion;
        return this;
    }

    /**
     * determines if is dead
     */
    public boolean isDead() {
        return state == State.DEAD;
    }

    public float getHpPoints() {
        return hpPoints;
    }

    public Criminal setHpPoints(float hpPoints) {
        this.hpPoints = hpPoints;
        return this;
    }

    @Override
    public float getDodgeRate() {
        return dodgeRate;
    }

    @Override
    public void onHit(float damage) {
        if (damage < 0) damage = 0;
        hpPoints -= damage;
        if (hpPoints <= 0) onDie();
    }

    /**
     * get the points (Money) of Criminal
     *
     * @return the points that give on die
     */
    public int getPoints() {
        //// TODO: 5/23/16 review formula
        return (int) (totalHpPoins * 0.75f);
    }

    @Override
    public void onDodge() {

    }

    public Criminal setDodgeRate(float dodgeRate) {
        this.dodgeRate = dodgeRate;
        return this;
    }

    @Override
    public void onUpdate(float delta) {
        if (System.currentTimeMillis() - lastNext >= speed) {
            nextPosition();
        }
    }

    protected void nextPosition() {
        if (pathPointer < level.getPath().size() - 1) {
            int next = pathPointer + 1;
            changePoint(level.getPath().get(pathPointer).getProperties(), level.getPath().get(next).getProperties());
            pathPointer = next;
        } else {
            level.setLifes(level.getLifes() - getPoints());

            if (onEndListener != null) onEndListener.onEnd(this, false);
            state = State.DEAD;
        }
        lastNext = System.currentTimeMillis();
    }

    /**
     * note only support moves in horizontal or vertical
     */
    private void changePoint(MapProperties currentPoint, MapProperties nextPoint) {
        float x0 = (float) currentPoint.get("x");
        float y0 = (float) currentPoint.get("y");
        float x1 = (float) nextPoint.get("x");
        float y1 = (float) nextPoint.get("y");
        circle.set(x1, y1, 16f);
        setOritentationRelative(x0, y0, x1, y1);
    }

    private void setOritentationRelative(float x0, float y0, float x1, float y1) {
        if (x0 == x1) { //vertical
            setOrientation(y1 > y0 ? Orientation.TOP : Orientation.DOWN);
        } else if (y0 == y1) {//horizontal
            setOrientation(orientation = x1 > x0 ? Orientation.RIGHT : Orientation.LEFT);
        }
    }

    public Circle getCircle() {
        return circle;
    }

    @Override
    public Vector2 getPosition() {
        return getPositionRetativeTo(level);
    }

    @Override
    public String getName() {
        return name;
    }

    public Criminal setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public void onDie() {
        state = State.DEAD;
        level.setCoins(level.getCoins() + getPoints());
        if (onEndListener != null) onEndListener.onEnd(this, true);
    }

    @Override
    public void spawn() {
        state = State.SPAWING;
        onSpawn();
        state = State.ALIVE;
    }

    @Override
    public void onSpawn() {
        state = State.ALIVE;
        pathPointer = 0;
        hpPoints = totalHpPoins;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public boolean isAlive() {
        return state == State.ALIVE;
    }

    @Override
    public long getSpeed() {
        return speed;
    }

    @Override
    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public Criminal setOrientation(Orientation orientation) {
        Orientation old = this.orientation;
        this.orientation = orientation;
        if (listener != null) listener.onChange(old, this.orientation);
        return this;
    }

    @Override
    public OrientationListener getOrientationListener() {
        return listener;
    }

    @Override
    public NPC setOrientationListener(OrientationListener listener) {
        this.listener = listener;
        return this;
    }

    public Criminal setSpeed(long speed) {
        this.speed = speed;
        return this;
    }

    public Criminal setState(State state) {
        this.state = state;
        return this;
    }

    protected Vector2 getPositionRetativeTo(Level level) {
        float x, y;
        MapProperties camino = level.getPath().get(pathPointer).getProperties();
        x = (float) camino.get("x");
        y = (float) camino.get("y");
        return new Vector2(x, y);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Criminal{");
        sb.append("state=").append(state);
//        sb.append(", hpPoints=").append(hpPoints);
//        sb.append(", dodgeRate=").append(dodgeRate);
//        sb.append(", protecion=").append(protecion);
//        sb.append(", speed=").append(speed);
//        sb.append(", orientation=").append(orientation);
//        sb.append(", listener=").append(listener);
        sb.append(", pathPointer=").append(pathPointer);
//        sb.append(", level=").append(level);
//        sb.append(", totalHpPoins=").append(totalHpPoins);
        sb.append(", name='").append(name).append('\'');
//        sb.append(", lastNext=").append(lastNext);
        sb.append('}');
        return sb.toString();
    }

    public interface OnEndListener {
        void onEnd(Criminal criminal, boolean died);
    }
}
