package me.deepness.siblings.utils

import me.deepness.siblings.core.ServiceNode
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NodeInfoUtilTest {
    @Autowired
    private lateinit var environment: Environment

    @Test
    fun testGetIPAddress() {
        val ip = getIPAddress()
        Assert.assertNotNull(ip)
        Assert.assertFalse(ip.isEmpty())
        println("IP = $ip")
    }

    @Test
    fun testGetHostName() {
        val host = getHostName()
        Assert.assertNotNull(host)
        Assert.assertTrue(host.isNotBlank() && host.isNotEmpty())
        println("host: $host")
    }

    @Test
    fun testGenServiceNode() {
        val port = environment.getProperty("local.server.port") ?: "0"
        val node = ServiceNode.Factory.init(port)
        val applicationName = environment.getProperty("spring.application.name") ?: ""
        println("node: $node")
        println("app: $applicationName")
    }
}