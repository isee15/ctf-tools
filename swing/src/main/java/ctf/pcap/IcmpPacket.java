package ctf.pcap;

// This is a generated file! Please edit source .ksy file and use kaitai-struct-compiler to rebuild

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IcmpPacket extends KaitaiStruct {
    private IcmpTypeEnum icmpType;
    private DestinationUnreachableMsg destinationUnreachable;
    private TimeExceededMsg timeExceeded;
    private EchoMsg echo;
    private final IcmpPacket _root;
    private final KaitaiStruct _parent;

    public IcmpPacket(KaitaiStream _io) {
        this(_io, null, null);
    }

    public IcmpPacket(KaitaiStream _io, KaitaiStruct _parent) {
        this(_io, _parent, null);
    }

    public IcmpPacket(KaitaiStream _io, KaitaiStruct _parent, IcmpPacket _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root == null ? this : _root;
        _read();
    }

    public static IcmpPacket fromFile(String fileName) throws IOException {
        return new IcmpPacket(new ByteBufferKaitaiStream(fileName));
    }

    private void _read() {
        this.icmpType = IcmpTypeEnum.byId(this._io.readU1());
        if (icmpType() == IcmpTypeEnum.DESTINATION_UNREACHABLE) {
            this.destinationUnreachable = new DestinationUnreachableMsg(this._io, this, _root);
        }
        if (icmpType() == IcmpTypeEnum.TIME_EXCEEDED) {
            this.timeExceeded = new TimeExceededMsg(this._io, this, _root);
        }
        if (((icmpType() == IcmpTypeEnum.ECHO) || (icmpType() == IcmpTypeEnum.ECHO_REPLY))) {
            this.echo = new EchoMsg(this._io, this, _root);
        }
    }

    public IcmpTypeEnum icmpType() {
        return icmpType;
    }

    public DestinationUnreachableMsg destinationUnreachable() {
        return destinationUnreachable;
    }

    public TimeExceededMsg timeExceeded() {
        return timeExceeded;
    }

    public EchoMsg echo() {
        return echo;
    }

    public IcmpPacket _root() {
        return _root;
    }

    public KaitaiStruct _parent() {
        return _parent;
    }

    public enum IcmpTypeEnum {
        ECHO_REPLY(0),
        DESTINATION_UNREACHABLE(3),
        SOURCE_QUENCH(4),
        REDIRECT(5),
        ECHO(8),
        TIME_EXCEEDED(11);

        private static final Map<Long, IcmpTypeEnum> byId = new HashMap<Long, IcmpTypeEnum>(6);

        static {
            for (IcmpTypeEnum e : IcmpTypeEnum.values())
                byId.put(e.id(), e);
        }

        private final long id;

        IcmpTypeEnum(long id) {
            this.id = id;
        }

        public static IcmpTypeEnum byId(long id) {
            return byId.get(id);
        }

        public long id() {
            return id;
        }
    }

    public static class DestinationUnreachableMsg extends KaitaiStruct {
        private DestinationUnreachableCode code;
        private int checksum;
        private final IcmpPacket _root;
        private final IcmpPacket _parent;

        public DestinationUnreachableMsg(KaitaiStream _io) {
            this(_io, null, null);
        }

        public DestinationUnreachableMsg(KaitaiStream _io, IcmpPacket _parent) {
            this(_io, _parent, null);
        }

        public DestinationUnreachableMsg(KaitaiStream _io, IcmpPacket _parent, IcmpPacket _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }

        public static DestinationUnreachableMsg fromFile(String fileName) throws IOException {
            return new DestinationUnreachableMsg(new ByteBufferKaitaiStream(fileName));
        }

        private void _read() {
            this.code = DestinationUnreachableCode.byId(this._io.readU1());
            this.checksum = this._io.readU2be();
        }

        public DestinationUnreachableCode code() {
            return code;
        }

        public int checksum() {
            return checksum;
        }

        public IcmpPacket _root() {
            return _root;
        }

        public IcmpPacket _parent() {
            return _parent;
        }

        public enum DestinationUnreachableCode {
            NET_UNREACHABLE(0),
            HOST_UNREACHABLE(1),
            PROTOCOL_UNREACHABLE(2),
            PORT_UNREACHABLE(3),
            FRAGMENTATION_NEEDED_AND_DF_SET(4),
            SOURCE_ROUTE_FAILED(5),
            DST_NET_UNKOWN(6),
            SDT_HOST_UNKOWN(7),
            SRC_ISOLATED(8),
            NET_PROHIBITED_BY_ADMIN(9),
            HOST_PROHIBITED_BY_ADMIN(10),
            NET_UNREACHABLE_FOR_TOS(11),
            HOST_UNREACHABLE_FOR_TOS(12),
            COMMUNICATION_PROHIBITED_BY_ADMIN(13),
            HOST_PRECEDENCE_VIOLATION(14),
            PRECEDENCE_CUTTOFF_IN_EFFECT(15);

            private static final Map<Long, DestinationUnreachableCode> byId = new HashMap<Long, DestinationUnreachableCode>(16);

            static {
                for (DestinationUnreachableCode e : DestinationUnreachableCode.values())
                    byId.put(e.id(), e);
            }

            private final long id;

            DestinationUnreachableCode(long id) {
                this.id = id;
            }

            public static DestinationUnreachableCode byId(long id) {
                return byId.get(id);
            }

            public long id() {
                return id;
            }
        }
    }

    public static class TimeExceededMsg extends KaitaiStruct {
        private TimeExceededCode code;
        private int checksum;
        private final IcmpPacket _root;
        private final IcmpPacket _parent;

        public TimeExceededMsg(KaitaiStream _io) {
            this(_io, null, null);
        }

        public TimeExceededMsg(KaitaiStream _io, IcmpPacket _parent) {
            this(_io, _parent, null);
        }

        public TimeExceededMsg(KaitaiStream _io, IcmpPacket _parent, IcmpPacket _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }

        public static TimeExceededMsg fromFile(String fileName) throws IOException {
            return new TimeExceededMsg(new ByteBufferKaitaiStream(fileName));
        }

        private void _read() {
            this.code = TimeExceededCode.byId(this._io.readU1());
            this.checksum = this._io.readU2be();
        }

        public TimeExceededCode code() {
            return code;
        }

        public int checksum() {
            return checksum;
        }

        public IcmpPacket _root() {
            return _root;
        }

        public IcmpPacket _parent() {
            return _parent;
        }

        public enum TimeExceededCode {
            TIME_TO_LIVE_EXCEEDED_IN_TRANSIT(0),
            FRAGMENT_REASSEMBLY_TIME_EXCEEDED(1);

            private static final Map<Long, TimeExceededCode> byId = new HashMap<Long, TimeExceededCode>(2);

            static {
                for (TimeExceededCode e : TimeExceededCode.values())
                    byId.put(e.id(), e);
            }

            private final long id;

            TimeExceededCode(long id) {
                this.id = id;
            }

            public static TimeExceededCode byId(long id) {
                return byId.get(id);
            }

            public long id() {
                return id;
            }
        }
    }

    public static class EchoMsg extends KaitaiStruct {
        private byte[] code;
        private int checksum;
        private int identifier;
        private int seqNum;
        private byte[] data;
        private final IcmpPacket _root;
        private final IcmpPacket _parent;
        public EchoMsg(KaitaiStream _io) {
            this(_io, null, null);
        }
        public EchoMsg(KaitaiStream _io, IcmpPacket _parent) {
            this(_io, _parent, null);
        }
        public EchoMsg(KaitaiStream _io, IcmpPacket _parent, IcmpPacket _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }

        public static EchoMsg fromFile(String fileName) throws IOException {
            return new EchoMsg(new ByteBufferKaitaiStream(fileName));
        }

        private void _read() {
            this.code = this._io.ensureFixedContents(new byte[]{0});
            this.checksum = this._io.readU2be();
            this.identifier = this._io.readU2be();
            this.seqNum = this._io.readU2be();
            this.data = this._io.readBytesFull();
        }

        public byte[] code() {
            return code;
        }

        public int checksum() {
            return checksum;
        }

        public int identifier() {
            return identifier;
        }

        public int seqNum() {
            return seqNum;
        }

        public byte[] data() {
            return data;
        }

        public IcmpPacket _root() {
            return _root;
        }

        public IcmpPacket _parent() {
            return _parent;
        }
    }
}