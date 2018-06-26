package me.deepness.siblings.core

import me.deepness.siblings.utils.getHostName
import me.deepness.siblings.utils.getIPAddress
import java.util.*

data class ServiceNode(
        val uuid: String,
        val ip: String,
        val port: String,
        val hostName: String,
        val version: Long
) {
    object Factory {
        fun init(port: String): ServiceNode {
            val ip = getIPAddress()
            val hostName = getHostName()
            val uuid = "$ip$$port$${UUID.randomUUID().toString().replace("-", "").toUpperCase()} "
            return ServiceNode(uuid = uuid, ip = ip, hostName = hostName, version = 0L, port = port)
        }
    }
}