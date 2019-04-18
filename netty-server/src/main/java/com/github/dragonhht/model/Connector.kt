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
    private var port: Int = 0
    @XStreamAsAttribute
    private var protocol: String = "HTTP/1.1"
    @XStreamAsAttribute
    private var maxThreads: Int = 0
    @XStreamAsAttribute
    private var enableLookups: Boolean = false
    @XStreamAsAttribute
    private var acceptCount: Int = 0
    @XStreamAsAttribute
    private var connectionTimeout: Int = 0
    @XStreamAsAttribute
    private var redirectPort: Int = 0
    @XStreamAsAttribute
    private var disableUploadTimeout: Boolean = false
    @XStreamAsAttribute
    private var URIEncoding: String = "UTF-8"

    override fun toString(): String {
        return "Connector(port=$port, protocol='$protocol', maxThreads=$maxThreads, enableLookups=$enableLookups, acceptCount=$acceptCount, connectionTimeout=$connectionTimeout, redirectPort=$redirectPort, disableUploadTimeout=$disableUploadTimeout, URIEncoding='$URIEncoding')"
    }


}