package com.yuk.cspserver.element

import com.yuk.cspserver.element.file.ElementFileService
import com.yuk.cspserver.element.rule.ElementRuleService
import com.yuk.cspserver.element.type.ElementTypeService
import org.springframework.stereotype.Service

@Service
class ElementService(private val elementTypeService: ElementTypeService,
                     private val elementRuleService: ElementRuleService,
                     private val elementFileService: ElementFileService,
                     private val elementQueryDAO: ElementQueryDAO,
                     private val elementCommandDAO: ElementCommandDAO) {

    suspend fun createElement(element: ElementRequestDTO): String {
        val elementType = elementTypeService.getType(element.elementTypeId)
        val initializeRules = elementRuleService.getInitializeRule(elementType.id)
        val elementId = elementCommandDAO.createElement(element.elementFile.getName(), element.contentId ,elementType.id) ?.run { this as Int }
                ?: throw IllegalStateException("can't save element, contentId is ${element.contentId}")
        initializeRules.forEach {
            elementFileService.saveFile(it.archiveId,elementId, element)
        }
        return "/storage/${element.contentId}/$elementId"
    }
}
