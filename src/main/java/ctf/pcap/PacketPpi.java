package ctf.pcap;

// This is a generated file! Please edit source .ksy file and use kaitai-struct-compiler to rebuild

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * PPI is a standard for link layer packet encapsulation, proposed as
 * generic extensible container to store both captured in-band data and
 * out-of-band data. Originally it was developed to provide 802.11n
 * radio information, but can be used for other purposes as well.
 * <p>
 * Sample capture: https://wiki.wireshark.org/SampleCaptures?action=AttachFile&do=get&target=Http.cap
 *
 * @see <a href="https://www.cacetech.com/documents/PPI_Header_format_1.0.1.pdf">PPI header format spec, section 3</a>
 */
public class PacketPpi extends KaitaiStruct {
    private PacketPpiHeader header;
    private PacketPpiFields fields;
    private Object body;
    private PacketPpi _root;
    private KaitaiStruct _parent;
    private byte[] _raw_fields;
    private byte[] _raw_body;

    public PacketPpi(KaitaiStream _io) {
        this(_io, null, null);
    }

    public PacketPpi(KaitaiStream _io, KaitaiStruct _parent) {
        this(_io, _parent, null);
    }

    public PacketPpi(KaitaiStream _io, KaitaiStruct _parent, PacketPpi _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root == null ? this : _root;
        _read();
    }

    public static PacketPpi fromFile(String fileName) throws IOException {
        return new PacketPpi(new ByteBufferKaitaiStream(fileName));
    }

    private void _read() {
        this.header = new PacketPpiHeader(this._io, this, _root);
        this._raw_fields = this._io.readBytes((header().pphLen() - 8));
        KaitaiStream _io__raw_fields = new ByteBufferKaitaiStream(_raw_fields);
        this.fields = new PacketPpiFields(_io__raw_fields, this, _root);
        switch (header().pphDlt()) {
            case PPI: {
                this._raw_body = this._io.readBytesFull();
                KaitaiStream _io__raw_body = new ByteBufferKaitaiStream(_raw_body);
                this.body = new PacketPpi(_io__raw_body);
                break;
            }
            case ETHERNET: {
                this._raw_body = this._io.readBytesFull();
                KaitaiStream _io__raw_body = new ByteBufferKaitaiStream(_raw_body);
                this.body = new EthernetFrame(_io__raw_body);
                break;
            }
            default: {
                this.body = this._io.readBytesFull();
                break;
            }
        }
    }

    public PacketPpiHeader header() {
        return header;
    }

    public PacketPpiFields fields() {
        return fields;
    }

    public Object body() {
        return body;
    }

    public PacketPpi _root() {
        return _root;
    }

    public KaitaiStruct _parent() {
        return _parent;
    }

    public byte[] _raw_fields() {
        return _raw_fields;
    }

    public byte[] _raw_body() {
        return _raw_body;
    }
    public enum PfhType {
        RADIO_802_11_COMMON(2),
        RADIO_802_11N_MAC_EXT(3),
        RADIO_802_11N_MAC_PHY_EXT(4),
        SPECTRUM_MAP(5),
        PROCESS_INFO(6),
        CAPTURE_INFO(7);

        private static final Map<Long, PfhType> byId = new HashMap<Long, PfhType>(6);

        static {
            for (PfhType e : PfhType.values())
                byId.put(e.id(), e);
        }

        private final long id;

        PfhType(long id) {
            this.id = id;
        }

        public static PfhType byId(long id) {
            return byId.get(id);
        }

