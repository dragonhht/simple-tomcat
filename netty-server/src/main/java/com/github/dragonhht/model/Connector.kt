package com.github.dragonhht.model

/**
 * .
 *
 * @author: huang
 * @Date: 2019-4-17
 */
class Connector {
    private var port: Int = 0
    private var protocol: String = "HTTP/1.1"
    private var maxThreads: Int = 0
    private var enableLookups: Boolean = false
    private var acceptCount: Int = 0
    private var connectionTimeout: Int = 0
    private var redirectPort: Int = 0
    private var disableUploadTimeout: Boolean = false
    private var URIEncoding: String = "UTF-8"
}