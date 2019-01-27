package com.games.ebocc.thehero.util;

import android.view.SurfaceView;

import com.games.ebocc.thehero.balloons.Message;

import java.util.ArrayList;
import java.util.List;

public class CharFactory{

    private SurfaceView view;

    private List<Message> messages;

    public CharFactory(SurfaceView view) {
        this.view = view;
        messages = new ArrayList<>();
    }

    public void generate(int x, int y, int index){
        Message message = new Message(x, y, view, index);
        messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }
}