        public long id() {
            return id;
        }
    }
    public enum Linktype {
        NULL_LINKTYPE(0),
        ETHERNET(1),
        AX25(3),
        IEEE802_5(6),
        ARCNET_BSD(7),
        SLIP(8),
        PPP(9),
        FDDI(10),
        PPP_HDLC(50),
        PPP_ETHER(51),
        ATM_RFC1483(100),
        RAW(101),
        C_HDLC(104),
        IEEE802_11(105),
        FRELAY(107),
        LOOP(108),
        LINUX_SLL(113),
        LTALK(114),
        PFLOG(117),
        IEEE802_11_PRISM(119),
        IP_OVER_FC(122),
        SUNATM(123),
        IEEE802_11_RADIOTAP(127),
        ARCNET_LINUX(129),
        APPLE_IP_OVER_IEEE1394(138),
        MTP2_WITH_PHDR(139),
        MTP2(140),
        MTP3(141),
        SCCP(142),
        DOCSIS(143),
        LINUX_IRDA(144),
        USER0(147),
        USER1(148),
        USER2(149),
        USER3(150),
        USER4(151),
        USER5(152),
        USER6(153),
        USER7(154),
        USER8(155),
        USER9(156),
        USER10(157),
        USER11(158),
        USER12(159),
        USER13(160),
        USER14(161),
        USER15(162),
        IEEE802_11_AVS(163),
        BACNET_MS_TP(165),
        PPP_PPPD(166),
        GPRS_LLC(169),
        GPF_T(170),
        GPF_F(171),
        LINUX_LAPD(177),
        BLUETOOTH_HCI_H4(187),
        USB_LINUX(189),
        PPI(192),
        IEEE802_15_4(195),
        SITA(196),
        ERF(197),
        BLUETOOTH_HCI_H4_WITH_PHDR(201),
        AX25_KISS(202),
        LAPD(203),
        PPP_WITH_DIR(204),
        C_HDLC_WITH_DIR(205),
        FRELAY_WITH_DIR(206),
        IPMB_LINUX(209),
        IEEE802_15_4_NONASK_PHY(215),
        USB_LINUX_MMAPPED(220),
        FC_2(224),
        FC_2_WITH_FRAME_DELIMS(225),
        IPNET(226),
        CAN_SOCKETCAN(227),
        IPV4(228),
        IPV6(229),
        IEEE802_15_4_NOFCS(230),
        DBUS(231),
        DVB_CI(235),
        MUX27010(236),
        STANAG_5066_D_PDU(237),
        NFLOG(239),
        NETANALYZER(240),
        NETANALYZER_TRANSPARENT(241),
        IPOIB(242),
        MPEG_2_TS(243),
        NG40(244),
        NFC_LLCP(245),
        INFINIBAND(247),
        SCTP(248),
        USBPCAP(249),
        RTAC_SERIAL(250),
        BLUETOOTH_LE_LL(251),
        NETLINK(253),
        BLUETOOTH_LINUX_MONITOR(254),
        BLUETOOTH_BREDR_BB(255),
        BLUETOOTH_LE_LL_WITH_PHDR(256),
        PROFIBUS_DL(257),
        PKTAP(258),
        EPON(259),
        IPMI_HPM_2(260),
        ZWAVE_R1_R2(261),
        ZWAVE_R3(262),
        WATTSTOPPER_DLM(263),
        ISO_14443(264);

        private static final Map<Long, Linktype> byId = new HashMap<Long, Linktype>(104);

        static {
            for (Linktype e : Linktype.values())
                byId.put(e.id(), e);
        }

        private final long id;

        Linktype(long id) {
            this.id = id;
        }

        public static Linktype byId(long id) {
            return byId.get(id);
        }

        public long id() {
            return id;
        }
    }

    public static class PacketPpiFields extends KaitaiStruct {
        private ArrayList<PacketPpiField> entries;
        private PacketPpi _root;
        private PacketPpi _parent;

        public PacketPpiFields(KaitaiStream _io) {
            this(_io, null, null);
        }

        public PacketPpiFields(KaitaiStream _io, PacketPpi _parent) {
            this(_io, _parent, null);
        }

        public PacketPpiFields(KaitaiStream _io, PacketPpi _parent, PacketPpi _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }

