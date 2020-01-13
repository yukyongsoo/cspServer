package com.yuk.cspserver.storage

import com.yuk.cspserver.storage.strategy.DiskStrategy

data class StorageDTO(val storageId: String,
                      val name : String,
                      val path : String,
                      val usable : Boolean,
                      val strategy : DiskStrategy)