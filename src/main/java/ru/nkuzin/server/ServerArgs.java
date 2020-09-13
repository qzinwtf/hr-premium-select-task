package ru.nkuzin.server;

import com.beust.jcommander.Parameter;
import lombok.Data;

@Data
public class ServerArgs {
    @Parameter(names = "-port", description = "Server's port")
    private int port = 7071;
}