        public static PacketPpiFields fromFile(String fileName) throws IOException {
            return new PacketPpiFields(new ByteBufferKaitaiStream(fileName));
        }

        private void _read() {
            this.entries = new ArrayList<PacketPpiField>();
            {
                int i = 0;
                while (!this._io.isEof()) {
                    this.entries.add(new PacketPpiField(this._io, this, _root));
                    i++;
                }
            }
        }

        public ArrayList<PacketPpiField> entries() {
            return entries;
        }

        public PacketPpi _root() {
            return _root;
        }

        public PacketPpi _parent() {
            return _parent;
        }
    }

    /**
     * @see <a href="https://www.cacetech.com/documents/PPI_Header_format_1.0.1.pdf">PPI header format spec, section 4.1.3</a>
     */
    public static class Radio80211nMacExtBody extends KaitaiStruct {
        private MacFlags flags;
        private long aMpduId;
        private int numDelimiters;
        private byte[] reserved;
        private PacketPpi _root;
        private PacketPpi.PacketPpiField _parent;
        public Radio80211nMacExtBody(KaitaiStream _io) {
            this(_io, null, null);
        }
        public Radio80211nMacExtBody(KaitaiStream _io, PacketPpi.PacketPpiField _parent) {
            this(_io, _parent, null);
        }
        public Radio80211nMacExtBody(KaitaiStream _io, PacketPpi.PacketPpiField _parent, PacketPpi _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }

        public static Radio80211nMacExtBody fromFile(String fileName) throws IOException {
            return new Radio80211nMacExtBody(new ByteBufferKaitaiStream(fileName));
        }

        private void _read() {
            this.flags = new MacFlags(this._io, this, _root);
            this.aMpduId = this._io.readU4le();
            this.numDelimiters = this._io.readU1();
            this.reserved = this._io.readBytes(3);
        }

        public MacFlags flags() {
            return flags;
        }

        public long aMpduId() {
            return aMpduId;
        }

        public int numDelimiters() {
            return numDelimiters;
        }

        public byte[] reserved() {
            return reserved;
        }

        public PacketPpi _root() {
            return _root;
        }

        public PacketPpi.PacketPpiField _parent() {
            return _parent;
        }
    }

    public static class MacFlags extends KaitaiStruct {
        private boolean unused1;
        private boolean aggregateDelimiter;
        private boolean moreAggregates;
        private boolean aggregate;
        private boolean dupRx;
        private boolean rxShortGuard;
        private boolean isHt40;
        private boolean greenfield;
        private byte[] unused2;
        private PacketPpi _root;
        private KaitaiStruct _parent;
        public MacFlags(KaitaiStream _io) {
            this(_io, null, null);
        }
        public MacFlags(KaitaiStream _io, KaitaiStruct _parent) {
            this(_io, _parent, null);
        }
        public MacFlags(KaitaiStream _io, KaitaiStruct _parent, PacketPpi _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }

        public static MacFlags fromFile(String fileName) throws IOException {
            return new MacFlags(new ByteBufferKaitaiStream(fileName));
        }

        private void _read() {
            this.unused1 = this._io.readBitsInt(1) != 0;
            this.aggregateDelimiter = this._io.readBitsInt(1) != 0;
            this.moreAggregates = this._io.readBitsInt(1) != 0;
            this.aggregate = this._io.readBitsInt(1) != 0;
            this.dupRx = this._io.readBitsInt(1) != 0;
            this.rxShortGuard = this._io.readBitsInt(1) != 0;
            this.isHt40 = this._io.readBitsInt(1) != 0;
            this.greenfield = this._io.readBitsInt(1) != 0;
            this._io.alignToByte();
            this.unused2 = this._io.readBytes(3);
        }

        public boolean unused1() {
            return unused1;
        }

        /**
         * Aggregate delimiter CRC error after this frame
         */
        public boolean aggregateDelimiter() {
            return aggregateDelimiter;
        }

