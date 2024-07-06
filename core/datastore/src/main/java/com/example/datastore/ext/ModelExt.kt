package com.example.datastore.ext

import com.example.datastore.User as DatastoreUser
import com.example.model.User as ModelUser

fun ModelUser.toDataStoreUser(): DatastoreUser {
    return DatastoreUser.newBuilder()
        .setLogin(this.login)
        .setId(this.id)
        .setNodeId(this.nodeId)
        .setAvatarUrl(this.avatarUrl)
        .setGravatarId(this.gravatarId)
        .setUrl(this.url)
        .setHtmlUrl(this.htmlUrl)
        .setFollowersUrl(this.followersUrl)
        .setFollowingUrl(this.followingUrl)
        .setGistsUrl(this.gistsUrl)
        .setStarredUrl(this.starredUrl)
        .setSubscriptionsUrl(this.subscriptionsUrl)
        .setOrganizationsUrl(this.organizationsUrl)
        .setReposUrl(this.reposUrl)
        .setEventsUrl(this.eventsUrl)
        .setReceivedEventsUrl(this.receivedEventsUrl)
        .setType(this.type)
        .setSiteAdmin(this.siteAdmin ?: false)
        .build()
}

fun DatastoreUser.toModelUser(): ModelUser {
    return ModelUser(
        login = this.login,
        id = this.id,
        nodeId = this.nodeId,
        avatarUrl = this.avatarUrl,
        gravatarId = this.gravatarId,
        url = this.url,
        htmlUrl = this.htmlUrl,
        followersUrl = this.followersUrl,
        followingUrl = this.followingUrl,
        gistsUrl = this.gistsUrl,
        starredUrl = this.starredUrl,
        subscriptionsUrl = this.subscriptionsUrl,
        organizationsUrl = this.organizationsUrl,
        reposUrl = this.reposUrl,
        eventsUrl = this.eventsUrl,
        receivedEventsUrl = this.receivedEventsUrl,
        type = this.type,
        siteAdmin = this.siteAdmin
    )
}