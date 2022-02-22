
import java.util.*;

public class ECJ_MessageSystem {
    private final Deque<ECJ_Event> events;
    private final Deque<ECJ_Command> commands;
    private final Map<ECJ_EventType, ArrayList<I_ECJ_EventListener>> event_subscribers;
    private final Map<ECJ_CommandType, ArrayList<I_ECJ_CommandListener>> command_subscribers;

    public ECJ_MessageSystem() {
        event_subscribers = new HashMap<ECJ_EventType, ArrayList<I_ECJ_EventListener>>();
        command_subscribers = new HashMap<ECJ_CommandType, ArrayList<I_ECJ_CommandListener>>();
        events = new ArrayDeque<ECJ_Event>();
        commands = new ArrayDeque<ECJ_Command>();
    }

    public void sendMessages() {
        while(!events.isEmpty()) {
            ECJ_Event event = events.removeFirst();
            var subs = event_subscribers.get(event.getType());
            for (var sub:subs) {
                sub.handleEvent(event);
            }
        }
        while(!commands.isEmpty()) {
            ECJ_Command command = commands.removeFirst();
            var subs = command_subscribers.get(command.getType());
            for (var sub:subs) {
                sub.handleCommand(command);
            }
        }
    }

    public void publish(ECJ_Event ecj_event) {
        events.addLast(ecj_event);
    }

    public void publish(ECJ_Command ecj_command) {
        commands.addLast(ecj_command);
    }

    public void subscribe(I_ECJ_CommandListener listener, ECJ_CommandType[] commands) {
        for (var command_type: commands) {
            var subs = command_subscribers.computeIfAbsent(command_type, k -> new ArrayList<I_ECJ_CommandListener>());
            subs.add(listener);
        }
    }
    public void subscribe(I_ECJ_EventListener listener, ECJ_EventType[] events) {
        for (var event_type: events) {
            var subs = event_subscribers.computeIfAbsent(event_type, k -> new ArrayList<I_ECJ_EventListener>());
            subs.add(listener);
        }
    }
}
