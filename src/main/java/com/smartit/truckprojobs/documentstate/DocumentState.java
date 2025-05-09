package com.smartit.truckprojobs.documentstate;

public interface DocumentState {
    void publish(Document doc);

    void reject(Document doc);

//    void expire(Document doc);
//
//    void archive(Document doc);
}
