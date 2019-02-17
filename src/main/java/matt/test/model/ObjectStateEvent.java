package matt.test.model;

import matt.test.enums.Events;

/**
 * Created by mskevington on 04/02/2019.
 */
public interface ObjectStateEvent {
    public void sendEvent(Events event);
}
