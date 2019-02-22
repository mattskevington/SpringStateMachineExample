package matt.test.model;

import matt.test.enums.Events;

/**
 * Receive a state event and action that event by deciding whether to move to the next State or not.
 */
public interface ObjectStateEvent {
    public void sendEvent(Events event);
}
