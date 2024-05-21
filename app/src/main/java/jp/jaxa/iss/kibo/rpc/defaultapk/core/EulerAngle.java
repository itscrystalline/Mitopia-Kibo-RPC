package core;

public class EulerAngle {
    double degX,degY,degZ;
    double radX,radY,radZ;
    public EulerAngle(double degX,double degY,double degZ){
        this.degX = degX; //(degX/360-(int)degX/360)*360; prevent angle overshoot 360 for debug purpose
        this.degY = degY; //(degY/360-(int)degY/360)*360;
        this.degZ = degZ; //(degZ/360-(int)degZ/360)*360;
        radX = Math.toRadians(degX);
        radY = Math.toRadians(degY);
        radZ = Math.toRadians(degZ);
    }
    public static EulerAngle add(EulerAngle first, EulerAngle second){
        EulerAngle result = new EulerAngle(first.degX+second.degX, first.degY+second.degY, first.degZ+second.degZ);
        return result;
    }
    public static EulerAngle subtract(EulerAngle first, EulerAngle second){
        EulerAngle result = new EulerAngle(first.degX-second.degX, first.degY-second.degY, first.degZ-second.degZ);
        return result;
    }
    public static EulerAngle multiplyFloat(EulerAngle first, float value){
        EulerAngle result = new EulerAngle(first.degX*value, first.degY*value, first.degZ*value);
        return result;
    }
    /*@Override
    public String toString() {
        String res = ("Degrees: ("+degX+","+degY+","+degZ+")\n"+"Rads: ("+radX+","+radY+","+radZ+")");
        return res;
    }
    FOR DEBUGGING
    */
}
