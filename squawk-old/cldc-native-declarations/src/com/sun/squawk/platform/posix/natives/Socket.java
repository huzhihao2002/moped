/*
 * Copyright 2004-2008 Sun Microsystems, Inc. All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2
 * only, as published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License version 2 for more details (a copy is
 * included in the LICENSE file that accompanied this code).
 *
 * You should have received a copy of the GNU General Public License
 * version 2 along with this work; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA
 *
 * Please contact Sun Microsystems, Inc., 16 Network Circle, Menlo
 * Park, CA 94025 or visit www.sun.com if you need additional
 * information or have any questions.
 */

package com.sun.squawk.platform.posix.natives;

import com.sun.cldc.jna.*;
import com.sun.cldc.jna.ptr.*;

/**
 *
 * java wrapper around BSD sockets
 */
@Includes({"<sys/socket.h>", "<netinet/in.h>"})
public interface Socket extends Library {
    Socket INSTANCE = (Socket)
            Native.loadLibrary("socket",
                               Socket.class);
        
    //            /* Supported address families. */
    //#define AF_UNSPEC	0
    //#define AF_UNIX		1	/* Unix domain sockets 		*/
    //#define AF_INET		2	/* Internet IP Protocol 	*/
    //#define AF_AX25		3	/* Amateur Radio AX.25 		*/
    //#define AF_IPX		4	/* Novell IPX 			*/
    //#define AF_APPLETALK	5	/* Appletalk DDP 		*/
    //#define	AF_NETROM	6	/* Amateur radio NetROM 	*/
    //#define AF_BRIDGE	7	/* Multiprotocol bridge 	*/
    //#define AF_AAL5		8	/* Reserved for Werner's ATM 	*/
    //#define AF_X25		9	/* Reserved for X.25 project 	*/
    //#define AF_INET6	10	/* IP version 6			*/
    //#define AF_MAX		12	/* For now.. */
    public final static int AF_INET = IMPORT;
    
    /* Socket types. */
    //#define SOCK_RDM	4		/* reliably-delivered message	*/
    //#define SOCK_SEQPACKET	5		/* sequential packet socket	*/
    //#define SOCK_PACKET	10		/* linux specific way of	*/
    public final static int SOCK_STREAM = IMPORT; /* stream (connection) socket	*/
    public final static int SOCK_DGRAM = IMPORT;  /* datagram (conn.less) socket	*/
    public final static int SOCK_RAW = IMPORT;    /* raw socket			*/
    
    /* Definitions of bits in internet address integers. */
    public final static int INADDR_ANY = IMPORT;
    
    public final static int INET_ADDRSTRLEN = IMPORT;
    
    /* Socket options */
    /*
     * Level number for (get/set)sockopt() to apply to socket itself.
     */
    public final static int SOL_SOCKET = IMPORT;		/* options for socket level */
    /*
     * Option flags per-socket.
     */
    public final static int SO_DEBUG = IMPORT;		/* turn on debugging info recording */
    public final static int SO_ACCEPTCONN = IMPORT;		/* socket has had listen() */
    public final static int SO_REUSEADDR = IMPORT;		/* allow local address reuse */
    public final static int SO_KEEPALIVE = IMPORT;		/* keep connections alive */
    public final static int SO_DONTROUTE = IMPORT;		/* just use interface addresses */
    public final static int SO_BROADCAST = IMPORT;		/* permit sending of broadcast msgs */
//#if !defined(_POSIX_C_SOURCE) || defined(_DARWIN_C_SOURCE)
//#define	SO_USELOOPBACK	0x0040		/* bypass hardware when possible */
//#define SO_LINGER	0x0080          /* linger on close if data present (in ticks) */
//#else
//#define SO_LINGER	0x1080          /* linger on close if data present (in seconds) */
//#endif	/* (!_POSIX_C_SOURCE || _DARWIN_C_SOURCE) */
    public final static int SO_OOBINLINE = IMPORT;		/* leave received OOB data in line */
//#if !defined(_POSIX_C_SOURCE) || defined(_DARWIN_C_SOURCE)
//#define	SO_REUSEPORT	0x0200		/* allow local address & port reuse */
//#define	SO_TIMESTAMP	0x0400		/* timestamp received dgram traffic */
//#ifndef __APPLE__
//#define	SO_ACCEPTFILTER	0x1000		/* there is an accept filter */
//#else
//#define SO_DONTTRUNC	0x2000		/* APPLE: Retain unread data */
//					/*  (ATOMIC proto) */
//#define SO_WANTMORE		0x4000		/* APPLE: Give hint when more data ready */
//#define SO_WANTOOBFLAG	0x8000		/* APPLE: Want OOB in MSG_FLAG on receive */
//#endif
//#endif	/* (!_POSIX_C_SOURCE || _DARWIN_C_SOURCE) */ 

