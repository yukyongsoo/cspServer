package com.yuk.cspserver.content

import com.yuk.cspserver.content.type.ContentTypeService
import com.yuk.cspserver.element.ElementRequestDTO
import com.yuk.cspserver.element.ElementService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ContentService(private val contentCommandDAO: ContentCommandDAO,
                     private val contentQueryDAO: ContentQueryDAO,
                     private val contentTypeService: ContentTypeService,
                     private val elementService: ElementService) {
    suspend fun createContent(contentRequest: ContentRequestDTO): String {
        val elementType = contentTypeService.getElementType(contentRequest.contentTypeId)
                ?: throw IllegalArgumentException("content Type Not Found. ${contentRequest.contentTypeId}")
        if(contentRequest.name.isBlank())
            throw  IllegalArgumentException("content must have name. you request is ${contentRequest.name}")

        val contentId = UUID.randomUUID().toString()
        contentCommandDAO.createContent(contentId,contentRequest)
        return "/content/$contentId"
    }

    suspend fun getContent(contentId: String) =
            contentQueryDAO.getContent(contentId)?.let {
                ContentResponseDTO(it.id, it.name, it.typeId)
            } ?: throw IllegalStateException("content $contentId not found")

    suspend fun createContentElement(element: ElementRequestDTO): String {
        contentQueryDAO.getContent(element.contentId)
                ?: throw IllegalStateException("content ${element.contentId} not found")
        val elementId = elementService.createElement(element)
        return "/content/${element.contentId}/$elementId"
    }

    suspend fun getContentElement(contentId: String, elementId: String) {
        //TODO :: we need make function
    }
}