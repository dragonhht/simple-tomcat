package com.github.dragonhht.model

import com.thoughtworks.xstream.annotations.XStreamAlias
import com.thoughtworks.xstream.annotations.XStreamAsAttribute
import com.thoughtworks.xstream.annotations.XStreamImplicit

/**
 * className="org.apache.catalina.realm.UserDatabaseRealm"
resourceName="UserDatabase".
 *
 * @author: huang
 * @Date: 2019-4-17
 */
@XStreamAlias("Realm")
class Realm {
    @XStreamAsAttribute
    var className: String? = null
    @XStreamAsAttribute
    var resourceName: String? = null
    @XStreamImplicit(itemFieldName = "Realm")
    var realms: MutableList<Realm> = mutableListOf()

    override fun toString(): String {
        return "Realm(className=$className, resourceName=$resourceName, realms=\n$realms)"
    }


}