    /**
     * socket() creates an endpoint for communication and returns a descriptor.
     * 
     * @param domain specifies a communications domain within which communication
     *               will take place; this selects the protocol family which should
     *               be used. The currently understood formats are:
     *               AF_UNIX, AF_INET, AF_ISO, AF_NS, AF_IMPLINK
     * @param type   specifies the semantics of communication.  Currently defined types are:
     *               SOCK_STREAM, SOCK_DGRAM, SOCK_RAW, SOCK_SEQPACKET, SOCK_RDM
     * @param protocol The protocol number to use is particular to the
     *               communication domain in which communication is to take place; see
     *                protocols(5).
     * @return  A -1 is returned if an error occurs, otherwise the return value is a
     *          descriptor referencing the socket.
     */
     int socket(int domain, int type, int protocol);
     
    /**
     * initiate a connection on a socket.
     * 
     * @param socket socket descriptor
     * @param address ptr to a sockaddr_in buffer
     * @param address_len pass in sockaddr_in.size()
     * @return  A -1 is returned if an error occurs, otherwise the return value is a
     *          descriptor referencing the socket.
     */
    int connect(int socket, sockaddr_in address, int address_len);
    
    /**
     * bind a socket to a port
     * 
     * @param socket socket descriptor
     * @param myaddress ptr to a SockAddr_In buffer
     * @return  A -1 is returned if an error occurs, otherwise the return value is a
     *          descriptor referencing the socket.
     */
    int bind(int socket, sockaddr_in myaddress);
    
    /**
     * accept a connection from a client
     * 
     * @param socket socket descriptor
     * @param remoteAddress ptr to a SockAddr_In buffer that will contain the address of the remote client
     * @param address_len pointer to int containing the size of an IP address
     * @return  A -1 is returned if an error occurs, otherwise the return value is a
     *          descriptor referencing the socket.
     */
    int accept(int socket, sockaddr_in remoteAddress, IntByReference address_len);
     
    /**
     * listen for connections on socket
     * 
     * @param socket socket descriptor
     * @param backlog
     * @return  A -1 is returned if an error occurs, otherwise the return value is a
     *          descriptor referencing the socket.
     */
    int listen(int socket, int backlog);
    
    /**
     * initiate a connection on a socket.
     * 
     * @param socket socket descriptor
     * @param how  If how is SHUT_RD, further receives will be disallowed.  If how
     *             is SHUT_WR, further sends will be disallowed.  If how is SHUT_RDWR, further sends and
     *             receives will be disallowed.
     * @return  A -1 is returned if an error occurs, otherwise zero is returned
     */
     int shutdown(int socket, int how);
     
