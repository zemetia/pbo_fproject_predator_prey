package Collection;

public class Coordinate<U extends Number> {
    private U posX;
    private U posY;

    public Coordinate(U posX, U posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public Coordinate(Coordinate<U> data) {
        this.posX = data.getPosX();
        this.posY = data.getPosY();
    }

    public U getPosY() {
        return posY;
    }

    public U getPosX() {
        return posX;
    }

    public void setAll(U posX, U posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void setByCoordinate(Coordinate<U> data) {
        this.posX = data.getPosX();
        this.posY = data.getPosY();
    }

    public void multiplyWithCoordinate(Coordinate<U> data){
        this.posX = (U)(Object)(this.posX.doubleValue() * data.getPosX().doubleValue());
        this.posY = (U)(Object)(this.posY.doubleValue() * data.getPosY().doubleValue());
    }

    public void multiply(U value) {
        this.posX = (U)(Object)(this.posX.doubleValue() * value.doubleValue());
        this.posY = (U)(Object)(this.posY.doubleValue() * value.doubleValue());
    }

    public void addWithCoordinate(Coordinate<U> data){
        this.posX = (U)(Object)(this.posX.doubleValue() + data.getPosX().doubleValue());
        this.posY = (U)(Object)(this.posY.doubleValue() + data.getPosY().doubleValue());
    }

    public void setPosX(U posX) {
        this.posX = posX;
    }

    public void setPosY(U posY) {
        this.posY = posY;
    }

    public Coordinate<U> clone() {
        return new Coordinate<U>(this);
    }

    public double determineDistance(Coordinate<U> data) {
        Coordinate<Double> distance = new Coordinate<Double>(
                Math.abs(this.posX.doubleValue() - data.getPosX().doubleValue()),
                Math.abs(this.posY.doubleValue() - data.getPosY().doubleValue())
        );

        return distance.getPhytagoras();
    }

    public double getPhytagoras() {
        return Math.sqrt(
                (this.posX.doubleValue() * this.posX.doubleValue()) + (this.posY.doubleValue() * this.posY.doubleValue())
        );
    }
}
