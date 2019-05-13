package kafka.streams.globalktable.join;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

public class UserTransformer implements Transformer<String, Long, KeyValue<String, Long>> {

    private String stateStoreName;
    private ReadOnlyKeyValueStore<String, String> keyValueStore;


    public UserTransformer(String stateStoreName) {
        this.stateStoreName = stateStoreName;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void init(ProcessorContext processorContext) {
        keyValueStore = (ReadOnlyKeyValueStore) processorContext.getStateStore(stateStoreName);
    }

    @Override
    public KeyValue<String, Long> transform(String user, Long value) {
        if (user != null) {
            System.out.println("UserTransformer Message => Key: " +  user + ", Value: " + value);
            System.out.println("UserTransformer => keyValueStore..approximateNumEntries(): " +  keyValueStore.approximateNumEntries());
            String regionTopExport = keyValueStore.get(user);
            System.out.println("UserTransformer => regionTopExport: " +  regionTopExport);
            if(regionTopExport != null){
                //Do something with the user occupation here
            }
        }
        return new KeyValue<>(user, value);
    }

    @Override
    public void close() {
        //no-op
    }

}