        /**
         * More aggregates
         */
        public boolean moreAggregates() {
            return moreAggregates;
        }

        /**
         * Aggregate
         */
        public boolean aggregate() {
            return aggregate;
        }

        /**
         * Duplicate RX
         */
        public boolean dupRx() {
            return dupRx;
        }

        /**
         * RX short guard interval (SGI)
         */
        public boolean rxShortGuard() {
            return rxShortGuard;
        }

        /**
         * true = HT40, false = HT20
         */
        public boolean isHt40() {
            return isHt40;
        }

        /**
         * Greenfield
         */
        public boolean greenfield() {
            return greenfield;
        }

        public byte[] unused2() {
            return unused2;
        }

        public PacketPpi _root() {
            return _root;
        }

        public KaitaiStruct _parent() {
            return _parent;
        }
    }

    /**
     * @see <a href="https://www.cacetech.com/documents/PPI_Header_format_1.0.1.pdf">PPI header format spec, section 3.1</a>
     */
    public static class PacketPpiHeader extends KaitaiStruct {
        private int pphVersion;
        private int pphFlags;
        private int pphLen;
        private Linktype pphDlt;
        private PacketPpi _root;
        private PacketPpi _parent;
        public PacketPpiHeader(KaitaiStream _io) {
            this(_io, null, null);
        }
        public PacketPpiHeader(KaitaiStream _io, PacketPpi _parent) {
            this(_io, _parent, null);
        }
        public PacketPpiHeader(KaitaiStream _io, PacketPpi _parent, PacketPpi _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }

        public static PacketPpiHeader fromFile(String fileName) throws IOException {
            return new PacketPpiHeader(new ByteBufferKaitaiStream(fileName));
        }

        private void _read() {
            this.pphVersion = this._io.readU1();
            this.pphFlags = this._io.readU1();
            this.pphLen = this._io.readU2le();
            this.pphDlt = PacketPpi.Linktype.byId(this._io.readU4le());
        }

        public int pphVersion() {
            return pphVersion;
        }

        public int pphFlags() {
            return pphFlags;
        }

        public int pphLen() {
            return pphLen;
        }

        public Linktype pphDlt() {
            return pphDlt;
        }

        public PacketPpi _root() {
            return _root;
        }

        public PacketPpi _parent() {
            return _parent;
        }
    }

    /**
     * @see <a href="https://www.cacetech.com/documents/PPI_Header_format_1.0.1.pdf">PPI header format spec, section 4.1.2</a>
     */
    public static class Radio80211CommonBody extends KaitaiStruct {
        private long tsfTimer;
        private int flags;
        private int rate;
        private int channelFreq;
        private int channelFlags;
        private int fhssHopset;
        private int fhssPattern;
        private byte dbmAntsignal;
        private byte dbmAntnoise;
        private PacketPpi _root;
        private PacketPpi.PacketPpiField _parent;
        public Radio80211CommonBody(KaitaiStream _io) {
            this(_io, null, null);
        }
        public Radio80211CommonBody(KaitaiStream _io, PacketPpi.PacketPpiField _parent) {
            this(_io, _parent, null);
        }
        public Radio80211CommonBody(KaitaiStream _io, PacketPpi.PacketPpiField _parent, PacketPpi _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }

        public static Radio80211CommonBody fromFile(String fileName) throws IOException {
            return new Radio80211CommonBody(new ByteBufferKaitaiStream(fileName));
        }

        private void _read() {
            this.tsfTimer = this._io.readU8le();
            this.flags = this._io.readU2le();
            this.rate = this._io.readU2le();
            this.channelFreq = this._io.readU2le();
            this.channelFlags = this._io.readU2le();
            this.fhssHopset = this._io.readU1();
            this.fhssPattern = this._io.readU1();
            this.dbmAntsignal = this._io.readS1();
            this.dbmAntnoise = this._io.readS1();
        }

