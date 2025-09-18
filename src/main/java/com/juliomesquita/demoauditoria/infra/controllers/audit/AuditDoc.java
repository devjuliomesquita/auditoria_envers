package com.juliomesquita.demoauditoria.infra.controllers.audit;

import com.juliomesquita.demoauditoria.application.usecases.audit.search.common.AuditEntityType;
import com.juliomesquita.demoauditoria.infra.controllers.shared.DefaultAuthAPIResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Audit", description = "API de gerenciamento de auditoria.")
public interface AuditDoc {
    @Operation(
        operationId = "timelineOfAEntity",
        summary = "Criar buscar uma timeline de uma entidade.",
        description = "Este endpoint recebe os parametros necessarios para a busca de uma timeline de uma entidade.",
        tags = {"Audit"},
        responses = @ApiResponse(responseCode = "200", description = "Ok"),
//        requestBody = @RequestBody(content = {@Content(examples = @ExampleObject(value = ListRolesRequest.exampleRequest))}),
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @DefaultAuthAPIResponses
    @GetMapping("/timeline-entity/{entityId}")
    ResponseEntity<?> timelineOfAEntity(
        @PathVariable(name = "entityId") Long entityId,
        @RequestParam(name = "auditEntityType") AuditEntityType auditEntityType,
        @RequestParam(name = "currentPage", required = false) Integer currentPage,
        @RequestParam(name = "itemsPerPage", required = false) Integer itemsPerPage,
        @RequestParam(name = "terms", required = false) String terms,
        @RequestParam(name = "sort", required = false) String sort,
        @RequestParam(name = "direction", required = false) String direction
    );
}
