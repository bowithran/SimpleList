package com.example.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object UserSerializer: Serializer<SavedUsers> {
    override val defaultValue: SavedUsers
        get() = SavedUsers.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SavedUsers {
        return try {
            // readFrom is already called on the data store background thread
            SavedUsers.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: SavedUsers, output: OutputStream) {
        // writeTo is already called on the data store background thread
        t.writeTo(output)
    }
}