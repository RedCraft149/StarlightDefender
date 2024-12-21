package com.redcraft.starlight;

import com.redcraft.starlight.server.SStarlightDefender;
import com.redcraft.starlight.shared.Connection;

public class ServerLauncher {
    public static void main(String[] args) {
        SStarlightDefender server = new SStarlightDefender(Connection.localhost(14900));
        server.create();
    }
}
