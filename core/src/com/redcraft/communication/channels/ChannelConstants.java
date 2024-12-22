package com.redcraft.communication.channels;

public interface ChannelConstants {
    /**Identity header for Indirect channel*/
    int NEW_PACKET = 0x70000000;

    int MAP_HEADER          = 0x00000001;
    int STRING_HEADER       = 0x00000002;
    int BOOLEAN_HEADER      = 0x00000003;
    int BYTE_HEADER         = 0x00000004;
    int SHORT_HEADER        = 0x00000005;
    int INTEGER_HEADER      = 0x00000006;
    int LONG_HEADER         = 0x00000007;
    int FLOAT_HEADER        = 0x00000008;
    int DOUBLE_HEADER       = 0x00000009;
    int CHAR_HEADER         = 0x0000000A;
    int PUBLIC_KEY_HEADER   = 0x0000000B;
    int REGISTRATION_HEADER      = 0x00000020;
    int REGISTRATION_FAIL_HEADER = 0x00000021;
    int ENCRYPTED_MODIFIER  = 0x80000000;
}
