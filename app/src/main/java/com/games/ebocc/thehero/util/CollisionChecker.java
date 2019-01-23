package com.games.ebocc.thehero.util;

import android.graphics.Rect;

public class CollisionChecker {

        public boolean checkCollision(Rect rectA, Rect rectB){

            int distanceRightX = Math.abs(rectA.right - rectB.right);
            int distanceBottomY = Math.abs(rectA.bottom - rectB.bottom);
            int threshold = 100;

            return(rectA.intersect(rectB) && distanceBottomY < threshold && distanceRightX < threshold);
        }

        public boolean checkIfBounceLeft(Rect rectA, Rect rectB){

            return(rectA.right < rectB.right && rectA.left < rectB.left);
        }

        public boolean checkIfBounceRight(Rect rectA, Rect rectB){

            return (rectA.right > rectB.right && rectA.left > rectB.left);
        }

        public boolean checkIfBounceUp(Rect rectA, Rect rectB){
            return (rectA.top < rectB.top && rectA.bottom < rectB.bottom);
        }

        public boolean checkIfBounceDown(Rect rectA, Rect rectB){
            return (rectA.top > rectB.top && rectA.bottom > rectB.bottom);
        }

}
