package com.yuk.cspserver.element

import com.yuk.cspserver.element.file.File

data class ElementRequestDTO(val contentId : String, val elementTypeId : Int, val file : File)

data class ElementResponseDTO(val id : Int)