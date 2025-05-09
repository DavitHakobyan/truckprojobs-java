package com.smartit.truckprojobs.documentstate;

public class Document {

    private DocumentState state;

    public Document(DocumentState state) {
        this.state = state;
    }

    public void setState(DocumentState state) {
        this.state = state;
    }

    public String getStateName() {
        return state.getClass().getSimpleName();
    }

    public void publish() {
        state.publish(this);
    }

    public void reject() {
        state.reject(this);
    }
}
