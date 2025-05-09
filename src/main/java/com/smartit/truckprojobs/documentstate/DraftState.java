package com.smartit.truckprojobs.documentstate;

public class DraftState implements DocumentState {
    @Override
    public void publish(Document doc) {
        System.out.println("Document is now published.");
        doc.setState(new ModerateState());
    }

    @Override
    public void reject(Document doc) {
        System.out.println("Document is now rejected.");
    }
}
