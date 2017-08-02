package com.example.dawson.mountgoodcontrol;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class OnSwipeListener extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        // Grab two events located on the plane at e1=(x1, y1) and e2=(x2, y2)
        // Let e1 be the initial event
        // e2 can be located at 4 different positions, consider the following diagram
        // (Assume that lines are separated by 90 degrees.)
        //
        //
        //         \ A  /
        //          \  /
        //       D   e1   B
        //          /  \
        //         / C  \
        //
        // So if (x2,y2) falls in region:
        //  A => it's an UP swipe
        //  B => it's a RIGHT swipe
        //  C => it's a DOWN swipe
        //  D => it's a LEFT swipe

        float x1 = e1.getX();
        float y1 = e1.getY();

        float x2 = e2.getX();
        float y2 = e2.getY();

        Direction direction = getDirection(x1,y1,x2,y2);
        return onSwipe(direction, velocityX, velocityY);
    }

    public boolean onSwipe(Direction direction, float velocityX, float velocityY){
        return false;
    }

    private Direction getDirection(float x1, float y1, float x2, float y2){
        double angle = getAngle(x1, y1, x2, y2);
        return Direction.getDirection(angle);
    }

    private double getAngle(float x1, float y1, float x2, float y2) {
        double rad = Math.atan2(y1-y2,x2-x1) + Math.PI;
        return (rad*180/Math.PI + 180)%360;
    }

    public enum Direction{
        up,
        down,
        left,
        right;

        /**
         * Returns a direction given an angle.
         * Directions are defined as follows:
         *
         * Up: [45, 135]
         * Right: [0,45] and [315, 360]
         * Down: [225, 315]
         * Left: [135, 225]
         *
         * @param angle an angle from 0 to 360 - e
         * @return the direction of an angle
         */
        public static Direction getDirection(double angle){
            if(inRange(angle, 45, 135)){ return Direction.up; }
            else if(inRange(angle, 0,45) || inRange(angle, 315, 360)){ return Direction.right; }
            else if(inRange(angle, 225, 315)){ return Direction.down; }
            else{ return Direction.left; }
        }

        private static boolean inRange(double angle, float init, float end){
            return (angle >= init) && (angle < end);
        }
    }
}