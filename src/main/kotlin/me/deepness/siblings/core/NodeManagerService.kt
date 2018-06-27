package me.deepness.siblings.core

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import me.deepness.siblings.utils.getAppName
import me.deepness.siblings.utils.runningOnPort
import mu.KotlinLogging
import org.apache.curator.framework.CuratorFramework
import org.apache.zookeeper.CreateMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

const val SIBLINGS_ROOT_PATH = "/siblings/e"
const val MACHINE_PATH = "machines"

interface NodeManagerService {
    fun getSiblings(): List<ServiceNode>
}


@Service
class NodeManagerServiceZkImpl : NodeManagerService {
    private val logger = KotlinLogging.logger {}
    @Autowired
    private lateinit var environment: Environment
    @Autowired
    private lateinit var client: CuratorFramework

    override fun getSiblings(): List<ServiceNode> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @PostConstruct
    fun init() {
        registerServiceNode()
    }

    private fun registerServiceNode() {
        val serviceName = environment.getAppName()
        val appPath = "$SIBLINGS_ROOT_PATH/$serviceName"
        val node = ServiceNode.Factory.init(environment.runningOnPort())
        val nodePath = "$appPath/$MACHINE_PATH/${node.ip}:${node.port}"
        val nodeStat = client.checkExists().forPath(nodePath)
        if (nodeStat == null) {
            val mapper = jacksonObjectMapper()
            val jsonStr = mapper.writeValueAsBytes(node)
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(nodePath, jsonStr)
        }
    }


}