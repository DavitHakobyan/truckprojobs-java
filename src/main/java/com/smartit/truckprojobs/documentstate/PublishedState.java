package com.smartit.truckprojobs.documentstate;

public class PublishedState implements DocumentState {
    @Override
    public void publish(Document doc) {
        System.out.println("Document is already published.");
        doc.setState(new PublishedState());
    }

    @Override
    public void reject(Document doc) {
        System.out.println("Document is now rejected.");
    }
}
