package com.games.ebocc.thehero.util;

import android.graphics.Rect;

public class CollisionChecker {

    public boolean checkCollisionOnLeft(Rect rectA, Rect rectB){

        return(rectA.right < rectB.right && rectA.left < rectB.left);
    }

    public boolean checkCollisionOnRight(Rect rectA, Rect rectB){

        return (rectA.right > rectB.right && rectA.left > rectB.left);
    }

    public boolean checkCollisionOnTop(Rect rectA, Rect rectB){
        return (rectA.top < rectB.top && rectA.bottom < rectB.bottom);
    }

    public boolean checkCollisionOnBottom(Rect rectA, Rect rectB){
        return (rectA.top > rectB.top && rectA.bottom > rectB.bottom);
    }

    public boolean checkCollisionOnTopWithNonMovingObjects(Rect rectA, Rect rectB){
        return rectA.top < rectB.top;
    }

    public boolean checkCollisionOnBottomWithNonMovingObjects(Rect rectA, Rect rectB){
        return rectA.bottom >= rectB.bottom;
    }
}
