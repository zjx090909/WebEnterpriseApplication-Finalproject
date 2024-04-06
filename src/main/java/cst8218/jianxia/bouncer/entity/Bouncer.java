package cst8218.jianxia.bouncer.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bouncer class represents a bouncing object in a game.
 * It defines the attributes and behavior of a bouncing object which is used in a game. 
 * It includes properties including position (x-axis, y-axis) and velocity (yVelocity), and some constants.
 * It class also implements methods to simulate the movement of the bouncing object.
 * 
 * @author zhangjianxia
 */
@Entity
@XmlRootElement
public class Bouncer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    // Define constants for game behavior
    private static final int FRAME_WIDTH = 800; // Define your frame width
    private static final int FRAME_HEIGHT = 600; // Define your frame height
    private static final int GRAVITY_ACCEL = 1; // Gravity acceleration
    private static final int DECAY_RATE = 1; // Rate at which yVelocity decreases after bouncing
    private static final int CHANGE_RATE = 60; // Number of times advanceOneFrame method is called per second
    
    // Define fields for game behavior
    private int x; // X position of the bouncer
    private int y; // Y position of the bouncer
    private int yVelocity; // Velocity of the bouncer in the y direction

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Updates the properties to simulate the passing of one unit of time
     */
    public void advanceOneFrame() {
    // Move the bouncer according to its velocity
    y += yVelocity;

    // Check if the bouncer goes beyond the top or bottom walls
    if (y <= 0) {
        // If the bouncer hits the top wall, reverse the velocity and set y to 0
        y = 0;
        yVelocity = -yVelocity - DECAY_RATE;
    } else if (y >= FRAME_HEIGHT) {
        // If the bouncer hits the bottom wall, reverse the velocity and set y to FRAME_HEIGHT
        y = FRAME_HEIGHT;
        yVelocity = -yVelocity - DECAY_RATE;
    }

    // Apply gravity if the bouncer is not at the bottom wall
    if (y < FRAME_HEIGHT) {
        yVelocity += GRAVITY_ACCEL;
    }

    // Ensure yVelocity does not exceed the maximum velocity
    if (yVelocity > 100) {
        yVelocity = 100;
    } else if (yVelocity < -100) {
        yVelocity = -100;
    }
}

    // Getters and setters for x, y, and yVelocity
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bouncer)) {
            return false;
        }
        Bouncer other = (Bouncer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cst8218.jianxia.bouncer.entity.Bouncer[ id=" + id + " ]";
    }
}