        public long tsfTimer() {
            return tsfTimer;
        }

        public int flags() {
            return flags;
        }

        public int rate() {
            return rate;
        }

        public int channelFreq() {
            return channelFreq;
        }

        public int channelFlags() {
            return channelFlags;
        }

        public int fhssHopset() {
            return fhssHopset;
        }

        public int fhssPattern() {
            return fhssPattern;
        }

        public byte dbmAntsignal() {
            return dbmAntsignal;
        }

        public byte dbmAntnoise() {
            return dbmAntnoise;
        }

        public PacketPpi _root() {
            return _root;
        }

        public PacketPpi.PacketPpiField _parent() {
            return _parent;
        }
    }

    /**
     * @see <a href="https://www.cacetech.com/documents/PPI_Header_format_1.0.1.pdf">PPI header format spec, section 3.1</a>
     */
    public static class PacketPpiField extends KaitaiStruct {
        private PfhType pfhType;
        private int pfhDatalen;
        private Object body;
        private PacketPpi _root;
        private PacketPpi.PacketPpiFields _parent;
        private byte[] _raw_body;
        public PacketPpiField(KaitaiStream _io) {
            this(_io, null, null);
        }
        public PacketPpiField(KaitaiStream _io, PacketPpi.PacketPpiFields _parent) {
            this(_io, _parent, null);
        }
        public PacketPpiField(KaitaiStream _io, PacketPpi.PacketPpiFields _parent, PacketPpi _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }

        public static PacketPpiField fromFile(String fileName) throws IOException {
            return new PacketPpiField(new ByteBufferKaitaiStream(fileName));
        }

        private void _read() {
            this.pfhType = PacketPpi.PfhType.byId(this._io.readU2le());
            this.pfhDatalen = this._io.readU2le();
            switch (pfhType()) {
                case RADIO_802_11_COMMON: {
                    this._raw_body = this._io.readBytes(pfhDatalen());
                    KaitaiStream _io__raw_body = new ByteBufferKaitaiStream(_raw_body);
                    this.body = new Radio80211CommonBody(_io__raw_body, this, _root);
                    break;
                }
                case RADIO_802_11N_MAC_EXT: {
                    this._raw_body = this._io.readBytes(pfhDatalen());
                    KaitaiStream _io__raw_body = new ByteBufferKaitaiStream(_raw_body);
                    this.body = new Radio80211nMacExtBody(_io__raw_body, this, _root);
                    break;
                }
                case RADIO_802_11N_MAC_PHY_EXT: {
                    this._raw_body = this._io.readBytes(pfhDatalen());
                    KaitaiStream _io__raw_body = new ByteBufferKaitaiStream(_raw_body);
                    this.body = new Radio80211nMacPhyExtBody(_io__raw_body, this, _root);
                    break;
                }
                default: {
                    this.body = this._io.readBytes(pfhDatalen());
                    break;
                }
            }
        }

        public PfhType pfhType() {
            return pfhType;
        }

        public int pfhDatalen() {
            return pfhDatalen;
        }

        public Object body() {
            return body;
        }

        public PacketPpi _root() {
            return _root;
        }

        public PacketPpi.PacketPpiFields _parent() {
            return _parent;
        }

        public byte[] _raw_body() {
            return _raw_body;
        }
    }

    /**
     * @see <a href="https://www.cacetech.com/documents/PPI_Header_format_1.0.1.pdf">PPI header format spec, section 4.1.4</a>
     */
    public static class Radio80211nMacPhyExtBody extends KaitaiStruct {
        private MacFlags flags;
        private long aMpduId;
        private int numDelimiters;
        private int mcs;
        private int numStreams;
        private int rssiCombined;
        private ArrayList<Integer> rssiAntCtl;
        private ArrayList<Integer> rssiAntExt;
        private int extChannelFreq;
        private ChannelFlags extChannelFlags;
        private ArrayList<SignalNoise> rfSignalNoise;
        private ArrayList<Long> evm;
        private PacketPpi _root;
        private PacketPpi.PacketPpiField _parent;
        public Radio80211nMacPhyExtBody(KaitaiStream _io) {
            this(_io, null, null);
        }
        public Radio80211nMacPhyExtBody(KaitaiStream _io, PacketPpi.PacketPpiField _parent) {
            this(_io, _parent, null);
        }
        public Radio80211nMacPhyExtBody(KaitaiStream _io, PacketPpi.PacketPpiField _parent, PacketPpi _root) {
            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }

