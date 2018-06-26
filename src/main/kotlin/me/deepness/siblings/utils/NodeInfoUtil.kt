package me.deepness.siblings.utils

import java.net.InetAddress
import java.net.NetworkInterface

fun getHostName(): String = InetAddress.getLocalHost().hostName

fun getIPAddress(): String {
    var localIP = "127.0.0.1"
    var netIP: String? = null

    var ip: InetAddress
    val netInterfaces = NetworkInterface.getNetworkInterfaces()
    var findNetIPAddress = false
    while (!findNetIPAddress && netInterfaces.hasMoreElements()) {
        val networkInterface = netInterfaces.nextElement()
        val addresses = networkInterface.inetAddresses
        while (addresses.hasMoreElements()) {
            ip = addresses.nextElement()
            if (!ip.isSiteLocalAddress && !ip.isLoopbackAddress && ip.hostAddress.indexOf(":") == -1) {
                //外网 IP
                netIP = ip.hostAddress
                findNetIPAddress = true
                break
            } else if (ip.isSiteLocalAddress && !ip.isLoopbackAddress && ip.hostAddress.indexOf(":") == -1) {
                //内网 IP
                localIP = ip.hostAddress
            }
        }
    }

    return if (!netIP.isNullOrEmpty()) {
        netIP!!
    } else {
        localIP
    }
}