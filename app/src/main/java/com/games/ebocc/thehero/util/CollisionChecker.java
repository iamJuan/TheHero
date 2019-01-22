package com.games.ebocc.thehero.util;

import android.graphics.Rect;

public class CollisionChecker {

        public boolean checkCollision(Rect rectA, Rect rectB){

            int distanceRightX = Math.abs(rectA.right - rectB.right);
            int distanceBottomY = Math.abs(rectA.bottom - rectB.bottom);
            int threshold = 150;

            if(rectA.intersect(rectB) && distanceBottomY < threshold && distanceRightX < threshold){
                return true;
            }
            return false;
        }

        public boolean checkIfBounceLeft(Rect rectA, Rect rectB){

            if(rectA.right < rectB.right && rectA.left < rectB.left){
                return true;
            }
            return false;
        }

        public boolean checkIfBounceRight(Rect rectA, Rect rectB){

            if(rectA.right > rectB.right && rectA.left > rectB.left){
                return true;
            }
            return false;
        }

        public boolean checkIfBounceUp(Rect rectA, Rect rectB){
            if(rectA.top < rectB.top && rectA.bottom < rectB.bottom){
                return true;
            }

            return false;
        }

        public boolean checkIfBounceDown(Rect rectA, Rect rectB){
            if(rectA.top > rectB.top && rectA.bottom > rectB.bottom){
                return true;
            }

            return false;
        }

}