        public static Radio80211nMacPhyExtBody fromFile(String fileName) throws IOException {
            return new Radio80211nMacPhyExtBody(new ByteBufferKaitaiStream(fileName));
        }

        private void _read() {
            this.flags = new MacFlags(this._io, this, _root);
            this.aMpduId = this._io.readU4le();
            this.numDelimiters = this._io.readU1();
            this.mcs = this._io.readU1();
            this.numStreams = this._io.readU1();
            this.rssiCombined = this._io.readU1();
            rssiAntCtl = new ArrayList<Integer>((int) (4));
            for (int i = 0; i < 4; i++) {
                this.rssiAntCtl.add(this._io.readU1());
            }
            rssiAntExt = new ArrayList<Integer>((int) (4));
            for (int i = 0; i < 4; i++) {
                this.rssiAntExt.add(this._io.readU1());
            }
            this.extChannelFreq = this._io.readU2le();
            this.extChannelFlags = new ChannelFlags(this._io, this, _root);
            rfSignalNoise = new ArrayList<SignalNoise>((int) (4));
            for (int i = 0; i < 4; i++) {
                this.rfSignalNoise.add(new SignalNoise(this._io, this, _root));
            }
            evm = new ArrayList<Long>((int) (4));
            for (int i = 0; i < 4; i++) {
                this.evm.add(this._io.readU4le());
            }
        }

        public MacFlags flags() {
            return flags;
        }

        public long aMpduId() {
            return aMpduId;
        }

        public int numDelimiters() {
            return numDelimiters;
        }

        /**
         * Modulation Coding Scheme (MCS)
         */
        public int mcs() {
            return mcs;
        }

        /**
         * Number of spatial streams (0 = unknown)
         */
        public int numStreams() {
            return numStreams;
        }

        /**
         * RSSI (Received Signal Strength Indication), combined from all active antennas / channels
         */
        public int rssiCombined() {
            return rssiCombined;
        }

        /**
         * RSSI (Received Signal Strength Indication) for antennas 0-3, control channel
         */
        public ArrayList<Integer> rssiAntCtl() {
            return rssiAntCtl;
        }

        /**
         * RSSI (Received Signal Strength Indication) for antennas 0-3, extension channel
         */
        public ArrayList<Integer> rssiAntExt() {
            return rssiAntExt;
        }

        /**
         * Extension channel frequency (MHz)
         */
        public int extChannelFreq() {
            return extChannelFreq;
        }

        /**
         * Extension channel flags
         */
        public ChannelFlags extChannelFlags() {
            return extChannelFlags;
        }

        /**
         * Signal + noise values for antennas 0-3
         */
        public ArrayList<SignalNoise> rfSignalNoise() {
            return rfSignalNoise;
        }

        /**
         * EVM (Error Vector Magnitude) for chains 0-3
         */
        public ArrayList<Long> evm() {
            return evm;
        }

        public PacketPpi _root() {
            return _root;
        }

        public PacketPpi.PacketPpiField _parent() {
            return _parent;
        }

