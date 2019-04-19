package com.github.dragonhht.model

import com.thoughtworks.xstream.annotations.XStreamAsAttribute

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-17
 */
class Connector {
    @XStreamAsAttribute
    var port: Int = 0
    @XStreamAsAttribute
    var protocol: String = "HTTP/1.1"
    @XStreamAsAttribute
    var maxThreads: Int = 0
    @XStreamAsAttribute
    var enableLookups: Boolean = false
    @XStreamAsAttribute
    var acceptCount: Int = 0
    @XStreamAsAttribute
    var connectionTimeout: Int = 0
    @XStreamAsAttribute
    var redirectPort: Int = 0
    @XStreamAsAttribute
    var disableUploadTimeout: Boolean = false
    @XStreamAsAttribute
    var URIEncoding: String = "UTF-8"

    override fun toString(): String {
        return "Connector(port=$port, protocol='$protocol', maxThreads=$maxThreads, enableLookups=$enableLookups, acceptCount=$acceptCount, connectionTimeout=$connectionTimeout, redirectPort=$redirectPort, disableUploadTimeout=$disableUploadTimeout, URIEncoding='$URIEncoding')"
    }


}