    /** C STRUCTURE sockaddr_in  /
     struct sockaddr_in {
        u_char  sin_len;     1
        u_char  sin_family;  1
        u_short sin_port;    2
        struct  in_addr sin_addr; 4
        char    sin_zero[8];     8
     }; 
     * 
     struct sockaddr {
	__uint8_t	sa_len;		/* total length 
	sa_family_t	sa_family;	/* [XSI] address family 
	char		sa_data[14];	/* [XSI] addr value (actually larger) 
};
     */
    public final static class sockaddr_in extends Structure {        
        /** u_char 
         * DOES NOT EXIST ON SOLARIS!
         */
        @IfNDef("sun")
        public int sin_len;
        
        /** u_char */
        public int sin_family;
        
        /** u_short */
        public int sin_port;
        
        /** in_addr is an opaque type that is typically a 4-byte int for IPv4.*/
        public int sin_addr;
                
        public sockaddr_in() {
            sin_len = size(); // default....
        }
        
//        public void read() {
//            Pointer p = getPointer();
//            if (layout[SIN_LEN_INDEX] >= 0) {
//                sin_len = p.getByte(layout[SIN_LEN_INDEX]) & 0xFF;
//                sin_family = p.getByte(layout[SIN_FAMILY_INDEX]) & 0xFF;
//            } else {    // Solaris and ?
//                sin_family = p.getShort(layout[SIN_FAMILY_INDEX]) & 0xFFFF;
//            }
//            sin_port    = p.getShort(layout[SIN_PORT_INDEX]) & 0xFFFF;
//            sin_addr    = p.getInt(layout[SIN_ADDR_INDEX]);
//        }
//
//        public void write() {
//            Pointer p = getPointer();
//            clear();
//            if (layout[SIN_LEN_INDEX] >= 0) {
//                p.setByte(layout[SIN_LEN_INDEX], (byte) sin_len);
//                p.setByte(layout[SIN_FAMILY_INDEX], (byte) sin_family);
//            } else {   // Solaris and ?
//                p.setShort(layout[SIN_FAMILY_INDEX], (byte) sin_family);
//            }
//            p.setShort(layout[SIN_PORT_INDEX],  (short)sin_port);
//            p.setInt(layout[SIN_ADDR_INDEX],    sin_addr);
//        }
        
        public String toString() {
            return "Struct_SockAddr{len: " + sin_len + ", family: " + sin_family + ", port: " + sin_port + ", sin_addr: " + sin_addr + "}";
        }
                 
    } /* SockAddr */

    /**
     * Interprets the specified character string as an Internet address, placing the
     * address into the structure provided.  It returns 1 if the string was successfully interpreted, or 0 if
     * the string is invalid
     * 
     * @param str 
     * @param in_addr (OUT) on sucessful return will contain the 32 bits of an IPv4 "struct in_addr"
     * @return true if success
     */
    boolean inet_pton(String str, IntByReference in_addr);

    /**
     * Takes an IPv4 Internet address and returns string representing the address
     * in `.' notation
     * 
     * @param af family (should be AF_INET)
     * @param src a pointer to the src internet address
     * @param dst  a pointer to tmp buffer used to store characters for the result.
     * @param size  the size of the tmp dst buffer 
     * @return String (created from the characters in the dst buffer)
     */
    String inet_ntop(int af, IntByReference src, Pointer dst, int size);
    
    /**
     * set a socket option
     * 
     * @param socket socket descriptor
     * @param level 
     * @param option_name 
     * @param option_value 
     * @param option_len (option_value.size()
     * @return  A -1 is returned if an error occurs, otherwise the return value is a
     *          descriptor referencing the socket.
     */
    int setsockopt(int socket, int level, int option_name, ByReference option_value, int option_len);
    
    /**
     * get a socket option
     * 
     * @param socket socket descriptor
     * @param level 
     * @param option_name 
     * @param option_value 
     * @param option_len On return, the by-reference int will contain the size of the option_value data
     * @return  A -1 is returned if an error occurs, otherwise the return value is a
     *          descriptor referencing the socket.
     */
    int getsockopt(int socket, int level, int option_name, ByReference option_value, IntByReference option_len);

}