        public static class ChannelFlags extends KaitaiStruct {
            private boolean spectrum2ghz;
            private boolean ofdm;
            private boolean cck;
            private boolean turbo;
            private long unused;
            private boolean gfsk;
            private boolean dynCckOfdm;
            private boolean onlyPassiveScan;
            private boolean spectrum5ghz;
            private PacketPpi _root;
            private PacketPpi.Radio80211nMacPhyExtBody _parent;
            public ChannelFlags(KaitaiStream _io) {
                this(_io, null, null);
            }
            public ChannelFlags(KaitaiStream _io, PacketPpi.Radio80211nMacPhyExtBody _parent) {
                this(_io, _parent, null);
            }
            public ChannelFlags(KaitaiStream _io, PacketPpi.Radio80211nMacPhyExtBody _parent, PacketPpi _root) {
                super(_io);
                this._parent = _parent;
                this._root = _root;
                _read();
            }

            public static ChannelFlags fromFile(String fileName) throws IOException {
                return new ChannelFlags(new ByteBufferKaitaiStream(fileName));
            }

            private void _read() {
                this.spectrum2ghz = this._io.readBitsInt(1) != 0;
                this.ofdm = this._io.readBitsInt(1) != 0;
                this.cck = this._io.readBitsInt(1) != 0;
                this.turbo = this._io.readBitsInt(1) != 0;
                this.unused = this._io.readBitsInt(8);
                this.gfsk = this._io.readBitsInt(1) != 0;
                this.dynCckOfdm = this._io.readBitsInt(1) != 0;
                this.onlyPassiveScan = this._io.readBitsInt(1) != 0;
                this.spectrum5ghz = this._io.readBitsInt(1) != 0;
            }

            /**
             * 2 GHz spectrum
             */
            public boolean spectrum2ghz() {
                return spectrum2ghz;
            }

            /**
             * OFDM (Orthogonal Frequency-Division Multiplexing)
             */
            public boolean ofdm() {
                return ofdm;
            }

            /**
             * CCK (Complementary Code Keying)
             */
            public boolean cck() {
                return cck;
            }

            public boolean turbo() {
                return turbo;
            }

            public long unused() {
                return unused;
            }

            /**
             * Gaussian Frequency Shift Keying
             */
            public boolean gfsk() {
                return gfsk;
            }

            /**
             * Dynamic CCK-OFDM
             */
            public boolean dynCckOfdm() {
                return dynCckOfdm;
            }

            /**
             * Only passive scan allowed
             */
            public boolean onlyPassiveScan() {
                return onlyPassiveScan;
            }

            /**
             * 5 GHz spectrum
             */
            public boolean spectrum5ghz() {
                return spectrum5ghz;
            }

            public PacketPpi _root() {
                return _root;
            }

            public PacketPpi.Radio80211nMacPhyExtBody _parent() {
                return _parent;
            }
        }

        /**
         * RF signal + noise pair at a single antenna
         */
        public static class SignalNoise extends KaitaiStruct {
            private byte signal;
            private byte noise;
            private PacketPpi _root;
            private PacketPpi.Radio80211nMacPhyExtBody _parent;

            public SignalNoise(KaitaiStream _io) {
                this(_io, null, null);
            }

            public SignalNoise(KaitaiStream _io, PacketPpi.Radio80211nMacPhyExtBody _parent) {
                this(_io, _parent, null);
            }
            public SignalNoise(KaitaiStream _io, PacketPpi.Radio80211nMacPhyExtBody _parent, PacketPpi _root) {
                super(_io);
                this._parent = _parent;
                this._root = _root;
                _read();
            }

            public static SignalNoise fromFile(String fileName) throws IOException {
                return new SignalNoise(new ByteBufferKaitaiStream(fileName));
            }

            private void _read() {
                this.signal = this._io.readS1();
                this.noise = this._io.readS1();
            }

            /**
             * RF signal, dBm
             */
            public byte signal() {
                return signal;
            }

            /**
             * RF noise, dBm
             */
            public byte noise() {
                return noise;
            }

            public PacketPpi _root() {
                return _root;
            }

            public PacketPpi.Radio80211nMacPhyExtBody _parent() {
                return _parent;
            }
        }
    }
}
