package rabbit.sender;

import com.rabbitmq.client.Channel;
import org.apache.log4j.Logger;
import rabbit.MQConnection;

import java.io.IOException;

public class Sender {

    private static final Logger log = Logger.getLogger(Sender.class);

    MQConnection connection;
    private final Channel outputChannel;
    private final String OUTPUT_QUEUE;

    private Sender(MQConnection mqConnection) {
        this.connection = mqConnection;
        this.outputChannel = mqConnection.getOutputChannel();
        this.OUTPUT_QUEUE = MQConnection.getOutputQueue();
    }

    public static Sender initSender(MQConnection mqConnection){
        return new Sender(mqConnection);
    }

    public void send(String jsonOutput) {

        System.out.println(jsonOutput);

                try {
                    log.debug("Publishing to " + OUTPUT_QUEUE);
                    outputChannel.basicPublish("", OUTPUT_QUEUE, null, jsonOutput.getBytes());
                    connection.confirm();
                } catch (IOException e) {
                    log.error(e);
                }


    }


}
