package ru.nkuzin.client;

import com.beust.jcommander.Parameter;
import lombok.Data;

@Data
public class ClientArgs {
    @Parameter(names = "-host", description = "News analyzer's host")
    private String serverHost = "127.0.0.1";
    @Parameter(names = "-port", description = "News analyzer's port")
    private int serverPort = 7071;
    @Parameter(names = "-freq", description = "Frequency of news generation (how many news will be generated in a SECOND), maximum is 1000")
    private int frequency = 10;
}
