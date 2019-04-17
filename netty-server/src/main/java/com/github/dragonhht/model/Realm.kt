package com.github.dragonhht.model

/**
 * className="org.apache.catalina.realm.UserDatabaseRealm"
resourceName="UserDatabase".
 *
 * @author: huang
 * @Date: 2019-4-17
 */
class Realm {
    private var className: String? = null
    private var resourceName: String? = null
    private var realms: MutableList<Realm> = mutableListOf()
}