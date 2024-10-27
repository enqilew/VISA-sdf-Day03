package game;

public class Box implements Damageable {

    private int integrity = 3;

    public int getIntegrity() { 
        return integrity;  
    }
    public void setIntegrity(int integrity) {
        this.integrity = integrity;
    } 

    public void damage(int damage) {
        integrity -= damage;
    }

    public int getDamaged() {
        return this.integrity;
    } 
    
}
