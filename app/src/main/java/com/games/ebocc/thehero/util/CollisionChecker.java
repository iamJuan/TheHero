package com.games.ebocc.thehero.util;

import android.graphics.Rect;

public class CollisionChecker {

        public boolean checkCollision(Rect rectA, Rect rectB){

            int distanceRightX = Math.abs(rectA.right - rectB.right);
            int distanceBottomY = Math.abs(rectA.bottom - rectB.bottom);
            int threshold = 100;

            if(rectA.intersect(rectB) && distanceBottomY < threshold && distanceRightX < threshold){
                return true;
            }
            return false;
        }

        public boolean checkAlignmentY(Rect rectA, Rect rectB){
            int distanceTopY = Math.abs(rectA.top - rectB.top);
            int distanceBottomY = Math.abs(rectA.bottom - rectB.bottom);
            int threshold = 100;

            if(distanceBottomY <= threshold && distanceTopY <= threshold){
                return true;
            }
            return false;
        }

        public boolean checkIfBounceToLeft(Rect rectA, Rect rectB){

            if((rectA.right <= rectB.right && rectA.left <= rectB.left)){
                return true;
            }
            return false;
        }

        public boolean checkIfBounceToRight(Rect rectA, Rect rectB){

            if((rectA.right >= rectB.right && rectA.left >= rectB.left)){
                return true;
            }
            return false;
        }

}
