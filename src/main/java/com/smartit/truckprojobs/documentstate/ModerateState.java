package com.smartit.truckprojobs.documentstate;

public class ModerateState implements DocumentState {
    @Override
    public void publish(Document doc) {
        System.out.println("Document is now published.");
        doc.setState(new PublishedState());
    }

    @Override
    public void reject(Document doc) {
        System.out.println("Document is now rejected.");
        doc.setState(new DraftState());
    }
}
